/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.search.composites;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Item;
import org.neontoolkit.oyster2.client.gui.search.FilterValue;
import org.neontoolkit.oyster2.client.gui.search.PersonFilterValue;

/**
 * @author David Muñoz
 *
 */
public class PersonFilterCellModifier implements ICellModifier {

	private String[] comparators = null;
	
	private StructuredViewer viewer = null;
	
	
	public PersonFilterCellModifier(StructuredViewer viewer,
			String[] comparators) {
		this.comparators = comparators;
		this.viewer = viewer;
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
		PersonFilterValue person =(PersonFilterValue)filter.getValue();
		if (property.equals("name"))
			return person.getName();
		else if (property.equals("lastname"))
			return person.getLastname();
		/*else if (property.equals("negate"))
			return filter.getNegate();*/
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
        FilterValue filter = (FilterValue) item.getData();
        PersonFilterValue person =(PersonFilterValue)filter.getValue();
        
		if (property.equals("name"))
			person.setName((String)value);
		else if (property.equals("lastname"))
			person.setLastname((String)value);
		/*else if (property.equals("negate"))
			filter.setNegate((Boolean)value);*/
		else if (property.equals("comparator")) {
			Integer index = (Integer)value;
			filter.setComparator(comparators[index.intValue()]);
		}
		viewer.update(filter,new String [] {property} );
	}

}
