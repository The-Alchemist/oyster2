/**
 * StringFilterType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:58 LKT)
 */
package org.oasis.names.tc.ebxml_regrep.xsd.query;


/**
 *  StringFilterType bean class
 */
public class StringFilterType extends org.oasis.names.tc.ebxml_regrep.xsd.query.SimpleFilterType
    implements org.apache.axis2.databinding.ADBBean {
    /**
     * field for Value
     * This was an Attribute!
     */
    protected java.lang.String localValue;

    /* This type was generated from the piece of schema that had
       name = StringFilterType
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
     * @return java.lang.String
     */
    public java.lang.String getValue() {
        return localValue;
    }

    /**
     * Auto generated setter method
     * @param param Value
     */
    public void setValue(java.lang.String param) {
        this.localValue = param;
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
                    StringFilterType.this.serialize(parentQName, factory,
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
                "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0");

        if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "type", namespacePrefix + ":StringFilterType", xmlWriter);
        } else {
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "type", "StringFilterType", xmlWriter);
        }

        if (true) {
            writeAttribute("", "negate",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localNegate), xmlWriter);
        }

        if (localDomainAttribute != null) {
            writeAttribute("", "domainAttribute",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localDomainAttribute), xmlWriter);
        }

        if (localComparator != null) {
            writeAttribute("", "comparator", localComparator.toString(),
                xmlWriter);
        } else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localComparator is null");
        }

        if (localValue != null) {
            writeAttribute("", "value",
                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localValue), xmlWriter);
        } else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localValue is null");
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
                "StringFilterType"));

        attribList.add(new javax.xml.namespace.QName("", "negate"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localNegate));

        attribList.add(new javax.xml.namespace.QName("", "domainAttribute"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localDomainAttribute));

        attribList.add(new javax.xml.namespace.QName("", "comparator"));

        attribList.add(localComparator.toString());

        attribList.add(new javax.xml.namespace.QName("", "value"));

        attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localValue));

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
        public static StringFilterType parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            StringFilterType object = new StringFilterType();

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

                        if (!"StringFilterType".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (StringFilterType) org.oasis.names.tc.ebxml_regrep.xsd.rs.ExtensionMapper.getTypeObject(nsUri,
                                type, reader);
                        }
                    }
                }

                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

                // handle attribute "negate"
                java.lang.String tempAttribNegate = reader.getAttributeValue(null,
                        "negate");

                if (tempAttribNegate != null) {
                    java.lang.String content = tempAttribNegate;

                    object.setNegate(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
                            tempAttribNegate));
                } else {
                }

                handledAttributes.add("negate");

                // handle attribute "domainAttribute"
                java.lang.String tempAttribDomainAttribute = reader.getAttributeValue(null,
                        "domainAttribute");

                if (tempAttribDomainAttribute != null) {
                    java.lang.String content = tempAttribDomainAttribute;

                    object.setDomainAttribute(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribDomainAttribute));
                } else {
                }

                handledAttributes.add("domainAttribute");

                // handle attribute "comparator"
                java.lang.String tempAttribComparator = reader.getAttributeValue(null,
                        "comparator");

                if (tempAttribComparator != null) {
                    java.lang.String content = tempAttribComparator;

                    if (tempAttribComparator.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribComparator.substring(0,
                                tempAttribComparator.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setComparator(org.oasis.names.tc.ebxml_regrep.xsd.query.ComparatorType.Factory.fromString(
                                tempAttribComparator, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setComparator(org.oasis.names.tc.ebxml_regrep.xsd.query.ComparatorType.Factory.fromString(
                                tempAttribComparator, ""));
                    }
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute comparator is missing");
                }

                handledAttributes.add("comparator");

                // handle attribute "value"
                java.lang.String tempAttribValue = reader.getAttributeValue(null,
                        "value");

                if (tempAttribValue != null) {
                    java.lang.String content = tempAttribValue;

                    object.setValue(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            tempAttribValue));
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute value is missing");
                }

                handledAttributes.add("value");

                reader.next();
            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }
    } //end of factory class
}
