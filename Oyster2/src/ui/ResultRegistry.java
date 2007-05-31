package ui;

import java.util.*;
import oyster2.*;
import ui.provider.ResultViewerContentProvider;
import ui.provider.OntologyLabelProvider;
import util.*;
import org.eclipse.jface.viewers.TreeViewer;
//import ui.provider.ResultViewerLabelProvider;
//import org.eclipse.jface.viewers.TableTreeViewer;
//import org.eclipse.swt.widgets.TreeItem;
//import org.eclipse.swt.widgets.Tree;
//import org.eclipse.swt.SWT;

import org.semanticweb.kaon2.api.*;


public class ResultRegistry implements QueryReplyListener{
	//private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private TreeViewer viewer;
	private List<Object> entries = new ArrayList<Object>();
	private ResultViewerContentProvider contentProvider;
	String  columnContent[];
	//private Map resourceMap =new HashMap();
	public ResultRegistry(ResultViewerRegistry resultViewer,int resourceType){
		this.viewer = resultViewer.getWrapedViewer();
		contentProvider = new ResultViewerContentProvider(resourceType);
		this.viewer.setContentProvider(contentProvider);
		if((resourceType==Resource.DataResource)||(resourceType==Resource.RegistryResource))
			this.viewer.setLabelProvider(new OntologyLabelProvider(resourceType));
		else {//this.viewer.getLabelProvider().dispose();
			this.viewer.setLabelProvider(new OntologyLabelProvider());	
		}
		viewer.setInput(entries);
		//viewer.expandToLevel(2);
	}
	
	public TreeViewer getViewer(){
		return viewer;
	}
	
	/**
	 * invoked when a new query reply received.
	 */
	public void newReplyReceived(QueryReply reply) {
		int type = reply.getType();
		if((type==QueryReply.TYPE_OK)&&(reply.getResourceSet().size()>0)){
			Iterator it = reply.getResourceSet().iterator();
			while(it.hasNext()){
				final Resource  entry =(Resource) it.next();
				entries.add(entry.getEntity());
				viewer.getControl().getDisplay().syncExec(new Runnable(){
					public void run(){
						viewer.add(entries, entry.getEntity());
					}
				});
			}
		}else if((type==QueryReply.TYPE_BAD_REQUEST)||(type==QueryReply.TYPE_INIT)||(reply.getResourceSet().size()<=0)){
			final String msg= "<no relevant resource found>";
			entries.add((String)msg);
			viewer.getControl().getDisplay().syncExec(new Runnable(){
				public void run(){
					viewer.add(entries, msg);
				}
			});	
		}
		StartGUI.getMainWindow().operationFinished();   //mOyster2.getMainWindow().operationFinished();
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
	 * @return final list of all collected entries.
	 * Returned list contains objects of type <code>Entry<code>. 
	 */
	public final List getAllEntries(){
		return entries;
	}
}
