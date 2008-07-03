/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs.content;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;


/**
 * @author David Muñoz
 *
 */
public class TextAreaComposite extends InputComposite {

	private Text  text = null;

	private static String ITEMS_KEY = "items";
	
	public TextAreaComposite(Composite parent, int style,
			String section) {
		super(parent,style,section,null);
		setCustomSettingsSection(section);
		
		setLayout(new GridLayout(1,false));
		
		
		text = new Text(this, SWT.MULTI|SWT.BORDER | SWT.MULTI
				|SWT.V_SCROLL);
		
		
		//get the size to hold five lines of text
		GC gc = new GC(text);
		FontMetrics fm = gc.getFontMetrics();
		gc.dispose ();
		
		int rows = 5;
		int height = rows * fm.getHeight();
		GridData data = new GridData();
		data.heightHint = height;
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		text.setLayoutData(data);
		
		
		text.setEditable(true);

		makeListeners();
	}
	private void makeListeners() {
		Listener listener = new Listener(){
			public void handleEvent(Event arg0) {
				setInput(text.getText());
			}
		};
		text.addListener(SWT.Modify,listener);
	}
	
	@Override
	public boolean testFilled() {
		if (isRequired()) {
			return !text.getText().trim().equals("");
		}
		return true;
	}
	@Override
	public void setInitialValue(Object value) {
		String oldText = (String)value;
		text.setText(oldText);
		setInput(oldText);
		
	}
}
