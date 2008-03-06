package org.neon_toolkit.registry.core;

import java.util.*;
import java.util.logging.Logger;

import org.neon_toolkit.registry.oyster2.Oyster2Factory;
import org.neon_toolkit.registry.oyster2.Oyster2Query;
import org.neon_toolkit.registry.oyster2.QueryReply;
import org.neon_toolkit.registry.util.Resource;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.owl.elements.*;
//import org.semanticweb.kaon2.api.*;
//import org.semanticweb.kaon2.api.owl.axioms.DataPropertyMember;


public class Searcher implements Runnable {

	/**
	 * The time to wait for Query Hit messages.
	 */
	//private static final int REPLY_TIMEOUT = 60000; // 1 min.

	/**
	 * The PGrid.Searcher logger.
	 */
	public static final Logger LOGGER = Logger.getLogger("PGrid.Searcher");

	/**
	 * The logging file.
	 */
	public static final String LOG_FILE = "Searcher.log";

	/**
	 * The list of all already contacted peers.
	 */
	//private Vector mAlreadyAsked = new Vector();

	/**
	 * The list of all already seen Querys.
	 */
	//private static Hashtable mAlreadySeen = new Hashtable();

	/**
	 * The requesting host.
	 */
	//private Oyster2Host mHost = null;

	/**
	 * The KaonP2P facility.
	 */
	private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	
	/**
	 * The local Expertise Registry.
	 */
	private LocalExpertiseRegistry localRegistry = mOyster2.getLocalExpertiseRegistry();
	/**
	 * The QueryReply Listener.
	 */
	//private QueryReplyListener mReplyListener = mKaonP2P.getQueryReplyListener();
	/**
	 * The Querylistener.
	 */
	private AdvertInformer mInformer = mOyster2.getLocalAdvertInformer();
	/**
	 * The query to process.
	 */
	private Oyster2Query typeQuery;
	private Oyster2Query topicQuery;
	private Set peerSet=null;
	private boolean normalSearchFlag = true;
	private boolean positiveResult = false;
	private boolean keywordSearch= false;
	private boolean mShutdownFlag=false;
	
	/**
	 * The amount of started remote searches.
	 */
	//private int mRemoteSearches = 0;

	static {
		/*LogFormatter formatter = new LogFormatter();
	    formatter.setDateFormat("HH:mm:ss");
		formatter.setFormatPattern(LogFormatter.DATE + ": " + LogFormatter.MESSAGE + LogFormatter.NEW_LINE + LogFormatter.THROWABLE);
		Constants.initChildLogger(LOGGER, formatter, LOG_FILE);*/
	}

	/**
	 * Creates a new Searcher for a received search request.
	 *
	 * @param host  the requesting host.
	 * @param query the query.
	 */
	public Searcher(Oyster2Query topicQuery, Oyster2Query typeQuery,Set peerSet, boolean manualSelected) {
		this.topicQuery = topicQuery;
		this.typeQuery = typeQuery;
		this.normalSearchFlag = true;
		this.keywordSearch = manualSelected;
		this.peerSet = peerSet;
	}
	
	/**
	 * Creates a new Searcher for a received search request.
	 *
	 * @param host  the requesting host.
	 * @param query the query.
	 */
	public Searcher(Oyster2Query topicQuery, Oyster2Query typeQuery,Set peerSet, boolean manualSelected, boolean normalFlag) {
		this.topicQuery = topicQuery;
		this.typeQuery = typeQuery;
		this.normalSearchFlag = normalFlag;
		this.keywordSearch = manualSelected;
		this.peerSet = peerSet;
	}
	
	/**
	 * Creates a new Searcher for a locally started search.
	 *
	 * @param peerSet the set of peers for which we search the ontologies provided by. 
	 */
	public Searcher(Set peerSet){
		this.peerSet = peerSet;
		this.normalSearchFlag = false;
		this.keywordSearch = true;
	}
	
