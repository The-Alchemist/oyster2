package api;

/**
 * The class OMVOntologyDomain provides the object representation
 * of the OntologyDomain class of OMV ontology.
 * @author Raul Palma
 * @version 0.1, March 2007
 */
public class OMVOntologyDomain {
	
	private String URI;
	private String name;
	
	public OMVOntologyDomain()
	    {
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
}
