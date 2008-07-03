package org.neontoolkit.changelogging.menu;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;
import org.neontoolkit.changelogging.core.flogic.FlogicChangeListener;
import org.neontoolkit.changelogging.core.owl.OWLChangeListener;
import org.semanticweb.kaon2.api.KAON2Exception;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.OntologyManager;
import com.ontoprise.ontostudio.datamodel.DatamodelPlugin;
import com.ontoprise.ontostudio.owl.gui.navigator.ontology.OntologyTreeElement;
import org.neontoolkit.registry.api.Oyster2Connection;
import org.neontoolkit.omv.api.core.OMVOntology;

public class Track extends Action implements IObjectActionDelegate {

	private IStructuredSelection selectionOntology;
    // selection specific variables
    protected String moduleId;
    protected String project;
    protected String ontoURI;
    
    public static Oyster2Connection oyster2Conn = null;

    
	private Shell shell;
	public static Map<Ontology, OWLChangeListener> OWLList = 
	new HashMap<Ontology, OWLChangeListener>();
	public static Map<Ontology, FlogicChangeListener> FlogicList = 
	new HashMap<Ontology, FlogicChangeListener>();


	public Track() {
		// TODO Auto-generated constructor stub
	}

	public Track(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public Track(String text, ImageDescriptor image) {
		super(text, image);
		// TODO Auto-generated constructor stub
	}

	public Track(String text, int style) {
		super(text, style);
		// TODO Auto-generated constructor stub
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
		shell = targetPart.getSite().getShell();

	}

	public void run(IAction action) {
		// TODO Auto-generated method stub

		oyster2Conn = StartRegistry.connection;
		Object first = ((IStructuredSelection) selectionOntology).getFirstElement();
		if (first instanceof OntologyTreeElement){
			OntologyTreeElement t = (OntologyTreeElement)first;
			moduleId = t.getModuleId();  	//ontology
			project = t.getProjectName(); //project
			ontoURI = t.getOntologyUri();
			
			if(oyster2Conn != null){
				OMVOntology o = new OMVOntology();
				o.setURI(ontoURI);
				o.setResourceLocator("");
				
				OntologyManager connection = DatamodelPlugin.getDefault().getKaon2Connection(project);
				try {
					Ontology ontology = connection.getOntology(ontoURI);
					if (!oyster2Conn.isTracked(o)){
						oyster2Conn.startTracking(o);
						
						OWLChangeListener listener = new OWLChangeListener(ontology, o);
						ontology.addOntologyListener(listener);
						Track.OWLList.put(ontology, listener);
					}
					else{
						boolean ans=MessageDialog.openQuestion(
								shell,
								"Change Capturing Component",
							"This ontology is already being tracked. Do you want to stop tracking it?");
						if (ans) {
							oyster2Conn.stopTracking(o);
							OWLChangeListener listener = Track.OWLList.remove(ontology);
							listener.persist();
							ontology.removeOntologyListener(listener);
						}
					}
				} catch (KAON2Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else
				MessageDialog.openError(
						shell,
						"Change Capturing Component",
						"Couldn´t connect to registry (make sure it is running)...");
			
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		selectionOntology = (IStructuredSelection) selection;	
	}

}
