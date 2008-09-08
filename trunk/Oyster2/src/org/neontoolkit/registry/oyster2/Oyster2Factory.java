package org.neontoolkit.registry.oyster2;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

import org.neontoolkit.registry.core.AdvertInformer;
import org.neontoolkit.registry.core.ExchangeInitiator;
import org.neontoolkit.registry.core.Exchanger;
import org.neontoolkit.registry.core.LocalExpertiseRegistry;
import org.neontoolkit.registry.core.OntoVocabulary;
import org.neontoolkit.registry.oyster2.Constants;
import org.neontoolkit.registry.oyster2.Properties;
import org.neontoolkit.registry.util.GUID;
import org.semanticweb.kaon2.api.*;
//import org.semanticweb.kaon2.api.logic.*;

/**
 * This class represents the Oyster2 facility. It is responsible for all
 * activities in the Oyster2 network (search, exchange). This class implements
 * the <code>Singleton</code> pattern as defined by Gamma et.al. As there
 * could only exist one instance of this class, other clients must use the
 * <code>sharedInstance</code> function to use this class.
 * 
 * @author <a href="mailto:Guo Rui <rui.guo@epfl.ch>">GuoRui</a>
 * @version 1.0.0
 */
public class Oyster2Factory {

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
	private static final Oyster2Factory SHARED_INSTANCE = new Oyster2Factory();

	/**
	 * The list of bootstrap hosts used to join the network at the first time.
	 */
	private Vector mBootstrapHosts = new Vector();

	/**
	 * The file storing the localExpertiseRegistry.
	 */
	private File localRegistryFile = null;

	/**
	 * The local host.
	 */
	private XMLOyster2Host mLocalHost;

	/**
	 * The local exchange times.
	 */
	//private int mExchangeVersion;

	/**
	 * The resources.
	 */
	//private ResourceBundle mResources = null;

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
	//private QueryFormulator mFormulator;

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

	//private PreferenceStore store = new PreferenceStore("new store");
	
	/**
	 * The application properties.
	 */
	private Properties mProperties = new Properties();

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
	 * the mapping Description Ontology.
	 */
	private Ontology mappingDescOntology;
	
	/**
	 * the change Ontology.
	 */
	private Ontology owlChangeOntology;

	/**
	 * the generic change Ontology.
	 */
	private Ontology changeOntology;
	
	/**
	 * the owlodm Ontology.
	 */
	private Ontology owlodmOntology;

	/**
	 * the workflow Ontology.
	 */
	private Ontology workflowOntology;
	
	/**
	 * the superOyster registry (if specified).
	 */
	private Ontology superOysterRegistry=null;
	
	/**
	 * the uri of mapping Description Ontology.
	 */
	private String mappingDescOntologyURI;
	
	/**
	 * the uri of change Ontology.
	 */
	private String owlChangeOntologyURI;

	/**
	 * the uri of generic change Ontology.
	 */
	private String changeOntologyURI;
	
	/**
	 * the uri of owlodm Ontology.
	 */
	private String owlodmOntologyURI;
	
	/**
	 * the uri of workflow Ontology.
	 */
	private String workflowOntologyURI;
	/**
	 * the KAON2 connection used to connect to the servers.
	 */
	//private KAON2Connection connection = KAON2Manager.newConnection();
	private KAON2Connection connection = null;

	/**
	 * the ontology resolver.
	 */
	private DefaultOntologyResolver resolver = new DefaultOntologyResolver();

	/**
	 * the details of search conditions which should be specified in the
	 * property file.
	 */
	private List<String> searchDetails = new ArrayList<String>();

	private String imageLocation = "";
	
	private Map<String,Integer> offlinePeers = new HashMap<String,Integer>();
	
	private boolean isSimple=false;
	
	private boolean caching=false;
	
	private boolean workflowSupport=false;
	
	private int TIMEOUT=120000;
	
	private String superOysterIP=null; //Storage is local or in other Oyster node? DEFAUL=NULL=LOCAL
	//LOGGER
	//Create a console handler
    private ConsoleHandler handler = new ConsoleHandler();
	private Logger logger = Logger.getLogger("org.neon_toolkit.registry.Logging");
	private boolean logEnabled=false;


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
	public static Oyster2Factory sharedInstance() {
		return SHARED_INSTANCE;
	}
	
	public int retInit = 0;
	
	
	
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
	
		if (getLogEnabled()) {
			logger.addHandler(handler);
			logger.setUseParentHandlers(false);
		}else {
			logger.setLevel(Level.OFF);
		}
		
