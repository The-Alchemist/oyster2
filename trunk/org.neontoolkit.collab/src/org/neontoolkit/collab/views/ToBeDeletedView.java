package org.neontoolkit.collab.views;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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
import org.neontoolkit.changelogging.menu.ApplyChangesFromLogToNTK;
import org.neontoolkit.collab.core.OysterTools;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.core.OMVPerson;
import org.neontoolkit.omv.api.extensions.change.OMVChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange.Addition;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange.Removal;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;
import org.neontoolkit.registry.api.Oyster2Connection;
import org.neontoolkit.registry.api.workflow.WorkflowManagement;
import org.neontoolkit.registry.oyster2.Constants;
import org.neontoolkit.workflow.api.Action;
import org.neontoolkit.workflow.api.Action.EntityAction;
import org.neontoolkit.workflow.api.Action.EntityAction.Insert;

import com.ontoprise.ontostudio.gui.GuiPlugin;

public class ToBeDeletedView extends ViewPart implements SelectionListener {

	public static final String ID = "org.neontoolkit.collab.ToBeDeletedView"; 
	
	static Oyster2Connection oyster2Conn = StartRegistry.connection;//null;
	private Composite composite = null;
	private static Table resTable ;
	private static Button refreshButton, acceptDeletionButton, rejectDeletionButton;
	static IPreferenceStore _store;
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	static Shell shell=null;
	static Color grayColor=null;
	
	int CHANGE_URI = 1;
	int AUTHOR_COLUMN = 4;
	int STATUS_COLUMN = 6;
	
	static HashSet<TableItem> selectedItems = new HashSet<TableItem>();
	
