package org.neontoolkit.registry.oyster2;


import java.io.File;
import java.util.*;
import java.text.DateFormat;
import java.util.Locale;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.registry.util.DAML;
import org.neontoolkit.registry.util.DC;
import org.neontoolkit.registry.util.OWL;
import org.neontoolkit.registry.util.RDFS;
import org.semanticweb.kaon2.api.*;
import org.semanticweb.kaon2.api.owl.elements.*;
import org.semanticweb.kaon2.api.formatting.*; 
//import org.neon_toolkit.registry.core.AdvertInformer;

public class ImportOntology {

	
	private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private String pOMVURI = mOyster2.getPeerDescOntologyURI();
	private Ontology localRegistry = mOyster2.getLocalHostOntology();
	private KAON2Connection connection = mOyster2.getConnection();
	private DefaultOntologyResolver resolver = mOyster2.getResolver();
	private File localRegistryFile = mOyster2.getLocalRegistryFile();
	private LinkedList<OntologyProperty> propertyList = new LinkedList<OntologyProperty>();
	private String localURI = localRegistry.getOntologyURI();
	//private AdvertInformer mInformer = mOyster2.getLocalAdvertInformer();
	
	public List extractMetadata(String filename){
		String ontologyURI = "";
		try{
			//System.out.println("file: "+filename);
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
			//System.out.println("The Properties size of '"+ontologyImported.getOntologyURI()+"' is: "+ontologyProperties.size());
	        //System.out.println("The Properties from '"+ontologyImported.getOntologyURI()+"' are:");
	        extractHeader(ontologyProperties);
	        
	        
	        // I DONT LIKE THIS
			prop = new OntologyProperty(Constants.name, Namespaces.guessLocalName(serializeFileName(filename)));
			if (!isPropertyIn(prop))propertyList.add(prop);
			prop = new OntologyProperty(Constants.resourceLocator, serializeFileName(filename));
			if (!isPropertyIn(prop))propertyList.add(prop);
			
			extractFormat(ontologyImported);
			
			// FINISH I DONT LIKE THIS
			if (ontologyImported!=mOyster2.getLocalHostOntology() && 
					ontologyImported!= mOyster2.getTypeOntology() &&
					ontologyImported!= mOyster2.getTopicOntology() &&
					ontologyImported!= mOyster2.getChangeOntology() &&
					ontologyImported!= mOyster2.getOWLChangeOntology() &&
					ontologyImported!= mOyster2.getWorkflowOntology() &&
					ontologyImported!= mOyster2.getOWLODMOntology() &&
					ontologyImported!= mOyster2.getPeerOntology() &&
					ontologyImported!= mOyster2.getMappingOntology()){
				Set<Ontology> finish = new HashSet<Ontology>();
				finish.add(ontologyImported);
				connection.closeOntologies(finish);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return propertyList;
		
	}
	
	private void extractFormat(Ontology ontologyImported){
		try{
			OntologyFormatting oFormat = ontologyImported.getOntologyFormatting();
			if (oFormat.getFormatName()!= null && oFormat.getFormatName()!=""){
				OntologyProperty prop = new OntologyProperty(Constants.hasOntologySyntax, oFormat.getFormatName());
		        if (!isPropertyIn(prop))propertyList.add(prop);
		        
		        LinkedList<OntologyProperty> pTempList = new LinkedList<OntologyProperty>();
	    		String values = oFormat.getFormatName();
	    		Individual refIndividual = KAON2Manager.factory().individual(values);
	    		OWLClass oTConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.OntologySyntaxConcept);
	    		
	    			OntologyProperty propTemp = new OntologyProperty(Constants.name, values);
	    			if(localRegistry.containsAxiom(KAON2Manager.factory().classMember(oTConcept,refIndividual),true)){
	    				Map dataPropertyMap = refIndividual.getDataPropertyValues(localRegistry);
						Map objectPropertyMap = refIndividual.getObjectPropertyValues(localRegistry);
						if ((dataPropertyMap.size()+objectPropertyMap.size())<=0){
		        			pTempList.add(propTemp);
		        			addConceptToRegistry(0,pTempList,9, null);
						}
	    			}
	    			else {
	        			pTempList.add(propTemp);
	        			addConceptToRegistry(0,pTempList,9, null);
	    			}    		
			}
	        
		}catch(Exception e){
				e.printStackTrace();
		}	
	    	
	}
	
	private void extractStatistics(Ontology ontologyImported ) {
	
		try{
			Request<Axiom> axiomRequest=ontologyImported.createAxiomRequest();
			int numAxioms=axiomRequest.size();
			OntologyProperty prop = new OntologyProperty(Constants.numberOfAxioms,Integer.toString(numAxioms));
			propertyList.add(prop);
			
		}catch(Exception e){
			e.printStackTrace();
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
        //values=getValues(lines);
        String predicate = getOMVPredicate(term);
        if (predicate.length()>0){
        	Iterator iVal = lines.iterator();
            while (iVal.hasNext()){ 
               values = (String)iVal.next();
               prop = new OntologyProperty(predicate, values);
               if (!isPropertyIn(prop))propertyList.add(prop);
               else{
            	   if ((predicate.equalsIgnoreCase(Constants.description))){
        			
            		   int pos=getPosition(prop);
            		   //System.out.println("INSIDE ELSE POSITION IS: "+pos);
            		   OntologyProperty more=(OntologyProperty)propertyList.get(pos);
            		   String val = more.getPropertyValue();
            		   propertyList.remove(pos);
            		   val=val+"  "+values;
            		   prop = new OntologyProperty(predicate, val);
            		   propertyList.add(pos,prop);
            	   }
            	   else if ((predicate.equalsIgnoreCase(Constants.useImports)) ||
        				(predicate.equalsIgnoreCase(Constants.isBackwardCompatibleWith)) ||	
        				(predicate.equalsIgnoreCase(Constants.isIncompatibleWith)) ||
        				(predicate.equalsIgnoreCase(Constants.hasDomain))
            	   ){
            		   propertyList.addFirst(prop);
            	   }
            	   else if (org.neontoolkit.registry.util.Utilities.multiple(predicate)){
        			//multiple datatypes values that can occur within the import process
        			propertyList.addFirst(prop);
            	   }	
               }
               if ((predicate.equalsIgnoreCase(Constants.useImports)) ||
    				(predicate.equalsIgnoreCase(Constants.isBackwardCompatibleWith)) ||	
    				(predicate.equalsIgnoreCase(Constants.isIncompatibleWith)) ||
    				(predicate.equalsIgnoreCase(Constants.hasPriorVersion))
    				){
            	   LinkedList<OntologyProperty> pTempList = new LinkedList<OntologyProperty>();
            	   Individual refIndividual = KAON2Manager.factory().individual(values);
            	   OWLClass oTConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.ontologyConcept);
            	   try{
            		   OntologyProperty propTemp = new OntologyProperty(Constants.URI, values);
            		   if(localRegistry.containsAxiom(KAON2Manager.factory().classMember(oTConcept,refIndividual),true)){
            			   Map dataPropertyMap = refIndividual.getDataPropertyValues(localRegistry);
            			   Map objectPropertyMap = refIndividual.getObjectPropertyValues(localRegistry);
            			   if ((dataPropertyMap.size()+objectPropertyMap.size())<=0){
            				   pTempList.add(propTemp);
            				   addImportOntologyToRegistry(pTempList,3, null);
            			   }
            		   }
            		   else {
            			   pTempList.add(propTemp);
            			   addImportOntologyToRegistry(pTempList,3, null);
            		   }
            	   }catch(Exception e){
            		   e.printStackTrace();
            	   }
               }
               if ((predicate.equalsIgnoreCase(Constants.hasDomain))){
            	   LinkedList<OntologyProperty> pTempList = new LinkedList<OntologyProperty>();
            	   if(!values.contains("://"))values = Constants.TopicsURI+values;  //Add namespace if not present
            	   Individual refIndividual = KAON2Manager.factory().individual(values);
            	   OWLClass oTConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.OntologyDomainConcept);
            	   try{
            		   OntologyProperty propTemp = new OntologyProperty(Constants.URI, values);
            		   if(localRegistry.containsAxiom(KAON2Manager.factory().classMember(oTConcept,refIndividual),true)){
            			   Map dataPropertyMap = refIndividual.getDataPropertyValues(localRegistry);
            			   Map objectPropertyMap = refIndividual.getObjectPropertyValues(localRegistry);
            			   if ((dataPropertyMap.size()+objectPropertyMap.size())<=0){
            				   pTempList.add(propTemp);
            				   addConceptToRegistry(0,pTempList,5, null);
            			   }
            		   }
            		   else {
            			   pTempList.add(propTemp);
            			   addConceptToRegistry(0,pTempList,5, null);
            		   }
            	   }catch(Exception e){
            		   e.printStackTrace();
            	   }
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
		
	/*
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
	*/
	
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
	
	
	/**
	 * Adds/modify/remove a metadata entry (different than ontology) 
	 * in the registry.
	 * @param how is the adding mode. It works as follows:
	 * 0=Merge: The properties specified are added for multiple values
	 * properties (if the value do not exists already) and replaced 
	 * for single value properties.
	 * 1=Pure Register: If the metadata entry exists already 
	 * in the registry the method returns.
	 * 2=Replace: Same as remove and add object i.e. all existing 
	 * properties are removed and the new set of properties are added
	 * without deleting the object
	 * 4=Remove: Remove the metadata entry
	 * 5=1 (Pure Register) but diplays message 
	 * @param properties is the set of properties (OMV properties)  
	 * that has the ontology metadata entry we want to register.
	 * @param which specifies the type of object we want to add
	 * 0-12= OMV Core objects
	 * 13-23= Mapping objects
	 * 30=Change objects
	 * 31=Log object
	 * 32=Change specification object
	 * 50=Axiom objects
	 * 60=Workflow objects
	 * 70=Description objects
	 * 80=OWLEntity objects
	 * @param registry the targetOntology to apply axioms. Null=localRegistry
	 */
	public boolean addConceptToRegistry(int how,List properties, int which, Ontology registry){
		Ontology targetRegistry;
		if (registry!=null) targetRegistry=registry;
		else targetRegistry = localRegistry;
		
		List propList = new LinkedList();
		propList.clear();
		propList=properties;
		String tURN=null;
		OWLClass oConcept = null;
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		if (which==0)oConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.personConcept);
		else if (which==1)oConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.organisationConcept);
		else if (which==2)oConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.OntologyEngineeringToolConcept);
		else if (which==3)oConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.OntologyEngineeringMethodologyConcept);
		else if (which==4)oConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.KnowledgeRepresentationParadigmConcept);
		else if (which==5)oConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.OntologyDomainConcept);
		else if (which==6)oConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.OntologyTypeConcept);
		else if (which==7)oConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.OntologyTaskConcept);
		else if (which==8)oConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.OntologyLanguageConcept);
		else if (which==9)oConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.OntologySyntaxConcept);
		else if (which==10)oConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.FormalityLevelConcept);
		else if (which==11)oConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.LicenseModelConcept);
		else if (which==12)oConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.LocationConcept);
		else if (which==13)oConcept = KAON2Manager.factory().owlClass(Constants.MOMVURI+Constants.mappingConcept);
		else if (which==14)oConcept = KAON2Manager.factory().owlClass(Constants.MOMVURI+Constants.propertyConcept);
		else if (which==15)oConcept = KAON2Manager.factory().owlClass(Constants.MOMVURI+Constants.algorithmConcept);
		else if (which==16)oConcept = KAON2Manager.factory().owlClass(Constants.MOMVURI+Constants.manualMethodConcept);
		else if (which==17)oConcept = KAON2Manager.factory().owlClass(Constants.MOMVURI+Constants.filterConcept);
		else if (which==18)oConcept = KAON2Manager.factory().owlClass(Constants.MOMVURI+Constants.parallelConcept);
		else if (which==19)oConcept = KAON2Manager.factory().owlClass(Constants.MOMVURI+Constants.sequenceConcept);
		else if (which==20)oConcept = KAON2Manager.factory().owlClass(Constants.MOMVURI+Constants.argumentConcept);
		else if (which==21)oConcept = KAON2Manager.factory().owlClass(Constants.MOMVURI+Constants.certificateConcept);
		else if (which==22)oConcept = KAON2Manager.factory().owlClass(Constants.MOMVURI+Constants.proofConcept);
		else if (which==23)oConcept = KAON2Manager.factory().owlClass(Constants.MOMVURI+Constants.parameterConcept);
		else if (which==30){
			oConcept = KAON2Manager.factory().owlClass(Constants.CHANGEURI+Constants.changeConcept);
			Iterator itT = propList.iterator();
			while(itT.hasNext()){
				OntologyProperty prop = (OntologyProperty)itT.next();
				if(prop.getPropertyName().equals(Constants.name)){
					oConcept = KAON2Manager.factory().owlClass(Constants.CHANGEURI+prop.getPropertyValue());
					break;
				}
			}
		}
		else if (which==31)oConcept = KAON2Manager.factory().owlClass(Constants.CHANGEURI+Constants.LogConcept);
		else if (which==32)oConcept = KAON2Manager.factory().owlClass(Constants.CHANGEURI+Constants.changeSpecificationConcept);
		else if (which==50){
			oConcept = KAON2Manager.factory().owlClass(Constants.OWLODMURI+Constants.AxiomConcept);
			Iterator itT = propList.iterator();
			while(itT.hasNext()){
				OntologyProperty prop = (OntologyProperty)itT.next();
				if(prop.getPropertyName().equals(Constants.name)){
					oConcept = KAON2Manager.factory().owlClass(Constants.OWLODMURI+prop.getPropertyValue());
					break;
				}
			}
		}
		else if (which==60){
			oConcept = KAON2Manager.factory().owlClass(Constants.OWLODMURI+Constants.ActionConcept);
			Iterator itT = propList.iterator();
			while(itT.hasNext()){
				OntologyProperty prop = (OntologyProperty)itT.next();
				if(prop.getPropertyName().equals(Constants.name)){
					oConcept = KAON2Manager.factory().owlClass(Constants.WORKFLOWURI+prop.getPropertyValue());
					break;
				}
			}
		}
		else if (which==70){
			oConcept = KAON2Manager.factory().owlClass(Constants.OWLODMURI+Constants.DescriptionConcept);
			Iterator itT = propList.iterator();
			while(itT.hasNext()){
				OntologyProperty prop = (OntologyProperty)itT.next();
				if(prop.getPropertyName().equals(Constants.name)){
					oConcept = KAON2Manager.factory().owlClass(Constants.OWLODMURI+prop.getPropertyValue());
					break;
				}
			}
		}
		else if (which==80){
			oConcept = KAON2Manager.factory().owlClass(Constants.OWLODMURI+Constants.OWLEntity);
			Iterator itT = propList.iterator();
			while(itT.hasNext()){
				OntologyProperty prop = (OntologyProperty)itT.next();
				if(prop.getPropertyName().equals(Constants.name)){
					oConcept = KAON2Manager.factory().owlClass(Constants.OWLODMURI+prop.getPropertyValue());
					break;
				}
			}
		}
		
		if (which==0){
			Iterator it1 = propList.iterator();
			while(it1.hasNext()){
				OntologyProperty prop = (OntologyProperty)it1.next();
				if(prop.getPropertyName().equals(Constants.firstName)){
					tURN = prop.getPropertyValue();
					break;
				}
			}
			Iterator it0 = propList.iterator();
			while(it0.hasNext()){
				OntologyProperty prop = (OntologyProperty)it0.next();
				if(prop.getPropertyName().equals(Constants.lastName)){
					tURN = tURN+prop.getPropertyValue();
					break;
				}
			}
		}
		else if (which==5){
			Iterator it1 = propList.iterator();
			while(it1.hasNext()){
				OntologyProperty prop = (OntologyProperty)it1.next();
				if(prop.getPropertyName().equals(Constants.URI)){
					tURN = prop.getPropertyValue();
					break;
				}
			}
		}
		else if (which==12){
			Iterator it1 = propList.iterator();
			while(it1.hasNext()){
				OntologyProperty prop = (OntologyProperty)it1.next();
				if(prop.getPropertyName().equals(Constants.street)){
					tURN = prop.getPropertyValue();
					break;
				}
			}
		}
		else if (which==13){
			Iterator it1 = propList.iterator();
			while(it1.hasNext()){
				OntologyProperty prop = (OntologyProperty)it1.next();
				if(prop.getPropertyName().equals(Constants.URI)){
					tURN = prop.getPropertyValue();
					break;
				}
			}
		}
		else if (which>13 && which<=23){
			Iterator it1 = propList.iterator();
			while(it1.hasNext()){
				OntologyProperty prop = (OntologyProperty)it1.next();
				if(prop.getPropertyName().equals(Constants.ID)){
					tURN = prop.getPropertyValue();
					break;
				}
			}
		}
		else if (which>=30){ //CHANGES & AXIOMS & WORKFLOW & DECLARATIONS & OWLEntity
			Iterator it1 = propList.iterator();
			while(it1.hasNext()){
				OntologyProperty prop = (OntologyProperty)it1.next();
				if(prop.getPropertyName().equals(Constants.URI)){
					tURN = prop.getPropertyValue();
					break;
				}
			}
		}
		else{
			Iterator it1 = propList.iterator();
			while(it1.hasNext()){
				OntologyProperty prop = (OntologyProperty)it1.next();
				if(prop.getPropertyName().equals(Constants.name)){
					tURN = prop.getPropertyValue();
					break;
				}
			}
		}
		Individual oIndividual;
		if (which==5 || which==13 || which>=30) oIndividual = KAON2Manager.factory().individual(tURN);
		else oIndividual = KAON2Manager.factory().individual(localURI+"#"+tURN);
		try{
			if(targetRegistry.containsAxiom(KAON2Manager.factory().classMember(oConcept,oIndividual),true)){		//if(targetRegistry.containsEntity(oIndividual,true))	
				if (how==0)
					mOyster2.getLogger().info("The concept "+ tURN +" already exist in the local expertise registry");
				else if (how==1 || how==5){
					if (how ==5) System.out.println("Please refer to the registry file! The registering concept already exist. Please use method Replace instead!");
					return false;
				}
				else if (how==2 || how==4){
					Map dataPropertyMap = oIndividual.getDataPropertyValues(targetRegistry);
					Map objectPropertyMap = oIndividual.getObjectPropertyValues(targetRegistry);
					if ((dataPropertyMap.size()+objectPropertyMap.size())>0){
						Collection keySet = dataPropertyMap.keySet();
						Iterator keys = keySet.iterator();
						while(keys.hasNext()){
							String keyStr = keys.next().toString();
							DataProperty property = KAON2Manager.factory().dataProperty(keyStr);
							/*7*/
							//TODO: manage multiple values for datatypes different to string in other objects.
							Collection propertyCol= new LinkedList();
							propertyCol = (Collection)dataPropertyMap.get(property);
							if(propertyCol==null)System.err.println("datapropertyCol is null");
							Iterator itInt = propertyCol.iterator();
							while(itInt.hasNext()){
								Object propertyObject =(Object) itInt.next();
								String	propertyValue = org.neontoolkit.registry.util.Utilities.getString(propertyObject);
								int dType;
								if (which>12 && which<30) dType = org.neontoolkit.registry.util.Utilities.getDType(Namespaces.guessLocalName(property.getURI()),mOyster2.getMappingOntology(),Constants.MOMVURI);
								else if (which>=30 && which<50) dType = org.neontoolkit.registry.util.Utilities.getDType(Namespaces.guessLocalName(property.getURI()),mOyster2.getChangeOntology(),Constants.CHANGEURI); //OWLChange ontology does not define properties...
								else if (which>=50 && which<60) dType = org.neontoolkit.registry.util.Utilities.getDType(Namespaces.guessLocalName(property.getURI()),mOyster2.getOWLODMOntology(),Constants.OWLODMURI); //OWLODM ontology
								else if (which>=60 && which<70) dType = org.neontoolkit.registry.util.Utilities.getDType(Namespaces.guessLocalName(property.getURI()),mOyster2.getWorkflowOntology(),Constants.WORKFLOWURI); //OWLODM ontology
								else if (which>=70 && which<80) dType = org.neontoolkit.registry.util.Utilities.getDType(Namespaces.guessLocalName(property.getURI()),mOyster2.getOWLODMOntology(),Constants.OWLODMURI); //OWLODM ontology
								else if (which>=80) dType = org.neontoolkit.registry.util.Utilities.getDType(Namespaces.guessLocalName(property.getURI()),mOyster2.getOWLODMOntology(),Constants.OWLODMURI); //OWLODM ontology
								else dType = org.neontoolkit.registry.util.Utilities.getDType(Namespaces.guessLocalName(property.getURI()),mOyster2.getTypeOntology(),Constants.OMVURI);								
								if (dType==org.neontoolkit.registry.util.Utilities.STRING_TYPE){						
									changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(property,oIndividual,KAON2Manager.factory().constant(propertyValue)),OntologyChangeEvent.ChangeType.REMOVE)); //Deal with multiple string values
								}
								else {	
									changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(property,oIndividual,KAON2Manager.factory().constant(oIndividual.getDataPropertyValue(targetRegistry,property).getValue())),OntologyChangeEvent.ChangeType.REMOVE));
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
								Individual propertyValue =(Individual) itInt.next();
								changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(property,oIndividual,propertyValue),OntologyChangeEvent.ChangeType.REMOVE));
							}
						}
					}
					if (how==4){
						if (which>12 && which<30){
							ObjectProperty provideMapping = KAON2Manager.factory().objectProperty(pOMVURI + "#"+Constants.provideMapping);
							Individual peerIndiv = mOyster2.getLocalAdvertInformer().getLocalPeer();
							if(targetRegistry.containsAxiom(KAON2Manager.factory().objectPropertyMember(provideMapping,peerIndiv,oIndividual),true))
								changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(provideMapping,peerIndiv,oIndividual),OntologyChangeEvent.ChangeType.REMOVE));
						}
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(oConcept,oIndividual),OntologyChangeEvent.ChangeType.REMOVE));
					}
					if (changes.size()>0){ 
						targetRegistry.applyChanges(changes);
						targetRegistry.persist();
						if (registry==null && localRegistryFile!=null) targetRegistry.saveOntology(OntologyFileFormat.OWL_RDF,localRegistryFile,"ISO-8859-1");
					}
					changes.clear();
					if (how==4){
						return true;
					}
				}
				
			}
			else changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(oConcept,oIndividual),OntologyChangeEvent.ChangeType.ADD));
			Ontology resourceTypeOntology = mOyster2.getTypeOntology();
			Ontology resourceMappingOntology=null;
			Ontology resourceChangeOntology=null;
			Ontology resourceOWLODMOntology=null;
			Ontology resourceWorkflowOntology=null;
			ObjectProperty mappingLocation = null;
	    	ObjectProperty provideMapping = null;
			if (which>12 && which<30) {
				resourceMappingOntology=mOyster2.getMappingOntology();
				mappingLocation = KAON2Manager.factory().objectProperty(pOMVURI + "#"+Constants.mappingOMVLocation);
		    	provideMapping = KAON2Manager.factory().objectProperty(pOMVURI + "#"+Constants.provideMapping);
			}
			else if (which>=30 && which<50) {
				resourceChangeOntology=mOyster2.getChangeOntology();
			}
			else if (which>=50 && which<60) {
				resourceOWLODMOntology=mOyster2.getOWLODMOntology();
			}
			else if (which>=60 && which<70) {
				resourceWorkflowOntology=mOyster2.getWorkflowOntology();
			}
			else if (which>=70 && which<80) {
				resourceOWLODMOntology=mOyster2.getOWLODMOntology();
			}
			else if (which>=80) {
				resourceOWLODMOntology=mOyster2.getOWLODMOntology();
			}
			Iterator it2 = propList.iterator();
			while(it2.hasNext()){
				OntologyProperty prop = (OntologyProperty)it2.next();
				//System.out.println(prop.getPropertyName());
				Boolean whatIs = checkDataProperty(prop.getPropertyName());
				if (which >=70){ 
					if (prop.getPropertyName().equalsIgnoreCase("value")) whatIs=false; //value is defined as dataproperty in mapping ontology and objectproperty in owlodmontology
					if (prop.getPropertyName().equalsIgnoreCase("constant")) whatIs=true; //constant is not defined as dataproperty in owlodm ontology
				}
				if (whatIs){
					DataProperty ontologyDataProperty=null;
					if (which<13)ontologyDataProperty = KAON2Manager.factory().dataProperty(Constants.OMVURI + prop.getPropertyName());
					else if (which<30) ontologyDataProperty = KAON2Manager.factory().dataProperty(Constants.MOMVURI + prop.getPropertyName());
					else if (which<50) ontologyDataProperty = KAON2Manager.factory().dataProperty(Constants.CHANGEURI + prop.getPropertyName());
					else if (which<60) ontologyDataProperty = KAON2Manager.factory().dataProperty(Constants.OWLODMURI + prop.getPropertyName());
					else if (which<70) ontologyDataProperty = KAON2Manager.factory().dataProperty(Constants.WORKFLOWURI + prop.getPropertyName());
					else if (which<80) ontologyDataProperty = KAON2Manager.factory().dataProperty(Constants.OWLODMURI + prop.getPropertyName());
					else ontologyDataProperty = KAON2Manager.factory().dataProperty(Constants.OWLODMURI + prop.getPropertyName());
					
					String pValue = prop.getPropertyValue();
					int dType;
					if (which>12 && which<30) dType = org.neontoolkit.registry.util.Utilities.getDType(prop.getPropertyName(),resourceMappingOntology,Constants.MOMVURI);
					else if (which>=30 && which<50) dType = org.neontoolkit.registry.util.Utilities.getDType(prop.getPropertyName(),resourceChangeOntology,Constants.CHANGEURI); //DataProperties are only defined in generic change ontology
					else if (which>=50 && which<60) dType = org.neontoolkit.registry.util.Utilities.getDType(prop.getPropertyName(),resourceOWLODMOntology,Constants.OWLODMURI);
					else if (which>=60 && which<70) dType = org.neontoolkit.registry.util.Utilities.getDType(prop.getPropertyName(),resourceWorkflowOntology,Constants.WORKFLOWURI);
					else if (which>=70 && which<80) dType = org.neontoolkit.registry.util.Utilities.getDType(prop.getPropertyName(),resourceOWLODMOntology,Constants.OWLODMURI);
					else if (which>=80) dType = org.neontoolkit.registry.util.Utilities.getDType(prop.getPropertyName(),resourceOWLODMOntology,Constants.OWLODMURI);
					else dType = org.neontoolkit.registry.util.Utilities.getDType(prop.getPropertyName(),resourceTypeOntology,Constants.OMVURI);
					String oldValue = org.neontoolkit.registry.util.Utilities.getString(oIndividual.getDataPropertyValue(targetRegistry,ontologyDataProperty)); //(String)
					if (dType==org.neontoolkit.registry.util.Utilities.STRING_TYPE ){
					  //if (which<30 || (which>=30 && !prop.getPropertyName().equalsIgnoreCase("URI") && !prop.getPropertyName().equalsIgnoreCase("name"))){
						if (org.neontoolkit.registry.util.Utilities.multipleO(prop.getPropertyName())){ //Manage multiple string values for data properties
							if(!targetRegistry.containsAxiom(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,oIndividual,KAON2Manager.factory().constant(pValue)),true)){
								changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,oIndividual,KAON2Manager.factory().constant(pValue)),OntologyChangeEvent.ChangeType.ADD));
							}
						}
						else{	
							if(oldValue !=null)changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,oIndividual,KAON2Manager.factory().constant(oldValue)),OntologyChangeEvent.ChangeType.REMOVE));
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,oIndividual,KAON2Manager.factory().constant(pValue)),OntologyChangeEvent.ChangeType.ADD));
						}
					  //}
					}
					else if (dType==org.neontoolkit.registry.util.Utilities.INTEGER_TYPE){	
						if(oldValue !=null)changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,oIndividual,KAON2Manager.factory().constant(oIndividual.getDataPropertyValue(targetRegistry,ontologyDataProperty).getValue())),OntologyChangeEvent.ChangeType.REMOVE));
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,oIndividual,KAON2Manager.factory().constant(new Integer(pValue))),OntologyChangeEvent.ChangeType.ADD));
					}
					else if (dType==org.neontoolkit.registry.util.Utilities.FLOAT_TYPE){	
						if(oldValue !=null)changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,oIndividual,KAON2Manager.factory().constant(oIndividual.getDataPropertyValue(targetRegistry,ontologyDataProperty).getValue())),OntologyChangeEvent.ChangeType.REMOVE));
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,oIndividual,KAON2Manager.factory().constant(new Float(pValue))),OntologyChangeEvent.ChangeType.ADD));
					}
					else if (dType==org.neontoolkit.registry.util.Utilities.BOOLEAN_TYPE){	
						if(oldValue !=null)changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,oIndividual,KAON2Manager.factory().constant(oIndividual.getDataPropertyValue(targetRegistry,ontologyDataProperty).getValue())),OntologyChangeEvent.ChangeType.REMOVE));
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,oIndividual,KAON2Manager.factory().constant(new Boolean(pValue))),OntologyChangeEvent.ChangeType.ADD));
					}
					else if (which>=30 && prop.getPropertyName().equalsIgnoreCase("URI")){ //URI NOT DEFINED FOR OWLCHANGES & AXIOMS & WORKFLOW & DECLARATIONS & OWLENTITY IN ONTOLOGIES BUT NECESSARY FOR IDENTIFYING OBJECTS
						if(oldValue !=null)changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,oIndividual,KAON2Manager.factory().constant(oldValue)),OntologyChangeEvent.ChangeType.REMOVE));
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,oIndividual,KAON2Manager.factory().constant(pValue)),OntologyChangeEvent.ChangeType.ADD));
					}
					else if (which>=70 && prop.getPropertyName().equalsIgnoreCase("constant")){ //constant NOT DEFINED FOR OWLCHANGES & AXIOMS & WORKFLOW & DECLARATIONS & OWLENTITY IN ONTOLOGIES BUT NECESSARY FOR IDENTIFYING OBJECTS
						if(oldValue !=null)changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,oIndividual,KAON2Manager.factory().constant(oldValue)),OntologyChangeEvent.ChangeType.REMOVE));
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,oIndividual,KAON2Manager.factory().constant(pValue)),OntologyChangeEvent.ChangeType.ADD));
					}
				}
				//0//
				//IMP//
				else if (prop.getPropertyName().equals(Constants.definedBy) || prop.getPropertyName().equals(Constants.specifiedBy) ||
						prop.getPropertyName().equals(Constants.developedBy) || prop.getPropertyName().equals(Constants.hasAffiliatedParty)){					
					String party = prop.getPropertyValue();
					ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.OMVURI + prop.getPropertyName());
					Individual sIndiv = KAON2Manager.factory().individual(localURI+"#"+party);
					// ADD INSTANCE
					OWLClass tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.personConcept);
					if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,sIndiv),true)){
						tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.organisationConcept);
						if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,sIndiv),true)){
							tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.partyConcept);
							if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,sIndiv),true))
									changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,sIndiv),OntologyChangeEvent.ChangeType.ADD));				
						}
					}
					// ADD PROPERTY VALUE
					Map<ObjectProperty,Set<Individual>> check=oIndividual.getObjectPropertyValues(targetRegistry);
					Set<Individual>checkValues=check.get(ontologyObjectProperty);
					if (checkValues==null || !checkValues.contains(sIndiv))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,oIndividual,sIndiv),OntologyChangeEvent.ChangeType.ADD));
					/*8*/	
				}
				else if (prop.getPropertyName().equals(Constants.createsOntology) || prop.getPropertyName().equals(Constants.contributesToOntology)) { 
					String pOValue = prop.getPropertyValue();
					ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.OMVURI + prop.getPropertyName());
					Individual objectPropertyIndividual = KAON2Manager.factory().individual(pOValue);
					/*ADD CONCEPT INSTANCE*/
					String className=ontologyObjectProperty.getRangeDescriptions(resourceTypeOntology).iterator().next().toString();
					if (className!=null){
						OWLClass tempConcept = KAON2Manager.factory().owlClass(className);
						if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),true))
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));				
					}
					/*ADD PROPERTY VALUE*/
					Map<ObjectProperty,Set<Individual>> check=oIndividual.getObjectPropertyValues(targetRegistry);
					Set<Individual>checkValues=check.get(ontologyObjectProperty);
					if (checkValues==null || !checkValues.contains(objectPropertyIndividual))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,oIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
					/*9*/
				}
				else if (prop.getPropertyName().equals(Constants.hasCreator)){
					String party = prop.getPropertyValue();
					ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.MOMVURI + prop.getPropertyName());
					Individual sIndiv = KAON2Manager.factory().individual(localURI+"#"+party);
					// ADD INSTANCE
					OWLClass tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.personConcept);
					if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,sIndiv),true)){
						tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.organisationConcept);
						if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,sIndiv),true)){
							tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.partyConcept);
							if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,sIndiv),true))
									changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,sIndiv),OntologyChangeEvent.ChangeType.ADD));				
						}
					}
					// ADD PROPERTY VALUE
					Map<ObjectProperty,Set<Individual>> check=oIndividual.getObjectPropertyValues(targetRegistry);
					Set<Individual>checkValues=check.get(ontologyObjectProperty);
					if (checkValues==null || !checkValues.contains(sIndiv))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,oIndividual,sIndiv),OntologyChangeEvent.ChangeType.ADD));
					/*10*/
				}
				else if (prop.getPropertyName().equals(Constants.hasAuthor)){
					String party = prop.getPropertyValue();
					ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.CHANGEURI + prop.getPropertyName());
					Individual sIndiv = KAON2Manager.factory().individual(localURI+"#"+party);
					// ADD INSTANCE
					OWLClass tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.personConcept);
					if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,sIndiv),true)){
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,sIndiv),OntologyChangeEvent.ChangeType.ADD));				
					}
					// ADD PROPERTY VALUE
					Map<ObjectProperty,Set<Individual>> check=oIndividual.getObjectPropertyValues(targetRegistry);
					Set<Individual>checkValues=check.get(ontologyObjectProperty);
					if (checkValues==null || !checkValues.contains(sIndiv))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,oIndividual,sIndiv),OntologyChangeEvent.ChangeType.ADD));
					/*10*/
				}
				else if (prop.getPropertyName().equals(Constants.performedBy)){
					String party = prop.getPropertyValue();
					ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.WORKFLOWURI + prop.getPropertyName());
					Individual sIndiv = KAON2Manager.factory().individual(localURI+"#"+party);
					// ADD INSTANCE
					OWLClass tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.personConcept);
					if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,sIndiv),true)){
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,sIndiv),OntologyChangeEvent.ChangeType.ADD));				
					}
					// ADD PROPERTY VALUE
					Map<ObjectProperty,Set<Individual>> check=oIndividual.getObjectPropertyValues(targetRegistry);
					Set<Individual>checkValues=check.get(ontologyObjectProperty);
					if (checkValues==null || !checkValues.contains(sIndiv))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,oIndividual,sIndiv),OntologyChangeEvent.ChangeType.ADD));
					/*10*/
				}
				else if (prop.getPropertyName().equals(Constants.hasSourceOntology) || prop.getPropertyName().equals(Constants.hasTargetOntology)) { 
					String pOValue = prop.getPropertyValue();
					ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.MOMVURI + prop.getPropertyName());
					Individual objectPropertyIndividual = KAON2Manager.factory().individual(pOValue);
					/*ADD CONCEPT INSTANCE*/
					String className=ontologyObjectProperty.getRangeDescriptions(resourceMappingOntology).iterator().next().toString();
					if (className!=null){
						OWLClass tempConcept = KAON2Manager.factory().owlClass(className);
						if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),true))
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));				
					}
					/*ADD PROPERTY VALUE*/
					Map<ObjectProperty,Set<Individual>> check=oIndividual.getObjectPropertyValues(targetRegistry);
					Set<Individual>checkValues=check.get(ontologyObjectProperty);
					if (checkValues==null || !checkValues.contains(objectPropertyIndividual))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,oIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
					/*11*/
				}
				else if (prop.getPropertyName().equals(Constants.appliedToOntology) || prop.getPropertyName().equals(Constants.hasRelatedOntology)
						|| prop.getPropertyName().equals(Constants.changeFromVersion) || prop.getPropertyName().equals(Constants.changeToVersion)) { 
					String pOValue = prop.getPropertyValue();
					ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.CHANGEURI + prop.getPropertyName());
					Individual objectPropertyIndividual = KAON2Manager.factory().individual(pOValue);
					/*ADD CONCEPT INSTANCE*/
					String className=ontologyObjectProperty.getRangeDescriptions(resourceChangeOntology).iterator().next().toString();
					if (className!=null){
						OWLClass tempConcept = KAON2Manager.factory().owlClass(className);
						if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),true))
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));				
					}
					/*ADD PROPERTY VALUE*/
					Map<ObjectProperty,Set<Individual>> check=oIndividual.getObjectPropertyValues(targetRegistry);
					Set<Individual>checkValues=check.get(ontologyObjectProperty);
					if (checkValues==null || !checkValues.contains(objectPropertyIndividual))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,oIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
					/*11*/
				}
				else if (prop.getPropertyName().equals(Constants.relatedOntology)) { 
					String pOValue = prop.getPropertyValue();
					ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.WORKFLOWURI + prop.getPropertyName());
					Individual objectPropertyIndividual = KAON2Manager.factory().individual(pOValue);
					/*ADD CONCEPT INSTANCE*/
					String className=ontologyObjectProperty.getRangeDescriptions(resourceWorkflowOntology).iterator().next().toString();
					if (className!=null){
						OWLClass tempConcept = KAON2Manager.factory().owlClass(className);
						if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),true))
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));				
					}
					/*ADD PROPERTY VALUE*/
					Map<ObjectProperty,Set<Individual>> check=oIndividual.getObjectPropertyValues(targetRegistry);
					Set<Individual>checkValues=check.get(ontologyObjectProperty);
					if (checkValues==null || !checkValues.contains(objectPropertyIndividual))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,oIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
					/*11*/
				}
				else if (prop.getPropertyName().equalsIgnoreCase(Constants.hasEntityState)) { //Special case: hasEntityState is defined in workflow ontology but its domain is EntityChange
					String pOValue = prop.getPropertyValue();
					ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.WORKFLOWURI + prop.getPropertyName());
					Individual objectPropertyIndividual = KAON2Manager.factory().individual(pOValue);
					/*ADD CONCEPT INSTANCE*/
					/*
					String className=ontologyObjectProperty.getRangeDescriptions(resourceOWLODMOntology).iterator().next().toString();
					if (className!=null){
						OWLClass tempConcept = KAON2Manager.factory().owlClass(className);
						if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),true))
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));				
					}
					*/
					/*ADD PROPERTY VALUE*/
					Map<ObjectProperty,Set<Individual>> check=oIndividual.getObjectPropertyValues(targetRegistry);
					Set<Individual>checkValues=check.get(ontologyObjectProperty);
					if (checkValues==null || !checkValues.contains(objectPropertyIndividual))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,oIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
				}
				else if (prop.getPropertyName().equalsIgnoreCase(Constants.hasRole)) { //Special case: hasRole is defined in workflow ontology but its domain is OMVPerson
					String pOValue = prop.getPropertyValue();
					ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.WORKFLOWURI + prop.getPropertyName());
					Individual objectPropertyIndividual = KAON2Manager.factory().individual(pOValue);
					/*ADD CONCEPT INSTANCE*/
					/*
					String className=ontologyObjectProperty.getRangeDescriptions(resourceOWLODMOntology).iterator().next().toString();
					if (className!=null){
						OWLClass tempConcept = KAON2Manager.factory().owlClass(className);
						if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),true))
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));				
					}
					*/
					/*ADD PROPERTY VALUE*/
					Map<ObjectProperty,Set<Individual>> check=oIndividual.getObjectPropertyValues(targetRegistry);
					Set<Individual>checkValues=check.get(ontologyObjectProperty);
					if (checkValues==null || !checkValues.contains(objectPropertyIndividual))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,oIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
				}
				else if (which<=12){//OMVCore 
					String pOValue = prop.getPropertyValue();
					ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.OMVURI + prop.getPropertyName());
					Individual objectPropertyIndividual = KAON2Manager.factory().individual(localURI+"#"+pOValue);
					/*ADD CONCEPT INSTANCE*/
					String className=ontologyObjectProperty.getRangeDescriptions(resourceTypeOntology).iterator().next().toString();
					if (className!=null){
						OWLClass tempConcept = KAON2Manager.factory().owlClass(className);
						if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),true))
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));				
					}
					/*ADD PROPERTY VALUE*/
					Map<ObjectProperty,Set<Individual>> check=oIndividual.getObjectPropertyValues(targetRegistry);
					Set<Individual>checkValues=check.get(ontologyObjectProperty);
					if (checkValues==null || !checkValues.contains(objectPropertyIndividual))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,oIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
					/*12*/
				}
				else if (which<30){//mappings 
					String pOValue = prop.getPropertyValue();
					ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.MOMVURI + prop.getPropertyName());
					Individual objectPropertyIndividual = KAON2Manager.factory().individual(localURI+"#"+pOValue);
					/*ADD CONCEPT INSTANCE*/
					String className=ontologyObjectProperty.getRangeDescriptions(resourceMappingOntology).iterator().next().toString();
					if (className!=null){
						OWLClass tempConcept = KAON2Manager.factory().owlClass(className);
						if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),true))
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));				
					}
					/*ADD PROPERTY VALUE*/
					Map<ObjectProperty,Set<Individual>> check=oIndividual.getObjectPropertyValues(targetRegistry);
					Set<Individual>checkValues=check.get(ontologyObjectProperty);
					if (checkValues==null || !checkValues.contains(objectPropertyIndividual))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,oIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
					/*13*/
				}
				else if (which<50){//change
					String pOValue = prop.getPropertyValue();
					Individual objectPropertyIndividual = KAON2Manager.factory().individual(pOValue);
					ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.CHANGEURI + prop.getPropertyName());
					/*ADD CONCEPT INSTANCE*/
					/*
					Set<Description> mSet=ontologyObjectProperty.getRangeDescriptions(resourceChangeOntology); //ObjectProperties are specialized in OWLChange ontology
					if (mSet.size()<=0) mSet=ontologyObjectProperty.getRangeDescriptions(resourceOWLChangeOntology);
					String className=mSet.iterator().next().toString();
					if (className!=null){
						OWLClass tempConcept = KAON2Manager.factory().owlClass(className);
						if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),true))
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));				
					}
					*/
					/*ADD PROPERTY VALUE*/
					Map<ObjectProperty,Set<Individual>> check=oIndividual.getObjectPropertyValues(targetRegistry);
					Set<Individual>checkValues=check.get(ontologyObjectProperty);
					if (checkValues==null || !checkValues.contains(objectPropertyIndividual))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,oIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
				}
				else if (which<60){//axioms
					String pOValue = prop.getPropertyValue();
					ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.OWLODMURI + prop.getPropertyName());
					Individual objectPropertyIndividual = KAON2Manager.factory().individual(pOValue);
					/*ADD CONCEPT INSTANCE*/
					
					/* OWLODM does not have the ranges defined
					String className=ontologyObjectProperty.getRangeDescriptions(resourceOWLODMOntology).iterator().next().toString();
					if (className!=null){
						OWLClass tempConcept = KAON2Manager.factory().owlClass(className);
						if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),true))
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));				
					}
					*/
					/*ADD PROPERTY VALUE*/
					Map<ObjectProperty,Set<Individual>> check=oIndividual.getObjectPropertyValues(targetRegistry);
					Set<Individual>checkValues=check.get(ontologyObjectProperty);
					if (checkValues==null || !checkValues.contains(objectPropertyIndividual))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,oIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
				}
				else if (which<70){//workflow
					String pOValue = prop.getPropertyValue();
					ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.WORKFLOWURI + prop.getPropertyName());
					Individual objectPropertyIndividual = KAON2Manager.factory().individual(pOValue);
					/*ADD CONCEPT INSTANCE*/
					
					/*
					String className=ontologyObjectProperty.getRangeDescriptions(resourceOWLODMOntology).iterator().next().toString();
					if (className!=null){
						OWLClass tempConcept = KAON2Manager.factory().owlClass(className);
						if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),true))
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));				
					}
					*/
					/*ADD PROPERTY VALUE*/
					Map<ObjectProperty,Set<Individual>> check=oIndividual.getObjectPropertyValues(targetRegistry);
					Set<Individual>checkValues=check.get(ontologyObjectProperty);
					if (checkValues==null || !checkValues.contains(objectPropertyIndividual))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,oIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
				}
				else if (which<80){//description
					String pOValue = prop.getPropertyValue();
					ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.OWLODMURI + prop.getPropertyName());
					Individual objectPropertyIndividual = KAON2Manager.factory().individual(pOValue);
					/*ADD CONCEPT INSTANCE*/
					
					/* OWLODM does not have the ranges defined
					String className=ontologyObjectProperty.getRangeDescriptions(resourceOWLODMOntology).iterator().next().toString();
					if (className!=null){
						OWLClass tempConcept = KAON2Manager.factory().owlClass(className);
						if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),true))
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));				
					}
					*/
					/*ADD PROPERTY VALUE*/
					Map<ObjectProperty,Set<Individual>> check=oIndividual.getObjectPropertyValues(targetRegistry);
					Set<Individual>checkValues=check.get(ontologyObjectProperty);
					if (checkValues==null || !checkValues.contains(objectPropertyIndividual))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,oIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
				}
				else {//OWLEntity
					String pOValue = prop.getPropertyValue();
					ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.OWLODMURI + prop.getPropertyName());
					Individual objectPropertyIndividual = KAON2Manager.factory().individual(pOValue);
					/*ADD CONCEPT INSTANCE*/
					
					/* OWLODM does not have the ranges defined
					String className=ontologyObjectProperty.getRangeDescriptions(resourceOWLODMOntology).iterator().next().toString();
					if (className!=null){
						OWLClass tempConcept = KAON2Manager.factory().owlClass(className);
						if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),true))
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));				
					}
					*/
					/*ADD PROPERTY VALUE*/
					Map<ObjectProperty,Set<Individual>> check=oIndividual.getObjectPropertyValues(targetRegistry);
					Set<Individual>checkValues=check.get(ontologyObjectProperty);
					if (checkValues==null || !checkValues.contains(objectPropertyIndividual))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,oIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
				}
			}
			if (which==13){
				Individual peerIndiv = mOyster2.getLocalAdvertInformer().getLocalPeer();
				if(!targetRegistry.containsAxiom(KAON2Manager.factory().objectPropertyMember(mappingLocation,oIndividual,peerIndiv),true))
					changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(mappingLocation,oIndividual,peerIndiv),OntologyChangeEvent.ChangeType.ADD));	
				if(!targetRegistry.containsAxiom(KAON2Manager.factory().objectPropertyMember(provideMapping,peerIndiv,oIndividual),true))
					changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(provideMapping,peerIndiv,oIndividual),OntologyChangeEvent.ChangeType.ADD));
			}
			targetRegistry.applyChanges(changes);
			targetRegistry.persist();
			if (registry==null && localRegistryFile!=null) targetRegistry.saveOntology(OntologyFileFormat.OWL_RDF,localRegistryFile,"ISO-8859-1");
			return true;
			//mInformer.updateRegistryVersion(targetRegistry);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Adds/modify/remove an ontology metadata entry in the registry.
	 * @param properties is the set of properties (OMV properties)  
	 * that has the ontology metadata entry we want to register.
	 * @param what is the adding mode. It works as follows:
	 * 0=Merge: The properties specified are added for multiple values
	 * properties (if the value do not exists already) and replaced 
	 * for single value properties.
	 * 1=Pure Register: If the ontology metadata entry exists already 
	 * in the registry the method returns.
	 * 2=Replace: Same as remove and add object i.e. all existing 
	 * properties are removed and the new set of properties are added
	 * without deleting the object
	 * 3=Special Register: Same as 0 i.e. merge, but do not add the 
	 * objectProperties ontologyLocation, provideOntology and hasExpertise to the 
	 * omv:Ontology and omv-p:Peer concepts. This allows us to add 
	 * referenced ontologies by e.g. useImports, hasPriorVersion, etc.
	 * for which we do not have any additional information like when
	 * importing and extracting metadata from an ontology.
	 * 4=Remove: Remove the ontology metadata entry
	 * 5=1 (Pure Register) but displays message 
	 * @param registry the targetOntology to apply axioms. Null=localRegistry
	 * 
	 */
	public boolean addImportOntologyToRegistry(List properties, int what, Ontology registry){
		Ontology targetRegistry;
		if (registry!=null) targetRegistry=registry;
		else targetRegistry = localRegistry;
		
		List propList = new LinkedList();
		propList.clear();
		propList=properties;
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		OWLClass ontologyConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.ontologyConcept);
    	ObjectProperty hasDomain = KAON2Manager.factory().objectProperty(Constants.OMVURI + Constants.hasDomain);
		ObjectProperty ontologyOMVLocation = KAON2Manager.factory().objectProperty(pOMVURI + "#"+Constants.ontologyOMVLocation);
    	ObjectProperty provideOntology = KAON2Manager.factory().objectProperty(pOMVURI + "#"+Constants.provideOntology);
    	
    	//Manage composite OMV identification. Before it was the same as Ontology URI
		Individual ontologyIndividual = null;
		String uri = ""; 
		Boolean hasVersion = false;
		Iterator it1 = propList.iterator();
		while(it1.hasNext()){
			OntologyProperty prop = (OntologyProperty)it1.next();
			if(prop.getPropertyName().equals(Constants.URI)){
				//uri = prop.getPropertyValue();
				String uri1 = prop.getPropertyValue();  //For using composite OMV identification
				uri=uri+uri1;
				break;
			}
		}
		
		Iterator it1a = propList.iterator();
		while(it1a.hasNext()){
			OntologyProperty prop = (OntologyProperty)it1a.next();
			if(prop.getPropertyName().equals(Constants.version)){
				String uri2 = prop.getPropertyValue();
				uri=uri+"?version="+uri2;//+";";
				hasVersion = true;
				break;
			}
		}
		Iterator it1b = propList.iterator();
		while(it1b.hasNext()){
			OntologyProperty prop = (OntologyProperty)it1b.next();
			if(prop.getPropertyName().equals(Constants.resourceLocator)){
				String uri3 = prop.getPropertyValue();
				if (!hasVersion) uri=uri+"?";
				else uri=uri+";";
				uri=uri+"location="+uri3;
				break;
			}
		}
		
		uri=uri.replace(" ", "_");
		
		
		/*6*/
		ontologyIndividual = KAON2Manager.factory().individual(uri);
		try{	
			if (targetRegistry.containsAxiom(KAON2Manager.factory().classMember(ontologyConcept,ontologyIndividual),true)){      //(targetRegistry.containsEntity(ontologyIndividual,true)){
				if (what==0)
					mOyster2.getLogger().info("The importing ontology already exist in the local expertise registry");
				else if (what==1 || what==5){
					if (what==5) System.out.println("Please refer to the registry file! The registering ontology already exist. Please use method Replace instead!");
					return false;
				}
				else if (what==2 || what==4){
					Map dataPropertyMap = ontologyIndividual.getDataPropertyValues(targetRegistry);
					Map objectPropertyMap = ontologyIndividual.getObjectPropertyValues(targetRegistry);
					if ((dataPropertyMap.size()+objectPropertyMap.size())>0){
						Collection keySet = dataPropertyMap.keySet();
						Iterator keys = keySet.iterator();
						while(keys.hasNext()){
							String keyStr = keys.next().toString();
							DataProperty property = KAON2Manager.factory().dataProperty(keyStr);
							//Handle multiple values for data properties. (before /*1*/) 
							//For now only multiple string values are supported.
							//TODO: Support multiple values for datatypes different from string						
							Collection propertyCol= new LinkedList();
							propertyCol = (Collection)dataPropertyMap.get(property);
							if(propertyCol==null)System.err.println("datapropertyCol is null");
							Iterator itInt = propertyCol.iterator();
							while(itInt.hasNext()){
								Object propertyObject =(Object) itInt.next();
								String	propertyValue = org.neontoolkit.registry.util.Utilities.getString(propertyObject);
								Boolean isI = org.neontoolkit.registry.util.Utilities.isInt(Namespaces.guessLocalName(property.getURI()));
								if (!isI){
									Boolean isB = org.neontoolkit.registry.util.Utilities.isBool(Namespaces.guessLocalName(property.getURI()));
									if (!isB){
										changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(property,ontologyIndividual,KAON2Manager.factory().constant(propertyValue)),OntologyChangeEvent.ChangeType.REMOVE)); //multiple string as we use propertyValue
									}
									else{
										changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(property,ontologyIndividual,KAON2Manager.factory().constant(ontologyIndividual.getDataPropertyValue(targetRegistry,property).getValue())),OntologyChangeEvent.ChangeType.REMOVE)); //assuming one value (for boolean is ok)
									}
								}
								else
									changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(property,ontologyIndividual,KAON2Manager.factory().constant(ontologyIndividual.getDataPropertyValue(targetRegistry,property).getValue())),OntologyChangeEvent.ChangeType.REMOVE)); //assuming one value (here not necessarly good)
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
								Individual propertyValue =(Individual) itInt.next();
								changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(property,ontologyIndividual,propertyValue),OntologyChangeEvent.ChangeType.REMOVE));
							}
						}
					}
					if (what==4){
						Individual peerIndiv = mOyster2.getLocalAdvertInformer().getLocalPeer();
						if(targetRegistry.containsAxiom(KAON2Manager.factory().objectPropertyMember(provideOntology,peerIndiv,ontologyIndividual),true))
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(provideOntology,peerIndiv,ontologyIndividual),OntologyChangeEvent.ChangeType.REMOVE));
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(ontologyConcept,ontologyIndividual),OntologyChangeEvent.ChangeType.REMOVE));
					}
					if (changes.size()>0){ 
						targetRegistry.applyChanges(changes);
						targetRegistry.persist();
						if (registry==null && localRegistryFile!=null) targetRegistry.saveOntology(OntologyFileFormat.OWL_RDF,localRegistryFile,"ISO-8859-1");
					}
					changes.clear();
					if (what==4){
						return true;
					}
					//changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(ontologyConcept,ontologyIndividual),OntologyChangeEvent.ChangeType.ADD));
				}
			}
			else changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(ontologyConcept,ontologyIndividual),OntologyChangeEvent.ChangeType.ADD));
			
			//TIMESTAMP
			DataProperty oProperty = KAON2Manager.factory().dataProperty(Constants.OMVURI + Constants.timeStamp);
			Date now = new Date();
			String sNow = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).format(now);
			
			String preValue = org.neontoolkit.registry.util.Utilities.getString(ontologyIndividual.getDataPropertyValue(targetRegistry,oProperty)); //(String)
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
					DataProperty ontologyDataProperty;
					ontologyDataProperty= KAON2Manager.factory().dataProperty(Constants.OMVURI + prop.getPropertyName());
					Boolean isI = org.neontoolkit.registry.util.Utilities.isInt(prop.getPropertyName());
					String pValue = prop.getPropertyValue();
					if (!isI){
						Boolean isB = org.neontoolkit.registry.util.Utilities.isBool(prop.getPropertyName());
						if (!isB){
							if (org.neontoolkit.registry.util.Utilities.multiple(prop.getPropertyName())){ //Manage multiple string values for data properties
								if(!targetRegistry.containsAxiom(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,ontologyIndividual,KAON2Manager.factory().constant(pValue)),true)){
									changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,ontologyIndividual,KAON2Manager.factory().constant(pValue)),OntologyChangeEvent.ChangeType.ADD));
								}
							}
							else{
								String oldValue = org.neontoolkit.registry.util.Utilities.getString(ontologyIndividual.getDataPropertyValue(targetRegistry,ontologyDataProperty)); //(String)
								if(oldValue !=null)changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,ontologyIndividual,KAON2Manager.factory().constant(oldValue)),OntologyChangeEvent.ChangeType.REMOVE));
								changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,ontologyIndividual,KAON2Manager.factory().constant(pValue)),OntologyChangeEvent.ChangeType.ADD));
							}
						}
						else{
							String oldValue = org.neontoolkit.registry.util.Utilities.getString(ontologyIndividual.getDataPropertyValue(targetRegistry,ontologyDataProperty)); //(String)
							if(oldValue !=null)changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,ontologyIndividual,KAON2Manager.factory().constant(ontologyIndividual.getDataPropertyValue(targetRegistry,ontologyDataProperty).getValue())),OntologyChangeEvent.ChangeType.REMOVE));
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,ontologyIndividual,KAON2Manager.factory().constant(java.lang.Boolean.parseBoolean(pValue))),OntologyChangeEvent.ChangeType.ADD));
						}
					}
					else{
						String oldValue = org.neontoolkit.registry.util.Utilities.getString(ontologyIndividual.getDataPropertyValue(targetRegistry,ontologyDataProperty)); //(String)
						if(oldValue !=null)changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,ontologyIndividual,KAON2Manager.factory().constant(ontologyIndividual.getDataPropertyValue(targetRegistry,ontologyDataProperty).getValue())),OntologyChangeEvent.ChangeType.REMOVE));
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().dataPropertyMember(ontologyDataProperty,ontologyIndividual,KAON2Manager.factory().constant(new Integer(pValue))),OntologyChangeEvent.ChangeType.ADD));
					}
				}
				//0//
				//IMP//
				else if (prop.getPropertyName().equals(Constants.hasDomain)){					
					String domain = prop.getPropertyValue();
					//if(!domain.contains(Constants.TopicsURI))domain = Constants.TopicsURI+domain;
					if(!domain.contains("://"))domain = Constants.TopicsURI+domain;  //Add namespace if not present
					Individual subjectIndiv = KAON2Manager.factory().individual(domain);
					// SHOULD WE ADD ONTOLOGY DOMAIN INSTANCE? YES
					String className=hasDomain.getRangeDescriptions(resourceTypeOntology).iterator().next().toString();
					if (className!=null){
						OWLClass tempConcept = KAON2Manager.factory().owlClass(className);
						if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,subjectIndiv),true))
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,subjectIndiv),OntologyChangeEvent.ChangeType.ADD));				
					}
					// ADD PROPERTY VALUE
					Map<ObjectProperty,Set<Individual>> check=ontologyIndividual.getObjectPropertyValues(targetRegistry);
					Set<Individual>checkValues=check.get(hasDomain);
					if (checkValues==null || !checkValues.contains(subjectIndiv))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(hasDomain,ontologyIndividual,subjectIndiv),OntologyChangeEvent.ChangeType.ADD));
					/*2*/
					// ADD HASEXPERTISE TO THE PEER
					if (what!=3){
						ObjectProperty hasExpertise = KAON2Manager.factory().objectProperty(pOMVURI + "#"+Constants.hasExpertise);
						Individual peerIndiv = mOyster2.getLocalAdvertInformer().getLocalPeer();
						if(!targetRegistry.containsAxiom(KAON2Manager.factory().objectPropertyMember(hasExpertise,peerIndiv,subjectIndiv),true))
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(hasExpertise,peerIndiv,subjectIndiv),OntologyChangeEvent.ChangeType.ADD));
					}
				}
				else if (prop.getPropertyName().equals(Constants.hasCreator) || prop.getPropertyName().equals(Constants.hasContributor) || prop.getPropertyName().equals(Constants.endorsedBy) ){					
					String party = prop.getPropertyValue();
					ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.OMVURI + prop.getPropertyName());
					Individual sIndiv = KAON2Manager.factory().individual(localURI+"#"+party);
					// ADD INSTANCE
					OWLClass tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.personConcept);
					if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,sIndiv),true)){
						tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.organisationConcept);
						if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,sIndiv),true)){
							tempConcept = KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.partyConcept);
							if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,sIndiv),true))
									changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,sIndiv),OntologyChangeEvent.ChangeType.ADD));				
						}
					}
					// ADD PROPERTY VALUE
					Map<ObjectProperty,Set<Individual>> check=ontologyIndividual.getObjectPropertyValues(targetRegistry);
					Set<Individual>checkValues=check.get(ontologyObjectProperty);
					if (checkValues==null || !checkValues.contains(sIndiv))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,ontologyIndividual,sIndiv),OntologyChangeEvent.ChangeType.ADD));					
					/*3*/	
				}
				else if (prop.getPropertyName().equals(Constants.useImports) || prop.getPropertyName().equals(Constants.hasPriorVersion) ||
						prop.getPropertyName().equals(Constants.isBackwardCompatibleWith) || prop.getPropertyName().equals(Constants.isIncompatibleWith)){ 
					String pOValue = prop.getPropertyValue();
					ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.OMVURI + prop.getPropertyName());
					Individual objectPropertyIndividual = KAON2Manager.factory().individual(pOValue);
					/*ADD CONCEPT INSTANCE*/
					String className=ontologyObjectProperty.getRangeDescriptions(resourceTypeOntology).iterator().next().toString();
					if (className!=null){
						OWLClass tempConcept = KAON2Manager.factory().owlClass(className);
						if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),true))
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));				
					}
					/*ADD PROPERTY VALUE*/
					Map<ObjectProperty,Set<Individual>> check=ontologyIndividual.getObjectPropertyValues(targetRegistry);
					Set<Individual>checkValues=check.get(ontologyObjectProperty);
					if (checkValues==null || !checkValues.contains(objectPropertyIndividual))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,ontologyIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
					/*4*/
				}
				else if (prop.getPropertyName().equalsIgnoreCase(Constants.hasOntologyState)){ //Special case: hasOntologyState is defined in workflow ontology but its domain is the ontology)
					String pOValue = prop.getPropertyValue();
					ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.WORKFLOWURI + prop.getPropertyName());
					Individual objectPropertyIndividual = KAON2Manager.factory().individual(pOValue);
					/*ADD CONCEPT INSTANCE*/
					/*
					String className=ontologyObjectProperty.getRangeDescriptions(resourceTypeOntology).iterator().next().toString();
					if (className!=null){
						OWLClass tempConcept = KAON2Manager.factory().owlClass(className);
						if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),true))
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));				
					}
					*/
					/*ADD PROPERTY VALUE*/
					Map<ObjectProperty,Set<Individual>> check=ontologyIndividual.getObjectPropertyValues(targetRegistry);
					Set<Individual>checkValues=check.get(ontologyObjectProperty);
					if (checkValues==null || !checkValues.contains(objectPropertyIndividual))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,ontologyIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
				}
				
				else{ 
					String pOValue = prop.getPropertyValue();
					ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.OMVURI + prop.getPropertyName());
					Individual objectPropertyIndividual = KAON2Manager.factory().individual(localURI+"#"+pOValue);
					/*ADD CONCEPT INSTANCE*/
					String className=ontologyObjectProperty.getRangeDescriptions(resourceTypeOntology).iterator().next().toString();
					if (className!=null){
						OWLClass tempConcept = KAON2Manager.factory().owlClass(className);
						if(!targetRegistry.containsAxiom(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),true))
							changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(tempConcept,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));				
					}
					/*ADD PROPERTY VALUE*/
					Map<ObjectProperty,Set<Individual>> check=ontologyIndividual.getObjectPropertyValues(targetRegistry);
					Set<Individual>checkValues=check.get(ontologyObjectProperty);
					if (checkValues==null || !checkValues.contains(objectPropertyIndividual))
						changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyObjectProperty,ontologyIndividual,objectPropertyIndividual),OntologyChangeEvent.ChangeType.ADD));
					/*5*/
				}
			}	
			if (what!=3){
				Individual peerIndiv = mOyster2.getLocalAdvertInformer().getLocalPeer();
				if(!targetRegistry.containsAxiom(KAON2Manager.factory().objectPropertyMember(ontologyOMVLocation,ontologyIndividual,peerIndiv),true))
					changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(ontologyOMVLocation,ontologyIndividual,peerIndiv),OntologyChangeEvent.ChangeType.ADD));	
				if(!targetRegistry.containsAxiom(KAON2Manager.factory().objectPropertyMember(provideOntology,peerIndiv,ontologyIndividual),true))
					changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(provideOntology,peerIndiv,ontologyIndividual),OntologyChangeEvent.ChangeType.ADD));
			}
			targetRegistry.applyChanges(changes);
			targetRegistry.persist();
			if (registry==null && localRegistryFile!=null) targetRegistry.saveOntology(OntologyFileFormat.OWL_RDF,localRegistryFile,"ISO-8859-1");
			return true;
			//mInformer.updateRegistryVersion(targetRegistry);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean checkDataProperty(String propertyName)  {
		Ontology resourceTypeOntology = mOyster2.getTypeOntology();
		try{
	        Request<Entity> entityRequest=resourceTypeOntology.createEntityRequest();
	        Cursor<Entity> cursor=entityRequest.openCursor();
	        while (cursor.hasNext()) {
	            Entity entity=cursor.next();
	            if (entity instanceof DataProperty)
	            	if (propertyName.equalsIgnoreCase(Namespaces.guessLocalName(entity.getURI()))) return true;
	        }
		}
	    catch (KAON2Exception e) {
	    	System.err.println(e + " in checkdataproperty()");
	    }
	    Ontology resourceMappingOntology = mOyster2.getMappingOntology();
		try{
	        Request<Entity> entityRequest=resourceMappingOntology.createEntityRequest();
	        Cursor<Entity> cursor=entityRequest.openCursor();
	        while (cursor.hasNext()) {
	            Entity entity=cursor.next();
	            if (entity instanceof DataProperty)
	            	if (propertyName.equalsIgnoreCase(Namespaces.guessLocalName(entity.getURI()))) return true;
	        }
		}
	    catch (KAON2Exception e) {
	    	System.err.println(e + " in checkdataproperty 2phase()");
	    }
	    Ontology resourceChangeOntology = mOyster2.getChangeOntology();
		try{
	        Request<Entity> entityRequest=resourceChangeOntology.createEntityRequest();
	        Cursor<Entity> cursor=entityRequest.openCursor();
	        while (cursor.hasNext()) {
	            Entity entity=cursor.next();
	            if (entity instanceof DataProperty)
	            	if (propertyName.equalsIgnoreCase(Namespaces.guessLocalName(entity.getURI()))) return true;
	        }
		}
	    catch (Exception e) {
	    	System.err.println(e + " in checkdataproperty 3phase()");
	    }
	    //OWLChangeOntology does not define properties just specialize two of the generic change ontology
	    /*
	    Ontology resourceOWLChangeOntology = mOyster2.getOWLChangeOntology();
		try{
	        Request<Entity> entityRequest=resourceOWLChangeOntology.createEntityRequest();
	        Cursor<Entity> cursor=entityRequest.openCursor();
	        while (cursor.hasNext()) {
	            Entity entity=cursor.next();
	            if (entity instanceof DataProperty)
	            	if (propertyName.equalsIgnoreCase(Namespaces.guessLocalName(entity.getURI()))) return true;
	        }
		}
	    catch (KAON2Exception e) {
	    	System.err.println(e + " in checkdataproperty 4phase()");
	    }
	    */
	    Ontology resourceOWLODMOntology = mOyster2.getOWLODMOntology();
		try{
	        Request<Entity> entityRequest=resourceOWLODMOntology.createEntityRequest();
	        Cursor<Entity> cursor=entityRequest.openCursor();
	        while (cursor.hasNext()) {
	            Entity entity=cursor.next();
	            if (entity instanceof DataProperty)
	            	if (propertyName.equalsIgnoreCase(Namespaces.guessLocalName(entity.getURI()))) return true;
	        }
		}
	    catch (KAON2Exception e) {
	    	System.err.println(e + " in checkdataproperty 5phase()");
	    }
	    if (mOyster2.getWorkflowSupport()){
	    	Ontology resourceWorkflowOntology = mOyster2.getWorkflowOntology();
	    	try{
	    		Request<Entity> entityRequest=resourceWorkflowOntology.createEntityRequest();
	    		Cursor<Entity> cursor=entityRequest.openCursor();
	    		while (cursor.hasNext()) {
	    			Entity entity=cursor.next();
	    			if (entity instanceof DataProperty)
	    				if (propertyName.equalsIgnoreCase(Namespaces.guessLocalName(entity.getURI()))) return true;
	    		}
	    	}
	    	catch (KAON2Exception e) {
	    		System.err.println(e + " in checkdataproperty 6phase()");
	    	}
	    }
	    return false;
	    //X
	}	

	public String getOntologyID(OMVOntology o){
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
	
	

}
