package oyster2;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import api.OMVOntology;
import api.OMVOntologyDomain;
import api.OMVPeer;
import api.Oyster2Manager;
import api.Oyster2Connection;


public class Test {
	
	public static void main(String[] args)throws Exception{
		OMVOntology conditions = new OMVOntology();
		Set<OMVOntology> expertise = new HashSet <OMVOntology>();
		conditions.setName("wine");
		conditions.setVersion("1.0");
		conditions.setURI("http://localhost/temp#");
		//conditions.setNumClasses(20);
		OMVOntology imp = new OMVOntology();
		imp.setURI("http://www.w3.org/TR/2003/PR-owl-guide-20031209/food");
		//conditions.addUseImports(imp);
		
		OMVOntologyDomain topic = new OMVOntologyDomain();
		topic.setURI("http://daml.umbc.edu/ontologies/topic-ont#Top/Arts");
		conditions.addHasDomain(topic);
		
		Oyster2Connection oyster2Conn = Oyster2Manager.newConnection();
		oyster2Conn.importOntology("F:\\wine.rdf");
		
		oyster2Conn.replace(conditions);
		Set<OMVOntology> OMVSet = oyster2Conn.search();
		String OMVSetSerial = Oyster2Manager.serializeOMV(OMVSet);
		System.out.println(OMVSetSerial);
		
		Map<String,OMVPeer> OMVPeerSet = oyster2Conn.getPeers();
		String OMVPeerSetSerial = Oyster2Manager.serializeOMVPeer(OMVPeerSet);
		System.out.println(OMVPeerSetSerial);
		
		Set keySet = OMVPeerSet.keySet();
		Collection keyValues = OMVPeerSet.values();
		Iterator itKey = keySet.iterator();
		Iterator it = keyValues.iterator();
		try{
			while(itKey.hasNext()){
				expertise = oyster2Conn.getPeerExpertise((String)itKey.next(), (OMVPeer)it.next());
				String OMVSerial = Oyster2Manager.serializeOMV(OMVSet);
				System.out.println("The Expertise: ");
				System.out.println(OMVSerial);
			}
		}catch(Exception e){
			System.out.println(e.toString()+" Search Problem in getpeerExpertise");
		}
		Oyster2Manager.closeConnection();
		shutdown();
    }
	
	private static void shutdown(){
		System.exit(0);
	}
}
	
