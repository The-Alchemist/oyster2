package api;

import java.util.HashSet;
import java.util.Set;

/**
 * The class OMVOntologySyntax provides the object 
 * representation of the OntologySyntax class of 
 * OMV ontology.
 * @author Raul Palma
 * @version 0.1, March 2007
 */
public class OMVOntologySyntax {
	
	private String name;
	private String acronym;
	private String description;
	private String documentation;
	private Set <OMVParty> developedBy = new HashSet <OMVParty>();
	
	public OMVOntologySyntax()
	    {
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