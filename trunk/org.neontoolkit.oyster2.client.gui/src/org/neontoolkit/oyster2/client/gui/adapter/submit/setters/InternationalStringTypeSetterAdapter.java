/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.submit.setters;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.FreeFormText;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringTypeSequence;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.LocalizedStringType;

/**
 * @author David Muñoz
 *
 */
public class InternationalStringTypeSetterAdapter implements SetterAdapter {

//	private static final InternationalStringTypeSetterAdapter instance =
//		new InternationalStringTypeSetterAdapter();
//	
//	static {
//		SetterAdaptersManager.getInstance().registerAdapter(
//				InternationalStringType.class, instance);
//	}
	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.adapter.submit.SetterAdapter#setValue(org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType, java.lang.reflect.Method, java.lang.Object)
	 */
	public void setValue(OMVRegistryObjectType object, Method method,
			Object value) {
		InternationalStringType intString = null;
		if (value instanceof String[])
			intString = setArray(object,method,value);
		else
			intString = setSingle(object,method,value);
		/*String[] values = (String[])value;
		InternationalStringType intString= new InternationalStringType();
		
		//while (it.hasNext()){
		for (int i = 0; i<values.length;i++) {
				
 			
			InternationalStringTypeSequence tempists=new InternationalStringTypeSequence();
			LocalizedStringType templst=new LocalizedStringType();
			FreeFormText tempfft=new FreeFormText();
			tempfft.setFreeFormText((String)values[i]);
			templst.setValue(tempfft);
			templst.setCharset("UTF-8");
			tempists.setLocalizedString(templst);
			intString.addInternationalStringTypeSequence(tempists);
		}
		*/
		
		
		
		try {
			method.invoke(object, new Object[]{intString});
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
		

	}

	private InternationalStringType setSingle(OMVRegistryObjectType object,
		Method method, Object value) {
		String text = (String)value;
		InternationalStringType intString= new InternationalStringType();
		InternationalStringTypeSequence tempists=new InternationalStringTypeSequence();
		LocalizedStringType templst=new LocalizedStringType();
		FreeFormText tempfft=new FreeFormText();
		tempfft.setFreeFormText((String)text);
		templst.setValue(tempfft);
		templst.setCharset("UTF-8");
		tempists.setLocalizedString(templst);
		intString.addInternationalStringTypeSequence(tempists);
		return intString;
	}

	private InternationalStringType setArray(OMVRegistryObjectType object, Method method, Object value) {
		String[] values = (String[])value;
		InternationalStringType intString= new InternationalStringType();
		
		//while (it.hasNext()){
		for (int i = 0; i<values.length;i++) {
			InternationalStringTypeSequence tempists=new InternationalStringTypeSequence();
			LocalizedStringType templst=new LocalizedStringType();
			FreeFormText tempfft=new FreeFormText();
			tempfft.setFreeFormText((String)values[i]);
			templst.setValue(tempfft);
			templst.setCharset("UTF-8");
			tempists.setLocalizedString(templst);
			intString.addInternationalStringTypeSequence(tempists);
		}
		return intString;
	}

	public Class getAdapterClass() {
		return InternationalStringType.class;
	}

}
