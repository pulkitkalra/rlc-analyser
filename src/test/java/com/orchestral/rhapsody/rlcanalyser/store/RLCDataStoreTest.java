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

import com.orchestral.rhapsody.rlcanalyser.RLCDataCollector;

public class RLCDataStoreTest {

	private RLCDataStore dataStore;

	private ConfigurationDataStore configurationDataStore;

	private RLCDataCollector dataCollector;

	//	@Before
	//	public void setUp() {
	//		this.dataStore = new RLCDataStore();
	//		final List<CommunicationPointConfigurationDataStore> communicationPointConfigurationStores = new ArrayList<CommunicationPointConfigurationDataStore>();
	//		final List<FilterConfigurationDataStore> filterConfigurationStores = new ArrayList<FilterConfigurationDataStore>();
	//		this.configurationDataStore = ConfigurationDataStore.getConfigStoreInstance();
	//		this.dataCollector = new RLCDataCollector(this.dataStore, this.configurationDataStore);
	//	}

	//	@Test
	//	public void testCommunicationPointData() {
	//		final List<CommunicationPoint> communicationPoints = new ArrayList<CommunicationPoint>();
	//		communicationPoints.add(createTCPClientCommunicationPoint(
	//				new String[]{"route-one", "route-two", "route-three", "route-four"},
	//				new String[]{"route-one", "route-two", "route-three", "route-four"}));
	//		communicationPoints.add(createTCPClientCommunicationPoint(
	//				new String[]{"route-one", "route-two", "route-three", "route-four"},
	//				new String[]{"route-one", "route-two", "route-three", "route-four"}));
	//		communicationPoints.add(createDatabaseCommunicationPoint(
	//				new String[]{"route-one", "route-two"},
	//				new String[]{"route-one", "route-two"}));
	//		communicationPoints.add(createDirectoryCommunicationPoint(
	//				new String[]{},
	//				new String[]{"route-one", "route-two", "route-three", "route-four"}));
	//		communicationPoints.add(createDirectoryCommunicationPoint(
	//				new String[]{"route-one"},
	//				new String[]{}));
	//		communicationPoints.add(createDirectoryCommunicationPoint(
	//				new String[]{},
	//				new String[]{"route-four"}));
	//
	//		final List<Route> routes = new ArrayList<Route>();
	//		this.dataCollector.collectData(communicationPoints, routes);
	//
	//		final RLCDataAnalyser dataAnalyser = new RLCDataAnalyser(dataStore);
	//
	//		final List<TypeCountData> typeCounts = dataAnalyser.getMostUsedCommunicationPoints(10);
	//		Assert.assertEquals(3, typeCounts.size());
	//		Assert.assertEquals("Directory", typeCounts.get(0).getType());
	//		Assert.assertEquals(3, typeCounts.get(0).getCounts());
	//		Assert.assertEquals("TCPClient", typeCounts.get(1).getType());
	//		Assert.assertEquals(2, typeCounts.get(1).getCounts());
	//		Assert.assertEquals("Database", typeCounts.get(2).getType());
	//		Assert.assertEquals(1, typeCounts.get(2).getCounts());
	//
	//		Assert.assertEquals(6, dataAnalyser.getNumberOfCommunicationPoints());
	//
	//		final List<TypeCountData> inputCounts = dataAnalyser.getMostUsedInputCommunicationPoints(10);
	//		Assert.assertEquals(3, inputCounts.size());
	//		Assert.assertEquals("TCPClient", inputCounts.get(0).getType());
	//		Assert.assertEquals(8, inputCounts.get(0).getCounts());
	//		Assert.assertEquals("Database", inputCounts.get(1).getType());
	//		Assert.assertEquals(2, inputCounts.get(1).getCounts());
	//		Assert.assertEquals("Directory", inputCounts.get(2).getType());
	//		Assert.assertEquals(1, inputCounts.get(2).getCounts());
	//
	//		Assert.assertEquals(11, dataAnalyser.getNumberOfInputCommunicationPoints());
	//
	//		final List<TypeCountData> outputCounts = dataAnalyser.getMostUsedOutputCommunicationPoints(10);
	//		Assert.assertEquals(3, outputCounts.size());
	//		Assert.assertEquals("TCPClient", outputCounts.get(0).getType());
	//		Assert.assertEquals(8, outputCounts.get(0).getCounts());
	//		Assert.assertEquals("Directory", outputCounts.get(1).getType());
	//		Assert.assertEquals(5, outputCounts.get(1).getCounts());
	//		Assert.assertEquals("Database", outputCounts.get(2).getType());
	//		Assert.assertEquals(2, outputCounts.get(2).getCounts());
	//
	//		Assert.assertEquals(15, dataAnalyser.getNumberOfOutputCommunicationPoints());
	//	}

