/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.util;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.neontoolkit.oyster2.client.gui.Activator;

/**
 * @author david
 *
 */
public class Template {
	private Map<String,List<String>> templates = null;
	
	private String defaultTemplate = null;
	
	private static final String DEFAULT_TEMPLATE_KEY = "templates.default";
	
	private static final String TEMPLATE_LIST_KEY ="templates";
	
	public Template(String filename) {
		String path = Activator.getDefault().getResourcesDir() +
			File.separator + filename;
		//String path = filename;
		try {
			
			PropertiesConfiguration configuration =
				new PropertiesConfiguration(path);
			templates = new TreeMap<String,List<String>>();
			String [] templateNames = configuration.getStringArray(TEMPLATE_LIST_KEY);
			List<String> templatePropeties = null;
			for (String templateName : templateNames) {
				templatePropeties = configuration.getList(templateName);
				templates.put(templateName,templatePropeties);
			}
			defaultTemplate = configuration.getString(DEFAULT_TEMPLATE_KEY);
			
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public Set<String> getTemplateNames() {
		return templates.keySet();
	}
	
	public List<String> getTemplateProperties(String templateName) {
		return templates.get(templateName);
	}

	public String getDefaultTemplate() {
		return defaultTemplate;
	}

	public void setDefaultTemplate(String defaultTemplate) {
		this.defaultTemplate = defaultTemplate;
	}
	
	
}
