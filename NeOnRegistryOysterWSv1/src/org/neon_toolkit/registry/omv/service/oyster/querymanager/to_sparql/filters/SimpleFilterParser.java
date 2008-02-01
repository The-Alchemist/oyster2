package org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.filters;

import java.util.Map;

import org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilter;
import org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.DateTimeFilterType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.FloatFilterType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.SimpleFilterType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType;

/**
 * This class is used to parse ebXML filter statements
 * 
 * @author wrkboy
 *
 */
public class SimpleFilterParser extends FilterParser {
	public static String parse(SimpleFilterType filter, Map attributes, int icond) throws Exception {
		String result="";
		String attribute=(String)attributes.get(filter.getDomainAttribute());
		if (attribute==null) 
			throw new Exception("Attribute used in this filter is not available for this object type by definition.");
		
		if (BooleanFilterType.class.isAssignableFrom(filter.getClass()))
			result=BooleanFilterParser.parse((BooleanFilterType)filter, attribute,icond);
		else
		if (StringFilterType.class.isAssignableFrom(filter.getClass()))
			result=StringFilterParser.parse((StringFilterType)filter, attribute,icond);
		else
		if (DateTimeFilterType.class.isAssignableFrom(filter.getClass()))
			result=DateTimeFilterParser.parse((DateTimeFilterType)filter, attribute, icond);
		else
		if (IntegerFilterType.class.isAssignableFrom(filter.getClass()))
			result=IntegerFilterParser.parse((IntegerFilterType)filter, attribute, icond);
		else
		if (FloatFilterType.class.isAssignableFrom(filter.getClass()))
			result=FloatFilterParser.parse((FloatFilterType)filter, attribute, icond);
		
		return result;
		//return "("+result+")";
	}
}
