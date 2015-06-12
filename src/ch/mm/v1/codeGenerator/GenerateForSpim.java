package ch.mm.v1.codeGenerator;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.mm.v1.scanner.base.Constants;
import ch.mm.v1.scanner.base.Item;

/**
 * Backend bereich vom Compiler
 * Generiert, aufgrund den vom Parser verarbeiteten Informationen, Assembler-Code
 * @author MARIUS
 *
 */
public class GenerateForSpim{

	public static final String DEFAULT_METHOD = "main";
	public static final int A0 = 0;
	public static final int A1 = 1;
	public static final int A2 = 2;
	public static final int IFELSE_REG = 16;
	private static final int INTERN_MV_REG = 25;
	
	private static Map<String, ObjectInfo> params = new HashMap<String, ObjectInfo>();
	private static List<String> commands = new ArrayList<String>();
	private static Map<String, List<String>> commandMap = new HashMap<String, List<String>>();
	private static String workingMehtod = DEFAULT_METHOD;

	private static int workingTypeA0 = 0;
	
	public static void op2(Operation oper) {
		Operation x = oper.getSubOperX();
		Operation y = oper.getSubOperY();

		if(x != null && x.getSubOperX() != null) {
			GenerateForSpim.op2(x);
			GenerateForSpim.op2(y);
		}
		if(x != null && x.getItem() != null) {
			if(oper.getOperator() == Constants.NULL) { //Nur eine Zuweisung, Wert wird in $a0 geschrieben um damit weiter zu arbeiten
				GenerateForSpim.loadIntoWorkingReg(0, x.getItem());
			}
			else {
				GenerateForSpim.loadIntoWorkingReg(1, x.getItem());
				oper.setSubOperX(null);
			}
		}
		if(y != null && y.getItem() != null) {
			GenerateForSpim.loadIntoWorkingReg(2, oper.getSubOperY().getItem());
			oper.setSubOperY(null);
		}
		
		String command = null;
		switch(oper.getOperator()) {
		case Constants.PLUS:
			command = "add";
			break;
		case Constants.MINUS:
			GenerateForSpim.setCommand("neg $a2, $a2");
			command = "add";
			break;
		case Constants.EQL:
			command = "seq";
			break;
		case Constants.NEQ:
			command = "sne";
			break;
		case Constants.GTR:
			command = "sgt";
			break;
		case Constants.GEQ:
			command = "sge";
			break;
		case Constants.LEQ:
			command = "sle";
			break;
		case Constants.LSS:
			command = "slt";
			break;
		case Constants.TIMES:
			command = "mul";
			break;
		case Constants.DIV:
			command = "div";
			break;
		}
		if(command != null) {
			GenerateForSpim.setCommand(command+" $a0, $a1, $a2");
			workingTypeA0 = Constants.NUMBER;
		}
	}
	
	public static void opSysout(boolean isLinePrint) {
		if(workingTypeA0 == Constants.STRING || workingTypeA0 == Constants.STRCONST) {
			GenerateForSpim.setCommand("li $v0, 4");
		}
		else {
			GenerateForSpim.setCommand("li $v0, 1");
		}
		GenerateForSpim.setCommand("syscall");
		
		if(isLinePrint) {
			GenerateForSpim.moveRegister(25, 0);
			GenerateForSpim.setCommand("la $a0, nl");
			GenerateForSpim.setCommand("li $v0, 4");
			GenerateForSpim.setCommand("syscall");
			GenerateForSpim.moveRegister(0, 25);
		}
	}
		
	public static void moveRegister(int destReg, int srcReg) {
		GenerateForSpim.setCommand("move "+GenerateForSpim.getReg(destReg)+", "+GenerateForSpim.getReg(srcReg));
	}

	/**
	 * 1. destReg zuruecksetzen
	 * 2. mittels add einen copy machen
	 * @param destReg
	 * @param srcReg
	 */
	public static void copyRegister(int destReg, int srcReg) {
		GenerateForSpim.setCommand("la "+GenerateForSpim.getReg(destReg)+", 0");
		GenerateForSpim.setCommand("add "+GenerateForSpim.getReg(destReg)+", "+GenerateForSpim.getReg(destReg)+", "+GenerateForSpim.getReg(srcReg));
	}
	
