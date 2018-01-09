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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.log4j.Logger;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 */
@XStreamAlias("Definition")
public class Definition {

	private final static Logger logger = Logger.getLogger(Definition.class);

	public enum DefinitionType {
		Mapper("mdf", "Mapper"),
		EDIMessageDefinition("s3d", "EDI Message Definition"),
		SymphoniaXMLMessageDefinition("sxd", "Symphonia XML Message Definition"),
		XMLSchemaMessageDefinition("xsd", "XML Schema Message Definition"),
		Unknown("Unknown", "Unknown");

		private final String fileSuffix;
		private final String correctName;

		/**
		 * The Definition Type enum is defined using the file suffix (which
		 * determines the type of the definition) and the name which the user
		 * expects to see in the IDE.
		 *
		 * @param fileSuffix
		 * @param correctName
		 */
		private DefinitionType(final String fileSuffix, final String correctName) {
			this.fileSuffix = fileSuffix;
			this.correctName = correctName;
		}

		public String getFileSuffix() {
			return this.fileSuffix;
		}

		public static DefinitionType getDefinitionType(final String filename) {
			for (final DefinitionType type : DefinitionType.values()) {
				if (filename.endsWith(type.fileSuffix)) {
					return type;
				}
			}

			logger.debug("Unknown definition type " + filename);
			return Unknown;
		}

		/**
		 * @return the correctName
		 */
		public String getCorrectName() {
			return this.correctName;
		}

	}

	@XStreamAlias("ID")
	private String id;

	private DefinitionType type;

	public Definition() {
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * @return the type
	 */
	public DefinitionType getType() {
		return this.type;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.id = id;
		setType(DefinitionType.getDefinitionType(id == null ? "" : id));
	}

	/**
	 * @param type the type to set
	 */
	public void setType(final DefinitionType type) {
		this.type = type;
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
