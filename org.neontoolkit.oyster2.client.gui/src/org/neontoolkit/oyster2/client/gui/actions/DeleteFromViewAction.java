/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.actions;


import org.eclipse.jface.viewers.IStructuredSelection;
import org.neontoolkit.oyster2.client.gui.adapter.IOMVObject;
import org.neontoolkit.oyster2.client.gui.views.SearchResultsView;

/**
 * @author David Muñoz
 *
 */
public class DeleteFromViewAction extends DeleteAction {

	private SearchResultsView view = null;
	
	@Override
	public void run() {
		getSelectedObjects();
		super.run();
	}

	
	private void getSelectedObjects() {
		IOMVObject omvObject = null;
		String [] ids = null;
		Object [] selectedResults =
			((IStructuredSelection)view.getViewer().getSelection()).toArray();
		
		
		ids = new String[selectedResults.length];
		int i = 0;
		for (;i<ids.length;i++) {
			omvObject = (IOMVObject)selectedResults[i];
			ids[i] = omvObject.getId();
		}
		setObjectIdentifiers(ids);
		
	}


	/**
	 * @return the view
	 */
	public final SearchResultsView getView() {
		return view;
	}


	/**
	 * @param view the view to set
	 */
	public final void setView(SearchResultsView view) {
		this.view = view;
	}
	
	
	
}
