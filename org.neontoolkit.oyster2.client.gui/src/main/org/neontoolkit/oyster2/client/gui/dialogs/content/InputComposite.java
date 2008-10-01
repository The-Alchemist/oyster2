/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs.content;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.GUIConstants;
import org.neontoolkit.oyster2.client.gui.validator.Validator;

/**
 * @author David Muñoz
 *
 */
public abstract class InputComposite extends org.eclipse.swt.widgets.Composite {

	private Validator validator = null; 
	
	private static int MAX_HEIGHT = 200;

	protected static int buttonWidth = GUIConstants.BUTTON_WIDTH;
		
	protected Object input = null;
	
	private String customSettingsSection = null;
	
	protected IDialogSettings dialogSettings = null;
	
	private boolean saveSettingsOnExit = false;
	
	protected boolean required = false;
	
	protected String [] predefined = null;
	
	public InputComposite(Composite parent, int style,String section, String[]predefined) {
		super (parent,style);
		customSettingsSection = section;
		
		dialogSettings = Activator.getDefault().getDialogSettings().getSection(section);
		if (dialogSettings == null) {
			Activator.getDefault().getDialogSettings().addNewSection(section);
			dialogSettings = Activator.getDefault().getDialogSettings().getSection(section);
		}
	}
	
	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		
		Point p = super.computeSize(wHint, hHint,false);
		
		if (p.y > MAX_HEIGHT)
			p.y = MAX_HEIGHT;
		p.x = wHint;
		return p;
	}
	
	protected GridData makeGridForControl(Control control,int rows) {
		//get the size to hold five lines of text
		GC gc = new GC(control);
		FontMetrics fm = gc.getFontMetrics();
		gc.dispose ();
		
		
		int height = rows * fm.getHeight();
		GridData data = new GridData();
		data.heightHint = height;
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		
		return data;
	}
	
	public abstract boolean testFilled();
	
	public ArrayList<String> validate() {
		ArrayList<String> errors = null;
		if (validator != null) {
			errors = validator.validate(input);
		}
		return errors;
	}
	
	
	public void saveSettings() {	
	}
	
	
	public abstract void setInitialValue(Object value);
	
	/**
	 * @return the saveSettingsOnExit
	 */
	public final boolean isSaveSettingsOnExit() {
		return saveSettingsOnExit;
	}

	/**
	 * @return the saveSettingsOnExit
	 */
	public final boolean getSaveSettingsOnExit() {
		return saveSettingsOnExit;
	}
	
	/**
	 * @param saveSettingsOnExit the saveSettingsOnExit to set
	 */
	public final void setSaveSettingsOnExit(boolean saveSettingsOnExit) {
		this.saveSettingsOnExit = saveSettingsOnExit;
	}

	
	/**
	 * @return the input
	 */
	public Object getInput() {
		return input;
	}

	/**
	 * @param input the input to set
	 */
	public final void setInput(Object input) {
		this.input = input;
	}


	/**
	 * @return the customSettingsSection
	 */
	public final String getCustomSettingsSection() {
		return customSettingsSection;
	}


	/**
	 * @param customSettingsSection the customSettingsSection to set
	 */
	public final void setCustomSettingsSection(String customSettingsSection) {
		this.customSettingsSection = customSettingsSection;
	}

	/**
	 * @return the predefined
	 */
	public final String[] getPredefined() {
		return predefined;
	}


	/**
	 * @param predefined the predefined to set
	 */
	public final void setPredefined(String[] predefined) {
		this.predefined = predefined;
	}

	/**
	 * @return the validator
	 */
	public final Validator getValidator() {
		return validator;
	}

	/**
	 * @param validator the validator to set
	 */
	public final void setValidator(Validator validator) {
		this.validator = validator;
	}

	/**
	 * @return the required
	 */
	public final boolean isRequired() {
		return required;
	}

	/**
	 * @param required the required to set
	 */
	public final void setRequired(boolean required) {
		this.required = required;
	}

	
}
