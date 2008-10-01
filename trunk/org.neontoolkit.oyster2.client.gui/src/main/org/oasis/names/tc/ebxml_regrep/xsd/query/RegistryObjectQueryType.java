/**
 * RegistryObjectQueryType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:58 LKT)
 */
package org.oasis.names.tc.ebxml_regrep.xsd.query;


/**
 *  RegistryObjectQueryType bean class
 */
public class RegistryObjectQueryType extends org.oasis.names.tc.ebxml_regrep.xsd.query.FilterQueryType
    implements org.apache.axis2.databinding.ADBBean {
    /**
     * field for SlotBranch
     * This was an Array!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.SlotBranchType[] localSlotBranch;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localSlotBranchTracker = false;

    /**
     * field for NameBranch
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType localNameBranch;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localNameBranchTracker = false;

    /**
     * field for DescriptionBranch
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType localDescriptionBranch;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localDescriptionBranchTracker = false;

    /**
     * field for VersionInfoFilter
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.FilterType localVersionInfoFilter;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localVersionInfoFilterTracker = false;

    /**
     * field for ClassificationQuery
     * This was an Array!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationQueryType[] localClassificationQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localClassificationQueryTracker = false;

    /**
     * field for ExternalIdentifierQuery
     * This was an Array!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.ExternalIdentifierQueryType[] localExternalIdentifierQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localExternalIdentifierQueryTracker = false;

    /**
     * field for ObjectTypeQuery
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationNodeQueryType localObjectTypeQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localObjectTypeQueryTracker = false;

    /**
     * field for StatusQuery
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationNodeQueryType localStatusQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localStatusQueryTracker = false;

    /**
     * field for SourceAssociationQuery
     * This was an Array!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType[] localSourceAssociationQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localSourceAssociationQueryTracker = false;

    /**
     * field for TargetAssociationQuery
     * This was an Array!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType[] localTargetAssociationQuery;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localTargetAssociationQueryTracker = false;

    /* This type was generated from the piece of schema that had
       name = RegistryObjectQueryType
       Namespace URI = urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0
       Namespace Prefix = ns3
     */
    private static java.lang.String generatePrefix(java.lang.String namespace) {
        if (namespace.equals("urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0")) {
            return "ns3";
        }

        return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.SlotBranchType[]
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.SlotBranchType[] getSlotBranch() {
        return localSlotBranch;
    }

    /**
     * validate the array for SlotBranch
     */
    protected void validateSlotBranch(
        org.oasis.names.tc.ebxml_regrep.xsd.query.SlotBranchType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param SlotBranch
     */
    public void setSlotBranch(
        org.oasis.names.tc.ebxml_regrep.xsd.query.SlotBranchType[] param) {
        validateSlotBranch(param);

        if (param != null) {
            //update the setting tracker
            localSlotBranchTracker = true;
        } else {
            localSlotBranchTracker = false;
        }

        this.localSlotBranch = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.oasis.names.tc.ebxml_regrep.xsd.query.SlotBranchType
     */
    public void addSlotBranch(
        org.oasis.names.tc.ebxml_regrep.xsd.query.SlotBranchType param) {
        if (localSlotBranch == null) {
            localSlotBranch = new org.oasis.names.tc.ebxml_regrep.xsd.query.SlotBranchType[] {
                    
                };
        }

        //update the setting tracker
        localSlotBranchTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localSlotBranch);
        list.add(param);
        this.localSlotBranch = (org.oasis.names.tc.ebxml_regrep.xsd.query.SlotBranchType[]) list.toArray(new org.oasis.names.tc.ebxml_regrep.xsd.query.SlotBranchType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType getNameBranch() {
        return localNameBranch;
    }

    /**
     * Auto generated setter method
     * @param param NameBranch
     */
    public void setNameBranch(
        org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType param) {
        if (param != null) {
            //update the setting tracker
            localNameBranchTracker = true;
        } else {
            localNameBranchTracker = false;
        }

        this.localNameBranch = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType getDescriptionBranch() {
        return localDescriptionBranch;
    }

    /**
     * Auto generated setter method
     * @param param DescriptionBranch
     */
    public void setDescriptionBranch(
        org.oasis.names.tc.ebxml_regrep.xsd.query.InternationalStringBranchType param) {
        if (param != null) {
            //update the setting tracker
            localDescriptionBranchTracker = true;
        } else {
            localDescriptionBranchTracker = false;
        }

        this.localDescriptionBranch = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.FilterType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.FilterType getVersionInfoFilter() {
        return localVersionInfoFilter;
    }

    /**
     * Auto generated setter method
     * @param param VersionInfoFilter
     */
    public void setVersionInfoFilter(
        org.oasis.names.tc.ebxml_regrep.xsd.query.FilterType param) {
        if (param != null) {
            //update the setting tracker
            localVersionInfoFilterTracker = true;
        } else {
            localVersionInfoFilterTracker = false;
        }

        this.localVersionInfoFilter = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationQueryType[]
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationQueryType[] getClassificationQuery() {
        return localClassificationQuery;
    }

    /**
     * validate the array for ClassificationQuery
     */
    protected void validateClassificationQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param ClassificationQuery
     */
    public void setClassificationQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationQueryType[] param) {
        validateClassificationQuery(param);

        if (param != null) {
            //update the setting tracker
            localClassificationQueryTracker = true;
        } else {
            localClassificationQueryTracker = false;
        }

        this.localClassificationQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationQueryType
     */
    public void addClassificationQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationQueryType param) {
        if (localClassificationQuery == null) {
            localClassificationQuery = new org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationQueryType[] {
                    
                };
        }

        //update the setting tracker
        localClassificationQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localClassificationQuery);
        list.add(param);
        this.localClassificationQuery = (org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationQueryType[]) list.toArray(new org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationQueryType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.ExternalIdentifierQueryType[]
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.ExternalIdentifierQueryType[] getExternalIdentifierQuery() {
        return localExternalIdentifierQuery;
    }

    /**
     * validate the array for ExternalIdentifierQuery
     */
    protected void validateExternalIdentifierQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.ExternalIdentifierQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param ExternalIdentifierQuery
     */
    public void setExternalIdentifierQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.ExternalIdentifierQueryType[] param) {
        validateExternalIdentifierQuery(param);

        if (param != null) {
            //update the setting tracker
            localExternalIdentifierQueryTracker = true;
        } else {
            localExternalIdentifierQueryTracker = false;
        }

        this.localExternalIdentifierQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.oasis.names.tc.ebxml_regrep.xsd.query.ExternalIdentifierQueryType
     */
    public void addExternalIdentifierQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.ExternalIdentifierQueryType param) {
        if (localExternalIdentifierQuery == null) {
            localExternalIdentifierQuery = new org.oasis.names.tc.ebxml_regrep.xsd.query.ExternalIdentifierQueryType[] {
                    
                };
        }

        //update the setting tracker
        localExternalIdentifierQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localExternalIdentifierQuery);
        list.add(param);
        this.localExternalIdentifierQuery = (org.oasis.names.tc.ebxml_regrep.xsd.query.ExternalIdentifierQueryType[]) list.toArray(new org.oasis.names.tc.ebxml_regrep.xsd.query.ExternalIdentifierQueryType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationNodeQueryType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationNodeQueryType getObjectTypeQuery() {
        return localObjectTypeQuery;
    }

    /**
     * Auto generated setter method
     * @param param ObjectTypeQuery
     */
    public void setObjectTypeQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationNodeQueryType param) {
        if (param != null) {
            //update the setting tracker
            localObjectTypeQueryTracker = true;
        } else {
            localObjectTypeQueryTracker = false;
        }

        this.localObjectTypeQuery = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationNodeQueryType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationNodeQueryType getStatusQuery() {
        return localStatusQuery;
    }

    /**
     * Auto generated setter method
     * @param param StatusQuery
     */
    public void setStatusQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.ClassificationNodeQueryType param) {
        if (param != null) {
            //update the setting tracker
            localStatusQueryTracker = true;
        } else {
            localStatusQueryTracker = false;
        }

        this.localStatusQuery = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType[]
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType[] getSourceAssociationQuery() {
        return localSourceAssociationQuery;
    }

    /**
     * validate the array for SourceAssociationQuery
     */
    protected void validateSourceAssociationQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param SourceAssociationQuery
     */
    public void setSourceAssociationQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType[] param) {
        validateSourceAssociationQuery(param);

        if (param != null) {
            //update the setting tracker
            localSourceAssociationQueryTracker = true;
        } else {
            localSourceAssociationQueryTracker = false;
        }

        this.localSourceAssociationQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType
     */
    public void addSourceAssociationQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType param) {
        if (localSourceAssociationQuery == null) {
            localSourceAssociationQuery = new org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType[] {
                    
                };
        }

        //update the setting tracker
        localSourceAssociationQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localSourceAssociationQuery);
        list.add(param);
        this.localSourceAssociationQuery = (org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType[]) list.toArray(new org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType[]
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType[] getTargetAssociationQuery() {
        return localTargetAssociationQuery;
    }

    /**
     * validate the array for TargetAssociationQuery
     */
    protected void validateTargetAssociationQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param TargetAssociationQuery
     */
    public void setTargetAssociationQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType[] param) {
        validateTargetAssociationQuery(param);

        if (param != null) {
            //update the setting tracker
            localTargetAssociationQueryTracker = true;
        } else {
            localTargetAssociationQueryTracker = false;
        }

        this.localTargetAssociationQuery = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType
     */
    public void addTargetAssociationQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType param) {
        if (localTargetAssociationQuery == null) {
            localTargetAssociationQuery = new org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType[] {
                    
                };
        }

        //update the setting tracker
        localTargetAssociationQueryTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localTargetAssociationQuery);
        list.add(param);
        this.localTargetAssociationQuery = (org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType[]) list.toArray(new org.oasis.names.tc.ebxml_regrep.xsd.query.AssociationQueryType[list.size()]);
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
                    RegistryObjectQueryType.this.serialize(parentQName,
                        factory, xmlWriter);
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
                "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0");

        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "type", namespacePrefix + ":RegistryObjectQueryType", xmlWriter);
        } else {
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "type", "RegistryObjectQueryType", xmlWriter);
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
                "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                "RegistryObjectQueryType"));

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
        public static RegistryObjectQueryType parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            RegistryObjectQueryType object = new RegistryObjectQueryType();

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

                        if (!"RegistryObjectQueryType".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (RegistryObjectQueryType) org.oasis.names.tc.ebxml_regrep.xsd.rs.ExtensionMapper.getTypeObject(nsUri,
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
