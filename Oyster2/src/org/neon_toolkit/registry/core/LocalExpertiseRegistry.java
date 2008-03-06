package org.neon_toolkit.registry.core;


import java.io.*;
import java.util.*;

import org.neon_toolkit.registry.oyster2.Constants;
import org.neon_toolkit.registry.oyster2.Oyster2Factory;
import org.neon_toolkit.registry.oyster2.Oyster2Host;
import org.neon_toolkit.registry.oyster2.Oyster2Query;
import org.neon_toolkit.registry.oyster2.QueryReply;
import org.neon_toolkit.registry.util.GUID;
import org.neon_toolkit.registry.util.Property;
import org.neon_toolkit.registry.util.Resource;
//import org.semanticweb.kaon2.api.DefaultOntologyResolver;
import org.semanticweb.kaon2.api.Entity;
import org.semanticweb.kaon2.api.KAON2Connection;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Namespaces;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.formatting.*;
import org.semanticweb.kaon2.api.owl.axioms.DataPropertyMember;
import org.semanticweb.kaon2.api.owl.elements.DataProperty;
import org.semanticweb.kaon2.api.owl.elements.Individual;
import org.semanticweb.kaon2.api.owl.elements.OWLClass;
import org.semanticweb.kaon2.api.owl.elements.ObjectProperty;
import org.semanticweb.kaon2.api.reasoner.*;



/**
 * This class presents the local expertise registry, the query is posed against 
 * this local expertise registry. this class contains methodes which return back 
 * the query reply to the searcher Manager.
 * @author Guo
 *
 */
public class LocalExpertiseRegistry {

		
		protected Oyster2Host mLocalHost = null;
		/**
		 * If set, no more saves are allowed.
		 */
		//private int version = 0;
		//private boolean mShutdownFlag = false;
		private File localRegistryFile;
		private String localURI;
		private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
		private Ontology localOntologyRegistry;
		private KAON2Connection connection;
		//private DefaultOntologyResolver resolver;
		private Reasoner reasoner;
//		private AdvertInformer mInformer = mOyster2.getLocalAdvertInformer();
		//private File virtualFile;
		//private Ontology virtualOntology;
		
		public void init(){
			this.localOntologyRegistry = mOyster2.getLocalHostOntology();
			this.localURI = this.localOntologyRegistry.getOntologyURI();
			this.connection = mOyster2.getConnection();
			//this.resolver = mOyster2.getResolver();
			this.localRegistryFile = mOyster2.getLocalRegistryFile();
			//this.virtualOntology = mOyster2.getVirtualOntology();
			//this.virtualFile = mOyster2.getVirtualOntologyFile();
			try{
				//this.reasoner = this.localRegistry.createReasoner();
			}catch(Exception e){
				System.err.println(e+ "in LocalExpertiseRegistry init()");
			}
		}
		/**
		 * search in localRegistry about all peers that provides the ontologies
		 * that that satisfy the conditions of the original query
		 * @param topicQuery
		 * @param typeQuery
		 * @return
		 * @throws Exception
		 */
		public boolean searchExpertiseOntology(Ontology mOntology, Oyster2Query topicQuery,boolean manualSelected){
			boolean positiveResult = false; 
			String queryStr = topicQuery.getQueryString();
		  
			try{	
				//localOntologyRegistry = mOyster2.getLocalHostOntology();
				//this.reasoner=localOntologyRegistry.createReasoner();
				this.reasoner=mOntology.createReasoner();
				Query query=reasoner.createQuery(Namespaces.INSTANCE,queryStr);
				query.open();
				while (!query.afterLast()) {
					positiveResult = true;
					String ontologyURI = query.tupleBuffer()[0].toString();
					Individual ontologyIndiv =KAON2Manager.factory().individual(ontologyURI);
					Query myQuery = reasoner.createQuery(Namespaces.INSTANCE,"SELECT ?x WHERE {<"+ontologyURI +"> <"+mOyster2.getPeerDescOntologyURI()+"#"+Constants.ontologyOMVLocation+"> ?x }");  //The same as //Query myQuery = reasoner.createQuerySPARQL(Namespaces.INSTANCE,"SELECT ?x WHERE {?x <http://localhost/basicRegistry#provideOntology>"+ "<"+ontologyURI +">}");
					myQuery.open();
					while (!myQuery.afterLast()) {
						String peerStr = myQuery.tupleBuffer()[0].toString();
						Individual peerIndiv = KAON2Manager.factory().individual(peerStr);
						System.out.println(peerIndiv+" provideOntology: "+ontologyIndiv);
						myQuery.next();
					}
					myQuery.close();
					myQuery.dispose();
					query.next();
				}
				query.close();
				query.dispose();
				reasoner.dispose();
			}catch(Exception e){
				System.err.println(e.toString()+":LocalExpertiseRegistry searchExpertiseRegistry  "+e.getLocalizedMessage());
			}
			return positiveResult;
		}
				
