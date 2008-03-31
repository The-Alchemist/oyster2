package org.neon_toolkit.registry.api.duplicates;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.neon_toolkit.omv.api.core.OMVLicenseModel;


/**
 * The class processLMDuplicates provides the methods to
 * process duplicates in OMV License Model objects  
 * representing the result of a query 
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class processLMDuplicates{
	
	static public Set<OMVLicenseModel> mergeLMDuplicates(Set<OMVLicenseModel> OMVSet){
		Set<OMVLicenseModel> OMVSet1 = new HashSet<OMVLicenseModel>();
		Set<OMVLicenseModel> OMVSet2 = new HashSet<OMVLicenseModel>();
		Set<OMVLicenseModel> OMVSetMerged = new HashSet<OMVLicenseModel>();
		OMVSet1.addAll(OMVSet);
		OMVSet2.addAll(OMVSet);
		Iterator it1 = OMVSet1.iterator();
		while (it1.hasNext()){
			OMVLicenseModel o1 = (OMVLicenseModel)it1.next();
			Iterator it2 = OMVSet2.iterator();
			boolean match=false;
			while (it2.hasNext()){
				OMVLicenseModel o2 = (OMVLicenseModel)it2.next();
				if(o2!=o1 && o1.getName()!=null && o2.getName()!=null && o1.getName().equalsIgnoreCase(o2.getName())){					
					match=true;
					OMVLicenseModel m= new OMVLicenseModel();
					m=o1;
					
					//m.setURI(o1.getURI()); //TODO merge properties							
					//do not add twice the same merged object
					Iterator dup = OMVSetMerged.iterator(); 
					boolean add=true;
					while (dup.hasNext()){
						OMVLicenseModel d = (OMVLicenseModel)dup.next();
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