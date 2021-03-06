/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs.content;



import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.neontoolkit.oyster2.client.gui.adapter.submit.PartyMembers;
import org.neontoolkit.oyster2.client.gui.dialogs.InputDialog;
import org.neontoolkit.oyster2.client.gui.dialogs.PartyDialog;
import org.neontoolkit.oyster2.client.gui.dialogs.content.PersonSelectionComposite.Person;

/**
 * Adds a nested class to hold the two sets of party members
 * 
 * @author David Mu�oz
 *
 */
public class PartyComposite extends InputComposite {
	private List list = null;
	
	private Button editButton = null;
	
	private InputDialog nestedDialog = null;
	
	private PartyMembers party = new PartyMembers();
	
	public PartyComposite(Composite parent, int style,
			String section) {
		super(parent,style,section,null);
		//TODO use the section to store data
		FormData formData = null;
		this.setLayout(new FormLayout());
		
		//buttons

		editButton = new Button(this, SWT.PUSH);
		editButton.setText(Messages.getString("PartyComposite.editbutton.text")); //$NON-NLS-1$
		formData = new FormData();
		formData.right = new FormAttachment(100,-5);
		formData.width = buttonWidth;  
		formData.top = new FormAttachment(0,5);
		editButton.setLayoutData(formData);
		
		
		list = new List(this, SWT.MULTI|SWT.BORDER|SWT.H_SCROLL| SWT.V_SCROLL);
		formData = new FormData();
		formData.top = new FormAttachment(0,5);
		formData.right = new FormAttachment(editButton,-5);
		formData.left = new FormAttachment(0,5);
		list.setLayoutData(formData);
		
		setInput(party);
		
		makeListeners();
	}

	
	private void makeListeners() {
		Listener listener = null;
		listener = new Listener() {
			public void handleEvent(Event event) {
				if (event.widget == editButton) {
					int result,i;
					try {
					PartyDialog dialog = (PartyDialog)nestedDialog;
					dialog.setPeople(party.getPeople());
					dialog.setOrganizations(party.getOrganizations());
					result = nestedDialog.open();
					
					if (result == TrayDialog.OK) {
						list.removeAll();
						
						party.setPeople(dialog.getPeople());
						
						String []organizations = dialog.getOrganizations();
						party.setOrganizations(organizations);
						for (i = 0;i<organizations.length;i++) {
							list.add(organizations[i]);
							
						}
						Person []peopleArray = new Person[party.getPeople().size()];
						peopleArray = party.getPeople().toArray(peopleArray);
						for (i = 0;i<peopleArray.length;i++) {
							list.add(peopleArray[i].getName() + " " + peopleArray[i].getLastname()); //$NON-NLS-1$
						}
						
					}
					}catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		};
		editButton.addListener(SWT.Selection, listener);
		
	}
	
	public InputDialog getNestedDialog() {
		return nestedDialog;
	}


	public void setNestedDialog(InputDialog nestedDialog) {
		this.nestedDialog = nestedDialog;
	}


	public PartyMembers getParty() {
		return party;
	}


	public void setParty(PartyMembers party) {
		this.party = party;
	}


	@Override
	public boolean testFilled() {
		if (isRequired()) {
			return (party.getOrganizations().length != 0) || (party.getPeople().size() != 0);
		}
		else return true;
	}


	@Override
	public void setInitialValue(Object value) {
		// TODO Auto-generated method stub
		return ;
	}
	
}