	public static void loadIntoWorkingReg(int regNo, Item i) {
		if(i.getSym() == Constants.INTCONST) {
			GenerateForSpim.setCommand("la "+GenerateForSpim.getReg(regNo)+", "+i.getVal());
		}
		else if(i.getSym() == Constants.INDENT) {
			GenerateForSpim.loadParam(regNo, GenerateForSpim.params.get(i.getVal()));
		}
	}
	
	/**
	 * 	la $t0, prompt     
		la $t1, another    
		lw $t2, 0($t1)   
		sw $t2, 0($t0)  
	 */
	public static void loadAdress(int regNo, String adress) {
		GenerateForSpim.setCommand("la "+GenerateForSpim.getReg(regNo)+", "+adress);
		ObjectInfo oi = GenerateForSpim.params.get(adress);
		if(regNo == 0 && oi != null) {
			workingTypeA0 = oi.getType();
		}
	}
	
	public static void loadParam(int regNo, ObjectInfo oi) {
		
		if(oi.getType() == Constants.STRING) {
			GenerateForSpim.setCommand("la "+GenerateForSpim.getReg(regNo)+", "+oi.getAdress());
		}
		else if(oi.getType() == Constants.NUMBER || oi.getType() == Constants.BOOLEAN) {
			GenerateForSpim.setCommand("lw "+GenerateForSpim.getReg(regNo)+", "+oi.getAdress());
		}
		
		if(regNo == 0) {
			workingTypeA0 = oi.getType();
		}
	}
	public static void setRegister(int regNo, ObjectInfo oi) {
		if(oi.getType() == Constants.INTCONST) {
			GenerateForSpim.setCommand("la "+GenerateForSpim.getReg(regNo)+", "+oi.getValue());
		}
		else {
			GenerateForSpim.loadParam(regNo, oi);
		}
	}
	
	public static void saveParam(int regNoToSave, ObjectInfo dest) {
		GenerateForSpim.setCommand("la "+getReg(INTERN_MV_REG)+", "+dest.getAdress());
		GenerateForSpim.setCommand("sw "+getReg(regNoToSave)+", 0("+getReg(GenerateForSpim.INTERN_MV_REG)+")");
	}
	
	/**
	 * JumpIfTrue wird benoetigt da:
	 * 	-bei Loop sollte bei zutreffendem Kriterium nicht gesprungen werden
	 *  -bei If sollte bei zutreffendem Kriterium gesprungen werden
	 * @param destName
	 * @param jumpIfTrue
	 */
	public static void bedJump(String destName, boolean jumpIfTrue) {
		//falls die Methode erst spaeter definiert wird, muss der Parameter trotzdem erstellt werden
		if(params.get(destName)== null) {
			ObjectInfo oi = new ObjectInfo();
			oi.setAdress(destName);
			oi.setName(destName);
			params.put(oi.getName(), oi);
		}
			
		if(jumpIfTrue) {
			GenerateForSpim.setCommand("beq "+GenerateForSpim.getReg(A0)+", 1,"+params.get(destName).getAdress());
		}
		else {
			GenerateForSpim.setCommand("beqz "+GenerateForSpim.getReg(A0)+", "+params.get(destName).getAdress());
			
		}
	}
	
	public static void jumpTo(String destAdr) {
		GenerateForSpim.setCommand("jal "+destAdr);
	}
	
