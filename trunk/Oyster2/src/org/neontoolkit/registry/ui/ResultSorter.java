/*
 * Created on 2004-01-12
 */
package org.neontoolkit.registry.ui;

//import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * @author pap
 */
public class ResultSorter extends ViewerSorter {

	/** LOG */
	//private final static Logger LOG = Logger.getLogger(ResultSorter.class);

	private int sortedColumnIndex = -1;  //YO CAMBIE AQUI -1
	private String sortedColumnType = null ;   //YO CAMBIE AQUI null
		
	/**
	 * @see org.eclipse.jface.viewers.ViewerSorter#category(java.lang.Object)
	 */
	public int category(Object element) {
		return super.category(element);
	}

	/**
	 * @see org.eclipse.jface.viewers.ViewerSorter#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public int compare(Viewer viewer, Object e1, Object e2) {
		if(sortedColumnIndex >= 0 && sortedColumnType != null){
			if(viewer != null && (viewer instanceof StructuredViewer)) {
				IBaseLabelProvider prov = ((StructuredViewer)viewer).getLabelProvider();
				if (prov instanceof ITableLabelProvider) {
					ITableLabelProvider lprov = (ITableLabelProvider)prov;
					String name1 = lprov.getColumnText(e1, sortedColumnIndex);
					String name2 = lprov.getColumnText(e2, sortedColumnIndex);
					if (name1==null) name1="";
					if (name2==null) name2="";
					if(name1 != null && name2 != null)
						return collator.compare(name1, name2);
				}
			}
		}
		return 0;
	}
	
	/**
	 * @see org.eclipse.jface.viewers.ViewerSorter#isSorterProperty(java.lang.Object, java.lang.String)
	 */
	public boolean isSorterProperty(Object element, String property) {
		return property.equals(sortedColumnType);
	}	

	/**
	 * 
	 * @param index
	 * @param typeName
	 */
	public void setSortedColumn(int index, String typeName){
		if(index != sortedColumnIndex){ 
			this.sortedColumnType = typeName;
			this.sortedColumnIndex = index;
		}
	}
	
	/**
	 * @return
	 */
	public int getSortedColumnIndex(){
		return this.sortedColumnIndex;
	}
	
	/**
	 * @return
	 */
	public String getSortedColumnType(){
		return this.sortedColumnType;
	}
}
