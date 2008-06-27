package org.neontoolkit.registry.oyster2;

import org.semanticweb.kaon2.api.Entity;
import org.semanticweb.kaon2.api.logic.Predicate;
import org.semanticweb.kaon2.api.logic.Term;
//import org.semanticweb.kaon2.api.rules.Predicate; //OLD VERSION
//import org.semanticweb.kaon2.api.rules.Term;  //OLD VERSION

public class Condition {

	public static final int LITERAL_TYPE = 0;
	public static final int RESOURCE_TYPE = 1;
	public static final String TOPIC_CONDITION = "omv:hasDomain";
	public static final String TYPE_CONDITION = "rdf:type";

	private String pred;
	private String value;
	private String reference;
	//private Entity conditionEntry;
	private int type = LITERAL_TYPE;
	
	public Condition(String pred, String value, boolean asResource){
		this.pred = pred;
		this.value = value;
		type = asResource ? LITERAL_TYPE : RESOURCE_TYPE;
	}

	public Condition(String pred, String value, String reference){
		this.pred = pred;
		this.value = value;
		this.reference = reference;
		type = RESOURCE_TYPE;
	}
	
	public Condition(String pred, String value){
		this.pred = pred;
		this.value = value;
		this.type = LITERAL_TYPE;
	}
	
	public Condition(String pred, Entity entry){
		this.pred = pred;
		this.value = entry.getURI();
		this.type = RESOURCE_TYPE;
	}
	
	
	
	public Condition(Predicate typePredicate){
		
		
		
	}
	
	public Condition(Predicate pred, Term term){
	}
	/**
	 * @return Returns condition type (literal or resource)
	 */
	public int getConditionType(){
		return type;
	}
	
	/**
	 * @return Returns the predicate e.g. rdfs:label.
	 */
	public String getPred() {
		return pred;
	}

	/**
	 * @return Returns the value of that condition.
	 */
	public String getValue() {
		return value;
	}
	
	public String getReference() {
		return reference;
	}
	
}

