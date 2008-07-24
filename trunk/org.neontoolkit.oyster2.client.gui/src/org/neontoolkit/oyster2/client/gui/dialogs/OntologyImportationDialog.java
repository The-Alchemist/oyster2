/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs;

import java.io.File;
import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.neontoolkit.oyster2.client.gui.adapter.IOMVObject;
import org.neontoolkit.oyster2.client.gui.adapter.results.IResultsAdapter;
import org.neontoolkit.oyster2.client.gui.results.ResultsLabelProvider;
import org.semanticweb.kaon2.api.KAON2Exception;
import org.semanticweb.kaon2.api.KAON2Manager;

import com.ontoprise.api.StringRepresentation;
import com.ontoprise.ontostudio.datamodel.DatamodelPlugin;
import com.ontoprise.util.URIUtilities;

/**
 * @author David Muñoz
 *
 */
public class OntologyImportationDialog extends ResizableInputDialog {

	private final static String DEFAULT_SECTION =
			OntologyImportationDialog.class.getSimpleName();
	
	private TableViewer viewer = null;
	
	private Composite ontologyListComposite  = null;
	
	private Composite projectSelectionComposite = null;
	
	private IOMVObject[] selection = null;
	
	private String project = null;
	
	private String[] columnsToShow = null;
	
	private IResultsAdapter results = null;
	
	private FormToolkit toolkit = null;
	
	private Form form = null;
	
	private List projectList = null;
	
	/**
	 * @param section
	 * @param parentShell
	 */
	public OntologyImportationDialog(String section, Shell parentShell) {
		super(section, parentShell);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param section
	 * @param parentShell
	 */
	public OntologyImportationDialog(String section, IShellProvider parentShell) {
		super(section, parentShell);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param section
	 * @param parentShell
	 */
	public OntologyImportationDialog(Shell parentShell) {
		super(DEFAULT_SECTION,parentShell);
		// TODO Auto-generated constructor stub
	}
	
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.getString("OntologyImportationDialog.shell.title")); //$NON-NLS-1$
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite baseComposite = (Composite) super.createDialogArea(parent);
		baseComposite.setLayout(new FillLayout());
		
		
		toolkit = new FormToolkit(baseComposite.getDisplay());
		
		form = toolkit.createForm(baseComposite);
		
		form.setText(Messages.getString("OntologyImportationDialog.form.title")); //$NON-NLS-1$
		
		form.setBackground(parent.getBackground());
		form.setBackgroundMode(SWT.INHERIT_NONE);
		
		ontologyListComposite = makeOntologyListComposite();
		projectSelectionComposite = makeProjectSelectionDialog();
		makeLayout();
		return baseComposite;
	}
	

	private void makeLayout() {
		GridLayout layout = new GridLayout(1,true);
		layout.horizontalSpacing = 10;
		layout.verticalSpacing = 10;
		form.getBody().setLayout(layout);
		
		
		GC gc = new GC((Control)viewer.getTable());
		FontMetrics fm = gc.getFontMetrics();
		gc.dispose ();
		
		
		int heightHint = 10 * fm.getHeight();
		
		GridData gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.heightHint = heightHint;
		
		
		ontologyListComposite.setLayoutData(gd);
		
		gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		heightHint = 10*fm.getHeight();
		gd.heightHint = heightHint;
		projectSelectionComposite.setLayoutData(gd);
	}

	private Composite makeProjectSelectionDialog() {
		// get the project list
		Group group = new Group(form.getBody(),SWT.SHADOW_ETCHED_IN);
		group.setLayout(new FillLayout());
		group.setText(Messages.getString("OntologyImportationDialog.select.project.label")); //$NON-NLS-1$
		String [] projectNames = null;
		try {
			projectNames = DatamodelPlugin.getDefault().getOntologyProjects();
			projectList = new List(group,SWT.SINGLE|SWT.BORDER);
			projectList.setItems(projectNames);
		} catch (CoreException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return group;
	}

	private Composite makeOntologyListComposite() {
		// TODO Auto-generated method stub
		Group group = new Group(form.getBody(),SWT.SHADOW_ETCHED_IN);
		group.setLayout(new FillLayout());
		group.setText(Messages.getString("OntologyImportationDialog.selected.ontologies.label")); //$NON-NLS-1$
		
		viewer =
			new TableViewer(group, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL| SWT.FULL_SELECTION);
		
		Table table = viewer.getTable();
		TableColumn column = null;
		
		for (int i = 0;i<columnsToShow.length;i++) {
			column = new TableColumn(table,SWT.LEFT);
			column.setText(results.getLabel(columnsToShow[i]));
			column.setData(columnsToShow[i]);
			column.setMoveable(true);
			column.pack();
			table.showColumn(column);			
		}
		
		
		viewer.setContentProvider(new ArrayContentProvider());
		
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		
		ResultsLabelProvider labelProvider =
			new ResultsLabelProvider(viewer);
		viewer.setLabelProvider(labelProvider);
		viewer.setColumnProperties(columnsToShow);
		viewer.setInput(selection);
		return group;
		
	}

	@Override
	protected void okPressed() {
		int selectedIndex =projectList.getSelectionIndex();
		if (selectedIndex == -1) {
			MessageDialog.openError(getShell(),Messages.getString("OntologyImportationDialog.error.label"),Messages.getString("OntologyImportationDialog.error.missing.project")); //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}
		String projectName = projectList.getItem(selectedIndex);
		setInput(projectName);
		project = projectName;
		super.okPressed();
	}
	
	public String getModule(URL location, String projectName) throws KAON2Exception {
		// TODO Auto-generated method stub
		String logicalURI = ""; //$NON-NLS-1$
    	
    	if (location.getProtocol().equals("file")) { //$NON-NLS-1$
    		String fileName = location.getFile();
    		String physicalURI = new File(fileName).toURI().toString();
    		logicalURI = KAON2Manager.getOntologyURI(physicalURI, null);
    	} else {
    		logicalURI = KAON2Manager.getOntologyURI(location.toString(), null); 
    	}
    	
        //return StringRepresentation.toString(IDTermUtil.uriToTerm(logicalURI));
        return StringRepresentation.toString(URIUtilities.ontologyURIToModule(logicalURI));
	}
	

	/**
	 * @return the selection
	 */
	public final IOMVObject[] getSelection() {
		return selection;
	}

	/**
	 * @param selection the selection to set
	 */
	public final void setSelection(IOMVObject[] selection) {
		this.selection = selection;
	}

	/**
	 * @return the columnsToShow
	 */
	public final String[] getColumnsToShow() {
		return columnsToShow;
	}

	/**
	 * @param columnsToShow the columnsToShow to set
	 */
	public final void setColumnsToShow(String[] columnsToShow) {
		this.columnsToShow = columnsToShow;
	}

	/**
	 * @return the results
	 */
	public final IResultsAdapter getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public final void setResults(IResultsAdapter results) {
		this.results = results;
	}

	/**
	 * @return the project
	 */
	public final String getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public final void setProject(String project) {
		this.project = project;
	}

	
	
}
