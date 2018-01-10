/*
 * Copyright (c) Orchestral Developments Ltd (2001 - 2011).
 *
 * This document is copyright. Except for the purpose of fair reviewing, no part
 * of this publication may be reproduced or transmitted in any form or by any
 * means, electronic or mechanical, including photocopying, recording, or any
 * information storage and retrieval system, without permission in writing from
 * the publisher. Infringers of copyright render themselves liable for
 * prosecution.
 *
 * $Id$
 */
package com.orchestral.rhapsody.rlcanalyser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.orchestral.rhapsody.rlcanalyser.io.CommunicationPoint;
import com.orchestral.rhapsody.rlcanalyser.io.Definition;
import com.orchestral.rhapsody.rlcanalyser.io.Definition.DefinitionType;
import com.orchestral.rhapsody.rlcanalyser.io.Engine;
import com.orchestral.rhapsody.rlcanalyser.io.Filter;
import com.orchestral.rhapsody.rlcanalyser.io.Locker;
import com.orchestral.rhapsody.rlcanalyser.io.LookupTable;
import com.orchestral.rhapsody.rlcanalyser.io.RestClient;
import com.orchestral.rhapsody.rlcanalyser.io.RhapsodyVersion;
import com.orchestral.rhapsody.rlcanalyser.io.RhapsodyVersion.VersionNumber;
import com.orchestral.rhapsody.rlcanalyser.io.Route;
import com.orchestral.rhapsody.rlcanalyser.io.RouteProperties;
import com.orchestral.rhapsody.rlcanalyser.io.SharedJSFunction;
import com.orchestral.rhapsody.rlcanalyser.io.SharedJSLib;
import com.orchestral.rhapsody.rlcanalyser.io.TrackingScheme;
import com.orchestral.rhapsody.rlcanalyser.io.Variable;
import com.orchestral.rhapsody.rlcanalyser.io.WebService;
import com.orchestral.rhapsody.rlcanalyser.store.ConfigurationDataStore;
import com.orchestral.rhapsody.rlcanalyser.store.RLCDataStore;
import com.orchestral.rhapsody.rlcanalyser.store.RLCDataStore.TotalCountDataType;

/**
 *
 */
public class RLCDataCollector {
	// The dataStoreMap is a large data structure, that is held in memory the whole time
	// the input is being processed. This means that large input sizes (magnitude of GB)
	// are not guaranteed to finish or be exported.
	private final Map<String, RLCDataStore> dataStoreMap;

	private final RLCDataStore overallDataStore;
	private RLCDataStore currentDataStore;
	private final ConfigurationDataStore configurationDataStore;

	/**
	 * The constructor initialises the overall data store (Aggregated view) and
	 * a map of individual current data stores, each of which correspond to a
	 * single RLC file (or engine).
	 *
	 * The DataStore Map consists of the (unique) file name of the Engine as the
	 * key and it's corresponding RLCDataStore is the value.
	 *
	 * @param dataStore is the overallDataStore - to be able to provide an
	 *            aggregated view of all RLCs.
	 * @param configurationDataStore - the overall config store.
	 */
	public RLCDataCollector(final RLCDataStore dataStore, final ConfigurationDataStore configurationDataStore) {
		this.overallDataStore = dataStore;
		this.dataStoreMap = new HashMap<String, RLCDataStore>();
		this.configurationDataStore = configurationDataStore;
		final RhapsodyVersion version = new RhapsodyVersion();
		version.setMaxVersion(VersionNumber.Rhapsody4_0);
		version.setMinVersion(VersionNumber.RhapsodyLatest);
		this.overallDataStore.setRhapsodyVersion(version);
	}