		/** 
		 * this methode query the local registry and return the reply to UI viewer.
		 * @param  mOntology
		 * @param  newQuery
		 * @return Query Reply directly to UI
		 */
		
		public QueryReply returnQueryReply(Ontology mOntology, Oyster2Query newQuery,int resourceType){ 
			Collection<Resource> resourceSet= new ArrayList<Resource>();
			GUID queryUID = newQuery.getGUID();
			
			Resource replyResource;
			String queryStr =newQuery.getQueryString();//queryStr ="SELECT ?x WHERE  { ?x <http://omv.ontoware.org/2005/05/ontology#keywords> "hola" }"
			try{
				Reasoner reasoner=mOntology.createReasoner();
				Query query=reasoner.createQuery(Namespaces.INSTANCE,queryStr);
				query.open();
				while (!query.afterLast()) {
					System.out.println("ontologyURI Or Whatever: "+query.tupleBuffer()[0].toString());
					String docURI = query.tupleBuffer()[0].toString();
					Individual indiv =KAON2Manager.factory().individual(docURI);
					//Filter
					try{
						/*
						 * TESTING
						 * Storing the whole information from reply i.e. get the property set
						 * now that i know which peer has this information
						 */
						OWLClass typeClass = (OWLClass)indiv.getDescriptionsMemberOf(mOntology).toArray()[0];
						Collection<Property> propertySet=getReplyPropertySet(mOntology,indiv);
						int required=0;
						Iterator a = propertySet.iterator();
						while (a.hasNext()){
							Property t=(Property)a.next();
							if (t.getPred().equalsIgnoreCase(KAON2Manager.factory().dataProperty(Constants.OMVURI + Constants.name).toString()) || 
									t.getPred().equalsIgnoreCase(KAON2Manager.factory().dataProperty(Constants.OMVURI + Constants.URI).toString())){
								required++;
							}
						}
						if (required>=2){
							replyResource = new Resource(indiv.getURI(),indiv, propertySet, typeClass, resourceType);
							resourceSet.add(replyResource);
						}
						/* 
						 * end of testing
						 */
						//String tName = indiv.getDataPropertyValue(mOntology, KAON2Manager.factory().dataProperty(Constants.OMVURI + Constants.name)).toString(); //mOyster2.getLocalHostOntology()
						//String tURI = indiv.getDataPropertyValue(mOntology, KAON2Manager.factory().dataProperty(Constants.OMVURI + Constants.URI)).toString(); //mOyster2.getLocalHostOntology()
						//if (tName!=null && tURI!=null){
						//	replyResource = new Resource(indiv.getURI(),indiv, resourceType);
						//	resourceSet.add(replyResource);
						//}
						
					}catch(Exception e){
	
					}
					query.next();
				}
				query.close();
				query.dispose();
				reasoner.dispose();
			}catch(Exception e){
				System.err.println(e.toString()+":LocalExpertiseRegistry,returnQueryReply()");
				return null;	
			}	
			QueryReply queryReply = new QueryReply(queryUID,resourceSet,mOntology);
			return queryReply;			
		}
		
