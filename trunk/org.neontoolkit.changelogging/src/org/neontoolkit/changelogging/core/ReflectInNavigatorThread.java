package org.neontoolkit.changelogging.core;


import java.text.DateFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;
import org.neontoolkit.registry.api.Oyster2Connection;
import org.neontoolkit.registry.api.Oyster2Manager;
import org.neontoolkit.workflow.api.Action;
import org.neontoolkit.workflow.api.Action.EntityAction;
import org.neontoolkit.workflow.api.Action.EntityAction.Delete;
import org.neontoolkit.workflow.api.Action.EntityAction.Insert;
import org.neontoolkit.workflow.api.Action.EntityAction.RejectToApproved;
import org.neontoolkit.workflow.api.Action.EntityAction.RejectToDraft;
import org.neontoolkit.workflow.api.Action.EntityAction.SendToBeDeleted;
import org.neontoolkit.workflow.api.Action.EntityAction.Update;
import org.neontoolkit.omv.api.core.*;
import org.neontoolkit.omv.api.extensions.change.OMVChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange.Addition;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange.Removal;

public class ReflectInNavigatorThread implements Runnable {
	public static Oyster2Connection oyster2Conn = null;
	private Shell shell;
	
	public ReflectInNavigatorThread (Shell arg){
		this.shell=arg;
	}
	
	public void run() {
		shell.getDisplay().asyncExec(new Runnable () {
            public void run () {	
            	Job exportJob = new Job("Synchronizing changes...") {
            		protected IStatus run(IProgressMonitor monitor) {
            			monitor.beginTask("Synchronizing changes...", 100);
            			try {
            				//Get the last changeid from log
            				Set<String> ids = new HashSet<String>();
            				Map<String,String> timestamps = new HashMap<String,String>();
            				oyster2Conn = StartRegistry.connection;
            				Set<OMVOntology> tOntos = oyster2Conn.getOntologiesWithChanges();
            				for (OMVOntology onto: tOntos){
            					ids.add(oyster2Conn.getLastChangeIdFromLog(onto));
            					List<Action> allActions=oyster2Conn.getEntityActionsHistory(onto, null);
            					if (allActions.size()>0) {
            						Action lastAction = (Action)allActions.get(allActions.size()-1);
            						timestamps.put(getOntologyID(onto),lastAction.getTimestamp());
            					}
            					else timestamps.put(getOntologyID(onto),"");
            				}
            				monitor.worked(20);
            				//Syncrhonize changes
            				oyster2Conn.syncrhonizeChangesWithKnownPeersNow();
            				
            				//Wait until synchonization is finished
            				boolean found=false;
            				int numThreads = Thread.activeCount();
            		        Thread[] threads = new Thread[numThreads*2];
            		        numThreads = Thread.enumerate(threads);
            		        // Enumerate each thread
            		        for (int i=0; i<numThreads; i++) {
            		            Thread thread = threads[i];
            		            if (thread!=Thread.currentThread()){ 
            		            	//ThreadGroup root = thread.getThreadGroup().getParent();
            		            	//if (root==Thread.currentThread().getThreadGroup()){
            		            		try{
            		            			//System.out.println("thread: "+thread.getName());
            		            			//thread.interrupt();
            		            			if (thread.getName().equalsIgnoreCase("ChangesSynchronizer")){
            		            				found=true;
            		            				System.out.println("will wait at:"+System.currentTimeMillis());
            		            				thread.join();
            		            				monitor.worked(40);
            		            				System.out.println("Finished. Ready to reflect in navigator "+System.currentTimeMillis());
            		            				reflect(ids, tOntos);
            		            				monitor.worked(20);
            		            				reflectActions(timestamps);
            		            				monitor.worked(20);
            		            			}	
            		            		} catch (Exception e) {
            		            			e.printStackTrace();
            		            		}
            		            	//}
            		            }
            		        }
            		        if (!found){
            		        	monitor.worked(40);
            		        	System.out.println("No Running syncthread. Ready to reflect in navigator");
            		        	reflect(ids, tOntos);
            		        	monitor.worked(20);
	            				reflectActions(timestamps);
	            				monitor.worked(20);
            		        }
            			}catch (Exception e) {
            				openMessage("Synchronization failed");
            				e.printStackTrace();
            			}	
            			finally{
            				monitor.done();
            			}
            			return Status.OK_STATUS;
            		}
        		};
        		exportJob.setUser(true);
    			exportJob.schedule();
            }
        });		
	}
	
