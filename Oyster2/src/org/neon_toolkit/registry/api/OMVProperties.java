package org.neon_toolkit.registry.api;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.neon_toolkit.omv.api.core.OMVFormalityLevel;
import org.neon_toolkit.omv.api.core.OMVKnowledgeRepresentationParadigm;
import org.neon_toolkit.omv.api.core.OMVLicenseModel;
import org.neon_toolkit.omv.api.core.OMVLocation;
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
import org.neon_toolkit.registry.oyster2.Constants;
import org.neon_toolkit.registry.oyster2.ImportOntology;
import org.neon_toolkit.registry.oyster2.OntologyProperty;

/**
 * The class OMVProperties provides the methods to
 * retrieve the properties from OMV objects 
 * representing the object of the registry 
 * @author Raul Palma
 * @version 1.0, March 2008
 */
public class OMVProperties{
	static private ImportOntology IOntology= new ImportOntology();
	
	static public LinkedList getPropertiesPerson(OMVPerson p){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (p.getFirstName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.firstName, p.getFirstName());
			tProperties.addFirst(prop);
		}
		if (p.getLastName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.lastName, p.getLastName());
			tProperties.addFirst(prop);
		}
		
		if (p.getEmail().size()>0) {
			Iterator it = p.getEmail().iterator();
			while(it.hasNext()){
				String nl = (String)it.next();
				OntologyProperty prop = new OntologyProperty(Constants.eMail, nl);
				tProperties.addFirst(prop);
			}
		}
		if (p.getPhoneNumber().size()>0) {
			Iterator it = p.getPhoneNumber().iterator();
			while(it.hasNext()){
				String nl = (String)it.next();
				OntologyProperty prop = new OntologyProperty(Constants.phoneNumber, nl);
				tProperties.addFirst(prop);
			}
		}
		if (p.getFaxNumber().size()>0) {
			Iterator it = p.getFaxNumber().iterator();
			while(it.hasNext()){
				String nl = (String)it.next();
				OntologyProperty prop = new OntologyProperty(Constants.faxNumber, nl);
				tProperties.addFirst(prop);
			}
		}
		if (p.getIsContactPerson()!=null) {
			String tURN;
			Iterator it = p.getIsContactPerson().iterator();
			while(it.hasNext()){
				OMVOrganisation o = (OMVOrganisation)it.next();
				if (o.getName()!=null){
					tURN=o.getName();
					tList.clear();
					tList=getPropertiesOrganisation(o);
					IOntology.addConceptToRegistry(0,tList,1);
					OntologyProperty prop = new OntologyProperty(Constants.isContactPerson, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (p.getIsLocatedAt()!=null) {
			String tURN;
			Iterator it = p.getIsLocatedAt().iterator();
			while(it.hasNext()){
				OMVLocation l = (OMVLocation)it.next();
				if (l.getStreet()!=null){
					tURN=l.getStreet();
					tList.clear();
					tList=getPropertiesLocation(l);
					IOntology.addConceptToRegistry(0,tList,12);
					OntologyProperty prop = new OntologyProperty(Constants.isLocatedAt, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (p.getDevelopesOntologyEngineeringTool()!=null) {
			String tURN;
			Iterator it = p.getDevelopesOntologyEngineeringTool().iterator();
			while(it.hasNext()){
				OMVOntologyEngineeringTool oet = (OMVOntologyEngineeringTool)it.next();
				if (oet.getName()!=null){
					tURN=oet.getName();
					tList.clear();
					tList=getPropertiesOntologyEngineeringTool(oet);
					IOntology.addConceptToRegistry(0,tList,2);
					OntologyProperty prop = new OntologyProperty(Constants.developesOntologyEngineeringTool, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (p.getDevelopesOntologyLanguage()!=null) {
			String tURN;
			Iterator it = p.getDevelopesOntologyLanguage().iterator();
			while(it.hasNext()){
				OMVOntologyLanguage ola = (OMVOntologyLanguage)it.next();
				if (ola.getName()!=null){
					tURN=ola.getName();
					tList.clear();
					tList=getPropertiesOntologyLanguage(ola);
					IOntology.addConceptToRegistry(0,tList,8);
					OntologyProperty prop = new OntologyProperty(Constants.developesOntologyLanguage, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (p.getDevelopesOntologySyntax()!=null) {
			String tURN;
			Iterator it = p.getDevelopesOntologySyntax().iterator();
			while(it.hasNext()){
				OMVOntologySyntax osy = (OMVOntologySyntax)it.next();
				if (osy.getName()!=null){
					tURN=osy.getName();
					tList.clear();
					tList=getPropertiesOntologySyntax(osy);
					IOntology.addConceptToRegistry(0,tList,9);
					OntologyProperty prop = new OntologyProperty(Constants.developesOntologySyntax, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (p.getSpecifiesKnowledgeRepresentationParadigm()!=null) {
			String tURN;
			Iterator it = p.getSpecifiesKnowledgeRepresentationParadigm().iterator();
			while(it.hasNext()){
				OMVKnowledgeRepresentationParadigm krp = (OMVKnowledgeRepresentationParadigm)it.next();
				if (krp.getName()!=null){
					tURN=krp.getName();
					tList.clear();
					tList=getPropertiesKRParadigm(krp);
					IOntology.addConceptToRegistry(0,tList,4);
					OntologyProperty prop = new OntologyProperty(Constants.specifiesKnowledgeRepresentationParadigm, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (p.getDefinesOntologyType()!=null) {
			String tURN;
			Iterator it = p.getDefinesOntologyType().iterator();
			while(it.hasNext()){
				OMVOntologyType ot = (OMVOntologyType)it.next();
				if (ot.getName()!=null){
					tURN=ot.getName();
					tList.clear();
					tList=getPropertiesOntologyType(ot);
					IOntology.addConceptToRegistry(0,tList,6);
					OntologyProperty prop = new OntologyProperty(Constants.definesOntologyType, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (p.getDevelopesOntologyEngineeringMethodology()!=null) {
			String tURN;
			Iterator it = p.getDevelopesOntologyEngineeringMethodology().iterator();
			while(it.hasNext()){
				OMVOntologyEngineeringMethodology oem = (OMVOntologyEngineeringMethodology)it.next();
				if (oem.getName()!=null){
					tURN=oem.getName();
					tList.clear();
					tList=getPropertiesOntologyEngineeringMethodology(oem);
					IOntology.addConceptToRegistry(0,tList,3);
					OntologyProperty prop = new OntologyProperty(Constants.developesOntologyEngineeringMethodology, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (p.getSpecifiesLicense()!=null) {
			String tURN;
			Iterator it = p.getSpecifiesLicense().iterator();
			while(it.hasNext()){
				OMVLicenseModel lm = (OMVLicenseModel)it.next();
				if (lm.getName()!=null){
					tURN=lm.getName();
					tList.clear();
					tList=getPropertiesLicense(lm);
					IOntology.addConceptToRegistry(0,tList,11);
					OntologyProperty prop = new OntologyProperty(Constants.specifiesLicense, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (p.getHasAffiliatedParty()!=null) {
			String tURN;
			Iterator it = p.getHasAffiliatedParty().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.hasAffiliatedParty, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.hasAffiliatedParty, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		if (p.getContributesToOntology()!=null) {
			String tURN;
			Iterator it = p.getContributesToOntology().iterator();
			while(it.hasNext()){
				OMVOntology otemp = (OMVOntology)it.next();
				if (otemp.getURI()!=null){
					tURN=otemp.getURI();
					tList.clear();
					tList=getProperties(otemp);
					if (otemp.getResourceLocator()!=null){
						IOntology.addImportOntologyToRegistry(tList,0);
					}
					else{
						IOntology.addImportOntologyToRegistry(tList,3);
					}
					OntologyProperty prop = new OntologyProperty(Constants.contributesToOntology, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (p.getCreatesOntology()!=null) {
			String tURN;
			Iterator it = p.getCreatesOntology().iterator();
			while(it.hasNext()){
				OMVOntology otemp = (OMVOntology)it.next();
				if (otemp.getURI()!=null){
					tURN=otemp.getURI();
					tList.clear();
					tList=getProperties(otemp);
					if (otemp.getResourceLocator()!=null){
						IOntology.addImportOntologyToRegistry(tList,0);
					}
					else{
						IOntology.addImportOntologyToRegistry(tList,3);
					}
					OntologyProperty prop = new OntologyProperty(Constants.createsOntology, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		return tProperties;
	}
	
	static public LinkedList getPropertiesOrganisation(OMVOrganisation o){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (o.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, o.getName());
			tProperties.addFirst(prop);
		}
		if (o.getAcronym()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.acronym, o.getAcronym());
			tProperties.addFirst(prop);
		}
		if (o.getHasContactPerson()!=null) {
			String tURN;
			Iterator it = o.getHasContactPerson().iterator();
			while(it.hasNext()){
				OMVPerson p = (OMVPerson)it.next();
				if ((p.getFirstName()!=null) && (p.getLastName()!=null)){
					tURN=p.getFirstName()+p.getLastName();
					tList.clear();
					tList=getPropertiesPerson(p);
					IOntology.addConceptToRegistry(0,tList,0);
					OntologyProperty prop = new OntologyProperty(Constants.hasContactPerson, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (o.getIsLocatedAt()!=null) {
			String tURN;
			Iterator it = o.getIsLocatedAt().iterator();
			while(it.hasNext()){
				OMVLocation l = (OMVLocation)it.next();
				if (l.getStreet()!=null){
					tURN=l.getStreet();
					tList.clear();
					tList=getPropertiesLocation(l);
					IOntology.addConceptToRegistry(0,tList,12);
					OntologyProperty prop = new OntologyProperty(Constants.isLocatedAt, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (o.getDevelopesOntologyEngineeringTool()!=null) {
			String tURN;
			Iterator it = o.getDevelopesOntologyEngineeringTool().iterator();
			while(it.hasNext()){
				OMVOntologyEngineeringTool oet = (OMVOntologyEngineeringTool)it.next();
				if (oet.getName()!=null){
					tURN=oet.getName();
					tList.clear();
					tList=getPropertiesOntologyEngineeringTool(oet);
					IOntology.addConceptToRegistry(0,tList,2);
					OntologyProperty prop = new OntologyProperty(Constants.developesOntologyEngineeringTool, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (o.getDevelopesOntologyLanguage()!=null) {
			String tURN;
			Iterator it = o.getDevelopesOntologyLanguage().iterator();
			while(it.hasNext()){
				OMVOntologyLanguage ola = (OMVOntologyLanguage)it.next();
				if (ola.getName()!=null){
					tURN=ola.getName();
					tList.clear();
					tList=getPropertiesOntologyLanguage(ola);
					IOntology.addConceptToRegistry(0,tList,8);
					OntologyProperty prop = new OntologyProperty(Constants.developesOntologyLanguage, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (o.getDevelopesOntologySyntax()!=null) {
			String tURN;
			Iterator it = o.getDevelopesOntologySyntax().iterator();
			while(it.hasNext()){
				OMVOntologySyntax osy = (OMVOntologySyntax)it.next();
				if (osy.getName()!=null){
					tURN=osy.getName();
					tList.clear();
					tList=getPropertiesOntologySyntax(osy);
					IOntology.addConceptToRegistry(0,tList,9);
					OntologyProperty prop = new OntologyProperty(Constants.developesOntologySyntax, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (o.getSpecifiesKnowledgeRepresentationParadigm()!=null) {
			String tURN;
			Iterator it = o.getSpecifiesKnowledgeRepresentationParadigm().iterator();
			while(it.hasNext()){
				OMVKnowledgeRepresentationParadigm krp = (OMVKnowledgeRepresentationParadigm)it.next();
				if (krp.getName()!=null){
					tURN=krp.getName();
					tList.clear();
					tList=getPropertiesKRParadigm(krp);
					IOntology.addConceptToRegistry(0,tList,4);
					OntologyProperty prop = new OntologyProperty(Constants.specifiesKnowledgeRepresentationParadigm, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (o.getDefinesOntologyType()!=null) {
			String tURN;
			Iterator it = o.getDefinesOntologyType().iterator();
			while(it.hasNext()){
				OMVOntologyType ot = (OMVOntologyType)it.next();
				if (ot.getName()!=null){
					tURN=ot.getName();
					tList.clear();
					tList=getPropertiesOntologyType(ot);
					IOntology.addConceptToRegistry(0,tList,6);
					OntologyProperty prop = new OntologyProperty(Constants.definesOntologyType, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (o.getDevelopesOntologyEngineeringMethodology()!=null) {
			String tURN;
			Iterator it = o.getDevelopesOntologyEngineeringMethodology().iterator();
			while(it.hasNext()){
				OMVOntologyEngineeringMethodology oem = (OMVOntologyEngineeringMethodology)it.next();
				if (oem.getName()!=null){
					tURN=oem.getName();
					tList.clear();
					tList=getPropertiesOntologyEngineeringMethodology(oem);
					IOntology.addConceptToRegistry(0,tList,3);
					OntologyProperty prop = new OntologyProperty(Constants.developesOntologyEngineeringMethodology, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (o.getSpecifiesLicense()!=null) {
			String tURN;
			Iterator it = o.getSpecifiesLicense().iterator();
			while(it.hasNext()){
				OMVLicenseModel lm = (OMVLicenseModel)it.next();
				if (lm.getName()!=null){
					tURN=lm.getName();
					tList.clear();
					tList=getPropertiesLicense(lm);
					IOntology.addConceptToRegistry(0,tList,11);
					OntologyProperty prop = new OntologyProperty(Constants.specifiesLicense, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (o.getHasAffiliatedParty()!=null) {
			String tURN;
			Iterator it = o.getHasAffiliatedParty().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.hasAffiliatedParty, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.hasAffiliatedParty, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		if (o.getContributesToOntology()!=null) {
			String tURN;
			Iterator it = o.getContributesToOntology().iterator();
			while(it.hasNext()){
				OMVOntology otemp = (OMVOntology)it.next();
				if (otemp.getURI()!=null){
					tURN=otemp.getURI();
					tList.clear();
					tList=getProperties(otemp);
					if (otemp.getResourceLocator()!=null){
						IOntology.addImportOntologyToRegistry(tList,0);
					}
					else{
						IOntology.addImportOntologyToRegistry(tList,3);
					}
					OntologyProperty prop = new OntologyProperty(Constants.contributesToOntology, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (o.getCreatesOntology()!=null) {
			String tURN;
			Iterator it = o.getCreatesOntology().iterator();
			while(it.hasNext()){
				OMVOntology otemp = (OMVOntology)it.next();
				if (otemp.getURI()!=null){
					tURN=otemp.getURI();
					tList.clear();
					tList=getProperties(otemp);
					if (otemp.getResourceLocator()!=null){
						IOntology.addImportOntologyToRegistry(tList,0);
					}
					else{
						IOntology.addImportOntologyToRegistry(tList,3);
					}
					OntologyProperty prop = new OntologyProperty(Constants.createsOntology, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		return tProperties;
	}
	
	static public LinkedList getPropertiesOntologyEngineeringTool(OMVOntologyEngineeringTool oet){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (oet.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, oet.getName());
			tProperties.addFirst(prop);
		}
		if (oet.getAcronym()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.acronym, oet.getAcronym());
			tProperties.addFirst(prop);
		}
		if (oet.getDescription()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.description, oet.getDescription());
			tProperties.addFirst(prop);
		}
		if (oet.getDocumentation()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.documentation, oet.getDocumentation());
			tProperties.addFirst(prop);
		}
		if (oet.getDevelopedBy()!=null) {
			String tURN;
			Iterator it = oet.getDevelopedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.developedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.developedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		return tProperties;
	}
	
	static public LinkedList getPropertiesOntologyEngineeringMethodology(OMVOntologyEngineeringMethodology oem){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (oem.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, oem.getName());
			tProperties.addFirst(prop);
		}
		if (oem.getAcronym()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.acronym, oem.getAcronym());
			tProperties.addFirst(prop);
		}
		if (oem.getDescription()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.description, oem.getDescription());
			tProperties.addFirst(prop);
		}
		if (oem.getDocumentation()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.documentation, oem.getDocumentation());
			tProperties.addFirst(prop);
		}
		if (oem.getDevelopedBy()!=null) {
			String tURN;
			Iterator it = oem.getDevelopedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.developedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.developedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		return tProperties;
	}
	
	static public LinkedList getPropertiesKRParadigm(OMVKnowledgeRepresentationParadigm krp){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (krp.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, krp.getName());
			tProperties.addFirst(prop);
		}
		if (krp.getAcronym()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.acronym, krp.getAcronym());
			tProperties.addFirst(prop);
		}
		if (krp.getDescription()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.description, krp.getDescription());
			tProperties.addFirst(prop);
		}
		if (krp.getDocumentation()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.documentation, krp.getDocumentation());
			tProperties.addFirst(prop);
		}
		if (krp.getSpecifiedBy()!=null) {
			String tURN;
			Iterator it = krp.getSpecifiedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.specifiedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.specifiedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		return tProperties;
	}
	
	static public LinkedList getPropertiesOntologyDomain(OMVOntologyDomain od){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (od.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, od.getName());
			tProperties.addFirst(prop);
		}
		if (od.getURI()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.URI, od.getURI());
			tProperties.addFirst(prop);
		}
		if (od.getIsSubDomainOf().size()>0) {
			String tURN;
			Iterator it = od.getIsSubDomainOf().iterator();
			while(it.hasNext()){
				OMVOntologyDomain odSub = (OMVOntologyDomain)it.next();
				if (odSub.getURI()!=null){
					tURN=odSub.getURI();
					if(!tURN.contains("://")){
						odSub.setURI(Constants.TopicsURI+tURN);  //Add namespace if not present
						tURN=odSub.getURI();
					}
					tList.clear();
					tList=getPropertiesOntologyDomain(odSub);
					IOntology.addConceptToRegistry(0,tList,5);
					OntologyProperty prop = new OntologyProperty(Constants.isSubDomainOf, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		return tProperties;
	}
	
	static public LinkedList getPropertiesOntologyType(OMVOntologyType ot){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (ot.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, ot.getName());
			tProperties.addFirst(prop);
		}
		if (ot.getAcronym()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.acronym, ot.getAcronym());
			tProperties.addFirst(prop);
		}
		if (ot.getDescription()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.description, ot.getDescription());
			tProperties.addFirst(prop);
		}
		if (ot.getDocumentation()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.documentation, ot.getDocumentation());
			tProperties.addFirst(prop);
		}
		if (ot.getDefinedBy()!=null) {
			String tURN;
			Iterator it = ot.getDefinedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.specifiedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.specifiedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		return tProperties;
	}
	
	static public LinkedList getPropertiesOntologyTask(OMVOntologyTask ota){
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (ota.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, ota.getName());
			tProperties.addFirst(prop);
		}
		if (ota.getAcronym()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.acronym, ota.getAcronym());
			tProperties.addFirst(prop);
		}
		if (ota.getDescription()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.description, ota.getDescription());
			tProperties.addFirst(prop);
		}
		if (ota.getDocumentation()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.documentation, ota.getDocumentation());
			tProperties.addFirst(prop);
		}
		return tProperties;
	}
	
	static public LinkedList getPropertiesOntologyLanguage(OMVOntologyLanguage ola){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (ola.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, ola.getName());
			tProperties.addFirst(prop);
		}
		if (ola.getAcronym()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.acronym, ola.getAcronym());
			tProperties.addFirst(prop);
		}
		if (ola.getDescription()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.description, ola.getDescription());
			tProperties.addFirst(prop);
		}
		if (ola.getDocumentation()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.documentation, ola.getDocumentation());
			tProperties.addFirst(prop);
		}
		if (ola.getDevelopedBy()!=null) {
			String tURN;
			Iterator it = ola.getDevelopedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.developedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.developedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		if (ola.getSupportsRepresentationParadigm().size()>0) {
			String tURN;
			Iterator it = ola.getSupportsRepresentationParadigm().iterator();
			while(it.hasNext()){
				OMVKnowledgeRepresentationParadigm krp = (OMVKnowledgeRepresentationParadigm)it.next();
				if (krp.getName()!=null){
					tURN=krp.getName();
					tList.clear();
					tList=getPropertiesKRParadigm(krp);
					IOntology.addConceptToRegistry(0,tList,4);
					OntologyProperty prop = new OntologyProperty(Constants.supportsRepresentationParadigm, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (ola.getHasSyntax().size()>0) {
			String tURN;
			Iterator it = ola.getHasSyntax().iterator();
			while(it.hasNext()){
				OMVOntologySyntax osy = (OMVOntologySyntax)it.next();
				if (osy.getName()!=null){
					tURN=osy.getName();
					tList.clear();
					tList=getPropertiesOntologySyntax(osy);
					IOntology.addConceptToRegistry(0,tList,9);
					OntologyProperty prop = new OntologyProperty(Constants.hasSyntax, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		return tProperties;
	}
	
	static public LinkedList getPropertiesOntologySyntax(OMVOntologySyntax osy){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (osy.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, osy.getName());
			tProperties.addFirst(prop);
		}
		if (osy.getAcronym()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.acronym, osy.getAcronym());
			tProperties.addFirst(prop);
		}
		if (osy.getDescription()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.description, osy.getDescription());
			tProperties.addFirst(prop);
		}
		if (osy.getDocumentation()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.documentation, osy.getDocumentation());
			tProperties.addFirst(prop);
		}
		if (osy.getDevelopedBy()!=null) {
			String tURN;
			Iterator it = osy.getDevelopedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.developedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.developedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		return tProperties;
	}
	
	static public LinkedList getPropertiesFormalityLevel(OMVFormalityLevel fl){
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (fl.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, fl.getName());
			tProperties.addFirst(prop);
		}
		if (fl.getDescription()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.description, fl.getDescription());
			tProperties.addFirst(prop);
		}
		return tProperties;
	}
	
	static public LinkedList getPropertiesLicense(OMVLicenseModel lm){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (lm.getName()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.name, lm.getName());
			tProperties.addFirst(prop);
		}
		if (lm.getAcronym()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.acronym, lm.getAcronym());
			tProperties.addFirst(prop);
		}
		if (lm.getDescription()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.description, lm.getDescription());
			tProperties.addFirst(prop);
		}
		if (lm.getDocumentation()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.documentation, lm.getDocumentation());
			tProperties.addFirst(prop);
		}
		if (lm.getSpecifiedBy()!=null) {
			String tURN;
			Iterator it = lm.getSpecifiedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.specifiedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.specifiedBy, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		return tProperties;
	}
	
	static public LinkedList getPropertiesLocation(OMVLocation l){
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (l.getState()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.state, l.getState());
			tProperties.addFirst(prop);
		}
		if (l.getCountry()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.country, l.getCountry());
			tProperties.addFirst(prop);
		}
		if (l.getCity()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.city, l.getCity());
			tProperties.addFirst(prop);
		}
		if (l.getStreet()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.street, l.getStreet());
			tProperties.addFirst(prop);
		}
		
		return tProperties;
	}
	
	static public LinkedList getProperties (OMVOntology o){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> ontoProperties = new LinkedList<OntologyProperty>();
		if (o.getURI()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.URI, o.getURI());
			ontoProperties.addFirst(prop);
		}
		if (o.getName().size()>0) {
			Iterator it = o.getName().iterator();
			while(it.hasNext()){
				String nl = (String)it.next();
				OntologyProperty prop = new OntologyProperty(Constants.name, nl);
				ontoProperties.addFirst(prop);
			}
		}
		if (o.getAcronym()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.acronym, o.getAcronym());
			ontoProperties.addFirst(prop);
		}
		if (o.getDescription()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.description, o.getDescription());
			ontoProperties.addFirst(prop);
		}
		if (o.getDocumentation()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.documentation, o.getDocumentation());
			ontoProperties.addFirst(prop);
		}
		if (o.getKeywords().size()>0) {
			Iterator it = o.getKeywords().iterator();
			while(it.hasNext()){
				String nl = (String)it.next();
				OntologyProperty prop = new OntologyProperty(Constants.keywords, nl);
				ontoProperties.addFirst(prop);
			}
		}
		if (o.getStatus()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.status, o.getStatus());
			ontoProperties.addFirst(prop);
		}
		if (o.getCreationDate()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.creationDate, o.getCreationDate());
			ontoProperties.addFirst(prop);
		}
		if (o.getModificationDate()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.modificationDate, o.getModificationDate());
			ontoProperties.addFirst(prop);
		}
		if (o.getHasContributor().size()>0) {
			String tURN;
			Iterator it = o.getHasContributor().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.hasContributor, tURN);
						ontoProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.hasContributor, tURN);
						ontoProperties.addFirst(prop);
					}
				}
			}
		}
		if (o.getHasCreator().size()>0) {
			String tURN;
			Iterator it = o.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						ontoProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						ontoProperties.addFirst(prop);
					}
				}
			}
		}
		if (o.getUsedOntologyEngineeringTool().size()>0) {
			String tURN;
			Iterator it = o.getUsedOntologyEngineeringTool().iterator();
			while(it.hasNext()){
				OMVOntologyEngineeringTool oet = (OMVOntologyEngineeringTool)it.next();
				if (oet.getName()!=null){
					tURN=oet.getName();
					tList.clear();
					tList=getPropertiesOntologyEngineeringTool(oet);
					IOntology.addConceptToRegistry(0,tList,2);
					OntologyProperty prop = new OntologyProperty(Constants.usedOntologyEngineeringTool, tURN);
					ontoProperties.addFirst(prop);
				}
			}
		}
		if (o.getUsedOntologyEngineeringMethodology().size()>0) {
			String tURN;
			Iterator it = o.getUsedOntologyEngineeringMethodology().iterator();
			while(it.hasNext()){
				OMVOntologyEngineeringMethodology oem = (OMVOntologyEngineeringMethodology)it.next();
				if (oem.getName()!=null){
					tURN=oem.getName();
					tList.clear();
					tList=getPropertiesOntologyEngineeringMethodology(oem);
					IOntology.addConceptToRegistry(0,tList,3);
					OntologyProperty prop = new OntologyProperty(Constants.usedOntologyEngineeringMethodology, tURN);
					ontoProperties.addFirst(prop);
				}
			}
		}
		if (o.getUsedKnowledgeRepresentationParadigm().size()>0) {
			String tURN;
			Iterator it = o.getUsedKnowledgeRepresentationParadigm().iterator();
			while(it.hasNext()){
				OMVKnowledgeRepresentationParadigm krp = (OMVKnowledgeRepresentationParadigm)it.next();
				if (krp.getName()!=null){
					tURN=krp.getName();
					tList.clear();
					tList=getPropertiesKRParadigm(krp);
					IOntology.addConceptToRegistry(0,tList,4);
					OntologyProperty prop = new OntologyProperty(Constants.usedKnowledgeRepresentationParadigm, tURN);
					ontoProperties.addFirst(prop);
				}

			}
		}
		if (o.getHasDomain().size()>0) {
			String tURN;
			Iterator it = o.getHasDomain().iterator();
			while(it.hasNext()){
				OMVOntologyDomain od = (OMVOntologyDomain)it.next();
				if (od.getURI()!=null){
					tURN=od.getURI();
					if(!tURN.contains("://")){
						od.setURI(Constants.TopicsURI+tURN);  //Add namespace if not present
						tURN=od.getURI();
					}
					tList.clear();
					tList=getPropertiesOntologyDomain(od);
					IOntology.addConceptToRegistry(0,tList,5);
					OntologyProperty prop = new OntologyProperty(Constants.hasDomain, tURN);
					ontoProperties.addFirst(prop);
				}
			}
		}
		if (o.getIsOfType()!=null) {
			String tURN;
			OMVOntologyType ot = o.getIsOfType();
			if (ot.getName()!=null){
				tURN=ot.getName();
				tList.clear();
				tList=getPropertiesOntologyType(ot);
				IOntology.addConceptToRegistry(0,tList,6);
				OntologyProperty prop = new OntologyProperty(Constants.isOfType, tURN);
				ontoProperties.addFirst(prop);
			}
		}
		if (o.getNaturalLanguage().size()>0) {
			Iterator it = o.getNaturalLanguage().iterator();
			while(it.hasNext()){
				String nl = (String)it.next();
				OntologyProperty prop = new OntologyProperty(Constants.naturalLanguage, nl);
				ontoProperties.addFirst(prop);
			}
		}
		if (o.getDesignedForOntologyTask().size()>0) {
			String tURN;
			Iterator it = o.getDesignedForOntologyTask().iterator();
			while(it.hasNext()){
				OMVOntologyTask ota = (OMVOntologyTask)it.next();
				if (ota.getName()!=null){
					tURN=ota.getName();
					tList.clear();
					tList=getPropertiesOntologyTask(ota);
					IOntology.addConceptToRegistry(0,tList,7);
					OntologyProperty prop = new OntologyProperty(Constants.designedForOntologyTask, tURN);
					ontoProperties.addFirst(prop);
				}
			}
		}
		if (o.getHasOntologyLanguage()!=null) {
			String tURN;
			OMVOntologyLanguage ole = o.getHasOntologyLanguage();
			if (ole.getName()!=null){
				tURN=ole.getName();
				tList.clear();
				tList=getPropertiesOntologyLanguage(ole);
				IOntology.addConceptToRegistry(0,tList,8);
				OntologyProperty prop = new OntologyProperty(Constants.hasOntologyLanguage, tURN);
				ontoProperties.addFirst(prop);
			}
		}
		if (o.getHasOntologySyntax()!=null) {
			String tURN;
			OMVOntologySyntax osy = o.getHasOntologySyntax();
			if (osy.getName()!=null){
				tURN=osy.getName();
				tList.clear();
				tList=getPropertiesOntologySyntax(osy);
				IOntology.addConceptToRegistry(0,tList,9);
				OntologyProperty prop = new OntologyProperty(Constants.hasOntologySyntax, tURN);
				ontoProperties.addFirst(prop);
			}
			
		}
		if (o.getHasFormalityLevel()!=null) {
			String tURN;
			OMVFormalityLevel fl = o.getHasFormalityLevel();
			if (fl.getName()!=null){
				tURN=fl.getName();
				tList.clear();
				tList=getPropertiesFormalityLevel(fl);
				IOntology.addConceptToRegistry(0,tList,10);
				OntologyProperty prop = new OntologyProperty(Constants.hasFormalityLevel, tURN);
				ontoProperties.addFirst(prop);
			}
		}
		if (o.getResourceLocator()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.resourceLocator, o.getResourceLocator());
			ontoProperties.addFirst(prop);
		}
		if (o.getVersion()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.version, o.getVersion());
			ontoProperties.addFirst(prop);
		}
		if (o.getHasLicense()!=null) {
			String tURN;
			OMVLicenseModel lm = o.getHasLicense();
			if (lm.getName()!=null){
				tURN=lm.getName();
				tList.clear();
				tList=getPropertiesLicense(lm);
				IOntology.addConceptToRegistry(0,tList,11);
				OntologyProperty prop = new OntologyProperty(Constants.hasLicense, tURN);
				ontoProperties.addFirst(prop);
			}
		}
		if (o.getUseImports().size()>0) {
			String tURN;
			Iterator it = o.getUseImports().iterator();
			while(it.hasNext()){
				OMVOntology otemp = (OMVOntology)it.next();
				if (otemp.getURI()!=null){
					tURN=otemp.getURI();
					tList.clear();
					tList=getProperties(otemp);
					if (otemp.getResourceLocator()!=null){
						IOntology.addImportOntologyToRegistry(tList,0);
					}
					else{
						IOntology.addImportOntologyToRegistry(tList,3);
					}
					OntologyProperty prop = new OntologyProperty(Constants.useImports, tURN);
					ontoProperties.addFirst(prop);
				}
			}
		}
		if (o.getHasPriorVersion()!=null) {
			String tURN;
			OMVOntology otemp = o.getHasPriorVersion();
			if (otemp.getURI()!=null){
				tURN=otemp.getURI();
				tList.clear();
				tList=getProperties(otemp);
				if (otemp.getResourceLocator()!=null){
					IOntology.addImportOntologyToRegistry(tList,0);
				}
				else{
					IOntology.addImportOntologyToRegistry(tList,3);
				}
				OntologyProperty prop = new OntologyProperty(Constants.hasPriorVersion, tURN);
				ontoProperties.addFirst(prop);
			}
		}
		if (o.getIsBackwardCompatibleWith().size()>0) {
			String tURN;
			Iterator it = o.getIsBackwardCompatibleWith().iterator();
			while(it.hasNext()){
				OMVOntology otemp = (OMVOntology)it.next();
				if (otemp.getURI()!=null){
					tURN=otemp.getURI();
					tList.clear();
					tList=getProperties(otemp);
					if (otemp.getResourceLocator()!=null){
						IOntology.addImportOntologyToRegistry(tList,0);
					}
					else{
						IOntology.addImportOntologyToRegistry(tList,3);
					}
					OntologyProperty prop = new OntologyProperty(Constants.isBackwardCompatibleWith, tURN);
					ontoProperties.addFirst(prop);
				}
			}
		}
		if (o.getIsIncompatibleWith().size()>0) {
			String tURN;
			Iterator it = o.getIsIncompatibleWith().iterator();
			while(it.hasNext()){
				OMVOntology otemp = (OMVOntology)it.next();
				if (otemp.getURI()!=null){
					tURN=otemp.getURI();
					tList.clear();
					tList=getProperties(otemp);
					if (otemp.getResourceLocator()!=null){
						IOntology.addImportOntologyToRegistry(tList,0);
					}
					else{
						IOntology.addImportOntologyToRegistry(tList,3);
					}
					OntologyProperty prop = new OntologyProperty(Constants.isIncompatibleWith, tURN);
					ontoProperties.addFirst(prop);
				}
			}
		}
		if (o.getNumberOfClasses()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.numberOfClasses, o.getNumberOfClasses().toString());
			ontoProperties.addFirst(prop);
		}
		if (o.getNumberOfProperties()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.numberOfProperties, o.getNumberOfProperties().toString());
			ontoProperties.addFirst(prop);
		}
		if (o.getNumberOfIndividuals()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.numberOfIndividuals, o.getNumberOfIndividuals().toString());
			ontoProperties.addFirst(prop);
		}
		if (o.getNumberOfAxioms()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.numberOfAxioms, o.getNumberOfAxioms().toString());
			ontoProperties.addFirst(prop);
		}
		
		if (o.getNotes()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.notes, o.getNotes());
			ontoProperties.addFirst(prop);
		}
		
		if (o.getKeyClasses().size()>0) {
			Iterator it = o.getKeyClasses().iterator();
			while(it.hasNext()){
				String nl = (String)it.next();
				OntologyProperty prop = new OntologyProperty(Constants.keyClasses, nl);
				ontoProperties.addFirst(prop);
			}
		}
		if (o.getKnownUsage().size()>0) {
			Iterator it = o.getKnownUsage().iterator();
			while(it.hasNext()){
				String nl = (String)it.next();
				OntologyProperty prop = new OntologyProperty(Constants.knownUsage, nl);
				ontoProperties.addFirst(prop);
			}
		}
		if (o.getExpressiveness()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.expressiveness, o.getExpressiveness());
			ontoProperties.addFirst(prop);
		}
		if (o.getIsConsistentAccordingToReasoner()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.isConsistentAccordingToReasoner, o.getIsConsistentAccordingToReasoner().toString());
			ontoProperties.addFirst(prop);
		}
		if (o.getContainsTBox()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.containsTBox, o.getContainsTBox().toString());
			ontoProperties.addFirst(prop);
		}
		if (o.getContainsRBox()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.containsRBox, o.getContainsRBox().toString());
			ontoProperties.addFirst(prop);
		}
		if (o.getContainsABox()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.containsABox, o.getContainsABox().toString());
			ontoProperties.addFirst(prop);
		}
		if (o.getEndorsedBy().size()>0) {
			String tURN;
			Iterator it = o.getEndorsedBy().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0);
						OntologyProperty prop = new OntologyProperty(Constants.endorsedBy, tURN);
						ontoProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1);
						OntologyProperty prop = new OntologyProperty(Constants.endorsedBy, tURN);
						ontoProperties.addFirst(prop);
					}
				}
			}
		}
		return ontoProperties;
	}
	
			
}