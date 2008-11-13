/**
 * 
 */
package org.neontoolkit.oyster2.client.gui;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.eclipse.jface.dialogs.IDialogSettings;
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
	
	private IDialogSettings dialogSettings = null;
	
	private static final String SERVER_SETTINGS_SECTION = "servers";
	
	private static final String SELECTION_KEY = "currentSelection";
	
	public WebServersLocator() {
		servers = new HashSet<String>();
		selectionListeners = new HashSet<Listener>();
		contentListeners = new HashSet<Listener>();
		try {
			

			IDialogSettings generalSection = Activator.getDefault().getDialogSettings();
			
			dialogSettings = generalSection.getSection(SERVER_SETTINGS_SECTION);
			
			if (dialogSettings == null) {
				dialogSettings = generalSection.addNewSection(SERVER_SETTINGS_SECTION);
				PropertiesConfiguration configuration = null;
				
				configuration =
					new PropertiesConfiguration(Activator.getDefault().getResourcesDir() +
							filename);
				configuration.setAutoSave(false);
				List<String> serversList = configuration.getList(SERVER_LIST_KEY);
				servers.addAll(serversList);
				dialogSettings.put(SERVER_LIST_KEY, 
						serversList.toArray(new String[serversList.size()]));
			}
			else {
				String [] serverArray = dialogSettings.getArray(SERVER_LIST_KEY);
				for (String server : serverArray) {
					servers.add(server);
				}
			}
			
			
			
			/*
			String selected = configuration.getString(CURRENT_SELECTION_KEY);
			
			if (selected == null)
				currentSelection = serversList.get(0);
			else 
				currentSelection = selected;
			*/
			String selected = dialogSettings.get(CURRENT_SELECTION_KEY);
			if (selected == null) {
				
				String firstServer = servers.iterator().next();
				currentSelection = firstServer;
			}
			else {
				currentSelection = selected;
			}
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
			String [] serverArray = new String[servers.size()];
			//configuration.addProperty(SERVER_LIST_KEY,server);
			
			dialogSettings.put(SERVER_LIST_KEY, servers.toArray(serverArray));
			
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
			
			String [] serverArray = new String[servers.size()];
			
			dialogSettings.put(SERVER_LIST_KEY, servers.toArray(serverArray));
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
		dialogSettings.put(CURRENT_SELECTION_KEY,server);
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
	
	
}
