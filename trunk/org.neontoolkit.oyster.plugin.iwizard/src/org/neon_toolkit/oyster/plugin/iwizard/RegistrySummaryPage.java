package org.neon_toolkit.oyster.plugin.iwizard;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.neon_toolkit.omv.api.core.OMVFormalityLevel;
import org.neon_toolkit.omv.api.core.OMVKnowledgeRepresentationParadigm;
import org.neon_toolkit.omv.api.core.OMVLicenseModel;
import org.neon_toolkit.omv.api.core.OMVOntology;
import org.neon_toolkit.omv.api.core.OMVOntologyDomain;
import org.neon_toolkit.omv.api.core.OMVOntologyEngineeringMethodology;
import org.neon_toolkit.omv.api.core.OMVOntologyEngineeringTool;
import org.neon_toolkit.omv.api.core.OMVOntologyLanguage;
import org.neon_toolkit.omv.api.core.OMVOntologySyntax;
import org.neon_toolkit.omv.api.core.OMVOntologyTask;
import org.neon_toolkit.omv.api.core.OMVOntologyType;
import org.neon_toolkit.omv.api.core.OMVPerson;
import org.neon_toolkit.registry.api.Oyster2Connection;
import org.neon_toolkit.registry.api.Oyster2Manager;


public class RegistrySummaryPage extends WizardPage  {

	//public static final String pathOyster = "C:\\Oyster2APIv0.98\\new store";
	public static final String PAGE_NAME = "Summary";
	
	//control used in this wizard
	private TableEditor[] senseEditors;
	private Button[] senseButtons;
	private int indexButton = 0;

	//table information
	private Table table;
	static String [] columnTitles	= {"", "URI", "Name"};

	//controls that show ontology information
	private Text text1;
	
	//conditions of the previous wizard
	private HashMap<String,Object> conditions = new HashMap<String,Object> () ;

	//path of the selected ontology
	private String ontologyLocator = "";
	
	
	//constructor
	public RegistrySummaryPage() {
		super(PAGE_NAME, "Summary Page", null);		  
        super.setTitle("Ontology Metadata"); 
        super.setDescription("Show the ontologies that fulfill the conditions provided by the user"); 
	}


	public IWizardPage getPreviousPage() {
		
		//get the conditions of the previous page
		RegistryConditionPage conditionPage = (RegistryConditionPage) super.getPreviousPage();
		Table conditionsTable = conditionPage.getTable();
		
		if (conditionsTable.getItemCount()>0) {
			
			for (int i=0; i<conditionsTable.getItemCount(); i++) {
				TableItem item = (TableItem) conditionsTable.getItem(i);  
				
				String key = item.getText(0);
				Object value = null;
				
				//get all conditions
				if (key.equals("name") ||
					key.equals("acronym") ||
					key.equals("description") ||
					key.equals("documentation") ||
					key.equals("URI") ||
					key.equals("creationDate") || 
					key.equals("notes") ||
					key.equals("modificationDate") ||
					key.equals("naturalLanguage") ||
					key.equals("version") ||
					key.equals("keywords") ||
					key.equals("status") ||
					key.equals("keyClasses") ||
					key.equals("resourceLocator") ||
					key.equals("knownUsage") ||
					key.equals("expressiveness")) value = (String) item.getText(1);
				
				else if (key.equals("numberOfIndividuals") ||
						 key.equals("numberOfClasses") ||
						 key.equals("numberOfAxioms") ||
						 key.equals("numberOfProperties")) value = Integer.parseInt(item.getText(1));
				
				else if (key.equals("isConsistentAccordingToReasoner") ||
						 key.equals("containsRBox") ||
						 key.equals("containsTBox") ||
						 key.equals("containsABox")) value = Boolean.valueOf(item.getText(1));
				
				else if (key.equals("hasPriorVersion") ||
						 key.equals("isIncompatibleWith") ||
						 key.equals("useImports") ||
						 key.equals("isBackwardCompatibleWith")) {
					
					OMVOntology ont = new OMVOntology();
					ont.setURI((String) item.getText(1));
					value = ont;
					
				} else if (key.equals("designedForOntologyTask")) {
					OMVOntologyTask task = new OMVOntologyTask();
					task.setName((String) item.getText(1));
					value = task;
					
				} else if (key.equals("hasDomain")) {
					OMVOntologyDomain domain = new OMVOntologyDomain();
					domain.setURI((String) item.getText(1));
					value = domain;
					
				} else if (key.equals("hasOntologyLanguage")) {
					OMVOntologyLanguage language = new OMVOntologyLanguage();
					language.setName((String) item.getText(1));
					value = language;
					
				} else if (key.equals("hasFormalityLevel")) {
					OMVFormalityLevel level = new OMVFormalityLevel();
					level.setName((String) item.getText(1));
					value = level;
					
				} else if (key.equals("hasOntologySyntax")) {
					OMVOntologySyntax syntax = new OMVOntologySyntax();
					syntax.setName((String) item.getText(1));
					value = syntax;
					
				} else if (key.equals("usedOntologyEngineeringTool")) {
					OMVOntologyEngineeringTool tool = new OMVOntologyEngineeringTool();
					tool.setName((String) item.getText(1));
					value = tool;
					
				} else if (key.equals("usedKnowledgeRepresentationParadigm")) {
					OMVKnowledgeRepresentationParadigm paradigma = new OMVKnowledgeRepresentationParadigm();
					paradigma.setName((String) item.getText(1));
					value = paradigma;
					
				} else if (key.equals("hasLicense")) {
					OMVLicenseModel license = new OMVLicenseModel();
					license.setName((String) item.getText(1));
					value = license;
					
				} else if (key.equals("usedOntologyEngineeringMethodology")) {
					OMVOntologyEngineeringMethodology methodology = new OMVOntologyEngineeringMethodology();
					methodology.setName((String) item.getText(1));
					value = methodology;
					
				} else if (key.equals("hasCreator") ||
						   key.equals("hasContributor") ||
						   key.equals("endorsedBy")) {
					
					OMVPerson creator = new OMVPerson();
					String[] tokens = ((String) item.getText(1)).split(" ");
					
					//split string in tokens
					if (tokens.length>1) {
						creator.setFirstName(tokens[0]);
						creator.setLastName(tokens[1]);
					} else
						creator.setLastName(tokens[0]);
					
					value = creator;
					
				} else if (key.equals("isOfType")) {
					OMVOntologyType type = new OMVOntologyType();
					type.setName((String) item.getText(1));
					value = type;
				}
				
				//store the conditions
				conditions.put(key, value);
			}
		}

		//show the table with the results
		fillTable(conditions);
		
		return super.getPreviousPage();
	}
	 

