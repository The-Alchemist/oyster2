/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ColumnLayout;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.GUIConstants;
import org.neontoolkit.oyster2.client.gui.IMessageResolver;
import org.neontoolkit.oyster2.client.gui.MessageResolver;
import org.neontoolkit.oyster2.client.gui.Oyster2;
import org.neontoolkit.oyster2.client.gui.TargetServerComposite;
import org.neontoolkit.oyster2.client.gui.dialogs.content.CompositeFactory;
import org.neontoolkit.oyster2.client.gui.dialogs.content.InputComposite;
import org.neontoolkit.oyster2.client.gui.util.Template;
import org.neontoolkit.oyster2.client.gui.util.TemplateManager;
import org.semanticweb.kaon2.api.KAON2Exception;

/**
 * @author David Mu�oz
 *
 */
public class PropertiesConfiguredSubmitDialog extends ResizableDialog {

	
	//constants
	private static final String CONFIGURATION_FILE_NAME = 
		"PropertiesConfiguredSubmitDialog.properties";
	
	private static final String IDENTIFIERS_KEY = "configuration.identifiers";
	
	private static final String TARGET_BUNDLE_KEY = "bundle.file";
	
	private static final String TARGET_CATEGORIES_KEY = "target.categories";
	
	private static final String CATEGORY_ATTRIBUTES_SUFFIX = ".category.attributes";
	
	private static final String TYPE_SUFFIX = ".type";
	
	private static final String EDITABLE_SUFFIX = ".editable";
	
	private static final String DEFAULT_SUFFIX = ".default";
	
	private static final String REQUIRED_SUFFIX = ".required";
	
	private static final String VALIDATOR_SUFFIX = ".validator";
	
	private static final String PREDEFINED_SUFFIX = ".predefined";
	
	private static final String SOURCE_TYPE_SUFFIX = ".source.type";
	
	private static final String FILE_SOURCE ="file";
	
	private static final String ONTOLOGY_SOURCE ="ontology";
	
	private static final String BOOLEAN_SOURCE ="booleans";
	
	private static final String ELEMENTS_KEY="elements";
	
	private static final String SOURCE_SUFFIX = ".source";
	
	private static final String CLASS_SUFFIX = ".class";
	
	private static final String NAMESPACE_KEY = "namespace";
	
	private ArrayList<String> booleanValues = null;
	
	private Map<String,InputComposite> composites = 
		new HashMap<String,InputComposite>();
	
	//non - dependent of the kind of object being submitted
	private TargetServerComposite serverComposite = null;
	private FormToolkit toolkit;
	private ScrolledForm form;
	
	private IMessageResolver messagesResolver = null;
	
	private PropertiesConfiguration generalConfiguration = null;
	
	private Combo targetCombo = null;
	
	private String [] targets = null;
	
	private Button attributesSelectionButton = null;
	
	// dependent of the object being submitted
	
	private PropertiesConfiguration targetConfiguration = null;
	
	private IMessageResolver targetMessagesResolver = null;
	
	private Template template = null;
	
	private Map<String,Composite> currentSections = null;
	
	private String submitTarget = null;
	
	private Map<String,String[]> categories = null;
	
	private Map<String,Boolean> selection = null;
	
	/**
	 * @param name
	 * @param parentShell
	 */
	public PropertiesConfiguredSubmitDialog(String name,
			IShellProvider parentShell) {
		super(name, parentShell);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 * @param parentShell
	 */
	public PropertiesConfiguredSubmitDialog(String name, Shell parentShell) {
		super(name, parentShell);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		booleanValues = new ArrayList<String>();
		booleanValues.add("true");
		booleanValues.add("false");
		
		Composite baseComposite =  (Composite)super.createDialogArea(parent);
		baseComposite.setLayout(new FillLayout());
		
		String path = Activator.getDefault().getResourcesDir();
		try {
			generalConfiguration = 
				new PropertiesConfiguration(path + File.separator + CONFIGURATION_FILE_NAME);
			messagesResolver = new MessageResolver(this.getClass().getPackage().getName() + 
					".SubmitMessages");
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		configureForm(baseComposite);
		serverComposite = new TargetServerComposite(form.getBody(),SWT.NONE);
		makeTargetComposite(form.getBody());
		getTargetConfiguration();
		makeComposites(form.getBody());
		
		
		
		return baseComposite;
		
		
	}
	
	private void makeTargetCompositeListeners() {
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				if ( event.widget == targetCombo) {
					String newTarget = targetCombo.getItem(targetCombo.getSelectionIndex());
					if (! submitTarget.equals(newTarget)) {
						cleanTargetConfiguration();
						getTargetConfiguration();
						makeComposites(form.getBody());
					}
				}
				else if (event.widget == attributesSelectionButton) {
					Shell shell = Activator.getDefault()
					.getWorkbench().getActiveWorkbenchWindow().getShell();
					TranslatedAttributeSelectionDialog dialog =
						new TranslatedAttributeSelectionDialog("searchViewAttributesDialog",
								shell,template);
					dialog.setCategories(categories);
					dialog.setSelection(selection);
		
					dialog.setMessagesResolver(targetMessagesResolver);
					int result = dialog.open();
					if (result == dialog.OK) {
						makeComposites(form.getBody());
					}
				}
			}

		};
		targetCombo.addListener(SWT.Selection, listener);
		attributesSelectionButton.addListener(SWT.Selection, listener);
	}
	
	
	private void cleanTargetConfiguration() {
		for (Entry<String,InputComposite> entry : composites.entrySet()) {
			entry.getValue().dispose();
		}
		
		selection.clear();
		composites.clear();
	}
	
