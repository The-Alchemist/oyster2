/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.neontoolkit.oyster2.client.gui.Activator;

/**
 * @author David Muñoz
 *
 */
public abstract class ResizableDialog extends TrayDialog {

	
	//constants
	
	public final static String TAG_X = "x"; //$NON-NLS-1$
	public final static String TAG_Y = "y"; //$NON-NLS-1$
	public final static String TAG_WIDTH = "width"; //$NON-NLS-1$
	public final static String TAG_HEIGHT = "height"; //$NON-NLS-1$
	
	
	//private and protected members
	protected IDialogSettings dialogSettings = null;
	private Rectangle cachedBounds = null;

	
	
	/*TODO fields to be accessed by listener when the user makes
	 * any input
	 */
	
	/* This method must be overriden to create the correct section */
	protected ResizableDialog(String name, IShellProvider parentShell) {
		this(parentShell);
		dialogSettings = Activator.getDefault().getDialogSettings().getSection(name);
		if (dialogSettings == null) {
			Activator.getDefault().getDialogSettings().addNewSection(name);
			dialogSettings = Activator.getDefault().getDialogSettings().getSection(name);
		}
	}
	
	/* This method must be overriden to create the correct section */
	protected ResizableDialog(String name, Shell parentShell) {
		this(parentShell);
		dialogSettings = Activator.getDefault().getDialogSettings().getSection(name);
		if (dialogSettings == null) {
			Activator.getDefault().getDialogSettings().addNewSection(name);
			dialogSettings = Activator.getDefault().getDialogSettings().getSection(name);
		}
	}
	
	/* ******* PRIVATE CONSTRUCTORS ********************************/
	private ResizableDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
		//newControlListener();
	}
	
	private ResizableDialog(IShellProvider parentShell) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
		//newControlListener();
	}
	
	/* ********** METHODS ***********************************/
	

	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#getInitialLocation(org.eclipse.swt.graphics.Point)
	 */
	protected Point getInitialLocation(Point initialSize) {
		Rectangle displayBounds = getShell().getDisplay().getBounds();
		Rectangle oldBounds = loadBounds();
		if (oldBounds != null) {
			int x = oldBounds.x;
			int y = oldBounds.y;
			int maxX = displayBounds.x + displayBounds.width - initialSize.x;
			int maxY = displayBounds.y + displayBounds.height - initialSize.y;
			if (x > maxX)
				x = maxX;
			if (y > maxY)
				y = maxY;
			if (x < displayBounds.x)
				x = displayBounds.x;
			if (y < displayBounds.y)
				y = displayBounds.y;
			return new Point(x,y);
		}
		
		return super.getInitialLocation(initialSize);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#getInitialSize()
	*/ 
	protected Point getInitialSize() {
		Rectangle displaySize = getShell().getDisplay().getBounds();
		Rectangle oldBounds = loadBounds();

		if (oldBounds != null) {
			return new Point(
					displaySize.width < oldBounds.width ? displaySize.width : oldBounds.width,
					displaySize.height < oldBounds.height ? displaySize.height : oldBounds.height);
		}
		
		// if not previous size was stored
		int height = 
			PlatformUI.getWorkbench().getDisplay().getClientArea().height;
		int width =
			PlatformUI.getWorkbench().getDisplay().getClientArea().width;
		
		return new Point(width * 3/4,height*3/4);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#close()
	 */
	public boolean close() {
		boolean closed = super.close();
		if (cachedBounds != null) {
			saveBounds(cachedBounds);
		}
		return closed;
	}

	/* ****** PRIVATE ***************************************/
	

	/* methods to save and load location 
	 * and bounds of the dialog using the
	 * dialog settings 
	 */
	private Rectangle loadBounds() {
		Rectangle rectangle = null;
		try {
			 rectangle = new Rectangle(
					dialogSettings.getInt(TAG_X),
					dialogSettings.getInt(TAG_Y),
					dialogSettings.getInt(TAG_WIDTH),
					dialogSettings.getInt(TAG_HEIGHT));
		}
		catch (NumberFormatException e) {
		}
		return rectangle;
	}
	
	private void saveBounds(Rectangle rectangle) {
		dialogSettings.put(TAG_X, rectangle.x);
		dialogSettings.put(TAG_Y, rectangle.y);
		dialogSettings.put(TAG_WIDTH, rectangle.width);
		dialogSettings.put(TAG_HEIGHT, rectangle.height);
	}
	
	/**
	 * Creates a control listener that will take care of
	 * saving the location and size of the dialog each
	 * time it changes, so they will be saved when
	 * the user closes the dialog 
	 */
	private void newControlListener() {
		//TODO couldn't I save these just when the close
		//method is called??
		getShell().addControlListener(
				new ControlListener() {
					public void controlMoved(ControlEvent e) {
						cachedBounds = getShell().getBounds();
					}
					public void controlResized(ControlEvent e) {
						cachedBounds = getShell().getBounds();		
					}
				});
	}

	
	
}
