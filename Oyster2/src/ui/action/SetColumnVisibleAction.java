/*
 * Created on 2004-01-12
 */
package ui.action;

import org.eclipse.jface.action.Action;

import ui.ResultViewer;
import ui.ResultViewerColumnInfo;

/**
 * @author pap
 */
public class SetColumnVisibleAction extends Action {

	private boolean dupa;
	private ResultViewerColumnInfo columnInfo;
	private ResultViewer viewer;
	
	public SetColumnVisibleAction(ResultViewerColumnInfo columnInfo, ResultViewer viewer){
		super(columnInfo.getColumnName(), Action.AS_CHECK_BOX);
		this.columnInfo = columnInfo;
		this.viewer = viewer;
	}
	
	public void run() {
		if(!dupa){
			for(int i=0; i<viewer.getColumnCount(); i++){
				if(viewer.getColumnInfo(i).getColumnType().equals(columnInfo.getColumnType())){
					viewer.removeColumn(i);
					break;
				}
			}
		}else {
			viewer.addColumn(columnInfo);
		}
	}
	
	public void setChecked(boolean checked){
		super.setChecked(checked);
		this.dupa = checked;
	}
}
