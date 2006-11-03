package core;


import oyster2.Oyster2;
import oyster2.Constants;
import util.GUID;
import java.util.*;
import java.io.*;

import org.semanticweb.kaon2.api.*;
import org.semanticweb.kaon2.api.owl.elements.*;
//import org.semanticweb.kaon2.api.rules.*; OLD VERSION
import org.semanticweb.kaon2.api.logic.*;
import org.semanticweb.kaon2.api.formatting.*;
import util.Utilities;


public class AdvertInformer  {
	private final static String omv = Constants.OMVURI;
	private final static String  versionInfo = Constants.VERSIONINFO;
	private Oyster2 mOyster2 = Oyster2.sharedInstance();
	private String pdURI;
	private String localURI;
    private File localRegistryFile;
	private KAON2Connection remoteConnection=KAON2Manager.newConnection();
	private DefaultOntologyResolver resolver=new DefaultOntologyResolver();
	private Ontology remoteOntologyRegistry = null;
	private Ontology localOntologyRegistry = null ;
	
	
	public  synchronized Ontology openRemoteRegistry(String remoteHostPort){
    	try{
    		System.out.println("open RemoteRegistry at: "+remoteHostPort);
    		String basicRegistry = "kaon2rmi://"+remoteHostPort+"?"+mOyster2.getPeerDescOntologyURI();
    		String remoteRMIURI = "kaon2rmi://"+remoteHostPort+"?http://localhost/localRegistry";
    		this.localURI = this.localOntologyRegistry.getOntologyURI();
    		//String remoteRMIURI = "kaon2rmi://"+remoteHostPort+"?"+localURI;
    		resolver.registerOntology(basicRegistry);
    		resolver.registerReplacement(localURI,remoteRMIURI);
    		mOyster2.getConnection().setOntologyResolver(resolver);
    		remoteOntologyRegistry = mOyster2.getConnection().openOntology(remoteRMIURI,new HashMap<String,Object>());
    	}catch(Exception e){
    		System.out.println(e+":when openRemoteRegistry().The reason may be remote server doesn't be started yet.");
    		remoteOntologyRegistry=null;
    	}
	    return remoteOntologyRegistry;
    }
	
    public void init(){
    	this.pdURI = mOyster2.getPeerDescOntologyURI();
    	this.localOntologyRegistry = mOyster2.getLocalHostOntology();
    	this.localURI = this.localOntologyRegistry.getOntologyURI();
    	this.localRegistryFile = mOyster2.getLocalRegistryFile();
    }
    
    public String resolveIP(String ontologyURI){
    	String	hostPort = "";
		if(ontologyURI.contains("?"))
			hostPort = ontologyURI.substring(ontologyURI.indexOf("?")+1);
		else System.out.println("wrong format of ontologyURI in OntologyRegister!");
		return hostPort;
    }
    
    public Collection getOntologyDocument(Ontology regOntology){
    	OWLClass OntologyDoc=KAON2Manager.factory().owlClass(omv+"Ontology");
    	Set<Individual> ontologyDocSet = null;
    	try{
    		 ontologyDocSet = OntologyDoc.getMemberIndividuals(regOntology);
    	}catch(KAON2Exception e){
    		System.err.println(e.toString()+" getOntologyDocument()in AdvertInformer");
    	}
    	return ontologyDocSet;	
    }
    
    public Individual getOntologySubject (Ontology regOntology, Individual ontologyDoc){
    	ObjectProperty baseSubject = KAON2Manager.factory().objectProperty(omv+Constants.hasDomain);
    	Individual subjectIndiv = null;
    	try{
    	 subjectIndiv = ontologyDoc.getObjectPropertyValue(regOntology,baseSubject); 
    	}catch(Exception e){
    		System.err.println(e.toString()+"getOntologySubject()in advertInformer.");
    	}
    	return subjectIndiv;
    }
   
    public Collection getOntologyProvider(Ontology regOntology,Individual ontologyDoc){
    	Map propertyMap = new HashMap();
    	ObjectProperty location = null;
    	Collection peerSet = new LinkedList();
    	try{
    		 propertyMap = ontologyDoc.getObjectPropertyValues(regOntology);
    		 location= KAON2Manager.factory().objectProperty(pdURI+"#resourceLocator");
    		 peerSet =(Collection) propertyMap.get(location);
    	}catch(Exception e){
    		System.err.println(e.toString()+" getOntologyProvider in AdvertInformer.");
    	}
    	if (peerSet==null) return new LinkedList();
    	return peerSet;
    }
    
    public Collection getOntologyDoc(Ontology regOntology,Individual peer) {
    	ObjectProperty provideOntology = KAON2Manager.factory().objectProperty(pdURI+"#provideOntology");
    	Collection ontologyDocSet = new ArrayList();
    	try{
    		Map propertyMap = peer.getObjectPropertyValues(regOntology);
    		ontologyDocSet = (Collection)propertyMap.get(provideOntology);
        }catch(Exception e){
        	System.err.println(e.toString()+"getOntologyDoc()in AdvertInformer.");
        }
        if(ontologyDocSet == null)ontologyDocSet = new ArrayList();
        return ontologyDocSet;
    }
    
    public Collection getImportOntology(Ontology regOntology,Individual ontologyDoc){
    	ObjectProperty importsOntology = KAON2Manager.factory().objectProperty(omv+Constants.useImports);
    	Map propertyMap = new HashMap();
    	Collection importOntoSet = new LinkedList();
    	try{
    		propertyMap = ontologyDoc.getObjectPropertyValues(regOntology);
    	    importOntoSet = (Collection)propertyMap.get(importsOntology);
    	}catch(Exception e){
    		System.err.println(e.toString()+"getImportOntology()in advertInformer.");
    	}
    	return importOntoSet;
    }
    
