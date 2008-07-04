package org.neontoolkit.changelogging.core.owl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.core.OMVPerson;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLClassChange.DisjointClassChange;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLClassChange.DisjointUnionChange;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLClassChange.EquivalentClassChange;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLClassChange.DisjointClassChange.AddDisjointClass;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLClassChange.DisjointClassChange.RemoveDisjointClass;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLClassChange.DisjointUnionChange.AddDisjointUnion;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLClassChange.DisjointUnionChange.RemoveDisjointUnion;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLClassChange.EquivalentClassChange.AddEquivalentClass;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLClassChange.EquivalentClassChange.RemoveEquivalentClass;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.DataPropertyDomainChange.AddDataPropertyDomain;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.DataPropertyDomainChange.RemoveDataPropertyDomain;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.DataPropertyRangeChange.AddDataPropertyRange;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.DataPropertyRangeChange.RemoveDataPropertyRange;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.EquivalentDataPropertyChange.AddEquivalentDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.EquivalentDataPropertyChange.RemoveEquivalentDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.FunctionalDataPropertyChange.AddFunctionalDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.FunctionalDataPropertyChange.RemoveFunctionalDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.SubDataPropertyOfChange.AddSubDataPropertyOf;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.SubDataPropertyOfChange.RemoveSubDataPropertyOf;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange.DifferentIndividualChange.AddDifferentIndividual;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange.DifferentIndividualChange.RemoveDifferentIndividual;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange.SameIndividualChange.AddSameIndividual;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange.SameIndividualChange.RemoveSameIndividual;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.EquivalentObjectPropertyChange.AddEquivalentObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.EquivalentObjectPropertyChange.RemoveEquivalentObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.FunctionalObjectPropertyChange.AddFunctionalObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.FunctionalObjectPropertyChange.RemoveFunctionalObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.InverseFunctionalObjectPropertyChange.AddInverseFunctionalObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.InverseFunctionalObjectPropertyChange.RemoveInverseFunctionalObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.InverseObjectPropertyChange.AddInverseObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.InverseObjectPropertyChange.RemoveInverseObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.ObjectPropertyDomainChange.AddObjectPropertyDomain;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.ObjectPropertyDomainChange.RemoveObjectPropertyDomain;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.ObjectPropertyRangeChange.AddObjectPropertyRange;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.ObjectPropertyRangeChange.RemoveObjectPropertyRange;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.SubObjectPropertyOfChange.AddSubObjectPropertyOf;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.SubObjectPropertyOfChange.RemoveSubObjectPropertyOf;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.SymmetricObjectPropertyChange.AddSymmetricObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.SymmetricObjectPropertyChange.RemoveSymmetricObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.TransitiveObjectPropertyChange.AddTransitiveObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.TransitiveObjectPropertyChange.RemoveTransitiveObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLOntologyChange.AddDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLOntologyChange.AddObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLOntologyChange.RemoveDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLOntologyChange.RemoveObjectProperty;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange.Addition;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange.Removal;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.AnnotationPropertyChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.ClassChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.OntologyChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.AnnotationPropertyChange.CommentChange.AddComment;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.AnnotationPropertyChange.CommentChange.RemoveComment;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.AnnotationPropertyChange.LabelChange.AddLabel;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.AnnotationPropertyChange.LabelChange.RemoveLabel;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.ClassChange.SubClassOfChange.AddSubClassOf;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.ClassChange.SubClassOfChange.RemoveSubClassOf;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.OntologyChange.AddClass;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.OntologyChange.AddIndividual;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.OntologyChange.RemoveClass;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.OntologyChange.RemoveIndividual;
import org.neontoolkit.owlodm.api.OWLEntity;
import org.neontoolkit.owlodm.api.Axiom.Declaration;
import org.neontoolkit.owlodm.api.Axiom.EntityAnnotation;
import org.neontoolkit.owlodm.api.Axiom.ClassAxiom.DisjointClasses;
import org.neontoolkit.owlodm.api.Axiom.ClassAxiom.DisjointUnion;
import org.neontoolkit.owlodm.api.Axiom.ClassAxiom.EquivalentClasses;
import org.neontoolkit.owlodm.api.Axiom.ClassAxiom.SubClassOf;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.DataPropertyDomain;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.DataPropertyRange;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.EquivalentDataProperties;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.FunctionalDataProperty;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.SubDataPropertyOf;
import org.neontoolkit.owlodm.api.Axiom.Fact.ClassAssertion;
import org.neontoolkit.owlodm.api.Axiom.Fact.DifferentIndividuals;
import org.neontoolkit.owlodm.api.Axiom.Fact.SameIndividual;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.EquivalentObjectProperties;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.FunctionalObjectProperty;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.InverseFunctionalObjectProperty;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.InverseObjectProperties;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.ObjectPropertyDomain;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.ObjectPropertyRange;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.SubObjectPropertyOf;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.SymmetricObjectProperty;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.TransitiveObjectProperty;
import org.neontoolkit.owlodm.api.Description.DataAllValuesFrom;
import org.neontoolkit.owlodm.api.Description.DataExactCardinality;
import org.neontoolkit.owlodm.api.Description.DataHasValue;
import org.neontoolkit.owlodm.api.Description.DataMaxCardinality;
import org.neontoolkit.owlodm.api.Description.DataMinCardinality;
import org.neontoolkit.owlodm.api.Description.DataSomeValuesFrom;
import org.neontoolkit.owlodm.api.Description.ObjectAllValuesFrom;
import org.neontoolkit.owlodm.api.Description.ObjectExactCardinality;
import org.neontoolkit.owlodm.api.Description.ObjectHasValue;
import org.neontoolkit.owlodm.api.Description.ObjectMaxCardinality;
import org.neontoolkit.owlodm.api.Description.ObjectMinCardinality;
import org.neontoolkit.owlodm.api.Description.ObjectSomeValuesFrom;
import org.neontoolkit.owlodm.api.OWLEntity.DataProperty;
import org.neontoolkit.owlodm.api.OWLEntity.Datatype;
import org.neontoolkit.owlodm.api.OWLEntity.Individual;
import org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;
import org.neontoolkit.registry.api.Oyster2Connection;
import org.semanticweb.kaon2.api.KAON2Exception;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Namespaces;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.OntologyChangeEvent.ChangeType;
import org.neontoolkit.changelogging.core.Constants;


