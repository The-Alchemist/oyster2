package org.neon_toolkit.registry.api;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.text.DateFormat;
import java.util.Locale;
//import java.util.regex.*;
import org.neon_toolkit.omv.api.core.OMVFormalityLevel;
import org.neon_toolkit.omv.api.core.OMVKnowledgeRepresentationParadigm;
import org.neon_toolkit.omv.api.core.OMVLicenseModel;
import org.neon_toolkit.omv.api.core.OMVOntology;
import org.neon_toolkit.omv.api.core.OMVOntologyDomain;
import org.neon_toolkit.omv.api.core.OMVOntologyEngineeringMethodology;
import org.neon_toolkit.omv.api.core.OMVOntologyEngineeringTool;
import org.neon_toolkit.omv.api.core.OMVOntologyLanguage;
import org.neon_toolkit.omv.api.core.OMVOntologySyntax;
import org.neon_toolkit.omv.api.core.OMVOntologyTask;
import org.neon_toolkit.omv.api.core.OMVOntologyType;
import org.neon_toolkit.omv.api.core.OMVOrganisation;
import org.neon_toolkit.omv.api.core.OMVPerson;
import org.neon_toolkit.omv.api.extensions.mapping.OMVMapping;
//import org.neon_toolkit.omv.api.core.OMVLocation;
//import org.neon_toolkit.omv.api.core.OMVParty;
//import org.neon_toolkit.omv.api.extensions.mapping.OMVMappingEvidence;
//import org.neon_toolkit.omv.api.extensions.mapping.OMVMappingMethod;
//import org.neon_toolkit.omv.api.extensions.mapping.OMVMappingParameter;
//import org.neon_toolkit.omv.api.extensions.mapping.OMVMappingProperty;
import org.neon_toolkit.omv.api.extensions.peer.OMVPeer;
import org.neon_toolkit.registry.core.AdvertInformer;
import org.neon_toolkit.registry.core.LocalExpertiseRegistry;
import org.neon_toolkit.registry.core.QueryFormulator;
import org.neon_toolkit.registry.oyster2.Condition;
import org.neon_toolkit.registry.oyster2.Constants;
import org.neon_toolkit.registry.oyster2.ImportOntology;
import org.neon_toolkit.registry.oyster2.OntologyProperty;
import org.neon_toolkit.registry.oyster2.Oyster2Factory;
import org.neon_toolkit.registry.oyster2.Oyster2Query;
import org.neon_toolkit.registry.oyster2.QueryReply;
import org.neon_toolkit.registry.oyster2.SearchManager;
import org.neon_toolkit.registry.util.GUID;
import org.neon_toolkit.registry.util.Resource;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.owl.elements.Individual;

/**
 * The class Oyster2Connection provides the API access methods to Oyster2 registry.
 * @author Raul Palma
 * @version 1.0, March 2008
 */
public class Oyster2Connection {
	static Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private SearchManager searchManager = mOyster2.getSearchManager();
	static public Thread apiThread = Thread.currentThread();
	private ImportOntology IOntology= new ImportOntology();
	private List pList = new LinkedList();
	private LocalExpertiseRegistry localRegistry = mOyster2.getLocalExpertiseRegistry();
	private Ontology ontologySearch=null;
	private int scope = Oyster2Query.Local_Scope;
	private ResultAPI resultAPI;
	static private int TIMEOUT=120000; 
	static private Set<OMVOntology> OMVSetDistributed=new HashSet <OMVOntology>();
	static private Set<OMVMapping> OMVMappingSetDistributed=new HashSet <OMVMapping>();
	static private Set<QueryReply> qReplyDistributedSet= new HashSet <QueryReply>();
	static private boolean waitMore=true;
	static private int OMVObject;
	
	public Oyster2Connection()
    {
		resultAPI = new ResultAPI(apiThread,Resource.RegistryResource);
    }
	
	/**
	 * Register a new object into Oyster2 registry.
	 * @param o is the registry object representing 
	 * that will be registered.
	 */
	public void register(Object o)
	{
		if (o instanceof OMVOntology){
			register ((OMVOntology)o);
		}
		else if (o instanceof OMVMapping){
			register ((OMVMapping)o);
		}
		else {
			OMVOntology nO = new OMVOntology();
			nO.addName("temporaryTrick");
			nO.setURI("http://localhost/temporaryTrick#");
			if (o instanceof OMVPerson) nO.addHasCreator((OMVPerson)o);
			else if (o instanceof OMVOrganisation) nO.addHasCreator((OMVOrganisation)o);
			else if (o instanceof OMVFormalityLevel) nO.setHasFormalityLevel((OMVFormalityLevel)o);
			else if (o instanceof OMVKnowledgeRepresentationParadigm) nO.addUsedKnowledgeRepresentationParadigm((OMVKnowledgeRepresentationParadigm)o);
			else if (o instanceof OMVLicenseModel) nO.setHasLicense((OMVLicenseModel)o);
			else if (o instanceof OMVOntologyDomain) nO.addHasDomain((OMVOntologyDomain)o);
			else if (o instanceof OMVOntologyEngineeringMethodology) nO.addUsedOntologyEngineeringMethodology((OMVOntologyEngineeringMethodology)o);
			else if (o instanceof OMVOntologyEngineeringTool) nO.addUsedOntologyEngineeringTool((OMVOntologyEngineeringTool)o);
			else if (o instanceof OMVOntologyLanguage) nO.setHasOntologyLanguage((OMVOntologyLanguage)o);
			else if (o instanceof OMVOntologySyntax) nO.setHasOntologySyntax((OMVOntologySyntax)o);
			else if (o instanceof OMVOntologyTask) nO.addDesignedForOntologyTask((OMVOntologyTask)o);
			else if (o instanceof OMVOntologyType) nO.setIsOfType((OMVOntologyType)o);
			register (nO);
			remove (nO);
		}
		
	}
		
