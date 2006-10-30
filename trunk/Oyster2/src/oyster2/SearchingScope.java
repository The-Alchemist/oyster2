package oyster2;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import oyster2.*;
import util.GUID;


public class SearchingScope implements Serializable {
	 
	  static final long serialVersionUID = -14399419759829923L;
	  private static Oyster2 mOyster2 = Oyster2.sharedInstance();
	  private Type type;
	  private Set selectedPeerIds = new HashSet();

	  public static SearchingScope local() {
	    SearchingScope scope = new SearchingScope(Type.LOCAL);
	    scope.selectedPeerIds.add(mOyster2.getLocalHost().getGUID());
	    return scope;
	  }
	  public static SearchingScope auto() {
	    return new SearchingScope(Type.AUTO);
	  }
	  
	  
	  private SearchingScope(Type type) {
	    this.type = type;
	  }
	  public Type getType() {
	    return type;
	  }
	  
	  
	  public static class Type implements Serializable {

	    static final long serialVersionUID = -14399419759829923L;
	    
	    public static final Type LOCAL = new Type("Local"); // only local search
	    public static final Type MANUAL = new Type("Manual"); // manual peer selection 
	    public static final Type AUTO = new Type("Automatic");  // delegates to PeerSelector   
	    
	    private String name;

	    private Type(String name) {
	      this.name = name;
	    }
	    
	    public String getName() {
	      return name;
	    }
	    
	    public boolean equals(Object type) {
	      if (type instanceof Type) {
	        return ((Type) type).name.equals(name);
	      } else {
	        return false;
	      }
	    }
	    
	    public int hashCode() {
	      return name.hashCode();
	    }
	    
	    public Object readResolve() throws ObjectStreamException {
	      if (name.equals(LOCAL.getName())) {
	        return LOCAL;
	      } else if (name.equals(MANUAL.getName())) {
	        return MANUAL;
	      } else if (name.equals(AUTO.getName())) {
	        return AUTO;
	      }
	      throw new ObjectStreamException(SearchingScope.Type.class.getName()) {};
	    }
	  }
	  
	}

