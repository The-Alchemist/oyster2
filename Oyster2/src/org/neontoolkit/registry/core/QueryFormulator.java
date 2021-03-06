package org.neontoolkit.registry.core;

import java.util.*;

import org.neontoolkit.registry.oyster2.Condition;
import org.neontoolkit.registry.oyster2.Constants;
import org.neontoolkit.registry.oyster2.Oyster2Factory;
import org.neontoolkit.registry.oyster2.Oyster2Query;
import org.neontoolkit.registry.util.GUID;
//import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Namespaces;
//import org.semanticweb.kaon2.api.OntologyChangeEvent;


public class QueryFormulator {
	static Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	//private static final String baseSubject = "<http://omv.ontoware.org/2005/05/ontology#hasDomain>";
	private static final String OMVURI =  Constants.OMVURI;
	private static final String MOMVURI =  Constants.MOMVURI;
	private static final String POMVURI =  Constants.POMVURI;
	//private static final String SWRCURI = Constants.SWRCURI; 
	//private static final String PROTONTURI = Constants.PROTONTURI;
	//private static final String PROTONSURI = Constants.PROTONSURI;
	//private int variableCounter = 0;
	private String typeClause = "";
	private StringBuffer queryBuffer = new StringBuffer();
	//private String subjectClause = "";
	private String domainClause = "";
	//private List variableList = new LinkedList();
	private List<Condition> literalCondition = new LinkedList<Condition>();
	private Oyster2Query topicQuery = null;
	private Oyster2Query typeQuery = null;
	private GUID queryUID;
	private boolean badRequest = false;
	
	public QueryFormulator(){
		this.queryUID = new GUID();
	}
	public void generateDataQuery(LinkedList conditions, int scope) {
		//if(conditions.size()<=0){badRequest= true;return;}
		typeClause = "";
		for (int i = 0; i < conditions.size(); i++) {
			Condition condition =(Condition) conditions.get(i);
			if(condition.getPred() != null && condition.getConditionType()==(Condition.LITERAL_TYPE))
				addLiteralCondition(condition);
		    else if (condition.getPred() != null && condition.getPred().equals(Condition.TYPE_CONDITION)) 
				addTypeCondition(condition);
		    else if (condition.getPred() != null && condition.getPred().equals(Condition.TOPIC_CONDITION))
		    	addTopicCondition(condition);
			else addLiteralCondition(condition);  //RESOURSE_TYPE IS RESOLVED LATER 
			
		}
		if(badRequest)return;
		//queryBuffer.append(addSWRCPrefix());
		
		/*
		if (subjectClause=="I DONT NEED THIS TOPIC QUERY ANYMORE I PUT IT AS PART OF TYPEQUERY"){  //if (subjectClause!=""){
			queryBuffer.append("SELECT ?x");
			queryBuffer.append(" WHERE "+subjectClause);
			queryBuffer.append("}");
		}else queryBuffer.append("");
		generateTopicQuery(queryBuffer);
		*/
		
		queryBuffer.append("SELECT ?x WHERE ");
		if (typeClause!="")	queryBuffer.append(typeClause);
		else queryBuffer.append(" { ?x rdf:type <"+Constants.OMVURI+Constants.ontologyConcept+"> ");
		
		for(int i = 0;i<literalCondition.toArray().length;i++){
			Condition condition = (Condition)(literalCondition.toArray()[i]);
			if((!condition.getValue().equals(""))&&(condition.getValue()!=null)){	
			queryBuffer.append(". ?x ");
			addFilter(queryBuffer,condition,i);
			//resolveLiteralCondition(queryBuffer,condition);
			//queryBuffer.append(condition.getPred()+" "+ '"' +condition.getValue()+'"' );
			}
		}
		if (domainClause!=""){
			queryBuffer.append(domainClause);
		}
		queryBuffer.append("}");
		generateTypeQuery(queryBuffer,scope); 
	}
	
