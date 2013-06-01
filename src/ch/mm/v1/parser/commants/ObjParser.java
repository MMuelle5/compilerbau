package ch.mm.v1.parser.commants;

import ch.mm.v1.codeGenerator.GenerateForSpim;
import ch.mm.v1.codeGenerator.ObjectInfo;
import ch.mm.v1.scanner.base.Constants;
import ch.mm.v1.scanner.base.Item;
import ch.mm.v1.scanner.scan.Scanner;

/**
 * Generiert ein Objekt
 *  TODO ev. nicht- falls das Objekt mit einer Konstante initialisiert wird --> Objekt wird direkt so angelegt
 * - falls das Objekt mit dem Wert eines anderen Objekts angelegt wird:
 * 		1. Adresse Reservieren
 * 		2. An der korrekten Stelle im Code Wert zuweisen 
 * @author Marius
 *
 */
public class ObjParser {

	public static void setObject(Item i, String readerName) {
		ObjectInfo oi = new ObjectInfo();
		oi.setType(i.getSym());
		
		i = Scanner.get();
		oi.setName(String.valueOf(i.getVal()));
		
		if(oi.getType() == Constants.STRING) {
			if(i == null) {
				
			}
			if(Scanner.get().getSym() == Constants.BECOMES) {
				Item val = Scanner.get();
				if(val.getSym() == Constants.STRCONST) {
					oi.setValue(val.getVal());
				}
				GenerateForSpim.initParam(oi);
			}
		}
		else if(i != null){
			GenerateForSpim.initParam(oi);
			ObjParser.allocateObject(i, readerName);
		}
	}


	/**
	 * allocateObject wird auch bei Funktionsaufrufen aufgerufen
	 * diese muessen dann weitergeleitet werden
	 * @param i
	 * @param readerName 
	 */
	public static void allocateObject(Item i, String readerName) {
		
		ObjectInfo oi = GenerateForSpim.getParams().get(i.getVal());
		Item next = Scanner.get();
		
		if(next.getSym() == Constants.BECOMES) {
			next = Scanner.get();
			if(readerName != null && readerName.equals(next.getVal())) {
				SystemCommands.readConsoleLine(oi);
			}
			else {
				Expression.startExpression(next);
			}
		}
		else if(next.getSym() == Constants.LPAREN) {
			JumpInstruction.callMethod(i);
			return;
		}
		
		GenerateForSpim.saveParam(0, oi);
	}
}
