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
package com.orchestral.rhapsody.rlcanalyser.store;

import java.util.List;

import com.orchestral.rhapsody.rlcanalyser.ModifiedPropertyCountData;
import com.orchestral.rhapsody.rlcanalyser.configuration.ConfigurationPropertyDefinition;

public interface ComponentConfigurationDataStore {
	
	String getType();
	
	List<ModifiedPropertyCountData> getModifedPropertyCounts(int maxResult);
	
	void addPropertyValue(String propertyName, String propertyValue);
}