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


public class CommunicationPointConfigurationProvider {
	/**
	 * CommunicationPoint Type Enum is used to define types of filters for which
	 * we wish to include configurations for and show in the UI.
	 *
	 * It is defined as: (Display name - in UI, Name - as it appears in the RLC
	 * File, properties).
	 */
	enum CommunicationPointType {

		//@formatter:off
		TCPServer("TCP Server", "TCPServer", new String[] {
				"LOCALPORT|ptInteger|",
				"LOCALHOST|ptString|",
				"SERVERSOCKET_LISTEN_BACKLOG|ptInteger|10",
				"SECURE|ptBoolean|false",
				"SSL_MODE|ptMap|0",
				"SSL_CLIENT_MODE|ptBoolean|false",
				"SECURE_KEYS|ptArray|",
				"SECURE_TRUSTS|ptArray|",
				"LOGCONNECTIONS|ptBoolean|false",
				"LOGDATA|ptBoolean|false",
				"DATAHEX|ptBoolean|false",
				"CONNECTIONLOG|ptString|",
				"LOGTIME|ptMap|1",
				"INCOMING_WRAPPER|ptMap|0",
				"INCOMING_STRIP_WRAPPERS|ptBoolean|true",
				"INCOMING_HEADER|ptString|",
				"INCOMING_TRAILER|ptString|",
				"INCOMING_ENDIAN|ptMap|0",
				"IN_OUT_TIMEOUT_ACTION|ptMap|RECEIVE_NEXT_MESSAGE",
				"IN_OUT_STATIC_TIMEOUT_RESPONSE|ptFile|",
				"OUTGOING_WRAPPER|ptMap|0",
				"OUTGOING_HEADER|ptString|",
				"OUTGOING_TRAILER|ptString|",
				"OUTGOING_ENDIAN|ptMap|0",
				"InputCharacterEncoding||151"
		}),

		TCPClient("TCP Client", "TCPClient", new String[] {
				"HOST|ptString|",
				"PORT|ptString|",
				"LOCALHOST|ptString|",
				"LOCALPORT|ptInteger|0",
				"SECURE|ptBoolean|false",
				"SSL_MODE|ptMap|0",
				"SSL_CLIENT_MODE|ptBoolean|false",
				"SECURE_KEYS|ptArray|",
				"SECURE_TRUSTS|ptArray|",
				"CONNECTION_TIMEOUT|ptInteger|300000",
				"LOGCONNECTIONS|ptBoolean|false",
				"LOGDATA|ptBoolean|false",
				"DATAHEX|ptBoolean|false",
				"CONNECTIONLOG|ptString|",
				"LOGTIME|ptMap|1",
				"ConnectionMode|ptMap|0",
				"INCOMING_WRAPPER|ptMap|0",
				"INCOMING_STRIP_WRAPPERS|ptBoolean|true",
				"INCOMING_HEADER|ptString|",
				"INCOMING_TRAILER|ptString|",
				"INCOMING_ENDIAN|ptMap|0",
				"OUTGOING_WRAPPER|ptMap|0",
				"OUTGOING_HEADER|ptString|",
				"OUTGOING_TRAILER|ptString|",
				"OUTGOING_ENDIAN|ptMap|0",
				"InputCharacterEncoding||151"
		}),

		Database("Database", "Database", new String[] {
				"DatabaseSetting|ptMap|4",
				"Host|ptString|",
				"Port|ptInteger|",
				"DatabaseName|ptString|",
				"Username|ptString|",
				"Password|ptPassword|r+AhUKNc2hHqANIieumDDSnIjENxpFtPFA8wlh3JYw5P1tfFfPSY3w==",
				"ConfigFile|ptFile|",
				"QueryTimeout|ptInteger|0",
				"MessageBodyColumn|ptString|",
				"MessageType|ptMap|0",
				"Definition|ptMsgDefn|",
				"Deadlock|ptBoolean|true",
				"QueryTimeoutConnectionError|ptBoolean|true",
				"InputCharacterEncoding||151"
		}),

