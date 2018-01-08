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
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("Route")
public class Route {

	@XStreamAlias("ID")
	private String id;

	@XStreamAlias("Folder")
	private String folder;

	@XStreamAlias("Filters")
	private List<Filter> filters;

	@XStreamAlias("InputCommPoints")
	private List<RouteCommunicationPoint> inputCommPoints;

	@XStreamAlias("OutputCommPoints")
	@XStreamConverter(value = ListToStringXStreamConverter.class, strings = { "ID" })
	private List<String> outputCommPoints;

	@XStreamAlias("Priority")
	private int priority;

	@XStreamAlias("State")
	private String state;

	@XStreamAlias("GeneralProperties")
	private List<Property> generalProperties;

	@XStreamAlias("RouteProperties")
	private RouteProperties routePropertyDefinitions;

	public Route() {
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the folder
	 */
	public String getFolder() {
		return this.folder;
	}

	/**
	 * @param folder the folder to set
	 */
	public void setFolder(final String folder) {
		this.folder = folder;
	}

	/**
	 * @return the filters
	 */
	public List<Filter> getFilters() {
		return this.filters;
	}

	/**
	 * @param filters the filters to set
	 */
	public void setFilters(final List<Filter> filters) {
		this.filters = filters;
	}

	/**
	 * @return the input comm point list
	 */
	public List<RouteCommunicationPoint> getInputCommPoints() {
		return this.inputCommPoints;
	}

	/**
	 * @param inputCommPoints
	 */
	public void setInputCommPoints(final List<RouteCommunicationPoint> inputCommPoints) {
		this.inputCommPoints = inputCommPoints;
	}

	/**
	 * @return String list of IDs registered with the output comm points list
	 */
	public List<String> getOutputCommPoints() {
		return this.outputCommPoints;
	}

	/**
	 * @param outputCommPoints
	 */
	public void setOutputCommPoints(final List<String> outputCommPoints) {
		this.outputCommPoints = outputCommPoints;
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

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	/**
	 * @return the routePropertyDefinitions
	 */
	public RouteProperties getRoutePropertyDefinitions() {
		return this.routePropertyDefinitions;
	}

	/**
	 * @param routePropertyDefinitions the routePropertyDefinitions to set
	 */
	public void setRoutePropertyDefinitions(final RouteProperties routePropertyDefinitions) {
		this.routePropertyDefinitions = routePropertyDefinitions;
	}
}
