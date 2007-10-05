package org.neon_toolkit.registry.ui.action;

//import java.io.ByteArrayInputStream;
//import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
//import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
//import org.semanticweb.kaon2.api.Ontology;
import org.neon_toolkit.registry.oyster2.ImportOntology;
import org.neon_toolkit.registry.ui.editor.ImportOntologyEditor;

//import org.semanticweb.kaon2.api.*;
//import org.semanticweb.kaon2.api.formatting.OntologyFormatting;
//import org.semanticweb.kaon2.api.owl.elements.*;
//import org.semanticweb.kaon2.api.OntologyFileFormat; //OLD VERSION
//import org.semanticweb.kaon2.api.formatting.*; 



public class ImportOntologyDocument extends Action implements IRunnableWithProgress {
	private String filterPath = "e:/O2ServerFiles";
	private String filename;
	//private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	//private String defURI = mOyster2.getPeerDescOntologyURI();
	//private Ontology localRegistry = mOyster2.getLocalHostOntology();
	//private KAON2Connection connection = mOyster2.getConnection();
	//private DefaultOntologyResolver resolver = mOyster2.getResolver();
	//private File localRegistryFile = mOyster2.getLocalRegistryFile();
	//private static final String acmTopic = "http://daml.umbc.edu/ontologies/topic-ont#ACMTopic/";
	//private String TopicURI;
	private List propertyList = new LinkedList();
	private ImportOntology IOntology = new ImportOntology();

	public ImportOntologyDocument() {
		setToolTipText("Import the shared ontolgy");
		this.setText("I&mport Ontology...");
		//window = w;
	}

	public void runWithEvent(Event event) {
		try {
			Shell activeShell = event.display.getActiveShell();
			FileDialog dialog = new FileDialog(activeShell, SWT.OPEN);
			dialog.setFilterPath(filterPath);
			filename = dialog.open();
			System.out.println(serializeFileName(filename));
			if (filename != null) {
				filterPath = dialog.getFilterPath();
				ProgressMonitorDialog progressBar = new ProgressMonitorDialog(activeShell);
				progressBar.run(true, false, this);
				
				//TEMP
				//String ontologyURI = resolver.registerOntology("file:///"+serializeFileName(filename));
				//connection.setOntologyResolver(resolver);
				//Ontology ontologyImported = connection.openOntology(ontologyURI,new HashMap<String,Object>());
				//Map<String,Set<String>> ontologyProperties=ontologyImported.getOntologyProperties();
				//System.out.println("The Properties size of '"+ontologyImported.getOntologyURI()+"' is: "+ontologyProperties.size());
				//END TEMP
				
				propertyList.clear();
				propertyList=IOntology.extractMetadata(filename);
				
				ImportOntologyEditor editor = new ImportOntologyEditor(activeShell,propertyList);
				editor.create();
				editor.open();
				if (editor.getReturnCode() == IDialogConstants.OK_ID) {
					propertyList.clear();
					propertyList = editor.getPropertyList();
					IOntology.addImportOntologyToRegistry(propertyList,0);
				}
			}
		} catch (InvocationTargetException e) {
			
			errorDialog("Import error1", e.getMessage());
		} catch (InterruptedException ie) {
			errorDialog("Import error2", ie.getMessage());
		} catch(Exception e){
			errorDialog("Import error", e.getMessage());
		}
	}
	
	private String serializeFileName(String filename){
		String seperator = System.getProperty("file.separator");
		filename = filename.replace(seperator.charAt(0),'/');
		return  filename;
	}	
	
	public void run(IProgressMonitor progressMonitor) throws InvocationTargetException, InterruptedException {
		
	}

	private void errorDialog(final String title, final String message) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(Display.getDefault().getActiveShell(), title, message);
			}
		});
	}
	
}
//0//
/*
if(prop.getPropertyName().equals(Constants.URI)){
	String uri = prop.getPropertyValue();
	String oldUri =(String) ontologyDocIndiv.getDataPropertyValue(localRegistry,ontologyURI);
	if(oldUri !=null){
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyURI,ontologyDocIndiv,oldUri),OntologyChangeEvent.ChangeType.REMOVE));
	}
	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyURI,ontologyDocIndiv,uri),OntologyChangeEvent.ChangeType.ADD));	
}
else if (prop.getPropertyName().equals(Constants.name)){
	String name = prop.getPropertyValue();
	String oldName = (String)ontologyDocIndiv.getDataPropertyValue(localRegistry,ontologyName);
	if(oldName !=null){
	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyName,ontologyDocIndiv,oldName),OntologyChangeEvent.ChangeType.REMOVE));
	}
	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyName,ontologyDocIndiv,name),OntologyChangeEvent.ChangeType.ADD));	
}
*/

//IMP//
/* NOT NECESSARY ANYMORE,IS IN GENERAL ELSE 
else if (prop.getPropertyName().equals(Constants.useImports)){ //
	String importsURI = prop.getPropertyValue();
	Individual importOntologyIndiv = KAON2Manager.factory().individual(importsURI);
	if(!localRegistry.containsAxiom(KAON2Manager.factory().classMember(ontologyConcept,importOntologyIndiv),true))
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(ontologyConcept,importOntologyIndiv),OntologyChangeEvent.ChangeType.ADD));
	if(!localRegistry.containsAxiom(KAON2Manager.factory().objectPropertyMember(useImports,ontologyIndividual,importOntologyIndiv),true))
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(useImports,ontologyIndividual,importOntologyIndiv),OntologyChangeEvent.ChangeType.ADD));	
}
*/