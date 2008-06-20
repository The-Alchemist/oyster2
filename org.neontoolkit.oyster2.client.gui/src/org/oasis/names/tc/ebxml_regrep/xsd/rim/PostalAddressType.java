/**
 * PostalAddressType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:58 LKT)
 */
package org.oasis.names.tc.ebxml_regrep.xsd.rim;


/**
 *  PostalAddressType bean class
 */
public class PostalAddressType implements org.apache.axis2.databinding.ADBBean {
    /**
     * field for City
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName localCity;

    /**
     * field for Country
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName localCountry;

    /**
     * field for PostalCode
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName localPostalCode;

    /**
     * field for StateOrProvince
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName localStateOrProvince;

    /**
     * field for Street
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName localStreet;

    /**
     * field for StreetNumber
     * This was an Attribute!
     */
    protected org.oasis.names.tc.ebxml_regrep.xsd.rim.String32 localStreetNumber;

    /* This type was generated from the piece of schema that had
       name = PostalAddressType
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
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName getCity() {
        return localCity;
    }

    /**
     * Auto generated setter method
     * @param param City
     */
    public void setCity(org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName param) {
        this.localCity = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName getCountry() {
        return localCountry;
    }

    /**
     * Auto generated setter method
     * @param param Country
     */
    public void setCountry(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName param) {
        this.localCountry = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName getPostalCode() {
        return localPostalCode;
    }

    /**
     * Auto generated setter method
     * @param param PostalCode
     */
    public void setPostalCode(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName param) {
        this.localPostalCode = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName getStateOrProvince() {
        return localStateOrProvince;
    }

    /**
     * Auto generated setter method
     * @param param StateOrProvince
     */
    public void setStateOrProvince(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName param) {
        this.localStateOrProvince = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName getStreet() {
        return localStreet;
    }

    /**
     * Auto generated setter method
     * @param param Street
     */
    public void setStreet(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName param) {
        this.localStreet = param;
    }

    /**
     * Auto generated getter method
     * @return org.oasis.names.tc.ebxml_regrep.xsd.rim.String32
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rim.String32 getStreetNumber() {
        return localStreetNumber;
    }

    /**
     * Auto generated setter method
     * @param param StreetNumber
     */
    public void setStreetNumber(
        org.oasis.names.tc.ebxml_regrep.xsd.rim.String32 param) {
        this.localStreetNumber = param;
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
                    PostalAddressType.this.serialize(parentQName, factory,
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

        if (localCity != null) {
            writeAttribute("", "city", localCity.toString(), xmlWriter);
        }

        if (localCountry != null) {
            writeAttribute("", "country", localCountry.toString(), xmlWriter);
        }

        if (localPostalCode != null) {
            writeAttribute("", "postalCode", localPostalCode.toString(),
                xmlWriter);
        }

        if (localStateOrProvince != null) {
            writeAttribute("", "stateOrProvince",
                localStateOrProvince.toString(), xmlWriter);
        }

        if (localStreet != null) {
            writeAttribute("", "street", localStreet.toString(), xmlWriter);
        }

        if (localStreetNumber != null) {
            writeAttribute("", "streetNumber", localStreetNumber.toString(),
                xmlWriter);
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

        attribList.add(new javax.xml.namespace.QName("", "city"));

        attribList.add(localCity.toString());

        attribList.add(new javax.xml.namespace.QName("", "country"));

        attribList.add(localCountry.toString());

        attribList.add(new javax.xml.namespace.QName("", "postalCode"));

        attribList.add(localPostalCode.toString());

        attribList.add(new javax.xml.namespace.QName("", "stateOrProvince"));

        attribList.add(localStateOrProvince.toString());

        attribList.add(new javax.xml.namespace.QName("", "street"));

        attribList.add(localStreet.toString());

        attribList.add(new javax.xml.namespace.QName("", "streetNumber"));

        attribList.add(localStreetNumber.toString());

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
        public static PostalAddressType parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            PostalAddressType object = new PostalAddressType();

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

                        if (!"PostalAddressType".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (PostalAddressType) org.oasis.names.tc.ebxml_regrep.xsd.rs.ExtensionMapper.getTypeObject(nsUri,
                                type, reader);
                        }
                    }
                }

                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

                // handle attribute "city"
                java.lang.String tempAttribCity = reader.getAttributeValue(null,
                        "city");

                if (tempAttribCity != null) {
                    java.lang.String content = tempAttribCity;

                    if (tempAttribCity.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribCity.substring(0,
                                tempAttribCity.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setCity(org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName.Factory.fromString(
                                tempAttribCity, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setCity(org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName.Factory.fromString(
                                tempAttribCity, ""));
                    }
                } else {
                }

                handledAttributes.add("city");

                // handle attribute "country"
                java.lang.String tempAttribCountry = reader.getAttributeValue(null,
                        "country");

                if (tempAttribCountry != null) {
                    java.lang.String content = tempAttribCountry;

                    if (tempAttribCountry.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribCountry.substring(0,
                                tempAttribCountry.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setCountry(org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName.Factory.fromString(
                                tempAttribCountry, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setCountry(org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName.Factory.fromString(
                                tempAttribCountry, ""));
                    }
                } else {
                }

                handledAttributes.add("country");

                // handle attribute "postalCode"
                java.lang.String tempAttribPostalCode = reader.getAttributeValue(null,
                        "postalCode");

                if (tempAttribPostalCode != null) {
                    java.lang.String content = tempAttribPostalCode;

                    if (tempAttribPostalCode.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribPostalCode.substring(0,
                                tempAttribPostalCode.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setPostalCode(org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName.Factory.fromString(
                                tempAttribPostalCode, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setPostalCode(org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName.Factory.fromString(
                                tempAttribPostalCode, ""));
                    }
                } else {
                }

                handledAttributes.add("postalCode");

                // handle attribute "stateOrProvince"
                java.lang.String tempAttribStateOrProvince = reader.getAttributeValue(null,
                        "stateOrProvince");

                if (tempAttribStateOrProvince != null) {
                    java.lang.String content = tempAttribStateOrProvince;

                    if (tempAttribStateOrProvince.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribStateOrProvince.substring(0,
                                tempAttribStateOrProvince.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setStateOrProvince(org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName.Factory.fromString(
                                tempAttribStateOrProvince, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setStateOrProvince(org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName.Factory.fromString(
                                tempAttribStateOrProvince, ""));
                    }
                } else {
                }

                handledAttributes.add("stateOrProvince");

                // handle attribute "street"
                java.lang.String tempAttribStreet = reader.getAttributeValue(null,
                        "street");

                if (tempAttribStreet != null) {
                    java.lang.String content = tempAttribStreet;

                    if (tempAttribStreet.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribStreet.substring(0,
                                tempAttribStreet.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setStreet(org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName.Factory.fromString(
                                tempAttribStreet, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setStreet(org.oasis.names.tc.ebxml_regrep.xsd.rim.ShortName.Factory.fromString(
                                tempAttribStreet, ""));
                    }
                } else {
                }

                handledAttributes.add("street");

                // handle attribute "streetNumber"
                java.lang.String tempAttribStreetNumber = reader.getAttributeValue(null,
                        "streetNumber");

                if (tempAttribStreetNumber != null) {
                    java.lang.String content = tempAttribStreetNumber;

                    if (tempAttribStreetNumber.indexOf(":") > 0) {
                        // this seems to be a Qname so find the namespace and send
                        prefix = tempAttribStreetNumber.substring(0,
                                tempAttribStreetNumber.indexOf(":"));
                        namespaceuri = reader.getNamespaceURI(prefix);
                        object.setStreetNumber(org.oasis.names.tc.ebxml_regrep.xsd.rim.String32.Factory.fromString(
                                tempAttribStreetNumber, namespaceuri));
                    } else {
                        // this seems to be not a qname send and empty namespace incase of it is
                        // check is done in fromString method
                        object.setStreetNumber(org.oasis.names.tc.ebxml_regrep.xsd.rim.String32.Factory.fromString(
                                tempAttribStreetNumber, ""));
                    }
                } else {
                }

                handledAttributes.add("streetNumber");

                reader.next();
            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }
    } //end of factory class
}
