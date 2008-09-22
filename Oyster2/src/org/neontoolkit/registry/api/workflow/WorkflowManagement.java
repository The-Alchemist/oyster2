package org.neontoolkit.registry.api.workflow;


import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.core.OMVPerson;
import org.neontoolkit.omv.api.extensions.change.OMVChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange;
import org.neontoolkit.registry.api.change.ChangeManagement;
import org.neontoolkit.registry.api.individuals.ProcessActionIndividuals;
import org.neontoolkit.registry.api.individuals.ProcessOMVIndividuals;
import org.neontoolkit.registry.api.properties.WorkflowProperties;
import org.neontoolkit.registry.oyster2.Constants;
import org.neontoolkit.registry.oyster2.ImportOntology;
import org.neontoolkit.registry.oyster2.OntologyProperty;
import org.neontoolkit.registry.oyster2.Oyster2Factory;
import org.neontoolkit.workflow.api.Action;
import org.neontoolkit.workflow.api.Action.EntityAction.Delete;
import org.neontoolkit.workflow.api.Action.EntityAction.Insert;
import org.neontoolkit.workflow.api.Action.EntityAction.RejectToApproved;
import org.neontoolkit.workflow.api.Action.EntityAction.RejectToBeApproved;
import org.neontoolkit.workflow.api.Action.EntityAction.RejectToDraft;
import org.neontoolkit.workflow.api.Action.EntityAction.SendToApproved;
import org.neontoolkit.workflow.api.Action.EntityAction.SendToBeApproved;
import org.neontoolkit.workflow.api.Action.EntityAction.SendToBeDeleted;
import org.neontoolkit.workflow.api.Action.EntityAction.Update;
import org.neontoolkit.workflow.api.Action.OntologyAction.Approval;
import org.neontoolkit.workflow.api.Action.OntologyAction.MoveToBeApproved;
import org.neontoolkit.workflow.api.Action.OntologyAction.MoveToDraft;
import org.neontoolkit.workflow.api.Action.OntologyAction.Publish;
import org.semanticweb.kaon2.api.KAON2Exception;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Namespaces;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.OntologyChangeEvent;
import org.semanticweb.kaon2.api.formatting.OntologyFileFormat;
import org.semanticweb.kaon2.api.owl.elements.Individual;
import org.semanticweb.kaon2.api.owl.elements.OWLClass;
import org.semanticweb.kaon2.api.owl.elements.ObjectProperty;

/**
 * The class WorkflowManager provides the methods to support the
 * editorial workflow 
 * @author Raul Palma
 * @version 2.2, September 2008
 */
public class WorkflowManagement {
	static Oyster2Factory mOyster2;// = Oyster2Factory.sharedInstance();
	private ImportOntology IOntology;//= new ImportOntology();
	private static Ontology localRegistry;// = mOyster2.getLocalHostOntology();
	
	
	public WorkflowManagement()
    {
		mOyster2 = Oyster2Factory.sharedInstance();
		IOntology= new ImportOntology();
		localRegistry = mOyster2.getLocalHostOntology();
    }

