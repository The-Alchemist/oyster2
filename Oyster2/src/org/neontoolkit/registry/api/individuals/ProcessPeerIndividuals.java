package org.neontoolkit.registry.api.individuals;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.core.OMVOntologyDomain;
import org.neontoolkit.omv.api.extensions.mapping.OMVMapping;
import org.neontoolkit.omv.api.extensions.peer.OMVPeer;
import org.neontoolkit.registry.oyster2.Constants;
import org.semanticweb.kaon2.api.Entity;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.owl.elements.DataProperty;
import org.semanticweb.kaon2.api.owl.elements.Individual;
import org.semanticweb.kaon2.api.owl.elements.ObjectProperty;

/**
 * The class ProcessOMVIndividuals provides the methods to
 * create OMV Peer objects  
 * representing the result of a query 
 * @author Raul Palma
 * @version 1.0, March 2008
 */
public class ProcessPeerIndividuals{
	static LinkedList<Individual> PeerOnProcess = new LinkedList<Individual>();
   
	static public Map<String,OMVPeer> processPeerIndividual(Individual peerIndiv, String whichClass, Ontology ontologySearch){
		  Map<String, OMVPeer> OMVPeerMap = new HashMap<String, OMVPeer>();
		  OMVPeer peerReply=null;
		  String peerNameID="";
		  if (!PeerOnProcess.contains(peerIndiv)){
			PeerOnProcess.add(peerIndiv);
			try{
				Map dataPropertyMap = peerIndiv.getDataPropertyValues(ontologySearch);
				Map objectPropertyMap = peerIndiv.getObjectPropertyValues(ontologySearch);
				if ((dataPropertyMap.size()+objectPropertyMap.size())>0){		
					if (whichClass.equalsIgnoreCase("peer")) {
						peerReply = new OMVPeer();
						peerNameID=peerIndiv.getURI();
					}
					Collection keySet = dataPropertyMap.keySet();
					Iterator keys = keySet.iterator();
					while(keys.hasNext()){
						String keyStr = keys.next().toString();
						DataProperty property = KAON2Manager.factory().dataProperty(keyStr);
						String	propertyValue = org.neontoolkit.registry.util.Utilities.getString(peerIndiv.getDataPropertyValue(ontologySearch,property));
						if (whichClass.equalsIgnoreCase("peer")) peerReply.append(createOMVPeer(property.getURI(),propertyValue, ontologySearch));
					}
					keySet = objectPropertyMap.keySet();
					Iterator okeys = keySet.iterator();
					while(okeys.hasNext()){
						String keyStr = okeys.next().toString();
						ObjectProperty property = KAON2Manager.factory().objectProperty(keyStr);
						Collection propertyCol= new LinkedList();
						propertyCol = (Collection)objectPropertyMap.get(property);
						if(propertyCol==null)System.err.println("propertyCol is null");
						Iterator itInt = propertyCol.iterator();
						while(itInt.hasNext()){
							Entity propertyValue =(Entity) itInt.next();							
							if (whichClass.equalsIgnoreCase("peer")) peerReply.append(createOMVPeer(property.getURI(),propertyValue.getURI(),ontologySearch));	
						}	
					}
				}
			}catch(Exception e){
				System.out.println(e.toString()+" Search Problem in processPeerIndividual");
			}
			PeerOnProcess.remove(peerIndiv);
		  }
		  else {
			  if (whichClass.equalsIgnoreCase("peer")) {
				  peerReply = new OMVPeer();
				  peerNameID=peerIndiv.getURI();
			  }
		  }
		  OMVPeerMap.put(peerNameID,peerReply);
		  return OMVPeerMap;
		}
		
	static private OMVPeer createOMVPeer(String URI, String value, Ontology ontologySearch){
			OMVPeer peerReply=new OMVPeer();
			if (URI.equalsIgnoreCase(Constants.POMVURI+Constants.GUID)) {peerReply.setGUID(value); return peerReply;}
			if (URI.equalsIgnoreCase(Constants.POMVURI+Constants.IPAdress)) {peerReply.setIPAdress(value); return peerReply;}
			if (URI.equalsIgnoreCase(Constants.POMVURI+Constants.localPeer)) 
				{
					if ((value.toString().equalsIgnoreCase("\"true\"^^<xsd:boolean>")))
							peerReply.setLocalPeer(true);
					else
							peerReply.setLocalPeer(false);
					return peerReply;
				}
			if (URI.equalsIgnoreCase(Constants.POMVURI+Constants.peerType)) {peerReply.setPeerType(value); return peerReply;}
			if (URI.equalsIgnoreCase(Constants.POMVURI+Constants.contextOntology)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				OMVOntology oReferencesReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(oIndividual, "ontology", ontologySearch); //oReferences
				if (oReferencesReply==null) {
					oReferencesReply = new OMVOntology();
					oReferencesReply.setURI(value);
				}
				peerReply.setContextOntology(oReferencesReply);
				oReferencesReply = null;
				return peerReply;
			}
			if (URI.equalsIgnoreCase(Constants.POMVURI+Constants.provideOntology)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				OMVOntology oReferencesReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(oIndividual, "ontology", ontologySearch);  //oReferences
				if (oReferencesReply==null) {
					oReferencesReply = new OMVOntology();
					oReferencesReply.setURI(value);
				}
				peerReply.addProvideOntology(oReferencesReply);
				oReferencesReply = null;
				return peerReply;
			}
			if (URI.equalsIgnoreCase(Constants.POMVURI+Constants.provideMapping)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				OMVMapping oReferencesReply=(OMVMapping)ProcessMappingIndividuals.processMappingIndividual(oIndividual, "mapping", ontologySearch);  //oReferences
				if (oReferencesReply==null) {
					oReferencesReply = new OMVMapping();
					oReferencesReply.setURI(value);
				}
				peerReply.addProvideMapping(oReferencesReply);
				oReferencesReply = null;
				return peerReply;
			}
			if (URI.equalsIgnoreCase(Constants.POMVURI+Constants.acquaintedWith)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				Map<String, OMVPeer> peerReplyMap=processPeerIndividual(oIndividual, "peer", ontologySearch); 
				OMVPeer peerReplyAW=(OMVPeer)peerReplyMap.values().iterator().next();
	    		String peerNameIDAW=(String)peerReplyMap.keySet().iterator().next();
				if (peerReplyAW!=null && !peerNameIDAW.equalsIgnoreCase("")){
					peerReply.addAcquaintedWith(peerReplyAW);
					peerReplyAW=null;
					peerNameIDAW="";
				}
				return peerReply;
			}
			if (URI.equalsIgnoreCase(Constants.OMVURI+Constants.hasExpertise)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				OMVOntologyDomain oDomainReply=(OMVOntologyDomain)ProcessOMVIndividuals.processIndividual(oIndividual, "oDomain", ontologySearch);
				if (oDomainReply==null) {
					oDomainReply = new OMVOntologyDomain();
					oDomainReply.setURI(value);
				}
				peerReply.addHasExpertise(oDomainReply);
				oDomainReply = null;
				return peerReply;
			}
			if (URI.equalsIgnoreCase(Constants.POMVURI+Constants.trackOntology)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				OMVOntology oReferencesReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(oIndividual, "ontology", ontologySearch);  //oReferences
				if (oReferencesReply==null) {
					oReferencesReply = new OMVOntology();
					oReferencesReply.setURI(value);
				}
				peerReply.addTrackOntology(oReferencesReply);
				oReferencesReply = null;
				return peerReply;
			}
			return peerReply;
		}
		
		
	
}