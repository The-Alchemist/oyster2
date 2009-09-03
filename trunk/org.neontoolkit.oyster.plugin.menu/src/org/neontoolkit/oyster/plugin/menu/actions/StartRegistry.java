package org.neontoolkit.oyster.plugin.menu.actions;

import java.io.File;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.MessageDialog;
import org.neontoolkit.registry.api.Oyster2Connection;
import org.neontoolkit.registry.api.Oyster2Manager;
import org.neontoolkit.registry.oyster2.Constants;
import org.neontoolkit.registry.oyster2.Properties;
import org.neontoolkit.registry.util.EntryDetailSerializer;
import com.ontoprise.ontostudio.gui.GuiPlugin;

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
	private IPreferenceStore _store = GuiPlugin.getDefault().getPreferenceStore();
	private String sOyster = "";
	private String pOyster = "";
	private Boolean readLocally=false;
	private IAction actionNew;
	
	
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
		actionNew=action;
		if (start){			
			Job exportJob = new Job("Starting registry...(The first time please wait few minutes)") {
				protected IStatus run(IProgressMonitor monitor) {
					monitor.beginTask("Starting registry...(The first time please wait few minutes)", 100);
					try {
						String o2File=System.getProperty("user.dir")+System.getProperty("file.separator")+"plugins"+System.getProperty("file.separator")+"org.neontoolkit.registry.api_2.3.3.jar";
						monitor.worked(10);
						sOyster = _store.getString("SUPEROYSTER");
						pOyster = _store.getString("PUSHOYSTER");
						readLocally=_store.getBoolean("READLOCALLY");
						if (readLocally) setOntologies();
							
						if(serverProcess==null && !superOysterStorage()) {
							System.out.println("executing the server...");
							serverProcess = Runtime.getRuntime().exec("java -cp "+ EntryDetailSerializer.QUOTE + o2File + EntryDetailSerializer.QUOTE + " org.neontoolkit.registry.oyster2.server.StartServer -workflowSupport -startKAON2 -noAutomaticSync");
						}
						if(serverProcess==null && !superOysterStorage()){
							openMessage("Registry couldn't start");
							changeAction();
						}
						else{	
							monitor.worked(20);
							if (!superOysterStorage()){
								File tFile = new File("server/localRegistry.owl"); //FIXED FOR NOW...
								if ((!tFile.exists())
										|| (tFile.length() <= 0)) {
									if (!readLocally){
										System.out.println("waiting 10S..."); //30s
										Thread.sleep(10000);
									}else Thread.sleep(7000);
								}
								else {
									if (!readLocally)
										Thread.sleep(7000);
									else
										Thread.sleep(5000);
								}
							}
							monitor.worked(30);
							startOysterSilent();
							if (!success) {
								openMessage("Registry couldn't start");
								changeAction();
							}else{
								monitor.worked(40);
								start = !start;
								changeAction();
							}
						}
					}catch (Exception e) {
						openMessage("Registry couldn't start");
						changeAction();
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
			boolean ans;
			if (window!=null && window.getShell()!=null){
				ans=MessageDialog.openQuestion(
						window.getShell(),
						"Menu Plug-in",
						"Are you sure you want to stop Oyster?");
			}else ans=true; //FOR THE JUNIT
			if (ans){
				stopOyster();
				if(serverProcess!=null && !superOysterStorage()) {
					try {
						serverProcess.destroy();
						serverProcess.waitFor();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				serverProcess = null;
				start = !start;
				changeAction();
			}
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

	public void changeAction(){
		if (actionNew==null)	return; //FOR JUNIT
		if (start) {
			actionNew.setChecked(false);
			actionNew.setText(startRegistryText);	
		}
		else {
			actionNew.setChecked(true);
			actionNew.setText(stopRegistryText);
		}
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
		if (superOysterStorage())
			Oyster2Manager.setSuperOyster(sOyster);
		if (pushOysterStorage())
			Oyster2Manager.setPushChangesToOysterIP(pOyster);
		connection = Oyster2Manager.newConnection(false);
		if (connection!=null){
			System.out.println("new connection...");
			success=true;
		}else{
			//ignore here
		}
		return success;
	}
	
	private boolean superOysterStorage(){
		if (sOyster!=null && !sOyster.equalsIgnoreCase("") && sOyster.length()>1)
			return true;
		else return false;
	}
	
	private boolean pushOysterStorage(){
		if (pOyster!=null && !pOyster.equalsIgnoreCase("") && pOyster.length()>1)
			return true;
		else return false;
	}
	
	public void stopOyster(){
		Job job = new Job("Stopping the registry...") {
			protected IStatus run(IProgressMonitor monitor) {
					connection=null;
					Oyster2Manager.closeConnection();
					System.out.println("connection closed at the end..."); 
					return Status.OK_STATUS;
				}
			};
		job.setUser(true);
		job.schedule();
	}
	
	public void openMessage(final String mess){
		window.getShell().getDisplay().asyncExec(new Runnable() {
	           public void run() {
	        	   MessageDialog.openInformation(
           				window.getShell(),
           				"Menu Plug-in",
           				mess);
	            }
		});
	}
	
	public void setOntologies(){
		Properties mprop = Oyster2Manager.getOysterProperties();
		mprop.init();
		mprop.setString(Constants.TypeOntology, "O2serverfiles/OMV.owl");
		mprop.setString(Constants.PDOntology, "O2serverfiles/pOMV.owl");
		mprop.setString(Constants.MDOntology, "O2serverfiles/mappingOMV.owl");
		mprop.setString(Constants.owlChangeOntology, "O2serverfiles/OWLChanges.owl");
		mprop.setString(Constants.TopicOntology, "O2serverfiles/dmozT.rdf");
		mprop.setString(Constants.changeOntology, "O2serverfiles/changes.owl");
		mprop.setString(Constants.owlodmOntology, "O2serverfiles/owl11v1.5.owl");
		mprop.setString(Constants.workflowOntology, "O2serverfiles/workflow.owl");
		mprop.setString(Constants.Image, "O2serverfiles/o1.gif");
		mprop.storeOn();
	}
}
