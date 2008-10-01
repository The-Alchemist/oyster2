/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.neontoolkit.oyster2.client.gui.GUIConstants;

/**
 * @author David Muñoz
 *
 */
public class DateValidator implements Validator {

	private static final SimpleDateFormat dateFormat =
		new SimpleDateFormat(GUIConstants.DATE_FORMAT);
	
	public final static String validatorName = "DateValidator";
	
	public ArrayList<String> validate(Object object) {
		ArrayList<String> errors = new ArrayList<String>();
		String date = (String)object;
		try {
			dateFormat.parse(date);
		} catch (ParseException e) {
			errors.add("Invalid date format: expected " +
					GUIConstants.DATE_FORMAT);
		}
		
		return errors;
	}
	
}
