package org.neontoolkit.oyster2.client.gui.adapter.results;

import org.oasis.names.tc.ebxml_regrep.xsd.rim.IdentifiableType;

public interface IRegistryObjectIdentifierResolver {

	public String getIdentifier(IdentifiableType object);

}