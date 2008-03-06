package org.neon_toolkit.registry.api;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import org.neon_toolkit.omv.api.core.OMVOntology;
import org.neon_toolkit.omv.api.core.OMVOrganisation;
import org.neon_toolkit.omv.api.core.OMVPerson;
import org.neon_toolkit.omv.api.extensions.mapping.OMVMapping;
import org.neon_toolkit.omv.api.extensions.mapping.OMVMappingEvidence;
import org.neon_toolkit.omv.api.extensions.mapping.OMVMappingMethod;
import org.neon_toolkit.omv.api.extensions.mapping.OMVMappingParameter;
import org.neon_toolkit.omv.api.extensions.mapping.OMVMappingProperty;
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
 * The class MappingConditions provides the methods to
 * retrieve the conditions from OMV Mapping objects 
 * representing the conditions of a search 
 * @author Raul Palma
 * @version 1.0, March 2008
 */
public class MappingConditions{
	static Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	
	static public LinkedList<Condition> getMappingConditions (OMVMapping o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getURI()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.URI, o.getURI(), checkDataPropertyM(Constants.URI));
			else condition = new Condition(Constants.momvCondition+Constants.URI, o.getURI(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getCreationDate()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.momvCondition+Constants.creationDate, o.getCreationDate(), checkDataPropertyM(Constants.creationDate));
			else condition = new Condition(Constants.momvCondition+Constants.creationDate, o.getCreationDate(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getHasCreator().size()>0) {
			Iterator it = o.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=OMVConditions.getPersonConditions(per,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=OMVConditions.getOrganisationConditions(org,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		if (o.getHasProperty().size()>0) {
			Iterator it = o.getHasProperty().iterator();
			while(it.hasNext()){
				OMVMappingProperty od = (OMVMappingProperty)it.next();
				LinkedList<Condition> conditionTemp=getMPRConditions(od,Constants.momvCondition+Constants.hasProperty);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getUsedMethod()!=null) {
			LinkedList<Condition> conditionTemp=null;
			Object t = o.getUsedMethod();
			if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm){conditionTemp=getMAConditions(((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)t),Constants.momvCondition+Constants.usedMethod);}
			else if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod){conditionTemp=getMMConditions(((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)t),Constants.momvCondition+Constants.usedMethod);}
			else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter){conditionTemp=getMFConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)t),Constants.momvCondition+Constants.usedMethod);}
			else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel){conditionTemp=getMPConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)t),Constants.momvCondition+Constants.usedMethod);}
			else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence){conditionTemp=getMSConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)t),Constants.momvCondition+Constants.usedMethod);}
			searchConditions.addAll(conditionTemp);
		}
		if (o.getHasSourceOntology()!=null) {
			OMVOntology otemp = o.getHasSourceOntology();
			LinkedList<Condition> conditionTemp=OMVConditions.getConditions(otemp,Constants.momvCondition+Constants.hasSourceOntology);
			searchConditions.addAll(conditionTemp);
		}
		if (o.getHasTargetOntology()!=null) {
			OMVOntology otemp = o.getHasTargetOntology();
			LinkedList<Condition> conditionTemp=OMVConditions.getConditions(otemp,Constants.momvCondition+Constants.hasTargetOntology);
			searchConditions.addAll(conditionTemp);
		}
		if (o.getLevel()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.momvCondition+Constants.level, o.getLevel(), checkDataPropertyM(Constants.level));
			else condition = new Condition(Constants.momvCondition+Constants.level, o.getLevel(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getProcessingTime()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.momvCondition+Constants.processingTime, o.getProcessingTime().toString(), checkDataPropertyM(Constants.processingTime));
			else condition = new Condition(Constants.momvCondition+Constants.processingTime, o.getProcessingTime().toString(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getPurpose()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.momvCondition+Constants.purpose, o.getPurpose(), checkDataPropertyM(Constants.purpose));
			else condition = new Condition(Constants.momvCondition+Constants.purpose, o.getPurpose(), which);
			searchConditions.addFirst(condition);
		}
		if (o.getType()!=null) {
			Condition condition;
			if (which=="")condition = new Condition(Constants.momvCondition+Constants.type, o.getType(), checkDataPropertyM(Constants.type));
			else condition = new Condition(Constants.momvCondition+Constants.type, o.getType(), which);
			searchConditions.addFirst(condition);
		}
		return searchConditions;
	}
	
	static public LinkedList<Condition> getMPRConditions (OMVMappingProperty o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getID()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), checkDataPropertyM(Constants.ID));
			else condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getHasEvidence().size()>0) {
			LinkedList<Condition> conditionTemp=null;
			Iterator it = o.getHasEvidence().iterator();
			while(it.hasNext()){
				Object t = o.getHasEvidence();
				if (t instanceof OMVMappingEvidence.OMVMappingArgument){conditionTemp=getEAConditions(((OMVMappingEvidence.OMVMappingArgument)t),Constants.momvCondition+Constants.hasEvidence);}
				else if (t instanceof OMVMappingEvidence.OMVMappingCertificate){conditionTemp=getECConditions(((OMVMappingEvidence.OMVMappingCertificate)t),Constants.momvCondition+Constants.usedMethod);}
				else if (t instanceof OMVMappingEvidence.OMVMappingProof){conditionTemp=getEPConditions(((OMVMappingEvidence.OMVMappingProof)t),Constants.momvCondition+Constants.usedMethod);}
				searchConditions.addAll(conditionTemp);
			}
		}
		return searchConditions;
	}
	
	static public LinkedList<Condition> getMAConditions (OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getID()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), checkDataPropertyM(Constants.ID));
			else condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getHasCreator().size()>0) {
			Iterator it = o.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=OMVConditions.getPersonConditions(per,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=OMVConditions.getOrganisationConditions(org,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		if (o.getHasParameter().size()>0) {
			Iterator it = o.getHasParameter().iterator();
			while(it.hasNext()){
				OMVMappingParameter od = (OMVMappingParameter)it.next();
				LinkedList<Condition> conditionTemp=getMPAConditions(od,Constants.momvCondition+Constants.hasParameter);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getSource()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.source, o.getSource(), checkDataPropertyM(Constants.source));
			else condition = new Condition(Constants.momvCondition+Constants.source, o.getSource(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getVersion()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.version, o.getVersion(), checkDataPropertyM(Constants.version));
			else condition = new Condition(Constants.momvCondition+Constants.version, o.getVersion(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getNaturalLanguage()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.naturalLanguage, o.getNaturalLanguage(), checkDataPropertyM(Constants.naturalLanguage));
			else condition = new Condition(Constants.momvCondition+Constants.naturalLanguage, o.getNaturalLanguage(), which); 
			searchConditions.addFirst(condition);
		}
		return searchConditions;
	}
	
	static public LinkedList<Condition> getMMConditions (OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getID()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), checkDataPropertyM(Constants.ID));
			else condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getHasCreator().size()>0) {
			Iterator it = o.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=OMVConditions.getPersonConditions(per,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=OMVConditions.getOrganisationConditions(org,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		if (o.getHasParameter().size()>0) {
			Iterator it = o.getHasParameter().iterator();
			while(it.hasNext()){
				OMVMappingParameter od = (OMVMappingParameter)it.next();
				LinkedList<Condition> conditionTemp=getMPAConditions(od,Constants.momvCondition+Constants.hasParameter);
				searchConditions.addAll(conditionTemp);
			}
		}
		return searchConditions;
	}
	
	static public LinkedList<Condition> getMFConditions (OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getID()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), checkDataPropertyM(Constants.ID));
			else condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getHasCreator().size()>0) {
			Iterator it = o.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=OMVConditions.getPersonConditions(per,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=OMVConditions.getOrganisationConditions(org,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		if (o.getHasParameter().size()>0) {
			Iterator it = o.getHasParameter().iterator();
			while(it.hasNext()){
				OMVMappingParameter od = (OMVMappingParameter)it.next();
				LinkedList<Condition> conditionTemp=getMPAConditions(od,Constants.momvCondition+Constants.hasParameter);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getVariety()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.variety, o.getVariety(), checkDataPropertyM(Constants.variety));
			else condition = new Condition(Constants.momvCondition+Constants.variety, o.getVariety(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getValue()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.value, o.getValue().toString(), checkDataPropertyM(Constants.value));
			else condition = new Condition(Constants.momvCondition+Constants.value, o.getValue().toString(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getFiltersMethod()!=null) {
			LinkedList<Condition> conditionTemp=null;
			Object t = o.getFiltersMethod();
			if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm){conditionTemp=getMAConditions(((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)t),Constants.momvCondition+Constants.filtersMethod);}
			else if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod){conditionTemp=getMMConditions(((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)t),Constants.momvCondition+Constants.filtersMethod);}
			else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter){conditionTemp=getMFConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)t),Constants.momvCondition+Constants.filtersMethod);}
			else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel){conditionTemp=getMPConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)t),Constants.momvCondition+Constants.filtersMethod);}
			else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence){conditionTemp=getMSConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)t),Constants.momvCondition+Constants.filtersMethod);}
			searchConditions.addAll(conditionTemp);
		}
		return searchConditions;
	}
	
	static public LinkedList<Condition> getMPConditions (OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getID()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), checkDataPropertyM(Constants.ID));
			else condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getHasCreator().size()>0) {
			Iterator it = o.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=OMVConditions.getPersonConditions(per,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=OMVConditions.getOrganisationConditions(org,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		if (o.getHasParameter().size()>0) {
			Iterator it = o.getHasParameter().iterator();
			while(it.hasNext()){
				OMVMappingParameter od = (OMVMappingParameter)it.next();
				LinkedList<Condition> conditionTemp=getMPAConditions(od,Constants.momvCondition+Constants.hasParameter);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getAggregatesMethod().size()>0) {
			Iterator it = o.getAggregatesMethod().iterator();
			while(it.hasNext()){
				LinkedList<Condition> conditionTemp=null;
				Object t = it.next();
				if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm){conditionTemp=getMAConditions(((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)t),Constants.momvCondition+Constants.aggregatesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod){conditionTemp=getMMConditions(((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)t),Constants.momvCondition+Constants.aggregatesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter){conditionTemp=getMFConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)t),Constants.momvCondition+Constants.aggregatesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel){conditionTemp=getMPConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)t),Constants.momvCondition+Constants.aggregatesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence){conditionTemp=getMSConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)t),Constants.momvCondition+Constants.aggregatesMethod);}
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getComposesMethod().size()>0) {
			Iterator it = o.getComposesMethod().iterator();
			while(it.hasNext()){
				LinkedList<Condition> conditionTemp=null;
				Object t = it.next();
				if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm){conditionTemp=getMAConditions(((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)t),Constants.momvCondition+Constants.composesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod){conditionTemp=getMMConditions(((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)t),Constants.momvCondition+Constants.composesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter){conditionTemp=getMFConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)t),Constants.momvCondition+Constants.composesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel){conditionTemp=getMPConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)t),Constants.momvCondition+Constants.composesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence){conditionTemp=getMSConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)t),Constants.momvCondition+Constants.composesMethod);}
				searchConditions.addAll(conditionTemp);
			}
		}
		return searchConditions;
	}
	
	static public LinkedList<Condition> getMSConditions (OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getID()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), checkDataPropertyM(Constants.ID));
			else condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), which); 
			searchConditions.addFirst(condition);
		}
		if (o.getHasCreator().size()>0) {
			Iterator it = o.getHasCreator().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					LinkedList<Condition> conditionTemp=OMVConditions.getPersonConditions(per,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
				else{
					OMVOrganisation org = (OMVOrganisation)t;
					LinkedList<Condition> conditionTemp=OMVConditions.getOrganisationConditions(org,Constants.momvCondition+Constants.hasCreator);
					searchConditions.addAll(conditionTemp);
				}
			}
		}
		if (o.getHasParameter().size()>0) {
			Iterator it = o.getHasParameter().iterator();
			while(it.hasNext()){
				OMVMappingParameter od = (OMVMappingParameter)it.next();
				LinkedList<Condition> conditionTemp=getMPAConditions(od,Constants.momvCondition+Constants.hasParameter);
				searchConditions.addAll(conditionTemp);
			}
		}
		if (o.getComposesMethod().size()>0) {
			Iterator it = o.getComposesMethod().iterator();
			while(it.hasNext()){
				LinkedList<Condition> conditionTemp=null;
				Object t = it.next();
				if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm){conditionTemp=getMAConditions(((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)t),Constants.momvCondition+Constants.composesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod){conditionTemp=getMMConditions(((OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)t),Constants.momvCondition+Constants.composesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter){conditionTemp=getMFConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)t),Constants.momvCondition+Constants.composesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel){conditionTemp=getMPConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)t),Constants.momvCondition+Constants.composesMethod);}
				else if (t instanceof OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence){conditionTemp=getMSConditions(((OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)t),Constants.momvCondition+Constants.composesMethod);}
				searchConditions.addAll(conditionTemp);
			}
		}
		return searchConditions;
	}
	
	static public LinkedList<Condition> getEAConditions (OMVMappingEvidence.OMVMappingArgument o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getID()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), checkDataPropertyM(Constants.ID));
			else condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), which); 
			searchConditions.addFirst(condition);
		}
		return searchConditions;
	}
	
	static public LinkedList<Condition> getECConditions (OMVMappingEvidence.OMVMappingCertificate o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getID()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), checkDataPropertyM(Constants.ID));
			else condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), which); 
			searchConditions.addFirst(condition);
		}
		return searchConditions;
	}
	
	static public LinkedList<Condition> getEPConditions (OMVMappingEvidence.OMVMappingProof o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getID()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), checkDataPropertyM(Constants.ID));
			else condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), which); 
			searchConditions.addFirst(condition);
		}
		return searchConditions;
	}
	
	static public LinkedList<Condition> getMPAConditions (OMVMappingParameter o, String which){
		LinkedList<Condition> searchConditions = new LinkedList<Condition>();
		if (o.getID()!=null) {
			Condition condition;	
			if (which=="") condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), checkDataPropertyM(Constants.ID));
			else condition = new Condition(Constants.momvCondition+Constants.ID, o.getID(), which); 
			searchConditions.addFirst(condition);
		}
		return searchConditions;
	}
	
	static public Boolean checkDataPropertyM(String propertyName)  {
		Ontology resourceMappingOntology = mOyster2.getMappingOntology();
		OWLClass ontologyClass = KAON2Manager.factory().owlClass(Constants.OMV+Constants.DefaultMappingOntologyRoot);
		try{
			/*KAON2 BUG, DOES NOT SUPPORT OWL DL
	         * SHOULD BE DELETED WHEN IT DOES
	         */
			if(propertyName.equals(Constants.ID)) return true;
			
			/* UNTIL HERE */
			
			Set<DataProperty> dataProperties=ontologyClass.getDataPropertiesFrom(resourceMappingOntology);
			for (DataProperty dataProperty : dataProperties){
				if(propertyName.equals(Namespaces.guessLocalName(dataProperty.getURI()))) return true; 
			}
		}
	    catch (KAON2Exception e) {
	    	System.err.println(e + " in checkDataPropertyM()");
	    }
		return false;
	}
	
			
}