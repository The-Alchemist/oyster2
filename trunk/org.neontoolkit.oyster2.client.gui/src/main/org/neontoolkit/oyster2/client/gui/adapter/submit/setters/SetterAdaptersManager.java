/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.submit.setters;

import java.util.HashMap;
import java.util.Map;


/**
 * @author David Muñoz
 *
 */
public class SetterAdaptersManager {

	private static SetterAdaptersManager instance = null;
	
	private static Class[] adapterClasses =null;
	
	private Map<Class,SetterAdapter> adapters = null;
	
	static {
		adapterClasses = new Class[] {BigIntegerSetterAdapter.class,BooleanSetterAdapter.class,
				CalendarSetterAdapter.class,FreeFormTextSetterAdapter.class,
				InternationalStringTypeArraySetterAdapter.class,
				InternationalStringTypeSetterAdapter.class,
				OMVObjectRefArraySetterAdapter.class,
				OMVObjectRefSetterAdapter.class,
				ReferenceURISetterAdapter.class,
				ShortNameSetterAdapter.class,
				StringArraySetterAdapter.class,
				StringSetterAdapter.class, URISetterAdapter.class};
		
		instance = new SetterAdaptersManager();
		
	}
	
	
	public SetterAdaptersManager() {
		adapters = new HashMap<Class,SetterAdapter>();
		SetterAdapter adapter = null;
		for (Class clazz : adapterClasses) {
			try {
				adapter = (SetterAdapter) clazz.newInstance();
				
				adapters.put(adapter.getAdapterClass(), adapter);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
	}
	/*
	public void registerAdapter(Class targetArgumentClass,SetterAdapter adapter) {
		adapters.put(targetArgumentClass,adapter);
	}*/
	
	public SetterAdapter getAdapter(Class targetArgumentClass) {
		return adapters.get(targetArgumentClass);
	}
	

	public static SetterAdaptersManager getInstance() {
		return instance;
	}
	
	
	
}