	//ADDING ACTIONS
	@SuppressWarnings("unchecked")
	public boolean insert(String changeURI, OMVPerson p, OMVOntology o){
		if (p==null || p.getFirstName()==null || p.getLastName()==null || p.getHasRole()==null) {System.out.println("Workflow support requires to specify the ontology editor and its role...");return false;} 
		//checking editor role permissions
		if (!p.getHasRole().equalsIgnoreCase(Constants.SubjectExpert)) {System.out.println("Only subject experts are allowed to insert elements...");return false;}
		//try get change concept
		Individual oIndividual = KAON2Manager.factory().individual(changeURI);
		String conceptChange=null;
		try {
			conceptChange = oIndividual.getDescriptionsMemberOf(localRegistry).iterator().next().toString();
		} catch (KAON2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (conceptChange!=null) {
			conceptChange = Namespaces.guessLocalName(conceptChange);
		}
		else return false;
		
		//checking previous entity state
		//first get the ontology of the change
		OMVOntology mainOntoReply;
		if (o !=null) mainOntoReply=o;
		else{
			ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.CHANGEURI + Constants.appliedToOntology);
			Individual targetValue=getPropertyValue(changeURI,ontologyObjectProperty,Constants.CHANGEURI+conceptChange);
			mainOntoReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(targetValue, "ontology", localRegistry);
		}
		//now get the entity state
		ChangeManagement cMgmt= new ChangeManagement();
		//String entity = cMgmt.getRelatedEntity(changeURI);
		String entityState = cMgmt.getEntityStateFromChange(changeURI);
		//now setting next state
		if (entityState==null) System.out.println("Inserting new element...");
		String nextState = Constants.DraftState;
		
		//update change state		
		removeEntityChangeState(changeURI, conceptChange);
		addEntityChangeState(changeURI, conceptChange, nextState);
		
		//add action
		Insert obj = new Insert();
		obj.setRelatedChange(changeURI);
		if (p!=null) obj.setPerformedBy(p);
		Date now = new Date();
		String sNow = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(now);
		obj.setTimestamp(sNow);
		List pList = new LinkedList();
		pList.clear();
		pList=WorkflowProperties.getActionProperties(obj);
		//Specify class name to add
		String concept=getActionConcept(obj);
		OntologyProperty tProp = new OntologyProperty(Constants.name, concept);
		pList.add(tProp);
		IOntology.addConceptToRegistry(1,pList,60, null);
		
		//update ontology state 
		//set draft state to ontology
		updateOntologyState(mainOntoReply,p,nextState);
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean delete(String changeURI, OMVPerson p, OMVOntology o){
		if (p==null || p.getFirstName()==null || p.getLastName()==null || p.getHasRole()==null) {System.out.println("Workflow support requires to specify the ontology editor and its role...");return false;}
		//checking editor role permissions
		if (!p.getHasRole().equalsIgnoreCase(Constants.SubjectExpert) && !p.getHasRole().equalsIgnoreCase(Constants.Validator)) {System.out.println("Only subject experts and validators are allowed to delete elements...");return false;}
		//THIS DELETE MEANS THE ELEMENT WILL BE PERMANENTLY DELETED
		//try get change concept
		Individual oIndividual = KAON2Manager.factory().individual(changeURI);
		String conceptChange=null;
		try {
			conceptChange = oIndividual.getDescriptionsMemberOf(localRegistry).iterator().next().toString();
		} catch (KAON2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (conceptChange!=null) {
			conceptChange = Namespaces.guessLocalName(conceptChange);
		}
		else return false;
		
		//checking previous entity state
		//first get the ontology of the change
		OMVOntology mainOntoReply;
		if (o !=null) mainOntoReply=o;
		else{
			ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.CHANGEURI + Constants.appliedToOntology);
			Individual targetValue=getPropertyValue(changeURI,ontologyObjectProperty,Constants.CHANGEURI+conceptChange);
			mainOntoReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(targetValue, "ontology", localRegistry);
		}
		//now get the entity state
		ChangeManagement cMgmt= new ChangeManagement();
		//String entity = cMgmt.getRelatedEntity(changeURI);
		String entityState = cMgmt.getEntityStateFromChange(changeURI);
		//now setting next state
		String nextState;
		if (p.getHasRole().equalsIgnoreCase(Constants.SubjectExpert)){
			if (!entityState.equalsIgnoreCase(Constants.DraftState)){
				System.out.println("Subject Experts can only delete elements in draft state...");
				return false;
			}
			nextState = Constants.DeletedState;
		}else{
			if (!entityState.equalsIgnoreCase(Constants.ToBeDeletedState)) {
				System.out.println("Validators can only delete elements in to be deleted state...");
				return false;
			}
			nextState = Constants.DeletedState;
		}
		
		//update change state		
		removeEntityChangeState(changeURI, conceptChange);
		addEntityChangeState(changeURI, conceptChange, nextState);
		
		//add action
		Delete obj = new Delete();
		obj.setRelatedChange(changeURI);
		if (p!=null) obj.setPerformedBy(p);
		Date now = new Date();
		String sNow = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(now);
		obj.setTimestamp(sNow);
		
		List pList = new LinkedList();
		pList.clear();
		pList=WorkflowProperties.getActionProperties(obj);
		//Specify class name to add
		String concept=getActionConcept(obj);
		OntologyProperty tProp = new OntologyProperty(Constants.name, concept);
		pList.add(tProp);
		IOntology.addConceptToRegistry(1,pList,60, null);
		
		//update ontology state if necessary
		updateOntologyState(mainOntoReply,p,nextState);
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean update(String changeURI, OMVPerson p, OMVOntology o){
		if (p==null || p.getFirstName()==null || p.getLastName()==null || p.getHasRole()==null) {System.out.println("Workflow support requires to specify the ontology editor and its role...");return false;}
		//checking editor role permissions
		if (!p.getHasRole().equalsIgnoreCase(Constants.SubjectExpert) && !p.getHasRole().equalsIgnoreCase(Constants.Validator)) {System.out.println("Only subject experts and validators are allowed to update elements...");return false;}
		//try get change concept
		Individual oIndividual = KAON2Manager.factory().individual(changeURI);
		String conceptChange=null;
		try {
			conceptChange = oIndividual.getDescriptionsMemberOf(localRegistry).iterator().next().toString();
		} catch (KAON2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (conceptChange!=null) {
			conceptChange = Namespaces.guessLocalName(conceptChange);
		}
		else return false;
		
		//checking previous entity state
		//first get the ontology of the change
		OMVOntology mainOntoReply;
		if (o !=null) mainOntoReply=o;
		else{
			ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.CHANGEURI + Constants.appliedToOntology);
			Individual targetValue=getPropertyValue(changeURI,ontologyObjectProperty,Constants.CHANGEURI+conceptChange);
			mainOntoReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(targetValue, "ontology", localRegistry);
		}
		//now get the entity state
		ChangeManagement cMgmt= new ChangeManagement();
		//String entity = cMgmt.getRelatedEntity(changeURI);
		String entityState = cMgmt.getEntityStateFromChange(changeURI);
		//now setting next state
		String nextState;
		if (p.getHasRole().equalsIgnoreCase(Constants.SubjectExpert)){
			if (entityState==null || entityState==""){ //Assuming initial state = approved
				nextState = Constants.DraftState;
			}
			else if (entityState.equalsIgnoreCase(Constants.DraftState)){
				nextState = Constants.DraftState;
			}
			else if (entityState.equalsIgnoreCase(Constants.ApprovedState)){
				nextState = Constants.DraftState;
			}
			else{
				System.out.println("Subject Experts can only update elements in draft or approved state...");
				return false;
			}
		}else{
			if (entityState==null || entityState==""){ //Assuming initial state = approved
				nextState = Constants.ApprovedState;
			}
			else if (entityState.equalsIgnoreCase(Constants.ToBeApprovedState)){
				nextState = Constants.ToBeApprovedState;
			}
			else if (entityState.equalsIgnoreCase(Constants.ApprovedState)){
				nextState = Constants.ApprovedState;
			}
			else{
				System.out.println("Validators can only update elements in to be approved or approved state...");
				return false;
			}
		}
		
		//update change state		
		removeEntityChangeState(changeURI, conceptChange);
		addEntityChangeState(changeURI, conceptChange, nextState);
		
		//add action
		Update obj = new Update();
		obj.setRelatedChange(changeURI);
		if (p!=null) obj.setPerformedBy(p);
		Date now = new Date();
		String sNow = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(now);
		obj.setTimestamp(sNow);
		
		List pList = new LinkedList();
		pList.clear();
		pList=WorkflowProperties.getActionProperties(obj);
		//Specify class name to add
		String concept=getActionConcept(obj);
		OntologyProperty tProp = new OntologyProperty(Constants.name, concept);
		pList.add(tProp);
		IOntology.addConceptToRegistry(1,pList,60, null);
		
		//update ontology state if neccesary
		updateOntologyState(mainOntoReply,p,nextState);
		return true;
	}
	@SuppressWarnings("unchecked")
	public void submitToBeApproved(String changeURI, OMVPerson p, OMVOntology o){
		if (p==null || p.getFirstName()==null || p.getLastName()==null || p.getHasRole()==null) {System.out.println("Workflow support requires to specify the ontology editor and its role...");return;}
		//checking editor role permissions
		if (!p.getHasRole().equalsIgnoreCase(Constants.SubjectExpert)) {System.out.println("Only subject experts are allowed to send elements to be approved...");return;}
		//try get change concept
		Individual oIndividual = KAON2Manager.factory().individual(changeURI);
		String conceptChange=null;
		try {
			conceptChange = oIndividual.getDescriptionsMemberOf(localRegistry).iterator().next().toString();
		} catch (KAON2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (conceptChange!=null) {
			conceptChange = Namespaces.guessLocalName(conceptChange);
		}
		else return;
		
		
		//checking previous entity state
		//first get the ontology of the change
		OMVOntology mainOntoReply;
		if (o !=null) mainOntoReply=o;
		else{
			ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.CHANGEURI + Constants.appliedToOntology);
			Individual targetValue=getPropertyValue(changeURI,ontologyObjectProperty,Constants.CHANGEURI+conceptChange);
			mainOntoReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(targetValue, "ontology", localRegistry);
		}
		//now get the entity state
		ChangeManagement cMgmt= new ChangeManagement();
		//String entity = cMgmt.getRelatedEntity(changeURI);
		String entityState = cMgmt.getEntityStateFromChange(changeURI);
		//now setting next state
		String nextState;
		if (!entityState.equalsIgnoreCase(Constants.DraftState)){
			System.out.println("Subject Experts can only send draft elements to be approved...");
			return;
		}
		nextState = Constants.ToBeApprovedState;
		
		//update change state		
		removeEntityChangeState(changeURI, conceptChange);
		addEntityChangeState(changeURI, conceptChange, nextState);
		
		//add action
		SendToBeApproved obj = new SendToBeApproved();
		obj.setRelatedChange(changeURI);
		if (p!=null) obj.setPerformedBy(p);
		Date now = new Date();
		String sNow = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(now);
		obj.setTimestamp(sNow);
		
		List pList = new LinkedList();
		pList.clear();
		pList=WorkflowProperties.getActionProperties(obj);
		//Specify class name to add
		String concept=getActionConcept(obj);
		OntologyProperty tProp = new OntologyProperty(Constants.name, concept);
		pList.add(tProp);
		IOntology.addConceptToRegistry(1,pList,60, null);
		
		//update ontology state if necessary
		updateOntologyState(mainOntoReply,p,nextState);
	}
	@SuppressWarnings("unchecked")
	public void submitToApproved(String changeURI, OMVPerson p, OMVOntology o){
		if (p==null || p.getFirstName()==null || p.getLastName()==null || p.getHasRole()==null) {System.out.println("Workflow support requires to specify the ontology editor and its role...");return;}
		//checking editor role permissions
		if (!p.getHasRole().equalsIgnoreCase(Constants.Validator)) {System.out.println("Only validators are allowed to approve elements...");return;}
		//try get change concept
		Individual oIndividual = KAON2Manager.factory().individual(changeURI);
		String conceptChange=null;
		try {
			conceptChange = oIndividual.getDescriptionsMemberOf(localRegistry).iterator().next().toString();
		} catch (KAON2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (conceptChange!=null) {
			conceptChange = Namespaces.guessLocalName(conceptChange);
		}
		else return;
		
		//checking previous entity state
		//first get the ontology of the change
		OMVOntology mainOntoReply;
		if (o !=null) mainOntoReply=o;
		else{
			ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.CHANGEURI + Constants.appliedToOntology);
			Individual targetValue=getPropertyValue(changeURI,ontologyObjectProperty,Constants.CHANGEURI+conceptChange);
			mainOntoReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(targetValue, "ontology", localRegistry);
		}
		//now get the entity state
		ChangeManagement cMgmt= new ChangeManagement();
		//String entity = cMgmt.getRelatedEntity(changeURI);
		String entityState = cMgmt.getEntityStateFromChange(changeURI);
		//now setting next state
		String nextState;
		if (!entityState.equalsIgnoreCase(Constants.ToBeApprovedState)){
			System.out.println("Validators can only approve elements in to be approved state...");
			return;
		}
		nextState = Constants.ApprovedState;
		
		//update change state		
		removeEntityChangeState(changeURI, conceptChange);
		addEntityChangeState(changeURI, conceptChange, nextState);
		
		//add action
		SendToApproved obj = new SendToApproved();
		obj.setRelatedChange(changeURI);
		if (p!=null) obj.setPerformedBy(p);
		Date now = new Date();
		String sNow = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(now);
		obj.setTimestamp(sNow);
		
		List pList = new LinkedList();
		pList.clear();
		pList=WorkflowProperties.getActionProperties(obj);
		//Specify class name to add
		String concept=getActionConcept(obj);
		OntologyProperty tProp = new OntologyProperty(Constants.name, concept);
		pList.add(tProp);
		IOntology.addConceptToRegistry(1,pList,60, null);
		
		//update ontology state if necessary
		updateOntologyState(mainOntoReply,p,nextState);	
	}
	@SuppressWarnings("unchecked")
	public boolean submitToBeDeleted(String changeURI, OMVPerson p, OMVOntology o){
		if (p==null || p.getFirstName()==null || p.getLastName()==null || p.getHasRole()==null) {System.out.println("Workflow support requires to specify the ontology editor and its role...");return false;}
		//checking editor role permissions
		if (!p.getHasRole().equalsIgnoreCase(Constants.SubjectExpert) && !p.getHasRole().equalsIgnoreCase(Constants.Validator)) {System.out.println("Only subject experts and validators are allowed to send elements to be deleted...");return false;}
		//try get change concept
		Individual oIndividual = KAON2Manager.factory().individual(changeURI);
		String conceptChange=null;
		try {
			conceptChange = oIndividual.getDescriptionsMemberOf(localRegistry).iterator().next().toString();
		} catch (KAON2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (conceptChange!=null) {
			conceptChange = Namespaces.guessLocalName(conceptChange);
		}
		else return false;
		
		//checking previous entity state
		//first get the ontology of the change
		OMVOntology mainOntoReply;
		if (o !=null) mainOntoReply=o;
		else{
			ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.CHANGEURI + Constants.appliedToOntology);
			Individual targetValue=getPropertyValue(changeURI,ontologyObjectProperty,Constants.CHANGEURI+conceptChange);
			mainOntoReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(targetValue, "ontology", localRegistry);
		}
		//now get the entity state
		ChangeManagement cMgmt= new ChangeManagement();
		//String entity = cMgmt.getRelatedEntity(changeURI);
		String entityState = cMgmt.getEntityStateFromChange(changeURI);
		//now setting next state
		String nextState;
		if (p.getHasRole().equalsIgnoreCase(Constants.SubjectExpert)){
			if (entityState==null || entityState==""){ //Assuming initial state = approved
				nextState = Constants.ToBeDeletedState;
			}
			else if (entityState.equalsIgnoreCase(Constants.ApprovedState)){
				nextState = Constants.ToBeDeletedState;
			}
			else{
				System.out.println("Subject Experts can only send elements to delete in approved state...");
				return false;
			}
		}else{
			if (entityState==null || entityState==""){ //Assuming initial state = approved
				nextState = Constants.ToBeDeletedState;
			}
			else if (entityState.equalsIgnoreCase(Constants.ApprovedState)){
				nextState = Constants.ToBeDeletedState;
			}
			else{
				System.out.println("Validators can only send elements to delete in approved state...");
				return false;
			}
		}
		
		//update change state		
		removeEntityChangeState(changeURI, conceptChange);
		addEntityChangeState(changeURI, conceptChange, nextState);
		
		//add action
		SendToBeDeleted obj = new SendToBeDeleted();
		obj.setRelatedChange(changeURI);
		if (p!=null) obj.setPerformedBy(p);
		Date now = new Date();
		String sNow = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(now);
		obj.setTimestamp(sNow);
		
		List pList = new LinkedList();
		pList.clear();
		pList=WorkflowProperties.getActionProperties(obj);
		//Specify class name to add
		String concept=getActionConcept(obj);
		OntologyProperty tProp = new OntologyProperty(Constants.name, concept);
		pList.add(tProp);
		IOntology.addConceptToRegistry(1,pList,60, null);
		
		//update ontology state if necessary
		updateOntologyState(mainOntoReply,p,nextState);
		return true;
	}
	@SuppressWarnings("unchecked")
	public void rejectToBeApproved(String changeURI, OMVPerson p, OMVOntology o){
		if (p==null || p.getFirstName()==null || p.getLastName()==null || p.getHasRole()==null) {System.out.println("Workflow support requires to specify the ontology editor and its role...");return;}
		//checking editor role permissions
		if (!p.getHasRole().equalsIgnoreCase(Constants.Validator)) {System.out.println("Only validators are allowed to reject elements and send them to be approved...");return;}
		//try get change concept
		Individual oIndividual = KAON2Manager.factory().individual(changeURI);
		String conceptChange=null;
		try {
			conceptChange = oIndividual.getDescriptionsMemberOf(localRegistry).iterator().next().toString();
		} catch (KAON2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (conceptChange!=null) {
			conceptChange = Namespaces.guessLocalName(conceptChange);
		}
		else return;
		
		//checking previous entity state
		//first get the ontology of the change
		OMVOntology mainOntoReply;
		if (o !=null) mainOntoReply=o;
		else{
			ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.CHANGEURI + Constants.appliedToOntology);
			Individual targetValue=getPropertyValue(changeURI,ontologyObjectProperty,Constants.CHANGEURI+conceptChange);
			mainOntoReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(targetValue, "ontology", localRegistry);
		}
		//now get the entity state
		ChangeManagement cMgmt= new ChangeManagement();
		//String entity = cMgmt.getRelatedEntity(changeURI);
		String entityState = cMgmt.getEntityStateFromChange(changeURI);
		//now setting next state
		String nextState;
		if (entityState==null || entityState==""){ //Assuming initial state = approved
			nextState = Constants.ToBeApprovedState;
		}
		else if (entityState.equalsIgnoreCase(Constants.ApprovedState)){
			nextState = Constants.ToBeApprovedState;
		}
		else{
			System.out.println("Validator can only reject elements to be approved in approved state...");
			return;
		}
		
		//update change state		
		removeEntityChangeState(changeURI, conceptChange);
		addEntityChangeState(changeURI, conceptChange, nextState);
		
		//add action
		RejectToBeApproved obj = new RejectToBeApproved();
		obj.setRelatedChange(changeURI);
		if (p!=null) obj.setPerformedBy(p);
		Date now = new Date();
		String sNow = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(now);
		obj.setTimestamp(sNow);	
		
		List pList = new LinkedList();
		pList.clear();
		pList=WorkflowProperties.getActionProperties(obj);
		//Specify class name to add
		String concept=getActionConcept(obj);
		OntologyProperty tProp = new OntologyProperty(Constants.name, concept);
		pList.add(tProp);
		IOntology.addConceptToRegistry(1,pList,60, null);
		
		//update ontology state if necessary
		updateOntologyState(mainOntoReply,p,nextState);
	}
	@SuppressWarnings("unchecked")
	public void rejectToApproved(String changeURI, OMVPerson p, OMVOntology o){
		if (p==null || p.getFirstName()==null || p.getLastName()==null || p.getHasRole()==null) {System.out.println("Workflow support requires to specify the ontology editor and its role...");return;}
		//checking editor role permissions
		if (!p.getHasRole().equalsIgnoreCase(Constants.Validator)) {System.out.println("Only validators are allowed to reject elements and send them to approved...");return;}
		//try get change concept
		Individual oIndividual = KAON2Manager.factory().individual(changeURI);
		String conceptChange=null;
		try {
			conceptChange = oIndividual.getDescriptionsMemberOf(localRegistry).iterator().next().toString();
		} catch (KAON2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (conceptChange!=null) {
			conceptChange = Namespaces.guessLocalName(conceptChange);
		}
		else return;
		
		//checking previous entity state
		//first get the ontology of the change
		OMVOntology mainOntoReply;
		if (o !=null) mainOntoReply=o;
		else{
			ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.CHANGEURI + Constants.appliedToOntology);
			Individual targetValue=getPropertyValue(changeURI,ontologyObjectProperty,Constants.CHANGEURI+conceptChange);
			mainOntoReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(targetValue, "ontology", localRegistry);
		}
		//now get the entity state
		ChangeManagement cMgmt= new ChangeManagement();
		//String entity = cMgmt.getRelatedEntity(changeURI);
		String entityState = cMgmt.getEntityStateFromChange(changeURI);
		//now setting next state
		String nextState;
		if (entityState.equalsIgnoreCase(Constants.ToBeDeletedState)){
			nextState = Constants.ApprovedState;
		}
		else{
			System.out.println("Validator can only reject elements to approved in to be deleted state...");
			return;
		}
		
		//update change state		
		removeEntityChangeState(changeURI, conceptChange);
		addEntityChangeState(changeURI, conceptChange, nextState);
		
		//add action
		RejectToApproved obj = new RejectToApproved();
		obj.setRelatedChange(changeURI);
		if (p!=null) obj.setPerformedBy(p);
		Date now = new Date();
		String sNow = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(now);
		obj.setTimestamp(sNow);
		
		List pList = new LinkedList();
		pList.clear();
		pList=WorkflowProperties.getActionProperties(obj);
		//Specify class name to add
		String concept=getActionConcept(obj);
		OntologyProperty tProp = new OntologyProperty(Constants.name, concept);
		pList.add(tProp);
		IOntology.addConceptToRegistry(1,pList,60, null);
		
		//update ontology state if necessary
		updateOntologyState(mainOntoReply,p,nextState);
	}
	@SuppressWarnings("unchecked")
	public void rejectToDraft(String changeURI, OMVPerson p, OMVOntology o){
		if (p==null || p.getFirstName()==null || p.getLastName()==null || p.getHasRole()==null) {System.out.println("Workflow support requires to specify the ontology editor and its role...");return;}
		//checking editor role permissions
		if (!p.getHasRole().equalsIgnoreCase(Constants.Validator)) {System.out.println("Only validators are allowed to reject elements and send them to draft...");return;}
		//try get change concept
		Individual oIndividual = KAON2Manager.factory().individual(changeURI);
		String conceptChange=null;
		try {
			conceptChange = oIndividual.getDescriptionsMemberOf(localRegistry).iterator().next().toString();
		} catch (KAON2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (conceptChange!=null) {
			conceptChange = Namespaces.guessLocalName(conceptChange);
		}
		else return;
		
		//checking previous entity state
		//first get the ontology of the change
		OMVOntology mainOntoReply;
		if (o !=null) mainOntoReply=o;
		else{
			ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.CHANGEURI + Constants.appliedToOntology);
			Individual targetValue=getPropertyValue(changeURI,ontologyObjectProperty,Constants.CHANGEURI+conceptChange);
			mainOntoReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(targetValue, "ontology", localRegistry);
		}
		//now get the entity state
		ChangeManagement cMgmt= new ChangeManagement();
		//String entity = cMgmt.getRelatedEntity(changeURI);
		String entityState = cMgmt.getEntityStateFromChange(changeURI);
		//now setting next state
		String nextState;
		if (entityState.equalsIgnoreCase(Constants.ToBeApprovedState)){
			nextState = Constants.DraftState;
		}
		else{
			System.out.println("Validator can only reject elements to draft in to be approved state...");
			return;
		}
		
		//update change state		
		removeEntityChangeState(changeURI, conceptChange);
		addEntityChangeState(changeURI, conceptChange, nextState);
		
		//add action
		RejectToDraft obj = new RejectToDraft();
		obj.setRelatedChange(changeURI);
		if (p!=null) obj.setPerformedBy(p);
		Date now = new Date();
		String sNow = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(now);
		obj.setTimestamp(sNow);
		
		List pList = new LinkedList();
		pList.clear();
		pList=WorkflowProperties.getActionProperties(obj);
		//Specify class name to add
		String concept=getActionConcept(obj);
		OntologyProperty tProp = new OntologyProperty(Constants.name, concept);
		pList.add(tProp);
		IOntology.addConceptToRegistry(1,pList,60, null);
		
		//update ontology state if necessary
		updateOntologyState(mainOntoReply,p,nextState);
	}
	@SuppressWarnings("unchecked")
	public void moveToDraft(OMVOntology o, OMVPerson p){
		if (p==null || p.getFirstName()==null || p.getLastName()==null || p.getHasRole()==null) {System.out.println("Workflow support requires to specify the ontology editor and its role...");return;}		
		
		if (getOntologyState(o).equalsIgnoreCase(Constants.DraftState)) return;
		//First remove current ontology action if exists i.e. only one ontology action is required
		removeOntologyActions(o);
		//update change state
		String ontologyID=getOntologyID(o);
		removeOntologyState(ontologyID);
		addOntologyState(ontologyID, Constants.DraftState);
		
		//add action
		MoveToDraft obj = new MoveToDraft();
		obj.setRelatedOntology(o);
		if (p!=null) obj.setPerformedBy(p);
		Date now = new Date();
		String sNow = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(now);
		obj.setTimestamp(sNow);
		
		List pList = new LinkedList();
		pList.clear();
		pList=WorkflowProperties.getActionProperties(obj);
		//Specify class name to add
		String concept=getActionConcept(obj);
		OntologyProperty tProp = new OntologyProperty(Constants.name, concept);
		pList.add(tProp);
		
		IOntology.addConceptToRegistry(1,pList,60, null);
	}
	@SuppressWarnings("unchecked")
	public void moveToBeApproved(OMVOntology o, OMVPerson p){
		if (p==null || p.getFirstName()==null || p.getLastName()==null || p.getHasRole()==null) {System.out.println("Workflow support requires to specify the ontology editor and its role...");return;}
		//update ontology state if necessary
		//get all changes of that ontology
		ChangeManagement cMgmt= new ChangeManagement();
		Set<OMVChange> changes= cMgmt.getTrackedChanges(o,new OMVEntityChange(), null, null);
		//check every change State
		boolean changeOntoState=true;
		Iterator cIt=changes.iterator();
		while (cIt.hasNext()){
			OMVChange cTemp=(OMVChange)cIt.next();
			ObjectProperty changeObjectProperty = KAON2Manager.factory().objectProperty(Constants.WORKFLOWURI + Constants.hasEntityState);
			Individual entityValue=getPropertyValue(cTemp.getURI(),changeObjectProperty,Constants.CHANGEURI+getChangeConcept(cTemp));
			if (!entityValue.getURI().equalsIgnoreCase(Constants.ToBeApprovedState)){
				changeOntoState=false;
				break;
			}
		}
		if (!changeOntoState) return;
		
		//First remove current ontology action if exists i.e. only one ontology action is required
		removeOntologyActions(o);
		//update change state
		String ontologyID=getOntologyID(o);
		removeOntologyState(ontologyID);
		addOntologyState(ontologyID, Constants.ToBeApprovedState);
		
		//add action
		MoveToBeApproved obj = new MoveToBeApproved();
		obj.setRelatedOntology(o);
		if (p!=null) obj.setPerformedBy(p);
		Date now = new Date();
		String sNow = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(now);
		obj.setTimestamp(sNow);
		
		List pList = new LinkedList();
		pList.clear();
		pList=WorkflowProperties.getActionProperties(obj);
		//Specify class name to add
		String concept=getActionConcept(obj);
		OntologyProperty tProp = new OntologyProperty(Constants.name, concept);
		pList.add(tProp);
		IOntology.addConceptToRegistry(1,pList,60, null);
	}
	@SuppressWarnings("unchecked")
	public void approval(OMVOntology o, OMVPerson p){
		if (p==null || p.getFirstName()==null || p.getLastName()==null || p.getHasRole()==null) {System.out.println("Workflow support requires to specify the ontology editor and its role...");return;}
		//update ontology state if necessary
		//get all changes of that ontology
		ChangeManagement cMgmt= new ChangeManagement();
		Set<OMVChange> changes= cMgmt.getTrackedChanges(o,new OMVEntityChange(), null, null);
		//check every change State
		boolean changeOntoState=true;
		Iterator cIt=changes.iterator();
		while (cIt.hasNext()){
			OMVChange cTemp=(OMVChange)cIt.next();
			ObjectProperty changeObjectProperty = KAON2Manager.factory().objectProperty(Constants.WORKFLOWURI + Constants.hasEntityState);
			Individual entityValue=getPropertyValue(cTemp.getURI(),changeObjectProperty,Constants.CHANGEURI+getChangeConcept(cTemp));
			if (!entityValue.getURI().equalsIgnoreCase(Constants.ApprovedState)){
				changeOntoState=false;
				break;
			}
		}
		if (!changeOntoState) return;
		
		//First remove current ontology action if exists i.e. only one ontology action is required
		removeOntologyActions(o);
		//update change state
		String ontologyID=getOntologyID(o);
		removeOntologyState(ontologyID);
		addOntologyState(ontologyID, Constants.ApprovedState);
		
		//add action
		Approval obj = new Approval();
		obj.setRelatedOntology(o);
		if (p!=null) obj.setPerformedBy(p);
		Date now = new Date();
		String sNow = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(now);
		obj.setTimestamp(sNow);
		
		List pList = new LinkedList();
		pList.clear();
		pList=WorkflowProperties.getActionProperties(obj);
		//Specify class name to add
		String concept=getActionConcept(obj);
		OntologyProperty tProp = new OntologyProperty(Constants.name, concept);
		pList.add(tProp);
		IOntology.addConceptToRegistry(1,pList,60, null);
	}
	@SuppressWarnings("unchecked")
	public void publish(OMVOntology fromPublicVersion, OMVOntology toPublicVersion, OMVPerson p){
		if (p==null || p.getFirstName()==null || p.getLastName()==null || p.getHasRole()==null) {System.out.println("Workflow support requires to specify the ontology editor and its role...");return;}
		//checking editor role permissions
		if (!p.getHasRole().equalsIgnoreCase(Constants.Validator)) {System.out.println("Only validators are allowed to publish ontologies...");return;}
		//Check here that ontology is in approved state
		
		//First remove current ontology action if exists i.e. only one ontology action is required
		removeOntologyActions(fromPublicVersion);
		//update change state
		String ontologyID=getOntologyID(fromPublicVersion);
		removeOntologyState(ontologyID);
		addOntologyState(ontologyID, Constants.PublishState);
		
		//add action
		Publish obj = new Publish();
		obj.setRelatedOntology(fromPublicVersion);
		obj.setFromPublicVersion(fromPublicVersion);
		obj.setToPublicVersion(toPublicVersion);
		if (p!=null) obj.setPerformedBy(p);
		Date now = new Date();
		String sNow = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(now);
		obj.setTimestamp(sNow);
		
		List pList = new LinkedList();
		pList.clear();
		pList=WorkflowProperties.getActionProperties(obj);
		//Specify class name to add
		String concept=getActionConcept(obj);
		OntologyProperty tProp = new OntologyProperty(Constants.name, concept);
		pList.add(tProp);
		IOntology.addConceptToRegistry(1,pList,60, null);
	}

	//GET INFORMATION
	public List<Action> getEntityActionsHistory(OMVOntology o, Ontology registry, String fromChange){
		Ontology targetRegistry;
		if (registry!=null) targetRegistry=registry;
		else targetRegistry = localRegistry;
		
		String [] actions = {"Delete", "Insert", "Update", "RejectToApproved", "RejectToBeApproved", "RejectToDraft", "SendToApproved", "SendToBeApproved", "SendToBeDeleted"};
		List<Action> replyActions = new LinkedList<Action>();
		List<OMVChange> workList = new LinkedList<OMVChange>(); 
		ChangeManagement cMgmt= new ChangeManagement();
		List<OMVChange> changes = cMgmt.getTrackedChanges(o, targetRegistry, fromChange);
		Iterator it = changes.iterator();
		while (it.hasNext()){
			OMVChange t = (OMVChange)it.next();
			if (t instanceof OMVEntityChange){
				workList.add(t);
			}
		}
		it = workList.iterator();
		while (it.hasNext()){
		  OMVChange c = (OMVChange)it.next();
		  for (int i=0; i<actions.length;i++){
			OWLClass oConcept = KAON2Manager.factory().owlClass(Constants.WORKFLOWURI+actions[i]);
			Individual oIndividual = KAON2Manager.factory().individual(c.getURI()+";action="+actions[i]);
			try {
				if(targetRegistry.containsAxiom(KAON2Manager.factory().classMember(oConcept,oIndividual),true)){
					Action reply = ProcessActionIndividuals.processActionIndividual(oIndividual, actions[i], targetRegistry);
					replyActions.add(reply);
				}
			} catch (KAON2Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		}
		return replyActions;
	}
	public Action getChangeAction(OMVChange o){
		String [] actions = {"Delete", "Insert", "Update", "RejectToApproved", "RejectToBeApproved", "RejectToDraft", "SendToApproved", "SendToBeApproved", "SendToBeDeleted"};
		Action replyAction = null;
		String tURN=o.getURI();
		
		
		for (int i=0; i<actions.length;i++){
			//System.out.println("trying with action: "+actions[i]);
			OWLClass oConcept = KAON2Manager.factory().owlClass(Constants.WORKFLOWURI+actions[i]);
			Individual oIndividual = KAON2Manager.factory().individual(tURN+";action="+actions[i]);
			try {
				if(localRegistry.containsAxiom(KAON2Manager.factory().classMember(oConcept,oIndividual),true)){
					Action reply = ProcessActionIndividuals.processActionIndividual(oIndividual, actions[i], localRegistry);
					replyAction=reply;
					break;
				}
			} catch (KAON2Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return replyAction;
	}
	
	public List<Action> getChangeActions(OMVChange o){	
		String [] actions = {"Delete", "Insert", "Update", "RejectToApproved", "RejectToBeApproved", "RejectToDraft", "SendToApproved", "SendToBeApproved", "SendToBeDeleted"};
		List<Action> replyAction = new LinkedList<Action>();
		String tURN=o.getURI();
		
		for (int i=0; i<actions.length;i++){
			//System.out.println("trying with action: "+actions[i]);
			OWLClass oConcept = KAON2Manager.factory().owlClass(Constants.WORKFLOWURI+actions[i]);
			Individual oIndividual = KAON2Manager.factory().individual(tURN+";action="+actions[i]);
			try {
				if(localRegistry.containsAxiom(KAON2Manager.factory().classMember(oConcept,oIndividual),true)){
					Action reply = ProcessActionIndividuals.processActionIndividual(oIndividual, actions[i], localRegistry);
					replyAction.add(reply);
				}
			} catch (KAON2Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Collections.sort(replyAction, TIME_ORDER);
		return replyAction;
	}
	
	public Action getLastChangeAction(OMVChange o){	
		List<Action> replyAction = getChangeActions(o);
		if (replyAction.size()>0)
			return replyAction.get(replyAction.size()-1);
		return null;
	}
	
	public Action getOntologyAction(OMVOntology o){
		String [] actions = {"MoveToDraft", "MoveToBeApproved", "Approval", "Publish"};
		Action replyAction = null;
		String tURN=getOntologyID(o);
		
		
		for (int i=0; i<actions.length;i++){
			//System.out.println("trying with action: "+actions[i]);
			OWLClass oConcept = KAON2Manager.factory().owlClass(Constants.WORKFLOWURI+actions[i]);
			Individual oIndividual = KAON2Manager.factory().individual(tURN+";action="+actions[i]);
			try {
				if(localRegistry.containsAxiom(KAON2Manager.factory().classMember(oConcept,oIndividual),true)){
					Action reply = ProcessActionIndividuals.processActionIndividual(oIndividual, actions[i], localRegistry);
					replyAction=reply;
					break;
				}
			} catch (KAON2Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return replyAction;
	}
	public String getOntologyState(OMVOntology o){
		String replyAction = "";
		String tURN=getOntologyID(o);
		
		OWLClass oConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.ontologyConcept);
		Individual oIndividual = KAON2Manager.factory().individual(tURN);
		try {
			if(localRegistry.containsAxiom(KAON2Manager.factory().classMember(oConcept,oIndividual),true)){
				ObjectProperty changeObjectProperty = KAON2Manager.factory().objectProperty(Constants.WORKFLOWURI + Constants.hasOntologyState);
				Individual entityValue=getPropertyValue(oIndividual.getURI(),changeObjectProperty,Constants.OMVURI+Constants.ontologyConcept);
				if (entityValue!=null)
					replyAction=entityValue.getURI();
				else return "";
			}
		} catch (KAON2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
		return replyAction;
	}
	
	public Set<OMVChange> getChangesWithState (OMVOntology o, Ontology registry, String state){
		Ontology targetRegistry;
		if (registry!=null) targetRegistry=registry;
		else targetRegistry = localRegistry;
		
		Set<OMVChange> reply = new HashSet<OMVChange>();
		ChangeManagement cMgmt= new ChangeManagement();
		Set<OMVChange> changes = cMgmt.getTrackedChangesSet(o, targetRegistry);
		Iterator it = changes.iterator();
		while (it.hasNext()){
			OMVChange c = (OMVChange)it.next();
			String st = cMgmt.getEntityStateFromChange(c.getURI());
			if (st.equalsIgnoreCase(state))
				reply.add(c);
		}
		return reply;
	}
	
	
	//WORKFLOW INFO MGMT
	public void addEntityChangeState(String c, String concept, String targetURI){
		//add hastEntityState property
		ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.WORKFLOWURI + Constants.hasEntityState);
		addProperty(c,ontologyObjectProperty, Constants.CHANGEURI+concept, targetURI );
	}
	public void removeEntityChangeState(String c, String concept){
		//remove hastEntityState property
		ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.WORKFLOWURI + Constants.hasEntityState);
		removeProperty(c,ontologyObjectProperty, Constants.CHANGEURI+concept);
	}
	public void addOntologyState(String c, String targetURI){
		//add hastEntityState property
		ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.WORKFLOWURI + Constants.hasOntologyState);
		addProperty(c,ontologyObjectProperty, Constants.OMVURI+Constants.ontologyConcept, targetURI );
	}
	public void removeOntologyState(String c){
		//remove hastOntologyState property
		ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.WORKFLOWURI + Constants.hasOntologyState);
		removeProperty(c,ontologyObjectProperty, Constants.OMVURI+Constants.ontologyConcept);
	}
	
	public void removeEntityActions (String c){
		String [] actions = {"Delete", "Insert", "Update", "RejectToApproved", "RejectToBeApproved", "RejectToDraft", "SendToApproved", "SendToBeApproved", "SendToBeDeleted"};
		//remove entity actions
		for (int i=0; i<actions.length;i++){
			//System.out.println("trying with action: "+actions[i]);
			OWLClass oConcept = KAON2Manager.factory().owlClass(Constants.WORKFLOWURI+actions[i]);
			Individual oIndividual = KAON2Manager.factory().individual(c+";action="+actions[i]);
			try {
				if(localRegistry.containsAxiom(KAON2Manager.factory().classMember(oConcept,oIndividual),true)){
					Action a= new Action();
					if (actions[i].equalsIgnoreCase("Delete")) a = new Delete();
					else if (actions[i].equalsIgnoreCase("Insert")) a = new Insert();
					else if (actions[i].equalsIgnoreCase("Update")) a = new Update();
					else if (actions[i].equalsIgnoreCase("RejectToApproved")) a = new RejectToApproved();
					else if (actions[i].equalsIgnoreCase("RejectToBeApproved")) a = new RejectToBeApproved();
					else if (actions[i].equalsIgnoreCase("RejectToDraft")) a = new RejectToDraft();
					else if (actions[i].equalsIgnoreCase("SendToApproved")) a = new SendToApproved();
					else if (actions[i].equalsIgnoreCase("SendToBeApproved")) a = new SendToBeApproved();
					else if (actions[i].equalsIgnoreCase("SendToBeDeleted")) a = new SendToBeDeleted();
					a.setURI(oIndividual.getURI());
					//System.out.println("trying with action URI: "+a.getURI());
					removeAction(a);
				}
			} catch (KAON2Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void removeOntologyActions (OMVOntology otemp){
		
		String [] actions = {"MoveToDraft", "MoveToBeApproved", "Approval", "Publish"};
		String tURN=getOntologyID(otemp);
		//remove hastOntologyState property
		ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.WORKFLOWURI + Constants.hasOntologyState);
		removeProperty(tURN,ontologyObjectProperty, Constants.OMVURI+Constants.ontologyConcept);
		//remove ontology action
		for (int i=0; i<actions.length;i++){
			//System.out.println("trying with action: "+actions[i]);
			OWLClass oConcept = KAON2Manager.factory().owlClass(Constants.WORKFLOWURI+actions[i]);
			Individual oIndividual = KAON2Manager.factory().individual(tURN+";action="+actions[i]);
			try {
				if(localRegistry.containsAxiom(KAON2Manager.factory().classMember(oConcept,oIndividual),true)){
					Action a= new Action();
					if (actions[i].equalsIgnoreCase("MoveToDraft")) a = new MoveToDraft();
					else if (actions[i].equalsIgnoreCase("MoveToBeApproved")) a = new MoveToBeApproved();
					else if (actions[i].equalsIgnoreCase("Approval")) a = new Approval();
					else if (actions[i].equalsIgnoreCase("Publish")) a = new Publish();
					a.setURI(oIndividual.getURI());
					//System.out.println("trying with action URI: "+a.getURI());
					removeAction(a);
				}
			} catch (KAON2Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void removeWorkflowHistory(OMVOntology o){
		//remove ontology action
		removeOntologyActions(o);
		//remove entity actions
		ChangeManagement cMgmt= new ChangeManagement();
		List<OMVChange> changes= cMgmt.getTrackedChanges(o, null, null);
		Iterator it2 = changes.iterator();
		while (it2.hasNext()){
			OMVChange t = (OMVChange)it2.next();
			if (t instanceof OMVEntityChange){
				removeEntityChangeState(t.getURI(), getChangeConcept(t));
				removeEntityActions (t.getURI());
			}
		}
		
	}
	@SuppressWarnings("unchecked")
	public void removeAction(Action a)
	{
		List pList = new LinkedList();
		if (a.getURI()!=null){
			pList.clear();
			pList=WorkflowProperties.getActionProperties(a);
			//Specify class name to add
			String concept=getActionConcept(a);
			OntologyProperty tProp = new OntologyProperty(Constants.name, concept);
			pList.add(tProp);
			IOntology.addConceptToRegistry(4,pList,60, null);
		}
	}
	
	//UTILITIES
	private void removeProperty(String URI, ObjectProperty oP, String Concept){
		Map propertyMap = new HashMap();
		File localRegistryFile = mOyster2.getLocalRegistryFile();
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		Collection hasSet = new LinkedList();
		
		OWLClass oConcept = KAON2Manager.factory().owlClass(Concept);
		Individual oIndividual = KAON2Manager.factory().individual(URI);
		try {
			if(localRegistry.containsAxiom(KAON2Manager.factory().classMember(oConcept,oIndividual),true)){	
				propertyMap = oIndividual.getObjectPropertyValues(localRegistry);
				hasSet = (Collection)propertyMap.get(oP);
				if (hasSet==null) return;
				Iterator i = hasSet.iterator();
				while (i.hasNext()){
					Individual indiv = (Individual)i.next();
					changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(oP,oIndividual,indiv),OntologyChangeEvent.ChangeType.REMOVE));
				}
			}
			if (changes.size()>0){ 
				localRegistry.applyChanges(changes);
				localRegistry.persist();
				try {
					if (localRegistryFile!=null)
						localRegistry.saveOntology(OntologyFileFormat.OWL_RDF,localRegistryFile,"ISO-8859-1");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			changes.clear();
		} catch (KAON2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void addProperty(String URI, ObjectProperty oP, String Concept, String targetURI){
		File localRegistryFile = mOyster2.getLocalRegistryFile();
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();

		OWLClass oConcept = KAON2Manager.factory().owlClass(Concept);
		Individual oIndividual = KAON2Manager.factory().individual(URI);
		Individual targetIndividual = KAON2Manager.factory().individual(targetURI);
		try {
			if(localRegistry.containsAxiom(KAON2Manager.factory().classMember(oConcept,oIndividual),true)){	
				Map<ObjectProperty,Set<Individual>> check=oIndividual.getObjectPropertyValues(localRegistry);
				Set<Individual>checkValues=check.get(oP);
				if (checkValues==null || !checkValues.contains(targetIndividual))
					changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(oP,oIndividual,targetIndividual),OntologyChangeEvent.ChangeType.ADD));
			}
			if (changes.size()>0){ 
				localRegistry.applyChanges(changes);
				localRegistry.persist();
				try {
					if (localRegistryFile!=null)
						localRegistry.saveOntology(OntologyFileFormat.OWL_RDF,localRegistryFile,"ISO-8859-1");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			changes.clear();
		} catch (KAON2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private Individual getPropertyValue(String URI, ObjectProperty oP, String Concept){
		OWLClass oConcept = KAON2Manager.factory().owlClass(Concept);
		Individual oIndividual = KAON2Manager.factory().individual(URI);
		try {
			if(localRegistry.containsAxiom(KAON2Manager.factory().classMember(oConcept,oIndividual),true)){	
				Individual targetIndividual = oIndividual.getObjectPropertyValue(localRegistry,oP);
				if (targetIndividual!=null) return targetIndividual;
				else return null;
			}
		} catch (KAON2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private String getOntologyID(OMVOntology o){
		String tURN="";
		if (o.getURI()!=null){
			tURN=o.getURI();
			boolean hasVersion=false;
			if (o.getVersion()!=null){
				tURN=tURN+"?version="+o.getVersion();//+";";
				hasVersion=true;
			}
			if (o.getResourceLocator()!=null){
				if (!hasVersion) tURN=tURN+"?";
				else tURN=tURN+";";
				tURN=tURN+"location="+o.getResourceLocator();
			}
			tURN=tURN.replace(" ", "_");
		}
		return tURN;
	}
	
	private void updateOntologyState(OMVOntology mainOntoReply, OMVPerson p, String nextState){
		if (nextState.equalsIgnoreCase(Constants.DraftState)) moveToDraft(mainOntoReply,p);
		else if (nextState.equalsIgnoreCase(Constants.ToBeApprovedState) || nextState.equalsIgnoreCase(Constants.ToBeDeletedState)) moveToBeApproved(mainOntoReply,p);
		else if (nextState.equalsIgnoreCase(Constants.ApprovedState) || nextState.equalsIgnoreCase(Constants.DeletedState)) approval(mainOntoReply,p);
	}
	
	private static String getActionConcept(Action a){
		if (a.getClass().getSimpleName()!=null && a.getClass().getSimpleName()!="") return a.getClass().getSimpleName();
		else return ""; 
	}
	private static String getChangeConcept(OMVChange a){
		if (a.getClass().getSimpleName()!=null && a.getClass().getSimpleName()!="") return a.getClass().getSimpleName();
		else return ""; 
	}
	
	static final Comparator<Action> TIME_ORDER =
        new Comparator<Action>() {
		public int compare(Action e1, Action e2) {
			try {
				Date d1=DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).parse(e1.getTimestamp());
				Date d2=DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).parse(e2.getTimestamp());
				return d1.compareTo(d2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		}
	};
}
	

