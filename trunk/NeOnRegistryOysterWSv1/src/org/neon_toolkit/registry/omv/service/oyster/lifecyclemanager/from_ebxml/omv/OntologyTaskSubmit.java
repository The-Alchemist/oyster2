package org.neon_toolkit.registry.omv.service.oyster.lifecyclemanager.from_ebxml.omv;

/*
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.RegistryObject;

import org.neon_toolkit.registry.omv.service.centrasite.lifecyclemanager.from_ebxml.AssociationSubmit;
import org.neon_toolkit.registry.omv.service.centrasite.lifecyclemanager.from_ebxml.ClassificationSubmit;
*/


import org.neon_toolkit.registry.omv.xsd.rim.OMVObjectRefType;
import org.neon_toolkit.registry.omv.xsd.rim.OntologyTaskType;
import org.neontoolkit.omv.api.core.OMVOntologyTask;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringTypeSequence;

public class OntologyTaskSubmit extends OMVRegistryObjectSubmit {
	public static Object submit(OntologyTaskType input, Object outputIn) {
		
		OMVOntologyTask output;
		if (outputIn==null) output = new OMVOntologyTask();
		else output = ((OMVOntologyTask)outputIn);
		
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
		
		return output;
	}
}
