package ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import util.*;
import oyster2.*;
import util.Utilities;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import org.semanticweb.kaon2.api.*;
import org.semanticweb.kaon2.api.owl.elements.*;

public class DetailViewer extends Composite{
	private StyledText wrapedTextComponent;
	private Color linkColor = new Color(Display.getCurrent(), new RGB(0, 0, 200));
	private Cursor defaultCursor = new Cursor(this.getShell().getDisplay(), SWT.CURSOR_IBEAM);
	private Cursor handCursor = new Cursor(this.getShell().getDisplay(), SWT.CURSOR_HAND);
	private ArrayList links = new ArrayList();
	private ArrayList linkSelectionListeners = new ArrayList();
	private Oyster2 mOyster2 = Oyster2.sharedInstance();
	
	/**
	 * @param parent
	 * @param style
	 */
	public DetailViewer(Composite parent, int style){
		super(parent, SWT.NONE);
		
		this.setLayout(new FillLayout());
		wrapedTextComponent = new StyledText(this, style);
		wrapedTextComponent.setEditable(false);
		wrapedTextComponent.setCaret(null);
		
		Listener listener = new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Dispose: onDispose(e); break;
				case SWT.FocusIn: onFocusIn(e); break;
				}
			}
		};
		addListener(SWT.Dispose, listener);
		addListener(SWT.FocusIn, listener);
		
		wrapedTextComponent.addMouseListener(new org.eclipse.swt.events.MouseAdapter(){
			public void mouseDown(MouseEvent e) {
				/*TODO BibTexLinkImpl link = getLink(new Point(e.x, e.y));
				if(link != null){
					for(int i=0; i<linkSelectionListeners.size(); i++){
						 ((LinkSelectionListener)linkSelectionListeners.get(i)).linkSelected(link);
					}
				}*/
			}
		});
		
		wrapedTextComponent.addMouseMoveListener(new MouseMoveListener(){
			public void mouseMove(MouseEvent e){
				/*BibTexLinkImpl link = getLink(new Point(e.x, e.y));
				if(link != null){
					wrapedTextComponent.setCursor(handCursor);
				}else {
					wrapedTextComponent.setCursor(defaultCursor);
				}*/
			}
		});
	}
	
	/**
	 * 
	 */
	public void setFont(Font font){
		wrapedTextComponent.setFont(font);
	}
	
	/**
	 * @return
	 */
	public Control getTextComponent(){
		return wrapedTextComponent;
	}
	
	/**
	 * @param entry
	 */
	public void append(Entity entry){
		EntryDetailSerializer serializer = EntryDetailSerializer.getInstance();
		Collection properties = getReplyPropertySet(mOyster2.getLocalHostOntology(),(Individual)entry);
		if (properties.size()>0)
			wrapedTextComponent.append(serializer.serialize(entry,mOyster2.getLocalHostOntology(),properties));
		else 
			wrapedTextComponent.append("details not available");
		/*
		 * 
		if(mKaonP2P.getVirtualOntology()!=null){
			wrapedTextComponent.append(serializer.serialize(entry,mKaonP2P.getVirtualOntology(),getReplyPropertySet(mKaonP2P.getVirtualOntology(),(Individual)entry)));
		}else
			wrapedTextComponent.append(serializer.serialize(entry,mKaonP2P.getLocalHostOntology(),getReplyPropertySet(mKaonP2P.getLocalHostOntology(),(Individual)entry)));
		*/	
		wrapedTextComponent.setTopIndex(0);
	}
	public void append(Object entry){
		EntryDetailSerializer serializer = EntryDetailSerializer.getInstance();
		if(entry.toString().contains(Constants.IMPORT)){
			int length = entry.toString().length();
			int headLength = Constants.IMPORT.length()+1;//1 place for the tab.
			Individual ontologyIndiv = KAON2Manager.factory().individual(entry.toString().substring(headLength,length));
			//System.out.println(ontologyIndiv.getURI());
			//wrapedTextComponent.append(serializer.serialize(ontologyIndiv,mKaonP2P.getVirtualOntology(),getReplyPropertySet(mKaonP2P.getVirtualOntology(),ontologyIndiv)));
		}
		else wrapedTextComponent.append(entry.toString());
		
		wrapedTextComponent.setTopIndex(0);
		
	}
	public void append(Ontology ontology){
		//System.err.println("ontology entry selected!");
		EntryDetailSerializer serializer = EntryDetailSerializer.getInstance();
		
		wrapedTextComponent.append(serializer.serialize(ontology));
		wrapedTextComponent.setTopIndex(0);
	}
	private Collection getReplyPropertySet(Ontology virtualOntology,Individual docIndiv){
		Collection propertySet = new ArrayList();
		Property replyProperty;
		try{
			if(docIndiv ==null)System.out.println("docIndiv is null");
		/*virtualOntology.removeFromImports(mKaonP2P.getLocalHostOntology());
		virtualOntology.addToImports(mKaonP2P.getLocalHostOntology());*/
		Map dataPropertyMap = docIndiv.getDataPropertyValues(virtualOntology);
		Map objectPropertyMap = docIndiv.getObjectPropertyValues(virtualOntology);
		Collection keySet = dataPropertyMap.keySet();
		Iterator keys = keySet.iterator();
		//System.out.println(" Reply: "+docIndiv.getURI());
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
			System.err.println(e.toString()+" :getReplyPropertySet() in DetailViewer.");
		}
		return propertySet;
	}
	
	private HashSet appendOrderedProperties(Entity entry){
		HashSet hashSet = new HashSet();
		/*TODO 
		IPreferenceStore prefStore = JFacePreferences.getPreferenceStore();
		for(int i=0; ; i++){
			String uri = (String)prefStore.getString("BIBTEX_PROPERTY_ORDER_" + i);
			if(uri.equals("")) break;
			ObjectProperty p = entry.getObjectProperty(uri);
			if(p != null){
				appendProperty(p);
				hashSet.add(p);
			}
		}*/
		return hashSet;
	}
	
	/**
	 * @param entry
	 */
	public void setEntry(Entity entry){
		wrapedTextComponent.setText("");
		links = new ArrayList();
		append(entry);
		wrapedTextComponent.setTopIndex(0);
	}
	
	/**
	 *
	 */
	public void clear(){
		wrapedTextComponent.setText("");
		links = new ArrayList();
	}
	
	
	
	private void appendText(String text, int style, Color textColor){
		wrapedTextComponent.append(text);
		StyleRange range = new StyleRange(wrapedTextComponent.getCharCount()-text.length(), text.length(), textColor, null, style);
		wrapedTextComponent.setStyleRange(range);
	}
	
	
	
	private void onDispose(Event e) {
		linkColor.dispose();
		defaultCursor.dispose();
		handCursor.dispose();
	}
	
	private void onFocusIn (Event e) {
		wrapedTextComponent.setFocus();
	}
	
	
}
