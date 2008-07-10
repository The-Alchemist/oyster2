/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.submit;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.neontoolkit.oyster2.client.gui.adapter.submit.setters.SetterAdapter;
import org.neontoolkit.oyster2.client.gui.adapter.submit.setters.SetterAdaptersManager;
import org.neontoolkit.oyster2.client.gui.dialogs.content.InputComposite;
import org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType;

/**
 * @author David Muñoz
 *
 */
public class PropertiesConfiguredAdapter {
	
	/**
	 * @author David Muñoz
	 *
	 */
	public class SetterConfiguration {
		private Method method = null;
		
		private SetterAdapter setterAdapter = null;

		public Method getMethod() {
			return method;
		}

		public void setMethod(Method method) {
			this.method = method;
		}

		public SetterAdapter getSetterAdapter() {
			return setterAdapter;
		}

		public void setSetterAdapter(SetterAdapter setterAdapter) {
			this.setterAdapter = setterAdapter;
		}
		
	}

	private final static String SETTER_SUFFIX =".setter";
	
	private final static String ATTRIBUTES_KEY="attributes";
	
	private final static String IDENTIFIER_KEY="identifier";
	
	private final static String SETTER_ARG_SUFFIX = ".setter.argument.class";
	
	private final static String CLASS_KEY = "class";
	
	private Map<String,SetterConfiguration> setterConfigurations = null;
	
	private String identifier = null;
	
	private Class axisObjectClass = null;
	
	public PropertiesConfiguredAdapter(String file) {
		String [] attributes = null;
		try {
			PropertiesConfiguration configuration =
				new PropertiesConfiguration(file);
			attributes = configuration.getStringArray(ATTRIBUTES_KEY);
			identifier = configuration.getString(IDENTIFIER_KEY);
			// this is the actual class of the object axis will use to send the SOAP message
			axisObjectClass = Class.forName(configuration.getString(CLASS_KEY));
			Class argumentClass = null;
			Method setterMethod = null;
			String setterMethodName = null;
			String setterArgClassName = null;
			
			SetterConfiguration setterConfiguration = null;
			SetterAdapter adapter = null;
			setterConfigurations = new HashMap<String, SetterConfiguration>();
			for (String attribute : attributes) {
				setterMethodName = configuration.getString(attribute + SETTER_SUFFIX);
				setterArgClassName = configuration.getString(attribute + SETTER_ARG_SUFFIX);
				
				
				argumentClass = getClass(setterArgClassName);
				setterMethod = axisObjectClass.getMethod(setterMethodName,new Class[]{argumentClass});
				
				setterConfiguration = new SetterConfiguration();
				adapter = SetterAdaptersManager.getInstance().getAdapter(argumentClass);
				setterConfiguration.setSetterAdapter(adapter);
				setterConfiguration.setMethod(setterMethod);
				
				setterConfigurations.put(attribute, setterConfiguration);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private Class getClass(String setterArgClassName) throws Exception {
		if (setterArgClassName.trim().equals("boolean")) {
			return boolean.class;
		}
		return Class.forName(setterArgClassName);
	}

	public OMVRegistryObjectType makeAxisObject(Map<String,Object> properties)
		throws InstantiationException, IllegalAccessException {
		
		OMVRegistryObjectType axisObject = 
			(OMVRegistryObjectType)axisObjectClass.newInstance();
		Method setterMethod = null;
		String attributeName = null;
		
		
		SetterAdapter adapter = null;
		SetterConfiguration configuration = null;
		
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			attributeName = entry.getKey();
			configuration = setterConfigurations.get(attributeName);
			adapter = configuration.getSetterAdapter();
			setterMethod = configuration.getMethod();
			
			adapter.setValue(axisObject, setterMethod, entry.getValue());
		}
		makeIdentifier(axisObject,properties);
		return axisObject;
		
	}

	private void makeIdentifier(OMVRegistryObjectType axisObject,
			Map<String, Object> properties) {
		String field = null;
		StringBuffer identifierBuffer = new StringBuffer(this.identifier);
		int fieldBegin = 0;
		int fieldEnd = 0;
		boolean finished = false;
		while (!finished) {
			fieldBegin = identifierBuffer.indexOf("\"", fieldEnd);
			if (fieldBegin != -1) {
				fieldEnd = identifierBuffer.indexOf("\"", fieldBegin+1);
				
				
			}
		}
		
	}

	
	
	
}
