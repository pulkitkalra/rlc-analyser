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
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import com.orchestral.rhapsody.rlcanalyser.io.RouteProperties.RoutePropertiesDefinitions;

public class RouteTest {

	private XStreamInstance xstream;

	public static Route route(	final String id,
	                          	final String folder,
	                          	final List<Filter> filters,
	                          	final List<Property> generalProperties,
	                          	final int priority,
	                          	final String state) {
		final Route route = new Route();
		route.setId(new String(id));
		route.setFolder(new String(folder));
		route.setFilters(new ArrayList<Filter>(filters));
		route.setGeneralProperties(new ArrayList<Property>(generalProperties));
		route.setPriority(priority);
		route.setState(new String(state));
		return route;
	}

	public static Route sampleRouteWithSingleFields() {
		final String id = "bbd4dfd1-d3bd-43ae-9e23-73c0d1361f29";
		final String folder = "A-Team;Millennium;Billing;Pro";
		final List<Filter> filters = Arrays.asList(FilterTest.sampleFilterWithSingleFields());
		final List<Property> generalProperties = Arrays.asList(PropertyTest.sampleProperty());
		final int priority = 2;
		final String state = "stRunning";
		return RouteTest.route(id, folder, filters, generalProperties, priority, state);
	}

	public static Route sampleRouteWithMultipleFields() {
		final Route route = RouteTest.sampleRouteWithSingleFields();
		route.setFilters(Arrays.asList(FilterTest.sampleFilterWithSingleFields(), FilterTest.sampleFilterWithMultipleFields()));
		route.setGeneralProperties(Arrays.asList(PropertyTest.sampleProperty(), PropertyTest.samplePropertyWithEmptyFields()));
		return route;
	}

	/**
	 * Helper method to add one or more (three) definitions per route.
	 *
	 * @param multipleDefinitions when true, a route is created with multiple
	 *            definitions.
	 * @return
	 */
	public static Route sampleRouteWithDefinitions(final boolean multipleDefinitions) {
		final Route route = RouteTest.sampleRouteWithMultipleFields();
		final RouteProperties properties = new RouteProperties();
		// mocking a single definition with an empty Definition
		if (multipleDefinitions) {
			final RoutePropertiesDefinitions defProperties = properties.new RoutePropertiesDefinitions(Arrays.asList("", "", ""));
			properties.setRouteProperties(defProperties);
		} else {
			final RoutePropertiesDefinitions defProperties = properties.new RoutePropertiesDefinitions(Arrays.asList(""));
			properties.setRouteProperties(defProperties);
		}
		route.setRoutePropertyDefinitions(properties);
		return route;
	}

	public static Route sampleRouteWithEmptyFields() {
		final String id = "";
		final String folder = "";
		final List<Filter> filters = new ArrayList<Filter>();
		final List<Property> generalProperties = new ArrayList<Property>();
		final int priority = 0;
		final String state = "";
		return RouteTest.route(id, folder, filters, generalProperties, priority, state);
	}

	public static Route sampleRouteWithNullFields() {
		return new Route();
	}

	// Helper function that opens files and returns a string to be parsed through xstream
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

	@Before
	public void routeTestInit() {
		// Initialise the xstream object - set up before executing the subsequent tests
		this.xstream = new XStreamInstance();
	}

	/**
	 * Test that no definition exists and the list of defintion properties
	 * doesn't exists and is null.
	 */
	@Test
	public void testNoDefinition() {
		final Route routeToTest = sampleRouteWithEmptyFields();
		final String xml = this.xstream.toXML(routeToTest);
		final Route resultRout = (Route) this.xstream.fromXML(xml);
		assertNull(resultRout.getRoutePropertyDefinitions());
	}

	/**
	 * Test adding of one definition.
	 */
	@Test
	public void testSingleDefinitionPerRoute() {
		final Route routeToTest = sampleRouteWithDefinitions(false);
		final String xml = this.xstream.toXML(routeToTest);
		final Route resultRout = (Route) this.xstream.fromXML(xml);
		final int numberOfDefinitions = resultRout.getRoutePropertyDefinitions().getRouteProperties().getPropertyList().size();
		assertEquals(numberOfDefinitions, 1);
	}

