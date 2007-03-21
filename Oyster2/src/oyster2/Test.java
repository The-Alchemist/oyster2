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
		Oyster2Connection oyster2Conn = Oyster2Manager.newConnection();
		
		//HERE WE TEST IMPORT METHOD
		oyster2Conn.importOntology("E:\\wine.rdf");
		
		//IMPORTED ONTOLOGY
		OMVOntology newOnto1 = new OMVOntology();
		newOnto1.setName("testUseImport");
		newOnto1.setURI("http://localhost/testuseimport#");
		newOnto1.addHasDomain(topic);
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