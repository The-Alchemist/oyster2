/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.results.details;

import java.util.List;

import org.neontoolkit.oyster2.client.gui.adapter.results.ISearchResult;

/**
 * @author David Muñoz
 *
 */
public abstract class ResultSerializer {
	
	protected static String lineDelimiter = "\n";
	
	private static final String HEADER =
		"<?xml version=\"1.0\" encoding=\"utf-8\"?>" + lineDelimiter +
		"<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#" +
		 "xmlns:omv=\"http://omv.ontoware.org/2005/05/ontology#\">" +
		lineDelimiter;
	
	private static final String DOC_END =
		"</rdf:Description>\n</rdf:RDF>";
	
	private static final String RDF_TYPE_STRING =
		"<rdf:type rdf:resource=\"http://www.w3.org/2000/01/rdf-schema#Resource\"/>";
	
	public String serializeHeader(ISearchResult result) {
		
		String header = HEADER;
		StringBuffer text = new StringBuffer(header + 
				lineDelimiter + "<rdf:Description rdf:about=\""); 
		text.append(result.getId() + "\">" + lineDelimiter);
		text.append(RDF_TYPE_STRING);
		text.append("<rdf:type rdf:resource=\"" + result.getRDFType() + "\"/>" +
				lineDelimiter);
		return text.toString();
	}
	
	public String serializeEnd() {
		return DOC_END;
	}
	
	public abstract List<LinkedText> serialize(ISearchResult result);
}
