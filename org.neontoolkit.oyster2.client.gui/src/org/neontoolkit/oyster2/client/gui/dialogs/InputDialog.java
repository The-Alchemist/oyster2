/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Shell;
import org.neontoolkit.oyster2.client.gui.Activator;

/**
 * @author David Muñoz
 *
 */
public abstract class InputDialog extends TrayDialog {
	
	/**
	 * Children dialogs may use this to save data 
	 */
	private String customSettingsSection = null;
	
	private Object input = null;
	
	protected IDialogSettings dialogSettings = null;
	
	public static final String DIALOG_SETTINGS_SUFFIX = "Dialog"; //$NON-NLS-1$
	
	public static final String ITEMS_KEY = "Items"; //$NON-NLS-1$
	/* This method must be overriden to create the correct section */
	protected InputDialog(String section, Shell parentShell) {
		super(parentShell);
		customSettingsSection = section;
		dialogSettings = Activator.getDefault().getDialogSettings().getSection(section);
		if (dialogSettings == null) {
			Activator.getDefault().getDialogSettings().addNewSection(section);
			dialogSettings = Activator.getDefault().getDialogSettings().getSection(section);
		}
	}
	

	protected InputDialog(String section,IShellProvider parentShell) {
		super(parentShell);
		customSettingsSection = section;
		dialogSettings = Activator.getDefault().getDialogSettings().getSection(section);
		if (dialogSettings == null) {
			Activator.getDefault().getDialogSettings().addNewSection(section);
			dialogSettings = Activator.getDefault().getDialogSettings().getSection(section);
		}
	}
	
	
	private InputDialog(IShellProvider parentShell) {
		super(parentShell);
	}

	private InputDialog(Shell parentShell) {
		super(parentShell);
	}
	

	/**
	 * @return the input
	 */
	public final Object getInput() {
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
	
	
	
}
