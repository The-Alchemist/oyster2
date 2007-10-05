package org.neon_toolkit.registry.ui;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;

public class ResizablePreferenceDialog extends PreferenceDialog {

	private int height = SWT.DEFAULT;
	private int width = SWT.DEFAULT;

	public ResizablePreferenceDialog(Shell shell, PreferenceManager manager){
		super(shell, manager);
	}
	
	public void setInitialSize(int width, int height){
		this.height = height;
		this.width = width;
	}
	
	protected Point getInitialSize() {
		return this.getShell().computeSize(width, height, true);
	}
}
