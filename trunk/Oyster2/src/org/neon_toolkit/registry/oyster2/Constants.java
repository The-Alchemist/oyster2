package org.neon_toolkit.registry.oyster2;

public class Constants {
	/**
	 * The minimal number of data items required in order to extend the path of a
	 * peer in a direction.
	 */
	public static final int AVG_STORAGE = 4;//5000;

	/**
	 * The build version of this application.
	 */
	public static final String BUILD = "$Revision: 1.1 $".replace('$', ' ').trim() + " (" + "$Date: 2007-10-05 15:12:31 $".replace('$', ' ').trim() + ")";

	/**
	 * The default listening port for incoming RMI connections.
	 */
	public static final int DEFAULT_PORT = 1099;

	/**
	 * The system line seperator.
	 */
	public static final String LINE_SEPERATOR = System.getProperty("line.separator");
	/**
	 * The default property file.
	 */
	public static final String PROPERTY_FILE = new String(System.getProperty("user.home") + System.getProperty("file.separator") + "Oyster2.ini");
	public static final String DMOZURI = "http://dmoz.org/";
	public static final String TopicsURI = "http://daml.umbc.edu/ontologies/topic-ont#Top/";
	public static final String OMVURI = "http://omv.ontoware.org/2005/05/ontology#";
	//public static final String PDURI = "http://localhost/basicRegistry#";
	public static final String POMVURI = "http://omv.ontoware.org/2007/05/pomv#";
	public static final String MOMVURI = "http://omv.ontoware.org/2007/05/mappingomv#";
	public static final String OMV= "http://omv.ontoware.org/2005/05/ontology";
	public static final String LocalRegistryURI = "http://localhost/localRegistry";
	public static final String VERSIONINFO ="http://www.w3.org/2002/07/owl#versionInfo";
	public static final String IMPORT ="imports";
	public static final String OMVIMPORT = OMVURI + IMPORT;
	public static final String omvCondition = "omv:";
	public static final String momvCondition = "momv:";
	public static final String pomvCondition = "pomv:";
	public static final String ontologyConcept = "Ontology";
	public static final String mappingConcept = "Mapping";
	public static final String propertyConcept = "Property";
	public static final String algorithmConcept = "Algorithm";
	public static final String manualMethodConcept = "ManualMethod";
	public static final String filterConcept = "Filter";
	public static final String parallelConcept = "Parallel";
	public static final String sequenceConcept = "Sequence";
	public static final String argumentConcept = "Argument";
	public static final String certificateConcept = "Certificate";
	public static final String proofConcept = "Proof";
	public static final String parameterConcept = "Parameter";
	public static final String mappingMethodConcept = "MappingMethod";
	public static final String evidenceConcept = "Evidence";
	public static final String basicMethodConcept = "BasicMethod";
	public static final String compoundMethodConcept = "CompoundMethod";
	public static final String personConcept = "Person";
	public static final String organisationConcept = "Organisation";
	public static final String partyConcept = "Party";
	public static final String OntologyEngineeringToolConcept = "OntologyEngineeringTool";
	public static final String OntologyEngineeringMethodologyConcept = "OntologyEngineeringMethodology";
	public static final String KnowledgeRepresentationParadigmConcept = "KnowledgeRepresentationParadigm";
	public static final String OntologyDomainConcept = "OntologyDomain";
	public static final String OntologyTypeConcept="OntologyType";
	public static final String OntologyTaskConcept="OntologyTask";
	public static final String OntologyLanguageConcept="OntologyLanguage";
	public static final String OntologySyntaxConcept="OntologySyntax";
	public static final String FormalityLevelConcept="FormalityLevel";
	public static final String LicenseModelConcept="LicenseModel";
	public static final String LocationConcept="Location";
	public static final String URI = "URI";
	public static final String name ="name";
	public static final String acronym="acronym";
	public static final String description="description";
	public static final String documentation="documentation";
	public static final String hasDomain = "hasDomain";
	//public static final String richnessOfContent = "richnessOfContent";
	public static final String numberOfAxioms = "numberOfAxioms";
	public static final String numberOfClasses = "numberOfClasses";
	public static final String numberOfIndividuals = "numberOfIndividuals";
	public static final String numberOfProperties = "numberOfProperties";
	public static final String useImports = "useImports";
	public static final String version = "version";
	public static final String hasPriorVersion = "hasPriorVersion";
	public static final String isBackwardCompatibleWith = "isBackwardCompatibleWith";
	public static final String isIncompatibleWith = "isIncompatibleWith";
	public static final String creationDate = "creationDate";
	public static final String naturalLanguage = "naturalLanguage";
	public static final String modificationDate = "modificationDate";
	public static final String keywords = "keywords";
	public static final String status = "status";
	public static final String hasContributor = "hasContributor";
	public static final String hasCreator = "hasCreator";
	public static final String usedOntologyEngineeringTool = "usedOntologyEngineeringTool";
	public static final String usedOntologyEngineeringMethodology = "usedOntologyEngineeringMethodology";
	public static final String usedKnowledgeRepresentationParadigm = "usedKnowledgeRepresentationParadigm";
	public static final String isOfType = "isOfType";
	public static final String designedForOntologyTask="designedForOntologyTask";
	public static final String hasOntologyLanguage="hasOntologyLanguage";
	public static final String hasOntologySyntax="hasOntologySyntax";
	public static final String hasFormalityLevel="hasFormalityLevel";
	public static final String resourceLocator="resourceLocator";
	public static final String hasLicense="hasLicense";
	public static final String isLocatedAt="isLocatedAt";
	public static final String developesOntologyEngineeringTool="developesOntologyEngineeringTool";
	public static final String developesOntologyLanguage="developesOntologyLanguage";
	public static final String developesOntologySyntax="developesOntologySyntax";
	public static final String specifiesKnowledgeRepresentationParadigm="specifiesKnowledgeRepresentationParadigm";
	public static final String definesOntologyType="definesOntologyType";
	public static final String developesOntologyEngineeringMethodology="developesOntologyEngineeringMethodology";
	public static final String specifiesLicense="specifiesLicense";
	public static final String hasAffiliatedParty="hasAffiliatedParty";
	public static final String createsOntology="createsOntology";
	public static final String contributesToOntology="contributesToOntology";
	public static final String developedBy="developedBy";
	public static final String specifiedBy="specifiedBy";
	public static final String definedBy="definedBy";
	public static final String supportsRepresentationParadigm="supportsRepresentationParadigm";
	public static final String hasSyntax="hasSyntax";
	public static final String state="state";
	public static final String country="country";
	public static final String city="city";
	public static final String street="street";
	public static final String lastName="lastName";
	public static final String firstName="firstName";
	public static final String eMail="eMail";
	public static final String phoneNumber="phoneNumber";
	public static final String faxNumber="faxNumber";
	public static final String isContactPerson="isContactPerson";
	public static final String hasContactPerson="hasContactPerson";
	public static final String GUID="GUID";
	public static final String IPAdress="IPAdress";
	public static final String localPeer="localPeer";
	public static final String peerType="peerType";
	public static final String contextOntology="contextOntology";
	public static final String ontologyOMVLocation="ontologyOMVLocation";
	public static final String provideOntology="provideOntology";
	public static final String isSubDomainOf="isSubDomainOf";
	public static final String acquaintedWith="acquaintedWith";
	public static final String hasExpertise="hasExpertise";
	public static final String provideMapping="provideMapping";
	public static final String mappingOMVLocation="mappingOMVLocation";
	public static final String hasProperty = "hasProperty";
	public static final String usedMethod = "usedMethod";
	public static final String hasEvidence = "hasEvidence";
	public static final String hasSourceOntology = "hasSourceOntology";
	public static final String hasTargetOntology = "hasTargetOntology";
	public static final String level = "level";
	public static final String processingTime="processingTime";
	public static final String purpose="purpose";
	public static final String type="type";
	public static final String ID = "ID";
	public static final String source = "source";
	public static final String value = "value";
	public static final String variety = "variety";
	public static final String filtersMethod = "filtersMethod";
	public static final String aggregatesMethod = "aggregatesMethod";
	public static final String composesMethod = "composesMethod";
	public static final String hasParameter = "hasParameter";
	public static final String timeStamp="timeStamp";
	
