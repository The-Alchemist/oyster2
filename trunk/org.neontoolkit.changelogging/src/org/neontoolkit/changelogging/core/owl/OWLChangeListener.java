package org.neontoolkit.changelogging.core.owl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.eclipse.swt.widgets.Shell;
import org.neontoolkit.changelogging.menu.Track;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.extensions.change.OMVChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;
import org.neontoolkit.registry.api.Oyster2Connection;
import org.semanticweb.kaon2.api.Axiom;
import org.semanticweb.kaon2.api.BulkChangeEvent;
import org.semanticweb.kaon2.api.Entity;
import org.semanticweb.kaon2.api.Namespaces;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.OntologyChangeEvent;
import org.semanticweb.kaon2.api.OntologyListener;
import org.semanticweb.kaon2.api.OntologyChangeEvent.ChangeType;
import org.semanticweb.kaon2.api.logic.Term;
import org.semanticweb.kaon2.api.owl.elements.Annotation;
import com.ontoprise.ontostudio.gui.GuiPlugin;

public class OWLChangeListener implements OntologyListener {
	public static Oyster2Connection oyster2Conn = StartRegistry.connection;//null;
	private Ontology monitoredOnto = null;
	private OMVOntology omvOnto = null;
	private boolean isChanged = false;
	private List<OMVAtomicChange> changeList = new ArrayList<OMVAtomicChange>();
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private Shell shell;
	private boolean isCollab;
	//private String project;
    //private String moduleId;
	
	public OWLChangeListener(Ontology onto, OMVOntology o, boolean c, String m, String p){
		monitoredOnto = onto;
		omvOnto=o;
		shell = GuiPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
		isCollab=c;
		//moduleId=m;
		//project=p;
	}
	
	public Ontology getMonitoredOntology(){
		return monitoredOnto;
	}

	public List<OMVChange> getChanges(){
		List<OMVChange> changes = new ArrayList<OMVChange>();
	
		if(oyster2Conn != null){
			changes.addAll(oyster2Conn.getChanges(omvOnto, null));
			Collections.reverse(changes);
		}
		return changes;
	}
		
	public void ontologyUpdated(Ontology changedOnto, Ontology sourceOnto,
			List<OntologyChangeEvent> changes, Set<Entity> added, Set<Entity> removed) {
		String selElement = Track.getSelectedElement();
		boolean itIs=false;
		isChanged = true;
		
		//System.out.println("size: "+changes.size()+ " selected element "+selElement);
		if (isCollab && changes.size()>1) {
			StringBuffer sbmm = new StringBuffer();
			for (OntologyChangeEvent mm : changes){
				Axiom axmm = mm.getAxiom();
				axmm.toString(sbmm, new Namespaces());
				if (sbmm.indexOf(selElement)>0) itIs=true;
			}
		}
		
		for (int i=0;i<changes.size();i++){
			ChangeType changeType = changes.get(i).getChangeType();
			StringBuffer sb = new StringBuffer();
			Axiom ax = changes.get(i).getAxiom();
			
			ax.toString(sb, new Namespaces());
			sb.deleteCharAt(sb.indexOf("["));
			sb.deleteCharAt(sb.indexOf("]"));
			
			if (sb.indexOf("\"")>0){
				int start = sb.indexOf("\"");
				if (sb.indexOf("\"", start+1)>0){
					int end = sb.indexOf("\"", start+1);
					String value = sb.substring(start+1, end);
					value = value.replace(" ", "%20");
					sb.replace(start, end+1, value);
				}
			}
			
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
			//System.out.println("going to check this axiom: "+getSerial(args));
			boolean doIt=false;
			if (isCollab){
				if (args.size()>1 && args.get(1) != null && args.get(1).equalsIgnoreCase(selElement)) doIt=true;
				else if (args.size()>2 && args.get(2) != null && args.get(2).equalsIgnoreCase(selElement)) doIt=true;
				else if (args.size()>3 && args.get(3) != null && args.get(3).equalsIgnoreCase(selElement)) doIt=true;
				else if (args.size()>4 && args.get(4) != null && args.get(4).equalsIgnoreCase(selElement)) doIt=true;
				else if (Track.getAddedIndividual()) {doIt=true;Track.resetAddedIndividual();}
				else if (changes.size()>1) {if (itIs) doIt=true;}
			}
			else doIt=true;
			
			if (doIt){				
				ThreadRunner tr = new ThreadRunner(changeType, args, omvOnto, changedOnto, shell);
				executor.execute(tr); 			//applyChange(changeType, args);
			}
			else{
				System.out.println("didnt log this change: "+getSerial(args)+ " elementselected had: "+selElement);
			}
		}
	}
	
	private String getSerial(List<String> args){
		String see="";
		for (String as : args) see+=" "+as;
		return see;
	}
	
	public boolean hasChanges(){
		return isChanged;
	}
	
	public void persist(){	// persist the changes on the monitored ontology
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

/*

try {
if(changedOnto.containsAxiom(ax,true)){
	System.out.println("ya esta");
	return;
}
} catch (KAON2Exception e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
*/