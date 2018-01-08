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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.orchestral.rhapsody.rlcanalyser.store.ConfigurationDataStore;
import com.orchestral.rhapsody.rlcanalyser.store.ConfigurationDataStoreFactory;
import com.orchestral.rhapsody.rlcanalyser.store.RLCDataStore;

public class RlcAnalyserTest {

	private RLCDataStore dataStore;
	private ConfigurationDataStore configurationDataStore;
	private RLCDataCollector dataCollector;
	private RlcAnalyser rlcAnalyser;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() {
		this.dataStore = new RLCDataStore();
		this.configurationDataStore = new ConfigurationDataStore(
				ConfigurationDataStoreFactory.get().createAllCommunicationPointStores(),
				ConfigurationDataStoreFactory.get().createAllFilterStores());
		this.dataCollector = new RLCDataCollector(this.dataStore, this.configurationDataStore);
		this.rlcAnalyser = new RlcAnalyser(this.dataCollector);
	}

	@Test
	public void testFileNotFoundExceptionFileHandling() throws IOException {
		final File file = new File("test/input/folderDoesNotExist/thisFileIsFake.xml");

		this.thrown.expect(FileNotFoundException.class);
		this.thrown.expectMessage("Unable to determine file type of: " + file.getAbsolutePath());

		this.rlcAnalyser.analysePath(file);
	}

	@Test
	public void testIOExceptionExceptionHandling() throws IOException {
		final File file = new File("test/input/basicTestXML/LockerTest.xml");

		this.thrown.expect(IOException.class);
		this.thrown.expectMessage("IOException occurs on: " + file.getAbsolutePath());

		this.rlcAnalyser.analysePath(file);
	}
}
