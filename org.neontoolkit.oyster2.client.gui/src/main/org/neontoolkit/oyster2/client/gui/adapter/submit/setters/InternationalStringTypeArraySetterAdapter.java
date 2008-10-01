/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.submit.setters;

import java.lang.reflect.Method;
import java.util.Collection;

import org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType;
import org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.FreeFormText;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringTypeSequence;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.LocalizedStringType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType;

/**
 * @author david
 *
 */
public class InternationalStringTypeArraySetterAdapter implements SetterAdapter {

//	private static final InternationalStringTypeArraySetterAdapter instance =
//		new InternationalStringTypeArraySetterAdapter();
//	
//	static {
//		SetterAdaptersManager.getInstance().registerAdapter(
//				InternationalStringType[].class, instance);
//	}
	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.adapter.submit.SetterAdapter#setValue(org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType, java.lang.reflect.Method, java.lang.Object)
	 */
	public void setValue(RegistryObjectType object, Method method,
			Object value) {
		String[] ids = (String[])value;
		InternationalStringType[] references = new InternationalStringType[ids.length];
		int i = 0;
		InternationalStringType iString = null;
		InternationalStringTypeSequence [] iStringSequenceArray = null;
		FreeFormText freeFormText = null;

		LocalizedStringType localizedString = null;
		for (String id : ids) {
			iString = new InternationalStringType();
			iStringSequenceArray = new InternationalStringTypeSequence[1];
			iStringSequenceArray[0] = new InternationalStringTypeSequence();
			freeFormText = new FreeFormText();
			freeFormText.setFreeFormText(id);
			localizedString = new LocalizedStringType();
			localizedString.setValue(freeFormText);
			localizedString.setCharset("UTF-8");
			iStringSequenceArray[0].setLocalizedString(localizedString);
			iString.setInternationalStringTypeSequence(iStringSequenceArray);
			references[i] = iString;
			i++;
		}
		try {
			method.invoke(object, new Object[]{references});
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Class getAdapterClass() {
		return InternationalStringType[].class;
	}

}