	public void generateOntologyQuery(LinkedList conditions, int scope){
	    if(conditions.size()<=0)badRequest= true;
	    /*
		for (int i = 0; i < conditions.size(); i++) {
			Condition condition =(Condition) conditions.get(i);
			if(condition.getPred() != null && condition.getConditionType()==(Condition.LITERAL_TYPE))
				addLiteralCondition(condition);
		    else if (condition.getPred() != null && condition.getPred().equals(Condition.TOPIC_CONDITION)) 
		    	addTopicCondition(condition); 
		}
		if(badRequest)return;
		queryBuffer.append("SELECT ?x ");
		queryBuffer.append(" WHERE "+subjectClause);
		for(int i = 0;i<literalCondition.toArray().length;i++){
			Condition condition = (Condition)(literalCondition.toArray()[i]);
			if((!condition.getValue().equals(""))&&(condition.getValue()!=null)){	
			queryBuffer.append(". ?x ");
			resolveLiteralCondition(queryBuffer,condition);
			//queryBuffer.append(condition.getPred()+" "+ '"' +condition.getValue()+'"');
			}
		}
		queryBuffer.append("}");
		*/
	    
	    typeClause = "";
		for (int i = 0; i < conditions.size(); i++) {
			Condition condition =(Condition) conditions.get(i);
			if(condition.getPred() != null && condition.getConditionType()==(Condition.LITERAL_TYPE))
				addLiteralCondition(condition);
		    else if (condition.getPred() != null && condition.getPred().equals(Condition.TYPE_CONDITION)) 
				addTypeCondition(condition);
		    else if (condition.getPred() != null && condition.getPred().equals(Condition.TOPIC_CONDITION))
		    	addTopicCondition(condition);
			else addLiteralCondition(condition);  //RESOURSE_TYPE IS RESOLVED LATER 
			
		}
		if(badRequest)return;
		queryBuffer.append("SELECT ?x WHERE ");
		if (typeClause!="")	queryBuffer.append(typeClause);
		else queryBuffer.append(" { ?x rdf:type <"+Constants.OMVURI+Constants.ontologyConcept+"> ");
		
		for(int i = 0;i<literalCondition.toArray().length;i++){
			Condition condition = (Condition)(literalCondition.toArray()[i]);
			if((!condition.getValue().equals(""))&&(condition.getValue()!=null)){	
			queryBuffer.append(". ?x ");
			addFilter(queryBuffer,condition,i);
			}
		}
		if (domainClause!=""){
			queryBuffer.append(domainClause);
		}
		queryBuffer.append("}");
		
		mOyster2.getLogger().info("ontology query is: "+queryBuffer.toString());
		generateTopicQuery(queryBuffer,scope); 
		
	}
	
	public void resolveLiteralCondition(StringBuffer ret,Condition c){
		String pred = c.getPred();
		String value = c.getValue();
		if(pred.contains("omv:")){
			ret.append("<"+OMVURI+Namespaces.guessLocalName(pred)+">"+" "+ '"' +value+'"');
		}
		//else if(pred.contains("swrc:")){
		//	ret.append("<"+SWRCURI+Namespaces.guessLocalName(pred)+">"+" "+ '"' +value+'"');
		//}
		//else if(pred.contains("protont:")){
		//	ret.append("<"+PROTONTURI+Namespaces.guessLocalName(pred)+">"+" "+ '"' +value+'"');	
		//}
		//else if(pred.contains("protons:")){
		//	ret.append("<"+PROTONSURI+Namespaces.guessLocalName(pred)+">"+" "+ '"' +value+'"');	
		//}
		
	}
	