    public synchronized Map getPeerSet(Ontology regOntology){
    	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    	Map guidTable = new HashMap();
    	try{
    		OWLClass Peer = KAON2Manager.factory().owlClass(pdURI+"#Peer");
    		Set<Individual> peerSet = Peer.getMemberIndividuals(regOntology);
    		DataProperty GUID = KAON2Manager.factory().dataProperty(pdURI+"#GUID");
    		for(Individual indiv: peerSet){
    			String guid = util.Utilities.getString(indiv.getDataPropertyValue(regOntology,GUID)); //OLD VERSION (String)
    			if(guid ==null) {
    				guid=(new GUID()).toString();
    				//if(regOntology.containsAxiom(KAON2Manager.factory().dataPropertyMember(GUID,peerIndiv,null),true))
    				//changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(GUID,indiv,guid),OntologyChangeEvent.ChangeType.ADD)); //OLD VERSION
    				changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(GUID,indiv,KAON2Manager.factory().constant(guid)),OntologyChangeEvent.ChangeType.ADD));
    			}
    			guidTable.put(guid,indiv);
    		}
    		regOntology.applyChanges(changes);
    	}catch(Exception e){
    		System.err.println(e + " Here on GetPeer");
    	}
    	return guidTable;
    }
    
    public  List getPeerList(Ontology regOntology){
    	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    	List peerList = new ArrayList();
    	Map guidTable = new HashMap();
    	try{
    		OWLClass Peer = KAON2Manager.factory().owlClass(pdURI+"#Peer");
    		Set<Individual> peerSet = Peer.getMemberIndividuals(regOntology);
    		for(Individual indiv: peerSet){
    			peerList.add(indiv);
    		}
    	}catch(Exception e){
    		System.err.println(e.toString()+"getPeerList()in AdvertInformer.");
    	}
    	return peerList;
    }
    
    public String getPeerIP(Ontology regOntology,Individual peerIndiv){
    	DataProperty IPAddress = KAON2Manager.factory().dataProperty(pdURI+"#IPAdress");
    	String IP = "";
    	try{
        	IP = util.Utilities.getString(peerIndiv.getDataPropertyValue(regOntology,IPAddress)); //NEW VALIDATION
        	}catch(Exception e){
        		System.err.println(e.toString()+" getMappingIP()in AdvertInformer.");
        	}
    	return IP;
    }
    
    public synchronized Map getRendezVousPeers(Ontology regOntology){
    	Map peerSet = getPeerSet(regOntology);
    	Map RPeerMap = new HashMap();
    	Collection guidList = peerSet.keySet();
    	DataProperty peerType = KAON2Manager.factory().dataProperty(pdURI+"#peerType");
    	DataProperty IPAdress = KAON2Manager.factory().dataProperty(pdURI+"#IPAdress");
    	Iterator peerGuids = guidList.iterator();
    	String localUID=this.getLocalUID();
    	while(peerGuids.hasNext()){
    		String guid = (String)peerGuids.next();
    		Individual peerIndiv =(Individual) peerSet.get(guid);
    		if(peerIndiv== null)
    			System.err.println("peerIndiv is null when getRendezVousPeers.");
    		try{
    			String type = util.Utilities.getString(peerIndiv.getDataPropertyValue(regOntology,peerType)); //(String)
    			if(type.equals("R")){//&&(!guid.equals(localUID))){
    				String IP = util.Utilities.getString(peerIndiv.getDataPropertyValue(regOntology,IPAdress)); //(String)
    				RPeerMap.put(guid,IP);	
    			}
    		}catch(Exception e){
    			System.out.println(e+" when getRendezVousPeers().");
    		}
    	}
    	return RPeerMap;
    }
    
    public String getLocalPeerName(){
    	Individual peerIndiv = getLocalPeer();
    	String localName = null;
    	try{
    	System.out.println(" que es esto" + peerIndiv);
    	localName = Namespaces.guessLocalName(peerIndiv.getURI());
    	}catch(Exception e){
    		System.err.println(e+" getLocalPeerName()");
    	}
    	return localName;
    }
    
    public synchronized Individual getLocalPeer(){
    	Ontology regOntology = mOyster2.getLocalHostOntology();
    	Map peerSet = getPeerSet(regOntology);
    	Collection guidList = peerSet.values();
    	DataProperty localPeer = KAON2Manager.factory().dataProperty(pdURI+"#localPeer");
    	Iterator peerIndivs = guidList.iterator();
    	Individual peerIndiv = null;
    	boolean local = false;
    	while(peerIndivs.hasNext()){
    		peerIndiv =(Individual) peerIndivs.next();	
    		try{
    			local = getBoolean(util.Utilities.getString(peerIndiv.getDataPropertyValue(regOntology,localPeer))); //(Boolean)
    		if(local){
    			break;
    		}
    		}catch(Exception e){
    			System.err.print(e.toString()+" getLocalPeerIndiv()");
    		}
    	}
    	if(!local){
    		return null;
    	}
    	else {
    		System.out.println("the local peer is: "+ peerIndiv);
    		return peerIndiv;
    	}
    }
    
    //KAON2 PROBLEM
    public Boolean getBoolean(String prop){
    	if (prop!=null){
    		if (prop.equalsIgnoreCase("\"true\"^^<xsd:boolean>")) return true;
    		else return false;
    	}
    	else return false;
    }
    
    
    public synchronized Individual getLocalPeerIndiv(Ontology regOntology){
    	Map peerSet = getPeerSet(regOntology);
    	String localIP = mOyster2.getLocalHost().getAddress();
    	Collection guidList = peerSet.values();
    	DataProperty IPAdress = KAON2Manager.factory().dataProperty(pdURI+"#IPAdress");
    	Iterator peerIndivs = guidList.iterator();
    	Individual peerIndiv = null;
    	while(peerIndivs.hasNext()){
    		peerIndiv =(Individual) peerIndivs.next();
    		try{
    		String ip = util.Utilities.getString(peerIndiv.getDataPropertyValue(regOntology,IPAdress)); //(String)
    		System.out.println("ip:"+ ip);
    	
    		if(ip.equals(localIP)||(ip.equals("localhost"))){
    			break;
    		}
    		}catch(Exception e){
    			System.err.print(e.toString()+" getLocalPeerIndiv()");
    		}
    	}
    	return peerIndiv;
    }
    
    public String getLocalUID(){
    	Ontology regOntology = mOyster2.getLocalHostOntology();
    	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    	Map peerSet = getPeerSet(regOntology);
    	Collection guidList = peerSet.values();
    	DataProperty localPeer = KAON2Manager.factory().dataProperty(pdURI+"#localPeer");
    	DataProperty GUID = KAON2Manager.factory().dataProperty(pdURI+"#GUID");
    	Individual peerIndiv = getLocalPeer();
    	String UID = null;
    	try{
    		if(peerIndiv!=null){
    			UID = util.Utilities.getString(peerIndiv.getDataPropertyValue(regOntology,GUID)); //(String)
    			if((UID ==null)||(UID.length()==0) ){
    				/* OLD VERSION
    				if(regOntology.containsAxiom(KAON2Manager.factory().dataPropertyMember(GUID,peerIndiv,UID),true))
    					changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(GUID,peerIndiv,UID),OntologyChangeEvent.ChangeType.REMOVE));
    				UID=(new GUID()).toString();
    				changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(GUID,peerIndiv,UID),OntologyChangeEvent.ChangeType.ADD));
    				*/
    				if(regOntology.containsAxiom(KAON2Manager.factory().dataPropertyMember(GUID,peerIndiv,KAON2Manager.factory().constant(UID)),true))
    					changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(GUID,peerIndiv,KAON2Manager.factory().constant(UID)),OntologyChangeEvent.ChangeType.REMOVE));
    				UID=(new GUID()).toString();
    				changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(GUID,peerIndiv,KAON2Manager.factory().constant(UID)),OntologyChangeEvent.ChangeType.ADD));
    				regOntology.applyChanges(changes);
    			}
    			regOntology.persist();
    			save(regOntology);
    		}
    		else{
    			UID=(new GUID()).toString();
    		}
    	}catch(Exception e){
    		System.err.println(e+" in AdvertInformer getLocalUID().");
    	}
    	return UID;
    }
    
    public Collection getMappingSet(Ontology regOntology){
    	OWLClass Mapping = KAON2Manager.factory().owlClass(pdURI+"#Mapping");
    	Set<Individual> mappingSet = null;
    	try{
    	 mappingSet = Mapping.getMemberIndividuals(regOntology);
    	}catch(KAON2Exception e){
    		System.err.println(e.toString()+" getMappingSet() in AdvertInformer.");
    	}
    	return mappingSet;
    }
    
    public boolean isMapping(Ontology regOntology,String uri){
    	Collection mappingSet = getMappingSet(regOntology);
    	Iterator it = mappingSet.iterator();
    	while(it.hasNext()){
    		String mappingURI = getMappingURI(regOntology,((Individual)it.next()));
    	
    		if(mappingURI.equals(uri))
    			return true;
    	}
    	return false; 
    }
    
    public Individual getContextOntology(Ontology regOntology,Individual peerIndiv){
    	ObjectProperty contextOntology=KAON2Manager.factory().objectProperty(pdURI+"#contextOntology");
    	Individual ontologyIndiv = null;
    	try{
    	ontologyIndiv= peerIndiv.getObjectPropertyValue(regOntology,contextOntology);
    	}catch(KAON2Exception e){
    		System.err.println(e.toString()+" getContextOntology()in AdvertInformer.");
    	}
    	if(ontologyIndiv == null)System.out.println("contextOntology is null");
    	return ontologyIndiv;
    }
    
    public Individual getRelevantMapping(Ontology regOntology,Individual source,Individual target){
    	Collection mappingSet = getMappingSet(regOntology);
    	//Collection resultSet = new LinkedList();
    	ObjectProperty mappingSource=KAON2Manager.factory().objectProperty(pdURI+"#mappingSource");
    	ObjectProperty mappingTarget=KAON2Manager.factory().objectProperty(pdURI+"#mappingTarget");
    	if(mappingSet==null)System.err.println("mappingSet is null!");
    	Iterator it = mappingSet.iterator();
    	try{
    	while(it.hasNext()){
    		Individual mappingIndiv = (Individual)it.next();
    		Individual sourceIndiv = mappingIndiv.getObjectPropertyValue(regOntology,mappingSource); 
        	Individual targetIndiv = mappingIndiv.getObjectPropertyValue(regOntology,mappingTarget); 
        	if((sourceIndiv == source)&&(targetIndiv == target)){
        		//resultSet.add(mappingIndiv);	
        		return mappingIndiv;
        	}
    	}
    	}catch(KAON2Exception e){
    		System.err.println(e.toString()+"getRelevantMapping() in AdvertInformer");
    	}
    	return null;
    	
    }
    
    public String getMappingIP(Ontology regOntology,Individual mappingIndiv){
    	DataProperty IPAddress = KAON2Manager.factory().dataProperty(pdURI+"#IPAdress");
    	ObjectProperty mappingProvider=KAON2Manager.factory().objectProperty(pdURI+"#mappingProvider");
    	String mappingIP = "";
    	try{
    	Individual peerIndiv = mappingIndiv.getObjectPropertyValue(regOntology,mappingProvider);
    	mappingIP = util.Utilities.getString(peerIndiv.getDataPropertyValue(regOntology,IPAddress)); //NEW VALIDATION
    	}catch(KAON2Exception e){
    		System.err.println(e.toString()+" getMappingIP()in AdvertInformer.");
    	}
    	return mappingIP;
    }
    
    public String getMappingURI(Ontology regOntology,Individual mappingIndiv){
    	DataProperty ontologyURL =  KAON2Manager.factory().dataProperty(omv+ Constants.URI);
    	String mappingURI = "";
    	try{
    		mappingURI = util.Utilities.getString(mappingIndiv.getDataPropertyValue(regOntology,ontologyURL));
    	}catch(KAON2Exception e){
    		System.err.println(e.toString()+" getMappingURI()in AdvertInformer.");
    	}
    	return mappingURI;
    }
    
    private boolean isEqualMapping(Individual mapIndivRemote, Individual mapIndivLocal){
    	ObjectProperty mappingSource=KAON2Manager.factory().objectProperty(pdURI+"#mappingSource");
    	ObjectProperty mappingTarget=KAON2Manager.factory().objectProperty(pdURI+"#mappingTarget");
    	try{
    	Individual sourceIndivRemote = mapIndivRemote.getObjectPropertyValue(remoteOntologyRegistry,mappingSource); 
    	Individual targetIndivRemote = mapIndivRemote.getObjectPropertyValue(remoteOntologyRegistry,mappingTarget); 
    	Individual sourceIndivLocal = mapIndivLocal.getObjectPropertyValue(localOntologyRegistry,mappingSource); 
    	Individual targetIndivLocal = mapIndivLocal.getObjectPropertyValue(localOntologyRegistry,mappingTarget); 
    	if((sourceIndivRemote == sourceIndivLocal)&&(targetIndivRemote == targetIndivLocal))
        	return true;
    	}catch(Exception e){
    		System.err.println(e + "isEqualMapping()in AdvertInformer");
    	}
    	return false;
    }
    
    private boolean containsMapping(Ontology ontology,Individual mappingIndiv){
    	Collection mappingSet = getMappingSet(ontology);
    	Iterator mappings = mappingSet.iterator();
    	while(mappings.hasNext()){
    		Individual mapping = (Individual)mappings.next();
    		if(isEqualMapping(mappingIndiv,mapping)){
    			return true;
    		}
    	}
    	return false;
    }
    
    public  Rule createSubTopicRule(Ontology ontology){
    	ObjectProperty baseSubject = KAON2Manager.factory().objectProperty(omv+Constants.hasDomain);
    	ObjectProperty subTopic=KAON2Manager.factory().objectProperty(ontology.getOntologyURI()+"SubTopic");
    	Variable X=KAON2Manager.factory().variable("X");
        Variable Y=KAON2Manager.factory().variable("Y"); 
        Variable Z=KAON2Manager.factory().variable("Z");
        Literal baseSubject_X_Z=KAON2Manager.factory().literal(true,baseSubject,new Term[] { X,Z });
        Literal baseSubject_X_Y=KAON2Manager.factory().literal(true,baseSubject,new Term[] { X,Y });
        Literal subTopic_Z_Y=KAON2Manager.factory().literal(true,subTopic,new Term[] { Z,Y});
        Rule rule=KAON2Manager.factory().rule(
                baseSubject_X_Z,                          // this is the rule head, i.e. the consequent of the rule
                new Literal[] { baseSubject_X_Y,subTopic_Z_Y }   // this is the rule body, i.e. the condition of the rule
            );
        return rule;
    }
    
    public void addRule(Ontology ontology,Rule newRule){
    	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    	try{
    		if(!ontology.containsAxiom(newRule,true))
    	    	changes.add(new OntologyChangeEvent(newRule,OntologyChangeEvent.ChangeType.ADD));
    	    ontology.applyChanges(changes);
    	}catch(KAON2Exception e){
    		System.err.println(e.toString()+"addRule()in AdvertInformer.");
    	}
    } 
    
    public synchronized void addExpertisePeer(Ontology remoteOntologyRegistry,Individual newPeer,Ontology targetOntology){
    	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    	System.out.println("peer to add:"+newPeer.getURI());
    	DataProperty GUID = KAON2Manager.factory().dataProperty(pdURI + "#GUID");
    	try{
    		String uid = util.Utilities.getString(newPeer.getDataPropertyValue(remoteOntologyRegistry,GUID));  //(String)
    		String olduid = util.Utilities.getString(newPeer.getDataPropertyValue(targetOntology,GUID)); //(String)
    		OWLClass Peer = KAON2Manager.factory().owlClass(pdURI+"#Peer");
    		if(!targetOntology.containsAxiom(KAON2Manager.factory().classMember(Peer,newPeer),true)){
    			System.out.println("add: "+newPeer.getURI()+ " to: "+targetOntology.getPhysicalURI() );
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(Peer,newPeer),OntologyChangeEvent.ChangeType.ADD));
    		}
    		else 
    			System.err.println(targetOntology.getPhysicalURI()+" already contains: "+newPeer.getURI());
    		
    		if(olduid ==null||!olduid.equals(uid)){
    			System.out.println("olduid in " + targetOntology.getPhysicalURI()+ " "+olduid + " uid in " +remoteOntologyRegistry.getPhysicalURI()+" "+uid);
    			if (olduid!=null) changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(GUID,newPeer,KAON2Manager.factory().constant(olduid)),OntologyChangeEvent.ChangeType.REMOVE));
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(GUID,newPeer,KAON2Manager.factory().constant(uid)),OntologyChangeEvent.ChangeType.ADD));
    		}
    		targetOntology.applyChanges(changes);
    		targetOntology.persist();
    		updatePeerAttributes(remoteOntologyRegistry,newPeer,targetOntology);
    	}catch(Exception e){
    		System.err.println(e+"in addExpertisePeer in AdvertInformer.");
    	}
    }
    
    public void setLocalPeer(String peerName, String uid, String ip,String peerType){
    	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    	DataProperty GUID = KAON2Manager.factory().dataProperty(pdURI + "#GUID");
    	DataProperty type = KAON2Manager.factory().dataProperty(pdURI + "#peerType");
    	DataProperty localPeer = KAON2Manager.factory().dataProperty(pdURI + "#localPeer");
    	DataProperty ipAdr = KAON2Manager.factory().dataProperty(pdURI + "#IPAdress");
    	ObjectProperty contextOntology = KAON2Manager.factory().objectProperty(pdURI + "#contextOntology");
    	Individual localPeerIndiv = KAON2Manager.factory().individual(localURI+"#"+peerName);
    	Individual contextOntologyIndiv = KAON2Manager.factory().individual(mOyster2.getTypeOntology().getOntologyURI());
    	OWLClass Peer = KAON2Manager.factory().owlClass(pdURI+"#Peer");
    	OWLClass OntologyDoc = KAON2Manager.factory().owlClass(omv+"Ontology");
    	try{
    		localOntologyRegistry = mOyster2.getLocalHostOntology();
    		if(localOntologyRegistry.containsEntity(localPeerIndiv, true)){
    			System.out.println("local registry contains already a peer with the same peer name: "+peerName);
    		}
    		else{
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(Peer,localPeerIndiv),OntologyChangeEvent.ChangeType.ADD));
    			if(!localOntologyRegistry.containsEntity(contextOntologyIndiv, true))
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(OntologyDoc,contextOntologyIndiv),OntologyChangeEvent.ChangeType.ADD));
    	    	/* OLD VERSION
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(GUID,localPeerIndiv,uid),OntologyChangeEvent.ChangeType.ADD));
    	    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(type,localPeerIndiv,peerType),OntologyChangeEvent.ChangeType.ADD));
    	    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(localPeer,localPeerIndiv,true),OntologyChangeEvent.ChangeType.ADD));
    	    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ipAdr,localPeerIndiv,ip),OntologyChangeEvent.ChangeType.ADD));
    	    	*/
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(GUID,localPeerIndiv,KAON2Manager.factory().constant(uid)),OntologyChangeEvent.ChangeType.ADD));
    	    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(type,localPeerIndiv,KAON2Manager.factory().constant(peerType)),OntologyChangeEvent.ChangeType.ADD));
    	    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(localPeer,localPeerIndiv,KAON2Manager.factory().constant(true)),OntologyChangeEvent.ChangeType.ADD));
    	    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ipAdr,localPeerIndiv,KAON2Manager.factory().constant(ip)),OntologyChangeEvent.ChangeType.ADD));
    	    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(contextOntology,localPeerIndiv,contextOntologyIndiv),OntologyChangeEvent.ChangeType.ADD));
    	    	localOntologyRegistry.applyChanges(changes);
        		localOntologyRegistry.persist();
        		save(localOntologyRegistry);
    		
    		}
    	}catch(Exception e){
    		System.err.println(e.getMessage()+"in setLocalPeer().");
    	}
    }
    
    public void addBootPeer(String peerName, String uid, String ip){
    	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    	DataProperty GUID = KAON2Manager.factory().dataProperty(pdURI + "#GUID");
    	DataProperty type = KAON2Manager.factory().dataProperty(pdURI + "#peerType");
    	DataProperty localPeer = KAON2Manager.factory().dataProperty(pdURI + "#localPeer");
    	DataProperty ipAdr = KAON2Manager.factory().dataProperty(pdURI + "#IPAdress");
    	Individual localPeerIndiv = KAON2Manager.factory().individual(localURI+"#"+peerName);
    	OWLClass Peer = KAON2Manager.factory().owlClass(pdURI+"#Peer");
    	try{
    		localOntologyRegistry = mOyster2.getLocalHostOntology();
    		if(localOntologyRegistry.containsEntity(localPeerIndiv, true)){
    			System.out.println("local registry contains already a peer with the same peer name: "+peerName);
    		}
    		else{
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(Peer,localPeerIndiv),OntologyChangeEvent.ChangeType.ADD));
    			/* OLD VERSION
    	    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(GUID,localPeerIndiv,uid),OntologyChangeEvent.ChangeType.ADD));
    	    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(type,localPeerIndiv,"R"),OntologyChangeEvent.ChangeType.ADD));
    	    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(localPeer,localPeerIndiv,false),OntologyChangeEvent.ChangeType.ADD));
    	    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ipAdr,localPeerIndiv,ip),OntologyChangeEvent.ChangeType.ADD));
    	    	*/
    			if (!uid.equalsIgnoreCase("")) changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(GUID,localPeerIndiv,KAON2Manager.factory().constant(uid)),OntologyChangeEvent.ChangeType.ADD));
    	    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(type,localPeerIndiv,KAON2Manager.factory().constant("R")),OntologyChangeEvent.ChangeType.ADD));
    	    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(localPeer,localPeerIndiv,KAON2Manager.factory().constant(false)),OntologyChangeEvent.ChangeType.ADD));
    	    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ipAdr,localPeerIndiv,KAON2Manager.factory().constant(ip)),OntologyChangeEvent.ChangeType.ADD));
    	    		
    	    	localOntologyRegistry.applyChanges(changes);
        		localOntologyRegistry.persist();
        		save(localOntologyRegistry);
        		System.out.println("add bootstrap peer successfully.");
    
    		}
    	}catch(Exception e){
    		System.err.println(e.getMessage()+"in setLocalPeer().");
    	}
    }
    
    public synchronized void updateIPAddress(Ontology remoteOntologyRegistry,Individual peerIndiv,Ontology targetOntology){
    	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    	DataProperty IPAdress = KAON2Manager.factory().dataProperty(pdURI + "#IPAdress");
    	DataProperty GUID = KAON2Manager.factory().dataProperty(pdURI + "#GUID");
    	String oldIPStr="";
    	String newIPStr="";
    	String oldGUID ="";
    	String newGUID ="";
    	try{
    		oldIPStr = util.Utilities.getString(peerIndiv.getDataPropertyValue(targetOntology,IPAdress)); //(String)
    		newIPStr = util.Utilities.getString(peerIndiv.getDataPropertyValue(remoteOntologyRegistry,IPAdress)); //(String)
    		oldGUID = util.Utilities.getString(peerIndiv.getDataPropertyValue(targetOntology,GUID)); //(String)
    		newGUID = util.Utilities.getString(peerIndiv.getDataPropertyValue(remoteOntologyRegistry,GUID)); //(String)
        
    		System.out.println("old IP: "+oldIPStr+" new IP: "+newIPStr);
    		System.out.println("old GUID: "+oldGUID+" new GUID: "+newGUID);
    		System.out.println("remoteontology "+remoteOntologyRegistry.getPhysicalURI()+" targetontology "+targetOntology.getPhysicalURI());
    	
    	if(oldIPStr!=null)
    		changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(IPAdress,peerIndiv,KAON2Manager.factory().constant(oldIPStr)),OntologyChangeEvent.ChangeType.REMOVE));
        changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(IPAdress,peerIndiv,KAON2Manager.factory().constant(newIPStr)),OntologyChangeEvent.ChangeType.ADD));
        if(oldGUID !=null)
        	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(GUID,peerIndiv,KAON2Manager.factory().constant(oldGUID)),OntologyChangeEvent.ChangeType.REMOVE));
        changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(GUID,peerIndiv,KAON2Manager.factory().constant(newGUID)),OntologyChangeEvent.ChangeType.ADD));
        targetOntology.applyChanges(changes); 
    	}catch(KAON2Exception e){
    		System.out.println(e.toString()+"updateIPAdress() in AdvertInformer.");
    	}
    }
    
    public synchronized void updatePeerAttributes(Ontology remoteOntologyRegistry,Individual peerIndiv,Ontology targetOntology){
    	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    	Collection ontologySet = getOntologyDoc(remoteOntologyRegistry,peerIndiv);
    	ObjectProperty provideOntology = KAON2Manager.factory().objectProperty(pdURI + "#provideOntology");
        DataProperty peerType = KAON2Manager.factory().dataProperty(pdURI + "#peerType");
        DataProperty localPeer = KAON2Manager.factory().dataProperty(pdURI + "#localPeer");
        ObjectProperty contextOntology = KAON2Manager.factory().objectProperty(pdURI + "#contextOntology");
        OWLClass Peer = KAON2Manager.factory().owlClass(pdURI+"#Peer");
        OWLClass OntologyDoc=KAON2Manager.factory().owlClass(omv+"Ontology");
        updateIPAddress(remoteOntologyRegistry,peerIndiv,targetOntology);
    	System.out.println("remote: "+remoteOntologyRegistry.getPhysicalURI()+" target: "+targetOntology.getPhysicalURI()+" peer: "+peerIndiv.getURI());
    	try{
    		Individual contextIndiv = getContextOntology(remoteOntologyRegistry,peerIndiv);
    		String type = util.Utilities.getString(peerIndiv.getDataPropertyValue(remoteOntologyRegistry,peerType)); //(String)
    		boolean local = getBoolean(util.Utilities.getString(peerIndiv.getDataPropertyValue(remoteOntologyRegistry,localPeer))); //(Boolean) 
    		
    		if(!targetOntology.containsAxiom(KAON2Manager.factory().classMember(Peer,peerIndiv),true)){
	    		changes.clear();
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(Peer,peerIndiv),OntologyChangeEvent.ChangeType.ADD));
	    		targetOntology.applyChanges(changes);
	    		System.out.println("add peer.");
    		}
    		if(!targetOntology.containsAxiom(KAON2Manager.factory().objectPropertyMember(contextOntology,peerIndiv,contextIndiv),true)){
        		changes.clear();
        		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(contextOntology,peerIndiv,contextIndiv),OntologyChangeEvent.ChangeType.ADD));
        		targetOntology.applyChanges(changes);
        		System.out.println("add context ontology.");
        	}
    		if(!targetOntology.containsAxiom(KAON2Manager.factory().dataPropertyMember(peerType,peerIndiv,KAON2Manager.factory().constant(type)),true)){
        		changes.clear();
        		changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(peerType,peerIndiv,KAON2Manager.factory().constant(type)),OntologyChangeEvent.ChangeType.ADD));
        		targetOntology.applyChanges(changes);
        		System.out.println("add peer type.");
        	}
    		if(!targetOntology.containsAxiom(KAON2Manager.factory().dataPropertyMember(localPeer,peerIndiv,KAON2Manager.factory().constant(false)),true)){
        		changes.clear();
        		changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(localPeer,peerIndiv,KAON2Manager.factory().constant(false)),OntologyChangeEvent.ChangeType.ADD));
        		targetOntology.applyChanges(changes);
        		System.out.println("localPeer (boolean attribute).");
        	}
    		
    		Iterator ontology = ontologySet.iterator();
    		while(ontology.hasNext()){
    			Individual ontologyIndiv =(Individual) ontology.next();
    			if(!targetOntology.containsAxiom(KAON2Manager.factory().objectPropertyMember(provideOntology,peerIndiv,ontologyIndiv),true)){
    				changes.clear();
    				changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(provideOntology,peerIndiv,ontologyIndiv),OntologyChangeEvent.ChangeType.ADD));
    				targetOntology.applyChanges(changes);
    				System.out.println("add "+peerIndiv+" provide: "+ontologyIndiv);
    			}        	
    			if(!targetOntology.containsAxiom(KAON2Manager.factory().classMember(OntologyDoc,ontologyIndiv),true)){
    				System.out.println("add OntologyIndiv to target registry :"+ontologyIndiv);
    				addExpertiseOntology(remoteOntologyRegistry,ontologyIndiv,targetOntology);
    			}
    			else System.out.println(targetOntology.getOntologyURI()+" already contains: "+ontologyIndiv.getURI());
        	}
    	}catch(Exception e){
    		System.err.println(e + " in updatePeerAttributes() in AdvertInformer. ");
    	}
    }
    
    public synchronized void addExpertiseOntology(Ontology remoteOntologyRegistry,Individual ontologyIndiv,Ontology targetOntology){
    	OWLClass OntologyDoc=KAON2Manager.factory().owlClass(omv+"OntologyDocument");
    	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    	try{
    	if(!targetOntology.containsAxiom(KAON2Manager.factory().classMember(OntologyDoc,ontologyIndiv),true))
    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(OntologyDoc,ontologyIndiv),OntologyChangeEvent.ChangeType.ADD));
    	targetOntology.applyChanges(changes);
    	updateOntologyAttributes(remoteOntologyRegistry,ontologyIndiv,targetOntology);
    	}catch(Exception e){
    		System.err.println(e.toString()+": addExpertiseOntology()in AdvertInformer");
    	}
    }
    
    public synchronized void updateOntologyAttributes(Ontology remoteOntologyRegistry,Individual ontologyIndiv,Ontology targetOntology){
    	try{
    		
    	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    	Individual subjectIndiv = getOntologySubject(remoteOntologyRegistry, ontologyIndiv);
    	Individual oldSubjectIndiv = getOntologySubject(targetOntology, ontologyIndiv);
    	Collection importSet = getImportOntology(remoteOntologyRegistry,ontologyIndiv);
    	ObjectProperty hasDomain = KAON2Manager.factory().objectProperty(omv + Constants.hasDomain);
    	ObjectProperty location = KAON2Manager.factory().objectProperty(pdURI + "#ontologyLocation");
    	DataProperty ontologyName = KAON2Manager.factory().dataProperty(omv + Constants.name);
    	DataProperty ontologyURI = KAON2Manager.factory().dataProperty(omv + Constants.URI);
    	String oldName = util.Utilities.getString(ontologyIndiv.getDataPropertyValue(targetOntology,ontologyName)); //(String)
    	String newName = util.Utilities.getString(ontologyIndiv.getDataPropertyValue(remoteOntologyRegistry,ontologyName)); //(String)
    	String oldURL = util.Utilities.getString(ontologyIndiv.getDataPropertyValue(targetOntology,ontologyURI)); //(String)
    	String newURL = util.Utilities.getString(ontologyIndiv.getDataPropertyValue(remoteOntologyRegistry,ontologyURI)); //(String)
    	
    	if(newName!=null){
    		if(oldName == null){
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyName,ontologyIndiv,KAON2Manager.factory().constant(newName)),OntologyChangeEvent.ChangeType.ADD));
    			
    		}
    		else {
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyName,ontologyIndiv,KAON2Manager.factory().constant(oldName)),OntologyChangeEvent.ChangeType.REMOVE));
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyName,ontologyIndiv,KAON2Manager.factory().constant(newName)),OntologyChangeEvent.ChangeType.ADD)); 
    		}
    	}
    	if(newURL != null){
    		if(oldURL==null){
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyURI,ontologyIndiv,KAON2Manager.factory().constant(newURL)),OntologyChangeEvent.ChangeType.ADD));
    		}else{
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyURI,ontologyIndiv,KAON2Manager.factory().constant(oldURL)),OntologyChangeEvent.ChangeType.REMOVE));
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyURI,ontologyIndiv,KAON2Manager.factory().constant(newURL)),OntologyChangeEvent.ChangeType.ADD));
    		}
    	}
    	
    	
    	/*if((newPeerIndiv != null)&&(oldPeerIndiv != newPeerIndiv))
    		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(location,ontologyIndiv,newPeerIndiv),OntologyChangeEvent.ChangeType.ADD));*/
    	//-------------------------------------------------
    
