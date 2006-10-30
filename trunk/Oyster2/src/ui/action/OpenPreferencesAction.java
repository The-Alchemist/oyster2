package ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;



public class OpenPreferencesAction extends Action {
	
	private ApplicationWindow window;
	
	/**
	 * Create a new <code>OpenPreferenceAction</code>
	 */
	public OpenPreferencesAction(ApplicationWindow w) {
		this.window = w;
		setToolTipText("Open preferences");
		setText("Preferences");
	}

	/**
	 * Perform the action: open the preference dialog.
	 */
	public void run() {
		/*TODO PreferenceManager pm = new PreferenceManager();
		BibsterPreferencesPage p = new BibsterPreferencesPage("Main preferences");
		pm.addToRoot(new PreferenceNode("mainBibsterProperties", p));
		//ColumnPreferencesPage p2 = new ColumnPreferencesPage("Column preferences");
		//pm.addToRoot(new PreferenceNode("columnProperties", p2));
		ResizablePreferenceDialog d = new ResizablePreferenceDialog(window.getShell(), pm);
		d.setInitialSize(700, SWT.DEFAULT);
		d.create();
		d.open();*/
	}
}

