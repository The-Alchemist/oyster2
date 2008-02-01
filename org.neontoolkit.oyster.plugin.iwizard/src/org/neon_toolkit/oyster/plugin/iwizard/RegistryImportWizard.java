package org.neon_toolkit.oyster.plugin.iwizard;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;
import org.semanticweb.kaon2.api.KAON2Exception;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.ProgressListener;

import com.ontoprise.api.StringRepresentation;
import com.ontoprise.extensionalDB.ModuleAlreadyExistsException;
//import com.ontoprise.ontostudio.datamodel.util.IDTermUtil;
import com.ontoprise.ontostudio.exception.OntoStudioExceptionHandler;
import com.ontoprise.ontostudio.io.Messages;
import com.ontoprise.ontostudio.io.OntologyImportException;
import com.ontoprise.ontostudio.io.wizard.AbstractImportSelectionPage;
import com.ontoprise.ontostudio.io.wizard.AbstractImportWizard;
import com.ontoprise.ontostudio.io.wizard.ImportProgressListener;
import com.ontoprise.util.URIUtilities;


public class RegistryImportWizard extends AbstractImportWizard {

	//constructor
    public RegistryImportWizard() {
        super();
        setWindowTitle("Oyster Import Wizard"); 
    }

    
    @Override
    public void addPages() {
        addPage(new RegistryConditionPage());
        addPage(new RegistrySummaryPage());
        addImportSelectionPage();
    }
    
    @Override
    public String getPageDescription() {
        return ""; 
    }

    @Override
    public String getPageTitle() {
        return "";//Messages.getString("FileSystemImportWizard.2");
    }

    @Override
    public AbstractImportSelectionPage getImportSelectionPage() {
        return new RegistryImportSelectionPage(getFileFilter());
    }

    
    @Override
    public String[] doImport(String project, URL location, String defaultUri, IProgressMonitor monitor) throws OntologyImportException {
        //ImportExportControl control = new ImportExportControl();
        ImportOntology control = new ImportOntology();
        ProgressListener pl = null;
        if(monitor != null) {
        	//monitor.beginTask(Messages.getString("FileSystemImportWizard.3"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
        	pl = new ImportProgressListener("", monitor);
        }
        String[] s = null;
        try {
        		//s = control.importFileSystem(project, location, defaultUri, pl);
        	
        		s = control.importFileSystem(project, location, "test", pl);
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
    
    

    @Override
    public boolean performFinish() {
        final String projectName = _pageSelection.getSelectedProject();
        //      final URL fileName = pageSelection.getSelectedURL();
        final URL[] urls = _pageSelection.getSelectedURLS();
        
        IRunnableWithProgress op = new IRunnableWithProgress() {

            public void run(IProgressMonitor monitor) throws InvocationTargetException {
                try {
                    //_urls2ontologies = new HashMap();
                    final IProgressMonitor mon = monitor;
                    mon.beginTask(Messages.getString("FileSystemImportWizard.3"), -1); //$NON-NLS-1$
                    for (int i = 0; i < urls.length; i++) {
                        URL url = urls[i];
                        if (url != null) {
                            try {
                                doFinish(projectName, url, mon);
                            } catch (OntologyImportException e) {
                                OntoStudioExceptionHandler handler = new OntoStudioExceptionHandler();
                                handler.handleException(e, e.getCause(), getShell());
                            } catch (CoreException e) {
                                OntoStudioExceptionHandler handler = new OntoStudioExceptionHandler();
                                handler.handleException(Messages.getString("FileSystemImportWizard.4"), e, getShell()); //$NON-NLS-1$
                            }
                        }
                    }

//                    if(mon != null) {
//                    	mon.done();
//                    }
                } catch (Exception e) {
                    throw new InvocationTargetException(e);
                } finally {
//                	monitor.done();
                }
            }
        };
        try {
            IWizardContainer container = getContainer();
            container.run(true, true, op);
        } catch (InterruptedException e) {
            return false;
        } catch (InvocationTargetException e) {
            Throwable realException = e.getTargetException();
            OntoStudioExceptionHandler handler = new OntoStudioExceptionHandler();
            handler.handleException((String) null, realException, getShell());
            return false;
        }
        return true;
    }
    /*
    @Override
    public String getModule(URL location) throws KAON2Exception {

    	String logicalURI = "";
    	
    	if (location.getProtocol().equals("file")) {
    		String fileName = location.getFile();
    		String physicalURI = new File(fileName).toURI().toString();
    		logicalURI = KAON2Manager.getOntologyURI(physicalURI, null);
    	} else {
    		logicalURI = KAON2Manager.getOntologyURI(location.toString(), null); 
    	}
    	
        return StringRepresentation.toString(IDTermUtil.uriToTerm(logicalURI));
    }
    */


	@Override
	public String getModule(URL location, String projectName) throws KAON2Exception {
		// TODO Auto-generated method stub
		String logicalURI = "";
    	
    	if (location.getProtocol().equals("file")) {
    		String fileName = location.getFile();
    		String physicalURI = new File(fileName).toURI().toString();
    		logicalURI = KAON2Manager.getOntologyURI(physicalURI, null);
    	} else {
    		logicalURI = KAON2Manager.getOntologyURI(location.toString(), null); 
    	}
    	
        //return StringRepresentation.toString(IDTermUtil.uriToTerm(logicalURI));
        return StringRepresentation.toString(URIUtilities.ontologyURIToModule(logicalURI));
	}
    
}
