/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.neontoolkit.oyster2.client.gui.dialogs.SearchDialog;


/**
 * @author David Muñoz
 *
 */
public class SearchAction implements IWorkbenchWindowActionDelegate {

	
	//TODO learn why is this necessary
	//private IWorkbenchWindow window = null;
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
		//this.window = window;
	
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {

		Shell shell = org.neontoolkit.oyster2.client.gui.Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
		SearchDialog dialog = new SearchDialog(SearchDialog.SETTINGS_SECTION, shell);
		dialog.open();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
