/**
 * NeOnRegistryOMVOysterSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package org.neon_toolkit.registry.omv.service;

/**
 *  NeOnRegistryOMVOysterSkeletonInterface java skeleton interface for the axisService
 */
public interface NeOnRegistryOMVOysterSkeletonInterface {
    /**
     * Auto generated method signature
     * @param adhocQueryRequest
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.query.AdhocQueryResponse submitAdhocQuery(
        org.oasis.names.tc.ebxml_regrep.xsd.query.AdhocQueryRequest adhocQueryRequest);
    /**
     * Auto generated method signature
     * @param approveObjectsRequest
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse approveObjects(
        org.oasis.names.tc.ebxml_regrep.xsd.lcm.ApproveObjectsRequest approveObjectsRequest);

    /**
     * Auto generated method signature
     * @param deprecateObjectsRequest
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse deprecateObjects(
        org.oasis.names.tc.ebxml_regrep.xsd.lcm.DeprecateObjectsRequest deprecateObjectsRequest);

    /**
     * Auto generated method signature
     * @param undeprecateObjectsRequest
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse undeprecateObjects(
        org.oasis.names.tc.ebxml_regrep.xsd.lcm.UndeprecateObjectsRequest undeprecateObjectsRequest);

    /**
     * Auto generated method signature
     * @param removeObjectsRequest
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse removeObjects(
        org.oasis.names.tc.ebxml_regrep.xsd.lcm.RemoveObjectsRequest removeObjectsRequest);

    /**
     * Auto generated method signature
     * @param submitObjectsRequest
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse submitObjects(
        org.oasis.names.tc.ebxml_regrep.xsd.lcm.SubmitObjectsRequest submitObjectsRequest);

    /**
     * Auto generated method signature
     * @param updateObjectsRequest
     */
    public org.oasis.names.tc.ebxml_regrep.xsd.rs.RegistryResponse updateObjects(
        org.oasis.names.tc.ebxml_regrep.xsd.lcm.UpdateObjectsRequest updateObjectsRequest);

}
