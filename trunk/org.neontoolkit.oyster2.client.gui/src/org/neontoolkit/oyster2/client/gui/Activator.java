package org.neontoolkit.oyster2.client.gui;


import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {
	
	private static WebServersLocator webServersLocator = null;
	// The plug-in ID
	public static final String PLUGIN_ID = "org.neontoolkit.oyster2.client.gui";

	private static String resourcesPath = null;
	
	
	
	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
		plugin = this;
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		Path path = new Path("resources" + File.separator);
		URL resourcesFileURL;
		try {
			resourcesFileURL = FileLocator.toFileURL(FileLocator.find(Activator.getDefault().getBundle(),
			path, null));
			resourcesPath = resourcesFileURL.getFile();
			webServersLocator = new WebServersLocator();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}


	public String getResourcesDir() {
		return resourcesPath;
	}

	/**
	 * @return the webServersLocator
	 */
	public static final WebServersLocator getWebServersLocator() {
		return webServersLocator;
	}
	
	
	
}

