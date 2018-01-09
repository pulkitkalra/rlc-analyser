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
package com.orchestral.rhapsody.rlcanalyser;

/**
 * 
 */
public class TypeCountData implements Comparable<TypeCountData> {
	
	private final String type;
	
	private int counts;

	/**
	 * @param type
	 * @param counts
	 */
	public TypeCountData(final String type) {
		this.type = type;
		this.counts = 0;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param counts the counts to set
	 */
	public void setCounts(final int counts) {
		this.counts = counts;
	}

	/**
	 * @return the counts
	 */
	public int getCounts() {
		return this.counts;
	}

	@Override
	public int compareTo(final TypeCountData another) {
		return (another).counts - this.counts;
	}
}