	/**
	 * Test adding of multiple definitions.
	 */
	@Test
	public void testMultipleDefinitionsPerRoute() {
		final Route routeToTest = sampleRouteWithDefinitions(true);
		final String xml = this.xstream.toXML(routeToTest);
		final Route resultRout = (Route) this.xstream.fromXML(xml);
		final int numberOfDefinitions = resultRout.getRoutePropertyDefinitions().getRouteProperties().getPropertyList().size();
		assertEquals(numberOfDefinitions, 3);
	}

	@Test
	public void testMarshallingWithSingleFields() throws FileNotFoundException {
		final Object testObject = this.xstream.fromXML(getFile("test/input/basicTestXML/Route/RouteSingleFieldTest.xml"));
		final Route routeToTest = (Route)testObject;

		// Tests if objects generated are equal
		assertEquals(routeToTest, sampleRouteWithSingleFields());
	}

	@Test
	public void testMarshallingWithMultipleFields() throws FileNotFoundException {
		final Object testObject = this.xstream.fromXML(getFile("test/input/basicTestXML/Route/RouteMultipleFieldTest.xml"));
		final Route routeToTest = (Route)testObject;

		// Tests if objects generated are equal
		assertEquals(routeToTest, sampleRouteWithMultipleFields());
	}

	@Test
	public void testMarshallingWithEmptyFields() throws FileNotFoundException {
		final Object testObject = this.xstream.fromXML(getFile("test/input/basicTestXML/Route/RouteEmptyFieldTest.xml"));
		final Route routeToTest = (Route)testObject;

		// Tests if objects generated are equal
		assertEquals(routeToTest, sampleRouteWithEmptyFields());
	}

	@Test
	public void testMarshallingWithNullFields() throws FileNotFoundException {
		final Object testObject = this.xstream.fromXML(getFile("test/input/basicTestXML/Route/RouteNullFieldTest.xml"));
		final Route routeToTest = (Route)testObject;

		// Tests if objects generated are equal
		assertEquals(routeToTest, sampleRouteWithNullFields());
	}

	@Test
	public void testUnexpectedField() throws FileNotFoundException {
		final Object testObject = this.xstream
				.fromXML(getFile("test/input/basicTestXML/Route/RouteUnexpectedFieldTest.xml"));
		final Route routeToTest = (Route)testObject;

		// Tests if objects generated are equal
		assertEquals(routeToTest, sampleRouteWithSingleFields());
	}

	@Test
	public void testInputCommPointField() throws FileNotFoundException {
		final Object testObject = this.xstream.fromXML(getFile("test/input/basicTestXML/Route/RouteInputCommTest.xml"));
		final Route routeToTest = (Route) testObject;

		// Test for correct ID placement in the list of inputCommPoints
		assertEquals(routeToTest.getInputCommPoints().get(0).getId(), "b1ea5940-1e5f-443f-8504-182dd00bda16");
		assertEquals(routeToTest.getInputCommPoints().get(1).getId(), "c4853633-2c98-4d8e-9b2f-7f312a2036ea");

		// Test for correct list implementations in the first filter list
		assertEquals(routeToTest.getInputCommPoints().get(1).getfirstFilters().get(0),
				"1cb57484-47e2-48a1-a84a-ff6cec2c3a06/619f5640-d8b0-4e52-b098-c4f3108b041a");
		assertEquals(routeToTest.getInputCommPoints().get(1).getfirstFilters().get(1),
				"1cb57484-47e2-48a1-a84a-ff6cec2c3a06/a1b5b039-5632-4ea7-9143-54471b09f075");
	}

	@Test
	public void testOutputCommPointField() throws FileNotFoundException {
		final Object testObject = this.xstream.fromXML(getFile("test/input/basicTestXML/Route/RouteOutputCommTest.xml"));
		final Route routeToTest = (Route) testObject;

		// Compares a list of ID generated under the OutputCommPoint header
		assertEquals(routeToTest.getOutputCommPoints().get(0), "b580930b-d402-4b02-bffe-5eb291c96f2b");
		assertEquals(routeToTest.getOutputCommPoints().get(1), "fbca924d-771e-4217-9739-74e188014686");
		assertEquals(routeToTest.getOutputCommPoints().get(2), "4ee16606-a1bd-4f1f-95ed-7b74989b6004");
	}
}
