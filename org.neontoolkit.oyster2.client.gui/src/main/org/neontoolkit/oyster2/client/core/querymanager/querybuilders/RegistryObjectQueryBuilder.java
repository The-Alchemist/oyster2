/**
 * 
 */
package org.neontoolkit.oyster2.client.core.querymanager.querybuilders;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;


import org.oasis.names.tc.ebxml_regrep.xsd.query.BranchType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.FilterType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.RegistryObjectQueryType;

/**
 * @author David Muñoz
 *
 */
public class RegistryObjectQueryBuilder {

	private static final RegistryObjectQueryBuilder instance =
		new RegistryObjectQueryBuilder();
	
	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.core.querymanager.RegistryObjectQueryBuilder#buildQuery(java.util.Map)
	 */
	/**
	 * Builds an object of the specified class queryType, which must be
	 * subclass of {@link RegistryObjectQueryType}, using the filters and nested
	 * {@link Map} objects contained in the argument called filters.
	 * The keys of the {@link Map} must be the name of the method intended to
	 * recieve the value. Such values must be one of the following:
	 * <ul>
	 * <li>A {@link FilterType} when the method must receive a single filter, subclass
	 * of this class.</li>
	 * <li>A {@link Map} when the method must receive a subclass of {@link RegistryObjectQueryType},
	 * to be able to build a nested query. The class argument (named queryType) to build the
	 * nested query is inferred from the method signature.</li>
	 * <li>An array of {@link FilterType} when the method expects a subclass
	 * of {@link BranchType}</li>
	 * </ul>
	 * @param queryType
	 * @param filters
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public RegistryObjectQueryType buildQuery(Class queryType,Map<String, Object> filters)
	throws InstantiationException, IllegalAccessException {
		
		RegistryObjectQueryType queryObject = 
				(RegistryObjectQueryType) queryType.newInstance();
		
		//fillRegistryObjectQueryTypeAttributes(queryObject,filters);
		Object filterValue = null;
		for (String attributeName : filters.keySet()) {
			filterValue = filters.get(attributeName);
			
			if (filterValue instanceof List) {
				putFilter(queryObject,attributeName,
						(List<Map<String,Object>>)filterValue);
			}
			else if (filterValue instanceof FilterType) {
				putFilter(queryObject, attributeName,(FilterType) filterValue);
			}
			else if (filterValue instanceof FilterType[]) {
				putFilter(queryObject, attributeName,(FilterType[]) filterValue);
			}
			
		}
		
		return queryObject;
	}
	
	private void putFilter(RegistryObjectQueryType queryObject,
			String attributeName,FilterType[] filter) {
		if (filter.length == 0)
			return;
		Method method = QueryObjectBuildersRepository.getMethod(
				queryObject.getClass().getName(),attributeName);
		InternationalStringBranchType branch = new InternationalStringBranchType();
		
		branch.setLocalizedStringFilter(filter);
		try {
			method.invoke(queryObject, branch);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void putFilter(RegistryObjectQueryType queryObject,
			String attributeName,FilterType filter) {
		//TODO fix?
		Method method = QueryObjectBuildersRepository
			.getMethod(queryObject.getClass().getName(),attributeName);
		try {
			method.invoke(queryObject, filter);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private void putFilter(RegistryObjectQueryType queryObject,
			String attributeName,List<Map<String,Object>> filter) {
		Method method = QueryObjectBuildersRepository
			.getMethod(queryObject.getClass().getName(),attributeName);
		// the class is an array of a RegistryObjectQuery subtype
		String argClassName = method.getParameterTypes()[0].getName();
		
		if (! BranchType.class.isAssignableFrom(method.getParameterTypes()[0]))
			argClassName = argClassName.substring("[L".length(),argClassName.length()-1);
		
		try {
			
			RegistryObjectQueryType []nestedQueries = (RegistryObjectQueryType[])
				Array.newInstance(Class.forName(argClassName),filter.size());
				
			RegistryObjectQueryType objectNestedQuery = null;
			int i = 0;
			for(Map<String,Object> fields : filter ) {
				
				objectNestedQuery =
					this.buildQuery(Class.forName(argClassName),fields);
				nestedQueries[i] = objectNestedQuery;
				i++;
			}
			//
			method.invoke(queryObject,(Object)nestedQueries);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


	/**
	 * @return the instance
	 */
	public static final RegistryObjectQueryBuilder getInstance() {
		return instance;
	}
	
}
