package org.neontoolkit.registry.oyster2.server;


import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.neontoolkit.registry.oyster2.Oyster2Factory;
import org.neontoolkit.registry.oyster2.Properties;
import org.neontoolkit.registry.oyster2.server.Options.Multiplicity;
import org.neontoolkit.registry.oyster2.server.Options.Separator;
import org.neontoolkit.registry.util.EntryDetailSerializer;

/**
 * The class StartServer allows to 
 * start Oyster in server mode   
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class StartServer {
	static Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private static Properties mprop = mOyster2.getProperties();
	public static Process serverProcess = null;
	private static String kaon2File = System.getProperty("user.dir")+System.getProperty("file.separator")+"kaon2.jar";
	private static String serverRoot= System.getProperty("user.dir")+System.getProperty("file.separator")+"server";
	private static String startParameters= "";
	private static boolean startKAON2=false;
	private static boolean onlineKAON2=true;
	protected static boolean appClosed = false;

	public static void main(String[] args)throws Exception{
		Options opt = new Options(args);
		opt.getSet().addOption("isSimple", Multiplicity.ZERO_OR_ONE);
		opt.getSet().addOption("caching", Multiplicity.ZERO_OR_ONE);
		opt.getSet().addOption("workflowSupport", Multiplicity.ZERO_OR_ONE);
		opt.getSet().addOption("debug", Multiplicity.ZERO_OR_ONE);
		opt.getSet().addOption("startKAON2", Multiplicity.ZERO_OR_ONE).addOption("KAON2Path", Separator.EQUALS, Multiplicity.ZERO_OR_ONE).addOption("serverRootPath", Separator.EQUALS, Multiplicity.ZERO_OR_ONE);
		opt.getSet().addOption("preferenceFile", Separator.EQUALS, Multiplicity.ZERO_OR_ONE);
		if (args.length>0 && !opt.check(false, false))  {
			System.out.println(opt.getCheckErrors());
			help();
		}
		if (opt.getSet().isSet("isSimple")) {
			  // React to option -isSimple
			  mOyster2.setIsSimple(true);
		}
		if (opt.getSet().isSet("caching")) {
			  // React to option -caching
			  mOyster2.setCaching(true);
		}
		if (opt.getSet().isSet("workflowSupport")) {
			  // React to option -workflowSupport
			  mOyster2.setWorkflowSupport(true);
		}
		if (opt.getSet().isSet("debug")) {
			  // React to option -debug
			  mOyster2.setLogEnabled(true);
		}
		if (opt.getSet().isSet("startKAON2")) {
			  // React to option -startKAON2
			startKAON2=true;
			if (opt.getSet().isSet("KAON2Path") && opt.getSet().isSet("serverRootPath")) {
				onlineKAON2=false;
				String KAON2Path = opt.getSet().getOption("KAON2Path").getResultValue(0);
				String serverRootPath = opt.getSet().getOption("serverRootPath").getResultValue(0);
				setKaon2File(KAON2Path);
				setServerRoot(serverRootPath);
			}else if (opt.getSet().isSet("KAON2Path") || opt.getSet().isSet("serverRootPath")) {
				help();
			}
		}else if (opt.getSet().isSet("KAON2Path") || opt.getSet().isSet("serverRootPath")) {
			help();
		}
		
		if (opt.getSet().isSet("preferenceFile")) {
			  // React to option -preferenceFile
			  String prefFile = opt.getSet().getOption("preferenceFile").getResultValue(0);
			  configureProperties(prefFile);
		}else{
			configureProperties("");
		}
		boolean success=initialize();
		if (success) run();
		shutdown();
    }
	
	private static void help(){
		System.out.println("Usage: StartServer [-isSimple ][-caching ][-workflowSupport ][-debug ][-startKAON2 [-KAON2Path=<kaon2path> -serverRootPath=<serverpath>]] [-preferenceFile=<prefFile>]");
		System.exit(1);
	}
	
	public static void configureProperties(String file){
		if (file!=null && file!="")
			mprop.init(file);
		else
			mprop.init();
	}
	
	private static boolean initialize(){
		try{
			if (startKAON2){
				if (onlineKAON2){
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
				        Thread.sleep(500);
					} catch (Exception ex) {
				        System.out.println(ex.getMessage()+"couldnt start KAON2 server...");
				        closeConnection();
					}
				}else{
					serverProcess = Runtime.getRuntime().exec("java " + startParameters +" -cp "+ EntryDetailSerializer.QUOTE + kaon2File + EntryDetailSerializer.QUOTE + " org.semanticweb.kaon2.server.ServerMain -registry -rmi -ontologies "+ EntryDetailSerializer.QUOTE + serverRoot +EntryDetailSerializer.QUOTE);
					if(serverProcess!=null) System.out.println("server started...");
					else {System.out.println("could not start KAON2 server..."); closeConnection();}
					Thread.sleep(2000);
				}
			}
			mOyster2.init(null);
			if (mOyster2.retInit!=0) {
				closeConnection();
				return false;
			}
	    } catch (Exception e) {
	    	System.out.println("StartServer.Initialize error "+e);
	    	return false;
		}
	    return true;
	}

	
	/**
	 * Closes the connection to Oyster2 registry. Destroys Kaon2 instance.
	 */
	public static void closeConnection(){
		appClosed=true;
		mOyster2.shutdown();
		if (startKAON2 && !onlineKAON2) serverProcess.destroy();	
	}
	
	
	private static void setKaon2File(String fileName){
		if (fileName!="" && fileName!="")
			kaon2File=fileName;
	}
	
	private static void setServerRoot(String sRoot){
		if (sRoot!="" && sRoot!=null)
			serverRoot=sRoot;
	}
		
	
	private static void shutdown(){ //NOT NECESSARY ANYMORE
		//appClosed=true;
		//mOyster2.shutdown();
		//if (startKAON2 && !onlineKAON2) serverProcess.destroy();	
		//System.exit(0);
	}
	
		
	public static void run() {
		while (!appClosed) {
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}

}

