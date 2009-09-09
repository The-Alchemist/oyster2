package org.neontoolkit.registry.api.change;


import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.extensions.change.OMVChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVCompositeChange;
import org.neontoolkit.registry.api.individuals.ProcessOMVIndividuals;
import org.neontoolkit.registry.api.workflow.WorkflowManagement;
import org.neontoolkit.registry.core.AdvertInformer;
import org.neontoolkit.registry.oyster2.Constants;
import org.neontoolkit.registry.oyster2.Oyster2Factory;
import org.neontoolkit.workflow.api.Action;
import org.neontoolkit.workflow.api.Action.EntityAction;
import org.neontoolkit.workflow.api.Action.EntityAction.Delete;
import org.neontoolkit.workflow.api.Action.EntityAction.Insert;
import org.neontoolkit.workflow.api.Action.EntityAction.RejectToApproved;
import org.neontoolkit.workflow.api.Action.EntityAction.RejectToBeApproved;
import org.neontoolkit.workflow.api.Action.EntityAction.RejectToDraft;
import org.neontoolkit.workflow.api.Action.EntityAction.SendToApproved;
import org.neontoolkit.workflow.api.Action.EntityAction.SendToBeApproved;
import org.neontoolkit.workflow.api.Action.EntityAction.SendToBeDeleted;
import org.neontoolkit.workflow.api.Action.EntityAction.Update;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.owl.elements.Individual;
import org.semanticweb.kaon2.api.owl.elements.ObjectProperty;

/**
 * The class ChangeSynchronization provides the methods to support the
 * synchronization of the changes (and workflow actions if enabled) between
 * peers 
 * @author Raul Palma
 * @version 2.3.3, August 2009
 */
public class ChangeSynchronization {
	static Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private static ChangeManagement cMgmt = new ChangeManagement();
	private static WorkflowManagement wMgmt= new WorkflowManagement();
	private static AdvertInformer localInformer = mOyster2.getLocalAdvertInformer();
	
	
	public ChangeSynchronization()
    {
	
    }
	
