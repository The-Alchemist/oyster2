package oyster2;

import core.*;
import ui.*;
import util.GUID;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.io.*;

import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.dialogs.MessageDialog;

import org.semanticweb.kaon2.api.*;
import org.semanticweb.kaon2.api.logic.*;
//import org.semanticweb.kaon2.api.rules.*;  //OLD VERSION
import oyster2.Properties;
import oyster2.Constants;

/**
 * This class represents the KaonP2P facility. It is responsible for all
 * activities in the KaonP2P network (search, exchange). This class implements
 * the <code>Singleton</code> pattern as defined by Gamma et.al. As there
 * could only exist one instance of this class, other clients must use the
 * <code>sharedInstance</code> function to use this class.
 * 
 * @author <a href="mailto:Guo Rui <rui.guo@epfl.ch>">GuoRui</a>
 * @version 1.0.0
 */
public class Oyster2 {

	/**
	 * The key for the property file used to initialize the oyster2 repository.
	 */
	public static final String INIT_PROPERTY_FILE = "PropertyFile";

	/**
	 * The reference to the only instance of this class (Singleton pattern).
	 * This differs from the C++ standard implementation by Gamma et.al. since
	 * Java ensures the order of static initialization at runtime.
	 * 
	 * @see <a
	 *      href="http://www.javaworld.com/javaworld/javatips/jw-javatip67.html">
	 *      Lazy instantiation - Balancing performance and resource usage</a>
	 */
	private static final Oyster2 SHARED_INSTANCE = new Oyster2();

	/**
	 * The list of bootstrap hosts used to join the network at the first time.
	 */
	private Vector mBootstrapHosts = new Vector();

	/**
	 * The file storing the localExpertiseRegistry.
	 */
	private File localRegistryFile = null;

	/**
	 * The application properties.
	 */
	private Properties mProperties = new Properties();

	/**
	 * The local host.
	 */
	private XMLOyster2Host mLocalHost;

	/**
	 * The local exchange times.
	 */
	private int mExchangeVersion;

	/**
	 * The resources.
	 */
	private ResourceBundle mResources = null;

	/**
	 * The local Peers Expertise Registry.
	 */
	private LocalExpertiseRegistry mLocalRegistry;

	/**
	 * The local Advertisement Informer for updating local expertise registry .
	 */
	private AdvertInformer mInformer;

	/**
	 * The context ontology vocabulary for search details.
	 */
	private OntoVocabulary mVocabulary;

	/**
	 * the semantic analyzer
	 */
	//private Analyzer mAnalyzer;

	/**
	 * the query Formulator;
	 */
	private QueryFormulator mFormulator;

	/**
	 * the context Ontology used in search, such as: swrc
	 */
	private Ontology typeOntology;

	/**
	 * the topic Ontology used in Search, such as: AMC
	 */
	private Ontology topicOntology;

	private Entity typeRoot;

	private Entity topicRoot;

	private PreferenceStore store = new PreferenceStore("new Store");

	/**
	 * The queryListener
	 */
	// private QueryListener queryListener = null;
	/**
	 * The queryReplyListener
	 */
	// private QueryReplyListener queryReplyListener = null;
	/**
	 * The exchangeInitiator which occupies the exchange tasks.
	 */
	private ExchangeInitiator mExchangeInitiator;

	/**
	 * The SearchManager which occupies the searching tasks.
	 */
	private SearchManager mSearchManager = SearchManager.sharedInstance();

	/**
	 * The initiator thread for Exchanges.
	 */
	private Thread mExchangeInitiatorThread = null;

	/**
	 * The main windows of KaonP2P System.
	 */
	private MainWindow mainWindow;

	protected boolean appClosed = false;

	/**
	 * If the window should be shown.
	 */
	protected boolean appWindowToBeShown = true;

	private boolean isSystemTrayActive;
	
	/**
	 * the virtual ontology.
	 */
	//private Ontology virtualOntology;

	/**
	 * the localExpertiseRegistry Ontology.
	 */
	private Ontology localRegistryOntology;

	/**
	 * the Peer Description Ontology.
	 */
	private Ontology peerDescOntology;

