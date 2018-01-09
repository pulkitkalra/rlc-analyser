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

import com.orchestral.rhapsody.rlcanalyser.io.Definition.DefinitionType;
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
	 *
	 * Note that not all Filters/ CommPoint names have been given a mapping.
	 * Most of the common ones are mapped to their correct names.
	 *
	 * FIXME: This is a lot of hard coded names and could change in the future.
	 * This table will need to be maintained as names change or new components
	 * are added and there is a risk that the information easily becomes out of
	 * date.
	 */
	private static final Map<String, String> correctNameMap;

	// statically initialize map
	static {
		final Map<String, String> nameMap = new HashMap<String, String>();
		// Retrieve comm points from CommunicationPointConfigurationProvider:
		Arrays.stream(CommunicationPointType.values()).forEach(value -> nameMap.put(value.getRlcName(), value.getType()));
		// Retrieve filters from FilterConfigurationProvider;
		Arrays.stream(FilterType.values()).forEach(value -> nameMap.put(value.getRlcName(), value.getType()));
		// Retrieve Definitions from DefinitionTypes;
		Arrays.stream(DefinitionType.values()).forEach(def -> nameMap.put(def.toString(), def.getCorrectName()));
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
		nameMap.put("WebServices", "RPC Web Service Client (deprecated)");
		nameMap.put("SOAPWebServiceConsumer", "SOAP Web Service Consumer (deprecated)");
		// for custom comm point types
		nameMap.put("LoadError", "Unknown Comm Point type");
		// common filter types (most included, not all)
		nameMap.put("ExecuteScript", "JavaScript");
		nameMap.put("SearchAndReplace", "Search And Replace");
		nameMap.put("DuplicateMessageFilter", "Duplicate Message Detection");
		nameMap.put("DateValidationFilter", "Date Validation");
		nameMap.put("ebXMLFilter", "ebXML");
		nameMap.put("Base64Encoder", "Base64 Encoding");
		nameMap.put("X12Splitter", "X12 Splitter");
		nameMap.put("CharacterSetTranslator", "Character Encoding Translator");
		nameMap.put("ZipFilter", "Zip/ Unzip");
		nameMap.put("AsymmetricCrypto", "Asymmetric Crypto");
		nameMap.put("ebXMLAutoReplyFilter", "ebXML AutoReply");
		nameMap.put("DuplicateMessageFilter", "Duplicate Message Detection");
		nameMap.put("FreeMarkerMapper", "FreeMarker Mapper");
		nameMap.put("IntelligentMapper", "Intelligent Mapper");
		nameMap.put("PropertyPopulationFilter", "Property Population");
		nameMap.put("SchematronFilter", "Schematron");
		nameMap.put("rhapsody: LoadSnapshot", "Load Snapshot");
		nameMap.put("rhapsody: SaveSnapshot", "Save Snapshot");
		nameMap.put("StubF", "Stub Filter");
		nameMap.put("ebXMLMessageStatusFilter", "ebXML Message Status Service");
		nameMap.put("ebMXLPingFilter", "ebXML Ping Service");
		nameMap.put("rhapsody:ihe:recipientProvideRegisterTransformationFilter", "IHE - XDR Recipient: Provide & Register >");
		nameMap.put("rhapsody:ihe:recipientProvideRegisterResponseTransformationFilter", "IHE - XDR Recipient: Provide & Register Response <");
		nameMap.put("rhapsody:ihe:consumerMPQTransformationFilter", "IHE - XDS Consumer: Registery MPQ <");
		nameMap.put("rhapsody:ihe:consumerMPQResponseTransformationFilter", "IHE - XDS Consumer: Registery MPQ Response >");
		nameMap.put("rhapsody:ihe:consumerQueryTransformationFilter", "IHE - XDS Consumer: Registery Query <");
		nameMap.put("rhapsody:ihe:consumerQueryResponseTransformationFilter", "IHE - XDS Consumer: Registery Query Response >");
		nameMap.put("rhapsody:ihe:consumerRetrieveTransformationFilter", "IHE - XDS Consumer: Retrieve Document <");
		nameMap.put("rhapsody:ihe:consumerRetrieveResponseTransformationFilter", "IHE - XDS Consumer: Retrieve Document Response >");
		nameMap.put("rhapsody:ihe:repositoryProvideRegisterTransformationFilter", "IHE - XDS Repository: Provide & Register >");
		nameMap.put("rhapsody:ihe:repositoryProvideRegisterResponseTransformationFilter", "IHE - XDS Repository: Provide & Register Response <");
		nameMap.put("rhapsody:ihe:repositoryRegisterTransformationFilter", "IHE - XDS Repository: Register <");
		nameMap.put("rhapsody:ihe:repositoryRegisterResponseTransformationFilter", "IHE - XDS Repository: Register Response >");
		nameMap.put("rhapsody:ihe:repositoryRetrieveTransformationFilter", "IHE - XDS Repository: Retrieve >");
		nameMap.put("rhapsody:ihe:repositoryRetrieveResponseTransformationFilter", "IHE - XDS Repository: Retrieve Response <");
		nameMap.put("rhapsody:ihe:sourceProvideRegisterTransformationFilter", "IHE - XDS/XDR Source: Provide & Register <");
		nameMap.put("rhapsody:ihe:sourceProvideRegisterResponseTransformationFilter", "IHE - XDS Repository: Provide & Register Response >");
		nameMap.put("HIPAA Validator2", "HIPAA 4010 Validation");
		nameMap.put("HIPAA ReBatcher", "HIPAA 4010 ReBatcher");
		nameMap.put("Segment Separator", "HIPAA 4010 Segment Formatter");
		nameMap.put("HIPAA Splitter", "HIPAA 4010 Splitter");

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
