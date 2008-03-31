package org.neon_toolkit.registry.api.properties;


import java.util.LinkedList;
import java.util.List;
import org.neon_toolkit.omv.api.core.OMVOntology;
import org.neon_toolkit.omv.api.core.OMVPerson;
import org.neon_toolkit.registry.oyster2.Constants;
import org.neon_toolkit.registry.oyster2.ImportOntology;
import org.neon_toolkit.registry.oyster2.OntologyProperty;
import org.neon_toolkit.workflow.api.Action;
import org.neon_toolkit.workflow.api.Action.EntityAction;
import org.neon_toolkit.workflow.api.Action.OntologyAction;
import org.neon_toolkit.workflow.api.Action.OntologyAction.Publish;

/**
 * The class WorkflowProperties provides the methods to
 * retrieve the properties from workflow objects 
 * representing the object of the registry 
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class WorkflowProperties{
	static private ImportOntology IOntology= new ImportOntology();
	static private String ontologyChangedURI="";
	
	static public LinkedList getActionProperties(Action m){
		//Ignore now axiom annotation
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		List tList = new LinkedList();
		
		if (m.getTimestamp()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.timestamp, m.getTimestamp());
			tProperties.addFirst(prop);
		}
		if (m.getPerformedBy()!=null) {
			String tURN;
			Object t = m.getPerformedBy();
			if (t instanceof OMVPerson){
				OMVPerson per = (OMVPerson)t;
				if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
					tURN=per.getFirstName()+per.getLastName();
					tList.clear();
					tList=OMVProperties.getPropertiesPerson(per);
					IOntology.addConceptToRegistry(1,tList,0, null);
					OntologyProperty prop = new OntologyProperty(Constants.performedBy, tURN);
					tProperties.addFirst(prop);
				}
			}
			
		}
		if (m instanceof EntityAction){
			EntityAction t = (EntityAction)m;
			if (t.getRelatedChange()!=null){
				OntologyProperty prop = new OntologyProperty(Constants.relatedChange, t.getRelatedChange());
				tProperties.addFirst(prop);
			}
			if (t.getURI()!=null){
				OntologyProperty tPropURN = new OntologyProperty(Constants.URI, t.getURI());
				tProperties.add(tPropURN);
			}else{
				String tURN="";
				if (t.getRelatedChange()!=null)	tURN=t.getRelatedChange()+";action="+t.getClass().getSimpleName();
				else tURN="noChangeAssociated";
				OntologyProperty tPropURN = new OntologyProperty(Constants.URI, tURN);
				tProperties.add(tPropURN);
			}
		}
		else if (m instanceof OntologyAction){
			OntologyAction t = (OntologyAction)m;
			ontologyChangedURI="";
			if (t.getRelatedOntology()!=null){
				String tURN;
				OMVOntology otemp = t.getRelatedOntology();
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
					ontologyChangedURI=tURN;
					//Pure Register Ontology i.e. what=1
					tList.clear();
					tList=OMVProperties.getProperties(otemp);
					IOntology.addImportOntologyToRegistry(tList,1, null);
					//add property
					OntologyProperty prop = new OntologyProperty(Constants.relatedOntology, ontologyChangedURI);
					tProperties.addFirst(prop);
				}
			}
			if (t.getURI()!=null){
				OntologyProperty tPropURN = new OntologyProperty(Constants.URI, t.getURI());
				tProperties.add(tPropURN);
			}else{
				String tURN="";
				if (t.getRelatedOntology()!=null) tURN=ontologyChangedURI+";action="+t.getClass().getSimpleName();
				else tURN="noOntologyAssociated";
				OntologyProperty tPropURN = new OntologyProperty(Constants.URI, tURN);
				tProperties.add(tPropURN);
			}
			if (m instanceof Publish){
				Publish p = (Publish)t;
				ontologyChangedURI="";
				if (p.getFromPublicVersion()!=null){
					String tURN;
					OMVOntology otemp = p.getFromPublicVersion();
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
						ontologyChangedURI=tURN;
						//Pure Register Ontology i.e. what=1
						tList.clear();
						tList=OMVProperties.getProperties(otemp);
						IOntology.addImportOntologyToRegistry(tList,1, null);
						//add property
						OntologyProperty prop = new OntologyProperty(Constants.fromPublicVersion, ontologyChangedURI);
						tProperties.addFirst(prop);
					}
				}
				ontologyChangedURI="";
				if (p.getToPublicVersion()!=null){
					String tURN;
					OMVOntology otemp = p.getToPublicVersion();
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
						ontologyChangedURI=tURN;
						//Pure Register Ontology i.e. what=1
						tList.clear();
						tList=OMVProperties.getProperties(otemp);
						IOntology.addImportOntologyToRegistry(tList,1, null);
						//add property
						OntologyProperty prop = new OntologyProperty(Constants.toPublicVersion, ontologyChangedURI);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		return tProperties;
	}
	
						
}