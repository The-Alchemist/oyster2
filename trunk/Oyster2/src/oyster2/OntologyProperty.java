package oyster2;

public class OntologyProperty {
	private String name;
	private String value;
	public OntologyProperty(String name,String value){
		this.name = name;
		this.value = value;
		
	}
	public String getPropertyName(){
		return name;
	}
	public String getPropertyValue(){
		return value;
	}
	public void setPropertyName(String name){
		this.name = name;
	}
	public void setPropertyValue(String value){
		this.value = value;
	}

}
