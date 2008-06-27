package org.neontoolkit.registry.oyster2;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public interface XMLizable {

		/**
		 * A part of the XML string.
		 */
		static final String XML_ATTR_OPEN = "=\"";

		/**
		 * A part of the XML string.
		 */
		static final String XML_ATTR_CLOSE = "\"";

		/**
		 * A part of the XML string.
		 */
		static final String XML_CDATA_OPEN = "<![CDATA[";

		/**
		 * A part of the XML string.
		 */
		static final String XML_CDATA_CLOSE = "]]>";

		/**
		 * A part of the XML string.
		 */
		static final String XML_ELEMENT_CLOSE = ">";

		/**
		 * A part of the XML string.
		 */
		static final String XML_ELEMENT_END_CLOSE = "/>";

		/**
		 * A part of the XML string.
		 */
		static final String XML_ELEMENT_OPEN = "<";

		/**
		 * A part of the XML string.
		 */
		static final String XML_ELEMENT_OPEN_END = "</";

		/**
		 * A part of the XML string.
		 */
		static final String XML_NEW_LINE = "\n";

		/**
		 * A part of the XML string.
		 */
		static final String XML_SPACE = " ";

		/**
		 * A part of the XML string.
		 */
		static final String XML_TAB = "\t";

		/**
		 * A part of the XML string.
		 */
		static final String XML_YES = "yes";

		/**
		 * The Parser will call this method to report each chunk of character data. SAX parsers may return all contiguous
		 * character data in a single chunk, or they may split it into several chunks; however, all of the characters in any
		 * single event must come from the same external entity so that the Locator provides useful information.
		 *
		 * @param ch     the characters from the XML document.
		 * @param start  the start position in the array.
		 * @param length the number of characters to read from the array.
		 * @throws org.xml.sax.SAXException any SAX exception, possibly wrapping another exception.
		 */
		public void characters(char[] ch, int start, int length) throws SAXException;

		/**
		 * The SAX parser will invoke this method at the end of every element in the XML document; there will be a
		 * corresponding startElement event for every endElement event (even when the element is empty).
		 *
		 * @param uri   the Namespace URI.
		 * @param lName the local name (without prefix), or the empty string if Namespace processing is not being performed.
		 * @param qName the qualified name (with prefix), or the empty string if qualified names are not available.
		 * @throws SAXException any SAX exception, possibly wrapping another exception.
		 */
		public void endElement(String uri, String lName, String qName) throws SAXException;

		/**
		 * The Parser will invoke this method at the beginning of every element in the XML document; there will be a
		 * corresponding endElement event for every startElement event (even when the element is empty). All of the element's
		 * content will be reported, in order, before the corresponding endElement event.
		 *
		 * @param uri   the Namespace URI.
		 * @param lName the local name (without prefix), or the empty string if Namespace processing is not being performed.
		 * @param qName the qualified name (with prefix), or the empty string if qualified names are not available.
		 * @param attrs the attributes attached to the element. If there are no attributes, it shall be an empty Attributes
		 *              object.
		 * @throws org.xml.sax.SAXException any SAX exception, possibly wrapping another exception.
		 */
		public void startElement(String uri, String lName, String qName, Attributes attrs) throws SAXException;

		/**
		 * Returns the XML representation of this object.
		 *
		 * @return the XML string.
		 */
		public String toXMLString();

		/**
		 * Returns the XML representation of this object.
		 *
		 * @param prefix  the XML prefix before each element in a new line.
		 * @param newLine the new line string.
		 * @return the XML string.
		 */
		public String toXMLString(String prefix, String newLine);



}

