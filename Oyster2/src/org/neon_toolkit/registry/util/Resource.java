package org.neon_toolkit.registry.util;

import java.io.Serializable;
import java.util.*;

import org.semanticweb.kaon2.api.*;
import org.semanticweb.kaon2.api.owl.elements.OWLClass;

public class Resource implements Entity,Serializable {
	private static final long serialVersionUID = 1L;
	public static final int OntologyResource = 0;
    public static final int DataResource = 1;
    public static final int RegistryResource = 2;
	private String uri;
    private Entity entity;
    private Ontology ontology;
    //private Collection propertySet = new LinkedList();
    Collection<Property> propertySet = new ArrayList<Property>();
    private int resourceType;
    private OWLClass typeClass;
    public Resource(String uri,Entity entity, Collection<Property> propertySet,int resourceType){
    	this.uri = uri;
    	this.entity = entity;
    	this.propertySet = propertySet;
    	this.resourceType = resourceType;
    }
    public Resource(String uri,Entity entity, Collection<Property> propertySet, OWLClass typeClass, int resourceType){
    	this.uri = uri;
    	this.entity = entity;
    	this.propertySet = propertySet;
    	this.typeClass=typeClass;
    	this.resourceType = resourceType;
    }
    
    public Resource(String uri, Entity entity, int resourceType){
    	this.uri = uri;
    	this.entity = entity;
    	this.resourceType = resourceType;
    }
    public Entity getEntity(){
    	return entity;
    }
    public Ontology getOntology(){
    	return ontology;
    }
    public OWLClass getTypeClass(){
    	return typeClass;
    }
    public void addProperty(Property attribute){
    	propertySet.add(attribute);
    }
    
    public Collection getPropertySet(){
    	return propertySet;
    }
    
    public void setPropertySet(Collection<Property> propertyS){
    	this.propertySet= propertyS;
    }
    
    public int resouceType(){
    	return this.resourceType;
    }
    public void toString(StringBuffer buffer,Namespaces namespaces){
    	 buffer.append(namespaces.abbreviateAsNamespace(uri));
    }
    public String getURI(){
    	return this.uri;
    }
    public Object accept(KAON2Visitor visitor){
    	return new Object();
    	
    }
}
