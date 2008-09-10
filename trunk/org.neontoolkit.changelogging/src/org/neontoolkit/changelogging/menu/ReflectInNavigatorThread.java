package org.neontoolkit.changelogging.menu;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;
import org.neontoolkit.registry.api.Oyster2Connection;
import org.neontoolkit.registry.api.Oyster2Manager;
import org.neontoolkit.omv.api.core.*;
import org.neontoolkit.omv.api.extensions.change.OMVChange;

public class ReflectInNavigatorThread implements Runnable {
	public static Oyster2Connection oyster2Conn = null;
	
	
	public void run() {
		//Get the last changeid from log
		Set<String> ids = new HashSet<String>();
		oyster2Conn = StartRegistry.connection;
		Set<OMVOntology> tOntos = oyster2Conn.getOntologiesWithChanges();
		Iterator<OMVOntology> it = tOntos.iterator();
		while (it.hasNext()){
			ids.add(oyster2Conn.getLastChangeIdFromLog((OMVOntology)it.next()));
		}
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
            				System.out.println("will wait "+System.currentTimeMillis());
            				thread.join();
            				System.out.println("Finished. Ready to reflect in navigator "+System.currentTimeMillis());
            				reflect(ids, tOntos);
            			}	
            		} catch (Exception e) {
            			e.printStackTrace();
            		}
            	//}
            }
        }
        if (!found){
        	System.out.println("No Running syncthread. Ready to reflect in navigator");
        	reflect(ids, tOntos);
        }

	}
	
	public void reflect(Set<String> p1, Set<OMVOntology> p2){
		//First reflect changes to ontologies that already HAD changes before syncrhonization
		
		for (String id : p1){
			OMVChange t = oyster2Conn.getChange(id);
			List<OMVChange> toApply = oyster2Conn.getChanges(t.getAppliedToOntology(), id);
			System.out.println("To Apply:");
			System.out.println(Oyster2Manager.serializeOMVChanges(toApply));
			ApplyChangesFromLogToNTK.applyChanges(toApply,t.getAppliedToOntology());
		}
		//Here reflect changes to ontologies that didnt have changes before syncrhonization but now they have
		Set<OMVOntology> tOntos = oyster2Conn.getOntologiesWithChanges();
		for (OMVOntology o1 : tOntos){
			boolean already=false;
			for (OMVOntology o2 : p2)
				if (getOntologyID(o1).equalsIgnoreCase(getOntologyID(o2))) already=true;
			if (!already){
				List<OMVChange> toApply = oyster2Conn.getChanges(o1,null);
				System.out.println("To Apply:");
				System.out.println(Oyster2Manager.serializeOMVChanges(toApply));
				ApplyChangesFromLogToNTK.applyChanges(toApply,o1);
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
