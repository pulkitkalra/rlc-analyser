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

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * RouteProperties is an element under Route and is necessary to parse in order
 * to determine the number of definitions per route. We do this by checking if
 * the element RoutePropertyDefinitions exists and then get the count of the
 * number of definitions by checking the size of the children of this element.
 */
@XStreamAlias("RouteProperties")
public class RouteProperties {

	@XStreamAlias("RoutePropertiesDefinitions")
	private RoutePropertiesDefinitions routePropertyDefinitions;

	public class RoutePropertiesDefinitions {

		@XStreamImplicit(itemFieldName = "RoutePropertiesDefinition")
		private final List<String> propertyList;

		/**
		 * @return the propertyList
		 */
		public List<String> getPropertyList() {
			return this.propertyList;
		}

		public RoutePropertiesDefinitions(final List<String> propertyList) {
			this.propertyList = propertyList;
		}

	}

	public RouteProperties() {
	}

	/**
	 * @return the routeProperties
	 */
	public RoutePropertiesDefinitions getRouteProperties() {
		return this.routePropertyDefinitions;
	}

	/**
	 * @param routeProperties the routeProperties to set
	 */
	public void setRouteProperties(final RoutePropertiesDefinitions routeProperties) {
		this.routePropertyDefinitions = routeProperties;
	}

}


