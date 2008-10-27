/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.submit;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.dialogs.content.PersonSelectionComposite.Person;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType;

/**
 * @author David
 *
 */
public class PartySubmitAdapter {
	private static final String FIRSTNAME_KEY = "firstname";
	private static final String NAME_KEY = "name";
	private static final String LASTNAME_KEY = "lastname";
	private PropertiesConfiguredAdapter peopleAdapter = null;
	private PropertiesConfiguredAdapter organizationsAdapter = null;
	private static final String PEOPLE_CONFIGURATION_PATH = 
		Activator.getDefault().getResourcesDir() + "submitAdapterFiles" +
		File.separator + "PersonAdapterType.submit.adapter.properties";
	private static final String ORGANIZATIONS_CONFIGURATION_PATH = 
		Activator.getDefault().getResourcesDir() + "submitAdapterFiles" +
		File.separator +  "OrganizationType.submit.adapter.properties";
	
	
	public PartySubmitAdapter() {
		peopleAdapter=new PropertiesConfiguredAdapter(PEOPLE_CONFIGURATION_PATH);
		organizationsAdapter=new PropertiesConfiguredAdapter(ORGANIZATIONS_CONFIGURATION_PATH);
	}
	
	private final static PartySubmitAdapter instance =
		new PartySubmitAdapter();
	
	public List<RegistryObjectType> getPartyObjects(List<PartyMembers> parties) {
		
		List<RegistryObjectType> objectList = new LinkedList<RegistryObjectType>();
		try {
			for (PartyMembers party : parties) {
			
				getPeople(objectList,party);
				getOrganizations(objectList,party);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return objectList;
	}
	private void getOrganizations(List<RegistryObjectType> objectList,
			PartyMembers party) throws InstantiationException, IllegalAccessException {
		RegistryObjectType omvObject = null;
		for (String name : party.getOrganizations()) {
			Map<String,Object> input = new HashMap<String, Object>();
			input.put(NAME_KEY,name);
			omvObject = organizationsAdapter.makeAxisObject(input);
			objectList.add(omvObject);
		}
		
	}

	private void getPeople(List<RegistryObjectType> objectList,
			PartyMembers party) throws InstantiationException, IllegalAccessException {
		RegistryObjectType omvObject = null;
		for (Person person : party.getPeople()) {
			Map<String,Object> input = new HashMap<String, Object>();
			input.put(FIRSTNAME_KEY,person.getName());
			input.put(LASTNAME_KEY,person.getLastname().replace(" ",""));
			omvObject = peopleAdapter.makeAxisObject(input);
			objectList.add(omvObject);
		}
		
	}
	public static PartySubmitAdapter getInstance() {
		return instance;
	}
	
}
