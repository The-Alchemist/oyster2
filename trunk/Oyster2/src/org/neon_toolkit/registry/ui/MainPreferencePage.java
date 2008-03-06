package org.neon_toolkit.registry.ui;

//import org.eclipse.jface.preference.IPreferenceStore;
//import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.neon_toolkit.registry.oyster2.Constants;
import org.neon_toolkit.registry.oyster2.Oyster2Factory;
import org.neon_toolkit.registry.oyster2.Properties;


public class MainPreferencePage extends PreferencePage{
	
	static Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private static Properties mprop = mOyster2.getProperties();
	
	private FileFieldEditor localRegistryField;
	private FileFieldEditor typeOntologyField;
	private FileFieldEditor peerDescriptionOntologyField;
	private FileFieldEditor mappingDescriptionOntologyField;
	private FileFieldEditor owlChangeOntologyField;
	private FileFieldEditor topicOntologyField;
	private FileFieldEditor changeOntologyField;
	private FileFieldEditor owlodmOntologyField;
	private FileFieldEditor imageLocationField;
	
	private static final String LocalRegistry =Constants.LocalRegistry;
	private static final String TypeOntology = Constants.TypeOntology;
	private static final String PDOntology = Constants.PDOntology;
	private static final String MDOntology = Constants.MDOntology;
	private static final String owlChangeOntology = Constants.owlChangeOntology;
	private static final String TopicOntology = Constants.TopicOntology;
	private static final String changeOntology = Constants.changeOntology;
	private static final String owlodmOntology = Constants.owlodmOntology;
	private static final String Image = Constants.Image; 
	
	
	public MainPreferencePage(String name){
		super(name);
	}
	
	protected Control createContents(Composite parent) {
		int style = SWT.NONE;
		style |= SWT.FULL_SELECTION;
		style |= SWT.BORDER;
		this.setTitle("Application preferences");
		Composite panel = new Composite(parent, SWT.NULL);
		panel.setLayout(new FillLayout());
		
		localRegistryField = new FileFieldEditor("LER", "Local Expertise Registry Ontology location:", false, panel);
		typeOntologyField = new FileFieldEditor("Type", "Type Ontology location:", false, panel);
		peerDescriptionOntologyField = new FileFieldEditor("PD", "Peer Description Ontology location:", false, panel);
		mappingDescriptionOntologyField = new FileFieldEditor("MD", "Mapping Description Ontology location:", false, panel);
		owlChangeOntologyField = new FileFieldEditor("owlChange", "OWL Change Ontology location:", false, panel);
		topicOntologyField = new FileFieldEditor("Topic", "Topic Ontology location:", false, panel);
		changeOntologyField = new FileFieldEditor("Change", "Change Ontology location:", false, panel);
		owlodmOntologyField = new FileFieldEditor("owlodm", "OWLODM Ontology location:", false, panel);
		imageLocationField = new FileFieldEditor("Image", "Image location:", false, panel);
		
		localRegistryField.setStringValue(Constants.DefaultLocalRegistry);
		typeOntologyField.setStringValue(Constants.DefaultTypeOntology);
		peerDescriptionOntologyField.setStringValue(Constants.DefaultPDOntology);
		mappingDescriptionOntologyField.setStringValue(Constants.DefaultMDOntology);
		owlChangeOntologyField.setStringValue(Constants.DefaultOWLChangeOntology);
		topicOntologyField.setStringValue(Constants.DefaultTopicOntology);
		changeOntologyField.setStringValue(Constants.DefaultChangeOntology);
		owlodmOntologyField.setStringValue(Constants.DefaultOWLODMOntology);
		imageLocationField.setStringValue(Constants.DefaultImage);
		
		return panel;
	}
	/** 
	 * Method declared on IPreferencePage.
	 */
	public boolean performOk() {
		
		mprop.setString(LocalRegistry, localRegistryField.getStringValue());
		mprop.setString(TypeOntology, typeOntologyField.getStringValue());
		mprop.setString(PDOntology, peerDescriptionOntologyField.getStringValue());
		mprop.setString(MDOntology, mappingDescriptionOntologyField.getStringValue());
		mprop.setString(owlChangeOntology, owlChangeOntologyField.getStringValue());
		mprop.setString(TopicOntology, topicOntologyField.getStringValue());
		mprop.setString(changeOntology, changeOntologyField.getStringValue());
		mprop.setString(owlodmOntology, owlodmOntologyField.getStringValue());
		mprop.setString(Image, imageLocationField.getStringValue());
		mprop.storeOn();
		//IPreferenceStore pref = getPreferenceStore();
		//pref.setValue(LocalRegistry, localRegistryField.getStringValue());
		//pref.setValue(PDOntology, peerDescriptionOntologyField.getStringValue());
		//pref.setValue(MDOntology, mappingDescriptionOntologyField.getStringValue());
		//pref.setValue(Image, imageLocationField.getStringValue());
		return true;
	}
	
	//protected IPreferenceStore doGetPreferenceStore() {
	//	return JFacePreferences.getPreferenceStore();
	//}
	/**
	 * It is overrided method. 
	 */
	protected void performDefaults() {
		localRegistryField.setStringValue(Constants.DefaultLocalRegistry);
		typeOntologyField.setStringValue(Constants.DefaultTypeOntology);
		peerDescriptionOntologyField.setStringValue(Constants.DefaultPDOntology);
		mappingDescriptionOntologyField.setStringValue(Constants.DefaultMDOntology);
		owlChangeOntologyField.setStringValue(Constants.DefaultOWLChangeOntology);
		topicOntologyField.setStringValue(Constants.DefaultTopicOntology);
		changeOntologyField.setStringValue(Constants.DefaultChangeOntology);
		owlodmOntologyField.setStringValue(Constants.DefaultOWLODMOntology);
		imageLocationField.setStringValue(Constants.DefaultImage);		
		super.performDefaults();
	}

}
