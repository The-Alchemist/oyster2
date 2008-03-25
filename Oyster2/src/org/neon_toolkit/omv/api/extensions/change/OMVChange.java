package org.neon_toolkit.omv.api.extensions.change;

import java.util.HashSet;
import java.util.Set;
import org.neon_toolkit.omv.api.core.OMVOntology;
import org.neon_toolkit.omv.api.core.OMVPerson;
import org.neon_toolkit.owlodm.api.Axiom;


/**
 * The class OMVChange provides the object 
 * representation of the change  
 * OMV ontology extension.
 * @author Raul Palma
 * @version 0.1, May 2007
 */
public class OMVChange {
	
	private String URI; //Special purpose just to identify object
	private String cost;
	private String date;
	private String priority;
	private String relevance;
	private String version;
	private String time;
	private String hasPreviousChange; //Automatically generated
	private OMVOntology appliedToOntology;
	private Set <OMVPerson> hasAuthor = new HashSet <OMVPerson>();
	private Set <String> causeChange = new HashSet <String>();
	
	
	public OMVChange()
	    {
	    }
	
	public void append(OMVChange element)
    {
		if (this.getURI()==null && element.getURI()!=null) {this.setURI(element.getURI());return;}
		if (this.getCost()==null && element.getCost()!=null) {this.setCost(element.getCost());return;}
		if (this.getDate()==null && element.getDate()!=null) {this.setDate(element.getDate());return;}
		if (this.getPriority()==null && element.getPriority()!=null) {this.setPriority(element.getPriority());return;}
		if (this.getRelevance()==null && element.getRelevance()!=null) {this.setRelevance(element.getRelevance());return;}
		if (this.getVersion()==null && element.getVersion()!=null) {this.setVersion(element.getVersion());return;}
		if (this.getTime()==null && element.getTime()!=null) {this.setTime(element.getTime());return;}
		if (this.getHasPreviousChange()==null && element.getHasPreviousChange()!=null) {this.setHasPreviousChange(element.getHasPreviousChange());return;}
		if (this.getAppliedToOntology()==null && element.getAppliedToOntology()!=null) {this.setAppliedToOntology(element.getAppliedToOntology());return;}
		if (element.getHasAuthor().size()>0) {this.getHasAuthor().addAll(element.getHasAuthor());return;}
		if (element.getCauseChange().size()>0) {this.getCauseChange().addAll(element.getCauseChange());return;}
    }
	
	public String getURI()
	{
		return this.URI;
	}
	public void setURI(String newURI)
	{
		this.URI=newURI;
	}
	
	public void setCost(String newCost)
	{
		this.cost=newCost;
	}
	
	public String getCost()
	{
		return this.cost;
	}
	public void setDate(String newDate)
	{
		this.date=newDate;
	}
	
	public String getDate()
	{
		return this.date;
	}
	
	public void setPriority(String newPriority)
	{
		this.priority=newPriority;
	}
	
	public String getPriority()
	{
		return this.priority;
	}
	
	public void setRelevance(String newRelevance)
	{
		this.relevance=newRelevance;
	}
	
	public String getRelevance()
	{
		return this.relevance;
	}
	
	public void setVersion(String newVersion)
	{
		this.version=newVersion;
	}
	
	public String getVersion()
	{
		return this.version;
	}
	
	public void setTime(String newTime)
	{
		this.time=newTime;
	}
	
	public String getTime()
	{
		return this.time;
	}	
	public void setAppliedToOntology (OMVOntology ontology)
	{
		this.appliedToOntology=ontology;
	}	
	public OMVOntology getAppliedToOntology()
	{
		return this.appliedToOntology;
	}
	
	public Set <OMVPerson> getHasAuthor()
	{
		return this.hasAuthor;
	}
	
	public void addHasAuthor(OMVPerson newHasAuthor)
	{
		this.hasAuthor.add(newHasAuthor);
	}
	
	public void removeHasAuthor(OMVPerson oldHasAuthor)
	{
		this.hasAuthor.remove(oldHasAuthor);
	}
	