	/**
	 * Returns the search result to the user.
	 *
	 * @param queryReply the query hit.
	 */
	private void returnResult(QueryReply queryReply) {
       mOyster2.getSearchManager().notifyReplyListener(queryReply);
	}
	/**
	 * return the found ontology Set to QueryReplyListener in SearchManager.
	 * @param ontologyDocSet
	 */
	private void returnResult(List ontologyDocSet){
		mOyster2.getSearchManager().notifyReplyListener(ontologyDocSet);
	}
		
	synchronized public void shutdown() {
		mShutdownFlag = true;
	}
	

	/**
	 * Processes a search request.
	 */
	public void run(){
		if(normalSearchFlag){ //QUERY OMV ONTOLOGIES
			QueryReply queryReply =null;
			if (!keywordSearch){
				if ((topicQuery!=null) && (topicQuery.getQueryString().length()>0)){ //THIS IS ONTOLOGYSEARCH QUERY?? DELETE??  
					queryReply = new QueryReply(topicQuery.getGUID(),QueryReply.TYPE_INIT);
					topicQuery.setStatus(Oyster2Query.STATUS_RUNNING);	
					//positiveResult = localRegistry.searchExpertiseOntology(mOyster2.getLocalHostOntology(),topicQuery,manualSelected);
					distributedQuery(3,topicQuery.getScope());
					if (!positiveResult ) {
						System.out.println("Bad request for '" + topicQuery.getQueryString() + "'.");
						queryReply = new QueryReply(topicQuery.getGUID(), QueryReply.TYPE_BAD_REQUEST);
					}
					returnResult(queryReply);
					returnResult(new QueryReply());
					topicQuery.setStatus(Oyster2Query.STATUS_FINISHED);
					return;
				}
				else if((typeQuery != null) && (typeQuery.getQueryString().length()>0)){ //THIS IS NORMAL QUERY WITH CONDITIONS
					typeQuery.setStatus(Oyster2Query.STATUS_RUNNING);
					//queryReply = localRegistry.returnQueryReply(mOyster2.getLocalHostOntology(),typeQuery,Resource.DataResource); //mKaonP2P.getVirtualOntology()
					distributedQuery(1,typeQuery.getScope());
					typeQuery.setStatus(Oyster2Query.STATUS_FINISHED);
					return;
				}
			}
			else{ //THIS IS QUERY WITH KEYWORDS AND/OR CONDITIONS
				topicQuery.setStatus(Oyster2Query.STATUS_RUNNING);
				//queryReply = localRegistry.returnQueryReply(mOyster2.getLocalHostOntology(),topicQuery,typeQuery,Resource.DataResource);
				distributedQuery(2,topicQuery.getScope());
				topicQuery.setStatus(Oyster2Query.STATUS_FINISHED);
				return;
			}
			//returnResult(queryReply);
		}
		else{ //QUERY OTHER OMV OBJECTS RATHER THAN ONTOLOGIES
			if (!keywordSearch){
				typeQuery.setStatus(Oyster2Query.STATUS_RUNNING);
				distributedQueryGeneral(1,typeQuery.getScope());
				typeQuery.setStatus(Oyster2Query.STATUS_FINISHED);
				return;
			}
			else{ //THIS IS QUERY IN REGISTRY VIEW WHEN SELECTING PEERS?? DELETE??
				Iterator it = peerSet.iterator();
				List ontologyDocSet = new ArrayList();
				while(it.hasNext()){
					Individual peerIndiv = (Individual)it.next();
					Collection ontologyCol= mInformer.getOntologyDoc(mOyster2.getLocalHostOntology(),peerIndiv);
					if(ontologyCol!=null)
						ontologyDocSet.addAll(ontologyCol);	
				}
				returnResult(ontologyDocSet);
			}
		}
	}
	
