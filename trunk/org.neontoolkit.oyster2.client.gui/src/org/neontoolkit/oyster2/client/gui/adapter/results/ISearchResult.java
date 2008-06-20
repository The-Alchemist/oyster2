/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.results;

/**
 * @author David Mu�oz
 *
 */
public interface ISearchResult {

	public String getRDFType();
	
	public String getId();
	
	public String[] getProperties();
	
	public Object getValue(String property);
		
}
