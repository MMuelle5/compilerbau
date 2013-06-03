package ch.mm.v1.parser.base;

import ch.mm.v1.codeGenerator.GenerateForSpim;
import ch.mm.v1.codeGenerator.ObjectInfo;
import ch.mm.v1.parser.commants.Expression;
import ch.mm.v1.parser.commants.JumpInstruction;
import ch.mm.v1.parser.commants.ObjParser;
import ch.mm.v1.parser.commants.SystemCommands;
import ch.mm.v1.scanner.base.Constants;
import ch.mm.v1.scanner.base.Item;
import ch.mm.v1.scanner.scan.Scanner;

/**
 * Erster Aufruf der Run Methode
 * @author MARIUS
 *
 */
public class GenerateBaseCommants {

	public final static String DATA = ".data";
	public final static String TEXT = ".text";

	private static boolean isInIf;
	private static String readerName;
	
	/**
	 * Handelt die Klasse ab
	 * Die Klasse kann n Methoden beinhalten und ruft diese der Reihe nach auf
	 */
	public static void parseClass() {
		
		Item i = null;

		GenerateForSpim.init();
		
		while(i == null || i.getSym() != Constants.LBRACE){
			i = Scanner.get();
		}
		
		GenerateBaseCommants.parseMehtod(Scanner.get());
		
		GenerateForSpim.finish();
	}

	/**
	 * Handelt eine komplette Methode ab
	 * @param item
	 */
	public static void parseMehtod(Item item) {
		Item i = item;
		
		while(i == null || (i.getSym() != Constants.INDENT && i.getSym() != Constants.METHOD)){
			i = Scanner.get();
		}
		
		ObjectInfo oi = new ObjectInfo();
		oi.setType(Constants.METHOD);
		oi.setName(String.valueOf(i.getVal()));

		GenerateForSpim.setWorkingMehtod(String.valueOf(i.getVal()));
		GenerateForSpim.initMehtod(oi);
		
		while(i == null || i.getSym() != Constants.LBRACE){
			i = Scanner.get();
		}
		
		GenerateBaseCommants.nextStep();
		
		if(!GenerateForSpim.DEFAULT_METHOD.equals(oi.getName())) {
			GenerateForSpim.endNonMainMethod();
		}
		
		i = Scanner.get();
		
		while(i == null){
			i = Scanner.get();
		}
		if(i == null || i.getSym() != Constants.RBRACE) {
			parseMehtod(i);
		}
	}

	/**
	 * Handelt einen separaten Bereich ab.
	 * Endet jeweils mit einem {
	 * Dies kann entweder von einer Schleife, einer if/else-Bedingung oder einem Ende der Methode stammen
	 */
	public static void nextStep() {
		Item i = Scanner.get();
		
		if(i != null) {
			if(isInIf && i.getSym() != Constants.IF && i.getSym() != Constants.ELSEIF && i.getSym() != Constants.ELSE) {
				isInIf = JumpInstruction.setContinueMethod();
				
			}
			if(i.getSym() == Constants.LPAREN) {
				Expression.startExpression(i);
			}
			else if(i.getSym() == Constants.SYSOUT || i.getSym() == Constants.SYSOUTLINE) {
				SystemCommands.sysout(i);
			}
			else if(i.getSym() == Constants.NUMBER || i.getSym() == Constants.STRING || i.getSym() == Constants.BOOLEAN) {
				ObjParser.setObject(i, readerName);
			}
			else if(i.getSym() == Constants.INDENT) {
				ObjParser.allocateObject(i, readerName);
			}
			else if(i.getSym() == Constants.RBRACE) {
				return;
			}
			else if(i.getSym() == Constants.IF) {
				JumpInstruction.ifInstruction(i);
				isInIf = true;
			}
			else if(i.getSym() == Constants.ELSEIF || i.getSym() == Constants.ELSE) {
				JumpInstruction.elseIfConstruction(i);
				isInIf = true;
			}
			else if(i.getSym() == Constants.WHILE) {
				JumpInstruction.whileLoop(i);
			}
			else if(i.getSym() == Constants.SYSREADER) {
				readerName = SystemCommands.initSysReader(i);
			}
		}
		
		if(i == null || i.getSym() != Constants.RBRACE) {
			GenerateBaseCommants.nextStep();
		}
	}
}