	//	@Test
	//	public void testRouteData() {
	//		final List<CommunicationPoint> communicationPoints = new ArrayList<CommunicationPoint>();
	//		communicationPoints.add(createTCPClientCommunicationPoint(
	//				new String[]{"route-one", "route-two", "route-three", "route-four"},
	//				new String[]{"route-one", "route-two", "route-three", "route-four"}));
	//
	//		final List<Route> routes = new ArrayList<Route>();
	//		routes.add(createRoute(new String[]{"Mapper"}));
	//		routes.add(createRoute(new String[]{"Batcher", "Mapper", "ExecuteScript", "XSD Validation Filter"}));
	//		routes.add(createRoute(new String[]{"Batcher", "No-op", "DatabaseLookup", "Mapper"}));
	//		routes.add(createRoute(new String[]{}));
	//		routes.add(createRoute(new String[]{"Batcher", "Mapper"}));
	//		routes.add(createRoute(new String[]{"ExecuteScript"}));
	//		routes.add(createRoute(new String[]{"No-op"}));
	//		this.dataCollector.collectData(communicationPoints, routes);
	//
	//		final RLCDataAnalyser dataAnalyser = new RLCDataAnalyser(dataStore);
	//
	//		final List<TypeCountData> typeCounts = dataAnalyser.getMostUsedFilters(10);
	//		Assert.assertEquals(6, typeCounts.size());
	//		Assert.assertEquals("Mapper", typeCounts.get(0).getType());
	//		Assert.assertEquals(4, typeCounts.get(0).getCounts());
	//		Assert.assertEquals("Batcher", typeCounts.get(1).getType());
	//		Assert.assertEquals(3, typeCounts.get(1).getCounts());
	//		Assert.assertEquals("No-op", typeCounts.get(2).getType());
	//		Assert.assertEquals(2, typeCounts.get(2).getCounts());
	//		Assert.assertEquals("ExecuteScript", typeCounts.get(3).getType());
	//		Assert.assertEquals(2, typeCounts.get(3).getCounts());
	//		Assert.assertEquals("XSD Validation Filter", typeCounts.get(4).getType());
	//		Assert.assertEquals(1, typeCounts.get(4).getCounts());
	//		Assert.assertEquals("DatabaseLookup", typeCounts.get(5).getType());
	//		Assert.assertEquals(1, typeCounts.get(5).getCounts());
	//
	//		final int[] filterCounts = dataAnalyser.getFilterCountsPerRoute();
	//		Assert.assertEquals(1, filterCounts[0]);
	//		Assert.assertEquals(3, filterCounts[1]);
	//		Assert.assertEquals(1, filterCounts[2]);
	//		Assert.assertEquals(0, filterCounts[3]);
	//		Assert.assertEquals(2, filterCounts[4]);
	//		for (int i=5; i<filterCounts.length; i++) {
	//			Assert.assertEquals(0, filterCounts[i]);
	//		}
	//		Assert.assertEquals(13, dataAnalyser.getNumberOfFilters());
	//	}
	//
	//	private CommunicationPoint createTCPClientCommunicationPoint(final String[] inputRouteIDs,
	//																 final String[] outputRouteIDs) {
	//		int random = (int) Math.random();
	//		return createCommunicationPoint("testTcp-"+random, "TCPClient", inputRouteIDs, outputRouteIDs);
	//	}
	//
	//	private CommunicationPoint createDirectoryCommunicationPoint(final String[] inputRouteIDs,
	//																 final String[] outputRouteIDs) {
	//		int random = (int) Math.random();
	//		return createCommunicationPoint("directory-"+random, "Directory", inputRouteIDs, outputRouteIDs);
	//	}
	//
	//	private CommunicationPoint createDatabaseCommunicationPoint(final String[] inputRouteIDs,
	//																final String[] outputRouteIDs) {
	//		int random = (int) Math.random();
	//		return createCommunicationPoint("database-"+random, "Database", inputRouteIDs, outputRouteIDs);
	//	}
	//
	//	private CommunicationPoint createCommunicationPoint(final String id, final String type,
	//														final String[] inputRouteIDs,
	//														final String[] outputRouteIDs) {
	//
	//		return new CommunicationPoint(id, type,
	//				Arrays.asList(inputRouteIDs),
	//				Arrays.asList(outputRouteIDs),
	//				Collections.EMPTY_LIST,
	//				Collections.EMPTY_LIST,
	//				0,
	//				"INPUT",
	//				5,
	//				5,
	//				-1,
	//				"",
	//				-1,
	//				-1,
	//				-1,
	//				"Runnint",
	//				"/");
	//	}

	//	private Route createRoute(final String[] filterTypes) {
	//		final List<Filter> filters = new ArrayList<Filter>();
	//		for (final String type : filterTypes) {
	//			filters.add(createFilter(type));
	//		}
	//		int random = (int) Math.random();
	//		Route route = new Route();
	//		route.setId("route-"+random);
	//		route.setFilters(filters);
	//		return route;
	//	}
	//
	//	private Filter createFilter(final String type) {
	//		int random = (int) Math.random();
	//		Filter filter = new Filter();
	//		filter.setId("filter-"+random);
	//		filter.setType(type);
	//		filter.setConfiguration(new ArrayList<Property>());
	//		return filter;
	//	}
}
