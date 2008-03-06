package org.neon_toolkit.registry.ui;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.swt.widgets.Display;
import org.neon_toolkit.registry.oyster2.Constants;
import org.neon_toolkit.registry.oyster2.Oyster2Factory;
import org.neon_toolkit.registry.oyster2.Properties;
import org.neon_toolkit.registry.ui.ConditionPreferencePage;
import org.neon_toolkit.registry.ui.MainPreferencePage;
import org.neon_toolkit.registry.ui.MainWindow;
//import org.eclipse.jface.preference.PreferenceStore;
//import org.semanticweb.kaon2.api.KAON2Exception;
//import org.semanticweb.kaon2.api.*;
//import org.semanticweb.kaon2.server.context.*;
//import org.semanticweb.kaon2.server.rmi.*;
//import org.neon_toolkit.registry.util.EntryDetailSerializer;

public class StartGUI {
	static Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	//private static PreferenceStore store = mOyster2.getPreferenceStore();
	public static Process serverProcess = null;
	private static Properties mprop = mOyster2.getProperties();
	//private static String kaon2File = System.getProperty("user.dir")+System.getProperty("file.separator")+"kaon2.jar";
	//private static String serverRoot= System.getProperty("user.dir")+System.getProperty("file.separator")+"server";
	//private static String startParameters= "";
	private static boolean startKAON2=true;
	
	/**
	 * The main windows of Oyster2 GUI.
	 */
	private static MainWindow mainWindow;

	protected static boolean appClosed = false;
	/**
	 * If the window should be shown.
	 */
	protected static boolean appWindowToBeShown = true;

	private static boolean isSystemTrayActive;
	
	

	
	public static void main(String[] args)throws Exception{
		
		if (args[0]!=null && args[0].equalsIgnoreCase("false"))	startKAON2=false;
		if (startKAON2){			
			try {
				URLClassLoader cl = new URLClassLoader(new URL[] { new URL("http://ontoware.org/frs/download.php/455/kaon2.jar") });//new URLClassLoader(urls);
				cl.loadClass("org.semanticweb.kaon2.server.ServerMain");
				Class cls = Class.forName("org.semanticweb.kaon2.server.ServerMain", false, cl);
		    	Method method = cls.getDeclaredMethod("main", new Class[] { String[].class });
		        String[] argum= new String[4];
		        argum[0]="-registry";
		        argum[1]="-rmi";
		        argum[2]="-ontologies";
		        argum[3]="server";
		        method.invoke(null, new Object[] { argum });
			} catch (Exception ex) {
		        System.out.println(ex.getMessage()+"couldnt start KAON2 server...");
		        startKAON2=false;
		        shutdown();
			}
			startKAON2=false;
			Thread.sleep(500);
			//serverProcess = Runtime.getRuntime().exec("java " + startParameters +" -cp "+ EntryDetailSerializer.QUOTE + kaon2File + EntryDetailSerializer.QUOTE + " org.semanticweb.kaon2.server.ServerMain -registry -rmi -ontologies "+ EntryDetailSerializer.QUOTE + serverRoot +EntryDetailSerializer.QUOTE); //serverProcess = Runtime.getRuntime().exec("java -cp kaon2.jar org.semanticweb.kaon2.server.ServerMain -registry -rmi -ontologies "+ args[0]);
			//if(serverProcess!=null) System.out.println("server started...");
			//else {System.out.println("could not start KAON2 server..."); shutdown();}
			//Thread.sleep(2000);	//Thread.currentThread().sleep(2000);
		}
        configurePropertiesL();
        mOyster2.init(null);
        if (mOyster2.retInit==0){
        	mOyster2.initUI();
        	if (mOyster2.retInit==0)
        		run();
        }
		shutdown();
    }
	
	private static void shutdown(){
		mOyster2.shutdown();
		if (startKAON2) serverProcess.destroy();
		System.exit(0);
	}
	
	/*
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
	*/
	
	public static void configurePropertiesL(){
		File mFile = new File(Constants.PROPERTY_FILE);
		try {
			if (!mFile.exists()) {
				mprop.init();
				Display display = new Display();
				PreferenceManager pm = new PreferenceManager();
				
				MainPreferencePage p = new MainPreferencePage("Main preferences");
				ConditionPreferencePage cp = new ConditionPreferencePage("ConditionPreference");
				
				PreferenceNode mainprop=new PreferenceNode("mainOyster2Properties", p);
				PreferenceNode conditionsprop=new PreferenceNode("searchConditions",cp);
				
				pm.addToRoot(mainprop);
				pm.addToRoot(conditionsprop);
				
				PreferenceDialog d = new PreferenceDialog(display.getActiveShell(), pm);
				//d.setSelectedNode("searchConditions");
				d.create();
				d.open();
			}
			else{
				mprop.init();
			} 
		} catch (Exception e) {
				System.err.println(e+" in configure propertiesL");
		}
	}
	
	public static void run() {
		//openWindowAndBlock();
		while (!appClosed) {
			if (appWindowToBeShown) {
				openWindowAndBlock();
				closeWindow();
				appWindowToBeShown = false;
				// bsc: did not work 
				//ThreadPool.setThreadsPriority(Thread.MIN_PRIORITY);
				if (!isSystemTrayActive) {
					appClosed = true;
				} else {
					boolean result = MessageDialog.openQuestion(null, "Oyster", "Would you like to leave Oyster running in the background?");
					if (!result) {
						appClosed = true;
					}
				}
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}

	private static void closeWindow() {
		if (mainWindow != null) {
			mainWindow.close();
		}
		mainWindow = null;
	}

	private static void openWindowAndBlock() {
		createWindow();
		mainWindow.setBlockOnOpen(true);
		mainWindow.open();
	}

	private static void createWindow() {
		if (mainWindow == null) {
			mainWindow = new MainWindow(null);
		}
	}
	
	/**
	 * return mainWindow instance of Oyster2
	 */
	public static MainWindow getMainWindow() {
		return StartGUI.mainWindow;
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