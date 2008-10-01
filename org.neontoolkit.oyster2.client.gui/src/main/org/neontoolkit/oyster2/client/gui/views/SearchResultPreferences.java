/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.views;

import java.util.Map;
import java.util.TreeMap;

import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IMemento;


/**
 * @author David Muñoz
 *
 */
public class SearchResultPreferences {
	
	private static final String VISIBILITY_MEMENTO_TAG = "visibility";
	private static final String COLUMN_WIDTH_MEMENTO_TAG = "columnWidth";
	private static final String COLUMN_ORDER_MEMENTO_TAG = "columnOrder";
	
	
	class ColumnPreferences {
		
		
		
		private int order = 0;
		private int width = 0;
		
		/**
		 * @return the order
		 */
		public final int getOrder() {
			return order;
		}
		/**
		 * @param order the order to set
		 */
		public final void setOrder(int order) {
			this.order = order;
		}
		
		/**
		 * @return the width
		 */
		public final int getWidth() {
			return width;
		}
		/**
		 * @param width the width to set
		 */
		public final void setWidth(int width) {
			this.width = width;
		}
		
	}
	
	class TypePreferences {
		
		private Map<String,ColumnPreferences> columns = null;
		
		private Map<String,Boolean> visibility = null;
		
		public TypePreferences() {
			columns = new TreeMap<String, ColumnPreferences>();
			visibility = new TreeMap<String, Boolean>();
		}
		
		public void load(IMemento memento) {
			
		}
		
		
		public ColumnPreferences getColumn(String column) {
			return columns.get(column);
		}
		
		public void setColumn(String columnName,ColumnPreferences preferences) {
			columns.put(columnName, preferences);
		}
		
		/**
		 * @return the columns
		 */
		public final Map<String, ColumnPreferences> getColumns() {
			return columns;
		}

		/**
		 * @param columns the columns to set
		 */
		public final void setColumns(Map<String, ColumnPreferences> columns) {
			this.columns = columns;
		}

		public final Boolean isVisible(String column) {
			return visibility.get(column);
		}
		
		public final void setVisible(String column,Boolean visible) {
			visibility.put(column,visible);
		}
		
		/**
		 * @return the visibility
		 */
		public final Map<String, Boolean> getVisibility() {
			return visibility;
		}

		/**
		 * @param visibility the visibility to set
		 */
		public final void setVisibility(Map<String, Boolean> visibility) {
			this.visibility = visibility;
		}

		public void setVisibility(String propertyName, boolean isVisible) {
			this.visibility.put(propertyName,isVisible);
			
		}

		public ColumnPreferences createColumn(String propertyName) {
			ColumnPreferences columnPreferences = new ColumnPreferences();
			columns.put(propertyName,columnPreferences);
			if (!visibility.containsKey(propertyName))
				visibility.put(propertyName,false);
			return columnPreferences;
		}
		
		
		
	}
	
	private Map<String,TypePreferences> typePreferencesMap = null;
	
	public SearchResultPreferences() {
		typePreferencesMap = new TreeMap<String, TypePreferences>();
	}

	public void load(IMemento memento, String type, String[] columns) {
		if (memento == null)
			return;
		IMemento typeMemento = memento.getChild(type);
		if (typeMemento==null)
			return;
		
		IMemento propertyMemento = null;
		String propertyName = null;
		int width, position ;
		Boolean visible = null;
		TypePreferences typePreferences = new TypePreferences();
		ColumnPreferences columnPreferences;
		
		
		for (int i = 0; i< columns.length;i++) {
			propertyName = columns[i];
			propertyMemento = typeMemento.getChild(propertyName);
			if (propertyMemento == null)
				continue;
			
			width = propertyMemento.getInteger(COLUMN_WIDTH_MEMENTO_TAG);
			position = propertyMemento.getInteger(COLUMN_ORDER_MEMENTO_TAG);
			visible = Boolean.valueOf(propertyMemento.getString(VISIBILITY_MEMENTO_TAG));
 
			columnPreferences = new ColumnPreferences();
			columnPreferences.setOrder(position);
			columnPreferences.setWidth(width);
			typePreferences.setVisible(propertyName, visible);
			typePreferences.setColumn(propertyName,columnPreferences);
			
		}
		typePreferencesMap.put(type,typePreferences);
		
	}
	
	public void save(IMemento memento) {
		if (memento == null)
			return;
		IMemento typeMemento  = null;
		IMemento propertyMemento = null;
		TypePreferences typePreferences = null;
		ColumnPreferences columnPreferences = null;
		
		for (String type : typePreferencesMap.keySet()) {
			typeMemento = memento.getChild(type);
			if (typeMemento==null)
				typeMemento = memento.createChild(type);
			typePreferences = typePreferencesMap.get(type);
			
			String showPreference = null;
			
			for (String propertyName : typePreferences.columns.keySet()) {
				columnPreferences = typePreferences.getColumn(propertyName);
				propertyMemento = typeMemento.getChild(propertyName);
				if (propertyMemento == null)
					propertyMemento = typeMemento.createChild(propertyName);
				
				showPreference = typePreferences.getVisibility().get(propertyName).toString();
				
				propertyMemento.putString(VISIBILITY_MEMENTO_TAG,showPreference);
				
				propertyMemento.putInteger(COLUMN_WIDTH_MEMENTO_TAG,
						columnPreferences.getWidth());
				
				propertyMemento.putInteger(COLUMN_ORDER_MEMENTO_TAG,
						columnPreferences.getOrder());
			}
		}
	}
	
	
	public TypePreferences getTypePreferences(String rdfType) {
		return typePreferencesMap.get(rdfType);
	}
	
	public void setTypePreferences(String rdfType,TypePreferences preferences) {
		typePreferencesMap.put(rdfType, preferences);
	}

	/**
	 * @return the typePreferencesMap
	 */
	public final Map<String, TypePreferences> getTypePreferencesMap() {
		return typePreferencesMap;
	}

	public void createTypePreferences(String targetName) {
		TypePreferences preferences = new TypePreferences();
		typePreferencesMap.put(targetName,preferences);
		
	}
	
	
}
