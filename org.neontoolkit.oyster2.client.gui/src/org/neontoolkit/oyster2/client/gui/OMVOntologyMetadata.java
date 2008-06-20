/**
 * 
 */
package org.neontoolkit.oyster2.client.gui;



/**
 * @author David Muñoz
 *
 */
public class OMVOntologyMetadata {

	
	public static String ONTOLOGY_ATTRIBUTES_FILE = Activator.getDefault().getResourcesDir() +
	"ontologyAttributes.xml";
	
	public static String ONTOLOGY_QUERY_ATTRIBUTES_FILE = Activator.getDefault().getResourcesDir() +
	"ontologyQueryAttributes.xml";
	
	public static final String CATEGORIES_KEY ="categories";
	
	
	//////////////////////////// OLD VALUES TO BE DELETED
	//public static final String ONTOLOGY_ATTRIBUTES_KEY = "ontology.attributes";
	
	public static final String[] REQUIRED_ATTRIBUTES = {
		"URI","name","description","creationDate","hasCreator","isOfType",
		"hasOntologyLanguage","hasOntologySyntax","resourceLocator",
		"version","numberOfClasses","numberOfProperties","numberOfIndividuals",
		"numberOfAxioms",
	};
		
}
