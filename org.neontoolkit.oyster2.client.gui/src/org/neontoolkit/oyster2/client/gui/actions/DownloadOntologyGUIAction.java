/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.adapter.IOMVObject;
import org.neontoolkit.oyster2.client.gui.dialogs.OntologyImportationDialog;
import org.neontoolkit.oyster2.client.gui.jobs.ImportOntologyJob;
import org.neontoolkit.oyster2.client.gui.views.SearchResultsView;

/**
 * @author david
 *
 */
public class DownloadOntologyGUIAction extends Action {
	
	
	private SearchResultsView view = null;

	public void run() {
		
		Object [] selectedResults =
			((IStructuredSelection)view.getViewer().getSelection()).toArray();
		
		String [] ontologyLocators = null;
		IOMVObject[] selectedSearchResults =
			new IOMVObject[selectedResults.length];
		ontologyLocators = new String[selectedResults.length];
		for (int i = 0; i<selectedResults.length;i++) {
			selectedSearchResults[i] = (IOMVObject)selectedResults[i];
			ontologyLocators[i] = (String)selectedSearchResults[i].getValue("resourceLocator");
		}

		
		Shell shell = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();

		OntologyImportationDialog dialog =
			new OntologyImportationDialog(shell);
		dialog.setColumnsToShow(view.getResults().getMainProperties());
		dialog.setResults(view.getResults());
		dialog.setSelection(selectedSearchResults);
		int result = dialog.open();
		
		if (result == OntologyImportationDialog.OK) {
			//OntologyImporter
			ImportOntologyJob job = new ImportOntologyJob("Import ontology");
			job.setProject(dialog.getProject());
			job.setOntologyLocators(ontologyLocators);
			job.schedule();
		}
		
	}

	public SearchResultsView getView() {
		return view;
	}

	public void setView(SearchResultsView view) {
		this.view = view;
	}

	
	
}
