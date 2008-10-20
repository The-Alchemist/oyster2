/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.submit.setters;

import java.lang.reflect.Method;

import org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType;

/**
 * @author David Muñoz
 *
 */
public class OMVObjectRefSetterAdapter implements SetterAdapter {

	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.adapter.submit.SetterAdapter#getAdapterClass()
	 */
	public Class getAdapterClass() {
		return  OMVObjectRefType.class;
	}

	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.adapter.submit.SetterAdapter#setValue(org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType, java.lang.reflect.Method, java.lang.Object)
	 */
	public void setValue(RegistryObjectType object, Method method,
			Object value) {
		String id = (String)value;
		OMVObjectRefType omvObject = new OMVObjectRefType();
		ReferenceURI uri = null;
		
		uri = OMVUriReferenceUtil.makeReferenceURI4OMV(id);
		omvObject.setId(uri);
		
		try {
			method.invoke(object, new Object[]{omvObject});
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		

	}

}
