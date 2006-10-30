package util;

import java.util.Set;

import org.semanticweb.kaon2.api.*;
import org.semanticweb.kaon2.api.owl.elements.*;

import oyster2.*;

/**
 * Constants for RDF Schema primitives and for the RDF Schema namespace.
 *
 * @author Jeen Broekstra
 *
 * @version $Revision: 1.1 $
 **/
public class Utilities {

	static private Oyster2 mOyster2 = Oyster2.sharedInstance();
//	KAON2 PROBLEM
    public static String getString(Object prop){
    	if (prop!=null)
    		if ((prop.toString().equalsIgnoreCase("\"true\"^^<xsd:boolean>")) ||
    			(prop.toString().equalsIgnoreCase("\"false\"^^<xsd:boolean>")))
    				return prop.toString();
    		else return prop.toString().substring(1, prop.toString().length()-1);
    	else return null;
    	
    }
    public static Boolean isInt(String prop){
    	Ontology typeOntology = mOyster2.getTypeOntology();
    	try{
    		DataProperty ontologyDataProperty = KAON2Manager.factory().dataProperty(Constants.OMVURI + prop);
    		Set<DataRange> ranges=ontologyDataProperty.getRangeDataRanges(typeOntology);
    		//System.out.println("range    "+ranges.toString());
    		for (DataRange range : ranges){
    			
    			if (range.toString().equalsIgnoreCase("xsd:integer")) return true;
    			else return false;
    		}
    	}
    	catch (KAON2Exception e) {
	    	System.err.println(e + " in isInt");
	    	return false;
	    }
    	return false;
    }
}
