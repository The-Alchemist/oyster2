package org.neontoolkit.registry.api;

import java.util.*;

import org.neontoolkit.registry.oyster2.Oyster2Factory;
import org.neontoolkit.registry.oyster2.QueryReply;
import org.neontoolkit.registry.oyster2.QueryReplyListener;
import org.neontoolkit.registry.util.Resource;
import org.semanticweb.kaon2.api.*;


/**
 * The class ResultAPI is the listener
 * for distributed querys sent by the API   
 * @author Raul Palma
 * @version 1.0, March 2008
 */
public class ResultAPI implements QueryReplyListener{

	static Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	Resource todelete=null;
	String  columnContent[];
	Thread apiThread=null;

	public ResultAPI(Thread apiThreadCall, int resourceType){
		apiThread=apiThreadCall;
	}
	
	/**
	 * invoked when a new query reply received.
	 */
	public synchronized void newReplyReceived(QueryReply reply) {
		if (reply==null) return;
		
		int type = reply.getType();
		if (type==-1){ //Finished
			Oyster2Connection.noWaitMore();
			mOyster2.getLogger().info("waking up API no more replies (finished)...");
			apiThread.interrupt();
		}else if((type==QueryReply.TYPE_OK)&&(reply.getResourceSet().size()>0)){
			Oyster2Connection.addQueryReply(reply);
		}else if((type==QueryReply.TYPE_BAD_REQUEST)||(type==QueryReply.TYPE_INIT)){//||(reply.getResourceSet().size()<=0)){
			
		}
	}
	
	public synchronized void entryReceived(final List entryList){
		
	}
	
	public synchronized void entryReceived(final Ontology virtualOntology){
			
	}
	
	
	
}
