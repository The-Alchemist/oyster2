/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.results.details;

/**
 * @author David Muñoz
 *
 */
public interface ISearchLink {
	
	/**
	 * @return the target
	 */
	public String getTarget();

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target);

	
	/**
	 * @return the attribute
	 */
	public Object getAttributeNames();
		
	/**
	 * @param attribute the attribute to set
	 */
	public void setAttributeNames(Object attribute);

	
	
	/**
	 * @return the value
	 */
	public Object getValue();
	/**
	 * @param value the value to set
	 */
	public void setValue(Object value);

	/**
	 * @return the isParty
	 */
	public boolean isParty();

	/**
	 * @param isParty the isParty to set
	 */
	public void setParty(boolean isParty);
	
	
}
