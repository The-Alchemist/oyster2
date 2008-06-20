/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.search.composites;

import java.util.Iterator;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.neontoolkit.oyster2.client.gui.search.Filter;
import org.neontoolkit.oyster2.client.gui.search.FilterValue;
import org.neontoolkit.oyster2.client.gui.search.PersonFilterValue;

/**
 * @author David Muñoz
 *
 */
public class PersonFilterComposite extends MultivalueFilterComposite {

	/**
	 * @param parent
	 * @param style
	 * @param section
	 * @param predefined
	 * @param comparators
	 * @param filter
	 */
	public PersonFilterComposite(Composite parent, int style, String section,
			String[] predefined, String[] comparators, Filter filter) {
		super(parent, style, section, predefined, comparators, filter);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void makeTableViewer(String[] comparators) {
		String [] columnNames = new String[3];
		TableColumn column = null;
		tableViewer = new TableViewer(this,SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		Table table = tableViewer.getTable();
		
		column = new TableColumn(table,SWT.LEFT);
		column.setText("name");
		columnNames[0] = column.getText();
		column.pack();
		table.showColumn(column);
		
		column = new TableColumn(table,SWT.LEFT);
		column.setText("lastname");
		columnNames[1] = column.getText();
		column.pack();
		table.showColumn(column);
		
		
		column = new TableColumn(table,SWT.LEFT);
		column.setText("comparator");
		columnNames[2] = column.getText();
		column.pack();
		table.showColumn(column);
		/*
		column = new TableColumn(table,SWT.LEFT);
		column.setText("negate");
		columnNames[3] = column.getText();
		column.pack();
		table.showColumn(column);
		*/
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		//cell editors
		CellEditor[] cellEditors = new CellEditor[4];
		cellEditors[0] = new TextCellEditor(table);
		cellEditors[1] = new TextCellEditor(table);
		
		cellEditors[2] =
			new ComboBoxCellEditor(table,this.comparators,SWT.READ_ONLY);
		//cellEditors[3] = new CheckboxCellEditor(table);
		
		
		tableViewer.setCellEditors(cellEditors);
		tableViewer.setColumnProperties(columnNames);
		tableViewer.setCellModifier(
				new PersonFilterCellModifier(tableViewer,this.comparators));
		tableViewer.setLabelProvider(new PersonFilterLabelProvider());
		tableViewer.setContentProvider(new ArrayContentProvider());
	}
	
	
	protected void makeListeners() {
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				if (event.widget == addButton) {
					PersonFilterValue person = new PersonFilterValue();
					person.setName("name");
					person.setLastname("lastname");
					FilterValue filter = new FilterValue();
					filter.setComparator(comparators[0]);
					filter.setNegate(Boolean.valueOf(false));
					filter.setValue(person);
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

	
}
