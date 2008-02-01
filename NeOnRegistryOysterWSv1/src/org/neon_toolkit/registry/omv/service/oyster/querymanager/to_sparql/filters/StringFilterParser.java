package org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.filters;

import java.util.ArrayList;
import java.util.Map;

import org.oasis.names.tc.ebxml_regrep.xsd.query.ComparatorType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType;

/**
 * This class is used to parse ebXML filter statements
 * 
 * @author wrkboy
 *
 */
public class StringFilterParser extends SimpleFilterParser {
	public static String parse(StringFilterType filter, String attribute, int icond) throws Exception {
		String result="";

		/*
		if (filter.getComparator().getValue().equals(ComparatorType._NE)) {
			filter.setComparator(ComparatorType.EQ);
			filter.setNegate(true);
		} else 
		if (filter.getComparator().getValue().equals(ComparatorType._NotLike)) {
			filter.setComparator(ComparatorType.Like);
			filter.setNegate(true);
		}  
		*/
		
		if (filter.getComparator().getValue().equals(ComparatorType._EQ))
			result=" . FILTER (?v"+Integer.toString(icond)+" = " + '"' + filter.getValue() + '"' + ") ";
		else
		if (filter.getComparator().getValue().equals(ComparatorType._Like))
			result=" . FILTER regex(?v"+Integer.toString(icond)+", " + '"' + filter.getValue() + '"' + "," + '"' + "i" + '"' + ") ";
		else
			throw new Exception("Comparator now allowed with String filters.");
		/*
		else
		if (filter.getComparator().getValue().equals(ComparatorType._LT))
			result="fn:compare("+attribute+",'"+filter.getValue()+"') = -1";
		else
		if (filter.getComparator().getValue().equals(ComparatorType._LE))
			result="fn:compare("+attribute+",'"+filter.getValue()+"') <= 0";
		else
		if (filter.getComparator().getValue().equals(ComparatorType._GT))
		result="fn:compare("+attribute+",'"+filter.getValue()+"') = 1";
		else
		if (filter.getComparator().getValue().equals(ComparatorType._GE))
			result="fn:compare("+attribute+",'"+filter.getValue()+"') >= 0";
		*/
		
		return result;
	}

}

/*
boolean wildmark_begin=filter.getValue().startsWith("%");
boolean wildmark_end=filter.getValue().endsWith("%");
String[] temp_patterns=filter.getValue().split("%");

if (!wildmark_begin && !wildmark_end && temp_patterns.length==1)
	result="fn:compare("+attribute+",'"+filter.getValue()+"')=0";
else
for (int i=0,j=0; i<temp_patterns.length;i++) {
	if (temp_patterns[i].equals(""))
		continue;
	if (j>0)
		result+=" and ";
	if ((j==0) && !wildmark_begin)
		result+="fn:starts-with("+attribute+",'"+temp_patterns[i]+"')";
	else
	if ((i==temp_patterns.length-1) && !wildmark_end)
		result+="fn:ends-with("+attribute+",'"+temp_patterns[i]+"')";
	else
		result+="fn:contains("+attribute+",'"+temp_patterns[i]+"')";
	j++;
}
*/
