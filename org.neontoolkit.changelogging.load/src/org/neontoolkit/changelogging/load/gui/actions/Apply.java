package org.neontoolkit.changelogging.load.gui.actions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.neontoolkit.changelogging.load.core.LoadThread;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;
import org.neontoolkit.registry.api.Oyster2Connection;
import com.ontoprise.ontostudio.owl.gui.navigator.ontology.OntologyTreeElement;

public class Apply extends Action implements IObjectActionDelegate {
	private Shell shell;
	private IStructuredSelection selectionOntology;
	public static Oyster2Connection oyster2Conn = null;
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	
	
	public Apply() {
		// TODO Auto-generated constructor stub
	}

	public Apply(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public Apply(String text, ImageDescriptor image) {
		super(text, image);
		// TODO Auto-generated constructor stub
	}

	public Apply(String text, int style) {
		super(text, style);
		// TODO Auto-generated constructor stub
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
		
	}

	public void run(IAction action) {
		oyster2Conn = StartRegistry.connection;
		Object first = ((IStructuredSelection) selectionOntology).getFirstElement();
		if (first instanceof OntologyTreeElement){
			OntologyTreeElement t = (OntologyTreeElement)first;
			if(oyster2Conn != null){
				try {
					FileDialog fd = new FileDialog(shell, SWT.OPEN);
					fd.setText("Open");
					//fd.setFilterPath("C:/");
					String[] filterExt = { "*.owl", "*.rdf"};//, ".rtf", "*.*" };
					fd.setFilterExtensions(filterExt);
					String selected = fd.open();
					//System.out.println(selected);
					//reflect changes in navigator
					if (selected!=null)
						executor.execute(new LoadThread(shell,t.getOntologyUri(),t.getProjectName(),selected));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
			else{
				MessageDialog.openError(
						shell,
						"Change Capturing Load Plug-in",
						"Couldn´t connect to registry");
				
			}
			
		}
	}

	
	
	public void selectionChanged(IAction action, ISelection selection) {
		selectionOntology = (IStructuredSelection) selection;

	}
	
}
