package core;

import util.*;

import java.io.*;
import java.util.*;

import org.semanticweb.kaon2.api.DefaultOntologyResolver;
import org.semanticweb.kaon2.api.Entity;
import org.semanticweb.kaon2.api.KAON2Connection;
import org.semanticweb.kaon2.api.KAON2Exception;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Namespaces;
import org.semanticweb.kaon2.api.Ontology;
//import org.semanticweb.kaon2.api.OntologyFileFormat; OLD VERSION
import org.semanticweb.kaon2.api.formatting.*;
import org.semanticweb.kaon2.api.owl.elements.DataProperty;
import org.semanticweb.kaon2.api.owl.elements.Individual;
import org.semanticweb.kaon2.api.owl.elements.ObjectProperty;
import org.semanticweb.kaon2.api.reasoner.*;

import util.Utilities;
import oyster2.*;
import util.GUID;
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
		private boolean mShutdownFlag = false;
		private File localRegistryFile;
		private String localURI;
		private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
		private Ontology localOntologyRegistry;
		private KAON2Connection connection;
		private DefaultOntologyResolver resolver;
		private AdvertInformer mInformer = mOyster2.getLocalAdvertInformer();
		private Reasoner reasoner;
		//private File virtualFile;
		//private Ontology virtualOntology;
		
		public void init(){
			this.localOntologyRegistry = mOyster2.getLocalHostOntology();
			this.localURI = this.localOntologyRegistry.getOntologyURI();
			this.connection = mOyster2.getConnection();
			this.resolver = mOyster2.getResolver();
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
		 * search in localRegistry about the expertiseOntologies and return virtual ontology.
		 * @param topicQuery
		 * @param typeQuery
		 * @return
		 * @throws Exception
		 */
		public boolean searchExpertiseOntology(Oyster2Query topicQuery,boolean manualSelected){
		  boolean positiveResult = false;
		  Collection importOntologySet = new LinkedList(); 
		  String queryStr = topicQuery.getQueryString();
		  if(!manualSelected){
			System.out.println("manualSelected: "+ manualSelected);
			try{	
				localOntologyRegistry = mOyster2.getLocalHostOntology();
				this.reasoner=localOntologyRegistry.createReasoner();
				System.out.println("First query: "+queryStr);
				Query query=reasoner.createQuery(Namespaces.INSTANCE,queryStr);
				query.open();
				while (!query.afterLast()) {
					positiveResult = true;
					String ontologyURI = query.tupleBuffer()[0].toString();
					Individual ontologyIndiv =KAON2Manager.factory().individual(ontologyURI);
					//Query myQuery = reasoner.createQuerySPARQL(Namespaces.INSTANCE,"SELECT ?x WHERE {?x <http://localhost/basicRegistry#provideOntology>"+ "<"+ontologyURI +">}");
					Query myQuery = reasoner.createQuery(Namespaces.INSTANCE,"SELECT ?x WHERE {<"+ontologyURI +"> <"+mOyster2.getPeerDescOntologyURI()+"#ontologyLocation> ?x }");
					System.out.println("SELECT ?x WHERE {<"+ontologyURI +"> <"+mOyster2.getPeerDescOntologyURI()+"#ontologyLocation> ?x }");
					myQuery.open();
					while (!myQuery.afterLast()) {
						String peerStr = myQuery.tupleBuffer()[0].toString();
						System.out.println(peerStr+" provideOntology: "+ontologyIndiv);
						Individual peerIndiv = KAON2Manager.factory().individual(peerStr);
						localOntologyRegistry = mOyster2.getLocalHostOntology();
						Individual targetIndiv = mInformer.getContextOntology(localOntologyRegistry,peerIndiv);
						String sourceURI = mOyster2.getTypeOntology().getOntologyURI();
						Individual sourceIndiv = KAON2Manager.factory().individual(sourceURI);
						try{
							if(targetIndiv != sourceIndiv){
								System.out.println("importMapping... source: "+sourceIndiv.toString()+" target: "+ targetIndiv.toString());
								Individual mappingIndiv=null;
								mappingIndiv = mInformer.getRelevantMapping(localOntologyRegistry,sourceIndiv,targetIndiv);
								System.out.println("relevant mapping is: "+ mappingIndiv.toString()+" source: "+sourceIndiv.toString()+" target: "+ targetIndiv.toString());
								if(mappingIndiv!=null){
									String IP = mInformer.getMappingIP(localOntologyRegistry,mappingIndiv);
									System.out.println("the mapping located at IP: "+IP);
									String mappingURI = mInformer.getMappingURI(localOntologyRegistry,mappingIndiv);
									String logicalURI=resolver.registerOntology("kaon2rmi://"+IP+"?"+mappingURI);
									connection.setOntologyResolver(resolver);
									Ontology mappingOntology = connection.openOntology(logicalURI,new HashMap<String,Object>());
									//if((mappingOntology!=null)&&(!this.virtualOntology.getImportedOntologies().contains(mappingOntology))){
									//	virtualOntology.addToImports(mappingOntology);	
									//}
								}
							}
						}catch(Exception e){
							System.err.println(e.toString());
						}
						String logicalURI= null;
						try{
							DataProperty IPAdress = KAON2Manager.factory().dataProperty(mOyster2.getPeerDescOntologyURI()+"#IPAdress");
							String IP = util.Utilities.getString(peerIndiv.getDataPropertyValue(localOntologyRegistry,IPAdress)); //(String)
							logicalURI=resolver.registerOntology("kaon2rmi://"+IP+"?"+ontologyURI);
							connection.setOntologyResolver(resolver);
							Ontology ontologyToImport = null;
							ontologyToImport = connection.openOntology(logicalURI,new HashMap<String,Object>());
						}catch(Exception e){
							System.err.println(e.toString()+" when opening ontology");
						}
						//System.out.println("ontologyToImport: "+ontologyToImport.getOntologyURI());
						//if(!this.virtualOntology.getImportedOntologies().contains(ontologyToImport))
							//virtualOntology.addToImports(ontologyToImport);
						myQuery.next();
					}
					myQuery.close();
					myQuery.dispose();
					query.next();
				}
				query.close();
				query.dispose();
				try{
					reasoner.dispose();
					//if(virtualOntology==null)System.err.println("virtualOntology null");
					//if(virtualFile ==null)System.err.println("virtualFile null");
					//virtualOntology.persist();
				}catch(Exception e){
					System.err.println(e.toString()+" I am Here below!");
				}
			}catch(Exception e){
				System.err.println(e.toString()+":LocalExpertiseRegistry searchExpertiseRegistry  "+e.getLocalizedMessage());
			}
			//mOyster2.setVirtualOntology(virtualOntology);
		  }
		  else {
			 try{
				Collection relevantOntologyList = new LinkedList();
				//Collection ontologyImportedCol = mOyster2.getVirtualOntology().getImportedOntologies();
				//System.out.println("in virtualontology: "+ontologyImportedCol.size());
				//Iterator vi = ontologyImportedCol.iterator();
				//while(vi.hasNext()){
				//	Ontology importedOntology = (Ontology)vi.next(); 
				//	virtualOntology.removeFromImports(importedOntology);
				//}
				//virtualOntology.persist();
				//System.out.println("in virtualontology: "+ontologyImportedCol.size());
				localOntologyRegistry = mOyster2.getLocalHostOntology();
				//virtualOntology.addToImports(localRegistry);
				this.reasoner=localOntologyRegistry.createReasoner();
				Query query=reasoner.createQuery(Namespaces.INSTANCE,queryStr);
				query.open();
				while (!query.afterLast()) {
					String ontologyURI = query.tupleBuffer()[0].toString();
					System.out.println("relevant ontologyURI: "+ ontologyURI);
					relevantOntologyList.add(ontologyURI);
					query.next();
				}
				query.close();
				query.dispose();
				//Iterator it = ontologyImportedCol.iterator();
				//while(it.hasNext()){
					//Ontology importedOntology = (Ontology)it.next(); 
					//String importURI = importedOntology.getOntologyURI();
					//System.out.println("importURI:"+importURI);
					//if(!relevantOntologyList.contains(importURI)&&(!importURI.equals(localRegistry.getOntologyURI()))){
					//	virtualOntology.removeFromImports(importedOntology);
					//	System.out.println("remove: "+importURI);
					//}
					//if(relevantOntologyList.contains(importURI))
					//virtualOntology.addToImports(importedOntology);
				//}
				//if(virtualOntology.getImportedOntologies().size()>1)
				positiveResult = true;
				//virtualOntology.persist();
				//mOyster2.setVirtualOntology(virtualOntology);		
			 }catch(Exception e){
				 System.out.println(e+" when manual!");
			 }	
		  }
	      return positiveResult;
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
		/** 
		 * this methode query the virtual ontology and return the reply to UI viewer.
		 * @param  mOntology
		 * @param  newQuery
		 * @return Query Reply directly to UI
		 */
		
		public QueryReply returnQueryReply(Ontology mOntology,Oyster2Query newQuery,int resourceType){ 
			Collection resourceSet= new ArrayList();
			GUID queryUID = newQuery.getGUID();
			
			Resource replyResource;
			String queryStr =newQuery.getQueryString();//queryStr ="SELECT ?x WHERE  { ?x <http://omv.ontoware.org/2005/05/ontology#keywords> "hola" }"
			try{
				Reasoner reasoner=mOntology.createReasoner();
				Query query=reasoner.createQuery(Namespaces.INSTANCE,queryStr); 
				query.open();
				    
				 while (!query.afterLast()) {
					 Object[] reply = query.tupleBuffer();
					 System.out.println("ontologyURI Or Whatever: "+query.tupleBuffer()[0].toString());
					 String docURI = query.tupleBuffer()[0].toString();
					 Individual docIndiv =KAON2Manager.factory().individual(docURI);
					 replyResource = new Resource(docIndiv.getURI(),docIndiv,resourceType);
					 resourceSet.add(replyResource);
					 query.next();
				 }
				 query.close();
				  //query.dispose();
				 reasoner.dispose();
			}catch(Exception e){
				System.err.println(e.toString()+":LocalExpertiseRegistry,returnQueryReply()");
				return null;	
			}	
			QueryReply queryReply = new QueryReply(queryUID,resourceSet,mOntology);
			return queryReply;			
		}
		
		
		public Collection getReplyPropertySet(Ontology virtualOntology,Individual docIndiv){
			Collection propertySet = new ArrayList();
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
				String	propertyValue = util.Utilities.getString(docIndiv.getDataPropertyValue(virtualOntology,property));
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
			mShutdownFlag = true;
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
			return localURI= localOntologyRegistry.getOntologyURI();
		}
		
		public void close()throws Exception{
		connection.close();
		}

}
