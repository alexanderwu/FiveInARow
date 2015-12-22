/**
 * Alexander Wu
 * perm # 7924624
 * alexanderwu@umail.ucsb.edu
 */


import java.util.Scanner;

public class HumanPlayer extends Player {
	static Scanner userInput = new Scanner(System.in);
	
	public HumanPlayer() {
		this(Pebble.X); // white
	}
	public HumanPlayer(Pebble p) {
		color = p;
	}
	
	@Override
	public int[] nextMove() {
		int xPos = 0, yPos = 0;
		System.out.println("Enter x-position and y-position: "); // (1,1) represent bottom-left corner
		System.out.print("x: ");
		xPos = userInput.nextInt();
		System.out.print("y: ");
		yPos = userInput.nextInt();
		System.out.printf("You entered %d,%d", xPos, yPos);		
		int[] indices = {xPos, yPos};		
		return indices;
	}
	public int[] nextMove(int xPos, int yPos) { // xPos and yPos from mouse click
		int[] indices = {xPos, yPos};		
		return indices;
	}
}
