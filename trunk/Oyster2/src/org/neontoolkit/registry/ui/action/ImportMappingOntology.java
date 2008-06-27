package org.neontoolkit.registry.ui.action;

//import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.neontoolkit.registry.oyster2.Constants;
import org.neontoolkit.registry.oyster2.OntologyProperty;
import org.neontoolkit.registry.oyster2.Oyster2Factory;
import org.neontoolkit.registry.ui.editor.ImportMappingEditor;
//import org.neon_toolkit.registry.util.Utilities;
//import org.semanticweb.kaon2.api.formatting.*;
import org.semanticweb.kaon2.api.DefaultOntologyResolver;
import org.semanticweb.kaon2.api.KAON2Connection;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Namespaces;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.OntologyChangeEvent;
//import org.semanticweb.kaon2.api.OntologyFileFormat;
import org.semanticweb.kaon2.api.owl.elements.DataProperty;
import org.semanticweb.kaon2.api.owl.elements.Individual;
import org.semanticweb.kaon2.api.owl.elements.OWLClass;
import org.semanticweb.kaon2.api.owl.elements.ObjectProperty;


public class ImportMappingOntology extends Action implements IRunnableWithProgress{
	private String filterPath = "C:/GuoRui/kaonserver";
	private String filename;
	private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private String defURI = mOyster2.getPeerDescOntologyURI();
	private Ontology localRegistry = mOyster2.getLocalHostOntology();
	private KAON2Connection connection = mOyster2.getConnection();
	private DefaultOntologyResolver resolver = mOyster2.getResolver();
	//private File localRegistryFile = mOyster2.getLocalRegistryFile();
	//private static final String acmTopic = "http://daml.umbc.edu/ontologies/topic-ont#ACMTopic/";
	private List propertyList = new LinkedList();

