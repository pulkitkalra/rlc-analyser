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

public class FilterConfigurationProvider {
	/**
	 * Filter Type Enum is used to define types of filters for which we wish to
	 * include configurations for and show in the UI.
	 *
	 * It is defined as: (Display name - in UI, Name - as it appears in the RLC
	 * File, properties).
	 */
	enum FilterType {

		Mapper("Mapper", "Mapper",
		       new String[] {
		    		   "NumberInstances|ptInteger|0",
		    		   "MapperFile|ptMapperDefn|",
		    		   "OutputEncoding|ptMap|5",
		    		   "OutputEncodingName|ptString|",
		    		   "OutputBOM|ptBoolean|false",
		    		   "FailOnErrors|ptBoolean|false",
		    		   "LogErrors|ptBoolean|false",
		    		   "PropertyCopyIndex|ptInteger|0",
		    		   "ValidateInput|ptMap|2",
		    		   "ValidateOutput|ptMap|1",
		}),

		Batcher("Batcher", "Batcher", new String[] {
				"Mode|ptMap|0",
				"Header|ptString|",
				"Separator|ptString|",
				"Trailer|ptString|",
				"HeaderExpr|ptString|",
				"TrailerExpr|ptString|",
				"MarkerOrSeparator|ptMap|0",
				"SeparatorExpr|ptString|",
		}),

		ContentInserter("Content Inserter", "ContentInserter",
		                new String[] {
		                		"NumberInstances|ptInteger|0",
		                		"Definition|ptMsgDefn|",
		                		"MsgBody|ptFile|",
		                		"Fields|ptArray|",
		}),

		AckGenerationFilter("ACK Generation Filter", "AckGenerationFilter",
		                    new String[] {
		                    		"MessageDefintion|ptMsgDefn|",
		                    		"AckMapping|ptArray|",
		                    		"Acknowledgment_Mode|ptMap|0",
		                    		"NACK Mode|ptString|AR",
		                    		"ErrorDescription|ptString|",
		                    		"On message validation failure|ptMap|0",
		                    		"On successful validation|ptMap|0",
		                    		"Message Errors Property Name|ptArray|",
		                    		"HL7 version|ptMap|0",
		                    		"FieldLength|ptMap|0",
		                    		"CopyFields|ptArray|[[MSH.SendingApplication,MSH.ReceivingApplication]," +
		                    				"[MSH.SendingFacility,MSH.ReceivingFacility]," +
		                    				"[MSH.ReceivingApplication,MSH.SendingApplication]," +
		                    				"[MSH.ReceivingFacility,MSH.SendingFacility]," +
		                    				"[MSH.MessageControlId,MSA.MessageControlId]," +
		                    				"[MSH.ProcessingID,MSH.ProcessingID]]"
		}),

		PropertyPopulationFilter("Property Population Filter", "PropertyPopulationFilter",
		                         new String[] {
		                        		 "PropertyStrings|ptArray|",
		                        		 "Definition|ptMsgDefn|",
		                        		 "PropertyFields|ptArray|",
		                        		 "OnMissingField|ptMap|1"
		}),

		DatabaseLookUp("Database LookUp", "DatabaseLookUp",
		               new String[] {
		            		   "NumberInstances|ptInteger|0",
		            		   "DatabaseSetting|ptMap|4",
		            		   "Host|ptString|",
		            		   "Port|ptInteger|",
		            		   "DatabaseName|ptString|",
		            		   "Username|ptString|",
		            		   "Password|ptPassword|RIJwae8+iBPmTbCKGqE7fThZW/SyP3Ia7sKo/6xjZlzMRyPjHgsWTw==",
		            		   "ConfigFile|ptFile|",
		            		   "MessageType|ptMap|0",
		            		   "Definition|ptMsgDefn|",
		            		   "RetryAttempts|ptInteger|5",
		            		   "QueryTimeout|ptInteger|",
		            		   "RetryDelay|ptInteger|100"
		}),

		EDIMessageValidator("EDI Message Validator", "EDIMessageValidator",
		                    new String[] {
		                    		"NumberInstances|ptInteger|0",
		                    		"Definition|ptMsgDefn|",
		                    		"OnValidationFailure|ptMap|0",
		                    		"OnParseFailure|ptMap|0"
		}),

		XSLT("XSLT", "XSLT", new String[] {
				"NumberInstances|ptInteger|0",
				"XSLTFile|ptFile|",
				"InputDefn|ptMsgDefn|",
				"OutputDefn|ptMsgDefn|"
		}),

		FOPFilter("FOP Filter", "FOPFilter",
		          new String[] {
		        		  "NumberInstances|ptInteger|0",
		        		  "Format|ptMap|0",
		        		  "XSLTFile|ptFile|",
		        		  "ConfigFile|ptFile|",
		        		  "InputDefn|ptMsgDefn|"
		}),

		DatabaseQuery("Database Message Extraction", "DatabaseQuery",
		              new String[] {
		            		  "NumberInstances|ptInteger|0",
		            		  "DatabaseSetting|ptMap|4",
		            		  "Host|ptString|",
		            		  "Port|ptInteger|",
		            		  "DatabaseName|ptString|",
		            		  "Username|ptString|",
		            		  "Password|ptPassword|drWtKHuzaReBdyKqoSZC3aEfU2czpiwBATR/WtzCFDKSkmqF+hLE3A==",
		            		  "ConfigFile|ptFile|",
		            		  "MessageBodyColumn|ptString|",
		            		  "MessageType|ptMap|0",
		            		  "Definition|ptMsgDefn|",
		            		  "RetryAttempts|ptInteger|5",
		            		  "QueryTimeout|ptInteger|",
		            		  "RetryDelay|ptInteger|100"
		}),

		ExecuteCmd("Execute Command", "ExecuteCmd", new String[] {
				"Cmd|ptString|",
				"StdErr|ptMap|0"
		});

		final String type;

		final String rlcName;

		final String[] propertyDefinitions;

		final static FilterType[] VALUES = values();

		FilterType(final String type, final String rlcFileName, final String[] propertyDefinitions) {
			this.type = type;
			this.propertyDefinitions = propertyDefinitions;
			this.rlcName = rlcFileName;
		}

		String getType() {
			return this.type;
		}

		String[] getPropertyDefinitions() {
			return this.propertyDefinitions;
		}

		static FilterType getFilterType(final String type) {
			assert type != null;

			for (final FilterType filterType : VALUES) {
				if (type.equalsIgnoreCase(filterType.type)) {
					return filterType;
				}
			}
			return null;
		}

		String getRlcName() {
			return this.rlcName;
		}
	}

	public static FilterType[] getAllFilterTypes() {
		return FilterType.VALUES;
	}

}
