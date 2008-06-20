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
public class RegistryObjectQueryBuilder implements QueryObjectBuilder {

	private static final RegistryObjectQueryBuilder instance =
		new RegistryObjectQueryBuilder();
	/*
	protected void fillRegistryObjectQueryTypeAttributes(
			RegistryObjectQueryType registryObject,
			Map<String,Object> filters) {
		
		FilterType[] names = (FilterType[]) filters.get("name");
		if (names != null) {
			if (names.length != 0) {
				InternationalStringBranchType nameBranch = new InternationalStringBranchType();
				// Filters for names are StringFilters
				nameBranch.setLocalizedStringFilter(names);
				registryObject.setNameBranch(nameBranch);
			}
		}
		FilterType[] descriptions = (FilterType[]) filters.get("description");
		if (descriptions != null) {
			if (descriptions.length != 0) {
				InternationalStringBranchType descriptionBranch = new InternationalStringBranchType();
				// Filters for names are StringFilters
				descriptionBranch.setLocalizedStringFilter(descriptions);
				registryObject.setDescriptionBranch(descriptionBranch);
			}
		}
	}
*/
	
	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.core.querymanager.RegistryObjectQueryBuilder#buildQuery(java.util.Map)
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
