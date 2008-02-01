package org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.omv;

import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.RegistryObjectQueryParser;

import org.neon_toolkit.registry.omv.xsd.query.FormalityLevelQueryType;
import org.neon_toolkit.registry.omv.xsd.query.OntologyQueryType;
import org.neon_toolkit.registry.oyster2.Constants;

/**
 * Parses a FormalityLevel query. This object is ment to be used by RegistryObjectQueryParser. 
 * Please use RegistryObjectQueryParser.parse 
 * 
 * @author wrkboy
 *
 */
public class FormalityLevelQueryParser extends RegistryObjectQueryParser {

	public static FormalityLevelQueryParser parse(FormalityLevelQueryType query, String ref) {
		FormalityLevelQueryParser result = new FormalityLevelQueryParser();
		if (ref!=null && ref.length()>1) result.objectType="";
		else result.objectType="SELECT ?x WHERE "+" { ?x rdf:type <"+Constants.OMVURI+Constants.FormalityLevelConcept+"> ";

		return result;
	}

}
