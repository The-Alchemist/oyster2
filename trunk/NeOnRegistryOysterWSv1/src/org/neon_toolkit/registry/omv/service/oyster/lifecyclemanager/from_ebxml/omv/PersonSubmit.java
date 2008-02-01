package org.neon_toolkit.registry.omv.service.oyster.lifecyclemanager.from_ebxml.omv;

/*
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.RegistryObject;

import org.neon_toolkit.registry.omv.service.centrasite.lifecyclemanager.from_ebxml.AssociationSubmit;
import org.neon_toolkit.registry.omv.service.centrasite.lifecyclemanager.from_ebxml.ClassificationSubmit;
*/


import java.util.ArrayList;
import java.util.Collection;

import org.neon_toolkit.omv.api.core.OMVLocation;
import org.neon_toolkit.omv.api.core.OMVPerson;
import org.neon_toolkit.registry.omv.xsd.rim.OMVObjectRefType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.EmailAddressType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.PersonType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.PostalAddressType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.TelephoneNumberType;

public class PersonSubmit extends OMVRegistryObjectSubmit {
	public static Object submit(PersonType input, Object outputIn) {
		
		OMVPerson output;
		if (outputIn==null) output = new OMVPerson();
		else output = ((OMVPerson)outputIn);
		
		if (input.getPersonName().getFirstName()!=null){
			output.setFirstName(input.getPersonName().getFirstName().getShortName());
		}
		if (input.getPersonName().getLastName()!=null){
			output.setFirstName(input.getPersonName().getLastName().getShortName());
		}
		if (input.getEmailAddress()!=null){
			for (EmailAddressType temp_pat: input.getEmailAddress())
				output.addEmail(temp_pat.getAddress().getShortName());
		}
		if (input.getTelephoneNumber()!=null){
			for (TelephoneNumberType temp_pat: input.getTelephoneNumber())
				output.addPhoneNumber(temp_pat.getNumber().getString16());
		}
		//fax i dont know which one in ebxml
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
		return output;
	}
}
