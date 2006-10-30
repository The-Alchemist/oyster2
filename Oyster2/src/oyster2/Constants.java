package oyster2;

public class Constants {
	/**
	 * The minimal number of data items required in order to extend the path of a
	 * peer in a direction.
	 */
	public static final int AVG_STORAGE = 4;//5000;

	/**
	 * The build version of this application.
	 */
	public static final String BUILD = "$Revision: 1.1 $".replace('$', ' ').trim() + " (" + "$Date: 2006-10-30 12:50:38 $".replace('$', ' ').trim() + ")";

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
	public static final String OMV= "http://omv.ontoware.org/2005/05/ontology";
	public static final String LocalRegistryURI = "http://localhost/localRegistry";
	public static final String VERSIONINFO ="http://www.w3.org/2002/07/owl#versionInfo";
	public static final String IMPORT ="imports";
	public static final String OMVIMPORT = OMVURI + IMPORT;
	public static final String ontologyConcept = "Ontology";
	public static final String URI = "URI";
	public static final String name ="name";
	public static final String acronym="acronym";
	public static final String description="description";
	public static final String documentation="documentation";
	public static final String hasDomain = "hasDomain";
	public static final String numAxioms = "numAxioms";
	public static final String numClasses = "numClasses";
	public static final String numIndividuals = "numIndividuals";
	public static final String numProperties = "numProperties";
	public static final String useImports = "useImports";
	public static final String version = "version";
	public static final String hasPriorVersion = "hasPriorVersion";
	public static final String isBackwardCompatibleWith = "isBackwardCompatibleWith";
	public static final String isIncompatibleWith = "isIncompatibleWith";
	public static final String creationDate = "creationDate";
	public static final String naturalLanguage = "naturalLanguage";
	public static final String modificationDate = "modificationDate";
	
	/*RDFS Parameters
	 * 
	 */
	
	
	
	/*----------------Preference Parameters---------------------*/
	
	
	
	public static final String LocalRegistry ="localRegistry";
	public static final String PDOntology = "peerDescriptionOntology";
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
	public static final String DefaultLocalRegistry = "e:/O2serverfiles/localRegistry.owl";
	public static final String DefaultPDOntology = "e:/O2serverfiles/peersExpertiseRegistry.owl";
	public static final String DefaultTypeOntology = "e:/O2serverfiles/OMV.owl";
	public static final String DefaultTopicOntology = "e:/O2serverfiles/dmozT.rdf";
	public static final String DefaultImage = "file:///e:/O2serverfiles/oyster.jpg";
	public static final String DefaultTypeOntologyRoot = "#Ontology";
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