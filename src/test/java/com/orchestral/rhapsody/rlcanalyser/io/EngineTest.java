package com.orchestral.rhapsody.rlcanalyser.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.custommonkey.xmlunit.XMLTestCase;
import org.junit.Before;
import org.junit.Test;

import com.orchestral.rhapsody.rlcanalyser.io.Definition.DefinitionType;

public class EngineTest {

	private XStreamInstance xstream;
	private XMLTestCase xmlUnit;

	public static Engine engine(final int rlcVersion,
			final int compatibleRlcVersion,
			final List<CommunicationPoint> communicationPoints,
			final List<Route> routes,
			final List<Definition> definitions) {
		final Engine engine = new Engine();
		engine.setRlcVersion(rlcVersion);
		engine.setCompatibleRlcVersion(compatibleRlcVersion);
		engine.setCommunicationPoints(new ArrayList<CommunicationPoint>(communicationPoints));
		engine.setRoutes(new ArrayList<Route>(routes));
		engine.setDefinitions(new ArrayList<Definition>(definitions));
		return engine;
	}

	private Engine sampleEngineWithSingleFields() {
		final int rlcVersion = 9;
		final int compatibleRlcVersion = 5;
		final List<CommunicationPoint> communicationPoints = Arrays.asList(CommunicationPointTest.sampleCommunicationPointWithSingleFields());
		final List<Route> routes = Arrays.asList(RouteTest.sampleRouteWithSingleFields());
		final List<Definition> definition = Arrays.asList(DefinitionTest.sampleDefinition2());
		return EngineTest.engine(rlcVersion, compatibleRlcVersion, communicationPoints, routes, definition);
	}

	private Engine sampleEngineWithMultipleFields() {
		final Engine engine = sampleEngineWithSingleFields();
		engine.setCommunicationPoints(Arrays.asList(CommunicationPointTest.sampleCommunicationPointWithMultipleFields(),
				CommunicationPointTest.sampleCommunicationPointWithEmptyFields(),
				CommunicationPointTest.sampleCommunicationPointWithNullFields(),
				CommunicationPointTest.sampleCommunicationPointWithSingleFields()));
		engine.setRoutes(Arrays.asList(	RouteTest.sampleRouteWithMultipleFields(),
				RouteTest.sampleRouteWithEmptyFields(),
				RouteTest.sampleRouteWithNullFields(),
				RouteTest.sampleRouteWithSingleFields()));
		engine.setDefinitions(Arrays.asList(DefinitionTest.sampleDefinition(), DefinitionTest.sampleDefinition2()));
		return engine;
	}

	private Engine sampleEngineWithEmptyFields() {
		final int rlcVersion = 0;
		final int compatibleRlcVersion = 0;
		final List<CommunicationPoint> communicationPoints = new ArrayList<CommunicationPoint>();
		final List<Route> routes = new ArrayList<Route>();
		final List<Definition> definition = new ArrayList<Definition>();
		return EngineTest.engine(rlcVersion, compatibleRlcVersion, communicationPoints, routes, definition);
	}

	private Engine sampleEngineWithNullFields() {
		return new Engine();
	}

	public Engine sampleEngineWithLockers() {
		final Engine engine = sampleEngineWithSingleFields();
		engine.setLockers(Arrays.asList(ComponentFactory.get().createBasicLocker(),
				ComponentFactory.get().createBasicLocker(), ComponentFactory.get().createBasicLocker()));
		engine.setRlcVersion(10);
		return engine;
	}

	public Engine sampleEngineWithDynamicRoutes() {
		final Engine engine = sampleEngineWithSingleFields();
		engine.setCommunicationPoints(Arrays.asList(CommunicationPointTest.sampleCommunicationPointWithMultipleFields(), CommunicationPointTest.sampleCommunicationPointWithMultipleFields(), CommunicationPointTest.sampleCommunicationPointWithMultipleFields()));
		engine.getCommunicationPoints().get(1).setType("rhapsody:route");
		return engine;
	}

	public Engine getSample() {
		return sampleEngineWithSingleFields();
	}

	@Before
	public void initialize() {
		this.xstream = new XStreamInstance();
		this.xmlUnit = new XMLTestCase() {
		};
	}

	/**
	 * Test to check that an Engine object is unmarshalled as expected with
	 * single fields only.
	 */
	@Test
	public void testEngineUnmarshallingSingleFields() {
		Engine engine = null;
		final String xml = this.xstream.toXML(sampleEngineWithSingleFields());
		final Object o1 = this.xstream.fromXML(xml);

		if (o1 instanceof Engine) {
			engine = (Engine) o1;
		} else {
			fail("Unmarshalled object was not an engine.");
		}

		try {
			this.xmlUnit.assertXMLEqual("XML Unit test failed. XMLs are not equivalent", this.xstream.toXML(engine), xml);
		} catch (final Exception e) {
			fail("XML Assert threw an excpetion");
			e.printStackTrace();
		}
	}

