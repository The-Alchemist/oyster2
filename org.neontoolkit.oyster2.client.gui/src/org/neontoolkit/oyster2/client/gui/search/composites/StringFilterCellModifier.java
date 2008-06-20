/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.search.composites;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Item;
import org.neontoolkit.oyster2.client.gui.search.FilterValue;

/**
 * @author David Muñoz
 *
 */
public class StringFilterCellModifier implements ICellModifier {

	private TableViewer viewer = null;
	
	private String[] comparators = null;
	
	public StringFilterCellModifier(TableViewer viewer,
			String []comparators) {
		this.viewer = viewer;
		this.comparators = comparators;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
	 */
	public boolean canModify(Object element, String property) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object, java.lang.String)
	 */
	public Object getValue(Object element, String property) {
		FilterValue filter = (FilterValue)element;
		if (property.equals("search for"))
			return filter.getValue();
		else if (property.equals("negate"))
			return filter.getNegate();
		else if (property.equals("comparator")) {
			String comparator = filter.getComparator();
			for (int i = 0;i<comparators.length;i++) {
				if (comparators[i].equals(comparator))
					return i;
			}
			throw new RuntimeException("Comparator in view not found in model");
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object, java.lang.String, java.lang.Object)
	 */
	public void modify(Object element, String property, Object value) {
		Item item = (Item) element;
		System.out.println("Modify, index " + value.toString());
        FilterValue filter = (FilterValue) item.getData();
		if (property.equals("search for"))
			filter.setValue(value);
		else if (property.equals("negate"))
			filter.setNegate((Boolean)value);
		else if (property.equals("comparator")) {
			Integer index = (Integer)value;
			filter.setComparator(comparators[index.intValue()]);
		}
		viewer.update(filter,new String [] {property} );
	}

}
