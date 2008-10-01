/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.search.composites;


import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.search.Filter;
import org.neontoolkit.oyster2.client.gui.search.FilterType;
import org.neontoolkit.oyster2.client.gui.search.model.IQueryDomainClassAttribute;

/**
 * @author David Muñoz
 *
 */
public class FilterCompositeFactory {
	
	private static FilterCompositeFactory factory = null;
	
	private static Map<String,Class> customComposites = null;
	
	//maps rdftype with omvclass
	//private static Map<String,Class> targetDescriptions = null;
	private static Class[] constructorParameters = {Composite.class,
		int.class,String.class,String[].class};
	
	static {
		factory = new FilterCompositeFactory();
	}
	
	public FilterCompositeFactory() {
		String path = Activator.getDefault().getResourcesDir();
		customComposites = new HashMap<String, Class>();
		try {
			PropertiesConfiguration configuration =
				new PropertiesConfiguration(path+ "/FilterCompositeFactory.properties");
			List<String> compositeNames = configuration.getList("customComposites");
			List<String> classes = null;
			Class compositeClass = null;
			path = FilterCompositeFactory.class.getPackage().getName();
			for (String compositeName : compositeNames) {
				classes = configuration.getList(compositeName);
				compositeClass = Class.forName(path + "." + compositeName);
				for (String className : classes) {
					customComposites.put(className, compositeClass);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public FilterComposite getComposite(Composite parent,
			IQueryDomainClassAttribute attribute,String section) {
		String [] predefined = attribute.getPredefined();
		String attributeDomain = attribute.getAttributeClass();
		if (attributeDomain != null) {
			Class compositeClass = customComposites.get(attributeDomain);
			if (compositeClass != null)
				return makeCustomComposite(parent,attribute,section,predefined);
		}

		FilterComposite composite;
		FilterType filterType = attribute.getFilterType();
		Filter filter = new Filter();
		filter.setType(attribute.getFilterType());
		filter.setMulti(attribute.hasMultipleParameters());
		String []comparators =
			FilterType.getComparators().get(attribute.getFilterType());
		if (filterType.equals(FilterType.PersonFilterType))  {
			composite = new PersonFilterComposite(parent,
					SWT.NONE,section,predefined,comparators,filter);
		}
		else if (attribute.getMultivalued()) {
			composite = new MultivalueFilterComposite(parent,SWT.NONE,section,
					predefined,	comparators,filter);
		}
		else {
			//filters with a single input
			if (filterType.equals(FilterType.DateTimeFilterType)) {
				composite = new DateFilterComposite(parent,SWT.NONE,
						section,predefined,comparators,filter);
			}
			else {
				composite = new SingleFilterComposite(parent,SWT.NONE,section,
						predefined,	comparators,filter);
			}
		}
		return composite;
	}
	
	
	private FilterComposite makeCustomComposite(Composite parent,
			IQueryDomainClassAttribute attribute, String section,
			String[] predefined) {
		
		Class compositeClass = customComposites.get(attribute.getAttributeClass());
		
		try {
			Constructor constructor = compositeClass.getConstructor(constructorParameters);
			
			//////////////////////
			Object []args = new Object[]{parent,SWT.NONE,section,predefined};
			
			FilterComposite composite = (FilterComposite)
				constructor.newInstance(args[0],args[1],args[2],
						args[3]);
			return composite;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}


	public static FilterCompositeFactory getInstance() {
		return factory;
	}
	
}
