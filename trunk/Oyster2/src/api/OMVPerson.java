package api;

import java.util.HashSet;
import java.util.Set;

/**
 * The class OMVPerson provides the object 
 * representation of the Person class of 
 * OMV ontology.
 * @author Raul Palma
 * @version 0.1, March 2007
 */
public class OMVPerson extends OMVParty{
	
	private String lastName;
	private String firstName;
	private String eMail;
	private String phoneNumber;
	private String faxNumber;
	private Set <OMVOrganisation> isContactPerson =  new HashSet <OMVOrganisation>();
	
	public OMVPerson()
	    {
	    }
	
	public void append(OMVPerson element)
    {
		if (this.getLastName()==null && element.getLastName()!=null) {this.setLastName(element.getLastName());return;}
		if (this.getFirstName()==null && element.getFirstName()!=null) {this.setFirstName(element.getFirstName());return;}
		if (this.getEMail()==null && element.getEMail()!=null) {this.setEMail(element.getEMail());return;}
		if (this.getPhoneNumber()==null && element.getPhoneNumber()!=null) {this.setPhoneNumber(element.getPhoneNumber());return;}
		if (this.getFaxNumber()==null && element.getFaxNumber()!=null) {this.setFaxNumber(element.getFaxNumber());return;}
		if (element.getIsContactPerson().size()>0) {this.isContactPerson.addAll(element.getIsContactPerson());return;}
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

}
