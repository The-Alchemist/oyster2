package api;

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
import org.semanticweb.kaon2.api.Entity;
import org.semanticweb.kaon2.api.KAON2Exception;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Namespaces;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.owl.elements.DataProperty;
import org.semanticweb.kaon2.api.owl.elements.Individual;
import org.semanticweb.kaon2.api.owl.elements.OWLClass;
import org.semanticweb.kaon2.api.owl.elements.ObjectProperty;
import core.AdvertInformer;
import core.LocalExpertiseRegistry;
import core.QueryFormulator;
import oyster2.Condition;
import oyster2.Constants;
import oyster2.OntologyProperty;
import oyster2.ImportOntology;
import oyster2.Oyster2Factory;
import oyster2.Oyster2Query;
import oyster2.QueryReply;
import util.Resource;

/**
 * The class Oyster2Connection provides the API access methods to Oyster2 registry.
 * @author Raul Palma
 * @version 0.1, March 2007
 */
public class Oyster2Connection {
	static Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private ImportOntology IOntology= new ImportOntology();
	private List pList = new LinkedList();
	private LocalExpertiseRegistry localRegistry = mOyster2.getLocalExpertiseRegistry();
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
	private Ontology ontologySearch=null;
	private OMVPeer peerReply=null;
	private String peerNameID="";
	
	public Oyster2Connection()
    {
		
    }
	
	/**
	 * Register a new ontology into Oyster2 registry.
	 * @param o is the OMVOntology object representing the
	 * ontology that will be registered.
	 */
	public void register(OMVOntology o)
	{
		pList.clear();
		pList=getProperties(o);
		OntologyProperty prop = new OntologyProperty(Constants.URI, "");
		OntologyProperty prop1 = new OntologyProperty(Constants.name, "");
		if (isPropertyIn(prop) && isPropertyIn(prop1))
			IOntology.addImportOntologyToRegistry(pList,1);
	}
	
