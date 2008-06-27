package org.neontoolkit.registry.ui;


import java.net.InetAddress;
import java.net.UnknownHostException;

import org.eclipse.jface.preference.BooleanFieldEditor;
//import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.*;
import org.neontoolkit.registry.oyster2.Constants;
import org.neontoolkit.registry.oyster2.Oyster2Factory;
import org.neontoolkit.registry.oyster2.Properties;


public class ConditionPreferencePage extends PreferencePage{
	
	static Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private static Properties mprop = mOyster2.getProperties();
	
	String host="";
	
	private Text typeOntologyRoot;
	private Text topicOntologyRoot;
	private Text searchCondition_1;
	private Text searchCondition_2;
	private Text searchCondition_3;
	private Text searchCondition_4;
	private Text searchCondition_5;
	private Text localPeerName;
	private Text localPeerType;
	private Text bootstrapPeerName;
	private Text bootstrapPeerIP;
	private Text peerRouterIP;
	private Boolean routerDisabled = false;
	private Label fieldLabel13;
	public ConditionPreferencePage(String name){
		super(name);
	}
	protected Control createContents(Composite parent) {
		int style = SWT.NONE;
		style |= SWT.FULL_SELECTION;
		style |= SWT.BORDER;
		this.setTitle("Conditions preferences");
		Composite panel = new Composite(parent, SWT.NULL);
		//panel.setLayout(new FillLayout());
		panel.setLayout(new GridLayout(2, false));
		Label fieldLabel1 = new Label(panel, SWT.NULL);
		fieldLabel1.setText("typeOntologyRoot: ");
		typeOntologyRoot = new Text(panel,SWT.SINGLE | SWT.BORDER);
		typeOntologyRoot.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL));
		Label fieldLabel2 = new Label(panel, SWT.NULL);
		fieldLabel2.setText("topicOntologyRoot: ");
		topicOntologyRoot = new Text(panel,SWT.SINGLE | SWT.BORDER);
		topicOntologyRoot.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL));
		Label fieldLabel3 = new Label(panel, SWT.NULL);
		fieldLabel3.setText("searchCondition_1: ");
		searchCondition_1 = new Text(panel,SWT.SINGLE | SWT.BORDER);
		searchCondition_1.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL));
		Label fieldLabel4 = new Label(panel, SWT.NULL);
		fieldLabel4.setText("searchCondition_2: ");
		searchCondition_2 = new Text(panel,SWT.SINGLE | SWT.BORDER);
		searchCondition_2.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL));
		Label fieldLabel5 = new Label(panel, SWT.NULL);
		fieldLabel5.setText("searchCondition_3: ");
		searchCondition_3 = new Text(panel,SWT.SINGLE | SWT.BORDER);
		searchCondition_3.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL));
		Label fieldLabel6 = new Label(panel, SWT.NULL);
		fieldLabel6.setText("searchCondition_4: ");
		searchCondition_4 = new Text(panel,SWT.SINGLE | SWT.BORDER);
		searchCondition_4.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL));
		Label fieldLabel7 = new Label(panel, SWT.NULL);
		fieldLabel7.setText("searchCondition_5: ");
		searchCondition_5 = new Text(panel,SWT.SINGLE | SWT.BORDER);
		searchCondition_5.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL));
		Label fieldLabel8 = new Label(panel, SWT.NULL);
		fieldLabel8.setText("localPeerName: ");
		localPeerName = new Text(panel,SWT.SINGLE | SWT.BORDER);
		localPeerName.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL));
		Label fieldLabel9 = new Label(panel, SWT.NULL);
		fieldLabel9.setText("localPeerType: ");
		localPeerType = new Text(panel,SWT.SINGLE | SWT.BORDER);
		localPeerType.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL));
		Label fieldLabel10 = new Label(panel, SWT.NULL);
		fieldLabel10.setText("bootstrapPeerName: ");
		bootstrapPeerName = new Text(panel,SWT.SINGLE | SWT.BORDER);
		bootstrapPeerName.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL));
		/*
		Label fieldLabel11 = new Label(panel, SWT.NULL);
		fieldLabel11.setText("bootstrapPeerUID: ");
		bootstrapPeerUID = new Text(panel,SWT.SINGLE | SWT.BORDER);
		bootstrapPeerUID.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL));
		*/
		Label fieldLabel12 = new Label(panel, SWT.NULL);
		fieldLabel12.setText("bootstrapPeerIP: ");
		bootstrapPeerIP = new Text(panel,SWT.SINGLE | SWT.BORDER);
		bootstrapPeerIP.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL));
		
		BooleanFieldEditor behindRouter = new BooleanFieldEditor("BehindRouter", "My peer is behind a router",
				BooleanFieldEditor.SEPARATE_LABEL, panel);
		behindRouter.setPropertyChangeListener(new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				routerDisabled = ((Boolean) event.getNewValue()).booleanValue();
				fieldLabel13.setEnabled(routerDisabled);
				peerRouterIP.setEnabled(routerDisabled);
				fieldLabel13.setVisible(routerDisabled);
				peerRouterIP.setVisible(routerDisabled);
			}
		});
		fieldLabel13 = new Label(panel, SWT.NULL);
		fieldLabel13.setText("peerRouterIP: ");
		peerRouterIP = new Text(panel,SWT.SINGLE | SWT.BORDER);
		peerRouterIP.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL));
		peerRouterIP.setEnabled(false);
		fieldLabel13.setEnabled(false);
		peerRouterIP.setVisible(false);
		fieldLabel13.setVisible(false);
		
		setHost();
		
		localPeerName.setText(host);
		localPeerType.setText(Constants.DefaultLocalPeerType);
		bootstrapPeerName.setText(Constants.DefaultBootStrapPeerName);
		bootstrapPeerIP.setText(Constants.DefaultBootStrapPeerIP);
		typeOntologyRoot.setText(Constants.DefaultTypeOntologyRoot);
		topicOntologyRoot.setText(Constants.DefaultTopicOntologyRoot);
		searchCondition_1.setText(Constants.DefaultSearchCondition_1);
		searchCondition_2.setText(Constants.DefaultSearchCondition_2);
		searchCondition_3.setText(Constants.DefaultSearchCondition_3);
		searchCondition_4.setText(Constants.DefaultSearchCondition_4);
		searchCondition_5.setText(Constants.DefaultSearchCondition_5);
		return panel;
	}
	/** 
	 * Method declared on IPreferencePage.
	 */
	public boolean performOk() {
		mprop.setString(Constants.TypeOntologyRoot, typeOntologyRoot.getText());
		mprop.setString(Constants.TopicOntologyRoot, topicOntologyRoot.getText());
		mprop.setString(Constants.SearchCondition_1, searchCondition_1.getText());
		mprop.setString(Constants.SearchCondition_2, searchCondition_2.getText());
		mprop.setString(Constants.SearchCondition_3, searchCondition_3.getText());
		mprop.setString(Constants.SearchCondition_4, searchCondition_4.getText());
		mprop.setString(Constants.SearchCondition_5, searchCondition_5.getText());
		mprop.setString(Constants.LocalPeerName, localPeerName.getText());
		mprop.setString(Constants.LocalPeerType, localPeerType.getText());
		mprop.setString(Constants.BootStrapPeerName, bootstrapPeerName.getText());
		mprop.setString(Constants.BootStrapPeerIP, bootstrapPeerIP.getText());
		
		mprop.storeOn();
		//mprop.setString(Constants.PeerRouterIP, peerRouterIP.getText());
		
		/*
		IPreferenceStore pref = this.getPreferenceStore();
		pref.setValue(Constants.TypeOntologyRoot, typeOntologyRoot.getText());
		pref.setValue(Constants.TopicOntologyRoot, topicOntologyRoot.getText());
		pref.setValue(Constants.SearchCondition_1, searchCondition_1.getText());
		pref.setValue(Constants.SearchCondition_2, searchCondition_2.getText());
		pref.setValue(Constants.SearchCondition_3, searchCondition_3.getText());
		pref.setValue(Constants.SearchCondition_4, searchCondition_4.getText());
		pref.setValue(Constants.SearchCondition_5, searchCondition_5.getText());
		pref.setValue(Constants.LocalPeerName, localPeerName.getText());
		pref.setValue(Constants.LocalPeerType, localPeerType.getText());
		pref.setValue(Constants.BootStrapPeerName, bootstrapPeerName.getText());
		pref.setValue(Constants.BootStrapPeerIP, bootstrapPeerIP.getText());
		pref.setValue(Constants.PeerRouterIP, peerRouterIP.getText());
		*/
		return true;
	}
	
	/**
	 * It is overrided method. 
	 */
	protected void performDefaults() {
		setHost();
		localPeerName.setText(host);
		localPeerType.setText(Constants.DefaultLocalPeerType);
		bootstrapPeerName.setText(Constants.DefaultBootStrapPeerName);
		bootstrapPeerIP.setText(Constants.BootStrapPeerIP);
		typeOntologyRoot.setText(Constants.DefaultTypeOntologyRoot);
		topicOntologyRoot.setText(Constants.DefaultTopicOntologyRoot);
		searchCondition_1.setText(Constants.DefaultSearchCondition_1);
		searchCondition_2.setText(Constants.DefaultSearchCondition_2);
		searchCondition_3.setText(Constants.DefaultSearchCondition_3);
		searchCondition_4.setText(Constants.DefaultSearchCondition_4);
		searchCondition_5.setText(Constants.DefaultSearchCondition_5);
		super.performDefaults();
	}
	
	private void setHost(){
		try {
			host = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

/*
mprop.setString(Constants.NUMBER_OF_COLUMNS, Constants.DefaultNUMBER_OF_COLUMNS);
mprop.setString(Constants.COLUMN_TYPE+"0", Constants.DefaultCOLUMN_TYPE0);
mprop.setString(Constants.COLUMN_TYPE+"1", Constants.DefaultCOLUMN_TYPE1);
mprop.setString(Constants.COLUMN_TYPE+"2", Constants.DefaultCOLUMN_TYPE2);
mprop.setString(Constants.COLUMN_NAME+"0", Constants.DefaultCOLUMN_NAME0);
mprop.setString(Constants.COLUMN_NAME+"1", Constants.DefaultCOLUMN_NAME1);
mprop.setString(Constants.COLUMN_NAME+"2", Constants.DefaultCOLUMN_NAME2);
mprop.setString(Constants.COLUMN_WIDTH+"0", Constants.DefaultCOLUMN_WIDTH0);
mprop.setString(Constants.COLUMN_WIDTH+"1", Constants.DefaultCOLUMN_WIDTH1);
mprop.setString(Constants.COLUMN_WIDTH+"2", Constants.DefaultCOLUMN_WIDTH2);
*/