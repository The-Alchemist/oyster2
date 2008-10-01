/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.actions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.neontoolkit.oyster2.client.gui.dialogs.content.InputComposite;
import org.neontoolkit.oyster2.client.gui.dialogs.content.PersonSelectionComposite;
import org.neontoolkit.oyster2.client.gui.dialogs.content.PartyComposite.PartyMembers;

/**
 * @author David Muñoz
 *
 */
public class SubmitFieldReader {
	
	private List<PartyMembers> parties = null;
	
	public Map<String,Object> getFields(Map<String, InputComposite> inputComposites) {
		Object value = null;
		PartyMembers party = null;
		
		Map<String,Object> inputObjects = new HashMap<String, Object>();
		parties = new LinkedList<PartyMembers>();
		String key = null;
		for (Map.Entry<String, InputComposite> entry : inputComposites.entrySet()) {
			key = entry.getKey();
			value = entry.getValue().getInput();
			if (value == null)
				continue;
			if (value instanceof String) {
				if (((String)value).trim().equals(""))
					continue;
			}
			else if (value instanceof String[]) {
				if (((String[])value).length == 0)
					continue;
			}
			if (value instanceof PartyMembers) {
				party = (PartyMembers)value;
				parties.add(party);
				serializeParty(key,party,inputObjects);
			}
			else {
				inputObjects.put(key,value);
			}
		}
		return inputObjects;
	}
	
	private void serializeParty(String key, PartyMembers party,
			Map<String,Object> inputObjects ) {
		
		
		String [] ids = new String[party.getOrganizations().length +
		                           party.getPeople().size()];
		if (ids.length == 0)
			return;
		int i = 0;
		for (String organization : party.getOrganizations()) {
			ids[i] = organization;
			i++;
		}
		for (PersonSelectionComposite.Person person : party.getPeople()) {
			ids[i] = person.getName() + person.getLastname();
			i++;
		}
		inputObjects.put(key, ids);
	}

	/**
	 * @return the parties
	 */
	public final List<PartyMembers> getParties() {
		return parties;
	}

	/**
	 * @param parties the parties to set
	 */
	public final void setParties(List<PartyMembers> parties) {
		this.parties = parties;
	}

	
}
