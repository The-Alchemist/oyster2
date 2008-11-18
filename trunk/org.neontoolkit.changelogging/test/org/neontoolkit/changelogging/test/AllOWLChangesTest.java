package org.neontoolkit.changelogging.test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertNotNull;
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import junit.framework.JUnit4TestAdapter;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.junit.Test;
import org.neontoolkit.changelogging.core.owl.OWLChangeListener;
import org.neontoolkit.changelogging.gui.actions.Track;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.core.OMVPerson;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLOntologyChange;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLClassChange.EquivalentClassChange;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLClassChange.EquivalentClassChange.AddEquivalentClass;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.DisjointDataPropertyChange.AddDisjointDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange.IndividualDataPropertyChange.AddIndividualDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange.IndividualObjectPropertyChange.AddInvidivualObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.DisjointObjectPropertyChange.AddDisjointObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLOntologyChange.AddDatatype;
import org.neontoolkit.omv.api.extensions.change.OMVChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange.Addition;
import org.neontoolkit.owlodm.api.Axiom.Declaration;
import org.neontoolkit.owlodm.api.Axiom.ClassAxiom.EquivalentClasses;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.DisjointDataProperties;
import org.neontoolkit.owlodm.api.Axiom.Fact.NegativeDataPropertyAssertion;
import org.neontoolkit.owlodm.api.Axiom.Fact.NegativeObjectPropertyAssertion;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.DisjointObjectProperties;
import org.neontoolkit.owlodm.api.Description.ObjectExistsSelf;
import org.neontoolkit.owlodm.api.OWLEntity.DataProperty;
import org.neontoolkit.owlodm.api.OWLEntity.Datatype;
import org.neontoolkit.owlodm.api.OWLEntity.Individual;
import org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;
import org.neontoolkit.registry.api.Oyster2Connection;
import org.semanticweb.kaon2.api.DefaultOntologyResolver;
import org.semanticweb.kaon2.api.KAON2Exception;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Namespaces;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.OntologyChangeEvent;
import org.semanticweb.kaon2.api.OntologyManager;
import org.semanticweb.kaon2.api.formatting.OntologyFileFormat;
import org.semanticweb.kaon2.api.owl.axioms.DataPropertyAttribute;
import org.semanticweb.kaon2.api.owl.axioms.ObjectPropertyAttribute;
import org.semanticweb.kaon2.api.owl.elements.DataCardinality;
import org.semanticweb.kaon2.api.owl.elements.DataPropertyExpression;
import org.semanticweb.kaon2.api.owl.elements.DataRange;
import org.semanticweb.kaon2.api.owl.elements.ObjectCardinality;
import com.ontoprise.ontostudio.datamodel.DatamodelPlugin;
import com.ontoprise.ontostudio.gui.GuiPlugin;


