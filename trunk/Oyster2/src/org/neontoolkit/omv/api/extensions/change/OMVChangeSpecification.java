package org.neontoolkit.omv.api.extensions.change;

import java.util.HashSet;
import java.util.Set;

import org.neontoolkit.omv.api.core.OMVOntology;

/**
 * The class OMVChangeSpecification provides the object 
 * representation of the change specification class of 
 * the change OMV ontology extension.
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class OMVChangeSpecification {
	private String URI;
	private String initialTimestamp;
	private String lastTimestamp;
	private OMVOntology changeFromVersion;
	private OMVOntology changeToVersion;
	private Set <String> hasChange = new HashSet <String>(); //Automatically generated
	
	public OMVChangeSpecification()
    {
    }
	
	
	public void append(OMVChangeSpecification element)
    {
		if (this.getURI()==null && element.getURI()!=null) {this.setURI(element.getURI());return;}
		if (this.getInitialTimestamp()==null && element.getInitialTimestamp()!=null) {this.setInitialTimestamp(element.getInitialTimestamp());return;}
		if (this.getLastTimestamp()==null && element.getLastTimestamp()!=null) {this.setLastTimestamp(element.getLastTimestamp());return;}
		if (this.getChangeFromVersion()==null && element.getChangeFromVersion()!=null) {this.setChangeFromVersion(element.getChangeFromVersion());return;}
		if (this.getChangeToVersion()==null && element.getChangeToVersion()!=null) {this.setChangeToVersion(element.getChangeToVersion());return;}
		if (element.getHasChange().size()>0) {this.getHasChange().addAll(element.getHasChange());return;}
    }
	
	public String getURI()
	{
		return this.URI;
	}
	public void setURI(String newURI)
	{
		this.URI=newURI;
	}
	
	public void setInitialTimestamp(String newInitialTimestamp)
	{
		this.initialTimestamp=newInitialTimestamp;
	}
	
	public String getInitialTimestamp()
	{
		return this.initialTimestamp;
	}
	
	public void setLastTimestamp(String newLastTimestamp)
	{
		this.lastTimestamp=newLastTimestamp;
	}
	
	public String getLastTimestamp()
	{
		return this.lastTimestamp;
	}

	public OMVOntology getChangeFromVersion()
	{
		return this.changeFromVersion;
	}
	
	public void setChangeFromVersion(OMVOntology newChangeFromVersion)
	{
		this.changeFromVersion=newChangeFromVersion;
	}
	
	
	public OMVOntology getChangeToVersion()
	{
		return this.changeToVersion;
	}
	
	public void setChangeToVersion(OMVOntology newChangeToVersion)
	{
		this.changeToVersion=newChangeToVersion;
	}
	
		
	public Set <String> getHasChange()
	{
		return this.hasChange;
	}
	
	public void addHasChange(String newHasChange)
	{
		this.hasChange.add(newHasChange);
	}
	
	public void removeHasChange(String oldHasChange)
	{
		this.hasChange.remove(oldHasChange);
	}

}
