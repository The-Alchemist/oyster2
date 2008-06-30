package org.neontoolkit.oyster2.client.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.Properties;
import java.util.Set;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.semanticweb.kaon2.api.DefaultOntologyResolver;
import org.semanticweb.kaon2.api.Entity;

import org.semanticweb.kaon2.api.KAON2Exception;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.OntologyManager;


import org.semanticweb.kaon2.api.owl.elements.Individual;
import org.semanticweb.kaon2.api.owl.elements.OWLClass;

import com.ontoprise.config.IConfig;

public class Oyster2 {
	//private OntologyManager connection = null;
	
	private static Oyster2 sharedInstance = null;
	
	private static String TOPIC_ONTOLOGY_ROOT = "Top";
	
	private static String TOPIC_ONTOLOGY_KEY = "Top";
	
	private static String OMV_ONTOLOGY_KEY = "OMVOntology";
	
	private Map<String,Ontology> ontologies = null;
	
	private Map<IConfig.OntologyLanguage,OntologyManager> connections = null;
	
	static {
		try {
			sharedInstance = new Oyster2();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public static Oyster2 getSharedInstance() {
		return sharedInstance;
	}
	
	private Oyster2() throws Exception {
			
		ontologies = new HashMap<String, Ontology>();
		connections = new HashMap<IConfig.OntologyLanguage, OntologyManager>();
		
		
		//register omv ontology
		openOntology(OMV_ONTOLOGY_KEY, "OMV.owl",IConfig.OntologyLanguage.OWL);
		
		//		 register topic ontology
		openOntology(TOPIC_ONTOLOGY_KEY, "dmozT.rdf",IConfig.OntologyLanguage.OWL);
				
		
	}
	
	private void openOntology(String ontologyKey,String filename, 
			IConfig.OntologyLanguage language)
	throws IOException, KAON2Exception, InterruptedException {
		
		//get file
		Path filePath = new Path("resources" + File.separator + filename);

		URL fileURL = FileLocator.toFileURL(FileLocator.find(Activator.getDefault().getBundle(),
				filePath, null));
		File file = new File(fileURL.getFile());

		if (! file.exists()) {
			throw new RuntimeException("Couldn't find the ontology file " + filename);
		}
		try {
		
		DefaultOntologyResolver ontologyResolver = null;
		OntologyManager connection = connections.get(language);
		if (connection == null) {
			Properties properties = new Properties();
			properties.put(IConfig.ONTOLOGY_LANGUAGE, language);
			connection = KAON2Manager.newOntologyManager(properties);
			ontologyResolver = new DefaultOntologyResolver();
			connection.setOntologyResolver(ontologyResolver);
		}
		else {
			ontologyResolver = (DefaultOntologyResolver)connection.getOntologyResolver();
		}
		
		String ontologyURI = ontologyResolver.registerOntology(file);
		
		
		Ontology theOntology = connection.openOntology(ontologyURI, new HashMap<String, Object>());
		ontologies.put(ontologyKey,theOntology);
		}
		catch (Exception e ) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	public Ontology getTopicOntology() {
			return ontologies.get(TOPIC_ONTOLOGY_KEY);
	}
	
	
	public Entity getTopicOntologyRoot() {
		Ontology topicOntology = getTopicOntology();
		Entity topicRoot = KAON2Manager.factory().owlClass(
				topicOntology.getOntologyURI()+ TOPIC_ONTOLOGY_ROOT);
		return topicRoot;
	}
	
	public ArrayList<String> getOMVInstanceNames(String theClass) throws KAON2Exception {
		OWLClass clazz=KAON2Manager.factory().owlClass(theClass);
		Ontology OMVOntology = ontologies.get(OMV_ONTOLOGY_KEY);
		Set<Individual> instances = clazz.getMemberIndividuals(OMVOntology);
		
		ArrayList<String> instanceList = new ArrayList<String>();
		for (Individual individual : instances) {
			instanceList.add(individual.toString());
		}
		
		return instanceList;
	}
	

	public ArrayList<String>
		getOMVInstanceNames(String theClass, String namespaceToStrip)
			throws KAON2Exception {
		int lengthOfNamespace = namespaceToStrip.length();
		OWLClass clazz=KAON2Manager.factory().owlClass(theClass);
		Ontology OMVOntology = ontologies.get(OMV_ONTOLOGY_KEY);
		Set<Individual> instances = clazz.getMemberIndividuals(OMVOntology);
		
		ArrayList<String> instanceList = new ArrayList<String>();
		for (Individual individual : instances) {
			
			instanceList.add(individual.toString().substring(lengthOfNamespace));
		}
		
		return instanceList;
	}
}
