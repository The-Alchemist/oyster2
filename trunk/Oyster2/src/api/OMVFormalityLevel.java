package api;

/**
 * The class OMVFormalityLevel provides the object representation
 * of the FormalityLevel class of OMV ontology.
 * @author Raul Palma
 * @version 0.1, March 2007
 */
public class OMVFormalityLevel {
	
	private String name;
	private String description;
		
	public OMVFormalityLevel()
	    {
	    }
	
	public void append(OMVFormalityLevel element)
    {
		if (this.getName()==null && element.getName()!=null) {this.setName(element.getName());return;}
		if (this.getDescription()==null && element.getDescription()!=null) {this.setDescription(element.getDescription());return;}
    }
	
	public void setName(String newName)
	{
		this.name=newName;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setDescription(String newDescription)
	{
		this.description=newDescription;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
}
