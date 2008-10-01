/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs.content;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * @author David Muñoz
 *
 */
public class ComboComposite extends AbstractComboComposite {


	public ComboComposite(Composite parent, int style, String section,
			String [] predefined,boolean editable,String defaultValue) {
		super(parent, style,section,predefined,editable,defaultValue);
		this.setLayout(new FormLayout());
		
		FormData fd = new FormData();
		fd.top = new FormAttachment(0,5);
		fd.left = new FormAttachment(0,5);
		fd.right = new FormAttachment(100,-5);
		fd.bottom = new FormAttachment(100,-5);
		combo.setLayoutData(fd);
		
		setInput(combo.getText());
		makeListeners();
		
	}
	
	
	private void makeListeners() {
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				if (event.type == SWT.Selection)
					setInput(combo.getItem(combo.getSelectionIndex()));
				else if (event.type == SWT.Modify)  {
					setInput(combo.getText());
				}
			}
		};
		
		combo.addListener(SWT.Selection,listener);
		combo.addListener(SWT.Modify,listener);
		
	}


	
	
	
}
