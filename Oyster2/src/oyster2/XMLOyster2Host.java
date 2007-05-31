package oyster2;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class XMLOyster2Host extends Oyster2Host implements XMLizable {

	/**
	 * A part of the XML string.
	 */
	public static final String XML_HOST = "Host";

	/**
	 * A part of the XML string.
	 */
	private static final String XML_HOST_ADDRESS = "Address";

	/**
	 * A part of the XML string.
	 */
	private static final String XML_HOST_GUID = "GUID";

	/**
	 * A part of the XML string.
	 */
	static final String XML_HOST_PATH = "Path";

	/**
	 * A part of the XML string.
	 */
	static final String XML_HOST_PORT = "Port";

	/**
	 * A part of the XML string.
	 */
	static final String XML_HOST_TIMESTAMP = "Timestamp";

	/**
	 * Creates a new host with an address string.
	 */
	public XMLOyster2Host() {
		super();
	}

	/**
	 * Creates a new host with an address string.
	 *
	 * @param addr the address of the host.
	 */
	protected XMLOyster2Host(String addr) {
		super(addr);
	}

	/**
	 * Creates a new host with an GUID, address, and ip string.
	 *
	 * @param guid the guid string of the host.
	 * @param addr the address of the host.
	 * @param port the port as string of the host.
	 * @throws UnknownHostException if the given address is unknown.
	 */
	protected XMLOyster2Host(String guid, String addr, String port) throws UnknownHostException {
		super(guid, addr, port);
	}

	/**
	 * Creates a new host.
	 *
	 * @param netAddr the internet address.
	 * @param port    the port.
	 */
	protected XMLOyster2Host(InetAddress netAddr, int port) {
		super(netAddr, port);
	}

	/**
	 * Creates a new host.
	 *
	 * @param guid    the GUID.
	 * @param netAddr the internet address.
	 * @param port    the port.
	 */
	protected XMLOyster2Host(util.GUID guid, InetAddress netAddr, int port) {
		super(guid, netAddr, port);
	}

	/**
	 * Returns a host for the given GUID.
	 *
	 * @param guid the GUID.
	 * @return the host.
	 */
	public static XMLOyster2Host getHost(util.GUID guid) {
		if (guid == null)
			throw new NullPointerException("GUID is null");
		return (XMLOyster2Host)mHosts.get(guid);
	}

	/**
	 * Returns a host for the given address string.
	 *
	 * @param addr the address string.
	 * @return the host.
	 */
	public static XMLOyster2Host getHost(String addr) {
		// try to resolve GUID
		int guidPos = addr.indexOf(EXCL_MARK);
		XMLOyster2Host host;
		if (guidPos > 0) {
			util.GUID guid = util.GUID.getGUID(addr.substring(0, guidPos));
			host = (XMLOyster2Host)mHosts.get(guid);
			if (host != null) {
				return host;
			}
		}
		host = new XMLOyster2Host(addr);
		mHosts.put(host.getGUID(), host);
		return host;
	}

	/**
	 * Returns a host for the given values.
	 *
	 * @param guid the GUID.
	 * @param addr the internet address.
	 * @param port the port.
	 * @return the created host.
	 */
	public static XMLOyster2Host getHost(String guid, String addr, String port) {
		util.GUID g = util.GUID.getGUID(guid);
		XMLOyster2Host host = (XMLOyster2Host)mHosts.get(util.GUID.getGUID(guid));
		if (host == null) {
			try {
				host = new XMLOyster2Host(guid, addr, port);
			} catch (UnknownHostException e) {
				return null;
			}
			mHosts.put(g, host);
		}
		return host;
	}

	/**
	 * Returns a host for the given values.
	 *
	 * @param netAddr the internet address.
	 * @param port    the port.
	 * @return the created host.
	 */
	public static XMLOyster2Host getHost(InetAddress netAddr, int port) {
		XMLOyster2Host host = new XMLOyster2Host(netAddr, port);
		if (host.isValid())
			mHosts.put(host.getGUID(), host);
		return host;
	}

	/**
	 * Returns a host for the given values.
	 *
	 * @param guid    the GUID.
	 * @param netAddr the internet address.
	 * @param port    the port.
	 * @return the created host.
	 */
	public static XMLOyster2Host getHost(util.GUID guid, InetAddress netAddr, int port) {
		XMLOyster2Host host = (XMLOyster2Host)mHosts.get(guid);
		if (host == null) {
			host = new XMLOyster2Host(guid, netAddr, port);
			mHosts.put(guid, host);
		}
		return host;
	}

	/**
	 * The Parser will invoke this method at the beginning of every element in the XML document; there will be a
	 * corresponding endElement event for every startElement event (even when the element is empty). All of the element's
	 * content will be reported, in order, before the corresponding endElement event.
	 *
	 * @param qName the qualified name (with prefix), or the empty string if qualified names are not available.
	 * @param attrs the attributes attached to the element. If there are no attributes, it shall be an empty Attributes
	 *              object.
	 * @return a XMLPGridHost for the given XML string.
	 */
	public static XMLOyster2Host getHost(String qName, Attributes attrs) {
		return getHost(qName, attrs, true);
	}

	/**
	 * The Parser will invoke this method at the beginning of every element in the XML document; there will be a
	 * corresponding endElement event for every startElement event (even when the element is empty). All of the element's
	 * content will be reported, in order, before the corresponding endElement event.
	 *
	 * @param qName          the qualified name (with prefix), or the empty string if qualified names are not available.
	 * @param attrs          the attributes attached to the element. If there are no attributes, it shall be an empty Attributes
	 *                       object.
	 * @param checkTimestamp if the timestamp for a host should be checked.
	 * @return a XMLPGridHost for the given XML string.
	 */
	public static XMLOyster2Host getHost(String qName, Attributes attrs, boolean checkTimestamp) {
		if (qName.equals(XML_HOST)) {
			XMLOyster2Host host = XMLOyster2Host.getHost(attrs.getValue(XML_HOST_GUID), attrs.getValue(XML_HOST_ADDRESS), attrs.getValue(XML_HOST_PORT));
			String path = attrs.getValue(XML_HOST_PATH);
			if (path != null) {
				if (checkTimestamp) {
					String timestamp = attrs.getValue(XML_HOST_TIMESTAMP);
					if (timestamp != null) {
						host.setPath(path, Long.parseLong(timestamp));
					} else {
						
					}
				} else {
				
				}
			}
			return host;
		}
		return null;
	}

	/**
	 * Returns the amount of known hosts.
	 *
	 * @return the amount of known hosts.
	 */
	public static int getHostsCount() {
		return mHosts.size();
	}

	/**
	 * The Parser will call this method to report each chunk of character data. SAX parsers may return all contiguous
	 * character data in a single chunk, or they may split it into several chunks; however, all of the characters in any
	 * single event must come from the same external entity so that the Locator provides useful information.
	 *
	 * @param ch     the characters from the XML document.
	 * @param start  the start position in the array.
	 * @param length the number of characters to read from the array.
	 * @throws SAXException any SAX exception, possibly wrapping another exception.
	 */
	public void characters(char[] ch, int start, int length) throws SAXException {
	}

	/**
	 * The SAX parser will invoke this method at the end of every element in the XML document; there will be a
	 * corresponding startElement event for every endElement event (even when the element is empty).
	 *
	 * @param uri   the Namespace URI.
	 * @param lName the local name (without prefix), or the empty string if Namespace processing is not being performed.
	 * @param qName the qualified name (with prefix), or the empty string if qualified names are not available.
	 * @throws SAXException any SAX exception, possibly wrapping another exception.
	 */
	public void endElement(String uri, String lName, String qName) throws SAXException {
	}

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
	 * @throws SAXException any SAX exception, possibly wrapping another exception.
	 */
	public void startElement(String uri, String lName, String qName, Attributes attrs) throws SAXException {
		if (qName.equals(XML_HOST)) {
			try {
				super.resolve(attrs.getValue(XML_HOST_GUID), attrs.getValue(XML_HOST_ADDRESS), attrs.getValue(XML_HOST_PORT));
			} catch (UnknownHostException e) {
				// unknown host
			}
			String path = attrs.getValue(XML_HOST_PATH);
			String timestamp = attrs.getValue(XML_HOST_TIMESTAMP);
			if ((path != null) && (timestamp != null)) {
				setPath(path, Long.parseLong(timestamp));
			}
		}
	}

	/**
	 * Returns the XML representation of this object.
	 *
	 * @return the XML string.
	 */
	public String toXMLString() {
		return toXMLString("", XML_NEW_LINE, false);
	}

	/**
	 * Returns the XML representation of this object.
	 *
	 * @param prefix  the XML prefix before each element in a new line.
	 * @param newLine the new line string.
	 * @return the XML string.
	 */
	public String toXMLString(String prefix, String newLine) {
		return toXMLString(prefix, newLine, false);
	}

	/**
	 * Returns the XML representation of this object.
	 *
	 * @param prefix  the XML prefix before each element in a new line.
	 * @param newLine the new line string.
	 * @param path    if the path and its timestamp should be included.
	 * @return the XML string.
	 */
	public String toXMLString(String prefix, String newLine, boolean path) {
		StringBuffer strBuff = new StringBuffer(100);
		strBuff.append(prefix + XML_ELEMENT_OPEN + XML_HOST + // {prefix}<Host
				XML_SPACE + XML_HOST_GUID + XML_ATTR_OPEN + mGUID.toString() + XML_ATTR_CLOSE + // _GUID="GUID"
				XML_SPACE + XML_HOST_ADDRESS + XML_ATTR_OPEN + getName() + XML_ATTR_CLOSE + // _Address="ADDRESS"
				XML_SPACE + XML_HOST_PORT + XML_ATTR_OPEN + mPort + XML_ATTR_CLOSE); // _Port="PORT"
		if (path) {
			strBuff.append(XML_SPACE + XML_HOST_PATH + XML_ATTR_OPEN + mPath + XML_ATTR_CLOSE + // _Path="PATH"
					XML_SPACE + XML_HOST_TIMESTAMP + XML_ATTR_OPEN + mPathTimestamp + XML_ATTR_CLOSE); // _Timestamp="TIMESTAMP"
		}
		strBuff.append(XML_ELEMENT_END_CLOSE + newLine); // />{newLine}
		return strBuff.toString();
	}

}

