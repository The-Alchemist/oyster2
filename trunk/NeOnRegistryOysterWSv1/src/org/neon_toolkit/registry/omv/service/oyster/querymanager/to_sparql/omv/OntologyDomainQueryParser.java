package org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.omv;

import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.RegistryObjectQueryParser;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.filters.FilterParser;
import org.neon_toolkit.registry.omv.xsd.query.OntologyDomainQueryType;
import org.neon_toolkit.registry.omv.xsd.query.OntologyTypeQueryType;
import org.neon_toolkit.registry.oyster2.Constants;

public class OntologyDomainQueryParser extends RegistryObjectQueryParser {
	public static OntologyDomainQueryParser parse(OntologyDomainQueryType query, String ref) {
		OntologyDomainQueryParser result = new OntologyDomainQueryParser();
		if (ref!=null && ref.length()>1) result.objectType="";
		else result.objectType="SELECT ?x WHERE "+" { ?x rdf:type <"+Constants.OMVURI+Constants.OntologyTaskConcept+"> ";
		
		StringBuffer queryBuffer = new StringBuffer();
		result.attributes.put("URI", "URI");		
		
		try {
			queryBuffer.append(FilterParser.parse(query.getURIFilter(),"URI",result.attributes,ref));
		} catch (Exception e) { }

		
		result.queryPredicate=queryBuffer.toString();
		return result;
	}
}
