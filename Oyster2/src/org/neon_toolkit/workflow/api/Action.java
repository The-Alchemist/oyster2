package org.neon_toolkit.workflow.api;

import org.neon_toolkit.omv.api.core.OMVOntology;
import org.neon_toolkit.omv.api.core.OMVPerson;

/**
 * The class Action provides the object 
 * representation of the Workflow Ontology.
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class Action {
	String URI;
	private String timestamp;
	private OMVPerson performedBy;
	
	public Action()
	    {
	    }
	
	public void append(Action element)
    {
		if (this.getURI()==null && element.getURI()!=null) {this.setURI(element.getURI());return;}
		if (this.getTimestamp()==null && element.getTimestamp()!=null) {this.setTimestamp(element.getTimestamp());return;}
		if (this.getPerformedBy()==null && element.getPerformedBy()!=null) {this.setPerformedBy(element.getPerformedBy());return;}
    }
	
	public void setURI(String newURI)
	{
		this.URI=newURI;
	}
	
	public String getURI()
	{
		return this.URI;
	}
	
	public void setTimestamp(String newTimeStamp)
	{
		this.timestamp=newTimeStamp;
	}
	
	public String getTimestamp()
	{
		return this.timestamp;
	}
	
	public OMVPerson getPerformedBy()
	{
		return this.performedBy;
	}
	
	public void setPerformedBy(OMVPerson newPerformedBy)
	{
		this.performedBy=newPerformedBy;
	}
	
	public static class EntityAction extends Action{
		
		String relatedChange;
		public EntityAction()
	    {
	    }
		
		public void append(EntityAction element)
	    {
			if (this.getRelatedChange()==null && element.getRelatedChange()!=null) {this.setRelatedChange(element.getRelatedChange());return;}
	    }

		
		public void setRelatedChange(String newRelatedChange)
		{
			this.relatedChange=newRelatedChange;
		}
		
		public String getRelatedChange()
		{
			return this.relatedChange;
		}

		public static class Delete extends EntityAction{
			public Delete()
		    {
		    }
		}
		public static class Insert extends EntityAction{
			public Insert()
		    {
		    }
		}
		public static class RejectToApproved extends EntityAction{
			public RejectToApproved()
		    {
		    }
		}
		public static class RejectToBeApproved extends EntityAction{
			public RejectToBeApproved()
		    {
		    }
		}
		public static class RejectToDraft extends EntityAction{
			public RejectToDraft()
		    {
		    }
		}
		public static class SendToApproved extends EntityAction{
			public SendToApproved()
		    {
		    }
		}
		public static class SendToBeApproved extends EntityAction{
			public SendToBeApproved()
		    {
		    }
		}
		public static class SendToBeDeleted extends EntityAction{
			public SendToBeDeleted()
		    {
		    }
		}
		public static class Update extends EntityAction{
			public Update()
		    {
		    }
		}
	}
	public static class OntologyAction extends Action{
		OMVOntology relatedOntology;
		public OntologyAction(){
			
		}
		
		public void append(OntologyAction element)
	    {
			if (this.getRelatedOntology()==null && element.getRelatedOntology()!=null) {this.setRelatedOntology(element.getRelatedOntology());return;}
	    }
		
		public void setRelatedOntology(OMVOntology newRelatedOntology)
		{
			this.relatedOntology=newRelatedOntology;
		}
		
		public OMVOntology getRelatedOntology()
		{
			return this.relatedOntology;
		}

		public static class MoveToDraft extends OntologyAction{
			public MoveToDraft()
		    {
		    }
		}
		public static class MoveToBeApproved extends OntologyAction{
			public MoveToBeApproved()
		    {
		    }
		}
		public static class Approval extends OntologyAction{
			public Approval()
		    {
		    }
		}
		public static class Publish extends OntologyAction{
			OMVOntology fromPublicVersion;
			OMVOntology toPublicVersion;
			public Publish(){
				
			}
			
			public void append(Publish element)
		    {
				if (this.getFromPublicVersion()==null && element.getFromPublicVersion()!=null) {this.setFromPublicVersion(element.getFromPublicVersion());return;}
				if (this.getToPublicVersion()==null && element.getToPublicVersion()!=null) {this.setToPublicVersion(element.getToPublicVersion());return;}
		    }
			
			public void setFromPublicVersion(OMVOntology newFromPublicVersion)
			{
				this.fromPublicVersion=newFromPublicVersion;
			}			
			public OMVOntology getFromPublicVersion()
			{
				return this.fromPublicVersion;
			}
			public void setToPublicVersion(OMVOntology newToPublicVersion)
			{
				this.toPublicVersion=newToPublicVersion;
			}	
			public OMVOntology getToPublicVersion()
			{
				return this.toPublicVersion;
			}

		}
	}
}
