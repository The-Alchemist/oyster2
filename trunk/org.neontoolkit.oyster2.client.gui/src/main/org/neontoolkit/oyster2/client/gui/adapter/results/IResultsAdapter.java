/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.results;

import java.io.File;


import org.neontoolkit.oyster2.client.gui.IMessageResolver;
import org.neontoolkit.oyster2.client.gui.adapter.IOMVObject;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.IdentifiableType;

/**
 * @author David Muñoz
 *
 */
public interface IResultsAdapter {
	public void setList(IdentifiableType[] list,IMessageResolver messagesResolver,
			File configurationFile, String RDFType);
	public IOMVObject[] getResults();
	public IOMVObject getResult(String id);
	public String getResultsType();
	public void setResultsType(String type);
	public String[] getResultPropertyNames();
	public String getLabel(String property);
	public String[] getMainProperties();
	public void setMainProperties(String []properties);
	public IMessageResolver getMessagesResolver();
}
