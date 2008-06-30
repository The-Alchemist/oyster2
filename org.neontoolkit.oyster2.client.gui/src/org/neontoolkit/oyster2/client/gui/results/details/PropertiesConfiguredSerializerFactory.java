/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.results.details;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.adapter.results.ISearchResult;

/**
 * @author David Muñoz
 *
 */
public class PropertiesConfiguredSerializerFactory implements
		IResultSerializerFactory {
		
	Map<String,String> serializers = null;
	
	public void setPropertiesFile(String []initArgs) {
		serializers = new HashMap<String, String>();
		PropertiesConfiguration generalConfiguration = null;
		
		///////////////////
		String file = Activator.getDefault().getResourcesDir()+File.separator +
		initArgs[0];
		try {
			generalConfiguration =
				new PropertiesConfiguration(file);
			String namespace = null;
			String[] serializerFiles =
				generalConfiguration.getStringArray("serializerFiles"); //$NON-NLS-1$
			for (int i = 0;i<serializerFiles.length;i++) {
				namespace = generalConfiguration.getString(serializerFiles[i]);
				serializers.put(namespace,serializerFiles[i]);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally {
			try {generalConfiguration.clear();} catch (Exception e) {};
		}
	}
	
	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.results.details.IResultSerializerFactory#getSerializer(org.neontoolkit.oyster2.client.gui.adapter.results.ISearchResult)
	 */
	public IResultSerializer getSerializer(ISearchResult result) {
		PropertiesConfiguredResultSerializer serializer =
			new PropertiesConfiguredResultSerializer();
		serializer.setProperties(serializers.get(result.getRDFType()));
		return serializer;
	}

}