public class AllOWLChangesTest extends ChangeLoggingBefore {

	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
	private IPreferenceStore _store = GuiPlugin.getDefault().getPreferenceStore();
	private IAction actionNew;
	private static final String pizzaURI = "http://www.co-ode.org/ontologies/pizza/pizza_20041007.owl#";
	public static final String ROLE = "ROLE"; 
	public static final String USER_FIRSTNAME = "USER_FIRSTNAME";
	public static final String USER_LASTNAME = "USER_LASTNAME";
	public static String localURI="http://localhost/";
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(AllOWLChangesTest.class);
	}
	    
    @Test
    public void doLogging() throws Exception {
    	System.out.println("-----------------------test1-------------------------");
    	System.out.println("Test 1: Change Logging");
    	System.out.println();
    	System.out.println("Sub1: Start Registry");
    	StartRegistry registry = new StartRegistry();
    	registry.run(actionNew);
    	int wait=9; //wait maximum 90 sec
    	while (wait>0 && StartRegistry.connection==null){
    		Thread.sleep(10000); //wait 10 sec
    		wait--;
    	}
    	assertNotNull("Registry did not start!", StartRegistry.connection);
    	
    	
    	System.out.println("Sub2: Start logging changes of ontology");
    	_store.setValue(USER_FIRSTNAME, "Raul");
        _store.setValue(USER_LASTNAME, "Palma");
        _store.setValue(ROLE, "http://omv.ontoware.org/2007/07/workflow#SubjectExpert");
    	Track track = new Track();
    	track.setModuleId("");
    	track.setProject(_project.getName());
    	track.setOntologyURI("http://www.co-ode.org/ontologies/pizza/pizza_20041007.owl");
    	track.run(actionNew);
    	wait=9; //wait maximum 90 sec
    	while (wait>0 && Track.OWLList.size()<=0){
    		Thread.sleep(5000); //wait 5 sec
    		wait--;
    	}
    	assertEquals("The Ontology is not being logged",1,Track.OWLList.size());
    }
    
    @Test
    public void addChange() throws Exception {
    	System.out.println("-----------------------test2-------------------------");
    	System.out.println("Test 2: Check if a change is logged");
    	System.out.println();
    	System.out.println("Sub1: Add All Changes");
    	
    	applyAll();
    	
    	OntologyManager connection = DatamodelPlugin.getDefault().getKaon2Connection(_project.getName());
    	Ontology ontology = connection.getOntology("http://www.co-ode.org/ontologies/pizza/pizza_20041007.owl");
    	try {
    		ontology.applyChanges(changes);
    		ontology.persist();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	try{
    		String outputOntologyFile=System.getProperty("user.dir")+System.getProperty("file.separator")+"pizzaAll.owl";
    		ontology.saveOntology(OntologyFileFormat.OWL_RDF,new File(outputOntologyFile),"ISO-8859-1");
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
		System.out.println("Sub2: Verifying if the change is logged");
		List<OWLChangeListener> listners = new ArrayList<OWLChangeListener>();
		listners.addAll(Track.OWLList.values());
		OWLChangeListener listener = listners.get(0);
		List<OMVChange> checking = new LinkedList<OMVChange>();
		int wait=20; //wait maximum 90 sec
    	while (wait>0){// && checking.size()<=0){
    		//checking=listener.getChanges();
    		Thread.sleep(5000); //wait 5 sec
    		wait--;
    	}
    	checking=listener.getChanges();
    	System.out.println("changes1: "+checking.size());
    	addManualErrors();
    	checking=listener.getChanges();
    	System.out.println("changes2: "+checking.size());
    	
    	try{
    		String registryFile="file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"server"+System.getProperty("file.separator")+"localRegistry.owl";
        	registryFile=registryFile.replace("\\", "//");
        	String outputRegistryFile=System.getProperty("user.dir")+System.getProperty("file.separator")+"localRegistryAllOWL.owl";
    		Properties properties = new Properties();
			properties.put("OntologyLanguage", "OWL");
			properties.put("Storage", "RAM.choose");
    		OntologyManager tempConnection=KAON2Manager.newOntologyManager(properties);
    		DefaultOntologyResolver resolver=new DefaultOntologyResolver();
        	resolver.registerReplacement("http://localhost/localRegistry",registryFile);
        	tempConnection.setOntologyResolver(resolver);                  
        	Ontology ontologyReg=tempConnection.openOntology("http://localhost/localRegistry",new HashMap<String,Object>());
        	ontologyReg.saveOntology(OntologyFileFormat.OWL_RDF,new File(outputRegistryFile),"ISO-8859-1");
        	tempConnection.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	assertTrue("The change was not logged",checking.size()>0);
    }
    
    public void applyAll(){
    	org.semanticweb.kaon2.api.Axiom app=null;
    	org.semanticweb.kaon2.api.owl.elements.OWLEntity ddm=null;
    	org.semanticweb.kaon2.api.owl.elements.Description ddmD=null;
    	org.semanticweb.kaon2.api.owl.elements.DataProperty dp=null;
    	Collection<org.semanticweb.kaon2.api.owl.elements.Description> cddm= new HashSet<org.semanticweb.kaon2.api.owl.elements.Description>();
    	org.semanticweb.kaon2.api.owl.elements.OWLClass uc=null;
    	org.semanticweb.kaon2.api.owl.elements.Description sup=null;
    	org.semanticweb.kaon2.api.owl.elements.Description sub=null;
    	org.semanticweb.kaon2.api.owl.elements.Description dom=null;
    	org.semanticweb.kaon2.api.owl.elements.Datatype dt=null;
    	Collection<org.semanticweb.kaon2.api.owl.elements.DataPropertyExpression> cddmdp= new HashSet<org.semanticweb.kaon2.api.owl.elements.DataPropertyExpression>();
    	List<DataPropertyExpression> dpes = new LinkedList<DataPropertyExpression>();
    	org.semanticweb.kaon2.api.owl.elements.DataProperty subdp = null;
		org.semanticweb.kaon2.api.owl.elements.DataProperty supdp = null;
		org.semanticweb.kaon2.api.owl.elements.ObjectProperty op = null;
		org.semanticweb.kaon2.api.owl.elements.Description ran =null;
		org.semanticweb.kaon2.api.owl.elements.ObjectProperty supob = null;
		org.semanticweb.kaon2.api.owl.elements.Description cl = null;
		org.semanticweb.kaon2.api.owl.elements.Individual in = null;
		org.semanticweb.kaon2.api.owl.elements.Individual inS=null;
		org.semanticweb.kaon2.api.owl.elements.Individual inT=null;
		org.semanticweb.kaon2.api.owl.elements.OWLClass owlClassIn=null;
		List<org.semanticweb.kaon2.api.owl.elements.Individual> dpesIndiv = new LinkedList<org.semanticweb.kaon2.api.owl.elements.Individual>();
		List<org.semanticweb.kaon2.api.owl.elements.Description> cddmList= new LinkedList<org.semanticweb.kaon2.api.owl.elements.Description>();
		DataRange dr = null;
		Collection<org.semanticweb.kaon2.api.owl.elements.ObjectPropertyExpression> cddmop= new HashSet<org.semanticweb.kaon2.api.owl.elements.ObjectPropertyExpression>();
		List<org.semanticweb.kaon2.api.owl.elements.ObjectPropertyExpression> cddmopList= new LinkedList<org.semanticweb.kaon2.api.owl.elements.ObjectPropertyExpression>();
		Collection<org.semanticweb.kaon2.api.owl.elements.Individual> cddmin= new HashSet<org.semanticweb.kaon2.api.owl.elements.Individual>();
		List<org.semanticweb.kaon2.api.owl.elements.Description> dpesD = new LinkedList<org.semanticweb.kaon2.api.owl.elements.Description>();
		
    	//--------------Classes------------------
    	//Disjoint classes
    	cddm= new HashSet<org.semanticweb.kaon2.api.owl.elements.Description>();
		cddm.add(KAON2Manager.factory().owlClass(pizzaURI+"Dis1"));
		cddm.add(KAON2Manager.factory().owlClass(pizzaURI+"Dis2"));
		app = KAON2Manager.factory().disjointClasses(cddm);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//Disjoint union classes -------IGNORED IN KAON2, NOT AVAILABLE IN NTK---------
		cddm= new HashSet<org.semanticweb.kaon2.api.owl.elements.Description>();
		cddm.add(KAON2Manager.factory().owlClass(pizzaURI+"DisU1"));
		uc = KAON2Manager.factory().owlClass(pizzaURI+"DisUnionClass1");
		app = KAON2Manager.factory().disjointUnion(uc, cddm);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//Equivalent classes
		cddm= new HashSet<org.semanticweb.kaon2.api.owl.elements.Description>();
		cddm.add(KAON2Manager.factory().owlClass(pizzaURI+"Eq1"));
		cddm.add(KAON2Manager.factory().owlClass(pizzaURI+"Eq2"));
		app = KAON2Manager.factory().equivalentClasses(cddm);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//SubClassOf classes
		sup = KAON2Manager.factory().owlClass(pizzaURI+"SuperClass1");
		sub = KAON2Manager.factory().owlClass(pizzaURI+"SubClass1");
		app = KAON2Manager.factory().subClassOf(sub,sup);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		
		//--------------Declaration------------------
		//Class
		ddm = KAON2Manager.factory().owlClass(pizzaURI+"ClassDeclaration");
		app = KAON2Manager.factory().declaration(ddm);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));

		//dataProperty
		ddm = KAON2Manager.factory().dataProperty(pizzaURI+"dataPropertyDeclaration");
		app = KAON2Manager.factory().declaration(ddm);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));

		//objectProperty
		ddm = KAON2Manager.factory().objectProperty(pizzaURI+"objectPropertyDeclaration");
		app = KAON2Manager.factory().declaration(ddm);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//Individual
		ddm = KAON2Manager.factory().individual(pizzaURI+"individualDeclaration");
		app = KAON2Manager.factory().declaration(ddm);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		
		//dataType ----------NOT SUPPORTED: ERROR IN KAON2, NOT AVAILABLE IN NTK-----------------
		//ddm = KAON2Manager.factory().datatype(pizzaURI+"dataTypeDeclaration");
		//app = KAON2Manager.factory().declaration(ddm);
		//changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//annotationProperty  ---NOT PART OF OWL 2 APRIL VERSION
		ddm = KAON2Manager.factory().annotationProperty(pizzaURI+"annotationPropertyDeclaration");
		app = KAON2Manager.factory().declaration(ddm);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//--------------DataProperties------------------
		
		//DataProperty domain
		dp = KAON2Manager.factory().dataProperty(pizzaURI+"dataPDom1");
		dom= KAON2Manager.factory().owlClass(pizzaURI+"dpDom1");
		app = KAON2Manager.factory().dataPropertyDomain(dp, dom);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//DataProperty range
		dp = KAON2Manager.factory().dataProperty(pizzaURI+"dataPRange");
		dt = KAON2Manager.factory().datatype("http://www.w3.org/2001/XMLSchema#"+"string");
		app = KAON2Manager.factory().dataPropertyRange(dp, dt);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//Disjoint DataProperty  ----------NOT SUPPORTED: ERROR IN KAON2, NOT AVAILABLE IN NTK-----------------
		//cddmdp= new HashSet<org.semanticweb.kaon2.api.owl.elements.DataPropertyExpression>();
		//cddmdp.add(KAON2Manager.factory().dataProperty(pizzaURI+"dpDis1"));
		//cddmdp.add(KAON2Manager.factory().dataProperty(pizzaURI+"dpDis2"));
		//app = KAON2Manager.factory().disjointDataProperties(cddmdp);
		//changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//Equivalent DataProperty
		cddmdp= new HashSet<org.semanticweb.kaon2.api.owl.elements.DataPropertyExpression>();
		cddmdp.add(KAON2Manager.factory().dataProperty(pizzaURI+"dpEq1"));
		cddmdp.add(KAON2Manager.factory().dataProperty(pizzaURI+"dpEq2"));
		app = KAON2Manager.factory().equivalentDataProperties(cddmdp);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//Functional DataProperty
		dp = KAON2Manager.factory().dataProperty(pizzaURI+"dataPFun1");
		app = KAON2Manager.factory().dataPropertyAttribute(dp, DataPropertyAttribute.DATA_PROPERTY_FUNCTIONAL);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//SubDataProperty
		subdp = KAON2Manager.factory().dataProperty(pizzaURI+"subDataP1");
		supdp = KAON2Manager.factory().dataProperty(pizzaURI+"supDataP1");
		app = KAON2Manager.factory().subDataPropertyOf(subdp, supdp);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//--------------ObjectProperties------------------
		
		//Asymetric objectProperty ---IGNORED IN NTK-----------
		op = KAON2Manager.factory().objectProperty(pizzaURI+"obPAsym1");
		app = KAON2Manager.factory().objectPropertyAttribute(op, ObjectPropertyAttribute.OBJECT_PROPERTY_ASYMMETRIC);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));

		//Disjoint objectProperty ----------NOT SUPPORTED: ERROR IN KAON2, NOT AVAILABLE IN NTK-----------------
		//cddmop= new HashSet<org.semanticweb.kaon2.api.owl.elements.ObjectPropertyExpression>();
		//cddmop.add(KAON2Manager.factory().objectProperty(pizzaURI+"obPDis1"));
		//cddmop.add(KAON2Manager.factory().objectProperty(pizzaURI+"obPDis2"));
		//app = KAON2Manager.factory().disjointObjectProperties(cddmop);
		//changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//Equivalent objectProperty
		cddmop= new HashSet<org.semanticweb.kaon2.api.owl.elements.ObjectPropertyExpression>();
		cddmop.add(KAON2Manager.factory().objectProperty(pizzaURI+"obPEq1"));
		cddmop.add(KAON2Manager.factory().objectProperty(pizzaURI+"obPEq2"));
		app = KAON2Manager.factory().equivalentObjectProperties(cddmop);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//Functional objectProperty
		op = KAON2Manager.factory().objectProperty(pizzaURI+"obPFunc1");
		app = KAON2Manager.factory().objectPropertyAttribute(op, ObjectPropertyAttribute.OBJECT_PROPERTY_FUNCTIONAL);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//Inverse Functional objectProperty
		op = KAON2Manager.factory().objectProperty(pizzaURI+"obPInvFunc1");
		app = KAON2Manager.factory().objectPropertyAttribute(op, ObjectPropertyAttribute.OBJECT_PROPERTY_INVERSE_FUNCTIONAL);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//Inverse objectProperties
		cddmop= new HashSet<org.semanticweb.kaon2.api.owl.elements.ObjectPropertyExpression>();
		cddmop.add(KAON2Manager.factory().objectProperty(pizzaURI+"obPInv1"));
		cddmop.add(KAON2Manager.factory().objectProperty(pizzaURI+"obPInv2"));
		Iterator<org.semanticweb.kaon2.api.owl.elements.ObjectPropertyExpression> strange = cddmop.iterator();
		app = KAON2Manager.factory().inverseObjectProperties(strange.next(), strange.next());
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//Irreflexible objectProperty ---IGNORED IN NTK---------
		op = KAON2Manager.factory().objectProperty(pizzaURI+"obPIrref1");
		app = KAON2Manager.factory().objectPropertyAttribute(op, ObjectPropertyAttribute.OBJECT_PROPERTY_IRREFLEXIVE);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//ObjectProperty domain
		op = KAON2Manager.factory().objectProperty(pizzaURI+"obPDom1");
		dom= KAON2Manager.factory().owlClass(pizzaURI+"obDom1");
		app = KAON2Manager.factory().objectPropertyDomain(op, dom);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//ObjectProperty range
		op = KAON2Manager.factory().objectProperty(pizzaURI+"obPropertyRan1");
		ran= KAON2Manager.factory().owlClass(pizzaURI+"obRan1");
		app = KAON2Manager.factory().objectPropertyRange(op, ran);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//Reflexible objectProperty -----------IGNORED IN NTK------------
		op = KAON2Manager.factory().objectProperty(pizzaURI+"obPRef1");
		app = KAON2Manager.factory().objectPropertyAttribute(op, ObjectPropertyAttribute.OBJECT_PROPERTY_REFLEXIVE);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//Symetric objectProperty
		op = KAON2Manager.factory().objectProperty(pizzaURI+"obPSym1");
		app = KAON2Manager.factory().objectPropertyAttribute(op, ObjectPropertyAttribute.OBJECT_PROPERTY_SYMMETRIC);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//Transitive objectProperty
		op = KAON2Manager.factory().objectProperty(pizzaURI+"obPTran1");
		app = KAON2Manager.factory().objectPropertyAttribute(op, ObjectPropertyAttribute.OBJECT_PROPERTY_TRANSITIVE);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//SubObjectProperty
		cddmopList= new LinkedList<org.semanticweb.kaon2.api.owl.elements.ObjectPropertyExpression>();
		cddmopList.add(KAON2Manager.factory().objectProperty(pizzaURI+"obSub1"));
		supob = KAON2Manager.factory().objectProperty(pizzaURI+"obSup1");
		app = KAON2Manager.factory().subObjectPropertyOf(cddmopList, supob);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//--------------Fact------------------		
		
		//Class Assertion
		cl = KAON2Manager.factory().owlClass(pizzaURI+"clAssert1");
		in = KAON2Manager.factory().individual(pizzaURI+"indiv1");
		app = KAON2Manager.factory().classMember(cl, in);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//DataProperty Assertion
		dp= KAON2Manager.factory().dataProperty(pizzaURI+"dpAssertion1");
		in = KAON2Manager.factory().individual(pizzaURI+"indiv1");
		app = KAON2Manager.factory().dataPropertyMember(dp, in, KAON2Manager.factory().constant("Value"));
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//Different Individuals
		cddmin= new HashSet<org.semanticweb.kaon2.api.owl.elements.Individual>();
		cddmin.add(KAON2Manager.factory().individual(pizzaURI+"indiv1"));
		cddmin.add(KAON2Manager.factory().individual(pizzaURI+"indiv2"));
		app = KAON2Manager.factory().differentIndividuals(cddmin);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//Negative DataProperty Assertion ----------NOT SUPPORTED: ERROR IN KAON2, NOT AVAILABLE IN NTK-----------------
		//dp= KAON2Manager.factory().dataProperty(pizzaURI+"dpNegAssertion1");
		//in = KAON2Manager.factory().individual(pizzaURI+"indiv1");
		//app = KAON2Manager.factory().negativeDataPropertyMember(dp, in, KAON2Manager.factory().constant("Value of Negative"));
		//changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//Negative ObjectProperty Assertion ----------NOT SUPPORTED: ERROR IN KAON2, NOT AVAILABLE IN NTK-----------------
		//op= KAON2Manager.factory().objectProperty(pizzaURI+"obNegAssertion1");
		//inS = KAON2Manager.factory().individual(pizzaURI+"indiv1");
		//inT = KAON2Manager.factory().individual(pizzaURI+"indiv3");
		//app = KAON2Manager.factory().negativeObjectPropertyMember(op, inS, inT);
		//changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//ObjectProperty Assertion
		op= KAON2Manager.factory().objectProperty(pizzaURI+"obAssertion1");
		inS = KAON2Manager.factory().individual(pizzaURI+"indiv1");
		inT = KAON2Manager.factory().individual(pizzaURI+"indiv4");
		app = KAON2Manager.factory().objectPropertyMember(op, inS, inT);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//Same Individuals
		cddmin= new HashSet<org.semanticweb.kaon2.api.owl.elements.Individual>();
		cddmin.add(KAON2Manager.factory().individual(pizzaURI+"indiv1"));
		cddmin.add(KAON2Manager.factory().individual(pizzaURI+"indiv5"));
		app = KAON2Manager.factory().sameIndividual(cddmin);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//--------------EntityAnnotation------------------
		
		//Class
		ddm = KAON2Manager.factory().owlClass(pizzaURI+"ClassEntityAnnotate");
		app = KAON2Manager.factory().entityAnnotation(KAON2Manager.factory().annotationProperty("http://www.w3.org/2000/01/rdf-schema#comment"), ddm, KAON2Manager.factory().constant("This is the class annotation"));
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//datatype
		ddm = KAON2Manager.factory().datatype(pizzaURI+"DatatypeAnnotate");
		app = KAON2Manager.factory().entityAnnotation(KAON2Manager.factory().annotationProperty("http://www.w3.org/2000/01/rdf-schema#comment"), ddm, KAON2Manager.factory().constant("This is the new datatype annotation"));
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		
		//dataProperty
		ddm = KAON2Manager.factory().dataProperty(pizzaURI+"DataPropertyAnnotate");
		app = KAON2Manager.factory().entityAnnotation(KAON2Manager.factory().annotationProperty("http://www.w3.org/2000/01/rdf-schema#comment"), ddm, KAON2Manager.factory().constant("This is the dataProperty annotation"));
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//objectProperty
		ddm = KAON2Manager.factory().objectProperty(pizzaURI+"ObjectPropertyAnnotate");
		app = KAON2Manager.factory().entityAnnotation(KAON2Manager.factory().annotationProperty("http://www.w3.org/2000/01/rdf-schema#comment"), ddm, KAON2Manager.factory().constant("This is the objectProperty annotation"));
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//individual
		ddm = KAON2Manager.factory().individual(pizzaURI+"IndividualAnnotate");
		app = KAON2Manager.factory().entityAnnotation(KAON2Manager.factory().annotationProperty("http://www.w3.org/2000/01/rdf-schema#comment"), ddm, KAON2Manager.factory().constant("This is the individual annotation"));
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//--------------ALL DESCRIPTIONS------------------------
		
		//Equivalent classes
		//DataAllValuesFrom
		cddmList= new LinkedList<org.semanticweb.kaon2.api.owl.elements.Description>();
		cddmList.add(KAON2Manager.factory().owlClass(pizzaURI+"Eq3"));
		dpes = new LinkedList<DataPropertyExpression>();
		try {
			dp=KAON2Manager.factory().dataProperty(pizzaURI+"dpAllValuesFrom");
			dr = KAON2Manager.factory().dataRange("http://www.w3.org/2001/XMLSchema#string", new Namespaces());
			dpes.add(KAON2Manager.factory().dataProperty(dp.getURI()));
			ddmD = KAON2Manager.factory().dataAll(dr, dpes);
		} catch (KAON2Exception e) {
			e.printStackTrace();
		}
		cddmList.add(ddmD);
		app = KAON2Manager.factory().equivalentClasses(cddmList);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		
		//Equivalent classes
		//DataExactCardinality
		cddmList= new LinkedList<org.semanticweb.kaon2.api.owl.elements.Description>();
		cddmList.add(KAON2Manager.factory().owlClass(pizzaURI+"Eq3"));
		try {
			dp=KAON2Manager.factory().dataProperty(pizzaURI+"dpexactCardinality");
			dr = KAON2Manager.factory().dataRange("http://www.w3.org/2001/XMLSchema#string", new Namespaces());
			ddmD = KAON2Manager.factory().dataCardinality(DataCardinality.EXACT, 2, dp, KAON2Manager.factory().rdfsLiteral());
		} catch (KAON2Exception e) {
			e.printStackTrace();
		}
		cddmList.add(ddmD);
		app = KAON2Manager.factory().equivalentClasses(cddmList);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		
		//Equivalent classes
		//DataHasValue
		cddmList= new LinkedList<org.semanticweb.kaon2.api.owl.elements.Description>();
		cddmList.add(KAON2Manager.factory().owlClass(pizzaURI+"Eq3"));
		try {
			dp=KAON2Manager.factory().dataProperty(pizzaURI+"dpHasValue");
			ddmD = KAON2Manager.factory().dataHasValue(dp, KAON2Manager.factory().constant("Value"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		cddmList.add(ddmD);
		app = KAON2Manager.factory().equivalentClasses(cddmList);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//Equivalent classes
		//DataMaxCardinality
		cddmList= new LinkedList<org.semanticweb.kaon2.api.owl.elements.Description>();
		cddmList.add(KAON2Manager.factory().owlClass(pizzaURI+"Eq3"));
		try {
			dp=KAON2Manager.factory().dataProperty(pizzaURI+"dpmaxCardinality");
			dr = KAON2Manager.factory().dataRange("http://www.w3.org/2001/XMLSchema#string", new Namespaces());
			ddmD = KAON2Manager.factory().dataCardinality(DataCardinality.MAXIMUM, 5, dp, KAON2Manager.factory().rdfsLiteral());
		} catch (KAON2Exception e) {
			e.printStackTrace();
		}
		cddmList.add(ddmD);
		app = KAON2Manager.factory().equivalentClasses(cddmList);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//Equivalent classes
		//DataMinCardinality
		cddmList= new LinkedList<org.semanticweb.kaon2.api.owl.elements.Description>();
		cddmList.add(KAON2Manager.factory().owlClass(pizzaURI+"Eq3"));
		try {
			dp=KAON2Manager.factory().dataProperty(pizzaURI+"dpminCardinality");
			dr = KAON2Manager.factory().dataRange("http://www.w3.org/2001/XMLSchema#string", new Namespaces());
			ddmD = KAON2Manager.factory().dataCardinality(DataCardinality.MINIMUM,1, dp, KAON2Manager.factory().rdfsLiteral());
		} catch (KAON2Exception e) {
			e.printStackTrace();
		}
		cddmList.add(ddmD);
		app = KAON2Manager.factory().equivalentClasses(cddmList);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));

		//Equivalent classes
		//DataSomeValuesFrom
		cddmList= new LinkedList<org.semanticweb.kaon2.api.owl.elements.Description>();
		cddmList.add(KAON2Manager.factory().owlClass(pizzaURI+"Eq3"));
		dpes = new LinkedList<DataPropertyExpression>();
		try {
			dp=KAON2Manager.factory().dataProperty(pizzaURI+"dpSomeValuesFrom");
			dr = KAON2Manager.factory().dataRange("http://www.w3.org/2001/XMLSchema#string", new Namespaces());
			dpes.add(KAON2Manager.factory().dataProperty(dp.getURI()));
			ddmD = KAON2Manager.factory().dataSome(dr, dpes);
		} catch (KAON2Exception e) {
			e.printStackTrace();
		}
		cddmList.add(ddmD);
		app = KAON2Manager.factory().equivalentClasses(cddmList);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));

		//SubClassOf classes
		//ObjectAllValuesFrom
		op = KAON2Manager.factory().objectProperty(pizzaURI+"obObjectAllValuesFrom");
		owlClassIn = KAON2Manager.factory().owlClass(pizzaURI+"classObjectAllValuesFrom");
		ddmD = KAON2Manager.factory().objectAll(op, owlClassIn);

		sub = KAON2Manager.factory().owlClass(pizzaURI+"SubClassDescription1");
		app = KAON2Manager.factory().subClassOf(sub,ddmD);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));

		//SubClassOf classes
		//ObjectComplementOf
		owlClassIn = KAON2Manager.factory().owlClass(pizzaURI+"classObjectComplementOf");
		ddmD = KAON2Manager.factory().objectNot(owlClassIn);

		cddmList= new LinkedList<org.semanticweb.kaon2.api.owl.elements.Description>();
		cddmList.add(KAON2Manager.factory().owlClass(pizzaURI+"SubClassDescription1"));
		cddmList.add(ddmD);
		
		app = KAON2Manager.factory().equivalentClasses(cddmList);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));

		//SubClassOf classes
		//ObjectExactCardinality
		op = KAON2Manager.factory().objectProperty(pizzaURI+"obObjectExactCardinality");
		ddmD = KAON2Manager.factory().objectCardinality(ObjectCardinality.EXACT, 2, op, KAON2Manager.factory().owlClass(org.semanticweb.kaon2.api.owl.elements.OWLClass.OWL_THING));
		
		sub = KAON2Manager.factory().owlClass(pizzaURI+"SubClassDescription1");
		app = KAON2Manager.factory().subClassOf(sub,ddmD);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));

		//SubClassOf classes
		//ObjectExistsSelf ---------NOT SUPPORTED: OWL CORRUPT---------
		//op = KAON2Manager.factory().objectProperty(pizzaURI+"obObjectExistsSelf");
		//ddmD = KAON2Manager.factory().objectSelf(op);
		
		//cddmList= new LinkedList<org.semanticweb.kaon2.api.owl.elements.Description>();
		//cddmList.add(KAON2Manager.factory().owlClass(pizzaURI+"SubClassDescription1"));
		//cddmList.add(ddmD);
		
		//app = KAON2Manager.factory().equivalentClasses(cddmList);
		//changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//SubClassOf classes
		//ObjectHasValue
		op = KAON2Manager.factory().objectProperty(pizzaURI+"obObjectHasValue");
		in = KAON2Manager.factory().individual(pizzaURI+"indiv1");
		ddmD = KAON2Manager.factory().objectHasValue(op, in);
				
		sub = KAON2Manager.factory().owlClass(pizzaURI+"SubClassDescription1");
		app = KAON2Manager.factory().subClassOf(sub,ddmD);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//SubClassOf classes
		//ObjectIntersectionOf
		dpesD = new LinkedList<org.semanticweb.kaon2.api.owl.elements.Description>();
		dpesD.add(KAON2Manager.factory().owlClass(pizzaURI+"classObjectIntersectionOf1"));
		dpesD.add(KAON2Manager.factory().owlClass(pizzaURI+"classObjectIntersectionOf2"));
		ddmD = KAON2Manager.factory().objectAnd(dpesD);
		
		cddmList= new LinkedList<org.semanticweb.kaon2.api.owl.elements.Description>();
		cddmList.add(KAON2Manager.factory().owlClass(pizzaURI+"SubClassDescription1"));
		cddmList.add(ddmD);
		
		app = KAON2Manager.factory().equivalentClasses(cddmList);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//SubClassOf classes
		//ObjectMaxCardinality
		op = KAON2Manager.factory().objectProperty(pizzaURI+"obObjectMaxCardinality");
		ddmD = KAON2Manager.factory().objectCardinality(ObjectCardinality.MAXIMUM,5, op, KAON2Manager.factory().owlClass(org.semanticweb.kaon2.api.owl.elements.OWLClass.OWL_THING));
		
		sub = KAON2Manager.factory().owlClass(pizzaURI+"SubClassDescription1");
		app = KAON2Manager.factory().subClassOf(sub,ddmD);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//SubClassOf classes
		//ObjectMinCardinality
		op = KAON2Manager.factory().objectProperty(pizzaURI+"obObjectMinCardinality");
		ddmD = KAON2Manager.factory().objectCardinality(ObjectCardinality.MINIMUM,1, op, KAON2Manager.factory().owlClass(org.semanticweb.kaon2.api.owl.elements.OWLClass.OWL_THING));
		
		sub = KAON2Manager.factory().owlClass(pizzaURI+"SubClassDescription1");
		app = KAON2Manager.factory().subClassOf(sub,ddmD);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//SubClassOf classes
		//ObjectSomeValuesFrom
		op = KAON2Manager.factory().objectProperty(pizzaURI+"obObjectSomeValuesFrom");
		owlClassIn = KAON2Manager.factory().owlClass(pizzaURI+"classObjectSomeValuesFrom");
		ddmD = KAON2Manager.factory().objectSome(op, owlClassIn);

		sub = KAON2Manager.factory().owlClass(pizzaURI+"SubClassDescription1");
		app = KAON2Manager.factory().subClassOf(sub,ddmD);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));

		//SubClassOf classes
		//ObjectOneOf
		dpesIndiv = new LinkedList<org.semanticweb.kaon2.api.owl.elements.Individual>();
		dpesIndiv.add(KAON2Manager.factory().individual(pizzaURI+"indiv1"));
		dpesIndiv.add(KAON2Manager.factory().individual(pizzaURI+"indiv2"));
		ddmD = KAON2Manager.factory().objectOneOf(dpesIndiv);
		
		cddmList= new LinkedList<org.semanticweb.kaon2.api.owl.elements.Description>();
		cddmList.add(KAON2Manager.factory().owlClass(pizzaURI+"SubClassDescription1"));
		cddmList.add(ddmD);
		
		app = KAON2Manager.factory().equivalentClasses(cddmList);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
		//SubClassOf classes
		//ObjectUnionOf
		dpesD = new LinkedList<org.semanticweb.kaon2.api.owl.elements.Description>();
		dpesD.add(KAON2Manager.factory().owlClass(pizzaURI+"classObjectUnionOf1"));
		dpesD.add(KAON2Manager.factory().owlClass(pizzaURI+"classObjectUnionOf2"));
		ddmD = KAON2Manager.factory().objectOr(dpesD);
		
		cddmList= new LinkedList<org.semanticweb.kaon2.api.owl.elements.Description>();
		cddmList.add(KAON2Manager.factory().owlClass(pizzaURI+"SubClassDescription1"));
		cddmList.add(ddmD);
		
		app = KAON2Manager.factory().equivalentClasses(cddmList);
		changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
		
    }
    
    public void addManualErrors(){
//-----------CHANGES NOT SUPPORTED------------------
		
		Oyster2Connection oyster2Conn = StartRegistry.connection;//null;
		OMVPerson se = new OMVPerson();
		se.setFirstName("Raul");
		se.setLastName("Palma");
		se.setHasRole("http://omv.ontoware.org/2007/07/workflow#SubjectExpert");
		OMVOntology omvOnto = new OMVOntology();
		omvOnto.setURI("http://www.co-ode.org/ontologies/pizza/pizza_20041007.owl");
		omvOnto.setResourceLocator("");
		
		//(Declaration)dataType ----------NOT SUPPORTED: ERROR IN KAON2, NOT AVAILABLE IN NTK-----------------
		OMVAtomicChange atomicChange = new Addition();
		atomicChange.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
		atomicChange.setAppliedToOntology(omvOnto);
		atomicChange.addHasAuthor(se);
		
		Declaration declaration = new Declaration();

		declaration.setEntity(new Datatype(pizzaURI+"declaratedDatatype"));
		atomicChange.setAppliedAxiom(declaration);
		
		oyster2Conn.register(atomicChange);
		OWLOntologyChange classEntity = new OWLOntologyChange();
		
		classEntity = new AddDatatype();
		
		classEntity.setAppliedToOntology(omvOnto);
		classEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
		classEntity.addHasRelatedEntity(pizzaURI+"declaratedDatatype");
		classEntity.addHasAuthor(se);
		classEntity.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
		oyster2Conn.register(classEntity);
		
		//Disjoint DataProperty  ----------NOT SUPPORTED: ERROR IN KAON2, NOT AVAILABLE IN NTK-----------------
		
		atomicChange = new Addition();
		atomicChange.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
		atomicChange.setAppliedToOntology(omvOnto);
		atomicChange.addHasAuthor(se);
		DisjointDataProperties dPE = new DisjointDataProperties();
		dPE.addDataProperties(new DataProperty(pizzaURI+"dpDis1"));
		dPE.addDataProperties(new DataProperty(pizzaURI+"dpDis2"));
		atomicChange.setAppliedAxiom(dPE);

		oyster2Conn.register(atomicChange);
		OWLDataPropertyChange classEntity1 = new OWLDataPropertyChange();
		classEntity1 = new AddDisjointDataProperty();
		
		classEntity1.setAppliedToOntology(omvOnto);
		classEntity1.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
		classEntity1.addHasRelatedEntity(pizzaURI+"dpDis1"); //THIS IS PROBABLY ERROR (DIFFERENT FROM THE REST)
		classEntity1.addHasAuthor(se);
		classEntity1.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
		oyster2Conn.register(classEntity1);

		//Disjoint objectProperty ----------NOT SUPPORTED: ERROR IN KAON2, NOT AVAILABLE IN NTK-----------------
		
		atomicChange = new Addition();
		atomicChange.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
		atomicChange.setAppliedToOntology(omvOnto);
		atomicChange.addHasAuthor(se);
		DisjointObjectProperties oPE = new DisjointObjectProperties();
		oPE.addDisjointObjectProperties(new ObjectProperty(pizzaURI+"obDis1"));
		oPE.addDisjointObjectProperties(new ObjectProperty(pizzaURI+"obDis2"));
		atomicChange.setAppliedAxiom(oPE);


		oyster2Conn.register(atomicChange);
		OWLObjectPropertyChange classEntity2 = new OWLObjectPropertyChange();
		
		classEntity2 = new AddDisjointObjectProperty();
		
		classEntity2.setAppliedToOntology(omvOnto);
		classEntity2.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
		classEntity2.addHasRelatedEntity(pizzaURI+"obDis1"); //THIS IS PROBABLY ERROR (DIFFERENT FROM THE REST) 
		classEntity2.addHasAuthor(se);
		classEntity2.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
		oyster2Conn.register(classEntity2);

		//Negative DataProperty Assertion ----------NOT SUPPORTED: ERROR IN KAON2, NOT AVAILABLE IN NTK-----------------
		
		atomicChange = new Addition();
		atomicChange.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
		atomicChange.setAppliedToOntology(omvOnto);
		atomicChange.addHasAuthor(se);
		NegativeDataPropertyAssertion subDP = new NegativeDataPropertyAssertion();
		subDP.setDataProperty(new DataProperty(pizzaURI+"negDPAssertion"));
		subDP.setSourceIndividual(new Individual(pizzaURI+"indiv1"));
		subDP.setTargetValue(localURI+"negValue");
		atomicChange.setAppliedAxiom(subDP);
		
		oyster2Conn.register(atomicChange);
		
		OWLIndividualChange classEntity3 = new OWLIndividualChange();
		
		classEntity3 = new AddIndividualDataProperty();
					
		classEntity3.setAppliedToOntology(omvOnto);
		classEntity3.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
		classEntity3.addHasRelatedEntity(pizzaURI+"indiv1");
		classEntity3.addHasAuthor(se);
		classEntity3.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
		oyster2Conn.register(classEntity3);
		
		//Negative ObjectProperty Assertion ----------NOT SUPPORTED: ERROR IN KAON2, NOT AVAILABLE IN NTK-----------------
		
		atomicChange = new Addition();
		atomicChange.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
		atomicChange.setAppliedToOntology(omvOnto);
		atomicChange.addHasAuthor(se);
		
		NegativeObjectPropertyAssertion subDP1 = new NegativeObjectPropertyAssertion();
		subDP1.setObjectProperty(new ObjectProperty(pizzaURI+"negOBAssertion"));
		subDP1.setSourceIndividual(new Individual(pizzaURI+"indiv1"));
		subDP1.setTargetIndividual(new Individual(pizzaURI+"indiv7"));
		atomicChange.setAppliedAxiom(subDP1);
		
		oyster2Conn.register(atomicChange);
		
		OWLIndividualChange classEntity4 = new OWLIndividualChange();
		classEntity4 = new AddInvidivualObjectProperty();
					
		classEntity4.setAppliedToOntology(omvOnto);
		classEntity4.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
		classEntity4.addHasRelatedEntity(pizzaURI+"indiv1");
		classEntity4.addHasAuthor(se);
		classEntity4.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
		oyster2Conn.register(classEntity4);
		
		//ObjectExistsSelf ---------NOT SUPPORTED: OWL CORRUPT---------
		
		atomicChange = new Addition();
		atomicChange.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
		atomicChange.setAppliedToOntology(omvOnto);
		atomicChange.addHasAuthor(se);
		
		EquivalentClasses ec = new EquivalentClasses();
		ec.addEquivalentClasses(new org.neontoolkit.owlodm.api.Description.OWLClass(pizzaURI+"equivClassInSelf"));
		ObjectExistsSelf o = new ObjectExistsSelf();
		o.setObjectProperty(new ObjectProperty(pizzaURI+"ObExistSelf"));
		ec.addEquivalentClasses(o);
		atomicChange.setAppliedAxiom(ec);
		
		oyster2Conn.register(atomicChange);
		EquivalentClassChange classEntity5 = new EquivalentClassChange();
		classEntity5 = new AddEquivalentClass();
		
		classEntity5.setAppliedToOntology(omvOnto);
		classEntity5.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
		classEntity5.addHasRelatedEntity(pizzaURI+"equivClassInSelf");
		classEntity5.addHasAuthor(se);
		classEntity5.setDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(Calendar.getInstance().getTime()));
		oyster2Conn.register(classEntity5);

    }
}

/*Summary errors

--//Disjoint union classes (IGNORED IN KAON2, NOT AVAILABLE IN NTK)
//(Declaration)dataType ----------NOT SUPPORTED: ERROR IN KAON2, NOT AVAILABLE IN NTK-----------------
//Disjoint DataProperty  ----------NOT SUPPORTED: ERROR IN KAON2, NOT AVAILABLE IN NTK-----------------
//Disjoint objectProperty ----------NOT SUPPORTED: ERROR IN KAON2, NOT AVAILABLE IN NTK-----------------

--//Asymetric objectProperty ---IGNORED IN NTK-----------
--//Irreflexible objectProperty ---IGNORED IN NTK---------
--//Reflexible objectProperty -----------IGNORED IN NTK------------

//Negative DataProperty Assertion ----------NOT SUPPORTED: ERROR IN KAON2, NOT AVAILABLE IN NTK-----------------
//Negative ObjectProperty Assertion ----------NOT SUPPORTED: ERROR IN KAON2, NOT AVAILABLE IN NTK-----------------

//ObjectExistsSelf ---------NOT SUPPORTED: OWL CORRUPT---------

--=is created the log entry

*/