package org.neontoolkit.registry.api;

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
import org.neontoolkit.omv.api.core.OMVCoreObject;
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
import org.neontoolkit.omv.api.core.OMVPerson;
import org.neontoolkit.omv.api.extensions.change.OMVChange;
import org.neontoolkit.omv.api.extensions.change.OMVChangeSpecification;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange;
import org.neontoolkit.omv.api.extensions.mapping.OMVMapping;
import org.neontoolkit.omv.api.extensions.peer.OMVPeer;
import org.neontoolkit.registry.api.change.ChangeManagement;
import org.neontoolkit.registry.api.change.RunnableSynchronization;
import org.neontoolkit.registry.api.conditions.MappingConditions;
import org.neontoolkit.registry.api.conditions.OMVConditions;
import org.neontoolkit.registry.api.duplicates.processDuplicates;
import org.neontoolkit.registry.api.duplicates.processFLDuplicates;
import org.neontoolkit.registry.api.duplicates.processKRPDuplicates;
import org.neontoolkit.registry.api.duplicates.processLMDuplicates;
import org.neontoolkit.registry.api.duplicates.processLocationDuplicates;
import org.neontoolkit.registry.api.duplicates.processMappingDuplicates;
import org.neontoolkit.registry.api.duplicates.processODDuplicates;
import org.neontoolkit.registry.api.duplicates.processOEMDuplicates;
import org.neontoolkit.registry.api.duplicates.processOETDuplicates;
import org.neontoolkit.registry.api.duplicates.processOLDuplicates;
import org.neontoolkit.registry.api.duplicates.processOSDuplicates;
import org.neontoolkit.registry.api.duplicates.processOTADuplicates;
import org.neontoolkit.registry.api.duplicates.processOTDuplicates;
import org.neontoolkit.registry.api.duplicates.processOrganisationDuplicates;
import org.neontoolkit.registry.api.duplicates.processPersonDuplicates;
import org.neontoolkit.registry.api.individuals.ProcessMappingIndividuals;
import org.neontoolkit.registry.api.individuals.ProcessOMVIndividuals;
import org.neontoolkit.registry.api.individuals.ProcessPeerIndividuals;
import org.neontoolkit.registry.api.properties.MappingProperties;
import org.neontoolkit.registry.api.properties.OMVProperties;
import org.neontoolkit.registry.api.workflow.WorkflowManagement;
import org.neontoolkit.registry.core.AdvertInformer;
import org.neontoolkit.registry.core.ExchangeInitiator;
import org.neontoolkit.registry.core.LocalExpertiseRegistry;
import org.neontoolkit.registry.core.QueryFormulator;
import org.neontoolkit.registry.oyster2.Condition;
import org.neontoolkit.registry.oyster2.Constants;
import org.neontoolkit.registry.oyster2.ImportOntology;
import org.neontoolkit.registry.oyster2.OntologyProperty;
import org.neontoolkit.registry.oyster2.Oyster2Factory;
import org.neontoolkit.registry.oyster2.Oyster2Query;
import org.neontoolkit.registry.oyster2.QueryReply;
import org.neontoolkit.registry.oyster2.SearchManager;
import org.neontoolkit.registry.util.GUID;
import org.neontoolkit.registry.util.Resource;
import org.neontoolkit.workflow.api.Action;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.owl.elements.Individual;

