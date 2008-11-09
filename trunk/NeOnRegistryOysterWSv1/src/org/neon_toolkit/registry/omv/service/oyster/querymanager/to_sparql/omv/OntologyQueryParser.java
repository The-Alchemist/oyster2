package org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.omv;

import java.util.HashMap;
import java.util.Map;


import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.RegistryObjectQueryParser;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.filters.FilterParser;
import org.neon_toolkit.registry.omv.xsd.query.OntologyQueryType;
import org.neon_toolkit.registry.omv.xsd.query.OntologyTypeQueryType;
import org.neontoolkit.registry.oyster2.Constants;
import org.oasis.names.tc.ebxml_regrep.xsd.query.FilterType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType;

/**
 * Parses a Ontology query. This object is ment to be used by RegistryObjectQueryParser. 
 * Please use RegistryObjectQueryParser.parse 
 * 
 * @author wrkboy
 *
 */
public class OntologyQueryParser extends RegistryObjectQueryParser {
	
	public static OntologyQueryParser parse(OntologyQueryType query, String ref) {
		OntologyQueryParser result = new OntologyQueryParser();
		if (ref!=null && ref.length()>1) result.objectType="";
		else result.objectType="SELECT ?x WHERE "+" { ?x rdf:type <"+Constants.OMVURI+Constants.ontologyConcept+"> ";

	
		result.attributes.put("acronym", "acronym");		
		result.attributes.put("documentation", "documentation");		
		
		result.attributes.put("isOfType", "isOfType");		
		result.attributes.put("hasOntologySyntax", "hasOntologySyntax");		
		result.attributes.put("hasOntologyLanguage", "hasOntologyLanguage");		
		result.attributes.put("hasLicense", "hasLicense");		
		result.attributes.put("hasFormalityLevel", "hasFormalityLevel");		
		result.attributes.put("hasPriorVersion", "hasPriorVersion");		

		result.attributes.put("numberOfClasses", "numberOfClasses");		
		result.attributes.put("numberOfProperties", "numberOfProperties");		
		result.attributes.put("numberOfIndividuals", "numberOfIndividuals");		
		result.attributes.put("numberOfAxioms", "numberOfAxioms");		
		result.attributes.put("ontologyStatus", "ontologyStatus");		
		result.attributes.put("creationDate", "creationDate");		
		result.attributes.put("modificationDate", "modificationDate");		
		result.attributes.put("resourceLocator", "resourceLocator");		
		result.attributes.put("URI", "URI");		
		result.attributes.put("version", "version");
		result.attributes.put("isConsistentAccordingToReasoner", "isConsistentAccordingToReasoner");
		result.attributes.put("containsABox", "containsABox");
		result.attributes.put("containsRBox", "containsRBox");
		result.attributes.put("containsTBox", "containsTBox");
		result.attributes.put("expressiveness", "expressiveness");
		result.attributes.put("keyClasses", "keyClasses");
		result.attributes.put("knownUsage", "knownUsage");
		result.attributes.put("notes", "notes");
		
		StringBuffer queryBuffer = new StringBuffer();
		//queryBuffer.append("SELECT ?x WHERE ");
		//queryBuffer.append(" { ?x rdf:type <"+Constants.OMVURI+Constants.ontologyConcept+"> ");
		
		
		try {
			queryBuffer.append(FilterParser.parse(query.getAcronymFilter(),"acronym",result.attributes,ref));
			queryBuffer.append(FilterParser.parse(query.getDocumentationFilter(),"documentation",result.attributes,ref));
			queryBuffer.append(FilterParser.parse(query.getNumberOfClassesFilter(),"numberOfClasses",result.attributes,""));
			queryBuffer.append(FilterParser.parse(query.getNumberOfPropertiesFilter(),"numberOfProperties",result.attributes,ref));
			queryBuffer.append(FilterParser.parse(query.getNumberOfIndividualsFilter(),"numberOfIndividuals",result.attributes,ref));
			queryBuffer.append(FilterParser.parse(query.getNumberOfAxiomsFilter(),"numberOfAxioms",result.attributes,ref));
			queryBuffer.append(FilterParser.parse(query.getOntologyStatusFilter(),"ontologyStatus",result.attributes,ref));
			queryBuffer.append(FilterParser.parse(query.getCreationDateFilter(),"creationDate",result.attributes,ref));
			queryBuffer.append(FilterParser.parse(query.getModificationDateFilter(),"modificationDate",result.attributes,ref));
			queryBuffer.append(FilterParser.parse(query.getResourceLocatorFilter(),"resourceLocator",result.attributes,ref));
			queryBuffer.append(FilterParser.parse(query.getURIFilter(),"URI",result.attributes,ref));
			queryBuffer.append(FilterParser.parse(query.getVersionFilter(),"version",result.attributes,ref));
			queryBuffer.append(FilterParser.parse(query.getIsConsistentAccordingToReasonerFilter(),"isConsistentAccordingToReasoner",result.attributes,ref));
			queryBuffer.append(FilterParser.parse(query.getContainsABoxFilter(),"containsABox",result.attributes,ref));
			queryBuffer.append(FilterParser.parse(query.getContainsRBoxFilter(),"containsRBox",result.attributes,ref));
			queryBuffer.append(FilterParser.parse(query.getContainsTBoxFilter(),"containsTBox",result.attributes,ref));
			queryBuffer.append(FilterParser.parse(query.getExpressivenessFilter(),"expressiveness",result.attributes,ref));
			queryBuffer.append(FilterParser.parse(query.getKeyClassesFilter(),"keyClasses",result.attributes,ref));
			queryBuffer.append(FilterParser.parse(query.getKnownUsageFilter(),"knownUsage",result.attributes,ref));
			queryBuffer.append(FilterParser.parse(query.getNotesFilter(),"notes",result.attributes,ref));
		} catch (Exception e) { }

		
		// parse Keywords
		if (query.getKeywordsBranch()!=null) {
			Map temp_keywordsAttributes = new HashMap();
			temp_keywordsAttributes.put("keywords", "keywords");
			for (FilterType temp_keywordsBranch:query.getKeywordsBranch().getLocalizedStringFilter())
				try {
					queryBuffer.append(FilterParser.parse(temp_keywordsBranch,"keywords",temp_keywordsAttributes,ref));
				} catch (Exception e) { }
		}
		// parse NaturalLanguage
		if (query.getNaturalLanguageBranch()!=null) {
			Map temp_naturalLanguageAttributes = new HashMap();
			temp_naturalLanguageAttributes.put("naturalLanguage", "naturalLanguage");
			for (FilterType temp_naturalLanguageBranch:query.getNaturalLanguageBranch().getLocalizedStringFilter())
				try {
					queryBuffer.append(FilterParser.parse(temp_naturalLanguageBranch,"naturalLanguage",temp_naturalLanguageAttributes,ref));
				} catch (Exception e) { }
		}

		// parse element subqueries
		
		try {
			if (query.getIsOfTypeQuery()!=null){	
				queryBuffer.append(RegistryObjectQueryParser.parse(query.getIsOfTypeQuery(),"isOfType"));
			}
			if (query.getHasOntologyLanguageQuery()!=null){	
				queryBuffer.append(RegistryObjectQueryParser.parse(query.getHasOntologyLanguageQuery(),"hasOntologyLanguage"));
			}
			if (query.getHasOntologySyntaxQuery()!=null){
				queryBuffer.append(RegistryObjectQueryParser.parse(query.getHasOntologyLanguageQuery(),"hasOntologySyntax"));
			}
			if (query.getHasLicenseQuery()!=null){
				queryBuffer.append(RegistryObjectQueryParser.parse(query.getHasLicenseQuery(),"hasLicense"));
			}
			if (query.getHasFormalityLevelQuery()!=null){
				queryBuffer.append(RegistryObjectQueryParser.parse(query.getHasFormalityLevelQuery(),"hasFormalityLevel"));
			}
			if (query.getHasCreatorPersonQuery()!=null){
				queryBuffer.append(RegistryObjectQueryParser.parse(query.getHasCreatorPersonQuery(),"hasCreator"));
			}
			if (query.getHasCreatorOrganizationQuery()!=null){
				queryBuffer.append(RegistryObjectQueryParser.parse(query.getHasCreatorOrganizationQuery(),"hasCreator"));
			}
			if (query.getHasContributorPersonQuery()!=null){
				queryBuffer.append(RegistryObjectQueryParser.parse(query.getHasContributorPersonQuery(),"hasContributor"));
			}
			if (query.getHasContributorOrganizationQuery()!=null){
				queryBuffer.append(RegistryObjectQueryParser.parse(query.getHasContributorOrganizationQuery(),"hasContributor"));
			}
			if (query.getEndorsedByPersonQuery()!=null){
				queryBuffer.append(RegistryObjectQueryParser.parse(query.getEndorsedByPersonQuery(),"endorsedBy"));
			}
			if (query.getEndorsedByOrganizationQuery()!=null){
				queryBuffer.append(RegistryObjectQueryParser.parse(query.getEndorsedByOrganizationQuery(),"endorsedBy"));
			}
			if (query.getUsedOntologyEngineeringToolQuery()!=null){
				queryBuffer.append(RegistryObjectQueryParser.parse(query.getUsedOntologyEngineeringToolQuery(),"usedOntologyEngineeringTool"));
			}
			if (query.getUsedOntologyEngineeringMethodologyQuery()!=null){
				queryBuffer.append(RegistryObjectQueryParser.parse(query.getUsedOntologyEngineeringMethodologyQuery(),"usedOntologyEngineeringMethodology"));
			}
			if (query.getUsedKnowledgeRepresentationParadigmQuery()!=null){
				queryBuffer.append(RegistryObjectQueryParser.parse(query.getUsedKnowledgeRepresentationParadigmQuery(),"usedKnowledgeRepresentationParadigm"));
			}
			if (query.getDesignedForOntologyTaskQuery()!=null){
				queryBuffer.append(RegistryObjectQueryParser.parse(query.getDesignedForOntologyTaskQuery(),"designedForOntologyTask"));
			}
			if (query.getHasDomainQuery()!=null){
				queryBuffer.append(RegistryObjectQueryParser.parse(query.getHasDomainQuery(),"hasDomain"));
			}
			if (query.getUseImportsQuery()!=null){
				queryBuffer.append(RegistryObjectQueryParser.parse(query.getUseImportsQuery(),"useImports"));
			}
			if (query.getIsBackwardCompatibleWithQuery()!=null){
				queryBuffer.append(RegistryObjectQueryParser.parse(query.getIsBackwardCompatibleWithQuery(),"isBackwardCompatibleWith"));
			}
			if (query.getIsIncompatibleWithQuery()!=null){
				queryBuffer.append(RegistryObjectQueryParser.parse(query.getIsIncompatibleWithQuery(),"isBackwardCompatibleWith"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//queryBuffer.append("}");
		result.queryPredicate=queryBuffer.toString();
		return result;
	}

}
