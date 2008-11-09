package org.neon_toolkit.registry.omv.service.oyster.lifecyclemanager.from_ebxml.omv;

/*
import javax.xml.registry.JAXRException;
import javax.xml.registry.LifeCycleManager;
import javax.xml.registry.infomodel.RegistryObject;

import org.neon_toolkit.registry.omv.service.centrasite.lifecyclemanager.from_ebxml.ClassificationSubmit;
import org.neon_toolkit.registry.omv.service.centrasite.lifecyclemanager.from_ebxml.InternationalStringSubmit;
import org.neon_toolkit.registry.omv.service.centrasite.lifecyclemanager.from_ebxml.SlotSubmit;
*/
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.neon_toolkit.registry.omv.xsd.rim.OMVObjectRefType;
import org.neon_toolkit.registry.omv.xsd.rim.Ontology_Type;
import org.neontoolkit.omv.api.core.OMVFormalityLevel;
import org.neontoolkit.omv.api.core.OMVKnowledgeRepresentationParadigm;
import org.neontoolkit.omv.api.core.OMVLicenseModel;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.core.OMVOntologyDomain;
import org.neontoolkit.omv.api.core.OMVOntologyEngineeringMethodology;
import org.neontoolkit.omv.api.core.OMVOntologyEngineeringTool;
import org.neontoolkit.omv.api.core.OMVOntologyLanguage;
import org.neontoolkit.omv.api.core.OMVOntologySyntax;
import org.neontoolkit.omv.api.core.OMVOntologyTask;
import org.neontoolkit.omv.api.core.OMVOntologyType;
import org.neontoolkit.omv.api.core.OMVOrganisation;
import org.neontoolkit.omv.api.core.OMVPerson;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringTypeSequence;

