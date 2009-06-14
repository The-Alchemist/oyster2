package org.neontoolkit.registry.oyster2;

public class Constants {
	/**
	 * The minimal number of data items required in order to extend the path of a
	 * peer in a direction.
	 */
	public static final int AVG_STORAGE = 4;//5000;

	/**
	 * The build version of this application.
	 */
	public static final String BUILD = "$Revision: 1.1 $".replace('$', ' ').trim() + " (" + "$Date: 2009-06-14 16:43:48 $".replace('$', ' ').trim() + ")";

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
	public static final String PROPERTY_FILE = new String(System.getProperty("user.dir") + System.getProperty("file.separator") + ".propertiesOyster"); //System.getProperty("user.home")
	public static final String DMOZURI = "http://dmoz.org/";
	public static final String TopicsURI = "http://daml.umbc.edu/ontologies/topic-ont#Top/";
	public static final String OMVURI = "http://omv.ontoware.org/2005/05/ontology#";
	//public static final String PDURI = "http://localhost/basicRegistry#";
	public static final String POMVURI = "http://omv.ontoware.org/2007/05/pomv#";
	public static final String MOMVURI = "http://omv.ontoware.org/2007/05/mappingomv#";
	public static final String CHANGEURI = "http://omv.ontoware.org/2007/10/changes#";
	public static final String OWLODMURI = "http://owlodm.ontoware.org/OWL1.1#";
	public static final String OWLCHANGEURI = "http://omv.ontoware.org/2007/07/OWLChanges#";
	public static final String WORKFLOWURI = "http://omv.ontoware.org/2007/07/workflow#";
	public static final String OMV= "http://omv.ontoware.org/2005/05/ontology";
	public static final String LocalRegistryURI = "http://localhost/localRegistry";
	public static final String VERSIONINFO ="http://www.w3.org/2002/07/owl#versionInfo";
	public static final String IMPORT ="imports";
	public static final String OMVIMPORT = OMVURI + IMPORT;
	public static final String peerConcept = "Peer";
	public static final String omvCondition = "omv:";
	public static final String momvCondition = "momv:";
	public static final String pomvCondition = "pomv:";
	public static final String workflowCondition = "workflow:";
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
	public static final String trackOntology = "trackOntology";
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
	
	
	public static final String changeConcept = "Change";
	public static final String atomicChangeConcept = "AtomicChange";
	public static final String compositeChangeConcept = "CompositeChange";
	public static final String entityChangeConcept = "EntityChange";
	public static final String additionConcept = "Addition";
	public static final String removalConcept = "Removal";
	public static final String addSubtreeConcept = "AddSubtree";
	public static final String mergeSiblingsConcept = "MergeSiblings";
	public static final String moveSiblingsDownConcept = "MoveSiblingsDown";
	public static final String moveSiblingsUpConcept = "MoveSiblingsUp";
	public static final String moveSubtreeConcept = "MoveSubtree";
	public static final String removeSubtreeConcept = "RemoveSubtree";
	public static final String splitClassConcept = "SplitClass";
	public static final String annotationPropertyChangeConcept = "AnnotationPropertyChange";
	public static final String addCommentConcept = "AddComment";
	public static final String removeCommentConcept = "RemoveComment";
	public static final String addLabelConcept = "AddLabel";
	public static final String removeLabelConcept = "RemoveLabel";
	public static final String addSubClassOfConcept = "AddSubClassOf";
	public static final String removeSubClassOfConcept = "RemoveSubClassOf";
	public static final String addDisjointClassConcept = "AddDisjointClass";
	public static final String removeDisjointClassConcept = "RemoveDisjointClass";
	public static final String addEquivalentClassConcept = "AddEquivalentClass";
	public static final String removeEquivalentClassConcept = "RemoveEquivalentClass";
	public static final String addDisjointUnionConcept = "AddDisjointUnion";
	public static final String removeDisjointUnionConcept = "RemoveDisjointUnion";
	public static final String addSameIndividualConcept = "AddSameIndividual";
	public static final String removeSameIndividualConcept = "RemoveSameIndividual";
	public static final String addIndividualDataPropertyConcept = "AddIndividualDataProperty";
	public static final String removeIndividualDataPropertyConcept = "RemoveIndividualDataProperty";
	public static final String addIndividualObjectPropertyConcept = "AddIndividualObjectProperty";
	public static final String removeIndividualObjectPropertyConcept = "RemoveIndividualObjectProperty";
	public static final String addDifferentIndividualConcept = "AddDifferentIndividual";
	public static final String removeDifferentIndividualConcept = "RemoveDifferentIndividual";
	public static final String addClassConcept = "AddClass";
	public static final String addIndividualConcept = "AddIndividual";
	public static final String addObjectPropertyConcept = "AddObjectProperty";
	public static final String addDataPropertyConcept = "AddDataProperty";
	public static final String removeClassConcept = "RemoveClass";
	public static final String removeIndividualConcept = "RemoveIndividual";
	public static final String removeDataPropertyConcept = "RemoveDataProperty";
	public static final String removeObjectPropertyConcept = "RemoveObjectProperty";
	public static final String addDatatypeConcept = "AddDatatype";
	public static final String removeDatatypeConcept = "RemoveDatatype";
	public static final String addAnnotationPropertyConcept = "AddAnnotationProperty";
	public static final String removeAnnotationPropertyConcept = "RemoveAnnotationProperty";
	
