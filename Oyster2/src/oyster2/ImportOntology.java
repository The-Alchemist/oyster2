package oyster2;

//import java.io.ByteArrayInputStream;
import java.io.File;
//import java.lang.reflect.InvocationTargetException;
import java.util.*;

//import ui.*;
//import ui.editor.*;
import util.*;
import java.text.DateFormat;
import java.util.Locale;

//import oyster2.*;
//import util.Utilities;

//import org.eclipse.core.runtime.IProgressMonitor;
//import org.eclipse.jface.action.Action;
//import org.eclipse.jface.dialogs.IDialogConstants;
//import org.eclipse.jface.dialogs.MessageDialog;
//import org.eclipse.jface.dialogs.ProgressMonitorDialog;
//import org.eclipse.jface.operation.IRunnableWithProgress;
//import org.eclipse.jface.window.ApplicationWindow;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Event;
//import org.eclipse.swt.widgets.FileDialog;
//import org.eclipse.swt.widgets.Shell;

//import org.semanticweb.kaon2.api.owl.axioms.*;
import org.semanticweb.kaon2.api.*;
//import org.semanticweb.kaon2.api.formatting.OntologyFormatting;
import org.semanticweb.kaon2.api.owl.elements.*;
//import org.semanticweb.kaon2.api.OntologyFileFormat; //OLD VERSION
import org.semanticweb.kaon2.api.formatting.*; 




public class ImportOntology {

	
	private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private String defURI = mOyster2.getPeerDescOntologyURI();
	private Ontology localRegistry = mOyster2.getLocalHostOntology();
	private KAON2Connection connection = mOyster2.getConnection();
	private DefaultOntologyResolver resolver = mOyster2.getResolver();
	private File localRegistryFile = mOyster2.getLocalRegistryFile();
	//private String TopicURI;
	private List propertyList = new LinkedList();
						
	
	public List extractMetadata(String filename){
		String ontologyURI = "";
		try{
			System.out.println("file: "+filename);
			if (filename.contains("://"))
				ontologyURI = resolver.registerOntology(serializeFileName(filename));
			else
				ontologyURI = resolver.registerOntology("file:///"+serializeFileName(filename));	
			connection.setOntologyResolver(resolver);
			Ontology ontologyImported = connection.openOntology(ontologyURI,new HashMap<String,Object>());
			propertyList.clear();
			OntologyProperty prop = new OntologyProperty(Constants.URI, ontologyURI);
			propertyList.add(prop);
			
			
			//STATISTICS
			extractStatistics(ontologyImported);
			
			//HEADER
			Map<String,Set<String>> ontologyProperties=ontologyImported.getOntologyProperties();
			System.out.println("The Properties size of '"+ontologyImported.getOntologyURI()+"' is: "+ontologyProperties.size());
	        //System.out.println("The Properties from '"+ontologyImported.getOntologyURI()+"' are:");
	        extractHeader(ontologyProperties);
	        
	        
	        // I DONT LIKE THIS
			prop = new OntologyProperty(Constants.name, Namespaces.guessLocalName(serializeFileName(filename)));
			if (!isPropertyIn(prop))propertyList.add(prop);
			//1
			prop = new OntologyProperty(Constants.hasDomain,"");
			if (!isPropertyIn(prop))propertyList.add(prop);
			
			// FINISH I DONT LIKE THIS
			
		}catch(Exception e){
			System.out.println(e + " in extractMetadata!");
		}
		return propertyList;
		
	}
	
	private void extractStatistics(Ontology ontologyImported ) {
	
		try{
			Request<Axiom> axiomRequest=ontologyImported.createAxiomRequest();
			int numAxioms=axiomRequest.size();
			OntologyProperty prop = new OntologyProperty(Constants.numAxioms,Integer.toString(numAxioms));
			propertyList.add(prop);
			
		}catch(Exception e){
			System.out.println(e + " extractStatistics");
		}	
		
	}
	