	/**
	 * Replaces an existing object in Oyster2 registry with
	 * the new information provided. If the object
	 * does not exists, it is registered.
	 * @param o is the object
	 * that will be replaced.
	 */
	public void replace(Object o)
	{
		if (o instanceof OMVOntology){
			replace ((OMVOntology)o);
		}
		else if (o instanceof OMVMapping){
			replace ((OMVMapping)o);
		}
		else {
			OMVOntology nO = new OMVOntology();
			nO.addName("temporaryTrick");
			nO.setURI("http://localhost/temporaryTrick#");
			if (o instanceof OMVPerson) nO.addHasCreator((OMVPerson)o);
			else if (o instanceof OMVOrganisation) nO.addHasCreator((OMVOrganisation)o);
			else if (o instanceof OMVFormalityLevel) nO.setHasFormalityLevel((OMVFormalityLevel)o);
			else if (o instanceof OMVKnowledgeRepresentationParadigm) nO.addUsedKnowledgeRepresentationParadigm((OMVKnowledgeRepresentationParadigm)o);
			else if (o instanceof OMVLicenseModel) nO.setHasLicense((OMVLicenseModel)o);
			else if (o instanceof OMVOntologyDomain) nO.addHasDomain((OMVOntologyDomain)o);
			else if (o instanceof OMVOntologyEngineeringMethodology) nO.addUsedOntologyEngineeringMethodology((OMVOntologyEngineeringMethodology)o);
			else if (o instanceof OMVOntologyEngineeringTool) nO.addUsedOntologyEngineeringTool((OMVOntologyEngineeringTool)o);
			else if (o instanceof OMVOntologyLanguage) nO.setHasOntologyLanguage((OMVOntologyLanguage)o);
			else if (o instanceof OMVOntologySyntax) nO.setHasOntologySyntax((OMVOntologySyntax)o);
			else if (o instanceof OMVOntologyTask) nO.addDesignedForOntologyTask((OMVOntologyTask)o);
			else if (o instanceof OMVOntologyType) nO.setIsOfType((OMVOntologyType)o);
			replace (nO);
			remove (nO);
		}
	}

	/**
	 * Removes an existing object in Oyster2 registry
	 * @param o is the object (or reference to the object)
	 * that will be removed.
	 */
	public void remove(Object o)
	{
		if (o instanceof OMVOntology){
			remove ((OMVOntology)o);
		}
		else if (o instanceof OMVMapping){
			remove ((OMVMapping)o);
		}
		//TODO FOR THE REST OF OBJECTS
	}

	/**
	 * Search Oyster2 local registry to retrieve all available
	 * metadata entries that fulfill certain conditions.
	 * @param o is an object that holds the conditions 
	 * that will be used for searching the registry.
	 * @return The set of objects representing the
	 * ontology metadata entries and that fulfill the conditions.
	 */
	public Set<Object> search(Object o)
	{ 
		Set<Object> OMVSet=new HashSet <Object>();
		if (o instanceof OMVOntology){
			OMVSet.addAll(search ((OMVOntology)o));
		}
		else if (o instanceof OMVMapping){
			OMVSet.addAll(searchMappings ((OMVMapping)o));
		}
		return OMVSet;
	}

	/**
	 * Search Oyster2 registry to retrieve all available
	 * metadata entries that fulfill certain conditions within a 
	 * certain scope and (if specified) a certain set of Peers.
	 * @param o is an object that holds the conditions 
	 * that will be used for searching the registry.
	 * @param scope integer that represents the scope of the search
	 * (use predefined values in Oyster2Query e.g. Local_Scope, 
	 * Auto_Scope, Selected_Scope)
	 * @param peerSet is the map of pairs (URI,OMVPeer object)
	 * representing the set of peers that will be included in the
	 * search. 
	 * @return The set of objects representing the
	 * metadata entries and that fulfill the conditions.
	 */	
	public Set<Object> search(Object o, int scope,Map<String,OMVPeer> peerSet)
	{ 
		Set<Object> OMVSet=new HashSet <Object>();
		if (o instanceof OMVOntology){
			OMVSet.addAll(search((OMVOntology)o, scope, peerSet));
		}
		else if (o instanceof OMVMapping){
			OMVSet.addAll(searchMappings((OMVMapping)o, scope, peerSet));
		}
		return OMVSet;
	}

