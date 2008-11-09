package org.neon_toolkit.registry.omv.service.oyster.lifecyclemanager.from_ebxml;

/*
import javax.xml.registry.BusinessLifeCycleManager;
import javax.xml.registry.BusinessQueryManager;
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.Association;
import javax.xml.registry.infomodel.Classification;
import javax.xml.registry.infomodel.RegistryEntry;
import javax.xml.registry.infomodel.RegistryObject;
import javax.xml.registry.infomodel.Slot;
*/

import org.neon_toolkit.registry.omv.service.oyster.lifecyclemanager.from_ebxml.omv.OMVRegistryObjectSubmit;
import org.neon_toolkit.registry.omv.service.oyster.lifecyclemanager.from_ebxml.omv.OrganisationSubmit;
import org.neon_toolkit.registry.omv.service.oyster.lifecyclemanager.from_ebxml.omv.PersonSubmit;
import org.neon_toolkit.registry.omv.xsd.rim.OMVRegistryObjectType;
import org.neon_toolkit.registry.omv.xsd.rim.Ontology_Type;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.ClassificationType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.OrganizationType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.PersonType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.SlotType1;




public class RegistryObjectSubmit {
	
   

	public static Object submit(RegistryObjectType input) {
		return submit(input,null);
	}
	
	public static Object submit(RegistryObjectType input, Object output) {
		if (input==null) return null;
		
		
		if (input instanceof OMVRegistryObjectType)
			output=OMVRegistryObjectSubmit.submit((OMVRegistryObjectType)input, output);
		else 
		if (input instanceof OrganizationType)
			output=OrganisationSubmit.submit((OrganizationType)input, output);
		else 
		if (input instanceof PersonType){
			output=PersonSubmit.submit((PersonType)input, output);
		}
		
		/*
		if (output==null)
			output=(RegistryObject)lcManager.createObject(lcManager.REGISTRY_ENTRY);
		*/ 
		//name
		//if (!isInternationalStringTypeEmpty(input.getName()))
		//	output.setName(InternationalStringSubmit.submit(input.getName()));
		
		//description
		//if (!isInternationalStringTypeEmpty(input.getDescription()))
		//	output.setDescription(InternationalStringSubmit.submit(input.getDescription()));
		
		//slots
		//SlotType1[] tempsl=input.getSlot();
		//if (tempsl!=null)
		//	for (int i=0;i<tempsl.length;i++)
		//		setSlot(SlotSubmit.submit(tempsl[i]),output);
		
		//classifications
		//ClassificationType[] tempclt = input.getClassification();
		//if (tempclt!=null)
		//	for (int i=0; i<tempclt.length; i++)
		//		setClassification(ClassificationSubmit.submit(tempclt[i]), output);

		return output;
	}
	
    protected static boolean isInternationalStringTypeEmpty(InternationalStringType input) {
    	return (input==null || input.getInternationalStringTypeSequence()==null || input.getInternationalStringTypeSequence().length==0);
    }
    
    /*
    protected static void setSlot(Slot arg, Object output){
    	if (arg==null || output==null) return;
    	output.removeSlot(arg.getName());
    	output.addSlot(arg);
    }
    
    protected static void setClassification(Classification arg, Object output){
		for (Object oneClassification: output.getClassifications())
			if (((Classification)oneClassification).getConcept().getKey().getId().equals(arg.getConcept().getKey().getId()))
				return;
		output.addClassification(arg);

    }
    ´
    
	protected static void setAssociation(Association arg, Object output) {
		for (Object oneAssoc: output.getAssociations())
			if (((Association)oneAssoc).getAssociationType().getKey().getId().equals(arg.getAssociationType().getKey().getId()) &&
				((Association)oneAssoc).getTargetObject().getKey().getId().equals(arg.getTargetObject().getKey().getId()))
				return;
		output.addAssociation(arg);		
	}
	*/
}
