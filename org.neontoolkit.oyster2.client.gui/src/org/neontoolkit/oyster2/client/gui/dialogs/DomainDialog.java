/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.neon_toolkit.registry.ui.provider.OntologyContentProvider;
import org.neon_toolkit.registry.ui.provider.OntologyLabelProvider;
import org.neontoolkit.oyster2.client.gui.Oyster2;
import org.semanticweb.kaon2.api.Entity;
import org.semanticweb.kaon2.api.Ontology;

/**
 * @author David Muñoz
 *
 */
public class DomainDialog extends ResizableInputDialog {

	
	private TreeViewer topicViewer = null;
	
	public DomainDialog(String section, IShellProvider parentShell) {
		super(section, parentShell);
	}
	public DomainDialog(String section, Shell parentShell) {
		super(section, parentShell);
	}
	
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite baseComposite = (Composite)super.createDialogArea(parent);
		
		FormData fd = null;		
		FormLayout layout = new FormLayout();
		baseComposite.setLayout(layout);
		
		Label label = new Label(baseComposite,SWT.NONE);
		fd = new FormData();
		fd.top = new FormAttachment(0,5);
		fd.left = new FormAttachment(0,5);
		label.setLayoutData(fd);
		
		Composite treeComposite = new Composite(baseComposite,SWT.NONE);
		makeTopicsViewer(treeComposite);
		fd = new FormData();
		fd.top = new FormAttachment(label,5);
		fd.left = new FormAttachment(0,5);
		fd.right = new FormAttachment(100,-5);
		fd.bottom = new FormAttachment(100,-5);
		treeComposite.setLayout(new FillLayout());
		treeComposite.setLayoutData(fd);
		return baseComposite;
	}
	
	private TreeViewer makeTopicsViewer(Composite baseComposite) {
		
		Ontology topicOntology = Oyster2.getSharedInstance().getTopicOntology();
		Entity topicRoot = Oyster2.getSharedInstance().getTopicOntologyRoot();
		System.out.println("Topic root: " + topicRoot.getURI());
		topicViewer = new TreeViewer(baseComposite, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER| SWT.FULL_SELECTION);
		topicViewer.setContentProvider(new OntologyContentProvider(
				 topicOntology,false,topicRoot));
		topicViewer.setLabelProvider(new OntologyLabelProvider());
		topicViewer.setInput(topicOntology);
		topicViewer.expandToLevel(2);
		return topicViewer;
	}
	
	@Override
	protected void okPressed() {
		StructuredSelection selection = (StructuredSelection) topicViewer.getSelection();
		HashMap<String,String> values = new HashMap<String,String>();
		String uri = null;
		String label = null;
		Entity topicEntry = null;
		ILabelProvider labelProvider = (ILabelProvider)topicViewer.getLabelProvider();
		Iterator it = selection.iterator();
		
		while (it.hasNext()) {
			topicEntry = (Entity)it.next();
			label = labelProvider.getText(topicEntry);
			uri = topicEntry.getURI();
			values.put(label,uri);
			
		}
		/*if (obj != null){
			= (Entity)obj;
			//Condition condition = new Condition(Constants.omvCondition+Constants.URI, topicEntry.getURI(), Constants.omvCondition+Constants.hasDomain);
		}*/
		setInput(values);
		super.okPressed();
	}
}
