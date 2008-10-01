/**
 * 
 */
package org.neontoolkit.oyster2.client.core.querymanager.querybuilders;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.oasis.names.tc.ebxml_regrep.xsd.query.BranchType;

/**
 * @author David Muñoz
 *
 */
public class QueryObjectBuildersRepository {
	
	private static final String CONFIG_FILE_NAME = "query" + File.separator + 
		"QueryObjectBuildersRepositoryConfiguration.properties";
	
	private static final String QUERY_TYPE_CLASSES_KEY = "queryTypeClasses";
	/*
	private final static Class[] queryTypeClasses = {
		FormalityLevelQueryType.class,KnowledgeRepresentationParadigmQueryType.class,
		LicenseModelQueryType.class,OntologyQueryType.class,
		OntologyDomainQueryType.class, OntologyEngineeringMethodologyQueryType.class,
		OntologyEngineeringToolQueryType.class,
		OntologyLanguageQueryType.class, OntologySyntaxQueryType.class,
		OntologyTaskQueryType.class,OntologyTypeQueryType.class,
		PersonQueryType.class,OrganizationQueryType.class
	};
	*/
	
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
		String configPath = Activator.getDefault().getResourcesDir() + CONFIG_FILE_NAME;
		PropertiesConfiguration configuration = null;
		try {
			 configuration = new PropertiesConfiguration(configPath);
		
		String []queryTypeClasses = null;
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
		queryTypeClasses = configuration.getStringArray(QUERY_TYPE_CLASSES_KEY);
		for(i = 0;i<queryTypeClasses.length;i++) {
			queryTypeClass = Class.forName(queryTypeClasses[i]);
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
		
	public static Method getMethod(String domainClassName,String methodName) {
		
		return methods.get(domainClassName).get(methodName);
	}
	
	public static Boolean isBranch(String domainClassName,String methodName) {
		return branches.get(domainClassName).get(methodName);
	}
}
