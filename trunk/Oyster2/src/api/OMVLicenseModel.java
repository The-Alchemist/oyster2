package api;

import java.util.HashSet;
import java.util.Set;

/**
 * The class OMVLicenseModel provides the object representation
 * of the LicenseModel class of OMV ontology.
 * @author Raul Palma
 * @version 0.1, March 2007
 */
public class OMVLicenseModel {
	
	private String name;
	private String acronym;
	private String description;
	private String documentation;
	private Set <OMVParty> specifiedBy = new HashSet <OMVParty>();
	
	public OMVLicenseModel()
	    {
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
	
	public void setDescription(String newDescription)
	{
		this.description=newDescription;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public void setDocumentation(String newDocumentation)
	{
		this.documentation=newDocumentation;
	}
	
	public String getDocumentation()
	{
		return this.documentation;
	}
	
	public void addSpecifiedBy(OMVParty newSpecifiedBy)
	{
		this.specifiedBy.add(newSpecifiedBy);
	}
	
	public void removeSpecifiedBy(OMVParty oldSpecifiedBy)
	{
		this.specifiedBy.add(oldSpecifiedBy);
	}
	
	public Set <OMVParty> getSpecifiedBy()
	{
		return this.specifiedBy;
	}
	
}
