/*
 * Copyright (c) Orchestral Developments Ltd and the Orion Health group of companies (2001 - 2017).
 *
 * This document is copyright. Except for the purpose of fair reviewing, no part
 * of this publication may be reproduced or transmitted in any form or by any
 * means, electronic or mechanical, including photocopying, recording, or any
 * information storage and retrieval system, without permission in writing from
 * the publisher. Infringers of copyright render themselves liable for
 * prosecution.
 */

package com.orchestral.rhapsody.rlcanalyser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.log4j.Logger;

import com.orchestral.rhapsody.rlcanalyser.io.Engine;
import com.orchestral.rhapsody.rlcanalyser.io.RlcParser;

/**
 * The RlcAnalyser Class allows for processing of RLC files and recursive
 * calling for a root directory to find all RLC files in nested and simple
 * directory structures.
 */
public class RlcAnalyser {

	private final static Logger logger = Logger.getLogger(RlcAnalyser.class);

	private final static FilenameFilter rlcFilter = new RlcFileAndDirectoryFilter();
	private final static RlcParser parser = new RlcParser();
	private final List<Engine> engineList = new ArrayList<Engine>();

	private final RLCDataCollector dataCollector;

	public RlcAnalyser(final RLCDataCollector dataCollector) {
		this.dataCollector = dataCollector;
	}

	private int fileCounter = 0;

	/**
	 * The method analysePath is a recursively called method on the root
	 * directory. The method determines the type of file the File input is. If
	 * the File input to the method is a directory it will continue to call
	 * itself until it reaches a document in which then analyseRlc is called.
	 *
	 * @param file
	 *            - root file/directory
	 * @throws IOException
	 *             propagated from analyseRlc
	 * @throws FileNotFoundException
	 *             thrown when unable to determine the file
	 * @throws RuntimeException
	 *             thrown when error in reading the contents of the file in
	 *             analyseRlc
	 */

	public void analysePath(final File file) throws FileNotFoundException, IOException, RuntimeException {
		// Determine the type of file the input file is
		if (file.isDirectory()) {
			for (final File childFile : file.listFiles(rlcFilter)) {
				analysePath(childFile);
			}
		} else if (file.isFile()) {
			try {
				analyseRlc(file);
			} catch (final IOException e) {
				logger.error("Could not analyse RLC: " + file.getAbsolutePath(), e);
				throw new IOException("IOException occurs on: " + file.getAbsolutePath());
			}
		} else {
			logger.error("Specified file is invalid or doesn't exist: " + file.getAbsolutePath(),
					new IOException("File not found."));
			throw new FileNotFoundException("Unable to determine file type of: " + file.getAbsolutePath());
		}
	}

	/**
	 * This method takes a potential RLC file and unpacks it as a ZIP file. Then
	 * filters through the entries in the ZIP file to find the XML file for
	 * processing. XML is then parsed through XStream and stored in an engine
	 * for data collection.
	 *
	 * @param file
	 *            - file for processing
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void analyseRlc(final File file) throws FileNotFoundException, IOException, RuntimeException {
		final InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		final ZipArchiveInputStream zipInputStream = new ZipArchiveInputStream(inputStream);
		ZipArchiveEntry entry;
		try {
			while ((entry = zipInputStream.getNextZipEntry()) != null) {
				// Find the XML configuration file and ignore the rest
				if (!entry.isDirectory()) {
					final String entryName = entry.getName().trim().toLowerCase();
					final int directoryIndex = entryName.indexOf('/');

					if (entryName.length() > 4 && entryName.endsWith(".xml") && directoryIndex <= 0) {
						if (!zipInputStream.canReadEntryData(entry)) {
							throw new RuntimeException("RLC file could not be processed.");
						}
						logger.info("[PROCESSING] : " + entryName);

						final Engine engine = parser.parse(zipInputStream);

						if (engine == null) {
							// Null returned do not add to engine list
							logger.error("[ERROR] : Could not process " + entryName);
						} else {
							this.fileCounter++; // increment file count.
							// file count used as a way to name files and distinguish RLC's with same names.
							// Name of a file: FileCoutner + ":" + <FileName>. n is the file count.
							engine.setFileName(this.fileCounter + ":" + file.getName());
							this.engineList.add(engine);
							this.dataCollector.collectData(engine);
						}
					}
				}
			}
		} finally {
			zipInputStream.close();
		}
	}

	/**
	 * Function to fetch the engine list for further processing
	 *
	 * @return List of engines
	 */
	public List<Engine> getEngineList() {
		return this.engineList;
	}
}