	public void reflect(Set<String> p1, Set<OMVOntology> p2){
		//First reflect changes to ontologies that already HAD changes before syncrhonization		
		for (String id : p1){
			OMVChange t = oyster2Conn.getChange(id);
			List<OMVChange> toApply = oyster2Conn.getChanges(t.getAppliedToOntology(), id);
			if (toApply!=null && toApply.size()>0){
				Collections.reverse(toApply);
				System.out.println("To Apply (adding to existing changes of the ontology):");
				System.out.println(Oyster2Manager.serializeOMVChanges(toApply));
				ApplyChangesFromLogToNTK.applyChanges(toApply,t.getAppliedToOntology());
			}
		}
		System.out.println();
		//Here reflect changes to ontologies that didnt have changes before syncrhonization but now they have
		Set<OMVOntology> tOntos = oyster2Conn.getOntologiesWithChanges();
		for (OMVOntology o1 : tOntos){
			boolean already=false;
			for (OMVOntology o2 : p2)
				if (getOntologyID(o1).equalsIgnoreCase(getOntologyID(o2))) already=true;
			if (!already){
				List<OMVChange> toApply = oyster2Conn.getChanges(o1,null);
				if (toApply!=null && toApply.size()>0){
					Collections.reverse(toApply);
					System.out.println("To Apply (initial set of changes for the ontology):");
					System.out.println(Oyster2Manager.serializeOMVChanges(toApply));
					ApplyChangesFromLogToNTK.applyChanges(toApply,o1);
				}
			}
		}
	}
	