	/**
	 * collectData calls the collector methods for each type of component. E.g.
	 * Lockers, Communication Points, Definitions etc.
	 *
	 * @param engine
	 */
	public void collectData(final Engine engine) {
		this.currentDataStore = new RLCDataStore();

		this.dataStoreMap.put(engine.getFileName(), this.currentDataStore);

		final List<Locker> lockers = engine.getLockers();
		if (lockers != null && !lockers.isEmpty()) {
			collectLockerData(lockers);
		}
		final List<CommunicationPoint> communicationPoints = engine.getCommunicationPoints();
		if (communicationPoints != null && !communicationPoints.isEmpty()) {
			collectCommunicationPointData(communicationPoints);
		}
		final List<Route> routes = engine.getRoutes();
		if (routes != null && !routes.isEmpty()) {
			collectRouteData(routes);
		}
		final List<Definition> definitions = engine.getDefinitions();
		if (definitions != null && !definitions.isEmpty()) {
			collectDefinitionData(definitions);
		}
		final List<WebService> webServices = engine.getWebServices();
		if (webServices != null && !webServices.isEmpty()) {
			collectWebServicesData(webServices);
		}
		final List<Variable> variables = engine.getVariables();
		if (variables != null && !variables.isEmpty()) {
			collectVariableData(variables);
		}
		final List<TrackingScheme> trackingSchemes = engine.getTrackingSchemes();
		if (trackingSchemes != null && !trackingSchemes.isEmpty()) {
			collectTrackingSchemeData(trackingSchemes);
		}
		final List<LookupTable> lookupTables = engine.getLookupTables();
		if (lookupTables != null && !lookupTables.isEmpty()) {
			collectLookupTableData(lookupTables);
		}
		final List<RestClient> restClients = engine.getRestClients();
		if (restClients != null && !restClients.isEmpty()) {
			collectRestClientData(restClients);
		}
		final List<SharedJSLib> jsLibs = engine.getSharedJSLibs();
		if (jsLibs != null && !jsLibs.isEmpty()) {
			collectSharedJSLibData(jsLibs);
		}
		final List<SharedJSFunction> jsFunctions = engine.getSharedJSFunctions();
		if (jsFunctions != null && !jsFunctions.isEmpty()) {
			collectSharedJSFunctionData(jsFunctions);
		}
		setRhapsodyVersion(engine);
	}

	/**
	 * @param lockers
	 */
	private void collectLockerData(final List<Locker> lockers) {
		this.currentDataStore.incrementTotalCounts(TotalCountDataType.Lockers, lockers.size());
		this.overallDataStore.incrementTotalCounts(TotalCountDataType.Lockers, lockers.size());
	}

	/**
	 * @param jsFunctions
	 */
	private void collectSharedJSFunctionData(final List<SharedJSFunction> jsFunctions) {
		this.currentDataStore.incrementTotalCounts(TotalCountDataType.SharedJSFunctions, jsFunctions.size());
		this.overallDataStore.incrementTotalCounts(TotalCountDataType.SharedJSFunctions, jsFunctions.size());
	}

	/**
	 * @param jsLibs
	 */
	private void collectSharedJSLibData(final List<SharedJSLib> jsLibs) {
		this.currentDataStore.incrementTotalCounts(TotalCountDataType.SharedJSLibraries, jsLibs.size());
		this.overallDataStore.incrementTotalCounts(TotalCountDataType.SharedJSLibraries, jsLibs.size());
	}

	/**
	 * @param restClients
	 */
	private void collectRestClientData(final List<RestClient> restClients) {
		this.currentDataStore.incrementTotalCounts(TotalCountDataType.RESTClients, restClients.size());
		this.overallDataStore.incrementTotalCounts(TotalCountDataType.RESTClients, restClients.size());
	}

	/**
	 * @param lookupTables
	 */
	private void collectLookupTableData(final List<LookupTable> lookupTables) {
		this.currentDataStore.incrementTotalCounts(TotalCountDataType.LookupTables, lookupTables.size());
		this.overallDataStore.incrementTotalCounts(TotalCountDataType.LookupTables, lookupTables.size());
	}

	/**
	 * @param trackingSchemes
	 */
	private void collectTrackingSchemeData(final List<TrackingScheme> trackingSchemes) {
		this.currentDataStore.incrementTotalCounts(TotalCountDataType.MessageTrackingSchemes, trackingSchemes.size());
		this.overallDataStore.incrementTotalCounts(TotalCountDataType.MessageTrackingSchemes, trackingSchemes.size());
	}

	private void collectVariableData(final List<Variable> variables) {
		this.currentDataStore.incrementTotalCounts(TotalCountDataType.Variables, variables.size());
		this.overallDataStore.incrementTotalCounts(TotalCountDataType.Variables, variables.size());
	}

	/**
	 * @param webServices
	 */
	private void collectWebServicesData(final List<WebService> webServices) {
		this.currentDataStore.incrementTotalCounts(TotalCountDataType.WebServices, webServices.size());
		this.overallDataStore.incrementTotalCounts(TotalCountDataType.WebServices, webServices.size());
	}

