package org.neontoolkit.registry.api.properties;



import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.neontoolkit.owlodm.api.Description;
import org.neontoolkit.owlodm.api.Description.DataAllValuesFrom;
import org.neontoolkit.owlodm.api.Description.DataExactCardinality;
import org.neontoolkit.owlodm.api.Description.DataHasValue;
import org.neontoolkit.owlodm.api.Description.DataMaxCardinality;
import org.neontoolkit.owlodm.api.Description.DataMinCardinality;
import org.neontoolkit.owlodm.api.Description.DataSomeValuesFrom;
import org.neontoolkit.owlodm.api.Description.OWLClass;
import org.neontoolkit.owlodm.api.Description.ObjectAllValuesFrom;
import org.neontoolkit.owlodm.api.Description.ObjectComplementOf;
import org.neontoolkit.owlodm.api.Description.ObjectExactCardinality;
import org.neontoolkit.owlodm.api.Description.ObjectExistsSelf;
import org.neontoolkit.owlodm.api.Description.ObjectHasValue;
import org.neontoolkit.owlodm.api.Description.ObjectIntersectionOf;
import org.neontoolkit.owlodm.api.Description.ObjectMaxCardinality;
import org.neontoolkit.owlodm.api.Description.ObjectMinCardinality;
import org.neontoolkit.owlodm.api.Description.ObjectOneOf;
import org.neontoolkit.owlodm.api.Description.ObjectSomeValuesFrom;
import org.neontoolkit.owlodm.api.Description.ObjectUnionOf;
import org.neontoolkit.owlodm.api.OWLEntity.DataProperty;
import org.neontoolkit.owlodm.api.OWLEntity.Datatype;
import org.neontoolkit.owlodm.api.OWLEntity.Individual;
import org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty;
import org.neontoolkit.registry.oyster2.Constants;
import org.neontoolkit.registry.oyster2.ImportOntology;
import org.neontoolkit.registry.oyster2.OntologyProperty;
import org.neontoolkit.registry.util.GUID;

