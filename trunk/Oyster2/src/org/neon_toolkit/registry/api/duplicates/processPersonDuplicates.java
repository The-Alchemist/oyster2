package org.neon_toolkit.registry.api.duplicates;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.neon_toolkit.omv.api.core.OMVPerson;


/**
 * The class processMappingDuplicates provides the methods to
 * process duplicates in OMV Mapping objects  
 * representing the result of a query 
 * @author Raul Palma
 * @version 1.0, March 2008
 */
public class processPersonDuplicates{
	
	static public Set<OMVPerson> mergePersonDuplicates(Set<OMVPerson> OMVSet){
		Set<OMVPerson> OMVSet1 = new HashSet<OMVPerson>();
		Set<OMVPerson> OMVSet2 = new HashSet<OMVPerson>();
		Set<OMVPerson> OMVSetMerged = new HashSet<OMVPerson>();
		OMVSet1.addAll(OMVSet);
		OMVSet2.addAll(OMVSet);
		Iterator it1 = OMVSet1.iterator();
		while (it1.hasNext()){
			OMVPerson o1 = (OMVPerson)it1.next();
			Iterator it2 = OMVSet2.iterator();
			boolean match=false;
			while (it2.hasNext()){
				OMVPerson o2 = (OMVPerson)it2.next();
				if(o2!=o1 && o1.getFirstName()!=null && o2.getFirstName()!=null && o1.getFirstName().equalsIgnoreCase(o2.getFirstName())){
				  if(o2!=o1 && o1.getLastName()!=null && o2.getLastName()!=null && o1.getLastName().equalsIgnoreCase(o2.getLastName())){	
					match=true;
					OMVPerson m= new OMVPerson();
					m=o1;
					
					//m.setURI(o1.getURI()); //TODO merge properties							
					//do not add twice the same merged object
					Iterator dup = OMVSetMerged.iterator(); 
					boolean add=true;
					while (dup.hasNext()){
						OMVPerson d = (OMVPerson)dup.next();
						if(m.getFirstName()!=null && d.getFirstName()!=null && m.getFirstName().equalsIgnoreCase(d.getFirstName())){
						  if(m.getLastName()!=null && d.getLastName()!=null && m.getLastName().equalsIgnoreCase(d.getLastName())){
							add=false;
						  }
						}
					}
					if (add) OMVSetMerged.add(m);
				  }
				}
			}
			if (!match) OMVSetMerged.add(o1);
		}
		return OMVSetMerged;
	}
	
}