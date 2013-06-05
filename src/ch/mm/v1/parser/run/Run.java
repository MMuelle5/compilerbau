package ch.mm.v1.parser.run;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import ch.mm.v1.codeGenerator.GenerateForSpim;
import ch.mm.v1.parser.base.GenerateBaseCommants;
import ch.mm.v1.scanner.scan.Scanner;



public class Run {

	/**
	 * Verlangt zwei Argumente und zwar als 1. das InputFile und als 2. das Outputfile 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		if(args == null || args.length < 2) {
			System.out.println("Fehler: Der Aufruf muss folgende Werte beinhalten: <<Input-File-Name>> <<Output-File-Name>>");
		}
		else {
			start(args[0], args[1]);
		}
//		start("absolutStart.txt");
//		start("add1.txt");
//		start("add2.txt");
//		start("sub1.txt");
//		start("string1.txt");
//		start("class1.txt");
//		start("compare.txt");
//		start("if.txt");
//		start("whileAndIf.txt");
//		start("differentMethod.txt");
//		start("input.txt");
//		start("Gogogo.java");
//		start("allInOne.txt");
	}

	private static void start(String fileName, String outputName) {
		String fileAsString = null;
		try {
			fileAsString = readFileToString(fileName);
		} catch (IOException e) { //Fehlermeldung wurde bereits in der Console ausgegeben
			return;
		}

		Scanner.setText(fileAsString);
		GenerateBaseCommants.parseClass();
		
		try {
			writeIntoFile(outputName);
			
			System.out.println("Generierung abgeschlossen");
		} catch (IOException e) { //Fehlermeldung wurde bereits in der Console ausgegeben
			return;
		}
	}
	
	private static void writeIntoFile(String fileName) throws IOException {
		BufferedWriter bw = null;

		try {
		
			File f = new File(fileName);
			bw = new BufferedWriter(new FileWriter(f));
			for(String s : GenerateForSpim.getCommandMap().get(GenerateForSpim.DEFAULT_METHOD)) {
				bw.write(s);
				bw.newLine();
			}
			GenerateForSpim.getCommandMap().remove(GenerateForSpim.DEFAULT_METHOD);
			
			for(String key : GenerateForSpim.getCommandMap().keySet()) {
				bw.newLine();
				for(String s : GenerateForSpim.getCommandMap().get(key)) {
					bw.write(s);
					bw.newLine();
				}
			}
		} catch (IOException e) {
			System.out.println("Fehler: Unbekannter Fehler beim Schreibvorgang");
			throw e;
		} finally {
			if(bw != null) {
				bw.close();
			}
		}
	}
	
	private static String readFileToString(String fileName) throws IOException {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			br = new BufferedReader(new FileReader(new File(fileName)));
			
			
			String s = null;
			
			while((s = br.readLine()) != null) {
				sb.append(s).append(" ");
			}
		} catch (IOException e) {
			System.out.println("Fehler: das einzulensende File ist entweder unbekannt oder korrupt");
			throw e;
		} finally {
			if(br != null) {
				br.close();
			}
		}
		
		return sb.toString();
	}
}
