package org.neontoolkit.registry.util;
/*****************************************************************************
 * Copyright (c) 2008 ontoprise GmbH.
 *
 * All rights reserved.
 *
 *****************************************************************************/

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import org.semanticweb.kaon2.api.Entity;
import org.semanticweb.kaon2.api.KAON2Visitor;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.KAON2Connection;
import org.semanticweb.kaon2.api.flogic.FMethodCall;
import org.semanticweb.kaon2.api.flogic.FMolecule;
import org.semanticweb.kaon2.api.logic.ClassicalNegation;
import org.semanticweb.kaon2.api.logic.Conjunction;
import org.semanticweb.kaon2.api.logic.Constant;
import org.semanticweb.kaon2.api.logic.DefaultNegation;
import org.semanticweb.kaon2.api.logic.Disjunction;
import org.semanticweb.kaon2.api.logic.Equivalence;
import org.semanticweb.kaon2.api.logic.Exists;
import org.semanticweb.kaon2.api.logic.Forall;
import org.semanticweb.kaon2.api.logic.FunctionalTerm;
import org.semanticweb.kaon2.api.logic.Implication;
import org.semanticweb.kaon2.api.logic.Literal;
import org.semanticweb.kaon2.api.logic.PredicateSymbol;
import org.semanticweb.kaon2.api.logic.QueryDefinition;
import org.semanticweb.kaon2.api.logic.Rule;
import org.semanticweb.kaon2.api.logic.Variable;
import org.semanticweb.kaon2.api.owl.axioms.ClassMember;
import org.semanticweb.kaon2.api.owl.axioms.DataPropertyAttribute;
import org.semanticweb.kaon2.api.owl.axioms.DataPropertyDomain;
import org.semanticweb.kaon2.api.owl.axioms.DataPropertyMember;
import org.semanticweb.kaon2.api.owl.axioms.DataPropertyRange;
import org.semanticweb.kaon2.api.owl.axioms.DifferentIndividuals;
import org.semanticweb.kaon2.api.owl.axioms.DisjointClasses;
import org.semanticweb.kaon2.api.owl.axioms.EquivalentClasses;
import org.semanticweb.kaon2.api.owl.axioms.EquivalentDataProperties;
import org.semanticweb.kaon2.api.owl.axioms.EquivalentObjectProperties;
import org.semanticweb.kaon2.api.owl.axioms.InverseObjectProperties;
import org.semanticweb.kaon2.api.owl.axioms.Annotation;
import org.semanticweb.kaon2.api.owl.axioms.Deprecation;
import org.semanticweb.kaon2.api.owl.axioms.ObjectPropertyAttribute;
import org.semanticweb.kaon2.api.owl.axioms.ObjectPropertyDomain;
import org.semanticweb.kaon2.api.owl.axioms.ObjectPropertyMember;
import org.semanticweb.kaon2.api.owl.axioms.ObjectPropertyRange;
import org.semanticweb.kaon2.api.owl.axioms.SameIndividual;
import org.semanticweb.kaon2.api.owl.axioms.SubClassOf;
import org.semanticweb.kaon2.api.owl.axioms.SubDataPropertyOf;
import org.semanticweb.kaon2.api.owl.axioms.SubObjectPropertyOf;
import org.semanticweb.kaon2.api.owl.elements.AnnotationProperty;
import org.semanticweb.kaon2.api.owl.elements.DataAll;
import org.semanticweb.kaon2.api.owl.elements.DataCardinality;
import org.semanticweb.kaon2.api.owl.elements.DataHasValue;
import org.semanticweb.kaon2.api.owl.elements.DataNot;
import org.semanticweb.kaon2.api.owl.elements.DataOneOf;
import org.semanticweb.kaon2.api.owl.elements.DataProperty;
import org.semanticweb.kaon2.api.owl.elements.DataSome;
import org.semanticweb.kaon2.api.owl.elements.Datatype;
import org.semanticweb.kaon2.api.owl.elements.Description;
import org.semanticweb.kaon2.api.owl.elements.Individual;
import org.semanticweb.kaon2.api.owl.elements.OWLClass;
import org.semanticweb.kaon2.api.owl.elements.OWLEntity;
import org.semanticweb.kaon2.api.owl.elements.ObjectAll;
import org.semanticweb.kaon2.api.owl.elements.ObjectAnd;
import org.semanticweb.kaon2.api.owl.elements.ObjectCardinality;
import org.semanticweb.kaon2.api.owl.elements.ObjectHasValue;
import org.semanticweb.kaon2.api.owl.elements.ObjectNot;
import org.semanticweb.kaon2.api.owl.elements.ObjectOneOf;
import org.semanticweb.kaon2.api.owl.elements.ObjectOr;
import org.semanticweb.kaon2.api.owl.elements.ObjectProperty;
import org.semanticweb.kaon2.api.owl.elements.ObjectSome;
//import org.semanticweb.kaon2.api.owl.axioms.Declaration;
//import org.semanticweb.kaon2.api.owl.axioms.DisjointDataProperties;
//import org.semanticweb.kaon2.api.owl.axioms.DisjointObjectProperties;
//import org.semanticweb.kaon2.api.owl.axioms.DisjointUnion;
//import org.semanticweb.kaon2.api.owl.axioms.EntityAnnotation;
//import org.semanticweb.kaon2.api.owl.axioms.NegativeDataPropertyMember;
//import org.semanticweb.kaon2.api.owl.axioms.NegativeObjectPropertyMember;
//import org.semanticweb.kaon2.api.owl.elements.AnnotationByConstant;
//import org.semanticweb.kaon2.api.owl.elements.AnnotationByIndividual;
//import org.semanticweb.kaon2.api.owl.elements.DataPropertyExpression;
//import org.semanticweb.kaon2.api.owl.elements.DatatypeRestriction;
//import org.semanticweb.kaon2.api.owl.elements.InverseObjectProperty;
//import org.semanticweb.kaon2.api.owl.elements.ObjectExistsSelf;
//import org.semanticweb.kaon2.api.owl.elements.ObjectPropertyExpression;