	public String getHasPreviousChange()
	{
		return this.hasPreviousChange;
	}
	
	public void setHasPreviousChange(String newHasPreviousChange)
	{
		this.hasPreviousChange=newHasPreviousChange;
	}
	
	public Set <String> getCauseChange()
	{
		return this.causeChange;
	}
	
	public void addCauseChange(String newCauseChange)
	{
		this.causeChange.add(newCauseChange);
	}
	
	public void removeCauseChange(String oldCauseChange)
	{
		this.causeChange.remove(oldCauseChange);
	}

	public static class OMVAtomicChange extends OMVChange{
		private Axiom appliedAxiom;
		public OMVAtomicChange(){
			
		}
		public void append(OMVAtomicChange element)
	    {
			if (this.getAppliedAxiom()==null && element.getAppliedAxiom()!=null) {this.setAppliedAxiom(element.getAppliedAxiom());return;}
	    }
		
		public void setAppliedAxiom(Axiom newAppliedAxiom)
		{
			this.appliedAxiom=newAppliedAxiom;
		}
		public Axiom getAppliedAxiom()
		{
			return this.appliedAxiom;
		}
		public static class Addition extends OMVAtomicChange{
			public Addition(){
				
			}
		}
		public static class Removal extends OMVAtomicChange{
			public Removal(){
				
			}
		}
	}
	
	public static class OMVCompositeChange extends OMVChange{
		private Set <String> consistsOf = new HashSet <String>();
		
		public OMVCompositeChange(){
			
		}
		public void append(OMVCompositeChange element)
	    {
			if (element.getConsistsOf().size()>0) {this.getConsistsOf().addAll(element.getConsistsOf());return;}
	    }
		
		public Set <String> getConsistsOf()
		{
			return this.consistsOf;
		}
		
		public void addConsistsOf(String newConsistsOf)
		{
			this.consistsOf.add(newConsistsOf);
		}
		
		public void removeConsistsOf(String oldConsistsOf)
		{
			this.consistsOf.remove(oldConsistsOf);
		}

		public static class AddSubtree extends OMVCompositeChange{
			public AddSubtree(){
				
			}
		}
		public static class MergeSiblings extends OMVCompositeChange{
			public MergeSiblings(){
				
			}
		}
		public static class MoveSiblings extends OMVCompositeChange{
			public MoveSiblings(){
				
			}
			public static class MoveSiblingsUp extends MoveSiblings{
				public MoveSiblingsUp(){
					
				}
			}
			public static class MoveSiblingsDown extends MoveSiblings{
				public MoveSiblingsDown(){
					
				}
			}
		}
		public static class MoveSubtree extends OMVCompositeChange{
			public MoveSubtree(){
				
			}
		}
		public static class RemoveSubtree extends OMVCompositeChange{
			public RemoveSubtree(){
				
			}
		}
		public static class SplitClass extends OMVCompositeChange{
			public SplitClass(){
				
			}
		}
		public static class MoveClass extends OMVCompositeChange{
			public MoveClass(){
				
			}
		}
	}

	public static class OMVEntityChange extends OMVChange{
		
		private String hasEntityState;
		private Set<String> hasRelatedEntity=new HashSet<String>();
		private Set<String> consistsOfAtomicOperation = new HashSet <String>();
		
		public OMVEntityChange(){
			
		}
		
		public void append(OMVEntityChange element)
	    {
			if (this.getHasEntityState()==null && element.getHasEntityState()!=null) {this.setHasEntityState(element.getHasEntityState());return;}
			if (element.getHasRelatedEntity().size()>0) {this.getHasRelatedEntity().addAll(element.getHasRelatedEntity());return;}
			if (element.getConsistsOfAtomicOperation().size()>0) {this.getConsistsOfAtomicOperation().addAll(element.getConsistsOfAtomicOperation());return;}	
	    }
		
		
		public void setHasEntityState(String newHasEntityState)
		{
			this.hasEntityState=newHasEntityState;
		}
		
		public String getHasEntityState()
		{
			return this.hasEntityState;
		}
		
