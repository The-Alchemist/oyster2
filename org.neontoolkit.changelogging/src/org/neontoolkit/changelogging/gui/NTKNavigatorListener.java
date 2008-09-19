/*****************************************************************************
 * Copyright (c) 2008 ontoprise GmbH.
 *
 * All rights reserved.
 *
 *****************************************************************************/

package org.neontoolkit.changelogging.gui;

import org.neontoolkit.changelogging.menu.Track;
import com.ontoprise.ontostudio.gui.navigator.EditorEvent;
import com.ontoprise.ontostudio.gui.navigator.IEditorListener;
import com.ontoprise.ontostudio.owl.gui.navigator.clazz.ClazzTreeElement;
import com.ontoprise.ontostudio.owl.gui.navigator.datatypes.DatatypeTreeElement;
import com.ontoprise.ontostudio.owl.gui.navigator.property.annotationProperty.AnnotationPropertyTreeElement;
import com.ontoprise.ontostudio.owl.gui.navigator.property.dataProperty.DataPropertyTreeElement;
import com.ontoprise.ontostudio.owl.gui.navigator.property.objectProperty.ObjectPropertyTreeElement;

/* 
 * Created on: 06.11.2007
 * Created by: Dirk Wenke
 *
 * Keywords: 
 */

public class NTKNavigatorListener implements IEditorListener{

	public void editingCancelled(EditorEvent event) {
		Track.setSelectedElement("");
		//System.out.println("element selected: "+Track.getSelectedElement());
	}

	public void editingFinished(EditorEvent event) {
		//System.out.println("here success: "+event.getText());
		if (event.getItem().getData() instanceof ClazzTreeElement){
    		ClazzTreeElement sel= (ClazzTreeElement)event.getItem().getData();
    		Track.setSelectedElement(sel.getOntologyUri()+"#"+event.getText());
    	}
    	else if (event.getItem().getData() instanceof ObjectPropertyTreeElement){
    		ObjectPropertyTreeElement sel= (ObjectPropertyTreeElement)event.getItem().getData();
    		Track.setSelectedElement(sel.getOntologyUri()+"#"+event.getText());
    	}
    	else if (event.getItem().getData() instanceof DataPropertyTreeElement){
    		DataPropertyTreeElement sel= (DataPropertyTreeElement)event.getItem().getData();
    		Track.setSelectedElement(sel.getOntologyUri()+"#"+event.getText());
    	}
    	else if (event.getItem().getData() instanceof AnnotationPropertyTreeElement){
    		AnnotationPropertyTreeElement sel= (AnnotationPropertyTreeElement)event.getItem().getData();
    		Track.setSelectedElement(sel.getOntologyUri()+"#"+event.getText());
    	}
    	else if (event.getItem().getData() instanceof DatatypeTreeElement){
    		DatatypeTreeElement sel= (DatatypeTreeElement)event.getItem().getData();
    		Track.setSelectedElement(sel.getOntologyUri()+"#"+event.getText());
    	}
    	else{
    		Track.setSelectedElement("");
    	}
    	//System.out.println("element selected: "+Track.getSelectedElement());
	}

	
}
