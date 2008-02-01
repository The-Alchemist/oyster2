package org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.omv;

import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.RegistryObjectQueryParser;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.filters.FilterParser;

import org.neon_toolkit.registry.omv.xsd.query.OntologyTypeQueryType;
import org.neon_toolkit.registry.oyster2.Constants;

/**
 * Parses a OntologyType query. This object is ment to be used by RegistryObjectQueryParser. 
 * Please use RegistryObjectQueryParser.parse 
 * 
 * @author wrkboy
 *
 */
public class OntologyTypeQueryParser extends RegistryObjectQueryParser {
	
	public static OntologyTypeQueryParser parse(OntologyTypeQueryType query, String  ref) {
		OntologyTypeQueryParser result = new OntologyTypeQueryParser();
		if (ref!=null && ref.length()>1) result.objectType="";
		else result.objectType="SELECT ?x WHERE "+" { ?x rdf:type <"+Constants.OMVURI+Constants.OntologyTypeConcept+"> ";

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
