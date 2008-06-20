/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.search.composites;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.neontoolkit.oyster2.client.gui.search.FilterValue;

/**
 * @author David Muñoz
 *
 */
public class StringFilterLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 */
	public String getColumnText(Object element, int index) {
		FilterValue filter = (FilterValue)element;
		//depending on name of the column
		switch (index) {
		case 0:
			return filter.getValue().toString();
		case 1:
			return filter.getComparator();
			
		case 2:
			return filter.getNegate().toString();
		default:
			break;
		}
		return null;
	}

}
