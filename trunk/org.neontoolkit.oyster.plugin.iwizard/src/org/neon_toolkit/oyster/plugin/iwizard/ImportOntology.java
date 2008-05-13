package org.neon_toolkit.oyster.plugin.iwizard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.semanticweb.kaon2.api.KAON2Connection;
import org.semanticweb.kaon2.api.KAON2Exception;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Namespaces;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.ProgressListener;
import com.ontoprise.api.StringRepresentation;
import com.ontoprise.collab.client.OntologyImportHelper;
import com.ontoprise.ontostudio.datamodel.DatamodelPlugin;
import com.ontoprise.ontostudio.datamodel.api.IOntologyContainer;
import com.ontoprise.ontostudio.exception.OntoStudioExceptionHandler;
import com.ontoprise.ontostudio.gui.control.Control;
import com.ontoprise.ontostudio.gui.navigator.module.ModuleControl;
import com.ontoprise.ontostudio.script.ProcedureCallLogger;
import com.ontoprise.util.URIUtilities;

public class ImportOntology extends Control {

	public ImportOntology() {}
	
    public String[] importFileSystem(String projectName, URL location, String physicalUri, ProgressListener listener) throws Exception {    	    	
    	ProcedureCallLogger.log(getClass().getName(), "importFileSystem",  //$NON-NLS-1$
                new Class[] {String.class, String.class, Object.class}, 
                new Object[] {projectName, physicalUri, null});
        List<String> importedModules = new ArrayList<String>();
        URI uri = new URI(physicalUri);
        IOntologyContainer container = getContainer(projectName);
        KAON2Connection connection = container.getConnection();

    	//check which Ontology Format the file is
        //String ontologyFormat =KAON2Manager.getFormatName(physicalUri, null);
        
        //check for merge
        String moduleToImport = getModule(physicalUri, projectName);
        String ontologyURI = URIUtilities.moduleToOntologyURI(getTerm(moduleToImport));
        boolean isMerge = container.existsModule(getTerm(moduleToImport));        
        
        if(isMerge) {
	        container.getConnection().getOntology(ontologyURI).importContentsFrom(physicalUri, listener, null); //container.getConnection().getOntology(ontologyURI).importContentsFrom(physicalUri, listener);
    		importedModules.add(moduleToImport);
        	ModuleControl.getDefault().addModuleToProject(moduleToImport, projectName);
        } else {
            if(!container.isPersistent()) {
        		//if Project is RAM based than copy file into project workspace
            	if (location.getProtocol().equals("file")) {
            		physicalUri = copyOntologyFileToProject(uri, ontologyURI, projectName).toString();
            	}
            	else{
            		physicalUri = copyOntologyURLToProject(uri, ontologyURI, projectName).toString();
            	}
            }
            
            Set<Ontology> ontos = OntologyImportHelper.importOntologies(connection, new String[] {physicalUri}, listener);
            if (!ontos.isEmpty()) {
        		Iterator ontosIter = ontos.iterator();
        		while (ontosIter.hasNext()) {
					Ontology onto = (Ontology) ontosIter.next();
		        	ModuleControl.getDefault().addModuleToProject(StringRepresentation.toString(onto.getModule()), projectName);
		        	importedModules.add(StringRepresentation.toString(onto.getModule()));
				}        	
            }
        }
        return importedModules.toArray(new String[0]);
    	
    }
    
    /**
	 * copies an ontology file to the project folder
	 * @param physicalUri
	 * @param module
	 * @param project
	 */
	public URI copyOntologyFileToProject(URI physicalUri, String ontologyURI, String projectName) {
		try {	        
	    	URI targetUri = physicalUri;
	        if(!isFileInWorkspace(physicalUri.toString(), projectName)){
	    		//ontology doesn't come from the project
	    		//ontology has to be copied to the project workspace
		        try {
		        	IFile file = getNewOntologyFile(physicalUri, projectName, false);
			        targetUri = file.getLocationURI();
			        InputStream is = physicalUri.toURL().openStream();
		        	file.create(is, true, null);
		        	is.close();
				} catch (FileNotFoundException e) {
					new OntoStudioExceptionHandler().handleException(e);
				} catch (IOException e) {
					new OntoStudioExceptionHandler().handleException(e);
				} catch (KAON2Exception e) {
					new OntoStudioExceptionHandler().handleException(e);
				}
	    	}
	        return targetUri;
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return physicalUri;		
	}
	
	/**
	 * copies an ontology file to the project folder
	 * @param physicalUri
	 * @param module
	 * @param project
	 */
	public URI copyOntologyURLToProject(URI physicalUri, String ontologyURI, String projectName) {
		try {	        
	    	URI targetUri = physicalUri;
	        if(!isFileInWorkspace(physicalUri.toString(), projectName)){
	    		//ontology doesn't come from the project
	    		//ontology has to be copied to the project workspace
		        try {
		        	URI pUri = URI.create("file:////" + Namespaces.guessLocalName(physicalUri.toString()));
		        	IFile file = getNewOntologyFile(pUri, projectName, false);
			        targetUri = file.getLocationURI();
			        InputStream is = physicalUri.toURL().openStream();
		        	file.create(is, true, null);
		        	is.close();
				} catch (FileNotFoundException e) {
					new OntoStudioExceptionHandler().handleException(e);
				} catch (IOException e) {
					new OntoStudioExceptionHandler().handleException(e);
				} catch (KAON2Exception e) {
					new OntoStudioExceptionHandler().handleException(e);
				}
	    	}
	        return targetUri;
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return physicalUri;
		
	}
	
	private boolean isFileInWorkspace(String physicalUri, String projectName) {
        IProject project = DatamodelPlugin.getDefault().getProject(projectName);
        URI projectUri = project.getLocationURI();
        return physicalUri.startsWith(projectUri.toString());
	}
	
	private IFile getNewOntologyFile(URI physicalUri, String projectName, boolean autoExtension) throws KAON2Exception, CoreException {
        IProject project = DatamodelPlugin.getDefault().getProject(projectName);
        String fileName =  new File(physicalUri).getName();
        String extension = null;
        if(!autoExtension && fileName.contains(".")) {
        	extension = fileName.substring(fileName.lastIndexOf("."));
        }
        fileName = DatamodelPlugin.getDefault().getNewOntologyFilename(projectName, fileName, extension);
    	return project.getFile(fileName);    		
	}
	
	/**
     * 
     * @param projectName TODO
     * @param location physical URL of an ontology
     * @return the string representation of the module-term
     * @throws KAON2Exception
     */
    public String getModule(String uri, String projectName) throws KAON2Exception, URISyntaxException {
        String physicalURI = new URI(uri).toString();        
        String logicalURI = KAON2Manager.getOntologyURI(physicalURI, null);
        return StringRepresentation.toString(URIUtilities.ontologyURIToModule(logicalURI));
    }
    
}