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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

/**
 * This test file is used to conduct unit testing on the functionality of
 * certain methods contains in the RLCContentPanel class. The class is used to
 * abstract away the JPanel contents and retrieve the components only when
 * needed.
 */
public class RLCContentPanelTest {
	private RLCContentPanel contentPanel;

	private JPanel mainContentPanel;

	@Before
	public void contentPanelInit() {
		this.contentPanel = new RLCContentPanel();
	}

	/**
	 * This test checks that the correct components are retrieved.
	 */
	@Test
	public void testGetComponents() {
		final JButton buttonToCheck = this.contentPanel.getProcessButton();
		final JLabel labelToCheck = this.contentPanel.getLabelInstructions();


		assertEquals(buttonToCheck.getText(), "Process");
		assertEquals(labelToCheck.getText(), "Click Browse to select a folder");
	}

	@Test
	public void testSetComponentText() {
		// Test a JLabel
		this.contentPanel.getLabelLoading().setText("This is a test string");
		// Test a JTextField
		this.contentPanel.getFolderText().setText("Not Foldertext:");

		assertEquals(this.contentPanel.getLabelLoading().getText(),
				"This is a test string");

		assertEquals(this.contentPanel.getFolderText().getText(), "Not Foldertext:");

	}

	/**
	 * This test will check the getComponentText function
	 */
	@Test
	public void testComparingLabelNames() {
		String textToCheck = this.contentPanel.getLabel().getText();
		JLabel objToCheck = this.contentPanel.getLabel();

		assertEquals(textToCheck, objToCheck.getText());

		textToCheck = this.contentPanel.getLabelInstructions().getText();
		objToCheck = this.contentPanel.getLabelInstructions();

		assertEquals(textToCheck, objToCheck.getText());
	}

	/**
	 * This test checks the checkEmptyFolderText method to ensure that is used
	 * correctly
	 */
	@Test
	public void testCheckEmptyFolderText() {
		this.contentPanel.getProcessButton().setEnabled(true);

		this.contentPanel.getFolderText().setText("");

		this.contentPanel.checkEmptyFolderText();
		assertFalse(this.contentPanel.getProcessButton().isEnabled());

		// Previous test sets enabled to false, assert to true for the next test
		// 'reset'
		this.contentPanel.getProcessButton().setEnabled(true);

		this.contentPanel.getFolderText().setText(
				"/Tools/RLCAnalyser/Java/com.orchestral.rhapsody.rlcanalyser/test/input");

		this.contentPanel.checkEmptyFolderText();
		assertTrue(this.contentPanel.getProcessButton().isEnabled());
	}
}
