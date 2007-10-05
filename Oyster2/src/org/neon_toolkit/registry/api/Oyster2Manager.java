package org.neon_toolkit.registry.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.io.File;
import org.eclipse.jface.preference.PreferenceStore;
import org.neon_toolkit.omv.api.core.OMVFormalityLevel;
import org.neon_toolkit.omv.api.core.OMVKnowledgeRepresentationParadigm;
import org.neon_toolkit.omv.api.core.OMVLicenseModel;
import org.neon_toolkit.omv.api.core.OMVLocation;
import org.neon_toolkit.omv.api.core.OMVOntology;
import org.neon_toolkit.omv.api.core.OMVOntologyDomain;
import org.neon_toolkit.omv.api.core.OMVOntologyEngineeringMethodology;
import org.neon_toolkit.omv.api.core.OMVOntologyEngineeringTool;
import org.neon_toolkit.omv.api.core.OMVOntologyLanguage;
import org.neon_toolkit.omv.api.core.OMVOntologySyntax;
import org.neon_toolkit.omv.api.core.OMVOntologyTask;
import org.neon_toolkit.omv.api.core.OMVOntologyType;
import org.neon_toolkit.omv.api.core.OMVOrganisation;
import org.neon_toolkit.omv.api.core.OMVParty;
import org.neon_toolkit.omv.api.core.OMVPerson;
import org.neon_toolkit.omv.api.extensions.mapping.OMVMapping;
import org.neon_toolkit.omv.api.extensions.mapping.OMVMappingProperty;
import org.neon_toolkit.omv.api.extensions.peer.OMVPeer;
import org.neon_toolkit.registry.oyster2.Constants;
import org.neon_toolkit.registry.oyster2.Oyster2Factory;
import org.neon_toolkit.registry.util.EntryDetailSerializer;
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
 * registry, and the means to display results
 * @author Raul Palma
 * @version 0.97, October 2007
 */
public class Oyster2Manager{
	static Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private static PreferenceStore store = mOyster2.getPreferenceStore();
	public static Process serverProcess = null;
	private static String kaon2File = System.getProperty("user.dir")+System.getProperty("file.separator")+"kaon2.jar";
	private static String prefFile = System.getProperty("user.dir")+System.getProperty("file.separator")+"new store";
	private static String serverRoot= System.getProperty("user.dir")+System.getProperty("file.separator")+"server";
	private static String startParameters= "";
	private static String localURI="";
	private static boolean startKAON2=true;
	
