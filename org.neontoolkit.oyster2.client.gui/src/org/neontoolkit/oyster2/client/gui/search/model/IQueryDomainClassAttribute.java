/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.search.model;

import org.neontoolkit.oyster2.client.gui.search.FilterType;

/**
 * @author David Muñoz
 *
 */
public interface IQueryDomainClassAttribute {
	public boolean hasMultipleParameters();
	
	public void setMultipleParameters(boolean hasMultipleParams);
	
	public String getSetterMethodName();
	
	public FilterType getFilterType();
	
	public void setMultivalued(boolean multivalued);
	
	public boolean getMultivalued();
	
	public void setSetterMethodName(String setterName);
	
	public void setFilterType(FilterType filterType);
	
	public String[] getPredefined();
	
	public void setPredefined(String []predefined);
	
	public String getAttributeClass();
	
	public void setAttributeClass(String attributeClass);
}
