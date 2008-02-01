package org.neon_toolkit.registry.omv.service.oyster.lifecyclemanager.from_ebxml.omv;

/*
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.RegistryObject;

import org.neon_toolkit.registry.omv.service.centrasite.lifecyclemanager.from_ebxml.AssociationSubmit;
import org.neon_toolkit.registry.omv.service.centrasite.lifecyclemanager.from_ebxml.ClassificationSubmit;
*/



import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.neon_toolkit.omv.api.core.OMVLocation;
import org.neon_toolkit.omv.api.core.OMVOntology;
import org.neon_toolkit.omv.api.core.OMVOrganisation;
import org.neon_toolkit.omv.api.core.OMVPerson;
import org.neon_toolkit.registry.omv.xsd.rim.OMVObjectRefType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringTypeSequence;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.OrganizationType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.PostalAddressType;


public class OrganisationSubmit extends OMVRegistryObjectSubmit {
	public static Object submit(OrganizationType input, Object outputIn) {
		
		OMVOrganisation output;
		if (outputIn==null) output = new OMVOrganisation();
		else output = ((OMVOrganisation)outputIn);
		
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
		if (input.getAddress()!=null){
			for (PostalAddressType temp_pat: input.getAddress()){
				OMVLocation temp = new OMVLocation();
				temp.setCity(temp_pat.getCity().getShortName());
				temp.setCountry(temp_pat.getCountry().getShortName());
				temp.setState(temp_pat.getStateOrProvince().getShortName());
				temp.setStreet(temp_pat.getStreet().getShortName());
				output.addIsLocatedAt(temp);
			}
		}
		if (input.getPrimaryContact()!=null){
			String t1=input.getPrimaryContact().toString();
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
				output.addHasContactPerson(temp);
			}
		}
		return output;
	}
}
