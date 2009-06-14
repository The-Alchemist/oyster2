package org.neontoolkit.registry.api.change;


/**
 * The class RunnableSynchronization starts the syncrhonization of changes
 * in its own thread.
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class RunnableSynchronization implements Runnable {
	

	/**
	 * Creates a new RunnableSynchronization 
	 */
	public RunnableSynchronization() {
		
	}
	
	/**
	 * Processes the synchronization
	 */
	@SuppressWarnings("unchecked")
	public void run() {
		ChangeSynchronization.SynchronizeChanges();
	}
	
}