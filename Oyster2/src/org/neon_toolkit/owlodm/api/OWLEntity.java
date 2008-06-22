package org.neon_toolkit.owlodm.api;



/**
 * The class OWLEntity provides the object 
 * representation of the OWLEntity OWLODM Object  
 * @author Raul Palma
 * @version 2.0, March 2008
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
		public DataProperty(String uri){
			this.URI=uri;
	    }
	
	}
	
	public static class Datatype extends OWLEntity{
		public Datatype(String uri){
			this.URI=uri;
	    }
	
	}
	
	public static class Individual extends OWLEntity{
		public Individual(String uri){
			this.URI=uri;
	    }
		
	}
	
	public static class ObjectProperty extends OWLEntity{
		public ObjectProperty(String uri){
			this.URI=uri;
	    }
	}
	
}