/**
 * Tests if an <code>OWLAxiom</code> contains at least one of a given set of <code>OWLEntity</code>'s.<br/>
 * <br/>
 * @author krekeler
 *
 */
public class ContainsOWLEntityVisitor implements KAON2Visitor {
    /** The entities to look for. */
    protected Collection<OWLEntity> _entities;
    
    /**
     * 
     * @param entities The entities to look for. Must not be <code>null</code>. Should provide a fast answer to <code>contains</code>.
     */
    public ContainsOWLEntityVisitor(Collection<OWLEntity> entities) {
        _entities = entities;
    }

    public ContainsOWLEntityVisitor(OWLEntity entity) {
        _entities = Collections.singleton(entity);
    }

    public ContainsOWLEntityVisitor(OWLEntity... entities) {
        _entities = new HashSet<OWLEntity>(Arrays.asList(entities));
    }
    
    public Object visit(Individual object) {
        return _entities.contains(object);
    }
    public Object visit(DataProperty object) {
        return _entities.contains(object);
    }
    public Object visit(ObjectProperty object) {
        return _entities.contains(object);
    }
    public Object visit(Datatype object) {
        return _entities.contains(object);
    }
    public Object visit(OWLClass object) {
        return _entities.contains(object);
    }
    public Object visit(AnnotationProperty object) {
        return _entities.contains(object);
    }

