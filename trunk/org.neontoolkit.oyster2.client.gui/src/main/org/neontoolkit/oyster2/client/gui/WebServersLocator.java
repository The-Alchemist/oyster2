/**
 * 
 */
package org.neontoolkit.oyster2.client.gui;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * @author David Muñoz
 *
 */
public class WebServersLocator {
	
	private static final String CURRENT_SELECTION_KEY ="selection";
	
	private static final String SERVER_LIST_KEY ="servers";
	
	private Set<Listener> selectionListeners = null;
	
	private Set<Listener> contentListeners = null;
	
	private static final String filename = "servers.properties";
	
	private Set<String> servers = null;
	
	private String currentSelection = null;
	
	public static final String ADDED = "ADDED";
	
	public static final String DELETED = "DELETED";
	
	private static PropertiesConfiguration configuration = null;
	
	public WebServersLocator() {
		servers = new HashSet<String>();
		selectionListeners = new HashSet<Listener>();
		contentListeners = new HashSet<Listener>();
		try {
			configuration =
				new PropertiesConfiguration(Activator.getDefault().getResourcesDir() +
						filename);
			configuration.setAutoSave(true);
			List<String> serversList = configuration.getList(SERVER_LIST_KEY);
			servers.addAll(serversList);
			String selected = configuration.getString(CURRENT_SELECTION_KEY);
			
			if (selected == null)
				currentSelection = serversList.get(0);
			else 
				currentSelection = selected;
			
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	
	public String[]getServers() {
		String [] serversArray = new String[servers.size()];
		return servers.toArray(serversArray);
	}
	
	
	public void addServer(String server) {
		if (servers.add(server)) {
			fireContentChanged(server,WebServersLocator.ADDED);
		
			configuration.addProperty(SERVER_LIST_KEY,server);
			try {
				configuration.save();
			} catch (ConfigurationException e) {
			
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	
	public boolean removeServer(String server) {
		
		if (servers.contains(server)) {
			servers.remove(server);
			if (server.equals(getCurrentSelection())) {

				
				if (!servers.isEmpty()) {
					setCurrentSelection(servers.iterator().next());
				}
			}
			
			fireContentChanged(server,WebServersLocator.DELETED);
			//List<String> servers = configuration.getList(SERVER_LIST_KEY);
			
			configuration.setProperty(SERVER_LIST_KEY, servers);
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * @return the currentSelection
	 */
	public final String getCurrentSelection() {
		return currentSelection;
	}


	/**
	 * @param currentSelection the currentSelection to set
	 */
	public final void setCurrentSelection(String server) {
		currentSelection = server;
		configuration.setProperty(CURRENT_SELECTION_KEY, server);
		fireSelectionChanged(server);
	}

	
	public void addSelectionListener(Listener listener) {
		this.selectionListeners.add(listener);
	}
	public void removeSelectionListener(Listener listener) {
		this.selectionListeners.remove(listener);
	}
	
	
	public void addContentListener(Listener listener) {
		this.contentListeners.add(listener);
	}
	public void removeContentListener(Listener listener) {
		this.contentListeners.remove(listener);
	}
	
	
	public void fireContentChanged(String item, String action) {
		Event event = new Event();
		
		event.text = item;
		event.data = action;
		for (Listener listener : contentListeners) {
			listener.handleEvent(event);
		}
	}
	
	public void fireSelectionChanged(String item) {
		
		for (Listener listener : selectionListeners) {
			listener.handleEvent(null);
		}
	}
	
	public void dispose() {
		try {
			PropertiesConfiguration configuration =
				new PropertiesConfiguration(Activator.getDefault().getResourcesDir() +
						filename);
			configuration.clear();
			configuration.setProperty(CURRENT_SELECTION_KEY,currentSelection);
			configuration.setProperty(SERVER_LIST_KEY, 
					getServers());
			
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	
}
