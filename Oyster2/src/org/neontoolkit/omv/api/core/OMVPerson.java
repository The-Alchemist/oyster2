package org.neontoolkit.omv.api.core;

import java.util.HashSet;
import java.util.Set;

/**
 * The class OMVPerson provides the object 
 * representation of the Person class of 
 * OMV ontology.
 * @author Raul Palma
 * @version 0.98, March 2007
 */
public class OMVPerson extends OMVParty{
	
	private String lastName;
	private String firstName;
	private Set<String> eMail = new HashSet<String>();
	private Set<String> phoneNumber = new HashSet<String>();
	private Set<String> faxNumber = new HashSet<String>();
	private Set <OMVOrganisation> isContactPerson =  new HashSet <OMVOrganisation>();
	
	private String hasRole;
	
	public OMVPerson()
	    {
	    }
	
	public void append(OMVPerson element)
    {
		if (this.getLastName()==null && element.getLastName()!=null) {this.setLastName(element.getLastName());return;}
		if (this.getFirstName()==null && element.getFirstName()!=null) {this.setFirstName(element.getFirstName());return;}
		if (element.getEmail().size()>0) {this.eMail.addAll(element.getEmail());return;} //if (this.getEMail()==null && element.getEMail()!=null) {this.setEMail(element.getEMail());return;}
		if (element.getPhoneNumber().size()>0) {this.phoneNumber.addAll(element.getPhoneNumber());return;} //if (this.getPhoneNumber()==null && element.getPhoneNumber()!=null) {this.setPhoneNumber(element.getPhoneNumber());return;}
		if (element.getFaxNumber().size()>0) {this.faxNumber.addAll(element.getFaxNumber());return;} //if (this.getFaxNumber()==null && element.getFaxNumber()!=null) {this.setFaxNumber(element.getFaxNumber());return;}
		if (element.getIsContactPerson().size()>0) {this.isContactPerson.addAll(element.getIsContactPerson());return;}
		
		if (this.getHasRole()==null && element.getHasRole()!=null) {this.setHasRole(element.getHasRole());return;}
    }
	
	public void setLastName(String newLastName)
	{
		this.lastName=newLastName;
	}
	
	public String getLastName()
	{
		return this.lastName;
	}
	
	public void setFirstName(String newFirstName)
	{
		this.firstName=newFirstName;
	}
	
	public String getFirstName()
	{
		return this.firstName;
	}
	
	public void addEmail (String newEmail)
	{
		this.eMail.add(newEmail);
	}
	
	public void removeEmail (String oldEmail)
	{
		this.eMail.remove(oldEmail);
	}
	
	public Set <String> getEmail()
	{
		return this.eMail;
	}
	
	public void addPhoneNumber (String newPhoneNumber)
	{
		this.phoneNumber.add(newPhoneNumber);
	}
	
	public void removePhoneNumber (String oldPhoneNumber)
	{
		this.phoneNumber.remove(oldPhoneNumber);
	}
	
	public Set <String> getPhoneNumber()
	{
		return this.phoneNumber;
	}
	
	public void addFaxNumber (String newFaxNumber)
	{
		this.faxNumber.add(newFaxNumber);
	}
	
	public void removeFaxNumber (String oldFaxNumber)
	{
		this.faxNumber.remove(oldFaxNumber);
	}
	
	public Set <String> getFaxNumber()
	{
		return this.faxNumber;
	}
	
	public void addIsContactPerson(OMVOrganisation newIsContactPerson)
	{
		this.isContactPerson.add(newIsContactPerson);
	}
	
	public void removeIsContactPerson(OMVOrganisation oldIsContactPerson)
	{
		this.isContactPerson.remove(oldIsContactPerson);
	}
	
	public Set <OMVOrganisation> getIsContactPerson()
	{
		return this.isContactPerson;
	}

	public void setHasRole(String newHasRole)
	{
		this.hasRole=newHasRole;
	}
	
	public String getHasRole()
	{
		return this.hasRole;
	}
}

/*
public void setEMail(String newEMail)
{
	this.eMail=newEMail;
}

public String getEMail()
{
	return this.eMail;
}

public void setPhoneNumber(String newPhoneNumber)
{
	this.phoneNumber=newPhoneNumber;
}

public String getPhoneNumber()
{
	return this.phoneNumber;
}
 
public void setFaxNumber(String newFaxNumber)
{
	this.faxNumber=newFaxNumber;
}

public String getFaxNumber()
{
	return this.faxNumber;
}
*/