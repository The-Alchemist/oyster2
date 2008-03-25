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
import org.neon_toolkit.owlodm.api.Axiom.Declaration;
import org.neon_toolkit.owlodm.api.Axiom.ClassAxiom.SubClassOf;
import org.neon_toolkit.owlodm.api.Axiom.ObjectPropertyAxiom.ObjectPropertyDomain;
import org.neon_toolkit.owlodm.api.Axiom.ObjectPropertyAxiom.ObjectPropertyRange;
import org.neon_toolkit.registry.api.Oyster2Connection;
import org.neon_toolkit.registry.api.Oyster2Manager;
import org.neon_toolkit.registry.oyster2.Constants;


public class ChangeTests {
	
	public static void main(String[] args)throws Exception{
        
		
		//NEW CONNECTION
		Oyster2Manager.setSimplePeer(true);
		Oyster2Manager.setWorkflowSupport(true);
		Oyster2Connection oyster2Conn = Oyster2Manager.newConnection(true);
		
		//EXAMPLE ONTOLOGY
		//oyster2Conn.importOntology("http://www.fao.org/aims/aos/fi/species_v1.0.owl");
		OMVOntology faoOntology = new OMVOntology();
		faoOntology.setURI("http://www.fao.org/aims/aos/fi/species_v1.0.owl");
		faoOntology.setResourceLocator("http://www.fao.org/aims/aos/fi/species_v1.0.owl");
		
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
		if (args[0].equalsIgnoreCase("5")){
			Set<OMVChange> changes=oyster2Conn.getChanges(faoOntology, new OMVEntityChange());
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
			Set<OMVChange> changes=oyster2Conn.getChanges(faoOntology, new OMVEntityChange());
			Iterator it = changes.iterator();
			while (it.hasNext()){
				OMVChange t = (OMVChange)it.next();
				oyster2Conn.submitToBeApproved(t.getURI(),null);
			}
		}
		
		if (args[0].equalsIgnoreCase("3")){
			OMVChangeSpecification specDel = new OMVChangeSpecification();
			specDel.setChangeFromVersion(faoOntology);
			oyster2Conn.remove(specDel);
		}
		
		
		if (args[0].equalsIgnoreCase("2")){
			List<OMVChange> changes=oyster2Conn.getChanges(faoOntology);
			System.out.println("Changes for the specified ontology: ");
			System.out.println(Oyster2Manager.serializeOMVChanges(changes));
		
			changes.clear();
			changes.addAll(oyster2Conn.getChanges(faoOntology, new OMVEntityChange()));
			System.out.println("Changes for the specified ontology: ");
			System.out.println(Oyster2Manager.serializeOMVChanges(changes));
		
			Set<OMVOntology> trackedOntologies = oyster2Conn.getOntologiesWithChanges();
			System.out.println("Tracked ontologies: ");
			System.out.println(Oyster2Manager.serializeOMVOntologies(trackedOntologies));
		}
		
		if (args[0].equalsIgnoreCase("1")){
		//Lets add a class
		//First axiom change
		System.out.println("registering example...");
		Declaration newClass = new Declaration();
		newClass.setEntity("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Monster");
		Addition addClassAxiom = new Addition();
		addClassAxiom.setAppliedAxiom(newClass);
		addClassAxiom.setAppliedToOntology(faoOntology);
		oyster2Conn.register(addClassAxiom);
		//Then entity change (optionally)
		AddClass newClassEntity = new AddClass();
		newClassEntity.setAppliedToOntology(faoOntology);
		newClassEntity.addConsistsOfAtomicOperation(oyster2Conn.getLastChangeId());
		newClassEntity.addHasRelatedEntity("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Monster");
		newClassEntity.addHasAuthor(va);
		oyster2Conn.register(newClassEntity);
		
		//Lets add another class
		//First axiom change
		Declaration newClass1 = new Declaration();
		newClass1.setEntity("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Dog");
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
		newClass2.setEntity("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Pet");
		Addition addClassAxiom2 = new Addition();
		addClassAxiom2.setAppliedAxiom(newClass2);
		addClassAxiom2.setAppliedToOntology(faoOntology);
		oyster2Conn.register(addClassAxiom2);
		String ch1=oyster2Conn.getLastChangeId();
		
		SubClassOf sco = new SubClassOf();
		sco.setSubClass("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Pet");
		sco.setSuperClass("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Monster");
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
		newObjectProperty.setEntity("http://www.fao.org/aims/aos/fi/species_v1.0.owl#fears");
		Addition addObjectProperty = new Addition();
		addObjectProperty.setAppliedAxiom(newObjectProperty);
		addObjectProperty.setAppliedToOntology(faoOntology);
		oyster2Conn.register(addObjectProperty);
		String c1=oyster2Conn.getLastChangeId();
		
		ObjectPropertyDomain pD = new ObjectPropertyDomain();
		pD.setObjectProperty("http://www.fao.org/aims/aos/fi/species_v1.0.owl#fears");
		pD.setDomain("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Dog");
		Addition addObjectPropertyDomain = new Addition();
		addObjectPropertyDomain.setAppliedAxiom(pD);
		addObjectPropertyDomain.setAppliedToOntology(faoOntology);
		oyster2Conn.register(addObjectPropertyDomain);
		String c2=oyster2Conn.getLastChangeId();
		
		ObjectPropertyRange pR = new ObjectPropertyRange();
		pR.setObjectProperty("http://www.fao.org/aims/aos/fi/species_v1.0.owl#fears");
		pR.setRange("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Monster");
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
		newObjectPropertyChange.addHasAuthor(va);
		oyster2Conn.register(newObjectPropertyChange);

		//Lets add a composite change (move class)
		//First removal changes (axiom & entity) i.e. entity changes (optionally, just to track the entities states)
		SubClassOf sco1 = new SubClassOf();
		sco1.setSubClass("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Pet");
		sco1.setSuperClass("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Monster");
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
		sco2.setSubClass("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Pet");
		sco2.setSuperClass("http://www.fao.org/aims/aos/fi/species_v1.0.owl#Dog");
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
	
