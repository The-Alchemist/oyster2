package org.neontoolkit.changelogging.gui.shutdown;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.PlatformUI;
import org.neontoolkit.changelogging.core.flogic.FlogicChangeListener;
import org.neontoolkit.changelogging.core.owl.OWLChangeListener;
import org.neontoolkit.changelogging.gui.actions.Track;
import org.semanticweb.kaon2.api.Ontology;

import com.ontoprise.ontostudio.gui.IPreShutdownListener;

/* 
 * Created on 21.04.2008
 * Created by Jia Yu
 *
 * Keywords: Ontology, Save, Shutdown
 */

public class PreShutdown implements IPreShutdownListener {
	private List<Ontology> modifiedOntos = new ArrayList<Ontology>();

	public boolean isShutdownOK() {
		init();
		if(modifiedOntos.size() == 0)
			return true;
		
		SaveListDialog sDialog = new SaveListDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), modifiedOntos);
		sDialog.setInitialElementSelections(modifiedOntos);
		sDialog.open();
		Object[] results = sDialog.getResult();
		
		List<FlogicChangeListener> flistener = new ArrayList<FlogicChangeListener>();
		List<OWLChangeListener> olistener = new ArrayList<OWLChangeListener>();
		for(Object result : results){
			if(Track.FlogicList.containsKey((Ontology)result))
				flistener.add(Track.FlogicList.get((Ontology)result));
			if(Track.OWLList.containsKey((Ontology)result))
				olistener.add(Track.OWLList.get((Ontology)result));
		}
		
		try {
			for(FlogicChangeListener listener : flistener){		
				listener.getChangeLogger().persist();
			}			
		}catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		
		try {
			for(OWLChangeListener listener : olistener)
				listener.persist();
		}catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		return true;
	}
	
	private void init(){
		for(Ontology onto : Track.FlogicList.keySet())
			if(Track.FlogicList.get(onto).hasChanges())
				modifiedOntos.add(onto);
		//for(Ontology onto : Track.OWLList.keySet())
		//	if(Track.OWLList.get(onto).hasChanges())
		//		modifiedOntos.add(onto);
	}
}

