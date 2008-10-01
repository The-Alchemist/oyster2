/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.jobs;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.progress.IProgressConstants;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.ImportOntology;
import org.semanticweb.kaon2.api.KAON2Exception;
import org.semanticweb.kaon2.api.ProgressListener;

import com.ontoprise.extensionalDB.ModuleAlreadyExistsException;
import com.ontoprise.ontostudio.io.OntologyImportException;
import com.ontoprise.ontostudio.io.wizard.ImportProgressListener;


/**
 * @author david
 *
 */
public class ImportOntologyJob extends Job {

	private String[] ontologyLocators = null;
	
	private String project = null;
	
	private List<String> errors = null;
	
	private String resultString = null;
	
	public ImportOntologyJob(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {

		Status status = null;
		errors = new LinkedList<String>();
		
		try {
			String [] result = doImport(project, ontologyLocators, monitor);
		}
		catch (Exception e) {
			e.printStackTrace();
			errors.add(e.getLocalizedMessage());
		}
		if (errors.size() != 0) {
			status = new Status(Status.ERROR,Activator.PLUGIN_ID,
					Status.OK,Messages.getString("ImportOntologyJob.errors.message"),null); //$NON-NLS-1$
			for (String errorString : errors) {
				resultString = errorString+"\n"; //$NON-NLS-1$
			}
		}
		else {
			status = new Status(Status.OK,Activator.PLUGIN_ID,Status.OK,
					Messages.getString("ImportOntologyJob.success.message"),null); //$NON-NLS-1$
			resultString = "Success"; //$NON-NLS-1$
		}
		if (isModal(this)) {
            // The progress dialog is still open so
            // just open the message
            showResultDialog();
         } else {
        	 setProperty(IProgressConstants.KEEP_PROPERTY, Boolean.TRUE);
        	 setProperty(IProgressConstants.ACTION_PROPERTY,getCompletedAction());
         }
		return status;
	}

		
		
		
	public String[] doImport(String project, String location[], IProgressMonitor monitor) throws OntologyImportException {
        //ImportExportControl control = new ImportExportControl();
    	
    	ImportOntology control = new ImportOntology();
        ProgressListener pl = null;
        
        if(monitor != null) {
        	//monitor.beginTask(Messages.getString("FileSystemImportWizard.3"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
        	pl = new ImportProgressListener("", monitor); //$NON-NLS-1$
        }
        String[] s = null;
        try {
        	
			//s = control.importFileSystem(project, uri, pl);
        	s = control.importOntologies(project, location, pl);
        	//s = control.importFileSystem(project, location, uri, pl);
        } catch (IOException e) {
            throw new OntologyImportException(location.toString(), e);
        } catch (ModuleAlreadyExistsException e) {
            throw new OntologyImportException(location.toString(), e);
        } catch (KAON2Exception e) {
        	throw new OntologyImportException(location.toString(), e);
        } catch (InterruptedException e) {
            throw new OntologyImportException(location.toString(), e);
        } catch (Exception e) {
            throw new OntologyImportException(location.toString(), e);
        }
        return s;
    }
	
	
	
	public boolean isModal(Job job) {
        Boolean isModal = (Boolean)job.getProperty(
                               IProgressConstants.PROPERTY_IN_DIALOG);
        if(isModal == null) return false;
        return isModal.booleanValue();
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
                  Messages.getString("ImportOntologyJob.finished.message"),  //$NON-NLS-1$
                  resultString);
			}
		};
	}

	/**
	 * @return the errors
	 */
	public final List<String> getErrors() {
		return errors;
	}

	/**
	 * @param errors the errors to set
	 */
	public final void setErrors(List<String> errors) {
		this.errors = errors;
	}

	/**
	 * @return the ontologyLocators
	 */
	public final String[] getOntologyLocators() {
		return ontologyLocators;
	}

	/**
	 * @param ontologyLocators the ontologyLocators to set
	 */
	public final void setOntologyLocators(String[] ontologyLocators) {
		this.ontologyLocators = ontologyLocators;
	}

	/**
	 * @return the project
	 */
	public final String getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public final void setProject(String project) {
		this.project = project;
	}

	/**
	 * @return the resultString
	 */
	public final String getResultString() {
		return resultString;
	}

	/**
	 * @param resultString the resultString to set
	 */
	public final void setResultString(String resultString) {
		this.resultString = resultString;
	}
	
}
