package org.neon_toolkit.registry.omv.service.oyster.lifecyclemanager.from_ebxml.omv;

/*
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.RegistryObject;

import org.neon_toolkit.registry.omv.service.centrasite.lifecyclemanager.from_ebxml.AssociationSubmit;
import org.neon_toolkit.registry.omv.service.centrasite.lifecyclemanager.from_ebxml.ClassificationSubmit;
*/

import org.neon_toolkit.registry.omv.xsd.rim.FormalityLevelType;
import org.neon_toolkit.registry.omv.xsd.rim.OMVObjectRefType;
import org.neontoolkit.omv.api.core.OMVFormalityLevel;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringTypeSequence;


public class FormalityLevelSubmit extends OMVRegistryObjectSubmit {
	public static Object submit(FormalityLevelType input, Object outputIn) {
		OMVFormalityLevel output;
		if (outputIn==null) output = new OMVFormalityLevel();
		else output = ((OMVFormalityLevel)outputIn);
		
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
		return output;
	}

}
