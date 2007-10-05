package org.neon_toolkit.omv.api.core;

/**
 * The class OMVOntologyTask provides the object 
 * representation of the OntologyTask class of 
 * OMV ontology.
 * @author Raul Palma
 * @version 0.1, March 2007
 */
public class OMVOntologyTask {
	
	private String name;
	private String acronym;
	private String description;
	private String documentation;
	
	public OMVOntologyTask()
	    {
	    }
	
	public void append(OMVOntologyTask element)
    {
		if (this.getName()==null && element.getName()!=null) {this.setName(element.getName());return;}
		if (this.getAcronym()==null && element.getAcronym()!=null) {this.setAcronym(element.getAcronym());return;}
		if (this.getDescription()==null && element.getDescription()!=null) {this.setDescription(element.getDescription());return;}
		if (this.getDocumentation()==null && element.getDocumentation()!=null) {this.setDocumentation(element.getDocumentation());return;}
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
		 	
}
