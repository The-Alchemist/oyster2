/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.results.details;

import java.util.Map;

/**
 * @author David Muñoz
 *
 */
public class PartySearchLink implements ISearchLink {

	private String target = null;
	
	private Object value = null;
	
	private Map<String,String> attributeNames = null;

	private boolean isParty = false;

	/**
	 * @return the attributeNames
	 */
	public final Map<String, String> getAttributeNames() {
		return attributeNames;
	}

	/**
	 * @return the isParty
	 */
	public final boolean isParty() {
		return isParty;
	}

	/**
	 * @param isParty the isParty to set
	 */
	public final void setParty(boolean isParty) {
		this.isParty = isParty;
	}

	/**
	 * @return the target
	 */
	public final String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public final void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the value
	 */
	public final Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public final void setValue(Object value) {
		this.value = value;
	}

	public void setAttributeNames(Object attribute) {
		attributeNames = (Map<String, String>)attribute;
	}

	
	
}
