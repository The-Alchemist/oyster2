/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.jobs;

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
import org.eclipse.ui.progress.IProgressConstants;
import org.neontoolkit.oyster2.client.core.lifecycle.LifecyclePortAxis2Adapter;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.registry.omv.service.lifecyclemanager.NeOnRegistryOMVOysterStub;
import org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType;

/**
 * @author David Muñoz
 *
 */
public class SubmitObjectsJob extends WorkspaceJob {

	private RegistryObjectType omvObject = null;
	
	private String targetService = null;
	
	private String resultString = null;
	
	public SubmitObjectsJob(String name) {
		super(name);
		
	}

	 public boolean isModal(Job job) {
	        Boolean isModal = (Boolean)job.getProperty(
	                               IProgressConstants.PROPERTY_IN_DIALOG);
	        if(isModal == null) return false;
	        return isModal.booleanValue();
	     }


	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
		if ((targetService == null) || (omvObject == null)) {
			throw new RuntimeException("Service endpoint set to " +
					targetService + " object set to " + omvObject.toString());
		}
		Status status = null;
		LifecyclePortAxis2Adapter lifecyclePortAdapter = new LifecyclePortAxis2Adapter();
		NeOnRegistryOMVOysterStub serviceStub;
		try {
			serviceStub = new NeOnRegistryOMVOysterStub(targetService);
			long soTimeout = 10 * 60 * 1000; // four minutes
	 	    serviceStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(soTimeout);
			lifecyclePortAdapter.setServiceStub(serviceStub);
			resultString = lifecyclePortAdapter.submitObjectsRequest(omvObject).toString();
			status = new Status(Status.OK,Activator.PLUGIN_ID,Status.OK,resultString,null);
		} catch (AxisFault e) {
			status = new Status(Status.ERROR,Activator.PLUGIN_ID,
					Status.OK,Messages.getString("SubmitObjectsJob.error.message"),e); //$NON-NLS-1$
		}
		
		if (isModal(this)) {
            // The progress dialog is still open so
            // just open the message
            showResultDialog();
         } else {
        	 setProperty(IProgressConstants.KEEP_PROPERTY, Boolean.TRUE);
        	 setProperty(IProgressConstants.ACTION_PROPERTY,getCompletedAction());
         }return status;
	}
	
	
	private void  showResultDialog() {
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
               getCompletedAction().run();
            }
         });
      }
	
	protected Action getCompletedAction() {
		return new Action() {
			public void run() {
				Shell shell = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
				MessageDialog.openInformation(shell, 
                  Messages.getString("SubmitObjectsJob.completed.message"),  //$NON-NLS-1$
                  resultString);
			}
		};
	}


	
	
	/**
	 * @return the targetService
	 */
	public final String getTargetService() {
		return targetService;
	}

	/**
	 * @param targetService the targetService to set
	 */
	public final void setTargetService(String targetService) {
		this.targetService = targetService;
	}

	public RegistryObjectType getOmvObject() {
		return omvObject;
	}

	public void setOmvObject(RegistryObjectType omvObject) {
		this.omvObject = omvObject;
	}
	
	
	
}
