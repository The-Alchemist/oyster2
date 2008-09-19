package org.neontoolkit.collab.core;

import org.eclipse.jface.preference.IPreferenceStore;
import org.neontoolkit.omv.api.core.OMVPerson;
import org.neontoolkit.registry.oyster2.Constants;

public class OysterTools {
	
	public static  void setRole(OMVPerson person, String role){
		if(role.equalsIgnoreCase("Subject Expert") || role.contains("SubjectExpert")){
			person.setHasRole(Constants.SubjectExpert);
		} else if(role.equalsIgnoreCase("Viewer") || role.contains("Viewer")){
			person.setHasRole(Constants.Viewer);
		} else if(role.equalsIgnoreCase("Validator") || role.contains("Validator")){
			person.setHasRole(Constants.Validator);
		}
	}

	public static OMVPerson getCurrentUser(IPreferenceStore _store){
		String role = _store.getString("ROLE");	
		String firstname = _store.getString("USER_FIRSTNAME");
		String lastname = _store.getString("USER_LASTNAME");
		
		OMVPerson currentPerson = new OMVPerson();
		currentPerson.setFirstName(firstname);
		currentPerson.setLastName(lastname);
		setRole(currentPerson, role);
		
		return currentPerson;
		
	}
}
