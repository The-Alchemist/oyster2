package org.neon_toolkit.omv.api.core;

//import java.text.DateFormat;
//import java.util.Date;
//import java.util.Locale;
import java.util.Set;
import java.util.HashSet;

import org.neon_toolkit.registry.api.Oyster2Manager;


/**
 * The class OMVOntology provides the object representation
 * of the Ontology class of OMV ontology.
 * @author Raul Palma
 * @version 1.0, March 2008
 */
public class OMVOntology {
	
	private String URI;
	private Set <String> name = new HashSet<String>();
	private String acronym;
	private String description;
	private String documentation;
	private Set <String> keywords = new HashSet<String>();
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
	private Set <String> naturalLanguage = new HashSet<String>();
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
	private Integer numberOfClasses;
	private Integer numberOfProperties;
	private Integer numberOfIndividuals;
	private Integer numberOfAxioms;
	
	private String notes;
	private Set<String> keyClasses = new HashSet<String>();
	private Set<String> knownUsage = new HashSet<String>();
	private String expressiveness;
	private Boolean isConsistentAccordingToReasoner;
	private Boolean containsTBox;
	private Boolean containsRBox;
	private Boolean containsABox;
	private Set <OMVParty> endorsedBy = new HashSet <OMVParty>();
	
	private String timeStamp;
	
	public OMVOntology()
	    {
	    }
	
	public void append(OMVOntology element)
    {
		if (this.getURI()==null && element.getURI()!=null) {this.setURI(element.getURI());return;}
		if (element.getName().size()>0) {this.name.addAll(element.getName());return;} //if (this.getName()==null && element.getName()!=null) {this.setName(element.getName());return;}
		if (this.getAcronym()==null && element.getAcronym()!=null) {this.setAcronym(element.getAcronym());return;}
		if (this.getDescription()==null && element.getDescription()!=null) {this.setDescription(element.getDescription());return;}
		if (this.getDocumentation()==null && element.getDocumentation()!=null) {this.setDocumentation(element.getDocumentation());return;}
		if (element.getKeywords().size()>0) {this.keywords.addAll(element.getKeywords());return;} //if (this.getKeywords()==null && element.getKeywords()!=null) {this.setKeywords(element.getKeywords());return;}
		if (this.getStatus()==null && element.getStatus()!=null) {this.setStatus(element.getStatus());return;}
		if (this.getCreationDate()==null && element.getCreationDate()!=null) {this.setCreationDate(element.getCreationDate());return;}
		if (this.getModificationDate()==null && element.getModificationDate()!=null) {this.setModificationDate(element.getModificationDate());return;}
		if (element.getHasContributor().size()>0) {this.hasContributor.addAll(element.getHasContributor());return;}
		if (element.getHasCreator().size()>0) {this.hasCreator.addAll(element.getHasCreator());return;}
		if (element.getUsedKnowledgeRepresentationParadigm().size()>0) {this.usedKnowledgeRepresentationParadigm.addAll(element.getUsedKnowledgeRepresentationParadigm());return;}
		if (element.getUsedOntologyEngineeringMethodology().size()>0) {this.usedOntologyEngineeringMethodology.addAll(element.getUsedOntologyEngineeringMethodology());return;}
		if (element.getUsedOntologyEngineeringTool().size()>0) {this.usedOntologyEngineeringTool.addAll(element.getUsedOntologyEngineeringTool());return;}
		if (element.getHasDomain().size()>0) {this.hasDomain.addAll(element.getHasDomain());return;}
		if (this.getIsOfType()==null && element.getIsOfType()!=null) {this.setIsOfType(element.getIsOfType());return;}
		if (element.getNaturalLanguage().size()>0) {this.naturalLanguage.addAll(element.getNaturalLanguage());return;}    //if (this.getNaturalLanguage()==null && element.getNaturalLanguage()!=null) {this.setNaturalLanguage(element.getNaturalLanguage());return;}
		if (element.getDesignedForOntologyTask().size()>0) {this.designedForOntologyTask.addAll(element.getDesignedForOntologyTask());return;}
		if (this.getHasOntologyLanguage()==null && element.getHasOntologyLanguage()!=null) {this.setHasOntologyLanguage(element.getHasOntologyLanguage());return;}
		if (this.getHasOntologySyntax()==null && element.getHasOntologySyntax()!=null) {this.setHasOntologySyntax(element.getHasOntologySyntax());return;}
		if (this.getHasFormalityLevel()==null && element.getHasFormalityLevel()!=null) {this.setHasFormalityLevel(element.getHasFormalityLevel());return;}
		if (this.getResourceLocator()==null && element.getResourceLocator()!=null) {this.setResourceLocator(element.getResourceLocator());return;}
		if (this.getVersion()==null && element.getVersion()!=null) {this.setVersion(element.getVersion());return;}
		if (this.getHasLicense()==null && element.getHasLicense()!=null) {this.setHasLicense(element.getHasLicense());return;}
		if (element.getUseImports().size()>0) {this.useImports.addAll(element.getUseImports());return;}
		if (this.getHasPriorVersion()==null && element.getHasPriorVersion()!=null) {this.setHasPriorVersion(element.getHasPriorVersion());return;}
		if (element.getIsBackwardCompatibleWith().size()>0) {this.isBackwardCompatibleWith.addAll(element.getIsBackwardCompatibleWith());return;}
		if (element.getIsIncompatibleWith().size()>0) {this.isIncompatibleWith.addAll(element.getIsIncompatibleWith());return;}
		if (this.getNumberOfClasses()==null && element.getNumberOfClasses()!=null) {this.setNumberOfClasses(element.getNumberOfClasses());return;}
		if (this.getNumberOfProperties()==null && element.getNumberOfProperties()!=null) {this.setNumberOfProperties(element.getNumberOfProperties());return;}
		if (this.getNumberOfAxioms()==null && element.getNumberOfAxioms()!=null) {this.setNumberOfAxioms(element.getNumberOfAxioms());return;}
		if (this.getNumberOfIndividuals()==null && element.getNumberOfIndividuals()!=null) {this.setNumberOfIndividuals(element.getNumberOfIndividuals());return;}
		if (this.getNotes()==null && element.getNotes()!=null) {this.setNotes(element.getNotes());return;}
		if (element.getKeyClasses().size()>0) {this.keyClasses.addAll(element.getKeyClasses());return;} //if (this.getKeyClasses()==null && element.getKeyClasses()!=null) {this.setKeyClasses(element.getKeyClasses());return;}
		if (this.getKnownUsage().size()>0) {this.knownUsage.addAll(element.getKnownUsage());return;} //if (this.getKnownUsage()==null && element.getKnownUsage()!=null) {this.setKnownUsage(element.getKnownUsage());return;}
		if (this.getExpressiveness()==null && element.getExpressiveness()!=null) {this.setExpressiveness(element.getExpressiveness());return;}
		if (this.getIsConsistentAccordingToReasoner()==null && element.getIsConsistentAccordingToReasoner()!=null) {this.setIsConsistentAccordingToReasoner(element.getIsConsistentAccordingToReasoner());return;}
		if (this.getContainsTBox()==null && element.getContainsTBox()!=null) {this.setContainsTBox(element.getContainsTBox());return;}
		if (this.getContainsRBox()==null && element.getContainsRBox()!=null) {this.setContainsRBox(element.getContainsRBox());return;}
		if (this.getContainsABox()==null && element.getContainsABox()!=null) {this.setContainsABox(element.getContainsABox());return;}
		if (element.getEndorsedBy().size()>0) {this.endorsedBy.addAll(element.getEndorsedBy());return;}
		
		if (this.getTimeStamp()==null && element.getTimeStamp()!=null) {this.setTimeStamp(element.getTimeStamp());return;}
		/*
		if (this.getTimeStamp()==null && element.getTimeStamp()!=null){
			Date now = new Date();
			String sNow = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(now);
			this.setTimeStamp(sNow);
			return;
		}
		*/
    }
	
