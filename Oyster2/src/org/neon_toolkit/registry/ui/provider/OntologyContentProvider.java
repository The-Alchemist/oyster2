package org.neon_toolkit.registry.ui.provider;

import java.util.*;

/*
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;
*/
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.semanticweb.kaon2.api.*;
import org.semanticweb.kaon2.api.owl.elements.*;




public class OntologyContentProvider implements ITreeContentProvider {
	
	
	private Ontology ontology;
	private Entity rootEntity;
	boolean isTypeOntology;
	//private String typeNameSpace;
	//private String topicNameSpace;

	public OntologyContentProvider(Ontology ontology,boolean isTypeOntology,Entity rootEntity){
		this.rootEntity = rootEntity;
		this.ontology = ontology;
		this.isTypeOntology = isTypeOntology;//indicate if this is a typeOntology(swrc) or a topicOntology(acm);
	}
	
	public Object[] getChildren(Object element) {
		Vector<OWLEntity> items = new Vector<OWLEntity>();
		Collection children = new ArrayList();
		try{
			if(isTypeOntology){
					children =((OWLClass) element).getSubDescriptions(ontology);
					Iterator iter = children.iterator();
					while(iter.hasNext()) {
						OWLClass item = (OWLClass)iter.next();
						String itemLocalName = Namespaces.guessLocalName(item.getURI()); 
						//typeNameSpace = Namespaces.guessNamespace(item.getURI());
						items.add(item);
					}
			}
			else {  System.out.println("element: "+((Entity)element).getURI());
					children = getSubTopics(ontology,(Entity)element);
					items.addAll(children);
				}
			
		}catch(KAON2Exception e){
			System.err.println(e.toString()+"at OntologyContentProvider getChildren()");
		}
		
		return items.toArray();
	}

	public Object[] getElements(Object element) {
		
		//just input the rootClass of the ontology;
		try{
			if(element instanceof Ontology){
				//System.out.println("element instanceof Ontology");
				return new Object[]{(Entity)rootEntity};
			}
			else if (element instanceof Entity){
				return new Object[]{(Entity)element};
			}
		}catch(Exception oe){
			//--just don't show ontology, so return empty array
		}
		return new Object[0];
	}

	public boolean hasChildren(Object element) {
		if(element != null){
			try{
				
				if(isTypeOntology)
					return ((OWLClass)element).getSubDescriptions(ontology).size()>0;
				else if(!isTypeOntology)
					return getSubTopics(ontology,(Entity)element).size()>0;
				else {
					System.err.println("element Type error in hasChildren at OntologyContentProvider");
				}
			}catch(KAON2Exception e){
			System.err.println(e.toString()+"in OntologyContentProvider:hasChildren");
			}
			return false;
		}
		else return false;
	}

	public Object getParent(Object element) {
		return null;
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object old_input, Object new_input) {
	}
	
public Collection getSubTopics(Ontology ontology,Entity topicEntry){
		Collection subTopics = new ArrayList();
		Individual topicIndiv = KAON2Manager.factory().individual(topicEntry.getURI());
		//String topicURI = topicIndiv.getURI();
		ObjectProperty subTopic = KAON2Manager.factory().objectProperty(ontology.getOntologyURI()+"SubTopic");
		Map propertyMap = new HashMap();
		try{
		 propertyMap = topicIndiv.getObjectPropertyValues(ontology);
		}catch(KAON2Exception e){
			System.err.println(e.toString()+":OntologyContentProvider getSubTopics()");
		}
		Collection subIndivs =(Collection) propertyMap.get(subTopic);
		if(subIndivs!=null){
		Iterator subs = subIndivs.iterator();
		while(subs.hasNext()){
			Individual subTopicIndividual = KAON2Manager.factory().individual(subs.next().toString());
			subTopics.add(subTopicIndividual);
		}
		}
		return subTopics;
	}
}

