package org.neon_toolkit.registry.api.properties;


import java.util.LinkedList;
import org.neon_toolkit.owlodm.api.Description;
import org.neon_toolkit.owlodm.api.OWLEntity;
import org.neon_toolkit.registry.oyster2.Constants;
import org.neon_toolkit.registry.oyster2.OntologyProperty;

/**
 * The class AxiomProperties provides the methods to
 * retrieve the properties from Axiom objects 
 * representing the object of the registry 
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class OWLEntityProperties{
	//static private ImportOntology IOntology= new ImportOntology();
	//static private String ontologyChangedURI="";
	
	@SuppressWarnings("unchecked")
	static public LinkedList getOWLEntityProperties(OWLEntity m){
		//Ignore now axiom annotation
		
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		
		if (m instanceof Description){
			tProperties.addAll(DescriptionProperties.getDescriptionProperties((Description)m));
		}else{
			if (m.getURI()!=null) {
				OntologyProperty tPropURN = new OntologyProperty(Constants.URI, m.getURI());
				tProperties.add(tPropURN);
			}
		}
		return tProperties;
	}
	
	public static String getOWLEntityConcept(OWLEntity a){
		if (a.getClass().getSimpleName()!=null && a.getClass().getSimpleName()!="") return a.getClass().getSimpleName();
		else return "";
	}
}

