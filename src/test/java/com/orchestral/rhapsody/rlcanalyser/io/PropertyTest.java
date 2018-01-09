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

import org.junit.Test;

public class PropertyTest {

	public static Property property(final String name, final String value, final String type) {
		final Property property = new Property();
		property.setName(new String(name));
		property.setValue(new String(value));
		property.setType(new String(type));
		return property;
	}

	public static Property sampleProperty() {
		return PropertyTest.property("UserName", "jasons", "UserDetails");
	}

	public static Property samplePropertyWithEmptyFields() {
		return PropertyTest.property("", "", "");
	}

	public static Property samplePropertyWithNullFields() {
		return new Property();
	}

	@Test
	public void testEquals() {
		assertEquals(PropertyTest.sampleProperty(), PropertyTest.sampleProperty());
	}

	@Test
	public void testEqualsWithEmptyFields() {
		assertEquals(PropertyTest.samplePropertyWithEmptyFields(), PropertyTest.samplePropertyWithEmptyFields());
	}

	@Test
	public void testEqualsWithNullFields() {
		assertEquals(PropertyTest.samplePropertyWithNullFields(), PropertyTest.samplePropertyWithNullFields());
	}
}
