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
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.core.OMVParty;
import org.neontoolkit.omv.api.core.OMVPerson;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLClassChange.DisjointClassChange.AddDisjointClass;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLClassChange.DisjointClassChange.RemoveDisjointClass;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLClassChange.DisjointUnionChange.AddDisjointUnion;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLClassChange.DisjointUnionChange.RemoveDisjointUnion;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLClassChange.EquivalentClassChange.AddEquivalentClass;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLClassChange.EquivalentClassChange.RemoveEquivalentClass;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.DataPropertyDomainChange.AddDataPropertyDomain;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.DataPropertyDomainChange.RemoveDataPropertyDomain;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.DataPropertyRangeChange.AddDataPropertyRange;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.DataPropertyRangeChange.RemoveDataPropertyRange;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.DisjointDataPropertyChange.AddDisjointDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.DisjointDataPropertyChange.RemoveDisjointDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.EquivalentDataPropertyChange.AddEquivalentDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.EquivalentDataPropertyChange.RemoveEquivalentDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.FunctionalDataPropertyChange.AddFunctionalDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.FunctionalDataPropertyChange.RemoveFunctionalDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.SubDataPropertyOfChange.AddSubDataPropertyOf;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLDataPropertyChange.SubDataPropertyOfChange.RemoveSubDataPropertyOf;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange.DifferentIndividualChange.AddDifferentIndividual;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange.DifferentIndividualChange.RemoveDifferentIndividual;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange.IndividualDataPropertyChange.AddIndividualDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange.IndividualDataPropertyChange.RemoveIndividualDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange.IndividualObjectPropertyChange.AddInvidivualObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange.IndividualObjectPropertyChange.RemoveInvidualObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange.SameIndividualChange.AddSameIndividual;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLIndividualChange.SameIndividualChange.RemoveSameIndividual;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.AsymmetricObjectPropertyChange.AddAsymmetricObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.AsymmetricObjectPropertyChange.RemoveAsymmetricObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.DisjointObjectPropertyChange.AddDisjointObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.DisjointObjectPropertyChange.RemoveDisjointObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.EquivalentObjectPropertyChange.AddEquivalentObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.EquivalentObjectPropertyChange.RemoveEquivalentObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.FunctionalObjectPropertyChange.AddFunctionalObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.FunctionalObjectPropertyChange.RemoveFunctionalObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.InverseFunctionalObjectPropertyChange.AddInverseFunctionalObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.InverseFunctionalObjectPropertyChange.RemoveInverseFunctionalObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.InverseObjectPropertyChange.AddInverseObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.InverseObjectPropertyChange.RemoveInverseObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.IrreflexiveObjectPropertyChange.AddIrreflexiveObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.IrreflexiveObjectPropertyChange.RemoveIrreflexiveObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.ObjectPropertyDomainChange.AddObjectPropertyDomain;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.ObjectPropertyDomainChange.RemoveObjectPropertyDomain;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.ObjectPropertyRangeChange.AddObjectPropertyRange;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.ObjectPropertyRangeChange.RemoveObjectPropertyRange;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.ReflexiveObjectPropertyChange.AddReflexiveObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.ReflexiveObjectPropertyChange.RemoveReflexiveObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.SubObjectPropertyOfChange.AddSubObjectPropertyOf;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.SubObjectPropertyOfChange.RemoveSubObjectPropertyOf;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.SymmetricObjectPropertyChange.AddSymmetricObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.SymmetricObjectPropertyChange.RemoveSymmetricObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.TransitiveObjectPropertyChange.AddTransitiveObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLObjectPropertyChange.TransitiveObjectPropertyChange.RemoveTransitiveObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLOntologyChange.AddDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLOntologyChange.AddDatatype;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLOntologyChange.AddObjectProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLOntologyChange.RemoveDataProperty;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLOntologyChange.RemoveDatatype;
import org.neontoolkit.omv.api.extensions.OWLchange.OMVOWLChange.OMVOWLEntityChange.OWLOntologyChange.RemoveObjectProperty;
import org.neontoolkit.omv.api.extensions.change.OMVChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVCompositeChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange.Addition;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange.Removal;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVCompositeChange.AddSubtree;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVCompositeChange.MergeSiblings;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVCompositeChange.MoveClass;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVCompositeChange.MoveSubtree;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVCompositeChange.RemoveSubtree;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVCompositeChange.SplitClass;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVCompositeChange.MoveSiblings.MoveSiblingsDown;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVCompositeChange.MoveSiblings.MoveSiblingsUp;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.AnnotationPropertyChange.CommentChange.AddComment;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.AnnotationPropertyChange.CommentChange.RemoveComment;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.AnnotationPropertyChange.LabelChange.AddLabel;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.AnnotationPropertyChange.LabelChange.RemoveLabel;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.ClassChange.SubClassOfChange.AddSubClassOf;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.ClassChange.SubClassOfChange.RemoveSubClassOf;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.OntologyChange.AddClass;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.OntologyChange.AddIndividual;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.OntologyChange.RemoveClass;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.OntologyChange.RemoveIndividual;
import org.neontoolkit.owlodm.api.Axiom;
import org.neontoolkit.registry.oyster2.Constants;




