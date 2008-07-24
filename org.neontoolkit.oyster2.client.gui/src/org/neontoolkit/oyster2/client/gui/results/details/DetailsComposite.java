/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.results.details;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

import org.neontoolkit.oyster2.client.gui.adapter.IOMVObject;



/**
 * @author David Muñoz
 * 			
 *
 */
public class DetailsComposite extends Composite {

	private StyledText wrappedTextComponent = null;
	private Color linkColor = new Color(Display.getCurrent(), new RGB(0, 0, 200));
	private Cursor defaultCursor = new Cursor(this.getShell().getDisplay(), SWT.CURSOR_IBEAM);
	private Cursor handCursor = new Cursor(this.getShell().getDisplay(), SWT.CURSOR_HAND);
	private List<LinkHolder> links = null;
	private ArrayList<ISearchLinkSelectionListener> linkSelectionListeners = new ArrayList<ISearchLinkSelectionListener>();
	
	public DetailsComposite(Composite parent, int style) {
		super(parent, style);

		
		this.setLayout(new FillLayout());
		wrappedTextComponent = new StyledText(this, style);
		wrappedTextComponent.setEditable(false);
		wrappedTextComponent.setCaret(null);
		
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
		
		
		//
		wrappedTextComponent.addMouseListener(new org.eclipse.swt.events.MouseAdapter(){
			public void mouseDown(MouseEvent e) {
				ISearchLink link = getLink(new Point(e.x, e.y));
				if(link != null){
					for(int i=0; i<linkSelectionListeners.size(); i++){
						 ((ISearchLinkSelectionListener)linkSelectionListeners.get(i)).linkSelected(link);
					}
				}
			}
		});
		
		wrappedTextComponent.addMouseMoveListener(new MouseMoveListener(){
			public void mouseMove(MouseEvent e){
				ISearchLink link = getLink(new Point(e.x, e.y));
				if(link != null){
					wrappedTextComponent.setCursor(handCursor);
				}else {
					wrappedTextComponent.setCursor(defaultCursor);
				}
			}
		});
		SearchLinkSelectionListener searchLinkListener =
			new SearchLinkSelectionListener();
		linkSelectionListeners.add(searchLinkListener);
	}
	
	private void onFocusIn(Event e) {
		// TODO Auto-generated method stub
		
	}

	private void onDispose(Event e) {
		linkColor.dispose();
		defaultCursor.dispose();
		handCursor.dispose();
	}
	
	private ISearchLink getLink(Point point) {
		try{
			int offset = wrappedTextComponent.getOffsetAtLocation(point);
			for(int i=0; i<links.size(); i++){
				LinkHolder holder = links.get(i);
				if(holder.contains(offset)){
					return holder.getLink();
				}
			}
		}catch(IllegalArgumentException e){
		}
		return null;
	}
	
	
	public void setEntry(IOMVObject result) {
		wrappedTextComponent.setText(""); //$NON-NLS-1$
		List<LinkedText> linkList = null;
		links = new LinkedList<LinkHolder>();
		IResultSerializer serializer =
			AbstractResultSerializerFactory.getFactory().getSerializer(result);
		
		linkList = serializer.serialize(result);
		for (LinkedText text : linkList) {
			append(text);
		}
		
		
		wrappedTextComponent.setTopIndex(0);
	}
	
	private void append(LinkedText linkedText) {
		if (linkedText.getLink() == null) {
			wrappedTextComponent.append(linkedText.getText() );
		}
		else {
			LinkHolder linkHolder = new LinkHolder();
			int begin = wrappedTextComponent.getCharCount()-1;
			int end = begin + linkedText.getText().length()+1; //BEWARE OFF TROLLS
			appendText(linkedText.getText(), SWT.BOLD, linkColor);
			linkHolder = new LinkHolder();
			linkHolder.begin = begin;
			linkHolder.end = end;
			linkHolder.link = linkedText.getLink();
			links.add(linkHolder);
			
		}
	}
	

	private void appendText(String text, int style, Color textColor){
		wrappedTextComponent.append(text);
		StyleRange range = new StyleRange(wrappedTextComponent.getCharCount()-text.length(), text.length(), textColor, null, style);
		wrappedTextComponent.setStyleRange(range);
	}
	
	
	/**
	 * 
	 */
	public void setFont(Font font){
		wrappedTextComponent.setFont(font);
	}
	
	/**
	 * @return
	 */
	public Control getTextComponent(){
		return wrappedTextComponent;
	}
	
	
	@Override
	public Point computeSize(int wHint, int hHint) {
		//return new Point(wHint,hHint);
		return wrappedTextComponent.computeSize(wHint, hHint);
	}
	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		//return new Point(wHint,hHint);
		return wrappedTextComponent.computeSize(wHint, hHint, true);
	}
	
	
	private class LinkHolder {
		private int begin = 0;
		private int end = 0;
		private ISearchLink link = null;
		/**
		 * @return the begin
		 */
		public final int getBegin() {
			return begin;
		}
		public boolean contains(int offset) {
			return (offset > begin) && (offset<end);
		}
		/**
		 * @param begin the begin to set
		 */
		public final void setBegin(int begin) {
			this.begin = begin;
		}
		/**
		 * @return the end
		 */
		public final int getEnd() {
			return end;
		}
		/**
		 * @param end the end to set
		 */
		public final void setEnd(int end) {
			this.end = end;
		}
		/**
		 * @return the link
		 */
		public final ISearchLink getLink() {
			return link;
		}
		/**
		 * @param link the link to set
		 */
		public final void setLink(ISearchLink link) {
			this.link = link;
		}
		
		
		
	}
	
}
