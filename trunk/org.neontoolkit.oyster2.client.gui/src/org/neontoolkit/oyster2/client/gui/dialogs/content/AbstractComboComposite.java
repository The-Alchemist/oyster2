/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs.content;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * @author David Muñoz
 *
 */
public abstract class AbstractComboComposite extends InputComposite {


	protected Combo combo = null;
	
	protected static final String ITEMS_KEY = "items"; //$NON-NLS-1$
	
	protected boolean editable = true;
	
	protected final static int numberOfSavedValues = 20;
	
	protected String defaultValue = null;
	
	/**
	 * @param parent
	 * @param style
	 * @param section
	 */
	public AbstractComboComposite(Composite parent, int style, String section) {
		super(parent, style, section,null);
	}

	public AbstractComboComposite(Composite parent, int style, String section,
			String [] predefined,boolean editable) {
		super(parent, style,section,predefined);
		
		if (predefined != null) { 
			if (predefined.length == 0) {
				setSaveSettingsOnExit(true);
			}
			else {
				setSaveSettingsOnExit(false);
			}
		}
		else {
			setSaveSettingsOnExit(true);
		}
		
		String []items = null;
		
		if (predefined == null) {
			items = dialogSettings.getArray(ITEMS_KEY);
		}
		else if (predefined.length == 0) {
			items = dialogSettings.getArray(ITEMS_KEY);
		}
		else {
			items = predefined;
		}
		int comboStyle = SWT.DROP_DOWN;
		if (! editable) {
			comboStyle = comboStyle | SWT.READ_ONLY;
		} 
		combo = new Combo(this,comboStyle);
		if (items != null) {
			combo.setItems(items);
		}
	}
	
	public AbstractComboComposite(Composite parent, int style, String section,
			String [] predefined,boolean editable,String defaultValue) {
		this(parent, style,section,predefined,editable);
		if (defaultValue != null) {
			combo.setText(defaultValue);
			setInput(defaultValue);
		}
	}
		
	@Override
	public void saveSettings() {
		

		// add the text if not already there. That text is the only element
		// that may be added.
		if (getSaveSettingsOnExit()) {
			String []itemsToSave = null;
			if ((! combo.getText().trim().equals("")) && //$NON-NLS-1$
					(! repeated(combo.getText(),combo.getItems()))) {
				int i = 0;
				int numItems = combo.getItemCount();
				
				if(numItems < numberOfSavedValues) {
					numItems++;
				}
				itemsToSave = new String[numItems];
				itemsToSave[0] = combo.getText();
						
				//add new text and delete oldest
				for(i = 1; i<itemsToSave.length;i++ ) {
					itemsToSave[i] = combo.getItems()[i-1];
				}
				dialogSettings.put(ITEMS_KEY,itemsToSave);
			}
			
			
		}
		
	}
	

	protected boolean repeated(String text,String [] set) {
		boolean found = false;
		int i = 0;
		while ((! found) && (i<set.length)) {
			if (set[i].equals(text))
				return true;
			i++;
		}
		return found;
	}
	

	@Override
	public boolean testFilled() {
		if (isRequired()) {
			return (! combo.getText().trim().equals("")); //$NON-NLS-1$
		}
		return true;
	}
	
	@Override
	public ArrayList<String> validate() {
		if ( !combo.getText().trim().equals("")) //$NON-NLS-1$
			return super.validate();
		return new ArrayList<String>();
	}
	
	/**
	 * @return the editable
	 */
	public final boolean isEditable() {
		return editable;
	}

	@Override
	public void setInitialValue(Object value) {
		String initialValue = null;
		if (value instanceof String)
			initialValue = (String)value;
		else 
			initialValue = ((ArrayList<String>)value).get(0);
		combo.setText(initialValue);
		setInput(initialValue);
		
	}

	
}
