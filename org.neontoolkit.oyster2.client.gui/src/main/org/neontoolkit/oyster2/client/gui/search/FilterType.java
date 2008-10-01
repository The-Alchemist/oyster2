/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.search;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author David Muñoz
 *
 */
public enum FilterType {
	// PersonFilterType is not really a filter. It should be deleted when
	// preparing the GUI for nested querys
	
	StringFilterType("StringFilterType"),DateTimeFilterType("DateTimeFilterType"), //$NON-NLS-1$ //$NON-NLS-2$
	BooleanFilterType("BooleanFilterType"),IntegerFilterType("IntegerFilterType"), //$NON-NLS-1$ //$NON-NLS-2$
	PersonFilterType("PersonFilterType");  //$NON-NLS-1$
	
	private String name = null;
	
	private FilterType(String name) {
		this.name = name;
	}
	private static Map<String, FilterType> lookup;
		
	
	static {
		lookup = new HashMap<String, FilterType>();
        for(FilterType filter : EnumSet.allOf(FilterType.class))
             lookup.put(filter.getName(), filter);
   }
	
	
	public static final String[] StringFilterTypeComparators = {"EQ","Like","NotLike", "NE"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	
	public static final String[] DateTimeFilterTypeComparators = {"EQ","NE","GT","GE","LT","LE"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
	
	public static final String[] IntegerFilterTypeComparators = {"EQ","NE","GT","GE","LT","LE"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
	
	public static final String[] BooleanFilterTypeComparators = {"EQ","NE"}; //$NON-NLS-1$ //$NON-NLS-2$
	
	public static final String COMPARATOR_EQUAL = "EQ"; //$NON-NLS-1$
	
	public static final String COMPARATOR_NOT_EQUAL = "NE"; //$NON-NLS-1$
	
	public static final String COMPARATOR_GREATER_THAN = "GT"; //$NON-NLS-1$
	
	public static final String COMPARATOR_GREATER_OR_EQUAL = "GE"; //$NON-NLS-1$
	
	public static final String COMPARATOR_LESSER_THAN = "LT"; //$NON-NLS-1$
	
	public static final String COMPARATOR_LESSER_OR_EQUAL = "LE"; //$NON-NLS-1$
	
	public static final String COMPARATOR_LIKE = "Like"; //$NON-NLS-1$
	
	public static final String COMPARATOR_NOT_LIKE = "NotLike"; //$NON-NLS-1$
	
	protected static Map<FilterType,String[]> comparators;
	static {
		comparators = new HashMap<FilterType, String[]>();
		comparators.put(StringFilterType, StringFilterTypeComparators);
		comparators.put(DateTimeFilterType, DateTimeFilterTypeComparators);
		comparators.put(IntegerFilterType, IntegerFilterTypeComparators);
		comparators.put(BooleanFilterType, BooleanFilterTypeComparators);
		comparators.put(PersonFilterType, StringFilterTypeComparators);
	}
	
	public static FilterType get(String name) {
		return lookup.get(name);
	}
	
	/**
	 * @return the comparators
	 */
	public static final Map<FilterType, String[]> getComparators() {
		return comparators;
	}
	/**
	 * @param comparators the comparators to set
	 */
	public static final void setComparators(Map<FilterType, String[]> comparators) {
		FilterType.comparators = comparators;
	}
	
	public String getName() {
		return name;
	}
	
	
	
}
