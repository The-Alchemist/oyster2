package api;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

//import oyster2.OntologyProperty;
import oyster2.Oyster2Factory;
import org.eclipse.jface.preference.PreferenceStore;
import org.semanticweb.kaon2.api.Namespaces;

/**
 * The class Oyster2Manager provides the initial connection to Oyster2
 * registry, and the means to display results
 * @author Raul Palma
 * @version 0.1, March 2007
 */
public class Oyster2Manager{
	static Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private static PreferenceStore store = mOyster2.getPreferenceStore();
	public static Process serverProcess = null;
	private static String kaon2File = System.getProperty("user.dir")+"\\kaon2.jar";
	private static String prefFile = System.getProperty("user.dir")+"\\new store";

	
	public Oyster2Manager()
	    {
		System.out.println("Created new Manager");
	    }

	/**
	 * Creates a new connection to Oyster2 registry. Initializes
	 * the default properties file and the registry factory.
	 * Starts an instance of Kaon2 platform.
	 * @return A connection object to the Oyster2 registry.
	 */
	public static Oyster2Connection newConnection()
    {
		setPreferencesFile(prefFile);
		initialize();
		return new Oyster2Connection();
    }
	
	/**
	 * Creates a new connection to Oyster2 registry. Initializes
	 * the registry factory and the properties file located at 
	 * the path passed by the parameters. Starts an instance 
	 * of Kaon2 platform.
	 * @param preferencesFilename the path to the properties file.
	 * @param kaon2Filename the path to the kaon2.jar file.
	 * @return a connection object to the Oyster2 registry.
	 */
	public static Oyster2Connection newConnection(String preferencesFilename, String kaon2Filename)
    {
		setPreferencesFile(preferencesFilename);
		setKaon2File(kaon2Filename);
		initialize();
		return new Oyster2Connection();
    }
	
	/**
	 * Return the string representation of a set of OMVOntology objects.
	 * @param OMVSet the set of OMVOntology objects.
	 * @return the string representation of the set of OMVOntology objects.
	 */
	public static String serializeOMV(Set<OMVOntology> OMVSet){
		String serial="";
		
		Iterator it = OMVSet.iterator();
		try{
		while(it.hasNext()){
			OMVOntology omv = (OMVOntology)it.next();
			serial=serial+"OMVOntology {\n"+
			getData(omv.getURI(),"URI")+
			getData(omv.getName(),"name")+
			getData(omv.getAcronym(),"acronym")+
			getData(omv.getDescription(),"description")+
			getData(omv.getDocumentation(),"documentation")+
			getData(omv.getKeywords(),"keywords")+
			getData(omv.getStatus(),"status")+
			getData(omv.getCreationDate(),"creationDate")+
			getData(omv.getModificationDate(),"modificationDate")+
			getDataSetString(omv.getHasContributor(),"hasContributor",1)+
			getDataSetString(omv.getHasCreator(),"hasCreator",1)+
			getDataSetString(omv.getUsedOntologyEngineeringTool(),"usedOntologyEngineeringTool",2)+
			getDataSetString(omv.getUsedOntologyEngineeringMethodology(),"usedOntologyEngineeringMethodology",3)+
			getDataSetString(omv.getUsedKnowledgeRepresentationParadigm(),"usedKnowledgeRepresentationParadigm",4)+
			getDataSetString(omv.getHasDomain(),"hasDomain",5);
			if (omv.getIsOfType()!=null) serial = serial + getData(omv.getIsOfType().getName(),"isOfType");
			serial=serial+getData(omv.getNaturalLanguage(),"naturalLanguage")+
			getDataSetString(omv.getDesignedForOntologyTask(),"designedForOntologyTask",6);
			if (omv.getHasOntologyLanguage()!=null) serial = serial + getData(omv.getHasOntologyLanguage().getName(),"ontologyLanguage");
			if (omv.getHasOntologySyntax()!=null) serial = serial + getData(omv.getHasOntologySyntax().getName(),"ontologySyntax");
			if (omv.getHasFormalityLevel()!=null) serial = serial + getData(omv.getHasFormalityLevel().getName(),"formalityLevel");
			serial=serial+getData(omv.getResourceLocator(),"resourceLocator")+
			getData(omv.getVersion(),"version");
			if (omv.getHasLicense()!=null) serial = serial + getData(omv.getHasLicense().getName(),"hasLicense");
			serial=serial+getDataSetString(omv.getUseImports(),"useImports",0);
			if (omv.getHasPriorVersion()!=null) serial = serial + getData(omv.getHasPriorVersion().getURI(),"hasPriorVersion");
			serial = serial + getDataSetString(omv.getIsBackwardCompatibleWith(),"isBackwardCompatibleWith",0)+
			getDataSetString(omv.getIsIncompatibleWith(),"isIncompatibleWith",0);
			if (omv.getNumClasses()!=null) serial = serial + getData(omv.getNumClasses().toString(),"numClasses");
			if (omv.getNumProperties()!=null) serial = serial + getData(omv.getNumProperties().toString(),"numProperties");
			if (omv.getNumIndividuals()!=null) serial = serial + getData(omv.getNumIndividuals().toString(),"numIndividuals");
			if (omv.getNumAxioms()!=null) serial = serial + getData(omv.getNumAxioms().toString(),"numAxioms");
			serial = serial + "}\n\n";
		}
		}catch(Exception ignore){
			//-- ignore
		}
		return serial;
	}
	
