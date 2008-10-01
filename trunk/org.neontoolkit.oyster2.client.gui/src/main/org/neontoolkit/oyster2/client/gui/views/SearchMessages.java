/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.views;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author david
 *
 */
public class SearchMessages {
	private static final String BUNDLE_NAME =
		SearchMessages.class.getPackage().getName()+".SearchMessages"; //$NON-NLS-1$

	  private static final ResourceBundle RESOURCE_BUNDLE =
	     ResourceBundle.getBundle(BUNDLE_NAME);

	  private SearchMessages(){};
	  
	  public static String getString(String key) {
	     try {
	         return RESOURCE_BUNDLE.getString(key);
	     } catch (MissingResourceException e) {
	    	 e.printStackTrace();
	         return "!" + key + "!";
	     }
	   }
	  
	  public static String getFormattedString(String key, Object arg) { 
	         String format= null; 
	         try { 
	             format= RESOURCE_BUNDLE.getString(key); 
	         } catch (MissingResourceException e) { 
	             return "!" + key + "!";//$NON-NLS-2$ //$NON-NLS-1$ 
	         } 
	         if (arg == null) 
	             arg= ""; //$NON-NLS-1$ 
	         return MessageFormat.format(format, new Object[] { arg }); 
	     }
	  
	   //  public static String getFormattedString (String key, String[] args) { 
	   //      return MessageFormat.format(RESOURCE_BUNDLE.getString(key), args); 
	   //  }
}
