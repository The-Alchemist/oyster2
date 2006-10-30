package ui.editor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.jface.dialogs.*;

import oyster2.*;


public class ImportMappingEditor extends Dialog{
	private static int TABLE_INSET_HORIZONTAL = 2;
	private static int TABLE_INSET_VERTICAL = 2;
	protected OntologyProperty property;
	private List properties;
	private Color color1;
	private Label warning;
	private Button editPropertyButton;
	private Table table;
	private LinkedList propList = new LinkedList();
	private EditableSelectionAdapter editableSelectionAdapterOrganization =	new EditableSelectionAdapter(1);

	public ImportMappingEditor(Shell parent){
		super(parent);
	}public ImportMappingEditor(Shell shell, List prop) {
		super(shell);
		properties = new LinkedList();
		setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
		properties=prop;
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		composite.setLayout(layout);
		Control pageContainer = createEditorContent(composite);
		pageContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		pageContainer.setFont(parent.getFont());
		this.getShell().setText("Mapping editor");
		return composite;
	}
	
	protected Control createEditorContent(Composite c){
		Composite pageContainer = new Composite(c, SWT.NULL);
		Composite tablePanel = createTablePanel(pageContainer);
		Composite buttonPanel = createButtonPanel(pageContainer);
		Composite labelPanel=createLabelText(pageContainer);
		
		//YO AGREGUE AQUI
		
		FormLayout layout = new FormLayout();
		layout.marginWidth = 3;
		layout.marginHeight = 3;
		
		FormData data0 = new FormData();
		data0.top = new FormAttachment(0,0);
		data0.left = new FormAttachment(0, 0);
		data0.right = new FormAttachment(100,-60);
		data0.bottom = new FormAttachment(15,0);
		labelPanel.setLayoutData(data0);

		FormData data1 = new FormData();
		data1.top = new FormAttachment(labelPanel);
		data1.left = new FormAttachment(0, 0);
		data1.right = new FormAttachment(100,-60);
		data1.bottom = new FormAttachment(100,0);
		tablePanel.setLayoutData(data1);

		FormData data2 = new FormData();
		data2.top = new FormAttachment(0, 30);
		data2.left = new FormAttachment(tablePanel);
		data1.bottom = new FormAttachment(100,0);
		buttonPanel.setLayoutData(data2);
		
		
		pageContainer.setLayout(layout);
		return pageContainer;
	}
private Composite createLabelText(Composite parent) {
		
		Composite labelPanel = new Composite(parent, SWT.NULL);
		GridLayout gl = new GridLayout();
		gl.numColumns = 1;
		labelPanel.setLayout(gl);
		
		Label label = new Label(labelPanel, SWT.NONE);
	    label.setText("Please Complete the Mapping Metadata ");    
	    label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	    color1=new Color (Display.getCurrent(), new RGB (0,0,200));
		warning = new Label(labelPanel, SWT.NONE);
		warning.setText("mappingSource, mappingTarget and mappingProvider are mandatory! ");
		warning.setForeground(color1);
		warning.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		warning.setVisible(true);
		labelPanel.setLayout(gl);
	
		return labelPanel;
		
	}
	private Composite createButtonPanel(Composite parent) {
		Composite buttonPanel = new Composite(parent, SWT.NULL);
		GridLayout gl = new GridLayout();
		gl.numColumns = 1;
		buttonPanel.setLayout(gl);
		
		editPropertyButton = new Button(buttonPanel, SWT.PUSH | SWT.FLAT);
		editPropertyButton.setText("Edit");
		editPropertyButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		editPropertyButton.setEnabled(false);
		editPropertyButton.setVisible(false);
		editPropertyButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//editProperty();
			}
		});
		return buttonPanel;
		
	}

	
	private Composite createTablePanel(Composite parent) {
		Composite tablePanel = new Composite(parent, SWT.NULL);
		tablePanel.setLayout(new FillLayout());
		table = new Table(tablePanel, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn column1 = new TableColumn(table, SWT.NONE);
		column1.setText("Property");
		column1.setWidth(105);
		TableColumn column2 = new TableColumn(table, SWT.NONE);
		column2.setText("Value");
		column2.setWidth(210);
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				switch (event.type) {
					case SWT.Traverse :
						handleTraverse(event);
						break;
					case SWT.Selection :
					case SWT.DefaultSelection :
						editPropertyButton.setEnabled(true);
						break;
				}
			}
		};
		table.addListener(SWT.Traverse, listener);
		table.addListener(SWT.Selection, listener);
		table.addListener(SWT.DefaultSelection, listener);
		table.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.CR) {
					//editAuthor();
					EditableSelectionAdapter a= new EditableSelectionAdapter(1);
				}
			}
		});
