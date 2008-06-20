package org.neontoolkit.oyster2.client.gui.dialogs;

import java.util.Calendar;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.ColumnLayout;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.neontoolkit.oyster2.client.gui.dialogs.content.CalendarComposite;

public class TestDialog extends TrayDialog {

	FormToolkit toolkit = null;
	
	ScrolledForm form = null;
	
	public TestDialog(IShellProvider parentShell) {
		super(parentShell);
		setShellStyle(parentShell.getShell().getStyle()| SWT.RESIZE| SWT.MAX);
		// TODO Auto-generated constructor stub
	}
	public TestDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(parentShell.getStyle()| SWT.RESIZE| SWT.MAX);
		// TODO Auto-generated constructor stub
	}
	
	
	protected Control createDialogArea(Composite parent) {
		Composite base = (Composite)super.createDialogArea(parent);
		toolkit =  new FormToolkit(base.getDisplay());
		form = toolkit.createScrolledForm(base);
		base.setLayout(new FillLayout());
		form.setBackground(parent.getBackground());
		form.setBackgroundMode(SWT.INHERIT_NONE);
		
		form.getBody().setLayout(new ColumnLayout());
		String items[] = new String[] {"item1","item2"};
		String names[] = new String[] {"name1","name2"};
		String lastnames[] = new String[] {"lastname1","lastname2"};
		//Composite c = new PersonSelectionComposite(form.getBody(),SWT.NONE,items,
		//		names,lastnames);
		//Composite c = new OrganizationSelectionComposite(form.getBody(),SWT.NONE,
		//		items,names);
		
		Composite c = new CalendarComposite(form.getBody(),SWT.NONE,Calendar.getInstance().getTime());
		
		return base;
	}
}
