package org.neon_toolkit.registry.api.individuals;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import org.semanticweb.kaon2.api.Entity;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Namespaces;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.owl.elements.DataProperty;
import org.semanticweb.kaon2.api.owl.elements.Individual;
import org.semanticweb.kaon2.api.owl.elements.ObjectProperty;
import org.neon_toolkit.omv.api.core.OMVOntology;
import org.neon_toolkit.omv.api.core.OMVParty;
import org.neon_toolkit.omv.api.core.OMVPerson;
import org.neon_toolkit.registry.oyster2.Constants;
import org.neon_toolkit.workflow.api.Action;
import org.neon_toolkit.workflow.api.Action.EntityAction;
import org.neon_toolkit.workflow.api.Action.OntologyAction;
import org.neon_toolkit.workflow.api.Action.EntityAction.Delete;
import org.neon_toolkit.workflow.api.Action.EntityAction.Insert;
import org.neon_toolkit.workflow.api.Action.EntityAction.RejectToApproved;
import org.neon_toolkit.workflow.api.Action.EntityAction.RejectToBeApproved;
import org.neon_toolkit.workflow.api.Action.EntityAction.RejectToDraft;
import org.neon_toolkit.workflow.api.Action.EntityAction.SendToApproved;
import org.neon_toolkit.workflow.api.Action.EntityAction.SendToBeApproved;
import org.neon_toolkit.workflow.api.Action.EntityAction.SendToBeDeleted;
import org.neon_toolkit.workflow.api.Action.EntityAction.Update;
import org.neon_toolkit.workflow.api.Action.OntologyAction.Approval;
import org.neon_toolkit.workflow.api.Action.OntologyAction.MoveToBeApproved;
import org.neon_toolkit.workflow.api.Action.OntologyAction.MoveToDraft;
import org.neon_toolkit.workflow.api.Action.OntologyAction.Publish;




