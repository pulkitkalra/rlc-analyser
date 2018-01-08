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

import com.orchestral.rhapsody.rlcanalyser.io.Definition.DefinitionType;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;


/**
 * This Converter is used to deserialize a Definition and set it's type. The
 * converter is required since the type of the Definition must be set using the
 * id of the definition.
 *
 */
public class EnumTypeConverter implements Converter {


	public EnumTypeConverter() {
		super();
	}

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") final Class clazz) {
		return clazz.equals(Definition.class);
	}

	/**
	 * The marshal method for EnumTypeConverter has been implemented to aid
	 * testing.
	 */
	@Override
	public void marshal(final Object value, final HierarchicalStreamWriter writer, final MarshallingContext context) {
		final Definition def = (Definition) value;
		writer.startNode("ID");
		writer.setValue(def.getId());
		writer.endNode();
		writer.startNode("Name");
		writer.setValue(def.getId());
		writer.endNode();
	}

	/**
	 * The unmarshal method reads through a Definition to parse the ID and the
	 * Name of a Definition. The ID and name of a definition are set and the
	 * type of the Definition is set using the suffix of the ID/ Name.
	 */
	@Override
	public Object unmarshal(final HierarchicalStreamReader reader, final UnmarshallingContext context) {
		final Definition definition = new Definition();
		String id = null, name = null;
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			final String nodeName = reader.getNodeName();
			String value = reader.getValue();
			value = value == null ? "" : value;
			if (nodeName.equalsIgnoreCase("ID")) {
				id = reader.getValue();
			} else if (nodeName.equalsIgnoreCase("NAME")) {
				name = reader.getValue();
			}
			definition.setId(id != null && !id.isEmpty() ? id : name);
			reader.moveUp();
		}

		definition.setType(DefinitionType.getDefinitionType(definition.getId()));
		return definition;
	}

}