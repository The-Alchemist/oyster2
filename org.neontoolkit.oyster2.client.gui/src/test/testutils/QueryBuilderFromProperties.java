/**
 * 
 */
package testutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.neontoolkit.oyster2.client.core.querymanager.QueryPortAxis2Adapter;
import org.neontoolkit.oyster2.client.gui.search.Filter;
import org.neontoolkit.oyster2.client.gui.search.FilterType;
import org.neontoolkit.oyster2.client.gui.search.FilterValue;
import org.neontoolkit.oyster2.client.gui.search.QueryBuilderAdapter;

/**
 * @author David Muñoz
 *
 */
public class QueryBuilderFromProperties {
	
	
private final static String TEST_ATTRIBUTES_SUFFIX = ".attributes";
	
	public final static String TEST_CLASS_SUFFIX = ".class";
	
	private final static String ATTR_TYPE_SUFFIX = ".attributeType";
	
	private final static String FILTER_VALUES_SUFFIX = ".filterValues";
	
	private final static String ATTR_MULTIVALUED_SUFFIX = ".multivalued";
	
	private final static String ATTR_COMPARATOR_SUFFIX = ".comparator";
	
	
	public static Map<String,Object> 
		getFilters(String test, PropertiesConfiguration configuration) {

		Map<String,Object> inputFilters = new HashMap<String, Object>();
		String javaClass = configuration.getString(test + TEST_CLASS_SUFFIX);
		String []testAttributes = 
			configuration.getStringArray(test + TEST_ATTRIBUTES_SUFFIX);
		
		Filter filter = null;
		
		String attributeType = null; //Boolean, Date, String, Integer
		
		String [] filterValuesStringList = null;
		
		String comparator = null;
		Boolean multivalued = false;
		
		for (String attribute : testAttributes) {
			
			attributeType = configuration.getString(test + "." + attribute + ATTR_TYPE_SUFFIX);
			
			filterValuesStringList = configuration.getStringArray(test + "." + 
					attribute + FILTER_VALUES_SUFFIX);
			multivalued = configuration.getBoolean(test + "." + 
					attribute + ATTR_MULTIVALUED_SUFFIX);
			comparator = configuration.getString(test + "." + 
					attribute + ATTR_COMPARATOR_SUFFIX);
			filter = makeFilter(attributeType,filterValuesStringList,
					multivalued,comparator);
			inputFilters.put(attribute,filter);
		}
		Map<String,Object> builtFilters = 
			QueryBuilderAdapter.getFilters(javaClass, inputFilters);
		return builtFilters;
		
	}

	private static Filter makeFilter(String attributeType,
			String[] filterValuesStringList,Boolean multivalued,String comparator) {
		FilterType filterType = null;
		FilterValue filterValue = null;
		ArrayList<FilterValue> filterValues = null;
		
		Filter filter = new Filter();
		filter.setMulti(multivalued);
		
		//get filter type
		filterType = FilterType.get(attributeType);
		filter.setType(filterType);
		//filter value type 
		
		filterValues = new ArrayList<FilterValue>();
		//FilterValue[filterValuesStringList.length];
		
		for (String thisValue : filterValuesStringList) {
			filterValue = new FilterValue();
			filterValue.setNegate(false);
			filterValue.setComparator(comparator);
			filterValue.setValue(thisValue);
			filterValues.add(filterValue);
		}
		
		filter.setFilterValues(filterValues);
		return filter;
	}
}
