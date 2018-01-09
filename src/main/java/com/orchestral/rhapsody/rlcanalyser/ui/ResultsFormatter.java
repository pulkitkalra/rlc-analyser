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
package com.orchestral.rhapsody.rlcanalyser.ui;

import com.orchestral.rhapsody.rlcanalyser.ModifiedPropertyCountData;
import com.orchestral.rhapsody.rlcanalyser.TypeCountData;

public class ResultsFormatter {

	public static String formatCount(final TypeCountData count, final long total) {
		final double percentage = (double)count.getCounts()/total * 100;
		final int ix = (int)(percentage * 100.0); // scale it
		final double dbl2 = ix/100.0;

		return new String(count.getType()+ " : " + count.getCounts()+" ("+dbl2+"%)");
	}

	public static String formatCount(final ModifiedPropertyCountData count) {
		final double percentage = (double)count.getNumberOfModified()/count.getNumberOfOccurrencies() * 100;
		final int ix = (int)(percentage * 100.0); // scale it
		final double dbl2 = ix/100.0;

		return new String(count.getPropertyName() + " : " + count.getNumberOfModified() + " per "+count.getNumberOfOccurrencies() +" ("+dbl2+"%)");
	}
}
