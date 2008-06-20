
package org.neon_toolkit.registry.ui.provider;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.neon_toolkit.registry.oyster2.Oyster2Factory;
//import org.neon_toolkit.registry.ui.ResultViewer;
import org.neon_toolkit.registry.util.Resource;
import org.semanticweb.kaon2.api.Entity;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.Namespaces;
import org.semanticweb.kaon2.api.owl.elements.Individual;
import org.semanticweb.kaon2.api.owl.elements.OWLClass;

public class OntologyLabelProvider implements ILabelProvider{
	private TreeViewer viewer; 
	private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private int resourceType;
	public OntologyLabelProvider(){
		super();
		this.resourceType = -1;
	}
	public OntologyLabelProvider(int resourceType) {
		super();
		this.resourceType = resourceType;
		
	}
	public Image getImage(Object arg0) {
		return null;
	}

	public String getText(Object entryIndiv) {
		try {
			//this.viewer.getTree().get
			StringBuffer text = new StringBuffer();
			if((entryIndiv instanceof Entity)&&(this.resourceType!=Resource.RegistryResource))
			try {
				if(Namespaces.guessLocalName(entryIndiv.toString()).length()>0)
					text.append(Namespaces.guessLocalName(entryIndiv.toString()));
				else
					text.append(entryIndiv.toString());
			} catch (Exception e) {
				text.append(e.toString()+Namespaces.guessLocalName(entryIndiv.toString()));
			}
			else if((entryIndiv instanceof Entity)&&(this.resourceType==Resource.RegistryResource)){
				try{
					OWLClass typeClass =(OWLClass) (((Individual) entryIndiv).getDescriptionsMemberOf(mOyster2.getLocalHostOntology()).toArray())[0];
					text.append(Namespaces.guessLocalName(typeClass.getURI())+" : ");
					//text.append(Namespaces.guessLocalName(entryIndiv.toString()));
					text.append((entryIndiv.toString()));
					
					
				}catch(Exception e){
					
				}
			}
			if(entryIndiv instanceof Ontology){
				try {
					//text.append(Namespaces.guessLocalName(((Ontology)entryIndiv).getOntologyURI()));
					text.append(((Ontology)entryIndiv).getOntologyURI());
					
				} catch (Exception e) {
					text.append(e.toString()+Namespaces.guessLocalName(entryIndiv.toString()));
				}	
			}
			if(entryIndiv instanceof String){
				text.append((String)entryIndiv);
			}
				
			return text.toString();
		} catch (RuntimeException e) {
			e.printStackTrace();
            throw e;
		}
	}
	 public void addListener(ILabelProviderListener arg0) {
		}

	public boolean isLabelProperty(Object arg0, String arg1) {
			return true;
		}

	public void removeListener(ILabelProviderListener arg0) {
		}
	public void dispose() {
	}
}
