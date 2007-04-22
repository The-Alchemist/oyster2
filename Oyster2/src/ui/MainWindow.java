package ui;

import ui.action.*;

import java.util.List;
import java.util.*;
import java.net.*;
import javax.swing.event.DocumentEvent.EventType;



import oyster2.*;
//import oyster2.KaonP2P;
//import kaonP2P.Properties;
//import kaonP2P.SearchingScope;
//import kaonP2P.SearchManager;
//import kaonP2P.Condition;
//import kaonP2P.KaonP2PQuery;
import core.*;
import ui.provider.*;
import ui.action.*;
import util.*;

import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManagerOverrides;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableTreeViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.MenuItem;

import org.semanticweb.kaon2.api.*;
import org.semanticweb.kaon2.api.owl.elements.*;

public class MainWindow extends ApplicationWindow {

	public static final String ResourceType = "resourceType";
	public static final String ResourceTopic = "resourceTopic";

	//--Default values of this component properties
	private int locationX = 0;
	private int locationY = 0;
	private int windowWidth = 800;
	private int windowHeight = 600;
	private int westPanelSize = 35;
	private int eastPanelSize = 100;
	private int northPanelSize = 100;
	private int southPanelSize = 40;

	//--Controls
	private ResultViewer resultViewer;
	private ResultViewerRegistry resultViewerRegistry;
	private DetailViewer detailViewer;
	private TreeViewer typeOntologyViewer;
	private TreeViewer dmozTopicsViewer;
	private TreeViewer ontologyTopicsViewer;
	private Text searchField;
	private Text topicField;
	private TreeViewer hitlist;
	private SashForm horizontalSashForm;
	private SashForm verticalSashForm;
	private SashForm verticalSashFormRegistry;
	private Button searchButton;

	//--Actions
	private ImportOntologyDocument importOntologyAction = new ImportOntologyDocument();
	private ImportMappingOntology importMappingAction = new ImportMappingOntology();
	private ExitAction exitAction = new ExitAction(this);
	private NewEntryAction newEntryAction = new NewEntryAction(this);
	private EditEntryAction editEntryAction = new EditEntryAction();
	private SaveEntryAction saveEntryAction = new SaveEntryAction(this);
	private RemoveEntryAction removeEntryAction = new RemoveEntryAction(this);
	private OpenPreferencesAction openPreferencesAction = new OpenPreferencesAction(this);
	private OpenRegistryAction openRegistryAction = new OpenRegistryAction();
	private OpenOntologyAction openOntologyAction = new OpenOntologyAction();
	private RemoveAllEntriesAction removeAllEntriesAction = new RemoveAllEntriesAction(this);
	private AboutKaonP2PAction aboutBibsterAction = new AboutKaonP2PAction(this);

	//--That variables should be described for swrc
	private List searchDetails = new ArrayList();
	//--That variables should be described for omv
	private List ontologyDetails = new ArrayList();
	//--store search detail values
	private List<Text> searchFields = new ArrayList<Text>();
	//--store ontology detail values
	private List<Text> ontologyFields = new ArrayList<Text>();
	
	private Color backgroundColor;

	/*TODO private EntryFinder finder;*/

	private Result result;
	private ResultRegistry resultRegistry;
	private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private List vocabulary;
	private SearchManager searchManager = mOyster2.getSearchManager();
	private SearchingScope searchingScope;
	private IProgressMonitor progresMonitor;
	private boolean mappingUsed = true;
	private boolean manualSelected = false;

	
	public MainWindow(Shell shell) {
		super(shell);
		addMenuAndStatus();
		//--Search details
		/*vocabulary = mKaonP2P.getOntologyAttributes();
		Collections.shuffle(vocabulary);
		Iterator it = vocabulary.iterator();
		for(int i =0;i<6 && it.hasNext();i++){
			String detail = (String)it.next();
			searchDetails.add(detail);
		}*/
		
		searchDetails = mOyster2.getSearchDetails();
		ontologyDetails = mOyster2.getSearchDetails();
		/*
		ontologyDetails.add("omv:name");
		ontologyDetails.add("omv:keywords");
		ontologyDetails.add("omv:hasDomain");
		ontologyDetails.add("omv:URI");
		ontologyDetails.add("omv:hasCreator");
		*/		
	}

	public int open() {
		try{
			System.out.println("window opening...");
			addMenuAndStatus();
		return super.open();
		}catch(Exception e){
			System.out.println(e.toString()+":open exception");
		}
		return 0;
	}

