package org.neon_toolkit.registry.oyster2;

import org.eclipse.jface.preference.PreferenceStore;
import org.neon_toolkit.registry.oyster2.Oyster2Factory;
import org.neon_toolkit.registry.util.EntryDetailSerializer;


public class StartServer {
	static Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private static PreferenceStore store = mOyster2.getPreferenceStore();
	public static Process serverProcess = null;
	private static String kaon2File = System.getProperty("user.dir")+System.getProperty("file.separator")+"kaon2.jar";
	private static String prefFile = System.getProperty("user.dir")+System.getProperty("file.separator")+"new store";
	private static String serverRoot= System.getProperty("user.dir")+System.getProperty("file.separator")+"server";
	private static boolean startKAON2=true;
	private static String startParameters= "";
	protected static boolean appClosed = false;

	
	public static void main(String[] args)throws Exception{
		 for ( int i = 0 ; i < args.length ; i++ )
	        {
	            String arg = args[ i ];
	            System.out.println( arg );
	        }
		setPreferencesFile(prefFile);
		setKaon2File(kaon2File);
		setServerRoot(serverRoot);
		setStartParameters(startParameters);
		
		initialize();
		
		run();
		shutdown();
    }
	
	private static void initialize(){
		try{
			if (startKAON2){
				serverProcess = Runtime.getRuntime().exec("java " + startParameters +" -cp "+ EntryDetailSerializer.QUOTE + kaon2File + EntryDetailSerializer.QUOTE + " org.semanticweb.kaon2.server.ServerMain -registry -rmi -ontologies "+ EntryDetailSerializer.QUOTE + serverRoot +EntryDetailSerializer.QUOTE);
				if(serverProcess!=null) System.out.println("server started...");
				else {System.out.println("could not start KAON2 server..."); closeConnection();}
				Thread.sleep(2000);
			}
			store.load();
			mOyster2.init(null);
			if (mOyster2.retInit!=0) closeConnection();
	    } catch (Exception e) {
	    	System.out.println("StartServer.Initialize error "+e);
		}
	}
	
	
	/**
	 * Closes the connection to Oyster2 registry. Destroys Kaon2 instance.
	 */
	public static void closeConnection(){
		mOyster2.shutdown();
		if (startKAON2) serverProcess.destroy();	
		//System.exit(0);
	}
	
	private static void setPreferencesFile(String fileName){
		String tFile;
		if (fileName!="" && fileName!=null)
			tFile = fileName;
		else
			tFile = prefFile;
		PreferenceStore customStore = new PreferenceStore(tFile);
		mOyster2.setPreferenceStore(customStore);
		store = mOyster2.getPreferenceStore();
	}
	
	
	private static void setKaon2File(String fileName){
		if (fileName!="" && fileName!="")
			kaon2File=fileName;
	}
	
	private static void setServerRoot(String sRoot){
		if (sRoot!="" && sRoot!=null)
			serverRoot=sRoot;
	}
	
	private static void setStartParameters(String sParameters){
		if (sParameters!="" && sParameters!=null)
			startParameters=sParameters;
	}
	
	private static void shutdown(){
		mOyster2.shutdown();
		serverProcess.destroy();	
		System.exit(0);
	}
	
		
	public static void run() {
		//openWindowAndBlock();
		while (!appClosed) {
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
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