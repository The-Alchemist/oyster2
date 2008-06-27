package org.neontoolkit.registry.api.duplicates;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.neontoolkit.omv.api.core.OMVOntologyDomain;


/**
 * The class processODDuplicates provides the methods to
 * process duplicates in OMV Ontology Domain objects  
 * representing the result of a query 
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class processODDuplicates{
	
	static public Set<OMVOntologyDomain> mergeODDuplicates(Set<OMVOntologyDomain> OMVSet){
		Set<OMVOntologyDomain> OMVSet1 = new HashSet<OMVOntologyDomain>();
		Set<OMVOntologyDomain> OMVSet2 = new HashSet<OMVOntologyDomain>();
		Set<OMVOntologyDomain> OMVSetMerged = new HashSet<OMVOntologyDomain>();
		OMVSet1.addAll(OMVSet);
		OMVSet2.addAll(OMVSet);
		Iterator it1 = OMVSet1.iterator();
		while (it1.hasNext()){
			OMVOntologyDomain o1 = (OMVOntologyDomain)it1.next();
			Iterator it2 = OMVSet2.iterator();
			boolean match=false;
			while (it2.hasNext()){
				OMVOntologyDomain o2 = (OMVOntologyDomain)it2.next();
				if(o2!=o1 && o1.getURI()!=null && o2.getURI()!=null && o1.getURI().equalsIgnoreCase(o2.getURI())){					
					match=true;
					OMVOntologyDomain m= new OMVOntologyDomain();
					m=o1;
					
					//m.setURI(o1.getURI()); //TODO merge properties							
					//do not add twice the same merged object
					Iterator dup = OMVSetMerged.iterator(); 
					boolean add=true;
					while (dup.hasNext()){
						OMVOntologyDomain d = (OMVOntologyDomain)dup.next();
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