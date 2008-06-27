package org.neontoolkit.registry.ui.action;

import java.lang.reflect.InvocationTargetException;
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


public class RemoveEntryAction extends Action implements IRunnableWithProgress {


	private StructuredViewer viewer;
	private List entries = new LinkedList();
	private boolean inProgress;
	private boolean containsRemoteEntries;
	private ApplicationWindow window;
	private Thread currentThread;
	private IProgressMonitor progressMonitor;

	public RemoveEntryAction(ApplicationWindow w) {
		setToolTipText("Remove selected bibliographical entry from local repository");
		setText("Remove@Ctrl+R");
		this.window = w;
	}

	public void setResultViewer(StructuredViewer viewer) {
		this.viewer = viewer;
	}

	public void run() {
		if (viewer != null) {
			if (!inProgress) {
				inProgress = true;
				StructuredSelection selection = (StructuredSelection) viewer.getSelection();
				entries.clear();
				Iterator it = selection.iterator();
				while (it.hasNext()) {
					Object element = it.next();
					if (element instanceof Entity) {
						Entity resourceToRemove = (Entity) element;
						/*TODO if (resourceToRemove.isLocal())
							entries.add(resourceToRemove);
						else
							containsRemoteEntries = true;*/
					}
				}
				if (containsRemoteEntries && entries.size() > 0) {
					Display.getDefault().syncExec(new Runnable() {
						public void run() {
							inProgress =
								MessageDialog.openConfirm(
									Display.getDefault().getActiveShell(),
									"Warning",
									"Some of entries you have selected aren't local. Would you like to continue?");
						}

					});
				}else if(entries.size() < 1) {
					inProgress = false;
					errorDialog("Error", "No bibliographical entries to remove.");
				}
				if (inProgress){
					ProgressMonitorDialog progressBar = new ProgressMonitorDialog(this.window.getShell());
					try{
						progressBar.run(true, false, this);
					}catch(Exception e){
						//LOG.error(e.getMessage(), e);
					}
				}
			}
		} else {
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", "Action is not initialized.");
				}
			});
		}
	}
	
	public void run(IProgressMonitor progressMonitor) throws InvocationTargetException, InterruptedException {
		this.progressMonitor = progressMonitor;
		progressMonitor.beginTask("Removing action in progress...", 10);
		progressMonitor.done();
		currentThread = Thread.currentThread();
		removeEntries(entries);
		try{	
			while(!progressMonitor.isCanceled()){
				synchronized(currentThread){
					currentThread.wait(300);
				}
			}
		}catch(InterruptedException ignore){
			
		}
	}

	private void removeEntries(List entries) {
		/*TODO Operation remover = BibsterController.getInstance().removeEntries(entries);
		remover.addOperationListener(new OperationListener() {
			public void operationError(Throwable t) {
				removingError(t);
			}
			public void operationFinished() {
				removingFinished();
			}
		});
		try {
			remover.performOperation();
		} catch (Exception e) {
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", e.getMessage());
			synchronized(this.currentThread){
				this.currentThread.interrupt();
			}
			inProgress = false;
		}*/
	}

	private void removingFinished() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				for (int i = 0; i < entries.size(); i++) {
					/*TODO Entity res = (Entity) entries.get(i);
					res.setLocal(false);
					List peers = res.getPeers();
					peers.remove(SystemConfig.getPeerFactory().getLocalPeer());
					viewer.refresh(res);*/
				}
			}
		});
		synchronized(this.currentThread){
			this.currentThread.interrupt();
		}
		inProgress = false;
	}

	private void removingError(final Throwable t) {
		inProgress = false;
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", t.getMessage());
			}
		});
		synchronized(this.currentThread){
			this.currentThread.interrupt();
		}
	}
	
	private void errorDialog(final String title, final String message) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(Display.getDefault().getActiveShell(), title, message);
			}
		});
	}
}