    /*
    protected boolean visitDataPropertyExpressions(Collection<DataPropertyExpression> items) {
        if (items == null) {
            return false;
        }
        for (DataPropertyExpression item: items) {
            if ((Boolean)item.accept(this)) {
                return true;
            }
        }
        return false;
    }
    protected boolean visitObjectPropertyExpressions(Collection<ObjectPropertyExpression> items) {
        if (items == null) {
            return false;
        }
        for (ObjectPropertyExpression item: items) {
            if ((Boolean)item.accept(this)) {
                return true;
            }
        }
        return false;
    }
    */
    protected boolean visitEntities(Collection<Entity> items) {
        if (items == null) {
            return false;
        }
        for (Entity item: items) {
            if ((Boolean)item.accept(this)) {
                return true;
            }
        }
        return false;
    }
    protected boolean visitDescriptions(Collection<Description> items) {
        if (items == null) {
            return false;
        }
        for (Description item: items) {
            if ((Boolean)item.accept(this)) {
                return true;
            }
        }
        return false;
    }

    public Object visit(PredicateSymbol object) {
        assert(false);
        throw new IllegalArgumentException();
    }

    

    public Object visit(QueryDefinition object) {
        assert(false);
        throw new IllegalArgumentException();
    }

    public Object visit(Variable object) {
        assert(false);
        throw new IllegalArgumentException();
    }

    public Object visit(Constant object) {
        return false;
    }

    public Object visit(FunctionalTerm object) {
        assert(false);
        throw new IllegalArgumentException();
    }

    public Object visit(Literal object) {
        assert(false);
        throw new IllegalArgumentException();
    }

    public Object visit(Rule object) {
        assert(false);
        throw new IllegalArgumentException();
    }

    public Object visit(Conjunction object) {
        assert(false);
        throw new IllegalArgumentException();
    }

    public Object visit(Disjunction object) {
        assert(false);
        throw new IllegalArgumentException();
    }

    public Object visit(ClassicalNegation object) {
        assert(false);
        throw new IllegalArgumentException();
    }

    public Object visit(DefaultNegation object) {
        assert(false);
        throw new IllegalArgumentException();
    }

    public Object visit(Forall object) {
        assert(false);
        throw new IllegalArgumentException();
    }

    public Object visit(Exists object) {
        assert(false);
        throw new IllegalArgumentException();
    }

    public Object visit(Implication object) {
        assert(false);
        throw new IllegalArgumentException();
    }

    public Object visit(Equivalence object) {
        assert(false);
        throw new IllegalArgumentException();
    }

    public Object visit(FMolecule object) {
        assert(false);
        throw new IllegalArgumentException();
    }

    public Object visit(FMethodCall object) {
        assert(false);
        throw new IllegalArgumentException();
    }

    public Object visit(DataNot object) {
        return object.getDataRange().accept(this);
    }

    public Object visit(DataOneOf object) {
        return false;
    }

    

    public Object visit(DataAll object) {
        return ((Boolean)object.getDataRange().accept(this));// || visitDataPropertyExpressions(object.getDataProperties());
    }

    public Object visit(DataSome object) {
        return ((Boolean)object.getDataRange().accept(this));// || visitDataPropertyExpressions(object.getDataProperties());
    }

    public Object visit(DataCardinality object) {
        return (Boolean)object.getDataProperty().accept(this);// || ((Boolean)object.getDataRange().accept(this));
    }

    public Object visit(DataHasValue object) {
        return object.getDataProperty().accept(this);
    }

    public Object visit(ObjectAll object) {
        return ((Boolean)object.getDescription().accept(this)) || (Boolean)object.getObjectProperty().accept(this);
    }

    public Object visit(ObjectSome object) {
        return ((Boolean)object.getDescription().accept(this)) || (Boolean)object.getObjectProperty().accept(this);
    }

    

    public Object visit(ObjectCardinality object) {
        return ((Boolean)object.getDescription().accept(this)) || (Boolean)object.getObjectProperty().accept(this);
    }

    
    public Object visit(ObjectOneOf object) {
        //Collection<Entity> entities = Cast.cast(object.getIndividuals());
    	Collection<Entity> entities=new HashSet<Entity>();
    	for (Individual ax: object.getIndividuals()){
    		entities.add(ax);
    	}
        return visitEntities(entities);
    }

    public Object visit(ObjectHasValue object) {
        return (Boolean)object.getObjectProperty().accept(this) || (Boolean)object.getIndividual().accept(this);
    }

