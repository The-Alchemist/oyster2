/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs.content;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.neontoolkit.oyster2.client.gui.Activator;

/**
 * Intended to let a user edit a list of people.
 * Features two combo editable fields with add button
 * and a List with remove button.
 *  
 * @author David Muñoz
 *
 */
public class PersonSelectionComposite extends Composite {

	private static final String NAME_COMBO_ITEMS_KEY ="nameComboItems"; //$NON-NLS-1$
	
	private static final String LASTNAME_COMBO_ITEMS_KEY ="lastnameComboItems"; //$NON-NLS-1$
	
	private static final int numberOfSavedValues = 20;
	
	private int labelWidth = 75;
	
	private Combo nameCombo = null;
	
	private Combo lastnameCombo = null;
	
	private List list = null;
	
	private Button addButton = null;
	
	private Button removeButton = null;
	
	private Map<Integer,Person> people = new HashMap<Integer,Person>();

	private Set<String> nameComboItems = new HashSet<String>();
	
	private Set<String> lastnameComboItems = new HashSet<String>();
	
	private IDialogSettings dialogSettings = null;
	
	public class Person {
		private String name;
		
		private String lastname;

		public String getLastname() {
			return lastname;
		}

		public void setLastname(String lastname) {
			this.lastname = lastname;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (! (obj.getClass().equals(Person.class)))
				return false;
			Person otherPerson = (Person)obj;
			return otherPerson.name.equals(this.name) &&
				otherPerson.lastname.equals(this.lastname);
		}
	}
	
	public PersonSelectionComposite(Composite parent, int style,
			Set<Person> people, String sectionName) {
		super(parent,style);
		setLayout(new FormLayout());
		FormData fd = null;
		
		dialogSettings = Activator.getDefault().getDialogSettings().getSection(sectionName);
		if (dialogSettings == null) {
			Activator.getDefault().getDialogSettings().addNewSection(sectionName);
			dialogSettings = Activator.getDefault().getDialogSettings().getSection(sectionName);
		}
		String [] nameItems = dialogSettings.getArray(NAME_COMBO_ITEMS_KEY);
		String [] lastnameItems = dialogSettings.getArray(LASTNAME_COMBO_ITEMS_KEY);
		
		Composite textInputComposite = createComboComposite(nameItems,
				lastnameItems);
		addButton = new Button(this,SWT.PUSH);
		addButton.setText(Messages.getString("PersonSelectionComposite.addbutton.text")); //$NON-NLS-1$
		fd = new FormData();
		fd.right = new FormAttachment(100,-5);
		fd.top = new FormAttachment(0,5);
		fd.width = CompositeConstants.BUTTON_WIDTH;
		addButton.setLayoutData(fd);
		
		fd = new FormData();
		fd.top = new FormAttachment(0,5);
		fd.right = new FormAttachment(addButton,-5);
		fd.left = new FormAttachment(0,5);
		textInputComposite.setLayoutData(fd);
				
		removeButton = new Button(this,SWT.PUSH);
		removeButton.setText(Messages.getString("PersonSelectionComposite.removebutton.text")); //$NON-NLS-1$
		fd = new FormData();
		fd.left = new FormAttachment(0,5);
		fd.bottom = new FormAttachment(100,-5);
		fd.width = CompositeConstants.BUTTON_WIDTH;
		removeButton.setLayoutData(fd);
		
		fillPeopleMap(people);
		makeList(textInputComposite);
		updateList();
		makeListeners();
	}
	
	private void fillPeopleMap(Set<Person> people) {
		int i = 0;
		for (Person p : people) {
			this.people.put(Integer.valueOf(i), p);
			i++;
		}
	}
	