	public synchronized static void SynchronizeChanges(){
		ObjectProperty trackOntology = KAON2Manager.factory().objectProperty(Constants.POMVURI + Constants.trackOntology);
		Collection peerList = localInformer.getPeerList(localInformer.getLocalRegistry());
		Ontology localRegistry = localInformer.getLocalRegistry();
		Ontology remoteRegistry = null;
		String IP= "";
		mOyster2.getLogger().info("starting change synchronization...");
		Collection ontologySetLocal = localInformer.getTrackedOntologies(localRegistry,localInformer.getLocalPeer());
		Ontology pushRegistry = null;
		try{
			Iterator it = peerList.iterator(); 
			while(it.hasNext()){
				Individual peerIndiv = (Individual)it.next();
				if(peerIndiv!=localInformer.getLocalPeerIndiv(localRegistry) &&	!mOyster2.isOfflinePeer(peerIndiv.getURI())){
					Iterator itOnto = ontologySetLocal.iterator();
					while (itOnto.hasNext()){
						Individual ontoindiv = (Individual)itOnto.next();
						if(localRegistry.containsAxiom(KAON2Manager.factory().objectPropertyMember(trackOntology,peerIndiv,ontoindiv),true)){
							IP = localInformer.getPeerIP(localRegistry,peerIndiv);
							mOyster2.getLogger().info("Attempt to synchronize changes with peer: "+IP);
							remoteRegistry = localInformer.openRemoteRegistry(IP);
							if (remoteRegistry!=null){
								SyncrhonizeChangesWithRegistry(remoteRegistry,ontoindiv,localRegistry);
							}
							else{
								mOyster2.addOfflinePeer(peerIndiv.getURI());
							}
						}
					}
				}
				else localInformer.updateLocalRegistry();
			}
			if (mOyster2.getPushChangesToOysterIP()!=null)	{
				pushRegistry = localInformer.openRemoteRegistry(mOyster2.getPushChangesToOysterIP());
				Iterator itOnto = ontologySetLocal.iterator();
				while (itOnto.hasNext()){
					Individual ontoindiv = (Individual)itOnto.next();
					PushChangesToPeer(localRegistry, ontoindiv, pushRegistry);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("finished change synchronization...");
		mOyster2.getLogger().info("finished change synchronization...");
	}
	
	public synchronized static void SyncDiff(OMVOntology mainOntoReply, Ontology remoteRegistry,Ontology targetOntology, boolean push){
		String originalLastLocal=cMgmt.getLastChangeIdFromLog(mainOntoReply, targetOntology);		
		Set<String> changes1URIs = cMgmt.getChangesIds(mainOntoReply, targetOntology);
		Set<String> changes2URIs = cMgmt.getChangesIds(mainOntoReply, remoteRegistry);
		
		changes2URIs.removeAll(changes1URIs);
		if (changes2URIs.size()>0) {//REMOTE NODE HAS CHANGES THAT ARE NOT AVAILABLE IN LOCAL NODE
			
			//GET CHANGES - REMOTE SHOULD BE ONLY THOSE NOT IN LOCAL NODE, BUT THIS MIGHT ME BETTER PERFORMANCE: CONTACT ONLY ONCE REMOTE NODE(OR NOT - HAVE TO CHECK), BUT REMOTE CHANGES TO BE APPLIED NEED TO BE PROCESSED IN ORDER
			List<OMVChange> changes1 = cMgmt.getTrackedChanges(mainOntoReply, targetOntology, null); 
			List<OMVChange> changes2 = cMgmt.getTrackedChanges(mainOntoReply, remoteRegistry, null);
			Collections.reverse(changes1);
			Collections.reverse(changes2);
			
			//GET ORIGINAL FIRST CHANGE IF AVAILABLE
			OMVChange originalFirstLocal = null;
			if (changes1.size()>0) originalFirstLocal=changes1.get(0);
			
			//USE REMOTE CHANGES IN ORDER
			for (OMVChange change : changes2){
				if (changes2URIs.contains(change.getURI())){ //PROCESS ONLY NEW CHANGES
					boolean success=RegisterChangeDiff(mainOntoReply, change, push); //APPLY THE NEW CHANGE
					if (success){
						if (change.getHasPreviousChange()==null ){//IT IS THE FIRST CHANGE IN REMOTE LOG 
							if (changes1.size()>0){ //THE LOCAL LOG HAD ALREADY CHANGES
								//ADD CHANGE AT THE BEGINNING OF LOG
								//FIX THE LASTLOCAL TO ORIGINAL LASTLOCAL, FIX THE PREVIOUS OF IMPORTED CHANGE - RETURN TO NULL BECAUSE IS THE NEW FIRST ONE, FIX THE ORIGINAL FIRST CHANGE 
								FixHistoryDiff (mainOntoReply, change, "", originalFirstLocal.getURI(), originalLastLocal, targetOntology, push, false);

								//UPDATE CHANGE LIST - ORIGINAL FIRST MUST NOT BE NULL HERE
								changes1.remove(originalFirstLocal);
								originalFirstLocal.setHasPreviousChange(change.getURI());
								changes1.add(originalFirstLocal);
							}//else IT IS ALSO THE FIRST CHANGE IN LOCAL LOG - APPEND TO THE END OF LOG
							//UPDATE LOCAL CHANGES LIST AND URIS - ORDER NOT MATTER HERE
							changes1.add(change);
							changes1URIs.add(change.getURI());
							//RESET FIRST LOCAL BECAUSE THIS CHANGE IS NOW THE FIRST LOCAL
							originalFirstLocal = change;
						}	
						else if (changes1URIs.contains(change.getHasPreviousChange())){ //SINCE IT IS IN ORDER (OF REMOTE) IF THE PREVIOUS CHANGE IS NOT IN THE LOCAL LOG ALREADY ERROR
							//FIND ORIGINAL NEXT OF PREVIOUS CHANGE
							OMVChange originalNextChange=null;
							for (OMVChange tc : changes1)
								if (tc.getHasPreviousChange()!=null && tc.getHasPreviousChange().equalsIgnoreCase(change.getHasPreviousChange())){ 
									originalNextChange = tc; // ORIGINAL NEXT CHANGE OF THE PREVIOUS OF THE NEW CHANGE 
									break;
								}
							if (originalNextChange !=null){ //OTHERWISE, THE PREVIOUS WAS THE LAST ONE
								//FIX THE ORIGINAL NEXT OF PREVIOUS CHANGE -> PREVIOUS CHANGE = NEW CHANGE, FIX THE LASTLOCAL TO ORIGINAL LASTLOCAL IF THE PREVIOUS CHANGE WAS NOT THE ORIGINAL LASTLOCAL
								FixHistoryDiff (mainOntoReply, change, change.getHasPreviousChange(), originalNextChange.getURI(), originalLastLocal, targetOntology, push, true);
								//RESET ORIGINAL NEXT CHANGE
								changes1.remove(originalNextChange);
								originalNextChange.setHasPreviousChange(change.getURI());
								changes1.add(originalNextChange);
							} 
							else //RESET LASTLOCAL BECAUSE THIS NEW CHANGE IS NOW THE LAST ONE 
								originalLastLocal = change.getURI();
							//UPDATE LOCAL CHANGES LIST AND URIS - ORDER NOT MATTER HERE
							changes1.add(change);
							changes1URIs.add(change.getURI());
						}
						else System.out.println("error");
					}
				}
			}
			//TRY TO SORT CHANGES ACCORDINGLY TO TIMESTAMP - IMPORTANT THE TIMESTAMP!
			int resultSort=0;
			if (mOyster2.getKeepOrder())	resultSort=sort(mainOntoReply, targetOntology, push);
			if (resultSort>=0){
				//NOW SYNC THE WORKFLOW HISTORY
				Map<List<Action>,List<Action>> actions=getActions(mainOntoReply,remoteRegistry,targetOntology);
				SyncWorkflow (mainOntoReply,remoteRegistry,targetOntology, actions,push);
			}
		}
	}
	
	public synchronized static int sort(OMVOntology mainOntoReply, Ontology targetOntology, boolean push){
		List<OMVChange> changesToSort= new ArrayList<OMVChange>();
		String lastChange="";
		//FIRST SORT THE CHANGES IN A LIST ACCORDINGLY TO TIMESTAMP
		try {
			List<OMVChange> allChanges = cMgmt.getTrackedChanges(mainOntoReply, targetOntology, null);
			Collections.reverse(allChanges);
			if (allChanges.size()<=0) return 0;
			lastChange = allChanges.get(allChanges.size()-1).getURI();

			Set<OMVChange> atomicChanges = new HashSet<OMVChange>();
			Set<OMVChange> atomicChangesFinal = new HashSet<OMVChange>();
			Set<OMVChange> entityChanges = new HashSet<OMVChange>();
			Set<OMVChange> compositeChanges = new HashSet<OMVChange>();
			Set<OMVChange> compositeChangesFinal = new HashSet<OMVChange>();
			
			for (OMVChange c: allChanges){
				if (c instanceof OMVAtomicChange) atomicChanges.add(c);
				else if (c instanceof OMVEntityChange) entityChanges.add(c);
				else if (c instanceof OMVCompositeChange) compositeChanges.add(c);
			}
			
			//IF THERE ARE ATOMIC WITH NO ENTITY CHANGE
			Set<String> atomicURIs = new HashSet<String>();
			for (OMVChange c: atomicChanges) atomicURIs.add(c.getURI());
			for (OMVChange c : entityChanges)
				for (String uriatomic : ((OMVEntityChange)c).getConsistsOfAtomicOperation())
					atomicURIs.remove(uriatomic);
			for (String uri: atomicURIs)
				for (OMVChange c: atomicChanges)
					if (c.getURI().equalsIgnoreCase(uri)) atomicChangesFinal.add(c); 
				
			//IF THERE ARE COMPOSITE CHANGES
			compositeChangesFinal.addAll(compositeChanges);
			for (OMVChange c : compositeChanges){
				if (c instanceof OMVCompositeChange){
					for (String cS: ((OMVCompositeChange)c).getConsistsOf()){
						for (OMVChange ec : entityChanges)
							if (ec.getURI().equalsIgnoreCase(cS))
								entityChanges.remove(ec);
						for (OMVChange cc : compositeChangesFinal)
							if (cc.getURI().equalsIgnoreCase(cS))
								compositeChangesFinal.remove(cc);
					}
				}
			}
			
			//SORT ENTITY (AND COMPOSITE) CHANGES IN THE LIST;
			changesToSort.addAll(atomicChangesFinal);
			changesToSort.addAll(entityChanges);
			changesToSort.addAll(compositeChangesFinal);
			Collections.sort(changesToSort, TIME_ORDER);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		//FIX THE PREVIOUSCHANGES HISTORY ACCORDING TO THIS ORDER
		try {
			String previousChange="";
			int index=0;
			for (OMVChange c : changesToSort){
				if (index == 0) previousChange="";
				else previousChange=changesToSort.get(index-1).getURI();
				
				if (c instanceof OMVEntityChange){
					Set<OMVAtomicChange> changesIn=cMgmt.getAtomicChanges((OMVEntityChange)c);
					Set<String> changesInURIs = new HashSet<String>();
					for (OMVAtomicChange ac : changesIn) changesInURIs.add(ac.getURI());
					for (OMVAtomicChange ac : changesIn){		
						if (ac.getHasPreviousChange()==null || !changesInURIs.contains(ac.getHasPreviousChange())){ //IT IS THE FIRST CHANGE IN LOG OR IT IS THE FIRST ATOMIC CHANGE OF THE ENTITY CHANGE
							String currentPrevious = (ac.getHasPreviousChange() == null) ? "" : ac.getHasPreviousChange();
							if (!currentPrevious.equalsIgnoreCase(previousChange))
								FixHistoryDiff (mainOntoReply, ac, previousChange, "", "", targetOntology, push, false);
							break;
						}
					}
				}
				else if (c instanceof OMVCompositeChange){
					Set<OMVChange> changesIn=getCompositeChangesIn(c, targetOntology);
					Set<String> changesInURIs = new HashSet<String>();
					for (OMVChange ac : changesIn) changesInURIs.add(ac.getURI());
					for (OMVChange ac : changesIn){		
						if (ac.getHasPreviousChange()==null || !changesInURIs.contains(ac.getHasPreviousChange())){ //IT IS THE FIRST CHANGE IN LOG OR IT IS THE FIRST ATOMIC CHANGE OF THE ENTITY CHANGE
							String currentPrevious = (ac.getHasPreviousChange() == null) ? "" : ac.getHasPreviousChange();
							if (!currentPrevious.equalsIgnoreCase(previousChange))
								FixHistoryDiff (mainOntoReply, ac, previousChange, "", "", targetOntology, push, false);
							break;
						}
					}
				}
				else if (c instanceof OMVAtomicChange){ //ATOMIC CHANGE NOT PART OF ANY ENTITY/COMPOSITE CHANGE
					String currentPrevious = (c.getHasPreviousChange() == null) ? "" : c.getHasPreviousChange();
					if (!currentPrevious.equalsIgnoreCase(previousChange))
						FixHistoryDiff (mainOntoReply, c, previousChange, "", "", targetOntology, push, false);
				}
				index++;
			}
			//FIX THE LAST CHANGE
			if (!lastChange.equalsIgnoreCase(changesToSort.get(changesToSort.size()-1).getURI()))
				FixHistoryDiff (mainOntoReply, new OMVChange(), "", "", changesToSort.get(changesToSort.size()-1).getURI(), targetOntology, push, true);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	public synchronized static Set<OMVChange> getCompositeChangesIn (OMVChange c, Ontology targetOntology){
		Set<String> changesInURIs = new HashSet<String>();
		Set<OMVChange> changesIn = new HashSet<OMVChange>();
		changesInURIs.addAll(((OMVCompositeChange)c).getConsistsOf());
		for (String cS: changesInURIs){
			OMVChange changeIn = cMgmt.getChange(cS, targetOntology);
			changesIn.add(changeIn);
			if (changeIn instanceof OMVEntityChange)
				changesIn.addAll(cMgmt.getAtomicChanges((OMVEntityChange)c));			
			else if (changeIn instanceof OMVCompositeChange) 
				changesIn.addAll(getCompositeChangesIn(changeIn, targetOntology));
		}
		return changesIn;
	}
	public synchronized static void FixHistoryDiff(OMVOntology mainOntoReply, OMVChange c, String originalPrevious, String originalNext, String lastLocal, Ontology targetOntology, boolean push, boolean keepOriginalFirstImported){
		ChangeManagement cMgmtTarget = cMgmt;
		ObjectProperty ontologyObjectProperty;
		try{
			if (push){
				mOyster2.setSuperOysterIP(mOyster2.getPushChangesToOysterIP());
				cMgmtTarget = new ChangeManagement();
			}
			//RETURN LAST CHANGE TO THE ORGINAL ONE
			if (!lastLocal.equalsIgnoreCase("")){
				ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.CHANGEURI + Constants.hasLastChange);
				cMgmtTarget.removeProperty(cMgmtTarget.getLogID(mainOntoReply), ontologyObjectProperty, Constants.CHANGEURI+Constants.LogConcept);
				cMgmtTarget.addProperty(cMgmtTarget.getLogID(mainOntoReply),ontologyObjectProperty,Constants.CHANGEURI+Constants.LogConcept,lastLocal);
			}
			//FIX HISTORY - HASPREVIOUSCHANGE OF CHANGE IMPORTED
			ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.CHANGEURI + Constants.hasPreviousChange);
			if (!keepOriginalFirstImported){
				cMgmtTarget.removeProperty(c.getURI(), ontologyObjectProperty, Constants.CHANGEURI+cMgmtTarget.getChangeConcept(c));
				if (originalPrevious!=null && !originalPrevious.equalsIgnoreCase("")) cMgmtTarget.addProperty(c.getURI(),ontologyObjectProperty,Constants.CHANGEURI+cMgmtTarget.getChangeConcept(c),originalPrevious);
			}
			//FIX HISTORY - HASPREVIOUSCHANGE OF ORIGINAL NEXT CHANGE
			if (!originalNext.equalsIgnoreCase("")){
				cMgmtTarget.removeProperty(originalNext, ontologyObjectProperty, Constants.CHANGEURI+cMgmtTarget.getChangeConcept(cMgmtTarget.getChange(originalNext, targetOntology)));
				cMgmtTarget.addProperty(originalNext,ontologyObjectProperty,Constants.CHANGEURI+cMgmtTarget.getChangeConcept(cMgmtTarget.getChange(originalNext, targetOntology)),c.getURI());
			}
			if (push) mOyster2.setSuperOysterIP(null);
		}catch(Exception e){
			if (push) mOyster2.setSuperOysterIP(null);
			e.printStackTrace();
		}
	} 
	
	public synchronized static boolean RegisterChangeDiff(OMVOntology mainOntoReply, OMVChange change, boolean push){
		List<OMVChange> syncChanges = new LinkedList<OMVChange>();
		ChangeManagement cMgmtTarget = cMgmt;
		try{
			if (push){
				mOyster2.setSuperOysterIP(mOyster2.getPushChangesToOysterIP());
				cMgmtTarget = new ChangeManagement();
			}
			
			mOyster2.getLogger().info("will copy ..."+change.getURI());
			String lastChangeURI=cMgmtTarget.register(change);
			if (!lastChangeURI.equalsIgnoreCase("")) { //IF THE CHANGE WAS ADDED SUCCESSFULLY
				syncChanges.add(change);
			}
			if (syncChanges.size()>0)	mOyster2.addLastChangesSync(mainOntoReply, syncChanges);
			else return false;
			
			if (push) mOyster2.setSuperOysterIP(null);
		}catch(Exception e){
			if (push) mOyster2.setSuperOysterIP(null);
			e.printStackTrace();
		}
		return true;
	}
	
		
	public synchronized static Map<List<Action>,List<Action>>getActions(OMVOntology mainOntoReply, Ontology remoteRegistry,Ontology targetOntology){
		List<Action> listActions=wMgmt.getEntityActionsHistory(mainOntoReply, remoteRegistry, null);
		List<Action> listActionsLocal=wMgmt.getEntityActionsHistory(mainOntoReply, targetOntology, null);
		Map<List<Action>,List<Action>> reply = new HashMap<List<Action>,List<Action>>();
		reply.put(listActions, listActionsLocal);
		return reply;
	}
	
	public synchronized static void SyncWorkflow(OMVOntology mainOntoReply, Ontology remoteRegistry,Ontology targetOntology, Map<List<Action>,List<Action>> actions, boolean push){
		WorkflowManagement wMgmtTarget = wMgmt;
		try{
			if (push){
				mOyster2.setSuperOysterIP(mOyster2.getPushChangesToOysterIP());
				wMgmtTarget = new WorkflowManagement();
			}
			if (actions == null || actions.keySet().size()<=0) return; //error
			List<Action> listActions = actions.keySet().iterator().next();
			List<Action> listActionsLocal = actions.get(listActions);  
			for (Action ac : listActions){
				boolean ins = false;
				for (Action acLocal : listActionsLocal){
					if (acLocal.getURI().equalsIgnoreCase(ac.getURI()) && acLocal.getTimestamp().equalsIgnoreCase(ac.getTimestamp()))
						ins = true;
				}
				if (!ins){
					if (ac instanceof EntityAction){
						mOyster2.getLogger().info("will copy action..."+ac.getURI());
						if (ac instanceof Delete) wMgmtTarget.delete(((Delete)ac).getRelatedChange(), ((Delete)ac).getPerformedBy(), mainOntoReply, ((Delete)ac).getTimestamp());
						else if (ac instanceof RejectToApproved) wMgmtTarget.rejectToApproved(((RejectToApproved)ac).getRelatedChange(), ((RejectToApproved)ac).getPerformedBy(), mainOntoReply, ((RejectToApproved)ac).getTimestamp());
						else if (ac instanceof RejectToBeApproved) wMgmtTarget.rejectToBeApproved(((RejectToBeApproved)ac).getRelatedChange(), ((RejectToBeApproved)ac).getPerformedBy(), mainOntoReply, ((RejectToBeApproved)ac).getTimestamp());
						else if (ac instanceof RejectToDraft) wMgmtTarget.rejectToDraft(((RejectToDraft)ac).getRelatedChange(), ((RejectToDraft)ac).getPerformedBy(), mainOntoReply, ((RejectToDraft)ac).getTimestamp());
						else if (ac instanceof SendToApproved) wMgmtTarget.submitToApproved(((SendToApproved)ac).getRelatedChange(), ((SendToApproved)ac).getPerformedBy(), mainOntoReply, ((SendToApproved)ac).getTimestamp());
						else if (ac instanceof SendToBeApproved) wMgmtTarget.submitToBeApproved(((SendToBeApproved)ac).getRelatedChange(), ((SendToBeApproved)ac).getPerformedBy(), mainOntoReply, ((SendToBeApproved)ac).getTimestamp());
						else if (ac instanceof SendToBeDeleted) wMgmtTarget.submitToBeDeleted(((SendToBeDeleted)ac).getRelatedChange(), ((SendToBeDeleted)ac).getPerformedBy(), mainOntoReply, ((SendToBeDeleted)ac).getTimestamp());
						else if (ac instanceof Insert) wMgmtTarget.insert(((Insert)ac).getRelatedChange(), ((Insert)ac).getPerformedBy(), mainOntoReply, ((Insert)ac).getTimestamp());
						else if (ac instanceof Update) wMgmtTarget.update(((Update)ac).getRelatedChange(), ((Update)ac).getPerformedBy(), mainOntoReply, ((Update)ac).getTimestamp());
					}
				}
			}
			
			if (push) mOyster2.setSuperOysterIP(null);
		}catch(Exception e){
			if (push) mOyster2.setSuperOysterIP(null);
			e.printStackTrace();
		}
	}
	
	public synchronized static void SyncrhonizeChangesWithRegistry(Ontology remoteRegistry,Individual ontoindiv,Ontology targetOntology){
		OMVOntology mainOntoReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(ontoindiv, "ontology",targetOntology);
		
		SyncDiff(mainOntoReply,remoteRegistry,targetOntology,false);

	}
	
	
	
	public synchronized static void SyncrhonizeChangesWithPeer(Ontology remoteRegistry,Individual peerIndiv,Ontology targetOntology){
		Collection ontologySetRemote = localInformer.getTrackedOntologies(remoteRegistry,peerIndiv);
		Collection ontologySetLocal = localInformer.getTrackedOntologies(targetOntology,localInformer.getLocalPeer()); //localInformer.getTrackedOntologies(targetOntology,peerIndiv);
		
		mOyster2.getLogger().info("starting syncrhonize with peer...");
		Iterator it = ontologySetLocal.iterator();
		while (it.hasNext()){
			Individual t = (Individual)it.next();
			Iterator it2 = ontologySetRemote.iterator();
			while (it2.hasNext()){
				Individual t2 = (Individual)it2.next();
				if (t.getURI().equalsIgnoreCase(t2.getURI())){
					OMVOntology mainOntoReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(t, "ontology",targetOntology);
					if (mainOntoReply==null) return;
					mOyster2.getLogger().info("verifying histories of common tracked ontology ..."+mainOntoReply.getURI());
					
					SyncDiff(mainOntoReply,remoteRegistry,targetOntology,false);
					
					
				}
			}
		}
	}
	
	public synchronized static void PushChangesToPeer(Ontology localRegistryFrom,Individual ontoindiv, Ontology targetOntology){
		OMVOntology mainOntoReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(ontoindiv, "ontology",localRegistryFrom);
	
		
		try{
			mOyster2.setSuperOysterIP(mOyster2.getPushChangesToOysterIP());
			mOyster2.openSuperOyster();
			ChangeManagement cMgmtSO = new ChangeManagement();
			if (!cMgmtSO.isTracked(mainOntoReply)) cMgmtSO.startTracking(mainOntoReply);
			mOyster2.setSuperOysterIP(null);
		}catch(Exception e){
			mOyster2.setSuperOysterIP(null);
			e.printStackTrace();
		}
		
		SyncDiff(mainOntoReply,localRegistryFrom,targetOntology,true);
		
	}
	
	static final Comparator<OMVChange> TIME_ORDER =
        new Comparator<OMVChange>() {
		public int compare(OMVChange e1, OMVChange e2) {
			try {
				Date d1=DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).parse(e1.getDate());
				Date d2=DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).parse(e2.getDate());
				return d1.compareTo(d2);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return 0;
		}
	};
}
