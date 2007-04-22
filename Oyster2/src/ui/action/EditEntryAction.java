package ui.action;

import ui.*;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Display;



public class EditEntryAction extends Action {
 
	private StructuredViewer viewer;
	private Result result;
	private ResultRegistry resultRegistry;

	public EditEntryAction() {
		setToolTipText("Edit selected bibliographical entry");
		setText("Edit@Ctrl+E");
	}

	public void setResult(Result result) {
		if(result !=null){
			this.viewer = result.getViewer();
			this.result = result;
		}
	}

	public void setResult(ResultRegistry result) {
		if(result !=null){
			this.viewer = result.getViewer();
			this.resultRegistry = result;
		}
	}
	
	public void run() {
		System.out.println("edit Action starts...");
		if(viewer==null){System.out.println("viewer is null in edit Action");}
		/*TODO if (viewer != null) {
			StructuredSelection selection = (StructuredSelection) viewer.getSelection();
			Object element = selection.getFirstElement();
			RDFSerializer serializer = new RDFSerializer();
			if (element instanceof Resource) {
				before = (Resource) element;
				EntryEditorDialog dialog = new EntryEditorDialog(Display.getDefault().getActiveShell(), (Resource) element);
				dialog.create();
				dialog.setTitle("Edit bibliographical entry");
				dialog.open();
				if (dialog.getReturnCode() == IDialogConstants.FINISH_ID) {
					updatedEntry = dialog.getEntry();
					removeEntry(before);
				}				
			} else {
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", "No entry selected.");
					}
				});
			}
		} else {
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", "Action is not initialized.");
				}
			});
		}*/
	}

}
