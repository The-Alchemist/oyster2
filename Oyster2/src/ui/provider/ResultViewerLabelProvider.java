package ui.provider;

import ui.*;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import org.semanticweb.kaon2.api.*;



public class ResultViewerLabelProvider implements ITableLabelProvider, IColorProvider{
	private ResultViewer viewer;
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
		String columnType = viewer.getColumnType(columnIndex);
		if(element instanceof Entity){
			Entity entry = (Entity)element;
			if(columnType.equals("omv:Ontology")){
			
			}
		}
			
		return null;
		
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
