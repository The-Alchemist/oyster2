package org.neontoolkit.registry.api.individuals;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Namespaces;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.owl.elements.DataProperty;
import org.semanticweb.kaon2.api.owl.elements.Individual;
import org.neontoolkit.owlodm.api.OWLEntity;
import org.neontoolkit.owlodm.api.OWLEntity.Datatype;
import org.neontoolkit.registry.oyster2.Constants;


/**
 * The class ProcessOWLEntityIndividuals provides the methods to
 * create OWLEntity objects  
 * representing the result of a query 
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class ProcessOWLEntityIndividuals{
	static LinkedList<Individual> onProcess = new LinkedList<Individual>();
   
    static public OWLEntity processOWLEntityIndividual(Individual OWLEntityIndividual, String whichClassFull, Ontology ontologySearch){
     String whichClass = Namespaces.guessLocalName(whichClassFull);
     OWLEntity reply=new OWLEntity();
     
     
     if  (!whichClass.equalsIgnoreCase("DataProperty") && !whichClass.equalsIgnoreCase("Datatype") && !whichClass.equalsIgnoreCase("Individual") && !whichClass.equalsIgnoreCase("ObjectProperty")){
    	 reply = ProcessDescriptionIndividuals.processDescriptionIndividual(OWLEntityIndividual, whichClassFull, ontologySearch);
    	 return reply;
     }
     else{
    	 if (!onProcess.contains(OWLEntityIndividual)){
    		 onProcess.add(OWLEntityIndividual);  
    		 try{
    			 Map dataPropertyMap = OWLEntityIndividual.getDataPropertyValues(ontologySearch);
    			 Map objectPropertyMap = OWLEntityIndividual.getObjectPropertyValues(ontologySearch);
    			 if ((dataPropertyMap.size()+objectPropertyMap.size())>0){
		
    				 if (whichClass.equalsIgnoreCase("DataProperty")) reply = new org.neontoolkit.owlodm.api.OWLEntity.DataProperty(OWLEntityIndividual.getURI());
    				 else if (whichClass.equalsIgnoreCase("Datatype")) reply = new Datatype(OWLEntityIndividual.getURI());
    				 else if (whichClass.equalsIgnoreCase("Individual")) reply = new org.neontoolkit.owlodm.api.OWLEntity.Individual(OWLEntityIndividual.getURI());
    				 else if (whichClass.equalsIgnoreCase("ObjectProperty")) reply = new org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty(OWLEntityIndividual.getURI());			
			
    				 Collection keySet = dataPropertyMap.keySet();
    				 Iterator keys = keySet.iterator();
    				 while(keys.hasNext()){
    					 String keyStr = keys.next().toString();
    					 DataProperty property = KAON2Manager.factory().dataProperty(keyStr);
    					 Collection propertyCol= new LinkedList();
    					 propertyCol = (Collection)dataPropertyMap.get(property);
    					 if(propertyCol==null)System.err.println("datapropertyCol is null");
    					 Iterator itInt = propertyCol.iterator();
    					 while(itInt.hasNext()){
    						 Object propertyObject =(Object) itInt.next();
    						 String	propertyValue = org.neontoolkit.registry.util.Utilities.getString(propertyObject);
    						 //System.out.println("property: "+property.getURI()+" VALUE= "+propertyValue);
    						 if (property.getURI().equalsIgnoreCase(Constants.OWLODMURI+Constants.URI)) reply.append(createOWLEntity(property.getURI(),propertyValue, ontologySearch));					
    					 }	
    				 }
    			 }
    		 }catch(Exception e){
    			 e.printStackTrace();
    		 }
    		 onProcess.remove(OWLEntityIndividual);
    		 if (reply instanceof org.neontoolkit.owlodm.api.OWLEntity.DataProperty || reply instanceof Datatype || reply instanceof org.neontoolkit.owlodm.api.OWLEntity.Individual || reply instanceof org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty )
    			 return reply;
    		 else return null; 
    	 }
    	 else{
    		 reply.setURI(OWLEntityIndividual.getURI());
    		 return reply;
    	 }
     }
	}
	
    private static OWLEntity createOWLEntity(String uri, String propertyValue, Ontology ontologySearch) {
		// TODO Auto-generated method stub
    	OWLEntity OWLEntityReply = new OWLEntity();
		try{
			if (uri.equalsIgnoreCase(Constants.OWLODMURI+Constants.URI)) {OWLEntityReply.setURI(propertyValue);return OWLEntityReply;}
		}catch(Exception e){
			e.printStackTrace();
		}
		return OWLEntityReply;
	}

}

