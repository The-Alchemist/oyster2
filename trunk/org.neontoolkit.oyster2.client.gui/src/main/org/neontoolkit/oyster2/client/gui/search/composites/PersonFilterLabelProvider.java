/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.search.composites;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.neontoolkit.oyster2.client.gui.search.FilterValue;
import org.neontoolkit.oyster2.client.gui.search.PersonFilterValue;

/**
 * @author David Muñoz
 *
 */
public class PersonFilterLabelProvider extends LabelProvider implements
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
	public String getColumnText(Object element, int columnIndex) {
		FilterValue filter = (FilterValue)element;
		//depending on name of the column
		PersonFilterValue person = null;
		switch (columnIndex) {
		case 0:
			person =(PersonFilterValue)filter.getValue();
			return person.getName();
		case 1:
			person =(PersonFilterValue)filter.getValue();
			return person.getLastname();
		case 2:
			return filter.getComparator();
		case 3:
			return filter.getNegate().toString();
		default:
			break;
		}
		return null;
	}

}
