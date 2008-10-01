/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs.content;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.neontoolkit.oyster2.client.gui.Activator;

/**
 * @author David Muñoz
 *
 */
public class OrganizationSelectionComposite extends Composite {
	
	private static final String NAME_COMBO_ITEMS_KEY = "organizationNamesKey"; //$NON-NLS-1$
		
	private static final int numberOfSavedValues = 20;
	
	private Combo nameCombo = null;
	
	private List list = null;
	
	private Button addButton = null;
	
	private Button removeButton = null;
	
	private Set<String> organizations = null;
	
	private IDialogSettings dialogSettings = null;
	
	public OrganizationSelectionComposite(Composite parent, int style,
			String [] items, String sectionName) {
		super(parent,style);
		
		
		organizations = new HashSet<String>();

		dialogSettings = Activator.getDefault().getDialogSettings().getSection(sectionName);
		if (dialogSettings == null) {
			Activator.getDefault().getDialogSettings().addNewSection(sectionName);
			dialogSettings = Activator.getDefault().getDialogSettings().getSection(sectionName);
		}
		String [] nameComboItems = dialogSettings.getArray(NAME_COMBO_ITEMS_KEY);
		
		
		setLayout(new FormLayout());
		FormData fd = null;
		
		Label label = new Label(this,SWT.NONE);
		label.setText(Messages.getString("OrganizationSelectionComposite.name.label")); //$NON-NLS-1$
		fd = new FormData();
		fd.top = new FormAttachment(0,5);
		fd.left = new FormAttachment(0,5);
		label.setLayoutData(fd);
		
		addButton = new Button(this, SWT.PUSH);
		addButton.setText(Messages.getString("OrganizationSelectionComposite.addbutton.text")); //$NON-NLS-1$
		fd = new FormData();
		fd.right = new FormAttachment(100,-5);
		fd.top = new FormAttachment(0,5);
		fd.width = CompositeConstants.BUTTON_WIDTH;
		addButton.setLayoutData(fd);
				
		nameCombo = new Combo(this, SWT.DROP_DOWN);
		if (nameComboItems != null) {
			nameCombo.setItems(nameComboItems);
						
		};
		fd = new FormData();
		fd.left = new FormAttachment(label,5);
		fd.right = new FormAttachment(addButton,-5);
		fd.top = new FormAttachment(0,5);
		nameCombo.setLayoutData(fd);
		
		label = new Label(this,SWT.NONE);
		label.setText(Messages.getString("OrganizationSelectionComposite.organizations.present.label")); //$NON-NLS-1$
		fd = new FormData();
		fd.left = new FormAttachment(0,5);
		fd.top = new FormAttachment(nameCombo,5);
		label.setLayoutData(fd);
		
		
		
		removeButton = new Button(this,SWT.PUSH);
		removeButton.setText(Messages.getString("OrganizationSelectionComposite.removebutton.text")); //$NON-NLS-1$
		fd = new FormData();
		fd.left = new FormAttachment(0,5);
		fd.bottom = new FormAttachment(100,-5);
		fd.width = CompositeConstants.BUTTON_WIDTH;
		removeButton.setLayoutData(fd);
		
		list = new List(this,SWT.MULTI|SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL);
		list.setItems(items);
		for (int i = 0;i<items.length;i++) {
			organizations.add(items[i]);
		};
		fd = new FormData();
		fd.left = new FormAttachment(0,5);
		fd.top = new FormAttachment(label,5);
		fd.bottom = new FormAttachment(removeButton,-5);
		fd.right = new FormAttachment(100,-5);
		list.setLayoutData(fd);
		
		
		
		
		makeListeners();
	}
	
	public void save() {
		
		int numberToSave = 0;
		if (organizations.size() > numberOfSavedValues) {
			numberToSave = numberOfSavedValues;
		}
		else {
			numberToSave = organizations.size();
		}
		
		String []itemsArray = new String[numberToSave];
		
		int i = 0;
		String []comboItems = nameCombo.getItems();
		
		for(i = numberToSave-1;i>=0;i--) {
			itemsArray[i] = comboItems[i];
		};
		
		dialogSettings.put(NAME_COMBO_ITEMS_KEY,itemsArray);
		
	}
	
	private void makeListeners() {
		Listener listener = new Listener(){
			public void handleEvent(Event event) {
				if (event.widget == addButton) {
					
					String name = nameCombo.getText().trim();

					if( ! name.equals("")) { //$NON-NLS-1$
						if (! organizations.contains(name)) {
							list.add(name);
							nameCombo.add(name);
							organizations.add(name);
						}
					}
				}
				else if (event.widget == removeButton) {
					organizations.remove(list.getSelection());
					list.remove(list.getSelectionIndices());
					
				}
			}
			
		};
		addButton.addListener(SWT.Selection, listener);
		removeButton.addListener(SWT.Selection, listener);
	}

	/**
	 * @return the list
	 */
	public final List getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public final void setList(List list) {
		this.list = list;
	}
	
	
	
}
