/**
 * 
 */
package org.neontoolkit.oyster2.client.core.querymanager.querybuilders;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.neontoolkit.registry.omv.xsd.query.FormalityLevelQueryType;
import org.neontoolkit.registry.omv.xsd.query.KnowledgeRepresentationParadigmQueryType;
import org.neontoolkit.registry.omv.xsd.query.LicenseModelQueryType;
import org.neontoolkit.registry.omv.xsd.query.OntologyDomainQueryType;
import org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringMethodologyQueryType;
import org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringToolQueryType;
import org.neontoolkit.registry.omv.xsd.query.OntologyLanguageQueryType;
import org.neontoolkit.registry.omv.xsd.query.OntologyQueryType;
import org.neontoolkit.registry.omv.xsd.query.OntologySyntaxQueryType;
import org.neontoolkit.registry.omv.xsd.query.OntologyTaskQueryType;
import org.neontoolkit.registry.omv.xsd.query.OntologyTypeQueryType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.BranchType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType;
import org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType;

/**
 * @author David Muñoz
 *
 */
public class QueryObjectBuildersRepository {
	
	private final static Class[] queryTypeClasses = {
		FormalityLevelQueryType.class,KnowledgeRepresentationParadigmQueryType.class,
		LicenseModelQueryType.class,OntologyQueryType.class,
		OntologyDomainQueryType.class, OntologyEngineeringMethodologyQueryType.class,
		OntologyEngineeringToolQueryType.class,
		OntologyLanguageQueryType.class, OntologySyntaxQueryType.class,
		OntologyTaskQueryType.class,OntologyTypeQueryType.class,
		PersonQueryType.class,OrganizationQueryType.class
	};
	
	
	/**
	 * The key is a class name. The class is listed in the queryTypeClasses
	 * array.
	 * The nested {@link Map} associates a method name with a {@link Method}.
	 */
	private static Map<String,Map<String,Method>> methods = null;
	
	
	/**
	 The key is a class name. The class is listed in the queryTypeClasses
	 * array.
	 * The nested {@link Map} tells if a method name matches 
	 * a branch in the query
	 */
	private static Map<String,Map<String,Boolean>> branches = null;
	
	static {
		Class queryTypeClass = null;
		String className = null;
		Map<String,Method> methodsMap = null;
		Map<String,Boolean> branchesMap = null;
		
		Method[] thisClassMethods = null;
		methods = new HashMap<String, Map<String,Method>>();
		
		//added for branches
		branches = new HashMap<String, Map<String,Boolean>>();
		Class[] parameters = null;
		int i = 0;
		int k = 0;
		for(i = 0;i<queryTypeClasses.length;i++) {
			queryTypeClass = queryTypeClasses[i];
			className = queryTypeClass.getName();
			methodsMap = new HashMap<String, Method>();
			branchesMap = new HashMap<String, Boolean>();
			
			//added for branches
			Class parameter = null;
			
			thisClassMethods = queryTypeClass.getMethods();
			for (k = 0;k<thisClassMethods.length;k++) {
				methodsMap.put(thisClassMethods[k].getName(),
						thisClassMethods[k]);
				
				//added for branches
				parameters = thisClassMethods[k].getParameterTypes();
				if (parameters.length == 0) {
					branchesMap.put(thisClassMethods[k].getName(),
							Boolean.valueOf(false));
					
				}
				else {
					parameter = thisClassMethods[k].getParameterTypes()[0];
					if (BranchType.class.isAssignableFrom(parameter)) {
						branchesMap.put(thisClassMethods[k].getName(),
								Boolean.valueOf(true));
					}
					else { // if (parameter.getSimpleName().indexOf("[")== -1) 
						branchesMap.put(thisClassMethods[k].getName(),
								Boolean.valueOf(false));
					}
				}
			}
			methods.put(className,methodsMap);
			branches.put(className,branchesMap);
			
		}
	}
		
	public static Method getMethod(String domainClassName,String methodName) {
		
		return methods.get(domainClassName).get(methodName);
	}
	
	public static Boolean isBranch(String domainClassName,String methodName) {
		return branches.get(domainClassName).get(methodName);
	}
}
