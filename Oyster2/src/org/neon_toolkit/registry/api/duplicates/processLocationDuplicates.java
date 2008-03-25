package org.neon_toolkit.registry.api.duplicates;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.neon_toolkit.omv.api.core.OMVLocation;


/**
 * The class processMappingDuplicates provides the methods to
 * process duplicates in OMV Mapping objects  
 * representing the result of a query 
 * @author Raul Palma
 * @version 1.0, March 2008
 */
public class processLocationDuplicates{
	
	static public Set<OMVLocation> mergeLocationDuplicates(Set<OMVLocation> OMVSet){
		Set<OMVLocation> OMVSet1 = new HashSet<OMVLocation>();
		Set<OMVLocation> OMVSet2 = new HashSet<OMVLocation>();
		Set<OMVLocation> OMVSetMerged = new HashSet<OMVLocation>();
		OMVSet1.addAll(OMVSet);
		OMVSet2.addAll(OMVSet);
		Iterator it1 = OMVSet1.iterator();
		while (it1.hasNext()){
			OMVLocation o1 = (OMVLocation)it1.next();
			Iterator it2 = OMVSet2.iterator();
			boolean match=false;
			while (it2.hasNext()){
				OMVLocation o2 = (OMVLocation)it2.next();
				if(o2!=o1 && o1.getStreet()!=null && o2.getStreet()!=null && o1.getStreet().equalsIgnoreCase(o2.getStreet())){					
					match=true;
					OMVLocation m= new OMVLocation();
					m=o1;
					
					//m.setURI(o1.getURI()); //TODO merge properties							
					//do not add twice the same merged object
					Iterator dup = OMVSetMerged.iterator(); 
					boolean add=true;
					while (dup.hasNext()){
						OMVLocation d = (OMVLocation)dup.next();
						if(m.getStreet()!=null && d.getStreet()!=null && m.getStreet().equalsIgnoreCase(d.getStreet())){
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