	public Oyster2Manager()
	    {
		System.out.println("Created new Manager");
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
		setPreferencesFile(prefFile);
		initialize();
		return new Oyster2Connection();
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
		setPreferencesFile(preferencesFilename);
		initialize();
		return new Oyster2Connection();
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
		setPreferencesFile(preferencesFilename);
		setKaon2File(kaon2Filename);
		setServerRoot(serverRootFolder);
		setStartParameters(javaStartParameters);
		initialize();
		return new Oyster2Connection();
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
	 * Closes the connection to Oyster2 registry. Destroys Kaon2 instance.
	 */
	public static void closeConnection(){
		mOyster2.shutdown();
		if (startKAON2) serverProcess.destroy();	
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
			getData(omv.getName(),"name")+
			getData(omv.getAcronym(),"acronym")+
			getData(omv.getDescription(),"description")+
			getData(omv.getDocumentation(),"documentation")+
			getData(omv.getKeywords(),"keywords")+
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
			serial=serial+getData(omv.getNaturalLanguage(),"naturalLanguage")+
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
			getData(omv.getKeyClasses(),"keyClasses")+
			getData(omv.getKnownUsage(),"knownUsage")+
			getData(omv.getExpressiveness(),"expressiveness");
			if (omv.getIsConsistentAccordingToReasoner()!=null) serial = serial + getData(omv.getIsConsistentAccordingToReasoner().toString(),"isConsistentAccordingToReasoner");
			if (omv.getContainsTBox()!=null) serial = serial + getData(omv.getContainsTBox().toString(),"containsTBox");
			if (omv.getContainsRBox()!=null) serial = serial + getData(omv.getContainsRBox().toString(),"containsRBox");
			if (omv.getContainsABox()!=null) serial = serial + getData(omv.getContainsABox().toString(),"containsABox");
			serial = serial + getDataSetString(omv.getEndorsedBy(),"endorsedBy",1);
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
			System.out.println("Oyster2Manager.OMV2RDF error "+e);
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
				
		return local;
	}
	
	private static OntologyChangeEvent getEvent(String URI,String value, Individual tIndividual){
		OntologyChangeEvent OCE;
		DataProperty ontologyDataProperty = KAON2Manager.factory().dataProperty(Constants.OMVURI + URI);
		Boolean isI = org.neon_toolkit.registry.util.Utilities.isInt(URI);
		if (!isI){
			Boolean isB = org.neon_toolkit.registry.util.Utilities.isBool(URI);
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
		if (omvT.getURI()!=null) tURI=omvT.getURI();
		else return null;
		Individual oIndividual = KAON2Manager.factory().individual(tURI);
		
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,oIndividual),OntologyChangeEvent.ChangeType.ADD));			
		if (omvT.getURI()!=null) changes.add(getEvent(Constants.URI,omvT.getURI(),oIndividual));
		if (omvT.getName()!=null) changes.add(getEvent(Constants.name,omvT.getName(),oIndividual));
		if (omvT.getAcronym()!=null) changes.add(getEvent(Constants.acronym,omvT.getAcronym(),oIndividual));
		if (omvT.getDescription()!=null) changes.add(getEvent(Constants.description,omvT.getDescription(),oIndividual));
		if (omvT.getDocumentation()!=null) changes.add(getEvent(Constants.documentation,omvT.getDocumentation(),oIndividual));
		if (omvT.getKeywords()!=null) changes.add(getEvent(Constants.keywords,omvT.getKeywords(),oIndividual));
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
		if (omvT.getNaturalLanguage()!=null) changes.add(getEvent(Constants.naturalLanguage,omvT.getNaturalLanguage(),oIndividual)); 
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
		if (omvT.getKeyClasses()!=null) changes.add(getEvent(Constants.keyClasses,omvT.getKeyClasses(),oIndividual));
		if (omvT.getKnownUsage()!=null) changes.add(getEvent(Constants.knownUsage,omvT.getKnownUsage(),oIndividual));
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
		if (omvT.getEMail()!=null) changes.add(getEvent(Constants.eMail,omvT.getEMail(),objectPropertyIndividual));
		if (omvT.getPhoneNumber()!=null) changes.add(getEvent(Constants.phoneNumber,omvT.getPhoneNumber(),objectPropertyIndividual));
		if (omvT.getFaxNumber()!=null) changes.add(getEvent(Constants.faxNumber,omvT.getFaxNumber(),objectPropertyIndividual));
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
			System.out.println("here in getdatasetevent");
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
			}
		}catch(Exception ignore){
			System.out.println("here in getdatasetstring");
		}
		if (temp.length()>0)
			local = "\t"+name+": "+temp+"\n";
		return local;
	}
	
	private static void initialize(){
		try{
			if (startKAON2){
				serverProcess = Runtime.getRuntime().exec("java " + startParameters +" -cp "+ EntryDetailSerializer.QUOTE + kaon2File + EntryDetailSerializer.QUOTE + " org.semanticweb.kaon2.server.ServerMain -registry -rmi -ontologies "+ EntryDetailSerializer.QUOTE + serverRoot +EntryDetailSerializer.QUOTE);
				if(serverProcess!=null) System.out.println("server started...");
				else {System.out.println("could not start KAON2 server..."); closeConnection();}
				Thread.sleep(2000);
			}
			store.load();
			mOyster2.init(null);
			if (mOyster2.retInit!=0) closeConnection();
	    } catch (Exception e) {
	    	System.out.println("Oyster2Manager.newConnection error "+e);
		}
	}
	
	
	private static void setPreferencesFile(String fileName){
		String tFile;
		if (fileName!="" && fileName!=null)
			tFile = fileName;
		else
			tFile = prefFile;
		PreferenceStore customStore = new PreferenceStore(tFile);
		mOyster2.setPreferenceStore(customStore);
		store = mOyster2.getPreferenceStore();
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



/*	
	public static void main(String[] args)throws Exception{
		
		serverProcess = Runtime.getRuntime().exec("java -cp kaon2.jar org.semanticweb.kaon2.server.ServerMain -registry -rmi -ontologies "+ args[0]); 
		if(serverProcess!=null) System.out.println("server started...");
		Thread.currentThread().sleep(2000);
		//List :)
        configureProperties();
		mOyster2.init(null);
		shutdown();
    }
	
	public static void shutdown(){
		mOyster2.shutdown();
		serverProcess.destroy();	
		System.exit(0);
	}
	
	public static void configureProperties(){
		Boolean first=true;
		try{
			store.load();
			first=false;
		} catch (Exception e) {
			first=true;
			System.out.println("First Time");
		}
		if (first){
			Display display = new Display();
			PreferenceManager pm = new PreferenceManager();
			MainPreferencePage p = new MainPreferencePage("Main preferences");
			ConditionPreferencePage cp = new ConditionPreferencePage("ConditionPreference");

			pm.addToRoot(new PreferenceNode("mainOyster2Properties", p));
			pm.addToRoot(new PreferenceNode("searchConditions",cp));
			PreferenceDialog d = new PreferenceDialog(display.getActiveShell(), pm);
			d.setPreferenceStore(store);
		
			d.create();
			d.open();
			try {
				// Save the preferences
				store.save();
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		}
		mOyster2.setPreferenceStore(store);
	}
*/


