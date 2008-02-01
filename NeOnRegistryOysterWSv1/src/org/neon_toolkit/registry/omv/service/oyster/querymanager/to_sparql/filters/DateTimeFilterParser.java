package org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.filters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.oasis.names.tc.ebxml_regrep.xsd.query.ComparatorType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.DateTimeFilterType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType;

/**
 * This class is used to parse ebXML filter statements
 * 
 * @author wrkboy
 *
 */
public class DateTimeFilterParser extends SimpleFilterParser {
	public static String parse(DateTimeFilterType filter, String attribute, int icond) throws Exception {
		String result="";
		//DateFormat dateformater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+00:00'");
		//dateformater.setTimeZone(TimeZone.getTimeZone("GMT"));
		DateFormat dateformater = new SimpleDateFormat("yyyy-MM-dd");
		String value=dateformater.format(filter.getValue().getTime());
		System.out.println(value);
		
			/*
		if (filter.getComparator().getValue().equals(ComparatorType._NE)) {
			filter.setComparator(ComparatorType.EQ);
			filter.setNegate(true);
		}
			*/
			
		if (filter.getComparator().getValue().equals(ComparatorType._EQ))
			result=" . FILTER (?v"+Integer.toString(icond)+" = " + value + ") ";
		else
		if (filter.getComparator().getValue().equals(ComparatorType._LT))
			result=" . FILTER (?v"+Integer.toString(icond)+" < " + value + ") ";
		else
		if (filter.getComparator().getValue().equals(ComparatorType._LE))
			result=" . FILTER (?v"+Integer.toString(icond)+" <= " + value + ") ";
		else
		if (filter.getComparator().getValue().equals(ComparatorType._GT))
			result=" . FILTER (?v"+Integer.toString(icond)+" > " + value + ") ";
		else
		if (filter.getComparator().getValue().equals(ComparatorType._GE))
			result=" . FILTER (?v"+Integer.toString(icond)+" >= " + value + ") ";
		else
			throw new Exception("Comparator now allowed with DateTime filters.");
	
		return result;

	}

}