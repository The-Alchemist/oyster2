package org.neontoolkit.collab.test;

import static junit.framework.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.core.OMVPerson;
import org.neontoolkit.omv.api.extensions.change.OMVChange;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;
import org.neontoolkit.registry.api.Oyster2Connection;
import org.neontoolkit.registry.oyster2.Constants;


public class WorkflowTest {
	private OMVPerson p=new OMVPerson();
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(WorkflowTest.class);
	}

	
	@Test
    public void sendToBeApprovedChange() throws Exception {
		System.out.println("-----------------------test3-------------------------");
    	System.out.println("Test 3: Submit a change to be approved state  (testing a subject expert)");
    	
    	Oyster2Connection oyster2Conn = StartRegistry.connection;
    	p.setFirstName("Raul");
    	p.setLastName("Palma");
    	p.setHasRole(Constants.SubjectExpert);
    	oyster2Conn.submitToBeApproved(oyster2Conn.getLastChangeId(), p);
    	
    	OMVOntology o = new OMVOntology();
    	o.setURI("http://www.co-ode.org/ontologies/pizza/pizza_20041007.owl");
		o.setResourceLocator("");
    	List<OMVChange> changes = new ArrayList<OMVChange>();
    	changes.addAll(oyster2Conn.getChangesWithState(o, Constants.ToBeApprovedState));
    	int wait=9; //wait maximum 90 sec
    	while (wait>0 && changes.size()<=0){
    		Thread.sleep(5000); //wait 5 sec
    		changes.addAll(oyster2Conn.getChangesWithState(o, Constants.ToBeApprovedState));
    		wait--;
    	}
    	
    	
    	assertTrue("There is no change in toBeApproved state",changes.size()>0);
    }
    
    @Test
    public void sendToApprovedChange() throws Exception {
    	System.out.println("-----------------------test4-------------------------");
    	System.out.println("Test 4: Submit a change to approved state  (testing a validator)");
    	Oyster2Connection oyster2Conn = StartRegistry.connection;
    	p.setFirstName("Peter");
    	p.setLastName("Haase");
    	p.setHasRole(Constants.Validator);
    	oyster2Conn.submitToApproved(oyster2Conn.getLastChangeId(), p);
    	
    	OMVOntology o = new OMVOntology();
    	o.setURI("http://www.co-ode.org/ontologies/pizza/pizza_20041007.owl");
		o.setResourceLocator("");
    	List<OMVChange> changes = new ArrayList<OMVChange>();
    	changes.addAll(oyster2Conn.getChangesWithState(o, Constants.ApprovedState));
    	
    	int wait=9; //wait maximum 90 sec
    	while (wait>0 && changes.size()<=0){    		
    		Thread.sleep(5000); //wait 5 sec
    		changes.addAll(oyster2Conn.getChangesWithState(o, Constants.ApprovedState));
    		wait--;
    	}
    	assertTrue("There is no change in Approved state",changes.size()>0);
    }

}
