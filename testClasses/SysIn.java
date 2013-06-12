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
		String grAls = "Ihre Zahl ist grösser als 3";
		
		String l = "Bitte geben sie eine 3 ein:";
		int i = 0;
		while(i != 3) {
			System.out.println(l);
			i = Integer.valueOf(br.readLine());
			
			if(i < 3) {
				nichtDrei();
			}
			else if(i > 8) {
				nichtDrei();
			}
			else if(i > 3) {
				System.out.println(grAls);
			}
		}
		
		String thx = "Vielen Dank für die korrekte Eingabe.";
		System.out.println(thx);
		
	}
	
	private static void nichtDrei() {

		String klAls = "Sie solltens doch wisse, diese Zahl ist nicht 3!";

		System.out.println(klAls);
	}
}
