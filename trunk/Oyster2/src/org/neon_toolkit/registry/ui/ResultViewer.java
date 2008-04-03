package org.neon_toolkit.registry.ui;

//import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
//import org.eclipse.jface.preference.IPreferenceStore;
//import org.eclipse.jface.preference.JFacePreferences;
//import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
//import org.eclipse.swt.custom.TableTree;
//import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
//import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
//import org.eclipse.swt.widgets.TableColumn;
import org.neon_toolkit.registry.oyster2.Constants;
import org.neon_toolkit.registry.oyster2.Oyster2Factory;
import org.neon_toolkit.registry.oyster2.Properties;
import org.neon_toolkit.registry.oyster2.QueryReply;
import org.neon_toolkit.registry.ui.action.SetColumnVisibleAction;
import org.neon_toolkit.registry.ui.provider.ResultViewerContentProvider;
import org.neon_toolkit.registry.ui.provider.ResultViewerLabelProvider;
import org.neon_toolkit.registry.util.RDFS;
import org.neon_toolkit.registry.util.Resource;
import org.semanticweb.kaon2.api.*;
import org.semanticweb.kaon2.api.owl.elements.*;


public class ResultViewer extends Composite {
	
	private static String OMV = "http://omv.ontoware.org/2005/05/ontology#";
	private TreeViewer wrapedViewer;
	private ResultViewerContentProvider contentProvider;
	private ResultViewerLabelProvider labelProvider;
	private ResultSorter sorter;
	private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();

	private List<ResultViewerColumnInfo> columns = new LinkedList<ResultViewerColumnInfo>();
	private Map<String, SetColumnVisibleAction> contextMenuActions = new HashMap<String, SetColumnVisibleAction>();
	
