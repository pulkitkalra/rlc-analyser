/*
 * Copyright (c) Orchestral Developments Ltd and the Orion Health group of
 * companies (2001 - 2017).
 *
 * This document is copyright. Except for the purpose of fair reviewing, no part
 * of this publication may be reproduced or transmitted in any form or by any
 * means, electronic or mechanical, including photocopying, recording, or any
 * information storage and retrieval system, without permission in writing from
 * the publisher. Infringers of copyright render themselves liable for
 * prosecution.
 */
package com.orchestral.rhapsody.rlcanalyser.store;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.orchestral.rhapsody.rlcanalyser.RLCDataAnalyser;
import com.orchestral.rhapsody.rlcanalyser.TypeCountData;
import com.orchestral.rhapsody.rlcanalyser.store.RLCDataStore.TotalCountDataType;

/**
 * This class is responsible for handling all the implementation for exporting
 * an aggregated view and for individual RLCs in the RLCDataStores to a CSV
 * file.
 */
public class RLCDataExporter {
	/**
	 * MapType Enum is used to identify the type of map required for writing to
	 * CSV.
	 */
	private enum MapType {
		CommunicationPoint,
		InputCommunicationPoint,
		OutputCommunicationPoint,
		Filter,
		Definition;
	}

	private final RLCDataStore currentStore;
	private final Map<String, RLCDataStore> dataStoreMap;
	private final RLCDataAnalyser analyser;
	private final List<String> fileNameHeaderList;

	/**
	 * Constructor initialises the current data store (overall store) and the
	 * dataStoreMap of all RLC files. The RLCDataAnalyser is also initialised so
	 * other analytics can be retrieved.
	 *
	 * @param store
	 * @param dataStoreMap
	 */
	public RLCDataExporter(final RLCDataStore store, final Map<String, RLCDataStore> dataStoreMap) {
		this.currentStore = store;
		this.dataStoreMap = dataStoreMap;
		this.analyser = new RLCDataAnalyser(this.currentStore);
		// construct a modified string list to remove file counter from file name displayed in CSV File.
		this.fileNameHeaderList = new ArrayList<String>(this.dataStoreMap.keySet());
		this.fileNameHeaderList.replaceAll(fileName -> fileName.substring(fileName.indexOf(":") + 1));
	}

	/**
	 * The export method is used to carry out the writing to the CSV File. This
	 * method calls other helper methods which perform the writing operations.
	 *
	 * @param csvFilePath path to which the file will be exported.
	 * @throws IOException
	 */
	public void export(final String csvFilePath) throws IOException {

		final FileWriter writer = new FileWriter(csvFilePath);
		try {
			this.currentStore.getCommunicationPointTypeCounts();
			this.currentStore.getTotalCounts(TotalCountDataType.CommunicationPoints);

			// Total Number of Components
			writeHeader(writer, "Component Summary");
			totalCounts(writer);

			CSVUtils.writeLine(writer, Arrays.asList("")); // empty line
			// Total Number of sub-types of the Components such as Comm Points, Routes, Filters and Definitions.
			writeHeader(writer, "Total Number of types of Components");
			totalCommPointTypes(writer);
			totalFilterTypes(writer);
			totalFilterCountsPerRoute(writer);
			totalDefinitionsCountsPerRoute(writer);
			totalDefinitionTypes(writer);
			// Add the Rhapsody Version numbers
			rhapsodyVersionNumbers(writer);

			writer.flush();
		} finally {
			writer.close();
		}
	}

	/**
	 * writeHeader can be used to write the header row in the CSV File before a
	 * new section of components begins. The row contains a column for
	 * "All Files" and creates other columns each corresponding to all other
	 * files in the dataStoreMap.
	 *
	 * @param writer
	 * @param heading The string which represents the component type fir which
	 *            the information is being exported. E.g. "Component Summary"
	 * @throws IOException
	 */
	private void writeHeader(final FileWriter writer, final String heading) throws IOException {
		final List<String> header = new ArrayList<String>(Arrays.asList(heading, "All Files"));
		header.addAll(this.fileNameHeaderList);
		CSVUtils.writeLine(writer, header);
	}

