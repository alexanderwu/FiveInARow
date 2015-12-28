/**
 * Alexander Wu
 * alexanderwu@umail.ucsb.edu
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Board {
	
	private int n;
	public int getN() { return n; }

	private Pebble[][] board;
	static Random randomNum = new Random();
	ArrayList<Integer[]> PrevMoves;
	
	public Board() {
		this(5);
	}
	public Board(int gridSize){
		n = gridSize;	
		board = new Pebble[n][n];
		PrevMoves = new ArrayList<Integer[]>(n*n);
		clearBoard();	
	}
	
	public void setCell(int xPos, int yPos, Pebble aPebble) {
		if(xPos < 1 || yPos < 1 || xPos > n || yPos > n)
			return;
		board[xPos-1][n-yPos] = aPebble;
	}
	public void setCell(int[] indices, Pebble aPebble) {
		if(indices.length < 2)
			return;
		int xPos = indices[0];
		int yPos = indices[1];
		setCell(xPos,yPos,aPebble);
	}
	public Pebble getCell(int xPos, int yPos){
		if(xPos < 1 || yPos < 1 || xPos > n || yPos > n)
			return null;
		return board[xPos-1][n-yPos];
	}
	public Pebble getCell(int[] indices) {
		if(indices.length < 2)
			return null;
		int xPos = indices[0];
		int yPos = indices[1];
		return getCell(xPos,yPos);
	}
	public boolean isEmpty(int xPos, int yPos) {
		if(xPos < 1 || yPos < 1 || xPos > n || yPos > n)
			return false;
		return (getCell(xPos,yPos) == Pebble.EMPTY) ? true : false; 
	}
	public void clearBoard() {
		for (Pebble[] row : board)
		    Arrays.fill(row, Pebble.EMPTY);
	}
	public boolean gameEnds() {
		if(fiveX() || fiveO())
			return true;
		for(int i=0; i<n; i++) {
			for(int j =0; j<n; j++) {
				if(board[i][j] == Pebble.EMPTY)
					return false;
			}
		}
	  	return true;
	}
	public boolean fiveX() {
		// check horizontal
		for(int i=0; i<n-4; i++) {
			for(int j=0; j<n; j++) {
				if(board[i][j] == Pebble.X)
					if(board[i+1][j] == Pebble.X && board[i+2][j] == Pebble.X &&
						board[i+3][j] == Pebble.X && board[i+4][j] == Pebble.X )
						return true;
			}					
		}
		// check vertical
		for(int i=0; i<n; i++) {
			for(int j=0; j<n-4; j++) {
				if(board[i][j] == Pebble.X)
					if(board[i][j+1] == Pebble.X &&	board[i][j+2] == Pebble.X &&
						board[i][j+3] == Pebble.X && board[i][j+4] == Pebble.X )
						return true;						
			}
		}
		// count '\' diagonals
		for(int i=0; i<n-4; i++) {
			for(int j=0; j<n-4; j++) {
				if(board[i][j] == Pebble.X)
					if(board[i+1][j+1] == Pebble.X && board[i+2][j+2] == Pebble.X &&
						board[i+3][j+3] == Pebble.X && board[i+4][j+4] == Pebble.X)
						return true;
			}					
		}
		// count '/' diagonals
		for(int i=4; i<n; i++) {
			for(int j=0; j<n-4; j++) {
				if(board[i][j] == Pebble.X)
					if(board[i-1][j+1] == Pebble.X && board[i-2][j+2] == Pebble.X &&
						board[i-3][j+3] == Pebble.X && board[i-4][j+4] == Pebble.X)
						return true;					
			}
		}
		return false; 
	}	
	public boolean fiveO() {
		// check horizontal
		for(int i=0; i<n-4; i++) {
			for(int j=0; j<n; j++) {
				if(board[i][j] == Pebble.O)
					if(board[i+1][j] == Pebble.O && board[i+2][j] == Pebble.O &&
						board[i+3][j] == Pebble.O && board[i+4][j] == Pebble.O )
						return true;
			}					
		}
		// check vertical
		for(int i=0; i<n; i++) {
			for(int j=0; j<n-4; j++) {
				if(board[i][j] == Pebble.O)
					if(board[i][j+1] == Pebble.O &&	board[i][j+2] == Pebble.O &&
						board[i][j+3] == Pebble.O && board[i][j+4] == Pebble.O )
						return true;						
			}
		}
		// count '\' diagonals
		for(int i=0; i<n-4; i++) {
			for(int j=0; j<n-4; j++) {
				if(board[i][j] == Pebble.O)
					if(board[i+1][j+1] == Pebble.O && board[i+2][j+2] == Pebble.O &&
						board[i+3][j+3] == Pebble.O && board[i+4][j+4] == Pebble.O)
						return true;
			}					
		}
		// count '/' diagonals
		for(int i=4; i<n; i++) {
			for(int j=0; j<n-4; j++) {
				if(board[i][j] == Pebble.O)
					if(board[i-1][j+1] == Pebble.O && board[i-2][j+2] == Pebble.O &&
						board[i-3][j+3] == Pebble.O && board[i-4][j+4] == Pebble.O)
						return true;					
			}
		}
		return false; 
	}
	
	public Pebble[][] getBoard() {
		return board;
	}
	
	/** The following methods are for Sammy (AI) **/
	public int[] randomEmpty() {
		ArrayList<Integer[]>emptyIndices = new ArrayList<Integer[]>();
		for(int i=1; i<=n; i++) {
			for(int j=1; j<=n; j++) {
				if(getCell(i,j) == Pebble.EMPTY) {
					Integer[] indices = new Integer[2];
					indices[0] = i;
					indices[1] = j;	
					emptyIndices.add(indices);
				}
			}
		}
		int randomIndex = randomNum.nextInt(emptyIndices.size());
		int[] indices = new int[2];
		indices[0] = emptyIndices.get(randomIndex)[0];
		indices[1] = emptyIndices.get(randomIndex)[1];
		return indices;
	}
	public int[] randomAdjacent() {
		// Pick random available spaces and check to see if they are valid.
		ArrayList<Integer[]>validIndices = new ArrayList<Integer[]>();
		for(int i=1; i<=n; i++) {
			for(int j=1; j<=n; j++) {
				if( (getCell(i,j) == Pebble.EMPTY) && adjacentPebble(i, j, 1)) {
					Integer[] e = {i,j};
					validIndices.add(e);
				}
			}
		}
		if(validIndices.size() == 0)
			return null;
		int randomIndex = randomNum.nextInt(validIndices.size());
		int[] indices = {validIndices.get(randomIndex)[0],validIndices.get(randomIndex)[1]};
		return indices;
	}
	public int[] randomClose() {
		// Pick random available spaces and check to see if they are valid.
		ArrayList<Integer[]>validIndices = new ArrayList<Integer[]>();
		for(int i=1; i<=n; i++) {
			for(int j=1; j<=n; j++) {
				if( (getCell(i,j) == Pebble.EMPTY) && adjacentPebble(i, j, 2)) {
					Integer[] e = {i,j};
					validIndices.add(e);
				}
			}
		}
		if(validIndices.size() == 0)
			return null;
		int randomIndex = randomNum.nextInt(validIndices.size());
		int[] indices = {validIndices.get(randomIndex)[0],validIndices.get(randomIndex)[1]};
		return indices;
	}
	public int[] bottomLeft() {
		for(int i=1; i<=n; i++) {
			for(int j=1; j<=n; j++) {
				if(getCell(i,j) == Pebble.EMPTY) {
					int[] indices = {i,j};
					return indices;
				}
			}
		}
		System.out.println("Error: topLeft()"); // this line should not be reached
		return null;
	}
	
	private boolean adjacentPebble(int x, int y, int range) {
		// adjacentPebble() used in randomAdjacent() and randomClose().
		// It checks to see if there are any pebbles "range" distance away.
		int xMin = (x-range < 1)? 1: x - range;
		int yMin = (y-range < 1)? 1: y - range;
		int xLim = (x+range > n)? n:x + range;
		int yLim = (y+range > n)? n:y + range;
		for(int i=xMin; i<=xLim; i++)
			for(int j=yMin; j<=yLim; j++)
				if(getCell(i,j) != Pebble.EMPTY)
					return true;
		return false;
	}

	public int[] clever(Pebble color) {
		Pebble otherColor = (color == Pebble.X) ? Pebble.O : Pebble.X;

		int[] bestMove =  {n/2+1,n/2+1};
		if(!isEmpty(n/2+1,n/2+1)) {
			int[] indices = randomAdjacent();
			bestMove[0] = indices[0];
			bestMove[1] = indices[1];
		}
		int bestWeight = 1;
		for(int i=1; i<=n; i++) {
			for(int j=1; j<=n; j++) {
				if(getCell(i,j) != Pebble.EMPTY)
					continue;
				int weight = weightOfMove(i,j,otherColor)*9/10;
				weight += weightOfMove(i,j,color);
				
				if(weight > bestWeight) {
					bestWeight = weight;
					bestMove[0] = i;
					bestMove[1] = j;
				} else if(weight == bestWeight) {
					if(randomNum.nextBoolean()) {
						bestMove[0] = i;
						bestMove[1] = j;
					}
				} else {
					//return randomAdjacent();
				}
			}
		}
		System.out.printf("%d,%d %d\n",bestMove[0],bestMove[1],bestWeight);
		return bestMove;
		
	}
	private int inARow(boolean arr[], int first, int last, int same, int range) {
		return inARow(arr,first,last,same,range,false);
	}
	private int inARow(boolean arr[], int first, int last, int same, int range, boolean blocked) {
		int weightOfMove = 0;
		int sameColor = 0;
				
		for(int i=first; i<first+range; i++)
			if(arr[i])
				sameColor++;
		if(sameColor >= same && blocked)
			weightOfMove++;
		if(last+1-first == range)
			return weightOfMove;
		for(int i=first; i<last-range; i++) {
			if(arr[i])
				sameColor--;
			if(arr[i+range])
				sameColor++;
			if(sameColor >= same)
				weightOfMove++;
		}
		if(blocked) {
			if(arr[last-range])
				sameColor--;
			if(arr[last])
				sameColor++;
			if(sameColor >= same)
				weightOfMove++;
		}		
		return weightOfMove;
	}

	private int weightedRow(boolean arr[], int first, int last) {
		if(last - first < 4) // length = last + 1 - first
			return 0;
		int weight = 0;
		
		weight += 1000*inARow(arr, first, last, 5, 5,true);
		weight += 100*inARow(arr, first, last, 4, 5);
		weight += 10*inARow(arr, first, last, 4, 4,true);
		weight += 10*inARow(arr, first, last, 3, 4);
		weight += 2*inARow(arr, first, last, 2, 3);
		weight += inARow(arr, first, last, 2, 4);		
		
		return weight;
	}
	public int weightOfMove(int x, int y, Pebble p) {
		boolean[] arr = new boolean[9];
		arr[4] = true;
		//	+-+-+-+-+-+-+-+-+-+
		//	| | | | |X| | | | |
		//	+-+-+-+-+-+-+-+-+-+
		//   0 1 2 3[4]5 6 7 8
		
		// check horizontal
		int weight = 0;
		
		int first = 4, last = 4;
		int i = 0;
		while( (--i >= -4) && (getCell(x+i,y)==Pebble.EMPTY || getCell(x+i,y)==p) )
			arr[--first] = (getCell(x+i,y)==p);
		i = 0;
		while( (++i <= 4) && (getCell(x+i,y)==Pebble.EMPTY || getCell(x+i,y)==p) )
			arr[++last] = (getCell(x+i,y)==p);
		weight += weightedRow(arr,first,last);
		
		// check vertical
		first = last = 4;
		i = 0;
		while( (--i >= -4) && (getCell(x,y+i)==Pebble.EMPTY || getCell(x,y+i)==p) )
			arr[--first] = (getCell(x,y+i)==p);
		i = 0;
		while( (++i <= 4) && (getCell(x,y+i)==Pebble.EMPTY || getCell(x,y+i)==p) )
			arr[++last] = (getCell(x,y+i)==p);
		weight += weightedRow(arr,first,last);
		
		// check '\' diagonal
		first = last = 4;
		i = 0;
		while( (--i >= -4) && (getCell(x-i,y+i)==Pebble.EMPTY || getCell(x-i,y+i)==p) )
			arr[--first] = (getCell(x-i,y+i)==p);			
		i = 0;
		while( (++i <= 4) && (getCell(x-i,y+i)==Pebble.EMPTY || getCell(x-i,y+i)==p) )
			arr[++last] = (getCell(x-i,y+i)==p);
		weight += weightedRow(arr,first,last);
		
		// check '/' diagonal
		first = last = 4;
		i = 0;
		while( (--i >= -4) && (getCell(x+i,y+i)==Pebble.EMPTY || getCell(x+i,y+i)==p) )
			arr[--first] = (getCell(x+i,y+i)==p);	
		i = 0;
		while( (++i <= 4) && (getCell(x+i,y+i)==Pebble.EMPTY || getCell(x+i,y+i)==p) )
			arr[++last] = (getCell(x+i,y+i)==p);
		weight += weightedRow(arr,first,last);
		
		return weight;
	}

}
