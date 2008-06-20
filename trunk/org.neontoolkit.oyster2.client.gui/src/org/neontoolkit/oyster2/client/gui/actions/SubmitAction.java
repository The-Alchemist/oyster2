/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.neon_toolkit.omv.api.core.OMVOntology;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.adapter.submit.OntologyAdapter;
import org.neontoolkit.oyster2.client.gui.dialogs.PropertiesConfiguredSubmitDialog;
import org.neontoolkit.oyster2.client.gui.jobs.SubmitObjectsJob;



/**
 * @author David Muñoz
 *
 */
public class SubmitAction implements IWorkbenchWindowActionDelegate {

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		
		Shell shell = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
		PropertiesConfiguredSubmitDialog dialog =
			new PropertiesConfiguredSubmitDialog("SUBMIT_SECTION",shell);
		
		int result = dialog.open();
		if (result == Dialog.OK) {
			OMVOntology ontology = OntologyAdapter.makeOntology(dialog.getComposites());
			if (ontology == null) {
				MessageDialog.openError(shell, 
                  "Error", 
                  "The ontology attribute values were not valid");
			}
			else {
			SubmitObjectsJob job = new SubmitObjectsJob("submit  "+ontology.getURI());
			
			String targetService = Activator.getWebServersLocator().getCurrentSelection();
			job.setTargetService(targetService);
			job.setOntology(ontology);
			
			job.setUser(true);
			job.schedule();
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
