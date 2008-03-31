package org.neon_toolkit.registry.api.properties;



import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.neon_toolkit.omv.api.core.OMVOntology;
import org.neon_toolkit.omv.api.core.OMVPerson;
import org.neon_toolkit.omv.api.extensions.change.OMVChange;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVAtomicChange;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVCompositeChange;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVEntityChange;
import org.neon_toolkit.owlodm.api.Axiom;
import org.neon_toolkit.registry.oyster2.Constants;
import org.neon_toolkit.registry.oyster2.ImportOntology;
import org.neon_toolkit.registry.oyster2.OntologyProperty;
import org.neon_toolkit.registry.util.GUID;
import org.neon_toolkit.registry.api.properties.OMVProperties;

/**
 * The class ChangeProperties provides the methods to
 * retrieve the properties from OMV Change objects 
 * representing the object of the registry 
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class ChangeProperties{
	static private ImportOntology IOntology= new ImportOntology();
	static private String ontologyChangedURI="";
	
	@SuppressWarnings("unchecked")
	static public LinkedList getChangeProperties(OMVChange m){
		
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		List tList = new LinkedList();
		
		if (m.getCost()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.cost, m.getCost());
			tProperties.addFirst(prop);
		}
		if (m.getDate()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.date, m.getDate());
			tProperties.addFirst(prop);
		}
		if (m.getPriority()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.priority, m.getPriority());
			tProperties.addFirst(prop);
		}
		if (m.getRelevance()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.relevance, m.getRelevance());
			tProperties.addFirst(prop);
		}
		if (m.getVersion()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.version, m.getVersion());
			tProperties.addFirst(prop);
		}
		if (m.getTime()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.time, m.getTime());
			tProperties.addFirst(prop);
		}
		if (m.getHasPreviousChange()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.hasPreviousChange, m.getHasPreviousChange());
			tProperties.addFirst(prop);
		}
		if (m.getHasAuthor().size()>0) {
			String tURN;
			Iterator it = m.getHasAuthor().iterator();
			while(it.hasNext()){
				Object t = it.next();
				if (t instanceof OMVPerson){
					OMVPerson per = (OMVPerson)t;
					if ((per.getFirstName()!=null) && (per.getLastName()!=null)){
						tURN=per.getFirstName()+per.getLastName();
						tList.clear();
						tList=OMVProperties.getPropertiesPerson(per);
						IOntology.addConceptToRegistry(1,tList,0, null);
						OntologyProperty prop = new OntologyProperty(Constants.hasAuthor, tURN);
						tProperties.addFirst(prop);
					}
				}
			}
		}
		if (m.getCauseChange().size()>0) {
			Iterator it = m.getCauseChange().iterator();
			while(it.hasNext()){
				String e = (String)it.next();
				
					OntologyProperty prop = new OntologyProperty(Constants.causeChange, e);
					tProperties.addFirst(prop);
				
			}
		}
		if (m.getAppliedToOntology()!=null) {
			ontologyChangedURI="";
			String tURN;
			OMVOntology otemp = m.getAppliedToOntology();
			if (otemp.getURI()!=null){
				tList.clear();
				tList=OMVProperties.getProperties(otemp);
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
				IOntology.addImportOntologyToRegistry(tList,1, null);
				OntologyProperty prop = new OntologyProperty(Constants.appliedToOntology, ontologyChangedURI);
				tProperties.addFirst(prop);
				
			}
		}
		if (m instanceof OMVAtomicChange){
			OMVAtomicChange t = (OMVAtomicChange)m;
			if (t.getAppliedAxiom()!=null) {
				String tURN="";
				if (t.getAppliedAxiom().getURI()!=null){
					tURN=t.getAppliedAxiom().getURI();
				}else{
					if (ontologyChangedURI.indexOf("?")>0){
						tURN=ontologyChangedURI+";";
					}else if (ontologyChangedURI.length()>0){
						tURN=ontologyChangedURI+"?";
					}
					tURN=tURN+"axiom="+GUID.getGUID().toString();
				}
				Axiom ot = t.getAppliedAxiom();
				tList.clear();
				tList=AxiomProperties.getAxiomProperties(ot);
				OntologyProperty tProp = new OntologyProperty(Constants.URI, tURN);
				tList.add(tProp);
				String concept=getAxiomConcept(ot);
				tProp = new OntologyProperty(Constants.name, concept);
				tList.add(tProp);
				IOntology.addConceptToRegistry(1,tList,50, null);
				OntologyProperty prop = new OntologyProperty(Constants.appliedAxiom, tURN);
				tProperties.addFirst(prop);
			}
		}
		if (m instanceof OMVCompositeChange){
			OMVCompositeChange t = (OMVCompositeChange)m;
			if (t.getConsistsOf().size()>0) {
				Iterator it = t.getConsistsOf().iterator();
				while(it.hasNext()){
					String nl = (String)it.next();
					OntologyProperty prop = new OntologyProperty(Constants.consistsOf, nl);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m instanceof OMVEntityChange){
			OMVEntityChange t = (OMVEntityChange)m;
			if (t.getConsistsOfAtomicOperation().size()>0) {
				Iterator it = t.getConsistsOfAtomicOperation().iterator();
				while(it.hasNext()){
					String nl = (String)it.next();
					OntologyProperty prop = new OntologyProperty(Constants.consistsOfAtomicOperation, nl);
					tProperties.addFirst(prop);
				}
			}
			if (t.getHasRelatedEntity().size()>0) {
				Iterator it = t.getHasRelatedEntity().iterator();
				while(it.hasNext()){
					String nl = (String)it.next();
					OntologyProperty prop = new OntologyProperty(Constants.hasRelatedEntity, nl);
					tProperties.addFirst(prop);
				}
			}
		}
		if (m.getURI()!=null) {
			OntologyProperty tPropURN = new OntologyProperty(Constants.URI, m.getURI());
			tProperties.add(tPropURN);
		}else{
			String tURNChange="";
			if (ontologyChangedURI.indexOf("?")>0){
				tURNChange=ontologyChangedURI+";";
			}else if (ontologyChangedURI.length()>0){
				tURNChange=ontologyChangedURI+"?";
			}
			tURNChange=tURNChange+"change="+GUID.getGUID().toString();
			OntologyProperty tPropURN = new OntologyProperty(Constants.URI, tURNChange);
			tProperties.add(tPropURN);
		}
		return tProperties;
	}
	
		
	public static String getAxiomConcept(Axiom a){
		if (a.getClass().getSimpleName()!=null && a.getClass().getSimpleName()!="") return a.getClass().getSimpleName();
		else return "";
	}
}

/*String temp="";
if (a instanceof DisjointClasses) return Constants.DisjointClassesConcept;
else if (a instanceof DisjointUnion) return Constants.DisjointUnionConcept;
else if (a instanceof EquivalentClasses) return Constants.EquivalentClassesConcept;
else if (a instanceof SubClassOf) return Constants.SubClassOfConcept;
else if (a instanceof DataPropertyDomain) return Constants.DataPropertyDomainConcept;
else if (a instanceof DataPropertyRange) return Constants.DataPropertyRangeConcept;
else if (a instanceof DisjointDataProperties) return Constants.DisjointDataPropertiesConcept;
else if (a instanceof EquivalentDataProperties) return Constants.EquivalentDataPropertiesConcept;
else if (a instanceof FunctionalDataProperty) return Constants.FunctionalDataPropertyConcept;
else if (a instanceof SubDataPropertyOf) return Constants.SubDataPropertyOfConcept;
else if (a instanceof Declaration) return Constants.DeclarationConcept;
else if (a instanceof EntityAnnotation) return Constants.EntityAnnotationConcept;
else if (a instanceof ClassAssertion) return Constants.ClassAssertionConcept;
else if (a instanceof DataPropertyAssertion) return Constants.DataPropertyAssertionConcept;
else if (a instanceof DifferentIndividuals) return Constants.DifferentIndividualsConcept;
else if (a instanceof NegativeDataPropertyAssertion) return Constants.NegativeDataPropertyAssertionConcept;
else if (a instanceof NegativeObjectPropertyAssertion) return Constants.NegativeObjectPropertyAssertionConcept;
else if (a instanceof ObjectPropertyAssertion) return Constants.ObjectPropertyAssertionConcept;
else if (a instanceof SameIndividual) return Constants.SameIndividualConcept;
else if (a instanceof AsymmetricObjectProperty) return Constants.AsymmetricObjectPropertyConcept;
else if (a instanceof DisjointObjectProperties) return Constants.DisjointObjectPropertiesConcept;
else if (a instanceof EquivalentObjectProperties) return Constants.EquivalentObjectPropertiesConcept;
else if (a instanceof FunctionalObjectProperty) return Constants.FunctionalObjectPropertyConcept;
else if (a instanceof InverseFunctionalObjectProperty) return Constants.InverseFunctionalObjectPropertyConcept;
else if (a instanceof InverseObjectProperties) return Constants.InverseObjectPropertiesConcept;
else if (a instanceof IrreflexiveObjectProperty) return Constants.IrreflexiveObjectPropertyConcept;
else if (a instanceof ObjectPropertyDomain) return Constants.ObjectPropertyDomainConcept;
else if (a instanceof ObjectPropertyRange) return Constants.ObjectPropertyRangeConcept;
else if (a instanceof ReflexiveObjectProperty) return Constants.ReflexiveObjectPropertyConcept;
else if (a instanceof SymmetricObjectProperty) return Constants.SymmetricObjectPropertyConcept;
else if (a instanceof TransitiveObjectProperty) return Constants.TransitiveObjectPropertyConcept;
else if (a instanceof SubObjectPropertyOf) return Constants.SubObjectPropertyOfConcept;

return temp;
*/