package org.neontoolkit.changelogging.menu;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;
import org.neontoolkit.registry.api.Oyster2Connection;


import com.ontoprise.ontostudio.owl.gui.navigator.ontology.OntologyTreeElement;

public class Syncrhonize extends Action implements IObjectActionDelegate {

	private Shell shell;
	private IStructuredSelection selectionOntology;
	public static Oyster2Connection oyster2Conn = null;
	protected String moduleId;
    protected String project;
    protected String ontoURI;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
	
	public Syncrhonize() {
		// TODO Auto-generated constructor stub
	}
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
		shell = targetPart.getSite().getShell();
		
	}
	public void run(IAction action) {
		oyster2Conn = StartRegistry.connection;
		Object first = ((IStructuredSelection) selectionOntology).getFirstElement();
		if (first instanceof OntologyTreeElement){
			OntologyTreeElement t = (OntologyTreeElement)first;
			moduleId = t.getModuleId();  	//ontology
			project = t.getProjectName(); //project
			ontoURI = t.getOntologyUri();
			if(oyster2Conn != null){
				try {
					//reflect changes in navigator
					executor.execute(new ReflectInNavigatorThread());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
				MessageDialog.openError(
						shell,
						"Change Capturing Component",
						"Couldn´t connect to registry to syncrhonize (make sure it is running)...");
		}
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		selectionOntology = (IStructuredSelection) selection;
	}

}
