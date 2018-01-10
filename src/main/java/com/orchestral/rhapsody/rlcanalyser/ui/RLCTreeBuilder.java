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

import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.orchestral.rhapsody.rlcanalyser.ConfigurationAnalyser;
import com.orchestral.rhapsody.rlcanalyser.ModifiedPropertyCountData;
import com.orchestral.rhapsody.rlcanalyser.RLCDataAnalyser;
import com.orchestral.rhapsody.rlcanalyser.TypeCountData;
import com.orchestral.rhapsody.rlcanalyser.store.ConfigurationDataStore;
import com.orchestral.rhapsody.rlcanalyser.store.GeneralTabType;
import com.orchestral.rhapsody.rlcanalyser.store.RLCDataStore;
import com.orchestral.rhapsody.rlcanalyser.store.RLCDataStore.TotalCountDataType;

/**
 * This class is for building trees of information for display on the UI
 */
public class RLCTreeBuilder {

	// Singleton class as it just output a single tree depending on the inputs
	public RLCTreeBuilder() {
	}

	/**
	 * This method creates the "Most Used" results tree which is then used to
	 * populate the JTabbedPanes
	 *
	 * @param dataStore
	 *            - the RLCDataStore that was used to collect all the data for
	 *            the RLCs processed
	 * @param configurationDataStore
	 *            - the ConfigurationDataStore that was used to collect all the
	 *            data for the RLCs processed
	 * @return JScrollPane
	 */
	public JScrollPane createMostUsedResultsTree(final RLCDataStore dataStore,
	                                             final ConfigurationDataStore configurationDataStore) {
		final DefaultMutableTreeNode top = new DefaultMutableTreeNode("root");

		final RLCDataAnalyser dataAnalyser = new RLCDataAnalyser(dataStore);
		// To restrict the maximum number of components to count for,
		// change the maxResult integer parameter for the getMostUsed methods.
		addResultTreeNode(	top,
		                  	"Most used communication points",
		                  	dataAnalyser.getMostUsedCommunicationPoints(-1),
		                  	dataAnalyser.getNumberOfCommunicationPoints());
		addResultTreeNode(top, "Most used input communication points",
		                  dataAnalyser.getMostUsedInputCommunicationPoints(-1),
		                  dataAnalyser.getNumberOfInputCommunicationPoints());
		addResultTreeNode(top, "Most used output communication points",
		                  dataAnalyser.getMostUsedOutputCommunicationPoints(-1),
		                  dataAnalyser.getNumberOfOutputCommunicationPoints());
		addResultTreeNode(top, "Most used filters", dataAnalyser.getMostUsedFilters(-1),
		                  dataAnalyser.getNumberOfFilters());
		addResultTreeNode(top, "Most used definition types", dataAnalyser.getMostUsedDefinitionTypes(-1),
		                  dataStore.getTotalCounts(TotalCountDataType.Definitions));

		return new JScrollPane(manipulateJTree(top));
	}

	/**
	 * This method will generate the resulting aggregated data tree
	 *
	 * @param dataStore
	 *            - the RLCDataStore that was used to collect all the data for
	 *            the RLCs processed
	 * @param configurationDataStore
	 *            - the ConfigurationDataStore that was used to collect all the
	 *            data for the RLCs processed
	 * @param dataStoreMap
	 *            - the DataStoreMap that is used to collect the names of the
	 *            different RLCs processed
	 * @return JScrollPane
	 */
	public JScrollPane createResultTree(final RLCDataStore dataStore,
	                                    final ConfigurationDataStore configurationDataStore, final Map<String, RLCDataStore> dataStoreMap) {
		final DefaultMutableTreeNode top = new DefaultMutableTreeNode("root");

		final RLCDataAnalyser dataAnalyser = new RLCDataAnalyser(dataStore);
		final List<String> fileNameHeaderList;


		final DefaultMutableTreeNode summary = new DefaultMutableTreeNode("Summary");
		final DefaultMutableTreeNode rlcSummary = new DefaultMutableTreeNode("RLC Information");

		addATreeNode(summary, "Total number of Lockers", dataStore.getTotalCounts(TotalCountDataType.Lockers));
		addATreeNode(summary, "Total number of Routes", dataStore.getTotalCounts(TotalCountDataType.Routes));
		addATreeNode(summary, "Total number of Communication Points",
		             dataStore.getTotalCounts(TotalCountDataType.CommunicationPoints));

		addATreeNode(summary, "Total number of Filters", dataAnalyser.getNumberOfFilters());
		addATreeNode(summary, "Total number of Web Services", dataStore.getTotalCounts(TotalCountDataType.WebServices));
		addATreeNode(summary, "Total number of Definitions", dataStore.getTotalCounts(TotalCountDataType.Definitions));
		addATreeNode(summary, "Total number of Message Tracking Schemes",
		             dataStore.getTotalCounts(TotalCountDataType.MessageTrackingSchemes));
		addATreeNode(summary, "Total number of REST Clients", dataStore.getTotalCounts(TotalCountDataType.RESTClients));
		addATreeNode(summary, "Total number of Variables", dataStore.getTotalCounts(TotalCountDataType.Variables));
		addATreeNode(summary, "Total number of Lookup Tables",
		             dataStore.getTotalCounts(TotalCountDataType.LookupTables));
		addATreeNode(summary, "Total number of Shared JavaScript Libraries",
		             dataStore.getTotalCounts(TotalCountDataType.SharedJSLibraries));

		fileNameHeaderList = new ArrayList<String>(dataStoreMap.keySet());
		fileNameHeaderList.replaceAll(fileName -> fileName.substring(fileName.indexOf(":") + 1));

		// Add the tree structure to the root
		addATreeNode(rlcSummary, "Total number of RLC files processed", fileNameHeaderList.size());
		addResultsTreeNode(rlcSummary, "RLCs Processed", fileNameHeaderList);

		top.add(summary);
		top.add(rlcSummary);

		return new JScrollPane(manipulateJTree(top));
	}

