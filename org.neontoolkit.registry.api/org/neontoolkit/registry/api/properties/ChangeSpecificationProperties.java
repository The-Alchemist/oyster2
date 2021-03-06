package org.neontoolkit.registry.api.properties;




import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.extensions.change.OMVChangeSpecification;
import org.neontoolkit.registry.oyster2.Constants;
import org.neontoolkit.registry.oyster2.ImportOntology;
import org.neontoolkit.registry.oyster2.OntologyProperty;

/**
 * The class ChangeSpecificationProperties provides the methods to
 * retrieve the properties from OMV Change Specification
 * objects representing the object of the registry 
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class ChangeSpecificationProperties{
	static private ImportOntology IOntology= new ImportOntology();
	static private String ontologyChangedURI="";
	
	static public LinkedList getChangeSpecificationProperties(OMVChangeSpecification m){

		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		List tList = new LinkedList();
		
		if (m.getInitialTimestamp()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.initialTimestamp, m.getInitialTimestamp());
			tProperties.addFirst(prop);
		}
		if (m.getLastTimestamp()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.lastTimestamp, m.getLastTimestamp());
			tProperties.addFirst(prop);
		}
		
		if (m.getHasChange().size()>0) {
			Iterator it = m.getHasChange().iterator();
			while(it.hasNext()){
				String nl = (String)it.next();
				OntologyProperty prop = new OntologyProperty(Constants.hasChange, nl);
				tProperties.addFirst(prop);
			}
		}
		if (m.getChangeFromVersion()!=null) {
			ontologyChangedURI="";
			String tURN;
			OMVOntology otemp = m.getChangeFromVersion();
			if (otemp.getURI()!=null){
				tList.clear();
				tList=OMVProperties.getProperties(otemp);
				tURN=otemp.getURI();
				boolean hasVersion=false;
				if (otemp.getVersion()!=null){
					tURN=tURN+"?version="+otemp.getVersion();//+";";
					hasVersion=true;
				}
				if (otemp.getResourceLocator()!=null){
					if (!hasVersion) tURN=tURN+"?";
					else tURN=tURN+";";
					tURN=tURN+"location="+otemp.getResourceLocator();
				}
				tURN=tURN.replace(" ", "_");
				ontologyChangedURI=tURN;
				//Pure Register Ontology i.e. what=1
				IOntology.addImportOntologyToRegistry(tList,1, null);
				OntologyProperty prop = new OntologyProperty(Constants.changeFromVersion, ontologyChangedURI);
				tProperties.addFirst(prop);
				
			}
		}
		if (m.getChangeToVersion()!=null) {
			
			String tURN;
			OMVOntology otemp = m.getChangeToVersion();
			if (otemp.getURI()!=null){
				tList.clear();
				tList=OMVProperties.getProperties(otemp);
				tURN=otemp.getURI();
				boolean hasVersion=false;
				if (otemp.getVersion()!=null){
					tURN=tURN+"?version="+otemp.getVersion();//+";";
					hasVersion=true;
				}
				if (otemp.getResourceLocator()!=null){
					if (!hasVersion) tURN=tURN+"?";
					else tURN=tURN+";";
					tURN=tURN+"location="+otemp.getResourceLocator();
				}
				tURN=tURN.replace(" ", "_");
				
				//Pure Register Ontology i.e. what=1
				IOntology.addImportOntologyToRegistry(tList,1, null);
				OntologyProperty prop = new OntologyProperty(Constants.changeToVersion, tURN);
				tProperties.addFirst(prop);
				
			}
		}
		if (m.getURI()!=null) {
			OntologyProperty tPropURN = new OntologyProperty(Constants.URI, m.getURI());
			tProperties.add(tPropURN);
		}else{
			String tURNChange="";
			if (ontologyChangedURI.indexOf("?")>0){
				tURNChange=ontologyChangedURI+";";
			}else{
				tURNChange=ontologyChangedURI+"?";
			}
			tURNChange=tURNChange+"changeSpecification";
			OntologyProperty tPropURN = new OntologyProperty(Constants.URI, tURNChange);
			tProperties.add(tPropURN);
		}
		return tProperties;
	}
	
						
}