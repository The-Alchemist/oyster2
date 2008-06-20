/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.results;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.configuration.PropertiesConfiguration;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.IMessageResolver;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.IdentifiableType;

/**
 * @author David Muñoz
 *
 */
public class ResultsAdapterFactory {
	
	private static SearchResultPropertyLabelsResolver labelsResolver = null;
	
	private static Map<Class,ResultsAdapterConfiguration> classes = null;
	
	static {
		
		//variables to start loading configuration
		classes = new HashMap<Class, ResultsAdapterConfiguration>();
		String  path = Activator.getDefault().getResourcesDir();
		File factoryConfigFile = new File(path+"ResultsAdapterConfiguration.properties");
		labelsResolver = new SearchResultPropertyLabelsResolver();
		try {
			//configuration objects and variables
			PropertiesConfiguration configProperties = 
				new PropertiesConfiguration(factoryConfigFile);
			
			ResultsAdapterConfiguration configuration = null;
			List<String> responseClasses = 
				(List<String>)configProperties.getList("responseClasses");
			String rdfType = null;
			File configFile = null;
			String fileName = null;
		
			
			//start loading
			for(String className : responseClasses) {
				fileName = path+configProperties.getString(className+".config.file");
				rdfType = configProperties.getString(className +".rdfType");
				configFile = new File(fileName);
			
				configuration = new ResultsAdapterConfiguration();
				configuration.setConfigurationFile(configFile);
				configuration.setRDF_TYPE(rdfType);
				//store
				classes.put(Class.forName(className),configuration);
				
				//store resource bundle
				fileName = configProperties.getString(className+".labels.file");
				labelsResolver.putBundle(className,fileName);
			}
			
			
		configProperties.clear();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
	}
	
	public static IResultsAdapter getResultsList(IdentifiableType[] list) {
		if (list == null)
			return null;
		else if (list.length == 0)
			return null;
			
		try {
			Class containedClass = getContainedClass(list);
			
			ResultsAdapterConfiguration configuration =
				classes.get(containedClass);
			IResultsAdapter resultsAdapter = 
				new ResultsAdapter();
			resultsAdapter.setResultsType(configuration.getRDF_TYPE());
			//load labels
			
			
			//Map<String,String> propertyLabels =
			//	loadLabels(containedClass,configuration);
			IMessageResolver messagesResolver =
				labelsResolver.getMessagesResolver(containedClass.getName());
			resultsAdapter.setList(list, messagesResolver,
					configuration.getConfigurationFile(),
					configuration.getRDF_TYPE());
			return resultsAdapter;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/*
	private static Map<String, String> loadLabels(Class containedClass,
			ResultsAdapterConfiguration configuration) {
		PropertiesConfiguration properties;
		String label = null;
		Map<String, String> labels = new HashMap<String, String>();
		try {
			properties = new PropertiesConfiguration(configuration.getConfigurationFile());
			List resultTypeProperties = properties.getList("propertyNames");
			for (Object property : resultTypeProperties) {
				label = labelsResolver.getString(containedClass.getName(),
						(String)property);
				labels.put((String)property,label);
			}
		} catch (ConfigurationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return labels;
	}
	*/
	/**
	 * returns the actual class at runtime of the first object in the
	 * array
	 * @param array
	 * @return
	 */
	private static Class getContainedClass(IdentifiableType[] array) {
		Object obj = array[0];
		return obj.getClass();
	}
}

class ResultsAdapterConfiguration {
	private String RDF_TYPE = null;
	
	private File configurationFile = null;

	public final File getConfigurationFile() {
		return configurationFile;
	}

	public final void setConfigurationFile(File configurationFile) {
		this.configurationFile = configurationFile;
	}

	public final String getRDF_TYPE() {
		return RDF_TYPE;
	}

	public final void setRDF_TYPE(String rdf_type) {
		RDF_TYPE = rdf_type;
	}
	
	
}
