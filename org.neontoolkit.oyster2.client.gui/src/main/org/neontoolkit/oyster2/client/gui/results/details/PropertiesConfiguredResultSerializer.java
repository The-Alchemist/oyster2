/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.results.details;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.adapter.IOMVObject;

/**
 * @author David Mu�oz
 *
 */
public class PropertiesConfiguredResultSerializer implements IResultSerializer {
	
	protected static String lineDelimiter = "\n"; //$NON-NLS-1$
	
	private List<String> partyProperties = null;
	
	private static final String HEADER =
		"<?xml version=\"1.0\" encoding=\"utf-8\"?>" + lineDelimiter + //$NON-NLS-1$
		"<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#" + //$NON-NLS-1$
		 "xmlns:omv=\"http://omv.ontoware.org/2005/05/ontology#\">" + //$NON-NLS-1$
		lineDelimiter;
	
	
	private PropertiesConfiguration configuration = null;
	
	private static final String DOC_END =
		"</rdf:Description>\n</rdf:RDF>"; //$NON-NLS-1$
	
	private static final String RDF_TYPE_STRING =
		"<rdf:type rdf:resource=\"http://www.w3.org/2000/01/rdf-schema#Resource\"/>" + //$NON-NLS-1$
		lineDelimiter;
	
	
	
