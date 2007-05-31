package oyster2;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;
//import java.io.File;
import api.*;





public class Test {
	
	public static void main(String[] args)throws Exception{
        
		OMVOntologyDomain topic = new OMVOntologyDomain();
		topic.setURI("http://daml.umbc.edu/ontologies/topic-ont#Top/Arts");
		
		//NEW CONNECTION
		//Oyster2Connection oyster2Conn = Oyster2Manager.newConnection("C:\\Archivos de programa\\Java\\jdk1.5.0_07\\test\\new store", "C:\\Archivos de programa\\Java\\jdk1.5.0_07\\test\\kaon2.jar","C:\\Archivos de programa\\Java\\jdk1.5.0_07\\test\\server","-ms256M -mx256M -DentityExpansionLimit=8000000" );
		Oyster2Connection oyster2Conn = Oyster2Manager.newConnection();
		
		
		//HERE WE TEST IMPORT METHOD
		oyster2Conn.importOntology("E:\\wine.rdf");
		
		//IMPORTED ONTOLOGY
		OMVOntology newOnto1 = new OMVOntology();
		newOnto1.setName("testUseImport");
		newOnto1.setURI("http://localhost/testuseimport#");
		newOnto1.addHasDomain(topic);
		newOnto1.setNumberOfAxioms(50);
		oyster2Conn.replace(newOnto1);
		
		//IMPORTING ONTOLOGY
		OMVOntology newOnto = new OMVOntology();
		newOnto.setName("testing");
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
		creator.setEMail("rpalma@fi.upm.es");
		creator.addCreatesOntology(cTemp);
		newOnto.addHasCreator(creator);
		OMVPerson creator1= new OMVPerson();
		creator1.setFirstName("Jens");
		creator1.setLastName("Hartmann");
		creator1.setEMail("hartmann@aifb-uka.de");
		creator1.addCreatesOntology(cTemp);
		newOnto.addHasCreator(creator1);
		newOnto.addResourceLocator("http://www.w3.org/TR/owl-guide/wine.rdf");
		newOnto.addResourceLocator("http://www.w3.org/testing/wine.rdf");
		oyster2Conn.replace(newOnto);
		
		//HERE WE TEST SIMPLE SEARCH
		Set<OMVOntology> OMVSet = oyster2Conn.search();
		String OMVSetSerial = Oyster2Manager.serializeOMV(OMVSet);
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
		cTemp1.setName("test");
		//conditions.addUseImports(cTemp1);
		
		Set<OMVOntology> OMVSet2 = oyster2Conn.search(conditions);
		String OMVSetSerial2 = Oyster2Manager.serializeOMV(OMVSet2);
		System.out.println("Search with conditions: ");
		System.out.println(OMVSetSerial2);
		
		//HERE WE WILL TEST SEARCH BY CONDITIONS2
		OMVOntology conditionsx = new OMVOntology();
		conditionsx.setResourceLocator("wine");
		
		Set<OMVOntology> OMVSetx = oyster2Conn.search(conditionsx);
		String OMVSetSerialx = Oyster2Manager.serializeOMV(OMVSetx);
		System.out.println("Search with conditionsx: ");
		System.out.println(OMVSetSerialx);
		
		//HERE WE TEST GETPEERS METHOD
		Map<String,OMVPeer> OMVPeerSet = oyster2Conn.getPeers();
		String OMVPeerSetSerial = Oyster2Manager.serializeOMVPeer(OMVPeerSet);
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
				String OMVSerial = Oyster2Manager.serializeOMV(expertise);
				System.out.println("The Expertise: ");
				System.out.println(OMVSerial);
			}
		}catch(Exception e){
			System.out.println(e.toString()+" Search Problem in getpeerExpertise");
		}
		
		// HERE WE TEST SEARCH BY DATE
		GregorianCalendar first = new GregorianCalendar(2007, Calendar.APRIL, 1);    
	    Date d = first.getTime();
		
	    Set<OMVOntology> OMVSet1 = oyster2Conn.search(d);
		String OMVSetSerial1 = Oyster2Manager.serializeOMV(OMVSet1);
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
		Set<OMVMapping> OMVSetM = oyster2Conn.SearchMappings();
		String OMVSetSerialM = Oyster2Manager.serializeOMVMapping(OMVSetM);
		System.out.println("Search Mappings: ");
		System.out.println(OMVSetSerialM);
		
		//HERE WE TEST SEARCH MAPPING WITH CONDITIONS
		
		OMVMapping mSource=new OMVMapping();
		mSource.setHasSourceOntology(oSource);
		Set<OMVMapping> OMVSetM1 = oyster2Conn.searchMappings(mSource);
		String OMVSetSerialM1 = Oyster2Manager.serializeOMVMapping(OMVSetM1);
		System.out.println("Search Mappings with condition (source=http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#):");
		System.out.println(OMVSetSerialM1);
		
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