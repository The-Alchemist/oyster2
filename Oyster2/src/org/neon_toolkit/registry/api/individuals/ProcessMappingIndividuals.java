package org.neon_toolkit.registry.api.individuals;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import org.neon_toolkit.omv.api.core.OMVOntology;
import org.neon_toolkit.omv.api.core.OMVOrganisation;
import org.neon_toolkit.omv.api.core.OMVParty;
import org.neon_toolkit.omv.api.core.OMVPerson;
import org.neon_toolkit.omv.api.extensions.mapping.OMVMapping;
import org.neon_toolkit.omv.api.extensions.mapping.OMVMappingEvidence;
import org.neon_toolkit.omv.api.extensions.mapping.OMVMappingMethod;
import org.neon_toolkit.omv.api.extensions.mapping.OMVMappingParameter;
import org.neon_toolkit.omv.api.extensions.mapping.OMVMappingProperty;
import org.neon_toolkit.registry.oyster2.Constants;
import org.semanticweb.kaon2.api.Entity;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.owl.elements.DataProperty;
import org.semanticweb.kaon2.api.owl.elements.Individual;
import org.semanticweb.kaon2.api.owl.elements.ObjectProperty;

/**
 * The class ProcessMappingIndividuals provides the methods to
 * create OMV Mapping objects  
 * representing the result of a query 
 * @author Raul Palma
 * @version 1.0, March 2008
 */
public class ProcessMappingIndividuals{
	static LinkedList<Individual> mappingOnProcess = new LinkedList<Individual>();
   
