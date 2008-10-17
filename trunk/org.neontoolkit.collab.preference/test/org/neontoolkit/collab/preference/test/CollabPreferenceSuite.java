package org.neontoolkit.collab.preference.test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.Set;

import junit.framework.JUnit4TestAdapter;

import org.eclipse.jface.action.IAction;
import org.junit.Test;
import org.neontoolkit.omv.api.core.OMVPerson;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;
import org.neontoolkit.registry.api.Oyster2Connection;
import org.neontoolkit.registry.oyster2.Constants;

public class CollabPreferenceSuite extends CollabPreferenceAbstractTest {
	private IAction actionNew;
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(CollabPreferenceSuite.class);
	}
	    
    @Test
    public void addUserTest() throws Exception {
    	System.out.println("-----------------------test1-------------------------");
    	System.out.println("Test 1: Register new user");
    	StartRegistry registry = new StartRegistry();
    	System.out.println();
    	System.out.println("Sub1: Start Registry");
    	registry.run(actionNew);
    	int wait=9; //wait maximum 90 sec
    	while (wait>0 && StartRegistry.connection==null){
    		Thread.sleep(10000); //wait 10 sec
    		wait--;
    	}
    	assertNotNull("Registry did not start!", StartRegistry.connection);
    	Oyster2Connection oyster2Conn = StartRegistry.connection;
    	OMVPerson p = new OMVPerson();
    	p.setFirstName("Raul");
    	p.setLastName("Palma");
    	p.setHasRole(Constants.SubjectExpert);
    	oyster2Conn.replace(p);
    	
    	//HERE WE TEST SUBMIT ADHOC QUERIES
		System.out.println("Sub2: Submit adhoc query(person) to verify it exists");
		Set<Object> OMVSetPerson = oyster2Conn.submitAdHocQuery("SELECT ?x WHERE  { ?x rdf:type <http://omv.ontoware.org/2005/05/ontology#Person>  }");
    	assertTrue("The person was not registered",OMVSetPerson.size()>0);
    }
}
