/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs;

import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

/**
 * @author David Muñoz
 *
 */
public class SimpleListSelectionDialog extends ResizableInputDialog {
	
	
	private List list = null;

	private String []predefined = null;
	
	public SimpleListSelectionDialog(String section,IShellProvider parentShell, String[] predefined) {
		super(section,parentShell);
		this.predefined = predefined;
	}
	
	public SimpleListSelectionDialog(String section,Shell shell, String[] predefined) {
		super(section,shell);
		this.predefined = predefined;
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite baseComposite = (Composite)super.createDialogArea(parent);
		
		baseComposite.setLayout(new FormLayout());
		FormData fd = null;
		Label label = null;
		label = new Label(baseComposite,SWT.NONE);
		label.setText("Choose elements to be added");
		fd = new FormData();
		fd.top = new FormAttachment(0,5);
		fd.left = new FormAttachment(0,5);
		label.setLayoutData(fd);
		
		list = new List(baseComposite,SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL|
				SWT.MULTI);
		fd = new FormData();
		fd.top = new FormAttachment(label,5);
		fd.left = new FormAttachment(0,5);
		fd.right = new FormAttachment(100,-5);
		fd.bottom = new FormAttachment(100,-5);
		list.setLayoutData(fd);
		String [] items  = null;
		if (predefined != null) {
			if (predefined.length == 0) {
				//items = rl.getArray(getCustomSettingsSection(),ITEMS_KEY);
				items = dialogSettings.getArray(ITEMS_KEY);
			}
			else {
				items = predefined;				
			}
		}
		else {
			items = dialogSettings.getArray(ITEMS_KEY);
		}
		if (items != null) {
			//items may be null when there aren't predefined values nor previous sessions
			list.setItems(items);
		}
		return baseComposite;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		setInput(list.getSelection());
		super.okPressed();
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
