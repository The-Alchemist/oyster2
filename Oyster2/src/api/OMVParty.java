package api;

import java.util.HashSet;
import java.util.Set;

/**
 * The class OMVParty provides the object 
 * representation of the Party class of 
 * OMV ontology.
 * @author Raul Palma
 * @version 0.1, March 2007
 */
public class OMVParty {
	
	private Set <OMVLocation> isLocatedAt = new HashSet <OMVLocation>();
	private Set <OMVOntologyEngineeringTool> developesOntologyEngineeringTool = new HashSet <OMVOntologyEngineeringTool>();
	private Set <OMVOntologyLanguage> developesOntologyLanguage = new HashSet <OMVOntologyLanguage>();
	private Set <OMVOntologySyntax> developesOntologySyntax = new HashSet <OMVOntologySyntax>();
	private Set <OMVKnowledgeRepresentationParadigm> specifiesKnowledgeRepresentationParadigm = new HashSet <OMVKnowledgeRepresentationParadigm>();
	private Set <OMVOntologyType> definesOntologyType = new HashSet <OMVOntologyType>();
	private Set <OMVOntologyEngineeringMethodology> developesOntologyEngineeringMethodology = new HashSet <OMVOntologyEngineeringMethodology>();
	private Set <OMVLicenseModel> specifiesLicense = new HashSet <OMVLicenseModel>();
	private Set <OMVParty> hasAffiliatedParty = new HashSet <OMVParty>();
	private Set <OMVOntology> createsOntology = new HashSet <OMVOntology>();
	private Set <OMVOntology> contributesToOntology  = new HashSet <OMVOntology>();
	
	public OMVParty()
	    {
	    }
	
	public void addIsLocatedAt(OMVLocation newIsLocatedAt)
	{
		this.isLocatedAt.add(newIsLocatedAt);
	}
	
	public void removeIsLocatedAt(OMVLocation oldIsLocatedAt)
	{
		this.isLocatedAt.remove(oldIsLocatedAt);
	}
	
	public Set <OMVLocation> getIsLocatedAt()
	{
		return this.isLocatedAt;
	}
	
	public void addDevelopesOntologyEngineeringTool(OMVOntologyEngineeringTool newDevelopesOntologyEngineeringTool)
	{
		this.developesOntologyEngineeringTool.add(newDevelopesOntologyEngineeringTool);
	}
	
	public void removeDevelopesOntologyEngineeringTool(OMVOntologyEngineeringTool oldDevelopesOntologyEngineeringTool)
	{
		this.developesOntologyEngineeringTool.remove(oldDevelopesOntologyEngineeringTool);
	}
	
	public Set <OMVOntologyEngineeringTool> getDevelopesOntologyEngineeringTool()
	{
		return this.developesOntologyEngineeringTool;
	}
	
	public void addDevelopesOntologyLanguage(OMVOntologyLanguage newDevelopesOntologyLanguage)
	{
		this.developesOntologyLanguage.add(newDevelopesOntologyLanguage);
	}
	
	public void removeDevelopesOntologyLanguage(OMVOntologyLanguage oldDevelopesOntologyLanguage)
	{
		this.developesOntologyLanguage.remove(oldDevelopesOntologyLanguage);
	}
	
	public Set <OMVOntologyLanguage> getDevelopesOntologyLanguage()
	{
		return this.developesOntologyLanguage;
	}
	
	public void addDevelopesOntologySyntax(OMVOntologySyntax newDevelopesOntologySyntax)
	{
		this.developesOntologySyntax.add(newDevelopesOntologySyntax);
	}
	
	public void removeDevelopesOntologySyntax(OMVOntologySyntax oldDevelopesOntologySyntax)
	{
		this.developesOntologySyntax.remove(oldDevelopesOntologySyntax);
	}
	
	public Set <OMVOntologySyntax> getDevelopesOntologySyntax()
	{
		return this.developesOntologySyntax;
	}
	
