/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.neontoolkit.oyster2.client.gui.Activator;

/**
 * @author david
 *
 */
public class TemplateManager {

	private static final TemplateManager instance =
		new TemplateManager();
	
	private Map<String,Template> templates = null;
	
	private final static String LIST_OF_FILES_KEY = "template.files";
	
	public TemplateManager() {
		templates = new HashMap<String, Template>();
		String path = Activator.getDefault().getResourcesDir() +
			File.separator + "TemplateManager.properties";
		try {
			PropertiesConfiguration configuration =
				new PropertiesConfiguration(path);
			String id = null;
			String [] files = configuration.getStringArray(LIST_OF_FILES_KEY);
			for (String filename : files) {
				id = configuration.getString(filename+".id");
				Template template = new Template(filename);
				templates.put(id,template);
			}
		} 
		catch (ConfigurationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public Template getTemplate(String id) {
		return templates.get(id);
	}
	public static TemplateManager getInstance() {
		return instance;
	}
	
	
	
}
