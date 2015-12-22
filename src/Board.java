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
	boolean firstMove = true;
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
		if(firstMove) {
			firstMove = false;
			if(getCell(n/2+1,n/2+1) == Pebble.EMPTY) {
				int[] indices = {n/2+1,n/2+1};
				return indices;
			} else {
				return randomAdjacent();
			}
		}
		Pebble otherColor = (color == Pebble.X) ? Pebble.O : Pebble.X;

		int defendingMove[] = {-1,-1};
		for(int i=1; i<=n; i++) {
			for(int j=1; j<=n; j++) {
				if(getCell(i,j) != Pebble.EMPTY)
					continue;
				// This move wins you the game or prevents defeat;
				// it checks for five-in-a-row's
				if(pebblesInARow(5,color,i,j)) {
					int[] winningMove = {i,j};
					return winningMove;
				}
				if(pebblesInARow(5,otherColor,i,j)) {
					defendingMove[0] = i;
					defendingMove[1] = j;
				}
			}
		}
		if(defendingMove[0] != -1)
			return defendingMove;
		
		for(int i=1; i<=n; i++) {
			for(int j=1; j<=n; j++) {
				if(getCell(i,j) != Pebble.EMPTY)
					continue;
				// this move decides whether you win the game or avoiding losing
				// checks for unblocked four-in-a-row's, or intersections of 3-in-a-row
				if(decisiveMove(i,j,color)) {
					int[] winningMove = {i,j};
					return winningMove;
				}
				if(decisiveMove(i,j,otherColor)) {
					defendingMove[0] = i;
					defendingMove[1] = j;
				}
			}
		}
		if(defendingMove[0] != -1)
			return defendingMove;
		