	public void distributedQuery(int which, int scope){
		QueryReply queryReply =null;
		
		AdvertInformer localInformer = mOyster2.getLocalAdvertInformer();
		Ontology localOntology = localInformer.getLocalRegistry();
		/*
		System.out.println("starting distributedquery method...");
		System.out.println("which: "+which);
		System.out.println("scope: "+scope);
		if (topicQuery!=null) System.out.println("topicquery: "+topicQuery.getQueryString());
		if (typeQuery!=null) System.out.println("typequery: "+typeQuery.getQueryString());
		*/
		
		if (mShutdownFlag) return;
		if (scope==Oyster2Query.Local_Scope || scope ==Oyster2Query.Auto_Scope){
			//Query first local peer
			if (which==1) queryReply = localRegistry.returnQueryReply(mOyster2.getLocalHostOntology(),typeQuery,Resource.DataResource);
			else if (which==2) queryReply = localRegistry.returnQueryReply(mOyster2.getLocalHostOntology(),topicQuery,typeQuery,Resource.DataResource);
			else if (which==3) positiveResult = localRegistry.searchExpertiseOntology(mOyster2.getLocalHostOntology(),topicQuery,keywordSearch);
			if (which==1 || which==2) returnResult(queryReply);
		}
		if (mShutdownFlag) return;
		if (scope==Oyster2Query.Auto_Scope || scope==Oyster2Query.Selected_Scope){
			//Query known peers
			Iterator it=null;
			if (scope==Oyster2Query.Selected_Scope){
				it = peerSet.iterator();
			}
			else{//it should be expertise based-selection of peer, for now all of them
				Collection peerList=null;
				peerList = localInformer.getPeerList(localInformer.getLocalRegistry());
				it = peerList.iterator();
			} 
			while(it.hasNext()){
				if (mShutdownFlag) return;
				Individual peerIndiv = (Individual)it.next();
				if((peerIndiv!=localInformer.getLocalPeerIndiv(localOntology) || scope==Oyster2Query.Selected_Scope) && !mOyster2.isOfflinePeer(peerIndiv.getURI())){
					String IP = localInformer.getPeerIP(localOntology,peerIndiv);
					System.out.println("Attempt to connect to peer: "+peerIndiv.getURI().toString()+" from Searcher");
					Ontology Registry = localInformer.openRemoteRegistry(IP);
					if (Registry!=null){
						//System.out.println("available, now will query: "+peerIndiv.getURI().toString());
						if (which ==1) queryReply = localRegistry.returnQueryReply(Registry,typeQuery,Resource.DataResource);
						else if (which==2) queryReply = localRegistry.returnQueryReply(Registry,topicQuery,typeQuery,Resource.DataResource);
						else if (which==3) positiveResult = localRegistry.searchExpertiseOntology(Registry,topicQuery,keywordSearch);
						if (which==1 || which==2) returnResult(queryReply);
					}else{
						mOyster2.addOfflinePeer(peerIndiv.getURI());
					}
				}
			}
		}
		if (which==1 || which==2) returnResult(new QueryReply());
	}
	
	public void distributedQueryGeneral(int which, int scope){
		QueryReply queryReply =null;
		
		AdvertInformer localInformer = mOyster2.getLocalAdvertInformer();
		Ontology localOntology = localInformer.getLocalRegistry();
		
		if (mShutdownFlag) return;
		if (scope==Oyster2Query.Local_Scope || scope ==Oyster2Query.Auto_Scope){
			//Query first local peer
			if (which==1) 				
				queryReply = localRegistry.returnQueryReplyGeneral(mOyster2.getLocalHostOntology(),typeQuery,Resource.DataResource);
			else if (which==2) {}
			returnResult(queryReply);
		}
		if (mShutdownFlag) return;
		if (scope==Oyster2Query.Auto_Scope || scope==Oyster2Query.Selected_Scope){
			//Query known peers
			Iterator it=null;
			if (scope==Oyster2Query.Selected_Scope){
				it = peerSet.iterator();
			}
			else{//it should be expertise based-selection of peer, for now all of them
				Collection peerList=null;
				peerList = localInformer.getPeerList(localInformer.getLocalRegistry());
				it = peerList.iterator();
			} 
			while(it.hasNext()){
				if (mShutdownFlag) return;
				Individual peerIndiv = (Individual)it.next();
				if((peerIndiv!=localInformer.getLocalPeerIndiv(localOntology) || scope==Oyster2Query.Selected_Scope) && !mOyster2.isOfflinePeer(peerIndiv.getURI())){
					String IP = localInformer.getPeerIP(localOntology,peerIndiv);
					System.out.println("Attempt to connect to peer: "+peerIndiv.getURI().toString()+" from Searcher");
					Ontology Registry = localInformer.openRemoteRegistry(IP);
					if (Registry!=null){
						//System.out.println("available, now will query: "+peerIndiv.getURI().toString());
						if (which ==1) 
							queryReply = localRegistry.returnQueryReplyGeneral(Registry,typeQuery,Resource.DataResource);
						else if (which==2) {}
						returnResult(queryReply);
					}else{
						mOyster2.addOfflinePeer(peerIndiv.getURI());
					}
				}
			}
		}
		returnResult(new QueryReply());
	}

}

