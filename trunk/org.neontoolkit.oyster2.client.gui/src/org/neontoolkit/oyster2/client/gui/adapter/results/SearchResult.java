/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.results;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.neon_toolkit.registry.omv.xsd.rim.OMVObjectRefType;
import org.neontoolkit.oyster2.client.gui.GUIConstants;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.IdentifiableType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringTypeSequence;

/**
 * @author David Muñoz
 *
 */
public class SearchResult implements ISearchResult {

	private static final SimpleDateFormat dateFormat =
		new SimpleDateFormat(GUIConstants.DATE_FORMAT);
	
	private Map<String,Object> properties = null;
	
	private String RDFType = null;
	
	/**
	 * identifier string, not necessary a value in the properties Map
	 */
	private String id = null;
	
	public SearchResult(IdentifiableType identifiable,
			File configFile, String RDFType) {
		this.RDFType = RDFType;
		this.id = identifiable.getId().toString();
		Class realClass = null;
		
		
		
		String methodName = null;
		Method method = null;
		Object propertyValue = null;
		String propertyName = null;
		PropertiesConfiguration propertiesConfiguration = null;
		
		try {
			 propertiesConfiguration = 
				new PropertiesConfiguration(configFile);
			//propertiesConfiguration.load();
			properties = new HashMap<String, Object>();
			realClass = identifiable.getClass();
			List propertyNames = propertiesConfiguration.getList("propertyNames");
			
			//the keys are property names
			for(Object keyObject : propertyNames) {
				propertyName = (String)keyObject;
				
				
				
				
				methodName = propertiesConfiguration.getString(propertyName);
				method = realClass.getMethod(methodName,null);
				propertyValue = method.invoke(identifiable,null);
				if (propertyValue == null) {
					//skip attribute
					continue;
				}
				
				if (propertyValue instanceof InternationalStringType[]) {
					propertyValue = get((InternationalStringType[])propertyValue);
				}
				else if (propertyValue instanceof OMVObjectRefType[]) {
					propertyValue = get((OMVObjectRefType[])propertyValue);
				}
				else if (propertyValue instanceof InternationalStringTypeSequence[]) {
					propertyValue = get((InternationalStringTypeSequence[])propertyValue);
				}
				else if (propertyValue instanceof InternationalStringType) {
					propertyValue = get((InternationalStringType)propertyValue);
				}
				else if (propertyValue instanceof java.util.Calendar) {
					java.util.Calendar calendar = (Calendar)propertyValue;
					propertyValue = dateFormat.format(calendar.getTime());
				}
				else if (propertyValue instanceof String[]) {
					propertyValue = get((String[])propertyValue);
				}
				else {
					propertyValue = propertyValue.toString();
				}
				properties.put(propertyName,propertyValue);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Creating search result object:\n" +e);
		}
		finally {
			propertiesConfiguration.clear();
		}
		
	}
	
	private ArrayList<String> get(String[] strings) {
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0;i < strings.length;i++) {
			list.add(strings[i]);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.adapter.results.ISearchResult#getId()
	 */
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.adapter.results.ISearchResult#getProperties()
	 */
	public String[] getProperties() {
		String propertyIdentifiers[] = new String[properties.size()];
		return properties.keySet().toArray(propertyIdentifiers);
	}

	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.adapter.results.ISearchResult#getRDFType()
	 */
	public String getRDFType() {
		return RDFType;
	}

	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.adapter.results.ISearchResult#getValue(java.lang.String)
	 */
	public Object getValue(String property) {
		return properties.get(property);
	}

	
	
	

	private ArrayList<Object> get(OMVObjectRefType[] references) {
		if (references == null)
			return null;
		ArrayList<Object> list = new ArrayList<Object>();
		for (int i = 0;i<references.length;i++) {
			list.add(references[i].getId().getReferenceURI().toString());
		}
		return list;
	}
	
	private ArrayList<String> get(InternationalStringTypeSequence[] sequence) {
		if (sequence == null)
			return null;
		ArrayList<String> simpleStrings = new ArrayList<String>();
		for (int i = 0;i<sequence.length;i++) {
			simpleStrings.add(sequence[i].getLocalizedString().getValue().toString());
		}
		return simpleStrings;
	}
	
	private ArrayList<String> get(InternationalStringType[] array) {
		if (array == null)
			return null;
		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0;i< array.length;i++) {
			arrayList.add(array[i].getInternationalStringTypeSequence()[0].
			getLocalizedString().getValue().toString());
		}
		return arrayList;
	}
	
	private ArrayList<String> get(InternationalStringType string) {
		return get(string.getInternationalStringTypeSequence());
	}
}
