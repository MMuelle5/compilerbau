package ch.mm.v1.scanner.base;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	/*
	 * Symbols
	 */
	public final static int NULL = 0; // Default Wert
	public final static int TIMES= 1; // MAL
	public final static int DIV= 3;
	public final static int MOD= 4;
	public final static int AND= 5;
	public final static int PLUS= 6;
	public final static int MINUS= 7;
	public final static int OR= 8;
	public final static int EQL= 9;
	public final static int NEQ= 10;
	public final static int LSS= 11;
	public final static int LEQ= 12;
	public final static int GTR= 13;
	public final static int GEQ= 14;
	public final static int ALLOCATE= 17;
	public final static int PERIOD= 18;
	public final static int COMMA= 19;
	public final static int COLON= 20;
	public final static int RPAREN= 22;
	public final static int RBRAK= 23;
	public final static int RBRACE=24;
	public final static int OF= 25;
	public final static int THEN= 26;
	public final static int DO= 27;
	public final static int LPAREN= 29;
	public final static int LBRAK= 30;
	public final static int LBRACE=31;
	public final static int NOT= 32;
	public final static int BECOMES= 33;
	public final static int NUMBER= 34;
	public final static int STRING= 35;
	public final static int BOOLEAN= 36;
	public final static int INDENT= 37;
	public final static int SEMICOLON= 38;
	public final static int END= 40;
	public final static int ELSE= 41;
	public final static int ELSEIF= 42;
	public final static int UNTIL= 43;
	public final static int IF= 44;
	public final static int WHILE= 46;
	public final static int REPEAT= 47;
	public final static int FOR= 48;
	public final static int ARRAY= 54;
	public final static int RECORD= 55;
	public final static int INTCONST= 57;
	public final static int STRCONST= 58;
	public final static int VAR= 59;
	public final static int METHOD= 60;
	public final static int BEGIN= 61;
	public final static int CLASS= 63;
	public final static int EOF= 64;
	public final static int SYSOUT = 70;
	public final static int SYSOUTLINE = 71;
	public final static int SYSREADER = 72;
	
	public final static Map<String, Integer> knownVars = new HashMap<String, Integer>() {
		{
			put("int", Constants.NUMBER);
			put("String", Constants.STRING);
		}
	};
}
