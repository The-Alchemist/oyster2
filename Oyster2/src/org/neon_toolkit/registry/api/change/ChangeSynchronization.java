package org.neon_toolkit.registry.api.change;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.neon_toolkit.omv.api.core.OMVOntology;
import org.neon_toolkit.omv.api.extensions.change.OMVChange;
import org.neon_toolkit.registry.api.individuals.ProcessOMVIndividuals;
import org.neon_toolkit.registry.core.AdvertInformer;
import org.neon_toolkit.registry.oyster2.Constants;
import org.neon_toolkit.registry.oyster2.Oyster2Factory;
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
		}catch(Exception e){
			System.out.println(e+" "+e.getMessage()+" "+e.getCause()+" "+e.getStackTrace()+" error! when connect to remote peers in synchonizing changes,some peer may not start the server!");
		}
		System.out.println("finished change synchronization...");
		mOyster2.getLogger().info("finished change synchronization...");
	}
	
	public synchronized static void SyncrhonizeChangesWithRegistry(Ontology remoteRegistry,Individual ontoindiv,Ontology targetOntology){
		OMVOntology mainOntoReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(ontoindiv, "ontology",targetOntology);
		if (cMgmt.isHistoryOlder(mainOntoReply, targetOntology, remoteRegistry)){
			String startsHere = cMgmt.getLastChangeIdFromLog(mainOntoReply, targetOntology);
			List<OMVChange> list;
			if (startsHere!=null && !startsHere.equalsIgnoreCase(""))
				list=cMgmt.getTrackedChanges(mainOntoReply, remoteRegistry, startsHere);
			else
				list=cMgmt.getTrackedChanges(mainOntoReply, remoteRegistry, null);
			mOyster2.getLogger().info("will copy ..."+list.size());
			for (int i=list.size()-1;i>=0;i--){
				OMVChange c = (OMVChange)list.get(i);
				mOyster2.getLogger().info("will copy ..."+c.getURI());
				cMgmt.register(c);
			}
		}	
	}
	
	public synchronized static void SyncrhonizeChangesWithPeer(Ontology remoteRegistry,Individual peerIndiv,Ontology targetOntology){
		Collection ontologySetRemote = localInformer.getTrackedOntologies(remoteRegistry,peerIndiv);
		Collection ontologySetLocal = localInformer.getTrackedOntologies(targetOntology,peerIndiv);
		
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
						if (startsHere!=null && !startsHere.equalsIgnoreCase(""))
							list=cMgmt.getTrackedChanges(mainOntoReply, remoteRegistry, startsHere);
						else
							list=cMgmt.getTrackedChanges(mainOntoReply, remoteRegistry, null);
						mOyster2.getLogger().info("will copy ..."+list.size());
						for (int i=list.size()-1;i>=0;i--){
							OMVChange c = (OMVChange)list.get(i);
							mOyster2.getLogger().info("will copy ..."+c.getURI());
							cMgmt.register(c);
						}
						
					}
				}
			}
		}
	}
}


