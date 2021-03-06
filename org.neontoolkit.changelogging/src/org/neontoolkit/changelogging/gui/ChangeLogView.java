package org.neontoolkit.changelogging.gui;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;
import org.neontoolkit.changelogging.core.flogic.ChangeLogEntry;
import org.neontoolkit.changelogging.core.flogic.FlogicChangeListener;
import org.neontoolkit.changelogging.core.owl.OWLChangeListener;
import org.neontoolkit.changelogging.gui.actions.Track;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.OntologyChangeEvent.ChangeType;
import org.neontoolkit.registry.api.Oyster2Connection;
import org.neontoolkit.registry.api.Oyster2Manager;
import org.neontoolkit.omv.api.core.OMVPerson;
import org.neontoolkit.omv.api.extensions.change.OMVChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange.Addition;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;


public class ChangeLogView extends ViewPart {
	static StyledText serialization;
	private Group loginGroup;
	private Group treeGroup;
	private TreeViewer treeViewer;
	private TableViewer tableViewer;
	private Table table;
	private Tree tree;
	static HashSet<TableItem> selectedItems = new HashSet<TableItem>();
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	static Shell shell=null;
	static Oyster2Connection oyster2Conn = StartRegistry.connection;//null;
	
	public ChangeLogView() {
		
	}
	
	@Override
	public void createPartControl(Composite parent) {
		shell=this.getSite().getShell();
		int style = SWT.MULTI | SWT.BORDER; //SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER;
		
		final ScrolledComposite scrolledComposite = new ScrolledComposite(parent, style | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		scrolledComposite.setRedraw(true);
		final Composite composite = new Composite(scrolledComposite, style );
		composite.setRedraw(true);
		composite.setLocation(0, 0);
		final GridLayout gridLayout = new GridLayout(); //new GridLayout(2,false);
		gridLayout.numColumns = 2;
		composite.setLayout(gridLayout);
	

		GridLayout groupLayoutTree = new GridLayout();	
		treeGroup = new Group(composite, SWT.NONE);
		treeGroup.setLayout(groupLayoutTree);
		
		final Button button = new Button(treeGroup, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				treeViewer.setInput(new Object());
				tableViewer.setInput(new Object());
				serialization.setText("");
				selectedItems.clear();
			}
		});
		final GridData gd_button = new GridData(SWT.FILL, SWT.CENTER, false, false);
		gd_button.widthHint = 80;
		button.setLayoutData(gd_button);
		button.setText("Refresh");
		new Label(treeGroup, SWT.NONE);

