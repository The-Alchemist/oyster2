/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
public class DomainComposite extends InputComposite {

	private List list = null;
	
	private Map<String,String> labelsMap = null;
	
	private Button addButton = null;
	
	private Button removeButton = null;
	
	private InputDialog nestedDialog = null;
		
	public DomainComposite(Composite parent, int style, String section,
			String [] predefined) {
		super(parent,style,section,predefined);
		labelsMap = new HashMap<String, String>();
		FormData formData = null;
		
		this.setLayout(new FormLayout());
		
		//buttons

		addButton = new Button(this, SWT.PUSH);
		addButton.setText(Messages.getString("DomainComposite.addbutton.text")); //$NON-NLS-1$
		formData = new FormData();
		formData.right = new FormAttachment(100,-5);
		formData.width = buttonWidth;  
		formData.top = new FormAttachment(0,5);
		addButton.setLayoutData(formData);
		
		
		removeButton = new Button(this, SWT.PUSH);
		removeButton.setText(Messages.getString("DomainComposite.removebutton.text")); //$NON-NLS-1$
		
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
		list.setLayoutData(makeGridForControl(list,6));
		
		
		setInput(list.getItems());
		makeListeners();
	}

	private void makeListeners() {
		Listener listener = null;
		listener = new Listener() {
			public void handleEvent(Event event) {
				
				if (event.widget == removeButton) {
					String [] selected = list.getSelection();
					
					for (int i = 0;i<selected.length;i++) {
						labelsMap.remove(selected[i]);
					}
					String [] newValues = new String[labelsMap.size()];
					list.remove(list.getSelectionIndices());
					setInput(labelsMap.values().toArray(newValues));
				}
				else if (event.widget == addButton) {
					int result;
					
					result = nestedDialog.open();
					
					if (result == TrayDialog.OK) {
						labelsMap = (Map<String,String>)nestedDialog.getInput();
						
						String []newList = new String[labelsMap.size()]; 
						newList = labelsMap.keySet().toArray(newList);
						list.setItems(newList);
						setInput((labelsMap.values().toArray(newList)));
					}
				}
			}
		};
		addButton.addListener(SWT.Selection, listener);
		removeButton.addListener(SWT.Selection, listener);
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
	
	@Override
	public void setInitialValue(Object value) {
		
		throw new RuntimeException("Not implemented yet");
		/*ArrayList<String> initialValues = 
			(ArrayList<String>)value;
		for (String domain : initialValues) {
			list.add(domain);
		}
		setInput(list.getItems());*/
	}
	
}
