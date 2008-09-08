package org.neontoolkit.registry.core;


import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.neontoolkit.registry.oyster2.Constants;
import org.neontoolkit.registry.oyster2.Oyster2Factory;
import org.neontoolkit.registry.oyster2.Properties;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.owl.elements.*;


public class ExchangeInitiator implements Runnable{
	/**
	 * The maximum of failed trys to contact a fidget peer before trying to contact a bootstrap peer.
	 */
	//private static int MAX_FAILED_COUNT = 5;

	/**
	 * The min. time to sleep between two exchanges (30 sec.).
	 */
	private static int MIN_SLEEP_TIME = 30000;
	/**
	 * The time to sleep between two exchanges (30 sec.).
	 */
	private static int SLEEP_TIME = 120000;

	/**
	 * Pause initiating or not.
	 */
	//private boolean mPauseFlag = false;
	//private boolean isExchangeTime = false;

	/**
	 * The Oyster2 facility.
	 */
	private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private Properties mprop = mOyster2.getProperties();
	
	/**
	 * The local Analyzer.
	 */
	private AdvertInformer localInformer = mOyster2.getLocalAdvertInformer();
	
	/**
	 * Shutdown or not.
	 */
	private boolean mShutdownFlag = false;

	/**
	 * Constructs the Exchanger.
	 */
	public ExchangeInitiator() {
		if ((mprop.getString(Constants.discoveryFrec) != null)
				&& (mprop.getString(Constants.discoveryFrec).length() > 0)){
			SLEEP_TIME= mprop.getInteger(Constants.discoveryFrec);
		}
	}
	
	/**
	 * First advertises to rendezvousPeers and send them local peer information e.g.
	 * Peer object, Ontologies (limited to resourceLocator, URI, name), Mappings
	 * (limited to URI, name), Domains.
	 * Second, updates the local peer with the list of the rendezvous known peers
	 * It retrieves the peer information of every known peer and add it or update 
	 * it locally e.g. Peer object, Ontologies (limited to resourceLocator, 
	 * URI, name), Mappings (limited to URI, name), Domains. 
	 *
	 */
	public void randomExchange(){
		Map peerSet = localInformer.getRendezVousPeers(localInformer.getLocalRegistry());
		//Ontology localRegistry = mKaonP2P.getLocalRegistryOntology();
		Ontology localRegistry = mOyster2.getLocalHostOntology();
		Ontology remoteRegistry = null;
		Individual lPeer= localInformer.getLocalPeerIndiv(localRegistry);
		
		try{
			Collection peers = peerSet.values();
			Iterator it = peers.iterator(); 
			//System.out.println("remote rendezvousPeers size: "+peers.size());
			while(it.hasNext()){
				if (mShutdownFlag) return;
				String IP = it.next().toString();
				if (!IP.equalsIgnoreCase(localInformer.getPeerIP(localRegistry, lPeer))){
					mOyster2.getLogger().info("rendezvous peers IP: "+IP);
					remoteRegistry = localInformer.openRemoteRegistry(IP);
					if (remoteRegistry!=null){
						//System.out.println("remoteOpened: "+remoteRegistry.getPhysicalURI());
						localInformer.informerIP(remoteRegistry,localRegistry, IP);
						localInformer.updateRegistry(remoteRegistry,localRegistry); //NOT NECESSARY (BUT TO FORCE EXCHANGE IN THE NEXT CALL WITH NEW PEERS)...
					}
					else {
						//System.out.println("Didn't find rendezvousPeers peer on randomExchange");
					}
				}
			}
		}catch(Exception e){
			System.out.println("error! when connect to remote rendezvous peers in randomExchangeInitor,some peer may not start the server!");
		}
	}
	
	public void expertiseExchange(){
		//long lastTime = System.currentTimeMillis();
		boolean keepGoing=true;
		while(keepGoing){
			mOyster2.getLogger().info("informer process starts..."+System.currentTimeMillis());
			exchange();
			if (mShutdownFlag)
				return;
			mOyster2.getLogger().info("informer process finish, now to sleep..."+System.currentTimeMillis());
			keepGoing=this.sleep(sleepTime());
		}
	}
	
	/**
	 * Contacts every known peer (i.e. remote peer). For each remote peer, updates the 
	 * local peer with the list of the remote peer known peers.
	 * It retrieves the peer information of every known peer and add it or update 
	 * it locally e.g. Peer object, Ontologies (limited to resourceLocator, 
	 * URI, name), Mappings (limited to URI, name), Domains.
	 *
	 */
	public void exchange(){
		Collection peerList = localInformer.getPeerList(localInformer.getLocalRegistry());
		Ontology localRegistry = localInformer.getLocalRegistry();
		Ontology remoteRegistry = null;
		String IP= "";
		Individual lPeer = localInformer.getLocalPeerIndiv(localRegistry);
		try{
			Iterator it = peerList.iterator(); 
			while(it.hasNext()){
				if (mShutdownFlag) return;
				Individual peerIndiv = (Individual)it.next();
				if(peerIndiv!=lPeer &&	!mOyster2.isOfflinePeer(peerIndiv.getURI())){
					IP = localInformer.getPeerIP(localRegistry,peerIndiv);
					mOyster2.getLogger().info("Attempt to connect with peer: "+IP+" from exchange process");
					remoteRegistry = localInformer.openRemoteRegistry(IP);
					if (remoteRegistry!=null){
						//System.out.println("remoteOpened: "+remoteRegistry.getPhysicalURI());
						localInformer.updateRegistry(remoteRegistry,localRegistry);
					}
					else{
						//System.out.println("Didn't find peer on Exchange");
						mOyster2.addOfflinePeer(peerIndiv.getURI());
					}
				}
				else localInformer.updateLocalRegistry();
			}
			mOyster2.updateOfflinePeerList();
		}catch(Exception e){
			System.out.println(e+" "+e.getMessage()+" "+e.getCause()+" "+e.getStackTrace()+" error! when connect to remote peers in randomExchangeInitor,some peer may not start the server!");
		}
		
	}
	/**
	 * Sleeps the delivered amount of time.
	 *
	 * @param time the time to sleep.
	 * @return <code>true</code> if the sleep was successful, <code>false</code> if interrupted.
	 */
	private boolean sleep(long time) {
		try {
			Thread.sleep(time);
			return true;
		} catch (InterruptedException e) {
			return false;
		}
	}

	/**
	 * Returns the time to sleep between two Exchanges.
	 *
	 * @return the time to sleep.
	 */
	private long sleepTime() {
		// sleep time
		int time = SLEEP_TIME;
		// as longer the path as longer to sleep
		
	
		// if the computed time is too short, set it to the minimum
		if (SLEEP_TIME < MIN_SLEEP_TIME)
			time = MIN_SLEEP_TIME;
		// if i'm in bootstrap process, reduce the sleep time
		return time;
	}

	/**
	 * Shutdown.
	 */
	synchronized public void shutdown() {
		mShutdownFlag = true;
	}
	/**
	 * Starts the Exchanger.
	 */
	public void run(){
			mOyster2.getLogger().info("ExchangeInitiator starting..."+System.currentTimeMillis());
			if (mShutdownFlag)
				return;
			mOyster2.getLogger().info("Rendezvouz exchange starting..."+System.currentTimeMillis());
			randomExchange();
			mOyster2.getLogger().info("Rendezvouz exchange finished..."+System.currentTimeMillis());
			expertiseExchange();
			
			
		
	}

}
