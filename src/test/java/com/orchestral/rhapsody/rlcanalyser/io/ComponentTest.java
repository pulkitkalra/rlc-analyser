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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class ComponentTest {

	private XStreamInstance xstream;

	/** Function will open a file specified by the input parameter, and return a string containing the contents of that file
	 * @param file name
	 */
	private String getFile(final String fileName) throws FileNotFoundException {
		String inputString = "";
		Scanner input = null;

		try {
			final File file = new File(fileName);
			input = new Scanner(file);
			inputString = input.useDelimiter("\\A").next();
		} finally {
			input.close();
		}

		return inputString;
	}

	/**
	 *  Processing all annotations manually
	 */
	@Before
	public void componentTestInit() {
		this.xstream = new XStreamInstance();
	}

	@Test
	public void testLocker() throws FileNotFoundException {
		final Locker lockerToCompare = ComponentFactory.get().createBasicLocker();

		// Getting the xml result and comparing the object stored in factory
		final String xmlToString = getFile("test/input/basicTestXML/LockerTest.xml");
		final Locker lockerToTest = (Locker) this.xstream.fromXML(xmlToString);

		assertEquals(lockerToCompare.getId(), lockerToTest.getId());
	}

	@Test
	public void testLookupTable() throws FileNotFoundException {
		final LookupTable lutToCompare = ComponentFactory.get().createBasicLookUpTable();

		// Getting the xml result and comparing the object stored in factory
		final String xmlToString = getFile("test/input/basicTestXML/LookupTableTest.xml");
		final LookupTable lutToTest = (LookupTable) this.xstream.fromXML(xmlToString);

		assertEquals(lutToCompare.getId(), lutToTest.getId());
	}

	@Test
	public void testRestClient() throws FileNotFoundException {
		final RestClient rcToCompare = ComponentFactory.get().createBasicRestClient();

		// Getting the xml result and comparing the object stored in factory
		final String xmlToString = getFile("test/input/basicTestXML/RestClientTest.xml");
		final RestClient rcToTest = (RestClient) this.xstream.fromXML(xmlToString);

		assertEquals(rcToCompare.getId(), rcToTest.getId());
	}

	@Test
	public void testSharedJSFunction() throws FileNotFoundException {
		//TODO: once the format has been finalised
	}

	@Test
	public void testSharedJSLib() throws FileNotFoundException {
		final SharedJSLib sjslibToCompare = ComponentFactory.get().createBasicSharedJSLib();

		// Getting the xml result and comparing the object stored in factory
		final String xmlToString = getFile("test/input/basicTestXML/SharedJSLibTest.xml");
		final SharedJSLib sjslibToTest = (SharedJSLib) this.xstream.fromXML(xmlToString);

		assertEquals(sjslibToCompare.getId(), sjslibToTest.getId());
	}

	@Test
	public void testTrackingScheme() throws FileNotFoundException {
		final TrackingScheme tsToCompare = ComponentFactory.get().createBasicTrackingScheme();

		// Getting the xml result and comparing the object stored in factory
		final String xmlToString = getFile("test/input/basicTestXML/TrackingSchemeTest.xml");
		final TrackingScheme tsToTest = (TrackingScheme) this.xstream.fromXML(xmlToString);

		assertEquals(tsToCompare.getId(), tsToTest.getId());
	}

	@Test
	public void testVariable() throws FileNotFoundException {
		final Variable vToCompare = ComponentFactory.get().createBasicVariable();

		// Getting the xml result and comparing the object stored in factory
		final String xmlToString = getFile("test/input/basicTestXML/VariableTest.xml");
		final Variable vToTest = (Variable) this.xstream.fromXML(xmlToString);

		assertEquals(vToCompare.getId(), vToTest.getId());
	}

	@Test
	public void testWebService() throws FileNotFoundException {
		final WebService wbToCompare = ComponentFactory.get().createBasicWebService();

		// Getting the xml result and comparing the object stored in factory
		final String xmlToString = getFile("test/input/basicTestXML/WebServiceTest.xml");
		final WebService wbToTest = (WebService) this.xstream.fromXML(xmlToString);

		assertEquals(wbToCompare.getId(), wbToTest.getId());
	}
}
