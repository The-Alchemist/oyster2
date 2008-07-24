package org.neontoolkit.oyster2.client.gui.results.details;

import org.neontoolkit.oyster2.client.gui.adapter.IOMVObject;

public interface IResultSerializerFactory {
	
	public IResultSerializer getSerializer(IOMVObject result);
	
}