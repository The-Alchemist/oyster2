package org.neontoolkit.oyster.plugin.iwizard;

import org.osgi.framework.BundleContext;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;


/**
 * The activator class controls the plug-in life cycle
 */
public class RegistryActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.neontoolkit.oyster.plugin.iwizard";

	// The shared instance
	private static RegistryActivator plugin;
	
	private static boolean distributed=false;
	
	/**
	 * The constructor
	 */
	public RegistryActivator() {
		plugin = this;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {	
		if (!StartRegistry.success || !StartRegistry.getState()) {
			plugin = null;
			super.stop(context);
		}
		super.start(context);
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
	public static RegistryActivator getDefault() {
		return plugin;
	}

	public  boolean getDistributed() {
		return distributed;
	}
	public  void setDistributed(boolean value) {
		distributed=value;
	}
	
}