/**
 * The class ProcessActionIndividuals provides the methods to
 * create workflow Action objects  
 * representing the result of a query 
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class ProcessActionIndividuals{
	static LinkedList<Individual> onProcess = new LinkedList<Individual>();
   
    static public Action processActionIndividual(Individual actionIndividual, String whichClassFull, Ontology ontologySearch){
     Action reply=new Action();
     String whichClass = Namespaces.guessLocalName(whichClassFull);
     
	 if (!onProcess.contains(actionIndividual)){
	  onProcess.add(actionIndividual);  
	  try{
		Map dataPropertyMap = actionIndividual.getDataPropertyValues(ontologySearch);
		Map objectPropertyMap = actionIndividual.getObjectPropertyValues(ontologySearch);
		if ((dataPropertyMap.size()+objectPropertyMap.size())>0){
		
			if (whichClass.equalsIgnoreCase("Delete")) reply = new Delete();
			else if (whichClass.equalsIgnoreCase("Insert")) reply = new Insert();
			else if (whichClass.equalsIgnoreCase("Update")) reply = new Update();
			else if (whichClass.equalsIgnoreCase("RejectToApproved"))  reply = new RejectToApproved();
			else if (whichClass.equalsIgnoreCase("RejectToBeApproved")) reply = new RejectToBeApproved();
			else if (whichClass.equalsIgnoreCase("RejectToDraft")) reply = new RejectToDraft();
			else if (whichClass.equalsIgnoreCase("SendToApproved")) reply = new SendToApproved();
			else if (whichClass.equalsIgnoreCase("SendToBeApproved")) reply = new SendToBeApproved();
			else if (whichClass.equalsIgnoreCase("SendToBeDeleted")) reply = new SendToBeDeleted();
			else if (whichClass.equalsIgnoreCase("MoveToDraft")) reply = new MoveToDraft();
			else if (whichClass.equalsIgnoreCase("MoveToBeApproved")) reply = new MoveToBeApproved();
			else if (whichClass.equalsIgnoreCase("Approval")) reply = new Approval();
			else if (whichClass.equalsIgnoreCase("Publish"))  reply = new Publish();
			
									
			Collection keySet = dataPropertyMap.keySet();
			Iterator keys = keySet.iterator();
			while(keys.hasNext()){
				String keyStr = keys.next().toString();
				DataProperty property = KAON2Manager.factory().dataProperty(keyStr);
				/*15*/
				Collection propertyCol= new LinkedList();
				propertyCol = (Collection)dataPropertyMap.get(property);
				if(propertyCol==null)System.err.println("datapropertyCol is null");
				Iterator itInt = propertyCol.iterator();
				while(itInt.hasNext()){
					Object propertyObject =(Object) itInt.next();
					String	propertyValue = org.neon_toolkit.registry.util.Utilities.getString(propertyObject);
					//System.out.println("property: "+property.getURI()+" VALUE= "+propertyValue);
					
					reply.append(createAction(property.getURI(),propertyValue, ontologySearch));
					if (reply instanceof EntityAction) ((EntityAction)reply).append(createEntityAction(property.getURI(),propertyValue, ontologySearch));
					else if (reply instanceof OntologyAction) {
						((OntologyAction)reply).append(createOntologyAction(property.getURI(),propertyValue, ontologySearch));
						if (reply instanceof Publish){
							((Publish)reply).append(createPublish(property.getURI(),propertyValue, ontologySearch));
						}
					}
				}
			}
			keySet = objectPropertyMap.keySet();
			Iterator okeys = keySet.iterator();
			while(okeys.hasNext()){
				String keyStr = okeys.next().toString();
				ObjectProperty property = KAON2Manager.factory().objectProperty(keyStr);
				Collection propertyCol= new LinkedList();
				propertyCol = (Collection)objectPropertyMap.get(property);
				if(propertyCol==null)System.err.println("objectpropertyCol is null");
				Iterator itInt = propertyCol.iterator();
				while(itInt.hasNext()){
					Entity propertyValue =(Entity) itInt.next();
					//System.out.println("The property "+property.getURI()+" has value: "+propertyValue.getURI());
					
					reply.append(createAction(property.getURI(),propertyValue.getURI(), ontologySearch));
					if (reply instanceof EntityAction) ((EntityAction)reply).append(createEntityAction(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (reply instanceof OntologyAction) {
						((OntologyAction)reply).append(createOntologyAction(property.getURI(),propertyValue.getURI(), ontologySearch));
						if (reply instanceof Publish){
							((Publish)reply).append(createPublish(property.getURI(),propertyValue.getURI(), ontologySearch));
						}
					}
						
				}	
			}
		}
	  }catch(Exception e){
			System.out.println(e.getMessage()+" "+e.getCause()+" "+e.getStackTrace()+" "+e.toString()+" Problem in processAxiomIndividual");
	  }
	  onProcess.remove(actionIndividual);
	  if (reply instanceof Action || reply instanceof EntityAction || reply instanceof OntologyAction) return reply;
	  else return null;
	 }
	 else{
		reply.setURI(actionIndividual.getURI());
		return reply;
	 }
	}
	
	static private Action createAction(String URI, String value, Ontology ontologySearch){
	  Action actionReply=new Action();
	  try{
		if (URI.equalsIgnoreCase(Constants.WORKFLOWURI+Constants.URI)) {actionReply.setURI(value);return actionReply;}
		if (URI.equalsIgnoreCase(Constants.WORKFLOWURI+Constants.timestamp)) {actionReply.setTimestamp(value);return actionReply;}
		if (URI.equalsIgnoreCase(Constants.WORKFLOWURI+Constants.performedBy)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)ProcessOMVIndividuals.processIndividual(oIndividual, "party", ontologySearch);
			OMVPerson personReply=(OMVPerson)ProcessOMVIndividuals.processIndividual(oIndividual, "person", ontologySearch);
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=ProcessOMVIndividuals.copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				actionReply.setPerformedBy(personReply);
				personReply = null;
			}
			partyReply=null;
			return actionReply;
		}
	  }catch(Exception e){
			System.out.println(e.toString()+" Search Problem in createAction");
	  }
	  return actionReply;
	}
	
	static private EntityAction createEntityAction(String URI, String value, Ontology ontologySearch){
		  EntityAction actionReply=new EntityAction();
		  try{
			  if (URI.equalsIgnoreCase(Constants.WORKFLOWURI+Constants.relatedChange)) { 
				  	actionReply.setRelatedChange(value);
					return actionReply;
				}
		  }catch(Exception e){
				System.out.println(e.toString()+" Search Problem in createEntityAction");
		  }
		  return actionReply;
	}
	
	static private OntologyAction createOntologyAction(String URI, String value, Ontology ontologySearch){
		  OntologyAction actionReply=new OntologyAction();
		  try{
			  if (URI.equalsIgnoreCase(Constants.WORKFLOWURI+Constants.relatedOntology)) {
					Individual oIndividual =KAON2Manager.factory().individual(value);
					OMVOntology oReferencesReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(oIndividual, "ontology", ontologySearch); //oReferences
					if (oReferencesReply==null) {
						oReferencesReply = new OMVOntology();
						oReferencesReply.setURI(value);
					}
					actionReply.setRelatedOntology(oReferencesReply);
					oReferencesReply = null;
					return actionReply;
			  }
		  }catch(Exception e){
				System.out.println(e.toString()+" Search Problem in createOntologyAction");
		  }
		  return actionReply;
	}
	
	static private Publish createPublish(String URI, String value, Ontology ontologySearch){
		  Publish actionReply=new Publish();
		  try{
			  if (URI.equalsIgnoreCase(Constants.WORKFLOWURI+Constants.fromPublicVersion)) {
					Individual oIndividual =KAON2Manager.factory().individual(value);
					OMVOntology oReferencesReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(oIndividual, "ontology", ontologySearch); //oReferences
					if (oReferencesReply==null) {
						oReferencesReply = new OMVOntology();
						oReferencesReply.setURI(value);
					}
					actionReply.setFromPublicVersion(oReferencesReply);
					oReferencesReply = null;
					return actionReply;
			  }
			  if (URI.equalsIgnoreCase(Constants.WORKFLOWURI+Constants.toPublicVersion)) {
					Individual oIndividual =KAON2Manager.factory().individual(value);
					OMVOntology oReferencesReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(oIndividual, "ontology", ontologySearch); //oReferences
					if (oReferencesReply==null) {
						oReferencesReply = new OMVOntology();
						oReferencesReply.setURI(value);
					}
					actionReply.setToPublicVersion(oReferencesReply);
					oReferencesReply = null;
					return actionReply;
			  }
		  }catch(Exception e){
				System.out.println(e.toString()+" Search Problem in createPublishAction");
		  }
		  return actionReply;
	}
}	