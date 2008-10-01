/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.results.details;


/**
 * @author whisp
 *
 */
public class LinkHolder {

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
