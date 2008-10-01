/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.search;

import java.io.File;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.JUnit4TestAdapter;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Test;
import org.neontoolkit.oyster2.client.core.querymanager.QueryPortAxis2Adapter;
import org.neontoolkit.oyster2.client.gui.Activator;

import testutils.QueryBuilderFromProperties;

/**
 * @author David Muñoz
 *
 */
public class QueryBuilderAdapterTest {

	private final static String CONFIGURATION_FILENAME = "QueryBuilderAdapterTest.properties";
	
	private final static String TEST_NAMES_KEY = "test.names";
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(QueryBuilderAdapterTest.class);
	}

	
	/**
	 * Test method for {@link org.neontoolkit.oyster2.client.gui.search.QueryBuilderAdapter#getFilters(java.lang.String, java.util.Map)}.
	 * getFilters accepts a collection of filters, each one associated with a setter method
	 * of a Java class. The configuration file must contain a list of test names.
	 * Each of this tests need:
	 * <ol>
	 * <li>Target Java class. Currently only {@link org.neontoolkit.registry.omv.xsd.query.OntologyQueryType}
	 * is supported</li>
	 * <li>List of attributes to be tested</li>
	 * <li>For each attribute it needs:
	 * <ol>
	 * <li>attribute setter name</li>
	 * <li>attribute type, being String, Date, Boolean, Integer, for FilterType</li>
	 * <li>attribute filter value type, being one of Simple, Party</li>
	 * <li>flag specifying if the attribute is multiple for branches
	 * and nested queries</li>
	 * </ol>
	 * </li>
	 * </ol>
	 */
	@Test
	public void testGetFilters() {
		String path = Activator.getDefault().getResourcesDir() +
		File.separator + "testFiles" + File.separator + CONFIGURATION_FILENAME;

		PropertiesConfiguration configuration = null;
		String [] testNames = null;
		
		try {
			configuration = new PropertiesConfiguration(path);
		} catch (ConfigurationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		testNames = configuration.getStringArray(TEST_NAMES_KEY);
		for (String test : testNames) {
			testCase(test,configuration);
		}
	}

	private void testCase(String test, PropertiesConfiguration configuration) {
		String javaClass = configuration.getString(test + QueryBuilderFromProperties.TEST_CLASS_SUFFIX);
		Map<String,Object> builtFilters = 
			QueryBuilderFromProperties.getFilters(test, configuration);
		try {
			QueryPortAxis2Adapter queryPortAxis2Adapter = new QueryPortAxis2Adapter();
			queryPortAxis2Adapter.setTargetService("http://localhost:8080/axis2/services/NeOnRegistryOMVOyster");
			queryPortAxis2Adapter.setTimeoutInMiliseconds(4 * 60 * 1000);
			queryPortAxis2Adapter.submitQueryRequest(javaClass,null, builtFilters);
		}
		catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

}
