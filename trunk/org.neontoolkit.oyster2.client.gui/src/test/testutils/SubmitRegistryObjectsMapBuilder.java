/**
 * 
 */
package testutils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.axis2.databinding.types.URI;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.adapter.submit.PropertiesConfiguredAdapter;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType;


/**
 * @author David Muñoz
 *
 */
public class SubmitRegistryObjectsMapBuilder {
	
	private final static String ATTRIBUTES_SUFFIX = ".attributes";
	
	private final static String ATTR_TYPE_SUFFIX = ".type";
	
	private final static String ATTR_VALUES_SUFFIX = ".values";
	
	private final static String APPEND_NUMBER_PROPERTIES_SUFFIX =".appendNumber";
	
	private final static String adaptersFolder = "submitAdapterFiles";
	
	private static int index = 0;
	
	public static RegistryObjectType getRegistryObject(PropertiesConfiguration configuration,
			String objectId) throws InstantiationException, IllegalAccessException {
		Map<String, Object> properties = null;
		RegistryObjectType registryObject = null;
		PropertiesConfiguredAdapter adapter = null;
		String adapterFileName = configuration.getString(objectId + ".adapterFile");
		adapterFileName = Activator.getDefault().getResourcesDir() + File.separator + 
		adaptersFolder + File.separator + adapterFileName;
		properties = SubmitRegistryObjectsMapBuilder.buildMap(configuration,objectId);
		adapter = new PropertiesConfiguredAdapter(adapterFileName);
		registryObject = adapter.makeAxisObject(properties);
		return registryObject;
	}
	
	/**
	 * Builds the Map that will be used by an adapter to build a
	 * RegistryObjectType.
	 */
	public static Map<String,Object> buildMap(PropertiesConfiguration configuration, String id) {
		String [] attributes = null;
		String valueType = null;
		Map<String, Object> map = new HashMap<String, Object>();
		attributes = configuration.getStringArray(id + ATTRIBUTES_SUFFIX);
		String []valuesArray = null;
		String singleValue = null;
		List<String> needSuffixNumber = null;
		needSuffixNumber = configuration.getList(id + APPEND_NUMBER_PROPERTIES_SUFFIX);
		if (needSuffixNumber == null) {
			// make it empty
			needSuffixNumber = new ArrayList<String>();
		}
		for (String attribute : attributes) {
			valueType = configuration.getString(id + "." + attribute + ATTR_TYPE_SUFFIX);
			if (valueType.equals("array")) {
				valuesArray = configuration.getStringArray(
						id + "." + attribute + ATTR_VALUES_SUFFIX);
				
				map.put(attribute, valuesArray);
			}
			else if (valueType.equals("string")) {
				
				if (needSuffixNumber.contains(attribute)) {
				singleValue = configuration.getString(
						id + "." + attribute + ATTR_VALUES_SUFFIX) + String.valueOf(index++);
				
				}
				else {
					singleValue = configuration.getString(
							id + "." + attribute + ATTR_VALUES_SUFFIX);
					
				}
				map.put(attribute, singleValue);
			}
			
		}
		return map;
	}
}
