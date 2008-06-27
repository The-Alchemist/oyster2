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
import org.neontoolkit.owlodm.api.Axiom;
import org.neontoolkit.owlodm.api.Description;
import org.neontoolkit.owlodm.api.OWLEntity;
import org.neontoolkit.owlodm.api.Axiom.ClassAxiom;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom;
import org.neontoolkit.owlodm.api.Axiom.Declaration;
import org.neontoolkit.owlodm.api.Axiom.EntityAnnotation;
import org.neontoolkit.owlodm.api.Axiom.Fact;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom;
import org.neontoolkit.owlodm.api.Axiom.ClassAxiom.DisjointClasses;
import org.neontoolkit.owlodm.api.Axiom.ClassAxiom.DisjointUnion;
import org.neontoolkit.owlodm.api.Axiom.ClassAxiom.EquivalentClasses;
import org.neontoolkit.owlodm.api.Axiom.ClassAxiom.SubClassOf;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.DataPropertyDomain;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.DataPropertyRange;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.DisjointDataProperties;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.EquivalentDataProperties;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.FunctionalDataProperty;
import org.neontoolkit.owlodm.api.Axiom.DataPropertyAxiom.SubDataPropertyOf;
import org.neontoolkit.owlodm.api.Axiom.Fact.ClassAssertion;
import org.neontoolkit.owlodm.api.Axiom.Fact.DataPropertyAssertion;
import org.neontoolkit.owlodm.api.Axiom.Fact.DifferentIndividuals;
import org.neontoolkit.owlodm.api.Axiom.Fact.NegativeDataPropertyAssertion;
import org.neontoolkit.owlodm.api.Axiom.Fact.NegativeObjectPropertyAssertion;
import org.neontoolkit.owlodm.api.Axiom.Fact.ObjectPropertyAssertion;
import org.neontoolkit.owlodm.api.Axiom.Fact.SameIndividual;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.AsymmetricObjectProperty;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.DisjointObjectProperties;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.EquivalentObjectProperties;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.FunctionalObjectProperty;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.InverseFunctionalObjectProperty;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.InverseObjectProperties;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.IrreflexiveObjectProperty;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.ObjectPropertyDomain;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.ObjectPropertyRange;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.ReflexiveObjectProperty;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.SubObjectPropertyOf;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.SymmetricObjectProperty;
import org.neontoolkit.owlodm.api.Axiom.ObjectPropertyAxiom.TransitiveObjectProperty;
import org.neontoolkit.owlodm.api.OWLEntity.Datatype;
import org.neontoolkit.registry.oyster2.Constants;


