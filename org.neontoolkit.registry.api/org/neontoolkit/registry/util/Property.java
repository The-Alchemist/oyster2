package org.neontoolkit.registry.util;

import org.semanticweb.kaon2.api.*;

public class Property {
	private String pred;
	private Object value;
	//private Entity entity;
	public Property(String pred, String value){
		this.pred = pred;
		this.value = value;
	}
	public Property(String pred,Entity value){
		this.pred = pred;
		this.value = value;
	}
	public String getPred(){
		return this.pred;
	}
	public Object getValue(){
		return this.value;
	}

}
