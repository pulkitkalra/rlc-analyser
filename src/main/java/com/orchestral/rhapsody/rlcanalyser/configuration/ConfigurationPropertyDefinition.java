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
package com.orchestral.rhapsody.rlcanalyser.configuration;

public class ConfigurationPropertyDefinition {
	
	private final String name;
	
	private final String type;
	
	private final String defaultValue;

	public ConfigurationPropertyDefinition(final String name, final String type, final String defaultValue) {
		assert name!= null;
		assert type != null;
		
		this.name = name;
		this.type = type;
		this.defaultValue = defaultValue;
	}
	
	public ConfigurationPropertyDefinition(final String name, final String type) {
		this(name, type, null);
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getDefaultValue() {
		return defaultValue;
	}
}
