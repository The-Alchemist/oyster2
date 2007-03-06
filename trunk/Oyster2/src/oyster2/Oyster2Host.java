package oyster2;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;

import oyster2.Constants;
import oyster2.Host;
import util.GUID;

public class Oyster2Host extends Host{
	/**
	 * The seperator between Internet address and port.
	 */
	protected static final String COLON = ":";

	/**
	 * The seperator between GUID and Internet address.
	 */
	protected static final String EXCL_MARK = "!";

	/**
	 * The string of the whole internet address ({GUID}ip:port).
	 */
	protected String mAddrString = null;

	/**
	 * The resolved host address.
	 */
	protected String mAddrStringResolved = null;

	/**
	 * The number of failed Exchanges.
	 */
	protected long mExchangeFailedCount = 0;

	/**
	 * The number of sucessful Exchanges.
	 */
	protected long mExchangeSuccessCount = 0;

	/**
	 * The time a KaonP2P Exchange can be processed.
	 */
	protected long mExchangeTime = 0;

	/**
	 * The global unique id.
	 */
	protected static GUID mGUID = null;

	/**
	 * The list of already created Hosts.
	 */
	protected static Hashtable mHosts = new Hashtable();

	/**
	 * The path of the host.
	 */
	protected String mPath = "";

	/**
	 * The last time the path of the host was set.
	 */
	protected long mPathTimestamp = 0;

	/**
	 * The PGrid facility.
	 */
	private Oyster2Factory mKaonP2P= Oyster2Factory.sharedInstance();

	/**
	 * Creates a new host.
	 */
	protected Oyster2Host() {
	}

	/**
	 * Creates a new host with an address string.
	 *
	 * @param addr the address of the host.
	 */
	protected Oyster2Host(String addr) {
		mAddrString = addr;
		resolve();
		mAddrString = null;
	}

	/**
	 * Creates a new host with an GUID, address, and ip string.
	 *
	 * @param guid the guid string of the host.
	 * @param addr the address of the host.
	 * @param port the port as string of the host.
	 * @throws UnknownHostException if the given address is unknown.
	 */
	protected Oyster2Host(String guid, String addr, String port) throws UnknownHostException {
		super(addr, port);
		mGUID = GUID.getGUID(guid);
	}

	/**
	 * Creates a new host.
	 *
	 * @param netAddr the internet address.
	 * @param port    the port.
	 */
	protected Oyster2Host(InetAddress netAddr, int port) {
		super(netAddr, port);
		mGUID = GUID.getGUID();
	}

	/**
	 * Creates a new host.
	 *
	 * @param guid    the GUID.
	 * @param netAddr the internet address.
	 * @param port    the port.
	 */
	protected Oyster2Host(GUID guid, InetAddress netAddr, int port) {
		super(netAddr, port);
		if(guid == null)guid = GUID.getGUID();
		mGUID = guid;
		//System.out.println("initialize localhost with GUID:"+mGUID);
	}

	

	/**
	 * Tests if the host can be contacted for an Exchange.
	 *
	 * @return <code>true</code> if allowed, <code>false</code> otherwise.
	 */
	public boolean isExchangeTime() {
		if (mExchangeTime < System.currentTimeMillis())
			return true;
		else
			return false;
	}

	/**
	 * Sets the next time a PGrid Exchange can be processed with this host.
	 *
	 * @param success if the last exchange was successful.
	 */
	/*public void setExchangeTime(boolean success) {
		if (success) {
			mExchangeSuccessCount++;
			mExchangeTime = System.currentTimeMillis() + (mPGrid.propertyInteger(Properties.EXCHANGE_RATE) * mExchangeSuccessCount);
		} else {
			mExchangeFailedCount++;
			int val = (int)Math.round(Math.random() * mPGrid.propertyInteger(Properties.EXCHANGE_RATE));
			mExchangeTime = System.currentTimeMillis() + val + (mPGrid.propertyInteger(Properties.EXCHANGE_RATE) * (mExchangeFailedCount - 1));
		}
	}*/

