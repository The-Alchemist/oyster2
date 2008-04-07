package org.neon_toolkit.registry.api.change;


import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.neon_toolkit.omv.api.core.OMVOntology;
import org.neon_toolkit.omv.api.core.OMVPerson;
import org.neon_toolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange;
import org.neon_toolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange;
import org.neon_toolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLOntologyChange.AddDatatype;
import org.neon_toolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLOntologyChange.RemoveDatatype;
import org.neon_toolkit.omv.api.extensions.change.OMVChange;
import org.neon_toolkit.omv.api.extensions.change.OMVChangeSpecification;
import org.neon_toolkit.omv.api.extensions.change.OMVLog;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVCompositeChange;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVEntityChange;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.ClassChange;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.IndividualChange;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.PropertyChange;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.OntologyChange.AddClass;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.OntologyChange.AddIndividual;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.OntologyChange.AddProperty;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.OntologyChange.RemoveClass;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.OntologyChange.RemoveIndividual;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.OntologyChange.RemoveProperty;
import org.neon_toolkit.owlodm.api.Axiom;
import org.neon_toolkit.registry.api.individuals.ProcessAxiomIndividuals;
import org.neon_toolkit.registry.api.individuals.ProcessChangeIndividuals;
import org.neon_toolkit.registry.api.individuals.ProcessOMVIndividuals;
import org.neon_toolkit.registry.api.properties.AxiomProperties;
import org.neon_toolkit.registry.api.properties.ChangeProperties;
import org.neon_toolkit.registry.api.properties.ChangeSpecificationProperties;
import org.neon_toolkit.registry.api.properties.LogProperties;
import org.neon_toolkit.registry.api.properties.OMVProperties;
import org.neon_toolkit.registry.api.workflow.WorkflowManagement;
import org.neon_toolkit.registry.oyster2.Constants;
import org.neon_toolkit.registry.oyster2.ImportOntology;
import org.neon_toolkit.registry.oyster2.OntologyProperty;
import org.neon_toolkit.registry.oyster2.Oyster2Factory;
import org.semanticweb.kaon2.api.KAON2Exception;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.OntologyChangeEvent;
import org.semanticweb.kaon2.api.formatting.OntologyFileFormat;
import org.semanticweb.kaon2.api.owl.elements.Description;
import org.semanticweb.kaon2.api.owl.elements.Individual;
import org.semanticweb.kaon2.api.owl.elements.OWLClass;
import org.semanticweb.kaon2.api.owl.elements.ObjectProperty;