/**
 * The class Oyster2Connection provides the API access methods to Oyster2 registry.
 * @author Raul Palma
 * @version 2.0, March 2008
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
	static private Set<OMVOntology> OMVSetDistributed=new HashSet <OMVOntology>();
	static private Set<OMVMapping> OMVMappingSetDistributed=new HashSet <OMVMapping>();
	static private Set<OMVPerson> OMVPersonSetDistributed=new HashSet <OMVPerson>();
	static private Set<OMVOrganisation> OMVOrganisationSetDistributed=new HashSet <OMVOrganisation>();
	static private Set<OMVOntologyEngineeringTool> OMVOETSetDistributed=new HashSet <OMVOntologyEngineeringTool>();
	static private Set<OMVOntologyEngineeringMethodology> OMVOEMSetDistributed=new HashSet <OMVOntologyEngineeringMethodology>();
	static private Set<OMVKnowledgeRepresentationParadigm> OMVKRPSetDistributed=new HashSet <OMVKnowledgeRepresentationParadigm>();
	static private Set<OMVOntologyDomain> OMVODSetDistributed=new HashSet <OMVOntologyDomain>();
	static private Set<OMVOntologyType> OMVOTSetDistributed=new HashSet <OMVOntologyType>();
	static private Set<OMVOntologyTask> OMVOTASetDistributed=new HashSet <OMVOntologyTask>();
	static private Set<OMVOntologyLanguage> OMVOLSetDistributed=new HashSet <OMVOntologyLanguage>();
	static private Set<OMVOntologySyntax> OMVOSSetDistributed=new HashSet <OMVOntologySyntax>();
	static private Set<OMVFormalityLevel> OMVFLSetDistributed=new HashSet <OMVFormalityLevel>();
	static private Set<OMVLicenseModel> OMVLMSetDistributed=new HashSet <OMVLicenseModel>();
	static private Set<OMVLocation> OMVLocationSetDistributed=new HashSet <OMVLocation>();
	static private Set<QueryReply> qReplyDistributedSet= new HashSet <QueryReply>();
	static private boolean waitMore=true;
	static private int OMVObject;
	private String lastChange;
	ExchangeInitiator mExchangeInitiator;
	Thread mExchangeInitiatorThread;
	
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
		else if (o instanceof OMVChange || o instanceof OMVChangeSpecification){
			ChangeManagement cMgmt= new ChangeManagement();
			if (o instanceof OMVChange){
				lastChange=cMgmt.register ((OMVChange)o);
			}else{
				cMgmt.register ((OMVChangeSpecification)o);
			}
		}
		else if (o instanceof OMVCoreObject){
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
		else {
			System.out.println("Object not supported..");
		}
	}
		
	/**
	 * Replaces an existing object in Oyster2 registry with
	 * the new information provided. If the object
	 * does not exists, it is registered.
	 * @param o is the object that will be replaced.
	 */
	public void replace(Object o)
	{
		if (o instanceof OMVOntology){
			replace ((OMVOntology)o);
		}
		else if (o instanceof OMVMapping){
			replace ((OMVMapping)o);
		}
		else if (o instanceof OMVChangeSpecification){
			ChangeManagement cMgmt= new ChangeManagement();
			cMgmt.replace ((OMVChangeSpecification)o);
		}
		else if (o instanceof OMVCoreObject){ //FOR THE REST OF OBJECTS
			replace ((OMVCoreObject)o);
		}
		else {
			System.out.println("Object not supported..");
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
		else if (o instanceof OMVChangeSpecification){
			ChangeManagement cMgmt= new ChangeManagement();
			cMgmt.remove ((OMVChangeSpecification)o);
		}
		else if (o instanceof OMVChange){
			ChangeManagement cMgmt= new ChangeManagement();
			cMgmt.safeRemove ((OMVChange)o);
		}
		else if (o instanceof OMVCoreObject){ //FOR THE REST OF OBJECTS
			remove ((OMVCoreObject)o);
		}
		else {
			System.out.println("Object not supported..");
		}
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
		else {
			System.out.println("Object not supported..");
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
	 * (use predefined values in Oyster2Query i.e. Local_Scope, 
	 * Auto_Scope, Selected_Scope)
	 * @param peerSet is the map of pairs (URI,OMVPeer object)
	 * representing the set of peers that will be included in the
	 * search. If not specified (i.e. peerSet=null) Oyster will 
	 * decide which peers to query according to its knowledge
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
		else {
			System.out.println("Object not supported..");
		}
		return OMVSet;
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
			Set<OMVMapping> OMVSet1 = processMappingsReply(qReply);
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
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#Person>")>0){
			qReply = localRegistry.returnQueryReplyGeneral(mOyster2.getLocalHostOntology(),query,Resource.DataResource);
			Set<Object> OMVSet1 = processGeneralOMVReply(qReply,2);
			OMVRet.addAll(OMVSet1);
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#Organisation>")>0){
			qReply = localRegistry.returnQueryReplyGeneral(mOyster2.getLocalHostOntology(),query,Resource.DataResource);
			Set<Object> OMVSet1 = processGeneralOMVReply(qReply,3);
			OMVRet.addAll(OMVSet1);
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#OntologyEngineeringTool>")>0){
			qReply = localRegistry.returnQueryReplyGeneral(mOyster2.getLocalHostOntology(),query,Resource.DataResource);
			Set<Object> OMVSet1 = processGeneralOMVReply(qReply,4);
			OMVRet.addAll(OMVSet1);
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#OntologyEngineeringMethodology>")>0){
			qReply = localRegistry.returnQueryReplyGeneral(mOyster2.getLocalHostOntology(),query,Resource.DataResource);
			Set<Object> OMVSet1 = processGeneralOMVReply(qReply,5);
			OMVRet.addAll(OMVSet1);
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#KnowledgeRepresentationParadigm>")>0){
			qReply = localRegistry.returnQueryReplyGeneral(mOyster2.getLocalHostOntology(),query,Resource.DataResource);
			Set<Object> OMVSet1 = processGeneralOMVReply(qReply,6);
			OMVRet.addAll(OMVSet1);
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#OntologyDomain>")>0){
			qReply = localRegistry.returnQueryReplyGeneral(mOyster2.getLocalHostOntology(),query,Resource.DataResource);
			Set<Object> OMVSet1 = processGeneralOMVReply(qReply,7);
			OMVRet.addAll(OMVSet1);
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#OntologyType>")>0){
			qReply = localRegistry.returnQueryReplyGeneral(mOyster2.getLocalHostOntology(),query,Resource.DataResource);
			Set<Object> OMVSet1 = processGeneralOMVReply(qReply,8);
			OMVRet.addAll(OMVSet1);
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#OntologyTask>")>0){
			qReply = localRegistry.returnQueryReplyGeneral(mOyster2.getLocalHostOntology(),query,Resource.DataResource);
			Set<Object> OMVSet1 = processGeneralOMVReply(qReply,9);
			OMVRet.addAll(OMVSet1);
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#OntologyLanguage>")>0){
			qReply = localRegistry.returnQueryReplyGeneral(mOyster2.getLocalHostOntology(),query,Resource.DataResource);
			Set<Object> OMVSet1 = processGeneralOMVReply(qReply,10);
			OMVRet.addAll(OMVSet1);
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#OntologySyntax>")>0){
			qReply = localRegistry.returnQueryReplyGeneral(mOyster2.getLocalHostOntology(),query,Resource.DataResource);
			Set<Object> OMVSet1 = processGeneralOMVReply(qReply,11);
			OMVRet.addAll(OMVSet1);
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#formalityLevel>")>0){
			qReply = localRegistry.returnQueryReplyGeneral(mOyster2.getLocalHostOntology(),query,Resource.DataResource);
			Set<Object> OMVSet1 = processGeneralOMVReply(qReply,12);
			OMVRet.addAll(OMVSet1);
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#LicenseModel>")>0){
			qReply = localRegistry.returnQueryReplyGeneral(mOyster2.getLocalHostOntology(),query,Resource.DataResource);
			Set<Object> OMVSet1 = processGeneralOMVReply(qReply,13);
			OMVRet.addAll(OMVSet1);
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#Location>")>0){
			qReply = localRegistry.returnQueryReplyGeneral(mOyster2.getLocalHostOntology(),query,Resource.DataResource);
			Set<Object> OMVSet1 = processGeneralOMVReply(qReply,14);
			OMVRet.addAll(OMVSet1);
		}
		return OMVRet;
	}
	
	/**
	 * Send a SPARQL query to Oyster2 registry and returns the 
	 * metadata entries that are part of the result within a 
	 * certain scope and (if specified)a certain set of Peers.
	 * @param sparqlQuery is the SPARQL query as a string.
	 * @param scope integer that represents the scope of the search
	 * (use predefined values in Oyster2Query i.e. Local_Scope, 
	 * Auto_Scope, Selected_Scope) 
	 * @param peerSet is the map of pairs (URI,OMVPeer object)
	 * representing the set of peers that will be included in the
	 * search. If not specified (i.e. peerSet=null) Oyster will 
	 * decide which peers to query according to its knowledge
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
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#Person>")>0){
			OMVPersonSetDistributed=new HashSet <OMVPerson>();
			OMVObject=3;
			
			searchManager.startSearch(null,query,peerList, false, false);
			waitReply(3);
			if (OMVPersonSetDistributed.size()>0){
				Iterator it = OMVPersonSetDistributed.iterator();
				try{
					while(it.hasNext()){
						Object omv = (Object)it.next();
						OMVRet.add(omv);
					}
					
				}catch(Exception ignore){
					//	-- ignore
				}
			}
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#Organisation>")>0){
			OMVOrganisationSetDistributed=new HashSet <OMVOrganisation>();
			OMVObject=4;
			
			searchManager.startSearch(null,query,peerList, false, false);
			waitReply(4);
			if (OMVOrganisationSetDistributed.size()>0){
				Iterator it = OMVOrganisationSetDistributed.iterator();
				try{
					while(it.hasNext()){
						Object omv = (Object)it.next();
						OMVRet.add(omv);
					}
					
				}catch(Exception ignore){
					//	-- ignore
				}
			}
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#OntologyEngineeringTool>")>0){
			OMVOETSetDistributed=new HashSet <OMVOntologyEngineeringTool>();
			OMVObject=5;
			
			searchManager.startSearch(null,query,peerList, false, false);
			waitReply(5);
			if (OMVOETSetDistributed.size()>0){
				Iterator it = OMVOETSetDistributed.iterator();
				try{
					while(it.hasNext()){
						Object omv = (Object)it.next();
						OMVRet.add(omv);
					}
					
				}catch(Exception ignore){
					//	-- ignore
				}
			}
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#OntologyEngineeringMethodology>")>0){
			OMVOEMSetDistributed=new HashSet <OMVOntologyEngineeringMethodology>();
			OMVObject=6;
			
			searchManager.startSearch(null,query,peerList, false, false);
			waitReply(6);
			if (OMVOEMSetDistributed.size()>0){
				Iterator it = OMVOEMSetDistributed.iterator();
				try{
					while(it.hasNext()){
						Object omv = (Object)it.next();
						OMVRet.add(omv);
					}
					
				}catch(Exception ignore){
					//	-- ignore
				}
			}
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#KnowledgeRepresentationParadigm>")>0){
			OMVKRPSetDistributed=new HashSet <OMVKnowledgeRepresentationParadigm>();
			OMVObject=7;
			
			searchManager.startSearch(null,query,peerList, false, false);
			waitReply(7);
			if (OMVKRPSetDistributed.size()>0){
				Iterator it = OMVKRPSetDistributed.iterator();
				try{
					while(it.hasNext()){
						Object omv = (Object)it.next();
						OMVRet.add(omv);
					}
					
				}catch(Exception ignore){
					//	-- ignore
				}
			}
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#OntologyDomain>")>0){
			OMVODSetDistributed=new HashSet <OMVOntologyDomain>();
			OMVObject=8;
			
			searchManager.startSearch(null,query,peerList, false, false);
			waitReply(8);
			if (OMVODSetDistributed.size()>0){
				Iterator it = OMVODSetDistributed.iterator();
				try{
					while(it.hasNext()){
						Object omv = (Object)it.next();
						OMVRet.add(omv);
					}
					
				}catch(Exception ignore){
					//	-- ignore
				}
			}
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#OntologyType>")>0){
			OMVOTSetDistributed=new HashSet <OMVOntologyType>();
			OMVObject=9;
			
			searchManager.startSearch(null,query,peerList, false, false);
			waitReply(9);
			if (OMVOTSetDistributed.size()>0){
				Iterator it = OMVOTSetDistributed.iterator();
				try{
					while(it.hasNext()){
						Object omv = (Object)it.next();
						OMVRet.add(omv);
					}
					
				}catch(Exception ignore){
					//	-- ignore
				}
			}
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#OntologyTask>")>0){
			OMVOTASetDistributed=new HashSet <OMVOntologyTask>();
			OMVObject=10;
			
			searchManager.startSearch(null,query,peerList, false, false);
			waitReply(10);
			if (OMVOTASetDistributed.size()>0){
				Iterator it = OMVOTASetDistributed.iterator();
				try{
					while(it.hasNext()){
						Object omv = (Object)it.next();
						OMVRet.add(omv);
					}
					
				}catch(Exception ignore){
					//	-- ignore
				}
			}
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#OntologyLanguage>")>0){
			OMVOLSetDistributed=new HashSet <OMVOntologyLanguage>();
			OMVObject=11;
			
			searchManager.startSearch(null,query,peerList, false, false);
			waitReply(11);
			if (OMVOLSetDistributed.size()>0){
				Iterator it = OMVOLSetDistributed.iterator();
				try{
					while(it.hasNext()){
						Object omv = (Object)it.next();
						OMVRet.add(omv);
					}
					
				}catch(Exception ignore){
					//	-- ignore
				}
			}
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#OntologySyntax>")>0){
			OMVOSSetDistributed=new HashSet <OMVOntologySyntax>();
			OMVObject=12;
			
			searchManager.startSearch(null,query,peerList, false, false);
			waitReply(12);
			if (OMVOSSetDistributed.size()>0){
				Iterator it = OMVOSSetDistributed.iterator();
				try{
					while(it.hasNext()){
						Object omv = (Object)it.next();
						OMVRet.add(omv);
					}
					
				}catch(Exception ignore){
					//	-- ignore
				}
			}
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#formalityLevel>")>0){
			OMVFLSetDistributed=new HashSet <OMVFormalityLevel>();
			OMVObject=13;
			
			searchManager.startSearch(null,query,peerList, false, false);
			waitReply(13);
			if (OMVFLSetDistributed.size()>0){
				Iterator it = OMVFLSetDistributed.iterator();
				try{
					while(it.hasNext()){
						Object omv = (Object)it.next();
						OMVRet.add(omv);
					}
					
				}catch(Exception ignore){
					//	-- ignore
				}
			}
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#LicenseModel>")>0){
			OMVLMSetDistributed=new HashSet <OMVLicenseModel>();
			OMVObject=14;
			
			searchManager.startSearch(null,query,peerList, false, false);
			waitReply(14);
			if (OMVLMSetDistributed.size()>0){
				Iterator it = OMVLMSetDistributed.iterator();
				try{
					while(it.hasNext()){
						Object omv = (Object)it.next();
						OMVRet.add(omv);
					}
					
				}catch(Exception ignore){
					//	-- ignore
				}
			}
		}else if (sparqlQuery.indexOf("rdf:type <http://omv.ontoware.org/2005/05/ontology#Location>")>0){
			OMVLocationSetDistributed=new HashSet <OMVLocation>();
			OMVObject=15;
			
			searchManager.startSearch(null,query,peerList, false, false);
			waitReply(15);
			if (OMVLocationSetDistributed.size()>0){
				Iterator it = OMVLocationSetDistributed.iterator();
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

	//ONTOLOGY SPECIFIC METHODS
	/**
	 * Opens an ontology file, extracts the ontology metadata 
	 * and import it into Oyster2 registry. If the ontology
	 * does already exists, it is merged. 
	 * It is not allowed to use blank spaces in the path 
	 * of an ontology, since this string (the path of the ontology) 
	 * should be a normal url (java.net.URI). But you can 
	 * use %20 instead of a blank space
	 * @param URL is the path or URL of the ontology file.
	 * @return true/false if the import was successful
	 */
	public boolean importOntology(String URL)
	{
		boolean success=false;
		pList.clear();
		pList=IOntology.extractMetadata(URL);
		OntologyProperty prop = new OntologyProperty(Constants.URI, "");
		OntologyProperty prop2 = new OntologyProperty(Constants.resourceLocator, "");
		//OntologyProperty prop1 = new OntologyProperty(Constants.name, "");
		if (isPropertyIn(prop) && isPropertyIn(prop2))// && isPropertyIn(prop1))
			success=IOntology.addImportOntologyToRegistry(pList,0, null);
		return success;
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
	 * (use predefined values in Oyster2Query i.e. Local_Scope, 
	 * Auto_Scope, Selected_Scope)
	 * @param peerSet is the map of pairs (URI,OMVPeer object)
	 * representing the set of peers that will be included in the
	 * search. If not specified (i.e. peerSet=null) Oyster will 
	 * decide which peers to query according to its knowledge 
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
	 * (use predefined values in Oyster2Query i.e. Local_Scope, 
	 * Auto_Scope, Selected_Scope)
	 * @param peerSet is the map of pairs (URI,OMVPeer object)
	 * representing the set of peers that will be included in the
	 * search. If not specified (i.e. peerSet=null) Oyster will 
	 * decide which peers to query according to its knowledge
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
	 * (use predefined values in Oyster2Query i.e. Local_Scope, 
	 * Auto_Scope, Selected_Scope)
	 * @param peerSet is the map of pairs (URI,OMVPeer object)
	 * representing the set of peers that will be included in the
	 * search. If not specified (i.e. peerSet=null) Oyster will 
	 * decide which peers to query according to its knowledge 
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

	//PEER SPECIFIC METHODS
	/**
	 * Retrieves the peers discovered in Oyster2 registry.
	 * @return The set of OMVPeer objects representing the
	 * peers discovered by Oyster2.
	 */
	
	public Map<String,OMVPeer> getPeers()
	{
		AdvertInformer mInformer = mOyster2.getLocalAdvertInformer();
		List peers = mInformer.getPeerList(mOyster2.getLocalHostOntology());
		Map<String, OMVPeer> OMVPeerSet = processPeersReply(peers);
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
		//Collection ontologySet = mInformer.getOntologyDoc(mOyster2.getLocalHostOntology(),peerIndiv);
		//TEST
		String IP = mInformer.getPeerIP(mOyster2.getLocalHostOntology(), peerIndiv); 
		Ontology registry=mInformer.openRemoteRegistry(IP);
		Collection ontologySet = mInformer.getOntologyDoc(registry,peerIndiv);
		//END TEST
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
			e.printStackTrace();
		}
		return OMVSet;
	}

	//MAPPING SPECIFIC METHODS
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
		Set<OMVMapping> OMVSet = processMappingsReply(qReply);
		return OMVSet;
	}

	/**
	 * Search Oyster2 registry to retrieve all available mappings
	 * metadata entries within a certain scope and (if specified )
	 * a certain set of Peers.
	 * @param scope integer that represents the scope of the search
	 * (use predefined values in Oyster2Query i.e. Local_Scope, 
	 * Auto_Scope, Selected_Scope)
	 * @param peerSet is the map of pairs (URI,OMVPeer object)
	 * representing the set of peers that will be included in the
	 * search. If not specified (i.e. peerSet=null) Oyster will 
	 * decide which peers to query according to its knowledge
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

	//CHANGE SPECIFIC METHODS
	/**
	 * Search Oyster2 registry to retrieve all available changes
	 * for a specific ontology in historical order, starting
	 * with the specified change. 
	 * @param o is the specified ontology 
	 * @param fromChange is the starting point in the change history.
	 * If not specified (i.e. fromChange=null) this method returns the
	 * complete history
	 * @return The list of OMVChanges objects representing the
	 * ontology changes.
	 */
	public List<OMVChange> getChanges(OMVOntology o, String fromChange){
		ChangeManagement cMgmt= new ChangeManagement();
		return cMgmt.getTrackedChanges(o, null, fromChange);
	}
	
	/**
	 * Search Oyster2 registry to retrieve changes
	 * of specific kind for a specific ontology. 
	 * @param o is the specified ontology. 
	 * @param c is the kind of ontology change Use predefined classes of the 
	 * API i.e. new OMVEntityChange().
	 * @param fromChange is the starting point in the change history.
	 * If not specified (i.e. fromChange=null) this method returns the
	 * complete history
	 * @return The set of OMVChanges objects representing the
	 * ontology changes.
	 */
	public Set<OMVChange> getChanges(OMVOntology o, OMVChange c, String fromChange){
		ChangeManagement cMgmt= new ChangeManagement();
		return cMgmt.getTrackedChanges(o,c, null, fromChange);
	}

	/**
	 * Search Oyster2 registry to retrieve all available changes
	 * for a specific ontology without any order 
	 * @param o is the specified ontology 
	 * @return The set of OMVChanges objects representing the
	 * ontology changes.
	 */
	public Set<OMVChange> getTrackedChangesSet(OMVOntology o){
		ChangeManagement cMgmt= new ChangeManagement();
		return cMgmt.getTrackedChangesSet(o, null);
	}
	
	/**
	 * Search Oyster2 registry to retrieve all ontologies with
	 * change history. 
	 * @return The set of OMVOntology objects
	 */
	public Set<OMVOntology> getOntologiesWithChanges(){
		ChangeManagement cMgmt= new ChangeManagement();
		return cMgmt.getTrackedOntologies();
	}
	
	/**
	 * Gets the URI of the last change succesfully
	 * registered in Oyster. This URI is in memory, which is 
	 * faster than the similar method getLastChangeIdFromLog,
	 * but if Oyster is restarted or in another session this
	 * information is lost
	 * @return The string of the last change registered
	 */
	public String getLastChangeId(){
		return lastChange;
	}
	
	/**
	 * Gets the set of atomic changes for a specific entity
	 * change
	 * @param c is the entity change for which for which we
	 * want to search atomic changes
	 * @return The set of atomic changes 
	 */
	public Set<OMVAtomicChange> getAtomicChanges(OMVEntityChange c){
		ChangeManagement cMgmt= new ChangeManagement();
		return cMgmt.getAtomicChanges(c);
	}
	
	/**
	 * Gets the entity associated to a specific change
	 * @param changeURI is the uri of the change for which for which we
	 * want to search the related entity
	 * @return The uri of the related entity 
	 */
	public String getRelatedEntity(String changeURI){
		ChangeManagement cMgmt= new ChangeManagement();
		return cMgmt.getRelatedEntity(changeURI);
	}

	/**
	 * Specify that the changes of the specified ontology
	 * should be logged
	 * @param o is the OMVOntology to track
	 */
	public void startTracking(OMVOntology o){
		ChangeManagement cMgmt= new ChangeManagement();
		cMgmt.startTracking(o);
	}
	
	/**
	 * Stops logging the changes of the specified ontology
	 * @param o is the OMVOntology to stop tracking
	 */
	public void stopTracking(OMVOntology o){
		ChangeManagement cMgmt= new ChangeManagement();
		cMgmt.stopTracking(o);
	}
		
	/**
	 * Get the ontologies that are being tracked by the
	 * local peer independently if changes are already available
	 * @return The set of the ontologies 
	 */
	public Set<OMVOntology> getOntologiesTrackedByPeer(){
		ChangeManagement cMgmt= new ChangeManagement();
		return cMgmt.getOntologiesTrackedByPeer();
	}
	
	/**
	 * Verify if an ontology has been registered to track
	 * its changes
	 * @param o is the OMVOntology to check
	 */
	public boolean isTracked(OMVOntology o){
		ChangeManagement cMgmt= new ChangeManagement();
		return cMgmt.isTracked(o);
	}
	
	/**
	 * Gets the URI of the last change registered
	 * for this ontology in the log
	 * @param o is the ontology from which we want to
	 * get the last change URI. 
	 * @return The string of the last change registered
	 */
	public String getLastChangeIdFromLog(OMVOntology o){
		ChangeManagement cMgmt= new ChangeManagement();
		return cMgmt.getLastChangeIdFromLog(o, null);
	}
	
	/**
	 * Gets the URI of all registered changes
	 * @param o is the ontology from which we want to
	 * get all changes URI. 
	 * @return The set of strings of the changes URIs
	 */
	public Set<String> getChangesIds(OMVOntology o){
		ChangeManagement cMgmt= new ChangeManagement();
		return cMgmt.getChangesIds(o, null);
	}
	
	/**
	 * Gets the change object given the change URI
	 * @param changeID is the change URI
	 * @return The OMVChange object
	 */
	public OMVChange getChange(String changeId){
		ChangeManagement cMgmt= new ChangeManagement();
		return cMgmt.getChange(changeId, null);
	}
	
	/**
	 * Force to start the syncrhonization of changes
	 * with all known peers. A new thread is created to 
	 * do the job
	 */
	public void syncrhonizeChangesWithKnownPeersNow(){
		RunnableSynchronization sync = new RunnableSynchronization();
		Thread syncThread = new Thread(sync,"ChangesSynchronizer");
		syncThread.setDaemon(true);
		syncThread.start();
	}
	
	/**
	 * If it this peer was configured as simple peer the discovery process
	 * can be started/stoped later on. If this is a normal peer this call
	 * is ignored
	 */
	public void startDiscoveryComponent(){
		if (mOyster2.getIsSimple()){
			System.out.println("Starting Exchange Initiator...");
			mExchangeInitiator = new ExchangeInitiator();
			mExchangeInitiatorThread = new Thread(mExchangeInitiator,"ExchangeInitiator");
			mExchangeInitiatorThread.setDaemon(true);
			mExchangeInitiatorThread.start();
		}
	}
	
	/**
	 * If it this peer was configured as simple peer the discovery process
	 * can be started/stoped later on. If this is a normal peer this call
	 * is ignored
	 */
	public void stopDiscoveryComponent(){
		if (mOyster2.getIsSimple()){
			System.out.println("Stopping Exchange Initiator...");
			if (this.mExchangeInitiator!=null) {
				this.mExchangeInitiator.shutdown();
				this.mExchangeInitiator=null;
			}
			if (this.mExchangeInitiatorThread!=null) {
				if (this.mExchangeInitiatorThread.isAlive()){
					this.mExchangeInitiatorThread.interrupt();
				}
				this.mExchangeInitiatorThread=null;
			}
		}
	}
	
	//WORKFLOW METHODS
	
	/**
	 * Submit a change to be approved
	 * @param changeURI is the URI of the change editor
	 * sends to be approved
	 * @param p is the editor submitting the change
	 */
	public void submitToBeApproved(String changeURI, OMVPerson p){
		if (mOyster2.getWorkflowSupport()){
			WorkflowManagement wMgmt= new WorkflowManagement();
			wMgmt.submitToBeApproved(changeURI, p, null);
		}else
			System.out.println("Wofkflow support is not enabled");
	}
	/**
	 * Approve a change.
	 * @param changeURI is the URI of the approved change
	 * @param p is the editor approving the change
	 */
	public void submitToApproved(String changeURI, OMVPerson p){
		if (mOyster2.getWorkflowSupport()){
			WorkflowManagement wMgmt= new WorkflowManagement();
			wMgmt.submitToApproved(changeURI, p, null);
		}else
			System.out.println("Wofkflow support is not enabled");
	}
	/**
	 * Submit a change to be deleted
	 * @param changeURI is the URI of the change editor
	 * sends to be deleted
	 * @param p is the editor submitting the change
	 */
	public void submitToBeDeleted(String changeURI, OMVPerson p){
		if (mOyster2.getWorkflowSupport()){
			WorkflowManagement wMgmt= new WorkflowManagement();
			wMgmt.submitToBeDeleted(changeURI, p, null);
		}else
			System.out.println("Wofkflow support is not enabled");
	}
	/**
	 * Rejects a change from approved state back to be approved
	 * @param changeURI is the URI of the change editor
	 * rejects
	 * @param p is the editor rejecting the change
	 */
	public void rejectToBeApproved(String changeURI, OMVPerson p){
		if (mOyster2.getWorkflowSupport()){
			WorkflowManagement wMgmt= new WorkflowManagement();
			wMgmt.rejectToBeApproved(changeURI, p, null);
		}else
			System.out.println("Wofkflow support is not enabled");
	}
	/**
	 * Reject a change from to be deleted state back to 
	 * approved
	 * @param changeURI is the URI of the change editor
	 * rejects
	 * @param p is the editor rejecting the change
	 */
	public void rejectToApproved(String changeURI, OMVPerson p){
		if (mOyster2.getWorkflowSupport()){
			WorkflowManagement wMgmt= new WorkflowManagement();
			wMgmt.rejectToApproved(changeURI, p, null);
		}else
			System.out.println("Wofkflow support is not enabled");
	}
	/**
	 * Reject a change from to be approved state 
	 * back to draft
	 * @param changeURI is the URI of the change editor
	 * rejects
	 * @param p is the editor rejecting the change
	 */
	public void rejectToDraft(String changeURI, OMVPerson p){
		if (mOyster2.getWorkflowSupport()){
			WorkflowManagement wMgmt= new WorkflowManagement();
			wMgmt.rejectToDraft(changeURI, p, null);
		}else
			System.out.println("Wofkflow support is not enabled");
	}
	/**
	 * Publish an ontology
	 * @param fromPublicVersion is the URI of the previous version
	 * of the ontology
	 * @param toPublicVersion is the URI of the next version
	 * of the ontology
	 * @param p is the editor publising the ontology
	 */
	public void publish(OMVOntology fromPublicVersion, OMVOntology toPublicVersion, OMVPerson p){
		if (mOyster2.getWorkflowSupport()){
			WorkflowManagement wMgmt= new WorkflowManagement();
			wMgmt.publish(fromPublicVersion, toPublicVersion, p);
		}else
			System.out.println("Wofkflow support is not enabled");
	}
	/**
	 * Removes all the history of workflow in Oyster for the
	 * specified ontology
	 * @param o is the ontology for which we want to remove
	 * workflow history
	 */
	public void removeWorkflowHistory(OMVOntology o){
		if (mOyster2.getWorkflowSupport()){
			WorkflowManagement wMgmt= new WorkflowManagement();
			wMgmt.removeWorkflowHistory(o);
		}else
			System.out.println("Wofkflow support is not enabled");
	}
	/**
	 * Gets the history of entity actions applied to the ontology
	 * @param o is the ontology for which we want to get the history
	 * @param fromChange is the starting point in the change history.
	 * If not specified (i.e. fromChange=null) this method returns the
	 * complete history. 
	 * @return the sorted list of entity actions.
	 */
	public List<Action> getEntityActionsHistory(OMVOntology o, String fromChange){
		if (mOyster2.getWorkflowSupport()){
			WorkflowManagement wMgmt= new WorkflowManagement();
			return wMgmt.getEntityActionsHistory(o, null, fromChange);
		}else
			System.out.println("Wofkflow support is not enabled");
		return null;
	}
	/**
	 * Gets the last ontology action applied to the ontology
	 * @param o is the ontology for which we want to get the last action
	 * @return the ontology action.
	 */
	public Action getOntologyAction(OMVOntology o){
		if (mOyster2.getWorkflowSupport()){
			WorkflowManagement wMgmt= new WorkflowManagement();
			return wMgmt.getOntologyAction(o);
		}else
			System.out.println("Wofkflow support is not enabled");
		return null;
	}
	/**
	 * Gets the ontology state
	 * @param o is the ontology for which we want to get its state
	 * @return the ontology state.
	 */
	public String getOntologyState(OMVOntology o){
		if (mOyster2.getWorkflowSupport()){
			WorkflowManagement wMgmt= new WorkflowManagement();
			return wMgmt.getOntologyState(o);
		}else
			System.out.println("Wofkflow support is not enabled");
		return null;
	}
	/**
	 * Gets the entity state
	 * @param o is the ontology for which we want to get the 
	 * entity state
	 * @param entityURI is the URI of the target entity
	 * @return the entity state.
	 */
	public String getEntityState(OMVOntology o,String entityURI){
		if (mOyster2.getWorkflowSupport()){
			ChangeManagement cMgmt= new ChangeManagement();
			return cMgmt.getEntityState(o,entityURI);
		}else
			System.out.println("Wofkflow support is not enabled");
		return null;
	}
	
	//REAL IMPLEMENTATIONS OF SOME PUBLIC METHODS
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
			IOntology.addImportOntologyToRegistry(pList,5, null);
		else
			System.out.println("URI, resourceLocator & name properties should not be empty");
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
			IOntology.addConceptToRegistry(5,pList,13, null);
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
			IOntology.addImportOntologyToRegistry(pList,2, null);
		else
			System.out.println("URI, resourceLocator & name properties should not be empty");
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
			IOntology.addConceptToRegistry(2,pList,13, null);
	}
	
	/**
	 * Removes an existing mapping in Oyster2 registry
	 * @param o is the OMVMapping object representing the
	 * mapping that will be removed.
	 */
	private void replace(OMVCoreObject o)
	{
		int concept=-1;
		pList.clear();
		if (o instanceof OMVPerson) {pList=OMVProperties.getPropertiesPerson((OMVPerson)o);concept=0;}
		else if (o instanceof OMVOrganisation) {pList=OMVProperties.getPropertiesOrganisation((OMVOrganisation)o);concept=1;}
		else if (o instanceof OMVFormalityLevel) {pList=OMVProperties.getPropertiesFormalityLevel((OMVFormalityLevel)o);concept=10;}
		else if (o instanceof OMVKnowledgeRepresentationParadigm) {pList=OMVProperties.getPropertiesKRParadigm((OMVKnowledgeRepresentationParadigm)o);concept=4;}
		else if (o instanceof OMVLicenseModel) {pList=OMVProperties.getPropertiesLicense((OMVLicenseModel)o);concept=11;}
		else if (o instanceof OMVOntologyDomain) {pList=OMVProperties.getPropertiesOntologyDomain((OMVOntologyDomain)o);concept=5;}
		else if (o instanceof OMVOntologyEngineeringMethodology) {pList=OMVProperties.getPropertiesOntologyEngineeringMethodology((OMVOntologyEngineeringMethodology)o);concept=3;}
		else if (o instanceof OMVOntologyEngineeringTool) {pList=OMVProperties.getPropertiesOntologyEngineeringTool((OMVOntologyEngineeringTool)o);concept=2;}
		else if (o instanceof OMVOntologyLanguage) {pList=OMVProperties.getPropertiesOntologyLanguage((OMVOntologyLanguage)o);concept=8;}
		else if (o instanceof OMVOntologySyntax){ pList=OMVProperties.getPropertiesOntologySyntax((OMVOntologySyntax)o);concept=9;}
		else if (o instanceof OMVOntologyTask) {pList=OMVProperties.getPropertiesOntologyTask((OMVOntologyTask)o);concept=7;}
		else if (o instanceof OMVOntologyType) {pList=OMVProperties.getPropertiesOntologyType((OMVOntologyType)o);concept=6;}
		if (concept>=0)
			IOntology.addConceptToRegistry(2,pList,concept, null);
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
		OntologyProperty prop2 = new OntologyProperty(Constants.resourceLocator, "");
		//OntologyProperty prop1 = new OntologyProperty(Constants.name, "");
		if (isPropertyIn(prop) && isPropertyIn(prop2))
			IOntology.addImportOntologyToRegistry(pList,4, null);
		else
			System.out.println("URI & resourceLocator properties should not be empty");
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
			IOntology.addConceptToRegistry(4,pList,13, null);
	}
	
	/**
	 * Removes an existing mapping in Oyster2 registry
	 * @param o is the OMVMapping object representing the
	 * mapping that will be removed.
	 */
	private void remove(OMVCoreObject o)
	{
		int concept=-1;
		pList.clear();
		if (o instanceof OMVPerson) {pList=OMVProperties.getPropertiesPerson((OMVPerson)o);concept=0;}
		else if (o instanceof OMVOrganisation) {pList=OMVProperties.getPropertiesOrganisation((OMVOrganisation)o);concept=1;}
		else if (o instanceof OMVFormalityLevel) {pList=OMVProperties.getPropertiesFormalityLevel((OMVFormalityLevel)o);concept=10;}
		else if (o instanceof OMVKnowledgeRepresentationParadigm) {pList=OMVProperties.getPropertiesKRParadigm((OMVKnowledgeRepresentationParadigm)o);concept=4;}
		else if (o instanceof OMVLicenseModel) {pList=OMVProperties.getPropertiesLicense((OMVLicenseModel)o);concept=11;}
		else if (o instanceof OMVOntologyDomain) {pList=OMVProperties.getPropertiesOntologyDomain((OMVOntologyDomain)o);concept=5;}
		else if (o instanceof OMVOntologyEngineeringMethodology) {pList=OMVProperties.getPropertiesOntologyEngineeringMethodology((OMVOntologyEngineeringMethodology)o);concept=3;}
		else if (o instanceof OMVOntologyEngineeringTool) {pList=OMVProperties.getPropertiesOntologyEngineeringTool((OMVOntologyEngineeringTool)o);concept=2;}
		else if (o instanceof OMVOntologyLanguage) {pList=OMVProperties.getPropertiesOntologyLanguage((OMVOntologyLanguage)o);concept=8;}
		else if (o instanceof OMVOntologySyntax){ pList=OMVProperties.getPropertiesOntologySyntax((OMVOntologySyntax)o);concept=9;}
		else if (o instanceof OMVOntologyTask) {pList=OMVProperties.getPropertiesOntologyTask((OMVOntologyTask)o);concept=7;}
		else if (o instanceof OMVOntologyType) {pList=OMVProperties.getPropertiesOntologyType((OMVOntologyType)o);concept=6;}
		if (concept>=0)
			IOntology.addConceptToRegistry(4,pList,concept, null);
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
		Set<OMVMapping> OMVSet = processMappingsReply(qReply);
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
		if (queryReply==null) return OMVSet;
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
			e.printStackTrace();
		}
		return OMVSet;
	}
	
	private Map<String, OMVPeer> processPeersReply(List peers){
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
			e.printStackTrace();
		}
		return OMVPeerMap;
	}
	
	private Set<OMVMapping> processMappingsReply(QueryReply queryReply){
		Set<OMVMapping> OMVSet= new HashSet <OMVMapping>();
		if (queryReply==null) return OMVSet;
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
			e.printStackTrace();
		}
		return OMVSet;
	}
	
	private Set<Object> processGeneralOMVReply(QueryReply queryReply, int which){
		Set<Object> OMVSet= new HashSet <Object>();
		if (queryReply==null) return OMVSet;
		ontologySearch = queryReply.getOntology();
		Collection entrySet = queryReply.getResourceSet();
		try{
			Iterator it = entrySet.iterator();
			while(it.hasNext()){
				final Resource  entry =(Resource) it.next();
				final Individual oIndividual = (Individual)entry.getEntity();
				
				if (which==1){
					OMVOntology mainReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(oIndividual, "ontology",ontologySearch);//(OMVOntology)processIndividual(oIndividual, "ontology");
					if (mainReply!=null){
						if (mainReply.getName()!=null && mainReply.getURI()!=null)
							OMVSet.add(mainReply);
						mainReply=null;
					}
				}else if (which==2){
					OMVPerson mainReply=(OMVPerson)ProcessOMVIndividuals.processIndividual(oIndividual, "person", ontologySearch);//(OMVOntology)processIndividual(oIndividual, "ontology");
					if (mainReply!=null){
						if (mainReply.getFirstName()!=null && mainReply.getLastName()!=null)
							OMVSet.add(mainReply);
						mainReply=null;
					}
				}else if (which==3){
					OMVOrganisation mainReply=(OMVOrganisation)ProcessOMVIndividuals.processIndividual(oIndividual, "organisation", ontologySearch);//(OMVOntology)processIndividual(oIndividual, "ontology");
					if (mainReply!=null){
						if (mainReply.getName()!=null)
							OMVSet.add(mainReply);
						mainReply=null;
					}
				}else if (which==4){
					OMVOntologyEngineeringTool mainReply=(OMVOntologyEngineeringTool)ProcessOMVIndividuals.processIndividual(oIndividual, "ontoEngTool", ontologySearch);
					if (mainReply!=null){
						if (mainReply.getName()!=null)
							OMVSet.add(mainReply);
						mainReply=null;
					}
				}else if (which==5){
					OMVOntologyEngineeringMethodology mainReply=(OMVOntologyEngineeringMethodology)ProcessOMVIndividuals.processIndividual(oIndividual, "ontoEngMet", ontologySearch);
					if (mainReply!=null){
						if (mainReply.getName()!=null)
							OMVSet.add(mainReply);
						mainReply=null;
					}
				}else if (which==6){
					OMVKnowledgeRepresentationParadigm mainReply=(OMVKnowledgeRepresentationParadigm)ProcessOMVIndividuals.processIndividual(oIndividual, "krParadigm", ontologySearch);
					if (mainReply!=null){
						if (mainReply.getName()!=null)
							OMVSet.add(mainReply);
						mainReply=null;
					}
				}else if (which==7){
					OMVOntologyDomain mainReply=(OMVOntologyDomain)ProcessOMVIndividuals.processIndividual(oIndividual, "oDomain", ontologySearch);
					if (mainReply!=null){
						if (mainReply.getURI()!=null)
							OMVSet.add(mainReply);
						mainReply=null;
					}
				}else if (which==8){
					OMVOntologyType mainReply=(OMVOntologyType)ProcessOMVIndividuals.processIndividual(oIndividual, "oType", ontologySearch);
					if (mainReply!=null){
						if (mainReply.getName()!=null)
							OMVSet.add(mainReply);
						mainReply=null;
					}
				}else if (which==9){
					OMVOntologyTask mainReply=(OMVOntologyTask)ProcessOMVIndividuals.processIndividual(oIndividual, "oTask", ontologySearch);
					if (mainReply!=null){
						if (mainReply.getName()!=null)
							OMVSet.add(mainReply);
						mainReply=null;
					}
				}else if (which==10){
					OMVOntologyLanguage mainReply=(OMVOntologyLanguage)ProcessOMVIndividuals.processIndividual(oIndividual, "oLanguage", ontologySearch);
					if (mainReply!=null){
						if (mainReply.getName()!=null)
							OMVSet.add(mainReply);
						mainReply=null;
					}
				}else if (which==11){
					OMVOntologySyntax mainReply=(OMVOntologySyntax)ProcessOMVIndividuals.processIndividual(oIndividual, "oSyntax", ontologySearch);
					if (mainReply!=null){
						if (mainReply.getName()!=null)
							OMVSet.add(mainReply);
						mainReply=null;
					}
				}else if (which==12){
					OMVFormalityLevel mainReply=(OMVFormalityLevel)ProcessOMVIndividuals.processIndividual(oIndividual, "fLevel", ontologySearch);
					if (mainReply!=null){
						if (mainReply.getName()!=null)
							OMVSet.add(mainReply);
						mainReply=null;
					}
				}else if (which==13){
					OMVLicenseModel mainReply=(OMVLicenseModel)ProcessOMVIndividuals.processIndividual(oIndividual, "lModel", ontologySearch);
					if (mainReply!=null){
						if (mainReply.getName()!=null)
							OMVSet.add(mainReply);
						mainReply=null;
					}
				}else if (which==14){
					OMVLocation mainReply=(OMVLocation)ProcessOMVIndividuals.processIndividual(oIndividual, "location", ontologySearch);
					if (mainReply!=null){
						if (mainReply.getStreet()!=null)
							OMVSet.add(mainReply);
						mainReply=null;
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return OMVSet;
	}
	
	// MANAGE DISTRIBUTED QUERY
	static public synchronized void addQueryReply(QueryReply qReplyDist){
		mOyster2.getLogger().info("recieving answers...");
		if (OMVObject<20)
			qReplyDistributedSet.add(qReplyDist);
	}
	
	static synchronized void noWaitMore(){
		waitMore=false;
	}
			
	private synchronized void waitReply(int which){
		waitMore=true;
		long initialTime=System.currentTimeMillis();
		long currentTime=System.currentTimeMillis();
		mOyster2.getLogger().info("initTime: "+initialTime);
		while (waitMore){
			try {
				mOyster2.getLogger().info("now will wait..."+mOyster2.getQueryTimeOut());
				Thread.sleep(mOyster2.getQueryTimeOut());
				mOyster2.getLogger().info("timeout..");
				waitMore=false;
			} catch (InterruptedException e) {
				mOyster2.getLogger().info("interrupted (finished)...");
				waitMore=false;
			}
		}
		currentTime=System.currentTimeMillis();
		mOyster2.getLogger().info("currentTime: "+currentTime);
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
				Set<OMVMapping> OMVSetDist=processMappingsReply((QueryReply)ir.next());
				OMVMappingSetDistributed.addAll(OMVSetDist);
			}
			mergeDuplicates(which);
		}else if (which>=3 && which <=15){
			Set<Object> OMVSetDistFinal = new HashSet<Object>();
			Iterator ir=qReplyDistributedSet.iterator();
			while (ir.hasNext()){
				Set<Object> OMVSetDist=processGeneralOMVReply((QueryReply)ir.next(),which-1);
				OMVSetDistFinal.addAll(OMVSetDist);
			}
			if (which==3){
				Iterator it=OMVSetDistFinal.iterator();
				while (it.hasNext()){
					OMVPerson t=(OMVPerson)it.next();
					OMVPersonSetDistributed.add(t);
				}
			}else if (which==4){
				Iterator it=OMVSetDistFinal.iterator();
				while (it.hasNext()){
					OMVOrganisation t=(OMVOrganisation)it.next();
					OMVOrganisationSetDistributed.add(t);
				}
			}else if (which==5){
				Iterator it=OMVSetDistFinal.iterator();
				while (it.hasNext()){
					OMVOntologyEngineeringTool t=(OMVOntologyEngineeringTool)it.next();
					OMVOETSetDistributed.add(t);
				}
			}else if (which==6){
				Iterator it=OMVSetDistFinal.iterator();
				while (it.hasNext()){
					OMVOntologyEngineeringMethodology t=(OMVOntologyEngineeringMethodology)it.next();
					OMVOEMSetDistributed.add(t);
				}
			}else if (which==7){
				Iterator it=OMVSetDistFinal.iterator();
				while (it.hasNext()){
					OMVKnowledgeRepresentationParadigm t=(OMVKnowledgeRepresentationParadigm)it.next();
					OMVKRPSetDistributed.add(t);
				}
			}else if (which==8){
				Iterator it=OMVSetDistFinal.iterator();
				while (it.hasNext()){
					OMVOntologyDomain t=(OMVOntologyDomain)it.next();
					OMVODSetDistributed.add(t);
				}
			}else if (which==9){
				Iterator it=OMVSetDistFinal.iterator();
				while (it.hasNext()){
					OMVOntologyType t=(OMVOntologyType)it.next();
					OMVOTSetDistributed.add(t);
				}
			}else if (which==10){
				Iterator it=OMVSetDistFinal.iterator();
				while (it.hasNext()){
					OMVOntologyTask t=(OMVOntologyTask)it.next();
					OMVOTASetDistributed.add(t);
				}
			}else if (which==11){
				Iterator it=OMVSetDistFinal.iterator();
				while (it.hasNext()){
					OMVOntologyLanguage t=(OMVOntologyLanguage)it.next();
					OMVOLSetDistributed.add(t);
				}
			}else if (which==12){
				Iterator it=OMVSetDistFinal.iterator();
				while (it.hasNext()){
					OMVOntologySyntax t=(OMVOntologySyntax)it.next();
					OMVOSSetDistributed.add(t);
				}
			}else if (which==13){
				Iterator it=OMVSetDistFinal.iterator();
				while (it.hasNext()){
					OMVFormalityLevel t=(OMVFormalityLevel)it.next();
					OMVFLSetDistributed.add(t);
				}
			}else if (which==14){
				Iterator it=OMVSetDistFinal.iterator();
				while (it.hasNext()){
					OMVLicenseModel t=(OMVLicenseModel)it.next();
					OMVLMSetDistributed.add(t);
				}
			}else if (which==15){
				Iterator it=OMVSetDistFinal.iterator();
				while (it.hasNext()){
					OMVLocation t=(OMVLocation)it.next();
					OMVLocationSetDistributed.add(t);
				}
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
		}else if (which==2){
			Set<OMVMapping> t = new HashSet<OMVMapping>();
			t.addAll(processMappingDuplicates.mergeMappingDuplicates(OMVMappingSetDistributed));
			OMVMappingSetDistributed.clear();
			OMVMappingSetDistributed.addAll(t);
		}else if (which==3){
			Set<OMVPerson> t = new HashSet<OMVPerson>();
			t.addAll(processPersonDuplicates.mergePersonDuplicates(OMVPersonSetDistributed));
			OMVPersonSetDistributed.clear();
			OMVPersonSetDistributed.addAll(t);
		}else if (which==4){
			Set<OMVOrganisation> t = new HashSet<OMVOrganisation>();
			t.addAll(processOrganisationDuplicates.mergeOrganisationDuplicates(OMVOrganisationSetDistributed));
			OMVOrganisationSetDistributed.clear();
			OMVOrganisationSetDistributed.addAll(t);
		}else if (which==5){
			Set<OMVOntologyEngineeringTool> t = new HashSet<OMVOntologyEngineeringTool>();
			t.addAll(processOETDuplicates.mergeOETDuplicates(OMVOETSetDistributed));
			OMVOETSetDistributed.clear();
			OMVOETSetDistributed.addAll(t);
		}else if (which==6){
			Set<OMVOntologyEngineeringMethodology> t = new HashSet<OMVOntologyEngineeringMethodology>();
			t.addAll(processOEMDuplicates.mergeOEMDuplicates(OMVOEMSetDistributed));
			OMVOEMSetDistributed.clear();
			OMVOEMSetDistributed.addAll(t);
		}else if (which==7){
			Set<OMVKnowledgeRepresentationParadigm> t = new HashSet<OMVKnowledgeRepresentationParadigm>();
			t.addAll(processKRPDuplicates.mergeKRPDuplicates(OMVKRPSetDistributed));
			OMVKRPSetDistributed.clear();
			OMVKRPSetDistributed.addAll(t);
		}else if (which==8){
			Set<OMVOntologyDomain> t = new HashSet<OMVOntologyDomain>();
			t.addAll(processODDuplicates.mergeODDuplicates(OMVODSetDistributed));
			OMVODSetDistributed.clear();
			OMVODSetDistributed.addAll(t);
		}else if (which==9){
			Set<OMVOntologyType> t = new HashSet<OMVOntologyType>();
			t.addAll(processOTDuplicates.mergeOTDuplicates(OMVOTSetDistributed));
			OMVOTSetDistributed.clear();
			OMVOTSetDistributed.addAll(t);
		}else if (which==10){
			Set<OMVOntologyTask> t = new HashSet<OMVOntologyTask>();
			t.addAll(processOTADuplicates.mergeOTADuplicates(OMVOTASetDistributed));
			OMVOTASetDistributed.clear();
			OMVOTASetDistributed.addAll(t);
		}else if (which==11){
			Set<OMVOntologyLanguage> t = new HashSet<OMVOntologyLanguage>();
			t.addAll(processOLDuplicates.mergeOLDuplicates(OMVOLSetDistributed));
			OMVOLSetDistributed.clear();
			OMVOLSetDistributed.addAll(t);
		}else if (which==12){
			Set<OMVOntologySyntax> t = new HashSet<OMVOntologySyntax>();
			t.addAll(processOSDuplicates.mergeOSDuplicates(OMVOSSetDistributed));
			OMVOSSetDistributed.clear();
			OMVOSSetDistributed.addAll(t);
		}else if (which==13){
			Set<OMVFormalityLevel> t = new HashSet<OMVFormalityLevel>();
			t.addAll(processFLDuplicates.mergeFLDuplicates(OMVFLSetDistributed));
			OMVFLSetDistributed.clear();
			OMVFLSetDistributed.addAll(t);
		}else if (which==14){
			Set<OMVLicenseModel> t = new HashSet<OMVLicenseModel>();
			t.addAll(processLMDuplicates.mergeLMDuplicates(OMVLMSetDistributed));
			OMVLMSetDistributed.clear();
			OMVLMSetDistributed.addAll(t);
		}else if (which==15){
			Set<OMVLocation> t = new HashSet<OMVLocation>();
			t.addAll(processLocationDuplicates.mergeLocationDuplicates(OMVLocationSetDistributed));
			OMVLocationSetDistributed.clear();
			OMVLocationSetDistributed.addAll(t);
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
