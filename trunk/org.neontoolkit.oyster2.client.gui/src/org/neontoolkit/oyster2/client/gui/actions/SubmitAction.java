/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.actions;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.adapter.submit.OntologyAdapter;
import org.neontoolkit.oyster2.client.gui.adapter.submit.PropertiesConfiguredAdapter;
import org.neontoolkit.oyster2.client.gui.dialogs.InputDialog;
import org.neontoolkit.oyster2.client.gui.dialogs.PropertiesConfiguredSubmitDialog;
import org.neontoolkit.oyster2.client.gui.dialogs.content.InputComposite;
import org.neontoolkit.oyster2.client.gui.dialogs.content.PartyComposite;
import org.neontoolkit.oyster2.client.gui.dialogs.content.PersonSelectionComposite;
import org.neontoolkit.oyster2.client.gui.dialogs.content.PartyComposite.PartyMembers;
import org.neontoolkit.oyster2.client.gui.dialogs.content.PersonSelectionComposite.Person;
import org.neontoolkit.oyster2.client.gui.jobs.SubmitObjectsJob;
import org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType;



/**
 * @author David Muñoz
 *
 */
public class SubmitAction implements IWorkbenchWindowActionDelegate {

	
	
	private List<PartyMembers> parties = null;
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		
		Shell shell = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
		PropertiesConfiguredSubmitDialog dialog =
			new PropertiesConfiguredSubmitDialog("SUBMIT_SECTION",shell);
		
		int result = dialog.open();
		if (result == Dialog.OK) {
			String adapterFileName = Activator.getDefault().getResourcesDir() +
			 dialog.getAdapterFileName();
			
			PropertiesConfiguredAdapter adapter = 
				new PropertiesConfiguredAdapter(adapterFileName);
			Map<String,InputComposite> inputComposites = dialog.getComposites();
			Map<String,Object> inputObjects = getFields(inputComposites);
			//sendParty();
			
			RegistryObjectType registryObject = null;
			try {
				registryObject = adapter.makeAxisObject(inputObjects);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
			
			if (registryObject == null) {
				MessageDialog.openError(shell, 
                  "Error", 
                  "The ontology attribute values were not valid");
				return;
			}
			else {
			SubmitObjectsJob job = new SubmitObjectsJob("submit "+registryObject.getId() + 
					" to " + Activator.getWebServersLocator().getCurrentSelection());
			
			String targetService = Activator.getWebServersLocator().getCurrentSelection();
			job.setTargetService(targetService);
			job.setOmvObject(registryObject);
			
			job.setUser(true);
			job.schedule();
			}
		}
	}

/*
	private void sendParty() {
		for (PartyMembers party : parties) {
			sendPeople(party.getPeople());
			sendOrganizations(party.getOrganizations());
		}
		
	}

	private void sendPeople(Set<Person> people) {
		Map<String, Object>
		for (Person person : people) {
			
		}
		
	}
*/
	private Map<String,Object> getFields(Map<String, InputComposite> inputComposites) {
		Object value = null;
		PartyMembers party = null;
		
		Map<String,Object> inputObjects = new HashMap<String, Object>();
		parties = new LinkedList<PartyMembers>();
		for (Map.Entry<String, InputComposite> entry : inputComposites.entrySet()) {
			value = entry.getValue().getInput();
			if (value instanceof PartyMembers) {
				party = (PartyMembers)value;
				parties.add(party);
				serializeParty(entry.getKey(),party,inputObjects);
			}
			else {
				inputObjects.put(entry.getKey(),value);
			}
		}
		return inputObjects;
	}

	private void serializeParty(String key, PartyMembers party,
			Map<String,Object> inputObjects ) {
		
		
		String [] ids = new String[party.getOrganizations().length +
		                           party.getPeople().size()];
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

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
