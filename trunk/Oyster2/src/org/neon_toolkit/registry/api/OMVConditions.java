package org.neon_toolkit.registry.api;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import org.neon_toolkit.omv.api.core.OMVFormalityLevel;
import org.neon_toolkit.omv.api.core.OMVKnowledgeRepresentationParadigm;
import org.neon_toolkit.omv.api.core.OMVLicenseModel;
import org.neon_toolkit.omv.api.core.OMVOntology;
import org.neon_toolkit.omv.api.core.OMVOntologyDomain;
import org.neon_toolkit.omv.api.core.OMVOntologyEngineeringMethodology;
import org.neon_toolkit.omv.api.core.OMVOntologyEngineeringTool;
import org.neon_toolkit.omv.api.core.OMVOntologyLanguage;
import org.neon_toolkit.omv.api.core.OMVOntologySyntax;
import org.neon_toolkit.omv.api.core.OMVOntologyTask;
import org.neon_toolkit.omv.api.core.OMVOntologyType;
import org.neon_toolkit.omv.api.core.OMVOrganisation;
import org.neon_toolkit.omv.api.core.OMVPerson;
import org.neon_toolkit.registry.oyster2.Condition;
import org.neon_toolkit.registry.oyster2.Constants;
import org.neon_toolkit.registry.oyster2.Oyster2Factory;
import org.semanticweb.kaon2.api.KAON2Exception;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Namespaces;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.owl.elements.DataProperty;
import org.semanticweb.kaon2.api.owl.elements.OWLClass;

/**
 * The class OMVConditions provides the methods to
 * retrieve the conditions from OMV objects 
 * representing the conditions of a search
 * @author Raul Palma
 * @version 1.0, March 2008
 */
public class OMVConditions{
	static Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	
	static public LinkedList<Condition> getPersonConditions (OMVPerson o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getFirstName()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.firstName, o.getFirstName(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.firstName, o.getFirstName(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getLastName()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.lastName, o.getLastName(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.lastName, o.getLastName(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getEmail().size()>0) {
			Iterator it = o.getEmail().iterator();
			while(it.hasNext()){
				String nl = (String)it.next();
				Condition condition;
				if (which=="") condition = new Condition(Constants.omvCondition+Constants.eMail, nl, checkDataProperty(Constants.eMail));
				else condition = new Condition(Constants.omvCondition+Constants.eMail, nl, which);
				searchConditions.addFirst(condition);
			}
		}
		if (o.getPhoneNumber().size()>0) {
			Iterator it = o.getPhoneNumber().iterator();
			while(it.hasNext()){
				String nl = (String)it.next();
				Condition condition;
				if (which=="") condition = new Condition(Constants.omvCondition+Constants.phoneNumber, nl, checkDataProperty(Constants.phoneNumber));
				else condition = new Condition(Constants.omvCondition+Constants.phoneNumber, nl, which);
				searchConditions.addFirst(condition);
			}
		}
		if (o.getFaxNumber().size()>0) {
			Iterator it = o.getFaxNumber().iterator();
			while(it.hasNext()){
				String nl = (String)it.next();
				Condition condition;
				if (which=="") condition = new Condition(Constants.omvCondition+Constants.faxNumber, nl, checkDataProperty(Constants.faxNumber));
				else condition = new Condition(Constants.omvCondition+Constants.faxNumber, nl, which);
				searchConditions.addFirst(condition);
			}
		}
		if (o.getIsContactPerson().size()>0) {
			Iterator it = o.getIsContactPerson().iterator();
			while(it.hasNext()){
				OMVOrganisation org = (OMVOrganisation)it.next();
				LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.omvCondition+Constants.isContactPerson);
				searchConditions.addAll(conditionTemp);
			}
		}
		
		return searchConditions;
	}
	
	static public LinkedList<Condition> getOrganisationConditions (OMVOrganisation o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getName()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getAcronym()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getHasContactPerson().size()>0) {
			Iterator it = o.getHasContactPerson().iterator();
			while(it.hasNext()){
				OMVPerson per = (OMVPerson)it.next();
				LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.omvCondition+Constants.hasContactPerson);
				searchConditions.addAll(conditionTemp);
			}
		}
		
		return searchConditions;
	}
	
