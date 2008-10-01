/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.validator;


import java.util.ArrayList;




/**
 * @author David Muñoz
 *
 */
public class OntologyValidator implements Validator{
	
	public final static String validatorName = "OntologyValidator";
	
	public ArrayList<String> validate(Object object) {
		String id = (String)object;
		ArrayList<String> errors = new ArrayList<String>();
		int URIEndIndex = id.indexOf("?version=");
		int versionEndIndex = id.indexOf(";location=", URIEndIndex);
		int endIndex = id.length();
		if ((URIEndIndex == -1) || (versionEndIndex == -1) ||
				endIndex ==versionEndIndex) {
			errors.add("The id must have the following structure: " + 
					"URI?[version=<version >;]location=<resourceLocator>");
		}
		return errors;		
	}
}
