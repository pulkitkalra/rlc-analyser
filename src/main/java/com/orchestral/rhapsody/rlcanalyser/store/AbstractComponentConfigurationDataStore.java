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

import org.apache.log4j.Logger;

import com.orchestral.rhapsody.rlcanalyser.ModifiedPropertyCountData;
import com.orchestral.rhapsody.rlcanalyser.configuration.ConfigurationPropertyDefinition;

public abstract class AbstractComponentConfigurationDataStore implements ComponentConfigurationDataStore {

	private final static Logger logger = Logger.getLogger(AbstractComponentConfigurationDataStore.class);

	private final String type;

	private final List<ConfigurationPropertyDefinition> properties;

	private final Map<String, ConfigurationPropertyDefinition> propertyDefinitions = Collections.synchronizedMap(
	                                                                                                             new HashMap<String, ConfigurationPropertyDefinition>());

	private final Map<String, ModifiedPropertyCountData> countMap = Collections.synchronizedMap(
	                                                                                            new HashMap<String, ModifiedPropertyCountData>());

	public AbstractComponentConfigurationDataStore(final String type,
	                                               final List<ConfigurationPropertyDefinition> properties) {
		assert type != null;
		assert properties != null;

		this.type = type;
		this.properties = properties;
		for (final ConfigurationPropertyDefinition propertyDefn : properties) {
			this.propertyDefinitions.put(propertyDefn.getName(), propertyDefn);
		}
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public List<ModifiedPropertyCountData> getModifedPropertyCounts(final int maxResult) {
		final List<ModifiedPropertyCountData> countList = new ArrayList<ModifiedPropertyCountData>(this.countMap.values());
		Collections.sort(countList);
		if (maxResult > 0 && maxResult < countList.size()) {
			return countList.subList(0, maxResult);
		}
		return countList;
	}

	@Override
	public void addPropertyValue(final String propertyName, final String propertyValue) {
		final ModifiedPropertyCountData propertyCount = this.countMap.containsKey(propertyName) ? this.countMap.get(propertyName)
				: new ModifiedPropertyCountData(propertyName);
		final ConfigurationPropertyDefinition propertyDefinition = this.propertyDefinitions.get(propertyName);
		if (propertyDefinition == null) {
			logger.info("[WARNING] " + this.type + ": property definition for '" + propertyName + "' not found");
			return;
		}
		if (isValueModified(propertyDefinition, propertyValue)) {
			propertyCount.increaseNumberOfModified();
		}
		propertyCount.increaseNumberOfOccurrencies();
		this.countMap.put(propertyName, propertyCount);
	}

	private boolean isValueModified(final ConfigurationPropertyDefinition propertyDefinition, final String propertyValue) {
		final String defaultValue = propertyDefinition.getDefaultValue();
		if (propertyValue == null || propertyValue.isEmpty()) {
			return defaultValue == null || defaultValue.isEmpty();
		}
		return !propertyValue.equalsIgnoreCase(defaultValue);
	}


}