		public QueryReply returnQueryReplyGeneral(Ontology mOntology, Oyster2Query newQuery,int resourceType){ 
			Collection<Resource> resourceSet= new ArrayList<Resource>();
			GUID queryUID = newQuery.getGUID();
			
			Resource replyResource;
			String queryStr =newQuery.getQueryString();//queryStr ="SELECT ?x WHERE  { ?x <http://omv.ontoware.org/2005/05/ontology#keywords> "hola" }"
			try{
				Reasoner reasoner=mOntology.createReasoner();
				Query query=reasoner.createQuery(Namespaces.INSTANCE,queryStr); 
				query.open();
				while (!query.afterLast()) {
					System.out.println("URI: "+query.tupleBuffer()[0].toString());
					String docURI = query.tupleBuffer()[0].toString();
					Individual indiv =KAON2Manager.factory().individual(docURI);
					//Filter
					/*
					 * TESTING
					 * Storing the whole information from reply i.e. get the property set
					 * now that i know which peer has this information
					 */
					OWLClass typeClass = (OWLClass)indiv.getDescriptionsMemberOf(mOntology).toArray()[0];
					Collection<Property> propertySet=getReplyPropertySet(mOntology,indiv);
					replyResource = new Resource(indiv.getURI(),indiv, propertySet, typeClass, resourceType);
					resourceSet.add(replyResource);
					/* 
					 * end of testing
					 */
					//replyResource = new Resource(indiv.getURI(),indiv,resourceType);
					//resourceSet.add(replyResource);
					query.next();
				}
				query.close();
				query.dispose();
				reasoner.dispose();
			}catch(Exception e){
				System.err.println(e.toString()+":LocalExpertiseRegistry,returnQueryReply()");
				return null;	
			}	
			QueryReply queryReply = new QueryReply(queryUID,resourceSet,mOntology);
			return queryReply;			
		}
		
