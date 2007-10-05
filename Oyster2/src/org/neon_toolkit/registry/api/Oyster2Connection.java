package org.neon_toolkit.registry.api;

//import org.semanticweb.kaon2.api.KAON2Exception;
//import org.semanticweb.kaon2.api.OntologyChangeEvent;
//import util.Property;
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
import java.util.regex.*;

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
import org.neon_toolkit.omv.api.extensions.mapping.OMVMappingEvidence;
import org.neon_toolkit.omv.api.extensions.mapping.OMVMappingMethod;
import org.neon_toolkit.omv.api.extensions.mapping.OMVMappingParameter;
import org.neon_toolkit.omv.api.extensions.mapping.OMVMappingProperty;
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
import org.neon_toolkit.registry.util.GUID;
import org.neon_toolkit.registry.util.Resource;
import org.semanticweb.kaon2.api.Entity;
import org.semanticweb.kaon2.api.KAON2Exception;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Namespaces;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.owl.elements.DataProperty;
import org.semanticweb.kaon2.api.owl.elements.Individual;
import org.semanticweb.kaon2.api.owl.elements.OWLClass;
import org.semanticweb.kaon2.api.owl.elements.ObjectProperty;

/**
 * The class Oyster2Connection provides the API access methods to Oyster2 registry.
 * @author Raul Palma
 * @version 0.97, October 2007
 */
public class Oyster2Connection {
	static Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private ImportOntology IOntology= new ImportOntology();
	private List pList = new LinkedList();
	private LocalExpertiseRegistry localRegistry = mOyster2.getLocalExpertiseRegistry();
	private Ontology ontologySearch=null;
	LinkedList<Individual> onProcess = new LinkedList<Individual>();
	LinkedList<Individual> PeerOnProcess = new LinkedList<Individual>();
	LinkedList<Individual> mappingOnProcess = new LinkedList<Individual>();
	
