package org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.filters;

import java.util.Map;

import org.oasis.names.tc.ebxml_regrep.xsd.query.CompoundFilterType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.SimpleFilterType;

/**
 * This class is used to parse ebXML filter statements
 * 
 * @author wrkboy
 *
 */
public class CompoundFilterParser extends FilterParser {
	public static String parse(CompoundFilterType filter, Map attributes, int icond) throws Exception {
		String result="";
		result=FilterParser.parse(filter.getLeftFilter(), attributes);
		result+=" "+filter.getLogicalOperator()+" ";
		result+=FilterParser.parse(filter.getRightFilter(), attributes);
		//return "("+result+")";
		return result;
	}

}
