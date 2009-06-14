package org.neontoolkit.registry.api.duplicates;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.neontoolkit.omv.api.core.OMVOntologyType;


/**
 * The class processOTDuplicates provides the methods to
 * process duplicates in OMV Ontology type objects  
 * representing the result of a query 
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class processOTDuplicates{
	
	static public Set<OMVOntologyType> mergeOTDuplicates(Set<OMVOntologyType> OMVSet){
		Set<OMVOntologyType> OMVSet1 = new HashSet<OMVOntologyType>();
		Set<OMVOntologyType> OMVSet2 = new HashSet<OMVOntologyType>();
		Set<OMVOntologyType> OMVSetMerged = new HashSet<OMVOntologyType>();
		OMVSet1.addAll(OMVSet);
		OMVSet2.addAll(OMVSet);
		Iterator it1 = OMVSet1.iterator();
		while (it1.hasNext()){
			OMVOntologyType o1 = (OMVOntologyType)it1.next();
			Iterator it2 = OMVSet2.iterator();
			boolean match=false;
			while (it2.hasNext()){
				OMVOntologyType o2 = (OMVOntologyType)it2.next();
				if(o2!=o1 && o1.getName()!=null && o2.getName()!=null && o1.getName().equalsIgnoreCase(o2.getName())){					
					match=true;
					OMVOntologyType m= new OMVOntologyType();
					m=o1;
					
					//m.setURI(o1.getURI()); //TODO merge properties							
					//do not add twice the same merged object
					Iterator dup = OMVSetMerged.iterator(); 
					boolean add=true;
					while (dup.hasNext()){
						OMVOntologyType d = (OMVOntologyType)dup.next();
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