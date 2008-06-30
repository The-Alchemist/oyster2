/**
 * 
 */
package org.neontoolkit.oyster2.client.core;

import org.apache.axis2.databinding.types.URI;
import org.neon_toolkit.omv.api.core.OMVOntology;
import org.neon_toolkit.registry.omv.service.lifecyclemanager.NeOnRegistryOMVOysterStub;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_ebxml.ebXMLTranslator;
import org.neon_toolkit.registry.omv.xsd.rim.Ontology_Type;
import org.oasis.names.tc.ebxml_regrep.xsd.lcm.SubmitObjectsRequest;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectListType;
import org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse;

/**
 * @author David Muñoz
 *
 */
public class LifecyclePortAxis2Adapter {
	
	private NeOnRegistryOMVOysterStub serviceStub = null;
	
	public String submitObjectsRequest(OMVOntology omvOntology) {
		RegistryResponse response = null;
		SubmitObjectsRequest submitObjectsRequest = new SubmitObjectsRequest();
		try {
		
			submitObjectsRequest.setId(new URI("http://submit_objects_request_id_" +
					 System.currentTimeMillis()));
		
			RegistryObjectListType registryObjectListType =
				new RegistryObjectListType();
			Ontology_Type ontologyType =
				ebXMLTranslator.translateOntology(omvOntology);
			
			if (ontologyType == null) {
				return "Invalid data in ontology: ebmXMLTranslator could not serialize ontology";
			}
			
			registryObjectListType.addIdentifiable(ontologyType);
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
