/**
 * 
 */
package org.neontoolkit.oyster2.client.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;



/**
 * @author David Muñoz
 *
 */
public class XMLResourceLoader {

	private static XMLResourceLoader resourceLoader = null;
	
	private XMLInputFactory xmlInputFactory = null;
	
	private OMFactory omFactory = null;
	
	static {
		resourceLoader = new XMLResourceLoader();
	};
	
	public XMLResourceLoader() {
		xmlInputFactory = XMLInputFactory.newInstance();
		omFactory = OMAbstractFactory.getOMFactory();
	}
	/*
	public String getElement(String file,String key)
		throws	XMLStreamException, IOException {
		
		String element = null;
		FileInputStream in = new FileInputStream(file);
		XMLStreamReader parser = xmlInputFactory.createXMLStreamReader(in);
		StAXOMBuilder builder =
		    new StAXOMBuilder(omFactory, parser); 
		OMElement documentElement = builder.getDocumentElement();
		QName elementName = new QName(key);
		Iterator it = documentElement.getChildrenWithName(elementName);
		if (! it.hasNext())
			throw new RuntimeException("Element " + key + " not found " +
					"in file " + file);
		OMElement omElement = (OMElement)it.next();
		
		element = omElement.getText();
		parser.close();
		in.close();
		builder.close();
		
		return element;
	}
	
	public String getAttribute(String file, String key, String attribute)
	throws XMLStreamException, IOException {
		String element = null;
		FileInputStream in = new FileInputStream(file);
		XMLStreamReader parser = xmlInputFactory.createXMLStreamReader(in);
		StAXOMBuilder builder =
		    new StAXOMBuilder(omFactory, parser); 
		builder.releaseParserOnClose(true);
		OMElement documentElement = builder.getDocumentElement();
		QName elementName = new QName(key);
		Iterator it = documentElement.getChildrenWithName(elementName);
		if (! it.hasNext())
			throw new RuntimeException("Element " + key + " not found " +
					"in file " + file);
		OMElement omElement = (OMElement)it.next();
		QName attributeQName = new QName(attribute);
		element = omElement.getAttributeValue(attributeQName);
		
		builder.close();
		return element;
	}
	
	public ArrayList<String> getArray(String file, String key)
	throws XMLStreamException, IOException {
		ArrayList<String> list = new ArrayList<String>();
		FileInputStream in = new FileInputStream(file);
		XMLStreamReader parser = xmlInputFactory.createXMLStreamReader(in);
		StAXOMBuilder builder =
		    new StAXOMBuilder(omFactory, parser); 
		builder.releaseParserOnClose(true);
		OMElement documentElement = builder.getDocumentElement();
		QName elementName = new QName(key);
		Iterator it = documentElement.getChildrenWithName(elementName);
		if (! it.hasNext())
			throw new RuntimeException("Element " + key + " not found " +
					"in file " + file);
		OMElement omElement = (OMElement)it.next();
		OMElement child = null;
		//take the children
		
		it = omElement.getChildren();
		while (it.hasNext()) {
			child = (OMElement)it.next();
			list.add(child.getText());
		}
		
		
		builder.close();
		if (list.size() == 0) {
			throw new RuntimeException("Asked for array of an element " +
					"with no children, at file " + file + " element " +
					key);
		}
		return list;
	}

	public ArrayList<String> getArray(String file)
	throws XMLStreamException, IOException {
		ArrayList<String> list = new ArrayList<String>();
		FileInputStream in = new FileInputStream(file);
		XMLStreamReader parser = xmlInputFactory.createXMLStreamReader(in);
		StAXOMBuilder builder =
		    new StAXOMBuilder(omFactory, parser); 
		builder.releaseParserOnClose(true);
		OMElement documentElement = builder.getDocumentElement();
		
		Iterator it = documentElement.getChildElements();
		if (! it.hasNext())
			throw new RuntimeException("Empty file " + file);
		OMElement omElement = (OMElement)it.next();
		OMElement child = null;
		//take the children
		
		it = omElement.getChildren();
		while (it.hasNext()) {
			child = (OMElement)it.next();
			list.add(child.getText());
		}
		
		builder.close();
		
		return list;
	}
	*/
	public Object open(String file) 
	throws FileNotFoundException, XMLStreamException {
		
		FileInputStream in = new FileInputStream(file);
		XMLStreamReader parser = xmlInputFactory.createXMLStreamReader(in);
		StAXOMBuilder builder =
		    new StAXOMBuilder(omFactory, parser);
		builder.releaseParserOnClose(true);
		return builder;
	}
	
