/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.search;

import java.util.ArrayList;


/**
 * @author David Muñoz
 *
 */
public class Filter {
	
	private FilterType type = null;
	
	private ArrayList<FilterValue> filterValues = null;
	
	private Boolean multi = null;

	
	public Filter() {
		filterValues = new ArrayList<FilterValue>();
	}
	
	/**
	 * @return the filterValues
	 */
	public final ArrayList<FilterValue> getFilterValues() {
		return filterValues;
	}

	/**
	 * @param filterValues the filterValues to set
	 */
	public final void setFilterValues(ArrayList<FilterValue> filterValues) {
		this.filterValues = filterValues;
	}

	/**
	 * @return the multi
	 */
	public final Boolean getMulti() {
		return multi;
	}

	/**
	 * @param multi the multi to set
	 */
	public final void setMulti(Boolean multi) {
		this.multi = multi;
	}

	/**
	 * @return the type
	 */
	public final FilterType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public final void setType(FilterType type) {
		this.type = type;
	}

	
}
