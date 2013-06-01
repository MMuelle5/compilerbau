package ch.mm.v1.scanner.scan;

import ch.mm.v1.scanner.base.Constants;
import ch.mm.v1.scanner.base.Item;

public class Scanner {

	private final static String SYS = "System";
	private final static String SYSOUT = "System.out";
	
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
		Item i = new Item(id, sym, null, id-1, id+1);
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
			else if(c == '.' && (SYS.equals(sb.toString()) || SYSOUT.equals(sb.toString()))) {
				sb.append(c);
			}
			else {
				if("int".equals(sb.toString())) {
					i = new Item(id, Constants.NUMBER, null, -1, id+1);
					break;
				}
				else if("String".equals(sb.toString())) {
					i = new Item(id, Constants.STRING, null, -1, id+1);
					break;
				}
				else if("BufferedReader".equals(sb.toString())) {
					i = new Item(id, Constants.SYSREADER, null, -1, id+1);
					break;
				}
				else if("boolean".equals(sb.toString())) {
					i = new Item(id, Constants.BOOLEAN, null, -1, id+1);
					break;
				}
				else if ("if".equals(sb.toString())) {
					i = new Item(id, Constants.IF, null, -1, id+1);
					break;
				}
				else if ("else".equals(sb.toString())) {
					i = handleElseIf(sb.toString());
					break;
				}
				else if ("while".equals(sb.toString())) {
					i = new Item(id, Constants.WHILE, 1, -1, -1);
					break;
				}
				else if ("true".equals(sb.toString())) {
					i = new Item(id, Constants.INTCONST, 1, -1, -1);
					break;
				}
				else if ("flase".endsWith(sb.toString())) {
					i = new Item(id, Constants.INTCONST, 0, -1, -1);
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
					i = new Item(id, Constants.SYSOUTLINE, null, -1, id+1);
					break;
				}
				else if("System.out.print".equals(sb.toString())) {
					i = new Item(id, Constants.SYSOUT, null, -1, id+1);
					break;
				}
				else { //Identifier
					i = new Item(id, Constants.INDENT, sb.toString(), id -1, id+1);
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
			return new Item(id, Constants.ELSE, null, id-1, id+1);
		}
		else if(c == 'i') {
			pos++;
			return new Item(id, Constants.ELSEIF, null, id-1, id+1);
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
				Item i = new Item(id, Constants.INTCONST, Integer.valueOf(sb.toString()), -1, -1);
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
				i = new Item(id, Constants.EQL, null, id -1, id+1);
			}
			else if(c == '>') {
				i = new Item(id, Constants.GEQ, null, id -1, id+1);
			}
			else if(c == '<') {
				i = new Item(id, Constants.LEQ, null, id -1, id+1);
			}
			id++;
			pos++;
			return i;
		}
		else {
			Item i = null;
			if(c == '=') {
				i = new Item(id, Constants.BECOMES, null, id -1, id+1);
			}
			else if(c == '>') {
				i = new Item(id, Constants.GTR, null, id -1, id+1);
			}
			else if(c == '<') {
				i = new Item(id, Constants.LSS, null, id -1, id+1);
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
