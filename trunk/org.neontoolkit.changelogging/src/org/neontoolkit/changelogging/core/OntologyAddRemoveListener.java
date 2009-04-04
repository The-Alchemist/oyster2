package org.neontoolkit.changelogging.core;

import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Display;
import org.neontoolkit.changelogging.core.flogic.FlogicChangeListener;
import org.neontoolkit.changelogging.core.owl.OWLChangeListener;
import org.neontoolkit.changelogging.gui.actions.Track;
import org.semanticweb.kaon2.api.KAON2Exception;
import org.semanticweb.kaon2.api.Ontology;

import com.ontoprise.ontostudio.datamodel.DatamodelPlugin;
import com.ontoprise.ontostudio.datamodel.api.IOntologyContainer;
import com.ontoprise.ontostudio.datamodel.event.OntologyModifiedListener;

public class OntologyAddRemoveListener implements OntologyModifiedListener {
	private static int totalOntologyNum = 0;
	private static boolean isInitialized = false;	

	public OntologyAddRemoveListener() {
		if(!isInitialized){
			try {
				String[] projects = DatamodelPlugin.getDefault().getOntologyProjects();
				for(int i=0;i<projects.length;i++){
					Set<Ontology> ontos = 
						DatamodelPlugin.getDefault().getKaon2Connection(projects[i]).getOntologies();
					totalOntologyNum += ontos.size();
				}
			} catch (CoreException e) {
				e.printStackTrace();
			} catch (KAON2Exception e) {
				e.printStackTrace();
			}
			isInitialized = true;
		}
	}



	public void dirtyChanged(IOntologyContainer container, String ontology, boolean dirty) {
		if(!dirty)
			return;
		int currentOntologyNum = 0;
		try {
			String[] projects = DatamodelPlugin.getDefault().getOntologyProjects();
			for(int i=0;i<projects.length;i++){
				Set<Ontology> ontos = 
					DatamodelPlugin.getDefault().getKaon2Connection(projects[i]).getOntologies();
				currentOntologyNum += ontos.size();
			}
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (KAON2Exception e) {
			e.printStackTrace();
		}
		
		if(currentOntologyNum > totalOntologyNum){			
			addOntologyListener(ontology);
		}else{
			removeOntologyListener(ontology);
		}
		totalOntologyNum = currentOntologyNum;
	}
	
	private void addOntologyListener(String ontology){
		ontology = ontology.replace("\"", "");
		ontology = ontology.replace("##", "#");
		try {
			String[] projects = DatamodelPlugin.getDefault().getOntologyProjects();
			for(int i=0;i<projects.length;i++){
				Set<Ontology> ontos = 
					DatamodelPlugin.getDefault().getKaon2Connection(projects[i]).getOntologies();
				for(Ontology onto : ontos){
					if(onto.getOntologyURI().equals(ontology)){
						if(DatamodelPlugin.getDefault().getProjectOntologyLanguage(
								projects[i]).equals("FLogic")){
							FlogicChangeListener listener = new FlogicChangeListener(onto);
							onto.addOntologyListener(listener);
							Track.FlogicList.put(onto, listener);
						}else if(DatamodelPlugin.getDefault().getProjectOntologyLanguage(
								projects[i]).equals("OWL")){
							
							OWLChangeListener listener = new OWLChangeListener(onto, null,false,"",projects[i], Display.getDefault().getActiveShell());
							onto.addOntologyListener(listener);
							Track.OWLList.put(onto, listener);
						}
					}
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (KAON2Exception e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void removeOntologyListener(String ontology){
		try {
			String[] projects = DatamodelPlugin.getDefault().getOntologyProjects();
			for(int i=0;i<projects.length;i++){
				Set<Ontology> ontos = 
					DatamodelPlugin.getDefault().getKaon2Connection(projects[i]).getOntologies();
				for(Ontology onto : ontos)
					if(onto.getOntologyURI().equals(ontology)){
						if(DatamodelPlugin.getDefault().getProjectOntologyLanguage(
								projects[i]).equals("OWL")){
							OWLChangeListener listener = Track.OWLList.get(onto);
							listener.persist();
						}
					}				
			}
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (KAON2Exception e) {
			e.printStackTrace();
		}
	}

}
