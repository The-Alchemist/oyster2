/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.adapter.submit;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.neontoolkit.omv.api.core.OMVFormalityLevel;
import org.neontoolkit.omv.api.core.OMVKnowledgeRepresentationParadigm;
import org.neontoolkit.omv.api.core.OMVLicenseModel;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.core.OMVOntologyDomain;
import org.neontoolkit.omv.api.core.OMVOntologyEngineeringMethodology;
import org.neontoolkit.omv.api.core.OMVOntologyEngineeringTool;
import org.neontoolkit.omv.api.core.OMVOntologyLanguage;
import org.neontoolkit.omv.api.core.OMVOntologySyntax;
import org.neontoolkit.omv.api.core.OMVOntologyTask;
import org.neontoolkit.omv.api.core.OMVOntologyType;
import org.neontoolkit.omv.api.core.OMVOrganisation;
import org.neontoolkit.omv.api.core.OMVParty;
import org.neontoolkit.omv.api.core.OMVPerson;
import org.neontoolkit.oyster2.client.gui.dialogs.content.InputComposite;
import org.neontoolkit.oyster2.client.gui.dialogs.content.PartyComposite;
import org.neontoolkit.oyster2.client.gui.dialogs.content.PersonSelectionComposite.Person;


/**
 * Makes a OMVOntology from the composites shown to the user.
 * 
 * @author David Muñoz
 *
 */
public class OntologyAdapter {
		
