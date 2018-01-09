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

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;



/**
 * This class aid the construction of the JPanel which can be set and get using
 * this class. This class when instantiated will create the components and set
 * their names. A getter will cycle through the list of components belonging to
 * the mainContentPanel and output the one with a unique name.
 *
 * This class will contain all of the
 */
public class RLCContentPanel {

	private final JPanel mainContentPanel;
	private JTextField folderText;
	private JLabel label;
	private JLabel labelInstructions;
	private JLabel labelLoading;
	private JLabel labelVersion;
	private JButton browseButton;
	private JButton processButton;
	private JButton exportButton;
	private JProgressBar progressBar;
	private JTabbedPane resultsTab;
	private ImageIcon image = null;
	private SpringLayout layout;
	private boolean exportStatus = false;

	/**
	 * Constructor for the RLCContentPanel. Generates the JPanel object,
	 * initialises the components and sets the layout for the components
	 */
	public RLCContentPanel() {
		this.mainContentPanel = new JPanel();
		initComponents();
		setLayout();
	}

	/**
	 * Return the mainContentPanel, JPanel that contains all the components of
	 * the JFrame.
	 *
	 * @return
	 */
	public JPanel getMainContentPanel() {
		return this.mainContentPanel;
	}

	/**
	 * Get the JTextField that the filepath text
	 *
	 * @return
	 */
	public JTextField getFolderText() {
		return this.folderText;
	}

	/**
	 * Set the JTextField to hold a different JTextField
	 *
	 * @param folderText
	 */
	public void setFolderText(final JTextField folderText) {
		this.folderText = folderText;
	}

	/**
	 * Get the Jlabel text ("Folder:"
	 *
	 * @return
	 */
	public JLabel getLabel() {
		return this.label;
	}

	/**
	 * Set the Jlabel to a different Jlabel
	 *
	 * @param label
	 */
	public void setLabel(final JLabel label) {
		this.label = label;
	}

	/**
	 * Get the JLabel that contains the instructions for the user
	 *
	 * @return
	 */
	public JLabel getLabelInstructions() {
		return this.labelInstructions;
	}

	/**
	 * Set the JLabel for the instructions
	 *
	 * @param labelInstructions
	 */
	public void setLabelInstructions(final JLabel labelInstructions) {
		this.labelInstructions = labelInstructions;
	}

	/**
	 * Get the JLabel for the loading symbol
	 *
	 * @return
	 */
	public JLabel getLabelLoading() {
		return this.labelLoading;
	}

	/**
	 * Set the JLabel for loading symbol
	 *
	 * @param labelLoading
	 */
	public void setLabelLoading(final JLabel labelLoading) {
		this.labelLoading = labelLoading;
	}

	/**
	 * Get the JLabel for the version number
	 *
	 * @return
	 */
	public JLabel getLabelVersion() {
		return this.labelVersion;
	}

	/**
	 * Set the JLabel for the version number
	 *
	 * @param labelVersion
	 */
	public void setLabelVersion(final JLabel labelVersion) {
		this.labelVersion = labelVersion;
	}

	/**
	 * Get the JButton that allows the user to browse for a file
	 *
	 * @return
	 */
	public JButton getBrowseButton() {
		return this.browseButton;
	}

	/**
	 * Set the JButton that allows the user to browse for a file
	 *
	 * @param browseButton
	 */
	public void setBrowseButton(final JButton browseButton) {
		this.browseButton = browseButton;
	}

	/**
	 * Get the JButton that allows the user to process the RLCs
	 *
	 * @return
	 */
	public JButton getProcessButton() {
		return this.processButton;
	}

	/**
	 * Set the JButton that allows the user to process the RLCs
	 *
	 * @param processButton
	 */
	public void setProcessButton(final JButton processButton) {
		this.processButton = processButton;
	}

	/**
	 * Get the JButton that allows the user to export the RLCs
	 *
	 * @return
	 */
	public JButton getExportButton() {
		return this.exportButton;
	}

