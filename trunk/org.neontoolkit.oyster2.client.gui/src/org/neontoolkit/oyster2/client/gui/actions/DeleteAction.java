/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.actions;

import java.util.LinkedList;
import java.util.List;

import org.apache.axis2.databinding.types.URI;
import org.apache.axis2.databinding.types.URI.MalformedURIException;
import org.eclipse.jface.action.Action;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.ObjectRefType;

/**
 * @author David Muñoz
 *
 */
public class DeleteAction extends Action {
	
	private String[] ids = null; 
	
	@Override
	public void run() {
		ObjectRefType objectRef = null;
		List<ObjectRefType> list = new LinkedList<ObjectRefType>();
		URI uri;
		try {
			for (String id : ids) {
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
		
		
	}

	/**
	 * @return the ids
	 */
	public final String[] getIds() {
		return ids;
	}

	/**
	 * @param ids the ids to set
	 */
	public final void setIds(String[] ids) {
		this.ids = ids;
	}

	
	
	
}
