/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.actions;

import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.adapter.IOMVObject;
import org.neontoolkit.oyster2.client.gui.adapter.submit.PartySubmitAdapter;
import org.neontoolkit.oyster2.client.gui.adapter.submit.PropertiesConfiguredAdapter;
import org.neontoolkit.oyster2.client.gui.dialogs.PropertiesConfiguredSubmitDialog;
import org.neontoolkit.oyster2.client.gui.dialogs.content.InputComposite;
import org.neontoolkit.oyster2.client.gui.dialogs.content.PartyComposite.PartyMembers;
import org.neontoolkit.oyster2.client.gui.jobs.SubmitObjectsJob;
import org.neontoolkit.oyster2.client.gui.views.SearchResultsView;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType;

/**
 * @author David Muñoz
 *
 */
public class UpdateAction extends Action {

	private IOMVObject objectToUpdate = null;
	
	private List<PartyMembers> parties = null;
	
	@Override
	public void run() {
		Shell shell = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
		PropertiesConfiguredSubmitDialog dialog =
			new PropertiesConfiguredSubmitDialog("UPDATE_SECTION",shell);
		SubmitFieldReader fieldReader = new SubmitFieldReader();
		
		
		dialog.setObjectToUpdate(objectToUpdate);
		int result = dialog.open();
		if (result == Dialog.OK) {
			String adapterFileName = Activator.getDefault().getResourcesDir() +
			 dialog.getAdapterFileName();
			
			PropertiesConfiguredAdapter adapter = 
				new PropertiesConfiguredAdapter(adapterFileName);
			Map<String,InputComposite> inputComposites = dialog.getComposites();
			Map<String,Object> inputObjects = fieldReader.getFields(inputComposites);
			
			parties = fieldReader.getParties();
			List<RegistryObjectType> partyObjects = null;
			if (parties.size() != 0) {
				partyObjects = 
					PartySubmitAdapter.getInstance().getPartyObjects(parties);
			}
			
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
                  "The attribute values were not valid");
				return;
			}
			else {
			SubmitObjectsJob job = new SubmitObjectsJob("update "+registryObject.getId() + 
					" at " + Activator.getWebServersLocator().getCurrentSelection());
			
			String targetService = Activator.getWebServersLocator().getCurrentSelection();
			job.setTargetService(targetService);
			job.setOmvObject(registryObject);
			job.setParties(partyObjects);
			job.setOperation(SubmitObjectsJob.UPDATE);
			job.setUser(true);
			job.schedule();
			}
		}
	
	}

	

	/**
	 * @return the objectToUpdate
	 */
	public final IOMVObject getObjectToUpdate() {
		return objectToUpdate;
	}

	/**
	 * @param objectToUpdate the objectToUpdate to set
	 */
	public final void setObjectToUpdate(IOMVObject objectToUpdate) {
		this.objectToUpdate = objectToUpdate;
	}

	
	
	/*
	private void makeEnableListener() {
		ISelectionChangedListener listener = new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				Object [] selectedResults =
					((IStructuredSelection)view.getViewer().getSelection()).toArray();
				if (selectedResults.length == 1)
					setEnabled(true);
				else
					setEnabled(false);
				
			}
			
		};
		view.getViewer().addSelectionChangedListener(listener);
	}*/

}