	private void extractHeader(Map<String,Set<String>> index) {
        // Print each entry from the concordance to the output file.
	 String values=null;
     Set entries = index.entrySet();  
          // The index viewed as a set of entries, where each
          // entry has a key and a value.  The objects in
          // this set are of type Map.Entry.
     Iterator iter = entries.iterator();
     OntologyProperty prop = null;
     while (iter.hasNext()) {
           // Get the next entry from the entry set and print
           // the term and list of line references that 
           // it contains.
        Map.Entry entry = (Map.Entry)iter.next();
        String term = (String)entry.getKey();
        Set lines = (Set)entry.getValue();      
        values=getValues(lines);
        String predicate = getOMVPredicate(term);
        if (predicate.length()>0){
        	prop = new OntologyProperty(predicate, values);
        	if (!isPropertyIn(prop))propertyList.add(prop);
        	else{
        		if ((predicate.equalsIgnoreCase(Constants.useImports)) ||
        				(predicate.equalsIgnoreCase(Constants.isBackwardCompatibleWith)) ||	
        				(predicate.equalsIgnoreCase(Constants.isIncompatibleWith)) ||
        				(predicate.equalsIgnoreCase(Constants.description))  ||
        				(predicate.equalsIgnoreCase(Constants.hasDomain))
        			){
        			
        			int pos=getPosition(prop);
        			System.out.println("INSIDE ELSE POSITION IS: "+pos);
        			OntologyProperty more=(OntologyProperty)propertyList.get(pos);
        			String val = more.getPropertyValue();
        			propertyList.remove(pos);
        			val=val+"  "+values;
        			prop = new OntologyProperty(predicate, val);
        			propertyList.add(pos,prop);
        		}
        	}
        }
        //System.out.println("Term: " + term + " ");
        //System.out.println("Values: "+ values);
        //System.out.println("Predicate: "+ predicate);
     }
  }
	
	private String getOMVPredicate(String term ) {
		String OMVPredicate="";
		OMVPredicate=evaluateOWL(term);
		if (OMVPredicate == "") OMVPredicate=evaluateDC(term);
		if (OMVPredicate == "") OMVPredicate=evaluateRDFS(term);
		if (OMVPredicate == "") OMVPredicate=evaluateDAML(term);
		return OMVPredicate;
	}

	private String evaluateOWL(String term){
		if (term.equals(OWL.IMPORTS))return Constants.useImports;
		if (term.equals(OWL.PRIORVERSION))return Constants.hasPriorVersion;
		if (term.equals(OWL.VERSIONINFO))return Constants.version;
		if (term.equals(OWL.BACKWARDCOMPATIBLEWITH))return Constants.isBackwardCompatibleWith;
		if (term.equals(OWL.INCOMPATIBLEWITH))return Constants.isIncompatibleWith;
		return "";
	}
	
	private String evaluateDC(String term){
		if (term.equals(DC.TITLE))return Constants.name;
		if (term.equals(DC.DATE))return Constants.creationDate;
		if (term.equals(DC.SUBJECT))return Constants.hasDomain;
		if (term.equals(DC.LANGUAGE))return Constants.naturalLanguage;
		if (term.equals(DC.DESCRIPTION))return Constants.description;
		if (term.equals(DC.MODIFIED))return Constants.modificationDate;
		return "";
	}
	
	private String evaluateRDFS(String term){
		if (term.equals(RDFS.LABEL))return Constants.name;
		if (term.equals(RDFS.COMMENT))return Constants.description;
		return "";
	}
	
	private String evaluateDAML(String term){
		if (term.equals(DAML.VERSIONINFO))return Constants.version;
		if (term.equals(DAML.IMPORTS))return Constants.useImports;
		return "";
	}
		
	private String getValues( Set stringSet ) {
        // Assume that all the objects in the set are of
        // type Integer.  Print the integer values on
        // one line, separated by commas.  The commas
        // make this a little tricky, since no comma
        // is printed before the first integer.
	 String tempValues=null;
     if (stringSet.isEmpty()) {
          // There is nothing to print if the set is empty.
        return null;
     }
     String stringValue;  // One of the Integers in the set.
     Iterator iter = stringSet.iterator(); // For traversing the set.
     stringValue = (String)iter.next(); // First item in the set.
                                     // We know the set is non-empty,
                                     // so this is OK.
     //System.out.println(" " + stringValue);  // Print the first item.
     tempValues = stringValue;
     while (iter.hasNext()) {
           // Print additional items, if any, with separating commas.
    	 stringValue = (String)iter.next();
         //System.out.println(" " + stringValue);
         tempValues = tempValues + " " + stringValue;
     }
     return tempValues;
  }
	
	public boolean isPropertyIn(OntologyProperty p){
		Iterator it = propertyList.iterator();
		try{
		while(it.hasNext()){
			OntologyProperty op = (OntologyProperty)it.next();
			if (op.getPropertyName().equalsIgnoreCase(p.getPropertyName())){
				return true;
			}
		}
		}catch(Exception ignore){
			//-- ignore
		}
		return false;
	}
	
