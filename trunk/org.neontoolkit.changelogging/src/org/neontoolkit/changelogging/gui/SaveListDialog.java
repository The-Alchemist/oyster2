package org.neontoolkit.changelogging.gui;

import java.util.List;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.semanticweb.kaon2.api.Ontology;

public class SaveListDialog extends ListSelectionDialog {

	public SaveListDialog(Shell parentShell, Object input) {		
		super(parentShell, input, new SCProvider(), new LProvider(), "Save Change Logs");
	}
}

class SCProvider implements IStructuredContentProvider {

	public Object[] getElements(Object inputElement) {
		if(inputElement instanceof List)
			return ((List)inputElement).toArray();
		return null;
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		
	}
	
}

class LProvider implements ILabelProvider {

	public Image getImage(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getText(Object element) {
		if(element instanceof Ontology)
			return ((Ontology)element).getOntologyURI();
		return null;
	}

	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}
	
}