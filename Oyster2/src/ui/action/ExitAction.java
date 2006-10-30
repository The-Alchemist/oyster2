package ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.ApplicationWindow;

public class ExitAction extends Action {
	
	private ApplicationWindow window;

	public ExitAction(ApplicationWindow w) {
		window = w;
		setToolTipText("Exit the application");
		setText("E&xit@Ctrl+Q");
	}

	public void run() {
		window.close();
	}
}