/**
 * The class ChangeManager provides the methods to support the
 * change support 
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class ChangeManagement {
	static Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private ImportOntology IOntology= new ImportOntology();
	private static Ontology localRegistry = mOyster2.getLocalHostOntology();
	
	
	public ChangeManagement()
    {
	
    }
	
	/**
	 * Register a new change into Oyster2 registry.
	 * @param o is the OMVChange object representing the
	 * change that will be registered. This method creates the
	 * whole trace, update the log and change specification
	 * and the corresponding workflow actions if workflow is enabled.
	 */
	@SuppressWarnings("unchecked")
	public String register(OMVChange o)
	{
		List pList = new LinkedList();
		if (o.getAppliedToOntology()!=null){
			if (!isTracked(o.getAppliedToOntology())){System.out.println("To register changes the ontology tracking of this ontology should be activated, use method startTracking...");return null;}
			if (o instanceof OMVAtomicChange){
				OMVAtomicChange t = (OMVAtomicChange)o;
				if (t.getAppliedAxiom()==null) return null;
			}
			else if (o instanceof OMVEntityChange){
				Set<String> remainingChanges = getChangesIds(o.getAppliedToOntology(),null);				
				OMVEntityChange t = (OMVEntityChange)o;
				if (t.getConsistsOfAtomicOperation()!=null && t.getConsistsOfAtomicOperation().size()>0) {
					Iterator it = t.getConsistsOfAtomicOperation().iterator();
					while (it.hasNext()){
						String str = (String)it.next();
						if (str==null) return null;
						else{
							boolean goodToGo=false;
							Iterator rem=remainingChanges.iterator();
							while (rem.hasNext()){
								String remChange=(String)rem.next();
								if (remChange.equalsIgnoreCase(str)) goodToGo=true;
							}
							if (!goodToGo) {
								System.out.println("Before registering an entity change with atomic changes, its atomic changes should be registered in the log");
								return null;
							}
						}
					}
				}
			}
			else if (o instanceof OMVCompositeChange){
				Set<String> remainingChanges = getChangesIds(o.getAppliedToOntology(),null);
				OMVCompositeChange t = (OMVCompositeChange)o;
				if (t.getConsistsOf()!=null && t.getConsistsOf().size()>0) {
					Iterator it = t.getConsistsOf().iterator();
					while (it.hasNext()){
						String str = (String)it.next();
						if (str==null) return null;
						else{
							boolean goodToGo=false;
							Iterator rem=remainingChanges.iterator();
							while (rem.hasNext()){
								String remChange=(String)rem.next();
								if (remChange.equalsIgnoreCase(str)) goodToGo=true;
							}
							if (!goodToGo) {
								System.out.println("Before registering a composite change with related changes, its related changes should be registered in the log");
								return null;
							}
						}
					}
				}
			}
			pList.clear();
			pList=ChangeProperties.getChangeProperties(o);
			//Generate trace
			String previousChange="";
			boolean first=true;
			if (o.getHasPreviousChange()==null || o.getHasPreviousChange().equalsIgnoreCase("")){
				previousChange=generateTrace(o.getAppliedToOntology());
				if (previousChange!=null){
					OntologyProperty prop = new OntologyProperty(Constants.hasPreviousChange, previousChange);
					pList.add(prop);
					first=false;
				}
			}else{
				previousChange=o.getHasPreviousChange();
				first=false;
			}
			//Specify class name to add
			String concept=getChangeConcept(o);
			OntologyProperty tProp = new OntologyProperty(Constants.name, concept);
			pList.add(tProp);
			
			String tURN="";
			Iterator it1 = pList.iterator();
			while(it1.hasNext()){
				OntologyProperty prop = (OntologyProperty)it1.next();
				if(prop.getPropertyName().equals(Constants.URI)){
					tURN = prop.getPropertyValue();
					break;
				}
			}
			
			//register change
			IOntology.addConceptToRegistry(1,pList,30, null);
			
			//Workflow info
			if (mOyster2.getWorkflowSupport()){
				if (o instanceof OMVEntityChange){
					WorkflowManagement wMgmt= new WorkflowManagement();
					OMVPerson editor=null;
					if (o.getHasAuthor().size()>0){
						editor=(OMVPerson)o.getHasAuthor().iterator().next();
					}			
					//Add entity action
					//CHECK ROLES AND NEXT STATES
					boolean success=false;
					if (o instanceof ClassChange || o instanceof IndividualChange || o instanceof PropertyChange || o instanceof OWLObjectPropertyChange || o instanceof OWLDataPropertyChange){
						success=wMgmt.update(tURN,editor);
					}else if (o instanceof AddClass || o instanceof AddIndividual || o instanceof AddProperty || o instanceof AddDatatype ){
						success=wMgmt.insert(tURN,editor);
					}else if (o instanceof RemoveClass || o instanceof RemoveIndividual || o instanceof RemoveProperty || o instanceof RemoveDatatype ){
						success=wMgmt.submitToBeDeleted(tURN,editor);
					}
					//Set entity state ->Automatically
					if (!success){ //if not success remove change (and its corresponding atomics & axioms) and return
						//get atomic changes
						OMVEntityChange oriChange = (OMVEntityChange)o;
						oriChange.setURI(tURN);
						Set<OMVAtomicChange> changesIn=getAtomicChanges(oriChange);
						if (changesIn!=null && changesIn.size()>0) {
						
							//sort atomic changes
							List<OMVAtomicChange> changesList=new LinkedList<OMVAtomicChange>();
							Iterator s = changesIn.iterator();
							while (s.hasNext()){
								OMVAtomicChange t = (OMVAtomicChange)s.next();
								if (t.getURI().equalsIgnoreCase(previousChange)){
									changesList.add(t);
									break;
								}
							}
							OMVChange ct = changesList.get(0);
							if (ct !=null){
								boolean keepGoing=true;
								while (ct.getHasPreviousChange()!=null && keepGoing){
									boolean found=false;
									Iterator sort = changesIn.iterator();
									while (sort.hasNext()){
										OMVAtomicChange t = (OMVAtomicChange)sort.next();
										if (t.getURI().equalsIgnoreCase(ct.getHasPreviousChange())){
											changesList.add(t);
											ct=t;
											found=true;
											break;
										}
									}
									if (!found) keepGoing=false;//ct.setHasPreviousChange(null);
								}
							}
						
							//remove atomic changes & references in change specification	
							Iterator iTemp=changesIn.iterator();
							while (iTemp.hasNext()){
								OMVAtomicChange at = (OMVAtomicChange)iTemp.next();
								remove (at);
							
								ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.CHANGEURI + Constants.hasChange);
								removeProperty(getCSID(o.getAppliedToOntology()),ontologyObjectProperty, Constants.CHANGEURI+Constants.changeSpecificationConcept, at.getURI());
							}
							//update log & change specification if necessary
							Set<String> remainingChanges = getChangesIds(oriChange.getAppliedToOntology(),null);
							if (remainingChanges==null || remainingChanges.size()<=0){
								removeRelatedLog(o.getAppliedToOntology());
								removeChangeSpecification(o.getAppliedToOntology());
							}
							else{ 
								if (changesList.get(changesList.size()-1).getHasPreviousChange()!=null){ 
									List pListTemp = new LinkedList();
									OntologyProperty tPropTemp = new OntologyProperty(Constants.URI, changesList.get(changesList.size()-1).getHasPreviousChange());
									pListTemp.add(tPropTemp);
									updateLog(o.getAppliedToOntology(),pListTemp,false);
								}else{						
									removeRelatedLog(o.getAppliedToOntology());
									removeChangeSpecification(o.getAppliedToOntology());
								}
							}
						}else{
							//update log & change specification if necessary
							Set<String> remainingChanges = getChangesIds(oriChange.getAppliedToOntology(),null);
							if (remainingChanges==null || remainingChanges.size()<=0){
								removeRelatedLog(o.getAppliedToOntology());
								removeChangeSpecification(o.getAppliedToOntology());
							}
							else{
								if (oriChange.getHasPreviousChange()!=null){
									List pListTemp = new LinkedList();
									OntologyProperty tPropTemp = new OntologyProperty(Constants.URI, oriChange.getHasPreviousChange());
									pListTemp.add(tPropTemp);
									updateLog(o.getAppliedToOntology(),pListTemp,false);
								}else{
									removeRelatedLog(o.getAppliedToOntology());
									removeChangeSpecification(o.getAppliedToOntology());
								}
							}
						}
						//remove this entity change
						remove (oriChange);
						return null;
					} 
				}
			}
			
			//when success update log and change specification
			updateLog(o.getAppliedToOntology(),pList, first);
			updateChangeSpecification(o.getAppliedToOntology(),pList,first);
			return tURN;
		}
		return null;
	}
	
	
	/**
	 * Register a new change log into Oyster2 registry.
	 * @param o is the OMVLog object representing the
	 * log that will be registered.
	 * NOTE: This method is called within the change management 
	 * implementation, it should not be called directly. 
	 */
	public void register(OMVLog o)
	{
		List pList = new LinkedList();
		if (o.getHasRelatedOntology()!=null){
			if (!isTracked(o.getHasRelatedOntology())){System.out.println("To register changes the ontology tracking of this ontology should be activated, use method startTracking...");return;}
			pList.clear();
			pList=LogProperties.getLogProperties(o);
			IOntology.addConceptToRegistry(1,pList,31, null);
		}
	}
	
	
	/**
	 * Register a new change specification into Oyster2 
	 * registry.
	 * @param o is the OMVChangeSpecification object 
	 * that will be registered.
	 */
	public void register(OMVChangeSpecification o)
	{
		List pList = new LinkedList();
		if (o.getChangeFromVersion()!=null){
			if (!isTracked(o.getChangeFromVersion())){System.out.println("To register changes the ontology tracking of this ontology should be activated, use method startTracking...");return;}
			pList.clear();
			pList=ChangeSpecificationProperties.getChangeSpecificationProperties(o);
			IOntology.addConceptToRegistry(1,pList,32, null);
		}
	}
		
	
	/**
	 * Replaces a new change into Oyster2 registry.
	 * @param o is the OMVChange object representing the
	 * change that will be replaced (i.e. this method is 
	 * implemented as a merge)
	 * NOTE: This method should be called within the change management 
	 * implementation, it should not be called directly.
	 */
	@SuppressWarnings("unchecked")
	public void replace(OMVChange o)
	{
		if (o.getURI()==null) return;
		if (o instanceof OMVAtomicChange){ //IF THE AXIOMS ARE NOT KNOWN IS NOT ALLOWED TO REPLACE ATOMIC CHANGE
			if (((OMVAtomicChange)o).getAppliedAxiom()==null) return;
			if (((OMVAtomicChange)o).getAppliedAxiom().getURI()==null) return;
		}
		List pList = new LinkedList();
		pList.clear();
		pList=ChangeProperties.getChangeProperties(o);
		//Specify class name to add
		String concept=getChangeConcept(o);
		OntologyProperty tProp = new OntologyProperty(Constants.name, concept);
		pList.add(tProp);
		//remove axioms just before replacing the change
		if (o instanceof OMVAtomicChange){
			removeRelatedAxioms((OMVAtomicChange)o);
		}
		IOntology.addConceptToRegistry(0,pList,30, null); //MERGE
	}
	
	
	/**
	 * Replaces the log into Oyster2 registry.
	 * @param o is the OMVLog object representing the
	 * log that will be replaced
	 * NOTE: This method is called within the change management 
	 * implementation, it should not be called directly.
	 */
	public void replace(OMVLog o)
	{
		List pList = new LinkedList();
		if (o.getHasRelatedOntology()!=null){
			pList.clear();
			pList=LogProperties.getLogProperties(o);
			IOntology.addConceptToRegistry(2,pList,31, null); //really replace
		}
	}
	
	
	/**
	 * Replaces the change specification object in Oyster
	 * @param o is the OMVChangeSpecification object 
	 * that will be replaced. This method is implemented as
	 * a merge
	 */
	public void replace(OMVChangeSpecification o)
	{
		List pList = new LinkedList();
		if (o.getChangeFromVersion()!=null){
			if (!isTracked(o.getChangeFromVersion())){System.out.println("To register changes the ontology tracking of this ontology should be activated, use method startTracking...");return;}
			pList.clear();
			pList=ChangeSpecificationProperties.getChangeSpecificationProperties(o);
			IOntology.addConceptToRegistry(0,pList,32, null); //merge
		}
	}
		
	/**
	 * Removes a change from Oyster2 registry.
	 * @param o is the OMVChange object representing the
	 * change that will be removed.
	 * NOTE: This method is called within the change management 
	 * implementation, it should not be called directly.
	 */
	@SuppressWarnings("unchecked")
	public void remove(OMVChange o)
	{
		if (o.getURI()==null) return;
		if (o instanceof OMVAtomicChange){ //IF THE AXIOMS ARE NOT KNOWN IS NOT ALLOWED TO REPLACE ATOMIC CHANGE
			if (((OMVAtomicChange)o).getAppliedAxiom()==null) return;
			if (((OMVAtomicChange)o).getAppliedAxiom().getURI()==null) return;
		}
		List pList = new LinkedList();
		pList.clear();
		pList=ChangeProperties.getChangeProperties(o);
		//Specify class name to add
		String concept=getChangeConcept(o);
		OntologyProperty tProp = new OntologyProperty(Constants.name, concept);
		pList.add(tProp);
		//remove axioms just before removing the change
		if (o instanceof OMVAtomicChange){
			removeRelatedAxioms((OMVAtomicChange)o);
		}
		IOntology.addConceptToRegistry(4,pList,30, null);
	}
	
	/**
	 * Removes a Log from Oyster2 registry.
	 * @param o is the OMVLog object representing the
	 * log that will be removed.
	 * NOTE: This method is called within the change management 
	 * implementation, it should not be called directly.
	 */
	public void remove(OMVLog o)
	{
		List pList = new LinkedList();
		if (o.getHasRelatedOntology()!=null){
			pList.clear();
			pList=LogProperties.getLogProperties(o);
			IOntology.addConceptToRegistry(4,pList,31, null);
		}
	}
	
	/**
	 * Removes a changeSpecification from Oyster2 registry and all
	 * its related changes, log, and if available workflow
	 * information i.e. removes all change information from the ontology
	 * specified by the change specification
	 * @param o is the OMVChangeSpecification object 
	 * 
	 */
	public void remove(OMVChangeSpecification o)
	{
		if (o.getChangeFromVersion()!=null){
			if (mOyster2.getWorkflowSupport()){
				WorkflowManagement wMgmt= new WorkflowManagement();
				wMgmt.removeWorkflowHistory(o.getChangeFromVersion());
			}
			removeRelatedChanges(o.getChangeFromVersion());
			removeRelatedLog(o.getChangeFromVersion());
			removeChangeSpecification(o.getChangeFromVersion());
		}
	}
	
	/**
	 * Gets the entity state
	 * @param o is the ontology for which we want to get the 
	 * entity state
	 * @param entityURI is the URI of the target entity
	 * @return the entity state.
	 */
	public String getEntityState(OMVOntology o, String entityURI){
		OMVEntityChange target= new OMVEntityChange();
		Set<OMVChange> entityChanges = getTrackedChanges(o, new OMVEntityChange(), null, null);
		Iterator it = entityChanges.iterator();
		boolean found=false;
		while (it.hasNext()){
			OMVChange a = (OMVChange)it.next();  //JUST TO AVOID EXCEPTIONS, BUT SHOULDNT BE OTHERWISE
			if (a instanceof OMVEntityChange){
				OMVEntityChange t = (OMVEntityChange)a;
				Set<String> entities = t.getHasRelatedEntity();
				Iterator it2 = entities.iterator();
				while (it2.hasNext()){
					String checkURI= (String)it2.next();
					if (checkURI.equalsIgnoreCase(entityURI)){
						found=true;
						target=t;
						break;
					}
				}
			}
			if (found) break;
		}
		if (found) return target.getHasEntityState();
		else return "";
	}
	
	/**
	 * Gets the entity associated to a specific change
	 * @param changeURI is the uri of the change for which for which we
	 * want to search the related entity
	 * @return The uri of the related entity 
	 */
	public String getRelatedEntity(String changeURI){
		//try get change concept
		Individual oIndividual = KAON2Manager.factory().individual(changeURI);
		String conceptChange=null;
		try {
			conceptChange = oIndividual.getDescriptionsMemberOf(localRegistry).iterator().next().toString();
		} catch (KAON2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.CHANGEURI + Constants.hasRelatedEntity);
		Individual t = getPropertyValue(changeURI, ontologyObjectProperty,conceptChange, null);
		if (t!=null) return t.getURI();
		else return "";
	}
	
	/**
	 * Specify that the changes of the specified ontology
	 * should be logged
	 * @param o is the OMVOntology to track
	 */
	public void startTracking(OMVOntology o){
		if (o!=null && o.getURI()!=null && o.getResourceLocator()!=null){
			//just to make sure the ontology core information is available
			List pList = new LinkedList();
			pList=OMVProperties.getProperties(o);
			IOntology.addImportOntologyToRegistry(pList,0, null);
			
			String tURN=getOntologyID(o);
			Individual localPeer=mOyster2.getLocalAdvertInformer().getLocalPeer();
			ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.POMVURI + Constants.trackOntology);
			addProperty(localPeer.getURI(), ontologyObjectProperty, Constants.POMVURI+Constants.peerConcept, tURN);
		}else System.out.println("The ontology requires at least URI and resourceLocator to identifiy it");
	}
	
	/**
	 * Stops logging the changes of the specified ontology
	 * @param o is the OMVOntology to stop tracking
	 */
	public void stopTracking(OMVOntology o){
		if (o!=null && o.getURI()!=null && o.getResourceLocator()!=null){
			String tURN=getOntologyID(o);
			Individual localPeer=mOyster2.getLocalAdvertInformer().getLocalPeer();
			ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.POMVURI + Constants.trackOntology);
			removeProperty(localPeer.getURI(), ontologyObjectProperty, Constants.POMVURI+Constants.peerConcept, tURN);
		}else System.out.println("The ontology requires at least URI and resourceLocator to identifiy it");
	}
	
	/**
	 * Get the ontologies that are being tracked by the
	 * local peer independently if changes are already available
	 * @return The set of the ontologies 
	 */
	public Set<OMVOntology> getOntologiesTrackedByPeer(){
		Set<OMVOntology> OMVSet = new HashSet<OMVOntology>();
		Individual localPeer=mOyster2.getLocalAdvertInformer().getLocalPeer();
		ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.POMVURI + Constants.trackOntology);
		Set<Individual> ontos = getPropertyValues(localPeer.getURI(), ontologyObjectProperty, Constants.POMVURI+Constants.peerConcept, null);
		Iterator it = ontos.iterator();
		while (it.hasNext()){
			Individual t = (Individual)it.next();
			OMVOntology mainOntoReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(t, "ontology",localRegistry);
			OMVSet.add(mainOntoReply);
		}
		return OMVSet;
	}
	
	/**
	 * Verify if an ontology has been registered to track
	 * its changes
	 * @param o is the OMVOntology to check
	 */
	public boolean isTracked(OMVOntology o){
		String tURN=getOntologyID(o);
		Individual localPeer=mOyster2.getLocalAdvertInformer().getLocalPeer();
		ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.POMVURI + Constants.trackOntology);
		Set<Individual> ontos = getPropertyValues(localPeer.getURI(), ontologyObjectProperty, Constants.POMVURI+Constants.peerConcept, null);
		Iterator it = ontos.iterator();
		while (it.hasNext()){
			Individual t = (Individual)it.next();
			if (t.getURI().equalsIgnoreCase(tURN)) return true;
		}
		return false;
	}
	
	//METHODS TO DELETE THE WHOLE HISTORY OF CHANGESPECIFICATION
	//PRIVATE IMPLEMENTATIONS
	@SuppressWarnings("unchecked")
	private void remove(Axiom o)
	{
		List pList = new LinkedList();
		pList.clear();
		pList=AxiomProperties.getAxiomProperties(o);
		
		OntologyProperty tProp = new OntologyProperty(Constants.URI, o.getURI());
		pList.add(tProp);
		String concept=ChangeProperties.getAxiomConcept(o);
		tProp = new OntologyProperty(Constants.name, concept);
		pList.add(tProp);
		IOntology.addConceptToRegistry(4,pList,50, null);
		
	}
	
	private void removeRelatedChanges(OMVOntology o){
		List<OMVChange> changes=getTrackedChanges(o, null, null);
		Iterator it2 = changes.iterator();
		while (it2.hasNext()){
			OMVChange t = (OMVChange)it2.next();			
			remove (t);
		}
	}
	
	private void removeRelatedLog(OMVOntology otemp){
		OMVLog log = new OMVLog();
		log.setHasRelatedOntology(otemp);
		remove (log);
	}
				
	private void removeRelatedAxioms(OMVAtomicChange o){
		Set<Axiom> t = getAxioms(o);
		Iterator it2 = t.iterator();
		while (it2.hasNext()){
			Axiom a = (Axiom)it2.next();
			if (a.getURI()!=null){
				//System.out.println("removing axiom: "+a.getURI() +" of class "+a.getClass().getName());
				remove (a);
			}
		}
	}
	
	private void removeChangeSpecification(OMVOntology otemp){
		String tURN="";
		if (otemp.getURI()!=null){
			tURN=otemp.getURI();
			boolean hasVersion=false;
			if (otemp.getVersion()!=null){
				tURN=tURN+"?version="+otemp.getVersion();//+";";
				hasVersion=true;
			}
			if (otemp.getResourceLocator()!=null){
				if (!hasVersion) tURN=tURN+"?";
				else tURN=tURN+";";
				tURN=tURN+"location="+otemp.getResourceLocator();
			}
			tURN=tURN.replace(" ", "_");
			if (tURN.indexOf("?")>0){
				tURN=tURN+";";
			}else{
				tURN=tURN+"?";
			}
			tURN=tURN+"changeSpecification";
			OMVChangeSpecification cs = new OMVChangeSpecification();
			cs.setURI(tURN);
			List pList = new LinkedList();
			pList.clear();
			pList=ChangeSpecificationProperties.getChangeSpecificationProperties(cs);
			IOntology.addConceptToRegistry(4,pList,32, null);
		}
	}
	
	/**
	 * Search Oyster2 registry to retrieve all available changes
	 * for a specific ontology in historical order, starting
	 * with the specified change. 
	 * @param o is the specified ontology 
	 * @param fromChange is the starting point in the change history.
	 * If not specified (i.e. fromChange=null) this method returns the
	 * complete history
	 * @return The list of OMVChanges objects representing the
	 * ontology changes.
	 */
	//SEARCH METHODS
	public List<OMVChange> getTrackedChanges(OMVOntology o, Ontology registry, String fromChange){
		Ontology targetRegistry;
		if (registry==null)targetRegistry=localRegistry;
		else targetRegistry=registry;
		
		mOyster2.getLogger().info("getting changes from ..."+targetRegistry.getPhysicalURI());
		List<OMVChange> OMVRet = new LinkedList<OMVChange>();
		List<OMVChange> OMVRetFinal = new LinkedList<OMVChange>();
		Set<OMVChange> OMVSet = new HashSet<OMVChange>();
		Map propertyMap = new HashMap();
		Collection hasChangeSet = new LinkedList();
		String tURN;
		String lastChangeURI="";
		
		if (o==null) return OMVRetFinal;
		if (o.getURI()!=null){
			tURN=getOntologyID(o);
			if (tURN.indexOf("?")>0){
				tURN=tURN+";";
			}else{
				tURN=tURN+"?";
			}
			tURN=tURN+"changeSpecification";
			//GET ALL CHANGES FOR THE SPECIFIED ONTOLOGY
			OWLClass oConcept = KAON2Manager.factory().owlClass(Constants.CHANGEURI+Constants.changeSpecificationConcept);
			Individual oIndividual = KAON2Manager.factory().individual(tURN);
			try {
				if(targetRegistry.containsAxiom(KAON2Manager.factory().classMember(oConcept,oIndividual),true)){
			
					ObjectProperty hasChange = KAON2Manager.factory().objectProperty(Constants.CHANGEURI+Constants.hasChange);
					propertyMap = oIndividual.getObjectPropertyValues(targetRegistry);
					hasChangeSet = (Collection)propertyMap.get(hasChange);
					//mOyster2.getLogger().info("hasChange size..."+hasChangeSet.size());
					Iterator i = hasChangeSet.iterator();
					while (i.hasNext()){
						Individual change = (Individual)i.next();
						//System.out.println("change: "+change.getURI().toString() +" description: "+change.getDescriptionsMemberOf(targetRegistry).iterator().next());
						OMVChange c=ProcessChangeIndividuals.processChangeIndividual(change, change.getDescriptionsMemberOf(targetRegistry).iterator().next().toString(), targetRegistry);
						OMVSet.add(c);			
					}
				}
			} catch (KAON2Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//SET THE LAST CHANGE URI TO RETURN SORTED LIST OF CHANGES
			lastChangeURI=getLastChangeIdFromLog(o, targetRegistry);
		}
		
		//SORT LIST
		if (lastChangeURI!=""){
			Iterator s = OMVSet.iterator();
			while (s.hasNext()){
				OMVChange t = (OMVChange)s.next();
				if (t.getURI().equalsIgnoreCase(lastChangeURI)){
					OMVRet.add(t);
					break;
				}
			}
			OMVChange ct = OMVRet.get(0);
			if (ct !=null){
				while (ct.getHasPreviousChange()!=null){
					boolean found=false;
					Iterator sort = OMVSet.iterator();
					while (sort.hasNext()){
						OMVChange t = (OMVChange)sort.next();
						if (t.getURI().equalsIgnoreCase(ct.getHasPreviousChange())){
							OMVRet.add(t);
							ct=t;
							found=true;
							break;
						}
					}
					if (!found) ct.setHasPreviousChange(null);
				}
			}
		}
		//else if (OMVSet.size()>0) OMVRet.addAll(OMVSet); //IN CASE THE HISTORY IS BROKEN JUST RETURN ALL CHANGES FOUND. SHOULDNT HAPPEN!
		if (fromChange!=null){
			Iterator it = OMVRet.iterator();
			while (it.hasNext()){
				OMVChange t = (OMVChange)it.next();
				if (!t.getURI().equalsIgnoreCase(fromChange)){
					OMVRetFinal.add(t);
				}else break;		
			}
		}else return OMVRet;
		return OMVRetFinal;
	}
	/**
	 * Search Oyster2 registry to retrieve changes
	 * of specific kind for a specific ontology. 
	 * @param o is the specified ontology. 
	 * @param c is the kind of ontology change Use predefined classes of the 
	 * API i.e. new OMVEntityChange().
	 * @param fromChange is the starting point in the change history.
	 * If not specified (i.e. fromChange=null) this method returns the
	 * complete history
	 * @return The set of OMVChanges objects representing the
	 * ontology changes.
	 */
	public Set<OMVChange> getTrackedChanges(OMVOntology o, OMVChange c, Ontology registry, String fromChange){
		Set<OMVChange> replySet = new HashSet<OMVChange>();
		List<OMVChange> reply = getTrackedChanges(o, registry, fromChange);
		Iterator s = reply.iterator();
		while (s.hasNext()){
			OMVChange t = (OMVChange)s.next();
			try {
				if ( Class.forName ( c.getClass().getName() ).isInstance ( t ) ) {
					replySet.add(t);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return replySet;
	}
	/**
	 * Search Oyster2 registry to retrieve all available changes
	 * for a specific ontology without any order 
	 * @param o is the specified ontology 
	 * @return The set of OMVChanges objects representing the
	 * ontology changes.
	 */
	public Set<OMVChange> getTrackedChangesSet(OMVOntology o, Ontology registry){
		Ontology targetRegistry;
		if (registry==null)targetRegistry=localRegistry;
		else targetRegistry=registry;
		
		Set<OMVChange> OMVSet = new HashSet<OMVChange>();
		Map propertyMap = new HashMap();
		Collection hasChangeSet = new LinkedList();
		String tURN;
		
		if (o==null) return OMVSet;
		if (o.getURI()!=null){
			tURN=getOntologyID(o);
			if (tURN.indexOf("?")>0){
				tURN=tURN+";";
			}else{
				tURN=tURN+"?";
			}
			tURN=tURN+"changeSpecification";
			//GET ALL CHANGES FOR THE SPECIFIED ONTOLOGY
			OWLClass oConcept = KAON2Manager.factory().owlClass(Constants.CHANGEURI+Constants.changeSpecificationConcept);
			Individual oIndividual = KAON2Manager.factory().individual(tURN);
			try {
				if(targetRegistry.containsAxiom(KAON2Manager.factory().classMember(oConcept,oIndividual),true)){	
					ObjectProperty hasChange = KAON2Manager.factory().objectProperty(Constants.CHANGEURI+Constants.hasChange);
					propertyMap = oIndividual.getObjectPropertyValues(targetRegistry);
					hasChangeSet = (Collection)propertyMap.get(hasChange);
					Iterator i = hasChangeSet.iterator();
					while (i.hasNext()){
						Individual change = (Individual)i.next();
						//System.out.println("change: "+change.getURI().toString() +" description: "+change.getDescriptionsMemberOf(targetRegistry).iterator().next());
						OMVChange c=ProcessChangeIndividuals.processChangeIndividual(change, change.getDescriptionsMemberOf(targetRegistry).iterator().next().toString(), targetRegistry);
						OMVSet.add(c);			
					}
				}
			} catch (KAON2Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return OMVSet;
	}
	/**
	 * Search Oyster2 registry to retrieve all ontologies with
	 * change history. 
	 * @return The set of OMVOntology objects
	 */
	public Set<OMVOntology> getTrackedOntologies(){
		OWLClass oConcept = KAON2Manager.factory().owlClass(Constants.CHANGEURI+Constants.changeSpecificationConcept);
		ObjectProperty changeFromVersion = KAON2Manager.factory().objectProperty(Constants.CHANGEURI+Constants.changeFromVersion);
		Set<Individual> changeSpecs = null;
		Set<OMVOntology> OMVSet = new HashSet<OMVOntology>();
    	try{
    		changeSpecs = oConcept.getMemberIndividuals(localRegistry);
    		Iterator it = changeSpecs.iterator();
    		while (it.hasNext()){
    			Individual changeSpec= (Individual)it.next();
    			Individual onto = changeSpec.getObjectPropertyValue(localRegistry, changeFromVersion);
    			 
    			OMVOntology mainOntoReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(onto, "ontology",localRegistry);
    			OMVSet.add(mainOntoReply);
    		}
    	}catch(KAON2Exception e){
    		System.err.println(e.toString()+" in getTackedOntologies");
    	}
		return OMVSet;
	}
	/**
	 * Gets the set of atomic changes for a specific entity
	 * change
	 * @param c is the entity change for which for which we
	 * want to search atomic changes
	 * @return The set of atomic changes 
	 */

	public Set<OMVAtomicChange> getAtomicChanges(OMVEntityChange c){
		Set<OMVAtomicChange> replySet = new HashSet<OMVAtomicChange>();
		Map propertyMap = new HashMap();
		Collection hasChangeSet = new LinkedList();
		
		ObjectProperty consistsOfAtomicOperation = KAON2Manager.factory().objectProperty(Constants.CHANGEURI+Constants.consistsOfAtomicOperation);
		Individual oIndividual = KAON2Manager.factory().individual(c.getURI());
		OWLClass oConcept = KAON2Manager.factory().owlClass(Constants.CHANGEURI+getChangeConcept(c));
		try {
			if(localRegistry.containsAxiom(KAON2Manager.factory().classMember(oConcept,oIndividual),true)){
				propertyMap = oIndividual.getObjectPropertyValues(localRegistry);
				hasChangeSet = (Collection)propertyMap.get(consistsOfAtomicOperation);
				Iterator i = hasChangeSet.iterator();
				while (i.hasNext()){
					Individual atomic = (Individual)i.next();
					String atomicClass="";
					Set<Description> className= atomic.getDescriptionsMemberOf(localRegistry);
					if (className!=null && className.size()>0)
						atomicClass = className.iterator().next().toString();
					else return null;
					OMVAtomicChange tc = (OMVAtomicChange)ProcessChangeIndividuals.processChangeIndividual(atomic, atomicClass, localRegistry);
					replySet.add(tc);
				}
			}
		} catch (KAON2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return replySet;
	}
	/**
	 * Gets the URI of the last change registered
	 * for this ontology in the log
	 * @param o is the ontology from which we want to
	 * get the last change URI. 
	 * @return The string of the last change registered
	 */
	
	public String getLastChangeIdFromLog(OMVOntology o, Ontology registry){
		Ontology targetRegistry;
		if (registry==null)targetRegistry=localRegistry;
		else targetRegistry=registry;
		
		if (o==null || o.getURI()==null) return "";
		String tURN=getOntologyID(o);
		if (tURN.indexOf("?")>0){
			tURN=tURN+";";
		}else{
			tURN=tURN+"?";
		}
		tURN=tURN+"log";
		ObjectProperty hasLastChange = KAON2Manager.factory().objectProperty(Constants.CHANGEURI+Constants.hasLastChange);
		Individual lastChange = getPropertyValue(tURN,hasLastChange,Constants.CHANGEURI+Constants.LogConcept, targetRegistry);
		if (lastChange!=null) return lastChange.getURI();
		else return "";
	}
	/**
	 * Gets the URI of all registered changes
	 * @param o is the ontology from which we want to
	 * get all changes URI. 
	 * @return The set of strings of the changes URIs
	 */
	public Set<String> getChangesIds(OMVOntology o, Ontology registry){
		Ontology targetRegistry;
		if (registry==null)targetRegistry=localRegistry;
		else targetRegistry=registry;
		Set<String> reply = new HashSet<String>();
		
		String tURN=getOntologyID(o);
		if (tURN.indexOf("?")>0){
			tURN=tURN+";";
		}else{
			tURN=tURN+"?";
		}
		tURN=tURN+"changeSpecification";
		ObjectProperty hasChange = KAON2Manager.factory().objectProperty(Constants.CHANGEURI+Constants.hasChange);
		Set<Individual> changes = getPropertyValues(tURN,hasChange,Constants.CHANGEURI+Constants.changeSpecificationConcept, targetRegistry);
		Iterator it = changes.iterator();
		while (it.hasNext()){
			Individual t = (Individual)it.next();
			if (t!=null) reply.add(t.getURI());
		}
		return reply;
	}
	/**
	 * Compares if change history in registry is older than in registry2
	 * @param o is the ontology from which we want to compare change history
	 * @param registry1 is the registry that will be compared
	 * @param registry2 is the registry that will be used as reference
	 * @return true/false
	 */
	public boolean isHistoryOlder(OMVOntology o, Ontology registry1, Ontology registry2){
		if (registry1==null || registry2 ==null) return false;
		
		String lastChange1 = getLastChangeIdFromLog(o, registry1);
		String lastChange2 = getLastChangeIdFromLog(o, registry2);
		mOyster2.getLogger().info("comparing ..."+lastChange1+" with..."+lastChange2);
		
		if ((lastChange1==null || lastChange1.equalsIgnoreCase("")) && (lastChange2!=null && !lastChange2.equalsIgnoreCase(""))) return true;
		else if ((lastChange2==null || lastChange2.equalsIgnoreCase("")) && (lastChange1!=null && !lastChange1.equalsIgnoreCase(""))) return false;
		else if (lastChange1.equalsIgnoreCase(lastChange2)) return false;
		
		Set<String> changes2 = getChangesIds(o, registry2);
		Iterator it = changes2.iterator();
		while(it.hasNext()){
			String t = (String)it.next();
			if (t.equalsIgnoreCase(lastChange1)) return true;
		}
		return false;
		
	}
	
	//HISTORY && TRACKING METHODS
	private Set<Axiom> getAxioms(OMVAtomicChange c){
		Set<Axiom> replySet = new HashSet<Axiom>();
		ObjectProperty appliedAxiom = KAON2Manager.factory().objectProperty(Constants.CHANGEURI+Constants.appliedAxiom);
		Individual oIndividual = KAON2Manager.factory().individual(c.getURI());
		OWLClass oConcept = KAON2Manager.factory().owlClass(Constants.CHANGEURI+getChangeConcept(c));
		try {
			if(localRegistry.containsAxiom(KAON2Manager.factory().classMember(oConcept,oIndividual),true)){
				Individual axiomDel = oIndividual.getObjectPropertyValue(localRegistry, appliedAxiom);
				Axiom t = (Axiom)ProcessAxiomIndividuals.processAxiomIndividual(axiomDel, axiomDel.getDescriptionsMemberOf(localRegistry).iterator().next().toString(), localRegistry); //oReferences
				replySet.add(t);	
			}
		} catch (KAON2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return replySet;
	}
	
	
	private void updateLog(OMVOntology o, List properties, boolean first){
		String tURN="";
		Iterator it1 = properties.iterator();
		while(it1.hasNext()){
			OntologyProperty prop = (OntologyProperty)it1.next();
			if(prop.getPropertyName().equals(Constants.URI)){
				tURN = prop.getPropertyValue();
				break;
			}
		}
		OMVLog log = new OMVLog();
		log.setHasLastChange(tURN);
		log.setHasRelatedOntology(o);
		if (first) register(log);
		else replace(log); //really replace
	}
	
	private void updateChangeSpecification(OMVOntology o, List properties, boolean first){
		String tURN="";
		Iterator it1 = properties.iterator();
		while(it1.hasNext()){
			OntologyProperty prop = (OntologyProperty)it1.next();
			if(prop.getPropertyName().equals(Constants.URI)){
				tURN = prop.getPropertyValue();
				break;
			}
		}
		OMVChangeSpecification cs = new OMVChangeSpecification();
		cs.setChangeFromVersion(o);
		Date now = new Date();
		String sNow = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(now);
		cs.setLastTimestamp(sNow);
		cs.addHasChange(tURN);
		if (first) {
			cs.setInitialTimestamp(sNow);
			register(cs);
		}else replace(cs); //merge
	}
	
	private String generateTrace(OMVOntology o){
		String tURN="";
		Ontology localRegistry = mOyster2.getLocalHostOntology();
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
			
			if (tURN.indexOf("?")>0){
				tURN=tURN+";";
			}else{
				tURN=tURN+"?";
			}
			tURN=tURN+"log";
			//Get the log instance
			Individual lastChange=null;
			Individual tIndividual = KAON2Manager.factory().individual(tURN);
			OWLClass tempConcept = KAON2Manager.factory().owlClass(Constants.LogConcept);
			ObjectProperty tProperty = KAON2Manager.factory().objectProperty(Constants.CHANGEURI + Constants.hasLastChange);
			try {
				if(!localRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,tIndividual),true)){
					lastChange = tIndividual.getObjectPropertyValue(localRegistry, tProperty);
				}
			} catch (KAON2Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (lastChange!=null){
				return lastChange.getURI();
			}
		}
		return null;
	}
	
	//UTILS	
	private static String getChangeConcept(OMVChange a){
		if (a.getClass().getSimpleName()!=null && a.getClass().getSimpleName()!="") return a.getClass().getSimpleName();
		else return ""; 
	}
	
	private Individual getPropertyValue(String uri, ObjectProperty oP, String concept, Ontology registry){
		Ontology targetRegistry;
		if (registry==null)targetRegistry=localRegistry;
		else targetRegistry=registry;
		
		OWLClass oConcept = KAON2Manager.factory().owlClass(concept);
		Individual oIndividual = KAON2Manager.factory().individual(uri);
		try {
			if(targetRegistry.containsAxiom(KAON2Manager.factory().classMember(oConcept,oIndividual),true)){	
				Individual targetIndividual = oIndividual.getObjectPropertyValue(targetRegistry,oP);
				if (targetIndividual!=null) return targetIndividual;
				else return null;
			}
		} catch (KAON2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private void removeProperty(String URI, ObjectProperty oP, String Concept, String targetValue){
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
					if (indiv.getURI().equalsIgnoreCase(targetValue))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(oP,oIndividual,indiv),OntologyChangeEvent.ChangeType.REMOVE));
				}
			}
			if (changes.size()>0){ 
				localRegistry.applyChanges(changes);
				localRegistry.persist();
				try {
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
	
	private Set<Individual> getPropertyValues(String uri, ObjectProperty oP, String concept, Ontology registry){
		Ontology targetRegistry;
		if (registry==null)targetRegistry=localRegistry;
		else targetRegistry=registry;
		
		Map propertyMap = new HashMap();
		Collection hasSet = new LinkedList();
		Set<Individual> reply = new HashSet<Individual>();
		OWLClass oConcept = KAON2Manager.factory().owlClass(concept);
		Individual oIndividual = KAON2Manager.factory().individual(uri);
		try {
			if(targetRegistry.containsAxiom(KAON2Manager.factory().classMember(oConcept,oIndividual),true)){	
				propertyMap = oIndividual.getObjectPropertyValues(targetRegistry);
				hasSet = (Collection)propertyMap.get(oP);
				if (hasSet==null) return reply;
				Iterator i = hasSet.iterator();
				while (i.hasNext()){
					Individual indiv = (Individual)i.next();
					reply.add(indiv);
				}
			}
		} catch (KAON2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reply;
	}

	
	private String getOntologyID(OMVOntology o){
		String tURN="";
		if (o==null) return "";
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
	
	private String getCSID(OMVOntology o){
		String tURN=getOntologyID(o);
		if (tURN.indexOf("?")>0){
			tURN=tURN+";";
		}else{
			tURN=tURN+"?";
		}
		tURN=tURN+"changeSpecification";
		return tURN;
	}

}




/*
public Set<String> getRelatedEntities(String changeURI){
Set<String> reply=new HashSet<String>();
//try get change concept
Individual oIndividual = KAON2Manager.factory().individual(changeURI);
String conceptChange=null;
try {
	conceptChange = oIndividual.getDescriptionsMemberOf(localRegistry).iterator().next().toString();
} catch (KAON2Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	return reply;
}
ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.CHANGEURI + Constants.hasRelatedEntity);
Set<Individual> t = getPropertyValues(changeURI, ontologyObjectProperty,conceptChange);
Iterator it = t.iterator();
while (it.hasNext()){
	Individual indiv= (Individual)it.next();
	reply.add(indiv.getURI());
}
return reply;
}
*/

/*
 oConcept = KAON2Manager.factory().owlClass(Constants.CHANGEURI+Constants.LogConcept);
			oIndividual = KAON2Manager.factory().individual(lastURN);
			try {
				if(localRegistry.containsAxiom(KAON2Manager.factory().classMember(oConcept,oIndividual),true)){	
					ObjectProperty hasLastChange = KAON2Manager.factory().objectProperty(Constants.CHANGEURI+Constants.hasLastChange);
					Individual lc = oIndividual.getObjectPropertyValue(localRegistry, hasLastChange);
					lastChangeURI = lc.getURI();
				}
			} catch (KAON2Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
*/