		public QueryReply returnQueryReply(Ontology mOntology, Oyster2Query topicQuery, Oyster2Query typeQuery, int resourceType){
			QueryReply queryReply =null;
			QueryReply queryReply1 =null;
			QueryReply queryReply2 =null;
			Collection<Resource> resourceSet= new ArrayList<Resource>();
			Collection<Resource> rSet= new ArrayList<Resource>();
			try{
				if ((topicQuery!=null) && (topicQuery.getQueryString().length()>0)){
					OWLClass oConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.ontologyConcept);
					for(DataPropertyMember dp : mOntology.createAxiomRequest (DataPropertyMember.class).getAll()){
						String value = dp.getTargetValue().getValue().toString();
						//System.out.println(dp.toString());
						if(mOntology.containsAxiom(KAON2Manager.factory().classMember(oConcept,dp.getSourceIndividual()),true) && (!dp.getDataProperty().equals(KAON2Manager.factory().dataProperty(Constants.OMVURI + Constants.timeStamp)))){
							if(value.toLowerCase().contains(topicQuery.getQueryString().toLowerCase())){
								Individual docIndiv=dp.getSourceIndividual();
								try{
									/*
									 * TESTING
									 * Storing the whole information from reply i.e. get the property set
									 * now that i know which peer has this information
									 */
									OWLClass typeClass = (OWLClass)docIndiv.getDescriptionsMemberOf(mOntology).toArray()[0];
									Collection<Property> propertySet=getReplyPropertySet(mOntology,docIndiv);
									int required=0;
									Iterator a = propertySet.iterator();
									while (a.hasNext()){
										Property t=(Property)a.next();
										if (t.getPred().equalsIgnoreCase(KAON2Manager.factory().dataProperty(Constants.OMVURI + Constants.name).toString()) || 
												t.getPred().equalsIgnoreCase(KAON2Manager.factory().dataProperty(Constants.OMVURI + Constants.URI).toString())){
											required++;
										}
									}
									if (required>=2){
										Resource replyResource = new Resource(docIndiv.getURI(),docIndiv, propertySet, typeClass, resourceType);
										
										Iterator it = resourceSet.iterator();
										boolean in=false;
										while(it.hasNext()){
											final Resource entry =(Resource) it.next();
											if (entry.getEntity().equals(replyResource.getEntity())) in=true;
										}
										if (!in)resourceSet.add(replyResource);
									}
									/* 
									 * end of testing
									 */
									//String tName = docIndiv.getDataPropertyValue(mOntology, KAON2Manager.factory().dataProperty(Constants.OMVURI + Constants.name)).toString();
									//String tURI = docIndiv.getDataPropertyValue(mOntology, KAON2Manager.factory().dataProperty(Constants.OMVURI + Constants.URI)).toString();
									//if (tName!=null && tURI!=null){			
										//System.out.println("yes , "+docIndiv);
									//	Resource replyResource = new Resource(docIndiv.getURI(),docIndiv,Resource.DataResource);
									//	Iterator it = resourceSet.iterator();
									//	boolean in=false;
									//	while(it.hasNext()){
									//		final Resource entry =(Resource) it.next();
									//		if (entry.getEntity().equals(replyResource.getEntity())) in=true;
									//	}
									//	if (!in)resourceSet.add(replyResource);
									//}
								}catch(Exception e){	
								}
							}
						}
					}
					queryReply1 = new QueryReply(topicQuery.getGUID(),resourceSet,mOntology);
				}
				if((typeQuery != null) && (typeQuery.getQueryString().length()>0)){
					queryReply2 = returnQueryReply(mOntology,typeQuery,Resource.DataResource); //mKaonP2P.getVirtualOntology()
				}
				//Filter intersection
				if (queryReply1!=null && queryReply2!=null){
					Iterator it1 = queryReply1.getResourceSet().iterator();
					while(it1.hasNext()){
						final Resource  entry =(Resource) it1.next();
						Iterator it2 = queryReply2.getResourceSet().iterator();
						while(it2.hasNext()){
							final Resource en =(Resource) it2.next();
							if (en.getEntity().equals(entry.getEntity())) rSet.add(en);
						}
					}
					queryReply = new QueryReply(topicQuery.getGUID(),rSet,mOntology);
				}
				else if (queryReply1!=null) queryReply=queryReply1;
				else if (queryReply2!=null) queryReply=queryReply2;
			}catch (Exception e) {
				System.out.println("localExpertiseRegistry Keyword search problem: "+e);
				return null;
			}
			return queryReply;
		}
		
		
		public Collection<Property> getReplyPropertySet(Ontology virtualOntology,Individual docIndiv){
			Collection<Property> propertySet = new ArrayList<Property>();
			Property replyProperty = null;
			try{
			Map dataPropertyMap = docIndiv.getDataPropertyValues(virtualOntology);
			Map objectPropertyMap = docIndiv.getObjectPropertyValues(virtualOntology);
			Collection keySet = dataPropertyMap.keySet();
			Iterator keys = keySet.iterator();
			//System.out.println(" Reply: "+docIndiv.getURI());
			while(keys.hasNext()){
				String keyStr = keys.next().toString();
				DataProperty property = KAON2Manager.factory().dataProperty(keyStr);
				String	propertyValue = org.neon_toolkit.registry.util.Utilities.getString(docIndiv.getDataPropertyValue(virtualOntology,property));
				replyProperty = new Property(keyStr,propertyValue);
				propertySet.add(replyProperty);
				//System.out.println("    "+Namespaces.guessLocalName(keyStr)+": "+propertyValue);	
				}
			
			keySet = objectPropertyMap.keySet();
			Iterator okeys = keySet.iterator();
			while(okeys.hasNext()){
				String keyStr = okeys.next().toString();
				ObjectProperty property = KAON2Manager.factory().objectProperty(keyStr);
				Collection propertyCol= new LinkedList();
				propertyCol = (Collection)objectPropertyMap.get(property);
				if(propertyCol==null)System.err.println("propertyCol is null");
				Iterator it = propertyCol.iterator();
				while(it.hasNext()){
					Entity propertyValue =(Entity) it.next();
					replyProperty = new Property(keyStr,propertyValue);
					propertySet.add(replyProperty);
					//System.out.println("    "+Namespaces.guessLocalName(keyStr)+": "+propertyValue.toString());	
				}
				
			}
			}catch(Exception e){
				System.err.println(e.toString()+" :getReplyPropertySet() in LocalRegistry");
			}
			return propertySet;
		}
		
		/**
		 * If oyster2 is shutdown the routing table is saved to a file.
		 */
		public synchronized void shutdown()throws Exception {
			//mShutdownFlag = true;
			save();
		}
		
		public void save()throws Exception{
		     
        		localOntologyRegistry.saveOntology(OntologyFileFormat.OWL_RDF,localRegistryFile,"ISO-8859-1");
        		//localRegistry.persist();
		     
        }
		
		public Ontology getLocalOntoRegister(){
			return this.localOntologyRegistry;
		}
		
		public String getLocalRegistryURI(){
			localURI= localOntologyRegistry.getOntologyURI();
			return localURI;
		}
		
		public void close()throws Exception{
		connection.close();
		}

}


