/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ColumnLayout;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.GUIConstants;
import org.neontoolkit.oyster2.client.gui.OMVOntologyMetadata;
import org.neontoolkit.oyster2.client.gui.Oyster2;
import org.neontoolkit.oyster2.client.gui.TargetServerComposite;
import org.neontoolkit.oyster2.client.gui.XMLResourceLoader;
import org.neontoolkit.oyster2.client.gui.dialogs.content.CompositeFactory;
import org.neontoolkit.oyster2.client.gui.dialogs.content.InputComposite;
import org.neontoolkit.oyster2.client.gui.util.Template;
import org.neontoolkit.oyster2.client.gui.util.TemplateManager;
import org.semanticweb.kaon2.api.KAON2Exception;


/**
 * @author David Muñoz
 *
 */
public class SubmitDialog extends ResizableDialog {

	//constants
	public static final String SUBMIT_DIALOG_SETTINGS_SECTION ="submitDialogSettings";
	
	
	private TargetServerComposite serverComposite = null;
	private FormToolkit toolkit;
	private ScrolledForm form;
	private XMLResourceLoader loader;
	private ArrayList<String> booleanValues = null;
	private Button selectAttributesButton = null;
	private Template template = null;
	
	// we hold a reference to the controls in this dialog so we
	// are able to retrieve input values. The keys are the constants
	// suffixed with _ROW
	/**
	 * The key holds the name associated with OMV's ontology attribute,
	 * and the InputComposite a reference to the Composite
	 * holding a reference to the input control.
	 * Map<String, InputComposite>
	 */
	private Map<String,InputComposite> rows = null;
	private Map<String,String> labels = null;
	
	public SubmitDialog(Shell parentShell) {
		super(SUBMIT_DIALOG_SETTINGS_SECTION, parentShell);
		loader = XMLResourceLoader.getResourceLoader();
		booleanValues = new ArrayList<String>();
		booleanValues.add("true");
		booleanValues.add("false");
	}
	

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Submit metadata");
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	
	protected Control createDialogArea(Composite parent) {
		
		Composite baseComposite = (Composite) super.createDialogArea(parent);
		baseComposite.setLayout(new FillLayout());
		String namespaceToStrip = null;
		
		Group group = null;
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
		
		form.setBackground(parent.getBackground());
		form.setBackgroundMode(SWT.INHERIT_NONE);
		
		rows = new HashMap<String,InputComposite>();
		labels = new HashMap<String,String>();
		ArrayList<String> categories;
		ArrayList<String> attributes;
		Object handler = null;
		try {
			serverComposite = new TargetServerComposite(form.getBody(),SWT.NONE);
			handler = loader.open(OMVOntologyMetadata.ONTOLOGY_ATTRIBUTES_FILE);
			namespaceToStrip = loader.getElement(handler,"attributesNamespace");
			categories = loader.getArray(handler,
					GUIConstants.CATEGORIES_KEY);
		
			template = TemplateManager.getInstance().getTemplate("submitOntology");
			
		String sectionLabel;
		for ( String category : categories) {
			group = new Group(form.getBody(),SWT.SHADOW_NONE);
			group.setLayout(new FillLayout());
			group.setBackground(parent.getBackground());
			sectionLabel =
				loader.getElement(handler,
						category);
			attributes =
				loader.getArray(handler,category);
			makeSection(handler,sectionLabel, attributes, group,namespaceToStrip);
		}
		
		
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally {
			try { loader.close(handler); } catch (NullPointerException e) {};
		}
		return baseComposite;
	}



	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.dialogs.ResizableDialog#close()
	 */
	public boolean close() {
		boolean returnCode  = super.close();
		toolkit.dispose();
		return returnCode;
	}

	private Section makeSection(Object fileHandler,String name,
			ArrayList<String>attributes,Composite parent,String namespaceToStrip)
				throws XMLStreamException, IOException, Exception {
		
		Section section = toolkit.createSection(parent, GUIConstants.SECTION_STYLE);
		boolean isRequired = false;
		String compositeType = null;
		ArrayList<String> predefinedValues = null;
		String editable = null;
		String label = null;
		String validatorName = null;
		//String valuesSource = null;
		String predefined = null;
		String defaultValue = null;
		String required = null;
		Group group = null;
		Composite sectionClient = null;
		InputComposite child = null;
				
		section.setText(name);
		
		section.setLayout(new FillLayout());
		sectionClient = toolkit.createComposite(section);
		section.setClient(sectionClient);
		sectionClient.setBackground(form.getBackground());
		
		// LAYOUT /////
		ColumnLayout sectionClientLayout = new ColumnLayout();
		sectionClientLayout.maxNumColumns = 2;
		sectionClientLayout.minNumColumns = 2;
		
		sectionClient.setLayout(sectionClientLayout);
		
		// COLORS
		section.setBackground(form.getBackground());
		section.setTitleBarBackground(form.getBackground());
		
		
		toolkit.createCompositeSeparator(section);
		for (String ontologyAttribute : attributes) {
			predefinedValues = null;
			required = null;
			editable = null;
			label = null;
			isRequired = false;
			validatorName = null;
			//valuesSource = null;
			predefined = null;
			defaultValue = null;
			compositeType =
				loader.getAttribute(fileHandler,
						ontologyAttribute, "type");
			editable =
				loader.getAttribute(fileHandler,
						ontologyAttribute, "editable");
			if (editable == null) {
				editable = "true";
			}
			validatorName = loader.getAttribute(fileHandler,
					ontologyAttribute, "validator");
			label =
				loader.getAttribute(fileHandler,
						ontologyAttribute, "label");
			defaultValue =
				loader.getAttribute(fileHandler,
						ontologyAttribute, "default");
			required = loader.getAttribute(fileHandler,
					ontologyAttribute, "required");
			
			if (required != null) {
				isRequired = Boolean.valueOf(required);
			}

			predefined =
				loader.getAttribute(fileHandler,
						ontologyAttribute, "predefined");
			if ((predefined != null) && predefined.equals("true")) {
				predefinedValues = loadPredefinedValues(fileHandler, ontologyAttribute,
						namespaceToStrip);
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
			
			rows.put(ontologyAttribute,child);
			labels.put(ontologyAttribute,label);
		}
		
		section.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(false);
			}
		});
		
