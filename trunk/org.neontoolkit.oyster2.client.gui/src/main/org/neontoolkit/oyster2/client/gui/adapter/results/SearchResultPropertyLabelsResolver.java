/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.results;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;


import org.neontoolkit.oyster2.client.gui.IMessageResolver;
import org.neontoolkit.oyster2.client.gui.MessageResolver;

/**
 * @author David Muñoz
 *
 */
public class SearchResultPropertyLabelsResolver {
		
	private static final Map<String,IMessageResolver> resourceBundles = 
		new HashMap<String, IMessageResolver>();
	
	
	public void putBundle(String name, String path) {
		MessageResolver messageResolver = new MessageResolver(path);
		
		resourceBundles.put(name,messageResolver);
	}
	
	  
	public String getString(String bundleName,String key) {
	     try {
	         return resourceBundles.get(bundleName).getString(key);
	     } catch (MissingResourceException e) {
	    	 e.printStackTrace();
	         return "!" + key + "!";
	     }
	   }
	  
	  public String getFormattedString(String bundleName,String key, Object arg) { 
	         String format= null; 
	         try { 
	             format= resourceBundles.get(bundleName).getString(key); 
	         } catch (MissingResourceException e) { 
	             return "!" + key + "!";//$NON-NLS-2$ //$NON-NLS-1$ 
	         } 
	         if (arg == null) 
	             arg= ""; //$NON-NLS-1$ 
	         return MessageFormat.format(format, new Object[] { arg }); 
	     }
	 public String getFormattedString (String bundleName,String key, String[] args) { 
	         return MessageFormat.format(resourceBundles.get(bundleName).getString(key), args); 
	     }
	 public IMessageResolver getMessagesResolver(String name) {
		 return resourceBundles.get(name);
	 }
}
