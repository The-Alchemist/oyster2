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
public class UpdateFromViewAction extends UpdateAction {
	private SearchResultsView view = null;
	
	
	@Override
	public void run() {
		getSelectedObject();
		super.run();
	}
	
	private void getSelectedObject() {
		Object [] selectedResults =
			((IStructuredSelection)view.getViewer().getSelection()).toArray();
		setObjectToUpdate((IOMVObject)selectedResults[0]);
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
