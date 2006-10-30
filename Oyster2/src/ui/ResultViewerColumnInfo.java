package ui;

import java.io.Serializable;

import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.widgets.TableColumn;


public class ResultViewerColumnInfo implements ControlListener, Serializable {
	
	private int width = 100;
	private String columnName;
	private String columnType;
		
	public ResultViewerColumnInfo(String uri){
		/*TODO this.columnName = uri.getLocalName();
		this.columnType = uri.getURI();
		*/
	}
		
	public ResultViewerColumnInfo(String columnName, String columnType){
		this.columnName = columnName;
		this.columnType = columnType;
	}
	
	public ResultViewerColumnInfo(String columnName, String columnType, int width){
		this.columnName = columnName;
		this.columnType = columnType;
		this.width = width;
	}
		
	public String getColumnName(){
		return columnName;
	}
		
	public String getColumnType(){
		return columnType;
	}
		
	public int getWidth(){
		return this.width;
	}
		
	/**
	 * @see org.eclipse.swt.events.ControlListener#controlMoved(org.eclipse.swt.events.ControlEvent)
	 */
	public void controlMoved(ControlEvent e) {

	}

	/**
	 * @see org.eclipse.swt.events.ControlListener#controlResized(org.eclipse.swt.events.ControlEvent)
	 */
	public void controlResized(ControlEvent e) {
		width = ((TableColumn)e.widget).getWidth();
	}

}