	public ImportMappingOntology() {
		
		setToolTipText("Import the shared mapping");
		this.setText("I&mport Mapping...");
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
				String mappingURI = "";
				try{
					mappingURI = resolver.registerOntology("file:///"+serializeFileName(filename));
					
				}catch(Exception e){
					System.out.println(e + "register!");
				}
				connection.setOntologyResolver(resolver);
				Ontology ontologyImported = connection.openOntology(mappingURI,new HashMap<String,Object>());
				OntologyProperty prop = new OntologyProperty(Constants.URI, mappingURI);
				propertyList.clear();
				propertyList.add(prop);
				prop = new OntologyProperty(Constants.name, Namespaces.guessLocalName(serializeFileName(filename)));
				propertyList.add(prop);
				Collection Col = ontologyImported.getImportedOntologies();
				Iterator it = Col.iterator();
				while(it.hasNext()){
					Ontology io = (Ontology)it.next();
					OntologyProperty op = new OntologyProperty(Constants.IMPORT, io.getOntologyURI());
					propertyList.add(op);
				}
				/*
				prop = new OntologyProperty(Constants.MappingSource,"");
				propertyList.add(prop);
				prop = new OntologyProperty(Constants.MappingTarget,"");
				propertyList.add(prop);
				*/
				ImportMappingEditor editor = new ImportMappingEditor(activeShell,propertyList);
				editor.create();
				editor.open();
				if (editor.getReturnCode() == IDialogConstants.OK_ID) {
					propertyList.clear();
					propertyList = editor.getPropertyList();
					addImportMappingToRegistry(localRegistry,propertyList);
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
	private void addImportMappingToRegistry(Ontology localregistry, List propertyList){
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		OWLClass OntologyDoc = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.ontologyConcept);
		OWLClass Mapping = KAON2Manager.factory().owlClass(defURI+"#Mapping");
		DataProperty ontologyURL = KAON2Manager.factory().dataProperty(Constants.OMVURI + Constants.URI);
    	ObjectProperty imports = KAON2Manager.factory().objectProperty(Constants.OMVURI + Constants.useImports);
    	ObjectProperty mappingSource = KAON2Manager.factory().objectProperty(defURI + "#mappingSource");
		ObjectProperty mappingTarget = KAON2Manager.factory().objectProperty(defURI + "#mappingTarget");
    	ObjectProperty mappingProvider = KAON2Manager.factory().objectProperty(defURI + "#mappingProvider");
    	ObjectProperty provideOntology = KAON2Manager.factory().objectProperty(defURI + "#provideOntology");
    	Individual mappingProviderIndiv = mOyster2.getLocalAdvertInformer().getLocalPeer();
    	Individual mappingIndiv = null;
		Individual mappingSourceIndiv = null;
    	Individual mappingTargetIndiv = null; 
		Iterator it1 = propertyList.iterator();
		while(it1.hasNext()){
			OntologyProperty prop = (OntologyProperty)it1.next();
			if(prop.getPropertyName().equals(Constants.URI)){
				String uri = prop.getPropertyValue();
				mappingIndiv = KAON2Manager.factory().individual(uri);
			}
			/*
			else if(prop.getPropertyName().equals(Constants.MappingSource)){
				String uri = prop.getPropertyValue();
				mappingSourceIndiv = KAON2Manager.factory().individual(uri);
			}
			else if(prop.getPropertyName().equals(Constants.MappingTarget)){
				String uri = prop.getPropertyValue();
				mappingTargetIndiv = KAON2Manager.factory().individual(uri);
			}
			*/
		}
		try{
		if(localRegistry.containsEntity(mappingIndiv,true)){
			errorDialog("import error:","The importing Mapping already exist!");
		}
		else changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(Mapping,mappingIndiv),OntologyChangeEvent.ChangeType.ADD));
		if(!localRegistry.containsAxiom(KAON2Manager.factory().classMember(OntologyDoc,mappingSourceIndiv),true))
			changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(OntologyDoc,mappingSourceIndiv),OntologyChangeEvent.ChangeType.ADD));
		if(!localRegistry.containsAxiom(KAON2Manager.factory().classMember(OntologyDoc,mappingTargetIndiv),true))
			changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(OntologyDoc,mappingTargetIndiv),OntologyChangeEvent.ChangeType.ADD));
		
		Iterator it2 = propertyList.iterator();
		while(it2.hasNext()){
			OntologyProperty prop = (OntologyProperty)it2.next();
			if(prop.getPropertyName().equals(Constants.URI)){
				String uri = prop.getPropertyValue();
				String oldUri = org.neontoolkit.registry.util.Utilities.getString(mappingIndiv.getDataPropertyValue(localRegistry,ontologyURL)); //(String)
				if(oldUri !=null){
					changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyURL,mappingIndiv,KAON2Manager.factory().constant(oldUri)),OntologyChangeEvent.ChangeType.REMOVE));
				}
				changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyURL,mappingIndiv,KAON2Manager.factory().constant(uri)),OntologyChangeEvent.ChangeType.ADD));	
			}	
			else if (prop.getPropertyName().equals(Constants.IMPORT)){
				String importsURI = prop.getPropertyValue();
				Individual importOntologyIndiv = KAON2Manager.factory().individual(importsURI);
				if(!localRegistry.containsEntity(importOntologyIndiv,true))
				changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(OntologyDoc,importOntologyIndiv),OntologyChangeEvent.ChangeType.ADD));
				if(!localRegistry.containsAxiom(KAON2Manager.factory().objectPropertyMember(imports,mappingIndiv,importOntologyIndiv),true))
					changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(imports,mappingIndiv,importOntologyIndiv),OntologyChangeEvent.ChangeType.ADD));	
			}
			/*
			else if (prop.getPropertyName().equals(Constants.MappingSource)){
				Individual oldSourceIndiv = mappingIndiv.getObjectPropertyValue(localRegistry,mappingSource);
				if(oldSourceIndiv !=null){
					changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(mappingSource,mappingIndiv,oldSourceIndiv),OntologyChangeEvent.ChangeType.REMOVE));
				}
				changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(mappingSource,mappingIndiv,mappingSourceIndiv),OntologyChangeEvent.ChangeType.ADD));	
			}	
			else if (prop.getPropertyName().equals(Constants.MappingTarget)){
				Individual oldTargetIndiv = mappingIndiv.getObjectPropertyValue(localRegistry,mappingTarget);
				if(oldTargetIndiv != null)
					changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(mappingTarget,mappingIndiv,mappingTargetIndiv),OntologyChangeEvent.ChangeType.REMOVE));	
				changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(mappingTarget,mappingIndiv,mappingTargetIndiv),OntologyChangeEvent.ChangeType.ADD));	
			}
			*/
		}	
		
		if(!localRegistry.containsAxiom(KAON2Manager.factory().objectPropertyMember(mappingProvider,mappingIndiv,mappingProviderIndiv),true))
			changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(mappingProvider,mappingIndiv,mappingProviderIndiv),OntologyChangeEvent.ChangeType.ADD));	
		if(!localRegistry.containsAxiom(KAON2Manager.factory().objectPropertyMember(provideOntology,mappingProviderIndiv,mappingIndiv),true))
			changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(provideOntology,mappingProviderIndiv,mappingIndiv),OntologyChangeEvent.ChangeType.ADD));
	    localRegistry.applyChanges(changes);
	    localRegistry.persist();
	    //localRegistry.saveOntology(OntologyFileFormat.OWL_RDF,localRegistryFile,"ISO-8859-1");
			
		}catch(Exception e){
			errorDialog("add ontologyDoc to registry error",e.getMessage());
		}
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
