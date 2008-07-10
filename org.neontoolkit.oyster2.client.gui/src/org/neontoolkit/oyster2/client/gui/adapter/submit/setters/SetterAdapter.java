/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.submit.setters;

import java.lang.reflect.Method;

import org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType;

/**
 * @author David Muñoz
 *
 */
public interface SetterAdapter {
	public void setValue(OMVRegistryObjectType object,
			Method method, Object value);
	public Class getAdapterClass();
}