/**
 * The class ProcessAxiomIndividuals provides the methods to
 * create Axiom objects  
 * representing the result of a query 
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class ProcessAxiomIndividuals{
	static LinkedList<Individual> onProcess = new LinkedList<Individual>();
   
    static public Axiom processAxiomIndividual(Individual axiomIndividual, String whichClassFull, Ontology ontologySearch){
     String whichClass = Namespaces.guessLocalName(whichClassFull);
     Axiom reply=new Axiom();
    
	 if (!onProcess.contains(axiomIndividual)){
	  onProcess.add(axiomIndividual);  
	  try{
		Map dataPropertyMap = axiomIndividual.getDataPropertyValues(ontologySearch);
		Map objectPropertyMap = axiomIndividual.getObjectPropertyValues(ontologySearch);
		if ((dataPropertyMap.size()+objectPropertyMap.size())>0){
		
			if (whichClass.equalsIgnoreCase("DisjointClasses")) reply = new DisjointClasses();
			else if (whichClass.equalsIgnoreCase("DisjointUnion")) reply = new DisjointUnion();
			else if (whichClass.equalsIgnoreCase("EquivalentClasses")) reply = new EquivalentClasses();
			else if (whichClass.equalsIgnoreCase("SubClassOf")) reply = new SubClassOf();
			else if (whichClass.equalsIgnoreCase("DataPropertyDomain")) reply = new DataPropertyDomain();
			else if (whichClass.equalsIgnoreCase("DataPropertyRange")) reply = new DataPropertyRange();
			else if (whichClass.equalsIgnoreCase("DisjointDataProperties")) reply = new DisjointDataProperties();
			else if (whichClass.equalsIgnoreCase("EquivalentDataProperties")) reply = new EquivalentDataProperties();
			else if (whichClass.equalsIgnoreCase("FunctionalDataProperty")) reply = new FunctionalDataProperty();
			else if (whichClass.equalsIgnoreCase("SubDataPropertyOf")) reply = new SubDataPropertyOf();
			else if (whichClass.equalsIgnoreCase("Declaration")) reply = new Declaration();
			else if (whichClass.equalsIgnoreCase("EntityAnnotation")) reply = new EntityAnnotation();
			else if (whichClass.equalsIgnoreCase("ClassAssertion")) reply = new ClassAssertion();
			else if (whichClass.equalsIgnoreCase("DataPropertyAssertion")) reply = new DataPropertyAssertion();
			else if (whichClass.equalsIgnoreCase("DifferentIndividuals")) reply = new DifferentIndividuals();
			else if (whichClass.equalsIgnoreCase("NegativeDataPropertyAssertion")) reply = new NegativeDataPropertyAssertion();
			else if (whichClass.equalsIgnoreCase("NegativeObjectPropertyAssertion")) reply = new NegativeObjectPropertyAssertion();
			else if (whichClass.equalsIgnoreCase("ObjectPropertyAssertion")) reply = new ObjectPropertyAssertion();
			else if (whichClass.equalsIgnoreCase("SameIndividual")) reply = new SameIndividual();
			else if (whichClass.equalsIgnoreCase("AsymmetricObjectProperty")) reply = new AsymmetricObjectProperty();
			else if (whichClass.equalsIgnoreCase("DisjointObjectProperties")) reply = new DisjointObjectProperties();
			else if (whichClass.equalsIgnoreCase("EquivalentObjectProperties")) reply = new EquivalentObjectProperties();
			else if (whichClass.equalsIgnoreCase("FunctionalObjectProperty")) reply = new FunctionalObjectProperty();
			else if (whichClass.equalsIgnoreCase("InverseFunctionalObjectProperty")) reply = new InverseFunctionalObjectProperty();
			else if (whichClass.equalsIgnoreCase("InverseObjectProperties")) reply = new InverseObjectProperties();
			else if (whichClass.equalsIgnoreCase("IrreflexiveObjectProperty")) reply = new IrreflexiveObjectProperty();
			else if (whichClass.equalsIgnoreCase("ObjectPropertyDomain")) reply = new ObjectPropertyDomain();
			else if (whichClass.equalsIgnoreCase("ObjectPropertyRange")) reply = new ObjectPropertyRange();
			else if (whichClass.equalsIgnoreCase("ReflexiveObjectProperty")) reply = new ReflexiveObjectProperty();
			else if (whichClass.equalsIgnoreCase("SymmetricObjectProperty")) reply = new SymmetricObjectProperty();
			else if (whichClass.equalsIgnoreCase("TransitiveObjectProperty")) reply = new TransitiveObjectProperty();
			else if (whichClass.equalsIgnoreCase("SubObjectPropertyOf")) reply = new SubObjectPropertyOf();
			
						
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
					
					if (property.getURI().equalsIgnoreCase(Constants.OWLODMURI+Constants.URI)) reply.append(createAxiom(property.getURI(),propertyValue, ontologySearch));					
					else if (whichClass.equalsIgnoreCase("DisjointClasses")) ((DisjointClasses)reply).append(createDisjointClasses(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("DisjointUnion")) ((DisjointUnion)reply).append(createDisjointUnion(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("EquivalentClasses")) ((EquivalentClasses)reply).append(createEquivalentClasses(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("SubClassOf")) ((SubClassOf)reply).append(createSubClassOf(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("DataPropertyDomain")) ((DataPropertyDomain)reply).append(createDataPropertyDomain(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("DataPropertyRange")) ((DataPropertyRange)reply).append(createDataPropertyRange(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("DisjointDataProperties")) ((DisjointDataProperties)reply).append(createDisjointDataProperties(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("EquivalentDataProperties")) ((EquivalentDataProperties)reply).append(createEquivalentDataProperties(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("FunctionalDataProperty")) ((FunctionalDataProperty)reply).append(createFunctionalDataProperty(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("SubDataPropertyOf")) ((SubDataPropertyOf)reply).append(createSubDataPropertyOf(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("Declaration")) ((Declaration)reply).append(createDeclaration(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("EntityAnnotation")) ((EntityAnnotation)reply).append(createEntityAnnotation(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("ClassAssertion")) ((ClassAssertion)reply).append(createClassAssertion(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("DataPropertyAssertion")) ((DataPropertyAssertion)reply).append(createDataPropertyAssertion(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("DifferentIndividuals")) ((DifferentIndividuals)reply).append(createDifferentIndividuals(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("NegativeDataPropertyAssertion")) ((NegativeDataPropertyAssertion)reply).append(createNegativeDataPropertyAssertion(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("NegativeObjectPropertyAssertion")) ((NegativeObjectPropertyAssertion)reply).append(createNegativeObjectPropertyAssertion(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectPropertyAssertion")) ((ObjectPropertyAssertion)reply).append(createObjectPropertyAssertion(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("SameIndividual")) ((SameIndividual)reply).append(createSameIndividual(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("AsymmetricObjectProperty")) ((AsymmetricObjectProperty)reply).append(createAsymmetricObjectProperty(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("DisjointObjectProperties")) ((DisjointObjectProperties)reply).append(createDisjointObjectProperties(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("EquivalentObjectProperties")) ((EquivalentObjectProperties)reply).append(createEquivalentObjectProperties(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("FunctionalObjectProperty")) ((FunctionalObjectProperty)reply).append(createFunctionalObjectProperty(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("InverseFunctionalObjectProperty")) ((InverseFunctionalObjectProperty)reply).append(createInverseFunctionalObjectProperty(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("InverseObjectProperties")) ((InverseObjectProperties)reply).append(createInverseObjectProperties(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("IrreflexiveObjectProperty")) ((IrreflexiveObjectProperty)reply).append(createIrreflexiveObjectProperty(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectPropertyDomain")) ((ObjectPropertyDomain)reply).append(createObjectPropertyDomain(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectPropertyRange")) ((ObjectPropertyRange)reply).append(createObjectPropertyRange(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("ReflexiveObjectProperty")) ((ReflexiveObjectProperty)reply).append(createReflexiveObjectProperty(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("SymmetricObjectProperty")) ((SymmetricObjectProperty)reply).append(createSymmetricObjectProperty(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("TransitiveObjectProperty")) ((TransitiveObjectProperty)reply).append(createTransitiveObjectProperty(property.getURI(),propertyValue, ontologySearch));
					else if (whichClass.equalsIgnoreCase("SubObjectPropertyOf")) ((SubObjectPropertyOf)reply).append(createSubObjectPropertyOf(property.getURI(),propertyValue, ontologySearch));
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
					
					if (whichClass.equalsIgnoreCase("DisjointClasses")) ((DisjointClasses)reply).append(createDisjointClasses(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("DisjointUnion")) ((DisjointUnion)reply).append(createDisjointUnion(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("EquivalentClasses")) ((EquivalentClasses)reply).append(createEquivalentClasses(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("SubClassOf")) ((SubClassOf)reply).append(createSubClassOf(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("DataPropertyDomain")) ((DataPropertyDomain)reply).append(createDataPropertyDomain(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("DataPropertyRange")) ((DataPropertyRange)reply).append(createDataPropertyRange(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("DisjointDataProperties")) ((DisjointDataProperties)reply).append(createDisjointDataProperties(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("EquivalentDataProperties")) ((EquivalentDataProperties)reply).append(createEquivalentDataProperties(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("FunctionalDataProperty")) ((FunctionalDataProperty)reply).append(createFunctionalDataProperty(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("SubDataPropertyOf")) ((SubDataPropertyOf)reply).append(createSubDataPropertyOf(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("Declaration")) ((Declaration)reply).append(createDeclaration(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("EntityAnnotation")) ((EntityAnnotation)reply).append(createEntityAnnotation(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("ClassAssertion")) ((ClassAssertion)reply).append(createClassAssertion(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("DataPropertyAssertion")) ((DataPropertyAssertion)reply).append(createDataPropertyAssertion(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("DifferentIndividuals")) ((DifferentIndividuals)reply).append(createDifferentIndividuals(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("NegativeDataPropertyAssertion")) ((NegativeDataPropertyAssertion)reply).append(createNegativeDataPropertyAssertion(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("NegativeObjectPropertyAssertion")) ((NegativeObjectPropertyAssertion)reply).append(createNegativeObjectPropertyAssertion(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectPropertyAssertion")) ((ObjectPropertyAssertion)reply).append(createObjectPropertyAssertion(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("SameIndividual")) ((SameIndividual)reply).append(createSameIndividual(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("AsymmetricObjectProperty")) ((AsymmetricObjectProperty)reply).append(createAsymmetricObjectProperty(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("DisjointObjectProperties")) ((DisjointObjectProperties)reply).append(createDisjointObjectProperties(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("EquivalentObjectProperties")) ((EquivalentObjectProperties)reply).append(createEquivalentObjectProperties(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("FunctionalObjectProperty")) ((FunctionalObjectProperty)reply).append(createFunctionalObjectProperty(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("InverseFunctionalObjectProperty")) ((InverseFunctionalObjectProperty)reply).append(createInverseFunctionalObjectProperty(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("InverseObjectProperties")) ((InverseObjectProperties)reply).append(createInverseObjectProperties(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("IrreflexiveObjectProperty")) ((IrreflexiveObjectProperty)reply).append(createIrreflexiveObjectProperty(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectPropertyDomain")) ((ObjectPropertyDomain)reply).append(createObjectPropertyDomain(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("ObjectPropertyRange")) ((ObjectPropertyRange)reply).append(createObjectPropertyRange(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("ReflexiveObjectProperty")) ((ReflexiveObjectProperty)reply).append(createReflexiveObjectProperty(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("SymmetricObjectProperty")) ((SymmetricObjectProperty)reply).append(createSymmetricObjectProperty(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("TransitiveObjectProperty")) ((TransitiveObjectProperty)reply).append(createTransitiveObjectProperty(property.getURI(),propertyValue.getURI(), ontologySearch));
					else if (whichClass.equalsIgnoreCase("SubObjectPropertyOf")) ((SubObjectPropertyOf)reply).append(createSubObjectPropertyOf(property.getURI(),propertyValue.getURI(), ontologySearch));
				
					//System.out.println("The property "+property.getURI()+" has value: "+propertyValue);	
				}	
			}
		}
	  }catch(Exception e){
			System.out.println(e.getMessage()+" "+e.getCause()+" "+e.getStackTrace()+" "+e.toString()+" Problem in processAxiomIndividual");
	  }
	  onProcess.remove(axiomIndividual);
	  if (reply instanceof ClassAxiom || reply instanceof DataPropertyAxiom || reply instanceof Declaration || reply instanceof EntityAnnotation || reply instanceof Fact || reply instanceof ObjectPropertyAxiom)
	  return reply;
	  else return null;
	  
	 }
	 else{
		reply.setURI(axiomIndividual.getURI());
		return reply;
	 }
	}

	private static Axiom createAxiom(String URI, String value, Ontology ontologySearch) {
		// TODO Auto-generated method stub
		Axiom axiomReply=new Axiom();
		  try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.URI)) {axiomReply.setURI(value);return axiomReply;}
		  }catch(Exception e){
				System.out.println(e.toString()+" Search Problem in createOMVAtomicChange");
			}
		return axiomReply;
	}

	private static SubObjectPropertyOf createSubObjectPropertyOf(String URI, String value, Ontology ontologySearch) {
		SubObjectPropertyOf axiomreply = new SubObjectPropertyOf();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.superObjectProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setSuperObjectProperty(entityReply);
				entityReply = null;
				return axiomreply;
			}
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.subObjectProperties)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.addSubObjectProperties(entityReply);
				entityReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static TransitiveObjectProperty createTransitiveObjectProperty(String URI, String value, Ontology ontologySearch) {
		TransitiveObjectProperty axiomreply = new TransitiveObjectProperty();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.objectProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setObjectProperty(entityReply);
				entityReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static SymmetricObjectProperty createSymmetricObjectProperty(String URI, String value, Ontology ontologySearch) {
		SymmetricObjectProperty axiomreply = new SymmetricObjectProperty();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.objectProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setObjectProperty(entityReply);
				entityReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static ReflexiveObjectProperty createReflexiveObjectProperty(String URI, String value, Ontology ontologySearch) {
		ReflexiveObjectProperty axiomreply = new ReflexiveObjectProperty();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.objectProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setObjectProperty(entityReply);
				entityReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static ObjectPropertyRange createObjectPropertyRange(String URI, String value, Ontology ontologySearch) {
		ObjectPropertyRange axiomreply = new ObjectPropertyRange();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.objectProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setObjectProperty(entityReply);
				entityReply = null;
				return axiomreply;
			}
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.range)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				Description descriptionReply=(Description)ProcessDescriptionIndividuals.processDescriptionIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setRange(descriptionReply);
				descriptionReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static ObjectPropertyDomain createObjectPropertyDomain(String URI, String value, Ontology ontologySearch) {
		ObjectPropertyDomain axiomreply = new ObjectPropertyDomain();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.objectProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setObjectProperty(entityReply);
				entityReply = null;
				return axiomreply;
			}
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.domain)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				Description descriptionReply=(Description)ProcessDescriptionIndividuals.processDescriptionIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setDomain(descriptionReply);
				descriptionReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;	
	}

	private static IrreflexiveObjectProperty createIrreflexiveObjectProperty(String URI, String value, Ontology ontologySearch) {
		IrreflexiveObjectProperty axiomreply = new IrreflexiveObjectProperty();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.objectProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setObjectProperty(entityReply);
				entityReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static InverseObjectProperties createInverseObjectProperties(String URI, String value, Ontology ontologySearch) {
		InverseObjectProperties axiomreply = new InverseObjectProperties();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.inverseObjectProperties)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.addInverseObjectProperties(entityReply);
				entityReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static InverseFunctionalObjectProperty createInverseFunctionalObjectProperty(String URI, String value, Ontology ontologySearch) {
		InverseFunctionalObjectProperty axiomreply = new InverseFunctionalObjectProperty();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.objectProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setObjectProperty(entityReply);
				entityReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static FunctionalObjectProperty createFunctionalObjectProperty(String URI, String value, Ontology ontologySearch) {
		FunctionalObjectProperty axiomreply = new FunctionalObjectProperty();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.objectProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setObjectProperty(entityReply);
				entityReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static EquivalentObjectProperties createEquivalentObjectProperties(String URI, String value, Ontology ontologySearch) {
		EquivalentObjectProperties axiomreply = new EquivalentObjectProperties();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.equivalentObjectProperties)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.addEquivalentObjectProperties(entityReply);
				entityReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static DisjointObjectProperties createDisjointObjectProperties(String URI, String value, Ontology ontologySearch) {
		DisjointObjectProperties axiomreply = new DisjointObjectProperties();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.disjointObjectProperties)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.addDisjointObjectProperties(entityReply);
				entityReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static AsymmetricObjectProperty createAsymmetricObjectProperty(String URI, String value, Ontology ontologySearch) {
		AsymmetricObjectProperty axiomreply = new AsymmetricObjectProperty();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.objectProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setObjectProperty(entityReply);
				entityReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static SameIndividual createSameIndividual(String URI, String value, Ontology ontologySearch) {
		SameIndividual axiomreply = new SameIndividual();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.sameIndividuals)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.Individual entityReply=(org.neontoolkit.owlodm.api.OWLEntity.Individual)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.addSameIndividuals(entityReply);
				entityReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static ObjectPropertyAssertion createObjectPropertyAssertion(String URI, String value, Ontology ontologySearch) {
		ObjectPropertyAssertion axiomreply = new ObjectPropertyAssertion();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.objectProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setObjectProperty(entityReply);
				entityReply = null;
				return axiomreply;
			}
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.sourceIndividual)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.Individual entityReply=(org.neontoolkit.owlodm.api.OWLEntity.Individual)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setSourceIndividual(entityReply);
				entityReply = null;
				return axiomreply;
			}
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.targetIndividual)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.Individual entityReply=(org.neontoolkit.owlodm.api.OWLEntity.Individual)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setTargetIndividual(entityReply);
				entityReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static NegativeObjectPropertyAssertion createNegativeObjectPropertyAssertion(String URI, String value, Ontology ontologySearch) {
		NegativeObjectPropertyAssertion axiomreply = new NegativeObjectPropertyAssertion();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.objectProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.ObjectProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setObjectProperty(entityReply);
				entityReply = null;
				return axiomreply;
			}
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.sourceIndividual)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.Individual entityReply=(org.neontoolkit.owlodm.api.OWLEntity.Individual)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setSourceIndividual(entityReply);
				entityReply = null;
				return axiomreply;
			}
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.targetIndividual)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.Individual entityReply=(org.neontoolkit.owlodm.api.OWLEntity.Individual)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setTargetIndividual(entityReply);
				entityReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static NegativeDataPropertyAssertion createNegativeDataPropertyAssertion(String URI, String value, Ontology ontologySearch) {
		NegativeDataPropertyAssertion axiomreply = new NegativeDataPropertyAssertion();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.dataProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.DataProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.DataProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setDataProperty(entityReply);
				entityReply = null;
				return axiomreply;
			}
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.sourceIndividual)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.Individual entityReply=(org.neontoolkit.owlodm.api.OWLEntity.Individual)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setSourceIndividual(entityReply);
				entityReply = null;
				return axiomreply;
			}
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.targetValue)) {axiomreply.setTargetValue(value);return axiomreply;}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static DifferentIndividuals createDifferentIndividuals(String URI, String value, Ontology ontologySearch) {
		DifferentIndividuals axiomreply = new DifferentIndividuals();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.differentIndividuals)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.Individual entityReply=(org.neontoolkit.owlodm.api.OWLEntity.Individual)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.addDifferentIndividuals(entityReply);
				entityReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static DataPropertyAssertion createDataPropertyAssertion(String URI, String value, Ontology ontologySearch) {
		DataPropertyAssertion axiomreply = new DataPropertyAssertion();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.dataProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.DataProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.DataProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setDataProperty(entityReply);
				entityReply = null;
				return axiomreply;
			}
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.sourceIndividual)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.Individual entityReply=(org.neontoolkit.owlodm.api.OWLEntity.Individual)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setSourceIndividual(entityReply);
				entityReply = null;
				return axiomreply;
			}
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.targetValue)) {axiomreply.setTargetValue(value);return axiomreply;}
		}catch(Exception e){
				e.printStackTrace();
		}		
		return axiomreply;
	}

	private static ClassAssertion createClassAssertion(String URI, String value, Ontology ontologySearch) {
		ClassAssertion axiomreply = new ClassAssertion();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.OWLClass)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				Description descriptionReply=(Description)ProcessDescriptionIndividuals.processDescriptionIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setOWLClass(descriptionReply);
				descriptionReply = null;
				return axiomreply;
			}
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.individual)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				org.neontoolkit.owlodm.api.OWLEntity.Individual descriptionReply=(org.neontoolkit.owlodm.api.OWLEntity.Individual)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setIndividual(descriptionReply);
				descriptionReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static EntityAnnotation createEntityAnnotation(String URI, String value, Ontology ontologySearch) {
		EntityAnnotation axiomreply = new EntityAnnotation();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.entity)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				OWLEntity descriptionReply=(OWLEntity)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setEntity(descriptionReply);
				descriptionReply = null;
				return axiomreply;
			}
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.entityAnnotation)) { 		
				axiomreply.addEntityAnnotation(value);
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static Declaration createDeclaration(String URI, String value, Ontology ontologySearch) {
		Declaration axiomreply = new Declaration();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.entity)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				OWLEntity descriptionReply=(OWLEntity)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setEntity(descriptionReply);
				descriptionReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static SubDataPropertyOf createSubDataPropertyOf(String URI, String value, Ontology ontologySearch) {
		SubDataPropertyOf axiomreply = new SubDataPropertyOf();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.subDataProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);				
				org.neontoolkit.owlodm.api.OWLEntity.DataProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.DataProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setSubDataProperty(entityReply);
				entityReply = null;
				return axiomreply;
			}
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.superDataProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);				
				org.neontoolkit.owlodm.api.OWLEntity.DataProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.DataProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setSuperDataProperty(entityReply);
				entityReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static FunctionalDataProperty createFunctionalDataProperty(String URI, String value, Ontology ontologySearch) {
		FunctionalDataProperty axiomreply = new FunctionalDataProperty();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.dataProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);				
				org.neontoolkit.owlodm.api.OWLEntity.DataProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.DataProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setDataProperty(entityReply);
				entityReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static EquivalentDataProperties createEquivalentDataProperties(String URI, String value, Ontology ontologySearch) {
		EquivalentDataProperties axiomreply = new EquivalentDataProperties();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.dataProperties)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);				
				org.neontoolkit.owlodm.api.OWLEntity.DataProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.DataProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.addDataProperties(entityReply);
				entityReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static DisjointDataProperties createDisjointDataProperties(String URI, String value, Ontology ontologySearch) {
		DisjointDataProperties axiomreply = new DisjointDataProperties();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.dataProperties)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);				
				org.neontoolkit.owlodm.api.OWLEntity.DataProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.DataProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.addDataProperties(entityReply);
				entityReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static DataPropertyRange createDataPropertyRange(String URI, String value, Ontology ontologySearch) {
		DataPropertyRange axiomreply = new DataPropertyRange();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.dataProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);				
				org.neontoolkit.owlodm.api.OWLEntity.DataProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.DataProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setDataProperty(entityReply);
				entityReply = null;
				return axiomreply;
			}
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.range)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				Datatype entityReply=(Datatype)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setRange(entityReply);
				entityReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static DataPropertyDomain createDataPropertyDomain(String URI, String value, Ontology ontologySearch) {
		DataPropertyDomain axiomreply = new DataPropertyDomain();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.dataProperty)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);				
				org.neontoolkit.owlodm.api.OWLEntity.DataProperty entityReply=(org.neontoolkit.owlodm.api.OWLEntity.DataProperty)ProcessOWLEntityIndividuals.processOWLEntityIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setDataProperty(entityReply);
				entityReply = null;
				return axiomreply;
			}
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.domain)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				Description descriptionReply=(Description)ProcessDescriptionIndividuals.processDescriptionIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setDomain(descriptionReply);
				descriptionReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static SubClassOf createSubClassOf(String URI, String value, Ontology ontologySearch) {
		SubClassOf axiomreply = new SubClassOf();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.subClass)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				Description descriptionReply=(Description)ProcessDescriptionIndividuals.processDescriptionIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setSubClass(descriptionReply);
				descriptionReply = null;
				return axiomreply;
			}
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.superClass)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				Description descriptionReply=(Description)ProcessDescriptionIndividuals.processDescriptionIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setSuperClass(descriptionReply);
				descriptionReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static EquivalentClasses createEquivalentClasses(String URI, String value, Ontology ontologySearch) {
		EquivalentClasses axiomreply = new EquivalentClasses();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.equivalentClasses)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				Description descriptionReply=(Description)ProcessDescriptionIndividuals.processDescriptionIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.addEquivalentClasses(descriptionReply);
				descriptionReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static DisjointUnion createDisjointUnion(String URI, String value, Ontology ontologySearch) {
		DisjointUnion axiomreply = new DisjointUnion();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.unionClass)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				Description descriptionReply=(Description)ProcessDescriptionIndividuals.processDescriptionIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.setUnionClass(descriptionReply);
				descriptionReply = null;
				return axiomreply;
			}
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.disjointClasses)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				Description descriptionReply=(Description)ProcessDescriptionIndividuals.processDescriptionIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.addDisjointClasses(descriptionReply);
				descriptionReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}

	private static DisjointClasses createDisjointClasses(String URI, String value, Ontology ontologySearch) {
		DisjointClasses axiomreply = new DisjointClasses();
		try{
			if (URI.equalsIgnoreCase(Constants.OWLODMURI+Constants.disjointClasses)) {
				Individual oIndividual =KAON2Manager.factory().individual(value);
				Description descriptionReply=(Description)ProcessDescriptionIndividuals.processDescriptionIndividual(oIndividual, oIndividual.getDescriptionsMemberOf(ontologySearch).iterator().next().toString(), ontologySearch); //oReferences
				axiomreply.addDisjointClasses(descriptionReply);
				descriptionReply = null;
				return axiomreply;
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return axiomreply;
	}
	
	
	
}