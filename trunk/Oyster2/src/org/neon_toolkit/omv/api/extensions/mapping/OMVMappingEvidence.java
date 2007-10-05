package org.neon_toolkit.omv.api.extensions.mapping;

/**
 * The class OMVMappingEvidence provides the object 
 * representation of the Evidence class of the Mapping 
 * OMV ontology extension.
 * @author Raul Palma
 * @version 0.1, May 2007
 */
public class OMVMappingEvidence {
	private String ID;
	
	public OMVMappingEvidence()
    {
    }
	
	public void append(OMVMappingEvidence element)
    {
		if (this.getID()==null && element.getID()!=null) {this.setID(element.getID());return;}
    }
	
	public void setID(String newID)
	{
		this.ID=newID;
	}
	
	public String getID()
	{
		return this.ID;
	}
	
	public static class OMVMappingArgument extends OMVMappingEvidence{
		public OMVMappingArgument()
	    {
	    }
	}
	
	public static class OMVMappingCertificate extends OMVMappingEvidence{
		public OMVMappingCertificate()
	    {
	    }
	}
	public static class OMVMappingProof extends OMVMappingEvidence{
		public OMVMappingProof()
	    {
	    }
	}
}