	public void reflectActions(Map<String, String> timestamps){
		Set<OMVOntology> tOntos = oyster2Conn.getOntologiesWithChanges();
		for (OMVOntology o1 : tOntos){
			List<Action> allActions=oyster2Conn.getEntityActionsHistory(o1, null);
			if (allActions.size()>0){ //ontology has actions
				String ontoId=getOntologyID(o1);
				if (timestamps.size()>0 && timestamps.get(ontoId)!=null && timestamps.get(ontoId)!=""){ //ontology had actions
					Date oldDate;
					try {
						oldDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).parse(timestamps.get(ontoId));
						List<Action> listAcToApply = new LinkedList<Action>();
						for (Action acToApply: allActions){
							Date acDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).parse(acToApply.getTimestamp());
							if (acDate.after(oldDate)) listAcToApply.add(acToApply);
						}
						reflectRequiredActions(listAcToApply,allActions);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}
				else{ //ontology didnt have actions
					reflectRequiredActions(allActions,allActions);
				}
			}
		}
	}
	
	
	public void reflectRequiredActions(List<Action> listApplyActions, List<Action> allActions){
		for (Action ac : listApplyActions){
			try{
				if (ac instanceof Delete){ //ONLY APPLY ACTION WHEN THE ELEMENT WAS SENT TO DRAFT
					System.out.println("checking if apply delete action: "+ac.getURI());
					OMVChange c = oyster2Conn.getChange(((Delete)ac).getRelatedChange());
					if (listApplyActions.size()>1){
						List<Action> prevAsso = new LinkedList<Action>();
						Date deleteDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).parse(((Delete)ac).getTimestamp());
						for (Action temp : allActions){ //GET THE PREVIOUS ACTIONS TO DELETE TO THE SAME CHANGE
							if (temp instanceof EntityAction) {
								String associated=((EntityAction)temp).getRelatedChange();
								if (((Delete)ac).getRelatedChange().equalsIgnoreCase(associated)){
									Date deleteAssoDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).parse(((EntityAction)temp).getTimestamp());
									if (deleteAssoDate.before(deleteDate))
										prevAsso.add(temp);
								}
							}
						}
						if (prevAsso.size()>0){ //CHECK WHAT KIND OF ACTION WAS THE EXACT PREVIOUS ONE
							System.out.println("checking if apply delete action - previous action: "+prevAsso.get(prevAsso.size()-1));
							Action prev =prevAsso.get(prevAsso.size()-1); //=listApplyActions.get(listApplyActions.size()-2);
							if (prev!=null && (prev instanceof Insert || prev instanceof Update || prev instanceof RejectToDraft) )
								applyActionDelete(c);
						}
					}
				}
				else if (ac instanceof SendToBeDeleted){ //APPLY ACTION ALWAYS EXCEPT WHEN IT WAS DELETED FROM NAVIGATOR STRAIGHT
					System.out.println("checking if apply sendtobedeleted action: "+ac.getURI());
					OMVChange c = oyster2Conn.getChange(((SendToBeDeleted)ac).getRelatedChange());
					List<Action> prevAsso = new LinkedList<Action>();
					Date sendToBeDeletedDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).parse(((SendToBeDeleted)ac).getTimestamp());
					for (Action temp : allActions){ //GET THE PREVIOUS ACTIONS TO SENDTOBEDELETED TO THE SAME CHANGE
						if (temp instanceof EntityAction) {
							String associated=((EntityAction)temp).getRelatedChange();
							if (((SendToBeDeleted)ac).getRelatedChange().equalsIgnoreCase(associated)){
								Date deleteAssoDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).parse(((EntityAction)temp).getTimestamp());
								if (deleteAssoDate.before(sendToBeDeletedDate))
									prevAsso.add(temp);
							}
						}
					}
					boolean apply=false;
					if (prevAsso.size()>0){ //CHECK WHAT KIND OF ACTIONS HAS THIS CHANGE ASSOCIATED
						for (Action prev : prevAsso)
							if (prev instanceof Insert || prev instanceof Update) apply=true;
							
					}
					if (apply) applyActionSendToBeDeleted(c);
				}
				else if (ac instanceof RejectToApproved){
					System.out.println("checking if apply rejecttoapproved action: "+ac.getURI());
					OMVChange c = oyster2Conn.getChange(((RejectToApproved)ac).getRelatedChange());
					applyActionRejectToApproved(c);
				}
			} catch(Exception e){
				e.printStackTrace();
			}			
		}
	}
	
	public void applyActionDelete(OMVChange change){
		//OMVChange change = oyster2Conn.getChange(changeURI);
		Set<OMVAtomicChange> aChanges = oyster2Conn.getAtomicChanges((OMVEntityChange)change);
		List<OMVChange> listToApply = new LinkedList<OMVChange>();
		for (OMVAtomicChange ac : aChanges){
			OMVAtomicChange changeInv=null;
			if (ac instanceof Addition){
				changeInv=new Removal();
			}
			else if (ac instanceof Removal){
				changeInv=new Addition();		
			}
			else {
				System.out.println("sth strange");
				return;
			}
			for (OMVPerson p : ac.getHasAuthor()) changeInv.addHasAuthor(p);
			if (ac.getAppliedAxiom()!=null) changeInv.setAppliedAxiom(ac.getAppliedAxiom());
			if (ac.getAppliedToOntology()!=null) changeInv.setAppliedToOntology(ac.getAppliedToOntology());
			for (String cc : ac.getCauseChange()) changeInv.addCauseChange(cc);
			if (ac.getCost()!=null) changeInv.setCost(ac.getCost());
			if (ac.getDate()!=null) changeInv.setDate(ac.getDate());
			if (ac.getHasPreviousChange()!=null) changeInv.setHasPreviousChange(ac.getHasPreviousChange());
			if (ac.getPriority()!=null) changeInv.setPriority(ac.getPriority());
			if (ac.getRelevance()!=null) changeInv.setRelevance(ac.getRelevance());
			if (ac.getTime()!=null) changeInv.setTime(ac.getTime());
			if (ac.getURI()!=null) changeInv.setURI(ac.getURI());
			if (ac.getVersion()!=null) changeInv.setVersion(ac.getVersion());
			listToApply.add(changeInv);
		}
		ApplyChangesFromLogToNTK.applyChanges(listToApply, change.getAppliedToOntology());
	}
	
	public void applyActionSendToBeDeleted (OMVChange change){
		//OMVChange change = oyster2Conn.getChange(changeURI);
		
		boolean inverse=false;
		List<Action> acs= oyster2Conn.getEntityActionsHistory(change.getAppliedToOntology(), null); //DONT CARE ORDER
		for (Action action : acs){
			if (action instanceof EntityAction){
				EntityAction eaction = (EntityAction) action;
				if (eaction.getRelatedChange().equalsIgnoreCase(change.getURI()) && ((eaction instanceof Insert) || (eaction instanceof Update)))
					inverse = true;
			}
		}
		
		Set<OMVAtomicChange> aChanges = oyster2Conn.getAtomicChanges((OMVEntityChange)change);
		List<OMVChange> listToApply = new LinkedList<OMVChange>();
		for (OMVAtomicChange ac : aChanges){
			OMVAtomicChange changeInv=null;
			if (ac instanceof Addition){
				if (inverse)
					changeInv=new Removal();
				else
					changeInv=new Addition();
			}
			else if (ac instanceof Removal){
				if (inverse)
					changeInv=new Addition();
				else
					changeInv=new Removal();
			}
			else {
				System.out.println("sth strange");
				return;
			}
			for (OMVPerson p : ac.getHasAuthor()) changeInv.addHasAuthor(p);
			if (ac.getAppliedAxiom()!=null) changeInv.setAppliedAxiom(ac.getAppliedAxiom());
			if (ac.getAppliedToOntology()!=null) changeInv.setAppliedToOntology(ac.getAppliedToOntology());
			for (String cc : ac.getCauseChange()) changeInv.addCauseChange(cc);
			if (ac.getCost()!=null) changeInv.setCost(ac.getCost());
			if (ac.getDate()!=null) changeInv.setDate(ac.getDate());
			if (ac.getHasPreviousChange()!=null) changeInv.setHasPreviousChange(ac.getHasPreviousChange());
			if (ac.getPriority()!=null) changeInv.setPriority(ac.getPriority());
			if (ac.getRelevance()!=null) changeInv.setRelevance(ac.getRelevance());
			if (ac.getTime()!=null) changeInv.setTime(ac.getTime());
			if (ac.getURI()!=null) changeInv.setURI(ac.getURI());
			if (ac.getVersion()!=null) changeInv.setVersion(ac.getVersion());
			listToApply.add(changeInv);
		}
		ApplyChangesFromLogToNTK.applyChanges(listToApply, change.getAppliedToOntology());
	}
	
	public void applyActionRejectToApproved (OMVChange change){
		boolean noinverse=false;
		List<Action> acs= oyster2Conn.getEntityActionsHistory(change.getAppliedToOntology(), null); //DONT CARE ORDER
		for (Action action : acs){
			if (action instanceof EntityAction){
				EntityAction eaction = (EntityAction) action;
				if (eaction.getRelatedChange().equalsIgnoreCase(change.getURI()) && ((eaction instanceof Insert) || (eaction instanceof Update)))
					noinverse = true;
			}
		}
		
		Set<OMVAtomicChange> aChanges = oyster2Conn.getAtomicChanges((OMVEntityChange)change);
		List<OMVChange> listToApply = new LinkedList<OMVChange>();
		for (OMVAtomicChange ac : aChanges){
			OMVAtomicChange changeInv=null;
			if (ac instanceof Addition){
				if (!noinverse)
					changeInv=new Removal();
				else
					changeInv=new Addition();
			}
			else if (ac instanceof Removal){
				if (!noinverse)
					changeInv=new Addition();
				else
					changeInv=new Removal();
			}
			else {
				System.out.println("sth strange");
				return;
			}
			for (OMVPerson p : ac.getHasAuthor()) changeInv.addHasAuthor(p);
			if (ac.getAppliedAxiom()!=null) changeInv.setAppliedAxiom(ac.getAppliedAxiom());
			if (ac.getAppliedToOntology()!=null) changeInv.setAppliedToOntology(ac.getAppliedToOntology());
			for (String cc : ac.getCauseChange()) changeInv.addCauseChange(cc);
			if (ac.getCost()!=null) changeInv.setCost(ac.getCost());
			if (ac.getDate()!=null) changeInv.setDate(ac.getDate());
			if (ac.getHasPreviousChange()!=null) changeInv.setHasPreviousChange(ac.getHasPreviousChange());
			if (ac.getPriority()!=null) changeInv.setPriority(ac.getPriority());
			if (ac.getRelevance()!=null) changeInv.setRelevance(ac.getRelevance());
			if (ac.getTime()!=null) changeInv.setTime(ac.getTime());
			if (ac.getURI()!=null) changeInv.setURI(ac.getURI());
			if (ac.getVersion()!=null) changeInv.setVersion(ac.getVersion());
			listToApply.add(changeInv);
		}
		ApplyChangesFromLogToNTK.applyChanges(listToApply, change.getAppliedToOntology());
	}
	
	private String getOntologyID(OMVOntology o){
		String tURN="";
		if (o==null) return "";
		if (o.getURI()!=null){
			tURN=o.getURI();
			boolean hasVersion=false;
			if (o.getVersion()!=null){
				tURN=tURN+"?version="+o.getVersion();//+";";
				hasVersion=true;
			}
			if (o.getResourceLocator()!=null){
				if (!hasVersion) tURN=tURN+"?";
				else tURN=tURN+";";
				tURN=tURN+"location="+o.getResourceLocator();
			}
			tURN=tURN.replace(" ", "_");
		}
		return tURN;
	}
	public void openMessage(final String mess){
		shell.getDisplay().asyncExec(new Runnable() {
	           public void run() {
	        	   MessageDialog.openInformation(
           				shell,
           				"Change Capturing Plug-in",
           				mess);
	            }
		});
	}
}


/*
public void AAreflectRequiredActions(List<OMVChange> listChanges){
	for (OMVChange c : listChanges){
		if (c instanceof OMVEntityChange){
			List<Action> listActions = oyster2Conn.getChangeActions(c);
			for (Action ac : listActions){
				if (ac instanceof Delete){ //ONLY APPLY ACTION WHEN THE ELEMENT WAS SENT TO DRAFT
					Action prev=listActions.get(listActions.size()-2);
						if (prev!=null && (prev instanceof Insert || prev instanceof Update || prev instanceof RejectToDraft) )
							applyActionDelete(c); 
				}
				else if (ac instanceof SendToBeDeleted){ //APPLY ACTION ALWAYS EXCEPT WHEN IS THE ONLY ACTION (IT MEANS IT WAS DELETED FROM NAVIGATOR STRAIGHT)
					if (listActions.size()>1)
						applyActionSendToBeDeleted(c);
					
				}
				else if (ac instanceof RejectToApproved){
					applyActionRejectToApproved(c);
				}
					
			}
		}
	}
}
*/