	/**
	 * Opens an ontology file, extracts the ontology metadata 
	 * and import it into Oyster2 registry. If the ontology
	 * does already exists, it is merged. 
	 * It is not allowed to use blank spaces in the path 
	 * of an ontology, since this string (the path of the ontology) 
	 * should be a normal url (java.net.URI). But you can 
	 * use %20 instead of a blank space
	 * @param URI is the path or URL of the ontology file.
	 */
	public void importOntology(String URI)
	{
		pList.clear();
		pList=IOntology.extractMetadata(URI);
		OntologyProperty prop = new OntologyProperty(Constants.URI, "");
		OntologyProperty prop1 = new OntologyProperty(Constants.name, "");
		//OntologyProperty prop2 = new OntologyProperty(Constants.resourceLocator, "");
		if (isPropertyIn(prop) && isPropertyIn(prop1))// && isPropertyIn(prop2))
			IOntology.addImportOntologyToRegistry(pList,0);
	}

	/**
	 * Search Oyster2 local registry to retrieve all available ontology
	 * metadata entries.
	 * @return The set of OMVOntology objects representing the
	 * ontology metadata entries.
	 */
	public Set<OMVOntology> getOntologies()
	{
		QueryReply qReply =null;
		LinkedList conditions = new LinkedList();
	
		QueryFormulator mFormulator = new QueryFormulator();
		mFormulator.generateDataQuery(conditions, scope);
		//Oyster2Query topicQuery = mFormulator.getTopicQuery();
		Oyster2Query typeQuery = mFormulator.getTypeQuery();
		qReply = localRegistry.returnQueryReply(mOyster2.getLocalHostOntology(),typeQuery,Resource.DataResource);
		Set<OMVOntology> OMVSet = processReply(qReply);
		return OMVSet;
	}

	/**
	 * Search Oyster2 registry to retrieve all available ontology
	 * metadata entries within a certain scope and (if specified) 
	 * a certain set of Peers.
	 * @param scope integer that represents the scope of the search
	 * (use predefined values in Oyster2Query e.g. Local_Scope, 
	 * Auto_Scope, Selected_Scope)
	 * @param peerSet is the map of pairs (URI,OMVPeer object)
	 * representing the set of peers that will be included in the
	 * search. 
	 * @return The set of OMVOntology objects representing the
	 * ontology metadata entries.
	 */
	public Set<OMVOntology> getOntologies(int scope,Map<String,OMVPeer> peerSet)
	{ 
		OMVObject=1;
		OMVSetDistributed=new HashSet <OMVOntology>();
		qReplyDistributedSet= new HashSet <QueryReply>();
		Set peerList=null;
		
		LinkedList conditions = new LinkedList();	
		QueryFormulator mFormulator = new QueryFormulator();
		mFormulator.generateDataQuery(conditions,scope);
		Oyster2Query typeQuery = mFormulator.getTypeQuery();
		if (peerSet!=null) peerList= peerSet.keySet();
		searchManager.addListener(resultAPI);
		searchManager.startSearch(null,typeQuery,peerList, false);
		waitReply(1);
		return OMVSetDistributed;
	}

	/**
	 * Search Oyster2 local registry to retrieve all available ontology
	 * metadata entries that were registered after certain date.
	 * @param fromDate is the date from which to start the search.
	 * @return The set of OMVOntology objects representing the
	 * ontology metadata entries and that were registered after the 
	 * date provided.
	 */
	
