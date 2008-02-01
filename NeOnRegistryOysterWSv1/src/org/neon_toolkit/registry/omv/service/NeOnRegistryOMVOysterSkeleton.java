/**
 * NeOnRegistryOMVOysterSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package org.neon_toolkit.registry.omv.service;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.apache.axis2.databinding.types.URI;
import org.neon_toolkit.omv.api.core.OMVOntology;
import org.neon_toolkit.registry.api.Oyster2Connection;
import org.neon_toolkit.registry.api.Oyster2Manager;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_sparql.RegistryObjectQueryParser;
import org.neon_toolkit.registry.omv.service.oyster.querymanager.to_ebxml.ebXMLTranslator;
import org.neon_toolkit.registry.omv.service.oyster.lifecyclemanager.from_ebxml.RegistryObjectSubmit;
import org.neon_toolkit.registry.omv.xsd.rim.Ontology_Type;
import org.oasis.names.tc.ebxml_regrep.xsd.query.AdhocQueryResponse;
import org.oasis.names.tc.ebxml_regrep.xsd.query.RegistryObjectQueryType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.ResponseOptionReturnType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.IdentifiableType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringTypeSequence;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.ObjectRefListTypeSequence;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.QueryExpressionType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectListType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType;
import org.oasis.names.tc.ebxml_regrep.xsd.rs.ExtensionMapper;
import org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse;
import org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponseType;





/**
 *  NeOnRegistryOMVOysterSkeleton java skeleton for the axisService
 */
