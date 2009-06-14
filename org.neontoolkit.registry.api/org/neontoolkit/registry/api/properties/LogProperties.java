package org.neontoolkit.registry.api.properties;




import java.util.LinkedList;
import java.util.List;
import org.neontoolkit.omv.api.core.OMVOntology;
import org.neontoolkit.omv.api.extensions.change.OMVLog;
import org.neontoolkit.registry.oyster2.Constants;
import org.neontoolkit.registry.oyster2.ImportOntology;
import org.neontoolkit.registry.oyster2.OntologyProperty;

/**
 * The class LogProperties provides the methods to
 * retrieve the properties from OMV Log objects 
 * representing the object of the registry 
 * @author Raul Palma
 * @version 2.0, March 2008
 */
public class LogProperties{
	static private ImportOntology IOntology= new ImportOntology();
	static private String ontologyChangedURI="";
	
	static public LinkedList getLogProperties(OMVLog m){
		//Ignore now axiom annotation
		LinkedList<OntologyProperty> tProperties = new LinkedList<OntologyProperty>();
		List tList = new LinkedList();
		
		if (m.getHasLastChange()!=null) {
			OntologyProperty prop = new OntologyProperty(Constants.hasLastChange, m.getHasLastChange());
			tProperties.addFirst(prop);
		}
		
		if (m.getHasRelatedOntology()!=null) {
			ontologyChangedURI="";
			String tURN;
			OMVOntology otemp = m.getHasRelatedOntology();
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
				OntologyProperty prop = new OntologyProperty(Constants.hasRelatedOntology, ontologyChangedURI);
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
			}else if (ontologyChangedURI.length()>0){
				tURNChange=ontologyChangedURI+"?";
			}
			tURNChange=tURNChange+"log";
			OntologyProperty tPropURN = new OntologyProperty(Constants.URI, tURNChange);
			tProperties.add(tPropURN);
		}
		return tProperties;
	}
	
						
}