	public void addSpecifiesKnowledgeRepresentationParadigm(OMVKnowledgeRepresentationParadigm newSpecifiesKnowledgeRepresentationParadigm)
	{
		this.specifiesKnowledgeRepresentationParadigm.add(newSpecifiesKnowledgeRepresentationParadigm);
	}
	
	public void removeSpecifiesKnowledgeRepresentationParadigm(OMVKnowledgeRepresentationParadigm oldSpecifiesKnowledgeRepresentationParadigm)
	{
		this.specifiesKnowledgeRepresentationParadigm.remove(oldSpecifiesKnowledgeRepresentationParadigm);
	}
	
	public Set <OMVKnowledgeRepresentationParadigm> getSpecifiesKnowledgeRepresentationParadigm()
	{
		return this.specifiesKnowledgeRepresentationParadigm;
	}
	
	public void addDefinesOntologyType(OMVOntologyType newDefinesOntologyType)
	{
		this.definesOntologyType.add(newDefinesOntologyType);
	}
	
	public void removeDefinesOntologyType(OMVOntologyType oldDefinesOntologyType)
	{
		this.definesOntologyType.remove(oldDefinesOntologyType);
	}
	
	public Set <OMVOntologyType> getDefinesOntologyType()
	{
		return this.definesOntologyType;
	}
	
	public void addDevelopesOntologyEngineeringMethodology(OMVOntologyEngineeringMethodology newDevelopesOntologyEngineeringMethodology)
	{
		this.developesOntologyEngineeringMethodology.add(newDevelopesOntologyEngineeringMethodology);
	}
	
	public void removeDevelopesOntologyEngineeringMethodology(OMVOntologyEngineeringMethodology oldDevelopesOntologyEngineeringMethodology)
	{
		this.developesOntologyEngineeringMethodology.remove(oldDevelopesOntologyEngineeringMethodology);
	}
	
	public Set <OMVOntologyEngineeringMethodology> getDevelopesOntologyEngineeringMethodology()
	{
		return this.developesOntologyEngineeringMethodology;
	}
	
	public void addSpecifiesLicense(OMVLicenseModel newSpecifiesLicense)
	{
		this.specifiesLicense.add(newSpecifiesLicense);
	}
	
	public void removeSpecifiesLicense(OMVLicenseModel oldSpecifiesLicense)
	{
		this.specifiesLicense.remove(oldSpecifiesLicense);
	}
	
	public Set <OMVLicenseModel> getSpecifiesLicense()
	{
		return this.specifiesLicense;
	}
	
	public void addHasAffiliatedParty(OMVParty newHasAffiliatedParty)
	{
		this.hasAffiliatedParty.add(newHasAffiliatedParty);
	}
	
	public void removeHasAffiliatedParty(OMVParty oldHasAffiliatedParty)
	{
		this.hasAffiliatedParty.remove(oldHasAffiliatedParty);
	}
	
	public Set <OMVParty> getHasAffiliatedParty()
	{
		return this.hasAffiliatedParty;
	}
	
	public void addCreatesOntology(OMVOntology newCreatesOntology)
	{
		this.createsOntology.add(newCreatesOntology);
	}
	
	public void removeCreatesOntology(OMVOntology oldCreatesOntology)
	{
		this.createsOntology.remove(oldCreatesOntology);
	}
	
	public Set <OMVOntology> getCreatesOntology()
	{
		return this.createsOntology;
	}

	public void addContributesToOntology(OMVOntology newContributesToOntology)
	{
		this.contributesToOntology.add(newContributesToOntology);
	}
	
	public void removeContributesToOntology(OMVOntology oldContributesToOntology)
	{
		this.contributesToOntology.remove(oldContributesToOntology);
	}
	
	public Set <OMVOntology> getContributesToOntology()
	{
		return this.contributesToOntology;
	}

}
