package org.neon_toolkit.oyster.plugin.iwizard;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
//import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

//import org.semanticweb.kaon2.api.*;                 // This package contains the basic classes of the API
//import org.semanticweb.kaon2.api.owl.elements.*;    // This package contains classes used to represent elements of OWL ontologies

//Class that store the conditions of OMV
public class RegistryConditionPage extends WizardPage  {

		static RegistryActivator conn = RegistryActivator.getDefault();
		public static final String PAGE_NAME = "Conditions";
		public static final String OMV_URI = "http://omv.ontoware.org/2005/05/ontology";
		
		private Label label;
		private Table table;
		private Label formatLabel;
		
		public RegistryConditionPage() {
			super(PAGE_NAME, "Condition Page", null);		  
	        super.setDescription("Add the properties conditions for the desired ontologies"); //$NON-NLS-1$	        
		}

		public boolean performFinish() {
			//return this.getWizard().performCancel();
			return true;
		}

		public Table getTable() {
			return table;
		}
		
		
		public void createControl(Composite parent) {
			
			conn.setDistributed(false);
		    Composite topLevel = new Composite(parent, SWT.NONE);
		    topLevel.setLayout(new GridLayout(2, false));

		    GridData gridData;
		    
		    label = new Label(topLevel, SWT.LEFT);
		    label.setText("Create, edit, or remove conditions:");
		    gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);		    
		    gridData.horizontalSpan = 2;
	        label.setLayoutData(gridData);
		    
	    	table = new Table (topLevel, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);
	    	table.setLinesVisible (true);
	    	table.setHeaderVisible (true);

	    	//titles of columns
	    	String[] titles = {"OMV Property", "Value"};
	    	for (int i=0; i<titles.length; i++) {
	    		TableColumn column = new TableColumn (table, SWT.NONE);
	    		if (i==0) column.setWidth(150); 
	    		else column.setWidth(200);
	    		column.setText (titles [i]);
	    	}	
	    	
	    	//show only 15 rows to user
	    	int itemHeight = table.getItemHeight();
	        gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
	        gridData.heightHint = 15 * itemHeight;
	        table.setLayoutData(gridData);

