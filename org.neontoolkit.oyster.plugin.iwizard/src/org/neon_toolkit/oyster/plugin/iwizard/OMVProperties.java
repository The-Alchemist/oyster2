package org.neon_toolkit.oyster.plugin.iwizard;

import java.util.Hashtable;

public class OMVProperties {

    private static Hashtable<String,String> properties = null;
        
    private  static void initOMVPropertiesFormat() {
    	
        if (properties == null) {        
            properties = new Hashtable<String, String>();

            String format = "Format (operator): ";
            String format1 = "Format (operator) [property]: ";
            
            properties.put("URI", format + "String (like)");
            properties.put("name", format + "String (like)");
            properties.put("acronym", format + "String (like)");
            properties.put("description", format + "String (like)");
            properties.put("documentation", format + "String (like)");
			properties.put("creationDate", format + "YYYY[-MM[-DD]] (like)");
			properties.put("notes", format + "String (like)");
			properties.put("modificationDate", format + "YYYY[-MM[-DD]] (like)");
			properties.put("naturalLanguage", format + "String (like)");
			properties.put("version", format + "String (like)");
			properties.put("keywords", format + "String (like)");
			properties.put("numberOfIndividuals", format + "Integer (>=)");
			properties.put("numberOfClasses", format + "Integer (>=)");
			properties.put("isConsistentAccordingToReasoner", format + "true/false (=)");
			properties.put("containsRBox", format + "true/false (=)");
			properties.put("numberOfAxioms", format + "Integer (>=)");
			properties.put("status", format + "String (like)");
			properties.put("numberOfProperties", format + "Integer (>=)");
			properties.put("keyClasses", format + "String (like)");
			properties.put("resourceLocator", format + "String (like)");
			properties.put("containsTBox", format + "true/false (=)");
			properties.put("knownUsage", format + "String (like)");
			properties.put("containsABox", format + "true/false (=)");
			properties.put("expressiveness", format + "String (like)");		
			
			properties.put("hasPriorVersion", format1 + "String (like) [URI]");
			properties.put("designedForOntologyTask", format1 + "String (like) [name]");
			properties.put("isIncompatibleWith", format1 + "String (like) [URI]");
			properties.put("hasOntologyLanguage", format1 + "String (like) [name]");
			properties.put("hasFormalityLevel", format1 + "String (like) [name]");
			properties.put("hasOntologySyntax", format1 + "String (like) [name]");
			properties.put("useImports", format1 + "String (like) [URI]");
			properties.put("hasContributor", format1 + "String (like) [[firstName] lastName]");
			properties.put("usedOntologyEngineeringTool", format1 + "String (like) [name]");
			properties.put("endorsedBy", format1 + "String (like) [[firstName] lastName]");
			properties.put("usedKnowledgeRepresentationParadigm", format1 + "String (like) [name]");
			properties.put("hasLicense", format1 + "String (like) [name]");
			properties.put("hasDomain", format1 + "String (like) [URI]");
			properties.put("usedOntologyEngineeringMethodology", format1 + "String (like) [name]");
			properties.put("isBackwardCompatibleWith", format1 + "String (like) [URI]");
			properties.put("hasCreator", format1 + "String (like) [[firstName] lastName]");
			properties.put("isOfType", format1 + "String (like) [name]");				    
            
        }
    }

    public static String getFormat(String str) {    	
    	initOMVPropertiesFormat();    	
    	return properties.get(str);
    }

    public static String[] getOMVPropeties() {    	
    	initOMVPropertiesFormat();    	
    	return (String[]) properties.keySet().toArray(new String[properties.size()]);
    }

    public static void main(String[] args) {
    	System.out.println(OMVProperties.getFormat("notes"));
    }
    
}
