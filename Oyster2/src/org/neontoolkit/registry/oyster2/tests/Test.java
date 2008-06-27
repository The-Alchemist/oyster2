package org.neontoolkit.registry.oyster2.tests;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.core.OMVOntologyDomain;
import org.neontoolkit.omv.api.core.OMVPerson;
import org.neontoolkit.omv.api.extensions.mapping.OMVMapping;
import org.neontoolkit.omv.api.extensions.mapping.OMVMappingMethod;
import org.neontoolkit.omv.api.extensions.peer.OMVPeer;
import org.neontoolkit.registry.api.Oyster2Connection;
import org.neontoolkit.registry.api.Oyster2Manager;






public class Test {
	
	public static void main(String[] args)throws Exception{
        
		
		//String[] arguments = new String[] {"-startKAON2", "-KAON2Path=\"O2ServerFiles\\kaon2.jar\"", "-serverRootPath=\"server\"","-preferenceFile=testingOyster"};
		//StartServer.main(arguments);

		OMVOntologyDomain topic = new OMVOntologyDomain();
		topic.setURI("http://daml.umbc.edu/ontologies/topic-ont#Top/Arts");
		OMVOntologyDomain kritem = new OMVOntologyDomain();
		kritem.setURI("http://daml.umbc.edu/ontologies/topic-ont#Top/Reference/Knowledge_Management/Knowledge_Representation");
		
		//NEW CONNECTION
		//Oyster2Connection oyster2Conn = Oyster2Manager.getConnection();
		Oyster2Connection oyster2Conn = Oyster2Manager.newConnection(true);
		//Oyster2Connection oyster2Conn = Oyster2Manager.newConnection(false,"F:\\My Documents\\Oyster2APIv0.95\\new store");
		//Oyster2Connection oyster2Conn = Oyster2Manager.newConnection("C:\\Archivos de programa\\Java\\jdk1.5.0_07\\test\\new store", "C:\\Archivos de programa\\Java\\jdk1.5.0_07\\test\\kaon2.jar","C:\\Archivos de programa\\Java\\jdk1.5.0_07\\test\\server","-ms256M -mx256M -DentityExpansionLimit=8000000" );
		//Oyster2Connection oyster2Conn = Oyster2Manager.newConnection("F:\\My Documents\\Oyster2APIv0.96\\new store", "F:\\My Documents\\Oyster2APIv0.96\\Oyster2\\kaon2.jar","F:\\My Documents\\Oyster2APIv0.96\\server","-ms256M -mx256M -DentityExpansionLimit=8000000" );
		
		if (oyster2Conn==null) shutdown();
		
		//HERE WE TEST IMPORT METHOD
		oyster2Conn.importOntology("F:\\myTestWatson.owl");//wine.rdf");
		
		//IMPORTED ONTOLOGY
		OMVOntology newOnto1 = new OMVOntology();
		newOnto1.addName("testUseImport");
		newOnto1.setURI("http://localhost/testuseimport#");
		newOnto1.setResourceLocator("http://localhost/testuseimport/thefile.owl");
		newOnto1.addHasDomain(topic);
		newOnto1.setNumberOfAxioms(50);
		oyster2Conn.replace(newOnto1);
		
		//IMPORTING ONTOLOGY
		OMVOntology newOnto = new OMVOntology();
		newOnto.addName("testing");
		newOnto.addName("Probare");
		newOnto.setVersion("1.0");
		newOnto.setURI("http://localhost/temp#");
		//newOnto.setNumClasses(20);
		OMVOntology imp = new OMVOntology();
		imp.setURI("http://localhost/testuseimport#");
		newOnto.addUseImports(imp);
		newOnto.addHasDomain(topic);
		OMVOntology cTemp = new OMVOntology();
		cTemp.setURI("http://localhost/temp#");
		OMVPerson creator= new OMVPerson();
		creator.setFirstName("Raul");
		creator.setLastName("Palma");
		creator.addEmail("rpalma@fi.upm.es");
		creator.addEmail("rpalma@delicias.dia.fi.upm.es");
		creator.addCreatesOntology(cTemp);
		newOnto.addHasCreator(creator);
		OMVPerson creator1= new OMVPerson();
		creator1.setFirstName("Jens");
		creator1.setLastName("Hartmann");
		creator1.addEmail("hartmann@aifb-uka.de");
		creator1.addCreatesOntology(cTemp);
		newOnto.addHasCreator(creator1);
		newOnto.setResourceLocator("http://www.w3.org/testing/wine.rdf");
		newOnto.addNaturalLanguage("English");
		newOnto.addNaturalLanguage("Italian");
		//newOnto.addResourceLocator("http://www.w3.org/TR/owl-guide/wine.rdf");
		//newOnto.addResourceLocator("http://www.w3.org/testing/wine.rdf");
		newOnto.setNotes("Notes for testing");
		newOnto.setIsConsistentAccordingToReasoner(false);
		newOnto.setContainsTBox(true);
		newOnto.addEndorsedBy(creator);
		newOnto.setNumberOfClasses(50);
		oyster2Conn.replace(newOnto);
		
		
		//FULL ONTOLOGY
		OMVOntology newOnto0 = new OMVOntology();
		newOnto0.addName("OWL Ontology Definition Metamodel");
		newOnto0.addName("OWL OntoDefModel");
		newOnto0.setURI("http://owlodm.ontoware.org/OWL1.0");
		newOnto0.addHasDomain(kritem);
		newOnto0.setNumberOfClasses(35);
		newOnto0.setNumberOfProperties(22);
		OMVOntology cTempx = new OMVOntology();
		cTempx.setURI("http://owlodm.ontoware.org/OWL1.0");
		OMVPerson creator0= new OMVPerson();
		creator0.setFirstName("Peter");
		creator0.setLastName("Haase");
		creator0.addEmail("pha@aifb-uka.de");
		creator0.addCreatesOntology(cTempx);
		newOnto0.addHasCreator(creator0);
		newOnto0.setAcronym("OWLODM");
		newOnto0.setVersion("1.0");
		newOnto0.setResourceLocator("http://ontoware.org/frs/download.php/307/owl10.owl");
		newOnto0.setDescription("OWL Object Definition Metamodel (ODM) allows interoperability of OWL ontologies with MOF-compatible software environments");
		oyster2Conn.replace(newOnto0);
		
		//HERE WE TEST SIMPLE SEARCH
		Set<OMVOntology> OMVSet = oyster2Conn.getOntologies();
		String OMVSetSerial = Oyster2Manager.serializeOMVOntologies(OMVSet);
		System.out.println("Search: ");
		System.out.println(OMVSetSerial);
		
		// HERE WE WILL TEST SEARCH BY CONDITIONS
		OMVOntology conditions = new OMVOntology();
		//conditions.setName("wine");
		//conditions.setVersion("1.0");
		//conditions.setURI("http://localhost/temp#");
		//conditions.setNumClasses(20);
		//conditions.addUseImports(imp);
		conditions.addHasDomain(topic);
		OMVOntology cTemp1 = new OMVOntology();
		cTemp1.addName("test");
		//conditions.addUseImports(cTemp1);
		
		Set<OMVOntology> OMVSet2 = new HashSet<OMVOntology>();
		Set<Object> OMVOb1 = oyster2Conn.search(conditions);
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
		String OMVSetSerial2 = Oyster2Manager.serializeOMVOntologies(OMVSet2);
		System.out.println("Search with conditions: ");
		System.out.println(OMVSetSerial2);
		
		//HERE WE WILL TEST SEARCH BY CONDITIONS2
		OMVOntology conditionsx = new OMVOntology();
		conditionsx.setResourceLocator("wine");
		conditionsx.setContainsTBox(true);
		conditionsx.setNumberOfClasses(35);
		//conditionsx.setNumberOfProperties(5);
		conditionsx.setNotes("notes");
		conditionsx.addEndorsedBy(creator);
		conditionsx.addNaturalLanguage("english");
		conditionsx.addNaturalLanguage("italian");
		conditionsx.addName("proba");
		
		Set<OMVOntology> OMVSetx = new HashSet<OMVOntology>(); 
		Set<Object> OMVOb2 = oyster2Conn.search(conditionsx);
		if (OMVOb2.size()>0){
			Iterator it = OMVOb2.iterator();
			try{
				while(it.hasNext()){
					OMVOntology omv = (OMVOntology)it.next();
					OMVSetx.add(omv);
				}
				
			}catch(Exception ignore){
				//	-- ignore
			}
		}
		
		String OMVSetSerialx = Oyster2Manager.serializeOMVOntologies(OMVSetx);
		System.out.println("Search with conditionsx: ");
		System.out.println(OMVSetSerialx);
		
//		HERE WE WILL TEST SEARCH BY CONDITIONS3
		OMVOntology conditionsx3 = new OMVOntology();
		OMVOntology imp3 = new OMVOntology();
		imp3.setURI("http://localhost/testuseimport#");
		conditionsx3.addUseImports(imp3);
		
		
		Set<OMVOntology> OMVSetx3 = new HashSet<OMVOntology>(); 
		Set<Object> OMVObx3 = oyster2Conn.search(conditionsx3);
		if (OMVObx3.size()>0){
			Iterator it = OMVObx3.iterator();
			try{
				while(it.hasNext()){
					OMVOntology omv = (OMVOntology)it.next();
					OMVSetx3.add(omv);
				}
				
			}catch(Exception ignore){
				//	-- ignore
			}
		}
		
		String OMVSetSerialx3 = Oyster2Manager.serializeOMVOntologies(OMVSetx3);
		System.out.println("Search with conditionsx by useimports: ");
		System.out.println(OMVSetSerialx3);
		
		
		
		//HERE WE TEST GETPEERS METHOD
		Map<String,OMVPeer> OMVPeerSet = oyster2Conn.getPeers();
		String OMVPeerSetSerial = Oyster2Manager.serializeOMVPeers(OMVPeerSet);
		System.out.println("Peers: ");
		System.out.println(OMVPeerSetSerial);
		
		//HERE WE TEST GETPEEREXPERTISE METHOD
		Set<OMVOntology> expertise = new HashSet <OMVOntology>();
		Set keySet = OMVPeerSet.keySet();
		Collection keyValues = OMVPeerSet.values();
		Iterator itKey = keySet.iterator();
		Iterator it = keyValues.iterator();
		try{
			while(itKey.hasNext()){
				expertise = oyster2Conn.getPeerExpertise((String)itKey.next(), (OMVPeer)it.next());
				String OMVSerial = Oyster2Manager.serializeOMVOntologies(expertise);
				System.out.println("The Expertise: ");
				System.out.println(OMVSerial);
			}
		}catch(Exception e){
			System.out.println(e.toString()+" Search Problem in getpeerExpertise");
		}
		
		// HERE WE TEST SEARCH BY DATE
		GregorianCalendar first = new GregorianCalendar(2007, Calendar.APRIL, 1);    
	    Date d = first.getTime();
		
	    Set<OMVOntology> OMVSet1 = oyster2Conn.getOntologies(d);
		String OMVSetSerial1 = Oyster2Manager.serializeOMVOntologies(OMVSet1);
		System.out.println("The search by date: ");
		System.out.println(OMVSetSerial1);
		
		//HERE WE TEST generateOMV2RDFFile
		
		newOnto.generateOMV2RDFFile("miTest.rdf");
		newOnto1.generateOMV2RDFFile("C:\\miTestImported.rdf");
		System.out.println();
		//TEST MAPPING
		OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter mMethod=new OMVMappingMethod.OMVMappingCompoundMethod.OMVMappingFilter();
		mMethod.setID("superFilter");
		
		OMVMapping nMapping = new OMVMapping();
		nMapping.setURI("http://mydomain.com/map1.rdf");
		nMapping.setCreationDate("20/3/2007");
		nMapping.setLevel("draft");
		nMapping.setProcessingTime(5.33);
		nMapping.addHasCreator(creator);
		nMapping.setHasSourceOntology(newOnto);
		nMapping.setHasTargetOntology(newOnto1);
		nMapping.setUsedMethod(mMethod);
		oyster2Conn.replace(nMapping);
		
		OMVOntology oSource=new OMVOntology();
		oSource.setURI("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#");
		OMVMapping nMapping1 = new OMVMapping();
		nMapping1.setURI("http://mydomain.com/map2.rdf");
		nMapping1.setCreationDate("25/11/2006");
		nMapping1.setLevel("final");
		nMapping1.setProcessingTime(2.95);
		nMapping1.addHasCreator(creator1);
		nMapping1.setHasSourceOntology(oSource);
		nMapping1.setHasTargetOntology(newOnto);
		nMapping1.setUsedMethod(mMethod);
		oyster2Conn.replace(nMapping1);

		
		//HERE WE TEST SIMPLE MAPPING SEARCH
		Set<OMVMapping> OMVSetM = oyster2Conn.getMappings();
		String OMVSetSerialM = Oyster2Manager.serializeOMVMappings(OMVSetM);
		System.out.println("Search Mappings: ");
		System.out.println(OMVSetSerialM);
		
		//HERE WE TEST SEARCH MAPPING WITH CONDITIONS
		
		OMVMapping mSource=new OMVMapping();
		mSource.setHasSourceOntology(oSource);
		Set<OMVMapping> OMVSetM1 = new HashSet<OMVMapping>() ;
		Set<Object> OMVOb3 = oyster2Conn.search(mSource);
		if (OMVOb3.size()>0){
			Iterator it1 = OMVOb3.iterator();
			try{
				while(it1.hasNext()){
					OMVMapping omv = (OMVMapping)it1.next();
					OMVSetM1.add(omv);
				}
				
			}catch(Exception ignore){
				//	-- ignore
			}
		}
		
		
		String OMVSetSerialM1 = Oyster2Manager.serializeOMVMappings(OMVSetM1);
		System.out.println("Search Mappings with condition (source=http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#):");
		System.out.println(OMVSetSerialM1);
		
		//HERE WE TEST SEARCH WITH KEYWORDS
		
		Set<OMVOntology> OMVSet3 = oyster2Conn.getOntologies("italian");
		String OMVSetSerial3 = Oyster2Manager.serializeOMVOntologies(OMVSet3);
		System.out.println("Search with keyword: ");
		System.out.println(OMVSetSerial3);
		
		//HERE WE TEST SUBMIT ADHOC QUERIES
		Set<OMVOntology> OMVRet = new HashSet<OMVOntology>();
		Set<Object> OMVSet4 = oyster2Conn.submitAdHocQuery("SELECT ?x WHERE  { ?x rdf:type <http://omv.ontoware.org/2005/05/ontology#Ontology> . ?x <http://omv.ontoware.org/2005/05/ontology#resourceLocator> ?v0 . FILTER regex(?v0, \"wine\",\"i\") }");
		Iterator it4 = OMVSet4.iterator();
		try{
			while(it4.hasNext()){
				OMVOntology omv = (OMVOntology)it4.next();
				OMVRet.add(omv);
			}
		}catch(Exception ignore){
			//	-- ignore
		}
		String OMVSetSerial4 = Oyster2Manager.serializeOMVOntologies(OMVRet);
		System.out.println("Submit adhoc query: ");
		System.out.println(OMVSetSerial4);
		
		//HERE WE TEST SUBMIT ADHOC QUERIES
		Set<OMVMapping> OMVRet1 = new HashSet<OMVMapping>();
		Set<Object> OMVSet5 = oyster2Conn.submitAdHocQuery("SELECT ?x WHERE  { ?x rdf:type <http://omv.ontoware.org/2007/05/mappingomv#Mapping> . ?x <http://omv.ontoware.org/2007/05/mappingomv#hasSourceOntology> ?r0 . ?r0 <http://omv.ontoware.org/2005/05/ontology#URI> ?v0 . FILTER regex(?v0, \"http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#\",\"i\") }");
		Iterator it5 = OMVSet5.iterator();
		try{
			while(it5.hasNext()){
				OMVMapping omv = (OMVMapping)it5.next();
				OMVRet1.add(omv);
			}
		}catch(Exception ignore){
			//	-- ignore
		}
		String OMVSetSerial5 = Oyster2Manager.serializeOMVMappings(OMVRet1);
		System.out.println("Submit adhoc query(mapping): ");
		System.out.println(OMVSetSerial5);
		
		//oyster2Conn.remove(newOnto0);
		//oyster2Conn.remove(nMapping);

		//ADD Other objects
		OMVPerson t1= new OMVPerson();
		t1.setFirstName("John");
		t1.setLastName("becker");
		t1.addEmail("jbecker@superhost.com");
		t1.addEmail("jbeckersuper@thehost.com");
		t1.addEmail("jbhost@temphost.com");
		oyster2Conn.replace(t1);
		
		//HERE WE TEST SIMPLE SEARCH
		Set<OMVOntology> OMVSetd = oyster2Conn.getOntologies();
		String OMVSetSeriald = Oyster2Manager.serializeOMVOntologies(OMVSetd);
		System.out.println("Search after delete: ");
		System.out.println(OMVSetSeriald);
		
		//HERE WE TEST SIMPLE MAPPING SEARCH
		Set<OMVMapping> OMVSetMd = oyster2Conn.getMappings();
		String OMVSetSerialMd = Oyster2Manager.serializeOMVMappings(OMVSetMd);
		System.out.println("Search Mappings after delete: ");
		System.out.println(OMVSetSerialMd);
		
		
		//CLOSE CONNECTION
		Oyster2Manager.closeConnection();
		shutdown();
    }
	
	private static void shutdown(){
		System.exit(0);
	}
}
	
/*
Oyster2Connection oyster2Conn = Oyster2Manager.newConnection();
Set<OMVOntology> OMVSet = oyster2Conn.search();
String OMVSetSerial = Oyster2Manager.serializeOMV(OMVSet);
System.out.println(OMVSetSerial);
*/