package oyster2;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;


import oyster2.Oyster2Query;
import core.*;

import ui.Result;
import ui.ResultRegistry;
import util.GUID;

public class SearchManager {
	/**
	 * The reference to the only instance of this class (Singleton
	 * pattern). This differs from the C++ standard implementation by Gamma
	 * et.al. since Java ensures the order of static initialization at runtime.
	 *
	 * @see <a href="http://www.javaworld.com/javaworld/javatips/jw-javatip67.html">
	 *      Lazy instantiation - Balancing performance and resource usage</a>
	 */
	private static final SearchManager SHARED_INSTANCE = new SearchManager();
	protected int entriesCounter = 0;
	//private Thread searchThread;
	
	/** List of listeners attached to instance of that class */
	protected List replyListeners = new ArrayList();
	protected QueryReplyListener peerDocListener;
	protected QueryReplyListener replyListener;
	
	protected Hashtable<GUID, Oyster2Query> mSentQueries = new Hashtable<GUID, Oyster2Query>();
	private boolean which;
	
	/**
	 * Query reply listeners that listen to replies to queries with
	 * a specific GUID.
	 */
	private Hashtable mListeners = new Hashtable();
	
	public static SearchManager sharedInstance() {
		return SHARED_INSTANCE;
	}
	
	/**
	 * Notify when a new Query Reply was received and invokes the methode in Result->update UI.
 	* @return void
 	*/
	/*public void notifyReplyListener(QueryReply queryReply){
		invoked();
		entriesCounter++;
		
		for (int i = 0; i < replyListeners.size(); i++) {
			((Result) replyListeners.get(i)).newReplyReceived(queryReply);
		}
		
	}*/
	public void notifyReplyListener(QueryReply queryReply){
		if (which)
			((Result)replyListener).newReplyReceived(queryReply);
		else
			((ResultRegistry)replyListener).newReplyReceived(queryReply);
	}
	
	/**
	 * notify the queryReplyListener when a set of ontology found for certain peers.
	 * @param ontologyDocList
	 */
	/*public void notifyReplyListener(List ontologyDocList){
		System.out.println("replyListener size: "+replyListeners.size());
		invoked();
		for (int i = 0; i < replyListeners.size(); i++) {
			((Result) replyListeners.get(i)).entryReceived(ontologyDocList);
		}
	}*/
	public void notifyReplyListener(List ontologyDocList){
		if (which)
			((Result)replyListener).entryReceived(ontologyDocList);
		else
			((ResultRegistry)replyListener).entryReceived(ontologyDocList);
	}
	
	public synchronized void invoked(){
		notify();	
	}
	
	/**
	 * add a replyListener to the listener pool.
	 * @param listener
	 */
	/*public synchronized void addReplyListener(QueryReplyListener listener) {
		replyListeners.add(listener);
		try{
		wait();
		}catch(InterruptedException e){
			
		}
	}*/
	public synchronized void addListener(QueryReplyListener listener) {
		replyListener=listener;
	}

	/**
	 * remove the reply Listener.
	 * @param listener
	 */
	public void removeReplyListener(QueryReplyListener listener,GUID guid) {
		if(mListeners.containsKey(guid)){
			mListeners.remove(guid);
		}	
	}
	
	/**
	 * Clears all results for the provided query.
	 *
	 * @param query the query for which to clear the results.
	 */
	public void clearSearch(Oyster2Query query) {
		mSentQueries.remove(query.getGUID());
	}
	
	/**
	 * stop the searching process,clears the listeners pool and queries pool.
	 * 
	 */
	public void stopSearch(){
		replyListeners.clear();
		mSentQueries.clear();
		//mKaonP2P.getMainWindow().operationFinished();
	}
	
	/**
	 * Starts a new search.
	 *
	 * @param query the new query.
	 */
	public void startSearch(Oyster2Query topicQuery,Oyster2Query typeQuery,boolean manualSelected) {
		//mSentQueries.put(topicQuery.getGUID(), topicQuery);
		if (topicQuery!=null) which = false;
		else which=true;
		Thread t = new Thread(new Searcher(topicQuery,typeQuery,manualSelected));
		t.setDaemon(true);
		t.start();
	}
	
	public void startSearch(Set peerSet){
		Thread t = new Thread(new Searcher(peerSet));
		t.setDaemon(true);
		t.start();	
	}
}
