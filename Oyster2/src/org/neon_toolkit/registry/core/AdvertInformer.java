package org.neon_toolkit.registry.core;


import java.util.*;
import java.io.*;

import org.neon_toolkit.registry.api.change.ChangeSynchronization;
import org.neon_toolkit.registry.oyster2.Constants;
import org.neon_toolkit.registry.oyster2.Oyster2Factory;
import org.neon_toolkit.registry.util.GUID;
import org.semanticweb.kaon2.api.*;
import org.semanticweb.kaon2.api.owl.elements.*;
import org.semanticweb.kaon2.api.logic.*;
import org.semanticweb.kaon2.api.formatting.*;
//import org.semanticweb.kaon2.api.rules.*; OLD VERSION


public class AdvertInformer{
	private final static String omv = Constants.OMVURI;
	private final static String momv = Constants.MOMVURI;
	private final static String  versionInfo = Constants.VERSIONINFO;
	private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private String pdURI;
	private String localURI;
    private File localRegistryFile;
	//private KAON2Connection remoteConnection=KAON2Manager.newConnection();
	private DefaultOntologyResolver resolver=new DefaultOntologyResolver();
	private Ontology remoteOntologyRegistry = null;
	private Ontology localOntologyRegistry = null ;
	
	public  synchronized Ontology openRemoteRegistry(String remoteHostPort){
    	try{
    		mOyster2.getLogger().info("open RemoteRegistry at: "+remoteHostPort);
    		//String basicRegistry = "kaon2rmi://"+remoteHostPort+"?"+mOyster2.getPeerDescOntologyURI();
    		//this.localURI = this.localOntologyRegistry.getOntologyURI();
    		//String remoteRMIURI = "kaon2rmi://"+remoteHostPort+"?"+localURI;
    		//resolver.registerOntology(basicRegistry);
    		
    		String remoteRMIURI = "kaon2rmi://"+remoteHostPort+"?http://localhost/localRegistry";
    		resolver.registerReplacement(localURI,remoteRMIURI);
    		mOyster2.getConnection().setOntologyResolver(resolver);
    		remoteOntologyRegistry = mOyster2.getConnection().openOntology(remoteRMIURI,new HashMap<String,Object>());
    		mOyster2.getLogger().info("opened...: "+remoteHostPort);
    	}catch(Exception e){
    		//System.out.println(e+":when openRemoteRegistry().The reason may be remote server doesn't be started yet.");
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
   
    public Collection getOntologyDomain(Ontology regOntology,Individual ontologyIndiv){
    	Map propertyMap = new HashMap();
    	ObjectProperty hasDomain = null;
    	Collection domainSet = new LinkedList();
    	try{
    		 propertyMap = ontologyIndiv.getObjectPropertyValues(regOntology);
    		 hasDomain= KAON2Manager.factory().objectProperty(omv+Constants.hasDomain);
    		 domainSet =(Collection) propertyMap.get(hasDomain);
    	}catch(Exception e){
    		System.err.println(e.toString()+" getOntologyDomain in AdvertInformer.");
    	}
    	if (domainSet==null) return new LinkedList();
    	return domainSet;
    }
    
    public Collection getOntologyProvider(Ontology regOntology,Individual ontologyDoc){
    	Map propertyMap = new HashMap();
    	ObjectProperty location = null;
    	Collection peerSet = new LinkedList();
    	try{
    		 propertyMap = ontologyDoc.getObjectPropertyValues(regOntology);
    		 location= KAON2Manager.factory().objectProperty(pdURI+"#"+Constants.ontologyOMVLocation);
    		 peerSet =(Collection) propertyMap.get(location);
    	}catch(Exception e){
    		System.err.println(e.toString()+" getOntologyProvider in AdvertInformer.");
    	}
    	if (peerSet==null) return new LinkedList();
    	return peerSet;
    }
    
    
    public Collection getMappingProvider(Ontology regOntology,Individual mappingIndiv){
    	Map propertyMap = new HashMap();
    	ObjectProperty location = null;
    	Collection peerSet = new LinkedList();
    	try{
    		 propertyMap = mappingIndiv.getObjectPropertyValues(regOntology);
    		 location= KAON2Manager.factory().objectProperty(pdURI+"#"+Constants.mappingOMVLocation);
    		 peerSet =(Collection) propertyMap.get(location);
    	}catch(Exception e){
    		System.err.println(e.toString()+" getMappingProvider in AdvertInformer.");
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
    
    public Collection getMappings(Ontology regOntology,Individual peer) {
    	ObjectProperty provideMapping = KAON2Manager.factory().objectProperty(pdURI+"#provideMapping");
    	Collection mappingSet = new ArrayList();
    	try{
    		Map propertyMap = peer.getObjectPropertyValues(regOntology);
    		mappingSet = (Collection)propertyMap.get(provideMapping);
        }catch(Exception e){
        	System.err.println(e.toString()+"getMappings()in AdvertInformer.");
        }
        if(mappingSet == null)mappingSet = new ArrayList();
        return mappingSet;
    }
    
    public Collection getExpertise(Ontology regOntology,Individual peer) {
    	ObjectProperty hasExpertise = KAON2Manager.factory().objectProperty(pdURI+"#hasExpertise");
    	Collection domainSet = new ArrayList();
    	try{
    		Map propertyMap = peer.getObjectPropertyValues(regOntology);
    		domainSet = (Collection)propertyMap.get(hasExpertise);
        }catch(Exception e){
        	System.err.println(e.toString()+"getExpertise()in AdvertInformer.");
        }
        if(domainSet == null)domainSet = new ArrayList();
        return domainSet;
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
    	Map<String, Individual> guidTable = new HashMap<String, Individual>();
    	try{
    		OWLClass Peer = KAON2Manager.factory().owlClass(pdURI+"#Peer");
    		Set<Individual> peerSet = Peer.getMemberIndividuals(regOntology);
    		DataProperty GUID = KAON2Manager.factory().dataProperty(pdURI+"#GUID");
    		for(Individual indiv: peerSet){
    			String guid = org.neon_toolkit.registry.util.Utilities.getString(indiv.getDataPropertyValue(regOntology,GUID)); //OLD VERSION (String)
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
    	//List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    	List<Individual> peerList = new ArrayList<Individual>();
    	//Map guidTable = new HashMap();
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
        	IP = org.neon_toolkit.registry.util.Utilities.getString(peerIndiv.getDataPropertyValue(regOntology,IPAddress)); //NEW VALIDATION
        	}catch(Exception e){
        		System.err.println(e.toString()+" getMappingIP()in AdvertInformer.");
        	}
    	return IP;
    }
    
    public synchronized Map getRendezVousPeers(Ontology regOntology){
    	Map peerSet = getPeerSet(regOntology);
    	Map<String, String> RPeerMap = new HashMap<String, String>();
    	Collection guidList = peerSet.keySet();
    	DataProperty peerType = KAON2Manager.factory().dataProperty(pdURI+"#peerType");
    	DataProperty IPAdress = KAON2Manager.factory().dataProperty(pdURI+"#IPAdress");
    	Iterator peerGuids = guidList.iterator();
    	//String localUID=this.getLocalUID();
    	while(peerGuids.hasNext()){
    		String guid = (String)peerGuids.next();
    		Individual peerIndiv =(Individual) peerSet.get(guid);
    		if(peerIndiv== null)
    			System.err.println("peerIndiv is null when getRendezVousPeers.");
    		try{
    			String type = org.neon_toolkit.registry.util.Utilities.getString(peerIndiv.getDataPropertyValue(regOntology,peerType)); //(String)
    			if(type.equals("R")){//&&(!guid.equals(localUID))){
    				String IP = org.neon_toolkit.registry.util.Utilities.getString(peerIndiv.getDataPropertyValue(regOntology,IPAdress)); //(String)
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
    			local = getBoolean(org.neon_toolkit.registry.util.Utilities.getString(peerIndiv.getDataPropertyValue(regOntology,localPeer))); //(Boolean)
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
    		//System.out.println("the local peer is: "+ peerIndiv);
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
    		String ip = org.neon_toolkit.registry.util.Utilities.getString(peerIndiv.getDataPropertyValue(regOntology,IPAdress)); //(String)
    		//System.out.println("ip:"+ ip);
    	
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
    	//Map peerSet = getPeerSet(regOntology);
    	//Collection guidList = peerSet.values();
    	//DataProperty localPeer = KAON2Manager.factory().dataProperty(pdURI+"#localPeer");
    	DataProperty GUID = KAON2Manager.factory().dataProperty(pdURI+"#GUID");
    	Individual peerIndiv = getLocalPeer();
    	String UID = null;
    	try{
    		if(peerIndiv!=null){
    			UID = org.neon_toolkit.registry.util.Utilities.getString(peerIndiv.getDataPropertyValue(regOntology,GUID)); //(String)
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
    	mappingIP = org.neon_toolkit.registry.util.Utilities.getString(peerIndiv.getDataPropertyValue(regOntology,IPAddress)); //NEW VALIDATION
    	}catch(KAON2Exception e){
    		System.err.println(e.toString()+" getMappingIP()in AdvertInformer.");
    	}
    	return mappingIP;
    }
    
    public String getMappingURI(Ontology regOntology,Individual mappingIndiv){
    	DataProperty ontologyURL =  KAON2Manager.factory().dataProperty(omv+ Constants.URI);
    	String mappingURI = "";
    	try{
    		mappingURI = org.neon_toolkit.registry.util.Utilities.getString(mappingIndiv.getDataPropertyValue(regOntology,ontologyURL));
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
    
    public boolean containsMapping(Ontology ontology,Individual mappingIndiv){
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
    	//System.out.println("peer to add:"+newPeer.getURI());
    	DataProperty GUID = KAON2Manager.factory().dataProperty(pdURI + "#GUID");
    	try{
    		String uid = org.neon_toolkit.registry.util.Utilities.getString(newPeer.getDataPropertyValue(remoteOntologyRegistry,GUID));  //(String)
    		String olduid = org.neon_toolkit.registry.util.Utilities.getString(newPeer.getDataPropertyValue(targetOntology,GUID)); //(String)
    		OWLClass Peer = KAON2Manager.factory().owlClass(pdURI+"#Peer");
    		if(!targetOntology.containsAxiom(KAON2Manager.factory().classMember(Peer,newPeer),true)){
    			//System.out.println("add: "+newPeer.getURI()+ " to: "+targetOntology.getPhysicalURI() );
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(Peer,newPeer),OntologyChangeEvent.ChangeType.ADD));
    		}
    		else 
    			{
    			//System.err.println(targetOntology.getPhysicalURI()+" already contains: "+newPeer.getURI());
    			}
    		
    		if(olduid ==null||!olduid.equals(uid)){
    			//System.out.println("olduid in " + targetOntology.getPhysicalURI()+ " "+olduid + " uid in " +remoteOntologyRegistry.getPhysicalURI()+" "+uid);
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
    			//System.out.println("local registry contains already a peer with the same peer name: "+peerName);
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
    			//System.out.println("local registry contains already a peer with the same peer name: "+peerName);
    		}
    		else{
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(Peer,localPeerIndiv),OntologyChangeEvent.ChangeType.ADD));
    			/* OLD VERSION
    	    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(GUID,localPeerIndiv,uid),OntologyChangeEvent.ChangeType.ADD));
    	    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(type,localPeerIndiv,"R"),OntologyChangeEvent.ChangeType.ADD));
    	    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(localPeer,localPeerIndiv,false),OntologyChangeEvent.ChangeType.ADD));
    	    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ipAdr,localPeerIndiv,ip),OntologyChangeEvent.ChangeType.ADD));
    	    	*/
    			if (uid!=null && !uid.equalsIgnoreCase("")) changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(GUID,localPeerIndiv,KAON2Manager.factory().constant(uid)),OntologyChangeEvent.ChangeType.ADD));
    	    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(type,localPeerIndiv,KAON2Manager.factory().constant("R")),OntologyChangeEvent.ChangeType.ADD));
    	    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(localPeer,localPeerIndiv,KAON2Manager.factory().constant(false)),OntologyChangeEvent.ChangeType.ADD));
    	    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ipAdr,localPeerIndiv,KAON2Manager.factory().constant(ip)),OntologyChangeEvent.ChangeType.ADD));
    	    		
    	    	localOntologyRegistry.applyChanges(changes);
        		localOntologyRegistry.persist();
        		save(localOntologyRegistry);
        		//System.out.println("add bootstrap peer successfully.");
    
    		}
    	}catch(Exception e){
    		System.err.println(e.getMessage()+"in setBootPeer().");
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
    		oldIPStr = org.neon_toolkit.registry.util.Utilities.getString(peerIndiv.getDataPropertyValue(targetOntology,IPAdress)); //(String)
    		newIPStr = org.neon_toolkit.registry.util.Utilities.getString(peerIndiv.getDataPropertyValue(remoteOntologyRegistry,IPAdress)); //(String)
    		oldGUID = org.neon_toolkit.registry.util.Utilities.getString(peerIndiv.getDataPropertyValue(targetOntology,GUID)); //(String)
    		newGUID = org.neon_toolkit.registry.util.Utilities.getString(peerIndiv.getDataPropertyValue(remoteOntologyRegistry,GUID)); //(String)
        
    		//System.out.println("old IP: "+oldIPStr+" new IP: "+newIPStr);
    		//System.out.println("old GUID: "+oldGUID+" new GUID: "+newGUID);
    		//System.out.println("remoteontology "+remoteOntologyRegistry.getPhysicalURI()+" targetontology "+targetOntology.getPhysicalURI());
    	
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
    	Collection mappingSet = getMappings(remoteOntologyRegistry,peerIndiv);
    	Collection domainSet = getExpertise(remoteOntologyRegistry,peerIndiv);
    	Collection trackedOntoSet = getTrackedOntologies(remoteOntologyRegistry,peerIndiv);
    	
    	ObjectProperty ontologyOMVLocation= KAON2Manager.factory().objectProperty(pdURI+"#"+Constants.ontologyOMVLocation);
    	ObjectProperty mappingOMVLocation= KAON2Manager.factory().objectProperty(pdURI+"#"+Constants.mappingOMVLocation);
    	Collection ontologySetT = getOntologyDoc(targetOntology,peerIndiv);
    	Collection mappingSetT = getMappings(targetOntology,peerIndiv);
    	Collection domainSetT = getExpertise(targetOntology,peerIndiv);
    	Collection trackedOntoSetT = getTrackedOntologies(targetOntology,peerIndiv);
    	
    	ObjectProperty provideOntology = KAON2Manager.factory().objectProperty(pdURI + "#provideOntology");
    	ObjectProperty provideMapping = KAON2Manager.factory().objectProperty(pdURI + "#provideMapping");
    	ObjectProperty hasExpertise = KAON2Manager.factory().objectProperty(pdURI + "#hasExpertise");
    	ObjectProperty trackOntology= KAON2Manager.factory().objectProperty(pdURI+"#"+Constants.trackOntology);
        DataProperty peerType = KAON2Manager.factory().dataProperty(pdURI + "#peerType");
        DataProperty localPeer = KAON2Manager.factory().dataProperty(pdURI + "#localPeer");
        ObjectProperty contextOntology = KAON2Manager.factory().objectProperty(pdURI + "#contextOntology");
        OWLClass Peer = KAON2Manager.factory().owlClass(pdURI+"#Peer");
        OWLClass OntologyDoc=KAON2Manager.factory().owlClass(omv+"Ontology");
        OWLClass mappingClass=KAON2Manager.factory().owlClass(momv+"Mapping");
        OWLClass oDomainClass=KAON2Manager.factory().owlClass(omv+"OntologyDomain");
        updateIPAddress(remoteOntologyRegistry,peerIndiv,targetOntology);
    	//System.out.println("remote: "+remoteOntologyRegistry.getPhysicalURI()+" target: "+targetOntology.getPhysicalURI()+" peer: "+peerIndiv.getURI());
    	try{
    		Individual contextIndiv = getContextOntology(remoteOntologyRegistry,peerIndiv);
    		String type = org.neon_toolkit.registry.util.Utilities.getString(peerIndiv.getDataPropertyValue(remoteOntologyRegistry,peerType)); //(String)
    		//boolean local = getBoolean(util.Utilities.getString(peerIndiv.getDataPropertyValue(remoteOntologyRegistry,localPeer))); //(Boolean) 
    		if(!targetOntology.containsAxiom(KAON2Manager.factory().classMember(Peer,peerIndiv),true)){
    			changes.clear();
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(Peer,peerIndiv),OntologyChangeEvent.ChangeType.ADD));
	    		targetOntology.applyChanges(changes);
	    		//System.out.println("add peer.");
    		}
    		if(!targetOntology.containsAxiom(KAON2Manager.factory().objectPropertyMember(contextOntology,peerIndiv,contextIndiv),true)){
        		changes.clear();
        		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(contextOntology,peerIndiv,contextIndiv),OntologyChangeEvent.ChangeType.ADD));
        		targetOntology.applyChanges(changes);
        		//System.out.println("add context ontology.");
        	}
    		if(!targetOntology.containsAxiom(KAON2Manager.factory().dataPropertyMember(peerType,peerIndiv,KAON2Manager.factory().constant(type)),true)){
        		changes.clear();
        		changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(peerType,peerIndiv,KAON2Manager.factory().constant(type)),OntologyChangeEvent.ChangeType.ADD));
        		targetOntology.applyChanges(changes);
        		//System.out.println("add peer type.");
        	}
    		if(!targetOntology.containsAxiom(KAON2Manager.factory().dataPropertyMember(localPeer,peerIndiv,KAON2Manager.factory().constant(false)),true)){
        		changes.clear();
        		changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(localPeer,peerIndiv,KAON2Manager.factory().constant(false)),OntologyChangeEvent.ChangeType.ADD));
        		targetOntology.applyChanges(changes);
        		//System.out.println("localPeer (boolean attribute).");
        	}
    		
    		
    		//First remove all expertise
    		Iterator elementsT = ontologySetT.iterator();
    		while(elementsT.hasNext()){
    			Individual ontologyIndiv =(Individual) elementsT.next();
    			changes.clear();
    			if(targetOntology.containsAxiom(KAON2Manager.factory().objectPropertyMember(provideOntology,peerIndiv,ontologyIndiv),true)){
    				changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(provideOntology,peerIndiv,ontologyIndiv),OntologyChangeEvent.ChangeType.REMOVE));
    			}
    			if(targetOntology.containsAxiom(KAON2Manager.factory().objectPropertyMember(ontologyOMVLocation,ontologyIndiv,peerIndiv),true)){	
    				changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyOMVLocation,ontologyIndiv,peerIndiv),OntologyChangeEvent.ChangeType.REMOVE));
    			}
    			if (!changes.isEmpty()) {    				
    				targetOntology.applyChanges(changes);
    			}
        	}
    		//Now add expertise
    		Iterator elements = ontologySet.iterator();
    		while(elements.hasNext()){
    			Individual ontologyIndiv =(Individual) elements.next();
    			changes.clear();
    			if(!targetOntology.containsAxiom(KAON2Manager.factory().objectPropertyMember(provideOntology,peerIndiv,ontologyIndiv),true)){
    				changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(provideOntology,peerIndiv,ontologyIndiv),OntologyChangeEvent.ChangeType.ADD));
    				//System.out.println("add "+peerIndiv+" provide: "+ontologyIndiv);
    			}        	
    			if(!targetOntology.containsAxiom(KAON2Manager.factory().classMember(OntologyDoc,ontologyIndiv),true)){
    				//System.out.println("add OntologyIndiv to target registry :"+ontologyIndiv);
    				addExpertiseOntology(remoteOntologyRegistry,ontologyIndiv,targetOntology);
    			}
    			else {
    				if(!targetOntology.containsAxiom(KAON2Manager.factory().objectPropertyMember(ontologyOMVLocation,ontologyIndiv,peerIndiv),true)){
        				changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyOMVLocation,ontologyIndiv,peerIndiv),OntologyChangeEvent.ChangeType.ADD));
        			}
    				//System.out.println(targetOntology.getOntologyURI()+" already contains: "+ontologyIndiv.getURI());
    			}
    			if (!changes.isEmpty()) {    				
    				targetOntology.applyChanges(changes);
    			}
        	}
    		
    		//First remove all mapping expertise
    		elementsT=null;
    		elementsT = mappingSetT.iterator();
    		while(elementsT.hasNext()){
    			Individual mappingIndiv =(Individual) elementsT.next();
    			changes.clear();
    			if(targetOntology.containsAxiom(KAON2Manager.factory().objectPropertyMember(provideMapping,peerIndiv,mappingIndiv),true)){
    				changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(provideMapping,peerIndiv,mappingIndiv),OntologyChangeEvent.ChangeType.REMOVE));
    			}
    			if(targetOntology.containsAxiom(KAON2Manager.factory().objectPropertyMember(mappingOMVLocation,mappingIndiv,peerIndiv),true)){
    				changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(mappingOMVLocation,mappingIndiv,peerIndiv),OntologyChangeEvent.ChangeType.REMOVE));
    			}
    			if (!changes.isEmpty()) {
    				targetOntology.applyChanges(changes);
    			}
        	}
    		//Now add mapping expertise
    		elements = null;
    		elements = mappingSet.iterator();
    		while(elements.hasNext()){
    			Individual mappingIndiv =(Individual) elements.next();
    			changes.clear();
    			if(!targetOntology.containsAxiom(KAON2Manager.factory().objectPropertyMember(provideMapping,peerIndiv,mappingIndiv),true)){    				
    				changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(provideMapping,peerIndiv,mappingIndiv),OntologyChangeEvent.ChangeType.ADD));
    				//System.out.println("add "+peerIndiv+" provide: "+mappingIndiv);
    			}        	
    			if(!targetOntology.containsAxiom(KAON2Manager.factory().classMember(mappingClass,mappingIndiv),true)){
    				//System.out.println("add OntologyIndiv to target registry :"+mappingIndiv);
    				addMappingExpertise(remoteOntologyRegistry,mappingIndiv,targetOntology);
    			}
    			else {
    				if(!targetOntology.containsAxiom(KAON2Manager.factory().objectPropertyMember(mappingOMVLocation,mappingIndiv,peerIndiv),true)){
        				changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(mappingOMVLocation,mappingIndiv,peerIndiv),OntologyChangeEvent.ChangeType.ADD));
        			}
    				//System.out.println(targetOntology.getOntologyURI()+" already contains: "+mappingIndiv.getURI());
    			}
    			if (!changes.isEmpty()) {
    				targetOntology.applyChanges(changes);
    			}
        	}
    		
    		//First remove all tracked ontologies
    		elementsT=null;
    		elementsT = trackedOntoSetT.iterator();
    		while(elementsT.hasNext()){
    			Individual ontologyIndiv =(Individual) elementsT.next();
    			changes.clear();
    			if(targetOntology.containsAxiom(KAON2Manager.factory().objectPropertyMember(trackOntology,peerIndiv,ontologyIndiv),true)){
    				changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(trackOntology,peerIndiv,ontologyIndiv),OntologyChangeEvent.ChangeType.REMOVE));
    			}
    			if (!changes.isEmpty()) {    				
    				targetOntology.applyChanges(changes);
    			}
        	}
    		//Now add tracked ontologies
    		elements=null;
    		elements = trackedOntoSet.iterator();
    		while(elements.hasNext()){
    			Individual ontologyIndiv =(Individual) elements.next();
    			changes.clear();
    			if(!targetOntology.containsAxiom(KAON2Manager.factory().objectPropertyMember(trackOntology,peerIndiv,ontologyIndiv),true)){
    				changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(trackOntology,peerIndiv,ontologyIndiv),OntologyChangeEvent.ChangeType.ADD));
    				//System.out.println("add "+peerIndiv+" provide: "+ontologyIndiv);
    			}        	
    			if(!targetOntology.containsAxiom(KAON2Manager.factory().classMember(OntologyDoc,ontologyIndiv),true)){
    				//System.out.println("add OntologyIndiv to target registry :"+ontologyIndiv);
    				addExpertiseOntology(remoteOntologyRegistry,ontologyIndiv,targetOntology);
    			}
    			if (!changes.isEmpty()) {    				
    				targetOntology.applyChanges(changes);
    			}
        	}
    		
    		//First remove all domain expertise
    		elementsT=null;
    		elementsT = domainSetT.iterator();
    		while(elementsT.hasNext()){
    			Individual domainIndiv =(Individual) elementsT.next();
    			changes.clear();
    			if(targetOntology.containsAxiom(KAON2Manager.factory().objectPropertyMember(hasExpertise,peerIndiv,domainIndiv),true)){
    				changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(hasExpertise,peerIndiv,domainIndiv),OntologyChangeEvent.ChangeType.REMOVE));
    			}
    			if (!changes.isEmpty()) {
    				targetOntology.applyChanges(changes);
    			}
        	}
    		//Now add domain expertise
    		elements = null;
    		elements = domainSet.iterator();
    		while(elements.hasNext()){
    			Individual domainIndiv =(Individual) elements.next();
    			if(!targetOntology.containsAxiom(KAON2Manager.factory().objectPropertyMember(hasExpertise,peerIndiv,domainIndiv),true)){
    				changes.clear();
    				changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(hasExpertise,peerIndiv,domainIndiv),OntologyChangeEvent.ChangeType.ADD));
    				targetOntology.applyChanges(changes);
    				//System.out.println("add "+peerIndiv+" provide: "+hasExpertise);
    			}        	
    			if(!targetOntology.containsAxiom(KAON2Manager.factory().classMember(oDomainClass,domainIndiv),true)){
    				//System.out.println("add domainIndiv to target registry :"+domainIndiv);
    				addDomainExpertise(remoteOntologyRegistry,domainIndiv,targetOntology);
    			}
    			else {
    				//System.out.println(targetOntology.getOntologyURI()+" already contains: "+domainIndiv.getURI());
    			}
        	}
    		
    		//SYNCHRONIZE CHANGES
    		mOyster2.getLogger().info("remoteOntologyRegistry ..."+remoteOntologyRegistry.getPhysicalURI());
    		mOyster2.getLogger().info("targetOntology ..."+targetOntology.getPhysicalURI());
    		if (targetOntology==mOyster2.getLocalHostOntology())
    			ChangeSynchronization.SyncrhonizeChangesWithPeer(remoteOntologyRegistry, peerIndiv, targetOntology);
    		targetOntology.persist();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    public synchronized void addExpertiseOntology(Ontology remoteOntologyRegistry,Individual ontologyIndiv,Ontology targetOntology){
    	if (!mOyster2.getCaching()) return;
    	OWLClass OntologyDoc=KAON2Manager.factory().owlClass(omv+Constants.ontologyConcept);
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
    
    public synchronized void addMappingExpertise(Ontology remoteOntologyRegistry,Individual mappingIndiv,Ontology targetOntology){
    	if (!mOyster2.getCaching()) return;
    	OWLClass mappingClass=KAON2Manager.factory().owlClass(momv+Constants.mappingConcept);
    	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    	try{
    	if(!targetOntology.containsAxiom(KAON2Manager.factory().classMember(mappingClass,mappingIndiv),true))
    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(mappingClass,mappingIndiv),OntologyChangeEvent.ChangeType.ADD));
    	targetOntology.applyChanges(changes);
    	updateMappingAttributes(remoteOntologyRegistry,mappingIndiv,targetOntology);
    	}catch(Exception e){
    		System.err.println(e.toString()+": addMappingExpertise()in AdvertInformer");
    	}
    }
    
    public synchronized void addDomainExpertise(Ontology remoteOntologyRegistry,Individual domainIndiv,Ontology targetOntology){
    	OWLClass oDomainClass=KAON2Manager.factory().owlClass(omv+Constants.OntologyDomainConcept);
    	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    	try{
    	if(!targetOntology.containsAxiom(KAON2Manager.factory().classMember(oDomainClass,domainIndiv),true))
    	changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(oDomainClass,domainIndiv),OntologyChangeEvent.ChangeType.ADD));
    	targetOntology.applyChanges(changes);
    	updateDomainAttributes(remoteOntologyRegistry,domainIndiv,targetOntology);
    	}catch(Exception e){
    		System.err.println(e.toString()+": addMappingExpertise()in AdvertInformer");
    	}
    }
    
    public synchronized void updateDomainAttributes(Ontology remoteOntologyRegistry,Individual domainIndiv,Ontology targetOntology){
    	try{
    		
    	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    
    	DataProperty mappingURI = KAON2Manager.factory().dataProperty(omv + Constants.URI);
    	String oldURI = org.neon_toolkit.registry.util.Utilities.getString(domainIndiv.getDataPropertyValue(targetOntology,mappingURI)); //(String)
    	String newURI = org.neon_toolkit.registry.util.Utilities.getString(domainIndiv.getDataPropertyValue(remoteOntologyRegistry,mappingURI)); //(String)
    	
    	if(newURI != null){
    		if(oldURI==null){
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(mappingURI,domainIndiv,KAON2Manager.factory().constant(newURI)),OntologyChangeEvent.ChangeType.ADD));
    		}else{
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(mappingURI,domainIndiv,KAON2Manager.factory().constant(oldURI)),OntologyChangeEvent.ChangeType.REMOVE));
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(mappingURI,domainIndiv,KAON2Manager.factory().constant(newURI)),OntologyChangeEvent.ChangeType.ADD));
    		}
    	}
    	//TODO ONLY URI
        	
    	targetOntology.applyChanges(changes); 

    	}catch(Exception e){
    		System.err.println(e.toString()+" updateDomainAttributes()in advertInformer.");
    	}
    }

    
    public synchronized void updateMappingAttributes(Ontology remoteOntologyRegistry,Individual mappingIndiv,Ontology targetOntology){
    	try{
    		
    	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    
    	ObjectProperty location = KAON2Manager.factory().objectProperty(pdURI + "#"+Constants.mappingOMVLocation);
    	DataProperty mappingURI = KAON2Manager.factory().dataProperty(momv + Constants.URI);
    	String oldURI = org.neon_toolkit.registry.util.Utilities.getString(mappingIndiv.getDataPropertyValue(targetOntology,mappingURI)); //(String)
    	String newURI = org.neon_toolkit.registry.util.Utilities.getString(mappingIndiv.getDataPropertyValue(remoteOntologyRegistry,mappingURI)); //(String)
    	
    	if(newURI != null){
    		if(oldURI==null){
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(mappingURI,mappingIndiv,KAON2Manager.factory().constant(newURI)),OntologyChangeEvent.ChangeType.ADD));
    		}else{
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(mappingURI,mappingIndiv,KAON2Manager.factory().constant(oldURI)),OntologyChangeEvent.ChangeType.REMOVE));
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(mappingURI,mappingIndiv,KAON2Manager.factory().constant(newURI)),OntologyChangeEvent.ChangeType.ADD));
    		}
    	}
    	//TODO ONLY URI & LOCATION
    	
        Collection newPeerSet = getMappingProvider(remoteOntologyRegistry,mappingIndiv);
        Collection oldPeerSet = getMappingProvider(targetOntology,mappingIndiv);
        
    	Iterator it = newPeerSet.iterator();
    	while(it.hasNext()){
    		Individual newPeerIndiv = (Individual)it.next();
    		if(!oldPeerSet.contains(newPeerIndiv)){
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(location,mappingIndiv,newPeerIndiv),OntologyChangeEvent.ChangeType.ADD));
    			//targetOntology.applyChanges(changes); 
    		}
    	}
    	
    	targetOntology.applyChanges(changes); 

    	}catch(Exception e){
    		System.err.println(e.toString()+" updateMappingAttributes()in advertInformer.");
    	}
    }
    
    public synchronized void updateOntologyAttributes(Ontology remoteOntologyRegistry,Individual ontologyIndiv,Ontology targetOntology){
    	try{
    	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    	DataProperty ontologyName = KAON2Manager.factory().dataProperty(omv + Constants.name);
    	DataProperty ontologyURI = KAON2Manager.factory().dataProperty(omv + Constants.URI);
    	DataProperty ontologyResourceLocator = KAON2Manager.factory().dataProperty(omv + Constants.resourceLocator);
    	DataProperty ontologyVersion = KAON2Manager.factory().dataProperty(omv + Constants.version);
    	String oldName = org.neon_toolkit.registry.util.Utilities.getString(ontologyIndiv.getDataPropertyValue(targetOntology,ontologyName)); //(String)
    	String newName = org.neon_toolkit.registry.util.Utilities.getString(ontologyIndiv.getDataPropertyValue(remoteOntologyRegistry,ontologyName)); //(String)
    	String oldURI = org.neon_toolkit.registry.util.Utilities.getString(ontologyIndiv.getDataPropertyValue(targetOntology,ontologyURI)); //(String)
    	String newURI = org.neon_toolkit.registry.util.Utilities.getString(ontologyIndiv.getDataPropertyValue(remoteOntologyRegistry,ontologyURI)); //(String)
    	String oldResourceLocator = org.neon_toolkit.registry.util.Utilities.getString(ontologyIndiv.getDataPropertyValue(targetOntology,ontologyResourceLocator)); //(String)
    	String newResourceLocator = org.neon_toolkit.registry.util.Utilities.getString(ontologyIndiv.getDataPropertyValue(remoteOntologyRegistry,ontologyResourceLocator)); //(String)
    	String oldVersion = org.neon_toolkit.registry.util.Utilities.getString(ontologyIndiv.getDataPropertyValue(targetOntology,ontologyVersion)); //(String)
    	String newVersion = org.neon_toolkit.registry.util.Utilities.getString(ontologyIndiv.getDataPropertyValue(remoteOntologyRegistry,ontologyVersion)); //(String)
    	
    	if(newName!=null){
    		if(oldName == null){
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyName,ontologyIndiv,KAON2Manager.factory().constant(newName)),OntologyChangeEvent.ChangeType.ADD));
    			
    		}
    		else {
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyName,ontologyIndiv,KAON2Manager.factory().constant(oldName)),OntologyChangeEvent.ChangeType.REMOVE));
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyName,ontologyIndiv,KAON2Manager.factory().constant(newName)),OntologyChangeEvent.ChangeType.ADD)); 
    		}
    	}
    	if(newURI != null){
    		if(oldURI==null){
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyURI,ontologyIndiv,KAON2Manager.factory().constant(newURI)),OntologyChangeEvent.ChangeType.ADD));
    		}else{
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyURI,ontologyIndiv,KAON2Manager.factory().constant(oldURI)),OntologyChangeEvent.ChangeType.REMOVE));
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyURI,ontologyIndiv,KAON2Manager.factory().constant(newURI)),OntologyChangeEvent.ChangeType.ADD));
    		}
    	}
    	if(newResourceLocator != null){
    		if(oldResourceLocator==null){
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyResourceLocator,ontologyIndiv,KAON2Manager.factory().constant(newResourceLocator)),OntologyChangeEvent.ChangeType.ADD));
    		}else{
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyResourceLocator,ontologyIndiv,KAON2Manager.factory().constant(oldResourceLocator)),OntologyChangeEvent.ChangeType.REMOVE));
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyResourceLocator,ontologyIndiv,KAON2Manager.factory().constant(newResourceLocator)),OntologyChangeEvent.ChangeType.ADD));
    		}
    	}
    	if(newVersion != null){
    		if(oldVersion==null){
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyVersion,ontologyIndiv,KAON2Manager.factory().constant(newVersion)),OntologyChangeEvent.ChangeType.ADD));
    		}else{
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyVersion,ontologyIndiv,KAON2Manager.factory().constant(oldVersion)),OntologyChangeEvent.ChangeType.REMOVE));
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyVersion,ontologyIndiv,KAON2Manager.factory().constant(newVersion)),OntologyChangeEvent.ChangeType.ADD));
    		}
    	}
    	
    	//TODO WHAT ATTRIBUTES SHOULD WE TRANSFER (URI and LOCATION and version and name) or nothing?
    	
    	/*if((newPeerIndiv != null)&&(oldPeerIndiv != newPeerIndiv))
    		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(location,ontologyIndiv,newPeerIndiv),OntologyChangeEvent.ChangeType.ADD));*/
    	//-------------------------------------------------
    	ObjectProperty location = KAON2Manager.factory().objectProperty(pdURI + "#"+Constants.ontologyOMVLocation);
        Collection newPeerSet = getOntologyProvider(remoteOntologyRegistry,ontologyIndiv);
        Collection oldPeerSet = getOntologyProvider(targetOntology,ontologyIndiv);        
    	Iterator it = newPeerSet.iterator();
    	while(it.hasNext()){
    		Individual newPeerIndiv = (Individual)it.next();
    		if(!oldPeerSet.contains(newPeerIndiv)){
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(location,ontologyIndiv,newPeerIndiv),OntologyChangeEvent.ChangeType.ADD));
    			//targetOntology.applyChanges(changes); 
    		}
    	}
    	ObjectProperty hasDomain = KAON2Manager.factory().objectProperty(omv + Constants.hasDomain);
    	Collection newDomainSet = getOntologyDomain(remoteOntologyRegistry,ontologyIndiv);
        Collection oldDomainSet = getOntologyDomain(targetOntology,ontologyIndiv);        
    	Iterator it1 = newDomainSet.iterator();
    	while(it1.hasNext()){
    		Individual newDomainIndiv = (Individual)it1.next();
    		if(!oldDomainSet.contains(newDomainIndiv)){
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(hasDomain,ontologyIndiv,newDomainIndiv),OntologyChangeEvent.ChangeType.ADD)); 
    		}
    	}
    	
    	targetOntology.applyChanges(changes); 
    	/*
    	Collection importSet = getImportOntology(remoteOntologyRegistry,ontologyIndiv);
    	if(importSet != null)
    		addImportOntology(remoteOntologyRegistry,ontologyIndiv,targetOntology);
    	*/
    	}catch(Exception e){
    		System.err.println(e.toString()+" updateOntologyAttributes()in advertInformer.");
    	}
    }
    /*
     * Add useimports of the ontology individual in remote ontology registry to the 
     * corresponding ontology individual in the targetRegistry
     */
    public synchronized void addImportOntology(Ontology remoteOntologyRegistry,Individual ontologyIndiv,Ontology targetOntology){
    	try{
    	Collection importSet = getImportOntology(remoteOntologyRegistry,ontologyIndiv);
    	ObjectProperty imports = KAON2Manager.factory().objectProperty(omv + Constants.useImports);
    	//OWLClass OntologyDoc=KAON2Manager.factory().owlClass(omv+ Constants.ontologyConcept);
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
    		
    	String url = org.neon_toolkit.registry.util.Utilities.getString(mapping.getDataPropertyValue(remoteOntologyRegistry,ontologyURL)); //(String)
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
    	//String baseURI = localOntologyRegistry.getOntologyURI();
    	OWLClass typeTopic = KAON2Manager.factory().owlClass(pdURI+"#Topic");
    	ObjectProperty subTopic = KAON2Manager.factory().objectProperty(pdURI + "#subTopic");
    	ObjectProperty subTopicOf = KAON2Manager.factory().objectProperty(pdURI + "#subTopicOf");
    	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    	if(!localOntologyRegistry.containsEntity(topic,true))
    		changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(typeTopic,topic),OntologyChangeEvent.ChangeType.ADD));
    	if(superTopic != null){
    		
    		if(!localOntologyRegistry.containsAxiom(KAON2Manager.factory().classMember(typeTopic,superTopic),true)){
    			//System.out.println("add as topic individual");
    			changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(typeTopic,superTopic),OntologyChangeEvent.ChangeType.ADD));
    		
    		}
    		if(!localOntologyRegistry.containsAxiom(KAON2Manager.factory().objectPropertyMember(subTopic,superTopic,topic),true)){
    			//System.out.println("add property:");
    			System.out.println(KAON2Manager.factory().objectPropertyMember(subTopic,superTopic,topic));
    		    System.out.println(KAON2Manager.factory().objectPropertyMember(subTopicOf,topic,superTopic));
    			System.out.println(changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(subTopic,superTopic,topic),OntologyChangeEvent.ChangeType.ADD)));  
    		    System.out.println(changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(subTopicOf,topic,superTopic),OntologyChangeEvent.ChangeType.ADD)));  
    		    
    		}
    	}
    	localOntologyRegistry.applyChanges(changes); 
    	changes.clear();
    	//System.out.println(localOntologyRegistry.containsAxiom(KAON2Manager.factory().objectPropertyMember(subTopic,superTopic,topic),true));
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
  	    		
  	    	    //System.out.println("For topic: "+topic.getURI()+"expertiseOntology added:"+ontologyIndiv);
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
  	    			//System.out.println("For ontology: "+ontologyIndiv.getURI()+"importOntology added:"+importIndiv);
  	    		}  
  	    	}
  	    }
  	  targetOntology.applyChanges(changes); 
  	    changes.clear();
    }
  
    /**
     * Update local peer every "discoveryFrec" seconds i.e. within the exchangeinitiator 
     * process.
     * Gets the list of all known peers from the remote peer and update the local registry
     * with them i.e. if it is not known by the local peer it is added, if is alrady known
     * the information is updated with the information from the remote peer.
     * 
     * @param remoteOntologyRegistry
     * @param localOntologyRegistry
     */
    
    public synchronized void updateRegistry(Ontology remoteOntologyRegistry,Ontology localOntologyRegistry){
  	  try{
  		  //Collection remoteMappingSet = getMappingSet(remoteOntologyRegistry);
  		  //Collection localMappingSet = getMappingSet(localOntologyRegistry);
  		  //Iterator mapping = remoteMappingSet.iterator();
  		  Map remotePeerSet = getPeerSet(remoteOntologyRegistry);
  		  Map localPeerSet = getPeerSet(localOntologyRegistry);
  		  int remoteVersion = getRegistryVersion(remoteOntologyRegistry);
  		  int localVersion = getRegistryVersion(localOntologyRegistry);
  		  if(localVersion > remoteVersion)return;
  		  Collection remoteGUIDs = remotePeerSet.keySet();
  		  Iterator remotePeer = remoteGUIDs.iterator();
  		  Collection localGUIDs = localPeerSet.keySet();
  		  
  		  while(remotePeer.hasNext()){
  			  String peerGUID = (String) remotePeer.next();
  			  if(!peerGUID.equals(this.getLocalUID())){  			  
  				  Individual peerIndiv =(Individual) remotePeerSet.get(peerGUID);
  				  if(!localGUIDs.contains(peerGUID)){
  					  //System.out.println("local registry doesn't contain: "+peerGUID+". The peer that will be added is: "+peerIndiv);
  					  
  					  //I DONT WANT TO ADD A PEER OFFLINE
  					  //Ontology remoteOntologyRegistrytoAdd = null;
  					  //String IP= "";
  					  //IP = getPeerIP(remoteOntologyRegistry,peerIndiv);
  					  //remoteOntologyRegistrytoAdd = openRemoteRegistry(IP);
  					  //if (remoteOntologyRegistrytoAdd!=null)
  						  addExpertisePeer(remoteOntologyRegistry,peerIndiv,localOntologyRegistry);
  					  //else
  						//  System.out.println("Peer was not reachable when trying to add it, so it was not added");
  				  }
  				  else{
  					  //System.out.println("local registry already contain: "+peerGUID+". The peer that will be updated is: "+peerIndiv);
  					  updatePeerAttributes(remoteOntologyRegistry,peerIndiv,localOntologyRegistry);
  				  }
  			  }
  		  }
  		  //1A
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
    
    
    /**
     * AVERTISE TO REMOTE PEER (I.E. TO THE RENDEVZVOUS PEER)
     * Every time the peer starts, informerIP runs to update local peer information in remote peer.
     * e.g. inform to rendezvous peer.
     * Add local peer to remote registry. If it exists already, the information is updated in the
     * remote peer with the current local information.
     * The local peer in the local registry is also updated with the current IP address in case 
     * the peer has dynamic IP
     * 
     * @param remoteOntology
     * @param localOntology
     */
    public synchronized void informerIP(Ontology remoteOntology,Ontology localOntology, String rendezvouzPeerIP){
	  try{
		  //List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		  DataProperty IPAdress = KAON2Manager.factory().dataProperty(pdURI+"#IPAdress");
		  String localGUID = mOyster2.getLocalHost().getGUID().toString();
	  
		  String newIP = mOyster2.getLocalHost().getAddress();
		  String localOldIP ="";
		  Map peerSet = getPeerSet(remoteOntology);
		  Individual peerIndiv =(Individual)peerSet.get(localGUID);
	  
		  //UPDATE LOCAL IP ADDRESS IN PEER INDIVIDUAL
		  localOldIP = org.neon_toolkit.registry.util.Utilities.getString(getLocalPeer().getDataPropertyValue(localOntology,IPAdress)); //(String)
		  if (localOldIP==null || !localOldIP.equals(newIP)){
			  //System.out.println("localOldIP: "+localOldIP +" newIP: "+newIP);
			  List<OntologyChangeEvent> changes2=new ArrayList<OntologyChangeEvent>();
			  changes2.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(IPAdress,peerIndiv,KAON2Manager.factory().constant(localOldIP)),OntologyChangeEvent.ChangeType.REMOVE));
			  changes2.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(IPAdress,peerIndiv,KAON2Manager.factory().constant(newIP)),OntologyChangeEvent.ChangeType.ADD));
			  localOntology.applyChanges(changes2);
			  localOntology.persist();
			  save(localOntology);
		  }
	  
		  //NOW UPDATE LOCAL PEER INFORMATION IN RENDEZVOUZ PEER
		  try{
			  if(peerIndiv!=null){
				  //System.out.println("update local peer to remote.");
				  //1B
				  updatePeerAttributes(localOntology,peerIndiv,remoteOntology);
			  }
			  else {
				  //System.out.println("add local peer to remote.");
				  peerIndiv = getLocalPeer();
				  if(peerIndiv!=null){
					  addExpertisePeer(localOntology,peerIndiv,remoteOntology);
				  }
				  else 
					  System.err.println("localPeer is null in localRegistry when informerIP process runs");
			  }
	  
		  }
		  catch(Exception e){
			  System.err.println(e);
		  }
	  
		  /* NOT NECESSARY */
		  //UPDATE GUID
		  /*
	  	  try{
		  	Iterator it = peerSet.values().iterator();
		  	while (it.hasNext()){
			  Individual peer = (Individual)it.next();
			  String add=getPeerIP(remoteOntology,peer);
			  if (add.equalsIgnoreCase(rendezvouzPeerIP)){
				  List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
				  DataProperty GUID = KAON2Manager.factory().dataProperty(pdURI + "#GUID");
				  String oldGUID = org.neon_toolkit.registry.util.Utilities.getString(peer.getDataPropertyValue(localOntology,GUID)); //(String)
				  String newGUID = org.neon_toolkit.registry.util.Utilities.getString(peer.getDataPropertyValue(remoteOntology,GUID)); //(String)
				  if(oldGUID !=null)
			        	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(GUID,peer,KAON2Manager.factory().constant(oldGUID)),OntologyChangeEvent.ChangeType.REMOVE));
			      changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(GUID,peer,KAON2Manager.factory().constant(newGUID)),OntologyChangeEvent.ChangeType.ADD));
			      localOntology.applyChanges(changes);
			      localOntology.persist();
			      save(localOntology);
			  }
		  	}
	  	  }catch(Exception e){
		  	System.err.println(e + " updating rendezvous GUID");
	  	  }
		   */
		  //END
		  remoteOntology.persist();
	  }catch(Exception e){
		  System.out.println(e+":when InformerIP().");
	  }
  }
    
  public synchronized void updateLocalIP(){
	  Ontology localOntology = mOyster2.getLocalHostOntology();
	  String localGUID = mOyster2.getLocalHost().getGUID().toString();
	  DataProperty IPAdress = KAON2Manager.factory().dataProperty(pdURI+"#IPAdress");
	  String newIP = mOyster2.getLocalHost().getAddress();
	  String localOldIP ="";
	  Map peerSet = getPeerSet(localOntology);
	  Individual peerIndiv =(Individual)peerSet.get(localGUID);
	  //UPDATE LOCAL IP ADDRESS IN PEER INDIVIDUAL
	  try {
		localOldIP = org.neon_toolkit.registry.util.Utilities.getString(getLocalPeer().getDataPropertyValue(localOntology,IPAdress));
		if (localOldIP==null || !localOldIP.equals(newIP)){
			  //System.out.println("localOldIP: "+localOldIP +" newIP: "+newIP);
			  List<OntologyChangeEvent> changes2=new ArrayList<OntologyChangeEvent>();
			  changes2.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(IPAdress,peerIndiv,KAON2Manager.factory().constant(localOldIP)),OntologyChangeEvent.ChangeType.REMOVE));
			  changes2.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(IPAdress,peerIndiv,KAON2Manager.factory().constant(newIP)),OntologyChangeEvent.ChangeType.ADD));
			  localOntology.applyChanges(changes2);
			  localOntology.persist();
			  try {
				save(localOntology);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		} 
	  }catch (KAON2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  } //(String)
	  
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
		  //System.out.println("Version Number updated to: "+version);
	  }catch(Exception e){
		  System.err.println(e+ " updateRegistryVersion() in AdvertInformer.");
	  }
  }  
  
  public Collection getTrackedOntologies(Ontology registry,Individual peer) {
  	ObjectProperty trackOntology = KAON2Manager.factory().objectProperty(Constants.POMVURI+Constants.trackOntology);
  	Collection ontologySet = new ArrayList();
  	try{
  		Map propertyMap = peer.getObjectPropertyValues(registry);
  		ontologySet = (Collection)propertyMap.get(trackOntology);
      }catch(Exception e){
      	System.err.println(e.toString()+"getOntology tracked ontologies in advertinfomer");
      }
      if(ontologySet == null)ontologySet = new ArrayList();
      return ontologySet;
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
	  //remoteConnection.close();
  }
}
//1A
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

//1B
//String remoteOldIP = org.neon_toolkit.registry.util.Utilities.getString(peerIndiv.getDataPropertyValue(remoteOntology,IPAdress)); //(String)
//if(remoteOldIP !=null)
//	  changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(IPAdress,peerIndiv,KAON2Manager.factory().constant(remoteOldIP)),OntologyChangeEvent.ChangeType.REMOVE));
//changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(IPAdress,peerIndiv,KAON2Manager.factory().constant(newIP)),OntologyChangeEvent.ChangeType.ADD));
//remoteOntology.applyChanges(changes);


/*
Individual subjectIndiv = getOntologySubject(remoteOntologyRegistry, ontologyIndiv);
Individual oldSubjectIndiv = getOntologySubject(targetOntology, ontologyIndiv);
if(subjectIndiv != null){
	if((oldSubjectIndiv==null))
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(hasDomain,ontologyIndiv,subjectIndiv),OntologyChangeEvent.ChangeType.ADD));
	else{
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(hasDomain,ontologyIndiv,oldSubjectIndiv),OntologyChangeEvent.ChangeType.REMOVE));
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(hasDomain,ontologyIndiv,subjectIndiv),OntologyChangeEvent.ChangeType.ADD));
	}
}
*/