	public Set<OMVOntology> getOntologies(Date fromDate)
	{ 
		DateFormat format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US);
		//String fromFomat = format.format(fromDate);
		Set<OMVOntology> OMVSetFiltered = new HashSet<OMVOntology>();
		Set<OMVOntology> OMVSet = getOntologies();
		Iterator it = OMVSet.iterator();
		try{
			while(it.hasNext()){
				OMVOntology omv = (OMVOntology)it.next();
				Date d = format.parse(omv.getTimeStamp());
				if (d.after(fromDate)){
					OMVSetFiltered.add(omv);
				}
			}
		
		}catch(Exception ignore){
		//-- ignore
		}
		return OMVSetFiltered;
	
	}

	/**
	 * Search Oyster2 registry to retrieve all available ontology
	 * metadata entries that were registered after certain date, 
	 * within a certain scope and (if specified)a certain set of Peers.
	 * @param fromDate is the date from which to start the search.
	 * @param scope integer that represents the scope of the search
	 * (use predefined values in Oyster2Query e.g. Local_Scope, 
	 * Auto_Scope, Selected_Scope)
	 * @param peerSet is the map of pairs (URI,OMVPeer object)
	 * representing the set of peers that will be included in the
	 * search.
	 * @return The set of OMVOntology objects representing the
	 * ontology metadata entries and that were registered after the 
	 * date provided.
	 */
	
	public Set<OMVOntology> getOntologies(Date fromDate, int scope,Map<String,OMVPeer> peerSet)
	{ 
		OMVObject=1;
		DateFormat format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US);
		//String fromFomat = format.format(fromDate);
		Set<OMVOntology> OMVSetFiltered = new HashSet<OMVOntology>();
		Set<OMVOntology> OMVSet = getOntologies(scope, peerSet);
		Iterator it = OMVSet.iterator();
		try{
			while(it.hasNext()){
				OMVOntology omv = (OMVOntology)it.next();
				if (omv.getTimeStamp()!=null){
					Date d = format.parse(omv.getTimeStamp());
					if (d.after(fromDate)){
						OMVSetFiltered.add(omv);
					}
				}
			}
		
		}catch(Exception ignore){
		//-- ignore
		}
		return OMVSetFiltered;
	
	}

	/**
	 * Search Oyster2 local registry to retrieve all available ontology
	 * metadata entries that contains the input keyword in any part 
	 * of any of their data properties. Implements a keword match search.
	 * @param keyword is the keyword to search in the ontology properties.
	 * @return The set of OMVOntology objects representing the
	 * ontology metadata entries that matched the keyword.
	 */
	
	public Set<OMVOntology> getOntologies(String keyword){ 
		QueryReply qReply =null;
		Oyster2Query query = new Oyster2Query(new GUID(),Oyster2Query.Local_Scope,Oyster2Query.TOPIC_QUERY,keyword);
		qReply = localRegistry.returnQueryReply(mOyster2.getLocalHostOntology(),query,null,Resource.DataResource);
		Set<OMVOntology> OMVSet = processReply(qReply);
		return OMVSet;
	}
	
	/**
	 * Search Oyster2 registry to retrieve all available ontology
	 * metadata entries that contains the input keyword in any part 
	 * of any of their data properties. Implements a keword match search
	 * within a certain scope and (if specified)a certain set of Peers.
	 * @param keyword is the keyword to search in the ontology properties.
	 * @param scope integer that represents the scope of the search
	 * (use predefined values in Oyster2Query e.g. Local_Scope, 
	 * Auto_Scope, Selected_Scope)
	 * @param peerSet is the map of pairs (URI,OMVPeer object)
	 * representing the set of peers that will be included in the
	 * search.  
	 * @return The set of OMVOntology objects representing the
	 * ontology metadata entries that matched the keyword.
	 */
	
	public Set<OMVOntology> getOntologies(String keyword, int scope, Map<String,OMVPeer> peerSet){
		OMVObject=1;
		OMVSetDistributed=new HashSet <OMVOntology>();
		qReplyDistributedSet= new HashSet <QueryReply>();
		Set peerList=null;
		Oyster2Query query = new Oyster2Query(new GUID(),scope,Oyster2Query.TOPIC_QUERY,keyword);
		if (peerSet!=null) peerList= peerSet.keySet();
		searchManager.addListener(resultAPI);
		searchManager.startSearch(query,null,peerList, true);
		waitReply(1);
		return OMVSetDistributed;
	}

	/**
	 * Send a SPARQL query to Oyster2 local registry and returns the 
	 * metadata entries that are part of the result.
	 * @param sparqlQuery is the SPARQL query as a string.
	 * @return The set of OMVOntology objects representing the
	 * ontology metadata entries that matched the query.
	 */
	
	public Set<Object> submitAdHocQuery (String sparqlQuery)
	{ 
		Set<Object> OMVRet = new HashSet<Object>();
		QueryReply qReply =null;
		Oyster2Query query = new Oyster2Query(Oyster2Query.Local_Scope,Oyster2Query.TYPE_QUERY,sparqlQuery.toString());
		if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#Ontology>")>0){
			qReply = localRegistry.returnQueryReply(mOyster2.getLocalHostOntology(),query,Resource.DataResource);
			Set<OMVOntology> OMVSet = processReply(qReply);
			if (OMVSet.size()>0){
				Iterator it = OMVSet.iterator();
				try{
					while(it.hasNext()){
						Object omv = (Object)it.next();
						OMVRet.add(omv);
					}
				
				}catch(Exception ignore){
					//	-- ignore
				}
			}
		}
		else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2007/05/mappingomv#Mapping>")>0){
			qReply = localRegistry.returnQueryReplyGeneral(mOyster2.getLocalHostOntology(),query,Resource.DataResource);
			Set<OMVMapping> OMVSet1 = processMappings(qReply);
			if (OMVSet1.size()>0){
				Iterator it = OMVSet1.iterator();
				try{
					while(it.hasNext()){
						Object omv = (Object)it.next();
						OMVRet.add(omv);
					}
					
				}catch(Exception ignore){
					//	-- ignore
				}
			}
		}
		return OMVRet;
	}
	
	/**
	 * Send a SPARQL query to Oyster2 registry and returns the 
	 * metadata entries that are part of the result within a 
	 * certain scope and (if specified)a certain set of Peers.
	 * @param sparqlQuery is the SPARQL query as a string.
	 * @param scope integer that represents the scope of the search
	 * (use predefined values in Oyster2Query e.g. Local_Scope, 
	 * Auto_Scope, Selected_Scope)
	 * @param peerSet is the map of pairs (URI,OMVPeer object)
	 * representing the set of peers that will be included in the
	 * search.  
	 * @return The set of OMVOntology objects representing the
	 * ontology metadata entries that matched the query.
	 */
	
	public Set<Object> submitAdHocQuery (String sparqlQuery, int scope, Map<String,OMVPeer> peerSet)
	{ 
		Set<Object> OMVRet = new HashSet<Object>();
		qReplyDistributedSet= new HashSet <QueryReply>();
		Set peerList=null;		
		
		if (peerSet!=null) peerList= peerSet.keySet();
		Oyster2Query query = new Oyster2Query(scope,Oyster2Query.TYPE_QUERY,sparqlQuery.toString());
		searchManager.addListener(resultAPI);
		if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#Ontology>")>0){
			OMVSetDistributed=new HashSet <OMVOntology>();
			OMVObject=1;
			
			searchManager.startSearch(null,query,peerList, false);
			waitReply(1);
			if (OMVSetDistributed.size()>0){
				Iterator it = OMVSetDistributed.iterator();
				try{
					while(it.hasNext()){
						Object omv = (Object)it.next();
						OMVRet.add(omv);
					}
					
				}catch(Exception ignore){
					//	-- ignore
				}
			}
		}
		else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2007/05/mappingomv#Mapping>")>0){
			OMVMappingSetDistributed=new HashSet <OMVMapping>();
			OMVObject=2;
			
			searchManager.startSearch(null,query,peerList, false, false);
			waitReply(2);
			if (OMVMappingSetDistributed.size()>0){
				Iterator it = OMVMappingSetDistributed.iterator();
				try{
					while(it.hasNext()){
						Object omv = (Object)it.next();
						OMVRet.add(omv);
					}
					
				}catch(Exception ignore){
					//	-- ignore
				}
			}
		}
		return OMVRet;
	}

	/**
	 * Retrieves the peers discovered in Oyster2 registry.
	 * @return The set of OMVPeer objects representing the
	 * peers discovered by Oyster2.
	 */
	
	public Map<String,OMVPeer> getPeers()
	{
		AdvertInformer mInformer = mOyster2.getLocalAdvertInformer();
		List peers = mInformer.getPeerList(mOyster2.getLocalHostOntology());
		Map<String, OMVPeer> OMVPeerSet = processPeers(peers);
		return OMVPeerSet;
	}

	/**
	 * Retrieves the ontologies available at a certain peer.
	 * @param peerURI the URI of the peer
	 * @param peer the OMVPeer object representing the peer to be
	 * searched.
	 * @return The set of OMVOntology objects representing the
	 * ontologies available in that peer.
	 */
	
	public Set<OMVOntology> getPeerExpertise(String peerURI, OMVPeer peer)
	{
		Set<OMVOntology> OMVSet= new HashSet <OMVOntology>();
		Individual peerIndiv = KAON2Manager.factory().individual(peerURI);
		AdvertInformer mInformer = mOyster2.getLocalAdvertInformer();
		Collection ontologySet = mInformer.getOntologyDoc(mOyster2.getLocalHostOntology(),peerIndiv);
		Iterator ontology = ontologySet.iterator();
		try{
			while(ontology.hasNext()){
				Individual ontologyIndiv =(Individual) ontology.next();
				OMVOntology mainOntoReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(ontologyIndiv, "ontology", mOyster2.getLocalHostOntology());//(OMVOntology)processIndividual(ontologyIndiv, "ontology");
				/*if (mainOntoReply==null) { //TEST IT
					mainOntoReply = new OMVOntology();
					mainOntoReply.setURI(ontologyIndiv.getURI());
				}*/
				if (mainOntoReply!=null){
					if (mainOntoReply.getName()!=null && mainOntoReply.getURI()!=null)
						OMVSet.add(mainOntoReply);
					mainOntoReply=null;
				}
			}
		}catch(Exception e){
			System.out.println(e.toString()+" Search Problem in getpeerExpertise");
		}
		return OMVSet;
	}

	/**
	 * Search Oyster2 local registry to retrieve all available mappings
	 * metadata entries.
	 * @return The set of OMVMapping objects representing the
	 * mapping metadata entries.
	 */
	public Set<OMVMapping> getMappings()
	{
		QueryReply qReply =null;
		LinkedList<Condition> conditions = new LinkedList<Condition>();
		Condition c1 = new Condition(Condition.TYPE_CONDITION,Constants.MOMVURI+Constants.mappingConcept,false);
		conditions.addFirst(c1);
		QueryFormulator mFormulator = new QueryFormulator();
		mFormulator.generateDataQuery(conditions,scope);
		//Oyster2Query topicQuery = mFormulator.getTopicQuery();
		Oyster2Query typeQuery = mFormulator.getTypeQuery();
		qReply = localRegistry.returnQueryReplyGeneral(mOyster2.getLocalHostOntology(),typeQuery,Resource.DataResource);
		Set<OMVMapping> OMVSet = processMappings(qReply);
		return OMVSet;
	}

	/**
	 * Search Oyster2 registry to retrieve all available mappings
	 * metadata entries within a certain scope and (if specified )
	 * a certain set of Peers.
	 * @param scope integer that represents the scope of the search
	 * (use predefined values in Oyster2Query e.g. Local_Scope, 
	 * Auto_Scope, Selected_Scope)
	 * @param peerSet is the map of pairs (URI,OMVPeer object)
	 * representing the set of peers that will be included in the
	 * search. 
	 * @return The set of OMVOntology objects representing the
	 * ontology metadata entries.
	 */
	public Set<OMVMapping> getMappings(int scope,Map<String,OMVPeer> peerSet)
	{ 
		OMVObject=2;
		OMVMappingSetDistributed=new HashSet <OMVMapping>();
		qReplyDistributedSet= new HashSet <QueryReply>();
		Set peerList=null;
		
		LinkedList<Condition> conditions = new LinkedList<Condition>();
		Condition c1 = new Condition(Condition.TYPE_CONDITION,Constants.MOMVURI+Constants.mappingConcept,false);
		conditions.addFirst(c1);
		
		QueryFormulator mFormulator = new QueryFormulator();
		mFormulator.generateDataQuery(conditions,scope);
		Oyster2Query typeQuery = mFormulator.getTypeQuery();
		if (peerSet!=null) peerList= peerSet.keySet();
		searchManager.addListener(resultAPI);
		searchManager.startSearch(null,typeQuery,peerList, false, false);
		waitReply(2);
		return OMVMappingSetDistributed;
	}

	/**
	 * Register a new ontology into Oyster2 registry.
	 * @param o is the OMVOntology object representing the
	 * ontology that will be registered.
	 */
	private void register(OMVOntology o)
	{
		pList.clear();
		pList=OMVProperties.getProperties(o);
		OntologyProperty prop = new OntologyProperty(Constants.URI, "");
		OntologyProperty prop1 = new OntologyProperty(Constants.name, "");
		OntologyProperty prop2 = new OntologyProperty(Constants.resourceLocator, "");
		if (isPropertyIn(prop) && isPropertyIn(prop1) && isPropertyIn(prop2))
			IOntology.addImportOntologyToRegistry(pList,1);
	}
	
	/**
	 * Register a new mapping into Oyster2 registry.
	 * @param o is the OMVMapping object representing the
	 * mapping that will be registered.
	 */
	private void register(OMVMapping o)
	{
		pList.clear();
		pList=MappingProperties.getMappingProperties(o);
		OntologyProperty prop = new OntologyProperty(Constants.URI, "");
		if (isPropertyIn(prop))
			IOntology.addConceptToRegistry(0,pList,13);
	}
	
	/**
	 * Replaces an existing ontology in Oyster2 registry with
	 * the new ontology information provided. If the ontology
	 * does not exists, it is registered.
	 * @param o is the OMVOntology object representing the
	 * ontology that will be replaced.
	 */
	private void replace(OMVOntology o)
	{
		pList.clear();
		pList=OMVProperties.getProperties(o);
		OntologyProperty prop = new OntologyProperty(Constants.URI, "");
		OntologyProperty prop1 = new OntologyProperty(Constants.name, "");
		OntologyProperty prop2 = new OntologyProperty(Constants.resourceLocator, "");
		if (isPropertyIn(prop) && isPropertyIn(prop1) && isPropertyIn(prop2))
			IOntology.addImportOntologyToRegistry(pList,2);
	}
	
	/**
	 * Replaces an existing mapping in Oyster2 registry with
	 * the new mapping information provided. If the mapping
	 * does not exists, it is registered.
	 * @param o is the OMVMapping object representing the
	 * mapping that will be replaced.
	 */
	private void replace(OMVMapping o)
	{
		pList.clear();
		pList=MappingProperties.getMappingProperties(o);
		OntologyProperty prop = new OntologyProperty(Constants.URI, "");
		if (isPropertyIn(prop))
			IOntology.addConceptToRegistry(2,pList,13);
	}
	
	/**
	 * Removes an existing ontology in Oyster2 registry
	 * @param o is the OMVOntology object representing the
	 * ontology that will be removed.
	 */
	private void remove(OMVOntology o)
	{
		pList.clear();
		pList=OMVProperties.getProperties(o);
		OntologyProperty prop = new OntologyProperty(Constants.URI, "");
		//OntologyProperty prop1 = new OntologyProperty(Constants.name, "");
		OntologyProperty prop2 = new OntologyProperty(Constants.resourceLocator, "");
		if (isPropertyIn(prop) && isPropertyIn(prop2))
			IOntology.addImportOntologyToRegistry(pList,4);
	}
		
	/**
	 * Removes an existing mapping in Oyster2 registry
	 * @param o is the OMVMapping object representing the
	 * mapping that will be removed.
	 */
	private void remove(OMVMapping o)
	{
		pList.clear();
		pList=MappingProperties.getMappingProperties(o);
		OntologyProperty prop = new OntologyProperty(Constants.URI, "");
		if (isPropertyIn(prop))
			IOntology.addConceptToRegistry(4,pList,13);
	}
		
	/**
	 * Search Oyster2 local registry to retrieve all available ontology
	 * metadata entries that fulfill certain conditions.
	 * @param o is a OMVOntology object that holds the conditions 
	 * that will be used for searching the registry.
	 * @return The set of OMVOntology objects representing the
	 * ontology metadata entries and that fulfill the conditions.
	 */
	private Set<OMVOntology> search(OMVOntology o)
	{ 
		QueryReply qReply =null;
		LinkedList conditions = new LinkedList();
	
		conditions = OMVConditions.getConditions(o,"");
		QueryFormulator mFormulator = new QueryFormulator();
		mFormulator.generateDataQuery(conditions, scope);
		Oyster2Query typeQuery = mFormulator.getTypeQuery();
		qReply = localRegistry.returnQueryReply(mOyster2.getLocalHostOntology(),typeQuery,Resource.DataResource);
		Set<OMVOntology> OMVSet = processReply(qReply);
		
		
		return OMVSet;
	}				
	
	/**
	 * Search Oyster2 registry to retrieve all available ontology
	 * metadata entries that fulfill certain conditions within a 
	 * certain scope and (if specified) a certain set of Peers.
	 * @param o is a OMVOntology object that holds the conditions 
	 * that will be used for searching the registry.
	 * @param scope integer that represents the scope of the search
	 * (use predefined values in Oyster2Query e.g. Local_Scope, 
	 * Auto_Scope, Selected_Scope)
	 * @param peerSet is the map of pairs (URI,OMVPeer object)
	 * representing the set of peers that will be included in the
	 * search. 
	 * @return The set of OMVOntology objects representing the
	 * ontology metadata entries and that fulfill the conditions.
	 */
	private synchronized Set<OMVOntology> search(OMVOntology o, int scope,Map<String,OMVPeer> peerSet)
	{ 
		OMVObject=1;
		OMVSetDistributed=new HashSet <OMVOntology>();
		qReplyDistributedSet= new HashSet <QueryReply>();
		Set peerList=null;
		LinkedList conditions = new LinkedList();	
		conditions = OMVConditions.getConditions(o,"");
		QueryFormulator mFormulator = new QueryFormulator();
		mFormulator.generateDataQuery(conditions,scope);
		Oyster2Query typeQuery = mFormulator.getTypeQuery();
		if (peerSet!=null) peerList= peerSet.keySet();
		searchManager.addListener(resultAPI);
		searchManager.startSearch(null,typeQuery,peerList, false);
		waitReply(1);
		return OMVSetDistributed;
	}
	
	/**
	 * Search Oyster2 local registry to retrieve all available mapping
	 * metadata entries that fulfill certain conditions.
	 * @param o is a OMVMapping object that holds the conditions 
	 * that will be used for searching the registry.
	 * @return The set of OMVMapping objects representing the
	 * mapping metadata entries and that fulfill the conditions.
	 */
	private Set<OMVMapping> searchMappings(OMVMapping o)
	{ 
		QueryReply qReply =null;
		LinkedList<Condition> conditions = new LinkedList<Condition>();
		
		conditions = MappingConditions.getMappingConditions(o,"");
		Condition c1 = new Condition(Condition.TYPE_CONDITION,Constants.MOMVURI+Constants.mappingConcept,false);
		conditions.addFirst(c1);
		
		QueryFormulator mFormulator = new QueryFormulator();
		mFormulator.generateDataQuery(conditions,scope);
		Oyster2Query typeQuery = mFormulator.getTypeQuery();
		qReply = localRegistry.returnQueryReplyGeneral(mOyster2.getLocalHostOntology(),typeQuery,Resource.DataResource);
		Set<OMVMapping> OMVSet = processMappings(qReply);
		return OMVSet;
	}
	
	/**
	 * Search Oyster2 registry to retrieve all available mapping
	 * metadata entries that fulfill certain conditions within a 
	 * certain scope and (if specified) a certain set of Peers.
	 * @param o is a OMVOntology object that holds the conditions 
	 * that will be used for searching the registry.
	 * @param scope integer that represents the scope of the search
	 * (use predefined values in Oyster2Query e.g. Local_Scope, 
	 * Auto_Scope, Selected_Scope)
	 * @param peerSet is the map of pairs (URI,OMVPeer object)
	 * representing the set of peers that will be included in the
	 * search. 
	 * @return The set of OMVOntology objects representing the
	 * ontology metadata entries and that fulfill the conditions.
	 */
	private Set<OMVMapping> searchMappings(OMVMapping o, int scope,Map<String,OMVPeer> peerSet)
	{ 
		OMVObject=2;
		OMVMappingSetDistributed=new HashSet <OMVMapping>();
		qReplyDistributedSet= new HashSet <QueryReply>();
		Set peerList=null;
		
		LinkedList<Condition> conditions = new LinkedList<Condition>();
		conditions = MappingConditions.getMappingConditions(o,"");
		Condition c1 = new Condition(Condition.TYPE_CONDITION,Constants.MOMVURI+Constants.mappingConcept,false);
		conditions.addFirst(c1);
		
		QueryFormulator mFormulator = new QueryFormulator();
		mFormulator.generateDataQuery(conditions,scope);
		Oyster2Query typeQuery = mFormulator.getTypeQuery();
		if (peerSet!=null) peerList= peerSet.keySet();
		searchManager.addListener(resultAPI);
		searchManager.startSearch(null,typeQuery,peerList, false, false);
		waitReply(2);
		return OMVMappingSetDistributed;
	}
		
	
	//PROCESS REPLIES
	private Set<OMVOntology> processReply(QueryReply queryReply){
		Set<OMVOntology> OMVSet= new HashSet <OMVOntology>();
		ontologySearch = queryReply.getOntology();
		Collection entrySet = queryReply.getResourceSet();
		try{
			Iterator it = entrySet.iterator();
			while(it.hasNext()){
				final Resource  entry =(Resource) it.next();
				final Individual oIndividual = (Individual)entry.getEntity();
				
				OMVOntology mainOntoReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(oIndividual, "ontology",ontologySearch);//(OMVOntology)processIndividual(oIndividual, "ontology");
				if (mainOntoReply!=null){
					if (mainOntoReply.getName()!=null && mainOntoReply.getURI()!=null)
						OMVSet.add(mainOntoReply);
					mainOntoReply=null;
				}
			}
			
		}catch(Exception e){
			System.out.println(e.toString()+" Search Problem in processReply");
		}
		return OMVSet;
	}
	
	private Map<String, OMVPeer> processPeers(List peers){
		//Set<OMVPeer> OMVPeerSet= new HashSet <OMVPeer>();
		Map<String, OMVPeer> OMVPeerMap = new HashMap<String, OMVPeer>();
		ontologySearch = mOyster2.getLocalHostOntology();
		Individual peerIndiv = null;
		try{
			Iterator peerIndivs = peers.iterator();
			while(peerIndivs.hasNext()){
	    		peerIndiv =(Individual) peerIndivs.next();	
	    		Map<String, OMVPeer> peerReplyMap=ProcessPeerIndividuals.processPeerIndividual(peerIndiv, "peer",ontologySearch);//processPeerIndividual(peerIndiv, "peer");
	    		OMVPeer peerReply=(OMVPeer)peerReplyMap.values().iterator().next();
	    		String peerNameID=(String)peerReplyMap.keySet().iterator().next();
				if (peerReply!=null && !peerNameID.equalsIgnoreCase("")){
					OMVPeerMap.put(peerNameID,peerReply);
					peerReply=null;
					peerNameID="";
				}
			}
		}catch(Exception e){
			System.out.println(e.toString()+" Search Problem in processPeer");
		}
		return OMVPeerMap;
	}
	
	private Set<OMVMapping> processMappings(QueryReply queryReply){
		Set<OMVMapping> OMVSet= new HashSet <OMVMapping>();
		ontologySearch = queryReply.getOntology();
		Collection entrySet = queryReply.getResourceSet();
		try{
			Iterator it = entrySet.iterator();
			while(it.hasNext()){
				final Resource  entry =(Resource) it.next();
				final Individual mIndividual = (Individual)entry.getEntity();
				OMVMapping mainMappingReply=(OMVMapping)ProcessMappingIndividuals.processMappingIndividual(mIndividual, "mapping", ontologySearch);//(OMVMapping)processMappingIndividual(mIndividual, "mapping");
				if (mainMappingReply!=null){
					if (mainMappingReply.getURI()!=null)
						OMVSet.add(mainMappingReply);
					mainMappingReply=null;
				}
			}
			
		}catch(Exception e){
			System.out.println(e.toString()+" Search Problem in processMappings");
		}
		return OMVSet;
	}
	
	// MANAGE DISTRIBUTED QUERY
	static public synchronized void addQueryReply(QueryReply qReplyDist){
		System.out.println("recieving...");
		if (OMVObject<10)
			qReplyDistributedSet.add(qReplyDist);
	}
	
	static synchronized void noWaitMore(){
		waitMore=false;
	}
			
	private synchronized void waitReply(int which){
		waitMore=true;
		long initialTime=System.currentTimeMillis();
		long currentTime=System.currentTimeMillis();
		System.out.println("initTime: "+initialTime);
		while (waitMore){
			try {
				System.out.println("now will wait...");
				Thread.sleep(TIMEOUT);
				System.out.println("timeout..");
				waitMore=false;
			} catch (InterruptedException e) {
				System.out.println("interrupted (finished)...");
				waitMore=false;
			}
		}
		currentTime=System.currentTimeMillis();
		System.out.println("currentTime: "+currentTime);
		searchManager.stopSearch();
		
		if (which==1){
			Iterator ir=qReplyDistributedSet.iterator();
			while (ir.hasNext()){
				Set<OMVOntology> OMVSetDist=processReply((QueryReply)ir.next());
				OMVSetDistributed.addAll(OMVSetDist);
			}
			mergeDuplicates(which);
		}
		else if (which==2){
			Iterator ir=qReplyDistributedSet.iterator();
			while (ir.hasNext()){
				Set<OMVMapping> OMVSetDist=processMappings((QueryReply)ir.next());
				OMVMappingSetDistributed.addAll(OMVSetDist);
			}
			mergeDuplicates(which);
		}
	}
	

	private synchronized void mergeDuplicates(int which){
		if (which==1){
			Set<OMVOntology> t = new HashSet<OMVOntology>();
			t.addAll(processDuplicates.mergeOMVDuplicates(OMVSetDistributed));
			OMVSetDistributed.clear();
			OMVSetDistributed.addAll(t);
		}
		else if (which==2){
			Set<OMVMapping> t = new HashSet<OMVMapping>();
			t.addAll(processMappingDuplicates.mergeMappingDuplicates(OMVMappingSetDistributed));
			OMVMappingSetDistributed.clear();
			OMVMappingSetDistributed.addAll(t);
		}
	}

	//UTILS
	private boolean isPropertyIn(OntologyProperty p){
		Iterator it = pList.iterator();
		try{
		while(it.hasNext()){
			OntologyProperty op = (OntologyProperty)it.next();
			if (op.getPropertyName().equalsIgnoreCase(p.getPropertyName())){
				return true;
			}
		}
		}catch(Exception ignore){
			//-- ignore
		}
		return false;
	}
		
}
