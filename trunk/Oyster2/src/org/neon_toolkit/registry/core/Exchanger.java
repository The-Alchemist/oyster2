package org.neon_toolkit.registry.core;

import java.util.*;

import org.neon_toolkit.registry.oyster2.Exchange;
import org.neon_toolkit.registry.oyster2.Oyster2Factory;
import org.semanticweb.kaon2.api.*;



public class Exchanger implements Runnable{
	/**
	 * If set, no more process is allowed.
	 */
	private static boolean mShutdownFlag = false;
	
	/**
	 * The KaonP2P facility.
	 */
	private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	
	/**
	 * The local Analyzer.
	 */
	private AdvertInformer localInformer = mOyster2.getLocalAdvertInformer();
	
	/**
	 * The exchange Type.
	 */
	//private int exchangeType;
	
	/**
	 * The IP String of the remoteHost for exchange 
	 */
	//private String remoteHost;
	/**
	 * The local Expertise Registry
	 */
	//private LocalExpertiseRegistry localRegistry = mKaon.getLocalExpertiseRegistry();
	
	private Exchange mExchange;
	/**
	 * constructor for exchanger, indicate the exchange type:initorExchange or updateExchange;
	 * @param exchange
	 */
	public Exchanger(Exchange exchange){
		mExchange = exchange;
	}
	
	public void randomExchangeInitor() {
		try{
			Map peerSet = localInformer.getRendezVousPeers(localInformer.getLocalRegistry());
			Ontology localRegistry = localInformer.getLocalRegistry();
			Collection peers = peerSet.entrySet();
			Iterator it = peers.iterator(); 
			while(it.hasNext()){
				String IP = it.next().toString();
				Ontology remoteRegistry = localInformer.openRemoteRegistry(IP);
				localInformer.informerIP(remoteRegistry,localRegistry, IP);
			}
		}catch(Exception e){
			System.err.println("Exception thrown in randomExchangeInitor:Exchanger");
		}
	}
	
	/**
	 * Processes the exchange.
	 */
	public void run() {
		if (mShutdownFlag)
			return;
		try{
			if (mExchange.getExchangeType()==(mExchange.updateExchange)){
				Ontology remoteOntology = localInformer.openRemoteRegistry(mExchange.getRemoteHost());
			    Ontology localOntology = localInformer.getLocalRegistry();
			    localInformer.updateRegistry(remoteOntology,localOntology);
			    localInformer.save(localOntology);
			    localInformer.persist(localOntology);
			}
			else if(mExchange.getExchangeType()==(mExchange.initorExchange)){
				
			}
		}catch(Exception e){
			System.err.println("Exception thrown by openRemoteRegistry() in Exchanger.run() process");
		}
		
		

	}
}
