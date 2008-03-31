package org.neon_toolkit.registry.api.duplicates;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.neon_toolkit.omv.api.core.OMVOntologyEngineeringTool;


/**
 * The class processOETDuplicates provides the methods to
 * process duplicates in OMV Ontology Engineering Tool objects  
 * representing the result of a query 
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class processOETDuplicates{
	
	static public Set<OMVOntologyEngineeringTool> mergeOETDuplicates(Set<OMVOntologyEngineeringTool> OMVSet){
		Set<OMVOntologyEngineeringTool> OMVSet1 = new HashSet<OMVOntologyEngineeringTool>();
		Set<OMVOntologyEngineeringTool> OMVSet2 = new HashSet<OMVOntologyEngineeringTool>();
		Set<OMVOntologyEngineeringTool> OMVSetMerged = new HashSet<OMVOntologyEngineeringTool>();
		OMVSet1.addAll(OMVSet);
		OMVSet2.addAll(OMVSet);
		Iterator it1 = OMVSet1.iterator();
		while (it1.hasNext()){
			OMVOntologyEngineeringTool o1 = (OMVOntologyEngineeringTool)it1.next();
			Iterator it2 = OMVSet2.iterator();
			boolean match=false;
			while (it2.hasNext()){
				OMVOntologyEngineeringTool o2 = (OMVOntologyEngineeringTool)it2.next();
				if(o2!=o1 && o1.getName()!=null && o2.getName()!=null && o1.getName().equalsIgnoreCase(o2.getName())){					
					match=true;
					OMVOntologyEngineeringTool m= new OMVOntologyEngineeringTool();
					m=o1;
					
					//m.setURI(o1.getURI()); //TODO merge properties							
					//do not add twice the same merged object
					Iterator dup = OMVSetMerged.iterator(); 
					boolean add=true;
					while (dup.hasNext()){
						OMVOntologyEngineeringTool d = (OMVOntologyEngineeringTool)dup.next();
						if(m.getName()!=null && d.getName()!=null && m.getName().equalsIgnoreCase(d.getName())){
							add=false;
						}
					}
					if (add) OMVSetMerged.add(m);
					
				}
			}
			if (!match) OMVSetMerged.add(o1);
		}
		return OMVSetMerged;
	}
	
}