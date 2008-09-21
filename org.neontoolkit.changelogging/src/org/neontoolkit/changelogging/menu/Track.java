package org.neontoolkit.changelogging.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;
import org.neontoolkit.changelogging.core.flogic.FlogicChangeListener;
import org.neontoolkit.changelogging.core.owl.OWLChangeListener;
import org.neontoolkit.changelogging.gui.NTKNavigatorListener;
import org.semanticweb.kaon2.api.KAON2Exception;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.OntologyManager;
import com.ontoprise.ontostudio.datamodel.DatamodelPlugin;
import com.ontoprise.ontostudio.gui.GuiPlugin;
import com.ontoprise.ontostudio.gui.navigator.MTreeView;
import com.ontoprise.ontostudio.owl.gui.individualview.IndividualView;
import com.ontoprise.ontostudio.owl.gui.individualview.IndividualViewItem;
import com.ontoprise.ontostudio.owl.gui.navigator.clazz.ClazzFolderTreeElement;
import com.ontoprise.ontostudio.owl.gui.navigator.clazz.ClazzTreeElement;
import com.ontoprise.ontostudio.owl.gui.navigator.datatypes.DatatypeFolderTreeElement;
import com.ontoprise.ontostudio.owl.gui.navigator.datatypes.DatatypeTreeElement;
import com.ontoprise.ontostudio.owl.gui.navigator.ontology.OntologyTreeElement;
import com.ontoprise.ontostudio.owl.gui.navigator.property.annotationProperty.AnnotationPropertyFolderTreeElement;
import com.ontoprise.ontostudio.owl.gui.navigator.property.annotationProperty.AnnotationPropertyTreeElement;
import com.ontoprise.ontostudio.owl.gui.navigator.property.dataProperty.DataPropertyFolderTreeElement;
import com.ontoprise.ontostudio.owl.gui.navigator.property.dataProperty.DataPropertyTreeElement;
import com.ontoprise.ontostudio.owl.gui.navigator.property.objectProperty.ObjectPropertyFolderTreeElement;
import com.ontoprise.ontostudio.owl.gui.navigator.property.objectProperty.ObjectPropertyTreeElement;
import org.neontoolkit.registry.api.Oyster2Connection;
import org.neontoolkit.omv.api.core.OMVOntology;

public class Track extends Action implements IObjectActionDelegate {

	private IStructuredSelection selectionOntology;
    // selection specific variables
    protected String moduleId;
    protected String project;
    protected String ontoURI;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    public static Oyster2Connection oyster2Conn = null;
    private IPreferenceStore _store = GuiPlugin.getDefault().getPreferenceStore();

	private Shell shell;
	public static Map<Ontology, OWLChangeListener> OWLList = 
	new HashMap<Ontology, OWLChangeListener>();
	public static Map<Ontology, FlogicChangeListener> FlogicList = 
	new HashMap<Ontology, FlogicChangeListener>();

	private static String selectedElement="";
	private static String selectedClass="";
	private static String selectedOB="";
	private static String selectedDP="";
	private static String selectedAP="";
	private static String selectedDT="";
	static boolean addInd = false;
	boolean del = false;
	
	public Track() {
		addNTKListener();
	}

	public Track(String text) {
		super(text);
	}

	public Track(String text, ImageDescriptor image) {
		super(text, image);
	}

