package org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
import javax.xml.registry.BusinessLifeCycleManager;
import javax.xml.registry.infomodel.ClassificationScheme;
import javax.xml.registry.infomodel.Concept;

import org.neon_toolkit.registry.omv.service.centrasite.querymanager.to_xquery.filters.FilterParser;
import org.neon_toolkit.registry.omv.service.centrasite.querymanager.to_xquery.omv.FormalityLevelQueryParser;
import org.neon_toolkit.registry.omv.service.centrasite.querymanager.to_xquery.omv.KnowledgeRepresentationParadigmQueryParser;
import org.neon_toolkit.registry.omv.service.centrasite.querymanager.to_xquery.omv.LicenseModelQueryParser;
import org.neon_toolkit.registry.omv.service.centrasite.querymanager.to_xquery.omv.OntologyDomainQueryParser;
import org.neon_toolkit.registry.omv.service.centrasite.querymanager.to_xquery.omv.OntologyEngineeringMethodologyQueryParser;
import org.neon_toolkit.registry.omv.service.centrasite.querymanager.to_xquery.omv.OntologyEngineeringToolQueryParser;
import org.neon_toolkit.registry.omv.service.centrasite.querymanager.to_xquery.omv.OntologyLanguageQueryParser;
import org.neon_toolkit.registry.omv.service.centrasite.querymanager.to_xquery.omv.OntologyQueryParser;
import org.neon_toolkit.registry.omv.service.centrasite.querymanager.to_xquery.omv.OntologySyntaxQueryParser;
import org.neon_toolkit.registry.omv.service.centrasite.querymanager.to_xquery.omv.OntologyTaskQueryParser;
import org.neon_toolkit.registry.omv.service.centrasite.querymanager.to_xquery.omv.OntologyTypeQueryParser;
*/
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.RegistryObjectQueryParser;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.filters.FilterParser;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.omv.FormalityLevelQueryParser;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.omv.KnowledgeRepresentationParadigmQueryParser;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.omv.LicenseModelQueryParser;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.omv.OntologyDomainQueryParser;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.omv.OntologyEngineeringMethodologyQueryParser;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.omv.OntologyEngineeringToolQueryParser;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.omv.OntologyLanguageQueryParser;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.omv.OntologyQueryParser;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.omv.OntologySyntaxQueryParser;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.omv.OntologyTaskQueryParser;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.omv.OntologyTypeQueryParser;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.omv.OrganizationQueryParser;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.omv.PersonQueryParser;
import org.neon_toolkit.registry.omv.xsd.query.FormalityLevelQueryType;
import org.neon_toolkit.registry.omv.xsd.query.KnowledgeRepresentationParadigmQueryType;
import org.neon_toolkit.registry.omv.xsd.query.LicenseModelQueryType;
import org.neon_toolkit.registry.omv.xsd.query.OntologyDomainQueryType;
import org.neon_toolkit.registry.omv.xsd.query.OntologyEngineeringMethodologyQueryType;
import org.neon_toolkit.registry.omv.xsd.query.OntologyEngineeringToolQueryType;
import org.neon_toolkit.registry.omv.xsd.query.OntologyLanguageQueryType;
import org.neon_toolkit.registry.omv.xsd.query.OntologyQueryType;
import org.neon_toolkit.registry.omv.xsd.query.OntologySyntaxQueryType;
import org.neon_toolkit.registry.omv.xsd.query.OntologyTaskQueryType;
import org.neon_toolkit.registry.omv.xsd.query.OntologyTypeQueryType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationNodeQueryType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.FilterType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.RegistryObjectQueryType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.SlotBranchType;

//import com.centrasite.jaxr.CentraSiteQueryManager;


/**
 * This class is used for parsing ebXML queries (ebRSFilterQuery). First the LifeCycleManager and the QueryManager 
 * should be set. Then the parse methods can be used to parse the queries. 
 * Finally the objectType and queryPredicate attributes can be used to create xqueries for CentraSiteQueryManager.findObjects 
 * 
 * @author wrkboy
 *
 */
public class RegistryObjectQueryParser {
	public String objectType=null;
	public String queryPredicate=null;
	protected Map attributes=new HashMap();
	protected Map slots=new HashMap();
	public static Map attributesOMV= new HashMap();

