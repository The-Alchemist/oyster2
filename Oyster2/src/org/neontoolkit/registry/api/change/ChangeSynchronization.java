package org.neontoolkit.registry.api.change;


import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.extensions.change.OMVChange;
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
	private static boolean firstWasThere =false;
	private static String lastWasThereURI="";
	
	
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
	
	public synchronized static void Sync(OMVOntology mainOntoReply, Ontology remoteRegistry,Ontology targetOntology, boolean push){
		String lastLocal=cMgmt.getLastChangeIdFromLog(mainOntoReply, targetOntology);
		String startsHere="";
		String lspPlusOne="";
		String fsp="";
		List<OMVChange> list;
		List<OMVChange> listOriginal;
		List<OMVChange> listActuallyApplied;
		
		//RESET
		firstWasThere=false;
		lastWasThereURI="";
		
		//GET CHANGES
		List<OMVChange> changes1 = cMgmt.getTrackedChanges(mainOntoReply, targetOntology, null); 
		List<OMVChange> changes2 = cMgmt.getTrackedChanges(mainOntoReply, remoteRegistry, null);
		
		//FIND LAST SYNC POINT - LSP
		//Map<String,String> reply =cMgmt.findLastSynchronizationPoint(mainOntoReply, targetOntology, remoteRegistry, changes1, changes2); 
		Map<String,String> reply =cMgmt.findLastSynchronizationPoint(changes1, changes2);
		if (reply==null) return; //ERROR
		startsHere = reply.keySet().iterator().next(); //LSP
		lspPlusOne = reply.get(startsHere);
		
		//FIND IF THERE IS A SYNC POINT OF lspPlusOne IN REMOTE LOG // - REPLACE PREVIOUS METHOD
		boolean isFSP = cMgmt.isASynchronizationPoint(lspPlusOne, changes2);
		if (isFSP) fsp = lspPlusOne;
		else fsp = "";
		
		//FIND CASES
		//int isOlder=cMgmt.isHistoryOlder(mainOntoReply, targetOntology, remoteRegistry, startsHere, changes2);
		int isOlder=cMgmt.isHistoryOlderNew(mainOntoReply, targetOntology, remoteRegistry);
		if (isOlder==0) return; //CASE (i or ii)
		mOyster2.getLogger().info("local history is older ..."+mainOntoReply.getURI());
		
		if (isOlder==1) {
			//GET CHANGES
			list = getChanges (startsHere, changes2);
			if (list==null) return; //ERROR
			listOriginal = getChanges (startsHere, changes1);
			if (listOriginal==null) return; //ERROR
			
			//APPLY CHANGES
			//listActuallyApplied = RegisterChanges(mainOntoReply,fsp,list,push); //changed "" for fsp, if the lspPlusOne is not in remote fsp = "" otherwise fsp=lspPlusOne
			listActuallyApplied = RegisterChangesNew(mainOntoReply,fsp,list,listOriginal,push); //changed "" for fsp, if the lspPlusOne is not in remote fsp = "" otherwise fsp=lspPlusOne
			if (listActuallyApplied.size()<=0 && fsp.equalsIgnoreCase("")) return; //AVOID LOOPS
			if (firstWasThere){
				if (listActuallyApplied.size()<=0){ //DID NOT APPLY ANY NEW CHANGES, SO ONLY THE ORDER WAS DIFFERENT
					for (int i=list.size()-1;i>=0;i--){
						OMVChange c = (OMVChange)list.get(i);
						if (fsp.equalsIgnoreCase(c.getURI())) break;
						else listActuallyApplied.add(c);
					}
					if (listActuallyApplied.size()<=0) return; //ERROR
					Collections.reverse(listActuallyApplied);
					if (listActuallyApplied.get(0).getURI().equalsIgnoreCase(lastLocal)) lastLocal = cMgmt.getChange(listActuallyApplied.get(listActuallyApplied.size()-1).getURI(), targetOntology).getHasPreviousChange(); //REORDER ACCORDINGLY TO REMOTE LOG
					//FIX: SET LASTLOCAL TO ORIGINAL PREVIOUS OF FIRST IMPORTED CHANGE (IF THE LAST IMPORTED CHANGE WAS THE ORIGINAL LASTLOCAL), OTHERWISE RESET TO ORIGINAL LAST; FIX PREVIOUS CHANGE OF FIRST CHANGE IMPORTED TO LSP, FIX PREVIOUS CHANGE OF LSP+1 TO LAST IMPORTED     
					FixHistory(mainOntoReply, listActuallyApplied, startsHere, lspPlusOne, lastLocal, targetOntology, push, false); 
				}					
				else{ //SOME NEW CHANGES WERE APPLIED, BUT AT LEAST 1(OR MORE) AT THE BEGINNING OF THE LIST EXISTED ALREADY
					String originalNextChange="";
					for (OMVChange tc : listOriginal)
						if (tc.getHasPreviousChange().equalsIgnoreCase(lastWasThereURI)) originalNextChange = tc.getURI(); // FIND THE ORIGINAL NEXT CHANGE OF THE LAST KNOWN
					if (!originalNextChange.equalsIgnoreCase("")) //ADDED SOMETHING TO THE MIDDLE OF THE LOG (AFTER LSP) - ELSE ADDED SOMETHING TO THE END OF THE LOG
						FixHistory(mainOntoReply, listActuallyApplied, startsHere, originalNextChange, lastLocal, targetOntology, push, true);	//FIX: RESET LASTLOCAL TO ORIGINAL LAST, KEEP ORIGINAL FIRST IMPORTED, SET THE PREVIOUS OF THE ORIGINAL NEXT CHANGE TO THE LAST IMPORTED CHANGE  
				}					
			}
			else {
				//if (isOlder!=1) //ADDED SOMETHING AT THE BEGINNING OF THE LOG (AFTER LSP) - ELSE ADDED STH AT THE END NORMALLY
				if (listActuallyApplied.size()>0 && listActuallyApplied.get(listActuallyApplied.size()-1).getHasPreviousChange()!= null && !listActuallyApplied.get(listActuallyApplied.size()-1).getHasPreviousChange().equalsIgnoreCase(lastLocal)) //ADDED SOMETHING AT THE BEGINNING OF THE LOG (AFTER LSP) -ELSE ADDED STH AT THE END NORMALLY 
					FixHistory(mainOntoReply, listActuallyApplied, startsHere, lspPlusOne, lastLocal, targetOntology, push, false); //FIX: RESET LASTLOCAL TO ORIGINAL LAST, FIX PREVIOUS CHANGE OF FIRST CHANGE IMPORTED TO LSP, FIX PREVIOUS CHANGE OF LSP+1 TO LAST IMPORTED
			}
			
			//DO IT AGAIN
			Sync(mainOntoReply,remoteRegistry,targetOntology,false);
			//return;
		}
		else return; //error
		
		Map<List<Action>,List<Action>> actions=getActions(mainOntoReply,remoteRegistry,targetOntology);
		SyncWorkflow (mainOntoReply,remoteRegistry,targetOntology, actions,push);
	}
	
	public synchronized static List<OMVChange> getChanges(String startsHere, List<OMVChange> changesOri){
		List<OMVChange> list = new LinkedList<OMVChange>();
		if (startsHere!=null && !startsHere.equalsIgnoreCase("")){
			//list=cMgmt.getTrackedChanges(mainOntoReply, remoteRegistry, startsHere);
			for (OMVChange c : changesOri){ 
				if (!c.getURI().equalsIgnoreCase(startsHere))
					list.add(c);
				else break;
			}
		}
		else{
			//list=cMgmt.getTrackedChanges(mainOntoReply, remoteRegistry, null);
			list.addAll(changesOri);
		}
		return list;
	}
	
	public synchronized static List<OMVChange> RegisterChanges(OMVOntology mainOntoReply, String stopHere, List<OMVChange> list, boolean push){
		List<OMVChange> syncChanges = new LinkedList<OMVChange>();
		ChangeManagement cMgmtTarget = cMgmt;
		try{
			if (push){
				mOyster2.setSuperOysterIP(mOyster2.getPushChangesToOysterIP());
				cMgmtTarget = new ChangeManagement();
			}
			mOyster2.getLogger().info("will copy ..."+list.size());
			for (int i=list.size()-1;i>=0;i--){
				OMVChange c = (OMVChange)list.get(i);
				if (stopHere.equalsIgnoreCase(c.getURI())) break;
				mOyster2.getLogger().info("will copy ..."+c.getURI());
				String lastChangeURI=cMgmtTarget.register(c);
				if (!lastChangeURI.equalsIgnoreCase("")) { //IF THE CHANGE WAS ADDED I.E. IT IS NEW
					if (firstWasThere && lastWasThereURI.equalsIgnoreCase("")) lastWasThereURI=c.getHasPreviousChange(); //IF ENTER THIS IF, C.GETHASPREVIOUSCHANGE() MUST NOT BE NULL, IT IS LATER THEN AT LEAST THE FIRST THAT EXISTED BEFORE
					syncChanges.add(c);
				}
				else { //ADD CHANGES UNTIL THE NEXT SYNC POINT (THIS IS CALLED RECURSIVELY)
					if (i == list.size()-1) firstWasThere=true;
					else if (firstWasThere && !lastWasThereURI.equalsIgnoreCase("")) break;
				}
			}
			if (syncChanges.size()>0)	mOyster2.addLastChangesSync(mainOntoReply, syncChanges);
			Collections.reverse(syncChanges); //THE FIX WAS EXPECTING THE ORIGINAL REVERSE ORDER
			
			if (push) mOyster2.setSuperOysterIP(null);
		}catch(Exception e){
			if (push) mOyster2.setSuperOysterIP(null);
			e.printStackTrace();
		}
		return syncChanges;
	}
	
	public synchronized static List<OMVChange> RegisterChangesNew(OMVOntology mainOntoReply, String stopHere, List<OMVChange> list, List<OMVChange> listOriginal, boolean push){
		List<OMVChange> syncChanges = new LinkedList<OMVChange>();
		Set<String> uriOriginal= new HashSet<String>();
		boolean firstWasApplied=false;
		String lastWasNotThereURI = "";
		
		for (OMVChange c : listOriginal) uriOriginal.add(c.getURI());
		ChangeManagement cMgmtTarget = cMgmt;
		try{
			if (push){
				mOyster2.setSuperOysterIP(mOyster2.getPushChangesToOysterIP());
				cMgmtTarget = new ChangeManagement();
			}
			mOyster2.getLogger().info("will copy ..."+list.size());
			for (int i=list.size()-1;i>=0;i--){
				OMVChange c = (OMVChange)list.get(i);
				if (stopHere.equalsIgnoreCase(c.getURI())) break;
				if (!uriOriginal.contains(c.getURI())){
					if (i == list.size()-1) firstWasApplied=true; //PROCESS CHANGES UNTIL THE NEXT NOSYNCPOINT -X*NEW+Y*EXIST- (THIS IS CALLED RECURSIVELY)
					else if (firstWasApplied && !lastWasNotThereURI.equalsIgnoreCase("")) break; //THIS IS THE NEXT NOSYNCPOINT
					
					mOyster2.getLogger().info("will copy ..."+c.getURI());
					String lastChangeURI=cMgmtTarget.register(c);
					if (!lastChangeURI.equalsIgnoreCase("")) { //IF THE CHANGE WAS ADDED I.E. IT IS NEW. 
						if (firstWasThere && lastWasThereURI.equalsIgnoreCase("")) lastWasThereURI=c.getHasPreviousChange(); //IF ENTER THIS IF, C.GETHASPREVIOUSCHANGE() MUST NOT BE NULL, IT IS LATER THEN AT LEAST THE FIRST THAT EXISTED BEFORE
						syncChanges.add(c);
					}
					else break; //THIS SHOULD NEVER BE TRUE BECAUSE THE CONTAINS IF, OTHERWISE ERROR
				}
				else { 
					if (i == list.size()-1) firstWasThere=true; //PROCESS CHANGES UNTIL THE NEXT SYNC POINT -X*EXIST+Y*NEW- (THIS IS CALLED RECURSIVELY)
					else if (firstWasThere && !lastWasThereURI.equalsIgnoreCase("")) break; //THIS IS THE NEXT SYNC POINT
					if (firstWasApplied && lastWasNotThereURI.equalsIgnoreCase("")) lastWasNotThereURI=c.getHasPreviousChange(); //IF ENTER THIS IF, C.GETHASPREVIOUSCHANGE() MUST NOT BE NULL, IT IS LATER THEN AT LEAST THE FIRST THAT WAS APPLIED BEFORE
				}
			}
			if (syncChanges.size()>0)	mOyster2.addLastChangesSync(mainOntoReply, syncChanges);
			Collections.reverse(syncChanges); //THE FIX WAS EXPECTING THE ORIGINAL REVERSE ORDER
			
			if (push) mOyster2.setSuperOysterIP(null);
		}catch(Exception e){
			if (push) mOyster2.setSuperOysterIP(null);
			e.printStackTrace();
		}
		return syncChanges;
	}
	
	public synchronized static void FixHistory(OMVOntology mainOntoReply, List<OMVChange> list, String startsHere, String lspPlusOne, String lastLocal, Ontology targetOntology, boolean push, boolean keepOriginalFirstImported){
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
			//FIX HISTORY - HASPREVIOUSCHANGE OF FIRST CHANGE IMPORTED
			ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.CHANGEURI + Constants.hasPreviousChange);
			if (!keepOriginalFirstImported){
				cMgmtTarget.removeProperty(((OMVChange)list.get(list.size()-1)).getURI(), ontologyObjectProperty, Constants.CHANGEURI+cMgmtTarget.getChangeConcept((OMVChange)list.get(list.size()-1)));
				if (startsHere!=null && !startsHere.equalsIgnoreCase("")) cMgmtTarget.addProperty(((OMVChange)list.get(list.size()-1)).getURI(),ontologyObjectProperty,Constants.CHANGEURI+cMgmtTarget.getChangeConcept((OMVChange)list.get(list.size()-1)),startsHere);
			}
			//FIX HISTORY - HASPREVIOUSCHANGE OF (NEXT CHANGE OF LSP) OR OF FSP
			if (!lspPlusOne.equalsIgnoreCase("")){
				cMgmtTarget.removeProperty(lspPlusOne, ontologyObjectProperty, Constants.CHANGEURI+cMgmtTarget.getChangeConcept(cMgmtTarget.getChange(lspPlusOne, targetOntology)));
				cMgmtTarget.addProperty(lspPlusOne,ontologyObjectProperty,Constants.CHANGEURI+cMgmtTarget.getChangeConcept(cMgmtTarget.getChange(lspPlusOne, targetOntology)),((OMVChange)list.get(0)).getURI());
			}
			if (push) mOyster2.setSuperOysterIP(null);
		}catch(Exception e){
			if (push) mOyster2.setSuperOysterIP(null);
			e.printStackTrace();
		}
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
		
		Sync(mainOntoReply,remoteRegistry,targetOntology,false);

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
					
					Sync(mainOntoReply,remoteRegistry,targetOntology,false);
					
					
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
		
		Sync(mainOntoReply,localRegistryFrom,targetOntology,true);
		
	}
}


