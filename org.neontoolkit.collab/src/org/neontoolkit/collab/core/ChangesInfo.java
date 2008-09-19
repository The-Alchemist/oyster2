package org.neontoolkit.collab.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.core.OMVPerson;
import org.neontoolkit.omv.api.extensions.change.OMVChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange;
import org.neontoolkit.registry.api.Oyster2Connection;
import org.neontoolkit.registry.api.Oyster2Manager;
import org.neontoolkit.workflow.api.Action;

public class ChangesInfo {

	public void showChanges(Oyster2Connection oyster2Conn, OMVOntology onto) {
		System.out.println("Last Change from Log: "
				+ oyster2Conn.getLastChangeIdFromLog(onto));
		
		
		List<OMVChange> changes = oyster2Conn.getChanges(onto, null);
		
		System.out.println("Changes for the specified ontology: ");
		System.out.println(Oyster2Manager.serializeOMVChanges(changes));

		this.getChanges(oyster2Conn, onto, null);
		this.getAtomicChanges(oyster2Conn, onto);		
		//this.getChanges(oyster2Conn, onto, "http://www.fao.org/aims/aos/fi/species_v1.0.owl?location=http://www.fao.org/aims/aos/fi/species_v1.0.owl;change=1C96E8F3A3E6967202EC4B0F930A78A76362A020");
		

		
		//this.getEntityState(oyster2Conn, onto, "http://www.fao.org/aims/aos/fi/species_v1.0.owl#Pet");

	}
	
	public void getOntologyState(Oyster2Connection oyster2Conn, OMVOntology onto){
		System.out.println("faoOntlogy state: "
				+ oyster2Conn.getOntologyState(onto));
	}
	
	public void getEntityState(Oyster2Connection oyster2Conn, OMVOntology onto, String entUri){
		System.out.println("state of entity "+entUri+" : "
				+ oyster2Conn.getEntityState(onto,entUri));
	}
	
	public void getAllEntityActions(Oyster2Connection oyster2Conn, OMVOntology onto){
		List<Action> actions = oyster2Conn.getEntityActionsHistory(onto,
				null);
		System.out.println("Entity actions for the specified ontology: ");
		System.out.println(Oyster2Manager.serializeActions(actions));
	}
	
	//"http://www.fao.org/aims/aos/fi/species_v1.0.owl?location=http://www.fao.org/aims/aos/fi/species_v1.0.owl;change=1C96E8F3A3E6967202EC4B0F930A78A76362A020
	public void getAnEntityActions(Oyster2Connection oyster2Conn, OMVOntology onto, String entUri){
		List<Action> actions = null;
		actions = oyster2Conn
				.getEntityActionsHistory(
						onto,
						entUri);
		System.out
				.println("Entity actions for the specified ontology since: ("+entUri+")");
		System.out.println(Oyster2Manager.serializeActions(actions));
	}
	
	public void getOntologyActions(Oyster2Connection oyster2Conn, OMVOntology onto){
		List<Action> actions = new ArrayList<Action>();
		Action ontologyAction = oyster2Conn.getOntologyAction(onto);
		System.out.println("Ontology action for the specified ontology: ");
		actions.clear();
		actions.add(ontologyAction);
		System.out.println(Oyster2Manager.serializeActions(actions));
	}
	
	public List<OMVChange> getChanges(Oyster2Connection oyster2Conn, OMVOntology onto, String fromChange){
		List<OMVChange> changes = new ArrayList<OMVChange>();
		changes.addAll(oyster2Conn.getChanges(onto,	fromChange));
		System.out
				.println("Changes for the specified ontology since: ("+fromChange+")");
		System.out.println(Oyster2Manager.serializeOMVChanges(changes));
		for(OMVChange change : changes){
			System.out.println("change : "+change.getURI()+", "+change.getDate());
		}
		return changes;
	}
	
	public static List<OMVChange> getEntityChanges(Oyster2Connection oyster2Conn, OMVOntology onto, String fromChange){
		List<OMVChange> changes = new ArrayList<OMVChange>();
		changes.addAll(oyster2Conn.getChanges(onto,	new OMVEntityChange(), fromChange));
		System.out.println("Changes for the specified ontology of type EntityChange since: ("+fromChange+")");
		System.out.println(Oyster2Manager.serializeOMVChanges(changes));
		return changes;
	}
	
	public void isTracked(Oyster2Connection oyster2Conn, OMVOntology onto){
		System.out.println("ontologies that are being tracked");
		System.out.println(Oyster2Manager.serializeOMVOntologies(oyster2Conn.getOntologiesTrackedByPeer()));
		System.out.println("tracking onto: "+oyster2Conn.isTracked(onto));
		
		Set<OMVOntology> trackedOntologies = oyster2Conn
		.getOntologiesWithChanges();
System.out.println("Tracked ontologies: ");
System.out.println(Oyster2Manager
		.serializeOMVOntologies(trackedOntologies));
	}
	
	public void getChangesIds(Oyster2Connection oyster2Conn, OMVOntology onto){
		Set<String> ids = oyster2Conn.getChangesIds(onto);
		Iterator idT = ids.iterator();
		while (idT.hasNext()) {
			System.out.println("change id: " + (String) idT.next());
		}
	}

	public void getAtomicChanges(Oyster2Connection oyster2Conn, OMVOntology onto){
		Set<OMVChange> changes=oyster2Conn.getChanges(onto, new OMVEntityChange(), null);
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
	
	public void sendToBeApprovedChanges(Oyster2Connection oyster2Conn, 
			OMVOntology onto, OMVPerson person){
		Set<OMVChange> changes=oyster2Conn.getChanges(onto, new OMVEntityChange(), null);
		Iterator it = changes.iterator();
		while (it.hasNext()){
			OMVChange t = (OMVChange)it.next();
			oyster2Conn.submitToBeApproved(t.getURI(),person);
		}
	}
	
	public void removeChange(Oyster2Connection oyster2Conn, String changeURI){
		OMVChange t = new OMVChange();
		//"http://www.fao.org/aims/aos/fi/species_v1.0.owl?location=http://www.fao.org/aims/aos/fi/species_v1.0.owl;change=C8468AE1ACC5E8B034DACEB8C0A2CC2102435C1E"
		t.setURI(changeURI);
		oyster2Conn.remove(t);
	}
	
	public void addChange(){
		
	}
}