		treeViewer = new TreeViewer(treeGroup, style);
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection sSelection = (StructuredSelection)treeViewer.getSelection();
				Object selection = sSelection.getFirstElement();
				if(selection instanceof Ontology)
					tableViewer.setInput(selection);
					serialization.setText("");
					selectedItems.clear();
			}
		});
		treeViewer.setContentProvider(new TreeContentProvider());
		treeViewer.setLabelProvider(new TreeLabelProvider());
		tree = treeViewer.getTree();
		final GridData gd_tree = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd_tree.widthHint = 250;
		gd_tree.heightHint = 560;
		tree.setLayoutData(gd_tree);

		GridLayout groupLayout = new GridLayout();
		loginGroup = new Group(composite, SWT.NONE);
		loginGroup.setLayout(groupLayout);
		
		tableViewer = new TableViewer(loginGroup,  SWT.CHECK | SWT.V_SCROLL | SWT.BORDER);
		//tableViewer.setSorter(new Sorter());
		tableViewer.setLabelProvider(new TableLabelProvider());
		tableViewer.setContentProvider(new ContentProvider());
		tableViewer.setInput(new Object());
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		/*
		 * NOTE: MeasureItem, PaintItem and EraseItem are called repeatedly.
		 * Therefore, it is critical for performance that these methods be
		 * as efficient as possible.
		 */
		final int TEXT_MARGIN = 1;

		table.addListener(SWT.MeasureItem, new Listener() {
			public void handleEvent(Event event) {
				TableItem item = (TableItem)event.item;
				String text = item.getText(event.index);
				Point size = event.gc.textExtent(text);
				event.width = size.x + 2 * TEXT_MARGIN;
				event.height = Math.max(event.height, size.y + TEXT_MARGIN);
			}
		});
		table.addListener(SWT.EraseItem, new Listener() {
			public void handleEvent(Event event) {
				event.detail &= ~SWT.FOREGROUND;
			}
		});
		table.addListener(SWT.PaintItem, new Listener() {
			public void handleEvent(Event event) {
				TableItem item = (TableItem)event.item;
				String text = item.getText(event.index);
				/* center column 1 vertically */
				int yOffset = 0;
				if (event.index == 1) {
					Point size = event.gc.textExtent(text);
					yOffset = Math.max(0, (event.height - size.y) / 2);
				}
				event.gc.drawText(text, event.x + TEXT_MARGIN, event.y + yOffset, true);
			}
		});
		table.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {		        
		        TableItem item = (TableItem)event.item;
		        if(item.getChecked()){
		        	selectedItems.add(item);
		        } else {
		        	selectedItems.remove(item);
		        }
		        serialization.setText("");
		        if (selectedItems.size()>0)
		        	executor.execute(new RefreshSerializationThread(shell));
		      }
		    });
		
		final GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd_table.widthHint = 550;
		gd_table.heightHint= 350;
		gd_table.grabExcessHorizontalSpace = true;
		gd_table.grabExcessVerticalSpace = true;
		gd_table.verticalAlignment=SWT.TOP;
		table.setLayoutData(gd_table);
		//table.setBounds(150, 0, 550, 350);
		
		
		TableColumn tColumn = new TableColumn(table, SWT.NONE);
		tColumn.setText("Time");
		tColumn.setWidth(150);
		TableColumn tColumn1 = new TableColumn(table, SWT.NONE);
		tColumn1.setText("Type");
		tColumn1.setWidth(100);
		TableColumn tColumn2 = new TableColumn(table, SWT.NONE);
		tColumn2.setText("Change");
		tColumn2.setWidth(150);
		TableColumn tColumn3 = new TableColumn(table, SWT.MULTI);
		tColumn3.setText("Author");
		tColumn3.setWidth(150);
		TableColumn tColumn4 = new TableColumn(table, SWT.MULTI);
		tColumn4.setText("changeURI");
		tColumn4.setWidth(0);
		
		GridData gd = new GridData();
		gd.widthHint = 550;
		gd.heightHint = 250;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		serialization = new StyledText(loginGroup, SWT.CHECK | SWT.V_SCROLL | SWT.BORDER);
		serialization.setLayoutData(gd);
		
		composite.setSize(890, 660);
		scrolledComposite.setContent(composite);
		initializeToolBar();
	}
		
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	
	private void initializeToolBar() {
		@SuppressWarnings("unused")
		IToolBarManager toolBarManager = getViewSite().getActionBars().getToolBarManager();
	}
	
	private static String getChangeConcept(OMVChange a){
		if (a.getClass().getSimpleName()!=null && a.getClass().getSimpleName()!="") return a.getClass().getSimpleName();
		else return ""; 
	}
	
	public void openMessage(final String mess){
		shell.getDisplay().asyncExec(new Runnable() {
	           public void run() {
	        	   MessageDialog.openInformation(
           				shell,
           				"Workflow Plug-in",
           				mess);
	            }
		});
	}
	
	public void addSerialization (final String text){
		shell.getDisplay().asyncExec(new Runnable() {
			public void run() {
				serialization.setText(text);
			}
		});
	}
	
	public boolean goContinue(){
		if(oyster2Conn==null)
			oyster2Conn = StartRegistry.connection;
		if(oyster2Conn==null || !StartRegistry.getState())
			return false;
		return true;
	}

	static final Comparator<OMVChange> TIME_ORDER =
        new Comparator<OMVChange>() {
		public int compare(OMVChange e1, OMVChange e2) {
			try {
				Date d1=DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).parse(e1.getDate());
				Date d2=DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).parse(e2.getDate());
				return d1.compareTo(d2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		}
	};
	
	public class RefreshSerializationThread implements Runnable {
		List<String> changeURIs= new LinkedList<String>();
		Shell shell;
		public RefreshSerializationThread(Shell s){
			shell=s;
		}
		public void run() {
			shell.getDisplay().asyncExec(new Runnable() {
		           public void run() {
		        	   if (!goContinue()) {
		        		   System.out.println("cannot continue connection=null");
		        		   return;
		        	   }
		        	   for(TableItem item : selectedItems)
		       				changeURIs.add(item.getText(4));
		        	   Job exportJob = new Job("Getting all change information...") {
			   	        	protected IStatus run(IProgressMonitor monitor) {
			   	        		monitor.beginTask("Getting all change information...", 100);
			   	        		try {
			   	        			List<OMVChange> at=new LinkedList<OMVChange>();
			   	        			for (String entityChange : changeURIs){
			   	        				OMVChange c=oyster2Conn.getChange(entityChange);
			   	        				at.add(c);
			   	        			}
			   	        			Collections.sort(at, TIME_ORDER);
			   	        			String text = Oyster2Manager.serializeOMVChanges(at);
			   						text.trim();
			   						if (text.length()>2){
			   							text=text.substring(0, text.length()-2);
			   							addSerialization(text);
			   						}else addSerialization("");
			   	        		}catch (Exception e) {
			   	            		openMessage("Serialization failed");
			   	            		e.printStackTrace();
			   	            	}	
			   	            	finally{
			   	            		monitor.done();
			   	            	}
			   	            	return Status.OK_STATUS;
			   	         	}
			   			};
			   	       exportJob.setUser(true);
			   	       exportJob.schedule();	        		
		           }
			});
		}
	}
		
	class Sorter extends ViewerSorter {
		public int compare(Viewer viewer, Object e1, Object e2) {
			try {
				if(e1 instanceof ChangeLogEntry && e2 instanceof ChangeLogEntry){
					String timeStamp1 = ((ChangeLogEntry)e1).getTimeStamp();
					String timeStamp2 = ((ChangeLogEntry)e2).getTimeStamp();					
					Date date1 = DateFormat.getInstance().parse(timeStamp1);
					Date date2 = DateFormat.getInstance().parse(timeStamp2);
					if(date1.before(date2))
						return -1;
					else if(date1.after(date2))
						return 1;
					
				}else if(e1 instanceof OMVChange && e2 instanceof OMVChange){
					//System.out.println(((OMVChange)e1).getURI()+ "is compared against: "+((OMVChange)e2).getURI());
					String uriPrev1=((OMVChange)e1).getHasPreviousChange();
					String uriPrev2=((OMVChange)e2).getHasPreviousChange();
					if (uriPrev1 ==null) return -1;
					else if (uriPrev2==null) return 1;
					else if (uriPrev1.equalsIgnoreCase(((OMVChange)e2).getURI())) return 1;
					else if (uriPrev2.equalsIgnoreCase(((OMVChange)e1).getURI())) return -1;
					
					String timeStamp1 = ((OMVChange)e1).getDate();
					String timeStamp2 = ((OMVChange)e2).getDate();
					Date date1 = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).parse(timeStamp1);
					Date date2 = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).parse(timeStamp2);
					if(date1.before(date2))
						return -1;
					else if(date1.after(date2))
						return 1;
				}
			} catch (ParseException e) {				
				e.printStackTrace();
				return 0;
			}
			return 0;
		}
	}
	
	class TableLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object element, int columnIndex) {
			if(element instanceof ChangeLogEntry){
				ChangeLogEntry entry = (ChangeLogEntry)element;
				if(columnIndex == 0){
					return entry.getTimeStamp();
				}else if(columnIndex == 1){
					return entry.getType().toString();
				}else if(columnIndex == 2){
					StringBuffer desc = new StringBuffer();
					desc.append(entry.getPredicate());
					desc.append("----");
					for(int i=0; i<entry.getArguments().length; i++){
						desc.append(entry.getArguments()[i]);
						desc.append("----");
					}
					
					return desc.substring(0, desc.lastIndexOf("----"));
				}
			}else if(element instanceof OMVChange){				
				OMVChange change = (OMVChange)element;
				if(columnIndex == 0){ //TIME
					return change.getDate();
				}else if(columnIndex == 1){ //TYPE
					if (change instanceof OMVAtomicChange)	return "AtomicChange";
					else return "EntityChange";
				}else if(columnIndex == 2){ //CHANGE
					if (change instanceof OMVAtomicChange){
						if(change instanceof Addition)
							return ChangeType.ADD.toString();
						else
							return ChangeType.REMOVE.toString();
					}
					else return getChangeConcept(change);
				}else if(columnIndex == 3){ //AUTHOR
					String persons = "";
					for(OMVPerson person : change.getHasAuthor()){
						persons += person.getFirstName()+" "+person.getLastName()+", ";
					}
					if(persons.length()>1)
							persons = persons.substring(0, persons.length()-2);
					return persons;
				}else if(columnIndex == 4){//URI 
					return change.getURI();
				}
			}
			
			return element.toString();
		}
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
	}
	
	class ContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			
			if(Track.FlogicList.keySet().contains(inputElement)){
				Ontology onto = (Ontology)inputElement;
				int index = 0;
				List<FlogicChangeListener> listners = new ArrayList<FlogicChangeListener>();
				listners.addAll(Track.FlogicList.values());
				for(;index<listners.size(); index++){
					if(onto == listners.get(index).getMonitoredOntology())
						break;
				}
				if(index<listners.size()){
					FlogicChangeListener listener = listners.get(index);
					List<ChangeLogEntry> list = new ArrayList<ChangeLogEntry>();
					list.addAll(listener.getChangeLogger().getLogEntries());
					list.addAll(listener.getChangeLogger().getOldLogEntries());
					return list.toArray(new ChangeLogEntry[]{});
				}
			}else if(Track.OWLList.keySet().contains(inputElement)){
				Ontology onto = (Ontology)inputElement;
				int index = 0;
				List<OWLChangeListener> listners = new ArrayList<OWLChangeListener>();
				listners.addAll(Track.OWLList.values());
				for(;index<Track.OWLList.size(); index++){
					if(onto == listners.get(index).getMonitoredOntology())
						break;
				}
				if(index<listners.size()){
					OWLChangeListener listener = listners.get(index);
					return listener.getChanges().toArray(new OMVChange[]{});
				}
			}
			return new Object[]{};
		}
		public void dispose() {
		}
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}
	
	class TreeContentProvider implements IStructuredContentProvider, ITreeContentProvider {
		private String flogicList = "Flogic Ontologies";
		private String owlList = "OWL Ontologies";
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object inputElement) {
			return new String[]{flogicList, owlList};
		}
		public Object[] getChildren(Object parentElement) {
			List<Ontology> ontoList = new ArrayList<Ontology>();
			if(parentElement.equals(flogicList)){
				ontoList.addAll(Track.FlogicList.keySet());
			}else if(parentElement.equals(owlList)){
				ontoList.addAll(Track.OWLList.keySet());				
			}
			return ontoList.toArray(new Ontology[]{});
		}
		public Object getParent(Object element) {
			return null;
		}
		public boolean hasChildren(Object element) {
			if(element.equals(flogicList) && Track.FlogicList.size()>0)
				return true;
			else if(element.equals(owlList) && Track.OWLList.size()>0)
				return true;
			else
				return false;
		}
	}
	
	class TreeLabelProvider extends LabelProvider {
		public String getText(Object element) {
			if(element instanceof String)
				return (String)element;
			else if(element instanceof Ontology)
				return ((Ontology)element).getOntologyURI();
			else
				return "";
		}
		public Image getImage(Object element) {
			return null;
		}
	}

}

//String timeStamp1 = ((OMVChange)e1).getDate();
//String timeStamp2 = ((OMVChange)e2).getDate();
//Date date1 = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).parse(timeStamp1);
//Date date2 = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).parse(timeStamp2);
//if(date1.before(date2))
//	return -1;
//else if(date1.after(date2))
//	return 1;


/*
List<OMVChange> list = new LinkedList<OMVChange>();
list.add(change);
String text = Oyster2Manager.serializeOMVChanges(list);
text.trim();
text=text.substring(0, text.length()-2);					
return text;
*/