	/**
	 * Set the JButton that allows the user to export the RLCs
	 *
	 * @param exportButton
	 */
	public void setExportButton(final JButton exportButton) {
		this.exportButton = exportButton;
	}

	/**
	 * Get the JProgressBar that displays the progress through the program
	 *
	 * @return
	 */
	public JProgressBar getProgressBar() {
		return this.progressBar;
	}

	/**
	 * Set the JProgressBar that displays the progress through the program
	 *
	 * @param progressBar
	 */
	public void setProgressBar(final JProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	/**
	 * Get the JTabbedPane that displays the results in a tree format
	 *
	 * @return
	 */
	public JTabbedPane getResultsTab() {
		return this.resultsTab;
	}

	/**
	 * Set the JTabbedPane that displays the results in a tree format
	 *
	 * @param resultsTab
	 */
	public void setResultsTab(final JTabbedPane resultsTab) {
		this.resultsTab = resultsTab;
	}

	/**
	 * Sets up all the components to be stored in the JPanel. and the initial
	 * state and condition of the components
	 */
	private void initComponents() {
		// Add the SpringLayout component
		this.layout = new SpringLayout();
		this.mainContentPanel.setLayout(this.layout);

		// Add the folder label
		this.label = new JLabel("Folder: ");
		this.mainContentPanel.add(this.label);

		// Add the instruction label
		this.labelInstructions = new JLabel("Click Browse to select a folder");
		this.labelInstructions.setFont(this.labelInstructions.getFont().deriveFont(12.0f));
		this.mainContentPanel.add(this.labelInstructions);

		// Add the version label
		this.labelVersion = new JLabel();
		this.labelVersion.setToolTipText("Approximate version range for all RLCs processed");
		this.mainContentPanel.add(this.labelVersion);

		// Add the folder text field
		this.folderText = new JTextField("", 15);
		this.mainContentPanel.add(this.folderText);

		// Add the buffering icon
		try {
			this.image = new ImageIcon(getImage("images/ajax-loader.gif"));
		} catch (final NullPointerException e1) {
			e1.printStackTrace();
		}

		// Adding the buffering icon to a label and then to the JPanel
		this.labelLoading = new JLabel();
		this.labelLoading.setIcon(this.image);
		this.labelLoading.setVisible(false);
		this.mainContentPanel.add(this.labelLoading);

		// Add the browse button
		this.browseButton = new JButton("Browse");
		this.mainContentPanel.add(this.browseButton);

		this.browseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				browseButtonClick(e);
			}
		});

		// Add the process button
		this.processButton = new JButton("Process");
		this.mainContentPanel.add(this.processButton);

		// Add the progress bar
		this.progressBar = new JProgressBar();
		this.mainContentPanel.add(this.progressBar);

		// Add the export button
		this.exportButton = new JButton("Export");
		this.exportButton.setEnabled(false);
		this.mainContentPanel.add(this.exportButton);

		// Add the results tab
		this.resultsTab = new JTabbedPane();
		this.resultsTab.removeAll();
		this.mainContentPanel.add(this.resultsTab);
	}

	/**
	 * Sets up the configuration for the JPanel using a SpringLayout design.
	 */
	private void setLayout() {
		// Set up the spring layout configuration

		// Set layout for the folder label
		this.layout.putConstraint(SpringLayout.WEST, this.label, 15, SpringLayout.WEST, this.mainContentPanel);
		this.layout.putConstraint(SpringLayout.NORTH, this.label, 10, SpringLayout.SOUTH, this.labelInstructions);

		// Set layout for the folder input text field
		this.layout.putConstraint(SpringLayout.WEST, this.folderText, 5, SpringLayout.EAST, this.label);
		this.layout.putConstraint(SpringLayout.NORTH, this.folderText, 5, SpringLayout.SOUTH, this.labelInstructions);

		// Set layout for the browse button
		this.layout.putConstraint(SpringLayout.WEST, this.browseButton, 5, SpringLayout.EAST, this.folderText);
		this.layout.putConstraint(SpringLayout.NORTH, this.browseButton, 5, SpringLayout.SOUTH, this.labelInstructions);
		this.layout.putConstraint(SpringLayout.EAST, this.mainContentPanel, 5, SpringLayout.EAST, this.browseButton);

		// Set layout for the process button
		this.layout.putConstraint(SpringLayout.WEST, this.processButton, 5, SpringLayout.WEST, this.mainContentPanel);
		this.layout.putConstraint(SpringLayout.NORTH, this.processButton, 5, SpringLayout.SOUTH, this.folderText);

		// Set layout for the progress bar
		this.layout.putConstraint(SpringLayout.WEST, this.progressBar, 12, SpringLayout.WEST, this.mainContentPanel);
		this.layout.putConstraint(SpringLayout.EAST, this.progressBar, -12, SpringLayout.EAST, this.mainContentPanel);
		this.layout.putConstraint(SpringLayout.NORTH, this.progressBar, 5, SpringLayout.SOUTH, this.processButton);

		// Set layout for the instruction label
		this.layout.putConstraint(SpringLayout.WEST, this.labelInstructions, 15, SpringLayout.WEST,
		                          this.mainContentPanel);
		this.layout.putConstraint(SpringLayout.NORTH, this.labelInstructions, 10, SpringLayout.NORTH,
		                          this.mainContentPanel);

		// Set layout for the loading icon
		this.layout.putConstraint(SpringLayout.NORTH, this.labelLoading, 10, SpringLayout.SOUTH, this.folderText);
		this.layout.putConstraint(SpringLayout.EAST, this.labelLoading, -15, SpringLayout.EAST, this.mainContentPanel);

		// Set layout for the results tab
		this.layout.putConstraint(SpringLayout.EAST, this.resultsTab, -5, SpringLayout.EAST, this.mainContentPanel);
		this.layout.putConstraint(SpringLayout.WEST, this.resultsTab, 5, SpringLayout.WEST, this.mainContentPanel);
		this.layout.putConstraint(SpringLayout.NORTH, this.resultsTab, 5, SpringLayout.SOUTH, this.progressBar);
		this.layout.putConstraint(SpringLayout.SOUTH, this.resultsTab, 5, SpringLayout.NORTH, this.exportButton);

		// Set layout for the version description label
		this.layout.putConstraint(SpringLayout.WEST, this.labelVersion, 15, SpringLayout.WEST, this.mainContentPanel);
		this.layout.putConstraint(SpringLayout.SOUTH, this.labelVersion, -13, SpringLayout.SOUTH,
		                          this.mainContentPanel);

		// Set layout for the export button
		this.layout.putConstraint(SpringLayout.EAST, this.exportButton, -5, SpringLayout.EAST, this.mainContentPanel);
		this.layout.putConstraint(SpringLayout.SOUTH, this.exportButton, -5, SpringLayout.SOUTH, this.mainContentPanel);
	}

	/**
	 * Checks if the JTextField containing the folder location is loaded as
	 * empty by default. If it is empty, display the process button - the user
	 * must now click browse to find an alternative folder.
	 *
	 */
	public void checkEmptyFolderText() {
		if (this.folderText.getText().isEmpty()) {
			this.processButton.setEnabled(false);
		}
	}

	/**
	 * Action that occurs after the browse button is clicked. It creates the
	 * JFileChooser and opens the window for folder/file selection and changes
	 * the current folder path and current folder text.
	 *
	 * @param e
	 */
	private void browseButtonClick(final ActionEvent e) {
		// Create the FileChooser object and set the selection mode
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		// Check for an existing path in the folder area
		String existingPath;
		existingPath = this.folderText.getText();

		if (!existingPath.isEmpty()) {
			fileChooser.setSelectedFile(new File(existingPath));
		}

		// Open the file chooser dialog box
		final int result = fileChooser.showOpenDialog(this.mainContentPanel);
		if (result == JFileChooser.APPROVE_OPTION) {
			this.folderText.setText(fileChooser.getSelectedFile().toString());
			browseButtonUIFeatures();
		}
	}

	/**
	 * The UI features that are enabled and disabled after the processing of the
	 * data has been completed.
	 */
	public void reenableGlobalUIFeatures() {
		this.labelLoading.setVisible(false);
		this.labelInstructions.setText("Click Browse to select a new file. Click Export to save in folder location.");
		this.labelVersion.setVisible(true);
		this.progressBar.setIndeterminate(false);
		this.browseButton.setEnabled(true);
		this.processButton.setEnabled(false);
		this.exportButton.setEnabled(true);
		this.resultsTab.removeAll();
	}

	/**
	 * The UI features enabled after the user has clicked on the process button.
	 */
	public void processButtonUIFeatures() {
		this.browseButton.setEnabled(false);
		this.processButton.setEnabled(false);
		this.progressBar.setIndeterminate(true);
		this.labelLoading.setVisible(true);
		this.labelInstructions.setText("Processing...");
		this.exportStatus = false;
	}

	/**
	 * The UI features enabled after the user has clicked on the browse button.
	 */
	public void browseButtonUIFeatures() {
		this.labelInstructions.setText("Click Process to analyse data");
		this.labelVersion.setVisible(false);
		this.processButton.setEnabled(true);
	}

	/**
	 * Method for adding to the Tabbed pane stored in the contentPanel.
	 *
	 * @param tabName
	 *            - String for the name of the tab.
	 * @param tree
	 *            - JScrollPane that belongs with tab.
	 */
	public void addResultTree(final String tabName, final JScrollPane tree) {
		this.resultsTab.add(tabName, tree);
	}

	/**
	 * This method is used to write the error messages to the UI.
	 */
	public void errorMissingRLCConsole() {
		JOptionPane.showMessageDialog(this.mainContentPanel, "No RLCs found within this folder location");
	}

	/**
	 * This method is used to write an invalid path message to the UI.
	 */
	public void errorBadFileRLCConsole() {
		JOptionPane.showMessageDialog(this.mainContentPanel, "Please enter a valid file path or folder");
	}

	/**
	 * This method is used to write the runtime exception method
	 */
	public void errorRuntimeRLCConsole() {
		JOptionPane.showMessageDialog(this.mainContentPanel,
				"Runtime Exception occured - refer to logger for more information.");
	}

	/**
	 * This method is used to generate a message dialog box that contains the
	 * error message from the IOException handled
	 *
	 * @param errorMessage
	 *            - the message from the error
	 */
	public void errorIOIssueRLCConsole(final String errorMessage) {
		JOptionPane.showMessageDialog(this.mainContentPanel, errorMessage);
	}

	/**
	 * This method creates an option dialog box to allow the user to select what
	 * user would like to do after the export is completed successfully. If 1 is
	 * returned then the created file should be opened for display.
	 *
	 * @return int representing the option selected by the user
	 */
	public int optionCSVRLCConsole() {
		final Object[] options = {"OK", "Open File"};
		return JOptionPane.showOptionDialog(this.mainContentPanel, "Successfully exported to CSV", "Export Successful",
		                                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
	}

	/**
	 * Get the export status - returns true if the export for the current
	 * process has already occurred. Returns false if the export has not
	 * occurred yet.
	 *
	 * @return a boolean representing the current export status.
	 */
	public boolean getExportStatus() {
		return this.exportStatus;
	}

	/**
	 * Sets the export status
	 *
	 * @param status
	 *            - a boolean that will be used to set the current export status
	 */
	public void setExportStatus(final Boolean status) {
		this.exportStatus = status;
	}

	/**
	 * Method is used to retrieve images from resources. The images must be
	 * stored in src/main/resources/images to be retrieved.
	 *
	 * @param filePathandName
	 * @return
	 */
	public Image getImage(final String filePathandName) {
		final URL url = Thread.currentThread().getContextClassLoader().getResource(filePathandName);
		return Toolkit.getDefaultToolkit().getImage(url);
	}
}
