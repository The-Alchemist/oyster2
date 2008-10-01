/**
 * 
 */
package org.neontoolkit.oyster2.client.core.querymanager;

import static org.junit.Assert.*;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.JUnit4TestAdapter;

import org.apache.axis2.databinding.types.URI;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neontoolkit.oyster2.client.core.lifecycle.LifecyclePortAxis2Adapter;
import org.neontoolkit.oyster2.client.core.lifecyclemanager.LifecyclePortAxis2AdapterTest;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.IdentifiableType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType;
import org.semanticweb.kaon2.co;

import testutils.QueryBuilderFromProperties;
import testutils.SubmitRegistryObjectsMapBuilder;
import testutils.SubmitRemoveObjects;

/**
 * @author whisp
 *
 */
public class QueryPortAxis2AdapterTest {
	
	
	private static final String TESTS_KEY = "tests";
	
	private static final String EXPECTED_IDS_SUFFIX = ".expected";
	
	private static final String IDENTIFIERS_KEY = "identifiers";
	
	private static final String CONFIGURATION_FILENAME = "QueryPortAxis2AdapterTest.properties";

	private static final String SUBMIT_KEY = "submit.objects";
	

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(QueryPortAxis2AdapterTest.class);
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		LifecyclePortAxis2Adapter lifecyclePort = 
			new LifecyclePortAxis2Adapter();
		lifecyclePort.setTargetService("http://localhost:8080/axis2/services/NeOnRegistryOMVOyster");
		lifecyclePort.setTimeoutInMiliseconds(4 * 60 * 1000);
		
			
		PropertiesConfiguration configuration = null;
		String path = Activator.getDefault().getResourcesDir() +
		File.separator + "testFiles" + File.separator + CONFIGURATION_FILENAME;
		configuration = new PropertiesConfiguration(path);
		List<RegistryObjectType> objects = new LinkedList<RegistryObjectType>();
		String [] objectNames = configuration.getStringArray(SUBMIT_KEY);
		RegistryObjectType registryObject = null;
		for (String thisObject : objectNames) {
			registryObject = 
				SubmitRegistryObjectsMapBuilder.getRegistryObject(configuration,thisObject);
			objects.add(registryObject);
		}
		String result = lifecyclePort.submitObjectsRequest(objects);
		Assert.assertEquals("Success", result);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {

		PropertiesConfiguration configuration = null;
		String path = Activator.getDefault().getResourcesDir() +
		File.separator + "testFiles" + File.separator + CONFIGURATION_FILENAME;
		
		try {
			configuration = new PropertiesConfiguration(path);
		} catch (ConfigurationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		List<String> objectsToRemove = configuration.getList(IDENTIFIERS_KEY);
		SubmitRemoveObjects.remove(objectsToRemove);
	}

	
	/**
	 * Test method for {@link org.neontoolkit.oyster2.client.core.querymanager.QueryPortAxis2Adapter#submitQueryRequest(java.lang.String, java.lang.String, java.util.Map)}.
	 */
	@Test
	public void testSubmitQueryRequest() {
		
		PropertiesConfiguration configuration = null;
		String path = Activator.getDefault().getResourcesDir() +
		File.separator + "testFiles" + File.separator + CONFIGURATION_FILENAME;
		
		try {
			configuration = new PropertiesConfiguration(path);
		} catch (ConfigurationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		QueryPortAxis2Adapter testedClass = 
			new QueryPortAxis2Adapter();
		testedClass.setTargetService("http://localhost:8080/axis2/services/NeOnRegistryOMVOyster");
		testedClass.setTimeoutInMiliseconds(4 * 60 * 1000);
		
		
		String [] tests = configuration.getStringArray(TESTS_KEY);
		Map<String,Object> builtFilters = null;
		String javaClass = null;
		List<String> expectedIds = null;
		QueryResponse response  = null;
		try {
			for (String test : tests) {
				javaClass = configuration.getString(test + QueryBuilderFromProperties.TEST_CLASS_SUFFIX);
				builtFilters = QueryBuilderFromProperties.getFilters(test, configuration);
				response = testedClass.submitQueryRequest(javaClass, null, builtFilters);
				expectedIds = configuration.getList(test + EXPECTED_IDS_SUFFIX);
				testSameIds(test,expectedIds,response.getObjects());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		Assert.assertTrue(true);
	}


	private void testSameIds(String testName,List<String> expectedIds,
			IdentifiableType[] identifiables) {
		URI id = null;
		for (IdentifiableType identifiable : identifiables) {
			id = identifiable.getId();
			if (expectedIds.contains(id.toString())) {
				expectedIds.remove(id.toString());
			}
			else {
				Assert.fail("Test " + "\"" + testName + "\" Query received unexpected id in response: \"" +
						id.toString() + "\"");
			}
		}
		if (expectedIds.size() != 0) {
			String notReceivedIds = "";
			for (String expectedId : expectedIds) {
				notReceivedIds += "\"" + expectedId + "\"; ";
			}
			Assert.fail("Test " + "\"" + testName + "\" Query didn't receive expected ids: \"" +
					notReceivedIds + "\"");
		}
		
	}

}
