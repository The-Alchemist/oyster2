package org.neontoolkit.changelogging.core.owl;

//import java.net.InetAddress;
//import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;
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
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.DisjointDataPropertyChange.AddDisjointDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.DisjointDataPropertyChange.RemoveDisjointDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.EquivalentDataPropertyChange.AddEquivalentDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.EquivalentDataPropertyChange.RemoveEquivalentDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.FunctionalDataPropertyChange.AddFunctionalDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.FunctionalDataPropertyChange.RemoveFunctionalDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.SubDataPropertyOfChange.AddSubDataPropertyOf;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.SubDataPropertyOfChange.RemoveSubDataPropertyOf;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange.DifferentIndividualChange.AddDifferentIndividual;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange.DifferentIndividualChange.RemoveDifferentIndividual;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange.IndividualDataPropertyChange.AddIndividualDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange.IndividualDataPropertyChange.RemoveIndividualDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange.IndividualObjectPropertyChange.AddInvidivualObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange.IndividualObjectPropertyChange.RemoveInvidualObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange.SameIndividualChange.AddSameIndividual;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange.SameIndividualChange.RemoveSameIndividual;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.AsymmetricObjectPropertyChange.AddAsymmetricObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.AsymmetricObjectPropertyChange.RemoveAsymmetricObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.DisjointObjectPropertyChange.AddDisjointObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.DisjointObjectPropertyChange.RemoveDisjointObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.EquivalentObjectPropertyChange.AddEquivalentObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.EquivalentObjectPropertyChange.RemoveEquivalentObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.FunctionalObjectPropertyChange.AddFunctionalObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.FunctionalObjectPropertyChange.RemoveFunctionalObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.InverseFunctionalObjectPropertyChange.AddInverseFunctionalObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.InverseFunctionalObjectPropertyChange.RemoveInverseFunctionalObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.InverseObjectPropertyChange.AddInverseObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.InverseObjectPropertyChange.RemoveInverseObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.IrreflexiveObjectPropertyChange.AddIrreflexiveObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.IrreflexiveObjectPropertyChange.RemoveIrreflexiveObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.ObjectPropertyDomainChange.AddObjectPropertyDomain;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.ObjectPropertyDomainChange.RemoveObjectPropertyDomain;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.ObjectPropertyRangeChange.AddObjectPropertyRange;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.ObjectPropertyRangeChange.RemoveObjectPropertyRange;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.ReflexiveObjectPropertyChange.AddReflexiveObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.ReflexiveObjectPropertyChange.RemoveReflexiveObjectProperty;
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
import org.neontoolkit.omv.api.extensions.change.OMVChange;
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
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.OntologyChange.AddProperty.AddAnnotationProperty;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.OntologyChange.RemoveProperty.RemoveAnnotationProperty;
import org.neontoolkit.owlodm.api.OWLEntity;
import org.neontoolkit.owlodm.api.Axiom.Declaration;
import org.neontoolkit.owlodm.api.Axiom.EntityAnnotation;
import org.neontoolkit.owlodm.api.Axiom.ClassAxiom.DisjointClasses;
import org.neontoolkit.owlodm.api.Axiom.ClassAxiom.DisjointUnion;
import org.neontoolkit.owlodm.api.Axiom.ClassAxiom.EquivalentClasses;
import org.neontoolkit.owlodm.api.Axiom.ClassAxiom.SubClassOf;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.DataPropertyDomain;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.DataPropertyRange;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.DisjointDataProperties;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.EquivalentDataProperties;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.FunctionalDataProperty;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.SubDataPropertyOf;
import org.neontoolkit.owlodm.api.Axiom.Fact.ClassAssertion;
import org.neontoolkit.owlodm.api.Axiom.Fact.DataPropertyAssertion;
import org.neontoolkit.owlodm.api.Axiom.Fact.DifferentIndividuals;
import org.neontoolkit.owlodm.api.Axiom.Fact.NegativeDataPropertyAssertion;
import org.neontoolkit.owlodm.api.Axiom.Fact.NegativeObjectPropertyAssertion;
import org.neontoolkit.owlodm.api.Axiom.Fact.ObjectPropertyAssertion;
import org.neontoolkit.owlodm.api.Axiom.Fact.SameIndividual;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.AsymmetricObjectProperty;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.DisjointObjectProperties;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.EquivalentObjectProperties;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.FunctionalObjectProperty;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.InverseFunctionalObjectProperty;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.InverseObjectProperties;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.IrreflexiveObjectProperty;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.ObjectPropertyDomain;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.ObjectPropertyRange;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.ReflexiveObjectProperty;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.SubObjectPropertyOf;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.SymmetricObjectProperty;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.TransitiveObjectProperty;
import org.neontoolkit.owlodm.api.Description.DataAllValuesFrom;
import org.neontoolkit.owlodm.api.Description.DataExactCardinality;
import org.neontoolkit.owlodm.api.Description.DataHasValue;
import org.neontoolkit.owlodm.api.Description.DataMaxCardinality;
import org.neontoolkit.owlodm.api.Description.DataMinCardinality;
import org.neontoolkit.owlodm.api.Description.DataSomeValuesFrom;
import org.neontoolkit.owlodm.api.Description.OWLClass;
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
import org.neontoolkit.changelogging.core.ApplyChangesFromLogToNTK;
import org.neontoolkit.changelogging.core.Constants;

import com.ontoprise.ontostudio.gui.GuiPlugin;


public class ThreadRunner implements Runnable {
	private ChangeType cType;
	private List<String> args;
	private OMVOntology omvOnto = null;
	private List<OMVAtomicChange> changeList = new ArrayList<OMVAtomicChange>();
	public static Oyster2Connection oyster2Conn = StartRegistry.connection;//null;
	private Ontology changedOntology;
	private IPreferenceStore _store = GuiPlugin.getDefault().getPreferenceStore();
	private Shell shell;
	public static String localURI="http://localhost/";
	public static String rdfsLiteral=localURI+"Literal";//www.w3.org/2000/01/rdf-schema#
	public static String owlThing=localURI+"Thing";//http://www.w3.org/2002/07/owl#
	
