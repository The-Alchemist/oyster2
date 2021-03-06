package org.neontoolkit.registry.core;


import java.util.*;

import org.neontoolkit.registry.oyster2.Oyster2Factory;
import org.semanticweb.kaon2.api.*;                 // This package contains the basic classes of the API
import org.semanticweb.kaon2.api.owl.elements.*;    // This package contains classes used to represent elements of OWL ontologies



public class OntoVocabulary {
	private List<String> vocabulary = new LinkedList<String>();
	private Ontology ontology;
	private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private AdvertInformer mInformer = mOyster2.getLocalAdvertInformer();
	private Entity rootEntity;
	public OntoVocabulary()throws Exception{
		this.ontology = mOyster2.getTypeOntology();
		this.rootEntity = mOyster2.getTypeOntologyRoot();
	}
	public  OntoVocabulary(Ontology ontology)throws Exception{
		this.ontology = ontology;
	}
	public OntoVocabulary(String physicalURI) throws Exception{
		this.ontology = mInformer.openRemoteRegistry(physicalURI);
	}
	
	public List getVocabulary(){
	  try{
	  Collection entitySet = getAllEntities();
	  Collection classSet = getAllClasses(entitySet);
	  Iterator it = classSet.iterator();
	    while(it.hasNext()){
	    	OWLClass classEntity = (OWLClass)it.next();
	    	String classURI = classEntity.getURI();
	    	String className = Namespaces.guessLocalName(classURI);
	    	Set<DataProperty> propertySet = classEntity.getDataPropertiesFrom(ontology);
	    	Iterator ip = propertySet.iterator();
	    	while(ip.hasNext()){
	        	DataProperty property = (DataProperty)ip.next();
	        	String propertyName = Namespaces.guessLocalName(property.getURI());
	        	if(!vocabulary.contains(propertyName))
		    		vocabulary.add(propertyName);
	        }
	    	
	    	if(!vocabulary.contains(className))
	    		vocabulary.add(className);
	    
	    }
	  }catch(KAON2Exception e){
		  System.err.println(e.toString()+"occurs when getting ontology vocabulary");
	  }
	  
	  return vocabulary;   
 }
	public List getAttributes(){
		  try{
		  Collection<Entity> entitySet = getAllEntities();
		  
		  Iterator it = entitySet.iterator();
		    while(it.hasNext()){
		    	Entity entry =(Entity) it.next();
		    	if(entry instanceof DataProperty){
		    		//System.err.println(entry.toString());
		    	Set<Description> domainCol =	((DataProperty)entry).getDomainDescriptions(ontology);
		    	if((domainCol.isEmpty()))
		    			System.err.println("domainCol is empty!");
		    	Iterator dom = domainCol.iterator();
		    	while(dom.hasNext())
		    		System.out.println(dom.next().toString());
		    	if(domainCol.contains((OWLClass)rootEntity)){
		    			String propertyURI = entry.getURI();
		    			String propertyLocalName = Namespaces.guessLocalName(propertyURI);
		    			vocabulary.add(propertyLocalName);
		    		}
		    	}
		    }
		  }catch(KAON2Exception e){
			  System.err.println(e.toString()+"occurs when getting ontology vocabulary");
		  }
		  
		  return vocabulary;   
	 }
	public Collection<Entity> getAllEntities()throws KAON2Exception{
		  Cursor<Entity> entities=ontology.createEntityRequest().openCursor();
		  Collection<Entity> entityList = new LinkedList<Entity>();
		  while (entities.hasNext()){
		      Entity entity = entities.next();
			  entityList.add(entity);
		  }
		  entities.close();
		  return entityList;
	}
	public Collection<OWLClass> getAllClasses(Collection entityList)throws KAON2Exception{
		Collection<OWLClass> classList = new LinkedList<OWLClass>();
		Iterator it = entityList.iterator();
		while(it.hasNext()){
			Entity entity =(Entity)it.next();
			if((entity instanceof OWLClass)&&(!classList.contains(entity))){
				classList.add((OWLClass)entity);
			}
		}
		return classList;
	}
	public Map getAllProperties()throws KAON2Exception{
	    Map propertySet = ontology.getOntologyProperties();
		if(propertySet.isEmpty())System.out.println(ontology.getOntologyURI()+" has no property!");
		return propertySet;
	}
	
	public Ontology getLocalOntology(){
		return this.ontology;
	}
	public String getNameSpace(Ontology ontology){
		return Namespaces.guessNamespace(ontology.getOntologyURI());
	}
	public String getNameSpace(){
		return Namespaces.guessNamespace(ontology.getOntologyURI());
	}
	public String getLocalName(){
		return Namespaces.guessLocalName(ontology.getOntologyURI());
	}
	public String getOntologyURI(){
		return ontology.getOntologyURI();
	}
	
	
}
