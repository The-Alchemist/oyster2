package org.neon_toolkit.owlodm.api;

import java.util.HashSet;
import java.util.Set;

/**
 * The class Axiom provides the object 
 * representation of the Axiom OWLODM Object  
 * @author Raul Palma
 * @version 0.1, May 2007
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
			Set<String> disjointClasses = new HashSet<String>();
			
			public DisjointClasses(){
		    }
			
			public void append(DisjointClasses element){				
				if (element.getDisjointClasses().size()>0) {this.getDisjointClasses().addAll(element.getDisjointClasses());return;}
		    }
			
			public Set<String> getDisjointClasses()
			{
				return this.disjointClasses;
			}
			
			public void addDisjointClasses(String newDisjointClasses)
			{
				this.disjointClasses.add(newDisjointClasses);
			}
			
			public void removeDisjointClasses(String oldDisjointClasses)
			{
				this.disjointClasses.remove(oldDisjointClasses);
			}

		}
		public static class DisjointUnion extends ClassAxiom{
			String unionClass;
			Set<String> disjointClasses = new HashSet<String>();
			
			public DisjointUnion(){
		    }
			
			public void append(DisjointUnion element){
				if (this.getUnionClass()==null && element.getUnionClass()!=null) {this.setUnionClass(element.getUnionClass());return;}
				if (element.getDisjointClasses().size()>0) {this.getDisjointClasses().addAll(element.getDisjointClasses());return;}
		    }
			
			public void setUnionClass(String newUnionClass)
			{
				this.unionClass=newUnionClass;
			}
			
			public String getUnionClass()
			{
				return this.unionClass;
			}
			
			public Set<String> getDisjointClasses()
			{
				return this.disjointClasses;
			}
			
			public void addDisjointClasses(String newDisjointClasses)
			{
				this.disjointClasses.add(newDisjointClasses);
			}
			
			public void removeDisjointClasses(String oldDisjointClasses)
			{
				this.disjointClasses.remove(oldDisjointClasses);
			}

		}
		public static class EquivalentClasses extends ClassAxiom{
			Set<String> equivalentClasses = new HashSet<String>();
			
			public EquivalentClasses(){
		    }
			
			public void append(EquivalentClasses element){				
				if (element.getEquivalentClasses().size()>0) {this.getEquivalentClasses().addAll(element.getEquivalentClasses());return;}
		    }
			
			public Set<String> getEquivalentClasses()
			{
				return this.equivalentClasses;
			}
			
			public void addEquivalentClasses(String newEquivalentClasses)
			{
				this.equivalentClasses.add(newEquivalentClasses);
			}
			
			public void removeEquivalentClasses(String oldEquivalentClasses)
			{
				this.equivalentClasses.remove(oldEquivalentClasses);
			}

		}
		public static class SubClassOf extends ClassAxiom{
			String subClass;
			String superClass;
			
			public SubClassOf(){
		    }
			
			public void append(SubClassOf element){				
				if (this.getSubClass()==null && element.getSubClass()!=null) {this.setSubClass(element.getSubClass());return;}
				if (this.getSuperClass()==null && element.getSuperClass()!=null) {this.setSuperClass(element.getSuperClass());return;}
		    }
			
			public void setSubClass(String newSubClass)
			{
				this.subClass=newSubClass;
			}
			
			public String getSubClass()
			{
				return this.subClass;
			}
			
			public void setSuperClass(String newSuperClass)
			{
				this.superClass=newSuperClass;
			}
			
			public String getSuperClass()
			{
				return this.superClass;
			}
		}

	}
	
	public static class DataPropertyAxiom extends Axiom{
		public DataPropertyAxiom(){
	    }
		public static class DataPropertyDomain extends DataPropertyAxiom{
			String domain;
			String dataProperty;
			
			public DataPropertyDomain(){
		    }
			
			public void append(DataPropertyDomain element){				
				if (this.getDomain()==null && element.getDomain()!=null) {this.setDomain(element.getDomain());return;}
				if (this.getDataProperty()==null && element.getDataProperty()!=null) {this.setDataProperty(element.getDataProperty());return;}
		    }
			
			public void setDomain(String newDomain)
			{
				this.domain=newDomain;
			}
			
			public String getDomain()
			{
				return this.domain;
			}
			
			public void setDataProperty(String newDataProperty)
			{
				this.dataProperty=newDataProperty;
			}
			
			public String getDataProperty()
			{
				return this.dataProperty;
			}

		}
		public static class DataPropertyRange extends DataPropertyAxiom{
			String range;
			String dataProperty;
			
			public DataPropertyRange(){
		    }
			
			public void append(DataPropertyRange element){				
				if (this.getRange()==null && element.getRange()!=null) {this.setRange(element.getRange());return;}
				if (this.getDataProperty()==null && element.getDataProperty()!=null) {this.setDataProperty(element.getDataProperty());return;}
		    }
			
			public void setRange(String newRange)
			{
				this.range=newRange;
			}
			
			public String getRange()
			{
				return this.range;
			}
			
			public void setDataProperty(String newDataProperty)
			{
				this.dataProperty=newDataProperty;
			}
			
			public String getDataProperty()
			{
				return this.dataProperty;
			}

		}
		public static class DisjointDataProperties extends DataPropertyAxiom{
			Set<String> dataProperties = new HashSet<String>();
			
			
			public DisjointDataProperties(){
		    }
			
			public void append(DisjointDataProperties element){				
				if (element.getDataProperties().size()>0) {this.getDataProperties().addAll(element.getDataProperties());return;}
		    }
			
			public Set<String> getDataProperties()
			{
				return this.dataProperties;
			}
			
			public void addDataProperties(String newDataProperties)
			{
				this.dataProperties.add(newDataProperties);
			}
			
			public void removeDataProperties(String oldDataProperties)
			{
				this.dataProperties.remove(oldDataProperties);
			}

		}
		public static class EquivalentDataProperties extends DataPropertyAxiom{
			Set<String> dataProperties = new HashSet<String>();
			
			
			public EquivalentDataProperties(){
		    }
			
			public void append(EquivalentDataProperties element){				
				if (element.getDataProperties().size()>0) {this.getDataProperties().addAll(element.getDataProperties());return;}
		    }
			
			public Set<String> getDataProperties()
			{
				return this.dataProperties;
			}
			
			public void addDataProperties(String newDataProperties)
			{
				this.dataProperties.add(newDataProperties);
			}
			
			public void removeDataProperties(String oldDataProperties)
			{
				this.dataProperties.remove(oldDataProperties);
			}

		}
		public static class FunctionalDataProperty extends DataPropertyAxiom{
			String dataProperty;
			
			
			public FunctionalDataProperty(){
		    }
			
			public void append(FunctionalDataProperty element){				
				if (this.getDataProperty()==null && element.getDataProperty()!=null) {this.setDataProperty(element.getDataProperty());return;}
		    }
			
			public String getDataProperty()
			{
				return this.dataProperty;
			}
			
			public void setDataProperty(String newDataProperty)
			{
				this.dataProperty=newDataProperty;
			}
			
		}
		public static class SubDataPropertyOf extends DataPropertyAxiom{
			String subDataProperty;
			String superDataProperty;
			
			
			public SubDataPropertyOf(){
		    }
			
			public void append(SubDataPropertyOf element){				
				if (this.getSubDataProperty()==null && element.getSubDataProperty()!=null) {this.setSubDataProperty(element.getSubDataProperty());return;}
				if (this.getSuperDataProperty()==null && element.getSuperDataProperty()!=null) {this.setSuperDataProperty(element.getSuperDataProperty());return;}
		    }
			
			public String getSubDataProperty()
			{
				return this.subDataProperty;
			}
			
			public void setSubDataProperty(String newSubDataProperty)
			{
				this.subDataProperty=newSubDataProperty;
			}
			public String getSuperDataProperty()
			{
				return this.superDataProperty;
			}
			
			public void setSuperDataProperty(String newSuperDataProperty)
			{
				this.superDataProperty=newSuperDataProperty;
			}
		}
	}
	
	public static class Declaration extends Axiom{
		String entity;
		public Declaration(){
	    }
		public void append(Declaration element){				
			if (this.getEntity()==null && element.getEntity()!=null) {this.setEntity(element.getEntity());return;}
	    }
		public String getEntity()
		{
			return this.entity;
		}		
		public void setEntity(String newEntity)
		{
			this.entity=newEntity;
		}
	}
	
	public static class EntityAnnotation extends Axiom{
		String entity;
		Set<String> entityAnnotation = new HashSet<String>();
		
		public EntityAnnotation(){
	    }
		public void append(EntityAnnotation element){				
			if (this.getEntity()==null && element.getEntity()!=null) {this.setEntity(element.getEntity());return;}
			if (element.getEntityAnnotation().size()>0) {this.getEntityAnnotation().addAll(element.getEntityAnnotation());return;}
	    }
		public String getEntity()
		{
			return this.entity;
		}		
		public void setEntity(String newEntity)
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
			String OWLClass;
			String individual;
			public ClassAssertion(){
		    }
			public void append(ClassAssertion element){				
				if (this.getOWLClass()==null && element.getOWLClass()!=null) {this.setOWLClass(element.getOWLClass());return;}
				if (this.getIndividual()==null && element.getIndividual()!=null) {this.setIndividual(element.getIndividual());return;}
		    }
			public String getOWLClass()
			{
				return this.OWLClass;
			}
			
			public void setOWLClass(String newOWLClass)
			{
				this.OWLClass=newOWLClass;
			}
			public String getIndividual()
			{
				return this.individual;
			}
			
			public void setIndividual(String newIndividual)
			{
				this.individual=newIndividual;
			}
		}
		public static class DataPropertyAssertion extends Fact{
			String dataProperty;
			String sourceIndividual;
			String targetValue;
			
			public DataPropertyAssertion(){
		    }
			public void append(DataPropertyAssertion element){				
				if (this.getDataProperty()==null && element.getDataProperty()!=null) {this.setDataProperty(element.getDataProperty());return;}
				if (this.getSourceIndividual()==null && element.getSourceIndividual()!=null) {this.setSourceIndividual(element.getSourceIndividual());return;}
				if (this.getTargetValue()==null && element.getTargetValue()!=null) {this.setTargetValue(element.getTargetValue());return;}
		    }
			
			public String getDataProperty()
			{
				return this.dataProperty;
			}
			
			public void setDataProperty(String newDataProperty)
			{
				this.dataProperty=newDataProperty;
			}
			
			public String getSourceIndividual()
			{
				return this.sourceIndividual;
			}
			
			public void setSourceIndividual(String newSourceIndividual)
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
			Set<String> differentIndividuals = new HashSet<String>();
			
			
			public DifferentIndividuals(){
		    }
			
			public void append(DifferentIndividuals element){				
				if (element.getDifferentIndividuals().size()>0) {this.getDifferentIndividuals().addAll(element.getDifferentIndividuals());return;}
		    }
			
			public Set<String> getDifferentIndividuals()
			{
				return this.differentIndividuals;
			}
			
			public void addDifferentIndividuals(String newDifferentIndividuals)
			{
				this.differentIndividuals.add(newDifferentIndividuals);
			}
			
			public void removeDifferentIndividuals(String oldDifferentIndividuals)
			{
				this.differentIndividuals.remove(oldDifferentIndividuals);
			}

		}
		public static class NegativeDataPropertyAssertion extends Fact{
			String dataProperty;
			String sourceIndividual;
			String targetValue;
			
			public NegativeDataPropertyAssertion(){
		    }
			public void append(NegativeDataPropertyAssertion element){				
				if (this.getDataProperty()==null && element.getDataProperty()!=null) {this.setDataProperty(element.getDataProperty());return;}
				if (this.getSourceIndividual()==null && element.getSourceIndividual()!=null) {this.setSourceIndividual(element.getSourceIndividual());return;}
				if (this.getTargetValue()==null && element.getTargetValue()!=null) {this.setTargetValue(element.getTargetValue());return;}
		    }
			
			public String getDataProperty()
			{
				return this.dataProperty;
			}
			
			public void setDataProperty(String newDataProperty)
			{
				this.dataProperty=newDataProperty;
			}
			
			public String getSourceIndividual()
			{
				return this.sourceIndividual;
			}
			
			public void setSourceIndividual(String newSourceIndividual)
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
			String objectProperty;
			String sourceIndividual;
			String targetIndividual;
			
			public NegativeObjectPropertyAssertion(){
		    }
			public void append(NegativeObjectPropertyAssertion element){				
				if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
				if (this.getSourceIndividual()==null && element.getSourceIndividual()!=null) {this.setSourceIndividual(element.getSourceIndividual());return;}
				if (this.getTargetIndividual()==null && element.getTargetIndividual()!=null) {this.setTargetIndividual(element.getTargetIndividual());return;}
		    }
			
			public String getObjectProperty()
			{
				return this.objectProperty;
			}
			
			public void setObjectProperty(String newObjectProperty)
			{
				this.objectProperty=newObjectProperty;
			}
			
			public String getSourceIndividual()
			{
				return this.sourceIndividual;
			}
			
			public void setSourceIndividual(String newSourceIndividual)
			{
				this.sourceIndividual=newSourceIndividual;
			}
			
			public String getTargetIndividual()
			{
				return this.targetIndividual;
			}
			
			public void setTargetIndividual(String newTargetIndividual)
			{
				this.targetIndividual=newTargetIndividual;
			}
		}
		public static class ObjectPropertyAssertion extends Fact{
			String objectProperty;
			String sourceIndividual;
			String targetIndividual;
			
			public ObjectPropertyAssertion(){
		    }
			public void append(ObjectPropertyAssertion element){				
				if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
				if (this.getSourceIndividual()==null && element.getSourceIndividual()!=null) {this.setSourceIndividual(element.getSourceIndividual());return;}
				if (this.getTargetIndividual()==null && element.getTargetIndividual()!=null) {this.setTargetIndividual(element.getTargetIndividual());return;}
		    }
			
			public String getObjectProperty()
			{
				return this.objectProperty;
			}
			
			public void setObjectProperty(String newObjectProperty)
			{
				this.objectProperty=newObjectProperty;
			}
			
			public String getSourceIndividual()
			{
				return this.sourceIndividual;
			}
			
			public void setSourceIndividual(String newSourceIndividual)
			{
				this.sourceIndividual=newSourceIndividual;
			}
			
			public String getTargetIndividual()
			{
				return this.targetIndividual;
			}
			
			public void setTargetIndividual(String newTargetIndividual)
			{
				this.targetIndividual=newTargetIndividual;
			}
		}
		public static class SameIndividual extends Fact{
			Set<String> sameIndividuals = new HashSet<String>();
			
			
			public SameIndividual(){
		    }
			
			public void append(SameIndividual element){				
				if (element.getSameIndividuals().size()>0) {this.getSameIndividuals().addAll(element.getSameIndividuals());return;}
		    }
			
			public Set<String> getSameIndividuals()
			{
				return this.sameIndividuals;
			}
			
			public void addSameIndividuals(String newSameIndividual)
			{
				this.sameIndividuals.add(newSameIndividual);
			}
			
			public void removeSameIndividuals(String oldSameIndividual)
			{
				this.sameIndividuals.remove(oldSameIndividual);
			}

		}
	}
	
	public static class ObjectPropertyAxiom extends Axiom{
		public ObjectPropertyAxiom(){		
	    }
		public static class AsymmetricObjectProperty extends ObjectPropertyAxiom{
			String objectProperty;
			public AsymmetricObjectProperty(){		
		    }
			
			public void append(AsymmetricObjectProperty element){				
				if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
		    }
			
			public String getObjectProperty()
			{
				return this.objectProperty;
			}
			
			public void setObjectProperty(String newObjectProperty)
			{
				this.objectProperty=newObjectProperty;
			}
		}
		public static class DisjointObjectProperties extends ObjectPropertyAxiom{
			Set<String> disjointObjectProperties = new HashSet<String>();
			
			
			public DisjointObjectProperties(){
		    }
			
			public void append(DisjointObjectProperties element){				
				if (element.getDisjointObjectProperties().size()>0) {this.getDisjointObjectProperties().addAll(element.getDisjointObjectProperties());return;}
		    }
			
			public Set<String> getDisjointObjectProperties()
			{
				return this.disjointObjectProperties;
			}
			
			public void addDisjointObjectProperties(String newDisjointObjectProperties)
			{
				this.disjointObjectProperties.add(newDisjointObjectProperties);
			}
			
			public void removeDisjointObjectProperties(String oldDisjointObjectProperties)
			{
				this.disjointObjectProperties.remove(oldDisjointObjectProperties);
			}

		}
		public static class EquivalentObjectProperties extends ObjectPropertyAxiom{
			Set<String> equivalentObjectProperties = new HashSet<String>();
			
			
			public EquivalentObjectProperties(){
		    }
			
			public void append(EquivalentObjectProperties element){				
				if (element.getEquivalentObjectProperties().size()>0) {this.getEquivalentObjectProperties().addAll(element.getEquivalentObjectProperties());return;}
		    }
			
			public Set<String> getEquivalentObjectProperties()
			{
				return this.equivalentObjectProperties;
			}
			
			public void addEquivalentObjectProperties(String newEquivalentObjectProperties)
			{
				this.equivalentObjectProperties.add(newEquivalentObjectProperties);
			}
			
			public void removeDisjointObjectProperties(String oldEquivalentObjectProperties)
			{
				this.equivalentObjectProperties.remove(oldEquivalentObjectProperties);
			}

		}
		public static class FunctionalObjectProperty extends ObjectPropertyAxiom{
			String objectProperty;
			public FunctionalObjectProperty(){		
		    }
			
			public void append(FunctionalObjectProperty element){				
				if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
		    }
			
			public String getObjectProperty()
			{
				return this.objectProperty;
			}
			
			public void setObjectProperty(String newObjectProperty)
			{
				this.objectProperty=newObjectProperty;
			}
		}
		public static class InverseFunctionalObjectProperty extends ObjectPropertyAxiom{
			String objectProperty;
			public InverseFunctionalObjectProperty(){		
		    }
			
			public void append(InverseFunctionalObjectProperty element){				
				if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
		    }
			
			public String getObjectProperty()
			{
				return this.objectProperty;
			}
			
			public void setObjectProperty(String newObjectProperty)
			{
				this.objectProperty=newObjectProperty;
			}
		}
		public static class InverseObjectProperties extends ObjectPropertyAxiom{
			Set<String> inverseObjectProperties = new HashSet<String>();
			
			
			public InverseObjectProperties(){
		    }
			
			public void append(InverseObjectProperties element){				
				if (element.getInverseObjectProperties().size()>0) {this.getInverseObjectProperties().addAll(element.getInverseObjectProperties());return;}
		    }
			
			public Set<String> getInverseObjectProperties()
			{
				return this.inverseObjectProperties;
			}
			
			public void addInverseObjectProperties(String newInverseObjectProperties)
			{
				this.inverseObjectProperties.add(newInverseObjectProperties);
			}
			
			public void removeInverseObjectProperties(String oldInverseObjectProperties)
			{
				this.inverseObjectProperties.remove(oldInverseObjectProperties);
			}

		}
		public static class IrreflexiveObjectProperty extends ObjectPropertyAxiom{
			String objectProperty;
			public IrreflexiveObjectProperty(){		
		    }
			
			public void append(IrreflexiveObjectProperty element){				
				if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
		    }
			
			public String getObjectProperty()
			{
				return this.objectProperty;
			}
			
			public void setObjectProperty(String newObjectProperty)
			{
				this.objectProperty=newObjectProperty;
			}
		}
		public static class ObjectPropertyDomain extends ObjectPropertyAxiom{
			String objectProperty;
			String domain;
			public ObjectPropertyDomain(){		
		    }
			
			public void append(ObjectPropertyDomain element){				
				if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
				if (this.getDomain()==null && element.getDomain()!=null) {this.setDomain(element.getDomain());return;}
		    }
			
			public String getObjectProperty()
			{
				return this.objectProperty;
			}
			
			public void setObjectProperty(String newObjectProperty)
			{
				this.objectProperty=newObjectProperty;
			}
			public String getDomain()
			{
				return this.domain;
			}
			
			public void setDomain(String newDomain)
			{
				this.domain=newDomain;
			}
		}
		public static class ObjectPropertyRange extends ObjectPropertyAxiom{
			String objectProperty;
			String range;
			public ObjectPropertyRange(){		
		    }
			
			public void append(ObjectPropertyRange element){				
				if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
				if (this.getRange()==null && element.getRange()!=null) {this.setRange(element.getRange());return;}
		    }
			
			public String getObjectProperty()
			{
				return this.objectProperty;
			}
			
			public void setObjectProperty(String newObjectProperty)
			{
				this.objectProperty=newObjectProperty;
			}
			public String getRange()
			{
				return this.range;
			}
			
			public void setRange(String newRange)
			{
				this.range=newRange;
			}
		}
		public static class ReflexiveObjectProperty extends ObjectPropertyAxiom{
			String objectProperty;
			public ReflexiveObjectProperty(){		
		    }
			
			public void append(ReflexiveObjectProperty element){				
				if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
		    }
			
			public String getObjectProperty()
			{
				return this.objectProperty;
			}
			
			public void setObjectProperty(String newObjectProperty)
			{
				this.objectProperty=newObjectProperty;
			}
		}
		public static class SymmetricObjectProperty extends ObjectPropertyAxiom{
			String objectProperty;
			public SymmetricObjectProperty(){		
		    }
			
			public void append(SymmetricObjectProperty element){				
				if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
		    }
			
			public String getObjectProperty()
			{
				return this.objectProperty;
			}
			
			public void setObjectProperty(String newObjectProperty)
			{
				this.objectProperty=newObjectProperty;
			}
		}
		public static class TransitiveObjectProperty extends ObjectPropertyAxiom{
			String objectProperty;
			public TransitiveObjectProperty(){		
		    }
			
			public void append(TransitiveObjectProperty element){				
				if (this.getObjectProperty()==null && element.getObjectProperty()!=null) {this.setObjectProperty(element.getObjectProperty());return;}
		    }
			
			public String getObjectProperty()
			{
				return this.objectProperty;
			}
			
			public void setObjectProperty(String newObjectProperty)
			{
				this.objectProperty=newObjectProperty;
			}
		}
		public static class SubObjectPropertyOf extends ObjectPropertyAxiom{
			String superObjectProperty;
			Set<String> subObjectProperties = new HashSet<String>();
			
			
			public SubObjectPropertyOf(){
		    }
			
			public void append(SubObjectPropertyOf element){				
				if (this.getSuperObjectProperty()==null && element.getSuperObjectProperty()!=null) {this.setSuperObjectProperty(element.getSuperObjectProperty());return;}
				if (element.getSubObjectProperties().size()>0) {this.getSubObjectProperties().addAll(element.getSubObjectProperties());return;}
		    }
			
			
			
			public String getSuperObjectProperty()
			{
				return this.superObjectProperty;
			}
			
			public void setSuperObjectProperty(String newSuperObjectProperty)
			{
				this.superObjectProperty=newSuperObjectProperty;
			}
			
			public Set<String> getSubObjectProperties()
			{
				return this.subObjectProperties;
			}
			
			public void addSubObjectProperties(String newSubObjectProperties)
			{
				this.subObjectProperties.add(newSubObjectProperties);
			}
			
			public void removeSubObjectProperties(String oldSubObjectProperties)
			{
				this.subObjectProperties.remove(oldSubObjectProperties);
			}
		}
	}
}
