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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.orchestral.rhapsody.rlcanalyser.store.CommunicationPointConfigurationProvider.CommunicationPointType;
import com.orchestral.rhapsody.rlcanalyser.store.FilterConfigurationProvider.FilterType;

/**
 * The Name Converter class contains a Lookup HashMap that contains mappings for
 * the names of components in the RLC file and their corresponding names in the
 * IDE. This class is used to swap out these names so that the Analyser shows
 * the components as a Rhapsody IDE user would expect to be named.
 */
public class RLCNameConverter {
	/**
	 * This method is used to initialize the map which contains the mapping of
	 * names found in the RLC which must be replaced with how components are
	 * named in the IDE.
	 *
	 * Modify the map in this method to add/ change any names that have changed
	 * in the IDE but appear different in the RLC file.
	 *
	 * If the component type being changed is one that has configurations
	 * pre-defined in either the CommPointConfigurationProvider or
	 * FilterConfigurationProvider, then it would be sufficient to change the
	 * enums there.
	 */
	private static final Map<String, String> correctNameMap;
	static {
		final Map<String, String> nameMap = new HashMap<String, String>();
		// Retrieve comm points from CommunicationPointConfigurationProvider:
		Arrays.stream(CommunicationPointType.values()).forEach(value -> nameMap.put(value.getRlcName(), value.getType()));
		// Retrieve filters from FilterConfigurationProvider;
		Arrays.stream(FilterType.values()).forEach(value -> nameMap.put(value.getRlcName(), value.getType()));

		// other comm points and filters that do not have specific configurations defined in the enum classes above.
		nameMap.put("Document-Type Web Service Client", "Document-Type Web Service Client (deprecated)");
		nameMap.put("rhapsody:webServicesConsumerDocument", "Web Service Client");
		nameMap.put("webservicesproducer", "Web Service Hosting");
		nameMap.put("ScriptTCPServer", "JavaScript TCP Server");
		nameMap.put("ScriptTCPClient", "JavaScript TCP Client");
		nameMap.put("ErrorDestinationRedirector", "Error Message Redirector");
		nameMap.put("fifotester", "FIFO Tester");
		nameMap.put("SmsInvision", "SMS Invision");
		nameMap.put("DatabaseInserter", "Database Insertion");
		nameMap.put("ClinicomTCPClient", "Clinicom Client");
		nameMap.put("ClinicomTCPServer", "Clinicom Server");
		nameMap.put("c.o.r.modules.sns", "Amazon SNS");
		nameMap.put("rhapsody:S3", "Amazon S3");
		nameMap.put("c.o.r.modules.sqs", "Amazon SQS");
		nameMap.put("WrappedSerial", "Wrapped Serial (RS-232)");
		// for custom comm point types
		nameMap.put("LoadError", "Unknown Comm Point type");

		correctNameMap = Collections.unmodifiableMap(nameMap);
	}

	/**
	 * Method is used to provide the correct name for component types such as
	 * comm points and filters. These names are needed to match those in the
	 * IDE. The RLC file names some components differently to the IDE.
	 *
	 * @param type String representation of the type of Component.
	 */
	public static String getCorrectComponentTypeName(final String type) {
		final String correctName = correctNameMap.get(type);

		if (StringUtils.isEmpty(correctName)) { // key not found, no need to change name.
			return type;
		}

		return correctName;
	}
}
