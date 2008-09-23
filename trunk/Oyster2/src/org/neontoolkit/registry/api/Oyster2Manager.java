package org.neontoolkit.registry.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import org.neontoolkit.omv.api.core.OMVFormalityLevel;
import org.neontoolkit.omv.api.core.OMVKnowledgeRepresentationParadigm;
import org.neontoolkit.omv.api.core.OMVLicenseModel;
import org.neontoolkit.omv.api.core.OMVLocation;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.core.OMVOntologyDomain;
import org.neontoolkit.omv.api.core.OMVOntologyEngineeringMethodology;
import org.neontoolkit.omv.api.core.OMVOntologyEngineeringTool;
import org.neontoolkit.omv.api.core.OMVOntologyLanguage;
import org.neontoolkit.omv.api.core.OMVOntologySyntax;
import org.neontoolkit.omv.api.core.OMVOntologyTask;
import org.neontoolkit.omv.api.core.OMVOntologyType;
import org.neontoolkit.omv.api.core.OMVOrganisation;
import org.neontoolkit.omv.api.core.OMVParty;
import org.neontoolkit.omv.api.core.OMVPerson;
import org.neontoolkit.omv.api.extensions.change.OMVChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVCompositeChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange;
import org.neontoolkit.omv.api.extensions.mapping.OMVMapping;
import org.neontoolkit.omv.api.extensions.mapping.OMVMappingProperty;
import org.neontoolkit.omv.api.extensions.peer.OMVPeer;
import org.neontoolkit.owlodm.api.Axiom;
import org.neontoolkit.owlodm.api.Description;
import org.neontoolkit.owlodm.api.OWLEntity;
import org.neontoolkit.owlodm.api.Axiom.Declaration;
import org.neontoolkit.owlodm.api.Axiom.EntityAnnotation;
import org.neontoolkit.owlodm.api.Axiom.ClassAxiom.DisjointClasses;
import org.neontoolkit.owlodm.api.Axiom.ClassAxiom.DisjointUnion;
import org.neontoolkit.owlodm.api.Axiom.ClassAxiom.EquivalentClasses;
import org.neontoolkit.owlodm.api.Axiom.ClassAxiom.SubClassOf;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.DataPropertyDomain;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.DataPropertyRange;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.DisjointDataProperties;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.EquivalentDataProperties;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.FunctionalDataProperty;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.SubDataPropertyOf;
import org.neontoolkit.owlodm.api.Axiom.Fact.ClassAssertion;
import org.neontoolkit.owlodm.api.Axiom.Fact.DataPropertyAssertion;
import org.neontoolkit.owlodm.api.Axiom.Fact.DifferentIndividuals;
import org.neontoolkit.owlodm.api.Axiom.Fact.NegativeDataPropertyAssertion;
import org.neontoolkit.owlodm.api.Axiom.Fact.NegativeObjectPropertyAssertion;
import org.neontoolkit.owlodm.api.Axiom.Fact.ObjectPropertyAssertion;
import org.neontoolkit.owlodm.api.Axiom.Fact.SameIndividual;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.AsymmetricObjectProperty;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.DisjointObjectProperties;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.EquivalentObjectProperties;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.FunctionalObjectProperty;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.InverseFunctionalObjectProperty;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.InverseObjectProperties;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.IrreflexiveObjectProperty;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.ObjectPropertyDomain;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.ObjectPropertyRange;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.ReflexiveObjectProperty;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.SubObjectPropertyOf;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.SymmetricObjectProperty;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.TransitiveObjectProperty;
import org.neontoolkit.owlodm.api.Description.DataAllValuesFrom;
import org.neontoolkit.owlodm.api.Description.DataExactCardinality;
import org.neontoolkit.owlodm.api.Description.DataHasValue;
import org.neontoolkit.owlodm.api.Description.DataMaxCardinality;
import org.neontoolkit.owlodm.api.Description.DataMinCardinality;
import org.neontoolkit.owlodm.api.Description.DataSomeValuesFrom;
import org.neontoolkit.owlodm.api.Description.ObjectAllValuesFrom;
import org.neontoolkit.owlodm.api.Description.ObjectComplementOf;
import org.neontoolkit.owlodm.api.Description.ObjectExactCardinality;
import org.neontoolkit.owlodm.api.Description.ObjectExistsSelf;
import org.neontoolkit.owlodm.api.Description.ObjectHasValue;
import org.neontoolkit.owlodm.api.Description.ObjectIntersectionOf;
import org.neontoolkit.owlodm.api.Description.ObjectMaxCardinality;
import org.neontoolkit.owlodm.api.Description.ObjectMinCardinality;
import org.neontoolkit.owlodm.api.Description.ObjectOneOf;
import org.neontoolkit.owlodm.api.Description.ObjectSomeValuesFrom;
import org.neontoolkit.owlodm.api.Description.ObjectUnionOf;
import org.neontoolkit.owlodm.api.OWLEntity.Datatype;
import org.neontoolkit.registry.oyster2.Constants;
import org.neontoolkit.registry.oyster2.Oyster2Factory;
import org.neontoolkit.registry.oyster2.Properties;
import org.neontoolkit.registry.util.EntryDetailSerializer;
import org.neontoolkit.workflow.api.Action;
import org.neontoolkit.workflow.api.Action.EntityAction;
import org.neontoolkit.workflow.api.Action.OntologyAction;
import org.neontoolkit.workflow.api.Action.OntologyAction.Publish;
import org.semanticweb.kaon2.api.DefaultOntologyResolver;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Namespaces;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.OntologyChangeEvent;
import org.semanticweb.kaon2.api.formatting.OntologyFileFormat;
import org.semanticweb.kaon2.api.owl.elements.DataProperty;
import org.semanticweb.kaon2.api.owl.elements.Individual;
import org.semanticweb.kaon2.api.owl.elements.OWLClass;
import org.semanticweb.kaon2.api.owl.elements.ObjectProperty;



/**
 * The class Oyster2Manager provides the initial connection to Oyster2
 * registry. It allows to configure the peer behaviour and provides the 
 * means to display results
 * @author Raul Palma
 * @version 2.2, September 2008
 */
public class Oyster2Manager{
	static Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private static Properties mprop = mOyster2.getProperties();
	public static Process serverProcess = null;
	private static String kaon2File = System.getProperty("user.dir")+System.getProperty("file.separator")+"kaon2.jar";
	private static String serverRoot= System.getProperty("user.dir")+System.getProperty("file.separator")+"server";
	private static String startParameters= "";
	private static String localURI="";
	private static boolean startKAON2=true;
	private static boolean onlineKAON2=true;
	
	public Oyster2Manager()
	    {
		System.out.println("Created new Manager");
	    }

	/**
	 * Specify if automatic syncrhonization is on/off 
	 * @param t true=on;false=off 
	 * default=true;
	 */
	public static void setAutomaticSyncrhonization(boolean t)
    {
		mOyster2.setAutomaticSyncrhonization(t);
    }
	/**
	 * Set the IP address of the superOyster node 
	 * where information will be stored
	 * @param t the ip of the oyster node 
	 * default=null; null=local;
	 */
	public static void setSuperOyster(String t)
    {
		mOyster2.setSuperOysterIP(t);
    }
	/**
	 * Set the IP address of the Oyster node 
	 * where changes will be pushed
	 * @param t the ip of the oyster node 
	 * default=null; null=nopush;
	 */
	public static void setPushChangesToOysterIP(String t)
    {
		mOyster2.setPushChangesToOysterIP(t);
    }
	/**
	 * Set the timeOut for queries.
	 * @param t time in miliseconds 
	 * default=120000 (2 minutes)
	 */
	public static void setQueryTimeOut(int t)
    {
		mOyster2.setQueryTimeOut(t);
    }
	/**
	 * Specify if the local peer will start the discovery process that
	 * runs periodically. This method is particulary useful when oyster 
	 * will start and stop quickly (i.e. web service invocation, plugin,
	 * etc.). DEFAULT=false  
	 * @param simplePeer specifies if Oyster2 should be simple
	 */
	public static void setSimplePeer(boolean simplePeer)
    {
		mOyster2.setIsSimple(simplePeer);
    }

	/**
	 * Specify if the local peer will cache locally information of
	 * remote objects i.e. save locally remote objects. 
	 * Note: This property has effect only if the peer is not set
	 * as a simple peer. DEFAULT=false
	 * @param caching specifies if Oyster2 should cache information
	 */
	public static void setCachingPeer(boolean caching)
    {
		mOyster2.setCaching(caching);
    }

	/**
	 * Specify if workflow support is enabled. DEFAULT=false
	 * @param workflowSupport specify if peer supports workflow.
	 */
	public static void setWorkflowSupport(boolean workflowSupport)
    {
		mOyster2.setWorkflowSupport(workflowSupport);
    }
	
	/**
	 * Specify if debug should be started i.e. log messages will
	 * be displayed on the screen. DEFAULT=false
	 * @param logEnabled specify if consolo will display debug
	 * information
	 */
	public static void setLogEnabled(boolean logEnabled)
    {
		mOyster2.setLogEnabled(logEnabled);
    }
	
	/**
	 * Creates a new connection to Oyster2 registry. Initializes
	 * the registry factory and its preference store with
	 * the default properties file.
	 * It should be specified if Oyster2 starts an instance of KAON2 platform or not.
	 * @param startKAON2Server specifies if Oyster2 should start KAON2 server or not.
	 * @return A connection object to the Oyster2 registry.
	 */
	public static Oyster2Connection newConnection(boolean startKAON2Server)
    {
		startKAON2 = startKAON2Server; 
		configureProperties("");		//setPreferencesFile("");
		boolean success=initialize();
		if (success) return new Oyster2Connection();
		else return null;
    }
	
	/**
	 * Creates a new semi customized connection to Oyster2 registry. Initializes
	 * the registry factory and its preference store with the properties 
	 * file located at the path passed by the parameters.
	 * It should be specified if Oyster2 starts an instance of KAON2 platform or not.
	 * @param startKAON2Server specifies if Oyster2 should start KAON2 server or not.
	 * @param preferencesFilename the path to the properties file.
	 * @return A connection object to the Oyster2 registry.
	 */
	public static Oyster2Connection newConnection(boolean startKAON2Server, String preferencesFilename)
    {
		startKAON2 = startKAON2Server; 
		configureProperties(preferencesFilename); //setPreferencesFile(preferencesFilename);
		boolean success=initialize();
		if (success) return new Oyster2Connection();
		else return null;
    }
	
