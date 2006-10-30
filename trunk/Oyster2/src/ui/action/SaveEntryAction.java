package ui.action;

import oyster2.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.widgets.Display;

import org.semanticweb.kaon2.api.*;


public class SaveEntryAction extends Action implements IRunnableWithProgress{
	private boolean inProgress;
	private StructuredViewer viewer;
	private ApplicationWindow window;
	private Entity currentMergedResource;
	private Entity currentResource;

	protected LinkedList resourcesToProcess = new LinkedList();
	protected List resourcesToSave = new ArrayList();
	private List resourcesToRemove = new ArrayList();
	private Thread currentThread;
	private IProgressMonitor progressMonitor;
	private Oyster2 mOyster2 = Oyster2.sharedInstance();

	public SaveEntryAction(ApplicationWindow w) {
		setToolTipText("Save selected bibliographical entries");
		setText("Save@Ctrl+S");
		this.window = w;
	}

	public void setResultViewer(StructuredViewer viewer) {
		this.viewer = viewer;
	}

	public void run() {
		System.out.println("button selected!");
		if (viewer != null) {
			inProgress = true;
			StructuredSelection selection = (StructuredSelection) viewer.getSelection();
			Iterator it = selection.iterator();
			List ontologySelectedList = selection.toList();
			//mKaonP2P.getLocalExpertiseRegistry().importToVirtualOntology(ontologySelectedList);
			/*boolean containsLocalEntries = false;
			while (it.hasNext()) {
				Object element = it.next();
				if (element instanceof Entity) {
					Entity res = (Entity) element;
					System.out.println("element selected: "+res.getURI());
				}
			}*/
		}
			
			
	}
	public void run(IProgressMonitor progressMonitor) throws InvocationTargetException, InterruptedException {
		this.progressMonitor = progressMonitor;
		progressMonitor.beginTask("Saving action in progress...", 10);
		progressMonitor.subTask("\tSearching duplicates in LNR");
		progressMonitor.done();
		currentThread = Thread.currentThread();
		if (!resourcesToProcess.isEmpty()) {
			//detectDuplicates();
			try{	
				while(!progressMonitor.isCanceled()){
					synchronized(currentThread){
						currentThread.wait(300);
					}
				}
			}catch(InterruptedException ignore){
				
			}
		}
	}
	protected void errorDialog(final String title, final String message) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(Display.getDefault().getActiveShell(), title, message);
			}
		});
	}
}
