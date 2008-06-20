/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.results.details;


/**
 * @author David Muñoz
 *
 */
public class SearchLink implements ISearchLink {

	private String target = null;
	
	private Object value = null;
	
	private String attribute = null;


	/**
	 * @return the attribute
	 */
	public final String getAttributeNames() {
		return attribute;
	}

	/**
	 * @param attribute the attribute to set
	 */
	public final void setAttributeNames(Object attribute) {
		this.attribute = (String)attribute;
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

	/**
	 * @return the isParty
	 */
	public final boolean isParty() {
		return false;
	}

	/**
	 * @param isParty the isParty to set
	 */
	public final void setParty(boolean isParty) {
		
	}
	
	
	
}
