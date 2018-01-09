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

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.orchestral.rhapsody.rlcanalyser.io.RhapsodyVersion.VersionNumber;

/**
 *
 */
public class RhapsodyVersionTest {

	private XStreamInstance xstream;
	private EngineTest engTest;

	@Before
	public void initialize() {
		this.xstream = new XStreamInstance();
		this.engTest = new EngineTest();
	}

	@Test
	public void testRhapsodyVersionContainsLockers() {
		final Engine engine = this.engTest.sampleEngineWithLockers();
		assertEquals(VersionNumber.calculateMaxVersionNumber(engine), VersionNumber.RhapsodyLatest);
		assertEquals(VersionNumber.calculateMinVersionNumber(engine), VersionNumber.Rhapsody6_1);
	}

	@Test
	public void testRhapsodyVersionContainsDynamicRoute() throws NullPointerException {
		final Engine engine = this.engTest.sampleEngineWithDynamicRoutes();
		assertEquals(VersionNumber.calculateMaxVersionNumber(engine), VersionNumber.Rhapsody6_0);
		assertEquals(VersionNumber.calculateMinVersionNumber(engine), VersionNumber.Rhapsody5_5);

	}

	@Test
	public void testRhapsodyVersionSimpleEngine() {
		assertEquals(VersionNumber.calculateMaxVersionNumber(this.engTest.getSample()), VersionNumber.Rhapsody6_0);
		assertEquals(VersionNumber.calculateMinVersionNumber(this.engTest.getSample()), VersionNumber.Rhapsody4_0);
	}
}
