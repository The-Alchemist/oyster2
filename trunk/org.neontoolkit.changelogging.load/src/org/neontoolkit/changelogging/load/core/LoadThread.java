package org.neontoolkit.changelogging.load.core;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.neontoolkit.changelogging.core.ReflectInNavigatorThread;
import org.neontoolkit.oyster.plugin.menu.actions.StartRegistry;
import org.neontoolkit.registry.api.Oyster2Connection;
import org.neontoolkit.registry.api.change.ChangeManagement;
import org.neontoolkit.registry.api.change.ChangeSynchronization;
import org.neontoolkit.registry.api.workflow.WorkflowManagement;
import org.neontoolkit.registry.oyster2.Constants;
import org.neontoolkit.registry.oyster2.Oyster2Factory;
import org.neontoolkit.workflow.api.Action;
import org.neontoolkit.omv.api.core.*;
import org.neontoolkit.omv.api.extensions.change.OMVChange;
import org.semanticweb.kaon2.api.DefaultOntologyResolver;
import org.semanticweb.kaon2.api.KAON2Connection;
import org.semanticweb.kaon2.api.KAON2Exception;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.OntologyChangeEvent;
import org.semanticweb.kaon2.api.owl.elements.Individual;
import org.semanticweb.kaon2.api.owl.elements.OWLClass;
import org.semanticweb.kaon2.api.owl.elements.ObjectProperty;


public class LoadThread implements Runnable {
	public Oyster2Connection oyster2Conn = null;
	static Shell shell;
	static String ontologyURI;
	static String projectName;
	static String selected;
	static boolean tracked;
	static OMVOntology omv = new OMVOntology();
	static ChangeManagement cMgmt= new ChangeManagement();
	static WorkflowManagement wMgmt = new WorkflowManagement();
	
	public LoadThread (Shell arg, String arg1, String arg2, String arg3){
		shell=arg;
		ontologyURI=arg1;
		projectName=arg2;
		selected=arg3;
	}
	
	public void run() {
		shell.getDisplay().asyncExec(new Runnable () {
            public void run () {	
            	Job exportJob = new Job("Loading...") {
            		protected IStatus run(IProgressMonitor monitor) {
            			monitor.beginTask("Loading...", 100);
            			try {
            				fixConceptNames();
            				oyster2Conn = StartRegistry.connection;
            				monitor.worked(10);
            				
            				KAON2Connection connection=KAON2Manager.newConnection();
            				DefaultOntologyResolver resolver=new DefaultOntologyResolver();
            				resolver.registerReplacement("http://localhost/registryChanges","file:"+convert2URI(serializeFileName(selected)));
            				connection.setOntologyResolver(resolver);
            				Ontology ontology=null;
            				try {
            					ontology=connection.openOntology("http://localhost/registryChanges",new HashMap<String,Object>());
            					fix(ontology);
            					//File reply = new File(serializeFileName(serializeFileName("c:\\test.owl")));
            					//ontology.saveOntology(OntologyFileFormat.OWL_RDF,reply,"ISO-8859-1");
            				} catch (KAON2Exception e) {
            					e.printStackTrace();
            				} catch (InterruptedException e) {
            					e.printStackTrace();
            				}
            				monitor.worked(20);
            				//IF ontology!=null
            				if (ontology!=null){
            					omv.setURI(ontologyURI);
            					omv.setResourceLocator("");
            					//System.out.println(ontologyURI);
            					
            					tracked=true;
            					if (!cMgmt.isTracked(omv)) {
            						tracked=false;
            						cMgmt.startTracking(omv);
            					}
            					//TESTING
            					//printTest(ontology, "FROM IMPORTED FILE");
            					
                				//Get current state of changes for this ontology locally
            					//Set<String> ids = new HashSet<String>();
                				Set<OMVOntology> tOntos = new HashSet<OMVOntology>();
                				Map<String,String> timestamps = new HashMap<String,String>();
                				Map<OMVOntology, Integer> sizeBefore = new HashMap<OMVOntology, Integer>();
                				
                				tOntos=oyster2Conn.getOntologiesWithChanges();
                				for (OMVOntology onto: tOntos){
                					//ids.add(oyster2Conn.getLastChangeIdFromLog(onto)); //NOT ANYMORE....
                					sizeBefore.put(onto, oyster2Conn.getChangesIds(onto).size());
                					
                					List<Action> allActions=oyster2Conn.getEntityActionsHistory(onto, null);
                					if (allActions.size()>0) {
                						Action lastAction = (Action)allActions.get(allActions.size()-1);
                						timestamps.put(getOntologyID(onto),lastAction.getTimestamp());
                					}
                					else timestamps.put(getOntologyID(onto),"");
                				}
                				monitor.worked(10);
                				
                				//COPY CHANGES LOCALLY
                				Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
                				Ontology localRegistry =mOyster2.getLocalAdvertInformer().getLocalRegistry();
                				ChangeSynchronization.SyncrhonizeChangesWithRegistry(ontology, KAON2Manager.factory().individual(getOntologyID(omv)), localRegistry);
            					monitor.worked(30);
                				
            					//TESTING
            					//printTest(localRegistry, "FROM LOCAL REGISTRY");
                				
                				//APPLY CHANGES IN NTK
                				
                				ReflectInNavigatorThread reflectIn = new ReflectInNavigatorThread(shell);
                				ReflectInNavigatorThread.oyster2Conn=StartRegistry.connection;
                				
                				//reflectIn.reflect(ids, tOntos);  //NOT ANYMORE...
                				reflectIn.reflectNew(sizeBefore);
                				
	            				monitor.worked(10);
	            				reflectIn.reflectActions(timestamps);
	            				monitor.worked(10);
                				
            					//closing....
            					Set<Ontology> ontos = new HashSet<Ontology>();
            					ontos.add(ontology);
            					try {
            						connection.closeOntologies(ontos);
            						connection.close();
            					} catch (KAON2Exception e) {					
            						e.printStackTrace();
            					}
            					if (!tracked) cMgmt.stopTracking(omv);
            				}
	            			monitor.worked(10);
            			}catch (Exception e) {
            				openMessage("Load failed");
            				e.printStackTrace();
            			}	
            			finally{
            				monitor.done();
            			}
            			return Status.OK_STATUS;
            		}
        		};
        		exportJob.setUser(true);
    			exportJob.schedule();
            }
        });		
	}
	
