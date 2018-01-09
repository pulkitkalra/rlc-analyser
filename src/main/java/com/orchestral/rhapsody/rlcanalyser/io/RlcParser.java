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

import java.io.InputStream;

import org.apache.log4j.Logger;

public class RlcParser {

	private final static Logger logger = Logger.getLogger(RlcParser.class);

	private final XStreamInstance xStream;

	/**
	 * Initializing XStream. Configured to ignore unknown elements and to
	 * process annotations. All custom converters must be registered here.
	 * XStream security (white list) is also set-up here.
	 */
	public RlcParser() {
		this.xStream = new XStreamInstance();
	}

	public Engine parse(final InputStream input) {
		final Object result = this.xStream.fromXML(input);
		if (result instanceof Engine) {
			return (Engine) result;
		} else {
			logger.error("Unmarshalled XML was not recognized as an Engine. Check XML to ensure that it starts with an <Engine> tag.");
			return null;
		}
	}
}
