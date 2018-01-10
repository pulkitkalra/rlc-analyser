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
package com.orchestral.rhapsody.rlcanalyser.store;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.orchestral.rhapsody.rlcanalyser.TypeCountData;
import com.orchestral.rhapsody.rlcanalyser.io.CommunicationPoint;
import com.orchestral.rhapsody.rlcanalyser.io.Definition.DefinitionType;
import com.orchestral.rhapsody.rlcanalyser.io.Property;
import com.orchestral.rhapsody.rlcanalyser.io.RhapsodyVersion;
import com.orchestral.rhapsody.rlcanalyser.io.RhapsodyVersion.VersionNumber;

/**
 *
 */
public class RLCDataStore {
	public enum TotalCountDataType {
		Lockers,
		Folders,
		CommunicationPoints,
		Routes,
		Filters,
		WebServices,
		RESTClients,
		Definitions,
		MessageTrackingSchemes,
		LookupTables,
		Variables,
		SharedJSLibraries,
		SharedJSFunctions
	}

	private final Map<TotalCountDataType, Long> totalCounts = Collections.synchronizedMap(new HashMap<TotalCountDataType, Long>());

	/**
	 * Stores number of communication points per each different types
	 */
	private final Map<String, TypeCountData> communicationPointTypeCounts = Collections.synchronizedMap(new HashMap<String, TypeCountData>());

	/**
	 * Store number of general tab drop down occurrences per comm point. The key
	 * of the map is the type of the comm point and the value is a map of Enums
	 * representing a general tab property and the corresponding counts.
	 */
	private final Map<String, Map<GeneralTabType, TypeCountData>> communicationPointGeneralTabTypeCounts = Collections
			.synchronizedMap(new HashMap<String, Map<GeneralTabType, TypeCountData>>());

	/**
	 * Stores number of input communication points per each different types
	 */
	private final Map<String, TypeCountData> inputCommunicationPointTypeCounts = Collections.synchronizedMap(new HashMap<String, TypeCountData>());

	/**
	 * Stores number of output communication points per each different types
	 */
	private final Map<String, TypeCountData> outputCommunicationPointTypeCounts = Collections.synchronizedMap(new HashMap<String, TypeCountData>());

	/**
	 * Stores number of filters per each different types
	 */
	private final Map<String, TypeCountData> filterTypeCounts = Collections.synchronizedMap(new HashMap<String, TypeCountData>());

	/**
	 * Stores number of definition per each different types
	 */
	private final Map<String, TypeCountData> definitionTypeCounts = Collections.synchronizedMap(new HashMap<String, TypeCountData>());

	/**
	 * Stores number of filters per route
	 *
	 * If a route contains n filter, the value of n-th entry will be added
	 *
	 * If a route contains equal or more than 30, the value of 30th entry will be added
	 */
	private final int[] filterCountsPerRoute = new int[31];

	/**
	 * Stores number of definitions per route
	 *
	 * If a route contains n definitions, the value of n-th entry will be added
	 *
	 * If a route contains equal or more than 10, the value of 10th entry will
	 * be added. The last index of the array stores any number of routes with 10
	 * or more definitions per route.
	 */
	private final int[] definitionCountsPerRoute = new int[11];

	private RhapsodyVersion rhapsodyVersion;

	public RLCDataStore() {
		init();
	}

	private void init() {
		for (int i=0; i<this.filterCountsPerRoute.length; i++) {
			this.filterCountsPerRoute[i] = 0;
		}
	}

	/**
	 * This method will take the minimum version number and store that in the
	 * Rhapsody Version number that belongs to the overall datastore.
	 *
	 * @param version
	 */
	public void calculateNewMinVersion(final VersionNumber version) {
		this.rhapsodyVersion.setMinVersion(version.compareTo(this.rhapsodyVersion.getMinVersion()) < 0 ? version
				: this.rhapsodyVersion.getMinVersion());
	}

	/**
	 * This method will the maximum version number nad store that in the
	 * Rhapsody Version number that belongs to the overall datastore.
	 *
	 * @param version
	 */
	public void calculateNewMaxVersion(final VersionNumber version) {
		this.rhapsodyVersion.setMaxVersion(version.compareTo(this.rhapsodyVersion.getMaxVersion()) > 0 ? version
				: this.rhapsodyVersion.getMaxVersion());
	}

