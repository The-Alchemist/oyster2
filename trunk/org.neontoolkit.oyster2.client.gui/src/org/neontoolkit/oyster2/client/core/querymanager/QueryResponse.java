/**
 * 
 */
package org.neontoolkit.oyster2.client.core.querymanager;

import org.oasis.names.tc.ebxml_regrep.xsd.rim.IdentifiableType;

/**
 * @author David Muñoz
 *
 */
public class QueryResponse {
	
	private String stringMessage = null;
	
	private IdentifiableType[] objects = null;
	
	public final IdentifiableType[] getObjects() {
		return objects;
	}

	public final void setObjects(IdentifiableType[] objects) {
		this.objects = objects;
	}

	public final String getStringMessage() {
		return stringMessage;
	}

	public final void setStringMessage(String stringMessage) {
		this.stringMessage = stringMessage;
	}
	
	@Override
	public String toString() {
		return stringMessage;
	}
}
