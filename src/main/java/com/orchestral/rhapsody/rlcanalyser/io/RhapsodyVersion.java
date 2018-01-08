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
package com.orchestral.rhapsody.rlcanalyser.io;

/**
 * This class contains information on the Rhapsody Engine Version for a set of
 * RLCs or a single RLC
 *
 */
public class RhapsodyVersion {

	/**
	 * Enum class that stores the various distinguishable version numbers
	 *
	 */
	public enum VersionNumber {
		Rhapsody4_0, Rhapsody5_5, Rhapsody6_0, Rhapsody6_1, RhapsodyLatest;

		private VersionNumber() {
		}

		/**
		 * Calculate the minimum Version number for an RLC. If the version
		 * number is 10 if the engine contains lockers then the engine used is
		 * above 6.1 If the engine contains communications points contain
		 * Dynamic Router Communication Points or (S)FTP Communication Points
		 * then engine used is above 5.5 If none of the above cases match, the
		 * function will return Rhapsody4_0, which is the earliest version
		 * compatible
		 *
		 * @param engine
		 * @return the version number that is the lowest possible for this
		 *         Engine
		 */
		public static VersionNumber calculateMinVersionNumber(final Engine engine) {
			if (engine.getRlcVersion() == 10 || engine.getLockers() != null) {
				return VersionNumber.Rhapsody6_1;
			}
			if (engine.getCommunicationPoints() != null) {
				for (final CommunicationPoint commpoint : engine.getCommunicationPoints()) {
					// Confirm that the type will contain the string exactly
					if (commpoint.getType().toLowerCase().contains("rhapsody:route")
							|| commpoint.getType().toLowerCase().contains("rhapsody:FileTransfer")) {
						return VersionNumber.Rhapsody5_5;
					}
				}
			}
			return VersionNumber.Rhapsody4_0;
		}

		/**
		 * Calculate the maximum version number for an RLC. If the RLC is
		 * version 9 then the maximum possible engine used is version 6.0
		 * Otherwise the function returns RhapsodyLatest (the latest engine
		 * version)
		 *
		 * @param engine
		 * @return the version number that is the maximum possible this engine
		 */
		public static VersionNumber calculateMaxVersionNumber(final Engine engine) {
			if (engine.getRlcVersion() == 9) {
				return VersionNumber.Rhapsody6_0;
			}
			return VersionNumber.RhapsodyLatest;
		}
	}

	private VersionNumber minVersion;

	private VersionNumber maxVersion;

	public RhapsodyVersion() {

	}

	/**
	 * Get the minimum version number
	 *
	 * @return minimum version number stored in this RhapsodyVersion
	 */
	public VersionNumber getMinVersion() {
		return this.minVersion;
	}

	/**
	 * Set the minimum version number of this RhapsodyVersion
	 *
	 * @param minVersion
	 */
	public void setMinVersion(final VersionNumber minVersion) {
		this.minVersion = minVersion;
	}

	/**
	 * Get the maximum version number
	 *
	 * @return maximum version number stored in this RhapsodyVersion
	 */
	public VersionNumber getMaxVersion() {
		return this.maxVersion;
	}

	/**
	 * Set the maximum version number
	 *
	 * @param maxVersion
	 */
	public void setMaxVersion(final VersionNumber maxVersion) {
		this.maxVersion = maxVersion;
	}
}
