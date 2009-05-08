package org.neontoolkit.collab.views;

import java.awt.MouseInfo;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchPartSite;

/**
 * 
 * @author José Monte(monte@uni-koblenz.de)
 * 
 */
public class ContextMenuManager extends MenuManager {
	private Viewer viewer;
	private Menu contextMenu;
	private IWorkbenchPartSite site;
	private String contextMenuId;

	private int posX;
	private int posY;

	/**
	 * 
	 * @param contextMenuId
	 * @param viewer
	 */
	public ContextMenuManager(String contextMenuId, Viewer viewer,
	    IWorkbenchPartSite site) {
		super();
		this.contextMenuId = contextMenuId;
		this.viewer = viewer;
		this.site = site;
		setRemoveAllWhenShown(false);
		createContextMenu();
		registerListener();
		registerContextMenu();
	}

	/**
	 * 
	 */
	private void registerListener() {
		addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				if (!ContextMenuManager.this.viewer.getSelection().isEmpty()) {
					// set the "right" location of the context menu
					ContextMenuManager.this.contextMenu.setLocation(new Point(
					    ContextMenuManager.this.posX, ContextMenuManager.this.posY));
				} else {
					ContextMenuManager.this.contextMenu.setVisible(false);
				}
			}
		});

		viewer.getControl().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// when selecting an item, the current mouse position needs to be
				// saved...
				ContextMenuManager.this.posX = MouseInfo.getPointerInfo().getLocation().x;
				ContextMenuManager.this.posY = MouseInfo.getPointerInfo().getLocation().y;
			}
		});
	}

	/**
	 * 
	 */
	private void createContextMenu() {
		contextMenu = createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(contextMenu);
	}

	/**
	 * 
	 */
	public void registerContextMenu() {
		site.registerContextMenu(contextMenuId, this, viewer);
	}
}