		public Set<String> getHasRelatedEntity()
		{
			return this.hasRelatedEntity;
		}
		
		public void addHasRelatedEntity(String newHasRelatedEntity)
		{
			this.hasRelatedEntity.add(newHasRelatedEntity);
		}
		
		public void removeHasRelatedEntity(String oldHasRelatedEntity)
		{
			this.hasRelatedEntity.remove(oldHasRelatedEntity);
		}
		
		public Set <String> getConsistsOfAtomicOperation()
		{
			return this.consistsOfAtomicOperation;
		}
		
		public void addConsistsOfAtomicOperation(String newConsistsOfAtomicOperation)
		{
			this.consistsOfAtomicOperation.add(newConsistsOfAtomicOperation);
		}
		
		public void removeConsistsOfAtomicOperation(String oldConsistsOfAtomicOperation)
		{
			this.consistsOfAtomicOperation.remove(oldConsistsOfAtomicOperation);
		}

		public static class AnnotationPropertyChange extends OMVEntityChange{
			public AnnotationPropertyChange(){
				
			}
			public static class CommentChange extends AnnotationPropertyChange{
				public CommentChange(){
					
				}
				public static class AddComment extends CommentChange{
					public AddComment(){
						
					}
				}
				public static class RemoveComment extends CommentChange{
					public RemoveComment(){
						
					}
				}
			}
		
			public static class LabelChange extends AnnotationPropertyChange{
				public LabelChange(){
					
				}
				public static class AddLabel extends LabelChange{
					public AddLabel(){
						
					}
				}
				public static class RemoveLabel extends LabelChange{
					public RemoveLabel(){
						
					}
				}
			}
		}
	
		public static class ClassChange extends OMVEntityChange{
			public ClassChange(){
				
			}
			public static class SubClassOfChange extends ClassChange{
				public SubClassOfChange(){
					
				}
				public static class AddSubClassOf extends SubClassOfChange{
					public AddSubClassOf(){
						
					}
				}
				public static class RemoveSubClassOf extends SubClassOfChange{
					public RemoveSubClassOf(){
						
					}
				}
			}
		}
		
		public static class IndividualChange extends OMVEntityChange{
			public IndividualChange(){
				
			}
		}
		
		public static class OntologyChange extends OMVEntityChange{
			public OntologyChange(){
				
			}
			public static class AddClass extends OntologyChange{
				public AddClass(){
					
				}
			}
			public static class RemoveClass extends OntologyChange{
				public RemoveClass(){
					
				}
			}
			public static class AddIndividual extends OntologyChange{
				public AddIndividual(){
					
				}
			}
			public static class RemoveIndividual extends OntologyChange{
				public RemoveIndividual(){
					
				}
			}
			public static class AddProperty extends OntologyChange{
				public AddProperty(){
					
				}
			}
			public static class RemoveProperty extends OntologyChange{
				public RemoveProperty(){
					
				}
			}
		}
		
		public static class PropertyChange extends OMVEntityChange{
			public PropertyChange(){
				
			}
			public static class DomainChange extends PropertyChange{
				public DomainChange(){
					
				}
				public static class AddDomain extends DomainChange{
					public AddDomain(){
						
					}
				}
				public static class RemoveDomain extends DomainChange{
					public RemoveDomain(){
						
					}
				}
			}
			
			public static class RangeChange extends PropertyChange{
				public RangeChange(){
					
				}
				public static class AddRange extends RangeChange{
					public AddRange(){
						
					}
				}
				public static class RemoveRange extends RangeChange{
					public RemoveRange(){
						
					}
				}
			}
		
			public static class SubPropertyOfChange extends PropertyChange{
				public SubPropertyOfChange(){
					
				}
				public static class AddSubPropertyOf extends SubPropertyOfChange{
					public AddSubPropertyOf(){
						
					}
				}
				public static class RemoveSubPropertyOf extends SubPropertyOfChange{
					public RemoveSubPropertyOf(){
						
					}
				}
			}
		}
	
	}
}
