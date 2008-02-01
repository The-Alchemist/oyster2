package org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.filters;

import java.util.ArrayList;
import java.util.Map;

import org.neon_toolkit.registry.oyster2.Constants;
import org.neon_toolkit.registry.omv.service.NeOnRegistryOMVOysterSkeleton;
import org.oasis.names.tc.ebxml_regrep.xsd.query.CompoundFilterType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.FilterType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.SimpleFilterType;



/**
 * This class is used to parse ebXML filter statements
 * 
 * @author wrkboy
 *
 */
public class FilterParser {
	public static String parse(FilterType filter, Map attributes) throws Exception {
		return parse(filter, null, attributes,"");
	}
	
	public static String parse(FilterType filter, String changeDomainAttribute) throws Exception {
		
		return "";
	}
	
	/**
	 * This method is used in case the domainAttribute is not specified.
	 * 
	 * @param filter
	 * @param changeDomainAttribute
	 * @param attributes
	 * @return
	 * @throws Exception
	 */
	public static String parse(FilterType filter, String changeDomainAttribute, Map attributes, String ref) throws Exception {
		if (filter==null) return "";
		
		
		int icond=org.neon_toolkit.registry.omv.service.NeOnRegistryOMVOysterSkeleton.getNOI();
		String result = null;
		StringBuffer queryBuffer = new StringBuffer();
		String attribute = null;
		
		if (SimpleFilterType.class.isAssignableFrom(filter.getClass())) {
			if (changeDomainAttribute!=null){
				((SimpleFilterType)filter).setDomainAttribute(changeDomainAttribute);
				attribute=(String)attributes.get(changeDomainAttribute);
			}
			else attribute = ((SimpleFilterType)filter).getDomainAttribute();
			if (attribute ==null) return "";
			
			if (ref!=null && ref.length()>1){
				queryBuffer.append(". ?x ");
				queryBuffer.append("<"+Constants.OMVURI+ ref +"> "+"?r"+Integer.toString(icond));
				queryBuffer.append(" . "+"?r"+Integer.toString(icond) + " <"+Constants.OMVURI+attribute +"> "+ "?v"+Integer.toString(icond));
			}
			else{
				queryBuffer.append(". ?x ");
				queryBuffer.append("<"+Constants.OMVURI+attribute+">"+" "+ "?v"+Integer.toString(icond));
			}
			
			queryBuffer.append(SimpleFilterParser.parse((SimpleFilterType)filter, attributes,icond));
		} else 
		if (CompoundFilterType.class.isAssignableFrom(filter.getClass()))
			queryBuffer.append(CompoundFilterParser.parse((CompoundFilterType)filter, attributes,icond));
		
		
		result=queryBuffer.toString();
		if (result==null)
			throw new Exception("Unable to parse filter structure.");

		//if (filter.getNegate())
		//	result="fn:not("+result+")";
		return result;
	}

	
}
