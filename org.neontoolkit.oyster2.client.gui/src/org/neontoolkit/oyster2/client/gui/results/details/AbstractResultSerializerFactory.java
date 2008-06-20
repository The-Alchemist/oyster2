/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.results.details;

import java.lang.reflect.Method;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.neontoolkit.oyster2.client.gui.Activator;

/**
 * @author David Muñoz
 *
 */
public abstract class AbstractResultSerializerFactory implements IResultSerializerFactory {
	
	private static IResultSerializerFactory factory = null;
	
	static {
		try {
			String []factoryInitArguments = null;
			String factoryInitMethodName = null;
			String factoryClassName = null;
			
			//String  path = Activator.getDefault().getResourcesDir() + "result/";
			String path = Activator.getDefault().getResourcesDir(); 
				
				
				//AbstractResultSerializerFactory.class.getPackage().getName().replace(".","/");
			
			PropertiesConfiguration configuration = 
				new PropertiesConfiguration(path+"/AbstractResultSerializerFactoryConfiguration.properties");
	
			//configuration.setBasePath(path);
			
			
			factoryClassName = configuration.getString("factory.class");
			
			if (factoryClassName == null) {
				throw new RuntimeException("missing resultSerializerFactory class name");
			}
			
			factoryInitArguments = configuration.getStringArray("factory.init.arguments");
			factoryInitMethodName = configuration.getString("factory.init.method");
			Class factoryClass = Class.forName(factoryClassName);
			factory = (IResultSerializerFactory)factoryClass.newInstance();
			//if has init method, use it. 
			if (factoryInitMethodName != null) {
				Method method = factoryClass.getMethod(factoryInitMethodName,String[].class);
				method.invoke(factory,new Object[]{factoryInitArguments});
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
	}
	
	public static IResultSerializerFactory getFactory() {
		return factory;
		
	}
	
}