	public static void initParam(ObjectInfo oi) {
		oi.setAdress(oi.getName()+""+oi.getType());
		if(params.get(oi.getName()) == null) {
			if(oi.getType() == Constants.STRING) {
				String value = oi.getValue() != null ? String.valueOf(oi.getValue()) : "";
				GenerateForSpim.setCommand(0, oi.getAdress()+": .asciiz \""+value+"\"");
			}
			else if(oi.getType() == Constants.NUMBER || oi.getType() == Constants.BOOLEAN){
				String value = oi.getValue() != null ? String.valueOf(oi.getValue()) : "0";
				GenerateForSpim.setCommand(0, oi.getAdress()+": .word "+value);
			}
			params.put(oi.getName(), oi);
		}
	}
	/**
	 * Wird bei if/else bedingungen benoetigt um mit dem urspruehnglichen code fortzufahren
	 * @param oi
	 */
	public static void initInnerMethod(String methodName) {
		GenerateForSpim.setCommand(methodName+":");
		if(params.get(methodName) == null) {
			ObjectInfo oi = new ObjectInfo();
			oi.setAdress(methodName);
			oi.setName(methodName);
			params.put(methodName, oi);
		}
	}
	
	public static void initMehtod(ObjectInfo oi) {
		if(params.get(oi.getName()) == null) {
			oi.setAdress(oi.getName());
			params.put(oi.getName(), oi);
			GenerateForSpim.setCommand(oi.getName()+":");
		}
		else {
			throw new RuntimeException("Param/Method"+oi.getName()+" already exists");
		}
	}
	
	public static Map<String, ObjectInfo> getParams() {
		return params;
	}

	public static List<String> getCommands() {
		return commands;
	}

	public static Map<String, List<String>> getCommandMap() {
		return commandMap;
	}

	public static void setCommandMap(Map<String, List<String>> commandMap) {
		GenerateForSpim.commandMap = commandMap;
	}

	private static void setCommand(String s) {
		commandMap.get(workingMehtod).add(s);
	}
	
	private static void setCommand(int pos, String s) {
		if(pos == 0) {
			commandMap.get(DEFAULT_METHOD).add(pos, s);
		}
		else {
			commandMap.get(workingMehtod).add(s);
		}
	}
	
	public static void setWorkingMehtod(String s) {
		GenerateForSpim.workingMehtod = s;
		if(commandMap.get(s) == null) {
			commandMap.put(s, new ArrayList<String>());
		}
	}

	public static String getWorkingMehtod() {
		return workingMehtod;
	}
	
	public static void init() {
		GenerateForSpim.setWorkingMehtod(GenerateForSpim.DEFAULT_METHOD);
		GenerateForSpim.getCommandMap().put(GenerateForSpim.DEFAULT_METHOD, new ArrayList<String>());
		GenerateForSpim.setCommand(".text");
		
	}
	
	public static void finish() {
		GenerateForSpim.setWorkingMehtod(GenerateForSpim.DEFAULT_METHOD);
		GenerateForSpim.setCommand(0, "inputStr: .space 80");
		GenerateForSpim.setCommand(0, "nl:  .asciiz \"\\n\"");
		GenerateForSpim.setCommand(0, ".data");
		GenerateForSpim.setCommand("li $v0, 10");
		GenerateForSpim.setCommand("syscall");
	}


	/**
	 * Ruecksprung zum Schluss definieren
	 */
	public static void endNonMainMethod() {
		GenerateForSpim.setCommand("jr $ra");
	}

	public static void readConsoleLine(ObjectInfo oi) {
		
		if(oi.getType() == Constants.STRING) {
			GenerateForSpim.setCommand("li $v0, 8");
		}
		else {
			GenerateForSpim.setCommand("li $v0, 5");
		}
		GenerateForSpim.setCommand("la "+GenerateForSpim.getReg(GenerateForSpim.A0)+", inputStr");
		GenerateForSpim.setCommand("syscall");
		GenerateForSpim.setCommand("move $a0, $v0");
	}
	
	private static String getReg(int regNo) {
		
		switch(regNo) {
		case GenerateForSpim.A0: return "$a0";
		case GenerateForSpim.A1: return "$a1";
		case GenerateForSpim.A2: return "$a2";
		case GenerateForSpim.IFELSE_REG: return "$s0"; //boolean Values für if/else
		case GenerateForSpim.INTERN_MV_REG: return "$t9"; //tmp save, sollte nur intern verwendet werden und nicht von aussen angesprochen werden
		}
		return null;
	}

}
