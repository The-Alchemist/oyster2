/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.results;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.neontoolkit.oyster2.client.gui.IMessageResolver;
import org.neontoolkit.oyster2.client.gui.adapter.IOMVObject;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.IdentifiableType;


/**
 * @author David Muñoz
 *
 */
public class ResultsAdapter implements IResultsAdapter {
	
	public static final String MAIN_PROPERTIES_KEY = "main.properties";
	
	// maps properties to labels
	//private Map<String,String> properties = null;
	
	private IMessageResolver messagesResolver = null;
	
	private String resultsType = null;
	
	//maps identifier with object
	private Map<String,IOMVObject> results = null;
		
	private String[] mainProperties = null;

	public IOMVObject getResult(String id) {
		return results.get(id);
	}

	public String getLabel(String property) {
		return messagesResolver.getString(property);
	}

	public String[] getResultPropertyNames() {
		
		ArrayList<String>propertiesArray = new ArrayList<String>();
		
		//	new String[messagesResolver.getBundle().getKeys().];
		Enumeration enumeration = messagesResolver.getBundle().getKeys();
		Object element = null;
		while (enumeration.hasMoreElements()) {
			element = enumeration.nextElement();
			propertiesArray.add((String)element);
		}
		String [] properties = new String[propertiesArray.size()];
		return propertiesArray.toArray(properties);
		
	}

	public IOMVObject[] getResults() {
		IOMVObject[] resultsArray =
			new IOMVObject[results.size()];
		return results.values().toArray(resultsArray);
	}

	public void setList(IdentifiableType[] list,IMessageResolver messagesResolver,
			File configurationFile, String RDFType) {
		IOMVObject result = null;
		this.messagesResolver = messagesResolver;
		
		PropertiesConfiguration propertiesConfiguration = null;
		try {
			propertiesConfiguration = new PropertiesConfiguration(configurationFile);
			mainProperties = propertiesConfiguration.getStringArray(MAIN_PROPERTIES_KEY);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
		
		results = new HashMap<String,IOMVObject>();
		for (int i = 0; i<list.length;i++) {
			result = new SearchResult(list[i],configurationFile,RDFType);
			results.put(result.getId(),result);
		}
	}

	/**
	 * @return the mainProperties
	 */
	public final String[] getMainProperties() {
		return mainProperties;
	}

	/**
	 * @param mainProperties the mainProperties to set
	 */
	public final void setMainProperties(String[] mainProperties) {
		this.mainProperties = mainProperties;
	}

	/**
	 * @return the resultsType
	 */
	public final String getResultsType() {
		return resultsType;
	}

	/**
	 * @param resultsType the resultsType to set
	 */
	public final void setResultsType(String resultsType) {
		this.resultsType = resultsType;
	}

	/**
	 * @return the messagesResolver
	 */
	public final IMessageResolver getMessagesResolver() {
		return messagesResolver;
	}

	/**
	 * @param messagesResolver the messagesResolver to set
	 */
	public final void setMessagesResolver(IMessageResolver messagesResolver) {
		this.messagesResolver = messagesResolver;
	}

	

	
	
	
}