	/**
	 * Creates a new customized connection to Oyster2 registry. Initializes
	 * the registry factory and its preference store with the properties 
	 * file located at the path passed by the parameters. Starts an instance 
	 * of KAON2 platform with the given parameters.
	 * @param preferencesFilename the path to the properties file.
	 * @param kaon2Filename the path to the kaon2.jar file.
	 * @param serverRootFolder the path to KAON2 server root folder.
	 * (i.e. -ontologies parameter)
	 * @param javaStartParameters the parameters to start the java jar.
	 * (e.g. -ms256M -mx256M)
	 * @return a connection object to the Oyster2 registry.
	 */
	public static Oyster2Connection newConnection(String preferencesFilename, String kaon2Filename, String serverRootFolder, String javaStartParameters)
    {
		startKAON2 = true;
		onlineKAON2 = false;
		configureProperties(preferencesFilename); //setPreferencesFile(preferencesFilename);
		setKaon2File(kaon2Filename);
		setServerRoot(serverRootFolder);
		setStartParameters(javaStartParameters);
		boolean success=initialize();
		if (success) return new Oyster2Connection();
		else return null;
    }
	
	/**
	 * Retrieves a connection to Oyster2 registry. It is assumed that
	 * the registry factory and its preference store 
	 * has been initialized.
	 * It also assumes an instance of KAON2 platform is running.
	 * @return A connection object to the Oyster2 registry.
	 */
	public static Oyster2Connection getConnection()
    {
		return new Oyster2Connection();
    }
	
	/**
	 * Closes the connection to Oyster2 registry. Destroys Kaon2 
	 * instance if started withing Oyster.
	 */
	public static void closeConnection(){
		mOyster2.shutdown();
		if (startKAON2 && !onlineKAON2) serverProcess.destroy();	
		//System.exit(0);
	}
		
	/**
	 * Return the string representation of a registry object.
	 * @param o the registry object.
	 * @return the string representation of the of the object.
	 */
	public static String serializeOMV(Object o){
		String serial="";
		if (o instanceof OMVOntology){
			Set<OMVOntology> OMVSetO = new HashSet <OMVOntology>();
			OMVSetO.add((OMVOntology)o);
			serial = serializeOMVOntologies(OMVSetO);
		}
		else if (o instanceof OMVMapping){
			Set<OMVMapping> OMVSetO = new HashSet <OMVMapping>();
			OMVSetO.add((OMVMapping)o);
			serial = serializeOMVMappings(OMVSetO);
		}
		else if (o instanceof OMVPeer){
			Map<String,OMVPeer> OMVSetO = new HashMap<String,OMVPeer>();
			OMVSetO.put(" ",(OMVPeer)o);
			serial = serializeOMVPeers(OMVSetO);
		}
		return serial;
		
	}
	
	/**
	 * Return the string representation of a set of OMVOntology objects.
	 * @param OMVSet the set of OMVOntology objects.
	 * @return the string representation of the set of OMVOntology objects.
	 */
	public static String serializeOMVOntologies(Set<OMVOntology> OMVSet){
		String serial="";
		
		Iterator it = OMVSet.iterator();
		try{
		while(it.hasNext()){
			OMVOntology omv = (OMVOntology)it.next();
			serial=serial+"OMVOntology {\n"+
			getData(omv.getURI(),"URI")+
			getDataSetString(omv.getName(),"name",100)+ //getData(omv.getName(),"name")+
			getData(omv.getAcronym(),"acronym")+
			getData(omv.getDescription(),"description")+
			getData(omv.getDocumentation(),"documentation")+
			getDataSetString(omv.getKeywords(),"keywords",100)+//getData(omv.getKeywords(),"keywords")+
			getData(omv.getStatus(),"status")+
			getData(omv.getCreationDate(),"creationDate")+
			getData(omv.getModificationDate(),"modificationDate")+
			getDataSetString(omv.getHasContributor(),"hasContributor",1)+
			getDataSetString(omv.getHasCreator(),"hasCreator",1)+
			getDataSetString(omv.getUsedOntologyEngineeringTool(),"usedOntologyEngineeringTool",2)+
			getDataSetString(omv.getUsedOntologyEngineeringMethodology(),"usedOntologyEngineeringMethodology",3)+
			getDataSetString(omv.getUsedKnowledgeRepresentationParadigm(),"usedKnowledgeRepresentationParadigm",4)+
			getDataSetString(omv.getHasDomain(),"hasDomain",5);
			if (omv.getIsOfType()!=null) serial = serial + getData(omv.getIsOfType().getName(),"isOfType");
			serial=serial+getDataSetString(omv.getNaturalLanguage(),"naturalLanguage",100)+ 			//serial=serial+getData(omv.getNaturalLanguage(),"naturalLanguage")+
			getDataSetString(omv.getDesignedForOntologyTask(),"designedForOntologyTask",6);
			if (omv.getHasOntologyLanguage()!=null) serial = serial + getData(omv.getHasOntologyLanguage().getName(),"ontologyLanguage");
			if (omv.getHasOntologySyntax()!=null) serial = serial + getData(omv.getHasOntologySyntax().getName(),"ontologySyntax");
			if (omv.getHasFormalityLevel()!=null) serial = serial + getData(omv.getHasFormalityLevel().getName(),"formalityLevel");
			serial=serial+getData(omv.getResourceLocator(),"resourceLocator")+
			getData(omv.getVersion(),"version");
			if (omv.getHasLicense()!=null) serial = serial + getData(omv.getHasLicense().getName(),"hasLicense");
			serial=serial+getDataSetString(omv.getUseImports(),"useImports",0);
			if (omv.getHasPriorVersion()!=null) serial = serial + getData(omv.getHasPriorVersion().getURI(),"hasPriorVersion");
			serial = serial + getDataSetString(omv.getIsBackwardCompatibleWith(),"isBackwardCompatibleWith",0)+
			getDataSetString(omv.getIsIncompatibleWith(),"isIncompatibleWith",0);
			if (omv.getNumberOfClasses()!=null) serial = serial + getData(omv.getNumberOfClasses().toString(),"numberOfClasses");
			if (omv.getNumberOfProperties()!=null) serial = serial + getData(omv.getNumberOfProperties().toString(),"numberOfProperties");
			if (omv.getNumberOfIndividuals()!=null) serial = serial + getData(omv.getNumberOfIndividuals().toString(),"numberOfIndividuals");
			if (omv.getNumberOfAxioms()!=null) serial = serial + getData(omv.getNumberOfAxioms().toString(),"numberOfAxioms");
			serial=serial+getData(omv.getNotes(),"notes")+
			getDataSetString(omv.getKeyClasses(),"keyClasses",100)+//getData(omv.getKeyClasses(),"keyClasses")+
			getDataSetString(omv.getKnownUsage(),"knownUsage",100)+//getData(omv.getKnownUsage(),"knownUsage")+
			getData(omv.getExpressiveness(),"expressiveness");
			if (omv.getIsConsistentAccordingToReasoner()!=null) serial = serial + getData(omv.getIsConsistentAccordingToReasoner().toString(),"isConsistentAccordingToReasoner");
			if (omv.getContainsTBox()!=null) serial = serial + getData(omv.getContainsTBox().toString(),"containsTBox");
			if (omv.getContainsRBox()!=null) serial = serial + getData(omv.getContainsRBox().toString(),"containsRBox");
			if (omv.getContainsABox()!=null) serial = serial + getData(omv.getContainsABox().toString(),"containsABox");
			serial = serial + getDataSetString(omv.getEndorsedBy(),"endorsedBy",1);
			serial = serial+ getData(omv.getHasOntologyState(),"hasOntologyState");
			serial = serial + "}\n\n";
		}
		}catch(Exception ignore){
			//-- ignore
		}
		return serial;
	}
	
	/**
	 * Return the string representation of a set of OMVPeer objects.
	 * @param OMVPeerSet the map of pairs (peer URI, OMVPeer object).
	 * @return the string representation of the set of OMVPeer objects.
	 */
	public static String serializeOMVPeers(Map<String,OMVPeer> OMVPeerSet){
		String serial="";
		
		Set keySet = OMVPeerSet.keySet();
		Collection keyValues = OMVPeerSet.values();
		Iterator itKey = keySet.iterator();
		Iterator it = keyValues.iterator();
		try{
		while(it.hasNext()){
			OMVPeer omvPeer = (OMVPeer)it.next();
			serial=serial+"OMVPeer {\n"+
			getData(Namespaces.guessLocalName((String)itKey.next()),"name")+
			getData(omvPeer.getGUID(),"GUID")+
			getData(omvPeer.getIPAdress(),"IPAddress")+
			getData(omvPeer.getLocalPeer().toString(),"localPeer")+
			getData(omvPeer.getPeerType(),"peerType");
			if (omvPeer.getContextOntology()!=null) serial = serial + getData(omvPeer.getContextOntology().getURI(),"contextOntology");
			serial=serial+getDataSetString(omvPeer.getProvideOntology(),"provideOntology",0);
			serial=serial+getDataSetString(omvPeer.getProvideMapping(),"provideMapping",7);
			serial=serial+getDataSetString(omvPeer.getHasExpertise(),"hasExpertise",5);
			serial=serial+getDataSetString(omvPeer.getTrackOntology(),"trackOntology",0);
			serial = serial + "}\n\n";
		}
		}catch(Exception ignore){
			//-- ignore
		}
		return serial;
	}
	
	/**
	 * Return the string representation of a set of OMVMapping objects.
	 * @param OMVSet the set of OMVMapping objects.
	 * @return the string representation of the set of OMVMapping objects.
	 */
	public static String serializeOMVMappings(Set<OMVMapping> OMVSet){
		String serial="";
		
		Iterator it = OMVSet.iterator();
		try{
		while(it.hasNext()){
			OMVMapping omv = (OMVMapping)it.next();
			serial=serial+"OMVMapping {\n"+
			getData(omv.getURI(),"URI")+
			getData(omv.getCreationDate(),"CreationDate")+
			getData(omv.getLevel(),"level")+
			getData(omv.getPurpose(),"purpose")+
			getData(omv.getType(),"type");
			if (omv.getProcessingTime()!=null) serial = serial + getData(omv.getProcessingTime().toString(),"processingTime");
			if (omv.getHasSourceOntology()!=null) serial = serial + getData(omv.getHasSourceOntology().getURI(),"hasSourceOntology");
			if (omv.getHasTargetOntology()!=null) serial = serial + getData(omv.getHasTargetOntology().getURI(),"hasTargetOntology");
			if (omv.getHasTargetOntology()!=null) serial = serial + getData(omv.getUsedMethod().getID(),"usedMethod");
			serial=serial+getDataSetString(omv.getHasCreator(),"hasCreator",1);
			serial=serial+getDataSetString(omv.getHasProperty(),"hasProperty",8);
			serial = serial + "}\n\n";
		}
		}catch(Exception ignore){
			//-- ignore
		}
		return serial;
	}
	
