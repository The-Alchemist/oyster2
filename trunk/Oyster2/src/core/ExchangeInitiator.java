package core;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import oyster2.Oyster2;

import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.owl.elements.*;


public class ExchangeInitiator implements Runnable{
	/**
	 * The maximum of failed trys to contact a fidget peer before trying to contact a bootstrap peer.
	 */
	private static int MAX_FAILED_COUNT = 5;

	/**
	 * The min. time to sleep between two exchanges (30 sec.).
	 */
	private static int MIN_SLEEP_TIME = 30000;

	/**
	 * Pause initiating or not.
	 */
	private boolean mPauseFlag = false;
	private boolean isExchangeTime = false;

	/**
	 * The Oyster2 facility.
	 */
	private Oyster2 mOyster2 = Oyster2.sharedInstance();
	
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
	}
	
	public void randomExchange(){
		Map peerSet = localInformer.getRendezVousPeers(localInformer.getLocalRegistry());
		//Ontology localRegistry = mKaonP2P.getLocalRegistryOntology();
		Ontology localRegistry = mOyster2.getLocalHostOntology();
		Ontology remoteRegistry = null;
		try{
			Collection peers = peerSet.values();
			Iterator it = peers.iterator(); 
			System.out.println("remote rendezvousPeers size: "+peers.size());
			while(it.hasNext()){
				String IP = it.next().toString();
				System.out.println("rendezvous peers IP: "+IP);
				remoteRegistry = localInformer.openRemoteRegistry(IP);
				if (remoteRegistry!=null){
					System.out.println("remoteOpened: "+remoteRegistry.getPhysicalURI());
					localInformer.informerIP(remoteRegistry,localRegistry);
					localInformer.updateRegistry(remoteRegistry,localRegistry);
				}
				else {
					System.out.println("Didn't find peer on randomExchange");
				}
			}
		}catch(Exception e){
			System.out.println("error! when connect to remote rendezvous peers in randomExchangeInitor,some peer may not start the server!");
		}
	}
	
	public void expertiseExchange(){
		long lastTime = System.currentTimeMillis();
		while(true){
			exchange();

			this.sleep(120000);

		}
	}
	public void exchange(){
		Collection peerList = localInformer.getPeerList(localInformer.getLocalRegistry());
		Ontology localRegistry = localInformer.getLocalRegistry();
		Ontology remoteRegistry = null;
		String IP= "";
		try{
			Iterator it = peerList.iterator(); 
			while(it.hasNext()){
				Individual peerIndiv = (Individual)it.next(); 
				if(peerIndiv!=localInformer.getLocalPeerIndiv(localRegistry)){
					IP = localInformer.getPeerIP(localRegistry,peerIndiv);
					System.out.println("exchange with peer: "+IP);
					remoteRegistry = localInformer.openRemoteRegistry(IP);
					if (remoteRegistry!=null){
						System.out.println("remoteOpened: "+remoteRegistry.getPhysicalURI());
						localInformer.updateRegistry(remoteRegistry,localRegistry);
					}
					else{
						System.out.println("Didn't find peer on Exchange");
					}
				}
				else localInformer.updateLocalRegistry();
			}
			
		}catch(Exception e){
			System.out.println("error! when connect to remote rendezvous peers in randomExchangeInitor,some peer may not start the server!");
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
			Thread.currentThread().sleep(time);
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
		// minimal sleep time
		int time = MIN_SLEEP_TIME;
		// as longer the path as longer to sleep
		
	
		// if the computed time is too short, set it to the minimum
		if (time < MIN_SLEEP_TIME)
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
		
			if (mShutdownFlag)
				return;
			
			randomExchange();
			System.out.println("exchangeInitiator finished.");
			expertiseExchange();
			
			
		
	}

}
