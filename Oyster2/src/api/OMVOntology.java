package api;

import java.util.Set;
import java.util.HashSet;
import java.io.*;

/**
 * The class OMVOntology provides the object representation
 * of the Ontology class of OMV ontology.
 * @author Raul Palma
 * @version 0.1, March 2007
 */
public class OMVOntology {
	
	private String URI;
	private String name;
	private String acronym;
	private String description;
	private String documentation;
	private String keywords;
	private String status;
	private String creationDate;
	private String modificationDate;
	private Set <OMVParty> hasContributor = new HashSet<OMVParty>();
	private Set <OMVParty> hasCreator = new HashSet <OMVParty>();
	private Set <OMVOntologyEngineeringTool> usedOntologyEngineeringTool = new HashSet <OMVOntologyEngineeringTool>();
	private Set <OMVOntologyEngineeringMethodology> usedOntologyEngineeringMethodology = new HashSet <OMVOntologyEngineeringMethodology>();
	private Set <OMVKnowledgeRepresentationParadigm> usedKnowledgeRepresentationParadigm = new HashSet <OMVKnowledgeRepresentationParadigm>();
	private Set <OMVOntologyDomain> hasDomain = new HashSet <OMVOntologyDomain>();
	private OMVOntologyType isOfType;
	private String naturalLanguage;
	private Set <OMVOntologyTask> designedForOntologyTask = new HashSet <OMVOntologyTask>();
	private OMVOntologyLanguage hasOntologyLanguage;
	private OMVOntologySyntax hasOntologySyntax;
	private OMVFormalityLevel hasFormalityLevel;
	private String resourceLocator;
	private String version;
	private OMVLicenseModel hasLicense;
	private Set <OMVOntology> useImports = new HashSet <OMVOntology>();
	private OMVOntology hasPriorVersion;
	private Set <OMVOntology> isBackwardCompatibleWith = new HashSet <OMVOntology>();
	private Set <OMVOntology> isIncompatibleWith = new HashSet <OMVOntology>();
	private Integer numClasses;
	private Integer numProperties;
	private Integer numIndividuals;
	private Integer numAxioms;
	private String timeStamp;
	
	public OMVOntology()
	    {
	    }
	 	
	public static File getRDFOMV()
	 	{
		 return new File("OMVInstance.rdf"); 
	 	}

	public void setURI(String newURI)
	{
		this.URI=newURI;
	}
	
