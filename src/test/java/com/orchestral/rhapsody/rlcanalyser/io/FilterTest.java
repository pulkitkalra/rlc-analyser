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
package com.orchestral.rhapsody.rlcanalyser.io;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class FilterTest {

	private XStreamInstance xstream;

	@Before
	public void filterTestInit() {
		this.xstream = new XStreamInstance();
	}

	public static Filter filter(final String id, final String type, final String state, final int priority, final List<Property> configuration, final List<Property> generalProperties, final List<Destination> destinations, final String errorOutputId, final String noMatchOutputId, final String nextDestinations) {
		final Filter filter = new Filter();
		filter.setId(new String(id));
		filter.setType(new String(type));
		filter.setState(new String(state));
		filter.setPriority(priority);
		filter.setConfiguration(new ArrayList<Property>(configuration));
		filter.setGeneralProperties(new ArrayList<Property>(generalProperties));
		filter.setDestinations(new ArrayList<Destination>(destinations));
		filter.setErrorOutputId(new String(errorOutputId));
		filter.setNoMatchOutputId(new String(noMatchOutputId));
		filter.setNextDestinations(new String(nextDestinations));
		return filter;
	}

	public static Filter sampleFilterWithSingleFields() {
		final String id = "bbd4dfd1-d3bd-43ae-9e23-73c0d1361f29/01113204-c7d1-4d35-ac3f-dda0eed7fbdd";
		final String type = "Batcher";
		final String state = "stConfigured";
		final int priority = -1;
		final List<Property> configuration = Arrays.asList(PropertyTest.sampleProperty());
		final List<Property> generalProperties = Arrays.asList(PropertyTest.sampleProperty());
		final List<Destination> destinations = Arrays.asList(DestinationTest.sampleDestination());
		final String errorOutputId = "bbd4dfd1-d3bd-43ae-9e23-73c0d1361f29/714fbd75-b53f-452e-ba6d-ea4cd940f295";
		final String noMatchOutputId = "bbd4dfd1-d3bd-43ae-9e23-73c0d1361f29/a92248b4-9425-49bf-b24c-09d6ad30134e";
		final String nextDestinations = "&lt;NextDestinations&gt;&lt;Connector&gt;&lt;DestinationId&gt;bbd4dfd1-d3bd-43ae-9e23-73c0d1361f29/098c3c8e-fc00-4e76-97c8-5804aabc27da&lt;/DestinationId&gt;&lt;/Connector&gt;&lt;ErrorConnector&gt;bbd4dfd1-d3bd-43ae-9e23-73c0d1361f29/714fbd75-b53f-452e-ba6d-ea4cd940f295&lt;/ErrorConnector&gt;&lt;/NextDestinations&gt;";
		final String unescapedNextDestinations = StringEscapeUtils.unescapeXml(nextDestinations);
		return filter(id, type, state, priority, configuration, generalProperties, destinations, errorOutputId, noMatchOutputId, unescapedNextDestinations);
	}

	public static Filter sampleFilterWithMultipleFields() {
		final Filter filter = sampleFilterWithSingleFields();
		filter.setConfiguration(Arrays.asList(PropertyTest.sampleProperty(), PropertyTest.samplePropertyWithEmptyFields()));
		filter.setGeneralProperties(Arrays.asList(PropertyTest.sampleProperty(), PropertyTest.samplePropertyWithNullFields()));
		filter.setDestinations(Arrays.asList(DestinationTest.sampleDestination(), DestinationTest.sampleDestinationWithEmptyFields()));
		return filter;
	}

	public static Filter sampleFilterWithEmptyFields() {
		final String id = "";
		final String type = "";
		final String state = "";
		final int priority = -1;
		final List<Property> configuration = new ArrayList<Property>();
		final List<Property> generalProperties = new ArrayList<Property>();
		final List<Destination> destinations = new ArrayList<Destination>();
		final String errorOutputId = "";
		final String noMatchOutputId = "";
		final String nextDestinations = "";
		return filter(id, type, state, priority, configuration, generalProperties, destinations, errorOutputId, noMatchOutputId, nextDestinations);
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

	public static Filter sampleFilterWithNullFields() {
		return new Filter();
	}

	@Test
	public void testFilterWithSingleFields() throws FileNotFoundException{
		final Object testObject = this.xstream.fromXML(getFile("test/input/basicTestXML/Filter/FilterSingleFieldTest.xml"));
		final Filter filterToTest = (Filter)testObject;

		// Tests if objects generated are equal
		assertEquals(filterToTest, sampleFilterWithSingleFields());
	}

	@Test
	public void testFilterWithMultipleFields() throws FileNotFoundException {
		final Object testObject = this.xstream
				.fromXML(getFile("test/input/basicTestXML/Filter/FilterMultipleFieldTest.xml"));
		final Filter filterToTest = (Filter) testObject;

		// Tests if objects generated are equal
		assertEquals(filterToTest, sampleFilterWithMultipleFields());
	}

	@Test
	public void testFilterWithEmptyFields() throws FileNotFoundException {
		final Object testObject = this.xstream.fromXML(getFile("test/input/basicTestXML/Filter/FilterEmptyFieldTest.xml"));
		final Filter filterToTest = (Filter)testObject;

		// Tests if objects generated are equal
		assertEquals(filterToTest, sampleFilterWithEmptyFields());
	}

	@Test
	public void testFilterWithNullFields() throws FileNotFoundException {
		final Object testObject = this.xstream.fromXML(getFile("test/input/basicTestXML/Filter/FilterNullFieldTest.xml"));
		final Filter filterToTest = (Filter)testObject;

		// Tests if objects generated are equal
		assertEquals(filterToTest, sampleFilterWithNullFields());
	}

	@Test
	public void testFilterUnexpectedParameterWithSingleFields() throws FileNotFoundException {
		final Object testObject = this.xstream
				.fromXML(getFile("test/input/basicTestXML/Filter/FilterUnexpectedFieldTest.xml"));
		final Filter filterToTest = (Filter)testObject;

		// Tests if objects generated are equal
		// This test should see that the object generated is the generic single field object - ignores the itemShouldNotBeHere field
		assertEquals(filterToTest, sampleFilterWithSingleFields());
	}

	@Test
	public void testEqualsFilterWithSingleFields() {
		assertEquals(sampleFilterWithSingleFields(), sampleFilterWithSingleFields());
	}

	@Test
	public void testEqualsFilterWithMultipleFields() {
		assertEquals(sampleFilterWithMultipleFields(), sampleFilterWithMultipleFields());
	}

	@Test
	public void testEqualsFilterWithEmptyFields() {
		assertEquals(sampleFilterWithEmptyFields(), sampleFilterWithEmptyFields());
	}

	@Test
	public void testEqualsFilterWithNullFields() {
		assertEquals(sampleFilterWithNullFields(), sampleFilterWithNullFields());
	}

}
