package org.neontoolkit.oyster2.client.gui.adapter.submit;

import java.util.HashSet;
import java.util.Set;

import org.neontoolkit.oyster2.client.gui.dialogs.content.PersonSelectionComposite;
import org.neontoolkit.oyster2.client.gui.dialogs.content.PersonSelectionComposite.Person;

/**
 * @author David Muñoz
 *
 */
public class PartyMembers {
	private Set<Person> people = new HashSet<Person>();
	
	private String[] organizations = new String[0];

	
	
	/**
	 * @return the organizations
	 */
	public final String[] getOrganizations() {
		return organizations;
	}

	/**
	 * @param organizations the organizations to set
	 */
	public final void setOrganizations(String[] organizations) {
		this.organizations = organizations;
	}

	public Set<Person> getPeople() {
		return people;
	}

	public void setPeople(Set<Person> people) {
		this.people = people;
	}
	
}