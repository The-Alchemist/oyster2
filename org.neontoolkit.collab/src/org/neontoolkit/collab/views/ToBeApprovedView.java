package org.neontoolkit.collab.views;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.ViewPart;
import org.neontoolkit.collab.core.OysterTools;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.core.OMVPerson;
import org.neontoolkit.omv.api.extensions.change.OMVChange;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;
import org.neontoolkit.registry.api.Oyster2Connection;
import org.neontoolkit.registry.oyster2.Constants;
import org.neontoolkit.workflow.api.Action;
import org.semanticweb.kaon2.api.Namespaces;

import com.ontoprise.ontostudio.gui.GuiPlugin;

public class ToBeApprovedView extends ViewPart implements SelectionListener {

	public static final String ID = "org.neontoolkit.collab.ToBeApprovedView"; 
	
	static Oyster2Connection oyster2Conn = StartRegistry.connection;//null;
	private Composite composite = null;
	static Table resTable ;
	private static Button refreshButton, rejectProposalButton, acceptProposalButton;
	static IPreferenceStore _store;
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	static Shell shell=null;
	static Color grayColor=null;
	
	int CHANGE_URI = 1;
	int AUTHOR_COLUMN = 4;
	int STATUS_COLUMN = 6;
	
	static HashSet<TableItem> selectedItems = new HashSet<TableItem>();
	
	public ToBeApprovedView() {
		_store = GuiPlugin.getDefault().getPreferenceStore();		
	}

