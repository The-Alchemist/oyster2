package org.neontoolkit.registry.ui;

import java.util.*;

import org.eclipse.jface.viewers.TreeViewer;
import org.neontoolkit.registry.oyster2.Oyster2Factory;
import org.neontoolkit.registry.oyster2.QueryReply;
import org.neontoolkit.registry.oyster2.QueryReplyListener;
import org.neontoolkit.registry.ui.provider.ResultViewerContentProvider;
import org.neontoolkit.registry.ui.provider.ResultViewerLabelProvider;
import org.neontoolkit.registry.util.Property;
import org.neontoolkit.registry.util.Resource;
import org.semanticweb.kaon2.api.*;

public class Result implements QueryReplyListener{
	private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private TreeViewer viewer;
	private List<Object> entries = new ArrayList<Object>();
	private ResultViewerContentProvider contentProvider;
	Resource todelete=null;
	String  columnContent[];
	
	//private List vocabulary;
	
	//private Map resourceMap =new HashMap();
	public Result(ResultViewer resultViewer,int resourceType){
		this.viewer = resultViewer.getWrapedViewer(); //this.viewer = resultViewer;
		contentProvider = new ResultViewerContentProvider(resourceType);
		this.viewer.setContentProvider(contentProvider);
		if((resourceType==Resource.DataResource)||(resourceType==Resource.RegistryResource))
			this.viewer.setLabelProvider(new ResultViewerLabelProvider(resultViewer));//new OntologyLabelProvider(resourceType));
		else {//this.viewer.getLabelProvider().dispose();
			this.viewer.setLabelProvider(new ResultViewerLabelProvider(resultViewer));//new OntologyLabelProvider());	
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
	public synchronized void newReplyReceived(QueryReply reply) { //NORMAL OPERATION
		if (reply==null) return;
		
		int type = reply.getType();
		if (type==-1){
			mOyster2.getLogger().info("finished replies...");
			StartGUI.getMainWindow().operationFinished();
		}else if((type==QueryReply.TYPE_OK)&&(reply.getResourceSet().size()>0)){
			mOyster2.getLogger().info("recieving replies...");
			Iterator it = reply.getResourceSet().iterator();
			while(it.hasNext()){
				final Resource  entry =(Resource) it.next();
				Collection<Property> propertySet=mergeDuplicates(entry);
				if (propertySet.size()>0 && todelete!=null) {
					entry.setPropertySet(propertySet);
					int a=entries.indexOf(todelete);
					entries.set(a, entry);
					viewer.getControl().getDisplay().syncExec(new Runnable(){
						public void run(){
							viewer.refresh();
						}	
					});
					todelete=null;
				}
				else{
					entries.add(entry);  //entry.getEntity()
					viewer.getControl().getDisplay().syncExec(new Runnable(){
						public void run(){
							viewer.add(entries, entry); //entry.getEntity()
						}
					});
				}
			}
		}else if((type==QueryReply.TYPE_BAD_REQUEST)||(type==QueryReply.TYPE_INIT)){//||(reply.getResourceSet().size()<=0)){
			final String msg= "<no relevant resource found>";
			entries.add((String)msg);
			viewer.getControl().getDisplay().syncExec(new Runnable(){
				public void run(){
					viewer.add(entries, msg);
				}
			});	
		}
		//StartGUI.getMainWindow().operationFinished();   //mOyster2.getMainWindow().operationFinished();
		//entries.clear();
	}
	
	public synchronized void entryReceived(final List entryList){ //ONLY CALLED FROM GUI REGISTRY VIEW WHEN SELECTING PEERS?? DELETE??
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
		//entries.clear();
	}
	
	public synchronized void entryReceived(final Ontology virtualOntology){ //NOT USED SO FAR...
		if (virtualOntology==null) return;
		
		entries.add(virtualOntology);
		viewer.getControl().getDisplay().syncExec(new Runnable(){
			public void run(){
				viewer.add(entries, virtualOntology);
			}
		});
		//entries.clear();	
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
	
	public Collection<Property> mergeDuplicates(Resource entry){
		Collection<Property> propertySet = new ArrayList<Property>();
		
		Iterator a = entries.iterator();
		while (a.hasNext()){
			Resource t=(Resource)a.next();
			if (t.getEntity().equals(entry.getEntity())){
				todelete=t;
				propertySet.clear();
				Iterator in = t.getPropertySet().iterator();
				while (in.hasNext()){
					Property rin=(Property)in.next();
					String value=rin.getValue().toString();
					Iterator inNew = entry.getPropertySet().iterator();
					while (inNew.hasNext()){
						Property rinNew=(Property)inNew.next();
						if (rin.getPred().equalsIgnoreCase(rinNew.getPred())){
							if (!rin.getValue().toString().equalsIgnoreCase(rinNew.getValue().toString())){
								value=rin.getValue().toString()+rinNew.getValue().toString();
							}
						}
					}
					Property end = new Property(rin.getPred(),value);
					propertySet.add(end);
				}
				boolean found=false;
				Iterator inNew = entry.getPropertySet().iterator();
				while (inNew.hasNext()){
					Property rinNew=(Property)inNew.next();
					in = t.getPropertySet().iterator();
					while (in.hasNext()){
						Property rin=(Property)in.next();
						if (rin.getPred().equalsIgnoreCase(rinNew.getPred())){
							found=true;
						}
					}
					if (!found){
						propertySet.add(rinNew);
					}
					found=false;
				}
				//Iterator testing = propertySet.iterator();
				//while (testing.hasNext()){
				//	Property tprop = (Property)testing.next();
				//	System.out.println(tprop.getPred()+": "+tprop.getValue().toString());
				//}
			}
		}
		
		return propertySet;
	}
}
