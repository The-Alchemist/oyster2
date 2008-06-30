package org.neontoolkit.oyster2.client.gui.dialogs;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.ColumnLayout;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import org.neontoolkit.oyster2.client.gui.dialogs.content.OrganizationSelectionComposite;
import org.neontoolkit.oyster2.client.gui.dialogs.content.PersonSelectionComposite;
import org.neontoolkit.oyster2.client.gui.dialogs.content.PersonSelectionComposite.Person;

public class PartyDialog extends ResizableInputDialog {

	
	public final static String NAME_COMBO_VALUES_KEY = "nameComboValues"; //$NON-NLS-1$
	
	public final static String LASTNAME_COMBO_VALUES_KEY = "lastnameComboValues"; //$NON-NLS-1$
	
	public final static String ORGANIZATION_COMBO_VALUES_KEY = "organizationComboValues"; //$NON-NLS-1$
	
	private Set<Person> people = new HashSet<Person>();
	
	/** Used to hold the names of the organizations between the creation
	 * of the dialog and the call to createDialogArea method
	 */
	private String[] organizations = null;
	
	
	
	private ScrolledForm form = null;
	
	/**
	 * Toolkit used to create the form
	 */
	private FormToolkit toolkit  = null;
	
	private PersonSelectionComposite personSelection = null;
	
	private OrganizationSelectionComposite organizationSelection = null;
	
	public PartyDialog(String section,Shell parentShell) {
		super(section,parentShell);
		
	}
	
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.getString("PartyDialog.shell.title")); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite baseComposite = (Composite) super.createDialogArea(parent);
		baseComposite.setLayout(new FillLayout());
		
		ColumnLayout layout = new ColumnLayout();
		layout.maxNumColumns = 1;
		layout.minNumColumns = 1;
		
		
		toolkit = new FormToolkit(baseComposite.getDisplay());
		
		form = toolkit.createScrolledForm(baseComposite);

		form.setBackground(parent.getBackground());
		//form.getBody().setBackground(parent.getBackground());
		form.setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		form.getBody().setLayout(layout);
		form.setText(Messages.getString("PartyDialog.form.text")); //$NON-NLS-1$
		
		
		makePeopleSection();
		makeOrganizationSelection();
				
		return baseComposite;
	}
	
	private Composite makeOrganizationSelection() {
		//TODO load combo values using resource loader
		
		Group group = new Group(form.getBody(), SWT.SHADOW_ETCHED_IN);
		group.setLayout(new FillLayout());
		group.setBackground(form.getBackground());
		Section section = 
			toolkit.createSection(group,Section.TWISTIE|SWT.WRAP);
		section.setText(Messages.getString("PartyDialog.organizations.label")); //$NON-NLS-1$
		section.setLayout(new FillLayout());
		section.setBackground(form.getBackground());
		section.setTitleBarBackground(form.getBackground());
		this.organizationSelection = new OrganizationSelectionComposite(section,SWT.NONE,
				this.organizations,
				getCustomSettingsSection()+"organizationSelectionComposite"); //$NON-NLS-1$
		section.setClient(organizationSelection);
		return group;
	}
	
	private Composite makePeopleSection() {
		//ResourceLoader rl = ResourceLoader.getResourceLoader();
		//values to be loaded and shown as predefined values in
		//the combo
		
		
		
		Group group = new Group(form.getBody(), SWT.SHADOW_ETCHED_IN);
		group.setBackground(form.getBackground());
		group.setLayout(new FillLayout());
		Section section = 
			toolkit.createSection(group,Section.TWISTIE|SWT.WRAP);
		section.setText(Messages.getString("PartyDialog.people.label")); //$NON-NLS-1$
		section.setBackground(form.getBackground());
		section.setTitleBarBackground(form.getBackground());
		section.setLayout(new FillLayout());
		this.personSelection = new PersonSelectionComposite(section,SWT.NONE,
				people,getCustomSettingsSection()+"personSelectionComposite"); //$NON-NLS-1$
		
		section.setClient(personSelection);
		return group;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		
		Map<Integer,PersonSelectionComposite.Person> newPeopleMap = personSelection.getPeople();
		
		people.clear();
		people.addAll(newPeopleMap.values());
		this.organizations = organizationSelection.getList().getItems();
		
		personSelection.save();
		organizationSelection.save();
		
		
		super.okPressed();
	}


	/**
	 * @return the organizations
	 */
	public final String[] getOrganizations() {
		return organizations;
	}


	/**
	 * @param organizations the organizations to set
	 */
	public final void setOrganizations(String[] organizations) {
		this.organizations = organizations;
	}

	public Set<Person> getPeople() {
		return people;
	}

	public void setPeople(Set<Person> people) {
		this.people = people;
	}


}
