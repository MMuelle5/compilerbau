import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Gogogo {

	/**
	 * 
	 * @param args
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {

		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

		String out = "bitte Zahl eingeben";
		System.out.println(out);
		int i = Integer.valueOf(bufferRead.readLine());

		/* Sinnloser Kommentar 
		 * 
		*/
		String ret = "die Zahl ist";
		System.out.print(ret);
		System.out.println(i);
	}

}
