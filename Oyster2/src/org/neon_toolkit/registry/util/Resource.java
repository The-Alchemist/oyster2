package org.neon_toolkit.registry.util;

import java.io.Serializable;
import java.util.*;
import org.semanticweb.kaon2.api.*;

public class Resource implements Entity,Serializable {
	private static final long serialVersionUID = 1L;
	public static final int OntologyResource = 0;
    public static final int DataResource = 1;
    public static final int RegistryResource = 2;
	private String uri;
    private Entity entity;
    private Ontology ontology;
    private Collection propertySet = new LinkedList();
    private int resourceType;
    public Resource(String uri,Entity entity, Collection propertySet,int resourceType){
    	this.uri = uri;
    	this.entity = entity;
    	this.propertySet = propertySet;
    	this.resourceType = resourceType;
    }
    public Resource(String uri, Entity entity, int resourceType){
    	this.uri = uri;
    	this.entity = entity;
    	this.resourceType = resourceType;
    	//this.propertySet = new LinkedList();
    }
    public Entity getEntity(){
    	return entity;
    }
    public Ontology getOntology(){
    	return ontology;
    }
    public void addAttribute(String attribute){
    	propertySet.add(attribute);
    }
    public Collection getPropertySet(){
    	return propertySet;
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
