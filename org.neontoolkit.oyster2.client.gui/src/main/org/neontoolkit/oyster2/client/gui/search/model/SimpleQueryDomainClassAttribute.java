/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.search.model;

import org.neontoolkit.oyster2.client.gui.search.FilterType;

/**
 * @author David Muñoz
 *
 */
public class SimpleQueryDomainClassAttribute implements
		IQueryDomainClassAttribute {

	private String [] predefined = null;
	
	private FilterType filterType = null;
	
	private String setterMethodName = null;
	
	private boolean multivalued = false;
	
	private boolean multipleParameters = false;

	private String attributeClass = null;
	
	/**
	 * @return the filterType
	 */
	public final FilterType getFilterType() {
		return filterType;
	}

	/**
	 * @param filterType the filterType to set
	 */
	public final void setFilterType(FilterType filterType) {
		this.filterType = filterType;
	}

	

	/**
	 * @return the multipleParameters
	 */
	public final boolean isMultipleParameters() {
		return multipleParameters;
	}

	/**
	 * @param multipleParameters the multipleParameters to set
	 */
	public final void setMultipleParameters(boolean multipleParameters) {
		this.multipleParameters = multipleParameters;
	}

	/**
	 * @return the setterMethodName
	 */
	public final String getSetterMethodName() {
		return setterMethodName;
	}

	/**
	 * @param setterMethodName the setterMethodName to set
	 */
	public final void setSetterMethodName(String setterMethodName) {
		this.setterMethodName = setterMethodName;
	}

	/**
	 * @return the predefined
	 */
	public final String[] getPredefined() {
		return predefined;
	}

	/**
	 * @param predefined the predefined to set
	 */
	public final void setPredefined(String[] predefined) {
		this.predefined = predefined;
	}

	/**
	 * @return the attributeClass
	 */
	public final String getAttributeClass() {
		return attributeClass;
	}

	/**
	 * @param attributeClass the attributeClass to set
	 */
	public final void setAttributeClass(String attributeClass) {
		this.attributeClass = attributeClass;
	}

	public boolean getMultivalued() {
		return multivalued;
	}

	public boolean hasMultipleParameters() {
		return multipleParameters;
	}

	public void setMultivalued(boolean multivalued) {
		this.multivalued = multivalued;
		
	}


}
