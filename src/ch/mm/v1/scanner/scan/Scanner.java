package ch.mm.v1.scanner.scan;

import ch.mm.v1.scanner.base.Constants;
import ch.mm.v1.scanner.base.Item;

/**
 * - Gibt das naechste definierte Zeichen zurueck
 * - Ebenfalls moeglich ist eine Zahl, die als Constante zurueck gegeben wird
 * - Ein String der als Indent zurueck gegeben wird (Parameter-Name)
 * - Ein String in "", als String-Constante
 * 
 * Aus Gruenden der Ausgabe musste zwischen Integer-Konstante und String-Konstante unterschieden werden
 * 
 * @author MARIUS
 *
 */
public class Scanner {

	private final static String SYS = "System";
	private final static String SYSOUT = "System.out";
	private final static String INTEGER = "Integer";
	private final static String INTEGERVAL = "Integer.valueOf";
	
	private static String text;
	private static int pos = 0;
	private static int id = 0;
	
	public static Item get() {

		while(text.length()< pos && text.charAt(pos) == ' ') {
			pos++;
		}

		if(text.length()==pos) {
			return new Item(id, Constants.EOF);
		}

		char c = text.charAt(pos);
		
		if(Character.isLetter(c)) {
			return getSymbolOrIdentifier(c);
		}
		else if(Character.isDigit(c)) {
			return getNumber(c);
		}
		else {
			switch(c) {
			case '=': return cmp(c);
			case '<': return cmp(c);
			case '>': return cmp(c);
			case '*': return getSymbol(Constants.TIMES);
			case '/': return getDash();
			case '+': return getSymbol(Constants.PLUS);
			case '-': return getSymbol(Constants.MINUS);
			case '(': return getSymbol(Constants.LPAREN);
			case ')': return getSymbol(Constants.RPAREN);
			case ';': return getSymbol(Constants.SEMICOLON);
			case '{': return getSymbol(Constants.LBRACE);
			case '}': return getSymbol(Constants.RBRACE);
			case '"': return getStringValue();
			}
		}
		
		pos++;
		return Scanner.get();
	}
	
	private static Item getDash() {
		pos++;
		char c = Scanner.text.charAt(pos);
		
		if(c == '*') { //Read Commend until the end of it
			while (true) {
				pos ++;
				c = Scanner.text.charAt(pos);
				if(c == '*') {
					pos++;
					c = Scanner.text.charAt(pos);
					if(c == '/') {
						return Scanner.get();
					}
				}
			}
		}
		
		else {
			return new Item(id, Constants.DIV);
		}
	}

	private static Item getStringValue() {
		StringBuilder sb = new StringBuilder();
		
		pos ++; // " muss ignoriert werden
		char c;
		while((c = text.charAt(pos)) != '"') {
			sb.append(c);
			pos++;
		}
		Item i = new Item(id, Constants.STRCONST, sb.toString());
		pos++;
		id++;
		return i;
	}

	private static Item getSymbol(int sym) {
		Item i = new Item(id, sym, null);
		id++;
		pos++;
		return i;
	}

	private static Item getSymbolOrIdentifier(char ci) {

		StringBuilder sb = new StringBuilder();
		sb.append(ci);
		Item i = null;
		
		char c;
		while (true) {
			pos ++;
			c = text.charAt(pos);
			
			if(Character.isDigit(c) || Character.isLetter(c) || c == '_' ) {
				sb.append(c);
			}
			else if(c == '.' && INTEGER.equals(sb.toString())) {
				sb.append(c);
			}
			else if(c =='(' && INTEGERVAL.equals(sb.toString())) {
				i = new Item(id, Constants.CAST, null);
				pos++;
				break;
			}
			else if(c == '.' && (SYS.equals(sb.toString()) || SYSOUT.equals(sb.toString()))) {
				sb.append(c);
			}
			else {
				if("int".equals(sb.toString())) {
					i = new Item(id, Constants.NUMBER, null);
					break;
				}
				else if("String".equals(sb.toString())) {
					i = new Item(id, Constants.STRING, null);
					break;
				}
				else if("BufferedReader".equals(sb.toString())) {
					i = new Item(id, Constants.SYSREADER, null);
					break;
				}
				else if("boolean".equals(sb.toString())) {
					i = new Item(id, Constants.BOOLEAN, null);
					break;
				}
				else if ("if".equals(sb.toString())) {
					i = new Item(id, Constants.IF, null);
					break;
				}
				else if ("else".equals(sb.toString())) {
					i = handleElseIf(sb.toString());
					break;
				}
				else if ("while".equals(sb.toString())) {
					i = new Item(id, Constants.WHILE, 1);
					break;
				}
				else if ("true".equals(sb.toString())) {
					i = new Item(id, Constants.INTCONST, 1);
					break;
				}
				else if ("flase".endsWith(sb.toString())) {
					i = new Item(id, Constants.INTCONST, 0);
					break;
				}
				else if ("public".equals(sb.toString()) || "private".equals(sb.toString())) {
					return null;
				}
				else if ("static".equals(sb.toString())) {
					return null;
				}
				else if("void".equals(sb.toString())) {
					return null;
				}
				else if("System.out.println".equals(sb.toString())) {
					i = new Item(id, Constants.SYSOUTLINE, null);
					break;
				}
				else if("System.out.print".equals(sb.toString())) {
					i = new Item(id, Constants.SYSOUT, null);
					break;
				}
				else { //Identifier
					i = new Item(id, Constants.INDENT, sb.toString());
					break;
				}
			}
		}
		
		id++;
		return i;
	}

	private static Item handleElseIf(String string) {
		pos++;

		char c = text.charAt(pos);
		while(c == ' ') {
			c = text.charAt(pos);
			pos++;
		}
		
		if(c == '{') {
			return new Item(id, Constants.ELSE, null);
		}
		else if(c == 'i') {
			pos++;
			return new Item(id, Constants.ELSEIF, null);
		}
		return null;
	}

	private static Item getNumber(char ci) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(ci);
		
		while(true) {
			pos++;
			char c = text.charAt(pos);
			
			if(Character.isDigit(c)) {
				sb.append(c);
			}
			else {
				Item i = new Item(id, Constants.INTCONST, Integer.valueOf(sb.toString()));
				id++;
				return i;
			}
		}
	}

	private static Item cmp(char c) {
		
		pos++;
		char c2 = text.charAt(pos);
		if(c2 == '=') {
			pos++;
			Item i = null;
			if(c == '=') {
				i = new Item(id, Constants.EQL, null);
			}
			else if(c == '>') {
				i = new Item(id, Constants.GEQ, null);
			}
			else if(c == '<') {
				i = new Item(id, Constants.LEQ, null);
			}
			id++;
			pos++;
			return i;
		}
		else {
			Item i = null;
			if(c == '=') {
				i = new Item(id, Constants.BECOMES, null);
			}
			else if(c == '>') {
				i = new Item(id, Constants.GTR, null);
			}
			else if(c == '<') {
				i = new Item(id, Constants.LSS, null);
			}
			id++;
			return i;
		}
	}

	public String getText() {
		return text;
	}

	public static void setText(String text) {
		Scanner.text = text;
	}

}
