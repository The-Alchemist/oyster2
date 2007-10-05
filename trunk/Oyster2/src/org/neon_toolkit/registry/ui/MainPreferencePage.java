package org.neon_toolkit.registry.ui;

//import org.eclipse.jface.dialogs.MessageDialog;
//import org.eclipse.swt.widgets.*;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.*;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.neon_toolkit.registry.oyster2.Constants;


public class MainPreferencePage extends PreferencePage{
	
	//private FileFieldEditor basicOntologyLocationField_1;
	//private FileFieldEditor basicOntologyLocationField_2;
	//private FileFieldEditor basicOntologyLocationField_3;
	//private FileFieldEditor basicOntologyLocationField_4;
	//private FileFieldEditor virtualOntologyLocationField;
	private FileFieldEditor localExpertiseRegistryField;
	private FileFieldEditor peerDescriptionOntologyField;
	private FileFieldEditor mappingDescriptionOntologyField;
	private FileFieldEditor defaultTypeOntologyLocationField;
	private FileFieldEditor defaultTopicOntologyLocationField;
	private FileFieldEditor imageLocationField;
	
	
	//public static final String Basic_1 =Constants.Basic_1;
	//private static final String Basic_2 =Constants.Basic_2;
	//private static final String Basic_3 =Constants.Basic_3;
	//private static final String Basic_4 =Constants.Basic_4;
	//private static final String VirtualOntology =Constants.VirtualOntology;
	//private static final String DefaultVirtualOntology = Constants.DefaultVirtualOntology;
	//private static final String DefaultBasic_1 = Constants.DefaultBasic_1;
	//private static final String DefaultBasic_2 = Constants.DefaultBasic_2;
	//private static final String DefaultBasic_3 = Constants.DefaultBasic_3;
	//private static final String DefaultBasic_4 = Constants.DefaultBasic_4;
	//private static final String DefaultLocalRegistry = Constants.DefaultLocalRegistry;
	//private static final String DefaultPDOntology = Constants.DefaultPDOntology;	
	//private static final String DefaultTypeOntology = Constants.DefaultTypeOntology;
	//private static final String DefaultTopicOntology = Constants.DefaultTopicOntology;
	//private Oyster2Factory mKaonP2P = Oyster2Factory.sharedInstance();
	private static final String LocalRegistry =Constants.LocalRegistry;
	private static final String PDOntology = Constants.PDOntology;
	private static final String MDOntology = Constants.MDOntology;
	private static final String TypeOntology = Constants.TypeOntology;
	private static final String TopicOntology = Constants.TopicOntology;
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
		//basicOntologyLocationField_1 = new FileFieldEditor("SWRC", "swrc ontology location:", false, panel);
		//basicOntologyLocationField_2 = new FileFieldEditor("Protons", "protons ontology location:", false, panel);
		//basicOntologyLocationField_3 = new FileFieldEditor("Protont", "protont ontology location:", false, panel);
		//basicOntologyLocationField_4 = new FileFieldEditor("OMV", "omv ontology location:", false, panel);
		//virtualOntologyLocationField = new FileFieldEditor("VO", "Virtual Ontology location:", false, panel);
		//basicOntologyLocationField_1.setStringValue(Constants.DefaultBasic_1);
		//basicOntologyLocationField_2.setStringValue(Constants.DefaultBasic_2);
		//basicOntologyLocationField_3.setStringValue(Constants.DefaultBasic_3);
		//basicOntologyLocationField_4.setStringValue(Constants.DefaultBasic_4);
		//virtualOntologyLocationField.setStringValue(Constants.DefaultVirtualOntology);
		//IPreferenceStore pref = getPreferenceStore();
		localExpertiseRegistryField = new FileFieldEditor("LER", "Local Expertise Registry Ontology location:", false, panel);
		peerDescriptionOntologyField = new FileFieldEditor("PD", "Peer Description Ontology location:", false, panel);
		mappingDescriptionOntologyField = new FileFieldEditor("MD", "Mapping Description Ontology location:", false, panel);
		defaultTypeOntologyLocationField = new FileFieldEditor("Type", "Type Ontology location:", false, panel);
		defaultTopicOntologyLocationField = new FileFieldEditor("Topic", "Topic Ontology location:", false, panel);
		imageLocationField = new FileFieldEditor("Image", "Image location:", false, panel);
		localExpertiseRegistryField.setStringValue(Constants.DefaultLocalRegistry);
		peerDescriptionOntologyField.setStringValue(Constants.DefaultPDOntology);
		mappingDescriptionOntologyField.setStringValue(Constants.DefaultMDOntology);
		defaultTypeOntologyLocationField.setStringValue(Constants.DefaultTypeOntology);
		defaultTopicOntologyLocationField.setStringValue(Constants.DefaultTopicOntology);
		imageLocationField.setStringValue(Constants.DefaultImage);
		
		return panel;
	}
	/** 
	 * Method declared on IPreferencePage.
	 */
	public boolean performOk() {
		IPreferenceStore pref = getPreferenceStore();
		//pref.setValue(Basic_1, basicOntologyLocationField_1.getStringValue());
		//pref.setValue(Basic_2, basicOntologyLocationField_2.getStringValue());
		//pref.setValue(Basic_3, basicOntologyLocationField_3.getStringValue());
		//pref.setValue(Basic_4, basicOntologyLocationField_4.getStringValue());
		//pref.setValue(VirtualOntology, virtualOntologyLocationField.getStringValue());
		pref.setValue(LocalRegistry, localExpertiseRegistryField.getStringValue());
		pref.setValue(PDOntology, peerDescriptionOntologyField.getStringValue());
		pref.setValue(MDOntology, mappingDescriptionOntologyField.getStringValue());
		pref.setValue(TypeOntology, defaultTypeOntologyLocationField.getStringValue());
		pref.setValue(TopicOntology, defaultTopicOntologyLocationField.getStringValue());
		pref.setValue(Image, imageLocationField.getStringValue());
		return true;
	}
	protected IPreferenceStore doGetPreferenceStore() {
		return JFacePreferences.getPreferenceStore();
	}
	/**
	 * It is overrided method. 
	 */
	protected void performDefaults() {
		//basicOntologyLocationField_1.setStringValue(Constants.DefaultBasic_1);
		//basicOntologyLocationField_2.setStringValue(Constants.DefaultBasic_2);
		//basicOntologyLocationField_3.setStringValue(Constants.DefaultBasic_3);
		//basicOntologyLocationField_4.setStringValue(Constants.DefaultBasic_4);
		//virtualOntologyLocationField.setStringValue(Constants.DefaultVirtualOntology);
		//pref.setValue(Basic_1,Constants.DefaultBasic_1);
		//pref.setValue(Basic_2,Constants.DefaultBasic_2);
		//IPreferenceStore pref = getPreferenceStore();
		localExpertiseRegistryField.setStringValue(Constants.DefaultLocalRegistry);
		peerDescriptionOntologyField.setStringValue(Constants.DefaultPDOntology);
		mappingDescriptionOntologyField.setStringValue(Constants.DefaultMDOntology);
		defaultTypeOntologyLocationField.setStringValue(Constants.DefaultTypeOntology);
		defaultTopicOntologyLocationField.setStringValue(Constants.DefaultTopicOntology);
		imageLocationField.setStringValue(Constants.DefaultImage);		
		super.performDefaults();
	}

}
