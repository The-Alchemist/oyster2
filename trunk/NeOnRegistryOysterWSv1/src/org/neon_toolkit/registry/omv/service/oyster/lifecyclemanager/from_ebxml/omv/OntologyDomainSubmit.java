package org.neon_toolkit.registry.omv.service.oyster.lifecyclemanager.from_ebxml.omv;

/*
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.RegistryObject;

import org.neon_toolkit.registry.omv.service.centrasite.lifecyclemanager.from_ebxml.ClassificationSubmit;
import org.neon_toolkit.registry.omv.service.centrasite.lifecyclemanager.from_ebxml.SlotSubmit;
*/

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.neon_toolkit.registry.omv.xsd.rim.OMVObjectRefType;
import org.neon_toolkit.registry.omv.xsd.rim.OntologyDomainType;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.core.OMVOntologyDomain;
import org.neontoolkit.omv.api.core.OMVOrganisation;
import org.neontoolkit.omv.api.core.OMVPerson;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringTypeSequence;


public class OntologyDomainSubmit extends OMVRegistryObjectSubmit {
	public static Object submit(OntologyDomainType input, Object outputIn) {
		OMVOntologyDomain output;
		if (outputIn==null) output = new OMVOntologyDomain();
		else output = ((OMVOntologyDomain)outputIn);
		
		
		if (input.getName()!=null){
			InternationalStringTypeSequence[] templst=input.getName().getInternationalStringTypeSequence();
	   		String tx="";
	   		if (templst!=null){
	   			for (int i=0;i<templst.length;i++) {
			   		tx=templst[i].getLocalizedString().getValue().getFreeFormText();
			   		output.setName(tx);
		   		} 					
	   		}
		}
		
		if (input.getURI()!=null){
			output.setURI(input.getURI().toString());
		}
		
		if (input.getIsSubDomainOf()!=null){
			for (int i=0;i<input.getIsSubDomainOf().length;i++) {
				OMVOntologyDomain temp = new OMVOntologyDomain();
				temp.setURI(input.getIsSubDomainOf()[i].getId().toString());
				output.addIsSubDomainOf(temp);			
			}
		}
					
		return output;
	}
}