/**
 * The class DescriptionProperties provides the methods to
 * retrieve the properties from Description objects 
 * representing the object of the registry 
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class DescriptionProperties{
	static private ImportOntology IOntology= new ImportOntology();
	//static private String ontologyChangedURI="";
	
	@SuppressWarnings("unchecked")
	static public LinkedList getDescriptionProperties(Description m){
		//Ignore now axiom annotation
		
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		List tList = new LinkedList();
		
		if (m instanceof DataAllValuesFrom){
			DataAllValuesFrom t = (DataAllValuesFrom)m;
			if (t.getDataProperties().size()>0) {
				Iterator it = t.getDataProperties().iterator();
				while(it.hasNext()){
					DataProperty ot = (DataProperty)it.next();		
					tList.clear();
					tList=OWLEntityProperties.getOWLEntityProperties(ot);
					String tURN="";
					OntologyProperty tProp;
					if (ot.getURI()!=null){
						tURN=ot.getURI();
					}else{
						tURN="OWLEntity="+GUID.getGUID().toString();
						tProp = new OntologyProperty(Constants.URI, tURN);
						tList.add(tProp);
					}
					String concept=OWLEntityProperties.getOWLEntityConcept(ot);
					tProp = new OntologyProperty(Constants.name, concept);
					tList.add(tProp);
					IOntology.addConceptToRegistry(1,tList,80, null); 
					OntologyProperty prop = new OntologyProperty(Constants.dataProperties, tURN);
					tProperties.addFirst(prop);
				}
			}
			if (t.getDataRange()!=null) {
				Datatype ot = t.getDataRange();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.dataRange, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof DataExactCardinality){
			DataExactCardinality t = (DataExactCardinality)m;
			if (t.getDataProperty()!=null) {
				DataProperty ot = t.getDataProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.dataProperty, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getDataRange()!=null) {
				Datatype ot = t.getDataRange();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.dataRange, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getCardinality()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.cardinality, t.getCardinality().toString());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof DataHasValue){
			DataHasValue t = (DataHasValue)m;
			if (t.getDataProperty()!=null) {
				DataProperty ot = t.getDataProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.dataProperty, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getConstant()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.constant, t.getConstant().toString());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof DataMaxCardinality){
			DataMaxCardinality t = (DataMaxCardinality)m;
			if (t.getDataProperty()!=null) {
				DataProperty ot = t.getDataProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.dataProperty, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getDataRange()!=null) {
				Datatype ot = t.getDataRange();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.dataRange, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getCardinality()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.cardinality, t.getCardinality().toString());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof DataMinCardinality){
			DataMinCardinality t = (DataMinCardinality)m;
			if (t.getDataProperty()!=null) {
				DataProperty ot = t.getDataProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.dataProperty, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getDataRange()!=null) {
				Datatype ot = t.getDataRange();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.dataRange, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getCardinality()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.cardinality, t.getCardinality().toString());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof DataSomeValuesFrom){
			DataSomeValuesFrom t = (DataSomeValuesFrom)m;
			if (t.getDataProperties().size()>0) {
				Iterator it = t.getDataProperties().iterator();
				while(it.hasNext()){
					DataProperty ot = (DataProperty)it.next();		
					tList.clear();
					tList=OWLEntityProperties.getOWLEntityProperties(ot);
					String tURN="";
					OntologyProperty tProp;
					if (ot.getURI()!=null){
						tURN=ot.getURI();
					}else{
						tURN="OWLEntity="+GUID.getGUID().toString();
						tProp = new OntologyProperty(Constants.URI, tURN);
						tList.add(tProp);
					}
					String concept=OWLEntityProperties.getOWLEntityConcept(ot);
					tProp = new OntologyProperty(Constants.name, concept);
					tList.add(tProp);
					IOntology.addConceptToRegistry(1,tList,80, null); 
					OntologyProperty prop = new OntologyProperty(Constants.dataProperties, tURN);
					tProperties.addFirst(prop);
				}
			}
			if (t.getDataRange()!=null) {
				Datatype ot = t.getDataRange();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.dataRange, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof ObjectAllValuesFrom){
			ObjectAllValuesFrom t = (ObjectAllValuesFrom)m;
			if (t.getOWLClass()!=null) {
				Description ot = t.getOWLClass();
				tList.clear();
				tList=DescriptionProperties.getDescriptionProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN=DescriptionProperties.getTURN(tList);
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=DescriptionProperties.getDescriptionConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,70, null); 
				OntologyProperty prop = new OntologyProperty(Constants.OWLClass, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getObjectProperty()!=null) {
				ObjectProperty ot = t.getObjectProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof ObjectComplementOf){
			ObjectComplementOf t = (ObjectComplementOf)m;
			if (t.getOWLClass()!=null) {
				Description ot = t.getOWLClass();
				tList.clear();
				tList=DescriptionProperties.getDescriptionProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN=DescriptionProperties.getTURN(tList);
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=DescriptionProperties.getDescriptionConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,70, null);  
				OntologyProperty prop = new OntologyProperty(Constants.OWLClass, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof ObjectExactCardinality){
			ObjectExactCardinality t = (ObjectExactCardinality)m;
			if (t.getOWLClass()!=null) {
				Description ot = t.getOWLClass();
				tList.clear();
				tList=DescriptionProperties.getDescriptionProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN=DescriptionProperties.getTURN(tList);
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=DescriptionProperties.getDescriptionConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,70, null);  
				OntologyProperty prop = new OntologyProperty(Constants.OWLClass, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getObjectProperty()!=null) {
				ObjectProperty ot = t.getObjectProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getCardinality()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.cardinality, t.getCardinality().toString());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof ObjectExistsSelf){
			ObjectExistsSelf t = (ObjectExistsSelf)m;
			if (t.getObjectProperty()!=null) {
				ObjectProperty ot = t.getObjectProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof ObjectHasValue){
			ObjectHasValue t = (ObjectHasValue)m;
			if (t.getObjectProperty()!=null) {
				ObjectProperty ot = t.getObjectProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getValue()!=null) {
				Individual ot = t.getValue();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.value, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof ObjectIntersectionOf){
			ObjectIntersectionOf t = (ObjectIntersectionOf)m;
			if (t.getOWLClasses().size()>0) {
				Iterator it = t.getOWLClasses().iterator();
				while(it.hasNext()){
					Description ot = (Description)it.next();
					tList.clear();
					tList=DescriptionProperties.getDescriptionProperties(ot);
					String tURN="";
					OntologyProperty tProp;
					if (ot.getURI()!=null){
						tURN=ot.getURI();
					}else{
						tURN=DescriptionProperties.getTURN(tList);
						tProp = new OntologyProperty(Constants.URI, tURN);
						tList.add(tProp);
					}
					String concept=DescriptionProperties.getDescriptionConcept(ot);
					tProp = new OntologyProperty(Constants.name, concept);
					tList.add(tProp);
					IOntology.addConceptToRegistry(1,tList,70, null); 
					OntologyProperty prop = new OntologyProperty(Constants.OWLClasses, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m instanceof ObjectMaxCardinality){
			ObjectMaxCardinality t = (ObjectMaxCardinality)m;
			if (t.getOWLClass()!=null) {
				Description ot = t.getOWLClass();
				tList.clear();
				tList=DescriptionProperties.getDescriptionProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN=DescriptionProperties.getTURN(tList);
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=DescriptionProperties.getDescriptionConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,70, null); 
				OntologyProperty prop = new OntologyProperty(Constants.OWLClass, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getObjectProperty()!=null) {
				ObjectProperty ot = t.getObjectProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getCardinality()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.cardinality, t.getCardinality().toString());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof ObjectMinCardinality){
			ObjectMinCardinality t = (ObjectMinCardinality)m;
			if (t.getOWLClass()!=null) {
				Description ot = t.getOWLClass();
				tList.clear();
				tList=DescriptionProperties.getDescriptionProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN=DescriptionProperties.getTURN(tList);
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=DescriptionProperties.getDescriptionConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,70, null); 
				OntologyProperty prop = new OntologyProperty(Constants.OWLClass, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getObjectProperty()!=null) {
				ObjectProperty ot = t.getObjectProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getCardinality()!=null) {
				OntologyProperty prop = new OntologyProperty(Constants.cardinality, t.getCardinality().toString());
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof ObjectOneOf){
			ObjectOneOf t = (ObjectOneOf)m;
			if (t.getIndividuals().size()>0) {
				Iterator it = t.getIndividuals().iterator();
				while(it.hasNext()){
					Individual ot = (Individual)it.next();		
					tList.clear();
					tList=OWLEntityProperties.getOWLEntityProperties(ot);
					String tURN="";
					OntologyProperty tProp;
					if (ot.getURI()!=null){
						tURN=ot.getURI();
					}else{
						tURN="OWLEntity="+GUID.getGUID().toString();
						tProp = new OntologyProperty(Constants.URI, tURN);
						tList.add(tProp);
					}
					String concept=OWLEntityProperties.getOWLEntityConcept(ot);
					tProp = new OntologyProperty(Constants.name, concept);
					tList.add(tProp);
					IOntology.addConceptToRegistry(1,tList,80, null); 
					OntologyProperty prop = new OntologyProperty(Constants.individuals, tURN);
					tProperties.addFirst(prop);	
				}
			}
		}
		if (m instanceof ObjectSomeValuesFrom){
			ObjectSomeValuesFrom t = (ObjectSomeValuesFrom)m;
			if (t.getOWLClass()!=null) {
				Description ot = t.getOWLClass();
				tList.clear();
				tList=DescriptionProperties.getDescriptionProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN=DescriptionProperties.getTURN(tList);
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=DescriptionProperties.getDescriptionConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,70, null); 
				OntologyProperty prop = new OntologyProperty(Constants.OWLClass, tURN);
				tProperties.addFirst(prop);
			}
			if (t.getObjectProperty()!=null) {
				ObjectProperty ot = t.getObjectProperty();		
				tList.clear();
				tList=OWLEntityProperties.getOWLEntityProperties(ot);
				String tURN="";
				OntologyProperty tProp;
				if (ot.getURI()!=null){
					tURN=ot.getURI();
				}else{
					tURN="OWLEntity="+GUID.getGUID().toString();
					tProp = new OntologyProperty(Constants.URI, tURN);
					tList.add(tProp);
				}
				String concept=OWLEntityProperties.getOWLEntityConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,80, null); 
				OntologyProperty prop = new OntologyProperty(Constants.objectProperty, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof ObjectUnionOf){
			ObjectUnionOf t = (ObjectUnionOf)m;
			if (t.getOWLClasses().size()>0) {
				Iterator it = t.getOWLClasses().iterator();
				while(it.hasNext()){
					Description ot = (Description)it.next();
					tList.clear();
					tList=DescriptionProperties.getDescriptionProperties(ot);
					String tURN="";
					OntologyProperty tProp;
					if (ot.getURI()!=null){
						tURN=ot.getURI();
					}else{
						//tURN="description="+GUID.getGUID().toString();
						tURN=DescriptionProperties.getTURN(tList);
						tProp = new OntologyProperty(Constants.URI, tURN);
						tList.add(tProp);
					}
					String concept=DescriptionProperties.getDescriptionConcept(ot);
					tProp = new OntologyProperty(Constants.name, concept);
					tList.add(tProp);
					IOntology.addConceptToRegistry(1,tList,70, null); 
					OntologyProperty prop = new OntologyProperty(Constants.OWLClasses, tURN);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m instanceof OWLClass){
			
		}
		if (m.getURI()!=null) {
			OntologyProperty tPropURN = new OntologyProperty(Constants.URI, m.getURI());
			tProperties.add(tPropURN);
		}
		return tProperties;
	}
	public static String getDescriptionConcept(Description a){
		if (a.getClass().getSimpleName()!=null && a.getClass().getSimpleName()!="") return a.getClass().getSimpleName();
		else return "";
	}
	
	public static String getTURN(List tList){
		OntologyProperty tProp;
		String tURN="http://localhost/description?v0=";
		Iterator itList = tList.iterator();
		int i=1;
		while (itList.hasNext()){
			tProp = (OntologyProperty)itList.next();
			tURN=tURN+tProp.getPropertyValue()+";v"+i+"=";
			i++;
		}
		return tURN.substring(0, tURN.length()-4).replace("#", "_");
	}
						
}

//if (ontologyChangedURI.indexOf("?")>0){
//	tURN=ontologyChangedURI+";";
//}else if (ontologyChangedURI.length()>0){
//	tURN=ontologyChangedURI+"?";
//}
//tURN=tURN+"description="+GUID.getGUID().toString();