	public Track(String text, int style) {
		super(text, style);
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
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
				executor.execute(new doTrack(shell));
			}else
				MessageDialog.openError(
						shell,
						"Change Capturing Component",
						"Couldn´t connect to registry (make sure it is running)...");
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		selectionOntology = (IStructuredSelection) selection;	
	}

	private void addNTKListener(){
		NTKNavigatorListener listenerT = new NTKNavigatorListener();
		MTreeView t = (MTreeView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(MTreeView.ID);
		t.addEditorListener(listenerT);
		t.getTreeViewer().addSelectionChangedListener(new ISelectionChangedListener() {
            public void selectionChanged(SelectionChangedEvent event) {
            	if (((TreeSelection)event.getSelection()).size()==1){
                	if (((TreeSelection)event.getSelection()).getFirstElement() instanceof ClazzTreeElement){
                		ClazzTreeElement sel= (ClazzTreeElement)((TreeSelection)event.getSelection()).getFirstElement();
                		selectedElement = sel.getOntologyUri()+"#"+sel.getLocalName();
                		selectedClass = selectedElement;
                	}
                	else if (((TreeSelection)event.getSelection()).getFirstElement() instanceof ObjectPropertyTreeElement){
                		ObjectPropertyTreeElement sel= (ObjectPropertyTreeElement)((TreeSelection)event.getSelection()).getFirstElement();
                		selectedElement = sel.getOntologyUri()+"#"+sel.getLocalName();
                		selectedOB = selectedElement;
                	}
                	else if (((TreeSelection)event.getSelection()).getFirstElement() instanceof DataPropertyTreeElement){
                		DataPropertyTreeElement sel= (DataPropertyTreeElement)((TreeSelection)event.getSelection()).getFirstElement();
                		selectedElement = sel.getOntologyUri()+"#"+sel.getLocalName();
                		selectedDP = selectedElement;
                	}
                	else if (((TreeSelection)event.getSelection()).getFirstElement() instanceof AnnotationPropertyTreeElement){
                		AnnotationPropertyTreeElement sel= (AnnotationPropertyTreeElement)((TreeSelection)event.getSelection()).getFirstElement();
                		selectedElement = sel.getOntologyUri()+"#"+sel.getLocalName();
                		selectedAP = selectedElement;
                	}
                	else if (((TreeSelection)event.getSelection()).getFirstElement() instanceof DatatypeTreeElement){
                		DatatypeTreeElement sel= (DatatypeTreeElement)((TreeSelection)event.getSelection()).getFirstElement();
                		selectedElement = sel.getOntologyUri()+"#"+sel.getLocalName();
                		selectedDT = selectedElement;
                	}
                	else if (((TreeSelection)event.getSelection()).getFirstElement() instanceof ClazzFolderTreeElement){
                		selectedElement=selectedClass;
                		selectedOB="";selectedDP="";selectedAP="";selectedDT="";
                		//if (del) selectedElement=selectedClass;
                		//else selectedElement="";
                	}
                	else if (((TreeSelection)event.getSelection()).getFirstElement() instanceof ObjectPropertyFolderTreeElement){
                		selectedElement=selectedOB;
                		selectedClass="";selectedDP="";selectedAP="";selectedDT="";
                		//if (del) selectedElement=selectedOB;
                		//else selectedElement="";
                	}
                	else if (((TreeSelection)event.getSelection()).getFirstElement() instanceof DataPropertyFolderTreeElement){
                		selectedElement=selectedDP;
                		selectedClass="";selectedOB="";selectedAP="";selectedDT="";
                		//if (del) selectedElement=selectedDP;
                		//else selectedElement="";
                	}
                	else if (((TreeSelection)event.getSelection()).getFirstElement() instanceof AnnotationPropertyFolderTreeElement){
                		selectedElement=selectedAP;
                		selectedClass="";selectedOB="";selectedDP="";selectedDT="";
                		//if (del) selectedElement=selectedAP;
                		//else selectedElement="";
                	}
                	else if (((TreeSelection)event.getSelection()).getFirstElement() instanceof DatatypeFolderTreeElement){
                		selectedElement=selectedDT;
                		selectedClass="";selectedOB="";selectedDP="";selectedAP="";
                		//if (del) selectedElement=selectedDT;
                		//else selectedElement="";
                	}
                	else{
                		selectedElement="";
                	}
                	del = false;
                	//System.out.println("element selected has: "+selectedElement);
                }
            	else if (((TreeSelection)event.getSelection()).size()==0){
                	del = true;
                	//System.out.println("elemented selected deleted");
                }
                else if (((TreeSelection)event.getSelection()).size()>1){
                	selectedElement ="";
                	del = false;
                }
            }
        });
	
		
		IndividualView iv = (IndividualView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(IndividualView.ID);;
		if (iv != null)
			iv.getTreeViewer().addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent event) {
					if (((TreeSelection)event.getSelection()).size()==1){
						if (((TreeSelection)event.getSelection()).getFirstElement() instanceof IndividualViewItem){
							IndividualViewItem sel= (IndividualViewItem)((TreeSelection)event.getSelection()).getFirstElement();
	                		selectedElement = sel.getId();
	                	}
						addInd=false;
					}
					else if (((TreeSelection)event.getSelection()).size()==0){
						selectedElement =selectedClass;
						addInd=true;
					}
					else if (((TreeSelection)event.getSelection()).size()>1){
						selectedElement ="";
						addInd=false;
					}
				}
			});
			
		
	}
	 
	public static boolean getAddedIndividual(){
		return addInd;
	}
	public static void resetAddedIndividual(){
		addInd=false;
	}
	public static String getSelectedElement(){
		return selectedElement;
	}
	public static void setSelectedElement(String e){
		selectedElement=e;
	}
	
	public class doTrack implements Runnable {
		private Shell shell;
		
		public doTrack(Shell arg){
			this.shell=arg;
		}
		public void run() {
			shell.getDisplay().asyncExec(new Runnable () {
				public void run () {
					OMVOntology o = new OMVOntology();
					o.setURI(ontoURI);
					o.setResourceLocator("");
					
					OntologyManager connection = DatamodelPlugin.getDefault().getKaon2Connection(project);
					try {
						Ontology ontology = connection.getOntology(ontoURI);
						if (!oyster2Conn.isTracked(o)){
							addTrack(ontology, o);
						}
						else{
							if (!Track.OWLList.containsKey(ontology)){
								addTrack(ontology,o);
							}
							else{
								boolean ans=MessageDialog.openQuestion(
									shell,
									"Change Capturing Component",
									"This ontology is already being tracked. Do you want to stop tracking it?");
								if (ans) {
									oyster2Conn.stopTracking(o);
									OWLChangeListener listener = Track.OWLList.remove(ontology);
									ontology.removeOntologyListener(listener);
								}
							}
						}
					} catch (KAON2Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		private void addTrack(Ontology ontology, OMVOntology o){
			String role = _store.getString("ROLE"); 
			String firstname = _store.getString("USER_FIRSTNAME");
			String lastname = _store.getString("USER_LASTNAME");
			if (lastname==null || firstname==null || role==null || lastname.equalsIgnoreCase("") || firstname.equalsIgnoreCase("") || role.equalsIgnoreCase("")) {
				MessageDialog.openError(
						shell,
						"Change Capturing Component",
						"You have to be logged-in first...");
				return;
			}
			oyster2Conn.startTracking(o);
			
			boolean collab = false;
			if (ontology.getPhysicalURI().startsWith("collab")) collab=true;
			OWLChangeListener listener = new OWLChangeListener(ontology, o, collab,moduleId, project);
			ontology.addOntologyListener(listener);
			Track.OWLList.put(ontology, listener);
		}
	}
}
