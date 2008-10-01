/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs;


import java.text.SimpleDateFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.neontoolkit.oyster2.client.gui.GUIConstants;
import org.vafada.swtcalendar.SWTCalendar;

/**
 * @author David Muñoz
 *
 */
public class CalendarDialog extends InputDialog {

	private SWTCalendar calendarComposite = null;
	
	public CalendarDialog(String section,Shell parentShell) {
		super(section,parentShell);
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Choose date");
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite baseComposite = (Composite)super.createDialogArea(parent);
		baseComposite.setLayout(new FillLayout());
		calendarComposite = new SWTCalendar(baseComposite,SWT.NONE);
		baseComposite.pack();		
		return baseComposite;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		SimpleDateFormat dateFormat =
			new SimpleDateFormat(GUIConstants.DATE_FORMAT);
		
		setInput(dateFormat.format(calendarComposite.getCalendar().getTime()));
		super.okPressed();
		
	}
	
	
}
