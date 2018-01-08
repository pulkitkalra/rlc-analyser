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

import com.orchestral.rhapsody.rlcanalyser.io.Definition.DefinitionType;

/**
 *
 */
public class DefinitionTest {

	private XStreamInstance xstream;

	public static Definition definition(final String id) {
		final Definition def = new Definition();
		def.setId(id);
		return def;
	}

	public static Definition sampleDefinition() {
		final Definition def = DefinitionTest.definition("bbd4dfd1-d3bd-43ae-9e23-73c0d1361f30");
		return def;
	}

	public static Definition sampleDefinition2() {
		return DefinitionTest.definition("CRIS3 v1.s3d");
	}

	public static Definition sampleDestinationWithEmptyFields() {
		return DefinitionTest.definition("");
	}

	public static Definition sampleDestinationWithNullFields() {
		return new Definition();
	}

	@Before
	public void setUpXStream() {
		this.xstream = new XStreamInstance();
	}

	/**
	 * The test checks that the DefinitionType Enum is unmarshalled as expected
	 * from the XML to the Definition Object.
	 */
	@Test
	public void testDefinitionType() {
		final String id1 = "<Definition>\n <ID>CRIS3 v1.s3d</ID>\n </Definition>";
		final String id2 = "<Definition>\n <ID>CRIS3 mdf</ID>\n </Definition>";
		final String id3 = "<Definition>\n <ID>CRIS3 mdf sxd</ID>\n </Definition>";
		final String id4 = "<Definition>\n <ID>CRIS3 sxd xsd</ID>\n </Definition>";
		final String id5 = "<Definition>\n <ID>CRIS3 v1.s3</ID>\n </Definition>";

		final Object o1 = this.xstream.fromXML(id1);

		final Definition cp = (Definition) o1;

		assertEquals(cp.getId(), "CRIS3 v1.s3d");
		assertEquals(cp.getType(), DefinitionType.EDIMessageDefinition);

		final Object o2 = this.xstream.fromXML(id2);
		final Definition cp2 = (Definition) o2;

		assertEquals(cp2.getType(), DefinitionType.Mapper);

		final Object o3 = this.xstream.fromXML(id3);
		final Definition cp3 = (Definition) o3;

		assertEquals(cp3.getType(), DefinitionType.SymphoniaXMLMessageDefinition);

		final Object o4 = this.xstream.fromXML(id4);
		final Definition cp4 = (Definition) o4;

		assertEquals(cp4.getType(), DefinitionType.XMLSchemaMessageDefinition);

		final Object o5 = this.xstream.fromXML(id5);
		final Definition cp5 = (Definition) o5;

		assertEquals(cp5.getType(), DefinitionType.Unknown);
	}

	/**
	 * Test that when the ID is null and the name is not null, the id is
	 * eventually set to be the same as the name and the type can be detected.
	 */
	@Test
	public void testDefinitionWithNullID() {
		final String id1 = "<Definition>\n <ID />\n <Name>CRIS3 v1.s3d</Name>\n</Definition>";
		final Object o1 = this.xstream.fromXML(id1);

		final Definition cp = (Definition) o1;

		assertEquals(cp.getId(), "CRIS3 v1.s3d");
		assertEquals(cp.getType(), DefinitionType.EDIMessageDefinition);
	}
}