	public static final String addAsymmetricObjectPropertyConcept = "AddAsymmetricObjectProperty";
	public static final String removeAsymmetricObjectPropertyConcept = "RemoveAsymmetricObjectProperty";
	public static final String addDisjointObjectPropertyConcept = "AddDisjointObjectProperty";
	public static final String removeDisjointObjectPropertyConcept = "RemoveDisjointObjectProperty";
	public static final String addEquivalentObjectPropertyConcept = "AddEquivalentObjectProperty";
	public static final String removeEquivalentObjectPropertyConcept = "RemoveEquivalentObjectProperty";
	public static final String addFunctionalObjectPropertyConcept = "AddFunctionalObjectProperty";
	public static final String removeFunctionalObjectPropertyConcept = "RemoveFunctionalObjectProperty";
	public static final String addInverseFunctionalObjectPropertyConcept = "AddInverseFunctionalObjectProperty";
	public static final String removeInverseFunctionalObjectProperty = "RemoveInverseFunctionalObjectProperty";
	public static final String addInverseObjectPropertyConcept = "AddInverseObjectProperty";
	public static final String removeInverseObjectPropertyConcept = "RemoveInverseObjectProperty";
	public static final String addIrreflexiveObjectPropertyConcept = "AddIrreflexiveObjectProperty";
	public static final String removeIrreflexiveObjectPropertyConcept = "RemoveIrreflexiveObjectProperty";
	public static final String addObjectPropertyDomainConcept = "AddObjectPropertyDomain";
	public static final String removeObjectPropertyDomainConcept = "RemoveObjectPropertyDomain";
	public static final String addObjectPropertyRangeConcept = "AddObjectPropertyRange";
	public static final String removeObjectPropertyRangeConcept = "RemoveObjectPropertyRange";
	public static final String addReflexiveObjectPropertyConcept = "AddReflexiveObjectProperty";
	public static final String removeReflexiveObjectPropertyConcept = "RemoveReflexiveObjectProperty";
	public static final String addSubObjectPropertyOfConcept = "AddSubObjectPropertyOf";
	public static final String removeSubObjectPropertyOfConcept = "RemoveSubObjectPropertyOf";
	public static final String addSymmetricObjectPropertyConcept = "AddSymmetricObjectProperty";
	public static final String removeSymmetricObjectPropertyConcept = "RemoveSymmetricObjectProperty";
	public static final String addTransitiveObjectPropertyConcept = "AddTransitiveObjectProperty";
	public static final String removeTransitiveObjectPropertyConcept = "RemoveTransitiveObjectProperty";
	
	public static final String addDataPropertyDomainConcept = "AddDataPropertyDomain";
	public static final String removeDataPropertyDomainConcept = "RemoveDataPropertyDomain";
	public static final String addDataPropertyRangeConcept = "AddDataPropertyRange";
	public static final String removeDataPropertyRangeConcept = "RemoveDataPropertyRange";
	public static final String addDisjointDataPropertyConcept = "AddDisjointDataProperty";
	public static final String removeDisjointDataPropertyConcept = "RemoveDisjointDataProperty";
	public static final String addEquivalentDataPropertyConcept = "AddEquivalentDataProperty";
	public static final String removeEquivalentDataPropertyConcept = "RemoveEquivalentDataProperty";
	public static final String addFunctionalDataPropertyConcept = "AddFunctionalDataProperty";
	public static final String removeFunctionalDataPropertyConcept = "RemoveFunctionalDataProperty";
	public static final String addSubDataPropertyOfConcept = "AddSubDataPropertyOf";
	public static final String removeSubDataPropertyOfConcept = "RemoveSubDataPropertyOf";
	
