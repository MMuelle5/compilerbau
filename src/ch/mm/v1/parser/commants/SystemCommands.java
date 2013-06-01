package ch.mm.v1.parser.commants;

import ch.mm.v1.codeGenerator.GenerateForSpim;
import ch.mm.v1.codeGenerator.ObjectInfo;
import ch.mm.v1.codeGenerator.Operation;
import ch.mm.v1.scanner.base.Constants;
import ch.mm.v1.scanner.base.Item;
import ch.mm.v1.scanner.scan.Scanner;

public final class SystemCommands {


	public static void sysout(Item item) {

		Operation op = new Operation();
		op.setOperator(item.getSym());
		
		Expression.startExpression(Scanner.get());
		
		GenerateForSpim.opSysout(item.getSym() == Constants.SYSOUTLINE);
	}

	public static String initSysReader(Item i) {
		
		i = Scanner.get();
		String scName = String.valueOf(i.getVal());
		
		while(i.getSym() != Constants.SEMICOLON) {
			i = Scanner.get();
		}
		
		return scName;
	}

	public static void readConsoleLine(ObjectInfo oi) {

		Item i = null;
		while(i == null ||i.getSym() != Constants.SEMICOLON) {
			i = Scanner.get();
		}

		GenerateForSpim.readConsoleLine(oi);
	}
}
