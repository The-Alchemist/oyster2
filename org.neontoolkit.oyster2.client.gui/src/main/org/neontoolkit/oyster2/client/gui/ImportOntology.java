package org.neontoolkit.oyster2.client.gui;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.semanticweb.kaon2.api.ProgressListener;

import com.ontoprise.ontostudio.datamodel.DatamodelPlugin;
import com.ontoprise.ontostudio.gui.control.Control;
import com.ontoprise.ontostudio.io.ImportExportControl;

public class ImportOntology extends Control {

	public ImportOntology() {}
	
	public String[] importOntologies(String projectName, String[] location, ProgressListener listener) throws Exception {
		URI []uris = new URI[location.length];
		IProject project = DatamodelPlugin.getDefault().getProject(projectName);
		List<File> newFiles = new LinkedList<File>();
		//File file = null;
		URL url = null;
		for (int i = 0; i<location.length;i++) {
			url = new URL(location[i]);
			if (url.getProtocol().equals("file")) {
				uris[i] = url.toURI();
			}
			else {
				
		        String filename =  url.getFile().substring(1);
		        
		        filename = DatamodelPlugin.getDefault().getNewOntologyFilename(projectName, filename,
		        		filename.substring(filename.length()-3,filename.length()));
				
		        IFile file = project.getFile(filename);
		        
		        
				//file = File.createTempFile("importOntology", url.getFile().substring(1));
				//OutputStream out = new FileOutputStream(file);
				InputStream in = url.openStream();
				file.create(in, true, null);
				//copy(in,out);
				uris[i] = file.getLocationURI();
				in.close();
				
				newFiles.add(new File(file.getLocationURI()));
			}
		}
		ImportExportControl control = new ImportExportControl();
		String [] result = control.importFileSystem(projectName, uris, listener);
		for (File tempFile : newFiles) {
			tempFile.delete();
		}
		return result;
	}
	
	   
}