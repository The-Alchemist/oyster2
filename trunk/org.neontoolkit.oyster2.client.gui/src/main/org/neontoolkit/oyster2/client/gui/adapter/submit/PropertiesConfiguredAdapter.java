/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.submit;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.axis2.databinding.types.URI;
import org.apache.axis2.databinding.types.URI.MalformedURIException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.neontoolkit.oyster2.client.gui.adapter.submit.setters.SetterAdapter;
import org.neontoolkit.oyster2.client.gui.adapter.submit.setters.SetterAdaptersManager;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType;

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

	public RegistryObjectType makeAxisObject(Map<String,Object> properties)
		throws InstantiationException, IllegalAccessException {
		
		RegistryObjectType axisObject = 
			(RegistryObjectType)axisObjectClass.newInstance();
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

	private void makeIdentifier(RegistryObjectType axisObject,
			Map<String, Object> properties) {
		String field = null;
		String literal = null;
		StringBuffer identifierBuffer = new StringBuffer(identifier);
		String builtIdentifier = ""; 
		int fieldEnd = 0;
		int current = 0;
		
		while (current < identifierBuffer.length()) {
			fieldEnd = getTokenEnd(identifierBuffer,current+1);
			
			if (identifierBuffer.charAt(current) == '\"') {
				if (fieldEnd != -1) {
					current++;
					literal = identifierBuffer.substring(current, fieldEnd);
					current = fieldEnd+1; // skip the last "
					builtIdentifier = builtIdentifier + literal;
					
				}
				else {
					throw new RuntimeException("Unfinished literal in identifier");
				}
			}
			
			else {
				if (fieldEnd == -1)
					fieldEnd = identifierBuffer.length();
				if (identifierBuffer.charAt(current) == ' ') {
					current++;
				}

				field = identifierBuffer.substring(current, fieldEnd);
				current = fieldEnd;
				builtIdentifier = builtIdentifier + properties.get(field);
				System.out.println("id " + field);
				System.out.println("value " + properties.get(field));
				
			}
			
		}
		try {
			axisObject.setId(new URI(builtIdentifier));
		} catch (MalformedURIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	private static int getTokenEnd(StringBuffer identifierBuffer, int current) {
		char c;
		for (int i = current;i<identifierBuffer.length();i++) {
			c = identifierBuffer.charAt(i);
			if ((c == '\"') || (c== ' '))
					return i;
		}
		return -1;
	}

	
	
	
}