	/**
	 * Replaces an existing ontology in Oyster2 registry with
	 * the new ontology information provided. If the ontology
	 * does not exists, it is registered.
	 * @param o is the OMVOntology object representing the
	 * ontology that will be replaced.
	 */
	public void replace(OMVOntology o)
	{
		pList.clear();
		pList=getProperties(o);
		OntologyProperty prop = new OntologyProperty(Constants.URI, "");
		OntologyProperty prop1 = new OntologyProperty(Constants.name, "");
		if (isPropertyIn(prop) && isPropertyIn(prop1))
			IOntology.addImportOntologyToRegistry(pList,2);
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
	public Set<OMVOntology> search()
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
	 * metadata entries that fulfill certain conditions.
	 * @param o is a OMVOntology object that holds the conditions 
	 * that will be used for searching the registry.
	 * @return The set of OMVOntology objects representing the
	 * ontology metadata entries and that fulfill the conditions.
	 */
	public Set<OMVOntology> search(OMVOntology o)
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
	public Set<OMVOntology> search(String scope,Map<String,OMVPeer> peerSet)
	{ 
		return null;
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
	
	public Set<OMVOntology> search(OMVOntology o, String scope,Map<String,OMVPeer> peerSet)
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
	
	public Set<OMVOntology> search(Date fromDate)
	{ 
		DateFormat format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US);
		//String fromFomat = format.format(fromDate);
		Set<OMVOntology> OMVSetFiltered = new HashSet<OMVOntology>();
		Set<OMVOntology> OMVSet = search();
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
				processIndividual(ontologyIndiv, "ontology");
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
				processIndividual(oIndividual, "ontology");
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
	private void processIndividual(Individual ontoIndiv, String whichClass){
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
			else if (whichClass.equalsIgnoreCase("oReferences")) oReferencesReply = new OMVOntology();
			else if (whichClass.equalsIgnoreCase("location")) locationReply = new OMVLocation();
			else if (whichClass.equalsIgnoreCase("person")) personReply = new OMVPerson();
			else if (whichClass.equalsIgnoreCase("organisation")) organisationReply = new OMVOrganisation();
			
			Collection keySet = dataPropertyMap.keySet();
			Iterator keys = keySet.iterator();
			while(keys.hasNext()){
				String keyStr = keys.next().toString();
				DataProperty property = KAON2Manager.factory().dataProperty(keyStr);
				String	propertyValue = util.Utilities.getString(ontoIndiv.getDataPropertyValue(ontologySearch,property));
					
				if (whichClass.equalsIgnoreCase("ontology")) createOMVOntology(property.getURI(),propertyValue);
				else if (whichClass.equalsIgnoreCase("party")) createOMVParty(property.getURI(),propertyValue);
				else if (whichClass.equalsIgnoreCase("ontoEngTool")) createOMVOntologyEngineeringTool(property.getURI(),propertyValue);
				else if (whichClass.equalsIgnoreCase("ontoEngMet")) createOMVOntologyEngineeringMethodology(property.getURI(),propertyValue);
				else if (whichClass.equalsIgnoreCase("krParadigm")) createOMVKnowledgeRepresentationParadigm(property.getURI(),propertyValue);
				else if (whichClass.equalsIgnoreCase("oDomain")) createOMVOntologyDomain(property.getURI(),propertyValue);
				else if (whichClass.equalsIgnoreCase("oType")) createOMVOntologyType(property.getURI(),propertyValue);
				else if (whichClass.equalsIgnoreCase("oTask")) createOMVOntologyTask(property.getURI(),propertyValue);
				else if (whichClass.equalsIgnoreCase("oLanguage")) createOMVOntologyLanguage(property.getURI(),propertyValue);
				else if (whichClass.equalsIgnoreCase("oSyntax")) createOMVOntologySyntax(property.getURI(),propertyValue);
				else if (whichClass.equalsIgnoreCase("fLevel")) createOMVFormalityLevel(property.getURI(),propertyValue);
				else if (whichClass.equalsIgnoreCase("lModel")) createOMVLicenseModel(property.getURI(),propertyValue);
				else if (whichClass.equalsIgnoreCase("oReferences")) createOMVOntologyReferences(property.getURI(),propertyValue);
				else if (whichClass.equalsIgnoreCase("location")) createOMVLocation(property.getURI(),propertyValue);
				else if (whichClass.equalsIgnoreCase("person")) createOMVPerson(property.getURI(),propertyValue);
				else if (whichClass.equalsIgnoreCase("organisation")) createOMVOrganisation(property.getURI(),propertyValue);
				
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
						
					if (whichClass.equalsIgnoreCase("ontology")) createOMVOntology(property.getURI(),propertyValue.getURI());
					else if (whichClass.equalsIgnoreCase("party")) createOMVParty(property.getURI(),propertyValue.getURI());
					else if (whichClass.equalsIgnoreCase("ontoEngTool")) createOMVOntologyEngineeringTool(property.getURI(),propertyValue.getURI());
					else if (whichClass.equalsIgnoreCase("ontoEngMet")) createOMVOntologyEngineeringMethodology(property.getURI(),propertyValue.getURI());
					else if (whichClass.equalsIgnoreCase("krParadigm")) createOMVKnowledgeRepresentationParadigm(property.getURI(),propertyValue.getURI());
					else if (whichClass.equalsIgnoreCase("oDomain")) createOMVOntologyDomain(property.getURI(),propertyValue.getURI());
					else if (whichClass.equalsIgnoreCase("oType")) createOMVOntologyType(property.getURI(),propertyValue.getURI());
					else if (whichClass.equalsIgnoreCase("oTask")) createOMVOntologyTask(property.getURI(),propertyValue.getURI());
					else if (whichClass.equalsIgnoreCase("oLanguage")) createOMVOntologyLanguage(property.getURI(),propertyValue.getURI());
					else if (whichClass.equalsIgnoreCase("oSyntax")) createOMVOntologySyntax(property.getURI(),propertyValue.getURI());
					else if (whichClass.equalsIgnoreCase("fLevel")) createOMVFormalityLevel(property.getURI(),propertyValue.getURI());
					else if (whichClass.equalsIgnoreCase("lModel")) createOMVLicenseModel(property.getURI(),propertyValue.getURI());
					else if (whichClass.equalsIgnoreCase("oReferences")) createOMVOntologyReferences(property.getURI(),propertyValue.getURI());
					else if (whichClass.equalsIgnoreCase("location")) createOMVLocation(property.getURI(),propertyValue.getURI());
					else if (whichClass.equalsIgnoreCase("person")) createOMVPerson(property.getURI(),propertyValue.getURI());
					else if (whichClass.equalsIgnoreCase("organisation")) createOMVOrganisation(property.getURI(),propertyValue.getURI());
					
					//System.out.println("The property "+property.getURI()+" has value: "+propertyValue);	
				}	
			}
		}
	  }catch(Exception e){
			System.out.println(e.toString()+" Search Problem in processIndividual");
	  }
	}
	private void createOMVOntology(String URI, String value){
	  try{
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.URI)) {mainOntoReply.setURI(value);return;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {mainOntoReply.setName(value);return;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {mainOntoReply.setAcronym(value);return;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {mainOntoReply.setDescription(value);return;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) {mainOntoReply.setDocumentation(value);return;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.keywords)) {mainOntoReply.setKeywords(value);return;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.status)) {mainOntoReply.setStatus(value);return;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.creationDate)) {mainOntoReply.setCreationDate(value);return;}        //DateFormat df = DateFormat.getDateInstance();	
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.modificationDate)) {mainOntoReply.setModificationDate(value);return;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasContributor)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "party");
			processIndividual(oIndividual, "person");
			if (personReply!=null){
				if (partyReply!=null) {
					copyParty2Person();
				}
				mainOntoReply.addHasContributor(personReply);
				personReply = null;
			}
			else{
				processIndividual(oIndividual, "organisation");
				if (organisationReply!=null){
					if (partyReply!=null) {
						copyParty2Organisation();
					}
					mainOntoReply.addHasContributor(organisationReply);
					organisationReply = null;
				}
			}
			partyReply=null;
			return;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasCreator)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "party");
			processIndividual(oIndividual, "person");
			if (personReply!=null){
				if (partyReply!=null) {
					copyParty2Person();
				}
				mainOntoReply.addHasCreator(personReply);
				personReply = null;
			}
			else{
				processIndividual(oIndividual, "organisation");
				if (organisationReply!=null){
					if (partyReply!=null) {
						copyParty2Organisation();
					}
					mainOntoReply.addHasCreator(organisationReply);
					organisationReply = null;
				}
			}
			partyReply=null;
			return;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.usedOntologyEngineeringTool)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "ontoEngTool");
			if (ontoEngToolReply!=null) mainOntoReply.addUsedOntologyEngineeringTool(ontoEngToolReply);
			ontoEngToolReply = null;
			return;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.usedOntologyEngineeringMethodology)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "ontoEngMet");
			if (ontoEngMetReply!=null) mainOntoReply.addUsedOntologyEngineeringMethodology(ontoEngMetReply);
			ontoEngMetReply = null;
			return;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.usedKnowledgeRepresentationParadigm)){
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "krParadigm");
			if (krParadigmReply!=null) mainOntoReply.addUsedKnowledgeRepresentationParadigm(krParadigmReply);
			krParadigmReply = null;
			return;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasDomain)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "oDomain");
			if (oDomainReply==null) {
				oDomainReply = new OMVOntologyDomain();
				oDomainReply.setURI(value);
			}
			mainOntoReply.addHasDomain(oDomainReply);
			oDomainReply = null;
			return;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isOfType)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "oType");
			if (oTypeReply!=null) mainOntoReply.setIsOfType(oTypeReply);
			oTypeReply = null;
			return;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.naturalLanguage)) {mainOntoReply.setNaturalLanguage(value);return;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.designedForOntologyTask)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "oTask");
			if (oTaskReply!=null) mainOntoReply.addDesignedForOntologyTask(oTaskReply);
			oTaskReply = null;
			return;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasOntologyLanguage)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "oLanguage");
			if (oLanguageReply!=null) mainOntoReply.setHasOntologyLanguage(oLanguageReply);
			oLanguageReply = null;
			return;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasOntologySyntax)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "oSyntax");
			if (oSyntaxReply!=null) mainOntoReply.setHasOntologySyntax(oSyntaxReply);
			oSyntaxReply = null;
			return;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasFormalityLevel)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "fLevel");
			if (fLevelReply!=null) mainOntoReply.setHasFormalityLevel(fLevelReply);
			fLevelReply = null;
			return;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.resourceLocator)) {mainOntoReply.setResourceLocator(value);return;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.version)) {mainOntoReply.setVersion(value);return;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasLicense)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "lModel");
			if (lModelReply!=null) mainOntoReply.setHasLicense(lModelReply);
			lModelReply = null;
			return;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.useImports)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "oReferences");
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			mainOntoReply.addUseImports(oReferencesReply);
			oReferencesReply = null;
			return;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasPriorVersion)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "oReferences");
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			mainOntoReply.setHasPriorVersion(oReferencesReply);
			oReferencesReply = null;
			return;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isBackwardCompatibleWith)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "oReferences");
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			mainOntoReply.addIsBackwardCompatibleWith(oReferencesReply);
			oReferencesReply = null;
			return;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isIncompatibleWith)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "oReferences");
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			mainOntoReply.addIsIncompatibleWith(oReferencesReply);
			oReferencesReply = null;
			return;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.numClasses)) {mainOntoReply.setNumClasses(new Integer(value.substring(1, value.indexOf("\"", 2))));return;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.numProperties)) {mainOntoReply.setNumProperties(new Integer(value.substring(1, value.indexOf("\"", 2))));return;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.numIndividuals)) {mainOntoReply.setNumIndividuals(new Integer(value.substring(1, value.indexOf("\"", 2))));return;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.numAxioms)) {mainOntoReply.setNumAxioms(new Integer(value.substring(1, value.indexOf("\"", 2))));return;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.timeStamp)) {mainOntoReply.setTimeStamp(value);return;}
	  }catch(Exception e){
			System.out.println(e.toString()+" Search Problem in createOMVOntology");
	  }	
	}
	private void createOMVParty(String URI, String value){
	  try{
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isLocatedAt)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "location");
			if (locationReply!=null) partyReply.addIsLocatedAt(locationReply);
			locationReply = null;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developesOntologyEngineeringTool)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "ontoEngTool");
			if (ontoEngToolReply!=null) partyReply.addDevelopesOntologyEngineeringTool(ontoEngToolReply);
			ontoEngToolReply = null;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developesOntologyLanguage)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "oLanguage");
			if (oLanguageReply!=null) partyReply.addDevelopesOntologyLanguage(oLanguageReply);
			oLanguageReply = null;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developesOntologySyntax)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "oSyntax");
			if (oSyntaxReply!=null) partyReply.addDevelopesOntologySyntax(oSyntaxReply);
			oSyntaxReply = null;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.specifiesKnowledgeRepresentationParadigm)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "krParadigm");
			if (krParadigmReply!=null) partyReply.addSpecifiesKnowledgeRepresentationParadigm(krParadigmReply);
			krParadigmReply = null;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.definesOntologyType)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "oType");
			if (oTypeReply!=null) partyReply.addDefinesOntologyType(oTypeReply);
			oTypeReply = null;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developesOntologyEngineeringMethodology)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "ontoEngMet");
			if (ontoEngMetReply!=null) partyReply.addDevelopesOntologyEngineeringMethodology(ontoEngMetReply);
			ontoEngMetReply = null;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.specifiesLicense)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "lModel");
			if (lModelReply!=null) partyReply.addSpecifiesLicense(lModelReply);
			lModelReply = null;
		}
		/*if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasAffiliatedParty)) { //CHANGE IT LIKE OMVOntology
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "party");
			partyReply.addHasAffiliatedParty(partyReply);
			partyReply = null;
		}*/
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.createsOntology))  {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "oReferences");
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			partyReply.addCreatesOntology(oReferencesReply);
			oReferencesReply = null;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.contributesToOntology))  {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "oReferences");
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			partyReply.addContributesToOntology(oReferencesReply);
			oReferencesReply = null;
		}
	  }catch(Exception e){
			System.out.println(e.toString()+" Search Problem in createOMVParty");
	  }	
	}
	
	private void createOMVOntologyEngineeringTool(String URI, String value){
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) ontoEngToolReply.setName(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) ontoEngToolReply.setAcronym(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) ontoEngToolReply.setDescription(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) ontoEngToolReply.setDocumentation(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "party");
			if (partyReply!=null) ontoEngToolReply.addDevelopedBy(partyReply);
			partyReply = null;
		}
	}
	
	private void createOMVOntologyEngineeringMethodology(String URI, String value){
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) ontoEngMetReply.setName(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) ontoEngMetReply.setAcronym(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) ontoEngMetReply.setDescription(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) ontoEngMetReply.setDocumentation(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "party");
			if (partyReply!=null) ontoEngMetReply.addDevelopedBy(partyReply);
			partyReply = null;
		}
	}
	
	private void createOMVKnowledgeRepresentationParadigm(String URI, String value){
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) krParadigmReply.setName(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) krParadigmReply.setAcronym(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) krParadigmReply.setDescription(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) krParadigmReply.setDocumentation(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.specifiedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "party");
			if (partyReply!=null) krParadigmReply.addSpecifiedBy(partyReply);
			partyReply = null;
		}
	}
	
	private void createOMVOntologyDomain(String URI, String value){
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.URI)) oDomainReply.setURI(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) oDomainReply.setName(value);
	}
	
	private void createOMVOntologyType(String URI, String value){
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) oTypeReply.setName(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) oTypeReply.setAcronym(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) oTypeReply.setDescription(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) oTypeReply.setDocumentation(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.definedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "party");
			if (partyReply!=null) oTypeReply.addDefinedBy(partyReply);
			partyReply = null;
		}
	}
	
	private void createOMVOntologyTask(String URI, String value){
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) oTaskReply.setName(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) oTaskReply.setAcronym(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) oTaskReply.setDescription(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) oTaskReply.setDocumentation(value);
	}
	
	private void createOMVOntologyLanguage(String URI, String value){
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) oLanguageReply.setName(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) oLanguageReply.setAcronym(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) oLanguageReply.setDescription(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) oLanguageReply.setDocumentation(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "party");
			if (partyReply!=null) oLanguageReply.addDevelopedBy(partyReply);
			partyReply = null;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.supportsRepresentationParadigm)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "krParadigm");
			if (krParadigmReply!=null) oLanguageReply.addSupportsRepresentationParadigm(krParadigmReply);
			krParadigmReply = null;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasSyntax)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "oSyntax");
			if (oSyntaxReply!=null) oLanguageReply.addHasSyntax(oSyntaxReply);
			oSyntaxReply = null;
		}
	}
	
	private void createOMVOntologySyntax(String URI, String value){
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) oSyntaxReply.setName(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) oSyntaxReply.setAcronym(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) oSyntaxReply.setDescription(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) oSyntaxReply.setDocumentation(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "party");
			if (partyReply!=null) oSyntaxReply.addDevelopedBy(partyReply);
			partyReply = null;
		}
	}
	
	private void createOMVFormalityLevel(String URI, String value){
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) fLevelReply.setName(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) fLevelReply.setDescription(value);
	}
	
	private void createOMVLicenseModel(String URI, String value){
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) lModelReply.setName(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) lModelReply.setAcronym(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) lModelReply.setDescription(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) lModelReply.setDocumentation(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.specifiedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "party");
			if (partyReply!=null) lModelReply.addSpecifiedBy(partyReply);
			partyReply = null;
		}
	}
	
	private void createOMVLocation(String URI, String value){
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.state)) locationReply.setState(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.country)) locationReply.setCountry(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.city)) locationReply.setCity(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.street)) locationReply.setStreet(value);
	}
	
	private void createOMVPerson(String URI, String value){
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.lastName)) personReply.setLastName(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.firstName)) personReply.setFirstName(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.eMail)) personReply.setEMail(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.phoneNumber)) personReply.setPhoneNumber(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.faxNumber)) personReply.setFaxNumber(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isContactPerson)) {
			Individual oIndividual1 =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual1, "organisation");
			if (organisationReply!=null) personReply.addIsContactPerson(organisationReply);
			organisationReply = null;
		}
	}
	
	private void createOMVOrganisation(String URI, String value){
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) organisationReply.setName(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) organisationReply.setAcronym(value);
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasContactPerson)) {
			Individual oIndividual1 =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual1, "person");
			if (personReply!=null) organisationReply.addHasContactPerson(personReply);
			personReply = null;
		}
	}
	
	private void createOMVOntologyReferences(String URI, String value){
		try{
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.URI)) {oReferencesReply.setURI(value);return;}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {oReferencesReply.setName(value);return;}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {oReferencesReply.setAcronym(value);return;}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {oReferencesReply.setDescription(value);return;}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) {oReferencesReply.setDocumentation(value);return;}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.keywords)) {oReferencesReply.setKeywords(value);return;}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.status)) {oReferencesReply.setStatus(value);return;}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.creationDate)) {oReferencesReply.setCreationDate(value);return;}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.modificationDate)) {oReferencesReply.setModificationDate(value);return;}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasContributor)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				processIndividual(oIndividual, "person");
				if (personReply!=null){
					oReferencesReply.addHasContributor(personReply);
					personReply = null;
				}
				else{ 
					processIndividual(oIndividual, "organisation");
					if (organisationReply!=null){
						oReferencesReply.addHasContributor(organisationReply);
						organisationReply = null;}
				}
				return;
			}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasCreator)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				processIndividual(oIndividual, "person");
				if (personReply!=null){
					oReferencesReply.addHasCreator(personReply);
					personReply = null;
				}
				else{
					processIndividual(oIndividual, "organisation");
					if (organisationReply!=null){
						oReferencesReply.addHasCreator(organisationReply);
						organisationReply = null;
					}
				}
				return;
			}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.usedOntologyEngineeringTool)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				processIndividual(oIndividual, "ontoEngTool");
				if (ontoEngToolReply!=null) oReferencesReply.addUsedOntologyEngineeringTool(ontoEngToolReply);
				ontoEngToolReply = null;
				return;
			}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.usedOntologyEngineeringMethodology)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				processIndividual(oIndividual, "ontoEngMet");
				if (ontoEngMetReply!=null) oReferencesReply.addUsedOntologyEngineeringMethodology(ontoEngMetReply);
				ontoEngMetReply = null;
				return;
			}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.usedKnowledgeRepresentationParadigm)){
				Individual oIndividual =KAON2Manager.factory().individual(value);
				processIndividual(oIndividual, "krParadigm");
				if (krParadigmReply!=null) oReferencesReply.addUsedKnowledgeRepresentationParadigm(krParadigmReply);
				krParadigmReply = null;
				return;
			}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasDomain)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				processIndividual(oIndividual, "oDomain");
				if (oDomainReply!=null) oReferencesReply.addHasDomain(oDomainReply);
				oDomainReply = null;
				return;
			}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isOfType)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				processIndividual(oIndividual, "oType");
				if (oTypeReply!=null) oReferencesReply.setIsOfType(oTypeReply);
				oTypeReply = null;
				return;
			}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.naturalLanguage)) {oReferencesReply.setNaturalLanguage(value);return;}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.designedForOntologyTask)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				processIndividual(oIndividual, "oTask");
				if (oTaskReply!=null) oReferencesReply.addDesignedForOntologyTask(oTaskReply);
				oTaskReply = null;
				return;
			}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasOntologyLanguage)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				processIndividual(oIndividual, "oLanguage");
				if (oLanguageReply!=null) oReferencesReply.setHasOntologyLanguage(oLanguageReply);
				oLanguageReply = null;
				return;
			}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasOntologySyntax)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				processIndividual(oIndividual, "oSyntax");
				if (oSyntaxReply!=null) oReferencesReply.setHasOntologySyntax(oSyntaxReply);
				oSyntaxReply = null;
				return;
			}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasFormalityLevel)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				processIndividual(oIndividual, "fLevel");
				if (fLevelReply!=null) oReferencesReply.setHasFormalityLevel(fLevelReply);
				fLevelReply = null;
				return;
			}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.resourceLocator)) {oReferencesReply.setResourceLocator(value);return;}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.version)) {oReferencesReply.setVersion(value);return;}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasLicense)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				processIndividual(oIndividual, "lModel");
				if (lModelReply!=null) oReferencesReply.setHasLicense(lModelReply);
				lModelReply = null;
				return;
			}
			/*  CHANGE IT FOR DOUBLE REFERENCE
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.useImports)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				processIndividual(oIndividual, "oReferences");
				if (oReferencesReply==null) {
					oReferencesReply = new OMVOntology();
					oReferencesReply.setURI(value);
				}	
				mainOntoReply.addUseImports(oReferencesReply);
				oReferencesReply = null;
			}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasPriorVersion)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				processIndividual(oIndividual, "oReferences");
				if (oReferencesReply==null) {
					oReferencesReply = new OMVOntology();
					oReferencesReply.setURI(value);
				}
				mainOntoReply.setHasPriorVersion(oReferencesReply);
				oReferencesReply = null;
			}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isBackwardCompatibleWith)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				processIndividual(oIndividual, "oReferences");
				if (oReferencesReply==null) {
					oReferencesReply = new OMVOntology();
					oReferencesReply.setURI(value);
				}
				mainOntoReply.addIsBackwardCompatibleWith(oReferencesReply);
				oReferencesReply = null;
			}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isIncompatibleWith)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				processIndividual(oIndividual, "oReferences");
				if (oReferencesReply==null) {
					oReferencesReply = new OMVOntology();
					oReferencesReply.setURI(value);
				}
				mainOntoReply.addIsIncompatibleWith(oReferencesReply);
				oReferencesReply = null;
			}
			*/
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.numClasses)) {oReferencesReply.setNumClasses(new Integer(value.substring(1, value.indexOf("\"", 2))));return;}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.numProperties)) {oReferencesReply.setNumProperties(new Integer(value.substring(1, value.indexOf("\"", 2))));return;}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.numIndividuals)) {oReferencesReply.setNumIndividuals(new Integer(value.substring(1, value.indexOf("\"", 2))));return;}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.numAxioms)) {oReferencesReply.setNumAxioms(new Integer(value.substring(1, value.indexOf("\"", 2))));return;}
		  }catch(Exception e){
				System.out.println(e.toString()+" Search Problem in creteOMVOntologyReferences");
		  }	
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
		if (o.getNumClasses()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.numClasses, o.getNumClasses().toString(), checkDataProperty(Constants.numClasses));
			else condition = new Condition(Constants.omvCondition+Constants.numClasses, o.getNumClasses().toString(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getNumProperties()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.numProperties, o.getNumProperties().toString(), checkDataProperty(Constants.numProperties));
			else condition = new Condition(Constants.omvCondition+Constants.numProperties, o.getNumProperties().toString(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getNumIndividuals()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.numIndividuals, o.getNumIndividuals().toString(), checkDataProperty(Constants.numIndividuals));
			else condition = new Condition(Constants.omvCondition+Constants.numIndividuals, o.getNumIndividuals().toString(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getNumAxioms()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.numAxioms, o.getNumAxioms().toString(), checkDataProperty(Constants.numAxioms));
			else condition = new Condition(Constants.omvCondition+Constants.numAxioms, o.getNumAxioms().toString(), which);
			searchConditions.addFirst(condition);
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
					IOntology.addConceptToRegistry(tList,1);
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
					IOntology.addConceptToRegistry(tList,12);
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
					IOntology.addConceptToRegistry(tList,2);
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
					IOntology.addConceptToRegistry(tList,8);
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
					IOntology.addConceptToRegistry(tList,9);
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
					IOntology.addConceptToRegistry(tList,4);
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
					IOntology.addConceptToRegistry(tList,6);
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
					IOntology.addConceptToRegistry(tList,3);
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
					IOntology.addConceptToRegistry(tList,11);
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
						IOntology.addConceptToRegistry(tList,0);
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
						IOntology.addConceptToRegistry(tList,1);
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
					IOntology.addConceptToRegistry(tList,0);
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
					IOntology.addConceptToRegistry(tList,12);
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
					IOntology.addConceptToRegistry(tList,2);
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
					IOntology.addConceptToRegistry(tList,8);
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
					IOntology.addConceptToRegistry(tList,9);
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
					IOntology.addConceptToRegistry(tList,4);
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
					IOntology.addConceptToRegistry(tList,6);
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
					IOntology.addConceptToRegistry(tList,3);
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
					IOntology.addConceptToRegistry(tList,11);
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
						IOntology.addConceptToRegistry(tList,0);
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
						IOntology.addConceptToRegistry(tList,1);
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
						IOntology.addConceptToRegistry(tList,0);
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
						IOntology.addConceptToRegistry(tList,1);
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
						IOntology.addConceptToRegistry(tList,0);
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
						IOntology.addConceptToRegistry(tList,1);
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
						IOntology.addConceptToRegistry(tList,0);
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
						IOntology.addConceptToRegistry(tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.specifiedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		return tProperties;
	}
	private LinkedList getPropertiesOntologyDomain(OMVOntologyDomain od){
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (od.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, od.getName());
			tProperties.addFirst(prop);
		}
		if (od.getURI()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.URI, od.getURI());
			tProperties.addFirst(prop);
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
						IOntology.addConceptToRegistry(tList,0);
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
						IOntology.addConceptToRegistry(tList,1);
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
						IOntology.addConceptToRegistry(tList,0);
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
						IOntology.addConceptToRegistry(tList,1);
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
					IOntology.addConceptToRegistry(tList,4);
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
					IOntology.addConceptToRegistry(tList,9);
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
						IOntology.addConceptToRegistry(tList,0);
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
						IOntology.addConceptToRegistry(tList,1);
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
						IOntology.addConceptToRegistry(tList,0);
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
						IOntology.addConceptToRegistry(tList,1);
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
						IOntology.addConceptToRegistry(tList,0);
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
						IOntology.addConceptToRegistry(tList,1);
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
						IOntology.addConceptToRegistry(tList,0);
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
						IOntology.addConceptToRegistry(tList,1);
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
					IOntology.addConceptToRegistry(tList,2);
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
					IOntology.addConceptToRegistry(tList,3);
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
					IOntology.addConceptToRegistry(tList,4);
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
					IOntology.addConceptToRegistry(tList,5);
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
				IOntology.addConceptToRegistry(tList,6);
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
					IOntology.addConceptToRegistry(tList,7);
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
				IOntology.addConceptToRegistry(tList,8);
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
				IOntology.addConceptToRegistry(tList,9);
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
				IOntology.addConceptToRegistry(tList,10);
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
				IOntology.addConceptToRegistry(tList,11);
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
		if (o.getNumClasses()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.numClasses, o.getNumClasses().toString());
			ontoProperties.addFirst(prop);
		}
		if (o.getNumProperties()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.numProperties, o.getNumProperties().toString());
			ontoProperties.addFirst(prop);
		}
		if (o.getNumIndividuals()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.numIndividuals, o.getNumIndividuals().toString());
			ontoProperties.addFirst(prop);
		}
		if (o.getNumAxioms()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.numAxioms, o.getNumAxioms().toString());
			ontoProperties.addFirst(prop);
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
				processPeerIndividual(peerIndiv, "peer");
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
	private void processPeerIndividual(Individual peerIndiv, String whichClass){
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
					String	propertyValue = util.Utilities.getString(peerIndiv.getDataPropertyValue(ontologySearch,property));
						
					if (whichClass.equalsIgnoreCase("peer")) createOMVPeer(property.getURI(),propertyValue);
					
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
							
						if (whichClass.equalsIgnoreCase("peer")) createOMVPeer(property.getURI(),propertyValue.getURI());
						
						//System.out.println("The property "+property.getURI()+" has value: "+propertyValue);	
					}	
				}
			}
		  }catch(Exception e){
				System.out.println(e.toString()+" Search Problem in processPeerIndividual");
		  }
		}
	private void createOMVPeer(String URI, String value){
		if (URI.equalsIgnoreCase(Constants.PDURI+Constants.GUID)) peerReply.setGUID(value);
		if (URI.equalsIgnoreCase(Constants.PDURI+Constants.IPAdress)) peerReply.setIPAdress(value);
		if (URI.equalsIgnoreCase(Constants.PDURI+Constants.localPeer)) 
			{
				if ((value.toString().equalsIgnoreCase("\"true\"^^<xsd:boolean>")))
						peerReply.setLocalPeer(true);
				else
						peerReply.setLocalPeer(false);
			}
		if (URI.equalsIgnoreCase(Constants.PDURI+Constants.peerType)) peerReply.setPeerType(value);
		if (URI.equalsIgnoreCase(Constants.PDURI+Constants.contextOntology)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "oReferences");
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			peerReply.setContextOntology(oReferencesReply);
			oReferencesReply = null;
			return;
		}
		if (URI.equalsIgnoreCase(Constants.PDURI+Constants.provideOntology)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			processIndividual(oIndividual, "oReferences");
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			peerReply.addProvideOntology(oReferencesReply);
			oReferencesReply = null;
			return;
		}
	}
	private void copyParty2Person(){
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
	}

	private void copyParty2Organisation(){
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
	}

}