	public void addFilter(StringBuffer ret,Condition c, int i){
	  String pred = c.getPred();
	  String value = c.getValue();
	  //System.out.println("Type : " + c.getConditionType()+ " pred : "+c.getPred()+" value : "+c.getValue());
	  if (pred!=null){
		if(pred.startsWith("omv:")){
			if (c.getConditionType()== Condition.LITERAL_TYPE){
				ret.append("<"+OMVURI+Namespaces.guessLocalName(pred)+">"+" "+ "?v"+Integer.toString(i));
				Boolean isI = org.neontoolkit.registry.util.Utilities.isInt(Namespaces.guessLocalName(pred));
				if (!isI){
					Boolean isB = org.neontoolkit.registry.util.Utilities.isBool(Namespaces.guessLocalName(pred));
					if (!isB){
						ret.append(" . FILTER regex(?v"+Integer.toString(i)+", " + '"' + value + '"' + "," + '"' + "i" + '"' + ") ");
					}
					else{
						ret.append(" . FILTER (?v"+Integer.toString(i)+" = " + value + ") ");
					}
				}
				else{
					ret.append(" . FILTER (?v"+Integer.toString(i)+" >= " + value + ") ");
				}
			}
			else if (c.getConditionType()== Condition.RESOURCE_TYPE){
				String ref = c.getReference();
				if (ref!=null){
					if(ref.startsWith("omv:")) ret.append("<"+OMVURI+Namespaces.guessLocalName(ref) +"> "+"?r"+Integer.toString(i));
					else if(ref.startsWith("momv:")) ret.append("<"+MOMVURI+Namespaces.guessLocalName(ref) +"> "+"?r"+Integer.toString(i));
					else if(ref.startsWith("pomv:")) ret.append("<"+POMVURI+Namespaces.guessLocalName(ref) +"> "+"?r"+Integer.toString(i));
					ret.append(" . "+"?r"+Integer.toString(i) + " <"+OMVURI+Namespaces.guessLocalName(pred) +"> "+ "?v"+Integer.toString(i));
					Boolean isI = org.neontoolkit.registry.util.Utilities.isInt(Namespaces.guessLocalName(pred));
					if (!isI){
						Boolean isB = org.neontoolkit.registry.util.Utilities.isBool(Namespaces.guessLocalName(pred));
						if (!isB){
							ret.append(" . FILTER regex(?v"+Integer.toString(i)+", " + '"' + value + '"' + "," + '"' + "i" + '"' + ") ");
						}
						else{
							ret.append(" . FILTER (?v"+Integer.toString(i)+" = " + value + ") ");
						}
					}
					else{
						ret.append(" . FILTER (?v"+Integer.toString(i)+" >= " + value + ") ");
					}
				}
				else ret.append("<"+OMVURI+Namespaces.guessLocalName(pred) +"> <"+value+"> ");//ret.append("<"+OMVURI+Namespaces.guessLocalName(pred) +"> \""+value+"\" ");
			}
		}
		else if(pred.startsWith("pomv:")){
			if (c.getConditionType()== Condition.RESOURCE_TYPE){
				ret.append("<"+Constants.POMVURI+Namespaces.guessLocalName(pred) +"> <"+Constants.LocalRegistryURI+"#"+value+"> ");
			}
		}
		else if(pred.startsWith("momv:")){
			if (c.getConditionType()== Condition.LITERAL_TYPE){
				ret.append("<"+MOMVURI+Namespaces.guessLocalName(pred)+">"+" "+ "?v"+Integer.toString(i));
				Boolean isI = org.neontoolkit.registry.util.Utilities.isIntM(Namespaces.guessLocalName(pred));
				if (!isI){
					Boolean isB = org.neontoolkit.registry.util.Utilities.isBoolM(Namespaces.guessLocalName(pred));
					if (!isB){
						ret.append(" . FILTER regex(?v"+Integer.toString(i)+", " + '"' + value + '"' + "," + '"' + "i" + '"' + ") ");
					}
					else{
						ret.append(" . FILTER (?v"+Integer.toString(i)+" = " + value + ") ");
					}
				}
				else{
					ret.append(" . FILTER (?v"+Integer.toString(i)+" >= " + value + ") ");
				}
			}
			else if (c.getConditionType()== Condition.RESOURCE_TYPE){
				String ref = c.getReference();
				if (ref!=null){
					if(ref.startsWith("omv:")) ret.append("<"+OMVURI+Namespaces.guessLocalName(ref) +"> "+"?r"+Integer.toString(i));
					else if(ref.startsWith("momv:")) ret.append("<"+MOMVURI+Namespaces.guessLocalName(ref) +"> "+"?r"+Integer.toString(i));
					else if(ref.startsWith("pomv:")) ret.append("<"+POMVURI+Namespaces.guessLocalName(ref) +"> "+"?r"+Integer.toString(i));
					ret.append(" . "+"?r"+Integer.toString(i) + " <"+MOMVURI+Namespaces.guessLocalName(pred) +"> "+ "?v"+Integer.toString(i));
					Boolean isI = org.neontoolkit.registry.util.Utilities.isIntM(Namespaces.guessLocalName(pred));
					if (!isI){
						Boolean isB = org.neontoolkit.registry.util.Utilities.isBoolM(Namespaces.guessLocalName(pred));
						if (!isB){
							ret.append(" . FILTER regex(?v"+Integer.toString(i)+", " + '"' + value + '"' + "," + '"' + "i" + '"' + ") ");
						}
						else{
							ret.append(" . FILTER (?v"+Integer.toString(i)+" = " + value + ") ");
						}
					}
					else{
						ret.append(" . FILTER (?v"+Integer.toString(i)+" >= " + value + ") ");
					}					
					//ret.append(" . FILTER regex(?v"+Integer.toString(i)+", " + '"' + value + '"' + "," + '"' + "i" + '"' + ") ");
				}
				else ret.append("<"+MOMVURI+Namespaces.guessLocalName(pred) +"> <"+value+"> ");//ret.append("<"+MOMVURI+Namespaces.guessLocalName(pred) +"> \""+value+"\" ");
			}
		}
	  }
	  else{ //WILDCARD DOES NOT WORK KAON2 LIMITATION :(
			ret.append("?any"+" "+ "?v"+Integer.toString(i));
			ret.append(" . FILTER regex(?v"+Integer.toString(i)+", " + '"' + value + '"' + "," + '"' + "i" + '"' + ") ");		
	  }
	}
	
