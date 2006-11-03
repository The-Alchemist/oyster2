package oyster2;

import java.util.HashMap;
import java.io.*;

import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.semanticweb.kaon2.api.*;
//import org.semanticweb.kaon2.server.context.*;
import org.semanticweb.kaon2.server.rmi.*;

import ui.MainPreferencePage;
import ui.ConditionPreferencePage;

//import org.semanticweb.kaon2.util.*;


public class Start {
	static Oyster2 mOyster2 = Oyster2.sharedInstance();
	private static PreferenceStore store = mOyster2.getPreferenceStore();
	public static Process serverProcess = null;

	
	public static void main(String[] args)throws Exception{
		
		serverProcess = Runtime.getRuntime().exec("java -cp kaon2.jar org.semanticweb.kaon2.server.ServerMain -registry -rmi -ontologies "+ args[0]); 
		if(serverProcess!=null) System.out.println("server started...");
		Thread.currentThread().sleep(2000);
		//List :)
        configureProperties();
		mOyster2.init(null);
		shutdown();
    }
	
	private static void shutdown(){
		mOyster2.shutdown();
		serverProcess.destroy();	
		System.exit(0);
	}
	
	
	public static void configureProperties(){
		Boolean first=true;
		try{
			store.load();
			first=false;
		} catch (Exception e) {
			first=true;
			System.out.println("First Time");
		}
		if (first){
			Display display = new Display();
			PreferenceManager pm = new PreferenceManager();
			MainPreferencePage p = new MainPreferencePage("Main preferences");
			ConditionPreferencePage cp = new ConditionPreferencePage("ConditionPreference");

			pm.addToRoot(new PreferenceNode("mainOyster2Properties", p));
			pm.addToRoot(new PreferenceNode("searchConditions",cp));
			PreferenceDialog d = new PreferenceDialog(display.getActiveShell(), pm);
			d.setPreferenceStore(store);
		
			d.create();
			d.open();
			try {
				// Save the preferences
				store.save();
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		}
		mOyster2.setPreferenceStore(store);
	}
}

/*
		//To obtain a list of ontologies registered at the server, use the following API.
		try{	
			String[] ontologyURIs1=RMIServerHelper.getAvailableOntologyURIs("localhost",-1);
			System.out.println();
	        System.out.println("The server currently contains the following ontologies:");
	        for (String ontologyURI : ontologyURIs1)
	            System.out.println("    "+ontologyURI);
		} catch (KAON2Exception e) {
			serverProcess.destroy();
			System.err.println(e + " here()");
		}
 */

/*
		/* long time = System.currentTimeMillis();
		while(true){
			long currentTime = System.currentTimeMillis();
			if(currentTime>time+6000)
				break;
		} 

*/
/*

Runtime.getRuntime().addShutdownHook(new Thread("ShutdownHook") {
public void run() {
    System.out.println("KAON2 Server is shutting down...");
    serverProcess.destroy();
    //shutdown(serverProcess);
}
}
);
*/