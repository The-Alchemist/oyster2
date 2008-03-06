package org.neon_toolkit.registry.api;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.neon_toolkit.omv.api.core.OMVParty;
import org.neon_toolkit.omv.api.core.OMVPerson;
import org.neon_toolkit.registry.oyster2.Constants;
import org.semanticweb.kaon2.api.Entity;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Namespaces;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.owl.elements.DataProperty;
import org.semanticweb.kaon2.api.owl.elements.Individual;
import org.semanticweb.kaon2.api.owl.elements.ObjectProperty;

/**
 * The class ProcessOMVIndividuals provides the methods to
 * create OMV objects  
 * representing the result of a query 
 * @author Raul Palma
 * @version 1.0, March 2008
 */
public class ProcessOMVIndividuals{
	static LinkedList<Individual> onProcess = new LinkedList<Individual>();
   
    static public Object processIndividual(Individual ontoIndiv, String whichClass, Ontology ontologySearch){
	 OMVOntology mainOntoReply=null;
	 OMVParty partyReply=null;
	 OMVOntologyEngineeringTool ontoEngToolReply=null;
	 OMVOntologyEngineeringMethodology ontoEngMetReply=null;
	 OMVKnowledgeRepresentationParadigm krParadigmReply=null;
	 OMVOntologyDomain oDomainReply=null;
	 OMVOntologyType oTypeReply=null;
	 OMVOntologyTask oTaskReply=null;
	 OMVOntologyLanguage oLanguageReply=null;
	 OMVOntologySyntax oSyntaxReply=null;
	 OMVFormalityLevel fLevelReply=null;
	 OMVLicenseModel lModelReply=null;
	 OMVLocation locationReply=null;
	 OMVPerson personReply=null;
	 OMVOrganisation organisationReply=null;
	 //OMVOntology oReferencesReply=null;
	 //OMVOntologyDomain oDomainReplySub=null;
	 if (!onProcess.contains(ontoIndiv)){
	  onProcess.add(ontoIndiv);  
	  try{
		Map dataPropertyMap = ontoIndiv.getDataPropertyValues(ontologySearch);
		Map objectPropertyMap = ontoIndiv.getObjectPropertyValues(ontologySearch);
		if ((dataPropertyMap.size()+objectPropertyMap.size())>0){
			
			if (whichClass.equalsIgnoreCase("ontology")) mainOntoReply = new OMVOntology();
			else if (whichClass.equalsIgnoreCase("party")) partyReply = new OMVParty();
			else if (whichClass.equalsIgnoreCase("ontoEngTool")) ontoEngToolReply = new OMVOntologyEngineeringTool();
			else if (whichClass.equalsIgnoreCase("ontoEngMet")) ontoEngMetReply = new OMVOntologyEngineeringMethodology();
			else if (whichClass.equalsIgnoreCase("krParadigm")) krParadigmReply = new OMVKnowledgeRepresentationParadigm();
			else if (whichClass.equalsIgnoreCase("oDomain")) oDomainReply = new OMVOntologyDomain();
			else if (whichClass.equalsIgnoreCase("oType")) oTypeReply = new OMVOntologyType();
			else if (whichClass.equalsIgnoreCase("oTask")) oTaskReply = new OMVOntologyTask();
			else if (whichClass.equalsIgnoreCase("oLanguage")) oLanguageReply = new OMVOntologyLanguage();
			else if (whichClass.equalsIgnoreCase("oSyntax")) oSyntaxReply = new OMVOntologySyntax();
			else if (whichClass.equalsIgnoreCase("fLevel")) fLevelReply = new OMVFormalityLevel();
			else if (whichClass.equalsIgnoreCase("lModel")) lModelReply = new OMVLicenseModel();
			else if (whichClass.equalsIgnoreCase("location")) locationReply = new OMVLocation();
			else if (whichClass.equalsIgnoreCase("person")) personReply = new OMVPerson();
			else if (whichClass.equalsIgnoreCase("organisation")) organisationReply = new OMVOrganisation();
			//else if (whichClass.equalsIgnoreCase("oReferences")) oReferencesReply = new OMVOntology();
			//else if (whichClass.equalsIgnoreCase("oDomainSub")) oDomainReplySub = new OMVOntologyDomain();
			
			Collection keySet = dataPropertyMap.keySet();
			Iterator keys = keySet.iterator();
			while(keys.hasNext()){
				String keyStr = keys.next().toString();
				DataProperty property = KAON2Manager.factory().dataProperty(keyStr);
				/*15*/
				Collection propertyCol= new LinkedList();
				propertyCol = (Collection)dataPropertyMap.get(property);
				if(propertyCol==null)System.err.println("datapropertyCol is null");
				Iterator itInt = propertyCol.iterator();
				while(itInt.hasNext()){
					Object propertyObject =(Object) itInt.next();
					String	propertyValue = org.neon_toolkit.registry.util.Utilities.getString(propertyObject);
					//System.out.println("property: "+property.getURI()+" VALUE= "+propertyValue);
					if (whichClass.equalsIgnoreCase("ontology")) mainOntoReply.append(createOMVOntology(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("party")) partyReply.append(createOMVParty(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("ontoEngTool")) ontoEngToolReply.append(createOMVOntologyEngineeringTool(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("ontoEngMet")) ontoEngMetReply.append(createOMVOntologyEngineeringMethodology(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("krParadigm")) krParadigmReply.append(createOMVKnowledgeRepresentationParadigm(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("oDomain")) oDomainReply.append(createOMVOntologyDomain(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("oType")) oTypeReply.append(createOMVOntologyType(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("oTask")) oTaskReply.append(createOMVOntologyTask(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("oLanguage")) oLanguageReply.append(createOMVOntologyLanguage(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("oSyntax")) oSyntaxReply.append(createOMVOntologySyntax(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("fLevel")) fLevelReply.append(createOMVFormalityLevel(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("lModel")) lModelReply.append(createOMVLicenseModel(property.getURI(),propertyValue, ontologySearch));				
					else if (whichClass.equalsIgnoreCase("location")) locationReply.append(createOMVLocation(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("person")) personReply.append(createOMVPerson(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("organisation")) organisationReply.append(createOMVOrganisation(property.getURI(),propertyValue, ontologySearch));
				}
				
				//System.out.println("The property "+property.getURI()+" has value: "+propertyValue);
			}
			keySet = objectPropertyMap.keySet();
			Iterator okeys = keySet.iterator();
			while(okeys.hasNext()){
				String keyStr = okeys.next().toString();
				ObjectProperty property = KAON2Manager.factory().objectProperty(keyStr);
				Collection propertyCol= new LinkedList();
				propertyCol = (Collection)objectPropertyMap.get(property);
				if(propertyCol==null)System.err.println("objectpropertyCol is null");
				Iterator itInt = propertyCol.iterator();
				while(itInt.hasNext()){
					Entity propertyValue =(Entity) itInt.next();
						
					if (whichClass.equalsIgnoreCase("ontology")) mainOntoReply.append(createOMVOntology(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("party")) partyReply.append(createOMVParty(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("ontoEngTool")) ontoEngToolReply.append(createOMVOntologyEngineeringTool(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("ontoEngMet")) ontoEngMetReply.append(createOMVOntologyEngineeringMethodology(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("krParadigm")) krParadigmReply.append(createOMVKnowledgeRepresentationParadigm(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("oDomain")) oDomainReply.append(createOMVOntologyDomain(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("oType")) oTypeReply.append(createOMVOntologyType(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("oTask")) oTaskReply.append(createOMVOntologyTask(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("oLanguage")) oLanguageReply.append(createOMVOntologyLanguage(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("oSyntax")) oSyntaxReply.append(createOMVOntologySyntax(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("fLevel")) fLevelReply.append(createOMVFormalityLevel(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("lModel")) lModelReply.append(createOMVLicenseModel(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("location")) locationReply.append(createOMVLocation(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("person")) personReply.append(createOMVPerson(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("organisation")) organisationReply.append(createOMVOrganisation(property.getURI(),propertyValue.getURI(), ontologySearch));
					//else if (whichClass.equalsIgnoreCase("oReferences")) oReferencesReply.append(createOMVOntologyReferences(property.getURI(),propertyValue.getURI()));
					//else if (whichClass.equalsIgnoreCase("oDomainSub")) oDomainReplySub.append(createOMVOntologyDomainSub(property.getURI(),propertyValue.getURI()));
					
					//System.out.println("The property "+property.getURI()+" has value: "+propertyValue);	
				}	
			}
		}
	  }catch(Exception e){
			System.out.println(e.getMessage()+" "+e.getCause()+" "+e.getStackTrace()+" "+e.toString()+" Search Problem in processIndividual");
	  }
	  onProcess.remove(ontoIndiv);
	  if (whichClass.equalsIgnoreCase("ontology")) return mainOntoReply;
	  else if (whichClass.equalsIgnoreCase("party")) return partyReply;
	  else if (whichClass.equalsIgnoreCase("ontoEngTool")) return ontoEngToolReply;
	  else if (whichClass.equalsIgnoreCase("ontoEngMet")) return ontoEngMetReply;
	  else if (whichClass.equalsIgnoreCase("krParadigm")) return krParadigmReply;
	  else if (whichClass.equalsIgnoreCase("oDomain")) return oDomainReply;
	  else if (whichClass.equalsIgnoreCase("oType")) return oTypeReply;
	  else if (whichClass.equalsIgnoreCase("oTask")) return oTaskReply;
	  else if (whichClass.equalsIgnoreCase("oLanguage")) return oLanguageReply;
	  else if (whichClass.equalsIgnoreCase("oSyntax")) return oSyntaxReply;
	  else if (whichClass.equalsIgnoreCase("fLevel")) return fLevelReply;
	  else if (whichClass.equalsIgnoreCase("lModel")) return lModelReply;
	  else if (whichClass.equalsIgnoreCase("location")) return locationReply;
	  else if (whichClass.equalsIgnoreCase("person")) return personReply;
	  else if (whichClass.equalsIgnoreCase("organisation")) return organisationReply;
	  //else if (whichClass.equalsIgnoreCase("oReferences")) return oReferencesReply;
	  //else if (whichClass.equalsIgnoreCase("oDomainSub")) return oDomainReplySub;
	  else return null;
	 }
	 else{
		if (whichClass.equalsIgnoreCase("ontology")) {
			mainOntoReply = new OMVOntology();
			//mainOntoReply.setURI(ontoIndiv.getURI());
			
			//Manage composite OMV identification//
			String localURI=ontoIndiv.getURI();
			Pattern p = Pattern.compile("[? ;]+");
			String[] result =  p.split(localURI);
			for (int i=0; i<result.length; i++){
	            if(result[i].indexOf("version=")!=-1){
	            	mainOntoReply.setVersion(result[i].substring(8, result[i].length())); //ERROR DELETED -1 FIXED FOR VERSION 0.99
	            }
	            else if(result[i].indexOf("location=")!=-1){
	            	mainOntoReply.setResourceLocator(result[i].substring(9, result[i].length()));
	            }
	            else{
	            	mainOntoReply.setURI(result[i]);
	            }
			}
			return mainOntoReply;
		}
		else if (whichClass.equalsIgnoreCase("party")) {return null;}
		else if (whichClass.equalsIgnoreCase("ontoEngTool")) {ontoEngToolReply = new OMVOntologyEngineeringTool();ontoEngToolReply.setName(Namespaces.guessLocalName(ontoIndiv.getURI()));return ontoEngToolReply;}
		else if (whichClass.equalsIgnoreCase("ontoEngMet")) {ontoEngMetReply = new OMVOntologyEngineeringMethodology();ontoEngMetReply.setName(Namespaces.guessLocalName(ontoIndiv.getURI()));return ontoEngMetReply;}
		else if (whichClass.equalsIgnoreCase("krParadigm")) {krParadigmReply = new OMVKnowledgeRepresentationParadigm();krParadigmReply.setName(Namespaces.guessLocalName(ontoIndiv.getURI()));return krParadigmReply;}
		else if (whichClass.equalsIgnoreCase("oDomain")) {oDomainReply = new OMVOntologyDomain();oDomainReply.setURI(ontoIndiv.getURI());return oDomainReply;}
		else if (whichClass.equalsIgnoreCase("oType")) {oTypeReply = new OMVOntologyType();oTypeReply.setName(Namespaces.guessLocalName(ontoIndiv.getURI()));return oTypeReply;}
		else if (whichClass.equalsIgnoreCase("oTask")) {oTaskReply = new OMVOntologyTask();oTaskReply.setName(Namespaces.guessLocalName(ontoIndiv.getURI()));return oTaskReply;}
		else if (whichClass.equalsIgnoreCase("oLanguage")) {oLanguageReply = new OMVOntologyLanguage();oLanguageReply.setName(Namespaces.guessLocalName(ontoIndiv.getURI()));return oLanguageReply;}
		else if (whichClass.equalsIgnoreCase("oSyntax")) {oSyntaxReply = new OMVOntologySyntax();oSyntaxReply.setName(Namespaces.guessLocalName(ontoIndiv.getURI()));return oSyntaxReply;}
		else if (whichClass.equalsIgnoreCase("fLevel")) {fLevelReply = new OMVFormalityLevel();fLevelReply.setName(Namespaces.guessLocalName(ontoIndiv.getURI()));return fLevelReply;}
		else if (whichClass.equalsIgnoreCase("lModel")) {lModelReply = new OMVLicenseModel();lModelReply.setName(Namespaces.guessLocalName(ontoIndiv.getURI()));return lModelReply;}
		else if (whichClass.equalsIgnoreCase("location")) {locationReply = new OMVLocation();locationReply.setStreet(Namespaces.guessLocalName(ontoIndiv.getURI()));return locationReply;}
		else if (whichClass.equalsIgnoreCase("person")) {
			personReply = new OMVPerson();
			String localName=Namespaces.guessLocalName(ontoIndiv.getURI());
			Pattern p1 = Pattern.compile("[A-Z]+");
			Matcher m = p1.matcher(localName);
			int sp=0;
			while (m.find()) if (m.start()>0) sp=m.start();
			personReply.setFirstName(localName.substring(0, sp));
			personReply.setLastName(localName.substring(sp, localName.length()));
			return personReply;
		}
		else if (whichClass.equalsIgnoreCase("organisation")) {organisationReply = new OMVOrganisation();organisationReply.setName(Namespaces.guessLocalName(ontoIndiv.getURI()));return organisationReply;}
		//else if (whichClass.equalsIgnoreCase("oReferences")) {oReferencesReply = new OMVOntology();oReferencesReply.setURI(ontoIndiv.getURI());return oReferencesReply;}
		//else if (whichClass.equalsIgnoreCase("oDomainSub")) {oDomainReplySub = new OMVOntologyDomain();oDomainReplySub.setURI(ontoIndiv.getURI());return oDomainReplySub;}
		return null;
	 }
	}
	
	static private OMVOntology createOMVOntology(String URI, String value, Ontology ontologySearch){
	  OMVOntology mainOntoReply=new OMVOntology();
	  try{
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.URI)) {mainOntoReply.setURI(value);return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) { 		//if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {mainOntoReply.setName(value);return mainOntoReply;}
			mainOntoReply.addName(value);
			return mainOntoReply;
		}		
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {mainOntoReply.setAcronym(value);return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {mainOntoReply.setDescription(value);return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) {mainOntoReply.setDocumentation(value);return mainOntoReply;}		
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.keywords)) { 		//if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.keywords)) {mainOntoReply.setKeywords(value);return mainOntoReply;}
			mainOntoReply.addKeywords(value);
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.status)) {mainOntoReply.setStatus(value);return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.creationDate)) {mainOntoReply.setCreationDate(value);return mainOntoReply;}        //DateFormat df = DateFormat.getDateInstance();	
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.modificationDate)) {mainOntoReply.setModificationDate(value);return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasContributor)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party", ontologySearch);
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person", ontologySearch);
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				mainOntoReply.addHasContributor(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation", ontologySearch);
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					mainOntoReply.addHasContributor(organisationReply);
					organisationReply = null;
				}
			}
			partyReply=null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasCreator)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party", ontologySearch);
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person", ontologySearch);
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				mainOntoReply.addHasCreator(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation", ontologySearch);
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					mainOntoReply.addHasCreator(organisationReply);
					organisationReply = null;
				}
			}
			partyReply=null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.usedOntologyEngineeringTool)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyEngineeringTool ontoEngToolReply=(OMVOntologyEngineeringTool)processIndividual(oIndividual, "ontoEngTool", ontologySearch);
			if (ontoEngToolReply!=null) mainOntoReply.addUsedOntologyEngineeringTool(ontoEngToolReply);
			ontoEngToolReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.usedOntologyEngineeringMethodology)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyEngineeringMethodology ontoEngMetReply=(OMVOntologyEngineeringMethodology)processIndividual(oIndividual, "ontoEngMet", ontologySearch);
			if (ontoEngMetReply!=null) mainOntoReply.addUsedOntologyEngineeringMethodology(ontoEngMetReply);
			ontoEngMetReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.usedKnowledgeRepresentationParadigm)){
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVKnowledgeRepresentationParadigm krParadigmReply=(OMVKnowledgeRepresentationParadigm)processIndividual(oIndividual, "krParadigm", ontologySearch);
			if (krParadigmReply!=null) mainOntoReply.addUsedKnowledgeRepresentationParadigm(krParadigmReply);
			krParadigmReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasDomain)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyDomain oDomainReply=(OMVOntologyDomain)processIndividual(oIndividual, "oDomain", ontologySearch);
			if (oDomainReply==null) {
				oDomainReply = new OMVOntologyDomain();
				oDomainReply.setURI(value);
			}
			mainOntoReply.addHasDomain(oDomainReply);
			oDomainReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isOfType)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyType oTypeReply=(OMVOntologyType)processIndividual(oIndividual, "oType", ontologySearch);
			if (oTypeReply!=null) mainOntoReply.setIsOfType(oTypeReply);
			oTypeReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.naturalLanguage)) { 		//if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.naturalLanguage)) {mainOntoReply.setNaturalLanguage(value);return mainOntoReply;}
			mainOntoReply.addNaturalLanguage(value);
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.designedForOntologyTask)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyTask oTaskReply=(OMVOntologyTask)processIndividual(oIndividual, "oTask", ontologySearch);
			if (oTaskReply!=null) mainOntoReply.addDesignedForOntologyTask(oTaskReply);
			oTaskReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasOntologyLanguage)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyLanguage oLanguageReply=(OMVOntologyLanguage)processIndividual(oIndividual, "oLanguage", ontologySearch);
			if (oLanguageReply!=null) mainOntoReply.setHasOntologyLanguage(oLanguageReply);
			oLanguageReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasOntologySyntax)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologySyntax oSyntaxReply=(OMVOntologySyntax)processIndividual(oIndividual, "oSyntax", ontologySearch);
			if (oSyntaxReply!=null) mainOntoReply.setHasOntologySyntax(oSyntaxReply);
			oSyntaxReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasFormalityLevel)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVFormalityLevel fLevelReply=(OMVFormalityLevel)processIndividual(oIndividual, "fLevel", ontologySearch);
			if (fLevelReply!=null) mainOntoReply.setHasFormalityLevel(fLevelReply);
			fLevelReply = null;
			return mainOntoReply;
		}
		//if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.resourceLocator)) {
		//	mainOntoReply.addResourceLocator(value);return mainOntoReply;
		//}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.resourceLocator)) {mainOntoReply.setResourceLocator(value);return mainOntoReply;}
		
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.version)) {mainOntoReply.setVersion(value);return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasLicense)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVLicenseModel lModelReply=(OMVLicenseModel)processIndividual(oIndividual, "lModel", ontologySearch);
			if (lModelReply!=null) mainOntoReply.setHasLicense(lModelReply);
			lModelReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.useImports)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntology oReferencesReply=(OMVOntology)processIndividual(oIndividual, "ontology", ontologySearch); //oReferences
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			mainOntoReply.addUseImports(oReferencesReply);
			oReferencesReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasPriorVersion)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntology oReferencesReply=(OMVOntology)processIndividual(oIndividual, "ontology", ontologySearch); //oReferences
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			mainOntoReply.setHasPriorVersion(oReferencesReply);
			oReferencesReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isBackwardCompatibleWith)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntology oReferencesReply=(OMVOntology)processIndividual(oIndividual, "ontology", ontologySearch); //oReferences
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			mainOntoReply.addIsBackwardCompatibleWith(oReferencesReply);
			oReferencesReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isIncompatibleWith)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntology oReferencesReply=(OMVOntology)processIndividual(oIndividual, "ontology", ontologySearch);  //oReferences
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			mainOntoReply.addIsIncompatibleWith(oReferencesReply);
			oReferencesReply = null;
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.numberOfClasses)) {mainOntoReply.setNumberOfClasses(new Integer(value.substring(1, value.indexOf("\"", 2))));return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.numberOfProperties)) {mainOntoReply.setNumberOfProperties(new Integer(value.substring(1, value.indexOf("\"", 2))));return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.numberOfIndividuals)) {mainOntoReply.setNumberOfIndividuals(new Integer(value.substring(1, value.indexOf("\"", 2))));return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.numberOfAxioms)) {mainOntoReply.setNumberOfAxioms(new Integer(value.substring(1, value.indexOf("\"", 2))));return mainOntoReply;}
		
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.notes)) {mainOntoReply.setNotes(value);return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.keyClasses)){ //if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.keyClasses)) {mainOntoReply.setKeyClasses(value);return mainOntoReply;}
			mainOntoReply.addKeyClasses(value);
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.knownUsage)) { //if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.knownUsage)) {mainOntoReply.setKnownUsage(value);return mainOntoReply;}
			mainOntoReply.addKnownUsage(value);
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.expressiveness)) {mainOntoReply.setExpressiveness(value);return mainOntoReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isConsistentAccordingToReasoner)) 
		{
			if ((value.toString().equalsIgnoreCase("\"true\"^^<xsd:boolean>")))
				mainOntoReply.setIsConsistentAccordingToReasoner(true);
			else
				mainOntoReply.setIsConsistentAccordingToReasoner(false);
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.containsTBox)) 
		{
			if ((value.toString().equalsIgnoreCase("\"true\"^^<xsd:boolean>")))
				mainOntoReply.setContainsTBox(true);
			else
				mainOntoReply.setContainsTBox(false);
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.containsRBox)) 
		{
			if ((value.toString().equalsIgnoreCase("\"true\"^^<xsd:boolean>")))
				mainOntoReply.setContainsRBox(true);
			else
				mainOntoReply.setContainsRBox(false);
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.containsABox)) 
		{
			if ((value.toString().equalsIgnoreCase("\"true\"^^<xsd:boolean>")))
				mainOntoReply.setContainsABox(true);
			else
				mainOntoReply.setContainsABox(false);
			return mainOntoReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.endorsedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party", ontologySearch);
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person", ontologySearch);
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				mainOntoReply.addEndorsedBy(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation", ontologySearch);
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					mainOntoReply.addEndorsedBy(organisationReply);
					organisationReply = null;
				}
			}
			partyReply=null;
			return mainOntoReply;
		}
		
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.timeStamp)) {
			mainOntoReply.setTimeStamp(value);return mainOntoReply;
		}
	  }catch(Exception e){
			System.out.println(e.toString()+" Search Problem in createOMVOntology");
	  }
	  return mainOntoReply;
	}
	
	static private OMVParty createOMVParty(String URI, String value, Ontology ontologySearch){
	  OMVParty partyReply = new OMVParty();	
	  try{
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isLocatedAt)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVLocation locationReply=(OMVLocation)processIndividual(oIndividual, "location", ontologySearch);
			if (locationReply!=null) partyReply.addIsLocatedAt(locationReply);
			locationReply = null;
			return partyReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developesOntologyEngineeringTool)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyEngineeringTool ontoEngToolReply=(OMVOntologyEngineeringTool)processIndividual(oIndividual, "ontoEngTool", ontologySearch);
			if (ontoEngToolReply!=null) partyReply.addDevelopesOntologyEngineeringTool(ontoEngToolReply);
			ontoEngToolReply = null;
			return partyReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developesOntologyLanguage)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyLanguage oLanguageReply=(OMVOntologyLanguage)processIndividual(oIndividual, "oLanguage", ontologySearch);
			if (oLanguageReply!=null) partyReply.addDevelopesOntologyLanguage(oLanguageReply);
			oLanguageReply = null;
			return partyReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developesOntologySyntax)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologySyntax oSyntaxReply=(OMVOntologySyntax)processIndividual(oIndividual, "oSyntax", ontologySearch);
			if (oSyntaxReply!=null) partyReply.addDevelopesOntologySyntax(oSyntaxReply);
			oSyntaxReply = null;
			return partyReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.specifiesKnowledgeRepresentationParadigm)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVKnowledgeRepresentationParadigm krParadigmReply=(OMVKnowledgeRepresentationParadigm)processIndividual(oIndividual, "krParadigm", ontologySearch);
			if (krParadigmReply!=null) partyReply.addSpecifiesKnowledgeRepresentationParadigm(krParadigmReply);
			krParadigmReply = null;
			return partyReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.definesOntologyType)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyType oTypeReply=(OMVOntologyType)processIndividual(oIndividual, "oType", ontologySearch);
			if (oTypeReply!=null) partyReply.addDefinesOntologyType(oTypeReply);
			oTypeReply = null;
			return partyReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developesOntologyEngineeringMethodology)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyEngineeringMethodology ontoEngMetReply=(OMVOntologyEngineeringMethodology)processIndividual(oIndividual, "ontoEngMet", ontologySearch);
			if (ontoEngMetReply!=null) partyReply.addDevelopesOntologyEngineeringMethodology(ontoEngMetReply);
			ontoEngMetReply = null;
			return partyReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.specifiesLicense)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVLicenseModel lModelReply=(OMVLicenseModel)processIndividual(oIndividual, "lModel", ontologySearch);
			if (lModelReply!=null) partyReply.addSpecifiesLicense(lModelReply);
			lModelReply = null;
			return partyReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasAffiliatedParty)) { //now should work//CHANGE IT LIKE OMVOntology
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReplyAff = (OMVParty)processIndividual(oIndividual, "party", ontologySearch);
			if (partyReplyAff!=null) partyReply.addHasAffiliatedParty(partyReplyAff);
			partyReplyAff = null;
			return partyReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.createsOntology))  {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntology oReferencesReply=(OMVOntology)processIndividual(oIndividual, "ontology", ontologySearch); //oReferences
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			partyReply.addCreatesOntology(oReferencesReply);
			oReferencesReply = null;
			return partyReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.contributesToOntology))  {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntology oReferencesReply=(OMVOntology)processIndividual(oIndividual, "ontology", ontologySearch);  //oReferences
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			partyReply.addContributesToOntology(oReferencesReply);
			oReferencesReply = null;
			return partyReply;
		}
	  }catch(Exception e){
			System.out.println(e.toString()+" Search Problem in createOMVParty");
	  }
	  return partyReply;
	}
	
	static private OMVOntologyEngineeringTool createOMVOntologyEngineeringTool(String URI, String value, Ontology ontologySearch){
		OMVOntologyEngineeringTool ontoEngToolReply=new OMVOntologyEngineeringTool();
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {ontoEngToolReply.setName(value); return ontoEngToolReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {ontoEngToolReply.setAcronym(value); return ontoEngToolReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {ontoEngToolReply.setDescription(value); return ontoEngToolReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) {ontoEngToolReply.setDocumentation(value); return ontoEngToolReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party", ontologySearch);
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person", ontologySearch);
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				ontoEngToolReply.addDevelopedBy(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation", ontologySearch);
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					ontoEngToolReply.addDevelopedBy(organisationReply);
					organisationReply = null;
				}
			}
			partyReply = null;
			return ontoEngToolReply;
		}
		return ontoEngToolReply;
	}
	
	static private OMVOntologyEngineeringMethodology createOMVOntologyEngineeringMethodology(String URI, String value, Ontology ontologySearch){
		OMVOntologyEngineeringMethodology ontoEngMetReply=new OMVOntologyEngineeringMethodology();  
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {ontoEngMetReply.setName(value); return ontoEngMetReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {ontoEngMetReply.setAcronym(value); return ontoEngMetReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {ontoEngMetReply.setDescription(value); return ontoEngMetReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) {ontoEngMetReply.setDocumentation(value); return ontoEngMetReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party", ontologySearch);
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person", ontologySearch);
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				ontoEngMetReply.addDevelopedBy(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation", ontologySearch);
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					ontoEngMetReply.addDevelopedBy(organisationReply);
					organisationReply = null;
				}
			}
			partyReply = null;
			return ontoEngMetReply;
		}
		return ontoEngMetReply;
	}
	
	static private OMVKnowledgeRepresentationParadigm createOMVKnowledgeRepresentationParadigm(String URI, String value, Ontology ontologySearch){
		OMVKnowledgeRepresentationParadigm krParadigmReply=new OMVKnowledgeRepresentationParadigm(); 
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {krParadigmReply.setName(value); return krParadigmReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {krParadigmReply.setAcronym(value); return krParadigmReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {krParadigmReply.setDescription(value); return krParadigmReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) {krParadigmReply.setDocumentation(value); return krParadigmReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.specifiedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party", ontologySearch);
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person", ontologySearch);
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				krParadigmReply.addSpecifiedBy(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation", ontologySearch);
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					krParadigmReply.addSpecifiedBy(organisationReply);
					organisationReply = null;
				}
			}
			partyReply = null;
			return krParadigmReply;
		}
		return krParadigmReply;
	}
	
	static private OMVOntologyDomain createOMVOntologyDomain(String URI, String value, Ontology ontologySearch){
		OMVOntologyDomain oDomainReply=new OMVOntologyDomain(); 
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.URI)) {oDomainReply.setURI(value); return oDomainReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {oDomainReply.setName(value); return oDomainReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isSubDomainOf)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologyDomain oDomainReplySub=(OMVOntologyDomain)processIndividual(oIndividual, "oDomain", ontologySearch); //oDomainSub
			if (oDomainReplySub==null) {
				oDomainReplySub = new OMVOntologyDomain();
				oDomainReplySub.setURI(value);
			}
			oDomainReply.addIsSubDomainOf(oDomainReplySub);
			oDomainReplySub = null;
			return oDomainReply;
		}
		return oDomainReply;
	}
				
	static private OMVOntologyType createOMVOntologyType(String URI, String value, Ontology ontologySearch){
		OMVOntologyType oTypeReply=new OMVOntologyType(); 
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {oTypeReply.setName(value); return oTypeReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {oTypeReply.setAcronym(value); return oTypeReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {oTypeReply.setDescription(value); return oTypeReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) {oTypeReply.setDocumentation(value); return oTypeReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.definedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party", ontologySearch);
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person", ontologySearch);
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				oTypeReply.addDefinedBy(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation", ontologySearch);
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					oTypeReply.addDefinedBy(organisationReply);
					organisationReply = null;
				}
			}
			partyReply = null;
			return oTypeReply;
		}
		return oTypeReply;
	}
	
	static private OMVOntologyTask createOMVOntologyTask(String URI, String value, Ontology ontologySearch){
		OMVOntologyTask oTaskReply=new OMVOntologyTask(); 
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {oTaskReply.setName(value); return oTaskReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {oTaskReply.setAcronym(value); return oTaskReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {oTaskReply.setDescription(value); return oTaskReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) {oTaskReply.setDocumentation(value); return oTaskReply;}
		return oTaskReply;
	}
	
	static private OMVOntologyLanguage createOMVOntologyLanguage(String URI, String value, Ontology ontologySearch){
		OMVOntologyLanguage oLanguageReply=new OMVOntologyLanguage(); 
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {oLanguageReply.setName(value); return oLanguageReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {oLanguageReply.setAcronym(value); return oLanguageReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {oLanguageReply.setDescription(value); return oLanguageReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) {oLanguageReply.setDocumentation(value); return oLanguageReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party", ontologySearch);
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person", ontologySearch);
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				oLanguageReply.addDevelopedBy(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation", ontologySearch);
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					oLanguageReply.addDevelopedBy(organisationReply);
					organisationReply = null;
				}
			}
			partyReply = null;
			return oLanguageReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.supportsRepresentationParadigm)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVKnowledgeRepresentationParadigm krParadigmReply=(OMVKnowledgeRepresentationParadigm)processIndividual(oIndividual, "krParadigm", ontologySearch);
			if (krParadigmReply!=null) oLanguageReply.addSupportsRepresentationParadigm(krParadigmReply);
			krParadigmReply = null;
			return oLanguageReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasSyntax)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntologySyntax oSyntaxReply=(OMVOntologySyntax)processIndividual(oIndividual, "oSyntax", ontologySearch);
			if (oSyntaxReply!=null) oLanguageReply.addHasSyntax(oSyntaxReply);
			oSyntaxReply = null;
			return oLanguageReply;
		}
		return oLanguageReply;
	}
	
	static private OMVOntologySyntax createOMVOntologySyntax(String URI, String value, Ontology ontologySearch){
		OMVOntologySyntax oSyntaxReply=new OMVOntologySyntax();
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {oSyntaxReply.setName(value); return oSyntaxReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {oSyntaxReply.setAcronym(value); return oSyntaxReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {oSyntaxReply.setDescription(value); return oSyntaxReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) {oSyntaxReply.setDocumentation(value); return oSyntaxReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.developedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party", ontologySearch);
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person", ontologySearch);
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				oSyntaxReply.addDevelopedBy(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation", ontologySearch);
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					oSyntaxReply.addDevelopedBy(organisationReply);
					organisationReply = null;
				}
			}
			partyReply = null;
			return oSyntaxReply;
		}
		return oSyntaxReply;
	}
	
	static private OMVFormalityLevel createOMVFormalityLevel(String URI, String value, Ontology ontologySearch){
		OMVFormalityLevel fLevelReply=new OMVFormalityLevel(); 
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {fLevelReply.setName(value); return fLevelReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {fLevelReply.setDescription(value); return fLevelReply;}
		return fLevelReply;
	}
	
	static private OMVLicenseModel createOMVLicenseModel(String URI, String value, Ontology ontologySearch){
		OMVLicenseModel lModelReply=new OMVLicenseModel(); 
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {lModelReply.setName(value); return lModelReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {lModelReply.setAcronym(value); return lModelReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.description)) {lModelReply.setDescription(value); return lModelReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.documentation)) {lModelReply.setDocumentation(value); return lModelReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.specifiedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)processIndividual(oIndividual, "party", ontologySearch);
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual, "person", ontologySearch);
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				lModelReply.addSpecifiedBy(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual, "organisation", ontologySearch);
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					lModelReply.addSpecifiedBy(organisationReply);
					organisationReply = null;
				}
			}
			partyReply = null;
			return lModelReply;
		}
		return lModelReply;
	}
	
	static private OMVLocation createOMVLocation(String URI, String value, Ontology ontologySearch){
		OMVLocation locationReply=new OMVLocation(); 
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.state)) {locationReply.setState(value); return locationReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.country)) {locationReply.setCountry(value); return locationReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.city)) {locationReply.setCity(value); return locationReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.street)) {locationReply.setStreet(value); return locationReply;}
		return locationReply;
	}
	
	static private OMVPerson createOMVPerson(String URI, String value, Ontology ontologySearch){
		OMVPerson personReply=new OMVPerson();
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.lastName)) {personReply.setLastName(value); return personReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.firstName)) {personReply.setFirstName(value); return personReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.eMail)) { //if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.eMail)) {personReply.setEMail(value);return personReply;}
			personReply.addEmail(value);
			return personReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.phoneNumber)) { //if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.phoneNumber)) {personReply.setPhoneNumber(value);return personReply;}
			personReply.addPhoneNumber(value);
			return personReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.faxNumber)) { //if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.faxNumber)) {personReply.setFaxNumber(value);return personReply;}
			personReply.addFaxNumber(value);
			return personReply;
		}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.isContactPerson)) {
			Individual oIndividual1 =KAON2Manager.factory().individual(value);
			OMVOrganisation organisationReply=(OMVOrganisation)processIndividual(oIndividual1, "organisation", ontologySearch);
			if (organisationReply!=null) personReply.addIsContactPerson(organisationReply);
			organisationReply = null;
			return personReply;
		}
		return personReply;
	}
	
	static private OMVOrganisation createOMVOrganisation(String URI, String value, Ontology ontologySearch){
		OMVOrganisation organisationReply=new OMVOrganisation(); 
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.name)) {organisationReply.setName(value); return organisationReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.acronym)) {organisationReply.setAcronym(value); return organisationReply;}
		if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasContactPerson)) {
			Individual oIndividual1 =KAON2Manager.factory().individual(value);
			OMVPerson personReply=(OMVPerson)processIndividual(oIndividual1, "person", ontologySearch);
			if (personReply!=null) organisationReply.addHasContactPerson(personReply);
			personReply = null;
			return organisationReply;
		}
		return organisationReply;
	}
		
	static public OMVPerson copyParty2Person(OMVParty paReply,OMVPerson peReply){
		OMVParty partyReply = paReply;
		OMVPerson personReply = peReply;
		Iterator it = partyReply.getIsLocatedAt().iterator();
		while(it.hasNext()){
			OMVLocation t = (OMVLocation)it.next();
			if (t!=null){
				personReply.addIsLocatedAt(t);
			}
		}
		it = partyReply.getDevelopesOntologyEngineeringTool().iterator();
		while(it.hasNext()){
			OMVOntologyEngineeringTool t = (OMVOntologyEngineeringTool)it.next();
			if (t!=null){
				personReply.addDevelopesOntologyEngineeringTool(t);
			}
		}
		it = partyReply.getDevelopesOntologyLanguage().iterator();
		while(it.hasNext()){
			OMVOntologyLanguage t = (OMVOntologyLanguage)it.next();
			if (t!=null){
				personReply.addDevelopesOntologyLanguage(t);
			}
		}
		it = partyReply.getDevelopesOntologySyntax().iterator();
		while(it.hasNext()){
			OMVOntologySyntax t = (OMVOntologySyntax)it.next();
			if (t!=null){
				personReply.addDevelopesOntologySyntax(t);
			}
		}
		it = partyReply.getSpecifiesKnowledgeRepresentationParadigm().iterator();
		while(it.hasNext()){
			OMVKnowledgeRepresentationParadigm t = (OMVKnowledgeRepresentationParadigm)it.next();
			if (t!=null){
				personReply.addSpecifiesKnowledgeRepresentationParadigm(t);
			}
		}
		it = partyReply.getDefinesOntologyType().iterator();
		while(it.hasNext()){
			OMVOntologyType t = (OMVOntologyType)it.next();
			if (t!=null){
				personReply.addDefinesOntologyType(t);
			}
		}
		it = partyReply.getSpecifiesLicense().iterator();
		while(it.hasNext()){
			OMVLicenseModel t = (OMVLicenseModel)it.next();
			if (t!=null){
				personReply.addSpecifiesLicense(t);
			}
		}
		it = partyReply.getHasAffiliatedParty().iterator();
		while(it.hasNext()){
			OMVParty t = (OMVParty)it.next();
			if (t!=null){
				personReply.addHasAffiliatedParty(t);
			}
		}
		it = partyReply.getContributesToOntology().iterator();
		while(it.hasNext()){
			OMVOntology t = (OMVOntology)it.next();
			if (t!=null){
				personReply.addContributesToOntology(t);
			}
		}
		it = partyReply.getCreatesOntology().iterator();
		while(it.hasNext()){
			OMVOntology t = (OMVOntology)it.next();
			if (t!=null){
				personReply.addCreatesOntology(t);
			}
		}
		return personReply;
	}

	static public OMVOrganisation copyParty2Organisation(OMVParty paReply, OMVOrganisation oReply){
		OMVParty partyReply=paReply;
		OMVOrganisation organisationReply=oReply;
		Iterator it = partyReply.getIsLocatedAt().iterator();
		while(it.hasNext()){
			OMVLocation t = (OMVLocation)it.next();
			if (t!=null){
				organisationReply.addIsLocatedAt(t);
			}
		}
		it = partyReply.getDevelopesOntologyEngineeringTool().iterator();
		while(it.hasNext()){
			OMVOntologyEngineeringTool t = (OMVOntologyEngineeringTool)it.next();
			if (t!=null){
				organisationReply.addDevelopesOntologyEngineeringTool(t);
			}
		}
		it = partyReply.getDevelopesOntologyLanguage().iterator();
		while(it.hasNext()){
			OMVOntologyLanguage t = (OMVOntologyLanguage)it.next();
			if (t!=null){
				organisationReply.addDevelopesOntologyLanguage(t);
			}
		}
		it = partyReply.getDevelopesOntologySyntax().iterator();
		while(it.hasNext()){
			OMVOntologySyntax t = (OMVOntologySyntax)it.next();
			if (t!=null){
				organisationReply.addDevelopesOntologySyntax(t);
			}
		}
		it = partyReply.getSpecifiesKnowledgeRepresentationParadigm().iterator();
		while(it.hasNext()){
			OMVKnowledgeRepresentationParadigm t = (OMVKnowledgeRepresentationParadigm)it.next();
			if (t!=null){
				organisationReply.addSpecifiesKnowledgeRepresentationParadigm(t);
			}
		}
		it = partyReply.getDefinesOntologyType().iterator();
		while(it.hasNext()){
			OMVOntologyType t = (OMVOntologyType)it.next();
			if (t!=null){
				organisationReply.addDefinesOntologyType(t);
			}
		}
		it = partyReply.getSpecifiesLicense().iterator();
		while(it.hasNext()){
			OMVLicenseModel t = (OMVLicenseModel)it.next();
			if (t!=null){
				organisationReply.addSpecifiesLicense(t);
			}
		}
		it = partyReply.getHasAffiliatedParty().iterator();
		while(it.hasNext()){
			OMVParty t = (OMVParty)it.next();
			if (t!=null){
				organisationReply.addHasAffiliatedParty(t);
			}
		}
		it = partyReply.getContributesToOntology().iterator();
		while(it.hasNext()){
			OMVOntology t = (OMVOntology)it.next();
			if (t!=null){
				organisationReply.addContributesToOntology(t);
			}
		}
		it = partyReply.getCreatesOntology().iterator();
		while(it.hasNext()){
			OMVOntology t = (OMVOntology)it.next();
			if (t!=null){
				organisationReply.addCreatesOntology(t);
			}
		}
		return organisationReply;
	}

	
}