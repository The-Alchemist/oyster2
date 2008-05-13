package org.neon_toolkit.oyster.plugin.menu.actions;

import java.io.File;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.MessageDialog;
import org.neon_toolkit.registry.api.Oyster2Connection;
import org.neon_toolkit.registry.api.Oyster2Manager;
import org.neon_toolkit.registry.util.EntryDetailSerializer;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class StartRegistry implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	private static String startRegistryText="Start Registry";
	private static String stopRegistryText="Stop Registry";
	private static boolean start=true;
	public static boolean success=false;
	public static Oyster2Connection connection=null;
	public static Process serverProcess = null;
	/**
	 * The constructor.
	 */
	public StartRegistry() {
		
	}

	public static boolean getState(){
		return !start;
	}
	
	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		if (start){			
			Job exportJob = new Job("Starting registry...(The first time please wait few minutes)") {
				protected IStatus run(IProgressMonitor monitor) {
					monitor.beginTask("Starting registry...(The first time please wait few minutes)", 100);
					try {
						String o2File=System.getProperty("user.dir")+System.getProperty("file.separator")+"plugins"+System.getProperty("file.separator")+"OysterUtil.jar";
						monitor.worked(10);
						if(serverProcess==null) {
							System.out.println("executing the server...");
							serverProcess = Runtime.getRuntime().exec("java -cp "+ EntryDetailSerializer.QUOTE + o2File + EntryDetailSerializer.QUOTE + " org.neon_toolkit.registry.oyster2.server.StartServer -workflowSupport -startKAON2");
						}
						if(serverProcess==null){
							MessageDialog.openInformation(
									window.getShell(),
									"Menu Plug-in",
									"Registry couldn't start");
						}
						else{	
							monitor.worked(20);
							File tFile = new File("O2serverfiles/localRegistry.owl"); //FIXED FOR NOW...
							if ((!tFile.exists())
									|| (tFile.length() <= 0)) {
								System.out.println("waiting 30S...");
								Thread.sleep(30000);
							}
							else Thread.sleep(7000);
							monitor.worked(30);
							startOysterSilent();
							if (!success) {
								MessageDialog.openInformation(
										window.getShell(),
										"Menu Plug-in",
										"Registry couldn't start");
								
							}else{
								monitor.worked(40);
								start = !start;
							}
						}
					}catch (Exception e) {
						// TODO Auto-generated catch block
						MessageDialog.openInformation(
								window.getShell(),
								"Menu Plug-in",
								"Registry couldn't start");
						
						e.printStackTrace();
					}	
					finally{
						monitor.done();
					}
					return Status.OK_STATUS;
				}   
			};
			exportJob.setUser(true);
			exportJob.schedule();
		}
		else {
			stopOyster();
			serverProcess.destroy();
			serverProcess = null;
			MessageDialog.openInformation(
				window.getShell(),
				"Menu Plug-in",
				"Registry stopping...");
			start = !start;
		}
		
		if (start) {
			action.setText(startRegistryText);
			
		}
		else {
			action.setText(stopRegistryText);
			
		}
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		action.setEnabled(true);
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
		if (serverProcess!=null)
			serverProcess.destroy();
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
	
	public boolean startOysterSilent(){
		success=false;
		Oyster2Manager.setSimplePeer(true);
		Oyster2Manager.setWorkflowSupport(true);
		Oyster2Manager.setLogEnabled(false);
		connection = Oyster2Manager.newConnection(false);
		if (connection!=null){
			System.out.println("new connection...");
			success=true;
		}else{
			//ignore here
		}
		return success;
	}
	
	public void stopOyster(){
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
	}
}