	public Integer getPosition(OntologyProperty p){
		Iterator it = propertyList.iterator();
		int pos=0;
		try{
		while(it.hasNext()){
			OntologyProperty op = (OntologyProperty)it.next();
			if (op.getPropertyName().equalsIgnoreCase(p.getPropertyName())){
				return pos;
			}
			pos++;
		}
		}catch(Exception ignore){
			//-- ignore
		}
		return pos;
	}
	
	private String serializeFileName(String filename){
		String seperator = System.getProperty("file.separator");
		filename = filename.replace(seperator.charAt(0),'/');
		return  filename;
	}
	
	public void addImportOntologyToRegistry(List properties, int what){
		List propList = new LinkedList();
		propList.clear();
		propList=properties;
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		OWLClass ontologyConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.ontologyConcept);
		
		
    	//DataProperty ontologyURI = KAON2Manager.factory().dataProperty(Constants.OMVURI + Constants.URI);
    	//DataProperty ontologyName = KAON2Manager.factory().dataProperty(Constants.OMVURI + Constants.name);
    	//ObjectProperty useImports = KAON2Manager.factory().objectProperty(Constants.OMVURI + Constants.useImports);
    	ObjectProperty hasDomain = KAON2Manager.factory().objectProperty(Constants.OMVURI + Constants.hasDomain);
		ObjectProperty ontologyLocation = KAON2Manager.factory().objectProperty(defURI + "#ontologyLocation");
    	ObjectProperty provideOntology = KAON2Manager.factory().objectProperty(defURI + "#provideOntology");
    	
