package api;

import java.util.HashSet;
import java.util.Set;


/**
 * The class OMVMappingProperty provides the object 
 * representation of the Property class of the mapping 
 * OMV ontology extension.
 * @author Raul Palma
 * @version 0.1, March 2007
 */
public class OMVMappingProperty {
	private String ID;
	private Set <OMVMappingEvidence> hasEvidence = new HashSet <OMVMappingEvidence>();
	
	public OMVMappingProperty()
    {
    }
	
	public void append(OMVMappingProperty element)
    {
		if (this.getID()==null && element.getID()!=null) {this.setID(element.getID());return;}
		if (element.getHasEvidence().size()>0) {this.hasEvidence.addAll(element.getHasEvidence());return;}
	}
	
	public void setID(String newID)
	{
		this.ID=newID;
	}
	
	public String getID()
	{
		return this.ID;
	}
	
	public Set <OMVMappingEvidence> getHasEvidence()
	{
		return this.hasEvidence;
	}
	
	public void addHasEvidence(OMVMappingEvidence newHasEvidence)
	{
		this.hasEvidence.add(newHasEvidence);
	}
	
	public void removeHasEvidence(OMVMappingMethod oldHasEvidence)
	{
		this.hasEvidence.remove(oldHasEvidence);
	}
		
}	