		//LAST FIX
		retInit = 0;
		
		//0//
		
		if (connection == null)  //new version of kaon2 (01/2007)
			connection = KAON2Manager.newConnection();
		
		if (mProperties.getString(Constants.Image).contains("://")) imageLocation = mProperties.getString(Constants.Image);
		else imageLocation = "file:"+serializeFileName(mProperties.getString(Constants.Image));
		
		try {
			//1//			
			if ((mProperties.getString(Constants.PDOntology) != null)
					&& (mProperties.getString(Constants.PDOntology).length() > 0))
				if (mProperties.getString(Constants.PDOntology).contains("://"))
					peerDescOntologyURI = resolver.registerOntology(
							mProperties.getString(Constants.PDOntology));
				else
					peerDescOntologyURI = resolver.registerOntology("file:"
						+ serializeFileName(mProperties
								.getString(Constants.PDOntology)));
			if ((mProperties.getString(Constants.MDOntology) != null)
					&& (mProperties.getString(Constants.MDOntology).length() > 0))
				if (mProperties.getString(Constants.MDOntology).contains("://"))
					mappingDescOntologyURI = resolver.registerOntology(
							mProperties.getString(Constants.MDOntology));
				else
					mappingDescOntologyURI = resolver.registerOntology("file:"
						+ serializeFileName(mProperties
								.getString(Constants.MDOntology)));
			if ((mProperties.getString(Constants.owlChangeOntology) != null)
					&& (mProperties.getString(Constants.owlChangeOntology).length() > 0))
				if (mProperties.getString(Constants.owlChangeOntology).contains("://"))
					owlChangeOntologyURI = resolver.registerOntology(
							mProperties.getString(Constants.owlChangeOntology));
				else
					owlChangeOntologyURI = resolver.registerOntology("file:"
						+ serializeFileName(mProperties
								.getString(Constants.owlChangeOntology)));
			if ((mProperties.getString(Constants.TypeOntology) != null)
					&& (mProperties.getString(Constants.TypeOntology).length() > 0))
				if (mProperties.getString(Constants.TypeOntology).contains("://"))
					typeOntologyURI = resolver.registerOntology(
							mProperties.getString(Constants.TypeOntology));
				else
					typeOntologyURI = resolver.registerOntology("file:"
						+ serializeFileName(mProperties
								.getString(Constants.TypeOntology)));
			if ((mProperties.getString(Constants.TopicOntology) != null)
					&& (mProperties.getString(Constants.TopicOntology).length() > 0))
				if (mProperties.getString(Constants.TopicOntology).contains("://"))
					topicOntologyURI = resolver.registerOntology(
							mProperties.getString(Constants.TopicOntology));
				else
					topicOntologyURI = resolver.registerOntology("file:"
							+ serializeFileName(mProperties
									.getString(Constants.TopicOntology)));
			if ((mProperties.getString(Constants.owlodmOntology) != null)
					&& (mProperties.getString(Constants.owlodmOntology).length() > 0))
				if (mProperties.getString(Constants.owlodmOntology).contains("://"))
					owlodmOntologyURI = resolver.registerOntology(
							mProperties.getString(Constants.owlodmOntology));
				else
					owlodmOntologyURI = resolver.registerOntology("file:"
							+ serializeFileName(mProperties
									.getString(Constants.owlodmOntology)));
			if ((mProperties.getString(Constants.changeOntology) != null)
					&& (mProperties.getString(Constants.changeOntology).length() > 0))
				if (mProperties.getString(Constants.changeOntology).contains("://"))
					changeOntologyURI = resolver.registerOntology(
							mProperties.getString(Constants.changeOntology));
				else
					changeOntologyURI = resolver.registerOntology("file:"
							+ serializeFileName(mProperties
									.getString(Constants.changeOntology)));
			if ((mProperties.getString(Constants.workflowOntology) != null)
					&& (mProperties.getString(Constants.workflowOntology).length() > 0))
				if (mProperties.getString(Constants.workflowOntology).contains("://"))
					workflowOntologyURI = resolver.registerOntology(
							mProperties.getString(Constants.workflowOntology));
				else
					workflowOntologyURI = resolver.registerOntology("file:"
							+ serializeFileName(mProperties
									.getString(Constants.workflowOntology)));
			
			this.localRegistryFile = new File(serializeFileName(mProperties
					.getString(Constants.LocalRegistry)));
			
			
			if ((!localRegistryFile.exists())
					|| (localRegistryFile.length() <= 0)) {
				
				/* For the 1 file  
				resolver.registerReplacement(Constants.LocalRegistryURI,
						"file:///"+serializeFileName(store.getString(Constants.LocalRegistry)));
				*/
				
				/* For the second file */ 
				
				if (getSuperOysterIP()==null){
					if (!localRegistryFile.getParentFile().exists())
						localRegistryFile.getParentFile().mkdir();
				
					localRegistryFile.createNewFile();
					resolver.registerReplacement(Constants.LocalRegistryURI,
						"kaon2rmi://localhost?" + Constants.LocalRegistryURI);
				
					localRegistryURI = Constants.LocalRegistryURI;				
					connection.setOntologyResolver(resolver);
				
					this.localRegistryOntology = connection.createOntology(
						localRegistryURI, new HashMap<String, Object>());
				
					this.localRegistryOntology.addOntologyProperty(
						Constants.VERSIONINFO, Integer.toString(1));
				}
				
				/*
				 * NOT NECESSARY, AND PERFORMANCE INCREASES DRAMATICALLY!
				 *
				this.topicOntology = connection.openOntology(
						topicOntologyURI, new HashMap<String, Object>());
				this.peerDescOntology = connection.openOntology(
						peerDescOntologyURI, new HashMap<String, Object>());
				this.mappingDescOntology = connection.openOntology(
						mappingDescOntologyURI, new HashMap<String, Object>());
				this.owlChangeOntology = connection.openOntology(
						owlChangeOntologyURI, new HashMap<String, Object>());
				this.workflowOntology = connection.openOntology(
						workflowOntologyURI, new HashMap<String, Object>());
				this.localRegistryOntology.addToImports(peerDescOntology);
				this.localRegistryOntology.addToImports(mappingDescOntology);
				this.localRegistryOntology.addToImports(owlChangeOntology);
				this.localRegistryOntology.addToImports(topicOntology);
				this.localRegistryOntology.addToImports(workflowOntology);
				
				this.mInformer = new AdvertInformer();
				Rule subTopicRule = this.mInformer.createSubTopicRule(topicOntology);
				this.mInformer.addRule(this.localRegistryOntology, subTopicRule);
				*/
				
			} else {
				/* For the 1 file  
				localRegistryURI=resolver.registerOntology("file:///"
						+ serializeFileName(store
								.getString(Constants.LocalRegistry)));
				*/
				
				/* For the second file */ 
				
				if (getSuperOysterIP()==null){
					resolver.registerReplacement(Constants.LocalRegistryURI,
						"kaon2rmi://localhost?" + Constants.LocalRegistryURI);
					localRegistryURI = Constants.LocalRegistryURI;
				
					connection.setOntologyResolver(resolver);
					this.localRegistryOntology = connection.openOntology(
						localRegistryURI, new HashMap<String, Object>());
				}
				
			}

			//2//
			
			connection.setOntologyResolver(resolver);
			this.peerDescOntology = connection.openOntology(
					peerDescOntologyURI, new HashMap<String, Object>());
			this.mappingDescOntology = connection.openOntology(
					mappingDescOntologyURI, new HashMap<String, Object>());
			this.changeOntology = connection.openOntology(
					changeOntologyURI, new HashMap<String, Object>());
			this.owlChangeOntology = connection.openOntology(
					owlChangeOntologyURI, new HashMap<String, Object>());			
			this.topicOntology = connection.openOntology(topicOntologyURI,
					new HashMap<String, Object>());
			this.workflowOntology = connection.openOntology(
					workflowOntologyURI, new HashMap<String, Object>());
			this.owlodmOntology = connection.openOntology(
					owlodmOntologyURI, new HashMap<String, Object>());
			this.typeOntology = connection.openOntology(typeOntologyURI,
					new HashMap<String, Object>());
			//3//
			
		} catch (KAON2Exception e) {
			e.printStackTrace();
			retInit=1;
			return;
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			retInit=1;
			return;
			
		}
		
		
		this.mInformer = new AdvertInformer();
		this.mLocalRegistry = new LocalExpertiseRegistry();
		
