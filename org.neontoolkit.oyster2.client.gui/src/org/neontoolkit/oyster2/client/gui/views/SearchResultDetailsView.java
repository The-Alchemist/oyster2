/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.neontoolkit.oyster2.client.gui.adapter.results.ISearchResult;
import org.neontoolkit.oyster2.client.gui.results.details.DetailsComposite;

/**
 * @author david
 *
 */
public class SearchResultDetailsView extends ViewPart {

	Composite parent = null;
	ScrolledComposite child = null;
	

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		parent.setLayout(new FillLayout());
	}

	public void setDetails(ISearchResult result) {
		if (child != null)
			child.dispose();
		child = new ScrolledComposite(parent,SWT.H_SCROLL | SWT.V_SCROLL);
		child.setLayout(new FillLayout());
		DetailsComposite composite = new DetailsComposite(child,SWT.NONE);
		child.setContent(composite);
		child.setExpandHorizontal(true);
		child.setBackground(composite.getBackground());
		composite.setEntry(result);
		composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		//child = composite;
		parent.layout(false);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