	/**
	 * the uri of Peer Description Ontology.
	 */
	private String peerDescOntologyURI;

	/**
	 * the KAON2 connection used to connect to the servers.
	 */
	private KAON2Connection connection = KAON2Manager.newConnection();

	/**
	 * the ontology resolver.
	 */
	private DefaultOntologyResolver resolver = new DefaultOntologyResolver();

	/**
	 * the details of search conditions which should be specified in the
	 * property file.
	 */
	private List searchDetails = new ArrayList();

	private String imageLocation = "";

	/**
	 * This creates the only instance of this class. This differs from the C++
	 * standard implementation by Gamma et.al. since Java ensures the order of
	 * static initialization at runtime.
	 * 
	 * @return the shared instance of this class.
	 * @see <a
	 *      href="http://www.javaworld.com/javaworld/javatips/jw-javatip67.html">
	 *      Lazy instantiation - Balancing performance and resource usage</a>
	 */
	public static Oyster2 sharedInstance() {
		return SHARED_INSTANCE;
	}
	
	/**
	 * Initializes the oyster2 facility with the given properties.
	 * 
	 * @param properties
	 *            further initialization properties.
	 */
	public void init(java.util.Properties properties) throws Exception {

		String localRegistryURI = null;
		String typeOntologyURI = null;
		String topicOntologyURI = null;
		//String virtualOntologyURI = null;
		
		//0//
		
		imageLocation = serializeFileName(store.getString(Constants.Image));
		try {
			//1//			
			if ((store.getString(Constants.PDOntology) != null)
					&& (store.getString(Constants.PDOntology).length() > 0))
				peerDescOntologyURI = resolver.registerOntology("file:///"
						+ serializeFileName(store
								.getString(Constants.PDOntology)));
			if ((store.getString(Constants.TypeOntology) != null)
					&& (store.getString(Constants.TypeOntology).length() > 0))
				typeOntologyURI = resolver.registerOntology("file:///"
						+ serializeFileName(store
								.getString(Constants.TypeOntology)));
			if ((store.getString(Constants.TopicOntology) != null)
					&& (store.getString(Constants.TopicOntology).length() > 0))
				topicOntologyURI = resolver.registerOntology("file:///"
						+ serializeFileName(store
								.getString(Constants.TopicOntology)));
			this.localRegistryFile = new File(serializeFileName(store
					.getString(Constants.LocalRegistry)));
			
			System.out.println(serializeFileName(store.getString(Constants.LocalRegistry)));
			
			if ((!localRegistryFile.exists())
					|| (localRegistryFile.length() <= 0)) {
				/* For the second file */ 
				localRegistryFile.createNewFile();
				resolver.registerReplacement(Constants.LocalRegistryURI,
						"kaon2rmi://localhost?" + Constants.LocalRegistryURI);
				
				/* For the 1 file  
				resolver.registerReplacement(Constants.LocalRegistryURI,
						"file:///"+serializeFileName(store.getString(Constants.LocalRegistry)));
				*/
				localRegistryURI = Constants.LocalRegistryURI;				
				connection.setOntologyResolver(resolver);
				
				this.localRegistryOntology = connection.createOntology(
						localRegistryURI, new HashMap<String, Object>());
				this.peerDescOntology = connection.openOntology(
						peerDescOntologyURI, new HashMap<String, Object>());
				this.topicOntology = connection.openOntology(topicOntologyURI,
						new HashMap<String, Object>());
				this.localRegistryOntology.addToImports(peerDescOntology);
				this.localRegistryOntology.addToImports(topicOntology);
				this.localRegistryOntology.addOntologyProperty(
						Constants.VERSIONINFO, Integer.toString(1));
				
				this.mInformer = new AdvertInformer();
				Rule subTopicRule = this.mInformer.createSubTopicRule(topicOntology);
				this.mInformer.addRule(this.localRegistryOntology, subTopicRule);
				
			} else {
				/* For the second file */ 
				resolver.registerReplacement(Constants.LocalRegistryURI,
						"kaon2rmi://localhost?" + Constants.LocalRegistryURI);
				localRegistryURI = Constants.LocalRegistryURI;
				
				/* For the 1 file  
				localRegistryURI=resolver.registerOntology("file:///"
						+ serializeFileName(store
								.getString(Constants.LocalRegistry)));
				*/
				connection.setOntologyResolver(resolver);
				this.localRegistryOntology = connection.openOntology(
						localRegistryURI, new HashMap<String, Object>());
			}

			//2//
			
			connection.setOntologyResolver(resolver);
			this.typeOntology = connection.openOntology(typeOntologyURI,
					new HashMap<String, Object>());
			this.topicOntology = connection.openOntology(topicOntologyURI,
					new HashMap<String, Object>());
			
			//3//
			
		} catch (KAON2Exception e) {
			Start.serverProcess.destroy();
			System.err.println(e + " in Oyster2 init()");
		} catch (InterruptedException e) {
			Start.serverProcess.destroy();
			System.err.println(e + " in Oyster2 init()");

		}
		
		
		this.mInformer = new AdvertInformer();
		this.mLocalRegistry = new LocalExpertiseRegistry();
		mInformer.init();
		mLocalRegistry.init();
		String uid = mInformer.getLocalUID();
		System.out.println("local peer UID is :" + uid);
		GUID localGUID = GUID.getGUID(uid);
		try {
			mLocalHost = new XMLOyster2Host(localGUID, InetAddress
					.getLocalHost(), 1099);
		} catch (UnknownHostException e) {
			System.exit(-1);
		}
		if ((store.getString(Constants.LocalPeerName) != null)
				&& (store.getString(Constants.LocalPeerName).length() > 0))
			mInformer.setLocalPeer(store.getString(Constants.LocalPeerName),
					uid, mLocalHost.getAddress(), store
							.getString(Constants.LocalPeerType));
		if ((store.getString(Constants.BootStrapPeerName) != null)
				&& (store.getString(Constants.BootStrapPeerName).length() > 0))
			mInformer.addBootPeer(store.getString(Constants.BootStrapPeerName),
					store.getString(Constants.BootStrapPeerUID), store
							.getString(Constants.BootStrapPeerIP));
		Entity typeRoot = KAON2Manager.factory().owlClass(
				typeOntology.getOntologyURI()
						+ store.getString(Constants.TypeOntologyRoot));
		Entity topicRoot = KAON2Manager.factory().owlClass(
				topicOntology.getOntologyURI()
						+ store.getString(Constants.TopicOntologyRoot));

		setTypeOntologyRoot(typeRoot);
		setTopicOntologyRoot(topicRoot);
		mExchangeInitiator = new ExchangeInitiator();
		mExchangeInitiatorThread = new Thread(mExchangeInitiator);
		mExchangeInitiatorThread.setDaemon(true);
		mExchangeInitiatorThread.start();
		System.out.println(store.getString(Constants.SearchCondition_1));
		searchDetails.add(store.getString(Constants.SearchCondition_1));
		searchDetails.add(store.getString(Constants.SearchCondition_2));
		searchDetails.add(store.getString(Constants.SearchCondition_3));
		searchDetails.add(store.getString(Constants.SearchCondition_4));
		searchDetails.add(store.getString(Constants.SearchCondition_5));
		run();
	}