	/**
	 * Generates an RDF File representing this OMVOntology object.
	 * @param filename is the name of the file that will be
	 * generated.
	 */
	public void generateOMV2RDFFile(String filename)
	{
		if (this.getURI()!=null && this.getName()!=null)
			Oyster2Manager.OMV2RDF(this,filename);
	}

	public void setURI(String newURI)
	{
		this.URI=newURI;
	}
	
	public String getURI()
	{
		return this.URI;
	}
	
	public void addName (String newName)
	{
		this.name.add(newName);
	}
	
	public void removeName (String oldName)
	{
		this.name.remove(oldName);
	}
	
	public Set <String> getName()
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
	
	public void addKeywords (String newKeyword)
	{
		this.keywords.add(newKeyword);
	}
	
	public void removeKeywords (String oldKeyword)
	{
		this.keywords.remove(oldKeyword);
	}
	
	public Set <String> getKeywords()
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
	
	
	public void addNaturalLanguage (String newNaturalLanguage)
	{
		this.naturalLanguage.add(newNaturalLanguage);
	}
	
	public void removeNaturalLanguage (String oldNaturalLanguage)
	{
		this.naturalLanguage.remove(oldNaturalLanguage);
	}
	
	public Set <String> getNaturalLanguage()
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
	
	public void setNumberOfClasses(Integer newNumClasses)
	{
		this.numberOfClasses=newNumClasses;
	}
	
