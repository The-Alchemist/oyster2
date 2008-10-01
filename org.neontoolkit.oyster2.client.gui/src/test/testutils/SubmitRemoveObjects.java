/**
 * 
 */
package testutils;

import java.util.LinkedList;
import java.util.List;

import org.apache.axis2.databinding.types.URI;
import org.apache.axis2.databinding.types.URI.MalformedURIException;
import org.neontoolkit.oyster2.client.core.lifecycle.LifecyclePortAxis2Adapter;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.ObjectRefType;

/**
 * @author David Muñoz
 *
 */
public class SubmitRemoveObjects {
	public static String remove(List<String> ids) {
		List<ObjectRefType> omvObjectList = new LinkedList<ObjectRefType>();
		ObjectRefType object = null;
		URI uri = null;
		try {
			for (String thisId : ids ) {
				uri = new URI(thisId);
				object = new ObjectRefType();
				object.setId(uri);
				omvObjectList.add(object);
			}
		}
		catch (MalformedURIException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		LifecyclePortAxis2Adapter lifecyclePortAxis2Adapter =
			new LifecyclePortAxis2Adapter();
		lifecyclePortAxis2Adapter.setTargetService("http://localhost:8080/axis2/services/NeOnRegistryOMVOyster");
		lifecyclePortAxis2Adapter.setTimeoutInMiliseconds(4 * 60 * 1000);
		return lifecyclePortAxis2Adapter.removeObjectsRequest(omvObjectList);
	}
}