	static public LinkedList<Condition> getOETConditions (OMVOntologyEngineeringTool o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getName()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getAcronym()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDescription()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), checkDataProperty(Constants.description));
			else condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getDocumentation()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), checkDataProperty(Constants.documentation));
			else condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDevelopedBy().size()>0) {
			Iterator it = o.getDevelopedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.omvCondition+Constants.developedBy);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.omvCondition+Constants.developedBy);
					searchConditions.addAll(conditionTemp);
				}
			}
		}	
		return searchConditions;
	}
	
	static public LinkedList<Condition> getKRPConditions (OMVKnowledgeRepresentationParadigm o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getName()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getAcronym()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDescription()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), checkDataProperty(Constants.description));
			else condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getDocumentation()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), checkDataProperty(Constants.documentation));
			else condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getSpecifiedBy().size()>0) {
			Iterator it = o.getSpecifiedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.omvCondition+Constants.specifiedBy);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.omvCondition+Constants.specifiedBy);
					searchConditions.addAll(conditionTemp);
				}
			}
		}	
		return searchConditions;
	}
	
	static public LinkedList<Condition> getOEMConditions (OMVOntologyEngineeringMethodology o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getName()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getAcronym()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDescription()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), checkDataProperty(Constants.description));
			else condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getDocumentation()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), checkDataProperty(Constants.documentation));
			else condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDevelopedBy().size()>0) {
			Iterator it = o.getDevelopedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.omvCondition+Constants.developedBy);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.omvCondition+Constants.developedBy);
					searchConditions.addAll(conditionTemp);
				}
			}
		}	
		return searchConditions;
	}
	
	static public LinkedList<Condition> getOTConditions (OMVOntologyType o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getName()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getAcronym()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDescription()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), checkDataProperty(Constants.description));
			else condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getDocumentation()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), checkDataProperty(Constants.documentation));
			else condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDefinedBy().size()>0) {
			Iterator it = o.getDefinedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.omvCondition+Constants.definedBy);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.omvCondition+Constants.definedBy);
					searchConditions.addAll(conditionTemp);
				}
			}
		}	
		return searchConditions;
	}
	
	static public LinkedList<Condition> getOLConditions (OMVOntologyLanguage o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getName()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getAcronym()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDescription()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), checkDataProperty(Constants.description));
			else condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getDocumentation()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), checkDataProperty(Constants.documentation));
			else condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDevelopedBy().size()>0) {
			Iterator it = o.getDevelopedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.omvCondition+Constants.developedBy);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.omvCondition+Constants.developedBy);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		if (o.getSupportsRepresentationParadigm().size()>0) {
			Iterator it = o.getSupportsRepresentationParadigm().iterator();
			while(it.hasNext()){
				OMVKnowledgeRepresentationParadigm krp = (OMVKnowledgeRepresentationParadigm)it.next();
				LinkedList<Condition> conditionTemp=getKRPConditions(krp,Constants.omvCondition+Constants.supportsRepresentationParadigm);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getHasSyntax().size()>0) {
			Iterator it = o.getHasSyntax().iterator();
			while(it.hasNext()){
				OMVOntologySyntax osy = (OMVOntologySyntax)it.next();
				LinkedList<Condition> conditionTemp=getOSConditions(osy,Constants.omvCondition+Constants.hasSyntax);
				searchConditions.addAll(conditionTemp);
			}
		}
		return searchConditions;
	}
	
	static public LinkedList<Condition> getOSConditions (OMVOntologySyntax o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getName()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getAcronym()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDescription()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), checkDataProperty(Constants.description));
			else condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getDocumentation()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), checkDataProperty(Constants.documentation));
			else condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDevelopedBy().size()>0) {
			Iterator it = o.getDevelopedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.omvCondition+Constants.developedBy);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.omvCondition+Constants.developedBy);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		
		return searchConditions;
	}
	
	static public LinkedList<Condition> getLMConditions (OMVLicenseModel o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getName()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getAcronym()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDescription()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), checkDataProperty(Constants.description));
			else condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getDocumentation()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), checkDataProperty(Constants.documentation));
			else condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getSpecifiedBy().size()>0) {
			Iterator it = o.getSpecifiedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.omvCondition+Constants.specifiedBy);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.omvCondition+Constants.specifiedBy);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		
		return searchConditions;
	}
	
	static public LinkedList<Condition> getFLConditions (OMVFormalityLevel o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getName()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getDescription()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), checkDataProperty(Constants.description));
			else condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), which); 
			searchConditions.addFirst(condition);
		}
		return searchConditions;
	}
	
	static public LinkedList<Condition> getOTAConditions (OMVOntologyTask o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getName()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getAcronym()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDescription()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), checkDataProperty(Constants.description));
			else condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getDocumentation()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), checkDataProperty(Constants.documentation));
			else condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), which);
			searchConditions.addFirst(condition);
		}
		return searchConditions;
	}
	
	static public LinkedList<Condition> getODConditions (OMVOntologyDomain o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getURI()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.URI, o.getURI(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.URI, o.getURI(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getName()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), checkDataProperty(Constants.name));
			else condition = new Condition(Constants.omvCondition+Constants.name, o.getName(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getIsSubDomainOf().size()>0) {
			Iterator it = o.getIsSubDomainOf().iterator();
			while(it.hasNext()){
				OMVOntologyDomain od = (OMVOntologyDomain)it.next();
				LinkedList<Condition> conditionTemp=getODConditions(od,Constants.omvCondition+Constants.isSubDomainOf);
				searchConditions.addAll(conditionTemp);
				//Condition condition = new Condition(Condition.TOPIC_CONDITION, od.getURI(), checkDataProperty(Constants.hasDomain));
				//searchConditions.addFirst(condition);
			}
		}
		return searchConditions;
	}

	static public LinkedList<Condition> getConditions (OMVOntology o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getURI()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.URI, o.getURI(), checkDataProperty(Constants.URI));
			else condition = new Condition(Constants.omvCondition+Constants.URI, o.getURI(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getName().size()>0) {
			Iterator it = o.getName().iterator();
			while(it.hasNext()){
				String nl = (String)it.next();
				Condition condition;
				if (which=="") condition = new Condition(Constants.omvCondition+Constants.name, nl, checkDataProperty(Constants.name));
				else condition = new Condition(Constants.omvCondition+Constants.name, nl, which);
				searchConditions.addFirst(condition);
			}
		}
		if (o.getAcronym()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), checkDataProperty(Constants.acronym));
			else condition = new Condition(Constants.omvCondition+Constants.acronym, o.getAcronym(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDescription()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), checkDataProperty(Constants.description));
			else condition = new Condition(Constants.omvCondition+Constants.description, o.getDescription(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getDocumentation()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), checkDataProperty(Constants.documentation));
			else condition = new Condition(Constants.omvCondition+Constants.documentation, o.getDocumentation(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getKeywords().size()>0) {
			Iterator it = o.getKeywords().iterator();
			while(it.hasNext()){
				String nl = (String)it.next();
				Condition condition;
				if (which=="") condition = new Condition(Constants.omvCondition+Constants.keywords, nl, checkDataProperty(Constants.keywords));
				else condition = new Condition(Constants.omvCondition+Constants.keywords, nl, which);
				searchConditions.addFirst(condition);
			}
		}
		if (o.getStatus()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.status, o.getStatus(), checkDataProperty(Constants.status));
			else condition = new Condition(Constants.omvCondition+Constants.status, o.getStatus(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getCreationDate()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.creationDate, o.getCreationDate(), checkDataProperty(Constants.creationDate));
			else condition = new Condition(Constants.omvCondition+Constants.creationDate, o.getCreationDate(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getModificationDate()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.modificationDate, o.getModificationDate(), checkDataProperty(Constants.modificationDate));
			else condition = new Condition(Constants.omvCondition+Constants.modificationDate, o.getModificationDate(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getHasContributor().size()>0) {
			Iterator it = o.getHasContributor().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.omvCondition+Constants.hasContributor);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.omvCondition+Constants.hasContributor);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		if (o.getHasCreator().size()>0) {
			Iterator it = o.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.omvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.omvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		if (o.getUsedOntologyEngineeringTool().size()>0) {
			Iterator it = o.getUsedOntologyEngineeringTool().iterator();
			while(it.hasNext()){
				OMVOntologyEngineeringTool oet = (OMVOntologyEngineeringTool)it.next();
				LinkedList<Condition> conditionTemp=getOETConditions(oet,Constants.omvCondition+Constants.usedOntologyEngineeringTool);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getUsedOntologyEngineeringMethodology().size()>0) {
			Iterator it = o.getUsedOntologyEngineeringMethodology().iterator();
			while(it.hasNext()){
				OMVOntologyEngineeringMethodology oem = (OMVOntologyEngineeringMethodology)it.next();
				LinkedList<Condition> conditionTemp=getOEMConditions(oem,Constants.omvCondition+Constants.usedOntologyEngineeringMethodology);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getUsedKnowledgeRepresentationParadigm().size()>0) {
			Iterator it = o.getUsedKnowledgeRepresentationParadigm().iterator();
			while(it.hasNext()){
				OMVKnowledgeRepresentationParadigm krp = (OMVKnowledgeRepresentationParadigm)it.next();
				LinkedList<Condition> conditionTemp=getKRPConditions(krp,Constants.omvCondition+Constants.usedKnowledgeRepresentationParadigm);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getHasDomain().size()>0) {
			Iterator it = o.getHasDomain().iterator();
			while(it.hasNext()){
				OMVOntologyDomain od = (OMVOntologyDomain)it.next();
				LinkedList<Condition> conditionTemp=getODConditions(od,Constants.omvCondition+Constants.hasDomain);
				searchConditions.addAll(conditionTemp);
				//Condition condition = new Condition(Condition.TOPIC_CONDITION, od.getURI(), checkDataProperty(Constants.hasDomain));
				//searchConditions.addFirst(condition);
			}
		}
		if (o.getIsOfType()!=null) {
			OMVOntologyType ot = o.getIsOfType();
			LinkedList<Condition> conditionTemp=getOTConditions(ot,Constants.omvCondition+Constants.isOfType);
			searchConditions.addAll(conditionTemp);
		}
		if (o.getNaturalLanguage().size()>0) {
			Iterator it = o.getNaturalLanguage().iterator();
			while(it.hasNext()){
				String nl = (String)it.next();
				Condition condition;
				if (which=="") condition = new Condition(Constants.omvCondition+Constants.naturalLanguage, nl, checkDataProperty(Constants.naturalLanguage));
				else condition = new Condition(Constants.omvCondition+Constants.naturalLanguage, nl, which);
				searchConditions.addFirst(condition);
			}
		}
		if (o.getDesignedForOntologyTask().size()>0) {
			Iterator it = o.getDesignedForOntologyTask().iterator();
			while(it.hasNext()){
				OMVOntologyTask ota = (OMVOntologyTask)it.next();
				LinkedList<Condition> conditionTemp=getOTAConditions(ota,Constants.omvCondition+Constants.designedForOntologyTask);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getHasOntologyLanguage()!=null) {
			OMVOntologyLanguage ola = o.getHasOntologyLanguage();
			LinkedList<Condition> conditionTemp=getOLConditions(ola,Constants.omvCondition+Constants.hasOntologyLanguage);
			searchConditions.addAll(conditionTemp);
		}
		if (o.getHasOntologySyntax()!=null) {
			OMVOntologySyntax osy = o.getHasOntologySyntax();
			LinkedList<Condition> conditionTemp=getOSConditions(osy,Constants.omvCondition+Constants.hasOntologySyntax);
			searchConditions.addAll(conditionTemp);
		}
		if (o.getHasFormalityLevel()!=null) {
			OMVFormalityLevel fl = o.getHasFormalityLevel();
			LinkedList<Condition> conditionTemp=getFLConditions(fl,Constants.omvCondition+Constants.hasFormalityLevel);
			searchConditions.addAll(conditionTemp);
		}
		if (o.getResourceLocator()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.resourceLocator, o.getResourceLocator(), checkDataProperty(Constants.resourceLocator));
			else condition = new Condition(Constants.omvCondition+Constants.resourceLocator, o.getResourceLocator(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getVersion()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.version, o.getVersion(), checkDataProperty(Constants.version));
			else condition = new Condition(Constants.omvCondition+Constants.version, o.getVersion(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getHasLicense()!=null) {
			OMVLicenseModel lm = o.getHasLicense();
			LinkedList<Condition> conditionTemp=getLMConditions(lm,Constants.omvCondition+Constants.hasLicense);
			searchConditions.addAll(conditionTemp);
		}
		if (o.getUseImports().size()>0) {
			Iterator it = o.getUseImports().iterator();
			while(it.hasNext()){
				OMVOntology otemp = (OMVOntology)it.next();
				LinkedList<Condition> conditionTemp=getConditions(otemp,Constants.omvCondition+Constants.useImports);
				searchConditions.addAll(conditionTemp);
				//Condition condition = new Condition(Constants.omvCondition+Constants.useImports, otemp.getURI(), checkDataProperty(Constants.useImports));
				//searchConditions.addFirst(condition);
			}
		}
		if (o.getHasPriorVersion()!=null) {
			OMVOntology otemp = o.getHasPriorVersion();
			LinkedList<Condition> conditionTemp=getConditions(otemp,Constants.omvCondition+Constants.hasPriorVersion);
			searchConditions.addAll(conditionTemp);
		}
		if (o.getIsBackwardCompatibleWith().size()>0) {
			Iterator it = o.getIsBackwardCompatibleWith().iterator();
			while(it.hasNext()){
				OMVOntology otemp = (OMVOntology)it.next();
				LinkedList<Condition> conditionTemp=getConditions(otemp,Constants.omvCondition+Constants.isBackwardCompatibleWith);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getIsIncompatibleWith().size()>0) {
			Iterator it = o.getIsIncompatibleWith().iterator();
			while(it.hasNext()){
				OMVOntology otemp = (OMVOntology)it.next();
				LinkedList<Condition> conditionTemp=getConditions(otemp,Constants.omvCondition+Constants.isIncompatibleWith);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getNumberOfClasses()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.numberOfClasses, o.getNumberOfClasses().toString(), checkDataProperty(Constants.numberOfClasses));
			else condition = new Condition(Constants.omvCondition+Constants.numberOfClasses, o.getNumberOfClasses().toString(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getNumberOfProperties()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.numberOfProperties, o.getNumberOfProperties().toString(), checkDataProperty(Constants.numberOfProperties));
			else condition = new Condition(Constants.omvCondition+Constants.numberOfProperties, o.getNumberOfProperties().toString(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getNumberOfIndividuals()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.numberOfIndividuals, o.getNumberOfIndividuals().toString(), checkDataProperty(Constants.numberOfIndividuals));
			else condition = new Condition(Constants.omvCondition+Constants.numberOfIndividuals, o.getNumberOfIndividuals().toString(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getNumberOfAxioms()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.numberOfAxioms, o.getNumberOfAxioms().toString(), checkDataProperty(Constants.numberOfAxioms));
			else condition = new Condition(Constants.omvCondition+Constants.numberOfAxioms, o.getNumberOfAxioms().toString(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getNotes()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.notes, o.getNotes(), checkDataProperty(Constants.notes));
			else condition = new Condition(Constants.omvCondition+Constants.notes, o.getNotes(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getKeyClasses().size()>0) {
			Iterator it = o.getKeyClasses().iterator();
			while(it.hasNext()){
				String nl = (String)it.next();
				Condition condition;
				if (which=="") condition = new Condition(Constants.omvCondition+Constants.keyClasses, nl, checkDataProperty(Constants.keyClasses));
				else condition = new Condition(Constants.omvCondition+Constants.keyClasses, nl, which);
				searchConditions.addFirst(condition);
			}
		}
		if (o.getKnownUsage().size()>0) {
			Iterator it = o.getKnownUsage().iterator();
			while(it.hasNext()){
				String nl = (String)it.next();
				Condition condition;
				if (which=="") condition = new Condition(Constants.omvCondition+Constants.knownUsage, nl, checkDataProperty(Constants.knownUsage));
				else condition = new Condition(Constants.omvCondition+Constants.knownUsage, nl, which);
				searchConditions.addFirst(condition);
			}
		}
		if (o.getExpressiveness()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.expressiveness, o.getExpressiveness(), checkDataProperty(Constants.expressiveness));
			else condition = new Condition(Constants.omvCondition+Constants.expressiveness, o.getExpressiveness(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getIsConsistentAccordingToReasoner()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.isConsistentAccordingToReasoner, o.getIsConsistentAccordingToReasoner().toString(), checkDataProperty(Constants.isConsistentAccordingToReasoner));
			else condition = new Condition(Constants.omvCondition+Constants.isConsistentAccordingToReasoner, o.getIsConsistentAccordingToReasoner().toString(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getContainsTBox()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.containsTBox, o.getContainsTBox().toString(), checkDataProperty(Constants.containsTBox));
			else condition = new Condition(Constants.omvCondition+Constants.containsTBox, o.getContainsTBox().toString(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getContainsRBox()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.containsRBox, o.getContainsRBox().toString(), checkDataProperty(Constants.containsRBox));
			else condition = new Condition(Constants.omvCondition+Constants.containsRBox, o.getContainsRBox().toString(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getContainsABox()!=null) {
			Condition condition;
			if (which=="") condition = new Condition(Constants.omvCondition+Constants.containsABox, o.getContainsABox().toString(), checkDataProperty(Constants.containsABox));
			else condition = new Condition(Constants.omvCondition+Constants.containsABox, o.getContainsABox().toString(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getEndorsedBy().size()>0) {
			Iterator it = o.getEndorsedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=getPersonConditions(per,Constants.omvCondition+Constants.endorsedBy);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=getOrganisationConditions(org,Constants.omvCondition+Constants.endorsedBy);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		
		return searchConditions;
	}
	
	static public Boolean checkDataProperty(String propertyName)  {
		
		Ontology resourceTypeOntology = mOyster2.getTypeOntology();
		OWLClass ontologyClass = KAON2Manager.factory().owlClass(Constants.OMV+Constants.DefaultTypeOntologyRoot);
		try{
			/*KAON2 BUG, DOES NOT SUPPORT OWL DL
	         * SHOULD BE DELETED WHEN IT DOES
	         */
			if(propertyName.equals(Constants.name)) return true;
			if(propertyName.equals(Constants.acronym)) return true;
			if(propertyName.equals(Constants.description)) return true;
			if(propertyName.equals(Constants.documentation)) return true;
			if(propertyName.equals(Constants.URI)) return true;
			
			/* UNTIL HERE */
			
			Set<DataProperty> dataProperties=ontologyClass.getDataPropertiesFrom(resourceTypeOntology);
			for (DataProperty dataProperty : dataProperties){
				if(propertyName.equals(Namespaces.guessLocalName(dataProperty.getURI()))) return true; 
			}
		}
	    catch (KAON2Exception e) {
	    	System.err.println(e + " in checkDataProperty()");
	    }
		return false;
	}
	
		
}