/**
 * The class ProcessChangeIndividuals provides the methods to
 * create OMV Change objects  
 * representing the result of a query 
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class ProcessChangeIndividuals{
	static LinkedList<Individual> onProcess = new LinkedList<Individual>();
   
    static public OMVChange processChangeIndividual(Individual changeIndividual, String whichClassFull, Ontology ontologySearch){
     OMVChange reply=new OMVChange();
     String whichClass = Namespaces.guessLocalName(whichClassFull);
     
	 if (!onProcess.contains(changeIndividual)){
	  onProcess.add(changeIndividual);  
	  try{
		Map dataPropertyMap = changeIndividual.getDataPropertyValues(ontologySearch);
		Map objectPropertyMap = changeIndividual.getObjectPropertyValues(ontologySearch);
		if ((dataPropertyMap.size()+objectPropertyMap.size())>0){
		
			if (whichClass.equalsIgnoreCase("Addition")) reply = new Addition();
			else if (whichClass.equalsIgnoreCase("Removal")) reply = new Removal();
			else if (whichClass.equalsIgnoreCase("AddSubtree")) reply = new AddSubtree();
			else if (whichClass.equalsIgnoreCase("MergeSiblings"))  reply = new MergeSiblings();
			else if (whichClass.equalsIgnoreCase("MoveSiblingsUp")) reply = new MoveSiblingsUp();
			else if (whichClass.equalsIgnoreCase("MoveSiblingsDown")) reply = new MoveSiblingsDown();
			else if (whichClass.equalsIgnoreCase("MoveSubtree")) reply = new MoveSubtree();
			else if (whichClass.equalsIgnoreCase("RemoveSubtree")) reply = new RemoveSubtree();
			else if (whichClass.equalsIgnoreCase("SplitClass")) reply = new SplitClass();
			else if (whichClass.equalsIgnoreCase("MoveClass")) reply = new MoveClass();
			else if (whichClass.equalsIgnoreCase("AddComment")) reply = new AddComment();
			else if (whichClass.equalsIgnoreCase("RemoveComment")) reply = new RemoveComment();
			else if (whichClass.equalsIgnoreCase("AddLabel")) reply = new AddLabel();
			else if (whichClass.equalsIgnoreCase("RemoveLabel")) reply = new RemoveLabel();
			else if (whichClass.equalsIgnoreCase("AddSubClassOf")) reply = new AddSubClassOf();
			else if (whichClass.equalsIgnoreCase("RemoveSubClassOf")) reply = new RemoveSubClassOf();
			else if (whichClass.equalsIgnoreCase("AddDisjointClass")) reply = new AddDisjointClass();
			else if (whichClass.equalsIgnoreCase("RemoveDisjointClass")) reply = new RemoveDisjointClass();
			else if (whichClass.equalsIgnoreCase("AddEquivalentClass")) reply = new AddEquivalentClass();
			else if (whichClass.equalsIgnoreCase("RemoveEquivalentClass")) reply = new RemoveEquivalentClass();
			else if (whichClass.equalsIgnoreCase("AddDisjointUnion")) reply = new AddDisjointUnion();
			else if (whichClass.equalsIgnoreCase("RemoveDisjointUnion")) reply = new RemoveDisjointUnion();
			else if (whichClass.equalsIgnoreCase("AddSameIndividual")) reply = new AddSameIndividual();
			else if (whichClass.equalsIgnoreCase("RemoveSameIndividual")) reply = new RemoveSameIndividual();
			else if (whichClass.equalsIgnoreCase("AddIndividualDataProperty")) reply = new AddIndividualDataProperty();
			else if (whichClass.equalsIgnoreCase("RemoveIndividualDataProperty")) reply = new RemoveIndividualDataProperty();
			else if (whichClass.equalsIgnoreCase("AddInvidivualObjectProperty")) reply = new AddInvidivualObjectProperty();
			else if (whichClass.equalsIgnoreCase("RemoveInvidualObjectProperty")) reply = new RemoveInvidualObjectProperty();
			else if (whichClass.equalsIgnoreCase("AddDifferentIndividual")) reply = new AddDifferentIndividual();
			else if (whichClass.equalsIgnoreCase("RemoveDifferentIndividual")) reply = new RemoveDifferentIndividual();
			else if (whichClass.equalsIgnoreCase("AddClass")) reply = new AddClass();
			else if (whichClass.equalsIgnoreCase("RemoveClass")) reply = new RemoveClass();
			else if (whichClass.equalsIgnoreCase("AddIndividual")) reply = new AddIndividual();
			else if (whichClass.equalsIgnoreCase("RemoveIndividual")) reply = new RemoveIndividual();
			else if (whichClass.equalsIgnoreCase("AddObjectProperty")) reply = new AddObjectProperty();
			else if (whichClass.equalsIgnoreCase("RemoveObjectProperty")) reply = new RemoveObjectProperty();
			else if (whichClass.equalsIgnoreCase("AddDataProperty")) reply = new AddDataProperty();
			else if (whichClass.equalsIgnoreCase("RemoveDataProperty")) reply = new RemoveDataProperty();
			else if (whichClass.equalsIgnoreCase("AddDatatype")) reply = new AddDatatype();
			else if (whichClass.equalsIgnoreCase("RemoveDatatype")) reply = new RemoveDatatype();
			else if (whichClass.equalsIgnoreCase("AddAsymmetricObjectProperty")) reply = new AddAsymmetricObjectProperty();
			else if (whichClass.equalsIgnoreCase("RemoveAsymmetricObjectProperty")) reply = new RemoveAsymmetricObjectProperty();
			else if (whichClass.equalsIgnoreCase("AddDisjointObjectProperty")) reply = new AddDisjointObjectProperty();
			else if (whichClass.equalsIgnoreCase("RemoveDisjointObjectProperty")) reply = new RemoveDisjointObjectProperty();
			else if (whichClass.equalsIgnoreCase("AddEquivalentObjectProperty")) reply = new AddEquivalentObjectProperty();
			else if (whichClass.equalsIgnoreCase("RemoveEquivalentObjectProperty")) reply = new RemoveEquivalentObjectProperty();
			else if (whichClass.equalsIgnoreCase("AddFunctionalObjectProperty")) reply = new AddFunctionalObjectProperty();
			else if (whichClass.equalsIgnoreCase("RemoveFunctionalObjectProperty")) reply = new RemoveFunctionalObjectProperty();
			else if (whichClass.equalsIgnoreCase("AddInverseFunctionalObjectProperty")) reply = new AddInverseFunctionalObjectProperty();
			else if (whichClass.equalsIgnoreCase("RemoveInverseFunctionalObjectProperty")) reply = new RemoveInverseFunctionalObjectProperty();
			else if (whichClass.equalsIgnoreCase("AddInverseObjectProperty")) reply = new AddInverseObjectProperty();
			else if (whichClass.equalsIgnoreCase("RemoveInverseObjectProperty")) reply = new RemoveInverseObjectProperty();
			else if (whichClass.equalsIgnoreCase("AddIrreflexiveObjectProperty")) reply = new AddIrreflexiveObjectProperty();
			else if (whichClass.equalsIgnoreCase("RemoveIrreflexiveObjectProperty")) reply = new RemoveIrreflexiveObjectProperty();
			else if (whichClass.equalsIgnoreCase("AddObjectPropertyDomain")) reply = new AddObjectPropertyDomain();
			else if (whichClass.equalsIgnoreCase("RemoveObjectPropertyDomain")) reply = new RemoveObjectPropertyDomain();
			else if (whichClass.equalsIgnoreCase("AddObjectPropertyRange")) reply = new AddObjectPropertyRange();
			else if (whichClass.equalsIgnoreCase("RemoveObjectPropertyRange")) reply = new RemoveObjectPropertyRange();
			else if (whichClass.equalsIgnoreCase("AddReflexiveObjectProperty")) reply = new AddReflexiveObjectProperty();
			else if (whichClass.equalsIgnoreCase("RemoveReflexiveObjectProperty")) reply = new RemoveReflexiveObjectProperty();
			else if (whichClass.equalsIgnoreCase("AddSubObjectPropertyOf")) reply = new AddSubObjectPropertyOf();
			else if (whichClass.equalsIgnoreCase("RemoveSubObjectPropertyOf")) reply = new RemoveSubObjectPropertyOf();
			else if (whichClass.equalsIgnoreCase("AddSymmetricObjectProperty")) reply = new AddSymmetricObjectProperty();
			else if (whichClass.equalsIgnoreCase("RemoveSymmetricObjectProperty")) reply = new RemoveSymmetricObjectProperty();
			else if (whichClass.equalsIgnoreCase("AddTransitiveObjectProperty")) reply = new AddTransitiveObjectProperty();
			else if (whichClass.equalsIgnoreCase("RemoveTransitiveObjectProperty")) reply = new RemoveTransitiveObjectProperty();
			else if (whichClass.equalsIgnoreCase("AddDataPropertyDomain")) reply = new AddDataPropertyDomain();
			else if (whichClass.equalsIgnoreCase("RemoveDataPropertyDomain")) reply = new RemoveDataPropertyDomain();
			else if (whichClass.equalsIgnoreCase("AddDataPropertyRange")) reply = new AddDataPropertyRange();
			else if (whichClass.equalsIgnoreCase("RemoveDataPropertyRange")) reply = new RemoveDataPropertyRange();
			else if (whichClass.equalsIgnoreCase("AddDisjointDataProperty")) reply = new AddDisjointDataProperty();
			else if (whichClass.equalsIgnoreCase("RemoveDisjointDataProperty")) reply = new RemoveDisjointDataProperty();
			else if (whichClass.equalsIgnoreCase("AddEquivalentDataProperty")) reply = new AddEquivalentDataProperty();
			else if (whichClass.equalsIgnoreCase("RemoveEquivalentDataProperty")) reply = new RemoveEquivalentDataProperty();
			else if (whichClass.equalsIgnoreCase("AddFunctionalDataProperty")) reply = new AddFunctionalDataProperty();
			else if (whichClass.equalsIgnoreCase("RemoveFunctionalDataProperty")) reply = new RemoveFunctionalDataProperty();
			else if (whichClass.equalsIgnoreCase("AddSubDataPropertyOf")) reply = new AddSubDataPropertyOf();
			else if (whichClass.equalsIgnoreCase("RemoveSubDataPropertyOf")) reply = new RemoveSubDataPropertyOf();
									
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
					
					reply.append(createOMVChange(property.getURI(),propertyValue, ontologySearch));
					if (reply instanceof OMVAtomicChange) ((OMVAtomicChange)reply).append(createOMVAtomicChange(property.getURI(),propertyValue, ontologySearch));
					else if (reply instanceof OMVCompositeChange) ((OMVCompositeChange)reply).append(createOMVCompositeChange(property.getURI(),propertyValue, ontologySearch));
					else if (reply instanceof OMVEntityChange) ((OMVEntityChange)reply).append(createOMVEntityChange(property.getURI(),propertyValue, ontologySearch));
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
					
					reply.append(createOMVChange(property.getURI(),propertyValue.getURI(), ontologySearch));
					if (reply instanceof OMVAtomicChange) ((OMVAtomicChange)reply).append(createOMVAtomicChange(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (reply instanceof OMVCompositeChange) ((OMVCompositeChange)reply).append(createOMVCompositeChange(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (reply instanceof OMVEntityChange) ((OMVEntityChange)reply).append(createOMVEntityChange(property.getURI(),propertyValue.getURI(), ontologySearch));
						
				}	
			}
		}
	  }catch(Exception e){
			e.printStackTrace();
	  }
	  onProcess.remove(changeIndividual);
	  if (reply instanceof OMVAtomicChange || reply instanceof OMVCompositeChange || reply instanceof OMVEntityChange) return reply;
	  else return null;
	 }
	 else{
		reply.setURI(changeIndividual.getURI());
		return reply;
	 }
	}
	
	static private OMVChange createOMVChange(String URI, String value, Ontology ontologySearch){
	  OMVChange changeReply=new OMVChange();
	  try{
		if (URI.equalsIgnoreCase(Constants.CHANGEURI+Constants.URI)) {changeReply.setURI(value);return changeReply;}
		if (URI.equalsIgnoreCase(Constants.CHANGEURI+Constants.cost)) {changeReply.setCost(value);return changeReply;}
		if (URI.equalsIgnoreCase(Constants.CHANGEURI+Constants.date)) {changeReply.setDate(value);return changeReply;}
		if (URI.equalsIgnoreCase(Constants.CHANGEURI+Constants.priority)) {changeReply.setPriority(value);return changeReply;}
		if (URI.equalsIgnoreCase(Constants.CHANGEURI+Constants.relevance)) {changeReply.setRelevance(value);return changeReply;}
		if (URI.equalsIgnoreCase(Constants.CHANGEURI+Constants.version)) {changeReply.setVersion(value);return changeReply;}
		if (URI.equalsIgnoreCase(Constants.CHANGEURI+Constants.time)) {changeReply.setTime(value);return changeReply;}
		if (URI.equalsIgnoreCase(Constants.CHANGEURI+Constants.hasPreviousChange)) {changeReply.setHasPreviousChange(value);return changeReply;}
		if (URI.equalsIgnoreCase(Constants.CHANGEURI+Constants.appliedToOntology)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVOntology oReferencesReply=(OMVOntology)ProcessOMVIndividuals.processIndividual(oIndividual, "ontology", ontologySearch); //oReferences
			if (oReferencesReply==null) {
				oReferencesReply = new OMVOntology();
				oReferencesReply.setURI(value);
			}
			changeReply.setAppliedToOntology(oReferencesReply);
			oReferencesReply = null;
			return changeReply;
		}
		if (URI.equalsIgnoreCase(Constants.CHANGEURI+Constants.hasAuthor)) {
			Individual oIndividual =KAON2Manager.factory().individual(value);
			OMVParty partyReply=(OMVParty)ProcessOMVIndividuals.processIndividual(oIndividual, "party", ontologySearch);
			OMVPerson personReply=(OMVPerson)ProcessOMVIndividuals.processIndividual(oIndividual, "person", ontologySearch);
			if (personReply!=null){
				if (partyReply!=null) {
					OMVPerson personReplyFinal=ProcessOMVIndividuals.copyParty2Person(partyReply,personReply);
					personReply=personReplyFinal;
				}
				changeReply.addHasAuthor(personReply);
				personReply = null;
			}
			partyReply=null;
			return changeReply;
		}
		if (URI.equalsIgnoreCase(Constants.CHANGEURI+Constants.causeChange)) { 		//if (URI.equalsIgnoreCase(Constants.CHANGEURI+Constants.keywords)) {mainOntoReply.setKeywords(value);return mainOntoReply;}
			changeReply.addCauseChange(value);
			return changeReply;
		}
	  }catch(Exception e){
		  e.printStackTrace();
	  }
	  return changeReply;
	}
	
	static private OMVAtomicChange createOMVAtomicChange(String URI, String value, Ontology ontologySearch){
	  OMVAtomicChange changeReply=new OMVAtomicChange();
	  try{
		  if (URI.equalsIgnoreCase(Constants.CHANGEURI+Constants.appliedAxiom)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				Axiom axiomReply=(Axiom)ProcessAxiomIndividuals.processAxiomIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				changeReply.setAppliedAxiom(axiomReply);
				axiomReply = null;
				return changeReply;
		  }
	  }catch(Exception e){
			e.printStackTrace();
	  }
	  return changeReply;
	}
	
	static private OMVCompositeChange createOMVCompositeChange(String URI, String value, Ontology ontologySearch){
		OMVCompositeChange changeReply=new OMVCompositeChange();
		try{
			if (URI.equalsIgnoreCase(Constants.CHANGEURI+Constants.consistsOf)) { 		
				changeReply.addConsistsOf(value);
				return changeReply;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return changeReply;
	}
	
	static private OMVEntityChange createOMVEntityChange(String URI, String value, Ontology ontologySearch){
		OMVEntityChange changeReply=new OMVEntityChange();
		try{
			if (URI.equalsIgnoreCase(Constants.WORKFLOWURI+Constants.hasEntityState)) {changeReply.setHasEntityState(value);return changeReply;}
			if (URI.equalsIgnoreCase(Constants.CHANGEURI+Constants.hasRelatedEntity)) { 		
				changeReply.addHasRelatedEntity(value);
				return changeReply;
			}
			if (URI.equalsIgnoreCase(Constants.CHANGEURI+Constants.consistsOfAtomicOperation)) { 		
				changeReply.addConsistsOfAtomicOperation(value);
				return changeReply;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return changeReply;
	}
}	