package org.neon_toolkit.registry.oyster2;

import java.util.Collection;
import java.util.Vector;

import org.neon_toolkit.registry.util.GUID;
import org.semanticweb.kaon2.api.Ontology;
import org.xml.sax.helpers.DefaultHandler;


public class QueryReply extends DefaultHandler {
    /**
     * The Query reply type inital.
     */
	
    public static final int TYPE_INIT =1;
	/**
	 * The Query reply type Bad Request.
	 */
	public static final int TYPE_BAD_REQUEST = 2;
	
	/**
	 * The Query reply type OK.
	 */
	public static final int TYPE_OK = 0;

	/**
	 * The Query Id.
	 */
	protected org.neon_toolkit.registry.util.GUID mQueryID = null;

	/**
	 * The result set.
	 */
	protected Collection mResultSet = null;
   
	/**
	 * The Query reply.
	 */
	protected int mType = -1;
	/**
	 * The reply entity: normally an instance of one class,such as: an instance of Book.
	 */
	private Collection resourceSet;
	/**
	 * The ontology which contains the reply entity: may we just use virtual ontology,because it imports all the other expertise ontologies.
	 */
    private Ontology ontology;
    /**
     * The property attributes of one reply entity:such as year or author of a book instance.
     */
    //private Collection attributeSet = new LinkedList();

	/**
	 * Creates an empty query reply.
	 */
	public QueryReply() {
	}

	/**
	 * Creates a new query reply with given values.
	 *
	 * @param guid      the GUID of the Query Reply.
	 * @param type      the type of the Query Reply.
	 * @param resultSet the result set of found files.
	 */
	public QueryReply(org.neon_toolkit.registry.util.GUID guid, int type) {
		mQueryID = guid;
		mType = type;
		resourceSet = null;
		ontology = null;
	}
	/**
	 * Creates a new query reply with given values.
	 *
	 * @param entity      the Reply entity.
	 * @param Ontology     the ontology containing reply entity.
	 */
	public QueryReply(GUID guid,Collection entrySet, Ontology ontology){
    	this.mQueryID = guid;
    	this.mType = TYPE_OK;
		this.resourceSet = entrySet;
    	this.ontology = ontology;
    }
	
    public Collection getResourceSet(){
    	return resourceSet;
    }
    
    public Ontology getOntology(){
    	return ontology;
    }
    /*public void addAttribute(String attribute){
    	attributeSet.add(attribute);
    }
    public Collection getAttributeSet(){
    	return attributeSet;
    }*/

	/**
	 * Returns the number of hits.
	 *
	 * @return the number of hits.
	 */
	public int getHits() {
		if (mResultSet == null)
			return 0;
		return mResultSet.size();
	}

	/**
	 * Returns the query id.
	 *
	 * @return the query id.
	 */
	public org.neon_toolkit.registry.util.GUID getGUID() {
		return mQueryID;
	}

	/**
	 * Returns a result of the result set, selected by the index.
	 *
	 * @param index the index of the result in the result set.
	 * @return the result.
	 */
	@SuppressWarnings("unchecked")
	public String getResultOntology(int index) {
		return (String)new Vector(mResultSet).get(index);
	}

	/**
	 * Returns the result set.
	 *
	 * @return the result set.
	 */
	public Collection getResultSet() {
		return mResultSet;
	}

	/**
	 * Returns the query reply type.
	 *
	 * @return the query reply type.
	 */
	public int getType() {
		return mType;
	}
  
}
