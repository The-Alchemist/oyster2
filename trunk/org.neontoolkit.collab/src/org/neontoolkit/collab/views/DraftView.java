package org.neontoolkit.collab.views;

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
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
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
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange.Addition;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange.Removal;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;
import org.neontoolkit.registry.api.Oyster2Connection;
import org.neontoolkit.registry.api.Oyster2Manager;
import org.neontoolkit.registry.oyster2.Constants;
import org.neontoolkit.workflow.api.Action;

import com.ontoprise.ontostudio.gui.GuiPlugin;
import org.neontoolkit.changelogging.core.ApplyChangesFromLogToNTK;
import org.neontoolkit.changelogging.core.ChangeTableItem;
import org.semanticweb.kaon2.api.Namespaces;


public class DraftView extends ViewPart implements SelectionListener {

	public static final String ID = "org.neontoolkit.collab.views.DraftView"; 
	
	static Oyster2Connection oyster2Conn = StartRegistry.connection;//null;
	private Composite composite = null;
	static Table resTable ;
	static StyledText serialization;
	private static Button refreshButton, removeChangeButton, toBeApprovedButton;
	static IPreferenceStore _store;
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	static Color grayColor=null;
	static Shell shell=null;
	
	int CHANGE_URI = 1;
	int AUTHOR_COLUMN = 4;
	int STATUS_COLUMN = 6;
	
	static HashSet<TableItem> selectedItems = new HashSet<TableItem>();
	
	public DraftView(){
		_store = GuiPlugin.getDefault().getPreferenceStore();
	}
	
	@Override
	public void createPartControl(Composite parent) {				
		GridData gd = null;
		int columns = 2;	
		int width = 800;
		int columnWidth = 100;
		grayColor=this.getSite().getShell().getDisplay().getSystemColor(SWT.COLOR_GRAY);
		shell=this.getSite().getShell();
		
		
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
		gd.heightHint = 300;
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
		        	//System.out.println(item.getText(4) + " (checked) ");
		        	selectedItems.add(item);
		        } else {
		        	//System.out.println(item.getText(4) + " (nonchecked) ");
		        	selectedItems.remove(item);
		        }
		        serialization.setText("");
		        if (selectedItems.size()>0)
		        	executor.execute(new RefreshSerializationThread(shell));
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
		