	public static final String notes = "notes";
	public static final String keyClasses = "keyClasses";
	public static final String knownUsage = "knownUsage";
	public static final String expressiveness = "expressiveness";
	public static final String isConsistentAccordingToReasoner = "isConsistentAccordingToReasoner";
	public static final String containsTBox = "containsTBox";
	public static final String containsRBox = "containsRBox";
	public static final String containsABox = "containsABox";
	public static final String endorsedBy = "endorsedBy";
	
	/*RDFS Parameters
	 * 
	 */
	
	
	
	/*----------------Preference Parameters---------------------*/
	
	
	
	public static final String LocalRegistry ="localRegistry";
	public static final String PDOntology = "peerDescriptionOntology";
	public static final String MDOntology = "mappingDescriptionOntology";
	public static final String TypeOntology = "typeOntology";
	public static final String TopicOntology = "topicOntology";
	public static final String Image = "image"; 
	public static final String TypeOntologyRoot = "typeOntologyRoot";
	public static final String TopicOntologyRoot = "topicOntologyRoot";
	public static final String SearchCondition_1 = "searchCondition_1";
	public static final String SearchCondition_2 = "searchCondition_2";
	public static final String SearchCondition_3 = "searchCondition_3";
	public static final String SearchCondition_4 = "searchCondition_4";
	public static final String SearchCondition_5 = "searchCondition_5";
	public static final String LocalPeerName = "localPeerName";
	public static final String LocalPeerType = "localPeerType";
	public static final String BootStrapPeerName = "bootStrapPeerName";
	public static final String BootStrapPeerUID = "bootStrapPeerUID";
	public static final String BootStrapPeerIP = "bootStrapPeerIP";
	public static final String PeerRouterIP = "peerRouterIP";
	public static final String DefaultLocalRegistry = "O2serverfiles/localRegistry.owl";
	//public static final String DefaultPDOntology = "O2serverfiles/peersExpertiseRegistry.owl";
	public static final String DefaultPDOntology = "O2serverfiles/pOMV.owl";
	public static final String DefaultMDOntology = "O2serverfiles/mappingOMV.owl";
	public static final String DefaultTypeOntology = "O2serverfiles/OMV.owl";
	public static final String DefaultTopicOntology = "O2serverfiles/dmozT.rdf";
	//public static final String DefaultImage = "file:///O2serverfiles/oyster.jpg";
	public static final String DefaultImage = "file:O2serverfiles/oyster.jpg";
	public static final String DefaultTypeOntologyRoot = "#Ontology";
	public static final String DefaultMappingOntologyRoot = "#Mapping";
	public static final String DefaultTopicOntologyRoot = "Top";
	public static final String DefaultSearchCondition_1 = "omv:name";
	public static final String DefaultSearchCondition_2 = "omv:isOfType";
	public static final String DefaultSearchCondition_3 = "omv:naturalLanguage";
	public static final String DefaultSearchCondition_4 = "omv:hasOntologyLanguage";
	public static final String DefaultSearchCondition_5 = "omv:keywords";
	public static final String DefaultLocalPeerName = "localhost";
	public static final String DefaultLocalPeerType = "S";
	public static final String DefaultBootStrapPeerName = "localhost";
	public static final String DefaultBootStrapPeerIP = "localhost";
	
