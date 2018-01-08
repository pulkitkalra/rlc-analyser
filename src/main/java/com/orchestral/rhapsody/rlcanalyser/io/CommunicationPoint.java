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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("CommPoint")
public class CommunicationPoint {

	@XStreamAlias("ID")
	private String id;

	@XStreamAlias("Type")
	private String type;

	@XStreamAlias("InputRoutes")
	@XStreamConverter(value = ListToStringXStreamConverter.class, strings = {"ID"})
	private List<String> inputRouteIds;

	@XStreamAlias("OutputRoutes")
	@XStreamConverter(value = ListToStringXStreamConverter.class, strings = {"ID"})
	private List<String> outputRouteIds;

	@XStreamAlias("Configuration")
	private List<Property> configuration;

	@XStreamAlias("GeneralProperties")
	private List<Property> generalProperties;

	@XStreamAlias("Priority")
	private int priority;

	@XStreamAlias("Mode")
	private String mode;

	@XStreamAlias("NumConnections")
	private int numberOfConnections;

	@XStreamAlias("IdleTimeout")
	private int idleTimeout;

	@XStreamAlias("Autotrack")
	private int autoTrack;

	@XStreamAlias("RetryType")
	private String retryType;

	@XStreamAlias("NumRetries")
	private int numberOfRetries;

	@XStreamAlias("NotificationRetries")
	private int notificationRetries;

	@XStreamAlias("RetryDelay")
	private int retryDelay;

	@XStreamAlias("State")
	private String state;

	@XStreamAlias("Folder")
	private String folder;

	CommunicationPoint() {
	}

	public CommunicationPoint(final String id, final String type,
	                          final List<String> inputRouteIds,
	                          final List<String> outputRouteIds,
	                          final List<Property> configuration,
	                          final List<Property> generalProperties,
	                          final int priority,
	                          final String mode,
	                          final int numberOfConnections,
	                          final int idleTimeout,
	                          final int autoTrack,
	                          final String retryType,
	                          final int numberOfRetries,
	                          final int notificationRetries,
	                          final int retryDelay,
	                          final String state,
	                          final String folder) {
		this.setId(new String(id));
		this.setType(new String(type));
		this.setInputRouteIds(new ArrayList<String>(inputRouteIds));
		this.setOutputRouteIds(new ArrayList<String>(outputRouteIds));
		this.setConfiguration(new ArrayList<Property>(configuration));
		this.setGeneralProperties(new ArrayList<Property>(generalProperties));
		this.setPriority(priority);
		this.setMode(new String(mode));
		this.setNumberOfConnections(numberOfConnections);
		this.setIdleTimeout(idleTimeout);
		this.setAutoTrack(autoTrack);
		this.setRetryType(new String(retryType));
		this.setNumberOfRetries(numberOfRetries);
		this.setNotificationRetries(notificationRetries);
		this.setRetryDelay(retryDelay);
		this.setState(new String(state));
		this.setFolder(new String(folder));
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
	 * @return the inputRouteIds
	 */
	public List<String> getInputRouteIds() {
		return this.inputRouteIds;
	}

	/**
	 * @param inputRouteIds the inputRouteIds to set
	 */
	public void setInputRouteIds(final List<String> inputRouteIds) {
		this.inputRouteIds = inputRouteIds;
	}

	/**
	 * @return the outputRouteIds
	 */
	public List<String> getOutputRouteIds() {
		return this.outputRouteIds;
	}

	/**
	 * @param outputRouteIds the outputRouteIds to set
	 */
	public void setOutputRouteIds(final List<String> outputRouteIds) {
		this.outputRouteIds = outputRouteIds;
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
	public List<Property> getGeneralProperties() {
		return this.generalProperties;
	}

	/**
	 * @param generalProperties the generalProperties to set
	 */
	public void setGeneralProperties(final List<Property> generalProperties) {
		this.generalProperties = generalProperties;
	}

	/**
	 * @return the priority
	 */
	public int getPriority() {
		return this.priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(final int priority) {
		this.priority = priority;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return this.mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(final String mode) {
		this.mode = mode;
	}

	/**
	 * @return the numberOfConnections
	 */
	public int getNumberOfConnections() {
		return this.numberOfConnections;
	}

	/**
	 * @param numberOfConnections the numberOfConnections to set
	 */
	public void setNumberOfConnections(final int numberOfConnections) {
		this.numberOfConnections = numberOfConnections;
	}

	/**
	 * @return the idleTimeout
	 */
	public int getIdleTimeout() {
		return this.idleTimeout;
	}

	/**
	 * @param idleTimeout the idleTimeout to set
	 */
	public void setIdleTimeout(final int idleTimeout) {
		this.idleTimeout = idleTimeout;
	}

	/**
	 * @return the autoTrack
	 */
	public int getAutoTrack() {
		return this.autoTrack;
	}

	/**
	 * @param autoTrack the autoTrack to set
	 */
	public void setAutoTrack(final int autoTrack) {
		this.autoTrack = autoTrack;
	}

	/**
	 * @return the retryType
	 */
	public String getRetryType() {
		return this.retryType;
	}

	/**
	 * @param retryType the retryType to set
	 */
	public void setRetryType(final String retryType) {
		this.retryType = retryType;
	}

	/**
	 * @return the numberOfRetries
	 */
	public int getNumberOfRetries() {
		return this.numberOfRetries;
	}

	/**
	 * @param numberOfRetries the numberOfRetries to set
	 */
	public void setNumberOfRetries(final int numberOfRetries) {
		this.numberOfRetries = numberOfRetries;
	}

	/**
	 * @return the notificationRetries
	 */
	public int getNotificationRetries() {
		return this.notificationRetries;
	}

	/**
	 * @param notificationRetries the notificationRetries to set
	 */
	public void setNotificationRetries(final int notificationRetries) {
		this.notificationRetries = notificationRetries;
	}

	/**
	 * @return the retryDelay
	 */
	public int getRetryDelay() {
		return this.retryDelay;
	}

	/**
	 * @param retryDelay the retryDelay to set
	 */
	public void setRetryDelay(final int retryDelay) {
		this.retryDelay = retryDelay;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return this.state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(final String state) {
		this.state = state;
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

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
}