		Directory("Directory", "Directory", new String[] {
				"INPUT_DIRECTORY_NAME|ptDir|",
				"FILTER_PATTERN|ptString|*",
				"EXCLUDE_PATTERN|ptString|",
				"InputOrder|ptMap|0",
				"JavaScriptComparator|ptJScript|",
				"DIR_REFRESH_RATE|ptInteger|5000",
				"RECEIVE_RATE|ptInteger|0",
				"MOVE_PROCCESSED_FILES|ptBoolean|false",
				"MOVE_TO_DIRECTORY_NAME|ptDir|",
				"ZIP_FILE_INPUT_MODE|ptBoolean|false",
				"INPUT_DEBATCH_MODE|ptBoolean|false",
				"InputHeaderRegex|ptString|",
				"InputTrailerRegex|ptString|",
				"InputSeparatorType|ptMap|0",
				"InputSeparatorRegex|ptString|",
				"OUTPUT_DIRECTORY_NAME|ptDir|",
				"OUTPUT_BASE_FILENAME|ptString|",
				"OUTPUT_FILENAME_SUFFIX|ptString|",
				"OUTPUT_FILENAME_APPENDDATE|ptBoolean|false",
				"OUTPUT_BATCH_MODE|ptBoolean|false",
				"OUTPUT_OVERWRITE_FILES|ptBoolean|false",
				"USE_TEMP|ptMap|0",
				"OUTPUT_WRAPPER|ptMap|2",
				"OUTPUT_HEADER|ptString|",
				"OUTPUT_TRAILER|ptString|",
				"ENDIAN|ptMap|0",
				"BATCHFILE_ROLLOVER|ptMap|2",
				"BATCHFILE_ROLLOVERSIZE|ptString|",
				"COMPRESS_BATCHFILE|ptBoolean|false",
				"INDIVIDUAL_FILES_IN_ZIP|ptBoolean|false",
				"InputCharacterEncoding||151"
		}),

		Email("E-mail", "E-mail", new String[] {
				"INCOMING_HOST|ptString|",
				"STORE_PROTOCOL|ptMap|0",
				"POP3_PORT|ptInteger|110",
				"IMAP_PORT|ptInteger|",
				"IN_SECURE|ptBoolean|false",
				"IN_SSL_MODE|ptMap|0",
				"IN_SECURE_TRUSTS|ptArray|",
				"IN_USER|ptString|",
				"IN_PASSWORD|ptPassword|ujrVvbtvhKAIQdeKX8nm08fMynFP51VujfYsW+GBO5SoJbOv065bqw==",
				"FOLDER|ptString|INBOX",
				"FOLDER_REFRESH_RATE|ptInteger|60000",
				"DELETE_MESSAGE|ptBoolean|false",
				"MESSAGE_AS_ATTACHMENT|ptBoolean|false",
				"JAVA_MAIL_OUTPUT_DEBUGGING_KEY|ptBoolean|false",
				"OUTGOING_HOST|ptString|",
				"TRANSPORT_PROTOCOL|ptMap|0",
				"PORT|ptInteger|25",
				"OUT_SECURE|ptBoolean|false",
				"OUT_SSL_MODE|ptMap|0",
				"OUT_SECURE_TRUSTS|ptArray|",
				"USER|ptString|",
				"PASSWORD|ptPassword|mjer0F5Ftl1hPpRGOFgNxf1IvPJREwc62im5zELPkVXTHPOHD0ZNQw==",
				"FROM|ptString|",
				"TO|ptString|",
				"CC|ptString|",
				"SUBJECT|ptString|Rhapsody Mail Message",
				"SEND_RHAPSODY_MESSAGE|ptBoolean|true",
				"REPLACEMENT_BODY_TEXT|ptString|",
				"SEND_AS_ATTACHMENT|ptBoolean|false",
				"ATTACHMENT_NAME|ptString|",
				"BODY_TEXT|ptString|",
				"CONTENT_TYPE|ptString|text/plain",
				"SMIME|ptBoolean|false",
				"SMIME_OPTION|ptMap|0",
				"SIGN_OPTION|ptMap|1",
				"PRIVATE_KEY|ptKey|",
				"SIGN_ALG|ptMap|0",
				"RECI_CERT|ptArray|",
				"ENCRYPTION_ALG|ptMap|0",
				"PRIORITY|ptMap|1",
				"JAVA_MAIL_INPUT_DEBUGGING|ptBoolean|false",
				"InputCharacterEncoding||151"
		}),

