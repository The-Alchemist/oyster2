package org.neon_toolkit.oyster.plugin.iwizard;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;

import com.ontoprise.ontostudio.datamodel.DatamodelPlugin;
import com.ontoprise.ontostudio.io.FileFilter;
import com.ontoprise.ontostudio.io.IOPlugin;
import com.ontoprise.ontostudio.io.Messages;
import com.ontoprise.ontostudio.io.wizard.AbstractImportSelectionPage;


public class RegistryImportSelectionPage extends AbstractImportSelectionPage {

	//path of the selected ontology
	private String locator = ""; 
	
    class EventHandler implements SelectionListener, ModifyListener {

        public void widgetSelected(SelectionEvent se) {
            if (se.getSource().equals(_createButton)) {
                createNewProject();
            }
            checkStatus();
        }

        public void widgetDefaultSelected(SelectionEvent se) {
            widgetSelected(se);
        }

        public void modifyText(ModifyEvent me) {
            checkStatus();
        }
    }

    public RegistryImportSelectionPage(FileFilter filter) {
        super(filter);
        setTitle("Ontology Import");      
    }

    @Override
    public URL getSelectedURL() {
    	
        try {
        	return new URL(locator);
            //return new URL(_fileInput.getText());
        } catch (MalformedURLException e) {
            try {
                //return new File(_fileInput.getText()).toURL();
                return new File(locator).toURL();
            } catch (MalformedURLException e1) {
                IOPlugin.logError("", e); //$NON-NLS-1$
                return null;
            }
        }
    }
    
    @Override
    public String[] getFiles() {
        String files = locator; //_fileInput.getText();
        return files.split(";"); //$NON-NLS-1$
    }

    @Override
    public URL[] getSelectedURLS() {
        String[] filesArray = getFiles(); 
        URL[] urls = new URL[filesArray.length];
        for (int i = 0; i < filesArray.length; i++) {
            URL url = null;
            try {
                url = new URL(filesArray[i]);
            } catch (MalformedURLException e) {
                try {
                    url = new File(filesArray[i]).toURL();
                } catch (MalformedURLException e1) {
                    IOPlugin.logError("", e); //$NON-NLS-1$
                    return null;
                }
            }
            urls[i] = url;
        }
        return urls;
    }

    public IWizardPage getPreviousPage() {
    	
    	//get selected ontology of the previous page
    	RegistrySummaryPage summaryPage = (RegistrySummaryPage) super.getPreviousPage();
		Table ontologiesTable = summaryPage.getTable();

		if (ontologiesTable.getItemCount()>0) {
			locator = summaryPage.getOntologyLocator();    			
		}
    	
    	return super.getPreviousPage();
    }
    
    @Override
    public boolean isFileSelected() {
    	return true;
    }


    @Override
    public void createControl(Composite parent) {
        _composite = new Composite(parent, SWT.NONE);

        GridLayout gridLayout = new GridLayout(3, false);
        _composite.setLayout(gridLayout);
        GridData gridData;
        EventHandler listener = new EventHandler();

        Label containerLabel = new Label(_composite, SWT.NONE);
        containerLabel.setText(Messages.getString("FileSystemImportSelectionPage.5")); //$NON-NLS-1$
        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        containerLabel.setLayoutData(gridData);

        _projectCombo = new Combo(_composite, SWT.READ_ONLY);
        gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
        _projectCombo.setLayoutData(gridData);
        _projectCombo.addSelectionListener(listener);

        _createButton = new Button(_composite, SWT.PUSH);
        _createButton.setText(Messages.getString("FileSystemImportSelectionPage.14")); //$NON-NLS-1$
        _createButton.addSelectionListener(listener);
        _createButton.setVisible(false);

        initControls();
        setControl(_composite);
    }

    @Override
    protected void initControls() {
    	
    	try {
            String[] projects = DatamodelPlugin.getDefault().getOntologyProjects();
            updateCombo(projects);
            if (projects.length == 0) {
                _createButton.setVisible(true);
            } else {
                if (projects.length == 1) { 
                    _projectCombo.select(_projectCombo.indexOf(projects[0]));
                }
            }
        } catch (CoreException e) {
            IOPlugin.logError("", e); //$NON-NLS-1$
        }
        if (_preselectedProject != null) {
            _projectCombo.select(_projectCombo.indexOf(_preselectedProject));
        }
        checkStatus();
    }

    @Override
    public void checkStatus() {
    	setPageComplete(true);
    }
    
}