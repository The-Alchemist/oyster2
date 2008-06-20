/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.search;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.Map.Entry;

import org.neon_toolkit.registry.omv.xsd.query.OntologyQueryType;
import org.neontoolkit.oyster2.client.core.querymanager.querybuilders.QueryObjectBuildersRepository;
import org.neontoolkit.oyster2.client.gui.GUIConstants;
import org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.ComparatorType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.DateTimeFilterType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.FilterType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType;


/**
 * Temporary class to be used until there is support for nested queries in
 * the interface. It shouldn't access the QueryObjectBuildersRepository
 * @author David Muñoz
 *
 */
public class QueryBuilderAdapter {
	
	private static final SimpleDateFormat dateFormat 
	= new SimpleDateFormat(GUIConstants.DATE_FORMAT);
	
	
	private static Map<String,ComparatorType> comparatorMappings = null;
	
	static {
		comparatorMappings = new HashMap<String, ComparatorType>();
		comparatorMappings.put("EQ",ComparatorType.EQ);
		comparatorMappings.put("NE",ComparatorType.NE);
		comparatorMappings.put("GE",ComparatorType.GE);
		comparatorMappings.put("GT",ComparatorType.GT);
		comparatorMappings.put("LE",ComparatorType.LE);
		comparatorMappings.put("Like",ComparatorType.Like);
		comparatorMappings.put("LT",ComparatorType.LT);
		comparatorMappings.put("NotLike",ComparatorType.NotLike);
	}
	
	
	/**
	 * The key in the filterValues Map references a setter method name of the
	 * class to be used to build the query. Values are of type Filter (of this package)
	 * @param queryTargetClass
	 * @param filterValues
	 * @return
	 */
	public static Map<String,Object> getFilters(String queryTargetClass,
			Map<String,Object> filterValues) {
		/*
		 * We will receive for now a Map whose values are always filters,
		 * but we specify a Map<String,Object> because someday we
		 * expect to receive nested queries
		 */
		Map<String,Object> filters = new HashMap<String, Object>();
		String methodName = null;
		
		Boolean isBranch = null;
		
		FilterType[] filterBranch = null;
		List<Map<String,Object>> nestedQueryFilters = null;
		FilterType simpleFilter = null;

		for (Entry<String,Object> entry : filterValues.entrySet()) {
			methodName = entry.getKey();
			Filter value = (Filter)entry.getValue();
			if (value.getMulti()) {
				isBranch = QueryObjectBuildersRepository.isBranch(queryTargetClass,
						methodName);
				if (isBranch) {
					try {
						filterBranch = makeBranch(value);
					}
					catch (Exception e) {
						
						e.printStackTrace();
						throw new RuntimeException(e);
					}
					filters.put(entry.getKey(),filterBranch);
				}
				else {
					//nested query
					//for now we have three cases:
					// 1. - if it's an ontology, create a nested query and use
					//		the value as URI
					// 2. - if it's a person, create a nested query with specified
					// 		name and lastname
					// 3. -	create a name branch in the nested query
					nestedQueryFilters = makeNestedQuery(entry.getKey(),value);
					filters.put(entry.getKey(),nestedQueryFilters);
				}
			}
			else {
				//simple filter
				try {
					simpleFilter = makeFilter(value);
					filters.put(entry.getKey(),simpleFilter);
				} catch (ParseException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				
			}
		}
		
		return filters;
	}

	private static FilterType makeFilter(Filter filter) throws ParseException{
		ComparatorType comparator = null;
		FilterType builtFilter = null;
		FilterValue filterValue = null;
		switch ((filter.getType())) {
		case BooleanFilterType:
			BooleanFilterType booleanFilter = null;
			filterValue = filter.getFilterValues().get(0);
				booleanFilter = new BooleanFilterType();
				comparator = comparatorMappings.get(filterValue.getComparator());
				booleanFilter.setComparator(comparator);
				booleanFilter.setValue(Boolean.valueOf(filterValue.getValue().toString()));
				booleanFilter.setNegate(filterValue.getNegate());
				builtFilter = booleanFilter;
			
			break;
		case StringFilterType:
			StringFilterType stringFilter = null;
			filterValue = filter.getFilterValues().get(0);
				stringFilter = new StringFilterType();
				comparator = comparatorMappings.get(filterValue.getComparator());
				stringFilter.setComparator(comparator);
				stringFilter.setValue(filterValue.getValue().toString());
				stringFilter.setNegate(filterValue.getNegate());
				builtFilter = stringFilter;
			
			break;
		case DateTimeFilterType:
			DateTimeFilterType dateTimeFilter = null;
			filterValue = filter.getFilterValues().get(0);
				dateTimeFilter = new DateTimeFilterType();
				comparator = comparatorMappings.get(filterValue.getComparator());
				dateTimeFilter.setComparator(comparator);
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeZone(new SimpleTimeZone(SimpleTimeZone.STANDARD_TIME,"null"));
		        calendar.setTime(dateFormat.parse((filterValue.getValue().toString())));
				dateTimeFilter.setValue(calendar);
				dateTimeFilter.setNegate(filterValue.getNegate());
				builtFilter = dateTimeFilter;
			
			break;
		case IntegerFilterType:
			IntegerFilterType integerFilter = new IntegerFilterType();
			filterValue = filter.getFilterValues().get(0);
				
				comparator = comparatorMappings.get(filterValue.getComparator());
				integerFilter.setComparator(comparator);
				
				integerFilter.setValue(new BigInteger((String)filterValue.getValue()));
				
				builtFilter = integerFilter;
			
			break;
		default:
			System.out.println("Filter type currently not supported");
			break;
		}
		return builtFilter;
	}

	private static List<Map<String,Object>> makeNestedQuery(String methodName,Filter filter) {
		if (filter.getType().equals(
				org.neontoolkit.oyster2.client.gui.search.FilterType.PersonFilterType)) {
			return createPersonQuery(filter);
		}
		
		Method targetMethod = QueryObjectBuildersRepository.
		getMethod(OntologyQueryType.class.getName(),methodName);
		
		Class argClass = targetMethod.getParameterTypes()[0];
		String argClassName = argClass.getName().substring(2,argClass.getName().length()-1);
		
		if (argClassName.equals(OntologyQueryType.class.getName())) {
			return createOntologyQuery(filter);
		}
		
		//just now, only the name branch is used
		FilterType[] filterBranch = null;
		List<Map<String,Object>> list = new LinkedList<Map<String,Object>>();
		Map<String,Object> nestedQuery = new HashMap<String, Object>();
		
		try {
			filterBranch = makeBranch(filter);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		nestedQuery.put("setNameBranch",filterBranch);
		list.add(nestedQuery);
		return list;
	}

	private static List<Map<String, Object>> createOntologyQuery(Filter filter) {
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		Map<String,Object> nestedQueryFilters = null;
		String ontURI  = null;
		StringFilterType stringFilter = null;
		//new StringFilterType();
		ComparatorType comparator  = null;
		
		for (FilterValue value : filter.getFilterValues()) {
			ontURI = value.getValue().toString();
			//again, another ugly hack until we support nested queries
			nestedQueryFilters = new HashMap<String, Object>();
			comparator = comparatorMappings.get(value.getComparator());
			stringFilter = new StringFilterType();
			stringFilter.setComparator(comparator);
			stringFilter.setValue(ontURI);
			stringFilter.setNegate(value.getNegate());
			nestedQueryFilters.put("setURIFilter",stringFilter);
			
			list.add(nestedQueryFilters);
		}
		
		return list;
	}
	
	
	private static List<Map<String, Object>> createPersonQuery(Filter filter) {
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		Map<String,Object> nestedQueryFilters = null;
		PersonFilterValue person  = null;
		StringFilterType stringFilter = null;
		//new StringFilterType();
		ComparatorType comparator  = null;
		// the server does not currently support a nested query with multiple filters,
		// so we add nested queries
		for (FilterValue value : filter.getFilterValues()) {
			person = (PersonFilterValue)value.getValue();
			//again, another hack until we fully support nested queries
			
			comparator = comparatorMappings.get(value.getComparator());
			if (person.getName() != null) {
				if (!person.getName().trim().equals("")) {
					nestedQueryFilters = new HashMap<String, Object>();
					stringFilter = new StringFilterType();
					stringFilter.setComparator(comparator);
					stringFilter.setValue(person.getName());
					stringFilter.setNegate(value.getNegate());
					stringFilter.setDomainAttribute("name");
					nestedQueryFilters.put("setPersonNameFilter",stringFilter);
					list.add(nestedQueryFilters);
				}
			}
			if (person.getLastname() != null) {
				if (! person.getLastname().trim().equals("")) {
					nestedQueryFilters = new HashMap<String, Object>();
					stringFilter = new StringFilterType();
					stringFilter.setComparator(comparator);
					stringFilter.setValue(person.getLastname());
					stringFilter.setNegate(value.getNegate());
					stringFilter.setDomainAttribute("lastName");
					//BUG
					nestedQueryFilters.put("setPersonNameFilter",stringFilter);
					list.add(nestedQueryFilters);
				}
				
			}
			
			
		}
		
		return list;
	}

	private static FilterType[] makeBranch(Filter filterBranch) throws ParseException {
		int size = filterBranch.getFilterValues().size();
		FilterType[] branch = new FilterType[size];
		ComparatorType comparator = null;
		int i = 0;
		switch ((filterBranch.getType())) {
		case BooleanFilterType:
			BooleanFilterType booleanFilter = null;
			for (FilterValue filterValue : filterBranch.getFilterValues()) {
				booleanFilter = new BooleanFilterType();
				comparator = comparatorMappings.get(filterValue.getComparator());
				booleanFilter.setComparator(comparator);
				booleanFilter.setValue(Boolean.valueOf(filterValue.getValue().toString()));
				booleanFilter.setNegate(filterValue.getNegate());
				
				branch[i++] = booleanFilter;
			}
			break;
		case StringFilterType:
			StringFilterType stringFilter = null;
			for (FilterValue filterValue : filterBranch.getFilterValues()) {
				stringFilter = new StringFilterType();
				comparator = comparatorMappings.get(filterValue.getComparator());
				stringFilter.setComparator(comparator);
				stringFilter.setValue(filterValue.getValue().toString());
				stringFilter.setNegate(filterValue.getNegate());
				stringFilter.setDomainAttribute("value");
				branch[i++] = stringFilter;
			}
			break;
		case DateTimeFilterType:
			DateTimeFilterType dateTimeFilter = null;
			for (FilterValue filterValue : filterBranch.getFilterValues()) {
				dateTimeFilter = new DateTimeFilterType();
				comparator = comparatorMappings.get(filterValue.getComparator());
				dateTimeFilter.setComparator(comparator);
				Calendar calendar = Calendar.getInstance();
		        calendar.setTime(dateFormat.parse((filterValue.getValue().toString())));
				dateTimeFilter.setValue(calendar);
				dateTimeFilter.setNegate(filterValue.getNegate());
				branch[i++] = dateTimeFilter;
			}
			break;
		default:
			System.out.println("Branch type currently not supported");
			break;
		}
		return branch;
	}
	
	
}