	public void save() {
		Set<String> itemsToSave = new HashSet<String>();
		String [] itemsArray = null;
		int i = 0;
		String []comboItems = nameCombo.getItems();
		
		for(i = comboItems.length-1;i>=0;i--) {
			itemsToSave.add(comboItems[i]);
			if (itemsToSave.size() == numberOfSavedValues)
				break;
		};
		itemsArray = new String[itemsToSave.size()];
		itemsArray = itemsToSave.toArray(itemsArray);
		
		dialogSettings.put(NAME_COMBO_ITEMS_KEY,itemsArray);
		
		
		comboItems = lastnameCombo.getItems();
		itemsToSave.clear();
		for(i = comboItems.length-1;i>=0;i--) {
			itemsToSave.add(comboItems[i]);
			if (itemsToSave.size() == numberOfSavedValues)
				break;
		};
		itemsArray = new String[itemsToSave.size()];
		itemsArray = itemsToSave.toArray(itemsArray);
		dialogSettings.put(LASTNAME_COMBO_ITEMS_KEY,itemsArray);
		
		
		
	}
	
	private void makeList(Composite textInputComposite) {
		Label label = null;
		FormData fd = null;
		
		label = new Label(this,SWT.NONE);
		label.setText(Messages.getString("PersonSelectionComposite.people.present.label")); //$NON-NLS-1$
		fd = new FormData();
		fd.top = new FormAttachment(textInputComposite,5);
		fd.left = new FormAttachment(0,5);
		label.setLayoutData(fd);
		
		list = new List(this,SWT.MULTI|SWT.H_SCROLL| SWT.V_SCROLL|SWT.BORDER);
		
		fd = new FormData();
		fd.bottom = new FormAttachment(removeButton,-5);
		fd.top = new FormAttachment(label,5);
		fd.left = new FormAttachment(0,5);
		fd.right = new FormAttachment(100,-5);
		list.setLayoutData(fd);
		
	}
	
	
	private Composite createComboComposite(String [] nameItems,
			String []lastnameItems) {

		Composite newInputComposite = new Composite(this,SWT.NONE);
		newInputComposite.setLayout(new FormLayout());
		Label nameLabel = null;
		Label lastnameLabel = null;
		FormData fd = null;
		
		nameLabel = new Label(newInputComposite,SWT.NONE);
		nameLabel.setText(Messages.getString("PersonSelectionComposite.name.label")); //$NON-NLS-1$
		fd = new FormData();
		fd.left = new FormAttachment(0,5);
		fd.top = new FormAttachment(0,5);
		fd.width = labelWidth;
		nameLabel.setLayoutData(fd);
		
		if (nameItems != null) {
		System.out.println("Name items class " + nameItems.getClass()); //$NON-NLS-1$
		System.out.println("Name items values;: " ); //$NON-NLS-1$
		for (int i = 0;i<nameItems.length;i++)
			System.out.println(nameItems[i]);
		}
		
		nameCombo = new Combo(newInputComposite,SWT.DROP_DOWN);
		if (nameItems != null) {
			nameCombo.setItems(nameItems);
			nameCombo.setItems(nameItems);
			for (int i = 0;i<nameItems.length;i++) {
				nameComboItems.add(nameItems[i]);
			}	
		}
		fd = new FormData();
		fd.right = new FormAttachment(100,-5);
		fd.top = new FormAttachment(0,5);
		fd.left = new FormAttachment(nameLabel,5);
		nameCombo.setLayoutData(fd);
		
		
		lastnameCombo = new Combo(newInputComposite,SWT.DROP_DOWN);
		if (lastnameItems != null) {
			lastnameCombo.setItems(lastnameItems);
			for (int i = 0;i<lastnameItems.length;i++) {
				lastnameComboItems.add(lastnameItems[i]);
			}
		}
		lastnameLabel = new Label(newInputComposite,SWT.NONE);
		lastnameLabel.setText(Messages.getString("PersonSelectionComposite.lastname.label")); //$NON-NLS-1$
		fd = new FormData();
		fd.width = labelWidth;
		fd.bottom = new FormAttachment(100,-5);
		fd.left = new FormAttachment(0,5);
		lastnameLabel.setLayoutData(fd);
		
		fd = new FormData();
		fd.right = new FormAttachment(100,-5);
		fd.bottom = new FormAttachment(100,-5);
		fd.top = new FormAttachment(nameCombo,5);
		fd.left = new FormAttachment(lastnameLabel,5);
		lastnameCombo.setLayoutData(fd);
		return newInputComposite;
	}
	
	
	
