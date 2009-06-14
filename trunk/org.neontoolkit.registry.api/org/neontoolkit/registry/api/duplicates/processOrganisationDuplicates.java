package org.neontoolkit.registry.api.duplicates;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.neontoolkit.omv.api.core.OMVOrganisation;


/**
 * The class processOrganisationDuplicates provides the methods to
 * process duplicates in OMV Organisation objects  
 * representing the result of a query 
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class processOrganisationDuplicates{
	
	static public Set<OMVOrganisation> mergeOrganisationDuplicates(Set<OMVOrganisation> OMVSet){
		Set<OMVOrganisation> OMVSet1 = new HashSet<OMVOrganisation>();
		Set<OMVOrganisation> OMVSet2 = new HashSet<OMVOrganisation>();
		Set<OMVOrganisation> OMVSetMerged = new HashSet<OMVOrganisation>();
		OMVSet1.addAll(OMVSet);
		OMVSet2.addAll(OMVSet);
		Iterator it1 = OMVSet1.iterator();
		while (it1.hasNext()){
			OMVOrganisation o1 = (OMVOrganisation)it1.next();
			Iterator it2 = OMVSet2.iterator();
			boolean match=false;
			while (it2.hasNext()){
				OMVOrganisation o2 = (OMVOrganisation)it2.next();
				if(o2!=o1 && o1.getName()!=null && o2.getName()!=null && o1.getName().equalsIgnoreCase(o2.getName())){					
					match=true;
					OMVOrganisation m= new OMVOrganisation();
					m=o1;
					
					//m.setURI(o1.getURI()); //TODO merge properties							
					//do not add twice the same merged object
					Iterator dup = OMVSetMerged.iterator(); 
					boolean add=true;
					while (dup.hasNext()){
						OMVOrganisation d = (OMVOrganisation)dup.next();
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