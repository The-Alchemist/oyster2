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
public interface SetterAdapter {
	public void setValue(RegistryObjectType object,
			Method method, Object value);
	public Class getAdapterClass();
}