	private void makeListeners() {
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				if (event.widget == addButton) {
					String nameText = nameCombo.getText().trim();
					String lastnameText = lastnameCombo.getText().trim();
					if ((! nameText.equals("")) && (!lastnameText.equals(""))) { //$NON-NLS-1$ //$NON-NLS-2$
					String fullName = nameText + " " +lastnameText; //$NON-NLS-1$
					
					Person person = new Person();
					person.setName(nameText);
					person.setLastname(lastnameText);
					
					if (! people.containsValue(person)) {
						int newIndex = list.getItemCount();
						people.put(Integer.valueOf(newIndex),person);
						list.add(fullName);
						if (! nameComboItems.contains(nameText)) {
							nameCombo.add(nameText);
							nameComboItems.add(nameText);
						}
						if (! lastnameComboItems.contains(lastnameText)) {
							lastnameCombo.add(lastnameText);
							lastnameComboItems.add(lastnameText);
						}
					}
					}
				}
				else if (event.widget == removeButton ) {
					int []selected = list.getSelectionIndices();
					
					removePeople(selected);
					updateList();
				}
			}
		};
		addButton.addListener(SWT.Selection, listener);
		removeButton.addListener(SWT.Selection, listener);
	}

	
	private void removePeople(int []selected) {
		Set<Person> values = null;//people.values();
		
		int i ;
		for (i= 0;i<selected.length;i++) {
			people.remove(Integer.valueOf(selected[i]));
			
		}
		//reorder
		values = new HashSet<Person>();
		values.addAll(people.values());
		
		Iterator<Person> valuesIterator = values.iterator();
		i = 0;
		people.clear();
		
		
		while(valuesIterator.hasNext()) {
			
			people.put(Integer.valueOf(i),valuesIterator.next());
			i++;
		}
		
		
	}
	
	
	private void updateList() {
		list.removeAll();
		Set<Map.Entry<Integer, Person>> entries = people.entrySet();
		Iterator<Map.Entry<Integer, Person>> it = entries.iterator();
		Map.Entry<Integer, Person> entry = null;
		
		while (it.hasNext()) {
			entry = it.next();
			
			list.add(entry.getValue().getName() + " " + entry.getValue().getLastname(), //$NON-NLS-1$
					entry.getKey().intValue());
		}
	}
	
	/**
	 * @return the list
	 */
	public final List getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public final void setList(List list) {
		this.list = list;
	}

	/**
	 * @return the addButton
	 */
	public final Button getAddButton() {
		return addButton;
	}

	/**
	 * @param addButton the addButton to set
	 */
	public final void setAddButton(Button addButton) {
		this.addButton = addButton;
	}

	/**
	 * @return the lastnameCombo
	 */
	public final Combo getLastnameCombo() {
		return lastnameCombo;
	}

	/**
	 * @param lastnameCombo the lastnameCombo to set
	 */
	public final void setLastnameCombo(Combo lastnameCombo) {
		this.lastnameCombo = lastnameCombo;
	}

	/**
	 * @return the nameCombo
	 */
	public final Combo getNameCombo() {
		return nameCombo;
	}

	/**
	 * @param nameCombo the nameCombo to set
	 */
	public final void setNameCombo(Combo nameCombo) {
		this.nameCombo = nameCombo;
	}

	/**
	 * @return the removeButton
	 */
	public final Button getRemoveButton() {
		return removeButton;
	}

	/**
	 * @param removeButton the removeButton to set
	 */
	public final void setRemoveButton(Button removeButton) {
		this.removeButton = removeButton;
	}

	public Map<Integer, Person> getPeople() {
		return people;
	}

	public void setPeople(Map<Integer, Person> people) {
		this.people = people;
	}
	
	
}