	@Override
	public void createPartControl(Composite parent) {
		
		GridData gd = null;
		int columns = 2;	
		int width = 800;
		int columnWidth = 100;
		
		composite = new Composite(parent, SWT.BORDER );
		composite.setLayout(new GridLayout(columns, false));
		composite.setSize(width, 550);
					
		gd = new GridData();
		gd.horizontalSpan = columns;
		refreshButton = new Button(composite, SWT.PUSH);
		refreshButton.setLayoutData(gd);
		refreshButton.setText("  Refresh Changes List  ");
		refreshButton.addSelectionListener(this);

		
		gd = new GridData();
		gd.horizontalSpan = columns;
		gd.widthHint = width;
		gd.heightHint = 400;
		gd.grabExcessHorizontalSpace = true;			
		resTable = new Table(composite, SWT.CHECK | SWT.V_SCROLL | SWT.BORDER);
		resTable.setLayoutData(gd);
		resTable.setHeaderVisible(true);
		resTable.setLinesVisible(true);
		resTable.setBounds(0, 0, width, 140);
		resTable.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {		        
		        TableItem item = (TableItem)event.item;
		        if(item.getChecked()){
		        	System.out.println(item.getText(4) + " (checked) ");
		        	selectedItems.add(item);
		        } else {
		        	System.out.println(item.getText(4) + " (nonchecked) ");
		        	selectedItems.remove(item);
		        }			        
		      }
		    });
		//first column
		TableColumn uSetNrColumn = new TableColumn(resTable, SWT.LEFT);
		uSetNrColumn.setText("Ontology"); //$NON-NLS-1$
		uSetNrColumn.setWidth(columnWidth-25);
		uSetNrColumn.setAlignment(SWT.LEFT);
		
		uSetNrColumn = new TableColumn(resTable, SWT.LEFT);
		uSetNrColumn.setText("Change URI"); //$NON-NLS-1$
		uSetNrColumn.setWidth(0);
		uSetNrColumn.setAlignment(SWT.LEFT);
				
		uSetNrColumn = new TableColumn(resTable, SWT.LEFT);
		uSetNrColumn.setText("Change Type"); //$NON-NLS-1$
		uSetNrColumn.setWidth(columnWidth+50);
		uSetNrColumn.setAlignment(SWT.LEFT);
		
		uSetNrColumn = new TableColumn(resTable, SWT.LEFT);
		uSetNrColumn.setText("Related Entity"); //$NON-NLS-1$
		uSetNrColumn.setWidth(columnWidth+50);
		uSetNrColumn.setAlignment(SWT.LEFT);
		
		uSetNrColumn = new TableColumn(resTable, SWT.LEFT);
		uSetNrColumn.setText("Author"); //$NON-NLS-1$
		uSetNrColumn.setWidth(columnWidth);
		uSetNrColumn.setAlignment(SWT.LEFT);
		
		uSetNrColumn = new TableColumn(resTable, SWT.LEFT);
		uSetNrColumn.setText("Time"); //$NON-NLS-1$
		uSetNrColumn.setWidth(columnWidth+20);
		uSetNrColumn.setAlignment(SWT.LEFT);
		
		uSetNrColumn = new TableColumn(resTable, SWT.LEFT);
		uSetNrColumn.setText("Status"); //$NON-NLS-1$
		uSetNrColumn.setWidth(columnWidth-35);
		uSetNrColumn.setAlignment(SWT.LEFT);
		
		uSetNrColumn = new TableColumn(resTable, SWT.LEFT);
		uSetNrColumn.setText("Last Action"); //$NON-NLS-1$
		uSetNrColumn.setWidth(columnWidth+55);
		uSetNrColumn.setAlignment(SWT.LEFT);
		
		acceptProposalButton = new Button(composite, SWT.PUSH);
		acceptProposalButton.setText("Approve Changes");
		acceptProposalButton.addSelectionListener(this);
		rejectProposalButton = new Button(composite, SWT.PUSH);
		rejectProposalButton.setText("Reject Changes back to Draft");
		rejectProposalButton.addSelectionListener(this);
	
		shell=this.getSite().getShell();
		grayColor=this.getSite().getShell().getDisplay().getSystemColor(SWT.COLOR_GRAY);

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void widgetSelected(SelectionEvent e) {
		
		if(e.getSource().equals(refreshButton)){
			shell.getDisplay().asyncExec(new Runnable () {
				public void run () {
					if(oyster2Conn==null)
						oyster2Conn = StartRegistry.connection;
					
					if(oyster2Conn==null || !StartRegistry.getState())
						return;
							
					resTable.removeAll();
					selectedItems.clear();
					OMVPerson currentPerson = OysterTools.getCurrentUser(_store);
					List<OMVChange> changes = new ArrayList<OMVChange>();
					for(OMVOntology onto : oyster2Conn.getOntologiesWithChanges()){
						changes.clear();
						//get the changes to be approved
						changes.addAll(oyster2Conn.getChangesWithState(onto, Constants.ToBeApprovedState));		
						//changes.addAll(oyster2Conn.getChangesWithState(onto, Constants.ApprovedState));
						Collections.sort(changes, TIME_ORDER);
						for(OMVChange change : changes){
							String state = oyster2Conn.getChangeState(change.getURI());					
							if(state.equals(Constants.ToBeApprovedState)|| state.equals(Constants.ApprovedState) ){
								String persons = "";
								for(OMVPerson person : change.getHasAuthor())						
									persons += person.getFirstName()+" "+person.getLastName()+", ";	
								if(persons.length()>0)
									persons = persons.substring(0, persons.length()-2);						
								
								TableItem item = new TableItem(resTable, SWT.NONE);
								if(state.indexOf("#")!=-1)
				                	state = state.substring(state.indexOf("#")+1);
								String relatedEntity = Namespaces.guessLocalName(oyster2Conn.getRelatedEntity(change.getURI()));
								
								Action ac = oyster2Conn.getLastChangeAction(change);
    			                String acText ="";
    			                if (ac!=null) {
    			                	if (ac.getPerformedBy()!=null){
    			                		OMVPerson per = ac.getPerformedBy();
    			                		acText=ac.getClass().getSimpleName()+ " by "+per.getFirstName()+ " " +per.getLastName();
    			                	}
    			                }
								item.setText(new String[]{
									onto.getURI().toString(), change.getURI(), 
									change.getClass().getSimpleName(),
									relatedEntity,
									persons, change.getDate(),	state, acText	});
								if(!currentPerson.getHasRole().equalsIgnoreCase(Constants.Validator) || state.equals(Constants.ApprovedState))
									item.setForeground(grayColor);
									
							}
						}
					}
				}
			});
		
		} else if (e.getSource().equals(acceptProposalButton)){
			
			OMVPerson currentPerson = OysterTools.getCurrentUser(_store);
			if(!currentPerson.getHasRole().equalsIgnoreCase(Constants.Validator)){
				MessageDialog
				.openInformation(
						this.getSite().getShell(),
						"Information",
						"Only "+Constants.Validator+" can control this view!");
				return;
			}
			for(TableItem item : selectedItems){
				item.setText(this.STATUS_COLUMN, "Approved");
				executor.execute(new SubmitToApprovedThread(item.getText(this.CHANGE_URI), currentPerson));
				//oyster2Conn.submitToApproved(item.getText(this.CHANGE_URI), currentPerson);
			}
			selectedItems.clear();
		} else if (e.getSource().equals(rejectProposalButton)){
			
			OMVPerson currentPerson = OysterTools.getCurrentUser(_store);
			if(!currentPerson.getHasRole().equalsIgnoreCase(Constants.Validator)){
				MessageDialog
				.openInformation(
						this.getSite().getShell(),
						"Information",
						"Only "+Constants.Validator+" can control this view!");
				return;
			}
			for(TableItem item : selectedItems){
				item.setText(this.STATUS_COLUMN, "Draft");
				executor.execute(new RejectToDraftThread(item.getText(this.CHANGE_URI), currentPerson));
				//oyster2Conn.rejectToDraft(item.getText(this.CHANGE_URI), currentPerson);
			}
 			selectedItems.clear();
		}
	
		
	}
	
	public class SubmitToApprovedThread implements Runnable {
		String changeURI;
		OMVPerson person;
		public SubmitToApprovedThread(String p1, OMVPerson p2){
			changeURI = p1;
			person = p2;
		}
		public void run() {
			oyster2Conn.submitToApproved(changeURI, person);
		}
		
	}
	
	public class RejectToDraftThread implements Runnable {
		String changeURI;
		OMVPerson person;
		public RejectToDraftThread(String p1, OMVPerson p2){
			changeURI = p1;
			person = p2;
		}
		public void run() {
			oyster2Conn.rejectToDraft(changeURI, person);
		}
		
	}
	
	static final Comparator<OMVChange> TIME_ORDER =
        new Comparator<OMVChange>() {
		public int compare(OMVChange e1, OMVChange e2) {
			try {
				Date d1=DateFormat.getDateTimeInstance().parse(e1.getDate());
				Date d2=DateFormat.getDateTimeInstance().parse(e2.getDate());
				return d1.compareTo(d2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		}
	};
}
