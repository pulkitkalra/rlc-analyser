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
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("Engine")
public class Engine {
	@XStreamOmitField
	private String fileName;

	@XStreamAlias("FileVersion")
	@XStreamAsAttribute
	private int rlcVersion;

	@XStreamAlias("CompatibleDownToFileVersion")
	@XStreamAsAttribute
	private int compatibleRlcVersion;

	@XStreamAlias("Lockers")
	private List<Locker> lockers;

	@XStreamAlias("CommPoints")
	private List<CommunicationPoint> communicationPoints;

	@XStreamAlias("Routes")
	private List<Route> routes;

	@XStreamAlias("Definitions")
	private List<Definition> definitions;

	@XStreamAlias("Variables")
	private List<Variable> variables;

	@XStreamAlias("WebServices")
	private List<WebService> webServices;

	@XStreamAlias("RestClients")
	private List<RestClient> restClients;

	@XStreamAlias("LookupTables")
	private List<LookupTable> lookupTables;

	@XStreamAlias("TrackingSchemes")
	private List<TrackingScheme> trackingSchemes;

	@XStreamAlias("SharedJavaScriptLibraries")
	private List<SharedJSLib> sharedJSLibs;

	// TODO: @XStreamAlias("SharedJSFunctions")
	private List<SharedJSFunction> sharedJSFunctions;


	/**
	 *
	 */
	Engine() { }

	/**
	 * @return the rlcVersion
	 */
	int getRlcVersion() {
		return this.rlcVersion;
	}

	/**
	 * @param rlcVersion the rlcVersion to set
	 */
	void setRlcVersion(final int rlcVersion) {
		this.rlcVersion = rlcVersion;
	}

	/**
	 * @return the compatibleRlcVersion
	 */
	int getCompatibleRlcVersion() {
		return this.compatibleRlcVersion;
	}

	/**
	 * @param compatibleRlcVersion the compatibleRlcVersion to set
	 */
	void setCompatibleRlcVersion(final int compatibleRlcVersion) {
		this.compatibleRlcVersion = compatibleRlcVersion;
	}

	/**
	 * @return the lockers
	 */
	public List<Locker> getLockers() {
		return this.lockers;
	}

	/**
	 * @param lockers the lockers to set
	 */
	void setLockers(final List<Locker> lockers) {
		this.lockers = lockers;
	}

	/**
	 * @return the communicationPoints
	 */
	public List<CommunicationPoint> getCommunicationPoints() {
		return this.communicationPoints;
	}

	/**
	 * @param communicationPoints the communicationPoints to set
	 */
	public void setCommunicationPoints(final List<CommunicationPoint> communicationPoints) {
		this.communicationPoints = communicationPoints;
	}

	/**
	 * @return the routes
	 */
	public List<Route> getRoutes() {
		return this.routes;
	}

	/**
	 * @param routes the routes to set
	 */
	void setRoutes(final List<Route> routes) {
		this.routes = routes;
	}

	/**
	 * @return the definitions
	 */
	public List<Definition> getDefinitions() {
		return this.definitions;
	}

	/**
	 * @param definitions the definitions to set
	 */
	public void setDefinitions(final List<Definition> definitions) {
		this.definitions = definitions;
	}

	/**
	 * @return the variables
	 */
	public List<Variable> getVariables() {
		return this.variables;
	}

	/**
	 * @param variables the variables to set
	 */
	public void setVariables(final List<Variable> variables) {
		this.variables = variables;
	}

	/**
	 * @return the webServices
	 */
	public List<WebService> getWebServices() {
		return this.webServices;
	}

	/**
	 * @param webServices the webServices to set
	 */
	public void setWebServices(final List<WebService> webServices) {
		this.webServices = webServices;
	}

	/**
	 * @return the restClients
	 */
	public List<RestClient> getRestClients() {
		return this.restClients;
	}

	/**
	 * @param restClients the restClients to set
	 */
	public void setRestClients(final List<RestClient> restClients) {
		this.restClients = restClients;
	}

	/**
	 * @return the lookupTables
	 */
	public List<LookupTable> getLookupTables() {
		return this.lookupTables;
	}

	/**
	 * @param lookupTables the lookupTables to set
	 */
	public void setLookupTables(final List<LookupTable> lookupTables) {
		this.lookupTables = lookupTables;
	}

	/**
	 * @return the trackingSchemes
	 */
	public List<TrackingScheme> getTrackingSchemes() {
		return this.trackingSchemes;
	}

	/**
	 * @param trackingSchemes the trackingSchemes to set
	 */
	public void setTrackingSchemes(final List<TrackingScheme> trackingSchemes) {
		this.trackingSchemes = trackingSchemes;
	}

	/**
	 * @return the sharedJSLibs
	 */
	public List<SharedJSLib> getSharedJSLibs() {
		return this.sharedJSLibs;
	}

	/**
	 * @param sharedJSLibs the sharedJSLibs to set
	 */
	public void setSharedJSLibs(final List<SharedJSLib> sharedJSLibs) {
		this.sharedJSLibs = sharedJSLibs;
	}

	/**
	 * @return the sharedJSFunctions
	 */
	public List<SharedJSFunction> getSharedJSFunctions() {
		return this.sharedJSFunctions;
	}

	/**
	 * @param sharedJSFunctions the sharedJSFunctions to set
	 */
	public void setSharedJSFunctions(final List<SharedJSFunction> sharedJSFunctions) {
		this.sharedJSFunctions = sharedJSFunctions;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(final String fileName) {
		this.fileName = fileName;
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
