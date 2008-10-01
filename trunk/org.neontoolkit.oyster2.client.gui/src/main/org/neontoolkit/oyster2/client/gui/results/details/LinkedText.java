/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.results.details;

/**
 * @author david
 *
 */
public class LinkedText {

	private String text = null;
	
	private ISearchLink link = null;

	public ISearchLink getLink() {
		return link;
	}

	public void setLink(ISearchLink link) {
		this.link = link;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
