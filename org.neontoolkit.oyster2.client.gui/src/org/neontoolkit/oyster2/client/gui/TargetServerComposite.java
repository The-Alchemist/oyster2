/**
 * 
 */
package org.neontoolkit.oyster2.client.gui;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.neontoolkit.oyster2.client.gui.dialogs.ListEditionDialog;

/**
 * @author David Muñoz
 *
 */
public class TargetServerComposite extends Composite {
	private Combo serverCombo = null;
	
	private Button addButton = null;
	
	private ListEditionDialog dialog  = null;
	
	public TargetServerComposite(Composite parent,int style) {
		super(parent,style);
		setLayout(new FillLayout());
		
		putServerCombo();
		
		putEditButton();
		makeLayout();
		makeListeners();
	}
	
	
	private void putEditButton() {
		addButton = new Button(this,SWT.PUSH);
		addButton.setText(Messages.getString("TargetServerComposite.serverComposite.edit.button")); //$NON-NLS-1$
	}


	private void putServerCombo() {
		serverCombo = new Combo(this,SWT.DROP_DOWN|SWT.READ_ONLY);
		String [] servers = Activator.getWebServersLocator().getServers();
		String selectedServer = Activator.getWebServersLocator().
			getCurrentSelection();
		serverCombo.setItems(servers);
		if (selectedServer != null) {
			int i = 0;
			for (; i<servers.length;i++) {
				if (servers[i].equals(selectedServer))
					break;
			}
			serverCombo.setText(selectedServer);
			serverCombo.select(i);
		}
		else {
			serverCombo.setText(serverCombo.getItem(0));
			
		}
		
	}


	private void makeLayout() {
		FormLayout layout = new FormLayout();
		FormData fd = new FormData();
		this.setLayout(layout);
		fd.top = new FormAttachment(0,0);
		//fd.bottom = new FormAttachment(100,0);
		fd.right = new FormAttachment(addButton,-5);
		fd.left = new FormAttachment(0,0);
		serverCombo.setLayoutData(fd);
		
		
		fd = new FormData();
		fd.top = new FormAttachment(0,0);
		fd.right = new FormAttachment(100,0);
		Point p = addButton.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		if (p.x < 50)
			fd.width = 50;
		addButton.setLayoutData(fd);
		
		
	}
	
	
	private void makeListeners() {
		// this listeners sets the current selection of this composite as
		// the global selected item
		Listener selectionListener = new Listener()	{
			public void handleEvent(Event event) {
				String selected = serverCombo.getItem(
						serverCombo.getSelectionIndex());
				Activator.getWebServersLocator().setCurrentSelection(selected);
			}
		
		};
		serverCombo.addListener(SWT.Selection, selectionListener);
		
		// this listener updates the list of servers
		Listener serverListChangeListener = new Listener() {
			public void handleEvent(Event event) {
				String server = event.text;
				if (((String)event.data).equals(WebServersLocator.ADDED) ) {
					serverCombo.add(server);
				}
				else if (((String)event.data).equals(WebServersLocator.DELETED) ) {
					serverCombo.remove(server);
					if (serverCombo.getText().trim().equals("")) { //$NON-NLS-1$
						if (serverCombo.getItemCount()>0)
							serverCombo.select(0);
					}
				}
			}
		};
		Activator.getWebServersLocator().addContentListener(serverListChangeListener);
		
		//this listener sets the current selection of this composite
		//to be the same as the global selection
		Listener serverSelectionListener = new Listener() {
			public void handleEvent(Event event) {
				String selected =
					Activator.getWebServersLocator().getCurrentSelection();
				serverCombo.setText(selected);
				
			}
		};
		Activator.getWebServersLocator().
			addSelectionListener(serverSelectionListener);
		
		
		// this is the edit listener, which opens a dialog to modify
		// the server list
		Listener buttonListener = new Listener(){

			public void handleEvent(Event event) {
				Shell shell = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
				dialog = new ListEditionDialog("ServerCompositeDialog",shell); //$NON-NLS-1$
				
				String []items = serverCombo.getItems();
				dialog.setItems(items);
				int result = dialog.open();
				if (result == TrayDialog.OK) {
					WebServersLocator serversManager = 
						Activator.getWebServersLocator();
					items = dialog.getItems();
					for (String added : dialog.getAdded()) {
						serversManager.addServer(added);
					}
					for (String deleted : dialog.getDeleted()) {
						serversManager.removeServer(deleted);
					}

				}
				
			}
			
		};
		addButton.addListener(SWT.Selection, buttonListener);
	}
	
	
	
}
