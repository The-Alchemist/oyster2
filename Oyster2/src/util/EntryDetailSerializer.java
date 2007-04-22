package util;

import oyster2.*;
import java.util.*;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.swt.custom.StyledText;

import org.semanticweb.kaon2.api.*;
import org.semanticweb.kaon2.api.owl.elements.*;

public  class EntryDetailSerializer {

	protected static final String BIBTEX_ORDER_PROPERTY_PREFIX = "BIBTEX_PROPERTY_ORDER_";
	private static final EntryDetailSerializer instance = new EntryDetailSerializer();
	private Oyster2Factory mKaonP2P = Oyster2Factory.sharedInstance();
	public static final char QUOTE = '"';
	public static final char QUOTE_PARENTHESIS_OPEN = '{';
	public static final char QUOTE_PARENTHESIS_CLOSE = '}';
	//private Ontology ontology;
	private EntryDetailSerializer(){ 
	}
	public static EntryDetailSerializer getInstance(){
		return instance;
	}
	
	protected void serializeSeqNode(StringBuffer result, DataProperty property, String propertyValue, int seqIndex, boolean isNested, boolean isLastIndex) {
		if (seqIndex > 1) {
			result.append(" and ");
		}
		result.append(propertyValue);
	}

	public String serialize(Entity entry,Ontology ontology,Collection propertySet) {
		StringBuffer ret = new StringBuffer();
		try{
			OWLClass typeClass =(OWLClass) (((Individual) entry).getDescriptionsMemberOf(ontology).toArray())[0];
			serializeType(ret, typeClass);
			serializeKey(ret, entry);
			serializeProperties( ret,propertySet);
			serializeEndOfEntry(ret);
		}catch(KAON2Exception e){
			System.out.println(e.toString()+":AbstractToString serialize()");
		}
		return ret.toString();
	}
	
	public String serialize(Ontology ontology){
		StringBuffer ret = new StringBuffer();
		Map propertyMap = new HashMap();
		try{
			propertyMap = ontology.getOntologyProperties();
		}catch(KAON2Exception e){
			System.err.println(e.toString()+" serialize(Ontology) in DetailSerializer.");
		}
		serializeType(ret,ontology);
		serializeKey(ret,ontology);
		serializeProperties(ret,propertyMap);
		serializeEndOfEntry(ret);
		return ret.toString();
		
	}
	
	public void serializeType(StringBuffer result, Entity type) {
		result.append("@" + serializeValue(type.getURI()));
		//result.append("<?xml version="+QUOTE+"1.0"+QUOTE+" encoding="+QUOTE+"utf-8"+QUOTE+"?>\n");
		//result.append("<rdf:RDF xmlns:rdf="+QUOTE+"http://www.w3.org/1999/02/22-rdf-syntax-ns#"+QUOTE+"\n");
		//result.append("xmlns:omv="+QUOTE+"http://omv.ontoware.org/2005/05/ontology#"+QUOTE+">\n");	
	}
	
	public void serializeType(StringBuffer result, Ontology ontology) {
		result.append("@Ontology" );
	}
	
	protected void serializeKey(StringBuffer result, Entity entry) {
		result.append("{\t");
		result.append("{\n\t");
		result.append("uri="+entry.getURI());
		//result.append("\t<rdf:Description rdf:about=");
		//result.append(QUOTE+entry.getURI()+QUOTE+">\n");
		//result.append("\t\t<rdf:type rdf:resource="+QUOTE+"http://www.w3.org/2000/01/rdf-schema#Resource"+QUOTE+"/>\n");		
		//result.append("\t\t<rdf:type rdf:resource="+QUOTE+"http://omv.ontoware.org/2005/05/ontology#Ontology"+QUOTE+"/>\n");
	}
	
	protected void serializeKey(StringBuffer result, Ontology ontology) {
		result.append("{\n\t");
		result.append("uri="+ontology.getOntologyURI());	
	}

	protected final void serializeProperties(StringBuffer ret,Collection propertySet) {
		Iterator it = propertySet.iterator();
		while(it.hasNext()){
			Property property = (Property)it.next();
			String pred = property.getPred();
			Object value = property.getValue();
			ret.append("\n\t" + serializeValue(pred) + "=" + QUOTE);
			//ret.append("\t\t<omv:"+ serializeValue(pred)+">");
			if((serializeValue(pred).equals("author"))||(serializeValue(pred).equals("publishedAt"))||(serializeValue(pred).equals("documentAuthor"))
					)
				ret.append(serializeValue(value.toString()) + QUOTE );
			else ret.append(value.toString()+QUOTE); //ret.append(value.toString());
			//ret.append("</omv:"+ serializeValue(pred)+">");
			if(it.hasNext()) ret.append(","); //ret.append("\n");
		}
	}
	
	protected final void serializeProperties(StringBuffer ret,Map propertyMap) {
		Collection keySet = propertyMap.keySet();
		Iterator it = keySet.iterator();
		try{
		while(it.hasNext()){
			String key = (String)it.next();
			String value = "";
			Collection propertySet = (Collection)propertyMap.get(key);
			Iterator ip = propertySet.iterator();
			while(ip.hasNext()){
				if(value.equals(""))
					value = (String)ip.next();
				else value = value + "  &  "+ (String)ip.next();
			}
			ret.append("\n\t" + serializeValue(key) + "=" + QUOTE);
			ret.append(value+QUOTE);
			if(it.hasNext())ret.append(",");
		}
		}catch(Exception e){
			System.err.println(e.toString()+"serializeProperties() for ontology in DetailSerializer. ");
		}
	}
	
	public void serializeEndOfEntry(StringBuffer result) {
		if (result.indexOf("\n") < 0) { // case with empty content
			result.append(","); //result.append(" ");
		}
		result.append("\n}\n"); //result.append("\n\t</rdf:Description>\n</rdf:RDF>\n");
	}
	
	public String serializeValue(String value) {	
		String	ret = Namespaces.guessLocalName(value);
		return ret;
		
	}
}

