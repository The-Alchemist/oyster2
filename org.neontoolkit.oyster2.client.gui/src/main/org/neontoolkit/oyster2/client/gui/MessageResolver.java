/**
 * 
 */
package org.neontoolkit.oyster2.client.gui;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author David Muñoz
 *
 */
public class MessageResolver implements IMessageResolver {

	
	  private ResourceBundle resourceBundle = null;
	     
	  
	  private MessageResolver() {};
	  
	  public MessageResolver(String bundleName) {
		  this.resourceBundle = ResourceBundle.getBundle(bundleName);
	  }

	  
	  /* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.IMessageResolver#getString(java.lang.String)
	 */
	public String getString(String key) {
	     try {
	         return resourceBundle.getString(key);
	     } 
	     catch (MissingResourceException e) {
	    	 e.printStackTrace();
	         return "!" + key + "!";
	     }
	  }

	  /* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.IMessageResolver#getFormattedString(java.lang.String, java.lang.Object)
	 */
	public String getFormattedString(String key, Object arg) { 
		  String format= null; 
		  try { 
			  format= resourceBundle.getString(key); 
		  } catch (MissingResourceException e) { 
			  return "!" + key + "!";//$NON-NLS-2$ //$NON-NLS-1$ 
		  } 
		  if (arg == null) 
			  arg= ""; //$NON-NLS-1$ 
		  return MessageFormat.format(format, new Object[] { arg }); 
	  }
	  
	  /* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.IMessageResolver#getFormattedString(java.lang.String, java.lang.String[])
	 */
	public String getFormattedString (String key, String[] args) { 
		  return MessageFormat.format(resourceBundle.getString(key), args); 
	  }

	public ResourceBundle getBundle() {
		return resourceBundle;
	}


}
