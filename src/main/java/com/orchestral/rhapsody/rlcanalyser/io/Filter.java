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
package com.orchestral.rhapsody.rlcanalyser.io;


import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Filter")
public class Filter {

	@XStreamAlias("ID")
	private String id;

	@XStreamAlias("Type")
	private String type;

	@XStreamAlias("State")
	private String state;

	@XStreamAlias("Priority")
	private int priority;

	@XStreamAlias("Configuration")
	private List<Property> configuration;

	@XStreamAlias("GeneralProperties")
	private List<Property> generalProperties;

	@XStreamAlias("Destinations")
	private List<Destination> destinations;

	@XStreamAlias("ErrorOutputID")
	private String errorOutputId;

	@XStreamAlias("NoMatchOutputID")
	private String noMatchOutputId;

	@XStreamAlias("NextDestinations")
	private String nextDestinations;

	public Filter() { }

	/**
	 * @return the id
	 */
	String getId() {
		return this.id;

	}

	/**
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * @return the state
	 */
	String getState() {
		return this.state;
	}

	/**
	 * @param state the state to set
	 */
	void setState(final String state) {
		this.state = state;
	}

	/**
	 * @return the priority
	 */
	int getPriority() {
		return this.priority;
	}

	/**
	 * @param priority the priority to set
	 */
	void setPriority(final int priority) {
		this.priority = priority;
	}

	/**
	 * @return the configuration
	 */
	public List<Property> getConfiguration() {
		return this.configuration;
	}

	/**
	 * @param configuration the configuration to set
	 */
	public void setConfiguration(final List<Property> configuration) {
		this.configuration = configuration;
	}

	/**
	 * @return the generalProperties
	 */
	List<Property> getGeneralProperties() {
		return this.generalProperties;
	}

	/**
	 * @param generalProperties the generalProperties to set
	 */
	void setGeneralProperties(final List<Property> generalProperties) {
		this.generalProperties = generalProperties;
	}

	/**
	 * @return the destinations
	 */
	List<Destination> getDestinations() {
		return this.destinations;
	}

	/**
	 * @param destinations the destinations to set
	 */
	void setDestinations(final List<Destination> destinations) {
		this.destinations = destinations;
	}

	/**
	 * @return the errorOutputId
	 */
	String getErrorOutputId() {
		return this.errorOutputId;
	}

	/**
	 * @param errorOutputId the errorOutputId to set
	 */
	void setErrorOutputId(final String errorOutputId) {
		this.errorOutputId = errorOutputId;
	}

	/**
	 * @return the noMatchOutputId
	 */
	String getNoMatchOutputId() {
		return this.noMatchOutputId;
	}

	/**
	 * @param noMatchOutputId the noMatchOutputId to set
	 */
	void setNoMatchOutputId(final String noMatchOutputId) {
		this.noMatchOutputId = noMatchOutputId;
	}

	/**
	 * @return the nextDestinations
	 */
	String getNextDestinations() {
		return this.nextDestinations;
	}

	/**
	 * @param nextDestinations the nextDestinations to set
	 */
	void setNextDestinations(final String nextDestinations) {
		this.nextDestinations = nextDestinations;
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

