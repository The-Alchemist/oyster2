package org.neon_toolkit.registry.api.duplicates;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.neon_toolkit.omv.api.core.OMVOntologyEngineeringMethodology;


/**
 * The class processMappingDuplicates provides the methods to
 * process duplicates in OMV Mapping objects  
 * representing the result of a query 
 * @author Raul Palma
 * @version 1.0, March 2008
 */
public class processOEMDuplicates{
	
	static public Set<OMVOntologyEngineeringMethodology> mergeOEMDuplicates(Set<OMVOntologyEngineeringMethodology> OMVSet){
		Set<OMVOntologyEngineeringMethodology> OMVSet1 = new HashSet<OMVOntologyEngineeringMethodology>();
		Set<OMVOntologyEngineeringMethodology> OMVSet2 = new HashSet<OMVOntologyEngineeringMethodology>();
		Set<OMVOntologyEngineeringMethodology> OMVSetMerged = new HashSet<OMVOntologyEngineeringMethodology>();
		OMVSet1.addAll(OMVSet);
		OMVSet2.addAll(OMVSet);
		Iterator it1 = OMVSet1.iterator();
		while (it1.hasNext()){
			OMVOntologyEngineeringMethodology o1 = (OMVOntologyEngineeringMethodology)it1.next();
			Iterator it2 = OMVSet2.iterator();
			boolean match=false;
			while (it2.hasNext()){
				OMVOntologyEngineeringMethodology o2 = (OMVOntologyEngineeringMethodology)it2.next();
				if(o2!=o1 && o1.getName()!=null && o2.getName()!=null && o1.getName().equalsIgnoreCase(o2.getName())){					
					match=true;
					OMVOntologyEngineeringMethodology m= new OMVOntologyEngineeringMethodology();
					m=o1;
					
					//m.setURI(o1.getURI()); //TODO merge properties							
					//do not add twice the same merged object
					Iterator dup = OMVSetMerged.iterator(); 
					boolean add=true;
					while (dup.hasNext()){
						OMVOntologyEngineeringMethodology d = (OMVOntologyEngineeringMethodology)dup.next();
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