package ch.mm.v1.scanner.base;

public class Item {

	private int id; //id des Var-Objects
	private int sym; //SymbolId von Constants
	private Object val; //value
	private int leftVar; //id des linken Var-Objects (-1 fuer keines)
	private int rightVar; //id des rechten Var-Objects (-1 fuer keines)
	private int mode; //modus (Konstante, Variable oder Register)
	
	public Item(int id, int sym, Object val) {
		super();
		this.id = id;
		this.sym = sym;
		this.val = val;
		this.leftVar = -1;
		this.rightVar = -1;
	}
	
	public Item(int id, int sym, Object val, int leftVar, int rightVar) {
		super();
		this.id = id;
		this.sym = sym;
		this.val = val;
		this.leftVar = leftVar;
		this.rightVar = rightVar;
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

	public int getLeftVar() {
		return leftVar;
	}

	public void setLeftVar(int lefVar) {
		this.leftVar = lefVar;
	}

	public int getRightVar() {
		return rightVar;
	}

	public void setRightVar(int rightVar) {
		this.rightVar = rightVar;
	}
		
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id: ").append(id).append("; Sym: ").append(sym).append("; Val: ").append(val).append("; leftVar: ").append(leftVar).append("; rightVar: ").append(rightVar)
		.append("; Mode: ").append(mode);
		
		return sb.toString();
	}
}
