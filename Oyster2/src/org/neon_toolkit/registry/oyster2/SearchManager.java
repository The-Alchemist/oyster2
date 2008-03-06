package org.neon_toolkit.registry.oyster2;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import org.neon_toolkit.registry.core.Searcher;
import org.neon_toolkit.registry.oyster2.Oyster2Query;
import org.neon_toolkit.registry.util.GUID;



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
	/** List of listeners attached to instance of that class */
	protected List<QueryReplyListener> replyListeners = new ArrayList<QueryReplyListener>();
	protected QueryReplyListener peerDocListener;
	protected QueryReplyListener replyListener;
	protected Hashtable<GUID, Oyster2Query> mSentQueries = new Hashtable<GUID, Oyster2Query>();
	protected Thread runningSearchThread=null;
	protected Searcher runningSearchClass=null;
	//private boolean which;
	//private Thread searchThread;
	
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
	public void notifyReplyListener(QueryReply queryReply){
		if (replyListener!=null)
			replyListener.newReplyReceived(queryReply);
		/*
		if (which)
			((Result)replyListener).newReplyReceived(queryReply);
		else
			((ResultRegistry)replyListener).newReplyReceived(queryReply);			
		*/
	}
	
	/**
	 * notify the queryReplyListener when a set of ontology found for certain peers.
	 * @param ontologyDocList
	 */
	public void notifyReplyListener(List ontologyDocList){
		if (replyListener!=null)
			replyListener.entryReceived(ontologyDocList);
		/*
		if (which)
			((Result)replyListener).entryReceived(ontologyDocList);
		else
			((ResultRegistry)replyListener).entryReceived(ontologyDocList);		
		*/
	}
	
	public synchronized void invoked(){
		notify();	
	}
	
	public synchronized void addListener(QueryReplyListener listener) {
		replyListener=listener;
	}

	/**
	 * add a replyListener to the listener pool.
	 * @param listener
	 */
	public synchronized void addReplyListener(QueryReplyListener listener) {
		replyListeners.add(listener);
		try{
			wait();
		}catch(InterruptedException e){
			
		}
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
		replyListener=null;
		replyListeners.clear();
		mSentQueries.clear();
		if (runningSearchClass!=null) runningSearchClass.shutdown();
	}
	
	public void stopSearchFull(){
		replyListener=null;
		replyListeners.clear();
		mSentQueries.clear();
		if (runningSearchClass!=null) runningSearchClass.shutdown();
		try {
			if (runningSearchThread!=null && runningSearchThread.isAlive()){
				runningSearchThread.interrupt();
				runningSearchThread.join(10000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void checkIfRunning(){
		try {
			if (runningSearchThread!=null && runningSearchThread.isAlive()){
				runningSearchThread.interrupt();
				runningSearchThread.join(10000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Starts a new search.
	 *
	 * @param query the new query.
	 */
	public void startSearch(Oyster2Query topicQuery,Oyster2Query typeQuery,Set peerSet, boolean keywordSearch) {
		//mSentQueries.put(topicQuery.getGUID(), topicQuery);
		checkIfRunning(); //JUST IN CASE A PREVIOUS QUERY TIMED OUT AND DIDNT HAVE CHANCE TO SHUTDOWN
		Searcher neu=new Searcher(topicQuery,typeQuery,peerSet, keywordSearch);
		Thread t = new Thread(neu,"Searcher1");
		runningSearchClass=neu;
		runningSearchThread=t;
		t.setDaemon(true);
		t.start();
	}
	
	/**
	 * Starts a new search.
	 *
	 * @param query the new query.
	 */
	public void startSearch(Oyster2Query topicQuery,Oyster2Query typeQuery,Set peerSet, boolean keywordSearch, boolean normalFlag) {
		//mSentQueries.put(topicQuery.getGUID(), topicQuery);
		checkIfRunning(); //JUST IN CASE A PREVIOUS QUERY TIMED OUT AND DIDNT HAVE CHANCE TO SHUTDOWN
		Searcher neu=new Searcher(topicQuery,typeQuery,peerSet, keywordSearch, normalFlag);
		Thread t = new Thread(neu,"Searcher2");
		runningSearchClass=neu;
		runningSearchThread=t;
		t.setDaemon(true);
		t.start();
	}
	
	public void startSearch(Set peerSet){
		//which=false;
		checkIfRunning(); //JUST IN CASE A PREVIOUS QUERY TIMED OUT AND DIDNT HAVE CHANCE TO SHUTDOWN
		Searcher neu=new Searcher(peerSet);
		Thread t = new Thread(neu,"Searcher3");
		runningSearchClass=neu;
		runningSearchThread=t;
		t.setDaemon(true);
		t.start();	
	}
}


/*
if (!manualSelected){
	if (topicQuery!=null) which = false;
	else which=true;
}else
	which=true;
*/

/*public void notifyReplyListener(QueryReply queryReply){
invoked();
entriesCounter++;

for (int i = 0; i < replyListeners.size(); i++) {
	((Result) replyListeners.get(i)).newReplyReceived(queryReply);
}

}*/

/*public void notifyReplyListener(List ontologyDocList){
System.out.println("replyListener size: "+replyListeners.size());
invoked();
for (int i = 0; i < replyListeners.size(); i++) {
	((Result) replyListeners.get(i)).entryReceived(ontologyDocList);
}
}*/