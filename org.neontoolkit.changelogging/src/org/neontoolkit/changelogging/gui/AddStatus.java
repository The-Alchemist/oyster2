package org.neontoolkit.changelogging.gui;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.SubStatusLineManager;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.neontoolkit.changelogging.gui.actions.Track;
import com.ontoprise.ontostudio.gui.navigator.MTreeView;

public  class AddStatus implements Runnable {
	private Shell shell;
	static SubStatusLineManager sslm=null;
	public AddStatus(Shell arg){
		this.shell=arg;
	}
	public void run() {
		shell.getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbench wb = PlatformUI.getWorkbench();
				IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
				IWorkbenchPage page = win.getActivePage();
				IWorkbenchPartSite site = page.findView(MTreeView.ID).getSite(); //part.getSite();
				IViewSite vSite = ( IViewSite ) site;
				IActionBars actionBars =  vSite.getActionBars();
				if( actionBars == null )return ;
				IStatusLineManager statusLineManager = actionBars.getStatusLineManager();
				if( statusLineManager == null )return ;
				
				statusLineManager.removeAll();
				if (sslm==null)	
					sslm = new SubStatusLineManager(statusLineManager);
				else
					sslm.removeAll();
				sslm.add(Track.statusLineItem);
				sslm.setVisible(true);

				Track.statusLineItem.setText("Logging: "+Track.OWLList.size()+ " ontologies");
				//statusLineManager.add(Track.statusLineItem); 
				//statusLineManager.setMessage("Tracking: "+Track.OWLList.size()+ " ontologies");
			}
		});					
	}		
}



