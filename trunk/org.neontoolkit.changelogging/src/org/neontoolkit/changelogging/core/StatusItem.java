package org.neontoolkit.changelogging.core;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.StatusLineLayoutData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

	
public class StatusItem extends ContributionItem {
	
	
	    public final static int DEFAULT_CHAR_WIDTH = 40;

	    private int charWidth;

	    private CLabel label;

	    private Composite statusLine = null;

	    private String text = "";

	    private int widthHint = -1;

	    private int heightHint = -1;

	    public StatusItem(String id) {
	        this(id, DEFAULT_CHAR_WIDTH);
	    }

	    public StatusItem(String id, int charWidth) {
	        super(id);
	        this.charWidth = charWidth;
	        setVisible(false); // no text to start with
	    }

	    public void fill(Composite parent) {
	    	
	        statusLine = parent;

	        Label sep = new Label(parent, SWT.SEPARATOR);
	        label = new CLabel(statusLine, SWT.SHADOW_NONE);

	        if (widthHint < 0) {
	            GC gc = new GC(statusLine);
	            gc.setFont(statusLine.getFont());
	            FontMetrics fm = gc.getFontMetrics();
	            widthHint = fm.getAverageCharWidth() * charWidth;
	            heightHint = fm.getHeight();
	            gc.dispose();
	        }

	        StatusLineLayoutData data = new StatusLineLayoutData();
	        data.widthHint = widthHint;
	        label.setLayoutData(data);
	        label.setText(text);

	        data = new StatusLineLayoutData();
	        data.heightHint = heightHint;
	        sep.setLayoutData(data);

	    }

	    public Point getDisplayLocation() {
	        if ((label != null) && (statusLine != null)) {
	            return statusLine.toDisplay(label.getLocation());
	        }

	        return null;
	    }

	    public String getText() {
	        return text;
	    }

	    public void setText(String text) {
	        if (text == null) {
				throw new NullPointerException();
			}

	        this.text = text;

	        if (label != null && !label.isDisposed()) {
				label.setText(this.text);
			}

	        if (this.text.length() == 0) {
	            if (isVisible()) {
	                setVisible(false);
	                IContributionManager contributionManager = getParent();

	                if (contributionManager != null) {
						contributionManager.update(true);
					}
	            }
	        } else {
	            if (!isVisible()) {
	                setVisible(true);
	                IContributionManager contributionManager = getParent();

	                if (contributionManager != null) {
						contributionManager.update(true);
					}
	            }
	        }
	    }
}


