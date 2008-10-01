/**
 * 
 */
package org.neontoolkit.oyster2.client.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.menus.IWorkbenchWidget;

/**
 * @author David Muñoz
 *
 */
public class ServerComboWorkbenchWidget implements
		IWorkbenchWidget {

	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.menus.IWorkbenchWidget#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow workbenchWindow) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.menus.IWidget#fill(org.eclipse.swt.widgets.Composite)
	 */
	public void fill(Composite parent) {
		new TargetServerComposite(parent,SWT.NONE);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.menus.IWidget#fill(org.eclipse.swt.widgets.Menu, int)
	 */
	public void fill(Menu parent, int index) {
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.menus.IWidget#fill(org.eclipse.swt.widgets.ToolBar, int)
	 */
	public void fill(ToolBar parent, int index) {
		//TargetServerComposite composite = new TargetServerComposite(parent,SWT.NONE);
		Combo combo = new Combo(parent,SWT.DROP_DOWN);
		combo.add("uno");
		ToolItem item = new ToolItem(parent, SWT.SEPARATOR, index);
	    item.setControl(combo);
	    //item.setWidth(combo.computeSize(SWT.D, hHint));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.menus.IWidget#fill(org.eclipse.swt.widgets.CoolBar, int)
	 */
	public void fill(CoolBar parent, int index) {
		new TargetServerComposite(parent,SWT.NONE);
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
