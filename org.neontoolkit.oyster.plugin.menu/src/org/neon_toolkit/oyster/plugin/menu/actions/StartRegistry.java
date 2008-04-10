package org.neon_toolkit.oyster.plugin.menu.actions;


import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
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
	private static boolean restart=true;
	
	private static boolean run=false;
	private static int first=0;
	public static Process serverProcess = null;
	/**
	 * The constructor.
	 */
	public StartRegistry() {
		
	}

	public static boolean getHasStarted(){
		return run;
	}
	
	public static boolean getRestart(){
		if (first<=1) return false;
		if (!start & !restart){ //RUNNNING (FIRST CALL) 
			restart=true;
			return true; 	
		}
		if (!start & restart){ //RUNNING
			return false;
		}
		if (start & restart){  //NOT RUNNING WILL FAIL UNLESS STARTED OUTSIDE
			restart=false;
			return true;
		}
		if (start & !restart){  //NOT RUNNING WILL FAIL UNLESS STARTED OUTSIDE
			return false;
		}
		
		return false;
	}
	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		run=true;
		if (start){
			IRunnableWithProgress exportJob = new IRunnableWithProgress(){
				public void run(IProgressMonitor monitor){					
					monitor.beginTask("starting registry... (If first time running registry please wait few minutes)", 100);
					try {
						String o2File=System.getProperty("user.dir")+System.getProperty("file.separator")+"plugins"+System.getProperty("file.separator")+"OysterUtil.jar";
						
						//System.out.println("java -cp "+ EntryDetailSerializer.QUOTE + o2File + EntryDetailSerializer.QUOTE + " org.neon_toolkit.registry.oyster2.server.StartServer -workflow -startKAON2");
						monitor.worked(20);
						serverProcess = Runtime.getRuntime().exec("java -cp "+ EntryDetailSerializer.QUOTE + o2File + EntryDetailSerializer.QUOTE + " org.neon_toolkit.registry.oyster2.server.StartServer -workflowSupport -startKAON2");
						if(serverProcess==null){
							MessageDialog.openInformation(
									window.getShell(),
									"Menu Plug-in",
									"Registry couldn't start");
						}
						else{	
							start = !start;
							restart=false;
							monitor.worked(30);
							Thread.sleep(30000);
							monitor.worked(40);
						}
					}catch (Exception e) {
						// TODO Auto-generated catch block
						MessageDialog.openInformation(
								window.getShell(),
								"Menu Plug-in",
								"Registry couldn't start");
						e.printStackTrace();
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
			
		}
		else {
			serverProcess.destroy();
			MessageDialog.openInformation(
				window.getShell(),
				"Menu Plug-in",
				"Registry stopping...");
			start = !start;
			restart=true;
		}
		
		if (start) {
			action.setText(startRegistryText);
			
		}
		else {
			action.setText(stopRegistryText);
			
		}
		first++;
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
}

/*
String o2File="Oyster2.jar";
try{
	System.out.println("java -cp "+ EntryDetailSerializer.QUOTE + o2File + EntryDetailSerializer.QUOTE + " org.neon_toolkit.registry.oyster2.server.StartServer");
	serverProcess = Runtime.getRuntime().exec("java -cp "+ EntryDetailSerializer.QUOTE + o2File + EntryDetailSerializer.QUOTE + " org.neon_toolkit.registry.oyster2.StartServer");
	if(serverProcess!=null) MessageDialog.openInformation(
			window.getShell(),
			"Menu Plug-in",
			"Registry started");
	else MessageDialog.openInformation(
			window.getShell(),
			"Menu Plug-in",
			"Registry couldn't start");
}catch(Exception e){
	MessageDialog.openInformation(
			window.getShell(),
			"Menu Plug-in",
			"Registry couldn't start");
}
*/