		if (getSuperOysterIP()!=null)
			this.superOysterRegistry = mInformer.openRemoteRegistry(getSuperOysterIP());
		
		mInformer.init();
		mLocalRegistry.init();
		String uid = mInformer.getLocalUID();
		//System.out.println("local peer UID is :" + uid);
		GUID localGUID = GUID.getGUID(uid);
		try {
			mLocalHost = new XMLOyster2Host(localGUID, InetAddress
				.getLocalHost(), 1099);
		} catch (UnknownHostException e) {
			e.printStackTrace();	
			retInit=1;
			return;
		}
		
		if (getSuperOysterIP()==null){
			if ((mProperties.getString(Constants.LocalPeerName) != null)
				&& (mProperties.getString(Constants.LocalPeerName).length() > 0))
				mInformer.setLocalPeer(mProperties.getString(Constants.LocalPeerName),
					uid, mLocalHost.getAddress(), mProperties
							.getString(Constants.LocalPeerType));
			if ((mProperties.getString(Constants.BootStrapPeerName) != null)
				&& (mProperties.getString(Constants.BootStrapPeerName).length() > 0))
				mInformer.addBootPeer(mProperties.getString(Constants.BootStrapPeerName),
					mProperties.getString(Constants.BootStrapPeerUID), mProperties
							.getString(Constants.BootStrapPeerIP));
		}
		