		FTPClient("FTP Client (deprecated)", "FTPClient", new String[] {
				"Server|ptString|",
				"Port|ptInteger|0",
				"Username|ptString|",
				"Password|ptPassword|CEL87UmPT/ThsAM/Sn+YBv0yiv9FYXMp/yT00+P+Ua39qjMrnnBEgA==",
				"Passive|ptBoolean|false",
				"ReadTimeout|ptInteger|30000",
				"TransferType|ptMap|0",
				"ConnectionMode|ptMap|0",
				"ConnectionType|ptMap|0",
				"DataChannelProtectionLevel|ptMap|1",
				"FTPSMode|ptMap|1",
				"DisableStandardClosure|ptBoolean|false",
				"ServerValidation|ptBoolean|false",
				"ServerCertificates|ptArray|",
				"CheckHostNames|ptBoolean|true",
				"ClientAuthentication|ptBoolean|false",
				"ClientKey|ptKey|",
				"ClientCertificate|ptCertificate|",
				"InputDir|ptDir|",
				"ListParam|ptString|",
				"Pattern|ptString|*",
				"RefreshRate|ptInteger|60000",
				"DeleteAfterReceive|ptBoolean|true",
				"BeforeReceive|ptArray|",
				"OutputDir|ptDir|",
				"OutputDirCreate|ptBoolean|false",
				"BaseFilename|ptString|",
				"Suffix|ptString|",
				"AppendDate|ptBoolean|true",
				"CheckName|ptBoolean|false",
				"UseTempFile|ptBoolean|true",
				"AppendKey|ptBoolean|false",
				"BeforeSend|ptArray|",
				"InputCharacterEncoding||151"
		}),

		HTTPClient("HTTP Client", "HTTPClient", new String[] {
				"URL|ptString|",
				"Method|ptMap|0",
				"RequestHeaders|ptArray|",
				"FollowRedirects|ptBoolean|true",
				"Secure|ptBoolean|false",
				"SecureMode|ptMap|0",
				"SecureTrusts|ptArray|",
				"SecureKeys|ptArray|",
				"HostnameVerification|ptBoolean|true",
				"Username|ptString|",
				"Password|ptPassword|pwbwKWilRqTkpBHZTM4W9CGDumXXKOMUEvUtJV0bEcLsgrTXj47vpA==",
				"ProtocolSupport|ptMap|0",
				"ReadTimeout|ptInteger|10000",
				"ConnectTimeout|ptInteger|60000",
				"RefreshRate|ptInteger|60000",
				"UseIfModifiedSince|ptBoolean|false",
				"ResponseHeaders|ptArray|",
				"MessageContent|ptMap|1",
				"ContentType|ptString|text/plain",
				"ContentEncoding|ptString|",
				"FormEncodingScheme|ptMap|0",
				"Definition|ptMsgDefn|",
				"MessageRequestHeaders|ptArray|",
				"MessageRequestParams|ptArray|",
				"FormValues|ptArray|",
				"OnClientErred|ptMap|0",
				"OnServerErred|ptMap|0",
				"Copyheaders|ptBoolean|false",
				"REtoMatchHeaders|ptArray|",
				"InputCharacterEncoding||151"
		}),

