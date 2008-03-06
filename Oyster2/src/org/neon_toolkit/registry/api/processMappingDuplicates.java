package org.neon_toolkit.registry.api;


import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import org.neon_toolkit.omv.api.core.OMVFormalityLevel;
import org.neon_toolkit.omv.api.core.OMVKnowledgeRepresentationParadigm;
import org.neon_toolkit.omv.api.core.OMVLicenseModel;
import org.neon_toolkit.omv.api.core.OMVOntology;
import org.neon_toolkit.omv.api.core.OMVOntologyDomain;
import org.neon_toolkit.omv.api.core.OMVOntologyEngineeringMethodology;
import org.neon_toolkit.omv.api.core.OMVOntologyEngineeringTool;
import org.neon_toolkit.omv.api.core.OMVOntologyLanguage;
import org.neon_toolkit.omv.api.core.OMVOntologySyntax;
import org.neon_toolkit.omv.api.core.OMVOntologyTask;
import org.neon_toolkit.omv.api.core.OMVOntologyType;
import org.neon_toolkit.omv.api.core.OMVOrganisation;
import org.neon_toolkit.omv.api.core.OMVParty;
import org.neon_toolkit.omv.api.core.OMVPerson;
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
	
	static public String getTimeStamp(String s1, String s2){
		DateFormat format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.US);
		if (s1!=null && s2!=null){
			try{
				Date d1 = format.parse(s1);
				Date d2 = format.parse(s2);
				if (d1.after(d2)) return s1;
				else return s2;
			}catch(Exception e){
				//ignore
			}
		}
		else if (s1==null && s2!=null) return s2;
		else if (s2==null && s1!=null) return s1;
		return null;
	}
	
	static public String getData(String s1, String s2){
		if (s1!=null){
			return s1;
		}else if (s2!=null){
			return s2;
		}
		return null;
	}
	
	static public Integer getIntData(Integer s1, Integer s2){
		if (s1!=null){
			return s1;
		}else if (s2!=null){
			return s2;
		}
		return null;
	}
	
	static public Boolean getBoolData(Boolean s1, Boolean s2){
		if (s1!=null){
			return s1;
		}else if (s2!=null){
			return s2;
		}
		return null;
	}
	
	static public OMVOntologyType getOTData(OMVOntologyType s1, OMVOntologyType s2){
		if (s1!=null){
			return s1;
		}else if (s2!=null){
			return s2;
		}
		return null;
	}
	
	static public OMVOntologyLanguage getOLData(OMVOntologyLanguage s1, OMVOntologyLanguage s2){
		if (s1!=null){
			return s1;
		}else if (s2!=null){
			return s2;
		}
		return null;
	}
	
	static public OMVOntologySyntax getOSData(OMVOntologySyntax s1, OMVOntologySyntax s2){
		if (s1!=null){
			return s1;
		}else if (s2!=null){
			return s2;
		}
		return null;
	}
	
	static public OMVFormalityLevel getFLData(OMVFormalityLevel s1, OMVFormalityLevel s2){
		if (s1!=null){
			return s1;
		}else if (s2!=null){
			return s2;
		}
		return null;
	}
	
	static public OMVLicenseModel getLMData(OMVLicenseModel s1, OMVLicenseModel s2){
		if (s1!=null){
			return s1;
		}else if (s2!=null){
			return s2;
		}
		return null;
	}
	
	static public OMVOntology getOntologyData(OMVOntology s1, OMVOntology s2){
		if (s1!=null){
			return s1;
		}else if (s2!=null){
			return s2;
		}
		return null;
	}
	
	static public Set<String> getStringSet(Set<String> s1, Set<String> s2){
		Set<String> temp = new HashSet<String>();
		temp.addAll(s1);
		Iterator is2=s2.iterator();
		while (is2.hasNext()){
			String t = (String)is2.next();
			if (!temp.contains(t)) temp.add(t);
		}
		return temp;
	}
	
	static public Set<OMVParty> getPartySet(Set<OMVParty> s1, Set<OMVParty> s2){
		Set<OMVParty> temp = new HashSet<OMVParty>();
		temp.addAll(s1);
		Iterator is2=s2.iterator();
		while (is2.hasNext()){
			OMVParty ori2=(OMVParty)is2.next();
			Iterator is1=temp.iterator();
			boolean match=false;
			while (is1.hasNext()){
				OMVParty ori1 = (OMVParty)is1.next();
				if (ori1 instanceof OMVPerson && ori2 instanceof OMVPerson){
					OMVPerson r1= (OMVPerson) ori1;
					OMVPerson r2= (OMVPerson) ori2;
					if (r1.getFirstName().equalsIgnoreCase(r2.getFirstName())) match=true;
				}else if (ori1 instanceof OMVOrganisation && ori2 instanceof OMVOrganisation){
					OMVOrganisation r1= (OMVOrganisation) ori1;
					OMVOrganisation r2= (OMVOrganisation) ori2;
					if (r1.getName().equalsIgnoreCase(r2.getName())) match=true;
				}
			}
			if (!match) temp.add(ori2);
		}
		return temp;
	}
	
	static public Set<OMVOntologyEngineeringTool> getOETSet(Set<OMVOntologyEngineeringTool> s1, Set<OMVOntologyEngineeringTool> s2){
		Set<OMVOntologyEngineeringTool> temp = new HashSet<OMVOntologyEngineeringTool>();
		temp.addAll(s1);
		Iterator is2=s2.iterator();
		while (is2.hasNext()){
			OMVOntologyEngineeringTool ori2=(OMVOntologyEngineeringTool)is2.next();
			Iterator is1=temp.iterator();
			boolean match=false;
			while (is1.hasNext()){
				OMVOntologyEngineeringTool ori1 = (OMVOntologyEngineeringTool)is1.next();
				if (ori1.getName().equalsIgnoreCase(ori2.getName())) match=true;
			}
			if (!match) temp.add(ori2);
		}
		return temp;
	}
	
	static public Set<OMVOntologyEngineeringMethodology> getOEMSet(Set<OMVOntologyEngineeringMethodology> s1, Set<OMVOntologyEngineeringMethodology> s2){
		Set<OMVOntologyEngineeringMethodology> temp = new HashSet<OMVOntologyEngineeringMethodology>();
		temp.addAll(s1);
		Iterator is2=s2.iterator();
		while (is2.hasNext()){
			OMVOntologyEngineeringMethodology ori2=(OMVOntologyEngineeringMethodology)is2.next();
			Iterator is1=temp.iterator();
			boolean match=false;
			while (is1.hasNext()){
				OMVOntologyEngineeringMethodology ori1 = (OMVOntologyEngineeringMethodology)is1.next();
				if (ori1.getName().equalsIgnoreCase(ori2.getName())) match=true;
			}
			if (!match) temp.add(ori2);
		}
		return temp;
	}
	
	static public Set<OMVKnowledgeRepresentationParadigm> getKRPSet(Set<OMVKnowledgeRepresentationParadigm> s1, Set<OMVKnowledgeRepresentationParadigm> s2){
		Set<OMVKnowledgeRepresentationParadigm> temp = new HashSet<OMVKnowledgeRepresentationParadigm>();
		temp.addAll(s1);
		Iterator is2=s2.iterator();
		while (is2.hasNext()){
			OMVKnowledgeRepresentationParadigm ori2=(OMVKnowledgeRepresentationParadigm)is2.next();
			Iterator is1=temp.iterator();
			boolean match=false;
			while (is1.hasNext()){
				OMVKnowledgeRepresentationParadigm ori1 = (OMVKnowledgeRepresentationParadigm)is1.next();
				if (ori1.getName().equalsIgnoreCase(ori2.getName())) match=true;
			}
			if (!match) temp.add(ori2);
		}
		return temp;
	}
	
	static public Set<OMVOntologyDomain> getODSet(Set<OMVOntologyDomain> s1, Set<OMVOntologyDomain> s2){
		Set<OMVOntologyDomain> temp = new HashSet<OMVOntologyDomain>();
		temp.addAll(s1);
		Iterator is2=s2.iterator();
		while (is2.hasNext()){
			OMVOntologyDomain ori2=(OMVOntologyDomain)is2.next();
			Iterator is1=temp.iterator();
			boolean match=false;
			while (is1.hasNext()){
				OMVOntologyDomain ori1 = (OMVOntologyDomain)is1.next();
				if (ori1.getURI().equalsIgnoreCase(ori2.getURI())) match=true;
			}
			if (!match) temp.add(ori2);
		}
		return temp;
	}
	
	static public Set<OMVOntologyTask> getOTASet(Set<OMVOntologyTask> s1, Set<OMVOntologyTask> s2){
		Set<OMVOntologyTask> temp = new HashSet<OMVOntologyTask>();
		temp.addAll(s1);
		Iterator is2=s2.iterator();
		while (is2.hasNext()){
			OMVOntologyTask ori2=(OMVOntologyTask)is2.next();
			Iterator is1=temp.iterator();
			boolean match=false;
			while (is1.hasNext()){
				OMVOntologyTask ori1 = (OMVOntologyTask)is1.next();
				if (ori1.getName().equalsIgnoreCase(ori2.getName())) match=true;
			}
			if (!match) temp.add(ori2);
		}
		return temp;
	}
	
	static public Set<OMVOntology> getOntologySet(Set<OMVOntology> s1, Set<OMVOntology> s2){
		Set<OMVOntology> temp = new HashSet<OMVOntology>();
		temp.addAll(s1);
		Iterator is2=s2.iterator();
		while (is2.hasNext()){
			OMVOntology ori2=(OMVOntology)is2.next();
			Iterator is1=temp.iterator();
			boolean match=false;
			while (is1.hasNext()){
				OMVOntology ori1 = (OMVOntology)is1.next();
				if (ori1.getURI().equalsIgnoreCase(ori2.getURI())) match=true;
			}
			if (!match) temp.add(ori2);
		}
		return temp;
	}
			
}