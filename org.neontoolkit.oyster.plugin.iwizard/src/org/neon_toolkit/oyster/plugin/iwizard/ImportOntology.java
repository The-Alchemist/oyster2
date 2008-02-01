package org.neon_toolkit.oyster.plugin.iwizard;

import java.io.File;
import java.net.URL;

import org.semanticweb.kaon2.api.KAON2Connection;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.ProgressListener;

import com.ontoprise.ontostudio.datamodel.api.IOntologyContainer;
import com.ontoprise.ontostudio.gui.control.Control;
import com.ontoprise.ontostudio.script.ProcedureCallLogger;

public class ImportOntology extends Control {

	public ImportOntology() {}
	
    public String[] importFileSystem(String projectName, URL location, String defaultUri, ProgressListener listener) throws Exception {

        IOntologyContainer container = getContainer(projectName);
        KAON2Connection connection = container.getConnection();
        Ontology mod = null;
        
        //System.out.println("here in importFileSystem"+projectName+" "+defaultUri+" "+location);
    	if (location.getProtocol().equals("file")) {

    		ProcedureCallLogger.log(getClass().getName(), "importFileSystem", 
	        		new Class[] {String.class, String.class, String.class, Object.class}, 
	        		new Object[] {projectName, location.getFile(), defaultUri, null});
       
    		String physicalURI = new File(location.getFile()).toURI().toString();
    		mod = connection.openOntology(physicalURI, null, listener);
    		
    	} else {
    		
    		ProcedureCallLogger.log(getClass().getName(), "importURLSystem", 
	        		new Class[] {String.class, String.class, String.class, Object.class}, 
	        		new Object[] {projectName, location.toString(), defaultUri, null});
    		
    		mod = connection.openOntology(location.toString(), null, listener);
    	}
                
        return new String[]{mod.getModule().toString()};
    }
}
