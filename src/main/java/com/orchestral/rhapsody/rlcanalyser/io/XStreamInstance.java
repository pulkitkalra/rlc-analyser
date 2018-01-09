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

import com.thoughtworks.xstream.XStream;

/**
 * Class is used to initialize XStream and it's security Framework.
 *
 * Use this class to initialize any XStream object. It sets up the default
 * security, annotations and the registration of converters.
 *
 * XStream 1.4.10 requires white listing classes into which XML can be
 * unmarshalled into. The deserialization mechanism should only allow explicit
 * types. Deserializing arbitrary user-input poses a security risk.
 */
public class XStreamInstance extends XStream {
	// array of classes which are accepted by XStream.
	private static final Class<?>[] classes = new Class[] {CommunicationPoint.class, Definition.class, Destination.class, Engine.class, Filter.class,
			Locker.class, LookupTable.class, Property.class, RestClient.class, Route.class, RouteCommunicationPoint.class, RouteProperties.class,
			SharedJSFunction.class, SharedJSLib.class, TrackingScheme.class, Variable.class, WebService.class};

	public XStreamInstance() {
		super();
		super.setupDefaultSecurity(this);
		this.allowTypes(classes);
		this.processAnnotations(Engine.class);
		this.ignoreUnknownElements();
		this.registerConverter(new EnumTypeConverter());
	}
}
