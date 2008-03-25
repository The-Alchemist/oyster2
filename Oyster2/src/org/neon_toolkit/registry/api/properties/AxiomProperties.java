package org.neon_toolkit.registry.api.properties;



import java.util.Iterator;
import java.util.LinkedList;

import org.neon_toolkit.owlodm.api.Axiom;
import org.neon_toolkit.owlodm.api.Axiom.Declaration;
import org.neon_toolkit.owlodm.api.Axiom.EntityAnnotation;
import org.neon_toolkit.owlodm.api.Axiom.ClassAxiom.DisjointClasses;
import org.neon_toolkit.owlodm.api.Axiom.ClassAxiom.DisjointUnion;
import org.neon_toolkit.owlodm.api.Axiom.ClassAxiom.EquivalentClasses;
import org.neon_toolkit.owlodm.api.Axiom.ClassAxiom.SubClassOf;
import org.neon_toolkit.owlodm.api.Axiom.DataPropertyAxiom.DataPropertyDomain;
import org.neon_toolkit.owlodm.api.Axiom.DataPropertyAxiom.DataPropertyRange;
import org.neon_toolkit.owlodm.api.Axiom.DataPropertyAxiom.DisjointDataProperties;
import org.neon_toolkit.owlodm.api.Axiom.DataPropertyAxiom.EquivalentDataProperties;
import org.neon_toolkit.owlodm.api.Axiom.DataPropertyAxiom.FunctionalDataProperty;
import org.neon_toolkit.owlodm.api.Axiom.DataPropertyAxiom.SubDataPropertyOf;
import org.neon_toolkit.owlodm.api.Axiom.Fact.ClassAssertion;
import org.neon_toolkit.owlodm.api.Axiom.Fact.DataPropertyAssertion;
import org.neon_toolkit.owlodm.api.Axiom.Fact.DifferentIndividuals;
import org.neon_toolkit.owlodm.api.Axiom.Fact.NegativeDataPropertyAssertion;
import org.neon_toolkit.owlodm.api.Axiom.Fact.NegativeObjectPropertyAssertion;
import org.neon_toolkit.owlodm.api.Axiom.Fact.ObjectPropertyAssertion;
import org.neon_toolkit.owlodm.api.Axiom.Fact.SameIndividual;
import org.neon_toolkit.owlodm.api.Axiom.ObjectPropertyAxiom.AsymmetricObjectProperty;
import org.neon_toolkit.owlodm.api.Axiom.ObjectPropertyAxiom.DisjointObjectProperties;
import org.neon_toolkit.owlodm.api.Axiom.ObjectPropertyAxiom.EquivalentObjectProperties;
import org.neon_toolkit.owlodm.api.Axiom.ObjectPropertyAxiom.FunctionalObjectProperty;
import org.neon_toolkit.owlodm.api.Axiom.ObjectPropertyAxiom.InverseFunctionalObjectProperty;
import org.neon_toolkit.owlodm.api.Axiom.ObjectPropertyAxiom.InverseObjectProperties;
import org.neon_toolkit.owlodm.api.Axiom.ObjectPropertyAxiom.IrreflexiveObjectProperty;
import org.neon_toolkit.owlodm.api.Axiom.ObjectPropertyAxiom.ObjectPropertyDomain;
import org.neon_toolkit.owlodm.api.Axiom.ObjectPropertyAxiom.ObjectPropertyRange;
import org.neon_toolkit.owlodm.api.Axiom.ObjectPropertyAxiom.ReflexiveObjectProperty;
import org.neon_toolkit.owlodm.api.Axiom.ObjectPropertyAxiom.SubObjectPropertyOf;
import org.neon_toolkit.owlodm.api.Axiom.ObjectPropertyAxiom.SymmetricObjectProperty;
import org.neon_toolkit.owlodm.api.Axiom.ObjectPropertyAxiom.TransitiveObjectProperty;
import org.neon_toolkit.registry.oyster2.Constants;
import org.neon_toolkit.registry.oyster2.OntologyProperty;

/**
 * The class MappingProperties provides the methods to
 * retrieve the properties from OMV Mapping objects 
 * representing the object of the registry 
 * @author Raul Palma
 * @version 1.0, March 2008
 */
public class AxiomProperties{
	
	

