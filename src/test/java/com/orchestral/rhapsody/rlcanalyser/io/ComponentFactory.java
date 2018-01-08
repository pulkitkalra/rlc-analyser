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

public class ComponentFactory {
	private static final ComponentFactory ONLY_ME = new ComponentFactory();

	private final String idA = "7f5625f2-2786-4c8a-83b6-2144bc91f726";
	private final String idB = "bedf2fc9-5b50-456c-a76e-7e7bee41a11d";
	private final String idC = "925a4616-06ae-43ea-b00a-e3b57aa9ff8b";
	private final String idD = "bedf2fc9-5b50-456c-a76e-7e7bee41a11d";

	private ComponentFactory() {
	}

	public static ComponentFactory get() {
		return ONLY_ME;
	}

	public Locker createBasicLocker() {
		final Locker locker = new Locker();
		locker.setId(this.idA);
		return locker;
	}

	public LookupTable createBasicLookUpTable() {
		final LookupTable lut = new LookupTable();
		lut.setId(this.idB);
		return lut;
	}

	public RestClient createBasicRestClient() {
		final RestClient rc = new RestClient();
		rc.setId(this.idC);
		return rc;
	}

	public SharedJSFunction createBasicSharedJSFunction() {
		final SharedJSFunction sjsfunc = new SharedJSFunction();
		sjsfunc.setId(this.idD);
		return sjsfunc;
	}

	public SharedJSLib createBasicSharedJSLib() {
		final SharedJSLib sjslib = new SharedJSLib();
		sjslib.setId(this.idA);
		return sjslib;
	}

	public TrackingScheme createBasicTrackingScheme() {
		final TrackingScheme ts = new TrackingScheme();
		ts.setId(this.idB);
		return ts;
	}

	public Variable createBasicVariable() {
		final Variable v = new Variable();
		v.setId(this.idC);
		return v;
	}

	public WebService createBasicWebService() {
		final WebService ws = new WebService();
		ws.setId(this.idD);
		return ws;
	}
}
