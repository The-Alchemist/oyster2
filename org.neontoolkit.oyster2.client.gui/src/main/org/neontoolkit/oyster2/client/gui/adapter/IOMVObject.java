/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter;

/**
 * @author David Mu�oz
 *
 */
public interface IOMVObject {

	public String getRDFType();
	
	public String getId();
	
	public String[] getProperties();
	
	public Object getValue(String property);
		
}
