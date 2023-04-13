package ilstu.edu;

import java.util.ArrayList;
import java.util.Scanner;


/**
 * @author Edward Domanus (edomanu@ilstu.edu)
 * This class contains the Game class and the Square class. 
 * Methods present in this class will allow the user to construct a gameboard made up of squares as well as play on the game board with specified ammount of players
 *
 */
public class Game {

	public class Square {
		private int squareNumber;
		protected Square next;
		protected Square prev;
		private int jumpVal;
		
		/**
		 * @param squareNum - int that specifies the ID of the square
		 * @param jumpVal - int that specifies if landing on the square will make the user jump
		 * @param prevSquare - Square reference to previous square on the gameboard
		 */
		public Square(int squareNum, int jumpVal, Square prevSquare) {
			this.squareNumber = squareNum;
			this.jumpVal = jumpVal;
			this.prev = prevSquare;
		}
	}
	
	private Square startSquare = null;
	private Square endSquare = null;
	private ArrayList<Square> playerPositions;
	private int curPlayer;
	private int curSquares = 0;
	
	/**
	 * @param x - Square that will be added to the 2d linked list of already existing squares on the gameboard
	 */
	public void addSquare(Square x) {
		x.next = null;
		if (curSquares == 0) {
			startSquare = x;
			endSquare = x;
		} else {
			x.prev = endSquare;
			endSquare.next = x;
			endSquare = x;
		}
		curSquares ++;
	}
	
	/**
	 * @param thisSquare - int value which is the squares ID and placement on the gameboard
	 * @return
	 * 
	 * -This method will generate a random jump value for a square that will ensure it will not result in a player jumping off of the board.
	 */
	public int generateJumpVal(int thisSquare) {
		int jumpVal = 0;
		int r = (int)Math.floor(Math.random() * 4 + 1);
		if(r == 4) {
			while(jumpVal == 0 || ((thisSquare +jumpVal) < 0) || ((thisSquare + jumpVal) > 100)) {
				jumpVal = (int)Math.floor(Math.random() * 21 -10);
			}
		}
		return jumpVal;
	}
	
	
	/**
	 * @param numPlayers - int that represents the number of players to play the current game
	 */
	public Game(int numPlayers) {
		Square lastSquare = null;
		int numSquares = 100;
		for(int i = 1; i <= numSquares; i++ ) {
			
			int newJumpVal = generateJumpVal(i);
			Square newSquare = new Square(i, newJumpVal, lastSquare);
			lastSquare = newSquare;
			addSquare(newSquare);
		}
			
		
		curPlayer = 1;
		
		//set all players positions to the starting square
		playerPositions = new ArrayList<Square>(numPlayers-1);
		for(int i = 0; i < numPlayers; i++) {
			playerPositions.add(startSquare);
		}
	}
	
	/**
	 * -plays the game object which this method was invoked on
	 */
	public void play() {
		Scanner s = new Scanner(System.in);
		int playerWon = -1;
		while(playerWon == -1) {
			System.out.println("It is player " + curPlayer + "'s turn to move please press any key to roll the dice");
			s.next();
			int roll = (int)Math.floor(Math.random() * 6 + 1);
			System.out.println("*******************************************");
			System.out.println("Player " + curPlayer + " rolled a " + roll);
			if (move(curPlayer, roll) == true) {
				System.out.println("Player " + curPlayer + " has won the game");
				playerWon = curPlayer;
			}
			System.out.println("*******************************************\n");
			if(curPlayer >= playerPositions.size()) {
				curPlayer = 1;
			} else {
				curPlayer += 1;
			}
		}
	}
	
	/**
	 * @param thisSquare - Square reference to a square which the player landed on
	 * @return - returns a String that will state what player has landed on what square
	 */
	public String landed(Square thisSquare) {
		String rs = ("Player " + curPlayer + " has landed on Square " + thisSquare.squareNumber + ".");
		return rs;
	}
	
	/**
	 * @param playerNumber - int that specifies which player to move
	 * @param distance - int that specifies how many squares a player will move
	 * @return
	 */
	public boolean move(int playerNumber, int distance) {
		boolean won = false;
		boolean hasNotJumped = true;
		Square curSquare = playerPositions.get(playerNumber - 1);
		System.out.println("Player " + playerNumber + " began on square " + curSquare.squareNumber);
		int spacesLeftToMove = distance;
		while(spacesLeftToMove != 0) {
			if (spacesLeftToMove > 0) {
				curSquare = curSquare.next;
				if (curSquare.squareNumber == 100) {
					return true;
				}
				spacesLeftToMove--;
			}
			if (spacesLeftToMove < 0) {
				curSquare = curSquare.prev;
				if (curSquare.squareNumber == 1) {
					return false;
				}
				spacesLeftToMove++;
			}
			if (spacesLeftToMove == 0) {
				System.out.println(landed(curSquare));
				if(hasNotJumped) {
					spacesLeftToMove = curSquare.jumpVal;
				}
				
				if(spacesLeftToMove != 0) {
					hasNotJumped = false;
					System.out.println("The square Player " + playerNumber + " landed on is going to make them jump " + curSquare.jumpVal + " Squares");
				}
			}
		}
		playerPositions.set(playerNumber - 1, curSquare);
		return won;
	}
}
