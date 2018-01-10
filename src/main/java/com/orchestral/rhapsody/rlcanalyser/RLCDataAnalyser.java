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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.orchestral.rhapsody.rlcanalyser.store.GeneralTabType;
import com.orchestral.rhapsody.rlcanalyser.store.RLCDataStore;

/**
 *
 */
public class RLCDataAnalyser {

	private final RLCDataStore dataStore;

	/**
	 * @param dataStore
	 */
	public RLCDataAnalyser(final RLCDataStore dataStore) {
		super();
		this.dataStore = dataStore;
	}

	/**
	 * Gets the most commonly used comm points.
	 * 
	 * @param maxResult if -1, then all possible communication points will be
	 *            shown.
	 * @return
	 */
	public List<TypeCountData> getMostUsedCommunicationPoints(final int maxResult) {
		return getCommPointProperties(this.dataStore.getCommunicationPointGeneralProperties(), maxResult);
	}

	public long getNumberOfCommunicationPoints() {
		return getNumberOfComponents(this.dataStore.getCommunicationPointTypeCounts());
	}

	public List<TypeCountData> getMostUsedInputCommunicationPoints(final int maxResult) {
		return getMostUsed(this.dataStore.getInputCommunicationPointTypeCounts(), maxResult);
	}

	public long getNumberOfInputCommunicationPoints() {
		return getNumberOfComponents(this.dataStore.getInputCommunicationPointTypeCounts());
	}

	public List<TypeCountData> getMostUsedOutputCommunicationPoints(final int maxResult) {
		return getMostUsed(this.dataStore.getOutputCommunicationPointTypeCounts(), maxResult);
	}

	public long getNumberOfOutputCommunicationPoints() {
		return getNumberOfComponents(this.dataStore.getOutputCommunicationPointTypeCounts());
	}

	public List<TypeCountData> getMostUsedFilters(final int maxResult) {
		return getMostUsed(this.dataStore.getFilterTypeCounts(), maxResult);
	}

	public List<TypeCountData> getMostUsedDefinitionTypes(final int maxResult) {
		return getMostUsed(this.dataStore.getDefinitionTypeCounts(), maxResult);
	}

	public int[] getFilterCountsPerRoute() {
		return this.dataStore.getFilterCountsPerRoute();
	}

	public int[] getDefinitionCountsPerRoute() {
		return this.dataStore.getDefinitionCountsPerRoute();
	}

	public long getNumberOfFilters() {
		final int[] counts = this.dataStore.getFilterCountsPerRoute();
		long total = 0;
		for (int i=0; i<counts.length; i++) {
			total += i * counts[i];
		}
		return total;
	}

	public long getNumberOfRoutes() {
		final int[] counts = this.dataStore.getFilterCountsPerRoute();
		long total = 0;
		for (int i=0; i<counts.length; i++) {
			total += counts[i];
		}
		return total;
	}

	private List<TypeCountData> getMostUsed(final Map<String, TypeCountData> countMap,
	                                        final int maxResult) {
		final List<TypeCountData> countList = new ArrayList<TypeCountData>(countMap.values());
		Collections.sort(countList);
		if (maxResult > 0 && maxResult < countList.size()) {
			return countList.subList(0, maxResult);
		}
		return countList;
	}

	/**
	 * Method is used get most used CommPoint given an input general properties
	 * type map.
	 *
	 * @param countMap
	 * @param maxResult
	 * @return
	 */
	private List<TypeCountData> getCommPointProperties(final Map<String, Map<GeneralTabType, TypeCountData>> countMap, final int maxResult) {
		final List<TypeCountData> countList = new ArrayList<TypeCountData>();
		// if max result is -1, that means that we don't wish to apply the max result parameter.
		for (final Map<GeneralTabType, TypeCountData> value : countMap.values()) {
			if (maxResult == -1 || maxResult > countList.size()) {
				countList.add(value.get(GeneralTabType.TotalCounts));
			} else {
				break; // stop adding values to map.
			}
		}

		Collections.sort(countList);
		return countList;
	}

	private long getNumberOfComponents(final Map<String, TypeCountData> countMap) {
		long total = 0;
		for (final TypeCountData countData : countMap.values()) {
			total += countData.getCounts();
		}
		return total;
	}
}
