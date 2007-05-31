package ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import org.eclipse.swt.widgets.Shell;

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
	private ArrayList<LinkImpl> links = new ArrayList<LinkImpl>();
	private ArrayList<LinkSelectionListener> linkSelectionListeners = new ArrayList<LinkSelectionListener>();
	private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	
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
				LinkImpl link = getLink(new Point(e.x, e.y));
				if(link != null){
					for(int i=0; i<linkSelectionListeners.size(); i++){
						 ((LinkSelectionListener)linkSelectionListeners.get(i)).linkSelected(link);
					}
				}
			}
		});
		
		wrapedTextComponent.addMouseMoveListener(new MouseMoveListener(){
			public void mouseMove(MouseEvent e){
				LinkImpl link = getLink(new Point(e.x, e.y));
				if(link != null){
					wrapedTextComponent.setCursor(handCursor);
				}else {
					wrapedTextComponent.setCursor(defaultCursor);
				}
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
		//EntryDetailSerializer serializer = EntryDetailSerializer.getInstance();
		Collection properties = getReplyPropertySet(mOyster2.getLocalHostOntology(),(Individual)entry);
		if (properties.size()>0){
			try{
				OWLClass typeClass =(OWLClass) (((Individual) entry).getDescriptionsMemberOf(mOyster2.getLocalHostOntology()).toArray())[0];
				appendText(serializeType(typeClass).toString(),SWT.BOLD,null);
				appendText(serializeKey(entry).toString(),SWT.NORMAL,null);
				serializeProperties(properties);
				appendText(serializeEndOfEntry().toString(),SWT.BOLD,null);
				//wrapedTextComponent.append(serializer.serialize(entry,mOyster2.getLocalHostOntology(),properties));
			}catch(KAON2Exception e){
				System.out.println(e.toString()+" :append");
			}	
		}
		else 
			appendText("details not available",SWT.BOLD,null);//wrapedTextComponent.append("details not available");
		wrapedTextComponent.setTopIndex(0);
	}
	
	public void append(Object entry){
		EntryDetailSerializer serializer = EntryDetailSerializer.getInstance();
		if(entry.toString().contains(Constants.IMPORT)){
			int length = entry.toString().length();
			int headLength = Constants.IMPORT.length()+1;//1 place for the tab.
			Individual ontologyIndiv = KAON2Manager.factory().individual(entry.toString().substring(headLength,length));
			//wrapedTextComponent.append(serializer.serialize(ontologyIndiv,mKaonP2P.getVirtualOntology(),getReplyPropertySet(mKaonP2P.getVirtualOntology(),ontologyIndiv)));
		}
		else wrapedTextComponent.append(entry.toString());
		wrapedTextComponent.setTopIndex(0);
	}
	
	public void append(Ontology ontology){
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
	
	public StringBuffer serializeType(Entity type) {
		StringBuffer result = new StringBuffer();
		result.append("<?xml version="+EntryDetailSerializer.QUOTE+"1.0"+EntryDetailSerializer.QUOTE+" encoding="+EntryDetailSerializer.QUOTE+"utf-8"+EntryDetailSerializer.QUOTE+"?>\n");
		result.append("<rdf:RDF xmlns:rdf="+EntryDetailSerializer.QUOTE+"http://www.w3.org/1999/02/22-rdf-syntax-ns#"+EntryDetailSerializer.QUOTE+"\n");
		result.append("xmlns:omv="+EntryDetailSerializer.QUOTE+"http://omv.ontoware.org/2005/05/ontology#"+EntryDetailSerializer.QUOTE+">\n");
		return result;
	}
	protected StringBuffer serializeKey(Entity entry) {
		StringBuffer result = new StringBuffer();
		result.append("\t<rdf:Description rdf:about=");
		result.append(EntryDetailSerializer.QUOTE+entry.getURI()+EntryDetailSerializer.QUOTE+">\n");
		result.append("\t\t<rdf:type rdf:resource="+EntryDetailSerializer.QUOTE+"http://www.w3.org/2000/01/rdf-schema#Resource"+EntryDetailSerializer.QUOTE+"/>\n");		
		result.append("\t\t<rdf:type rdf:resource="+EntryDetailSerializer.QUOTE+"http://omv.ontoware.org/2005/05/ontology#Ontology"+EntryDetailSerializer.QUOTE+"/>\n");
		return result;
	}
	
	protected void serializeProperties(Collection propertySet) {
		Iterator it = propertySet.iterator();
		while(it.hasNext()){
		  Property property = (Property)it.next();
		  String pred = property.getPred();
		  Object value = property.getValue();
		  if (!serializeValue(pred).equals(Constants.timeStamp)){
			appendText("\t\t<omv:"+ serializeValue(pred)+">",SWT.NORMAL,null);
			Boolean whatIs = checkDataProperty(serializeValue(pred));
			if (!whatIs){
				if (serializeValue(pred).equals(Constants.useImports) ||
					serializeValue(pred).equals(Constants.isBackwardCompatibleWith) ||
					serializeValue(pred).equals(Constants.isIncompatibleWith) ||
					serializeValue(pred).equals(Constants.hasPriorVersion)
						)
					appendAsLink(value.toString(),serializeValue(pred));
				else if (serializeValue(pred).equals(Constants.hasContributor) ||
						serializeValue(pred).equals(Constants.hasCreator)
						){
					String oriValue=serializeValue(value.toString());
					String firstname="";
					String lastname="";
					int i;
					for (i=1; i<oriValue.length(); i++)
						if (Character.isUpperCase(oriValue.charAt(i)))
							break;
					firstname=oriValue.substring(0, i);
					lastname=oriValue.substring(i,oriValue.length());
					appendAsLink(firstname+" "+lastname,serializeValue(pred));
				}
				else
					appendAsLink(serializeValue(value.toString()),serializeValue(pred));//appendText(serializeValue(value.toString()),SWT.BOLD,linkColor);
			}
			else if (serializeValue(pred).equals(Constants.resourceLocator) 
						){
					String[] result = value.toString().split(";");
					for (int x=0; x<result.length; x++){
						appendAsLink(result[x],serializeValue(pred));
						if (x<result.length-1)appendText("  ",SWT.NORMAL,null);
					}
			}
			else if (serializeValue(pred).equals(Constants.documentation) 
						)
					appendAsLink(value.toString(),serializeValue(pred));
			else appendText(value.toString(),SWT.NORMAL,null);
			appendText("</omv:"+ serializeValue(pred)+">",SWT.NORMAL,null);
			if(it.hasNext()) appendText("\n",SWT.NORMAL,null);
		  }
		}
	}
	
	private void appendAsLink(String linkedValue, String linkType){
		int begin = wrapedTextComponent.getCharCount();
		int end = begin + linkedValue.length();
		appendText(linkedValue, SWT.BOLD, linkColor); 
		links.add(new LinkImpl(begin, end, linkedValue, linkType));
	}
	
	public String serializeValue(String value) {	
		String	ret = Namespaces.guessLocalName(value);
		return ret;
		
	}
	
	public StringBuffer serializeEndOfEntry() {
		StringBuffer result = new StringBuffer();
		if (result.indexOf("\n") < 0) { // case with empty content
			result.append(" ");
		}
		result.append("\n\t</rdf:Description>\n</rdf:RDF>\n");
		return result;
	}
	
	public Boolean checkDataProperty(String propertyName)  {
		Ontology resourceTypeOntology = mOyster2.getTypeOntology();
		try{
	        Request<Entity> entityRequest=resourceTypeOntology.createEntityRequest();
	        org.semanticweb.kaon2.api.Cursor<Entity> cursor=entityRequest.openCursor();
	        while (cursor.hasNext()) {
	            Entity entity=cursor.next();
	            if (entity instanceof DataProperty)
	            	if (propertyName.equalsIgnoreCase(Namespaces.guessLocalName(entity.getURI()))) return true;
	        }
	        if (propertyName.equalsIgnoreCase(Namespaces.guessLocalName(Constants.timeStamp))) return true;
		}
	    catch (KAON2Exception e) {
	    	System.err.println(e + " in checkdataproperty()");
	    }	
	    return false;
	}
	/**
	 * @param listener
	 */
	public void addLinkSelectionListener(LinkSelectionListener listener){
		linkSelectionListeners.add(listener);
	}
	
	/**
	 * @param listener
	 */
	public void removeLinkSelectionListener(LinkSelectionListener listener){
		linkSelectionListeners.remove(listener);
	}
	
	private LinkImpl getLink(Point point){
		try{
			int offset = wrapedTextComponent.getOffsetAtLocation(point);
			for(int i=0; i<links.size(); i++){
				LinkImpl p = (LinkImpl)links.get(i);
				if(p.contains(offset)){
					return p;
				}
			}
		}catch(IllegalArgumentException e){
		}
		return null;
	}
	
	private class LinkImpl implements ViewerLink {
		int beginIndex;
		int endIndex;
		private String linkedValue;
		private String linkType;
		
		public LinkImpl(int begin, int end, String value, String linkType){
			this.beginIndex = begin;
			this.endIndex = end;
			this.linkType = linkType;
			this.linkedValue = value;
		}
		
		public boolean contains(int index){
			if(index >= beginIndex && index <= endIndex)
				return true;
			return false;
		}
		
		/**
		 * @see com.empolis.swap.bibster.ui.BibTexViewerLink#getLinkedResource()
		 */
		public String getLinkedValue() {
			return this.linkedValue;
		}

		/**
		 * @see com.empolis.swap.bibster.ui.BibTexViewerLink#getLinkType()
		 */
		public String getLinkType() {
			return this.linkType;
		}

	}

}
