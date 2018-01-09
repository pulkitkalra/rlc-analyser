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

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("InputCommPoint")
public class RouteCommunicationPoint {
	// Class will be used by inputCommPoints and outputCommPoints - eventually

	@XStreamAlias("ID")
	private String id;

	@XStreamAlias("FirstFilters")
	@XStreamConverter(value = ListToStringXStreamConverter.class, strings = { "ID" })
	private List<String> firstFilters;

	/**
	 * Constructor method for this class
	 */
	public RouteCommunicationPoint() {
	}

	/**
	 * Get the ID for this class
	 *
	 * @return id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Setter function for ID
	 *
	 * @param id
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * Getter for the list of IDs for FirstFilter object
	 *
	 * @return list of IDs
	 */
	public List<String> getfirstFilters() {
		return this.firstFilters;
	}

	/**
	 * Setter for firstFilter parameter
	 *
	 * @param firstFilter
	 */
	public void setFirstFilters(final List<String> firstFilter) {
		this.firstFilters = firstFilter;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);

	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
}
