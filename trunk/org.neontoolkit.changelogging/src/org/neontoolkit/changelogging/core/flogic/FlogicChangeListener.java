package org.neontoolkit.changelogging.core.flogic;

import java.util.List;
import java.util.Set;

import org.semanticweb.kaon2.api.BulkChangeEvent;
import org.semanticweb.kaon2.api.Entity;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.OntologyChangeEvent;
import org.semanticweb.kaon2.api.OntologyListener;
import org.semanticweb.kaon2.api.BulkChangeEvent.BulkChangeType;
import org.semanticweb.kaon2.api.OntologyChangeEvent.ChangeType;
import org.semanticweb.kaon2.api.logic.FactFormula;
import org.semanticweb.kaon2.api.logic.Term;
import org.semanticweb.kaon2.api.owl.elements.Annotation;

public class FlogicChangeListener implements OntologyListener {
	private ChangeLogger changeLogger;
	private Ontology monitoredOnto;
	private boolean isChanged = false;
//	private Boolean lastChangeLogged = false;

	public FlogicChangeListener(Ontology onto) throws Exception {
		monitoredOnto = onto;
		changeLogger = new ChangeLogger(monitoredOnto);		
	}
	
	public Ontology getMonitoredOntology(){
		return monitoredOnto;
	}
	
	public ChangeLogger getChangeLogger(){
		return changeLogger;
	}

	public void ontologyAllAxiomsRemoved(Ontology arg0, Ontology arg1) {
		// TODO Auto-generated method stub

	}

	public void ontologyAnnotationUpdated(Ontology arg0, Ontology arg1,
			ChangeType arg2, Annotation arg3) {
		// TODO Auto-generated method stub

	}

	public void ontologyBulkUpdated(Ontology arg0, Ontology arg1,
			BulkChangeEvent arg2) {
		List<FactFormula> list_facts=arg2.getUpdateFormulae();
		BulkChangeType changetype=arg2.getChangeType();
		changeLogger.add(changetype, list_facts);
		isChanged = true;
	}

	public void ontologyChangedDrastically(Ontology arg0) {
		// TODO Auto-generated method stub

	}

	public void ontologyClosed(Ontology arg0) {
		// TODO Auto-generated method stub

	}

	public void ontologyImportUpdated(Ontology arg0, Ontology arg1,
			ChangeType arg2, String arg3) {
		// TODO Auto-generated method stub

	}

	public void ontologyTermReplaced(Ontology arg0, Ontology arg1, Term arg2,
			Term arg3) {
		// TODO Auto-generated method stub

	}

	public void ontologyUpdated(Ontology changedOnto, Ontology sourceOnto,
			List<OntologyChangeEvent> changes, Set<Entity> added, Set<Entity> removed) {
		changeLogger.add(changes.get(0).getChangeType(), changes.get(0).getAxiom());
		isChanged = true;
	}
	
	public boolean hasChanges(){
		return isChanged;
	}

}
