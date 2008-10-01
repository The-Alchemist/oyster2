/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ColumnLayout;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.neontoolkit.oyster2.client.gui.GUIConstants;
import org.neontoolkit.oyster2.client.gui.IMessageResolver;
import org.neontoolkit.oyster2.client.gui.dialogs.content.TemplateComposite;
import org.neontoolkit.oyster2.client.gui.util.Template;


/**
 * @author David Muñoz
 *
 */
public class TranslatedAttributeSelectionDialog extends ResizableDialog {

	
	/**
	 * @author david
	 *
	 */
	public class CheckboxComposite extends Composite {

		String attribute = null;
		
		Button checkButton = null;
		/**
		 * @param parent
		 * @param style
		 */
		public CheckboxComposite(Composite parent, int style) {
			super(parent, style);
		}
		public String getAttribute() {
			return attribute;
		}
		public void setAttribute(String attribute) {
			this.attribute = attribute;
		}
		public Button getCheckButton() {
			return checkButton;
		}
		public void setCheckButton(Button checkButton) {
			this.checkButton = checkButton;
		}
		public Boolean isSelected() {
			return Boolean.valueOf(checkButton.getSelection());
		}

	}

	private Map<String,Boolean> selection = null;
	
	private Map<String,String []> categories = null;
		
	private FormToolkit toolkit = null;
	
	private ScrolledForm form = null;
	
	private IMessageResolver messagesResolver = null;
	
	private Map<String,CheckboxComposite> composites = null;
	
	private Template template = null;
	
	private TemplateComposite templateComposite = null;
	
	/**
	 * @param section
	 * @param parentShell
	 */
	public TranslatedAttributeSelectionDialog(String section, Shell parentShell,Template template) {
		super(section, parentShell);
		this.template = template;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param section
	 * @param parentShell
	 */
	public TranslatedAttributeSelectionDialog(String section, IShellProvider parentShell,
			Template template) {
		super(section, parentShell);
		this.template = template;
		// TODO Auto-generated constructor stub
	}

	
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Attribute selection");
	}
	
	
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite baseComposite = (Composite)super.createDialogArea(parent);
		
		composites = new HashMap<String,CheckboxComposite>();
		
		baseComposite.setLayout(new FillLayout());
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
		
		form.setText("Select attributes to show");

		form.setBackground(parent.getBackground());
		form.setBackgroundMode(SWT.INHERIT_NONE);
		
