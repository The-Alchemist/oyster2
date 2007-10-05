package org.neon_toolkit.registry.oyster2;

import java.io.Serializable;
import org.xml.sax.helpers.DefaultHandler;


public class Oyster2Query extends DefaultHandler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The finished status (query has been processed).
	 */
	public static final int STATUS_FINISHED = 2;

	/**
	 * The init status (query is created and prepared).
	 */
	public static final int STATUS_INIT = 0;

	/**
	 * The running status (query is processed).
	 */
	public static final int STATUS_RUNNING = 1;
	public static final int TOPIC_QUERY =4;
	public static final int TYPE_QUERY =5;
	public static final int Local_Scope =0;
	public static final int Auto_Scope = 1;
	public static final int Selected_Scope = 2;
	public static final String Local = "LocalSearchScope";
	public static final String Auto = "AutoSearchScope";
	public static final String Select = "SelectedSearchScope";
	public static final String Unknown = "UnknownScope";
	public boolean relatedSearch = true;

	/**
	 * The message id.
	 */
	protected org.neon_toolkit.registry.util.GUID mGUID = null;

	/**
	 * The key (binary represantion of the search string).
	 */
	//protected String mKey = null;


	/**
	 * The search string.
	 */
	protected String mQueryString = null;

	/**
	 * The Scope of search.
	 */
	protected int mScope;

	/**
	 * The status.
	 */
	private int mStatus = STATUS_INIT;
	private int mType;

	/**
	 * Creates a new empty Query.
	 */
	protected Oyster2Query() {
	}

	/**
	 * Creates a new Query for a given search string.
	 *
	 * @param scope    the scope of Query.
	 * @param query    the search string.
	 * 
	 */
	public Oyster2Query(int type,String query) {
		mGUID = new org.neon_toolkit.registry.util.GUID();
		mType = type;
		mQueryString = query;
		relatedSearch = true;
	}
	
	public Oyster2Query(int scope, int type,String query, boolean related ) {
		mGUID = new org.neon_toolkit.registry.util.GUID();
		mScope = scope;
		mType = type;
		mQueryString = query;
		relatedSearch = related;
	}

	/**
	 * Creates a new Query with a given search string, guid.
	 *
	 * @param guid      the Query guid.
	 * @param scope      the type of Query.
	 * @param query     the search string.
	 */
	public Oyster2Query(org.neon_toolkit.registry.util.GUID guid, int type, String query) {
		mGUID = guid;
		mType = type;
		mQueryString = query;
	}

	/**
	 * Returns the message id.
	 *
	 * @return the message id.
	 */
	public org.neon_toolkit.registry.util.GUID getGUID() {
		return mGUID;
	}

	
	/**
	 * Returns the search string.
	 *
	 * @return the search string.
	 */
	public String getQueryString() {
		return (mQueryString != null ? mQueryString : "");
	}

	/**
	 * Returns the type.
	 *
	 * @return the type.
	 */
	public int getType() {
		return mType;
	}
 
	/**
	 * Sets the type.
	 *
	 * @param type of type.
	 */
	public void setType(int type) {
		mType = type;
	}
	
	/**
	 * Returns the status.
	 *
	 * @return the status.
	 */
	public int getStatus() {
		return mStatus;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the status.
	 */
	public void setStatus(int status) {
		mStatus = status;
	}

	/**
	 * Returns the query scope.
	 *
	 * @return the scope.
	 */
	public int getScope() {
		return mScope;
	}

	/**
	 * Returns a string representation of the type.
	 *
	 * @return the type as string.
	 */
	public String getTypeString() {
		if (mScope==0)
			return Local;
		else if(mScope == 1)
			return Auto;
		else if(mScope == 2)
			return Select;
		else return Unknown;
	}
	public boolean getRelatedResult(){
		return relatedSearch;
	}

	/**
	 * Sets the message guid.
	 *
	 * @param guid the message guid.
	 */
	public void setGUID(org.neon_toolkit.registry.util.GUID guid) {
		mGUID = guid;
	}

	

	/**
	 * Sets the scope.
	 *
	 * @param  the scope.
	 */
	public void setScope(int scope) {
		mScope = scope;
	}

	/**
	 * Sets the search string.
	 *
	 * @param query the search string.
	 */
	public void setQueryString(String query) {
		mQueryString = query;
	}

}
