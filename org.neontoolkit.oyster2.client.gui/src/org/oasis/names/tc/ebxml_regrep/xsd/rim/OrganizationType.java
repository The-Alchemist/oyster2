/**
 * OrganizationType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:58 LKT)
 */
package org.oasis.names.tc.ebxml_regrep.xsd.rim;


/**
 *  OrganizationType bean class
 */
public class OrganizationType extends org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType
    implements org.apache.axis2.databinding.ADBBean {
    /**
     * field for Address
     * This was an Array!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.PostalAddressType[] localAddress;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localAddressTracker = false;

    /**
     * field for TelephoneNumber
     * This was an Array!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.TelephoneNumberType[] localTelephoneNumber;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localTelephoneNumberTracker = false;

    /**
     * field for EmailAddress
     * This was an Array!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.EmailAddressType[] localEmailAddress;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localEmailAddressTracker = false;

    /**
     * field for Parent
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI localParent;

    /**
     * field for PrimaryContact
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI localPrimaryContact;

    /* This type was generated from the piece of schema that had
       name = OrganizationType
       Namespace URI = urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0
       Namespace Prefix = ns1
     */
    private static java.lang.String generatePrefix(java.lang.String namespace) {
        if (namespace.equals("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0")) {
            return "ns1";
        }

        return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.PostalAddressType[]
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.PostalAddressType[] getAddress() {
        return localAddress;
    }

    /**
     * validate the array for Address
     */
    protected void validateAddress(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.PostalAddressType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param Address
     */
    public void setAddress(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.PostalAddressType[] param) {
        validateAddress(param);

        if (param != null) {
            //update the setting tracker
            localAddressTracker = true;
        } else {
            localAddressTracker = false;
        }

        this.localAddress = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.oasis.names.tc.ebxml_regrep.xsd.rim.PostalAddressType
     */
    public void addAddress(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.PostalAddressType param) {
        if (localAddress == null) {
            localAddress = new org.oasis.names.tc.ebxml_regrep.xsd.rim.PostalAddressType[] {
                    
                };
        }

        //update the setting tracker
        localAddressTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localAddress);
        list.add(param);
        this.localAddress = (org.oasis.names.tc.ebxml_regrep.xsd.rim.PostalAddressType[]) list.toArray(new org.oasis.names.tc.ebxml_regrep.xsd.rim.PostalAddressType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.TelephoneNumberType[]
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.TelephoneNumberType[] getTelephoneNumber() {
        return localTelephoneNumber;
    }

    /**
     * validate the array for TelephoneNumber
     */
    protected void validateTelephoneNumber(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.TelephoneNumberType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param TelephoneNumber
     */
    public void setTelephoneNumber(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.TelephoneNumberType[] param) {
        validateTelephoneNumber(param);

        if (param != null) {
            //update the setting tracker
            localTelephoneNumberTracker = true;
        } else {
            localTelephoneNumberTracker = false;
        }

        this.localTelephoneNumber = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.oasis.names.tc.ebxml_regrep.xsd.rim.TelephoneNumberType
     */
    public void addTelephoneNumber(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.TelephoneNumberType param) {
        if (localTelephoneNumber == null) {
            localTelephoneNumber = new org.oasis.names.tc.ebxml_regrep.xsd.rim.TelephoneNumberType[] {
                    
                };
        }

        //update the setting tracker
        localTelephoneNumberTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localTelephoneNumber);
        list.add(param);
        this.localTelephoneNumber = (org.oasis.names.tc.ebxml_regrep.xsd.rim.TelephoneNumberType[]) list.toArray(new org.oasis.names.tc.ebxml_regrep.xsd.rim.TelephoneNumberType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.EmailAddressType[]
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.EmailAddressType[] getEmailAddress() {
        return localEmailAddress;
    }

    /**
     * validate the array for EmailAddress
     */
    protected void validateEmailAddress(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.EmailAddressType[] param) {
    }

    /**
     * Auto generated setter method
     * @param param EmailAddress
     */
    public void setEmailAddress(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.EmailAddressType[] param) {
        validateEmailAddress(param);

        if (param != null) {
            //update the setting tracker
            localEmailAddressTracker = true;
        } else {
            localEmailAddressTracker = false;
        }

        this.localEmailAddress = param;
    }

    /**
     * Auto generated add method for the array for convenience
     * @param param org.oasis.names.tc.ebxml_regrep.xsd.rim.EmailAddressType
     */
    public void addEmailAddress(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.EmailAddressType param) {
        if (localEmailAddress == null) {
            localEmailAddress = new org.oasis.names.tc.ebxml_regrep.xsd.rim.EmailAddressType[] {
                    
                };
        }

        //update the setting tracker
        localEmailAddressTracker = true;

        java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil.toList(localEmailAddress);
        list.add(param);
        this.localEmailAddress = (org.oasis.names.tc.ebxml_regrep.xsd.rim.EmailAddressType[]) list.toArray(new org.oasis.names.tc.ebxml_regrep.xsd.rim.EmailAddressType[list.size()]);
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI getParent() {
        return localParent;
    }

    /**
     * Auto generated setter method
     * @param param Parent
     */
    public void setParent(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI param) {
        this.localParent = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI getPrimaryContact() {
        return localPrimaryContact;
    }

    /**
     * Auto generated setter method
     * @param param PrimaryContact
     */
    public void setPrimaryContact(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI param) {
        this.localPrimaryContact = param;
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
                    OrganizationType.this.serialize(parentQName, factory,
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
                "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0");

        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "type", namespacePrefix + ":OrganizationType", xmlWriter);
        } else {
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "type", "OrganizationType", xmlWriter);
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

        if (localParent != null) {
            writeAttribute("", "parent", localParent.toString(), xmlWriter);
        }

        if (localPrimaryContact != null) {
            writeAttribute("", "primaryContact",
                localPrimaryContact.toString(), xmlWriter);
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

        if (localAddressTracker) {
            if (localAddress != null) {
                for (int i = 0; i < localAddress.length; i++) {
                    if (localAddress[i] != null) {
                        localAddress[i].serialize(new javax.xml.namespace.QName(
                                "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                                "Address"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "Address cannot be null!!");
            }
        }

        if (localTelephoneNumberTracker) {
            if (localTelephoneNumber != null) {
                for (int i = 0; i < localTelephoneNumber.length; i++) {
                    if (localTelephoneNumber[i] != null) {
                        localTelephoneNumber[i].serialize(new javax.xml.namespace.QName(
                                "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                                "TelephoneNumber"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "TelephoneNumber cannot be null!!");
            }
        }

        if (localEmailAddressTracker) {
            if (localEmailAddress != null) {
                for (int i = 0; i < localEmailAddress.length; i++) {
                    if (localEmailAddress[i] != null) {
                        localEmailAddress[i].serialize(new javax.xml.namespace.QName(
                                "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                                "EmailAddress"), factory, xmlWriter);
                    } else {
                        // we don't have to do any thing since minOccures is zero
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "EmailAddress cannot be null!!");
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
                "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                "OrganizationType"));

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

        if (localAddressTracker) {
            if (localAddress != null) {
                for (int i = 0; i < localAddress.length; i++) {
                    if (localAddress[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                                "Address"));
                        elementList.add(localAddress[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "Address cannot be null!!");
            }
        }

        if (localTelephoneNumberTracker) {
            if (localTelephoneNumber != null) {
                for (int i = 0; i < localTelephoneNumber.length; i++) {
                    if (localTelephoneNumber[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                                "TelephoneNumber"));
                        elementList.add(localTelephoneNumber[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "TelephoneNumber cannot be null!!");
            }
        }

        if (localEmailAddressTracker) {
            if (localEmailAddress != null) {
                for (int i = 0; i < localEmailAddress.length; i++) {
                    if (localEmailAddress[i] != null) {
                        elementList.add(new javax.xml.namespace.QName(
                                "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                                "EmailAddress"));
                        elementList.add(localEmailAddress[i]);
                    } else {
                        // nothing to do
                    }
                }
            } else {
                throw new org.apache.axis2.databinding.ADBException(
                    "EmailAddress cannot be null!!");
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

        attribList.add(new javax.xml.namespace.QName("", "parent"));

        attribList.add(localParent.toString());

        attribList.add(new javax.xml.namespace.QName("", "primaryContact"));

        attribList.add(localPrimaryContact.toString());

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
        public static OrganizationType parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            OrganizationType object = new OrganizationType();

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

                        if (!"OrganizationType".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (OrganizationType) org.oasis.names.tc.ebxml_regrep.xsd.rs.ExtensionMapper.getTypeObject(nsUri,
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

                // handle attribute "parent"
                java.lang.String tempAttribParent = reader.getAttributeValue(null,
                        "parent");

                if (tempAttribParent != null) {
                    java.lang.String content = tempAttribParent;

                    if (tempAttribParent.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribParent.substring(0,
                                tempAttribParent.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setParent(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribParent, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setParent(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribParent, ""));
                    }
                } else {
                }

                handledAttributes.add("parent");

                // handle attribute "primaryContact"
                java.lang.String tempAttribPrimaryContact = reader.getAttributeValue(null,
                        "primaryContact");

                if (tempAttribPrimaryContact != null) {
                    java.lang.String content = tempAttribPrimaryContact;

                    if (tempAttribPrimaryContact.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribPrimaryContact.substring(0,
                                tempAttribPrimaryContact.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setPrimaryContact(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribPrimaryContact, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setPrimaryContact(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribPrimaryContact, ""));
                    }
                } else {
                }

                handledAttributes.add("primaryContact");

                reader.next();

                java.util.ArrayList list1 = new java.util.ArrayList();

                java.util.ArrayList list5 = new java.util.ArrayList();

                java.util.ArrayList list6 = new java.util.ArrayList();

                java.util.ArrayList list7 = new java.util.ArrayList();

                java.util.ArrayList list8 = new java.util.ArrayList();

                java.util.ArrayList list9 = new java.util.ArrayList();

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
                            "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                            "Address").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list7.add(org.oasis.names.tc.ebxml_regrep.xsd.rim.PostalAddressType.Factory.parse(
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
                                        "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                                        "Address").equals(reader.getName())) {
                                list7.add(org.oasis.names.tc.ebxml_regrep.xsd.rim.PostalAddressType.Factory.parse(
                                        reader));
                            } else {
                                loopDone7 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setAddress((org.oasis.names.tc.ebxml_regrep.xsd.rim.PostalAddressType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.oasis.names.tc.ebxml_regrep.xsd.rim.PostalAddressType.class,
                            list7));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                            "TelephoneNumber").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list8.add(org.oasis.names.tc.ebxml_regrep.xsd.rim.TelephoneNumberType.Factory.parse(
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
                                        "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                                        "TelephoneNumber").equals(
                                        reader.getName())) {
                                list8.add(org.oasis.names.tc.ebxml_regrep.xsd.rim.TelephoneNumberType.Factory.parse(
                                        reader));
                            } else {
                                loopDone8 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setTelephoneNumber((org.oasis.names.tc.ebxml_regrep.xsd.rim.TelephoneNumberType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.oasis.names.tc.ebxml_regrep.xsd.rim.TelephoneNumberType.class,
                            list8));
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                            "EmailAddress").equals(reader.getName())) {
                    // Process the array and step past its final element's end.
                    list9.add(org.oasis.names.tc.ebxml_regrep.xsd.rim.EmailAddressType.Factory.parse(
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
                                        "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                                        "EmailAddress").equals(reader.getName())) {
                                list9.add(org.oasis.names.tc.ebxml_regrep.xsd.rim.EmailAddressType.Factory.parse(
                                        reader));
                            } else {
                                loopDone9 = true;
                            }
                        }
                    }

                    // call the converter utility  to convert and set the array
                    object.setEmailAddress((org.oasis.names.tc.ebxml_regrep.xsd.rim.EmailAddressType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                            org.oasis.names.tc.ebxml_regrep.xsd.rim.EmailAddressType.class,
                            list9));
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
