package org.neontoolkit.registry.omv.service.oyster.querymanager.to_ebxml;


/*
 * wkrboy
 * 
 * Ugly tools for dealing with jaxr/ebxml differences
 */

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import org.apache.axis2.databinding.types.URI;
import org.apache.axis2.databinding.types.URI.MalformedURIException;
import org.neontoolkit.omv.api.core.OMVKnowledgeRepresentationParadigm;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.core.OMVOntologyDomain;
import org.neontoolkit.omv.api.core.OMVOntologyEngineeringMethodology;
import org.neontoolkit.omv.api.core.OMVOntologyEngineeringTool;
import org.neontoolkit.omv.api.core.OMVOntologyTask;
import org.neontoolkit.omv.api.core.OMVOrganisation;
import org.neontoolkit.omv.api.core.OMVPerson;
import org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType;
import org.neontoolkit.registry.omv.xsd.rim.Ontology_Type;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.FreeFormText;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringTypeSequence;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.LocalizedStringType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.ObjectRefType;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI;
import org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName;



/**
 * This class has several static methods used for translating JAXR objects into ebXML ones. Currently it 
 * supports these JAXR types - InternationalString, RegistryEntry, Concept, Classification and Association. 
 * Note: translating external identifiers and external links is not supported yet.
 * 
 * @author wrkboy
 *
 */
public class ebXMLTranslator {

	public static final String RETURNTYPE_ObjectRef="ObjectRef";
	public static final String RETURNTYPE_RegistryObject="RegistryObject";
	public static final String RETURNTYPE_LeafClass="LeafClass";
	public static final String RETURNTYPE_LeafClassWithRepositoryItem="LeafClassWithRepositoryItem";
	
	private static String returnType=RETURNTYPE_LeafClass;
	
	/**
	 * Sets how the objects should be returned
	 * 
	 * @param value - return type 
	 */
	public static void setReturnType(String value) {
		returnType=value;
	}
	
	/**
	 * Translates the JAXR registry object to ebXML and returns all its associations.
	 * 
	 * @param input - a JAXR RegistryObject
	 * @return
	 * @throws Exception
	 */
	public static org.oasis.names.tc.ebxml_regrep.xsd.rim.IdentifiableType[] translate(Object input) throws Exception {
		return translate(input, true);
	}
	
	/**
	 * Finds out of what type the input JAXR RegistryObject is, translates it into a ebXML type and 
	 * encapsulates it into an element class fit for using into an ebXML response. 
	 * If follow_associations is true, the object associations are also returned.
	 * 
	 * @param input - a JAXR RegistryObject
	 * @param follow_associations - should it pass the associations
	 * @return an ebXML object with all its associations (depending on follow_associations) 
	 * @throws Exception
	 */	
	public static org.oasis.names.tc.ebxml_regrep.xsd.rim.IdentifiableType[] translate(Object input, boolean follow_associations) throws Exception {
		ArrayList<org.oasis.names.tc.ebxml_regrep.xsd.rim.IdentifiableType> results = new ArrayList<org.oasis.names.tc.ebxml_regrep.xsd.rim.IdentifiableType>();

		//if return type is ObjectRef
		
		if (returnType.equals(RETURNTYPE_ObjectRef)) {
			ObjectRefType temp_obrt = new ObjectRefType();
			if (input instanceof OMVOntology) {
				OMVOntology omvT=(OMVOntology)input;
				String tURI="";
				tURI=omvT.getURI();
				if ((omvT.getVersion()!=null) && (omvT.getResourceLocator()!=null)) {
					tURI=tURI+"?version="+omvT.getVersion()+";";
					tURI=tURI+"location="+omvT.getResourceLocator();
				}
				else if (omvT.getResourceLocator()!=null) tURI=tURI+"?location="+omvT.getResourceLocator();
				else return null;
				tURI=tURI.replace(" ", "_");
				temp_obrt.setId(new URI(tURI));
			}	
			results.add(temp_obrt);
		} else 
		//if the whole objects should be returned
		if (returnType.equals(RETURNTYPE_LeafClass)) {
			if (input instanceof OMVOntology) {
				results.add(translateOntology((OMVOntology)input));
			}
			/*
			if (RegistryEntry.class.isAssignableFrom(input.getClass())){
				String temp_obj_class_name=getRegistryObjectClassificationName(input);
				
				follow_associations=false;
				//checks if it is an OMV object 
				//else it translates it like an ordinary CentraSite registryobject
				if (QName.valueOf(temp_obj_class_name).getLocalPart().equalsIgnoreCase("FormalityLevel")) {
					results.add(translateFormalityLevel(input));
				} else
				if (QName.valueOf(temp_obj_class_name).getLocalPart().equalsIgnoreCase("KnowledgeRepresentationParadigm")) {
					results.add(translateKnowledgeRepresentationParadigm(input));
				} else
				if (QName.valueOf(temp_obj_class_name).getLocalPart().equalsIgnoreCase("LicenseModel")) {
					results.add(translateLicenseModel(input));
				} else
				if (QName.valueOf(temp_obj_class_name).getLocalPart().equalsIgnoreCase("Ontology")) {
					results.add(translateOntology((RegistryEntry)input));
				} else
				if (QName.valueOf(temp_obj_class_name).getLocalPart().equalsIgnoreCase("OntologyEngineeringMethodology")) {
					results.add(translateOntologyEngineeringMethodology(input));
				} else
				if (QName.valueOf(temp_obj_class_name).getLocalPart().equalsIgnoreCase("OntologyEngineeringTool")) {
					results.add(translateOntologyEngineeringTool(input));
				} else
				if (QName.valueOf(temp_obj_class_name).getLocalPart().equalsIgnoreCase("OntologyLanguage")) {
					results.add(translateOntologyLanguage(input));
				} else
				if (QName.valueOf(temp_obj_class_name).getLocalPart().equalsIgnoreCase("OntologySyntax")) {
					results.add(translateOntologySyntax(input));
				} else
				if (QName.valueOf(temp_obj_class_name).getLocalPart().equalsIgnoreCase("OntologyTask")) {
					results.add(translateOntologyTask(input));
				} else
				if (QName.valueOf(temp_obj_class_name).getLocalPart().equalsIgnoreCase("OntologyType")) {
					results.add(translateOntologyType(input));
				} else
				if (QName.valueOf(temp_obj_class_name).getLocalPart().equalsIgnoreCase("OntologyDomain")) {
					results.add(translateOntologyDomain(input));
				} else
					throw new Exception("Unsupported return type."); 
				
			} else
			// in case it is any other CentraSite object
			if (User.class.isAssignableFrom(input.getClass())) {
				results.add(translate((User)input));
			} else
			if (Organization.class.isAssignableFrom(input.getClass())) {
				results.add(translate((Organization)input));
			} else
				return null;
			
			// if associations should be also passed 
			if (follow_associations)
				for (Object one_assoc:input.getAssociations())
					if (Association.class.isAssignableFrom(one_assoc.getClass())) {
						org.oasis.names.tc.ebxml_regrep.xsd.rim.IdentifiableType[] temp_assoc_list = translate((Association)one_assoc,false);
						if (temp_assoc_list.length>0)
							results.add(translate((Association)one_assoc,false)[0]);
					}
			*/
		} else
			throw new Exception("Unsupported return type."); 
		return results.toArray(new org.oasis.names.tc.ebxml_regrep.xsd.rim.IdentifiableType[1]);
	}

	
	
