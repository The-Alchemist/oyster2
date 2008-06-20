/**
 * RegistryResponseType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:58 LKT)
 */
package org.oasis.names.tc.ebxml_regrep.xsd.rs;


/**
 *  RegistryResponseType bean class
 */
public class RegistryResponseType implements org.apache.axis2.databinding.ADBBean {
    /**
     * field for ResponseSlotList
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.SlotListType localResponseSlotList;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localResponseSlotListTracker = false;

    /**
     * field for RegistryErrorList
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryErrorListType localRegistryErrorList;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localRegistryErrorListTracker = false;

    /**
     * field for Status
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI localStatus;

    /**
     * field for RequestId
     * This was an Attribute!
     */
    protected org.apache.axis2.databinding.types.URI localRequestId;

    /* This type was generated from the piece of schema that had
       name = RegistryResponseType
       Namespace URI = urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0
       Namespace Prefix = ns2
     */
    private static java.lang.String generatePrefix(java.lang.String namespace) {
        if (namespace.equals("urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0")) {
            return "ns2";
        }

        return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.SlotListType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.SlotListType getResponseSlotList() {
        return localResponseSlotList;
    }

    /**
     * Auto generated setter method
     * @param param ResponseSlotList
     */
    public void setResponseSlotList(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.SlotListType param) {
        if (param != null) {
            //update the setting tracker
            localResponseSlotListTracker = true;
        } else {
            localResponseSlotListTracker = false;
        }

        this.localResponseSlotList = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryErrorListType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryErrorListType getRegistryErrorList() {
        return localRegistryErrorList;
    }

    /**
     * Auto generated setter method
     * @param param RegistryErrorList
     */
    public void setRegistryErrorList(
        org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryErrorListType param) {
        if (param != null) {
            //update the setting tracker
            localRegistryErrorListTracker = true;
        } else {
            localRegistryErrorListTracker = false;
        }

        this.localRegistryErrorList = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI getStatus() {
        return localStatus;
    }

    /**
     * Auto generated setter method
     * @param param Status
     */
    public void setStatus(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI param) {
        this.localStatus = param;
    }

    /**
     * Auto generated getter method
     * @return org.apache.axis2.databinding.types.URI
     */
    public org.apache.axis2.databinding.types.URI getRequestId() {
        return localRequestId;
    }

    /**
     * Auto generated setter method
     * @param param RequestId
     */
    public void setRequestId(org.apache.axis2.databinding.types.URI param) {
        this.localRequestId = param;
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
                    RegistryResponseType.this.serialize(parentQName, factory,
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

        if (localStatus != null) {
            writeAttribute("", "status", localStatus.toString(), xmlWriter);
        } else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localStatus is null");
        }

        if (localRequestId != null) {
            writeAttribute("", "requestId",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localRequestId), xmlWriter);
        }

        if (localResponseSlotListTracker) {
            if (localResponseSlotList == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "ResponseSlotList cannot be null!!");
            }

            localResponseSlotList.serialize(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0",
                    "ResponseSlotList"), factory, xmlWriter);
        }

        if (localRegistryErrorListTracker) {
            if (localRegistryErrorList == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "RegistryErrorList cannot be null!!");
            }

            localRegistryErrorList.serialize(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0",
                    "RegistryErrorList"), factory, xmlWriter);
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

        if (localResponseSlotListTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0",
                    "ResponseSlotList"));

            if (localResponseSlotList == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "ResponseSlotList cannot be null!!");
            }

            elementList.add(localResponseSlotList);
        }

        if (localRegistryErrorListTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0",
                    "RegistryErrorList"));

            if (localRegistryErrorList == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "RegistryErrorList cannot be null!!");
            }

            elementList.add(localRegistryErrorList);
        }

        attribList.add(new javax.xml.namespace.QName("", "status"));

        attribList.add(localStatus.toString());

        attribList.add(new javax.xml.namespace.QName("", "requestId"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localRequestId));

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
        public static RegistryResponseType parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            RegistryResponseType object = new RegistryResponseType();

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

                        if (!"RegistryResponseType".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (RegistryResponseType) org.oasis.names.tc.ebxml_regrep.xsd.rs.ExtensionMapper.getTypeObject(nsUri,
                                type, reader);
                        }
                    }
                }

                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

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
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute status is missing");
                }

                handledAttributes.add("status");

                // handle attribute "requestId"
                java.lang.String tempAttribRequestId = reader.getAttributeValue(null,
                        "requestId");

                if (tempAttribRequestId != null) {
                    java.lang.String content = tempAttribRequestId;

                    object.setRequestId(org.apache.axis2.databinding.utils.ConverterUtil.convertToAnyURI(
                            tempAttribRequestId));
                } else {
                }

                handledAttributes.add("requestId");

                reader.next();

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0",
                            "ResponseSlotList").equals(reader.getName())) {
                    object.setResponseSlotList(org.oasis.names.tc.ebxml_regrep.xsd.rim.SlotListType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0",
                            "RegistryErrorList").equals(reader.getName())) {
                    object.setRegistryErrorList(org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryErrorListType.Factory.parse(
                            reader));

                    reader.next();
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
