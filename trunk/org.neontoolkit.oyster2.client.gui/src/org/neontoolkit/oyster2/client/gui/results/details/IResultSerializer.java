package org.neontoolkit.oyster2.client.gui.results.details;

import java.util.List;

import org.neontoolkit.oyster2.client.gui.adapter.results.ISearchResult;

public interface IResultSerializer {

	public List<LinkedText> serialize(ISearchResult result);

}