	public Oyster2Connection()
    {
		
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
			nO.setName("temporaryTrick");
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
			nO.setName("temporaryTrick");
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
	 * Search Oyster2 registry to retrieve all available
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
	 * certain scope and a certain set of Peers.
	 * @param o is an object that holds the conditions 
	 * that will be used for searching the registry.
	 * @param scope represents the scope of the search (local,
	 * automatic, manual)
	 * @param peerSet is the map of pairs (URI,OMVPeer object)
	 * representing the set of peers that will be included in the
	 * search. 
	 * @return The set of objects representing the
	 * metadata entries and that fulfill the conditions.
	 */
	
	public Set<Object> search(Object o, String scope,Map<String,OMVPeer> peerSet)
	{ 
		Set<Object> OMVSet=new HashSet <Object>();
		if (o instanceof OMVOntology){
			OMVSet.addAll(search((OMVOntology)o, scope, peerSet));
		}
		else if (o instanceof OMVMapping){
			
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
		if (isPropertyIn(prop) && isPropertyIn(prop1))
			IOntology.addImportOntologyToRegistry(pList,0);
	}


	/**
	 * Search Oyster2 registry to retrieve all available ontology
	 * metadata entries.
	 * @return The set of OMVOntology objects representing the
	 * ontology metadata entries.
	 */
	public Set<OMVOntology> getOntologies()
	{
		QueryReply qReply =null;
		LinkedList conditions = new LinkedList();
	
		QueryFormulator mFormulator = new QueryFormulator();
		mFormulator.generateDataQuery(conditions);
		//Oyster2Query topicQuery = mFormulator.getTopicQuery();
		Oyster2Query typeQuery = mFormulator.getTypeQuery();
		qReply = localRegistry.returnQueryReply(mOyster2.getLocalHostOntology(),typeQuery,Resource.DataResource);
		Set<OMVOntology> OMVSet = processReply(qReply);
		return OMVSet;
	}


	/**
	 * Search Oyster2 registry to retrieve all available ontology
	 * metadata entries within a certain scope and a certain set
	 * of Peers.
	 * @param scope represents the scope of the search (local,
	 * automatic, manual)
	 * @param peerSet is the map of pairs (URI,OMVPeer object)
	 * representing the set of peers that will be included in the
	 * search. 
	 * @return The set of OMVOntology objects representing the
	 * ontology metadata entries.
	 */
	public Set<OMVOntology> getOntologies(String scope,Map<String,OMVPeer> peerSet)
	{ 
		return null;
	}


	/**
	 * Search Oyster2 registry to retrieve all available ontology
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
	 * metadata entries that contains the input keyword in any part 
	 * of any of their data properties. Implements a keword match search.
	 * @param keyword is the keyword to search in the ontology properties.
	 * @return The set of OMVOntology objects representing the
	 * ontology metadata entries that matched the keyword.
	 */
	
	public Set<OMVOntology> getOntologies(String keyword)
	{ 
		
		QueryReply qReply =null;
		Oyster2Query query = new Oyster2Query(new GUID(),Oyster2Query.TOPIC_QUERY,keyword);
		qReply = localRegistry.returnQueryReply(mOyster2.getLocalHostOntology(),query,null,Resource.DataResource);
		Set<OMVOntology> OMVSet = processReply(qReply);
		return OMVSet;
	}


	/**
	 * Send a SPARQL query to Oyster2 registry and returns the 
	 * metadata entries that are part of the result.
	 * @param sparqlQuery is the SPARQL query as a string.
	 * @return The set of OMVOntology objects representing the
	 * ontology metadata entries that matched the query.
	 */
	
	public Set<Object> submitAdHocQuery (String sparqlQuery)
	{ 
		Set<Object> OMVRet = new HashSet<Object>();
		QueryReply qReply =null;
		Oyster2Query query = new Oyster2Query(Oyster2Query.TYPE_QUERY,sparqlQuery.toString());
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
		else{
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
				OMVOntology mainOntoReply=(OMVOntology)processIndividual(ontologyIndiv, "ontology");
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
	 * Search Oyster2 registry to retrieve all available mappings
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
		mFormulator.generateDataQuery(conditions);
		//Oyster2Query topicQuery = mFormulator.getTopicQuery();
		Oyster2Query typeQuery = mFormulator.getTypeQuery();
		qReply = localRegistry.returnQueryReplyGeneral(mOyster2.getLocalHostOntology(),typeQuery,Resource.DataResource);
		Set<OMVMapping> OMVSet = processMappings(qReply);
		return OMVSet;
	}


	/**
	 * Register a new ontology into Oyster2 registry.
	 * @param o is the OMVOntology object representing the
	 * ontology that will be registered.
	 */
	private void register(OMVOntology o)
	{
		pList.clear();
		pList=getProperties(o);
		OntologyProperty prop = new OntologyProperty(Constants.URI, "");
		OntologyProperty prop1 = new OntologyProperty(Constants.name, "");
		if (isPropertyIn(prop) && isPropertyIn(prop1))
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
		pList=getMappingProperties(o);
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
		pList=getProperties(o);
		OntologyProperty prop = new OntologyProperty(Constants.URI, "");
		OntologyProperty prop1 = new OntologyProperty(Constants.name, "");
		if (isPropertyIn(prop) && isPropertyIn(prop1))
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
		pList=getMappingProperties(o);
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
		pList=getProperties(o);
		OntologyProperty prop = new OntologyProperty(Constants.URI, "");
		OntologyProperty prop1 = new OntologyProperty(Constants.name, "");
		if (isPropertyIn(prop) && isPropertyIn(prop1))
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
		pList=getMappingProperties(o);
		OntologyProperty prop = new OntologyProperty(Constants.URI, "");
		if (isPropertyIn(prop))
			IOntology.addConceptToRegistry(4,pList,13);
	}
	
	
	/**
	 * Search Oyster2 registry to retrieve all available ontology
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
	
		conditions = getConditions(o,"");
		QueryFormulator mFormulator = new QueryFormulator();
		mFormulator.generateDataQuery(conditions);
		Oyster2Query typeQuery = mFormulator.getTypeQuery();
		qReply = localRegistry.returnQueryReply(mOyster2.getLocalHostOntology(),typeQuery,Resource.DataResource);
		Set<OMVOntology> OMVSet = processReply(qReply);
		return OMVSet;
	}
	
	
	/**
	 * Search Oyster2 registry to retrieve all available ontology
	 * metadata entries that fulfill certain conditions within a 
	 * certain scope and a certain set of Peers.
	 * @param o is a OMVOntology object that holds the conditions 
	 * that will be used for searching the registry.
	 * @param scope represents the scope of the search (local,
	 * automatic, manual)
	 * @param peerSet is the map of pairs (URI,OMVPeer object)
	 * representing the set of peers that will be included in the
	 * search. 
	 * @return The set of OMVOntology objects representing the
	 * ontology metadata entries and that fulfill the conditions.
	 */
	
	private Set<OMVOntology> search(OMVOntology o, String scope,Map<String,OMVPeer> peerSet)
	{ 
		return null;
	}
	
	/**
	 * Search Oyster2 registry to retrieve all available mapping
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
	
		conditions = getMappingConditions(o,"");
		Condition c1 = new Condition(Condition.TYPE_CONDITION,Constants.MOMVURI+Constants.mappingConcept,false);
		conditions.addFirst(c1);
		QueryFormulator mFormulator = new QueryFormulator();
		mFormulator.generateDataQuery(conditions);
		Oyster2Query typeQuery = mFormulator.getTypeQuery();
		qReply = localRegistry.returnQueryReplyGeneral(mOyster2.getLocalHostOntology(),typeQuery,Resource.DataResource);
		Set<OMVMapping> OMVSet = processMappings(qReply);
		return OMVSet;
	}
	
	
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
	
	private Set<OMVOntology> processReply(QueryReply queryReply){
		Set<OMVOntology> OMVSet= new HashSet <OMVOntology>();
		ontologySearch = queryReply.getOntology();
		Collection entrySet = queryReply.getResourceSet();
		try{
			Iterator it = entrySet.iterator();
			while(it.hasNext()){
				final Resource  entry =(Resource) it.next();
				final Individual oIndividual = (Individual)entry.getEntity();
				OMVOntology mainOntoReply=(OMVOntology)processIndividual(oIndividual, "ontology");
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
	
	private Object processIndividual(Individual ontoIndiv, String whichClass){
	 OMVOntology mainOntoReply=null;
	 OMVParty partyReply=null;
	 OMVOntologyEngineeringTool ontoEngToolReply=null;
	 OMVOntologyEngineeringMethodology ontoEngMetReply=null;
	 OMVKnowledgeRepresentationParadigm krParadigmReply=null;
	 OMVOntologyDomain oDomainReply=null;
	 OMVOntologyType oTypeReply=null;
	 OMVOntologyTask oTaskReply=null;
	 OMVOntologyLanguage oLanguageReply=null;
	 OMVOntologySyntax oSyntaxReply=null;
	 OMVFormalityLevel fLevelReply=null;
	 OMVLicenseModel lModelReply=null;
	 OMVLocation locationReply=null;
	 OMVPerson personReply=null;
	 OMVOrganisation organisationReply=null;
	 //OMVOntology oReferencesReply=null;
	 //OMVOntologyDomain oDomainReplySub=null;
	 if (!onProcess.contains(ontoIndiv)){
	  onProcess.add(ontoIndiv);  
	  try{
		Map dataPropertyMap = ontoIndiv.getDataPropertyValues(ontologySearch);
		Map objectPropertyMap = ontoIndiv.getObjectPropertyValues(ontologySearch);
		if ((dataPropertyMap.size()+objectPropertyMap.size())>0){
			
			if (whichClass.equalsIgnoreCase("ontology")) mainOntoReply = new OMVOntology();
			else if (whichClass.equalsIgnoreCase("party")) partyReply = new OMVParty();
			else if (whichClass.equalsIgnoreCase("ontoEngTool")) ontoEngToolReply = new OMVOntologyEngineeringTool();
			else if (whichClass.equalsIgnoreCase("ontoEngMet")) ontoEngMetReply = new OMVOntologyEngineeringMethodology();
			else if (whichClass.equalsIgnoreCase("krParadigm")) krParadigmReply = new OMVKnowledgeRepresentationParadigm();
			else if (whichClass.equalsIgnoreCase("oDomain")) oDomainReply = new OMVOntologyDomain();
			else if (whichClass.equalsIgnoreCase("oType")) oTypeReply = new OMVOntologyType();
			else if (whichClass.equalsIgnoreCase("oTask")) oTaskReply = new OMVOntologyTask();
			else if (whichClass.equalsIgnoreCase("oLanguage")) oLanguageReply = new OMVOntologyLanguage();
			else if (whichClass.equalsIgnoreCase("oSyntax")) oSyntaxReply = new OMVOntologySyntax();
			else if (whichClass.equalsIgnoreCase("fLevel")) fLevelReply = new OMVFormalityLevel();
			else if (whichClass.equalsIgnoreCase("lModel")) lModelReply = new OMVLicenseModel();
			else if (whichClass.equalsIgnoreCase("location")) locationReply = new OMVLocation();
			else if (whichClass.equalsIgnoreCase("person")) personReply = new OMVPerson();
			else if (whichClass.equalsIgnoreCase("organisation")) organisationReply = new OMVOrganisation();
			//else if (whichClass.equalsIgnoreCase("oReferences")) oReferencesReply = new OMVOntology();
			//else if (whichClass.equalsIgnoreCase("oDomainSub")) oDomainReplySub = new OMVOntologyDomain();
			
			Collection keySet = dataPropertyMap.keySet();
			Iterator keys = keySet.iterator();
			while(keys.hasNext()){
				String keyStr = keys.next().toString();
				DataProperty property = KAON2Manager.factory().dataProperty(keyStr);
				String	propertyValue = org.neon_toolkit.registry.util.Utilities.getString(ontoIndiv.getDataPropertyValue(ontologySearch,property));
					
				if (whichClass.equalsIgnoreCase("ontology")) mainOntoReply.append(createOMVOntology(property.getURI(),propertyValue));
				else if (whichClass.equalsIgnoreCase("party")) partyReply.append(createOMVParty(property.getURI(),propertyValue));
				else if (whichClass.equalsIgnoreCase("ontoEngTool")) ontoEngToolReply.append(createOMVOntologyEngineeringTool(property.getURI(),propertyValue));
				else if (whichClass.equalsIgnoreCase("ontoEngMet")) ontoEngMetReply.append(createOMVOntologyEngineeringMethodology(property.getURI(),propertyValue));
				else if (whichClass.equalsIgnoreCase("krParadigm")) krParadigmReply.append(createOMVKnowledgeRepresentationParadigm(property.getURI(),propertyValue));
				else if (whichClass.equalsIgnoreCase("oDomain")) oDomainReply.append(createOMVOntologyDomain(property.getURI(),propertyValue));
				else if (whichClass.equalsIgnoreCase("oType")) oTypeReply.append(createOMVOntologyType(property.getURI(),propertyValue));
				else if (whichClass.equalsIgnoreCase("oTask")) oTaskReply.append(createOMVOntologyTask(property.getURI(),propertyValue));
				else if (whichClass.equalsIgnoreCase("oLanguage")) oLanguageReply.append(createOMVOntologyLanguage(property.getURI(),propertyValue));
				else if (whichClass.equalsIgnoreCase("oSyntax")) oSyntaxReply.append(createOMVOntologySyntax(property.getURI(),propertyValue));
				else if (whichClass.equalsIgnoreCase("fLevel")) fLevelReply.append(createOMVFormalityLevel(property.getURI(),propertyValue));
				else if (whichClass.equalsIgnoreCase("lModel")) lModelReply.append(createOMVLicenseModel(property.getURI(),propertyValue));				
				else if (whichClass.equalsIgnoreCase("location")) locationReply.append(createOMVLocation(property.getURI(),propertyValue));
				else if (whichClass.equalsIgnoreCase("person")) personReply.append(createOMVPerson(property.getURI(),propertyValue));
				else if (whichClass.equalsIgnoreCase("organisation")) organisationReply.append(createOMVOrganisation(property.getURI(),propertyValue));
				//else if (whichClass.equalsIgnoreCase("oReferences")) oReferencesReply.append(createOMVOntologyReferences(property.getURI(),propertyValue));
				//else if (whichClass.equalsIgnoreCase("oDomainSub")) oDomainReplySub.append(createOMVOntologyDomainSub(property.getURI(),propertyValue));
				
				//System.out.println("The property "+property.getURI()+" has value: "+propertyValue);
			}
			keySet = objectPropertyMap.keySet();
			Iterator okeys = keySet.iterator();
			while(okeys.hasNext()){
				String keyStr = okeys.next().toString();
				ObjectProperty property = KAON2Manager.factory().objectProperty(keyStr);
				Collection propertyCol= new LinkedList();
				propertyCol = (Collection)objectPropertyMap.get(property);
				if(propertyCol==null)System.err.println("propertyCol is null");
				Iterator itInt = propertyCol.iterator();
				while(itInt.hasNext()){
					Entity propertyValue =(Entity) itInt.next();
						
					if (whichClass.equalsIgnoreCase("ontology")) mainOntoReply.append(createOMVOntology(property.getURI(),propertyValue.getURI()));
					else if (whichClass.equalsIgnoreCase("party")) partyReply.append(createOMVParty(property.getURI(),propertyValue.getURI()));
					else if (whichClass.equalsIgnoreCase("ontoEngTool")) ontoEngToolReply.append(createOMVOntologyEngineeringTool(property.getURI(),propertyValue.getURI()));
					else if (whichClass.equalsIgnoreCase("ontoEngMet")) ontoEngMetReply.append(createOMVOntologyEngineeringMethodology(property.getURI(),propertyValue.getURI()));
					else if (whichClass.equalsIgnoreCase("krParadigm")) krParadigmReply.append(createOMVKnowledgeRepresentationParadigm(property.getURI(),propertyValue.getURI()));
					else if (whichClass.equalsIgnoreCase("oDomain")) oDomainReply.append(createOMVOntologyDomain(property.getURI(),propertyValue.getURI()));
					else if (whichClass.equalsIgnoreCase("oType")) oTypeReply.append(createOMVOntologyType(property.getURI(),propertyValue.getURI()));
					else if (whichClass.equalsIgnoreCase("oTask")) oTaskReply.append(createOMVOntologyTask(property.getURI(),propertyValue.getURI()));
					else if (whichClass.equalsIgnoreCase("oLanguage")) oLanguageReply.append(createOMVOntologyLanguage(property.getURI(),propertyValue.getURI()));
					else if (whichClass.equalsIgnoreCase("oSyntax")) oSyntaxReply.append(createOMVOntologySyntax(property.getURI(),propertyValue.getURI()));
					else if (whichClass.equalsIgnoreCase("fLevel")) fLevelReply.append(createOMVFormalityLevel(property.getURI(),propertyValue.getURI()));
					else if (whichClass.equalsIgnoreCase("lModel")) lModelReply.append(createOMVLicenseModel(property.getURI(),propertyValue.getURI()));
					else if (whichClass.equalsIgnoreCase("location")) locationReply.append(createOMVLocation(property.getURI(),propertyValue.getURI()));
					else if (whichClass.equalsIgnoreCase("person")) personReply.append(createOMVPerson(property.getURI(),propertyValue.getURI()));
					else if (whichClass.equalsIgnoreCase("organisation")) organisationReply.append(createOMVOrganisation(property.getURI(),propertyValue.getURI()));
					//else if (whichClass.equalsIgnoreCase("oReferences")) oReferencesReply.append(createOMVOntologyReferences(property.getURI(),propertyValue.getURI()));
					//else if (whichClass.equalsIgnoreCase("oDomainSub")) oDomainReplySub.append(createOMVOntologyDomainSub(property.getURI(),propertyValue.getURI()));
					
					//System.out.println("The property "+property.getURI()+" has value: "+propertyValue);	
				}	
			}
		}
	  }catch(Exception e){
			System.out.println(e.toString()+" Search Problem in processIndividual");
	  }
	  onProcess.remove(ontoIndiv);
	  if (whichClass.equalsIgnoreCase("ontology")) return mainOntoReply;
	  else if (whichClass.equalsIgnoreCase("party")) return partyReply;
	  else if (whichClass.equalsIgnoreCase("ontoEngTool")) return ontoEngToolReply;
	  else if (whichClass.equalsIgnoreCase("ontoEngMet")) return ontoEngMetReply;
	  else if (whichClass.equalsIgnoreCase("krParadigm")) return krParadigmReply;
	  else if (whichClass.equalsIgnoreCase("oDomain")) return oDomainReply;
	  else if (whichClass.equalsIgnoreCase("oType")) return oTypeReply;
	  else if (whichClass.equalsIgnoreCase("oTask")) return oTaskReply;
	  else if (whichClass.equalsIgnoreCase("oLanguage")) return oLanguageReply;
	  else if (whichClass.equalsIgnoreCase("oSyntax")) return oSyntaxReply;
	  else if (whichClass.equalsIgnoreCase("fLevel")) return fLevelReply;
	  else if (whichClass.equalsIgnoreCase("lModel")) return lModelReply;
	  else if (whichClass.equalsIgnoreCase("location")) return locationReply;
	  else if (whichClass.equalsIgnoreCase("person")) return personReply;
	  else if (whichClass.equalsIgnoreCase("organisation")) return organisationReply;
	  //else if (whichClass.equalsIgnoreCase("oReferences")) return oReferencesReply;
	  //else if (whichClass.equalsIgnoreCase("oDomainSub")) return oDomainReplySub;
	  else return null;
	 }
	 else{
		if (whichClass.equalsIgnoreCase("ontology")) {mainOntoReply = new OMVOntology();mainOntoReply.setURI(ontoIndiv.getURI());return mainOntoReply;}
		else if (whichClass.equalsIgnoreCase("party")) {return null;}
		else if (whichClass.equalsIgnoreCase("ontoEngTool")) {ontoEngToolReply = new OMVOntologyEngineeringTool();ontoEngToolReply.setName(Namespaces.guessLocalName(ontoIndiv.getURI()));return ontoEngToolReply;}
		else if (whichClass.equalsIgnoreCase("ontoEngMet")) {ontoEngMetReply = new OMVOntologyEngineeringMethodology();ontoEngMetReply.setName(Namespaces.guessLocalName(ontoIndiv.getURI()));return ontoEngMetReply;}
		else if (whichClass.equalsIgnoreCase("krParadigm")) {krParadigmReply = new OMVKnowledgeRepresentationParadigm();krParadigmReply.setName(Namespaces.guessLocalName(ontoIndiv.getURI()));return krParadigmReply;}
		else if (whichClass.equalsIgnoreCase("oDomain")) {oDomainReply = new OMVOntologyDomain();oDomainReply.setURI(ontoIndiv.getURI());return oDomainReply;}
		else if (whichClass.equalsIgnoreCase("oType")) {oTypeReply = new OMVOntologyType();oTypeReply.setName(Namespaces.guessLocalName(ontoIndiv.getURI()));return oTypeReply;}
		else if (whichClass.equalsIgnoreCase("oTask")) {oTaskReply = new OMVOntologyTask();oTaskReply.setName(Namespaces.guessLocalName(ontoIndiv.getURI()));return oTaskReply;}
		else if (whichClass.equalsIgnoreCase("oLanguage")) {oLanguageReply = new OMVOntologyLanguage();oLanguageReply.setName(Namespaces.guessLocalName(ontoIndiv.getURI()));return oLanguageReply;}
		else if (whichClass.equalsIgnoreCase("oSyntax")) {oSyntaxReply = new OMVOntologySyntax();oSyntaxReply.setName(Namespaces.guessLocalName(ontoIndiv.getURI()));return oSyntaxReply;}
		else if (whichClass.equalsIgnoreCase("fLevel")) {fLevelReply = new OMVFormalityLevel();fLevelReply.setName(Namespaces.guessLocalName(ontoIndiv.getURI()));return fLevelReply;}
		else if (whichClass.equalsIgnoreCase("lModel")) {lModelReply = new OMVLicenseModel();lModelReply.setName(Namespaces.guessLocalName(ontoIndiv.getURI()));return lModelReply;}
		else if (whichClass.equalsIgnoreCase("location")) {locationReply = new OMVLocation();locationReply.setStreet(Namespaces.guessLocalName(ontoIndiv.getURI()));return locationReply;}
		else if (whichClass.equalsIgnoreCase("person")) {
			personReply = new OMVPerson();
			String localName=Namespaces.guessLocalName(ontoIndiv.getURI());
			Pattern p1 = Pattern.compile("[A-Z]+");
			Matcher m = p1.matcher(localName);
			int sp=0;
			while (m.find()) if (m.start()>0) sp=m.start();
			personReply.setFirstName(localName.substring(0, sp));
			personReply.setLastName(localName.substring(sp, localName.length()));
			return personReply;
		}
		else if (whichClass.equalsIgnoreCase("organisation")) {organisationReply = new OMVOrganisation();organisationReply.setName(Namespaces.guessLocalName(ontoIndiv.getURI()));return organisationReply;}
		//else if (whichClass.equalsIgnoreCase("oReferences")) {oReferencesReply = new OMVOntology();oReferencesReply.setURI(ontoIndiv.getURI());return oReferencesReply;}
		//else if (whichClass.equalsIgnoreCase("oDomainSub")) {oDomainReplySub = new OMVOntologyDomain();oDomainReplySub.setURI(ontoIndiv.getURI());return oDomainReplySub;}
		return null;
	 }
	}
	
	private OMVOntology createOMVOntology(String URI, String value){
	  OMVOntology mainOntoReply=new OMVOntology();
	  try{
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.URI)) {mainOntoReply.setURI(value);return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {mainOntoReply.setName(value);return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {mainOntoReply.setAcronym(value);return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {mainOntoReply.setDescription(value);return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) {mainOntoReply.setDocumentation(value);return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.keywords)) {mainOntoReply.setKeywords(value);return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.status)) {mainOntoReply.setStatus(value);return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.creationDate)) {mainOntoReply.setCreationDate(value);return mainOntoReply;}        //DateFormat df = DateFormat.getDateInstance();	
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.modificationDate)) {mainOntoReply.setModificationDate(value);return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasContributor)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party");
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person");
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				mainOntoReply.addHasContributor(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation");
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					mainOntoReply.addHasContributor(organisationReply);
					organisationReply = null;
				}
			}
			partyReply=null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasCreator)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party");
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person");
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				mainOntoReply.addHasCreator(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation");
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					mainOntoReply.addHasCreator(organisationReply);
					organisationReply = null;
				}
			}
			partyReply=null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.usedOntologyEngineeringTool)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyEngineeringTool ontoEngToolReply=(OMVOntologyEngineeringTool)processIndividual(oIndividual, "ontoEngTool");
			if (ontoEngToolReply!=null) mainOntoReply.addUsedOntologyEngineeringTool(ontoEngToolReply);
			ontoEngToolReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.usedOntologyEngineeringMethodology)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyEngineeringMethodology ontoEngMetReply=(OMVOntologyEngineeringMethodology)processIndividual(oIndividual, "ontoEngMet");
			if (ontoEngMetReply!=null) mainOntoReply.addUsedOntologyEngineeringMethodology(ontoEngMetReply);
			ontoEngMetReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.usedKnowledgeRepresentationParadigm)){
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVKnowledgeRepresentationParadigm krParadigmReply=(OMVKnowledgeRepresentationParadigm)processIndividual(oIndividual, "krParadigm");
			if (krParadigmReply!=null) mainOntoReply.addUsedKnowledgeRepresentationParadigm(krParadigmReply);
			krParadigmReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasDomain)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyDomain oDomainReply=(OMVOntologyDomain)processIndividual(oIndividual, "oDomain");
			if (oDomainReply==null) {
				oDomainReply = new OMVOntologyDomain();
				oDomainReply.setURI(value);
			}
			mainOntoReply.addHasDomain(oDomainReply);
			oDomainReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isOfType)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyType oTypeReply=(OMVOntologyType)processIndividual(oIndividual, "oType");
			if (oTypeReply!=null) mainOntoReply.setIsOfType(oTypeReply);
			oTypeReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.naturalLanguage)) {mainOntoReply.setNaturalLanguage(value);return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.designedForOntologyTask)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyTask oTaskReply=(OMVOntologyTask)processIndividual(oIndividual, "oTask");
			if (oTaskReply!=null) mainOntoReply.addDesignedForOntologyTask(oTaskReply);
			oTaskReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasOntologyLanguage)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyLanguage oLanguageReply=(OMVOntologyLanguage)processIndividual(oIndividual, "oLanguage");
			if (oLanguageReply!=null) mainOntoReply.setHasOntologyLanguage(oLanguageReply);
			oLanguageReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasOntologySyntax)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologySyntax oSyntaxReply=(OMVOntologySyntax)processIndividual(oIndividual, "oSyntax");
			if (oSyntaxReply!=null) mainOntoReply.setHasOntologySyntax(oSyntaxReply);
			oSyntaxReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasFormalityLevel)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVFormalityLevel fLevelReply=(OMVFormalityLevel)processIndividual(oIndividual, "fLevel");
			if (fLevelReply!=null) mainOntoReply.setHasFormalityLevel(fLevelReply);
			fLevelReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.resourceLocator)) {
			mainOntoReply.addResourceLocator(value);return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.version)) {mainOntoReply.setVersion(value);return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasLicense)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVLicenseModel lModelReply=(OMVLicenseModel)processIndividual(oIndividual, "lModel");
			if (lModelReply!=null) mainOntoReply.setHasLicense(lModelReply);
			lModelReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.useImports)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntology oReferencesReply=(OMVOntology)processIndividual(oIndividual, "ontology"); //oReferences
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			mainOntoReply.addUseImports(oReferencesReply);
			oReferencesReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasPriorVersion)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntology oReferencesReply=(OMVOntology)processIndividual(oIndividual, "ontology"); //oReferences
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			mainOntoReply.setHasPriorVersion(oReferencesReply);
			oReferencesReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isBackwardCompatibleWith)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntology oReferencesReply=(OMVOntology)processIndividual(oIndividual, "ontology"); //oReferences
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			mainOntoReply.addIsBackwardCompatibleWith(oReferencesReply);
			oReferencesReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isIncompatibleWith)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntology oReferencesReply=(OMVOntology)processIndividual(oIndividual, "ontology");  //oReferences
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			mainOntoReply.addIsIncompatibleWith(oReferencesReply);
			oReferencesReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.numberOfClasses)) {mainOntoReply.setNumberOfClasses(new Integer(value.substring(1, value.indexOf("\"", 2))));return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.numberOfProperties)) {mainOntoReply.setNumberOfProperties(new Integer(value.substring(1, value.indexOf("\"", 2))));return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.numberOfIndividuals)) {mainOntoReply.setNumberOfIndividuals(new Integer(value.substring(1, value.indexOf("\"", 2))));return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.numberOfAxioms)) {mainOntoReply.setNumberOfAxioms(new Integer(value.substring(1, value.indexOf("\"", 2))));return mainOntoReply;}
		
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.notes)) {mainOntoReply.setNotes(value);return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.keyClasses)) {mainOntoReply.setKeyClasses(value);return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.knownUsage)) {mainOntoReply.setKnownUsage(value);return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.expressiveness)) {mainOntoReply.setExpressiveness(value);return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isConsistentAccordingToReasoner)) 
		{
			if ((value.toString().equalsIgnoreCase("\"true\"^^<xsd:boolean>")))
				mainOntoReply.setIsConsistentAccordingToReasoner(true);
			else
				mainOntoReply.setIsConsistentAccordingToReasoner(false);
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.containsTBox)) 
		{
			if ((value.toString().equalsIgnoreCase("\"true\"^^<xsd:boolean>")))
				mainOntoReply.setContainsTBox(true);
			else
				mainOntoReply.setContainsTBox(false);
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.containsRBox)) 
		{
			if ((value.toString().equalsIgnoreCase("\"true\"^^<xsd:boolean>")))
				mainOntoReply.setContainsRBox(true);
			else
				mainOntoReply.setContainsRBox(false);
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.containsABox)) 
		{
			if ((value.toString().equalsIgnoreCase("\"true\"^^<xsd:boolean>")))
				mainOntoReply.setContainsABox(true);
			else
				mainOntoReply.setContainsABox(false);
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.endorsedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party");
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person");
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				mainOntoReply.addEndorsedBy(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation");
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					mainOntoReply.addEndorsedBy(organisationReply);
					organisationReply = null;
				}
			}
			partyReply=null;
			return mainOntoReply;
		}
		
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.timeStamp)) {mainOntoReply.setTimeStamp(value);return mainOntoReply;}
	  }catch(Exception e){
			System.out.println(e.toString()+" Search Problem in createOMVOntology");
	  }
	  return mainOntoReply;
	}
	
	private OMVParty createOMVParty(String URI, String value){
	  OMVParty partyReply = new OMVParty();	
	  try{
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isLocatedAt)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVLocation locationReply=(OMVLocation)processIndividual(oIndividual, "location");
			if (locationReply!=null) partyReply.addIsLocatedAt(locationReply);
			locationReply = null;
			return partyReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developesOntologyEngineeringTool)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyEngineeringTool ontoEngToolReply=(OMVOntologyEngineeringTool)processIndividual(oIndividual, "ontoEngTool");
			if (ontoEngToolReply!=null) partyReply.addDevelopesOntologyEngineeringTool(ontoEngToolReply);
			ontoEngToolReply = null;
			return partyReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developesOntologyLanguage)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyLanguage oLanguageReply=(OMVOntologyLanguage)processIndividual(oIndividual, "oLanguage");
			if (oLanguageReply!=null) partyReply.addDevelopesOntologyLanguage(oLanguageReply);
			oLanguageReply = null;
			return partyReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developesOntologySyntax)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologySyntax oSyntaxReply=(OMVOntologySyntax)processIndividual(oIndividual, "oSyntax");
			if (oSyntaxReply!=null) partyReply.addDevelopesOntologySyntax(oSyntaxReply);
			oSyntaxReply = null;
			return partyReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.specifiesKnowledgeRepresentationParadigm)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVKnowledgeRepresentationParadigm krParadigmReply=(OMVKnowledgeRepresentationParadigm)processIndividual(oIndividual, "krParadigm");
			if (krParadigmReply!=null) partyReply.addSpecifiesKnowledgeRepresentationParadigm(krParadigmReply);
			krParadigmReply = null;
			return partyReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.definesOntologyType)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyType oTypeReply=(OMVOntologyType)processIndividual(oIndividual, "oType");
			if (oTypeReply!=null) partyReply.addDefinesOntologyType(oTypeReply);
			oTypeReply = null;
			return partyReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developesOntologyEngineeringMethodology)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyEngineeringMethodology ontoEngMetReply=(OMVOntologyEngineeringMethodology)processIndividual(oIndividual, "ontoEngMet");
			if (ontoEngMetReply!=null) partyReply.addDevelopesOntologyEngineeringMethodology(ontoEngMetReply);
			ontoEngMetReply = null;
			return partyReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.specifiesLicense)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVLicenseModel lModelReply=(OMVLicenseModel)processIndividual(oIndividual, "lModel");
			if (lModelReply!=null) partyReply.addSpecifiesLicense(lModelReply);
			lModelReply = null;
			return partyReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasAffiliatedParty)) { //now should work//CHANGE IT LIKE OMVOntology
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReplyAff = (OMVParty)processIndividual(oIndividual, "party");
			if (partyReplyAff!=null) partyReply.addHasAffiliatedParty(partyReplyAff);
			partyReplyAff = null;
			return partyReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.createsOntology))  {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntology oReferencesReply=(OMVOntology)processIndividual(oIndividual, "ontology"); //oReferences
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			partyReply.addCreatesOntology(oReferencesReply);
			oReferencesReply = null;
			return partyReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.contributesToOntology))  {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntology oReferencesReply=(OMVOntology)processIndividual(oIndividual, "ontology");  //oReferences
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			partyReply.addContributesToOntology(oReferencesReply);
			oReferencesReply = null;
			return partyReply;
		}
	  }catch(Exception e){
			System.out.println(e.toString()+" Search Problem in createOMVParty");
	  }
	  return partyReply;
	}
	
	private OMVOntologyEngineeringTool createOMVOntologyEngineeringTool(String URI, String value){
		OMVOntologyEngineeringTool ontoEngToolReply=new OMVOntologyEngineeringTool();
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {ontoEngToolReply.setName(value); return ontoEngToolReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {ontoEngToolReply.setAcronym(value); return ontoEngToolReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {ontoEngToolReply.setDescription(value); return ontoEngToolReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) {ontoEngToolReply.setDocumentation(value); return ontoEngToolReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party");
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person");
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				ontoEngToolReply.addDevelopedBy(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation");
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					ontoEngToolReply.addDevelopedBy(organisationReply);
					organisationReply = null;
				}
			}
			partyReply = null;
			return ontoEngToolReply;
		}
		return ontoEngToolReply;
	}
	
	private OMVOntologyEngineeringMethodology createOMVOntologyEngineeringMethodology(String URI, String value){
		OMVOntologyEngineeringMethodology ontoEngMetReply=new OMVOntologyEngineeringMethodology();  
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {ontoEngMetReply.setName(value); return ontoEngMetReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {ontoEngMetReply.setAcronym(value); return ontoEngMetReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {ontoEngMetReply.setDescription(value); return ontoEngMetReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) {ontoEngMetReply.setDocumentation(value); return ontoEngMetReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party");
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person");
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				ontoEngMetReply.addDevelopedBy(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation");
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					ontoEngMetReply.addDevelopedBy(organisationReply);
					organisationReply = null;
				}
			}
			partyReply = null;
			return ontoEngMetReply;
		}
		return ontoEngMetReply;
	}
	
	private OMVKnowledgeRepresentationParadigm createOMVKnowledgeRepresentationParadigm(String URI, String value){
		OMVKnowledgeRepresentationParadigm krParadigmReply=new OMVKnowledgeRepresentationParadigm(); 
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {krParadigmReply.setName(value); return krParadigmReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {krParadigmReply.setAcronym(value); return krParadigmReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {krParadigmReply.setDescription(value); return krParadigmReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) {krParadigmReply.setDocumentation(value); return krParadigmReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.specifiedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party");
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person");
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				krParadigmReply.addSpecifiedBy(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation");
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					krParadigmReply.addSpecifiedBy(organisationReply);
					organisationReply = null;
				}
			}
			partyReply = null;
			return krParadigmReply;
		}
		return krParadigmReply;
	}
	
	private OMVOntologyDomain createOMVOntologyDomain(String URI, String value){
		OMVOntologyDomain oDomainReply=new OMVOntologyDomain(); 
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.URI)) {oDomainReply.setURI(value); return oDomainReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {oDomainReply.setName(value); return oDomainReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isSubDomainOf)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyDomain oDomainReplySub=(OMVOntologyDomain)processIndividual(oIndividual, "oDomain"); //oDomainSub
			if (oDomainReplySub==null) {
				oDomainReplySub = new OMVOntologyDomain();
				oDomainReplySub.setURI(value);
			}
			oDomainReply.addIsSubDomainOf(oDomainReplySub);
			oDomainReplySub = null;
			return oDomainReply;
		}
		return oDomainReply;
	}
				
	private OMVOntologyType createOMVOntologyType(String URI, String value){
		OMVOntologyType oTypeReply=new OMVOntologyType(); 
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {oTypeReply.setName(value); return oTypeReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {oTypeReply.setAcronym(value); return oTypeReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {oTypeReply.setDescription(value); return oTypeReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) {oTypeReply.setDocumentation(value); return oTypeReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.definedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party");
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person");
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				oTypeReply.addDefinedBy(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation");
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					oTypeReply.addDefinedBy(organisationReply);
					organisationReply = null;
				}
			}
			partyReply = null;
			return oTypeReply;
		}
		return oTypeReply;
	}
	
	private OMVOntologyTask createOMVOntologyTask(String URI, String value){
		OMVOntologyTask oTaskReply=new OMVOntologyTask(); 
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {oTaskReply.setName(value); return oTaskReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {oTaskReply.setAcronym(value); return oTaskReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {oTaskReply.setDescription(value); return oTaskReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) {oTaskReply.setDocumentation(value); return oTaskReply;}
		return oTaskReply;
	}
	
	private OMVOntologyLanguage createOMVOntologyLanguage(String URI, String value){
		OMVOntologyLanguage oLanguageReply=new OMVOntologyLanguage(); 
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {oLanguageReply.setName(value); return oLanguageReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {oLanguageReply.setAcronym(value); return oLanguageReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {oLanguageReply.setDescription(value); return oLanguageReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) {oLanguageReply.setDocumentation(value); return oLanguageReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party");
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person");
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				oLanguageReply.addDevelopedBy(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation");
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					oLanguageReply.addDevelopedBy(organisationReply);
					organisationReply = null;
				}
			}
			partyReply = null;
			return oLanguageReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.supportsRepresentationParadigm)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVKnowledgeRepresentationParadigm krParadigmReply=(OMVKnowledgeRepresentationParadigm)processIndividual(oIndividual, "krParadigm");
			if (krParadigmReply!=null) oLanguageReply.addSupportsRepresentationParadigm(krParadigmReply);
			krParadigmReply = null;
			return oLanguageReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasSyntax)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologySyntax oSyntaxReply=(OMVOntologySyntax)processIndividual(oIndividual, "oSyntax");
			if (oSyntaxReply!=null) oLanguageReply.addHasSyntax(oSyntaxReply);
			oSyntaxReply = null;
			return oLanguageReply;
		}
		return oLanguageReply;
	}
	
	private OMVOntologySyntax createOMVOntologySyntax(String URI, String value){
		OMVOntologySyntax oSyntaxReply=new OMVOntologySyntax();
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {oSyntaxReply.setName(value); return oSyntaxReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {oSyntaxReply.setAcronym(value); return oSyntaxReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {oSyntaxReply.setDescription(value); return oSyntaxReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) {oSyntaxReply.setDocumentation(value); return oSyntaxReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party");
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person");
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				oSyntaxReply.addDevelopedBy(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation");
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					oSyntaxReply.addDevelopedBy(organisationReply);
					organisationReply = null;
				}
			}
			partyReply = null;
			return oSyntaxReply;
		}
		return oSyntaxReply;
	}
	
	private OMVFormalityLevel createOMVFormalityLevel(String URI, String value){
		OMVFormalityLevel fLevelReply=new OMVFormalityLevel(); 
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {fLevelReply.setName(value); return fLevelReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {fLevelReply.setDescription(value); return fLevelReply;}
		return fLevelReply;
	}
	
	private OMVLicenseModel createOMVLicenseModel(String URI, String value){
		OMVLicenseModel lModelReply=new OMVLicenseModel(); 
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {lModelReply.setName(value); return lModelReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {lModelReply.setAcronym(value); return lModelReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {lModelReply.setDescription(value); return lModelReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) {lModelReply.setDocumentation(value); return lModelReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.specifiedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party");
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person");
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				lModelReply.addSpecifiedBy(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation");
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					lModelReply.addSpecifiedBy(organisationReply);
					organisationReply = null;
				}
			}
			partyReply = null;
			return lModelReply;
		}
		return lModelReply;
	}
	
	private OMVLocation createOMVLocation(String URI, String value){
		OMVLocation locationReply=new OMVLocation(); 
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.state)) {locationReply.setState(value); return locationReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.country)) {locationReply.setCountry(value); return locationReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.city)) {locationReply.setCity(value); return locationReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.street)) {locationReply.setStreet(value); return locationReply;}
		return locationReply;
	}
	
	private OMVPerson createOMVPerson(String URI, String value){
		OMVPerson personReply=new OMVPerson();
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.lastName)) {personReply.setLastName(value); return personReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.firstName)) {personReply.setFirstName(value); return personReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.eMail)) {personReply.setEMail(value);return personReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.phoneNumber)) {personReply.setPhoneNumber(value);return personReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.faxNumber)) {personReply.setFaxNumber(value);return personReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isContactPerson)) {
			Individual oIndividual1 =KAON2Manager.factory().individual(value);
			OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual1, "organisation");
			if (organisationReply!=null) personReply.addIsContactPerson(organisationReply);
			organisationReply = null;
			return personReply;
		}
		return personReply;
	}
	
	private OMVOrganisation createOMVOrganisation(String URI, String value){
		OMVOrganisation organisationReply=new OMVOrganisation(); 
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {organisationReply.setName(value); return organisationReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {organisationReply.setAcronym(value); return organisationReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasContactPerson)) {
			Individual oIndividual1 =KAON2Manager.factory().individual(value);
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual1, "person");
			if (personReply!=null) organisationReply.addHasContactPerson(personReply);
			personReply = null;
			return organisationReply;
		}
		return organisationReply;
	}
		
	private LinkedList<Condition> getPersonConditions (OMVPerson o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getFirstName()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.firstName, o.getFirstName(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.firstName, o.getFirstName(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getLastName()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.lastName, o.getLastName(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.lastName, o.getLastName(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getEMail()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.eMail, o.getEMail(), checkDataProperty(Constants.eMail));
			else condition = new Condition(Constants.omvCondition+Constants.eMail, o.getEMail(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getPhoneNumber()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.phoneNumber, o.getPhoneNumber(), checkDataProperty(Constants.phoneNumber));
			else condition = new Condition(Constants.omvCondition+Constants.phoneNumber, o.getPhoneNumber(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getFaxNumber()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.faxNumber, o.getFaxNumber(), checkDataProperty(Constants.faxNumber));
			else condition = new Condition(Constants.omvCondition+Constants.faxNumber, o.getFaxNumber(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getIsContactPerson().size()>0) {
			Iterator it = o.getIsContactPerson().iterator();
			while(it.hasNext()){
				OMVOrganisation org = (OMVOrganisation)it.next();
				LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.omvCondition+Constants.isContactPerson);
				searchConditions.addAll(conditionTemp);
			}
		}
		
		return searchConditions;
	}
	
	private LinkedList<Condition> getOrganisationConditions (OMVOrganisation o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getName()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getAcronym()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getHasContactPerson().size()>0) {
			Iterator it = o.getHasContactPerson().iterator();
			while(it.hasNext()){
				OMVPerson per = (OMVPerson)it.next();
				LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.omvCondition+Constants.hasContactPerson);
				searchConditions.addAll(conditionTemp);
			}
		}
		
		return searchConditions;
	}
	
	private LinkedList<Condition> getOETConditions (OMVOntologyEngineeringTool o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getName()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getAcronym()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDescription()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), checkDataProperty(Constants.description));
			else condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getDocumentation()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), checkDataProperty(Constants.documentation));
			else condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDevelopedBy().size()>0) {
			Iterator it = o.getDevelopedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.omvCondition+Constants.developedBy);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.omvCondition+Constants.developedBy);
					searchConditions.addAll(conditionTemp);
				}
			}
		}	
		return searchConditions;
	}
	
	private LinkedList<Condition> getKRPConditions (OMVKnowledgeRepresentationParadigm o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getName()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getAcronym()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDescription()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), checkDataProperty(Constants.description));
			else condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getDocumentation()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), checkDataProperty(Constants.documentation));
			else condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getSpecifiedBy().size()>0) {
			Iterator it = o.getSpecifiedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.omvCondition+Constants.specifiedBy);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.omvCondition+Constants.specifiedBy);
					searchConditions.addAll(conditionTemp);
				}
			}
		}	
		return searchConditions;
	}
	
	private LinkedList<Condition> getOEMConditions (OMVOntologyEngineeringMethodology o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getName()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getAcronym()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDescription()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), checkDataProperty(Constants.description));
			else condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getDocumentation()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), checkDataProperty(Constants.documentation));
			else condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDevelopedBy().size()>0) {
			Iterator it = o.getDevelopedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.omvCondition+Constants.developedBy);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.omvCondition+Constants.developedBy);
					searchConditions.addAll(conditionTemp);
				}
			}
		}	
		return searchConditions;
	}
	
	private LinkedList<Condition> getOTConditions (OMVOntologyType o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getName()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getAcronym()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDescription()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), checkDataProperty(Constants.description));
			else condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getDocumentation()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), checkDataProperty(Constants.documentation));
			else condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDefinedBy().size()>0) {
			Iterator it = o.getDefinedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.omvCondition+Constants.definedBy);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.omvCondition+Constants.definedBy);
					searchConditions.addAll(conditionTemp);
				}
			}
		}	
		return searchConditions;
	}
	
	private LinkedList<Condition> getOLConditions (OMVOntologyLanguage o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getName()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getAcronym()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDescription()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), checkDataProperty(Constants.description));
			else condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getDocumentation()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), checkDataProperty(Constants.documentation));
			else condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDevelopedBy().size()>0) {
			Iterator it = o.getDevelopedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.omvCondition+Constants.developedBy);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.omvCondition+Constants.developedBy);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		if (o.getSupportsRepresentationParadigm().size()>0) {
			Iterator it = o.getSupportsRepresentationParadigm().iterator();
			while(it.hasNext()){
				OMVKnowledgeRepresentationParadigm krp = (OMVKnowledgeRepresentationParadigm)it.next();
				LinkedList<Condition> conditionTemp=getKRPConditions(krp,Constants.omvCondition+Constants.supportsRepresentationParadigm);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getHasSyntax().size()>0) {
			Iterator it = o.getHasSyntax().iterator();
			while(it.hasNext()){
				OMVOntologySyntax osy = (OMVOntologySyntax)it.next();
				LinkedList<Condition> conditionTemp=getOSConditions(osy,Constants.omvCondition+Constants.hasSyntax);
				searchConditions.addAll(conditionTemp);
			}
		}
		return searchConditions;
	}
	
	private LinkedList<Condition> getOSConditions (OMVOntologySyntax o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getName()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getAcronym()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDescription()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), checkDataProperty(Constants.description));
			else condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getDocumentation()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), checkDataProperty(Constants.documentation));
			else condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDevelopedBy().size()>0) {
			Iterator it = o.getDevelopedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.omvCondition+Constants.developedBy);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.omvCondition+Constants.developedBy);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		
		return searchConditions;
	}
	
	private LinkedList<Condition> getLMConditions (OMVLicenseModel o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getName()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getAcronym()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDescription()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), checkDataProperty(Constants.description));
			else condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getDocumentation()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), checkDataProperty(Constants.documentation));
			else condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getSpecifiedBy().size()>0) {
			Iterator it = o.getSpecifiedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.omvCondition+Constants.specifiedBy);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.omvCondition+Constants.specifiedBy);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		
		return searchConditions;
	}
	
	private LinkedList<Condition> getFLConditions (OMVFormalityLevel o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getName()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getDescription()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), checkDataProperty(Constants.description));
			else condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), which); 
			searchConditions.addFirst(condition);
		}
		return searchConditions;
	}
	
	private LinkedList<Condition> getOTAConditions (OMVOntologyTask o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getName()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getAcronym()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDescription()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), checkDataProperty(Constants.description));
			else condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getDocumentation()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), checkDataProperty(Constants.documentation));
			else condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), which);
			searchConditions.addFirst(condition);
		}
		return searchConditions;
	}
	
	private LinkedList<Condition> getODConditions (OMVOntologyDomain o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getURI()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.URI, o.getURI(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.URI, o.getURI(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getName()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getIsSubDomainOf().size()>0) {
			Iterator it = o.getIsSubDomainOf().iterator();
			while(it.hasNext()){
				OMVOntologyDomain od = (OMVOntologyDomain)it.next();
				LinkedList<Condition> conditionTemp=getODConditions(od,Constants.omvCondition+Constants.isSubDomainOf);
				searchConditions.addAll(conditionTemp);
				//Condition condition = new Condition(Condition.TOPIC_CONDITION, od.getURI(), checkDataProperty(Constants.hasDomain));
				//searchConditions.addFirst(condition);
			}
		}
		return searchConditions;
	}

	private LinkedList<Condition> getConditions (OMVOntology o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getURI()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.URI, o.getURI(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.URI, o.getURI(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getName()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getAcronym()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), checkDataProperty(Constants.acronym));
			else condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDescription()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), checkDataProperty(Constants.description));
			else condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDocumentation()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), checkDataProperty(Constants.documentation));
			else condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getKeywords()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.keywords, o.getKeywords(), checkDataProperty(Constants.keywords));
			else condition = new Condition(Constants.omvCondition+Constants.keywords, o.getKeywords(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getStatus()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.status, o.getStatus(), checkDataProperty(Constants.status));
			else condition = new Condition(Constants.omvCondition+Constants.status, o.getStatus(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getCreationDate()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.creationDate, o.getCreationDate(), checkDataProperty(Constants.creationDate));
			else condition = new Condition(Constants.omvCondition+Constants.creationDate, o.getCreationDate(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getModificationDate()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.modificationDate, o.getModificationDate(), checkDataProperty(Constants.modificationDate));
			else condition = new Condition(Constants.omvCondition+Constants.modificationDate, o.getModificationDate(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getHasContributor().size()>0) {
			Iterator it = o.getHasContributor().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.omvCondition+Constants.hasContributor);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.omvCondition+Constants.hasContributor);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		if (o.getHasCreator().size()>0) {
			Iterator it = o.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.omvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.omvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		if (o.getUsedOntologyEngineeringTool().size()>0) {
			Iterator it = o.getUsedOntologyEngineeringTool().iterator();
			while(it.hasNext()){
				OMVOntologyEngineeringTool oet = (OMVOntologyEngineeringTool)it.next();
				LinkedList<Condition> conditionTemp=getOETConditions(oet,Constants.omvCondition+Constants.usedOntologyEngineeringTool);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getUsedOntologyEngineeringMethodology().size()>0) {
			Iterator it = o.getUsedOntologyEngineeringMethodology().iterator();
			while(it.hasNext()){
				OMVOntologyEngineeringMethodology oem = (OMVOntologyEngineeringMethodology)it.next();
				LinkedList<Condition> conditionTemp=getOEMConditions(oem,Constants.omvCondition+Constants.usedOntologyEngineeringMethodology);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getUsedKnowledgeRepresentationParadigm().size()>0) {
			Iterator it = o.getUsedKnowledgeRepresentationParadigm().iterator();
			while(it.hasNext()){
				OMVKnowledgeRepresentationParadigm krp = (OMVKnowledgeRepresentationParadigm)it.next();
				LinkedList<Condition> conditionTemp=getKRPConditions(krp,Constants.omvCondition+Constants.usedKnowledgeRepresentationParadigm);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getHasDomain().size()>0) {
			Iterator it = o.getHasDomain().iterator();
			while(it.hasNext()){
				OMVOntologyDomain od = (OMVOntologyDomain)it.next();
				LinkedList<Condition> conditionTemp=getODConditions(od,Constants.omvCondition+Constants.hasDomain);
				searchConditions.addAll(conditionTemp);
				//Condition condition = new Condition(Condition.TOPIC_CONDITION, od.getURI(), checkDataProperty(Constants.hasDomain));
				//searchConditions.addFirst(condition);
			}
		}
		if (o.getIsOfType()!=null) {
			OMVOntologyType ot = o.getIsOfType();
			LinkedList<Condition> conditionTemp=getOTConditions(ot,Constants.omvCondition+Constants.isOfType);
			searchConditions.addAll(conditionTemp);
		}
		if (o.getNaturalLanguage()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.naturalLanguage, o.getNaturalLanguage(), checkDataProperty(Constants.naturalLanguage));
			else condition = new Condition(Constants.omvCondition+Constants.naturalLanguage, o.getNaturalLanguage(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDesignedForOntologyTask().size()>0) {
			Iterator it = o.getDesignedForOntologyTask().iterator();
			while(it.hasNext()){
				OMVOntologyTask ota = (OMVOntologyTask)it.next();
				LinkedList<Condition> conditionTemp=getOTAConditions(ota,Constants.omvCondition+Constants.designedForOntologyTask);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getHasOntologyLanguage()!=null) {
			OMVOntologyLanguage ola = o.getHasOntologyLanguage();
			LinkedList<Condition> conditionTemp=getOLConditions(ola,Constants.omvCondition+Constants.hasOntologyLanguage);
			searchConditions.addAll(conditionTemp);
		}
		if (o.getHasOntologySyntax()!=null) {
			OMVOntologySyntax osy = o.getHasOntologySyntax();
			LinkedList<Condition> conditionTemp=getOSConditions(osy,Constants.omvCondition+Constants.hasOntologySyntax);
			searchConditions.addAll(conditionTemp);
		}
		if (o.getHasFormalityLevel()!=null) {
			OMVFormalityLevel fl = o.getHasFormalityLevel();
			LinkedList<Condition> conditionTemp=getFLConditions(fl,Constants.omvCondition+Constants.hasFormalityLevel);
			searchConditions.addAll(conditionTemp);
		}
		if (o.getResourceLocator()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.resourceLocator, o.getResourceLocator(), checkDataProperty(Constants.resourceLocator));
			else condition = new Condition(Constants.omvCondition+Constants.resourceLocator, o.getResourceLocator(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getVersion()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.version, o.getVersion(), checkDataProperty(Constants.version));
			else condition = new Condition(Constants.omvCondition+Constants.version, o.getVersion(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getHasLicense()!=null) {
			OMVLicenseModel lm = o.getHasLicense();
			LinkedList<Condition> conditionTemp=getLMConditions(lm,Constants.omvCondition+Constants.hasLicense);
			searchConditions.addAll(conditionTemp);
		}
		if (o.getUseImports().size()>0) {
			Iterator it = o.getUseImports().iterator();
			while(it.hasNext()){
				OMVOntology otemp = (OMVOntology)it.next();
				LinkedList<Condition> conditionTemp=getConditions(otemp,Constants.omvCondition+Constants.useImports);
				searchConditions.addAll(conditionTemp);
				//Condition condition = new Condition(Constants.omvCondition+Constants.useImports, otemp.getURI(), checkDataProperty(Constants.useImports));
				//searchConditions.addFirst(condition);
			}
		}
		if (o.getHasPriorVersion()!=null) {
			OMVOntology otemp = o.getHasPriorVersion();
			LinkedList<Condition> conditionTemp=getConditions(otemp,Constants.omvCondition+Constants.hasPriorVersion);
			searchConditions.addAll(conditionTemp);
		}
		if (o.getIsBackwardCompatibleWith().size()>0) {
			Iterator it = o.getIsBackwardCompatibleWith().iterator();
			while(it.hasNext()){
				OMVOntology otemp = (OMVOntology)it.next();
				LinkedList<Condition> conditionTemp=getConditions(otemp,Constants.omvCondition+Constants.isBackwardCompatibleWith);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getIsIncompatibleWith().size()>0) {
			Iterator it = o.getIsIncompatibleWith().iterator();
			while(it.hasNext()){
				OMVOntology otemp = (OMVOntology)it.next();
				LinkedList<Condition> conditionTemp=getConditions(otemp,Constants.omvCondition+Constants.isIncompatibleWith);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getNumberOfClasses()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.numberOfClasses, o.getNumberOfClasses().toString(), checkDataProperty(Constants.numberOfClasses));
			else condition = new Condition(Constants.omvCondition+Constants.numberOfClasses, o.getNumberOfClasses().toString(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getNumberOfProperties()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.numberOfProperties, o.getNumberOfProperties().toString(), checkDataProperty(Constants.numberOfProperties));
			else condition = new Condition(Constants.omvCondition+Constants.numberOfProperties, o.getNumberOfProperties().toString(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getNumberOfIndividuals()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.numberOfIndividuals, o.getNumberOfIndividuals().toString(), checkDataProperty(Constants.numberOfIndividuals));
			else condition = new Condition(Constants.omvCondition+Constants.numberOfIndividuals, o.getNumberOfIndividuals().toString(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getNumberOfAxioms()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.numberOfAxioms, o.getNumberOfAxioms().toString(), checkDataProperty(Constants.numberOfAxioms));
			else condition = new Condition(Constants.omvCondition+Constants.numberOfAxioms, o.getNumberOfAxioms().toString(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getNotes()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.notes, o.getNotes(), checkDataProperty(Constants.notes));
			else condition = new Condition(Constants.omvCondition+Constants.notes, o.getNotes(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getKeyClasses()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.keyClasses, o.getKeyClasses(), checkDataProperty(Constants.keyClasses));
			else condition = new Condition(Constants.omvCondition+Constants.keyClasses, o.getKeyClasses(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getKnownUsage()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.knownUsage, o.getKnownUsage(), checkDataProperty(Constants.knownUsage));
			else condition = new Condition(Constants.omvCondition+Constants.knownUsage, o.getKnownUsage(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getExpressiveness()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.expressiveness, o.getExpressiveness(), checkDataProperty(Constants.expressiveness));
			else condition = new Condition(Constants.omvCondition+Constants.expressiveness, o.getExpressiveness(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getIsConsistentAccordingToReasoner()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.isConsistentAccordingToReasoner, o.getIsConsistentAccordingToReasoner().toString(), checkDataProperty(Constants.isConsistentAccordingToReasoner));
			else condition = new Condition(Constants.omvCondition+Constants.isConsistentAccordingToReasoner, o.getIsConsistentAccordingToReasoner().toString(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getContainsTBox()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.containsTBox, o.getContainsTBox().toString(), checkDataProperty(Constants.containsTBox));
			else condition = new Condition(Constants.omvCondition+Constants.containsTBox, o.getContainsTBox().toString(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getContainsRBox()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.containsRBox, o.getContainsRBox().toString(), checkDataProperty(Constants.containsRBox));
			else condition = new Condition(Constants.omvCondition+Constants.containsRBox, o.getContainsRBox().toString(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getContainsABox()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.containsABox, o.getContainsABox().toString(), checkDataProperty(Constants.containsABox));
			else condition = new Condition(Constants.omvCondition+Constants.containsABox, o.getContainsABox().toString(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getEndorsedBy().size()>0) {
			Iterator it = o.getEndorsedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.omvCondition+Constants.endorsedBy);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.omvCondition+Constants.endorsedBy);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		
		return searchConditions;
	}
	
	private Boolean checkDataProperty(String propertyName)  {
		Ontology resourceTypeOntology = mOyster2.getTypeOntology();
		OWLClass ontologyClass = KAON2Manager.factory().owlClass(Constants.OMV+Constants.DefaultTypeOntologyRoot);
		try{
			/*KAON2 BUG, DOES NOT SUPPORT OWL DL
	         * SHOULD BE DELETED WHEN IT DOES
	         */
			if(propertyName.equals(Constants.name)) return true;
			if(propertyName.equals(Constants.acronym)) return true;
			if(propertyName.equals(Constants.description)) return true;
			if(propertyName.equals(Constants.documentation)) return true;
			if(propertyName.equals(Constants.URI)) return true;
			
			/* UNTIL HERE */
			
			Set<DataProperty> dataProperties=ontologyClass.getDataPropertiesFrom(resourceTypeOntology);
			for (DataProperty dataProperty : dataProperties){
				if(propertyName.equals(Namespaces.guessLocalName(dataProperty.getURI()))) return true; 
			}
		}
	    catch (KAON2Exception e) {
	    	System.err.println(e + " in checkDataProperty()");
	    }
		return false;
	}
	
	private LinkedList getPropertiesPerson(OMVPerson p){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (p.getFirstName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.firstName, p.getFirstName());
			tProperties.addFirst(prop);
		}
		if (p.getLastName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.lastName, p.getLastName());
			tProperties.addFirst(prop);
		}
		if (p.getEMail()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.eMail, p.getEMail());
			tProperties.addFirst(prop);
		}
		if (p.getPhoneNumber()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.phoneNumber, p.getPhoneNumber());
			tProperties.addFirst(prop);
		}
		if (p.getFaxNumber()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.faxNumber, p.getFaxNumber());
			tProperties.addFirst(prop);
		}
		if (p.getIsContactPerson()!=null) {
			String tURN;
			Iterator it = p.getIsContactPerson().iterator();
			while(it.hasNext()){
				OMVOrganisation o = (OMVOrganisation)it.next();
				if (o.getName()!=null){
					tURN=o.getName();
					tList.clear();
					tList=getPropertiesOrganisation(o);
					IOntology.addConceptToRegistry(0,tList,1);
					OntologyProperty prop = new OntologyProperty(Constants.isContactPerson, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (p.getIsLocatedAt()!=null) {
			String tURN;
			Iterator it = p.getIsLocatedAt().iterator();
			while(it.hasNext()){
				OMVLocation l = (OMVLocation)it.next();
				if (l.getStreet()!=null){
					tURN=l.getStreet();
					tList.clear();
					tList=getPropertiesLocation(l);
					IOntology.addConceptToRegistry(0,tList,12);
					OntologyProperty prop = new OntologyProperty(Constants.isLocatedAt, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (p.getDevelopesOntologyEngineeringTool()!=null) {
			String tURN;
			Iterator it = p.getDevelopesOntologyEngineeringTool().iterator();
			while(it.hasNext()){
				OMVOntologyEngineeringTool oet = (OMVOntologyEngineeringTool)it.next();
				if (oet.getName()!=null){
					tURN=oet.getName();
					tList.clear();
					tList=getPropertiesOntologyEngineeringTool(oet);
					IOntology.addConceptToRegistry(0,tList,2);
					OntologyProperty prop = new OntologyProperty(Constants.developesOntologyEngineeringTool, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (p.getDevelopesOntologyLanguage()!=null) {
			String tURN;
			Iterator it = p.getDevelopesOntologyLanguage().iterator();
			while(it.hasNext()){
				OMVOntologyLanguage ola = (OMVOntologyLanguage)it.next();
				if (ola.getName()!=null){
					tURN=ola.getName();
					tList.clear();
					tList=getPropertiesOntologyLanguage(ola);
					IOntology.addConceptToRegistry(0,tList,8);
					OntologyProperty prop = new OntologyProperty(Constants.developesOntologyLanguage, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (p.getDevelopesOntologySyntax()!=null) {
			String tURN;
			Iterator it = p.getDevelopesOntologySyntax().iterator();
			while(it.hasNext()){
				OMVOntologySyntax osy = (OMVOntologySyntax)it.next();
				if (osy.getName()!=null){
					tURN=osy.getName();
					tList.clear();
					tList=getPropertiesOntologySyntax(osy);
					IOntology.addConceptToRegistry(0,tList,9);
					OntologyProperty prop = new OntologyProperty(Constants.developesOntologySyntax, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (p.getSpecifiesKnowledgeRepresentationParadigm()!=null) {
			String tURN;
			Iterator it = p.getSpecifiesKnowledgeRepresentationParadigm().iterator();
			while(it.hasNext()){
				OMVKnowledgeRepresentationParadigm krp = (OMVKnowledgeRepresentationParadigm)it.next();
				if (krp.getName()!=null){
					tURN=krp.getName();
					tList.clear();
					tList=getPropertiesKRParadigm(krp);
					IOntology.addConceptToRegistry(0,tList,4);
					OntologyProperty prop = new OntologyProperty(Constants.specifiesKnowledgeRepresentationParadigm, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (p.getDefinesOntologyType()!=null) {
			String tURN;
			Iterator it = p.getDefinesOntologyType().iterator();
			while(it.hasNext()){
				OMVOntologyType ot = (OMVOntologyType)it.next();
				if (ot.getName()!=null){
					tURN=ot.getName();
					tList.clear();
					tList=getPropertiesOntologyType(ot);
					IOntology.addConceptToRegistry(0,tList,6);
					OntologyProperty prop = new OntologyProperty(Constants.definesOntologyType, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (p.getDevelopesOntologyEngineeringMethodology()!=null) {
			String tURN;
			Iterator it = p.getDevelopesOntologyEngineeringMethodology().iterator();
			while(it.hasNext()){
				OMVOntologyEngineeringMethodology oem = (OMVOntologyEngineeringMethodology)it.next();
				if (oem.getName()!=null){
					tURN=oem.getName();
					tList.clear();
					tList=getPropertiesOntologyEngineeringMethodology(oem);
					IOntology.addConceptToRegistry(0,tList,3);
					OntologyProperty prop = new OntologyProperty(Constants.developesOntologyEngineeringMethodology, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (p.getSpecifiesLicense()!=null) {
			String tURN;
			Iterator it = p.getSpecifiesLicense().iterator();
			while(it.hasNext()){
				OMVLicenseModel lm = (OMVLicenseModel)it.next();
				if (lm.getName()!=null){
					tURN=lm.getName();
					tList.clear();
					tList=getPropertiesLicense(lm);
					IOntology.addConceptToRegistry(0,tList,11);
					OntologyProperty prop = new OntologyProperty(Constants.specifiesLicense, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (p.getHasAffiliatedParty()!=null) {
			String tURN;
			Iterator it = p.getHasAffiliatedParty().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.hasAffiliatedParty, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.hasAffiliatedParty, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		if (p.getContributesToOntology()!=null) {
			String tURN;
			Iterator it = p.getContributesToOntology().iterator();
			while(it.hasNext()){
				OMVOntology otemp = (OMVOntology)it.next();
				if (otemp.getURI()!=null){
					tURN=otemp.getURI();
					tList.clear();
					tList=getProperties(otemp);
					IOntology.addImportOntologyToRegistry(tList,0);
					OntologyProperty prop = new OntologyProperty(Constants.contributesToOntology, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (p.getCreatesOntology()!=null) {
			String tURN;
			Iterator it = p.getCreatesOntology().iterator();
			while(it.hasNext()){
				OMVOntology otemp = (OMVOntology)it.next();
				if (otemp.getURI()!=null){
					tURN=otemp.getURI();
					tList.clear();
					tList=getProperties(otemp);
					IOntology.addImportOntologyToRegistry(tList,0);
					OntologyProperty prop = new OntologyProperty(Constants.createsOntology, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		return tProperties;
	}
	
	private LinkedList getPropertiesOrganisation(OMVOrganisation o){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (o.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, o.getName());
			tProperties.addFirst(prop);
		}
		if (o.getAcronym()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.acronym, o.getAcronym());
			tProperties.addFirst(prop);
		}
		if (o.getHasContactPerson()!=null) {
			String tURN;
			Iterator it = o.getHasContactPerson().iterator();
			while(it.hasNext()){
				OMVPerson p = (OMVPerson)it.next();
				if ((p.getFirstName()!=null) && (p.getLastName()!=null)){
					tURN=p.getFirstName()+p.getLastName();
					tList.clear();
					tList=getPropertiesPerson(p);
					IOntology.addConceptToRegistry(0,tList,0);
					OntologyProperty prop = new OntologyProperty(Constants.hasContactPerson, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (o.getIsLocatedAt()!=null) {
			String tURN;
			Iterator it = o.getIsLocatedAt().iterator();
			while(it.hasNext()){
				OMVLocation l = (OMVLocation)it.next();
				if (l.getStreet()!=null){
					tURN=l.getStreet();
					tList.clear();
					tList=getPropertiesLocation(l);
					IOntology.addConceptToRegistry(0,tList,12);
					OntologyProperty prop = new OntologyProperty(Constants.isLocatedAt, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (o.getDevelopesOntologyEngineeringTool()!=null) {
			String tURN;
			Iterator it = o.getDevelopesOntologyEngineeringTool().iterator();
			while(it.hasNext()){
				OMVOntologyEngineeringTool oet = (OMVOntologyEngineeringTool)it.next();
				if (oet.getName()!=null){
					tURN=oet.getName();
					tList.clear();
					tList=getPropertiesOntologyEngineeringTool(oet);
					IOntology.addConceptToRegistry(0,tList,2);
					OntologyProperty prop = new OntologyProperty(Constants.developesOntologyEngineeringTool, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (o.getDevelopesOntologyLanguage()!=null) {
			String tURN;
			Iterator it = o.getDevelopesOntologyLanguage().iterator();
			while(it.hasNext()){
				OMVOntologyLanguage ola = (OMVOntologyLanguage)it.next();
				if (ola.getName()!=null){
					tURN=ola.getName();
					tList.clear();
					tList=getPropertiesOntologyLanguage(ola);
					IOntology.addConceptToRegistry(0,tList,8);
					OntologyProperty prop = new OntologyProperty(Constants.developesOntologyLanguage, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (o.getDevelopesOntologySyntax()!=null) {
			String tURN;
			Iterator it = o.getDevelopesOntologySyntax().iterator();
			while(it.hasNext()){
				OMVOntologySyntax osy = (OMVOntologySyntax)it.next();
				if (osy.getName()!=null){
					tURN=osy.getName();
					tList.clear();
					tList=getPropertiesOntologySyntax(osy);
					IOntology.addConceptToRegistry(0,tList,9);
					OntologyProperty prop = new OntologyProperty(Constants.developesOntologySyntax, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (o.getSpecifiesKnowledgeRepresentationParadigm()!=null) {
			String tURN;
			Iterator it = o.getSpecifiesKnowledgeRepresentationParadigm().iterator();
			while(it.hasNext()){
				OMVKnowledgeRepresentationParadigm krp = (OMVKnowledgeRepresentationParadigm)it.next();
				if (krp.getName()!=null){
					tURN=krp.getName();
					tList.clear();
					tList=getPropertiesKRParadigm(krp);
					IOntology.addConceptToRegistry(0,tList,4);
					OntologyProperty prop = new OntologyProperty(Constants.specifiesKnowledgeRepresentationParadigm, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (o.getDefinesOntologyType()!=null) {
			String tURN;
			Iterator it = o.getDefinesOntologyType().iterator();
			while(it.hasNext()){
				OMVOntologyType ot = (OMVOntologyType)it.next();
				if (ot.getName()!=null){
					tURN=ot.getName();
					tList.clear();
					tList=getPropertiesOntologyType(ot);
					IOntology.addConceptToRegistry(0,tList,6);
					OntologyProperty prop = new OntologyProperty(Constants.definesOntologyType, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (o.getDevelopesOntologyEngineeringMethodology()!=null) {
			String tURN;
			Iterator it = o.getDevelopesOntologyEngineeringMethodology().iterator();
			while(it.hasNext()){
				OMVOntologyEngineeringMethodology oem = (OMVOntologyEngineeringMethodology)it.next();
				if (oem.getName()!=null){
					tURN=oem.getName();
					tList.clear();
					tList=getPropertiesOntologyEngineeringMethodology(oem);
					IOntology.addConceptToRegistry(0,tList,3);
					OntologyProperty prop = new OntologyProperty(Constants.developesOntologyEngineeringMethodology, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (o.getSpecifiesLicense()!=null) {
			String tURN;
			Iterator it = o.getSpecifiesLicense().iterator();
			while(it.hasNext()){
				OMVLicenseModel lm = (OMVLicenseModel)it.next();
				if (lm.getName()!=null){
					tURN=lm.getName();
					tList.clear();
					tList=getPropertiesLicense(lm);
					IOntology.addConceptToRegistry(0,tList,11);
					OntologyProperty prop = new OntologyProperty(Constants.specifiesLicense, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (o.getHasAffiliatedParty()!=null) {
			String tURN;
			Iterator it = o.getHasAffiliatedParty().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.hasAffiliatedParty, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.hasAffiliatedParty, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		if (o.getContributesToOntology()!=null) {
			String tURN;
			Iterator it = o.getContributesToOntology().iterator();
			while(it.hasNext()){
				OMVOntology otemp = (OMVOntology)it.next();
				if (otemp.getURI()!=null){
					tURN=otemp.getURI();
					tList.clear();
					tList=getProperties(otemp);
					IOntology.addImportOntologyToRegistry(tList,0);
					OntologyProperty prop = new OntologyProperty(Constants.contributesToOntology, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (o.getCreatesOntology()!=null) {
			String tURN;
			Iterator it = o.getCreatesOntology().iterator();
			while(it.hasNext()){
				OMVOntology otemp = (OMVOntology)it.next();
				if (otemp.getURI()!=null){
					tURN=otemp.getURI();
					tList.clear();
					tList=getProperties(otemp);
					IOntology.addImportOntologyToRegistry(tList,0);
					OntologyProperty prop = new OntologyProperty(Constants.createsOntology, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		return tProperties;
	}
	
	private LinkedList getPropertiesOntologyEngineeringTool(OMVOntologyEngineeringTool oet){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (oet.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, oet.getName());
			tProperties.addFirst(prop);
		}
		if (oet.getAcronym()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.acronym, oet.getAcronym());
			tProperties.addFirst(prop);
		}
		if (oet.getDescription()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.description, oet.getDescription());
			tProperties.addFirst(prop);
		}
		if (oet.getDocumentation()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.documentation, oet.getDocumentation());
			tProperties.addFirst(prop);
		}
		if (oet.getDevelopedBy()!=null) {
			String tURN;
			Iterator it = oet.getDevelopedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.developedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.developedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		return tProperties;
	}
	
	private LinkedList getPropertiesOntologyEngineeringMethodology(OMVOntologyEngineeringMethodology oem){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (oem.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, oem.getName());
			tProperties.addFirst(prop);
		}
		if (oem.getAcronym()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.acronym, oem.getAcronym());
			tProperties.addFirst(prop);
		}
		if (oem.getDescription()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.description, oem.getDescription());
			tProperties.addFirst(prop);
		}
		if (oem.getDocumentation()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.documentation, oem.getDocumentation());
			tProperties.addFirst(prop);
		}
		if (oem.getDevelopedBy()!=null) {
			String tURN;
			Iterator it = oem.getDevelopedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.developedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.developedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		return tProperties;
	}
	
	private LinkedList getPropertiesKRParadigm(OMVKnowledgeRepresentationParadigm krp){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (krp.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, krp.getName());
			tProperties.addFirst(prop);
		}
		if (krp.getAcronym()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.acronym, krp.getAcronym());
			tProperties.addFirst(prop);
		}
		if (krp.getDescription()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.description, krp.getDescription());
			tProperties.addFirst(prop);
		}
		if (krp.getDocumentation()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.documentation, krp.getDocumentation());
			tProperties.addFirst(prop);
		}
		if (krp.getSpecifiedBy()!=null) {
			String tURN;
			Iterator it = krp.getSpecifiedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.specifiedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.specifiedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		return tProperties;
	}
	
	private LinkedList getPropertiesOntologyDomain(OMVOntologyDomain od){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (od.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, od.getName());
			tProperties.addFirst(prop);
		}
		if (od.getURI()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.URI, od.getURI());
			tProperties.addFirst(prop);
		}
		if (od.getIsSubDomainOf().size()>0) {
			String tURN;
			Iterator it = od.getIsSubDomainOf().iterator();
			while(it.hasNext()){
				OMVOntologyDomain odSub = (OMVOntologyDomain)it.next();
				if (odSub.getURI()!=null){
					tURN=odSub.getURI();
					if(!tURN.contains("://")){
						odSub.setURI(Constants.TopicsURI+tURN);  //Add namespace if not present
						tURN=odSub.getURI();
					}
					tList.clear();
					tList=getPropertiesOntologyDomain(odSub);
					IOntology.addConceptToRegistry(0,tList,5);
					OntologyProperty prop = new OntologyProperty(Constants.isSubDomainOf, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		return tProperties;
	}
	
	private LinkedList getPropertiesOntologyType(OMVOntologyType ot){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (ot.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, ot.getName());
			tProperties.addFirst(prop);
		}
		if (ot.getAcronym()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.acronym, ot.getAcronym());
			tProperties.addFirst(prop);
		}
		if (ot.getDescription()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.description, ot.getDescription());
			tProperties.addFirst(prop);
		}
		if (ot.getDocumentation()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.documentation, ot.getDocumentation());
			tProperties.addFirst(prop);
		}
		if (ot.getDefinedBy()!=null) {
			String tURN;
			Iterator it = ot.getDefinedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.specifiedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.specifiedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		return tProperties;
	}
	
	private LinkedList getPropertiesOntologyTask(OMVOntologyTask ota){
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (ota.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, ota.getName());
			tProperties.addFirst(prop);
		}
		if (ota.getAcronym()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.acronym, ota.getAcronym());
			tProperties.addFirst(prop);
		}
		if (ota.getDescription()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.description, ota.getDescription());
			tProperties.addFirst(prop);
		}
		if (ota.getDocumentation()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.documentation, ota.getDocumentation());
			tProperties.addFirst(prop);
		}
		return tProperties;
	}
	
	private LinkedList getPropertiesOntologyLanguage(OMVOntologyLanguage ola){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (ola.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, ola.getName());
			tProperties.addFirst(prop);
		}
		if (ola.getAcronym()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.acronym, ola.getAcronym());
			tProperties.addFirst(prop);
		}
		if (ola.getDescription()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.description, ola.getDescription());
			tProperties.addFirst(prop);
		}
		if (ola.getDocumentation()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.documentation, ola.getDocumentation());
			tProperties.addFirst(prop);
		}
		if (ola.getDevelopedBy()!=null) {
			String tURN;
			Iterator it = ola.getDevelopedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.developedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.developedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		if (ola.getSupportsRepresentationParadigm().size()>0) {
			String tURN;
			Iterator it = ola.getSupportsRepresentationParadigm().iterator();
			while(it.hasNext()){
				OMVKnowledgeRepresentationParadigm krp = (OMVKnowledgeRepresentationParadigm)it.next();
				if (krp.getName()!=null){
					tURN=krp.getName();
					tList.clear();
					tList=getPropertiesKRParadigm(krp);
					IOntology.addConceptToRegistry(0,tList,4);
					OntologyProperty prop = new OntologyProperty(Constants.supportsRepresentationParadigm, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (ola.getHasSyntax().size()>0) {
			String tURN;
			Iterator it = ola.getHasSyntax().iterator();
			while(it.hasNext()){
				OMVOntologySyntax osy = (OMVOntologySyntax)it.next();
				if (osy.getName()!=null){
					tURN=osy.getName();
					tList.clear();
					tList=getPropertiesOntologySyntax(osy);
					IOntology.addConceptToRegistry(0,tList,9);
					OntologyProperty prop = new OntologyProperty(Constants.hasSyntax, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		return tProperties;
	}
	
	private LinkedList getPropertiesOntologySyntax(OMVOntologySyntax osy){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (osy.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, osy.getName());
			tProperties.addFirst(prop);
		}
		if (osy.getAcronym()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.acronym, osy.getAcronym());
			tProperties.addFirst(prop);
		}
		if (osy.getDescription()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.description, osy.getDescription());
			tProperties.addFirst(prop);
		}
		if (osy.getDocumentation()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.documentation, osy.getDocumentation());
			tProperties.addFirst(prop);
		}
		if (osy.getDevelopedBy()!=null) {
			String tURN;
			Iterator it = osy.getDevelopedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.developedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.developedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		return tProperties;
	}
	
	private LinkedList getPropertiesFormalityLevel(OMVFormalityLevel fl){
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (fl.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, fl.getName());
			tProperties.addFirst(prop);
		}
		if (fl.getDescription()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.description, fl.getDescription());
			tProperties.addFirst(prop);
		}
		return tProperties;
	}
	
	private LinkedList getPropertiesLicense(OMVLicenseModel lm){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (lm.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, lm.getName());
			tProperties.addFirst(prop);
		}
		if (lm.getAcronym()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.acronym, lm.getAcronym());
			tProperties.addFirst(prop);
		}
		if (lm.getDescription()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.description, lm.getDescription());
			tProperties.addFirst(prop);
		}
		if (lm.getDocumentation()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.documentation, lm.getDocumentation());
			tProperties.addFirst(prop);
		}
		if (lm.getSpecifiedBy()!=null) {
			String tURN;
			Iterator it = lm.getSpecifiedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.specifiedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.specifiedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		return tProperties;
	}
	
	private LinkedList getPropertiesLocation(OMVLocation l){
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (l.getState()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.state, l.getState());
			tProperties.addFirst(prop);
		}
		if (l.getCountry()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.country, l.getCountry());
			tProperties.addFirst(prop);
		}
		if (l.getCity()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.city, l.getCity());
			tProperties.addFirst(prop);
		}
		if (l.getStreet()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.street, l.getStreet());
			tProperties.addFirst(prop);
		}
		
		return tProperties;
	}
	
	private LinkedList getProperties (OMVOntology o){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> ontoProperties = new LinkedList<OntologyProperty>();
		if (o.getURI()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.URI, o.getURI());
			ontoProperties.addFirst(prop);
		}
		if (o.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, o.getName());
			ontoProperties.addFirst(prop);
		}
		if (o.getAcronym()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.acronym, o.getAcronym());
			ontoProperties.addFirst(prop);
		}
		if (o.getDescription()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.description, o.getDescription());
			ontoProperties.addFirst(prop);
		}
		if (o.getDocumentation()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.documentation, o.getDocumentation());
			ontoProperties.addFirst(prop);
		}
		if (o.getKeywords()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.keywords, o.getKeywords());
			ontoProperties.addFirst(prop);
		}
		if (o.getStatus()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.status, o.getStatus());
			ontoProperties.addFirst(prop);
		}
		if (o.getCreationDate()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.creationDate, o.getCreationDate());
			ontoProperties.addFirst(prop);
		}
		if (o.getModificationDate()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.modificationDate, o.getModificationDate());
			ontoProperties.addFirst(prop);
		}
		if (o.getHasContributor().size()>0) {
			String tURN;
			Iterator it = o.getHasContributor().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.hasContributor, tURN);
						ontoProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.hasContributor, tURN);
						ontoProperties.addFirst(prop);
					}
				}
			}
		}
		if (o.getHasCreator().size()>0) {
			String tURN;
			Iterator it = o.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						ontoProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						ontoProperties.addFirst(prop);
					}
				}
			}
		}
		if (o.getUsedOntologyEngineeringTool().size()>0) {
			String tURN;
			Iterator it = o.getUsedOntologyEngineeringTool().iterator();
			while(it.hasNext()){
				OMVOntologyEngineeringTool oet = (OMVOntologyEngineeringTool)it.next();
				if (oet.getName()!=null){
					tURN=oet.getName();
					tList.clear();
					tList=getPropertiesOntologyEngineeringTool(oet);
					IOntology.addConceptToRegistry(0,tList,2);
					OntologyProperty prop = new OntologyProperty(Constants.usedOntologyEngineeringTool, tURN);
					ontoProperties.addFirst(prop);
				}
			}
		}
		if (o.getUsedOntologyEngineeringMethodology().size()>0) {
			String tURN;
			Iterator it = o.getUsedOntologyEngineeringMethodology().iterator();
			while(it.hasNext()){
				OMVOntologyEngineeringMethodology oem = (OMVOntologyEngineeringMethodology)it.next();
				if (oem.getName()!=null){
					tURN=oem.getName();
					tList.clear();
					tList=getPropertiesOntologyEngineeringMethodology(oem);
					IOntology.addConceptToRegistry(0,tList,3);
					OntologyProperty prop = new OntologyProperty(Constants.usedOntologyEngineeringMethodology, tURN);
					ontoProperties.addFirst(prop);
				}
			}
		}
		if (o.getUsedKnowledgeRepresentationParadigm().size()>0) {
			String tURN;
			Iterator it = o.getUsedKnowledgeRepresentationParadigm().iterator();
			while(it.hasNext()){
				OMVKnowledgeRepresentationParadigm krp = (OMVKnowledgeRepresentationParadigm)it.next();
				if (krp.getName()!=null){
					tURN=krp.getName();
					tList.clear();
					tList=getPropertiesKRParadigm(krp);
					IOntology.addConceptToRegistry(0,tList,4);
					OntologyProperty prop = new OntologyProperty(Constants.usedKnowledgeRepresentationParadigm, tURN);
					ontoProperties.addFirst(prop);
				}

			}
		}
		if (o.getHasDomain().size()>0) {
			String tURN;
			Iterator it = o.getHasDomain().iterator();
			while(it.hasNext()){
				OMVOntologyDomain od = (OMVOntologyDomain)it.next();
				if (od.getURI()!=null){
					tURN=od.getURI();
					if(!tURN.contains("://")){
						od.setURI(Constants.TopicsURI+tURN);  //Add namespace if not present
						tURN=od.getURI();
					}
					tList.clear();
					tList=getPropertiesOntologyDomain(od);
					IOntology.addConceptToRegistry(0,tList,5);
					OntologyProperty prop = new OntologyProperty(Constants.hasDomain, tURN);
					ontoProperties.addFirst(prop);
				}
			}
		}
		if (o.getIsOfType()!=null) {
			String tURN;
			OMVOntologyType ot = o.getIsOfType();
			if (ot.getName()!=null){
				tURN=ot.getName();
				tList.clear();
				tList=getPropertiesOntologyType(ot);
				IOntology.addConceptToRegistry(0,tList,6);
				OntologyProperty prop = new OntologyProperty(Constants.isOfType, tURN);
				ontoProperties.addFirst(prop);
			}
		}
		if (o.getNaturalLanguage()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.naturalLanguage, o.getNaturalLanguage());
			ontoProperties.addFirst(prop);
		}
		if (o.getDesignedForOntologyTask().size()>0) {
			String tURN;
			Iterator it = o.getDesignedForOntologyTask().iterator();
			while(it.hasNext()){
				OMVOntologyTask ota = (OMVOntologyTask)it.next();
				if (ota.getName()!=null){
					tURN=ota.getName();
					tList.clear();
					tList=getPropertiesOntologyTask(ota);
					IOntology.addConceptToRegistry(0,tList,7);
					OntologyProperty prop = new OntologyProperty(Constants.designedForOntologyTask, tURN);
					ontoProperties.addFirst(prop);
				}
			}
		}
		if (o.getHasOntologyLanguage()!=null) {
			String tURN;
			OMVOntologyLanguage ole = o.getHasOntologyLanguage();
			if (ole.getName()!=null){
				tURN=ole.getName();
				tList.clear();
				tList=getPropertiesOntologyLanguage(ole);
				IOntology.addConceptToRegistry(0,tList,8);
				OntologyProperty prop = new OntologyProperty(Constants.hasOntologyLanguage, tURN);
				ontoProperties.addFirst(prop);
			}
		}
		if (o.getHasOntologySyntax()!=null) {
			String tURN;
			OMVOntologySyntax osy = o.getHasOntologySyntax();
			if (osy.getName()!=null){
				tURN=osy.getName();
				tList.clear();
				tList=getPropertiesOntologySyntax(osy);
				IOntology.addConceptToRegistry(0,tList,9);
				OntologyProperty prop = new OntologyProperty(Constants.hasOntologySyntax, tURN);
				ontoProperties.addFirst(prop);
			}
			
		}
		if (o.getHasFormalityLevel()!=null) {
			String tURN;
			OMVFormalityLevel fl = o.getHasFormalityLevel();
			if (fl.getName()!=null){
				tURN=fl.getName();
				tList.clear();
				tList=getPropertiesFormalityLevel(fl);
				IOntology.addConceptToRegistry(0,tList,10);
				OntologyProperty prop = new OntologyProperty(Constants.hasFormalityLevel, tURN);
				ontoProperties.addFirst(prop);
			}
		}
		if (o.getResourceLocator()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.resourceLocator, o.getResourceLocator());
			ontoProperties.addFirst(prop);
		}
		if (o.getVersion()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.version, o.getVersion());
			ontoProperties.addFirst(prop);
		}
		if (o.getHasLicense()!=null) {
			String tURN;
			OMVLicenseModel lm = o.getHasLicense();
			if (lm.getName()!=null){
				tURN=lm.getName();
				tList.clear();
				tList=getPropertiesLicense(lm);
				IOntology.addConceptToRegistry(0,tList,11);
				OntologyProperty prop = new OntologyProperty(Constants.hasLicense, tURN);
				ontoProperties.addFirst(prop);
			}
		}
		if (o.getUseImports().size()>0) {
			String tURN;
			Iterator it = o.getUseImports().iterator();
			while(it.hasNext()){
				OMVOntology otemp = (OMVOntology)it.next();
				if (otemp.getURI()!=null){
					tURN=otemp.getURI();
					tList.clear();
					tList=getProperties(otemp);
					IOntology.addImportOntologyToRegistry(tList,0);
					OntologyProperty prop = new OntologyProperty(Constants.useImports, tURN);
					ontoProperties.addFirst(prop);
				}
			}
		}
		if (o.getHasPriorVersion()!=null) {
			String tURN;
			OMVOntology otemp = o.getHasPriorVersion();
			if (otemp.getURI()!=null){
				tURN=otemp.getURI();
				tList.clear();
				tList=getProperties(otemp);
				IOntology.addImportOntologyToRegistry(tList,0);
				OntologyProperty prop = new OntologyProperty(Constants.hasPriorVersion, tURN);
				ontoProperties.addFirst(prop);
			}
		}
		if (o.getIsBackwardCompatibleWith().size()>0) {
			String tURN;
			Iterator it = o.getIsBackwardCompatibleWith().iterator();
			while(it.hasNext()){
				OMVOntology otemp = (OMVOntology)it.next();
				if (otemp.getURI()!=null){
					tURN=otemp.getURI();
					tList.clear();
					tList=getProperties(otemp);
					IOntology.addImportOntologyToRegistry(tList,0);
					OntologyProperty prop = new OntologyProperty(Constants.isBackwardCompatibleWith, tURN);
					ontoProperties.addFirst(prop);
				}
			}
		}
		if (o.getIsIncompatibleWith().size()>0) {
			String tURN;
			Iterator it = o.getIsIncompatibleWith().iterator();
			while(it.hasNext()){
				OMVOntology otemp = (OMVOntology)it.next();
				if (otemp.getURI()!=null){
					tURN=otemp.getURI();
					tList.clear();
					tList=getProperties(otemp);
					IOntology.addImportOntologyToRegistry(tList,0);
					OntologyProperty prop = new OntologyProperty(Constants.isIncompatibleWith, tURN);
					ontoProperties.addFirst(prop);
				}
			}
		}
		if (o.getNumberOfClasses()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.numberOfClasses, o.getNumberOfClasses().toString());
			ontoProperties.addFirst(prop);
		}
		if (o.getNumberOfProperties()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.numberOfProperties, o.getNumberOfProperties().toString());
			ontoProperties.addFirst(prop);
		}
		if (o.getNumberOfIndividuals()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.numberOfIndividuals, o.getNumberOfIndividuals().toString());
			ontoProperties.addFirst(prop);
		}
		if (o.getNumberOfAxioms()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.numberOfAxioms, o.getNumberOfAxioms().toString());
			ontoProperties.addFirst(prop);
		}
		
		if (o.getNotes()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.notes, o.getNotes());
			ontoProperties.addFirst(prop);
		}
		if (o.getKeyClasses()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.keyClasses, o.getKeyClasses());
			ontoProperties.addFirst(prop);
		}
		if (o.getKnownUsage()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.knownUsage, o.getKnownUsage());
			ontoProperties.addFirst(prop);
		}
		if (o.getExpressiveness()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.expressiveness, o.getExpressiveness());
			ontoProperties.addFirst(prop);
		}
		if (o.getIsConsistentAccordingToReasoner()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.isConsistentAccordingToReasoner, o.getIsConsistentAccordingToReasoner().toString());
			ontoProperties.addFirst(prop);
		}
		if (o.getContainsTBox()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.containsTBox, o.getContainsTBox().toString());
			ontoProperties.addFirst(prop);
		}
		if (o.getContainsRBox()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.containsRBox, o.getContainsRBox().toString());
			ontoProperties.addFirst(prop);
		}
		if (o.getContainsABox()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.containsABox, o.getContainsABox().toString());
			ontoProperties.addFirst(prop);
		}
		if (o.getEndorsedBy().size()>0) {
			String tURN;
			Iterator it = o.getEndorsedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.endorsedBy, tURN);
						ontoProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.endorsedBy, tURN);
						ontoProperties.addFirst(prop);
					}
				}
			}
		}
		return ontoProperties;
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
	    		Map<String, OMVPeer> peerReplyMap=processPeerIndividual(peerIndiv, "peer");
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
	
	private Map<String,OMVPeer> processPeerIndividual(Individual peerIndiv, String whichClass){
	  Map<String, OMVPeer> OMVPeerMap = new HashMap<String, OMVPeer>();
	  OMVPeer peerReply=null;
	  String peerNameID="";
	  if (!PeerOnProcess.contains(peerIndiv)){
		PeerOnProcess.add(peerIndiv);
		try{
			Map dataPropertyMap = peerIndiv.getDataPropertyValues(ontologySearch);
			Map objectPropertyMap = peerIndiv.getObjectPropertyValues(ontologySearch);
			if ((dataPropertyMap.size()+objectPropertyMap.size())>0){		
				if (whichClass.equalsIgnoreCase("peer")) {
					peerReply = new OMVPeer();
					peerNameID=peerIndiv.getURI();
				}
				Collection keySet = dataPropertyMap.keySet();
				Iterator keys = keySet.iterator();
				while(keys.hasNext()){
					String keyStr = keys.next().toString();
					DataProperty property = KAON2Manager.factory().dataProperty(keyStr);
					String	propertyValue = org.neon_toolkit.registry.util.Utilities.getString(peerIndiv.getDataPropertyValue(ontologySearch,property));
					if (whichClass.equalsIgnoreCase("peer")) peerReply.append(createOMVPeer(property.getURI(),propertyValue));
				}
				keySet = objectPropertyMap.keySet();
				Iterator okeys = keySet.iterator();
				while(okeys.hasNext()){
					String keyStr = okeys.next().toString();
					ObjectProperty property = KAON2Manager.factory().objectProperty(keyStr);
					Collection propertyCol= new LinkedList();
					propertyCol = (Collection)objectPropertyMap.get(property);
					if(propertyCol==null)System.err.println("propertyCol is null");
					Iterator itInt = propertyCol.iterator();
					while(itInt.hasNext()){
						Entity propertyValue =(Entity) itInt.next();							
						if (whichClass.equalsIgnoreCase("peer")) peerReply.append(createOMVPeer(property.getURI(),propertyValue.getURI()));	
					}	
				}
			}
		}catch(Exception e){
			System.out.println(e.toString()+" Search Problem in processPeerIndividual");
		}
		PeerOnProcess.remove(peerIndiv);
	  }
	  else {
		  if (whichClass.equalsIgnoreCase("peer")) {
			  peerReply = new OMVPeer();
			  peerNameID=peerIndiv.getURI();
		  }
	  }
	  OMVPeerMap.put(peerNameID,peerReply);
	  return OMVPeerMap;
	}
	
	private OMVPeer createOMVPeer(String URI, String value){
		OMVPeer peerReply=new OMVPeer();
		if (URI.equalsIgnoreCase(Constants.POMVURI+Constants.GUID)) {peerReply.setGUID(value); return peerReply;}
		if (URI.equalsIgnoreCase(Constants.POMVURI+Constants.IPAdress)) {peerReply.setIPAdress(value); return peerReply;}
		if (URI.equalsIgnoreCase(Constants.POMVURI+Constants.localPeer)) 
			{
				if ((value.toString().equalsIgnoreCase("\"true\"^^<xsd:boolean>")))
						peerReply.setLocalPeer(true);
				else
						peerReply.setLocalPeer(false);
				return peerReply;
			}
		if (URI.equalsIgnoreCase(Constants.POMVURI+Constants.peerType)) {peerReply.setPeerType(value); return peerReply;}
		if (URI.equalsIgnoreCase(Constants.POMVURI+Constants.contextOntology)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntology oReferencesReply=(OMVOntology)processIndividual(oIndividual, "ontology"); //oReferences
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			peerReply.setContextOntology(oReferencesReply);
			oReferencesReply = null;
			return peerReply;
		}
		if (URI.equalsIgnoreCase(Constants.POMVURI+Constants.provideOntology)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntology oReferencesReply=(OMVOntology)processIndividual(oIndividual, "ontology");  //oReferences
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			peerReply.addProvideOntology(oReferencesReply);
			oReferencesReply = null;
			return peerReply;
		}
		if (URI.equalsIgnoreCase(Constants.POMVURI+Constants.provideMapping)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVMapping oReferencesReply=(OMVMapping)processMappingIndividual(oIndividual, "mapping");  //oReferences
			if (oReferencesReply==null) {
				oReferencesReply = new OMVMapping();
				oReferencesReply.setURI(value);
			}
			peerReply.addProvideMapping(oReferencesReply);
			oReferencesReply = null;
			return peerReply;
		}
		if (URI.equalsIgnoreCase(Constants.POMVURI+Constants.acquaintedWith)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			Map<String, OMVPeer> peerReplyMap=processPeerIndividual(oIndividual, "peer"); 
			OMVPeer peerReplyAW=(OMVPeer)peerReplyMap.values().iterator().next();
    		String peerNameIDAW=(String)peerReplyMap.keySet().iterator().next();
			if (peerReplyAW!=null && !peerNameIDAW.equalsIgnoreCase("")){
				peerReply.addAcquaintedWith(peerReplyAW);
				peerReplyAW=null;
				peerNameIDAW="";
			}
			return peerReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasExpertise)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyDomain oDomainReply=(OMVOntologyDomain)processIndividual(oIndividual, "oDomain");
			if (oDomainReply==null) {
				oDomainReply = new OMVOntologyDomain();
				oDomainReply.setURI(value);
			}
			peerReply.addHasExpertise(oDomainReply);
			oDomainReply = null;
			return peerReply;
		}
		return peerReply;
	}
	
	private OMVPerson copyParty2Person(OMVParty paReply,OMVPerson peReply){
		OMVParty partyReply = paReply;
		OMVPerson personReply = peReply;
		Iterator it = partyReply.getIsLocatedAt().iterator();
		while(it.hasNext()){
			OMVLocation t = (OMVLocation)it.next();
			if (t!=null){
				personReply.addIsLocatedAt(t);
			}
		}
		it = partyReply.getDevelopesOntologyEngineeringTool().iterator();
		while(it.hasNext()){
			OMVOntologyEngineeringTool t = (OMVOntologyEngineeringTool)it.next();
			if (t!=null){
				personReply.addDevelopesOntologyEngineeringTool(t);
			}
		}
		it = partyReply.getDevelopesOntologyLanguage().iterator();
		while(it.hasNext()){
			OMVOntologyLanguage t = (OMVOntologyLanguage)it.next();
			if (t!=null){
				personReply.addDevelopesOntologyLanguage(t);
			}
		}
		it = partyReply.getDevelopesOntologySyntax().iterator();
		while(it.hasNext()){
			OMVOntologySyntax t = (OMVOntologySyntax)it.next();
			if (t!=null){
				personReply.addDevelopesOntologySyntax(t);
			}
		}
		it = partyReply.getSpecifiesKnowledgeRepresentationParadigm().iterator();
		while(it.hasNext()){
			OMVKnowledgeRepresentationParadigm t = (OMVKnowledgeRepresentationParadigm)it.next();
			if (t!=null){
				personReply.addSpecifiesKnowledgeRepresentationParadigm(t);
			}
		}
		it = partyReply.getDefinesOntologyType().iterator();
		while(it.hasNext()){
			OMVOntologyType t = (OMVOntologyType)it.next();
			if (t!=null){
				personReply.addDefinesOntologyType(t);
			}
		}
		it = partyReply.getSpecifiesLicense().iterator();
		while(it.hasNext()){
			OMVLicenseModel t = (OMVLicenseModel)it.next();
			if (t!=null){
				personReply.addSpecifiesLicense(t);
			}
		}
		it = partyReply.getHasAffiliatedParty().iterator();
		while(it.hasNext()){
			OMVParty t = (OMVParty)it.next();
			if (t!=null){
				personReply.addHasAffiliatedParty(t);
			}
		}
		it = partyReply.getContributesToOntology().iterator();
		while(it.hasNext()){
			OMVOntology t = (OMVOntology)it.next();
			if (t!=null){
				personReply.addContributesToOntology(t);
			}
		}
		it = partyReply.getCreatesOntology().iterator();
		while(it.hasNext()){
			OMVOntology t = (OMVOntology)it.next();
			if (t!=null){
				personReply.addCreatesOntology(t);
			}
		}
		return personReply;
	}

	private OMVOrganisation copyParty2Organisation(OMVParty paReply, OMVOrganisation oReply){
		OMVParty partyReply=paReply;
		OMVOrganisation organisationReply=oReply;
		Iterator it = partyReply.getIsLocatedAt().iterator();
		while(it.hasNext()){
			OMVLocation t = (OMVLocation)it.next();
			if (t!=null){
				organisationReply.addIsLocatedAt(t);
			}
		}
		it = partyReply.getDevelopesOntologyEngineeringTool().iterator();
		while(it.hasNext()){
			OMVOntologyEngineeringTool t = (OMVOntologyEngineeringTool)it.next();
			if (t!=null){
				organisationReply.addDevelopesOntologyEngineeringTool(t);
			}
		}
		it = partyReply.getDevelopesOntologyLanguage().iterator();
		while(it.hasNext()){
			OMVOntologyLanguage t = (OMVOntologyLanguage)it.next();
			if (t!=null){
				organisationReply.addDevelopesOntologyLanguage(t);
			}
		}
		it = partyReply.getDevelopesOntologySyntax().iterator();
		while(it.hasNext()){
			OMVOntologySyntax t = (OMVOntologySyntax)it.next();
			if (t!=null){
				organisationReply.addDevelopesOntologySyntax(t);
			}
		}
		it = partyReply.getSpecifiesKnowledgeRepresentationParadigm().iterator();
		while(it.hasNext()){
			OMVKnowledgeRepresentationParadigm t = (OMVKnowledgeRepresentationParadigm)it.next();
			if (t!=null){
				organisationReply.addSpecifiesKnowledgeRepresentationParadigm(t);
			}
		}
		it = partyReply.getDefinesOntologyType().iterator();
		while(it.hasNext()){
			OMVOntologyType t = (OMVOntologyType)it.next();
			if (t!=null){
				organisationReply.addDefinesOntologyType(t);
			}
		}
		it = partyReply.getSpecifiesLicense().iterator();
		while(it.hasNext()){
			OMVLicenseModel t = (OMVLicenseModel)it.next();
			if (t!=null){
				organisationReply.addSpecifiesLicense(t);
			}
		}
		it = partyReply.getHasAffiliatedParty().iterator();
		while(it.hasNext()){
			OMVParty t = (OMVParty)it.next();
			if (t!=null){
				organisationReply.addHasAffiliatedParty(t);
			}
		}
		it = partyReply.getContributesToOntology().iterator();
		while(it.hasNext()){
			OMVOntology t = (OMVOntology)it.next();
			if (t!=null){
				organisationReply.addContributesToOntology(t);
			}
		}
		it = partyReply.getCreatesOntology().iterator();
		while(it.hasNext()){
			OMVOntology t = (OMVOntology)it.next();
			if (t!=null){
				organisationReply.addCreatesOntology(t);
			}
		}
		return organisationReply;
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
				OMVMapping mainMappingReply=(OMVMapping)processMappingIndividual(mIndividual, "mapping");
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

	private Object processMappingIndividual(Individual ontoIndiv, String whichClass){
		 OMVMapping mainMappingReply=null;
		 OMVMappingProperty mPropertyReply=null;
		 OMVMappingMethod mMethodReply=null;
		 OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mAlgorithmReply=null;
		 OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod mManualReply=null;
		 OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mFilterReply=null;
		 OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mParallelReply=null;
		 OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mSequenceReply =null;
		 OMVMappingEvidence mEvidenceReply =null;
		 OMVMappingEvidence.OMVMappingArgument mArgumentReply =null;
		 OMVMappingEvidence.OMVMappingCertificate mCertificateReply =null;
		 OMVMappingEvidence.OMVMappingProof mProofReply =null;
		 
		 
		 if (!mappingOnProcess.contains(ontoIndiv)){
		  mappingOnProcess.add(ontoIndiv);  
		  try{
			Map dataPropertyMap = ontoIndiv.getDataPropertyValues(ontologySearch);
			Map objectPropertyMap = ontoIndiv.getObjectPropertyValues(ontologySearch);
			if ((dataPropertyMap.size()+objectPropertyMap.size())>0){
				
				if (whichClass.equalsIgnoreCase("mapping")) mainMappingReply = new OMVMapping();
				else if (whichClass.equalsIgnoreCase("mappingProperty")) mPropertyReply = new OMVMappingProperty();
				else if (whichClass.equalsIgnoreCase("mappingMethod")) mMethodReply = new OMVMappingMethod();
				else if (whichClass.equalsIgnoreCase("mappingAlgorithm")) mAlgorithmReply = new OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm();
				else if (whichClass.equalsIgnoreCase("mappingManualMethod")) mManualReply = new OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod();
				else if (whichClass.equalsIgnoreCase("mappingFilter")) mFilterReply = new OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter();
				else if (whichClass.equalsIgnoreCase("mappingParallel")) mParallelReply = new OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel();
				else if (whichClass.equalsIgnoreCase("mappingSequence")) mSequenceReply = new OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence();
				else if (whichClass.equalsIgnoreCase("mappingEvidence")) mEvidenceReply = new OMVMappingEvidence();
				else if (whichClass.equalsIgnoreCase("mappingArgument")) mArgumentReply = new OMVMappingEvidence.OMVMappingArgument();
				else if (whichClass.equalsIgnoreCase("mappingCertificate")) mCertificateReply = new OMVMappingEvidence.OMVMappingCertificate();
				else if (whichClass.equalsIgnoreCase("mappingProof")) mProofReply = new OMVMappingEvidence.OMVMappingProof();
				
				Collection keySet = dataPropertyMap.keySet();
				Iterator keys = keySet.iterator();
				while(keys.hasNext()){
					String keyStr = keys.next().toString();
					DataProperty property = KAON2Manager.factory().dataProperty(keyStr);
					String	propertyValue = org.neon_toolkit.registry.util.Utilities.getString(ontoIndiv.getDataPropertyValue(ontologySearch,property));
						
					if (whichClass.equalsIgnoreCase("mapping")) mainMappingReply.append(createOMVMapping(property.getURI(),propertyValue));
					else if (whichClass.equalsIgnoreCase("mappingProperty")) mPropertyReply.append(createOMVMappingProperty(property.getURI(),propertyValue));
					else if (whichClass.equalsIgnoreCase("mappingMethod")) mMethodReply.append(createOMVMappingMethod(property.getURI(),propertyValue));
					else if (whichClass.equalsIgnoreCase("mappingAlgorithm")) mAlgorithmReply.append(createOMVMappingAlgorithm(property.getURI(),propertyValue));
					else if (whichClass.equalsIgnoreCase("mappingManualMethod")) mManualReply.setID(ontoIndiv.getURI());
					else if (whichClass.equalsIgnoreCase("mappingFilter")) mFilterReply.append(createOMVMappingFilter(property.getURI(),propertyValue));
					else if (whichClass.equalsIgnoreCase("mappingParallel"))mParallelReply.append(createOMVMappingParallel(property.getURI(),propertyValue));
					else if (whichClass.equalsIgnoreCase("mappingSequence")) mSequenceReply.append(createOMVMappingSequence(property.getURI(),propertyValue));
					else if (whichClass.equalsIgnoreCase("mappingEvidence")) mEvidenceReply.setID(ontoIndiv.getURI());
					else if (whichClass.equalsIgnoreCase("mappingArgument")) mArgumentReply.setID(ontoIndiv.getURI());
					else if (whichClass.equalsIgnoreCase("mappingCertificate")) mCertificateReply.setID(ontoIndiv.getURI());
					else if (whichClass.equalsIgnoreCase("mappingProof")) mProofReply.setID(ontoIndiv.getURI());
					
					//System.out.println("The property "+property.getURI()+" has value: "+propertyValue);
				}
				keySet = objectPropertyMap.keySet();
				Iterator okeys = keySet.iterator();
				while(okeys.hasNext()){
					String keyStr = okeys.next().toString();
					ObjectProperty property = KAON2Manager.factory().objectProperty(keyStr);
					Collection propertyCol= new LinkedList();
					propertyCol = (Collection)objectPropertyMap.get(property);
					if(propertyCol==null)System.err.println("propertyCol is null");
					Iterator itInt = propertyCol.iterator();
					while(itInt.hasNext()){
						Entity propertyValue =(Entity) itInt.next();
							
						if (whichClass.equalsIgnoreCase("mapping")) mainMappingReply.append(createOMVMapping(property.getURI(),propertyValue.getURI()));
						else if (whichClass.equalsIgnoreCase("mappingProperty")) mPropertyReply.append(createOMVMappingProperty(property.getURI(),propertyValue.getURI()));
						else if (whichClass.equalsIgnoreCase("mappingMethod")) mMethodReply.append(createOMVMappingMethod(property.getURI(),propertyValue.getURI()));
						else if (whichClass.equalsIgnoreCase("mappingAlgorithm")) mAlgorithmReply.append(createOMVMappingAlgorithm(property.getURI(),propertyValue.getURI()));
						else if (whichClass.equalsIgnoreCase("mappingManualMethod")) mManualReply.setID(ontoIndiv.getURI());
						else if (whichClass.equalsIgnoreCase("mappingFilter")) mFilterReply.append(createOMVMappingFilter(property.getURI(),propertyValue.getURI()));
						else if (whichClass.equalsIgnoreCase("mappingParallel")) mParallelReply.append(createOMVMappingParallel(property.getURI(),propertyValue.getURI()));
						else if (whichClass.equalsIgnoreCase("mappingSequence")) mSequenceReply.append(createOMVMappingSequence(property.getURI(),propertyValue.getURI()));
						else if (whichClass.equalsIgnoreCase("mappingEvidence")) mEvidenceReply.setID(ontoIndiv.getURI());
						else if (whichClass.equalsIgnoreCase("mappingArgument")) mArgumentReply.setID(ontoIndiv.getURI());
						else if (whichClass.equalsIgnoreCase("mappingCertificate")) mCertificateReply.setID(ontoIndiv.getURI());
						else if (whichClass.equalsIgnoreCase("mappingProof")) mProofReply.setID(ontoIndiv.getURI());
						
						//System.out.println("The property "+property.getURI()+" has value: "+propertyValue);	
					}	
				}
			}
		  }catch(Exception e){
				System.out.println(e.toString()+" Search Problem in processIndividual");
		  }
		  mappingOnProcess.remove(ontoIndiv);
		  if (whichClass.equalsIgnoreCase("mapping")) return mainMappingReply;
		  else if (whichClass.equalsIgnoreCase("mappingProperty")) return mPropertyReply;
		  else if (whichClass.equalsIgnoreCase("mappingMethod")) return mMethodReply;
		  else if (whichClass.equalsIgnoreCase("mappingAlgorithm")) return mAlgorithmReply;
		  else if (whichClass.equalsIgnoreCase("mappingManualMethod")) return mManualReply;
		  else if (whichClass.equalsIgnoreCase("mappingFilter")) return mFilterReply;
		  else if (whichClass.equalsIgnoreCase("mappingParallel")) return mParallelReply;
		  else if (whichClass.equalsIgnoreCase("mappingSequence")) return mSequenceReply;
		  else if (whichClass.equalsIgnoreCase("mappingEvidence")) return mEvidenceReply;
		  else if (whichClass.equalsIgnoreCase("mappingArgument")) return mArgumentReply;
		  else if (whichClass.equalsIgnoreCase("mappingCertificate")) return mCertificateReply;
		  else if (whichClass.equalsIgnoreCase("mappingProof")) return mProofReply;
		  else return null;
		 }
		 else{
			if (whichClass.equalsIgnoreCase("mapping")) {mainMappingReply = new OMVMapping();mainMappingReply.setURI(ontoIndiv.getURI());return mainMappingReply;}			
			else if (whichClass.equalsIgnoreCase("mappingProperty")) {mPropertyReply = new OMVMappingProperty();mPropertyReply.setID(ontoIndiv.getURI());return mPropertyReply;}
			else if (whichClass.equalsIgnoreCase("mappingProperty")) {mMethodReply = new OMVMappingMethod();mMethodReply.setID(ontoIndiv.getURI());return mMethodReply;}
			else if (whichClass.equalsIgnoreCase("mappingAlgorithm")) {mAlgorithmReply = new OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm();mAlgorithmReply.setID(ontoIndiv.getURI());return mAlgorithmReply;}
			else if (whichClass.equalsIgnoreCase("mappingManualMethod")) {mManualReply = new OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod();mManualReply.setID(ontoIndiv.getURI());return mManualReply;}
			else if (whichClass.equalsIgnoreCase("mappingFilter")) {mFilterReply = new OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter();mFilterReply.setID(ontoIndiv.getURI());return mFilterReply;}
			else if (whichClass.equalsIgnoreCase("mappingParallel")) {mParallelReply = new OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel();mParallelReply.setID(ontoIndiv.getURI());return mParallelReply;}
			else if (whichClass.equalsIgnoreCase("mappingSequence")) {mSequenceReply = new OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence();mSequenceReply.setID(ontoIndiv.getURI());return mSequenceReply;}
			else if (whichClass.equalsIgnoreCase("mappingEvidence")) {mEvidenceReply = new OMVMappingEvidence();mEvidenceReply.setID(ontoIndiv.getURI());return mEvidenceReply;}
			else if (whichClass.equalsIgnoreCase("mappingArgument")) {mArgumentReply = new OMVMappingEvidence.OMVMappingArgument();mArgumentReply.setID(ontoIndiv.getURI());return mArgumentReply;}
			else if (whichClass.equalsIgnoreCase("mappingCertificate")) {mCertificateReply = new OMVMappingEvidence.OMVMappingCertificate();mCertificateReply.setID(ontoIndiv.getURI());return mCertificateReply;}
			else if (whichClass.equalsIgnoreCase("mappingProof")) {mProofReply = new OMVMappingEvidence.OMVMappingProof();mProofReply.setID(ontoIndiv.getURI());return mProofReply;}
			return null;
		 }
		}

	private OMVMapping createOMVMapping(String URI, String value){
		OMVMapping mappingReply=new OMVMapping();
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.URI)) {mappingReply.setURI(value); return mappingReply;}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.creationDate)) {mappingReply.setCreationDate(value); return mappingReply;}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.hasCreator)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party");
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person");
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				mappingReply.addHasCreator(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation");
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					mappingReply.addHasCreator(organisationReply);
					organisationReply = null;
				}
			}
			partyReply=null;
			return mappingReply;
		}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.hasProperty)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVMappingProperty mPropertyReply=(OMVMappingProperty)processMappingIndividual(oIndividual, "mappingProperty");
			if (mPropertyReply!=null) mappingReply.addHasProperty(mPropertyReply);
			mPropertyReply = null;
			return mappingReply;
		}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.usedMethod)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVMappingMethod mMethodReply=(OMVMappingMethod)processMappingIndividual(oIndividual, "mappingMethod");
			OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mAlgorithmReply=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)processMappingIndividual(oIndividual, "mappingAlgorithm");
			if (mAlgorithmReply!=null){
				if (mMethodReply!=null) {
					OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mAlgorithmFinal=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)copyMethod2SubClass(mMethodReply,mAlgorithmReply);
					mAlgorithmReply=mAlgorithmFinal;
				}
				mappingReply.setUsedMethod(mAlgorithmReply);
				mAlgorithmReply = null;
			}
			else{
				OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod mManualReply=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)processMappingIndividual(oIndividual, "mappingManualMethod");
				if (mManualReply!=null){
					if (mMethodReply!=null) {
						OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod mManualFinal=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)copyMethod2SubClass(mMethodReply,mManualReply);
						mManualReply=mManualFinal;
					}
					mappingReply.setUsedMethod(mManualReply);
					mManualReply = null;
				}
				else{
					OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mFilterReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)processMappingIndividual(oIndividual, "mappingFilter");
					if (mFilterReply!=null){
						if (mMethodReply!=null) {
							OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mFilterFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)copyMethod2SubClass(mMethodReply,mFilterReply);
							mFilterReply=mFilterFinal;
						}
						mappingReply.setUsedMethod(mFilterReply);
						mFilterReply = null;
					}
					else{
						OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mParallelReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)processMappingIndividual(oIndividual, "mappingParallel");
						if (mParallelReply!=null){
							if (mMethodReply!=null) {
								OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mParallelFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)copyMethod2SubClass(mMethodReply,mParallelReply);
								mParallelReply=mParallelFinal;
							}
							mappingReply.setUsedMethod(mParallelReply);
							mParallelReply = null;
						}
						else{
							OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mSequenceReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)processMappingIndividual(oIndividual, "mappingSequence");
							if (mSequenceReply!=null){
								if (mMethodReply!=null) {
									OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mSequenceFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)copyMethod2SubClass(mMethodReply,mSequenceReply);
									mSequenceReply=mSequenceFinal;
								}
								mappingReply.setUsedMethod(mSequenceReply);
								mSequenceReply = null;
							}
						}
					}
				}
			}
			mMethodReply = null;
			return mappingReply;
		}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.hasSourceOntology)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntology oReferencesReply=(OMVOntology)processIndividual(oIndividual, "ontology");
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			mappingReply.setHasSourceOntology(oReferencesReply);
			oReferencesReply = null;
			return mappingReply;
		}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.hasTargetOntology)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntology oReferencesReply=(OMVOntology)processIndividual(oIndividual, "ontology");
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			mappingReply.setHasTargetOntology(oReferencesReply);
			oReferencesReply = null;
			return mappingReply;
		}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.level)) {mappingReply.setLevel(value); return mappingReply;}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.processingTime)) {mappingReply.setProcessingTime(new Float(value.substring(1, value.indexOf("\"", 2))));return mappingReply;}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.purpose)) {mappingReply.setPurpose(value); return mappingReply;}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.type)) {mappingReply.setType(value); return mappingReply;}
		return mappingReply;
	}
	
	private OMVMappingProperty createOMVMappingProperty(String URI, String value){
		OMVMappingProperty mappingPropertyReply=new OMVMappingProperty();
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.ID)) {mappingPropertyReply.setID(value); return mappingPropertyReply;}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.hasEvidence)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVMappingEvidence mEvidenceReply=(OMVMappingEvidence)processMappingIndividual(oIndividual, "mappingEvidence");
			OMVMappingEvidence.OMVMappingArgument mArgumentReply=(OMVMappingEvidence.OMVMappingArgument)processMappingIndividual(oIndividual, "mappingArgument");
			if (mArgumentReply!=null){
				if (mEvidenceReply!=null) {
					mArgumentReply.setID(mEvidenceReply.getID());
				}
				mappingPropertyReply.addHasEvidence(mArgumentReply);
				mArgumentReply = null;
			}
			else{
				OMVMappingEvidence.OMVMappingCertificate mCertificateReply=(OMVMappingEvidence.OMVMappingCertificate)processMappingIndividual(oIndividual, "mappingCertificate");
				if (mCertificateReply!=null){
					if (mEvidenceReply!=null) {
						mCertificateReply.setID(mEvidenceReply.getID());
					}
					mappingPropertyReply.addHasEvidence(mCertificateReply);
					mCertificateReply = null;
				}
				else{
					OMVMappingEvidence.OMVMappingProof mProofReply=(OMVMappingEvidence.OMVMappingProof)processMappingIndividual(oIndividual, "mappingProof");
					if (mProofReply!=null){
						if (mEvidenceReply!=null) {
							mProofReply.setID(mEvidenceReply.getID());
						}
						mappingPropertyReply.addHasEvidence(mProofReply);
						mProofReply = null;
					}
				}
			}
			mEvidenceReply = null;
			return mappingPropertyReply;
		}
		return mappingPropertyReply;
	}
	
	private OMVMappingMethod createOMVMappingMethod(String URI, String value){
		OMVMappingMethod mappingMethodReply=new OMVMappingMethod();
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.ID)) {mappingMethodReply.setID(value); return mappingMethodReply;}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.hasParameter)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVMappingParameter mParameterReply=(OMVMappingParameter)processMappingIndividual(oIndividual, "mappingParameter");
			if (mParameterReply!=null) mappingMethodReply.addHasParameter(mParameterReply);
			mParameterReply = null;
			return mappingMethodReply;
		}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.hasCreator)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party");
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person");
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				mappingMethodReply.addHasCreator(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation");
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					mappingMethodReply.addHasCreator(organisationReply);
					organisationReply = null;
				}
			}
			partyReply=null;
			return mappingMethodReply;
		}
		return mappingMethodReply;
	}
	
	private OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm createOMVMappingAlgorithm(String URI, String value){
		OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mappingAlgorithReply=new OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm();
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.source)) {mappingAlgorithReply.setSource(value); return mappingAlgorithReply;}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.version)) {mappingAlgorithReply.setVersion(value); return mappingAlgorithReply;}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.naturalLanguage)) {mappingAlgorithReply.setNaturalLanguage(value); return mappingAlgorithReply;}
		return mappingAlgorithReply;
	}
	
	private OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter createOMVMappingFilter(String URI, String value){
		OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mappingFilterReply=new OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter();
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.value)) {mappingFilterReply.setValue(new Float(value.substring(1, value.indexOf("\"", 2)))); return mappingFilterReply;}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.variety)) {mappingFilterReply.setVariety(value); return mappingFilterReply;}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.filtersMethod)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVMappingMethod mMethodReply=(OMVMappingMethod)processMappingIndividual(oIndividual, "mappingMethod");
			OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mAlgorithmReply=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)processMappingIndividual(oIndividual, "mappingAlgorithm");
			if (mAlgorithmReply!=null){
				if (mMethodReply!=null) {
					OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mAlgorithmFinal=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)copyMethod2SubClass(mMethodReply,mAlgorithmReply);
					mAlgorithmReply=mAlgorithmFinal;
				}
				mappingFilterReply.setFiltersMethod(mAlgorithmReply);
				mAlgorithmReply = null;
			}
			else{
				OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod mManualReply=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)processMappingIndividual(oIndividual, "mappingManualMethod");
				if (mManualReply!=null){
					if (mMethodReply!=null) {
						OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod mManualFinal=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)copyMethod2SubClass(mMethodReply,mManualReply);
						mManualReply=mManualFinal;
					}
					mappingFilterReply.setFiltersMethod(mManualReply);
					mManualReply = null;
				}
				else{
					OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mFilterReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)processMappingIndividual(oIndividual, "mappingFilter");
					if (mFilterReply!=null){
						if (mMethodReply!=null) {
							OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mFilterFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)copyMethod2SubClass(mMethodReply,mFilterReply);
							mFilterReply=mFilterFinal;
						}
						mappingFilterReply.setFiltersMethod(mFilterReply);
						mFilterReply = null;
					}
					else{
						OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mParallelReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)processMappingIndividual(oIndividual, "mappingParallel");
						if (mParallelReply!=null){
							if (mMethodReply!=null) {
								OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mParallelFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)copyMethod2SubClass(mMethodReply,mParallelReply);
								mParallelReply=mParallelFinal;
							}
							mappingFilterReply.setFiltersMethod(mParallelReply);
							mParallelReply = null;
						}
						else{
							OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mSequenceReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)processMappingIndividual(oIndividual, "mappingSequence");
							if (mSequenceReply!=null){
								if (mMethodReply!=null) {
									OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mSequenceFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)copyMethod2SubClass(mMethodReply,mSequenceReply);
									mSequenceReply=mSequenceFinal;
								}
								mappingFilterReply.setFiltersMethod(mSequenceReply);
								mSequenceReply = null;
							}
						}
					}
				}
			}
			mMethodReply = null;
			return mappingFilterReply;
		}
		return mappingFilterReply;
	}
	
	private OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel createOMVMappingParallel(String URI, String value){
		OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mappingParallelReply=new OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel();
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.aggregatesMethod)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVMappingMethod mMethodReply=(OMVMappingMethod)processMappingIndividual(oIndividual, "mappingMethod");
			OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mAlgorithmReply=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)processMappingIndividual(oIndividual, "mappingAlgorithm");
			if (mAlgorithmReply!=null){
				if (mMethodReply!=null) {
					OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mAlgorithmFinal=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)copyMethod2SubClass(mMethodReply,mAlgorithmReply);
					mAlgorithmReply=mAlgorithmFinal;
				}
				mappingParallelReply.addAggregatesMethod(mAlgorithmReply);
				mAlgorithmReply = null;
			}
			else{
				OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod mManualReply=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)processMappingIndividual(oIndividual, "mappingManualMethod");
				if (mManualReply!=null){
					if (mMethodReply!=null) {
						OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod mManualFinal=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)copyMethod2SubClass(mMethodReply,mManualReply);
						mManualReply=mManualFinal;
					}
					mappingParallelReply.addAggregatesMethod(mManualReply);
					mManualReply = null;
				}
				else{
					OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mFilterReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)processMappingIndividual(oIndividual, "mappingFilter");
					if (mFilterReply!=null){
						if (mMethodReply!=null) {
							OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mFilterFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)copyMethod2SubClass(mMethodReply,mFilterReply);
							mFilterReply=mFilterFinal;
						}
						mappingParallelReply.addAggregatesMethod(mFilterReply);
						mFilterReply = null;
					}
					else{
						OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mParallelReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)processMappingIndividual(oIndividual, "mappingParallel");
						if (mParallelReply!=null){
							if (mMethodReply!=null) {
								OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mParallelFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)copyMethod2SubClass(mMethodReply,mParallelReply);
								mParallelReply=mParallelFinal;
							}
							mappingParallelReply.addAggregatesMethod(mParallelReply);
							mParallelReply = null;
						}
						else{
							OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mSequenceReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)processMappingIndividual(oIndividual, "mappingSequence");
							if (mSequenceReply!=null){
								if (mMethodReply!=null) {
									OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mSequenceFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)copyMethod2SubClass(mMethodReply,mSequenceReply);
									mSequenceReply=mSequenceFinal;
								}
								mappingParallelReply.addAggregatesMethod(mSequenceReply);
								mSequenceReply = null;
							}
						}
					}
				}
			}
			mMethodReply = null;
			return mappingParallelReply;
		}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.composesMethod)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVMappingMethod mMethodReply=(OMVMappingMethod)processMappingIndividual(oIndividual, "mappingMethod");
			OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mAlgorithmReply=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)processMappingIndividual(oIndividual, "mappingAlgorithm");
			if (mAlgorithmReply!=null){
				if (mMethodReply!=null) {
					OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mAlgorithmFinal=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)copyMethod2SubClass(mMethodReply,mAlgorithmReply);
					mAlgorithmReply=mAlgorithmFinal;
				}
				mappingParallelReply.addComposesMethod(mAlgorithmReply);
				mAlgorithmReply = null;
			}
			else{
				OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod mManualReply=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)processMappingIndividual(oIndividual, "mappingManualMethod");
				if (mManualReply!=null){
					if (mMethodReply!=null) {
						OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod mManualFinal=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)copyMethod2SubClass(mMethodReply,mManualReply);
						mManualReply=mManualFinal;
					}
					mappingParallelReply.addComposesMethod(mManualReply);
					mManualReply = null;
				}
				else{
					OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mFilterReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)processMappingIndividual(oIndividual, "mappingFilter");
					if (mFilterReply!=null){
						if (mMethodReply!=null) {
							OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mFilterFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)copyMethod2SubClass(mMethodReply,mFilterReply);
							mFilterReply=mFilterFinal;
						}
						mappingParallelReply.addComposesMethod(mFilterReply);
						mFilterReply = null;
					}
					else{
						OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mParallelReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)processMappingIndividual(oIndividual, "mappingParallel");
						if (mParallelReply!=null){
							if (mMethodReply!=null) {
								OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mParallelFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)copyMethod2SubClass(mMethodReply,mParallelReply);
								mParallelReply=mParallelFinal;
							}
							mappingParallelReply.addComposesMethod(mParallelReply);
							mParallelReply = null;
						}
						else{
							OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mSequenceReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)processMappingIndividual(oIndividual, "mappingSequence");
							if (mSequenceReply!=null){
								if (mMethodReply!=null) {
									OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mSequenceFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)copyMethod2SubClass(mMethodReply,mSequenceReply);
									mSequenceReply=mSequenceFinal;
								}
								mappingParallelReply.addComposesMethod(mSequenceReply);
								mSequenceReply = null;
							}
						}
					}
				}
			}
			mMethodReply = null;
			return mappingParallelReply;
		}
		return mappingParallelReply;
	}

	private OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence createOMVMappingSequence(String URI, String value){
		OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mappingSequenceReply=new OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence();
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.composesMethod)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVMappingMethod mMethodReply=(OMVMappingMethod)processMappingIndividual(oIndividual, "mappingMethod");
			OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mAlgorithmReply=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)processMappingIndividual(oIndividual, "mappingAlgorithm");
			if (mAlgorithmReply!=null){
				if (mMethodReply!=null) {
					OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mAlgorithmFinal=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)copyMethod2SubClass(mMethodReply,mAlgorithmReply);
					mAlgorithmReply=mAlgorithmFinal;
				}
				mappingSequenceReply.addComposesMethod(mAlgorithmReply);
				mAlgorithmReply = null;
			}
			else{
				OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod mManualReply=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)processMappingIndividual(oIndividual, "mappingManualMethod");
				if (mManualReply!=null){
					if (mMethodReply!=null) {
						OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod mManualFinal=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)copyMethod2SubClass(mMethodReply,mManualReply);
						mManualReply=mManualFinal;
					}
					mappingSequenceReply.addComposesMethod(mManualReply);
					mManualReply = null;
				}
				else{
					OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mFilterReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)processMappingIndividual(oIndividual, "mappingFilter");
					if (mFilterReply!=null){
						if (mMethodReply!=null) {
							OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mFilterFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)copyMethod2SubClass(mMethodReply,mFilterReply);
							mFilterReply=mFilterFinal;
						}
						mappingSequenceReply.addComposesMethod(mFilterReply);
						mFilterReply = null;
					}
					else{
						OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mParallelReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)processMappingIndividual(oIndividual, "mappingParallel");
						if (mParallelReply!=null){
							if (mMethodReply!=null) {
								OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mParallelFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)copyMethod2SubClass(mMethodReply,mParallelReply);
								mParallelReply=mParallelFinal;
							}
							mappingSequenceReply.addComposesMethod(mParallelReply);
							mParallelReply = null;
						}
						else{
							OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mSequenceReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)processMappingIndividual(oIndividual, "mappingSequence");
							if (mSequenceReply!=null){
								if (mMethodReply!=null) {
									OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mSequenceFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)copyMethod2SubClass(mMethodReply,mSequenceReply);
									mSequenceReply=mSequenceFinal;
								}
								mappingSequenceReply.addComposesMethod(mSequenceReply);
								mSequenceReply = null;
							}
						}
					}
				}
			}
			mMethodReply = null;
			return mappingSequenceReply;
		}
		return mappingSequenceReply;
	}
	
	private Object copyMethod2SubClass(OMVMappingMethod paReply,Object peReply){
		OMVMappingMethod methodReply = paReply;
		Object element = peReply;
		
		((OMVMappingMethod)element).setID(methodReply.getID());
		Iterator it = methodReply.getHasCreator().iterator();
		while(it.hasNext()){
			OMVParty t = (OMVParty)it.next();
			if (t!=null){
				((OMVMappingMethod)element).addHasCreator(t);
			}
		}
		it = methodReply.getHasParameter().iterator();
		while(it.hasNext()){
			OMVMappingParameter t = (OMVMappingParameter)it.next();
			if (t!=null){
				((OMVMappingMethod)element).addHasParameter(t);
			}
		}
		return element;
	}

	private LinkedList<Condition> getMappingConditions (OMVMapping o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getURI()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.URI, o.getURI(), checkDataPropertyM(Constants.URI));
			else condition = new Condition(Constants.momvCondition+Constants.URI, o.getURI(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getCreationDate()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.momvCondition+Constants.creationDate, o.getCreationDate(), checkDataPropertyM(Constants.creationDate));
			else condition = new Condition(Constants.momvCondition+Constants.creationDate, o.getCreationDate(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getHasCreator().size()>0) {
			Iterator it = o.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		if (o.getHasProperty().size()>0) {
			Iterator it = o.getHasProperty().iterator();
			while(it.hasNext()){
				OMVMappingProperty od = (OMVMappingProperty)it.next();
				LinkedList<Condition> conditionTemp=getMPRConditions(od,Constants.momvCondition+Constants.hasProperty);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getUsedMethod()!=null) {
			LinkedList<Condition> conditionTemp=null;
			Object t = o.getUsedMethod();
			if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm){conditionTemp=getMAConditions(((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)t),Constants.momvCondition+Constants.usedMethod);}
			else if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod){conditionTemp=getMMConditions(((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)t),Constants.momvCondition+Constants.usedMethod);}
			else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter){conditionTemp=getMFConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)t),Constants.momvCondition+Constants.usedMethod);}
			else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel){conditionTemp=getMPConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)t),Constants.momvCondition+Constants.usedMethod);}
			else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence){conditionTemp=getMSConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)t),Constants.momvCondition+Constants.usedMethod);}
			searchConditions.addAll(conditionTemp);
		}
		if (o.getHasSourceOntology()!=null) {
			OMVOntology otemp = o.getHasSourceOntology();
			LinkedList<Condition> conditionTemp=getConditions(otemp,Constants.momvCondition+Constants.hasSourceOntology);
			searchConditions.addAll(conditionTemp);
		}
		if (o.getHasTargetOntology()!=null) {
			OMVOntology otemp = o.getHasTargetOntology();
			LinkedList<Condition> conditionTemp=getConditions(otemp,Constants.momvCondition+Constants.hasTargetOntology);
			searchConditions.addAll(conditionTemp);
		}
		if (o.getLevel()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.momvCondition+Constants.level, o.getLevel(), checkDataPropertyM(Constants.level));
			else condition = new Condition(Constants.momvCondition+Constants.level, o.getLevel(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getProcessingTime()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.momvCondition+Constants.processingTime, o.getProcessingTime().toString(), checkDataPropertyM(Constants.processingTime));
			else condition = new Condition(Constants.momvCondition+Constants.processingTime, o.getProcessingTime().toString(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getPurpose()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.momvCondition+Constants.purpose, o.getPurpose(), checkDataPropertyM(Constants.purpose));
			else condition = new Condition(Constants.momvCondition+Constants.purpose, o.getPurpose(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getType()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.momvCondition+Constants.type, o.getType(), checkDataPropertyM(Constants.type));
			else condition = new Condition(Constants.momvCondition+Constants.type, o.getType(), which);
			searchConditions.addFirst(condition);
		}
		return searchConditions;
	}
	
	private LinkedList<Condition> getMPRConditions (OMVMappingProperty o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getID()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), checkDataPropertyM(Constants.ID));
			else condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getHasEvidence().size()>0) {
			LinkedList<Condition> conditionTemp=null;
			Iterator it = o.getHasEvidence().iterator();
			while(it.hasNext()){
				Object t = o.getHasEvidence();
				if (t instanceof OMVMappingEvidence.OMVMappingArgument){conditionTemp=getEAConditions(((OMVMappingEvidence.OMVMappingArgument)t),Constants.momvCondition+Constants.hasEvidence);}
				else if (t instanceof OMVMappingEvidence.OMVMappingCertificate){conditionTemp=getECConditions(((OMVMappingEvidence.OMVMappingCertificate)t),Constants.momvCondition+Constants.usedMethod);}
				else if (t instanceof OMVMappingEvidence.OMVMappingProof){conditionTemp=getEPConditions(((OMVMappingEvidence.OMVMappingProof)t),Constants.momvCondition+Constants.usedMethod);}
				searchConditions.addAll(conditionTemp);
			}
		}
		return searchConditions;
	}
	
	private LinkedList<Condition> getMAConditions (OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getID()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), checkDataPropertyM(Constants.ID));
			else condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getHasCreator().size()>0) {
			Iterator it = o.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		if (o.getHasParameter().size()>0) {
			Iterator it = o.getHasParameter().iterator();
			while(it.hasNext()){
				OMVMappingParameter od = (OMVMappingParameter)it.next();
				LinkedList<Condition> conditionTemp=getMPAConditions(od,Constants.momvCondition+Constants.hasParameter);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getSource()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.source, o.getSource(), checkDataPropertyM(Constants.source));
			else condition = new Condition(Constants.momvCondition+Constants.source, o.getSource(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getVersion()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.version, o.getVersion(), checkDataPropertyM(Constants.version));
			else condition = new Condition(Constants.momvCondition+Constants.version, o.getVersion(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getNaturalLanguage()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.naturalLanguage, o.getNaturalLanguage(), checkDataPropertyM(Constants.naturalLanguage));
			else condition = new Condition(Constants.momvCondition+Constants.naturalLanguage, o.getNaturalLanguage(), which); 
			searchConditions.addFirst(condition);
		}
		return searchConditions;
	}
	
	private LinkedList<Condition> getMMConditions (OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getID()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), checkDataPropertyM(Constants.ID));
			else condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getHasCreator().size()>0) {
			Iterator it = o.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		if (o.getHasParameter().size()>0) {
			Iterator it = o.getHasParameter().iterator();
			while(it.hasNext()){
				OMVMappingParameter od = (OMVMappingParameter)it.next();
				LinkedList<Condition> conditionTemp=getMPAConditions(od,Constants.momvCondition+Constants.hasParameter);
				searchConditions.addAll(conditionTemp);
			}
		}
		return searchConditions;
	}
	
	private LinkedList<Condition> getMFConditions (OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getID()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), checkDataPropertyM(Constants.ID));
			else condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getHasCreator().size()>0) {
			Iterator it = o.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		if (o.getHasParameter().size()>0) {
			Iterator it = o.getHasParameter().iterator();
			while(it.hasNext()){
				OMVMappingParameter od = (OMVMappingParameter)it.next();
				LinkedList<Condition> conditionTemp=getMPAConditions(od,Constants.momvCondition+Constants.hasParameter);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getVariety()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.variety, o.getVariety(), checkDataPropertyM(Constants.variety));
			else condition = new Condition(Constants.momvCondition+Constants.variety, o.getVariety(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getValue()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.value, o.getValue().toString(), checkDataPropertyM(Constants.value));
			else condition = new Condition(Constants.momvCondition+Constants.value, o.getValue().toString(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getFiltersMethod()!=null) {
			LinkedList<Condition> conditionTemp=null;
			Object t = o.getFiltersMethod();
			if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm){conditionTemp=getMAConditions(((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)t),Constants.momvCondition+Constants.filtersMethod);}
			else if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod){conditionTemp=getMMConditions(((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)t),Constants.momvCondition+Constants.filtersMethod);}
			else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter){conditionTemp=getMFConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)t),Constants.momvCondition+Constants.filtersMethod);}
			else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel){conditionTemp=getMPConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)t),Constants.momvCondition+Constants.filtersMethod);}
			else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence){conditionTemp=getMSConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)t),Constants.momvCondition+Constants.filtersMethod);}
			searchConditions.addAll(conditionTemp);
		}
		return searchConditions;
	}
	
	private LinkedList<Condition> getMPConditions (OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getID()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), checkDataPropertyM(Constants.ID));
			else condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getHasCreator().size()>0) {
			Iterator it = o.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		if (o.getHasParameter().size()>0) {
			Iterator it = o.getHasParameter().iterator();
			while(it.hasNext()){
				OMVMappingParameter od = (OMVMappingParameter)it.next();
				LinkedList<Condition> conditionTemp=getMPAConditions(od,Constants.momvCondition+Constants.hasParameter);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getAggregatesMethod().size()>0) {
			Iterator it = o.getAggregatesMethod().iterator();
			while(it.hasNext()){
				LinkedList<Condition> conditionTemp=null;
				Object t = it.next();
				if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm){conditionTemp=getMAConditions(((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)t),Constants.momvCondition+Constants.aggregatesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod){conditionTemp=getMMConditions(((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)t),Constants.momvCondition+Constants.aggregatesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter){conditionTemp=getMFConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)t),Constants.momvCondition+Constants.aggregatesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel){conditionTemp=getMPConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)t),Constants.momvCondition+Constants.aggregatesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence){conditionTemp=getMSConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)t),Constants.momvCondition+Constants.aggregatesMethod);}
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getComposesMethod().size()>0) {
			Iterator it = o.getComposesMethod().iterator();
			while(it.hasNext()){
				LinkedList<Condition> conditionTemp=null;
				Object t = it.next();
				if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm){conditionTemp=getMAConditions(((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)t),Constants.momvCondition+Constants.composesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod){conditionTemp=getMMConditions(((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)t),Constants.momvCondition+Constants.composesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter){conditionTemp=getMFConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)t),Constants.momvCondition+Constants.composesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel){conditionTemp=getMPConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)t),Constants.momvCondition+Constants.composesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence){conditionTemp=getMSConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)t),Constants.momvCondition+Constants.composesMethod);}
				searchConditions.addAll(conditionTemp);
			}
		}
		return searchConditions;
	}
	
	private LinkedList<Condition> getMSConditions (OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getID()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), checkDataPropertyM(Constants.ID));
			else condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getHasCreator().size()>0) {
			Iterator it = o.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		if (o.getHasParameter().size()>0) {
			Iterator it = o.getHasParameter().iterator();
			while(it.hasNext()){
				OMVMappingParameter od = (OMVMappingParameter)it.next();
				LinkedList<Condition> conditionTemp=getMPAConditions(od,Constants.momvCondition+Constants.hasParameter);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getComposesMethod().size()>0) {
			Iterator it = o.getComposesMethod().iterator();
			while(it.hasNext()){
				LinkedList<Condition> conditionTemp=null;
				Object t = it.next();
				if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm){conditionTemp=getMAConditions(((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)t),Constants.momvCondition+Constants.composesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod){conditionTemp=getMMConditions(((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)t),Constants.momvCondition+Constants.composesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter){conditionTemp=getMFConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)t),Constants.momvCondition+Constants.composesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel){conditionTemp=getMPConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)t),Constants.momvCondition+Constants.composesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence){conditionTemp=getMSConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)t),Constants.momvCondition+Constants.composesMethod);}
				searchConditions.addAll(conditionTemp);
			}
		}
		return searchConditions;
	}
	
	private LinkedList<Condition> getEAConditions (OMVMappingEvidence.OMVMappingArgument o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getID()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), checkDataPropertyM(Constants.ID));
			else condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), which); 
			searchConditions.addFirst(condition);
		}
		return searchConditions;
	}
	
	private LinkedList<Condition> getECConditions (OMVMappingEvidence.OMVMappingCertificate o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getID()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), checkDataPropertyM(Constants.ID));
			else condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), which); 
			searchConditions.addFirst(condition);
		}
		return searchConditions;
	}
	
	private LinkedList<Condition> getEPConditions (OMVMappingEvidence.OMVMappingProof o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getID()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), checkDataPropertyM(Constants.ID));
			else condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), which); 
			searchConditions.addFirst(condition);
		}
		return searchConditions;
	}
	
	private LinkedList<Condition> getMPAConditions (OMVMappingParameter o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getID()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), checkDataPropertyM(Constants.ID));
			else condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), which); 
			searchConditions.addFirst(condition);
		}
		return searchConditions;
	}
	
	private Boolean checkDataPropertyM(String propertyName)  {
		Ontology resourceMappingOntology = mOyster2.getMappingOntology();
		OWLClass ontologyClass = KAON2Manager.factory().owlClass(Constants.OMV+Constants.DefaultMappingOntologyRoot);
		try{
			/*KAON2 BUG, DOES NOT SUPPORT OWL DL
	         * SHOULD BE DELETED WHEN IT DOES
	         */
			if(propertyName.equals(Constants.ID)) return true;
			
			/* UNTIL HERE */
			
			Set<DataProperty> dataProperties=ontologyClass.getDataPropertiesFrom(resourceMappingOntology);
			for (DataProperty dataProperty : dataProperties){
				if(propertyName.equals(Namespaces.guessLocalName(dataProperty.getURI()))) return true; 
			}
		}
	    catch (KAON2Exception e) {
	    	System.err.println(e + " in checkDataPropertyM()");
	    }
		return false;
	}
	
	private LinkedList getMappingProperties(OMVMapping m){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (m.getURI()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.URI, m.getURI());
			tProperties.addFirst(prop);
		}
		if (m.getCreationDate()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.creationDate, m.getCreationDate());
			tProperties.addFirst(prop);
		}
		if (m.getHasCreator().size()>0) {
			String tURN;
			Iterator it = m.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		if (m.getHasProperty().size()>0) {
			String tURN;
			Iterator it = m.getHasProperty().iterator();
			while(it.hasNext()){
				OMVMappingProperty e = (OMVMappingProperty)it.next();
				if (e.getID()!=null){
					tURN=e.getID();
					tList.clear();
					tList=getPropertiesMPR(e);
					IOntology.addConceptToRegistry(0,tList,14);
					OntologyProperty prop = new OntologyProperty(Constants.hasProperty, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m.getUsedMethod()!=null) {
			String tURN;
			Object t = m.getUsedMethod();
			if (m.getUsedMethod().getID()!=null){
				tURN=m.getUsedMethod().getID();
				tList.clear();
				if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm){
					tList=getPropertiesMA((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)t);
					IOntology.addConceptToRegistry(0,tList,15);
				}
				else if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod){
					tList=getPropertiesMM((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)t);
					IOntology.addConceptToRegistry(0,tList,16);
				}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter){
					tList=getPropertiesMF((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)t);
					IOntology.addConceptToRegistry(0,tList,17);
				}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel){
					tList=getPropertiesMP((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)t);
					IOntology.addConceptToRegistry(0,tList,18);
				}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence){
					tList=getPropertiesMS((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)t);
					IOntology.addConceptToRegistry(0,tList,19);
				}
				OntologyProperty prop = new OntologyProperty(Constants.usedMethod, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m.getHasSourceOntology()!=null) {
			String tURN;
			OMVOntology otemp = m.getHasSourceOntology();
			if (otemp.getURI()!=null){
				tURN=otemp.getURI();
				tList.clear();
				tList=getProperties(otemp);
				IOntology.addImportOntologyToRegistry(tList,0);
				OntologyProperty prop = new OntologyProperty(Constants.hasSourceOntology, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m.getHasTargetOntology()!=null) {
			String tURN;
			OMVOntology otemp = m.getHasTargetOntology();
			if (otemp.getURI()!=null){
				tURN=otemp.getURI();
				tList.clear();
				tList=getProperties(otemp);
				IOntology.addImportOntologyToRegistry(tList,0);
				OntologyProperty prop = new OntologyProperty(Constants.hasTargetOntology, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m.getLevel()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.level, m.getLevel());
			tProperties.addFirst(prop);
		}
		if (m.getProcessingTime()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.processingTime, m.getProcessingTime().toString());
			tProperties.addFirst(prop);
		}
		if (m.getPurpose()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.purpose, m.getPurpose());
			tProperties.addFirst(prop);
		}
		if (m.getType()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.type, m.getType());
			tProperties.addFirst(prop);
		}
		return tProperties;
	}
	
	private LinkedList getPropertiesMPR(OMVMappingProperty m){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (m.getID()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.ID, m.getID());
			tProperties.addFirst(prop);
		}
		if (m.getHasEvidence().size()>0) {
			String tURN;
			Iterator it = m.getHasEvidence().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (((OMVMappingEvidence)t).getID()!=null){
					tURN=((OMVMappingEvidence)t).getID();
					tList.clear();
					if (t instanceof OMVMappingEvidence.OMVMappingArgument){
						tList=getPropertiesEA((OMVMappingEvidence.OMVMappingArgument)t);
						IOntology.addConceptToRegistry(0,tList,20);
					}
					else if (t instanceof OMVMappingEvidence.OMVMappingCertificate){
						tList=getPropertiesEC((OMVMappingEvidence.OMVMappingCertificate)t);
						IOntology.addConceptToRegistry(0,tList,21);
					}
					else if (t instanceof OMVMappingEvidence.OMVMappingProof){
						tList=getPropertiesEP((OMVMappingEvidence.OMVMappingProof)t);
						IOntology.addConceptToRegistry(0,tList,22);
					}
					OntologyProperty prop = new OntologyProperty(Constants.hasEvidence, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		return tProperties;
	}
	
	private LinkedList getPropertiesMA(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm m){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (m.getID()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.ID, m.getID());
			tProperties.addFirst(prop);
		}
		if (m.getHasCreator().size()>0) {
			String tURN;
			Iterator it = m.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		if (m.getHasParameter().size()>0) {
			String tURN;
			Iterator it = m.getHasParameter().iterator();
			while(it.hasNext()){
				OMVMappingParameter e = (OMVMappingParameter)it.next();
				if (e.getID()!=null){
					tURN=e.getID();
					tList.clear();
					tList=getPropertiesMPA(e);
					IOntology.addConceptToRegistry(0,tList,23);
					OntologyProperty prop = new OntologyProperty(Constants.hasParameter, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m.getSource()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.source, m.getSource());
			tProperties.addFirst(prop);
		}
		if (m.getVersion()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.version, m.getVersion());
			tProperties.addFirst(prop);
		}
		if (m.getNaturalLanguage()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.naturalLanguage, m.getNaturalLanguage());
			tProperties.addFirst(prop);
		}
		return tProperties;
	}
	
	private LinkedList getPropertiesMM(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod m){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (m.getID()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.ID, m.getID());
			tProperties.addFirst(prop);
		}
		if (m.getHasCreator().size()>0) {
			String tURN;
			Iterator it = m.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		if (m.getHasParameter().size()>0) {
			String tURN;
			Iterator it = m.getHasParameter().iterator();
			while(it.hasNext()){
				OMVMappingParameter e = (OMVMappingParameter)it.next();
				if (e.getID()!=null){
					tURN=e.getID();
					tList.clear();
					tList=getPropertiesMPA(e);
					IOntology.addConceptToRegistry(0,tList,23);
					OntologyProperty prop = new OntologyProperty(Constants.hasParameter, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		return tProperties;
	}
	
	private LinkedList getPropertiesMF(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter m){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (m.getID()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.ID, m.getID());
			tProperties.addFirst(prop);
		}
		if (m.getHasCreator().size()>0) {
			String tURN;
			Iterator it = m.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		if (m.getHasParameter().size()>0) {
			String tURN;
			Iterator it = m.getHasParameter().iterator();
			while(it.hasNext()){
				OMVMappingParameter e = (OMVMappingParameter)it.next();
				if (e.getID()!=null){
					tURN=e.getID();
					tList.clear();
					tList=getPropertiesMPA(e);
					IOntology.addConceptToRegistry(0,tList,23);
					OntologyProperty prop = new OntologyProperty(Constants.hasParameter, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m.getValue()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.value, m.getValue().toString());
			tProperties.addFirst(prop);
		}
		if (m.getVariety()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.variety, m.getVariety());
			tProperties.addFirst(prop);
		}
		if (m.getFiltersMethod()!=null) {
			String tURN;
			Object t = m.getFiltersMethod();
			if (m.getFiltersMethod().getID()!=null){
				tURN=m.getFiltersMethod().getID();
				tList.clear();
				if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm){
					tList=getPropertiesMA((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)t);
					IOntology.addConceptToRegistry(0,tList,15);
				}
				else if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod){
					tList=getPropertiesMM((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)t);
					IOntology.addConceptToRegistry(0,tList,16);
				}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter){
					tList=getPropertiesMF((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)t);
					IOntology.addConceptToRegistry(0,tList,17);
				}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel){
					tList=getPropertiesMP((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)t);
					IOntology.addConceptToRegistry(0,tList,18);
				}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence){
					tList=getPropertiesMS((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)t);
					IOntology.addConceptToRegistry(0,tList,19);
				}
				OntologyProperty prop = new OntologyProperty(Constants.filtersMethod, tURN);
				tProperties.addFirst(prop);
			}
		}
		return tProperties;
	}

	private LinkedList getPropertiesMP(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel m){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (m.getID()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.ID, m.getID());
			tProperties.addFirst(prop);
		}
		if (m.getHasCreator().size()>0) {
			String tURN;
			Iterator it = m.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		if (m.getHasParameter().size()>0) {
			String tURN;
			Iterator it = m.getHasParameter().iterator();
			while(it.hasNext()){
				OMVMappingParameter e = (OMVMappingParameter)it.next();
				if (e.getID()!=null){
					tURN=e.getID();
					tList.clear();
					tList=getPropertiesMPA(e);
					IOntology.addConceptToRegistry(0,tList,23);
					OntologyProperty prop = new OntologyProperty(Constants.hasParameter, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m.getAggregatesMethod().size()>0) {
			String tURN;
			Iterator it = m.getAggregatesMethod().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (((OMVMappingMethod)t).getID()!=null){
					tURN=((OMVMappingMethod)t).getID();
					tList.clear();
					if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm){
						tList=getPropertiesMA((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)t);
						IOntology.addConceptToRegistry(0,tList,15);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod){
						tList=getPropertiesMM((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)t);
						IOntology.addConceptToRegistry(0,tList,16);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter){
						tList=getPropertiesMF((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)t);
						IOntology.addConceptToRegistry(0,tList,17);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel){
						tList=getPropertiesMP((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)t);
						IOntology.addConceptToRegistry(0,tList,18);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence){
						tList=getPropertiesMS((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)t);
						IOntology.addConceptToRegistry(0,tList,19);
					}
					OntologyProperty prop = new OntologyProperty(Constants.aggregatesMethod, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m.getComposesMethod().size()>0) {
			String tURN;
			Iterator it = m.getComposesMethod().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (((OMVMappingMethod)t).getID()!=null){
					tURN=((OMVMappingMethod)t).getID();
					tList.clear();
					if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm){
						tList=getPropertiesMA((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)t);
						IOntology.addConceptToRegistry(0,tList,15);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod){
						tList=getPropertiesMM((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)t);
						IOntology.addConceptToRegistry(0,tList,16);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter){
						tList=getPropertiesMF((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)t);
						IOntology.addConceptToRegistry(0,tList,17);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel){
						tList=getPropertiesMP((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)t);
						IOntology.addConceptToRegistry(0,tList,18);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence){
						tList=getPropertiesMS((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)t);
						IOntology.addConceptToRegistry(0,tList,19);
					}
					OntologyProperty prop = new OntologyProperty(Constants.composesMethod, tURN);
					tProperties.addFirst(prop);
				}
			}
		}				
		return tProperties;
	}

	private LinkedList getPropertiesMS(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence m){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (m.getID()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.ID, m.getID());
			tProperties.addFirst(prop);
		}
		if (m.getHasCreator().size()>0) {
			String tURN;
			Iterator it = m.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		if (m.getHasParameter().size()>0) {
			String tURN;
			Iterator it = m.getHasParameter().iterator();
			while(it.hasNext()){
				OMVMappingParameter e = (OMVMappingParameter)it.next();
				if (e.getID()!=null){
					tURN=e.getID();
					tList.clear();
					tList=getPropertiesMPA(e);
					IOntology.addConceptToRegistry(0,tList,23);
					OntologyProperty prop = new OntologyProperty(Constants.hasParameter, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m.getComposesMethod().size()>0) {
			String tURN;
			Iterator it = m.getComposesMethod().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (((OMVMappingMethod)t).getID()!=null){
					tURN=((OMVMappingMethod)t).getID();
					tList.clear();
					if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm){
						tList=getPropertiesMA((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)t);
						IOntology.addConceptToRegistry(0,tList,15);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod){
						tList=getPropertiesMM((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)t);
						IOntology.addConceptToRegistry(0,tList,16);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter){
						tList=getPropertiesMF((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)t);
						IOntology.addConceptToRegistry(0,tList,17);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel){
						tList=getPropertiesMP((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)t);
						IOntology.addConceptToRegistry(0,tList,18);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence){
						tList=getPropertiesMS((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)t);
						IOntology.addConceptToRegistry(0,tList,19);
					}
					OntologyProperty prop = new OntologyProperty(Constants.composesMethod, tURN);
					tProperties.addFirst(prop);
				}
			}
		}				
		return tProperties;
	}
	
	private LinkedList getPropertiesEA(OMVMappingEvidence.OMVMappingArgument m){
		//List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (m.getID()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.ID, m.getID());
			tProperties.addFirst(prop);
		}
		return tProperties;
	}
	
	private LinkedList getPropertiesEC(OMVMappingEvidence.OMVMappingCertificate m){
		//List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (m.getID()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.ID, m.getID());
			tProperties.addFirst(prop);
		}
		return tProperties;
	}
	
	private LinkedList getPropertiesEP(OMVMappingEvidence.OMVMappingProof m){
		//List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (m.getID()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.ID, m.getID());
			tProperties.addFirst(prop);
		}
		return tProperties;
	}
	
	private LinkedList getPropertiesMPA(OMVMappingParameter m){
		//List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (m.getID()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.ID, m.getID());
			tProperties.addFirst(prop);
		}
		return tProperties;
	}
}



