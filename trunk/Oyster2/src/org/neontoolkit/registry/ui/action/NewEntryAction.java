package org.neontoolkit.registry.ui.action;


import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.widgets.Display;

import org.neontoolkit.registry.ui.Result;
import org.neontoolkit.registry.ui.ResultRegistry;
import org.neontoolkit.registry.ui.editor.CreateEntryDialog;
import org.semanticweb.kaon2.api.*;



public class NewEntryAction extends SaveEntryAction{
	private ApplicationWindow window;
	private Result result;
	private ResultRegistry resultRegistry;

	public NewEntryAction(ApplicationWindow w) {
		super(w);
		window = w;
		setToolTipText("Create new ontology metadata entry");
		setText("Create@Ctrl+N");
	}

	public void setResult(Result result){
		this.result = result;
	}
	
	public void setResult(ResultRegistry result){
		this.resultRegistry = result;
	}
	
	public void run() {
		if (result != null || resultRegistry!=null) {
			CreateEntryDialog dialog = new CreateEntryDialog(window.getShell());
			dialog.create();
			dialog.setTitle("Create new ontology metadata entry");
			dialog.open();
			if (dialog.getReturnCode() == IDialogConstants.FINISH_ID) {
				Entity resourceToSave = dialog.getEntry();
				resourcesToProcess.add(resourceToSave);
				ProgressMonitorDialog progressBar = new ProgressMonitorDialog(this.window.getShell());
				try {
					progressBar.run(true, true, this);
				} catch (Exception e) {
					//LOG.error(e.getMessage(), e);
				}
			}
		}else {
			errorDialog("Error", "Action is not initialized.");
		}
	}
	
	protected void entrySaved(final Entity entry){
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				/*TODO entry.addPeer(SystemConfig.getPeerFactory().getLocalPeer());*/
				//result.entryReceived(entry);
				
				if (result != null){
					result.getViewer().refresh();
					final StructuredSelection selection = new StructuredSelection(new Object[] { entry });
					result.getViewer().setSelection(selection);
				}
				else if (resultRegistry != null){
					resultRegistry.getViewer().refresh();
					final StructuredSelection selection = new StructuredSelection(new Object[] { entry });
					resultRegistry.getViewer().setSelection(selection);
				}
			}
		});
	}
}


