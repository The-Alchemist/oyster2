/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;


/**
 * @author David Muñoz
 *
 */
public class SearchDialog extends ResizableDialog {

	//constants
	public final static String SETTINGS_SECTION ="Oyster2_search_dialog_section";
	
	
	//private members
	private CTabItem ontologyItem;
	private CTabFolder tabFolder;
	
	
	public SearchDialog(String name, Shell parentShell) {
		super(name,parentShell);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		
		Composite baseComposite = (Composite) super.createDialogArea(parent);
		baseComposite.setLayout(new FillLayout());
		
	    /* TabFolder */
	    tabFolder = new CTabFolder(baseComposite, SWT.FLAT);
	    	    
	    /* ONTOLOGY TAB */
	    ontologyItem = new CTabItem(tabFolder,SWT.NONE);
	    ontologyItem.setText("ontology");

	    //Composite ontologyComposite = new SubmitOntologyItemComposite(tabFolder,SWT.NONE);
	    //ontologyItem.setControl(ontologyComposite);
	  
		return baseComposite;
	}


	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Search criteria");
	}

	
	
	
}
