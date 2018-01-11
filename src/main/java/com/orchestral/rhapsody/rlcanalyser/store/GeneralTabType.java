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

import org.apache.log4j.Logger;

/**
 * The GeneralTabType enum represents the values of all types of general tab
 * properties found within communication points (and filters).
 */
public enum GeneralTabType {
	TotalCounts("Total Counts", ""),
	// Connection Mode
	cpmBidirectional("Bidirectional", "Connection Mode"),
	cpmInput("Input", "Connection Mode"),
	cpmOutput("Output", "Connection Mode"),
	cpmInOut("In-Out", "Connection Mode"),
	cpmOutIn("Out-In", "Connection Mode"),
	// Startup State
	DONT_START("Manual Restart Required", "Startup State"),
	START_LEVEL_1("Startup First", "Startup State"),
	START_LEVEL_2("Startup Second", "Startup State"),
	START_LEVEL_3("Startup Third", "Startup State"),
	START_LEVEL_4("Startup Fourth", "Startup State"),
	START_LEVEL_5("Startup Last", "Startup State"), // is this correct?
	// Connection Retries
	rtNone("No Retry", "Retry Type"),
	rtImmediate("Immediate", "Retry Type"),
	rtLinear("Linear", "Retry Type"),
	rtExponential("Exponential", "Retry Type");

	private final String type;
	private final String propertyType;
	private final static Logger logger = Logger.getLogger(GeneralTabType.class);

	GeneralTabType(final String type, final String propertyType) {
		this.type = type;
		this.propertyType = propertyType;
	}

	/**
	 * @return the propertyType
	 */
	public String getPropertyType() {
		return this.propertyType;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Method is used to get the enum type give the string representation of the
	 * enum.
	 *
	 * @param correctName
	 * @return
	 */
	public static GeneralTabType getEnumFromName(final String correctName) {
		for (final GeneralTabType tabType : GeneralTabType.values()) {
			if (tabType.type.equals(correctName)) {
				return tabType;
			}
		}
		// If we've reached this far, an unexpected Enum value was encountered.
		throw new IllegalArgumentException("No General Tab Type Enum corresponds to " + correctName);
	}
}

