/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter;

import org.oasis.names.tc.ebxml_regrep.xsd.rim.PersonNameType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.PersonType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName;

/**
 * @author David
 *
 */
public class PersonTypeAdapter extends PersonType {

	public void setName(String name) {
		if (getPersonName() == null) {
			setPersonName(new PersonNameType());
		}
		ShortName theName = new ShortName();
		theName.setShortName(name);
		getPersonName().setFirstName(theName);
	}
	
	public void setLastName(String lastname) {
		if (getPersonName() == null) {
			setPersonName(new PersonNameType());
		}
		ShortName theName = new ShortName();
		theName.setShortName(lastname);
		getPersonName().setLastName(theName);
	}

}
