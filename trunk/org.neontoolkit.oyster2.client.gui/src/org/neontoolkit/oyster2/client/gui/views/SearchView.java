/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.views;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.ColumnLayout;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.ViewPart;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.GUIConstants;
import org.neontoolkit.oyster2.client.gui.IMessageResolver;
import org.neontoolkit.oyster2.client.gui.MessageResolver;
import org.neontoolkit.oyster2.client.gui.TargetServerComposite;
import org.neontoolkit.oyster2.client.gui.dialogs.TranslatedAttributeSelectionDialog;
import org.neontoolkit.oyster2.client.gui.jobs.SubmitQueryJob;
import org.neontoolkit.oyster2.client.gui.search.QueryBuilderAdapter;
import org.neontoolkit.oyster2.client.gui.search.composites.FilterComposite;
import org.neontoolkit.oyster2.client.gui.search.composites.FilterCompositeFactory;
import org.neontoolkit.oyster2.client.gui.search.model.IQueryDomainClass;
import org.neontoolkit.oyster2.client.gui.search.model.IQueryDomainClassAttribute;
import org.neontoolkit.oyster2.client.gui.search.model.QueryFactory;
import org.neontoolkit.oyster2.client.gui.util.Template;
import org.neontoolkit.oyster2.client.gui.util.TemplateManager;

/**
 * @author David Muñoz
 *
 */
public class SearchView extends ViewPart {
	
	private TargetServerComposite serverComposite = null;
	
	private static final QueryFactory queryFactory = QueryFactory.getInstance();
	
	private Button searchButton = null;
	
	private Combo queryTargetCombo = null;
	
	private Button attributeSelectionButton = null;
	
	private FormToolkit toolkit;
	
	private ScrolledForm form;
	
	private Label serverComboLabel = null;
	
	private Label targetQueryLabel = null;
	
	private IMessageResolver messagesResolver = null;
	
	private IMessageResolver querySpecificMessagesResolver = null;
	
	private Template template = null;
	
	/**
	 * Maps a label with the internal value
	 */
	private Map<String,String> queryTargets = null;
	
	//the following fields are to be wiped whenever the target of the
	// query changes
	
	private Section fieldsSection = null;
	
	/**
	 * Selected attributes to be used in the query
	 */
	private Map<String,Boolean> selection = null;
	// this should be taken from cached values
	
	/**
	 * Table mapping categories to the list of their attributes in the
	 * current query target
	 */
	private Map<String,String[]> categories = null;
	
		
	/**
	 * children composites of this view
	 */
	private Map<String,FilterComposite> composites = null;
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	
	public SearchView() {
		messagesResolver = new MessageResolver(
				this.getClass().getPackage().getName() + ".SearchMessages");
	}
	
	public void createPartControl(Composite parent) {
		
		queryTargets = new HashMap<String, String>();
		
		ColumnLayout layout = new ColumnLayout();
		layout.maxNumColumns = 1;
		layout.minNumColumns = 1;
		
		toolkit = new FormToolkit(parent.getDisplay());
		
		//setup form
		form = toolkit.createScrolledForm(parent);
		form.getBody().setLayout(layout);
		form.setText("Make search");
		form.setBackground(parent.getBackground());
		form.setBackgroundMode(SWT.INHERIT_NONE);
		
		makeConfigurationSection(form.getBody());
		
		//get information about the query target and
		//set values of tables to correct values
		
		makeFieldsSection(form.getBody());
		//use information to build sections
		makeDisposeListener(form.getBody());
		
		
	}
	
