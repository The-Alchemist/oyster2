package org.neontoolkit.omv.api.extensions.mapping;


/**
 * The class OMVMappingParameter provides the object 
 * representation of the Parameter class of the Mapping 
 * OMV ontology extension.
 * @author Raul Palma
 * @version 0.1, May 2007
 */
public class OMVMappingParameter {
	private String ID;
	
	public OMVMappingParameter()
    {
    }
	
	public void append(OMVMappingParameter element)
    {
		if (this.getID()==null && element.getID()!=null) {this.setID(element.getID());return;}
    }
	
	public void setID(String newID)
	{
		this.ID=newID;
	}
	
	public String getID()
	{
		return this.ID;
	}
	
	
		
}