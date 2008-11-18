package org.neontoolkit.changelogging.core;

public class Constants {
	public static final String logFolder = 
		System.getProperty("user.dir") + System.getProperty("file.separator") + 
		"ChangeLogs" + System.getProperty("file.separator");
	
	public static final String XML_ROOT = "ChangeLog";
	public static final String ELEM_ENTRY = "Entry";
	public static final String ELEM_ARG = "Argument";

	public static final String ATTR_ONTO = "Ontology";
	public static final String ATTR_TYPE = "ChangeType";
	public static final String ATTR_MODULE = "Module";
	public static final String ATTR_PREDICATE = "Predicate";
	public static final String ATTR_TIME = "TimeStamp";
	public static final String ATTR_INDEX = "Index";
	
	public static final String ACTION_CLASS = "classDeclaration";
	public static final String ACTION_CLASS_SUBOF = "subClassOf";
	public static final String ACTION_CLASS_EQ = "equivalent";
	public static final String ACTION_CLASS_DISJ = "disjoint";
	public static final String ACTION_CLASS_ANN = "classAnnotation";
	public static final String ACTION_CLASS_DISJUN = "disjointUnion";  //NOT TESTED HOW TO DO IT IN NTK?
	
	public static final String ACTION_OBPROPERTY = "objectDeclaration";
	public static final String ACTION_OBPROPERTY_SUBOF = "subObjectPropertyOf";
	public static final String ACTION_OBPROPERTY_DOMAIN = "objectDomain";
	public static final String ACTION_OBPROPERTY_RANGE = "objectRange";
	public static final String ACTION_OBPROPERTY_EQ = "objectEquivalent";
	public static final String ACTION_OBPROPERTY_FUNC = "objectFunctional";
	public static final String ACTION_OBPROPERTY_INVFUNC = "objectInverseFunctional";
	public static final String ACTION_OBPROPERTY_TRA = "objectTransitive";
	public static final String ACTION_OBPROPERTY_SYM = "objectSymmetric";
	public static final String ACTION_OBPROPERTY_INV = "objectInverse";
	public static final String ACTION_OBPROPERTY_ANN = "objectAnnotation";
	public static final String ACTION_OBPROPERTY_ASY = "objectAsymmetric"; //NOT TESTED HOW TO DO IT IN NTK?
	public static final String ACTION_OBPROPERTY_DIS = "objectDisjoint"; //NOT TESTED HOW TO DO IT IN NTK?
	public static final String ACTION_OBPROPERTY_IRR = "objectIrreflexive"; //NOT TESTED HOW TO DO IT IN NTK?
	public static final String ACTION_OBPROPERTY_REF = "objectReflexive"; //NOT TESTED HOW TO DO IT IN NTK?
	
	public static final String ACTION_DAPROPERTY = "dataDeclaration";
	public static final String ACTION_DAPROPERTY_SUBOF = "subDataPropertyOf";
	public static final String ACTION_DAPROPERTY_DOMAIN = "dataDomain";
	public static final String ACTION_DAPROPERTY_RANGE = "dataRange";
	public static final String ACTION_DAPROPERTY_EQ = "dataEquivalent";
	public static final String ACTION_DAPROPERTY_FUNC = "dataFunctional"; //ERROR IN NTK (now is objectFunctional)
	public static final String ACTION_DAPROPERTY_ANN = "dataAnnotation"; //ERROR IN NTK (it adds data annotation and then object annotation)
	public static final String ACTION_DAPROPERTY_DIS = "dataDisjoint"; //NOT TESTED HOW TO DO IT IN NTK?
	
	public static final String ACTION_ANPROPERTY = "annotationDeclaration";
	
	public static final String ACTION_DATATYPE = "datatypeDeclaration";
	
	public static final String ACTION_INDIVIDUAL = "individualDeclaration";
	public static final String ACTION_INDIVIDUAL_MEM = "classMember";
	public static final String ACTION_INDIVIDUAL_SAM = "same";
	public static final String ACTION_INDIVIDUAL_DIF = "different";
	public static final String ACTION_INDIVIDUAL_DATAASSERTION = "dataMember";
	public static final String ACTION_INDIVIDUAL_OBJECTASSERTION = "objectMember";
	public static final String ACTION_INDIVIDUAL_ANN = "individualAnnotation";	
	public static final String ACTION_INDIVIDUAL_NEGDATAASSERTION = "negativeDataMember"; //NOT WORKING IN NTK
	public static final String ACTION_INDIVIDUAL_NEGOBJECTASSERTION = "negativeObjectMember"; //NOT WORKING IN NTK
	
	public static final String ACTION_DATYPE_ANN = "datatypeAnnotation";
	
	public static final String OWL_THINGS = "OWL_Things";
}
