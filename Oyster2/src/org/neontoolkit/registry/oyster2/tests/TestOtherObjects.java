package org.neontoolkit.registry.oyster2.tests;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.core.OMVPerson;
import org.neontoolkit.omv.api.extensions.mapping.OMVMapping;
import org.neontoolkit.registry.api.Oyster2Connection;
import org.neontoolkit.registry.api.Oyster2Manager;
import org.neontoolkit.registry.oyster2.Oyster2Query;






public class TestOtherObjects {
	
	public static void main(String[] args)throws Exception{
        
		
				
		//NEW CONNECTION
		
		Oyster2Connection oyster2Conn = Oyster2Manager.newConnection(true);
		if (oyster2Conn==null) shutdown();				
				
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
		
		//HERE WE TEST SUBMIT ADHOC QUERIES
		System.out.println("Submit adhoc query(person): ");
		Set<Object> OMVSetPerson = oyster2Conn.submitAdHocQuery("SELECT ?x WHERE  { ?x rdf:type <http://omv.ontoware.org/2005/05/ontology#Person>  }");
		Iterator itPerson = OMVSetPerson.iterator();
		try{
			while(itPerson.hasNext()){
				OMVPerson omv = (OMVPerson)itPerson.next();
				System.out.println("firstname: "+omv.getFirstName());
				System.out.println("lastname: "+omv.getLastName());
			}
			System.out.println();
		}catch(Exception ignore){
			//	-- ignore
		}
		
		
		//HERE WE TEST SUBMIT ADHOC QUERIES DISTRIBUTED
		Set<OMVOntology> OMVRetDist = new HashSet<OMVOntology>();
		Set<Object> OMVSet4Dist = oyster2Conn.submitAdHocQuery("SELECT ?x WHERE  { ?x rdf:type <http://omv.ontoware.org/2005/05/ontology#Ontology> . ?x <http://omv.ontoware.org/2005/05/ontology#resourceLocator> ?v0 . FILTER regex(?v0, \"wine\",\"i\") }", Oyster2Query.Auto_Scope,null);
		Iterator it4Dist = OMVSet4Dist.iterator();
		try{
			while(it4Dist.hasNext()){
				OMVOntology omv = (OMVOntology)it4Dist.next();
				OMVRetDist.add(omv);
			}
		}catch(Exception ignore){
			//	-- ignore
		}
		String OMVSetSerial4Dist = Oyster2Manager.serializeOMVOntologies(OMVRetDist);
		System.out.println("Submit adhoc query distributed: ");
		System.out.println(OMVSetSerial4Dist);
		
		//HERE WE TEST SUBMIT ADHOC QUERIES
		Set<OMVMapping> OMVRetMap = new HashSet<OMVMapping>();
		Set<Object> OMVSetMap = oyster2Conn.submitAdHocQuery("SELECT ?x WHERE  { ?x rdf:type <http://omv.ontoware.org/2007/05/mappingomv#Mapping> . ?x <http://omv.ontoware.org/2007/05/mappingomv#hasSourceOntology> ?r0 . ?r0 <http://omv.ontoware.org/2005/05/ontology#URI> ?v0 . FILTER regex(?v0, \"http://localhost/temp#\",\"i\") }", Oyster2Query.Auto_Scope,null);
		Iterator itMap = OMVSetMap.iterator();
		try{
			while(itMap.hasNext()){
				OMVMapping omv = (OMVMapping)itMap.next();
				OMVRetMap.add(omv);
			}
		}catch(Exception ignore){
			//	-- ignore
		}
		String OMVSetSerialMap = Oyster2Manager.serializeOMVMappings(OMVRetMap);
		System.out.println("Submit adhoc query distributed (mapping): ");
		System.out.println(OMVSetSerialMap);
		
		
		//HERE WE TEST SUBMIT ADHOC QUERIES
		System.out.println("Submit adhoc query(person) distributed: ");
		Set<Object> OMVSetDistPerson = oyster2Conn.submitAdHocQuery("SELECT ?x WHERE  { ?x rdf:type <http://omv.ontoware.org/2005/05/ontology#Person> . ?x <http://omv.ontoware.org/2005/05/ontology#lastName> ?v0 . FILTER regex(?v0, \"Palma\",\"i\") }", Oyster2Query.Auto_Scope,null);
		Iterator itDistPerson = OMVSetDistPerson.iterator();
		try{
			while(itDistPerson.hasNext()){
				
				OMVPerson omv = (OMVPerson)itDistPerson.next();
				System.out.println("firstname: "+omv.getFirstName());
				System.out.println("lastname: "+omv.getLastName());
			}
			System.out.println();
			
		}catch(Exception ignore){
			//	-- ignore
		}
		
		
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
	