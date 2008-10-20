/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.submit.setters;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.neontoolkit.oyster2.client.gui.GUIConstants;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType;

/**
 * @author David Muñoz
 *
 */
public class CalendarSetterAdapter implements SetterAdapter {

	private SimpleDateFormat dateFormat =
		new SimpleDateFormat(GUIConstants.DATE_FORMAT);
	
	/*private static final CalendarSetterAdapter instance =
		new CalendarSetterAdapter();
	
	static {
		SetterAdaptersManager.getInstance().registerAdapter(Calendar.class, instance);
	}*/
	
	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.adapter.submit.SetterAdapter#setValue(org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType, java.lang.reflect.Method, java.lang.Object)
	 */
	public void setValue(RegistryObjectType object, Method method,
			Object value) {
		String dateString = (String)value;
		Calendar calendar = Calendar.getInstance();

		try {
			calendar.setTime(dateFormat.parse(dateString));
			method.invoke(object, new Object[]{calendar});
			
		}
		catch (Exception e) {
			
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public Class getAdapterClass() {
		return Calendar.class;
	}

}
