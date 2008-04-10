package org.neon_toolkit.oyster.plugin.iwizard;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.neon_toolkit.registry.api.Oyster2Connection;
import org.neon_toolkit.registry.api.Oyster2Manager;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class RegistryActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.neon_toolkit.oyster.plugin.iwizard";

	// The shared instance
	private static RegistryActivator plugin;
	
	private static boolean distributed=false;
	
	private static boolean startOyster;
	
	private static Oyster2Connection connection=null;
	
	private static BundleContext BContext;
	
	private boolean success=false;
	
	
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
		BContext=context;
		startOyster();
		if (!success) {
			setStartOyster(false);
			plugin = null;
			super.stop(context);
		}else
			setStartOyster(true);
		super.start(context);
	}

	public void stopOyster(){
		connection=null;
		Oyster2Manager.closeConnection();
		System.out.println("connection closed...");
	}
	
	public boolean startOyster(){
		success=false;
		IRunnableWithProgress exportJob = new IRunnableWithProgress(){
			public void run(IProgressMonitor monitor){
				try{
					monitor.beginTask("Initializing import wizard (only first time it is loaded)...", 100);
					Oyster2Manager.setSimplePeer(true);
					Oyster2Manager.setLogEnabled(false);
					monitor.worked(30);
					connection = Oyster2Manager.newConnection(false);
					if (connection!=null){
						monitor.worked(60);
						System.out.println("new connection...");
						success=true;
					}else{
						//ignore here
					}
				}catch (Exception e){
					e.printStackTrace();  //LOTS OF ERRORS HERE
				}
			}   
		};
		ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(Display.getDefault().getActiveShell());
		progressDialog.setBlockOnOpen(false);
		progressDialog.setCancelable(false);
		progressDialog.open();
		try{
			exportJob.run(progressDialog.getProgressMonitor());
		}catch (InvocationTargetException e){
			e.printStackTrace();
		}catch (InterruptedException e){
			e.printStackTrace();
		}
		progressDialog.close();
		return success;
	}
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		
		Job job = new Job("Closing import wizard session...") {
			protected IStatus run(IProgressMonitor monitor) {
				// Do some work
				connection=null;
				Oyster2Manager.closeConnection();
				System.out.println("connection closed at the end..."); 
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();

		plugin = null;
		super.stop(context);
	}

	
	public void stopError() throws Exception {
		plugin = null;
		super.stop(getBContext());
	}

	
	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static RegistryActivator getDefault() {
		return plugin;
	}

	public  BundleContext getBContext() {
		return BContext;
	}
	
	public  boolean getDistributed() {
		return distributed;
	}
	public  void setDistributed(boolean value) {
		distributed=value;
	}
	
	public  boolean getStartOyster() {
		return startOyster;
	}
	public  void setStartOyster(boolean value) {
		startOyster=value;
	}
	public  Oyster2Connection getOyster2Connection() {
		return connection;
	}
}


