package ch.mm.v1.parser.commants;

import ch.mm.v1.codeGenerator.GenerateForSpim;
import ch.mm.v1.codeGenerator.ObjectInfo;
import ch.mm.v1.parser.base.GenerateBaseCommants;
import ch.mm.v1.scanner.base.Constants;
import ch.mm.v1.scanner.base.Item;
import ch.mm.v1.scanner.scan.Scanner;

/**
 * Handelt die bedingten (if/else if/else), als auch die unbedingten Spruenge(Methodenaufrufe) ab
 * Ebenfalls wird die While-Loop in dieser Klasse abgehandelt
 * 		diese beinhaltet sowohl einen unbedingten Sprung (vom Ende der Schleife zum Anfang)
 * 		als auch einen bedingten (Abbruchbedingung)
 * @author MARIUS
 *
 */
public class JumpInstruction {

	private static String continueMehtod;
	private static String realMehtod;
	
	public static void ifInstruction(Item i) {

		continueMehtod = "cont"+i.getId();
		realMehtod = GenerateForSpim.getWorkingMehtod();
		
		ObjectInfo oi = new ObjectInfo();
		oi.setType(Constants.INTCONST);
		oi.setValue(0);
		
		elseIfConstruction(i);

	}
	
	public static void elseIfConstruction(Item i) {

		int type = i.getSym();

		String workingMethod = GenerateForSpim.getWorkingMehtod();
		ObjectInfo oi = new ObjectInfo();
		oi.setType(Constants.METHOD);
		oi.setName("if"+i.getId());
		
		if(i.getSym() != Constants.ELSE) {
			Scanner.get();
			Expression.startExpression(Scanner.get());
		}		

		GenerateForSpim.setWorkingMehtod(String.valueOf(i.getVal()));
		GenerateForSpim.initMehtod(oi);

		while(i == null || i.getSym() != Constants.LBRACE){
			i = Scanner.get();
		}
		GenerateBaseCommants.nextStep();
		
		GenerateForSpim.jumpTo(continueMehtod);

		GenerateForSpim.setWorkingMehtod(workingMethod); //in der Urspruenglichen Mehtode weiterfahren
		if(type == Constants.IF || type == Constants.ELSEIF) {
			GenerateForSpim.bedJump(oi.getName(), true);
		}
		else {
			GenerateForSpim.jumpTo(oi.getName());
		}
	}
	

	public static boolean setContinueMethod() {

		if(GenerateForSpim.getWorkingMehtod().equals(realMehtod)) {
			GenerateForSpim.initInnerMethod(continueMehtod);
			return false;
		}
		
		return true;
	}

	/**
	 * Konstruktion der Loop:
	 * loopStart:
	 * 	bedingter Sprung, wenn Abbruch-Bedingung erfuellt
	 * 	<<Code>>
	 * 	unbedingter Ruecksprung zu loopStart
	 * loopEnde: 
	 * @param i
	 */
	public static void whileLoop(Item i) {

		String mehtodName = "while"+i.getId();
		String endWhile = "endwhile"+i.getId();
		GenerateForSpim.initInnerMethod(mehtodName);

		Scanner.get();
		Expression.startExpression(Scanner.get());
		GenerateForSpim.bedJump(endWhile, false);
		
		while(i == null || i.getSym() != Constants.LBRACE){
			i = Scanner.get();
		}
		
		GenerateBaseCommants.nextStep();
		GenerateForSpim.jumpTo(mehtodName);
		GenerateForSpim.initInnerMethod(endWhile);
	}

	public static void callMethod(Item i) {
		
		GenerateForSpim.jumpTo(String.valueOf(i.getVal()));
	}
	
}