	/**
	 * Return the string representation of a set of OMVChange objects.
	 * @param OMVSet the set of OMVChange objects.
	 * @return the string representation of the set of OMVChange objects.
	 */
	public static String serializeOMVChanges(List<OMVChange> OMVSet){
		String serial="";
		
		Iterator it = OMVSet.iterator();
		try{
		while(it.hasNext()){
			OMVChange omv = (OMVChange)it.next();
			//System.out.println("processing change: "+omv.getURI());
			serial=serial+"OMVChange {\n"+"\t"+"type:"+omv.getClass().getSimpleName()+"\n"+
			getData(omv.getURI(),"URI")+
			getData(omv.getCost(),"cost")+
			getData(omv.getDate(),"date")+
			getData(omv.getPriority(),"priority")+
			getData(omv.getRelevance(),"relevance")+
			getData(omv.getVersion(),"version")+
			getData(omv.getTime(),"time")+
			getData(omv.getHasPreviousChange(),"hasPreviousChange");
			if (omv.getAppliedToOntology()!=null) serial = serial + getData(omv.getAppliedToOntology().getURI(),"appliedToOntology");
			serial=serial+getDataSetString(omv.getHasAuthor(),"hasAuthor",1);
			serial=serial+getDataSetString(omv.getCauseChange(),"causeChange",100);
			if (omv instanceof OMVAtomicChange){
				String temp=getAxiomString(((OMVAtomicChange)omv).getAppliedAxiom());
				serial=serial+"\t"+"appliedAxiom: "+"\n"+temp;
			}else if (omv instanceof OMVCompositeChange){
				serial=serial+getDataSetString(((OMVCompositeChange)omv).getConsistsOf(),"consistsOf",100);
			}else if (omv instanceof OMVEntityChange){
				serial=serial+getData(((OMVEntityChange)omv).getHasEntityState(),"hasEntityState");
				serial=serial+getDataSetString(((OMVEntityChange)omv).getHasRelatedEntity(),"hasRelatedEntity",100);
				serial=serial+getDataSetString(((OMVEntityChange)omv).getConsistsOfAtomicOperation(),"consistsOfAtomicOperation",100);
			}
			serial = serial + "}\n\n";
		}
		}catch(Exception ignore){
			//-- ignore
		}
		return serial;
	}
	
