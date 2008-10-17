package org.neontoolkit.oyster.plugin.menu.test;

import java.io.File;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import com.ontoprise.ontostudio.gui.navigator.MTreeView;
import com.ontoprise.ontostudio.perspectives.SchemaPerspective;


public class OysterMenuAbstractTest {

	protected static IWorkbench _workbench;
    protected static MTreeView _schemaView;
    protected static IWorkbenchPage _activePage;
    protected static IWorkbenchWindow _activeWorkbenchWindow;
    protected static Display _display;
	
	@BeforeClass 
	   public static void runBeforeClass() throws Exception {
		prepareViews();
	}
	
	@AfterClass 
	   public static void runAfterClass() throws Exception {
		File tFile = new File("server/localRegistry.owl");
		if (tFile.exists())	tFile.delete();
		tFile = new File(".propertiesOyster");
		if (tFile.exists())	tFile.delete();
		tFile = new File("server");
		if (tFile.exists())	tFile.delete();
	}
	
	protected static void prepareViews() throws Exception {
        _workbench = PlatformUI.getWorkbench();
        _activeWorkbenchWindow = _workbench.getActiveWorkbenchWindow();
        _display = _workbench.getDisplay();
        _activePage = _workbench.showPerspective(SchemaPerspective.ID, _activeWorkbenchWindow);
        _activePage.showView(IPageLayout.ID_RES_NAV);
        _activePage.showView(MTreeView.ID);
        _schemaView = (MTreeView) _activePage.findViewReference(MTreeView.ID).getView(true);
    }
	
	
}
