package api.omv.extensions.mapping;

import java.util.HashSet;
import java.util.Set;
import api.omv.core.*;

/**
 * The class OMVMapping provides the object 
 * representation of the Mapping  
 * OMV ontology extension.
 * @author Raul Palma
 * @version 0.1, May 2007
 */
public class OMVMapping {
	
	private String URI;
	private String creationDate;
	private Set <OMVParty> hasCreator = new HashSet <OMVParty>();
	private Set <OMVMappingProperty> hasProperty = new HashSet <OMVMappingProperty>();
	private OMVMappingMethod usedMethod;
	private OMVOntology hasSourceOntology;
	private OMVOntology hasTargetOntology;
	private String level;
	private Number processingTime;
	private String purpose;
	private String type;
	
	
	public OMVMapping()
	    {
	    }
	
	public void append(OMVMapping element)
    {
		if (this.getURI()==null && element.getURI()!=null) {this.setURI(element.getURI());return;}
		if (this.getCreationDate()==null && element.getCreationDate()!=null) {this.setCreationDate(element.getCreationDate());return;}
		if (element.getHasCreator().size()>0) {this.hasCreator.addAll(element.getHasCreator());return;}
		if (element.getHasProperty().size()>0) {this.hasProperty.addAll(element.getHasProperty());return;}
		if (this.getUsedMethod()==null && element.getUsedMethod()!=null) {this.setUsedMethod(element.getUsedMethod());return;}
		if (this.getHasSourceOntology()==null && element.getHasSourceOntology()!=null) {this.setHasSourceOntology(element.getHasSourceOntology());return;}
		if (this.getHasTargetOntology()==null && element.getHasTargetOntology()!=null) {this.setHasTargetOntology(element.getHasTargetOntology());return;}
		if (this.getLevel()==null && element.getLevel()!=null) {this.setLevel(element.getLevel());return;}
		if (this.getProcessingTime()==null && element.getProcessingTime()!=null) {this.setProcessingTime(element.getProcessingTime());return;}
		if (this.getPurpose()==null && element.getPurpose()!=null) {this.setPurpose(element.getPurpose());return;}
		if (this.getType()==null && element.getType()!=null) {this.setType(element.getType());return;}
    }
	
	public void setURI(String newURI)
	{
		this.URI=newURI;
	}
	
	public String getURI()
	{
		return this.URI;
	}
	
	
	
	public void setCreationDate(String newCreationDate)
	{
		this.creationDate=newCreationDate;
	}
	
	public String getCreationDate()
	{
		return this.creationDate;
	}
		 	
	public void addHasCreator(OMVParty newHasCreator)
	{
		this.hasCreator.add(newHasCreator);
	}
	
	public void removeHasCreator(OMVParty oldHasCreator)
	{
		this.hasCreator.remove(oldHasCreator);
	}
	
	public Set <OMVParty> getHasCreator()
	{
		return this.hasCreator;
	}
	
	public void addHasProperty(OMVMappingProperty newProperty)
	{
		this.hasProperty.add(newProperty);
	}
	
	public void removeHasProperty(OMVMappingProperty oldProperty)
	{
		this.hasProperty.remove(oldProperty);
	}
	
	public Set <OMVMappingProperty> getHasProperty()
	{
		return this.hasProperty;
	}

	public void setUsedMethod(OMVMappingMethod newMappingMethod)
	{
		this.usedMethod=newMappingMethod;
	}
	
	public OMVMappingMethod getUsedMethod()
	{
		return this.usedMethod;
	}
	
	public void setHasSourceOntology(OMVOntology newSourceOntology)
	{
		this.hasSourceOntology=newSourceOntology;
	}
	
	public OMVOntology getHasSourceOntology()
	{
		return this.hasSourceOntology;
	}
	
	public void setHasTargetOntology(OMVOntology newTargetOntology)
	{
		this.hasTargetOntology=newTargetOntology;
	}
	
	public OMVOntology getHasTargetOntology()
	{
		return this.hasTargetOntology;
	}

	public void setLevel(String newLevel)
	{
		this.level=newLevel;
	}
	
	public String getLevel()
	{
		return this.level;
	}
	
	public void setProcessingTime(Number newProcessingTime)
	{
		this.processingTime=newProcessingTime;
	}
	
	public Number getProcessingTime()
	{
		return this.processingTime;
	}
	
	public void setPurpose(String newPurpose)
	{
		this.purpose=newPurpose;
	}
	
	public String getPurpose()
	{
		return this.purpose;
	}
	
	public void setType(String newType)
	{
		this.type=newType;
	}
	
	public String getType()
	{
		return this.type;
	}
}
