/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.submit.setters;

import java.lang.reflect.Method;

import org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType;

/**
 * @author David Muñoz
 *
 */
public class StringArraySetterAdapter implements SetterAdapter {

	/*
	private static final StringArraySetterAdapter instance =
		new StringArraySetterAdapter();
	
	static {
		SetterAdaptersManager.getInstance().registerAdapter(String[].class, instance);
	}*/
	
	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.adapter.submit.SetterAdapter#setValue(org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType, java.lang.reflect.Method, java.lang.Object)
	 */
	public void setValue(RegistryObjectType object, Method method,
			Object value) {
		try {
			method.invoke(object, new Object[]{value});
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	
	public Class getAdapterClass() {
		return String[].class;
	}
	
}
