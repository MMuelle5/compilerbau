package ch.mm.v1.parser.commants;

import ch.mm.v1.codeGenerator.GenerateForSpim;
import ch.mm.v1.codeGenerator.Operation;
import ch.mm.v1.scanner.base.Constants;
import ch.mm.v1.scanner.base.Item;
import ch.mm.v1.scanner.scan.Scanner;

/**
 * Wird bei einfachen mathematischen Funktionen, Vergleichen, als auch einfachen Zuweisungen
 * aufgerufen
 * @author MARIUS
 *
 */
public class Expression {

	public static void startExpression(Item i) {
		
		Operation oper = new Operation();
		
		boolean hasSenceLessParen = false;
		if(i.getSym() == Constants.LPAREN) {
			i = Scanner.get();
			hasSenceLessParen = true;
		}
		Expression.simpleExpression(i, oper);
		if(hasSenceLessParen) {
			Scanner.get();
		}

		GenerateForSpim.op2(oper);
	}
	
	public static void simpleExpression(Item i, Operation actOperation) {
			
		if(i.getSym() == Constants.SEMICOLON) {
			return;
		}
		
		if(i.getSym() == Constants.PLUS) {
			actOperation.setOperator(Constants.PLUS);
			Expression.simpleExpression(Scanner.get(), actOperation);
		}
		else if(i.getSym() == Constants.MINUS) {
			actOperation.setOperator(Constants.MINUS);
			Expression.simpleExpression(Scanner.get(), actOperation);
		}
		else {
			if(Expression.term(i, actOperation)) {
				return;
			}
		}
	}
	
	public static boolean term(Item i, Operation oper) {
		
		if(i.getSym() == Constants.TIMES) {
			oper.setOperator(Constants.TIMES);
			Expression.simpleExpression(Scanner.get(), oper);
		}
		else if(i.getSym() == Constants.DIV) {
			oper.setOperator(Constants.DIV);
			Expression.simpleExpression(Scanner.get(), oper);
		}
		else {
			return factor(i, oper);
		}
		
		return false;
	}	
	public static boolean factor(Item i, Operation oper) {
		if(i.getSym() == Constants.LPAREN) {
			Operation child = new Operation();
			if(oper.getSubOperX() == null) {
				oper.setSubOperX(child);
			}
			else {
				oper.setSubOperY(child);
			}
			Item next = Scanner.get();
			if(next.getSym() != Constants.RPAREN) {
				Expression.simpleExpression(next, child);
			}
			
		}
		else if (i.getSym() == Constants.EQL) {
			oper.setOperator(Constants.EQL);
			Expression.simpleExpression(Scanner.get(), oper);
		}
		else if (i.getSym() == Constants.NEQ) {
			oper.setOperator(Constants.NEQ);
			Expression.simpleExpression(Scanner.get(), oper);
		}
		else if (i.getSym() == Constants.LEQ) {
			oper.setOperator(Constants.LEQ);
			Expression.simpleExpression(Scanner.get(), oper);
		}
		else if (i.getSym() == Constants.GEQ) {
			oper.setOperator(Constants.GEQ);
			Expression.simpleExpression(Scanner.get(), oper);
		}
		else if (i.getSym() == Constants.GTR) {
			oper.setOperator(Constants.GTR);
			Expression.simpleExpression(Scanner.get(), oper);
		}
		else if (i.getSym() == Constants.LSS) {
			oper.setOperator(Constants.LSS);
			Expression.simpleExpression(Scanner.get(), oper);
		}
		else if (i.getSym() == Constants.RPAREN) {
			return true;
		}
		else if(i.getSym() == Constants.INTCONST || i.getSym() == Constants.INDENT) {
			Operation it = new Operation();
			it.setItem(i);
			it.setOperator(Constants.INTCONST);
			if(oper.getSubOperX() == null) {
				oper.setSubOperX(it);
				Expression.simpleExpression(Scanner.get(), oper);
			}
			else {
				oper.setSubOperY(it);
			}
		}
				
		return false;
	}
	
}