	public ThreadRunner(ChangeType cTypeX, List<String> argsX, OMVOntology o, Ontology changedOnto, Shell arg){
		cType=cTypeX;
		args=argsX;
		omvOnto=o;
		changedOntology=changedOnto;
		shell=arg;
	}
	
		
	public void run() {
			try{
            	OMVAtomicChange atomicChange = null;
        		
            	try{
            		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            	}catch(Exception e){
            		OWLChangeListener.working--;
            		e.printStackTrace();
            	}
        		
        		String date_time = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime());
        		if(cType.equals(ChangeType.ADD)){
        			atomicChange = new Addition();
        		}else if(cType.equals(ChangeType.REMOVE)){
        			atomicChange = new Removal();
        		}
        		if(atomicChange == null)	return;
        			
        		atomicChange.setDate(date_time);
        		atomicChange.setAppliedToOntology(omvOnto);
        		
        		
        		OMVPerson se = new OMVPerson();
        		try{
        			String role = _store.getString("ROLE"); 
        			String firstname = _store.getString("USER_FIRSTNAME");
        			String lastname = _store.getString("USER_LASTNAME");
        			se.setFirstName(firstname);
        			se.setLastName(lastname);
        			se.setHasRole(role);
        			if (se.getLastName()==null || se.getFirstName()==null ||  se.getLastName().equalsIgnoreCase("") || se.getFirstName().equalsIgnoreCase("")) return;
        		}
        		catch(Exception e){
        			OWLChangeListener.working--;
        			return;
        		}
        		atomicChange.addHasAuthor(se);
        		
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
        			oyster2Conn.register(classEntity);
        		}
        		else if (args.get(0).equals(Constants.ACTION_ANPROPERTY)){  // SHOULD TAKE CARE OF THIS ONE
        			Declaration declaration = new Declaration();
        			OWLEntity ot = new OWLEntity();
        			ot.setURI(args.get(1)+"?Annotation");  //TRICK
        			
        			declaration.setEntity(ot);
        			atomicChange.setAppliedAxiom(declaration);
        			changeList.add(atomicChange);
        			oyster2Conn.register(atomicChange);
        			
        			OntologyChange classEntity = new OntologyChange();
        			if (atomicChange instanceof Addition){
        				classEntity = new AddAnnotationProperty();
        			}else if (atomicChange instanceof Removal){
        				classEntity = new RemoveAnnotationProperty();
        			}
        			classEntity.setAppliedToOntology(omvOnto);
        			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
        			classEntity.addHasRelatedEntity(args.get(1));
        			classEntity.addHasAuthor(se);
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
        			oyster2Conn.register(classEntity);
        		}
        		else if(args.get(0).equals(Constants.ACTION_CLASS_SUBOF)){
        			SubClassOf subC = new SubClassOf();
        			if (args.size()<=3){
        				subC.setSubClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
        				subC.setSuperClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(2)));
        				atomicChange.setAppliedAxiom(subC);
        			}else{
        				if (args.get(2).replace("[", "").equalsIgnoreCase("all") || args.get(2).replace("[", "").equalsIgnoreCase("dataAll")){
        					try {
        						if (changedOntology.containsEntity(KAON2Manager.factory().dataProperty(args.get(3)), true)){						
        							DataAllValuesFrom o = new DataAllValuesFrom();
        							o.addDataProperties(new DataProperty(args.get(3)));
        							o.setDataRange(new Datatype(args.get(4).replace("]", "")));
        							subC.setSuperClass(o);
        						}else{
        							ObjectAllValuesFrom o = new ObjectAllValuesFrom();
        							o.setObjectProperty(new ObjectProperty(args.get(3)));
        							o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(4).replace("]", "")));
        							subC.setSuperClass(o);
        						}
        						subC.setSubClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
        						atomicChange.setAppliedAxiom(subC);
        					} catch (KAON2Exception e) {
        						OWLChangeListener.working--;
        						e.printStackTrace();
        					}
        					
        				}
        				else if (args.get(2).replace("[", "").equalsIgnoreCase("some") || args.get(2).replace("[", "").equalsIgnoreCase("dataSome")){
        					try {
        						if (changedOntology.containsEntity(KAON2Manager.factory().dataProperty(args.get(3)), true)){
        							DataSomeValuesFrom o = new DataSomeValuesFrom();
        							o.addDataProperties(new DataProperty(args.get(3)));
        							o.setDataRange(new Datatype(args.get(4).replace("]", "")));
        							subC.setSuperClass(o);
        						}else{
        							ObjectSomeValuesFrom o = new ObjectSomeValuesFrom();
        							o.setObjectProperty(new ObjectProperty(args.get(3)));
        							o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(4).replace("]", "")));
        							subC.setSuperClass(o);
        						}
        						subC.setSubClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
        						atomicChange.setAppliedAxiom(subC);
        					} catch (KAON2Exception e) {
        						OWLChangeListener.working--;
        						e.printStackTrace();
        					}	
        				}
        				else if (args.get(2).replace("[", "").equalsIgnoreCase("atLeast") || args.get(2).replace("[", "").equalsIgnoreCase("dataAtLeast")){
        					try {
        						if (changedOntology.containsEntity(KAON2Manager.factory().dataProperty(args.get(4).replace("]", "")), true)){
        							DataMinCardinality o = new DataMinCardinality();
        							o.setDataProperty(new DataProperty(args.get(4).replace("]", "")));
        							if (args.size()>5)
        								o.setDataRange(new Datatype(args.get(5).replace("]", "")));
        							else
        								o.setDataRange(new Datatype(rdfsLiteral));//o.setDataRange(new Datatype(args.get(1)));
        							o.setCardinality(new Integer(args.get(3)));
        							subC.setSuperClass(o);
        						}else{
        							ObjectMinCardinality o = new ObjectMinCardinality();
        							o.setObjectProperty(new ObjectProperty(args.get(4).replace("]", "")));
        							if (args.size()>5)
        								o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(5).replace("]", "")));
            						else
            							o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(owlThing));//o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
        							o.setCardinality(new Integer(args.get(3)));
        							subC.setSuperClass(o);
        						}
        						subC.setSubClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
        						atomicChange.setAppliedAxiom(subC);
        					} catch (NumberFormatException e) {
        						OWLChangeListener.working--;
        						e.printStackTrace();
        					} catch (KAON2Exception e) {
        						OWLChangeListener.working--;
        						e.printStackTrace();
        					}
        				}
        				else if (args.get(2).replace("[", "").equalsIgnoreCase("atMost") || args.get(2).replace("[", "").equalsIgnoreCase("dataAtMost")){
        					try {
        						if (changedOntology.containsEntity(KAON2Manager.factory().dataProperty(args.get(4).replace("]", "")), true)){
        							DataMaxCardinality o = new DataMaxCardinality();
        							o.setDataProperty(new DataProperty(args.get(4).replace("]", "")));
        							if (args.size()>5)
        								o.setDataRange(new Datatype(args.get(5).replace("]", "")));
        							else
        								o.setDataRange(new Datatype(rdfsLiteral));//o.setDataRange(new Datatype(args.get(1)));
        							o.setCardinality(new Integer(args.get(3)));
        							subC.setSuperClass(o);
        						}else{
        							ObjectMaxCardinality o = new ObjectMaxCardinality();
        							o.setObjectProperty(new ObjectProperty(args.get(4).replace("]", "")));
        							if (args.size()>5)
        								o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(5).replace("]", "")));
            						else
            							o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(owlThing));//o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
        							o.setCardinality(new Integer(args.get(3)));
        							subC.setSuperClass(o);
        						}
        						subC.setSubClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
        						atomicChange.setAppliedAxiom(subC);
        					} catch (NumberFormatException e) {
        						OWLChangeListener.working--;
        						e.printStackTrace();
        					} catch (KAON2Exception e) {
        						OWLChangeListener.working--;
        						e.printStackTrace();
        					}					
        				}
        				else if (args.get(2).replace("[", "").equalsIgnoreCase("exactly") || args.get(2).replace("[", "").equalsIgnoreCase("dataExactly")){
        					try {
        						if (changedOntology.containsEntity(KAON2Manager.factory().dataProperty(args.get(4).replace("]", "")), true)){
        							DataExactCardinality o = new DataExactCardinality();
        							o.setDataProperty(new DataProperty(args.get(4).replace("]", "")));
        							if (args.size()>5)
        								o.setDataRange(new Datatype(args.get(5).replace("]", "")));
        							else
        								o.setDataRange(new Datatype(rdfsLiteral));//o.setDataRange(new Datatype(args.get(1)));
        							o.setCardinality(new Integer(args.get(3)));
        							subC.setSuperClass(o);
        						}else{
        							ObjectExactCardinality o = new ObjectExactCardinality();
        							o.setObjectProperty(new ObjectProperty(args.get(4).replace("]", "")));
        							if (args.size()>5)
        								o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(5).replace("]", "")));
            						else
            							o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(owlThing));//o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
        							o.setCardinality(new Integer(args.get(3)));
        							subC.setSuperClass(o);
        						}
        						subC.setSubClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
        						atomicChange.setAppliedAxiom(subC);
        					} catch (NumberFormatException e) {
        						OWLChangeListener.working--;
        						e.printStackTrace();
        					} catch (KAON2Exception e) {
        						OWLChangeListener.working--;
        						e.printStackTrace();
        					}
        				}
        				else if (args.get(2).replace("[", "").equalsIgnoreCase("hasValue") || args.get(2).replace("[", "").equalsIgnoreCase("dataHasValue")){
        					try {
        						if (changedOntology.containsEntity(KAON2Manager.factory().dataProperty(args.get(3)), true)){
        							DataHasValue o = new DataHasValue();
        							o.setDataProperty(new DataProperty(args.get(3)));
        							String cleanValue=args.get(4).replace("]", "");
        							if (cleanValue.indexOf("^^<")>0)
        								cleanValue=cleanValue.replace("^^<", "???").substring(0, cleanValue.length()-1);//cleanValue=cleanValue.substring(0, cleanValue.indexOf("^^"));	
        							o.setConstant(localURI+cleanValue);
        							subC.setSuperClass(o);
        						}else{
        							ObjectHasValue o = new ObjectHasValue();
        							o.setObjectProperty(new ObjectProperty(args.get(3)));
        							o.setValue(new Individual(args.get(4).replace("]", "")));
        							subC.setSuperClass(o);
        						}
        						subC.setSubClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
        						atomicChange.setAppliedAxiom(subC);
        					} catch (KAON2Exception e) {
        						OWLChangeListener.working--;
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
        			oyster2Conn.register(classEntity);
        		}
        		else if(args.get(0).equals(Constants.ACTION_CLASS_EQ)){
        			EquivalentClasses ec = new EquivalentClasses();
        			
        			if (args.size()<=3){
        				ec.addEquivalentClasses(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
        				ec.addEquivalentClasses(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(2)));
        				atomicChange.setAppliedAxiom(ec);
        			}else{
        				if (args.get(2).replace("[", "").equalsIgnoreCase("all") || args.get(2).replace("[", "").equalsIgnoreCase("dataAll")){
        					try {
        						ec.addEquivalentClasses(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
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
        						atomicChange.setAppliedAxiom(ec);
        					} catch (KAON2Exception e) {
        						OWLChangeListener.working--;
        						e.printStackTrace();
        					}
        				}
        				else if (args.get(2).replace("[", "").equalsIgnoreCase("some") || args.get(2).replace("[", "").equalsIgnoreCase("dataSome")){
        					try {
        						ec.addEquivalentClasses(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
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
        						atomicChange.setAppliedAxiom(ec);
        					} catch (KAON2Exception e) {
        						OWLChangeListener.working--;
        						e.printStackTrace();
        					}
        				}
        				else if (args.get(2).replace("[", "").equalsIgnoreCase("atLeast") || args.get(2).replace("[", "").equalsIgnoreCase("dataAtLeast")){
        					try {
        						ec.addEquivalentClasses(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
        						if (changedOntology.containsEntity(KAON2Manager.factory().dataProperty(args.get(4).replace("]", "")), true)){
        							DataMinCardinality o = new DataMinCardinality();
        							o.setDataProperty(new DataProperty(args.get(4).replace("]", "")));
        							if (args.size()>5)
        								o.setDataRange(new Datatype(args.get(5).replace("]", "")));
        							else
        								o.setDataRange(new Datatype(rdfsLiteral));//o.setDataRange(new Datatype(args.get(1)));
        							o.setCardinality(new Integer(args.get(3)));
        							ec.addEquivalentClasses(o);
        						}else{
        							ObjectMinCardinality o = new ObjectMinCardinality();
        							o.setObjectProperty(new ObjectProperty(args.get(4).replace("]", "")));
        							if (args.size()>5)
        								o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(5).replace("]", "")));
            						else
            							o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(owlThing));//o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
        							o.setCardinality(new Integer(args.get(3)));
        							ec.addEquivalentClasses(o);
        						}
        						atomicChange.setAppliedAxiom(ec);
        					} catch (NumberFormatException e) {
        						OWLChangeListener.working--;
        						e.printStackTrace();
        					} catch (KAON2Exception e) {
        						OWLChangeListener.working--;
        						e.printStackTrace();
        					}
        				}
        				else if (args.get(2).replace("[", "").equalsIgnoreCase("atMost") || args.get(2).replace("[", "").equalsIgnoreCase("dataAtMost")){
        					try {
        						ec.addEquivalentClasses(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
        						if (changedOntology.containsEntity(KAON2Manager.factory().dataProperty(args.get(4).replace("]", "")), true)){
        							DataMaxCardinality o = new DataMaxCardinality();
        							o.setDataProperty(new DataProperty(args.get(4).replace("]", "")));
        							if (args.size()>5)
        								o.setDataRange(new Datatype(args.get(5).replace("]", "")));
        							else
        								o.setDataRange(new Datatype(rdfsLiteral));//o.setDataRange(new Datatype(args.get(1)));
        							o.setCardinality(new Integer(args.get(3)));
        							ec.addEquivalentClasses(o);
        						}else{
        							ObjectMaxCardinality o = new ObjectMaxCardinality();
        							o.setObjectProperty(new ObjectProperty(args.get(4).replace("]", "")));
        							if (args.size()>5)
        								o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(5).replace("]", "")));
            						else
            							o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(owlThing));//o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
        							o.setCardinality(new Integer(args.get(3)));
        							ec.addEquivalentClasses(o);
        						}
        						atomicChange.setAppliedAxiom(ec);
        					} catch (NumberFormatException e) {
        						OWLChangeListener.working--;
        						e.printStackTrace();
        					} catch (KAON2Exception e) {
        						OWLChangeListener.working--;
        						e.printStackTrace();
        					}
        				}
        				else if (args.get(2).replace("[", "").equalsIgnoreCase("exactly") || args.get(2).replace("[", "").equalsIgnoreCase("dataExactly")){
        					try {
        						ec.addEquivalentClasses(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
        						if (changedOntology.containsEntity(KAON2Manager.factory().dataProperty(args.get(4).replace("]", "")), true)){
        							DataExactCardinality o = new DataExactCardinality();
        							o.setDataProperty(new DataProperty(args.get(4).replace("]", "")));
        							if (args.size()>5)
        								o.setDataRange(new Datatype(args.get(5).replace("]", "")));
        							else
        								o.setDataRange(new Datatype(rdfsLiteral));//o.setDataRange(new Datatype(args.get(1)));
        							o.setCardinality(new Integer(args.get(3)));
        							ec.addEquivalentClasses(o);
        						}else{
        							ObjectExactCardinality o = new ObjectExactCardinality();
        							o.setObjectProperty(new ObjectProperty(args.get(4).replace("]", "")));
        							if (args.size()>5)
        								o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(5).replace("]", "")));
            						else
            							o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(owlThing));//o.setOWLClass(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
        							o.setCardinality(new Integer(args.get(3)));
        							ec.addEquivalentClasses(o);
        						}
        						atomicChange.setAppliedAxiom(ec);
        					} catch (NumberFormatException e) {
        						OWLChangeListener.working--;
        						e.printStackTrace();
        					} catch (KAON2Exception e) {
        						OWLChangeListener.working--;
        						e.printStackTrace();
        					}
        				}
        				else if (args.get(2).replace("[", "").equalsIgnoreCase("hasValue") || args.get(2).replace("[", "").equalsIgnoreCase("dataHasValue")){
        					try {
        						ec.addEquivalentClasses(new org.neontoolkit.owlodm.api.Description.OWLClass(args.get(1)));
        						if (changedOntology.containsEntity(KAON2Manager.factory().dataProperty(args.get(3)), true)){
        							DataHasValue o = new DataHasValue();
        							o.setDataProperty(new DataProperty(args.get(3)));
        							String cleanValue=args.get(4).replace("]", "");
        							if (cleanValue.indexOf("^^<")>0)
        								cleanValue=cleanValue.replace("^^<", "???").substring(0, cleanValue.length()-1);//cleanValue=cleanValue.substring(0, cleanValue.indexOf("^^"));	
        							o.setConstant(localURI+cleanValue);
        							ec.addEquivalentClasses(o);
        						}else{
        							ObjectHasValue o = new ObjectHasValue();
        							o.setObjectProperty(new ObjectProperty(args.get(3)));
        							o.setValue(new Individual(args.get(4).replace("]", "")));
        							ec.addEquivalentClasses(o);
        						}
        						atomicChange.setAppliedAxiom(ec);
        					} catch (KAON2Exception e) {
        						OWLChangeListener.working--;
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
        			oyster2Conn.register(classEntity);
        		}
        		else if(args.get(0).equals(Constants.ACTION_CLASS_ANN)){  
        			// 1 = annotationProperty, 2=annotated entity, 3= value
        			EntityAnnotation ec = new EntityAnnotation();
        			OWLClass ot = new OWLClass(args.get(2));
        			//ot.setURI(args.get(2));
        			ec.setEntity(ot);
        			String localN=Namespaces.guessLocalName(args.get(1)); //cannot use reserved names as values in ontology
        			if (localN!=null && (localN.equalsIgnoreCase("comment") || localN.equalsIgnoreCase("label")))
        				ec.setAnnotationProperty(localN);
        			else
        				ec.setAnnotationProperty(args.get(1));
        			ec.addEntityAnnotation(args.get(3));
        			atomicChange.setAppliedAxiom(ec);
        			
        			changeList.add(atomicChange);
        			oyster2Conn.register(atomicChange);
        			
        			AnnotationPropertyChange classEntity = new AnnotationPropertyChange();
        			if (atomicChange instanceof Addition){
        				String t=Namespaces.guessLocalName(args.get(1));
        				if (t.equalsIgnoreCase("comment")) classEntity = new AddComment();
        				else if (t.equalsIgnoreCase("label")) classEntity = new AddLabel();
        				else classEntity = new AddComment();// else return;
        			}else if (atomicChange instanceof Removal){
        				String t=Namespaces.guessLocalName(args.get(1));
        				if (t.equalsIgnoreCase("comment")) classEntity = new RemoveComment();
        				else if (t.equalsIgnoreCase("label")) classEntity = new RemoveLabel();
        				else classEntity = new RemoveComment();// else return;
        			}
        			classEntity.setAppliedToOntology(omvOnto);
        			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
        			classEntity.addHasRelatedEntity(args.get(2));
        			classEntity.addHasAuthor(se);
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
        			oyster2Conn.register(classEntity);
        		}
        		else if(args.get(0).equals(Constants.ACTION_OBPROPERTY_ASY)){ //HOW TO USE IT IN NTK
        			AsymmetricObjectProperty oPE = new AsymmetricObjectProperty();
        			oPE.setObjectProperty(new ObjectProperty(args.get(1)));
        			atomicChange.setAppliedAxiom(oPE);

        			changeList.add(atomicChange);
        			oyster2Conn.register(atomicChange);
        			OWLObjectPropertyChange classEntity = new OWLObjectPropertyChange();
        			if (atomicChange instanceof Addition){
        				classEntity = new AddAsymmetricObjectProperty();
        			}else if (atomicChange instanceof Removal){
        				classEntity = new RemoveAsymmetricObjectProperty();
        			}
        			classEntity.setAppliedToOntology(omvOnto);
        			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
        			classEntity.addHasRelatedEntity(args.get(1)); 
        			classEntity.addHasAuthor(se);
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
        			oyster2Conn.register(classEntity);
        		}
        		else if(args.get(0).equals(Constants.ACTION_OBPROPERTY_DIS)){ //HOW TO USE IT IN NTK
        			DisjointObjectProperties oPE = new DisjointObjectProperties();
        			oPE.addDisjointObjectProperties(new ObjectProperty(args.get(1)));
        			oPE.addDisjointObjectProperties(new ObjectProperty(args.get(2)));
        			atomicChange.setAppliedAxiom(oPE);

        			changeList.add(atomicChange);
        			oyster2Conn.register(atomicChange);
        			OWLObjectPropertyChange classEntity = new OWLObjectPropertyChange();
        			if (atomicChange instanceof Addition){
        				classEntity = new AddDisjointObjectProperty();
        			}else if (atomicChange instanceof Removal){
        				classEntity = new RemoveDisjointObjectProperty();
        			}
        			classEntity.setAppliedToOntology(omvOnto);
        			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
        			classEntity.addHasRelatedEntity(args.get(2)); //THIS IS PROBABLY ERROR (DIFFERENT FROM THE REST) 
        			classEntity.addHasAuthor(se);
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
        			oyster2Conn.register(classEntity);
        		}
        		else if(args.get(0).equals(Constants.ACTION_OBPROPERTY_IRR)){ //HOW TO USE IT IN NTK
        			IrreflexiveObjectProperty oPE = new IrreflexiveObjectProperty();
        			oPE.setObjectProperty(new ObjectProperty(args.get(1)));
        			atomicChange.setAppliedAxiom(oPE);

        			changeList.add(atomicChange);
        			oyster2Conn.register(atomicChange);
        			OWLObjectPropertyChange classEntity = new OWLObjectPropertyChange();
        			if (atomicChange instanceof Addition){
        				classEntity = new AddIrreflexiveObjectProperty();
        			}else if (atomicChange instanceof Removal){
        				classEntity = new RemoveIrreflexiveObjectProperty();
        			}
        			classEntity.setAppliedToOntology(omvOnto);
        			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
        			classEntity.addHasRelatedEntity(args.get(1)); 
        			classEntity.addHasAuthor(se);
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
        			oyster2Conn.register(classEntity);
        		}
        		else if(args.get(0).equals(Constants.ACTION_OBPROPERTY_REF)){ //HOW TO USE IT IN NTK
        			ReflexiveObjectProperty oPE = new ReflexiveObjectProperty();
        			oPE.setObjectProperty(new ObjectProperty(args.get(1)));
        			atomicChange.setAppliedAxiom(oPE);

        			changeList.add(atomicChange);
        			oyster2Conn.register(atomicChange);
        			OWLObjectPropertyChange classEntity = new OWLObjectPropertyChange();
        			if (atomicChange instanceof Addition){
        				classEntity = new AddReflexiveObjectProperty();
        			}else if (atomicChange instanceof Removal){
        				classEntity = new RemoveReflexiveObjectProperty();
        			}
        			classEntity.setAppliedToOntology(omvOnto);
        			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
        			classEntity.addHasRelatedEntity(args.get(1)); 
        			classEntity.addHasAuthor(se);
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
        			oyster2Conn.register(classEntity);
        		}
        		else if(args.get(0).equals(Constants.ACTION_OBPROPERTY_ANN)){ 
        			// 1 = annotationProperty, 2=annotated entity, 3= value
        			EntityAnnotation ec = new EntityAnnotation();
        			ObjectProperty ot = new ObjectProperty(args.get(2));
        			//ot.setURI(args.get(2));
        			ec.setEntity(ot);
        			String localN=Namespaces.guessLocalName(args.get(1)); //cannot use reserved names as values in ontology
        			if (localN!=null && (localN.equalsIgnoreCase("comment") || localN.equalsIgnoreCase("label")))
        				ec.setAnnotationProperty(localN);
        			else
        				ec.setAnnotationProperty(args.get(1));
        			ec.addEntityAnnotation(args.get(3));
        			atomicChange.setAppliedAxiom(ec);
        			
        			changeList.add(atomicChange);
        			oyster2Conn.register(atomicChange);
        			
        			AnnotationPropertyChange classEntity = new AnnotationPropertyChange();
        			if (atomicChange instanceof Addition){
        				String t=Namespaces.guessLocalName(args.get(1));
        				if (t.equalsIgnoreCase("comment")) classEntity = new AddComment();
        				else if (t.equalsIgnoreCase("label")) classEntity = new AddLabel();
        				else classEntity = new AddComment();//else return;
        			}else if (atomicChange instanceof Removal){
        				String t=Namespaces.guessLocalName(args.get(1));
        				if (t.equalsIgnoreCase("comment")) classEntity = new RemoveComment();
        				else if (t.equalsIgnoreCase("label")) classEntity = new RemoveLabel();
        				else classEntity = new RemoveComment();//else return;
        			}
        			classEntity.setAppliedToOntology(omvOnto);
        			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
        			classEntity.addHasRelatedEntity(args.get(2));
        			classEntity.addHasAuthor(se);
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
        			oyster2Conn.register(classEntity);
        		}
        		else if(args.get(0).equals(Constants.ACTION_DAPROPERTY_DIS)){ //HOW TO USE IT IN NTK?
        			DisjointDataProperties dPE = new DisjointDataProperties();
        			dPE.addDataProperties(new DataProperty(args.get(1)));
        			dPE.addDataProperties(new DataProperty(args.get(2)));
        			atomicChange.setAppliedAxiom(dPE);

        			changeList.add(atomicChange);
        			oyster2Conn.register(atomicChange);
        			OWLDataPropertyChange classEntity = new OWLDataPropertyChange();
        			if (atomicChange instanceof Addition){
        				classEntity = new AddDisjointDataProperty();
        			}else if (atomicChange instanceof Removal){
        				classEntity = new RemoveDisjointDataProperty();
        			}
        			classEntity.setAppliedToOntology(omvOnto);
        			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
        			classEntity.addHasRelatedEntity(args.get(2)); //THIS IS PROBABLY ERROR (DIFFERENT FROM THE REST)
        			classEntity.addHasAuthor(se);
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
        			oyster2Conn.register(classEntity);
        		}
        		else if(args.get(0).equals(Constants.ACTION_DAPROPERTY_ANN)){ 
        			// 1 = annotationProperty, 2=annotated entity, 3= value
        			EntityAnnotation ec = new EntityAnnotation();
        			DataProperty ot = new DataProperty(args.get(2));
        			//ot.setURI(args.get(2));
        			ec.setEntity(ot);
        			String localN=Namespaces.guessLocalName(args.get(1)); //cannot use reserved names as values in ontology
        			if (localN!=null && (localN.equalsIgnoreCase("comment") || localN.equalsIgnoreCase("label")))
        				ec.setAnnotationProperty(localN);
        			else
        				ec.setAnnotationProperty(args.get(1));
        			ec.addEntityAnnotation(args.get(3));
        			atomicChange.setAppliedAxiom(ec);
        			
        			changeList.add(atomicChange);
        			oyster2Conn.register(atomicChange);
        			
        			AnnotationPropertyChange classEntity = new AnnotationPropertyChange();
        			if (atomicChange instanceof Addition){
        				String t=Namespaces.guessLocalName(args.get(1));
        				if (t.equalsIgnoreCase("comment")) classEntity = new AddComment();
        				else if (t.equalsIgnoreCase("label")) classEntity = new AddLabel();
        				else classEntity = new AddComment();//else return;
        			}else if (atomicChange instanceof Removal){
        				String t=Namespaces.guessLocalName(args.get(1));
        				if (t.equalsIgnoreCase("comment")) classEntity = new RemoveComment();
        				else if (t.equalsIgnoreCase("label")) classEntity = new RemoveLabel();
        				else classEntity = new RemoveComment();//else return;
        			}
        			classEntity.setAppliedToOntology(omvOnto);
        			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
        			classEntity.addHasRelatedEntity(args.get(2));
        			classEntity.addHasAuthor(se);
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
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
        			String lastChange = oyster2Conn.getLastChangeId();
        			//classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId()); //WE ASSUME LAST CHANGE WAS THE ADDINDIVIDUAL
        			if (lastChange!=null){
        				OMVChange lastChangeO = oyster2Conn.getChange(lastChange);
        				if ((lastChangeO != null) && (lastChangeO instanceof OMVAtomicChange)){
        					OMVAtomicChange oac = (OMVAtomicChange)lastChangeO;
        					if ((oac.getAppliedAxiom() !=null) && (oac.getAppliedAxiom() instanceof Declaration)){
        						Declaration d = (Declaration)oac.getAppliedAxiom();
        						if ((d.getEntity()!=null) && (d.getEntity() instanceof Individual)){
        							classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
        						}
        					}
        				}
        			}
        			oyster2Conn.register(atomicChange);
        			classEntity.setAppliedToOntology(omvOnto);
        			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
        			classEntity.addHasRelatedEntity(args.get(1));
        			classEntity.addHasAuthor(se);
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
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
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
        			oyster2Conn.register(classEntity);
        		}
        		else if(args.get(0).equals(Constants.ACTION_INDIVIDUAL_DATAASSERTION)){
        			DataPropertyAssertion subDP = new DataPropertyAssertion();
        			subDP.setDataProperty(new DataProperty(args.get(1)));
        			subDP.setSourceIndividual(new Individual(args.get(2)));
        			String cleanValue=args.get(3);
        			if (cleanValue.indexOf("^^<")>0)
						cleanValue=cleanValue.replace("^^<", "???").substring(0, cleanValue.length()-1);//cleanValue=cleanValue.substring(0, cleanValue.indexOf("^^"));	
        			subDP.setTargetValue(localURI+cleanValue);
        			atomicChange.setAppliedAxiom(subDP);
        			
        			changeList.add(atomicChange);
        			oyster2Conn.register(atomicChange);
        			
        			OWLIndividualChange classEntity = new OWLIndividualChange();
        			if (atomicChange instanceof Addition){
        				classEntity = new AddIndividualDataProperty();
        			}else if (atomicChange instanceof Removal){
        				classEntity = new RemoveIndividualDataProperty();
        			}			
        			classEntity.setAppliedToOntology(omvOnto);
        			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
        			classEntity.addHasRelatedEntity(args.get(2));
        			classEntity.addHasAuthor(se);
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
        			oyster2Conn.register(classEntity);
        		}
        		else if(args.get(0).equals(Constants.ACTION_INDIVIDUAL_OBJECTASSERTION)){
        			ObjectPropertyAssertion subDP = new ObjectPropertyAssertion();
        			subDP.setObjectProperty(new ObjectProperty(args.get(1)));
        			subDP.setSourceIndividual(new Individual(args.get(2)));
        			subDP.setTargetIndividual(new Individual(args.get(3)));
        			atomicChange.setAppliedAxiom(subDP);
        			
        			changeList.add(atomicChange);
        			oyster2Conn.register(atomicChange);
        			
        			OWLIndividualChange classEntity = new OWLIndividualChange();
        			if (atomicChange instanceof Addition){
        				classEntity = new AddInvidivualObjectProperty();
        			}else if (atomicChange instanceof Removal){
        				classEntity = new RemoveInvidualObjectProperty();
        			}			
        			classEntity.setAppliedToOntology(omvOnto);
        			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
        			classEntity.addHasRelatedEntity(args.get(2));
        			classEntity.addHasAuthor(se);
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
        			oyster2Conn.register(classEntity);
        		}
        		else if(args.get(0).equals(Constants.ACTION_INDIVIDUAL_NEGDATAASSERTION)){ //HOW TO USE IT IN NTK?
        			NegativeDataPropertyAssertion subDP = new NegativeDataPropertyAssertion();
        			subDP.setDataProperty(new DataProperty(args.get(1)));
        			subDP.setSourceIndividual(new Individual(args.get(2)));
        			String cleanValue=args.get(3);
        			if (cleanValue.indexOf("^^<")>0)
						cleanValue=cleanValue.replace("^^<", "???").substring(0, cleanValue.length()-1);//cleanValue=cleanValue.substring(0, cleanValue.indexOf("^^"));	
        			subDP.setTargetValue(localURI+cleanValue);
        			atomicChange.setAppliedAxiom(subDP);
        			
        			changeList.add(atomicChange);
        			oyster2Conn.register(atomicChange);
        			
        			OWLIndividualChange classEntity = new OWLIndividualChange();
        			if (atomicChange instanceof Addition){
        				classEntity = new AddIndividualDataProperty();
        			}else if (atomicChange instanceof Removal){
        				classEntity = new RemoveIndividualDataProperty();
        			}			
        			classEntity.setAppliedToOntology(omvOnto);
        			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
        			classEntity.addHasRelatedEntity(args.get(2));
        			classEntity.addHasAuthor(se);
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
        			oyster2Conn.register(classEntity);
        		}
        		else if(args.get(0).equals(Constants.ACTION_INDIVIDUAL_NEGOBJECTASSERTION)){ //HOW TO USE IT IN NTK?
        			NegativeObjectPropertyAssertion subDP = new NegativeObjectPropertyAssertion();
        			subDP.setObjectProperty(new ObjectProperty(args.get(1)));
        			subDP.setSourceIndividual(new Individual(args.get(2)));
        			subDP.setTargetIndividual(new Individual(args.get(3)));
        			atomicChange.setAppliedAxiom(subDP);
        			
        			changeList.add(atomicChange);
        			oyster2Conn.register(atomicChange);
        			
        			OWLIndividualChange classEntity = new OWLIndividualChange();
        			if (atomicChange instanceof Addition){
        				classEntity = new AddInvidivualObjectProperty();
        			}else if (atomicChange instanceof Removal){
        				classEntity = new RemoveInvidualObjectProperty();
        			}			
        			classEntity.setAppliedToOntology(omvOnto);
        			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
        			classEntity.addHasRelatedEntity(args.get(2));
        			classEntity.addHasAuthor(se);
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
        			oyster2Conn.register(classEntity);
        		}
        		else if(args.get(0).equals(Constants.ACTION_INDIVIDUAL_ANN)){ 
        			// 1 = annotationProperty, 2=annotated entity, 3= value
        			EntityAnnotation ec = new EntityAnnotation();
        			Individual ot = new Individual(args.get(2));
        			//ot.setURI(args.get(2));
        			ec.setEntity(ot);
        			String localN=Namespaces.guessLocalName(args.get(1)); //cannot use reserved names as values in ontology
        			if (localN!=null && (localN.equalsIgnoreCase("comment") || localN.equalsIgnoreCase("label")))
        				ec.setAnnotationProperty(localN);
        			else
        				ec.setAnnotationProperty(args.get(1));
        			ec.addEntityAnnotation(args.get(3));
        			atomicChange.setAppliedAxiom(ec);
        			
        			changeList.add(atomicChange);
        			oyster2Conn.register(atomicChange);
        			
        			AnnotationPropertyChange classEntity = new AnnotationPropertyChange();
        			if (atomicChange instanceof Addition){
        				String t=Namespaces.guessLocalName(args.get(1));
        				if (t.equalsIgnoreCase("comment")) classEntity = new AddComment();
        				else if (t.equalsIgnoreCase("label")) classEntity = new AddLabel();
        				else classEntity = new AddComment();//else return;
        			}else if (atomicChange instanceof Removal){
        				String t=Namespaces.guessLocalName(args.get(1));
        				if (t.equalsIgnoreCase("comment")) classEntity = new RemoveComment();
        				else if (t.equalsIgnoreCase("label")) classEntity = new RemoveLabel();
        				else classEntity = new RemoveComment();//else return;
        			}
        			classEntity.setAppliedToOntology(omvOnto);
        			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
        			classEntity.addHasRelatedEntity(args.get(2));
        			classEntity.addHasAuthor(se);
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
        			oyster2Conn.register(classEntity);
        		}
        		else if(args.get(0).equals(Constants.ACTION_DATYPE_ANN)){  
        			// 1 = annotationProperty, 2=annotated entity, 3= value
        			EntityAnnotation ec = new EntityAnnotation();
        			
        			Datatype ot;
        			String localNE=Namespaces.guessLocalName(args.get(2)); //cannot use reserved names as values in ontology
        			ot = new Datatype(localNE);
        			ec.setEntity(ot);
        			String localN=Namespaces.guessLocalName(args.get(1)); //cannot use reserved names as values in ontology
        			if (localN!=null && (localN.equalsIgnoreCase("comment") || localN.equalsIgnoreCase("label")))
        				ec.setAnnotationProperty(localN);
        			else
        				ec.setAnnotationProperty(args.get(1));
        			ec.addEntityAnnotation(args.get(3));
        			atomicChange.setAppliedAxiom(ec);
        			
        			changeList.add(atomicChange);
        			oyster2Conn.register(atomicChange);
        			
        			AnnotationPropertyChange classEntity = new AnnotationPropertyChange();
        			if (atomicChange instanceof Addition){
        				String t=Namespaces.guessLocalName(args.get(1));
        				if (t.equalsIgnoreCase("comment")) classEntity = new AddComment();
        				else if (t.equalsIgnoreCase("label")) classEntity = new AddLabel();
        				else classEntity = new AddComment();// else return;
        			}else if (atomicChange instanceof Removal){
        				String t=Namespaces.guessLocalName(args.get(1));
        				if (t.equalsIgnoreCase("comment")) classEntity = new RemoveComment();
        				else if (t.equalsIgnoreCase("label")) classEntity = new RemoveLabel();
        				else classEntity = new RemoveComment();// else return;
        			}
        			classEntity.setAppliedToOntology(omvOnto);
        			classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
        			classEntity.addHasRelatedEntity(args.get(2));
        			classEntity.addHasAuthor(se);
        			classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
        			oyster2Conn.register(classEntity);
        		}
        		else{
            		String see="";
        			for (String as : args) see+=" "+as;
        			System.out.println("didnt log (no change operation available!): "+see);
        			return;
        		}
        		//Check if failed registration
        		if (oyster2Conn.getLastChangeId()==null){
        			List<OMVChange> listToApply = new LinkedList<OMVChange>();
        			OMVAtomicChange undo = null;
        			if (atomicChange instanceof Addition){
        				undo = new Removal();
        			}
        			else if (atomicChange instanceof Removal){
        				undo = new Addition();
        			}
        			for (OMVPerson p : atomicChange.getHasAuthor()) undo.addHasAuthor(p);
        			if (atomicChange.getAppliedAxiom()!=null) undo.setAppliedAxiom(atomicChange.getAppliedAxiom());
        			if (atomicChange.getAppliedToOntology()!=null) undo.setAppliedToOntology(atomicChange.getAppliedToOntology());
        			for (String cc : atomicChange.getCauseChange()) undo.addCauseChange(cc);
        			if (atomicChange.getCost()!=null) undo.setCost(atomicChange.getCost());
        			if (atomicChange.getDate()!=null) undo.setDate(atomicChange.getDate());
        			if (atomicChange.getHasPreviousChange()!=null) undo.setHasPreviousChange(atomicChange.getHasPreviousChange());
        			if (atomicChange.getPriority()!=null) undo.setPriority(atomicChange.getPriority());
        			if (atomicChange.getRelevance()!=null) undo.setRelevance(atomicChange.getRelevance());
        			if (atomicChange.getTime()!=null) undo.setTime(atomicChange.getTime());
        			if (atomicChange.getURI()!=null) undo.setURI(atomicChange.getURI());
        			if (atomicChange.getVersion()!=null) undo.setVersion(atomicChange.getVersion());
        			listToApply.add(undo);
        			
        			if (atomicChange.getAppliedAxiom() instanceof ClassAssertion){ //The only complex operation we have
        				Declaration declaration = new Declaration();
        				declaration.setEntity(((ClassAssertion)atomicChange.getAppliedAxiom()).getIndividual());
        				OMVAtomicChange undoDeclareIndividual=null;
        				if (undo instanceof Addition) undoDeclareIndividual=new Addition();
        				else if (undo instanceof Removal) undoDeclareIndividual=new Removal();
        				if (undoDeclareIndividual!=null){
        					List<OMVChange> listToApplyIndividual = new LinkedList<OMVChange>();
        					undoDeclareIndividual.setAppliedAxiom(declaration);
        					listToApplyIndividual.add(undoDeclareIndividual);
        					ApplyChangesFromLogToNTK.applyChanges(listToApplyIndividual, atomicChange.getAppliedToOntology());
        				}
        			}
        			ApplyChangesFromLogToNTK.applyChanges(listToApply, atomicChange.getAppliedToOntology());
        			String see="";
        			for (String as : args) see+=" "+as;
        			System.out.println("args: "+see);
        			MessageDialog.openError(
    						shell,
    						"Change Capturing Error",
    						"The operation could not be performed. (Could be incorrect permission)!");
        		}
        		OWLChangeListener.working--;
            }catch(Exception e){
        		OWLChangeListener.working--;
        		e.printStackTrace();
        	}	
	}
}