	/**
	 * Test to check that an Engine object is unmarshalled as expected with
	 * multiple fields. Test also that all definitions have their type set as
	 * expected.
	 */
	@Test
	public void testEngineUnmarshallingMultipleFields() {
		Engine engine = null;
		final String xml = this.xstream.toXML(sampleEngineWithMultipleFields());
		final Object o1 = this.xstream.fromXML(xml);

		if (o1 instanceof Engine) {
			engine = (Engine) o1;
		} else {
			fail("Unmarshalled object was not an engine.");
		}

		final List<Definition> defList = engine.getDefinitions();

		assertEquals(DefinitionType.Unknown, defList.get(0).getType());
		assertEquals(DefinitionType.EDIMessageDefinition, defList.get(1).getType());
		assertEquals(sampleEngineWithMultipleFields().getCommunicationPoints(), engine.getCommunicationPoints());
		assertEquals(sampleEngineWithMultipleFields().getDefinitions(), engine.getDefinitions());
		assertEquals(sampleEngineWithMultipleFields().getLockers(), engine.getLockers());
		assertEquals(sampleEngineWithMultipleFields().getLookupTables(), engine.getLookupTables());
		assertEquals(sampleEngineWithMultipleFields(), engine);
	}

	/**
	 * Test unmarshal with empty engine fields : edge case
	 */
	@Test
	public void testEngineUnmarshallingEmptyFields() {
		Engine engine = null;
		final String xml = this.xstream.toXML(sampleEngineWithEmptyFields());
		final Object o1 = this.xstream.fromXML(xml);

		if (o1 instanceof Engine) {
			engine = (Engine) o1;
		} else {
			fail("Unmarshalled object was not an engine.");
		}

		assertEquals(sampleEngineWithEmptyFields(), engine);

		try {
			this.xmlUnit.assertXMLEqual("XML Unit test failed. XMLs are not equivalent", this.xstream.toXML(engine), xml);
		} catch (final Exception e) {
			fail("XML Assert threw an excpetion");
			e.printStackTrace();
		}
	}

	/**
	 * Test unmarshal with null fields: edge case
	 */
	@Test
	public void testEngineMarshallingNullFields() {
		Engine engine = null;
		final String xml = this.xstream.toXML(sampleEngineWithNullFields());
		final Object o1 = this.xstream.fromXML(xml);

		if (o1 instanceof Engine) {
			engine = (Engine) o1;
		} else {
			fail("Unmarshalled object was not an engine.");
		}

		assertEquals(sampleEngineWithNullFields(), engine);

		try {
			this.xmlUnit.assertXMLEqual("XML Unit test failed. XMLs are not equivalent", this.xstream.toXML(engine), xml);
		} catch (final Exception e) {
			fail("XML Assert threw an excpetion");
			e.printStackTrace();
		}
	}

	/**
	 * Testing that the engine is parsed/ unmarshalled correctly with a custom
	 * Version 10 RLC. The test checks many different properties and navigates
	 * through many branches of the XML schema to ensure that all important
	 * information is stored and parsed (into the correct type) as expected.
	 */
	@Test
	public void testUnmarshallingEngine() {
		Object o1 = null;
		String inputString = "";

		try {
			final File file = new File("test_files/testEngine.txt");
			final Scanner input = new Scanner(file);
			inputString = input.useDelimiter("\\A").next();
			o1 = this.xstream.fromXML(inputString);
			input.close();
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}

		final Engine testEngine = (Engine) o1;
		// check correct number of commpoints
		assertEquals(testEngine.getCommunicationPoints().size(), 11);
		// check correct compatible version
		assertEquals(testEngine.getCompatibleRlcVersion(), 10);
		// check correct RLC version
		assertEquals(testEngine.getRlcVersion(), 10);
		// check that no definitions have been defined.
		assertNull(testEngine.getDefinitions());
		// test size of general properties of the first comm points
		assertEquals(testEngine.getCommunicationPoints().get(0).getGeneralProperties().size(), 4);
		// check value of the first general properties
		assertEquals(testEngine.getCommunicationPoints().get(0).getGeneralProperties().get(0).getValue(), "DONT_START");
		// check the name of the first configuration of the 1st comm point.
		assertEquals(testEngine.getCommunicationPoints().get(0).getConfiguration().get(0).getName(), "ConnectionErrorRetriesIncludedMessages");
		// check number of retries for 1st comm point
		assertEquals(testEngine.getCommunicationPoints().get(0).getNumberOfRetries(), 5);
		// check retry delay for 1st comm point
		assertEquals(testEngine.getCommunicationPoints().get(0).getRetryDelay(), 5000);
		// check that rest clients have not been defined.
		assertNull(testEngine.getRestClients());
		// check that only one route exists
		assertEquals(testEngine.getRoutes().size(), 1);
		// check folder name for route
		assertEquals(testEngine.getRoutes().get(0).getFolder(), "Locker");
		// check priority of a filter
		assertEquals(testEngine.getRoutes().get(0).getFilters().get(0).getPriority(), -1);
		// check the type of configuration of a filter.
		assertEquals(testEngine.getRoutes().get(0).getFilters().get(0).getConfiguration().get(0).getType(), "ptString");
		// check that four definitions exist in the first route.
		assertEquals(testEngine.getRoutes().get(0).getRoutePropertyDefinitions().getRouteProperties().getPropertyList().size(), 4);
	}
}