		makeHeaderSection(form.getBody());
		if (categories != null){ 
			makeDialogWithCategories(form.getBody());
		}
		else {
			makeDialogWithoutCategories(form.getBody());
		}
		makeListeners();
		form.pack();
		return baseComposite;
	}

	private void makeHeaderSection(Composite parent) {
		if (template != null)
			templateComposite = 
				new TemplateComposite(parent,SWT.NONE,template);
		
	}

	private void makeListeners() {
		if (template != null) {
		Listener templateListener = new Listener() {
			public void handleEvent(Event event) {
				List<String> enabledProperties = templateComposite.getCurrentTemplate();
				String attribute = null;
				for (Entry<String,CheckboxComposite> entry : composites.entrySet()) {
					attribute = entry.getKey();
					if (enabledProperties.contains(attribute)) {
						entry.getValue().checkButton.setSelection(true);
						selection.put(attribute, true);
					}
					else {
						entry.getValue().checkButton.setSelection(false);
						selection.put(attribute,false);
					}
				}
			}
			
		};
		templateComposite.addTemplateListener(templateListener);
		}
	}
	
	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.SELECT_ALL_ID) {
			for(CheckboxComposite composite : composites.values()) {
				composite.getCheckButton().setSelection(true);
			}
			for (Entry<String,Boolean> entry : selection.entrySet()) {
				entry.setValue(Boolean.valueOf(true));
			}
		}
		else if (buttonId == IDialogConstants.DESELECT_ALL_ID) {
			for(CheckboxComposite composite : composites.values()) {
				composite.getCheckButton().setSelection(false);
			}
			for (Entry<String,Boolean> entry : selection.entrySet()) {
				entry.setValue(Boolean.valueOf(false));
			}
		}
		else
			super.buttonPressed(buttonId);
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.SELECT_ALL_ID,
				"Select All", true);
		createButton(parent, IDialogConstants.DESELECT_ALL_ID,
				"Deselect All", true);
		super.createButtonsForButtonBar(parent);
		
	}
	
	/**
	 * Populates the form body with composites having the checkbuttons
	 * and sets a layout on it.
	 *
	 */
	private void makeDialogWithoutCategories(Composite parent) {
		Composite fieldsComposite = new Composite(parent,SWT.NONE);
		ColumnLayout layout = new ColumnLayout();
		layout.maxNumColumns = 3;
		layout.minNumColumns = 2;
		fieldsComposite.setLayout(layout);
		CheckboxComposite composite = null;
		for (Map.Entry<String,Boolean> entry : selection.entrySet()) {
			composite = makeCheckComposite(fieldsComposite,entry.getKey());
			composites.put(entry.getKey(),composite);
		}
		
	}

	
	/**
	 * Populates the dialog area using sections for the categories
	 * in the categories map
	 */
	private void makeDialogWithCategories(Composite parent) {
		Composite fieldsComposite = new Composite(parent,SWT.NONE);
		GridLayout layout = new GridLayout();
		//layout.makeColumnsEqualWidth = true;
		layout.numColumns = 1;
		GridData gd = null;
		Composite sectionComposite = null;
		fieldsComposite.setLayout(layout);
		for ( Map.Entry<String, String[]> entry : categories.entrySet()) {
			sectionComposite = makeSection(fieldsComposite,entry);
			gd = new GridData();
			gd.grabExcessHorizontalSpace = true;
			gd.horizontalAlignment = SWT.FILL;
			sectionComposite.setLayoutData(gd);
		}
		
	}

	/**
	 * Creates a section for the given entry, which contains a category name
	 * as key and a List of attribute names as value. The section is added
	 * to the form body.
	 * @param entry
	 */
	private Composite makeSection(Composite parent,Entry<String,String[]> entry) {
		String category = entry.getKey();
		String[] attributes = entry.getValue();
		
		Group group = new Group(parent,SWT.SHADOW_NONE);
		group.setLayout(new FillLayout());
		group.setBackground(form.getBody().getBackground());
		
		
		Section section = toolkit.createSection(group, GUIConstants.SECTION_STYLE);
		section.setLayout(new FillLayout());
		section.setBackground(form.getBackground());
		section.setTitleBarBackground(form.getBackground());
		section.setText(messagesResolver.getString(category));
		
		
		Composite sectionClient = toolkit.createComposite(section);
		section.setClient(sectionClient);
		sectionClient.setBackground(form.getBackground());
		ColumnLayout layout = new ColumnLayout();
		layout.maxNumColumns = 3;
		layout.minNumColumns = 2;
		sectionClient.setLayout(layout);
		putCheckComposites(sectionClient, attributes);
		
		toolkit.createCompositeSeparator(section);
		section.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(false);
			}
		});
		return group;
	}
	
	/**
	 * Creates the composites containing checkboxes for the elements of the
	 * list as children of the given composite, and puts a reference to them in
	 * the Composites list
	 * @param parent The parent composite of the check composites
	 * @param attributes List of attributes that will have a checkbox in the
	 * parent composite
	 */
	private void putCheckComposites(Composite parent, String[] attributes) {
		CheckboxComposite checkbuttonComposite = null;
		for (String attribute : attributes) {
			checkbuttonComposite = makeCheckComposite(parent, attribute);
			this.composites.put(attribute,checkbuttonComposite);
		}
	}
	
	
	/**
	 * Creates a Composite with a single checkbutton, and selecting it if tagged
	 * as selected in the "selected" Map.
	 * list.
	 * @param parent
	 * @param attribute
	 * @return
	 */
	private CheckboxComposite makeCheckComposite(Composite parent,String attribute) {
		
		CheckboxComposite composite = new CheckboxComposite(parent,SWT.NONE);
		Button button = new Button(composite,SWT.CHECK);
		String label = null;
		label = messagesResolver.getString(attribute);
		if (selection.get(attribute).booleanValue()) {
			button.setSelection(true);
		}
		else {
			button.setSelection(false);
		}
		button.setText(label);
		composite.setCheckButton(button);
		composite.setAttribute(attribute);
		composite.setLayout(new FillLayout());
		return composite;
	}
	
	/*
	private void makeTemplateComposite() {
		Composite composite = new Composite(form.getBody(),SWT.NONE);
		composite.setLayout(new FormLayout());
		FormData fd = null;
		Label label = new Label(composite,SWT.NONE);
		label.setText("Select template");
		
	}*/
	
	
	@Override
	protected void okPressed() {
		for (CheckboxComposite composite : composites.values()) {
			selection.put(composite.getAttribute(),composite.isSelected());
		}
		super.okPressed();
	}

	public final Map<String, String[]> getCategories() {
		return categories;
	}

	public final void setCategories(Map<String, String[]> categories) {
		this.categories = categories;
	}

	public final Map<String, Boolean> getSelection() {
		return selection;
	}

	public final void setSelection(Map<String, Boolean> selection) {
		this.selection = selection;
	}

	/**
	 * @return the messagesResolver
	 */
	public final IMessageResolver getMessagesResolver() {
		return messagesResolver;
	}

	/**
	 * @param messagesResolver the messagesResolver to set
	 */
	public final void setMessagesResolver(IMessageResolver messagesResolver) {
		this.messagesResolver = messagesResolver;
	}

	/**
	 * @return the template
	 */
	public final Template getTemplate() {
		return template;
	}

	/**
	 * @param template the template to set
	 */
	public final void setTemplate(Template template) {
		this.template = template;
	}

	
	
	
}
