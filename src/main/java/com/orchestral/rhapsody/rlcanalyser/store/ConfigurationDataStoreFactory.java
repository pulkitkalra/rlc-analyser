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
import java.util.List;

import com.orchestral.rhapsody.rlcanalyser.configuration.ConfigurationPropertyDefinition;
import com.orchestral.rhapsody.rlcanalyser.store.CommunicationPointConfigurationProvider.CommunicationPointType;
import com.orchestral.rhapsody.rlcanalyser.store.FilterConfigurationProvider.FilterType;

public class ConfigurationDataStoreFactory {
	
	private static final ConfigurationDataStoreFactory ONLY_ME = new ConfigurationDataStoreFactory();
	
	private ConfigurationDataStoreFactory() {
	}
	
	public static ConfigurationDataStoreFactory get() {
		return ONLY_ME;
	}
	
	public CommunicationPointConfigurationDataStore createCommunicationPointConfigurationDataStore(final String type) {
		return new CommunicationPointConfigurationDataStore(type, createConfigurationProperties(type));
	}
	
	/**
	 * Returns all available communication point configuration data stores
	 * @return
	 */
	public List<CommunicationPointConfigurationDataStore> createAllCommunicationPointStores() {
		final List<CommunicationPointConfigurationDataStore> list = new ArrayList<CommunicationPointConfigurationDataStore>();
		for (final CommunicationPointType communicationPointType : CommunicationPointConfigurationProvider.getAllCommunicationPointTypes()) {
			list.add( new CommunicationPointConfigurationDataStore(communicationPointType.getType(), 
					createConfigurationProperties(communicationPointType.getPropertyDefinitions())));
		}
		return list;
	}
	
	/**
	 * Returns all available filter configuration data stores
	 * @return
	 */
	public List<FilterConfigurationDataStore> createAllFilterStores() {
		final List<FilterConfigurationDataStore> list = new ArrayList<FilterConfigurationDataStore>();
		for (final FilterType filterType : FilterConfigurationProvider.getAllFilterTypes()) {
			list.add( new FilterConfigurationDataStore(filterType.getType(), 
					createConfigurationProperties(filterType.getPropertyDefinitions())));
		}
		return list;
	}
	
	private List<ConfigurationPropertyDefinition> createConfigurationProperties(final String type) {
		final CommunicationPointType communicationPointType = CommunicationPointType.getCommunicationPointType(type);
		return createConfigurationProperties(communicationPointType.getPropertyDefinitions());
	}
	
	private List<ConfigurationPropertyDefinition> createConfigurationProperties(final String[] definitions) {
		final List<ConfigurationPropertyDefinition> defns = new ArrayList<ConfigurationPropertyDefinition>();
		for (final String propertyDefinition : definitions) {
			defns.add(parsePropertyDefinition(propertyDefinition));
		}
		return defns;
	}
	
	private ConfigurationPropertyDefinition parsePropertyDefinition(final String propertyDefintion) {
		assert propertyDefintion != null && !propertyDefintion.isEmpty();

		final String[] tokens = propertyDefintion.split("\\|", -1); //$NON-NLS-1$
		assert tokens.length == 3;
		return new ConfigurationPropertyDefinition(tokens[0], tokens[1], tokens[2]);
	}
}
