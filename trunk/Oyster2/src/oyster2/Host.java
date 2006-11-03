package oyster2;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.eclipse.jface.preference.PreferenceStore;

/**
 * This class represents a host of the Oyster2 Network.
 *
 * @author <a href="mailto:Roman Schmidt <Roman.Schmidt@epfl.ch>">Roman Schmidt</a>
 * @version 1.0.0
 */
public abstract class Host {

	/**
	 * The seperator between Internet address and port.
	 */
	private static final String COLON = ":";

	/**
	 * The string of the whole internet address ({GUID}ip:port).
	 */
	private String mAddrString = null;

	/**
	 * The resolved host address.
	 */
	private String mAddrStringResolved = null;

	/**
	 * The internet address.
	 */
	protected InetAddress mNetAddr = null;

	/**
	 * The PGrid facility.
	 */
	private Oyster2 mOyster2 = Oyster2.sharedInstance();
	private PreferenceStore store = mOyster2.getPreferenceStore();

	/**
	 * The listening port of the host.
	 */
	protected int mPort = -1;

	/**
	 * Creates a new dummy host.
	 */
	protected Host() {
	}

	/**
	 * Creates a new host with an address, and ip string.
	 *
	 * @param addr the address of the host.
	 * @param port the port as string of the host.
	 * @throws UnknownHostException if the given address is unknown.
	 */
	public Host(String addr, String port) throws UnknownHostException {
		resolve(addr, port);
	}

	/**
	 * Creates a new host.
	 *
	 * @param netAddr the internet address.
	 * @param port    the port.
	 */
	public Host(InetAddress netAddr, int port) {
		mNetAddr = netAddr;
		mPort = port;
	}

	/**
	 * Tests if the delivered host equals the host.
	 *
	 * @param host the host to compare.
	 * @return <code>true</code> if the hosts are equal, else <code>false</code>.
	 */
	public boolean equals(Host host) {
		if (host == null)
			return false;
		byte[] addr1 = mNetAddr.getAddress();
		byte[] addr2 = host.getInetAddress().getAddress();
		if (addr1.length != addr2.length)
			return false;
		for (int i = 0; i < addr1.length; i++)
			if (addr1[i] != addr2[i])
				return false;
		if (mPort != host.getPort())
			return false;
		return true;
	}

	/**
	 * Test if this host is a valid host.
	 *
	 * @return <code>true</code> if valid, <code>false</code> otherwise.
	 */
	public boolean isValid() {
		if ((mNetAddr != null) && (mPort > 0))
			return true;
		else
			return false;
	}

	/**
	 * Resolves the given string to set the host values.
	 *
	 * @param address the host address.
	 * @param port    the host port.
	 * @throws UnknownHostException if the given address is unknown.
	 */
	protected void resolve(String address, String port) throws UnknownHostException {
		//@todo if unknown host => keep the host address anyway
		mNetAddr = InetAddress.getByName(address);
		try {
			mPort = Integer.parseInt(port);
		} catch (NumberFormatException e) {
			mPort = Constants.DEFAULT_PORT;
		}
	}

	/**
	 * Returns a string represantation of this host.
	 *
	 * @return a string.
	 */
	/*public String toString() {
		return getName() + COLON + mPort;
	}*/

	/**
	 * Returns the Internet address.
	 *
	 * @return the Internet address.
	 */
	public String getAddress() {
		if ((store.getString(Constants.PeerRouterIP) != null)
				&& (store.getString(Constants.PeerRouterIP).length() > 0))
			return store.getString(Constants.PeerRouterIP);
		else
			return mNetAddr.getHostAddress();
	}

	/**
	 * Returns the Internet address.
	 *
	 * @return the Internet address.
	 */
	public InetAddress getInetAddress() {
		return mNetAddr;
	}

	/**
	 * Sets the internet address of the host.
	 *
	 * @param netAddr the internet address.
	 */
	public void setInetAddress(InetAddress netAddr) {
		mNetAddr = netAddr;
	}

	/**
	 * Returns the Internet address of the host.
	 *
	 * @return the Internet address of the host.
	 */
	public int getPort() {
		return mPort;
	}

	/**
	 * Sets the listening port.
	 *
	 * @param port the port.
	 */
	public void setPort(int port) {
		mPort = port;
	}

	/**
	 * Returns the Internet name.
	 *
	 * @return the Internet name.
	 */
	public String getName() {
		if (mOyster2.propertyBoolean(Properties.RESOLVE_IP)) {
			if (mAddrStringResolved == null) {
				if (mNetAddr == null) {
					mAddrStringResolved = "0.0.0.0";
				} else {
					mAddrStringResolved = mNetAddr.getCanonicalHostName();
				}
			}
			return mAddrStringResolved;
		} else {
			if (mAddrString == null) {
				if (mNetAddr == null)
					mAddrString = "0.0.0.0";
				else
					mAddrString = mNetAddr.getHostAddress();
			}
			return mAddrString;
		}
	}

}
