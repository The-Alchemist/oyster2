package ui;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import oyster2.*;

public class ConditionPreferencePage extends PreferencePage{
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
	private Text bootstrapPeerUID;
	private Text bootstrapPeerIP;
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
		Label fieldLabel11 = new Label(panel, SWT.NULL);
		fieldLabel11.setText("bootstrapPeerUID: ");
		bootstrapPeerUID = new Text(panel,SWT.SINGLE | SWT.BORDER);
		bootstrapPeerUID.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL));
		Label fieldLabel12 = new Label(panel, SWT.NULL);
		fieldLabel12.setText("bootstrapPeerIP: ");
		bootstrapPeerIP = new Text(panel,SWT.SINGLE | SWT.BORDER);
		bootstrapPeerIP.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL));
		
		typeOntologyRoot.setText(Constants.DefaultTypeOntologyRoot);
		topicOntologyRoot.setText(Constants.DefaultTopicOntologyRoot);
		searchCondition_1.setText(Constants.DefaultSearchCondition_1);
		searchCondition_2.setText(Constants.DefaultSearchCondition_2);
		searchCondition_3.setText(Constants.DefaultSearchCondition_3);
		searchCondition_4.setText(Constants.DefaultSearchCondition_4);
		searchCondition_5.setText(Constants.DefaultSearchCondition_5);
		/*localPeerName.setText(Constants.DefaultLocalPeerName);
		localPeerType.setText(Constants.DefaultLocalPeerType);
		bootstrapPeerName.setText(Constants.DefaultBootStrapPeerName);
		bootstrapPeerIP.setText(Constants.DefaultBootStrapPeerIP);*/
		return panel;
	}
	/** 
	 * Method declared on IPreferencePage.
	 */
	public boolean performOk() {
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
		pref.setValue(Constants.BootStrapPeerUID, bootstrapPeerUID.getText());
		pref.setValue(Constants.BootStrapPeerIP, bootstrapPeerIP.getText());
		return true;
	}
	
	/**
	 * It is overrided method. 
	 */
	protected void performDefaults() {
		typeOntologyRoot.setText(Constants.DefaultTypeOntologyRoot);
		topicOntologyRoot.setText(Constants.DefaultTopicOntologyRoot);
		searchCondition_1.setText(Constants.DefaultSearchCondition_1);
		searchCondition_2.setText(Constants.DefaultSearchCondition_2);
		searchCondition_3.setText(Constants.DefaultSearchCondition_3);
		searchCondition_4.setText(Constants.DefaultSearchCondition_4);
		searchCondition_5.setText(Constants.DefaultSearchCondition_5);
		/*localPeerName.setText(Constants.DefaultLocalPeerName);
		localPeerType.setText(Constants.DefaultLocalPeerType);
		bootstrapPeerName.setText(Constants.DefaultBootStrapPeerName);
		bootstrapPeerIP.setText(Constants.BootStrapPeerIP);*/
		super.performDefaults();
	}


}
