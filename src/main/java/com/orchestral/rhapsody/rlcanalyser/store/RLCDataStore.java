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
import com.orchestral.rhapsody.rlcanalyser.io.Definition.DefinitionType;
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
		addCounts(this.communicationPointTypeCounts, correctType, 1);
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

