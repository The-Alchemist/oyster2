package org.neon_toolkit.registry.api.duplicates;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.neon_toolkit.omv.api.extensions.mapping.OMVMapping;

/**
 * The class processMappingDuplicates provides the methods to
 * process duplicates in OMV Mapping objects  
 * representing the result of a query 
 * @author Raul Palma
 * @version 1.0, March 2008
 */
public class processMappingDuplicates{
	
	static public Set<OMVMapping> mergeMappingDuplicates(Set<OMVMapping> OMVSet){
		Set<OMVMapping> OMVSet1 = new HashSet<OMVMapping>();
		Set<OMVMapping> OMVSet2 = new HashSet<OMVMapping>();
		Set<OMVMapping> OMVSetMerged = new HashSet<OMVMapping>();
		OMVSet1.addAll(OMVSet);
		OMVSet2.addAll(OMVSet);
		Iterator it1 = OMVSet1.iterator();
		while (it1.hasNext()){
			OMVMapping o1 = (OMVMapping)it1.next();
			Iterator it2 = OMVSet2.iterator();
			boolean match=false;
			while (it2.hasNext()){
				OMVMapping o2 = (OMVMapping)it2.next();
				if(o2!=o1 && o1.getURI()!=null && o2.getURI()!=null && o1.getURI().equalsIgnoreCase(o2.getURI())){					
					match=true;
					OMVMapping m= new OMVMapping();
					if (o1.getHasSourceOntology()!=null)
						m=o1;
					else
						m=o2;
					//m.setURI(o1.getURI()); //TODO merge properties							
					//do not add twice the same merged object
					Iterator dup = OMVSetMerged.iterator(); 
					boolean add=true;
					while (dup.hasNext()){
						OMVMapping d = (OMVMapping)dup.next();
						if(m.getURI()!=null && d.getURI()!=null && m.getURI().equalsIgnoreCase(d.getURI())){
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