/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.search.composites;



import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.neontoolkit.oyster2.client.gui.dialogs.CalendarDialog;
import org.neontoolkit.oyster2.client.gui.search.Filter;

/**
 * @author David Muñoz
 * dryadcito at yahoo dot es
 *
 */
public class DateFilterComposite extends SingleFilterComposite {

	private Button dialogButton = null;
	
	private CalendarDialog nestedDialog = null;
	
	public DateFilterComposite(Composite parent, int style, String section,
			String[] predefined, String [] comparators,Filter filter) {
		super(parent, style, section, predefined,comparators,filter);
		dialogButton = new Button(this,SWT.PUSH);
		dialogButton.setText(Messages.getString("DateFilterComposite.dialob.button.text.changedate")); //$NON-NLS-1$
		addButtonListener();
		nestedDialog = new CalendarDialog(section+"CalendarDialog",getShell()); //$NON-NLS-1$
	}

	protected void makeLayout() {
	super.makeLayout();
	GridLayout layout = (GridLayout)getLayout();
	layout.numColumns++;
	
		
	}
	
	private void addButtonListener() {
		
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				if (event.widget == dialogButton) {
					int result = nestedDialog.open();
					if (result == TrayDialog.OK) {
						String input = (String)nestedDialog.getInput();
						valueCombo.setText(input);
						
					}
				}
				
			}
		};
		dialogButton.addListener(SWT.Selection,listener);
		
	}

	/**
	 * @return the nestedDialog
	 */
	public final CalendarDialog getNestedDialog() {
		return nestedDialog;
	}

	/**
	 * @param nestedDialog the nestedDialog to set
	 */
	public final void setNestedDialog(CalendarDialog nestedDialog) {
		this.nestedDialog = nestedDialog;
	}
	

	
}
