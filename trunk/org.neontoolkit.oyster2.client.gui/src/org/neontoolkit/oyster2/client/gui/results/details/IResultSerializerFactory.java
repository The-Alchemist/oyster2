package org.neontoolkit.oyster2.client.gui.results.details;

import org.neontoolkit.oyster2.client.gui.adapter.results.ISearchResult;

public interface IResultSerializerFactory {
	
	public IResultSerializer getSerializer(ISearchResult result);
	
}