package org.neontoolkit.changelogging.gui.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.neontoolkit.changelogging.core.OntologyAddRemoveListener;
import org.neontoolkit.changelogging.core.flogic.FlogicChangeListener;
import org.neontoolkit.changelogging.core.owl.OWLChangeListener;
import org.semanticweb.kaon2.api.KAON2Exception;
import org.semanticweb.kaon2.api.Ontology;

import com.ontoprise.ontostudio.datamodel.DatamodelPlugin;

public class EnableLogging implements IWorkbenchWindowActionDelegate {
	
	//private IWorkbenchWindow window;
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		//this.window = window;
	}

	public void run(IAction action) {
		
		IRunnableWithProgress exportJob = new IRunnableWithProgress(){
			public void run(IProgressMonitor monitor) 
				throws InvocationTargetException, InterruptedException {
				monitor.beginTask("starting Logging...", 100);
				try {
					String[] projects = DatamodelPlugin.getDefault().getOntologyProjects();
					monitor.worked(10);
					for(int i=0;i<projects.length;i++){
						if(DatamodelPlugin.getDefault().getProjectOntologyLanguage(projects[i]).equals("FLogic")){
							Set<Ontology> ontos = DatamodelPlugin.getDefault().getKaon2Connection(
									projects[i]).getOntologies();
							for(Ontology onto : ontos){
								FlogicChangeListener listener = new FlogicChangeListener(onto);						
								onto.addOntologyListener(listener);
								Track.FlogicList.put(onto, listener);
							}
						}else if(DatamodelPlugin.getDefault().getProjectOntologyLanguage(projects[i]).equals("OWL")){
							Set<Ontology> ontos = DatamodelPlugin.getDefault().getKaon2Connection(
									projects[i]).getOntologies();
							for(Ontology onto : ontos){
								
								OWLChangeListener listener = new OWLChangeListener(onto, null, false,"",projects[i], Display.getDefault().getActiveShell());
								onto.addOntologyListener(listener);
								Track.OWLList.put(onto, listener);
							}
						}
						monitor.worked(10+90*i/projects.length);
					}					
				} catch (CoreException e) {
					e.printStackTrace();
				} catch (KAON2Exception e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				monitor.worked(100);
				DatamodelPlugin.getDefault().addModuleModifiedListener(new OntologyAddRemoveListener());
				
			}
		};
		ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(Display.getDefault().getActiveShell());
		progressDialog.setBlockOnOpen(false);
		progressDialog.setCancelable(false);
		progressDialog.open();
		try{
			exportJob.run(progressDialog.getProgressMonitor());
		}catch (InvocationTargetException e){
			e.printStackTrace();
		}catch (InterruptedException e){
			e.printStackTrace();
		}
		progressDialog.close();
			
	}
	
	

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
	}

}
