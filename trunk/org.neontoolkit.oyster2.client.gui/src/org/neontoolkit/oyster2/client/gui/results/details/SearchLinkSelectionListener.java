/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.results.details;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.jobs.SubmitQueryJob;
import org.neontoolkit.oyster2.client.gui.search.Filter;
import org.neontoolkit.oyster2.client.gui.search.FilterType;
import org.neontoolkit.oyster2.client.gui.search.FilterValue;
import org.neontoolkit.oyster2.client.gui.search.PersonFilterValue;
import org.neontoolkit.oyster2.client.gui.search.QueryBuilderAdapter;
import org.neontoolkit.oyster2.client.gui.search.model.IQueryDomainClass;
import org.neontoolkit.oyster2.client.gui.search.model.IQueryDomainClassAttribute;
import org.neontoolkit.oyster2.client.gui.search.model.QueryFactory;

/**
 * @author David Muñoz
 *
 */
public class SearchLinkSelectionListener implements ISearchLinkSelectionListener {

	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.results.details.ISearchLinkSelectionListener#linkSelected(org.neontoolkit.oyster2.client.gui.results.details.ISearchLink)
	 */
	public void linkSelected(ISearchLink link) {
		if (link.isParty()) {
			makePartySearch(link);
		}
		else {
			makeSimpleSearch(link);
		}


	}

	private void makePartySearch(ISearchLink link) {
		Map<String,Object> filters = new HashMap<String,Object>();
		PartySearchLink partyLink = (PartySearchLink)link;
		Map<String, String> propertyNames = partyLink.getAttributeNames();
		String domainClassName = link.getTarget();
		String propertyValue = link.getValue().toString();

		IQueryDomainClass domainClass = 
			QueryFactory.getInstance().getQueryTargetDescription(domainClassName);
		Class javaClass = domainClass.getTargetJavaClass();
		IQueryDomainClassAttribute domainAttribute = null;


		// try to get a person filter value
		PersonFilterValue personFilter = null;
		String name = getName(propertyValue);
		String lastname = null;
		if (name != null) {
			lastname = getLastname(propertyValue);
		}
		if ((name != null) && (lastname != null)) {
			personFilter = new PersonFilterValue();
			personFilter.setLastname(lastname);
			personFilter.setName(name);


			//get person domain attribute
			domainAttribute = domainClass.getAttribute(propertyNames.get("person"));
			boolean multi = domainAttribute.hasMultipleParameters();
			FilterType filterType = domainAttribute.getFilterType();

			FilterValue filterValue = new FilterValue();
			filterValue.setValue(personFilter);
			filterValue.setComparator(FilterType.COMPARATOR_EQUAL);
			filterValue.setNegate(false);


			Filter filter = new Filter();
			filter.setMulti(multi);
			filter.setType(filterType);

			ArrayList<FilterValue> listOfFilterValues = new ArrayList<FilterValue>();
			listOfFilterValues.add(filterValue);
			filter.setFilterValues(listOfFilterValues);


			String setterMethodName = domainAttribute.getSetterMethodName();
			filters.put(setterMethodName,filter);
		}
		else {
		//create the organization filter

		//get organization domain attribute
		domainAttribute = domainClass.getAttribute(propertyNames.get("organization"));
		boolean multi = domainAttribute.hasMultipleParameters();
		FilterType filterType = domainAttribute.getFilterType();

		FilterValue filterValue = new FilterValue();
		filterValue.setValue(propertyValue);
		filterValue.setComparator(FilterType.COMPARATOR_EQUAL);
		filterValue.setNegate(false);


		Filter filter = new Filter();
		filter.setMulti(multi);
		filter.setType(filterType);

		ArrayList<FilterValue> listOfFilterValues = new ArrayList<FilterValue>();
		listOfFilterValues.add(filterValue);
		filter.setFilterValues(listOfFilterValues);


		String setterMethodName = domainAttribute.getSetterMethodName();
		filters.put(setterMethodName,filter);
		}

	//we get the filters as they must be passed to the WS client
	filters = QueryBuilderAdapter.getFilters(javaClass.getName(),filters);

	sendQuery(domainClassName,domainClass,filters);

}




private String getName(String text) {
	char [] buffer = new char[text.length()];

	text.getChars(0,text.length(), buffer, 0);
	//ignore the first capital letter
	int i = 1;
	for (; i<buffer.length;i++) {
		if (Character.isUpperCase(buffer[i]))
			break;
	}
	if (i == buffer.length) {
		//we reached the end... can't get name and lastname
		return null;
	}
	return text.substring(0,i);

}

private String getLastname(String text) {
	char [] buffer = new char[text.length()];

	text.getChars(0,text.length(), buffer, 0);
	//ignore the first capital letter
	int i = 1;
	for (; i<buffer.length;i++) {
		if (Character.isUpperCase(buffer[i]))
			break;
	}
	if (i == buffer.length) {
		//we reached the end... can't get name and lastname
		return null;
	}
	return text.substring(i);

}



private void makeSimpleSearch(ISearchLink link) {

	String propertyName = link.getAttributeNames().toString();
	String domainClassName = link.getTarget();
	String propertyValue = link.getValue().toString();

	IQueryDomainClass domainClass = 
		QueryFactory.getInstance().getQueryTargetDescription(domainClassName);


	IQueryDomainClassAttribute domainAttribute = domainClass.getAttribute(propertyName);

	boolean multi = domainAttribute.hasMultipleParameters();
	FilterType filterType = domainAttribute.getFilterType();

	FilterValue filterValue = new FilterValue();
	
	
	filterValue.setValue(propertyValue);
	
	filterValue.setComparator(FilterType.COMPARATOR_EQUAL);
	filterValue.setNegate(false);


	Filter filter = new Filter();
	filter.setMulti(multi);
	filter.setType(filterType);

	ArrayList<FilterValue> listOfFilterValues = new ArrayList<FilterValue>();
	listOfFilterValues.add(filterValue);
	filter.setFilterValues(listOfFilterValues);

	Class javaClass = domainClass.getTargetJavaClass();
	String setterMethodName = domainAttribute.getSetterMethodName();


	Map<String,Object> filters = new HashMap<String,Object>();
	filters.put(setterMethodName,filter);

	//we get the filters as they must be passed to the WS client
	filters = QueryBuilderAdapter.getFilters(javaClass.getName(),filters);

	sendQuery(domainClassName,domainClass,filters);

}

private void sendQuery(String domainClassName,
		IQueryDomainClass domainClass,Map<String, Object> filters) {
	SubmitQueryJob job = new SubmitQueryJob("search " + domainClassName );
	job.setTargetService(Activator.getWebServersLocator().getCurrentSelection());
	job.setFilters(filters);
	job.setUser(false);
	job.setQueryTarget(domainClass.getTargetJavaClass());
	job.schedule();
}

}
