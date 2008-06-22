package org.neon_toolkit.registry.api.properties;



import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.neon_toolkit.owlodm.api.Axiom;
import org.neon_toolkit.owlodm.api.Description;
import org.neon_toolkit.owlodm.api.OWLEntity;
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
import org.neon_toolkit.owlodm.api.OWLEntity.DataProperty;
import org.neon_toolkit.owlodm.api.OWLEntity.Datatype;
import org.neon_toolkit.owlodm.api.OWLEntity.Individual;
import org.neon_toolkit.owlodm.api.OWLEntity.ObjectProperty;
import org.neon_toolkit.registry.oyster2.Constants;
import org.neon_toolkit.registry.oyster2.ImportOntology;
import org.neon_toolkit.registry.oyster2.OntologyProperty;
import org.neon_toolkit.registry.util.GUID;

/**
 * The class AxiomProperties provides the methods to
 * retrieve the properties from Axiom objects 
 * representing the object of the registry 
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class AxiomProperties{
	static private ImportOntology IOntology= new ImportOntology();
	//static private String ontologyChangedURI="";
	
	@SuppressWarnings("unchecked")
	static public LinkedList getAxiomProperties(Axiom m){
		//Ignore now axiom annotation
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		List tList = new LinkedList();
		
		if (m instanceof DisjointClasses){
			DisjointClasses t = (DisjointClasses)m;
			if (t.getDisjointClasses().size()>0) {
				Iterator it = t.getDisjointClasses().iterator();
				while(it.hasNext()){
					Description ot = (Description)it.next();	
					tList.clear();
					tList=DescriptionProperties.getDescriptionProperties(ot);
					String tURN="";
					OntologyProperty tProp;
					if (ot.getURI()!=null){
						tURN=ot.getURI();
					}else{
						tURN=DescriptionProperties.getTURN(tList);
						tProp = new OntologyProperty(Constants.URI, tURN);
						tList.add(tProp);
					}
					String concept=DescriptionProperties.getDescriptionConcept(ot);
					tProp = new OntologyProperty(Constants.name, concept);
					tList.add(tProp);
					IOntology.addConceptToRegistry(1,tList,70, null); 
					OntologyProperty prop = new OntologyProperty(Constants.disjointClasses, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m instanceof DisjointUnion){
			DisjointUnion t = (DisjointUnion)m;
			if (t.getDisjointClasses().size()>0) {
				Iterator it = t.getDisjointClasses().iterator();
				while(it.hasNext()){
					Description ot = (Description)it.next();	
					tList.clear();
					tList=DescriptionProperties.getDescriptionProperties(ot);
					String tURN="";
					OntologyProperty tProp;
					if (ot.getURI()!=null){
						tURN=ot.getURI();
					}else{
						tURN=DescriptionProperties.getTURN(tList);
						tProp = new OntologyProperty(Constants.URI, tURN);
						tList.add(tProp);
					}
					String concept=DescriptionProperties.getDescriptionConcept(ot);
					tProp = new OntologyProperty(Constants.name, concept);
					tList.add(tProp);
					IOntology.addConceptToRegistry(1,tList,70, null); 
					OntologyProperty prop = new OntologyProperty(Constants.disjointClasses, tURN);
					tProperties.addFirst(prop);
				}
			}
			if (t.getUnionClass()!=null) {
				Description ot = t.getUnionClass();	
				tList.clear();
				tList=DescriptionProperties.getDescriptionProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN=DescriptionProperties.getTURN(tList);
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=DescriptionProperties.getDescriptionConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,70, null); 
				OntologyProperty prop = new OntologyProperty(Constants.unionClass, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof EquivalentClasses){
			EquivalentClasses t = (EquivalentClasses)m;
			if (t.getEquivalentClasses().size()>0) {
				Iterator it = t.getEquivalentClasses().iterator();
				while(it.hasNext()){
					Description ot = (Description)it.next();	
					tList.clear();
					tList=DescriptionProperties.getDescriptionProperties(ot);
					String tURN="";
					OntologyProperty tProp;
					if (ot.getURI()!=null){
						tURN=ot.getURI();
					}else{
						tURN=DescriptionProperties.getTURN(tList);
						tProp = new OntologyProperty(Constants.URI, tURN);
						tList.add(tProp);
					}
					String concept=DescriptionProperties.getDescriptionConcept(ot);
					tProp = new OntologyProperty(Constants.name, concept);
					tList.add(tProp);
					IOntology.addConceptToRegistry(1,tList,70, null); 
					OntologyProperty prop = new OntologyProperty(Constants.equivalentClasses, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m instanceof SubClassOf){
			SubClassOf t = (SubClassOf)m;
			if (t.getSubClass()!=null) {
				Description ot = t.getSubClass();	
				tList.clear();
				tList=DescriptionProperties.getDescriptionProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN=DescriptionProperties.getTURN(tList);
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=DescriptionProperties.getDescriptionConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,70, null); 
				OntologyProperty prop = new OntologyProperty(Constants.subClass, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getSuperClass()!=null) {
				Description ot = t.getSuperClass();		
				tList.clear();
				tList=DescriptionProperties.getDescriptionProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN=DescriptionProperties.getTURN(tList);
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=DescriptionProperties.getDescriptionConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,70, null); 
				OntologyProperty prop = new OntologyProperty(Constants.superClass, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof DataPropertyDomain){
			DataPropertyDomain t = (DataPropertyDomain)m;
			if (t.getDataProperty()!=null) {
				DataProperty ot = t.getDataProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.dataProperty, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getDomain()!=null) {
				Description ot = t.getDomain();		
				tList.clear();
				tList=DescriptionProperties.getDescriptionProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN=DescriptionProperties.getTURN(tList);
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=DescriptionProperties.getDescriptionConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,70, null); 
				OntologyProperty prop = new OntologyProperty(Constants.domain, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof DataPropertyRange){
			DataPropertyRange t = (DataPropertyRange)m;
			if (t.getDataProperty()!=null) {
				DataProperty ot = t.getDataProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.dataProperty, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getRange()!=null) {
				Datatype ot = t.getRange();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.range, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof DisjointDataProperties){
			DisjointDataProperties t = (DisjointDataProperties)m;
			if (t.getDataProperties().size()>0) {
				Iterator it = t.getDataProperties().iterator();
				while(it.hasNext()){
					DataProperty ot = (DataProperty)it.next();		
					tList.clear();
					tList=OWLEntityProperties.getOWLEntityProperties(ot);
					String tURN="";
					OntologyProperty tProp;
					if (ot.getURI()!=null){
						tURN=ot.getURI();
					}else{
						tURN="OWLEntity="+GUID.getGUID().toString();
						tProp = new OntologyProperty(Constants.URI, tURN);
						tList.add(tProp);
					}
					String concept=OWLEntityProperties.getOWLEntityConcept(ot);
					tProp = new OntologyProperty(Constants.name, concept);
					tList.add(tProp);
					IOntology.addConceptToRegistry(1,tList,80, null); 
					OntologyProperty prop = new OntologyProperty(Constants.dataProperties, tURN);
					tProperties.addFirst(prop);
				}
			}

		}
		if (m instanceof EquivalentDataProperties){
			EquivalentDataProperties t = (EquivalentDataProperties)m;
			if (t.getDataProperties().size()>0) {
				Iterator it = t.getDataProperties().iterator();
				while(it.hasNext()){
					DataProperty ot = (DataProperty)it.next();		
					tList.clear();
					tList=OWLEntityProperties.getOWLEntityProperties(ot);
					String tURN="";
					OntologyProperty tProp;
					if (ot.getURI()!=null){
						tURN=ot.getURI();
					}else{
						tURN="OWLEntity="+GUID.getGUID().toString();
						tProp = new OntologyProperty(Constants.URI, tURN);
						tList.add(tProp);
					}
					String concept=OWLEntityProperties.getOWLEntityConcept(ot);
					tProp = new OntologyProperty(Constants.name, concept);
					tList.add(tProp);
					IOntology.addConceptToRegistry(1,tList,80, null); 
					OntologyProperty prop = new OntologyProperty(Constants.dataProperties, tURN);
					tProperties.addFirst(prop);
				}
			}

		}
		if (m instanceof FunctionalDataProperty){
			FunctionalDataProperty t = (FunctionalDataProperty)m;
			if (t.getDataProperty()!=null) {
				DataProperty ot = t.getDataProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.dataProperty, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof SubDataPropertyOf){
			SubDataPropertyOf t = (SubDataPropertyOf)m;
			if (t.getSubDataProperty()!=null) {
				DataProperty ot = t.getSubDataProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.subDataProperty, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getSuperDataProperty()!=null) {
				DataProperty ot = t.getSuperDataProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.superDataProperty, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof Declaration){
			Declaration t = (Declaration)m;
			if (t.getEntity()!=null) {
				OWLEntity ot = t.getEntity();	
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					if (ot instanceof Description) tURN=DescriptionProperties.getTURN(tList);
					else tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				if (ot instanceof Description)
					IOntology.addConceptToRegistry(1,tList,70, null); 
				else
					IOntology.addConceptToRegistry(1,tList,80, null);
				OntologyProperty prop = new OntologyProperty(Constants.entity, tURN);
				tProperties.addFirst(prop);
			}
			
		}
		if (m instanceof EntityAnnotation){
			EntityAnnotation t = (EntityAnnotation)m;
			if (t.getEntity()!=null) {
				OWLEntity ot = t.getEntity();	
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					if (ot instanceof Description) tURN=DescriptionProperties.getTURN(tList);
					else tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				if (ot instanceof Description)
					IOntology.addConceptToRegistry(1,tList,70, null); 
				else
					IOntology.addConceptToRegistry(1,tList,80, null);
				OntologyProperty prop = new OntologyProperty(Constants.entity, tURN);
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
				Description ot = t.getOWLClass();
				tList.clear();
				tList=DescriptionProperties.getDescriptionProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN=DescriptionProperties.getTURN(tList);
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=DescriptionProperties.getDescriptionConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,70, null); 
				OntologyProperty prop = new OntologyProperty(Constants.OWLClass, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getIndividual()!=null) {
				Individual ot = t.getIndividual();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.individual, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof DataPropertyAssertion){
			DataPropertyAssertion t = (DataPropertyAssertion)m;
			if (t.getDataProperty()!=null) {
				DataProperty ot = t.getDataProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.dataProperty, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getSourceIndividual()!=null) {
				Individual ot = t.getSourceIndividual();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.sourceIndividual, tURN);
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
					Individual ot = (Individual)it.next();		
					tList.clear();
					tList=OWLEntityProperties.getOWLEntityProperties(ot);
					String tURN="";
					OntologyProperty tProp;
					if (ot.getURI()!=null){
						tURN=ot.getURI();
					}else{
						tURN="OWLEntity="+GUID.getGUID().toString();
						tProp = new OntologyProperty(Constants.URI, tURN);
						tList.add(tProp);
					}
					String concept=OWLEntityProperties.getOWLEntityConcept(ot);
					tProp = new OntologyProperty(Constants.name, concept);
					tList.add(tProp);
					IOntology.addConceptToRegistry(1,tList,80, null); 
					OntologyProperty prop = new OntologyProperty(Constants.differentIndividuals, tURN);
					tProperties.addFirst(prop);
				}
			}

		}
		if (m instanceof NegativeDataPropertyAssertion){
			NegativeDataPropertyAssertion t = (NegativeDataPropertyAssertion)m;
			if (t.getDataProperty()!=null) {
				DataProperty ot = t.getDataProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.dataProperty, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getSourceIndividual()!=null) {
				Individual ot = t.getSourceIndividual();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.sourceIndividual, tURN);
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
				ObjectProperty ot = t.getObjectProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getSourceIndividual()!=null) {
				Individual ot = t.getSourceIndividual();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.sourceIndividual, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getTargetIndividual()!=null) {
				Individual ot = t.getTargetIndividual();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.targetIndividual, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof ObjectPropertyAssertion){
			ObjectPropertyAssertion t = (ObjectPropertyAssertion)m;
			if (t.getObjectProperty()!=null) {
				ObjectProperty ot = t.getObjectProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getSourceIndividual()!=null) {
				Individual ot = t.getSourceIndividual();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.sourceIndividual, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getTargetIndividual()!=null) {
				Individual ot = t.getTargetIndividual();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.targetIndividual, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof SameIndividual){
			SameIndividual t = (SameIndividual)m;
			if (t.getSameIndividuals().size()>0) {
				Iterator it = t.getSameIndividuals().iterator();
				while(it.hasNext()){
					Individual ot = (Individual)it.next();		
					tList.clear();
					tList=OWLEntityProperties.getOWLEntityProperties(ot);
					String tURN="";
					OntologyProperty tProp;
					if (ot.getURI()!=null){
						tURN=ot.getURI();
					}else{
						tURN="OWLEntity="+GUID.getGUID().toString();
						tProp = new OntologyProperty(Constants.URI, tURN);
						tList.add(tProp);
					}
					String concept=OWLEntityProperties.getOWLEntityConcept(ot);
					tProp = new OntologyProperty(Constants.name, concept);
					tList.add(tProp);
					IOntology.addConceptToRegistry(1,tList,80, null); 
					OntologyProperty prop = new OntologyProperty(Constants.sameIndividuals, tURN);
					tProperties.addFirst(prop);
				}
			}

		}
		if (m instanceof AsymmetricObjectProperty){
			AsymmetricObjectProperty t = (AsymmetricObjectProperty)m;
			if (t.getObjectProperty()!=null) {
				ObjectProperty ot = t.getObjectProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof DisjointObjectProperties){
			DisjointObjectProperties t = (DisjointObjectProperties)m;
			if (t.getDisjointObjectProperties().size()>0) {
				Iterator it = t.getDisjointObjectProperties().iterator();
				while(it.hasNext()){
					ObjectProperty ot = (ObjectProperty)it.next();		
					tList.clear();
					tList=OWLEntityProperties.getOWLEntityProperties(ot);
					String tURN="";
					OntologyProperty tProp;
					if (ot.getURI()!=null){
						tURN=ot.getURI();
					}else{
						tURN="OWLEntity="+GUID.getGUID().toString();
						tProp = new OntologyProperty(Constants.URI, tURN);
						tList.add(tProp);
					}
					String concept=OWLEntityProperties.getOWLEntityConcept(ot);
					tProp = new OntologyProperty(Constants.name, concept);
					tList.add(tProp);
					IOntology.addConceptToRegistry(1,tList,80, null); 
					OntologyProperty prop = new OntologyProperty(Constants.disjointObjectProperties, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m instanceof EquivalentObjectProperties){
			EquivalentObjectProperties t = (EquivalentObjectProperties)m;
			if (t.getEquivalentObjectProperties().size()>0) {
				Iterator it = t.getEquivalentObjectProperties().iterator();
				while(it.hasNext()){
					ObjectProperty ot = (ObjectProperty)it.next();		
					tList.clear();
					tList=OWLEntityProperties.getOWLEntityProperties(ot);
					String tURN="";
					OntologyProperty tProp;
					if (ot.getURI()!=null){
						tURN=ot.getURI();
					}else{
						tURN="OWLEntity="+GUID.getGUID().toString();
						tProp = new OntologyProperty(Constants.URI, tURN);
						tList.add(tProp);
					}
					String concept=OWLEntityProperties.getOWLEntityConcept(ot);
					tProp = new OntologyProperty(Constants.name, concept);
					tList.add(tProp);
					IOntology.addConceptToRegistry(1,tList,80, null); 
					OntologyProperty prop = new OntologyProperty(Constants.equivalentObjectProperties, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m instanceof FunctionalObjectProperty){
			FunctionalObjectProperty t = (FunctionalObjectProperty)m;
			if (t.getObjectProperty()!=null) {
				ObjectProperty ot = t.getObjectProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof InverseFunctionalObjectProperty){
			InverseFunctionalObjectProperty t = (InverseFunctionalObjectProperty)m;
			if (t.getObjectProperty()!=null) {
				ObjectProperty ot = t.getObjectProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof InverseObjectProperties){
			InverseObjectProperties t = (InverseObjectProperties)m;
			if (t.getInverseObjectProperties().size()>0) {
				Iterator it = t.getInverseObjectProperties().iterator();
				while(it.hasNext()){
					ObjectProperty ot = (ObjectProperty)it.next();		
					tList.clear();
					tList=OWLEntityProperties.getOWLEntityProperties(ot);
					String tURN="";
					OntologyProperty tProp;
					if (ot.getURI()!=null){
						tURN=ot.getURI();
					}else{
						tURN="OWLEntity="+GUID.getGUID().toString();
						tProp = new OntologyProperty(Constants.URI, tURN);
						tList.add(tProp);
					}
					String concept=OWLEntityProperties.getOWLEntityConcept(ot);
					tProp = new OntologyProperty(Constants.name, concept);
					tList.add(tProp);
					IOntology.addConceptToRegistry(1,tList,80, null); 
					OntologyProperty prop = new OntologyProperty(Constants.inverseObjectProperties, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m instanceof IrreflexiveObjectProperty){
			IrreflexiveObjectProperty t = (IrreflexiveObjectProperty)m;
			if (t.getObjectProperty()!=null) {
				ObjectProperty ot = t.getObjectProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof ObjectPropertyDomain){
			ObjectPropertyDomain t = (ObjectPropertyDomain)m;
			if (t.getObjectProperty()!=null) {
				ObjectProperty ot = t.getObjectProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getDomain()!=null) {
				Description ot = t.getDomain();	
				tList.clear();
				tList=DescriptionProperties.getDescriptionProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN=DescriptionProperties.getTURN(tList);
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=DescriptionProperties.getDescriptionConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,70, null); 
				OntologyProperty prop = new OntologyProperty(Constants.domain, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof ObjectPropertyRange){
			ObjectPropertyRange t = (ObjectPropertyRange)m;
			if (t.getObjectProperty()!=null) {
				ObjectProperty ot = t.getObjectProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getRange()!=null) {
				Description ot = t.getRange();	
				tList.clear();
				tList=DescriptionProperties.getDescriptionProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN=DescriptionProperties.getTURN(tList);
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=DescriptionProperties.getDescriptionConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,70, null); 
				OntologyProperty prop = new OntologyProperty(Constants.range, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof ReflexiveObjectProperty){
			ReflexiveObjectProperty t = (ReflexiveObjectProperty)m;
			if (t.getObjectProperty()!=null) {
				ObjectProperty ot = t.getObjectProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof SymmetricObjectProperty){
			SymmetricObjectProperty t = (SymmetricObjectProperty)m;
			if (t.getObjectProperty()!=null) {
				ObjectProperty ot = t.getObjectProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof TransitiveObjectProperty){
			TransitiveObjectProperty t = (TransitiveObjectProperty)m;
			if (t.getObjectProperty()!=null) {
				ObjectProperty ot = t.getObjectProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof SubObjectPropertyOf){
			SubObjectPropertyOf t = (SubObjectPropertyOf)m;
			if (t.getSuperObjectProperty()!=null) {
				ObjectProperty ot = t.getSuperObjectProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.superObjectProperty, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getSubObjectProperties().size()>0) {
				Iterator it = t.getSubObjectProperties().iterator();
				while(it.hasNext()){
					ObjectProperty ot = (ObjectProperty)it.next();		
					tList.clear();
					tList=OWLEntityProperties.getOWLEntityProperties(ot);
					String tURN="";
					OntologyProperty tProp;
					if (ot.getURI()!=null){
						tURN=ot.getURI();
					}else{
						tURN="OWLEntity="+GUID.getGUID().toString();
						tProp = new OntologyProperty(Constants.URI, tURN);
						tList.add(tProp);
					}
					String concept=OWLEntityProperties.getOWLEntityConcept(ot);
					tProp = new OntologyProperty(Constants.name, concept);
					tList.add(tProp);
					IOntology.addConceptToRegistry(1,tList,80, null); 
					OntologyProperty prop = new OntologyProperty(Constants.subObjectProperties, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		
		return tProperties;
	}
}