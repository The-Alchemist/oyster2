package org.neontoolkit.registry.ui.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class OpenRegistryAction extends Action implements IRunnableWithProgress {

	
	private String filterPath = "./";
	private String filename;
	private IProgressMonitor progressMonitor;

	public OpenRegistryAction() {
		setToolTipText("Open registry");
		this.setText("Open localRegistry");
	}

	public void runWithEvent(Event event) {
		try {
			Shell activeShell = event.display.getActiveShell();
			FileDialog dialog = new FileDialog(activeShell, SWT.OPEN);
			//dialog.setFilterNames (new String [] {"Batch Files", "All Files (*.*)"});
			//dialog.setFilterExtensions (new String [] {"*.bat", "*.*"}); //Windows wild cards
			dialog.setFilterPath(filterPath);
			filename = dialog.open();
			if (filename != null) {
				filterPath = dialog.getFilterPath();
				ProgressMonitorDialog progressBar = new ProgressMonitorDialog(activeShell);
				progressBar.run(true, false, this);
			}
		} catch (InvocationTargetException e) {
			errorDialog("Open registry error", e.getMessage());
		} catch (InterruptedException ie) {
			
		}
	}
	private void errorDialog(final String title, final String message) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(Display.getDefault().getActiveShell(), title, message);
			}
		});
	}
	public void run(IProgressMonitor progressMonitor) throws InvocationTargetException, InterruptedException {
		this.progressMonitor = progressMonitor;
		/*LOG.debug("Importing bibliographical entries from " + filename);
		try {
			File file = new File(filename);
			progressMonitor.beginTask("Importing " + filename + "...", 10);
			progressMonitor.done();
			progressMonitor.subTask("\tExtracting file...");
			ExternalCall external = new ExternalCall(null, new File[] { file });
			progressMonitor.subTask("\tLogging extraction...");
			ExtractBibtexMessage message = new ExtractBibtexMessage(SystemConfig.getPeerFactory().getLocalPeer().getUID());
			message.setTopics(external.getTopicBeans());
			message.setNumberItems(external.getNumberBibItems());
			EvaluationLogger.getLogger().info(message);
			StringBuffer result = external.getResult();
			LOG.debug("Number of imported items: " + external.getNumberBibItems());
			if (external.getNumberBibItems()>0) {
				progressMonitor.subTask("\tAdding statements to local repository...");
				ByteArrayInputStream is = new ByteArrayInputStream(result.toString().getBytes());
				SystemConfig.getSWAPRepository().addRdf(is, "", RDFFormat.XML);
	//			System.out.println(result);
				//--Bad hack but necessary
				progressMonitor.subTask("\tReloading local repository...");
				reloadLNR();
				((DBLPAdvertiser)SystemConfig.getAdvertiser()).refreshPublicationCount();
				
				//-----------------------
				progressMonitor.subTask("\tSending advertisment...");
				Runnable runnable = new Runnable() {
					public void run() {
						try {
							SystemConfig.getAdvertiser().advertise();
						} catch (Exception e) {
							LOG.error(e.getMessage(), e);
						}
					}
				};
				ThreadPool.runAsync(runnable, "EntryFinder");
			} else {
				errorDialog("Import error", "Cannot read file " + filename + ". \n Did not find any bibliographic items.");		
			}

			//			SWAPRepository newRep = new SesameRAMRepository();
			//			is = new ByteArrayInputStream(result.toString().getBytes());
			//			newRep.addRdf(is, "", RDFFormat.XML);
			//			StatementIterator statementIterator = newRep.getStatements(null, null, null);
			//			while (statementIterator.hasNext()) {
			//				Statement statement = statementIterator.next();
			//				System.out.println(statement.getSubject().toString());
			//			}
			//			System.out.println(" ");
			//			SWAPRepository resultx = BibsterURIGenerator.generateURI(newRep);
			//			statementIterator = newRep.getStatements(null, null, null);
			//			while (statementIterator.hasNext()) {
			//				Statement statement = statementIterator.next();
			//				System.out.println(statement.getSubject().toString());
			//			}

		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
			errorDialog("Import error", "Cannot read file " + filename + ". \n" + ex.getMessage());
		}*/
	}
}