	/**
	 * Resolves the given string to set the host values.
	 *
	 * @param guid    the GUID.
	 * @param address the host address.
	 * @param port    the host port.
	 * @throws UnknownHostException if the given address is unknown.
	 */
	protected void resolve(String guid, String address, String port) throws UnknownHostException {
		mGUID = GUID.getGUID(guid);
		super.resolve(address, port);
	}

	/**
	 * Resolves the Internet address and port from the Address string string.
	 */
	private void resolve() {
		if (mAddrString == null)
			return;

		// try to resolve GUID
		int guidPos = mAddrString.indexOf(EXCL_MARK);
		if (guidPos > 0) {
			mGUID = GUID.getGUID(mAddrString.substring(0, guidPos));
		}

		// try to resolve IP address and port
		int dotPos = mAddrString.indexOf(COLON, guidPos);
		if (dotPos > 0) {
			try {
				mPort = Integer.parseInt(mAddrString.substring(dotPos + 1, mAddrString.length()));
			} catch (NumberFormatException e) {
				mPort = Constants.DEFAULT_PORT;
			}
		}
		try {
			mNetAddr = InetAddress.getByName(mAddrString.substring(guidPos + 1, dotPos));
		} catch (UnknownHostException e) {
			mNetAddr = null;
		}
	}

	/**
	 * Compares this object with the specified object for order. Returns a negative integer, zero, or a positive integer
	 * as this object is less than, equal to, or greater than the specified object.
	 *
	 * @param obj the Object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the
	 *         specified object.
	 */
	public int compareTo(Object obj) {
		return mGUID.compareTo(((Oyster2Host)obj).getGUID());
	}

	/**
	 * Tests if the delivered host equals the host.
	 *
	 * @param host the host to compare.
	 * @return <code>true</code> if the hosts are equal, else <code>false</code>.
	 */
	public boolean equals(Oyster2Host host) {
		if (!super.equals(host))
			return false;
		if ((mGUID != null) && (host.getGUID() != null))
			return mGUID.equals(host.getGUID());
		return true;
	}

	/**
	 * Test if this host is a valid host.
	 *
	 * @return <code>true</code> if valid, <code>false</code> otherwise.
	 */
	public boolean isValid() {
		if (!super.isValid())
			return false;
		if (mGUID == null)
			return false;
		return true;
	}

	/**
	 * Returns a string represantation of this host.
	 *
	 * @return a string.
	 */
	public String toHostString() {
		return super.toString();
	}

	/**
	 * Returns a string represantation of this host.
	 *
	 * @return a string.
	 */
	/*public String toString() {
		if (mPGrid.propertyBoolean(Properties.RESOLVE_IP)) {
			if (mAddrStringResolved == null) {
				StringBuffer buff = new StringBuffer(70);
				if (mGUID != null) {
					buff.append(mGUID.toString());
					buff.append(EXCL_MARK);
				}
				buff.append(mNetAddr.getCanonicalHostName());
				buff.append(COLON);
				buff.append(mPort);
				mAddrStringResolved = buff.toString();
			}
			return mAddrStringResolved;
		}
		if (mAddrString == null) {
			StringBuffer buff = new StringBuffer(61);
			if (mGUID != null) {
				buff.append(mGUID.toString());
				buff.append(EXCL_MARK);
			}
			buff.append(mNetAddr.getHostAddress());
			buff.append(COLON);
			buff.append(mPort);
			mAddrString = buff.toString();
		}
		return mAddrString;
	}*/

	/**
	 * Returns the GUID of this host.
	 *
	 * @return the GUID of this host.
	 */
	public static GUID getGUID() {
		return mGUID;
	}

	/**
	 * Sets the GUID of this host.
	 *
	 * @param guid the GUID of this host.
	 */
	public void setGUID(GUID guid) {
		mGUID = guid;
	}

	/**
	 * Returns the path of the host.
	 *
	 * @return the path of the host.
	 */
	public String getPath() {
		return mPath;
	}

	
	/**
	 * Sets the path of the host, if the timestamp is later than the current.
	 *
	 * @param path      the path of the host.
	 * @param timestamp the timestamp for the path.
	 */
	public void setPath(String path, long timestamp) {
		if (timestamp >= mPathTimestamp) {
			mPath = path;
			mPathTimestamp = timestamp;
		}
	}
	

	

}
