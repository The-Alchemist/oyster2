/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.submit;

import org.neontoolkit.oyster2.client.gui.adapter.submit.setters.InternationalStringTypeSetterAdapter;
import org.neontoolkit.oyster2.client.gui.adapter.submit.setters.SetterAdapter;
import org.neontoolkit.oyster2.client.gui.adapter.submit.setters.SetterAdaptersManager;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.FreeFormText;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringTypeSequence;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.LocalizedString;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.LocalizedStringType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.PersonNameType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.PersonType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName;

/**
 * @author David
 *
 */
public class PersonTypeAdapter extends PersonType {

	
	public void setFirstname(String name) {
		if (getPersonName() == null) {
			setPersonName(new PersonNameType());
		}
		ShortName theName = new ShortName();
		theName.setShortName(name);
		getPersonName().setFirstName(theName);
		if (getPersonName().getLastName() != null) {
			setName(name + getPersonName().getLastName().toString());
		}
		
	}
	
	public void setLastName(String lastname) {
		if (getPersonName() == null) {
			setPersonName(new PersonNameType());
		}
		ShortName theName = new ShortName();
		theName.setShortName(lastname);
		getPersonName().setLastName(theName);
		if (getPersonName().getFirstName() != null ) {
			setName(getPersonName().getFirstName().toString() + lastname);
		}
	}
	
	
	
	private void setName(String text) {
		InternationalStringType intString= new InternationalStringType();
		InternationalStringTypeSequence tempists=new InternationalStringTypeSequence();
		LocalizedStringType templst=new LocalizedStringType();
		FreeFormText tempfft=new FreeFormText();
		tempfft.setFreeFormText((String)text);
		templst.setValue(tempfft);
		templst.setCharset("UTF-8");
		tempists.setLocalizedString(templst);
		intString.addInternationalStringTypeSequence(tempists);
		setName(intString);
	}
	
}
