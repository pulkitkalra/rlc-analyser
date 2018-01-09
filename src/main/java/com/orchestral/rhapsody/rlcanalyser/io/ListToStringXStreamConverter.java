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

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * This Converter can be used whenever a List of String type needs to be
 * marshaled in XML with a tag of <ID> (and not <string> which is the default).
 * The un-marshal method is responsible for converting <ID> tags into a List
 * <String>.
 *
 * Credit for marshal method:
 * https://stackoverflow.com/questions/1791178/customising-serialisation-of-java
 * -collections-using-xstream From wandi.darko
 */
public class ListToStringXStreamConverter implements Converter {

	private final String alias;

	public ListToStringXStreamConverter(final String alias) {
		super();
		this.alias = alias;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(final Class type) {
		return true;
	}

	@Override
	public void marshal(final Object source, final HierarchicalStreamWriter writer, final MarshallingContext context) {

		@SuppressWarnings("unchecked")
		final List<String> list = (List<String>) source;

		for (final String string : list) {
			writer.startNode(this.alias);
			writer.setValue(string);
			writer.endNode();
		}

	}

	/**
	 * Method is used unmarshal XML of any type of component which contains a
	 * List<String> where the string elements would appear within <ID> tags.
	 * E.g. the List<String> of inputRoutes and outputRoutes in
	 * CommunicationPoint.
	 *
	 * @return a List<String> of all IDs.
	 */
	@Override
	public Object unmarshal(final HierarchicalStreamReader reader, final UnmarshallingContext context) {
		final List<String> IdList = new ArrayList<String>();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			final String nodeName = reader.getNodeName();
			final String value = reader.getValue();
			if (nodeName.equalsIgnoreCase("ID")) {
				IdList.add(value);
			}
			reader.moveUp();
		}
		return IdList;
	}

}