		Individual ontologyIndividual = null; 
		Iterator it1 = propList.iterator();
		while(it1.hasNext()){
			OntologyProperty prop = (OntologyProperty)it1.next();
			if(prop.getPropertyName().equals(Constants.URI)){
				String uri = prop.getPropertyValue();
				ontologyIndividual = KAON2Manager.factory().individual(uri);
				break;
			}
		}
		try{	
			if(localRegistry.containsEntity(ontologyIndividual,true)){
				if (what==0)
					System.out.println("The importing ontology already exist in the local expertise registry");
				else if (what==1){
					System.out.println("Please refer to the registry file! The registering ontology already exist. Please use method Replace instead!");
					return;
				}
				else if (what==2){
					Map dataPropertyMap = ontologyIndividual.getDataPropertyValues(localRegistry);
					Map objectPropertyMap = ontologyIndividual.getObjectPropertyValues(localRegistry);
					if ((dataPropertyMap.size()+objectPropertyMap.size())>0){
						Collection keySet = dataPropertyMap.keySet();
						Iterator keys = keySet.iterator();
						while(keys.hasNext()){
							String keyStr = keys.next().toString();
							DataProperty property = KAON2Manager.factory().dataProperty(keyStr);
							String	propertyValue = util.Utilities.getString(ontologyIndividual.getDataPropertyValue(localRegistry,property));
							Boolean isI = util.Utilities.isInt(Namespaces.guessLocalName(property.getURI()));
							if (!isI)
								changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(property,ontologyIndividual,KAON2Manager.factory().constant(propertyValue)),OntologyChangeEvent.ChangeType.REMOVE));
							else
								changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(property,ontologyIndividual,KAON2Manager.factory().constant(ontologyIndividual.getDataPropertyValue(localRegistry,property).getValue())),OntologyChangeEvent.ChangeType.REMOVE));
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
								Individual propertyValue =(Individual) itInt.next();
								changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(property,ontologyIndividual,propertyValue),OntologyChangeEvent.ChangeType.REMOVE));
							}
						}
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(ontologyConcept,ontologyIndividual),OntologyChangeEvent.ChangeType.REMOVE));
						localRegistry.applyChanges(changes);
						localRegistry.persist();
						localRegistry.saveOntology(OntologyFileFormat.OWL_RDF,localRegistryFile,"ISO-8859-1");						
					}
					//return;
					changes.clear();
					changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(ontologyConcept,ontologyIndividual),OntologyChangeEvent.ChangeType.ADD));
				}
			}
			else changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(ontologyConcept,ontologyIndividual),OntologyChangeEvent.ChangeType.ADD));
			
			//TIMESTAMP
			DataProperty oProperty = KAON2Manager.factory().dataProperty(Constants.OMVURI + Constants.timeStamp);
			Date now = new Date();
			String sNow = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(now);
			System.out.println("us format: "+sNow);
			
			String preValue = util.Utilities.getString(ontologyIndividual.getDataPropertyValue(localRegistry,oProperty)); //(String)
			if(preValue !=null)changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(oProperty,ontologyIndividual,KAON2Manager.factory().constant(preValue)),OntologyChangeEvent.ChangeType.REMOVE));
			changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(oProperty,ontologyIndividual,KAON2Manager.factory().constant(sNow)),OntologyChangeEvent.ChangeType.ADD));		    
			//END TIMESTAMP
			
			Ontology resourceTypeOntology = mOyster2.getTypeOntology();
			Iterator it2 = propList.iterator();
			while(it2.hasNext()){
				OntologyProperty prop = (OntologyProperty)it2.next();
				//System.out.println(prop.getPropertyName());
				Boolean whatIs = checkDataProperty(prop.getPropertyName());
				if (whatIs){
					DataProperty ontologyDataProperty = KAON2Manager.factory().dataProperty(Constants.OMVURI + prop.getPropertyName());
					Boolean isI = util.Utilities.isInt(prop.getPropertyName());
					String pValue = prop.getPropertyValue();
					if (!isI){
						String oldValue = util.Utilities.getString(ontologyIndividual.getDataPropertyValue(localRegistry,ontologyDataProperty)); //(String)
						if(oldValue !=null)changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,ontologyIndividual,KAON2Manager.factory().constant(oldValue)),OntologyChangeEvent.ChangeType.REMOVE));
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,ontologyIndividual,KAON2Manager.factory().constant(pValue)),OntologyChangeEvent.ChangeType.ADD));
					}
					else{
						String oldValue = util.Utilities.getString(ontologyIndividual.getDataPropertyValue(localRegistry,ontologyDataProperty)); //(String)
						if(oldValue !=null)changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,ontologyIndividual,KAON2Manager.factory().constant(ontologyIndividual.getDataPropertyValue(localRegistry,ontologyDataProperty).getValue())),OntologyChangeEvent.ChangeType.REMOVE));
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,ontologyIndividual,KAON2Manager.factory().constant(new Integer(pValue))),OntologyChangeEvent.ChangeType.ADD));
					}
				}
				//0//
				//IMP//
				else if (prop.getPropertyName().equals(Constants.hasDomain)){					
					String domain = prop.getPropertyValue();
					if(!domain.contains(Constants.TopicsURI))domain = Constants.TopicsURI+domain;
					Individual subjectIndiv = KAON2Manager.factory().individual(domain);
					// SHOULD WE ADD ONTOLOGY DOMAIN INSTANCE? YES
					String className=hasDomain.getRangeDescriptions(resourceTypeOntology).iterator().next().toString();
					if (className!=null){
						OWLClass tempConcept = KAON2Manager.factory().owlClass(className);
						if(!localRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,subjectIndiv),true))
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,subjectIndiv),OntologyChangeEvent.ChangeType.ADD));				
					}
					// ADD PROPERTY VALUE
					Individual oldSubjectIndiv = ontologyIndividual.getObjectPropertyValue(localRegistry,hasDomain);
					if(oldSubjectIndiv != null)	changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(hasDomain,ontologyIndividual,oldSubjectIndiv),OntologyChangeEvent.ChangeType.REMOVE));	
					changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(hasDomain,ontologyIndividual,subjectIndiv),OntologyChangeEvent.ChangeType.ADD));	
				}
				else{ 
					String pOValue = prop.getPropertyValue();
					ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.OMVURI + prop.getPropertyName());
					Individual objectPropertyIndividual = KAON2Manager.factory().individual(pOValue);
					/*ADD CONCEPT INSTANCE*/
					String className=ontologyObjectProperty.getRangeDescriptions(resourceTypeOntology).iterator().next().toString();
					if (className!=null){
						OWLClass tempConcept = KAON2Manager.factory().owlClass(className);
						if(!localRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),true))
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));				
					}
					/*ADD PROPERTY VALUE*/
					/*TODO How to handle multiple values */
					Individual oldObjectPropertyIndividual = ontologyIndividual.getObjectPropertyValue(localRegistry,ontologyObjectProperty);
					if(oldObjectPropertyIndividual != null)	changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,ontologyIndividual,oldObjectPropertyIndividual),OntologyChangeEvent.ChangeType.REMOVE));	
					changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,ontologyIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
				}
			}	
			Individual peerIndiv = mOyster2.getLocalAdvertInformer().getLocalPeer();
			if(!localRegistry.containsAxiom(KAON2Manager.factory().objectPropertyMember(ontologyLocation,ontologyIndividual,peerIndiv),true))
				changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyLocation,ontologyIndividual,peerIndiv),OntologyChangeEvent.ChangeType.ADD));	
			if(!localRegistry.containsAxiom(KAON2Manager.factory().objectPropertyMember(provideOntology,peerIndiv,ontologyIndividual),true))
				changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(provideOntology,peerIndiv,ontologyIndividual),OntologyChangeEvent.ChangeType.ADD));
			localRegistry.applyChanges(changes);
			localRegistry.persist();
			localRegistry.saveOntology(OntologyFileFormat.OWL_RDF,localRegistryFile,"ISO-8859-1");
			
		}catch(Exception e){
			System.out.println("add ontologyDoc to registry error: "+e.getMessage());
		}
	}
	
	public Boolean checkDataProperty(String propertyName)  {
		Ontology resourceTypeOntology = mOyster2.getTypeOntology();
		OWLClass ontologyClass = KAON2Manager.factory().owlClass(Constants.OMV+Constants.DefaultTypeOntologyRoot);
		try{
			/*KAON2 BUG, DOES NOT SUPPORT OWL DL
	         * SHOULD BE DELETED WHEN IT DOES
	         */
			if(propertyName.equals(Constants.name)) return true;
			if(propertyName.equals(Constants.acronym)) return true;
			if(propertyName.equals(Constants.description)) return true;
			if(propertyName.equals(Constants.documentation)) return true;
			
			/* UNTIL HERE */
			
			Set<DataProperty> dataProperties=ontologyClass.getDataPropertiesFrom(resourceTypeOntology);
			for (DataProperty dataProperty : dataProperties){
				if(propertyName.equals(Namespaces.guessLocalName(dataProperty.getURI()))) return true; 
			}
		}
	    catch (KAON2Exception e) {
	    	System.err.println(e + " in checkDataProperty()");
	    }
		return false;
	}
		
}
//0//
/*
if(prop.getPropertyName().equals(Constants.URI)){
	String uri = prop.getPropertyValue();
	String oldUri =(String) ontologyDocIndiv.getDataPropertyValue(localRegistry,ontologyURI);
	if(oldUri !=null){
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyURI,ontologyDocIndiv,oldUri),OntologyChangeEvent.ChangeType.REMOVE));
	}
	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyURI,ontologyDocIndiv,uri),OntologyChangeEvent.ChangeType.ADD));	
}
else if (prop.getPropertyName().equals(Constants.name)){
	String name = prop.getPropertyValue();
	String oldName = (String)ontologyDocIndiv.getDataPropertyValue(localRegistry,ontologyName);
	if(oldName !=null){
	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyName,ontologyDocIndiv,oldName),OntologyChangeEvent.ChangeType.REMOVE));
	}
	changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyName,ontologyDocIndiv,name),OntologyChangeEvent.ChangeType.ADD));	
}
*/

//IMP//
/* NOT NECESSARY ANYMORE,IS IN GENERAL ELSE 
else if (prop.getPropertyName().equals(Constants.useImports)){ //
	String importsURI = prop.getPropertyValue();
	Individual importOntologyIndiv = KAON2Manager.factory().individual(importsURI);
	if(!localRegistry.containsAxiom(KAON2Manager.factory().classMember(ontologyConcept,importOntologyIndiv),true))
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(ontologyConcept,importOntologyIndiv),OntologyChangeEvent.ChangeType.ADD));
	if(!localRegistry.containsAxiom(KAON2Manager.factory().objectPropertyMember(useImports,ontologyIndividual,importOntologyIndiv),true))
		changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(useImports,ontologyIndividual,importOntologyIndiv),OntologyChangeEvent.ChangeType.ADD));	
}
*/

//1
/*
Collection Col = ontologyImported.getImportedOntologies();
Iterator it = Col.iterator();
while(it.hasNext()){
	Ontology io = (Ontology)it.next();
	OntologyProperty op = new OntologyProperty(Constants.IMPORT, io.getOntologyURI());
	propertyList.add(op);
}
*/		