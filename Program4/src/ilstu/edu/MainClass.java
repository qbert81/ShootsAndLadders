package ilstu.edu;

import java.util.Scanner;

/**
 * @author Edward Domanus (edomanu@ilstu.edu)
 * This class contains the main method which runs the program, creates a game with specified ammount of players, and plays the game until a player has won.
 *
 */

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		System.out.println("Welcome to chutes and ladders!");
		System.out.println("Please enter the number of players to begin the game:");
		int input = 0;
		
		//repeat till input is valid integer
		while (input == 0) {
			String inputString = s.next();
			try {
				input =  Integer.parseInt(inputString);
			} catch( NumberFormatException ioe ) {
				System.out.println("Please enter an interger value");
				input = 0;
			}
			
		}

		Game z = new Game(input);
		z.play();
	}

}