/*
 * TESTING
 * Quering all known peers.
 * 
*/
/*
AdvertInformer localInformer = mOyster2.getLocalAdvertInformer();
Collection peerList = localInformer.getPeerList(localInformer.getLocalRegistry());
Ontology localOntology = localInformer.getLocalRegistry();

//Query first local peer
queryReply = localRegistry.returnQueryReply(mOyster2.getLocalHostOntology(),typeQuery,Resource.DataResource);
returnResult(queryReply);
//Query known peers
Iterator it = peerList.iterator(); 
while(it.hasNext()){
	Individual peerIndiv = (Individual)it.next();
	if(peerIndiv!=localInformer.getLocalPeerIndiv(localOntology) && !mOyster2.isOfflinePeer(peerIndiv.getURI())){
		String IP = localInformer.getPeerIP(localOntology,peerIndiv);
		Ontology Registry = localInformer.openRemoteRegistry(IP);
		if (Registry!=null){
			System.out.println("available, now will query: "+peerIndiv.getURI().toString());
			queryReply = localRegistry.returnQueryReply(Registry,typeQuery,Resource.DataResource);
			returnResult(queryReply);
		}else{
			mOyster2.addOfflinePeer(peerIndiv.getURI());
		}
	}
}
returnResult(new QueryReply());
*/
/*
 * END TESTING
 */

/*
 * TESTING
 * Quering all known peers.
 * 
*/
/*
AdvertInformer localInformer = mOyster2.getLocalAdvertInformer();
Collection peerList = localInformer.getPeerList(localInformer.getLocalRegistry());
Ontology localOntology = localInformer.getLocalRegistry();

//Query first local peer
queryReply = localRegistry.returnQueryReply(mOyster2.getLocalHostOntology(),topicQuery,typeQuery,Resource.DataResource);
returnResult(queryReply);
//Query known peers
Iterator it = peerList.iterator(); 
while(it.hasNext()){
	Individual peerIndiv = (Individual)it.next();
	if(peerIndiv!=localInformer.getLocalPeerIndiv(localOntology) && !mOyster2.isOfflinePeer(peerIndiv.getURI())){
		String IP = localInformer.getPeerIP(localOntology,peerIndiv);
		Ontology Registry = localInformer.openRemoteRegistry(IP);
		if (Registry!=null){
			//System.out.println("available, now will query: "+peerIndiv.getURI().toString());
			queryReply = localRegistry.returnQueryReply(Registry,topicQuery,typeQuery,Resource.DataResource);
			returnResult(queryReply);
		}
		else{
			mOyster2.addOfflinePeer(peerIndiv.getURI());
		}
	}
}
returnResult(new QueryReply());
*/
/*
 * END TESTING
 */

/**
 * filter the query results within the query scope
 */
/*
private Collection filter(int scope,Collection ontologySet){
	Collection resultSet = new LinkedList();
	Iterator it = ontologySet.iterator();
	while(it.hasNext()){
	if(scope == Oyster2Query.Local_Scope){
		String hostPort = mOyster2.getLocalHost().getAddress();
		String uri = (String)it.next();
		if(uri.substring(uri.indexOf("?")+1).contains(hostPort))
			resultSet.add(uri);
	}
	else if(scope == Oyster2Query.Auto_Scope){
		resultSet = ontologySet;
		return resultSet;//TODO HOW to test peers online.
	}
	else if(scope == Oyster2Query.Selected_Scope){
		
	}
	}
	return resultSet;
}
*/