	/**
	 * writeData is used to create a List<String> containing all the columns
	 * which are going to be written to in a row. The method calls the CSV
	 * writeLine method which performs the actual writing to file.
	 *
	 * @param category type of Data
	 * @param mapList List of Map, containing the overallMap and all the other
	 *            maps for individual RLCs.
	 * @param writer
	 * @throws IOException
	 */
	private void writeData(final String category, final List<Map<String, TypeCountData>> mapList, final FileWriter writer) throws IOException {
		final Map<String, TypeCountData> defaultMap = mapList.get(0); // make the first map default and iterate through it.
		// iterate through all the types of information that needs to be written to CSV
		// for the overall Map. Retrieve the same information from other maps and write it out in the same row.
		for (final Entry<String, TypeCountData> entry : defaultMap.entrySet()) {
			final List<String> stringWrite = new ArrayList<String>();
			final TypeCountData data = entry.getValue();

			stringWrite.add(category + data.getType());
			stringWrite.add(String.valueOf(data.getCounts()));
			final String type = data.getType();

			for (int i = 1; i < mapList.size(); i++) {
				// for mapList, we don't need to iterate through the first index (since that is the defaultMap)
				// so the loop variable i starts at 1.
				final Map<String, TypeCountData> listItem = mapList.get(i);

				if (listItem.get(type) != null) {
					stringWrite.add(String.valueOf(listItem.get(type).getCounts()));
				} else {
					stringWrite.add(0 + "");
				}

			}
			CSVUtils.writeLine(writer, stringWrite);
		}
	}

	/**
	 * Class is used to perform the formatting required to write to a file in
	 * the CSV format.
	 */
	public static class CSVUtils {

		private static final char DEFAULT_SEPARATOR = ',';

		public static void writeLine(final Writer w, final List<String> values) throws IOException {
			writeLine(w, values, DEFAULT_SEPARATOR, ' ');
		}

		public static void writeLine(final Writer w, final List<String> values, final char separators) throws IOException {
			writeLine(w, values, separators, ' ');
		}

		//https://tools.ietf.org/html/rfc4180
		private static String followCVSformat(final String value) {

			String result = value;
			if (result.contains("\"")) {
				result = result.replace("\"", "\"\"");
			}
			return result;
		}

		public static void writeLine(final Writer w, final List<String> values, char separators, final char customQuote) throws IOException {

			boolean first = true;

			//default customQuote is empty

			if (separators == ' ') {
				separators = DEFAULT_SEPARATOR;
			}

			final StringBuilder sb = new StringBuilder();
			for (final String value : values) {
				if (!first) {
					sb.append(separators);
				}
				if (customQuote == ' ') {
					sb.append(followCVSformat(value));
				} else {
					sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
				}

				first = false;
			}
			sb.append("\n");
			w.append(sb.toString());
		}
	}

	/**
	 * Method is used to write the total counts for each of the component types.
	 * This is the component summary that is available at the top of the CSV
	 * file.
	 *
	 * @param writer
	 * @throws IOException
	 */
	private void totalCounts(final FileWriter writer) throws IOException {
		// iterate through all types of total count data types
		for (final TotalCountDataType type : TotalCountDataType.values()) {
			String countType = "";
			// Filter counts need to analysed and are not stored in the DataStore, so we need
			// to create the RLCDataAnalyser object to invoke the filter analysis method.
			if (type.equals(TotalCountDataType.Filters)) {
				countType = this.analyser.getNumberOfFilters() + "";
			} else {
				countType = this.currentStore.getTotalCounts(type) + "";
			}

			final List<String> stringWrite = new ArrayList<String>();

			stringWrite.add("Number of " + type.toString());
			stringWrite.add(countType);
			// iterate through the map of individual RLCDataStores and get Filter Counts.
			for (final RLCDataStore store : this.dataStoreMap.values()) {
				// Filter counts again need to be acquired from the RLCDataAnalyser
				if (type.equals(TotalCountDataType.Filters)) {
					final RLCDataAnalyser fileAnalyser = new RLCDataAnalyser(store);
					stringWrite.add(fileAnalyser.getNumberOfFilters() + "");
				} else {
					stringWrite.add(store.getTotalCounts(type) + "");
				}

			}
			CSVUtils.writeLine(writer, stringWrite);
		}
	}

