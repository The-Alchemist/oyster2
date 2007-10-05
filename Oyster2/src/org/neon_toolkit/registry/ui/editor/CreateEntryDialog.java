package org.neon_toolkit.registry.ui.editor;

/*
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.semanticweb.kaon2.api.owl.elements.*;
*/

import java.util.Map;
import java.util.HashMap;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.semanticweb.kaon2.api.*;


public class CreateEntryDialog extends TitleAreaDialog{
	public static final String NUMBER_OF_TEMPLATES = "NUMBER_OF_TEMPLATES_";
	public static final String TEMPLATE = "TEMPLATE_";
	public static final String TEMPLATE_PROPERTIES = "TEMPLATE_PROPERTIES_";

	/* TODO private EntryEditor editor;*/
	private Entity entry;
	private Map templatesMap = new HashMap();

	private Button finishButton;
	private Button cancelButton;
	private Combo combo;

	public CreateEntryDialog(Shell shell) {
		super(shell);
		setShellStyle(SWT.CLOSE | SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL | SWT.RESIZE);
		/*TODO RDFObjectFactory factory = SystemConfig.getSWAPRepository().getRDFObjectFactory();
		this.entry = BibsterModelFactory.getInstance().createResource("urn://bibster.temporary#" + System.currentTimeMillis());
	*/
	}

	public Entity getEntry() {
		return this.entry;
	}

	/*
	 * (non-Javadoc) Method declared on Dialog.
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		finishButton = createButton(parent, IDialogConstants.FINISH_ID, IDialogConstants.FINISH_LABEL, true);
		cancelButton = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, true);
	}

	/*
	 * (non-Javadoc) Method declared on Dialog.
	 */
	protected void buttonPressed(int buttonId) {
		switch (buttonId) {
			case IDialogConstants.NEXT_ID :
				//nextPressed();
				break;
			case IDialogConstants.FINISH_ID :
				finishPressed();
				break;
			case IDialogConstants.CANCEL_ID :
				cancelPressed();
				break;
		}
	}

	/*
	 * (non-Javadoc) Method declared on Dialog.
	 */
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		/*TODO Group top = new Group(composite, SWT.NULL);
		top.setLayout(new GridLayout(4, false));
		top.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Label l1 = new Label(top, SWT.NULL);
		l1.setText("Template: ");
		combo = new Combo(top, SWT.FLAT);
		GridData comboGridData = new GridData();
		comboGridData.widthHint = 100;
		combo.setLayoutData(comboGridData);
		combo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				loadTemplate(combo.getText());
			}
		});
		
		loadTemplatesFile(combo);
		Button saveTemplateButton = new Button(top, SWT.PUSH | SWT.FLAT);
		saveTemplateButton.setText("Save");
		saveTemplateButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				saveTemplate(combo.getText());
			}
		});
		Button removeTemplateButton = new Button(top, SWT.PUSH | SWT.FLAT);
		removeTemplateButton.setText("Remove");
		removeTemplateButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				removeTemplate(combo.getText());
			}
		});
		
		Group pageContainer = new Group(composite, SWT.NULL);
		pageContainer.setLayout(new FillLayout());
		editor = new EntryEditor(pageContainer);
		editor.setEntry(this.entry);
		pageContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		pageContainer.setFont(parent.getFont());

		this.getShell().setText("Bibliographical entry editor");*/
		return composite;
	}

	private void finishPressed() {
		/*TODO List prop = editor.getProperties();
		for (int i = 0; i < prop.size(); i++) {
			DataProperty p = (DataProperty) prop.get(i);
			this.entry.addProperty(p);
		}
		Property p = this.entry.getProperty(RDF.TYPE);
		if (p == null || p.computePropertyValues() <= 0) {
			this.setMessage("The property rdf:type is mandatory.", IMessageProvider.ERROR);
		} else {
			setReturnCode(IDialogConstants.FINISH_ID);
			//saveTemplateFile(combo);
			close();
		}*/
	}

	protected Point getInitialSize() {
		return this.getShell().computeSize(SWT.DEFAULT, 400, true);
	}

	private void loadTemplatesFile(Combo combo) {
		/*TODO String templatesLocation = JFacePreferences.getPreferenceStore().getString(BibsterPreferencesPage.TEMPLATES_LOCATION_PROP);
		if (templatesLocation.equals("")) {
			this.setMessage("Templates file is not set!", IMessageProvider.ERROR);
		} else {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(templatesLocation));
				this.templatesMap = (Map)ois.readObject();
				
				Iterator it = templatesMap.entrySet().iterator();
				while(it.hasNext()){
					Template template = (Template)((Map.Entry)it.next()).getValue();
					combo.add(template.getName());
				}
			} catch (IOException ioe) {
				this.setMessage("Can't load templates file " + templatesLocation + "!", IMessageProvider.ERROR);
				ioe.printStackTrace();
			} catch(ClassNotFoundException cnfe){
				this.setMessage("Can't parse templates file " + templatesLocation + "!", IMessageProvider.ERROR);
			}
		}*/
	}

	private void saveTemplateFile(Combo combo) {
		/*TODO String templatesLocation = JFacePreferences.getPreferenceStore().getString(BibsterPreferencesPage.TEMPLATES_LOCATION_PROP);
		if (templatesLocation.equals("")) {
			this.setMessage("Templates file is not set!", IMessageProvider.ERROR);
		} else {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(templatesLocation));
				oos.writeObject(templatesMap);
			} catch (IOException ioe) {
				this.setMessage("Can't save templates file " + templatesLocation + "!", IMessageProvider.ERROR);
			}
		}*/
	}

	private void loadTemplate(String templateName) {
		/*TODO Object o = templatesMap.get(templateName);
		if(o != null){
			BibsterModelFactory factory = BibsterModelFactory.getInstance();
			Template template = ((Template)o);
			List prop = template.getProperties();
			this.entry = BibsterModelFactory.getInstance().createResource("urn://bibster.temporary#" + System.currentTimeMillis());
			for(int i=0; i<prop.size(); i++){
				this.entry.addProperty(factory.createProperty(prop.get(i).toString()));
			}
			editor.setEntry(this.entry);
		}*/
	}

	private void saveTemplate(String templateName) {
		/*TODO List list = editor.getProperties();
		Template template = new Template(templateName);
		ArrayList propToSave = new ArrayList();
		for(int i=0; i<list.size(); i++){
			propToSave.add(((Property)list.get(i)).getURI().getURI());
		}
		if(!templatesMap.containsKey(templateName))
			combo.add(templateName);
		template.setProperties(propToSave);
		templatesMap.put(templateName, template);
		saveTemplateFile(this.combo);*/
	}
	
	private void removeTemplate(String templateName){
		templatesMap.remove(templateName);
		this.combo.remove(templateName);
		saveTemplateFile(this.combo);
	}

}
