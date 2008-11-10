package org.neontoolkit.oyster2.client.gui;


import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
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
			
			addWorkbenchListener();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		
	}

	private void addWorkbenchListener() {
		IWorkbenchListener listener = new IWorkbenchListener() {

			@Override
			public void postShutdown(IWorkbench workbench) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean preShutdown(IWorkbench workbench, boolean forced) {
				
				String firstView = PerspectiveFactory.OYSTER2_RESULTS_VIEW_SECONDARY_ID+String.valueOf(1);
				IWorkbenchPage page =
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				
				IViewReference[] views = page.getViewReferences();
				String id = null;
				for (IViewReference viewReference : views ) {
					id = viewReference.getId();
					if (id.equals(PerspectiveFactory.OYSTER2_RESULTS_VIEW_ID)) {
						String secId = viewReference.getSecondaryId();
						System.out.println("Id " + id + secId);
						if (! secId.equals(firstView))
							page.hideView(viewReference.getView(false));
					}
				}
				return true;
			}
			
		};
		PlatformUI.getWorkbench().addWorkbenchListener(listener);
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