/*
public void importToVirtualOntology(List ontologySelectedSet){
	Collection importOntologySet = new LinkedList();
	importOntologySet =	this.virtualOntology.getImportedOntologies();
	Iterator it = importOntologySet.iterator();
	try{
		while(it.hasNext()){
			Ontology importedOntology = (Ontology)it.next(); 
			virtualOntology.removeFromImports(importedOntology);		
		}
		localRegistry = mOyster2.getLocalHostOntology();
		virtualOntology.persist();
		virtualOntology.addToImports(localRegistry);
		this.reasoner=localRegistry.createReasoner();
		Iterator io = ontologySelectedSet.iterator();
		while(io.hasNext()){
			Object obj = io.next();
			if(obj instanceof Individual){
				Individual ontologyIndiv = (Individual)obj;
				String ontologyURI = ontologyIndiv.getURI();
				final String queryStr = "SELECT ?x WHERE {<"+ontologyURI +"> <"+mOyster2.getPeerDescOntologyURI()+"#ontologyLocation> ?x }"; 
				Query query=reasoner.createQuerySPARQL(Namespaces.INSTANCE,queryStr);
				query.open();
				while (!query.afterLast()) {
					String peerStr = query.tupleBuffer()[0].toString();
					Individual peerIndiv = KAON2Manager.factory().individual(peerStr);
					String logicalURI= null;
					try{
						
						DataProperty IPAdress = KAON2Manager.factory().dataProperty(mOyster2.getPeerDescOntologyURI()+"#IPAdress");
						String IP = (String)peerIndiv.getDataPropertyValue(localRegistry,IPAdress);
						logicalURI=resolver.registerOntology("kaon2rmi://"+IP+"?"+ontologyURI);
						connection.setOntologyResolver(resolver);
						Ontology ontologyToImport = connection.openOntology(logicalURI,new HashMap<String,Object>());
						if(!this.virtualOntology.getAllImportedOntologies().contains(ontologyToImport))
							virtualOntology.addToImports(ontologyToImport);
						System.out.println("ontology imported: "+ontologyURI);
					}catch(Exception e){
						System.err.println(e +"hehere!");
					}
					
					query.next();
				}
				query.close();
				query.dispose();
				final String queryStr1 = "SELECT ?x WHERE {<"+ontologyURI +"> <"+mOyster2.getPeerDescOntologyURI()+"#mappingProvider> ?x }"; 
				query=reasoner.createQuerySPARQL(Namespaces.INSTANCE,queryStr1);
				query.open();
				while (!query.afterLast()) {
					String peerStr = query.tupleBuffer()[0].toString();
					Individual peerIndiv = KAON2Manager.factory().individual(peerStr);
					String logicalURI= null;
					try{
						
						DataProperty IPAdress = KAON2Manager.factory().dataProperty(mOyster2.getPeerDescOntologyURI()+"#IPAdress");
						String IP = (String)peerIndiv.getDataPropertyValue(localRegistry,IPAdress);
						logicalURI=resolver.registerOntology("kaon2rmi://"+IP+"?"+ontologyURI);
						connection.setOntologyResolver(resolver);
						Ontology ontologyToImport = connection.openOntology(logicalURI,new HashMap<String,Object>());
						if(!this.virtualOntology.getAllImportedOntologies().contains(ontologyToImport))
							virtualOntology.addToImports(ontologyToImport);
					}catch(Exception e){
						System.err.println(e +"hehere!");
					}
					
					query.next();
				}
				query.close();
				query.dispose();
			}
			else if (obj instanceof Ontology){
				System.out.println(" local ontology is selected!"); 
			}
		}
		virtualOntology.persist();
		mOyster2.setVirtualOntology(virtualOntology);
	}catch(Exception e){
		
	}
	
	
}
*/