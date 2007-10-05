package org.neon_toolkit.omv.api.core;

import java.util.HashSet;
import java.util.Set;

/**
 * The class OMVOntologyDomain provides the object representation
 * of the OntologyDomain class of OMV ontology.
 * @author Raul Palma
 * @version 0.1, March 2007
 */
public class OMVOntologyDomain {
	
	private String URI;
	private String name;
	private Set <OMVOntologyDomain> isSubDomainOf = new HashSet <OMVOntologyDomain>();
	
	public OMVOntologyDomain()
	    {
	    }
	
	public void append(OMVOntologyDomain element)
    {
		if (this.getURI()==null && element.getURI()!=null) {this.setURI(element.getURI());return;}
		if (this.getName()==null && element.getName()!=null) {this.setName(element.getName());return;}
		if (element.getIsSubDomainOf().size()>0) {this.isSubDomainOf.addAll(element.getIsSubDomainOf());return;}
    }
	
	public void setURI(String newURI)
	{
		this.URI=newURI;
	}
	
	public String getURI()
	{
		return this.URI;
	}

	public void setName(String newName)
	{
		this.name=newName;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void addIsSubDomainOf (OMVOntologyDomain newDomain)
	{
		this.isSubDomainOf.add(newDomain);
	}
	
	public void removeIsSubDomainOf (OMVOntologyDomain oldDomain)
	{
		this.isSubDomainOf.remove(oldDomain);
	}
	
	public Set <OMVOntologyDomain> getIsSubDomainOf()
	{
		return this.isSubDomainOf;
	}

	
}
