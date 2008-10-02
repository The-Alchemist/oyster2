package org.neontoolkit.changelogging.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.neontoolkit.changelogging.core.owl.OWLChangeListener;
import org.neontoolkit.changelogging.gui.actions.Track;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.extensions.change.OMVChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange;
import org.neontoolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange.Addition;
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
import org.semanticweb.kaon2.api.KAON2Exception;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Namespaces;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.OntologyChangeEvent;
import org.semanticweb.kaon2.api.OntologyManager;
import org.semanticweb.kaon2.api.owl.axioms.DataPropertyAttribute;
import org.semanticweb.kaon2.api.owl.axioms.ObjectPropertyAttribute;
import org.semanticweb.kaon2.api.owl.elements.DataPropertyExpression;
import org.semanticweb.kaon2.api.owl.elements.DataRange;

import com.ontoprise.ontostudio.datamodel.DatamodelPlugin;

public class ApplyChangesFromLogToNTK {
	public static void applyChanges(List<OMVChange> p1, OMVOntology o){
		OntologyManager connection;		
		try {
			String[] temp = DatamodelPlugin.getDefault().getOntologyProjects();
			for (int i=0; i < temp.length; i++) {
				String [] temp2 = DatamodelPlugin.getDefault().getProjectOntologies(temp[i]);
				for (int j=0; j < temp2.length; j++) {
					if (temp2[j].equalsIgnoreCase(o.getURI())){
						connection = DatamodelPlugin.getDefault().getKaon2Connection(temp[i]);
						Iterator<OMVChange> it3 = p1.iterator();
						//switch off listener while we are here
						while (it3.hasNext()){
							OMVChange tt=(OMVChange)it3.next();
							
							if (tt instanceof OMVAtomicChange){
								try {
									List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
									org.semanticweb.kaon2.api.Axiom app=null;
									Ontology targetRegistry = connection.getOntology(temp2[j]);

									//Get the axiom applied
									Axiom ax = ((OMVAtomicChange)tt).getAppliedAxiom();
								
									//Construct the axiom in datamodel
									if (ax instanceof ClassAxiom){
										if (ax instanceof DisjointClasses){
											DisjointClasses axT = (DisjointClasses)ax;
											Collection<org.semanticweb.kaon2.api.owl.elements.Description> cddm= new HashSet<org.semanticweb.kaon2.api.owl.elements.Description>();
											for (Description diT : axT.getDisjointClasses()){
												cddm.add(transformDescription(diT));
											}
											if (cddm.size()>0)
												app = KAON2Manager.factory().disjointClasses(cddm);
										}
										if (ax instanceof DisjointUnion){
											DisjointUnion axT = (DisjointUnion)ax;
											Collection<org.semanticweb.kaon2.api.owl.elements.Description> cddm= new HashSet<org.semanticweb.kaon2.api.owl.elements.Description>();
											for (Description diT : axT.getDisjointClasses()){
												cddm.add(transformDescription(diT));
											}
											org.semanticweb.kaon2.api.owl.elements.OWLClass uc=null;
											if (axT.getUnionClass() instanceof OWLClass)
												 uc = KAON2Manager.factory().owlClass(((OWLClass)axT.getUnionClass()).getURI());
											if (cddm.size()>0 && uc!=null)
												app = KAON2Manager.factory().disjointUnion(uc, cddm);
										}
										if (ax instanceof EquivalentClasses){
											EquivalentClasses axT = (EquivalentClasses)ax;
											Collection<org.semanticweb.kaon2.api.owl.elements.Description> cddm= new HashSet<org.semanticweb.kaon2.api.owl.elements.Description>();
											for (Description diT : axT.getEquivalentClasses()){
												cddm.add(transformDescription(diT));
											}
											if (cddm.size()>0)
												app = KAON2Manager.factory().equivalentClasses(cddm);
										}
										if (ax instanceof SubClassOf){
											SubClassOf axT = (SubClassOf)ax;
											org.semanticweb.kaon2.api.owl.elements.Description sup = transformDescription(axT.getSuperClass());
											org.semanticweb.kaon2.api.owl.elements.Description sub = transformDescription(axT.getSubClass());
											if (sub!=null && sup!=null)
												app = KAON2Manager.factory().subClassOf(sub,sup);
										}
									}
									else if (ax instanceof Declaration){
										OWLEntity oeoy = ((Declaration)ax).getEntity();
										org.semanticweb.kaon2.api.owl.elements.OWLEntity ddm=null;
										if (oeoy instanceof Description){
											//org.semanticweb.kaon2.api.owl.elements.Description des=transformDescription((Description)oeoy);
											//ddm = des;
											if (oeoy instanceof OWLClass)
												ddm = KAON2Manager.factory().owlClass(((OWLClass)oeoy).getURI());
										}
										else if (oeoy instanceof DataProperty){
											ddm = KAON2Manager.factory().dataProperty(((DataProperty)oeoy).getURI());
										}
										else if (oeoy instanceof Datatype){
											ddm = KAON2Manager.factory().datatype(((Datatype)oeoy).getURI());
										}
										else if (oeoy instanceof Individual){
											ddm = KAON2Manager.factory().individual(((Individual)oeoy).getURI());
										}
										else if (oeoy instanceof ObjectProperty){
											ddm = KAON2Manager.factory().objectProperty(((ObjectProperty)oeoy).getURI());
										}
										if (ddm!=null)
											app = KAON2Manager.factory().declaration(ddm);
									}
									else if (ax instanceof DataPropertyAxiom){
										if (ax instanceof DataPropertyDomain){
											DataPropertyDomain axT = (DataPropertyDomain)ax;
											org.semanticweb.kaon2.api.owl.elements.DataProperty dp = KAON2Manager.factory().dataProperty(axT.getDataProperty().getURI());
											org.semanticweb.kaon2.api.owl.elements.Description dom = transformDescription(axT.getDomain());
											if (dp!=null && dom!=null)
												app = KAON2Manager.factory().dataPropertyDomain(dp, dom);
										}
										else if (ax instanceof DataPropertyRange){
											DataPropertyRange axT = (DataPropertyRange)ax;
											org.semanticweb.kaon2.api.owl.elements.DataProperty dp = KAON2Manager.factory().dataProperty(axT.getDataProperty().getURI());
											org.semanticweb.kaon2.api.owl.elements.Datatype dt = KAON2Manager.factory().datatype(axT.getRange().getURI());
											if (dp!=null && dt!=null)
												app = KAON2Manager.factory().dataPropertyRange(dp, dt);
										}
										else if (ax instanceof DisjointDataProperties){
											DisjointDataProperties axT = (DisjointDataProperties)ax;
											Collection<org.semanticweb.kaon2.api.owl.elements.DataPropertyExpression> cddm= new HashSet<org.semanticweb.kaon2.api.owl.elements.DataPropertyExpression>();
											for (DataProperty diT : axT.getDataProperties()){
												cddm.add(KAON2Manager.factory().dataProperty(diT.getURI()));
											}
											if (cddm.size()>0)
												app = KAON2Manager.factory().disjointDataProperties(cddm);
										}
										else if (ax instanceof EquivalentDataProperties){
											EquivalentDataProperties axT = (EquivalentDataProperties)ax;
											Collection<org.semanticweb.kaon2.api.owl.elements.DataPropertyExpression> cddm= new HashSet<org.semanticweb.kaon2.api.owl.elements.DataPropertyExpression>();
											for (DataProperty diT : axT.getDataProperties()){
												cddm.add(KAON2Manager.factory().dataProperty(diT.getURI()));
											}
											if (cddm.size()>0)
												app = KAON2Manager.factory().equivalentDataProperties(cddm);
										}
										else if (ax instanceof FunctionalDataProperty){
											FunctionalDataProperty axT = (FunctionalDataProperty)ax;
											org.semanticweb.kaon2.api.owl.elements.DataProperty dp = KAON2Manager.factory().dataProperty(axT.getDataProperty().getURI());
											if (dp!=null)
												app = KAON2Manager.factory().dataPropertyAttribute(dp, DataPropertyAttribute.DATA_PROPERTY_FUNCTIONAL); 
										}
										else if (ax instanceof SubDataPropertyOf){
											SubDataPropertyOf axT = (SubDataPropertyOf)ax;
											org.semanticweb.kaon2.api.owl.elements.DataProperty sub = KAON2Manager.factory().dataProperty(axT.getSubDataProperty().getURI());
											org.semanticweb.kaon2.api.owl.elements.DataProperty sup = KAON2Manager.factory().dataProperty(axT.getSuperDataProperty().getURI());
											if (sub!=null && sup!=null)
												app = KAON2Manager.factory().subDataPropertyOf(sub, sup);
										}
									}
									else if (ax instanceof ObjectPropertyAxiom){
										if (ax instanceof AsymmetricObjectProperty){
											AsymmetricObjectProperty axT = (AsymmetricObjectProperty)ax;
											org.semanticweb.kaon2.api.owl.elements.ObjectProperty op = KAON2Manager.factory().objectProperty(axT.getObjectProperty().getURI());
											if (op!=null)
												app = KAON2Manager.factory().objectPropertyAttribute(op, ObjectPropertyAttribute.OBJECT_PROPERTY_ASYMMETRIC); 
										}
										else if (ax instanceof DisjointObjectProperties){
											DisjointObjectProperties axT = (DisjointObjectProperties)ax;
											Collection<org.semanticweb.kaon2.api.owl.elements.ObjectPropertyExpression> cddm= new HashSet<org.semanticweb.kaon2.api.owl.elements.ObjectPropertyExpression>();
											for (ObjectProperty diT : axT.getDisjointObjectProperties()){
												cddm.add(KAON2Manager.factory().objectProperty(diT.getURI()));
											}
											if (cddm.size()>0)
												app = KAON2Manager.factory().disjointObjectProperties(cddm);
										}
										else if (ax instanceof EquivalentObjectProperties){
											EquivalentObjectProperties axT = (EquivalentObjectProperties)ax;
											Collection<org.semanticweb.kaon2.api.owl.elements.ObjectPropertyExpression> cddm= new HashSet<org.semanticweb.kaon2.api.owl.elements.ObjectPropertyExpression>();
											for (ObjectProperty diT : axT.getEquivalentObjectProperties()){
												cddm.add(KAON2Manager.factory().objectProperty(diT.getURI()));
											}
											if (cddm.size()>0)
												app = KAON2Manager.factory().equivalentObjectProperties(cddm);
										}
										else if (ax instanceof FunctionalObjectProperty){
											FunctionalObjectProperty axT = (FunctionalObjectProperty)ax;
											org.semanticweb.kaon2.api.owl.elements.ObjectProperty op = KAON2Manager.factory().objectProperty(axT.getObjectProperty().getURI());
											if (op!=null)
												app = KAON2Manager.factory().objectPropertyAttribute(op, ObjectPropertyAttribute.OBJECT_PROPERTY_FUNCTIONAL); 
										}
										else if (ax instanceof InverseFunctionalObjectProperty){
											InverseFunctionalObjectProperty axT = (InverseFunctionalObjectProperty)ax;
											org.semanticweb.kaon2.api.owl.elements.ObjectProperty op = KAON2Manager.factory().objectProperty(axT.getObjectProperty().getURI());
											if (op!=null)
												app = KAON2Manager.factory().objectPropertyAttribute(op, ObjectPropertyAttribute.OBJECT_PROPERTY_INVERSE_FUNCTIONAL); 
										}
										else if (ax instanceof InverseObjectProperties){
											InverseObjectProperties axT = (InverseObjectProperties)ax;
											Collection<org.semanticweb.kaon2.api.owl.elements.ObjectPropertyExpression> cddm= new HashSet<org.semanticweb.kaon2.api.owl.elements.ObjectPropertyExpression>();
											for (ObjectProperty diT : axT.getInverseObjectProperties()){
												cddm.add(KAON2Manager.factory().objectProperty(diT.getURI()));
											}
											if (cddm.size()>1){
												Iterator<org.semanticweb.kaon2.api.owl.elements.ObjectPropertyExpression> strange = cddm.iterator();
												app = KAON2Manager.factory().inverseObjectProperties(strange.next(), strange.next());
											}
										}
										else if (ax instanceof IrreflexiveObjectProperty){
											IrreflexiveObjectProperty axT = (IrreflexiveObjectProperty)ax;
											org.semanticweb.kaon2.api.owl.elements.ObjectProperty op = KAON2Manager.factory().objectProperty(axT.getObjectProperty().getURI());
											if (op!=null)
												app = KAON2Manager.factory().objectPropertyAttribute(op, ObjectPropertyAttribute.OBJECT_PROPERTY_IRREFLEXIVE);
										}
										else if (ax instanceof ObjectPropertyDomain){
											ObjectPropertyDomain axT = (ObjectPropertyDomain)ax;
											org.semanticweb.kaon2.api.owl.elements.ObjectProperty op = KAON2Manager.factory().objectProperty(axT.getObjectProperty().getURI());
											org.semanticweb.kaon2.api.owl.elements.Description dom = transformDescription(axT.getDomain());
											if (op!=null && dom!=null)
												app = KAON2Manager.factory().objectPropertyDomain(op, dom); 
										}
										else if (ax instanceof ObjectPropertyRange){
											ObjectPropertyRange axT = (ObjectPropertyRange)ax;
											org.semanticweb.kaon2.api.owl.elements.ObjectProperty op = KAON2Manager.factory().objectProperty(axT.getObjectProperty().getURI());
											org.semanticweb.kaon2.api.owl.elements.Description ran = transformDescription(axT.getRange());
											if (op!=null && ran!=null)
												app = KAON2Manager.factory().objectPropertyRange(op, ran); 
										}
										else if (ax instanceof ReflexiveObjectProperty){
											ReflexiveObjectProperty axT = (ReflexiveObjectProperty)ax;
											org.semanticweb.kaon2.api.owl.elements.ObjectProperty op = KAON2Manager.factory().objectProperty(axT.getObjectProperty().getURI());
											if (op!=null)
												app = KAON2Manager.factory().objectPropertyAttribute(op, ObjectPropertyAttribute.OBJECT_PROPERTY_REFLEXIVE); 
										}
										else if (ax instanceof SymmetricObjectProperty){
											SymmetricObjectProperty axT = (SymmetricObjectProperty)ax;
											org.semanticweb.kaon2.api.owl.elements.ObjectProperty op = KAON2Manager.factory().objectProperty(axT.getObjectProperty().getURI());
											if (op!=null)
												app = KAON2Manager.factory().objectPropertyAttribute(op, ObjectPropertyAttribute.OBJECT_PROPERTY_SYMMETRIC); 
										}
										else if (ax instanceof TransitiveObjectProperty){
											TransitiveObjectProperty axT = (TransitiveObjectProperty)ax;
											org.semanticweb.kaon2.api.owl.elements.ObjectProperty op = KAON2Manager.factory().objectProperty(axT.getObjectProperty().getURI());
											if (op!=null)
												app = KAON2Manager.factory().objectPropertyAttribute(op, ObjectPropertyAttribute.OBJECT_PROPERTY_TRANSITIVE); 
										}
										else if (ax instanceof SubObjectPropertyOf){
											SubObjectPropertyOf axT = (SubObjectPropertyOf)ax;
											List<org.semanticweb.kaon2.api.owl.elements.ObjectPropertyExpression> cddm= new LinkedList<org.semanticweb.kaon2.api.owl.elements.ObjectPropertyExpression>();
											for (ObjectProperty diT : axT.getSubObjectProperties()){
												cddm.add(KAON2Manager.factory().objectProperty(diT.getURI()));
											}
											org.semanticweb.kaon2.api.owl.elements.ObjectProperty sup = KAON2Manager.factory().objectProperty(axT.getSuperObjectProperty().getURI());
											if (sup!=null && cddm.size()>0)
												app = KAON2Manager.factory().subObjectPropertyOf(cddm, sup);
										}
									}
									else if (ax instanceof Fact){
										if (ax instanceof ClassAssertion){
											ClassAssertion axT = (ClassAssertion)ax;
											org.semanticweb.kaon2.api.owl.elements.Description cl = transformDescription(axT.getOWLClass());
											org.semanticweb.kaon2.api.owl.elements.Individual in = KAON2Manager.factory().individual(axT.getIndividual().getURI());
											if (cl!=null && in!=null)
												app = KAON2Manager.factory().classMember(cl, in);
										}
										else if (ax instanceof DataPropertyAssertion){
											DataPropertyAssertion axT = (DataPropertyAssertion)ax;
											org.semanticweb.kaon2.api.owl.elements.DataProperty dp= KAON2Manager.factory().dataProperty(axT.getDataProperty().getURI());
											org.semanticweb.kaon2.api.owl.elements.Individual in = KAON2Manager.factory().individual(axT.getSourceIndividual().getURI());
											if (dp!=null && in!=null)
												app = KAON2Manager.factory().dataPropertyMember(dp, in, KAON2Manager.factory().constant(axT.getTargetValue()));
										}
										else if (ax instanceof DifferentIndividuals){
											DifferentIndividuals axT = (DifferentIndividuals)ax;
											
											Collection<org.semanticweb.kaon2.api.owl.elements.Individual> cddm= new HashSet<org.semanticweb.kaon2.api.owl.elements.Individual>();
											for (Individual diT : axT.getDifferentIndividuals()){
												cddm.add(KAON2Manager.factory().individual(diT.getURI()));
											}
											if (cddm.size()>1)
												app = KAON2Manager.factory().differentIndividuals(cddm);
										}
										else if (ax instanceof NegativeDataPropertyAssertion){
											NegativeDataPropertyAssertion axT = (NegativeDataPropertyAssertion)ax;
											org.semanticweb.kaon2.api.owl.elements.DataProperty dp= KAON2Manager.factory().dataProperty(axT.getDataProperty().getURI());
											org.semanticweb.kaon2.api.owl.elements.Individual in = KAON2Manager.factory().individual(axT.getSourceIndividual().getURI());
											if (dp!=null && in!=null)
												app = KAON2Manager.factory().negativeDataPropertyMember(dp, in, KAON2Manager.factory().constant(axT.getTargetValue()));
										}
										else if (ax instanceof NegativeObjectPropertyAssertion){
											NegativeObjectPropertyAssertion axT = (NegativeObjectPropertyAssertion)ax;
											org.semanticweb.kaon2.api.owl.elements.ObjectProperty op= KAON2Manager.factory().objectProperty(axT.getObjectProperty().getURI());
											org.semanticweb.kaon2.api.owl.elements.Individual inS = KAON2Manager.factory().individual(axT.getSourceIndividual().getURI());
											org.semanticweb.kaon2.api.owl.elements.Individual inT = KAON2Manager.factory().individual(axT.getTargetIndividual().getURI());
											if (op!=null && inS!=null && inT!=null)
												app = KAON2Manager.factory().negativeObjectPropertyMember(op, inS, inT);
										}
										else if (ax instanceof ObjectPropertyAssertion){
											ObjectPropertyAssertion axT = (ObjectPropertyAssertion)ax;
											org.semanticweb.kaon2.api.owl.elements.ObjectProperty op= KAON2Manager.factory().objectProperty(axT.getObjectProperty().getURI());
											org.semanticweb.kaon2.api.owl.elements.Individual inS = KAON2Manager.factory().individual(axT.getSourceIndividual().getURI());
											org.semanticweb.kaon2.api.owl.elements.Individual inT = KAON2Manager.factory().individual(axT.getTargetIndividual().getURI());
											if (op!=null && inS!=null && inT!=null)
												app = KAON2Manager.factory().objectPropertyMember(op, inS, inT);
										}
										else if (ax instanceof SameIndividual){
											SameIndividual axT = (SameIndividual)ax;
											
											Collection<org.semanticweb.kaon2.api.owl.elements.Individual> cddm= new HashSet<org.semanticweb.kaon2.api.owl.elements.Individual>();
											for (Individual diT : axT.getSameIndividuals()){
												cddm.add(KAON2Manager.factory().individual(diT.getURI()));
											}
											if (cddm.size()>1)
												app = KAON2Manager.factory().sameIndividual(cddm);
										}
									}
									else if (ax instanceof EntityAnnotation){
										EntityAnnotation axT = (EntityAnnotation)ax;
										OWLEntity oeoy = axT.getEntity();
										org.semanticweb.kaon2.api.owl.elements.OWLEntity ddm=null;
										
										if (oeoy==null) return;
										
										String annoProp=axT.getAnnotationProperty();
										String entity=oeoy.getURI();
										String constantValue="";
										for (String diT : axT.getEntityAnnotation()){
											constantValue=constantValue+" "+diT;
										}
										constantValue=constantValue.replace("%20", " ");
										if (oeoy instanceof OWLClass) ddm = KAON2Manager.factory().owlClass(entity);
										else if (oeoy instanceof DataProperty) ddm = KAON2Manager.factory().dataProperty(entity);
										else if (oeoy instanceof ObjectProperty) ddm = KAON2Manager.factory().objectProperty(entity);
										else if (oeoy instanceof Individual) ddm = KAON2Manager.factory().individual(entity);
										else if (oeoy instanceof Datatype) ddm = KAON2Manager.factory().datatype(entity);
										if (constantValue.length()>1 && ddm!=null && annoProp!=null){
											constantValue=constantValue.substring(1);
											String localN=Namespaces.guessLocalName(annoProp); //cannot use reserved names as values in ontology
						        			if (localN!=null && (localN.equalsIgnoreCase("comment") || localN.equalsIgnoreCase("label")))
						        				annoProp="http://www.w3.org/2000/01/rdf-schema#"+localN;
											app = KAON2Manager.factory().entityAnnotation(KAON2Manager.factory().annotationProperty(annoProp), ddm, KAON2Manager.factory().constant(constantValue));
										}
									}

									if (app!=null) {//check wether it is a valid axiom
										System.out.println("Going to apply axiom: "+app.toString()+ " "+tt.getClass().toString());
										//Add or remove axiom
										if (tt instanceof Addition){	
											changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.ADD));
										}else{
											changes.add(new OntologyChangeEvent(app,OntologyChangeEvent.ChangeType.REMOVE));
										}
										//Apply axiom
										OWLChangeListener listener=Track.OWLList.get(targetRegistry);
										if (listener !=null)
											targetRegistry.removeOntologyListener(listener);
										try {
											targetRegistry.applyChanges(changes);
											targetRegistry.persist();
										} catch (Exception e) {
											e.printStackTrace();
										}
										if (listener !=null)
											targetRegistry.addOntologyListener(listener);
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static org.semanticweb.kaon2.api.owl.elements.Description transformDescription (Description oeoy){
		org.semanticweb.kaon2.api.owl.elements.Description ddm=null;
		
		try{
			if (oeoy instanceof org.neontoolkit.owlodm.api.Description.OWLClass){
				//org.semanticweb.kaon2.api.owl.elements.OWLEntity ddm = KAON2Manager.factory().owlClass(((org.neontoolkit.owlodm.api.Description.OWLClass)oeoy).getURI());
				//app = KAON2Manager.factory().declaration(ddm);
				ddm = KAON2Manager.factory().owlClass(((org.neontoolkit.owlodm.api.Description.OWLClass)oeoy).getURI());
			}
			else if (oeoy instanceof DataAllValuesFrom){
				DataAllValuesFrom dav = (DataAllValuesFrom)oeoy;
				DataRange dr = KAON2Manager.factory().dataRange(dav.getDataRange().getURI(), new Namespaces());
				List<DataPropertyExpression> dpes = new LinkedList<DataPropertyExpression>();
				for (DataProperty dp : dav.getDataProperties()){
					dpes.add(KAON2Manager.factory().dataProperty(dp.getURI()));
				}
				ddm = KAON2Manager.factory().dataAll(dr, dpes);
			}
			else if (oeoy instanceof DataExactCardinality){
				DataExactCardinality dav = (DataExactCardinality)oeoy;
				DataRange dr = KAON2Manager.factory().dataRange(dav.getDataRange().getURI(), new Namespaces());
				org.semanticweb.kaon2.api.owl.elements.DataProperty dp = KAON2Manager.factory().dataProperty(dav.getDataProperty().getURI()); 
				ddm = KAON2Manager.factory().dataCardinality(dav.getCardinality(), dav.getCardinality(), dp, dr);
			}
			else if (oeoy instanceof DataHasValue){
				DataHasValue dav = (DataHasValue)oeoy;
				org.semanticweb.kaon2.api.owl.elements.DataProperty dp = KAON2Manager.factory().dataProperty(dav.getDataProperty().getURI()); 
				ddm = KAON2Manager.factory().dataHasValue(dp, KAON2Manager.factory().constant(dav.getConstant()));
			}
			else if (oeoy instanceof DataMaxCardinality){
				DataMaxCardinality dav = (DataMaxCardinality)oeoy;
				DataRange dr = KAON2Manager.factory().dataRange(dav.getDataRange().getURI(), new Namespaces());
				org.semanticweb.kaon2.api.owl.elements.DataProperty dp = KAON2Manager.factory().dataProperty(dav.getDataProperty().getURI()); 
				ddm = KAON2Manager.factory().dataCardinality(-1, dav.getCardinality(), dp, dr);
			}
			else if (oeoy instanceof DataMinCardinality){
				DataMinCardinality dav = (DataMinCardinality)oeoy;
				DataRange dr = KAON2Manager.factory().dataRange(dav.getDataRange().getURI(), new Namespaces());
				org.semanticweb.kaon2.api.owl.elements.DataProperty dp = KAON2Manager.factory().dataProperty(dav.getDataProperty().getURI()); 
				ddm = KAON2Manager.factory().dataCardinality(dav.getCardinality(),-1, dp, dr);
			}
			else if (oeoy instanceof DataSomeValuesFrom){
				DataSomeValuesFrom dav = (DataSomeValuesFrom)oeoy;
				DataRange dr = KAON2Manager.factory().dataRange(dav.getDataRange().getURI(), new Namespaces());
				List<DataPropertyExpression> dpes = new LinkedList<DataPropertyExpression>();
				for (DataProperty dp : dav.getDataProperties()){
					dpes.add(KAON2Manager.factory().dataProperty(dp.getURI()));
				}
				ddm = KAON2Manager.factory().dataSome(dr, dpes);
			}
			
			else if (oeoy instanceof ObjectAllValuesFrom){
				ObjectAllValuesFrom dav = (ObjectAllValuesFrom)oeoy;
				org.semanticweb.kaon2.api.owl.elements.ObjectProperty op = KAON2Manager.factory().objectProperty(dav.getObjectProperty().getURI());
				org.semanticweb.kaon2.api.owl.elements.Description owlClassIn=transformDescription(dav.getOWLClass());											
				ddm = KAON2Manager.factory().objectAll(op, owlClassIn);
			}
			else if (oeoy instanceof ObjectComplementOf){
				ObjectComplementOf dav = (ObjectComplementOf)oeoy;
				org.semanticweb.kaon2.api.owl.elements.Description owlClassIn=transformDescription(dav.getOWLClass());											
				ddm = KAON2Manager.factory().objectNot(owlClassIn);
			}
			else if (oeoy instanceof ObjectExactCardinality){
				ObjectExactCardinality dav = (ObjectExactCardinality)oeoy;
				org.semanticweb.kaon2.api.owl.elements.ObjectProperty op = KAON2Manager.factory().objectProperty(dav.getObjectProperty().getURI());
				org.semanticweb.kaon2.api.owl.elements.Description owlClassIn=transformDescription(dav.getOWLClass());
				ddm = KAON2Manager.factory().objectCardinality(dav.getCardinality(), dav.getCardinality(), op, owlClassIn);
			}
			else if (oeoy instanceof ObjectExistsSelf){
				ObjectExistsSelf dav = (ObjectExistsSelf)oeoy;
				org.semanticweb.kaon2.api.owl.elements.ObjectProperty op = KAON2Manager.factory().objectProperty(dav.getObjectProperty().getURI());											
				ddm = KAON2Manager.factory().objectSelf(op);
			}
			else if (oeoy instanceof ObjectHasValue){
				ObjectHasValue dav = (ObjectHasValue)oeoy;
				org.semanticweb.kaon2.api.owl.elements.ObjectProperty op = KAON2Manager.factory().objectProperty(dav.getObjectProperty().getURI());
				org.semanticweb.kaon2.api.owl.elements.Individual in = KAON2Manager.factory().individual(dav.getValue().getURI());
				ddm = KAON2Manager.factory().objectHasValue(op, in);
			}
			else if (oeoy instanceof ObjectIntersectionOf){
				ObjectIntersectionOf dav = (ObjectIntersectionOf)oeoy;
				List<org.semanticweb.kaon2.api.owl.elements.Description> dpes = new LinkedList<org.semanticweb.kaon2.api.owl.elements.Description>();
				for (Description dp : dav.getOWLClasses()){
					dpes.add(transformDescription(dp));
				}
				ddm = KAON2Manager.factory().objectAnd(dpes);
			}
			else if (oeoy instanceof ObjectMaxCardinality){
				ObjectMaxCardinality dav = (ObjectMaxCardinality)oeoy;
				org.semanticweb.kaon2.api.owl.elements.ObjectProperty op = KAON2Manager.factory().objectProperty(dav.getObjectProperty().getURI());
				org.semanticweb.kaon2.api.owl.elements.Description owlClassIn=transformDescription(dav.getOWLClass());
				ddm = KAON2Manager.factory().objectCardinality(-1, dav.getCardinality(), op, owlClassIn);
			}
			else if (oeoy instanceof ObjectMinCardinality){
				ObjectMinCardinality dav = (ObjectMinCardinality)oeoy;
				org.semanticweb.kaon2.api.owl.elements.ObjectProperty op = KAON2Manager.factory().objectProperty(dav.getObjectProperty().getURI());
				org.semanticweb.kaon2.api.owl.elements.Description owlClassIn=transformDescription(dav.getOWLClass());
				ddm = KAON2Manager.factory().objectCardinality(dav.getCardinality(), -1, op, owlClassIn);
			}
			else if (oeoy instanceof ObjectOneOf){
				ObjectOneOf dav = (ObjectOneOf)oeoy;
				List<org.semanticweb.kaon2.api.owl.elements.Individual> dpes = new LinkedList<org.semanticweb.kaon2.api.owl.elements.Individual>();
				for (Individual dp : dav.getIndividuals()){
					dpes.add(KAON2Manager.factory().individual(dp.getURI()));
				}
				ddm = KAON2Manager.factory().objectOneOf(dpes);
			}
			else if (oeoy instanceof ObjectSomeValuesFrom){
				ObjectSomeValuesFrom dav = (ObjectSomeValuesFrom)oeoy;
				org.semanticweb.kaon2.api.owl.elements.ObjectProperty op = KAON2Manager.factory().objectProperty(dav.getObjectProperty().getURI());
				org.semanticweb.kaon2.api.owl.elements.Description owlClassIn=transformDescription(dav.getOWLClass());											
				ddm = KAON2Manager.factory().objectSome(op, owlClassIn);
			}
			else if (oeoy instanceof ObjectUnionOf){
				ObjectUnionOf dav = (ObjectUnionOf)oeoy;
				List<org.semanticweb.kaon2.api.owl.elements.Description> dpes = new LinkedList<org.semanticweb.kaon2.api.owl.elements.Description>();
				for (Description dp : dav.getOWLClasses()){
					dpes.add(transformDescription(dp));
				}
				ddm = KAON2Manager.factory().objectOr(dpes);
			}
		}catch (KAON2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ddm;
	}
	
}

/*
private static String getEntityType(String name, Ontology changedOnto)  {
	try{
        Request<Entity> entityRequest=changedOnto.createEntityRequest();
        Cursor<Entity> cursor=entityRequest.openCursor();
        while (cursor.hasNext()) {
            Entity entity=cursor.next();
            if (name.equalsIgnoreCase(Namespaces.guessLocalName(entity.getURI()))){ 
            	if (entity instanceof org.semanticweb.kaon2.api.owl.elements.DataProperty)
            		return "DataProperty";
            	else if (entity instanceof org.semanticweb.kaon2.api.owl.elements.ObjectProperty)
            		return "ObjectProperty";
            	else if (entity instanceof OWLClass)
            		return "OWLClass";
            	else if (entity instanceof org.semanticweb.kaon2.api.owl.elements.Datatype)
            		return "Datatype";
            	else if (entity instanceof org.semanticweb.kaon2.api.owl.elements.Individual)
            		return "Individual";
            }
        }
	}
    catch (KAON2Exception e) {
    	System.err.println(e + " in checkdataproperty()");
    }
    return "";
}
*/