	/**
	 * Adds a simple query predicate.
	 * 
	 * @param newQP - new predicate
	 */
	protected void addQueryPredicate(String newQP) {
		if (newQP==null) return;
		if (queryPredicate!=null)
			queryPredicate+=" and "+newQP;
		else
			queryPredicate=newQP;
	}

	/**
	 * Creates multiple complex query predicates from subqueries and adds them to the currect predicate
	 * 
	 * @param elementName - the element to which the result should be compared
	 * @param subquery - a list of subquery parsers
	 */
	protected void addSubQueryPredicate(String elementName, RegistryObjectQueryParser[] subquery) {
		if (subquery==null) return;
		for (RegistryObjectQueryParser temp_onequery:subquery)
			addSubQueryPredicate(elementName, temp_onequery);
	}
	
	/**
	 * Creates a complex query predicate from a subquery and adds it to the currect predicate
	 * 
	 * @param elementName - the element to which the result should be compared
	 * @param subquery - the subquery parser
	 */
	protected void addSubQueryPredicate(String elementName, RegistryObjectQueryParser subquery) {
		if (subquery==null) return;
		// sets a random xquery element name fo this subquery
		String subElementName="$ro"+Math.round(Math.random()*1000000+100000);
		String subQueryPredicate="";
		if (subquery.queryPredicate!=null)
			subQueryPredicate=" where "+subquery.queryPredicate.replaceAll("\\$ro\\/", "\\"+subElementName+"\\/");
		String temp_subquery="for "+subElementName+" in collection(\"CentraSite\")/"+subquery.objectType+subQueryPredicate+" return "+subElementName+"/cs:key";
		addQueryPredicate("(("+temp_subquery+")="+elementName+")");
	}
	
	/**
	 * Initialises the LifeCycleManager
	 * 
	 * @param param - a LifeCycleManager
	 */
	/*
	public static void setLCManager(BusinessLifeCycleManager param) {
		lcManager=param;
	}
	*/
	/**
	 * Initialises the QueryManager. This method also preloads the CentraSite keys of all OMV associations. 
	 * 
	 * @param param - the QueryManager
	 * @throws Exception
	 */
	/*
	public static void setBQManager(CentraSiteQueryManager param) throws Exception {
		bqManager=param;
		
		attributesOMV.put("isOfType", getAssociationTypeKeyByName("IsOfType"));
		attributesOMV.put("has", getAssociationTypeKeyByName("Has") );
		attributesOMV.put("hasPriorVersion", getAssociationTypeKeyByName("HasPriorVersion") );
		attributesOMV.put("hasContributor", getAssociationTypeKeyByName("HasContributor") );
		attributesOMV.put("hasCreator", getAssociationTypeKeyByName("HasCreator") );
		attributesOMV.put("uses", getAssociationTypeKeyByName("Uses") );
		attributesOMV.put("designedForOntologyTask", getAssociationTypeKeyByName("DesignedForOntologyTask") );
		attributesOMV.put("useImports", getAssociationTypeKeyByName("UseImports") );
		attributesOMV.put("isBackwardCompatibleWith", getAssociationTypeKeyByName("IsBackwardCompatibleWith") );
		attributesOMV.put("isIncompatibleWith", getAssociationTypeKeyByName("IsIncompatibleWith") );
		attributesOMV.put("specifiedBy", getAssociationTypeKeyByName("SpecifiedBy") );
		attributesOMV.put("developedBy", getAssociationTypeKeyByName("DevelopedBy") );
		attributesOMV.put("definedBy", getAssociationTypeKeyByName("DefinedBy") );
		attributesOMV.put("supportsRepresentationParadigm", getAssociationTypeKeyByName("SupportsRepresentationParadigm") );
		attributesOMV.put("endorsedBy", getAssociationTypeKeyByName("EndorsedBy") );
		attributesOMV.put("isSubDomainOf", getAssociationTypeKeyByName("IsSubDomainOf") );
		
	}
*/
	/**
	 * Fetches an association concept key by its name.
	 * 
	 * @param name - the name of the association concept
	 * @return the concept key
	 * @throws Exception
	 */
	/*
	public static String getAssociationTypeKeyByName(String name) throws Exception {
		try {
			if (name==null) throw new Exception("Empty name");
			ClassificationScheme classScheme = bqManager.findClassificationSchemeByName(null, "AssociationType");
			if (classScheme==null) throw new Exception("Could not get the AssociationType class scheme.");
			Concept tempAssoc = bqManager.findConceptByPath("/" + classScheme.getKey().getId()+"/"+name);
			if (tempAssoc==null) throw new Exception("Could not find the concept.");
			return tempAssoc.getKey().getId();
		} catch (Exception e) {
			throw new Exception("Could not preload OMV AssociationType "+name+":"+e.getMessage());
		}
	}
	*/
	/**
	 * Parses multiple ebXML queries
	 * 
	 * @param query - a list of ebXML query objects
	 * @return a RegistryObjectQueryParser containing the needed objectType and queryPredicate
	 */
	public static RegistryObjectQueryParser[] parse(RegistryObjectQueryType[] query) {
		if (query==null) return null;
		ArrayList temp_list=new ArrayList();
		RegistryObjectQueryParser temp_oneresult;
		for (RegistryObjectQueryType temp_onequery:query)
			if ((temp_oneresult=parse(temp_onequery))!=null)
				temp_list.add(temp_oneresult);
		return (RegistryObjectQueryParser[])temp_list.toArray(new RegistryObjectQueryParser[1]);
	}
	
	
	public static String parse(RegistryObjectQueryType[] query, String ref) {
		if (query==null) return "";
		StringBuffer queryBuffer = new StringBuffer();
		
		RegistryObjectQueryParser temp_oneresult;
		for (RegistryObjectQueryType temp_onequery:query)
			if ((temp_oneresult=parseRef(temp_onequery,ref))!=null)
				queryBuffer.append(temp_oneresult.queryPredicate);
		return queryBuffer.toString();
	}
	
