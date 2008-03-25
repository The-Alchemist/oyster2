package org.neon_toolkit.omv.api.core;

import java.util.HashSet;
import java.util.Set;

/**
 * The class OMVOntologyEngineeringTool provides the object 
 * representation of the OntologyEngineeringTool class of 
 * OMV ontology.
 * @author Raul Palma
 * @version 0.1, March 2007
 */
public class OMVOntologyEngineeringTool extends OMVCoreObject{
	
	private String name;
	private String acronym;
	private String description;
	private String documentation;
	private Set <OMVParty> developedBy = new HashSet <OMVParty>();
	
	public OMVOntologyEngineeringTool()
	    {
	    }
	
	public void append(OMVOntologyEngineeringTool element)
    {
		if (this.getName()==null && element.getName()!=null) {this.setName(element.getName());return;}
		if (this.getAcronym()==null && element.getAcronym()!=null) {this.setAcronym(element.getAcronym());return;}
		if (this.getDescription()==null && element.getDescription()!=null) {this.setDescription(element.getDescription());return;}
		if (this.getDocumentation()==null && element.getDocumentation()!=null) {this.setDocumentation(element.getDocumentation());return;}
		if (element.getDevelopedBy().size()>0) {this.developedBy.addAll(element.getDevelopedBy());return;}
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
	
	public void addDevelopedBy(OMVParty newDevelopedBy)
	{
		this.developedBy.add(newDevelopedBy);
	}
	
	public void removeDevelopedBy(OMVParty oldDevelopedBy)
	{
		this.developedBy.remove(oldDevelopedBy);
	}
	
	public Set <OMVParty> getDevelopedBy()
	{
		return this.developedBy;
	}

}
