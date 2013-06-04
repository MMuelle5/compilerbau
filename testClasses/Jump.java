
public class Jump {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int i = 0;
		String grt11 = ": else if, grösser gleich 11";
		
		while(i < 20) {
			
			if(i == 10) {
				zehn();
			}
			else if(i >= 11) {
				System.out.print(i);
				System.out.println(grt11);
			}
			else {
				System.out.println(i);
			}
			
			i = i+1;
		}
	}
	
	private static void zehn() {
		String lZehn = "10: Methodenaufruf im if";
		System.out.println(lZehn);
	}

}
