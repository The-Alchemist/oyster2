package org.neontoolkit.registry.api.properties;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.core.OMVOrganisation;
import org.neontoolkit.omv.api.core.OMVPerson;
import org.neontoolkit.omv.api.extensions.mapping.OMVMapping;
import org.neontoolkit.omv.api.extensions.mapping.OMVMappingEvidence;
import org.neontoolkit.omv.api.extensions.mapping.OMVMappingMethod;
import org.neontoolkit.omv.api.extensions.mapping.OMVMappingParameter;
import org.neontoolkit.omv.api.extensions.mapping.OMVMappingProperty;
import org.neontoolkit.registry.oyster2.Constants;
import org.neontoolkit.registry.oyster2.ImportOntology;
import org.neontoolkit.registry.oyster2.OntologyProperty;

/**
 * The class MappingProperties provides the methods to
 * retrieve the properties from OMV Mapping objects 
 * representing the object of the registry 
 * @author Raul Palma
 * @version 1.0, March 2008
 */
public class MappingProperties{
	static private ImportOntology IOntology= new ImportOntology();
	
	static public LinkedList getMappingProperties(OMVMapping m){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (m.getURI()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.URI, m.getURI());
			tProperties.addFirst(prop);
		}
		if (m.getCreationDate()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.creationDate, m.getCreationDate());
			tProperties.addFirst(prop);
		}
		if (m.getHasCreator().size()>0) {
			String tURN;
			Iterator it = m.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=OMVProperties.getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0, null);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=OMVProperties.getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1, null);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		if (m.getHasProperty().size()>0) {
			String tURN;
			Iterator it = m.getHasProperty().iterator();
			while(it.hasNext()){
				OMVMappingProperty e = (OMVMappingProperty)it.next();
				if (e.getID()!=null){
					tURN=e.getID();
					tList.clear();
					tList=getPropertiesMPR(e);
					IOntology.addConceptToRegistry(0,tList,14, null);
					OntologyProperty prop = new OntologyProperty(Constants.hasProperty, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m.getUsedMethod()!=null) {
			String tURN;
			Object t = m.getUsedMethod();
			if (m.getUsedMethod().getID()!=null){
				tURN=m.getUsedMethod().getID();
				tList.clear();
				if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm){
					tList=getPropertiesMA((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)t);
					IOntology.addConceptToRegistry(0,tList,15, null);
				}
				else if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod){
					tList=getPropertiesMM((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)t);
					IOntology.addConceptToRegistry(0,tList,16, null);
				}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter){
					tList=getPropertiesMF((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)t);
					IOntology.addConceptToRegistry(0,tList,17, null);
				}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel){
					tList=getPropertiesMP((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)t);
					IOntology.addConceptToRegistry(0,tList,18, null);
				}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence){
					tList=getPropertiesMS((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)t);
					IOntology.addConceptToRegistry(0,tList,19, null);
				}
				OntologyProperty prop = new OntologyProperty(Constants.usedMethod, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m.getHasSourceOntology()!=null) {
			String tURN;
			OMVOntology otemp = m.getHasSourceOntology();
			if (otemp.getURI()!=null){
				tURN=otemp.getURI();
				tList.clear();
				tList=OMVProperties.getProperties(otemp);
				if (otemp.getResourceLocator()!=null){
					IOntology.addImportOntologyToRegistry(tList,0, null);
					tURN=IOntology.getOntologyID(otemp);
				}
				else{
					IOntology.addImportOntologyToRegistry(tList,3, null);
				}
				OntologyProperty prop = new OntologyProperty(Constants.hasSourceOntology, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m.getHasTargetOntology()!=null) {
			String tURN;
			OMVOntology otemp = m.getHasTargetOntology();
			if (otemp.getURI()!=null){
				tURN=otemp.getURI();
				tList.clear();
				tList=OMVProperties.getProperties(otemp);
				if (otemp.getResourceLocator()!=null){
					IOntology.addImportOntologyToRegistry(tList,0, null);
					tURN=IOntology.getOntologyID(otemp);
				}
				else{
					IOntology.addImportOntologyToRegistry(tList,3, null);
				}
				OntologyProperty prop = new OntologyProperty(Constants.hasTargetOntology, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m.getLevel()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.level, m.getLevel());
			tProperties.addFirst(prop);
		}
		if (m.getProcessingTime()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.processingTime, m.getProcessingTime().toString());
			tProperties.addFirst(prop);
		}
		if (m.getPurpose()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.purpose, m.getPurpose());
			tProperties.addFirst(prop);
		}
		if (m.getType()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.type, m.getType());
			tProperties.addFirst(prop);
		}
		return tProperties;
	}
	
	static public LinkedList getPropertiesMPR(OMVMappingProperty m){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (m.getID()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.ID, m.getID());
			tProperties.addFirst(prop);
		}
		if (m.getHasEvidence().size()>0) {
			String tURN;
			Iterator it = m.getHasEvidence().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (((OMVMappingEvidence)t).getID()!=null){
					tURN=((OMVMappingEvidence)t).getID();
					tList.clear();
					if (t instanceof OMVMappingEvidence.OMVMappingArgument){
						tList=getPropertiesEA((OMVMappingEvidence.OMVMappingArgument)t);
						IOntology.addConceptToRegistry(0,tList,20, null);
					}
					else if (t instanceof OMVMappingEvidence.OMVMappingCertificate){
						tList=getPropertiesEC((OMVMappingEvidence.OMVMappingCertificate)t);
						IOntology.addConceptToRegistry(0,tList,21, null);
					}
					else if (t instanceof OMVMappingEvidence.OMVMappingProof){
						tList=getPropertiesEP((OMVMappingEvidence.OMVMappingProof)t);
						IOntology.addConceptToRegistry(0,tList,22, null);
					}
					OntologyProperty prop = new OntologyProperty(Constants.hasEvidence, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		return tProperties;
	}
	
	static public LinkedList getPropertiesMA(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm m){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (m.getID()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.ID, m.getID());
			tProperties.addFirst(prop);
		}
		if (m.getHasCreator().size()>0) {
			String tURN;
			Iterator it = m.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=OMVProperties.getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0, null);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=OMVProperties.getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1, null);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		if (m.getHasParameter().size()>0) {
			String tURN;
			Iterator it = m.getHasParameter().iterator();
			while(it.hasNext()){
				OMVMappingParameter e = (OMVMappingParameter)it.next();
				if (e.getID()!=null){
					tURN=e.getID();
					tList.clear();
					tList=getPropertiesMPA(e);
					IOntology.addConceptToRegistry(0,tList,23, null);
					OntologyProperty prop = new OntologyProperty(Constants.hasParameter, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m.getSource()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.source, m.getSource());
			tProperties.addFirst(prop);
		}
		if (m.getVersion()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.version, m.getVersion());
			tProperties.addFirst(prop);
		}
		if (m.getNaturalLanguage()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.naturalLanguage, m.getNaturalLanguage());
			tProperties.addFirst(prop);
		}
		return tProperties;
	}
	
	static public LinkedList getPropertiesMM(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod m){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (m.getID()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.ID, m.getID());
			tProperties.addFirst(prop);
		}
		if (m.getHasCreator().size()>0) {
			String tURN;
			Iterator it = m.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=OMVProperties.getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0, null);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=OMVProperties.getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1, null);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		if (m.getHasParameter().size()>0) {
			String tURN;
			Iterator it = m.getHasParameter().iterator();
			while(it.hasNext()){
				OMVMappingParameter e = (OMVMappingParameter)it.next();
				if (e.getID()!=null){
					tURN=e.getID();
					tList.clear();
					tList=getPropertiesMPA(e);
					IOntology.addConceptToRegistry(0,tList,23, null);
					OntologyProperty prop = new OntologyProperty(Constants.hasParameter, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		return tProperties;
	}
	
	static public LinkedList getPropertiesMF(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter m){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (m.getID()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.ID, m.getID());
			tProperties.addFirst(prop);
		}
		if (m.getHasCreator().size()>0) {
			String tURN;
			Iterator it = m.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=OMVProperties.getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0, null);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=OMVProperties.getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1, null);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		if (m.getHasParameter().size()>0) {
			String tURN;
			Iterator it = m.getHasParameter().iterator();
			while(it.hasNext()){
				OMVMappingParameter e = (OMVMappingParameter)it.next();
				if (e.getID()!=null){
					tURN=e.getID();
					tList.clear();
					tList=getPropertiesMPA(e);
					IOntology.addConceptToRegistry(0,tList,23, null);
					OntologyProperty prop = new OntologyProperty(Constants.hasParameter, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m.getValue()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.value, m.getValue().toString());
			tProperties.addFirst(prop);
		}
		if (m.getVariety()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.variety, m.getVariety());
			tProperties.addFirst(prop);
		}
		if (m.getFiltersMethod()!=null) {
			String tURN;
			Object t = m.getFiltersMethod();
			if (m.getFiltersMethod().getID()!=null){
				tURN=m.getFiltersMethod().getID();
				tList.clear();
				if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm){
					tList=getPropertiesMA((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)t);
					IOntology.addConceptToRegistry(0,tList,15, null);
				}
				else if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod){
					tList=getPropertiesMM((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)t);
					IOntology.addConceptToRegistry(0,tList,16, null);
				}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter){
					tList=getPropertiesMF((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)t);
					IOntology.addConceptToRegistry(0,tList,17, null);
				}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel){
					tList=getPropertiesMP((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)t);
					IOntology.addConceptToRegistry(0,tList,18, null);
				}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence){
					tList=getPropertiesMS((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)t);
					IOntology.addConceptToRegistry(0,tList,19, null);
				}
				OntologyProperty prop = new OntologyProperty(Constants.filtersMethod, tURN);
				tProperties.addFirst(prop);
			}
		}
		return tProperties;
	}

	static public LinkedList getPropertiesMP(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel m){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (m.getID()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.ID, m.getID());
			tProperties.addFirst(prop);
		}
		if (m.getHasCreator().size()>0) {
			String tURN;
			Iterator it = m.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=OMVProperties.getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0, null);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=OMVProperties.getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1, null);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		if (m.getHasParameter().size()>0) {
			String tURN;
			Iterator it = m.getHasParameter().iterator();
			while(it.hasNext()){
				OMVMappingParameter e = (OMVMappingParameter)it.next();
				if (e.getID()!=null){
					tURN=e.getID();
					tList.clear();
					tList=getPropertiesMPA(e);
					IOntology.addConceptToRegistry(0,tList,23, null);
					OntologyProperty prop = new OntologyProperty(Constants.hasParameter, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m.getAggregatesMethod().size()>0) {
			String tURN;
			Iterator it = m.getAggregatesMethod().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (((OMVMappingMethod)t).getID()!=null){
					tURN=((OMVMappingMethod)t).getID();
					tList.clear();
					if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm){
						tList=getPropertiesMA((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)t);
						IOntology.addConceptToRegistry(0,tList,15, null);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod){
						tList=getPropertiesMM((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)t);
						IOntology.addConceptToRegistry(0,tList,16, null);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter){
						tList=getPropertiesMF((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)t);
						IOntology.addConceptToRegistry(0,tList,17, null);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel){
						tList=getPropertiesMP((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)t);
						IOntology.addConceptToRegistry(0,tList,18, null);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence){
						tList=getPropertiesMS((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)t);
						IOntology.addConceptToRegistry(0,tList,19, null);
					}
					OntologyProperty prop = new OntologyProperty(Constants.aggregatesMethod, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m.getComposesMethod().size()>0) {
			String tURN;
			Iterator it = m.getComposesMethod().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (((OMVMappingMethod)t).getID()!=null){
					tURN=((OMVMappingMethod)t).getID();
					tList.clear();
					if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm){
						tList=getPropertiesMA((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)t);
						IOntology.addConceptToRegistry(0,tList,15, null);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod){
						tList=getPropertiesMM((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)t);
						IOntology.addConceptToRegistry(0,tList,16, null);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter){
						tList=getPropertiesMF((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)t);
						IOntology.addConceptToRegistry(0,tList,17, null);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel){
						tList=getPropertiesMP((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)t);
						IOntology.addConceptToRegistry(0,tList,18, null);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence){
						tList=getPropertiesMS((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)t);
						IOntology.addConceptToRegistry(0,tList,19, null);
					}
					OntologyProperty prop = new OntologyProperty(Constants.composesMethod, tURN);
					tProperties.addFirst(prop);
				}
			}
		}				
		return tProperties;
	}

	static public LinkedList getPropertiesMS(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence m){
		List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (m.getID()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.ID, m.getID());
			tProperties.addFirst(prop);
		}
		if (m.getHasCreator().size()>0) {
			String tURN;
			Iterator it = m.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=OMVProperties.getPropertiesPerson(per);
						IOntology.addConceptToRegistry(0,tList,0, null);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					if (org.getName()!=null){
						tURN=org.getName();
						tList.clear();
						tList=OMVProperties.getPropertiesOrganisation(org);
						IOntology.addConceptToRegistry(0,tList,1, null);
						OntologyProperty prop = new OntologyProperty(Constants.hasCreator, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		if (m.getHasParameter().size()>0) {
			String tURN;
			Iterator it = m.getHasParameter().iterator();
			while(it.hasNext()){
				OMVMappingParameter e = (OMVMappingParameter)it.next();
				if (e.getID()!=null){
					tURN=e.getID();
					tList.clear();
					tList=getPropertiesMPA(e);
					IOntology.addConceptToRegistry(0,tList,23, null);
					OntologyProperty prop = new OntologyProperty(Constants.hasParameter, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m.getComposesMethod().size()>0) {
			String tURN;
			Iterator it = m.getComposesMethod().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (((OMVMappingMethod)t).getID()!=null){
					tURN=((OMVMappingMethod)t).getID();
					tList.clear();
					if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm){
						tList=getPropertiesMA((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)t);
						IOntology.addConceptToRegistry(0,tList,15, null);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod){
						tList=getPropertiesMM((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)t);
						IOntology.addConceptToRegistry(0,tList,16, null);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter){
						tList=getPropertiesMF((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)t);
						IOntology.addConceptToRegistry(0,tList,17, null);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel){
						tList=getPropertiesMP((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)t);
						IOntology.addConceptToRegistry(0,tList,18, null);
					}
					else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence){
						tList=getPropertiesMS((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)t);
						IOntology.addConceptToRegistry(0,tList,19, null);
					}
					OntologyProperty prop = new OntologyProperty(Constants.composesMethod, tURN);
					tProperties.addFirst(prop);
				}
			}
		}				
		return tProperties;
	}
	
	static public LinkedList getPropertiesEA(OMVMappingEvidence.OMVMappingArgument m){
		//List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (m.getID()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.ID, m.getID());
			tProperties.addFirst(prop);
		}
		return tProperties;
	}
	
	static public LinkedList getPropertiesEC(OMVMappingEvidence.OMVMappingCertificate m){
		//List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (m.getID()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.ID, m.getID());
			tProperties.addFirst(prop);
		}
		return tProperties;
	}
	
	static public LinkedList getPropertiesEP(OMVMappingEvidence.OMVMappingProof m){
		//List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (m.getID()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.ID, m.getID());
			tProperties.addFirst(prop);
		}
		return tProperties;
	}
	
	static public LinkedList getPropertiesMPA(OMVMappingParameter m){
		//List tList = new LinkedList();
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		if (m.getID()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.ID, m.getID());
			tProperties.addFirst(prop);
		}
		return tProperties;
	}

				
}