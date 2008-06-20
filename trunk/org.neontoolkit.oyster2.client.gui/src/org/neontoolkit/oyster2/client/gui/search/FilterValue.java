/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.search;

/**
 * @author David Muñoz
 * dryadcito at yahoo dot es
 *
 */
public class FilterValue {
	
	
	private Boolean negate = Boolean.FALSE;
	
	private Object value = null;
	
	private String comparator = null;

	
	/**
	 * @return the comparator
	 */
	public final String getComparator() {
		return comparator;
	}

	/**
	 * @param comparator the comparator to set
	 */
	public final void setComparator(String comparator) {
		this.comparator = comparator;
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

	public final Boolean getNegate() {
		return negate;
	}

	public final void setNegate(Boolean negate) {
		this.negate = negate;
	}

}