	public static OMVOntology makeOntology(Map<String,InputComposite> composites) {
		// the values are values to set on those attributes
		// and can be String or String[]
		String attributeName = null;
		InputComposite composite = null;
		Object input = null;
		Field field = null;
		
		String []values = null;
		String singleValue = null;
		//String [] item = null;
		int i = 0;
		
		Set keys = composites.keySet();
		Iterator it = keys.iterator();
		
		OMVOntology omvObject = new OMVOntology();
		Class clazz = OMVOntology.class;
		
		while (it.hasNext()) {
			attributeName = (String)it.next();
			
			composite = composites.get(attributeName);
			input = composite.getInput();
			if ((input instanceof String[])) {
				if (((String[])input).length == 0)
					continue;
			}
			else if ((input instanceof String)) {
				if (((String)input).trim().equals("")) {
			
					continue;
				}
			}
			
			//TODO we could use reflection for every field
			try {
				
				field = clazz.getDeclaredField(attributeName);
				
				field.setAccessible(true);
				if (field.getType().equals(String.class)) {
					field.set(omvObject,(String)input);
				}
				else if (field.getType().equals(Boolean.class)) {
					field.set(omvObject,Boolean.valueOf((String)input));
				}
				else if (field.getType().equals(Integer.class)) {
					field.set(omvObject,Integer.valueOf((String)input));
				}
				else {
					
					if (attributeName.equals("name")) {
						values = (String[])input;
						for (i = 0;i<values.length;i++) {
							omvObject.addName(values[i]);
						}
					}
					else if (attributeName.equals("keywords")) {
						values = (String[])input;
						for (i = 0;i<values.length;i++) {
							omvObject.addKeywords(values[i]);
						}
					}
					else if (attributeName.equals("hasContributor")) {
						Set<OMVParty> party = getParty(input);
						for (OMVParty partyMember : party) {
							omvObject.addHasContributor(partyMember);
						}
					}
					else if (attributeName.equals("hasCreator")) {
						Set<OMVParty> party = getParty(input);
						for (OMVParty partyMember : party) {
							omvObject.addHasCreator(partyMember);
						}
					}
					else if (attributeName.equals("usedOntologyEngineeringTool")) {
						values = (String[])input;
						OMVOntologyEngineeringTool tool = null;
							
						for (i = 0;i<values.length;i++) {
							tool = new OMVOntologyEngineeringTool();
							tool.setName(values[i]);
							omvObject.addUsedOntologyEngineeringTool(tool);
						}
					}
					else if (attributeName.equals("usedOntologyEngineeringMethodology")) {
						values = (String[])input;
						OMVOntologyEngineeringMethodology methodology = null;
						for (i = 0;i<values.length;i++) {
							methodology = new OMVOntologyEngineeringMethodology();
							methodology.setName(values[i]);
							omvObject.addUsedOntologyEngineeringMethodology(methodology);
						}
					}
					else if (attributeName.equals("usedKnowledgeRepresentationParadigm")) {
						values = (String[])input;
						OMVKnowledgeRepresentationParadigm paradigm = null;
						for (i = 0;i<values.length;i++) {
							paradigm = new OMVKnowledgeRepresentationParadigm();
							paradigm.setName(values[i]);
							omvObject.addUsedKnowledgeRepresentationParadigm(paradigm);
						}
					}
					else if (attributeName.equals("hasDomain")) {
						Map<String,String> domainMap = (Map<String,String>)input;
						OMVOntologyDomain domain = null;
						for (Map.Entry<String,String> entry : domainMap.entrySet()) {
							domain = new OMVOntologyDomain();
							domain.setName(entry.getKey());
							domain.setURI(entry.getValue());
							omvObject.addHasDomain(domain);
						}
					}
					else if (attributeName.equals("isOfType")) {
						singleValue = (String)input;
						OMVOntologyType ontType = null;
						ontType = new OMVOntologyType();
						ontType.setName(singleValue);
						omvObject.setIsOfType(ontType);
					}
					else if (attributeName.equals("naturalLanguage")) {
						values = (String[])input;
						String langCode = null;
						for (i = 0;i<values.length;i++) {
							langCode = values[i].substring(0,2);
							omvObject.addNaturalLanguage(langCode);
						}
					}
					else if (attributeName.equals("designedForOntologyTask")) {
						values = (String[])input;
						OMVOntologyTask task = null;
						for (i = 0;i<values.length;i++) {
							task = new OMVOntologyTask();
							task.setName(values[i]);
							omvObject.addDesignedForOntologyTask(task);
						}
					}
					else if (attributeName.equals("hasOntologyLanguage")) {
						singleValue = (String)input;
						OMVOntologyLanguage ontLanguage = null;
						ontLanguage = new OMVOntologyLanguage();
						ontLanguage.setName(singleValue);
						omvObject.setHasOntologyLanguage(ontLanguage);
					}
					else if (attributeName.equals("hasOntologySyntax")) {
						singleValue = (String)input;
						OMVOntologySyntax syntax = null;
						syntax = new OMVOntologySyntax();
						syntax.setName(singleValue);
						omvObject.setHasOntologySyntax(syntax);
					}
					else if (attributeName.equals("hasFormalityLevel")) {
						singleValue = (String)input;
						OMVFormalityLevel formalityLevel;
						formalityLevel = new OMVFormalityLevel();
						formalityLevel.setName(singleValue);
						omvObject.setHasFormalityLevel(formalityLevel);
					}
					else if (attributeName.equals("hasLicense")) {
						singleValue = (String)input;
						OMVLicenseModel license = new OMVLicenseModel();
						license.setName(singleValue);
						omvObject.setHasLicense(license);
					}
					else if (attributeName.equals("useImports")) {
						values = (String[])input;
						OMVOntology imported = null;
						for (i = 0;i<values.length;i++) {
							imported = new OMVOntology();
							imported.setURI(values[i]);
							//setOntologyId(imported,values[i]);
							omvObject.addUseImports(imported);
						}
					}
					else if (attributeName.equals("hasPriorVersion")) {
						singleValue = (String)input;
						OMVOntology previous = null;
						previous = new OMVOntology();
						setOntologyId(previous, singleValue);
						omvObject.setHasPriorVersion(previous);
					}
					else if (attributeName.equals("isBackwardCompatibleWith")) {
						values = (String[])input;
						OMVOntology otherOntology = null;
						for (i = 0;i<values.length;i++) {
							otherOntology = new OMVOntology();
							otherOntology.setURI(values[i]);
							//setOntologyId(otherOntology,values[i]);
							omvObject.addIsBackwardCompatibleWith(otherOntology);
						}
					}
					else if (attributeName.equals("isIncompatibleWith")) {
						values = (String[])input;
						
						OMVOntology otherOntology = null;
						for (i = 0;i<values.length;i++) {
							otherOntology = new OMVOntology();
							//setOntologyId(otherOntology,values[i]);
							otherOntology.setURI(values[i]);
							omvObject.addIsIncompatibleWith(otherOntology);
						}
					}
					else if (attributeName.equals("keyClasses")) {
						values = (String[])input;
						for (i = 0;i<values.length;i++) {
							omvObject.addKeyClasses(values[i]);
						}
					}
					else if (attributeName.equals("keyClasses")) {
						values = (String[])input;
						for (i = 0;i<values.length;i++) {
							omvObject.addKnownUsage(values[i]);
						}
					}
					else if (attributeName.equals("endorsedBy")) {
						Set<OMVParty> party = getParty(input);
						for (OMVParty partyMember : party) {
							omvObject.addEndorsedBy(partyMember);
						}
					}
				}
			}
			catch (NoSuchFieldException e) {
				throw new RuntimeException(e);
			}
			catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			
		};
		
		
		return omvObject;
	}
	
	
	
	private static void setOntologyId(OMVOntology ontology, String id) {
		//URI
		
		int URIEndIndex = id.indexOf("?version=");
		
		int versionStartIndex = URIEndIndex + "?version=".length();
		int versionEndIndex = id.indexOf(";location=", versionStartIndex);
		
		int locationStartIndex = versionEndIndex + ";location=".length();
		int locationEndIndex = id.length();
				
		ontology.setURI(id.substring(0, URIEndIndex));
		ontology.setVersion(id.substring(versionStartIndex,versionEndIndex));
		ontology.setResourceLocator(id.substring(locationStartIndex, locationEndIndex));
		
	}
	
	
	private static Set<OMVParty> getParty(Object input) {
		Set<OMVParty> party = new HashSet<OMVParty>();
		OMVPerson person = null;
		PartyComposite.PartyMembers partyMembers = 
			(PartyComposite.PartyMembers)input;
		
		for (Person member : partyMembers.getPeople()) {
			person = new OMVPerson();
			person.setFirstName(member.getName());
			person.setLastName(member.getLastname());
			party.add(person);
		}
		OMVOrganisation organization = null;
		for (String org : partyMembers.getOrganizations()) {
			organization = new OMVOrganisation();
			organization.setName(org);
			party.add(organization);
		}
		return party;
	}
	
}