	public String getURI()
	{
		return this.URI;
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
	
	public void setKeywords(String newKeywords)
	{
		this.keywords=newKeywords;
	}
	
	public String getKeywords()
	{
		return this.keywords;
	}
	
	public void setStatus(String newStatus)
	{
		this.status=newStatus;
	}
	
	public String getStatus()
	{
		return this.status;
	}
	
	public void setCreationDate(String newCreationDate)
	{
		this.creationDate=newCreationDate;
	}
	
	public String getCreationDate()
	{
		return this.creationDate;
	}
	
	public void setModificationDate(String newModificationDate)
	{
		this.modificationDate=newModificationDate;
	}
	
	public String getModificationDate()
	{
		return this.modificationDate;
	}
	
	public void addHasContributor(OMVParty newHasContributor)
	{
		this.hasContributor.add(newHasContributor);
	}
	
	public void removeHasContributor(OMVParty oldHasContributor)
	{
		this.hasContributor.remove(oldHasContributor);
	}
	
	public Set <OMVParty> getHasContributor()
	{
		return this.hasContributor;
	}
	
	public void addHasCreator(OMVParty newHasCreator)
	{
		this.hasCreator.add(newHasCreator);
	}
	
	public void removeHasCreator(OMVParty oldHasCreator)
	{
		this.hasCreator.remove(oldHasCreator);
	}
	
	public Set <OMVParty> getHasCreator()
	{
		return this.hasCreator;
	}
	
	public void addUsedOntologyEngineeringTool(OMVOntologyEngineeringTool newUsedOntologyEngineeringTool)
	{
		this.usedOntologyEngineeringTool.add(newUsedOntologyEngineeringTool);
	}
	
	public void removeUsedOntologyEngineeringTool(OMVOntologyEngineeringTool oldUsedOntologyEngineeringTool)
	{
		this.usedOntologyEngineeringTool.remove(oldUsedOntologyEngineeringTool);
	}
	
	public Set <OMVOntologyEngineeringTool> getUsedOntologyEngineeringTool()
	{
		return this.usedOntologyEngineeringTool;
	}
	
	public void addUsedOntologyEngineeringMethodology(OMVOntologyEngineeringMethodology newUsedOntologyEngineeringMethodology)
	{
		this.usedOntologyEngineeringMethodology.add(newUsedOntologyEngineeringMethodology);
	}
	
	public void removeUsedOntologyEngineeringMethodology(OMVOntologyEngineeringMethodology oldUsedOntologyEngineeringMethodology)
	{
		this.usedOntologyEngineeringMethodology.remove(oldUsedOntologyEngineeringMethodology);
	}

	public Set <OMVOntologyEngineeringMethodology> getUsedOntologyEngineeringMethodology()
	{
		return this.usedOntologyEngineeringMethodology;
	}
	
	public void addUsedKnowledgeRepresentationParadigm(OMVKnowledgeRepresentationParadigm newUsedKnowledgeRepresentationParadigm)
	{
		this.usedKnowledgeRepresentationParadigm.add(newUsedKnowledgeRepresentationParadigm);
	}
	
	public void removeUsedKnowledgeRepresentationParadigm(OMVKnowledgeRepresentationParadigm oldUsedKnowledgeRepresentationParadigm)
	{
		this.usedKnowledgeRepresentationParadigm.remove(oldUsedKnowledgeRepresentationParadigm);
	}
	
	public Set <OMVKnowledgeRepresentationParadigm> getUsedKnowledgeRepresentationParadigm()
	{
		return this.usedKnowledgeRepresentationParadigm;
	}
	
	public void addHasDomain (OMVOntologyDomain newHasDomain)
	{
		this.hasDomain.add(newHasDomain);
	}
	
	public void removeHasDomain (OMVOntologyDomain oldHasDomain)
	{
		this.hasDomain.remove(oldHasDomain);
	}
	
	public Set <OMVOntologyDomain> getHasDomain()
	{
		return this.hasDomain;
	}
	
	public void setIsOfType (OMVOntologyType newIsOfType)
	{
		this.isOfType=newIsOfType;
	}
	
	public OMVOntologyType getIsOfType()
	{
		return this.isOfType;
	}
	
	public void setNaturalLanguage(String newNaturalLanguage)
	{
		this.naturalLanguage=newNaturalLanguage;
	}
	
	public String getNaturalLanguage()
	{
		return this.naturalLanguage;
	}
	
	public void addDesignedForOntologyTask (OMVOntologyTask newDesignedForOntologyTask)
	{
		this.designedForOntologyTask.add(newDesignedForOntologyTask);
	}
	
	public void removeDesignedForOntologyTask (OMVOntologyTask oldDesignedForOntologyTask)
	{
		this.designedForOntologyTask.remove(oldDesignedForOntologyTask);
	}
	
	public Set <OMVOntologyTask> getDesignedForOntologyTask()
	{
		return this.designedForOntologyTask;
	}
	
	public void setHasOntologyLanguage (OMVOntologyLanguage newHasOntologyLanguage)
	{
		this.hasOntologyLanguage=newHasOntologyLanguage;
	}
	
	public OMVOntologyLanguage getHasOntologyLanguage()
	{
		return this.hasOntologyLanguage;
	}
	
	public void setHasOntologySyntax (OMVOntologySyntax newHasOntologySyntax)
	{
		this.hasOntologySyntax=newHasOntologySyntax;
	}
	
	public OMVOntologySyntax getHasOntologySyntax()
	{
		return this.hasOntologySyntax;
	}
	
	public void setHasFormalityLevel (OMVFormalityLevel newHasFormalityLevel)
	{
		this.hasFormalityLevel=newHasFormalityLevel;
	}
	
	public OMVFormalityLevel getHasFormalityLevel()
	{
		return this.hasFormalityLevel;
	}
	
	public void setResourceLocator(String newResourceLocator)
	{
		this.resourceLocator=newResourceLocator;
	}
	
	public String getResourceLocator()
	{
		return this.resourceLocator;
	}
	
	public void setVersion(String newVersion)
	{
		this.version=newVersion;
	}
	
	public String getVersion()
	{
		return this.version;
	}
	
	public void setHasLicense (OMVLicenseModel newHasLicense)
	{
		this.hasLicense=newHasLicense;
	}
	
	public OMVLicenseModel getHasLicense()
	{
		return this.hasLicense;
	}
	
	public void addUseImports (OMVOntology newUseImports)
	{
		this.useImports.add(newUseImports);
	}
	
	public void removeUseImports (OMVOntology oldUseImports)
	{
		this.useImports.remove(oldUseImports);
	}
	
	public Set <OMVOntology> getUseImports()
	{
		return this.useImports;
	}
	
	public void setHasPriorVersion (OMVOntology newHasPriorVersion)
	{
		this.hasPriorVersion=newHasPriorVersion;
	}
	
	public OMVOntology getHasPriorVersion()
	{
		return this.hasPriorVersion;
	}
	
	public void addIsBackwardCompatibleWith (OMVOntology newIsBackwardCompatibleWith)
	{
		this.isBackwardCompatibleWith.add(newIsBackwardCompatibleWith);
	}
	
	public void removeIsBackwardCompatibleWith (OMVOntology oldIsBackwardCompatibleWith)
	{
		this.isBackwardCompatibleWith.remove(oldIsBackwardCompatibleWith);
	}
	
	public Set <OMVOntology> getIsBackwardCompatibleWith()
	{
		return this.isBackwardCompatibleWith;
	}
	
	public void addIsIncompatibleWith (OMVOntology newIsIncompatibleWith)
	{
		this.isIncompatibleWith.add(newIsIncompatibleWith);
	}
	
	public void removeIsIncompatibleWith (OMVOntology oldIsIncompatibleWith)
	{
		this.isIncompatibleWith.add(oldIsIncompatibleWith);
	}
	
	public Set <OMVOntology> getIsIncompatibleWith()
	{
		return this.isIncompatibleWith;
	}
	
	public void setNumClasses(Integer newNumClasses)
	{
		this.numClasses=newNumClasses;
	}
	
	public Integer getNumClasses()
	{
		return this.numClasses;
	}
	
	public void setNumProperties(Integer newNumProperties)
	{
		this.numProperties=newNumProperties;
	}
	
	public Integer getNumProperties()
	{
		return this.numProperties;
	}
	
	public void setNumIndividuals(Integer newNumIndividuals)
	{
		this.numIndividuals=newNumIndividuals;
	}
	
	public Integer getNumIndividuals()
	{
		return this.numIndividuals;
	}
	
	public void setNumAxioms(Integer newNumAxioms)
	{
		this.numAxioms=newNumAxioms;
	}
	
	public Integer getNumAxioms()
	{
		return this.numAxioms;
	}
	
	public void setTimeStamp(String newTimeStamp)
	{
		this.timeStamp=newTimeStamp;
	}
	
	public String getTimeStamp()
	{
		return this.timeStamp;
	}
	
}