//		for(int i=1; i<=n; i++) {
//			for(int j=1; j<=n; j++) {
//				if(getCell(i,j) != Pebble.EMPTY)
//					continue;
//				// this move decides whether you win the game or avoiding losing
//				// checks for unblocked four-in-a-row's, or intersections of 3-in-a-row	
//				if(unblockedPebblesInARow(3,color,i,j)) {
//					int[] winningMove = {i,j};
//					return winningMove;
//				}
//				if(unblockedPebblesInARow(3,otherColor,i,j)) {
//					defendingMove[0] = i;
//					defendingMove[1] = j;
//				}
//			}
//		}
//		if(defendingMove[0] != -1)
//			return defendingMove;
		
		return bottomLeft();
		//return randomAdjacent();
	}
	static private int forcedMoves(boolean p[], int first, int last) {
		// used in decisiveMove to determine if we have a forced move
		
		// no forced move possible if length < 5
		if(last - first < 4) // length = last + 1 - first
			return 0;
		
		int forcedMoves = 0;
		int sameColor = 0;
		for(int i=first; i<first+5; i++)
			if(p[i])
				sameColor++;
		if(sameColor >= 4)
			forcedMoves++;
		for(int i=first; i<last-4; i++) {
			if(p[i])
				sameColor--;
			if(p[i+5])
				sameColor++;
			if(sameColor >= 4)
				forcedMoves++;
		}
		
		if(forcedMoves != 0)
			return forcedMoves;
		// No threat of potential five-in-a-row's, 
		// so we check for potential unblocked 4-in-a-row's
		if(last - first == 4) // blocked 4-in-a-row guaranteed
			return 0;
		
		sameColor = 0;
		for(int i=first+1; i<first+5; i++)
			if(p[i])
				sameColor++;
		if(sameColor >= 3)
			return 1;
		for(int i=first+1; i<last-4; i++) {
			if(p[i])
				sameColor--;
			if(p[i+4])
				sameColor++;
			if(sameColor >= 3)
				return 1;
		}		
		return 0;
	}
	private boolean decisiveMove(int x, int y, Pebble p) {
		// this move decides whether you win the game or avoiding losing
		// checks for >2 forcedMoves (unblocked 3-in-a-row or 4 in-a-row)
		// uses: forcedMoves()
		
		boolean[] arr = new boolean[9];
		arr[4] = true;
		//	+-+-+-+-+-+-+-+-+-+
		//	| | | | |X| | | | |
		//	+-+-+-+-+-+-+-+-+-+
		//   0 1 2 3[4]5 6 7 8
		
		// check horizontal
		int forcedMoves = 0;
		
		int first = 4, last = 4;
		int i = 0;
		while( (--i >= -4) && (getCell(x+i,y)==Pebble.EMPTY || getCell(x+i,y)==p) )
			arr[--first] = (getCell(x+i,y)==p);
		i = 0;
		while( (++i <= 4) && (getCell(x+i,y)==Pebble.EMPTY || getCell(x+i,y)==p) )
			arr[++last] = (getCell(x+i,y)==p);
		forcedMoves += forcedMoves(arr,first,last);
		
		// check vertical
		first = last = 4;
		i = 0;
		while( (--i >= -4) && (getCell(x,y+i)==Pebble.EMPTY || getCell(x,y+i)==p) )
			arr[--first] = (getCell(x,y+i)==p);
		i = 0;
		while( (++i <= 4) && (getCell(x,y+i)==Pebble.EMPTY || getCell(x,y+i)==p) )
			arr[++last] = (getCell(x,y+i)==p);
		forcedMoves += forcedMoves(arr,first,last);
		
		// check '\' diagonal
		first = last = 4;
		i = 0;
		while( (--i >= -4) && (getCell(x-i,y+i)==Pebble.EMPTY || getCell(x-i,y+i)==p) )
			arr[--first] = (getCell(x-i,y+i)==p);			
		i = 0;
		while( (++i <= 4) && (getCell(x-i,y+i)==Pebble.EMPTY || getCell(x-i,y+i)==p) )
			arr[++last] = (getCell(x-i,y+i)==p);
		forcedMoves += forcedMoves(arr,first,last);
		
		// check '/' diagonal
		first = last = 4;
		i = 0;
		while( (--i >= -4) && (getCell(x+i,y+i)==Pebble.EMPTY || getCell(x+i,y+i)==p) )
			arr[--first] = (getCell(x+i,y+i)==p);	
		i = 0;
		while( (++i <= 4) && (getCell(x+i,y+i)==Pebble.EMPTY || getCell(x+i,y+i)==p) )
			arr[++last] = (getCell(x+i,y+i)==p);
		forcedMoves += forcedMoves(arr,first,last);
		
		return (forcedMoves > 1);
	}
	private boolean pebblesInARow(int range, Pebble p, int x, int y) {
		int sameColor = 1;
		// check colors to the left and right
		int i = x;
		while(getCell(--i,y) == p)
			sameColor++;		
		i = x;
		while(getCell(++i,y) == p)
			sameColor++;
		if(sameColor >= range)
			return true;
		
		// now check colors below and above
		sameColor = 1;
		int j = y;
		while(getCell(x,--j) == p)
			sameColor++;
		j = y;
		while(getCell(x,++j) == p)
			sameColor++;
		
		if(sameColor >= range)
			return true;
		
		sameColor = 1;
		// check '/' diagonal
		i = x;
		j = y;
		while(getCell(--i,--j) == p)
			sameColor++;		
		i = x;
		j = y;
		while(getCell(++i,++j) == p)
			sameColor++;
		if(sameColor >= range)
			return true;
		
		// now check '\' diagonal
		sameColor = 1;
		i = x;
		j = y;
		while(getCell(++i,--j) == p)
			sameColor++;
		i = x;
		j = y;
		while(getCell(--i,++j) == p)
			sameColor++;	
		
		return (sameColor >=  range);
	}
	@SuppressWarnings("unused")
	private boolean unblockedPebblesInARow(int range, Pebble p, int x, int y) {
		boolean blocked = false;
		int sameColor = 1;
		// check colors to the left and right
		int i = x;
		while(getCell(--i,y) == p)
			sameColor++;
		if(getCell(i,y) != Pebble.EMPTY)
			blocked = true;
		i = x;
		while(getCell(++i,y) == p)
			sameColor++;
		if(getCell(i,y) != Pebble.EMPTY)
			blocked = true;
		if(!blocked && sameColor >= range)
			return true;		
		// now check colors below and above
		blocked = false;
		sameColor = 1;
		int j = y;
		while(getCell(x,--j) == p)
			sameColor++;
		if(getCell(x,j) != Pebble.EMPTY)
			blocked = true;
		j = y;
		while(getCell(x,++j) == p)
			sameColor++;
		if(getCell(x,j) != Pebble.EMPTY)
			blocked = true;
		if(!blocked && sameColor >= range)
			return true;
		
		blocked = false;
		sameColor = 1;
		// check '\' diagonal
		i = x;
		j = y;
		while(getCell(--i,--j) == p)
			sameColor++;
		if(getCell(i,j) != Pebble.EMPTY)
			blocked = true;
		i = x;
		j = y;
		while(getCell(++i,++j) == p)
			sameColor++;
		if(getCell(i,j) != Pebble.EMPTY)
			blocked = true;
		if(!blocked && sameColor >= range)
			return true;		
		// now check '/' diagonal
		blocked = false;
		sameColor = 1;
		i = x;
		j = y;
		while(getCell(++i,--j) == p)
			sameColor++;
		if(getCell(i,j) != Pebble.EMPTY)
			blocked = true;
		i = x;
		j = y;
		while(getCell(--i,++j) == p)
			sameColor++;
		if(getCell(i,j) != Pebble.EMPTY)
			blocked = true;
//		if(!blocked && sameColor >= range)
//			return true;
		
		return false;
	}
}
