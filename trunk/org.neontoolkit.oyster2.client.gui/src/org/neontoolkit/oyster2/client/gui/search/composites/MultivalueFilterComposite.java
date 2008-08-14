/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.search.composites;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.neontoolkit.oyster2.client.gui.GUIConstants;
import org.neontoolkit.oyster2.client.gui.search.Filter;
import org.neontoolkit.oyster2.client.gui.search.FilterValue;

import contributed.john.mason.CComboBoxCellEditor;

/**
 * @author David Muñoz
 *
 */
public class MultivalueFilterComposite extends FilterComposite {

	protected Filter filter = null;
	
	protected ArrayList<FilterValue> filters = null;
	//columns: value, comparator, negate
	
	protected TableViewer tableViewer = null;
	
	protected Button addButton = null;
	
	protected Button removeButton = null;
	
	protected String[] comparators = null;
	

	/**
	 * @param parent
	 * @param style
	 * @param section
	 * @param predefined
	 */
	public MultivalueFilterComposite(Composite parent, int style,
			String section, String[] predefined, String []comparators,
			Filter filter) {
		super(parent, style, section, predefined);
		this.filter = filter;
		
		this.comparators = comparators;
		filters = filter.getFilterValues();
		
		makeTableViewer(comparators);
		tableViewer.setInput(filters);
		
		makeButtons();
		makeLayout();
		makeListeners();
	}
	
	protected void makeListeners() {
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				if (event.widget == addButton) {
					FilterValue filter = new FilterValue();
					filter.setComparator(comparators[0]);
					filter.setNegate(Boolean.valueOf(false));
					filter.setValue(Messages.getString("MultivalueFilterComposite.filter.table.column.text")); //$NON-NLS-1$
					filters.add(filter);
					tableViewer.add(filter);
				}
				else if (event.widget == removeButton) {
					FilterValue filter = null;
					IStructuredSelection selection = (IStructuredSelection)
						tableViewer.getSelection();
					Iterator it = selection.iterator();
					while (it.hasNext()) {
						filter = (FilterValue)it.next();
						filters.remove(filter);
						tableViewer.remove(filter);
					}
				}
			}
		};
		addButton.addListener(SWT.Selection,listener);
		removeButton.addListener(SWT.Selection,listener);
	}

	private void makeButtons() {
		addButton = new Button(this, SWT.PUSH);
		addButton.setText(Messages.getString("MultivalueFilterComposite.button.add.text")); //$NON-NLS-1$
		removeButton = new Button(this,SWT.PUSH);
		removeButton.setText(Messages.getString("MultivalueFilterComposite.button.remove.text")); //$NON-NLS-1$
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		
		GC gc = new GC(tableViewer.getTable());
		FontMetrics fm = gc.getFontMetrics();
		gc.dispose ();
		
		int rows = 6;
		int y = rows * fm.getHeight();
		return super.computeSize(wHint, y,changed);
	}
		
	
	private void makeLayout() {
		setLayout(new FormLayout());
		FormData fd = null;
		//buttons
		fd = new FormData();
		fd.top = new FormAttachment(tableViewer.getTable().getHeaderHeight(),5);
		fd.right = new FormAttachment(100,-5);
		fd.width = GUIConstants.BUTTON_WIDTH;
		addButton.setLayoutData(fd);
		
		fd = new FormData();
		fd.top = new FormAttachment(addButton,5);
		fd.right = new FormAttachment(100,-5);
		fd.width = GUIConstants.BUTTON_WIDTH;
		removeButton.setLayoutData(fd);
		
		//table viewer
		fd = new FormData();
		fd.top = new FormAttachment(0,5);
		fd.left = new FormAttachment(0,5);
		fd.right = new FormAttachment(addButton,-5);
		fd.bottom = new FormAttachment(100,-5);
		tableViewer.getTable().setLayoutData(fd);
	}

	protected void makeTableViewer(String []comparators) {
		String [] columnNames = new String[2];
		TableColumn column = null;
		tableViewer = new TableViewer(this,SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		Table table = tableViewer.getTable();
		
		column = new TableColumn(table,SWT.LEFT);
		column.setText(Messages.getString("MultivalueFilterComposite.filter.table.column.text")); //$NON-NLS-1$
		columnNames[0] = column.getText();
		column.pack();
		
		table.showColumn(column);
		
		column = new TableColumn(table,SWT.LEFT);
		column.setText(Messages.getString("MultivalueFilterComposite.comparator.table.column.text")); //$NON-NLS-1$
		columnNames[1] = column.getText();
		column.pack();
		table.showColumn(column);
		
		/*column = new TableColumn(table,SWT.LEFT);
		column.setText("negate");
		columnNames[2] = column.getText();
		column.pack();
		table.showColumn(column);
		*/
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		//cell editors
		CellEditor[] cellEditors = new CellEditor[2];
		if (predefined != null)
			if (predefined.length == 0)
				cellEditors[0] = new TextCellEditor(table);
			else
				cellEditors[0] = new CComboBoxCellEditor(table,predefined,SWT.DROP_DOWN);
		else
			cellEditors[0] = new TextCellEditor(table);
		cellEditors[1] =
			new ComboBoxCellEditor(table,this.comparators,SWT.READ_ONLY);
		//cellEditors[2] = new CheckboxCellEditor(table);
		
		
		tableViewer.setCellEditors(cellEditors);
		tableViewer.setColumnProperties(columnNames);
		tableViewer.setCellModifier(
				new StringFilterCellModifier(tableViewer,this.comparators));
		tableViewer.setLabelProvider(new StringFilterLabelProvider());
		tableViewer.setContentProvider(new ArrayContentProvider());
		
	}

	/* (non-Javadoc)
	 * @see org.neontoolkit.oyster2.client.gui.search.composites.FilterComposite#getFilterData()
	 */
	@Override
	public Filter getFilterData() {
		Iterator<FilterValue> it = filter.getFilterValues().iterator();
		FilterValue filterValue = null;
		if (filter.getFilterValues().size() == 0)
			return null;
		while (it.hasNext()) {
			filterValue = it.next();
			if (filterValue.getValue().toString().trim().equals("")) { //$NON-NLS-1$
				it.remove();
			}
		}
		return filter;
	}
	
	public void saveSettings() {
		
	}
	
	@Override
	public void loadSettings() {
		// TODO Auto-generated method stub
		
	}

}
