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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.orchestral.rhapsody.rlcanalyser.io.CommunicationPoint;
import com.orchestral.rhapsody.rlcanalyser.io.Filter;
import com.orchestral.rhapsody.rlcanalyser.io.Property;


public class ConfigurationDataStore {

	private final List<String> communicationPointTypes;

	private final List<String> filterTypes;


	private final Map<String, ComponentConfigurationDataStore> communicationPointConfigurationDataStores = Collections
			.synchronizedMap(new HashMap<String, ComponentConfigurationDataStore>());

	private final Map<String, ComponentConfigurationDataStore> filterConfigurationDataStores = Collections
			.synchronizedMap(new HashMap<String, ComponentConfigurationDataStore>());

	public ConfigurationDataStore(
			final List<CommunicationPointConfigurationDataStore> communicationPointConfigurationStores,
			final List<FilterConfigurationDataStore> filterConfigurationStores) {
		this.communicationPointTypes = new ArrayList<String>();
		for (final ComponentConfigurationDataStore configurationDataStore : communicationPointConfigurationStores) {
			final String type = configurationDataStore.getType();
			this.communicationPointConfigurationDataStores.put(type, configurationDataStore);
			this.communicationPointTypes.add(type);
		}

		this.filterTypes = new ArrayList<String>();
		for (final ComponentConfigurationDataStore configurationDataStore : filterConfigurationStores) {
			final String type = configurationDataStore.getType();
			this.filterConfigurationDataStores.put(type, configurationDataStore);
			this.filterTypes.add(type);
		}

	}

	public List<String> getAllCommunicationPointTypes() {
		return this.communicationPointTypes;
	}

	public List<String> getAllFilterTypes() {
		return this.filterTypes;
	}

	public ComponentConfigurationDataStore getCommunicationPointConfigurationDataStore(final String type) {
		return this.communicationPointConfigurationDataStores.get(type);
	}

	public ComponentConfigurationDataStore getFilterConfigurationDataStore(final String type) {
		return this.filterConfigurationDataStores.get(type);
	}

	public void collectCommunicationPointConfigurationData(final CommunicationPoint communicationPoint) {
		// get correct comm point name for configuration.
		final String type = RLCNameConverter.getCorrectComponentTypeName(communicationPoint.getType());
		if (this.communicationPointConfigurationDataStores.containsKey(type)) {
			final ComponentConfigurationDataStore configurationDataStore = this.communicationPointConfigurationDataStores.get(type);

			final List<Property> properties = communicationPoint.getConfiguration();
			if (properties != null) {
				for (final Property property : properties) {
					configurationDataStore.addPropertyValue(property.getName(), property.getValue());
				}
			}
		}
	}

	public void collectFilterConfigurationData(final Filter filter) {
		final String type = RLCNameConverter.getCorrectComponentTypeName(filter.getType());
		if (this.filterConfigurationDataStores.containsKey(type)) {
			final ComponentConfigurationDataStore configurationDataStore = this.filterConfigurationDataStores.get(type);

			final List<Property> properties = filter.getConfiguration();
			if (properties != null) {
				for (final Property property : properties) {
					configurationDataStore.addPropertyValue(property.getName(), property.getValue());
				}
			}
		}
	}
}
