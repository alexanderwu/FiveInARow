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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game implements ActionListener, MouseListener{
	
	private JFrame frame;
	private JPanel panel;
	final int MARGIN = 30;
	private int cellSize;
	private int frameWidth = 560;
	private int frameHeight = 600;
	private Color boardColor = Color.decode("#F0D250");
	private Board board;
	private Player player1; // Default color: black
	private Player player2;// Default color: white
	private boolean player1Turn = true; // true: player1 first, false: player2 first
		
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		//Player player1 = new HumanPlayer();
		//Player player2 = new HumanPlayer(Pebble.O);
		Player player1 = new Sammy(Pebble.X);
		Player player2 = new Sammy();
		int gridSize = 3;
		Game game = new Game(gridSize, player1, player2);
	}
	
	public Game() {
		this(5);
	}
	public Game(int gridSize) {
		this(gridSize, new HumanPlayer(), new Sammy());
	}
	public Game(int gridSize, Player p1, Player p2) {
		board = new Board(gridSize);
		cellSize = 500/board.getN();
		player1 = p1;
		player2 = p2;	
		player1.board = board;
		player2.board = board;
		initGUI();
		
	}
	
	private void initGUI() {
		frame = new JFrame("Five in a Row");
		frame.setSize(frameWidth, frameHeight);
		frame.setLayout(null);
		initBoard(board.getN());
		frame.add(panel);
		frame.setVisible(true);
		panel.addMouseListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game",true);
		
		JMenuItem newGame = new JMenuItem("New Game", 'n');
        JMenuItem changeBoardSize = new JMenuItem("Board Size", 't');
        
        gameMenu.add(newGame); 
        gameMenu.add(changeBoardSize);
        
        menuBar.add(gameMenu); 
        
        //Create a main Panel to include Player options
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
	
        // Add everything to Window
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(mainPanel);
		frame.setVisible(true);
		
		newGame.addActionListener(this);
		changeBoardSize.addActionListener(this);
		//gamePanel.addMouseListener(this);
        
	}
	@SuppressWarnings("serial")
	private void initBoard(final int n) {
		panel = new JPanel(){
			public void paintComponent(Graphics g){
				g.setColor(boardColor);
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
	private void initPebble(int xPos, int yPos, Pebble aPebble) {
		final Pebble p = aPebble;
		JLabel oval = new JLabel() {
			private static final long serialVersionUID = 1L;
			public void paintComponent(Graphics g){
				switch(p) {
				case X:
					g.setColor(Color.BLACK);
					break;
				case O:
					g.setColor(Color.WHITE);
					break;
				case EMPTY:
					g.setColor(boardColor);
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
			JOptionPane.showMessageDialog(null, "It's a tie!");
		}
	}
	
	private void clickNextPebble(int xPos, int yPos, Player p) {
		// used in mouseReleased as a helper function to determine how to place next pebble
		if(p instanceof Sammy) {
			int[] indices1 = p.nextMove();
			board.setCell(indices1[0], indices1[1], p.color);
			this.initPebble(indices1[0]-1, board.getN()-indices1[1], p.color);
			frame.repaint();
		}
		else if(p instanceof HumanPlayer) {
			this.initPebble(xPos-1, board.getN()-yPos, p.color);
			board.setCell(xPos, yPos, p.color);
			frame.repaint();
		}
		// else an error has occured
	}
	@Override
 	public void mouseReleased(MouseEvent e) {
		if(board.gameEnds()) {			
			board.clearBoard();
			panel.removeAll();  
			panel.repaint();	
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
	public void mousePressed(MouseEvent e) { 
	}
	@Override
	public void mouseEntered(MouseEvent e) { }
	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		if(str.equals("New Game"))
			System.out.println("New Game");
		
	}
	
}