	/**
	 * Parses an ebXML query 
	 * 
	 * @param query - an ebXML query object
	 * @return a RegistryObjectQueryParser containing the needed objectType and queryPredicate
	 */
	public static RegistryObjectQueryParser parse(RegistryObjectQueryType query) {
		if (query==null) return null;
		
		RegistryObjectQueryParser result;
		
		//find query type
		
		//OMV objects
		StringBuffer queryBuffer = new StringBuffer();
		
		if (OntologyQueryType.class.isAssignableFrom(query.getClass()))
			result = OntologyQueryParser.parse((OntologyQueryType)query, "");
		else {			
			result = new RegistryObjectQueryParser();
			result.objectType="registryEntry";
		}
		// parse all primary filters 
		//result.attributes.put("id", "$ro/cs:key");
		//try {
		//	result.addQueryPredicate(FilterParser.parse(query.getPrimaryFilter(),result.attributes));
		//} catch (Exception e) { }

		// parse name
		if (query.getNameBranch()!=null) {
			Map temp_nameAttributes = new HashMap();
			temp_nameAttributes.put("name", "name");
			for (FilterType temp_nameBranch:query.getNameBranch().getLocalizedStringFilter())
				try {
					queryBuffer.append(FilterParser.parse(temp_nameBranch,"name",temp_nameAttributes,""));
				} catch (Exception e) { }
		}

		// parse description
		if (query.getDescriptionBranch()!=null) {
			Map temp_descriptionAttributes = new HashMap();
			temp_descriptionAttributes.put("description", "description");
			for (FilterType temp_descriptionBranch:query.getDescriptionBranch().getLocalizedStringFilter())
				try {
					queryBuffer.append(FilterParser.parse(temp_descriptionBranch,"description",temp_descriptionAttributes,""));
				} catch (Exception e) { }
		}
			
		// parse all the slots
		/*
		if (!result.slots.isEmpty() && (query.getSlotBranch()!=null))
			for (SlotBranchType temp_slotBranch:query.getSlotBranch())
				try {
					result.addQueryPredicate(FilterParser.parse(temp_slotBranch.getPrimaryFilter(),result.attributes));
				} catch (Exception e) { }
		*/
		// parse all source associations
		//result.addSubQueryPredicate("$ro/cs:associations/cs:association/cs:key", 
		//		RegistryObjectQueryParser.parse(query.getSourceAssociationQuery()));
			
// implement the target associations HERE

// implement the classifications HERE

// implement the objecttype attribute HERE

// implement the status attribute HERE

// implement the version info HERE

// implement the external identifier HERE
		queryBuffer.append("}");
		result.queryPredicate=result.queryPredicate+queryBuffer.toString();
		return result;		
	}
	
