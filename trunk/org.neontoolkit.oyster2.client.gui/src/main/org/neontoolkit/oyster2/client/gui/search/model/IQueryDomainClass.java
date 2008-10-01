/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.search.model;


/**
 * @author David Muñoz
 *
 */
public interface IQueryDomainClass {
	
	public String getDefaultQueryAttribute();
	
	public String[] getCategoryNames();
	
	public String[] getAttributeNames();
	
	public String[] getCategoryAttributes(String category);
	
	public IQueryDomainClassAttribute 
		getAttribute(String attributeName);
	
	public boolean hasCategories();
	
	public Class getTargetJavaClass();
	
	
}