	private static String getAxiomString(Axiom a){
		if (a instanceof DisjointClasses) {
			Iterator it = ((DisjointClasses)a).getDisjointClasses().iterator();
			String rep="";
			while (it.hasNext()){
				Description temp=(Description)it.next();
				rep=rep+"\t"+ getData(getDescriptionString(temp),"disjointClasses");
			}
			return rep;
		}
		else if (a instanceof DisjointUnion) {
			Iterator it = ((DisjointUnion)a).getDisjointClasses().iterator();
			String rep="";
			while (it.hasNext()){
				Description temp=(Description)it.next();
				rep=rep+"\t"+ getData(getDescriptionString(temp),"disjointClasses");
			}
			rep=rep+"\t"+ getData(getDescriptionString(((DisjointUnion)a).getUnionClass()),"unionClass");
			return rep;
		}
		else if (a instanceof EquivalentClasses) {
			Iterator it = ((EquivalentClasses)a).getEquivalentClasses().iterator();
			String rep="";
			while (it.hasNext()){
				Description temp=(Description)it.next();
				rep=rep+"\t"+ getData(getDescriptionString(temp),"equivalentClasses");
			}
			return rep;
		}
		else if (a instanceof SubClassOf) {
			return "\t"+getData(getDescriptionString(((SubClassOf)a).getSubClass()),"subClass")+"\t"+getData(getDescriptionString(((SubClassOf)a).getSuperClass()),"superClass");
		}
		else if (a instanceof DataPropertyDomain) {
			return "\t"+getData(getOWLEntityString(((DataPropertyDomain)a).getDataProperty()),"dataProperty")+"\t"+getData(getDescriptionString(((DataPropertyDomain)a).getDomain()),"domain");
		}
		else if (a instanceof DataPropertyRange) {
			return "\t"+getData(getOWLEntityString(((DataPropertyRange)a).getDataProperty()),"dataProperty")+"\t"+getData(getOWLEntityString(((DataPropertyRange)a).getRange()),"range");
		}
		else if (a instanceof DisjointDataProperties) {
			Iterator it = ((DisjointDataProperties)a).getDataProperties().iterator();
			String rep="";
			while (it.hasNext()){
				org.neontoolkit.owlodm.api.OWLEntity.DataProperty temp=(org.neontoolkit.owlodm.api.OWLEntity.DataProperty)it.next();
				rep=rep+"\t"+ getData(getOWLEntityString(temp),"dataProperties");
			}
			return rep;
		}
		else if (a instanceof EquivalentDataProperties) {
			Iterator it = ((EquivalentDataProperties)a).getDataProperties().iterator();
			String rep="";
			while (it.hasNext()){
				org.neontoolkit.owlodm.api.OWLEntity.DataProperty temp=(org.neontoolkit.owlodm.api.OWLEntity.DataProperty)it.next();
				rep=rep+"\t"+ getData(getOWLEntityString(temp),"dataProperties");
			}
			return rep;
		}
		else if (a instanceof FunctionalDataProperty) return "\t"+getData(getOWLEntityString(((FunctionalDataProperty)a).getDataProperty()),"dataProperty");
		else if (a instanceof SubDataPropertyOf) return "\t"+getData(getOWLEntityString(((SubDataPropertyOf)a).getSubDataProperty()),"subDataProperty")+"\t"+getData(getOWLEntityString(((SubDataPropertyOf)a).getSuperDataProperty()),"superDataProperty");
		else if (a instanceof Declaration) {
			return "\t"+getData(getOWLEntityString(((Declaration)a).getEntity()),"entity");
		}
		else if (a instanceof EntityAnnotation) {
			return "\t"+getData(getOWLEntityString(((EntityAnnotation)a).getEntity()),"entity")+"\t"+getDataSetString(((EntityAnnotation)a).getEntityAnnotation(),"entityAnnotation",100)+"\t"+getData(((EntityAnnotation)a).getAnnotationProperty(),"AnnotationProperty");
		}
		else if (a instanceof ClassAssertion) {
			return "\t"+getData(getDescriptionString(((ClassAssertion)a).getOWLClass()),"OWLClass")+"\t"+getData(getOWLEntityString(((ClassAssertion)a).getIndividual()),"individual");
		}
		else if (a instanceof DataPropertyAssertion) return "\t"+getData(getOWLEntityString(((DataPropertyAssertion)a).getDataProperty()),"dataProperty")+"\t"+getData(getOWLEntityString(((DataPropertyAssertion)a).getSourceIndividual()),"sourceIndividual")+"\t"+getData(((DataPropertyAssertion)a).getTargetValue(),"targetValue");
		else if (a instanceof DifferentIndividuals) {
			Iterator it = ((DifferentIndividuals)a).getDifferentIndividuals().iterator();
			String rep="";
			while (it.hasNext()){
				org.neontoolkit.owlodm.api.OWLEntity.Individual temp=(org.neontoolkit.owlodm.api.OWLEntity.Individual)it.next();
				rep=rep+"\t"+ getData(getOWLEntityString(temp),"differentIndividuals");
			}
			return rep;
		}
		else if (a instanceof NegativeDataPropertyAssertion) return "\t"+getData(getOWLEntityString(((NegativeDataPropertyAssertion)a).getDataProperty()),"dataProperty")+"\t"+getData(getOWLEntityString(((NegativeDataPropertyAssertion)a).getSourceIndividual()),"sourceIndividual")+"\t"+getData(((NegativeDataPropertyAssertion)a).getTargetValue(),"targetValue");
		else if (a instanceof NegativeObjectPropertyAssertion) return "\t"+getData(getOWLEntityString(((NegativeObjectPropertyAssertion)a).getObjectProperty()),"objectProperty")+"\t"+getData(getOWLEntityString(((NegativeObjectPropertyAssertion)a).getSourceIndividual()),"sourceIndividual")+"\t"+getData(getOWLEntityString(((NegativeObjectPropertyAssertion)a).getTargetIndividual()),"targetIndividual");
		else if (a instanceof ObjectPropertyAssertion) return "\t"+getData(getOWLEntityString(((ObjectPropertyAssertion)a).getObjectProperty()),"objectProperty")+"\t"+getData(getOWLEntityString(((ObjectPropertyAssertion)a).getSourceIndividual()),"sourceIndividual")+"\t"+getData(getOWLEntityString(((ObjectPropertyAssertion)a).getTargetIndividual()),"targetIndividual");
		else if (a instanceof SameIndividual) {
			Iterator it = ((SameIndividual)a).getSameIndividuals().iterator();
			String rep="";
			while (it.hasNext()){
				org.neontoolkit.owlodm.api.OWLEntity.Individual temp=(org.neontoolkit.owlodm.api.OWLEntity.Individual)it.next();
				rep=rep+"\t"+ getData(getOWLEntityString(temp),"sameIndividuals");
			}
			return rep;
		}
		else if (a instanceof AsymmetricObjectProperty) return "\t"+getData(getOWLEntityString(((AsymmetricObjectProperty)a).getObjectProperty()),"objectProperty");
		else if (a instanceof DisjointObjectProperties) {
			Iterator it = ((DisjointObjectProperties)a).getDisjointObjectProperties().iterator();
			String rep="";
			while (it.hasNext()){
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty temp=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)it.next();
				rep=rep+"\t"+ getData(getOWLEntityString(temp),"disjointObjectProperties");
			}
			return rep;
		}
		else if (a instanceof EquivalentObjectProperties) {
			Iterator it = ((EquivalentObjectProperties)a).getEquivalentObjectProperties().iterator();
			String rep="";
			while (it.hasNext()){
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty temp=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)it.next();
				rep=rep+"\t"+ getData(getOWLEntityString(temp),"equivalentObjectProperties");
			}
			return rep;
		}
		else if (a instanceof FunctionalObjectProperty) return "\t"+getData(getOWLEntityString(((FunctionalObjectProperty)a).getObjectProperty()),"objectProperty");
		else if (a instanceof InverseFunctionalObjectProperty) return "\t"+getData(getOWLEntityString(((InverseFunctionalObjectProperty)a).getObjectProperty()),"objectProperty");
		else if (a instanceof InverseObjectProperties) {
			Iterator it = ((InverseObjectProperties)a).getInverseObjectProperties().iterator();
			String rep="";
			while (it.hasNext()){
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty temp=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)it.next();
				rep=rep+"\t"+ getData(getOWLEntityString(temp),"inverseObjectProperties");
			}
			return rep;
		}
		else if (a instanceof IrreflexiveObjectProperty) return "\t"+getData(getOWLEntityString(((IrreflexiveObjectProperty)a).getObjectProperty()),"objectProperty");
		else if (a instanceof ObjectPropertyDomain) {
			return "\t"+getData(getOWLEntityString(((ObjectPropertyDomain)a).getObjectProperty()),"objectProperty")+"\t"+getData(getDescriptionString(((ObjectPropertyDomain)a).getDomain()),"domain");
		}
		else if (a instanceof ObjectPropertyRange) {
			return "\t"+getData(getOWLEntityString(((ObjectPropertyRange)a).getObjectProperty()),"objectProperty")+"\t"+getData(getDescriptionString(((ObjectPropertyRange)a).getRange()),"range");
		}
		else if (a instanceof ReflexiveObjectProperty) return "\t"+getData(getOWLEntityString(((ReflexiveObjectProperty)a).getObjectProperty()),"objectProperty");
		else if (a instanceof SymmetricObjectProperty) return "\t"+getData(getOWLEntityString(((SymmetricObjectProperty)a).getObjectProperty()),"objectProperty");
		else if (a instanceof TransitiveObjectProperty) return "\t"+getData(getOWLEntityString(((TransitiveObjectProperty)a).getObjectProperty()),"objectProperty");
		else if (a instanceof SubObjectPropertyOf) {
			String rep= "\t"+getData(getOWLEntityString(((SubObjectPropertyOf)a).getSuperObjectProperty()),"superObjectProperty");
			Iterator it = ((SubObjectPropertyOf)a).getSubObjectProperties().iterator();
			while (it.hasNext()){
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty temp=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)it.next();
				rep=rep+"\t"+ getData(getOWLEntityString(temp),"subObjectProperties");
			}
			return rep;		
		}
		return null;
	}
	
	private static String getOWLEntityString(OWLEntity a){
		if (a instanceof org.neontoolkit.owlodm.api.OWLEntity.DataProperty) return getData(((org.neontoolkit.owlodm.api.OWLEntity.DataProperty)a).getURI(),"dataProperty");
		if (a instanceof Datatype) return getData(((Datatype)a).getURI(),"dataType");
		if (a instanceof org.neontoolkit.owlodm.api.OWLEntity.Individual) return getData(((org.neontoolkit.owlodm.api.OWLEntity.Individual)a).getURI(),"individual");
		if (a instanceof org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty) return getData(((org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)a).getURI(),"objectProperty");
		if (a instanceof Description) return getDescriptionString((Description)a);
		return null;
	}
	
	private static String getDescriptionString(Description a){
		if (a instanceof DataAllValuesFrom) {
			String rep= "DataAllValuesFrom:\n"+"\t\t\t"+getOWLEntityString(((DataAllValuesFrom)a).getDataRange());
			Iterator it = ((DataAllValuesFrom)a).getDataProperties().iterator();
			while (it.hasNext()){
				org.neontoolkit.owlodm.api.OWLEntity.DataProperty temp=(org.neontoolkit.owlodm.api.OWLEntity.DataProperty)it.next();
				rep=rep+"\n\t\t\t"+ getOWLEntityString(temp);
			}
			return rep;
		}
		else if (a instanceof DataExactCardinality) {
			return "DataExactCardinality:\n"+"\t\t\t"+getOWLEntityString(((DataExactCardinality)a).getDataProperty())+"\n\t\t\t"+getOWLEntityString(((DataExactCardinality)a).getDataRange())+"\n\t\t\t"+getData(((DataExactCardinality)a).getCardinality().toString(),"cardinality");
		}
		else if (a instanceof DataHasValue)  {
			return "DataHasValue:\n"+"\t\t\t"+getOWLEntityString(((DataHasValue)a).getDataProperty())+"\n\t\t\t"+getData(((DataHasValue)a).getConstant(),"constant");
		}
		else if (a instanceof DataMaxCardinality)  {
			return "DataMaxCardinality:\n"+"\t\t\t"+getOWLEntityString(((DataMaxCardinality)a).getDataProperty())+"\n\t\t\t"+getOWLEntityString(((DataMaxCardinality)a).getDataRange())+"\n\t\t\t"+getData(((DataMaxCardinality)a).getCardinality().toString(),"cardinality");
		}
		else if (a instanceof DataMinCardinality) {
			return "DataMinCardinality:\n"+"\t\t\t"+getOWLEntityString(((DataMinCardinality)a).getDataProperty())+"\n\t\t\t"+getOWLEntityString(((DataMinCardinality)a).getDataRange())+"\n\t\t\t"+getData(((DataMinCardinality)a).getCardinality().toString(),"cardinality");
		}
		else if (a instanceof DataSomeValuesFrom) {
			String rep= "DataSomeValuesFrom:\n"+"\t\t\t"+getOWLEntityString(((DataSomeValuesFrom)a).getDataRange());
			Iterator it = ((DataSomeValuesFrom)a).getDataProperties().iterator();
			while (it.hasNext()){
				org.neontoolkit.owlodm.api.OWLEntity.DataProperty temp=(org.neontoolkit.owlodm.api.OWLEntity.DataProperty)it.next();
				rep=rep+"\n\t\t\t"+ getOWLEntityString(temp);
			}
			return rep;
		}
		
		else if (a instanceof ObjectAllValuesFrom) {
			return "ObjectAllValuesFrom:\n"+"\t\t\t"+ getOWLEntityString(((ObjectAllValuesFrom)a).getObjectProperty())+"\n\t\t\t"+getDescriptionString (((ObjectAllValuesFrom)a).getOWLClass());
		}
		else if (a instanceof ObjectComplementOf) {
			return "ObjectComplementOf:\n"+"\t\t\t"+ getDescriptionString (((ObjectComplementOf)a).getOWLClass());
		}
		else if (a instanceof ObjectExactCardinality) {
			return "ObjectExactCardinality:\n"+"\t\t\t"+getOWLEntityString(((ObjectExactCardinality)a).getObjectProperty())+"\n\t\t\t"+getDescriptionString (((ObjectExactCardinality)a).getOWLClass())+"\t\t\t"+getData(((ObjectExactCardinality)a).getCardinality().toString(),"cardinality");
		}
		else if (a instanceof ObjectExistsSelf) {
			return "ObjectExistsSelf:\n"+"\t\t\t"+ getOWLEntityString(((ObjectExistsSelf)a).getObjectProperty());
		}
		else if (a instanceof ObjectHasValue) {
			return "ObjectHasValue:\n"+"\t\t\t"+ getOWLEntityString(((ObjectHasValue)a).getObjectProperty())+"\n\t\t\t"+ getOWLEntityString(((ObjectHasValue)a).getValue());
		}
		else if (a instanceof ObjectIntersectionOf) {
			Iterator it = ((ObjectIntersectionOf)a).getOWLClasses().iterator();
			String rep="ObjectIntersectionOf:\n";
			while (it.hasNext()){
				Description temp=(Description)it.next();
				rep=rep+"\t\t\t"+ getDescriptionString (temp);
			}
			return rep;
		}
		else if (a instanceof ObjectMaxCardinality) {
			return "ObjectMaxCardinality:\n"+"\t\t\t"+getOWLEntityString(((ObjectMaxCardinality)a).getObjectProperty())+"\n\t\t\t"+getDescriptionString (((ObjectMaxCardinality)a).getOWLClass())+"\t\t\t"+getData(((ObjectMaxCardinality)a).getCardinality().toString(),"cardinality");
		}
		else if (a instanceof ObjectMinCardinality) {
			return "ObjectMinCardinality:\n"+"\t\t\t"+getOWLEntityString(((ObjectMinCardinality)a).getObjectProperty())+"\n\t\t\t"+getDescriptionString (((ObjectMinCardinality)a).getOWLClass())+"\t\t\t"+getData(((ObjectMinCardinality)a).getCardinality().toString(),"cardinality");
		}
		else if (a instanceof ObjectOneOf) {
			String rep= "ObjectOneOf:";
			Iterator it = ((ObjectOneOf)a).getIndividuals().iterator();
			while (it.hasNext()){
				org.neontoolkit.owlodm.api.OWLEntity.Individual temp=(org.neontoolkit.owlodm.api.OWLEntity.Individual)it.next();
				rep=rep+"\n\t\t\t"+ getOWLEntityString(temp);
			}
			return rep;
		}
		else if (a instanceof ObjectSomeValuesFrom) {
			return "ObjectSomeValuesFrom:\n"+"\t\t\t"+ getOWLEntityString(((ObjectSomeValuesFrom)a).getObjectProperty())+"\n\t\t\t"+getDescriptionString (((ObjectSomeValuesFrom)a).getOWLClass());
		}
		else if (a instanceof ObjectUnionOf) {
			Iterator it = ((ObjectUnionOf)a).getOWLClasses().iterator();
			String rep="ObjectSomeValuesFrom:\n";
			while (it.hasNext()){
				Description temp=(Description)it.next();
				rep=rep+"\t\t\t"+ getDescriptionString (temp);
			}
			return rep;
		}
		
		else if (a instanceof org.neontoolkit.owlodm.api.Description.OWLClass) return getData(((org.neontoolkit.owlodm.api.Description.OWLClass)a).getURI(),"OWLClass"); 
		return null;
	}
	
	/**
	 * Return the string representation of a set of Action objects.
	 * @param OMVSet the set of Action objects.
	 * @return the string representation of the set of Action objects.
	 */
	public static String serializeActions(List<Action> OMVSet){
		String serial="";
		
		Iterator it = OMVSet.iterator();
		try{
		while(it.hasNext()){
			Action omv = (Action)it.next();
			//System.out.println("processing change: "+omv.getURI());
			serial=serial+"Action {\n"+"\t"+"type:"+omv.getClass().getSimpleName()+"\n"+
			getData(omv.getURI(),"URI")+
			getData(omv.getTimestamp(),"timestamp");
			if (omv.getPerformedBy()!=null && omv.getPerformedBy().getFirstName()!=null && omv.getPerformedBy().getLastName()!=null){
				String temp=omv.getPerformedBy().getFirstName()+" "+omv.getPerformedBy().getLastName();
				serial=serial+getData(temp,"performedBy");
			}
			if (omv instanceof EntityAction){
				serial=serial+getData(((EntityAction)omv).getRelatedChange(),"relatedChange");
			}else if (omv instanceof OntologyAction){
				if (((OntologyAction)omv).getRelatedOntology()!=null) serial = serial + getData(((OntologyAction)omv).getRelatedOntology().getURI(),"relatedOntology");
				if (omv instanceof Publish){
					if (((Publish)omv).getFromPublicVersion()!=null) serial = serial + getData(((Publish)omv).getFromPublicVersion().getURI(),"fromPublicVersion");
					if (((Publish)omv).getToPublicVersion()!=null) serial = serial + getData(((Publish)omv).getToPublicVersion().getURI(),"toPublicVersion");
				}
			}
			serial = serial + "}\n\n";
		}
		}catch(Exception ignore){
			//-- ignore
		}
		return serial;
	}
	
	/**
	 * Generates an RDF File from an OMVOntology object
	 * @param OMVOnto is a OMVOntology object that we want to 
	 * convert to a RDF file.
	 * @param filename is the name of the file that will be
	 * generated.
	 */
	public static void OMV2RDF(OMVOntology OMVOnto, String filename){
		File reply=null;
		localURI = mOyster2.getTypeOntologyURI()+"#";
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		try{					
			DefaultOntologyResolver resolver = mOyster2.getResolver();
			resolver.registerReplacement(localURI,"file:"+convert2URI(serializeFileName(filename)));
			mOyster2.getConnection().setOntologyResolver(resolver);
			Ontology ontology=mOyster2.getConnection().createOntology(mOyster2.getTypeOntologyURI()+"#",new HashMap<String,Object>());
			
			changes.addAll(Ontology2RDF(KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.ontologyConcept),OMVOnto,null,null));
			List<OntologyChangeEvent> eChanges = removeDuplicates(changes);
			
			ontology.applyChanges(eChanges);
			reply = new File(serializeFileName(filename));
			ontology.saveOntology(OntologyFileFormat.OWL_RDF,reply,"ISO-8859-1");
			resolver.unregisterReplacement(mOyster2.getTypeOntologyURI()+"#");
			Set<Ontology> cOntos = new HashSet<Ontology>();
			cOntos.add(ontology);
			mOyster2.getConnection().closeOntologies(cOntos);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static List<OntologyChangeEvent> removeDuplicates(List<OntologyChangeEvent> eChanges){
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		Iterator it=eChanges.iterator();
		while(it.hasNext()){
			OntologyChangeEvent OCE=(OntologyChangeEvent) it.next();
			if (!changes.contains(OCE)) changes.add(OCE);
		}
		return changes;
	}
	
	private static String serializeFileName(String filename){
		String seperator = System.getProperty("file.separator");
		filename = filename.replace(seperator.charAt(0),'/');
		return  filename;
	}
	
	private static String convert2URI(String filename){
		filename = filename.replace(" ","%20");
		return  filename;
	}
	
	private static String getData(String which, String name){
		String local="";
		if (which!=null)
			if (name!="resourceLocator")
				local = "\t"+name+": "+which+"\n";
			else{
				String[] result = which.split(";");
				local = "\t"+name+": ";
			    for (int x=0; x<result.length; x++)
			         local=local+result[x]+" ";
			    local = local+"\n";
			}
				
		return local.replace("\n\n", "\n");
	}
	
	private static OntologyChangeEvent getEvent(String URI,String value, Individual tIndividual){
		OntologyChangeEvent OCE;
		DataProperty ontologyDataProperty = KAON2Manager.factory().dataProperty(Constants.OMVURI + URI);
		Boolean isI = org.neontoolkit.registry.util.Utilities.isInt(URI);
		if (!isI){
			Boolean isB = org.neontoolkit.registry.util.Utilities.isBool(URI);
			if (!isB){
				OCE = new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,tIndividual,KAON2Manager.factory().constant(value)),OntologyChangeEvent.ChangeType.ADD);
			}
			else{
				OCE = new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,tIndividual,KAON2Manager.factory().constant(java.lang.Boolean.parseBoolean(value))),OntologyChangeEvent.ChangeType.ADD);
			}
		}
		else{
			OCE = new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,tIndividual,KAON2Manager.factory().constant(new Integer(value))),OntologyChangeEvent.ChangeType.ADD);
		}
		return OCE;
	}
	
	private static List<OntologyChangeEvent> Ontology2RDF(OWLClass tempConcept, OMVOntology omvT, ObjectProperty ontologyObjectProperty, Individual ontologyIndividual){
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		String tURI;
		if ((omvT.getURI()!=null)){ 
			tURI=omvT.getURI();
			if ((omvT.getVersion()!=null) && (omvT.getResourceLocator()!=null)) {
				tURI=tURI+"?version="+omvT.getVersion()+";";
				tURI=tURI+"location="+omvT.getResourceLocator();
			}
			else if (omvT.getResourceLocator()!=null) tURI=tURI+"?location="+omvT.getResourceLocator();
			tURI=tURI.replace(" ", "_");
		}
		else return null;
		Individual oIndividual = KAON2Manager.factory().individual(tURI);
		
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,oIndividual),OntologyChangeEvent.ChangeType.ADD));			
		if (omvT.getURI()!=null) changes.add(getEvent(Constants.URI,omvT.getURI(),oIndividual));
		if (omvT.getName().size()>0) { //if (omvT.getName()!=null) changes.add(getEvent(Constants.name,omvT.getName(),oIndividual));
			Iterator it = omvT.getName().iterator();
			while(it.hasNext()){
					String nl = (String)it.next();
					changes.add(getEvent(Constants.name,nl,oIndividual));
			}
		}
		if (omvT.getAcronym()!=null) changes.add(getEvent(Constants.acronym,omvT.getAcronym(),oIndividual));
		if (omvT.getDescription()!=null) changes.add(getEvent(Constants.description,omvT.getDescription(),oIndividual));
		if (omvT.getDocumentation()!=null) changes.add(getEvent(Constants.documentation,omvT.getDocumentation(),oIndividual));		
		if (omvT.getKeywords().size()>0) { //if (omvT.getKeywords()!=null) changes.add(getEvent(Constants.keywords,omvT.getKeywords(),oIndividual));
			Iterator it = omvT.getKeywords().iterator();
			while(it.hasNext()){
					String nl = (String)it.next();
					changes.add(getEvent(Constants.keywords,nl,oIndividual));
			}
		}
		if (omvT.getStatus()!=null) changes.add(getEvent(Constants.status,omvT.getStatus(),oIndividual));
		if (omvT.getCreationDate()!=null) changes.add(getEvent(Constants.creationDate,omvT.getCreationDate(),oIndividual));
		if (omvT.getModificationDate()!=null) changes.add(getEvent(Constants.modificationDate,omvT.getModificationDate(),oIndividual));
		if (omvT.getHasContributor().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.hasContributor,omvT.getHasContributor(),oIndividual,1); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getHasCreator().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.hasCreator,omvT.getHasCreator(),oIndividual,1);
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getUsedOntologyEngineeringTool().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.usedOntologyEngineeringTool,omvT.getUsedOntologyEngineeringTool(),oIndividual,2); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getUsedOntologyEngineeringMethodology().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.usedOntologyEngineeringMethodology,omvT.getUsedOntologyEngineeringMethodology(),oIndividual,3); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getUsedKnowledgeRepresentationParadigm().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.usedKnowledgeRepresentationParadigm,omvT.getUsedKnowledgeRepresentationParadigm(),oIndividual,4); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getHasDomain().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.hasDomain,omvT.getHasDomain(),oIndividual,5); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getIsOfType()!=null) {
			List<OntologyChangeEvent> tChanges = OT2RDF(KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.OntologyTypeConcept),omvT.getIsOfType(),KAON2Manager.factory().objectProperty(Constants.OMVURI + Constants.isOfType), oIndividual); 
			if (tChanges!=null) changes.addAll(tChanges);		
		}
		if (omvT.getNaturalLanguage().size()>0) { //if (omvT.getNaturalLanguage()!=null) changes.add(getEvent(Constants.naturalLanguage,omvT.getNaturalLanguage(),oIndividual));
			Iterator it = omvT.getNaturalLanguage().iterator();
			while(it.hasNext()){
					String nl = (String)it.next();
					changes.add(getEvent(Constants.naturalLanguage,nl,oIndividual));
			}
		}
		if (omvT.getDesignedForOntologyTask().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.designedForOntologyTask,omvT.getDesignedForOntologyTask(),oIndividual,6); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getHasOntologyLanguage()!=null) {
			List<OntologyChangeEvent> tChanges = OLA2RDF(KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.OntologyLanguageConcept),omvT.getHasOntologyLanguage(), KAON2Manager.factory().objectProperty(Constants.OMVURI + Constants.hasOntologyLanguage), oIndividual); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getHasOntologySyntax()!=null) {
			List<OntologyChangeEvent> tChanges = OSY2RDF(KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.OntologySyntaxConcept),omvT.getHasOntologySyntax(),KAON2Manager.factory().objectProperty(Constants.OMVURI + Constants.hasOntologySyntax), oIndividual); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getHasFormalityLevel()!=null) {
			List<OntologyChangeEvent> tChanges = FL2RDF(KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.FormalityLevelConcept),omvT.getHasFormalityLevel(),KAON2Manager.factory().objectProperty(Constants.OMVURI + Constants.hasFormalityLevel), oIndividual); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getResourceLocator()!=null) changes.add(getEvent(Constants.resourceLocator,omvT.getResourceLocator(),oIndividual));
		if (omvT.getVersion()!=null) changes.add(getEvent(Constants.version,omvT.getVersion(),oIndividual));
		if (omvT.getHasLicense()!=null) {
			List<OntologyChangeEvent> tChanges = LM2RDF(KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.LicenseModelConcept),omvT.getHasLicense(),KAON2Manager.factory().objectProperty(Constants.OMVURI + Constants.hasLicense), oIndividual); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getUseImports().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.useImports,omvT.getUseImports(),oIndividual,0); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getHasPriorVersion()!=null) {
			List<OntologyChangeEvent> tChanges = Ontology2RDF(KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.ontologyConcept),omvT.getHasPriorVersion(),KAON2Manager.factory().objectProperty(Constants.OMVURI + Constants.hasPriorVersion), oIndividual); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getIsBackwardCompatibleWith().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.isBackwardCompatibleWith,omvT.getIsBackwardCompatibleWith(),oIndividual,0); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getIsIncompatibleWith().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.isIncompatibleWith,omvT.getIsIncompatibleWith(),oIndividual,0); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getNumberOfClasses()!=null) changes.add(getEvent(Constants.numberOfClasses,omvT.getNumberOfClasses().toString(),oIndividual));
		if (omvT.getNumberOfProperties()!=null) changes.add(getEvent(Constants.numberOfProperties,omvT.getNumberOfProperties().toString(),oIndividual));
		if (omvT.getNumberOfIndividuals()!=null) changes.add(getEvent(Constants.numberOfIndividuals,omvT.getNumberOfIndividuals().toString(),oIndividual));
		if (omvT.getNumberOfAxioms()!=null) changes.add(getEvent(Constants.numberOfAxioms,omvT.getNumberOfAxioms().toString(),oIndividual));
		if (omvT.getNotes()!=null) changes.add(getEvent(Constants.notes,omvT.getNotes(),oIndividual));
		if (omvT.getKeyClasses().size()>0) { //if (omvT.getKeyClasses()!=null) changes.add(getEvent(Constants.keyClasses,omvT.getKeyClasses(),oIndividual));
			Iterator it = omvT.getKeyClasses().iterator();
			while(it.hasNext()){
					String nl = (String)it.next();
					changes.add(getEvent(Constants.keyClasses,nl,oIndividual));
			}
		}
		if (omvT.getKnownUsage().size()>0) { //if (omvT.getKnownUsage()!=null) changes.add(getEvent(Constants.knownUsage,omvT.getKnownUsage(),oIndividual));
			Iterator it = omvT.getKnownUsage().iterator();
			while(it.hasNext()){
					String nl = (String)it.next();
					changes.add(getEvent(Constants.knownUsage,nl,oIndividual));
			}
		}
		if (omvT.getExpressiveness()!=null) changes.add(getEvent(Constants.expressiveness,omvT.getExpressiveness(),oIndividual));
		if (omvT.getIsConsistentAccordingToReasoner()!=null) changes.add(getEvent(Constants.isConsistentAccordingToReasoner,omvT.getIsConsistentAccordingToReasoner().toString(),oIndividual));
		if (omvT.getContainsTBox()!=null) changes.add(getEvent(Constants.containsTBox,omvT.getContainsTBox().toString(),oIndividual));
		if (omvT.getContainsRBox()!=null) changes.add(getEvent(Constants.containsRBox,omvT.getContainsRBox().toString(),oIndividual));
		if (omvT.getContainsABox()!=null) changes.add(getEvent(Constants.containsABox,omvT.getContainsABox().toString(),oIndividual));
		if (omvT.getEndorsedBy().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.endorsedBy,omvT.getEndorsedBy(),oIndividual,1);
			if (tChanges!=null) changes.addAll(tChanges);
		}
		
		if (ontologyObjectProperty!=null && ontologyIndividual!=null) changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,ontologyIndividual,oIndividual),OntologyChangeEvent.ChangeType.ADD));
		return changes;
	}
	
	private static List<OntologyChangeEvent> party2RDF(OMVParty omvT, Individual objectPropertyIndividual){
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		
		if (omvT.getIsLocatedAt().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.isLocatedAt,omvT.getIsLocatedAt(),objectPropertyIndividual,7); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getDevelopesOntologyEngineeringTool().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.developesOntologyEngineeringTool,omvT.getDevelopesOntologyEngineeringTool(),objectPropertyIndividual,2); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getDevelopesOntologyLanguage().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.developesOntologyLanguage,omvT.getDevelopesOntologyLanguage(),objectPropertyIndividual,8); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getDevelopesOntologySyntax().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.developesOntologySyntax,omvT.getDevelopesOntologySyntax(),objectPropertyIndividual,9); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getSpecifiesKnowledgeRepresentationParadigm().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.specifiesKnowledgeRepresentationParadigm,omvT.getSpecifiesKnowledgeRepresentationParadigm(),objectPropertyIndividual,4); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getDefinesOntologyType().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.definesOntologyType,omvT.getDefinesOntologyType(),objectPropertyIndividual,10); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getDevelopesOntologyEngineeringMethodology().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.developesOntologyEngineeringMethodology,omvT.getDevelopesOntologyEngineeringMethodology(),objectPropertyIndividual,3); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getSpecifiesLicense().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.specifiesLicense,omvT.getSpecifiesLicense(),objectPropertyIndividual,11); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getSpecifiesLicense().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.specifiesLicense,omvT.getSpecifiesLicense(),objectPropertyIndividual,11); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getHasAffiliatedParty().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.hasAffiliatedParty,omvT.getHasAffiliatedParty(),objectPropertyIndividual,1); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getCreatesOntology().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.createsOntology,omvT.getCreatesOntology(),objectPropertyIndividual,0); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getContributesToOntology().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.contributesToOntology,omvT.getContributesToOntology(),objectPropertyIndividual,0); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		return changes;
	}
		
	private static List<OntologyChangeEvent> person2RDF(OWLClass tempConcept, OMVPerson omvT, ObjectProperty ontologyObjectProperty, Individual ontologyIndividual){
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		String tURI;
		if (omvT.getFirstName()!=null && omvT.getLastName()!=null)
			tURI=localURI+omvT.getFirstName()+omvT.getLastName();
		else return null;
		Individual objectPropertyIndividual = KAON2Manager.factory().individual(tURI);
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		if (omvT.getFirstName()!=null) changes.add(getEvent(Constants.firstName,omvT.getFirstName(),objectPropertyIndividual));
		if (omvT.getLastName()!=null) changes.add(getEvent(Constants.lastName,omvT.getLastName(),objectPropertyIndividual));
		if (omvT.getEmail().size()>0) { //if (omvT.getEMail()!=null) changes.add(getEvent(Constants.eMail,omvT.getEMail(),objectPropertyIndividual));
			Iterator it = omvT.getEmail().iterator();
			while(it.hasNext()){
					String nl = (String)it.next();
					changes.add(getEvent(Constants.eMail,nl,objectPropertyIndividual));
			}
		}
		if (omvT.getPhoneNumber().size()>0) { //if (omvT.getPhoneNumber()!=null) changes.add(getEvent(Constants.phoneNumber,omvT.getPhoneNumber(),objectPropertyIndividual));
			Iterator it = omvT.getPhoneNumber().iterator();
			while(it.hasNext()){
					String nl = (String)it.next();
					changes.add(getEvent(Constants.phoneNumber,nl,objectPropertyIndividual));
			}
		}
		if (omvT.getFaxNumber().size()>0) { //if (omvT.getFaxNumber()!=null) changes.add(getEvent(Constants.faxNumber,omvT.getFaxNumber(),objectPropertyIndividual));
			Iterator it = omvT.getFaxNumber().iterator();
			while(it.hasNext()){
					String nl = (String)it.next();
					changes.add(getEvent(Constants.faxNumber,nl,objectPropertyIndividual));
			}
		}
		if (omvT.getIsContactPerson().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.isContactPerson,omvT.getIsContactPerson(),objectPropertyIndividual,1); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		List<OntologyChangeEvent> tChanges = party2RDF(omvT, objectPropertyIndividual); 
		if (tChanges!=null) changes.addAll(tChanges);
		
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,ontologyIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		return changes;
	}
	
	private static List<OntologyChangeEvent> organisation2RDF(OWLClass tempConcept, OMVOrganisation omvT, ObjectProperty ontologyObjectProperty, Individual ontologyIndividual){
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		String tURI;
		if (omvT.getName()!=null)
			tURI=localURI+omvT.getName();
		else return null;
		Individual objectPropertyIndividual = KAON2Manager.factory().individual(tURI);
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		if (omvT.getName()!=null) changes.add(getEvent(Constants.name,omvT.getName(),objectPropertyIndividual));
		if (omvT.getAcronym()!=null) changes.add(getEvent(Constants.acronym,omvT.getAcronym(),objectPropertyIndividual));
		if (omvT.getHasContactPerson().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.hasContactPerson,omvT.getHasContactPerson(),objectPropertyIndividual,1); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		List<OntologyChangeEvent> tChanges = party2RDF(omvT, objectPropertyIndividual); 
		if (tChanges!=null) changes.addAll(tChanges);
		
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,ontologyIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		return changes;
	}
	
	private static List<OntologyChangeEvent> OET2RDF(OWLClass tempConcept, OMVOntologyEngineeringTool omvT, ObjectProperty ontologyObjectProperty, Individual ontologyIndividual){
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		String tURI;
		if (omvT.getName()!=null)
			tURI=localURI+omvT.getName();
		else return null;
		Individual objectPropertyIndividual = KAON2Manager.factory().individual(tURI);
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		if (omvT.getName()!=null) changes.add(getEvent(Constants.name,omvT.getName(),objectPropertyIndividual));
		if (omvT.getAcronym()!=null) changes.add(getEvent(Constants.acronym,omvT.getAcronym(),objectPropertyIndividual));
		if (omvT.getDescription()!=null) changes.add(getEvent(Constants.description,omvT.getDescription(),objectPropertyIndividual));
		if (omvT.getDocumentation()!=null) changes.add(getEvent(Constants.documentation,omvT.getDocumentation(),objectPropertyIndividual));
		if (omvT.getDevelopedBy().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.developedBy,omvT.getDevelopedBy(),objectPropertyIndividual,1); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,ontologyIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		return changes;
	}
	
	private static List<OntologyChangeEvent> OEM2RDF(OWLClass tempConcept, OMVOntologyEngineeringMethodology omvT, ObjectProperty ontologyObjectProperty, Individual ontologyIndividual){
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		String tURI;
		if (omvT.getName()!=null)
			tURI=localURI+omvT.getName();
		else return null;
		Individual objectPropertyIndividual = KAON2Manager.factory().individual(tURI);
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		if (omvT.getName()!=null) changes.add(getEvent(Constants.name,omvT.getName(),objectPropertyIndividual));
		if (omvT.getAcronym()!=null) changes.add(getEvent(Constants.acronym,omvT.getAcronym(),objectPropertyIndividual));
		if (omvT.getDescription()!=null) changes.add(getEvent(Constants.description,omvT.getDescription(),objectPropertyIndividual));
		if (omvT.getDocumentation()!=null) changes.add(getEvent(Constants.documentation,omvT.getDocumentation(),objectPropertyIndividual));
		if (omvT.getDevelopedBy().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.developedBy,omvT.getDevelopedBy(),objectPropertyIndividual,1); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,ontologyIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		return changes;
	}
	
	private static List<OntologyChangeEvent> KRP2RDF(OWLClass tempConcept, OMVKnowledgeRepresentationParadigm omvT, ObjectProperty ontologyObjectProperty, Individual ontologyIndividual){
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		String tURI;
		if (omvT.getName()!=null)
			tURI=localURI+omvT.getName();
		else return null;
		Individual objectPropertyIndividual = KAON2Manager.factory().individual(tURI);
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		if (omvT.getName()!=null) changes.add(getEvent(Constants.name,omvT.getName(),objectPropertyIndividual));
		if (omvT.getAcronym()!=null) changes.add(getEvent(Constants.acronym,omvT.getAcronym(),objectPropertyIndividual));
		if (omvT.getDescription()!=null) changes.add(getEvent(Constants.description,omvT.getDescription(),objectPropertyIndividual));
		if (omvT.getDocumentation()!=null) changes.add(getEvent(Constants.documentation,omvT.getDocumentation(),objectPropertyIndividual));
		if (omvT.getSpecifiedBy().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.specifiedBy,omvT.getSpecifiedBy(),objectPropertyIndividual,1); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,ontologyIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		return changes;
	}
	
	private static List<OntologyChangeEvent> OD2RDF(OWLClass tempConcept, OMVOntologyDomain omvT, ObjectProperty ontologyObjectProperty, Individual ontologyIndividual){
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		String tURI;
		if (omvT.getURI()!=null)
			tURI=omvT.getURI();
		else return null;
		if(!tURI.contains("://")){
			omvT.setURI(Constants.TopicsURI+tURI); //Add namespace if not present
			tURI=omvT.getURI();  
		}
		Individual objectPropertyIndividual = KAON2Manager.factory().individual(tURI);
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		if (omvT.getName()!=null) changes.add(getEvent(Constants.name,omvT.getName(),objectPropertyIndividual));
		if (omvT.getURI()!=null) changes.add(getEvent(Constants.URI,omvT.getURI(),objectPropertyIndividual));
		if (omvT.getIsSubDomainOf().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.isSubDomainOf,omvT.getIsSubDomainOf(),objectPropertyIndividual,5); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,ontologyIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		return changes;
	}
	
	private static List<OntologyChangeEvent> OT2RDF(OWLClass tempConcept, OMVOntologyType omvT, ObjectProperty ontologyObjectProperty, Individual ontologyIndividual){
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		String tURI;
		if (omvT.getName()!=null)
			tURI=localURI+omvT.getName();
		else return null;
		Individual objectPropertyIndividual = KAON2Manager.factory().individual(tURI);
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		if (omvT.getName()!=null) changes.add(getEvent(Constants.name,omvT.getName(),objectPropertyIndividual));
		if (omvT.getAcronym()!=null) changes.add(getEvent(Constants.acronym,omvT.getAcronym(),objectPropertyIndividual));
		if (omvT.getDescription()!=null) changes.add(getEvent(Constants.description,omvT.getDescription(),objectPropertyIndividual));
		if (omvT.getDocumentation()!=null) changes.add(getEvent(Constants.documentation,omvT.getDocumentation(),objectPropertyIndividual));
		if (omvT.getDefinedBy().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.definedBy,omvT.getDefinedBy(),objectPropertyIndividual,1); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,ontologyIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		return changes;
	}
	
	private static List<OntologyChangeEvent> OTA2RDF(OWLClass tempConcept, OMVOntologyTask omvT, ObjectProperty ontologyObjectProperty, Individual ontologyIndividual){
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		String tURI;
		if (omvT.getName()!=null)
			tURI=localURI+omvT.getName();
		else return null;
		Individual objectPropertyIndividual = KAON2Manager.factory().individual(tURI);
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		if (omvT.getName()!=null) changes.add(getEvent(Constants.name,omvT.getName(),objectPropertyIndividual));
		if (omvT.getAcronym()!=null) changes.add(getEvent(Constants.acronym,omvT.getAcronym(),objectPropertyIndividual));
		if (omvT.getDescription()!=null) changes.add(getEvent(Constants.description,omvT.getDescription(),objectPropertyIndividual));
		if (omvT.getDocumentation()!=null) changes.add(getEvent(Constants.documentation,omvT.getDocumentation(),objectPropertyIndividual));

		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,ontologyIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		return changes;
	}
	
	private static List<OntologyChangeEvent> OLA2RDF(OWLClass tempConcept, OMVOntologyLanguage omvT, ObjectProperty ontologyObjectProperty, Individual ontologyIndividual){
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		String tURI;
		if (omvT.getName()!=null)
			tURI=localURI+omvT.getName();
		else return null;
		Individual objectPropertyIndividual = KAON2Manager.factory().individual(tURI);
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		if (omvT.getName()!=null) changes.add(getEvent(Constants.name,omvT.getName(),objectPropertyIndividual));
		if (omvT.getAcronym()!=null) changes.add(getEvent(Constants.acronym,omvT.getAcronym(),objectPropertyIndividual));
		if (omvT.getDescription()!=null) changes.add(getEvent(Constants.description,omvT.getDescription(),objectPropertyIndividual));
		if (omvT.getDocumentation()!=null) changes.add(getEvent(Constants.documentation,omvT.getDocumentation(),objectPropertyIndividual));
		if (omvT.getDevelopedBy().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.developedBy,omvT.getDevelopedBy(),objectPropertyIndividual,1); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getSupportsRepresentationParadigm().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.supportsRepresentationParadigm,omvT.getSupportsRepresentationParadigm(),objectPropertyIndividual,4); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		if (omvT.getHasSyntax().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.hasSyntax,omvT.getHasSyntax(),objectPropertyIndividual,9); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,ontologyIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		return changes;
	}
	
	private static List<OntologyChangeEvent> OSY2RDF(OWLClass tempConcept, OMVOntologySyntax omvT, ObjectProperty ontologyObjectProperty, Individual ontologyIndividual){
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		String tURI;
		if (omvT.getName()!=null)
			tURI=localURI+omvT.getName();
		else return null;
		Individual objectPropertyIndividual = KAON2Manager.factory().individual(tURI);
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		if (omvT.getName()!=null) changes.add(getEvent(Constants.name,omvT.getName(),objectPropertyIndividual));
		if (omvT.getAcronym()!=null) changes.add(getEvent(Constants.acronym,omvT.getAcronym(),objectPropertyIndividual));
		if (omvT.getDescription()!=null) changes.add(getEvent(Constants.description,omvT.getDescription(),objectPropertyIndividual));
		if (omvT.getDocumentation()!=null) changes.add(getEvent(Constants.documentation,omvT.getDocumentation(),objectPropertyIndividual));
		if (omvT.getDevelopedBy().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.developedBy,omvT.getDevelopedBy(),objectPropertyIndividual,1); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,ontologyIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		return changes;
	}
	
	private static List<OntologyChangeEvent> FL2RDF(OWLClass tempConcept, OMVFormalityLevel omvT, ObjectProperty ontologyObjectProperty, Individual ontologyIndividual){
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		String tURI;
		if (omvT.getName()!=null)
			tURI=localURI+omvT.getName();
		else return null;
		Individual objectPropertyIndividual = KAON2Manager.factory().individual(tURI);
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		if (omvT.getName()!=null) changes.add(getEvent(Constants.name,omvT.getName(),objectPropertyIndividual));
		if (omvT.getDescription()!=null) changes.add(getEvent(Constants.description,omvT.getDescription(),objectPropertyIndividual));
		
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,ontologyIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		return changes;
	}
	
	private static List<OntologyChangeEvent> LM2RDF(OWLClass tempConcept, OMVLicenseModel omvT, ObjectProperty ontologyObjectProperty, Individual ontologyIndividual){
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		String tURI;
		if (omvT.getName()!=null)
			tURI=localURI+omvT.getName();
		else return null;
		Individual objectPropertyIndividual = KAON2Manager.factory().individual(tURI);
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		if (omvT.getName()!=null) changes.add(getEvent(Constants.name,omvT.getName(),objectPropertyIndividual));
		if (omvT.getAcronym()!=null) changes.add(getEvent(Constants.acronym,omvT.getAcronym(),objectPropertyIndividual));
		if (omvT.getDescription()!=null) changes.add(getEvent(Constants.description,omvT.getDescription(),objectPropertyIndividual));
		if (omvT.getDocumentation()!=null) changes.add(getEvent(Constants.documentation,omvT.getDocumentation(),objectPropertyIndividual));
		if (omvT.getSpecifiedBy().size()>0) {
			List<OntologyChangeEvent> tChanges = getDataSetEvent(Constants.specifiedBy,omvT.getSpecifiedBy(),objectPropertyIndividual,1); 
			if (tChanges!=null) changes.addAll(tChanges);
		}
		
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,ontologyIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		return changes;
	}
	
	private static List<OntologyChangeEvent> LOC2RDF(OWLClass tempConcept, OMVLocation omvT, ObjectProperty ontologyObjectProperty, Individual ontologyIndividual){
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		String tURI;
		if (omvT.getStreet()!=null)
			tURI=localURI+omvT.getStreet();
		else return null;
		Individual objectPropertyIndividual = KAON2Manager.factory().individual(tURI);
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		if (omvT.getState()!=null) changes.add(getEvent(Constants.state,omvT.getState(),objectPropertyIndividual));
		if (omvT.getCountry()!=null) changes.add(getEvent(Constants.country,omvT.getCountry(),objectPropertyIndividual));
		if (omvT.getCity()!=null) changes.add(getEvent(Constants.city,omvT.getCity(),objectPropertyIndividual));
		if (omvT.getStreet()!=null) changes.add(getEvent(Constants.street,omvT.getStreet(),objectPropertyIndividual));
		
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,ontologyIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
		return changes;
	}
	
	private static List<OntologyChangeEvent> getDataSetEvent(String name, Set which, Individual ontologyIndividual, int tClass){		
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		OWLClass tempConcept=null;
		OMVOntology omv0;
		OMVPerson omv1a;
		OMVOrganisation omv1b;
		OMVOntologyEngineeringTool omv2;
		OMVOntologyEngineeringMethodology omv3;
		OMVKnowledgeRepresentationParadigm omv4;
		OMVOntologyDomain omv5;
		OMVOntologyTask omv6;
		OMVLocation omv7;
		OMVOntologyLanguage omv8;
		OMVOntologySyntax omv9;
		OMVOntologyType omv10;
		OMVLicenseModel omv11;
		
		ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.OMVURI + name);
		Iterator it = which.iterator();
		try{
			while(it.hasNext()){
				if (tClass==0) {
					omv0 = (OMVOntology)it.next();
					tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.ontologyConcept);
					List<OntologyChangeEvent> tChanges = Ontology2RDF(tempConcept,omv0,ontologyObjectProperty, ontologyIndividual); 
					if (tChanges!=null) changes.addAll(tChanges);
				}
				else if (tClass==1) {					
					Object t=it.next();
					if (t instanceof OMVPerson){
						omv1a = (OMVPerson)t;
						tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.personConcept);
						List<OntologyChangeEvent> tChanges = person2RDF(tempConcept,omv1a,ontologyObjectProperty, ontologyIndividual); 
						if (tChanges!=null) changes.addAll(tChanges);			
					}
					else{
						omv1b = (OMVOrganisation)t;
						tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.organisationConcept);
						List<OntologyChangeEvent> tChanges = organisation2RDF(tempConcept,omv1b,ontologyObjectProperty, ontologyIndividual); 
						if (tChanges!=null) changes.addAll(tChanges);
					}
				}
				else if (tClass==2) {
					omv2 = (OMVOntologyEngineeringTool)it.next();
					tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.OntologyEngineeringToolConcept);
					List<OntologyChangeEvent> tChanges = OET2RDF(tempConcept,omv2,ontologyObjectProperty, ontologyIndividual); 
					if (tChanges!=null) changes.addAll(tChanges);
				}
				else if (tClass==3) {
					omv3 = (OMVOntologyEngineeringMethodology)it.next();
					tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.OntologyEngineeringMethodologyConcept);
					List<OntologyChangeEvent> tChanges = OEM2RDF(tempConcept,omv3,ontologyObjectProperty, ontologyIndividual); 
					if (tChanges!=null) changes.addAll(tChanges);
				}
				else if (tClass==4) {
					omv4 = (OMVKnowledgeRepresentationParadigm)it.next();
					tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.KnowledgeRepresentationParadigmConcept);
					List<OntologyChangeEvent> tChanges = KRP2RDF(tempConcept,omv4,ontologyObjectProperty, ontologyIndividual); 
					if (tChanges!=null) changes.addAll(tChanges);
				}
				else if (tClass==5) {
					omv5 = (OMVOntologyDomain)it.next();
					tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.OntologyDomainConcept);
					List<OntologyChangeEvent> tChanges = OD2RDF(tempConcept,omv5,ontologyObjectProperty, ontologyIndividual); 
					if (tChanges!=null) changes.addAll(tChanges);
				}
				else if (tClass==6) {
					omv6 = (OMVOntologyTask)it.next();
					tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.OntologyTaskConcept);
					List<OntologyChangeEvent> tChanges = OTA2RDF(tempConcept,omv6,ontologyObjectProperty, ontologyIndividual); 
					if (tChanges!=null) changes.addAll(tChanges);
				}
				else if (tClass==7) {
					omv7 = (OMVLocation)it.next();
					tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.LocationConcept);
					List<OntologyChangeEvent> tChanges = LOC2RDF(tempConcept,omv7,ontologyObjectProperty, ontologyIndividual); 
					if (tChanges!=null) changes.addAll(tChanges);
				}
				else if (tClass==8) {
					omv8 = (OMVOntologyLanguage)it.next();
					tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.OntologyLanguageConcept);
					List<OntologyChangeEvent> tChanges = OLA2RDF(tempConcept,omv8,ontologyObjectProperty, ontologyIndividual); 
					if (tChanges!=null) changes.addAll(tChanges);
				}
				else if (tClass==9) {
					omv9 = (OMVOntologySyntax)it.next();
					tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.OntologySyntaxConcept);
					List<OntologyChangeEvent> tChanges = OSY2RDF(tempConcept,omv9,ontologyObjectProperty, ontologyIndividual); 
					if (tChanges!=null) changes.addAll(tChanges);
				}
				else if (tClass==10) {
					omv10 = (OMVOntologyType)it.next();
					tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.OntologyTypeConcept);
					List<OntologyChangeEvent> tChanges = OT2RDF(tempConcept,omv10,ontologyObjectProperty, ontologyIndividual); 
					if (tChanges!=null) changes.addAll(tChanges);
				}
				else if (tClass==11) {
					omv11 = (OMVLicenseModel)it.next();
					tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.LicenseModelConcept);
					List<OntologyChangeEvent> tChanges = LM2RDF(tempConcept,omv11,ontologyObjectProperty, ontologyIndividual); 
					if (tChanges!=null) changes.addAll(tChanges);
				}
			}
		}catch(Exception ignore){
			//System.out.println("here in getdatasetevent");
		}
		
		return changes;
	}
	
	private static String getDataSetString(Set which, String name, int tClass){
		String local="",temp="";
		OMVOntology omv0;
		OMVPerson omv1a;
		OMVOrganisation omv1b;
		OMVOntologyEngineeringTool omv2;
		OMVOntologyEngineeringMethodology omv3;
		OMVKnowledgeRepresentationParadigm omv4;
		OMVOntologyDomain omv5;
		OMVOntologyTask omv6;
		OMVMapping omv7;
		OMVMappingProperty omv8;
		String omv100="";
		
		 
		
		Iterator it = which.iterator();
		try{
			while(it.hasNext()){
				if (tClass==0) {
					omv0=(OMVOntology)it.next();
					temp=temp+omv0.getURI()+" ";
				}
				else if (tClass==1) {
					Object t=it.next();
					if (t instanceof OMVPerson){
						omv1a = (OMVPerson)t;
						temp=temp+omv1a.getFirstName()+" "+omv1a.getLastName()+" ";
					}
					else{
						omv1b = (OMVOrganisation)t;
						temp=temp+omv1b.getName()+" ";
					}
				}
				else if (tClass==2) {
					omv2=(OMVOntologyEngineeringTool)it.next();
					temp=temp+omv2.getName()+" ";
				}
				else if (tClass==3) {
					omv3=(OMVOntologyEngineeringMethodology)it.next();
					temp=temp+omv3.getName()+" ";
				}
				else if (tClass==4) {
					omv4=(OMVKnowledgeRepresentationParadigm)it.next();
					temp=temp+omv4.getName()+" ";
				}
				else if (tClass==5) {
					omv5=(OMVOntologyDomain)it.next();
					temp=temp+omv5.getURI()+" ";
				}
				else if (tClass==6) {
					omv6=(OMVOntologyTask)it.next();
					temp=temp+omv6.getName()+" ";
				}
				else if (tClass==7) {
					omv7=(OMVMapping)it.next();
					temp=temp+omv7.getURI()+" ";
				}
				else if (tClass==8) {
					omv8=(OMVMappingProperty)it.next();
					temp=temp+omv8.getID()+" ";
				}
				else if (tClass==100) {
					omv100=(String)it.next();
					temp=temp+omv100+" ";
				}
			}
		}catch(Exception ignore){
			//System.out.println("here in getdatasetstring");
		}
		if (temp.length()>0)
			local = "\t"+name+": "+temp+"\n";
		return local;
	}
	
	private static boolean initialize(){
		try{
			if (startKAON2){
				if (onlineKAON2){
					try {
						URLClassLoader cl = new URLClassLoader(new URL[] { new URL("http://ontoware.org/frs/download.php/455/kaon2.jar") });//new URLClassLoader(urls);
						cl.loadClass("org.semanticweb.kaon2.server.ServerMain");
						Class<?> cls = Class.forName("org.semanticweb.kaon2.server.ServerMain", false, cl);
				    	Method method = cls.getDeclaredMethod("main", new Class[] { String[].class });
				        String[] argum= new String[4];
				        argum[0]="-registry";
				        argum[1]="-rmi";
				        argum[2]="-ontologies";
				        argum[3]="server";
				        method.invoke(null, new Object[] { argum });
				        Thread.sleep(500);
					} catch (Exception ex) {
				        System.out.println("couldnt start KAON2 server...");
				        ex.printStackTrace();
				        closeConnection();
					}
				}else{
					serverProcess = Runtime.getRuntime().exec("java " + startParameters +" -cp "+ EntryDetailSerializer.QUOTE + kaon2File + EntryDetailSerializer.QUOTE + " org.semanticweb.kaon2.server.ServerMain -registry -rmi -ontologies "+ EntryDetailSerializer.QUOTE + serverRoot +EntryDetailSerializer.QUOTE);
					if(serverProcess!=null) System.out.println("server started...");
					else {System.out.println("could not start KAON2 server..."); closeConnection();}
					Thread.sleep(2000);
				}
				
			}
			mOyster2.init(null);
			if (mOyster2.retInit!=0) {
				closeConnection();
				return false;
			}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	return false;
		}
	    return true;
	}
	
	public static void configureProperties(String file){
		if (file!=null && file!="")
			mprop.init(file);
		else
			mprop.init();
	}
		
	private static void setKaon2File(String fileName){
		if (fileName!="" && fileName!="")
			kaon2File=fileName;
	}
	
	private static void setServerRoot(String sRoot){
		if (sRoot!="" && sRoot!=null)
			serverRoot=sRoot;
	}
	
	private static void setStartParameters(String sParameters){
		if (sParameters!="" && sParameters!=null)
			startParameters=sParameters;
	}
	
	
}	


