package org.neon_toolkit.owlodm.api;



/**
 * The class Axiom provides the object 
 * representation of the Axiom OWLODM Object  
 * @author Raul Palma
 * @version 0.1, May 2007
 */
public class OWLEntity {
	String URI;
	public OWLEntity()
    {
    }	

	public void append(OWLEntity element)
    {
		if (this.getURI()==null && element.getURI()!=null) {this.setURI(element.getURI());return;}
    }
	
	public void setURI(String newURI)
	{
		this.URI=newURI;
	}
	
	public String getURI()
	{
		return this.URI;
	}
	
	public static class DataProperty extends OWLEntity{
		public DataProperty(){
	    }
	
	}
	
	public static class Datatype extends OWLEntity{
		public Datatype(){
	    }
	
	}
	
	public static class Individual extends OWLEntity{
		public Individual(){
	    }
		
	}
	
	public static class ObjectProperty extends OWLEntity{
		public ObjectProperty(){
	    }
	}
	
	public static class OWLClass extends OWLEntity{
		public OWLClass(){
	    }
	}
	
}