		return section;
	}

	private ArrayList<String> loadPredefinedValues(Object fileHandler,
				String ontologyAttribute, String namespacesToStrip) throws XMLStreamException,
				IOException, KAON2Exception {
		ArrayList<String> predefinedValues = null;
		String valuesSource = null;
		
		valuesSource =
			loader.getAttribute(fileHandler,
					ontologyAttribute,"file");
		if (valuesSource != null) {
			//read from file
			String resourcesDir = Activator.getDefault().getResourcesDir();
			Object handler = loader.open(resourcesDir + valuesSource);
			predefinedValues = loader.getArray(handler);
			loader.close(handler);
			return predefinedValues;
		}
		valuesSource =
			loader.getAttribute(fileHandler,
					ontologyAttribute,"ontology");
		if (valuesSource != null) {
			String theClass =loader.getAttribute(fileHandler,
					ontologyAttribute,"class"); 
			predefinedValues =
				Oyster2.getSharedInstance().getOMVInstanceNames(theClass,namespacesToStrip);
			return predefinedValues;
		}
		else {
			predefinedValues = booleanValues;
			return predefinedValues;
		}
		
	}
	
	@Override
	protected void okPressed() {
		
		Iterator<String> keys = rows.keySet().iterator();
		String key = null;
		
		
		//Iterator<InputComposite> it = rows.values().iterator();
		InputComposite composite = null;
		ArrayList<String> allErrors = new ArrayList<String>();
		ArrayList<String> validationErrors = null; 
		while (keys.hasNext()) {
			key = keys.next();
			composite = rows.get(key);
			if ( !composite.testFilled()) {
				allErrors.add("Missing required field: " + labels.get(key));
			}
			else {
				validationErrors = composite.validate(); 
				if (validationErrors == null) {
					composite.saveSettings();
				}
				else {
					if (validationErrors.size() != 0) {
						for (String errorMessage : validationErrors) {
							allErrors.add(labels.get(key) + ": " +errorMessage);
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
			MessageDialog.openError(getShell(),"Error",dialogErrorMessage);
		}
	}

	public Map<String, InputComposite> getRows() {
		return rows;
	}



	public void setRows(Map<String, InputComposite> rows) {
		this.rows = rows;
	}
	
	
	
}
