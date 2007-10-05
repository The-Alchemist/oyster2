package org.neon_toolkit.omv.api.extensions.mapping;

import java.util.HashSet;
import java.util.Set;
import org.neon_toolkit.omv.api.core.OMVParty;

//import api.omv.core.*;

/**
 * The class OMVMappingMethod provides the object 
 * representation of the MappingMethod class of the Mapping 
 * OMV ontology extension.
 * @author Raul Palma
 * @version 0.1, May 2007
 */
public class OMVMappingMethod {
	private String ID;
	private Set <OMVParty> hasCreator = new HashSet <OMVParty>();
	private Set <OMVMappingParameter> hasParameter = new HashSet <OMVMappingParameter>();
	
	public OMVMappingMethod()
    {
    }
		
	public void append(OMVMappingMethod element)
    {
		if (this.getID()==null && element.getID()!=null) {this.setID(element.getID());return;}
		if (element.getHasCreator().size()>0) {this.hasCreator.addAll(element.getHasCreator());return;}
		if (element.getHasParameter().size()>0) {this.hasParameter.addAll(element.getHasParameter());return;}
	}

	public void setID(String newID)
	{
		this.ID=newID;
	}
	
	public String getID()
	{
		return this.ID;
	}
	
	public Set <OMVParty> getHasCreator()
	{
		return this.hasCreator;
	}
	
	public void addHasCreator(OMVParty newHasCreator)
	{
		this.hasCreator.add(newHasCreator);
	}
	
	public void removeHasCreator(OMVParty oldHasCreator)
	{
		this.hasCreator.remove(oldHasCreator);
	}
	
	public Set <OMVMappingParameter> getHasParameter()
	{
		return this.hasParameter;
	}
	
	public void addHasParameter(OMVMappingParameter newHasParameter)
	{
		this.hasParameter.add(newHasParameter);
	}
	
	public void removeHasParameter(OMVParty oldHasParameter)
	{
		this.hasParameter.remove(oldHasParameter);
	}
	
	public static class OMVMappingBasicMethod extends OMVMappingMethod{
		public OMVMappingBasicMethod()
	    {
	    }
		
		public static class OMVMappingAlgorithm extends OMVMappingBasicMethod{
			private String source;
			private String version;
			private String naturalLanguage;
			
			public OMVMappingAlgorithm()
		    {
		    }
			
			public void append(OMVMappingAlgorithm element){
				if (this.getSource()==null && element.getSource()!=null) {this.setSource(element.getSource());return;}
				if (this.getVersion()==null && element.getVersion()!=null) {this.setVersion(element.getVersion());return;}
				if (this.getNaturalLanguage()==null && element.getNaturalLanguage()!=null) {this.setNaturalLanguage(element.getNaturalLanguage());return;}
			}
			
			public void setSource(String newSource)
			{
				this.source=newSource;
			}
			
			public String getSource()
			{
				return this.source;
			}
			
			public void setVersion(String newVersion)
			{
				this.version=newVersion;
			}
			
			public String getVersion()
			{
				return this.version;
			}
			
			public void setNaturalLanguage(String newNaturalLanguage)
			{
				this.naturalLanguage=newNaturalLanguage;
			}
			
			public String getNaturalLanguage()
			{
				return this.naturalLanguage;
			}
			
		}
		public static class OMVMappingManualMethod extends OMVMappingBasicMethod {
			public OMVMappingManualMethod()
		    {
		    }
		}
	}
	
	public static class OMVMappingCompoundMethod extends OMVMappingMethod{
		public OMVMappingCompoundMethod()
	    {
	    }
		public static class OMVMappingFilter extends OMVMappingCompoundMethod{
			private Number value;
			private String variety;
			private OMVMappingMethod filtersMethod;
			
			public OMVMappingFilter()
		    {
		    }
			
			public void append(OMVMappingFilter element){
				if (this.getValue()==null && element.getValue()!=null) {this.setValue(element.getValue());return;}
				if (this.getVariety()==null && element.getVariety()!=null) {this.setVariety(element.getVariety());return;}
				if (this.getFiltersMethod()==null && element.getFiltersMethod()!=null) {this.setFiltersMethod(element.getFiltersMethod());return;}
			}
			
			public void setValue(Number newValue)
			{
				this.value=newValue;
			}
			
			public Number getValue()
			{
				return this.value;
			}
			
			public void setVariety(String newVariety)
			{
				this.variety=newVariety;
			}
			
			public String getVariety()
			{
				return this.variety;
			}
			
			public void setFiltersMethod(OMVMappingMethod newFiltersMethod)
			{
				this.filtersMethod=newFiltersMethod;
			}
			
			public OMVMappingMethod getFiltersMethod()
			{
				return this.filtersMethod;
			}
			
			
			
		}
		public static class OMVMappingParallel extends OMVMappingCompoundMethod{
			private Set <OMVMappingMethod> aggregatesMethod = new HashSet <OMVMappingMethod>();
			private Set <OMVMappingMethod> composesMethod = new HashSet <OMVMappingMethod>();
			
			public OMVMappingParallel()
		    {
		    }
			
			public void append(OMVMappingParallel element)
		    {
				if (element.getAggregatesMethod().size()>0) {this.aggregatesMethod.addAll(element.getAggregatesMethod());return;}
				if (element.getComposesMethod().size()>0) {this.composesMethod.addAll(element.getComposesMethod());return;}
			}
			
			public Set <OMVMappingMethod> getAggregatesMethod()
			{
				return this.aggregatesMethod;
			}
			
			public void addAggregatesMethod(OMVMappingMethod newAggregatesMethod)
			{
				this.aggregatesMethod.add(newAggregatesMethod);
			}
			
			public void removeAggregatesMethod(OMVMappingMethod oldAggregatesMethod)
			{
				this.aggregatesMethod.remove(oldAggregatesMethod);
			}
			
			public Set <OMVMappingMethod> getComposesMethod()
			{
				return this.composesMethod;
			}
			
			public void addComposesMethod(OMVMappingMethod newComposesMethod)
			{
				this.composesMethod.add(newComposesMethod);
			}
			
			public void removeComposesMethod(OMVMappingMethod oldComposesMethod)
			{
				this.composesMethod.remove(oldComposesMethod);
			}

		}
		public static class OMVMappingSequence extends OMVMappingCompoundMethod{
			private Set <OMVMappingMethod> composesMethod = new HashSet <OMVMappingMethod>();
			
			public OMVMappingSequence()
		    {
		    }
			
			public void append(OMVMappingSequence element)
		    {
				if (element.getComposesMethod().size()>0) {this.composesMethod.addAll(element.getComposesMethod());return;}
			}
			
			public Set <OMVMappingMethod> getComposesMethod()
			{
				return this.composesMethod;
			}
			
			public void addComposesMethod(OMVMappingMethod newComposesMethod)
			{
				this.composesMethod.add(newComposesMethod);
			}
			
			public void removeComposesMethod(OMVMappingMethod oldComposesMethod)
			{
				this.composesMethod.remove(oldComposesMethod);
			}
		}
	}
	
}