		removeChangeButton = new Button(composite, SWT.PUSH);
		removeChangeButton.setText("Delete");
		removeChangeButton.addSelectionListener(this);
		toBeApprovedButton = new Button(composite, SWT.PUSH);
		toBeApprovedButton.setText("Submit Changes To Be Approved");
		toBeApprovedButton.addSelectionListener(this);
		
		
		gd = new GridData();
		gd.horizontalSpan = columns;
		gd.widthHint = width;
		gd.heightHint = 250;
		gd.grabExcessHorizontalSpace = true;
		serialization = new StyledText(composite, SWT.CHECK | SWT.V_SCROLL | SWT.BORDER);
		serialization.setLayoutData(gd);
		
	}

	@Override
	public void setFocus() {
		
	}
	
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void widgetSelected(SelectionEvent e) {
		
		if(e.getSource().equals(refreshButton)){
			executor.execute(new Refresh(shell));
			serialization.setText("");
		} else if (e.getSource().equals(toBeApprovedButton)){
			OMVPerson currentPerson = OysterTools.getCurrentUser(_store);
			if(!currentPerson.getHasRole().equalsIgnoreCase(Constants.SubjectExpert)){
				MessageDialog
				.openInformation(
						this.getSite().getShell(),
						"Information",
						"Only "+Constants.SubjectExpert+" can control this view!");
				return;
			}
			List<String> changeURIs=new LinkedList<String>();
			for(TableItem item : selectedItems){
				String authors = item.getText(this.AUTHOR_COLUMN);
				if(authors.indexOf(currentPerson.getFirstName()+" "+currentPerson.getLastName())!=-1){
					item.setText(this.STATUS_COLUMN, "ToBeApproved");
					changeURIs.add(item.getText(this.CHANGE_URI));
					//executor.execute(new SubmitToBeApprovedThread(item.getText(this.CHANGE_URI), currentPerson,shell));
					resTable.remove(resTable.indexOf(item));
				}
			}
			if (changeURIs.size()>0)
				executor.execute(new SubmitToBeApprovedThread(changeURIs, currentPerson,shell));
			selectedItems.clear();
			serialization.setText("");
			
		} else if (e.getSource().equals(removeChangeButton)){
			OMVPerson currentPerson = OysterTools.getCurrentUser(_store);
			if(!currentPerson.getHasRole().equalsIgnoreCase(Constants.SubjectExpert)){
				MessageDialog
				.openInformation(
						this.getSite().getShell(),
						"Information",
						"Only "+Constants.SubjectExpert+" can control this view!");
				return;
			} 
			
			HashSet<TableItem> validItems = new HashSet<TableItem>();
			for(TableItem item : selectedItems){
				String authors = item.getText(this.AUTHOR_COLUMN);
				if(authors.indexOf(currentPerson.getFirstName()+" "+currentPerson.getLastName())!=-1){
					validItems.add(item);
				}
			}
			selectedItems.clear();
			if(validItems.size()>0){
				boolean answer = MessageDialog.openQuestion(
						this.getSite().getShell(),
						"Information",
						"Are you sure to delete this changes?");
				if(answer){
					List<String> changeURIs=new LinkedList<String>();
					for(TableItem item : validItems){
						changeURIs.add(item.getText(this.CHANGE_URI));
						//executor.execute(new DeleteThread(item.getText(this.CHANGE_URI),currentPerson,shell));
					    resTable.remove(resTable.indexOf(item));
					}
					if (changeURIs.size()>0)
						executor.execute(new DeleteThread(changeURIs, currentPerson,shell));
					validItems.clear();
				}
			} else {
				MessageDialog
				.openInformation(
						this.getSite().getShell(),
						"Information",
						"There is no change that the current user can do deletion!");
			}
			serialization.setText("");
		}
	}
	
	public class SubmitToBeApprovedThread implements Runnable {
		List<String> changeURIs;
		OMVPerson person;
		Shell shell;
		public SubmitToBeApprovedThread(List<String> p1, OMVPerson p2, Shell s){
			changeURIs = p1;
			person = p2;
			shell=s;
		}
		public void run() {
			if (!goContinue()) return;
			if(changeURIs==null || person==null || shell==null) return;
			shell.getDisplay().asyncExec(new Runnable() {
		           public void run() {
		        	   Job exportJob = new Job("Submitting changes...") {
			   	        	protected IStatus run(IProgressMonitor monitor) {
			   	        		monitor.beginTask("Submitting changes...", 100);
			   	        		try {
			   	        			for (String cURI : changeURIs){
			   	        				monitor.worked(50/changeURIs.size());
			   	        				oyster2Conn.submitToBeApproved(cURI, person);
			   	        				monitor.worked(50/changeURIs.size());
			   	        			}
			   	        		}catch (Exception e) {
			   	            		openMessage("Submit failed");
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
	
	public class DeleteThread implements Runnable {
		List<String> changeURIs;
		OMVPerson person;
		Shell shell;
		public DeleteThread(List<String> p1, OMVPerson p2, Shell s){
			changeURIs = p1;
			person=p2;
			shell=s;
		}
		public void run() {
			if (!goContinue()) return;
			if(changeURIs==null || person==null || shell==null) return;
			shell.getDisplay().asyncExec(new Runnable() {
		           public void run() {
		        	   Job exportJob = new Job("Deleting changes...") {
			   	        	protected IStatus run(IProgressMonitor monitor) {
			   	        		monitor.beginTask("Deleting changes...", 100);
			   	        		try {
			   	        			for (String cURI : changeURIs){
			   	        				OMVChange change = oyster2Conn.getChange(cURI);
				   	        			monitor.worked(20/changeURIs.size());
				   	        			Set<OMVAtomicChange> aChanges = oyster2Conn.getAtomicChanges((OMVEntityChange)change);
				   	        			monitor.worked(20/changeURIs.size());
					   	     			List<OMVChange> listToApply = new LinkedList<OMVChange>();
					   	     			for (OMVAtomicChange ac : aChanges){
					   	     				OMVAtomicChange changeInv=null;
					   	     				if (ac instanceof Addition){
					   	     					changeInv=new Removal();
					   	     				}
					   	     				else if (ac instanceof Removal){
					   	     					changeInv=new Addition();		
					   	     				}
					   	     				else 
					   	     					openMessage("Something strange happened");
					   	     				if (changeInv!=null){	
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
					   	     			}
					   	     			monitor.worked(20/changeURIs.size());
					   	     			try{
					   	     				ApplyChangesFromLogToNTK.applyChanges(listToApply, change.getAppliedToOntology());
					   	     			}catch(Exception e){
					   	     				//ignore
					   	     			}
					   	     			//oyster2Conn.remove(change); 
					   	     			//do not loose the change
					   	     			monitor.worked(20/changeURIs.size());
					   	     			oyster2Conn.delete(cURI, person);
					   	     			monitor.worked(20/changeURIs.size());
			   	        			}
			   	        		}catch (Exception e) {
			   	            		openMessage("Deletion failed");
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
	
	static final Comparator<OMVChange> TIME_ORDER =
        new Comparator<OMVChange>() {
		public int compare(OMVChange e1, OMVChange e2) {
			try {
				Date d1=DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).parse(e1.getDate());
				Date d2=DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).parse(e2.getDate());
				return d1.compareTo(d2);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return 0;
		}
	};
	
	public class Refresh implements Runnable {
		Shell shell;
		public Refresh(Shell p1){
			shell = p1;
		}
		public void run() {
			shell.getDisplay().asyncExec(new Runnable() {
		           public void run() {
		   				if (!goContinue()) return;
		   				resTable.removeAll();
		   				selectedItems.clear();
		   				Job exportJob = new Job("Refreshing changes...") {
			   	        	protected IStatus run(IProgressMonitor monitor) {
			   	        		monitor.beginTask("Refreshing changes...", 100);
			   	        		try {	            			
			   	        			OMVPerson currentPerson = OysterTools.getCurrentUser(_store);
			   	        			List<OMVChange> changes = new ArrayList<OMVChange>();
			   	        			for(OMVOntology onto : oyster2Conn.getOntologiesWithChanges()){				
			   	        				changes.clear();
			   	        				monitor.worked(20);
			   	        				changes.addAll(oyster2Conn.getChangesWithState(onto, Constants.DraftState));
			   	        				monitor.worked(40);
			   	        				//changes.addAll(oyster2Conn.getChangesWithState(onto, Constants.ApprovedState));
			   	        				//System.out.println("onto : "+onto.getURI()+" (changes = "+changes.size());
			   	        				Collections.sort(changes, TIME_ORDER);
			   	        				for(OMVChange change : changes){
			   	        					String state = oyster2Conn.getChangeState(change.getURI());
			   	        					if(state.equals(Constants.DraftState) || state.equals(Constants.ApprovedState)){
			   	        						String persons = "";
			   	        						boolean isCurrentUser = false;
			   	        						for(OMVPerson person : change.getHasAuthor()){						
			   	        							persons += person.getFirstName()+" "+person.getLastName()+", ";		
			   	        							if(person.getFirstName().equalsIgnoreCase(currentPerson.getFirstName()) && 
			   	        									person.getLastName().equalsIgnoreCase(currentPerson.getLastName())){
			   	        								isCurrentUser = true;
			   	        								break;
			   	        							}
			   	        						}												
			   	        						if(persons.length()>0)
			   	        							persons = persons.substring(0, persons.length()-2);						
			   	        									
			   	        		                	 
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
			   	        		                addItem(onto.getURI().toString(),
			   		        							change.getURI(), change.getClass().getSimpleName(),
			   		        							relatedEntity,
			   		        							persons, change.getDate(),	state, acText, currentPerson, isCurrentUser);
			   	        					}
			   	        					monitor.worked(40/changes.size());
			   	        				}
			   	        			}
			   	            	}catch (Exception e) {
			   	            		openMessage("Refresh failed");
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
	
	
	public void addItem(final String ontoURI,final String changeURI,final String changeName,final String relatedEntity,final String persons,final String date,final String state,final String acText,final OMVPerson currentPerson,final boolean isCurrentUser){
		shell.getDisplay().asyncExec(new Runnable() {
	           public void run() {
	        	   ChangeTableItem item = new ChangeTableItem(resTable, SWT.NONE);
	        	   item.setText(new String[]{
	        			   ontoURI,
	        			   changeURI, changeName,
	        			   relatedEntity,
	        			   persons, date,	state, acText	});					    					
	        	   if(!currentPerson.getHasRole().equalsIgnoreCase(Constants.SubjectExpert) || !isCurrentUser || state.equals(Constants.ApprovedState))
	        		   item.setForeground(grayColor);
	           }
		});
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
	
	
	public class RefreshSerializationThread implements Runnable {
		List<String> changeURIs= new LinkedList<String>();
		Shell shell;
		public RefreshSerializationThread(Shell s){
			shell=s;
		}
		public void run() {
			shell.getDisplay().asyncExec(new Runnable() {
		           public void run() {
		        	   if (!goContinue()) return;
		        	   for(TableItem item : selectedItems)
		       				changeURIs.add(item.getText(CHANGE_URI));
		        	   Job exportJob = new Job("Getting all change information...") {
			   	        	protected IStatus run(IProgressMonitor monitor) {
			   	        		monitor.beginTask("Getting all change information...", 100);
			   	        		try {
			   	        			List<OMVChange> at=new LinkedList<OMVChange>();
			   	        			for (String entityChange : changeURIs){
			   	        				OMVChange c=oyster2Conn.getChange(entityChange);
			   	        				if (c instanceof OMVEntityChange){
			   	        					for (OMVChange intern : oyster2Conn.getAtomicChanges((OMVEntityChange)c)){
			   	        						if (intern instanceof OMVAtomicChange) at.add(intern);
			   	        					}
			   	        				}
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
}	