	/**
	 * Translates a JAXR RegistryEntry into an ebXML RegistryObjectType
	 * 
	 * @param input - a JAXR RegistryEntry
	 * @return an ebXML RegistryObjectType
	 * @throws Exception
	 */
	/*
    public static RegistryObjectType translate(RegistryEntry input) throws Exception {
    	RegistryObjectType onereturn = new RegistryObjectType();
		
    	translateRegistryObjectType(input, onereturn);        	
    	
			// set User Version
		if (input.getUserVersion() != null && !input.getUserVersion().equals("")) {
			VersionInfoType temp1=new VersionInfoType();
			temp1.setComment(input.getUserVersion());
			onereturn.setVersionInfo(temp1);
		}

		return onereturn;
    } 
	*/
    /**
     * Translates a JAXR Classification into an ebXML ClassificationType
     * 
     * @param input - a JAXR Classification
     * @return an ebXML ClassificationType
     * @throws Exception
     */
	/*
    public static ClassificationType translate(Classification input) throws Exception {
    	ClassificationType onereturn = new ClassificationType();
		
    	translateRegistryObjectType(input, onereturn);        	
		
		//set classification details
    	if (input.getConcept()!=null)
    		onereturn.setClassificationNode(makeReferenceURI(new URI(input.getConcept().getKey().getId())));
    	if (input.getClassificationScheme()!=null)
    		onereturn.setClassificationScheme(makeReferenceURI(new URI(input.getClassificationScheme().getKey().getId())));
    	if (input.getClassifiedObject()!=null)
    		onereturn.setClassifiedObject(makeReferenceURI(new URI(input.getClassifiedObject().getKey().getId())));
    	
		return onereturn;
    } 
    */
    /**
     * Translates a JAXR Concept into an ebXML ClassificationNodeType
     * 
     * @param input - a JAXR Concept
     * @return an ebXML ClassificationNodeType
     * @throws Exception
     */ 
	/*
    public static ClassificationNodeType translate(Concept input) throws Exception {
    	ClassificationNodeType onereturn = new ClassificationNodeType();
    	
    	translateRegistryObjectType(input, onereturn);  
    	
    	if (input.getParent()!=null)
    		onereturn.setParent(makeReferenceURI(new URI(input.getParent().getKey().getId())));
    	if (input.getPath()!=null)
    		onereturn.setPath(input.getPath());       	
    	if (input.getValue()!=null) {
    		LongName templv = new LongName();
    		templv.setLongName(input.getValue());
    		onereturn.setCode(templv);
    	}        	
    	
    	return onereturn;
    }
	*/
    /**
     * Translates a JAXR Association into an ebXML AssociationType1
     * 
     * @param input - a JAXR Association
     * @return an ebXML AssociationType1
     * @throws Exception
     */
	/*
    public static AssociationType1 translate(Association input) throws Exception {
    	AssociationType1 onereturn = new AssociationType1();
    	
    	translateRegistryObjectType(input, onereturn);
    	
    	if (input.getAssociationType()==null || input.getSourceObject()==null || input.getTargetObject()==null)
    		return null;
    	onereturn.setAssociationType(makeReferenceURI(new URI(input.getAssociationType().getKey().getId())));
   		onereturn.setSourceObject(makeReferenceURI(new URI(input.getSourceObject().getKey().getId())));
   		onereturn.setTargetObject(makeReferenceURI(new URI(input.getTargetObject().getKey().getId())));
   		
    	return onereturn;
    }
	*/
    /**
     * Translates a JAXR InternationalString into an ebXML InternationalStringType. 
     * 
     * @param input - a JAXR InternationalString
     * @return an ebXML InternationalStringType
     * @throws JAXRException 
     */ 
	/*
    public static InternationalStringType translate(InternationalString input) throws JAXRException {
    	InternationalStringType onereturn= new InternationalStringType();
    	for (Object one_ls_o: input.getLocalizedStrings()){
    		javax.xml.registry.infomodel.LocalizedString one_ls=(javax.xml.registry.infomodel.LocalizedString)one_ls_o;
        	InternationalStringTypeSequence tempists=new InternationalStringTypeSequence();
        	LocalizedStringType templst=new LocalizedStringType();
        	FreeFormText tempfft=new FreeFormText();
        	tempfft.setFreeFormText(one_ls.getValue());
        	templst.setValue(tempfft);
        	templst.setLang(new Language(one_ls.getLocale()!=null? one_ls.getLocale().getLanguage(): "en_US"));
        	templst.setCharset(one_ls.getCharsetName()!=null? one_ls.getCharsetName() : "UTF-8");
        	tempists.setLocalizedString(templst);
        	onereturn.addInternationalStringTypeSequence(tempists);
    	}
    	return onereturn;
    }
    */
	/*
    public static PostalAddressType translate(PostalAddress input) throws JAXRException {
		PostalAddressType temp_pat = new PostalAddressType();
		ShortName temp_sn;

		if (input.getCity()!=null) {
			temp_sn=new ShortName();
			temp_sn.setShortName(input.getCity());
			temp_pat.setCity(temp_sn);
		}	
		if (input.getCity()!=null) {
			temp_sn=new ShortName();
			temp_sn.setShortName(input.getCountry());
			temp_pat.setCountry(temp_sn);
		}
		if (input.getCity()!=null) {
			temp_sn=new ShortName();
			temp_sn.setShortName(input.getPostalCode());
			temp_pat.setPostalCode(temp_sn);
		}
		if (input.getCity()!=null) {
			temp_sn=new ShortName();
			temp_sn.setShortName(input.getStateOrProvince());
			temp_pat.setStateOrProvince(temp_sn);
		}
		if (input.getCity()!=null) {
			temp_sn=new ShortName();
			temp_sn.setShortName(input.getStreet());
			temp_pat.setStreet(temp_sn);
		}
		if (input.getCity()!=null) {
			String32 temp_str32=new String32();
			temp_str32.setString32(input.getStreetNumber());
			temp_pat.setStreetNumber(temp_str32);
		}
		return temp_pat;
    }
    */
	/*
    public static TelephoneNumberType translate(TelephoneNumber input) throws JAXRException {
    	TelephoneNumberType temp_tnt = new TelephoneNumberType();
    	
    	if (input.getAreaCode()!=null) {
    		String8 temp_str8 = new String8();
    		temp_str8.setString8(input.getAreaCode());
    		temp_tnt.setAreaCode(temp_str8);
    	}
    	if (input.getCountryCode()!=null) {
    		String8 temp_str8 = new String8();
    		temp_str8.setString8(input.getCountryCode());
    		temp_tnt.setCountryCode(temp_str8);
    	}
    	if (input.getExtension()!=null) {
    		String8 temp_str8 = new String8();
    		temp_str8.setString8(input.getExtension());
    		temp_tnt.setExtension(temp_str8);
    	}
    	if (input.getNumber()!=null) {
    		String16 temp_str16 = new String16();
    		temp_str16.setString16(input.getNumber());
    		temp_tnt.setNumber(temp_str16);
    	}
    	if (input.getType()!=null) {
    		String32 temp_str32 = new String32();
    		temp_str32.setString32(input.getType());
    		temp_tnt.setPhoneType(temp_str32);
    	}
    	
    	return temp_tnt;
    }
    */
	/*
    public static EmailAddressType translate(EmailAddress input) throws JAXRException {
    	EmailAddressType temp_eat=new EmailAddressType();
    	
    	if (input.getAddress()!=null) {
    		ShortName temp_sn = new ShortName();
    		temp_sn.setShortName(input.getAddress());
    		temp_eat.setAddress(temp_sn);
    	}
    	
    	if (input.getType()!=null) {
    		String32 temp_str32 = new String32();
    		temp_str32.setString32(input.getType());
    		temp_eat.setType(temp_str32);
    	}
    	
    	return temp_eat;
    }
    */
	/*
    public static PersonNameType translate(PersonName input) throws JAXRException {
    	PersonNameType temp_pnt = new PersonNameType();
    	ShortName temp_sn;
    	if (input.getFirstName()!=null) {
    		temp_sn = new ShortName();
    		temp_sn.setShortName(input.getFirstName());
    		temp_pnt.setFirstName(temp_sn);
    	}
    	if (input.getMiddleName()!=null) {
    		temp_sn = new ShortName();
    		temp_sn.setShortName(input.getMiddleName());
    		temp_pnt.setMiddleName(temp_sn);
    	}
    	if (input.getLastName()!=null) {
    		temp_sn = new ShortName();
    		temp_sn.setShortName(input.getLastName());
    		temp_pnt.setLastName(temp_sn);
    	}
    	return temp_pnt;
    }
    */
	/*
    public static OrganizationType translate(Organization input) throws Exception {
    	OrganizationType onereturn = new OrganizationType();
    	
    	translateRegistryObjectType(input, onereturn);  

    	if (input.getPostalAddress()!=null)
    		onereturn.addAddress(translate(input.getPostalAddress()));

    	if (input.getParentOrganization()!=null)
    		onereturn.setParent(makeReferenceURI(new URI(input.getParentOrganization().getKey().getId())));
    	
    	if (input.getPrimaryContact()!=null)
    		onereturn.setPrimaryContact(makeReferenceURI(new URI(input.getPrimaryContact().getKey().getId())));
    	
    	if (input.getTelephoneNumbers(null)!=null)
    		for (Object one_tn:input.getTelephoneNumbers(null))
    		onereturn.addTelephoneNumber(translate((TelephoneNumber)one_tn));
    	
    	return onereturn;
    }
    */
	/*
    public static PersonType translate(User input) throws Exception {
    	PersonType onereturn = new PersonType();

    	translateRegistryObjectType(input, onereturn);  
    	
    	if (input.getPostalAddresses()!=null)
    		for (Object one_tn:input.getPostalAddresses())
    			onereturn.addAddress(translate((PostalAddress)one_tn));

    	if (input.getTelephoneNumbers(null)!=null)
    		for (Object one_tn:input.getTelephoneNumbers(null))
    		onereturn.addTelephoneNumber(translate((TelephoneNumber)one_tn));

    	if (input.getPersonName()!=null)
    		onereturn.setPersonName(translate(input.getPersonName()));
    	
    	if (input.getEmailAddresses()!=null)
    		for (Object one_tn:input.getEmailAddresses())
    			onereturn.addEmailAddress(translate((EmailAddress)one_tn));    		
    	
    	return onereturn;
    }
    */
    
