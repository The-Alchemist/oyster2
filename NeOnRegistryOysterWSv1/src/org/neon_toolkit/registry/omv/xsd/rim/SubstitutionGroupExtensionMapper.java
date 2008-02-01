package org.neon_toolkit.registry.omv.xsd.rim;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

public class SubstitutionGroupExtensionMapper {
	
    private static Map<QName,Method> typeMapping = new HashMap<QName,Method>();
    
    static {
  	  Class[] temp_args=new Class[1];
  	  temp_args[0]=javax.xml.stream.XMLStreamReader.class;
  	  
  	  typeMapping.put(org.neon_toolkit.registry.omv.xsd.rim.FormalityLevel.MY_QNAME, 
  			  		  org.neon_toolkit.registry.omv.xsd.rim.FormalityLevelType.Factory.class.getDeclaredMethods()[0]);
  	  typeMapping.put(org.neon_toolkit.registry.omv.xsd.rim.KnowledgeRepresentationParadigm.MY_QNAME, 
  			  		  org.neon_toolkit.registry.omv.xsd.rim.KnowledgeRepresentationParadigmType.Factory.class.getDeclaredMethods()[0]);
  	  typeMapping.put(org.neon_toolkit.registry.omv.xsd.rim.LicenseModel.MY_QNAME, 
  			  		  org.neon_toolkit.registry.omv.xsd.rim.LicenseModelType.Factory.class.getDeclaredMethods()[0]);
  	  typeMapping.put(org.neon_toolkit.registry.omv.xsd.rim.OntologyEngineeringMethodology.MY_QNAME, 
  			  		  org.neon_toolkit.registry.omv.xsd.rim.OntologyEngineeringMethodologyType.Factory.class.getDeclaredMethods()[0]);
  	  typeMapping.put(org.neon_toolkit.registry.omv.xsd.rim.OntologyEngineeringTool.MY_QNAME,
  			  		  org.neon_toolkit.registry.omv.xsd.rim.OntologyEngineeringToolType.Factory.class.getDeclaredMethods()[0]);
  	  typeMapping.put(org.neon_toolkit.registry.omv.xsd.rim.OntologyLanguage.MY_QNAME, 
  			  		  org.neon_toolkit.registry.omv.xsd.rim.OntologyLanguageType.Factory.class.getDeclaredMethods()[0]);
  	  typeMapping.put(org.neon_toolkit.registry.omv.xsd.rim.Ontology.MY_QNAME, 
  			  		  org.neon_toolkit.registry.omv.xsd.rim.Ontology_Type.Factory.class.getDeclaredMethods()[0]);
  	  typeMapping.put(org.neon_toolkit.registry.omv.xsd.rim.OntologySyntax.MY_QNAME, 
  			  		  org.neon_toolkit.registry.omv.xsd.rim.OntologySyntaxType.Factory.class.getDeclaredMethods()[0]);
  	  typeMapping.put(org.neon_toolkit.registry.omv.xsd.rim.OntologyTask.MY_QNAME, 
  			  		  org.neon_toolkit.registry.omv.xsd.rim.OntologyTaskType.Factory.class.getDeclaredMethods()[0]);
  	  typeMapping.put(org.neon_toolkit.registry.omv.xsd.rim.OntologyType.MY_QNAME, 
  			  		  org.neon_toolkit.registry.omv.xsd.rim.OntologyType_Type.Factory.class.getDeclaredMethods()[0]);
  	  typeMapping.put(org.neon_toolkit.registry.omv.xsd.rim.OntologyDomain.MY_QNAME, 
		  		  	  org.neon_toolkit.registry.omv.xsd.rim.OntologyDomainType.Factory.class.getDeclaredMethods()[0]);
  	  typeMapping.put(org.oasis.names.tc.ebxml_regrep.xsd.rim.Organization.MY_QNAME, 
  			  		  org.oasis.names.tc.ebxml_regrep.xsd.rim.OrganizationType.Factory.class.getDeclaredMethods()[0]);
  	  typeMapping.put(org.oasis.names.tc.ebxml_regrep.xsd.rim.Person.MY_QNAME, 
  			  		  org.oasis.names.tc.ebxml_regrep.xsd.rim.PersonType.Factory.class.getDeclaredMethods()[0]);
  	  typeMapping.put(org.oasis.names.tc.ebxml_regrep.xsd.rim.ObjectRef.MY_QNAME, 
  			  		  org.oasis.names.tc.ebxml_regrep.xsd.rim.ObjectRefType.Factory.class.getDeclaredMethods()[0]);
    }

    public static java.lang.Object getSubstitutionGroupTypeObject(java.lang.String namespaceURI,
            java.lang.String typeName,
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
  	  return getSubstitutionGroupTypeObject(new QName(namespaceURI,typeName),reader);
    }
    
    public static java.lang.Object getSubstitutionGroupTypeObject(QName typeName,
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {

	  	  if (typeMapping.containsKey(typeName)) {
	  		  	try {
	  		  		Method temp_meth=typeMapping.get(typeName);
			    		return temp_meth.invoke(temp_meth.getDeclaringClass().newInstance(), reader);
			  		} catch (Exception e) {
			  			e.printStackTrace();
					}
	  	  }
	      throw new org.apache.axis2.databinding.ADBException("Unsupported type or error while parsing " + typeName.toString());
    }

    public static boolean getSubstitutionGroupTypeCheck(java.lang.String namespaceURI,
            java.lang.String typeName) throws java.lang.Exception{
    	return getSubstitutionGroupTypeCheck(new QName(namespaceURI, typeName));
    }

    public static boolean getSubstitutionGroupTypeCheck(QName typeName) throws java.lang.Exception{
    	return typeMapping.containsKey(typeName);
    }
    
    public static QName getSubstitutionGroupTypeQName(Class obj) throws Exception {
  	  for (QName temp_qname:typeMapping.keySet()) {
  		  if (typeMapping.get(temp_qname).getDeclaringClass().getDeclaringClass().isAssignableFrom(obj))
  				  return temp_qname;
  	  }
        throw new org.apache.axis2.databinding.ADBException("Unsupported type or error while parsing " + obj.getName());
    }
    
    
}