/*
private OMVOntology mainOntoReply=null;
private OMVParty partyReply=null;
private OMVOntologyEngineeringTool ontoEngToolReply=null;
private OMVOntologyEngineeringMethodology ontoEngMetReply=null;
private OMVKnowledgeRepresentationParadigm krParadigmReply=null;
private OMVOntologyDomain oDomainReply=null;
private OMVOntologyType oTypeReply=null;
private OMVOntologyTask oTaskReply=null;
private OMVOntologyLanguage oLanguageReply=null;
private OMVOntologySyntax oSyntaxReply=null;
private OMVFormalityLevel fLevelReply=null;
private OMVLicenseModel lModelReply=null;
private OMVOntology oReferencesReply=null;
private OMVLocation locationReply=null;
private OMVPerson personReply=null;
private OMVOrganisation organisationReply=null;
private OMVOntologyDomain oDomainReplySub=null;
private OMVPeer peerReply=null;
private String peerNameID="";
*/

/*
private OMVOntologyDomain createOMVOntologyDomainSub(String URI, String value){
	OMVOntologyDomain oDomainReplySub=new OMVOntologyDomain();  
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.URI)) {oDomainReplySub.setURI(value); return oDomainReplySub;}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {oDomainReplySub.setName(value); return oDomainReplySub;}
	return oDomainReplySub;
}
*/

