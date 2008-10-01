/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.submit.setters;

import java.lang.reflect.Method;
import org.apache.axis2.databinding.types.URI;
import org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType;

/**
 * @author David Muñoz
 *
 */
public class URISetterAdapter implements SetterAdapter {
	
	/*private static final URISetterAdapter instance =
		new URISetterAdapter();
	
	static {
		SetterAdaptersManager.getInstance().registerAdapter(URI.class, instance);
	}*/
	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.adapter.submit.SetterAdapter#setValue(org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType, java.lang.reflect.Method, java.lang.Object)
	 */
	public void setValue(RegistryObjectType object, Method method,
			Object value) {
		String uriString = (String)value;
		
		try {
			URI uri = new URI(uriString);
			method.invoke(object, new Object[]{uri});
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public Class getAdapterClass() {
		return URI.class;
	}

}