	private void addMenuAndStatus() {
		this.addStatusLine();
		this.addMenuBar();
	}

	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setImage(createIconImage());
	}
	
	private synchronized Image createIconImage(){
		String iconImageKey = "oyster.jpg";
		try{
		if (JFaceResources.getImageRegistry().get(iconImageKey) == null) {
			URL url = new URL(mOyster2.getImageLocation());
			ImageDescriptor imageDesc = ImageDescriptor
					.createFromURL(url);
			Image image = imageDesc.createImage();
			JFaceResources.getImageRegistry().put(iconImageKey, image);
		}
		}catch(Exception e){
			System.err.println(e.toString()+"when createIconImage()");
		}
		return JFaceResources.getImageRegistry().get(iconImageKey);
	}
	
	protected Control createContents(Composite parent) {
			//int value;
            locationX = 0;
            locationY = 0;
			this.getShell().setLocation(locationX, locationY);
			this.getShell().setSize(windowWidth, windowHeight);
			String title = "Oyster2";
			try {
				/*title += " - "
						+ mKaonP2P.getLocalHost().getName();*/
				title +="-"+mOyster2.getLocalAdvertInformer().getLocalPeerName();
			} catch (Exception ignore) {
				System.err.println("localHost getName():error in MainWindow!");
			}
			this.getShell().setText(title);
			backgroundColor = this.getShell().getDisplay().getSystemColor(
					SWT.COLOR_WHITE);

			horizontalSashForm = new SashForm(parent, SWT.HORIZONTAL);
			horizontalSashForm.setBackground(backgroundColor);
			
			final CTabFolder tabFolder = new CTabFolder(horizontalSashForm, SWT.TOP
					| SWT.FLAT);
			
			
			/*---------------Ontology search pane---------------*/
			CTabItem searchPane = new CTabItem(tabFolder, SWT.NULL | SWT.FLAT);
			searchPane.setText("Search registry");
			Composite searchPropertiesPanel = createSearchPropertiesPanel(tabFolder);
			searchPropertiesPanel.setBackground(backgroundColor);
			searchPane.setControl(searchPropertiesPanel);
			
			/*---------------Registry ontology search pane-------------*/
			CTabItem registryPane = new CTabItem(tabFolder, SWT.NULL
					| SWT.FLAT);
			registryPane.setText("Registry monitor");
			Composite registryMonitorPanel = createRegistryMonitorPanel(tabFolder);
			registryMonitorPanel.setBackground(backgroundColor);
			registryPane.setControl(registryMonitorPanel);
			
			/*Default view*/
			tabFolder.setSelection(searchPane);
			
			verticalSashForm = new SashForm(horizontalSashForm, SWT.VERTICAL);
			verticalSashForm.setBackground(backgroundColor);
			
			final CTabFolder tabFolderResult = new CTabFolder(verticalSashForm, SWT.TOP
					| SWT.FLAT);
	
			/*---------------Ontology result pane---------------*/
			CTabItem searchResult = new CTabItem(tabFolderResult, SWT.NULL | SWT.FLAT);
			searchResult.setText("ResultSearch");
			Composite resultPanel = createResultPanel(tabFolderResult);
			resultPanel.setBackground(backgroundColor);
			searchResult.setControl(resultPanel);
			
			/*---------------Registry result pane---------------*/
			CTabItem registryResult = new CTabItem(tabFolderResult, SWT.NULL | SWT.FLAT);
			registryResult.setText("ResultRegistry");
			Composite resultPanelRegistry = createResultPanelRegistry(tabFolderResult);
			resultPanelRegistry.setBackground(backgroundColor);
			registryResult.setControl(resultPanelRegistry);
			
			
			/*Default View*/
			tabFolderResult.setSelection(searchResult);
			tabFolderResult.setTabHeight(0);
			
			/*---------------view switch from one tabItem to another-------- */
		    tabFolder.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e) {
					if (tabFolder.getSelectionIndex()==0)
						tabFolderResult.setSelection(0);
					else
						tabFolderResult.setSelection(1);
					
					resultViewer.clear();
					resultViewerRegistry.clear();
					detailViewer.clear();
				}

			});
			/*---------------view switch from one tabItem to another--------*/ 
		    tabFolderResult.addListener(SWT.Selection, new Listener(){
				public void handleEvent(Event e) {
					resultViewer.clear();
					resultViewerRegistry.clear();
					detailViewer.clear();
				}

			});
			 
			
			Composite detailsPanel = createDetailsPanel(verticalSashForm);
			
			/* ORI
			Composite resultPanel = createResultPanel(verticalSashForm);
			resultPanel.setBackground(backgroundColor);
			Composite detailsPanel = createDetailsPanel(verticalSashForm);
			*/
			horizontalSashForm.setWeights(new int[] { 40,60 });
			verticalSashForm.setWeights(new int[] { 60,40 });
			return horizontalSashForm;
	}
	
	private Composite createSearchPropertiesPanel(Composite parent) {
		Composite searchPropertiesPanel = new Composite(parent, SWT.NULL);
		searchPropertiesPanel.setBackground(backgroundColor);
		//Composite manualSearchPanel = createManualSearchPanel(searchPropertiesPanel);
		//manualSearchPanel.setBackground(backgroundColor);
		Composite peerSelectionPanel = createScopeSelectionPanel(searchPropertiesPanel);
		peerSelectionPanel.setBackground(backgroundColor);
		Composite keywordSearchPanel = createKeywordSearchPanel(searchPropertiesPanel);
		keywordSearchPanel.setBackground(backgroundColor);
		Composite queryDetailsPanel = createQueryDetailsPanel(searchPropertiesPanel);
		queryDetailsPanel.setBackground(backgroundColor);
		Composite searchPanel = createSearchButtonPanel(searchPropertiesPanel,false);
		//--------------------------------------------------------------------------------

		FormLayout layout = new FormLayout();
		layout.marginWidth = 3;
		layout.marginHeight = 3;
        //--search Panel: manual or automatic
		FormData data1 = new FormData();
		data1.left = new FormAttachment(0, 0);
		data1.right = new FormAttachment(100, 0);
		data1.top = new FormAttachment(0, 0);
		//manualSearchPanel.setLayoutData(data1);
		peerSelectionPanel.setLayoutData(data1);
		//Keyword based search
		FormData data1a = new FormData();
		data1a.left = new FormAttachment(0, 0);
		data1a.right = new FormAttachment(100, 0);
		data1a.top = new FormAttachment(peerSelectionPanel);
		data1a.bottom = new FormAttachment(28, 0);
		keywordSearchPanel.setLayoutData(data1a);
		
		//--queryDatailPanel is below the peerSelection Panel:publication,details,topic fields
		FormData data2 = new FormData();
		data2.left = new FormAttachment(0, 0);
		data2.right = new FormAttachment(100, 0);
		data2.top = new FormAttachment(keywordSearchPanel);
		//data2.top = new FormAttachment(manualSearchPanel,0);
		data2.bottom = new FormAttachment(90, 0);
		queryDetailsPanel.setLayoutData(data2);

		//the search buttons are below the queryDetail panel.
		FormData data3 = new FormData();
		data3.left = new FormAttachment(0, 0);
		data3.right = new FormAttachment(100, 0);
		data3.top = new FormAttachment(queryDetailsPanel);
		searchPanel.setLayoutData(data3);

		
		searchPropertiesPanel.setLayout(layout);
		
		
		return searchPropertiesPanel;
	}
	
	/*----------------------Registry Monitor start-------------------------*/
	
	private Composite createRegistryMonitorPanel(Composite parent){
		Composite RegistryMonitorPanel = new Composite(parent,SWT.NULL);
		Composite peerSelectionPanel = createPeerSelectionPanel(RegistryMonitorPanel,true);
		Composite ontologySearchPanel = createOntologySearchPanel(RegistryMonitorPanel);
		Composite searchButton = createSearchButtonPanel(RegistryMonitorPanel,true);
		FormLayout layout = new FormLayout();
		layout.marginWidth = 3;
		layout.marginHeight = 3;
        //--peerSelection Panel: local or automatic
		FormData data1 = new FormData();
		data1.left = new FormAttachment(0, 0);
		data1.right = new FormAttachment(100, 0);
		data1.top = new FormAttachment(0, 0);
		peerSelectionPanel.setLayoutData(data1);
		//--queryDatailPanel is below the peerSelection Panel:publication,details,topic fields
		FormData data2 = new FormData();
		data2.left = new FormAttachment(0, 0);
		data2.right = new FormAttachment(100, 0);
		data2.top = new FormAttachment(peerSelectionPanel);
		data2.bottom = new FormAttachment(90, 0);
		ontologySearchPanel.setLayoutData(data2);

		//the search buttons are below the queryDetail panel.
		FormData data3 = new FormData();
		data3.left = new FormAttachment(0, 0);
		data3.right = new FormAttachment(100, 0);
		data3.top = new FormAttachment(ontologySearchPanel);
		searchButton.setLayoutData(data3);

		
		RegistryMonitorPanel.setLayout(layout);
		
		return RegistryMonitorPanel;
	}
	
	/*-----------------------Registry Monitor end-------------------------*/
	
	private Composite createManualSearchPanel(Composite parent){
		Composite manualSearchPanel = new Composite(parent,SWT.NULL);
		final Button manualSearch = new Button(manualSearchPanel, SWT.RADIO);
		manualSearch.setText("ManualSearch");
		manualSearch.setBackground(backgroundColor);
		manualSearch.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (manualSearch.getSelection()) {
					manualSelected = !manualSelected;
					System.out.println(manualSelected);
					if(manualSelected)
						manualSearch.setSelection(true);
					else manualSearch.setSelection(false);
		
				
				}
			}
		});
		FormLayout layout = new FormLayout();
		layout.marginWidth = 3;
		layout.marginHeight = 3;

		FormData manualFormData = new FormData();
		manualFormData.top = new FormAttachment(0, 5);
		manualSearch.setLayoutData(manualFormData);
		
		manualSearchPanel.setLayout(layout);
		
		return manualSearchPanel;
	}
	
	private Composite createKeywordSearchPanel(Composite parent){
		
		Group searchPanel = new Group(parent, SWT.NULL);
		searchPanel.setBackground(backgroundColor);
		searchPanel
				.setFont(decorateFont(parent.getFont(), SWT.BOLD));
		searchPanel.setText("Keyword");
		searchField = new Text(searchPanel, SWT.FULL_SELECTION | SWT.BORDER);
		
		FormLayout formLayout = new FormLayout();
		formLayout.marginWidth = 3;
		formLayout.marginHeight = 3;
		searchPanel.setLayout(formLayout);
		
		FormData formData1 = new FormData();
		formData1.left = new FormAttachment(0, 0);
		formData1.right = new FormAttachment(100, 0);
		formData1.top = new FormAttachment(13, 0);
		searchField.setLayoutData(formData1);
		return searchPanel;
	}
	
	private Composite createSearchButtonPanel(Composite parent, Boolean registrySearch){
		
		Group searchPanel = new Group(parent, SWT.NULL);
		searchPanel.setBackground(backgroundColor);
		searchPanel
				.setFont(decorateFont(parent.getFont(), SWT.BOLD));
		searchPanel.setText("Search");

		Composite buttonPane = new Composite(searchPanel, SWT.NULL) {
			public void dispose() {
				System.err.println("Disposing...");
				new Exception().printStackTrace();
				super.dispose();
			}
		};
		buttonPane.setBackground(backgroundColor);
		searchButton = new Button(buttonPane, SWT.PUSH | SWT.CENTER | SWT.FLAT);
		searchButton.setText("Search Now");
		searchButton.setFont(JFaceResources.getBannerFont());
		if(registrySearch){
		searchButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
					performOntologySearch();
			}
		});
		}
		else {
			searchButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
						performDataSearch();
				}
			});
		}
		
		Button stopSearchButton = new Button(buttonPane, SWT.PUSH | SWT.CENTER
				| SWT.FLAT);
		stopSearchButton.setText("Stop Search");
		stopSearchButton.setFont(JFaceResources.getBannerFont());
		stopSearchButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if ( result != null || resultRegistry != null) {
					try {
						mOyster2.getSearchManager().stopSearch();
						searchButton.setEnabled(true);
						if (progresMonitor != null)
							progresMonitor.done();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		Button clearSearchButton = new Button(buttonPane, SWT.PUSH | SWT.CENTER
				| SWT.FLAT);
		clearSearchButton.setText("Clear");
		clearSearchButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				for (Iterator<Text> fields = searchFields.iterator(); fields
						.hasNext();) {
					Text field = fields.next();
					field.setText("");
				}
				for (Iterator<Text> fields = ontologyFields.iterator(); fields.hasNext();) {
					Text field = fields.next();
					field.setText("");
				}
				searchField.setText("");
				//typeOntologyViewer
				//		.setSelection(new StructuredSelection());
				dmozTopicsViewer.setSelection(new StructuredSelection());
				detailViewer.clear();
				resultViewer.clear();
			}
		});

		GridLayout gridLayout = new GridLayout();
		gridLayout.horizontalSpacing = 10;
		gridLayout.numColumns = 5;
		//gridLayout.makeColumnsEqualWidth = true;

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.END;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		searchButton.setLayoutData(gridData);

		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.BEGINNING;
		gridData2.horizontalSpan = 2;
		gridData2.grabExcessHorizontalSpace = true;
		stopSearchButton.setLayoutData(gridData2);

		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.END;
		//gridData3.grabExcessHorizontalSpace = true;
		clearSearchButton.setLayoutData(gridData3);

		buttonPane.setLayout(gridLayout);

		//--search buttons panel: search now, search stop, clear.
		FormLayout formLayout = new FormLayout();
		formLayout.marginWidth = 3;
		formLayout.marginHeight = 3;
		searchPanel.setLayout(formLayout);

		FormData formData1 = new FormData();
		formData1.left = new FormAttachment(0, 0);
		formData1.right = new FormAttachment(100, 0);
		formData1.top = new FormAttachment(0, 0);
		buttonPane.setLayoutData(formData1);
		return searchPanel;
	}
	
	private Composite createPeerSelectionPanel(Composite parent,boolean ontologySearch) {
		Group peerSelectionPanel = new Group(parent, SWT.NULL);
		peerSelectionPanel.setBackground(backgroundColor);
		peerSelectionPanel.setFont(decorateFont(peerSelectionPanel.getFont(),
				SWT.BOLD));	
		if(!ontologySearch){
			System.out.println(" here in no ontology search of peerselctionpanel");
			final Button mapping = new Button(peerSelectionPanel,SWT.Selection);
			mapping.setText("Enable Mapping");
			mapping.setBackground(backgroundColor);
			mapping.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					mappingUsed = !mappingUsed;
					System.err.println("mappingUsed: "+mappingUsed);			
				}
			});
			FormLayout layout = new FormLayout();
			layout.marginWidth = 3;
			layout.marginHeight = 3;
			FormData FormData = new FormData();
			FormData.top = new FormAttachment(0, 3);
			mapping.setLayoutData(FormData);
		}
		if(ontologySearch){
			System.out.println(" here in ontology search of peerselctionpanel");
			final PeerSelectionDialog dialog = new PeerSelectionDialog(new HashSet());
			final Button vo = new Button(peerSelectionPanel, SWT.RADIO);
			final Button localPeer = new Button(peerSelectionPanel, SWT.RADIO);
			final Button allPeers = new Button(peerSelectionPanel, SWT.RADIO);
			peerSelectionPanel.setText("Registry Explorer");
			vo.setText("Local Peer");
			vo.setBackground(backgroundColor);
			localPeer.setText("Local Registry Detail");
			localPeer.setBackground(backgroundColor);
			allPeers.setText("Selected Peers");
			allPeers.setBackground(backgroundColor);
			//localPeer.setSelection(true);
			searchingScope = SearchingScope.local();
			vo.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					if (vo.getSelection()) {
						List ontologyInfo = new LinkedList();
						resultRegistry = new ResultRegistry(resultViewerRegistry,Resource.RegistryResource);
						//ontologyInfo.add(mKaonP2P.getVirtualOntology());
						//result.entryReceived(mKaonP2P.getVirtualOntology());
						TreeViewer treeViewer = resultViewerRegistry.getWrapedViewer();
						treeViewer.setSelection(new StructuredSelection());
						Tree tree = treeViewer.getTree();
						tree.setSelection(new TreeItem[] { tree.getTopItem() });
					}
				}
			});
			localPeer.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					if (localPeer.getSelection()) {
						List<Ontology> ontologyInfo = new LinkedList<Ontology>();
						resultRegistry = new ResultRegistry(resultViewerRegistry,Resource.RegistryResource);
						ontologyInfo.add(mOyster2.getLocalHostOntology());
						resultRegistry.entryReceived(ontologyInfo);
						TreeViewer treeViewer = resultViewerRegistry.getWrapedViewer();
						treeViewer.setSelection(new StructuredSelection());
						Tree tree = treeViewer.getTree();
						tree.setSelection(new TreeItem[] { tree.getTopItem() });
					}
				}
			});
			allPeers.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					if (allPeers.getSelection()) {
					//allPeers.setEnabled(true);
					Set peerSet = dialog.getPeerSelection();
					resultRegistry = new ResultRegistry(resultViewerRegistry,Resource.OntologyResource);
					mOyster2.getSearchManager().addListener(resultRegistry);
					mOyster2.getSearchManager().startSearch(peerSet);
					searchingScope = SearchingScope.auto();
					}
				}
			});
		
		FormLayout layout = new FormLayout();
		layout.marginWidth = 3;
		layout.marginHeight = 3;

		FormData voFormData = new FormData();
		voFormData.top = new FormAttachment(0, 5);
		vo.setLayoutData(voFormData);
		
		FormData localPeerFormData = new FormData();
		localPeerFormData.top = new FormAttachment(vo, 5);
		localPeer.setLayoutData(localPeerFormData);

		FormData allPeersFormData = new FormData();
		allPeersFormData.top = new FormAttachment(localPeer, 5);
		allPeers.setLayoutData(allPeersFormData);

		peerSelectionPanel.setLayout(layout);
		}
		return peerSelectionPanel;
	}

	private Composite createScopeSelectionPanel(Composite parent) {
		Group peerSelectionPanel = new Group(parent, SWT.NULL);
		peerSelectionPanel.setBackground(backgroundColor);
		peerSelectionPanel.setFont(decorateFont(peerSelectionPanel.getFont(),
				SWT.BOLD));	
		
		
		final PeerSelectionDialog dialog = new PeerSelectionDialog(new HashSet());
		final Button localPeer = new Button(peerSelectionPanel, SWT.RADIO);
		final Button automaticPeer = new Button(peerSelectionPanel, SWT.RADIO);
		final Button selectedPeers = new Button(peerSelectionPanel, SWT.RADIO);
		peerSelectionPanel.setText("Search Scope");
		localPeer.setText("Local Peer");
		localPeer.setBackground(backgroundColor);
		automaticPeer.setText("Automatic Search");
		automaticPeer.setBackground(backgroundColor);
		selectedPeers.setText("Selected Peers");
		selectedPeers.setBackground(backgroundColor);
		localPeer.setSelection(true);	
		searchingScope = SearchingScope.local();
		
		localPeer.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (localPeer.getSelection()) {
					searchingScope = SearchingScope.local();
					List ontologyInfo = new LinkedList();
					result = new Result(resultViewer,Resource.RegistryResource);
				}
			}
		});
		automaticPeer.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (automaticPeer.getSelection()) {
					searchingScope = SearchingScope.auto();
					List<Ontology> ontologyInfo = new LinkedList<Ontology>();
					result = new Result(resultViewer,Resource.RegistryResource);
					//ontologyInfo.add(mOyster2.getLocalHostOntology());
					//result.entryReceived(ontologyInfo);
					//TreeViewer treeViewer = resultViewer.getWrapedViewer();
					//treeViewer.setSelection(new StructuredSelection());
					//Tree tree = treeViewer.getTree();
					//tree.setSelection(new TreeItem[] { tree.getTopItem() });
				}
			}
		});
		selectedPeers.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (selectedPeers.getSelection()) {
					searchingScope = SearchingScope.manual();
					Set peerSet = dialog.getPeerSelection();
					result = new Result(resultViewer,Resource.OntologyResource);
					//mOyster2.getSearchManager().addListener(result);
					//mOyster2.getSearchManager().startSearch(peerSet);
				}
			}
		});
		
		FormLayout layout = new FormLayout();
		layout.marginWidth = 3;
		layout.marginHeight = 3;

		FormData voFormData = new FormData();
		voFormData.top = new FormAttachment(0, 5);
		localPeer.setLayoutData(voFormData);
		
		FormData localPeerFormData = new FormData();
		localPeerFormData.top = new FormAttachment(localPeer, 5);
		automaticPeer.setLayoutData(localPeerFormData);

		FormData allPeersFormData = new FormData();
		allPeersFormData.top = new FormAttachment(automaticPeer, 5);
		selectedPeers.setLayoutData(allPeersFormData);

		peerSelectionPanel.setLayout(layout);
		
		return peerSelectionPanel;
	}

	
	private Composite createQueryDetailsPanel(Composite parent) {
		Group queryDetailsPanel = new Group(parent, SWT.NULL);
		queryDetailsPanel.setFont(decorateFont(queryDetailsPanel.getFont(),
				SWT.BOLD));
		queryDetailsPanel.setText("Search Details");

		//Composite typePanel = createResourceTypePanel(queryDetailsPanel);
		Composite searchForPanel = new Composite(queryDetailsPanel, SWT.NULL );
		searchForPanel.setBackground(backgroundColor);
		Composite topicsPanel = createTopicsPanel(queryDetailsPanel);
		topicsPanel.setBackground(backgroundColor);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;

		int style = SWT.NONE;
		style |= SWT.FULL_SELECTION;
		style |= SWT.BORDER;
		String tip="";
		for (int i = 0; i < searchDetails.size(); i++) {
			Label fieldLabel = new Label(searchForPanel, SWT.NULL);
			fieldLabel.setText(whichName((String) searchDetails.get(i)));//fieldLabel.setText((String) searchDetails.get(i));
			fieldLabel.setBackground(backgroundColor);
			tip=whichTip((String) searchDetails.get(i));
			fieldLabel.setToolTipText(tip);
			Text text = new Text(searchForPanel, style);
			text.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
					| GridData.GRAB_HORIZONTAL));
			searchFields.add(text);
		}

		FormLayout formLayout = new FormLayout();
		formLayout.marginHeight = 3;
		formLayout.marginWidth = 3;

		//ORI1
		
		/*
		FormData data1 = new FormData();
		data1.left = new FormAttachment(0, 0);
		data1.right = new FormAttachment(100, 0);
		data1.top = new FormAttachment(0, 0);
		data1.bottom = new FormAttachment(45, -20);
		typePanel.setLayoutData(data1);
		*/

		FormData data2 = new FormData();
		data2.top = new FormAttachment(0, 0);
		data2.left = new FormAttachment(0, 0);
		data2.right = new FormAttachment(100, 0);
		data2.bottom = new FormAttachment(50,0);
		searchForPanel.setLayoutData(data2);

		FormData data3 = new FormData();
		data3.left = new FormAttachment(0, 0);
		data3.right = new FormAttachment(100, 0);
		data3.top = new FormAttachment(searchForPanel);
		data3.bottom = new FormAttachment(100, 0);
		topicsPanel.setLayoutData(data3);

		queryDetailsPanel.setLayout(formLayout);
		searchForPanel.setLayout(layout);

		return queryDetailsPanel;
	}
	
	/*------------------------OntologySearchPanel starts-------------------*/

	private Composite createOntologySearchPanel(Composite parent){
		Group searchPanel = new Group(parent, SWT.NULL);
		searchPanel.setFont(decorateFont(searchPanel.getFont(),
				SWT.BOLD));
		searchPanel.setText("Search Ontology");
		Composite ontologyTopicPanel = createOntologyTopicsPanel(searchPanel);
		ontologyTopicPanel.setBackground(backgroundColor);
		Composite searchForPanel = new Composite(searchPanel, SWT.NULL );
		searchForPanel.setBackground(backgroundColor);
		

		int style = SWT.NONE;
		style |= SWT.FULL_SELECTION;
		style |= SWT.BORDER;

		for (int i = 0; i < ontologyDetails.size(); i++) {
			Label fieldLabel = new Label(searchForPanel, SWT.NULL);
			fieldLabel.setText((String) ontologyDetails.get(i));
			fieldLabel.setBackground(backgroundColor);
			Text text = new Text(searchForPanel, style);
			text.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
					| GridData.GRAB_HORIZONTAL));
			ontologyFields.add(text);
		}
		FormLayout formLayout = new FormLayout();
		formLayout.marginHeight = 3;
		formLayout.marginWidth = 3;

		FormData data1 = new FormData();
		data1.left = new FormAttachment(0, 0);
		data1.right = new FormAttachment(100, 0);
		data1.top = new FormAttachment(0, 0);
		data1.bottom = new FormAttachment(65, 0);
		ontologyTopicPanel.setLayoutData(data1);

		FormData data2 = new FormData();
		data2.top = new FormAttachment(ontologyTopicPanel);
		data2.left = new FormAttachment(0, 0);
		data2.right = new FormAttachment(100, 0);
		data2.bottom = new FormAttachment(100,0);
		searchForPanel.setLayoutData(data2);
		
		searchPanel.setLayout(formLayout);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		searchForPanel.setLayout(layout);
		return searchPanel;
	}
	
	/*------------------------OntologySearchPanel ends---------------------*/
	
	private Composite createResourceTypePanel(Composite parent) {
		Ontology resourceTypeOntology = mOyster2.getTypeOntology();
		Composite typePanel = new Composite(parent, SWT.NULL);
		typePanel.setLayout(new FillLayout());
		typeOntologyViewer = new TreeViewer(typePanel,
				SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		ViewerSorter sorter = new ViewerSorter() {
		};
		Entity rootNode = mOyster2.getTypeOntologyRoot();
		typeOntologyViewer.setSorter(sorter);
		typeOntologyViewer
				.setContentProvider(new OntologyContentProvider(
						resourceTypeOntology,true,rootNode));
		typeOntologyViewer.setLabelProvider(new OntologyLabelProvider());
		typeOntologyViewer.setInput(resourceTypeOntology);
		typeOntologyViewer.expandToLevel(2);
		Tree tableTree = typeOntologyViewer.getTree();
		tableTree.setSelection(new TreeItem[] { tableTree.getTopItem() });
		return typePanel;
	}

	private Composite createTopicsPanel(Composite parent) {
		Ontology resourceTopicOntology = mOyster2.getTopicOntology();
		Composite topicsPanel = new Composite(parent, SWT.NULL);
		topicsPanel.setLayout(new FillLayout());
		Entity rootNode = mOyster2.getTopicOntologyRoot();
		dmozTopicsViewer = new TreeViewer(topicsPanel, SWT.SINGLE | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER);
		dmozTopicsViewer.setContentProvider(new OntologyContentProvider(
				 resourceTopicOntology,false,rootNode));
		dmozTopicsViewer.setLabelProvider(new OntologyLabelProvider());
		dmozTopicsViewer.setInput(resourceTopicOntology);
		dmozTopicsViewer.expandToLevel(2);
		//dmozTopicsViewer.setSelection(new StructuredSelection());
		Tree tableTree = dmozTopicsViewer.getTree();
		//tableTree.setSelection(new TreeItem[] { tableTree.getTopItem() });
		return topicsPanel;
	}
	
	private Composite createOntologyTopicsPanel(Composite parent) {
		Ontology resourceTopicOntology = mOyster2.getTopicOntology();
		Composite topicsPanel = new Composite(parent, SWT.NULL);
		topicsPanel.setLayout(new FillLayout());
		//Entity rootNode = KAON2Manager.factory().owlClass(resourceTopicOntology.getOntologyURI()+"ACMTopic");
		Entity rootNode = mOyster2.getTopicOntologyRoot();
		ontologyTopicsViewer = new TreeViewer(topicsPanel, SWT.SINGLE | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER);
		ontologyTopicsViewer.setContentProvider(new OntologyContentProvider(
				 resourceTopicOntology,false,rootNode));
		ontologyTopicsViewer.setLabelProvider(new OntologyLabelProvider());
		ontologyTopicsViewer.setInput(resourceTopicOntology);
		ontologyTopicsViewer.expandToLevel(2);
		return topicsPanel;
	}
	
	private Composite createResultPanel(Composite parent) {
		Composite resultPanel = new Composite(parent, SWT.NULL);
		resultPanel.setLayout(new FillLayout());
		try {
		
			resultViewer = new ResultViewer(resultPanel, SWT.FULL_SELECTION
					| SWT.MULTI | SWT.BORDER);
			result = new Result(resultViewer,Resource.DataResource);
			resultViewer.addSelectionListener(new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent arg0) {
					TreeViewer tv = resultViewer.getWrapedViewer();
					StructuredSelection selection = (StructuredSelection) tv
							.getSelection();
					detailViewer.clear();
					Iterator it = selection.iterator();
					while (it.hasNext()) {
						Object element = it.next();
						if (element instanceof Entity) {
							detailViewer.append((Entity) element);
						}else if(element instanceof Ontology){
							detailViewer.append((Ontology)element);
						}else {
							//System.err.println("type is wrong!");
							detailViewer.append((Object)element);
						}
					}
				}
			});
		} catch (Exception e) {
			System.err.println(e.toString()+"in createResultPanel.");
		}
		//Initialize actions
		newEntryAction.setResult(result);
		saveEntryAction.setResultViewer(resultViewer.getWrapedViewer());
		if(result==null)System.out.println("result is null");
		editEntryAction.setResult(result);
		return resultPanel;
	}
	
	private Composite createResultPanelRegistry(Composite parent) {
		Composite resultPanel = new Composite(parent, SWT.NULL);
		resultPanel.setLayout(new FillLayout());
		try {
		
			resultViewerRegistry = new ResultViewerRegistry(resultPanel, SWT.FULL_SELECTION
					| SWT.MULTI | SWT.BORDER);
			resultRegistry = new ResultRegistry(resultViewerRegistry,Resource.DataResource);
			resultViewerRegistry.addSelectionListener(new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent arg0) {
					TreeViewer tv = resultViewerRegistry.getWrapedViewer();
					StructuredSelection selection = (StructuredSelection) tv
							.getSelection();
					detailViewer.clear();
					Iterator it = selection.iterator();
					while (it.hasNext()) {
						Object element = it.next();
						if (element instanceof Entity) {
							detailViewer.append((Entity) element);
						}else if(element instanceof Ontology){
							detailViewer.append((Ontology)element);
						}else {
							//System.err.println("type is wrong!");
							detailViewer.append((Object)element);
						}
					}
				}
			});
		} catch (Exception e) {
			System.err.println(e.toString()+"in createResultPanel.");
		}
		//Initialize actions
		newEntryAction.setResult(resultRegistry);
		saveEntryAction.setResultViewer(resultViewerRegistry.getWrapedViewer());
		if(resultRegistry==null)System.out.println("result is null");
		editEntryAction.setResult(resultRegistry);
		return resultPanel;
	}
	


	TreeItem findItem(TreeItem item, Object o) {

		if (item.getData() == o)
			return item;
		else {
			TreeItem[] children = item.getItems();
			for (int i = 0; i < children.length; i++) {
				TreeItem result = findItem(children[i], o);
				if (result != null)
					return result;
			}
		}
		return null;
	}
	
	private Composite createDetailsPanel(Composite parent) {
		Composite bottom = new Composite(parent, SWT.NULL);
		bottom.setBackground(backgroundColor);
		CTabFolder tabFolder = new CTabFolder(bottom, SWT.TOP | SWT.FLAT);
//		--Button Panel--------------------------------------
		Composite buttonPanel = new Composite(bottom, SWT.NULL);
		//Button saveButton = new Button(buttonPanel, SWT.PUSH | SWT.CENTER
		//		| SWT.FLAT);
		//saveButton.setFont(JFaceResources.getBannerFont());
		//saveButton.setText("   &ImportToVirtualOntology   ");
		//saveButton.addSelectionListener(new SelectionAdapter() {
		//	public void widgetSelected(SelectionEvent e) {
		//		saveEntryAction.run();
		//	}
		//});

		
		GridLayout gridLayout = new GridLayout();
		gridLayout.horizontalSpacing = 10;
		gridLayout.numColumns = 6;
		

		FormLayout layout = new FormLayout();
		layout.marginWidth = 3;
		layout.marginHeight = 3;
		
		//GridData gridData = new GridData();
		//gridData.horizontalAlignment = GridData.END;
		//gridData.grabExcessHorizontalSpace = true;
		//gridData.horizontalSpan = 4;
		//saveButton.setLayoutData(gridData);
		//buttonPanel.setLayout(gridLayout);
		
		FormData data1 = new FormData();
		data1.left = new FormAttachment(0, 0);
		data1.right = new FormAttachment(100, 0);
		data1.top = new FormAttachment(0, 0);
		data1.bottom = new FormAttachment(100, -30);
		tabFolder.setLayoutData(data1);

		FormData data2 = new FormData();
		data2.left = new FormAttachment(0, 0);
		data2.right = new FormAttachment(100, 0);
		data2.top = new FormAttachment(tabFolder);
		buttonPanel.setLayoutData(data2);

		bottom.setLayout(layout);

		//--Editor tab
		CTabItem editorPane = new CTabItem(tabFolder, SWT.NULL | SWT.FLAT);
		tabFolder.setSelection(editorPane);
		editorPane.setText("DetailView");

		Composite editorPage = new Composite(tabFolder, SWT.NULL);
		editorPage.setLayout(new FillLayout());
		editorPane.setControl(editorPage);

		int style = SWT.MULTI;
		style |= SWT.V_SCROLL;
		style |= SWT.H_SCROLL;
		style |= SWT.FULL_SELECTION;
		style |= SWT.BORDER;

		detailViewer = new DetailViewer(editorPage, style);
		FontData[] fd = detailViewer.getFont().getFontData();
		for (int i = 0; i < fd.length; i++)
			fd[i].setHeight(fd[i].getHeight() + 1);
		detailViewer.setFont(new Font(MainWindow.this.getShell()
				.getDisplay(), fd));
		
		detailViewer.addLinkSelectionListener(new LinkSelectionListener() {
			public void linkSelected(ViewerLink link) {
				if(link.getLinkType().equals(Constants.useImports)||
						link.getLinkType().equals(Constants.hasPriorVersion)||
						link.getLinkType().equals(Constants.isBackwardCompatibleWith)||
						link.getLinkType().equals(Constants.isIncompatibleWith)
						){
					//HERE SHOULD BE THE LINK TO THE URIs
					LinkedList<Condition> searchConditions = new LinkedList<Condition>();
					Condition condition=null;					
					condition = new Condition(Constants.omvCondition+Constants.URI, link.getLinkedValue(), true);
					searchConditions.addFirst(condition);
					performDataSearch(searchConditions);
				}
				else if(link.getLinkType().equals(Constants.hasCreator)||
						link.getLinkType().equals(Constants.hasContributor)
						){
					//HERE SHOULD BE THE LINK TO THE URIs
					String firstname="";
					String lastname="";
					LinkedList<Condition> searchConditions = new LinkedList<Condition>();
					Condition condition=null;
					int space=link.getLinkedValue().indexOf(" ");
					if (space>0) {
						firstname=link.getLinkedValue().substring(0, space);
						lastname=link.getLinkedValue().substring(space+1,link.getLinkedValue().length());
						condition = new Condition(Constants.omvCondition+Constants.firstName, firstname, Constants.omvCondition+link.getLinkType());
						searchConditions.addFirst(condition);
						if (lastname!=null && lastname!="") {
							condition = new Condition(Constants.omvCondition+Constants.lastName, lastname, Constants.omvCondition+link.getLinkType());
							searchConditions.addFirst(condition);
						}
					}
					else{
						condition = new Condition(Constants.omvCondition+Constants.firstName, link.getLinkedValue(), Constants.omvCondition+link.getLinkType());
						searchConditions.addFirst(condition);
					}
					performDataSearch(searchConditions);
				}
				else if(link.getLinkType().equals(Constants.hasDomain)
						){
					LinkedList<Condition> searchConditions = new LinkedList<Condition>();
					Condition condition=null;
					condition = new Condition(Constants.omvCondition+Constants.URI, link.getLinkedValue(), Constants.omvCondition+link.getLinkType());
					searchConditions.addFirst(condition);
					performDataSearch(searchConditions);
				}
				else if (link.getLinkType().equals(Constants.resourceLocator)||
						link.getLinkType().equals(Constants.documentation)
						){
					Program b = Program.findProgram("html");
					if (b == null || !b.execute(link.getLinkedValue())) {
						errorDialog("Error", "Can't open browser");
					}
				}
				else if(link.getLinkType().equals(Constants.ontologyLocation)
				){
					LinkedList<Condition> searchConditions = new LinkedList<Condition>();
					Condition condition=null;
					condition = new Condition(Constants.pomvCondition+Constants.ontologyLocation, link.getLinkedValue(), false);
					searchConditions.addFirst(condition);
					performDataSearch(searchConditions);
				}
				else  {
					LinkedList<Condition> searchConditions = new LinkedList<Condition>();
					Condition condition=null;
					condition = new Condition(Constants.omvCondition+Constants.name, link.getLinkedValue(), Constants.omvCondition+link.getLinkType());
					searchConditions.addFirst(condition);
					performDataSearch(searchConditions);
				}
			}
		});
		//-----------------------------------------------------------
		
		removeAllEntriesAction.setDetailViewer(detailViewer);
		return bottom;
	}
		
	protected MenuManager createMenuManager() {
		MenuManager barMenu = new MenuManager();

		MenuManager fileMenu = new MenuManager("&File");
		MenuManager openSubMenu = new MenuManager("&Import");
		MenuManager editMenu = new MenuManager("&Edit");
		MenuManager optionMenu = new MenuManager("&Options");
		MenuManager viewMenu = new MenuManager("&Help");
		barMenu.add(fileMenu);
		barMenu.add(editMenu);
		barMenu.add(optionMenu);
		barMenu.add(viewMenu);
		fileMenu.add(openSubMenu);

		//--To show accelerators keys in menu
		//--Because of bug in current verision of
		// ActionContributionItem#update(java.lang.String);
		IContributionManagerOverrides overrides = new IContributionManagerOverrides() {
			public Boolean getEnabled(IContributionItem item) {
				return null;
			}

			public Integer getAccelerator(IContributionItem item) {
				return null;
			}

			public String getAcceleratorText(IContributionItem item) {
				if (item instanceof ActionContributionItem) {
					String text = ((ActionContributionItem) item).getAction()
							.getText();
					int index = text.lastIndexOf('\t');
					if (index == -1)
						index = text.lastIndexOf('@');
					if (index >= 0 && index < text.length())
						return text.substring(index + 1, text.length());
				}
				return null;
			}

			public String getText(IContributionItem item) {
				return null;
			}
		};
		fileMenu.setOverrides(overrides);
		editMenu.setOverrides(overrides);
		//--End of
		// hack-----------------------------------------------------------------------

		//fileMenu.add(importLocalRegistryAction);
		fileMenu.add(new Separator());
		fileMenu.add(exitAction);

		editMenu.add(newEntryAction);
		editMenu.add(editEntryAction);
		editMenu.add(saveEntryAction);
		editMenu.add(removeEntryAction);
		editMenu.add(new Separator());
		editMenu.add(removeAllEntriesAction);

		optionMenu.add(openPreferencesAction);
		openSubMenu.add(importMappingAction);
		openSubMenu.add(importOntologyAction);

		viewMenu.add(aboutBibsterAction);
		return barMenu;
	}

	private Font decorateFont(Font font, int decoration) {
		FontData[] fd = font.getFontData();
		for (int i = 0; i < fd.length; i++)
			fd[i].setStyle(decoration);
		return new Font(this.getShell().getDisplay(), fd);
	}
	
	private void performDataSearch() {
		detailViewer.clear();
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		for (int i = 0; i < searchFields.size(); i++) {
			String text = searchFields.get(i).getText().trim();
			if (text != null && !text.equals("")) {
				Condition condition = new Condition(
						((String) searchDetails.get(i)), text, checkDataProperty(Namespaces.guessLocalName((String) searchDetails.get(i))));			
				searchConditions.addFirst(condition);
			}
		}
		
		//--With inferencing enabled
		//StructuredSelection selection = (StructuredSelection) typeOntologyViewer
		//		.getSelection();
		//Object obj = selection.getFirstElement();
		//if (obj != null) {
		//	Entity typeEntry= (Entity) obj;	
		//	Condition condition = new Condition(Condition.TYPE_CONDITION,typeEntry);
		//	searchConditions.addFirst(condition);
		//}
		
		//--topic
		StructuredSelection selection = (StructuredSelection) dmozTopicsViewer.getSelection();
		Object obj = selection.getFirstElement();
		if (obj != null){
			Entity topicEntry = (Entity)obj;
			Condition condition = new Condition(Constants.omvCondition+Constants.URI, topicEntry.getURI(), Constants.omvCondition+Constants.hasDomain);
			//Condition condition = new Condition(Condition.TOPIC_CONDITION,topicEntry);
			searchConditions.addFirst(condition);	
		}
		
		String wildCard = searchField.getText().trim();
		if (wildCard != null && !wildCard.equals("")) {
			Condition condition = new Condition(
					null, wildCard, true );			
			searchConditions.addFirst(condition);
		}
		
		performDataSearch(searchConditions);
	}

	public Boolean checkDataProperty(String propertyName)  {
		Ontology resourceTypeOntology = mOyster2.getTypeOntology();
		OWLClass ontologyClass = KAON2Manager.factory().owlClass(Constants.OMV+Constants.DefaultTypeOntologyRoot);
		try{
			/*KAON2 BUG, DOES NOT SUPPORT OWL DL
	         * SHOULD BE DELETED WHEN IT DOES
	         */
			if(propertyName.equals(Constants.name)) return true;
			if(propertyName.equals(Constants.acronym)) return true;
			if(propertyName.equals(Constants.description)) return true;
			if(propertyName.equals(Constants.documentation)) return true;
			if(propertyName.equals(Constants.URI)) return true;
			
			/* UNTIL HERE */
			
			Set<DataProperty> dataProperties=ontologyClass.getDataPropertiesFrom(resourceTypeOntology);
			for (DataProperty dataProperty : dataProperties){
				if(propertyName.equals(Namespaces.guessLocalName(dataProperty.getURI()))) return true; 
			}
		}
	    catch (KAON2Exception e) {
	    	System.err.println(e + " in checkDataProperty()");
	    }
		return false;
	}
	
	private void performDataSearch(LinkedList<Condition> conditions) {	
		searchButton.setEnabled(false);
		progresMonitor = getStatusLineManager().getProgressMonitor();
		progresMonitor.beginTask("Searching metadata entries ... ",	0);
		QueryFormulator mFormulator = new QueryFormulator();
		mFormulator.generateDataQuery(conditions);
		Oyster2Query typeQuery = mFormulator.getTypeQuery();
		//Z	
		result = new Result(resultViewer,Resource.DataResource);
		searchManager.startSearch(null,typeQuery,false);
		searchManager.addListener(result);   //mOyster2.getSearchManager().addListener(result);
	}
	
	private void performOntologySearch(){
		System.out.println("performOntologySearch.....");
		detailViewer.clear();
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		for (int i = 0; i < ontologyFields.size(); i++) {
			String text = ontologyFields.get(i).getText().trim();
			if (text != null && !text.equals("")) {
				Condition condition = new Condition(
						((String) ontologyDetails.get(i)), text);
				searchConditions.add(condition);
			}
		}
		
		//-- ontology topic condition
		StructuredSelection selection = (StructuredSelection) ontologyTopicsViewer.getSelection();
		Object obj = selection.getFirstElement();
		if (obj != null){
			Entity topicEntry = (Entity)obj;
			//System.err.println("topicEntry:  "+topicEntry.getURI());
			Condition condition = new Condition(Condition.TOPIC_CONDITION,topicEntry);
			searchConditions.addFirst(condition);	
		}
		performOntologySearch(searchConditions);
	}
	
	private void performOntologySearch(LinkedList<Condition> conditions){
		System.out.println("performOntologySearch(LinkedList conditions).... ");
		searchButton.setEnabled(false);
		progresMonitor = getStatusLineManager().getProgressMonitor();
		progresMonitor.beginTask("Searching ontology entries ... ",
				0);
		QueryFormulator mFormulator = new QueryFormulator();
		mFormulator.generateOntologyQuery(conditions);
		Oyster2Query topicQuery = mFormulator.getTopicQuery();
		if((topicQuery == null)){
			errorDialog("query null","topic should be specified!");
			searchManager.stopSearch();
			if (progresMonitor != null)
				progresMonitor.done();
			return;
		}
		searchManager.startSearch(topicQuery,null,false);
		//result = new Result(resultViewer.getWrapedViewer());
		resultRegistry = new ResultRegistry(resultViewerRegistry,Resource.OntologyResource);
		newEntryAction.setResult(resultRegistry);
		editEntryAction.setResult(resultRegistry);
		//mKaonP2P.getSearchManager().addReplyListener(result);
		mOyster2.getSearchManager().addListener(resultRegistry);
	}
	
	public void operationError(Throwable t) {
		errorDialog("Error", t.getMessage());
		//LOG.debug(t.getMessage(), t);
		operationFinished();
	}

	public void operationFinished() {
		
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				searchButton.setEnabled(true);
				if (progresMonitor != null)
					progresMonitor.done();
			}
		});
	}
	
	private void errorDialog(final String title, final String message) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(Display.getDefault().getActiveShell(),
						title, message);
			}
		});
	}
	
	protected void constrainShellSize() {
		Shell shell = this.getShell();
		Point size = shell.getSize();
		Rectangle bounds = shell.getDisplay().getClientArea();
		int newX = Math.min(size.x, bounds.width);
		int newY = Math.min(size.y, bounds.height);
		if (size.x != newX || size.y != newY)
			shell.setBounds(0, 0, newX, newY);
	}

	protected void handleShellCloseEvent() {
		super.handleShellCloseEvent();
	}
	
	public static String whichName(String which) {
		if (which.equals(Constants.omvCondition+Constants.URI)) {
			return "Ontology URI";
		}
		if (which.equals(Constants.omvCondition+Constants.name)) {
			return "Ontology Name";
		}
		if (which.equals(Constants.omvCondition+Constants.acronym)) {
			return "Ontology Acronym";
		}
		if (which.equals(Constants.omvCondition+Constants.description)) {
			return "Description";
		}
		if (which.equals(Constants.omvCondition+Constants.documentation)) {
			return "Documentation";
		}
		if (which.equals(Constants.omvCondition+Constants.keywords)) {
			return "Keywords";
		}
		if (which.equals(Constants.omvCondition+Constants.status)) {
			return "Status";
		}
		if (which.equals(Constants.omvCondition+Constants.creationDate)) {
			return "Creation Date";
		}
		if (which.equals(Constants.omvCondition+Constants.modificationDate)) {
			return "Modification Date";
		}
		if (which.equals(Constants.omvCondition+Constants.hasContributor)) {
			return "Contributor";
		}
		if (which.equals(Constants.omvCondition+Constants.hasCreator)) {
			return "Creator";
		}
		if (which.equals(Constants.omvCondition+Constants.usedOntologyEngineeringTool)) {
			return "Engineering Tool";
		}
		if (which.equals(Constants.omvCondition+Constants.usedOntologyEngineeringMethodology)) {
			return "Engineering Methodology";
		}
		if (which.equals(Constants.omvCondition+Constants.usedKnowledgeRepresentationParadigm)) {
			return "KR Paradigm";
		}
		if (which.equals(Constants.omvCondition+Constants.hasDomain)) {
			return "Ontology Domain";
		}
		if (which.equals(Constants.omvCondition+Constants.isOfType)) {
			return "Ontology Type";
		}
		if (which.equals(Constants.omvCondition+Constants.naturalLanguage)) {
			return "Natural Language";
		}
		if (which.equals(Constants.omvCondition+Constants.designedForOntologyTask)) {
			return "Ontology Task";
		}
		if (which.equals(Constants.omvCondition+Constants.hasOntologyLanguage)) {
			return "Ontology Language";
		}
		if (which.equals(Constants.omvCondition+Constants.hasOntologySyntax)) {
			return "Ontology Syntax";
		}
		if (which.equals(Constants.omvCondition+Constants.hasFormalityLevel)) {
			return "Formality Level";
		}
		if (which.equals(Constants.omvCondition+Constants.resourceLocator)) {
			return "Resource Locator";
		}
		if (which.equals(Constants.omvCondition+Constants.version)) {
			return "Version";
		}
		if (which.equals(Constants.omvCondition+Constants.hasLicense)) {
			return "License Model";
		}
		if (which.equals(Constants.omvCondition+Constants.useImports)) {
			return "Ontology Imports";
		}
		if (which.equals(Constants.omvCondition+Constants.hasPriorVersion)) {
			return "Prior Version";
		}
		if (which.equals(Constants.omvCondition+Constants.isBackwardCompatibleWith)) {
			return "Backward Compatible";
		}
		if (which.equals(Constants.omvCondition+Constants.isIncompatibleWith)) {
			return "Incompatible";
		}
		if (which.equals(Constants.omvCondition+Constants.numClasses)) {
			return "Classes";
		}
		if (which.equals(Constants.omvCondition+Constants.numProperties)) {
			return "Properties";
		}
		if (which.equals(Constants.omvCondition+Constants.numIndividuals)) {
			return "Individuals";
		}
		if (which.equals(Constants.omvCondition+Constants.numAxioms)) {
			return "Axioms";
		}
		if (which.equals(RDFS.LABEL)) {
			return "rdfs:label";
		}
		//if (which.equals(RDF.TYPE)) {
		//	return "rdf:type";
		//}
		return which;
	}
	
	public String whichTip(String which) {
		if (which.equals(Constants.omvCondition+Constants.URI)) {
			return "The URI of the ontology which is described by this metadata";
		}
		if (which.equals(Constants.omvCondition+Constants.name)) {
			return "The name by which an ontology is formally known";
		}
		if (which.equals(Constants.omvCondition+Constants.acronym)) {
			return "A short name by which an ontology is formally known";
		}
		if (which.equals(Constants.omvCondition+Constants.description)) {
			return "Free text description of an ontology";
		}
		if (which.equals(Constants.omvCondition+Constants.documentation)) {
			return "URL for further documentation";
		}
		if (which.equals(Constants.omvCondition+Constants.keywords)) {
			return "List of keywords related to an ontology. Typically this set includes names of the classes, properties, etc.";
		}
		if (which.equals(Constants.omvCondition+Constants.status)) {
			return "It specifies the tracking information for the contents of the ontology";
		}
		if (which.equals(Constants.omvCondition+Constants.creationDate)) {
			return "Date when the ontology was initially created";
		}
		if (which.equals(Constants.omvCondition+Constants.modificationDate)) {
			return "Date of the last modification made to the ontology";
		}
		if (which.equals(Constants.omvCondition+Constants.hasContributor)) {
			return "Contributor to the ontology";
		}
		if (which.equals(Constants.omvCondition+Constants.hasCreator)) {
			return "Main responsible for the ontology";
		}
		if (which.equals(Constants.omvCondition+Constants.usedOntologyEngineeringTool)) {
			return "Information about tool used to create the ontology";
		}
		if (which.equals(Constants.omvCondition+Constants.usedOntologyEngineeringMethodology)) {
			return "Information about the method model used to create the ontology";
		}
		if (which.equals(Constants.omvCondition+Constants.usedKnowledgeRepresentationParadigm)) {
			return "Information about the paradigm model used to create the ontology";
		}
		if (which.equals(Constants.omvCondition+Constants.hasDomain)) {
			return "Specifies the domain topic of an ontology. " +
					"Typically, the domain can refer to established topic hierarchies " +
					"such as the general purpose topic hierarchy DMOZ or the domain " +
					"specific topic hierarchy ACM for the computer science domain";
		}
		if (which.equals(Constants.omvCondition+Constants.isOfType)) {
			return "The nature of the content of the ontology";
		}
		if (which.equals(Constants.omvCondition+Constants.naturalLanguage)) {
			return "The language of the content of the ontology, i.e. English, German, etc.";
		}
		if (which.equals(Constants.omvCondition+Constants.designedForOntologyTask)) {
			return "Declares for which purpose the ontology was originally designed";
		}
		if (which.equals(Constants.omvCondition+Constants.hasOntologyLanguage)) {
			return "Specifies the ontology language";
		}
		if (which.equals(Constants.omvCondition+Constants.hasOntologySyntax)) {
			return "It specifies the presentation syntax for the ontology language";
		}
		if (which.equals(Constants.omvCondition+Constants.hasFormalityLevel)) {
			return "Level of formality of the ontology";
		}
		if (which.equals(Constants.omvCondition+Constants.resourceLocator)) {
			return "The location where the ontology can be found. It should be accessible via a URL";
		}
		if (which.equals(Constants.omvCondition+Constants.version)) {
			return "Specifies the version information of the ontology";
		}
		if (which.equals(Constants.omvCondition+Constants.hasLicense)) {
			return "Underlying license model";
		}
		if (which.equals(Constants.omvCondition+Constants.useImports)) {
			return "References another ontology metadata instance that describes an ontology " +
					"containing definitions, whose meaning is considered to be part of the " +
					"meaning of the ontology described by this ontology metadata instance";
		}
		if (which.equals(Constants.omvCondition+Constants.hasPriorVersion)) {
			return "Contains a reference to another ontology metadata instance." +
					"Identifies the ontology metadata instance which describes an " +
					"ontology that is a prior version of the ontology described by " +
					"this ontology metadata instance. It may be used to organize " +
					"ontologies by versions and is NULL for initial ontology";
		}
		if (which.equals(Constants.omvCondition+Constants.isBackwardCompatibleWith)) {
			return "This property identifies the ontology metadata instance which " +
					"describes an ontology that is a compatible prior version of the " +
					"ontology described by this ontology metadata instance";
		}
		if (which.equals(Constants.omvCondition+Constants.isIncompatibleWith)) {
			return "This property indicates that the described ontology is a later version " +
					"of the ontology described by the metadata specified, but is not backward " +
					"compatible with it. It can be used to explicitly state that ontology " +
					"cannot upgrade to use the new version without checking whether changes are required.";
		}
		if (which.equals(Constants.omvCondition+Constants.numClasses)) {
			return "Number of classes in the ontology";
		}
		if (which.equals(Constants.omvCondition+Constants.numProperties)) {
			return "Number of properties in the ontology";
		}
		if (which.equals(Constants.omvCondition+Constants.numIndividuals)) {
			return "Number of individuals in the ontology";
		}
		if (which.equals(Constants.omvCondition+Constants.numAxioms)) {
			return "Number of axioms in the ontology";
		}
		return "";
	}
}


/* ORI1

FormData data1 = new FormData();
data1.left = new FormAttachment(0, 0);
data1.right = new FormAttachment(100, 0);
data1.top = new FormAttachment(0, 0);
data1.bottom = new FormAttachment(45, -20);
typePanel.setLayoutData(data1);

FormData data2 = new FormData();
data2.top = new FormAttachment(typePanel);
data2.left = new FormAttachment(0, 0);
data2.right = new FormAttachment(100, 0);
data2.bottom = new FormAttachment(70,0);
searchForPanel.setLayoutData(data2);

FormData data3 = new FormData();
data3.left = new FormAttachment(0, 0);
data3.right = new FormAttachment(100, 0);
data3.top = new FormAttachment(searchForPanel);
data3.bottom = new FormAttachment(100, 0);
topicsPanel.setLayoutData(data3);

*/

//Z
/*
Oyster2Query topicQuery = mFormulator.getTopicQuery();
if((topicQuery == null)||(typeQuery == null)){
	errorDialog("query null","type or topic should be specified!");
	searchManager.stopSearch();
	if (progresMonitor != null)
		progresMonitor.done();
	return;
}
*/