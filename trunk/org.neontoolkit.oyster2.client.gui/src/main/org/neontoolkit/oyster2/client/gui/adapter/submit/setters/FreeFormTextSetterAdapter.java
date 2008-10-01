/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.submit.setters;

import java.lang.reflect.Method;

import org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.FreeFormText;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType;

/**
 * @author David Muñoz
 *
 */
public class FreeFormTextSetterAdapter implements SetterAdapter {

	/*private static final FreeFormTextSetterAdapter instance =
		new FreeFormTextSetterAdapter();
	
	static {
		SetterAdaptersManager.getInstance().registerAdapter(FreeFormText.class, instance);
	}*/
	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.adapter.submit.SetterAdapter#setValue(org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType, java.lang.reflect.Method, java.lang.Object)
	 */
	public void setValue(RegistryObjectType object, Method method,
			Object value) {
		String text = (String)value;
		FreeFormText freeFormText = new FreeFormText();
		freeFormText.setFreeFormText(text);
		try {
			method.invoke(object, new Object[]{freeFormText});
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 

	}

	public Class getAdapterClass() {
		return FreeFormText.class;
	}

}
