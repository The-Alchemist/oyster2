package org.neon_toolkit.registry.oyster2.tests;



import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.neon_toolkit.omv.api.core.OMVOntology;
import org.neon_toolkit.omv.api.core.OMVPerson;
import org.neon_toolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLOntologyChange.AddObjectProperty;
import org.neon_toolkit.omv.api.extensions.change.OMVChange;
import org.neon_toolkit.omv.api.extensions.change.OMVChangeSpecification;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVEntityChange;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange.Addition;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange.Removal;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVCompositeChange.MoveClass;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.ClassChange.SubClassOfChange.AddSubClassOf;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.ClassChange.SubClassOfChange.RemoveSubClassOf;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.OntologyChange.AddClass;
import org.neon_toolkit.omv.api.extensions.mapping.OMVMapping;
import org.neon_toolkit.omv.api.extensions.mapping.OMVMappingMethod;
import org.neon_toolkit.owlodm.api.Axiom.Declaration;
import org.neon_toolkit.owlodm.api.Axiom.ClassAxiom.SubClassOf;
import org.neon_toolkit.owlodm.api.Axiom.ObjectPropertyAxiom.ObjectPropertyDomain;
import org.neon_toolkit.owlodm.api.Axiom.ObjectPropertyAxiom.ObjectPropertyRange;
import org.neon_toolkit.registry.api.Oyster2Connection;
import org.neon_toolkit.registry.api.Oyster2Manager;
import org.neon_toolkit.registry.oyster2.Constants;
import org.neon_toolkit.workflow.api.Action;
import org.neon_toolkit.owlodm.api.Description.OWLClass;
import org.neon_toolkit.owlodm.api.Description.ObjectMaxCardinality;
import org.neon_toolkit.owlodm.api.OWLEntity.ObjectProperty;


public class ChangeTests {
	
