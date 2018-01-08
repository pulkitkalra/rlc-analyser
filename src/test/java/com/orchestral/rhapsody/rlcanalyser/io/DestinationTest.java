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

public class DestinationTest {

	public static Destination destination(final String id) {
		final Destination destination = new Destination();
		destination.setId(new String(id));
		return destination;
	}

	public static Destination sampleDestination() {
		return DestinationTest.destination("bbd4dfd1-d3bd-43ae-9e23-73c0d1361f29/098c3c8e-fc00-4e76-97c8-5804aabc27da");
	}

	public static Destination sampleDestinationWithEmptyFields() {
		return DestinationTest.destination("");
	}

	public static Destination sampleDestinationWithNullFields() {
		return new Destination();
	}

	@Test
	public void testEquals() {
		assertEquals(DestinationTest.sampleDestination(), DestinationTest.sampleDestination());
	}

	@Test
	public void testEqualsWithEmptyFields() {
		assertEquals(DestinationTest.sampleDestinationWithEmptyFields(),
				DestinationTest.sampleDestinationWithEmptyFields());
	}

	@Test
	public void testEqualsWithNullFields() {
		assertEquals(DestinationTest.sampleDestinationWithNullFields(),
				DestinationTest.sampleDestinationWithNullFields());
	}
}
