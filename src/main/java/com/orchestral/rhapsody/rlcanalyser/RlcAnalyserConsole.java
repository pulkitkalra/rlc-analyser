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

package com.orchestral.rhapsody.rlcanalyser;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.orchestral.rhapsody.rlcanalyser.store.ConfigurationDataStore;
import com.orchestral.rhapsody.rlcanalyser.store.ConfigurationDataStoreFactory;
import com.orchestral.rhapsody.rlcanalyser.store.RLCDataStore;
import com.orchestral.rhapsody.rlcanalyser.ui.MainFrame;


public class RlcAnalyserConsole {

	public static void main(final String[] args) {
		try {
			if (args.length < 1) {
				// run the GUI
				EventQueue.invokeLater(new Runnable()  {
					@Override
					public void run() {
						try {
							UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						} catch (final Exception e) {
							e.printStackTrace();
						}

						final MainFrame frame = new MainFrame();
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frame.setVisible(true);
					}
				});
			} else {
				final String filePath = args[0];

				final RLCDataStore dataStore = new RLCDataStore();
				final ConfigurationDataStore configurationDataStore = new ConfigurationDataStore(
						ConfigurationDataStoreFactory.get().createAllCommunicationPointStores(),
						ConfigurationDataStoreFactory.get().createAllFilterStores());

				final RLCDataCollector dataCollector = new RLCDataCollector(dataStore, configurationDataStore);

				final RlcAnalyser rlcAnalyser = new RlcAnalyser(dataCollector);
				final File file = new File(filePath);

				rlcAnalyser.analysePath(file);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}