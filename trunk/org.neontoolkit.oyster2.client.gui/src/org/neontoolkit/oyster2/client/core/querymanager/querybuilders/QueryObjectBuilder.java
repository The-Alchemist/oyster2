/**
 * 
 */
package org.neontoolkit.oyster2.client.core.querymanager.querybuilders;

import java.util.Map;

import org.oasis.names.tc.ebxml_regrep.xsd.query.BranchType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.FilterType;

import org.oasis.names.tc.ebxml_regrep.xsd.query.RegistryObjectQueryType;

/**
 * @author David Muñoz
 *
 */
public interface QueryObjectBuilder {
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
	public RegistryObjectQueryType buildQuery(Class queryType,
			Map<String,Object> filters)
				throws InstantiationException, IllegalAccessException;
}
