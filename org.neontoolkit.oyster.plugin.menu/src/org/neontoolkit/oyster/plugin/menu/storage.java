package org.neontoolkit.oyster.plugin.menu;

import java.io.File;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;
import org.neontoolkit.registry.api.Oyster2Connection;

import com.ontoprise.ontostudio.gui.GuiPlugin;

public class storage extends PreferencePage implements IWorkbenchPreferencePage {

	public static final String SUPEROYSTER = "SUPEROYSTER";
	public static final String PUSHOYSTER = "PUSHOYSTER";
	public static final String READLOCALLY = "READLOCALLY";
	private IPreferenceStore _store;
	private Text superOysterIP;
	private Text pushOysterIP;
	private Group loginGroup;
	Oyster2Connection oyster2Conn;
	BooleanFieldEditor readLocally;
	private Boolean isReadLocally;
	private Button reset;
	static Shell shell=null;
	
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
		
		shell=this.getShell();
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
        
        readLocally = new BooleanFieldEditor(READLOCALLY, "Read ontologies locally",
				BooleanFieldEditor.SEPARATE_LABEL, loginGroup);
        readLocally.setPreferenceStore(_store);
        readLocally.load();
        isReadLocally=_store.getBoolean(READLOCALLY);
        readLocally.setPropertyChangeListener(new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				isReadLocally=((Boolean) event.getNewValue()).booleanValue();
			}
		});
		
        reset = new Button(loginGroup, SWT.NONE);
        reset.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (StartRegistry.connection==null && StartRegistry.serverProcess==null){
					try{
						File tFile = new File("O2serverfiles/localRegistry.owl"); //NOT NECESSARY ANYMORE BUT JUST FOR PREV VERSIONS...
						if (tFile.exists())	tFile.delete();
						tFile = new File("server/localRegistry.owl");
						if (tFile.exists())	tFile.delete();
						tFile = new File(".propertiesOyster");
						if (tFile.exists())	tFile.delete();
					}catch(Exception ex){
						MessageDialog.openInformation(
		           				shell,
		           				"Menu Plug-in",
		           				"Reset failed...");
					}
					finally{
						MessageDialog.openInformation(
		           				shell,
		           				"Menu Plug-in",
		           				"Oyster has been reset (Its recommended to restart the NTK)...");
					}
				}else if ((StartRegistry.connection!=null && StartRegistry.serverProcess==null) || 
						(StartRegistry.connection==null && StartRegistry.serverProcess!=null)){
					MessageDialog.openInformation(
	           				shell,
	           				"Menu Plug-in",
	           				"Cannot reset Oyster. Please restart the NTK and reset again.");
				}
				else{ 
					MessageDialog.openInformation(
	           				shell,
	           				"Menu Plug-in",
	           				"Cannot reset Oyster. Please first shutdown Oyster...");
				}
			}
		});
		final GridData gd_button = new GridData(SWT.FILL, SWT.CENTER, false, false);
		gd_button.widthHint = 80;
		reset.setLayoutData(gd_button);
		reset.setText("Reset Oyster");
        
		return composite;
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}

	protected void performApply() {      
		_store.setValue(SUPEROYSTER, superOysterIP.getText().trim());
		_store.setValue(PUSHOYSTER, pushOysterIP.getText().trim());
		_store.setValue(READLOCALLY, isReadLocally);
    }

    @Override
    public boolean performOk() {
        performApply();
        return true;
    }
    
    protected void performDefaults() {
    	_store.setValue(SUPEROYSTER, "");
    	_store.setValue(PUSHOYSTER, "");
    	_store.setValue(READLOCALLY, false);
    	superOysterIP.setText("");
    	pushOysterIP.setText("");
    	readLocally.load();
    }
}
