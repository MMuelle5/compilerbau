package ch.mm.v1.scanner.base;

/**
 * Vom Scanner generiertes Objekt, mit einer Id, dem Typen des Items und optional einem Value
 * @author MARIUS
 *
 */
public class Item {

	private int id; //id des Var-Objects
	private int sym; //SymbolId von Constants
	private Object val; //value
	
	public Item(int id, int sym, Object val) {
		super();
		this.id = id;
		this.sym = sym;
		this.val = val;
	}

	public Item(int id, int sym) {
		super();
		this.id = id;
		this.sym = sym;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Object getVal() {
		return val;
	}
	public void setVal(Object val) {
		this.val = val;
	}

	public int getSym() {
		return sym;
	}

	public void setSym(int sym) {
		this.sym = sym;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id: ").append(id).append("; Sym: ").append(sym).append("; Val: ").append(val);
		
		return sb.toString();
	}
}
