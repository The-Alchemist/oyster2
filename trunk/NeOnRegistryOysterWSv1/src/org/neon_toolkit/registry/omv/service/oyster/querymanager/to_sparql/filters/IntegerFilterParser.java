package org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.filters;

import org.oasis.names.tc.ebxml_regrep.xsd.query.ComparatorType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType;

/**
 * This class is used to parse ebXML filter statements
 * 
 * @author wrkboy
 *
 */
public class IntegerFilterParser extends SimpleFilterParser {
	public static String parse(IntegerFilterType filter, String attribute, int icond) throws Exception {
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
		if (filter.getComparator().getValue().equals(ComparatorType._LT))
			result=" . FILTER (?v"+Integer.toString(icond)+" < " + filter.getValue() + ") ";
		else
		if (filter.getComparator().getValue().equals(ComparatorType._LE))
			result=" . FILTER (?v"+Integer.toString(icond)+" <= " + filter.getValue() + ") ";
		else
		if (filter.getComparator().getValue().equals(ComparatorType._GT))
			result=" . FILTER (?v"+Integer.toString(icond)+" > " + filter.getValue() + ") ";
		else
		if (filter.getComparator().getValue().equals(ComparatorType._GE))
			result=" . FILTER (?v"+Integer.toString(icond)+" >= " + filter.getValue() + ") ";
		else
			throw new Exception("Comparator now allowed with Integer filters.");
	

		return result;
	}

}
