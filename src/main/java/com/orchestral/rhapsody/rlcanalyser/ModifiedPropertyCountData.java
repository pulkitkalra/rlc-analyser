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

public class ModifiedPropertyCountData implements Comparable<ModifiedPropertyCountData> {
	
	private final String propertyName;
	
	private int numberOfOccurrencies = 0;
	
	private int numberOfModified = 0;
	
	public ModifiedPropertyCountData(final String type) {
		this.propertyName = type;
	}
	
	public String getPropertyName() {
		return this.propertyName;
	}

	public int getNumberOfOccurrencies() {
		return this.numberOfOccurrencies;
	}
	
	public int getNumberOfModified() {
		return this.numberOfModified;
	}
	 
	public void increaseNumberOfOccurrencies() {
		this.numberOfOccurrencies++;
	}
	
	public void increaseNumberOfModified() {
		this.numberOfModified++;
	}
	
	@Override
	public int compareTo(final ModifiedPropertyCountData another) {
		return (another).numberOfModified - this.numberOfModified;
	}

}
