package org.neontoolkit.changelogging.core;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class ChangeTableItem extends TableItem {
	
	int CHANGE_URI = 1; //We fix for now the position
	
	public ChangeTableItem(Table parent, int style) {
		super(parent, style);
	}

	public ChangeTableItem(Table parent, int style, int index) {
		super(parent, style, index);
	}

	public String getChangeURI(){
		return getText(CHANGE_URI);
	}
	
	@Override
    protected void checkSubclass() {
    }
}
