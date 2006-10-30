package ui;

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

public class Result implements QueryReplyListener{
	private Oyster2 mOyster2 = Oyster2.sharedInstance();
	private TreeViewer viewer;
	private ResultViewer resultViewer;
	private static String OMV = "http://omv.ontoware.org/2005/05/ontology#";
	private List entries = new ArrayList();
	//private Map resourceMap =new HashMap();
	private ResultViewerContentProvider contentProvider;
	String  columnContent[];
	public Result(ResultViewer resultViewer,int resourceType){
		this.resultViewer =resultViewer;
		this.viewer = resultViewer.getWrapedViewer();
		contentProvider = new ResultViewerContentProvider(resourceType);
		this.viewer.setContentProvider(contentProvider);
		if((resourceType==Resource.DataResource)||(resourceType==Resource.RegistryResource))
			this.viewer.setLabelProvider(new OntologyLabelProvider(resourceType));
		else {//this.viewer.getLabelProvider().dispose();
			this.viewer.setLabelProvider(new OntologyLabelProvider());
		
		}
		viewer.setInput(entries);
		viewer.expandToLevel(2);
		//viewer.setInput(resourceMap);
	}
	public TreeViewer getViewer(){
		return viewer;
	}
	/**
	 * invoked when a new query reply received.
	 */
	public void newReplyReceived(QueryReply reply) {
		//System.out.println("reply received:");
		Collection docSet =new LinkedList();
		int type = reply.getType();
		if((type==QueryReply.TYPE_OK)&&(reply.getResourceSet().size()>0)){
			Iterator it = reply.getResourceSet().iterator();
			  while(it.hasNext()){
				final Resource  entry =(Resource) it.next();
				//System.out.println("reply:"+entry.getEntity().getURI());
				entries.add(entry.getEntity());
				//resourceMap.put(entry.getEntity(),entry);
				viewer.getControl().getDisplay().syncExec(new Runnable(){
					public void run(){
						viewer.add(entries, entry.getEntity());
					}
				});
			}
			  entries.clear();
			  mOyster2.getMainWindow().operationFinished();
		}else if((type==QueryReply.TYPE_BAD_REQUEST)||(type==QueryReply.TYPE_INIT)||(reply.getResourceSet().size()<=0)){
			final String msg= "<no relevant resource found>";
			entries.add((String)msg);
			viewer.getControl().getDisplay().syncExec(new Runnable(){
				public void run(){
					viewer.add(entries, msg);
				}
			});	
			mOyster2.getMainWindow().operationFinished();
		}
		//mKaonP2P.getMainWindow().operationFinished();
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
				viewer.getControl().getDisplay().syncExec(new Runnable(){
					public void run(){
						viewer.add(entries, docIndiv);
					}
				});
			
			}else if(entry instanceof Ontology){
				System.out.println("entry instanceof Ontology");
				final Ontology ontologyEntry = (Ontology)entry;
				entries.add(ontologyEntry);
				viewer.getControl().getDisplay().syncExec(new Runnable(){
				public void run(){
					viewer.add(entries, ontologyEntry);
				}
				});
				
			}
			
		}
		}
		entries.clear();
		
	}
	public void entryReceived(final Ontology virtualOntology){
		
			entries.add(virtualOntology);
			viewer.getControl().getDisplay().syncExec(new Runnable(){
			public void run(){
				viewer.add(entries, virtualOntology);
			}
		});
		
		entries.clear();
		
	}
	/**
	 * @return number of received entries.
	 */
	public int computeEntries(){
		return entries.size();
	}
	
	/**
	 * @return final list of all collected bibliographical entries.
	 * Returned list contains objects of type <code>Entry<code>. 
	 */
	public final List getAllEntries(){
		return entries;
	}
	
}
