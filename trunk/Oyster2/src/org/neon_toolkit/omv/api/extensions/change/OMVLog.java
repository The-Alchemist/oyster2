package org.neon_toolkit.omv.api.extensions.change;

import org.neon_toolkit.omv.api.core.OMVOntology;

/**
 * The class OMVLog provides the object 
 * representation of the log class of 
 * the change OMV ontology extension.
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class OMVLog {
	String URI;
	String hasLastChange; //Automatically generated
	OMVOntology hasRelatedOntology;
	public OMVLog()
    {
    }	
	
	public void append(OMVLog element)
    {
		if (this.getURI()==null && element.getURI()!=null) {this.setURI(element.getURI());return;}
		if (this.getHasLastChange()==null && element.getHasLastChange()!=null) {this.setHasLastChange(element.getHasLastChange());return;}
		if (this.getHasRelatedOntology()==null && element.getHasRelatedOntology()!=null) {this.setHasRelatedOntology(element.getHasRelatedOntology());return;}
    }
	
	public String getURI()
	{
		return this.URI;
	}
	public void setURI(String newURI)
	{
		this.URI=newURI;
	}
	
	public void setHasLastChange(String newHasLastChange)
	{
		this.hasLastChange=newHasLastChange;
	}
	
	public String getHasLastChange()
	{
		return this.hasLastChange;
	}
	public void setHasRelatedOntology(OMVOntology ontology)
	{
		this.hasRelatedOntology=ontology;
	}
	
	public OMVOntology getHasRelatedOntology()
	{
		return this.hasRelatedOntology;
	}
}
