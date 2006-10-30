package ui.action;

import ui.*;
import util.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.widgets.Display;




public class RemoveAllEntriesAction extends Action implements IRunnableWithProgress {

	//private static final Logger LOG = Logger.getLogger(RemoveAllEntriesAction.class);

	private IProgressMonitor progressMonitor = null;
	
	private static String BIBSTER_SUBJECT = "http://www.semanticweb.org/bibster#main";
	private static String IS_SWRC_LOADED = "http://www.semanticweb.org/bibster#isSWRCAdded";
	private static String IS_LOADED_LITERAL = "true";

	private boolean deleteResult = false;
	private ApplicationWindow w = null;
	
	private Result result;
	private ResultViewer resultViewer;
	private DetailViewer oyster2Viewer;
	
	public RemoveAllEntriesAction(ApplicationWindow w) {
		setToolTipText("Remove all bibliographical entry from local repository");
		setText("Delete repository");
		this.w=w;
	}

	public void run() {
		try {
			if (perfomItDialog("Delete local repository","Delete your complete local repository?")) { 
				ProgressMonitorDialog progressBar = new ProgressMonitorDialog(this.w.getShell());
				progressBar.run(true, false, this);
				result = new Result(resultViewer,Resource.DataResource);
				result.getViewer().refresh();
				oyster2Viewer.clear();
			}
		} catch (InvocationTargetException e) {
			//LOG.error(e.getMessage(), e);
			errorDialog("Could not remove all entries ", e.getMessage());
		} catch (InterruptedException ie) {
			//LOG.error(ie.getMessage(), ie);
		}
	}
	
	public void setResult(Result result) {
		this.result = result;
	}
	
	public void setResultViewer(ResultViewer resultViewer) {
		this.resultViewer = resultViewer;
	}
	
	public void setDetailViewer(DetailViewer detailViewer) {
		this.oyster2Viewer = detailViewer;
	}

	public void run(IProgressMonitor progressMonitor)  {
		this.progressMonitor = progressMonitor;
		progressMonitor.beginTask("Removing action in progress...", 10);
//		List entries = result.getAllEntries();
//		resultViewer.redraw();
		deleteLNR();
		//viewer.refresh();
		progressMonitor.done();
	}	
	
	private void deleteLNR() {
		/*TODO SystemConfig.getSWAPRepository().removeStatements(null,null,null);
		try {
			IPreferenceStore prefStore = JFacePreferences.getPreferenceStore();
			//Load SWRC ontology to LNR and set that repository to SWRCOntology class.
			//There is very ugly hack
			SWAPRepository repository = SystemConfig.getSWAPRepository();
			RDFObjectFactory factory = repository.getRDFObjectFactory();
			URI subject = factory.createURI(BIBSTER_SUBJECT);
			URI predicate = factory.createURI(IS_SWRC_LOADED);
			Literal value = factory.createLiteral(IS_LOADED_LITERAL);
			StatementIterator si = repository.getStatements(subject, predicate, value);
			
			if (!si.hasNext()) {
					BufferedInputStream inS = new BufferedInputStream(new
			FileInputStream(BibsterPreferencesPage.DEFAULT_SHORT_SWRC_ONTOLOGY_LOCATION));
					repository.addRdf(inS, SWRCVocabulary.NAMESPACE, RDFFormat.XML);
					repository.addStatement(subject, predicate, value);
			}
                        
			SWAPRepository swrcRepository = new SesameRAMInferencingRepository();
			BufferedInputStream inS = new BufferedInputStream(new
			FileInputStream(prefStore.getString(BibsterPreferencesPage.SWRC_ONTOLOGY_LOCATION_PROP)));
			swrcRepository.addRdf(inS, SWRCVocabulary.NAMESPACE, RDFFormat.XML);
                        
			SWRCOntology.getInstance().setRepository(swrcRepository);

			ACMOntology.getInstance().loadRepository(prefStore.getString(BibsterPreferencesPage.ACM_TOPICS_LOCATION_PROP));

		} catch (IOException ioe) {
			errorDialog("Error", "Can't read ontologies! " + ioe.getMessage());
		} catch (Exception e) {
			errorDialog("Fatal error", "Can't initialize application! " + e.getMessage());
			//System.exit(0);
		}*/
	}
	
	private void errorDialog(final String title, final String message) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(Display.getDefault().getActiveShell(), title, message);
			}
		});
	}
	
	private boolean perfomItDialog(final String title, final String message) {
		this.deleteResult = false;
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				deleteResult = MessageDialog.openConfirm(Display.getDefault().getActiveShell(), title, message);
			}
		});
		return deleteResult;
	}
}

