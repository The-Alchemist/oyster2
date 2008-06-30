/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.jobs;

import java.util.Map;

import org.apache.axis2.AxisFault;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressConstants;
import org.neon_toolkit.registry.omv.service.querymanager.NeOnRegistryOMVOysterStub;
import org.neontoolkit.oyster2.client.core.QueryResponse;
import org.neontoolkit.oyster2.client.core.querymanager.QueryPortAxis2Adapter;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.PerspectiveFactory;
import org.neontoolkit.oyster2.client.gui.adapter.results.IResultsAdapter;
import org.neontoolkit.oyster2.client.gui.adapter.results.ResultsAdapterFactory;
import org.neontoolkit.oyster2.client.gui.views.SearchResultsView;

/**
 * @author David Muñoz
 *
 */
public class SubmitQueryJob extends WorkspaceJob {

	private String targetService = null;
	
	private String resultString = null;
	
	private Class queryTarget = null;
	
	private QueryResponse response = null;
	
	private Map<String,Object> filters = null;
	
	public SubmitQueryJob(String name) {
		super(name);
	}

	 public boolean isModal(Job job) {
	        Boolean isModal = (Boolean)job.getProperty(
	                               IProgressConstants.PROPERTY_IN_DIALOG);
	        if(isModal == null) return false;
	        return isModal.booleanValue();
	     }
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.WorkspaceJob#runInWorkspace(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor)
			throws CoreException {
		Status status = null;
		if (this.targetService == null || this.filters == null 
				|| queryTarget == null) {
			
			throw new RuntimeException("Null value in required parameter"); //$NON-NLS-1$
		}
		try {
			monitor.beginTask(Messages.getString("SubmitQueryJob.monitor.begin"),3); //$NON-NLS-1$
			monitor.subTask(Messages.getString("SubmitQueryJob.monitor.building")); //$NON-NLS-1$
			QueryPortAxis2Adapter queryPortAdapter =
				new QueryPortAxis2Adapter();
			
			NeOnRegistryOMVOysterStub serviceStub =
				new NeOnRegistryOMVOysterStub(targetService);
			long soTimeout = 10 * 60 * 1000; // four minutes
	 	    serviceStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(soTimeout);
			queryPortAdapter.setServiceStub(serviceStub);
			monitor.worked(1);
			monitor.subTask(Messages.getString("SubmitQueryJob.monitor.waiting")); //$NON-NLS-1$
			response =
				queryPortAdapter.submitQueryRequest(queryTarget.getName(),null, filters);
			resultString = response.getStringMessage();
			monitor.worked(2);
			monitor.subTask(Messages.getString("SubmitQueryJob.monitor.view")); //$NON-NLS-1$
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			status = new Status(Status.OK,Activator.PLUGIN_ID,Status.OK,resultString,null);
			openResultsView();
			monitor.worked(3);
			monitor.done();
			
		}
		catch (AxisFault e) {
			resultString = Messages.getString("SubmitQueryJob.error.message.axis") + //$NON-NLS-1$
				e.getMessage();
			e.printStackTrace();
			response = null;
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			status = new Status(Status.ERROR,Activator.PLUGIN_ID,
					Status.OK,Messages.getString("SubmitQueryJob.error.status.message") + //$NON-NLS-1$
					e.getLocalizedMessage(),e);
			
			openResultsView();
		}
		catch (Exception e) {
			resultString =  e.getMessage();
			e.printStackTrace();
			response = null;
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			status = new Status(Status.ERROR,Activator.PLUGIN_ID,
					Status.OK,Messages.getString("SubmitQueryJob.error.label") + e.getLocalizedMessage(),e); //$NON-NLS-1$
			openResultsView();
		}
		
		if (isModal(this)) {
            // The progress dialog is still open so
            // just open the message
           // showResultDialog();
         } else {
        	 setProperty(IProgressConstants.KEEP_PROPERTY, Boolean.TRUE);
        	 setProperty(IProgressConstants.ACTION_PROPERTY,getCompletedAction());
         }return status;
	}
	
	private void openResultsView() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page =
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				
				try {
					SearchResultsView view = (SearchResultsView)
						page.showView(PerspectiveFactory.OYSTER2_RESULTS_VIEW_ID);
					IResultsAdapter results = null;
					if (response != null) {
					results =
						ResultsAdapterFactory.getResultsList(response.getObjects());
					}
					view.putResults(results);
					
					
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	/*
	private void  showResultDialog() {
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
               getCompletedAction().run();
            }
         });
      }
	*/
	protected Action getCompletedAction() {
		return new Action() {
			public void run() {
				Shell shell = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
				MessageDialog.openInformation(shell, 
                  Messages.getString("SubmitQueryJob.succes.message"),  //$NON-NLS-1$
                  resultString);
			}
		};
	}
	
	public final Map<String, Object> getFilters() {
		return filters;
	}


	public final void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}


	
	public final Class getQueryTarget() {
		return queryTarget;
	}

	public final void setQueryTarget(Class queryTarget) {
		this.queryTarget = queryTarget;
	}

	public final String getTargetService() {
		return targetService;
	}


	public final void setTargetService(String targetService) {
		this.targetService = targetService;
	}
	
	
	
}
