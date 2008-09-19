package org.neontoolkit.collab.preference;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.neontoolkit.omv.api.core.OMVPerson;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;
import org.neontoolkit.registry.api.Oyster2Connection;
import org.neontoolkit.registry.oyster2.Constants;

import com.ontoprise.ontostudio.gui.GuiPlugin;

public class CollabPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage, SelectionListener {
	
	public static final String ROLE = "ROLE"; 
	public static final String USER_FIRSTNAME = "USER_FIRSTNAME";
	public static final String USER_LASTNAME = "USER_LASTNAME";
	
	private IPreferenceStore _store;
    private Button loginButton, logoutButton, registerButton;
    private Text firstnameText, lastnameText, roleText, 
    newFirstnameText, newLastnameText, statusText;
    private Group loginGroup, registerGroup;
    private Combo rolesGroup ;
    
    Oyster2Connection oyster2Conn ;
    boolean startServer = false;
    
	public CollabPreferencePage() {		
		super();
		startServer = StartRegistry.getState();
        setPreferenceStore(GuiPlugin.getDefault().getPreferenceStore());
        _store = getPreferenceStore();
	}

	public CollabPreferencePage(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public CollabPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Control createContents(Composite parent) {
		if(!StartRegistry.getState()){
			MessageDialog
			.openInformation(
					this.getShell(),
					"Information",
					"Please start oyster server first!");
			return null;			
		}
		oyster2Conn = StartRegistry.connection;		

		int columns = 1;
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(columns, false);
        composite.setLayout(layout);
        GridLayout groupLayout = new GridLayout();
        groupLayout.numColumns = 2;
        
        loginGroup = new Group(composite, SWT.NONE);
        loginGroup.setText("Log in / out");
        loginGroup.setLayout(groupLayout);
        
        Label firstNameLabel = new Label(loginGroup, SWT.NONE);
        GridData textdata = new GridData();
        textdata.widthHint = 200;      
        firstNameLabel.setText("First name: ");  //$NON-NLS-1$
        firstnameText = new Text(loginGroup, SWT.SINGLE | SWT.BORDER);
        firstnameText.setLayoutData(textdata);  
        String oldUsername = _store.getString(USER_FIRSTNAME);
        if(oldUsername.length()>0){
        	firstnameText.setText(oldUsername);
        }
        
        Label lastNameLabel = new Label(loginGroup, SWT.NONE);
        lastNameLabel.setText("Last name: ");  //$NON-NLS-1$
        lastnameText = new Text(loginGroup, SWT.SINGLE | SWT.BORDER);
        lastnameText.setLayoutData(textdata);     
        oldUsername = _store.getString(USER_LASTNAME);
        if(oldUsername.length()>0){
        	lastnameText.setText(oldUsername);
        }
        
        Label roleLabel = new Label(loginGroup, SWT.NONE);
        roleLabel.setText("Role: ");  
        roleText = new Text(loginGroup, SWT.SINGLE | SWT.BORDER);
        roleText.setLayoutData(textdata);  
        String oldvalue = _store.getString(ROLE);
        roleText.setEditable(false);
        roleText.setText(oldvalue);
        if(oldvalue.equalsIgnoreCase("viewer")){
        	_store.setValue(ROLE, oldvalue);
        }
        
        loginButton = new Button(loginGroup, SWT.PUSH);
        loginButton.setText("Log in");
        loginButton.addSelectionListener(this);
        logoutButton = new Button(loginGroup, SWT.PUSH);
        logoutButton.setText("Log out");
        logoutButton.addSelectionListener(this);
        
        registerGroup = new Group(composite, SWT.NONE);
        registerGroup.setText("Register");
        registerGroup.setLayout(groupLayout);
                
        Label newFirstnameLabel = new Label(registerGroup, SWT.None);
        newFirstnameLabel.setText("First name");
        newFirstnameText = new Text(registerGroup, SWT.SINGLE | SWT.BORDER);
        newFirstnameText.setLayoutData(textdata);  
        newFirstnameText.setText(firstnameText.getText().trim());
        
        Label newLastnameLabel = new Label(registerGroup, SWT.None);
        newLastnameLabel.setText("Last name");
        newLastnameText = new Text(registerGroup, SWT.SINGLE | SWT.BORDER);
        newLastnameText.setLayoutData(textdata); 
        newLastnameText.setText(lastnameText.getText().trim());
        
        (new Label(registerGroup, SWT.NONE)).setText("Choose a role: ");
        rolesGroup = new Combo(registerGroup, SWT.SIMPLE | SWT.BORDER); 
        rolesGroup.add("Viewer");
        rolesGroup.add("Subject Expert");
        rolesGroup.add("Validator");
        rolesGroup.select(0);
       
        registerButton = new Button(registerGroup, SWT.PUSH);        
        registerButton.setText("Register");
        GridData gd = new GridData();
        gd.horizontalSpan = 2;
        registerButton.setLayoutData(gd);
        registerButton.addSelectionListener(this);
        
        Group statusGroup = new Group(composite, SWT.None);
        statusGroup.setLayout(groupLayout);        
        Label statusLabel = new Label(statusGroup, SWT.None);
        statusLabel.setText("Current status: ");
        statusText = new Text(statusGroup, SWT.SINGLE | SWT.BORDER);
        statusText.setEditable(false);
        statusText.setLayoutData(textdata);
        String firstname = _store.getString("USER_FIRSTNAME");
        String lastname = _store.getString("USER_LASTNAME");
        if(firstname.length()>0 && lastname.length()>0){
        	statusText.setText(firstname+" "+lastname+" ("+_store.getString("ROLE")+") logged in.");
        } else {
        	statusText.setText("No user logged in.");
        }
        
		return composite;
	}

	public void init(IWorkbench workbench) {

	}
	
    protected void performApply() {      
        
    }

    @Override
    public boolean performOk() {
        performApply();
        return true;
    }

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(loginButton)){
			String firstname = firstnameText.getText().trim();
			String lastname = lastnameText.getText().trim();
			if(firstname.length()==0 || lastname.length()==0){
				MessageDialog
				.openInformation(
						this.getShell(),
						"Information",
						"The fields of First name and Last name can not be empty!");
			} else {
				OMVPerson person = new OMVPerson();
				person.setFirstName(firstname);
				person.setLastName(lastname);
				OMVPerson samePerson = this.exists(person);
				if(samePerson != null){
					//This user has registered.
					String hasRole = samePerson.getHasRole();
					this.roleText.setText(hasRole);
					_store.setValue(USER_FIRSTNAME, firstname);
			        _store.setValue(USER_LASTNAME, lastname);
			        _store.setValue(ROLE, hasRole);
			        statusText.setText(firstname+" "+lastname+" ("+hasRole+") logged in.");
			        oyster2Conn.replace(samePerson);
				} else {
					//ask user to register
					boolean flag = MessageDialog.openConfirm(this.getShell(),
							"Information",	"Please register first!");
					if(flag){
						newFirstnameText.setText(firstname);
						newLastnameText.setText(lastname);
						rolesGroup.select(0);
					}
				}
			}			
			
		} else if(e.getSource().equals(logoutButton)){
			statusText.setText(_store.getString("USER_FIRSTNAME")+" "+
					_store.getString("USER_LASTNAME")+" ("+_store.getString("ROLE")+") logged out.");
			_store.setValue(USER_FIRSTNAME, "");
	        _store.setValue(USER_LASTNAME, "");
	        _store.setValue(ROLE, "Viewer");
	        firstnameText.setText("");
	        lastnameText.setText("");
	        roleText.setText("");
			
		} else if(e.getSource().equals(registerButton)){
			String firstname = newFirstnameText.getText().trim();
			String lastname = newLastnameText.getText().trim();
			if(firstname.length()==0 || lastname.length()==0){
				MessageDialog
				.openInformation(
						this.getShell(),
						"Information",
						"The fields of first name and last name can not be empty!");
			} else {
				OMVPerson person = new OMVPerson();
				person.setFirstName(firstname);
				person.setLastName(lastname);
				
				OMVPerson samePerson = this.exists(person);
				if(samePerson != null){
					//This user has registered.
					MessageDialog
					.openInformation(
							this.getShell(),
							"Information",
							"The user name has been used by another user. Please input different first name or surname!");
					
				} else {
					//register new user					  
					String hasRole = rolesGroup.getItem(rolesGroup.getSelectionIndex());
										
					this.setRole(person, hasRole);
					
					_store.setValue(USER_FIRSTNAME, firstname);
			        _store.setValue(USER_LASTNAME, lastname);
			        _store.setValue(ROLE, this.getRole(hasRole));
					
			        this.firstnameText.setText(firstname);
			        this.lastnameText.setText(lastname);
			        this.roleText.setText(person.getHasRole());
			        this.newFirstnameText.setText("");
			        this.newLastnameText.setText("");
			        this.rolesGroup.select(0);
			        oyster2Conn.replace(person);
			        statusText.setText(firstname+" "+lastname+" ("+person.getHasRole()+") logged in.");
			        System.out.println("Current user is: "+person.getFirstName()+" ("+person.getHasRole()+")");
			        			        
			        MessageDialog
					.openInformation(
							this.getShell(),
							"Information",
							"You have registered successfully and logged in!");
					
				}
			}	
		}
		
	}
	
	private void setRole(OMVPerson person, String role){
		String roleStr = this.getRole(role);
		if(roleStr!=null){
			person.setHasRole(roleStr);
		}
	}
	
	private String getRole(String role){
		String roleStr = null;
		if(role.equalsIgnoreCase("Subject Expert")){
			roleStr = Constants.SubjectExpert;
		} else if(role.equalsIgnoreCase("Viewer")){
			roleStr = Constants.Viewer;
		} else if(role.equalsIgnoreCase("Validator")){
			roleStr = Constants.Validator;
		}
		return roleStr;
	}
	
	private OMVPerson exists(OMVPerson person){
		  Set<Object> OMVSetPerson = oyster2Conn.submitAdHocQuery("SELECT ?x WHERE  { ?x rdf:type <http://omv.ontoware.org/2005/05/ontology#Person>  }");
		  Iterator<Object> itPerson = OMVSetPerson.iterator();
		  try{
			  while (itPerson.hasNext()) {
				OMVPerson omv = (OMVPerson) itPerson.next();
				if(omv.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
						omv.getLastName().equalsIgnoreCase(person.getLastName())){
					return omv;
				}
			  }
		  }catch(Exception ignore){
		   // -- ignore
		  }
		  return null;
	}

}
