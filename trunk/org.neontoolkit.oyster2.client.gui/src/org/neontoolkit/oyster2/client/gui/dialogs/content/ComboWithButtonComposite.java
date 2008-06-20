/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs.content;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.neontoolkit.oyster2.client.gui.dialogs.InputDialog;

/**
 * @author David Muñoz
 *
 */
public class ComboWithButtonComposite extends AbstractComboComposite {
	
	private Button button = null;
	
	private InputDialog nestedDialog = null;
	
	public ComboWithButtonComposite(Composite parent, int style,
			String section, String[] predefined,boolean editable,
			String defaultValue) {
		super(parent, style,section,predefined,editable,defaultValue);
				
		
		this.setLayout(new FormLayout());
		FormData fd = null;
		
		button = new Button(this,SWT.PUSH);
		button.setText("Edit");
		fd = new FormData();
		fd.top = new FormAttachment(0,5);
		fd.right = new FormAttachment(100,-5);
		fd.width = InputComposite.buttonWidth;
		fd.bottom = new FormAttachment(100,-5);
		button.setLayoutData(fd);
		
		
		fd = new FormData();
		fd.left = new FormAttachment(0,5);
		fd.top = new FormAttachment(0,5);
		fd.right = new FormAttachment(button,-5);
		fd.bottom = new FormAttachment(100,-5);
		combo.setLayoutData(fd);
		
		setInput(combo.getText());
		makeListeners();
		
	}
	

	private void makeListeners() {
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				if (event.widget == button) {
					int result = nestedDialog.open();
					if (result == TrayDialog.OK) {
						String input = (String)nestedDialog.getInput();
						combo.setText(input);
						
					}
				}
				else if (event.widget == combo) {
					if (event.type == SWT.Selection) {
						setInput(combo.getItem(combo.getSelectionIndex()));
					}
					else if (event.type == SWT.Modify) {
						setInput(combo.getText());
					}
					
				}
			}
		};
		button.addListener(SWT.Selection,listener);
		combo.addListener(SWT.Selection,listener);
		combo.addListener(SWT.Modify, listener);
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

	/**
	 * @return the button
	 */
	public final Button getButton() {
		return button;
	}

	/**
	 * @param button the button to set
	 */
	public final void setButton(Button button) {
		this.button = button;
	}


	
}
