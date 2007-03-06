package core;

import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.Vector;
import java.util.logging.Logger;

import oyster2.*;
import util.Resource;
import org.semanticweb.kaon2.api.*;
import org.semanticweb.kaon2.api.owl.elements.*;

public class Searcher implements Runnable {

	/**
	 * The time to wait for Query Hit messages.
	 */
	private static final int REPLY_TIMEOUT = 60000; // 1 min.

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
	private Vector mAlreadyAsked = new Vector();

	/**
	 * The list of all already seen Querys.
	 */
	private static Hashtable mAlreadySeen = new Hashtable();

	/**
	 * The requesting host.
	 */
	private Oyster2Host mHost = null;

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
	private Set peerSet;
	private boolean normalSearchFlag = true;
	private boolean positiveResult = false;
	private boolean manualSelected= false;

	/**
	 * The amount of started remote searches.
	 */
	private int mRemoteSearches = 0;

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
	public Searcher(Oyster2Query topicQuery, Oyster2Query typeQuery,boolean manualSelected) {
		this.topicQuery = topicQuery;
		this.typeQuery = typeQuery;
		this.normalSearchFlag = true;
		this.manualSelected = manualSelected;
	}
	/**
	 * Creates a new Searcher for a locally started search.
	 *
	 * @param peerSet the set of peers for which we search the ontologies provided by. 
	 */
	public Searcher(Set peerSet){
		this.peerSet = peerSet;
		this.normalSearchFlag = false;	
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
	/**
	 * filter the query results within the query scope
	 */
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

	/**
	 * Processes a search request.
	 */
	public void run(){
		if(normalSearchFlag){
			QueryReply queryReply =null;
			if ((topicQuery!=null) && (topicQuery.getQueryString().length()>0)){
				//queryReply = localRegistry.returnQueryReply(mKaonP2P.getLocalHostOntology(),topicQuery,Resource.OntologyResource);  
				queryReply = new QueryReply(topicQuery.getGUID(),QueryReply.TYPE_INIT);
				topicQuery.setStatus(Oyster2Query.STATUS_RUNNING);	
				positiveResult = localRegistry.searchExpertiseOntology(topicQuery,manualSelected);
				if (!positiveResult ) {
					System.out.println("Bad request for '" + topicQuery.getQueryString() + "'.");
					queryReply = new QueryReply(topicQuery.getGUID(), QueryReply.TYPE_BAD_REQUEST);
					mOyster2.getSearchManager().notifyReplyListener(queryReply);
				}			
			}
			else if((typeQuery != null) && (typeQuery.getQueryString().length()>0))
				queryReply = localRegistry.returnQueryReply(mOyster2.getLocalHostOntology(),typeQuery,Resource.DataResource); //mKaonP2P.getVirtualOntology()
			//else 
			//	queryReply = localRegistry.returnQueryReply(mKaonP2P.getLocalHostOntology(),topicQuery,Resource.OntologyResource);
			// ** THIS WAS COMMENTED BEFORE typeQuery.setStatus(KaonP2PQuery.STATUS_FINISHED);
			returnResult(queryReply);
		}
		else{
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
