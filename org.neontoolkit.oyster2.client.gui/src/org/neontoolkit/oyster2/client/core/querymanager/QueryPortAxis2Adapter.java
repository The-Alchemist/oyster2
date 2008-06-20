/**
 * 
 */
package org.neontoolkit.oyster2.client.core.querymanager;

import java.util.Map;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axis2.databinding.ADBException;
import org.apache.axis2.databinding.types.URI;
import org.apache.axis2.databinding.types.URI.MalformedURIException;
import org.neon_toolkit.registry.omv.service.querymanager.NeOnRegistryOMVOysterStub;
import org.neontoolkit.oyster2.client.core.QueryResponse;
import org.neontoolkit.oyster2.client.core.querymanager.querybuilders.RegistryObjectQueryBuilder;
import org.oasis.names.tc.ebxml_regrep.xsd.query.AdhocQueryRequest;
import org.oasis.names.tc.ebxml_regrep.xsd.query.AdhocQueryResponse;
import org.oasis.names.tc.ebxml_regrep.xsd.query.RegistryObjectQuery;
import org.oasis.names.tc.ebxml_regrep.xsd.query.RegistryObjectQueryType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.ResponseOptionReturnType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.ResponseOptionType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.AdhocQueryType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.QueryExpressionType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI;


/**
 * @author David Muñoz
 *
 */
public class QueryPortAxis2Adapter {

	public static final String RETURN_TYPE_REFERENCES = "ObjectRef";
	
	public static final String RETURN_TYPE_LEAF_CLASS ="LeafClass";
	
	public static final String RETURN_TYPE_LEAF_CLASS_WITH_REPOS_ITEM =
		"LeafClassWithRespositoryItem";
	
	public static ReferenceURI QUERY_LANGUAGE = null;
	
	//this is the default value for return type
	public static final String RETURN_TYPE_REG_OBJECT ="RegistryObject";
	
	private NeOnRegistryOMVOysterStub serviceStub = null;
	
	static {
		QUERY_LANGUAGE = new ReferenceURI();
		URI queryURI;
		try {
			queryURI = new URI("urn:oasis:names:tc:ebxml-regrep:QueryLanguage:ebRSFilterQuery");
		} catch (MalformedURIException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		QUERY_LANGUAGE.setReferenceURI(queryURI);
		
		
		
	}
	
	
	public QueryResponse submitQueryRequest(String mainQueryTargetClassName,String returnType, 
			Map<String,Object> filters ) throws Exception,
						IllegalAccessException, ClassNotFoundException {
		// the values for the Map may be
		// a FilterType for primitive properties, like String or Date
		// an array of SimpleFilter for name
		// a List<Map<String,Object>> when it's a reference to another
		// instance
		AdhocQueryRequest queryRequest = makeFixedHeader(returnType);
		
		// the query element
		OMElement element = makeQuery(mainQueryTargetClassName,filters);
		queryRequest.getAdhocQuery().getQueryExpression().setExtraElement(element);
		QueryResponse queryResponse = new QueryResponse();
		//response.setStringMessage(stringMessage)
		
		AdhocQueryResponse adhocQueryResponse =
			serviceStub.submitAdhocQuery(queryRequest);
		
		String message = adhocQueryResponse.getStatus().toString();
		int beginIndex = message.lastIndexOf(":")+1;
		queryResponse.setStringMessage(message.substring(beginIndex));
		queryResponse.setObjects(adhocQueryResponse.getRegistryObjectList().getIdentifiable());
		return queryResponse;
	}
	
	
	private AdhocQueryRequest makeFixedHeader(String returnType) throws Exception {
		AdhocQueryRequest queryRequest = new AdhocQueryRequest();
		queryRequest.setId(new URI("http://query_request_" + System.currentTimeMillis()));
		
		
		// we don't touch the federated attribute and responseOption element 
		ResponseOptionType responseOptionType = new ResponseOptionType();
		ResponseOptionReturnType responseOptionReturnType = null;
		if (returnType != null) {
			if (returnType.equals(RETURN_TYPE_REG_OBJECT)) {
			 responseOptionReturnType =
				 ResponseOptionReturnType.RegistryObject;
			}
			else if (returnType.equals(RETURN_TYPE_REFERENCES)) {
				responseOptionReturnType =
					ResponseOptionReturnType.ObjectRef;
			}
			else if (returnType.equals(RETURN_TYPE_LEAF_CLASS)) {
				responseOptionReturnType =
					ResponseOptionReturnType.LeafClass;
			}
			else if (returnType.equals(RETURN_TYPE_LEAF_CLASS_WITH_REPOS_ITEM)) {
				responseOptionReturnType =
					ResponseOptionReturnType.LeafClassWithRepositoryItem;
			}
			else {
				throw new RuntimeException("Unsupported response option return type");
			}
			responseOptionType.setReturnType(responseOptionReturnType);
		}
		else {
			//default behaviour will be to return the registry object
			//responseOptionReturnType = ResponseOptionReturnType.RegistryObject;
		}
		
		
		
		//AdhocQueryType and QueryExpressionType
		AdhocQueryType adhocQueryType = makeAdhocQueryType();
		adhocQueryType.setId(new URI("urn:query:adhocQueryType:adhoc" +
				System.currentTimeMillis()));
		queryRequest.setResponseOption(responseOptionType);
		queryRequest.setAdhocQuery(adhocQueryType);
		
		return queryRequest;
	}
	
	private AdhocQueryType makeAdhocQueryType() {
		AdhocQueryType adhocQueryType = new AdhocQueryType();
		QueryExpressionType queryExpressionType =
			new QueryExpressionType();
		queryExpressionType.setQueryLanguage(QUERY_LANGUAGE);
		adhocQueryType.setQueryExpression(queryExpressionType);
		
		return adhocQueryType;
	}
	
	
	

	private OMElement makeQuery(String mainQueryTarget,
			Map<String,Object> filters) throws ADBException,Exception {
		
		RegistryObjectQueryType query =
			RegistryObjectQueryBuilder.getInstance().buildQuery(
					Class.forName(mainQueryTarget),filters);
		return query.getOMElement(RegistryObjectQuery.MY_QNAME,OMAbstractFactory.getOMFactory());
		
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
