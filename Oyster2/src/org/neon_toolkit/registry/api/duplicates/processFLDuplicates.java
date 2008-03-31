package org.neon_toolkit.registry.api.duplicates;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.neon_toolkit.omv.api.core.OMVFormalityLevel;


/**
 * The class processFLDuplicates provides the methods to
 * process duplicates in OMV Formality Level objects  
 * representing the result of a query 
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class processFLDuplicates{
	
	static public Set<OMVFormalityLevel> mergeFLDuplicates(Set<OMVFormalityLevel> OMVSet){
		Set<OMVFormalityLevel> OMVSet1 = new HashSet<OMVFormalityLevel>();
		Set<OMVFormalityLevel> OMVSet2 = new HashSet<OMVFormalityLevel>();
		Set<OMVFormalityLevel> OMVSetMerged = new HashSet<OMVFormalityLevel>();
		OMVSet1.addAll(OMVSet);
		OMVSet2.addAll(OMVSet);
		Iterator it1 = OMVSet1.iterator();
		while (it1.hasNext()){
			OMVFormalityLevel o1 = (OMVFormalityLevel)it1.next();
			Iterator it2 = OMVSet2.iterator();
			boolean match=false;
			while (it2.hasNext()){
				OMVFormalityLevel o2 = (OMVFormalityLevel)it2.next();
				if(o2!=o1 && o1.getName()!=null && o2.getName()!=null && o1.getName().equalsIgnoreCase(o2.getName())){					
					match=true;
					OMVFormalityLevel m= new OMVFormalityLevel();
					m=o1;
					
					//m.setURI(o1.getURI()); //TODO merge properties							
					//do not add twice the same merged object
					Iterator dup = OMVSetMerged.iterator(); 
					boolean add=true;
					while (dup.hasNext()){
						OMVFormalityLevel d = (OMVFormalityLevel)dup.next();
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