	private void getTargetConfiguration() {
		submitTarget = targets[targetCombo.getSelectionIndex()];
		
		String path = Activator.getDefault().getResourcesDir() + File.separator +
		generalConfiguration.getString(submitTarget+".file");
		
		try {
			targetConfiguration = new PropertiesConfiguration(path);
		} catch (ConfigurationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		String targetBundle = targetConfiguration.getString(TARGET_BUNDLE_KEY);
		template = TemplateManager.getInstance().getTemplate(submitTarget);
		targetMessagesResolver = new MessageResolver(
				this.getClass().getPackage().getName() + "." +targetBundle);
		String defaultTemplate = template.getDefaultTemplate();
		List<String> templateAttributes =
			template.getTemplateProperties(defaultTemplate);
		
		currentSections = new TreeMap<String,Composite>();
		categories = new HashMap<String, String[]>();
		selection = new HashMap<String, Boolean>();
		for (String enabledAttribute : templateAttributes) {
			selection.put(enabledAttribute,true);
		}
		
		
	}
	
	private void makeComposites(Composite parent) {
		
		
		String [] categoryNames = targetConfiguration.getStringArray(TARGET_CATEGORIES_KEY); 
		System.err.println("====== BEGIN =========================");
		
		String []categoryAttributes = null;
		
		for (String category : categoryNames) {
			
			
			categoryAttributes = targetConfiguration.getStringArray(category + CATEGORY_ATTRIBUTES_SUFFIX);
			categories.put(category,categoryAttributes);
			makeSection(parent,category,categoryAttributes);
			
		}
		form.getBody().layout(true,true);
		form.reflow(false);
		System.err.println("=============END==================");
	}
	
	private boolean makeSection(Composite parent,String category, String[] categoryAttributes) {
		
		Group sectionGroup = (Group)currentSections.get(category);
		Group attributeGroup = null;
		Section section = null;
		Composite sectionClient = null;
		if (sectionGroup == null) {
			sectionGroup = new Group(parent,SWT.SHADOW_NONE);
			
			sectionGroup.setLayout(new FillLayout());
			sectionGroup.setBackground(parent.getBackground());
			
			section = toolkit.createSection(sectionGroup, GUIConstants.SECTION_STYLE);
			sectionGroup.setData(section);
			String name = targetMessagesResolver.getString(category);
			section.setText(name);
			section.setLayout(new FillLayout());
			
			
			sectionClient = toolkit.createComposite(section);
			section.setClient(sectionClient);
			sectionClient.setBackground(form.getBackground());
			section.setData(sectionClient);
			
			
			// COLORS
			section.setBackground(form.getBackground());
			section.setTitleBarBackground(form.getBackground());
			
			
			
		}
		else {
			section = (Section)sectionGroup.getData();
			sectionClient = (Composite)section.getData();
		}
		
				
		boolean emptySection = true;
				
		InputComposite child;
		toolkit.createCompositeSeparator(section);
		Boolean isEnabled = null;
		
		
		for (String ontologyAttribute : categoryAttributes) {
			isEnabled = selection.get(ontologyAttribute);
			if (isEnabled == null)
				selection.put(ontologyAttribute,false);
			if ((isEnabled != null) &&
					isEnabled) {
				System.out.println(ontologyAttribute + " must be present");
				// the composite must be present
				if (! composites.containsKey(ontologyAttribute)) {
					System.out.println("creating composite");
					child = makeComposite(sectionClient, ontologyAttribute);
					
					composites.put(ontologyAttribute,child);
					emptySection = false;
				}
				else {
					System.out.println("present already");
					emptySection = false;
				}
				
			}
			else {
				// the composite must not be present
				System.out.println(ontologyAttribute + " must not be present");
				if (composites.containsKey(ontologyAttribute)) {
					System.out.println("removing");
					attributeGroup = (Group)composites.get(ontologyAttribute).getData();
					composites.get(ontologyAttribute).dispose();
					attributeGroup.dispose();
					
					composites.remove(ontologyAttribute);
				}
				
			}
			
		}
		if (emptySection) {
			System.out.println("section " + category + " deleted");
			section.dispose();
			sectionGroup.dispose();
			currentSections.remove(category);
		}
		else {
			currentSections.put(category,sectionGroup);
//			 LAYOUT /////
			ColumnLayout sectionClientLayout = new ColumnLayout();
			if (sectionClient.getChildren().length == 1)
				sectionClientLayout.maxNumColumns = 1;
			else
				sectionClientLayout.maxNumColumns = 2;
			
			sectionClientLayout.minNumColumns = 1;
			
			
			sectionClient.setLayout(sectionClientLayout);
			
			section.layout(true,true);
		}
		section.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(false);
			}
		});
		
		return ! emptySection;
		
	}

	private InputComposite makeComposite(Composite sectionClient, String ontologyAttribute) {
		boolean isRequired = false;
		String compositeType = null;
		List<String> predefinedValues = null;
		String editable = null;
		String label = null;
		String validatorName = null;
		//String valuesSource = null;
		String predefined = null;
		String defaultValue = null;
		String required = null;
		Group group = null;
		
		
		System.out.println("MAKING COMPOSITE FOR  " + ontologyAttribute);
		
		InputComposite child = null;
		
		compositeType = targetConfiguration.getString(ontologyAttribute + TYPE_SUFFIX);
			//loader.getAttribute(fileHandler,
			//		ontologyAttribute, "type");
		editable = targetConfiguration.getString(ontologyAttribute + EDITABLE_SUFFIX);
			//loader.getAttribute(fileHandler,
			//		ontologyAttribute, "editable");
		if (editable == null) {
			editable = "true";
		}
		validatorName = targetConfiguration.getString(ontologyAttribute + VALIDATOR_SUFFIX); 
			
			//loader.getAttribute(fileHandler,
			//	ontologyAttribute, "validator");
		
		
		label = targetMessagesResolver.getString(ontologyAttribute);
			//loader.getAttribute(fileHandler,
			//		ontologyAttribute, "label");
		
		defaultValue = targetConfiguration.getString(ontologyAttribute+ DEFAULT_SUFFIX);
		//	loader.getAttribute(fileHandler,
		//			ontologyAttribute, "default");
		
		required = targetConfiguration.getString(ontologyAttribute + REQUIRED_SUFFIX); 
			
		//	loader.getAttribute(fileHandler,
		//		ontologyAttribute, "required");
		
		if (required != null) {
			isRequired = Boolean.valueOf(required);
		}

		predefined = targetConfiguration.
		getString(ontologyAttribute + PREDEFINED_SUFFIX);
			
		//loader.getAttribute(fileHandler,
		//			ontologyAttribute, "predefined");
		
		if ((predefined != null) && predefined.equals("true")) {
			predefinedValues = loadPredefinedValues(ontologyAttribute);
		}
		
		group = new Group(sectionClient,SWT.SHADOW_NONE);
		group.setLayout(new FillLayout());
		
		if (isRequired)
			group.setText(label + " (Required)");
		else 
			group.setText(label);
		
		child = CompositeFactory.createComposite(compositeType,
				group, SWT.NONE,"submitOntologyPreferences." + ontologyAttribute,
				predefinedValues,editable,validatorName,defaultValue,
				isRequired);
		
		child.setData(group);
		return child;
		
	}

	private List<String> loadPredefinedValues(String ontologyAttribute) {
		String valuesSource = null;
		List<String> values = null;
		String typeOfSource = targetConfiguration.getString(ontologyAttribute + SOURCE_TYPE_SUFFIX);
//			loader.getAttribute(fileHandler,
//					ontologyAttribute,"file");
		
		if (typeOfSource.equals(FILE_SOURCE)) {
			valuesSource = targetConfiguration.getString(ontologyAttribute + SOURCE_SUFFIX);
			String path = Activator.getDefault().getResourcesDir();
			PropertiesConfiguration configuration;
			try {
				configuration = new PropertiesConfiguration(
						path + valuesSource );
				values = configuration.getList(ELEMENTS_KEY);
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
		}
		else if (typeOfSource.equals(ONTOLOGY_SOURCE)) {
			String theClass = 
				targetConfiguration.getString(ontologyAttribute + CLASS_SUFFIX);
				String namespacesToStrip = targetConfiguration.getString(NAMESPACE_KEY);
				//loader.getAttribute(fileHandler,
				//	ontologyAttribute,"class"); 
				try {
					values = 
					Oyster2.getSharedInstance().getOMVInstanceNames(theClass,namespacesToStrip);
				} catch (KAON2Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}
		}
		else if (typeOfSource.equals(BOOLEAN_SOURCE)) {
			values = booleanValues;
		}
		return values;
	}

	private void makeTargetComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FormLayout());
		
		Label label = new Label(composite,SWT.NONE);
		label.setText(messagesResolver.getString("target.label"));
		
		targetCombo = new Combo(composite,SWT.DROP_DOWN|SWT.BORDER);
		targets = generalConfiguration.getStringArray(IDENTIFIERS_KEY);
		String target = null;
		
		for (int i = 0; i<targets.length;i++) {
			target = targets[i];
			targetCombo.add(messagesResolver.getString(target),i);
		}
		
		
		
		attributesSelectionButton = new Button(composite,SWT.PUSH);
		attributesSelectionButton.setText("Select attributes");
		makeTargetCompositeListeners();
		targetCombo.select(0);
		FormData fd = null;
		
		//make layout of this composite
		fd = new FormData();
		fd.left = new FormAttachment(0,0);
		fd.bottom = new FormAttachment(100,0);
		label.setLayoutData(fd);
		
		fd = new FormData();
		fd.left = new FormAttachment(label,5);
		fd.bottom = new FormAttachment(100,0);
		fd.right = new FormAttachment(attributesSelectionButton,0);
		targetCombo.setLayoutData(fd);
		
		fd = new FormData();
		
		fd.bottom = new FormAttachment(100,0);
		fd.right = new FormAttachment(100,0);
		attributesSelectionButton.setLayoutData(fd);
		
	} 

	
	private void configureForm(Composite baseComposite) {
		ColumnLayout layout = new ColumnLayout();
		
		layout.topMargin = 0;
		layout.bottomMargin = 5;
		layout.leftMargin = 10;
		layout.rightMargin = 10;
		layout.horizontalSpacing = 10;
		layout.verticalSpacing = 10;
		layout.maxNumColumns = 1;
		layout.minNumColumns = 1;
		
		toolkit = new FormToolkit(baseComposite.getDisplay());
		
		form = toolkit.createScrolledForm(baseComposite);
		form.getBody().setLayout(layout);
		form.setText("Ontology metadata");
		
		form.setBackground(baseComposite.getBackground());
		form.setBackgroundMode(SWT.INHERIT_NONE);
	}
	
	
	
	@Override
	protected void okPressed() {
		ArrayList<String> allErrors = new ArrayList<String>();
		ArrayList<String> validationErrors = null;
		if (composites.size() == 0) {
			allErrors.add(messagesResolver.getString("form.empty"));
		}
		Iterator<String> keys = composites.keySet().iterator();
		String key = null;
		String message = null;
		
		//Iterator<InputComposite> it = rows.values().iterator();
		InputComposite composite = null;
		 
		while (keys.hasNext()) {
			key = keys.next();
			composite = composites.get(key);
			if ( !composite.testFilled()) {
				message = messagesResolver.getString("missing.field");
				allErrors.add(message + ": " + targetMessagesResolver.getString(key));
			}
			else {
				validationErrors = composite.validate(); 
				if (validationErrors == null) {
					composite.saveSettings();
				}
				else {
					if (validationErrors.size() != 0) {
						for (String errorMessage : validationErrors) {
							allErrors.add(targetMessagesResolver.getString(key) + ": " +errorMessage);
						}
					}
					else {
						composite.saveSettings();
					}
				}
			}
		}
		if (allErrors.size() == 0) {
			super.okPressed();
		}
		else {
			String dialogErrorMessage = "";
			for (String errorMessage : allErrors) {
				dialogErrorMessage = dialogErrorMessage + "\n\t" + errorMessage;
			}
			message = messagesResolver.getString("error");
			MessageDialog.openError(getShell(),message,dialogErrorMessage);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.dialogs.ResizableDialog#close()
	 */
	public boolean close() {
		boolean returnCode  = super.close();
		toolkit.dispose();
		return returnCode;
	}

	/**
	 * @return the composites
	 */
	public final Map<String, InputComposite> getComposites() {
		return composites;
	}

	/**
	 * @param composites the composites to set
	 */
	public final void setComposites(Map<String, InputComposite> composites) {
		this.composites = composites;
	}
	
	
}