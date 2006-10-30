package ui.action;

import ui.*;
import ui.editor.*;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.widgets.Display;

import org.semanticweb.kaon2.api.*;



public class NewEntryAction extends SaveEntryAction{
	private ApplicationWindow window;
	private Result result;

	public NewEntryAction(ApplicationWindow w) {
		super(w);
		window = w;
		setToolTipText("Create new ontology metadata entry");
		setText("Create@Ctrl+N");
	}

	public void setResult(Result result){
		this.result = result;
	}
	
	public void run() {
		if (result != null) {
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
				result.getViewer().refresh();
				final StructuredSelection selection = new StructuredSelection(new Object[] { entry });
				result.getViewer().setSelection(selection);		
			}
		});
	}
}


