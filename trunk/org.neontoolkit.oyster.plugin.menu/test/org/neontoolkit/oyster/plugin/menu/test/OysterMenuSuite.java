package org.neontoolkit.oyster.plugin.menu.test;


import junit.framework.JUnit4TestAdapter;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import org.eclipse.jface.action.IAction;
import org.junit.Test;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;

public class OysterMenuSuite extends OysterMenuAbstractTest {

	private IAction actionNew;
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(OysterMenuSuite.class);
	}
	    
    @Test
    public void startTest() throws Exception {
    	System.out.println("-----------------------test1-------------------------");
    	System.out.println("Test 1: Start Registry");
    	StartRegistry registry = new StartRegistry();
    	registry.run(actionNew);
    	int wait=9; //wait maximum 90 sec
    	while (wait>0 && StartRegistry.connection==null){
    		Thread.sleep(10000); //wait 10 sec
    		wait--;
    	}
    	assertNotNull("Registry did not start!", StartRegistry.connection);
    }
	
    @Test
    public void stopTest() throws Exception {
    	System.out.println("-----------------------test2-------------------------");
    	System.out.println("Test 2: Stop Registry");
    	StartRegistry registry = new StartRegistry();
    	registry.run(actionNew);
    	int wait=9; //wait maximum 90 sec
    	while (wait>0 && StartRegistry.connection!=null){// && StartRegistry.actionNew.getText().contains("Start")){
    		Thread.sleep(5000); //wait 10 sec
    		wait--;
    	}
    	assertNull("Registry could not be stopped!", StartRegistry.connection);
    }
}
