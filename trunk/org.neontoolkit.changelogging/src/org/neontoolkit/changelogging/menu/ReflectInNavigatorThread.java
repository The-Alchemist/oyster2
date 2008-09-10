package org.neontoolkit.changelogging.menu;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
import org.neontoolkit.omv.api.core.*;
import org.neontoolkit.omv.api.extensions.change.OMVChange;

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
            				oyster2Conn = StartRegistry.connection;
            				Set<OMVOntology> tOntos = oyster2Conn.getOntologiesWithChanges();
            				Iterator<OMVOntology> it = tOntos.iterator();
            				while (it.hasNext()){
            					ids.add(oyster2Conn.getLastChangeIdFromLog((OMVOntology)it.next()));
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
            		            				monitor.worked(40);
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
            		        	monitor.worked(40);
            		        }
            			}catch (Exception e) {
            				// TODO Auto-generated catch block
            				MessageDialog.openInformation(
            						shell,
            						"Change Capturing Plug-in",
            						"Synchronization failed");
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
				System.out.println("To Apply (adding to existing changes of the ontology):");
				System.out.println(Oyster2Manager.serializeOMVChanges(toApply));
				ApplyChangesFromLogToNTK.applyChanges(toApply,t.getAppliedToOntology());
			}
		}
		//Here reflect changes to ontologies that didnt have changes before syncrhonization but now they have
		Set<OMVOntology> tOntos = oyster2Conn.getOntologiesWithChanges();
		for (OMVOntology o1 : tOntos){
			boolean already=false;
			for (OMVOntology o2 : p2)
				if (getOntologyID(o1).equalsIgnoreCase(getOntologyID(o2))) already=true;
			if (!already){
				List<OMVChange> toApply = oyster2Conn.getChanges(o1,null);
				if (toApply!=null && toApply.size()>0){
					System.out.println("To Apply (initial set of changes for the ontology):");
					System.out.println(Oyster2Manager.serializeOMVChanges(toApply));
					ApplyChangesFromLogToNTK.applyChanges(toApply,o1);
				}
			}
		}
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
}
