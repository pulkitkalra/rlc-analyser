/*
 * Copyright (c) Orchestral Developments Ltd and the Orion Health group of
 * companies (2001 - 2017).
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
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.custommonkey.xmlunit.XMLTestCase;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class CommunicationPointTest {

	private XStreamInstance xstream;
	private XMLTestCase xmlUnit;

	public static CommunicationPoint communicationPoint(final String id,
	                                                    final String type,
	                                                    final List<String> inputRouteIds,
	                                                    final List<String> outputRouteIds,
	                                                    final List<Property> configuration,
	                                                    final List<Property> generalProperties,
	                                                    final int priority,
	                                                    final String mode,
	                                                    final int numberOfConnections,
	                                                    final int idleTimeout,
	                                                    final int autoTrack,
	                                                    final String retryType,
	                                                    final int numberOfRetries,
	                                                    final int notificationRetries,
	                                                    final int retryDelay,
	                                                    final String state,
	                                                    final String folder) {
		final CommunicationPoint communicationPoint = new CommunicationPoint();
		communicationPoint.setId(new String(id));
		communicationPoint.setType(new String(type));
		communicationPoint.setInputRouteIds(new ArrayList<String>(inputRouteIds));
		communicationPoint.setOutputRouteIds(new ArrayList<String>(outputRouteIds));
		communicationPoint.setConfiguration(new ArrayList<Property>(configuration));
		communicationPoint.setGeneralProperties(new ArrayList<Property>(generalProperties));
		communicationPoint.setPriority(priority);
		communicationPoint.setMode(new String(mode));
		communicationPoint.setNumberOfConnections(numberOfConnections);
		communicationPoint.setIdleTimeout(idleTimeout);
		communicationPoint.setAutoTrack(autoTrack);
		communicationPoint.setRetryType(new String(retryType));
		communicationPoint.setNumberOfRetries(numberOfRetries);
		communicationPoint.setNotificationRetries(notificationRetries);
		communicationPoint.setRetryDelay(retryDelay);
		communicationPoint.setState(new String(state));
		communicationPoint.setFolder(new String(folder));
		return communicationPoint;
	}

	public static CommunicationPoint sampleCommunicationPointWithSingleFields() {
		final String id = "4b185350-aec9-418e-90ce-f0d0b3aa29ba";
		final String type = "TCPServer";
		final List<String> inputRouteIds = Arrays.asList("bbd4dfd1-d3bd-43ae-9e23-73c0d1361f29");
		final List<String> outputRouteIds = Arrays.asList("bbd4dfd1-d3bd-43ae-9e23-73c0d1361f29");
		final List<Property> configuration = Arrays.asList(PropertyTest.sampleProperty());
		final List<Property> generalProperties = Arrays.asList(PropertyTest.sampleProperty());
		final int priority = -1;
		final String mode = "cpmBidirectional";
		final int numberOfConnections = 1;
		final int idleTimeout = 0;
		final int autoTrack = 0;
		final String retryType = "rtImmediate";
		final int numberOfRetries = 5;
		final int notificationRetries = 5;
		final int retryDelay = 5000;
		final String state = "stRunning";
		final String folder = "A-Team;Millennium;Billing;Pro";
		return CommunicationPointTest.communicationPoint(	id,
		                                                 	type,
		                                                 	inputRouteIds,
		                                                 	outputRouteIds,
		                                                 	configuration,
		                                                 	generalProperties,
		                                                 	priority,
		                                                 	mode,
		                                                 	numberOfConnections,
		                                                 	idleTimeout,
		                                                 	autoTrack,
		                                                 	retryType,
		                                                 	numberOfRetries,
		                                                 	notificationRetries,
		                                                 	retryDelay,
		                                                 	state,
		                                                 	folder);
	}

	public static CommunicationPoint sampleCommunicationPointWithMultipleFields() {
		final CommunicationPoint communicationPoint = sampleCommunicationPointWithSingleFields();
		communicationPoint.setInputRouteIds(Arrays.asList("bbd4dfd1-d3bd-43ae-9e23-73c0d1361f29", "bbd4dfd1-d3bd-43ae-9e23-73c0d1361f30"));
		communicationPoint.setInputRouteIds(Arrays.asList("bbd4dfd1-d3bd-43ae-9e23-73c0d1361f29", "bbd4dfd1-d3bd-43ae-9e23-73c0d1361f30"));
		communicationPoint.setConfiguration(Arrays.asList(PropertyTest.sampleProperty(), PropertyTest.samplePropertyWithEmptyFields()));
		communicationPoint.setGeneralProperties(Arrays.asList(PropertyTest.sampleProperty(), PropertyTest.samplePropertyWithNullFields()));
		return communicationPoint;
	}

	public static CommunicationPoint sampleCommunicationPointWithEmptyFields() {
		final String id = "";
		final String type = "";
		final List<String> inputRouteIds = new ArrayList<String>();
		final List<String> outputRouteIds = new ArrayList<String>();
		final List<Property> configuration = new ArrayList<Property>();
		final List<Property> generalProperties = new ArrayList<Property>();
		final int priority = 0;
		final String mode = "";
		final int numberOfConnections = 0;
		final int idleTimeout = 0;
		final int autoTrack = 0;
		final String retryType = "";
		final int numberOfRetries = 0;
		final int notificationRetries = 0;
		final int retryDelay = 0;
		final String state = "";
		final String folder = "";
		return CommunicationPointTest.communicationPoint(	id,
		                                                 	type,
		                                                 	inputRouteIds,
		                                                 	outputRouteIds,
		                                                 	configuration,
		                                                 	generalProperties,
		                                                 	priority,
		                                                 	mode,
		                                                 	numberOfConnections,
		                                                 	idleTimeout,
		                                                 	autoTrack,
		                                                 	retryType,
		                                                 	numberOfRetries,
		                                                 	notificationRetries,
		                                                 	retryDelay,
		                                                 	state,
		                                                 	folder);
	}

	public static CommunicationPoint sampleCommunicationPointWithNullFields() {
		return new CommunicationPoint();
	}

	@Before
	public void setUpXStream() {
		this.xstream = new XStreamInstance();
		this.xmlUnit = new XMLTestCase() {
		};
	}

	/**
	 * Test that XStream annotations produce XML schema which is unmarshalled as
	 * expected. The communication Point provided only has a single
	 * sub-attribute.
	 */
	@Test
	public void testEqualsWithSingleFields() {

		final String xml = this.xstream.toXML(CommunicationPointTest.sampleCommunicationPointWithSingleFields());

		final Object o1 = this.xstream.fromXML(xml);
		final CommunicationPoint cp = (CommunicationPoint) o1;
		try {
			// compare xml
			this.xmlUnit.assertXMLEqual("xml was not equal", xml, this.xstream.toXML(cp));
		} catch (final Exception e) {
			fail("XMLUnit test case failed");
			e.printStackTrace();
		}
		assertEquals(xml, this.xstream.toXML(cp));
	}

	/**
	 * Test that XStream annotations produce XML schema which is unmarshalled as
	 * expected. The communication Point provided only has multiple
	 * sub-attributes.
	 */
	@Test
	public void testEqualsWithMultipleFields() {

		this.xstream.processAnnotations(CommunicationPoint.class);

		final String xml = this.xstream.toXML(CommunicationPointTest.sampleCommunicationPointWithMultipleFields());
		final Object o1 = this.xstream.fromXML(xml);
		final CommunicationPoint cp = (CommunicationPoint) o1;

		assertEquals(cp, CommunicationPointTest.sampleCommunicationPointWithMultipleFields());
	}

	/**
	 * Test that XStream annotations produce XML schema which is unmarshalled as
	 * expected. The communication Point provided is a custom one through a test
	 * file.
	 */
	@Test
	public void testUnmarshallwithMultipleFields() {

		this.xstream.processAnnotations(CommunicationPoint.class);
		Scanner scanner = null;
		String text = "";
		try {
			scanner = new Scanner(new File("test_files/commPoint.txt"), "UTF-8");
			text = scanner.useDelimiter("\\A").next();
		} catch (final FileNotFoundException e) {
			fail("Scanner did not find the file");
			e.printStackTrace();
		} finally {
			scanner.close();
		}

		final Object o1 = this.xstream.fromXML(text);

		final CommunicationPoint cp = (CommunicationPoint) o1;
		assertEquals(cp, CommunicationPointTest.sampleCommunicationPointWithMultipleFields());
	}

	/**
	 * Test whether unmarshaling with XStream works even there are extra leaf
	 * and branch nodes that are unknown to the defined classes. NOTE: need to
	 * use xstream.ingnoreUnknownElements()
	 * 
	 * @throws IOException
	 * @throws SAXException
	 */
	@Test
	public void testUnmarshallwithUnknownFields() throws SAXException, IOException {

		this.xstream.ignoreUnknownElements();
		this.xstream.processAnnotations(CommunicationPoint.class);

		Scanner scanner = null;
		String text = "";
		try {
			scanner = new Scanner(new File("test_files/commPoint2.txt"), "UTF-8");
			text = scanner.useDelimiter("\\A").next();
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}

		final Object o1 = this.xstream.fromXML(text);

		final CommunicationPoint cp = (CommunicationPoint) o1;

		String expectedXml = "";
		try {
			scanner = new Scanner(new File("test_files/commPoint.txt"), "UTF-8");
			expectedXml = scanner.useDelimiter("\\A").next();
		} catch (final FileNotFoundException e) {
			fail("Scanner did not find the file");
			e.printStackTrace();
		} finally {
			scanner.close();
		}

		this.xmlUnit.assertXMLEqual("XML Unit test failed. XMLs are not equivalent", expectedXml, this.xstream.toXML(cp));

	}
}
