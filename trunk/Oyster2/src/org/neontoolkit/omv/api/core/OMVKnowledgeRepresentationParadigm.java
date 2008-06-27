package org.neontoolkit.omv.api.core;

import java.util.Set;
import java.util.HashSet;

/**
 * The class OMVKnowledgeRepresentationParadigm provides the object 
 * representation of the KnowledgeReresentationParadigm class of 
 * OMV ontology.
 * @author Raul Palma
 * @version 0.1, March 2007
 */
public class OMVKnowledgeRepresentationParadigm extends OMVCoreObject{
	
	private String name;
	private String acronym;
	private String description;
	private String documentation;
	private Set <OMVParty> specifiedBy = new HashSet <OMVParty>();
	
	public OMVKnowledgeRepresentationParadigm()
	    {
	    }
	
	public void append(OMVKnowledgeRepresentationParadigm element)
    {
		if (this.getName()==null && element.getName()!=null) {this.setName(element.getName()); return;}
		if (this.getAcronym()==null && element.getAcronym()!=null) {this.setAcronym(element.getAcronym());return;}
		if (this.getDescription()==null && element.getDescription()!=null) {this.setDescription(element.getDescription());return;}
		if (this.getDocumentation()==null && element.getDocumentation()!=null) {this.setDocumentation(element.getDocumentation());return;}
		if (element.getSpecifiedBy().size()>0) {this.specifiedBy.addAll(element.getSpecifiedBy());return;}
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
	
	public void addSpecifiedBy(OMVParty newSpecifiedBy)
	{
		this.specifiedBy.add(newSpecifiedBy);
	}
	
	public void removeSpecifiedBy(OMVParty oldSpecifiedBy)
	{
		this.specifiedBy.add(oldSpecifiedBy);
	}
	
	public Set <OMVParty> getSpecifiedBy()
	{
		return this.specifiedBy;
	}
	
}