public class OntologySubmit extends OMVRegistryObjectSubmit {
	public static Object submit(Ontology_Type input, Object outputIn) {
		
		OMVOntology output;
		if (outputIn==null) output = new OMVOntology();
		else output = ((OMVOntology)outputIn);
		
		if (input.getName()!=null){
			InternationalStringTypeSequence[] templst=input.getName().getInternationalStringTypeSequence();
	   		String tx="";
	   		if (templst!=null){
	   			for (int i=0;i<templst.length;i++) {
			   		tx=templst[i].getLocalizedString().getValue().getFreeFormText();
			   		output.addName(tx);
		   		} 					
	   		}
		}
		if (input.getDescription()!=null){
			InternationalStringTypeSequence[] templst=input.getDescription().getInternationalStringTypeSequence();
			String tx="";
			if (templst!=null){
				for (int i=0;i<templst.length;i++) {
					tx=templst[i].getLocalizedString().getValue().getFreeFormText();
					if (output.getDescription()!=null) tx=output.getDescription()+" "+tx;
					output.setDescription(tx);
				} 					
			}
		}
		if (input.getAcronym()!=null){
			output.setAcronym(input.getAcronym().getShortName());
		}
		if (input.getDocumentation()!=null){
			output.setDocumentation(input.getDocumentation().toString());
		}
	   	if (input.getKeywords()!=null){
			for (int i=0;i<input.getKeywords().length;i++) {
				InternationalStringTypeSequence[] templst=input.getKeywords()[i].getInternationalStringTypeSequence();
				String tx="";
				if (templst!=null){
					for (int j=0;j<templst.length;j++) {
						tx=templst[j].getLocalizedString().getValue().getFreeFormText();
						output.addKeywords(tx);
					} 					
				}
							
			}
		}
		if (input.getNaturalLanguage()!=null){
			for (int i=0;i<input.getNaturalLanguage().length;i++) {
				InternationalStringTypeSequence[] templst=input.getNaturalLanguage()[i].getInternationalStringTypeSequence();
				String tx="";
				if (templst!=null){
					for (int j=0;j<templst.length;j++) {
						tx=templst[j].getLocalizedString().getValue().getFreeFormText();
						output.addNaturalLanguage(tx);
					} 					
				}
			}
		}
		
		if (input.getIsConsistentAccordingToReasonerTracker())
		if (input.getIsConsistentAccordingToReasoner()==true)output.setIsConsistentAccordingToReasoner(true);
		else if (input.getIsConsistentAccordingToReasoner()==false)output.setIsConsistentAccordingToReasoner(false);
		
		if (input.getContainsTBoxTracker())
		if (input.getContainsTBox()==true) output.setContainsTBox(true);
		else if (input.getContainsTBox()==false) output.setContainsTBox(false);
		
		if (input.getContainsABoxTracker())
		if (input.getContainsABox()==true) output.setContainsABox(true);
		else if (input.getContainsABox()==false) output.setContainsABox(false);
		
		if (input.getContainsRBoxTracker())
		if (input.getContainsRBox()==true) output.setContainsRBox(true);
		else if (input.getContainsRBox()==false) output.setContainsRBox(false);
		
		if (input.getKeyClasses()!=null){
			for (int i=0;i<input.getKeyClasses().length;i++) {
				output.addKeyClasses(input.getKeyClasses()[i]);			
			}
		}
		if (input.getKnownUsage()!=null){
			for (int i=0;i<input.getKnownUsage().length;i++) {
				output.addKnownUsage(input.getKnownUsage()[i]);			
			}
		}	
		if (input.getNotes()!=null){
			output.setNotes(input.getNotes());			
		}
		if (input.getExpressiveness()!=null){
			output.setExpressiveness(input.getExpressiveness());			
		}
		if (input.getNumberOfClasses()!=null){
			output.setNumberOfClasses(input.getNumberOfClasses().intValue());
		}
		if (input.getNumberOfProperties()!=null){
			output.setNumberOfProperties(input.getNumberOfProperties().intValue());
		}
		if (input.getNumberOfIndividuals()!=null){
			output.setNumberOfIndividuals(input.getNumberOfIndividuals().intValue());
		}
		if (input.getNumberOfAxioms()!=null){
			output.setNumberOfAxioms(input.getNumberOfAxioms().intValue());
		}
		if (input.getOntologyStatus()!=null){
			output.setStatus(input.getOntologyStatus().getShortName());
		}
		if (input.getCreationDate()!=null){
			DateFormat dateformater = new SimpleDateFormat("yyyy-MM-dd");
			String value=dateformater.format(input.getCreationDate().getTime());
			output.setCreationDate(value);
			
			//DateFormat format = DateFormat.getDateInstance();//.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US);
			//output.setCreationDate(format.format(input.getCreationDate().getTime()));
		}
		if (input.getModificationDate()!=null){
			DateFormat dateformater = new SimpleDateFormat("yyyy-MM-dd");
			String value=dateformater.format(input.getModificationDate().getTime());
			output.setModificationDate(value);
			
			//DateFormat format = DateFormat.getDateInstance();//.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US);
			//output.setModificationDate(format.format(input.getModificationDate().getTime()));
		}
		if (input.getResourceLocator()!=null){
			output.setResourceLocator(input.getResourceLocator().toString());
		}
		if (input.getURI()!=null){
			output.setURI(input.getURI().toString());
		}
		if (input.getVersion()!=null){
			output.setVersion(input.getVersion().getFreeFormText());
		}
		
		
		
		if (input.getIsOfType()!=null){
			OMVOntologyType temp = new OMVOntologyType();
			temp.setName(input.getIsOfType().toString());
			output.setIsOfType(temp);
		}
		if (input.getHasOntologySyntax()!=null){
			OMVOntologySyntax temp = new OMVOntologySyntax();
			temp.setName(input.getHasOntologySyntax().toString());
			output.setHasOntologySyntax(temp);
		}
		if (input.getHasOntologyLanguage()!=null){
			OMVOntologyLanguage temp = new OMVOntologyLanguage();
			temp.setName(input.getHasOntologyLanguage()[0].getId().toString());
			output.setHasOntologyLanguage(temp);
		}
		if (input.getHasLicense()!=null){
			OMVLicenseModel temp = new OMVLicenseModel();
			temp.setName(input.getHasLicense().toString());
			output.setHasLicense(temp);
		}
		if (input.getHasFormalityLevel()!=null){
			OMVFormalityLevel temp = new OMVFormalityLevel();
			temp.setName(input.getHasFormalityLevel().toString());
			output.setHasFormalityLevel(temp);
		}
		if (input.getHasPriorVersion()!=null){
			OMVOntology temp = new OMVOntology();
			temp.setURI(input.getHasPriorVersion().toString());
			output.setHasPriorVersion(temp);
		}
		if (input.getUseImports()!=null){
			for (int i=0;i<input.getUseImports().length;i++) {
				OMVOntology temp = new OMVOntology();
				temp.setURI(input.getUseImports()[i].getId().toString());
				output.addUseImports(temp);			
			}	
		}
		if (input.getIsBackwardCompatibleWith()!=null){
			for (int i=0;i<input.getIsBackwardCompatibleWith().length;i++) {
				OMVOntology temp = new OMVOntology();
				temp.setURI(input.getIsBackwardCompatibleWith()[i].getId().toString());
				output.addIsBackwardCompatibleWith(temp);			
			}
		}
		if (input.getIsIncompatibleWith()!=null){
			for (int i=0;i<input.getIsIncompatibleWith().length;i++) {
				OMVOntology temp = new OMVOntology();
				temp.setURI(input.getIsIncompatibleWith()[i].getId().toString());
				output.addIsIncompatibleWith(temp);			
			}
		}
		if (input.getHasDomain()!=null){
			for (int i=0;i<input.getHasDomain().length;i++) {
				OMVOntologyDomain temp = new OMVOntologyDomain();
				temp.setURI(input.getHasDomain()[i].getId().toString());
				output.addHasDomain(temp);			
			}
		}
		if (input.getDesignedForOntologyTask()!=null){
			for (int i=0;i<input.getDesignedForOntologyTask().length;i++) {
				OMVOntologyTask temp = new OMVOntologyTask();
				temp.setName(input.getDesignedForOntologyTask()[i].getId().toString());
				output.addDesignedForOntologyTask(temp);			
			}
		}
		if (input.getUsedKnowledgeRepresentationParadigm()!=null){
			for (int i=0;i<input.getUsedKnowledgeRepresentationParadigm().length;i++) {
				OMVKnowledgeRepresentationParadigm temp = new OMVKnowledgeRepresentationParadigm();
				temp.setName(input.getUsedKnowledgeRepresentationParadigm()[i].getId().toString());
				output.addUsedKnowledgeRepresentationParadigm(temp);			
			}
		}
		if (input.getUsedOntologyEngineeringMethodology()!=null){
			for (int i=0;i<input.getUsedOntologyEngineeringMethodology().length;i++) {
				OMVOntologyEngineeringMethodology temp = new OMVOntologyEngineeringMethodology();
				temp.setName(input.getUsedOntologyEngineeringMethodology()[i].getId().toString());
				output.addUsedOntologyEngineeringMethodology(temp);			
			}
		}
		if (input.getUsedOntologyEngineeringTool()!=null){
			for (int i=0;i<input.getUsedOntologyEngineeringTool().length;i++) {
				OMVOntologyEngineeringTool temp = new OMVOntologyEngineeringTool();
				temp.setName(input.getUsedOntologyEngineeringTool()[i].getId().toString());
				output.addUsedOntologyEngineeringTool(temp);			
			}
		}
		if (input.getHasContributor()!=null){
			for (int i=0;i<input.getHasContributor().length;i++) {
				String t1=input.getHasContributor()[i].getId().toString();
				Pattern p1 = Pattern.compile("[A-Z]+");
				Matcher m = p1.matcher(t1);
				int sp=0;
				int person=0;
				while (m.find()) {
					if (m.start()>0) {
						sp=m.start();
						person++;
					}
				}
				if (person==1){
					OMVPerson temp = new OMVPerson();
					temp.setFirstName(t1.substring(0, sp));
					temp.setLastName(t1.substring(sp, t1.length()));
					output.addHasContributor(temp);
				}
				else{
					OMVOrganisation temp = new OMVOrganisation();
					temp.setName(t1);
					output.addHasContributor(temp);
				}
			}
		}
		
		if (input.getHasCreator()!=null){
			for (int i=0;i<input.getHasCreator().length;i++) {
				String t1=input.getHasCreator()[i].getId().toString();
				
				Pattern p1 = Pattern.compile("[A-Z]+");
				Matcher m = p1.matcher(t1);
				int sp=0;
				int person=0;
				while (m.find()) {
					if (m.start()>0) {
						sp=m.start();
						person++;
					}
				}
				if (person==1){
					OMVPerson temp = new OMVPerson();
					temp.setFirstName(t1.substring(0, sp));
					temp.setLastName(t1.substring(sp, t1.length()));
					output.addHasCreator(temp);
				}
				else{
					OMVOrganisation temp = new OMVOrganisation();
					temp.setName(t1);
					output.addHasCreator(temp);
				}
			}
		}				
		if (input.getEndorsedBy()!=null){
			for (int i=0;i<input.getEndorsedBy().length;i++) {
				String t1=input.getEndorsedBy()[i].getId().toString();
				Pattern p1 = Pattern.compile("[A-Z]+");
				Matcher m = p1.matcher(t1);
				int sp=0;
				int person=0;
				while (m.find()) {
					if (m.start()>0) {
						sp=m.start();
						person++;
					}
				}
				if (person==1){
					OMVPerson temp = new OMVPerson();
					temp.setFirstName(t1.substring(0, sp));
					temp.setLastName(t1.substring(sp, t1.length()));
					output.addEndorsedBy(temp);
				}
				else{
					OMVOrganisation temp = new OMVOrganisation();
					temp.setName(t1);
					output.addEndorsedBy(temp);
				}
			}
		}				
		
		return output;
	}
}
