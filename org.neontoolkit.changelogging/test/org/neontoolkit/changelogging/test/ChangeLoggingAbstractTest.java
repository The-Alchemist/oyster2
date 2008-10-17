package org.neontoolkit.changelogging.test;



import java.io.File;
import java.net.URI;
import java.util.Properties;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import com.ontoprise.ontostudio.io.ImportExportControl;
import com.ontoprise.config.IConfig;
import com.ontoprise.ontostudio.datamodel.DatamodelPlugin;
import com.ontoprise.ontostudio.gui.commands.project.CreateProject;
import com.ontoprise.ontostudio.gui.navigator.MTreeView;
import com.ontoprise.ontostudio.gui.navigator.module.ModuleControl;
import com.ontoprise.ontostudio.owl.perspectives.OWLPerspective;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class ChangeLoggingAbstractTest {

	protected static IWorkbench _workbench;
    protected static MTreeView _schemaView;
    protected static IWorkbenchPage _activePage;
    protected static IWorkbenchWindow _activeWorkbenchWindow;
    protected static Display _display;
    protected static IProject _project;
    protected static final String ONTOLOGY_SOURCE = "http://www.co-ode.org/ontologies/pizza/pizza_20041007.owl"; 
    protected static final String ONTOLOGY_PROJECT = "testproject"; 
	
	@BeforeClass 
	   public static void runBeforeClass() throws Exception {
		prepareViews();
		prepareWorkspace();
		assertEquals("Workspace is not empty", 0, ResourcesPlugin.getWorkspace().getRoot().getProjects().length);
		createProject();
		loadOntology();
	}
	
	@AfterClass 
	   public static void runAfterClass() throws Exception {
		File tFile = new File("server/localRegistry.owl");
		if (tFile.exists())	tFile.delete();
		tFile = new File(".propertiesOyster");
		if (tFile.exists())	tFile.delete();
		tFile = new File("server");
		if (tFile.exists())	tFile.delete();
		prepareWorkspace();
	}
	
	protected static void prepareViews() throws Exception {
        _workbench = PlatformUI.getWorkbench();
        _activeWorkbenchWindow = _workbench.getActiveWorkbenchWindow();
        _display = _workbench.getDisplay();
        _activePage = _workbench.showPerspective(OWLPerspective.ID, _activeWorkbenchWindow);
        _activePage.showView(IPageLayout.ID_RES_NAV);
        _activePage.showView(MTreeView.ID);
        _schemaView = (MTreeView) _activePage.findViewReference(MTreeView.ID).getView(true);
    }

	
	protected static void prepareWorkspace() throws Exception {
	    String[] projects = DatamodelPlugin.getDefault().getOntologyProjects();
	    for (int i = 0; i < projects.length; i++) {
	        String[] ontologies = DatamodelPlugin.getDefault().getProjectOntologies(projects[i]);
	        for (int j = 0; j < ontologies.length; j++) {
	            ModuleControl.getDefault().removeModule(ontologies[j], projects[i], true);
	        }
	        IProject project = DatamodelPlugin.getDefault().getProject(projects[i]);
	        project.delete(true, null);
	    }
	    ResourcesPlugin.getWorkspace().getRoot().delete(true, null);
	}
	
	//create a new OWL project 
    protected static void createProject() throws Exception {
		Properties projectProperties = new Properties();
		projectProperties.put(IConfig.STORAGE, "RAM.choose");
		projectProperties.put(IConfig.ONTOLOGY_LANGUAGE, "OWL");
    	new CreateProject(ONTOLOGY_PROJECT, "RAM.choose", projectProperties).run();
    	_project = ResourcesPlugin.getWorkspace().getRoot().getProject(ONTOLOGY_PROJECT);
    	assertNotNull("Project was not created",_project);
    }
    
    protected static void loadOntology() throws Exception {
    	URI[] urls = new URI[1];
    	urls[0] = new URI(ONTOLOGY_SOURCE);//new File(ONTOLOGY_SOURCE).toURI();
    	ImportExportControl control = new ImportExportControl();
    	try {
    		control.importFileSystem(ONTOLOGY_PROJECT, urls, null);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
}