	/**
	 * Return the string representation of a set of OMVPeer objects.
	 * @param OMVPeerSet the map of pairs (peer URI, OMVPeer object).
	 * @return the string representation of the set of OMVPeer objects.
	 */
	public static String serializeOMVPeer(Map<String,OMVPeer> OMVPeerSet){
		String serial="";
		
		Set keySet = OMVPeerSet.keySet();
		Collection keyValues = OMVPeerSet.values();
		Iterator itKey = keySet.iterator();
		Iterator it = keyValues.iterator();
		try{
		while(it.hasNext()){
			OMVPeer omvPeer = (OMVPeer)it.next();
			serial=serial+"OMVPeer {\n"+
			getData(Namespaces.guessLocalName((String)itKey.next()),"name")+
			getData(omvPeer.getGUID(),"GUID")+
			getData(omvPeer.getIPAdress(),"IPAddress")+
			getData(omvPeer.getLocalPeer().toString(),"localPeer")+
			getData(omvPeer.getPeerType(),"peerType");
			if (omvPeer.getContextOntology()!=null) serial = serial + getData(omvPeer.getContextOntology().getURI(),"contextOntology");
			serial=serial+getDataSetString(omvPeer.getProvideOntology(),"provideOntology",0);
			serial = serial + "}\n\n";
		}
		}catch(Exception ignore){
			//-- ignore
		}
		return serial;
	}
	
	private static String getData(String which, String name){
		String local="";
		if (which!=null)
			local = "\t"+name+": "+which+"\n";
		return local;
	}
	
	private static String getDataSetString(Set which, String name, int tClass){
		String local="",temp="";
		OMVOntology omv0;
		OMVPerson omv1a;
		OMVOrganisation omv1b;
		OMVOntologyEngineeringTool omv2;
		OMVOntologyEngineeringMethodology omv3;
		OMVKnowledgeRepresentationParadigm omv4;
		OMVOntologyDomain omv5;
		OMVOntologyTask omv6;
		
		Iterator it = which.iterator();
		try{
			while(it.hasNext()){
				if (tClass==0) {
					omv0=(OMVOntology)it.next();
					temp=temp+omv0.getURI()+" ";
				}
				else if (tClass==1) {
					if (it.next() instanceof OMVPerson){
						omv1a = (OMVPerson)it.next();
						temp=temp+omv1a.getFirstName()+omv1a.getLastName()+" ";
					}
					else{
						omv1b = (OMVOrganisation)it.next();
						temp=temp+omv1b.getName()+" ";
					}
				}
				else if (tClass==2) {
					omv2=(OMVOntologyEngineeringTool)it.next();
					temp=temp+omv2.getName()+" ";
				}
				else if (tClass==3) {
					omv3=(OMVOntologyEngineeringMethodology)it.next();
					temp=temp+omv3.getName()+" ";
				}
				else if (tClass==4) {
					omv4=(OMVKnowledgeRepresentationParadigm)it.next();
					temp=temp+omv4.getName()+" ";
				}
				else if (tClass==5) {
					omv5=(OMVOntologyDomain)it.next();
					temp=temp+omv5.getURI()+" ";
				}
				else if (tClass==6) {
					omv6=(OMVOntologyTask)it.next();
					temp=temp+omv6.getName()+" ";
				}
			}
		}catch(Exception ignore){
			System.out.println("here in getdatasetstring");
		}
		if (temp.length()>0)
			local = "\t"+name+": "+temp+"\n";
		return local;
	}
	
	private static void initialize()
	    {
		try{
			serverProcess = Runtime.getRuntime().exec("java -cp "+ kaon2File +" org.semanticweb.kaon2.server.ServerMain -registry -rmi -ontologies server");
			if(serverProcess!=null) System.out.println("server started...");
			Thread.sleep(2000);
			store.load();
			mOyster2.init(null);
	    } catch (Exception e) {
	    	System.out.println("Oyster2Manager.newConnection error "+e);
		}
	    }
	
	/**
	 * Closes the connection to Oyster2 registry. Destroys Kaon2 instance.
	 */
	public static void closeConnection(){
		mOyster2.shutdown();
		serverProcess.destroy();	
		//System.exit(0);
	}
	
	private static void setPreferencesFile(String fileName){
		PreferenceStore customStore = new PreferenceStore(fileName);
		mOyster2.setPreferenceStore(customStore);
		store = mOyster2.getPreferenceStore();
	}
	
	private static void setKaon2File(String fileName){
		kaon2File=fileName;
	}
}	



/*	
	public static void main(String[] args)throws Exception{
		
		serverProcess = Runtime.getRuntime().exec("java -cp kaon2.jar org.semanticweb.kaon2.server.ServerMain -registry -rmi -ontologies "+ args[0]); 
		if(serverProcess!=null) System.out.println("server started...");
		Thread.currentThread().sleep(2000);
		//List :)
        configureProperties();
		mOyster2.init(null);
		shutdown();
    }
	
	public static void shutdown(){
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
*/


