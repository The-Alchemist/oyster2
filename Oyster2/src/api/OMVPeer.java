package api;

import java.util.HashSet;
import java.util.Set;

/**
 * The class OMVPeer provides the object 
 * representation of the Peer class of 
 * OMV ontology extension.
 * @author Raul Palma
 * @version 0.1, March 2007
 */
public class OMVPeer {
	
	private String GUID;
	private String IPAdress;
	private Boolean localPeer;
	private String peerType;
	private OMVOntology contextOntology;
	private OMVPeer ontologyLocation;
	private Set <OMVOntology> provideOntology = new HashSet <OMVOntology>();
	
	public OMVPeer()
	    {
	    }
		
	public void setGUID(String newGUID)
	{
		this.GUID=newGUID;
	}
	
	public String getGUID()
	{
		return this.GUID;
	}
	
	public void setIPAdress(String newIPAdress)
	{
		this.IPAdress=newIPAdress;
	}
	
	public String getIPAdress()
	{
		return this.IPAdress;
	}
	
	public void setLocalPeer(Boolean newLocalPeer)
	{
		this.localPeer=newLocalPeer;
	}
	
	public Boolean getLocalPeer()
	{
		return this.localPeer;
	}
	
	public void setPeerType(String newPeerType)
	{
		this.peerType=newPeerType;
	}
	
	public String getPeerType()
	{
		return this.peerType;
	}
	
	public void setContextOntology(OMVOntology newContextOntology)
	{
		this.contextOntology=newContextOntology;
	}
	
	public OMVOntology getContextOntology()
	{
		return this.contextOntology;
	}

	public void setOntologyLocation(OMVPeer newOntologyLocation)
	{
		this.ontologyLocation=newOntologyLocation;
	}
	
	public OMVPeer getOntologyLocation()
	{
		return this.ontologyLocation;
	}
		 	
	public void addProvideOntology(OMVOntology newProvideOntology)
	{
		this.provideOntology.add(newProvideOntology);
	}
	
	public void removeProvideOntology(OMVOntology oldProvideOntology)
	{
		this.provideOntology.remove(oldProvideOntology);
	}
	
	public Set <OMVOntology> getProvideOntology()
	{
		return this.provideOntology;
	}

}