	public static final String changeSpecificationConcept = "ChangeSpecification";
	public static final String LogConcept = "Log";
	
	public static final String causeChange = "causeChange";
	public static final String cost = "cost";
	public static final String date = "date";
	public static final String hasAuthor = "hasAuthor";
	public static final String hasPreviousChange = "hasPreviousChange";
	public static final String priority = "priority";
	public static final String relevance = "relevance";
	public static final String time = "time";
	public static final String appliedAxiom = "appliedAxiom";
	public static final String consistsOf = "consistsOf";
	public static final String consistsOfAtomicOperation = "consistsOfAtomicOperation";
	public static final String hasRelatedEntity = "hasRelatedEntity";
	public static final String hasEntityState = "hasEntityState";
	public static final String hasOntologyState = "hasOntologyState";
	public static final String hasRole = "hasRole";
	
	public static final String AxiomConcept = "Axiom";
	public static final String ClassAxiomConcept = "ClassAxiom";
	public static final String DisjointClassesConcept = "DisjointClasses";
	public static final String DisjointUnionConcept = "DisjointUnion";
	public static final String EquivalentClassesConcept = "EquivalentClasses";
	public static final String SubClassOfConcept = "SubClassOf";
	public static final String DataPropertyAxiomConcept = "DataPropertyAxiom";
	public static final String DataPropertyDomainConcept = "DataPropertyDomain";
	public static final String DataPropertyRangeConcept = "DataPropertyRange";
	public static final String DisjointDataPropertiesConcept = "DisjointDataProperties";
	public static final String EquivalentDataPropertiesConcept = "EquivalentDataProperties";
	public static final String FunctionalDataPropertyConcept = "FunctionalDataProperty";
	public static final String SubDataPropertyOfConcept = "SubDataPropertyOf";
	public static final String DeclarationConcept = "Declaration";
	public static final String EntityAnnotationConcept = "EntityAnnotation";
	public static final String FactConcept = "Fact";
	public static final String ClassAssertionConcept = "ClassAssertion";
	public static final String DataPropertyAssertionConcept = "DataPropertyAssertion";
	public static final String DifferentIndividualsConcept = "DifferentIndividuals";
	public static final String NegativeDataPropertyAssertionConcept = "NegativeDataPropertyAssertion";
	public static final String NegativeObjectPropertyAssertionConcept = "NegativeObjectPropertyAssertion";
	public static final String ObjectPropertyAssertionConcept = "ObjectPropertyAssertion";
	public static final String SameIndividualConcept = "SameIndividual";
	public static final String AsymmetricObjectPropertyConcept = "AsymmetricObjectProperty";
	public static final String DisjointObjectPropertiesConcept = "DisjointObjectProperties";
	public static final String EquivalentObjectPropertiesConcept = "EquivalentObjectProperties";
	public static final String FunctionalObjectPropertyConcept = "FunctionalObjectProperty";
	public static final String InverseFunctionalObjectPropertyConcept = "InverseFunctionalObjectProperty";
	public static final String InverseObjectPropertiesConcept = "InverseObjectProperties";
	public static final String IrreflexiveObjectPropertyConcept = "IrreflexiveObjectProperty";
	public static final String ObjectPropertyDomainConcept = "ObjectPropertyDomain";
	public static final String ObjectPropertyRangeConcept = "ObjectPropertyRange";
	public static final String ReflexiveObjectPropertyConcept = "ReflexiveObjectProperty";
	public static final String SubObjectPropertyOfConcept = "SubObjectPropertyOf";
	public static final String SymmetricObjectPropertyConcept = "SymmetricObjectProperty";
	public static final String TransitiveObjectPropertyConcept = "TransitiveObjectProperty";
	
	
	public static final String OWLEntityConcept = "OWLEntity";
	public static final String DataPropertyConcept = "DataProperty";
	public static final String DatatypeConcept = "Datatype";
	public static final String IndividualConcept = "Individual";
	public static final String ObjectPropertyConcept = "ObjectProperty";
	public static final String OWLClassConcept = "OWLClass";
	
