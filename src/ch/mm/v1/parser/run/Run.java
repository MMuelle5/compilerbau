package ch.mm.v1.parser.run;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import ch.mm.v1.codeGenerator.GenerateForSpim;
import ch.mm.v1.parser.base.GenerateBaseCommants;
import ch.mm.v1.scanner.scan.Scanner;



public class Run {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		if(args == null || args.length == 0) {
			System.out.println("Es wurde kein File übergeben");
		}
		else {
			start(args[0]);
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
		start("Gogogo.java");
//		start("allInOne.txt");
	}

	private static void start(String fileName) throws IOException {
		String fileAsString = readFileToString(fileName);

		//		GenerateForSpim.getCommands().add("main:");
		Scanner.setText(fileAsString);
		GenerateBaseCommants.parseClass();
		
		for(String s : GenerateForSpim.getCommandMap().get(GenerateForSpim.DEFAULT_METHOD)) {
			System.out.println(s);
		}
		GenerateForSpim.getCommandMap().remove(GenerateForSpim.DEFAULT_METHOD);
		
		for(String key : GenerateForSpim.getCommandMap().keySet()) {
			System.out.println("");
			for(String s : GenerateForSpim.getCommandMap().get(key)) {
				System.out.println(s);
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
		} finally {
			if(br != null) {
				br.close();
			}
		}
		
		return sb.toString();
	}
}
