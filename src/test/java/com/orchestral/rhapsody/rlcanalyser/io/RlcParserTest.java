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

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

public class RlcParserTest {

	private static RlcParser parser;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		parser = new RlcParser();
	}

	/**
	 * @param filePath
	 * @throws FileNotFoundException
	 */
	private void testXmlFile(final String filePath) throws FileNotFoundException {
		final File file = new File(filePath);
		final InputStream input = new FileInputStream(file);
		final Engine engine = parser.parse(input);
		assertNotNull(engine);
	}

	@Test
	public void testParseSimple() throws FileNotFoundException {
		testXmlFile("./test/input/xml/simple.xml");
	}

	@Test
	public void testParseComplex() throws FileNotFoundException {
		testXmlFile("./test/input/xml/smetmp634E.xml");
	}

	@Test
	public void testParseWithExtra() throws FileNotFoundException {
		testXmlFile("./test/input/xml/smetmp387C.xml");
	}
}
