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

/**
 * The class processDuplicates provides the methods to
 * process duplicates in OMV objects  
 * representing the result of a query 
 * @author Raul Palma
 * @version 1.0, March 2008
 */
public class processDuplicates{
	
	static public Set<OMVOntology> mergeOMVDuplicates(Set<OMVOntology> OMVSet){
		Set<OMVOntology> OMVOntoSet1 = new HashSet<OMVOntology>();
		Set<OMVOntology> OMVOntoSet2 = new HashSet<OMVOntology>();
		Set<OMVOntology> OMVOntoSetMerged = new HashSet<OMVOntology>();
		OMVOntoSet1.addAll(OMVSet);
		OMVOntoSet2.addAll(OMVSet);
		Iterator it1 = OMVOntoSet1.iterator();
		while (it1.hasNext()){
			OMVOntology o1 = (OMVOntology)it1.next();
			Iterator it2 = OMVOntoSet2.iterator();
			boolean match=false;
			while (it2.hasNext()){
				OMVOntology o2 = (OMVOntology)it2.next();
				if(o2!=o1 && o1.getURI()!=null && o2.getURI()!=null && o1.getURI().equalsIgnoreCase(o2.getURI())){
					if(o1.getResourceLocator()!=null && o2.getResourceLocator()!=null && o1.getResourceLocator().equalsIgnoreCase(o2.getResourceLocator())){
						if((o1.getVersion()!=null && o2.getVersion()!=null && o1.getVersion().equalsIgnoreCase(o2.getVersion())) || (o1.getVersion()==null && o2.getVersion()==null)){
							match=true;
							OMVOntology m= new OMVOntology();
							m.setURI(o1.getURI());
							m.setResourceLocator(o1.getResourceLocator());
							m.setVersion(o1.getVersion());
							m.setAcronym(getData(o1.getAcronym(),o2.getAcronym()));
							m.getName().addAll(getStringSet(o1.getName(),o2.getName()));
							m.setDescription(getData(o1.getDescription(),o2.getDescription()));
							m.setDocumentation(getData(o1.getDocumentation(),o2.getDocumentation()));
							m.getKeywords().addAll(getStringSet(o1.getKeywords(),o2.getKeywords()));
							m.setStatus(getData(o1.getStatus(),o2.getStatus()));
							m.setCreationDate(getData(o1.getCreationDate(),o2.getCreationDate()));
							m.setModificationDate(getData(o1.getModificationDate(),o2.getModificationDate()));
							m.getHasContributor().addAll(getPartySet(o1.getHasContributor(),o2.getHasContributor()));
							m.getHasCreator().addAll(getPartySet(o1.getHasCreator(),o2.getHasCreator()));
							m.getUsedOntologyEngineeringTool().addAll(getOETSet(o1.getUsedOntologyEngineeringTool(),o2.getUsedOntologyEngineeringTool()));
							m.getUsedOntologyEngineeringMethodology().addAll(getOEMSet(o1.getUsedOntologyEngineeringMethodology(),o2.getUsedOntologyEngineeringMethodology()));
							m.getUsedKnowledgeRepresentationParadigm().addAll(getKRPSet(o1.getUsedKnowledgeRepresentationParadigm(),o2.getUsedKnowledgeRepresentationParadigm()));
							m.getHasDomain().addAll(getODSet(o1.getHasDomain(),o2.getHasDomain()));
							m.setIsOfType(getOTData(o1.getIsOfType(),o2.getIsOfType()));
							m.getNaturalLanguage().addAll(getStringSet(o1.getNaturalLanguage(),o2.getNaturalLanguage()));
							m.getDesignedForOntologyTask().addAll(getOTASet(o1.getDesignedForOntologyTask(),o2.getDesignedForOntologyTask()));
							m.setHasOntologyLanguage(getOLData(o1.getHasOntologyLanguage(),o2.getHasOntologyLanguage()));
							m.setHasOntologySyntax(getOSData(o1.getHasOntologySyntax(),o2.getHasOntologySyntax()));
							m.setHasFormalityLevel(getFLData(o1.getHasFormalityLevel(),o2.getHasFormalityLevel()));
							m.setHasLicense(getLMData(o1.getHasLicense(),o2.getHasLicense()));
							m.getUseImports().addAll(getOntologySet(o1.getUseImports(),o2.getUseImports()));
							m.setHasPriorVersion(getOntologyData(o1.getHasPriorVersion(),o2.getHasPriorVersion()));
							m.getIsBackwardCompatibleWith().addAll(getOntologySet(o1.getIsBackwardCompatibleWith(),o2.getIsBackwardCompatibleWith()));
							m.getIsIncompatibleWith().addAll(getOntologySet(o1.getIsIncompatibleWith(),o2.getIsIncompatibleWith()));
							m.setNumberOfAxioms(getIntData(o1.getNumberOfAxioms(),o2.getNumberOfAxioms()));
							m.setNumberOfClasses(getIntData(o1.getNumberOfClasses(),o2.getNumberOfClasses()));
							m.setNumberOfIndividuals(getIntData(o1.getNumberOfIndividuals(),o2.getNumberOfIndividuals()));
							m.setNumberOfProperties(getIntData(o1.getNumberOfProperties(),o2.getNumberOfProperties()));
							m.setNotes(getData(o1.getNotes(),o2.getNotes()));
							m.getKeyClasses().addAll(getStringSet(o1.getKeyClasses(),o2.getKeyClasses()));
							m.getKnownUsage().addAll(getStringSet(o1.getKnownUsage(),o2.getKnownUsage()));
							m.setExpressiveness(getData(o1.getExpressiveness(),o2.getExpressiveness()));
							m.setIsConsistentAccordingToReasoner(getBoolData(o1.getIsConsistentAccordingToReasoner(),o2.getIsConsistentAccordingToReasoner()));
							m.setContainsABox(getBoolData(o1.getContainsABox(),o2.getContainsABox()));
							m.setContainsTBox(getBoolData(o1.getContainsTBox(),o2.getContainsTBox()));
							m.setContainsRBox(getBoolData(o1.getContainsRBox(),o2.getContainsRBox()));
							m.getEndorsedBy().addAll(getPartySet(o1.getEndorsedBy(),o2.getEndorsedBy()));
							m.setTimeStamp(getTimeStamp(o1.getTimeStamp(),o2.getTimeStamp()));
							//do not add twice the same merged object
							Iterator dup = OMVOntoSetMerged.iterator(); 
							boolean add=true;
							while (dup.hasNext()){
								OMVOntology d = (OMVOntology)dup.next();
								if(m.getURI()!=null && d.getURI()!=null && m.getURI().equalsIgnoreCase(d.getURI())){
									if(m.getResourceLocator()!=null && d.getResourceLocator()!=null && m.getResourceLocator().equalsIgnoreCase(d.getResourceLocator())){
										if((m.getVersion()!=null && d.getVersion()!=null && m.getVersion().equalsIgnoreCase(d.getVersion())) || (m.getVersion()==null && d.getVersion()==null)){
											add=false;
										}
									}
								}
							}
							if (add) OMVOntoSetMerged.add(m);		
						}
					}
				}
			}
			if (!match) OMVOntoSetMerged.add(o1);
		}
		return OMVOntoSetMerged;
		//OMVSetDistributed.clear();
		//OMVSetDistributed.addAll(OMVOntoSetMerged);
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