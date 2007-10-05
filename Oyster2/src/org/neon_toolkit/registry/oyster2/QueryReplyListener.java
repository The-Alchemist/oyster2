package org.neon_toolkit.registry.oyster2;

import java.util.List;

import org.semanticweb.kaon2.api.Ontology;


/**
 * The listener interface for receiving notification about new Query Replys.
 * Everytime a new message is received the <code>newQueryReply()</code> method would be used
 * to inform all listeners.
 */
public interface QueryReplyListener {

	/**
	 * Invoked when a new Query Reply was received.
	 *
	 * @param queryReply the Query Reply.
	 */
	public void newReplyReceived(QueryReply queryReply);
	
	public void entryReceived(final List entryList);
	
	public void entryReceived(final Ontology virtualOntology);

}