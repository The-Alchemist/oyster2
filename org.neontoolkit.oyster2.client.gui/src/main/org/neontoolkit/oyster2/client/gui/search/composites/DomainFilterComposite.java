/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.search.composites;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.neontoolkit.oyster2.client.gui.dialogs.content.DomainTreeComposite;
import org.neontoolkit.oyster2.client.gui.search.Filter;
import org.neontoolkit.oyster2.client.gui.search.FilterType;
import org.neontoolkit.oyster2.client.gui.search.FilterValue;

/**
 * @author David Muñoz
 *
 */
public class DomainFilterComposite extends FilterComposite {

	private DomainTreeComposite composite = null;
	
	public DomainFilterComposite(Composite parent, int style, String section,String[]predefined) {
		
		super(parent, style, section, null);
		
		this.setLayout(new FillLayout());
		composite = new DomainTreeComposite(this,style,section);
	}

	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.search.composites.FilterComposite#getFilterData()
	 */
	@Override
	public Filter getFilterData() {
		HashMap<String,String>  topics = 
			(HashMap<String,String>)composite.getInput();
		if (topics.size() == 0)
			return null;
		Filter filter = new Filter();
		filter.setMulti(true);
		filter.setType(FilterType.StringFilterType);
		ArrayList<FilterValue> filterValues = new ArrayList<FilterValue>();
		FilterValue filterValue = null;
		for (String topic : topics.values()) {
			filterValue = new FilterValue();
			filterValue.setComparator("Like"); //$NON-NLS-1$
			filterValue.setNegate(false);
			filterValue.setValue(topic);
			filterValues.add(filterValue);
		}
		
		filter.setFilterValues(filterValues);
		
		
		return filter;
	}

	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.search.composites.FilterComposite#loadSettings()
	 */
	@Override
	public void loadSettings() {
		// doesn't need to do anything here... we will always have
		// a provided list of values

	}

}
