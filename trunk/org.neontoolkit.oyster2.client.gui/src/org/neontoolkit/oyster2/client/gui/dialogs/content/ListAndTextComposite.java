/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs.content;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.dialogs.InputDialog;




/**
 * @author David Muñoz
 *
 */
public class ListAndTextComposite extends InputComposite {
	
	private Text text = null;
	
	private List list = null;
	
	private Set<String> items = null;
	
	private Button addButton = null;
	
	private Button removeButton = null;
	
	private Button browseButton = null;
	
	private InputDialog nestedDialog = null;
	
	private static final int numberOfSavedItems = 20;
	
	public ListAndTextComposite(Composite parent, int style,String section,
			String []predefined) {
		super(parent,style,section,predefined);
		items = new HashSet<String>();
		setSaveSettingsOnExit(true);
		setCustomSettingsSection(section);
		String []listItems = null;
		FormData formData = null;
		//ResourceLoader rl = ResourceLoader.getResourceLoader();
		
		this.setLayout(new FormLayout());
		//buttons

		addButton = new Button(this, SWT.PUSH);
		addButton.setText("Add");
		formData = new FormData();
		formData.right = new FormAttachment(100,-5);
		formData.width = buttonWidth;  // Generated
		formData.top = new FormAttachment(0,5);
		addButton.setLayoutData(formData);
		
		
		removeButton = new Button(this, SWT.PUSH);
		removeButton.setText("Remove");
		
		formData = new FormData();
		formData.top = new FormAttachment(addButton,5);
		formData.right = new FormAttachment(100,-5);
		formData.width = buttonWidth;
		removeButton.setLayoutData(formData);
		
		
		browseButton = new Button(this,SWT.PUSH);
		browseButton.setText("...");
		formData = new FormData();
		formData.top = new FormAttachment(removeButton,5);
		formData.right = new FormAttachment(100,-5);
		formData.width = buttonWidth;
		browseButton.setLayoutData(formData);		
		
		
		Composite inputComposite = makeInputComposite(listItems);
		
		
		formData = new FormData();
		formData.left = new FormAttachment(0,0);
		formData.top = new FormAttachment(0,0);
		formData.right = new FormAttachment(addButton,-5);
		formData.bottom = new FormAttachment(100,0);
		inputComposite.setLayoutData(formData);
		
		setInput(list.getItems());
		
		makeListeners();
		
	}
	
	
	private void makeListeners() {
		Listener listener = null;
		listener = new Listener() {
			public void handleEvent(Event event) {
				if (event.widget == addButton) {
					if(! text.getText().trim().equals("")) {
						
						if (!items.contains(text.getText())) {
							list.add(text.getText());
							items.add(text.getText());
							setInput(list.getItems());	
						}
					}
				}
				else if (event.widget == removeButton) {
					String []selected = list.getSelection();
					for (int i = 0;i<selected.length;i++) {
						items.remove(selected[i]);
					}
					list.remove(list.getSelectionIndices());
					
					setInput(list.getItems());
				}
				else if (event.widget == browseButton) {
					
					int result = nestedDialog.open();
					if (result == TrayDialog.OK) {
						String [] newItems = (String [])nestedDialog.getInput();
						addItems(newItems);
					}
				}
			}
		};
		addButton.addListener(SWT.Selection, listener);
		removeButton.addListener(SWT.Selection, listener);
		browseButton.addListener(SWT.Selection, listener);
		
	}

	private void addItems(String [] newItems) {
		
		if (newItems == null)
			return;
		for (int i = 0;i<newItems.length;i++) {
			if (! items.contains(newItems[i])) {
				list.add(newItems[i]);
				items.add(newItems[i]);
			}
		}
		setInput(list.getItems());
		
	}
	
	private Composite makeInputComposite(String [] items) {
		Composite inputComposite = new Composite(this, SWT.NONE);
		FormLayout layout = new FormLayout();
		FormData fd = null;
		
		inputComposite.setLayout(layout);
		text = new Text(inputComposite, SWT.BORDER | SWT.WRAP | SWT.SINGLE);
		fd = new FormData();
		fd.top = new FormAttachment(0,5);
		fd.left = new FormAttachment(0,5);
		fd.right = new FormAttachment(100,-5);
		text.setLayoutData(fd);
		
		
		Composite gridComposite = new Composite(inputComposite,SWT.NONE);
		gridComposite.setLayout(new GridLayout(1,false));
		
		fd = new FormData();
		fd.top = new FormAttachment(text,0);
		fd.left = new FormAttachment(0,0);
		fd.right = new FormAttachment(100,0);
		fd.bottom = new FormAttachment(100,0);
		gridComposite.setLayoutData(fd);
		
		list = new List(gridComposite, SWT.MULTI|SWT.BORDER|SWT.H_SCROLL| SWT.V_SCROLL);
		list.setLayoutData(makeGridForControl(list,5));
		
		return inputComposite;
	}

	@Override
	public void saveSettings() {
		
		IDialogSettings nestedDialogSettings =
			Activator.getDefault().getDialogSettings().
				getSection(dialogSettings.getName()+InputDialog.DIALOG_SETTINGS_SUFFIX);
		if (getSaveSettingsOnExit()) {
			LinkedHashSet<String> itemsToSave = new LinkedHashSet<String>();
			String []listItems = list.getItems();
			String []oldItems = nestedDialogSettings.getArray(InputDialog.ITEMS_KEY);
			if (oldItems == null) {
				nestedDialogSettings.put(InputDialog.ITEMS_KEY,listItems);
				return;
			}
			else if(oldItems.length == 0) {
				nestedDialogSettings.put(InputDialog.ITEMS_KEY,listItems);
				return;
			}
			
			int numberOfNewItems = listItems.length;
			
			if (numberOfNewItems >= numberOfSavedItems) {
				nestedDialogSettings.put(InputDialog.ITEMS_KEY,listItems);
				return;
			}
			
			// we must merge the newest of the old with the new list
			int slotsRemaining = numberOfSavedItems-numberOfNewItems;
			int i = 0;
			if (slotsRemaining > oldItems.length) {
				for (i=0;i<oldItems.length;i++ )
					itemsToSave.add(oldItems[i]);
			}
			else {
				for (i = oldItems.length - slotsRemaining;i<oldItems.length;i++) {
					itemsToSave.add(oldItems[i]);
				}
			}
			
			
			for (i = 0;i<listItems.length;i++) {
					itemsToSave.add(listItems[i]);
			}
			
			listItems = itemsToSave.toArray(listItems);
			nestedDialogSettings.put(InputDialog.ITEMS_KEY,listItems);
		
		}
	}
	
	/**
	 * @return the nestedDialog
	 */
	public final InputDialog getNestedDialog() {
		return nestedDialog;
	}


	/**
	 * @param nestedDialog the nestedDialog to set
	 */
	public final void setNestedDialog(InputDialog nestedDialog) {
		this.nestedDialog = nestedDialog;
	}


	@Override
	public boolean testFilled() {
		if (isRequired()) {
			return list.getItemCount() != 0;
		}
		return true;
	}
	
	
	
	
}
