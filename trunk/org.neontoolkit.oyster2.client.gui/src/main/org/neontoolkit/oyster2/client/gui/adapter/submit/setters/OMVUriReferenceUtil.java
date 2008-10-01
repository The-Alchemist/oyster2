/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.submit.setters;

import org.apache.axis2.databinding.types.URI;
import org.apache.axis2.databinding.types.URI.MalformedURIException;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI;

/**
 * @author David Muñoz
 *
 */
public class OMVUriReferenceUtil {
	public static final ReferenceURI makeReferenceURI4OMV(String ref) {
    	URI param;
    	String params = ref.replaceAll(" ","%20");
		try {
			
			/*
			if (params.contains("://")) param=new URI(params);
			else param = new URI("#"+params);
			 */
			param = new URI(params);
			ReferenceURI temp_refURI = new ReferenceURI();
	    	temp_refURI.setReferenceURI(param);
	    	
	    	return temp_refURI;
		} catch (MalformedURIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }

}