	/**
	 * Method is called by the export method and calls other helper methods to
	 * write out the comm points summary and the input and output comm point
	 * summary.
	 *
	 * @param writer
	 * @throws IOException
	 */
	private void totalCommPointTypes(final FileWriter writer) throws IOException {

		writeData("Comm Point: No. of ", getEngineMaps(MapType.CommunicationPoint), writer);
		CSVUtils.writeLine(writer, Arrays.asList("")); // empty line
		writeHeader(writer, "Input Comm Points");
		writeData("Input Comm Point: No. of ", getEngineMaps(MapType.InputCommunicationPoint), writer);
		CSVUtils.writeLine(writer, Arrays.asList("")); // empty line
		writeHeader(writer, "Output Comm Points");
		writeData("Output Comm Point: No. of ", getEngineMaps(MapType.OutputCommunicationPoint), writer);
	}

	/**
	 * Used to write total types of each Filter in an RLC.
	 *
	 * @param writer
	 * @throws IOException
	 */
	private void totalFilterTypes(final FileWriter writer) throws IOException {
		CSVUtils.writeLine(writer, Arrays.asList("")); // empty line
		writeHeader(writer, "Filter Types");
		writeData("Filter Type: No. of ", getEngineMaps(MapType.Filter), writer);
	}

	/**
	 * Used to write total types of definitions in each RLC.
	 *
	 * @param writer
	 * @throws IOException
	 */
	private void totalDefinitionTypes(final FileWriter writer) throws IOException {
		CSVUtils.writeLine(writer, Arrays.asList("")); // empty line
		writeHeader(writer, "Definition Type");
		writeData("Definition Type: ", getEngineMaps(MapType.Definition), writer);
	}

	/**
	 * Used to write filter counts per route.
	 *
	 * @param writer
	 * @throws IOException
	 */
	private void totalFilterCountsPerRoute(final FileWriter writer) throws IOException {
		CSVUtils.writeLine(writer, Arrays.asList("")); // empty line
		writeHeader(writer, "Filters per route");
		final int[] overallCounts = this.currentStore.getFilterCountsPerRoute();
		final List<String> stringWrite = new ArrayList<String>();
		for (int i = 0; i < overallCounts.length; i++) {
			stringWrite.clear();
			// final index needs to have different name: e.g. 30 or more
			if (i == overallCounts.length - 1) {
				stringWrite.add("Filters per Route: " + i + " or more");
			} else {
				stringWrite.add("Filters per Route: " + i);
			}
			stringWrite.add(overallCounts[i] + "");
			for (final RLCDataStore store : this.dataStoreMap.values()) {
				stringWrite.add(String.valueOf(store.getFilterCountsPerRoute()[i]));
			}
			CSVUtils.writeLine(writer, stringWrite);
		}
	}