		Entity typeRoot = KAON2Manager.factory().owlClass(
				typeOntology.getOntologyURI()
						+ mProperties.getString(Constants.TypeOntologyRoot));
		Entity topicRoot = KAON2Manager.factory().owlClass(
				topicOntology.getOntologyURI()
						+ mProperties.getString(Constants.TopicOntologyRoot));

		setTypeOntologyRoot(typeRoot);
		setTopicOntologyRoot(topicRoot);
		
		if (!isSimple){
			mExchangeInitiator = new ExchangeInitiator();
			mExchangeInitiatorThread = new Thread(mExchangeInitiator,"ExchangeInitiator");
			mExchangeInitiatorThread.setDaemon(true);
			mExchangeInitiatorThread.start();
		}else{
			if (getSuperOysterIP()==null)
				mInformer.updateLocalIP();
		}
	}
	
	
	public void initUI(){
		try {
		searchDetails.add(mProperties.getString(Constants.SearchCondition_1));
		searchDetails.add(mProperties.getString(Constants.SearchCondition_2));
		searchDetails.add(mProperties.getString(Constants.SearchCondition_3));
		searchDetails.add(mProperties.getString(Constants.SearchCondition_4));
		searchDetails.add(mProperties.getString(Constants.SearchCondition_5));
		} catch (Exception e) {
			e.printStackTrace();
			retInit=1;
			return;
		}
	}
	
	
	/*
	 * Shutdowns the oyster2 facility.
	 */

	synchronized public void shutdown() {
		System.out.println("shutting down");
		System.out.println(" Searcher");
		getSearchManager().stopSearchFull();
		try {
			System.out.println(" KAON2 saving");
			if (this.localRegistryOntology!=null) this.localRegistryOntology.persist();			
			if (this.mLocalRegistry!=null) this.mLocalRegistry.save();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		try {
			if (!isSimple){
				System.out.println(" Exchange Initiator");
				if (this.mExchangeInitiator!=null) {
					this.mExchangeInitiator.shutdown();
					this.mExchangeInitiator=null;
				}
				if (this.mExchangeInitiatorThread!=null) {
					if (this.mExchangeInitiatorThread.isAlive()){
						this.mExchangeInitiatorThread.interrupt();
						this.mExchangeInitiatorThread.join(2000);
					}
					this.mExchangeInitiatorThread=null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try{
			System.out.println(" Closing connection");
			if (this.connection!=null) {
				this.connection.close();
				this.connection=null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			int numThreads = Thread.activeCount();
	        Thread[] threads = new Thread[numThreads*2];
	        numThreads = Thread.enumerate(threads);
	        // Enumerate each thread
	        for (int i=0; i<numThreads; i++) {
	            Thread thread = threads[i];
	            if (thread!=Thread.currentThread()){ 
	            	ThreadGroup root = thread.getThreadGroup().getParent();
	            	if (root==Thread.currentThread().getThreadGroup()){
	            		try{
	            			System.out.println("Killing thread: "+thread.getName());
	            			thread.interrupt();
	            			thread.join(2000);
	            		} catch (Exception e) {
	            			e.printStackTrace();
	            		}
	            	}
	            }
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
		//mExchangeInitiator.randomExchange(hosts, recursion, lCurrent, false);
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

	public Properties getProperties() {
		return mProperties;
	}

	public void setProperties(Properties mprop) {
		this.mProperties = mprop;
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
	 * Returns the peer description ontology
	 */
	public Ontology getPeerOntology() {
		return peerDescOntology;
	}
	
	/**
	 * Returns the mapping description ontology
	 */
	public Ontology getMappingOntology() {
		return mappingDescOntology;
	}
	
	/**
	 * Returns the OWL change description ontology
	 */
	public Ontology getOWLChangeOntology() {
		return owlChangeOntology;
	}
	/**
	 * Returns the change description ontology
	 */
	public Ontology getChangeOntology() {
		return changeOntology;
	}
	/**
	 * Returns the owlodm description ontology
	 */
	public Ontology getOWLODMOntology() {
		return owlodmOntology;
	}
	/**
	 * Returns the workflow description ontology
	 */
	public Ontology getWorkflowOntology() {
		return workflowOntology;
	}
	
	/**
	 * return the query reply Listener
	 */
	public SearchManager getSearchManager() {
		return mSearchManager;
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

		
	private String serializeFileName(String filename) {
		String seperator = System.getProperty("file.separator");
		filename = filename.replace(seperator.charAt(0), '/');
		return filename;
	}

	public Ontology getLocalHostOntology() {
		if (getSuperOysterIP()!=null) //To add super oyster functionality
				return superOysterRegistry;//mInformer.openRemoteRegistry(getSuperOyster());
		return localRegistryOntology;
		//1++
	}

	public File getLocalRegistryFile() {
		if (getSuperOysterIP()!=null) //To add super oyster functionality
			return null;
		return this.localRegistryFile;
	}

	public String getPeerDescOntologyURI() {
		return this.peerDescOntologyURI;
	}
	
	public String getMappingDescOntologyURI() {
		return this.mappingDescOntologyURI;
	}
	
	public String getOWLChangeOntologyURI() {
		return this.owlChangeOntologyURI;
	}
	
	public String getChangeOntologyURI() {
		return this.changeOntologyURI;
	}
	
	public String getOWLODMOntologyURI() {
		return this.owlodmOntologyURI;
	}
	
	public String getWorkflowOntologyURI() {
		return this.workflowOntologyURI;
	}
	
	public String getTypeOntologyURI() {
		return this.typeOntology.getOntologyURI();
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
	
	synchronized public Set<String> getOfflinePeers() {
		return this.offlinePeers.keySet();
	}

	
	synchronized public void addOfflinePeer(String peer) {
		this.offlinePeers.put(peer,Constants.offlineTries);
	}

	synchronized public boolean isOfflinePeer(String peer) {
		if (this.offlinePeers.containsKey(peer)) return true;
		else return false;
	}
	
	synchronized public void  updateOfflinePeerList(){
		List<String> todel=new LinkedList<String>();
		Set<Entry<String,Integer>> entries = this.offlinePeers.entrySet();
		for(Map.Entry<String, Integer> entry : entries) {   
			Integer value = entry.getValue();
			if (value<1){
				String key = entry.getKey();
				todel.add(key);//this.offlinePeers.remove(key);
			}else{
				value--;
				entry.setValue(value);
			}
		}
		if (!todel.isEmpty()){
			Iterator i=todel.iterator();
			while (i.hasNext()){
				this.offlinePeers.remove((String)i.next());
			}
		}
		/*System.out.println("offline peers: ");
		for(Object key : this.offlinePeers.keySet()) {
			Object value = this.offlinePeers.get(key);   
			System.out.println(key + " = " + value);
		}*/
	}
	
	public void setIsSimple(boolean value) {
		this.isSimple=value;
	}
	
	public boolean getIsSimple() {
		return this.isSimple;
	}
	
	public void setCaching(boolean value) {
		this.caching=value;
	}
	
	public boolean getCaching() {
		return this.caching;
	}
	
	public void setWorkflowSupport(boolean value) {
		this.workflowSupport=value;
	}
	
	public boolean getWorkflowSupport() {
		return this.workflowSupport;
	}
	
	public void setLogEnabled(boolean value) {
		this.logEnabled=value;
	}
	
	public boolean getLogEnabled() {
		return this.logEnabled;
	}
	
	public Logger getLogger() {
		return this.logger;
	}
	
	public void setQueryTimeOut(int t)
    {
		this.TIMEOUT=t;
    }
	
	public int getQueryTimeOut()
    {
		return this.TIMEOUT;
    }
	
	public void setSuperOysterIP(String t)
    {
		this.superOysterIP=t;
    }
	
	public String getSuperOysterIP()
    {
		return this.superOysterIP;
    }
}