public class ThreadRunner implements Runnable {
	private ChangeType cType;
	private List<String> args;
	private OMVOntology omvOnto = null;
	private List<OMVAtomicChange> changeList = new ArrayList<OMVAtomicChange>();
	public static Oyster2Connection oyster2Conn = StartRegistry.connection;//null;
	private Ontology changedOntology;
	
	public ThreadRunner(ChangeType cTypeX, List<String> argsX, OMVOntology o, Ontology changedOnto){
		cType=cTypeX;
		args=argsX;
		omvOnto=o;
		changedOntology=changedOnto;
	}
	
		
	public void run() {
		OMVAtomicChange atomicChange = null;
		
		//System.out.println("changeType: "+cType);
		//System.out.println("axiom: "+args.get(0));
		//System.out.println("num of args "+ (args.size()-1));
		
		String date_time = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		
		if(cType.equals(ChangeType.ADD)){
			atomicChange = new Addition();
		}else if(cType.equals(ChangeType.REMOVE)){
			atomicChange = new Removal();
		}
		if(atomicChange == null)	return;
			
		atomicChange.setDate(date_time);
		atomicChange.setAppliedToOntology(omvOnto);
		
		OMVPerson se = new OMVPerson();
		se.setFirstName("System");
		try {
			se.setLastName(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		se.setHasRole(org.neontoolkit.registry.oyster2.Constants.SubjectExpert);
		
		if(args.get(0).equals(Constants.ACTION_CLASS) ){
			Declaration declaration = new Declaration();
			declaration.setEntity(new org.neontoolkit.owlodm.api.Description.OWLClass (args.get(1)));
			atomicChange.setAppliedAxiom(declaration);
			
			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			OntologyChange classEntity = new OntologyChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddClass();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveClass();
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(1));
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		else if (args.get(0).equals(Constants.ACTION_OBPROPERTY)){
			Declaration declaration = new Declaration();
			
			declaration.setEntity(new ObjectProperty(args.get(1)));
			atomicChange.setAppliedAxiom(declaration);
			
			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			OntologyChange classEntity = new OntologyChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddObjectProperty();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveObjectProperty();
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(1));
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		else if (args.get(0).equals(Constants.ACTION_DAPROPERTY)){
			Declaration declaration = new Declaration();
			
			declaration.setEntity(new DataProperty(args.get(1)));
			atomicChange.setAppliedAxiom(declaration);
			
			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			OntologyChange classEntity = new OntologyChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddDataProperty();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveDataProperty();
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(1));
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		else if (args.get(0).equals(Constants.ACTION_ANPROPERTY)){  // SHOULD TAKE CARE OF THIS ONE
			Declaration declaration = new Declaration();
			OWLEntity ot = new OWLEntity();
			ot.setURI(args.get(1));
			
			declaration.setEntity(ot);
			atomicChange.setAppliedAxiom(declaration);
			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			
		}
		else if(args.get(0).equals(Constants.ACTION_CLASS_SUBOF)){
			SubClassOf subC = new SubClassOf();
			if (args.size()<=3){
				subC.setSubClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
				subC.setSuperClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(2)));
				atomicChange.setAppliedAxiom(subC);
			}else{
				if (args.get(2).replace("[", "").equalsIgnoreCase("all")){
					try {
						if (changedOntology.containsEntity(KAON2Manager.factory().dataProperty(args.get(3)), true)){						
							DataAllValuesFrom o = new DataAllValuesFrom();
							o.addDataProperties(new DataProperty(args.get(3)));
							o.setDataRange(new Datatype(args.get(4).replace("]", "")));
							subC.setSubClass(o);
						}else{
							ObjectAllValuesFrom o = new ObjectAllValuesFrom();
							o.setObjectProperty(new ObjectProperty(args.get(3)));
							o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(4).replace("]", "")));
							subC.setSubClass(o);
						}
						subC.setSuperClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
						atomicChange.setAppliedAxiom(subC);
					} catch (KAON2Exception e) {
						e.printStackTrace();
					}
					
				}
				else if (args.get(2).replace("[", "").equalsIgnoreCase("some")){
					try {
						if (changedOntology.containsEntity(KAON2Manager.factory().dataProperty(args.get(3)), true)){
							DataSomeValuesFrom o = new DataSomeValuesFrom();
							o.addDataProperties(new DataProperty(args.get(3)));
							o.setDataRange(new Datatype(args.get(4).replace("]", "")));
							subC.setSubClass(o);
						}else{
							ObjectSomeValuesFrom o = new ObjectSomeValuesFrom();
							o.setObjectProperty(new ObjectProperty(args.get(3)));
							o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(4).replace("]", "")));
							subC.setSubClass(o);
						}
						subC.setSuperClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
						atomicChange.setAppliedAxiom(subC);
					} catch (KAON2Exception e) {
						e.printStackTrace();
					}	
				}
				else if (args.get(2).replace("[", "").equalsIgnoreCase("atLeast")){
					try {
						if (changedOntology.containsEntity(KAON2Manager.factory().dataProperty(args.get(4).replace("]", "")), true)){
							DataMinCardinality o = new DataMinCardinality();
							o.setDataProperty(new DataProperty(args.get(4).replace("]", "")));
							o.setDataRange(new Datatype(args.get(1)));
							o.setCardinality(new Integer(args.get(3)));
							subC.setSubClass(o);
						}else{
							ObjectMinCardinality o = new ObjectMinCardinality();
							o.setObjectProperty(new ObjectProperty(args.get(4).replace("]", "")));
							o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
							o.setCardinality(new Integer(args.get(3)));
							subC.setSubClass(o);
						}
						subC.setSuperClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
						atomicChange.setAppliedAxiom(subC);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (KAON2Exception e) {
						e.printStackTrace();
					}
				}
				else if (args.get(2).replace("[", "").equalsIgnoreCase("atMost")){
					try {
						if (changedOntology.containsEntity(KAON2Manager.factory().dataProperty(args.get(4).replace("]", "")), true)){
							DataMaxCardinality o = new DataMaxCardinality();
							o.setDataProperty(new DataProperty(args.get(4).replace("]", "")));
							o.setDataRange(new Datatype(args.get(1)));
							o.setCardinality(new Integer(args.get(3)));
							subC.setSubClass(o);
						}else{
							ObjectMaxCardinality o = new ObjectMaxCardinality();
							o.setObjectProperty(new ObjectProperty(args.get(4).replace("]", "")));
							o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
							o.setCardinality(new Integer(args.get(3)));
							subC.setSubClass(o);
						}
						subC.setSuperClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
						atomicChange.setAppliedAxiom(subC);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (KAON2Exception e) {
						e.printStackTrace();
					}					
				}
				else if (args.get(2).replace("[", "").equalsIgnoreCase("exactly")){
					try {
						if (changedOntology.containsEntity(KAON2Manager.factory().dataProperty(args.get(4).replace("]", "")), true)){
							DataExactCardinality o = new DataExactCardinality();
							o.setDataProperty(new DataProperty(args.get(4).replace("]", "")));
							o.setDataRange(new Datatype(args.get(1)));
							o.setCardinality(new Integer(args.get(3)));
							subC.setSubClass(o);
						}else{
							ObjectExactCardinality o = new ObjectExactCardinality();
							o.setObjectProperty(new ObjectProperty(args.get(4).replace("]", "")));
							o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
							o.setCardinality(new Integer(args.get(3)));
							subC.setSubClass(o);
						}
						subC.setSuperClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
						atomicChange.setAppliedAxiom(subC);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (KAON2Exception e) {
						e.printStackTrace();
					}
				}
				else if (args.get(2).replace("[", "").equalsIgnoreCase("hasValue")){
					try {
						if (changedOntology.containsEntity(KAON2Manager.factory().dataProperty(args.get(3)), true)){
							DataHasValue o = new DataHasValue();
							o.setDataProperty(new DataProperty(args.get(3)));
							o.setConstant(args.get(4).replace("]", ""));
							subC.setSubClass(o);
						}else{
							ObjectHasValue o = new ObjectHasValue();
							o.setObjectProperty(new ObjectProperty(args.get(3)));
							o.setValue(new Individual(args.get(4).replace("]", "")));
							subC.setSubClass(o);
						}
						subC.setSuperClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
						atomicChange.setAppliedAxiom(subC);
					} catch (KAON2Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			ClassChange classEntity = new ClassChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddSubClassOf();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveSubClassOf();
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(1));
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		else if(args.get(0).equals(Constants.ACTION_CLASS_EQ)){
			EquivalentClasses ec = new EquivalentClasses();
			
			if (args.size()<=3){
				ec.addEquivalentClasses(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
				ec.addEquivalentClasses(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(2)));
				atomicChange.setAppliedAxiom(ec);
			}else{
				if (args.get(2).replace("[", "").equalsIgnoreCase("all")){
					try {
						if (changedOntology.containsEntity(KAON2Manager.factory().dataProperty(args.get(3)), true)){
							DataAllValuesFrom o = new DataAllValuesFrom();
							o.addDataProperties(new DataProperty(args.get(3)));
							o.setDataRange(new Datatype(args.get(4).replace("]", "")));
							ec.addEquivalentClasses(o);
						}else{
							ObjectAllValuesFrom o = new ObjectAllValuesFrom();
							o.setObjectProperty(new ObjectProperty(args.get(3)));
							o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(4).replace("]", "")));
							ec.addEquivalentClasses(o);
						}
						ec.addEquivalentClasses(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
						atomicChange.setAppliedAxiom(ec);
					} catch (KAON2Exception e) {
						e.printStackTrace();
					}
				}
				else if (args.get(2).replace("[", "").equalsIgnoreCase("some")){
					try {
						if (changedOntology.containsEntity(KAON2Manager.factory().dataProperty(args.get(3)), true)){
							DataSomeValuesFrom o = new DataSomeValuesFrom();
							o.addDataProperties(new DataProperty(args.get(3)));
							o.setDataRange(new Datatype(args.get(4).replace("]", "")));
							ec.addEquivalentClasses(o);
						}else{
							ObjectSomeValuesFrom o = new ObjectSomeValuesFrom();
							o.setObjectProperty(new ObjectProperty(args.get(3)));
							o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(4).replace("]", "")));
							ec.addEquivalentClasses(o);
						}
						ec.addEquivalentClasses(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
						atomicChange.setAppliedAxiom(ec);
					} catch (KAON2Exception e) {
						e.printStackTrace();
					}
				}
				else if (args.get(2).replace("[", "").equalsIgnoreCase("atLeast")){
					try {
						if (changedOntology.containsEntity(KAON2Manager.factory().dataProperty(args.get(4).replace("]", "")), true)){
							DataMinCardinality o = new DataMinCardinality();
							o.setDataProperty(new DataProperty(args.get(4).replace("]", "")));
							o.setDataRange(new Datatype(args.get(1)));
							o.setCardinality(new Integer(args.get(3)));
							ec.addEquivalentClasses(o);
						}else{
							ObjectMinCardinality o = new ObjectMinCardinality();
							o.setObjectProperty(new ObjectProperty(args.get(4).replace("]", "")));
							o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
							o.setCardinality(new Integer(args.get(3)));
							ec.addEquivalentClasses(o);
						}
						ec.addEquivalentClasses(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
						atomicChange.setAppliedAxiom(ec);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (KAON2Exception e) {
						e.printStackTrace();
					}
				}
				else if (args.get(2).replace("[", "").equalsIgnoreCase("atMost")){
					try {
						if (changedOntology.containsEntity(KAON2Manager.factory().dataProperty(args.get(4).replace("]", "")), true)){
							DataMaxCardinality o = new DataMaxCardinality();
							o.setDataProperty(new DataProperty(args.get(4).replace("]", "")));
							o.setDataRange(new Datatype(args.get(1)));
							o.setCardinality(new Integer(args.get(3)));
							ec.addEquivalentClasses(o);
						}else{
							ObjectMaxCardinality o = new ObjectMaxCardinality();
							o.setObjectProperty(new ObjectProperty(args.get(4).replace("]", "")));
							o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
							o.setCardinality(new Integer(args.get(3)));
							ec.addEquivalentClasses(o);
						}
						ec.addEquivalentClasses(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
						atomicChange.setAppliedAxiom(ec);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (KAON2Exception e) {
						e.printStackTrace();
					}
				}
				else if (args.get(2).replace("[", "").equalsIgnoreCase("exactly")){
					try {
						if (changedOntology.containsEntity(KAON2Manager.factory().dataProperty(args.get(4).replace("]", "")), true)){
							DataExactCardinality o = new DataExactCardinality();
							o.setDataProperty(new DataProperty(args.get(4).replace("]", "")));
							o.setDataRange(new Datatype(args.get(1)));
							o.setCardinality(new Integer(args.get(3)));
							ec.addEquivalentClasses(o);
						}else{
							ObjectExactCardinality o = new ObjectExactCardinality();
							o.setObjectProperty(new ObjectProperty(args.get(4).replace("]", "")));
							o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
							o.setCardinality(new Integer(args.get(3)));
							ec.addEquivalentClasses(o);
						}
						ec.addEquivalentClasses(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
						atomicChange.setAppliedAxiom(ec);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (KAON2Exception e) {
						e.printStackTrace();
					}
				}
				else if (args.get(2).replace("[", "").equalsIgnoreCase("hasValue")){
					try {
						if (changedOntology.containsEntity(KAON2Manager.factory().dataProperty(args.get(3)), true)){
							DataHasValue o = new DataHasValue();
							o.setDataProperty(new DataProperty(args.get(3)));
							o.setConstant(args.get(4).replace("]", ""));
							ec.addEquivalentClasses(o);
						}else{
							ObjectHasValue o = new ObjectHasValue();
							o.setObjectProperty(new ObjectProperty(args.get(3)));
							o.setValue(new Individual(args.get(4).replace("]", "")));
							ec.addEquivalentClasses(o);
						}
						ec.addEquivalentClasses(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
						atomicChange.setAppliedAxiom(ec);
					} catch (KAON2Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			EquivalentClassChange classEntity = new EquivalentClassChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddEquivalentClass();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveEquivalentClass();
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(1));
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		else if(args.get(0).equals(Constants.ACTION_CLASS_DISJ)){
			DisjointClasses ec = new DisjointClasses();
			ec.addDisjointClasses(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
			ec.addDisjointClasses(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(2)));
			atomicChange.setAppliedAxiom(ec);
			
			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			DisjointClassChange classEntity = new DisjointClassChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddDisjointClass();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveDisjointClass();
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(1));
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		else if(args.get(0).equals(Constants.ACTION_CLASS_DISJUN)){ //HOW TO USE IT???
			DisjointUnion ec = new DisjointUnion();
			ec.setUnionClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
			ec.addDisjointClasses(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(2)));
			atomicChange.setAppliedAxiom(ec);
			
			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			DisjointUnionChange classEntity = new DisjointUnionChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddDisjointUnion();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveDisjointUnion();
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(1));
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		else if(args.get(0).equals(Constants.ACTION_CLASS_ANN)){ 
			// 1 = annotationProperty, 2=annotated entity, 3= value
			EntityAnnotation ec = new EntityAnnotation();
			OWLEntity ot = new OWLEntity();
			ot.setURI(args.get(1));
			ec.setEntity(ot);
			ec.addEntityAnnotation(args.get(3));
			atomicChange.setAppliedAxiom(ec);
			
			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			
			AnnotationPropertyChange classEntity = new AnnotationPropertyChange();
			if (atomicChange instanceof Addition){
				String t=Namespaces.guessLocalName(args.get(1));
				if (t.equalsIgnoreCase("comment")) classEntity = new AddComment();
				else if (t.equalsIgnoreCase("label")) classEntity = new AddLabel();
				else return;
			}else if (atomicChange instanceof Removal){
				String t=Namespaces.guessLocalName(args.get(1));
				if (t.equalsIgnoreCase("comment")) classEntity = new RemoveComment();
				else if (t.equalsIgnoreCase("label")) classEntity = new RemoveLabel();
				else return;
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(2));
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		
		else if(args.get(0).equals(Constants.ACTION_OBPROPERTY_DOMAIN)){
			ObjectPropertyDomain oPD = new ObjectPropertyDomain();
			oPD.setObjectProperty(new ObjectProperty(args.get(1)));
			oPD.setDomain(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(2)));
			atomicChange.setAppliedAxiom(oPD);

			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			OWLObjectPropertyChange classEntity = new OWLObjectPropertyChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddObjectPropertyDomain();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveObjectPropertyDomain();
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(1));
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
			
			
		}else if(args.get(0).equals(Constants.ACTION_OBPROPERTY_RANGE)){
			ObjectPropertyRange oPR = new ObjectPropertyRange();
			oPR.setObjectProperty(new ObjectProperty(args.get(1)));
			oPR.setRange(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(2)));
			atomicChange.setAppliedAxiom(oPR);
			
			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			OWLObjectPropertyChange classEntity = new OWLObjectPropertyChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddObjectPropertyRange();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveObjectPropertyRange();
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(1));
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
			
		}else if(args.get(0).equals(Constants.ACTION_OBPROPERTY_SUBOF)){
			SubObjectPropertyOf subOP = new SubObjectPropertyOf();
			subOP.addSubObjectProperties(new ObjectProperty(args.get(1)));
			subOP.setSuperObjectProperty(new ObjectProperty(args.get(2)));
			atomicChange.setAppliedAxiom(subOP);

			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			OWLObjectPropertyChange classEntity = new OWLObjectPropertyChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddSubObjectPropertyOf();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveSubObjectPropertyOf();
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(1));
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		else if(args.get(0).equals(Constants.ACTION_OBPROPERTY_EQ)){
			EquivalentObjectProperties oPE = new EquivalentObjectProperties();
			oPE.addEquivalentObjectProperties(new ObjectProperty(args.get(1)));
			oPE.addEquivalentObjectProperties(new ObjectProperty(args.get(2)));
			atomicChange.setAppliedAxiom(oPE);

			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			OWLObjectPropertyChange classEntity = new OWLObjectPropertyChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddEquivalentObjectProperty();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveEquivalentObjectProperty();
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(2));  //THIS IS PROBABLY ERROR (DIFFERENT FROM THE REST)
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		else if(args.get(0).equals(Constants.ACTION_OBPROPERTY_FUNC)){
			FunctionalObjectProperty oPE = new FunctionalObjectProperty();
			oPE.setObjectProperty(new ObjectProperty(args.get(1)));
			atomicChange.setAppliedAxiom(oPE);

			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			OWLObjectPropertyChange classEntity = new OWLObjectPropertyChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddFunctionalObjectProperty();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveFunctionalObjectProperty();
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(1)); 
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		else if(args.get(0).equals(Constants.ACTION_OBPROPERTY_INVFUNC)){
			InverseFunctionalObjectProperty oPE = new InverseFunctionalObjectProperty();
			oPE.setObjectProperty(new ObjectProperty(args.get(1)));
			atomicChange.setAppliedAxiom(oPE);

			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			OWLObjectPropertyChange classEntity = new OWLObjectPropertyChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddInverseFunctionalObjectProperty();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveInverseFunctionalObjectProperty();
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(1)); 
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		else if(args.get(0).equals(Constants.ACTION_OBPROPERTY_TRA)){
			TransitiveObjectProperty oPE = new TransitiveObjectProperty();
			oPE.setObjectProperty(new ObjectProperty(args.get(1)));
			atomicChange.setAppliedAxiom(oPE);

			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			OWLObjectPropertyChange classEntity = new OWLObjectPropertyChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddTransitiveObjectProperty();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveTransitiveObjectProperty();
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(1)); 
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		else if(args.get(0).equals(Constants.ACTION_OBPROPERTY_SYM)){
			SymmetricObjectProperty oPE = new SymmetricObjectProperty();
			oPE.setObjectProperty(new ObjectProperty(args.get(1)));
			atomicChange.setAppliedAxiom(oPE);

			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			OWLObjectPropertyChange classEntity = new OWLObjectPropertyChange();
			if (atomicChange instanceof Addition){
				
				classEntity = new AddSymmetricObjectProperty();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveSymmetricObjectProperty();
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(1)); 
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		else if(args.get(0).equals(Constants.ACTION_OBPROPERTY_INV)){
			InverseObjectProperties oPE = new InverseObjectProperties();
			oPE.addInverseObjectProperties(new ObjectProperty(args.get(1)));
			oPE.addInverseObjectProperties(new ObjectProperty(args.get(2)));
			atomicChange.setAppliedAxiom(oPE);

			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			OWLObjectPropertyChange classEntity = new OWLObjectPropertyChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddInverseObjectProperty();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveInverseObjectProperty();
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(2));  //THIS IS PROBABLY ERROR (DIFFERENT FROM THE REST)
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		else if(args.get(0).equals(Constants.ACTION_OBPROPERTY_ANN)){ 
			// 1 = annotationProperty, 2=annotated entity, 3= value
			EntityAnnotation ec = new EntityAnnotation();
			OWLEntity ot = new OWLEntity();
			ot.setURI(args.get(1));
			ec.setEntity(ot);
			ec.addEntityAnnotation(args.get(3));
			atomicChange.setAppliedAxiom(ec);
			
			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			
			AnnotationPropertyChange classEntity = new AnnotationPropertyChange();
			if (atomicChange instanceof Addition){
				String t=Namespaces.guessLocalName(args.get(1));
				if (t.equalsIgnoreCase("comment")) classEntity = new AddComment();
				else if (t.equalsIgnoreCase("label")) classEntity = new AddLabel();
				else return;
			}else if (atomicChange instanceof Removal){
				String t=Namespaces.guessLocalName(args.get(1));
				if (t.equalsIgnoreCase("comment")) classEntity = new RemoveComment();
				else if (t.equalsIgnoreCase("label")) classEntity = new RemoveLabel();
				else return;
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(2));
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		
		else if(args.get(0).equals(Constants.ACTION_DAPROPERTY_DOMAIN)){
			DataPropertyDomain dPD = new DataPropertyDomain();
			dPD.setDataProperty(new DataProperty(args.get(1)));
			dPD.setDomain(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(2)));
			atomicChange.setAppliedAxiom(dPD);

			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			OWLDataPropertyChange classEntity = new OWLDataPropertyChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddDataPropertyDomain();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveDataPropertyDomain();
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(1));
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
			
		}else if(args.get(0).equals(Constants.ACTION_DAPROPERTY_RANGE)){
			DataPropertyRange dPR = new DataPropertyRange();
			dPR.setDataProperty(new DataProperty(args.get(1)));
			dPR.setRange(new Datatype(args.get(2)));
			atomicChange.setAppliedAxiom(dPR);

			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			OWLDataPropertyChange classEntity = new OWLDataPropertyChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddDataPropertyRange();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveDataPropertyRange();
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(1));
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}else if(args.get(0).equals(Constants.ACTION_DAPROPERTY_EQ)){
			EquivalentDataProperties dPE = new EquivalentDataProperties();
			dPE.addDataProperties(new DataProperty(args.get(1)));
			dPE.addDataProperties(new DataProperty(args.get(2)));
			atomicChange.setAppliedAxiom(dPE);

			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			OWLDataPropertyChange classEntity = new OWLDataPropertyChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddEquivalentDataProperty();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveEquivalentDataProperty();
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(2)); //THIS IS PROBABLY ERROR (DIFFERENT FROM THE REST)
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}else if(args.get(0).equals(Constants.ACTION_DAPROPERTY_SUBOF)){
			SubDataPropertyOf subDP = new SubDataPropertyOf();
			subDP.setSubDataProperty(new DataProperty(args.get(1)));
			subDP.setSuperDataProperty(new DataProperty(args.get(2)));
			atomicChange.setAppliedAxiom(subDP);
			
			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			OWLDataPropertyChange classEntity = new OWLDataPropertyChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddSubDataPropertyOf();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveSubDataPropertyOf();
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(1));
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		else if(args.get(0).equals(Constants.ACTION_DAPROPERTY_FUNC)){
			FunctionalDataProperty oPE = new FunctionalDataProperty();
			oPE.setDataProperty(new DataProperty(args.get(1)));
			atomicChange.setAppliedAxiom(oPE);

			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			OWLDataPropertyChange classEntity = new OWLDataPropertyChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddFunctionalDataProperty();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveFunctionalDataProperty();
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(1)); 
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		else if(args.get(0).equals(Constants.ACTION_DAPROPERTY_ANN)){ 
			// 1 = annotationProperty, 2=annotated entity, 3= value
			EntityAnnotation ec = new EntityAnnotation();
			OWLEntity ot = new OWLEntity();
			ot.setURI(args.get(1));
			ec.setEntity(ot);
			ec.addEntityAnnotation(args.get(3));
			atomicChange.setAppliedAxiom(ec);
			
			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			
			AnnotationPropertyChange classEntity = new AnnotationPropertyChange();
			if (atomicChange instanceof Addition){
				String t=Namespaces.guessLocalName(args.get(1));
				if (t.equalsIgnoreCase("comment")) classEntity = new AddComment();
				else if (t.equalsIgnoreCase("label")) classEntity = new AddLabel();
				else return;
			}else if (atomicChange instanceof Removal){
				String t=Namespaces.guessLocalName(args.get(1));
				if (t.equalsIgnoreCase("comment")) classEntity = new RemoveComment();
				else if (t.equalsIgnoreCase("label")) classEntity = new RemoveLabel();
				else return;
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(2));
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		
		else if (args.get(0).equals(Constants.ACTION_INDIVIDUAL)){
			
			Declaration declaration = new Declaration();
			declaration.setEntity(new Individual(args.get(1)));
			atomicChange.setAppliedAxiom(declaration);
			
			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
		}
		else if(args.get(0).equals(Constants.ACTION_INDIVIDUAL_MEM)){
			ClassAssertion subDP = new ClassAssertion();
			subDP.setIndividual(new Individual(args.get(2)));
			subDP.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
			atomicChange.setAppliedAxiom(subDP);
			
			changeList.add(atomicChange);
			
			OntologyChange classEntity = new OntologyChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddIndividual();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveIndividual();
			}
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId()); //WE ASSUME LAST CHANGE WAS THE ADDINDIVIDUAL
			oyster2Conn.register(atomicChange);
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(1));
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		else if(args.get(0).equals(Constants.ACTION_INDIVIDUAL_SAM)){
			SameIndividual subDP = new SameIndividual();
			subDP.addSameIndividuals(new Individual(args.get(2)));
			subDP.addSameIndividuals(new Individual(args.get(1)));
			atomicChange.setAppliedAxiom(subDP);
			
			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			
			OWLIndividualChange classEntity = new OWLIndividualChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddSameIndividual();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveSameIndividual();
			}			
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(2));
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		else if(args.get(0).equals(Constants.ACTION_INDIVIDUAL_DIF)){
			DifferentIndividuals subDP = new DifferentIndividuals();
			subDP.addDifferentIndividuals(new Individual(args.get(2)));
			subDP.addDifferentIndividuals(new Individual(args.get(1)));
			atomicChange.setAppliedAxiom(subDP);
			
			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			
			OWLIndividualChange classEntity = new OWLIndividualChange();
			if (atomicChange instanceof Addition){
				classEntity = new AddDifferentIndividual();
			}else if (atomicChange instanceof Removal){
				classEntity = new RemoveDifferentIndividual();
			}			
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(2));
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		else if(args.get(0).equals(Constants.ACTION_INDIVIDUAL_ANN)){ 
			// 1 = annotationProperty, 2=annotated entity, 3= value
			EntityAnnotation ec = new EntityAnnotation();
			OWLEntity ot = new OWLEntity();
			ot.setURI(args.get(1));
			ec.setEntity(ot);
			ec.addEntityAnnotation(args.get(3));
			atomicChange.setAppliedAxiom(ec);
			
			changeList.add(atomicChange);
			oyster2Conn.register(atomicChange);
			
			AnnotationPropertyChange classEntity = new AnnotationPropertyChange();
			if (atomicChange instanceof Addition){
				String t=Namespaces.guessLocalName(args.get(1));
				if (t.equalsIgnoreCase("comment")) classEntity = new AddComment();
				else if (t.equalsIgnoreCase("label")) classEntity = new AddLabel();
				else return;
			}else if (atomicChange instanceof Removal){
				String t=Namespaces.guessLocalName(args.get(1));
				if (t.equalsIgnoreCase("comment")) classEntity = new RemoveComment();
				else if (t.equalsIgnoreCase("label")) classEntity = new RemoveLabel();
				else return;
			}
			classEntity.setAppliedToOntology(omvOnto);
			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
			classEntity.addHasRelatedEntity(args.get(2));
			classEntity.addHasAuthor(se);
			classEntity.setDate(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
			oyster2Conn.register(classEntity);
		}
		else{
			return;
		}
//		String date = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) + 
//						String.valueOf(Calendar.getInstance().get(Calendar.MONTH)) + 
//						String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
//		String time = String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) + 
//						String.valueOf(Calendar.getInstance().get(Calendar.MINUTE)) + 
//						String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
		
		
		
		//oyster2Conn.register(atomicChange);
	}
	
	
}

//private static List<OMVOntology> omvOntoList = new ArrayList<OMVOntology>();


//if(oyster2Conn != null){
//	omvOnto = new OMVOntology();
//	omvOnto.setURI(monitoredOnto.getOntologyURI());
//	omvOnto.setResourceLocator("");//monitoredOnto.getPhysicalURI());
//	oyster2Conn.startTracking(omvOnto);
//}
//omvOnto.addName(Namespaces.guessLocalName(monitoredOnto.getOntologyURI()));
//omvOntoList.add(omvOnto);


/*
if(args.get(0).equals(Constants.ACTION_CLASS)){
	if(args.get(2).equals(Constants.OWL_THINGS)){ // create or remove an OWLClass
		Declaration declaration = new Declaration();
		declaration.setEntity(args.get(1));
		atomicChange.setAppliedAxiom(declaration);
		changeList.add(atomicChange);
	}else{										  // create or remove a subClassOf relation
		SubClassOf sco = new SubClassOf();
		sco.setSubClass(args.get(1));
		sco.setSuperClass(args.get(2));
		atomicChange.setAppliedAxiom(sco);
		changeList.add(atomicChange);
	}			
}
*/