	public static RegistryObjectQueryParser parseRef(RegistryObjectQueryType query, String ref) {
		if (query==null) return null;
		
		RegistryObjectQueryParser result;
		
		//find query type
		
		//OMV objects
		StringBuffer queryBuffer = new StringBuffer();
		
		if (FormalityLevelQueryType.class.isAssignableFrom(query.getClass()))
			result = FormalityLevelQueryParser.parse((FormalityLevelQueryType)query, ref);
		else			
		if (KnowledgeRepresentationParadigmQueryType.class.isAssignableFrom(query.getClass()))
			result = KnowledgeRepresentationParadigmQueryParser.parse((KnowledgeRepresentationParadigmQueryType)query, ref);
		else			
		if (LicenseModelQueryType.class.isAssignableFrom(query.getClass()))
			result = LicenseModelQueryParser.parse((LicenseModelQueryType)query,ref);
		else
		if (OntologyEngineeringMethodologyQueryType.class.isAssignableFrom(query.getClass()))
			result = OntologyEngineeringMethodologyQueryParser.parse((OntologyEngineeringMethodologyQueryType)query, ref);
		else
		if (OntologyEngineeringToolQueryType.class.isAssignableFrom(query.getClass()))
			result = OntologyEngineeringToolQueryParser.parse((OntologyEngineeringToolQueryType)query, ref);
		else			
		if (OntologyLanguageQueryType.class.isAssignableFrom(query.getClass()))
			result = OntologyLanguageQueryParser.parse((OntologyLanguageQueryType)query,ref);
		else
		if (OntologyQueryType.class.isAssignableFrom(query.getClass()))
			result = OntologyQueryParser.parse((OntologyQueryType)query,ref);
		else
		if (OntologySyntaxQueryType.class.isAssignableFrom(query.getClass()))
			result = OntologySyntaxQueryParser.parse((OntologySyntaxQueryType)query,ref);
		else
		if (OntologyTaskQueryType.class.isAssignableFrom(query.getClass()))
			result = OntologyTaskQueryParser.parse((OntologyTaskQueryType)query, ref);
		else
		if (OntologyTypeQueryType.class.isAssignableFrom(query.getClass()))
			result = OntologyTypeQueryParser.parse((OntologyTypeQueryType)query,ref);
		else
		if (OntologyDomainQueryType.class.isAssignableFrom(query.getClass()))
			result = OntologyDomainQueryParser.parse((OntologyDomainQueryType)query, ref);
		else
		if (PersonQueryType.class.isAssignableFrom(query.getClass()))
			result = PersonQueryParser.parse((PersonQueryType)query,ref);
		else
		if (OrganizationQueryType.class.isAssignableFrom(query.getClass()))
			result = OrganizationQueryParser.parse((OrganizationQueryType)query, ref);
		else {			
			result = new RegistryObjectQueryParser();
			result.objectType="registryEntry";
		}
		
		// parse name
		if (query.getNameBranch()!=null) {
			Map temp_nameAttributes = new HashMap();
			temp_nameAttributes.put("name", "name");
			for (FilterType temp_nameBranch:query.getNameBranch().getLocalizedStringFilter())
				try {
					queryBuffer.append(FilterParser.parse(temp_nameBranch,"name",temp_nameAttributes,ref));
				} catch (Exception e) { }
		}

		// parse description
		if (query.getDescriptionBranch()!=null) {
			Map temp_descriptionAttributes = new HashMap();
			temp_descriptionAttributes.put("description", "description");
			for (FilterType temp_descriptionBranch:query.getDescriptionBranch().getLocalizedStringFilter())
				try {
					queryBuffer.append(FilterParser.parse(temp_descriptionBranch,"description",temp_descriptionAttributes,ref));
				} catch (Exception e) { }
		}
		
		result.queryPredicate=result.queryPredicate+queryBuffer.toString();
		
		return result;		
	}

}