	public ResultViewer(Composite parent, int style) throws Exception {
		
		super(parent, SWT.NONE);
		this.setLayout(new FillLayout());
		Tree tree = new Tree(this, style);
		wrapedViewer = new TreeViewer(tree);
		wrapedViewer.expandToLevel(2);
		contentProvider = new ResultViewerContentProvider(Resource.DataResource);
		wrapedViewer.setContentProvider(contentProvider);
		labelProvider = new ResultViewerLabelProvider(this);
		wrapedViewer.setLabelProvider(labelProvider);
		sorter = new ResultSorter();
		wrapedViewer.setSorter(sorter);
		wrapedViewer.getTree().setHeaderVisible(true);	
		Listener listener = new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
					case SWT.Dispose: onDispose(e); break;
				}
			}
		};
		addListener(SWT.Dispose, listener);
		initContextMenu(); 
		initColumns();
	}
	
	
	/**
	 * Initialize context menu of this component.
	 */
	private void initContextMenu() throws Exception {
		
		MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(wrapedViewer.getTree());
		wrapedViewer.getTree().setMenu(menu);
		
		//--RDF and RDFS columns
		ResultViewerColumnInfo labelColumnInfo = new ResultViewerColumnInfo("rdfs:label", RDFS.LABEL);
		SetColumnVisibleAction showLabelAction = new SetColumnVisibleAction(labelColumnInfo, this);
		contextMenuActions.put(labelColumnInfo.getColumnType(), showLabelAction);
		menuManager.add(showLabelAction);
		//ResultViewerColumnInfo typeColumnInfo = new ResultViewerColumnInfo("rdf:type", RDF.TYPE);
		//SetColumnVisibleAction showTypeAction = new SetColumnVisibleAction(typeColumnInfo, this);
		//contextMenuActions.put(typeColumnInfo.getColumnType(), showTypeAction);
		//menuManager.add(showTypeAction);
		
		//--Peer column
		ResultViewerColumnInfo peerColumnInfo = new ResultViewerColumnInfo("oyster:peer", Constants.POMVURI+Constants.ontologyOMVLocation);
		SetColumnVisibleAction showPeerAction = new SetColumnVisibleAction(peerColumnInfo, this);
		contextMenuActions.put(peerColumnInfo.getColumnType(), showPeerAction);
		menuManager.add(showPeerAction);
		menuManager.add(new Separator());
		
		
		Ontology resourceTypeOntology = mOyster2.getTypeOntology();
		OWLClass ontologyClass = KAON2Manager.factory().owlClass(Constants.OMV+Constants.DefaultTypeOntologyRoot);
		try{
			/*KAON2 BUG, DOES NOT SUPPORT OWL DL
	         * SHOULD BE DELETED WHEN IT DOES
	         */
			
			ResultViewerColumnInfo nameColumnInfo = new ResultViewerColumnInfo(MainWindow.whichName(Constants.omvCondition+Constants.name), Constants.OMVURI+Constants.name);
			SetColumnVisibleAction showNameAction = new SetColumnVisibleAction(nameColumnInfo, this);
			contextMenuActions.put(nameColumnInfo.getColumnType(), showNameAction);
			menuManager.add(showNameAction);
			ResultViewerColumnInfo acronymColumnInfo = new ResultViewerColumnInfo(MainWindow.whichName(Constants.omvCondition+Constants.acronym), Constants.OMVURI+Constants.acronym);
			SetColumnVisibleAction showAcronymAction = new SetColumnVisibleAction(acronymColumnInfo, this);
			contextMenuActions.put(acronymColumnInfo.getColumnType(), showAcronymAction);
			menuManager.add(showAcronymAction);
			ResultViewerColumnInfo descriptionColumnInfo = new ResultViewerColumnInfo(MainWindow.whichName(Constants.omvCondition+Constants.description), Constants.OMVURI+Constants.description);
			SetColumnVisibleAction showDescriptionAction = new SetColumnVisibleAction(descriptionColumnInfo, this);
			contextMenuActions.put(descriptionColumnInfo.getColumnType(), showDescriptionAction);
			menuManager.add(showDescriptionAction);
			ResultViewerColumnInfo documentationColumnInfo = new ResultViewerColumnInfo(MainWindow.whichName(Constants.omvCondition+Constants.documentation), Constants.OMVURI+Constants.documentation);
			SetColumnVisibleAction showDocumentationAction = new SetColumnVisibleAction(documentationColumnInfo, this);
			contextMenuActions.put(documentationColumnInfo.getColumnType(), showDocumentationAction);
			menuManager.add(showDocumentationAction);
			ResultViewerColumnInfo uriColumnInfo = new ResultViewerColumnInfo(MainWindow.whichName(Constants.omvCondition+Constants.URI), Constants.OMVURI+Constants.URI);
			SetColumnVisibleAction showURIAction = new SetColumnVisibleAction(uriColumnInfo, this);
			contextMenuActions.put(uriColumnInfo.getColumnType(), showURIAction);
			menuManager.add(showURIAction);
			
			/* UNTIL HERE */
			
			Set<DataProperty> dataProperties=ontologyClass.getDataPropertiesFrom(resourceTypeOntology);
			for (DataProperty dataProperty : dataProperties){
				SetColumnVisibleAction action = new SetColumnVisibleAction(new ResultViewerColumnInfo(MainWindow.whichName(Constants.omvCondition+Namespaces.guessLocalName(dataProperty.getURI())), dataProperty.getURI()), this);
				contextMenuActions.put(dataProperty.getURI(), action);
				menuManager.add(action);
			}
	        Set<ObjectProperty> objectProperties=ontologyClass.getObjectPropertiesFrom(resourceTypeOntology);
	        for (ObjectProperty objectProperty : objectProperties){
	        	SetColumnVisibleAction action = new SetColumnVisibleAction(new ResultViewerColumnInfo(MainWindow.whichName(Constants.omvCondition+Namespaces.guessLocalName(objectProperty.getURI())), objectProperty.getURI()), this);
				contextMenuActions.put(objectProperty.getURI(), action);
				menuManager.add(action);
	        }
	        
		}
	    catch (KAON2Exception e) {
	    	System.err.println(e + " in contextinit()");
	    }
		
	}
	
	
	/**
	 * Initialize columns of this component.
	 */
	private void initColumns() throws Exception {
		
		Tree tree = wrapedViewer.getTree();
		tree.setHeaderVisible(true);
		
		//PreferenceStore prefStore = mOyster2.getPreferenceStore();
		Properties mprop = mOyster2.getProperties();
		//IPreferenceStore prefStore = JFacePreferences.getPreferenceStore();
		int count = mprop.getInteger(Constants.NUMBER_OF_COLUMNS);
		for(int i=0; i<count; i++){
			String columnType = mprop.getString(Constants.COLUMN_TYPE+i);
			String columnName = mprop.getString(Constants.COLUMN_NAME+i);
			int columnWidth = mprop.getInteger(Constants.COLUMN_WIDTH+i);
			addColumn(new ResultViewerColumnInfo(columnName, columnType, columnWidth));
		}
		
	}
	
	public synchronized void createTreeContent(QueryReply queryReply){
		
		Ontology ontology = queryReply.getOntology();
		Collection entrySet = queryReply.getResourceSet();
		Tree tree = wrapedViewer.getTree();
		String[] columnContent;
//		OWLClass omvDoc = KAON2Manager.factory().owlClass(OMV+"OntologyDocument");
		try{
		
		//System.out.println("importset size: "+entrySet.size());
		Iterator it = entrySet.iterator();
		while(it.hasNext()){
			String locationStr="";
			Individual docIndiv = (Individual)it.next();
			//Individual docIndiv = KAON2Manager.factory().individual(importOnto.getURI());
			String  entryURI = docIndiv.getURI();
			//System.out.println("reply:"+entryURI);
			//entries.add(entryURI);
			ObjectProperty subject = KAON2Manager.factory().objectProperty(OMV+Constants.hasDomain);
			ObjectProperty location = KAON2Manager.factory().objectProperty(mOyster2.getPeerDescOntologyURI()+"#"+Constants.ontologyOMVLocation);
			DataProperty URL = KAON2Manager.factory().dataProperty(OMV+ Constants.URI);
			String baseSubject = docIndiv.getObjectPropertyValue(ontology,subject).getURI();
			if(docIndiv.getObjectPropertyValue(ontology,location)!=null)
				locationStr = docIndiv.getObjectPropertyValue(ontology,location).getURI();
			String URLStr = org.neon_toolkit.registry.util.Utilities.getString(docIndiv.getDataPropertyValue(ontology,URL));
			columnContent = new String[]{entryURI,baseSubject,locationStr,URLStr};
			TreeItem item = new TreeItem(tree,SWT.NONE);
			item.setText(columnContent);
		}
		}catch(KAON2Exception e){
			System.out.println(e.toString()+"createTreeContent");
		}
		
	}
	
	public String getColumnType(int index){
		//return ((TreeColumn)columns.get(index)).getText();
		return ((ResultViewerColumnInfo)columns.get(index)).getColumnType();
	}
	
	/**
	 * Adds the listener to the wraped component's collection of listeners 
	 * who will be notified when the receiver's selection changes, by sending
	 * it one of the messages defined in the <code>SelectionListener</code>
	 * interface.
	 * 
	 * @param listener the instance of listener which should be notified.
	 */
	public void addSelectionListener(ISelectionChangedListener listener){
		wrapedViewer.addSelectionChangedListener(listener);
	}
	
	/**
	 * Returns the wraped viewer associated with this component.
	 * 
	 * @return the JFace viewer which displays this component's content.
	 */
	public TreeViewer getWrapedViewer(){
		return wrapedViewer;
	}
	
	/**
	 * @return
	 */
	public IAction[] getContextMenuActions(){
		return null;
	}
	
	/**
	 * Adds new column with given info.
	 * 
	 * @param columnInfo - column info.
	 */
	public void addColumn(ResultViewerColumnInfo columnInfo){
		
		Tree tree = wrapedViewer.getTree();
		TreeColumn treeColumn = new TreeColumn(tree, SWT.LEFT);
		treeColumn.setText(columnInfo.getColumnName());
		treeColumn.setWidth(columnInfo.getWidth());
		treeColumn.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				TreeColumn[] columns = wrapedViewer.getTree().getColumns();
				for(int i=0; i<columns.length; i++){
					if(columns[i].equals(e.widget)){
						ResultViewerColumnInfo colInf = (ResultViewerColumnInfo)ResultViewer.this.columns.get(i);
						int previousCol = sorter.getSortedColumnIndex();
						if(previousCol == i){
							sorter.setSortedColumn(-1, null);
							columns[i].setText(colInf.getColumnName());
						}else {
							sorter.setSortedColumn(i, colInf.getColumnType());
							columns[i].setText(colInf.getColumnName() + "  *");
							if(previousCol >= 0){
								ResultViewerColumnInfo previousColInf = (ResultViewerColumnInfo)ResultViewer.this.columns.get(previousCol);
								columns[previousCol].setText(previousColInf.getColumnName());
							}
							wrapedViewer.refresh();
						}
						break;
					}
				}
				
			}
		});
		treeColumn.addControlListener(columnInfo);
		columns.add(columnInfo);
		SetColumnVisibleAction action = (SetColumnVisibleAction)contextMenuActions.get(columnInfo.getColumnType());
		if(action != null)
			action.setChecked(true);
		if (tree.isVisible()) {
			wrapedViewer.refresh();
		}
	}
	
		
	/**
	 * @return the number of columns.
	 */
	public int getColumnCount(){
		return wrapedViewer.getTree().getColumnCount();
	}
	
	/**
	 * Returns the column at the given, zero-relative index 
	 * in the receiver.
	 * 
	 * @param index - zero-relative index of column.
	 * @return
	 */
	public TreeColumn getColumn(int index) throws IndexOutOfBoundsException {
		return wrapedViewer.getTree().getColumn(index);
	}
	
	/**
	 * Returns the column info at the given, zero-relative index 
	 * in the receiver.
	 * 
	 * @param index - zero-relative index of column.
	 * @return
	 */
	public ResultViewerColumnInfo getColumnInfo(int index) throws IndexOutOfBoundsException {
		return (ResultViewerColumnInfo)columns.get(index);
	}
	
	/**
	 * Notified on component dispose event.
	 */
	private void onDispose(Event e) {
		storComponentProperties();
	}
	
	public void clear(){
		wrapedViewer.refresh();
		wrapedViewer.getTree().removeAll();
	}
	
	/**
	 * Removes the column and its info.
	 * 
	 * @param index - zero-relative index of column.
	 */
	public void removeColumn(int columnIndex) throws IndexOutOfBoundsException {
		ResultViewerColumnInfo columnInfo = null;
		SetColumnVisibleAction action = null;
		//--Remove column info
		if(columnIndex <= columns.size())
			columnInfo = (ResultViewerColumnInfo)columns.remove(columnIndex);
		//--Set context menu action unchecked
		if(columnInfo != null){
			action = (SetColumnVisibleAction)contextMenuActions.get(columnInfo.getColumnType());
			if(action != null){
				action.setChecked(false);
			}
		}
		//--Disable sorting when sorted column was removed
		if(sorter != null && sorter.isSorterProperty(null, columnInfo.getColumnType()))
			sorter.setSortedColumn(-1, null);
		//--Change columns of underlying table
		//Table table = wrapedViewer.getTree().getTable();
		
		Tree table = wrapedViewer.getTree();
		if(table.getColumnCount() > 0){
			//--Dispose last column
			TreeColumn tableColumn = table.getColumn(table.getColumnCount()-1);
			tableColumn.dispose();
			//--Set columns' info and change sorting
			for(int i=0; i<columns.size(); i++){
				ResultViewerColumnInfo colInf = (ResultViewerColumnInfo)columns.get(i);
				if(sorter.isSorterProperty(null, colInf.getColumnType())){
					sorter.setSortedColumn(i, colInf.getColumnType());
					table.getColumn(i).setText(colInf.getColumnName() + "  *");
				}else{
					table.getColumn(i).setText(colInf.getColumnName());
				}
				table.getColumn(i).setWidth(colInf.getWidth());
			}
		}
		wrapedViewer.refresh();
	}
	
	/**
	 * Storing this component properties.
	 */
	private void storComponentProperties(){
		//IPreferenceStore prefStore = JFacePreferences.getPreferenceStore();
		//PreferenceStore prefStore = mOyster2.getPreferenceStore();
		Properties mprop = mOyster2.getProperties();
		int count = mprop.getInteger(Constants.NUMBER_OF_COLUMNS);
		for(int i=0; i<count; i++){
			mprop.setString(Constants.COLUMN_TYPE+i, mprop.getDefaultString(Constants.COLUMN_TYPE+i));
			mprop.setString(Constants.COLUMN_NAME+i, mprop.getDefaultString(Constants.COLUMN_NAME+i));
			mprop.setString(Constants.COLUMN_WIDTH+i, mprop.getDefaultString(Constants.COLUMN_WIDTH+i));
			/*
			prefStore.setToDefault(Constants.COLUMN_TYPE+i);
			prefStore.setToDefault(Constants.COLUMN_NAME+i);
			prefStore.setToDefault(Constants.COLUMN_WIDTH+i);
			*/
		}
		mprop.setString(Constants.NUMBER_OF_COLUMNS, ""+columns.size());
		for(int i=0; i<columns.size(); i++){
			ResultViewerColumnInfo colInf = getColumnInfo(i);
			mprop.setString(Constants.COLUMN_NAME + i, colInf.getColumnName());
			mprop.setString(Constants.COLUMN_TYPE + i, colInf.getColumnType());
			mprop.setString(Constants.COLUMN_WIDTH + i, ""+colInf.getWidth());
		}
		try{
			mprop.storeOn();
			//prefStore.save();
		}
		catch(Exception IO){
			System.out.println("couldnt save properties");
		}
		
	}
}
