package org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.filters;

import org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.ComparatorType;

/**
 * This class is used to parse ebXML filter statements
 * 
 * @author wrkboy
 *
 */
public class BooleanFilterParser extends SimpleFilterParser {
	public static String parse(BooleanFilterType filter, String attribute, int icond) throws Exception {
		String result;
		
		/*
		if (filter.getComparator().getValue().equals(ComparatorType._NE)) {
			filter.setComparator(ComparatorType.EQ);
			filter.setNegate(true);
		}
		*/
		
		if (filter.getComparator().getValue().equals(ComparatorType._EQ))
			result=" . FILTER (?v"+Integer.toString(icond)+" = " + filter.getValue() + ") ";
		else
			throw new Exception("Comparator now allowed with boolean filters.");
		
		return result;
	}
}

//result=attribute+" = "+(filter.getValue()? "fn:true()" : "fn:false()");