	static public Object processMappingIndividual(Individual ontoIndiv, String whichClass, Ontology ontologySearch){
		 OMVMapping mainMappingReply=null;
		 OMVMappingProperty mPropertyReply=null;
		 OMVMappingMethod mMethodReply=null;
		 OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mAlgorithmReply=null;
		 OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod mManualReply=null;
		 OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mFilterReply=null;
		 OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mParallelReply=null;
		 OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mSequenceReply =null;
		 OMVMappingEvidence mEvidenceReply =null;
		 OMVMappingEvidence.OMVMappingArgument mArgumentReply =null;
		 OMVMappingEvidence.OMVMappingCertificate mCertificateReply =null;
		 OMVMappingEvidence.OMVMappingProof mProofReply =null;
		 
		 
		 if (!mappingOnProcess.contains(ontoIndiv)){
		  mappingOnProcess.add(ontoIndiv);  
		  try{
			Map dataPropertyMap = ontoIndiv.getDataPropertyValues(ontologySearch);
			Map objectPropertyMap = ontoIndiv.getObjectPropertyValues(ontologySearch);
			if ((dataPropertyMap.size()+objectPropertyMap.size())>0){
				
				if (whichClass.equalsIgnoreCase("mapping")) mainMappingReply = new OMVMapping();
				else if (whichClass.equalsIgnoreCase("mappingProperty")) mPropertyReply = new OMVMappingProperty();
				else if (whichClass.equalsIgnoreCase("mappingMethod")) mMethodReply = new OMVMappingMethod();
				else if (whichClass.equalsIgnoreCase("mappingAlgorithm")) mAlgorithmReply = new OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm();
				else if (whichClass.equalsIgnoreCase("mappingManualMethod")) mManualReply = new OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod();
				else if (whichClass.equalsIgnoreCase("mappingFilter")) mFilterReply = new OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter();
				else if (whichClass.equalsIgnoreCase("mappingParallel")) mParallelReply = new OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel();
				else if (whichClass.equalsIgnoreCase("mappingSequence")) mSequenceReply = new OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence();
				else if (whichClass.equalsIgnoreCase("mappingEvidence")) mEvidenceReply = new OMVMappingEvidence();
				else if (whichClass.equalsIgnoreCase("mappingArgument")) mArgumentReply = new OMVMappingEvidence.OMVMappingArgument();
				else if (whichClass.equalsIgnoreCase("mappingCertificate")) mCertificateReply = new OMVMappingEvidence.OMVMappingCertificate();
				else if (whichClass.equalsIgnoreCase("mappingProof")) mProofReply = new OMVMappingEvidence.OMVMappingProof();
				
				Collection keySet = dataPropertyMap.keySet();
				Iterator keys = keySet.iterator();
				while(keys.hasNext()){
					String keyStr = keys.next().toString();
					DataProperty property = KAON2Manager.factory().dataProperty(keyStr);
					String	propertyValue = org.neon_toolkit.registry.util.Utilities.getString(ontoIndiv.getDataPropertyValue(ontologySearch,property));
						
					if (whichClass.equalsIgnoreCase("mapping")) mainMappingReply.append(createOMVMapping(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("mappingProperty")) mPropertyReply.append(createOMVMappingProperty(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("mappingMethod")) mMethodReply.append(createOMVMappingMethod(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("mappingAlgorithm")) mAlgorithmReply.append(createOMVMappingAlgorithm(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("mappingManualMethod")) mManualReply.setID(ontoIndiv.getURI());
					else if (whichClass.equalsIgnoreCase("mappingFilter")) mFilterReply.append(createOMVMappingFilter(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("mappingParallel"))mParallelReply.append(createOMVMappingParallel(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("mappingSequence")) mSequenceReply.append(createOMVMappingSequence(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("mappingEvidence")) mEvidenceReply.setID(ontoIndiv.getURI());
					else if (whichClass.equalsIgnoreCase("mappingArgument")) mArgumentReply.setID(ontoIndiv.getURI());
					else if (whichClass.equalsIgnoreCase("mappingCertificate")) mCertificateReply.setID(ontoIndiv.getURI());
					else if (whichClass.equalsIgnoreCase("mappingProof")) mProofReply.setID(ontoIndiv.getURI());
					
					//System.out.println("The property "+property.getURI()+" has value: "+propertyValue);
				}
				keySet = objectPropertyMap.keySet();
				Iterator okeys = keySet.iterator();
				while(okeys.hasNext()){
					String keyStr = okeys.next().toString();
					ObjectProperty property = KAON2Manager.factory().objectProperty(keyStr);
					Collection propertyCol= new LinkedList();
					propertyCol = (Collection)objectPropertyMap.get(property);
					if(propertyCol==null)System.err.println("propertyCol is null");
					Iterator itInt = propertyCol.iterator();
					while(itInt.hasNext()){
						Entity propertyValue =(Entity) itInt.next();
							
						if (whichClass.equalsIgnoreCase("mapping")) mainMappingReply.append(createOMVMapping(property.getURI(),propertyValue.getURI(), ontologySearch));
						else if (whichClass.equalsIgnoreCase("mappingProperty")) mPropertyReply.append(createOMVMappingProperty(property.getURI(),propertyValue.getURI(), ontologySearch));
						else if (whichClass.equalsIgnoreCase("mappingMethod")) mMethodReply.append(createOMVMappingMethod(property.getURI(),propertyValue.getURI(), ontologySearch));
						else if (whichClass.equalsIgnoreCase("mappingAlgorithm")) mAlgorithmReply.append(createOMVMappingAlgorithm(property.getURI(),propertyValue.getURI(), ontologySearch));
						else if (whichClass.equalsIgnoreCase("mappingManualMethod")) mManualReply.setID(ontoIndiv.getURI());
						else if (whichClass.equalsIgnoreCase("mappingFilter")) mFilterReply.append(createOMVMappingFilter(property.getURI(),propertyValue.getURI(), ontologySearch));
						else if (whichClass.equalsIgnoreCase("mappingParallel")) mParallelReply.append(createOMVMappingParallel(property.getURI(),propertyValue.getURI(), ontologySearch));
						else if (whichClass.equalsIgnoreCase("mappingSequence")) mSequenceReply.append(createOMVMappingSequence(property.getURI(),propertyValue.getURI(), ontologySearch));
						else if (whichClass.equalsIgnoreCase("mappingEvidence")) mEvidenceReply.setID(ontoIndiv.getURI());
						else if (whichClass.equalsIgnoreCase("mappingArgument")) mArgumentReply.setID(ontoIndiv.getURI());
						else if (whichClass.equalsIgnoreCase("mappingCertificate")) mCertificateReply.setID(ontoIndiv.getURI());
						else if (whichClass.equalsIgnoreCase("mappingProof")) mProofReply.setID(ontoIndiv.getURI());
						
						//System.out.println("The property "+property.getURI()+" has value: "+propertyValue);	
					}	
				}
			}
		  }catch(Exception e){
				System.out.println(e.toString()+" Search Problem in processIndividuals.processIndividual");
		  }
		  mappingOnProcess.remove(ontoIndiv);
		  if (whichClass.equalsIgnoreCase("mapping")) return mainMappingReply;
		  else if (whichClass.equalsIgnoreCase("mappingProperty")) return mPropertyReply;
		  else if (whichClass.equalsIgnoreCase("mappingMethod")) return mMethodReply;
		  else if (whichClass.equalsIgnoreCase("mappingAlgorithm")) return mAlgorithmReply;
		  else if (whichClass.equalsIgnoreCase("mappingManualMethod")) return mManualReply;
		  else if (whichClass.equalsIgnoreCase("mappingFilter")) return mFilterReply;
		  else if (whichClass.equalsIgnoreCase("mappingParallel")) return mParallelReply;
		  else if (whichClass.equalsIgnoreCase("mappingSequence")) return mSequenceReply;
		  else if (whichClass.equalsIgnoreCase("mappingEvidence")) return mEvidenceReply;
		  else if (whichClass.equalsIgnoreCase("mappingArgument")) return mArgumentReply;
		  else if (whichClass.equalsIgnoreCase("mappingCertificate")) return mCertificateReply;
		  else if (whichClass.equalsIgnoreCase("mappingProof")) return mProofReply;
		  else return null;
		 }
		 else{
			if (whichClass.equalsIgnoreCase("mapping")) {mainMappingReply = new OMVMapping();mainMappingReply.setURI(ontoIndiv.getURI());return mainMappingReply;}			
			else if (whichClass.equalsIgnoreCase("mappingProperty")) {mPropertyReply = new OMVMappingProperty();mPropertyReply.setID(ontoIndiv.getURI());return mPropertyReply;}
			else if (whichClass.equalsIgnoreCase("mappingProperty")) {mMethodReply = new OMVMappingMethod();mMethodReply.setID(ontoIndiv.getURI());return mMethodReply;}
			else if (whichClass.equalsIgnoreCase("mappingAlgorithm")) {mAlgorithmReply = new OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm();mAlgorithmReply.setID(ontoIndiv.getURI());return mAlgorithmReply;}
			else if (whichClass.equalsIgnoreCase("mappingManualMethod")) {mManualReply = new OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod();mManualReply.setID(ontoIndiv.getURI());return mManualReply;}
			else if (whichClass.equalsIgnoreCase("mappingFilter")) {mFilterReply = new OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter();mFilterReply.setID(ontoIndiv.getURI());return mFilterReply;}
			else if (whichClass.equalsIgnoreCase("mappingParallel")) {mParallelReply = new OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel();mParallelReply.setID(ontoIndiv.getURI());return mParallelReply;}
			else if (whichClass.equalsIgnoreCase("mappingSequence")) {mSequenceReply = new OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence();mSequenceReply.setID(ontoIndiv.getURI());return mSequenceReply;}
			else if (whichClass.equalsIgnoreCase("mappingEvidence")) {mEvidenceReply = new OMVMappingEvidence();mEvidenceReply.setID(ontoIndiv.getURI());return mEvidenceReply;}
			else if (whichClass.equalsIgnoreCase("mappingArgument")) {mArgumentReply = new OMVMappingEvidence.OMVMappingArgument();mArgumentReply.setID(ontoIndiv.getURI());return mArgumentReply;}
			else if (whichClass.equalsIgnoreCase("mappingCertificate")) {mCertificateReply = new OMVMappingEvidence.OMVMappingCertificate();mCertificateReply.setID(ontoIndiv.getURI());return mCertificateReply;}
			else if (whichClass.equalsIgnoreCase("mappingProof")) {mProofReply = new OMVMappingEvidence.OMVMappingProof();mProofReply.setID(ontoIndiv.getURI());return mProofReply;}
			return null;
		 }
		}

	static private OMVMapping createOMVMapping(String URI, String value, Ontology ontologySearch){
		OMVMapping mappingReply=new OMVMapping();
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.URI)) {mappingReply.setURI(value); return mappingReply;}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.creationDate)) {mappingReply.setCreationDate(value); return mappingReply;}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.hasCreator)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)ProcessOMVIndividuals.processIndividual(oIndividual, "party", ontologySearch);
			OMVPerson personReply=(OMVPerson)ProcessOMVIndividuals.processIndividual(oIndividual, "person", ontologySearch);
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=ProcessOMVIndividuals.copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				mappingReply.addHasCreator(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)ProcessOMVIndividuals.processIndividual(oIndividual, "organisation", ontologySearch);
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=ProcessOMVIndividuals.copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					mappingReply.addHasCreator(organisationReply);
					organisationReply = null;
				}
			}
			partyReply=null;
			return mappingReply;
		}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.hasProperty)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVMappingProperty mPropertyReply=(OMVMappingProperty)processMappingIndividual(oIndividual, "mappingProperty", ontologySearch);
			if (mPropertyReply!=null) mappingReply.addHasProperty(mPropertyReply);
			mPropertyReply = null;
			return mappingReply;
		}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.usedMethod)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVMappingMethod mMethodReply=(OMVMappingMethod)processMappingIndividual(oIndividual, "mappingMethod", ontologySearch);
			OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mAlgorithmReply=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)processMappingIndividual(oIndividual, "mappingAlgorithm", ontologySearch);
			if (mAlgorithmReply!=null){
				if (mMethodReply!=null) {
					OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mAlgorithmFinal=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)copyMethod2SubClass(mMethodReply,mAlgorithmReply);
					mAlgorithmReply=mAlgorithmFinal;
				}
				mappingReply.setUsedMethod(mAlgorithmReply);
				mAlgorithmReply = null;
			}
			else{
				OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod mManualReply=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)processMappingIndividual(oIndividual, "mappingManualMethod", ontologySearch);
				if (mManualReply!=null){
					if (mMethodReply!=null) {
						OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod mManualFinal=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)copyMethod2SubClass(mMethodReply,mManualReply);
						mManualReply=mManualFinal;
					}
					mappingReply.setUsedMethod(mManualReply);
					mManualReply = null;
				}
				else{
					OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mFilterReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)processMappingIndividual(oIndividual, "mappingFilter", ontologySearch);
					if (mFilterReply!=null){
						if (mMethodReply!=null) {
							OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mFilterFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)copyMethod2SubClass(mMethodReply,mFilterReply);
							mFilterReply=mFilterFinal;
						}
						mappingReply.setUsedMethod(mFilterReply);
						mFilterReply = null;
					}
					else{
						OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mParallelReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)processMappingIndividual(oIndividual, "mappingParallel", ontologySearch);
						if (mParallelReply!=null){
							if (mMethodReply!=null) {
								OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mParallelFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)copyMethod2SubClass(mMethodReply,mParallelReply);
								mParallelReply=mParallelFinal;
							}
							mappingReply.setUsedMethod(mParallelReply);
							mParallelReply = null;
						}
						else{
							OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mSequenceReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)processMappingIndividual(oIndividual, "mappingSequence", ontologySearch);
							if (mSequenceReply!=null){
								if (mMethodReply!=null) {
									OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mSequenceFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)copyMethod2SubClass(mMethodReply,mSequenceReply);
									mSequenceReply=mSequenceFinal;
								}
								mappingReply.setUsedMethod(mSequenceReply);
								mSequenceReply = null;
							}
						}
					}
				}
			}
			mMethodReply = null;
			return mappingReply;
		}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.hasSourceOntology)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntology oReferencesReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(oIndividual, "ontology", ontologySearch);
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			mappingReply.setHasSourceOntology(oReferencesReply);
			oReferencesReply = null;
			return mappingReply;
		}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.hasTargetOntology)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntology oReferencesReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(oIndividual, "ontology", ontologySearch);
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			mappingReply.setHasTargetOntology(oReferencesReply);
			oReferencesReply = null;
			return mappingReply;
		}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.level)) {mappingReply.setLevel(value); return mappingReply;}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.processingTime)) {mappingReply.setProcessingTime(new Float(value.substring(1, value.indexOf("\"", 2))));return mappingReply;}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.purpose)) {mappingReply.setPurpose(value); return mappingReply;}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.type)) {mappingReply.setType(value); return mappingReply;}
		return mappingReply;
	}
	
	static private OMVMappingProperty createOMVMappingProperty(String URI, String value, Ontology ontologySearch){
		OMVMappingProperty mappingPropertyReply=new OMVMappingProperty();
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.ID)) {mappingPropertyReply.setID(value); return mappingPropertyReply;}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.hasEvidence)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVMappingEvidence mEvidenceReply=(OMVMappingEvidence)processMappingIndividual(oIndividual, "mappingEvidence", ontologySearch);
			OMVMappingEvidence.OMVMappingArgument mArgumentReply=(OMVMappingEvidence.OMVMappingArgument)processMappingIndividual(oIndividual, "mappingArgument", ontologySearch);
			if (mArgumentReply!=null){
				if (mEvidenceReply!=null) {
					mArgumentReply.setID(mEvidenceReply.getID());
				}
				mappingPropertyReply.addHasEvidence(mArgumentReply);
				mArgumentReply = null;
			}
			else{
				OMVMappingEvidence.OMVMappingCertificate mCertificateReply=(OMVMappingEvidence.OMVMappingCertificate)processMappingIndividual(oIndividual, "mappingCertificate", ontologySearch);
				if (mCertificateReply!=null){
					if (mEvidenceReply!=null) {
						mCertificateReply.setID(mEvidenceReply.getID());
					}
					mappingPropertyReply.addHasEvidence(mCertificateReply);
					mCertificateReply = null;
				}
				else{
					OMVMappingEvidence.OMVMappingProof mProofReply=(OMVMappingEvidence.OMVMappingProof)processMappingIndividual(oIndividual, "mappingProof", ontologySearch);
					if (mProofReply!=null){
						if (mEvidenceReply!=null) {
							mProofReply.setID(mEvidenceReply.getID());
						}
						mappingPropertyReply.addHasEvidence(mProofReply);
						mProofReply = null;
					}
				}
			}
			mEvidenceReply = null;
			return mappingPropertyReply;
		}
		return mappingPropertyReply;
	}
	
	static private OMVMappingMethod createOMVMappingMethod(String URI, String value , Ontology ontologySearch){
		OMVMappingMethod mappingMethodReply=new OMVMappingMethod();
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.ID)) {mappingMethodReply.setID(value); return mappingMethodReply;}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.hasParameter)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVMappingParameter mParameterReply=(OMVMappingParameter)processMappingIndividual(oIndividual, "mappingParameter", ontologySearch);
			if (mParameterReply!=null) mappingMethodReply.addHasParameter(mParameterReply);
			mParameterReply = null;
			return mappingMethodReply;
		}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.hasCreator)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)ProcessOMVIndividuals.processIndividual(oIndividual, "party", ontologySearch);
			OMVPerson personReply=(OMVPerson)ProcessOMVIndividuals.processIndividual(oIndividual, "person", ontologySearch);
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=ProcessOMVIndividuals.copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				mappingMethodReply.addHasCreator(personReply);
				personReply = null;
			}
			else{
				OMVOrganisation organisationReply=(OMVOrganisation)ProcessOMVIndividuals.processIndividual(oIndividual, "organisation", ontologySearch);
				if (organisationReply!=null){
					if (partyReply!=null) {
						OMVOrganisation organisationReplyFinal=ProcessOMVIndividuals.copyParty2Organisation(partyReply,organisationReply);
						organisationReply=organisationReplyFinal;
					}
					mappingMethodReply.addHasCreator(organisationReply);
					organisationReply = null;
				}
			}
			partyReply=null;
			return mappingMethodReply;
		}
		return mappingMethodReply;
	}
	
	static private OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm createOMVMappingAlgorithm(String URI, String value, Ontology ontologySearch){
		OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mappingAlgorithReply=new OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm();
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.source)) {mappingAlgorithReply.setSource(value); return mappingAlgorithReply;}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.version)) {mappingAlgorithReply.setVersion(value); return mappingAlgorithReply;}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.naturalLanguage)) {mappingAlgorithReply.setNaturalLanguage(value); return mappingAlgorithReply;}
		return mappingAlgorithReply;
	}
	
	static private OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter createOMVMappingFilter(String URI, String value , Ontology ontologySearch){
		OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mappingFilterReply=new OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter();
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.value)) {mappingFilterReply.setValue(new Float(value.substring(1, value.indexOf("\"", 2)))); return mappingFilterReply;}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.variety)) {mappingFilterReply.setVariety(value); return mappingFilterReply;}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.filtersMethod)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVMappingMethod mMethodReply=(OMVMappingMethod)processMappingIndividual(oIndividual, "mappingMethod", ontologySearch);
			OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mAlgorithmReply=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)processMappingIndividual(oIndividual, "mappingAlgorithm", ontologySearch);
			if (mAlgorithmReply!=null){
				if (mMethodReply!=null) {
					OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mAlgorithmFinal=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)copyMethod2SubClass(mMethodReply,mAlgorithmReply);
					mAlgorithmReply=mAlgorithmFinal;
				}
				mappingFilterReply.setFiltersMethod(mAlgorithmReply);
				mAlgorithmReply = null;
			}
			else{
				OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod mManualReply=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)processMappingIndividual(oIndividual, "mappingManualMethod", ontologySearch);
				if (mManualReply!=null){
					if (mMethodReply!=null) {
						OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod mManualFinal=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)copyMethod2SubClass(mMethodReply,mManualReply);
						mManualReply=mManualFinal;
					}
					mappingFilterReply.setFiltersMethod(mManualReply);
					mManualReply = null;
				}
				else{
					OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mFilterReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)processMappingIndividual(oIndividual, "mappingFilter", ontologySearch);
					if (mFilterReply!=null){
						if (mMethodReply!=null) {
							OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mFilterFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)copyMethod2SubClass(mMethodReply,mFilterReply);
							mFilterReply=mFilterFinal;
						}
						mappingFilterReply.setFiltersMethod(mFilterReply);
						mFilterReply = null;
					}
					else{
						OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mParallelReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)processMappingIndividual(oIndividual, "mappingParallel", ontologySearch);
						if (mParallelReply!=null){
							if (mMethodReply!=null) {
								OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mParallelFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)copyMethod2SubClass(mMethodReply,mParallelReply);
								mParallelReply=mParallelFinal;
							}
							mappingFilterReply.setFiltersMethod(mParallelReply);
							mParallelReply = null;
						}
						else{
							OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mSequenceReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)processMappingIndividual(oIndividual, "mappingSequence", ontologySearch);
							if (mSequenceReply!=null){
								if (mMethodReply!=null) {
									OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mSequenceFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)copyMethod2SubClass(mMethodReply,mSequenceReply);
									mSequenceReply=mSequenceFinal;
								}
								mappingFilterReply.setFiltersMethod(mSequenceReply);
								mSequenceReply = null;
							}
						}
					}
				}
			}
			mMethodReply = null;
			return mappingFilterReply;
		}
		return mappingFilterReply;
	}
	
	static private OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel createOMVMappingParallel(String URI, String value , Ontology ontologySearch){
		OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mappingParallelReply=new OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel();
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.aggregatesMethod)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVMappingMethod mMethodReply=(OMVMappingMethod)processMappingIndividual(oIndividual, "mappingMethod", ontologySearch);
			OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mAlgorithmReply=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)processMappingIndividual(oIndividual, "mappingAlgorithm", ontologySearch);
			if (mAlgorithmReply!=null){
				if (mMethodReply!=null) {
					OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mAlgorithmFinal=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)copyMethod2SubClass(mMethodReply,mAlgorithmReply);
					mAlgorithmReply=mAlgorithmFinal;
				}
				mappingParallelReply.addAggregatesMethod(mAlgorithmReply);
				mAlgorithmReply = null;
			}
			else{
				OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod mManualReply=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)processMappingIndividual(oIndividual, "mappingManualMethod", ontologySearch);
				if (mManualReply!=null){
					if (mMethodReply!=null) {
						OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod mManualFinal=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)copyMethod2SubClass(mMethodReply,mManualReply);
						mManualReply=mManualFinal;
					}
					mappingParallelReply.addAggregatesMethod(mManualReply);
					mManualReply = null;
				}
				else{
					OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mFilterReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)processMappingIndividual(oIndividual, "mappingFilter", ontologySearch);
					if (mFilterReply!=null){
						if (mMethodReply!=null) {
							OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mFilterFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)copyMethod2SubClass(mMethodReply,mFilterReply);
							mFilterReply=mFilterFinal;
						}
						mappingParallelReply.addAggregatesMethod(mFilterReply);
						mFilterReply = null;
					}
					else{
						OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mParallelReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)processMappingIndividual(oIndividual, "mappingParallel", ontologySearch);
						if (mParallelReply!=null){
							if (mMethodReply!=null) {
								OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mParallelFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)copyMethod2SubClass(mMethodReply,mParallelReply);
								mParallelReply=mParallelFinal;
							}
							mappingParallelReply.addAggregatesMethod(mParallelReply);
							mParallelReply = null;
						}
						else{
							OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mSequenceReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)processMappingIndividual(oIndividual, "mappingSequence", ontologySearch);
							if (mSequenceReply!=null){
								if (mMethodReply!=null) {
									OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mSequenceFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)copyMethod2SubClass(mMethodReply,mSequenceReply);
									mSequenceReply=mSequenceFinal;
								}
								mappingParallelReply.addAggregatesMethod(mSequenceReply);
								mSequenceReply = null;
							}
						}
					}
				}
			}
			mMethodReply = null;
			return mappingParallelReply;
		}
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.composesMethod)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVMappingMethod mMethodReply=(OMVMappingMethod)processMappingIndividual(oIndividual, "mappingMethod", ontologySearch);
			OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mAlgorithmReply=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)processMappingIndividual(oIndividual, "mappingAlgorithm", ontologySearch);
			if (mAlgorithmReply!=null){
				if (mMethodReply!=null) {
					OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mAlgorithmFinal=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)copyMethod2SubClass(mMethodReply,mAlgorithmReply);
					mAlgorithmReply=mAlgorithmFinal;
				}
				mappingParallelReply.addComposesMethod(mAlgorithmReply);
				mAlgorithmReply = null;
			}
			else{
				OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod mManualReply=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)processMappingIndividual(oIndividual, "mappingManualMethod", ontologySearch);
				if (mManualReply!=null){
					if (mMethodReply!=null) {
						OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod mManualFinal=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)copyMethod2SubClass(mMethodReply,mManualReply);
						mManualReply=mManualFinal;
					}
					mappingParallelReply.addComposesMethod(mManualReply);
					mManualReply = null;
				}
				else{
					OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mFilterReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)processMappingIndividual(oIndividual, "mappingFilter", ontologySearch);
					if (mFilterReply!=null){
						if (mMethodReply!=null) {
							OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mFilterFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)copyMethod2SubClass(mMethodReply,mFilterReply);
							mFilterReply=mFilterFinal;
						}
						mappingParallelReply.addComposesMethod(mFilterReply);
						mFilterReply = null;
					}
					else{
						OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mParallelReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)processMappingIndividual(oIndividual, "mappingParallel", ontologySearch);
						if (mParallelReply!=null){
							if (mMethodReply!=null) {
								OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mParallelFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)copyMethod2SubClass(mMethodReply,mParallelReply);
								mParallelReply=mParallelFinal;
							}
							mappingParallelReply.addComposesMethod(mParallelReply);
							mParallelReply = null;
						}
						else{
							OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mSequenceReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)processMappingIndividual(oIndividual, "mappingSequence", ontologySearch);
							if (mSequenceReply!=null){
								if (mMethodReply!=null) {
									OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mSequenceFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)copyMethod2SubClass(mMethodReply,mSequenceReply);
									mSequenceReply=mSequenceFinal;
								}
								mappingParallelReply.addComposesMethod(mSequenceReply);
								mSequenceReply = null;
							}
						}
					}
				}
			}
			mMethodReply = null;
			return mappingParallelReply;
		}
		return mappingParallelReply;
	}

	static private OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence createOMVMappingSequence(String URI, String value , Ontology ontologySearch){
		OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mappingSequenceReply=new OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence();
		if (URI.equalsIgnoreCase(Constants.MOMVURI+Constants.composesMethod)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVMappingMethod mMethodReply=(OMVMappingMethod)processMappingIndividual(oIndividual, "mappingMethod", ontologySearch);
			OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mAlgorithmReply=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)processMappingIndividual(oIndividual, "mappingAlgorithm", ontologySearch);
			if (mAlgorithmReply!=null){
				if (mMethodReply!=null) {
					OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm mAlgorithmFinal=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingAlgorithm)copyMethod2SubClass(mMethodReply,mAlgorithmReply);
					mAlgorithmReply=mAlgorithmFinal;
				}
				mappingSequenceReply.addComposesMethod(mAlgorithmReply);
				mAlgorithmReply = null;
			}
			else{
				OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod mManualReply=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)processMappingIndividual(oIndividual, "mappingManualMethod", ontologySearch);
				if (mManualReply!=null){
					if (mMethodReply!=null) {
						OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod mManualFinal=(OMVMappingMethod.OMVMappingBasicMethod.OMVMappingManualMethod)copyMethod2SubClass(mMethodReply,mManualReply);
						mManualReply=mManualFinal;
					}
					mappingSequenceReply.addComposesMethod(mManualReply);
					mManualReply = null;
				}
				else{
					OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mFilterReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)processMappingIndividual(oIndividual, "mappingFilter", ontologySearch);
					if (mFilterReply!=null){
						if (mMethodReply!=null) {
							OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mFilterFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter)copyMethod2SubClass(mMethodReply,mFilterReply);
							mFilterReply=mFilterFinal;
						}
						mappingSequenceReply.addComposesMethod(mFilterReply);
						mFilterReply = null;
					}
					else{
						OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mParallelReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)processMappingIndividual(oIndividual, "mappingParallel", ontologySearch);
						if (mParallelReply!=null){
							if (mMethodReply!=null) {
								OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel mParallelFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingParallel)copyMethod2SubClass(mMethodReply,mParallelReply);
								mParallelReply=mParallelFinal;
							}
							mappingSequenceReply.addComposesMethod(mParallelReply);
							mParallelReply = null;
						}
						else{
							OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mSequenceReply=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)processMappingIndividual(oIndividual, "mappingSequence", ontologySearch);
							if (mSequenceReply!=null){
								if (mMethodReply!=null) {
									OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence mSequenceFinal=(OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingSequence)copyMethod2SubClass(mMethodReply,mSequenceReply);
									mSequenceReply=mSequenceFinal;
								}
								mappingSequenceReply.addComposesMethod(mSequenceReply);
								mSequenceReply = null;
							}
						}
					}
				}
			}
			mMethodReply = null;
			return mappingSequenceReply;
		}
		return mappingSequenceReply;
	}
	
	static private Object copyMethod2SubClass(OMVMappingMethod paReply,Object peReply){
		OMVMappingMethod methodReply = paReply;
		Object element = peReply;
		
		((OMVMappingMethod)element).setID(methodReply.getID());
		Iterator it = methodReply.getHasCreator().iterator();
		while(it.hasNext()){
			OMVParty t = (OMVParty)it.next();
			if (t!=null){
				((OMVMappingMethod)element).addHasCreator(t);
			}
		}
		it = methodReply.getHasParameter().iterator();
		while(it.hasNext()){
			OMVMappingParameter t = (OMVMappingParameter)it.next();
			if (t!=null){
				((OMVMappingMethod)element).addHasParameter(t);
			}
		}
		return element;
	}

		
}