//		ADDED TO INSIDE EDITING
		table.addSelectionListener(editableSelectionAdapterOrganization);
		//TO HERE
		initializeTable();
		
		return tablePanel;
	}
	private void initializeTable(){
		Iterator it = properties.iterator();
		try{
		while(it.hasNext()){
			TableItem item = new TableItem(table, SWT.NONE);;
			OntologyProperty op = (OntologyProperty)it.next();
			/*
			if ((op.getPropertyName().equalsIgnoreCase(Constants.MappingSource)||(op.getPropertyName().equalsIgnoreCase(Constants.MappingProvider)))){
				Color color1=new Color (Display.getCurrent(), new RGB (0,0,200));
				item.setForeground(color1);
			}
			*/
			item.setText(0, op.getPropertyName());
			item.setText(1, op.getPropertyValue());
		}
		}catch(Exception ignore){
			//-- ignore
		}
		
	}
	/*private void editProperty() {
		final TableItem[] selected = table.getSelection();
		if (selected.length <= 0) return;
		TableItem item = selected[0];
		int index = item.getParent().indexOf(item);
		OntologyProperty prop = (OntologyProperty) properties.get(index);
		//item.setText(1, text.getText());
	}*/
	public void setProperty(OntologyProperty p) {
		this.property = p;
	}

	public OntologyProperty getProperty() {
		return property;
	}
	private void handleTraverse(Event event) {
		switch (event.detail) {
			case SWT.TRAVERSE_ARROW_NEXT :
			case SWT.TRAVERSE_ARROW_PREVIOUS :
			case SWT.TRAVERSE_RETURN :
			case SWT.TRAVERSE_ESCAPE :
				event.doit = false;
				break;
			default :
				event.doit = true;
				break;
		}
	}
	class EditableSelectionAdapter extends SelectionAdapter {

		private int column;
		
		
		EditableSelectionAdapter(int column) {
			this.column = column;

		}

		class EditableFocusListener extends FocusAdapter {

			private Text text;
			private TableItem itemToUpdate;

			EditableFocusListener(Text text, TableItem itemToUpdate) {
				this.text = text;
				this.itemToUpdate = itemToUpdate;

			}
			public void focusLost(FocusEvent e) {
				updateMapElement();
			}
				
			private void updateMapElement() {
				try {		
					itemToUpdate.setText(column, text.getText());
					//notifyTableItemChange(itemToUpdate, column);
	
					////TO MODIFY THE VALUE TO BE SAVED
					int index = itemToUpdate.getParent().indexOf(itemToUpdate);
					OntologyProperty prop = (OntologyProperty) properties.get(index);
					if(text.getText().length() > 0){
						properties.remove(index);
						String value = text.getText().trim();
						prop.setPropertyValue(value);
						properties.add(index, prop);
						itemToUpdate.setText(1, value);
					}
					text.dispose();
					text = null;
				} catch (Exception e1) {
					MessageBox mb =
						new MessageBox(getShell(), SWT.ICON_ERROR | SWT.OK);
					mb.setMessage(
						"It is not possible to create a new object based on the given string ("
							+ e1.getCause().getMessage()
							+ ")");
				}

			}
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			int selected = table.getSelectionIndex();
			String currentValue = table.getItem(selected).getText(1);
			int x = TABLE_INSET_HORIZONTAL + table.getColumn(0).getWidth();
			int y =
				TABLE_INSET_VERTICAL
					+ table.getHeaderHeight()
					+ (selected - table.getTopIndex()) * table.getItemHeight();
			int width = table.getColumn(1).getWidth();
			int height = table.getItemHeight();

			Text text = new Text(table, SWT.SINGLE | SWT.LEFT);
			text.setBounds(x, y, width, height);
			text.setText(currentValue);
			text.setSelection(0, currentValue.length());
			text.moveAbove(null);

			text.addFocusListener(
				new EditableFocusListener(text, table.getItem(selected)));

			text.forceFocus();
		}
		
	}
	protected void okPressed() {
		System.out.println("ok pressed!");
		boolean successful = updateProperty();
		if(successful)
		close();
		
	}
	public  boolean updateProperty(){
		TableItem[] updateProp= table.getItems();
		OntologyProperty prop;
		for(int i=0;i<updateProp.length;i++){
			if((updateProp[i].getText(1)!=null)&&(updateProp[i].getText(1).length()>0)){
				prop = new OntologyProperty(updateProp[i].getText(0),updateProp[i].getText(1));
				propList.add(prop);
			}
			else {
				errorDialog("update error","property field should be completed!");
				propList.clear();
				return false;
			}
		}
		return true;
	}
	private void errorDialog(final String title, final String message) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(Display.getDefault().getActiveShell(), title, message);
			}
		});
	}
	public LinkedList getPropertyList(){
		return this.propList;
	}

}
