package org.neon_toolkit.owlodm.api;

import java.util.HashSet;
import java.util.Set;

import org.neon_toolkit.owlodm.api.OWLEntity.DataProperty;
import org.neon_toolkit.owlodm.api.OWLEntity.Datatype;
import org.neon_toolkit.owlodm.api.OWLEntity.Individual;
import org.neon_toolkit.owlodm.api.OWLEntity.ObjectProperty;

/**
 * The class Axiom provides the object 
 * representation of the Axiom OWLODM Object  
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class Axiom {
	String URI;
	Set<String> axiomAnnotation = new HashSet<String>();
	public Axiom()
    {

    }	
	
	public void append(Axiom element)
    {
		if (this.getURI()==null && element.getURI()!=null) {this.setURI(element.getURI());return;}
    }
	
	public void setURI(String newURI){
		this.URI=newURI;
	}
	public String getURI()
	{
		return this.URI;
	}
	public static class ClassAxiom extends Axiom{
		public ClassAxiom(){
	    }
		public static class DisjointClasses extends ClassAxiom{
			Set<Description> disjointClasses = new HashSet<Description>();
			
			public DisjointClasses(){
		    }
			
			public void append(DisjointClasses element){				
				if (element.getDisjointClasses().size()>0) {this.getDisjointClasses().addAll(element.getDisjointClasses());return;}
		    }
			
			public Set<Description> getDisjointClasses()
			{
				return this.disjointClasses;
			}
			
			public void addDisjointClasses(Description newDisjointClasses)
			{
				this.disjointClasses.add(newDisjointClasses);
			}
			
			public void removeDisjointClasses(Description oldDisjointClasses)
			{
				this.disjointClasses.remove(oldDisjointClasses);
			}

		}
		public static class DisjointUnion extends ClassAxiom{
			Description unionClass;
			Set<Description> disjointClasses = new HashSet<Description>();
			
			public DisjointUnion(){
		    }
			
			public void append(DisjointUnion element){
				if (this.getUnionClass()==null && element.getUnionClass()!=null) {this.setUnionClass(element.getUnionClass());return;}
				if (element.getDisjointClasses().size()>0) {this.getDisjointClasses().addAll(element.getDisjointClasses());return;}
		    }
			
			public void setUnionClass(Description newUnionClass)
			{
				this.unionClass=newUnionClass;
			}
			
			public Description getUnionClass()
			{
				return this.unionClass;
			}
			
			public Set<Description> getDisjointClasses()
			{
				return this.disjointClasses;
			}
			
			public void addDisjointClasses(Description newDisjointClasses)
			{
				this.disjointClasses.add(newDisjointClasses);
			}
			
			public void removeDisjointClasses(Description oldDisjointClasses)
			{
				this.disjointClasses.remove(oldDisjointClasses);
			}

		}
		public static class EquivalentClasses extends ClassAxiom{
			Set<Description> equivalentClasses = new HashSet<Description>();
			
			public EquivalentClasses(){
		    }
			
			public void append(EquivalentClasses element){				
				if (element.getEquivalentClasses().size()>0) {this.getEquivalentClasses().addAll(element.getEquivalentClasses());return;}
		    }
			
			public Set<Description> getEquivalentClasses()
			{
				return this.equivalentClasses;
			}
			
			public void addEquivalentClasses(Description newEquivalentClasses)
			{
				this.equivalentClasses.add(newEquivalentClasses);
			}
			
			public void removeEquivalentClasses(Description oldEquivalentClasses)
			{
				this.equivalentClasses.remove(oldEquivalentClasses);
			}

		}
		public static class SubClassOf extends ClassAxiom{
			Description subClass;
			Description superClass;
			
			public SubClassOf(){
		    }
			
			public void append(SubClassOf element){				
				if (this.getSubClass()==null && element.getSubClass()!=null) {this.setSubClass(element.getSubClass());return;}
				if (this.getSuperClass()==null && element.getSuperClass()!=null) {this.setSuperClass(element.getSuperClass());return;}
		    }
			
			public void setSubClass(Description newSubClass)
			{
				this.subClass=newSubClass;
			}
			
			public Description getSubClass()
			{
				return this.subClass;
			}
			
			public void setSuperClass(Description newSuperClass)
			{
				this.superClass=newSuperClass;
			}
			
			public Description getSuperClass()
			{
				return this.superClass;
			}
		}

	}
	
	public static class DataPropertyAxiom extends Axiom{
		public DataPropertyAxiom(){
	    }
		public static class DataPropertyDomain extends DataPropertyAxiom{
			Description domain;
			DataProperty dataProperty;
			
			public DataPropertyDomain(){
		    }
			
			public void append(DataPropertyDomain element){				
				if (this.getDomain()==null && element.getDomain()!=null) {this.setDomain(element.getDomain());return;}
				if (this.getDataProperty()==null && element.getDataProperty()!=null) {this.setDataProperty(element.getDataProperty());return;}
		    }
			
			public void setDomain(Description newDomain)
			{
				this.domain=newDomain;
			}
			
			public Description getDomain()
			{
				return this.domain;
			}
			
			public void setDataProperty(DataProperty newDataProperty)
			{
				this.dataProperty=newDataProperty;
			}
			
			public DataProperty getDataProperty()
			{
				return this.dataProperty;
			}

		}
		public static class DataPropertyRange extends DataPropertyAxiom{
			Datatype range;
			DataProperty dataProperty;
			
			public DataPropertyRange(){
		    }
			
			public void append(DataPropertyRange element){				
				if (this.getRange()==null && element.getRange()!=null) {this.setRange(element.getRange());return;}
				if (this.getDataProperty()==null && element.getDataProperty()!=null) {this.setDataProperty(element.getDataProperty());return;}
		    }
			
			public void setRange(Datatype newRange)
			{
				this.range=newRange;
			}
			
			public Datatype getRange()
			{
				return this.range;
			}
			
			public void setDataProperty(DataProperty newDataProperty)
			{
				this.dataProperty=newDataProperty;
			}
			
			public DataProperty getDataProperty()
			{
				return this.dataProperty;
			}

		}
		public static class DisjointDataProperties extends DataPropertyAxiom{
			Set<DataProperty> dataProperties = new HashSet<DataProperty>();
			
			
			public DisjointDataProperties(){
		    }
			
			public void append(DisjointDataProperties element){				
				if (element.getDataProperties().size()>0) {this.getDataProperties().addAll(element.getDataProperties());return;}
		    }
			
			public Set<DataProperty> getDataProperties()
			{
				return this.dataProperties;
			}
			
			public void addDataProperties(DataProperty newDataProperties)
			{
				this.dataProperties.add(newDataProperties);
			}
			
			public void removeDataProperties(DataProperty oldDataProperties)
			{
				this.dataProperties.remove(oldDataProperties);
			}

		}
		public static class EquivalentDataProperties extends DataPropertyAxiom{
			Set<DataProperty> dataProperties = new HashSet<DataProperty>();
			
			
			public EquivalentDataProperties(){
		    }
			
			public void append(EquivalentDataProperties element){				
				if (element.getDataProperties().size()>0) {this.getDataProperties().addAll(element.getDataProperties());return;}
		    }
			
			public Set<DataProperty> getDataProperties()
			{
				return this.dataProperties;
			}
			
			public void addDataProperties(DataProperty newDataProperties)
			{
				this.dataProperties.add(newDataProperties);
			}
			
			public void removeDataProperties(DataProperty oldDataProperties)
			{
				this.dataProperties.remove(oldDataProperties);
			}

		}
		public static class FunctionalDataProperty extends DataPropertyAxiom{
			DataProperty dataProperty;
			
			
			public FunctionalDataProperty(){
		    }
			
			public void append(FunctionalDataProperty element){				
				if (this.getDataProperty()==null && element.getDataProperty()!=null) {this.setDataProperty(element.getDataProperty());return;}
		    }
			
			public DataProperty getDataProperty()
			{
				return this.dataProperty;
			}
			
			public void setDataProperty(DataProperty newDataProperty)
			{
				this.dataProperty=newDataProperty;
			}
			
		}
		public static class SubDataPropertyOf extends DataPropertyAxiom{
			DataProperty subDataProperty;
			DataProperty superDataProperty;
			
			
			public SubDataPropertyOf(){
		    }
			
			public void append(SubDataPropertyOf element){				
				if (this.getSubDataProperty()==null && element.getSubDataProperty()!=null) {this.setSubDataProperty(element.getSubDataProperty());return;}
				if (this.getSuperDataProperty()==null && element.getSuperDataProperty()!=null) {this.setSuperDataProperty(element.getSuperDataProperty());return;}
		    }
			
			public DataProperty getSubDataProperty()
			{
				return this.subDataProperty;
			}
			
			public void setSubDataProperty(DataProperty newSubDataProperty)
			{
				this.subDataProperty=newSubDataProperty;
			}
			public DataProperty getSuperDataProperty()
			{
				return this.superDataProperty;
			}
			
			public void setSuperDataProperty(DataProperty newSuperDataProperty)
			{
				this.superDataProperty=newSuperDataProperty;
			}
		}
	}
	
	public static class Declaration extends Axiom{
		OWLEntity entity;
		public Declaration(){
	    }
		public void append(Declaration element){				
			if (this.getEntity()==null && element.getEntity()!=null) {this.setEntity(element.getEntity());return;}
	    }
		public OWLEntity getEntity()
		{
			return this.entity;
		}		
		public void setEntity(OWLEntity newEntity)
		{
			this.entity=newEntity;
		}
	}
	
	public static class EntityAnnotation extends Axiom{
		OWLEntity entity;
		Set<String> entityAnnotation = new HashSet<String>();
		
		public EntityAnnotation(){
	    }
		public void append(EntityAnnotation element){				
			if (this.getEntity()==null && element.getEntity()!=null) {this.setEntity(element.getEntity());return;}
			if (element.getEntityAnnotation().size()>0) {this.getEntityAnnotation().addAll(element.getEntityAnnotation());return;}
	    }
		public OWLEntity getEntity()
		{
			return this.entity;
		}		
		public void setEntity(OWLEntity newEntity)
		{
			this.entity=newEntity;
		}
		public Set<String> getEntityAnnotation()
		{
			return this.entityAnnotation;
		}
		public void addEntityAnnotation(String newEntityAnnotation)
		{
			this.entityAnnotation.add(newEntityAnnotation);
		}
		public void removeEntityAnnotation(String oldEntityAnnotation)
		{
			this.entityAnnotation.remove(oldEntityAnnotation);
		}
	}
	
	public static class Fact extends Axiom{
		public Fact(){
	    }
		public static class ClassAssertion extends Fact{
			Description OWLClass;
			Individual individual;
			public ClassAssertion(){
		    }
			public void append(ClassAssertion element){				
				if (this.getOWLClass()==null && element.getOWLClass()!=null) {this.setOWLClass(element.getOWLClass());return;}
				if (this.getIndividual()==null && element.getIndividual()!=null) {this.setIndividual(element.getIndividual());return;}
		    }
			public Description getOWLClass()
			{
				return this.OWLClass;
			}
			
			public void setOWLClass(Description newOWLClass)
			{
				this.OWLClass=newOWLClass;
			}
			public Individual getIndividual()
			{
				return this.individual;
			}
			
			public void setIndividual(Individual newIndividual)
			{
				this.individual=newIndividual;
			}
		}
		public static class DataPropertyAssertion extends Fact{
			DataProperty dataProperty;
			Individual sourceIndividual;
			String targetValue;
			
			public DataPropertyAssertion(){
		    }
			public void append(DataPropertyAssertion element){				
				if (this.getDataProperty()==null && element.getDataProperty()!=null) {this.setDataProperty(element.getDataProperty());return;}
				if (this.getSourceIndividual()==null && element.getSourceIndividual()!=null) {this.setSourceIndividual(element.getSourceIndividual());return;}
				if (this.getTargetValue()==null && element.getTargetValue()!=null) {this.setTargetValue(element.getTargetValue());return;}
		    }
			
			public DataProperty getDataProperty()
			{
				return this.dataProperty;
			}
			
			public void setDataProperty(DataProperty newDataProperty)
			{
				this.dataProperty=newDataProperty;
			}
			
			public Individual getSourceIndividual()
			{
				return this.sourceIndividual;
			}
			
			public void setSourceIndividual(Individual newSourceIndividual)
			{
				this.sourceIndividual=newSourceIndividual;
			}
			
			public String getTargetValue()
			{
				return this.targetValue;
			}
			
			public void setTargetValue(String newTargetValue)
			{
				this.targetValue=newTargetValue;
			}
		}
		public static class DifferentIndividuals extends Fact{
			Set<Individual> differentIndividuals = new HashSet<Individual>();
			
			
			public DifferentIndividuals(){
		    }
			
			public void append(DifferentIndividuals element){				
				if (element.getDifferentIndividuals().size()>0) {this.getDifferentIndividuals().addAll(element.getDifferentIndividuals());return;}
		    }
			
			public Set<Individual> getDifferentIndividuals()
			{
				return this.differentIndividuals;
			}
			
			public void addDifferentIndividuals(Individual newDifferentIndividuals)
			{
				this.differentIndividuals.add(newDifferentIndividuals);
			}
			
			public void removeDifferentIndividuals(Individual oldDifferentIndividuals)
			{
				this.differentIndividuals.remove(oldDifferentIndividuals);
			}

		}
		public static class NegativeDataPropertyAssertion extends Fact{
			DataProperty dataProperty;
			Individual sourceIndividual;
			String targetValue;
			
			public NegativeDataPropertyAssertion(){
		    }
			public void append(NegativeDataPropertyAssertion element){				
				if (this.getDataProperty()==null && element.getDataProperty()!=null) {this.setDataProperty(element.getDataProperty());return;}
				if (this.getSourceIndividual()==null && element.getSourceIndividual()!=null) {this.setSourceIndividual(element.getSourceIndividual());return;}
				if (this.getTargetValue()==null && element.getTargetValue()!=null) {this.setTargetValue(element.getTargetValue());return;}
		    }
			
			public DataProperty getDataProperty()
			{
				return this.dataProperty;
			}
			
			public void setDataProperty(DataProperty newDataProperty)
			{
				this.dataProperty=newDataProperty;
			}
			
			public Individual getSourceIndividual()
			{
				return this.sourceIndividual;
			}
			
			public void setSourceIndividual(Individual newSourceIndividual)
			{
				this.sourceIndividual=newSourceIndividual;
			}
			
			public String getTargetValue()
			{
				return this.targetValue;
			}
			
			public void setTargetValue(String newTargetValue)
			{
				this.targetValue=newTargetValue;
			}
		}
		public static class NegativeObjectPropertyAssertion extends Fact{
			ObjectProperty objectProperty;
			Individual sourceIndividual;
			Individual targetIndividual;
			
			public NegativeObjectPropertyAssertion(){
		    }
			public void append(NegativeObjectPropertyAssertion element){				
				if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
				if (this.getSourceIndividual()==null && element.getSourceIndividual()!=null) {this.setSourceIndividual(element.getSourceIndividual());return;}
				if (this.getTargetIndividual()==null && element.getTargetIndividual()!=null) {this.setTargetIndividual(element.getTargetIndividual());return;}
		    }
			
			public ObjectProperty getObjectProperty()
			{
				return this.objectProperty;
			}
			
			public void setObjectProperty(ObjectProperty newObjectProperty)
			{
				this.objectProperty=newObjectProperty;
			}
			
			public Individual getSourceIndividual()
			{
				return this.sourceIndividual;
			}
			
			public void setSourceIndividual(Individual newSourceIndividual)
			{
				this.sourceIndividual=newSourceIndividual;
			}
			
			public Individual getTargetIndividual()
			{
				return this.targetIndividual;
			}
			
			public void setTargetIndividual(Individual newTargetIndividual)
			{
				this.targetIndividual=newTargetIndividual;
			}
		}
		public static class ObjectPropertyAssertion extends Fact{
			ObjectProperty objectProperty;
			Individual sourceIndividual;
			Individual targetIndividual;
			
			public ObjectPropertyAssertion(){
		    }
			public void append(ObjectPropertyAssertion element){				
				if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
				if (this.getSourceIndividual()==null && element.getSourceIndividual()!=null) {this.setSourceIndividual(element.getSourceIndividual());return;}
				if (this.getTargetIndividual()==null && element.getTargetIndividual()!=null) {this.setTargetIndividual(element.getTargetIndividual());return;}
		    }
			
			public ObjectProperty getObjectProperty()
			{
				return this.objectProperty;
			}
			
			public void setObjectProperty(ObjectProperty newObjectProperty)
			{
				this.objectProperty=newObjectProperty;
			}
			
			public Individual getSourceIndividual()
			{
				return this.sourceIndividual;
			}
			
			public void setSourceIndividual(Individual newSourceIndividual)
			{
				this.sourceIndividual=newSourceIndividual;
			}
			
			public Individual getTargetIndividual()
			{
				return this.targetIndividual;
			}
			
			public void setTargetIndividual(Individual newTargetIndividual)
			{
				this.targetIndividual=newTargetIndividual;
			}
		}
		public static class SameIndividual extends Fact{
			Set<Individual> sameIndividuals = new HashSet<Individual>();
			
			
			public SameIndividual(){
		    }
			
			public void append(SameIndividual element){				
				if (element.getSameIndividuals().size()>0) {this.getSameIndividuals().addAll(element.getSameIndividuals());return;}
		    }
			
			public Set<Individual> getSameIndividuals()
			{
				return this.sameIndividuals;
			}
			
			public void addSameIndividuals(Individual newSameIndividual)
			{
				this.sameIndividuals.add(newSameIndividual);
			}
			
			public void removeSameIndividuals(Individual oldSameIndividual)
			{
				this.sameIndividuals.remove(oldSameIndividual);
			}

		}
	}
	
	public static class ObjectPropertyAxiom extends Axiom{
		public ObjectPropertyAxiom(){		
	    }
		public static class AsymmetricObjectProperty extends ObjectPropertyAxiom{
			ObjectProperty objectProperty;
			public AsymmetricObjectProperty(){		
		    }
			
			public void append(AsymmetricObjectProperty element){				
				if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
		    }
			
			public ObjectProperty getObjectProperty()
			{
				return this.objectProperty;
			}
			
			public void setObjectProperty(ObjectProperty newObjectProperty)
			{
				this.objectProperty=newObjectProperty;
			}
		}
		public static class DisjointObjectProperties extends ObjectPropertyAxiom{
			Set<ObjectProperty> disjointObjectProperties = new HashSet<ObjectProperty>();
			
			
			public DisjointObjectProperties(){
		    }
			
			public void append(DisjointObjectProperties element){				
				if (element.getDisjointObjectProperties().size()>0) {this.getDisjointObjectProperties().addAll(element.getDisjointObjectProperties());return;}
		    }
			
			public Set<ObjectProperty> getDisjointObjectProperties()
			{
				return this.disjointObjectProperties;
			}
			
			public void addDisjointObjectProperties(ObjectProperty newDisjointObjectProperties)
			{
				this.disjointObjectProperties.add(newDisjointObjectProperties);
			}
			
			public void removeDisjointObjectProperties(ObjectProperty oldDisjointObjectProperties)
			{
				this.disjointObjectProperties.remove(oldDisjointObjectProperties);
			}

		}
		public static class EquivalentObjectProperties extends ObjectPropertyAxiom{
			Set<ObjectProperty> equivalentObjectProperties = new HashSet<ObjectProperty>();
			
			
			public EquivalentObjectProperties(){
		    }
			
			public void append(EquivalentObjectProperties element){				
				if (element.getEquivalentObjectProperties().size()>0) {this.getEquivalentObjectProperties().addAll(element.getEquivalentObjectProperties());return;}
		    }
			
			public Set<ObjectProperty> getEquivalentObjectProperties()
			{
				return this.equivalentObjectProperties;
			}
			
			public void addEquivalentObjectProperties(ObjectProperty newEquivalentObjectProperties)
			{
				this.equivalentObjectProperties.add(newEquivalentObjectProperties);
			}
			
			public void removeDisjointObjectProperties(ObjectProperty oldEquivalentObjectProperties)
			{
				this.equivalentObjectProperties.remove(oldEquivalentObjectProperties);
			}

		}
		public static class FunctionalObjectProperty extends ObjectPropertyAxiom{
			ObjectProperty objectProperty;
			public FunctionalObjectProperty(){		
		    }
			
			public void append(FunctionalObjectProperty element){				
				if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
		    }
			
			public ObjectProperty getObjectProperty()
			{
				return this.objectProperty;
			}
			
			public void setObjectProperty(ObjectProperty newObjectProperty)
			{
				this.objectProperty=newObjectProperty;
			}
		}
		public static class InverseFunctionalObjectProperty extends ObjectPropertyAxiom{
			ObjectProperty objectProperty;
			public InverseFunctionalObjectProperty(){		
		    }
			
			public void append(InverseFunctionalObjectProperty element){				
				if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
		    }
			
			public ObjectProperty getObjectProperty()
			{
				return this.objectProperty;
			}
			
			public void setObjectProperty(ObjectProperty newObjectProperty)
			{
				this.objectProperty=newObjectProperty;
			}
		}
		public static class InverseObjectProperties extends ObjectPropertyAxiom{
			Set<ObjectProperty> inverseObjectProperties = new HashSet<ObjectProperty>();
			
			
			public InverseObjectProperties(){
		    }
			
			public void append(InverseObjectProperties element){				
				if (element.getInverseObjectProperties().size()>0) {this.getInverseObjectProperties().addAll(element.getInverseObjectProperties());return;}
		    }
			
			public Set<ObjectProperty> getInverseObjectProperties()
			{
				return this.inverseObjectProperties;
			}
			
			public void addInverseObjectProperties(ObjectProperty newInverseObjectProperties)
			{
				this.inverseObjectProperties.add(newInverseObjectProperties);
			}
			
			public void removeInverseObjectProperties(ObjectProperty oldInverseObjectProperties)
			{
				this.inverseObjectProperties.remove(oldInverseObjectProperties);
			}

		}
		public static class IrreflexiveObjectProperty extends ObjectPropertyAxiom{
			ObjectProperty objectProperty;
			public IrreflexiveObjectProperty(){		
		    }
			
			public void append(IrreflexiveObjectProperty element){				
				if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
		    }
			
			public ObjectProperty getObjectProperty()
			{
				return this.objectProperty;
			}
			
			public void setObjectProperty(ObjectProperty newObjectProperty)
			{
				this.objectProperty=newObjectProperty;
			}
		}
		public static class ObjectPropertyDomain extends ObjectPropertyAxiom{
			ObjectProperty objectProperty;
			Description domain;
			public ObjectPropertyDomain(){		
		    }
			
			public void append(ObjectPropertyDomain element){				
				if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
				if (this.getDomain()==null && element.getDomain()!=null) {this.setDomain(element.getDomain());return;}
		    }
			
			public ObjectProperty getObjectProperty()
			{
				return this.objectProperty;
			}
			
			public void setObjectProperty(ObjectProperty newObjectProperty)
			{
				this.objectProperty=newObjectProperty;
			}
			public Description getDomain()
			{
				return this.domain;
			}
			
			public void setDomain(Description newDomain)
			{
				this.domain=newDomain;
			}
		}
		public static class ObjectPropertyRange extends ObjectPropertyAxiom{
			ObjectProperty objectProperty;
			Description range;
			public ObjectPropertyRange(){		
		    }
			
			public void append(ObjectPropertyRange element){				
				if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
				if (this.getRange()==null && element.getRange()!=null) {this.setRange(element.getRange());return;}
		    }
			
			public ObjectProperty getObjectProperty()
			{
				return this.objectProperty;
			}
			
			public void setObjectProperty(ObjectProperty newObjectProperty)
			{
				this.objectProperty=newObjectProperty;
			}
			public Description getRange()
			{
				return this.range;
			}
			
			public void setRange(Description newRange)
			{
				this.range=newRange;
			}
		}
		public static class ReflexiveObjectProperty extends ObjectPropertyAxiom{
			ObjectProperty objectProperty;
			public ReflexiveObjectProperty(){		
		    }
			
			public void append(ReflexiveObjectProperty element){				
				if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
		    }
			
			public ObjectProperty getObjectProperty()
			{
				return this.objectProperty;
			}
			
			public void setObjectProperty(ObjectProperty newObjectProperty)
			{
				this.objectProperty=newObjectProperty;
			}
		}
		public static class SymmetricObjectProperty extends ObjectPropertyAxiom{
			ObjectProperty objectProperty;
			public SymmetricObjectProperty(){		
		    }
			
			public void append(SymmetricObjectProperty element){				
				if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
		    }
			
			public ObjectProperty getObjectProperty()
			{
				return this.objectProperty;
			}
			
			public void setObjectProperty(ObjectProperty newObjectProperty)
			{
				this.objectProperty=newObjectProperty;
			}
		}
		public static class TransitiveObjectProperty extends ObjectPropertyAxiom{
			ObjectProperty objectProperty;
			public TransitiveObjectProperty(){		
		    }
			
			public void append(TransitiveObjectProperty element){				
				if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
		    }
			
			public ObjectProperty getObjectProperty()
			{
				return this.objectProperty;
			}
			
			public void setObjectProperty(ObjectProperty newObjectProperty)
			{
				this.objectProperty=newObjectProperty;
			}
		}
		public static class SubObjectPropertyOf extends ObjectPropertyAxiom{
			ObjectProperty superObjectProperty;
			Set<ObjectProperty> subObjectProperties = new HashSet<ObjectProperty>();
			
			
			public SubObjectPropertyOf(){
		    }
			
			public void append(SubObjectPropertyOf element){				
				if (this.getSuperObjectProperty()==null && element.getSuperObjectProperty()!=null) {this.setSuperObjectProperty(element.getSuperObjectProperty());return;}
				if (element.getSubObjectProperties().size()>0) {this.getSubObjectProperties().addAll(element.getSubObjectProperties());return;}
		    }
			
			
			
			public ObjectProperty getSuperObjectProperty()
			{
				return this.superObjectProperty;
			}
			
			public void setSuperObjectProperty(ObjectProperty newSuperObjectProperty)
			{
				this.superObjectProperty=newSuperObjectProperty;
			}
			
			public Set<ObjectProperty> getSubObjectProperties()
			{
				return this.subObjectProperties;
			}
			
			public void addSubObjectProperties(ObjectProperty newSubObjectProperties)
			{
				this.subObjectProperties.add(newSubObjectProperties);
			}
			
			public void removeSubObjectProperties(ObjectProperty oldSubObjectProperties)
			{
				this.subObjectProperties.remove(oldSubObjectProperties);
			}
		}
	}
}