	/**
	 * 
	 * @return the topicQuery to query the expertise Ontologies in order to generate the virtual Ontology. 
	 */
	public Oyster2Query getTopicQuery(){
		if(badRequest)
			return null;
		return this.topicQuery;
	}
	/**
	 * 
	 * @return the typeQuery to query the virtual Ontology in order to get "instances" solutions.
	 */
	public Oyster2Query getTypeQuery(){
		if(badRequest)
			return null;
		return this.typeQuery;
	}
	private void generateTopicQuery(StringBuffer queryBuffer,int scope){
		Oyster2Query query = new Oyster2Query(queryUID,scope, Oyster2Query.TOPIC_QUERY,queryBuffer.toString());
		this.topicQuery = query;
		int bufferLength = queryBuffer.length();
		queryBuffer.delete(0,bufferLength);
		mOyster2.getLogger().info("topicQuery: "+topicQuery.getQueryString());
	}
	
	private void generateTypeQuery(StringBuffer queryBuffer, int scope){
		Oyster2Query query = new Oyster2Query(queryUID, scope, Oyster2Query.TYPE_QUERY,queryBuffer.toString());
		this.typeQuery = query;
		int bufferLength = queryBuffer.length();
		queryBuffer.delete(0,bufferLength);
		mOyster2.getLogger().info("typeQuery: "+typeQuery.getQueryString());
	}

	private void addTypeCondition(Condition c){
		if((c.getValue()==null)||(c.getValue().length()<=0)||(c.getPred()==null)){
			badRequest = true;
			System.out.println("bad!");
			//return;
		}
		typeClause = " { ?x rdf:type <"+c.getValue()+"> ";
	}
	private void addTopicCondition(Condition c){
		if((c.getValue()==null)||(c.getValue().length()<=0)||(c.getPred()==null)){
			badRequest = true;
			System.out.println("bad!");
			//return;
		}
		String topicURI = c.getValue();
		//subjectClause=" { ?x <"+Constants.OMVURI+Constants.hasDomain+"> <"+topicURI+"> ";
		domainClause=". ?x <"+Constants.OMVURI+Constants.hasDomain+"> <"+topicURI+"> ";
	}
	/**
	 * TODO the queryFormat maybe not correct, should be confirmed!
	 * add literalCondition into the queryString
	 * @param c  literalCondition:such as swrc:author="Laurent"
	 */
	private void addLiteralCondition(Condition c){
		literalCondition.add(c);	
	}
	//private String addOMVPrefix(){
	//	 return "PREFIX omv: "+OMVURI+" ";
	//}
	//private String addSWRCPrefix(){
	//	return "PREFIX swrc: "; //+SWRCURI+" ";
	//}

}
