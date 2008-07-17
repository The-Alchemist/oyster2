/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.submit.setters;

import java.lang.reflect.Method;
import java.math.BigInteger;

import org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType;

/**
 * @author David Muñoz
 *
 */
public class BigIntegerSetterAdapter implements SetterAdapter {

	//private static final BigIntegerSetterAdapter instance =
	//	new BigIntegerSetterAdapter();
	
	//static {
	//	SetterAdaptersManager.getInstance().registerAdapter(BigInteger.class, instance);
	//}
	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.adapter.submit.SetterAdapter#setValue(org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType, java.lang.reflect.Method, java.lang.Object)
	 */
	public void setValue(RegistryObjectType object, Method method,
			Object value) {
		BigInteger integer = new BigInteger(value.toString());
		try {
			method.invoke(object, new Object[]{integer});
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	public Class getAdapterClass() {
		return BigInteger.class;
	}

}