	public void close(Object handler) {
		StAXOMBuilder builder = (StAXOMBuilder)handler;
		builder.close();
	}
	
	
	
	
	public String getElement(Object handler,String key) {
	
	String element = null;
	StAXOMBuilder builder = (StAXOMBuilder)handler;
	
	
	OMElement documentElement = builder.getDocumentElement();
	QName elementName = new QName(key);
	
	
	Iterator it = documentElement.getChildrenWithName(elementName);
	
	if (! (it.hasNext())) {
		throw new RuntimeException("Element " + key + " not found ");
	}
	OMElement omElement = (OMElement)it.next();
	
	element = removeBlanks(omElement.getText());
	
	return element;
}

	public String getAttribute(Object handler, String key, String attribute) {
	String element = null;
	StAXOMBuilder builder = (StAXOMBuilder)handler;
	
	OMElement documentElement = builder.getDocumentElement();
	QName elementName = new QName(key);
	Iterator it = documentElement.getChildrenWithName(elementName);
	
	
	
	if (! it.hasNext())
		throw new RuntimeException("Element " + key + " not found ");
	
	OMElement omElement = (OMElement)it.next();
	QName attributeQName = new QName(attribute);
	element = omElement.getAttributeValue(attributeQName);
	return element;
}

public ArrayList<String> getArray(Object handler, String key) {
	ArrayList<String> list = new ArrayList<String>();
	
	StAXOMBuilder builder = (StAXOMBuilder)handler;
	
	OMElement documentElement = builder.getDocumentElement();
	QName elementName = new QName(key);
	Iterator it = documentElement.getChildrenWithName(elementName);
	if (! it.hasNext())
		throw new RuntimeException("Element " + key + " not found ");
	OMElement omElement = (OMElement)it.next();
	OMElement child = null;
	//take the children
	
	it = omElement.getChildElements();
	while (it.hasNext()) {
		child = (OMElement)it.next();
		list.add(removeBlanks(child.getText()));
	}
	
	if (list.size() == 0) {
		throw new RuntimeException("Asked for array of an element " +
				"with no children, element " +key);
	}
	return list;
}

public ArrayList<String> getArray(Object handler)  {
	StAXOMBuilder builder = (StAXOMBuilder)handler;
	
	ArrayList<String> list = new ArrayList<String>();
		OMElement documentElement = builder.getDocumentElement();
	
	
	OMElement child = null;
	//take the children
	
	Iterator it = documentElement.getChildElements();
	while (it.hasNext()) {
		child = (OMElement)it.next();
		list.add(removeBlanks(child.getText()));
	}
	
	return list;
}
	
	
	protected static String removeBlanks(String text) {
		String cleanString = null;
		int firstNewLine = text.indexOf('\n');
		int firstTab = text.indexOf('\t');
		if ((firstTab == -1) && (firstNewLine == -1)) {
			return text;
		}
		if ( firstNewLine < firstTab) {
			cleanString = text.substring(0,firstNewLine);
		}
		else {
			cleanString = text.substring(0,firstTab);
		}
		return cleanString;
	}
	
	
	
	
	
	
	public boolean hasElement(Object handler,String key) {
	
	StAXOMBuilder builder = (StAXOMBuilder)handler;
	
	OMElement documentElement = builder.getDocumentElement();
	QName elementName = new QName(key);
	
	Iterator it = documentElement.getChildrenWithName(elementName);
	
	if (! (it.hasNext())) {
		return false;
	}
	return true;
	}

	
	
	
	
	/**
	 * @return the resourceLoader
	 */
	public static final XMLResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	/**
	 * @param resourceLoader the resourceLoader to set
	 */
	public static final void setResourceLoader(XMLResourceLoader resourceLoader) {
		XMLResourceLoader.resourceLoader = resourceLoader;
	}
	
	
	
}