	/**
	 * Method for retrieving the Rhapsody Version that belongs to the datastore
	 *
	 * @return
	 */
	public RhapsodyVersion getRhapsodyVersion() {
		return this.rhapsodyVersion;
	}

	/**
	 * Method for setting the new Rhapsody Version number
	 *
	 * @param rhapsodyVersion
	 */
	public void setRhapsodyVersion(final RhapsodyVersion rhapsodyVersion) {
		this.rhapsodyVersion = rhapsodyVersion;
	}

	public void addCommunicationPointTypeCounts(final String type) {
		// get correct type
		final String correctType = RLCNameConverter.getCorrectComponentTypeName(type);
		addCountsCommunicationPoint(this.communicationPointGeneralTabTypeCounts, correctType, 1);
	}

	public void addInputCommunicationPointTypeCounts(final String type, final int count) {
		// get correct type
		final String correctType = RLCNameConverter.getCorrectComponentTypeName(type);
		addCounts(this.inputCommunicationPointTypeCounts, correctType, count);
	}

	public void addOutputCommunicationPointTypeCounts(final String type, final int count) {
		// get correct type
		final String correctType = RLCNameConverter.getCorrectComponentTypeName(type);
		addCounts(this.outputCommunicationPointTypeCounts, correctType, count);
	}

	public void addFilterTypeCounts(final String type) {
		final String correctType = RLCNameConverter.getCorrectComponentTypeName(type);
		addCounts(this.filterTypeCounts, correctType, 1);
	}

	public void addDefinitionTypeCounts(final DefinitionType type) {
		final String correctType = RLCNameConverter.getCorrectComponentTypeName(type.name());
		addCounts(this.definitionTypeCounts, correctType, 1);
	}

	public void addFilterCountsPerRoute(final int filterCounts) {
		if (filterCounts >= 30) {
			this.filterCountsPerRoute[30]++;
		} else {
			this.filterCountsPerRoute[filterCounts]++;
		}
	}

	/**
	 * Method is used to add to the definition counts array - the number of
	 * definitions per route. The last index of the array is used to store any
	 * number of counts greater than the size of the array (10 at present).
	 *
	 * @param definitionCounts number of definitions found in a route.
	 */
	public void addDefinitionCountsPerRoute(final int definitionCounts) {
		final int definitionArrLength = this.definitionCountsPerRoute.length - 1;
		if (definitionCounts >= definitionArrLength) {
			this.definitionCountsPerRoute[definitionArrLength]++;
		} else {
			this.definitionCountsPerRoute[definitionCounts]++;
		}
	}

	private void addCounts(final Map<String, TypeCountData> countMap, final String type, final int count) {
		if (type == null) {
			return;
		}
		TypeCountData countData = countMap.get(type);
		if (countData == null) {
			countData = new TypeCountData(type);
		}
		countData.setCounts(countData.getCounts()+count);
		countMap.put(type, countData);
	}

	/**
	 * Method is used to add counts for the total number of comm point given an
	 * input of the general properties map.
	 * 
	 * @param countMap
	 * @param type
	 * @param count
	 */
	private void addCountsCommunicationPoint(final Map<String, Map<GeneralTabType, TypeCountData>> countMap, final String type, final int count) {
		if (type == null) {
			return;
		}
		// Adding total counts for each comm point type
		Map<GeneralTabType, TypeCountData> countDataMap = countMap.get(type);

		if (countDataMap == null) {
			countDataMap = new HashMap<GeneralTabType, TypeCountData>();
			countDataMap.put(GeneralTabType.TotalCounts, new TypeCountData(type));
		}

		TypeCountData countData = countDataMap.get(GeneralTabType.TotalCounts);
		if (countData == null) {
			countData = new TypeCountData(type);
		}
		countData.setCounts(countData.getCounts() + count);
		countDataMap.put(GeneralTabType.TotalCounts, countData);
		countMap.put(type, countDataMap);
	}