/*
private OMVOntology createOMVOntologyReferences(String URI, String value){
OMVOntology oReferencesReply=new OMVOntology();
try{
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.URI)) {oReferencesReply.setURI(value);return oReferencesReply;}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {oReferencesReply.setName(value);return oReferencesReply;}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {oReferencesReply.setAcronym(value);return oReferencesReply;}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {oReferencesReply.setDescription(value);return oReferencesReply;}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) {oReferencesReply.setDocumentation(value);return oReferencesReply;}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.keywords)) {oReferencesReply.setKeywords(value);return oReferencesReply;}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.status)) {oReferencesReply.setStatus(value);return oReferencesReply;}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.creationDate)) {oReferencesReply.setCreationDate(value);return oReferencesReply;}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.modificationDate)) {oReferencesReply.setModificationDate(value);return oReferencesReply;}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasContributor)) {
		Individual oIndividual =KAON2Manager.factory().individual(value);
		OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party");
		OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person");
		if (personReply!=null){
			if (partyReply!=null) {
				OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
				personReply=personReplyFinal;
			}
			oReferencesReply.addHasContributor(personReply);
			personReply = null;
		}
		else{
			OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation");
			if (organisationReply!=null){
				if (partyReply!=null) {
					OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
					organisationReply=organisationReplyFinal;
				}
				oReferencesReply.addHasContributor(organisationReply);
				organisationReply = null;
			}
		}
		partyReply=null;
		return oReferencesReply;
	}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasCreator)) {
		Individual oIndividual =KAON2Manager.factory().individual(value);
		OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party");
		OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person");
		if (personReply!=null){
			if (partyReply!=null) {
				OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
				personReply=personReplyFinal;
			}
			oReferencesReply.addHasCreator(personReply);
			personReply = null;
		}
		else{
			OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation");
			if (organisationReply!=null){
				if (partyReply!=null) {
					OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
					organisationReply=organisationReplyFinal;
				}
				oReferencesReply.addHasCreator(organisationReply);
				organisationReply = null;
			}
		}
		partyReply=null;
		return oReferencesReply;
	}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.usedOntologyEngineeringTool)) {
		Individual oIndividual =KAON2Manager.factory().individual(value);
		OMVOntologyEngineeringTool ontoEngToolReply=(OMVOntologyEngineeringTool)processIndividual(oIndividual, "ontoEngTool");
		if (ontoEngToolReply!=null) oReferencesReply.addUsedOntologyEngineeringTool(ontoEngToolReply);
		ontoEngToolReply = null;
		return oReferencesReply;
	}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.usedOntologyEngineeringMethodology)) {
		Individual oIndividual =KAON2Manager.factory().individual(value);
		OMVOntologyEngineeringMethodology ontoEngMetReply=(OMVOntologyEngineeringMethodology)processIndividual(oIndividual, "ontoEngMet");
		if (ontoEngMetReply!=null) oReferencesReply.addUsedOntologyEngineeringMethodology(ontoEngMetReply);
		ontoEngMetReply = null;
		return oReferencesReply;
	}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.usedKnowledgeRepresentationParadigm)){
		Individual oIndividual =KAON2Manager.factory().individual(value);
		OMVKnowledgeRepresentationParadigm krParadigmReply=(OMVKnowledgeRepresentationParadigm)processIndividual(oIndividual, "krParadigm");
		if (krParadigmReply!=null) oReferencesReply.addUsedKnowledgeRepresentationParadigm(krParadigmReply);
		krParadigmReply = null;
		return oReferencesReply;
	}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasDomain)) {
		Individual oIndividual =KAON2Manager.factory().individual(value);
		OMVOntologyDomain oDomainReply=(OMVOntologyDomain)processIndividual(oIndividual, "oDomain");
		if (oDomainReply==null) {
			oDomainReply = new OMVOntologyDomain();
			oDomainReply.setURI(value);
		}
		oReferencesReply.addHasDomain(oDomainReply);
		oDomainReply = null;
		return oReferencesReply;
	}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isOfType)) {
		Individual oIndividual =KAON2Manager.factory().individual(value);
		OMVOntologyType oTypeReply=(OMVOntologyType)processIndividual(oIndividual, "oType");
		if (oTypeReply!=null) oReferencesReply.setIsOfType(oTypeReply);
		oTypeReply = null;
		return oReferencesReply;
	}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.naturalLanguage)) {oReferencesReply.setNaturalLanguage(value);return oReferencesReply;}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.designedForOntologyTask)) {
		Individual oIndividual =KAON2Manager.factory().individual(value);
		OMVOntologyTask oTaskReply=(OMVOntologyTask)processIndividual(oIndividual, "oTask");
		if (oTaskReply!=null) oReferencesReply.addDesignedForOntologyTask(oTaskReply);
		oTaskReply = null;
		return oReferencesReply;
	}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasOntologyLanguage)) {
		Individual oIndividual =KAON2Manager.factory().individual(value);
		OMVOntologyLanguage oLanguageReply=(OMVOntologyLanguage)processIndividual(oIndividual, "oLanguage");
		if (oLanguageReply!=null) oReferencesReply.setHasOntologyLanguage(oLanguageReply);
		oLanguageReply = null;
		return oReferencesReply;
	}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasOntologySyntax)) {
		Individual oIndividual =KAON2Manager.factory().individual(value);
		OMVOntologySyntax oSyntaxReply=(OMVOntologySyntax)processIndividual(oIndividual, "oSyntax");
		if (oSyntaxReply!=null) oReferencesReply.setHasOntologySyntax(oSyntaxReply);
		oSyntaxReply = null;
		return oReferencesReply;
	}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasFormalityLevel)) {
		Individual oIndividual =KAON2Manager.factory().individual(value);
		OMVFormalityLevel fLevelReply=(OMVFormalityLevel)processIndividual(oIndividual, "fLevel");
		if (fLevelReply!=null) oReferencesReply.setHasFormalityLevel(fLevelReply);
		fLevelReply = null;
		return oReferencesReply;
	}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.resourceLocator)) {
		oReferencesReply.addResourceLocator(value);return oReferencesReply;
	}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.version)) {oReferencesReply.setVersion(value);return oReferencesReply;}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasLicense)) {
		Individual oIndividual =KAON2Manager.factory().individual(value);
		OMVLicenseModel lModelReply=(OMVLicenseModel)processIndividual(oIndividual, "lModel");
		if (lModelReply!=null) oReferencesReply.setHasLicense(lModelReply);
		lModelReply = null;
		return oReferencesReply;
	}
	//  CHANGE IT FOR DOUBLE REFERENCE  //NO LONGER NECCESARY
	//if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.useImports)) {
	//	Individual oIndividual =KAON2Manager.factory().individual(value);
	//	processIndividual(oIndividual, "oReferences");
	//	if (oReferencesReply==null) {
	//		oReferencesReply = new OMVOntology();
	//		oReferencesReply.setURI(value);
	//	}	
	//	mainOntoReply.addUseImports(oReferencesReply);
	//	oReferencesReply = null;
	//}
	//if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasPriorVersion)) {
	//	Individual oIndividual =KAON2Manager.factory().individual(value);
	//	processIndividual(oIndividual, "oReferences");
	//	if (oReferencesReply==null) {
	//		oReferencesReply = new OMVOntology();
	//		oReferencesReply.setURI(value);
	//	}
	//	mainOntoReply.setHasPriorVersion(oReferencesReply);
	//	oReferencesReply = null;
	//}
	//if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isBackwardCompatibleWith)) {
	//	Individual oIndividual =KAON2Manager.factory().individual(value);
	//	processIndividual(oIndividual, "oReferences");
	//	if (oReferencesReply==null) {
	//		oReferencesReply = new OMVOntology();
	//		oReferencesReply.setURI(value);
	//	}
	//	mainOntoReply.addIsBackwardCompatibleWith(oReferencesReply);
	//	oReferencesReply = null;
	//}
	//if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isIncompatibleWith)) {
	//	Individual oIndividual =KAON2Manager.factory().individual(value);
	//	processIndividual(oIndividual, "oReferences");
	//	if (oReferencesReply==null) {
	//		oReferencesReply = new OMVOntology();
	//		oReferencesReply.setURI(value);
	//	}
	//	mainOntoReply.addIsIncompatibleWith(oReferencesReply);
	//	oReferencesReply = null;
	//}
	
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.numberOfClasses)) {oReferencesReply.setNumberOfClasses(new Integer(value.substring(1, value.indexOf("\"", 2))));return oReferencesReply;}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.numberOfProperties)) {oReferencesReply.setNumberOfProperties(new Integer(value.substring(1, value.indexOf("\"", 2))));return oReferencesReply;}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.numberOfIndividuals)) {oReferencesReply.setNumberOfIndividuals(new Integer(value.substring(1, value.indexOf("\"", 2))));return oReferencesReply;}
	if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.numberOfAxioms)) {oReferencesReply.setNumberOfAxioms(new Integer(value.substring(1, value.indexOf("\"", 2))));return oReferencesReply;}
  }catch(Exception e){
		System.out.println(e.toString()+" Search Problem in creteOMVOntologyReferences");
  }	
  return oReferencesReply;
}
*/