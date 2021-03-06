package org.neontoolkit.registry.ui.provider;


import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.neontoolkit.registry.oyster2.Oyster2Factory;
import org.neontoolkit.registry.ui.ResultViewer;
import org.neontoolkit.registry.ui.ResultViewerColumnInfo;
import org.neontoolkit.registry.util.Property;
import org.neontoolkit.registry.util.Resource;
import org.semanticweb.kaon2.api.*;
import org.semanticweb.kaon2.api.owl.elements.DataProperty;
import org.semanticweb.kaon2.api.owl.elements.Individual;
import org.semanticweb.kaon2.api.owl.elements.ObjectProperty;

//import oyster2.Constants;
//import oyster2.OntologyProperty;



public class ResultViewerLabelProvider implements ITableLabelProvider, IColorProvider{
	private ResultViewer viewer;
	private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private Ontology localRegistry = mOyster2.getLocalHostOntology();
	private Color remoteResourceColor = new Color(Display.getCurrent(), new RGB(0, 0, 200));
	private Color mergedResourceColor = new Color(Display.getCurrent(), new RGB(128, 0, 0));
	
	public ResultViewerLabelProvider(ResultViewer viewer) {
		super();
		this.viewer = viewer;
	}

	/**
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 */
	public String getColumnText(Object element, int columnIndex){
		
		//String columnType = viewer.getColumnType(columnIndex);
		if(element instanceof Resource){
			//USE ALREADY AVAILABLE INFORMATION INSTEAD OF GETTING IT AGAIN
			Resource entry = (Resource)element;
			ResultViewerColumnInfo info = viewer.getColumnInfo(columnIndex);
			return serializeResourceProperty(entry, info.getColumnType()); 
		}
		else if(element instanceof Entity){
			//if(columnType.equals("omv:Ontology")){
			//}
			Entity entry = (Entity)element;
			ResultViewerColumnInfo info = viewer.getColumnInfo(columnIndex);
			return serializeProperty(entry, info.getColumnType());
		}
		return "";
		
	}
	
	private String serializeResourceProperty(Resource entry, String property) {
		try{
			Boolean whatIs = checkDataProperty(Namespaces.guessLocalName(property));
			if (whatIs){
				Collection propertySet = entry.getPropertySet();
				
				Iterator a = propertySet.iterator();
				while (a.hasNext()){
					Property t=(Property)a.next();
					if (t.getPred().equalsIgnoreCase(property)){
						return t.getValue().toString();
					}
				}
				return "";
			}
			else{
				Collection propertySet = entry.getPropertySet();
				
				Iterator a = propertySet.iterator();
				while (a.hasNext()){
					Property t=(Property)a.next();
					if (t.getPred().equalsIgnoreCase(property)){
						return Namespaces.guessLocalName(t.getValue().toString());
					}
				}
				return "";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	private String serializeProperty(Entity entry, String property) {
		Individual oIndividual = KAON2Manager.factory().individual(entry.getURI());
		try{
			Boolean whatIs = checkDataProperty(Namespaces.guessLocalName(property));
			if (whatIs){
				DataProperty ontologyDataProperty = KAON2Manager.factory().dataProperty(property);
				String oldValue = org.neontoolkit.registry.util.Utilities.getString(oIndividual.getDataPropertyValue(localRegistry,ontologyDataProperty)); //(String)
				return oldValue;
			}
			else{
				ObjectProperty ontologyObjectProperty = KAON2Manager.factory().objectProperty(property);
				Individual oldSIndiv = oIndividual.getObjectPropertyValue(localRegistry,ontologyObjectProperty);
				if (oldSIndiv!=null)
					return Namespaces.guessLocalName(oldSIndiv.getURI());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	public Boolean checkDataProperty(String propertyName)  {
		
		Ontology resourceTypeOntology = mOyster2.getTypeOntology();
		try{
	        Request<Entity> entityRequest=resourceTypeOntology.createEntityRequest();
	        Cursor<Entity> cursor=entityRequest.openCursor();
	        while (cursor.hasNext()) {
	            Entity entity=cursor.next();
	            if (entity instanceof DataProperty)
	            	if (propertyName.equalsIgnoreCase(Namespaces.guessLocalName(entity.getURI()))) return true;
	        }
		}
	    catch (KAON2Exception e) {
	    	e.printStackTrace();
	    }	
	    return false;
	    //X
	}
	
	public boolean isLabelProperty(Object element, String property) {
		return true;
		
	}
	/**
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void addListener(ILabelProviderListener listener) {
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	public void dispose() {
		remoteResourceColor.dispose();
		mergedResourceColor.dispose();
	}
	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void removeListener(ILabelProviderListener listener) {
	}
	
	
	/**
	 * @see org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.Object)
	 */
	public Color getBackground(Object element) {
		return null;
	}

	/**
	 * @see org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
	 */
	public Color getForeground(Object element) {
		
		return null;
	}
}
