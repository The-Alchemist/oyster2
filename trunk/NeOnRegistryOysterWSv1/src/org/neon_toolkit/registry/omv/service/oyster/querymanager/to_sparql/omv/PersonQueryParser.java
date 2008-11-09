package org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.omv;

import java.util.HashMap;
import java.util.Map;

import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.RegistryObjectQueryParser;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.filters.FilterParser;
import org.neontoolkit.omv.api.core.OMVPerson;
import org.neontoolkit.registry.oyster2.Constants;

import org.oasis.names.tc.ebxml_regrep.xsd.query.FilterType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType;

/**
 * Parses a Person query. This object is ment to be used by RegistryObjectQueryParser. 
 * Please use RegistryObjectQueryParser.parse 
 * 
 * @author wrkboy
 *
 */
public class PersonQueryParser extends RegistryObjectQueryParser {
	public static PersonQueryParser parse(PersonQueryType query, String ref) {
		PersonQueryParser result = new PersonQueryParser();
		if (ref!=null && ref.length()>1) result.objectType="";
		else result.objectType="SELECT ?x WHERE "+" { ?x rdf:type <"+Constants.OMVURI+Constants.personConcept+"> ";

		StringBuffer queryBuffer = new StringBuffer();
		//parse PersonName
		if (query.getPersonNameFilter()!=null) {
			Map temp_personNameAttributes = new HashMap();
			temp_personNameAttributes.put("firstName", "firstName");
			temp_personNameAttributes.put("middleName", "middleName");
			temp_personNameAttributes.put("lastName", "lastName");
			try {
				queryBuffer.append(FilterParser.parse(query.getPersonNameFilter(),null,temp_personNameAttributes,ref));
			} catch (Exception e) { }
		}

		// parse EmailAddress
		if (query.getEmailAddressFilter()!=null) {
			Map temp_emailAddressAttributes = new HashMap();
			temp_emailAddressAttributes.put("address", "email");
			temp_emailAddressAttributes.put("type", "type");
			for (FilterType temp_emailAddressFilter:query.getEmailAddressFilter())
				try {
					queryBuffer.append(FilterParser.parse(temp_emailAddressFilter,null,temp_emailAddressAttributes,ref));
				} catch (Exception e) { }
		}

		// parse Telefonenumber
		if (query.getTelephoneNumberFilter()!=null) {
			Map temp_telefonNumberAttributes = new HashMap();
			temp_telefonNumberAttributes.put("number", "phoneNumber");
			for (FilterType temp_telefonNumberFilter:query.getTelephoneNumberFilter())
				try {
					queryBuffer.append(FilterParser.parse(temp_telefonNumberFilter,null,temp_telefonNumberAttributes,ref));
				} catch (Exception e) { }
		}
		
		
		
		// parse Address
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
		
		
		result.queryPredicate=queryBuffer.toString();
		return result;
	}
}
