package org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.omv;


import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.RegistryObjectQueryParser;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.filters.FilterParser;
import org.neon_toolkit.registry.omv.xsd.query.FormalityLevelQueryType;
import org.neon_toolkit.registry.omv.xsd.query.LicenseModelQueryType;
import org.neontoolkit.registry.oyster2.Constants;

/**
 * Parses a LicenseModel query. This object is ment to be used by RegistryObjectQueryParser. 
 * Please use RegistryObjectQueryParser.parse 
 * 
 * @author wrkboy
 *
 */
public class LicenseModelQueryParser extends RegistryObjectQueryParser {
	public static LicenseModelQueryParser parse(LicenseModelQueryType query, String ref) {
		LicenseModelQueryParser result = new LicenseModelQueryParser();
		if (ref!=null && ref.length()>1) result.objectType="";
		else result.objectType="SELECT ?x WHERE "+" { ?x rdf:type <"+Constants.OMVURI+Constants.LicenseModelConcept+"> ";
		
		StringBuffer queryBuffer = new StringBuffer();
		result.attributes.put("acronym", "acronym");		
		result.attributes.put("documentation", "documentation");
		
		try {
			queryBuffer.append(FilterParser.parse(query.getAcronymFilter(),"acronym",result.attributes,ref));
			queryBuffer.append(FilterParser.parse(query.getDocumentationFilter(),"documentation",result.attributes,ref));
		} catch (Exception e) { }
		
		result.queryPredicate=queryBuffer.toString();
		return result;
	}
}