	/**
	 * Method is called to retrieve and count general tab property information
	 * for each comm point type. general properties for each comm point type.
	 *
	 * @param communicationPoint The CommPoint for which general tab information
	 *            is required to be collected.
	 * @param type Type for the comm point, must be the correct comm point type.
	 */
	public void retrieveGeneralProperties(final CommunicationPoint communicationPoint) {
		final String type = RLCNameConverter.getCorrectComponentTypeName(communicationPoint.getType());
		if (this.communicationPointGeneralTabTypeCounts.containsKey(type)) {
			final Map<GeneralTabType, TypeCountData> generalPropertiesMap = this.communicationPointGeneralTabTypeCounts.get(type);

			String startUpState = "";
			/* Get startup mode from general properties: */
			for (final Property property : communicationPoint.getGeneralProperties()) {
				if (property.getName().equals("StartupState")) {
					startUpState = property.getValue();
					break;
				}
			}

			if (!startUpState.isEmpty()) {
				updateGeneralTabTypeMap(generalPropertiesMap, startUpState, communicationPoint);
			}

			/* Getting start up state */
			updateGeneralTabTypeMap(generalPropertiesMap, communicationPoint.getMode(), communicationPoint);

			/* Getting Retry type */
			updateGeneralTabTypeMap(generalPropertiesMap, communicationPoint.getRetryType(), communicationPoint);
			// update overall map.
			this.communicationPointGeneralTabTypeCounts.put(type, generalPropertiesMap);
		}
	}

	/**
	 * Helper method used to update the general properties map.
	 *
	 * @param generalPropertiesMap Map containing all the general properties for
	 *            a given comm point type.
	 * @param tabTypeEnum the string representation of the enum representing the
	 *            property we register in the map and count.
	 * @param communicationPoint The communication point obbject from which the
	 *            properties will be extracted.
	 */
	private void updateGeneralTabTypeMap(	final Map<GeneralTabType, TypeCountData> generalPropertiesMap,
	                                     	final String tabTypeEnum,
	                                     	final CommunicationPoint communicationPoint) {
		// get enum based on start up state type defined in the comm point general properties
		final GeneralTabType tabType = GeneralTabType.valueOf(tabTypeEnum);
		// setup key in map if it doesn't already exist.
		if (generalPropertiesMap.get(tabType) == null) {
			final TypeCountData data = new TypeCountData(communicationPoint.getMode());
			generalPropertiesMap.put(tabType, data);
		}
		// update the map counts to reflect that another starup state was detected.
		final TypeCountData updatedData = generalPropertiesMap.get(tabType);
		updatedData.setCounts(updatedData.getCounts() + 1);
		generalPropertiesMap.put(tabType, updatedData);
	}

	public synchronized void incrementTotalCounts(final TotalCountDataType type, final long increment) {
		final Long currentCount = this.totalCounts.get(type);
		if (currentCount != null) {
			this.totalCounts.put(type, currentCount + increment);
		} else {
			this.totalCounts.put(type, increment);
		}
	}

	/**
	 * @return the communicationPointTypeCounts
	 */
	public Map<String, TypeCountData> getCommunicationPointTypeCounts() {
		return this.communicationPointTypeCounts;
	}

	/**
	 * Returns the General Properties map. The key of the map is the type of
	 * commpoint and the values are general tab dropdowns.
	 *
	 * @return
	 */
	public Map<String, Map<GeneralTabType, TypeCountData>> getCommunicationPointGeneralProperties() {
		return this.communicationPointGeneralTabTypeCounts;
	}

	/**
	 * @return the inputCommunicationPointTypeCounts
	 */
	public Map<String, TypeCountData> getInputCommunicationPointTypeCounts() {
		return this.inputCommunicationPointTypeCounts;
	}

	/**
	 * @return the outputCommunicationPointTypeCounts
	 */
	public Map<String, TypeCountData> getOutputCommunicationPointTypeCounts() {
		return this.outputCommunicationPointTypeCounts;
	}

	/**
	 * @return the filterTypeCounts
	 */
	public Map<String, TypeCountData> getFilterTypeCounts() {
		return this.filterTypeCounts;
	}

	/**
	 * @return the filterCountsPerRoute
	 */
	public int[] getFilterCountsPerRoute() {
		return this.filterCountsPerRoute;
	}

	/**
	 * @return the number of definitions per route.
	 */
	public int[] getDefinitionCountsPerRoute() {
		return this.definitionCountsPerRoute;
	}

	public long getTotalCounts(final TotalCountDataType type) {
		return this.totalCounts.containsKey(type) ? this.totalCounts.get(type) : 0L;
	}

	public Map<String, TypeCountData> getDefinitionTypeCounts() {
		return this.definitionTypeCounts;
	}
}

