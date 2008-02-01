/**
 * NeOnRegistryOMVOysterMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package org.neon_toolkit.registry.omv.service.lifecyclemanager;

import org.neon_toolkit.registry.omv.service.NeOnRegistryOMVOysterSkeletonInterface;

/**
 *  NeOnRegistryOMVOysterMessageReceiverInOut message receiver
 */
public class NeOnRegistryOMVOysterMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutSyncMessageReceiver {
    public void invokeBusinessLogic(
        org.apache.axis2.context.MessageContext msgContext,
        org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault {
        try {
            // get the implementation class for the Web Service
            Object obj = getTheImplementationObject(msgContext);

            NeOnRegistryOMVOysterSkeletonInterface skel = (NeOnRegistryOMVOysterSkeletonInterface) obj;

            //Out Envelop
            org.apache.axiom.soap.SOAPEnvelope envelope = null;

            //Find the axisOperation that has been set by the Dispatch phase.
            org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext()
                                                                      .getAxisOperation();

            if (op == null) {
                throw new org.apache.axis2.AxisFault(
                    "Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
            }

            java.lang.String methodName;

            if ((op.getName() != null) &&
                    ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJava(
                            op.getName().getLocalPart())) != null)) {
                if ("approveObjects".equals(methodName)) {
                    org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse registryResponse30 =
                        null;
                    org.oasis.names.tc.ebxml_regrep.xsd.lcm.ApproveObjectsRequest wrappedParam =
                        (org.oasis.names.tc.ebxml_regrep.xsd.lcm.ApproveObjectsRequest) fromOM(msgContext.getEnvelope()
                                                                                                         .getBody()
                                                                                                         .getFirstElement(),
                            org.oasis.names.tc.ebxml_regrep.xsd.lcm.ApproveObjectsRequest.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    registryResponse30 = skel.approveObjects(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            registryResponse30, false);
                } else
                 if ("deprecateObjects".equals(methodName)) {
                    org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse registryResponse32 =
                        null;
                    org.oasis.names.tc.ebxml_regrep.xsd.lcm.DeprecateObjectsRequest wrappedParam =
                        (org.oasis.names.tc.ebxml_regrep.xsd.lcm.DeprecateObjectsRequest) fromOM(msgContext.getEnvelope()
                                                                                                           .getBody()
                                                                                                           .getFirstElement(),
                            org.oasis.names.tc.ebxml_regrep.xsd.lcm.DeprecateObjectsRequest.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    registryResponse32 = skel.deprecateObjects(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            registryResponse32, false);
                } else
                 if ("undeprecateObjects".equals(methodName)) {
                    org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse registryResponse34 =
                        null;
                    org.oasis.names.tc.ebxml_regrep.xsd.lcm.UndeprecateObjectsRequest wrappedParam =
                        (org.oasis.names.tc.ebxml_regrep.xsd.lcm.UndeprecateObjectsRequest) fromOM(msgContext.getEnvelope()
                                                                                                             .getBody()
                                                                                                             .getFirstElement(),
                            org.oasis.names.tc.ebxml_regrep.xsd.lcm.UndeprecateObjectsRequest.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    registryResponse34 = skel.undeprecateObjects(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            registryResponse34, false);
                } else
                 if ("removeObjects".equals(methodName)) {
                    org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse registryResponse36 =
                        null;
                    org.oasis.names.tc.ebxml_regrep.xsd.lcm.RemoveObjectsRequest wrappedParam =
                        (org.oasis.names.tc.ebxml_regrep.xsd.lcm.RemoveObjectsRequest) fromOM(msgContext.getEnvelope()
                                                                                                        .getBody()
                                                                                                        .getFirstElement(),
                            org.oasis.names.tc.ebxml_regrep.xsd.lcm.RemoveObjectsRequest.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    registryResponse36 = skel.removeObjects(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            registryResponse36, false);
                } else
                 if ("submitObjects".equals(methodName)) {
                    org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse registryResponse38 =
                        null;
                    org.oasis.names.tc.ebxml_regrep.xsd.lcm.SubmitObjectsRequest wrappedParam =
                        (org.oasis.names.tc.ebxml_regrep.xsd.lcm.SubmitObjectsRequest) fromOM(msgContext.getEnvelope()
                                                                                                        .getBody()
                                                                                                        .getFirstElement(),
                            org.oasis.names.tc.ebxml_regrep.xsd.lcm.SubmitObjectsRequest.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    registryResponse38 = skel.submitObjects(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            registryResponse38, false);
                } else
                 if ("updateObjects".equals(methodName)) {
                    org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse registryResponse40 =
                        null;
                    org.oasis.names.tc.ebxml_regrep.xsd.lcm.UpdateObjectsRequest wrappedParam =
                        (org.oasis.names.tc.ebxml_regrep.xsd.lcm.UpdateObjectsRequest) fromOM(msgContext.getEnvelope()
                                                                                                        .getBody()
                                                                                                        .getFirstElement(),
                            org.oasis.names.tc.ebxml_regrep.xsd.lcm.UpdateObjectsRequest.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    registryResponse40 = skel.updateObjects(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            registryResponse40, false);
                } else {
                    throw new java.lang.RuntimeException("method not found");
                }

                newMsgContext.setEnvelope(envelope);
            }
        } catch (java.lang.Exception e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    //
    private org.apache.axiom.om.OMElement toOM(
        org.oasis.names.tc.ebxml_regrep.xsd.lcm.SubmitObjectsRequest param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.oasis.names.tc.ebxml_regrep.xsd.lcm.SubmitObjectsRequest.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.oasis.names.tc.ebxml_regrep.xsd.lcm.UndeprecateObjectsRequest param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.oasis.names.tc.ebxml_regrep.xsd.lcm.UndeprecateObjectsRequest.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.oasis.names.tc.ebxml_regrep.xsd.lcm.UpdateObjectsRequest param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.oasis.names.tc.ebxml_regrep.xsd.lcm.UpdateObjectsRequest.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.oasis.names.tc.ebxml_regrep.xsd.lcm.DeprecateObjectsRequest param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.oasis.names.tc.ebxml_regrep.xsd.lcm.DeprecateObjectsRequest.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.oasis.names.tc.ebxml_regrep.xsd.lcm.ApproveObjectsRequest param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.oasis.names.tc.ebxml_regrep.xsd.lcm.ApproveObjectsRequest.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.oasis.names.tc.ebxml_regrep.xsd.lcm.RemoveObjectsRequest param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.oasis.names.tc.ebxml_regrep.xsd.lcm.RemoveObjectsRequest.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    /**
     *  get the default envelope
     */
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory) {
        return factory.getDefaultEnvelope();
    }

    private java.lang.Object fromOM(org.apache.axiom.om.OMElement param,
        java.lang.Class type, java.util.Map extraNamespaces)
        throws org.apache.axis2.AxisFault {
        try {
            if (org.oasis.names.tc.ebxml_regrep.xsd.lcm.SubmitObjectsRequest.class.equals(
                        type)) {
                return org.oasis.names.tc.ebxml_regrep.xsd.lcm.SubmitObjectsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse.class.equals(
                        type)) {
                return org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.oasis.names.tc.ebxml_regrep.xsd.lcm.UndeprecateObjectsRequest.class.equals(
                        type)) {
                return org.oasis.names.tc.ebxml_regrep.xsd.lcm.UndeprecateObjectsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse.class.equals(
                        type)) {
                return org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.oasis.names.tc.ebxml_regrep.xsd.lcm.UpdateObjectsRequest.class.equals(
                        type)) {
                return org.oasis.names.tc.ebxml_regrep.xsd.lcm.UpdateObjectsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse.class.equals(
                        type)) {
                return org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.oasis.names.tc.ebxml_regrep.xsd.lcm.DeprecateObjectsRequest.class.equals(
                        type)) {
                return org.oasis.names.tc.ebxml_regrep.xsd.lcm.DeprecateObjectsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse.class.equals(
                        type)) {
                return org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.oasis.names.tc.ebxml_regrep.xsd.lcm.ApproveObjectsRequest.class.equals(
                        type)) {
                return org.oasis.names.tc.ebxml_regrep.xsd.lcm.ApproveObjectsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse.class.equals(
                        type)) {
                return org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.oasis.names.tc.ebxml_regrep.xsd.lcm.RemoveObjectsRequest.class.equals(
                        type)) {
                return org.oasis.names.tc.ebxml_regrep.xsd.lcm.RemoveObjectsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse.class.equals(
                        type)) {
                return org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }
        } catch (java.lang.Exception e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }

        return null;
    }

    /**
     *  A utility method that copies the namepaces from the SOAPEnvelope
     */
    private java.util.Map getEnvelopeNamespaces(
        org.apache.axiom.soap.SOAPEnvelope env) {
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();

        while (namespaceIterator.hasNext()) {
            org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
            returnMap.put(ns.getPrefix(), ns.getNamespaceURI());
        }

        return returnMap;
    }

    private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();

        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }
} //end of class
