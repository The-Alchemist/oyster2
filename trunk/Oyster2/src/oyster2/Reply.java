package oyster2;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.Hashtable;

import oyster2.*;
import ui.provider.ResultViewerContentProvider;
import ui.provider.OntologyLabelProvider;
import util.*;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.SWT;

import org.semanticweb.kaon2.api.*;
import org.semanticweb.kaon2.api.owl.elements.*;

public class Reply implements QueryReplyListener{
	private List entries = new ArrayList();
	
	public Reply(){
	}
	
	/**
	 * invoked when a new query reply received.
	 */
	public void newReplyReceived(QueryReply reply) {
		Collection docSet =new LinkedList();
		int type = reply.getType();
		if((type==QueryReply.TYPE_OK)&&(reply.getResourceSet().size()>0)){
			Iterator it = reply.getResourceSet().iterator();
			while(it.hasNext()){
				final Resource  entry =(Resource) it.next();
				entries.add(entry.getEntity());
				//resourceMap.put(entry.getEntity(),entry);
			}
		}else if((type==QueryReply.TYPE_BAD_REQUEST)||(type==QueryReply.TYPE_INIT)||(reply.getResourceSet().size()<=0)){
			final String msg= "<no relevant resource found>";
			entries.add((String)msg);
		}
		entries.clear();
	}
	
	public void entryReceived(final List entryList){
		if(entryList!=null){
		Iterator it = entryList.iterator(); 
		while(it.hasNext()){
			Object entry = it.next();
			if(entry instanceof Entity){
				final Entity docIndiv = (Entity)entry;
				entries.add(docIndiv);
			}else if(entry instanceof Ontology){
				System.out.println("entry instanceof Ontology");
				final Ontology ontologyEntry = (Ontology)entry;
				entries.add(ontologyEntry);
			}			
		}
		}
		entries.clear();
	}
	
	
	/**
	 * @return number of received entries.
	 */
	public int computeEntries(){
		return entries.size();
	}
	
	/**
	 * @return final list of all collected entries.
	 * Returned list contains objects of type <code>Entry<code>. 
	 */
	public final List getAllEntries(){
		return entries;
	}
}