//else if (isOlder==3 || isOlder==4 || isOlder==5){
//DEAL WITH SPECIAL CASES OF 0 AND 1
//	list = RegisterChanges(mainOntoReply,remoteRegistry,startsHere,fsp,list,push);
//	if (list.size()<=0) return; //AVOID LOOPS
//	FixHistory(mainOntoReply, list, startsHere, fsp, lastLocal, targetOntology,push,false); //fsp != "", fsp==lspPluOne 

//DO IT AGAIN
//	Sync(mainOntoReply,remoteRegistry,targetOntology,false);
//return;
//}

//String fspMinusOne="";
//FIND FIRST SYNC POINT OF lspPlusOne
//reply =cMgmt.findFirstSynchronizationPoint(mainOntoReply, targetOntology, remoteRegistry,lspPlusOne); //CASE(iv)
//if (reply==null) return; //ERROR
//fspMinusOne = reply.keySet().iterator().next();
//fsp = reply.get(fspMinusOne);

//list = getChanges (mainOntoReply,remoteRegistry,startsHere);
//if (list==null) return; //ERROR
//listOriginal = getChanges (mainOntoReply,targetOntology,startsHere);
//if (listOriginal==null) return; //ERROR


//if (isOlder==1)	{	//CASE (iii)
//	startsHere = lastLocal; 	//START COPYING AT THE END OF THE LOG
//	fsp = "";					//RETRIEVE ALL CHANGES AFTER THE POSITION OF LASTLOCAL IN REMOTELOG
	//RegisterChanges(mainOntoReply,remoteRegistry,startsHere,"",list,push);
//}//else CASE(iv) 				//START COPYING AT THE BEGINNING IF POSSIBLE


//if (originalNextChange.equalsIgnoreCase("")) //ADDED SOMETHING TO THE END OF THE LOG
//	FixHistory(mainOntoReply, listActuallyApplied, startsHere, originalNextChange, "", targetOntology, push, true); // IF THE LAST KNOWN WAS THE LAST ONE, DO NOT RESET THE LAST CHANGE TO THAT CHANGE, AS IT HAS THE NEW LAST ONE
//else //ADDED SOMETHING TO THE MIDDLE OF THE LOG (AFTER LSP)
//	FixHistory(mainOntoReply, listActuallyApplied, startsHere, originalNextChange, lastLocal, targetOntology, push, true);	//FIX: RESET LASTLOCAL TO ORIGINAL LAST, KEEP ORIGINAL FIRST IMPORTED, SET THE PREVIOUS OF THE ORIGINAL NEXT CHANGE TO THE LAST IMPORTED CHANGE