public class NeOnRegistryOMVOysterSkeleton
    implements NeOnRegistryOMVOysterSkeletonInterface {
	static Oyster2Connection oyster2Conn = null;
	private URI requestId;
	private ReferenceURI statusFailure = null;
	private ReferenceURI statusSuccess = null;
	private static int numberOfCondition; 
	
	
	/**
     * Auto generated method signature
     * @param queryRequest
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.AdhocQueryResponse submitAdhocQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.AdhocQueryRequest queryRequest) {
        //TODO : fill this with the necessary business logic
    	
        connectManager();
    	AdhocQueryResponse res=new AdhocQueryResponse();
    	requestId=queryRequest.getId();
       	res.setRequestId(requestId);
       	//search
       	QueryExpressionType qexp = queryRequest.getAdhocQuery().getQueryExpression();
       	//check the query language. only ebRSFilterQuery is supported for now
       	if (!qexp.getQueryLanguage().getReferenceURI().toString().equals("urn:oasis:names:tc:ebxml-regrep:QueryLanguage:ebRSFilterQuery")){
       		throw new java.lang.UnsupportedOperationException("Unsupported query language.");
       	}
       	//check the return type. only ObjectRef and LeafClass are supported for now
       	if (queryRequest.getResponseOption()==null || queryRequest.getResponseOption().getReturnType()==null)
       		//default for this service
       		ebXMLTranslator.setReturnType(ebXMLTranslator.RETURNTYPE_LeafClass);
       	else
 	    if (queryRequest.getResponseOption().getReturnType().equals(ResponseOptionReturnType.LeafClass))
 	       		ebXMLTranslator.setReturnType(ebXMLTranslator.RETURNTYPE_LeafClass);
 	    else
 	    if (queryRequest.getResponseOption().getReturnType().equals(ResponseOptionReturnType.ObjectRef))
 	       		ebXMLTranslator.setReturnType(ebXMLTranslator.RETURNTYPE_ObjectRef);
 	    else
 	       		throw new java.lang.UnsupportedOperationException("Unsupported return type.");
       	//TODO check the return type. only ObjectRef and LeafClass are supported for now
       	
       	RegistryObjectListType reslist=new RegistryObjectListType();
	    RegistryObjectQueryType the_query;
    	
	    try {
	       	// translate the registry query object and parse it	       		
	       	the_query = (RegistryObjectQueryType) ExtensionMapper.getTypeObject(qexp.getExtraElement().getNamespace().getNamespaceURI(), 
						qexp.getExtraElement().getLocalName()+"Type", 
						qexp.getExtraElement().getXMLStreamReader());
	    } catch (Exception e1) {
			e1.printStackTrace();
     		throw new java.lang.UnsupportedOperationException("Error in the query expression. Possible unsupported query object.");
		}
	    
	    try {
	    	numberOfCondition=0;
	       	RegistryObjectQueryParser query_parsed=RegistryObjectQueryParser.parse(the_query);
	       	System.out.println(query_parsed.objectType+query_parsed.queryPredicate);
	       	//execute the query
	       	
	    
			Set<Object> OMVSet4 = oyster2Conn.submitAdHocQuery(query_parsed.objectType+query_parsed.queryPredicate);
			Iterator it4 = OMVSet4.iterator();
			IdentifiableType[] result_array;
			try{
				while(it4.hasNext()){
					Object ready=it4.next();
					result_array = ebXMLTranslator.translate(ready);
					for (IdentifiableType one_identifiable:result_array)
						reslist.addIdentifiable(one_identifiable);
				}
			}catch(Exception ignore){
				//	-- ignore
			}
			
	    } catch (Exception e1) {
			e1.printStackTrace();
			//closeManager();
     		throw new java.lang.UnsupportedOperationException(e1.getMessage());
		}
	    
	    
	    //Sets the number of results
		res.setRegistryObjectList(reslist);
		long temp_numresults=res.getRegistryObjectList().getIdentifiable()==null? 0 : res.getRegistryObjectList().getIdentifiable().length;
	    res.setStartIndex(BigInteger.ZERO);
	    res.setTotalResultCount(BigInteger.valueOf(temp_numresults));
	       	
	    //Sets the response status
	    try {
	       		if (temp_numresults>0)
	       			res.setStatus(statusSuccess);
	       		else
	       			res.setStatus(statusFailure);
	    } catch (Exception e) {}
    	
   		return res;
   		//throw new java.lang.UnsupportedOperationException("Please implement " +
        //    this.getClass().getName() + "#submitAdhocQuery");
    }
    /**
     * Auto generated method signature
     * @param approveObjectsRequest5
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse approveObjects(
        org.oasis.names.tc.ebxml_regrep.xsd.lcm.ApproveObjectsRequest approveObjectsRequest5) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#approveObjects");
    }

    /**
     * Auto generated method signature
     * @param deprecateObjectsRequest7
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse deprecateObjects(
        org.oasis.names.tc.ebxml_regrep.xsd.lcm.DeprecateObjectsRequest deprecateObjectsRequest7) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#deprecateObjects");
    }

    /**
     * Auto generated method signature
     * @param undeprecateObjectsRequest9
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse undeprecateObjects(
        org.oasis.names.tc.ebxml_regrep.xsd.lcm.UndeprecateObjectsRequest undeprecateObjectsRequest9) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#undeprecateObjects");
    }

    /**
     * Auto generated method signature
     * @param removeObjectsRequest11
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse removeObjects(
        org.oasis.names.tc.ebxml_regrep.xsd.lcm.RemoveObjectsRequest objectRequest) {
        //TODO : fill this with the necessary business logic
    	connectManager();
    	RegistryResponse bigres = new RegistryResponse();
 	  	RegistryResponseType res = new RegistryResponseType();
 	  	bigres.setRegistryResponse(res);
 	  	res.setRequestId(objectRequest.getId());
 	  	
 	  	try {
 	  		ObjectRefListTypeSequence[] rolist = objectRequest.getObjectRefList().getObjectRefListTypeSequence();
	  		for (int i=0;i<rolist.length;i++) {
	  			String w=rolist[i].getObjectRef().getId().toString();
	  			if (w.indexOf("location=")!=-1){ //ONLY onotologies and mappings are now supported by the API
	  				OMVOntology toRemove = new OMVOntology();
	  				Pattern p = Pattern.compile("[? ;]+");
	  				String[] result =  p.split(w);
	  				for (int ix=0; ix<result.length; ix++){
	  		            if(result[ix].indexOf("version=")!=-1){
	  		            	toRemove.setVersion(result[ix].substring(8, result[ix].length()));
	  		            }
	  		            else if(result[ix].indexOf("location=")!=-1){
	  		            	toRemove.setResourceLocator(result[ix].substring(9, result[ix].length()));
	  		            }
	  		            else{
	  		            	toRemove.setURI(result[ix]);
	  		            }
	  				}
	  				oyster2Conn.remove(toRemove);
	  			}
	  		}
 	    	 			
 			
 	  	} catch (Exception e) {
  			res.setStatus(statusFailure);
 			e.printStackTrace();
 		}
 	  	res.setStatus(statusSuccess);
 	  	//closeManager();
 	  	return bigres;

        //throw new java.lang.UnsupportedOperationException("Please implement " +
        //    this.getClass().getName() + "#removeObjects");
    }

    /**
     * Auto generated method signature
     * @param objectRequest
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse submitObjects(
        org.oasis.names.tc.ebxml_regrep.xsd.lcm.SubmitObjectsRequest objectRequest) {
        //TODO : fill this with the necessary business logic
    	connectManager();
    	RegistryResponse bigres = new RegistryResponse();
 	  	RegistryResponseType res = new RegistryResponseType();
 	  	bigres.setRegistryResponse(res);
 	  	res.setRequestId(objectRequest.getId());
 	  	
 	  	try {
 	      	ArrayList<IdentifiableType> newregobjects = new ArrayList<IdentifiableType>();
 	      	for (IdentifiableType one_ro: objectRequest.getRegistryObjectList().getIdentifiable())
 				newregobjects.add(one_ro);
 	    	 			
 			for (Object one_ro:newregobjects) {
 				RegistryObjectType tempro=(RegistryObjectType)one_ro;
 				Object tempnew = RegistryObjectSubmit.submit(tempro);
 				if (tempnew!=null){
 					if (tempnew instanceof OMVOntology)
 						System.out.println(Oyster2Manager.serializeOMV(tempnew));
 					oyster2Conn.register(tempnew);
 				}
 			}
 			
 	  	} catch (Exception e) {
  			res.setStatus(statusFailure);
 			e.printStackTrace();
 		}
 	  	res.setStatus(statusSuccess);
 	  	//closeManager();
 	  	return bigres;

        //throw new java.lang.UnsupportedOperationException("Please implement " +
        //    this.getClass().getName() + "#submitObjects");
    }

    /**
     * Auto generated method signature
     * @param updateObjectsRequest15
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse updateObjects(
        org.oasis.names.tc.ebxml_regrep.xsd.lcm.UpdateObjectsRequest objectRequest) {
        //TODO : fill this with the necessary business logic
    	connectManager();
    	RegistryResponse bigres = new RegistryResponse();
 	  	RegistryResponseType res = new RegistryResponseType();
 	  	bigres.setRegistryResponse(res);
 	  	res.setRequestId(objectRequest.getId());
 	  	
 	  	try {
 	      	ArrayList<IdentifiableType> newregobjects = new ArrayList<IdentifiableType>();
 	      	for (IdentifiableType one_ro: objectRequest.getRegistryObjectList().getIdentifiable())
 				newregobjects.add(one_ro);
 	    	 			
 			for (Object one_ro:newregobjects) {
 				RegistryObjectType tempro=(RegistryObjectType)one_ro;
 				Object tempnew = RegistryObjectSubmit.submit(tempro);
 				if (tempnew!=null){
 					if (tempnew instanceof OMVOntology)
 						System.out.println(Oyster2Manager.serializeOMV(tempnew));
 					oyster2Conn.replace(tempnew);
 				}
 			}
 			
 	  	} catch (Exception e) {
  			res.setStatus(statusFailure);
 			e.printStackTrace();
 		}
 	  	res.setStatus(statusSuccess);
 	  	//closeManager();
 	  	return bigres;

        //throw new java.lang.UnsupportedOperationException("Please implement " +
        //    this.getClass().getName() + "#updateObjects");
    }
    
    private void connectManager (){
    	try{
    		statusSuccess=makeReferenceURI(new URI("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success"));
    		statusFailure=makeReferenceURI(new URI("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure"));
    	}catch(Exception e){
    		
    	}
    	oyster2Conn =  Oyster2Manager.newConnection(false);
    }
    
    private void closeManager (){
    	Oyster2Manager.closeConnection();
    }
    
    public static int getNOI (){
    	numberOfCondition++;
    	return numberOfCondition;
    }
    
    public static ReferenceURI makeReferenceURI(URI param) {
    	ReferenceURI temp_refURI = new ReferenceURI();
    	temp_refURI.setReferenceURI(param);
    	return temp_refURI;
    }
    
    public static void main(String[] args) {
    	NeOnRegistryOMVOysterSkeleton a= new NeOnRegistryOMVOysterSkeleton();
    	//a.connectManager();
    	//OMVOntology tempOnto = new OMVOntology();
		//tempOnto.setURI("http://a.temp.com/temp");
		//tempOnto.addName("hola tu");
		//oyster2Conn.register(tempOnto);
		//a.closeManager();
    	
    	Date tte;
    	try {
    	DateFormat dateformater = new SimpleDateFormat("yyyy-MM-dd");
		dateformater.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		Calendar tDate = Calendar.getInstance();
		tDate.setTime(dateformater.parse("2007-05-15T12:00:00"));
		
		
		//tte = dateformater.parse("2007-05-15T12:00:00");
		String value=dateformater.format(tDate.getTime());
    	
		System.out.println(value);
		
	
    	Calendar temp_CreationDate = Calendar.getInstance();
    	
			temp_CreationDate.setTime((new SimpleDateFormat("yyyy-MM-dd")).parse(value));
			System.out.println(temp_CreationDate.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
 
}

/*
	HashMap saveregobjects = new HashMap();
	saveregobjects.put(tempro.getId().toString(), tempnew);
	if (Ontology_Type.class.isAssignableFrom(tempro.getClass())){
		OMVOntology tempOnto = new OMVOntology();

		tempOnto.setURI(((Ontology_Type)tempro).getURI().toString());
		InternationalStringTypeSequence[] templst=((Ontology_Type)tempro).getName().getInternationalStringTypeSequence();
		String temp="";
		if (templst!=null)
	    	for (int i=0;i<templst.length;i++) {
	    		temp=templst[i].getLocalizedString().getValue().getFreeFormText();			
	    } 					
		tempOnto.addName(temp);
		oyster2Conn.register(tempOnto);
	}
	*/
