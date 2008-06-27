package org.neontoolkit.registry.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.List;

//import org.eclipse.ui.model.WorkbenchLabelProvider;
//import org.eclipse.ui.model.BaseWorkbenchContentProvider;
//import org.eclipse.jface.viewers.LabelProvider;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
//import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
//import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
//import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.neontoolkit.registry.core.AdvertInformer;
import org.neontoolkit.registry.oyster2.Oyster2Factory;
import org.semanticweb.kaon2.api.*;
import org.semanticweb.kaon2.api.owl.elements.Individual;

public class PeerSelectionDialog implements IStructuredContentProvider, ILabelProvider{
	
	private final Set selectedPeerId;
	private CheckboxTableViewer listViewer;
	private DialogUI dialogUI;
	private boolean showAllPeers;
	private Oyster2Factory mOyster2 = Oyster2Factory.sharedInstance();
	private AdvertInformer mInformer = mOyster2.getLocalAdvertInformer();
	private List peersInput = mInformer.getPeerList(mOyster2.getLocalHostOntology());

	private static final class DialogUI extends ListSelectionDialog {
		private CheckboxTableViewer listViewer;
		private SelectionListener buttonListener;
		

		private DialogUI(Shell parentShell, SelectionListener buttonListener, Object input, IStructuredContentProvider contentProvider, ILabelProvider labelProvider, String message) {
			super(parentShell, input, contentProvider, labelProvider, message);
			this.buttonListener = buttonListener;
		}
		protected Control createDialogArea(Composite parent) {
			Control c = super.createDialogArea(parent);
			listViewer = getViewer();
			Composite composite = new Composite(parent, SWT.NONE);
			GridLayout layout = new GridLayout();
			composite.setLayout(layout);
			return c;
		}
		public boolean close() {
			boolean result = super.close();
			return result;
		}
	}

	public PeerSelectionDialog(Set inputSet) {
		this.selectedPeerId = inputSet;	
	}

	public Set getPeerSelection() {
	  try {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				try {
					Shell shell = Display.getDefault().getActiveShell();
					//System.out.println("yes or not " + org.eclipse.ui.PlatformUI.getWorkbench());
					SelectionListener buttonListener = new SelectionListener() {
						public void widgetSelected(SelectionEvent arg0) {
							showAllPeers = !showAllPeers;
							refreshPeerList();
						}
						public void widgetDefaultSelected(SelectionEvent arg0) {
						} 
					};
					peersInput = mInformer.getPeerList(mOyster2.getLocalHostOntology());										
					dialogUI = new DialogUI(shell, buttonListener, peersInput, PeerSelectionDialog.this,
							PeerSelectionDialog.this, "Please select peers ");
					dialogUI.setTitle("Please select peers");
					//dialogUI.setInitialSelections(new Object[]{});
					dialogUI.setInitialSelections(peersInput.toArray());					
					dialogUI.create();

					Iterator it = mOyster2.getOfflinePeers().iterator();
					List<Individual> grayedElements = new ArrayList<Individual>();
					while (it.hasNext()){
						Individual peerIndiv = KAON2Manager.factory().individual((String)it.next());
						grayedElements.add(peerIndiv);

					}
					dialogUI.listViewer.setGrayedElements(grayedElements.toArray());
					
					listViewer = dialogUI.listViewer;
					System.out.println("Opening...");
					if(dialogUI ==null)
						System.out.println("dialogUI is null.");
					else
						dialogUI.open();
					System.out.println("Opened...");
					Object[] result = dialogUI.getResult();
					if (result!=null)
					for(int i=0;i<result.length;i++)
						System.out.println("Result " + result[i]);
					if (result != null) {
						selectedPeerId.clear();
						selectedPeerId.addAll(Arrays.asList(result));
					}
					dialogUI.close();
					dialogUI = null;
					listViewer = null;
						
					} catch (RuntimeException e) {
						System.err.println(e.toString()+" getPeerSelection() :run()in PeerSelectionDialog.");
					}
				}
			});
			return selectedPeerId;
		} catch (RuntimeException e) {
            System.err.println(e.toString()+" getPeerSelection() in PeerSelectionDialog.");
		}
		return null;
	}

	public Object[] getElements(Object peerIds) {
		return peersInput.toArray() ;
	}

	public void dispose() {
		//    SystemConfig.getPeerFactory().removePeerPresenceListener(this);
	}

	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
	}

	public Image getImage(Object arg0) {
		return null;
	}

	public String getText(Object peerIndiv) {
		try {
			StringBuffer text = new StringBuffer();
			if(peerIndiv instanceof Entity)
			try {
				text.append(Namespaces.guessLocalName(peerIndiv.toString()));
			} catch (Exception e) {
				text.append(e.toString()+Namespaces.guessLocalName(peerIndiv.toString()));
			}
			return text.toString();
		} catch (RuntimeException e) {
			e.printStackTrace();
            throw e;
		}
	}

    public void addListener(ILabelProviderListener arg0) {
	}

	public boolean isLabelProperty(Object arg0, String arg1) {
		return true;
	}

	public void removeListener(ILabelProviderListener arg0) {
	}
	


	private void refreshPeerList() {
		/*if (listViewer != null) {
			listViewer.setInput(model);
		}*/
	}
	
}
