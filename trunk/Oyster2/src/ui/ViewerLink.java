/*
 * Created on 2004-01-10
 */
package ui;


/**
 * @author pap
 */
public interface ViewerLink {

	/**
	 * Link type is the predicate which associates the bibliographical entry with
	 * particular linked resource. E.g. if link concerning author, the link type is 
	 * <code>"http://ontobroker.semanticweb.org/ontologies/swrc-onto-2001-12-11.daml#author"<code>
	 * 
	 * @return string representation of type.
	 */
	public String getLinkType();
	
	/**
	 * @return the linked resource.
	 */
	public String getLinkedValue();
}
