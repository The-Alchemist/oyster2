package org.neon_toolkit.registry.omv.service.oyster.lifecyclemanager.from_ebxml.omv;

/*
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.Association;
import javax.xml.registry.infomodel.RegistryObject;

import org.neon_toolkit.registry.omv.service.centrasite.lifecyclemanager.from_ebxml.AssociationSubmit;
import org.neon_toolkit.registry.omv.service.centrasite.lifecyclemanager.from_ebxml.RegistryObjectSubmit;
import org.neon_toolkit.registry.omv.service.centrasite.lifecyclemanager.from_ebxml.SlotSubmit;
*/
import org.neon_toolkit.omv.api.core.OMVOntology;
import org.neon_toolkit.registry.omv.service.oyster.lifecyclemanager.from_ebxml.RegistryObjectSubmit;
import org.neon_toolkit.registry.omv.xsd.rim.FormalityLevelType;
import org.neon_toolkit.registry.omv.xsd.rim.KnowledgeRepresentationParadigmType;
import org.neon_toolkit.registry.omv.xsd.rim.LicenseModelType;
import org.neon_toolkit.registry.omv.xsd.rim.OMVObjectRefType;
import org.neon_toolkit.registry.omv.xsd.rim.OMVRegistryObjectType;
import org.neon_toolkit.registry.omv.xsd.rim.OntologyDomainType;
import org.neon_toolkit.registry.omv.xsd.rim.OntologyEngineeringMethodologyType;
import org.neon_toolkit.registry.omv.xsd.rim.OntologyEngineeringToolType;
import org.neon_toolkit.registry.omv.xsd.rim.OntologyLanguageType;
import org.neon_toolkit.registry.omv.xsd.rim.OntologySyntaxType;
import org.neon_toolkit.registry.omv.xsd.rim.OntologyTaskType;
import org.neon_toolkit.registry.omv.xsd.rim.OntologyType_Type;
import org.neon_toolkit.registry.omv.xsd.rim.Ontology_Type;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI;

public class OMVRegistryObjectSubmit extends RegistryObjectSubmit {
	public static String OMV_NS_Prefix="{OMV}";
	
	public static Object submit(OMVRegistryObjectType input, Object output) {
		
		if (input instanceof FormalityLevelType)
			output=FormalityLevelSubmit.submit((FormalityLevelType)input, output);
		else
		if (input instanceof KnowledgeRepresentationParadigmType)
			output=KnowledgeRepresentationParadigmSubmit.submit((KnowledgeRepresentationParadigmType)input, output);
		else
		if (input instanceof LicenseModelType)
			output=LicenseModelSumbit.submit((LicenseModelType)input, output);
		else
		if (input instanceof OntologyEngineeringMethodologyType)
			output=OntologyEngineeringMethodologySubmit.submit((OntologyEngineeringMethodologyType)input, output);
		else
		if (input instanceof OntologyEngineeringToolType)
			output=OntologyEngineeringToolSubmit.submit((OntologyEngineeringToolType)input, output);
		else
		if (input instanceof OntologyLanguageType)
			output=OntologyLanguageSubmit.submit((OntologyLanguageType)input, output);
		else
		if (input instanceof Ontology_Type)
			output=OntologySubmit.submit((Ontology_Type)input, output);
		else
		if (input instanceof OntologySyntaxType)
			output=OntologySyntaxSubmit.submit((OntologySyntaxType)input, output);
		else
		if (input instanceof OntologyTaskType)
			output=OntologyTaskSubmit.submit((OntologyTaskType)input, output);
		else
		if (input instanceof OntologyType_Type)
			output=OntologyTypeSubmit.submit((OntologyType_Type)input, output);
		else
		if (input instanceof OntologyDomainType)
			output=OntologyDomainSubmit.submit((OntologyDomainType)input, output);
		
		return output;
	}

	/*
	private static String removeAssociation(String assocName1,RegistryObject output) throws JAXRException {
		String assocName=assocName1;
		String targetName=null;
		if (assocName.equalsIgnoreCase("hasOntologySyntax") || 
			assocName.equalsIgnoreCase("hasSyntax")) {
			assocName="has"; targetName=OMV_NS_Prefix+"OntologySyntax";
		} else
		if (assocName.equalsIgnoreCase("hasOntologyLanguage")) {
			assocName="has"; targetName=OMV_NS_Prefix+"OntologyLanguage";
		} else
		if (assocName.equalsIgnoreCase("hasLicense")) {
			assocName="has"; targetName=OMV_NS_Prefix+"LicenseModel";
		} else
		if (assocName.equalsIgnoreCase("hasFormalityLevel")) {
			assocName="has"; targetName=OMV_NS_Prefix+"FormalityLevel";
		} else
		if (assocName.equalsIgnoreCase("usedOntologyEngineeringTool")) {
			assocName="uses"; targetName=OMV_NS_Prefix+"OntologyEngineeringTool";
		} else
		if (assocName.equalsIgnoreCase("usedOntologyEngineeringMethodology")) {
			assocName="uses"; targetName=OMV_NS_Prefix+"OntologyEngineeringMethodology";
		} else
		if (assocName.equalsIgnoreCase("usedKnowledgeRepresentationParadigm")) {
			assocName="uses"; targetName=OMV_NS_Prefix+"KnowledgeRepresentationParadigm";
		} else
		if (assocName.equalsIgnoreCase("hasDomain")) {
			assocName="has"; targetName=OMV_NS_Prefix+"OntologyDomain";
		}
		for (Object oneAssoc: output.getAssociations())
			if (((Association)oneAssoc).getAssociationType().getValue().equalsIgnoreCase(assocName)) {
				if (targetName!=null && !((Association)oneAssoc).getTargetObject().getObjectType().getValue().equalsIgnoreCase(targetName))
					continue;
				output.removeAssociation((Association)oneAssoc);
			}
		return assocName;
	}

	protected static void setAssociation(String assocName, OMVObjectRefType[] assocTarget, RegistryObject output) throws JAXRException {
		if (assocTarget==null) return;
		assocName = removeAssociation(assocName, output);
		for (OMVObjectRefType temp_ort: assocTarget)
			setAssociation(AssociationSubmit.submit(assocName,temp_ort.getId().getReferenceURI().toString()), output);
	}
		
	protected static void setAssociation(String assocName, ReferenceURI assocTarget, RegistryObject output) throws JAXRException {
		if (assocTarget==null) return;
		assocName = removeAssociation(assocName, output);
		setAssociation(AssociationSubmit.submit(assocName,assocTarget.getReferenceURI().toString()), output);
	}
	*/
}