	static public LinkedList getAxiomProperties(Axiom m){
		//Ignore now axiom annotation
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		
		if (m instanceof DisjointClasses){
			DisjointClasses t = (DisjointClasses)m;
			if (t.getDisjointClasses().size()>0) {
				Iterator it = t.getDisjointClasses().iterator();
				while(it.hasNext()){
					String nl = (String)it.next();
					OntologyProperty prop = new OntologyProperty(Constants.disjointClasses, nl);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m instanceof DisjointUnion){
			DisjointUnion t = (DisjointUnion)m;
			if (t.getDisjointClasses().size()>0) {
				Iterator it = t.getDisjointClasses().iterator();
				while(it.hasNext()){
					String nl = (String)it.next();
					OntologyProperty prop = new OntologyProperty(Constants.disjointClasses, nl);
					tProperties.addFirst(prop);
				}
			}
			if (t.getUnionClass()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.unionClass, t.getUnionClass());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof EquivalentClasses){
			EquivalentClasses t = (EquivalentClasses)m;
			if (t.getEquivalentClasses().size()>0) {
				Iterator it = t.getEquivalentClasses().iterator();
				while(it.hasNext()){
					String nl = (String)it.next();
					OntologyProperty prop = new OntologyProperty(Constants.equivalentClasses, nl);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m instanceof SubClassOf){
			SubClassOf t = (SubClassOf)m;
			if (t.getSubClass()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.subClass, t.getSubClass());
				tProperties.addFirst(prop);
			}
			if (t.getSuperClass()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.superClass, t.getSuperClass());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof DataPropertyDomain){
			DataPropertyDomain t = (DataPropertyDomain)m;
			if (t.getDataProperty()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.dataProperty, t.getDataProperty());
				tProperties.addFirst(prop);
			}
			if (t.getDomain()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.domain, t.getDomain());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof DataPropertyRange){
			DataPropertyRange t = (DataPropertyRange)m;
			if (t.getDataProperty()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.dataProperty, t.getDataProperty());
				tProperties.addFirst(prop);
			}
			if (t.getRange()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.range, t.getRange());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof DisjointDataProperties){
			DisjointDataProperties t = (DisjointDataProperties)m;
			if (t.getDataProperties().size()>0) {
				Iterator it = t.getDataProperties().iterator();
				while(it.hasNext()){
					String nl = (String)it.next();
					OntologyProperty prop = new OntologyProperty(Constants.dataProperties, nl);
					tProperties.addFirst(prop);
				}
			}

		}
		if (m instanceof EquivalentDataProperties){
			EquivalentDataProperties t = (EquivalentDataProperties)m;
			if (t.getDataProperties().size()>0) {
				Iterator it = t.getDataProperties().iterator();
				while(it.hasNext()){
					String nl = (String)it.next();
					OntologyProperty prop = new OntologyProperty(Constants.dataProperties, nl);
					tProperties.addFirst(prop);
				}
			}

		}
		if (m instanceof FunctionalDataProperty){
			FunctionalDataProperty t = (FunctionalDataProperty)m;
			if (t.getDataProperty()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.dataProperty, t.getDataProperty());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof SubDataPropertyOf){
			SubDataPropertyOf t = (SubDataPropertyOf)m;
			if (t.getSubDataProperty()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.subDataProperty, t.getSubDataProperty());
				tProperties.addFirst(prop);
			}
			if (t.getSuperDataProperty()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.superDataProperty, t.getSuperDataProperty());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof Declaration){
			Declaration t = (Declaration)m;
			if (t.getEntity()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.entity, t.getEntity());
				tProperties.addFirst(prop);
			}
			
		}
		if (m instanceof EntityAnnotation){
			EntityAnnotation t = (EntityAnnotation)m;
			if (t.getEntity()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.entity, t.getEntity());
				tProperties.addFirst(prop);
			}
			if (t.getEntityAnnotation().size()>0) {
				Iterator it = t.getEntityAnnotation().iterator();
				while(it.hasNext()){
					String nl = (String)it.next();
					OntologyProperty prop = new OntologyProperty(Constants.entityAnnotation, nl);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m instanceof ClassAssertion){
			ClassAssertion t = (ClassAssertion)m;
			if (t.getOWLClass()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.OWLClass, t.getOWLClass());
				tProperties.addFirst(prop);
			}
			if (t.getIndividual()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.individual, t.getIndividual());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof DataPropertyAssertion){
			DataPropertyAssertion t = (DataPropertyAssertion)m;
			if (t.getDataProperty()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.dataProperty, t.getDataProperty());
				tProperties.addFirst(prop);
			}
			if (t.getSourceIndividual()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.sourceIndividual, t.getSourceIndividual());
				tProperties.addFirst(prop);
			}
			if (t.getTargetValue()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.targetValue, t.getTargetValue());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof DifferentIndividuals){
			DifferentIndividuals t = (DifferentIndividuals)m;
			if (t.getDifferentIndividuals().size()>0) {
				Iterator it = t.getDifferentIndividuals().iterator();
				while(it.hasNext()){
					String nl = (String)it.next();
					OntologyProperty prop = new OntologyProperty(Constants.differentIndividuals, nl);
					tProperties.addFirst(prop);
				}
			}

		}
		if (m instanceof NegativeDataPropertyAssertion){
			NegativeDataPropertyAssertion t = (NegativeDataPropertyAssertion)m;
			if (t.getDataProperty()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.dataProperty, t.getDataProperty());
				tProperties.addFirst(prop);
			}
			if (t.getSourceIndividual()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.sourceIndividual, t.getSourceIndividual());
				tProperties.addFirst(prop);
			}
			if (t.getTargetValue()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.targetValue, t.getTargetValue());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof NegativeObjectPropertyAssertion){
			NegativeObjectPropertyAssertion t = (NegativeObjectPropertyAssertion)m;
			if (t.getObjectProperty()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, t.getObjectProperty());
				tProperties.addFirst(prop);
			}
			if (t.getSourceIndividual()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.sourceIndividual, t.getSourceIndividual());
				tProperties.addFirst(prop);
			}
			if (t.getTargetIndividual()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.targetIndividual, t.getTargetIndividual());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof ObjectPropertyAssertion){
			ObjectPropertyAssertion t = (ObjectPropertyAssertion)m;
			if (t.getObjectProperty()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, t.getObjectProperty());
				tProperties.addFirst(prop);
			}
			if (t.getSourceIndividual()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.sourceIndividual, t.getSourceIndividual());
				tProperties.addFirst(prop);
			}
			if (t.getTargetIndividual()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.targetIndividual, t.getTargetIndividual());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof SameIndividual){
			SameIndividual t = (SameIndividual)m;
			if (t.getSameIndividuals().size()>0) {
				Iterator it = t.getSameIndividuals().iterator();
				while(it.hasNext()){
					String nl = (String)it.next();
					OntologyProperty prop = new OntologyProperty(Constants.sameIndividuals, nl);
					tProperties.addFirst(prop);
				}
			}

		}
		if (m instanceof AsymmetricObjectProperty){
			AsymmetricObjectProperty t = (AsymmetricObjectProperty)m;
			if (t.getObjectProperty()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, t.getObjectProperty());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof DisjointObjectProperties){
			DisjointObjectProperties t = (DisjointObjectProperties)m;
			if (t.getDisjointObjectProperties().size()>0) {
				Iterator it = t.getDisjointObjectProperties().iterator();
				while(it.hasNext()){
					String nl = (String)it.next();
					OntologyProperty prop = new OntologyProperty(Constants.disjointObjectProperties, nl);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m instanceof EquivalentObjectProperties){
			EquivalentObjectProperties t = (EquivalentObjectProperties)m;
			if (t.getEquivalentObjectProperties().size()>0) {
				Iterator it = t.getEquivalentObjectProperties().iterator();
				while(it.hasNext()){
					String nl = (String)it.next();
					OntologyProperty prop = new OntologyProperty(Constants.equivalentObjectProperties, nl);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m instanceof FunctionalObjectProperty){
			FunctionalObjectProperty t = (FunctionalObjectProperty)m;
			if (t.getObjectProperty()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, t.getObjectProperty());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof InverseFunctionalObjectProperty){
			InverseFunctionalObjectProperty t = (InverseFunctionalObjectProperty)m;
			if (t.getObjectProperty()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, t.getObjectProperty());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof InverseObjectProperties){
			InverseObjectProperties t = (InverseObjectProperties)m;
			if (t.getInverseObjectProperties().size()>0) {
				Iterator it = t.getInverseObjectProperties().iterator();
				while(it.hasNext()){
					String nl = (String)it.next();
					OntologyProperty prop = new OntologyProperty(Constants.inverseObjectProperties, nl);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m instanceof IrreflexiveObjectProperty){
			IrreflexiveObjectProperty t = (IrreflexiveObjectProperty)m;
			if (t.getObjectProperty()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, t.getObjectProperty());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof ObjectPropertyDomain){
			ObjectPropertyDomain t = (ObjectPropertyDomain)m;
			if (t.getObjectProperty()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, t.getObjectProperty());
				tProperties.addFirst(prop);
			}
			if (t.getDomain()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.domain, t.getDomain());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof ObjectPropertyRange){
			ObjectPropertyRange t = (ObjectPropertyRange)m;
			if (t.getObjectProperty()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, t.getObjectProperty());
				tProperties.addFirst(prop);
			}
			if (t.getRange()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.range, t.getRange());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof ReflexiveObjectProperty){
			ReflexiveObjectProperty t = (ReflexiveObjectProperty)m;
			if (t.getObjectProperty()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, t.getObjectProperty());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof SymmetricObjectProperty){
			SymmetricObjectProperty t = (SymmetricObjectProperty)m;
			if (t.getObjectProperty()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, t.getObjectProperty());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof TransitiveObjectProperty){
			TransitiveObjectProperty t = (TransitiveObjectProperty)m;
			if (t.getObjectProperty()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, t.getObjectProperty());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof SubObjectPropertyOf){
			SubObjectPropertyOf t = (SubObjectPropertyOf)m;
			if (t.getSuperObjectProperty()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.superObjectProperty, t.getSuperObjectProperty());
				tProperties.addFirst(prop);
			}
			if (t.getSubObjectProperties().size()>0) {
				Iterator it = t.getSubObjectProperties().iterator();
				while(it.hasNext()){
					String nl = (String)it.next();
					OntologyProperty prop = new OntologyProperty(Constants.subObjectProperties, nl);
					tProperties.addFirst(prop);
				}
			}
		}
		
		return tProperties;
	}
	
						
}