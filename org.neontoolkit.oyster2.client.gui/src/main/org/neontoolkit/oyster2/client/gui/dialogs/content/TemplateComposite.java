/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs.content;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.neontoolkit.oyster2.client.gui.util.Template;

/**
 * @author david
 *
 */
public class TemplateComposite extends Composite {

	private Template template = null;
	
	private Combo templateCombo = null;
	
	private Label label = null;
	
	public TemplateComposite(Composite parent, int style,Template template) {
		super(parent, style);
		this.template = template;
		label = new Label(this,SWT.NONE);
		templateCombo = new Combo(this,SWT.DROP_DOWN);
		String[] templateNames = new String[0];
		templateNames = template.getTemplateNames().toArray(templateNames);
		templateCombo.setItems(templateNames);
		String defaultSelection = template.getDefaultTemplate();
		if (defaultSelection != null) {
			for (int i = 0; i<templateNames.length;i++) {
				if (templateCombo.getItem(i).equals(defaultSelection)) {
					templateCombo.select(i);
					break;
				}
			}
		}
		makeLayout();
	}

	private void makeLayout() {
		FormLayout layout = new FormLayout();
		this.setLayout(layout);
		
		
		FormData fd = new FormData();
		fd.left = new FormAttachment(0,5);
		fd.top = new FormAttachment(0,5);
		label.setLayoutData(fd);
		
		fd = new FormData();
		fd.left = new FormAttachment(label,5);
		fd.top = new FormAttachment(0,5);
		fd.right = new FormAttachment(100,-5);
		templateCombo.setLayoutData(fd);
		
		
	}

	public void addTemplateListener(Listener listener) {
		templateCombo.addListener(SWT.Selection, listener);
	}
	
	public void removeTemplateListener(Listener listener) {
		templateCombo.removeListener(SWT.Selection, listener);
	}
	
	public List<String> getCurrentTemplate() {
		String selected = templateCombo.getItem(templateCombo.getSelectionIndex());
		return template.getTemplateProperties(selected);
	}
	
	public String[] getTemplateNames() {
		return templateCombo.getItems();
	}
	
	
}