	public static final String axiomAnnotation = "axiomAnnotation";
	public static final String disjointClasses = "disjointClasses";
	public static final String unionClass = "unionClass";
	public static final String equivalentClasses = "equivalentClasses";
	public static final String subClass = "subClass";
	public static final String superClass = "superClass";
	public static final String dataProperty = "dataProperty";
	public static final String domain = "domain";
	public static final String range = "range";
	public static final String dataProperties = "dataProperties";
	public static final String subDataProperty = "subDataProperty";
	public static final String superDataProperty = "superDataProperty";
	public static final String entity = "entity";
	public static final String entityAnnotation = "entityAnnotation";
	public static final String annotationProperty = "annotationProperty";
	public static final String individual = "individual";
	public static final String sourceIndividual = "sourceIndividual";
	public static final String targetValue = "targetValue";
	public static final String differentIndividuals = "differentIndividuals";
	public static final String sameIndividuals = "sameIndividuals";
	public static final String objectProperty = "objectProperty";
	public static final String targetIndividual = "targetIndividual";
	public static final String disjointObjectProperties = "disjointObjectProperties";
	public static final String equivalentObjectProperties = "equivalentObjectProperties";
	public static final String inverseObjectProperties = "inverseObjectProperties";
	public static final String subObjectProperties = "subObjectProperties";
	public static final String superObjectProperty = "superObjectProperty";
	public static final String OWLClass = "class";
	public static final String dataRange = "dataRange";
	public static final String cardinality = "cardinality";
	public static final String constant = "constant";
	public static final String individuals = "individuals";
	public static final String OWLClasses = "classes";
	public static final String DescriptionConcept = "Description";
	public static final String OWLEntity = "OWLEntity";
	
	public static final String hasLastChange = "hasLastChange";
	
	public static final String appliedToOntology = "appliedToOntology";
	public static final String hasRelatedOntology = "hasRelatedOntology";
	
	public static final String initialTimestamp = "initialTimestamp";
	public static final String lastTimestamp = "lastTimestamp";
	public static final String changeFromVersion = "changeFromVersion";
	public static final String changeToVersion = "changeToVersion";
	public static final String hasChange = "hasChange";
	
	
	//Workflow constants
	public static final String ApprovedState = "http://omv.ontoware.org/2007/07/workflow#Approved";
	public static final String DeletedState = "http://omv.ontoware.org/2007/07/workflow#Deleted";
	public static final String DraftState = "http://omv.ontoware.org/2007/07/workflow#Draft";
	public static final String ToBeApprovedState = "http://omv.ontoware.org/2007/07/workflow#ToBeApproved";
	public static final String ToBeDeletedState = "http://omv.ontoware.org/2007/07/workflow#ToBeDeleted";
	public static final String PublishState = "http://omv.ontoware.org/2007/07/workflow#Published";
	public static final String timestamp="timestamp";
	public static final String performedBy="performedBy";
	public static final String relatedChange="relatedChange";
	public static final String relatedOntology="relatedOntology";
	public static final String ActionConcept="Action";
	public static final String SubjectExpert="http://omv.ontoware.org/2007/07/workflow#SubjectExpert";
	public static final String Validator="http://omv.ontoware.org/2007/07/workflow#Validator";
	public static final String Viewer="http://omv.ontoware.org/2007/07/workflow#Viewer";
	public static final String fromPublicVersion="fromPublicVersion";
	public static final String toPublicVersion="toPublicVersion";
	//NOT USING THIS NOW, USING STATES ABOVE... IS NICER. MAYBE CHANGE ONTOLOGY
	public static final String DraftOntologyState = "http://omv.ontoware.org/2007/07/workflow#DraftOntology";
	public static final String ApprovedOntologyState = "http://omv.ontoware.org/2007/07/workflow#ApprovedOntology";
	public static final String PublishedOntologyState = "http://omv.ontoware.org/2007/07/workflow#PublishedOntology";
	public static final String ToBeApprovedOntologyState = "http://omv.ontoware.org/2007/07/workflow#ToBeApprovedOntology";
	
