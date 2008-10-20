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
public class OMVObjectRefArraySetterAdapter implements SetterAdapter {

	/*private static final OMVObjectRefSetterAdapter instance =
		new OMVObjectRefSetterAdapter();
	
	static {
		//[Lorg.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType;
		Class targetClass = OMVObjectRefType[].class;
		SetterAdaptersManager.getInstance().registerAdapter(targetClass, instance);
	}
	*/
	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.adapter.submit.SetterAdapter#setValue(org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType, java.lang.reflect.Method, java.lang.Object)
	 */
	public void setValue(RegistryObjectType object, Method method,
			Object value) {
		//we need to check if there's only one id because the WSDL interface
		//specifies an array even for some single - valued properties
		//(i.e. hasOntologyLanguage of Ontology )
		String[] ids = null;
		if (value instanceof String) {
			ids = new String[]{(String)value};
		}
		else {
			ids = (String[])value;
		}
		OMVObjectRefType[] references = new OMVObjectRefType[ids.length];
		int i = 0;
		ReferenceURI uri = null;
		for (String id : ids) {
			uri = OMVUriReferenceUtil.makeReferenceURI4OMV(id);
			references[i] = new OMVObjectRefType();
			references[i].setId(uri);
			i++;
		}
		try {
			method.invoke(object, new Object[]{references});
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	 
	public Class getAdapterClass() {
		return  OMVObjectRefType[].class;
	}
}
