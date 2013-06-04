import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class SysIn {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String klAls = "Ihre Zahl ist kleiner als 3";
		String grAls = "Ihre Zahl ist gr�sser als 3";
		
		String l = "Bitte geben sie eine 3 ein:";
		int i = 0;
		while(i != 3) {
			System.out.println(l);
			i = Integer.valueOf(br.readLine());
			
			if(i < 3) {
				System.out.println(klAls);
			}
			else if(i > 3) {
				System.out.println(grAls);
			}
		}
		
		String thx = "Vielen Dank f�r die korrekte Eingabe.";
		System.out.println(thx);
		
	}
}
