/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.search.composites;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.neontoolkit.oyster2.client.gui.Activator;

/**
 * @author David Muñoz
 * dryadcito at yahoo dot es
 *
 */
public abstract class FilterComposite extends Composite {
	
	protected IDialogSettings dialogSettings = null;
	
	protected static final String DIALOG_ITEMS_KEY = "items";
		
	private boolean saveSettingsOnExit = false;
	
	protected String [] predefined = null;
	
	private static int MAX_HEIGHT = 200;
	
	/**
	 * Initializes the dialog settings object creating the section with the given name
	 * and stores predefined values that may be accessed by inheriting composites. These
	 * predefined values are intended to be used in combos.
	 * @param parent
	 * @param style
	 * @param section
	 * @param predefined
	 */
	public FilterComposite(Composite parent, int style,String section, String[]predefined) {
		super (parent,style);
		this.predefined = predefined;
		dialogSettings = Activator.getDefault().getDialogSettings().getSection(section);
		if (dialogSettings == null) {
			Activator.getDefault().getDialogSettings().addNewSection(section);
			dialogSettings = Activator.getDefault().getDialogSettings().getSection(section);
		}
	}
	
	public abstract void loadSettings();
	
	protected int getWidthForComparator(Control control) {
		GC gc = new GC(control);
		FontMetrics fm = gc.getFontMetrics();
		gc.dispose ();
		
		int characters = 9;
		int width = characters * fm.getAverageCharWidth();
		
		return width; 
	}
	
	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		
		Point p = super.computeSize(wHint, hHint,false);
		
		if (p.y > MAX_HEIGHT)
			p.y = MAX_HEIGHT;
		p.x = wHint;
		return p;
	}
	
	
	
	
	public void saveSettings() {
	}
	
	public abstract Object getFilterData();
	
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

	
}
