package api;

/**
 * The class OMVLocation provides the object representation
 * of the Location class of OMV ontology.
 * @author Raul Palma
 * @version 0.1, March 2007
 */
public class OMVLocation {
	
	private String state;
	private String country;
	private String city;
	private String street;
		
	public OMVLocation()
	    {
	    }

	public void setState(String newState)
	{
		this.state=newState;
	}
	
	public String getState()
	{
		return this.state;
	}
	
	public void setCountry(String newCountry)
	{
		this.country=newCountry;
	}
	
	public String getCountry()
	{
		return this.country;
	}

	public void setCity(String newCity)
	{
		this.city=newCity;
	}
	
	public String getCity()
	{
		return this.city;
	}

	public void setStreet(String newStreet)
	{
		this.street=newStreet;
	}
	
	public String getStreet()
	{
		return this.street;
	}
	
}