//    		-------------------------------new added---------------
        	Collection newPeerSet = getOntologyProvider(remoteOntologyRegistry,ontologyIndiv);
        	Collection oldPeerSet = getOntologyProvider(targetOntology,ontologyIndiv);
        	//-------------------------------------------------------
        
    	Iterator it = newPeerSet.iterator();
    	while(it.hasNext()){
    		Individual newPeerIndiv = (Individual)it.next();
    		if(!oldPeerSet.contains(newPeerIndiv)){
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(location,ontologyIndiv,newPeerIndiv),OntologyChangeEvent.ChangeType.ADD));
    			//targetOntology.applyChanges(changes); 
    		}
    	}
    	if((oldSubjectIndiv==null))
    		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(hasDomain,ontologyIndiv,subjectIndiv),OntologyChangeEvent.ChangeType.ADD));
    	
    	targetOntology.applyChanges(changes); 
    	
    	
    	//-------------------------------------------------
    	
    	
    	if(importSet != null)
    		addImportOntology(remoteOntologyRegistry,ontologyIndiv,targetOntology);
    	}catch(Exception e){
    		System.err.println(e.toString()+" updateOntologyAttributes()in advertInformer.");
    	}
    }
    
    public synchronized void addImportOntology(Ontology remoteOntologyRegistry,Individual ontologyIndiv,Ontology targetOntology){
    	try{
    	Collection importSet = getImportOntology(remoteOntologyRegistry,ontologyIndiv);
    	ObjectProperty imports = KAON2Manager.factory().objectProperty(omv + Constants.useImports);
    	OWLClass OntologyDoc=KAON2Manager.factory().owlClass(omv+ Constants.ontologyConcept);
    	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    	Iterator importOnto = importSet.iterator();
    	while(importOnto.hasNext()){
    		Individual importIndiv = (Individual)importOnto.next();
    		if(!targetOntology.containsAxiom(KAON2Manager.factory().objectPropertyMember(imports,ontologyIndiv,importIndiv),true))
	    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(imports,ontologyIndiv,importIndiv),OntologyChangeEvent.ChangeType.ADD));
	    	if(!targetOntology.containsEntity(importIndiv,true))
	    			addExpertiseOntology(remoteOntologyRegistry,importIndiv,targetOntology);	
    	}
    	targetOntology.applyChanges(changes); 
    	changes.clear();
    	}catch(Exception e){
    		System.err.println(e.toString()+"addImportOntology()in advertInformer.");
    	}
    }
    
    public synchronized void addMappingOntology(Ontology remoteOntologyRegistry,Individual mapping,Ontology localOntologyRegistry){
    	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    	OWLClass Mapping=KAON2Manager.factory().owlClass(pdURI+"#Mapping");
    	Individual newMapping = KAON2Manager.factory().individual(mapping.getURI());
    	ObjectProperty mappingSource=KAON2Manager.factory().objectProperty(pdURI+"#mappingSource");
    	ObjectProperty mappingTarget=KAON2Manager.factory().objectProperty(pdURI+"#mappingTarget");
    	ObjectProperty mappingProvider=KAON2Manager.factory().objectProperty(pdURI+"#mappingProvider");
    	DataProperty ontologyURL = KAON2Manager.factory().dataProperty(omv+ Constants.URI);
    	try{
    		
    	String url = util.Utilities.getString(mapping.getDataPropertyValue(remoteOntologyRegistry,ontologyURL)); //(String)
    	Individual source = (Individual) mapping.getObjectPropertyValue(remoteOntologyRegistry,mappingSource);
    	Individual target = (Individual) mapping.getObjectPropertyValue(remoteOntologyRegistry,mappingTarget);
    	Individual provider = (Individual) mapping.getObjectPropertyValue(remoteOntologyRegistry,mappingProvider);
    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(Mapping,newMapping),OntologyChangeEvent.ChangeType.ADD));
    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(mappingSource,newMapping,source),OntologyChangeEvent.ChangeType.ADD));
    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(mappingTarget,newMapping,target),OntologyChangeEvent.ChangeType.ADD));
    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(mappingProvider,newMapping,provider),OntologyChangeEvent.ChangeType.ADD));
    	if(url !=null)
    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyURL,newMapping,KAON2Manager.factory().constant(url)),OntologyChangeEvent.ChangeType.ADD));
    	   
    	localOntologyRegistry.applyChanges(changes); 
    	}catch(KAON2Exception e){
    		System.err.println(e.toString()+"updateMappingOntology()in advertInformer.");
    	}
    }
    
    public synchronized void addExpertiseTopic(Individual topic,Individual superTopic)throws Exception{
    	String baseURI = localOntologyRegistry.getOntologyURI();
    	OWLClass typeTopic = KAON2Manager.factory().owlClass(pdURI+"#Topic");
    	ObjectProperty subTopic = KAON2Manager.factory().objectProperty(pdURI + "#subTopic");
    	ObjectProperty subTopicOf = KAON2Manager.factory().objectProperty(pdURI + "#subTopicOf");
    	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    	if(!localOntologyRegistry.containsEntity(topic,true))
    		changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(typeTopic,topic),OntologyChangeEvent.ChangeType.ADD));
    	if(superTopic != null){
    		
    		if(!localOntologyRegistry.containsAxiom(KAON2Manager.factory().classMember(typeTopic,superTopic),true)){
    			System.out.println("add as topic individual");
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(typeTopic,superTopic),OntologyChangeEvent.ChangeType.ADD));
    		
    		}
    		if(!localOntologyRegistry.containsAxiom(KAON2Manager.factory().objectPropertyMember(subTopic,superTopic,topic),true)){
    			System.out.println("add property:");
    			System.out.println(KAON2Manager.factory().objectPropertyMember(subTopic,superTopic,topic));
    		    System.out.println(KAON2Manager.factory().objectPropertyMember(subTopicOf,topic,superTopic));
    			System.out.println(changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(subTopic,superTopic,topic),OntologyChangeEvent.ChangeType.ADD)));  
    		    System.out.println(changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(subTopicOf,topic,superTopic),OntologyChangeEvent.ChangeType.ADD)));  
    		    
    		}
    	}
    	localOntologyRegistry.applyChanges(changes); 
    	changes.clear();
    	System.out.println(localOntologyRegistry.containsAxiom(KAON2Manager.factory().objectPropertyMember(subTopic,superTopic,topic),true));
    }
    
    public synchronized void addExpertiseOntology(Individual topic,Map ontologyMap,Ontology targetOntology)throws Exception{
    	//String baseURI = localOntologyRegistry.getOntologyURI();
    	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    	OWLClass typeTopic = KAON2Manager.factory().owlClass(pdURI+"#Ontology");
    	ObjectProperty ExpertiseOntologies=KAON2Manager.factory().objectProperty(pdURI+"#expertiseOntology");
  	    ObjectProperty ImportOntologies=KAON2Manager.factory().objectProperty(pdURI+"#importOntology"); 
  	    Iterator it = ((Collection)ontologyMap.keySet()).iterator();
  	    while(it.hasNext()){
  	    	String ontologyAdd =(String) it.next();
  	    	//ontologySet.add(ontologyAdd);
  	    	Individual ontologyIndiv=KAON2Manager.factory().individual(ontologyAdd);   
  	    	if(!targetOntology.containsAxiom(KAON2Manager.factory().objectPropertyMember(ExpertiseOntologies,topic,ontologyIndiv),true)){
  	    		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ExpertiseOntologies,topic,ontologyIndiv),OntologyChangeEvent.ChangeType.ADD));
  	    		if(!targetOntology.containsAxiom(KAON2Manager.factory().classMember(typeTopic,ontologyIndiv),true))
  	    		changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(typeTopic,ontologyIndiv),OntologyChangeEvent.ChangeType.ADD));
  	    		
  	    	    System.out.println("For topic: "+topic.getURI()+"expertiseOntology added:"+ontologyIndiv);
  	    	}
  	    	Collection importSet = (Collection)ontologyMap.get(ontologyAdd);
  	    	if(importSet==null) importSet = new LinkedList();
  	    	Iterator im = importSet.iterator();
  	    	while(im.hasNext()){
  	    		String ontologyImport = im.next().toString();
  	    		Individual importIndiv = KAON2Manager.factory().individual(ontologyImport.toString());
  	    		if(!targetOntology.containsAxiom(KAON2Manager.factory().objectPropertyMember(ImportOntologies,ontologyIndiv,importIndiv),true)){
  	    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ImportOntologies,ontologyIndiv,importIndiv),OntologyChangeEvent.ChangeType.ADD));
  	    			if(!targetOntology.containsAxiom(KAON2Manager.factory().classMember(typeTopic,importIndiv),true))
  	    				changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(typeTopic,importIndiv),OntologyChangeEvent.ChangeType.ADD));
  	    			System.out.println("For ontology: "+ontologyIndiv.getURI()+"importOntology added:"+importIndiv);
  	    		}  
  	    	}
  	    }
  	  targetOntology.applyChanges(changes); 
  	    changes.clear();
    }
  
    public synchronized void updateRegistry(Ontology remoteOntologyRegistry,Ontology localOntologyRegistry){
      String remoteURI = remoteOntologyRegistry.getOntologyURI();
  	  String localURI = localOntologyRegistry.getOntologyURI();
  	  try{
  		  Map remotePeerSet = getPeerSet(remoteOntologyRegistry);
  		  Map localPeerSet = getPeerSet(localOntologyRegistry);
  		  //Collection remoteMappingSet = getMappingSet(remoteOntologyRegistry);
  		  //Collection localMappingSet = getMappingSet(localOntologyRegistry);
  		  int remoteVersion = getRegistryVersion(remoteOntologyRegistry);
  		  int localVersion = getRegistryVersion(localOntologyRegistry);
  		  if(localVersion > remoteVersion)return;
  		  Collection remoteGUIDs = remotePeerSet.keySet();
  		  Iterator remotePeer = remoteGUIDs.iterator();
  		  Collection localGUIDs = localPeerSet.keySet();
  		  //Iterator mapping = remoteMappingSet.iterator();
  		  while(remotePeer.hasNext()){
  			  String peerGUID = (String) remotePeer.next();
  			  System.out.println("remoteContains:"+peerGUID);  			  
  			  if(!peerGUID.equals(this.getLocalUID())){  			  
  				  Individual peerIndiv =(Individual) remotePeerSet.get(peerGUID);
  				  if(!localGUIDs.contains(peerGUID)){
  					  System.out.println("local registry doesn't contain: "+peerGUID+". The peer that will be added is: "+peerIndiv);
  					  
  					  //I DONT WANT TO ADD A PEER OFFLINE
  					  Ontology remoteRegistry = null;
  					  String IP= "";
  					  IP = getPeerIP(remoteOntologyRegistry,peerIndiv);
  					  remoteRegistry = openRemoteRegistry(IP);
  					  if (remoteRegistry!=null)
  						  addExpertisePeer(remoteOntologyRegistry,peerIndiv,localOntologyRegistry);
  					  else
  						  System.out.println("Peer was not reachable when trying to add it, so it was not added");
  				  }
  				  else
  					  updatePeerAttributes(remoteOntologyRegistry,peerIndiv,localOntologyRegistry);
  			  }
  		  }
  		  /*
  		  while(mapping.hasNext()){
  			  System.out.println("mapping testing!");
  			  Individual mappingIndiv = (Individual) mapping.next();
  			  if(!containsMapping(localOntologyRegistry,mappingIndiv)){
  				  addMappingOntology(remoteOntologyRegistry,mappingIndiv,localOntologyRegistry); 
  				  System.out.println("add Mapping: "+mappingIndiv);
  			  }
  		  }
  		  */
  		  updateRegistryVersion(localOntologyRegistry);
  		  localOntologyRegistry = mOyster2.getLocalHostOntology();
  		  localOntologyRegistry.persist();
  		  save(localOntologyRegistry);
  	  }catch(Exception e){
  		  System.err.println(e+" when localOntologyRegistry is persisted in updateRegistry()of AdvertInformer.");
  	  }
    }
    
    public void updateLocalRegistry(){
    	try{
    		Ontology localOntology = mOyster2.getLocalHostOntology();
    		localOntology.persist();
    		save(localOntology);
    	}catch(Exception e){
    		System.err.println(e+" when updateLocalRegistry.");
    	}
    }
    
    public synchronized void informerIP(Ontology remoteOntology,Ontology localOntology){
	  try{
	  List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
	  DataProperty IPAdress = KAON2Manager.factory().dataProperty(pdURI+"#IPAdress");
	  String localGUID = mOyster2.getLocalHost().getGUID().toString();
	  String newIP = mOyster2.getLocalHost().getAddress();
	  String localOldIP ="";
	  System.out.println("new IPAddress of the localhost: "+ newIP);
	  Map peerSet = getPeerSet(remoteOntology);
	  Individual peerIndiv =(Individual)peerSet.get(localGUID);
	  try{
		  if(peerIndiv!=null){
			  localOldIP = util.Utilities.getString(peerIndiv.getDataPropertyValue(localOntology,IPAdress)); //(String)
			  String remoteOldIP = util.Utilities.getString(peerIndiv.getDataPropertyValue(remoteOntology,IPAdress)); //(String)
			  System.out.println("remoteOldIP in remote peer: "+remoteOldIP);
			  if(remoteOldIP !=null)
				  changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(IPAdress,peerIndiv,KAON2Manager.factory().constant(remoteOldIP)),OntologyChangeEvent.ChangeType.REMOVE));
			  changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(IPAdress,peerIndiv,KAON2Manager.factory().constant(newIP)),OntologyChangeEvent.ChangeType.ADD));
			  remoteOntology.applyChanges(changes);
		  }
		  else {
			  peerIndiv = getLocalPeer();
			  localOldIP = util.Utilities.getString(peerIndiv.getDataPropertyValue(localOntology,IPAdress));
			  System.out.println("add local peer to remote.");
			  if(peerIndiv!=null)
				  addExpertisePeer(localOntology,peerIndiv,remoteOntology);
			  else 
				  System.err.println("localPeer is null in localRegistry when informerIP process runs");
		  }
	  
	  }
	  catch(Exception e){
		  System.err.println(e);
	  }
	 
	  remoteOntology.persist();
	  
	  if (!localOldIP.equals(newIP)){
		  System.out.println("localOldIP: "+localOldIP +" newIP: "+newIP);
		  List<OntologyChangeEvent> changes2=new ArrayList<OntologyChangeEvent>();
		  changes2.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(IPAdress,peerIndiv,KAON2Manager.factory().constant(localOldIP)),OntologyChangeEvent.ChangeType.REMOVE));
		  changes2.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(IPAdress,peerIndiv,KAON2Manager.factory().constant(newIP)),OntologyChangeEvent.ChangeType.ADD));
		  localOntology.applyChanges(changes2);
		  localOntology.persist();
		  save(localOntology);
	  }
	  }catch(Exception e){
		  System.out.println(e+":when InformerIP().");
	  }
  }
    
  public int getRegistryVersion(Ontology regOntology){
	  Collection versionSet = new LinkedList();
	  try{
	   versionSet = regOntology.getOntologyPropertyValues(versionInfo);
	  }catch(Exception e){
		  System.err.println(e+" in getRegistryVersion()in AdverInformer");
	  }
	  Iterator it = versionSet.iterator();
	  String version = it.next().toString();
	  int versionNo = Integer.parseInt(version);
      return versionNo;
  }
  
  public void updateRegistryVersion(Ontology regOntology){
	  int versionNo =getRegistryVersion(regOntology);
	  String version = Integer.toString(versionNo);
	  try{
		  regOntology.removeOntologyProperty(versionInfo,version);
		  versionNo++;
		  version = Integer.toString(versionNo);
		  regOntology.addOntologyProperty(versionInfo,version);
	  }catch(Exception e){
		  System.err.println(e+ " updateRegistryVersion() in AdvertInformer.");
	  }
  }  
  
  public Ontology getLocalRegistry(){
	  return localOntologyRegistry;
  }
  
  public String getBaseRegistryURI(){
	  return pdURI;
  }
  
  public void save(Ontology ontology)throws Exception{
	  ontology.saveOntology(OntologyFileFormat.OWL_RDF,localRegistryFile,"ISO-8859-1");
  }
  
  public void persist(Ontology ontology)throws Exception{
	  ontology.persist();
  }
  
  public void closeConnection()throws Exception{
	  //localConnection.close();
	  remoteConnection.close();
  }
}
