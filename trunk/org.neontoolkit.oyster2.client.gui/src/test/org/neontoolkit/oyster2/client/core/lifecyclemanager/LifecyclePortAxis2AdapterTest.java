/**
 * 
 */
package org.neontoolkit.oyster2.client.core.lifecyclemanager;

import static org.junit.Assert.*;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.JUnit4TestAdapter;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neontoolkit.oyster2.client.core.lifecycle.LifecyclePortAxis2Adapter;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.adapter.submit.PropertiesConfiguredAdapter;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType;

import org.apache.axis2.databinding.types.URI; 

import testutils.SubmitRegistryObjectsMapBuilder;
import testutils.SubmitRemoveObjects;

/**
 * @author David Muñoz
 *
 */
public class LifecyclePortAxis2AdapterTest {

	private static int index = 0;

	private final static String CONFIGURATION_FILENAME = 
		"LifecyclePortAxis2AdapterTestSubmit.properties";

	private final static String REMOVE_IDENTIFIERS = "remove.identifiers";
	
	private final static String TESTS_KEY = "tests";

	private String adaptersFolder = "submitAdapterFiles";

	private final static String OBJECTS_SUFFIX =".objects";


	private final static String EXPECTED_RESULT_KEY =".result";

	private List<String> submittedObjectsIds = null;

	//key for objects to be submitted in the remove test
	private static final String SUBMIT_KEY = "submit.objects";
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(LifecyclePortAxis2AdapterTest.class);
	}

	// used when submitting the same object multiple times, to avoid
	// having to delete it during the submit test, the properties that
	// will make the identifier will have a number as suffix

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		SubmitRemoveObjects.remove(submittedObjectsIds);
	}

	/**
	 * Test method for {@link org.neontoolkit.oyster2.client.core.lifecycle.LifecyclePortAxis2Adapter#submitObjectsRequest(java.util.List)}.
	 * The tested class will receive a collection of String, String[]
	 * 
	 */
	@Test
	public void testSubmitObjectsRequest() {
		

		String path = Activator.getDefault().getResourcesDir() +
		File.separator + "testFiles" + File.separator + CONFIGURATION_FILENAME;


		PropertiesConfiguration configuration;
		try {

			configuration = new PropertiesConfiguration(path);

		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		String [] tests = configuration.getStringArray(TESTS_KEY);

		List<RegistryObjectType> list = null;
		LifecyclePortAxis2Adapter testedClass = 
			new LifecyclePortAxis2Adapter();
		testedClass.setTargetService("http://localhost:8080/axis2/services/NeOnRegistryOMVOyster");
		testedClass.setTimeoutInMiliseconds(4 * 60 * 1000);
		String result = null;
		String objects[] = null;
		submittedObjectsIds = new LinkedList<String>();
		String thisTest = null;
		String thisTestResult = null;
		RegistryObjectType registryObject = null;
		for (int i = 0;i<tests.length;i++) {
			list = new LinkedList<RegistryObjectType>();
			try {
				thisTest = tests[i];
				thisTestResult = configuration.getString(thisTest +
						EXPECTED_RESULT_KEY);
				objects = configuration.getStringArray(thisTest + OBJECTS_SUFFIX);
				for (String thisObject : objects) {
					registryObject = 
						SubmitRegistryObjectsMapBuilder.getRegistryObject(configuration,thisObject);
					submittedObjectsIds.add(registryObject.getId().toString());
					list.add(registryObject);
				}
				result = testedClass.submitObjectsRequest(list);

				if(thisTestResult.equals(result)) {
					Assert.assertTrue("test " + thisTest, true);
				}
				else {
					Assert.fail("test " + thisTest);
				}
			}

			catch (Exception e) {
				if (!thisTestResult.equals("Failure")) {
					e.printStackTrace();
					Assert.fail("Submitting objects from test: \"" + thisTest + "\"" + e.getMessage());
				}
			}
		}


		// second part: take the test
		// with the required attributes, and try to send that
		// object lacking each one of the required attributes
		Assert.assertTrue(true);


	}

	/*

	private URI getRegistryObject(PropertiesConfiguration configuration, String objectId,
			List<RegistryObjectType> list) throws InstantiationException,
			IllegalAccessException {
		Map<String, Object> properties = null;
		RegistryObjectType registryObject = null;
		PropertiesConfiguredAdapter adapter = null;
		String adapterFileName = configuration.getString(objectId + ".adapterFile");
		adapterFileName = Activator.getDefault().getResourcesDir() + File.separator + 
		adaptersFolder + File.separator + adapterFileName;
		properties = SubmitRegistryObjectsMapBuilder.buildMap(configuration,objectId);
		adapter = new PropertiesConfiguredAdapter(adapterFileName);
		registryObject = adapter.makeAxisObject(properties);
		URI id = registryObject.getId();
		list.add(registryObject);
		return id;
	}
*/
	/**
	 * Test method for {@link org.neontoolkit.oyster2.client.core.lifecycle.LifecyclePortAxis2Adapter#updateObjectsRequest(java.util.List)}.
	 */
	@Test
	public void testUpdateObjectsRequest() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.neontoolkit.oyster2.client.core.lifecycle.LifecyclePortAxis2Adapter#removeObjectsRequest(java.util.List)}.
	 */
	@Test
	public void testRemoveObjectsRequest() {
		LifecyclePortAxis2Adapter lifecyclePort = 
			new LifecyclePortAxis2Adapter();
		lifecyclePort.setTargetService("http://localhost:8080/axis2/services/NeOnRegistryOMVOyster");
		lifecyclePort.setTimeoutInMiliseconds(4 * 60 * 1000);
		
			
		PropertiesConfiguration configuration = null;
		String path = Activator.getDefault().getResourcesDir() +
		File.separator + "testFiles" + File.separator + CONFIGURATION_FILENAME;
		try {
			configuration = new PropertiesConfiguration(path);
		} catch (ConfigurationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		List<RegistryObjectType> objects = new LinkedList<RegistryObjectType>();
		String [] objectNames = configuration.getStringArray(SUBMIT_KEY);
		RegistryObjectType registryObject = null;
		try {
			for (String thisObject : objectNames) {
				registryObject = 
					SubmitRegistryObjectsMapBuilder.getRegistryObject(configuration,thisObject);
				objects.add(registryObject);
			}
			String result = lifecyclePort.submitObjectsRequest(objects);
			Assert.assertEquals("Success", result);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		// now to the test
		List<String> objectsToBeRemoved = configuration.getList(REMOVE_IDENTIFIERS);
		String result = SubmitRemoveObjects.remove(objectsToBeRemoved);
		Assert.assertEquals("Success", result);
	}

}
