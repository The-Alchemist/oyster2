package org.neon_toolkit.omv.api.extensions.peer;

import java.util.HashSet;
import java.util.Set;
import org.neon_toolkit.omv.api.core.OMVOntology;
import org.neon_toolkit.omv.api.core.OMVOntologyDomain;
import org.neon_toolkit.omv.api.extensions.mapping.OMVMapping;

//import api.omv.core.*;
//import api.omv.extensions.mapping.*;

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
	private OMVPeer ontologyOMVLocation;
	private Set <OMVOntology> provideOntology = new HashSet <OMVOntology>();
	private Set <OMVPeer> acquaintedWith = new HashSet <OMVPeer>();
	private Set <OMVOntologyDomain> hasExpertise = new HashSet <OMVOntologyDomain>();
	private Set <OMVMapping> provideMapping = new HashSet <OMVMapping>();
	private Set <OMVOntology> trackOntology = new HashSet <OMVOntology>();
	private OMVPeer mappingOMVLocation;
	
	
	
	public OMVPeer()
	    {
	    }
	
	public void append(OMVPeer element)
    {
		if (this.getGUID()==null && element.getGUID()!=null) {this.setGUID(element.getGUID());return;}
		if (this.getIPAdress()==null && element.getIPAdress()!=null) {this.setIPAdress(element.getIPAdress());return;}
		if (this.getLocalPeer()==null && element.getLocalPeer()!=null) {this.setLocalPeer(element.getLocalPeer());return;}
		if (this.getPeerType()==null && element.getPeerType()!=null) {this.setPeerType(element.getPeerType());return;}
		if (this.getContextOntology()==null && element.getContextOntology()!=null) {this.setContextOntology(element.getContextOntology());return;}
		if (this.getOntologyOMVLocation()==null && element.getOntologyOMVLocation()!=null) {this.setOntologyOMVLocation(element.getOntologyOMVLocation());return;}
		if (element.getProvideOntology().size()>0) {this.provideOntology.addAll(element.getProvideOntology());return;}
		if (element.getAcquaintedWith().size()>0) {this.acquaintedWith.addAll(element.getAcquaintedWith());return;}
		if (element.getHasExpertise().size()>0) {this.hasExpertise.addAll(element.getHasExpertise());return;}
		if (element.getProvideMapping().size()>0) {this.provideMapping.addAll(element.getProvideMapping());return;}
		if (this.getMappingOMVLocation()==null && element.getMappingOMVLocation()!=null) {this.setMappingOMVLocation(element.getMappingOMVLocation());return;}
		if (element.getTrackOntology().size()>0) {this.trackOntology.addAll(element.getTrackOntology());return;}
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

	public void setOntologyOMVLocation(OMVPeer newOntologyLocation)
	{
		this.ontologyOMVLocation=newOntologyLocation;
	}
	
	public OMVPeer getOntologyOMVLocation()
	{
		return this.ontologyOMVLocation;
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

	public void addAcquaintedWith(OMVPeer newAcquaintedWith)
	{
		this.acquaintedWith.add(newAcquaintedWith);
	}
	
	public void removeAcquaintedWith(OMVPeer oldAcquaintedWith)
	{
		this.acquaintedWith.remove(oldAcquaintedWith);
	}
	
	public Set <OMVPeer> getAcquaintedWith()
	{
		return this.acquaintedWith;
	}
	
	public void addHasExpertise(OMVOntologyDomain newHasExpertise)
	{
		this.hasExpertise.add(newHasExpertise);
	}
	
	public void removeHasExpertise(OMVPeer oldHasExpertise)
	{
		this.hasExpertise.remove(oldHasExpertise);
	}
	
	public Set <OMVOntologyDomain> getHasExpertise()
	{
		return this.hasExpertise;
	}
	
	public void addProvideMapping(OMVMapping newProvideMapping)
	{
		this.provideMapping.add(newProvideMapping);
	}
	
	public void removeProvideMapping(OMVPeer oldProvideMapping)
	{
		this.provideMapping.remove(oldProvideMapping);
	}
	
	public Set <OMVMapping> getProvideMapping()
	{
		return this.provideMapping;
	}
	
	public void setMappingOMVLocation(OMVPeer newMappingLocation)
	{
		this.mappingOMVLocation=newMappingLocation;
	}
	
	public OMVPeer getMappingOMVLocation()
	{
		return this.mappingOMVLocation;
	}
	
	public void addTrackOntology(OMVOntology newTrackOntology)
	{
		this.trackOntology.add(newTrackOntology);
	}
	
	public void removeTrackOntology(OMVOntology oldTrackOntology)
	{
		this.trackOntology.remove(oldTrackOntology);
	}
	
	public Set <OMVOntology> getTrackOntology()
	{
		return this.trackOntology;
	}
}
