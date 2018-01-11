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
package com.orchestral.rhapsody.rlcanalyser.store;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import com.orchestral.rhapsody.rlcanalyser.RLCDataCollector;
import com.orchestral.rhapsody.rlcanalyser.TypeCountData;
import com.orchestral.rhapsody.rlcanalyser.io.Engine;
import com.orchestral.rhapsody.rlcanalyser.io.XStreamInstance;

public class RLCDataStoreTest {
	private XStreamInstance xstream;

	private RLCDataStore dataStore;

	private ConfigurationDataStore configurationDataStore;

	private RLCDataCollector dataCollector;

	@Before
	public void setUp() {
		this.dataStore = new RLCDataStore();
		final List<CommunicationPointConfigurationDataStore> communicationPointConfigurationStores = new ArrayList<>();
		final List<FilterConfigurationDataStore> filterConfigurationStores = new ArrayList<>();
		this.configurationDataStore = new ConfigurationDataStore(communicationPointConfigurationStores, filterConfigurationStores);
		this.dataCollector = new RLCDataCollector(this.dataStore, this.configurationDataStore);
		this.xstream = new XStreamInstance();
	}

	@Test
	public void testCommunicationPointGeneralData() {
		Object o1 = null;
		String inputString = "";

		try {
			final File file = new File("test_files/commPointGenPropTest.txt");
			final Scanner input = new Scanner(file);
			inputString = input.useDelimiter("\\A").next();
			o1 = this.xstream.fromXML(inputString);
			input.close();
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}

		final Engine testEngine = (Engine) o1;
		this.dataCollector.collectData(testEngine);
		// getDataStoreMap is used when there are defined filenames as input.
		// for testing, we pass in a .txt file instead of an RLC so the DataStoreMap
		// uses a null key to store the RLCDataStore.
		final RLCDataStore map = this.dataCollector.getDataStoreMap().get(null);
		final Map<String, Map<GeneralTabType, TypeCountData>> generalPropertyMap = map.getCommunicationPointGeneralProperties();
		// Test that there are 3 sinks
		assertEquals(generalPropertyMap.get("Sink").get(GeneralTabType.TotalCounts).getCounts(), 3);
		// Test that there are correct Start States for all Sinks:
		assertEquals(generalPropertyMap.get("Sink").get(GeneralTabType.DONT_START).getCounts(), 1);
		assertEquals(generalPropertyMap.get("Sink").get(GeneralTabType.START_LEVEL_2).getCounts(), 1);
		assertEquals(generalPropertyMap.get("Sink").get(GeneralTabType.START_LEVEL_3).getCounts(), 1);
		// Test that there is 1 AmazonSNS comm point
		assertEquals(generalPropertyMap.get("Amazon SNS").get(GeneralTabType.TotalCounts).getCounts(), 1);
		// test that the SNS Comm point has only one startup state of START_LEVEL_3
		assertEquals(generalPropertyMap.get("Amazon SNS").get(GeneralTabType.START_LEVEL_3).getCounts(), 1);
		// Test 5 email comm points and 2 totals DONT_STARTs among them
		assertEquals(generalPropertyMap.get("E-mail").get(GeneralTabType.TotalCounts).getCounts(), 5);
		assertEquals(generalPropertyMap.get("E-mail").get(GeneralTabType.DONT_START).getCounts(), 2);
		// Test 4 Input Connection Mode in Email:
		assertEquals(generalPropertyMap.get("E-mail").get(GeneralTabType.cpmInput).getCounts(), 4);
		// Test HTTP Server and Amazon SNS
		assertEquals(generalPropertyMap.get("HTTP Server").get(GeneralTabType.cpmInput).getCounts(), 1);
		assertEquals(generalPropertyMap.get("Amazon SNS").get(GeneralTabType.cpmOutput).getCounts(), 1);

	}

	@Test
	public void testAllGeneralData() {
		Object o1 = null;
		String inputString = "";

		try {
			final File file = new File("test_files/testGenPropCustom.txt");
			final Scanner input = new Scanner(file);
			inputString = input.useDelimiter("\\A").next();
			o1 = this.xstream.fromXML(inputString);
			input.close();
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}

		final Engine testEngine = (Engine) o1;
		this.dataCollector.collectData(testEngine);
		// getDataStoreMap is used when there are defined filenames as input.
		// for testing, we pass in a .txt file instead of an RLC so the DataStoreMap
		// uses a null key to store the RLCDataStore.
		final RLCDataStore map = this.dataCollector.getDataStoreMap().get(null);
		final Map<String, Map<GeneralTabType, TypeCountData>> generalPropertyMap = map.getCommunicationPointGeneralProperties();
		// Test Email
		assertEquals(generalPropertyMap.get("E-mail").get(GeneralTabType.TotalCounts).getCounts(), 2);
		assertEquals(generalPropertyMap.get("E-mail").get(GeneralTabType.DONT_START).getCounts(), 2);
		assertEquals(generalPropertyMap.get("E-mail").get(GeneralTabType.cpmInput).getCounts(), 1);
		assertEquals(generalPropertyMap.get("E-mail").get(GeneralTabType.cpmOutput).getCounts(), 1);
		assertEquals(generalPropertyMap.get("E-mail").get(GeneralTabType.rtLinear).getCounts(), 2);
		// Test FTP Client
		assertEquals(generalPropertyMap.get("FTP Client (deprecated)").get(GeneralTabType.TotalCounts).getCounts(), 1);
		assertEquals(generalPropertyMap.get("FTP Client (deprecated)").get(GeneralTabType.START_LEVEL_5).getCounts(), 1);
		assertEquals(generalPropertyMap.get("FTP Client (deprecated)").get(GeneralTabType.cpmOutIn).getCounts(), 1);
		assertEquals(generalPropertyMap.get("FTP Client (deprecated)").get(GeneralTabType.rtLinear).getCounts(), 1);
		// Test Directory
		assertEquals(generalPropertyMap.get("Directory").get(GeneralTabType.TotalCounts).getCounts(), 2);
		assertEquals(generalPropertyMap.get("Directory").get(GeneralTabType.START_LEVEL_2).getCounts(), 1);
		assertEquals(generalPropertyMap.get("Directory").get(GeneralTabType.START_LEVEL_3).getCounts(), 1);
		assertEquals(generalPropertyMap.get("Directory").get(GeneralTabType.cpmBidirectional).getCounts(), 1);
		assertEquals(generalPropertyMap.get("Directory").get(GeneralTabType.cpmInOut).getCounts(), 1);
		assertEquals(generalPropertyMap.get("Directory").get(GeneralTabType.rtExponential).getCounts(), 1);
		// Test Database
		assertEquals(generalPropertyMap.get("Database").get(GeneralTabType.TotalCounts).getCounts(), 1);
		assertEquals(generalPropertyMap.get("Database").get(GeneralTabType.START_LEVEL_1).getCounts(), 1);
		assertEquals(generalPropertyMap.get("Database").get(GeneralTabType.cpmOutput).getCounts(), 1);
		assertEquals(generalPropertyMap.get("Database").get(GeneralTabType.rtImmediate).getCounts(), 1);

	}
}
