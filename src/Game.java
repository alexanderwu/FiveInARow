/**
 * Alexander Wu
 * alexanderwu@umail.ucsb.edu
 */


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game extends JFrame implements ActionListener, MouseListener{
	
	private static final long serialVersionUID = 1L; // gets rid of eclipse warning
	private JPanel panel;
	final int MARGIN = 30;
	private int cellSize;
	private int frameSize = 600;
	private Board board;
	private Player player1; // Default color: black
	private Player player2;// Default color: white
	private boolean player1Turn = true; // true: player1 first, false: player2 first
		
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Player player1 = new HumanPlayer();
		//Player player2 = new HumanPlayer(Pebble.O);
		//Player player1 = new Sammy(Pebble.X);
		Player player2 = new Sammy();
		int gridSize = 12;
		Game game = new Game(gridSize, player1, player2);
	}
	
	public Game() {
		this(5);
	}
	public Game(int gridSize) {
		this(gridSize, new HumanPlayer(), new Sammy());
	}
	public Game(int gridSize, Player p1, Player p2) {
		super("Five in a Row");
		board = new Board(gridSize);
		cellSize = 500/board.getN();
		player1 = p1;
		player2 = p2;	
		player1.board = board;
		player2.board = board;
		
		this.setSize(frameSize, frameSize);
		this.setLayout(null);
		initBoard(board.getN());
		this.add(panel);
		this.setVisible(true);
		panel.addMouseListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		if(p1 instanceof Sammy) {
//			clickNextPebble(b.getN()/2+1,b.getN()/2+1,player1);
//			player1Turn = false;
//		}
	}
	
	@SuppressWarnings("serial")
	private void initBoard(final int n) {
		panel = new JPanel(){
			public void paintComponent(Graphics g){
				g.setColor(Color.decode("#F0D250"));
				g.fillRect(0, 0, n*cellSize, n*cellSize);
				g.setColor(Color.BLACK);
				for(int i=0; i<=n; i++){
					g.drawLine(0, i*cellSize, n*cellSize, i*cellSize);
					g.drawLine(i*cellSize, 0, i*cellSize, n*cellSize);
				}
			}
		};
		panel.setLocation(MARGIN, MARGIN);
		panel.setSize(n*cellSize+1, n*cellSize+1);	
		panel.setEnabled(true);
	}
	@SuppressWarnings("serial")
	private void initPebble(int xPos, int yPos, Pebble aPebble) {
		final Pebble p = aPebble;
		JLabel oval = new JLabel() {
			public void paintComponent(Graphics g){
				switch(p) {
				case X:
					g.setColor(Color.BLACK);
					break;
				case O:
					g.setColor(Color.WHITE);
					break;
				default:
				}
				g.fillOval(0, 0, cellSize, cellSize);
			}
		};
		oval.setLocation(cellSize*xPos,cellSize*yPos);
		oval.setSize(cellSize, cellSize);
		oval.setVisible(true);
		panel.add(oval);
	}
	private void gameEndsDialog() {
		// assumes the that game has ended		
		// assumes player1 is Pebble.X and player2 is Pebble.O
		if(board.fiveX()) {
			if(player1.color == Pebble.X) 
				player1.wins = true;
			if(player2.color == Pebble.X)
				player2.wins = true;
			System.out.println("Black wins!");
			JOptionPane.showMessageDialog(null, "Black wins!");
		}
		else if(board.fiveO()) {
			if(player1.color == Pebble.O)
				player1.wins = true;
			if(player2.color == Pebble.O)
				player2.wins = true;
			System.out.println("White wins!");			
			JOptionPane.showMessageDialog(null, "White wins!");
		}
		else {
			System.out.println("Game end error");
		}
	}
	
	private void clickNextPebble(int xPos, int yPos, Player p) {
		// used in mouseReleased as a helper function to determine how to place next pebble
		if(p instanceof Sammy) {
			int[] indices1 = p.nextMove();
			board.setCell(indices1[0], indices1[1], p.color);
			this.initPebble(indices1[0]-1, board.getN()-indices1[1], p.color);
			this.repaint();
		}
		else if(p instanceof HumanPlayer) {
			this.initPebble(xPos-1, board.getN()-yPos, p.color);
			board.setCell(xPos, yPos, p.color);
			this.repaint();
		}
		// else an error has occured
	}
	@Override
 	public void mouseReleased(MouseEvent e) {
		if(player1.wins || player2.wins) {			
			this.setVisible(false);
			this.dispose();
			new Game(board.getN(), player1, player2);
			player1.wins = player2.wins = false;
			return;	
		}
		
		// get position of click
		int x = e.getX()-1;
		int y = e.getY()-1;
		int xPos = x/cellSize + 1;
		int yPos = board.getN() - y/cellSize;
		Player currentPlayer = (player1Turn)? player1: player2;
		Player otherPlayer = (!player1Turn)? player1: player2;
		if(currentPlayer instanceof HumanPlayer) {
			if(!board.isEmpty(xPos, yPos)) 
				return;
			clickNextPebble(xPos,yPos,currentPlayer);
			if(board.gameEnds()) {
				gameEndsDialog();
				return;
			}
			if(otherPlayer instanceof Sammy)
				clickNextPebble(xPos,yPos,otherPlayer);				
			else 
				player1Turn = !player1Turn;
		}
		else {
			clickNextPebble(xPos,yPos,currentPlayer);
			player1Turn = !player1Turn;
		}		
		if(board.gameEnds())
			gameEndsDialog();
	}
	@Override
	public void mouseClicked(MouseEvent e) { }
	@Override
	public void mousePressed(MouseEvent e) { }
	@Override
	public void mouseEntered(MouseEvent e) { }
	@Override
	public void mouseExited(MouseEvent e) { }

//	private class GamePanel extends JPanel{
//		private static final long serialVersionUID = 1L;
//		
//		public void paintComponent(Graphics g) {
//            g.setColor(Color.WHITE);
//            g.fillRect(0, 0, this.getWidth()-2, this.getHeight()-2);
//
//            g.setColor(Color.BLACK);
//            int cellSize = 500/board.getN();
//            for (int x = 0; x < this.getWidth(); x += cellSize)
//                g.drawLine(x, 0, x, this.getHeight());
//
//            for (int y = 0; y < this.getHeight(); y += cellSize)
//                g.drawLine(0, y, this.getWidth(), y);
//        }
//	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		if(str.equals("New Game"))
			System.out.println("New Game");
		
	}
	
}