	public void fixConceptNames (){
	try
        {
        File file = new File(serializeFileName(selected));
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = "", oldtext = "";
        while((line = reader.readLine()) != null)
            {
            oldtext += line + "\r\n";
        }
        reader.close();
        // replace a word in a file
        String newtext = oldtext.replaceAll("AddIndividualObjectProperty", "AddInvidivualObjectProperty");
        newtext = newtext.replaceAll("RemoveIndividualObjectProperty", "RemoveInvidualObjectProperty");
       
        //To replace a line in a file
        //String newtext = oldtext.replaceAll("This is test string 20000", "blah blah blah");
       
        FileWriter writer = new FileWriter(serializeFileName(selected));
        writer.write(newtext);writer.close();
    }
    catch (IOException ioe)
        {
        ioe.printStackTrace();
    }
	}
	
	public void fix (Ontology onto){
		try {
			OWLClass person=KAON2Manager.factory().owlClass(Constants.OMVURI+Constants.personConcept);
			Set<Individual> people = person.getMemberIndividuals(onto);
			for (Individual i : people){
				ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(Constants.WORKFLOWURI + Constants.hasRole);
				addObjectProperty(i.getURI(), ontologyObjectProperty, Constants.OMVURI+Constants.personConcept,Constants.SubjectExpert, onto);
			}
		} catch (KAON2Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getOntologyID(OMVOntology o){
		String tURN="";
		if (o==null) return "";
		if (o.getURI()!=null){
			tURN=o.getURI();
			boolean hasVersion=false;
			if (o.getVersion()!=null){
				tURN=tURN+"?version="+o.getVersion();//+";";
				hasVersion=true;
			}
			if (o.getResourceLocator()!=null){
				if (!hasVersion) tURN=tURN+"?";
				else tURN=tURN+";";
				tURN=tURN+"location="+o.getResourceLocator();
			}
			tURN=tURN.replace(" ", "_");
		}
		return tURN;
	}
	
	public void openMessage(final String mess){
		shell.getDisplay().asyncExec(new Runnable() {
	           public void run() {
	        	   MessageDialog.openInformation(
           				shell,
           				"Change Capturing Load Plug-in",
           				mess);
	            }
		});
	}
	
	private static String serializeFileName(String filename){
		String seperator = System.getProperty("file.separator");
		filename = filename.replace(seperator.charAt(0),'/');
		return  filename;
	}
	
	private static String convert2URI(String filename){
		filename = filename.replace(" ","%20");
		return  filename;
	}
	
	public static void printTest(Ontology toCheck, String message){
		System.out.println();
		System.out.println("CHANGES "+message);
		System.out.println();
		List<OMVChange> listChanges=cMgmt.getTrackedChanges(omv, toCheck, "");
		Collections.reverse(listChanges);
		for (OMVChange change : listChanges){
			System.out.println(change.getClass().getSimpleName()+" "+change.getDate()+" "+change.getHasAuthor().iterator().next().getFirstName()+" "+change.getURI());
		}            	
		System.out.println();
		System.out.println("ACTIONS "+message);
		System.out.println();
		List<Action> listActions=wMgmt.getEntityActionsHistory(omv, toCheck, null);
		for (Action action : listActions){
			System.out.println(action.getClass().getSimpleName()+" "+action.getTimestamp()+" "+action.getPerformedBy().getFirstName()+" "+action.getURI());
		}           					
		//String changesString= Oyster2Manager.serializeOMVChanges(listChanges);
		//System.out.println(changesString);
	}
	
	private void addObjectProperty(String URI, ObjectProperty oP, String Concept, String targetURI, Ontology onto){
		
		List<OntologyChangeEvent> changes=new ArrayList<OntologyChangeEvent>();
		OWLClass oConcept = KAON2Manager.factory().owlClass(Concept);
		Individual oIndividual = KAON2Manager.factory().individual(URI);
		Individual targetIndividual = KAON2Manager.factory().individual(targetURI);
		try {
			if(onto.containsAxiom(KAON2Manager.factory().classMember(oConcept,oIndividual),true)){	
				Map<ObjectProperty,Set<Individual>> check=oIndividual.getObjectPropertyValues(onto);
				Set<Individual>checkValues=check.get(oP);
				if (checkValues==null || !checkValues.contains(targetIndividual))
					changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(oP,oIndividual,targetIndividual),OntologyChangeEvent.ChangeType.ADD));
			}
			if (changes.size()>0){ 
				onto.applyChanges(changes);
				//onto.persist();
			}
			changes.clear();
		} catch (KAON2Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	static final Comparator<Action> TIME_ORDER =
        new Comparator<Action>() {
		public int compare(Action e1, Action e2) {
			try {
				Date d1=DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).parse(e1.getTimestamp());
				Date d2=DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US).parse(e2.getTimestamp());
				return d1.compareTo(d2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		}
	};
}

//IOntologyContainer container = DatamodelPlugin.getDefault().getContainer(projectName);
//OntologyManager connection = container.getConnection();
