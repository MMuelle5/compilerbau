package ch.mm.v1.codeGenerator;

/**
 * Wird benötigt bei um Parameter zu einem spaeteren Zeitpunkt wieder zu finden
 * @author MARIUS
 *
 */
public class ObjectInfo {

	private String name;
	private int type;
	private Object value;
	private String adress;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof ObjectInfo) || o == null) {
			return false;
		}
		
		ObjectInfo comp = (ObjectInfo) o;
		if(name != null && name.equals(comp.getName())) { //adress muss Unique sein und SOLLTE !null
			return true;
		}
		
		return false;
	}
}
