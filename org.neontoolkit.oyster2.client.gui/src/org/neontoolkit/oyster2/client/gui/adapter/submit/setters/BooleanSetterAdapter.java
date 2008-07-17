/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.submit.setters;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType;
import org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType;

/**
 * @author david
 *
 */
public class BooleanSetterAdapter implements SetterAdapter {

	/*private static final BooleanSetterAdapter instance =
		new BooleanSetterAdapter();
	
	static {
		SetterAdaptersManager.getInstance().registerAdapter(boolean.class, instance);
	}*/
	
	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.adapter.submit.SetterAdapter#setValue(org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType, java.lang.reflect.Method, java.lang.Object)
	 */
	public void setValue(RegistryObjectType object, Method method,
			Object value) {
		Boolean theValue = Boolean.valueOf((String)value);
		
		try {
			method.invoke(object, new Object[]{theValue.booleanValue()});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	public Class getAdapterClass() {
		return boolean.class;
	}

}
