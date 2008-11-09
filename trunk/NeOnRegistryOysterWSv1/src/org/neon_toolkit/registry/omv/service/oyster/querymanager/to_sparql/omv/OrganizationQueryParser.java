package org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.omv;

import java.util.HashMap;
import java.util.Map;

import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.RegistryObjectQueryParser;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.filters.FilterParser;
import org.neontoolkit.registry.oyster2.Constants;

import org.oasis.names.tc.ebxml_regrep.xsd.query.FilterType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType;

/**
 * Parses an Organization query. This object is ment to be used by RegistryObjectQueryParser. 
 * Please use RegistryObjectQueryParser.parse 
 * 
 * @author wrkboy
 *
 */

public class OrganizationQueryParser extends RegistryObjectQueryParser {
	public static OrganizationQueryParser parse(OrganizationQueryType query, String ref) {
		OrganizationQueryParser result = new OrganizationQueryParser();
		if (ref!=null && ref.length()>1) result.objectType="";
		else result.objectType="SELECT ?x WHERE "+" { ?x rdf:type <"+Constants.OMVURI+Constants.organisationConcept+"> ";

		StringBuffer queryBuffer = new StringBuffer();
//		 parse Address
		if (query.getAddressFilter()!=null) {
			Map temp_addressAttributes = new HashMap();
			temp_addressAttributes.put("city", "city");
			temp_addressAttributes.put("country", "country");
			temp_addressAttributes.put("stateOrProvince", "state");
			temp_addressAttributes.put("street", "street");
			for (FilterType temp_addressFilter:query.getAddressFilter())
				try {
					queryBuffer.append(FilterParser.parse(temp_addressFilter,null,temp_addressAttributes,ref));
				} catch (Exception e) { }
		}
//		 implement the child organisation HERE
		
		result.queryPredicate=queryBuffer.toString();
		return result;
	}
}