	public static final String NUMBER_OF_COLUMNS="NUMBER_OF_COLUMNS";
	public static final String COLUMN_TYPE="COLUMN_TYPE";
	public static final String COLUMN_NAME="COLUMN_NAME";
	public static final String COLUMN_WIDTH="COLUMN_WIDTH";
	
	
	
	
}
/*



public static final String SWRCURI = "http://swrc.ontoware.org/ontology#";
public static final String BTURI = "http://protege.stanford.edu/rdf#";
public static final String PROTONTURI = "http://proton.semanticweb.org/2005/04/protont#";
public static final String PROTONSURI = "http://proton.semanticweb.org/2005/04/protons#";
public static final String MappingSource = "mappingSource";
public static final String MappingTarget = "mappingTarget";
public static final String MappingProvider = "mappingProvider";


public static final String Basic_1 ="basicOntolgy_1";
public static final String Basic_2 ="basicOntolgy_2";
public static final String Basic_3 ="basicOntolgy_3";
public static final String Basic_4 ="basicOntolgy_4";
public static final String VirtualOntology ="virtualOntology";

public static final String DefaultBasic_1 = "d:/workspace/KAONp2p/kaonp2p/kaonp2pserver/swrc.owl";
public static final String DefaultBasic_2 = "d:/workspace/KAONp2p/kaonp2p/kaonp2pserver/protons.owl";
public static final String DefaultBasic_3 = "d:/workspace/KAONp2p/kaonp2p/kaonp2pserver/protont.owl";
public static final String DefaultBasic_4 = "d:/workspace/KAONp2p/kaonp2p/kaonp2pserver/omv.owl";
public static final String DefaultVirtualOntology = "d:/workspace/KAONp2p/kaonp2p/kaonp2pserver/virtualOntology.owl";
*/