	private void run() {
		//openWindowAndBlock();
		while (!appClosed) {
			if (appWindowToBeShown) {
				openWindowAndBlock();
				closeWindow();
				appWindowToBeShown = false;
				// bsc: did not work 
//				ThreadPool.setThreadsPriority(Thread.MIN_PRIORITY);
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

	private void closeWindow() {
		if (mainWindow != null) {
			mainWindow.close();
		}
		mainWindow = null;
	}

	private void openWindowAndBlock() {
		createWindow();
		mainWindow.setBlockOnOpen(true);
		mainWindow.open();
	}

	private void createWindow() {
		if (mainWindow == null) {
			mainWindow = new MainWindow(null);
		}
	}

	/*
	 * Shutdowns the oyster2 facility.
	 */

	synchronized public void shutdown() {
		try {
			this.localRegistryOntology.persist();
			this.connection.close();
			//this.mLocalRegistry.save();
		} catch (Exception e) {
			System.err.println(e + " when oyster2 shutdown!");
		}

	}

	public void newExchange(Exchange exchange) {
		Thread t = new Thread(new Exchanger(exchange));
		t.start();
	}

	/**
	 * Invites one of the delivered hosts for an exchange.
	 * 
	 * @param hosts
	 *            the hosts.
	 * @param recursion
	 *            the recursion value.
	 * @param lCurrent
	 *            the current common length.
	 */
	public void randomExchange(Oyster2Host[] hosts, int recursion, int lCurrent) {
		// mExchangeInitiator.randomExchange(hosts, recursion, lCurrent, false);
	}

	/**
	 * Returns the list of bootstrap hosts.
	 * 
	 * @return the list of bootstrap hosts.
	 */
	public Vector getBootstrapHosts() {
		return mBootstrapHosts;
	}

	/**
	 * Returns the local host.
	 * 
	 * @return the local host.
	 */
	public Oyster2Host getLocalHost() {
		if (mLocalHost == null)
			System.out.println("mlocalhost is null");
		return mLocalHost;
	}

	public PreferenceStore getPreferenceStore() {
		return store;
	}

	public void setPreferenceStore(PreferenceStore store) {
		this.store = store;
	}

	/**
	 * Sets the local host.
	 * 
	 * @param host
	 *            the local host.
	 */
	public void setLocalHost(XMLOyster2Host host) {
		mLocalHost = host;
	}

	/**
	 * Returns the local Peers Expertise Registry
	 * 
	 * @return the peer expertise registry.
	 */

	public LocalExpertiseRegistry getLocalExpertiseRegistry() {
		return mLocalRegistry;
	}

	public AdvertInformer getLocalAdvertInformer() {
		return mInformer;
	}

	/**
	 * Returns the context ontology
	 */
	public Ontology getTypeOntology() {
		return typeOntology;
	}

	/**
	 * Returns the topic search ontology
	 */
	public Ontology getTopicOntology() {
		return topicOntology;
	}

	/**
	 * return the query reply Listener
	 */
	public SearchManager getSearchManager() {
		return mSearchManager;
	}

	/**
	 * return mainWindow instance of KaonP2P.
	 */
	public MainWindow getMainWindow() {
		return this.mainWindow;
	}

	/**
	 * Return the ontology Vocabulary for search details.
	 */
	public List getOntologyAttributes() {
		return mVocabulary.getAttributes();
	}

	public Entity getTypeOntologyRoot() {
		return typeRoot;
	}

	public Entity getTopicOntologyRoot() {
		return topicRoot;
	}

	public void setTypeOntologyRoot(Entity root) {
		this.typeRoot = root;
	}

	public void setTopicOntologyRoot(Entity root) {
		this.topicRoot = root;
	}

	/**
	 * Returns the default property value as boolean.
	 * 
	 * @param key
	 *            the key of the property.
	 * @return the default value of the property.
	 */
	public boolean defaultPropertyBoolean(String key) {
		return mProperties.getDefaultBoolean(key);
	}

	/**
	 * Returns the default property value as integer.
	 * 
	 * @param key
	 *            the key of the property.
	 * @return the default value of the property.
	 */
	public int defaultPropertyInteger(String key) {
		return mProperties.getDefaultInteger(key);
	}

	/**
	 * Returns the default property value as string.
	 * 
	 * @param key
	 *            the key of the property.
	 * @return the default value of the property.
	 */
	public String defaultPropertyString(String key) {
		return mProperties.getDefaultString(key);
	}

	/**
	 * Returns the property value as boolean.
	 * 
	 * @param key
	 *            the key of the property.
	 * @return the value of the property.
	 */
	public boolean propertyBoolean(String key) {
		return mProperties.getBoolean(key);
	}

	/**
	 * Returns the property value as integer.
	 * 
	 * @param key
	 *            the key of the property.
	 * @return the value of the property.
	 */
	public int propertyInteger(String key) {
		return mProperties.getInteger(key);
	}

	/**
	 * Returns the property value as string.
	 * 
	 * @param key
	 *            the key of the property.
	 * @return the value of the property.
	 */
	public String propertyString(String key) {
		return mProperties.getString(key);
	}

	/**
	 * Sets the property value by the delivered string.
	 * 
	 * @param key
	 *            the key of the property.
	 * @param value
	 *            the value of the property.
	 */
	public void setProperty(String key, String value) {
		mProperties.setString(key, value);
	}

	private String serializeFileName(String filename) {
		String seperator = System.getProperty("file.separator");
		filename = filename.replace(seperator.charAt(0), '/');
		return filename;
	}

	public Ontology getLocalHostOntology() {
		Ontology localHostOntology = null;
		try {
			/*For the second file */ 
			localHostOntology = connection.openOntology("kaon2rmi://localhost?"
					+ localRegistryOntology.getOntologyURI(),
					new HashMap<String, Object>());
			
			/* For 1 file only  
			localHostOntology = connection.openOntology(localRegistryOntology.getOntologyURI(),
							new HashMap<String, Object>());
			*/
		} catch (Exception e) {
			System.err.println(e + " getLocalHostOntology()in Oyster2.");
		}
		return localHostOntology;
	}

	public File getLocalRegistryFile() {
		return this.localRegistryFile;
	}


	public String getPeerDescOntologyURI() {
		return this.peerDescOntologyURI;
	}

	public KAON2Connection getConnection() {
		return this.connection;
	}

	public DefaultOntologyResolver getResolver() {
		return this.resolver;
	}

	public List getSearchDetails() {
		return this.searchDetails;
	}

	public String getImageLocation() {
		return this.imageLocation;
	}

}

/*
 * 0
 * 
 * String propFile = null; 
 * if (properties != null) 
 * 	propFile = properties.getProperty(INIT_PROPERTY_FILE); 
 * if (propFile == null)
 *  propFile = Constants.PROPERTY_FILE; 
 *  mProperties.init(propFile,properties);
 */

/*
 * 1			 
if ((store.getString(Constants.Basic_1) != null)
		&& (store.getString(Constants.Basic_1).length() > 0))
	resolver
			.registerOntology("file:///"
					+ serializeFileName(store
							.getString(Constants.Basic_1)));
if ((store.getString(Constants.Basic_2) != null)
		&& (store.getString(Constants.Basic_2).length() > 0))
	resolver
			.registerOntology("file:///"
					+ serializeFileName(store
							.getString(Constants.Basic_2)));
if ((store.getString(Constants.Basic_3) != null)
		&& (store.getString(Constants.Basic_3).length() > 0))
	resolver
			.registerOntology("file:///"
					+ serializeFileName(store
							.getString(Constants.Basic_3)));
if ((store.getString(Constants.Basic_4) != null)
		&& (store.getString(Constants.Basic_4).length() > 0))
	resolver
			.registerOntology("file:///"
					+ serializeFileName(store
							.getString(Constants.Basic_4)));
							
*/

/*
 * 2
this.virtualOntologyFile = new File(serializeFileName(store
		.getString(Constants.VirtualOntology)));
if ((!virtualOntologyFile.exists())
		|| (virtualOntologyFile.length() <= 0)) {
	System.out.println("virtualOntologyFile doesn't exist!"
			+ " "
			+ serializeFileName(store
					.getString(Constants.VirtualOntology))
			+ " creating...");
	virtualOntologyFile.createNewFile();
	resolver.registerReplacement(
			"http://localhost/virtualOntology", "file:///"
					+ serializeFileName(store
							.getString(Constants.VirtualOntology)));
	virtualOntologyURI = "http://localhost/virtualOntology";
} else
	virtualOntologyURI = resolver.registerOntology("file:///"
			+ serializeFileName(store
					.getString(Constants.VirtualOntology)));
					
*/

/*
* 3
this.virtualOntology = connection.createOntology(
		virtualOntologyURI, new HashMap<String, Object>());
this.virtualOntology.addToImports(localRegistryOntology);
*/

/*
public void setVirtualOntology(Ontology ontology) {
	this.virtualOntology = ontology;
}

public Ontology getVirtualOntology() {
	return this.virtualOntology;
}
public File getVirtualOntologyFile() {
		return this.virtualOntologyFile;
}
 */
