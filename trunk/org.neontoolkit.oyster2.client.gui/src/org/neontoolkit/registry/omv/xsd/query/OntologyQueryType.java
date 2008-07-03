/**
 * OntologyQueryType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:58 LKT)
 */
package org.neontoolkit.registry.omv.xsd.query;


/**
 *  OntologyQueryType bean class
 */
public class OntologyQueryType extends org.oasis.names.tc.ebxml_regrep.xsd.query.RegistryObjectQueryType
    implements org.apache.axis2.databinding.ADBBean {
    /**
     * field for IsOfTypeQuery
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.query.OntologyTypeQueryType[] localIsOfTypeQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localIsOfTypeQueryTracker = false;

    /**
     * field for HasOntologySyntaxQuery
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.query.OntologySyntaxQueryType[] localHasOntologySyntaxQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localHasOntologySyntaxQueryTracker = false;

    /**
     * field for HasOntologyLanguageQuery
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.query.OntologyLanguageQueryType[] localHasOntologyLanguageQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localHasOntologyLanguageQueryTracker = false;

    /**
     * field for HasLicenseQuery
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.query.LicenseModelQueryType[] localHasLicenseQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localHasLicenseQueryTracker = false;

    /**
     * field for HasFormalityLevelQuery
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.query.FormalityLevelQueryType[] localHasFormalityLevelQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localHasFormalityLevelQueryTracker = false;

    /**
     * field for HasPriorVersionQuery
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[] localHasPriorVersionQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localHasPriorVersionQueryTracker = false;

    /**
     * field for AcronymFilter
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType localAcronymFilter;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localAcronymFilterTracker = false;

    /**
     * field for DocumentationFilter
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType localDocumentationFilter;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localDocumentationFilterTracker = false;

    /**
     * field for NumberOfClassesFilter
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType localNumberOfClassesFilter;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localNumberOfClassesFilterTracker = false;

    /**
     * field for NumberOfPropertiesFilter
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType localNumberOfPropertiesFilter;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localNumberOfPropertiesFilterTracker = false;

    /**
     * field for NumberOfIndividualsFilter
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType localNumberOfIndividualsFilter;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localNumberOfIndividualsFilterTracker = false;

    /**
     * field for NumberOfAxiomsFilter
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType localNumberOfAxiomsFilter;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localNumberOfAxiomsFilterTracker = false;

    /**
     * field for OntologyStatusFilter
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType localOntologyStatusFilter;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localOntologyStatusFilterTracker = false;

    /**
     * field for CreationDateFilter
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.DateTimeFilterType localCreationDateFilter;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localCreationDateFilterTracker = false;

    /**
     * field for ModificationDateFilter
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.DateTimeFilterType localModificationDateFilter;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localModificationDateFilterTracker = false;

    /**
     * field for ResourceLocatorFilter
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType localResourceLocatorFilter;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localResourceLocatorFilterTracker = false;

    /**
     * field for URIFilter
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType localURIFilter;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localURIFilterTracker = false;

    /**
     * field for VersionFilter
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType localVersionFilter;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localVersionFilterTracker = false;

    /**
     * field for IsConsistentAccordingToReasonerFilter
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType localIsConsistentAccordingToReasonerFilter;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localIsConsistentAccordingToReasonerFilterTracker = false;

    /**
     * field for ContainsABoxFilter
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType localContainsABoxFilter;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localContainsABoxFilterTracker = false;

    /**
     * field for ContainsRBoxFilter
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType localContainsRBoxFilter;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localContainsRBoxFilterTracker = false;

    /**
     * field for ContainsTBoxFilter
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType localContainsTBoxFilter;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localContainsTBoxFilterTracker = false;

    /**
     * field for ExpressivenessFilter
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType localExpressivenessFilter;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localExpressivenessFilterTracker = false;

    /**
     * field for KeyClassesFilter
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType localKeyClassesFilter;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localKeyClassesFilterTracker = false;

    /**
     * field for KnownUsageFilter
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType localKnownUsageFilter;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localKnownUsageFilterTracker = false;

    /**
     * field for NotesFilter
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType localNotesFilter;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localNotesFilterTracker = false;

    /**
     * field for KeywordsBranch
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType localKeywordsBranch;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localKeywordsBranchTracker = false;

    /**
     * field for NaturalLanguageBranch
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType localNaturalLanguageBranch;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localNaturalLanguageBranchTracker = false;

    /**
     * field for HasCreatorOrganizationQuery
     * This was an Array!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[] localHasCreatorOrganizationQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localHasCreatorOrganizationQueryTracker = false;

    /**
     * field for HasCreatorPersonQuery
     * This was an Array!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[] localHasCreatorPersonQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localHasCreatorPersonQueryTracker = false;

    /**
     * field for HasContributorOrganizationQuery
     * This was an Array!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[] localHasContributorOrganizationQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localHasContributorOrganizationQueryTracker = false;

    /**
     * field for HasContributorPersonQuery
     * This was an Array!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[] localHasContributorPersonQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localHasContributorPersonQueryTracker = false;

    /**
     * field for UsedOntologyEngineeringToolQuery
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringToolQueryType[] localUsedOntologyEngineeringToolQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localUsedOntologyEngineeringToolQueryTracker = false;

    /**
     * field for UsedOntologyEngineeringMethodologyQuery
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringMethodologyQueryType[] localUsedOntologyEngineeringMethodologyQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localUsedOntologyEngineeringMethodologyQueryTracker = false;

    /**
     * field for UsedKnowledgeRepresentationParadigmQuery
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.query.KnowledgeRepresentationParadigmQueryType[] localUsedKnowledgeRepresentationParadigmQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localUsedKnowledgeRepresentationParadigmQueryTracker = false;

    /**
     * field for DesignedForOntologyTaskQuery
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.query.OntologyTaskQueryType[] localDesignedForOntologyTaskQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localDesignedForOntologyTaskQueryTracker = false;

    /**
     * field for UseImportsQuery
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[] localUseImportsQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localUseImportsQueryTracker = false;

    /**
     * field for IsBackwardCompatibleWithQuery
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[] localIsBackwardCompatibleWithQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localIsBackwardCompatibleWithQueryTracker = false;

    /**
     * field for IsIncompatibleWithQuery
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[] localIsIncompatibleWithQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localIsIncompatibleWithQueryTracker = false;

    /**
     * field for EndorsedByPersonQuery
     * This was an Array!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[] localEndorsedByPersonQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localEndorsedByPersonQueryTracker = false;

    /**
     * field for EndorsedByOrganizationQuery
     * This was an Array!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[] localEndorsedByOrganizationQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localEndorsedByOrganizationQueryTracker = false;

    /**
     * field for HasDomainQuery
     * This was an Array!
     */
    protected org.neontoolkit.registry.omv.xsd.query.OntologyDomainQueryType[] localHasDomainQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localHasDomainQueryTracker = false;

    /* This type was generated from the piece of schema that had
       name = OntologyQueryType
       Namespace URI = urn:neon-toolkit-org:registry:omv:xsd:query:2.3
       Namespace Prefix = ns6
     */
    private static java.lang.String generatePrefix(java.lang.String namespace) {
        if (namespace.equals("urn:neon-toolkit-org:registry:omv:xsd:query:2.3")) {
            return "ns6";
        }

        return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
    }

    /**
     * Auto generated getter method
     * @return org.neon_toolkit.registry.omv.xsd.query.OntologyTypeQueryType[]
     */
    public org.neontoolkit.registry.omv.xsd.query.OntologyTypeQueryType[] getIsOfTypeQuery() {
        return localIsOfTypeQuery;
    }

    /**
     * validate the array for IsOfTypeQuery
     */
    protected void validateIsOfTypeQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyTypeQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param IsOfTypeQuery
     */
    public void setIsOfTypeQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyTypeQueryType[] param) {
        validateIsOfTypeQuery(param);

        if (param != null) {
            //update the setting tracker
            localIsOfTypeQueryTracker = true;
        } else {
            localIsOfTypeQueryTracker = false;
        }

        this.localIsOfTypeQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neon_toolkit.registry.omv.xsd.query.OntologyTypeQueryType
     */
    public void addIsOfTypeQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyTypeQueryType param) {
        if (localIsOfTypeQuery == null) {
            localIsOfTypeQuery = new org.neontoolkit.registry.omv.xsd.query.OntologyTypeQueryType[] {
                    
                };
        }

        //update the setting tracker
        localIsOfTypeQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localIsOfTypeQuery);
        list.add(param);
        this.localIsOfTypeQuery = (org.neontoolkit.registry.omv.xsd.query.OntologyTypeQueryType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.query.OntologyTypeQueryType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neon_toolkit.registry.omv.xsd.query.OntologySyntaxQueryType[]
     */
    public org.neontoolkit.registry.omv.xsd.query.OntologySyntaxQueryType[] getHasOntologySyntaxQuery() {
        return localHasOntologySyntaxQuery;
    }

    /**
     * validate the array for HasOntologySyntaxQuery
     */
    protected void validateHasOntologySyntaxQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologySyntaxQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param HasOntologySyntaxQuery
     */
    public void setHasOntologySyntaxQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologySyntaxQueryType[] param) {
        validateHasOntologySyntaxQuery(param);

        if (param != null) {
            //update the setting tracker
            localHasOntologySyntaxQueryTracker = true;
        } else {
            localHasOntologySyntaxQueryTracker = false;
        }

        this.localHasOntologySyntaxQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neon_toolkit.registry.omv.xsd.query.OntologySyntaxQueryType
     */
    public void addHasOntologySyntaxQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologySyntaxQueryType param) {
        if (localHasOntologySyntaxQuery == null) {
            localHasOntologySyntaxQuery = new org.neontoolkit.registry.omv.xsd.query.OntologySyntaxQueryType[] {
                    
                };
        }

        //update the setting tracker
        localHasOntologySyntaxQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localHasOntologySyntaxQuery);
        list.add(param);
        this.localHasOntologySyntaxQuery = (org.neontoolkit.registry.omv.xsd.query.OntologySyntaxQueryType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.query.OntologySyntaxQueryType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neon_toolkit.registry.omv.xsd.query.OntologyLanguageQueryType[]
     */
    public org.neontoolkit.registry.omv.xsd.query.OntologyLanguageQueryType[] getHasOntologyLanguageQuery() {
        return localHasOntologyLanguageQuery;
    }

    /**
     * validate the array for HasOntologyLanguageQuery
     */
    protected void validateHasOntologyLanguageQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyLanguageQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param HasOntologyLanguageQuery
     */
    public void setHasOntologyLanguageQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyLanguageQueryType[] param) {
        validateHasOntologyLanguageQuery(param);

        if (param != null) {
            //update the setting tracker
            localHasOntologyLanguageQueryTracker = true;
        } else {
            localHasOntologyLanguageQueryTracker = false;
        }

        this.localHasOntologyLanguageQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neon_toolkit.registry.omv.xsd.query.OntologyLanguageQueryType
     */
    public void addHasOntologyLanguageQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyLanguageQueryType param) {
        if (localHasOntologyLanguageQuery == null) {
            localHasOntologyLanguageQuery = new org.neontoolkit.registry.omv.xsd.query.OntologyLanguageQueryType[] {
                    
                };
        }

        //update the setting tracker
        localHasOntologyLanguageQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localHasOntologyLanguageQuery);
        list.add(param);
        this.localHasOntologyLanguageQuery = (org.neontoolkit.registry.omv.xsd.query.OntologyLanguageQueryType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.query.OntologyLanguageQueryType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neon_toolkit.registry.omv.xsd.query.LicenseModelQueryType[]
     */
    public org.neontoolkit.registry.omv.xsd.query.LicenseModelQueryType[] getHasLicenseQuery() {
        return localHasLicenseQuery;
    }

    /**
     * validate the array for HasLicenseQuery
     */
    protected void validateHasLicenseQuery(
        org.neontoolkit.registry.omv.xsd.query.LicenseModelQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param HasLicenseQuery
     */
    public void setHasLicenseQuery(
        org.neontoolkit.registry.omv.xsd.query.LicenseModelQueryType[] param) {
        validateHasLicenseQuery(param);

        if (param != null) {
            //update the setting tracker
            localHasLicenseQueryTracker = true;
        } else {
            localHasLicenseQueryTracker = false;
        }

        this.localHasLicenseQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neon_toolkit.registry.omv.xsd.query.LicenseModelQueryType
     */
    public void addHasLicenseQuery(
        org.neontoolkit.registry.omv.xsd.query.LicenseModelQueryType param) {
        if (localHasLicenseQuery == null) {
            localHasLicenseQuery = new org.neontoolkit.registry.omv.xsd.query.LicenseModelQueryType[] {
                    
                };
        }

        //update the setting tracker
        localHasLicenseQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localHasLicenseQuery);
        list.add(param);
        this.localHasLicenseQuery = (org.neontoolkit.registry.omv.xsd.query.LicenseModelQueryType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.query.LicenseModelQueryType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neon_toolkit.registry.omv.xsd.query.FormalityLevelQueryType[]
     */
    public org.neontoolkit.registry.omv.xsd.query.FormalityLevelQueryType[] getHasFormalityLevelQuery() {
        return localHasFormalityLevelQuery;
    }

    /**
     * validate the array for HasFormalityLevelQuery
     */
    protected void validateHasFormalityLevelQuery(
        org.neontoolkit.registry.omv.xsd.query.FormalityLevelQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param HasFormalityLevelQuery
     */
    public void setHasFormalityLevelQuery(
        org.neontoolkit.registry.omv.xsd.query.FormalityLevelQueryType[] param) {
        validateHasFormalityLevelQuery(param);

        if (param != null) {
            //update the setting tracker
            localHasFormalityLevelQueryTracker = true;
        } else {
            localHasFormalityLevelQueryTracker = false;
        }

        this.localHasFormalityLevelQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neon_toolkit.registry.omv.xsd.query.FormalityLevelQueryType
     */
    public void addHasFormalityLevelQuery(
        org.neontoolkit.registry.omv.xsd.query.FormalityLevelQueryType param) {
        if (localHasFormalityLevelQuery == null) {
            localHasFormalityLevelQuery = new org.neontoolkit.registry.omv.xsd.query.FormalityLevelQueryType[] {
                    
                };
        }

        //update the setting tracker
        localHasFormalityLevelQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localHasFormalityLevelQuery);
        list.add(param);
        this.localHasFormalityLevelQuery = (org.neontoolkit.registry.omv.xsd.query.FormalityLevelQueryType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.query.FormalityLevelQueryType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neon_toolkit.registry.omv.xsd.query.OntologyQueryType[]
     */
    public org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[] getHasPriorVersionQuery() {
        return localHasPriorVersionQuery;
    }

    /**
     * validate the array for HasPriorVersionQuery
     */
    protected void validateHasPriorVersionQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param HasPriorVersionQuery
     */
    public void setHasPriorVersionQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[] param) {
        validateHasPriorVersionQuery(param);

        if (param != null) {
            //update the setting tracker
            localHasPriorVersionQueryTracker = true;
        } else {
            localHasPriorVersionQueryTracker = false;
        }

        this.localHasPriorVersionQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neon_toolkit.registry.omv.xsd.query.OntologyQueryType
     */
    public void addHasPriorVersionQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyQueryType param) {
        if (localHasPriorVersionQuery == null) {
            localHasPriorVersionQuery = new org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[] {
                    
                };
        }

        //update the setting tracker
        localHasPriorVersionQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localHasPriorVersionQuery);
        list.add(param);
        this.localHasPriorVersionQuery = (org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType getAcronymFilter() {
        return localAcronymFilter;
    }

    /**
     * Auto generated setter method
     * @param param AcronymFilter
     */
    public void setAcronymFilter(
        org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType param) {
        if (param != null) {
            //update the setting tracker
            localAcronymFilterTracker = true;
        } else {
            localAcronymFilterTracker = false;
        }

        this.localAcronymFilter = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType getDocumentationFilter() {
        return localDocumentationFilter;
    }

    /**
     * Auto generated setter method
     * @param param DocumentationFilter
     */
    public void setDocumentationFilter(
        org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType param) {
        if (param != null) {
            //update the setting tracker
            localDocumentationFilterTracker = true;
        } else {
            localDocumentationFilterTracker = false;
        }

        this.localDocumentationFilter = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType getNumberOfClassesFilter() {
        return localNumberOfClassesFilter;
    }

    /**
     * Auto generated setter method
     * @param param NumberOfClassesFilter
     */
    public void setNumberOfClassesFilter(
        org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType param) {
        if (param != null) {
            //update the setting tracker
            localNumberOfClassesFilterTracker = true;
        } else {
            localNumberOfClassesFilterTracker = false;
        }

        this.localNumberOfClassesFilter = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType getNumberOfPropertiesFilter() {
        return localNumberOfPropertiesFilter;
    }

    /**
     * Auto generated setter method
     * @param param NumberOfPropertiesFilter
     */
    public void setNumberOfPropertiesFilter(
        org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType param) {
        if (param != null) {
            //update the setting tracker
            localNumberOfPropertiesFilterTracker = true;
        } else {
            localNumberOfPropertiesFilterTracker = false;
        }

        this.localNumberOfPropertiesFilter = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType getNumberOfIndividualsFilter() {
        return localNumberOfIndividualsFilter;
    }

    /**
     * Auto generated setter method
     * @param param NumberOfIndividualsFilter
     */
    public void setNumberOfIndividualsFilter(
        org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType param) {
        if (param != null) {
            //update the setting tracker
            localNumberOfIndividualsFilterTracker = true;
        } else {
            localNumberOfIndividualsFilterTracker = false;
        }

        this.localNumberOfIndividualsFilter = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType getNumberOfAxiomsFilter() {
        return localNumberOfAxiomsFilter;
    }

    /**
     * Auto generated setter method
     * @param param NumberOfAxiomsFilter
     */
    public void setNumberOfAxiomsFilter(
        org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType param) {
        if (param != null) {
            //update the setting tracker
            localNumberOfAxiomsFilterTracker = true;
        } else {
            localNumberOfAxiomsFilterTracker = false;
        }

        this.localNumberOfAxiomsFilter = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType getOntologyStatusFilter() {
        return localOntologyStatusFilter;
    }

    /**
     * Auto generated setter method
     * @param param OntologyStatusFilter
     */
    public void setOntologyStatusFilter(
        org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType param) {
        if (param != null) {
            //update the setting tracker
            localOntologyStatusFilterTracker = true;
        } else {
            localOntologyStatusFilterTracker = false;
        }

        this.localOntologyStatusFilter = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.DateTimeFilterType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.DateTimeFilterType getCreationDateFilter() {
        return localCreationDateFilter;
    }

    /**
     * Auto generated setter method
     * @param param CreationDateFilter
     */
    public void setCreationDateFilter(
        org.oasis.names.tc.ebxml_regrep.xsd.query.DateTimeFilterType param) {
        if (param != null) {
            //update the setting tracker
            localCreationDateFilterTracker = true;
        } else {
            localCreationDateFilterTracker = false;
        }

        this.localCreationDateFilter = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.DateTimeFilterType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.DateTimeFilterType getModificationDateFilter() {
        return localModificationDateFilter;
    }

    /**
     * Auto generated setter method
     * @param param ModificationDateFilter
     */
    public void setModificationDateFilter(
        org.oasis.names.tc.ebxml_regrep.xsd.query.DateTimeFilterType param) {
        if (param != null) {
            //update the setting tracker
            localModificationDateFilterTracker = true;
        } else {
            localModificationDateFilterTracker = false;
        }

        this.localModificationDateFilter = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType getResourceLocatorFilter() {
        return localResourceLocatorFilter;
    }

    /**
     * Auto generated setter method
     * @param param ResourceLocatorFilter
     */
    public void setResourceLocatorFilter(
        org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType param) {
        if (param != null) {
            //update the setting tracker
            localResourceLocatorFilterTracker = true;
        } else {
            localResourceLocatorFilterTracker = false;
        }

        this.localResourceLocatorFilter = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType getURIFilter() {
        return localURIFilter;
    }

    /**
     * Auto generated setter method
     * @param param URIFilter
     */
    public void setURIFilter(
        org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType param) {
        if (param != null) {
            //update the setting tracker
            localURIFilterTracker = true;
        } else {
            localURIFilterTracker = false;
        }

        this.localURIFilter = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType getVersionFilter() {
        return localVersionFilter;
    }

    /**
     * Auto generated setter method
     * @param param VersionFilter
     */
    public void setVersionFilter(
        org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType param) {
        if (param != null) {
            //update the setting tracker
            localVersionFilterTracker = true;
        } else {
            localVersionFilterTracker = false;
        }

        this.localVersionFilter = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType getIsConsistentAccordingToReasonerFilter() {
        return localIsConsistentAccordingToReasonerFilter;
    }

    /**
     * Auto generated setter method
     * @param param IsConsistentAccordingToReasonerFilter
     */
    public void setIsConsistentAccordingToReasonerFilter(
        org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType param) {
        if (param != null) {
            //update the setting tracker
            localIsConsistentAccordingToReasonerFilterTracker = true;
        } else {
            localIsConsistentAccordingToReasonerFilterTracker = false;
        }

        this.localIsConsistentAccordingToReasonerFilter = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType getContainsABoxFilter() {
        return localContainsABoxFilter;
    }

    /**
     * Auto generated setter method
     * @param param ContainsABoxFilter
     */
    public void setContainsABoxFilter(
        org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType param) {
        if (param != null) {
            //update the setting tracker
            localContainsABoxFilterTracker = true;
        } else {
            localContainsABoxFilterTracker = false;
        }

        this.localContainsABoxFilter = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType getContainsRBoxFilter() {
        return localContainsRBoxFilter;
    }

    /**
     * Auto generated setter method
     * @param param ContainsRBoxFilter
     */
    public void setContainsRBoxFilter(
        org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType param) {
        if (param != null) {
            //update the setting tracker
            localContainsRBoxFilterTracker = true;
        } else {
            localContainsRBoxFilterTracker = false;
        }

        this.localContainsRBoxFilter = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType getContainsTBoxFilter() {
        return localContainsTBoxFilter;
    }

    /**
     * Auto generated setter method
     * @param param ContainsTBoxFilter
     */
    public void setContainsTBoxFilter(
        org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType param) {
        if (param != null) {
            //update the setting tracker
            localContainsTBoxFilterTracker = true;
        } else {
            localContainsTBoxFilterTracker = false;
        }

        this.localContainsTBoxFilter = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType getExpressivenessFilter() {
        return localExpressivenessFilter;
    }

    /**
     * Auto generated setter method
     * @param param ExpressivenessFilter
     */
    public void setExpressivenessFilter(
        org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType param) {
        if (param != null) {
            //update the setting tracker
            localExpressivenessFilterTracker = true;
        } else {
            localExpressivenessFilterTracker = false;
        }

        this.localExpressivenessFilter = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType getKeyClassesFilter() {
        return localKeyClassesFilter;
    }

    /**
     * Auto generated setter method
     * @param param KeyClassesFilter
     */
    public void setKeyClassesFilter(
        org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType param) {
        if (param != null) {
            //update the setting tracker
            localKeyClassesFilterTracker = true;
        } else {
            localKeyClassesFilterTracker = false;
        }

        this.localKeyClassesFilter = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType getKnownUsageFilter() {
        return localKnownUsageFilter;
    }

    /**
     * Auto generated setter method
     * @param param KnownUsageFilter
     */
    public void setKnownUsageFilter(
        org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType param) {
        if (param != null) {
            //update the setting tracker
            localKnownUsageFilterTracker = true;
        } else {
            localKnownUsageFilterTracker = false;
        }

        this.localKnownUsageFilter = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType getNotesFilter() {
        return localNotesFilter;
    }

    /**
     * Auto generated setter method
     * @param param NotesFilter
     */
    public void setNotesFilter(
        org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType param) {
        if (param != null) {
            //update the setting tracker
            localNotesFilterTracker = true;
        } else {
            localNotesFilterTracker = false;
        }

        this.localNotesFilter = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType getKeywordsBranch() {
        return localKeywordsBranch;
    }

    /**
     * Auto generated setter method
     * @param param KeywordsBranch
     */
    public void setKeywordsBranch(
        org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType param) {
        if (param != null) {
            //update the setting tracker
            localKeywordsBranchTracker = true;
        } else {
            localKeywordsBranchTracker = false;
        }

        this.localKeywordsBranch = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType getNaturalLanguageBranch() {
        return localNaturalLanguageBranch;
    }

    /**
     * Auto generated setter method
     * @param param NaturalLanguageBranch
     */
    public void setNaturalLanguageBranch(
        org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType param) {
        if (param != null) {
            //update the setting tracker
            localNaturalLanguageBranchTracker = true;
        } else {
            localNaturalLanguageBranchTracker = false;
        }

        this.localNaturalLanguageBranch = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[]
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[] getHasCreatorOrganizationQuery() {
        return localHasCreatorOrganizationQuery;
    }

    /**
     * validate the array for HasCreatorOrganizationQuery
     */
    protected void validateHasCreatorOrganizationQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param HasCreatorOrganizationQuery
     */
    public void setHasCreatorOrganizationQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[] param) {
        validateHasCreatorOrganizationQuery(param);

        if (param != null) {
            //update the setting tracker
            localHasCreatorOrganizationQueryTracker = true;
        } else {
            localHasCreatorOrganizationQueryTracker = false;
        }

        this.localHasCreatorOrganizationQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType
     */
    public void addHasCreatorOrganizationQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType param) {
        if (localHasCreatorOrganizationQuery == null) {
            localHasCreatorOrganizationQuery = new org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[] {
                    
                };
        }

        //update the setting tracker
        localHasCreatorOrganizationQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localHasCreatorOrganizationQuery);
        list.add(param);
        this.localHasCreatorOrganizationQuery = (org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[]) list.toArray(new org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[]
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[] getHasCreatorPersonQuery() {
        return localHasCreatorPersonQuery;
    }

    /**
     * validate the array for HasCreatorPersonQuery
     */
    protected void validateHasCreatorPersonQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param HasCreatorPersonQuery
     */
    public void setHasCreatorPersonQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[] param) {
        validateHasCreatorPersonQuery(param);

        if (param != null) {
            //update the setting tracker
            localHasCreatorPersonQueryTracker = true;
        } else {
            localHasCreatorPersonQueryTracker = false;
        }

        this.localHasCreatorPersonQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType
     */
    public void addHasCreatorPersonQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType param) {
        if (localHasCreatorPersonQuery == null) {
            localHasCreatorPersonQuery = new org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[] {
                    
                };
        }

        //update the setting tracker
        localHasCreatorPersonQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localHasCreatorPersonQuery);
        list.add(param);
        this.localHasCreatorPersonQuery = (org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[]) list.toArray(new org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[]
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[] getHasContributorOrganizationQuery() {
        return localHasContributorOrganizationQuery;
    }

    /**
     * validate the array for HasContributorOrganizationQuery
     */
    protected void validateHasContributorOrganizationQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param HasContributorOrganizationQuery
     */
    public void setHasContributorOrganizationQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[] param) {
        validateHasContributorOrganizationQuery(param);

        if (param != null) {
            //update the setting tracker
            localHasContributorOrganizationQueryTracker = true;
        } else {
            localHasContributorOrganizationQueryTracker = false;
        }

        this.localHasContributorOrganizationQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType
     */
    public void addHasContributorOrganizationQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType param) {
        if (localHasContributorOrganizationQuery == null) {
            localHasContributorOrganizationQuery = new org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[] {
                    
                };
        }

        //update the setting tracker
        localHasContributorOrganizationQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localHasContributorOrganizationQuery);
        list.add(param);
        this.localHasContributorOrganizationQuery = (org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[]) list.toArray(new org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[]
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[] getHasContributorPersonQuery() {
        return localHasContributorPersonQuery;
    }

    /**
     * validate the array for HasContributorPersonQuery
     */
    protected void validateHasContributorPersonQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param HasContributorPersonQuery
     */
    public void setHasContributorPersonQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[] param) {
        validateHasContributorPersonQuery(param);

        if (param != null) {
            //update the setting tracker
            localHasContributorPersonQueryTracker = true;
        } else {
            localHasContributorPersonQueryTracker = false;
        }

        this.localHasContributorPersonQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType
     */
    public void addHasContributorPersonQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType param) {
        if (localHasContributorPersonQuery == null) {
            localHasContributorPersonQuery = new org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[] {
                    
                };
        }

        //update the setting tracker
        localHasContributorPersonQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localHasContributorPersonQuery);
        list.add(param);
        this.localHasContributorPersonQuery = (org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[]) list.toArray(new org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neon_toolkit.registry.omv.xsd.query.OntologyEngineeringToolQueryType[]
     */
    public org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringToolQueryType[] getUsedOntologyEngineeringToolQuery() {
        return localUsedOntologyEngineeringToolQuery;
    }

    /**
     * validate the array for UsedOntologyEngineeringToolQuery
     */
    protected void validateUsedOntologyEngineeringToolQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringToolQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param UsedOntologyEngineeringToolQuery
     */
    public void setUsedOntologyEngineeringToolQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringToolQueryType[] param) {
        validateUsedOntologyEngineeringToolQuery(param);

        if (param != null) {
            //update the setting tracker
            localUsedOntologyEngineeringToolQueryTracker = true;
        } else {
            localUsedOntologyEngineeringToolQueryTracker = false;
        }

        this.localUsedOntologyEngineeringToolQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neon_toolkit.registry.omv.xsd.query.OntologyEngineeringToolQueryType
     */
    public void addUsedOntologyEngineeringToolQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringToolQueryType param) {
        if (localUsedOntologyEngineeringToolQuery == null) {
            localUsedOntologyEngineeringToolQuery = new org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringToolQueryType[] {
                    
                };
        }

        //update the setting tracker
        localUsedOntologyEngineeringToolQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localUsedOntologyEngineeringToolQuery);
        list.add(param);
        this.localUsedOntologyEngineeringToolQuery = (org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringToolQueryType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringToolQueryType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neon_toolkit.registry.omv.xsd.query.OntologyEngineeringMethodologyQueryType[]
     */
    public org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringMethodologyQueryType[] getUsedOntologyEngineeringMethodologyQuery() {
        return localUsedOntologyEngineeringMethodologyQuery;
    }

    /**
     * validate the array for UsedOntologyEngineeringMethodologyQuery
     */
    protected void validateUsedOntologyEngineeringMethodologyQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringMethodologyQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param UsedOntologyEngineeringMethodologyQuery
     */
    public void setUsedOntologyEngineeringMethodologyQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringMethodologyQueryType[] param) {
        validateUsedOntologyEngineeringMethodologyQuery(param);

        if (param != null) {
            //update the setting tracker
            localUsedOntologyEngineeringMethodologyQueryTracker = true;
        } else {
            localUsedOntologyEngineeringMethodologyQueryTracker = false;
        }

        this.localUsedOntologyEngineeringMethodologyQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neon_toolkit.registry.omv.xsd.query.OntologyEngineeringMethodologyQueryType
     */
    public void addUsedOntologyEngineeringMethodologyQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringMethodologyQueryType param) {
        if (localUsedOntologyEngineeringMethodologyQuery == null) {
            localUsedOntologyEngineeringMethodologyQuery = new org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringMethodologyQueryType[] {
                    
                };
        }

        //update the setting tracker
        localUsedOntologyEngineeringMethodologyQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localUsedOntologyEngineeringMethodologyQuery);
        list.add(param);
        this.localUsedOntologyEngineeringMethodologyQuery = (org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringMethodologyQueryType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringMethodologyQueryType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neon_toolkit.registry.omv.xsd.query.KnowledgeRepresentationParadigmQueryType[]
     */
    public org.neontoolkit.registry.omv.xsd.query.KnowledgeRepresentationParadigmQueryType[] getUsedKnowledgeRepresentationParadigmQuery() {
        return localUsedKnowledgeRepresentationParadigmQuery;
    }

    /**
     * validate the array for UsedKnowledgeRepresentationParadigmQuery
     */
    protected void validateUsedKnowledgeRepresentationParadigmQuery(
        org.neontoolkit.registry.omv.xsd.query.KnowledgeRepresentationParadigmQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param UsedKnowledgeRepresentationParadigmQuery
     */
    public void setUsedKnowledgeRepresentationParadigmQuery(
        org.neontoolkit.registry.omv.xsd.query.KnowledgeRepresentationParadigmQueryType[] param) {
        validateUsedKnowledgeRepresentationParadigmQuery(param);

        if (param != null) {
            //update the setting tracker
            localUsedKnowledgeRepresentationParadigmQueryTracker = true;
        } else {
            localUsedKnowledgeRepresentationParadigmQueryTracker = false;
        }

        this.localUsedKnowledgeRepresentationParadigmQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neon_toolkit.registry.omv.xsd.query.KnowledgeRepresentationParadigmQueryType
     */
    public void addUsedKnowledgeRepresentationParadigmQuery(
        org.neontoolkit.registry.omv.xsd.query.KnowledgeRepresentationParadigmQueryType param) {
        if (localUsedKnowledgeRepresentationParadigmQuery == null) {
            localUsedKnowledgeRepresentationParadigmQuery = new org.neontoolkit.registry.omv.xsd.query.KnowledgeRepresentationParadigmQueryType[] {
                    
                };
        }

        //update the setting tracker
        localUsedKnowledgeRepresentationParadigmQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localUsedKnowledgeRepresentationParadigmQuery);
        list.add(param);
        this.localUsedKnowledgeRepresentationParadigmQuery = (org.neontoolkit.registry.omv.xsd.query.KnowledgeRepresentationParadigmQueryType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.query.KnowledgeRepresentationParadigmQueryType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neon_toolkit.registry.omv.xsd.query.OntologyTaskQueryType[]
     */
    public org.neontoolkit.registry.omv.xsd.query.OntologyTaskQueryType[] getDesignedForOntologyTaskQuery() {
        return localDesignedForOntologyTaskQuery;
    }

    /**
     * validate the array for DesignedForOntologyTaskQuery
     */
    protected void validateDesignedForOntologyTaskQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyTaskQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param DesignedForOntologyTaskQuery
     */
    public void setDesignedForOntologyTaskQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyTaskQueryType[] param) {
        validateDesignedForOntologyTaskQuery(param);

        if (param != null) {
            //update the setting tracker
            localDesignedForOntologyTaskQueryTracker = true;
        } else {
            localDesignedForOntologyTaskQueryTracker = false;
        }

        this.localDesignedForOntologyTaskQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neon_toolkit.registry.omv.xsd.query.OntologyTaskQueryType
     */
    public void addDesignedForOntologyTaskQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyTaskQueryType param) {
        if (localDesignedForOntologyTaskQuery == null) {
            localDesignedForOntologyTaskQuery = new org.neontoolkit.registry.omv.xsd.query.OntologyTaskQueryType[] {
                    
                };
        }

        //update the setting tracker
        localDesignedForOntologyTaskQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localDesignedForOntologyTaskQuery);
        list.add(param);
        this.localDesignedForOntologyTaskQuery = (org.neontoolkit.registry.omv.xsd.query.OntologyTaskQueryType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.query.OntologyTaskQueryType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neon_toolkit.registry.omv.xsd.query.OntologyQueryType[]
     */
    public org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[] getUseImportsQuery() {
        return localUseImportsQuery;
    }

    /**
     * validate the array for UseImportsQuery
     */
    protected void validateUseImportsQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param UseImportsQuery
     */
    public void setUseImportsQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[] param) {
        validateUseImportsQuery(param);

        if (param != null) {
            //update the setting tracker
            localUseImportsQueryTracker = true;
        } else {
            localUseImportsQueryTracker = false;
        }

        this.localUseImportsQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neon_toolkit.registry.omv.xsd.query.OntologyQueryType
     */
    public void addUseImportsQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyQueryType param) {
        if (localUseImportsQuery == null) {
            localUseImportsQuery = new org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[] {
                    
                };
        }

        //update the setting tracker
        localUseImportsQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localUseImportsQuery);
        list.add(param);
        this.localUseImportsQuery = (org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neon_toolkit.registry.omv.xsd.query.OntologyQueryType[]
     */
    public org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[] getIsBackwardCompatibleWithQuery() {
        return localIsBackwardCompatibleWithQuery;
    }

    /**
     * validate the array for IsBackwardCompatibleWithQuery
     */
    protected void validateIsBackwardCompatibleWithQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param IsBackwardCompatibleWithQuery
     */
    public void setIsBackwardCompatibleWithQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[] param) {
        validateIsBackwardCompatibleWithQuery(param);

        if (param != null) {
            //update the setting tracker
            localIsBackwardCompatibleWithQueryTracker = true;
        } else {
            localIsBackwardCompatibleWithQueryTracker = false;
        }

        this.localIsBackwardCompatibleWithQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neon_toolkit.registry.omv.xsd.query.OntologyQueryType
     */
    public void addIsBackwardCompatibleWithQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyQueryType param) {
        if (localIsBackwardCompatibleWithQuery == null) {
            localIsBackwardCompatibleWithQuery = new org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[] {
                    
                };
        }

        //update the setting tracker
        localIsBackwardCompatibleWithQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localIsBackwardCompatibleWithQuery);
        list.add(param);
        this.localIsBackwardCompatibleWithQuery = (org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neon_toolkit.registry.omv.xsd.query.OntologyQueryType[]
     */
    public org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[] getIsIncompatibleWithQuery() {
        return localIsIncompatibleWithQuery;
    }

    /**
     * validate the array for IsIncompatibleWithQuery
     */
    protected void validateIsIncompatibleWithQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param IsIncompatibleWithQuery
     */
    public void setIsIncompatibleWithQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[] param) {
        validateIsIncompatibleWithQuery(param);

        if (param != null) {
            //update the setting tracker
            localIsIncompatibleWithQueryTracker = true;
        } else {
            localIsIncompatibleWithQueryTracker = false;
        }

        this.localIsIncompatibleWithQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neon_toolkit.registry.omv.xsd.query.OntologyQueryType
     */
    public void addIsIncompatibleWithQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyQueryType param) {
        if (localIsIncompatibleWithQuery == null) {
            localIsIncompatibleWithQuery = new org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[] {
                    
                };
        }

        //update the setting tracker
        localIsIncompatibleWithQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localIsIncompatibleWithQuery);
        list.add(param);
        this.localIsIncompatibleWithQuery = (org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[]
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[] getEndorsedByPersonQuery() {
        return localEndorsedByPersonQuery;
    }

    /**
     * validate the array for EndorsedByPersonQuery
     */
    protected void validateEndorsedByPersonQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param EndorsedByPersonQuery
     */
    public void setEndorsedByPersonQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[] param) {
        validateEndorsedByPersonQuery(param);

        if (param != null) {
            //update the setting tracker
            localEndorsedByPersonQueryTracker = true;
        } else {
            localEndorsedByPersonQueryTracker = false;
        }

        this.localEndorsedByPersonQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType
     */
    public void addEndorsedByPersonQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType param) {
        if (localEndorsedByPersonQuery == null) {
            localEndorsedByPersonQuery = new org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[] {
                    
                };
        }

        //update the setting tracker
        localEndorsedByPersonQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localEndorsedByPersonQuery);
        list.add(param);
        this.localEndorsedByPersonQuery = (org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[]) list.toArray(new org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[]
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[] getEndorsedByOrganizationQuery() {
        return localEndorsedByOrganizationQuery;
    }

    /**
     * validate the array for EndorsedByOrganizationQuery
     */
    protected void validateEndorsedByOrganizationQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param EndorsedByOrganizationQuery
     */
    public void setEndorsedByOrganizationQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[] param) {
        validateEndorsedByOrganizationQuery(param);

        if (param != null) {
            //update the setting tracker
            localEndorsedByOrganizationQueryTracker = true;
        } else {
            localEndorsedByOrganizationQueryTracker = false;
        }

        this.localEndorsedByOrganizationQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType
     */
    public void addEndorsedByOrganizationQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType param) {
        if (localEndorsedByOrganizationQuery == null) {
            localEndorsedByOrganizationQuery = new org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[] {
                    
                };
        }

        //update the setting tracker
        localEndorsedByOrganizationQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localEndorsedByOrganizationQuery);
        list.add(param);
        this.localEndorsedByOrganizationQuery = (org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[]) list.toArray(new org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.neon_toolkit.registry.omv.xsd.query.OntologyDomainQueryType[]
     */
    public org.neontoolkit.registry.omv.xsd.query.OntologyDomainQueryType[] getHasDomainQuery() {
        return localHasDomainQuery;
    }

    /**
     * validate the array for HasDomainQuery
     */
    protected void validateHasDomainQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyDomainQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param HasDomainQuery
     */
    public void setHasDomainQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyDomainQueryType[] param) {
        validateHasDomainQuery(param);

        if (param != null) {
            //update the setting tracker
            localHasDomainQueryTracker = true;
        } else {
            localHasDomainQueryTracker = false;
        }

        this.localHasDomainQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.neon_toolkit.registry.omv.xsd.query.OntologyDomainQueryType
     */
    public void addHasDomainQuery(
        org.neontoolkit.registry.omv.xsd.query.OntologyDomainQueryType param) {
        if (localHasDomainQuery == null) {
            localHasDomainQuery = new org.neontoolkit.registry.omv.xsd.query.OntologyDomainQueryType[] {
                    
                };
        }

        //update the setting tracker
        localHasDomainQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localHasDomainQuery);
        list.add(param);
        this.localHasDomainQuery = (org.neontoolkit.registry.omv.xsd.query.OntologyDomainQueryType[]) list.toArray(new org.neontoolkit.registry.omv.xsd.query.OntologyDomainQueryType[list.size()]);
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
                    OntologyQueryType.this.serialize(parentQName, factory,
                        xmlWriter);
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
                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3");

        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "type", namespacePrefix + ":OntologyQueryType", xmlWriter);
        } else {
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "type", "OntologyQueryType", xmlWriter);
        }

        if (localPrimaryFilterTracker) {
            if (localPrimaryFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "PrimaryFilter cannot be null!!");
            }

            localPrimaryFilter.serialize(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                    "PrimaryFilter"), factory, xmlWriter);
        }

        if (localSlotBranchTracker) {
            if (localSlotBranch != null) {
                for (int i = 0; i < localSlotBranch.length; i++) {
                    if (localSlotBranch[i] != null) {
                        localSlotBranch[i].serialize(new javax.xml.namespace.QName(
                                "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                                "SlotBranch"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "SlotBranch cannot be null!!");
            }
        }

        if (localNameBranchTracker) {
            if (localNameBranch == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "NameBranch cannot be null!!");
            }

            localNameBranch.serialize(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                    "NameBranch"), factory, xmlWriter);
        }

        if (localDescriptionBranchTracker) {
            if (localDescriptionBranch == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "DescriptionBranch cannot be null!!");
            }

            localDescriptionBranch.serialize(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                    "DescriptionBranch"), factory, xmlWriter);
        }

        if (localVersionInfoFilterTracker) {
            if (localVersionInfoFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "VersionInfoFilter cannot be null!!");
            }

            localVersionInfoFilter.serialize(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                    "VersionInfoFilter"), factory, xmlWriter);
        }

        if (localClassificationQueryTracker) {
            if (localClassificationQuery != null) {
                for (int i = 0; i < localClassificationQuery.length; i++) {
                    if (localClassificationQuery[i] != null) {
                        localClassificationQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                                "ClassificationQuery"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "ClassificationQuery cannot be null!!");
            }
        }

        if (localExternalIdentifierQueryTracker) {
            if (localExternalIdentifierQuery != null) {
                for (int i = 0; i < localExternalIdentifierQuery.length; i++) {
                    if (localExternalIdentifierQuery[i] != null) {
                        localExternalIdentifierQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                                "ExternalIdentifierQuery"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "ExternalIdentifierQuery cannot be null!!");
            }
        }

        if (localObjectTypeQueryTracker) {
            if (localObjectTypeQuery == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "ObjectTypeQuery cannot be null!!");
            }

            localObjectTypeQuery.serialize(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                    "ObjectTypeQuery"), factory, xmlWriter);
        }

        if (localStatusQueryTracker) {
            if (localStatusQuery == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "StatusQuery cannot be null!!");
            }

            localStatusQuery.serialize(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                    "StatusQuery"), factory, xmlWriter);
        }

        if (localSourceAssociationQueryTracker) {
            if (localSourceAssociationQuery != null) {
                for (int i = 0; i < localSourceAssociationQuery.length; i++) {
                    if (localSourceAssociationQuery[i] != null) {
                        localSourceAssociationQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                                "SourceAssociationQuery"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "SourceAssociationQuery cannot be null!!");
            }
        }

        if (localTargetAssociationQueryTracker) {
            if (localTargetAssociationQuery != null) {
                for (int i = 0; i < localTargetAssociationQuery.length; i++) {
                    if (localTargetAssociationQuery[i] != null) {
                        localTargetAssociationQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                                "TargetAssociationQuery"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "TargetAssociationQuery cannot be null!!");
            }
        }

        if (localIsOfTypeQueryTracker) {
            if (localIsOfTypeQuery != null) {
                for (int i = 0; i < localIsOfTypeQuery.length; i++) {
                    if (localIsOfTypeQuery[i] != null) {
                        localIsOfTypeQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "IsOfTypeQuery"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "IsOfTypeQuery cannot be null!!");
            }
        }

        if (localHasOntologySyntaxQueryTracker) {
            if (localHasOntologySyntaxQuery != null) {
                for (int i = 0; i < localHasOntologySyntaxQuery.length; i++) {
                    if (localHasOntologySyntaxQuery[i] != null) {
                        localHasOntologySyntaxQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "HasOntologySyntaxQuery"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "HasOntologySyntaxQuery cannot be null!!");
            }
        }

        if (localHasOntologyLanguageQueryTracker) {
            if (localHasOntologyLanguageQuery != null) {
                for (int i = 0; i < localHasOntologyLanguageQuery.length;
                        i++) {
                    if (localHasOntologyLanguageQuery[i] != null) {
                        localHasOntologyLanguageQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "HasOntologyLanguageQuery"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "HasOntologyLanguageQuery cannot be null!!");
            }
        }

        if (localHasLicenseQueryTracker) {
            if (localHasLicenseQuery != null) {
                for (int i = 0; i < localHasLicenseQuery.length; i++) {
                    if (localHasLicenseQuery[i] != null) {
                        localHasLicenseQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "HasLicenseQuery"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "HasLicenseQuery cannot be null!!");
            }
        }

        if (localHasFormalityLevelQueryTracker) {
            if (localHasFormalityLevelQuery != null) {
                for (int i = 0; i < localHasFormalityLevelQuery.length; i++) {
                    if (localHasFormalityLevelQuery[i] != null) {
                        localHasFormalityLevelQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "HasFormalityLevelQuery"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "HasFormalityLevelQuery cannot be null!!");
            }
        }

        if (localHasPriorVersionQueryTracker) {
            if (localHasPriorVersionQuery != null) {
                for (int i = 0; i < localHasPriorVersionQuery.length; i++) {
                    if (localHasPriorVersionQuery[i] != null) {
                        localHasPriorVersionQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "HasPriorVersionQuery"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "HasPriorVersionQuery cannot be null!!");
            }
        }

        if (localAcronymFilterTracker) {
            if (localAcronymFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "AcronymFilter cannot be null!!");
            }

            localAcronymFilter.serialize(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "AcronymFilter"), factory, xmlWriter);
        }

        if (localDocumentationFilterTracker) {
            if (localDocumentationFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "DocumentationFilter cannot be null!!");
            }

            localDocumentationFilter.serialize(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "DocumentationFilter"), factory, xmlWriter);
        }

        if (localNumberOfClassesFilterTracker) {
            if (localNumberOfClassesFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "NumberOfClassesFilter cannot be null!!");
            }

            localNumberOfClassesFilter.serialize(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "NumberOfClassesFilter"), factory, xmlWriter);
        }

        if (localNumberOfPropertiesFilterTracker) {
            if (localNumberOfPropertiesFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "NumberOfPropertiesFilter cannot be null!!");
            }

            localNumberOfPropertiesFilter.serialize(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "NumberOfPropertiesFilter"), factory, xmlWriter);
        }

        if (localNumberOfIndividualsFilterTracker) {
            if (localNumberOfIndividualsFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "NumberOfIndividualsFilter cannot be null!!");
            }

            localNumberOfIndividualsFilter.serialize(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "NumberOfIndividualsFilter"), factory, xmlWriter);
        }

        if (localNumberOfAxiomsFilterTracker) {
            if (localNumberOfAxiomsFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "NumberOfAxiomsFilter cannot be null!!");
            }

            localNumberOfAxiomsFilter.serialize(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "NumberOfAxiomsFilter"), factory, xmlWriter);
        }

        if (localOntologyStatusFilterTracker) {
            if (localOntologyStatusFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "OntologyStatusFilter cannot be null!!");
            }

            localOntologyStatusFilter.serialize(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "OntologyStatusFilter"), factory, xmlWriter);
        }

        if (localCreationDateFilterTracker) {
            if (localCreationDateFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "CreationDateFilter cannot be null!!");
            }

            localCreationDateFilter.serialize(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "CreationDateFilter"), factory, xmlWriter);
        }

        if (localModificationDateFilterTracker) {
            if (localModificationDateFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "ModificationDateFilter cannot be null!!");
            }

            localModificationDateFilter.serialize(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "ModificationDateFilter"), factory, xmlWriter);
        }

        if (localResourceLocatorFilterTracker) {
            if (localResourceLocatorFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "ResourceLocatorFilter cannot be null!!");
            }

            localResourceLocatorFilter.serialize(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "ResourceLocatorFilter"), factory, xmlWriter);
        }

        if (localURIFilterTracker) {
            if (localURIFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "URIFilter cannot be null!!");
            }

            localURIFilter.serialize(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "URIFilter"), factory, xmlWriter);
        }

        if (localVersionFilterTracker) {
            if (localVersionFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "VersionFilter cannot be null!!");
            }

            localVersionFilter.serialize(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "VersionFilter"), factory, xmlWriter);
        }

        if (localIsConsistentAccordingToReasonerFilterTracker) {
            if (localIsConsistentAccordingToReasonerFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "IsConsistentAccordingToReasonerFilter cannot be null!!");
            }

            localIsConsistentAccordingToReasonerFilter.serialize(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "IsConsistentAccordingToReasonerFilter"), factory, xmlWriter);
        }

        if (localContainsABoxFilterTracker) {
            if (localContainsABoxFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "ContainsABoxFilter cannot be null!!");
            }

            localContainsABoxFilter.serialize(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "ContainsABoxFilter"), factory, xmlWriter);
        }

        if (localContainsRBoxFilterTracker) {
            if (localContainsRBoxFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "ContainsRBoxFilter cannot be null!!");
            }

            localContainsRBoxFilter.serialize(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "ContainsRBoxFilter"), factory, xmlWriter);
        }

        if (localContainsTBoxFilterTracker) {
            if (localContainsTBoxFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "ContainsTBoxFilter cannot be null!!");
            }

            localContainsTBoxFilter.serialize(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "ContainsTBoxFilter"), factory, xmlWriter);
        }

        if (localExpressivenessFilterTracker) {
            if (localExpressivenessFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "ExpressivenessFilter cannot be null!!");
            }

            localExpressivenessFilter.serialize(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "ExpressivenessFilter"), factory, xmlWriter);
        }

        if (localKeyClassesFilterTracker) {
            if (localKeyClassesFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "KeyClassesFilter cannot be null!!");
            }

            localKeyClassesFilter.serialize(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "KeyClassesFilter"), factory, xmlWriter);
        }

        if (localKnownUsageFilterTracker) {
            if (localKnownUsageFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "KnownUsageFilter cannot be null!!");
            }

            localKnownUsageFilter.serialize(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "KnownUsageFilter"), factory, xmlWriter);
        }

        if (localNotesFilterTracker) {
            if (localNotesFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "NotesFilter cannot be null!!");
            }

            localNotesFilter.serialize(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "NotesFilter"), factory, xmlWriter);
        }

        if (localKeywordsBranchTracker) {
            if (localKeywordsBranch == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "KeywordsBranch cannot be null!!");
            }

            localKeywordsBranch.serialize(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "KeywordsBranch"), factory, xmlWriter);
        }

        if (localNaturalLanguageBranchTracker) {
            if (localNaturalLanguageBranch == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "NaturalLanguageBranch cannot be null!!");
            }

            localNaturalLanguageBranch.serialize(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "NaturalLanguageBranch"), factory, xmlWriter);
        }

        if (localHasCreatorOrganizationQueryTracker) {
            if (localHasCreatorOrganizationQuery != null) {
                for (int i = 0; i < localHasCreatorOrganizationQuery.length;
                        i++) {
                    if (localHasCreatorOrganizationQuery[i] != null) {
                        localHasCreatorOrganizationQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "HasCreatorOrganizationQuery"), factory,
                            xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "HasCreatorOrganizationQuery cannot be null!!");
            }
        }

        if (localHasCreatorPersonQueryTracker) {
            if (localHasCreatorPersonQuery != null) {
                for (int i = 0; i < localHasCreatorPersonQuery.length; i++) {
                    if (localHasCreatorPersonQuery[i] != null) {
                        localHasCreatorPersonQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "HasCreatorPersonQuery"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "HasCreatorPersonQuery cannot be null!!");
            }
        }

        if (localHasContributorOrganizationQueryTracker) {
            if (localHasContributorOrganizationQuery != null) {
                for (int i = 0;
                        i < localHasContributorOrganizationQuery.length; i++) {
                    if (localHasContributorOrganizationQuery[i] != null) {
                        localHasContributorOrganizationQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "HasContributorOrganizationQuery"), factory,
                            xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "HasContributorOrganizationQuery cannot be null!!");
            }
        }

        if (localHasContributorPersonQueryTracker) {
            if (localHasContributorPersonQuery != null) {
                for (int i = 0; i < localHasContributorPersonQuery.length;
                        i++) {
                    if (localHasContributorPersonQuery[i] != null) {
                        localHasContributorPersonQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "HasContributorPersonQuery"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "HasContributorPersonQuery cannot be null!!");
            }
        }

        if (localUsedOntologyEngineeringToolQueryTracker) {
            if (localUsedOntologyEngineeringToolQuery != null) {
                for (int i = 0;
                        i < localUsedOntologyEngineeringToolQuery.length;
                        i++) {
                    if (localUsedOntologyEngineeringToolQuery[i] != null) {
                        localUsedOntologyEngineeringToolQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "UsedOntologyEngineeringToolQuery"), factory,
                            xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "UsedOntologyEngineeringToolQuery cannot be null!!");
            }
        }

        if (localUsedOntologyEngineeringMethodologyQueryTracker) {
            if (localUsedOntologyEngineeringMethodologyQuery != null) {
                for (int i = 0;
                        i < localUsedOntologyEngineeringMethodologyQuery.length;
                        i++) {
                    if (localUsedOntologyEngineeringMethodologyQuery[i] != null) {
                        localUsedOntologyEngineeringMethodologyQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "UsedOntologyEngineeringMethodologyQuery"),
                            factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "UsedOntologyEngineeringMethodologyQuery cannot be null!!");
            }
        }

        if (localUsedKnowledgeRepresentationParadigmQueryTracker) {
            if (localUsedKnowledgeRepresentationParadigmQuery != null) {
                for (int i = 0;
                        i < localUsedKnowledgeRepresentationParadigmQuery.length;
                        i++) {
                    if (localUsedKnowledgeRepresentationParadigmQuery[i] != null) {
                        localUsedKnowledgeRepresentationParadigmQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "UsedKnowledgeRepresentationParadigmQuery"),
                            factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "UsedKnowledgeRepresentationParadigmQuery cannot be null!!");
            }
        }

        if (localDesignedForOntologyTaskQueryTracker) {
            if (localDesignedForOntologyTaskQuery != null) {
                for (int i = 0; i < localDesignedForOntologyTaskQuery.length;
                        i++) {
                    if (localDesignedForOntologyTaskQuery[i] != null) {
                        localDesignedForOntologyTaskQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "DesignedForOntologyTaskQuery"), factory,
                            xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "DesignedForOntologyTaskQuery cannot be null!!");
            }
        }

        if (localUseImportsQueryTracker) {
            if (localUseImportsQuery != null) {
                for (int i = 0; i < localUseImportsQuery.length; i++) {
                    if (localUseImportsQuery[i] != null) {
                        localUseImportsQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "UseImportsQuery"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "UseImportsQuery cannot be null!!");
            }
        }

        if (localIsBackwardCompatibleWithQueryTracker) {
            if (localIsBackwardCompatibleWithQuery != null) {
                for (int i = 0; i < localIsBackwardCompatibleWithQuery.length;
                        i++) {
                    if (localIsBackwardCompatibleWithQuery[i] != null) {
                        localIsBackwardCompatibleWithQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "IsBackwardCompatibleWithQuery"), factory,
                            xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "IsBackwardCompatibleWithQuery cannot be null!!");
            }
        }

        if (localIsIncompatibleWithQueryTracker) {
            if (localIsIncompatibleWithQuery != null) {
                for (int i = 0; i < localIsIncompatibleWithQuery.length; i++) {
                    if (localIsIncompatibleWithQuery[i] != null) {
                        localIsIncompatibleWithQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "IsIncompatibleWithQuery"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "IsIncompatibleWithQuery cannot be null!!");
            }
        }

        if (localEndorsedByPersonQueryTracker) {
            if (localEndorsedByPersonQuery != null) {
                for (int i = 0; i < localEndorsedByPersonQuery.length; i++) {
                    if (localEndorsedByPersonQuery[i] != null) {
                        localEndorsedByPersonQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "EndorsedByPersonQuery"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "EndorsedByPersonQuery cannot be null!!");
            }
        }

        if (localEndorsedByOrganizationQueryTracker) {
            if (localEndorsedByOrganizationQuery != null) {
                for (int i = 0; i < localEndorsedByOrganizationQuery.length;
                        i++) {
                    if (localEndorsedByOrganizationQuery[i] != null) {
                        localEndorsedByOrganizationQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "EndorsedByOrganizationQuery"), factory,
                            xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "EndorsedByOrganizationQuery cannot be null!!");
            }
        }

        if (localHasDomainQueryTracker) {
            if (localHasDomainQuery != null) {
                for (int i = 0; i < localHasDomainQuery.length; i++) {
                    if (localHasDomainQuery[i] != null) {
                        localHasDomainQuery[i].serialize(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "HasDomainQuery"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "HasDomainQuery cannot be null!!");
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
                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                "OntologyQueryType"));

        if (localPrimaryFilterTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                    "PrimaryFilter"));

            if (localPrimaryFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "PrimaryFilter cannot be null!!");
            }

            elementList.add(localPrimaryFilter);
        }

        if (localSlotBranchTracker) {
            if (localSlotBranch != null) {
                for (int i = 0; i < localSlotBranch.length; i++) {
                    if (localSlotBranch[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                                "SlotBranch"));
                        elementList.add(localSlotBranch[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "SlotBranch cannot be null!!");
            }
        }

        if (localNameBranchTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                    "NameBranch"));

            if (localNameBranch == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "NameBranch cannot be null!!");
            }

            elementList.add(localNameBranch);
        }

        if (localDescriptionBranchTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                    "DescriptionBranch"));

            if (localDescriptionBranch == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "DescriptionBranch cannot be null!!");
            }

            elementList.add(localDescriptionBranch);
        }

        if (localVersionInfoFilterTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                    "VersionInfoFilter"));

            if (localVersionInfoFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "VersionInfoFilter cannot be null!!");
            }

            elementList.add(localVersionInfoFilter);
        }

        if (localClassificationQueryTracker) {
            if (localClassificationQuery != null) {
                for (int i = 0; i < localClassificationQuery.length; i++) {
                    if (localClassificationQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                                "ClassificationQuery"));
                        elementList.add(localClassificationQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "ClassificationQuery cannot be null!!");
            }
        }

        if (localExternalIdentifierQueryTracker) {
            if (localExternalIdentifierQuery != null) {
                for (int i = 0; i < localExternalIdentifierQuery.length; i++) {
                    if (localExternalIdentifierQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                                "ExternalIdentifierQuery"));
                        elementList.add(localExternalIdentifierQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "ExternalIdentifierQuery cannot be null!!");
            }
        }

        if (localObjectTypeQueryTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                    "ObjectTypeQuery"));

            if (localObjectTypeQuery == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "ObjectTypeQuery cannot be null!!");
            }

            elementList.add(localObjectTypeQuery);
        }

        if (localStatusQueryTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                    "StatusQuery"));

            if (localStatusQuery == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "StatusQuery cannot be null!!");
            }

            elementList.add(localStatusQuery);
        }

        if (localSourceAssociationQueryTracker) {
            if (localSourceAssociationQuery != null) {
                for (int i = 0; i < localSourceAssociationQuery.length; i++) {
                    if (localSourceAssociationQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                                "SourceAssociationQuery"));
                        elementList.add(localSourceAssociationQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "SourceAssociationQuery cannot be null!!");
            }
        }

        if (localTargetAssociationQueryTracker) {
            if (localTargetAssociationQuery != null) {
                for (int i = 0; i < localTargetAssociationQuery.length; i++) {
                    if (localTargetAssociationQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                                "TargetAssociationQuery"));
                        elementList.add(localTargetAssociationQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "TargetAssociationQuery cannot be null!!");
            }
        }

        if (localIsOfTypeQueryTracker) {
            if (localIsOfTypeQuery != null) {
                for (int i = 0; i < localIsOfTypeQuery.length; i++) {
                    if (localIsOfTypeQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "IsOfTypeQuery"));
                        elementList.add(localIsOfTypeQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "IsOfTypeQuery cannot be null!!");
            }
        }

        if (localHasOntologySyntaxQueryTracker) {
            if (localHasOntologySyntaxQuery != null) {
                for (int i = 0; i < localHasOntologySyntaxQuery.length; i++) {
                    if (localHasOntologySyntaxQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "HasOntologySyntaxQuery"));
                        elementList.add(localHasOntologySyntaxQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "HasOntologySyntaxQuery cannot be null!!");
            }
        }

        if (localHasOntologyLanguageQueryTracker) {
            if (localHasOntologyLanguageQuery != null) {
                for (int i = 0; i < localHasOntologyLanguageQuery.length;
                        i++) {
                    if (localHasOntologyLanguageQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "HasOntologyLanguageQuery"));
                        elementList.add(localHasOntologyLanguageQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "HasOntologyLanguageQuery cannot be null!!");
            }
        }

        if (localHasLicenseQueryTracker) {
            if (localHasLicenseQuery != null) {
                for (int i = 0; i < localHasLicenseQuery.length; i++) {
                    if (localHasLicenseQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "HasLicenseQuery"));
                        elementList.add(localHasLicenseQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "HasLicenseQuery cannot be null!!");
            }
        }

        if (localHasFormalityLevelQueryTracker) {
            if (localHasFormalityLevelQuery != null) {
                for (int i = 0; i < localHasFormalityLevelQuery.length; i++) {
                    if (localHasFormalityLevelQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "HasFormalityLevelQuery"));
                        elementList.add(localHasFormalityLevelQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "HasFormalityLevelQuery cannot be null!!");
            }
        }

        if (localHasPriorVersionQueryTracker) {
            if (localHasPriorVersionQuery != null) {
                for (int i = 0; i < localHasPriorVersionQuery.length; i++) {
                    if (localHasPriorVersionQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "HasPriorVersionQuery"));
                        elementList.add(localHasPriorVersionQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "HasPriorVersionQuery cannot be null!!");
            }
        }

        if (localAcronymFilterTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "AcronymFilter"));

            if (localAcronymFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "AcronymFilter cannot be null!!");
            }

            elementList.add(localAcronymFilter);
        }

        if (localDocumentationFilterTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "DocumentationFilter"));

            if (localDocumentationFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "DocumentationFilter cannot be null!!");
            }

            elementList.add(localDocumentationFilter);
        }

        if (localNumberOfClassesFilterTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "NumberOfClassesFilter"));

            if (localNumberOfClassesFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "NumberOfClassesFilter cannot be null!!");
            }

            elementList.add(localNumberOfClassesFilter);
        }

        if (localNumberOfPropertiesFilterTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "NumberOfPropertiesFilter"));

            if (localNumberOfPropertiesFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "NumberOfPropertiesFilter cannot be null!!");
            }

            elementList.add(localNumberOfPropertiesFilter);
        }

        if (localNumberOfIndividualsFilterTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "NumberOfIndividualsFilter"));

            if (localNumberOfIndividualsFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "NumberOfIndividualsFilter cannot be null!!");
            }

            elementList.add(localNumberOfIndividualsFilter);
        }

        if (localNumberOfAxiomsFilterTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "NumberOfAxiomsFilter"));

            if (localNumberOfAxiomsFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "NumberOfAxiomsFilter cannot be null!!");
            }

            elementList.add(localNumberOfAxiomsFilter);
        }

        if (localOntologyStatusFilterTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "OntologyStatusFilter"));

            if (localOntologyStatusFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "OntologyStatusFilter cannot be null!!");
            }

            elementList.add(localOntologyStatusFilter);
        }

        if (localCreationDateFilterTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "CreationDateFilter"));

            if (localCreationDateFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "CreationDateFilter cannot be null!!");
            }

            elementList.add(localCreationDateFilter);
        }

        if (localModificationDateFilterTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "ModificationDateFilter"));

            if (localModificationDateFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "ModificationDateFilter cannot be null!!");
            }

            elementList.add(localModificationDateFilter);
        }

        if (localResourceLocatorFilterTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "ResourceLocatorFilter"));

            if (localResourceLocatorFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "ResourceLocatorFilter cannot be null!!");
            }

            elementList.add(localResourceLocatorFilter);
        }

        if (localURIFilterTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "URIFilter"));

            if (localURIFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "URIFilter cannot be null!!");
            }

            elementList.add(localURIFilter);
        }

        if (localVersionFilterTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "VersionFilter"));

            if (localVersionFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "VersionFilter cannot be null!!");
            }

            elementList.add(localVersionFilter);
        }

        if (localIsConsistentAccordingToReasonerFilterTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "IsConsistentAccordingToReasonerFilter"));

            if (localIsConsistentAccordingToReasonerFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "IsConsistentAccordingToReasonerFilter cannot be null!!");
            }

            elementList.add(localIsConsistentAccordingToReasonerFilter);
        }

        if (localContainsABoxFilterTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "ContainsABoxFilter"));

            if (localContainsABoxFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "ContainsABoxFilter cannot be null!!");
            }

            elementList.add(localContainsABoxFilter);
        }

        if (localContainsRBoxFilterTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "ContainsRBoxFilter"));

            if (localContainsRBoxFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "ContainsRBoxFilter cannot be null!!");
            }

            elementList.add(localContainsRBoxFilter);
        }

        if (localContainsTBoxFilterTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "ContainsTBoxFilter"));

            if (localContainsTBoxFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "ContainsTBoxFilter cannot be null!!");
            }

            elementList.add(localContainsTBoxFilter);
        }

        if (localExpressivenessFilterTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "ExpressivenessFilter"));

            if (localExpressivenessFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "ExpressivenessFilter cannot be null!!");
            }

            elementList.add(localExpressivenessFilter);
        }

        if (localKeyClassesFilterTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "KeyClassesFilter"));

            if (localKeyClassesFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "KeyClassesFilter cannot be null!!");
            }

            elementList.add(localKeyClassesFilter);
        }

        if (localKnownUsageFilterTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "KnownUsageFilter"));

            if (localKnownUsageFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "KnownUsageFilter cannot be null!!");
            }

            elementList.add(localKnownUsageFilter);
        }

        if (localNotesFilterTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "NotesFilter"));

            if (localNotesFilter == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "NotesFilter cannot be null!!");
            }

            elementList.add(localNotesFilter);
        }

        if (localKeywordsBranchTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "KeywordsBranch"));

            if (localKeywordsBranch == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "KeywordsBranch cannot be null!!");
            }

            elementList.add(localKeywordsBranch);
        }

        if (localNaturalLanguageBranchTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                    "NaturalLanguageBranch"));

            if (localNaturalLanguageBranch == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "NaturalLanguageBranch cannot be null!!");
            }

            elementList.add(localNaturalLanguageBranch);
        }

        if (localHasCreatorOrganizationQueryTracker) {
            if (localHasCreatorOrganizationQuery != null) {
                for (int i = 0; i < localHasCreatorOrganizationQuery.length;
                        i++) {
                    if (localHasCreatorOrganizationQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "HasCreatorOrganizationQuery"));
                        elementList.add(localHasCreatorOrganizationQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "HasCreatorOrganizationQuery cannot be null!!");
            }
        }

        if (localHasCreatorPersonQueryTracker) {
            if (localHasCreatorPersonQuery != null) {
                for (int i = 0; i < localHasCreatorPersonQuery.length; i++) {
                    if (localHasCreatorPersonQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "HasCreatorPersonQuery"));
                        elementList.add(localHasCreatorPersonQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "HasCreatorPersonQuery cannot be null!!");
            }
        }

        if (localHasContributorOrganizationQueryTracker) {
            if (localHasContributorOrganizationQuery != null) {
                for (int i = 0;
                        i < localHasContributorOrganizationQuery.length; i++) {
                    if (localHasContributorOrganizationQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "HasContributorOrganizationQuery"));
                        elementList.add(localHasContributorOrganizationQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "HasContributorOrganizationQuery cannot be null!!");
            }
        }

        if (localHasContributorPersonQueryTracker) {
            if (localHasContributorPersonQuery != null) {
                for (int i = 0; i < localHasContributorPersonQuery.length;
                        i++) {
                    if (localHasContributorPersonQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "HasContributorPersonQuery"));
                        elementList.add(localHasContributorPersonQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "HasContributorPersonQuery cannot be null!!");
            }
        }

        if (localUsedOntologyEngineeringToolQueryTracker) {
            if (localUsedOntologyEngineeringToolQuery != null) {
                for (int i = 0;
                        i < localUsedOntologyEngineeringToolQuery.length;
                        i++) {
                    if (localUsedOntologyEngineeringToolQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "UsedOntologyEngineeringToolQuery"));
                        elementList.add(localUsedOntologyEngineeringToolQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "UsedOntologyEngineeringToolQuery cannot be null!!");
            }
        }

        if (localUsedOntologyEngineeringMethodologyQueryTracker) {
            if (localUsedOntologyEngineeringMethodologyQuery != null) {
                for (int i = 0;
                        i < localUsedOntologyEngineeringMethodologyQuery.length;
                        i++) {
                    if (localUsedOntologyEngineeringMethodologyQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "UsedOntologyEngineeringMethodologyQuery"));
                        elementList.add(localUsedOntologyEngineeringMethodologyQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "UsedOntologyEngineeringMethodologyQuery cannot be null!!");
            }
        }

        if (localUsedKnowledgeRepresentationParadigmQueryTracker) {
            if (localUsedKnowledgeRepresentationParadigmQuery != null) {
                for (int i = 0;
                        i < localUsedKnowledgeRepresentationParadigmQuery.length;
                        i++) {
                    if (localUsedKnowledgeRepresentationParadigmQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "UsedKnowledgeRepresentationParadigmQuery"));
                        elementList.add(localUsedKnowledgeRepresentationParadigmQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "UsedKnowledgeRepresentationParadigmQuery cannot be null!!");
            }
        }

        if (localDesignedForOntologyTaskQueryTracker) {
            if (localDesignedForOntologyTaskQuery != null) {
                for (int i = 0; i < localDesignedForOntologyTaskQuery.length;
                        i++) {
                    if (localDesignedForOntologyTaskQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "DesignedForOntologyTaskQuery"));
                        elementList.add(localDesignedForOntologyTaskQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "DesignedForOntologyTaskQuery cannot be null!!");
            }
        }

        if (localUseImportsQueryTracker) {
            if (localUseImportsQuery != null) {
                for (int i = 0; i < localUseImportsQuery.length; i++) {
                    if (localUseImportsQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "UseImportsQuery"));
                        elementList.add(localUseImportsQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "UseImportsQuery cannot be null!!");
            }
        }

        if (localIsBackwardCompatibleWithQueryTracker) {
            if (localIsBackwardCompatibleWithQuery != null) {
                for (int i = 0; i < localIsBackwardCompatibleWithQuery.length;
                        i++) {
                    if (localIsBackwardCompatibleWithQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "IsBackwardCompatibleWithQuery"));
                        elementList.add(localIsBackwardCompatibleWithQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "IsBackwardCompatibleWithQuery cannot be null!!");
            }
        }

        if (localIsIncompatibleWithQueryTracker) {
            if (localIsIncompatibleWithQuery != null) {
                for (int i = 0; i < localIsIncompatibleWithQuery.length; i++) {
                    if (localIsIncompatibleWithQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "IsIncompatibleWithQuery"));
                        elementList.add(localIsIncompatibleWithQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "IsIncompatibleWithQuery cannot be null!!");
            }
        }

        if (localEndorsedByPersonQueryTracker) {
            if (localEndorsedByPersonQuery != null) {
                for (int i = 0; i < localEndorsedByPersonQuery.length; i++) {
                    if (localEndorsedByPersonQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "EndorsedByPersonQuery"));
                        elementList.add(localEndorsedByPersonQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "EndorsedByPersonQuery cannot be null!!");
            }
        }

        if (localEndorsedByOrganizationQueryTracker) {
            if (localEndorsedByOrganizationQuery != null) {
                for (int i = 0; i < localEndorsedByOrganizationQuery.length;
                        i++) {
                    if (localEndorsedByOrganizationQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "EndorsedByOrganizationQuery"));
                        elementList.add(localEndorsedByOrganizationQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "EndorsedByOrganizationQuery cannot be null!!");
            }
        }

        if (localHasDomainQueryTracker) {
            if (localHasDomainQuery != null) {
                for (int i = 0; i < localHasDomainQuery.length; i++) {
                    if (localHasDomainQuery[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                "HasDomainQuery"));
                        elementList.add(localHasDomainQuery[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "HasDomainQuery cannot be null!!");
            }
        }

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
        public static OntologyQueryType parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            OntologyQueryType object = new OntologyQueryType();

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

                        if (!"OntologyQueryType".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (OntologyQueryType) org.oasis.names.tc.ebxml_regrep.xsd.rs.ExtensionMapper.getTypeObject(nsUri,
                                type, reader);
                        }
                    }
                }

                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

                reader.next();

                java.util.ArrayList list2 = new java.util.ArrayList();

                java.util.ArrayList list6 = new java.util.ArrayList();

                java.util.ArrayList list7 = new java.util.ArrayList();

                java.util.ArrayList list10 = new java.util.ArrayList();

                java.util.ArrayList list11 = new java.util.ArrayList();

                java.util.ArrayList list12 = new java.util.ArrayList();

                java.util.ArrayList list13 = new java.util.ArrayList();

                java.util.ArrayList list14 = new java.util.ArrayList();

                java.util.ArrayList list15 = new java.util.ArrayList();

                java.util.ArrayList list16 = new java.util.ArrayList();

                java.util.ArrayList list17 = new java.util.ArrayList();

                java.util.ArrayList list40 = new java.util.ArrayList();

                java.util.ArrayList list41 = new java.util.ArrayList();

                java.util.ArrayList list42 = new java.util.ArrayList();

                java.util.ArrayList list43 = new java.util.ArrayList();

                java.util.ArrayList list44 = new java.util.ArrayList();

                java.util.ArrayList list45 = new java.util.ArrayList();

                java.util.ArrayList list46 = new java.util.ArrayList();

                java.util.ArrayList list47 = new java.util.ArrayList();

                java.util.ArrayList list48 = new java.util.ArrayList();

                java.util.ArrayList list49 = new java.util.ArrayList();

                java.util.ArrayList list50 = new java.util.ArrayList();

                java.util.ArrayList list51 = new java.util.ArrayList();

                java.util.ArrayList list52 = new java.util.ArrayList();

                java.util.ArrayList list53 = new java.util.ArrayList();

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                            "PrimaryFilter").equals(reader.getName())) {
                    object.setPrimaryFilter(org.oasis.names.tc.ebxml_regrep.xsd.query.FilterType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                            "SlotBranch").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list2.add(org.oasis.names.tc.ebxml_regrep.xsd.query.SlotBranchType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone2 = false;

                    while (!loopDone2) {
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
                            loopDone2 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                                        "SlotBranch").equals(reader.getName())) {
                                list2.add(org.oasis.names.tc.ebxml_regrep.xsd.query.SlotBranchType.Factory.parse(
                                        reader));
                            } else {
                                loopDone2 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setSlotBranch((org.oasis.names.tc.ebxml_regrep.xsd.query.SlotBranchType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.oasis.names.tc.ebxml_regrep.xsd.query.SlotBranchType.class,
                            list2));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                            "NameBranch").equals(reader.getName())) {
                    object.setNameBranch(org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                            "DescriptionBranch").equals(reader.getName())) {
                    object.setDescriptionBranch(org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                            "VersionInfoFilter").equals(reader.getName())) {
                    object.setVersionInfoFilter(org.oasis.names.tc.ebxml_regrep.xsd.query.FilterType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                            "ClassificationQuery").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list6.add(org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationQueryType.Factory.parse(
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
                                        "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                                        "ClassificationQuery").equals(
                                        reader.getName())) {
                                list6.add(org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone6 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setClassificationQuery((org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationQueryType.class,
                            list6));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                            "ExternalIdentifierQuery").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list7.add(org.oasis.names.tc.ebxml_regrep.xsd.query.ExternalIdentifierQueryType.Factory.parse(
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
                                        "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                                        "ExternalIdentifierQuery").equals(
                                        reader.getName())) {
                                list7.add(org.oasis.names.tc.ebxml_regrep.xsd.query.ExternalIdentifierQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone7 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setExternalIdentifierQuery((org.oasis.names.tc.ebxml_regrep.xsd.query.ExternalIdentifierQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.oasis.names.tc.ebxml_regrep.xsd.query.ExternalIdentifierQueryType.class,
                            list7));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                            "ObjectTypeQuery").equals(reader.getName())) {
                    object.setObjectTypeQuery(org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationNodeQueryType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                            "StatusQuery").equals(reader.getName())) {
                    object.setStatusQuery(org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationNodeQueryType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                            "SourceAssociationQuery").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list10.add(org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType.Factory.parse(
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
                                        "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                                        "SourceAssociationQuery").equals(
                                        reader.getName())) {
                                list10.add(org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone10 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setSourceAssociationQuery((org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType.class,
                            list10));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                            "TargetAssociationQuery").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list11.add(org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType.Factory.parse(
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
                                        "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                                        "TargetAssociationQuery").equals(
                                        reader.getName())) {
                                list11.add(org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone11 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setTargetAssociationQuery((org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType.class,
                            list11));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "IsOfTypeQuery").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list12.add(org.neontoolkit.registry.omv.xsd.query.OntologyTypeQueryType.Factory.parse(
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
                                        "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                        "IsOfTypeQuery").equals(
                                        reader.getName())) {
                                list12.add(org.neontoolkit.registry.omv.xsd.query.OntologyTypeQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone12 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setIsOfTypeQuery((org.neontoolkit.registry.omv.xsd.query.OntologyTypeQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.query.OntologyTypeQueryType.class,
                            list12));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "HasOntologySyntaxQuery").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list13.add(org.neontoolkit.registry.omv.xsd.query.OntologySyntaxQueryType.Factory.parse(
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
                                        "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                        "HasOntologySyntaxQuery").equals(
                                        reader.getName())) {
                                list13.add(org.neontoolkit.registry.omv.xsd.query.OntologySyntaxQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone13 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setHasOntologySyntaxQuery((org.neontoolkit.registry.omv.xsd.query.OntologySyntaxQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.query.OntologySyntaxQueryType.class,
                            list13));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "HasOntologyLanguageQuery").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list14.add(org.neontoolkit.registry.omv.xsd.query.OntologyLanguageQueryType.Factory.parse(
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
                                        "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                        "HasOntologyLanguageQuery").equals(
                                        reader.getName())) {
                                list14.add(org.neontoolkit.registry.omv.xsd.query.OntologyLanguageQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone14 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setHasOntologyLanguageQuery((org.neontoolkit.registry.omv.xsd.query.OntologyLanguageQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.query.OntologyLanguageQueryType.class,
                            list14));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "HasLicenseQuery").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list15.add(org.neontoolkit.registry.omv.xsd.query.LicenseModelQueryType.Factory.parse(
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
                                        "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                        "HasLicenseQuery").equals(
                                        reader.getName())) {
                                list15.add(org.neontoolkit.registry.omv.xsd.query.LicenseModelQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone15 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setHasLicenseQuery((org.neontoolkit.registry.omv.xsd.query.LicenseModelQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.query.LicenseModelQueryType.class,
                            list15));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "HasFormalityLevelQuery").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list16.add(org.neontoolkit.registry.omv.xsd.query.FormalityLevelQueryType.Factory.parse(
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
                                        "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                        "HasFormalityLevelQuery").equals(
                                        reader.getName())) {
                                list16.add(org.neontoolkit.registry.omv.xsd.query.FormalityLevelQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone16 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setHasFormalityLevelQuery((org.neontoolkit.registry.omv.xsd.query.FormalityLevelQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.query.FormalityLevelQueryType.class,
                            list16));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "HasPriorVersionQuery").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list17.add(org.neontoolkit.registry.omv.xsd.query.OntologyQueryType.Factory.parse(
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
                                        "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                        "HasPriorVersionQuery").equals(
                                        reader.getName())) {
                                list17.add(org.neontoolkit.registry.omv.xsd.query.OntologyQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone17 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setHasPriorVersionQuery((org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.query.OntologyQueryType.class,
                            list17));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "AcronymFilter").equals(reader.getName())) {
                    object.setAcronymFilter(org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "DocumentationFilter").equals(reader.getName())) {
                    object.setDocumentationFilter(org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "NumberOfClassesFilter").equals(reader.getName())) {
                    object.setNumberOfClassesFilter(org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "NumberOfPropertiesFilter").equals(reader.getName())) {
                    object.setNumberOfPropertiesFilter(org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "NumberOfIndividualsFilter").equals(
                            reader.getName())) {
                    object.setNumberOfIndividualsFilter(org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "NumberOfAxiomsFilter").equals(reader.getName())) {
                    object.setNumberOfAxiomsFilter(org.oasis.names.tc.ebxml_regrep.xsd.query.IntegerFilterType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "OntologyStatusFilter").equals(reader.getName())) {
                    object.setOntologyStatusFilter(org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "CreationDateFilter").equals(reader.getName())) {
                    object.setCreationDateFilter(org.oasis.names.tc.ebxml_regrep.xsd.query.DateTimeFilterType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "ModificationDateFilter").equals(reader.getName())) {
                    object.setModificationDateFilter(org.oasis.names.tc.ebxml_regrep.xsd.query.DateTimeFilterType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "ResourceLocatorFilter").equals(reader.getName())) {
                    object.setResourceLocatorFilter(org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "URIFilter").equals(reader.getName())) {
                    object.setURIFilter(org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "VersionFilter").equals(reader.getName())) {
                    object.setVersionFilter(org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "IsConsistentAccordingToReasonerFilter").equals(
                            reader.getName())) {
                    object.setIsConsistentAccordingToReasonerFilter(org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "ContainsABoxFilter").equals(reader.getName())) {
                    object.setContainsABoxFilter(org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "ContainsRBoxFilter").equals(reader.getName())) {
                    object.setContainsRBoxFilter(org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "ContainsTBoxFilter").equals(reader.getName())) {
                    object.setContainsTBoxFilter(org.oasis.names.tc.ebxml_regrep.xsd.query.BooleanFilterType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "ExpressivenessFilter").equals(reader.getName())) {
                    object.setExpressivenessFilter(org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "KeyClassesFilter").equals(reader.getName())) {
                    object.setKeyClassesFilter(org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "KnownUsageFilter").equals(reader.getName())) {
                    object.setKnownUsageFilter(org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "NotesFilter").equals(reader.getName())) {
                    object.setNotesFilter(org.oasis.names.tc.ebxml_regrep.xsd.query.StringFilterType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "KeywordsBranch").equals(reader.getName())) {
                    object.setKeywordsBranch(org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "NaturalLanguageBranch").equals(reader.getName())) {
                    object.setNaturalLanguageBranch(org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "HasCreatorOrganizationQuery").equals(
                            reader.getName())) {
                    // Process the array and step past its final element's end.
                    list40.add(org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone40 = false;

                    while (!loopDone40) {
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
                            loopDone40 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                        "HasCreatorOrganizationQuery").equals(
                                        reader.getName())) {
                                list40.add(org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone40 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setHasCreatorOrganizationQuery((org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType.class,
                            list40));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "HasCreatorPersonQuery").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list41.add(org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone41 = false;

                    while (!loopDone41) {
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
                            loopDone41 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                        "HasCreatorPersonQuery").equals(
                                        reader.getName())) {
                                list41.add(org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone41 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setHasCreatorPersonQuery((org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType.class,
                            list41));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "HasContributorOrganizationQuery").equals(
                            reader.getName())) {
                    // Process the array and step past its final element's end.
                    list42.add(org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone42 = false;

                    while (!loopDone42) {
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
                            loopDone42 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                        "HasContributorOrganizationQuery").equals(
                                        reader.getName())) {
                                list42.add(org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone42 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setHasContributorOrganizationQuery((org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType.class,
                            list42));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "HasContributorPersonQuery").equals(
                            reader.getName())) {
                    // Process the array and step past its final element's end.
                    list43.add(org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone43 = false;

                    while (!loopDone43) {
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
                            loopDone43 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                        "HasContributorPersonQuery").equals(
                                        reader.getName())) {
                                list43.add(org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone43 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setHasContributorPersonQuery((org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType.class,
                            list43));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "UsedOntologyEngineeringToolQuery").equals(
                            reader.getName())) {
                    // Process the array and step past its final element's end.
                    list44.add(org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringToolQueryType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone44 = false;

                    while (!loopDone44) {
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
                            loopDone44 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                        "UsedOntologyEngineeringToolQuery").equals(
                                        reader.getName())) {
                                list44.add(org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringToolQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone44 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setUsedOntologyEngineeringToolQuery((org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringToolQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringToolQueryType.class,
                            list44));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "UsedOntologyEngineeringMethodologyQuery").equals(
                            reader.getName())) {
                    // Process the array and step past its final element's end.
                    list45.add(org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringMethodologyQueryType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone45 = false;

                    while (!loopDone45) {
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
                            loopDone45 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                        "UsedOntologyEngineeringMethodologyQuery").equals(
                                        reader.getName())) {
                                list45.add(org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringMethodologyQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone45 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setUsedOntologyEngineeringMethodologyQuery((org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringMethodologyQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.query.OntologyEngineeringMethodologyQueryType.class,
                            list45));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "UsedKnowledgeRepresentationParadigmQuery").equals(
                            reader.getName())) {
                    // Process the array and step past its final element's end.
                    list46.add(org.neontoolkit.registry.omv.xsd.query.KnowledgeRepresentationParadigmQueryType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone46 = false;

                    while (!loopDone46) {
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
                            loopDone46 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                        "UsedKnowledgeRepresentationParadigmQuery").equals(
                                        reader.getName())) {
                                list46.add(org.neontoolkit.registry.omv.xsd.query.KnowledgeRepresentationParadigmQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone46 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setUsedKnowledgeRepresentationParadigmQuery((org.neontoolkit.registry.omv.xsd.query.KnowledgeRepresentationParadigmQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.query.KnowledgeRepresentationParadigmQueryType.class,
                            list46));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "DesignedForOntologyTaskQuery").equals(
                            reader.getName())) {
                    // Process the array and step past its final element's end.
                    list47.add(org.neontoolkit.registry.omv.xsd.query.OntologyTaskQueryType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone47 = false;

                    while (!loopDone47) {
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
                            loopDone47 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                        "DesignedForOntologyTaskQuery").equals(
                                        reader.getName())) {
                                list47.add(org.neontoolkit.registry.omv.xsd.query.OntologyTaskQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone47 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setDesignedForOntologyTaskQuery((org.neontoolkit.registry.omv.xsd.query.OntologyTaskQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.query.OntologyTaskQueryType.class,
                            list47));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "UseImportsQuery").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list48.add(org.neontoolkit.registry.omv.xsd.query.OntologyQueryType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone48 = false;

                    while (!loopDone48) {
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
                            loopDone48 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                        "UseImportsQuery").equals(
                                        reader.getName())) {
                                list48.add(org.neontoolkit.registry.omv.xsd.query.OntologyQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone48 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setUseImportsQuery((org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.query.OntologyQueryType.class,
                            list48));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "IsBackwardCompatibleWithQuery").equals(
                            reader.getName())) {
                    // Process the array and step past its final element's end.
                    list49.add(org.neontoolkit.registry.omv.xsd.query.OntologyQueryType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone49 = false;

                    while (!loopDone49) {
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
                            loopDone49 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                        "IsBackwardCompatibleWithQuery").equals(
                                        reader.getName())) {
                                list49.add(org.neontoolkit.registry.omv.xsd.query.OntologyQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone49 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setIsBackwardCompatibleWithQuery((org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.query.OntologyQueryType.class,
                            list49));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "IsIncompatibleWithQuery").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list50.add(org.neontoolkit.registry.omv.xsd.query.OntologyQueryType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone50 = false;

                    while (!loopDone50) {
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
                            loopDone50 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                        "IsIncompatibleWithQuery").equals(
                                        reader.getName())) {
                                list50.add(org.neontoolkit.registry.omv.xsd.query.OntologyQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone50 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setIsIncompatibleWithQuery((org.neontoolkit.registry.omv.xsd.query.OntologyQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.query.OntologyQueryType.class,
                            list50));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "EndorsedByPersonQuery").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list51.add(org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone51 = false;

                    while (!loopDone51) {
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
                            loopDone51 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                        "EndorsedByPersonQuery").equals(
                                        reader.getName())) {
                                list51.add(org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone51 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setEndorsedByPersonQuery((org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.oasis.names.tc.ebxml_regrep.xsd.query.PersonQueryType.class,
                            list51));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "EndorsedByOrganizationQuery").equals(
                            reader.getName())) {
                    // Process the array and step past its final element's end.
                    list52.add(org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone52 = false;

                    while (!loopDone52) {
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
                            loopDone52 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                        "EndorsedByOrganizationQuery").equals(
                                        reader.getName())) {
                                list52.add(org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone52 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setEndorsedByOrganizationQuery((org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.oasis.names.tc.ebxml_regrep.xsd.query.OrganizationQueryType.class,
                            list52));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                            "HasDomainQuery").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list53.add(org.neontoolkit.registry.omv.xsd.query.OntologyDomainQueryType.Factory.parse(
                            reader));

                    //loop until we find a start element that is not part of this array
                    boolean loopDone53 = false;

                    while (!loopDone53) {
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
                            loopDone53 = true;
                        } else {
                            if (new javax.xml.namespace.QName(
                                        "urn:neon-toolkit-org:registry:omv:xsd:query:2.3",
                                        "HasDomainQuery").equals(
                                        reader.getName())) {
                                list53.add(org.neontoolkit.registry.omv.xsd.query.OntologyDomainQueryType.Factory.parse(
                                        reader));
                            } else {
                                loopDone53 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setHasDomainQuery((org.neontoolkit.registry.omv.xsd.query.OntologyDomainQueryType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.neontoolkit.registry.omv.xsd.query.OntologyDomainQueryType.class,
                            list53));
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
