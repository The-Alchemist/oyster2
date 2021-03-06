/**
 * TelephoneNumberType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:58 LKT)
 */
package org.oasis.names.tc.ebxml_regrep.xsd.rim;


/**
 *  TelephoneNumberType bean class
 */
public class TelephoneNumberType implements org.apache.axis2.databinding.ADBBean {
    /**
     * field for AreaCode
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.String8 localAreaCode;

    /**
     * field for CountryCode
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.String8 localCountryCode;

    /**
     * field for Extension
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.String8 localExtension;

    /**
     * field for Number
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.String16 localNumber;

    /**
     * field for PhoneType
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.String32 localPhoneType;

    /* This type was generated from the piece of schema that had
       name = TelephoneNumberType
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
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.String8
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.String8 getAreaCode() {
        return localAreaCode;
    }

    /**
     * Auto generated setter method
     * @param param AreaCode
     */
    public void setAreaCode(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.String8 param) {
        this.localAreaCode = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.String8
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.String8 getCountryCode() {
        return localCountryCode;
    }

    /**
     * Auto generated setter method
     * @param param CountryCode
     */
    public void setCountryCode(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.String8 param) {
        this.localCountryCode = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.String8
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.String8 getExtension() {
        return localExtension;
    }

    /**
     * Auto generated setter method
     * @param param Extension
     */
    public void setExtension(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.String8 param) {
        this.localExtension = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.String16
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.String16 getNumber() {
        return localNumber;
    }

    /**
     * Auto generated setter method
     * @param param Number
     */
    public void setNumber(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.String16 param) {
        this.localNumber = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.String32
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.String32 getPhoneType() {
        return localPhoneType;
    }

    /**
     * Auto generated setter method
     * @param param PhoneType
     */
    public void setPhoneType(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.String32 param) {
        this.localPhoneType = param;
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
                    TelephoneNumberType.this.serialize(parentQName, factory,
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

        if (localAreaCode != null) {
            writeAttribute("", "areaCode", localAreaCode.toString(), xmlWriter);
        }

        if (localCountryCode != null) {
            writeAttribute("", "countryCode", localCountryCode.toString(),
                xmlWriter);
        }

        if (localExtension != null) {
            writeAttribute("", "extension", localExtension.toString(), xmlWriter);
        }

        if (localNumber != null) {
            writeAttribute("", "number", localNumber.toString(), xmlWriter);
        }

        if (localPhoneType != null) {
            writeAttribute("", "phoneType", localPhoneType.toString(), xmlWriter);
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

        attribList.add(new javax.xml.namespace.QName("", "areaCode"));

        attribList.add(localAreaCode.toString());

        attribList.add(new javax.xml.namespace.QName("", "countryCode"));

        attribList.add(localCountryCode.toString());

        attribList.add(new javax.xml.namespace.QName("", "extension"));

        attribList.add(localExtension.toString());

        attribList.add(new javax.xml.namespace.QName("", "number"));

        attribList.add(localNumber.toString());

        attribList.add(new javax.xml.namespace.QName("", "phoneType"));

        attribList.add(localPhoneType.toString());

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
        public static TelephoneNumberType parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            TelephoneNumberType object = new TelephoneNumberType();

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

                        if (!"TelephoneNumberType".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (TelephoneNumberType) org.oasis.names.tc.ebxml_regrep.xsd.rs.ExtensionMapper.getTypeObject(nsUri,
                                type, reader);
                        }
                    }
                }

                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

                // handle attribute "areaCode"
                java.lang.String tempAttribAreaCode = reader.getAttributeValue(null,
                        "areaCode");

                if (tempAttribAreaCode != null) {
                    java.lang.String content = tempAttribAreaCode;

                    if (tempAttribAreaCode.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribAreaCode.substring(0,
                                tempAttribAreaCode.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setAreaCode(org.oasis.names.tc.ebxml_regrep.xsd.rim.String8.Factory.fromString(
                                tempAttribAreaCode, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setAreaCode(org.oasis.names.tc.ebxml_regrep.xsd.rim.String8.Factory.fromString(
                                tempAttribAreaCode, ""));
                    }
                } else {
                }

                handledAttributes.add("areaCode");

                // handle attribute "countryCode"
                java.lang.String tempAttribCountryCode = reader.getAttributeValue(null,
                        "countryCode");

                if (tempAttribCountryCode != null) {
                    java.lang.String content = tempAttribCountryCode;

                    if (tempAttribCountryCode.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribCountryCode.substring(0,
                                tempAttribCountryCode.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setCountryCode(org.oasis.names.tc.ebxml_regrep.xsd.rim.String8.Factory.fromString(
                                tempAttribCountryCode, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setCountryCode(org.oasis.names.tc.ebxml_regrep.xsd.rim.String8.Factory.fromString(
                                tempAttribCountryCode, ""));
                    }
                } else {
                }

                handledAttributes.add("countryCode");

                // handle attribute "extension"
                java.lang.String tempAttribExtension = reader.getAttributeValue(null,
                        "extension");

                if (tempAttribExtension != null) {
                    java.lang.String content = tempAttribExtension;

                    if (tempAttribExtension.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribExtension.substring(0,
                                tempAttribExtension.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setExtension(org.oasis.names.tc.ebxml_regrep.xsd.rim.String8.Factory.fromString(
                                tempAttribExtension, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setExtension(org.oasis.names.tc.ebxml_regrep.xsd.rim.String8.Factory.fromString(
                                tempAttribExtension, ""));
                    }
                } else {
                }

                handledAttributes.add("extension");

                // handle attribute "number"
                java.lang.String tempAttribNumber = reader.getAttributeValue(null,
                        "number");

                if (tempAttribNumber != null) {
                    java.lang.String content = tempAttribNumber;

                    if (tempAttribNumber.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribNumber.substring(0,
                                tempAttribNumber.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setNumber(org.oasis.names.tc.ebxml_regrep.xsd.rim.String16.Factory.fromString(
                                tempAttribNumber, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setNumber(org.oasis.names.tc.ebxml_regrep.xsd.rim.String16.Factory.fromString(
                                tempAttribNumber, ""));
                    }
                } else {
                }

                handledAttributes.add("number");

                // handle attribute "phoneType"
                java.lang.String tempAttribPhoneType = reader.getAttributeValue(null,
                        "phoneType");

                if (tempAttribPhoneType != null) {
                    java.lang.String content = tempAttribPhoneType;

                    if (tempAttribPhoneType.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribPhoneType.substring(0,
                                tempAttribPhoneType.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setPhoneType(org.oasis.names.tc.ebxml_regrep.xsd.rim.String32.Factory.fromString(
                                tempAttribPhoneType, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setPhoneType(org.oasis.names.tc.ebxml_regrep.xsd.rim.String32.Factory.fromString(
                                tempAttribPhoneType, ""));
                    }
                } else {
                }

                handledAttributes.add("phoneType");

                reader.next();
            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }
    } //end of factory class
}
