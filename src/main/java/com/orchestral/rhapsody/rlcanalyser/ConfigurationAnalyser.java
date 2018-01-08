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
 */
package com.orchestral.rhapsody.rlcanalyser;

import java.util.Collections;
import java.util.List;

import com.orchestral.rhapsody.rlcanalyser.store.ComponentConfigurationDataStore;
import com.orchestral.rhapsody.rlcanalyser.store.ConfigurationDataStore;

public class ConfigurationAnalyser {

	private final ConfigurationDataStore dataStore;

	public ConfigurationAnalyser(final ConfigurationDataStore dataStore) {
		this.dataStore = dataStore;
	}

	public List<ModifiedPropertyCountData> getModifiedCommunicationPointPropertyCounts(final String type, final int maxResult) {
		final ComponentConfigurationDataStore communicationPointDataStore = this.dataStore.getCommunicationPointConfigurationDataStore(type);
		if (communicationPointDataStore == null) {
			return Collections.emptyList();
		}
		final List<ModifiedPropertyCountData> counts = communicationPointDataStore.getModifedPropertyCounts(maxResult);
		if (counts == null) {
			return Collections.emptyList();
		}
		return counts;
	}

	public List<ModifiedPropertyCountData> getModifiedFilterPropertyCounts(final String type, final int maxResult) {
		final ComponentConfigurationDataStore filterDataStore = this.dataStore.getFilterConfigurationDataStore(type);
		if (filterDataStore == null) {
			return Collections.emptyList();
		}
		final List<ModifiedPropertyCountData> counts = filterDataStore.getModifedPropertyCounts(maxResult);
		if (counts == null) {
			return Collections.emptyList();
		}
		return counts;
	}

	public List<String> getAllCommunicationPointTypes() {
		return this.dataStore.getAllCommunicationPointTypes();
	}

	public List<String> getAllFilterTypes() {
		return this.dataStore.getAllFilterTypes();
	}

}
