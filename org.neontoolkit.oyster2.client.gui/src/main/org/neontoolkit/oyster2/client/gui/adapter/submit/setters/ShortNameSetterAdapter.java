/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.submit.setters;

import java.lang.reflect.Method;

import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName;

/**
 * @author David Mu�oz
 *
 */
public class ShortNameSetterAdapter implements SetterAdapter {

	/*private static final ShortNameSetterAdapter instance =
		new ShortNameSetterAdapter();
	
	static {
		SetterAdaptersManager.getInstance().registerAdapter(ShortName.class, instance);
	}*/
	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.adapter.submit.SetterAdapter#setValue(org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType, java.lang.reflect.Method, java.lang.Object)
	 */
	public void setValue(RegistryObjectType object, Method method,
			Object value) {
		ShortName name = new ShortName();
		name.setShortName((String)value);
		try {
			method.invoke(object,name);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	public Class getAdapterClass() {
		return ShortName.class;
	}
}
