package org.neontoolkit.registry.api.duplicates;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.neontoolkit.omv.api.core.OMVOntologySyntax;


/**
 * The class processOSDuplicates provides the methods to
 * process duplicates in OMV Ontology Syntax objects  
 * representing the result of a query 
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class processOSDuplicates{
	
	static public Set<OMVOntologySyntax> mergeOSDuplicates(Set<OMVOntologySyntax> OMVSet){
		Set<OMVOntologySyntax> OMVSet1 = new HashSet<OMVOntologySyntax>();
		Set<OMVOntologySyntax> OMVSet2 = new HashSet<OMVOntologySyntax>();
		Set<OMVOntologySyntax> OMVSetMerged = new HashSet<OMVOntologySyntax>();
		OMVSet1.addAll(OMVSet);
		OMVSet2.addAll(OMVSet);
		Iterator it1 = OMVSet1.iterator();
		while (it1.hasNext()){
			OMVOntologySyntax o1 = (OMVOntologySyntax)it1.next();
			Iterator it2 = OMVSet2.iterator();
			boolean match=false;
			while (it2.hasNext()){
				OMVOntologySyntax o2 = (OMVOntologySyntax)it2.next();
				if(o2!=o1 && o1.getName()!=null && o2.getName()!=null && o1.getName().equalsIgnoreCase(o2.getName())){					
					match=true;
					OMVOntologySyntax m= new OMVOntologySyntax();
					m=o1;
					
					//m.setURI(o1.getURI()); //TODO merge properties							
					//do not add twice the same merged object
					Iterator dup = OMVSetMerged.iterator(); 
					boolean add=true;
					while (dup.hasNext()){
						OMVOntologySyntax d = (OMVOntologySyntax)dup.next();
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