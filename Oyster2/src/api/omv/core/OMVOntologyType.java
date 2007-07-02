package api.omv.core;

import java.util.HashSet;
import java.util.Set;

/**
 * The class OMVOntologyType provides the object 
 * representation of the OntologyType class of 
 * OMV ontology.
 * @author Raul Palma
 * @version 0.1, March 2007
 */
public class OMVOntologyType {
	
	private String name;
	private String acronym;
	private String description;
	private String documentation;
	private Set <OMVParty> definedBy = new HashSet <OMVParty>();
	
	public OMVOntologyType()
	    {
	    }
	 	
	public void append(OMVOntologyType element)
    {
		if (this.getName()==null && element.getName()!=null) {this.setName(element.getName());return;}
		if (this.getAcronym()==null && element.getAcronym()!=null) {this.setAcronym(element.getAcronym());return;}
		if (this.getDescription()==null && element.getDescription()!=null) {this.setDescription(element.getDescription());return;}
		if (this.getDocumentation()==null && element.getDocumentation()!=null) {this.setDocumentation(element.getDocumentation());return;}
		if (element.getDefinedBy().size()>0) {this.definedBy.addAll(element.getDefinedBy());return;}
    }
	
	public void setName(String newName)
	{
		this.name=newName;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setAcronym(String newAcronym)
	{
		this.acronym=newAcronym;
	}
	
	public String getAcronym()
	{
		return this.acronym;
	}
	
	public void setDescription(String newDescription)
	{
		this.description=newDescription;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public void setDocumentation(String newDocumentation)
	{
		this.documentation=newDocumentation;
	}
	
	public String getDocumentation()
	{
		return this.documentation;
	}
	
	public void addDefinedBy(OMVParty newDefinedBy)
	{
		this.definedBy.add(newDefinedBy);
	}
	
	public void removeDefinedBy(OMVParty oldDefinedBy)
	{
		this.definedBy.remove(oldDefinedBy);
	}
	
	public Set <OMVParty> getDefinedBy()
	{
		return this.definedBy;
	}
	
}