	/**
	 * Setting the version number for the current data store and updating the
	 * min and max version number for the overall data store.
	 *
	 * @param engine
	 */
	private void setRhapsodyVersion(final Engine engine) {
		this.currentDataStore.setRhapsodyVersion(rhapsodyVersionGenerator(engine));

		this.overallDataStore.calculateNewMinVersion(this.currentDataStore.getRhapsodyVersion().getMinVersion());
		this.overallDataStore.calculateNewMaxVersion(this.currentDataStore.getRhapsodyVersion().getMaxVersion());
	}
	/**
	 * @param definitions
	 */
	private void collectDefinitionData(final List<Definition> definitions) {
		// create a list of (two) data stores and iterate through it to collect data.
		final List<RLCDataStore> dataStores = Arrays.asList(this.currentDataStore, this.overallDataStore);
		for (final RLCDataStore store : dataStores) {
			for (final Definition definition : definitions) {
				final DefinitionType type = definition.getType();
				store.addDefinitionTypeCounts(type);
			}
			store.incrementTotalCounts(TotalCountDataType.Definitions, definitions.size());
		}

	}

	private RhapsodyVersion rhapsodyVersionGenerator(final Engine engine) {
		final RhapsodyVersion versionRange = new RhapsodyVersion();
		versionRange.setMaxVersion(VersionNumber.calculateMaxVersionNumber(engine));
		versionRange.setMinVersion(VersionNumber.calculateMinVersionNumber(engine));
		return versionRange;
	}

	private void collectCommunicationPointData(final List<CommunicationPoint> communicationPoints) {
		final List<RLCDataStore> dataStores = Arrays.asList(this.overallDataStore, this.currentDataStore);
		for (final RLCDataStore store : dataStores) {
			for (final CommunicationPoint communicationPoint : communicationPoints) {
				final String type = communicationPoint.getType();
				// add comm point type to total type counts
				store.addCommunicationPointTypeCounts(type);
				// collect general properties about the comm point:
				store.retrieveGeneralProperties(communicationPoint);

				final List<String> inputRouteIds = communicationPoint.getInputRouteIds();
				if (inputRouteIds != null) {
					store.addInputCommunicationPointTypeCounts(type, inputRouteIds.size());
				}

				final List<String> outputRouteIds = communicationPoint.getOutputRouteIds();
				if (outputRouteIds != null) {
					store.addOutputCommunicationPointTypeCounts(type, outputRouteIds.size());
				}
				if (store != null && store.equals(this.overallDataStore)) {
					this.configurationDataStore.collectCommunicationPointConfigurationData(communicationPoint);
				}
			}
			store.incrementTotalCounts(TotalCountDataType.CommunicationPoints, communicationPoints.size());
		}
	}

	private void collectRouteData(final List<Route> routes) {
		final List<RLCDataStore> dataStores = Arrays.asList(this.overallDataStore, this.currentDataStore);
		for (final RLCDataStore store : dataStores) {
			for (final Route route : routes) {
				final List<Filter> filters = route.getFilters();
				if (filters != null) {
					store.addFilterCountsPerRoute(filters.size());
					for (final Filter filter : filters) {
						store.addFilterTypeCounts(filter.getType());
						if (this.configurationDataStore != null && store.equals(this.overallDataStore)) {
							this.configurationDataStore.collectFilterConfigurationData(filter);
						}
					}
				}
				// adding definitions for the route.
				final RouteProperties definitions = route.getRoutePropertyDefinitions();
				if (definitions != null && definitions.getRouteProperties() != null && definitions.getRouteProperties().getPropertyList() != null) {
					final int definitionCount = definitions.getRouteProperties().getPropertyList().size();
					store.addDefinitionCountsPerRoute(definitionCount);
				}
			}
			store.incrementTotalCounts(TotalCountDataType.Routes, routes.size());
		}
	}

	/**
	 * Method is used to retrieve the dataStoreMap.
	 *
	 * @return null if the map hasn't been initialised or is empty.
	 */
	public Map<String, RLCDataStore> getDataStoreMap() {
		if (this.dataStoreMap != null & !this.dataStoreMap.isEmpty()) {
			return this.dataStoreMap;
		}
		return null;
	}
}
