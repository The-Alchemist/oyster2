package org.neontoolkit.changelogging.test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import junit.framework.JUnit4TestAdapter;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.junit.Test;
import org.neontoolkit.changelogging.core.owl.OWLChangeListener;
import org.neontoolkit.changelogging.gui.actions.Track;
import org.neontoolkit.omv.api.extensions.change.OMVChange;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.OntologyChangeEvent;
import org.semanticweb.kaon2.api.OntologyManager;
import com.ontoprise.ontostudio.datamodel.DatamodelPlugin;
import com.ontoprise.ontostudio.gui.GuiPlugin;


public class ChangeLoggingTest extends ChangeLoggingBefore {

	private IPreferenceStore _store = GuiPlugin.getDefault().getPreferenceStore();
	private IAction actionNew;
	public static final String ROLE = "ROLE"; 
	public static final String USER_FIRSTNAME = "USER_FIRSTNAME";
	public static final String USER_LASTNAME = "USER_LASTNAME";
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(ChangeLoggingTest.class);
	}
	    
    @Test
    public void doLogging() throws Exception {
    	System.out.println("-----------------------test1-------------------------");
    	System.out.println("Test 1: Change Logging");
    	System.out.println();
    	System.out.println("Sub1: Start Registry");
    	StartRegistry registry = new StartRegistry();
    	registry.run(actionNew);
    	int wait=9; //wait maximum 90 sec
    	while (wait>0 && StartRegistry.connection==null){
    		Thread.sleep(10000); //wait 10 sec
    		wait--;
    	}
    	assertNotNull("Registry did not start!", StartRegistry.connection);
    	
    	
    	System.out.println("Sub2: Start logging changes of ontology");
    	_store.setValue(USER_FIRSTNAME, "Raul");
        _store.setValue(USER_LASTNAME, "Palma");
        _store.setValue(ROLE, "http://omv.ontoware.org/2007/07/workflow#SubjectExpert");
    	Track track = new Track();
    	track.setModuleId("");
    	track.setProject(_project.getName());
    	track.setOntologyURI("http://www.co-ode.org/ontologies/pizza/pizza_20041007.owl");
    	track.run(actionNew);
    	wait=9; //wait maximum 90 sec
    	while (wait>0 && Track.OWLList.size()<=0){
    		Thread.sleep(5000); //wait 5 sec
    		wait--;
    	}
    	assertEquals("The Ontology is not being logged",1,Track.OWLList.size());
    }
    
    @Test
    public void addChange() throws Exception {
    	System.out.println("-----------------------test2-------------------------");
    	System.out.println("Test 2: Check if a change is logged");
    	System.out.println();
    	System.out.println("Sub1: Add Change");
    	org.semanticweb.kaon2.api.owl.elements.OWLEntity ddm=null;
    	org.semanticweb.kaon2.api.Axiom app=null;
    	List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
    	ddm = KAON2Manager.factory().owlClass("http://www.co-ode.org/ontologies/pizza/myClass");
    	app = KAON2Manager.factory().declaration(ddm);
    	changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
    	OntologyManager connection = DatamodelPlugin.getDefault().getKaon2Connection(_project.getName());
    	Ontology ontology = connection.getOntology("http://www.co-ode.org/ontologies/pizza/pizza_20041007.owl");
    	try {
    		ontology.applyChanges(changes);
    		ontology.persist();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
		System.out.println("Sub2: Verifying if the change is logged");
		List<OWLChangeListener> listners = new ArrayList<OWLChangeListener>();
		listners.addAll(Track.OWLList.values());
		OWLChangeListener listener = listners.get(0);
		List<OMVChange> checking = new LinkedList<OMVChange>();
		int wait=9; //wait maximum 90 sec
    	while (wait>0 && checking.size()<=0){
    		checking=listener.getChanges();
    		Thread.sleep(5000); //wait 5 sec
    		wait--;
    	}
		
    	assertTrue("The change was not logged",checking.size()>0);
    }
}
