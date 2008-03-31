package org.neon_toolkit.omv.api.extensions.OWLchange;

import org.neon_toolkit.omv.api.extensions.change.OMVChange;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.OntologyChange.AddProperty;
import org.neon_toolkit.omv.api.extensions.change.OMVChange.OMVEntityChange.OntologyChange.RemoveProperty;

/**
 * The class OMVOWLChange provides the object 
 * representation of the OWL Change  
 * OMV ontology extension.
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class OMVOWLChange extends OMVChange{
	public OMVOWLChange()
    {
		
    }
	public static class OMVOWLEntityChange extends OMVEntityChange{		
		public OMVOWLEntityChange()
	    {
			
	    }		
		public static class OWLClassChange extends OMVOWLEntityChange{
			public OWLClassChange()
		    {
			
		    }		
			public static class EquivalentClassChange extends OWLClassChange{
				public EquivalentClassChange(){
			
				}
				public static class AddEquivalentClass extends EquivalentClassChange{
					public AddEquivalentClass(){
						
					}
				}
				public static class RemoveEquivalentClass extends EquivalentClassChange{
					public RemoveEquivalentClass(){
						
					}
				}
			}
			public static class DisjointClassChange extends OWLClassChange{
				public DisjointClassChange(){
					
				}
				public static class AddDisjointClass extends DisjointClassChange{
					public AddDisjointClass(){
						
					}
				}
				public static class RemoveDisjointClass extends DisjointClassChange{
					public RemoveDisjointClass(){
						
					}
				}
			}
			public static class DisjointUnionChange extends OWLClassChange{
				public DisjointUnionChange(){
					
				}
				public static class AddDisjointUnion extends DisjointUnionChange{
					public AddDisjointUnion(){
						
					}
				}
				public static class RemoveDisjointUnion extends DisjointUnionChange{
					public RemoveDisjointUnion(){
						
					}
				}
			}
		}
		public static class OWLIndividualChange extends OMVOWLEntityChange{
			public OWLIndividualChange()
		    {
			
		    }
			public static class SameIndividualChange extends OWLIndividualChange{
				public SameIndividualChange(){
					
				}
				public static class AddSameIndividual extends SameIndividualChange{
					public AddSameIndividual(){
						
					}
				}
				public static class RemoveSameIndividual extends SameIndividualChange{
					public RemoveSameIndividual(){
						
					}
				}
			}
			public static class IndividualDataPropertyChange extends OWLIndividualChange{
				public IndividualDataPropertyChange(){
					
				}
				public static class AddIndividualDataProperty extends IndividualDataPropertyChange{
					public AddIndividualDataProperty(){
						
					}
				}
				public static class RemoveIndividualDataProperty extends IndividualDataPropertyChange{
					public RemoveIndividualDataProperty(){
						
					}
				}
			}
			public static class IndividualObjectPropertyChange extends OWLIndividualChange{
				public IndividualObjectPropertyChange(){
					
				}
				public static class AddInvidivualObjectProperty extends IndividualObjectPropertyChange{
					public AddInvidivualObjectProperty(){
						
					}
				}
				public static class RemoveInvidualObjectProperty extends IndividualObjectPropertyChange{
					public RemoveInvidualObjectProperty(){
						
					}
				}
			}
			public static class DifferentIndividualChange extends OWLIndividualChange{
				public DifferentIndividualChange(){
					
				}
				public static class AddDifferentIndividual extends DifferentIndividualChange{
					public AddDifferentIndividual(){
						
					}
				}
				public static class RemoveDifferentIndividual extends DifferentIndividualChange{
					public RemoveDifferentIndividual(){
						
					}
				}
			}
		}
		public static class OWLOntologyChange extends OMVOWLEntityChange{
			public OWLOntologyChange(){
				
			}
			public static class AddDatatype extends OWLOntologyChange{
				public AddDatatype(){
					
				}
				
			}
			public static class RemoveDatatype extends OWLOntologyChange{
				public RemoveDatatype(){
					
				}
				
			}
			public static class AddObjectProperty extends AddProperty{
				public AddObjectProperty(){
					
				}
			}
			public static class AddDataProperty extends AddProperty{
				public AddDataProperty(){
					
				}
			}
			public static class RemoveDataProperty extends RemoveProperty{
				public RemoveDataProperty(){
					
				}
			}
			public static class RemoveObjectProperty extends RemoveProperty{
				public RemoveObjectProperty(){
					
				}
			}
		}
		public static class OWLObjectPropertyChange extends OMVOWLEntityChange{
			public OWLObjectPropertyChange(){
				
			}
			public static class AsymmetricObjectPropertyChange extends OWLObjectPropertyChange{
				public AsymmetricObjectPropertyChange(){
					
				}
				public static class AddAsymmetricObjectProperty extends AsymmetricObjectPropertyChange{
					public AddAsymmetricObjectProperty(){
						
					}
				}
				public static class RemoveAsymmetricObjectProperty extends AsymmetricObjectPropertyChange{
					public RemoveAsymmetricObjectProperty(){
						
					}
				}
			}
			public static class DisjointObjectPropertyChange extends OWLObjectPropertyChange{
				public DisjointObjectPropertyChange(){
					
				}
				public static class AddDisjointObjectProperty extends DisjointObjectPropertyChange{
					public AddDisjointObjectProperty(){
						
					}
				}
				public static class RemoveDisjointObjectProperty extends DisjointObjectPropertyChange{
					public RemoveDisjointObjectProperty(){
						
					}
				}
			}
			public static class EquivalentObjectPropertyChange extends OWLObjectPropertyChange{
				public EquivalentObjectPropertyChange(){
					
				}
				public static class AddEquivalentObjectProperty extends EquivalentObjectPropertyChange{
					public AddEquivalentObjectProperty(){
						
					}
				}
				public static class RemoveEquivalentObjectProperty extends EquivalentObjectPropertyChange{
					public RemoveEquivalentObjectProperty(){
						
					}
				}
			}
			public static class FunctionalObjectPropertyChange extends OWLObjectPropertyChange{
				public FunctionalObjectPropertyChange(){
					
				}
				public static class AddFunctionalObjectProperty extends FunctionalObjectPropertyChange{
					public AddFunctionalObjectProperty(){
						
					}
				}
				public static class RemoveFunctionalObjectProperty extends FunctionalObjectPropertyChange{
					public RemoveFunctionalObjectProperty(){
						
					}
				}
			}
			public static class InverseFunctionalObjectPropertyChange extends OWLObjectPropertyChange{
				public InverseFunctionalObjectPropertyChange(){
					
				}
				public static class AddInverseFunctionalObjectProperty extends InverseFunctionalObjectPropertyChange{
					public AddInverseFunctionalObjectProperty(){
						
					}
				}
				public static class RemoveInverseFunctionalObjectProperty extends InverseFunctionalObjectPropertyChange{
					public RemoveInverseFunctionalObjectProperty(){
						
					}
				}
			}
			public static class InverseObjectPropertyChange extends OWLObjectPropertyChange{
				public InverseObjectPropertyChange(){
					
				}
				public static class AddInverseObjectProperty extends InverseObjectPropertyChange{
					public AddInverseObjectProperty(){
						
					}
				}
				public static class RemoveInverseObjectProperty extends InverseObjectPropertyChange{
					public RemoveInverseObjectProperty(){
						
					}
				}
			}
			public static class IrreflexiveObjectPropertyChange extends OWLObjectPropertyChange{
				public IrreflexiveObjectPropertyChange(){
					
				}
				public static class AddIrreflexiveObjectProperty extends IrreflexiveObjectPropertyChange{
					public AddIrreflexiveObjectProperty(){
						
					}
				}
				public static class RemoveIrreflexiveObjectProperty extends IrreflexiveObjectPropertyChange{
					public RemoveIrreflexiveObjectProperty(){
						
					}
				}
			}
			public static class ObjectPropertyDomainChange extends OWLObjectPropertyChange{
				public ObjectPropertyDomainChange(){
					
				}
				public static class AddObjectPropertyDomain extends ObjectPropertyDomainChange{
					public AddObjectPropertyDomain(){
						
					}
				}
				public static class RemoveObjectPropertyDomain extends ObjectPropertyDomainChange{
					public RemoveObjectPropertyDomain(){
						
					}
				}
			}
			public static class ObjectPropertyRangeChange extends OWLObjectPropertyChange{
				public ObjectPropertyRangeChange(){
					
				}
				public static class AddObjectPropertyRange extends ObjectPropertyRangeChange{
					public AddObjectPropertyRange(){
						
					}
				}
				public static class RemoveObjectPropertyRange extends ObjectPropertyRangeChange{
					public RemoveObjectPropertyRange(){
						
					}
				}
			}
			public static class ReflexiveObjectPropertyChange extends OWLObjectPropertyChange{
				public ReflexiveObjectPropertyChange(){
					
				}
				public static class AddReflexiveObjectProperty extends ReflexiveObjectPropertyChange{
					public AddReflexiveObjectProperty(){
						
					}
				}
				public static class RemoveReflexiveObjectProperty extends ReflexiveObjectPropertyChange{
					public RemoveReflexiveObjectProperty(){
						
					}
				}
			}
			public static class SubObjectPropertyOfChange extends OWLObjectPropertyChange{
				public SubObjectPropertyOfChange(){
					
				}
				public static class AddSubObjectPropertyOf extends SubObjectPropertyOfChange{
					public AddSubObjectPropertyOf(){
						
					}
				}
				public static class RemoveSubObjectPropertyOf extends SubObjectPropertyOfChange{
					public RemoveSubObjectPropertyOf(){
						
					}
				}
			}
			public static class SymmetricObjectPropertyChange extends OWLObjectPropertyChange{
				public SymmetricObjectPropertyChange(){
					
				}
				public static class AddSymmetricObjectProperty extends SymmetricObjectPropertyChange{
					public AddSymmetricObjectProperty(){
						
					}
				}
				public static class RemoveSymmetricObjectProperty extends SymmetricObjectPropertyChange{
					public RemoveSymmetricObjectProperty(){
						
					}
				}
			}
			public static class TransitiveObjectPropertyChange extends OWLObjectPropertyChange{
				public TransitiveObjectPropertyChange(){
					
				}
				public static class AddTransitiveObjectProperty extends TransitiveObjectPropertyChange{
					public AddTransitiveObjectProperty(){
						
					}
				}
				public static class RemoveTransitiveObjectProperty extends TransitiveObjectPropertyChange{
					public RemoveTransitiveObjectProperty(){
						
					}
				}
			}
		}
		public static class OWLDataPropertyChange extends OMVOWLEntityChange{
			public OWLDataPropertyChange(){
				
			}
			public static class DataPropertyDomainChange extends OWLDataPropertyChange{
				public DataPropertyDomainChange(){
					
				}
				public static class AddDataPropertyDomain extends DataPropertyDomainChange{
					public AddDataPropertyDomain(){
						
					}
				}
				public static class RemoveDataPropertyDomain extends DataPropertyDomainChange{
					public RemoveDataPropertyDomain(){
						
					}
				}
			}
			public static class DataPropertyRangeChange extends OWLDataPropertyChange{
				public DataPropertyRangeChange(){
					
				}
				public static class AddDataPropertyRange extends DataPropertyRangeChange{
					public AddDataPropertyRange(){
						
					}
				}
				public static class RemoveDataPropertyRange extends DataPropertyRangeChange{
					public RemoveDataPropertyRange(){
						
					}
				}
			}
			public static class DisjointDataPropertyChange extends OWLDataPropertyChange{
				public DisjointDataPropertyChange(){
					
				}
				public static class AddDisjointDataProperty extends DisjointDataPropertyChange{
					public AddDisjointDataProperty(){
						
					}
				}
				public static class RemoveDisjointDataProperty extends DisjointDataPropertyChange{
					public RemoveDisjointDataProperty(){
						
					}
				}
			}
			public static class EquivalentDataPropertyChange extends OWLDataPropertyChange{
				public EquivalentDataPropertyChange(){
					
				}
				public static class AddEquivalentDataProperty extends EquivalentDataPropertyChange{
					public AddEquivalentDataProperty(){
						
					}
				}
				public static class RemoveEquivalentDataProperty extends EquivalentDataPropertyChange{
					public RemoveEquivalentDataProperty(){
						
					}
				}
			}
			public static class FunctionalDataPropertyChange extends OWLDataPropertyChange{
				public FunctionalDataPropertyChange(){
					
				}
				public static class AddFunctionalDataProperty extends FunctionalDataPropertyChange{
					public AddFunctionalDataProperty(){
						
					}
				}
				public static class RemoveFunctionalDataProperty extends FunctionalDataPropertyChange{
					public RemoveFunctionalDataProperty(){
						
					}
				}
			}
			public static class SubDataPropertyOfChange extends OWLDataPropertyChange{
				public SubDataPropertyOfChange(){
					
				}
				public static class AddSubDataPropertyOf extends SubDataPropertyOfChange{
					public AddSubDataPropertyOf(){
						
					}
				}
				public static class RemoveSubDataPropertyOf extends SubDataPropertyOfChange{
					public RemoveSubDataPropertyOf(){
						
					}
				}
			}
		}
	}	

}
