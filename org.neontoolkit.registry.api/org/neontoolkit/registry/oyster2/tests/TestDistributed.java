package org.neontoolkit.registry.oyster2.tests;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.core.OMVOntologyDomain;
import org.neontoolkit.omv.api.core.OMVPerson;
import org.neontoolkit.omv.api.extensions.mapping.OMVMapping;
import org.neontoolkit.registry.api.Oyster2Connection;
import org.neontoolkit.registry.api.Oyster2Manager;
import org.neontoolkit.registry.oyster2.Oyster2Query;
import org.semanticweb.kaon2.api.DefaultOntologyResolver;
import org.semanticweb.kaon2.api.KAON2Connection;
import org.semanticweb.kaon2.api.KAON2Manager;
import org.semanticweb.kaon2.api.Namespaces;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.owl.elements.Individual;
import org.semanticweb.kaon2.api.reasoner.Query;
import org.semanticweb.kaon2.api.reasoner.Reasoner;






public class TestDistributed {
	
	public static void main(String[] args)throws Exception{
        

		if (args[0].equalsIgnoreCase("0")){
			KAON2Connection connection=KAON2Manager.newConnection();
			DefaultOntologyResolver resolver=new DefaultOntologyResolver();
	        resolver.registerReplacement("http://localhost/localRegistry","file:f://localRegistry.owl");
	        resolver.registerReplacement("http://omv.ontoware.org/2007/05/mappingomv","http://omv.ontoware.org/2007/05/mappingomv");
	        resolver.registerReplacement("http://omv.ontoware.org/2007/05/pomv","http://omv.ontoware.org/2007/05/pomv");
	        resolver.registerReplacement("http://omv.ontoware.org/2007/07/OWLChanges","http://omv.ontoware.org/2007/07/OWLChanges");
	        resolver.registerReplacement("http://omv.ontoware.org/2007/07/workflow","http://omv.ontoware.org/2007/07/workflow");
	        resolver.registerReplacement("http://daml.umbc.edu/ontologies/topic-ont#","http://oyster2.ontoware.org/dmozT.rdf");
	        connection.setOntologyResolver(resolver);
	        Ontology ontology=connection.openOntology("http://localhost/localRegistry",new HashMap<String,Object>());
	        
			String queryStr="SELECT ?x WHERE  { ?x rdf:type <http://omv.ontoware.org/2005/05/ontology#Ontology> }";
			Reasoner reasoner=ontology.createReasoner();
			Query query=reasoner.createQuery(Namespaces.INSTANCE,queryStr);
			query.open();
			while (!query.afterLast()) {					
				//System.out.println("ontologyURI Or Whatever: "+query.tupleBuffer()[0].toString());
				String docURI = query.tupleBuffer()[0].toString();
				Individual indiv =KAON2Manager.factory().individual(docURI);
				System.out.println(indiv.getURI());
			}
			System.exit(0);
		}
		//String[] arguments = new String[] {"-startKAON2", "-KAON2Path=\"O2ServerFiles\\kaon2.jar\"", "-serverRootPath=\"server\"","-preferenceFile=testingOyster"};
		//StartServer.main(arguments);

		OMVOntologyDomain topic = new OMVOntologyDomain();
		topic.setURI("http://daml.umbc.edu/ontologies/topic-ont#Top/Arts");
		OMVOntologyDomain kritem = new OMVOntologyDomain();
		kritem.setURI("http://daml.umbc.edu/ontologies/topic-ont#Top/Reference/Knowledge_Management/Knowledge_Representation");
		
		//NEW CONNECTION
		
		Oyster2Manager.setSimplePeer(true);
		Oyster2Manager.setWorkflowSupport(false);
		Oyster2Manager.setLogEnabled(true);
		Oyster2Connection oyster2Conn = Oyster2Manager.newConnection(false);
		//Oyster2Connection oyster2Conn = Oyster2Manager.newConnection(false,"F:\\My Documents\\Oyster2APIv0.95\\new store");
		//Oyster2Connection oyster2Conn = Oyster2Manager.newConnection("C:\\Archivos de programa\\Java\\jdk1.5.0_07\\test\\new store", "C:\\Archivos de programa\\Java\\jdk1.5.0_07\\test\\kaon2.jar","C:\\Archivos de programa\\Java\\jdk1.5.0_07\\test\\server","-ms256M -mx256M -DentityExpansionLimit=8000000" );
		//Oyster2Connection oyster2Conn = Oyster2Manager.newConnection("F:\\My Documents\\Oyster2APIv0.96\\new store", "F:\\My Documents\\Oyster2APIv0.96\\Oyster2\\kaon2.jar","F:\\My Documents\\Oyster2APIv0.96\\server","-ms256M -mx256M -DentityExpansionLimit=8000000" );
		
		if (oyster2Conn==null) shutdown();
		
		if (args[0].equalsIgnoreCase("1")){
			//HERE WE TEST SEARCH WITH KEYWORDS DISTRIBUTED
			Oyster2Manager.setQueryTimeOut(350000);
			Set<OMVOntology> OMVSet3Dist = oyster2Conn.getOntologies("resource",Oyster2Query.Auto_Scope,null);
			String OMVSetSerial3Dist = Oyster2Manager.serializeOMVOntologies(OMVSet3Dist);
			System.out.println("Search with keyword distributed: ");
			System.out.println(OMVSetSerial3Dist);
		}else if (args[0].equalsIgnoreCase("2")){
			//HERE WE TEST SIMPLE SEARCH DISTRIBUTED
			Set<OMVOntology> OMVSetDistGet = oyster2Conn.getOntologies(Oyster2Query.Auto_Scope,null);
			String OMVSetSerialDistGet = Oyster2Manager.serializeOMVOntologies(OMVSetDistGet);
			System.out.println("Search (get ontologies) distributed: ");
			System.out.println(OMVSetSerialDistGet);
		}
		else if (args[0].equalsIgnoreCase("3")){
			//HERE WE TEST SIMPLE SEARCH Local
			Set<OMVOntology> OMVSetDistGet = oyster2Conn.getOntologies();
			String OMVSetSerialDistGet = Oyster2Manager.serializeOMVOntologies(OMVSetDistGet);
			System.out.println("Search (get ontologies) local: ");
			System.out.println(OMVSetSerialDistGet);
		}
		else{
		
		//HERE WE TEST SIMPLE SEARCH DISTRIBUTED
		/*Set<OMVOntology> OMVSetOntDist = new HashSet<OMVOntology>();
		OMVOntology conditionsDist= new OMVOntology();
		Set<Object> OMVSetDist = oyster2Conn.search(conditionsDist, Oyster2Query.Auto_Scope, null);
		
		if (OMVSetDist.size()>0){
			Iterator it = OMVSetDist.iterator();
			try{
				while(it.hasNext()){
					OMVOntology omv = (OMVOntology)it.next();
					OMVSetOntDist.add(omv);
				}
				
			}catch(Exception ignore){
				//	-- ignore
			}
		}
		String OMVSetSerialDist = Oyster2Manager.serializeOMVOntologies(OMVSetOntDist);
		System.out.println("Search with conditions (distributed): ");
		System.out.println(OMVSetSerialDist);
		*/
		
		//HERE WE TEST SIMPLE SEARCH DISTRIBUTED2
		Set<OMVOntology>OMVSetOntDist = new HashSet<OMVOntology>();
		OMVOntology conditionsDist= new OMVOntology();
		conditionsDist.addName("changes");
		OMVPerson rap=new OMVPerson();
		rap.setFirstName("Raul");
		conditionsDist.addHasCreator(rap);
		Set<Object> OMVSetDist = oyster2Conn.search(conditionsDist, Oyster2Query.Auto_Scope, null);
		
		if (OMVSetDist.size()>0){
			Iterator it = OMVSetDist.iterator();
			try{
				while(it.hasNext()){
					OMVOntology omv = (OMVOntology)it.next();
					OMVSetOntDist.add(omv);
				}
				
			}catch(Exception ignore){
				//	-- ignore
			}
		}
		String OMVSetSerialDist = Oyster2Manager.serializeOMVOntologies(OMVSetOntDist);
		System.out.println("Search with conditions (distributed)2: ");
		System.out.println(OMVSetSerialDist);
		
		
		//HERE WE TEST SIMPLE SEARCH DISTRIBUTED
		Set<OMVOntology> OMVSetDistGet = oyster2Conn.getOntologies(Oyster2Query.Auto_Scope,null);
		String OMVSetSerialDistGet = Oyster2Manager.serializeOMVOntologies(OMVSetDistGet);
		System.out.println("Search (get ontologies) distributed: ");
		System.out.println(OMVSetSerialDistGet);
		
		
		//HERE WE TEST SEARCH BY DATE DISTRIBUTED
		GregorianCalendar firstDist = new GregorianCalendar(2008, Calendar.MARCH, 1);    
	    Date dDist = firstDist.getTime();
		
	    Set<OMVOntology> OMVSet1Dist = oyster2Conn.getOntologies(dDist, Oyster2Query.Auto_Scope,null);
		String OMVSetSerial1Dist = Oyster2Manager.serializeOMVOntologies(OMVSet1Dist);
		System.out.println("The search by date distributed: ");
		System.out.println(OMVSetSerial1Dist);
		
		
		//HERE WE TEST SEARCH WITH KEYWORDS DISTRIBUTED
		
		Set<OMVOntology> OMVSet3Dist = oyster2Conn.getOntologies("change",Oyster2Query.Auto_Scope,null);
		String OMVSetSerial3Dist = Oyster2Manager.serializeOMVOntologies(OMVSet3Dist);
		System.out.println("Search with keyword distributed: ");
		System.out.println(OMVSetSerial3Dist);
		
		
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
		
		//HERE WE TEST SIMPLE MAPPING SEARCH
		Set<OMVMapping> OMVSetM = oyster2Conn.getMappings(Oyster2Query.Auto_Scope,null);
		String OMVSetSerialM = Oyster2Manager.serializeOMVMappings(OMVSetM);
		System.out.println("Search Mappings distributed: ");
		System.out.println(OMVSetSerialM);
		
		//HERE WE TEST SEARCH MAPPING WITH CONDITIONS
		
		OMVOntology oSource=new OMVOntology();
		oSource.setURI("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#");
		OMVMapping mSource=new OMVMapping();
		mSource.setHasSourceOntology(oSource);
		Set<OMVMapping> OMVSetM1 = new HashSet<OMVMapping>() ;
		Set<Object> OMVOb3 = oyster2Conn.search(mSource,Oyster2Query.Auto_Scope,null);
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
		System.out.println("Search Mappings distributed with condition (source=http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#):");
		System.out.println(OMVSetSerialM1);

		
		//HERE WE TEST SUBMIT ADHOC QUERIES
		Set<OMVMapping> OMVRet1 = new HashSet<OMVMapping>();
		Set<Object> OMVSet5 = oyster2Conn.submitAdHocQuery("SELECT ?x WHERE  { ?x rdf:type <http://omv.ontoware.org/2007/05/mappingomv#Mapping> . ?x <http://omv.ontoware.org/2007/05/mappingomv#hasSourceOntology> ?r0 . ?r0 <http://omv.ontoware.org/2005/05/ontology#URI> ?v0 . FILTER regex(?v0, \"http://localhost/temp#\",\"i\") }", Oyster2Query.Auto_Scope,null);
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
		System.out.println("Submit adhoc query distributed (mapping): ");
		System.out.println(OMVSetSerial5);
		}
		
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