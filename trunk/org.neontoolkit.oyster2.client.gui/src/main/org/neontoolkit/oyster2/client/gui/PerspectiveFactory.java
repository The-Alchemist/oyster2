/**
 * 
 */
package org.neontoolkit.oyster2.client.gui;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * @author david
 *
 */
public class PerspectiveFactory implements IPerspectiveFactory {


	public static final String OYSTER2_SEARCH_VIEW_ID =
		"org.neontoolkit.oyster2.client.gui.views.searchView";
	
	public static final String OYSTER2_RESULTS_VIEW_ID =
		"org.neontoolkit.oyster2.client.gui.views.SearchResultsView";
	
	public static final String OYSTER2_RESULTS_VIEW_SECONDARY_ID =
		"ResultsSecondaryId";
	
	
	public static final String OYSTER2_RESULT_DETAILS_VIEW_ID =
		"org.neontoolkit.oyster2.client.gui.SearchResultDetails";
	
	
	public static final String OYSTER2_ACTION_SET_SUBMIT_ID =
		"org.neontoolkit.oyster2.client.gui.actionSet.submit";
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
	 */
	public void createInitialLayout(IPageLayout layout) {
		
		addActions(layout);
		addViews(layout);
		layout.setEditorAreaVisible(false);
		
//		 Get the editor area.
		
		// Top left: Resource Navigator view and Bookmarks view placeholder
		/*IFolderLayout topLeft = layout.createFolder("topLeft", IPageLayout.LEFT, 0.25f,
			editorArea);
		topLeft.addView(IPageLayout.ID_RES_NAV);
		topLeft.addPlaceholder(IPageLayout.ID_BOOKMARKS);

		// Bottom left: Outline view and Property Sheet view
		IFolderLayout bottomLeft = layout.createFolder("bottomLeft", IPageLayout.BOTTOM, 0.50f,
			"topLeft");
		bottomLeft.addView(IPageLayout.ID_OUTLINE);
		bottomLeft.addView(IPageLayout.ID_PROP_SHEET);

		// Bottom right: Task List view
		layout.addView(IPageLayout.ID_TASK_LIST, IPageLayout.BOTTOM, 0.66f, editorArea);*/
		
	}
	private void addActions(IPageLayout layout) {
		//layout.addActionSet(OYSTER2_ACTION_SET_SUBMIT_ID);
	}
	
	private void addViews(IPageLayout layout) {
		String editorArea = layout.getEditorArea();

		IFolderLayout topLeft = layout.createFolder("topLeft", IPageLayout.LEFT, 0.25f,
				editorArea);
		topLeft.addView(OYSTER2_SEARCH_VIEW_ID);
		topLeft.addPlaceholder(OYSTER2_SEARCH_VIEW_ID);
		
		IFolderLayout topRight = layout.createFolder("topRight", IPageLayout.RIGHT, 0.25f,
					editorArea);
		
		//topRight.addView(OYSTER2_RESULTS_VIEW_ID);
		topRight.addPlaceholder(OYSTER2_RESULTS_VIEW_ID +":" + OYSTER2_RESULTS_VIEW_SECONDARY_ID
				+ "*");
		
		IFolderLayout bottomRight = layout.createFolder("bottomRight", IPageLayout.BOTTOM, 0.50f,
			"topRight");
		bottomRight.addPlaceholder(OYSTER2_RESULT_DETAILS_VIEW_ID);
		bottomRight.addView(OYSTER2_RESULT_DETAILS_VIEW_ID);
			
		/*
		layout.addView(OYSTER2_SEARCH_VIEW_ID,
				IPageLayout.BOTTOM,0.5f, layout.getEditorArea());
		
		layout.addView(OYSTER2_RESULTS_VIEW_ID,
				IPageLayout.TOP,0.5f, OYSTER2_SEARCH_VIEW_ID);
		layout.addView(OYSTER2_RESULT_DETAILS_VIEW_ID,
				IPageLayout.LEFT,0.5f, layout.getEditorArea());*/
	}
	
}
