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
import java.util.HashMap;
import java.util.Map;

import com.orchestral.rhapsody.rlcanalyser.TypeCountData;

/**
 *
 */
public class CommunicationPointGeneralTabProvider {

	enum GeneralTabType {
		Mode("Connection Mode", new String[] { "cpmBidirectional", "cpmInput", "cpmOutput", "cpmInOut", "cpmOutIn" }),

		StartupState("Startup State", new String[] { "DONT_START", "START_LEVEL_1", "START_LEVEL_2", "START_LEVEL_3",
				"START_LEVEL_4", "START_LEVEL_5" }),

		RetryType("Retry Type", new String[] { "rtNone", "rtImmediate", "rtLinear", "rtExponential" }),

		TotalCounts("Total Counts", new String[] {});

		final static GeneralTabType[] VALUES = values();
		private final String type;
		private final String[] propertyDefinitions;

		GeneralTabType(final String type, final String[] propertyDefinitions) {
			this.type = type;
			this.propertyDefinitions = propertyDefinitions;
		}
	}

	private final Map<GeneralTabType, TypeCountData> generalMap;

	public CommunicationPointGeneralTabProvider(final RLCDataStore dataStore) {
		this.generalMap = new HashMap<GeneralTabType, TypeCountData>();
		Arrays.stream(GeneralTabType.values()).forEach(type -> this.generalMap.put(type, null));
	}
}
