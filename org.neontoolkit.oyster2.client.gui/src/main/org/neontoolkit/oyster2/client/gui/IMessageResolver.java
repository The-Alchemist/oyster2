package org.neontoolkit.oyster2.client.gui;

import java.util.ResourceBundle;

public interface IMessageResolver {

	public String getString(String key);

	public String getFormattedString(String key, Object arg);

	public String getFormattedString(String key, String[] args);

	public ResourceBundle getBundle();
}