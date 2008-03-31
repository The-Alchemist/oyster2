package org.neon_toolkit.registry.core;

import org.neon_toolkit.registry.oyster2.Oyster2Factory;
import org.neon_toolkit.registry.oyster2.Oyster2Query;
import org.neon_toolkit.registry.oyster2.QueryReply;
import org.neon_toolkit.registry.util.Resource;
import org.semanticweb.kaon2.api.Ontology;

/**
 * The class ConcurrentSearcher allows to 
 * Oyster to run concurrently in a pool thread
 * queries to many peers   
 * @author Raul Palma
 * @version 2.0, March 2008
 */

public class ConcurrentSearcher implements Runnable {
	
	/**
	 * The Oyster2 facility.
	 */
	private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	
	/**
	 * The local Expertise Registry.
	 */
	private LocalExpertiseRegistry localRegistry = mOyster2.getLocalExpertiseRegistry();
	
	/**
	 * The query to process.
	 */
	private Ontology registry;
	private Oyster2Query typeQuery;
	private Oyster2Query topicQuery;
	private int resourceType;
	private int which;
	private boolean general;
	private boolean mShutdownFlag=false;
	

	/**
	 * Creates a new Searcher for a received search request.
	 *
	 * @param host  the requesting host.
	 * @param query the query.
	 */
	public ConcurrentSearcher(Ontology registry, Oyster2Query topicQuery, Oyster2Query typeQuery, int resourceType, int which, boolean general) {
		this.registry = registry;
		this.topicQuery = topicQuery;
		this.typeQuery = typeQuery;
		this.resourceType=resourceType;
		this.which=which;
		this.general=general;
	}
	
		/**
	 * Returns the search result to the user.
	 *
	 * @param queryReply the query hit.
	 */
	private void returnResult(QueryReply queryReply) {
       mOyster2.getSearchManager().notifyReplyListener(queryReply);
	}
		
	synchronized public void shutdown() {
		mShutdownFlag = true;
	}
	

	/**
	 * Processes a search request.
	 */
	@SuppressWarnings("unchecked")
	public void run() {
		QueryReply queryReply =null;
		if (general){
			queryReply = localRegistry.returnQueryReplyGeneral(registry,typeQuery,Resource.DataResource);
		}else{		
			if (which==1) queryReply = localRegistry.returnQueryReply(registry,typeQuery,resourceType);
			else if (which==2) queryReply = localRegistry.returnQueryReply(registry,topicQuery,typeQuery,resourceType);
		}
		if (!mShutdownFlag){
			returnResult(queryReply);
		}
	}
	
}