	public Table getTable() {		
		return table;
	}
	
	public String getOntologyLocator() {
		return ontologyLocator;
	}
	
	public void createControl(Composite parent) {
		
	    Composite topLevel = new Composite(parent, SWT.NONE);
	    topLevel.setLayout(new GridLayout(1, false));

		// Create the table widget
	    
	    GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);//new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
	    
    	table = new Table (topLevel, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
    	table.setLinesVisible (true);
    	table.setHeaderVisible (true);
    	
    	
		//Fill the table with the header information
    	
		for (int i = 0; i < columnTitles.length; i++) {		
			TableColumn column = new TableColumn(table, SWT.LEFT);
			column.setText(columnTitles[i]);
		}
		
		
		int itemHeight = table.getItemHeight();
	    gridData.heightHint = 5 * itemHeight;
	    
		
    	table.setLayoutData(gridData);
    	
    	//setWidgetSize (table);
    	//Control that show ontology information (serialized)
	    text1 = new Text(topLevel, SWT.LEFT | SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
	    text1.setEditable(false);
	    //text1.setLayoutData(new GridData(GridData.FILL, SWT.FILL, true, false));
	    setWidgetSize (text1);
	    
	    setControl(topLevel);
	    setPageComplete(true);
	  }

	
	/*Set the size of a control*/
	void setWidgetSize(Control control) {
		int size = SWT.DEFAULT;
		GridData gridData = new GridData(size, size); 
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		
		//Expand the size of the control
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		control.setLayoutData (gridData);		
	}
	

	//Fill table with the ontologies that fulfill the conditions
	private void fillTable (HashMap OntConditions) {
		
		//search the ontologies
        Set<OMVOntology> ontologies = getOntologies(OntConditions);
        int numOntologies = ontologies.size();
                
	    // Create the table editors for radio button
	    senseEditors = new TableEditor[numOntologies];
	    // Create buttons for choose the ontology
	    senseButtons = new Button[numOntologies];

	    
	    //Listener for radio button
        Listener listener = new Listener() {
            public void handleEvent (Event e) {
                 doSelection((Button)e.widget);
            }
        };
        
        //Listener for radio button
        Listener listener1 = new Listener() {
            public void handleEvent (Event e) {
                //System.out.println("Here"+((Table)e.widget).getSelectionIndex());
                doSelection(senseButtons[((Table)e.widget).getSelectionIndex()]);
            }
        };
        table.addListener(SWT.CHECK, listener1);
        
	    // Fill the table with the ontology information
        for(Iterator it = ontologies.iterator(); it.hasNext();) {
        	
        	//retrieve a sense
        	OMVOntology ontology = (OMVOntology) it.next();
        	
        	String URI = ontology.getURI();
        	//String name = ontology.getName().toString()==null ? "": ontology.getName().toString();
        	String name;
        	Iterator itS = ontology.getName().iterator();
        	if(itS.hasNext()){
        		name=(String)itS.next();
        	}else{
        		name ="";
        	}
        	
        	
        	String locator = ontology.getResourceLocator()==null ? "" : ontology.getResourceLocator();
        	
        	//serialized information
        	Set<OMVOntology> OMVSet = new HashSet<OMVOntology>() ;
        	OMVSet.add(ontology);
        	String OMVSerial = Oyster2Manager.serializeOMVOntologies(OMVSet);
        	
			TableItem item = new TableItem (table, SWT.NONE);
			
			
			//create the editor and button
		    senseEditors[indexButton] = new TableEditor(table);
		    
			senseButtons[indexButton] = new Button(table, SWT.RADIO);
			senseButtons[indexButton].addListener(SWT.Selection, listener);
									
		    //set attributes of the button
			senseButtons[indexButton].setText("");
			senseButtons[indexButton].setData("Locator", locator);
			senseButtons[indexButton].setData("Serial", OMVSerial);
			
		    senseButtons[indexButton].computeSize(SWT.DEFAULT, table.getItemHeight());
			
		    if (indexButton==0) {
		    	senseButtons[indexButton].setSelection(true);
		    	text1.setText(OMVSerial);
		    	ontologyLocator = locator;
		    }
		    
		    //set attributes of the editor
		    senseEditors[indexButton].grabHorizontal = true;
		    senseEditors[indexButton].minimumHeight = senseButtons[indexButton].getSize().y;
		    senseEditors[indexButton].minimumWidth = senseButtons[indexButton].getSize().x;
		    
		    //set the editor for the first column in the row
		    senseEditors[indexButton].setEditor(senseButtons[indexButton], item, 0);
			
		    indexButton++;
		    
		    //extracting the ontology information
			item.setText(1, URI);			
			item.setText(2, name);
			
		}
        
		packColumns();
        
		//refresh layout
        Composite composite = table.getParent();
        
        composite.layout();        
	}
	
	//select one radio button 
	void doSelection(Button button) {		
		for (int i=0; i<senseButtons.length; i++) {
			senseButtons[i].setSelection(false);
		}		
		button.setSelection(true);
		
		//update ontology information
		ontologyLocator = (String) button.getData("Locator");
		text1.setText((String) button.getData("Serial"));
	}
	
	void packColumns () {
		int columnCount = table.getColumnCount(); 
		for (int i = 0; i < columnCount; i++) {
			TableColumn tableColumn = table.getColumn(i);
			
			tableColumn.pack();
		}		
	}
	
	//get the ontologies that fulfill the conditions provided by the user 
	public Set<OMVOntology> getOntologies(HashMap OntConditions) {
		
		//retrieve all conditions		
		OMVOntology conditions = new OMVOntology();
		
		for (Iterator it = OntConditions.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			
			//string values
			if (key.equals("name")) conditions.addName((String) OntConditions.get(key));
			else if (key.equals("acronym")) conditions.setAcronym((String) OntConditions.get(key));
			else if (key.equals("description")) conditions.setDescription((String) OntConditions.get(key));
			else if (key.equals("documentation")) conditions.setDocumentation((String) OntConditions.get(key));
			else if (key.equals("URI")) conditions.setURI((String) OntConditions.get(key));
			else if (key.equals("creationDate")) conditions.setCreationDate((String) OntConditions.get(key));
			else if (key.equals("notes")) conditions.setNotes((String) OntConditions.get(key));
			else if (key.equals("modificationDate")) conditions.setModificationDate((String) OntConditions.get(key));
			else if (key.equals("naturalLanguage")) conditions.addNaturalLanguage((String) OntConditions.get(key));
			else if (key.equals("version")) conditions.setVersion((String) OntConditions.get(key)); 
			else if (key.equals("keywords")) conditions.addKeywords((String) OntConditions.get(key)); 
			else if (key.equals("status")) conditions.setStatus((String) OntConditions.get(key));
			else if (key.equals("keyClasses")) conditions.addKeyClasses((String) OntConditions.get(key));
			else if (key.equals("resourceLocator")) conditions.setResourceLocator((String) OntConditions.get(key));
			else if (key.equals("knownUsage")) conditions.addKnownUsage((String) OntConditions.get(key));
			else if (key.equals("expressiveness")) conditions.setExpressiveness((String) OntConditions.get(key));

			//integer values
			else if (key.equals("numberOfIndividuals")) conditions.setNumberOfIndividuals((Integer) OntConditions.get(key));
			else if (key.equals("numberOfClasses")) conditions.setNumberOfClasses((Integer) OntConditions.get(key));
			else if (key.equals("numberOfAxioms")) conditions.setNumberOfAxioms((Integer) OntConditions.get(key));
			else if (key.equals("numberOfProperties")) conditions.setNumberOfProperties((Integer) OntConditions.get(key));

			//boolean values
			else if (key.equals("isConsistentAccordingToReasoner")) conditions.setIsConsistentAccordingToReasoner((Boolean) OntConditions.get(key));
			else if (key.equals("containsRBox")) conditions.setContainsRBox((Boolean) OntConditions.get(key));
			else if (key.equals("containsTBox")) conditions.setContainsTBox((Boolean) OntConditions.get(key));
			else if (key.equals("containsABox")) conditions.setContainsABox((Boolean) OntConditions.get(key));
			
			//OMVOntology values
			else if (key.equals("hasPriorVersion")) conditions.setHasPriorVersion((OMVOntology) OntConditions.get(key));
			else if (key.equals("isIncompatibleWith")) conditions.addIsIncompatibleWith((OMVOntology) OntConditions.get(key));
			else if (key.equals("useImports")) conditions.addUseImports((OMVOntology) OntConditions.get(key));
			else if (key.equals("isBackwardCompatibleWith")) conditions.addIsBackwardCompatibleWith((OMVOntology) OntConditions.get(key));
			
			//OMVOntologyDomain values
			else if (key.equals("hasDomain")) conditions.addHasDomain((OMVOntologyDomain) OntConditions.get(key));
			
			//OMVOntologyTask values
			else if (key.equals("designedForOntologyTask")) conditions.addDesignedForOntologyTask((OMVOntologyTask) OntConditions.get(key));
			
			//OMVOntologyLanguage values
			else if (key.equals("hasOntologyLanguage")) conditions.setHasOntologyLanguage((OMVOntologyLanguage) OntConditions.get(key));

			//OMVFormalityLevel values
			else if (key.equals("hasFormalityLevel")) conditions.setHasFormalityLevel((OMVFormalityLevel) OntConditions.get(key));
			
			//OMVOntologySyntax values
			else if (key.equals("hasOntologySyntax")) conditions.setHasOntologySyntax((OMVOntologySyntax) OntConditions.get(key));
			
			//OMVOntologyEngineeringTool values
			else if (key.equals("usedOntologyEngineeringTool")) conditions.addUsedOntologyEngineeringTool((OMVOntologyEngineeringTool) OntConditions.get(key));
			
			//OMVKnowledgeRepresentationParadigm values
			else if (key.equals("usedKnowledgeRepresentationParadigm")) conditions.addUsedKnowledgeRepresentationParadigm((OMVKnowledgeRepresentationParadigm) OntConditions.get(key));
			
			//OMVLicenseModel values
			else if (key.equals("hasLicense")) conditions.setHasLicense((OMVLicenseModel) OntConditions.get(key));
			
			//OMVOntologyEngineeringMethodology values
			else if (key.equals("usedOntologyEngineeringMethodology")) conditions.addUsedOntologyEngineeringMethodology((OMVOntologyEngineeringMethodology) OntConditions.get(key));
			
			//OMVPerson values
			else if (key.equals("hasCreator")) conditions.addHasCreator((OMVPerson) OntConditions.get(key));
			else if (key.equals("hasContributor")) conditions.addHasContributor((OMVPerson) OntConditions.get(key));
			else if (key.equals("endorsedBy")) conditions.addEndorsedBy((OMVPerson) OntConditions.get(key));
			
			//OMVOntologyType
			else if (key.equals("isOfType")) conditions.setIsOfType((OMVOntologyType) OntConditions.get(key));
			
		}
		
		return executeSearchInOyster(conditions);		
	}
	
	//search ontologies using Oyster
	private Set<OMVOntology> executeSearchInOyster(OMVOntology conditions) {

		
		//Oyster2Connection connection = Oyster2Manager.newConnection(false, pathOyster);
		Oyster2Connection connection = Oyster2Manager.newConnection(false);
		
		
		//execute the search
		Set<OMVOntology> OMVSet2 = new HashSet<OMVOntology>();
		Set<Object> OMVOb1 = connection.search(conditions);
		
		
		if (OMVOb1.size()>0){
			Iterator it = OMVOb1.iterator();
			try{
				while(it.hasNext()){
					OMVOntology omv = (OMVOntology)it.next();
					OMVSet2.add(omv);
				}				
			}catch(Exception ignore){
				//	-- ignore
			}
		}		
		
		return OMVSet2;
	}
	
}
