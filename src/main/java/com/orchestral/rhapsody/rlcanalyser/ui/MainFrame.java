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

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import com.orchestral.rhapsody.rlcanalyser.RLCDataCollector;
import com.orchestral.rhapsody.rlcanalyser.RlcAnalyser;
import com.orchestral.rhapsody.rlcanalyser.store.ConfigurationDataStore;
import com.orchestral.rhapsody.rlcanalyser.store.ConfigurationDataStoreFactory;
import com.orchestral.rhapsody.rlcanalyser.store.RLCDataExporter;
import com.orchestral.rhapsody.rlcanalyser.store.RLCDataStore;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -688393678136634384L;

	private final static Logger logger = Logger.getLogger(MainFrame.class);

	public static final int DEFAULT_WIDTH = 600;
	public static final int DEFAULT_HEIGHT = 560;

	// Determines if there is anything for the tree to create
	private enum ProcessOutput {
		NOTHING_TO_PROCESS, SOMETHING_TO_PROCESS, BAD_FILE, RUNTIME_ERROR, ERROR
	};


	private static final String APP_PROPERTIES_FILENAME = "rclanalyser.properties";
	private Properties defaultProperties;
	private final RLCContentPanel contentPanel;
	private String previousFolderPath;
	private RLCDataStore currentDataStore;
	private ConfigurationDataStore currentConfigDataStore;
	private Map<String, RLCDataStore> dataStoreMap;
	private List<String> fileNameHeaderList;
	private long start_time; // used for performance testing


	private class RlcProcessTask extends SwingWorker<Void, Void> {

		private RLCDataStore dataStore;
		private ConfigurationDataStore configurationDataStore;
		private ProcessOutput outputType;

		/*
		 * RLC loading task. Executed in background thread.
		 */
		@Override
		public Void doInBackground() {

			try {
				this.dataStore = new RLCDataStore();
				this.configurationDataStore = new ConfigurationDataStore(
				                                                         ConfigurationDataStoreFactory.get().createAllCommunicationPointStores(),
				                                                         ConfigurationDataStoreFactory.get().createAllFilterStores());
				final RLCDataCollector dataCollector = new RLCDataCollector(this.dataStore, this.configurationDataStore);

				final RlcAnalyser rlcAnalyser = new RlcAnalyser(dataCollector);

				final File file = new File(
				                           MainFrame.this.contentPanel.getFolderText().getText());

				try {
					rlcAnalyser.analysePath(file);
				} catch (final IOException e) {
					this.outputType = ProcessOutput.BAD_FILE;
					return null;
				} catch (final RuntimeException e) {
					this.outputType = ProcessOutput.RUNTIME_ERROR;
					return null;
				}

				// After processing the RLC determine if there is anything to
				// process
				if (rlcAnalyser.getEngineList() != null && rlcAnalyser.getEngineList().isEmpty()) {
					this.outputType = ProcessOutput.NOTHING_TO_PROCESS;
					return null;
				}

				// Retrieve the DataStore Map: the Map for all RLCDataStores.
				MainFrame.this.dataStoreMap = dataCollector.getDataStoreMap();

				this.outputType = ProcessOutput.SOMETHING_TO_PROCESS;
			} catch (final Exception e) {
				e.printStackTrace();
				// Error occurred
				this.outputType = ProcessOutput.ERROR;
			}
			return null;
		}

		/**
		 * Executed in event dispatching thread
		 */
		@Override
		public void done() {
			MainFrame.this.setCursor(null);
			MainFrame.this.contentPanel.reenableGlobalUIFeatures();

			// Process data collected if something is contained
			if (this.outputType == ProcessOutput.SOMETHING_TO_PROCESS) {
				processingDone(this.dataStore, this.configurationDataStore);
			} else if (this.outputType == ProcessOutput.NOTHING_TO_PROCESS) {
				MainFrame.this.contentPanel.errorMissingRLCConsole();
			} else if (this.outputType == ProcessOutput.RUNTIME_ERROR) {
				MainFrame.this.contentPanel.errorRuntimeRLCConsole();
			} else {
				MainFrame.this.contentPanel.errorBadFileRLCConsole();
			}
		}
	}

	/**
	 * MainFrame constructor - used to set up the UI through the RLCContentPanel
	 * class as well as window size and buttons. This also reads from the
	 * properties file which is used to restore the last saved configuration ofr
	 * the folder selected.
	 */
	public MainFrame() {
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		this.setLocationByPlatform(true);
		this.setTitle("RLC Analyser");

		this.contentPanel = new RLCContentPanel();
		this.getContentPane().add(this.contentPanel.getMainContentPanel());

		initButtons();

		// Load default text for folder location
		loadDefaults();
		final String defaultFolderName = this.defaultProperties.getProperty("RLCAnalyser.folderName", "");
		this.contentPanel.getFolderText().setText(defaultFolderName);

		this.contentPanel.checkEmptyFolderText();

		this.pack();
		this.setVisible(true);
	}

	/**
	 * This method will initialize the process and export button as part of the
	 * initialization process in the MainFrame constructor
	 */
	private void initButtons() {
		// Call the initComponents from RLCContentPanel
		this.contentPanel.getProcessButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				processButtonClick(e);
			}
		});

		this.contentPanel.getExportButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					exportButtonClick(e);
				} catch (final IOException ex) {
					ex.printStackTrace();
				}
			}
		});
	}


	/**
	 * Action that occurs when the export button is clicked. Determines the file
	 * path of the created CSV file. Creates the exporter and saves the CSV file
	 * in the predetermined location.
	 *
	 * @param e
	 * @throws IOException
	 */
	protected void exportButtonClick(final ActionEvent e) throws IOException {
		String csvFilePath;
		try {
			csvFilePath = generateCSVFilePath();
		} catch (final IOException e1) {
			this.contentPanel.errorIOIssueRLCConsole(e1.getMessage() + ":\nClick export to try exporting again");
			return;
		}


		final RLCDataExporter exporter = new RLCDataExporter(this.currentDataStore, this.dataStoreMap);
		exporter.export(csvFilePath);
		// show a dialogue confirming that csv was created.
		this.contentPanel.getLabelInstructions().setText("Click Browse to select a new file.");
		// Confirm the export has completed. Asks to open the exported CSV
		if (this.contentPanel.optionCSVRLCConsole() == 1) {
			Desktop.getDesktop().open(new File(csvFilePath));
		}
	}

	/**
	 * Action that occurs after the process button is clicked. The method will
	 * firstly check to see if the export button has previously been pressed. It
	 * will then reset the DataStore and the current folder path. The method
	 * then does a check of the folder input to ensure it satisfies the
	 * requirements; returning if requirements are not met (not a folder or RLC
	 * file).
	 *
	 * @param e
	 *            - ActionEvent - on click process button.
	 */
	private void processButtonClick(final ActionEvent e) {
		String folderPath;
		folderPath = this.contentPanel.getFolderText().getText();

		final File file = new File(folderPath);

		// Check to see if user wants to export the file if they haven't already
		if (this.contentPanel.getResultsTab().getComponentCount() > 0 && !this.contentPanel.getExportStatus()) {
			final int result = JOptionPane.showConfirmDialog(	this,
			                                                 	"Processing new files will remove the previous analysis.\nDo you wish to export the data first?",
			                                                 	"Export", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				this.contentPanel.getExportButton().doClick();
			}
		}

		try {
			if (folderPath == null || folderPath.isEmpty() || !file.exists() || !file.isDirectory()) {
				if (file.isFile() && !file.getName().endsWith(".rlc")) {
					this.contentPanel.errorBadFileRLCConsole();
					System.err.println(
							"[ERROR] : Selected item was not a folder or an rlc file. Only folders or single rlc files are acceptable");
					return;
				}
			}
		} catch (final Exception e1) {
			e1.printStackTrace();
			System.err.println("[ERROR] : The file was not accepted for some reason");
			JOptionPane.showMessageDialog(this, "Oops, something went wrong. Please try that again.");
		}

		// Save folder name
		this.defaultProperties.setProperty("RLCAnalyser.folderName", folderPath);
		saveDefaults();
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		this.contentPanel.processButtonUIFeatures();

		final RlcProcessTask rlcProcessTask = new RlcProcessTask();
		rlcProcessTask.execute();
	}

	/**
	 * This function is called in the overwritten SwingWorker function; done().
	 * It creates the trees for viewing on the IDE as well as produces the tabs
	 * for viewing the tree data.
	 *
	 * @param dataStore
	 *            - RLCDataStore that hold the information of the last processed
	 *            RLC(s)
	 * @param configurationDataStore
	 *            - ConfigurationDataStore that hold the configuration
	 *            information form the last processed RLC(s)
	 */
	private void processingDone(final RLCDataStore dataStore, final ConfigurationDataStore configurationDataStore) {
		this.currentDataStore = dataStore;
		this.currentConfigDataStore = configurationDataStore;
		//final Map<String, Map<GeneralTabType, TypeCountData>> mapTemp = this.currentDataStore.getCommunicationPointGeneralProperties();
		final RLCTreeBuilder builder = new RLCTreeBuilder();

		// Processing the multiple tabs of data
		final JScrollPane resultsTreeView = builder.createResultTree(dataStore, configurationDataStore,
		                                                             this.dataStoreMap);
		final JScrollPane mostUsedTreeView = builder.createMostUsedResultsTree(dataStore,
		                                                                       configurationDataStore);
		final JScrollPane configurationTreeView = builder.createConfigurationResultsTree(dataStore,
		                                                                                 configurationDataStore);
		final JScrollPane filterTreeView = builder.createFilterResultsTree(dataStore,
		                                                                   configurationDataStore);
		final JScrollPane routeTreeView = builder.createRouteResultsTree(dataStore);
		final JScrollPane totalsTreeView = builder.createTotalPerRLCResultsTree(this.dataStoreMap);
		final JScrollPane generalPropertiesTreeView = builder.createGeneralPropertiesTree(dataStore.getCommunicationPointGeneralProperties());

		// Add to the results tree
		this.contentPanel.addResultTree("General Summary", resultsTreeView);
		this.contentPanel.addResultTree("Most Used Summary", mostUsedTreeView);
		this.contentPanel.addResultTree("General Properties", generalPropertiesTreeView);
		this.contentPanel.addResultTree("Totals Per RLC", totalsTreeView);
		this.contentPanel.addResultTree("Communication Point Summary", configurationTreeView);
		this.contentPanel.addResultTree("Filter Summary", filterTreeView);
		this.contentPanel.addResultTree("Route Summary", routeTreeView);

		this.contentPanel.getLabelVersion()
		.setText("Rhapsody Engine Version Range (MAX): "
				+ this.currentDataStore.getRhapsodyVersion().getMaxVersion() + "  (MIN): "
				+ this.currentDataStore.getRhapsodyVersion().getMinVersion());

		// Store the folder path of the previously processed folder/rlc

		this.previousFolderPath = this.contentPanel.getFolderText().getText();
	}

	/**
	 * Generates the CSV file path to be used in storing the exported file.
	 * Opens up a file chooser dialog box which will then be used to select the
	 * destination. The file name will have the extension ".csv" appended if the
	 * file extension is not already ".csv".
	 *
	 * @return String - string of the file name
	 * @throws IOException
	 */
	private String generateCSVFilePath() throws IOException {
		String csvFilePath = null;
		String filename;
		final String name = "rlcData" + new Date().getTime();

		final JFileChooser fileChooser = new JFileChooser();

		fileChooser.setCurrentDirectory(new File(this.previousFolderPath));
		fileChooser.setSelectedFile(new File(name + ".csv"));

		final int result = fileChooser.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			csvFilePath = fileChooser.getSelectedFile().toString();
		} else {
			throw new IOException("Export Cancelled");
		}

		// remove the extension then add it back on in the end
		if (fileChooser.getSelectedFile().getName().contains(".")) {
			// delimit by the .
			filename = fileChooser.getSelectedFile().getName().substring(0,
			                                                             fileChooser.getSelectedFile().getName().lastIndexOf("."));
		} else {
			filename = fileChooser.getSelectedFile().getName();
		}

		// If after getting the name and removing any extension, the file name
		// still contains invalid characters throw an exception
		if (containsIllegalChars(filename)) {
			// The name contains invalid characters
			throw new IOException("Invalid character detected in file name");
		}

		csvFilePath = csvFilePath.substring(0, csvFilePath.indexOf(filename));
		csvFilePath = csvFilePath + filename + ".csv";
		this.contentPanel.setExportStatus(true);
		return csvFilePath;
	}

	/**
	 * Checks for illegal characters in the input string
	 *
	 * @param text
	 *            - String to be checked
	 * @return true if the text contains invalid characters, false if it does
	 *         not
	 */
	private boolean containsIllegalChars(final String text) {
		return text.contains(" ") || text.contains("/") || text.contains("\\") || text.contains(".")
				|| text.length() < 1;
	}

	/**
	 * Load the last used folder into the application
	 */
	private void loadDefaults() {
		try {
			this.defaultProperties = new Properties();
			final FileInputStream propertiesFile = new FileInputStream(APP_PROPERTIES_FILENAME);
			this.defaultProperties.load(propertiesFile);
			propertiesFile.close();
		} catch (final Exception e) {
			// Will use default values
			logger.error("Unable to load properties, " + e.getMessage(), e);
		}
	}

	/**
	 * Save the foldertext information (last used folder)
	 */
	private void saveDefaults() {
		try {
			final FileOutputStream out = new FileOutputStream(APP_PROPERTIES_FILENAME);
			this.defaultProperties.store(out, "---No Comment---");
			out.close();
		} catch (final Exception e) {
			logger.error("Unable to save properties, " + e.getMessage(), e);
		}
	}

}