	/*RDFS Parameters
	 * 
	 */
	
	
	
	/*----------------Preference Parameters---------------------*/
	
	
	
	public static final String LocalRegistry ="localRegistry";
	public static final String PDOntology = "peerDescriptionOntology";
	public static final String MDOntology = "mappingDescriptionOntology";
	public static final String owlChangeOntology = "owlChangeOntology";
	public static final String changeOntology = "changeOntology";
	public static final String TypeOntology = "typeOntology";
	public static final String TopicOntology = "topicOntology";
	public static final String owlodmOntology = "owlodmOntology";
	public static final String workflowOntology = "workflowOntology";
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
	public static final String discoveryFrec="discoveryFrec";
	
	public static final String DefaultLocalRegistry = "O2serverfiles/localRegistry.owl";
	
	public static final String CICERO_NS = "http://isweb.uni-koblenz.de/cicero#";
	public static final String HASARGUMENTATION_ANNOTATION = CICERO_NS + "hasArgumentation"; 
	
	/*
	public static final String DefaultPDOntology = "O2serverfiles/pOMV.owl";
	public static final String DefaultMDOntology = "O2serverfiles/mappingOMV.owl";
	public static final String DefaultTypeOntology = "O2serverfiles/OMV.owl";
	public static final String DefaultTopicOntology = "O2serverfiles/dmozT.rdf";
	public static final String DefaultOWLChangeOntology = "O2serverfiles/OWLChanges.owl";
	public static final String DefaultChangeOntology = "O2serverfiles/changes.owl";
	public static final String DefaultOWLODMOntology = "O2serverfiles/owl11v1.5.owl";
	public static final String DefaultWorkflowOntology = "O2serverfiles/workflow.owl";
	public static final String DefaultImage = "O2serverfiles/o1.GIF"; 
	//public static final String DefaultImage = "file:///O2serverfiles/oyster.jpg";
	*/
	
	public static final String DefaultPDOntology = "http://omv.ontoware.org/2007/05/pomv";
	public static final String DefaultMDOntology = "http://omv.ontoware.org/2007/05/mappingomv";
	public static final String DefaultTypeOntology = "http://omv.ontoware.org/2005/05/ontology";
	public static final String DefaultTopicOntology = "http://oyster2.ontoware.org/dmozT.rdf";
	public static final String DefaultOWLChangeOntology = "http://omv.ontoware.org/2007/07/OWLChanges";
	public static final String DefaultChangeOntology = "http://omv.ontoware.org/2007/10/changes";
	public static final String DefaultOWLODMOntology = "http://owlodm.ontoware.org/OWL1.1";	
	public static final String DefaultWorkflowOntology = "http://omv.ontoware.org/2007/07/workflow";
	public static final String DefaultImage = "http://oyster2.ontoware.org/o1.gif";
	
	
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
	public static final String DefaultBootStrapPeerName = "oysterUPM";
	public static final String DefaultBootStrapPeerIP = "138.100.11.159";
	
	public static final String NUMBER_OF_COLUMNS="NUMBER_OF_COLUMNS";
	public static final String COLUMN_TYPE="COLUMN_TYPE";
	public static final String COLUMN_NAME="COLUMN_NAME";
	public static final String COLUMN_WIDTH="COLUMN_WIDTH";
	
	public static final String DefaultNUMBER_OF_COLUMNS="3";
	public static final String DefaultCOLUMN_TYPE0="http://omv.ontoware.org/2005/05/ontology#name";
	public static final String DefaultCOLUMN_TYPE1="http://omv.ontoware.org/2005/05/ontology#description";
	public static final String DefaultCOLUMN_TYPE2="http://omv.ontoware.org/2007/05/pomv#ontologyOMVLocation";
	public static final String DefaultCOLUMN_NAME0="Ontology Name";
	public static final String DefaultCOLUMN_NAME1="Description";
	public static final String DefaultCOLUMN_NAME2="oyster:peer";
	public static final String DefaultCOLUMN_WIDTH0="100";
	public static final String DefaultCOLUMN_WIDTH1="163";
	public static final String DefaultCOLUMN_WIDTH2="100";
	
	public static final Integer offlineTries=5;
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