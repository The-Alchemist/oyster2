/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.neontoolkit.oyster2.client.gui.Oyster2;
import org.neontoolkit.registry.ui.provider.OntologyContentProvider;
import org.neontoolkit.registry.ui.provider.OntologyLabelProvider;
import org.semanticweb.kaon2.on;
import org.semanticweb.kaon2.api.Entity;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Ontology;

/**
 * @author David Muñoz
 *
 */
public class DomainTreeComposite extends InputComposite {

	
	private TreeViewer topicViewer = null;
		
	/**
	 * @param parent
	 * @param style
	 * @param section
	 * @param predefined
	 */
	public DomainTreeComposite(Composite parent, int style, String section) {
		super(parent, SWT.NONE, section, null);
		/*FormData fd = null;		
		FormLayout layout = new FormLayout();
		this.setLayout(layout);*/
		GridLayout layout = new GridLayout();
		this.setLayout(layout);
		
		
		/*
		Label label = new Label(this,SWT.NONE);
		fd = new FormData();
		fd.top = new FormAttachment(0,0);
		fd.left = new FormAttachment(0,5);
		label.setLayoutData(fd);
		*/
		Composite treeComposite = new Composite(this,SWT.NONE);
		GridData gd = makeGridForControl(treeComposite,6);
		makeTopicsViewer(treeComposite);
		/*fd = new FormData();
		fd.top = new FormAttachment(0,5);
		fd.left = new FormAttachment(0,5);
		fd.right = new FormAttachment(100,-5);
		fd.bottom = new FormAttachment(100,-5);*/
		treeComposite.setLayout(new FillLayout());
		treeComposite.setLayoutData(gd);
	}

	

	private TreeViewer makeTopicsViewer(Composite baseComposite) {
		
		//excepcion aqui
		Ontology topicOntology = Oyster2.getSharedInstance().getTopicOntology();
		Entity topicRoot = Oyster2.getSharedInstance().getTopicOntologyRoot();
		
		topicViewer = new TreeViewer(baseComposite, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER| SWT.FULL_SELECTION);
		topicViewer.setContentProvider(new OntologyContentProvider(
				 topicOntology,false,topicRoot));
		topicViewer.setLabelProvider(new OntologyLabelProvider());
		topicViewer.setInput(topicOntology);
		topicViewer.expandToLevel(2);
		makeListener();
		return topicViewer;
	}
	
	
	private void makeListener() {
		ISelectionChangedListener selectionListener =
			new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection selection = (StructuredSelection)event.getSelection();
				String uri = null;
				String label = null;
				Entity topicEntry = null;
				//ILabelProvider labelProvider = (ILabelProvider)topicViewer.getLabelProvider();
				/*HashMap<String,String> values = new HashMap<String,String>();
				
				Iterator it = selection.iterator();
				
				while (it.hasNext()) {
					topicEntry = (Entity)it.next();
					label = labelProvider.getText(topicEntry);
					uri = topicEntry.getURI();
					values.put(label,uri);
				}
				
				setInput(values);
				return values;*/
				
				Iterator it = selection.iterator();
				String values[] = new String[selection.size()];
				int i = 0;
				while (it.hasNext()) {
					topicEntry = (Entity)it.next();
					
					uri = topicEntry.getURI();
					values[i++] = uri;
				}
				setInput(values);
			}
		};
		topicViewer.addSelectionChangedListener(selectionListener);
	}
	
	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.dialogs.content.InputComposite#testFilled()
	 */
	@Override
	public boolean testFilled() {
		if (isRequired()) {
			StructuredSelection selection =
				(StructuredSelection) topicViewer.getSelection();
			return ! selection.isEmpty();
		}
		return true;
	}
	
	@Override
	public Object getInput() {
		
		return input;
	}

	@Override
	public void setInitialValue(Object value) {
		ArrayList<String> initialValues = (ArrayList<String>)value;
		//Ontology topicOntology = Oyster2.getSharedInstance().getTopicOntology();
		Entity entry = null;
		Entity[] topicList = new Entity[initialValues.size()];
		int i = 0;
		for (String topic : initialValues) {
			entry = KAON2Manager.factory().owlClass(topic);
			topicList[i++] = entry;
		}
		StructuredSelection selection = 
			new StructuredSelection(topicList);
		topicViewer.setSelection(selection);
	}
	
}
