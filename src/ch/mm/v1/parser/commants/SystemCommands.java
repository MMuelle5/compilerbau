package ch.mm.v1.parser.commants;

import ch.mm.v1.codeGenerator.GenerateForSpim;
import ch.mm.v1.codeGenerator.ObjectInfo;
import ch.mm.v1.codeGenerator.Operation;
import ch.mm.v1.scanner.base.Constants;
import ch.mm.v1.scanner.base.Item;
import ch.mm.v1.scanner.scan.Scanner;

/**
 * Verantwortlich für die System Kommandos
 * @author MARIUS
 *
 */
public final class SystemCommands {

	/**
	 * Ruft eine Ausgabe auf, der Wert wird aber zuerst mittels startExpression ins Verzeichnis $a0 geladen
	 * @param item
	 */
	public static void sysout(Item item) {

		Operation op = new Operation();
		op.setOperator(item.getSym());
		
		Expression.startExpression(Scanner.get());
		
		GenerateForSpim.opSysout(item.getSym() == Constants.SYSOUTLINE);
	}

	/**
	 * System-In kann mittels einer beliebigen Variable definiert werden
	 * Diese muss gespeichert werden
	 * @param i
	 * @return
	 */
	public static String initSysReader(Item i) {
		
		i = Scanner.get();
		String scName = String.valueOf(i.getVal());
		
		while(i.getSym() != Constants.SEMICOLON) {
			i = Scanner.get();
		}
		
		return scName;
	}

	/**
	 * Zustaendig fuer das Einlesen aus der Console
	 * @param oi
	 */
	public static void readConsoleLine(ObjectInfo oi) {

		Item i = null;
		while(i == null ||i.getSym() != Constants.SEMICOLON) {
			i = Scanner.get();
		}

		GenerateForSpim.readConsoleLine(oi);
	}
}
