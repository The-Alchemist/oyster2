package org.neontoolkit.owlodm.api;

import java.util.HashSet;
import java.util.Set;

/**
 * The class Description provides the object 
 * representation of the Description OWLODM Object  
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class Description extends OWLEntity{
	
	public Description()
    {

    }		
	
	public static class DataAllValuesFrom extends Description{
		Set<DataProperty> dataProperties = new HashSet<DataProperty>();
		Datatype dataRange;
		
		public DataAllValuesFrom(){
	    }
		public void append(DataAllValuesFrom element){				
			if (element.getDataProperties().size()>0) {this.getDataProperties().addAll(element.getDataProperties());return;}
			if (this.getDataRange()==null && element.getDataRange()!=null) {this.setDataRange(element.getDataRange());return;}
	    }
		public Set<DataProperty> getDataProperties()
		{
			return this.dataProperties;
		}
		
		public void addDataProperties(DataProperty newDataProperties)
		{
			this.dataProperties.add(newDataProperties);
		}
		
		public void removeDataProperties(DataProperty oldDataRange)
		{
			this.dataProperties.remove(oldDataRange);
		}
		
		public Datatype getDataRange()
		{
			return this.dataRange;
		}
		
		public void setDataRange(Datatype newDataRange){
			this.dataRange=newDataRange;
		}	
	}
	public static class DataExactCardinality extends Description{
		DataProperty dataProperty;
		Datatype dataRange;
		Integer cardinality;
		
		public DataExactCardinality(){
	    }
		public void append(DataExactCardinality element){				
			if (this.getDataProperty()==null && element.getDataProperty()!=null) {this.setDataProperty(element.getDataProperty());return;}
			if (this.getDataRange()==null && element.getDataRange()!=null) {this.setDataRange(element.getDataRange());return;}
			if (this.getCardinality()==null && element.getCardinality()!=null) {this.setCardinality(element.getCardinality());return;}
	    }
		
		public DataProperty getDataProperty()
		{
			return this.dataProperty;
		}
		
		public void setDataProperty(DataProperty newDataProperty)
		{
			this.dataProperty=newDataProperty;
		}
		
		public Datatype getDataRange()
		{
			return this.dataRange;
		}
		
		public void setDataRange(Datatype newDataRange){
			this.dataRange=newDataRange;
		}
		
		public void setCardinality(Integer newCardinality)
		{
			this.cardinality=newCardinality;
		}
		
		public Integer getCardinality()
		{
			return this.cardinality;
		}
	}
	public static class DataHasValue extends Description{
		DataProperty dataProperty;
		String constant;
		
		
		public DataHasValue(){
	    }
		public void append(DataHasValue element){				
			if (this.getDataProperty()==null && element.getDataProperty()!=null) {this.setDataProperty(element.getDataProperty());return;}
			if (this.getConstant()==null && element.getConstant()!=null) {this.setConstant(element.getConstant());return;}
	    }
		
		public DataProperty getDataProperty()
		{
			return this.dataProperty;
		}
		
		public void setDataProperty(DataProperty newDataProperty)
		{
			this.dataProperty=newDataProperty;
		}
		
		public String getConstant()
		{
			return this.constant;
		}
		
		public void setConstant(String newConstant){
			this.constant=newConstant;
		}
	}
	public static class DataMaxCardinality extends Description{
		DataProperty dataProperty;
		Datatype dataRange;
		Integer cardinality;
		
		public DataMaxCardinality(){
	    }
		public void append(DataMaxCardinality element){				
			if (this.getDataProperty()==null && element.getDataProperty()!=null) {this.setDataProperty(element.getDataProperty());return;}
			if (this.getDataRange()==null && element.getDataRange()!=null) {this.setDataRange(element.getDataRange());return;}
			if (this.getCardinality()==null && element.getCardinality()!=null) {this.setCardinality(element.getCardinality());return;}
	    }
		
		public DataProperty getDataProperty()
		{
			return this.dataProperty;
		}
		
		public void setDataProperty(DataProperty newDataProperty)
		{
			this.dataProperty=newDataProperty;
		}
		
		public Datatype getDataRange()
		{
			return this.dataRange;
		}
		
		public void setDataRange(Datatype newDataRange){
			this.dataRange=newDataRange;
		}
		
		public void setCardinality(Integer newCardinality)
		{
			this.cardinality=newCardinality;
		}
		
		public Integer getCardinality()
		{
			return this.cardinality;
		}
	}
	public static class DataMinCardinality extends Description{
		DataProperty dataProperty;
		Datatype dataRange;
		Integer cardinality;
		
		public DataMinCardinality(){
	    }
		public void append(DataMinCardinality element){				
			if (this.getDataProperty()==null && element.getDataProperty()!=null) {this.setDataProperty(element.getDataProperty());return;}
			if (this.getDataRange()==null && element.getDataRange()!=null) {this.setDataRange(element.getDataRange());return;}
			if (this.getCardinality()==null && element.getCardinality()!=null) {this.setCardinality(element.getCardinality());return;}
	    }
		
		public DataProperty getDataProperty()
		{
			return this.dataProperty;
		}
		
		public void setDataProperty(DataProperty newDataProperty)
		{
			this.dataProperty=newDataProperty;
		}
		
		public Datatype getDataRange()
		{
			return this.dataRange;
		}
		
		public void setDataRange(Datatype newDataRange){
			this.dataRange=newDataRange;
		}
		
		public void setCardinality(Integer newCardinality)
		{
			this.cardinality=newCardinality;
		}
		
		public Integer getCardinality()
		{
			return this.cardinality;
		}
	}
	public static class DataSomeValuesFrom extends Description{
		Set<DataProperty> dataProperties = new HashSet<DataProperty>();
		Datatype dataRange;
		
		public DataSomeValuesFrom(){
	    }
		public void append(DataSomeValuesFrom element){				
			if (element.getDataProperties().size()>0) {this.getDataProperties().addAll(element.getDataProperties());return;}
			if (this.getDataRange()==null && element.getDataRange()!=null) {this.setDataRange(element.getDataRange());return;}
	    }
		public Set<DataProperty> getDataProperties()
		{
			return this.dataProperties;
		}
		
		public void addDataProperties(DataProperty newDataProperties)
		{
			this.dataProperties.add(newDataProperties);
		}
		
		public void removeDataProperties(DataProperty oldDataRange)
		{
			this.dataProperties.remove(oldDataRange);
		}
		
		public Datatype getDataRange()
		{
			return this.dataRange;
		}
		
		public void setDataRange(Datatype newDataRange){
			this.dataRange=newDataRange;
		}	
	}
	
	public static class ObjectAllValuesFrom extends Description{
		Description OWLClass;
		ObjectProperty objectProperty;
		
		public ObjectAllValuesFrom(){
	    }
		public void append(ObjectAllValuesFrom element){				
			if (this.getOWLClass()==null && element.getOWLClass()!=null) {this.setOWLClass(element.getOWLClass());return;}
			if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
	    }
		
		public Description getOWLClass()
		{
			return this.OWLClass;
		}
		
		public void setOWLClass(Description newOWLClass){
			this.OWLClass=newOWLClass;
		}
		
		public ObjectProperty getObjectProperty()
		{
			return this.objectProperty;
		}
		
		public void setObjectProperty(ObjectProperty newObjectProperty){
			this.objectProperty=newObjectProperty;
		}
	}
	public static class ObjectComplementOf extends Description{
		Description OWLClass;
		
		
		public ObjectComplementOf(){
	    }
		public void append(ObjectComplementOf element){				
			if (this.getOWLClass()==null && element.getOWLClass()!=null) {this.setOWLClass(element.getOWLClass());return;}
	    }
		
		public Description getOWLClass()
		{
			return this.OWLClass;
		}
		
		public void setOWLClass(Description newOWLClass){
			this.OWLClass=newOWLClass;
		}
			
	}
	public static class ObjectExactCardinality extends Description{
		ObjectProperty objectProperty;
		Description OWLClass;
		Integer cardinality;
		
		public ObjectExactCardinality(){
	    }
		public void append(ObjectExactCardinality element){				
			if (this.getOWLClass()==null && element.getOWLClass()!=null) {this.setOWLClass(element.getOWLClass());return;}
			if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
			if (this.getCardinality()==null && element.getCardinality()!=null) {this.setCardinality(element.getCardinality());return;}
	    }
		
		public ObjectProperty getObjectProperty()
		{
			return this.objectProperty;
		}
		
		public void setObjectProperty(ObjectProperty newObjectProperty){
			this.objectProperty=newObjectProperty;
		}
		
		public Description getOWLClass()
		{
			return this.OWLClass;
		}
		
		public void setOWLClass(Description newOWLClass){
			this.OWLClass=newOWLClass;
		}
		
		public void setCardinality(Integer newCardinality)
		{
			this.cardinality=newCardinality;
		}
		
		public Integer getCardinality()
		{
			return this.cardinality;
		}
	}
	public static class ObjectExistsSelf extends Description{
		ObjectProperty objectProperty;
		
		
		public ObjectExistsSelf(){
	    }
		public void append(ObjectExistsSelf element){				
			if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
	    }
		
		public ObjectProperty getObjectProperty()
		{
			return this.objectProperty;
		}
		
		public void setObjectProperty(ObjectProperty newObjectProperty){
			this.objectProperty=newObjectProperty;
		}
			
	}
	public static class ObjectHasValue extends Description{
		ObjectProperty objectProperty;
		Individual value;
		
		public ObjectHasValue(){
	    }
		public void append(ObjectHasValue element){				
			if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
			if (this.getValue()==null && element.getValue()!=null) {this.setValue(element.getValue());return;}
	    }
		
		
		public ObjectProperty getObjectProperty()
		{
			return this.objectProperty;
		}
		
		public void setObjectProperty(ObjectProperty newObjectProperty){
			this.objectProperty=newObjectProperty;
		}
		
		public Individual getValue()
		{
			return this.value;
		}
		
		public void setValue(Individual newValue){
			this.value=newValue;
		}
	}
	public static class ObjectIntersectionOf extends Description{
		Set<Description> OWLClasses = new HashSet<Description>();
		
		
		public ObjectIntersectionOf(){
	    }
		public void append(ObjectIntersectionOf element){				
			if (element.getOWLClasses().size()>0) {this.getOWLClasses().addAll(element.getOWLClasses());return;}
	    }
		public Set<Description> getOWLClasses()
		{
			return this.OWLClasses;
		}
		
		public void addOWLClasses(Description newOWLClasses)
		{
			this.OWLClasses.add(newOWLClasses);
		}
		
		public void removeOWLClasses(Description oldOWLClasses)
		{
			this.OWLClasses.remove(oldOWLClasses);
		}
			
	}
	public static class ObjectMaxCardinality extends Description{
		ObjectProperty objectProperty;
		Description OWLClass;
		Integer cardinality;
		
		public ObjectMaxCardinality(){
	    }
		public void append(ObjectMaxCardinality element){				
			if (this.getOWLClass()==null && element.getOWLClass()!=null) {this.setOWLClass(element.getOWLClass());return;}
			if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
			if (this.getCardinality()==null && element.getCardinality()!=null) {this.setCardinality(element.getCardinality());return;}
	    }
		
		public ObjectProperty getObjectProperty()
		{
			return this.objectProperty;
		}
		
		public void setObjectProperty(ObjectProperty newObjectProperty){
			this.objectProperty=newObjectProperty;
		}
		
		public Description getOWLClass()
		{
			return this.OWLClass;
		}
		
		public void setOWLClass(Description newOWLClass){
			this.OWLClass=newOWLClass;
		}
		
		public void setCardinality(Integer newCardinality)
		{
			this.cardinality=newCardinality;
		}
		
		public Integer getCardinality()
		{
			return this.cardinality;
		}
	}
	public static class ObjectMinCardinality extends Description{
		ObjectProperty objectProperty;
		Description OWLClass;
		Integer cardinality;
		
		public ObjectMinCardinality(){
	    }
		public void append(ObjectMinCardinality element){				
			if (this.getOWLClass()==null && element.getOWLClass()!=null) {this.setOWLClass(element.getOWLClass());return;}
			if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
			if (this.getCardinality()==null && element.getCardinality()!=null) {this.setCardinality(element.getCardinality());return;}
	    }
		
		public ObjectProperty getObjectProperty()
		{
			return this.objectProperty;
		}
		
		public void setObjectProperty(ObjectProperty newObjectProperty){
			this.objectProperty=newObjectProperty;
		}
		
		public Description getOWLClass()
		{
			return this.OWLClass;
		}
		
		public void setOWLClass(Description newOWLClass){
			this.OWLClass=newOWLClass;
		}
		
		public void setCardinality(Integer newCardinality)
		{
			this.cardinality=newCardinality;
		}
		
		public Integer getCardinality()
		{
			return this.cardinality;
		}
	}
	public static class ObjectOneOf extends Description{
		Set<Individual> individuals = new HashSet<Individual>();
		
		
		public ObjectOneOf(){
	    }
		public void append(ObjectOneOf element){				
			if (element.getIndividuals().size()>0) {this.getIndividuals().addAll(element.getIndividuals());return;}
	    }
		public Set<Individual> getIndividuals()
		{
			return this.individuals;
		}
		
		public void addIndividuals(Individual newIndividuals)
		{
			this.individuals.add(newIndividuals);
		}
		
		public void removeIndividuals(Individual oldIndividuals)
		{
			this.individuals.remove(oldIndividuals);
		}
	}
	public static class ObjectSomeValuesFrom extends Description{
		Description OWLClass;
		ObjectProperty objectProperty;
		
		public ObjectSomeValuesFrom(){
	    }
		public void append(ObjectSomeValuesFrom element){				
			if (this.getOWLClass()==null && element.getOWLClass()!=null) {this.setOWLClass(element.getOWLClass());return;}
			if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
	    }
		
		public Description getOWLClass()
		{
			return this.OWLClass;
		}
		
		public void setOWLClass(Description newOWLClass){
			this.OWLClass=newOWLClass;
		}
		
		public ObjectProperty getObjectProperty()
		{
			return this.objectProperty;
		}
		
		public void setObjectProperty(ObjectProperty newObjectProperty){
			this.objectProperty=newObjectProperty;
		}
	}
	public static class ObjectUnionOf extends Description{
		Set<Description> OWLClasses = new HashSet<Description>();
		
		
		public ObjectUnionOf(){
	    }
		public void append(ObjectUnionOf element){				
			if (element.getOWLClasses().size()>0) {this.getOWLClasses().addAll(element.getOWLClasses());return;}
	    }
		public Set<Description> getOWLClasses()
		{
			return this.OWLClasses;
		}
		
		public void addOWLClasses(Description newOWLClasses)
		{
			this.OWLClasses.add(newOWLClasses);
		}
		
		public void removeOWLClasses(Description oldOWLClasses)
		{
			this.OWLClasses.remove(oldOWLClasses);
		}
			
	}
	public static class OWLClass extends Description{
		public OWLClass(String uri){
			this.URI=uri;
	    }
	}
}