	/**
	 * Used to write definitions counts per route.
	 *
	 * @param writer
	 * @throws IOException
	 */
	private void totalDefinitionsCountsPerRoute(final FileWriter writer) throws IOException {
		CSVUtils.writeLine(writer, Arrays.asList("")); // empty line
		writeHeader(writer, "Definitions per route");
		final int[] overallCounts = this.currentStore.getDefinitionCountsPerRoute();
		final List<String> stringWrite = new ArrayList<String>();
		for (int i = 0; i < overallCounts.length; i++) {
			stringWrite.clear();
			// final index needs to have different name: e.g. 10 or more
			if (i == overallCounts.length - 1) {
				stringWrite.add("Definitions per Route: " + i + " or more");
			} else {
				stringWrite.add("Definitions per Route: " + i);
			}

			stringWrite.add(overallCounts[i] + "");
			for (final RLCDataStore store : this.dataStoreMap.values()) {
				stringWrite.add(String.valueOf(store.getDefinitionCountsPerRoute()[i]));
			}
			CSVUtils.writeLine(writer, stringWrite);
		}
	}

	/**
	 * Used to write the Rhapsody engine version number
	 * 
	 * @param writer
	 * @throws IOException
	 */
	private void rhapsodyVersionNumbers(final FileWriter writer) throws IOException {
		CSVUtils.writeLine(writer, Arrays.asList("")); // empty line
		writeHeader(writer, "Approximate Rhapsody Engine Number");

		final List<String> stringWriteMax = new ArrayList<String>();
		final List<String> stringWriteMin = new ArrayList<String>();

		stringWriteMax.add("Maximum Rhapsody Engine Version:");
		stringWriteMin.add("Minimum Rhapsody Engine Version:");
		stringWriteMax.add(this.currentStore.getRhapsodyVersion().getMaxVersion().toString());
		stringWriteMin.add(this.currentStore.getRhapsodyVersion().getMinVersion().toString());

		for (final RLCDataStore store : this.dataStoreMap.values()) {
			// Loop through all the datastore
			stringWriteMax.add(store.getRhapsodyVersion().getMaxVersion().toString());
			stringWriteMin.add(store.getRhapsodyVersion().getMinVersion().toString());
		}

		CSVUtils.writeLine(writer, stringWriteMax);
		CSVUtils.writeLine(writer, stringWriteMin);
	}

	/**
	 * The order in which the output List is populated is: All Files map first,
	 * then the other maps. This order is important to maintain when the list is
	 * generated.
	 *
	 * @param type MapType enum to specify which map to generate.
	 * @return an empty list if an invalid MapType is specified.
	 */
	private List<Map<String, TypeCountData>> getEngineMaps(final MapType type) {

		final List<Map<String, TypeCountData>> enginesMap = new ArrayList<Map<String, TypeCountData>>();
		switch (type) {
			case CommunicationPoint:
				enginesMap.add(this.currentStore.getCommunicationPointTypeCounts());
				for (final RLCDataStore store : this.dataStoreMap.values()) {
					enginesMap.add(store.getCommunicationPointTypeCounts());
				}
				return enginesMap;
			case InputCommunicationPoint:
				enginesMap.add(this.currentStore.getInputCommunicationPointTypeCounts());
				for (final RLCDataStore store : this.dataStoreMap.values()) {
					enginesMap.add(store.getInputCommunicationPointTypeCounts());
				}
				return enginesMap;
			case OutputCommunicationPoint:
				enginesMap.add(this.currentStore.getOutputCommunicationPointTypeCounts());
				for (final RLCDataStore store : this.dataStoreMap.values()) {
					enginesMap.add(store.getOutputCommunicationPointTypeCounts());
				}
				return enginesMap;
			case Filter:
				enginesMap.add(this.currentStore.getFilterTypeCounts());
				for (final RLCDataStore store : this.dataStoreMap.values()) {
					enginesMap.add(store.getFilterTypeCounts());
				}
				return enginesMap;
			case Definition:
				enginesMap.add(this.currentStore.getDefinitionTypeCounts());
				for (final RLCDataStore store : this.dataStoreMap.values()) {
					enginesMap.add(store.getDefinitionTypeCounts());
				}
				return enginesMap;
			default:
				return enginesMap;
		}
	}
}
