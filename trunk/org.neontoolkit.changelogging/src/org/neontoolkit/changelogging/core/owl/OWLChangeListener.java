package org.neontoolkit.changelogging.core.owl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.extensions.change.OMVChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;
import org.neontoolkit.registry.api.Oyster2Connection;
import org.semanticweb.kaon2.api.BulkChangeEvent;
import org.semanticweb.kaon2.api.Entity;
import org.semanticweb.kaon2.api.Namespaces;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.OntologyChangeEvent;
import org.semanticweb.kaon2.api.OntologyListener;
import org.semanticweb.kaon2.api.OntologyChangeEvent.ChangeType;
import org.semanticweb.kaon2.api.logic.Term;
import org.semanticweb.kaon2.api.owl.elements.Annotation;

public class OWLChangeListener implements OntologyListener {
	public static Oyster2Connection oyster2Conn = StartRegistry.connection;//null;
	private Ontology monitoredOnto = null;
	private OMVOntology omvOnto = null;
	private boolean isChanged = false;
	private List<OMVAtomicChange> changeList = new ArrayList<OMVAtomicChange>();
	private ExecutorService executor = Executors.newSingleThreadExecutor();

	
	public OWLChangeListener(Ontology onto, OMVOntology o){
		monitoredOnto = onto;
		omvOnto=o;
	}
	
	public Ontology getMonitoredOntology(){
		return monitoredOnto;
	}
	
	public static boolean startOyster(){			
		if(oyster2Conn == null)
			return false;
		return true;
	}

	public static void stopOyster(){
		//for(OMVOntology omv : omvOntoList)
		//	oyster2Conn.stopTracking(omv);
	}

	public List<OMVChange> getChanges(){
		List<OMVChange> changes = new ArrayList<OMVChange>();
	
		if(oyster2Conn != null){
			//for(int i=changeList.size()-1; i>=0; i--)
			//	changes.add(changeList.get(i));
			changes.addAll(oyster2Conn.getChanges(omvOnto, null));
		}
		return changes;
	}
	
	
	public void ontologyUpdated(Ontology changedOnto, Ontology sourceOnto,
			List<OntologyChangeEvent> changes, Set<Entity> added, Set<Entity> removed) {
		isChanged = true;
		
		for (int i=0;i<changes.size();i++){
			ChangeType changeType = changes.get(i).getChangeType();
			StringBuffer sb = new StringBuffer();
			changes.get(i).getAxiom().toString(sb, new Namespaces());
			sb.deleteCharAt(sb.indexOf("["));
			sb.deleteCharAt(sb.indexOf("]"));
			
			List<String> args = new ArrayList<String>();
			while(sb.length()>0){
				if(sb.indexOf(" ") != -1){
					String arg = sb.substring(0, sb.indexOf(" "));
					sb.delete(0, sb.indexOf(" ")+1);
					args.add(arg);
				}else{
					String arg = sb.toString();
					sb.delete(0, sb.length());
					args.add(arg);
				}
			}
			ThreadRunner tr = new ThreadRunner(changeType, args, omvOnto, changedOnto);
			executor.execute(tr); 			//applyChange(changeType, args);
		}
	}
	
	
	
	public boolean hasChanges(){
		return isChanged;
	}
	
	public void persist(){			// persist the changes on the monitored ontology
		for(OMVAtomicChange change : changeList){
			change.setAppliedToOntology(omvOnto);
			//oyster2Conn.register(change);
		}
	}
	
	public void ontologyChangedDrastically(Ontology arg0) {
		System.out.println("ontologyChangedDrastically: " + arg0.getOntologyURI());		
	}

	public void ontologyClosed(Ontology arg0) {
		// TODO Auto-generated method stub
		System.out.println("ontologyClosed: " + arg0.getOntologyURI());
	}

	public void ontologyImportUpdated(Ontology arg0, Ontology arg1,
			ChangeType arg2, String arg3) {
		
		System.out.println("ontologyImportUpdated: " + arg0.getOntologyURI() + 
				" " + arg1.getOntologyURI() + " " + arg2.toString()+ " " +arg3);
	}
	
	public void ontologyAnnotationUpdated(Ontology arg0, Ontology arg1,
			ChangeType arg2, Annotation arg3) {
		// TODO Auto-generated method stub
		System.out.println("ontologyAnnotationUpdated: " + arg0.getOntologyURI() + 
				" " + arg1.getOntologyURI() + " " + arg2.name() + " " + arg3.toString());
	}
	
	public void ontologyAllAxiomsRemoved(Ontology arg0, Ontology arg1) {
		// TODO Auto-generated method stub
		System.out.println("ontologyAllAxiomsRemoved: " + arg0.getOntologyURI() + 
				" " + arg1.getOntologyURI());
	}

	public void ontologyBulkUpdated(Ontology arg0, Ontology arg1,
			BulkChangeEvent arg2) {
		isChanged = true;
		System.out.println("ontologyBulkUpdated: " + arg0.getOntologyURI() + 
				" " + arg1.getOntologyURI() + " " + arg2.toString());
		
	}

	public void ontologyTermReplaced(Ontology arg0, Ontology arg1, Term arg2,
			Term arg3) {
		// TODO Auto-generated method stub
		System.out.println("ontologyTermReplaced: " + arg0.getOntologyURI() + 
				" " + arg1.getOntologyURI() + " " + arg2.toString());
	}

}

//private static List<OMVOntology> omvOntoList = new ArrayList<OMVOntology>();


//if(oyster2Conn != null){
//	omvOnto = new OMVOntology();
//	omvOnto.setURI(monitoredOnto.getOntologyURI());
//	omvOnto.setResourceLocator("");//monitoredOnto.getPhysicalURI());
//	oyster2Conn.startTracking(omvOnto);
//}
//omvOnto.addName(Namespaces.guessLocalName(monitoredOnto.getOntologyURI()));
//omvOntoList.add(omvOnto);


/*
if(args.get(0).equals(Constants.ACTION_CLASS)){
	if(args.get(2).equals(Constants.OWL_THINGS)){ // create or remove an OWLClass
		Declaration declaration = new Declaration();
		declaration.setEntity(args.get(1));
		atomicChange.setAppliedAxiom(declaration);
		changeList.add(atomicChange);
	}else{										  // create or remove a subClassOf relation
		SubClassOf sco = new SubClassOf();
		sco.setSubClass(args.get(1));
		sco.setSuperClass(args.get(2));
		atomicChange.setAppliedAxiom(sco);
		changeList.add(atomicChange);
	}			
}
*/