	public static void main(String[] args)throws Exception{
        
		
		//NEW CONNECTION
		Oyster2Manager.setSimplePeer(true);
		Oyster2Manager.setWorkflowSupport(true);
		Oyster2Manager.setLogEnabled(true);
		Oyster2Connection oyster2Conn = Oyster2Manager.newConnection(true);
		
		if (oyster2Conn==null) shutdown();
		
		//EXAMPLE ONTOLOGY
		//oyster2Conn.importOntology("http://www.fao.org/aims/aos/fi/species_v1.0.owl");
		OMVOntology faoOntology = new OMVOntology();
		faoOntology.setURI("http://www.fao.org/aims/aos/fi/species_v1.0.owl");
		faoOntology.setResourceLocator("http://www.fao.org/aims/aos/fi/species_v1.0.owl");
		faoOntology.setNumberOfAxioms(10);
		
		//EXAMPLE EDITORS
		OMVPerson va = new OMVPerson();
		va.setFirstName("Marta");
		va.setLastName("Iglesias");
		va.setHasRole(Constants.Validator);
		
		OMVPerson se = new OMVPerson();
		se.setFirstName("Caterina");
		se.setLastName("Catarricio");
		se.setHasRole(Constants.SubjectExpert);
		
		//SCENARIOS
		if (args[0].equalsIgnoreCase("14")){
			oyster2Conn.syncrhonizeChangesWithKnownPeersNow();
			Thread.sleep(60000);
		}
		if (args[0].equalsIgnoreCase("13")){
			oyster2Conn.startTracking(faoOntology);
			oyster2Conn.startDiscoveryComponent();
			Thread.sleep(90000);
			oyster2Conn.stopDiscoveryComponent();
			Thread.sleep(90000);
		}
		if (args[0].equalsIgnoreCase("12")){
			oyster2Conn.startTracking(faoOntology);
		}
		if (args[0].equalsIgnoreCase("11")){
			//ADD Other objects
			OMVPerson t1= new OMVPerson();
			t1.setFirstName("John");
			t1.setLastName("becker");
			t1.addEmail("jbecker@superhost.com");
			t1.addEmail("jbeckersuper@thehost.com");
			t1.addEmail("jbhost@temphost.com");
			t1.setHasRole(Constants.SubjectExpert);
			oyster2Conn.replace(t1);
		}
		
		if (args[0].equalsIgnoreCase("10")){
			oyster2Conn.importOntology("f:\\localRegistryWatson.owl");
		}
		if (args[0].equalsIgnoreCase("9")){
			
			oyster2Conn.importOntology("http://omv.ontoware.org/2007/07/workflow");
			oyster2Conn.importOntology("http://omv.ontoware.org/2007/10/changes");
			oyster2Conn.importOntology("http://oyster2.ontoware.org/dmozT.rdf");
			oyster2Conn.importOntology("http://owlodm.ontoware.org/OWL1.1");
			oyster2Conn.importOntology("http://ontoware.org/frs/download.php/354/swrc_updated_v0.7.1.owl");
			oyster2Conn.importOntology("http://omv.ontoware.org/2007/07/OWLChanges");
			oyster2Conn.importOntology("http://omv.ontoware.org/2005/05/ontology");
			oyster2Conn.importOntology("http://omv.ontoware.org/2007/05/pomv");
			
			
			OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mMethod=new OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter();
			mMethod.setID("superFilter");
			
			OMVOntology newOnto1 = new OMVOntology();
			newOnto1.setURI("http://omv.ontoware.org/2007/07/OWLChanges");
			OMVMapping nMapping = new OMVMapping();
			nMapping.setURI("http://mydomain.com/map1.rdf");
			nMapping.setCreationDate("20/3/2007");
			nMapping.setLevel("draft");
			nMapping.setProcessingTime(5.33);
			nMapping.addHasCreator(se);
			nMapping.setHasSourceOntology(faoOntology);
			nMapping.setHasTargetOntology(newOnto1);
			nMapping.setUsedMethod(mMethod);
			oyster2Conn.replace(nMapping);
			
			OMVOntology oSource=new OMVOntology();
			oSource.setURI("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#");
			OMVMapping nMapping1 = new OMVMapping();
			nMapping1.setURI("http://mydomain.com/map2.rdf");
			nMapping1.setCreationDate("25/11/2006");
			nMapping1.setLevel("final");
			nMapping1.setProcessingTime(2.95);
			nMapping1.addHasCreator(va);
			nMapping1.setHasSourceOntology(oSource);
			nMapping1.setHasTargetOntology(faoOntology);
			nMapping1.setUsedMethod(mMethod);
			oyster2Conn.replace(nMapping1);

		}
		if (args[0].equalsIgnoreCase("8")){
			System.out.println("ontologies that are being tracked");
			System.out.println(Oyster2Manager.serializeOMVOntologies(oyster2Conn.getOntologiesTrackedByPeer()));
			System.out.println("tracking faoOntology: "+oyster2Conn.isTracked(faoOntology));
		}
		
		if (args[0].equalsIgnoreCase("7")){
			oyster2Conn.stopTracking(faoOntology);
		}
		
		if (args[0].equalsIgnoreCase("6")){
			oyster2Conn.startTracking(faoOntology);
			oyster2Conn.syncrhonizeChangesWithKnownPeersNow();
			Thread.sleep(60000);
		}
		
		if (args[0].equalsIgnoreCase("5")){
			Set<OMVChange> changes=oyster2Conn.getChanges(faoOntology, new OMVEntityChange(), null);
			Iterator it = changes.iterator();
			System.out.println("Atomic Changes: ");
			while (it.hasNext()){
				OMVChange t = (OMVChange)it.next();
				Set<OMVAtomicChange> changesIn=oyster2Conn.getAtomicChanges((OMVEntityChange)t);
				List<OMVChange> temp= new LinkedList<OMVChange>();
				temp.addAll(changesIn);
				System.out.println(Oyster2Manager.serializeOMVChanges(temp));
			}
			
		}
		
		if (args[0].equalsIgnoreCase("4")){
			Set<OMVChange> changes=oyster2Conn.getChanges(faoOntology, new OMVEntityChange(), null);
			Iterator it = changes.iterator();
			while (it.hasNext()){
				OMVChange t = (OMVChange)it.next();
				oyster2Conn.submitToBeApproved(t.getURI(),se);
			}
		}
		
		if (args[0].equalsIgnoreCase("3")){
			OMVChangeSpecification specDel = new OMVChangeSpecification();
			specDel.setChangeFromVersion(faoOntology);
			oyster2Conn.remove(specDel);
		}
		
		if (args[0].equalsIgnoreCase("15")){
			OMVChange t = new OMVChange();
			t.setURI("http://www.fao.org/aims/aos/fi/species_v1.0.owl?location=http://www.fao.org/aims/aos/fi/species_v1.0.owl;change=C8468AE1ACC5E8B034DACEB8C0A2CC2102435C1E");
			oyster2Conn.remove(t);
		}
		
		if (args[0].equalsIgnoreCase("2")){
			System.out.println ("Last Change from Log: "+oyster2Conn.getLastChangeIdFromLog(faoOntology));
			List<OMVChange> changes=oyster2Conn.getChanges(faoOntology, null);
			System.out.println("Changes for the specified ontology: ");
			System.out.println(Oyster2Manager.serializeOMVChanges(changes));
			
			changes.clear();
			changes.addAll(oyster2Conn.getChanges(faoOntology, "http://www.fao.org/aims/aos/fi/species_v1.0.owl?location=http://www.fao.org/aims/aos/fi/species_v1.0.owl;change=1C96E8F3A3E6967202EC4B0F930A78A76362A020"));
			System.out.println("Changes for the specified ontology since: (http://www.fao.org/aims/aos/fi/species_v1.0.owl?location=http://www.fao.org/aims/aos/fi/species_v1.0.owl;change=1C96E8F3A3E6967202EC4B0F930A78A76362A020)");
			System.out.println(Oyster2Manager.serializeOMVChanges(changes));
			
			changes.clear();
			changes.addAll(oyster2Conn.getChanges(faoOntology, new OMVEntityChange(),null));
			System.out.println("Changes for the specified ontology of type EntityChange: ");
			System.out.println(Oyster2Manager.serializeOMVChanges(changes));
		
			changes.clear();
			changes.addAll(oyster2Conn.getChanges(faoOntology, new OMVEntityChange(), "http://www.fao.org/aims/aos/fi/species_v1.0.owl?location=http://www.fao.org/aims/aos/fi/species_v1.0.owl;change=1C96E8F3A3E6967202EC4B0F930A78A76362A020"));
			System.out.println("Changes for the specified ontology of type EntityChange since: (http://www.fao.org/aims/aos/fi/species_v1.0.owl?location=http://www.fao.org/aims/aos/fi/species_v1.0.owl;change=1C96E8F3A3E6967202EC4B0F930A78A76362A020)");
			System.out.println(Oyster2Manager.serializeOMVChanges(changes));
			
			Set<OMVOntology> trackedOntologies = oyster2Conn.getOntologiesWithChanges();
			System.out.println("Tracked ontologies: ");
			System.out.println(Oyster2Manager.serializeOMVOntologies(trackedOntologies));
			
			List<Action> actions=oyster2Conn.getEntityActionsHistory(faoOntology,null);
			System.out.println("Entity actions for the specified ontology: ");
			System.out.println(Oyster2Manager.serializeActions(actions));
			
			actions.clear();
			actions=oyster2Conn.getEntityActionsHistory(faoOntology, "http://www.fao.org/aims/aos/fi/species_v1.0.owl?location=http://www.fao.org/aims/aos/fi/species_v1.0.owl;change=1C96E8F3A3E6967202EC4B0F930A78A76362A020");
			System.out.println("Entity actions for the specified ontology since: (http://www.fao.org/aims/aos/fi/species_v1.0.owl?location=http://www.fao.org/aims/aos/fi/species_v1.0.owl;change=1C96E8F3A3E6967202EC4B0F930A78A76362A020)");
			System.out.println(Oyster2Manager.serializeActions(actions));
			
			Action ontologyAction=oyster2Conn.getOntologyAction(faoOntology);
			System.out.println("Ontology action for the specified ontology: ");
			actions.clear();
			actions.add(ontologyAction);
			System.out.println(Oyster2Manager.serializeActions(actions));
			
			System.out.println("faoOntlogy state: "+oyster2Conn.getOntologyState(faoOntology));
			System.out.println("dog state: "+oyster2Conn.getEntityState(faoOntology,"http://www.fao.org/aims/aos/fi/species_v1.0.owl#Dog"));
			System.out.println("pet state: "+oyster2Conn.getEntityState(faoOntology,"http://www.fao.org/aims/aos/fi/species_v1.0.owl#Pet"));
			System.out.println("monster state: "+oyster2Conn.getEntityState(faoOntology,"http://www.fao.org/aims/aos/fi/species_v1.0.owl#Monster"));
			System.out.println("fears state: "+oyster2Conn.getEntityState(faoOntology,"http://www.fao.org/aims/aos/fi/species_v1.0.owl#fears"));
			
			Set<String> ids = oyster2Conn.getChangesIds(faoOntology);
			Iterator idT=ids.iterator();
			while (idT.hasNext()){
				System.out.println("change id: "+(String)idT.next());
			}
			//oyster2Conn.syncrhonizeChangesWithKnownPeersNow();
			//Thread.sleep(60000);

		}
		
		if (args[0].equalsIgnoreCase("1")){
			oyster2Conn.startTracking(faoOntology);
		//Lets add a class
		//First axiom change
		System.out.println("registering example...");
		Declaration newClass = new Declaration();
		newClass.setEntity(new OWLClass("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Monster"));
		Addition addClassAxiom = new Addition();
		addClassAxiom.setAppliedAxiom(newClass);
		addClassAxiom.setAppliedToOntology(faoOntology);
		oyster2Conn.register(addClassAxiom);
		//Then entity change (optionally)
		AddClass newClassEntity = new AddClass();
		newClassEntity.setAppliedToOntology(faoOntology);
		newClassEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
		newClassEntity.addHasRelatedEntity("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Monster");
		newClassEntity.addHasAuthor(se);
		oyster2Conn.register(newClassEntity);
		
		//Lets add another class
		//First axiom change
		Declaration newClass1 = new Declaration();
		newClass1.setEntity(new OWLClass("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Dog"));
		Addition addClassAxiom1 = new Addition();
		addClassAxiom1.setAppliedAxiom(newClass1);
		addClassAxiom1.setAppliedToOntology(faoOntology);
		oyster2Conn.register(addClassAxiom1);
		//Then entity change (optionally)
		AddClass newClassEntity1 = new AddClass();
		newClassEntity1.setAppliedToOntology(faoOntology);
		newClassEntity1.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
		newClassEntity1.addHasRelatedEntity("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Dog");
		newClassEntity1.addHasAuthor(se);
		oyster2Conn.register(newClassEntity1);
		
		//Lets add a subclass
		//First axiom changes
		Declaration newClass2 = new Declaration();
		newClass2.setEntity(new OWLClass("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Pet"));
		Addition addClassAxiom2 = new Addition();
		addClassAxiom2.setAppliedAxiom(newClass2);
		addClassAxiom2.setAppliedToOntology(faoOntology);
		oyster2Conn.register(addClassAxiom2);
		String ch1=oyster2Conn.getLastChangeId();
		
		SubClassOf sco = new SubClassOf();
		sco.setSubClass(new OWLClass("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Pet"));
		sco.setSuperClass(new OWLClass("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Monster"));
		Addition addSubClassOf = new Addition();
		addSubClassOf.setAppliedAxiom(sco);
		addSubClassOf.setAppliedToOntology(faoOntology);
		oyster2Conn.register(addSubClassOf);
		String ch2=oyster2Conn.getLastChangeId();
		
		//Then entity change (optionally)
		AddClass newClassEntity2 = new AddClass();
		newClassEntity2.setAppliedToOntology(faoOntology);
		newClassEntity2.addConsistsOfAtomicOperation(ch1);
		newClassEntity2.addConsistsOfAtomicOperation(ch2);
		newClassEntity2.addHasRelatedEntity("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Pet");
		newClassEntity2.addHasAuthor(se);
		oyster2Conn.register(newClassEntity2);
		
		//Lets add an object property with domain and range
		//First axiom changes
		
		Declaration newObjectProperty = new Declaration();
		newObjectProperty.setEntity(new ObjectProperty("http://www.fao.org/aims/aos/fi/species_v1.0.owl#fears"));
		Addition addObjectProperty = new Addition();
		addObjectProperty.setAppliedAxiom(newObjectProperty);
		addObjectProperty.setAppliedToOntology(faoOntology);
		oyster2Conn.register(addObjectProperty);
		String c1=oyster2Conn.getLastChangeId();
		
		ObjectPropertyDomain pD = new ObjectPropertyDomain();
		pD.setObjectProperty(new ObjectProperty("http://www.fao.org/aims/aos/fi/species_v1.0.owl#fears"));
		pD.setDomain(new OWLClass("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Dog"));
		Addition addObjectPropertyDomain = new Addition();
		addObjectPropertyDomain.setAppliedAxiom(pD);
		addObjectPropertyDomain.setAppliedToOntology(faoOntology);
		oyster2Conn.register(addObjectPropertyDomain);
		String c2=oyster2Conn.getLastChangeId();
		
		ObjectPropertyRange pR = new ObjectPropertyRange();
		pR.setObjectProperty(new ObjectProperty("http://www.fao.org/aims/aos/fi/species_v1.0.owl#fears"));
		pR.setRange(new OWLClass("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Monster"));
		Addition addObjectPropertyRange = new Addition();
		addObjectPropertyRange.setAppliedAxiom(pR);
		addObjectPropertyRange.setAppliedToOntology(faoOntology);
		oyster2Conn.register(addObjectPropertyRange);
		String c3=oyster2Conn.getLastChangeId();
		
		//Then entity change (optionally)
		AddObjectProperty newObjectPropertyChange = new AddObjectProperty();
		newObjectPropertyChange.setAppliedToOntology(faoOntology);
		newObjectPropertyChange.addConsistsOfAtomicOperation(c1);
		newObjectPropertyChange.addConsistsOfAtomicOperation(c2);
		newObjectPropertyChange.addConsistsOfAtomicOperation(c3);
		newObjectPropertyChange.addHasRelatedEntity("http://www.fao.org/aims/aos/fi/species_v1.0.owl#fears");
		newObjectPropertyChange.addHasAuthor(se);
		oyster2Conn.register(newObjectPropertyChange);

		//Lets add a composite change (move class)
		//First removal changes (axiom & entity) i.e. entity changes (optionally, just to track the entities states)
		SubClassOf sco1 = new SubClassOf();
		sco1.setSubClass(new OWLClass("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Pet"));
		sco1.setSuperClass(new OWLClass("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Monster"));
		Removal removeSubClassOf = new Removal();
		removeSubClassOf.setAppliedAxiom(sco1);
		removeSubClassOf.setAppliedToOntology(faoOntology);
		oyster2Conn.register(removeSubClassOf);
		String change1=oyster2Conn.getLastChangeId();
		
		RemoveSubClassOf newRemoveSubClassOf = new RemoveSubClassOf();
		newRemoveSubClassOf.setAppliedToOntology(faoOntology);
		newRemoveSubClassOf.addConsistsOfAtomicOperation(change1);
		newRemoveSubClassOf.addHasRelatedEntity("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Pet");
		newRemoveSubClassOf.addHasAuthor(se);
		oyster2Conn.register(newRemoveSubClassOf);
		String change3=oyster2Conn.getLastChangeId();
		
		//Then addition changes (axiom & entity) i.e. entity changes (optionally, just to track the entities states)
		SubClassOf sco2 = new SubClassOf();
		sco2.setSubClass(new OWLClass("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Pet"));
		sco2.setSuperClass(new OWLClass("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Dog"));
		Addition addSubClassOf1 = new Addition();
		addSubClassOf1.setAppliedAxiom(sco2);
		addSubClassOf1.setAppliedToOntology(faoOntology);
		oyster2Conn.register(addSubClassOf1);
		String change2=oyster2Conn.getLastChangeId();
			
		AddSubClassOf newSubClassOf = new AddSubClassOf();
		newSubClassOf.setAppliedToOntology(faoOntology);
		newSubClassOf.addConsistsOfAtomicOperation(change2);
		newSubClassOf.addHasRelatedEntity("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Pet");
		newSubClassOf.addHasAuthor(se);
		oyster2Conn.register(newSubClassOf);
		String change4=oyster2Conn.getLastChangeId();
		
		//Then the composite change
		MoveClass newMoveClass = new MoveClass();
		newMoveClass.setAppliedToOntology(faoOntology);
		newMoveClass.addConsistsOf(change3);
		newMoveClass.addConsistsOf(change4);
		oyster2Conn.register(newMoveClass);
		
		
		//Lets add a subclass complex
		//First axiom changes
		
		ObjectMaxCardinality omc= new ObjectMaxCardinality();
		omc.setCardinality(5);
		omc.setObjectProperty(new ObjectProperty("http://www.fao.org/aims/aos/fi/species_v1.0.owl#fears"));
		omc.setOWLClass(new OWLClass("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Pet"));
		
		Declaration newClass5 = new Declaration();
		newClass5.setEntity(omc);
		Addition addClassAxiom5 = new Addition();
		addClassAxiom5.setAppliedAxiom(newClass5);
		addClassAxiom5.setAppliedToOntology(faoOntology);
		oyster2Conn.register(addClassAxiom5);
		String ch5=oyster2Conn.getLastChangeId();
		
		SubClassOf sco5 = new SubClassOf();
		sco5.setSubClass(omc);
		sco5.setSuperClass(new OWLClass("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Pet"));
		Addition addSubClassOf5 = new Addition();
		addSubClassOf5.setAppliedAxiom(sco5);
		addSubClassOf5.setAppliedToOntology(faoOntology);
		oyster2Conn.register(addSubClassOf5);
		String ch6=oyster2Conn.getLastChangeId();
		
		//Then entity change (optionally)
		AddSubClassOf newClassEntity5 = new AddSubClassOf();
		newClassEntity5.setAppliedToOntology(faoOntology);
		newClassEntity5.addConsistsOfAtomicOperation(ch5);
		newClassEntity5.addConsistsOfAtomicOperation(ch6);
		newClassEntity5.addHasRelatedEntity("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Pet");
		newClassEntity5.addHasAuthor(se);
		oyster2Conn.register(newClassEntity5);
		
		//Specify the next version of the ontology
		OMVOntology faoOntology_v2 = new OMVOntology();
		faoOntology_v2.setURI("http://www.fao.org/aims/aos/fi/species_v2.0.owl");
		faoOntology_v2.setResourceLocator("http://www.fao.org/aims/aos/fi/species_v2.0.owl");
		
		OMVChangeSpecification spec = new OMVChangeSpecification();
		spec.setChangeFromVersion(faoOntology);
		spec.setChangeToVersion(faoOntology_v2);
		oyster2Conn.replace(spec);
		}
		//CLOSE CONNECTION
		Oyster2Manager.closeConnection();
		shutdown();
    }
	
	private static void shutdown(){
		System.exit(0);
	}
}
	
