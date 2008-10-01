/**
 * ClassificationType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:58 LKT)
 */
package org.oasis.names.tc.ebxml_regrep.xsd.rim;


/**
 *  ClassificationType bean class
 */
public class ClassificationType extends org.oasis.names.tc.ebxml_regrep.xsd.rim.RegistryObjectType
    implements org.apache.axis2.databinding.ADBBean {
    /**
     * field for ClassificationScheme
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI localClassificationScheme;

    /**
     * field for ClassifiedObject
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI localClassifiedObject;

    /**
     * field for ClassificationNode
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI localClassificationNode;

    /**
     * field for NodeRepresentation
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.LongName localNodeRepresentation;

    /* This type was generated from the piece of schema that had
       name = ClassificationType
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
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI getClassificationScheme() {
        return localClassificationScheme;
    }

    /**
     * Auto generated setter method
     * @param param ClassificationScheme
     */
    public void setClassificationScheme(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI param) {
        this.localClassificationScheme = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI getClassifiedObject() {
        return localClassifiedObject;
    }

    /**
     * Auto generated setter method
     * @param param ClassifiedObject
     */
    public void setClassifiedObject(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI param) {
        this.localClassifiedObject = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI getClassificationNode() {
        return localClassificationNode;
    }

    /**
     * Auto generated setter method
     * @param param ClassificationNode
     */
    public void setClassificationNode(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI param) {
        this.localClassificationNode = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.LongName
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.LongName getNodeRepresentation() {
        return localNodeRepresentation;
    }

    /**
     * Auto generated setter method
     * @param param NodeRepresentation
     */
    public void setNodeRepresentation(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.LongName param) {
        this.localNodeRepresentation = param;
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
                    ClassificationType.this.serialize(parentQName, factory,
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
                "type", namespacePrefix + ":ClassificationType", xmlWriter);
        } else {
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "type", "ClassificationType", xmlWriter);
        }

        if (localClassificationScheme != null) {
            writeAttribute("", "classificationScheme",
                localClassificationScheme.toString(), xmlWriter);
        }

        if (localClassifiedObject != null) {
            writeAttribute("", "classifiedObject",
                localClassifiedObject.toString(), xmlWriter);
        } else {
            throw new org.apache.axis2.databinding.ADBException(
                "required attribute localClassifiedObject is null");
        }

        if (localClassificationNode != null) {
            writeAttribute("", "classificationNode",
                localClassificationNode.toString(), xmlWriter);
        }

        if (localNodeRepresentation != null) {
            writeAttribute("", "nodeRepresentation",
                localNodeRepresentation.toString(), xmlWriter);
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
                "ClassificationType"));

        attribList.add(new javax.xml.namespace.QName("", "classificationScheme"));

        attribList.add(localClassificationScheme.toString());

        attribList.add(new javax.xml.namespace.QName("", "classifiedObject"));

        attribList.add(localClassifiedObject.toString());

        attribList.add(new javax.xml.namespace.QName("", "classificationNode"));

        attribList.add(localClassificationNode.toString());

        attribList.add(new javax.xml.namespace.QName("", "nodeRepresentation"));

        attribList.add(localNodeRepresentation.toString());

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
        public static ClassificationType parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            ClassificationType object = new ClassificationType();

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

                        if (!"ClassificationType".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (ClassificationType) org.oasis.names.tc.ebxml_regrep.xsd.rs.ExtensionMapper.getTypeObject(nsUri,
                                type, reader);
                        }
                    }
                }

                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

                // handle attribute "classificationScheme"
                java.lang.String tempAttribClassificationScheme = reader.getAttributeValue(null,
                        "classificationScheme");

                if (tempAttribClassificationScheme != null) {
                    java.lang.String content = tempAttribClassificationScheme;

                    if (tempAttribClassificationScheme.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribClassificationScheme.substring(0,
                                tempAttribClassificationScheme.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setClassificationScheme(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribClassificationScheme, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setClassificationScheme(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribClassificationScheme, ""));
                    }
                } else {
                }

                handledAttributes.add("classificationScheme");

                // handle attribute "classifiedObject"
                java.lang.String tempAttribClassifiedObject = reader.getAttributeValue(null,
                        "classifiedObject");

                if (tempAttribClassifiedObject != null) {
                    java.lang.String content = tempAttribClassifiedObject;

                    if (tempAttribClassifiedObject.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribClassifiedObject.substring(0,
                                tempAttribClassifiedObject.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setClassifiedObject(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribClassifiedObject, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setClassifiedObject(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribClassifiedObject, ""));
                    }
                } else {
                    throw new org.apache.axis2.databinding.ADBException(
                        "Required attribute classifiedObject is missing");
                }

                handledAttributes.add("classifiedObject");

                // handle attribute "classificationNode"
                java.lang.String tempAttribClassificationNode = reader.getAttributeValue(null,
                        "classificationNode");

                if (tempAttribClassificationNode != null) {
                    java.lang.String content = tempAttribClassificationNode;

                    if (tempAttribClassificationNode.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribClassificationNode.substring(0,
                                tempAttribClassificationNode.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setClassificationNode(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribClassificationNode, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setClassificationNode(org.oasis.names.tc.ebxml_regrep.xsd.rim.ReferenceURI.Factory.fromString(
                                tempAttribClassificationNode, ""));
                    }
                } else {
                }

                handledAttributes.add("classificationNode");

                // handle attribute "nodeRepresentation"
                java.lang.String tempAttribNodeRepresentation = reader.getAttributeValue(null,
                        "nodeRepresentation");

                if (tempAttribNodeRepresentation != null) {
                    java.lang.String content = tempAttribNodeRepresentation;

                    if (tempAttribNodeRepresentation.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribNodeRepresentation.substring(0,
                                tempAttribNodeRepresentation.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setNodeRepresentation(org.oasis.names.tc.ebxml_regrep.xsd.rim.LongName.Factory.fromString(
                                tempAttribNodeRepresentation, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setNodeRepresentation(org.oasis.names.tc.ebxml_regrep.xsd.rim.LongName.Factory.fromString(
                                tempAttribNodeRepresentation, ""));
                    }
                } else {
                }

                handledAttributes.add("nodeRepresentation");

                reader.next();
            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }
    } //end of factory class
}
