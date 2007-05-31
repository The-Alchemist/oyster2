package ui.action;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.ApplicationWindow;



public class AboutOyster2Action extends Action {
	
	//private static final Logger LOG = Logger.getLogger(AboutBibsterAction.class);
	
	private ApplicationWindow window;

	public AboutOyster2Action(ApplicationWindow window) {
		try {
			this.window = window;
			setText("About...");
			setToolTipText("Show info about Bibster");
		} catch (Throwable t) {
			//LOG.error("Error", t);
		}
	}
	
	public void run() {
		try {
			/*TODO AboutBibsterDialog dialog = new AboutBibsterDialog(window.getShell());
			dialog.create();
			dialog.open();*/
		} catch (Throwable t) {
			//LOG.error("Error", t);
		}
	}

}
