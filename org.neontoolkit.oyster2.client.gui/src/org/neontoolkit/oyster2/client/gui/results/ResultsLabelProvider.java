/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.results;

import java.util.Iterator;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.graphics.Image;
import org.neontoolkit.oyster2.client.gui.adapter.IOMVObject;

/**
 * @author David Muñoz
 *
 */
public class ResultsLabelProvider extends LabelProvider implements
		ITableLabelProvider {
	
	
	
	private TableViewer tableViewer = null;
	
	
	
	public ResultsLabelProvider(TableViewer tableViewer) {
		super();
		
		this.tableViewer = tableViewer;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 */
	public String getColumnText(Object element, int columnIndex) {
		Object [] properties = tableViewer.getColumnProperties();
		IOMVObject result = (IOMVObject)element;
		Object labelObject =
			result.getValue(properties[columnIndex].toString());
		if (labelObject == null)
			return null;
		if (labelObject instanceof String)
			return labelObject.toString().replaceAll("#","");
		Iterable iterableResult = (Iterable)labelObject;
		Iterator it = iterableResult.iterator();
		StringBuffer composedLabel = new StringBuffer();
		while (it.hasNext()) {
			composedLabel.append(it.next().toString().replaceAll("#","") +
					"; ");
		}
		return composedLabel.toString().trim();
	}

}
