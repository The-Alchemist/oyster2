package api;

import java.util.HashSet;
import java.util.Set;

/**
 * The class OMVOntologyLanguage provides the object 
 * representation of the OntologyLanguage class of 
 * OMV ontology.
 * @author Raul Palma
 * @version 0.1, March 2007
 */
public class OMVOntologyLanguage {
	
	private String name;
	private String acronym;
	private String description;
	private String documentation;
	private Set <OMVParty> developedBy = new HashSet <OMVParty>();
	private Set <OMVKnowledgeRepresentationParadigm> supportsRepresentationParadigm = new HashSet <OMVKnowledgeRepresentationParadigm>();
	private Set <OMVOntologySyntax> hasSyntax = new HashSet <OMVOntologySyntax>();
	
	public OMVOntologyLanguage()
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
		this.developedBy.add(oldDevelopedBy);
	}
	
	public Set <OMVParty> getDevelopedBy()
	{
		return this.developedBy;
	}

	public void addSupportsRepresentationParadigm(OMVKnowledgeRepresentationParadigm newSupportsRepresentationParadigm)
	{
		this.supportsRepresentationParadigm.add(newSupportsRepresentationParadigm);
	}
	
	public void removeSupportsRepresentationParadigm(OMVKnowledgeRepresentationParadigm oldSupportsRepresentationParadigm)
	{
		this.supportsRepresentationParadigm.remove(oldSupportsRepresentationParadigm);
	}
	
	public Set <OMVKnowledgeRepresentationParadigm> getSupportsRepresentationParadigm()
	{
		return this.supportsRepresentationParadigm;
	}
		 	
	public void addHasSyntax(OMVOntologySyntax newHasSyntax)
	{
		this.hasSyntax.add(newHasSyntax);
	}
	
	public void removeHasSyntax(OMVOntologySyntax oldHasSyntax)
	{
		this.hasSyntax.add(oldHasSyntax);
	}
	
	public Set <OMVOntologySyntax> getHasSyntax()
	{
		return this.hasSyntax;
	}

}