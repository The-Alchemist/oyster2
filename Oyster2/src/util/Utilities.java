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
 * @version $Revision: 1.3 $
 **/
public class Utilities {

	static private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	public static final int STRING_TYPE = 0;
	public static final int INTEGER_TYPE = 1;
	public static final int FLOAT_TYPE = 2;
	public static final int BOOLEAN_TYPE = 3;
//	KAON2 PROBLEM
    public static String getString(Object prop){
    	if (prop!=null){
    		if ((prop.toString().equalsIgnoreCase("\"true\"^^<xsd:boolean>")) ||
    			(prop.toString().equalsIgnoreCase("\"false\"^^<xsd:boolean>")) ||
    			(prop.toString().contains("<xsd:unsignedInt>")) ||
    			(prop.toString().contains("<xsd:integer>")) ||
    			(prop.toString().contains("<xsd:float>")) 
    			)
    				return prop.toString();
    		else return prop.toString().substring(1, prop.toString().length()-1);
    	}
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
    
    public static int getDType(String prop, Ontology wOntology, String pURI){
    	//Ontology typeOntology = mOyster2.getTypeOntology();
    	try{
    		DataProperty ontologyDataProperty = KAON2Manager.factory().dataProperty(pURI + prop);
    		Set<DataRange> ranges=ontologyDataProperty.getRangeDataRanges(wOntology);
    		//System.out.println("range    "+ranges.toString());
    		for (DataRange range : ranges){
    			if (range.toString().equalsIgnoreCase("xsd:integer")) return INTEGER_TYPE;
    			else if (range.toString().equalsIgnoreCase("xsd:float")) return FLOAT_TYPE;
    			else if (range.toString().equalsIgnoreCase("xsd:boolean")) return BOOLEAN_TYPE;
    			else return STRING_TYPE;
    		}
    	}
    	catch (KAON2Exception e) {
	    	System.err.println(e + " in getDType");
	    	return -1;
	    }
    	return -1;
    }

}
