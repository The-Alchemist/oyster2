package org.neontoolkit.oyster.plugin.menu;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.neontoolkit.registry.api.Oyster2Connection;

import com.ontoprise.ontostudio.gui.GuiPlugin;

public class storage extends PreferencePage implements IWorkbenchPreferencePage {

	public static final String SUPEROYSTER = "SUPEROYSTER";
	public static final String PUSHOYSTER = "PUSHOYSTER";
	private IPreferenceStore _store;
	private Text superOysterIP;
	private Text pushOysterIP;
	private Group loginGroup;
	Oyster2Connection oyster2Conn;
	
	
	
	public storage() {
		super();
        setPreferenceStore(GuiPlugin.getDefault().getPreferenceStore());
        _store = getPreferenceStore();
		// TODO Auto-generated constructor stub
	}

	public storage(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public storage(String title, ImageDescriptor image) {
		super(title, image);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Control createContents(Composite parent) {
		// TODO Auto-generated method stub
		
		int columns = 1;
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(columns, false);
        composite.setLayout(layout);
        GridLayout groupLayout = new GridLayout();
        groupLayout.numColumns = 2;
        
        loginGroup = new Group(composite, SWT.NONE);
        loginGroup.setText("Oyster storage location");
        loginGroup.setLayout(groupLayout);
        
        Label NodeIP = new Label(loginGroup, SWT.NONE);
        GridData textdata = new GridData();
        textdata.widthHint = 200;      
        NodeIP.setText("Super Node IP: ");  //$NON-NLS-1$
        superOysterIP = new Text(loginGroup, SWT.SINGLE | SWT.BORDER);
        superOysterIP.setLayoutData(textdata);  
        String oldUsername = _store.getString(SUPEROYSTER);
        if(oldUsername.length()>0){
        	superOysterIP.setText(oldUsername);
        }
        
        Label NodeIP1 = new Label(loginGroup, SWT.NONE);
        GridData textdata1 = new GridData();
        textdata1.widthHint = 200;      
        NodeIP1.setText("Push Node IP: ");  //$NON-NLS-1$
        pushOysterIP = new Text(loginGroup, SWT.SINGLE | SWT.BORDER);
        pushOysterIP.setLayoutData(textdata1);  
        String oldU = _store.getString(PUSHOYSTER);
        if(oldU.length()>0){
        	pushOysterIP.setText(oldU);
        }
        
		
		return composite;
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}

	protected void performApply() {      
		_store.setValue(SUPEROYSTER, superOysterIP.getText().trim());
		_store.setValue(PUSHOYSTER, pushOysterIP.getText().trim());
    }

    @Override
    public boolean performOk() {
        performApply();
        return true;
    }
    
    protected void performDefaults() {
    	superOysterIP.setText("");
    	pushOysterIP.setText("");
    	_store.setValue(SUPEROYSTER, "");
    	_store.setValue(PUSHOYSTER, "");
    }
}
