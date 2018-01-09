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
package com.orchestral.rhapsody.rlcanalyser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.orchestral.rhapsody.rlcanalyser.io.Engine;
import com.orchestral.rhapsody.rlcanalyser.io.EngineTest;
import com.orchestral.rhapsody.rlcanalyser.io.RhapsodyVersion;
import com.orchestral.rhapsody.rlcanalyser.io.RhapsodyVersion.VersionNumber;
import com.orchestral.rhapsody.rlcanalyser.store.RLCDataStore;

/**
 *
 */
public class RhapsodyVersionCollectorTest {

	private RLCDataStore collectiveDataStore;

	private EngineTest engTest;

	public RhapsodyVersionCollectorTest() {
		initTest();
	}

	private RhapsodyVersion rhapsodyVersionGenerator(final Engine engine) {
		final RhapsodyVersion versionRange = new RhapsodyVersion();
		versionRange.setMaxVersion(VersionNumber.calculateMaxVersionNumber(engine));
		versionRange.setMinVersion(VersionNumber.calculateMinVersionNumber(engine));
		return versionRange;
	}


	public void initTest() {
		this.engTest = new EngineTest();
	}

	@Test
	public void testCalculateNewMinVersionNumber() {
		this.collectiveDataStore = new RLCDataStore();
		this.collectiveDataStore.setRhapsodyVersion(rhapsodyVersionGenerator(this.engTest.getSample()));

		assertEquals(this.collectiveDataStore.getRhapsodyVersion().getMinVersion(), VersionNumber.Rhapsody4_0);

		final RhapsodyVersion newVersion = rhapsodyVersionGenerator(this.engTest.sampleEngineWithDynamicRoutes());
		// Apply the calculate new min version
		this.collectiveDataStore.calculateNewMinVersion(newVersion.getMinVersion());

		assertEquals(this.collectiveDataStore.getRhapsodyVersion().getMinVersion(), VersionNumber.Rhapsody4_0);
	}

	@Test
	public void testCalculateNewMaxVersionNumber() {
		this.collectiveDataStore = new RLCDataStore();
		this.collectiveDataStore.setRhapsodyVersion(rhapsodyVersionGenerator(this.engTest.getSample()));

		assertEquals(this.collectiveDataStore.getRhapsodyVersion().getMaxVersion(), VersionNumber.Rhapsody6_0);

		final RhapsodyVersion newVersion = rhapsodyVersionGenerator(this.engTest.sampleEngineWithDynamicRoutes());

		this.collectiveDataStore.calculateNewMaxVersion(newVersion.getMaxVersion());

		assertEquals(this.collectiveDataStore.getRhapsodyVersion().getMaxVersion(), VersionNumber.Rhapsody6_0);

	}

	@Test
	public void testMultipleRLCNewVersion() {
		this.collectiveDataStore = new RLCDataStore();
		this.collectiveDataStore.setRhapsodyVersion(rhapsodyVersionGenerator(this.engTest.getSample()));

		RhapsodyVersion newVersion = rhapsodyVersionGenerator(this.engTest.sampleEngineWithLockers());

		this.collectiveDataStore.calculateNewMinVersion(newVersion.getMinVersion());
		this.collectiveDataStore.calculateNewMaxVersion(newVersion.getMaxVersion());

		assertEquals(this.collectiveDataStore.getRhapsodyVersion().getMinVersion(), VersionNumber.Rhapsody4_0);
		// Retained the previously calculated max version
		assertEquals(this.collectiveDataStore.getRhapsodyVersion().getMaxVersion(), VersionNumber.RhapsodyLatest);

		newVersion = rhapsodyVersionGenerator(this.engTest.sampleEngineWithDynamicRoutes());

		this.collectiveDataStore.calculateNewMinVersion(newVersion.getMinVersion());
		this.collectiveDataStore.calculateNewMaxVersion(newVersion.getMaxVersion());

		assertEquals(this.collectiveDataStore.getRhapsodyVersion().getMinVersion(), VersionNumber.Rhapsody4_0);
		// Retained the previously calculated max version
		assertEquals(this.collectiveDataStore.getRhapsodyVersion().getMaxVersion(), VersionNumber.RhapsodyLatest);
	}

}
