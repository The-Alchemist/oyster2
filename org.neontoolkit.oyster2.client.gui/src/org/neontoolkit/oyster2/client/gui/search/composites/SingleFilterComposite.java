/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.search.composites;



import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.neontoolkit.oyster2.client.gui.search.Filter;
import org.neontoolkit.oyster2.client.gui.search.FilterValue;


/**
 * @author David Muñoz
 * dryadcito at yahoo dot es
 *
 */
public class SingleFilterComposite extends FilterComposite {

	protected Combo valueCombo = null;
	
	protected Combo comparatorCombo = null;
	
	//protected Button negateButton = null;
	
	protected static final String ITEMS_KEY = "items";
	
	protected boolean editable = true;
	
	protected final static int numberOfSavedValues = 20;
	
	protected Filter filter = null;

	/**
	 * @param parent
	 * @param style
	 * @param section
	 * @param predefined
	 */
	public SingleFilterComposite(Composite parent, int style,
			String section, String[] predefined,String [] comparators,
			Filter filter) {
		super(parent, style, section, predefined);
		this.filter = filter; 
		makeValuesCombo(predefined);
		//makeNegateButton();
		makeComparatorCombo(comparators);
		loadSettings();
		makeLayout();
		
	}
	
	
	
	protected void makeLayout() {
		
		GridData gd = null;
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;
		layout.numColumns = 2;
		setLayout(layout);
		
		
		
		//negateButton.setLayoutData();
		
		gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		valueCombo.setLayoutData(gd);
		
		gd = new GridData();
		gd.widthHint = getWidthForComparator(comparatorCombo);
		comparatorCombo.setLayoutData(gd);
		
	}
	
	public void loadSettings() {
		String []items = dialogSettings.getArray(ITEMS_KEY);
		if (items != null)
			valueCombo.setItems(items);
	}

	@Override
	public void saveSettings() {
		
		// add the text if not already there. That text is the only element
		// that may be added.
		if (getSaveSettingsOnExit()) {
			String []itemsToSave = null;
			if ((! valueCombo.getText().trim().equals("")) &&
					(! repeated(valueCombo.getText(),valueCombo.getItems()))) {
				int i = 0;
				int numItems = valueCombo.getItemCount();
				
				if(numItems < numberOfSavedValues) {
					numItems++;
				}
				itemsToSave = new String[numItems];
				itemsToSave[0] = valueCombo.getText();
						
				//add new text and delete oldest
				for(i = 1; i<itemsToSave.length;i++ ) {
					itemsToSave[i] = valueCombo.getItems()[i-1];
				}
				dialogSettings.put(ITEMS_KEY,itemsToSave);
			}
		}
	}
	

	@Override
	public Filter getFilterData() {
		if (valueCombo.getText().trim().equals(""))
			return null;
		filter.getFilterValues().clear();
		String value = comparatorCombo.getItem(comparatorCombo.getSelectionIndex());
		
		FilterValue filterValue = new FilterValue();
		filterValue = new FilterValue();
		filterValue.setComparator(value);
		filterValue.setValue(valueCombo.getText());
		filterValue.setNegate(false);
		//filterData.setNegate(negateButton.getSelection());
		filter.getFilterValues().add(filterValue);
		return filter;
	}
	
	protected boolean repeated(String text,String [] set) {
		boolean found = false;
		int i = 0;
		while ((! found) && (i<set.length)) {
			if (set[i].equals(text))
				return true;
			i++;
		}
		return found;
	}
	
	
	/**
	 * Creates the combo that will serve as value for the filter.
	 * @param predefined
	 */
	private void makeValuesCombo(String[] predefined) {
		/*
		 * We allow writing on every filter combo by default
		if (predefined != null) { 
			if (predefined.length == 0) {
				setSaveSettingsOnExit(true);
			}
			else {
				setSaveSettingsOnExit(false);
			}
		}
		else {
			setSaveSettingsOnExit(true);
		}
		*/
		setSaveSettingsOnExit(true);
		String []items = null;
		
		if (predefined == null) {
			items = dialogSettings.getArray(ITEMS_KEY);
		}
		else if (predefined.length == 0) {
			items = dialogSettings.getArray(ITEMS_KEY);
		}
		else {
			items = predefined;
		}
		
	 
		valueCombo = new Combo(this,SWT.DROP_DOWN);
		if (items != null) {
			valueCombo.setItems(items);
		}
		
	}

	
	private void makeComparatorCombo(String [] values) {
		if (comparatorCombo == null) {
			comparatorCombo = new Combo(this,SWT.READ_ONLY | SWT.DROP_DOWN);
		}
		
		comparatorCombo.setItems(values);
		comparatorCombo.select(0);
		
		
	}
	
	
	
	
	/*private Control makeNegateButton() {
		
		negateButton = new Button(this,SWT.CHECK);
		negateButton.setSelection(false);
		negateButton.setText("negate");
		
		
		return negateButton;
	}*/
	
	
}
