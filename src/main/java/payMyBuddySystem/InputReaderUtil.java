package payMyBuddySystem;

import java.util.Scanner;

public class InputReaderUtil {
	public static Scanner scan = new Scanner(System.in);
	public int readSelection() {
		 try {
	            int input = Integer.parseInt(scan.nextLine());
	            return input;
	        }catch(Exception e){
	            System.out.println("Error reading input. Please enter valid number for proceeding further");
	            return -1;
	        }
	}
	public String readText() throws Exception {
		try {
			String text = scan.nextLine();
			if(text== null || text.trim().length()==0) {
				 throw new IllegalArgumentException("Invalid input provided");
			}
			return text;
		}
		catch(Exception e) {
			System.out.println("Error reading text");
			 throw e; 
		}
	}

}
