/**
 * AdhocQueryRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:58 LKT)
 */
package org.oasis.names.tc.ebxml_regrep.xsd.query;


/**
 *  AdhocQueryRequest bean class
 */
public class AdhocQueryRequest extends org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryRequestType
    implements org.apache.axis2.databinding.ADBBean {
    public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
            "AdhocQueryRequest", "ns3");

    /**
     * field for ResponseOption
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.query.ResponseOptionType localResponseOption;

    /**
     * field for AdhocQuery
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.AdhocQueryType localAdhocQuery;

    /**
     * field for Federated
     * This was an Attribute!
     */
    protected boolean localFederated;

    /**
     * field for Federation
     * This was an Attribute!
     */
    protected org.apache.axis2.databinding.types.URI localFederation;

    /**
     * field for StartIndex
     * This was an Attribute!
     */
    protected java.math.BigInteger localStartIndex;

    /**
     * field for MaxResults
     * This was an Attribute!
     */
    protected java.math.BigInteger localMaxResults;

    private static java.lang.String generatePrefix(java.lang.String namespace) {
        if (namespace.equals("urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0")) {
            return "ns3";
        }

        return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.query.ResponseOptionType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.ResponseOptionType getResponseOption() {
        return localResponseOption;
    }

    /**
     * Auto generated setter method
     * @param param ResponseOption
     */
    public void setResponseOption(
        org.oasis.names.tc.ebxml_regrep.xsd.query.ResponseOptionType param) {
        this.localResponseOption = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.AdhocQueryType
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.AdhocQueryType getAdhocQuery() {
        return localAdhocQuery;
    }

    /**
     * Auto generated setter method
     * @param param AdhocQuery
     */
    public void setAdhocQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.AdhocQueryType param) {
        this.localAdhocQuery = param;
    }

    /**
     * Auto generated getter method
     * @return boolean
     */
    public boolean getFederated() {
        return localFederated;
    }

    /**
     * Auto generated setter method
     * @param param Federated
     */
    public void setFederated(boolean param) {
        this.localFederated = param;
    }

    /**
     * Auto generated getter method
     * @return org.apache.axis2.databinding.types.URI
     */
    public org.apache.axis2.databinding.types.URI getFederation() {
        return localFederation;
    }

    /**
     * Auto generated setter method
     * @param param Federation
     */
    public void setFederation(org.apache.axis2.databinding.types.URI param) {
        this.localFederation = param;
    }

    /**
     * Auto generated getter method
     * @return java.math.BigInteger
     */
    public java.math.BigInteger getStartIndex() {
        return localStartIndex;
    }

    /**
     * Auto generated setter method
     * @param param StartIndex
     */
    public void setStartIndex(java.math.BigInteger param) {
        this.localStartIndex = param;
    }

    /**
     * Auto generated getter method
     * @return java.math.BigInteger
     */
    public java.math.BigInteger getMaxResults() {
        return localMaxResults;
    }

    /**
     * Auto generated setter method
     * @param param MaxResults
     */
    public void setMaxResults(java.math.BigInteger param) {
        this.localMaxResults = param;
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
                MY_QNAME) {
                public void serialize(
                    org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                    throws javax.xml.stream.XMLStreamException {
                    AdhocQueryRequest.this.serialize(MY_QNAME, factory,
                        xmlWriter);
                }
            };

        return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(MY_QNAME,
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
                "type", namespacePrefix + ":AdhocQueryRequest", xmlWriter);
        } else {
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "type", "AdhocQueryRequest", xmlWriter);
        }

        if (localId != null) {
            writeAttribute("", "id",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localId), xmlWriter);
        }

        if (localComment != null) {
            writeAttribute("", "comment",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localComment), xmlWriter);
        }

        if (true) {
            writeAttribute("", "federated",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localFederated), xmlWriter);
        }

        if (localFederation != null) {
            writeAttribute("", "federation",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localFederation), xmlWriter);
        }

        if (localStartIndex != null) {
            writeAttribute("", "startIndex",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localStartIndex), xmlWriter);
        }

        if (localMaxResults != null) {
            writeAttribute("", "maxResults",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localMaxResults), xmlWriter);
        }

        if (localRequestSlotListTracker) {
            if (localRequestSlotList == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "RequestSlotList cannot be null!!");
            }

            localRequestSlotList.serialize(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0",
                    "RequestSlotList"), factory, xmlWriter);
        }

        if (localResponseOption == null) {
            throw new org.apache.axis2.databinding.ADBException(
                "ResponseOption cannot be null!!");
        }

        localResponseOption.serialize(new javax.xml.namespace.QName(
                "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                "ResponseOption"), factory, xmlWriter);

        if (localAdhocQuery == null) {
            throw new org.apache.axis2.databinding.ADBException(
                "AdhocQuery cannot be null!!");
        }

        localAdhocQuery.serialize(new javax.xml.namespace.QName(
                "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "AdhocQuery"),
            factory, xmlWriter);

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
                "AdhocQueryRequest"));

        if (localRequestSlotListTracker) {
            elementList.add(new javax.xml.namespace.QName(
                    "urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0",
                    "RequestSlotList"));

            if (localRequestSlotList == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "RequestSlotList cannot be null!!");
            }

            elementList.add(localRequestSlotList);
        }

        elementList.add(new javax.xml.namespace.QName(
                "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0",
                "ResponseOption"));

        if (localResponseOption == null) {
            throw new org.apache.axis2.databinding.ADBException(
                "ResponseOption cannot be null!!");
        }

        elementList.add(localResponseOption);

        elementList.add(new javax.xml.namespace.QName(
                "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "AdhocQuery"));

        if (localAdhocQuery == null) {
            throw new org.apache.axis2.databinding.ADBException(
                "AdhocQuery cannot be null!!");
        }

        elementList.add(localAdhocQuery);

        attribList.add(new javax.xml.namespace.QName("", "id"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localId));

        attribList.add(new javax.xml.namespace.QName("", "comment"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localComment));

        attribList.add(new javax.xml.namespace.QName("", "federated"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localFederated));

        attribList.add(new javax.xml.namespace.QName("", "federation"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localFederation));

        attribList.add(new javax.xml.namespace.QName("", "startIndex"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localStartIndex));

        attribList.add(new javax.xml.namespace.QName("", "maxResults"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localMaxResults));

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
        public static AdhocQueryRequest parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            AdhocQueryRequest object = new AdhocQueryRequest();

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

                        if (!"AdhocQueryRequest".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (AdhocQueryRequest) org.oasis.names.tc.ebxml_regrep.xsd.rs.ExtensionMapper.getTypeObject(nsUri,
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
                }

                handledAttributes.add("id");

                // handle attribute "comment"
                java.lang.String tempAttribComment = reader.getAttributeValue(null,
                        "comment");

                if (tempAttribComment != null) {
                    java.lang.String content = tempAttribComment;

                    object.setComment(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribComment));
                } else {
                }

                handledAttributes.add("comment");

                // handle attribute "federated"
                java.lang.String tempAttribFederated = reader.getAttributeValue(null,
                        "federated");

                if (tempAttribFederated != null) {
                    java.lang.String content = tempAttribFederated;

                    object.setFederated(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                            tempAttribFederated));
                } else {
                }

                handledAttributes.add("federated");

                // handle attribute "federation"
                java.lang.String tempAttribFederation = reader.getAttributeValue(null,
                        "federation");

                if (tempAttribFederation != null) {
                    java.lang.String content = tempAttribFederation;

                    object.setFederation(org.apache.axis2.databinding.utils.ConverterUtil.convertToAnyURI(
                            tempAttribFederation));
                } else {
                }

                handledAttributes.add("federation");

                // handle attribute "startIndex"
                java.lang.String tempAttribStartIndex = reader.getAttributeValue(null,
                        "startIndex");

                if (tempAttribStartIndex != null) {
                    java.lang.String content = tempAttribStartIndex;

                    object.setStartIndex(org.apache.axis2.databinding.utils.ConverterUtil.convertToInteger(
                            tempAttribStartIndex));
                } else {
                }

                handledAttributes.add("startIndex");

                // handle attribute "maxResults"
                java.lang.String tempAttribMaxResults = reader.getAttributeValue(null,
                        "maxResults");

                if (tempAttribMaxResults != null) {
                    java.lang.String content = tempAttribMaxResults;

                    object.setMaxResults(org.apache.axis2.databinding.utils.ConverterUtil.convertToInteger(
                            tempAttribMaxResults));
                } else {
                }

                handledAttributes.add("maxResults");

                reader.next();

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0",
                            "RequestSlotList").equals(reader.getName())) {
                    object.setRequestSlotList(org.oasis.names.tc.ebxml_regrep.xsd.rim.SlotListType.Factory.parse(
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
                            "ResponseOption").equals(reader.getName())) {
                    object.setResponseOption(org.oasis.names.tc.ebxml_regrep.xsd.query.ResponseOptionType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName(
                            "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0",
                            "AdhocQuery").equals(reader.getName())) {
                    object.setAdhocQuery(org.oasis.names.tc.ebxml_regrep.xsd.rim.AdhocQueryType.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
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
