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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class RlcFilenameFilterTest {

	private RlcFileAndDirectoryFilter filter;

	@Before
	public void setup() {
		this.filter = new RlcFileAndDirectoryFilter();
	}

	@Test
	public void testAcceptRlcLowerCase() {
		final File dir = new File("");
		final String name = "test.rlc";
		assertTrue(this.filter.accept(dir, name));
	}

	@Test
	public void testAcceptRlcUpperCase() {
		final File dir = new File("");
		final String name = "TEST.RLC";
		assertTrue(this.filter.accept(dir, name));
	}

	@Test
	public void testAcceptRlcMixedCase() {
		final File dir = new File("");
		final String name = "TesT.RlC";
		assertTrue(this.filter.accept(dir, name));
	}

	@Test
	public void testAcceptRlcTrim() {
		final File dir = new File("");
		final String name = " TesT.RlC ";
		assertTrue(this.filter.accept(dir, name));
	}

	@Test
	public void testAcceptRlcNoName() {
		final File dir = new File("");
		final String name = ".rlc";
		assertFalse(this.filter.accept(dir, name));
	}

	@Test
	public void testAcceptRlcNoNameTrim() {
		final File dir = new File("");
		final String name = " .rlc";
		assertFalse(this.filter.accept(dir, name));
	}

	@Test
	public void testAcceptRlcNoDot() {
		final File dir = new File("");
		final String name = "abcrlc";
		assertFalse(this.filter.accept(dir, name));
	}

	@Test
	public void testAcceptRlcBadSuffix1() {
		final File dir = new File("");
		final String name = "abc.rlcd";
		assertFalse(this.filter.accept(dir, name));
	}

	@Test
	public void testAcceptRlcBadSuffix2() {
		final File dir = new File("");
		final String name = "abc.rl";
		assertFalse(this.filter.accept(dir, name));
	}

	@Test
	public void testAcceptRlcBadSuffix3() {
		final File dir = new File("");
		final String name = "abc.rrlc";
		assertFalse(this.filter.accept(dir, name));
	}

	@Test
	public void testAcceptRlcRealFile() {
		final File dir = new File("./test/input/junk_dir");
		final String name = "SIZEOF.rlc";
		assertTrue(this.filter.accept(dir, name));
	}

	@Test
	public void testAcceptRlcRealDir() {
		final File dir = new File("./test/input/junk_dir");
		final String name = "mckesson";
		assertTrue(this.filter.accept(dir, name));
	}

	@Test
	public void testAcceptRlcFakeDir() {
		final File dir = new File("./test/input/junk_dir");
		final String name = "mckesso";
		assertFalse(this.filter.accept(dir, name));
	}

	@Test
	public void testAcceptRlcBadFile() {
		final File dir = new File("./test/input/junk_dir");
		final String name = "RLC%20Load.jpg";
		assertFalse(this.filter.accept(dir, name));
	}

}