    public Object visit(ObjectNot object) {
        return object.getDescription().accept(this);
    }

    public Object visit(ObjectOr object) {
        return visitDescriptions(object.getDescriptions());
    }

    public Object visit(ObjectAnd object) {
        return visitDescriptions(object.getDescriptions());
    }

    public Object visit(SubClassOf object) {
        return (Boolean)object.getSubDescription().accept(this) || (Boolean)object.getSuperDescription().accept(this);
    }

    public Object visit(EquivalentClasses object) {
        return visitDescriptions(object.getDescriptions());
    }

    public Object visit(DisjointClasses object) {
        return visitDescriptions(object.getDescriptions());
    }

    

    public Object visit(DataPropertyAttribute object) {
        return object.getDataProperty().accept(this);
    }

    public Object visit(DataPropertyDomain object) {
        return (Boolean)object.getDataProperty().accept(this) || (Boolean)object.getDomain().accept(this);
    }

    public Object visit(DataPropertyRange object) {
        return (Boolean)object.getDataProperty().accept(this) || (Boolean)object.getRange().accept(this);
    }

    public Object visit(SubDataPropertyOf object) {
        return (Boolean)object.getSubDataProperty().accept(this) || (Boolean)object.getSuperDataProperty().accept(this);
    }

    public Object visit(EquivalentDataProperties object) {
        return null;//visitDataPropertyExpressions(object.getDataProperties());
    }

    public Object visit(ObjectPropertyAttribute object) {
        return object.getObjectProperty().accept(this);
    }

    public Object visit(ObjectPropertyDomain object) {
        return (Boolean)object.getObjectProperty().accept(this) || (Boolean)object.getDomain().accept(this);
    }

    public Object visit(ObjectPropertyRange object) {
        return (Boolean)object.getObjectProperty().accept(this) || (Boolean)object.getRange().accept(this);
    }

    public Object visit(SubObjectPropertyOf object) {
        return (Boolean)object.getSuperObjectProperty().accept(this);// || visitObjectPropertyExpressions(object.getSubObjectProperties());
    }

    public Object visit(EquivalentObjectProperties object) {
        return null;//visitObjectPropertyExpressions(object.getObjectProperties());
    }

    

    public Object visit(InverseObjectProperties object) {
        return (Boolean)object.getFirst().accept(this) || (Boolean)object.getSecond().accept(this);
    }

    
    public Object visit(SameIndividual object) {
        //Collection<Entity> entities = Cast.cast(object.getIndividuals());
    	Collection<Entity> entities=new HashSet<Entity>();
    	for (Individual ax: object.getIndividuals()){
    		entities.add(ax);
    	}
        return visitEntities(entities);
    }

    public Object visit(DifferentIndividuals object) {
        //Collection<Entity> entities = Cast.cast(object.getIndividuals());
    	Collection<Entity> entities=new HashSet<Entity>();
    	for (Individual ax: object.getIndividuals()){
    		entities.add(ax);
    	}
        return visitEntities(entities);
    }

    public Object visit(DataPropertyMember object) {
        return (Boolean)object.getDataProperty().accept(this) || (Boolean)object.getSourceIndividual().accept(this);
    }

   

    public Object visit(ObjectPropertyMember object) {
        return (Boolean)object.getObjectProperty().accept(this) || (Boolean)object.getSourceIndividual().accept(this) || (Boolean)object.getTargetIndividual().accept(this);
    }

   
    public Object visit(ClassMember object) {
        return (Boolean)object.getDescription().accept(this) || (Boolean)object.getIndividual().accept(this);
    }

    public Object visit(Ontology object) {
        assert(false);
        throw new IllegalArgumentException();
    }

    public Object visit(KAON2Connection object) {
        assert(false);
        throw new IllegalArgumentException();
    }

	public Object visit(Annotation arg0) {
		return null;
	}

	public Object visit(Deprecation arg0) {
		return null;
	}

}