		HTTPServer("HTTP Server", "HTTPServer", new String[] {
				"LocalHost|ptString|",
				"LocalPort|ptInteger|80",
				"ContextPath|ptString|",
				"Timeout|ptInteger|0",
				"Secure|ptBoolean|false",
				"SslMode|ptMap|0",
				"ClientAuth|ptBoolean|false",
				"SecureKeys|ptArray|",
				"SecureTrusts|ptArray|",
				"GetAction|ptMap|4",
				"PutAction|ptMap|5",
				"PostAction|ptMap|5",
				"HeadAction|ptMap|4",
				"TraceAction|ptMap|5",
				"DeleteAction|ptMap|5",
				"OptionsAction|ptMap|5",
				"RedirectURL|ptString|",
				"Template|ptFile|",
				"RequestParams|ptArray|",
				"MessageParamName|ptString|",
				"NullMessage|ptBoolean|false",
				"ProtocolSupport|ptMap|0",
				"RequestHeaders|ptArray|",
				"ContentType|ptString|text/plain",
				"ContentEncoding|ptString|",
				"ResponseHeaders|ptArray|",
				"Copyheaders|ptBoolean|false",
				"REtoMatchHeaders|ptArray|",
				"InputCharacterEncoding||151"
		}),

		DynamicRouter("Dynamic Router", "rhapsody:router", new String[] {
				"TargetName|unknown|",
				"UniqueTargetName|unknown|false",
				"DeliveryMode|unknown|dynamicDestination",
				"OnMissingDynamicDestination|unknown|errorQueue",
				"OnInvalidDynamicDestination|unknown|errorQueue",
				"StaticDestination|unknown|",
				"IndexMessageProperties|unknown|false"
		}),

		SFTPClient("(S)FTP Client","rhapsody:FileTransfer", new String[] {
				"BeforeReceiveCommands|unknown|",
				"AfterReceiveCommands|unknown|",
				"ArchivingStrategy|unknown|normal",
				"OutputDir|unknown|",
				"BaseFilename|unknown|",
				"Suffix|unknown|",
				"DuplicateBehaviour|unknown|rename",
				"AppendDate|unknown|doNotAppendDateTime",
				"UseTempFile|unknown|noTempFiles",
				"BeforeSendCommands|unknown|",
				"AfterSendCommands|unknown|"
		}),

		JavaScriptRestClient("JavaScript REST Client", "rhapsody:RestClient",new String[] {
				"RestClient|unknown|",
				"JavascriptRequest|unknown|",
				"ReadTimeout|unknown|10000"
		}),

		RhapsodyConnctor("Rhapsody Connector", "rhapsodyConnector", new String[] {
				"MODE|unknown|Client",
				"HOST|unknown|",
				"PORT|unknown|",
				"LOCALPORT|unknown|0",
				"LOCALHOST|unknown|",
				"SSL_MODE|unknown|Transport Layer Security (TLS v1.2)",
				"SSL_CIPHER_SUITES|unknown|FIPS",
				"SECURE_KEYS|unknown|",
				"SECURE_TRUSTS|unknown|",
				"SECURE_ISSUERS|unknown|",
				"SERVERSOCKET_LISTEN_BACKLOG|unknown|0",
				"REQUIREDCAPABILITIES|unknown|",
				"OPTIONALCAPABILITIES|unknown|",
				"CONNECTIONRESPONSETIMEOUT|unknown|60",
				"CONNECTIONESTABLISHMENTTIMEOUT|unknown|120",
				"KEEPALIVEPERIOD|unknown|300",
				"CONNECTION_IDENTIFIERS|unknown|AUTO"
		})
		;
		//@formatter:on

		final String type;

		final String rlcName;

		final String[] propertyDefinitions;

		final static CommunicationPointType[] VALUES = values();

		CommunicationPointType(final String type, final String rlcName, final String[] propertyDefinitions) {
			this.type = type;
			this.propertyDefinitions = propertyDefinitions;
			this.rlcName = rlcName;
		}

		String getType() {
			return this.type;
		}

		String[] getPropertyDefinitions() {
			return this.propertyDefinitions;
		}

		static CommunicationPointType getCommunicationPointType(final String type) {
			assert type != null;

			for (final CommunicationPointType cpType : VALUES) {
				if (type.equalsIgnoreCase(cpType.type)) {
					return cpType;
				}
			}
			return null;
		}

		String getRlcName(){
			return this.rlcName;
		}
	}

	public static CommunicationPointType[] getAllCommunicationPointTypes() {
		return CommunicationPointType.VALUES;
	}
}