    /**
     * Creates an ebXML InternationalStringType from a list of Strings. 
     * 
     * @param input - a list of Strings
     * @return an ebXML InternationalStringType
     */ 
    private static InternationalStringType createInternationalString(String[] input) {
    	InternationalStringType onereturn = new InternationalStringType();
    	for (int i=0;i<input.length;i++) {
        	InternationalStringTypeSequence tempists=new InternationalStringTypeSequence();
        	LocalizedStringType templst=new LocalizedStringType();
        	FreeFormText tempfft=new FreeFormText();
        	tempfft.setFreeFormText(input[i]);
        	templst.setValue(tempfft);
        	tempists.setLocalizedString(templst);
        	onereturn.addInternationalStringTypeSequence(tempists);
    	}
    	return onereturn;
    }
    
    /**
     * Translates a JAXR RegistryObject in an ebXML FormalityLevelType. 
     * 
     * @param input - JAXR RegistryObject
     * @return an ebXML FormalityLevelType
     * @throws Exception
     */
    /*
    public static FormalityLevelType translateFormalityLevel(RegistryObject input) throws Exception {
    	FormalityLevelType onereturn = new FormalityLevelType();
    	
    	translateOMVRegistryObjectType(input, onereturn);
    	
    	return onereturn;   	
    }
    */
    /**
     * Translates a JAXR RegistryObject in an ebXML KnowledgeRepresentationParadigmType. 
     * 
     * @param input - JAXR RegistryObject
     * @return an ebXML KnowledgeRepresentationParadigmType
     * @throws Exception
     */
    /*
    public static KnowledgeRepresentationParadigmType translateKnowledgeRepresentationParadigm(RegistryObject input) throws Exception {
    	KnowledgeRepresentationParadigmType onereturn = new KnowledgeRepresentationParadigmType();
    	
    	translateOMVRegistryObjectType(input, onereturn);
    	
		try {
			ArrayList<OMVObjectRefType> all_objref = new ArrayList<OMVObjectRefType>();
			//goes through all the associations that are named "SpecifiedBy" and 
			//creates a list of OMVObjectRefType objects that contain the target objects
			for (Object one_association: input.getAssociations())
				if (QName.valueOf(flattenInternationalString(((Association)one_association).getAssociationType().getName())).getLocalPart().equalsIgnoreCase("SpecifiedBy"))
				{
					OMVObjectRefType new_objref = new OMVObjectRefType();
					new_objref.setId(makeReferenceURI(new URI(((Association)one_association).getTargetObject().getKey().getId())));
					all_objref.add(new_objref);
				}
			onereturn.setSpecifiedBy(all_objref.toArray(new OMVObjectRefType[1]));
		}
		catch (Exception e) {
			throw new Exception("Wrong information in the associations of the KnowledgeRepresentationParadigm object "+input.getKey().getId());
		}
    	
    	return onereturn;   	
    }
    */
    /**
     * Translates a JAXR RegistryObject in an ebXML LicenseModelType. 
     * 
     * @param input - JAXR RegistryObject
     * @return an ebXML LicenseModelType
     * @throws Exception
     */
    /*
    public static LicenseModelType translateLicenseModel(RegistryObject input) throws Exception {
    	LicenseModelType onereturn = new LicenseModelType();
    	
    	translateOMVRegistryObjectType(input, onereturn);
    	
		try {
			ArrayList<OMVObjectRefType> all_objref = new ArrayList<OMVObjectRefType>();
			//goes through all the associations that are named "SpecifiedBy" and 
			//creates a list of OMVObjectRefType objects that contain the target objects
			for (Object one_association: input.getAssociations())
				if (QName.valueOf(flattenInternationalString(((Association)one_association).getAssociationType().getName())).getLocalPart().equalsIgnoreCase("SpecifiedBy"))
				{
					OMVObjectRefType new_objref = new OMVObjectRefType();
					new_objref.setId(makeReferenceURI(new URI(((Association)one_association).getTargetObject().getKey().getId())));
					all_objref.add(new_objref);
				}
			onereturn.setSpecifiedBy(all_objref.toArray(new OMVObjectRefType[1]));
		}
		catch (Exception e) {
			throw new Exception("Wrong information in the associations of the LicenseModel object "+input.getKey().getId());
		}
    	
    	return onereturn;   	
    }
    */
    /**
     * Translates a JAXR RegistryObject in an ebXML OntologyTaskType. 
     * 
     * @param input - JAXR RegistryObject
     * @return an ebXML OntologyTaskType
     * @throws Exception
     */
    /*
    public static OntologyTaskType translateOntologyTask(RegistryObject input) throws Exception {
    	OntologyTaskType onereturn = new OntologyTaskType();
    	
    	translateOMVRegistryObjectType(input, onereturn);
    	    	
    	return onereturn;   	
    }
    */
    /**
     * Translates a JAXR RegistryObject in an ebXML OntologyEngineeringMethodologyType. 
     * 
     * @param input - JAXR RegistryObject
     * @return an ebXML OntologyEngineeringMethodologyType
     * @throws Exception
     */
    /*
    public static OntologyEngineeringMethodologyType translateOntologyEngineeringMethodology(RegistryObject input) throws Exception {
    	OntologyEngineeringMethodologyType onereturn = new OntologyEngineeringMethodologyType();
    	
    	translateOMVRegistryObjectType(input, onereturn);
    	
		try {
			ArrayList<OMVObjectRefType> all_objref = new ArrayList<OMVObjectRefType>();
			//goes through all the associations that are named "DevelopedBy" and 
			//creates a list of OMVObjectRefType objects that contain the target objects
			for (Object one_association: input.getAssociations())
				if (QName.valueOf(flattenInternationalString(((Association)one_association).getAssociationType().getName())).getLocalPart().equalsIgnoreCase("DevelopedBy"))
				{
					OMVObjectRefType new_objref = new OMVObjectRefType();
					new_objref.setId(makeReferenceURI(new URI(((Association)one_association).getTargetObject().getKey().getId())));
					all_objref.add(new_objref);
				}
			onereturn.setDevelopedBy(all_objref.toArray(new OMVObjectRefType[1]));
		}
		catch (Exception e) {
			throw new Exception("Wrong information in the associations of the OntologyEngineeringMethodology object "+input.getKey().getId());
		}
    	
    	return onereturn;   	
    }
    */
    /**
     * Translates a JAXR RegistryObject in an ebXML OntologyDomainType. 
     * 
     * @param input - JAXR RegistryObject
     * @return an ebXML OntologyDomainType
     * @throws Exception
     */
    /*
    public static OntologyDomainType translateOntologyDomain(RegistryObject input) throws Exception {
    	OntologyDomainType onereturn = new OntologyDomainType();
    	
    	translateOMVRegistryObjectType(input, onereturn);
    	
    	try {
			for (Object one_slot: input.getSlots())
				if (!((Slot)one_slot).getValues().isEmpty()) {
					Object[] temp_value = ((Slot)one_slot).getValues().toArray();
					String temp_slot_name=((Slot)one_slot).getName();
					
					if (QName.valueOf(temp_slot_name).getLocalPart().equalsIgnoreCase("URI"))
						onereturn.setURI(new URI((String)(temp_value[0])));
				}
		} catch (Exception e) {
			throw new Exception("Wrong information in the slots of the Ontology object: "+input.getKey().getId());
		}
    	
		try {
			ArrayList<OMVObjectRefType> all_objref = new ArrayList<OMVObjectRefType>();
			//goes through all the associations that are named "IsSubDomainOf" and 
			//creates a list of OMVObjectRefType objects that contain the target objects
			for (Object one_association: input.getAssociations())
				if (QName.valueOf(flattenInternationalString(((Association)one_association).getAssociationType().getName())).getLocalPart().equalsIgnoreCase("IsSubDomainOf"))
				{
					OMVObjectRefType new_objref = new OMVObjectRefType();
					new_objref.setId(makeReferenceURI(new URI(((Association)one_association).getTargetObject().getKey().getId())));
					all_objref.add(new_objref);
				}
			onereturn.setIsSubDomainOf(all_objref.toArray(new OMVObjectRefType[1]));
		}
		catch (Exception e) {
			throw new Exception("Wrong information in the associations of the OntologyDomain object "+input.getKey().getId());
		}
    	
    	return onereturn;   	
    }
    */
    /**
     * Translates a JAXR RegistryObject in an ebXML OntologyEngineeringToolType. 
     * 
     * @param input - JAXR RegistryObject
     * @return an ebXML OntologyEngineeringToolType
     * @throws Exception
     */
    /*
    public static OntologyEngineeringToolType translateOntologyEngineeringTool(RegistryObject input) throws Exception {
    	OntologyEngineeringToolType onereturn = new OntologyEngineeringToolType();
    	
    	translateOMVRegistryObjectType(input, onereturn);
    	
		try {
			ArrayList<OMVObjectRefType> all_objref = new ArrayList<OMVObjectRefType>();
			//goes through all the associations that are named "DevelopedBy" and 
			//creates a list of OMVObjectRefType objects that contain the target objects
			for (Object one_association: input.getAssociations())
				if (QName.valueOf(flattenInternationalString(((Association)one_association).getAssociationType().getName())).getLocalPart().equalsIgnoreCase("DevelopedBy"))
				{
					OMVObjectRefType new_objref = new OMVObjectRefType();
					new_objref.setId(makeReferenceURI(new URI(((Association)one_association).getTargetObject().getKey().getId())));
					all_objref.add(new_objref);
				}
			onereturn.setDevelopedBy(all_objref.toArray(new OMVObjectRefType[1]));
		}
		catch (Exception e) {
			throw new Exception("Wrong information in the associations of the OntologyEngineeringTool object "+input.getKey().getId());
		}
    	
    	return onereturn;   	
    }    
    */
    /**
     * Translates a JAXR RegistryObject in an ebXML OntologySyntaxType. 
     * 
     * @param input - JAXR RegistryObject
     * @return an ebXML OntologySyntaxType
     * @throws Exception
     */
    /*
    public static OntologySyntaxType translateOntologySyntax(RegistryObject input) throws Exception {
    	OntologySyntaxType onereturn = new OntologySyntaxType();
    	
    	translateOMVRegistryObjectType(input, onereturn);
    	
		try {
			ArrayList<OMVObjectRefType> all_objref = new ArrayList<OMVObjectRefType>();
			//goes through all the associations that are named "DevelopedBy" and 
			//creates a list of OMVObjectRefType objects that contain the target objects
			for (Object one_association: input.getAssociations())
				if (QName.valueOf(flattenInternationalString(((Association)one_association).getAssociationType().getName())).getLocalPart().equalsIgnoreCase("DevelopedBy"))
				{
					OMVObjectRefType new_objref = new OMVObjectRefType();
					new_objref.setId(makeReferenceURI(new URI(((Association)one_association).getTargetObject().getKey().getId())));
					all_objref.add(new_objref);
				}
			onereturn.setDevelopedBy(all_objref.toArray(new OMVObjectRefType[1]));
		}
		catch (Exception e) {
			throw new Exception("Wrong information in the associations of the OntologySyntax object "+input.getKey().getId());
		}
    	
    	return onereturn;   	
    }
    */
    /**
     * Translates a JAXR RegistryObject in an ebXML OntologyLanguageType. 
     * 
     * @param input - JAXR RegistryObject
     * @return an ebXML OntologyLanguageType
     * @throws Exception
     */
    /*
    public static OntologyLanguageType translateOntologyLanguage(RegistryObject input) throws Exception {
    	OntologyLanguageType onereturn = new OntologyLanguageType();
    	
    	translateOMVRegistryObjectType(input, onereturn);
    	
		try {
			//goes through all the associations and sorts them in lists
			for (Object one_association: input.getAssociations()) {
				String temp_assoc_name=flattenInternationalString(((Association)one_association).getAssociationType().getName());
				OMVObjectRefType new_objref = new OMVObjectRefType();
				new_objref.setId(makeReferenceURI(new URI(((Association)one_association).getTargetObject().getKey().getId())));
				if (QName.valueOf(temp_assoc_name).getLocalPart().equalsIgnoreCase("DevelopedBy"))
					onereturn.addDevelopedBy(new_objref);
				else
				if (QName.valueOf(temp_assoc_name).getLocalPart().equalsIgnoreCase("SupportsRepresentationParadigm"))
					onereturn.addSupportsRepresentationParadigm(new_objref);
				else
				if (QName.valueOf(temp_assoc_name).getLocalPart().equalsIgnoreCase("Has") && 
						QName.valueOf(getRegistryObjectClassificationName(((Association)one_association).getTargetObject())).getLocalPart().equalsIgnoreCase("OntologySyntax"))
					onereturn.addHasSyntax(new_objref);
			}
		}
		catch (Exception e) {
			throw new Exception("Wrong information in the associations of the OntologyLanguage object "+input.getKey().getId());
		}
    	
    	return onereturn;   	
    }
    */
    /**
     * Translates a JAXR RegistryObject in an ebXML OntologyType_Type. 
     * 
     * @param input - JAXR RegistryObject
     * @return an ebXML OntologyType_Type
     * @throws Exception
     */
    /*
    public static OntologyType_Type translateOntologyType(RegistryObject input) throws Exception {
    	OntologyType_Type onereturn = new OntologyType_Type();
    	
    	translateOMVRegistryObjectType(input, onereturn);
    	
		try {
			ArrayList<OMVObjectRefType> all_objref = new ArrayList<OMVObjectRefType>();
			//goes through all the associations that are named "definedBy" and 
			//creates a list of OMVObjectRefType objects that contain the target objects
			for (Object one_association: input.getAssociations())
				if (QName.valueOf(flattenInternationalString(((Association)one_association).getAssociationType().getName())).getLocalPart().equalsIgnoreCase("DefinedBy"))
				{
					OMVObjectRefType new_objref = new OMVObjectRefType();
					new_objref.setId(makeReferenceURI(new URI(((Association)one_association).getTargetObject().getKey().getId())));
					all_objref.add(new_objref);
				}
			onereturn.setDefinedBy(all_objref.toArray(new OMVObjectRefType[1]));
		}
		catch (Exception e) {
			throw new Exception("Wrong information in the associations of the OntologyType object "+input.getKey().getId());
		}
    	
    	return onereturn;   	
    }
    */
    /**
     * Translates a JAXR RegistryObject in an ebXML OntologyType. 
     * 
     * @param input - JAXR RegistryObject
     * @return an ebXML OntologyType
     * @throws Exception
     */
    public static Ontology_Type translateOntology(OMVOntology omvT) throws Exception {
    	Ontology_Type onereturn = new Ontology_Type();
    	// set ID (key)
    	String tURI="";
		tURI=omvT.getURI();
		if ((omvT.getVersion()!=null) && (omvT.getResourceLocator()!=null)) {
			tURI=tURI+"?version="+omvT.getVersion()+";";
			tURI=tURI+"location="+omvT.getResourceLocator();
		}
		else if (omvT.getResourceLocator()!=null) tURI=tURI+"?location="+omvT.getResourceLocator();
		else return null;
		tURI=tURI.replace(" ", "_");	
		onereturn.setId(new URI(tURI));		
		try{
		// set Name
		if (omvT.getName().size()>0){						
			InternationalStringType intString= new InternationalStringType();
			Iterator it = omvT.getName().iterator();
			while (it.hasNext()){
				InternationalStringTypeSequence tempists=new InternationalStringTypeSequence();
				LocalizedStringType templst=new LocalizedStringType();
				FreeFormText tempfft=new FreeFormText();
				tempfft.setFreeFormText((String)it.next());
				templst.setValue(tempfft);
				templst.setCharset("UTF-8");
				tempists.setLocalizedString(templst);
				intString.addInternationalStringTypeSequence(tempists);
			}
			onereturn.setName(intString);
		}
		// set Description
		if (omvT.getDescription()!=null){
			InternationalStringType intString= new InternationalStringType();
			InternationalStringTypeSequence tempists=new InternationalStringTypeSequence();
			LocalizedStringType templst=new LocalizedStringType();
			FreeFormText tempfft=new FreeFormText();
			tempfft.setFreeFormText(omvT.getDescription());
			templst.setValue(tempfft);
			templst.setCharset("UTF-8");
			tempists.setLocalizedString(templst);
			intString.addInternationalStringTypeSequence(tempists);
			onereturn.setDescription(intString);
		}
		if (omvT.getKeywords().size()>0){
			ArrayList<InternationalStringType> temp_intstr_list = new ArrayList<InternationalStringType>();
			Iterator it = omvT.getKeywords().iterator();
			while (it.hasNext()){
				InternationalStringType intString= new InternationalStringType();
				InternationalStringTypeSequence tempists=new InternationalStringTypeSequence();
				LocalizedStringType templst=new LocalizedStringType();
				FreeFormText tempfft=new FreeFormText();
				tempfft.setFreeFormText((String)it.next());
				templst.setValue(tempfft);
				templst.setCharset("UTF-8");
				tempists.setLocalizedString(templst);
				intString.addInternationalStringTypeSequence(tempists);
				temp_intstr_list.add(intString);
			}
			onereturn.setKeywords(temp_intstr_list.toArray(new InternationalStringType[1]));
		}
		if (omvT.getNaturalLanguage().size()>0){
			ArrayList<InternationalStringType> temp_intstr_list = new ArrayList<InternationalStringType>();
			Iterator it = omvT.getNaturalLanguage().iterator();
			while (it.hasNext()){
				InternationalStringType intString= new InternationalStringType();
				InternationalStringTypeSequence tempists=new InternationalStringTypeSequence();
				LocalizedStringType templst=new LocalizedStringType();
				FreeFormText tempfft=new FreeFormText();
				tempfft.setFreeFormText((String)it.next());
				templst.setValue(tempfft);
				templst.setCharset("UTF-8");
				tempists.setLocalizedString(templst);
				intString.addInternationalStringTypeSequence(tempists);
				temp_intstr_list.add(intString);
			}
			onereturn.setNaturalLanguage(temp_intstr_list.toArray(new InternationalStringType[1]));
		}
    	if (omvT.getURI()!=null){
    		onereturn.setURI(new URI(omvT.getURI()));
    	}
    	if (omvT.getStatus()!=null) {
			ShortName temp_Status = new ShortName();
			temp_Status.setShortName(omvT.getStatus());
			onereturn.setOntologyStatus(temp_Status);
		}
    	if (omvT.getCreationDate()!=null) {
    		try{
    			Calendar temp_CreationDate = Calendar.getInstance();
    			temp_CreationDate.setTime((new SimpleDateFormat("yyyy-MM-dd")).parse((String)(omvT.getCreationDate())));
    			onereturn.setCreationDate(temp_CreationDate);
    		}catch(Exception e){
    			
    		}
    		if (onereturn.getCreationDate()==null){
    			Calendar temp_CreationDate = Calendar.getInstance();
    			temp_CreationDate.setTime((new SimpleDateFormat("yyyy-MM-dd")).parse("9999-99-99"));
    			onereturn.setCreationDate(temp_CreationDate);
    		}
		}
    	if (omvT.getModificationDate()!=null) {
    		try{
    			Calendar temp_CreationDate = Calendar.getInstance();
    			temp_CreationDate.setTime((new SimpleDateFormat("yyyy-MM-dd")).parse((String)(omvT.getModificationDate())));
    			onereturn.setCreationDate(temp_CreationDate);
    		}catch(Exception e){
    			
    		}
		}
    	if (omvT.getResourceLocator()!=null)
			onereturn.setResourceLocator(new URI(omvT.getResourceLocator()));
		if (omvT.getNumberOfClasses()!=null)
			onereturn.setNumberOfClasses(new BigInteger(omvT.getNumberOfClasses().toString()));
		else onereturn.setNumberOfClasses(new BigInteger("-1"));
		if (omvT.getNumberOfProperties()!=null)
			onereturn.setNumberOfProperties(new BigInteger(omvT.getNumberOfProperties().toString()));
		else onereturn.setNumberOfProperties(new BigInteger("-1"));
		if (omvT.getNumberOfIndividuals()!=null)
			onereturn.setNumberOfIndividuals(new BigInteger(omvT.getNumberOfIndividuals().toString()));
		else onereturn.setNumberOfIndividuals(new BigInteger("-1"));
		if (omvT.getNumberOfAxioms()!=null)
			onereturn.setNumberOfAxioms(new BigInteger(omvT.getNumberOfAxioms().toString()));
		else onereturn.setNumberOfAxioms(new BigInteger("-1"));
		if (omvT.getVersion()!=null) {
			FreeFormText temp_fft = new FreeFormText();
			temp_fft.setFreeFormText(omvT.getVersion());
			onereturn.setVersion(temp_fft);
		}
		else {
			FreeFormText temp_fft = new FreeFormText();
			temp_fft.setFreeFormText("");
			onereturn.setVersion(temp_fft);
		}
		if (omvT.getIsConsistentAccordingToReasoner()!=null)
			onereturn.setIsConsistentAccordingToReasoner(omvT.getIsConsistentAccordingToReasoner());
		if (omvT.getContainsTBox()!=null)
			onereturn.setContainsTBox(omvT.getContainsTBox());
		if (omvT.getContainsABox()!=null)
			onereturn.setContainsABox(omvT.getContainsABox());
		if (omvT.getContainsRBox()!=null)
			onereturn.setContainsRBox(omvT.getContainsRBox());
		if (omvT.getExpressiveness()!=null)
			onereturn.setExpressiveness(omvT.getExpressiveness());
		if (omvT.getKeyClasses().size()>0) {
			Iterator it = omvT.getKeyClasses().iterator();
			while (it.hasNext()){
				onereturn.addKeyClasses((String)it.next());
			}
		} 
		if (omvT.getKnownUsage().size()>0) {
			Iterator it = omvT.getKnownUsage().iterator();
			while (it.hasNext()){
				onereturn.addKnownUsage((String)it.next());
			}
		} 
		if (omvT.getNotes()!=null)
			onereturn.setNotes(omvT.getNotes());
		if (omvT.getIsOfType()!=null){
			ReferenceURI tRefURI=makeReferenceURI4OMV(omvT.getIsOfType().getName());
			if (tRefURI!=null) onereturn.setIsOfType(tRefURI);
		}
		else{
			ReferenceURI tRefURI=makeReferenceURI4OMV("");
			if (tRefURI!=null) onereturn.setIsOfType(tRefURI);
		}
		if (omvT.getHasOntologySyntax()!=null){
			ReferenceURI tRefURI=makeReferenceURI4OMV(omvT.getHasOntologySyntax().getName());
			if (tRefURI!=null) onereturn.setHasOntologySyntax(tRefURI);
		}
		else {
			ReferenceURI tRefURI=makeReferenceURI4OMV("");
			if (tRefURI!=null) onereturn.setHasOntologySyntax(tRefURI);
		}
		if (omvT.getHasOntologyLanguage()!=null){
			ReferenceURI tRefURI=makeReferenceURI4OMV(omvT.getHasOntologyLanguage().getName());
			if (tRefURI!=null){
				OMVObjectRefType new_objref = new OMVObjectRefType();
				new_objref.setId(tRefURI);
				onereturn.addHasOntologyLanguage(new_objref);
			}
		}
		if (omvT.getHasFormalityLevel()!=null){
			ReferenceURI tRefURI=makeReferenceURI4OMV(omvT.getHasFormalityLevel().getName());
			if (tRefURI!=null) onereturn.setHasFormalityLevel(tRefURI);
		}
		if (omvT.getHasLicense()!=null){
			ReferenceURI tRefURI=makeReferenceURI4OMV(omvT.getHasLicense().getName());
			if (tRefURI!=null) onereturn.setHasLicense(tRefURI);
		}
		if (omvT.getHasDomain()!=null){
			Iterator it = omvT.getHasDomain().iterator();
			while (it.hasNext()){
				ReferenceURI tRefURI=makeReferenceURI4OMV(((OMVOntologyDomain)it.next()).getURI());
				if (tRefURI!=null){
					OMVObjectRefType new_objref = new OMVObjectRefType();
					new_objref.setId(tRefURI);
					onereturn.addHasDomain(new_objref);
				}
			}
		}
		
		if (omvT.getHasPriorVersion()!=null){
			ReferenceURI tRefURI=makeReferenceURI4OMV(omvT.getHasPriorVersion().getURI());
			if (tRefURI!=null) onereturn.setHasPriorVersion(tRefURI);
		}
		if (omvT.getHasContributor()!=null){
			Iterator it = omvT.getHasContributor().iterator();
			while (it.hasNext()){
				Object t=it.next();
				String temp="";
				if (t instanceof OMVPerson){
					OMVPerson omv1a = (OMVPerson)t;
					temp=temp+omv1a.getFirstName()+omv1a.getLastName();
				}
				else{
					OMVOrganisation omv1b = (OMVOrganisation)t;
					temp=temp+omv1b.getName();
				}
				ReferenceURI tRefURI=makeReferenceURI4OMV(temp);
				if (tRefURI!=null){
					OMVObjectRefType new_objref = new OMVObjectRefType();
					new_objref.setId(tRefURI);
					onereturn.addHasContributor(new_objref);
				}
			}
		}
		if (omvT.getHasCreator()!=null){
			Iterator it = omvT.getHasCreator().iterator();
			while (it.hasNext()){
				Object t=it.next();
				String temp="";
				if (t instanceof OMVPerson){
					OMVPerson omv1a = (OMVPerson)t;
					temp=temp+omv1a.getFirstName()+omv1a.getLastName();
				}
				else{
					OMVOrganisation omv1b = (OMVOrganisation)t;
					temp=temp+omv1b.getName();
				}
				ReferenceURI tRefURI=makeReferenceURI4OMV(temp);
				if (tRefURI!=null){
					OMVObjectRefType new_objref = new OMVObjectRefType();
					new_objref.setId(tRefURI);
					onereturn.addHasCreator(new_objref);
				}
			}
		}
		else{
			ReferenceURI tRefURI=makeReferenceURI4OMV("");
			if (tRefURI!=null){
				OMVObjectRefType new_objref = new OMVObjectRefType();
				new_objref.setId(tRefURI);
				onereturn.addHasCreator(new_objref);
			}
		}
		if (omvT.getEndorsedBy()!=null){
			Iterator it = omvT.getEndorsedBy().iterator();
			while (it.hasNext()){
				Object t=it.next();
				String temp="";
				if (t instanceof OMVPerson){
					OMVPerson omv1a = (OMVPerson)t;
					temp=temp+omv1a.getFirstName()+omv1a.getLastName();
				}
				else{
					OMVOrganisation omv1b = (OMVOrganisation)t;
					temp=temp+omv1b.getName();
				}
				ReferenceURI tRefURI=makeReferenceURI4OMV(temp);
				if (tRefURI!=null){
					OMVObjectRefType new_objref = new OMVObjectRefType();
					new_objref.setId(tRefURI);
					onereturn.addEndorsedBy(new_objref);
				}
			}
		}
		if (omvT.getUsedOntologyEngineeringTool()!=null){
			Iterator it = omvT.getUsedOntologyEngineeringTool().iterator();
			while (it.hasNext()){
				ReferenceURI tRefURI=makeReferenceURI4OMV(((OMVOntologyEngineeringTool)it.next()).getName());
				if (tRefURI!=null){
					OMVObjectRefType new_objref = new OMVObjectRefType();
					new_objref.setId(tRefURI);
					onereturn.addUsedOntologyEngineeringTool(new_objref);
				}
			}
		}
		if (omvT.getUsedOntologyEngineeringMethodology()!=null){
			Iterator it = omvT.getUsedOntologyEngineeringMethodology().iterator();
			while (it.hasNext()){
				ReferenceURI tRefURI=makeReferenceURI4OMV(((OMVOntologyEngineeringMethodology)it.next()).getName());
				if (tRefURI!=null){
					OMVObjectRefType new_objref = new OMVObjectRefType();
					new_objref.setId(tRefURI);
					onereturn.addUsedOntologyEngineeringMethodology(new_objref);
				}
			}
		}
		if (omvT.getUsedKnowledgeRepresentationParadigm()!=null){
			Iterator it = omvT.getUsedKnowledgeRepresentationParadigm().iterator();
			while (it.hasNext()){
				ReferenceURI tRefURI=makeReferenceURI4OMV(((OMVKnowledgeRepresentationParadigm)it.next()).getName());
				if (tRefURI!=null){
					OMVObjectRefType new_objref = new OMVObjectRefType();
					new_objref.setId(tRefURI);
					onereturn.addUsedKnowledgeRepresentationParadigm(new_objref);
				}
			}
		}
		if (omvT.getDesignedForOntologyTask()!=null){
			Iterator it = omvT.getDesignedForOntologyTask().iterator();
			while (it.hasNext()){
				ReferenceURI tRefURI=makeReferenceURI4OMV(((OMVOntologyTask)it.next()).getName());
				if (tRefURI!=null){
					OMVObjectRefType new_objref = new OMVObjectRefType();
					new_objref.setId(tRefURI);
					onereturn.addDesignedForOntologyTask(new_objref);
				}
			}
		}
		if (omvT.getUseImports()!=null){
			Iterator it = omvT.getUseImports().iterator();
			while (it.hasNext()){
				ReferenceURI tRefURI=makeReferenceURI4OMV(((OMVOntology)it.next()).getURI());
				if (tRefURI!=null){
					OMVObjectRefType new_objref = new OMVObjectRefType();
					new_objref.setId(tRefURI);
					onereturn.addUseImports(new_objref);
				}
			}
		}
		if (omvT.getIsBackwardCompatibleWith()!=null){
			Iterator it = omvT.getIsBackwardCompatibleWith().iterator();
			while (it.hasNext()){
				ReferenceURI tRefURI=makeReferenceURI4OMV(((OMVOntology)it.next()).getURI());
				if (tRefURI!=null){
					OMVObjectRefType new_objref = new OMVObjectRefType();
					new_objref.setId(tRefURI);
					onereturn.addIsBackwardCompatibleWith(new_objref);
				}
			}
		}
		if (omvT.getIsIncompatibleWith()!=null){
			Iterator it = omvT.getIsIncompatibleWith().iterator();
			while (it.hasNext()){
				ReferenceURI tRefURI=makeReferenceURI4OMV(((OMVOntology)it.next()).getURI());
				if (tRefURI!=null){
					OMVObjectRefType new_objref = new OMVObjectRefType();
					new_objref.setId(tRefURI);
					onereturn.addIsIncompatibleWith(new_objref);
				}
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
    	return onereturn;
    }
    
    
    
    
    /*
    public static Ontology_Type translateOntology(RegistryEntry input) throws Exception {
    	Ontology_Type onereturn = new Ontology_Type();
    	
    	translateOMVRegistryObjectType(input, onereturn);

		try {
			// checks all slots and copies the information to the ebXML object
			for (Object one_slot: input.getSlots())
				if (!((Slot)one_slot).getValues().isEmpty()) {
					Object[] temp_value = ((Slot)one_slot).getValues().toArray();
					String temp_slot_name=((Slot)one_slot).getName();
					
					if (QName.valueOf(temp_slot_name).getLocalPart().equalsIgnoreCase("Keywords")) {
//						onereturn.setKeywords(translate((InternationalString)(temp_value[0])));
						
						ArrayList<InternationalStringType> temp_intstr_list = new ArrayList<InternationalStringType>(temp_value.length);
				    	InternationalStringType temp_ist= new InternationalStringType();
			        	InternationalStringTypeSequence temp_ists=new InternationalStringTypeSequence();
			        	LocalizedStringType templst=new LocalizedStringType();
			        	FreeFormText tempfft=new FreeFormText();
			        	tempfft.setFreeFormText((String)temp_value[0]);
			        	templst.setValue(tempfft);
			        	temp_ists.setLocalizedString(templst);
			        	temp_ist.addInternationalStringTypeSequence(temp_ists);
						temp_intstr_list.add(temp_ist);
						onereturn.setKeywords(temp_intstr_list.toArray(new InternationalStringType[1]));
					} else
					if (QName.valueOf(temp_slot_name).getLocalPart().equalsIgnoreCase("NaturalLanguage")) {
						ArrayList<InternationalStringType> temp_intstr_list = new ArrayList<InternationalStringType>(temp_value.length);
//						for (Object temp_one_intstr: temp_value)
//							if (temp_one_intstr.getClass().isAssignableFrom(InternationalString.class))
//								temp_intstr_list.add(translate((InternationalString)temp_one_intstr));
//
				    	InternationalStringType temp_ist= new InternationalStringType();
			        	InternationalStringTypeSequence temp_ists=new InternationalStringTypeSequence();
			        	LocalizedStringType templst=new LocalizedStringType();
			        	FreeFormText tempfft=new FreeFormText();
			        	tempfft.setFreeFormText((String)temp_value[0]);
			        	templst.setValue(tempfft);
			        	temp_ists.setLocalizedString(templst);
			        	temp_ist.addInternationalStringTypeSequence(temp_ists);
						temp_intstr_list.add(temp_ist);
						
						onereturn.setNaturalLanguage(temp_intstr_list.toArray(new InternationalStringType[1]));
					}
					else
					if (QName.valueOf(temp_slot_name).getLocalPart().equalsIgnoreCase("OntologyStatus")) {
						ShortName temp_Status = new ShortName();
						temp_Status.setShortName((String)(temp_value[0]));
						onereturn.setOntologyStatus(temp_Status);
					}else
					if (QName.valueOf(temp_slot_name).getLocalPart().equalsIgnoreCase("URI"))
						onereturn.setURI(new URI((String)(temp_value[0])));
					else
					if (QName.valueOf(temp_slot_name).getLocalPart().equalsIgnoreCase("CreationDate")) {
						Calendar temp_CreationDate = Calendar.getInstance();
						temp_CreationDate.setTime((new SimpleDateFormat("yyyy-MM-dd")).parse((String)(temp_value[0])));
						onereturn.setCreationDate(temp_CreationDate);
					}
					else
					if (QName.valueOf(temp_slot_name).getLocalPart().equalsIgnoreCase("ModificationDate")) {
						Calendar temp_ModificationDate = Calendar.getInstance();
						temp_ModificationDate.setTime((new SimpleDateFormat("yyyy-MM-dd")).parse((String)(temp_value[0])));
						onereturn.setModificationDate(temp_ModificationDate);
					}
					else
					if (QName.valueOf(temp_slot_name).getLocalPart().equalsIgnoreCase("ResourceLocator"))
						onereturn.setResourceLocator(new URI((String)(temp_value[0])));
					else
					if (QName.valueOf(temp_slot_name).getLocalPart().equalsIgnoreCase("NumberOfClasses"))
						onereturn.setNumberOfClasses(new BigInteger((String)(temp_value[0])));
					else
					if (QName.valueOf(temp_slot_name).getLocalPart().equalsIgnoreCase("NumberOfProperties"))
						onereturn.setNumberOfProperties(new BigInteger((String)(temp_value[0])));
					else
					if (QName.valueOf(temp_slot_name).getLocalPart().equalsIgnoreCase("NumberOfIndividuals"))
						onereturn.setNumberOfIndividuals(new BigInteger((String)(temp_value[0])));
					else
					if (QName.valueOf(temp_slot_name).getLocalPart().equalsIgnoreCase("NumberOfAxioms"))
						onereturn.setNumberOfAxioms(new BigInteger((String)(temp_value[0])));
					else
					if (QName.valueOf(temp_slot_name).getLocalPart().equalsIgnoreCase("Version")) {
						FreeFormText temp_fft = new FreeFormText();
						temp_fft.setFreeFormText((String)(temp_value[0]));
						onereturn.setVersion(temp_fft);
					} else						
					if (QName.valueOf(temp_slot_name).getLocalPart().equalsIgnoreCase("IsConsistentAccordingToReasoner"))
						onereturn.setIsConsistentAccordingToReasoner(((String)temp_value[0]).equalsIgnoreCase("True"));
					 else
					if (QName.valueOf(temp_slot_name).getLocalPart().equalsIgnoreCase("ContainsABox"))
						onereturn.setContainsABox(((String)temp_value[0]).equalsIgnoreCase("True"));
					 else
					if (QName.valueOf(temp_slot_name).getLocalPart().equalsIgnoreCase("ContainsRBox"))
						onereturn.setContainsRBox(((String)temp_value[0]).equalsIgnoreCase("True"));
					 else
					if (QName.valueOf(temp_slot_name).getLocalPart().equalsIgnoreCase("ContainsTBox"))
						onereturn.setContainsTBox(((String)temp_value[0]).equalsIgnoreCase("True"));
					 else
					if (QName.valueOf(temp_slot_name).getLocalPart().equalsIgnoreCase("Expressiveness"))
						onereturn.setExpressiveness((String)(temp_value[0]));
					 else
					if (QName.valueOf(temp_slot_name).getLocalPart().equalsIgnoreCase("KeyClasses")) {
						for (Object onestr:temp_value)
							if (onestr instanceof String)
								onereturn.addKeyClasses((String)onestr);
					} else
					if (QName.valueOf(temp_slot_name).getLocalPart().equalsIgnoreCase("KnownUsage")) {
						for (Object onestr:temp_value)
							if (onestr instanceof String)
								onereturn.addKnownUsage((String)(onestr));
					} else
					if (QName.valueOf(temp_slot_name).getLocalPart().equalsIgnoreCase("Notes"))
						onereturn.setNotes((String)(temp_value[0]));

				}
		} catch (Exception e) {
			throw new Exception("Wrong information in the slots of the Ontology object: "+input.getKey().getId());
		}
		
		try {
			//goes through all the associations and sorts them in lists
			for (Object one_association: input.getAssociations()) {
				String temp_assoc_name=flattenInternationalString(((Association)one_association).getAssociationType().getName());
				String temp_target_class=getRegistryObjectClassificationName(((Association)one_association).getTargetObject());

				OMVObjectRefType new_objref = new OMVObjectRefType();
				new_objref.setId(makeReferenceURI(new URI(((Association)one_association).getTargetObject().getKey().getId())));
				
				
				if (QName.valueOf(temp_assoc_name).getLocalPart().equalsIgnoreCase("IsOfType"))
					onereturn.setIsOfType(new_objref.getId());
				else
				if (QName.valueOf(temp_assoc_name).getLocalPart().equalsIgnoreCase("Has")) {
					if (QName.valueOf(temp_target_class).getLocalPart().equalsIgnoreCase("OntologySyntax"))
						onereturn.setHasOntologySyntax(new_objref.getId());
					else
					if (QName.valueOf(temp_target_class).getLocalPart().equalsIgnoreCase("OntologyLanguage"))
						onereturn.addHasOntologyLanguage(new_objref);
					else
					if (QName.valueOf(temp_target_class).getLocalPart().equalsIgnoreCase("FormalityLevel"))
						onereturn.setHasFormalityLevel(new_objref.getId());
					else
					if (QName.valueOf(temp_target_class).getLocalPart().equalsIgnoreCase("LicenseModel"))
						onereturn.setHasLicense(new_objref.getId());
					else
					if (QName.valueOf(temp_target_class).getLocalPart().equalsIgnoreCase("OntologyDomain"))
						onereturn.addHasDomain(new_objref);
				}
				else
				if (QName.valueOf(temp_assoc_name).getLocalPart().equalsIgnoreCase("HasPriorVersion"))
					onereturn.setHasPriorVersion(new_objref.getId());
				else
				if (QName.valueOf(temp_assoc_name).getLocalPart().equalsIgnoreCase("HasContributor"))
					onereturn.addHasContributor(new_objref);
				else
				if (QName.valueOf(temp_assoc_name).getLocalPart().equalsIgnoreCase("HasCreator"))
					onereturn.addHasCreator(new_objref);
				else
				if (QName.valueOf(temp_assoc_name).getLocalPart().equalsIgnoreCase("Uses")) {
					if (QName.valueOf(temp_target_class).getLocalPart().equalsIgnoreCase("OntologyEngineeringTool"))
						onereturn.addUsedOntologyEngineeringTool(new_objref);
					else
					if (QName.valueOf(temp_target_class).getLocalPart().equalsIgnoreCase("OntologyEngineeringMethodology"))
						onereturn.addUsedOntologyEngineeringMethodology(new_objref);
					else
					if (QName.valueOf(temp_target_class).getLocalPart().equalsIgnoreCase("KnowledgeRepresentationParadigm"))
						onereturn.addUsedKnowledgeRepresentationParadigm(new_objref);
				}
				else
				if (QName.valueOf(temp_assoc_name).getLocalPart().equalsIgnoreCase("DesignedForOntologyTask"))
					onereturn.addDesignedForOntologyTask(new_objref);
				else
				if (QName.valueOf(temp_assoc_name).getLocalPart().equalsIgnoreCase("UseImports"))
					onereturn.addUseImports(new_objref);
				else
				if (QName.valueOf(temp_assoc_name).getLocalPart().equalsIgnoreCase("IsBackwardCompatibleWith"))
					onereturn.addIsBackwardCompatibleWith(new_objref);
				else
				if (QName.valueOf(temp_assoc_name).getLocalPart().equalsIgnoreCase("IsIncompatibleWith"))
					onereturn.addIsIncompatibleWith(new_objref);
				else
				if (QName.valueOf(temp_assoc_name).getLocalPart().equalsIgnoreCase("EndorsedBy"))
					onereturn.addEndorsedBy(new_objref);
			}
		}
		
		catch (Exception e) {
			throw new Exception("Wrong information in the associations of the Ontology object "+input.getKey().getId());
		}
    	
    	return onereturn;   	
    }
    */
    /**
     * Returns the name of the ObjectType of a registry object
     * 
     * @param input - a registry object
     * @return - the name of the ObjectType
     * @throws Exception
     */
    /*
    private static String getRegistryObjectClassificationName(RegistryObject input) throws Exception {
    	try {
	    	for (Object one_classification: input.getClassifications())
	    		if (flattenInternationalString(((Classification)one_classification).getClassificationScheme().getName()).equalsIgnoreCase("ObjectType"))
	    			return ((Classification)one_classification).getConcept().getValue();
    	} catch (Exception e) { 		
    	}

    	throw new Exception("There is an error in the classification of the registry object "+input.getKey().getId());
    }
    */
    /**
     * Copies the information from a JAXR object to an ebXML OMV object
     * 
     * @param input - a JAXR object
     * @param onereturn - an ebXML OMV object
     * @throws Exception
     */
    /*
    private static void translateOMVRegistryObjectType( RegistryObject input, OMVRegistryObjectType onereturn) throws Exception {
    	if (onereturn==null) return;

		// set ID (key)
		onereturn.setId(new URI(input.getKey().getId()));
    	
		// set Name 
		if (input.getName()!= null && flattenInternationalString(input.getName())!= null)						
			onereturn.setName(translate(input.getName()));
		// set Description
		if (input.getDescription()!= null && flattenInternationalString(input.getDescription())!= null)						
			onereturn.setDescription(translate(input.getDescription()));
 
		try {
			for (Object one_slot: input.getSlots())
				if (!((Slot)one_slot).getValues().isEmpty()) {
					Object temp_value = ((Slot)one_slot).getValues().iterator().next();
					
					if (QName.valueOf(((Slot)one_slot).getName()).getLocalPart().equalsIgnoreCase("Acronym")) {
						ShortName temp_acronym = new ShortName();
						temp_acronym.setShortName((String)temp_value);
						onereturn.setAcronym(temp_acronym);
					} else
					if (QName.valueOf(((Slot)one_slot).getName()).getLocalPart().equalsIgnoreCase("Documentation"))
						onereturn.setDocumentation(new URI((String)temp_value));
				}
		} catch (Exception e) {
			throw new Exception("Wrong information in the slots of the OMV object: "+input.getKey().getId());
		}
		
		try {
			if (!input.getClassifications().isEmpty()) {
				ArrayList<ClassificationType> temp5= new ArrayList<ClassificationType>();
	
				//loops all the classications and singles out those that are not an ObjectType
				for (Object temp_one_classification: input.getClassifications())
					if ((temp_one_classification!=null) && 
							!(flattenInternationalString(((Classification)temp_one_classification).getClassificationScheme().getName()).equalsIgnoreCase("ObjectType")))
						temp5.add(translate((Classification)temp_one_classification));
				
				onereturn.setClassification(temp5.toArray(new ClassificationType[1]));
			}
		} catch (Exception e) {
			throw new Exception("Wrong information in the classifications of the OMV object: "+input.getKey().getId());
		}
    }
    */
    /**
     * Translates a JAXR RegistryObject into an ebXML RegistryObjectType. This method is used for setting the 
     * basic properties of more complex objects like Concept, Classification and RegistryEntry. Note: translating 
     * external identifiers and external links is not supported yet.
     *
     * @param input - a JAXR RegistryObject from which the properties are read
     * @param onereturn - an ebXML RegistryObjectType which is updated
     * @throws Exception
     */
    /*
    private static void translateRegistryObjectType(RegistryObject input, RegistryObjectType onereturn) throws Exception {
    	if (onereturn==null) return;
		// set ID (key)
		onereturn.setId(new URI(input.getKey().getId()));
//		System.out.println(input.getKey().getId() +" - "+ flattenInternationalString(input.getName()));

		// set LID 
		onereturn.setLid(new URI(input.getKey().getId()));
		
		// set Name 
		if (input.getName()!= null && flattenInternationalString(input.getName())!= null)						
			onereturn.setName(translate(input.getName()));
		// set Description
		if (input.getDescription()!= null && flattenInternationalString(input.getDescription())!= null)						
			onereturn.setDescription(translate(input.getDescription()));
		// set ObjectType
		try {
			onereturn.setObjectType(makeReferenceURI(new URI(input.getObjectType().getKey().getId())));
		} catch (Exception e) {} 
		
		// set Slots
		if (!input.getSlots().isEmpty()) {
			ArrayList<SlotType1> temp2 = new ArrayList<SlotType1>(); 
			Iterator temp3 = input.getSlots().iterator();
			Slot temp4=null;
			
			while (temp3.hasNext())
				if ((temp4=(Slot)temp3.next())!=null)
					temp2.add(translateSlot(temp4));
			onereturn.setSlot(temp2.toArray(new SlotType1[1]));
		}
		//set Classifications
		if (!input.getClassifications().isEmpty()) {
			ArrayList<ClassificationType> temp5= new ArrayList<ClassificationType>();
			Iterator temp6 = input.getClassifications().iterator();
			Classification temp7;
				
			while (temp6.hasNext())
				if ((temp7=(Classification)temp6.next())!=null)
					temp5.add(translate(temp7));
			
			onereturn.setClassification(temp5.toArray(new ClassificationType[1]));
		}
    } 
    */
    /**
     * Translates a JAXR Slot into an ebXML SlotType1
     * 
     * @param input - a JAXR Slot
     * @return an ebXML SlotType1
     * @throws Exception
     */
    /*
    private static SlotType1 translateSlot(Slot input) throws Exception  {
    	SlotType1 res=new SlotType1();
    	LongName temp_ln=new LongName();

    	temp_ln.setLongName(input.getName());
    	res.setName(temp_ln);

    	try {
    		res.setSlotType(makeReferenceURI(new URI(input.getSlotType())));
    	}catch (Exception e) {    		
    	}
    	
    	if (input.getValues().size()>0) {
        	ValueListType temp_vlt = new ValueListType();
        	ValueListTypeSequence temp_vlts = new ValueListTypeSequence();
        	for (Object temp_input: input.getValues())
        		if (temp_input!=null) {
        			temp_ln=new LongName();
        			temp_ln.setLongName((String)temp_input);
        			temp_vlts.setValue(temp_ln);
        			temp_vlt.addValueListTypeSequence(temp_vlts);
	        	}
        	res.setValueList(temp_vlt);
    	}	
   	
    	return res;
    }
    */
    /**
     * Converts a JAXR InternationalString into a String by joining its LocalizedString-s.
     * 
     * @param value - a JAXR InternationalString
     * @return its value as a String 
     * @throws Exception - is case there is a convertion or class cast problem
     */
    /*
    public static String flattenInternationalString(InternationalString value) throws Exception {
    	String res="";
    	Iterator tempit=value.getLocalizedStrings().iterator();
    	while (tempit.hasNext())
    		res+=((javax.xml.registry.infomodel.LocalizedString)tempit.next()).getValue();
    	return res.equals("") ? null : res;
    }
    */
    /** 
     * Creates a new ebXML ReferenceURI
     * 
     * @param param - a URI
     * @return an ebXML ReferenceURI
     */
    public static ReferenceURI makeReferenceURI(URI param) {
    	ReferenceURI temp_refURI = new ReferenceURI();
    	temp_refURI.setReferenceURI(param);
    	return temp_refURI;
    }
	
    public static ReferenceURI makeReferenceURI4OMV(String ref) {
    	URI param;
    	String params = ref.replaceAll(" ","%20");
		try {
			
			/*
			if (params.contains("://")) param=new URI(params);
			else param = new URI("#"+params);
			 */
			param = new URI(params);
			ReferenceURI temp_refURI = new ReferenceURI();
	    	temp_refURI.setReferenceURI(param);
	    	
	    	return temp_refURI;
		} catch (MalformedURIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
}