	    	/*for (int i=0; i<titles.length; i++) {
	    		table.getColumn (i).pack ();
	    	}*/
	    	
	    	
	    	gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING);
	        Button newCondition = new Button(topLevel, SWT.PUSH);
	        newCondition.setText(" New Condition ");
	        newCondition.setLayoutData(gridData);
	        newCondition.addSelectionListener(new SelectionListener(){

				public void widgetDefaultSelected(SelectionEvent e) {

					final TableItem item = new TableItem(table, SWT.NONE);					
					table.showItem(item);
					item.setBackground(new Color(Display.getDefault(), 245, 245, 245));

					//update format information
					formatLabel.setText("");
					
					//refresh layout
			        Composite composite = table.getParent();
			        composite.layout();        
				}

				public void widgetSelected(SelectionEvent e) {
					//if (!table.isVisible()) table.setVisible(true);
					final TableItem item = new TableItem(table, SWT.NONE);
					table.showItem(item);
					item.setBackground(new Color(Display.getDefault(), 245, 245, 245));
					
					//update format information
					formatLabel.setText("");
					
					//refresh layout
			        Composite composite = table.getParent();
			        composite.layout();        
				}	        	
	        }
	        );

	        //show format to input the values
		    formatLabel = new Label(topLevel, SWT.BOTTOM);
		    formatLabel.setText("");
		    gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);		    
		    gridData.horizontalSpan = 2;
		    formatLabel.setLayoutData(gridData);

		    BooleanFieldEditor behindRouter = new BooleanFieldEditor("DistributedQuery", "Submit a distributed query",
					BooleanFieldEditor.SEPARATE_LABEL, topLevel);
			behindRouter.setPropertyChangeListener(new IPropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent event) {
					conn.setDistributed(!conn.getDistributed());
				}
			});
			
			
	        //get OMV properties
	        final String[] options = getOMVProperties();
	        
	        // Create an editor object to use for text editing
	        final TableEditor editor = new TableEditor(table);
	        editor.horizontalAlignment = SWT.LEFT;
	        editor.grabHorizontal = true;     
	        
	        // Use a mouse listener, not a selection listener, since we're interested
	        // in the selected column as well as row
	        table.addMouseListener(new MouseAdapter() {
	          public void mouseDown(MouseEvent event) {
	            // Dispose any existing editor
	            Control old = editor.getEditor();
	            if (old != null) old.dispose();

	            // Determine where the mouse was clicked
	            Point pt = new Point(event.x, event.y);

	            // Determine which row was selected
	            final TableItem item = table.getItem(pt);
	            if (item != null) {
	              // Determine which column was selected
	              int column = -1;
	              for (int i = 0, n = table.getColumnCount(); i < n; i++) {
	                Rectangle rect = item.getBounds(i);
	                if (rect.contains(pt)) {
	                  // This is the selected column
	                  column = i;
	                  break;
	                }
	              }

	              // Column 1 holds dropdowns
	              if (column == 0) {
	                // Create the dropdown and add data to it
	                final CCombo combo = new CCombo(table, SWT.READ_ONLY);
	                for (int i = 0, n = options.length; i < n; i++) {
	                  combo.add(options[i]);
	                }

	                // Select the previously selected item from the cell
	                combo.select(combo.indexOf(item.getText(column)));

	                // Compute the width for the editor
	                // Also, compute the column width, so that the dropdown fits
	                //editor.minimumWidth = combo.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
	                //if (editor.minimumWidth<150) table.getColumn(column).setWidth(150);
	                //else table.getColumn(column).setWidth(editor.minimumWidth);

	                // Set the focus on the dropdown and set into the editor
	                combo.setFocus();
	                editor.setEditor(combo, item, column);

	                // Add a listener to set the selected item back into the cell
	                final int col = column;
	                combo.addSelectionListener(new SelectionAdapter() {
	                  public void widgetSelected(SelectionEvent event) {
	                	  
	                	String selected = combo.getText();	                	
	                    item.setText(col, selected);

	                    formatLabel.setText(getFormat(selected));
	                    
	                    // They selected an item; end the editing session
	                    combo.dispose();
	                  }
	                });
	              } else if (column > 0) {
	                // Create the Text object for our editor
	                final Text text = new Text(table, SWT.NONE);
	                text.setForeground(item.getForeground());

	                // Transfer any text from the cell to the Text control,
	                // set the color to match this row, select the text,
	                // and set focus to the control
	                text.setText(item.getText(column));
	                text.setForeground(item.getForeground());
	                text.selectAll();
	                text.setFocus();

	                // Add a handler to detect key presses
	                final int col1 = column;
	                text.addKeyListener(new KeyAdapter() {
	                  public void keyPressed(KeyEvent event) {
	                    // End the editing and save the text if the user presses Enter
	                    // End the editing and throw away the text if the user presses Esc
	                	  switch (event.keyCode) {
	                      case SWT.CR:
	                    	  item.setText(col1, text.getText());
	                      case SWT.ESC:
	                        text.dispose();
	                        break;
	                      }

	                  }
	                });

	                
	                // Recalculate the minimum width for the editor
	                editor.minimumWidth = text.getBounds().width;

	                // Set the control into the editor
	                editor.setEditor(text, item, column);

	                // Add a handler to transfer the text back to the cell
	                // any time it's modified
	                final int col = column;
	                text.addModifyListener(new ModifyListener() {
	                  public void modifyText(ModifyEvent event) {
	                    // Set the text of the editor's control back into the cell
	                    item.setText(col, text.getText());
	                  }
	                });
	              }
	            }
	          }
	        });


		    setControl(topLevel);
		    setPageComplete(true);
		  }		
		
		//get format of an OMV property
		private String getFormat(String OMVproperty) {
			return OMVProperties.getFormat(OMVproperty);
		}
		
		
		//get OMV both datatype and object properties
		private String[] getOMVProperties()  {
			return OMVProperties.getOMVPropeties(); 
		}
		
}