	public void setProperties(String path) {
		try {
			path = Activator.getDefault().getResourcesDir() + File.separator
			+ path;
			configuration = new PropertiesConfiguration(path);
			partyProperties = configuration.getList("party.properties"); //$NON-NLS-1$
		} catch (ConfigurationException e) {
		
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.results.details.IResultSerializer#serialize(org.neontoolkit.oyster2.client.gui.adapter.results.ISearchResult)
	 */
	public List<LinkedText> serialize(IOMVObject result) {
		List<LinkedText> list = serializeHeader(result);
		
		//LinkedText linkedText = null;
		String [] propertyNames = result.getProperties();
		for (String propertyName : propertyNames) {
			if (partyProperties.contains(propertyName)) {
				serializeParty(list, result, propertyName);
				
			}
			else {
				serializeProperty(list, propertyName, result);
				
			}
			
			//list.add(linkedText);
		}
		serializeEnd(list);
		return list;
	}
	
	private void serializeEnd(List<LinkedText> list) {
		LinkedText link = new LinkedText();
		link.setText(DOC_END);
		list.add(link);
		return;
		
	}
	
	private String makeEndTag(String beginTag) {
		return beginTag.replaceAll("<", "</"); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	private void serializeProperty(List<LinkedText> list,String propertyName,
			IOMVObject result) {
		LinkedText linkedText = null;
		SearchLink link = null;
		String beginTag = null;
		String endTag = null;
		Object propertyValue = result.getValue(propertyName);
		
		beginTag = configuration.getString(propertyName);
		endTag = makeEndTag(beginTag);
		
		
		if (propertyValue instanceof String) {
			String propertyText = (String)propertyValue;
			if (propertyText.length() == 0)
				return;
			makePlainText(list, "\t" + beginTag); //$NON-NLS-1$
			
			linkedText = new LinkedText();
			
			
			if (propertyText.charAt(0) == '#') {
				propertyText = propertyText.substring(1);
			}
			linkedText.setText(propertyText);
			
			link = new SearchLink();
			link.setAttributeNames(propertyName);
			link.setValue(propertyText);
			link.setTarget(result.getRDFType());
						
			linkedText.setLink(link);
			list.add(linkedText);
			makePlainText(list,endTag+lineDelimiter);
			
			
		}
		else if (propertyValue instanceof ArrayList) {
			for (Object obj : (ArrayList)propertyValue) {
				String propertyText = (String)obj;
				if (propertyText.length() == 0)
					continue;
				linkedText = new LinkedText();
				
				if (propertyText.charAt(0) == '#') {
					propertyText = propertyText.substring(1);
				}
				makePlainText(list,"\t" + beginTag); //$NON-NLS-1$
				linkedText.setText(propertyText);
				
				
				
				link = new SearchLink();
				link.setAttributeNames(propertyName);
				link.setValue(propertyText);
				link.setTarget(result.getRDFType());
				linkedText.setLink(link);
				list.add(linkedText);
				makePlainText(list,endTag+lineDelimiter);
			}
			
		}
		
	}
	
	private void makePlainText(List<LinkedText> list,String text) {
		LinkedText linkedText = new LinkedText();
		linkedText.setText(text);
		linkedText.setLink(null);
		list.add(linkedText);
	}
	
	/*
	private LinkedText makeLink(String propertyName,
			String propertyText,String rdfType,String beginTag,String endTag) {
		LinkedText linkedText = new LinkedText();
		
		if (propertyText.charAt(0) == '#') {
			propertyText = propertyText.substring(1);
		}
		linkedText.setText(beginTag+propertyText+endTag+
		lineDelimiter);
		
		
		SearchLink link = new SearchLink();
		link.setAttributeNames(propertyName);
		link.setValue(propertyText);
		link.setTarget(rdfType);
		

		linkedText.setLink(link);
		return linkedText;
	}
	*/
	
	
	
	private void serializeParty(List<LinkedText> list,
			IOMVObject result, String propertyName ) {
		
		// get party specific methods
		String personPropertyName = configuration.getString(propertyName+".person"); //$NON-NLS-1$
		
		String orgNamePropertyName = configuration.getString(propertyName+".organization"); //$NON-NLS-1$
		
		
		Map<String,String> propertyNames = new HashMap<String,String>();
		propertyNames.put("person",personPropertyName); //$NON-NLS-1$
		propertyNames.put("organization",orgNamePropertyName); //$NON-NLS-1$
		
		LinkedText linkedText = null;
		ISearchLink link = null;
		String beginTag = null;
		String endTag = null;
		
		Object propertyValue = result.getValue(propertyName);
		
		//tags are the same, no matter if the values are people or organizations
		beginTag = configuration.getString(propertyName);
		endTag = makeEndTag(beginTag);
		
		String propertyText = null;
		if (propertyValue instanceof String) {
			propertyText = (String)propertyValue;
			if (propertyText.length() == 0)
				return;
			
			makePlainText(list, "\t" + beginTag); //$NON-NLS-1$
			linkedText = new LinkedText();
			link = new PartySearchLink();
			
			
			if (propertyText.charAt(0) == '#') {
				propertyText = propertyText.substring(1);
			}
			link.setAttributeNames(propertyNames);
			link.setParty(true);
			link.setValue(propertyText);
			link.setTarget(result.getRDFType());
			
			linkedText.setLink(link);
			linkedText.setText(propertyText);
			list.add(linkedText);
			makePlainText(list,endTag+lineDelimiter);
		}
		else if (propertyValue instanceof ArrayList) {
			for (Object obj : (ArrayList)propertyValue) {
				propertyText = (String)obj;
				if (propertyText.length() == 0)
					continue;
				linkedText = new LinkedText();
				
				if (propertyText.charAt(0) == '#') {
					propertyText = propertyText.substring(1);
				}
				linkedText.setText(propertyText);
				
				link = new PartySearchLink();
				link.setParty(true);
				link.setAttributeNames(propertyNames);
				link.setValue(propertyText);
				link.setTarget(result.getRDFType());
				

				linkedText.setLink(link);
				makePlainText(list, "\t" + beginTag); //$NON-NLS-1$
				list.add(linkedText);
				makePlainText(list, endTag+lineDelimiter);
			}
		}
		
	}
	
	private List<LinkedText> serializeHeader(IOMVObject result) {
		List<LinkedText> list = new LinkedList<LinkedText>();
		
		LinkedText link = new LinkedText();
		
		StringBuffer text = new StringBuffer(HEADER + 
				lineDelimiter + "<rdf:Description rdf:about=\"");  //$NON-NLS-1$
		text.append(result.getId() + "\">" + lineDelimiter); //$NON-NLS-1$
		text.append(RDF_TYPE_STRING);
		text.append("<rdf:type rdf:resource=\"" + result.getRDFType() + "\"/>" + //$NON-NLS-1$ //$NON-NLS-2$
				lineDelimiter);
		link.setText(text.toString());
		list.add(link);
		return list;
		
	}
}
