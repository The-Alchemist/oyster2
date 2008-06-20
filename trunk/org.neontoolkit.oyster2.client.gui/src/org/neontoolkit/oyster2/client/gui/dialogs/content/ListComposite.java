/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs.content;

import java.util.HashSet;
import java.util.Set;

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
import org.neontoolkit.oyster2.client.gui.dialogs.InputDialog;


/**
 * @author David Muñoz
 *
 */
public class ListComposite extends InputComposite {
	
	private List list = null;
	
	private Button addButton = null;
	
	private Button removeButton = null;
	
	private InputDialog nestedDialog = null;
	
	private Set<String> items = null;
	
	public ListComposite(Composite parent, int style,
			String section, String [] predefined) {
		super(parent,style,section,predefined);
		//ResourceLoader rl = ResourceLoader.getResourceLoader();
		if (predefined != null) { 
			if (predefined.length == 0) {
				throw new RuntimeException("Must have predefined values");
			}
			else {
				setSaveSettingsOnExit(false);
			}
		}
		else {
			throw new RuntimeException("Must have predefined values");
		}
		items = new HashSet<String>();
		FormData formData = null;
		String [] listItems = null;
		this.setLayout(new FormLayout());
		
		//buttons

		addButton = new Button(this, SWT.PUSH);
		addButton.setText("Add");
		formData = new FormData();
		formData.right = new FormAttachment(100,-5);
		formData.width = buttonWidth;  
		formData.top = new FormAttachment(0,5);
		addButton.setLayoutData(formData);
		
		
		removeButton = new Button(this, SWT.PUSH);
		removeButton.setText("Remove");
		
		formData = new FormData();
		formData.top = new FormAttachment(addButton,5);
		formData.right = new FormAttachment(100,-5);
		formData.width = buttonWidth;
		removeButton.setLayoutData(formData);
		
		formData = new FormData();
		formData.top = new FormAttachment(0,0);
		formData.right = new FormAttachment(addButton,0);
		formData.left = new FormAttachment(0,0);
		
		
		Composite listGrid = new Composite(this,SWT.NONE);
		listGrid.setLayoutData(formData);
		listGrid.setLayout(new GridLayout(1,false));
		
		list = new List(listGrid, SWT.MULTI|SWT.BORDER|SWT.H_SCROLL| SWT.V_SCROLL);
		list.setLayoutData(makeGridForControl(list,5));

		addItems(listItems);
		
		makeListeners();
	}

	private void addItems(String [] newItems) {
		//
		if (newItems != null) {
			for (int i = 0;i<newItems.length;i++) {
				if (! items.contains(newItems[i])) {
					list.add(newItems[i]);
					items.add(newItems[i]);
				}
			}
		}
		setInput(list.getItems());
		
	}
	
	
	private void makeListeners() {
		Listener listener = null;
		listener = new Listener() {
			public void handleEvent(Event event) {
				if (event.widget == removeButton) {
					String []selected = list.getSelection();
					for (int i = 0;i<selected.length;i++) {
						items.remove(selected[i]);
					}
					list.remove(list.getSelectionIndices());
					
					setInput(list.getItems());
				}
				else if (event.widget == addButton) {
					int result;
					result = nestedDialog.open();
					
					if (result == TrayDialog.OK) {
						String [] newItems = (String[])nestedDialog.getInput();
						addItems(newItems);
					}
				}
			}
		};
		addButton.addListener(SWT.Selection, listener);
		removeButton.addListener(SWT.Selection, listener);
	}
	
	@Override
	public void saveSettings() {
		
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
