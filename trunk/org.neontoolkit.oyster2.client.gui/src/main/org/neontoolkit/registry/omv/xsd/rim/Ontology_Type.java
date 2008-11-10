/**
 * Ontology_Type.java
 *
 * This file was auto-generated from WSDL and modified by Raul Palma and David Munoz
 * to make some fields no longer required
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:58 LKT)
 */
package org.neontoolkit.registry.omv.xsd.rim;


/**
 *  Ontology_Type bean class
 */
public class Ontology_Type extends org.neontoolkit.registry.omv.xsd.rim.OMVRegistryObjectType
    implements org.apache.axis2.databinding.ADBBean {

	/**
     * field for Keywords
     * This was an Array!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType[] localKeywords;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localKeywordsTracker = false;

    /**
     * field for NaturalLanguage
     * This was an Array!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType[] localNaturalLanguage;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localNaturalLanguageTracker = false;

    /**
     * field for HasContributor
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] localHasContributor;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localHasContributorTracker = false;

    /**
     * field for HasCreator
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] localHasCreator;

    /**
     * field for UsedOntologyEngineeringTool
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] localUsedOntologyEngineeringTool;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localUsedOntologyEngineeringToolTracker = false;

    /**
     * field for UsedOntologyEngineeringMethodology
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] localUsedOntologyEngineeringMethodology;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localUsedOntologyEngineeringMethodologyTracker = false;

    /**
     * field for UsedKnowledgeRepresentationParadigm
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] localUsedKnowledgeRepresentationParadigm;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localUsedKnowledgeRepresentationParadigmTracker = false;

    /**
     * field for DesignedForOntologyTask
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] localDesignedForOntologyTask;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localDesignedForOntologyTaskTracker = false;

    /**
     * field for UseImports
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] localUseImports;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localUseImportsTracker = false;

    /**
     * field for IsBackwardCompatibleWith
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] localIsBackwardCompatibleWith;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localIsBackwardCompatibleWithTracker = false;

    /**
     * field for IsIncompatibleWith
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] localIsIncompatibleWith;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localIsIncompatibleWithTracker = false;

    /**
     * field for EndorsedBy
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] localEndorsedBy;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localEndorsedByTracker = false;

    /**
     * field for HasDomain
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] localHasDomain;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localHasDomainTracker = false;

    /**
     * field for HasOntologyLanguage
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] localHasOntologyLanguage;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localHasOntologyLanguageTracker = false;

    /**
     * field for KeyClasses
     * This was an Array!
     */
    protected java.lang.String[] localKeyClasses;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localKeyClassesTracker = false;

    /**
     * field for KnownUsage
     * This was an Array!
     */
    protected java.lang.String[] localKnownUsage;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localKnownUsageTracker = false;

    /**
     * field for IsOfType
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI localIsOfType;

    /**
     * field for HasOntologySyntax
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI localHasOntologySyntax;

    /**
     * field for HasLicense
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI localHasLicense;

    /**
     * field for HasFormalityLevel
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI localHasFormalityLevel;

    /**
     * field for HasPriorVersion
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI localHasPriorVersion;

    /**
     * field for NumberOfClasses
     * This was an Attribute!
     */
    protected java.math.BigInteger localNumberOfClasses;

    /**
     * field for NumberOfProperties
     * This was an Attribute!
     */
    protected java.math.BigInteger localNumberOfProperties;

    /**
     * field for NumberOfIndividuals
     * This was an Attribute!
     */
    protected java.math.BigInteger localNumberOfIndividuals;

    /**
     * field for NumberOfAxioms
     * This was an Attribute!
     */
    protected java.math.BigInteger localNumberOfAxioms;

    /**
     * field for OntologyStatus
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName localOntologyStatus;

    /**
     * field for CreationDate
     * This was an Attribute!
     */
    protected java.util.Calendar localCreationDate;

    /**
     * field for ModificationDate
     * This was an Attribute!
     */
    protected java.util.Calendar localModificationDate;

    /**
     * field for ResourceLocator
     * This was an Attribute!
     */
    protected org.apache.axis2.databinding.types.URI localResourceLocator;

    /**
     * field for URI
     * This was an Attribute!
     */
    protected org.apache.axis2.databinding.types.URI localURI;

    /**
     * field for Version
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.FreeFormText localVersion;

    /**
     * field for IsConsistentAccordingToReasoner
     * This was an Attribute!
     */
    protected Boolean localIsConsistentAccordingToReasoner;

    
    /**
     * field for ContainsABox
     * This was an Attribute!
     */
    protected Boolean localContainsABox;

    
    /**
     * field for ContainsRBox
     * This was an Attribute!
     */
    protected Boolean localContainsRBox;
    

    /**
     * field for ContainsTBox
     * This was an Attribute!
     */
    protected Boolean localContainsTBox;

    
    /**
     * field for Expressiveness
     * This was an Attribute!
     */
    protected java.lang.String localExpressiveness;

    /**
     * field for Notes
     * This was an Attribute!
     */
    protected java.lang.String localNotes;

    
   
    /* This type was generated from the piece of schema that had
       name = Ontology_Type
       Namespace URI = urn:neon-toolkit-org:registry:omv:xsd:rim:2.3
       Namespace Prefix = ns5
     */
    private static java.lang.String generatePrefix(java.lang.String namespace) {
        if (namespace.equals("urn:neon-toolkit-org:registry:omv:xsd:rim:2.3")) {
            return "ns5";
        }

        return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType[]
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType[] getKeywords() {
        return localKeywords;
    }

    /**
     * validate the array for Keywords
     */
    protected void validateKeywords(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param Keywords
     */
    public void setKeywords(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType[] param) {
        validateKeywords(param);

        if (param != null) {
            //update the setting tracker
            localKeywordsTracker = true;
        } else {
            localKeywordsTracker = false;
        }

        this.localKeywords = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType
     */
    public void addKeywords(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType param) {
        if (localKeywords == null) {
            localKeywords = new org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType[] {
                    
                };
        }

        //update the setting tracker
        localKeywordsTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localKeywords);
        list.add(param);
        this.localKeywords = (org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType[]) list.toArray(new org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType[]
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType[] getNaturalLanguage() {
        return localNaturalLanguage;
    }

    /**
     * validate the array for NaturalLanguage
     */
    protected void validateNaturalLanguage(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param NaturalLanguage
     */
    public void setNaturalLanguage(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType[] param) {
        validateNaturalLanguage(param);

        if (param != null) {
            //update the setting tracker
            localNaturalLanguageTracker = true;
        } else {
            localNaturalLanguageTracker = false;
        }

        this.localNaturalLanguage = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType
     */
    public void addNaturalLanguage(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType param) {
        if (localNaturalLanguage == null) {
            localNaturalLanguage = new org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType[] {
                    
                };
        }

        //update the setting tracker
        localNaturalLanguageTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localNaturalLanguage);
        list.add(param);
        this.localNaturalLanguage = (org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType[]) list.toArray(new org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]
     */
    public org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] getHasContributor() {
        return localHasContributor;
    }

    /**
     * validate the array for HasContributor
     */
    protected void validateHasContributor(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param HasContributor
     */
    public void setHasContributor(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
        validateHasContributor(param);

        if (param != null) {
            //update the setting tracker
            localHasContributorTracker = true;
        } else {
            localHasContributorTracker = false;
        }

        this.localHasContributor = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType
     */
    public void addHasContributor(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType param) {
        if (localHasContributor == null) {
            localHasContributor = new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] {
                    
                };
        }

        //update the setting tracker
        localHasContributorTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localHasContributor);
        list.add(param);
        this.localHasContributor = (org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]
     */
    public org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] getHasCreator() {
        return localHasCreator;
    }

    /**
     * validate the array for HasCreator
     */
    protected void validateHasCreator(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
        if ((param != null) && (param.length < 1)) {
            throw new java.lang.RuntimeException();
        }
    }

    /**
     * Auto generated setter method
     * @param param HasCreator
     */
    public void setHasCreator(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
        validateHasCreator(param);

        this.localHasCreator = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType
     */
    public void addHasCreator(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType param) {
        if (localHasCreator == null) {
            localHasCreator = new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] {
                    
                };
        }

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localHasCreator);
        list.add(param);
        this.localHasCreator = (org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]
     */
    public org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] getUsedOntologyEngineeringTool() {
        return localUsedOntologyEngineeringTool;
    }

    /**
     * validate the array for UsedOntologyEngineeringTool
     */
    protected void validateUsedOntologyEngineeringTool(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param UsedOntologyEngineeringTool
     */
    public void setUsedOntologyEngineeringTool(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
        validateUsedOntologyEngineeringTool(param);

        if (param != null) {
            //update the setting tracker
            localUsedOntologyEngineeringToolTracker = true;
        } else {
            localUsedOntologyEngineeringToolTracker = false;
        }

        this.localUsedOntologyEngineeringTool = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType
     */
    public void addUsedOntologyEngineeringTool(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType param) {
        if (localUsedOntologyEngineeringTool == null) {
            localUsedOntologyEngineeringTool = new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] {
                    
                };
        }

        //update the setting tracker
        localUsedOntologyEngineeringToolTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localUsedOntologyEngineeringTool);
        list.add(param);
        this.localUsedOntologyEngineeringTool = (org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]
     */
    public org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] getUsedOntologyEngineeringMethodology() {
        return localUsedOntologyEngineeringMethodology;
    }

    /**
     * validate the array for UsedOntologyEngineeringMethodology
     */
    protected void validateUsedOntologyEngineeringMethodology(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param UsedOntologyEngineeringMethodology
     */
    public void setUsedOntologyEngineeringMethodology(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
        validateUsedOntologyEngineeringMethodology(param);

        if (param != null) {
            //update the setting tracker
            localUsedOntologyEngineeringMethodologyTracker = true;
        } else {
            localUsedOntologyEngineeringMethodologyTracker = false;
        }

        this.localUsedOntologyEngineeringMethodology = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType
     */
    public void addUsedOntologyEngineeringMethodology(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType param) {
        if (localUsedOntologyEngineeringMethodology == null) {
            localUsedOntologyEngineeringMethodology = new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] {
                    
                };
        }

        //update the setting tracker
        localUsedOntologyEngineeringMethodologyTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localUsedOntologyEngineeringMethodology);
        list.add(param);
        this.localUsedOntologyEngineeringMethodology = (org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]
     */
    public org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] getUsedKnowledgeRepresentationParadigm() {
        return localUsedKnowledgeRepresentationParadigm;
    }

    /**
     * validate the array for UsedKnowledgeRepresentationParadigm
     */
    protected void validateUsedKnowledgeRepresentationParadigm(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param UsedKnowledgeRepresentationParadigm
     */
    public void setUsedKnowledgeRepresentationParadigm(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
        validateUsedKnowledgeRepresentationParadigm(param);

        if (param != null) {
            //update the setting tracker
            localUsedKnowledgeRepresentationParadigmTracker = true;
        } else {
            localUsedKnowledgeRepresentationParadigmTracker = false;
        }

        this.localUsedKnowledgeRepresentationParadigm = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType
     */
    public void addUsedKnowledgeRepresentationParadigm(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType param) {
        if (localUsedKnowledgeRepresentationParadigm == null) {
            localUsedKnowledgeRepresentationParadigm = new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] {
                    
                };
        }

        //update the setting tracker
        localUsedKnowledgeRepresentationParadigmTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localUsedKnowledgeRepresentationParadigm);
        list.add(param);
        this.localUsedKnowledgeRepresentationParadigm = (org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]
     */
    public org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] getDesignedForOntologyTask() {
        return localDesignedForOntologyTask;
    }

    /**
     * validate the array for DesignedForOntologyTask
     */
    protected void validateDesignedForOntologyTask(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param DesignedForOntologyTask
     */
    public void setDesignedForOntologyTask(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
        validateDesignedForOntologyTask(param);

        if (param != null) {
            //update the setting tracker
            localDesignedForOntologyTaskTracker = true;
        } else {
            localDesignedForOntologyTaskTracker = false;
        }

        this.localDesignedForOntologyTask = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType
     */
    public void addDesignedForOntologyTask(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType param) {
        if (localDesignedForOntologyTask == null) {
            localDesignedForOntologyTask = new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] {
                    
                };
        }

        //update the setting tracker
        localDesignedForOntologyTaskTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localDesignedForOntologyTask);
        list.add(param);
        this.localDesignedForOntologyTask = (org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]
     */
    public org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] getUseImports() {
        return localUseImports;
    }

    /**
     * validate the array for UseImports
     */
    protected void validateUseImports(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param UseImports
     */
    public void setUseImports(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
        validateUseImports(param);

        if (param != null) {
            //update the setting tracker
            localUseImportsTracker = true;
        } else {
            localUseImportsTracker = false;
        }

        this.localUseImports = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType
     */
    public void addUseImports(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType param) {
        if (localUseImports == null) {
            localUseImports = new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] {
                    
                };
        }

        //update the setting tracker
        localUseImportsTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localUseImports);
        list.add(param);
        this.localUseImports = (org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]
     */
    public org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] getIsBackwardCompatibleWith() {
        return localIsBackwardCompatibleWith;
    }

    /**
     * validate the array for IsBackwardCompatibleWith
     */
    protected void validateIsBackwardCompatibleWith(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param IsBackwardCompatibleWith
     */
    public void setIsBackwardCompatibleWith(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
        validateIsBackwardCompatibleWith(param);

        if (param != null) {
            //update the setting tracker
            localIsBackwardCompatibleWithTracker = true;
        } else {
            localIsBackwardCompatibleWithTracker = false;
        }

        this.localIsBackwardCompatibleWith = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType
     */
    public void addIsBackwardCompatibleWith(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType param) {
        if (localIsBackwardCompatibleWith == null) {
            localIsBackwardCompatibleWith = new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] {
                    
                };
        }

        //update the setting tracker
        localIsBackwardCompatibleWithTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localIsBackwardCompatibleWith);
        list.add(param);
        this.localIsBackwardCompatibleWith = (org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]
     */
    public org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] getIsIncompatibleWith() {
        return localIsIncompatibleWith;
    }

    /**
     * validate the array for IsIncompatibleWith
     */
    protected void validateIsIncompatibleWith(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param IsIncompatibleWith
     */
    public void setIsIncompatibleWith(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
        validateIsIncompatibleWith(param);

        if (param != null) {
            //update the setting tracker
            localIsIncompatibleWithTracker = true;
        } else {
            localIsIncompatibleWithTracker = false;
        }

        this.localIsIncompatibleWith = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType
     */
    public void addIsIncompatibleWith(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType param) {
        if (localIsIncompatibleWith == null) {
            localIsIncompatibleWith = new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] {
                    
                };
        }

        //update the setting tracker
        localIsIncompatibleWithTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localIsIncompatibleWith);
        list.add(param);
        this.localIsIncompatibleWith = (org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]
     */
    public org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] getEndorsedBy() {
        return localEndorsedBy;
    }

    /**
     * validate the array for EndorsedBy
     */
    protected void validateEndorsedBy(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param EndorsedBy
     */
    public void setEndorsedBy(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
        validateEndorsedBy(param);

        if (param != null) {
            //update the setting tracker
            localEndorsedByTracker = true;
        } else {
            localEndorsedByTracker = false;
        }

        this.localEndorsedBy = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType
     */
    public void addEndorsedBy(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType param) {
        if (localEndorsedBy == null) {
            localEndorsedBy = new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] {
                    
                };
        }

        //update the setting tracker
        localEndorsedByTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localEndorsedBy);
        list.add(param);
        this.localEndorsedBy = (org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]
     */
    public org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] getHasDomain() {
        return localHasDomain;
    }

    /**
     * validate the array for HasDomain
     */
    protected void validateHasDomain(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param HasDomain
     */
    public void setHasDomain(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
        validateHasDomain(param);

        if (param != null) {
            //update the setting tracker
            localHasDomainTracker = true;
        } else {
            localHasDomainTracker = false;
        }

        this.localHasDomain = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType
     */
    public void addHasDomain(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType param) {
        if (localHasDomain == null) {
            localHasDomain = new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] {
                    
                };
        }

        //update the setting tracker
        localHasDomainTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localHasDomain);
        list.add(param);
        this.localHasDomain = (org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]
     */
    public org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] getHasOntologyLanguage() {
        return localHasOntologyLanguage;
    }

    /**
     * validate the array for HasOntologyLanguage
     */
    protected void validateHasOntologyLanguage(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param HasOntologyLanguage
     */
    public void setHasOntologyLanguage(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] param) {
        validateHasOntologyLanguage(param);

        if (param != null) {
            //update the setting tracker
            localHasOntologyLanguageTracker = true;
        } else {
            localHasOntologyLanguageTracker = false;
        }

        this.localHasOntologyLanguage = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType
     */
    public void addHasOntologyLanguage(
        org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType param) {
        if (localHasOntologyLanguage == null) {
            localHasOntologyLanguage = new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[] {
                    
                };
        }

        //update the setting tracker
        localHasOntologyLanguageTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localHasOntologyLanguage);
        list.add(param);
        this.localHasOntologyLanguage = (org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return java.lang.String[]
     */
    public java.lang.String[] getKeyClasses() {
        return localKeyClasses;
    }

    /**
     * validate the array for KeyClasses
     */
    protected void validateKeyClasses(java.lang.String[] param) {
    }

    /**
     * Auto generated setter method
     * @param param KeyClasses
     */
    public void setKeyClasses(java.lang.String[] param) {
        validateKeyClasses(param);

        if (param != null) {
            //update the setting tracker
            localKeyClassesTracker = true;
        } else {
            localKeyClassesTracker = false;
        }

        this.localKeyClasses = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param java.lang.String
     */
    public void addKeyClasses(java.lang.String param) {
        if (localKeyClasses == null) {
            localKeyClasses = new java.lang.String[] {  };
        }

        //update the setting tracker
        localKeyClassesTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localKeyClasses);
        list.add(param);
        this.localKeyClasses = (java.lang.String[]) list.toArray(new java.lang.String[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return java.lang.String[]
     */
    public java.lang.String[] getKnownUsage() {
        return localKnownUsage;
    }

    /**
     * validate the array for KnownUsage
     */
    protected void validateKnownUsage(java.lang.String[] param) {
    }

    /**
     * Auto generated setter method
     * @param param KnownUsage
     */
    public void setKnownUsage(java.lang.String[] param) {
        validateKnownUsage(param);

        if (param != null) {
            //update the setting tracker
            localKnownUsageTracker = true;
        } else {
            localKnownUsageTracker = false;
        }

        this.localKnownUsage = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param java.lang.String
     */
    public void addKnownUsage(java.lang.String param) {
        if (localKnownUsage == null) {
            localKnownUsage = new java.lang.String[] {  };
        }

        //update the setting tracker
        localKnownUsageTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localKnownUsage);
        list.add(param);
        this.localKnownUsage = (java.lang.String[]) list.toArray(new java.lang.String[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI getIsOfType() {
        return localIsOfType;
    }

    /**
     * Auto generated setter method
     * @param param IsOfType
     */
    public void setIsOfType(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI param) {
        this.localIsOfType = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI getHasOntologySyntax() {
        return localHasOntologySyntax;
    }

    /**
     * Auto generated setter method
     * @param param HasOntologySyntax
     */
    public void setHasOntologySyntax(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI param) {
        this.localHasOntologySyntax = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI getHasLicense() {
        return localHasLicense;
    }

    /**
     * Auto generated setter method
     * @param param HasLicense
     */
    public void setHasLicense(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI param) {
        this.localHasLicense = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI getHasFormalityLevel() {
        return localHasFormalityLevel;
    }

    /**
     * Auto generated setter method
     * @param param HasFormalityLevel
     */
    public void setHasFormalityLevel(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI param) {
        this.localHasFormalityLevel = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI getHasPriorVersion() {
        return localHasPriorVersion;
    }

    /**
     * Auto generated setter method
     * @param param HasPriorVersion
     */
    public void setHasPriorVersion(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI param) {
        this.localHasPriorVersion = param;
    }

    /**
     * Auto generated getter method
     * @return java.math.BigInteger
     */
    public java.math.BigInteger getNumberOfClasses() {
        return localNumberOfClasses;
    }

    /**
     * Auto generated setter method
     * @param param NumberOfClasses
     */
    public void setNumberOfClasses(java.math.BigInteger param) {
        this.localNumberOfClasses = param;
    }

    /**
     * Auto generated getter method
     * @return java.math.BigInteger
     */
    public java.math.BigInteger getNumberOfProperties() {
        return localNumberOfProperties;
    }

    /**
     * Auto generated setter method
     * @param param NumberOfProperties
     */
    public void setNumberOfProperties(java.math.BigInteger param) {
        this.localNumberOfProperties = param;
    }

    /**
     * Auto generated getter method
     * @return java.math.BigInteger
     */
    public java.math.BigInteger getNumberOfIndividuals() {
        return localNumberOfIndividuals;
    }

    /**
     * Auto generated setter method
     * @param param NumberOfIndividuals
     */
    public void setNumberOfIndividuals(java.math.BigInteger param) {
        this.localNumberOfIndividuals = param;
    }

    /**
     * Auto generated getter method
     * @return java.math.BigInteger
     */
    public java.math.BigInteger getNumberOfAxioms() {
        return localNumberOfAxioms;
    }

    /**
     * Auto generated setter method
     * @param param NumberOfAxioms
     */
    public void setNumberOfAxioms(java.math.BigInteger param) {
        this.localNumberOfAxioms = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName getOntologyStatus() {
        return localOntologyStatus;
    }

    /**
     * Auto generated setter method
     * @param param OntologyStatus
     */
    public void setOntologyStatus(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName param) {
        this.localOntologyStatus = param;
    }

    /**
     * Auto generated getter method
     * @return java.util.Calendar
     */
    public java.util.Calendar getCreationDate() {
        return localCreationDate;
    }

    /**
     * Auto generated setter method
     * @param param CreationDate
     */
    public void setCreationDate(java.util.Calendar param) {
        this.localCreationDate = param;
    }

    /**
     * Auto generated getter method
     * @return java.util.Calendar
     */
    public java.util.Calendar getModificationDate() {
        return localModificationDate;
    }

    /**
     * Auto generated setter method
     * @param param ModificationDate
     */
    public void setModificationDate(java.util.Calendar param) {
        this.localModificationDate = param;
    }

    /**
     * Auto generated getter method
     * @return org.apache.axis2.databinding.types.URI
     */
    public org.apache.axis2.databinding.types.URI getResourceLocator() {
        return localResourceLocator;
    }

    /**
     * Auto generated setter method
     * @param param ResourceLocator
     */
    public void setResourceLocator(org.apache.axis2.databinding.types.URI param) {
        this.localResourceLocator = param;
    }

    /**
     * Auto generated getter method
     * @return org.apache.axis2.databinding.types.URI
     */
    public org.apache.axis2.databinding.types.URI getURI() {
        return localURI;
    }

    /**
     * Auto generated setter method
     * @param param URI
     */
    public void setURI(org.apache.axis2.databinding.types.URI param) {
        this.localURI = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.FreeFormText
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.FreeFormText getVersion() {
        return localVersion;
    }

    /**
     * Auto generated setter method
     * @param param Version
     */
    public void setVersion(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.FreeFormText param) {
        this.localVersion = param;
    }

    /**
     * Auto generated getter method
     * @return boolean
     */
    public Boolean getIsConsistentAccordingToReasoner() {
        return localIsConsistentAccordingToReasoner;
    }
    
    

    /**
     * Auto generated setter method
     * @param param IsConsistentAccordingToReasoner
     */
    public void setIsConsistentAccordingToReasoner(boolean param) {
    	
        this.localIsConsistentAccordingToReasoner = param;
    }

    /**
     * Auto generated getter method
     * @return boolean
     */
    public Boolean getContainsABox() {
        return localContainsABox;
    }

    
    /**
     * Auto generated setter method
     * @param param ContainsABox
     */
    public void setContainsABox(boolean param) {
    	
        this.localContainsABox = param;
    }

    /**
     * Auto generated getter method
     * @return boolean
     */
    public Boolean getContainsRBox() {
        return localContainsRBox;
    }

    /**
     * Auto generated setter method
     * @param param ContainsRBox
     */
    public void setContainsRBox(boolean param) {
    	
        this.localContainsRBox = param;
    }

    /**
     * Auto generated getter method
     * @return boolean
     */
    public Boolean getContainsTBox() {
        return localContainsTBox;
    }

    
    /**
     * Auto generated setter method
     * @param param ContainsTBox
     */
    public void setContainsTBox(boolean param) {
    	
        this.localContainsTBox = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getExpressiveness() {
        return localExpressiveness;
    }

    /**
     * Auto generated setter method
     * @param param Expressiveness
     */
    public void setExpressiveness(java.lang.String param) {
        this.localExpressiveness = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getNotes() {
        return localNotes;
    }

    /**
     * Auto generated setter method
     * @param param Notes
     */
    public void setNotes(java.lang.String param) {
        this.localNotes = param;
    }

    /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
    public static boolean isReaderMTOMAware(
        javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;

        try {
            isReaderMTOMAware = java.lang.Boolean.TRUE.equals(reader.getProperty(
                        org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        } catch (java.lang.IllegalArgumentException e) {
            isReaderMTOMAware = false;
        }

        return isReaderMTOMAware;
    }

    /**
     *
     * @param parentQName
     * @param factory
     * @return org.apache.axiom.om.OMElement
     */
    public org.apache.axiom.om.OMElement getOMElement(
        final javax.xml.namespace.QName parentQName,
        final org.apache.axiom.om.OMFactory factory)
        throws org.apache.axis2.databinding.ADBException {
        org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(this,
                parentQName) {
                public void serialize(
                    org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                    throws javax.xml.stream.XMLStreamException {
                    Ontology_Type.this.serialize(parentQName, factory, xmlWriter);
                }
            };

        return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(parentQName,
            factory, dataSource);
    }

    public void serialize(final javax.xml.namespace.QName parentQName,
        final org.apache.axiom.om.OMFactory factory,
        org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException,
            org.apache.axis2.databinding.ADBException {
        java.lang.String prefix = null;
        java.lang.String namespace = null;

        prefix = parentQName.getPrefix();
        namespace = parentQName.getNamespaceURI();

        if (namespace != null) {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace,
                    parentQName.getLocalPart());
            } else {
                if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(),
                    namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        } else {
            xmlWriter.writeStartElement(parentQName.getLocalPart());
        }

        java.lang.String namespacePrefix = registerPrefix(xmlWriter,
                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3");

        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "type", namespacePrefix + ":Ontology_Type", xmlWriter);
        } else {
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "type", "Ontology_Type", xmlWriter);
        }

        if (localId != null) {
            writeAttribute("", "id",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localId), xmlWriter);
        } else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localId is null");
        }

        if (localHome != null) {
            writeAttribute("", "home",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localHome), xmlWriter);
        }

        if (localLid != null) {
            writeAttribute("", "lid",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localLid), xmlWriter);
        }

        if (localObjectType != null) {
            writeAttribute("", "objectType", localObjectType.toString(),
                xmlWriter);
        }

        if (localStatus != null) {
            writeAttribute("", "status", localStatus.toString(), xmlWriter);
        }

        if (localAcronym != null) {
            writeAttribute("", "acronym", localAcronym.toString(), xmlWriter);
        }

        if (localDocumentation != null) {
            writeAttribute("", "documentation",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localDocumentation), xmlWriter);
        }

        if (localIsOfType != null) {
            writeAttribute("", "isOfType", localIsOfType.toString(), xmlWriter);
        } //else {
          //  throw new org.apache.axis2.databinding.ADBException(
          //      "required attribute localIsOfType is null");
        //}

        if (localHasOntologySyntax != null) {
            writeAttribute("", "hasOntologySyntax",
                localHasOntologySyntax.toString(), xmlWriter);
        } //else {
          //  throw new org.apache.axis2.databinding.ADBException(
          //      "required attribute localHasOntologySyntax is null");
        //}

        if (localHasLicense != null) {
            writeAttribute("", "hasLicense", localHasLicense.toString(),
                xmlWriter);
        }

        if (localHasFormalityLevel != null) {
            writeAttribute("", "hasFormalityLevel",
                localHasFormalityLevel.toString(), xmlWriter);
        }

        if (localHasPriorVersion != null) {
            writeAttribute("", "hasPriorVersion",
                localHasPriorVersion.toString(), xmlWriter);
        }

        if (localNumberOfClasses != null) {
            writeAttribute("", "numberOfClasses",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localNumberOfClasses), xmlWriter);
        } //else {
          //  throw new org.apache.axis2.databinding.ADBException(
          //      "required attribute localNumberOfClasses is null");
        //}

        if (localNumberOfProperties != null) {
            writeAttribute("", "numberOfProperties",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localNumberOfProperties), xmlWriter);
        } //else {
          //  throw new org.apache.axis2.databinding.ADBException(
          //      "required attribute localNumberOfProperties is null");
        //}

        if (localNumberOfIndividuals != null) {
            writeAttribute("", "numberOfIndividuals",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localNumberOfIndividuals), xmlWriter);
        } //else {
          //  throw new org.apache.axis2.databinding.ADBException(
          //      "required attribute localNumberOfIndividuals is null");
        //}

        if (localNumberOfAxioms != null) {
            writeAttribute("", "numberOfAxioms",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localNumberOfAxioms), xmlWriter);
        } //else {
          //  throw new org.apache.axis2.databinding.ADBException(
          //      "required attribute localNumberOfAxioms is null");
        //}

        if (localOntologyStatus != null) {
            writeAttribute("", "ontologyStatus",
                localOntologyStatus.toString(), xmlWriter);
        }

        if (localCreationDate != null) {
            writeAttribute("", "creationDate",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localCreationDate), xmlWriter);
        } //else {
          //  throw new org.apache.axis2.databinding.ADBException(
          //      "required attribute localCreationDate is null");
        //}

        if (localModificationDate != null) {
            writeAttribute("", "modificationDate",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localModificationDate), xmlWriter);
        }

        if (localResourceLocator != null) {
            writeAttribute("", "resourceLocator",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localResourceLocator), xmlWriter);
        } else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localResourceLocator is null");
        }

        if (localURI != null) {
            writeAttribute("", "URI",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localURI), xmlWriter);
        } else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localURI is null");
        }

        if (localVersion != null) {
            writeAttribute("", "version", localVersion.toString(), xmlWriter);
        } //else {
          //  throw new org.apache.axis2.databinding.ADBException(
          //      "required attribute localVersion is null");
        //}

        if (localIsConsistentAccordingToReasoner!=null) {
            writeAttribute("", "isConsistentAccordingToReasoner",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localIsConsistentAccordingToReasoner), xmlWriter);
        }

        if (localContainsABox != null) {
            writeAttribute("", "containsABox",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localContainsABox), xmlWriter);
        }

        if (localContainsRBox != null) {
            writeAttribute("", "containsRBox",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localContainsRBox), xmlWriter);
        }

        if (localContainsTBox != null) {
            writeAttribute("", "containsTBox",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localContainsTBox), xmlWriter);
        }

        if (localExpressiveness != null) {
            writeAttribute("", "expressiveness",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localExpressiveness), xmlWriter);
        }

        if (localNotes != null) {
            writeAttribute("", "notes",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localNotes), xmlWriter);
        }

        if (localSlotTracker) {
            if (localSlot != null) {
                for (int i = 0; i < localSlot.length; i++) {
                    if (localSlot[i] != null) {
                        localSlot[i].serialize(new javax.xml.namespace.QName(
                                "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                                "Slot"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "Slot cannot be null!!");
            }
        }

        if (localNameTracker) {
            if (localName == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "Name cannot be null!!");
            }

            localName.serialize(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "Name"),
                factory, xmlWriter);
        }

        if (localDescriptionTracker) {
            if (localDescription == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "Description cannot be null!!");
            }

            localDescription.serialize(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "Description"),
                factory, xmlWriter);
        }

        if (localVersionInfoTracker) {
            if (localVersionInfo == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "VersionInfo cannot be null!!");
            }

            localVersionInfo.serialize(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "VersionInfo"),
                factory, xmlWriter);
        }

        if (localClassificationTracker) {
            if (localClassification != null) {
                for (int i = 0; i < localClassification.length; i++) {
                    if (localClassification[i] != null) {
                        localClassification[i].serialize(new javax.xml.namespace.QName(
                                "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                                "Classification"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "Classification cannot be null!!");
            }
        }

        if (localExternalIdentifierTracker) {
            if (localExternalIdentifier != null) {
                for (int i = 0; i < localExternalIdentifier.length; i++) {
                    if (localExternalIdentifier[i] != null) {
                        localExternalIdentifier[i].serialize(new javax.xml.namespace.QName(
                                "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                                "ExternalIdentifier"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "ExternalIdentifier cannot be null!!");
            }
        }

        if (localKeywordsTracker) {
            if (localKeywords != null) {
                for (int i = 0; i < localKeywords.length; i++) {
                    if (localKeywords[i] != null) {
                        localKeywords[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "Keywords"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "Keywords cannot be null!!");
            }
        }

        if (localNaturalLanguageTracker) {
            if (localNaturalLanguage != null) {
                for (int i = 0; i < localNaturalLanguage.length; i++) {
                    if (localNaturalLanguage[i] != null) {
                        localNaturalLanguage[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "NaturalLanguage"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "NaturalLanguage cannot be null!!");
            }
        }

        if (localHasContributorTracker) {
            if (localHasContributor != null) {
                for (int i = 0; i < localHasContributor.length; i++) {
                    if (localHasContributor[i] != null) {
                        localHasContributor[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "hasContributor"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "hasContributor cannot be null!!");
            }
        }

        if (localHasCreator != null) {
            for (int i = 0; i < localHasCreator.length; i++) {
                if (localHasCreator[i] != null) {
                    localHasCreator[i].serialize(new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                            "hasCreator"), factory, xmlWriter);
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "hasCreator cannot be null!!");
                }
            }
        } //else {
          //  throw new org.apache.axis2.databinding.ADBException(
          //      "hasCreator cannot be null!!");
        //}

        if (localUsedOntologyEngineeringToolTracker) {
            if (localUsedOntologyEngineeringTool != null) {
                for (int i = 0; i < localUsedOntologyEngineeringTool.length;
                        i++) {
                    if (localUsedOntologyEngineeringTool[i] != null) {
                        localUsedOntologyEngineeringTool[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "usedOntologyEngineeringTool"), factory,
                            xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "usedOntologyEngineeringTool cannot be null!!");
            }
        }

        if (localUsedOntologyEngineeringMethodologyTracker) {
            if (localUsedOntologyEngineeringMethodology != null) {
                for (int i = 0;
                        i < localUsedOntologyEngineeringMethodology.length;
                        i++) {
                    if (localUsedOntologyEngineeringMethodology[i] != null) {
                        localUsedOntologyEngineeringMethodology[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "usedOntologyEngineeringMethodology"), factory,
                            xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "usedOntologyEngineeringMethodology cannot be null!!");
            }
        }

        if (localUsedKnowledgeRepresentationParadigmTracker) {
            if (localUsedKnowledgeRepresentationParadigm != null) {
                for (int i = 0;
                        i < localUsedKnowledgeRepresentationParadigm.length;
                        i++) {
                    if (localUsedKnowledgeRepresentationParadigm[i] != null) {
                        localUsedKnowledgeRepresentationParadigm[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "usedKnowledgeRepresentationParadigm"),
                            factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "usedKnowledgeRepresentationParadigm cannot be null!!");
            }
        }

        if (localDesignedForOntologyTaskTracker) {
            if (localDesignedForOntologyTask != null) {
                for (int i = 0; i < localDesignedForOntologyTask.length; i++) {
                    if (localDesignedForOntologyTask[i] != null) {
                        localDesignedForOntologyTask[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "designedForOntologyTask"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "designedForOntologyTask cannot be null!!");
            }
        }

        if (localUseImportsTracker) {
            if (localUseImports != null) {
                for (int i = 0; i < localUseImports.length; i++) {
                    if (localUseImports[i] != null) {
                        localUseImports[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "useImports"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "useImports cannot be null!!");
            }
        }

        if (localIsBackwardCompatibleWithTracker) {
            if (localIsBackwardCompatibleWith != null) {
                for (int i = 0; i < localIsBackwardCompatibleWith.length;
                        i++) {
                    if (localIsBackwardCompatibleWith[i] != null) {
                        localIsBackwardCompatibleWith[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "isBackwardCompatibleWith"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "isBackwardCompatibleWith cannot be null!!");
            }
        }

        if (localIsIncompatibleWithTracker) {
            if (localIsIncompatibleWith != null) {
                for (int i = 0; i < localIsIncompatibleWith.length; i++) {
                    if (localIsIncompatibleWith[i] != null) {
                        localIsIncompatibleWith[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "isIncompatibleWith"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "isIncompatibleWith cannot be null!!");
            }
        }

        if (localEndorsedByTracker) {
            if (localEndorsedBy != null) {
                for (int i = 0; i < localEndorsedBy.length; i++) {
                    if (localEndorsedBy[i] != null) {
                        localEndorsedBy[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "endorsedBy"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "endorsedBy cannot be null!!");
            }
        }

        if (localHasDomainTracker) {
            if (localHasDomain != null) {
                for (int i = 0; i < localHasDomain.length; i++) {
                    if (localHasDomain[i] != null) {
                        localHasDomain[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "hasDomain"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "hasDomain cannot be null!!");
            }
        }

        if (localHasOntologyLanguageTracker) {
            if (localHasOntologyLanguage != null) {
                for (int i = 0; i < localHasOntologyLanguage.length; i++) {
                    if (localHasOntologyLanguage[i] != null) {
                        localHasOntologyLanguage[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "hasOntologyLanguage"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "hasOntologyLanguage cannot be null!!");
            }
        }

        if (localKeyClassesTracker) {
            if (localKeyClasses != null) {
                namespace = "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3";

                boolean emptyNamespace = (namespace == null) ||
                    (namespace.length() == 0);
                prefix = emptyNamespace ? null : xmlWriter.getPrefix(namespace);

                for (int i = 0; i < localKeyClasses.length; i++) {
                    if (localKeyClasses[i] != null) {
                        if (!emptyNamespace) {
                            if (prefix == null) {
                                java.lang.String prefix2 = generatePrefix(namespace);

                                xmlWriter.writeStartElement(prefix2,
                                    "keyClasses", namespace);
                                xmlWriter.writeNamespace(prefix2, namespace);
                                xmlWriter.setPrefix(prefix2, namespace);
                            } else {
                                xmlWriter.writeStartElement(namespace,
                                    "keyClasses");
                            }
                        } else {
                            xmlWriter.writeStartElement("keyClasses");
                        }

                        xmlWriter.writeCharacters(localKeyClasses[i]);

                        xmlWriter.writeEndElement();
                    } else {
                        // we have to do nothing since minOccurs is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "keyClasses cannot be null!!");
            }
        }

        if (localKnownUsageTracker) {
            if (localKnownUsage != null) {
                namespace = "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3";

                boolean emptyNamespace = (namespace == null) ||
                    (namespace.length() == 0);
                prefix = emptyNamespace ? null : xmlWriter.getPrefix(namespace);

                for (int i = 0; i < localKnownUsage.length; i++) {
                    if (localKnownUsage[i] != null) {
                        if (!emptyNamespace) {
                            if (prefix == null) {
                                java.lang.String prefix2 = generatePrefix(namespace);

                                xmlWriter.writeStartElement(prefix2,
                                    "knownUsage", namespace);
                                xmlWriter.writeNamespace(prefix2, namespace);
                                xmlWriter.setPrefix(prefix2, namespace);
                            } else {
                                xmlWriter.writeStartElement(namespace,
                                    "knownUsage");
                            }
                        } else {
                            xmlWriter.writeStartElement("knownUsage");
                        }

                        xmlWriter.writeCharacters(localKnownUsage[i]);

                        xmlWriter.writeEndElement();
                    } else {
                        // we have to do nothing since minOccurs is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "knownUsage cannot be null!!");
            }
        }

        xmlWriter.writeEndElement();
    }

    /**
     * Util method to write an attribute with the ns prefix
     */
    private void writeAttribute(java.lang.String prefix,
        java.lang.String namespace, java.lang.String attName,
        java.lang.String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        if (xmlWriter.getPrefix(namespace) == null) {
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
        }

        xmlWriter.writeAttribute(namespace, attName, attValue);
    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeAttribute(java.lang.String namespace,
        java.lang.String attName, java.lang.String attValue,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        if (namespace.equals("")) {
            xmlWriter.writeAttribute(attName, attValue);
        } else {
            registerPrefix(xmlWriter, namespace);
            xmlWriter.writeAttribute(namespace, attName, attValue);
        }
    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeQNameAttribute(java.lang.String namespace,
        java.lang.String attName, javax.xml.namespace.QName qname,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String attributeNamespace = qname.getNamespaceURI();
        java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

        if (attributePrefix == null) {
            attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
        }

        java.lang.String attributeValue;

        if (attributePrefix.trim().length() > 0) {
            attributeValue = attributePrefix + ":" + qname.getLocalPart();
        } else {
            attributeValue = qname.getLocalPart();
        }

        if (namespace.equals("")) {
            xmlWriter.writeAttribute(attName, attributeValue);
        } else {
            registerPrefix(xmlWriter, namespace);
            xmlWriter.writeAttribute(namespace, attName, attributeValue);
        }
    }

    /**
     *  method to handle Qnames
     */
    private void writeQName(javax.xml.namespace.QName qname,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String namespaceURI = qname.getNamespaceURI();

        if (namespaceURI != null) {
            java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

            if (prefix == null) {
                prefix = generatePrefix(namespaceURI);
                xmlWriter.writeNamespace(prefix, namespaceURI);
                xmlWriter.setPrefix(prefix, namespaceURI);
            }

            if (prefix.trim().length() > 0) {
                xmlWriter.writeCharacters(prefix + ":" +
                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            } else {
                // i.e this is the default namespace
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        } else {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    qname));
        }
    }

    private void writeQNames(javax.xml.namespace.QName[] qnames,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        if (qnames != null) {
            // we have to store this data until last moment since it is not possible to write any
            // namespace data after writing the charactor data
            java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
            java.lang.String namespaceURI = null;
            java.lang.String prefix = null;

            for (int i = 0; i < qnames.length; i++) {
                if (i > 0) {
                    stringToWrite.append(" ");
                }

                namespaceURI = qnames[i].getNamespaceURI();

                if (namespaceURI != null) {
                    prefix = xmlWriter.getPrefix(namespaceURI);

                    if ((prefix == null) || (prefix.length() == 0)) {
                        prefix = generatePrefix(namespaceURI);
                        xmlWriter.writeNamespace(prefix, namespaceURI);
                        xmlWriter.setPrefix(prefix, namespaceURI);
                    }

                    if (prefix.trim().length() > 0) {
                        stringToWrite.append(prefix).append(":")
                                     .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                } else {
                    stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qnames[i]));
                }
            }

            xmlWriter.writeCharacters(stringToWrite.toString());
        }
    }

    /**
     * Register a namespace prefix
     */
    private java.lang.String registerPrefix(
        javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String prefix = xmlWriter.getPrefix(namespace);

        if (prefix == null) {
            prefix = generatePrefix(namespace);

            while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
            }

            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
        }

        return prefix;
    }

    /**
     * databinding method to get an XML representation of this object
     *
     */
    public javax.xml.stream.XMLStreamReader getPullParser(
        javax.xml.namespace.QName qName)
        throws org.apache.axis2.databinding.ADBException {
        java.util.ArrayList elementList = new java.util.ArrayList();
        java.util.ArrayList attribList = new java.util.ArrayList();

        attribList.add(new javax.xml.namespace.QName(
                "http://www.w3.org/2001/XMLSchema-instance", "type"));
        attribList.add(new javax.xml.namespace.QName(
                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3", "Ontology_Type"));

        if (localSlotTracker) {
            if (localSlot != null) {
                for (int i = 0; i < localSlot.length; i++) {
                    if (localSlot[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                                "Slot"));
                        elementList.add(localSlot[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "Slot cannot be null!!");
            }
        }

        if (localNameTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "Name"));

            if (localName == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "Name cannot be null!!");
            }

            elementList.add(localName);
        }

        if (localDescriptionTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "Description"));

            if (localDescription == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "Description cannot be null!!");
            }

            elementList.add(localDescription);
        }

        if (localVersionInfoTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "VersionInfo"));

            if (localVersionInfo == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "VersionInfo cannot be null!!");
            }

            elementList.add(localVersionInfo);
        }

        if (localClassificationTracker) {
            if (localClassification != null) {
                for (int i = 0; i < localClassification.length; i++) {
                    if (localClassification[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                                "Classification"));
                        elementList.add(localClassification[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "Classification cannot be null!!");
            }
        }

        if (localExternalIdentifierTracker) {
            if (localExternalIdentifier != null) {
                for (int i = 0; i < localExternalIdentifier.length; i++) {
                    if (localExternalIdentifier[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                                "ExternalIdentifier"));
                        elementList.add(localExternalIdentifier[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "ExternalIdentifier cannot be null!!");
            }
        }

        if (localKeywordsTracker) {
            if (localKeywords != null) {
                for (int i = 0; i < localKeywords.length; i++) {
                    if (localKeywords[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "Keywords"));
                        elementList.add(localKeywords[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "Keywords cannot be null!!");
            }
        }

        if (localNaturalLanguageTracker) {
            if (localNaturalLanguage != null) {
                for (int i = 0; i < localNaturalLanguage.length; i++) {
                    if (localNaturalLanguage[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "NaturalLanguage"));
                        elementList.add(localNaturalLanguage[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "NaturalLanguage cannot be null!!");
            }
        }

        if (localHasContributorTracker) {
            if (localHasContributor != null) {
                for (int i = 0; i < localHasContributor.length; i++) {
                    if (localHasContributor[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "hasContributor"));
                        elementList.add(localHasContributor[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "hasContributor cannot be null!!");
            }
        }

        if (localHasCreator != null) {
            for (int i = 0; i < localHasCreator.length; i++) {
                if (localHasCreator[i] != null) {
                    elementList.add(new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                            "hasCreator"));
                    elementList.add(localHasCreator[i]);
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "hasCreator cannot be null !!");
                }
            }
        } //else {
          //  throw new org.apache.axis2.databinding.ADBException(
          //      "hasCreator cannot be null!!");
        //}

        if (localUsedOntologyEngineeringToolTracker) {
            if (localUsedOntologyEngineeringTool != null) {
                for (int i = 0; i < localUsedOntologyEngineeringTool.length;
                        i++) {
                    if (localUsedOntologyEngineeringTool[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "usedOntologyEngineeringTool"));
                        elementList.add(localUsedOntologyEngineeringTool[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "usedOntologyEngineeringTool cannot be null!!");
            }
        }

        if (localUsedOntologyEngineeringMethodologyTracker) {
            if (localUsedOntologyEngineeringMethodology != null) {
                for (int i = 0;
                        i < localUsedOntologyEngineeringMethodology.length;
                        i++) {
                    if (localUsedOntologyEngineeringMethodology[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "usedOntologyEngineeringMethodology"));
                        elementList.add(localUsedOntologyEngineeringMethodology[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "usedOntologyEngineeringMethodology cannot be null!!");
            }
        }

        if (localUsedKnowledgeRepresentationParadigmTracker) {
            if (localUsedKnowledgeRepresentationParadigm != null) {
                for (int i = 0;
                        i < localUsedKnowledgeRepresentationParadigm.length;
                        i++) {
                    if (localUsedKnowledgeRepresentationParadigm[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "usedKnowledgeRepresentationParadigm"));
                        elementList.add(localUsedKnowledgeRepresentationParadigm[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "usedKnowledgeRepresentationParadigm cannot be null!!");
            }
        }

        if (localDesignedForOntologyTaskTracker) {
            if (localDesignedForOntologyTask != null) {
                for (int i = 0; i < localDesignedForOntologyTask.length; i++) {
                    if (localDesignedForOntologyTask[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "designedForOntologyTask"));
                        elementList.add(localDesignedForOntologyTask[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "designedForOntologyTask cannot be null!!");
            }
        }

        if (localUseImportsTracker) {
            if (localUseImports != null) {
                for (int i = 0; i < localUseImports.length; i++) {
                    if (localUseImports[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "useImports"));
                        elementList.add(localUseImports[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "useImports cannot be null!!");
            }
        }

        if (localIsBackwardCompatibleWithTracker) {
            if (localIsBackwardCompatibleWith != null) {
                for (int i = 0; i < localIsBackwardCompatibleWith.length;
                        i++) {
                    if (localIsBackwardCompatibleWith[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "isBackwardCompatibleWith"));
                        elementList.add(localIsBackwardCompatibleWith[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "isBackwardCompatibleWith cannot be null!!");
            }
        }

        if (localIsIncompatibleWithTracker) {
            if (localIsIncompatibleWith != null) {
                for (int i = 0; i < localIsIncompatibleWith.length; i++) {
                    if (localIsIncompatibleWith[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "isIncompatibleWith"));
                        elementList.add(localIsIncompatibleWith[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "isIncompatibleWith cannot be null!!");
            }
        }

        if (localEndorsedByTracker) {
            if (localEndorsedBy != null) {
                for (int i = 0; i < localEndorsedBy.length; i++) {
                    if (localEndorsedBy[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "endorsedBy"));
                        elementList.add(localEndorsedBy[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "endorsedBy cannot be null!!");
            }
        }

        if (localHasDomainTracker) {
            if (localHasDomain != null) {
                for (int i = 0; i < localHasDomain.length; i++) {
                    if (localHasDomain[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "hasDomain"));
                        elementList.add(localHasDomain[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "hasDomain cannot be null!!");
            }
        }

        if (localHasOntologyLanguageTracker) {
            if (localHasOntologyLanguage != null) {
                for (int i = 0; i < localHasOntologyLanguage.length; i++) {
                    if (localHasOntologyLanguage[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "hasOntologyLanguage"));
                        elementList.add(localHasOntologyLanguage[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "hasOntologyLanguage cannot be null!!");
            }
        }

        if (localKeyClassesTracker) {
            if (localKeyClasses != null) {
                for (int i = 0; i < localKeyClasses.length; i++) {
                    if (localKeyClasses[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "keyClasses"));
                        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                localKeyClasses[i]));
                    } else {
                        // have to do nothing
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "keyClasses cannot be null!!");
            }
        }

        if (localKnownUsageTracker) {
            if (localKnownUsage != null) {
                for (int i = 0; i < localKnownUsage.length; i++) {
                    if (localKnownUsage[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                "knownUsage"));
                        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                localKnownUsage[i]));
                    } else {
                        // have to do nothing
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "knownUsage cannot be null!!");
            }
        }

        attribList.add(new javax.xml.namespace.QName("", "id"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localId));

        attribList.add(new javax.xml.namespace.QName("", "home"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localHome));

        attribList.add(new javax.xml.namespace.QName("", "lid"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localLid));

        attribList.add(new javax.xml.namespace.QName("", "objectType"));

        attribList.add(localObjectType.toString());

        attribList.add(new javax.xml.namespace.QName("", "status"));

        attribList.add(localStatus.toString());

        attribList.add(new javax.xml.namespace.QName("", "acronym"));

        attribList.add(localAcronym.toString());

        attribList.add(new javax.xml.namespace.QName("", "documentation"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localDocumentation));

        attribList.add(new javax.xml.namespace.QName("", "isOfType"));

        attribList.add(localIsOfType.toString());

        attribList.add(new javax.xml.namespace.QName("", "hasOntologySyntax"));

        attribList.add(localHasOntologySyntax.toString());

        attribList.add(new javax.xml.namespace.QName("", "hasLicense"));

        attribList.add(localHasLicense.toString());

        attribList.add(new javax.xml.namespace.QName("", "hasFormalityLevel"));

        attribList.add(localHasFormalityLevel.toString());

        attribList.add(new javax.xml.namespace.QName("", "hasPriorVersion"));

        attribList.add(localHasPriorVersion.toString());

        attribList.add(new javax.xml.namespace.QName("", "numberOfClasses"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localNumberOfClasses));

        attribList.add(new javax.xml.namespace.QName("", "numberOfProperties"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localNumberOfProperties));

        attribList.add(new javax.xml.namespace.QName("", "numberOfIndividuals"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localNumberOfIndividuals));

        attribList.add(new javax.xml.namespace.QName("", "numberOfAxioms"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localNumberOfAxioms));

        attribList.add(new javax.xml.namespace.QName("", "ontologyStatus"));

        attribList.add(localOntologyStatus.toString());

        attribList.add(new javax.xml.namespace.QName("", "creationDate"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localCreationDate));

        attribList.add(new javax.xml.namespace.QName("", "modificationDate"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localModificationDate));

        attribList.add(new javax.xml.namespace.QName("", "resourceLocator"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localResourceLocator));

        attribList.add(new javax.xml.namespace.QName("", "URI"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localURI));

        attribList.add(new javax.xml.namespace.QName("", "version"));

        attribList.add(localVersion.toString());

        attribList.add(new javax.xml.namespace.QName("",
                "isConsistentAccordingToReasoner"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localIsConsistentAccordingToReasoner));

        attribList.add(new javax.xml.namespace.QName("", "containsABox"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localContainsABox));

        attribList.add(new javax.xml.namespace.QName("", "containsRBox"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localContainsRBox));

        attribList.add(new javax.xml.namespace.QName("", "containsTBox"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localContainsTBox));

        attribList.add(new javax.xml.namespace.QName("", "expressiveness"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localExpressiveness));

        attribList.add(new javax.xml.namespace.QName("", "notes"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localNotes));

        return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName,
            elementList.toArray(), attribList.toArray());
    }

    /**
     *  Factory class that keeps the parse method
     */
    public static class Factory {
        /**
         * static method to create the object
         * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
         *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
         * Postcondition: If this object is an element, the reader is positioned at its end element
         *                If this object is a complex type, the reader is positioned at the end element of its outer element
         */
        public static Ontology_Type parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            Ontology_Type object = new Ontology_Type();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix = "";
            java.lang.String namespaceuri = "";

            try {
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.getAttributeValue(
                            "http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
                    java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "type");

                    if (fullTypeName != null) {
                        java.lang.String nsPrefix = null;

                        if (fullTypeName.indexOf(":") > -1) {
                            nsPrefix = fullTypeName.substring(0,
                                    fullTypeName.indexOf(":"));
                        }

                        nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

                        java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(
                                    ":") + 1);

                        if (!"Ontology_Type".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (Ontology_Type) org.oasis.names.tc.ebxml_regrep.xsd.rs.ExtensionMapper.getTypeObject(nsUri,
                                type, reader);
                        }
                    }
                }

                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

                // handle attribute "id"
                java.lang.String tempAttribId = reader.getAttributeValue(null,
                        "id");

                if (tempAttribId != null) {
                    java.lang.String content = tempAttribId;

                    object.setId(org.apache.axis2.databinding.utils.ConverterUtil.convertToAnyURI(
                            tempAttribId));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute id is missing");
                }

                handledAttributes.add("id");

                // handle attribute "home"
                java.lang.String tempAttribHome = reader.getAttributeValue(null,
                        "home");

                if (tempAttribHome != null) {
                    java.lang.String content = tempAttribHome;

                    object.setHome(org.apache.axis2.databinding.utils.ConverterUtil.convertToAnyURI(
                            tempAttribHome));
                } else {
                }

                handledAttributes.add("home");

                // handle attribute "lid"
                java.lang.String tempAttribLid = reader.getAttributeValue(null,
                        "lid");

                if (tempAttribLid != null) {
                    java.lang.String content = tempAttribLid;

                    object.setLid(org.apache.axis2.databinding.utils.ConverterUtil.convertToAnyURI(
                            tempAttribLid));
                } else {
                }

                handledAttributes.add("lid");

                // handle attribute "objectType"
                java.lang.String tempAttribObjectType = reader.getAttributeValue(null,
                        "objectType");

                if (tempAttribObjectType != null) {
                    java.lang.String content = tempAttribObjectType;

                    if (tempAttribObjectType.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribObjectType.substring(0,
                                tempAttribObjectType.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setObjectType(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribObjectType, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setObjectType(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribObjectType, ""));
                    }
                } else {
                }

                handledAttributes.add("objectType");

                // handle attribute "status"
                java.lang.String tempAttribStatus = reader.getAttributeValue(null,
                        "status");

                if (tempAttribStatus != null) {
                    java.lang.String content = tempAttribStatus;

                    if (tempAttribStatus.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribStatus.substring(0,
                                tempAttribStatus.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setStatus(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribStatus, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setStatus(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribStatus, ""));
                    }
                } else {
                }

                handledAttributes.add("status");

                // handle attribute "acronym"
                java.lang.String tempAttribAcronym = reader.getAttributeValue(null,
                        "acronym");

                if (tempAttribAcronym != null) {
                    java.lang.String content = tempAttribAcronym;

                    if (tempAttribAcronym.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribAcronym.substring(0,
                                tempAttribAcronym.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setAcronym(org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName.Factory.fromString(
                                tempAttribAcronym, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setAcronym(org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName.Factory.fromString(
                                tempAttribAcronym, ""));
                    }
                } else {
                }

                handledAttributes.add("acronym");

                // handle attribute "documentation"
                java.lang.String tempAttribDocumentation = reader.getAttributeValue(null,
                        "documentation");

                if (tempAttribDocumentation != null) {
                    java.lang.String content = tempAttribDocumentation;

                    object.setDocumentation(org.apache.axis2.databinding.utils.ConverterUtil.convertToAnyURI(
                            tempAttribDocumentation));
                } else {
                }

                handledAttributes.add("documentation");

                // handle attribute "isOfType"
                java.lang.String tempAttribIsOfType = reader.getAttributeValue(null,
                        "isOfType");

                if (tempAttribIsOfType != null) {
                    java.lang.String content = tempAttribIsOfType;

                    if (tempAttribIsOfType.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribIsOfType.substring(0,
                                tempAttribIsOfType.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setIsOfType(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribIsOfType, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setIsOfType(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribIsOfType, ""));
                    }
                } else {
                  //  throw new org.apache.axis2.databinding.ADBException(
                  //      "Required attribute isOfType is missing");
                }

                handledAttributes.add("isOfType");

                // handle attribute "hasOntologySyntax"
                java.lang.String tempAttribHasOntologySyntax = reader.getAttributeValue(null,
                        "hasOntologySyntax");

                if (tempAttribHasOntologySyntax != null) {
                    java.lang.String content = tempAttribHasOntologySyntax;

                    if (tempAttribHasOntologySyntax.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribHasOntologySyntax.substring(0,
                                tempAttribHasOntologySyntax.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setHasOntologySyntax(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribHasOntologySyntax, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setHasOntologySyntax(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribHasOntologySyntax, ""));
                    }
                } else {
                  //  throw new org.apache.axis2.databinding.ADBException(
                   //     "Required attribute hasOntologySyntax is missing");
                }

                handledAttributes.add("hasOntologySyntax");

                // handle attribute "hasLicense"
                java.lang.String tempAttribHasLicense = reader.getAttributeValue(null,
                        "hasLicense");

                if (tempAttribHasLicense != null) {
                    java.lang.String content = tempAttribHasLicense;

                    if (tempAttribHasLicense.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribHasLicense.substring(0,
                                tempAttribHasLicense.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setHasLicense(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribHasLicense, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setHasLicense(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribHasLicense, ""));
                    }
                } else {
                }

                handledAttributes.add("hasLicense");

                // handle attribute "hasFormalityLevel"
                java.lang.String tempAttribHasFormalityLevel = reader.getAttributeValue(null,
                        "hasFormalityLevel");

                if (tempAttribHasFormalityLevel != null) {
                    java.lang.String content = tempAttribHasFormalityLevel;

                    if (tempAttribHasFormalityLevel.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribHasFormalityLevel.substring(0,
                                tempAttribHasFormalityLevel.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setHasFormalityLevel(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribHasFormalityLevel, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setHasFormalityLevel(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribHasFormalityLevel, ""));
                    }
                } else {
                }

                handledAttributes.add("hasFormalityLevel");

                // handle attribute "hasPriorVersion"
                java.lang.String tempAttribHasPriorVersion = reader.getAttributeValue(null,
                        "hasPriorVersion");

                if (tempAttribHasPriorVersion != null) {
                    java.lang.String content = tempAttribHasPriorVersion;

                    if (tempAttribHasPriorVersion.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribHasPriorVersion.substring(0,
                                tempAttribHasPriorVersion.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setHasPriorVersion(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribHasPriorVersion, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setHasPriorVersion(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribHasPriorVersion, ""));
                    }
                } else {
                }

                handledAttributes.add("hasPriorVersion");

                // handle attribute "numberOfClasses"
                java.lang.String tempAttribNumberOfClasses = reader.getAttributeValue(null,
                        "numberOfClasses");

                if (tempAttribNumberOfClasses != null) {
                    java.lang.String content = tempAttribNumberOfClasses;

                    object.setNumberOfClasses(org.apache.axis2.databinding.utils.ConverterUtil.convertToInteger(
                            tempAttribNumberOfClasses));
                } else {
                    //throw new org.apache.axis2.databinding.ADBException(
                    //    "Required attribute numberOfClasses is missing");
                }

                handledAttributes.add("numberOfClasses");

                // handle attribute "numberOfProperties"
                java.lang.String tempAttribNumberOfProperties = reader.getAttributeValue(null,
                        "numberOfProperties");

                if (tempAttribNumberOfProperties != null) {
                    java.lang.String content = tempAttribNumberOfProperties;

                    object.setNumberOfProperties(org.apache.axis2.databinding.utils.ConverterUtil.convertToInteger(
                            tempAttribNumberOfProperties));
                } else {
                   // throw new org.apache.axis2.databinding.ADBException(
                   //     "Required attribute numberOfProperties is missing");
                }

                handledAttributes.add("numberOfProperties");

                // handle attribute "numberOfIndividuals"
                java.lang.String tempAttribNumberOfIndividuals = reader.getAttributeValue(null,
                        "numberOfIndividuals");

                if (tempAttribNumberOfIndividuals != null) {
                    java.lang.String content = tempAttribNumberOfIndividuals;

                    object.setNumberOfIndividuals(org.apache.axis2.databinding.utils.ConverterUtil.convertToInteger(
                            tempAttribNumberOfIndividuals));
                } else {
                   // throw new org.apache.axis2.databinding.ADBException(
                   //     "Required attribute numberOfIndividuals is missing");
                }

                handledAttributes.add("numberOfIndividuals");

                // handle attribute "numberOfAxioms"
                java.lang.String tempAttribNumberOfAxioms = reader.getAttributeValue(null,
                        "numberOfAxioms");

                if (tempAttribNumberOfAxioms != null) {
                    java.lang.String content = tempAttribNumberOfAxioms;

                    object.setNumberOfAxioms(org.apache.axis2.databinding.utils.ConverterUtil.convertToInteger(
                            tempAttribNumberOfAxioms));
                } else {
                   // throw new org.apache.axis2.databinding.ADBException(
                   //     "Required attribute numberOfAxioms is missing");
                }

                handledAttributes.add("numberOfAxioms");

                // handle attribute "ontologyStatus"
                java.lang.String tempAttribOntologyStatus = reader.getAttributeValue(null,
                        "ontologyStatus");

                if (tempAttribOntologyStatus != null) {
                    java.lang.String content = tempAttribOntologyStatus;

                    if (tempAttribOntologyStatus.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribOntologyStatus.substring(0,
                                tempAttribOntologyStatus.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setOntologyStatus(org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName.Factory.fromString(
                                tempAttribOntologyStatus, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setOntologyStatus(org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName.Factory.fromString(
                                tempAttribOntologyStatus, ""));
                    }
                } else {
                }

                handledAttributes.add("ontologyStatus");

                // handle attribute "creationDate"
                java.lang.String tempAttribCreationDate = reader.getAttributeValue(null,
                        "creationDate");

                if (tempAttribCreationDate != null) {
                    java.lang.String content = tempAttribCreationDate;

                    object.setCreationDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                            tempAttribCreationDate));
                } else {
                   // throw new org.apache.axis2.databinding.ADBException(
                   //     "Required attribute creationDate is missing");
                }

                handledAttributes.add("creationDate");

                // handle attribute "modificationDate"
                java.lang.String tempAttribModificationDate = reader.getAttributeValue(null,
                        "modificationDate");

                if (tempAttribModificationDate != null) {
                    java.lang.String content = tempAttribModificationDate;

                    object.setModificationDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                            tempAttribModificationDate));
                } else {
                }

                handledAttributes.add("modificationDate");

                // handle attribute "resourceLocator"
                java.lang.String tempAttribResourceLocator = reader.getAttributeValue(null,
                        "resourceLocator");

                if (tempAttribResourceLocator != null) {
                    java.lang.String content = tempAttribResourceLocator;

                    object.setResourceLocator(org.apache.axis2.databinding.utils.ConverterUtil.convertToAnyURI(
                            tempAttribResourceLocator));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute resourceLocator is missing");
                }

                handledAttributes.add("resourceLocator");

                // handle attribute "URI"
                java.lang.String tempAttribURI = reader.getAttributeValue(null,
                        "URI");

                if (tempAttribURI != null) {
                    java.lang.String content = tempAttribURI;

                    object.setURI(org.apache.axis2.databinding.utils.ConverterUtil.convertToAnyURI(
                            tempAttribURI));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute URI is missing");
                }

                handledAttributes.add("URI");

                // handle attribute "version"
                java.lang.String tempAttribVersion = reader.getAttributeValue(null,
                        "version");

                if (tempAttribVersion != null) {
                    java.lang.String content = tempAttribVersion;

                    if (tempAttribVersion.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribVersion.substring(0,
                                tempAttribVersion.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setVersion(org.oasis.names.tc.ebxml_regrep.xsd.rim.FreeFormText.Factory.fromString(
                                tempAttribVersion, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setVersion(org.oasis.names.tc.ebxml_regrep.xsd.rim.FreeFormText.Factory.fromString(
                                tempAttribVersion, ""));
                    }
                } else {
                   // throw new org.apache.axis2.databinding.ADBException(
                   //     "Required attribute version is missing");
                }

                handledAttributes.add("version");

                // handle attribute "isConsistentAccordingToReasoner"
                java.lang.String tempAttribIsConsistentAccordingToReasoner = reader.getAttributeValue(null,
                        "isConsistentAccordingToReasoner");

                if (tempAttribIsConsistentAccordingToReasoner != null) {
                    java.lang.String content = tempAttribIsConsistentAccordingToReasoner;

                    object.setIsConsistentAccordingToReasoner(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                            tempAttribIsConsistentAccordingToReasoner));
                } else {
                }

                handledAttributes.add("isConsistentAccordingToReasoner");

                // handle attribute "containsABox"
                java.lang.String tempAttribContainsABox = reader.getAttributeValue(null,
                        "containsABox");

                if (tempAttribContainsABox != null) {
                    java.lang.String content = tempAttribContainsABox;

                    object.setContainsABox(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                            tempAttribContainsABox));
                } else {
                }

                handledAttributes.add("containsABox");

                // handle attribute "containsRBox"
                java.lang.String tempAttribContainsRBox = reader.getAttributeValue(null,
                        "containsRBox");

                if (tempAttribContainsRBox != null) {
                    java.lang.String content = tempAttribContainsRBox;

                    object.setContainsRBox(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                            tempAttribContainsRBox));
                } else {
                }

                handledAttributes.add("containsRBox");

                // handle attribute "containsTBox"
                java.lang.String tempAttribContainsTBox = reader.getAttributeValue(null,
                        "containsTBox");

                if (tempAttribContainsTBox != null) {
                    java.lang.String content = tempAttribContainsTBox;

                    object.setContainsTBox(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                            tempAttribContainsTBox));
                } else {
                }

                handledAttributes.add("containsTBox");

                // handle attribute "expressiveness"
                java.lang.String tempAttribExpressiveness = reader.getAttributeValue(null,
                        "expressiveness");

                if (tempAttribExpressiveness != null) {
                    java.lang.String content = tempAttribExpressiveness;

                    object.setExpressiveness(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribExpressiveness));
                } else {
                }

                handledAttributes.add("expressiveness");

                // handle attribute "notes"
                java.lang.String tempAttribNotes = reader.getAttributeValue(null,
                        "notes");

                if (tempAttribNotes != null) {
                    java.lang.String content = tempAttribNotes;

                    object.setNotes(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribNotes));
                } else {
                }

                handledAttributes.add("notes");

                reader.next();

                java.util.ArrayList list1 = new java.util.ArrayList();

                java.util.ArrayList list5 = new java.util.ArrayList();

                java.util.ArrayList list6 = new java.util.ArrayList();

                java.util.ArrayList list7 = new java.util.ArrayList();

                java.util.ArrayList list8 = new java.util.ArrayList();

                java.util.ArrayList list9 = new java.util.ArrayList();

                java.util.ArrayList list10 = new java.util.ArrayList();

                java.util.ArrayList list11 = new java.util.ArrayList();

                java.util.ArrayList list12 = new java.util.ArrayList();

                java.util.ArrayList list13 = new java.util.ArrayList();

                java.util.ArrayList list14 = new java.util.ArrayList();

                java.util.ArrayList list15 = new java.util.ArrayList();

                java.util.ArrayList list16 = new java.util.ArrayList();

                java.util.ArrayList list17 = new java.util.ArrayList();

                java.util.ArrayList list18 = new java.util.ArrayList();

                java.util.ArrayList list19 = new java.util.ArrayList();

                java.util.ArrayList list20 = new java.util.ArrayList();

                java.util.ArrayList list21 = new java.util.ArrayList();

                java.util.ArrayList list22 = new java.util.ArrayList();

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                            "Slot").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list1.add(org.oasis.names.tc.ebxml_regrep.xsd.rim.SlotType1.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone1 = false;

                    while (!loopDone1) {
                        // We should be at the end element, but make sure
                        while (!reader.isEndElement())
                            reader.next();

                        // Step out of this element
                        reader.next();

                        // Step to next element event.
                        while (!reader.isStartElement() &&
                                !reader.isEndElement())
                            reader.next();

                        if (reader.isEndElement()) {
                            //two continuous end elements means we are exiting the xml structure
                            loopDone1 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                                        "Slot").equals(reader.getName())) {
                                list1.add(org.oasis.names.tc.ebxml_regrep.xsd.rim.SlotType1.Factory.parse(
                                        reader));
                            } else {
                                loopDone1 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setSlot((org.oasis.names.tc.ebxml_regrep.xsd.rim.SlotType1[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.oasis.names.tc.ebxml_regrep.xsd.rim.SlotType1.class,
                            list1));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                            "Name").equals(reader.getName())) {
                    object.setName(org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                            "Description").equals(reader.getName())) {
                    object.setDescription(org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                            "VersionInfo").equals(reader.getName())) {
                    object.setVersionInfo(org.oasis.names.tc.ebxml_regrep.xsd.rim.VersionInfoType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                            "Classification").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list5.add(org.oasis.names.tc.ebxml_regrep.xsd.rim.ClassificationType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone5 = false;

                    while (!loopDone5) {
                        // We should be at the end element, but make sure
                        while (!reader.isEndElement())
                            reader.next();

                        // Step out of this element
                        reader.next();

                        // Step to next element event.
                        while (!reader.isStartElement() &&
                                !reader.isEndElement())
                            reader.next();

                        if (reader.isEndElement()) {
                            //two continuous end elements means we are exiting the xml structure
                            loopDone5 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                                        "Classification").equals(
                                        reader.getName())) {
                                list5.add(org.oasis.names.tc.ebxml_regrep.xsd.rim.ClassificationType.Factory.parse(
                                        reader));
                            } else {
                                loopDone5 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setClassification((org.oasis.names.tc.ebxml_regrep.xsd.rim.ClassificationType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.oasis.names.tc.ebxml_regrep.xsd.rim.ClassificationType.class,
                            list5));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                            "ExternalIdentifier").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list6.add(org.oasis.names.tc.ebxml_regrep.xsd.rim.ExternalIdentifierType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone6 = false;

                    while (!loopDone6) {
                        // We should be at the end element, but make sure
                        while (!reader.isEndElement())
                            reader.next();

                        // Step out of this element
                        reader.next();

                        // Step to next element event.
                        while (!reader.isStartElement() &&
                                !reader.isEndElement())
                            reader.next();

                        if (reader.isEndElement()) {
                            //two continuous end elements means we are exiting the xml structure
                            loopDone6 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                                        "ExternalIdentifier").equals(
                                        reader.getName())) {
                                list6.add(org.oasis.names.tc.ebxml_regrep.xsd.rim.ExternalIdentifierType.Factory.parse(
                                        reader));
                            } else {
                                loopDone6 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setExternalIdentifier((org.oasis.names.tc.ebxml_regrep.xsd.rim.ExternalIdentifierType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.oasis.names.tc.ebxml_regrep.xsd.rim.ExternalIdentifierType.class,
                            list6));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                            "Keywords").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list7.add(org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone7 = false;

                    while (!loopDone7) {
                        // We should be at the end element, but make sure
                        while (!reader.isEndElement())
                            reader.next();

                        // Step out of this element
                        reader.next();

                        // Step to next element event.
                        while (!reader.isStartElement() &&
                                !reader.isEndElement())
                            reader.next();

                        if (reader.isEndElement()) {
                            //two continuous end elements means we are exiting the xml structure
                            loopDone7 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                        "Keywords").equals(reader.getName())) {
                                list7.add(org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType.Factory.parse(
                                        reader));
                            } else {
                                loopDone7 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setKeywords((org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType.class,
                            list7));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                            "NaturalLanguage").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list8.add(org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone8 = false;

                    while (!loopDone8) {
                        // We should be at the end element, but make sure
                        while (!reader.isEndElement())
                            reader.next();

                        // Step out of this element
                        reader.next();

                        // Step to next element event.
                        while (!reader.isStartElement() &&
                                !reader.isEndElement())
                            reader.next();

                        if (reader.isEndElement()) {
                            //two continuous end elements means we are exiting the xml structure
                            loopDone8 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                        "NaturalLanguage").equals(
                                        reader.getName())) {
                                list8.add(org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType.Factory.parse(
                                        reader));
                            } else {
                                loopDone8 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setNaturalLanguage((org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.oasis.names.tc.ebxml_regrep.xsd.rim.InternationalStringType.class,
                            list8));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                            "hasContributor").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list9.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone9 = false;

                    while (!loopDone9) {
                        // We should be at the end element, but make sure
                        while (!reader.isEndElement())
                            reader.next();

                        // Step out of this element
                        reader.next();

                        // Step to next element event.
                        while (!reader.isStartElement() &&
                                !reader.isEndElement())
                            reader.next();

                        if (reader.isEndElement()) {
                            //two continuous end elements means we are exiting the xml structure
                            loopDone9 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                        "hasContributor").equals(
                                        reader.getName())) {
                                list9.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                                        reader));
                            } else {
                                loopDone9 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setHasContributor((org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.class,
                            list9));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                            "hasCreator").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list10.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone10 = false;

                    while (!loopDone10) {
                        // We should be at the end element, but make sure
                        while (!reader.isEndElement())
                            reader.next();

                        // Step out of this element
                        reader.next();

                        // Step to next element event.
                        while (!reader.isStartElement() &&
                                !reader.isEndElement())
                            reader.next();

                        if (reader.isEndElement()) {
                            //two continuous end elements means we are exiting the xml structure
                            loopDone10 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                        "hasCreator").equals(reader.getName())) {
                                list10.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                                        reader));
                            } else {
                                loopDone10 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setHasCreator((org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.class,
                            list10));
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                //    throw new org.apache.axis2.databinding.ADBException(
                //        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                            "usedOntologyEngineeringTool").equals(
                            reader.getName())) {
                    // Process the array and step past its final element's end.
                    list11.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone11 = false;

                    while (!loopDone11) {
                        // We should be at the end element, but make sure
                        while (!reader.isEndElement())
                            reader.next();

                        // Step out of this element
                        reader.next();

                        // Step to next element event.
                        while (!reader.isStartElement() &&
                                !reader.isEndElement())
                            reader.next();

                        if (reader.isEndElement()) {
                            //two continuous end elements means we are exiting the xml structure
                            loopDone11 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                        "usedOntologyEngineeringTool").equals(
                                        reader.getName())) {
                                list11.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                                        reader));
                            } else {
                                loopDone11 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setUsedOntologyEngineeringTool((org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.class,
                            list11));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                            "usedOntologyEngineeringMethodology").equals(
                            reader.getName())) {
                    // Process the array and step past its final element's end.
                    list12.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone12 = false;

                    while (!loopDone12) {
                        // We should be at the end element, but make sure
                        while (!reader.isEndElement())
                            reader.next();

                        // Step out of this element
                        reader.next();

                        // Step to next element event.
                        while (!reader.isStartElement() &&
                                !reader.isEndElement())
                            reader.next();

                        if (reader.isEndElement()) {
                            //two continuous end elements means we are exiting the xml structure
                            loopDone12 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                        "usedOntologyEngineeringMethodology").equals(
                                        reader.getName())) {
                                list12.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                                        reader));
                            } else {
                                loopDone12 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setUsedOntologyEngineeringMethodology((org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.class,
                            list12));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                            "usedKnowledgeRepresentationParadigm").equals(
                            reader.getName())) {
                    // Process the array and step past its final element's end.
                    list13.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone13 = false;

                    while (!loopDone13) {
                        // We should be at the end element, but make sure
                        while (!reader.isEndElement())
                            reader.next();

                        // Step out of this element
                        reader.next();

                        // Step to next element event.
                        while (!reader.isStartElement() &&
                                !reader.isEndElement())
                            reader.next();

                        if (reader.isEndElement()) {
                            //two continuous end elements means we are exiting the xml structure
                            loopDone13 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                        "usedKnowledgeRepresentationParadigm").equals(
                                        reader.getName())) {
                                list13.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                                        reader));
                            } else {
                                loopDone13 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setUsedKnowledgeRepresentationParadigm((org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.class,
                            list13));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                            "designedForOntologyTask").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list14.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone14 = false;

                    while (!loopDone14) {
                        // We should be at the end element, but make sure
                        while (!reader.isEndElement())
                            reader.next();

                        // Step out of this element
                        reader.next();

                        // Step to next element event.
                        while (!reader.isStartElement() &&
                                !reader.isEndElement())
                            reader.next();

                        if (reader.isEndElement()) {
                            //two continuous end elements means we are exiting the xml structure
                            loopDone14 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                        "designedForOntologyTask").equals(
                                        reader.getName())) {
                                list14.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                                        reader));
                            } else {
                                loopDone14 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setDesignedForOntologyTask((org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.class,
                            list14));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                            "useImports").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list15.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone15 = false;

                    while (!loopDone15) {
                        // We should be at the end element, but make sure
                        while (!reader.isEndElement())
                            reader.next();

                        // Step out of this element
                        reader.next();

                        // Step to next element event.
                        while (!reader.isStartElement() &&
                                !reader.isEndElement())
                            reader.next();

                        if (reader.isEndElement()) {
                            //two continuous end elements means we are exiting the xml structure
                            loopDone15 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                        "useImports").equals(reader.getName())) {
                                list15.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                                        reader));
                            } else {
                                loopDone15 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setUseImports((org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.class,
                            list15));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                            "isBackwardCompatibleWith").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list16.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone16 = false;

                    while (!loopDone16) {
                        // We should be at the end element, but make sure
                        while (!reader.isEndElement())
                            reader.next();

                        // Step out of this element
                        reader.next();

                        // Step to next element event.
                        while (!reader.isStartElement() &&
                                !reader.isEndElement())
                            reader.next();

                        if (reader.isEndElement()) {
                            //two continuous end elements means we are exiting the xml structure
                            loopDone16 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                        "isBackwardCompatibleWith").equals(
                                        reader.getName())) {
                                list16.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                                        reader));
                            } else {
                                loopDone16 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setIsBackwardCompatibleWith((org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.class,
                            list16));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                            "isIncompatibleWith").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list17.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone17 = false;

                    while (!loopDone17) {
                        // We should be at the end element, but make sure
                        while (!reader.isEndElement())
                            reader.next();

                        // Step out of this element
                        reader.next();

                        // Step to next element event.
                        while (!reader.isStartElement() &&
                                !reader.isEndElement())
                            reader.next();

                        if (reader.isEndElement()) {
                            //two continuous end elements means we are exiting the xml structure
                            loopDone17 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                        "isIncompatibleWith").equals(
                                        reader.getName())) {
                                list17.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                                        reader));
                            } else {
                                loopDone17 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setIsIncompatibleWith((org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.class,
                            list17));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                            "endorsedBy").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list18.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone18 = false;

                    while (!loopDone18) {
                        // We should be at the end element, but make sure
                        while (!reader.isEndElement())
                            reader.next();

                        // Step out of this element
                        reader.next();

                        // Step to next element event.
                        while (!reader.isStartElement() &&
                                !reader.isEndElement())
                            reader.next();

                        if (reader.isEndElement()) {
                            //two continuous end elements means we are exiting the xml structure
                            loopDone18 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                        "endorsedBy").equals(reader.getName())) {
                                list18.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                                        reader));
                            } else {
                                loopDone18 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setEndorsedBy((org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.class,
                            list18));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                            "hasDomain").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list19.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone19 = false;

                    while (!loopDone19) {
                        // We should be at the end element, but make sure
                        while (!reader.isEndElement())
                            reader.next();

                        // Step out of this element
                        reader.next();

                        // Step to next element event.
                        while (!reader.isStartElement() &&
                                !reader.isEndElement())
                            reader.next();

                        if (reader.isEndElement()) {
                            //two continuous end elements means we are exiting the xml structure
                            loopDone19 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                        "hasDomain").equals(reader.getName())) {
                                list19.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                                        reader));
                            } else {
                                loopDone19 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setHasDomain((org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.class,
                            list19));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                            "hasOntologyLanguage").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list20.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone20 = false;

                    while (!loopDone20) {
                        // We should be at the end element, but make sure
                        while (!reader.isEndElement())
                            reader.next();

                        // Step out of this element
                        reader.next();

                        // Step to next element event.
                        while (!reader.isStartElement() &&
                                !reader.isEndElement())
                            reader.next();

                        if (reader.isEndElement()) {
                            //two continuous end elements means we are exiting the xml structure
                            loopDone20 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                        "hasOntologyLanguage").equals(
                                        reader.getName())) {
                                list20.add(org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.Factory.parse(
                                        reader));
                            } else {
                                loopDone20 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setHasOntologyLanguage((org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.rim.OMVObjectRefType.class,
                            list20));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                            "keyClasses").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list21.add(reader.getElementText());

                    //loop until we find a start element that is not part of this array
                    boolean loopDone21 = false;

                    while (!loopDone21) {
                        // Ensure we are at the EndElement
                        while (!reader.isEndElement()) {
                            reader.next();
                        }

                        // Step out of this element
                        reader.next();

                        // Step to next element event.
                        while (!reader.isStartElement() &&
                                !reader.isEndElement())
                            reader.next();

                        if (reader.isEndElement()) {
                            //two continuous end elements means we are exiting the xml structure
                            loopDone21 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                        "keyClasses").equals(reader.getName())) {
                                list21.add(reader.getElementText());
                            } else {
                                loopDone21 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setKeyClasses((java.lang.String[]) list21.toArray(
                            new java.lang.String[list21.size()]));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                            "knownUsage").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list22.add(reader.getElementText());

                    //loop until we find a start element that is not part of this array
                    boolean loopDone22 = false;

                    while (!loopDone22) {
                        // Ensure we are at the EndElement
                        while (!reader.isEndElement()) {
                            reader.next();
                        }

                        // Step out of this element
                        reader.next();

                        // Step to next element event.
                        while (!reader.isStartElement() &&
                                !reader.isEndElement())
                            reader.next();

                        if (reader.isEndElement()) {
                            //two continuous end elements means we are exiting the xml structure
                            loopDone22 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:rim:2.3",
                                        "knownUsage").equals(reader.getName())) {
                                list22.add(reader.getElementText());
                            } else {
                                loopDone22 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setKnownUsage((java.lang.String[]) list22.toArray(
                            new java.lang.String[list22.size()]));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement()) {
                    // A start element we are not expecting indicates a trailing invalid property
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }
            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }
    } //end of factory class
}
