package org.neontoolkit.registry.api.individuals;

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
import org.neontoolkit.owlodm.api.OWLEntity.Datatype;
import org.neontoolkit.registry.oyster2.Constants;


/**
 * The class ProcessDescriptionIndividuals provides the methods to
 * create Description objects  
 * representing the result of a query 
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class ProcessDescriptionIndividuals{
	static LinkedList<Individual> onProcess = new LinkedList<Individual>();
   
    static public Description processDescriptionIndividual(Individual descriptionIndividual, String whichClassFull, Ontology ontologySearch){
     String whichClass = Namespaces.guessLocalName(whichClassFull);
     Description reply=new Description();
    
	 if (!onProcess.contains(descriptionIndividual)){
	  onProcess.add(descriptionIndividual);  
	  try{
		Map dataPropertyMap = descriptionIndividual.getDataPropertyValues(ontologySearch);
		Map objectPropertyMap = descriptionIndividual.getObjectPropertyValues(ontologySearch);
		if ((dataPropertyMap.size()+objectPropertyMap.size())>0){
		
			
			if (whichClass.equalsIgnoreCase("DataAllValuesFrom")) reply = new DataAllValuesFrom();
			else if (whichClass.equalsIgnoreCase("DataExactCardinality")) reply = new DataExactCardinality();
			else if (whichClass.equalsIgnoreCase("DataHasValue")) reply = new DataHasValue();
			else if (whichClass.equalsIgnoreCase("DataMaxCardinality")) reply = new DataMaxCardinality();
			else if (whichClass.equalsIgnoreCase("DataMinCardinality")) reply = new DataMinCardinality();
			else if (whichClass.equalsIgnoreCase("DataSomeValuesFrom")) reply = new DataSomeValuesFrom();
			
			else if (whichClass.equalsIgnoreCase("ObjectAllValuesFrom")) reply = new ObjectAllValuesFrom();
			else if (whichClass.equalsIgnoreCase("ObjectComplementOf")) reply = new ObjectComplementOf();
			else if (whichClass.equalsIgnoreCase("ObjectExactCardinality")) reply = new ObjectExactCardinality();
			else if (whichClass.equalsIgnoreCase("ObjectExistsSelf")) reply = new ObjectExistsSelf();
			else if (whichClass.equalsIgnoreCase("ObjectHasValue")) reply = new ObjectHasValue();
			else if (whichClass.equalsIgnoreCase("ObjectIntersectionOf")) reply = new ObjectIntersectionOf();
			else if (whichClass.equalsIgnoreCase("ObjectMaxCardinality")) reply = new ObjectMaxCardinality();
			else if (whichClass.equalsIgnoreCase("ObjectMinCardinality")) reply = new ObjectMinCardinality();
			else if (whichClass.equalsIgnoreCase("ObjectOneOf")) reply = new ObjectOneOf();
			else if (whichClass.equalsIgnoreCase("ObjectSomeValuesFrom")) reply = new ObjectSomeValuesFrom();
			else if (whichClass.equalsIgnoreCase("ObjectUnionOf")) reply = new ObjectUnionOf();
			else if (whichClass.equalsIgnoreCase("OWLClass")) {reply=null;reply = new OWLClass(descriptionIndividual.getURI());}
						
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
					String	propertyValue = org.neontoolkit.registry.util.Utilities.getString(propertyObject);
					//System.out.println("property: "+property.getURI()+" VALUE= "+propertyValue);
					
					if (property.getURI().equalsIgnoreCase(Constants.OWLODMURI+Constants.URI)) reply.append(createDescription(property.getURI(),propertyValue, ontologySearch));					
					else if (whichClass.equalsIgnoreCase("DataAllValuesFrom")) ((DataAllValuesFrom)reply).append(createDataAllValuesFrom(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("DataExactCardinality")) ((DataExactCardinality)reply).append(createDataExactCardinality(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("DataHasValue")) ((DataHasValue)reply).append(createDataHasValue(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("DataMaxCardinality")) ((DataMaxCardinality)reply).append(createDataMaxCardinality(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("DataMinCardinality")) ((DataMinCardinality)reply).append(createDataMinCardinality(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("DataSomeValuesFrom")) ((DataSomeValuesFrom)reply).append(createDataSomeValuesFrom(property.getURI(),propertyValue, ontologySearch));
					
					else if (whichClass.equalsIgnoreCase("ObjectAllValuesFrom")) ((ObjectAllValuesFrom)reply).append(createObjectAllValuesFrom(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectComplementOf")) ((ObjectComplementOf)reply).append(createObjectComplementOf(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectExactCardinality")) ((ObjectExactCardinality)reply).append(createObjectExactCardinality(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectExistsSelf")) ((ObjectExistsSelf)reply).append(createObjectExistsSelf(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectHasValue")) ((ObjectHasValue)reply).append(createObjectHasValue(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectIntersectionOf")) ((ObjectIntersectionOf)reply).append(createObjectIntersectionOf(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectMaxCardinality")) ((ObjectMaxCardinality)reply).append(createObjectMaxCardinality(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectMinCardinality")) ((ObjectMinCardinality)reply).append(createObjectMinCardinality(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectOneOf")) ((ObjectOneOf)reply).append(createObjectOneOf(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectSomeValuesFrom")) ((ObjectSomeValuesFrom)reply).append(createObjectSomeValuesFrom(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectUnionOf")) ((ObjectUnionOf)reply).append(createObjectUnionOf(property.getURI(),propertyValue, ontologySearch));
					//else if (whichClass.equalsIgnoreCase("OWLClass")) ((OWLClass)reply).append(createOWLClass(property.getURI(),propertyValue, ontologySearch));
				}
				
				//System.out.println("The property "+property.getURI()+" has value: "+propertyValue);
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
					
					if (whichClass.equalsIgnoreCase("DataAllValuesFrom")) ((DataAllValuesFrom)reply).append(createDataAllValuesFrom(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("DataExactCardinality")) ((DataExactCardinality)reply).append(createDataExactCardinality(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("DataHasValue")) ((DataHasValue)reply).append(createDataHasValue(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("DataMaxCardinality")) ((DataMaxCardinality)reply).append(createDataMaxCardinality(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("DataMinCardinality")) ((DataMinCardinality)reply).append(createDataMinCardinality(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("DataSomeValuesFrom")) ((DataSomeValuesFrom)reply).append(createDataSomeValuesFrom(property.getURI(),propertyValue.getURI(), ontologySearch));
					
					else if (whichClass.equalsIgnoreCase("ObjectAllValuesFrom")) ((ObjectAllValuesFrom)reply).append(createObjectAllValuesFrom(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectComplementOf")) ((ObjectComplementOf)reply).append(createObjectComplementOf(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectExactCardinality")) ((ObjectExactCardinality)reply).append(createObjectExactCardinality(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectExistsSelf")) ((ObjectExistsSelf)reply).append(createObjectExistsSelf(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectHasValue")) ((ObjectHasValue)reply).append(createObjectHasValue(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectIntersectionOf")) ((ObjectIntersectionOf)reply).append(createObjectIntersectionOf(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectMaxCardinality")) ((ObjectMaxCardinality)reply).append(createObjectMaxCardinality(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectMinCardinality")) ((ObjectMinCardinality)reply).append(createObjectMinCardinality(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectOneOf")) ((ObjectOneOf)reply).append(createObjectOneOf(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectSomeValuesFrom")) ((ObjectSomeValuesFrom)reply).append(createObjectSomeValuesFrom(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectUnionOf")) ((ObjectUnionOf)reply).append(createObjectUnionOf(property.getURI(),propertyValue.getURI(), ontologySearch));
					//else if (whichClass.equalsIgnoreCase("OWLClass")) ((OWLClass)reply).append(createOWLClass(property.getURI(),propertyValue.getURI(), ontologySearch));
									
					//System.out.println("The property "+property.getURI()+" has value: "+propertyValue);	
				}	
			}
		}
	  }catch(Exception e){
			e.printStackTrace();
	  }
	  onProcess.remove(descriptionIndividual);
	  if (reply instanceof DataAllValuesFrom || reply instanceof DataExactCardinality || reply instanceof DataHasValue || reply instanceof DataMaxCardinality || reply instanceof DataMinCardinality || reply instanceof DataSomeValuesFrom ||
		reply instanceof ObjectAllValuesFrom || reply instanceof ObjectComplementOf || reply instanceof ObjectExactCardinality || reply instanceof ObjectExistsSelf || reply instanceof ObjectHasValue || reply instanceof ObjectIntersectionOf || 
		reply instanceof ObjectMaxCardinality || reply instanceof ObjectMinCardinality || reply instanceof ObjectOneOf || reply instanceof ObjectSomeValuesFrom || reply instanceof ObjectUnionOf || reply instanceof OWLClass)
	  return reply;
	  else return null;
	  
	 }
	 else{
		reply.setURI(descriptionIndividual.getURI());
		return reply;
	 }
	}
	

	private static ObjectUnionOf createObjectUnionOf(String uri, String propertyValue, Ontology ontologySearch) {
		ObjectUnionOf descriptionReply = new ObjectUnionOf();
		try{
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.OWLClasses)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				Description replyTemp=(Description)processDescriptionIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.addOWLClasses(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return descriptionReply;
	}

	private static ObjectSomeValuesFrom createObjectSomeValuesFrom(String uri, String propertyValue, Ontology ontologySearch) {
		ObjectSomeValuesFrom descriptionReply = new ObjectSomeValuesFrom();
		try{
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.OWLClass)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				Description replyTemp=(Description)processDescriptionIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setOWLClass(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.objectProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty replyTemp=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setObjectProperty(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		return descriptionReply;
	}

	private static ObjectOneOf createObjectOneOf(String uri, String propertyValue, Ontology ontologySearch) {
		ObjectOneOf descriptionReply = new ObjectOneOf();
		try{
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.individuals)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				org.neontoolkit.owlodm.api.OWLEntity.Individual replyTemp=(org.neontoolkit.owlodm.api.OWLEntity.Individual)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.addIndividuals(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
		return descriptionReply;
	}

	private static ObjectMinCardinality createObjectMinCardinality(String uri, String propertyValue, Ontology ontologySearch) {
		ObjectMinCardinality descriptionReply = new ObjectMinCardinality();
		try{
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.OWLClass)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				Description replyTemp=(Description)processDescriptionIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setOWLClass(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.objectProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty replyTemp=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setObjectProperty(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.cardinality)) {descriptionReply.setCardinality(new Integer(propertyValue.substring(1, propertyValue.indexOf("\"", 2))));return descriptionReply;}
		}catch(Exception e){
			e.printStackTrace();
		}
		return descriptionReply;

	}

	private static ObjectMaxCardinality createObjectMaxCardinality(String uri, String propertyValue, Ontology ontologySearch) {
		ObjectMaxCardinality descriptionReply = new ObjectMaxCardinality();
		try{
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.OWLClass)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				Description replyTemp=(Description)processDescriptionIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setOWLClass(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.objectProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty replyTemp=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setObjectProperty(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.cardinality)) {descriptionReply.setCardinality(new Integer(propertyValue.substring(1, propertyValue.indexOf("\"", 2))));return descriptionReply;}
		}catch(Exception e){
			e.printStackTrace();
		}
		return descriptionReply;

	}

	private static ObjectIntersectionOf createObjectIntersectionOf(String uri, String propertyValue, Ontology ontologySearch) {
		ObjectIntersectionOf descriptionReply = new ObjectIntersectionOf();
		try{
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.OWLClasses)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				Description replyTemp=(Description)processDescriptionIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.addOWLClasses(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return descriptionReply;
	}

	private static ObjectHasValue createObjectHasValue(String uri, String propertyValue, Ontology ontologySearch) {
		ObjectHasValue descriptionReply = new ObjectHasValue();
		try{
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.objectProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty replyTemp=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setObjectProperty(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.value)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				org.neontoolkit.owlodm.api.OWLEntity.Individual replyTemp=(org.neontoolkit.owlodm.api.OWLEntity.Individual)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setValue(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return descriptionReply;
	}

	private static ObjectExistsSelf createObjectExistsSelf(String uri, String propertyValue, Ontology ontologySearch) {
		ObjectExistsSelf descriptionReply = new ObjectExistsSelf();
		try{
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.objectProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty replyTemp=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setObjectProperty(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return descriptionReply;
	}

	private static ObjectExactCardinality createObjectExactCardinality(String uri, String propertyValue, Ontology ontologySearch) {
		ObjectExactCardinality descriptionReply = new ObjectExactCardinality();
		try{
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.OWLClass)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				Description replyTemp=(Description)processDescriptionIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setOWLClass(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.objectProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty replyTemp=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setObjectProperty(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.cardinality)) {descriptionReply.setCardinality(new Integer(propertyValue.substring(1, propertyValue.indexOf("\"", 2))));return descriptionReply;}
		}catch(Exception e){
			e.printStackTrace();
		}
		return descriptionReply;
	}

	private static ObjectComplementOf createObjectComplementOf(String uri, String propertyValue, Ontology ontologySearch) {
		ObjectComplementOf descriptionReply = new ObjectComplementOf();
		try{
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.OWLClass)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				Description replyTemp=(Description)processDescriptionIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setOWLClass(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return descriptionReply;
	}

	private static ObjectAllValuesFrom createObjectAllValuesFrom(String uri, String propertyValue, Ontology ontologySearch) {
		ObjectAllValuesFrom descriptionReply = new ObjectAllValuesFrom();
		try{
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.OWLClass)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				Description replyTemp=(Description)processDescriptionIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setOWLClass(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.objectProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty replyTemp=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setObjectProperty(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		return descriptionReply;
	}
	
	private static DataSomeValuesFrom createDataSomeValuesFrom(String uri, String propertyValue, Ontology ontologySearch) {
		DataSomeValuesFrom descriptionReply = new DataSomeValuesFrom();
		try{
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.dataProperties)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				org.neontoolkit.owlodm.api.OWLEntity.DataProperty replyTemp=(org.neontoolkit.owlodm.api.OWLEntity.DataProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.addDataProperties(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.dataRange)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				Datatype replyTemp=(Datatype)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setDataRange(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return descriptionReply;
	}

	private static DataMinCardinality createDataMinCardinality(String uri, String propertyValue, Ontology ontologySearch) {
		DataMinCardinality descriptionReply = new DataMinCardinality();
		try{
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.dataProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				org.neontoolkit.owlodm.api.OWLEntity.DataProperty replyTemp=(org.neontoolkit.owlodm.api.OWLEntity.DataProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setDataProperty(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.dataRange)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				Datatype replyTemp=(Datatype)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setDataRange(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.cardinality)) {descriptionReply.setCardinality(new Integer(propertyValue.substring(1, propertyValue.indexOf("\"", 2))));return descriptionReply;}
		}catch(Exception e){
			e.printStackTrace();
		}
		return descriptionReply;
	}

	private static DataMaxCardinality createDataMaxCardinality(String uri, String propertyValue, Ontology ontologySearch) {
		DataMaxCardinality descriptionReply = new DataMaxCardinality();
		try{
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.dataProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				org.neontoolkit.owlodm.api.OWLEntity.DataProperty replyTemp=(org.neontoolkit.owlodm.api.OWLEntity.DataProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setDataProperty(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.dataRange)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				Datatype replyTemp=(Datatype)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setDataRange(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.cardinality)) {descriptionReply.setCardinality(new Integer(propertyValue.substring(1, propertyValue.indexOf("\"", 2))));return descriptionReply;}
		}catch(Exception e){
			e.printStackTrace();
		}
		return descriptionReply;
	}

	private static DataHasValue createDataHasValue(String uri, String propertyValue, Ontology ontologySearch) {
		DataHasValue descriptionReply = new DataHasValue();
		try{
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.dataProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				org.neontoolkit.owlodm.api.OWLEntity.DataProperty replyTemp=(org.neontoolkit.owlodm.api.OWLEntity.DataProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setDataProperty(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.constant)) {descriptionReply.setConstant(propertyValue);return descriptionReply;}
		}catch(Exception e){
			e.printStackTrace();
		}
		return descriptionReply;
	}

	private static DataExactCardinality createDataExactCardinality(String uri, String propertyValue, Ontology ontologySearch) {
		DataExactCardinality descriptionReply = new DataExactCardinality();
		try{
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.dataProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				org.neontoolkit.owlodm.api.OWLEntity.DataProperty replyTemp=(org.neontoolkit.owlodm.api.OWLEntity.DataProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setDataProperty(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.dataRange)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				Datatype replyTemp=(Datatype)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setDataRange(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.cardinality)) {descriptionReply.setCardinality(new Integer(propertyValue.substring(1, propertyValue.indexOf("\"", 2))));return descriptionReply;}
		}catch(Exception e){
			e.printStackTrace();
		}
		return descriptionReply;
	}

	private static DataAllValuesFrom createDataAllValuesFrom(String uri, String propertyValue, Ontology ontologySearch) {
		DataAllValuesFrom descriptionReply = new DataAllValuesFrom();
		try{
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.dataProperties)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				org.neontoolkit.owlodm.api.OWLEntity.DataProperty replyTemp=(org.neontoolkit.owlodm.api.OWLEntity.DataProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.addDataProperties(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.dataRange)) {
				Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
				Datatype replyTemp=(Datatype)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				descriptionReply.setDataRange(replyTemp);
				replyTemp = null;
				return descriptionReply;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return descriptionReply;
	}

	private static Description createDescription(String uri, String propertyValue, Ontology ontologySearch) {
		// TODO Auto-generated method stub
		Description descriptionReply = new Description();
		try{
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.URI)) {descriptionReply.setURI(propertyValue);return descriptionReply;}
		}catch(Exception e){
			e.printStackTrace();
		}
		return descriptionReply;
	}

		
	
}

/*
if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.OWLClasses)) {
Individual oIndividual =KAON2Manager.factory().individual(propertyValue);
String descriptionClass="";
try {
	Set<org.semanticweb.kaon2.api.owl.elements.Description> className = oIndividual.getDescriptionsMemberOf(ontologySearch);
	if (className!=null && className.size()>0)
		descriptionClass = className.iterator().next().toString();
	else return descriptionReply;
	
	Description replyTemp = (Description)processDescriptionIndividual(oIndividual, descriptionClass, ontologySearch);
	if (replyTemp==null) replyTemp = createMinDespcription(propertyValue);
	descriptionReply.addOWLClasses(replyTemp);
	replyTemp = null;
} catch (KAON2Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
return descriptionReply;
}

private static Description createMinDespcription(String propertyValue){
		Description desReply = new Description();
		desReply.setURI(propertyValue);
		return desReply;
	}
*/