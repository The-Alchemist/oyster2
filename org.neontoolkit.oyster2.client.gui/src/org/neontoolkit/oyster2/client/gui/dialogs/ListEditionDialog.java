/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs;


import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author David Muñoz
 *
 */
public class ListEditionDialog extends ResizableInputDialog {
	
	private Text textLine = null;
	
	private String[] items = null;
	
	private Set<String> added = null;
	
	private Set<String> deleted = null;
	
	private List list = null; 
	
	private Button addButton = null;
	
	private Button removeButton = null;
	
	private Composite controlsComposite = null;
	
	private Composite buttonsComposite = null;
	/**
	 * @param section
	 * @param parentShell
	 */
	public ListEditionDialog(String section, Shell parentShell) {
		super(section, parentShell);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param section
	 * @param parentShell
	 */
	public ListEditionDialog(String section, IShellProvider parentShell) {
		super(section, parentShell);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		
		Composite baseComposite = (Composite) super.createDialogArea(parent);
		
		FormLayout layout = new FormLayout();
		baseComposite.setLayout(layout);
				
		added = new HashSet<String>();
		deleted = new HashSet<String>();

		
		
		makeControls(baseComposite);
		makeButtons(baseComposite);
		makeListeners();
		makeLayout(baseComposite);
		
		return baseComposite;
	}

	private void makeControls(Composite baseComposite) {
		FormLayout layout = new FormLayout();
		controlsComposite =
			new Composite(baseComposite,SWT.NONE);
		controlsComposite.setLayout(layout);
		FormData fd = null;
		
		list = new List(controlsComposite,SWT.BORDER);
		if (items != null)
			list.setItems(items);
		
		textLine = new Text(controlsComposite,SWT.BORDER);
		
		fd = new FormData();
		fd.top = new FormAttachment(0,0);
		fd.left = new FormAttachment(0,5);
		fd.right = new FormAttachment(100,-5);
		textLine.setLayoutData(fd);
		
		fd = new FormData();
		fd.top = new FormAttachment(textLine,5);
		fd.left = new FormAttachment(0,5);
		fd.right = new FormAttachment(100,-5);
		fd.bottom = new FormAttachment(100,-5);
		list.setLayoutData(fd);
		
		
	}

	private void makeListeners() {
		Listener buttonsListener = new Listener() {
			public void handleEvent(Event event) {
				if (event.widget == addButton) {
					addElement();
				}
				else if (event.widget == removeButton) {
					removeElement();
				}
			}
	
		};
		addButton.addListener(SWT.Selection, buttonsListener);
		removeButton.addListener(SWT.Selection, buttonsListener);
	}

	private void removeElement() {
		String []items = list.getSelection();
		for (String item : items) {
			added.remove(item);
			deleted.add(item);
		}

		list.remove(list.getSelectionIndices());
	}
	
	private void addElement() {
		String text = textLine.getText().trim();
		
		if (text.equals("")) //$NON-NLS-1$
			return;
		
		if (deleted.contains(text)) {
			deleted.remove(text);
		}
		
		added.add(text);
		
		list.add(text);
		
	}
	
	
	@Override
	protected void okPressed() {
		this.items = list.getItems();
		super.okPressed();
	}
	
	private Composite makeButtons(Composite baseComposite) {
		buttonsComposite = new Composite(baseComposite,SWT.NONE);
		//	new Composite(baseComposite,SWT.NONE);
		/*TableWrapLayout layout = new TableWrapLayout();
		layout.makeColumnsEqualWidth = true;
		layout.numColumns = 1;
		buttonsComposite.setLayout(layout);
		*/
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = true;
		layout.numColumns = 1;
		layout.marginHeight = 0;
		buttonsComposite.setLayout(layout);
		GridData gd = null;
		
		
		gd = new GridData();
		
		addButton = new Button(buttonsComposite,SWT.PUSH);
		addButton.setText(Messages.getString("ListEditionDialog.listEditionDialog.addbutton.add")); //$NON-NLS-1$
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = GridData.FILL;
		addButton.setLayoutData(gd);
		
		gd = new GridData();
		removeButton = new Button(buttonsComposite,SWT.PUSH);
		removeButton.setText(Messages.getString("ListEditionDialog.listEditionDialog.removebutton.remove")); //$NON-NLS-1$
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		removeButton.setLayoutData(gd);
		return buttonsComposite;
	}

	private void makeLayout(Composite baseComposite) {
		
		FormData fd = null;
		
		
		//buttons composite
		fd = new FormData();
		fd.top = new FormAttachment(5,5);
		fd.right = new FormAttachment(100,-5);
		buttonsComposite.setLayoutData(fd);
		
		
		//controls composite
		fd = new FormData();
		fd.top = new FormAttachment(5,5);
		fd.left = new FormAttachment(0,5);
		fd.right = new FormAttachment(buttonsComposite,-5);
		fd.bottom = new FormAttachment(100,-5);
		controlsComposite.setLayoutData(fd);
		
		//align the text line with the add button
		fd = (FormData)textLine.getLayoutData();
		GridData gd = (GridData)addButton.getLayoutData();
		int textHeight = textLine.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		int buttonHeight = addButton.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		/*
		if (buttonHeight > textHeight) {
			fd.height = buttonHeight;
			textLine.setLayoutData(fd);
		}
		else {
			gd.heightHint = textHeight;
			addButton.setLayoutData(gd);
			gd = (GridData)removeButton.getLayoutData();
			gd.heightHint = textHeight;
			removeButton.setLayoutData(gd);
		}
		baseComposite.layout(true);
		*/
	}

	public String[] getItems() {
		return items;
	}

	public void setItems(String[] items) {
		this.items = items;
	}

	public Set<String> getAdded() {
		return added;
	}

	public void setAdded(Set<String> added) {
		this.added = added;
	}

	public Set<String> getDeleted() {
		return deleted;
	}

	public void setDeleted(Set<String> deleted) {
		this.deleted = deleted;
	}
	
	
	
}