	/**
	 * Method is used to create a tree with general properties for each comm
	 * point/ filter to be shown.
	 *
	 * @param generalPropMap
	 * @return
	 */
	public JScrollPane createGeneralPropertiesTree(final Map<String, Map<GeneralTabType, TypeCountData>> generalPropMap) {
		final DefaultMutableTreeNode top = new DefaultMutableTreeNode("root");
		// Iterate through general properties map, to access each type of comm point.
		for (final Map.Entry<String, Map<GeneralTabType, TypeCountData>> entry : generalPropMap.entrySet()) {

			final DefaultMutableTreeNode commPointNode = new DefaultMutableTreeNode(entry.getKey());
			// Total Counts
			addATreeNode(commPointNode, "Total Counts", entry.getValue().get(GeneralTabType.TotalCounts).getCounts());
			// Connection Mode
			final DefaultMutableTreeNode connectionModeNode = new DefaultMutableTreeNode("Connection Mode");

			final Map<String, Long> countMap = generateCountMap("cpm", entry);
			for (final Entry<String, Long> mapEntry : countMap.entrySet()) {
				addATreeNode(connectionModeNode, mapEntry.getKey(), mapEntry.getValue());
			}

			// Startup State
			final DefaultMutableTreeNode startupStateNode = new DefaultMutableTreeNode("Startup State");
			final Map<String, Long> countMap1 = generateCountMap("START", entry);
			for (final Entry<String, Long> mapEntry : countMap1.entrySet()) {
				addATreeNode(startupStateNode, mapEntry.getKey(), mapEntry.getValue());
			}

			// Retry Type
			final DefaultMutableTreeNode retryTypeNode = new DefaultMutableTreeNode("Retry Type");
			final Map<String, Long> countMap2 = generateCountMap("rt", entry);
			for (final Entry<String, Long> mapEntry : countMap2.entrySet()) {
				addATreeNode(retryTypeNode, mapEntry.getKey(), mapEntry.getValue());
			}

			commPointNode.add(connectionModeNode);
			commPointNode.add(startupStateNode);
			commPointNode.add(retryTypeNode);

			top.add(commPointNode);
		}

		return new JScrollPane(manipulateJTree(top));

	}

	/**
	 * Helper method to generate a Map<String, Long> where the key is the string
	 * representation of the enum and the long is the corresponding long value
	 * for the TypeCountData of the GeneralTabType property.
	 *
	 * The map generated is for a particular class of general properties.
	 *
	 * E.g. use "rt" as the startPhrase paramater to get a Map of Retry Type
	 * with the corresponding counts.
	 *
	 * @param startPhrase
	 * @param entry
	 * @return
	 */
	private Map<String, Long> generateCountMap(final String startPhrase, final Entry<String, Map<GeneralTabType, TypeCountData>> entry) {
		final Map<String, Long> countMap = new HashMap<>();

		final List<GeneralTabType> tabTypeList = new ArrayList<>();
		// add enum types to list based on start/ end characters input.
		for (final GeneralTabType tabType : GeneralTabType.values()) {
			// StartupState, need starts with and ends with 'START'
			if (tabType.name().startsWith(startPhrase) || tabType.name().endsWith(startPhrase)) {
				tabTypeList.add(tabType);
			}
		}

		for (final GeneralTabType tabType : tabTypeList) {
			final TypeCountData countData = entry.getValue().get(tabType);
			if (countData != null) {
				countMap.put(tabType.getType(), (long) countData.getCounts());
			}
		}
		return countMap;
	}

