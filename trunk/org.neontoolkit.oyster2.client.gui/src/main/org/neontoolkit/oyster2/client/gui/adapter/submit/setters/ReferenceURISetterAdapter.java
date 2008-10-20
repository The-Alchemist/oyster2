/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.submit.setters;

import java.lang.reflect.Method;

import org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType;

/**
 * @author David Muñoz
 *
 */
public class ReferenceURISetterAdapter implements SetterAdapter {
	/*
	private static final ReferenceURISetterAdapter instance =
		new ReferenceURISetterAdapter();
	
	static {
		SetterAdaptersManager.getInstance().registerAdapter(ReferenceURI.class, instance);
	}*/
	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.adapter.submit.SetterAdapter#setValue(org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType, java.lang.reflect.Method, java.lang.Object)
	 */
	public void setValue(RegistryObjectType object, Method method,
			Object value) {
		
			String uriString = (String)value;
			ReferenceURI uri = OMVUriReferenceUtil.makeReferenceURI4OMV(uriString);

			try {
				method.invoke(object, new Object[]{uri});
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} 

				
	}

	
	public Class getAdapterClass() {
		return ReferenceURI.class;
	}
	
	
}
