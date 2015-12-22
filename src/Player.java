/**
 * Alexander Wu
 * perm # 7924624
 * alexanderwu@umail.ucsb.edu
 */


abstract public class Player {
	public Pebble color;
	public Board board;
	public boolean wins = false;
	
	abstract public int[] nextMove();
}
