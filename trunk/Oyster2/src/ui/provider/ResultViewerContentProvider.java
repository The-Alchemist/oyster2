package ui.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import java.util.*;

import oyster2.*;
import core.*;
import util.*;
import util.Utilities;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.semanticweb.kaon2.api.*;
import org.semanticweb.kaon2.api.owl.elements.*;
import org.semanticweb.kaon2.api.owl.elements.Individual;



public class ResultViewerContentProvider implements ITreeContentProvider{
	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	private Collection children = new ArrayList();
	private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private AdvertInformer mInformer = mOyster2.getLocalAdvertInformer();
	//private Ontology ontology = mInformer.getLocalRegistry();
	private Ontology ontology = mOyster2.getLocalHostOntology();
	private int resourceType;
	public ResultViewerContentProvider(int resourceType){
		this.resourceType = resourceType;
	}
	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof Entity){
			children = new ArrayList();
			//System.out.println("this is ontologyResource in ContentProvider!");
			if(this.resourceType==Resource.OntologyResource){
				try{
					//System.out.println("this is ontologyResource in ContentProvider!");
					Individual ontologyIndiv = (Individual)parentElement;
					Collection propertySet = getReplyPropertySet(ontology,ontologyIndiv);
					Iterator it = propertySet.iterator();
					while(it.hasNext()){
						Property propertyIndiv = (Property)it.next();
						String pred = propertyIndiv.getPred();
						Object value = propertyIndiv.getValue();
						if(Namespaces.guessLocalName(pred).equals("ontologyLocation"))
							children.add((Entity)value);
						else if(Namespaces.guessLocalName(pred).equals("IPAdress"))
							children.add((String)value);
					}
					return children.toArray();
				}catch(Exception e){
					System.err.println(e.toString()+"  getChildren()in ResultViewerContentProvider,Resourcetype: OntologyResource");	
				}
			}
			else if(this.resourceType==Resource.DataResource){
				children = new ArrayList();
				try{
//					System.out.println("this is ontologyResource in ContentProvider!");
					Individual dataIndiv = (Individual)parentElement;
					//Collection propertySet = getReplyPropertySet(mKaonP2P.getVirtualOntology(),dataIndiv);
					Collection propertySet = getReplyPropertySet(mOyster2.getTopicOntology(),dataIndiv);
					Iterator it = propertySet.iterator();
					while(it.hasNext()){
						Property propertyIndiv = (Property)it.next();
						String pred = propertyIndiv.getPred();
						Object value = propertyIndiv.getValue();
						if((Namespaces.guessLocalName(pred).equals("author"))||((Namespaces.guessLocalName(pred).equals("documentAuthor"))))
						children.add((Entity)value);
					}
				return children.toArray();
				}catch(Exception e){
					System.err.println(e.toString()+"getChildren()in ResultViewerContentProvider,Resourcetype: DataResource");	
				}
			}
			else if(this.resourceType == Resource.RegistryResource){
				children = new ArrayList();
				try{
					/*System.out.println("this is ontologyResource in ContentProvider!");
					Individual dataIndiv = (Individual)parentElement;
					Collection propertySet = getReplyPropertySet(mKaonP2P.getVirtualOntology(),dataIndiv);
					Iterator it = propertySet.iterator();
					while(it.hasNext()){
						Property propertyIndiv = (Property)it.next();
						String pred = propertyIndiv.getPred();
						Object value = propertyIndiv.getValue();
						if((Namespaces.guessLocalName(pred).equals("author")))
						children.add((Entity)value);
					}*/
				return children.toArray();
				}catch(Exception e){
					System.err.println(e.toString()+"getChildren()in ResultViewerContentProvider,Resourcetype: DataResource");	
				}
				
			}
			else System.err.println("ResourceType error in ResultViewerContentProvider");
			
			
		}else if(parentElement instanceof Ontology){
			children = new ArrayList();
			//System.out.println("this is ontology Type in ContentProvider!");
			Ontology ontologyParent = (Ontology)parentElement;
			children = getOntologyElements(ontologyParent);
			return children.toArray();
		}
		else if(parentElement instanceof String){
			children = new ArrayList();
			children.add((String)parentElement);
			return children.toArray();
		}
		
		return new Object[0];
	}
	private Collection getReplyPropertySet(Ontology virtualOntology,Individual docIndiv){
		Collection propertySet = new ArrayList();
		Property replyProperty;
		try{
		Map dataPropertyMap = docIndiv.getDataPropertyValues(virtualOntology);
		Map objectPropertyMap = docIndiv.getObjectPropertyValues(virtualOntology);
		Collection keySet = dataPropertyMap.keySet();
		Iterator keys = keySet.iterator();
		while(keys.hasNext()){
			String keyStr = keys.next().toString();
			DataProperty property = KAON2Manager.factory().dataProperty(keyStr);
			String propertyValue = util.Utilities.getString(docIndiv.getDataPropertyValue(virtualOntology,property));
			replyProperty = new Property(keyStr,propertyValue);
			propertySet.add(replyProperty);
			//System.out.println("    "+Namespaces.guessLocalName(keyStr)+": "+propertyValue);	
		}
		
		keySet = objectPropertyMap.keySet();
		Iterator okeys = keySet.iterator();
		while(okeys.hasNext()){
			String keyStr = okeys.next().toString();
			ObjectProperty property = KAON2Manager.factory().objectProperty(keyStr);
			Collection propertyCol = (Collection)objectPropertyMap.get(property);
			Iterator it = propertyCol.iterator();
			while(it.hasNext()){
				Entity propertyValue = (Entity)it.next();
				replyProperty = new Property(keyStr,propertyValue);
				propertySet.add(replyProperty);
				//System.out.println("    "+Namespaces.guessLocalName(keyStr)+": "+propertyValue);	
			}
			
		}
		}catch(Exception e){
			System.err.println(e.toString()+" :getReplyPropertySet() in ResultViewerContentProvider.");
		}
		return propertySet;
	}
	private Collection getOntologyElements(Ontology ontology){
		//System.out.println("getOntology Element");
		Collection elements = new ArrayList();
		Collection importSet = new ArrayList();
		if(!ontology.getOntologyURI().equals("http://localhost/virtualOntology")){
		elements.addAll(mInformer.getOntologyDocument(ontology));
		elements.addAll(mInformer.getMappingSet(ontology));
		elements.addAll(mInformer.getPeerList(ontology));
		}
		else{//ontology is virtual ontology
		
		 importSet = ontology.getImportedOntologies();
			if(importSet!=null){
				for(Iterator it = importSet.iterator();it.hasNext();){
					String uri = ((Ontology)it.next()).getOntologyURI();
					if(!uri.equalsIgnoreCase(Constants.LocalRegistryURI))
					elements.add("imports "+uri);
					//Individual ontologyIndiv = KAON2Manager.factory().individual(uri);
					//elements.add(ontologyIndiv);
				}
			}
		}
		return elements;
		
	}
	/*public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof Entity){
			children = new ArrayList();
			
			if(this.resourceType==Resource.OntologyResource){
				try{
					//System.out.println("this is ontologyResource in ContentProvider!");
					Individual ontologyIndiv = (Individual)parentElement;
					Collection peerIndivSet = mInformer.getOntologyProvider(ontology,ontologyIndiv);
					DataProperty IPAdress = KAON2Manager.factory().dataProperty(mInformer.getBaseRegistryURI()+"#IPAdress");
					Iterator it = peerIndivSet.iterator();
					while(it.hasNext()){
						Individual peerIndiv = (Individual)it.next();
						String ipAdr =(String) peerIndiv.getDataPropertyValue(ontology,IPAdress);
						String peerObject = Namespaces.guessLocalName(peerIndiv.getURI())+": "+ipAdr;
						children.add(peerObject);
					}
					return children.toArray();
				}catch(Exception e){
					System.err.println(e.toString()+"  getChildren()in ResultViewerContentProvider,Resourcetype: OntologyResource");	
				}
		}
			else if(this.resourceType==Resource.DataResource){
				children = new ArrayList();
				try{
					//System.out.println("this is dataResource in ContentProvider!");
				Individual dataIndiv = (Individual)parentElement;
				ObjectProperty author = KAON2Manager.factory().objectProperty(mKaonP2P.getContextOntology().getOntologyURI()+"#author");
				DataProperty year = KAON2Manager.factory().dataProperty(mKaonP2P.getContextOntology().getOntologyURI()+"#year");
				System.out.println(mKaonP2P.getContextOntology().getOntologyURI()+"#author");
				String authorStr =dataIndiv.getObjectPropertyValue(mKaonP2P.getVirtualOntology(),author).getURI();
				String yearStr =dataIndiv.getDataPropertyValue(mKaonP2P.getVirtualOntology(),year).toString();
				children.add("author: "+authorStr);
				children.add("year:   "+yearStr);
				return children.toArray();
				}catch(KAON2Exception e){
					System.err.println(e.toString()+"getChildren()in ResultViewerContentProvider,Resourcetype: DataResource");	
				}
			}
			else System.err.println("ResourceType error in ResultViewerContentProvider");
			
			
		}
		
		return new Object[0];
	}*/
	
	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	public Object getParent(Object element) {
		return null;
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	public boolean hasChildren(Object element){
		if(this.resourceType!=Resource.RegistryResource){
			if(element instanceof String){
				return false;
			}
			else return true;
		}else{
			if((element instanceof Entity)|| (element instanceof String)){
				return false;
			}
			else return true;
			
		}
	}

	/**
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		Object[] resources=((java.util.List)inputElement).toArray();
		/*for(int i = 0;i<resources.length;i++){
			//if(resources[i] instanceof Entity)
			//System.out.println("doc: "+resources[i]);
		}*/
		return resources;
	}

	/**
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {

	}

	/**
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}
}


