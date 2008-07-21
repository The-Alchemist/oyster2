/**
 * 
 */
package org.neontoolkit.oyster2.client.core.lifecycle;

import java.util.List;

import org.apache.axis2.databinding.types.URI;
import org.neontoolkit.registry.omv.service.lifecyclemanager.NeOnRegistryOMVOysterStub;
import org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType;
import org.oasis.names.tc.ebxml_regrep.xsd.lcm.SubmitObjectsRequest;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectListType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType;
import org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse;

/**
 * @author David Muñoz
 *
 */
public class LifecyclePortAxis2Adapter {
private NeOnRegistryOMVOysterStub serviceStub = null;
	
	public String submitObjectsRequest(List<RegistryObjectType> omvObjectList) {
		RegistryResponse response = null;
		SubmitObjectsRequest submitObjectsRequest = new SubmitObjectsRequest();
		try {
			submitObjectsRequest.setId(new URI("http://submit_objects_request_id_" +
					 System.currentTimeMillis()));
		
			RegistryObjectListType registryObjectListType =
				new RegistryObjectListType();
			for (RegistryObjectType omvObject : omvObjectList) {
				registryObjectListType.addIdentifiable(omvObject);
			}
			submitObjectsRequest.setRegistryObjectList(registryObjectListType);
			response = serviceStub.submitObjects(submitObjectsRequest);
			
		}
		catch (Exception e) {
			return e.getMessage();
		}
		
		String message = response.getRegistryResponse().getStatus().toString();
		int beginIndex = message.lastIndexOf(":")+1;
		return message.substring(beginIndex);
		
		
	}

	/**
	 * @return the serviceStub
	 */
	public final NeOnRegistryOMVOysterStub getServiceStub() {
		return serviceStub;
	}

	/**
	 * @param serviceStub the serviceStub to set
	 */
	public final void setServiceStub(NeOnRegistryOMVOysterStub serviceStub) {
		this.serviceStub = serviceStub;
	}
	
	
}
