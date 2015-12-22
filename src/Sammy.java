/**
 * Alexander Wu
 * alexanderwu@umail.ucsb.edu
 */


public class Sammy extends Player {	
	
	public Sammy() {
		this(Pebble.O); // black
	}
	public Sammy(Pebble x) {
		color = x;
	}
	
	@Override
	public int[] nextMove() {
		int[] indices = board.clever(this.color);
		return indices;
	}
	

}
