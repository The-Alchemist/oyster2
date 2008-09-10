package org.neon_toolkit.registry.omv.service.oyster.lifecyclemanager.from_ebxml.omv;

/*
import javax.xml.registry.JAXRException;
import javax.xml.registry.LifeCycleManager;
import javax.xml.registry.infomodel.RegistryObject;

import org.neon_toolkit.registry.omv.service.centrasite.lifecyclemanager.from_ebxml.AssociationSubmit;
import org.neon_toolkit.registry.omv.service.centrasite.lifecyclemanager.from_ebxml.ClassificationSubmit;
*/

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.neon_toolkit.omv.api.core.OMVKnowledgeRepresentationParadigm;
import org.neon_toolkit.omv.api.core.OMVOntology;
import org.neon_toolkit.omv.api.core.OMVOrganisation;
import org.neon_toolkit.omv.api.core.OMVPerson;
import org.neon_toolkit.registry.omv.xsd.rim.KnowledgeRepresentationParadigmType;
import org.neon_toolkit.registry.omv.xsd.rim.OMVObjectRefType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringTypeSequence;

public class KnowledgeRepresentationParadigmSubmit extends
		OMVRegistryObjectSubmit {
	public static Object submit(KnowledgeRepresentationParadigmType input, Object outputIn) {
		
		OMVKnowledgeRepresentationParadigm output;
		if (outputIn==null) output = new OMVKnowledgeRepresentationParadigm();
		else output = ((OMVKnowledgeRepresentationParadigm)outputIn);
		
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
		
		if (input.getSpecifiedBy()!=null){
			for (int i=0;i<input.getSpecifiedBy().length;i++) {
				String t1=input.getSpecifiedBy()[i].getId().toString();
				
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
					output.addSpecifiedBy(temp);
				}
				else{
					OMVOrganisation temp = new OMVOrganisation();
					temp.setName(t1);
					output.addSpecifiedBy(temp);
				}
			}
		}
				
		return output;
	}
}