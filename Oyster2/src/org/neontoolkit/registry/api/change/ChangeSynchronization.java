package org.neontoolkit.registry.api.change;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;
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
import org.neontoolkit.workflow.api.Action.EntityAction.RejectToApproved;
import org.neontoolkit.workflow.api.Action.EntityAction.RejectToBeApproved;
import org.neontoolkit.workflow.api.Action.EntityAction.RejectToDraft;
import org.neontoolkit.workflow.api.Action.EntityAction.SendToApproved;
import org.neontoolkit.workflow.api.Action.EntityAction.SendToBeApproved;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.owl.elements.Individual;
import org.semanticweb.kaon2.api.owl.elements.ObjectProperty;

/**
 * The class ChangeSynchronization provides the methods to support the
 * synchronization of the changes (and workflow actions if enabled) between
 * peers 
 * @author Raul Palma
 * @version 2.0, March 2008
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
	
	public synchronized static void SyncrhonizeChangesWithRegistry(Ontology remoteRegistry,Individual ontoindiv,Ontology targetOntology){
		OMVOntology mainOntoReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(ontoindiv, "ontology",targetOntology);
		if (cMgmt.isHistoryOlder(mainOntoReply, targetOntology, remoteRegistry)){
			String startsHere = cMgmt.getLastChangeIdFromLog(mainOntoReply, targetOntology);
			List<OMVChange> list;
			if (startsHere!=null && !startsHere.equalsIgnoreCase("")){
				list=cMgmt.getTrackedChanges(mainOntoReply, remoteRegistry, startsHere);
			}
			else{
				list=cMgmt.getTrackedChanges(mainOntoReply, remoteRegistry, null);
			}
			mOyster2.getLogger().info("will copy ..."+list.size());
			for (int i=list.size()-1;i>=0;i--){
				OMVChange c = (OMVChange)list.get(i);
				mOyster2.getLogger().info("will copy ..."+c.getURI());
				cMgmt.register(c);
			}
		}
		List<Action> listActions=wMgmt.getEntityActionsHistory(mainOntoReply, remoteRegistry, null);
		List<Action> listActionsLocal=wMgmt.getEntityActionsHistory(mainOntoReply, targetOntology, null);
		for (Action ac : listActions){
			boolean ins = false;
			for (Action acLocal : listActionsLocal){
				if (acLocal.getURI().equalsIgnoreCase(ac.getURI()))
					ins = true;
			}
			if (!ins){
				if (ac instanceof EntityAction){
					mOyster2.getLogger().info("will copy action..."+ac.getURI());
					if (ac instanceof Delete) wMgmt.delete(((Delete)ac).getRelatedChange(), ((Delete)ac).getPerformedBy(), mainOntoReply);
					else if (ac instanceof RejectToApproved) wMgmt.rejectToApproved(((RejectToApproved)ac).getRelatedChange(), ((RejectToApproved)ac).getPerformedBy(), mainOntoReply);
					else if (ac instanceof RejectToBeApproved) wMgmt.rejectToBeApproved(((RejectToBeApproved)ac).getRelatedChange(), ((RejectToBeApproved)ac).getPerformedBy(), mainOntoReply);
					else if (ac instanceof RejectToDraft) wMgmt.rejectToDraft(((RejectToDraft)ac).getRelatedChange(), ((RejectToDraft)ac).getPerformedBy(), mainOntoReply);
					else if (ac instanceof SendToApproved) wMgmt.submitToApproved(((SendToApproved)ac).getRelatedChange(), ((SendToApproved)ac).getPerformedBy(), mainOntoReply);
					else if (ac instanceof SendToBeApproved) wMgmt.submitToBeApproved(((SendToBeApproved)ac).getRelatedChange(), ((SendToBeApproved)ac).getPerformedBy(), mainOntoReply);
				}
			}
		}
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
					if (cMgmt.isHistoryOlder(mainOntoReply, targetOntology, remoteRegistry)){
						mOyster2.getLogger().info("local history is older ..."+mainOntoReply.getURI());
						String startsHere = cMgmt.getLastChangeIdFromLog(mainOntoReply, targetOntology);
						List<OMVChange> list;
						if (startsHere!=null && !startsHere.equalsIgnoreCase("")){
							list=cMgmt.getTrackedChanges(mainOntoReply, remoteRegistry, startsHere);
						}
						else{
							list=cMgmt.getTrackedChanges(mainOntoReply, remoteRegistry, null);
						}
						mOyster2.getLogger().info("will copy ..."+list.size());
						for (int i=list.size()-1;i>=0;i--){
							OMVChange c = (OMVChange)list.get(i);
							mOyster2.getLogger().info("will copy ..."+c.getURI());
							cMgmt.register(c);
						}
					}
					List<Action> listActions=wMgmt.getEntityActionsHistory(mainOntoReply, remoteRegistry, null);
					List<Action> listActionsLocal=wMgmt.getEntityActionsHistory(mainOntoReply, targetOntology, null);
					for (Action ac : listActions){
						boolean ins = false;
						for (Action acLocal : listActionsLocal){
							if (acLocal.getURI().equalsIgnoreCase(ac.getURI()))
								ins = true;
						}
						if (!ins){
							if (ac instanceof EntityAction){
								mOyster2.getLogger().info("will copy action..."+ac.getURI());
								if (ac instanceof Delete) wMgmt.delete(((Delete)ac).getRelatedChange(), ((Delete)ac).getPerformedBy(), mainOntoReply);
								else if (ac instanceof RejectToApproved) wMgmt.rejectToApproved(((RejectToApproved)ac).getRelatedChange(), ((RejectToApproved)ac).getPerformedBy(), mainOntoReply);
								else if (ac instanceof RejectToBeApproved) wMgmt.rejectToBeApproved(((RejectToBeApproved)ac).getRelatedChange(), ((RejectToBeApproved)ac).getPerformedBy(), mainOntoReply);
								else if (ac instanceof RejectToDraft) wMgmt.rejectToDraft(((RejectToDraft)ac).getRelatedChange(), ((RejectToDraft)ac).getPerformedBy(), mainOntoReply);
								else if (ac instanceof SendToApproved) wMgmt.submitToApproved(((SendToApproved)ac).getRelatedChange(), ((SendToApproved)ac).getPerformedBy(), mainOntoReply);
								else if (ac instanceof SendToBeApproved) wMgmt.submitToBeApproved(((SendToBeApproved)ac).getRelatedChange(), ((SendToBeApproved)ac).getPerformedBy(), mainOntoReply);
							}
						}
					}
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
		if (cMgmt.isHistoryOlder(mainOntoReply, targetOntology, localRegistryFrom)){
			String startsHere = cMgmt.getLastChangeIdFromLog(mainOntoReply, targetOntology);
			List<OMVChange> list;
			if (startsHere!=null && !startsHere.equalsIgnoreCase("")){
				list=cMgmt.getTrackedChanges(mainOntoReply, localRegistryFrom, startsHere);
			}
			else{
				list=cMgmt.getTrackedChanges(mainOntoReply, localRegistryFrom, null);
			}
			try{
				mOyster2.setSuperOysterIP(mOyster2.getPushChangesToOysterIP());
				ChangeManagement cMgmtSOEnd = new ChangeManagement();
				mOyster2.getLogger().info("will copy ..."+list.size());
				for (int i=list.size()-1;i>=0;i--){
					OMVChange c = (OMVChange)list.get(i);
					mOyster2.getLogger().info("will copy ..."+c.getURI());
					cMgmtSOEnd.register(c);
				}
				mOyster2.setSuperOysterIP(null);
			}catch(Exception e){
				mOyster2.setSuperOysterIP(null);
				e.printStackTrace();
			}	
		}
		List<Action> listActions=wMgmt.getEntityActionsHistory(mainOntoReply, localRegistryFrom, null);
		List<Action> listActionsLocal=wMgmt.getEntityActionsHistory(mainOntoReply, targetOntology, null);
		try{
			mOyster2.setSuperOysterIP(mOyster2.getPushChangesToOysterIP());
			WorkflowManagement wMgmtSOEnd = new WorkflowManagement();
			for (Action ac : listActions){
				boolean ins = false;
				for (Action acLocal : listActionsLocal){
					if (acLocal.getURI().equalsIgnoreCase(ac.getURI()))
						ins = true;
				}
				if (!ins){
					if (ac instanceof EntityAction){
						mOyster2.getLogger().info("will copy action..."+ac.getURI());
						if (ac instanceof Delete) wMgmtSOEnd.delete(((Delete)ac).getRelatedChange(), ((Delete)ac).getPerformedBy(), mainOntoReply);
						else if (ac instanceof RejectToApproved) wMgmtSOEnd.rejectToApproved(((RejectToApproved)ac).getRelatedChange(), ((RejectToApproved)ac).getPerformedBy(), mainOntoReply);
						else if (ac instanceof RejectToBeApproved) wMgmtSOEnd.rejectToBeApproved(((RejectToBeApproved)ac).getRelatedChange(), ((RejectToBeApproved)ac).getPerformedBy(), mainOntoReply);
						else if (ac instanceof RejectToDraft) wMgmtSOEnd.rejectToDraft(((RejectToDraft)ac).getRelatedChange(), ((RejectToDraft)ac).getPerformedBy(), mainOntoReply);
						else if (ac instanceof SendToApproved) wMgmtSOEnd.submitToApproved(((SendToApproved)ac).getRelatedChange(), ((SendToApproved)ac).getPerformedBy(), mainOntoReply);
						else if (ac instanceof SendToBeApproved) wMgmtSOEnd.submitToBeApproved(((SendToBeApproved)ac).getRelatedChange(), ((SendToBeApproved)ac).getPerformedBy(), mainOntoReply);
					}
				}
			}
			mOyster2.setSuperOysterIP(null);
		}catch(Exception e){
			mOyster2.setSuperOysterIP(null);
			e.printStackTrace();
		}
	}
}


