package org.neontoolkit.oyster2.client.gui.views;


import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.GUIConstants;
import org.neontoolkit.oyster2.client.gui.IMessageResolver;
import org.neontoolkit.oyster2.client.gui.MessageResolver;
import org.neontoolkit.oyster2.client.gui.PerspectiveFactory;
import org.neontoolkit.oyster2.client.gui.actions.DownloadOntologyGUIAction;
import org.neontoolkit.oyster2.client.gui.adapter.results.IResultsAdapter;
import org.neontoolkit.oyster2.client.gui.adapter.results.ISearchResult;
import org.neontoolkit.oyster2.client.gui.dialogs.TranslatedAttributeSelectionDialog;
import org.neontoolkit.oyster2.client.gui.results.ResultsLabelProvider;
import org.neontoolkit.oyster2.client.gui.search.model.QueryFactory;
import org.neontoolkit.oyster2.client.gui.views.SearchResultPreferences.ColumnPreferences;
import org.neontoolkit.oyster2.client.gui.views.SearchResultPreferences.TypePreferences;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class SearchResultsView extends ViewPart {
	private TableViewer viewer = null;
	private Composite parent = null;
	private Composite child = null;
	private DownloadOntologyGUIAction downloadAction;
	
	private Action doubleClickAction;
	private Action changeVisibilityAction;
	private IResultsAdapter results = null;
	private IMemento memento = null;
	
	
	//private Map<String,Boolean> visibility = null;
	private IMessageResolver messagesResolver = null;
	private String targetName = null;
	private SearchResultPreferences preferences = null;
	
	
	
	private static final String VISIBILITY_MEMENTO_TAG = "visibility";
	private static final String COLUMN_WIDTH_MEMENTO_TAG = "columnWidth";
	private static final String COLUMN_ORDER_MEMENTO_TAG = "columnOrder";
	

	
	/**
	 * The constructor.
	 */
	public SearchResultsView() {
		//visibility = new HashMap<String, Boolean>();
		preferences = new SearchResultPreferences();
		messagesResolver = new MessageResolver(
				this.getClass().getPackage().getName() + ".SearchMessages");
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		
		parent.setLayout(new FillLayout());
		this.parent = parent;
		makeActions();
		createToolbar();
		makeEmptyView();
		
	}
	
	private void saveTablePreferences() {
		if (viewer == null)
			return;
		TableColumn[] columns =  viewer.getTable().getColumns();
		Object [] columnNames = viewer.getColumnProperties();
		int[] columnOrder = viewer.getTable().getColumnOrder();
		String propertyName = null;
		ColumnPreferences columnPreferences = null;
		
		for (int i = 0; i<columns.length;i++) {
			propertyName = (String)columnNames[i];
			columnPreferences = 
				preferences.getTypePreferences(targetName).getColumn(propertyName);
			
			if (columnPreferences == null)
				columnPreferences = preferences.getTypePreferences(targetName).
				createColumn(propertyName);
			columnPreferences.setWidth(columns[i].getWidth());
			columnPreferences.setOrder(columnOrder[i]);
	
		}
		
	}
	
	public void putResults(IResultsAdapter results) {
		int i = 0;

		if (results == null){
			saveTablePreferences();
			makeEmptyView();
			return;
		}
		changeVisibilityAction.setEnabled(true);
		this.results = results;
		
		
		String newTargetName = 
			QueryFactory.getInstance().getInternalName(results.getResultsType());
		
		TypePreferences typePreferences =
			preferences.getTypePreferences(newTargetName);
		if (typePreferences == null)
			preferences.createTypePreferences(newTargetName);
		
		
		
		boolean makeNewViewer = false;
		if ((targetName != null) && (viewer != null)) {
			makeNewViewer = ! newTargetName.equals(targetName);
		}
		else {
			makeNewViewer = true;
		}
		
		if (makeNewViewer) {
				saveTablePreferences();
				targetName = newTargetName;
				
				if (child != null)
					child.dispose();
				child = new Composite(parent,SWT.NONE);
				child.setLayout(new FillLayout());
				
				String [] columns = results.getResultPropertyNames();
				viewer =
					new TableViewer(child, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL| SWT.FULL_SELECTION);
				
				Table table = viewer.getTable();
				TableColumn column = null;
				
				for (i = 0;i<columns.length;i++) {
					column = new TableColumn(table,SWT.LEFT);
					column.setText(results.getLabel(columns[i]));
					column.setData(columns[i]);
					column.setMoveable(true);
				}
				
				loadVisibility();
				//updateTableVisibility();
				
				viewer.setContentProvider(new ArrayContentProvider());
				
				table.setHeaderVisible(true);
				table.setLinesVisible(true);
				
				
				ResultsLabelProvider labelProvider =
					new ResultsLabelProvider(viewer);
				viewer.setLabelProvider(labelProvider);
				viewer.setColumnProperties(columns);
				viewer.setInput(results.getResults());
				updateTableVisibility();
				loadTablePreferences();
				
			}
			else {
				// just update the input in the table
				viewer.setInput(results.getResults());
			}
				
				
		
		hookContextMenu();
		hookDoubleClickAction();
		parent.layout();
	}

	private void updateTableVisibility() {
		for (TableColumn column : this.viewer.getTable().getColumns()) {
			viewer.getTable().showColumn(column);
			if (columnIsVisible((String)column.getData())) {
				column.pack();
				if (column.getWidth() > GUIConstants.DEFAULT_COLUMN_WIDTH)
					column.setWidth(GUIConstants.DEFAULT_COLUMN_WIDTH);
				column.setResizable(true);
			}
			else {
				column.setWidth(0);
				column.setResizable(false);
			}
		}
		
	}

	private boolean columnIsVisible(String propertyName) {
		/*IMemento typeMemento = memento.getChild(results.getResultsType());
		String showPreference = typeMemento.getString(propertyName);
		if (Boolean.valueOf(showPreference)) {
			return true;
		}
		return false;*/
		
		
		Boolean visible = preferences.getTypePreferences(targetName).getVisibility().get(propertyName);
		if (visible == null)
			return false;
		return visible;
		//return visibility.get(propertyName);
	}

	

	private void makeEmptyView() {
		if (child != null)
			child.dispose();
		viewer = null;
		changeVisibilityAction.setEnabled(false);
		/*String actionText = messagesResolver.
		getString("SearchResultsView.action.changeVisibility.action");
		toolbarManager.remove(actionText);*/
		child = new Composite(parent,SWT.NONE);
		child.setLayout(new FillLayout());
		Label label = new Label(child,SWT.NONE);
		label.setText("No results were found");
		parent.layout(true,true);
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				SearchResultsView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}



	private void fillContextMenu(IMenuManager manager) {
		manager.add(downloadAction);
		
		// Other plug-ins can contribute their actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	

	private void makeActions() {
		downloadAction = new DownloadOntologyGUIAction();
		downloadAction.setText("download");
		downloadAction.setView(this);
		
		//downloadAction.setSelection((IStructuredSelection)viewer.getSelection());
		downloadAction.setToolTipText("Downloads the ontology from the URL specified by it's resource locator");
		downloadAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				ISearchResult result = (ISearchResult) ((IStructuredSelection)selection).getFirstElement();
				
				
				IWorkbenchPage page =
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				
				
				try {
					SearchResultDetailsView view = (SearchResultDetailsView)
						page.showView(PerspectiveFactory.OYSTER2_RESULT_DETAILS_VIEW_ID);
					
					view.setDetails(result);
					
				} catch (PartInitException e) {
					e.printStackTrace();
				}
				//showMessage("Double-click detected on "+result.toString());
			}
		};
		String actionText = messagesResolver.
			getString("SearchResultsView.action.changeVisibility.action");
		changeVisibilityAction = new Action(actionText){
			@Override
			public void run() {
				changeVisibility();
			}
		};
		changeVisibilityAction.setImageDescriptor(getImageDescriptor("select.gif"));
		actionText = messagesResolver.getString
		("SearchResultsView.action.changeVisibility.action.tooltip");
	}

	
	private void changeVisibility() {
		String section = "SearchResultsView";
		Shell shell = this.getSite().getShell();
		TranslatedAttributeSelectionDialog dialog = 
			new TranslatedAttributeSelectionDialog(section,shell,null);
		IMessageResolver messageResolver = 
			results.getMessagesResolver();
		dialog.setMessagesResolver(messageResolver);
		dialog.setSelection(preferences.getTypePreferences(targetName).getVisibility());
		int result = dialog.open();
		
		if (result == dialog.OK) {
			updateTableVisibility();
		}
	}
	
	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Search results",
			message);
	}

	 /**
     * Create toolbar.
     */
    private void createToolbar() {
            IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
            
            toolbarManager.add(this.changeVisibilityAction);
            
    }
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.ViewPart#init(org.eclipse.ui.IViewSite, org.eclipse.ui.IMemento)
	 */
	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site);
		this.memento = memento;
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.ViewPart#saveState(org.eclipse.ui.IMemento)
	 */
	@Override
	public void saveState(IMemento memento) {
		saveTablePreferences();
		preferences.save(memento);
		
		
	}
	
	private void loadTablePreferences() {
		
		TableColumn[] columns = viewer.getTable().getColumns();
		int[] columnOrder = viewer.getTable().getColumnOrder();
		Object [] columnNames = viewer.getColumnProperties();
		TypePreferences typePreferences =preferences.getTypePreferences(targetName);
		if (typePreferences.getColumns().size() == 0) {
			return;
		}
		String propertyName;
		int position = 0;
		/*IMemento typeMemento = memento.getChild(this.targetName);
		if (typeMemento == null) {
			return;
		}*/
		
		int width = 0;
		//IMemento propertyMemento = null;
		for (int i = 0; i< columnNames.length;i++) {
			propertyName = (String)columnNames[i];
			//propertyMemento = typeMemento.getChild(propertyName);
			
			if (typePreferences.getColumn(propertyName) == null)
				continue;
			
			width = typePreferences.getColumn(propertyName).getWidth();
			
			position = typePreferences.getColumn(propertyName).getOrder();
			
			columns[i].setWidth(width);
			columnOrder[i] = position;
			
		}
		viewer.getTable().setColumnOrder(columnOrder);
		return ;
	}
	
	private void loadVisibility() {
		
		// TODO esto parece redundante
		//String newTargetName = 
		//	QueryFactory.getInstance().getInternalName(results.getResultsType());
		
		
		
		if (preferences.getTypePreferences(targetName).getVisibility().size() == 0) {
			if (memento == null) {
				loadDefaultVisibility();
			}
			else {
				preferences.load(memento, targetName,results.getResultPropertyNames());
			}
			return;
		}
		/*
		IMemento typeMemento = memento.getChild(targetName);
		if (typeMemento == null) {
			loadDefaultVisibility();
			return;
		}*/
		
		
		/*
		
		
		//visibility.clear();
		
		
		IMemento propertyMemento = null;
		
		//TODO quizas sea solo getResultMainProperties??
		for (String propertyName : results.getResultPropertyNames()) {
			
			// es null cuando se ha hecho save y no habia viewer
			propertyMemento = typeMemento.getChild(propertyName);
			
			showPreference = propertyMemento.getString(VISIBILITY_MEMENTO_TAG);
			if (Boolean.valueOf(showPreference)) {
				preferences.getTypePreferences(targetName).getVisibility().
					put(propertyName,Boolean.TRUE);
			}
			else {
				preferences.getTypePreferences(targetName).getVisibility().
					put(propertyName,Boolean.FALSE);
			}
		}
		
		return ;
		*/
	}

	public void loadDefaultVisibility() {
		preferences.createTypePreferences(targetName);
		String[] defaultVisibility = results.getMainProperties();
		for (String propertyName : results.getResultPropertyNames()) {
			
			preferences.getTypePreferences(targetName).setVisibility(propertyName,false);
			//preferences.getTypePreferences(targetName).getVisibility().
			//	put(propertyName, false);
		}
		for (String propertyName : defaultVisibility)  {
			
			preferences.getTypePreferences(targetName).setVisibility(propertyName,true);
			//preferences.getTypePreferences(targetName).getVisibility().
			//	put(propertyName, true);
		}
		return ;
	}
	
	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		//viewer.getControl().setFocus();
	}
	
	
	 /**
     * Returns the image descriptor with the given relative path.
     */
    private ImageDescriptor getImageDescriptor(String iconName) {
            String iconPath = "icons/";
            try {
                    Activator plugin = Activator.getDefault();
                    
                    Path path = new Path("icons/" + iconName); 
                    URL url = FileLocator.toFileURL(FileLocator.find(Activator.getDefault().getBundle(),
                			path, null));

                    return ImageDescriptor.createFromURL(url);
            }
            catch (Exception e) {
                    // should not happen
                    return ImageDescriptor.getMissingImageDescriptor();
            }
    }

	public IResultsAdapter getResults() {
		return results;
	}

	public void setResults(IResultsAdapter results) {
		this.results = results;
	}

	public TableViewer getViewer() {
		return viewer;
	}

	public void setViewer(TableViewer viewer) {
		this.viewer = viewer;
	}



	
}