	private void makeDisposeListener(Composite composite) {
		DisposeListener listener = new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				onDispose();
			}
		};
		composite.addDisposeListener(listener);
	}
	
	/**
	 * Makes fields section, wiping out previous composites.
	 * It's intended to be used when the target of the query has changed,
	 * and there are no composites to be reused. 
	 * @param body
	 */
	private void makeFieldsSection(Composite formBody) {
		
		Section section = toolkit.createSection(formBody, GUIConstants.SECTION_STYLE);
		fieldsSection = section;
		section.setText("Query parameters");
		section.setLayout(new FillLayout());
		
		// create child composite to hold the Combos
		Composite sectionClient = toolkit.createComposite(section);
		section.setClient(sectionClient);
		sectionClient.setBackground(form.getBackground());
		
		
		// LAYOUT /////
		ColumnLayout sectionClientLayout = new ColumnLayout();
		sectionClientLayout.maxNumColumns = 1;
		sectionClientLayout.minNumColumns = 1;
		sectionClient.setLayout(sectionClientLayout);
		
		// COLORS
		section.setBackground(form.getBackground());
		section.setTitleBarBackground(form.getBackground());
		toolkit.createCompositeSeparator(section);
		
		composites = new HashMap<String, FilterComposite>();
		
		String queryTarget = queryTargetCombo.getItem(queryTargetCombo.getSelectionIndex());
		//get the internal name instead of the label:
		queryTarget = this.queryTargets.get(queryTarget);
		
		
		IQueryDomainClass classData = 
			queryFactory.getQueryTargetDescription(queryTarget);
		this.querySpecificMessagesResolver = 
			QueryFactory.getInstance().getMessageBundle(queryTarget);
		initFieldsContent(sectionClient,classData);
		
	}
	
	private void initFieldsContent(Composite sectionClient, IQueryDomainClass classData) {
		categories = null;
		selection = new HashMap<String, Boolean>();
		
		
		if (classData.hasCategories()) {
			categories = new HashMap<String, String[]>();
			String[] categoryNames = classData.getCategoryNames();
			for (String category : categoryNames) {
				categories.put(category,classData.getCategoryAttributes(category));
			}
		}
		//make composites for attributes
		String [] attributeNames = classData.getAttributeNames();
		List<String> defaultProperties = 
			template.getTemplateProperties(template.getDefaultTemplate());
		for (String attributeName : attributeNames) {
			if (defaultProperties.contains(attributeName)) {
				addFieldComposite(sectionClient,classData,attributeName);
				selection.put(attributeName,true);
			}
			else {
				selection.put(attributeName, false);
			}
		}
		
	}
	
	private void addFieldComposite(Composite sectionClient,
			IQueryDomainClass classData, String attributeName) {
		// TODO Auto-generated method stub
		//selection.put(attributeName,Boolean.valueOf(true));
		String section = "SearchViewAttr" +
			this.queryTargets.get(queryTargetCombo.getItem(queryTargetCombo.getSelectionIndex()))
			+ attributeName;
		// we don't have support for predefined values just now
		Group group = new Group(sectionClient,SWT.SHADOW_NONE);
		group.setText(querySpecificMessagesResolver.getString(attributeName));
		group.setLayout(new FillLayout());
		
		FilterComposite composite = FilterCompositeFactory.getInstance()
		.getComposite(group,classData.getAttribute(attributeName), section);
		
		composites.put(attributeName,composite);
		composite.setData(group);
		
	}

	
	/** 
	 * Creates a section for query settings, which will hold a combo
	 * to change the kind of query (for example, to ask for ontologies,
	 * ontologyTasks,ontologyTypes,etc, and a button to open the dialog
	 * change the attributes used in the query.
	 * @param parent The composite parent of this section, most likely the form
	 * body
	 * @return
	 */
	private Section makeConfigurationSection(Composite parent) {
		
		Section section = toolkit.createSection(parent, GUIConstants.SECTION_STYLE);
		section.setText("Query settings");
		section.setLayout(new FillLayout());
		
		// create child composite to hold the Combos
		Composite sectionClient = toolkit.createComposite(section);
		section.setClient(sectionClient);
		sectionClient.setBackground(form.getBackground());
		
		
		// LAYOUT /////
		/*GridLayout sectionClientLayout = new GridLayout();
		sectionClientLayout.makeColumnsEqualWidth = false;
		sectionClientLayout.numColumns = 2;
		sectionClient.setLayout(sectionClientLayout);
		*/
		
		
		// COLORS
		section.setBackground(form.getBackground());
		section.setTitleBarBackground(form.getBackground());
		
		toolkit.createCompositeSeparator(section);
		
		
		TableWrapLayout layout = new  TableWrapLayout();
		layout.makeColumnsEqualWidth = false;
		
		layout.numColumns = 2;
		sectionClient.setLayout(layout);
		
		
		//put the controls
		putServerCombo(sectionClient);
		putQueryTargetCombo(sectionClient);
		putAttributeSelectionButton(sectionClient);
		putSearchButton(sectionClient);
		
		TableWrapData td = new TableWrapData();
		td.grabHorizontal = true;
		serverComposite.setLayoutData(td);
		
		td = new TableWrapData();
		td.grabHorizontal = true;
		queryTargetCombo.setLayoutData(td);
		
		
		
		// make the listener that will change the fields for the
		// selected attributes of the selected query target.
		makeConfigurationListeners();
		return section;
	}
	

	private void putServerCombo(Composite parent) {
		serverComboLabel = new Label(parent,SWT.NONE);
		serverComboLabel.setText(messagesResolver.getString("SearchView.server.target.combo.label"));
		
		serverComposite = new TargetServerComposite(parent, SWT.WRAP);
		
	}

	private void putSearchButton(Composite sectionClient) {
		searchButton = new Button(sectionClient,SWT.PUSH);
		searchButton.setText("Start search");
		
	}

	private void makeConfigurationListeners() {
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				
				if (event.widget == attributeSelectionButton) {
					Shell shell = Activator.getDefault()
								.getWorkbench().getActiveWorkbenchWindow().getShell();
					TranslatedAttributeSelectionDialog dialog =
						new TranslatedAttributeSelectionDialog("searchViewAttributesDialog",
								shell,template);
					dialog.setCategories(categories);
					dialog.setSelection(selection);
					
					dialog.setMessagesResolver(querySpecificMessagesResolver);
					int result = dialog.open();
					if (result == Window.OK) {
						changeFieldsSection();
						form.getBody().layout(true,true);
						form.reflow(true);
					}
					
				}
				else if (event.widget == searchButton) {
					
					//save current input in composites
					SubmitQueryJob job = new SubmitQueryJob("search ontology");
					String targetService = Activator.getWebServersLocator().getCurrentSelection();
					job.setTargetService(targetService);
					Map<String,Object> filters = getFilters();
					
					String queryTarget = 
						queryTargetCombo.getItem(queryTargetCombo.getSelectionIndex());
					//get the internal name instead of the label:
					queryTarget = queryTargets.get(queryTarget);
					
					
					IQueryDomainClass classData = 
						queryFactory.getQueryTargetDescription(queryTarget);
					
					job.setFilters(filters);
					job.setUser(false);
					job.setQueryTarget(classData.getTargetJavaClass());
					job.schedule();
					
				}
				else if (event.widget == queryTargetCombo) {
					String target = queryTargetCombo.getItem(queryTargetCombo.getSelectionIndex());
					target = queryTargets.get(target);
					template = TemplateManager.getInstance().getTemplate(target);
				}
				
				
				
			}
		};
		attributeSelectionButton.addListener(SWT.Selection, listener);
		queryTargetCombo.addListener(SWT.Selection, listener);
		searchButton.addListener(SWT.Selection, listener);
		
		
	}

	private void putAttributeSelectionButton(Composite composite) {
		attributeSelectionButton = new Button(composite, SWT.PUSH);
		
		attributeSelectionButton.setText(messagesResolver.getString("SearchView.button.attribute.selection"));
		
	}

	private void putQueryTargetCombo(Composite composite) {
		targetQueryLabel = new Label(composite,SWT.NONE);
		targetQueryLabel.setText(messagesResolver.getString("SearchView.query.target.combo.label"));
		
		queryTargetCombo = new Combo(composite, SWT.READ_ONLY | SWT.DROP_DOWN);
		String omvClass = null;
		String label = null;
		
		String [] OMVSupportedClasses =
			queryFactory.getQueryTargets();
		String [] labels = new String[OMVSupportedClasses.length];
		
		IMessageResolver temporaryMessageResolver = null;
		//la clave con la que tengo que meterlo es el nombre interno
		for (int i = 0; i< OMVSupportedClasses.length;i++) {
			omvClass = OMVSupportedClasses[i];
			temporaryMessageResolver = QueryFactory.getInstance().getMessageBundle(omvClass);
			
			label =  temporaryMessageResolver.getString("target");
			queryTargets.put(label,omvClass);
			labels[i] = label;
		}
			 
		queryTargetCombo.setItems(labels);
		queryTargetCombo.select(0);
		
		template = TemplateManager.getInstance().getTemplate(OMVSupportedClasses[0]);
		
		
		this.querySpecificMessagesResolver = 
			QueryFactory.getInstance().getMessageBundle(OMVSupportedClasses[0]);
	}

	private void changeFieldsSection() {
		String queryTarget = queryTargetCombo.getItem(queryTargetCombo.getSelectionIndex());
		//get the internal name instead of the label:
		queryTarget = this.queryTargets.get(queryTarget);
		
		
		IQueryDomainClass classData = 
			queryFactory.getQueryTargetDescription(queryTarget);
		
		
		Composite currentComposite = null;
		Boolean attributeEnabled = null;
		for (String attributeName : classData.getAttributeNames()) {
			currentComposite = composites.get(attributeName);
			attributeEnabled = this.selection.get(attributeName);
			if (attributeEnabled) {
				//the composite must be present. If not found, we add it
				if (currentComposite == null) {
					this.addFieldComposite((Composite)fieldsSection.getClient(),
							classData, attributeName);
				}
			}
			else {
				// there must be no composite for this attribute
				if (currentComposite != null) {
					composites.remove(attributeName);
					//dispose the Group we set as data in this composite, and
					//the composite itself will be disposed
					((Composite)currentComposite.getData()).dispose();
				}
			}
		}
		
		
		fieldsSection.layout(true,true);
		
	}
	
	private Map<String,Object> getFilters() {
		//Map<String,Object> filters = new HashMap<String, Object>();
		
		String queryTarget = queryTargetCombo.getItem(queryTargetCombo.getSelectionIndex());
		//get the internal name instead of the label:
		queryTarget = this.queryTargets.get(queryTarget);
		
		IQueryDomainClassAttribute attributeData = null;
		IQueryDomainClass classData = 
			queryFactory.getQueryTargetDescription(queryTarget);
		
		Map<String,Object> filters = new HashMap<String, Object>();
		FilterComposite composite = null;
		Object filterInput = null;
		Class queryTargetJavaClass = classData.getTargetJavaClass();
		
		for (Map.Entry<String,FilterComposite> entry : composites.entrySet()) {
			attributeData = classData.getAttribute(entry.getKey());
			composite = entry.getValue();
			filterInput = composite.getFilterData();
			if (filterInput != null) {
				entry.getValue().saveSettings();
				filters.put(attributeData.getSetterMethodName(),filterInput);
			}
		}
		return QueryBuilderAdapter.getFilters(queryTargetJavaClass.getName(),filters);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	public void setFocus() {
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	
	public void onDispose() {
		for (FilterComposite composite : this.composites.values()) {
			composite.saveSettings();
		}
		toolkit.dispose();
		//super.dispose();
	}
	
	


}
