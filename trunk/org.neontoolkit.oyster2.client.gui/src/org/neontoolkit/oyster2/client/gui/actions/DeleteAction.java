/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.actions;

import java.util.LinkedList;
import java.util.List;

import org.apache.axis2.databinding.types.URI;
import org.apache.axis2.databinding.types.URI.MalformedURIException;
import org.eclipse.jface.action.Action;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.jobs.DeleteObjectsJob;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.ObjectRefType;

/**
 * @author David Muñoz
 *
 */
public class DeleteAction extends Action {
	
	private String[] objectIdentifiers = null; 
	
	@Override
	public void run() {
		ObjectRefType objectRef = null;
		List<ObjectRefType> list = new LinkedList<ObjectRefType>();
		URI uri;
		try {
			for (String id : objectIdentifiers) {
				uri = new URI(id);
				objectRef = new ObjectRefType();
				objectRef.setId(uri);
				list.add(objectRef);
			}
		} catch (MalformedURIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		DeleteObjectsJob job = new DeleteObjectsJob("Submitting remove request" +
				Activator.getWebServersLocator().getCurrentSelection());
		
		String targetService = Activator.getWebServersLocator().getCurrentSelection();
		job.setTargetService(targetService);
		job.setObjectList(list);
		job.setUser(true);
		job.schedule();
		
	}

	/**
	 * @return the objectIdentifiers
	 */
	public final String[] getObjectIdentifiers() {
		return objectIdentifiers;
	}

	/**
	 * @param objectIdentifiers the objectIdentifiers to set
	 */
	public final void setObjectIdentifiers(String[] objectIdentifiers) {
		this.objectIdentifiers = objectIdentifiers;
	}

	
	
	
	
}
