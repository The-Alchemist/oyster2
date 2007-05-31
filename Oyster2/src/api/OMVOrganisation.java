package api;

import java.util.HashSet;
import java.util.Set;

/**
 * The class OMVOrganisation provides the object 
 * representation of the Organisation class of 
 * OMV ontology.
 * @author Raul Palma
 * @version 0.1, March 2007
 */
public class OMVOrganisation extends OMVParty{
	
	private String name;
	private String acronym;
	private Set <OMVPerson> hasContactPerson = new HashSet <OMVPerson>();
	
	public OMVOrganisation()
	    {
	    }
	
	public void append(OMVOrganisation element)
    {
		if (this.getName()==null && element.getName()!=null) {this.setName(element.getName());return;}
		if (this.getAcronym()==null && element.getAcronym()!=null) {this.setAcronym(element.getAcronym());return;}
		if (element.getHasContactPerson().size()>0) {this.hasContactPerson.addAll(element.getHasContactPerson());return;}
    }
	
	public void setName(String newName)
	{
		this.name=newName;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setAcronym(String newAcronym)
	{
		this.acronym=newAcronym;
	}
	
	public String getAcronym()
	{
		return this.acronym;
	}
	
	public void addHasContactPerson(OMVPerson newHasContactPerson)
	{
		this.hasContactPerson.add(newHasContactPerson);
	}
	
	public void removeHasContactPerson(OMVPerson oldHasContactPerson)
	{
		this.hasContactPerson.remove(oldHasContactPerson);
	}
	
	public Set <OMVPerson> getHasContactPerson()
	{
		return this.hasContactPerson;
	}
	 	
}