	/**
	 * This method creates the breakdown of the total components within each RLC
	 *
	 * @param dataStoreMap - used to collect information from each RLC and
	 *            display them accordingly
	 * @return JScrollPane
	 */
	public JScrollPane createTotalPerRLCResultsTree(final Map<String, RLCDataStore> dataStoreMap) {
		final DefaultMutableTreeNode top = new DefaultMutableTreeNode("root");
		final DefaultMutableTreeNode totalPerRLC = new DefaultMutableTreeNode("RLC Totals Breakdown");

		final List<String> totalObjectRLCList = new ArrayList<String>();

		for (final TotalCountDataType configTypes : RLCDataStore.TotalCountDataType.values()) {
			for (final String item : dataStoreMap.keySet()) {
				if (configTypes == TotalCountDataType.Filters) {
					final RLCDataAnalyser dataAnalyser = new RLCDataAnalyser(dataStoreMap.get(item));
					totalObjectRLCList
					.add("" + item.substring(item.indexOf(":") + 1) + ": " + dataAnalyser.getNumberOfFilters());
				} else {
					totalObjectRLCList.add("" + item.substring(item.indexOf(":") + 1) + ": "
							+ dataStoreMap.get(item).getTotalCounts(configTypes));
				}
			}

			// Add the tree structure to the root
			addResultsTreeNode(totalPerRLC, "Total number of " + configTypes.toString() + " per RLC",
			                   totalObjectRLCList);

			totalObjectRLCList.clear();
		}

		top.add(totalPerRLC);
		return new JScrollPane(manipulateJTree(top));
	}

	/**
	 * This method will create the configuration tab tree
	 *
	 * @param dataStore
	 *            - the RLCDataStore that was used to collect all the data for
	 *            the RLCs processed
	 * @param configurationDataStore
	 *            - the ConfigurationDataStore that was used to collect all the
	 *            data for the RLCs processed
	 * @return JScrollPane
	 */
	public JScrollPane createConfigurationResultsTree(final RLCDataStore dataStore,
	                                                  final ConfigurationDataStore configurationDataStore) {
		final DefaultMutableTreeNode top = new DefaultMutableTreeNode("root");

		final ConfigurationAnalyser configurationAnalyser = new ConfigurationAnalyser(configurationDataStore);

		// Populate with config tab results
		final DefaultMutableTreeNode commPointAnalysis = new DefaultMutableTreeNode("Communication Points");
		for (final String type : configurationAnalyser.getAllCommunicationPointTypes()) {
			final List<ModifiedPropertyCountData> properties = configurationAnalyser
					.getModifiedCommunicationPointPropertyCounts(type, -1);
			addResultTreeNode(commPointAnalysis, "" + type + " configuration", properties);
		}
		top.add(commPointAnalysis);

		return new JScrollPane(manipulateJTree(top));
	}

	/**
	 * This method will create the filter configuration data tree
	 *
	 * @param dataStore
	 *            - the RLCDataStore that was used to collect all the data for
	 *            the RLCs processed
	 * @param configurationDataStore
	 *            - the ConfigurationDataStore that was used to collect all the
	 *            data for the RLCs processed
	 * @return JScrollPane
	 */
	public JScrollPane createFilterResultsTree(final RLCDataStore dataStore,
	                                           final ConfigurationDataStore configurationDataStore) {
		final DefaultMutableTreeNode top = new DefaultMutableTreeNode("root");

		final ConfigurationAnalyser configurationAnalyser = new ConfigurationAnalyser(configurationDataStore);

		// Get the filter data
		final DefaultMutableTreeNode filterAnalysis = new DefaultMutableTreeNode("Filters");
		for (final String type : configurationAnalyser.getAllFilterTypes()) {
			final List<ModifiedPropertyCountData> properties = configurationAnalyser
					.getModifiedFilterPropertyCounts(type, -1);
			addResultTreeNode(filterAnalysis, "" + type + " configuration", properties);
		}
		top.add(filterAnalysis);

		return new JScrollPane(manipulateJTree(top));
	}

	/**
	 * This method will create the route configuration data tree
	 *
	 * @param dataStore
	 *            - the RLCDataStore that was used to collect all the data for
	 *            the RLCs processed
	 * @return JScrollPane
	 */
	public JScrollPane createRouteResultsTree(final RLCDataStore dataStore) {
		final DefaultMutableTreeNode top = new DefaultMutableTreeNode("root");
		final RLCDataAnalyser dataAnalyser = new RLCDataAnalyser(dataStore);

		addResultsTreeNode(top, "Number of filters per route", dataAnalyser.getFilterCountsPerRoute(),
		                   dataAnalyser.getNumberOfRoutes());

		addResultsTreeNode(top, "Number of definitions per route", dataAnalyser.getDefinitionCountsPerRoute(),
		                   dataAnalyser.getNumberOfRoutes());

		return new JScrollPane(manipulateJTree(top));
	}