	public Integer getNumberOfClasses()
	{
		return this.numberOfClasses;
	}
	
	public void setNumberOfProperties(Integer newNumProperties)
	{
		this.numberOfProperties=newNumProperties;
	}
	
	public Integer getNumberOfProperties()
	{
		return this.numberOfProperties;
	}
	
	public void setNumberOfIndividuals(Integer newNumIndividuals)
	{
		this.numberOfIndividuals=newNumIndividuals;
	}
	
	public Integer getNumberOfIndividuals()
	{
		return this.numberOfIndividuals;
	}
	
	public void setNumberOfAxioms(Integer newNumAxioms)
	{
		this.numberOfAxioms=newNumAxioms;
	}
	
	public Integer getNumberOfAxioms()
	{
		return this.numberOfAxioms;
	}
	
	public void setTimeStamp(String newTimeStamp)
	{
		this.timeStamp=newTimeStamp;
	}
	
	public String getTimeStamp()
	{
		return this.timeStamp;
	}
	
	public void setNotes(String newValue)
	{
		this.notes=newValue;
	}
	
	public String getNotes()
	{
		return this.notes;
	}
	
	public void addKeyClasses (String newKeyClasses)
	{
		this.keyClasses.add(newKeyClasses);
	}
	
	public void removeKeyClasses (String oldKeyClasses)
	{
		this.keyClasses.remove(oldKeyClasses);
	}
	
	public Set <String> getKeyClasses()
	{
		return this.keyClasses;
	}
	
	public void addKnownUsage (String newKnownUsage)
	{
		this.knownUsage.add(newKnownUsage);
	}
	
	public void removeKnownUsage (String oldKnwonUsage)
	{
		this.knownUsage.remove(oldKnwonUsage);
	}
	
	public Set <String> getKnownUsage()
	{
		return this.knownUsage;
	}
	
	public void setIsConsistentAccordingToReasoner(Boolean newValue)
	{
		this.isConsistentAccordingToReasoner=newValue;
	}
	
	public Boolean getIsConsistentAccordingToReasoner()
	{
		return this.isConsistentAccordingToReasoner;
	}
	
	public void setExpressiveness(String newValue)
	{
		this.expressiveness=newValue;
	}
	
	public String getExpressiveness()
	{
		return this.expressiveness;
	}
	
	public void setContainsTBox(Boolean newValue)
	{
		this.containsTBox=newValue;
	}
	
	public Boolean getContainsTBox()
	{
		return this.containsTBox;
	}
	
	public void setContainsRBox(Boolean newValue)
	{
		this.containsRBox=newValue;
	}
	
	public Boolean getContainsRBox()
	{
		return this.containsRBox;
	}
	
	public void setContainsABox(Boolean newValue)
	{
		this.containsABox=newValue;
	}
	
	public Boolean getContainsABox()
	{
		return this.containsABox;
	}
	
	public void addEndorsedBy(OMVParty newValue)
	{
		this.endorsedBy.add(newValue);
	}
	
	public void removeEndorsedBy(OMVParty oldValue)
	{
		this.endorsedBy.remove(oldValue);
	}
	
	public Set <OMVParty> getEndorsedBy()
	{
		return this.endorsedBy;
	}
	
}


/*
public void setNaturalLanguage(String newNaturalLanguage)
{
	this.naturalLanguage=newNaturalLanguage;
}

public String getNaturalLanguage()
{
	return this.naturalLanguage;
}
*/

//public void addResourceLocator(String newResourceLocator)
//{
//	if (this.resourceLocator!=null && this.resourceLocator!="")
//		this.resourceLocator=this.resourceLocator+";"+newResourceLocator;
//	else
//		this.resourceLocator=newResourceLocator;
//}

//public void removeResourceLocator(String oldResourceLocator)
//{
//	if (this.resourceLocator!=null && this.resourceLocator!=""){
//		int space=this.resourceLocator.indexOf(oldResourceLocator);
//		if (space>=0) {
//			String t1=this.resourceLocator.substring(0, space);
//			t1=t1+this.resourceLocator.substring(space+oldResourceLocator.length()+1,this.resourceLocator.length());
//		}
//	}
//}

/*
public void setName(String newName)
{
	this.name=newName;
}

public String getName()
{
	return this.name;
}
*/

/*
public void setKeywords(String newKeywords)
{
	this.keywords=newKeywords;
}

public String getKeywords()
{
	return this.keywords;
}
*/

/*
public void setKeyClasses(String newValue)
{
	this.keyClasses=newValue;
}

public String getKeyClasses()
{
	return this.keyClasses;
}
*/

/*
public void setKnownUsage(String newValue)
{
	this.knownUsage=newValue;
}

public String getKnownUsage()
{
	return this.knownUsage;
}
*/