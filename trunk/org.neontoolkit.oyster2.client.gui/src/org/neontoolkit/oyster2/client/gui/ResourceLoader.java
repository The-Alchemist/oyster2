/**
 * 
 */
package org.neontoolkit.oyster2.client.gui;

import org.eclipse.jface.dialogs.IDialogSettings;


/**
 * @author David Muñoz
 *
 */
public class ResourceLoader {
	
	//private Map<String,Object> submitDialogProperties = null;
	
	private IDialogSettings dialogSettings = null;
	
	private static ResourceLoader resourceLoader = new ResourceLoader();
	
	
	public ResourceLoader() {
		
		dialogSettings = Activator.getDefault().getDialogSettings();
		//settings for ontologies
		initializeDefaults(dialogSettings);
		
	}
	
	public String get(String sectionName, String key) {
		
		IDialogSettings section = dialogSettings.getSection(sectionName);
		return section.get(key);	
		
	}

	public String[] getArray(String sectionName, String key) {
		
		IDialogSettings section = dialogSettings.getSection(sectionName);
		String [] array = section.getArray(key);
		if (array == null)
			return new String[0];
		return array;
	}
	
	public void put(String sectionName, String key, String value) {
		IDialogSettings section = dialogSettings.getSection(sectionName);
		section.put(key, value);
	}
	
	public void put(String sectionName, String key, String[] value) {
		IDialogSettings section = dialogSettings.getSection(sectionName);
		section.put(key, value);
	}
	
	
	public void initializeDefaults(IDialogSettings dialogSettings) {
		String needsInit = dialogSettings.get("needsInit");
		System.out.println("Initialize defaults");
		System.out.println("Needs init: " + needsInit);
		if ( needsInit == null) {
			System.out.println("Needs init");
			dialogSettings.put("needsInit",Boolean.toString(false));
			System.out.println("Initialized");
		}
		else
			System.out.println("Doesn't need init");
		
		
	}

	/**
	 * @return the resourceLoader
	 */
	public static final ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	/**
	 * @param resourceLoader the resourceLoader to set
	 */
	public static final void setResourceLoader(ResourceLoader resourceLoader) {
		ResourceLoader.resourceLoader = resourceLoader;
	}
	
	
	
}
