/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.search.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.IMessageResolver;
import org.neontoolkit.oyster2.client.gui.MessageResolver;

/**
 * @author David Muñoz
 *
 */
public class QueryFactory {
	
	private Map<String,IQueryDomainClass> domainClasses = null;
	
	private static QueryFactory instance = null;
	
	private Map<String,String> resourceBundleNames = null;
	
	//we can't use rdf names in property files as keys, so this
	//maps a rdf type to a name used by this plugin to identify it
	private Map<String,String> internalNames = null;
	
	static {
		instance = new QueryFactory();
	}
	
	public static QueryFactory getInstance() {
		return instance;
	}
	
	public QueryFactory() {
		
		String [] initArgs = null;
		domainClasses = new HashMap<String, IQueryDomainClass>();
		internalNames = new HashMap<String, String>();
		resourceBundleNames = new HashMap<String, String>();
		try {
			IQueryDomainClass domainClass = null;
			String  path = Activator.getDefault().getResourcesDir() + "query/";
			PropertiesConfiguration configuration = 
				new PropertiesConfiguration(path + "QueryFactoryConfiguration.properties");
			String domainClassRDF = null;
			String bundleName = null;
			String [] domainClassNames =
				configuration.getStringArray("domainClassNames");

			for (int i = 0; i < domainClassNames.length; i++) {
				bundleName = this.getClass().getPackage().getName() + "." +
				configuration.getString(domainClassNames[i] +
						".bundle");
				initArgs = configuration.getStringArray(domainClassNames[i] +
						".init.args");
				domainClassRDF =
					configuration.getString(domainClassNames[i] +
							".rdf");
				domainClass = makeDomainClass(initArgs);
				this.resourceBundleNames.put(domainClassRDF, bundleName);
				this.domainClasses.put(domainClassRDF,domainClass);
				this.internalNames.put(domainClassRDF,domainClassNames[i]);
			}
			
		} catch (ConfigurationException e) {
			
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private IQueryDomainClass makeDomainClass(String[] initArgs) {
		PropertiesConfiguredQueryDomainClass queryDomainClass =
			new PropertiesConfiguredQueryDomainClass();
		queryDomainClass.init(initArgs);
		return queryDomainClass;
	}

	public String[] getQueryTargets() {
		String [] targets = new String[domainClasses.size()];
		return this.domainClasses.keySet().toArray(targets);
	}
	
	
	public String[] getInternalNames() {
		String [] targets = new String[internalNames.size()];
		return this.internalNames.values().toArray(targets);
	}
	
	public IQueryDomainClass getQueryTargetDescription(String targetName) {
		return this.domainClasses.get(targetName);
	}

	public String getInternalName(String rdfType) {
		return internalNames.get(rdfType);
	}
	
	
	public IMessageResolver getMessageBundle(String rdfClassName) {
		String bundleName = resourceBundleNames.get(rdfClassName);
		IMessageResolver messageResolver = new MessageResolver(bundleName);
		return messageResolver;
	}
}