	public ToBeDeletedView() {
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
		uSetNrColumn.setWidth(columnWidth);
		uSetNrColumn.setAlignment(SWT.LEFT);
		
		uSetNrColumn = new TableColumn(resTable, SWT.LEFT);
		uSetNrColumn.setText("Change URI"); //$NON-NLS-1$
		uSetNrColumn.setWidth(columnWidth);
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
		uSetNrColumn.setWidth(columnWidth+50);
		uSetNrColumn.setAlignment(SWT.LEFT);
		
		uSetNrColumn = new TableColumn(resTable, SWT.LEFT);
		uSetNrColumn.setText("Status"); //$NON-NLS-1$
		uSetNrColumn.setWidth(columnWidth);
		uSetNrColumn.setAlignment(SWT.LEFT);
		
		acceptDeletionButton = new Button(composite, SWT.PUSH);
		acceptDeletionButton.setText(" Delete Permanently");
		acceptDeletionButton.addSelectionListener(this);
		rejectDeletionButton = new Button(composite, SWT.PUSH);
		rejectDeletionButton.setText(" Reject Changes Back to Approved ");
		rejectDeletionButton.addSelectionListener(this);
		
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
						//System.out.println("onto : "+onto.getURI());
						changes.clear();
						//get the changes to be approved
						changes.addAll(oyster2Conn.getChangesWithState(onto, Constants.ToBeDeletedState));	
						//changes.addAll(oyster2Conn.getChangesWithState(onto, Constants.ApprovedState));			
						for(OMVChange change : changes){
							String state = oyster2Conn.getChangeState(change.getURI());					
							if(state.equals(Constants.ToBeDeletedState)||state.equals(Constants.ApprovedState)){
								String persons = "";
								for(OMVPerson person : change.getHasAuthor())						
									persons += person.getFirstName()+" "+person.getLastName()+", ";	
								if(persons.length()>0){
									persons = persons.substring(0, persons.length()-2);						
								}		 
								
								TableItem item = new TableItem(resTable, SWT.NONE);
								if(state.indexOf("#")!=-1)
				                	state = state.substring(state.indexOf("#")+1);
								item.setText(new String[]{
									onto.getURI().toString(),
									change.getURI(), change.getClass().getSimpleName(),
									oyster2Conn.getRelatedEntity(change.getURI()),
									persons, change.getDate(),	state	});
								if(!currentPerson.getHasRole().equalsIgnoreCase(Constants.Validator) || state.equals(Constants.ApprovedState)){
									item.setForeground(grayColor);
								}
									
							}
						}
					}
				}
			});
		
		} else if (e.getSource().equals(acceptDeletionButton)){
			
			OMVPerson currentPerson = OysterTools.getCurrentUser(_store);
			if(!currentPerson.getHasRole().equalsIgnoreCase(Constants.Validator)){
				MessageDialog
				.openInformation(
						this.getSite().getShell(),
						"Information",
						"Only "+Constants.Validator+" can control this view!");
				return;
			} else {
				//WorkflowManagement wMgmt= new WorkflowManagement();
				for(TableItem item : selectedItems){
					item.setText(this.STATUS_COLUMN, "Deleted");
					executor.execute(new DestroyThread(item.getText(this.CHANGE_URI), currentPerson));
					//wMgmt.delete(item.getText(this.CHANGE_URI), currentPerson, null);
				}
				selectedItems.clear();				
			}
			
		} else if (e.getSource().equals(rejectDeletionButton)){
			
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
				executor.execute(new RejectToApprovedThread(item.getText(this.CHANGE_URI), currentPerson));
				//oyster2Conn.rejectToApproved(item.getText(this.CHANGE_URI), currentPerson);
			}
			selectedItems.clear();
		}
	}
	
	public class DestroyThread implements Runnable {
		String changeURI;
		OMVPerson person;
		public DestroyThread(String p1, OMVPerson p2){
			changeURI = p1;
			person = p2;
		}
		public void run() {
			WorkflowManagement wMgmt= new WorkflowManagement();
			wMgmt.delete(changeURI, person, null);
		}
		
	}
	
	public class RejectToApprovedThread implements Runnable {
		String changeURI;
		OMVPerson person;
		public RejectToApprovedThread(String p1, OMVPerson p2){
			changeURI = p1;
			person = p2;
		}
		public void run() {
			OMVChange change = oyster2Conn.getChange(changeURI);
			
			boolean noinverse=false;
			List<Action> acs= oyster2Conn.getEntityActionsHistory(change.getAppliedToOntology(), null);
			for (Action action : acs){
				if (action instanceof EntityAction){
					EntityAction eaction = (EntityAction) action;
					if (eaction.getRelatedChange().equalsIgnoreCase(changeURI) && (eaction instanceof Insert))
						noinverse = true;
				}
			}
			
			Set<OMVAtomicChange> aChanges = oyster2Conn.getAtomicChanges((OMVEntityChange)change);
			List<OMVChange> listToApply = new LinkedList<OMVChange>();
			for (OMVAtomicChange ac : aChanges){
				OMVAtomicChange changeInv=null;
				if (ac instanceof Addition){
					if (!noinverse)
						changeInv=new Removal();
					else
						changeInv=new Addition();
				}
				else if (ac instanceof Removal){
					if (!noinverse)
						changeInv=new Addition();
					else
						changeInv=new Removal();
				}
				else {
					System.out.println("sth strange");
					return;
				}
				for (OMVPerson p : ac.getHasAuthor()) changeInv.addHasAuthor(p);
				if (ac.getAppliedAxiom()!=null) changeInv.setAppliedAxiom(ac.getAppliedAxiom());
				if (ac.getAppliedToOntology()!=null) changeInv.setAppliedToOntology(ac.getAppliedToOntology());
				for (String cc : ac.getCauseChange()) changeInv.addCauseChange(cc);
				if (ac.getCost()!=null) changeInv.setCost(ac.getCost());
				if (ac.getDate()!=null) changeInv.setDate(ac.getDate());
				if (ac.getHasPreviousChange()!=null) changeInv.setHasPreviousChange(ac.getHasPreviousChange());
				if (ac.getPriority()!=null) changeInv.setPriority(ac.getPriority());
				if (ac.getRelevance()!=null) changeInv.setRelevance(ac.getRelevance());
				if (ac.getTime()!=null) changeInv.setTime(ac.getTime());
				if (ac.getURI()!=null) changeInv.setURI(ac.getURI());
				if (ac.getVersion()!=null) changeInv.setVersion(ac.getVersion());
				listToApply.add(changeInv);
			}
			ApplyChangesFromLogToNTK.applyChanges(listToApply, change.getAppliedToOntology());
			oyster2Conn.rejectToApproved(changeURI, person);
		}
		
	}

}
