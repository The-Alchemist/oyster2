package org.neontoolkit.changelogging.core.flogic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.neontoolkit.changelogging.core.Constants;
import org.semanticweb.kaon2.api.Ontology;
import org.semanticweb.kaon2.api.BulkChangeEvent.BulkChangeType;
import org.semanticweb.kaon2.api.OntologyChangeEvent.ChangeType;
import org.semanticweb.kaon2.api.logic.Literal;
import org.semanticweb.kaon2.api.logic.Predicate;
import org.semanticweb.kaon2.api.logic.Term;

public class ChangeLogger {
	private List<ChangeLogEntry> oldLogEntries;
	private List<ChangeLogEntry> logEntries;
	private Ontology monitoredOnto;
	private File logFile = null;
	
	public ChangeLogger(Ontology monitoredOnto) throws Exception{
		this.monitoredOnto = monitoredOnto;
		oldLogEntries = new ArrayList<ChangeLogEntry>();
		logEntries = new ArrayList<ChangeLogEntry>();
		init();
	}
	
	@SuppressWarnings("unchecked")
	private void init() throws IOException, JDOMException{
		File rootDir = new File(Constants.logFolder);
		if(!rootDir.exists()){
			boolean flag = rootDir.mkdir();
			System.out.println(flag);
		}
			
		String ontoURI = monitoredOnto.getOntologyURI();
		logFile = new File(Constants.logFolder + regular(ontoURI) + ".xml");
		if(!logFile.exists()){	// create a new log file
			logFile.createNewFile();
			Element rootElement = new Element(Constants.XML_ROOT);
			Attribute attrURI = new Attribute(Constants.ATTR_ONTO, 
					ontoURI);
			rootElement.setAttribute(attrURI);
			Document doc = new Document(rootElement);
	        XMLOutputter xo = new XMLOutputter(Format.getCompactFormat());  
	        FileWriter fw = new FileWriter(logFile);
	        xo.output(doc,fw);  
	        fw.close();
		}else{					//read old log info
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(logFile);
			Element root = doc.getRootElement();
			List children = root.getChildren();
			for(Object obj : children){
				Element entry = (Element)obj;
				String cType = null;
				if(entry.getAttribute(Constants.ATTR_TYPE).getValue().equals(ChangeType.ADD.toString()))
					cType = ChangeType.ADD.toString();
				else
					cType = ChangeType.REMOVE.toString();
				String pred = entry.getAttribute(Constants.ATTR_PREDICATE).getValue();
				String module = entry.getAttribute(Constants.ATTR_MODULE).getValue();
				String timeStamp = entry.getAttribute(Constants.ATTR_TIME).getValue();
				List<String> args = new ArrayList<String>();
				List argList = entry.getChildren();
				for(Object arg : argList){
					int index = Integer.parseInt(((Element)arg).getAttribute(Constants.ATTR_INDEX).getValue());
					args.add(index, ((Element)arg).getValue());
				}
				oldLogEntries.add(
						new ChangeLogEntry(cType, pred, module, args.toArray(new String[]{}), timeStamp));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void add(Object changeType, Object event){
		if(changeType instanceof ChangeType){
			Literal lit = (Literal) event;
			Term module = lit.getModule();
			Term[] args = lit.getArguments();
			Predicate pred = lit.getPredicate();			
	
			List<String> sArgs = new ArrayList<String>();
			for(int i=0; i<args.length; i++)
				sArgs.add(args[i].toString());
			logEntries.add(new ChangeLogEntry(
					changeType.toString(), pred.toString(), module.toString(), 
					sArgs.toArray(new String[]{}), null));
		}else if(changeType instanceof BulkChangeType){
			List eventList = (List) event;
			for(int i=0; i<eventList.size(); i++){				
				Literal lit=(Literal)eventList.get(i);
				Term module = lit.getModule();
				Term[] args = lit.getArguments();
				Predicate pred = lit.getPredicate();			
		
				List<String> sArgs = new ArrayList<String>();
				for(int j=0; j<args.length; j++)
					sArgs.add(args[j].toString());
				logEntries.add(new ChangeLogEntry(
						changeType.toString(), pred.toString(), module.toString(), 
						sArgs.toArray(new String[]{}), null));
			}
		}

	}
	
	public void persist() throws JDOMException, IOException{
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(logFile);
		Element root = doc.getRootElement();
		
		for(int i=0; i<logEntries.size(); i++){// write every log entry into the log file
			Element entry = new Element(Constants.ELEM_ENTRY);
			Attribute attrType = new Attribute(Constants.ATTR_TYPE, 
					logEntries.get(i).getType().toString());
			entry.setAttribute(attrType);
			Attribute attrModule = new Attribute(Constants.ATTR_MODULE, 
					logEntries.get(i).getModule().toString());
			entry.setAttribute(attrModule);
			Attribute attrPred = new Attribute(Constants.ATTR_PREDICATE, 
					logEntries.get(i).getPredicate().toString());
			entry.setAttribute(attrPred);
			Attribute attrTime = new Attribute(Constants.ATTR_TIME, 
					logEntries.get(i).getTimeStamp());
			entry.setAttribute(attrTime);
			
			String[] args = logEntries.get(i).getArguments();
			for(int j=0; j<args.length; j++){
				Element argElem = new Element(Constants.ELEM_ARG);
				argElem.addContent(args[j]);
				Attribute argAttr = new Attribute(Constants.ATTR_INDEX, String.valueOf(j));
				argElem.setAttribute(argAttr);
				entry.addContent(argElem);
			}
			
			root.addContent(entry);			
		}
		XMLOutputter xo = new XMLOutputter(Format.getCompactFormat());  
        FileWriter fw = new FileWriter(logFile);
        xo.output(doc,fw);  
        fw.close();
	}
	
    private static String regular(String string) {
		string=string.replace("/", "(sl)");
		string=string.replace("\\", "(bsl)");
		string=string.replace(":", "(co)");
		string=string.replace("*", "(st)");
		string=string.replace("?", "(qu)");
		string=string.replace("\"", "(ci)");
		string=string.replace("<", "(le)");
		string=string.replace(">", "(mo)");
		string=string.replace("|", "(or)");
		return string;
	}
    
    public List<ChangeLogEntry> getOldLogEntries(){
    	return this.oldLogEntries;
    }
    
    public List<ChangeLogEntry> getLogEntries(){
    	return this.logEntries;
    }

}