	/**
	 * This method aids in the creation of the JTrees which are used to add in
	 * the JTabbedPane component
	 *
	 * @param top
	 *            - input to the JTree which will be formatted for display
	 * @return JTree which will be used for adding to the root of a pane
	 */
	private JTree manipulateJTree(final DefaultMutableTreeNode top) {
		final JTree resultsTree = new JTree(top) {
			private static final long serialVersionUID = 1748421292182815368L;

			@Override
			public Insets getInsets() {
				return new Insets(5, 5, 5, 5);
			}
		};

		resultsTree.setRootVisible(false);
		resultsTree.setShowsRootHandles(true);

		if (resultsTree.getRowCount() > 0) {
			resultsTree.expandRow(0);
		}

		return resultsTree;
	}

	/**
	 * This method is used to add nodes to a tree
	 *
	 * @param parent
	 *            - tree root
	 * @param categoryName
	 *            - name of the item to be added
	 * @param count
	 *            - the number of occurrences for that item
	 */
	private void addATreeNode(final DefaultMutableTreeNode parent, final String categoryName, final long count) {
		parent.add(new DefaultMutableTreeNode(categoryName + " : " + count));
	}

	/**
	 * This method is used to add nodes to a tree
	 *
	 * @param node
	 *            - the node to be added to
	 * @param categoryName
	 *            - group name of the item list to be added
	 * @param counts
	 *            - the list of count values
	 * @param total
	 *            - the total number of RLCs
	 */
	private void addResultTreeNode(final DefaultMutableTreeNode node, final String categoryName,
	                               final List<TypeCountData> counts, final long total) {
		final DefaultMutableTreeNode category = new DefaultMutableTreeNode(categoryName);
		node.add(category);

		for (final TypeCountData count : counts) {
			final DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(
			                                                                    ResultsFormatter.formatCount(count, total));
			category.add(childNode);
		}
	}

	/**
	 * This method is used to add nodes to a tree
	 *
	 * @param node
	 *            - the node to be added to
	 * @param categoryName
	 *            - the group name of the list of the items
	 * @param counts
	 *            - list of counts values
	 */
	private void addResultTreeNode(final DefaultMutableTreeNode node, final String categoryName,
	                               final List<ModifiedPropertyCountData> counts) {
		final DefaultMutableTreeNode category = new DefaultMutableTreeNode(categoryName);
		node.add(category);

		for (final ModifiedPropertyCountData count : counts) {
			final DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(ResultsFormatter.formatCount(count));
			category.add(childNode);
		}
	}

	/**
	 * This method is used to add nodes to a tree
	 *
	 * @param node
	 *            - the node to be added to
	 * @param categoryName
	 *            - the name of the category
	 * @param names
	 *            - the list of strings to be displayed under the node
	 */
	private void addResultsTreeNode(final DefaultMutableTreeNode node, final String categoryName,
	                                final List<String> names) {
		final DefaultMutableTreeNode category = new DefaultMutableTreeNode(categoryName);
		node.add(category);

		for (final String name : names) {
			final DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(name);
			category.add(childNode);
		}
	}

	/**
	 * This method is used to add nodes to a tree - specifically the percentage
	 * of filters per route
	 *
	 * @param root
	 *            - the root of the node used for display
	 * @param categoryName
	 *            - the name of the item list
	 * @param numFiltersPerRoute
	 *            - the count list of the number of filters per route
	 * @param total
	 *            - the total number of RLCs used for calculating the percentage
	 */
	private void addResultsTreeNode(final DefaultMutableTreeNode root, final String categoryName,
	                                final int[] numFiltersPerRoute, final long total) {
		final DefaultMutableTreeNode category = new DefaultMutableTreeNode(categoryName);
		root.add(category);
		for (int i = 0; i < numFiltersPerRoute.length; i++) {
			final double percentage = (double) numFiltersPerRoute[i] / total * 100;
			final int ix = (int) (percentage * 100.0); // scale it
			final double dbl2 = ix / 100.0;
			DefaultMutableTreeNode node;
			if (i == 30) {
				node = new DefaultMutableTreeNode("30 or more : " + numFiltersPerRoute[i] + " (" + dbl2 + "%)");
			} else {
				node = new DefaultMutableTreeNode(i + " : " + numFiltersPerRoute[i] + " (" + dbl2 + "%)");
			}

			category.add(node);
		}
	}
}
