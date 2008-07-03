package org.neontoolkit.oyster.plugin.iwizard;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;
import org.semanticweb.kaon2.api.KAON2Exception;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.ProgressListener;
import com.ontoprise.api.StringRepresentation;
import com.ontoprise.extensionalDB.ModuleAlreadyExistsException;
import com.ontoprise.ontostudio.exception.OntoStudioExceptionHandler;
import com.ontoprise.ontostudio.gui.navigator.IProjectElement;
import com.ontoprise.ontostudio.io.Messages;
import com.ontoprise.ontostudio.io.OntologyImportException;
import com.ontoprise.ontostudio.io.wizard.AbstractImportSelectionPage;
import com.ontoprise.ontostudio.io.wizard.AbstractImportWizard;
import com.ontoprise.ontostudio.io.wizard.ImportProgressListener;
import com.ontoprise.util.URIUtilities;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;

public class RegistryImportWizard extends AbstractImportWizard {
	static RegistryActivator conn = RegistryActivator.getDefault();
	private RegistryConditionPage p1;
	private RegistrySummaryPage p2;
	//constructor
    public RegistryImportWizard() {
        super();
        setWindowTitle("Oyster Import Wizard");
        if (!StartRegistry.success || !StartRegistry.getState()) {
            p1.getWizard().performCancel();
        }
    }
    @Override
    public boolean performCancel() {
        return true;
    }
    
    @Override
    public void addPages() {
    	p1= new RegistryConditionPage();
    	p2 = new RegistrySummaryPage();
    	
        addPage(p1);
        addPage(p2);
        addImportSelectionPage();
        if (_selection != null) {
            if (_selection instanceof IProject) {
                _pageSelection.setSelectedProject(((IProject) _selection).getName());
            } else if (_selection instanceof IProjectElement) {
                _pageSelection.setSelectedProject(((IProjectElement) _selection).getProjectName());
            }
        }
        if (_preselectedProject != null) {
            _pageSelection.setSelectedProject(_preselectedProject);
        }
        
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
	public String[] doImport(String project, URI[] physicalURIs, IProgressMonitor monitor) throws OntologyImportException {
		// TODO Auto-generated method stub
		return null;
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
        	String uri = location.toURI().toString();
            if (uri.startsWith("file://") && !uri.startsWith("file:////")) {
            	uri = "file:////" + uri.substring(7);
            }
			//s = control.importFileSystem(project, uri, pl);
        	s = control.importFileSystem(project, location, uri, pl);
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
        
        if (urls.length==0) return false;
        if (urls.length==1 && urls[0].getFile().equalsIgnoreCase("/F:/eclipse-SDK-3.2.1-win32/eclipse")) return false;
        
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
                } catch (Exception e) {
                    throw new InvocationTargetException(e);
                } finally {
                	monitor.done();
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