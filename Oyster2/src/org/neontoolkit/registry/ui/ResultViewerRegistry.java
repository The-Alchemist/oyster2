package org.neontoolkit.registry.ui;
/*
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.neon_toolkit.registry.util.Utilities;
*/
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.neontoolkit.registry.oyster2.Constants;
import org.neontoolkit.registry.oyster2.Oyster2Factory;
import org.neontoolkit.registry.oyster2.QueryReply;
//import org.neon_toolkit.registry.ui.provider.ResultViewerContentProvider;
//import org.neon_toolkit.registry.ui.provider.ResultViewerLabelProvider;
import org.semanticweb.kaon2.api.*;
import org.semanticweb.kaon2.api.owl.elements.*;


public class ResultViewerRegistry extends Composite {
	
	private static String OMV = "http://omv.ontoware.org/2005/05/ontology#";
	private TreeViewer wrapedViewer;
	//private ResultViewerContentProvider contentProvider;
	//private ResultViewerLabelProvider labelProvider;
	private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();

	private List columns = new LinkedList();
	
	public ResultViewerRegistry(Composite parent, int style) throws Exception {
		
		super(parent, SWT.NONE);
		this.setLayout(new FillLayout());
		Tree tree = new Tree(this, style);
		wrapedViewer = new TreeViewer(tree);
		wrapedViewer.expandToLevel(2);
		initColumns();
		Listener listener = new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
					case SWT.Dispose: onDispose(e); break;
				}
			}
		};
		addListener(SWT.Dispose, listener);
		//initContextMenu();
		
	}
	
	
	/**
	 * Initialize columns of this component.
	 */
	private void initColumns() throws Exception {
		Tree tree = wrapedViewer.getTree();
		tree.setHeaderVisible(true);
		
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
			String URLStr = org.neontoolkit.registry.util.Utilities.getString(docIndiv.getDataPropertyValue(ontology,URL));
			columnContent = new String[]{entryURI,baseSubject,locationStr,URLStr};
			TreeItem item = new TreeItem(tree,SWT.NONE);
			item.setText(columnContent);

		}
		}catch(KAON2Exception e){
			System.out.println(e.toString()+"createTreeContent");
		}
		
	}
	
	public String getColumnType(int index){
		return ((TreeColumn)columns.get(index)).getText();
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
						//ResultViewerColumnInfo colInf = (ResultViewerColumnInfo)ResultViewerRegistry.this.columns.get(i);
						break;
					}
				}
			}
		});
		treeColumn.addControlListener(columnInfo);
		columns.add(columnInfo);
		/*TODO SetColumnVisibleAction action = (SetColumnVisibleAction)contextMenuActions.get(columnInfo.getColumnType());
		if(action != null)
			action.setChecked(true);*/
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
	 * Storing this component properties.
	 */
	private void storComponentProperties(){
		/*IPreferenceStore prefStore = JFacePreferences.getPreferenceStore();
		prefStore.setValue(NUMBER_OF_COLUMNS, columns.size());
		for(int i=0; i<columns.size(); i++){
			ResultViewerColumnInfo colInf = getColumnInfo(i);
			prefStore.setValue(COLUMN_NAME + i, colInf.getColumnName());
			prefStore.setValue(COLUMN_TYPE + i, colInf.getColumnType());
			prefStore.setValue(COLUMN_WIDTH + i, colInf.getWidth());
		}*/
	}
}
