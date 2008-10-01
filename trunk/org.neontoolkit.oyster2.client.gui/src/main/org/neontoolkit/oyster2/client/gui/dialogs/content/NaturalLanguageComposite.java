/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs.content;

import org.eclipse.swt.widgets.Composite;

/**
 * @author David Muñoz
 *
 */
public class NaturalLanguageComposite extends ListComposite {

	/**
	 * @param parent
	 * @param style
	 * @param section
	 * @param predefined
	 */
	public NaturalLanguageComposite(Composite parent, int style,
			String section, String[] predefined) {
		super(parent, style, section, predefined);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getInput() {
		String []values = (String[])input;
		String langCode = null;
		for (int i = 0;i<values.length;i++) {
			values[i] = values[i].substring(0,2);
		}
		return values;
	}
	
}
