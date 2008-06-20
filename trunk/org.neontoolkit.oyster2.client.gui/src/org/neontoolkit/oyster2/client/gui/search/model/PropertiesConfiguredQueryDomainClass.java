/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.search.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.search.FilterType;

/**
 * @author David Muñoz
 *
 */
public class PropertiesConfiguredQueryDomainClass implements IQueryDomainClass {
	
	//attributes mapped with their internal (non-translated) name
	private Map<String,IQueryDomainClassAttribute> attributes = null;
	
	private Map<String,String[]> categories = null;
	
	private List<String> orderedList = null;
	
	private Class targetJavaClass = null;
	
	private String defaultQueryAttribute = null;
	
	public PropertiesConfiguredQueryDomainClass() {
		attributes = new HashMap<String, IQueryDomainClassAttribute>();
		orderedList = new LinkedList<String>();
	}
	
	public void init(String[] initArgs)  {
		String path = Activator.getDefault().getResourcesDir() + initArgs[0];
		String []categories = null;
		String className = null;
		
		try {
			PropertiesConfiguration configuration = 
				new PropertiesConfiguration(path);
			categories = configuration.getStringArray("categories");
			
			if (categories != null) {
				loadCategories(configuration,categories);
			}
			loadAttributes(configuration);
			className = configuration.getString("targetJavaClass");
			targetJavaClass = Class.forName(className);
			
		} catch (ConfigurationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("Please check the class " + className +
					"exists  as specified in " + path);
			throw new RuntimeException(e);
		}
		
	}
	
	

	private void loadCategories(PropertiesConfiguration configuration,
			String[] categories) {
		this.categories = new HashMap<String, String[]>();
		String [] attributes = null;
		for (int i = 0; i < categories.length;i++) {
			attributes = configuration.getStringArray(categories[i]);
			this.categories.put(categories[i],attributes);	
		}
		
	}

	private void loadAttributes(PropertiesConfiguration configuration) {
		String propertyValue = null;
		String attributeName = null;
		IQueryDomainClassAttribute attribute = null;
		String[] attributes = configuration.getStringArray("attributes");
		String [] predefinedValues = null;
		for ( int i = 0; i<attributes.length; i++) {
			attributeName = attributes[i];
			attribute = new SimpleQueryDomainClassAttribute();
			
			//number of parameters in the query?
			propertyValue = configuration.getString(attributeName + ".multipleParameters"); 
			attribute.setMultipleParameters(Boolean.valueOf(propertyValue));
			
			//multivalued?
			propertyValue = configuration.getString(attributeName + ".multiple"); 
			attribute.setMultivalued(Boolean.valueOf(propertyValue));
			
			
			
			//setter method
			propertyValue = configuration.getString(attributeName + ".setterMethod"); 
			attribute.setSetterMethodName(propertyValue);
			
			//filter type
			propertyValue = configuration.getString(attributeName + ".filterType"); 
			attribute.setFilterType(FilterType.get(propertyValue));
			
			//attribute type (if not primitive)
			propertyValue = configuration.getString(attributeName + ".class"); 
			attribute.setAttributeClass(propertyValue);
			
			//predefined values
			predefinedValues = configuration.getStringArray(attributeName + ".predefined");
			if (predefinedValues != null) {
				if (predefinedValues.length != 0) {
					attribute.setPredefined(predefinedValues);
				}
			}
			this.attributes.put(attributeName,attribute);
			orderedList.add(attributeName);
		}
		defaultQueryAttribute = configuration.getString("default.query.attribute");
		
	}

	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.search.model.IQueryDomainClass#getAttribute(java.lang.String)
	 */
	public IQueryDomainClassAttribute getAttribute(String attributeName) {
		return attributes.get(attributeName);
	}

	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.search.model.IQueryDomainClass#getAttributeNames()
	 */
	public String[] getAttributeNames() {
		String[] list = new String[orderedList.size()];
		return orderedList.toArray(list);
	}

	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.search.model.IQueryDomainClass#getCategoryNames()
	 */
	public String[] getCategoryNames() {
		String[] names = new String[categories.size()];
		return categories.keySet().toArray(names);
	}

	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.search.model.IQueryDomainClass#getTargetJavaClass()
	 */
	public Class getTargetJavaClass() {
		return targetJavaClass;
	}

	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.search.model.IQueryDomainClass#hasCategories()
	 */
	public boolean hasCategories() {
		return categories != null;
	}

	public String[] getCategoryAttributes(String category) {
		return this.categories.get(category);
	}

	/**
	 * @return the defaultQueryAttribute
	 */
	public final String getDefaultQueryAttribute() {
		return defaultQueryAttribute;
	}

	/**
	 * @param defaultQueryAttribute the defaultQueryAttribute to set
	 */
	public final void setDefaultQueryAttribute(String defaultQueryAttribute) {
		this.defaultQueryAttribute = defaultQueryAttribute;
	}

	
}
