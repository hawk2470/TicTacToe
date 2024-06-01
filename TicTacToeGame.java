import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Random;

/**
* TicTacToeGame.java for final project
* This class represents a Tic-Tac-Toe game that can be played PvP or PvC
* @author Abhi Battepati, Section A3
*/
public class TicTacToeGame {
   
   // These constants are the game panel dimensions
   public static final int SIDE = 600;
   public static final int SIZE = 200;
   
   // Components and variables for the game
   private DrawingPanel panel;
   private Graphics2D g;
   private char[][] board;
   private boolean playerTurn = true;
   private boolean gameEnded = false;
   private boolean gameMode;
   private int xWins = 0;
   private int oWins = 0;
   private int ties = 0;
   
   // Colors and names for the players
   private Color xColor;
   private Color oColor;
   private String xName;
   private String oName;
   
   /**
   * Main method that starts the Tic-Tac-Toe game
   * @param args the command line arguments
   */
   public static void main(String[] args) {
   
      new TicTacToeGame();
   }
   
   /**
   * Constructor that initializes the game
   */
   public TicTacToeGame() {
   
      panel = new DrawingPanel(SIDE, SIDE);
      g = panel.getGraphics();
      board = new char[3][3];
      gameMode = chooseGameMode();
      enterUsernames();
      chooseColors();
      createBoard();
      TicTacToeMouseListener mouseListener = new TicTacToeMouseListener(this);
      panel.addMouseListener(mouseListener);
   }
   
   /**
   * Allows the users to choose the game mode (PvP or PvC)
   * @return true if PvP and false if PvC
   */
   // A lot of the JOptionPane functionality I learned from here:
   // https://www.youtube.com/watch?v=arcTW_znJYY
   // https://www.youtube.com/watch?v=BuW7y21FcYI
   // https://docs.oracle.com/javase/8/docs/api/javax/swing/JOptionPane.html
   private boolean chooseGameMode() {
   
      String[] options = {"Player vs Player", "Player vs Computer"};
      int choice = JOptionPane.showOptionDialog(null, "Choose Game Mode",
      "Tic-Tac-Toe", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
      return choice == 0;
   }

   /**
   * Allows the users to enter their usernames for game
   */
   private void enterUsernames() {
   
      if (gameMode) {
      
         xName = JOptionPane.showInputDialog(null, "Enter Player X Username:");
         if (xName == null || xName.trim().isEmpty()) {
         
            xName = "Player X";
         }
         
         oName = JOptionPane.showInputDialog(null, "Enter Player O Username:");
         if (oName == null || oName.trim().isEmpty()) {
         
            oName = "Player O";
         }
      }
      
      else {
      
         xName = JOptionPane.showInputDialog(null, "Enter Username:");
         if (xName == null || xName.trim().isEmpty()) {
         
            xName = "Player";
         }
         oName = "Computer";
      }
   }
   
   /**
   * Allows the users to enter color for their tokens
   */
   // JColorChooser I got from here:
   // https://www.youtube.com/watch?v=ILPVSRoUmI8
   // https://www.geeksforgeeks.org/java-swing-jcolorchooser-class/
   private void chooseColors() {
   
      if (gameMode) {
      
         xColor = JColorChooser.showDialog(null, "Choose X Color", Color.RED);
         if (xColor == null) {
         
            xColor = Color.RED;
         }
      
         oColor = JColorChooser.showDialog(null, "Choose O Color", Color.BLUE);
         if (oColor == null) {
         
            oColor = Color.BLUE;
         }
      }
      
      else {
      
         xColor = JColorChooser.showDialog(null, "Choose Character Color", Color.RED);
         if (xColor == null) {
         
            xColor = Color.RED;
         }
      
         Random rand = new Random();
         oColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
      }
   }
   
   /**
   * Creates the Tic-Tac-Toe game board
   */
   private void createBoard() {
   
      g.setColor(Color.BLACK);
      g.drawLine(SIZE, 0, SIZE, SIDE);
      g.drawLine(2 * SIZE, 0, 2 * SIZE, SIDE);
      g.drawLine(0, SIZE, SIDE, SIZE);
      g.drawLine(0, 2 * SIZE, SIDE, 2 * SIZE);
   }
   
   /**
   * Places the character (X or O) in a specified slot
   * @param row is the row to place the token
   * @param col is the column to place the token
   * @param character is the token (X or O)
   */
   public void placeCharacter(int row, int col, char character) {
   
      int x = col * SIZE;
      int y = row * SIZE;
      // For X
      if (character == 'X') {
      
         g.setColor(xColor);
         g.drawLine(x, y, x + SIZE, y + SIZE);
         g.drawLine(x + SIZE, y, x, y + SIZE);
      }
      
      // For O
      else if (character == 'O') {
      
         g.setColor(oColor);
         g.drawOval(x, y, SIZE, SIZE);
      }
   }
   
   /**
   * Shows a message about the outcome of the game
   * @param message the outcome message
   */
   public void outcomeMessage(String message) {
   
      g.setColor(Color.BLACK);
      g.setFont(new Font("Times New Roman", Font.BOLD, 20));
      FontMetrics fm = g.getFontMetrics();
      int textWidth = fm.stringWidth(message);
      int x = (SIDE - textWidth) / 2;
      int y = SIDE / 2;
      g.drawString(message, x, y);
   }
   
   /**
   * Allows the users to play again or see the results
   */
   public void playAgain() {
   
      String stats = xName + " Wins: " + xWins + " | " + oName + " Wins: " + oWins + " | Ties: " + ties;
      String[] options = {"Play Again", "Show Results"};
      int choice = JOptionPane.showOptionDialog(null, stats + "\nWould you like to play again or see the results?",
      "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
   
      if (choice == 0) {
      
         resetGame();
      }
      
      else {
      
         showResults();
      }
   }
   
   /**
   * Shows the results of the Tic-Tac-Toe round
   */
   // https://www.youtube.com/watch?v=BuW7y21FcYI
   public void showResults() {
   
      String message = xName + " Wins: " + xWins + "\n" + oName + " Wins: " + oWins + "\nTies: " + ties;
      JOptionPane.showMessageDialog(null, message, "Results", JOptionPane.INFORMATION_MESSAGE);
      System.exit(0);
   }

   /**
   * Resets the Tic-Tac-Toe game
   */
   public void resetGame() {
   
      board = new char[3][3];
      playerTurn = true;
      gameEnded = false;
      panel.clear();
      g = panel.getGraphics();
      createBoard();
   }

   /**
   * Gets the gameboard
   * @return a 2D char array that represents the game board
   */
   public char[][] getBoard() {
   
      return board;
   }
   
   /**
   * Checks the players turn
   * @return true if players turn and false if not
   */
   public boolean checkPlayerTurn() {
   
      return playerTurn;
   }
   
   /**
   * Sets the players turn
   * @param boolean value represeting players turn
   */
   public void setPlayerTurn(boolean playerTurn) {
   
      this.playerTurn = playerTurn;
   }
   
   /**
   * Checks if the game has ended
   * @return true if game ended and false if not
   */
   public boolean checkGameEnded() {
   
      return gameEnded;
   }
   
   /**
   * Sets the game to end
   * @param boolean representing if game ended
   */
   public void setGameEnded(boolean gameEnded) {
   
      this.gameEnded = gameEnded;
   }
   
   /**
   * Checks if PvP or PvC
   * @return true if PvC and false if PvP
   */
   public boolean checkGameMode() {
   
      return gameMode;
   }
   
   /**
   * Gets the name of player X
   * @return the String of the name of player X
   */
   public String getXName() {
   
      return xName;
   }
   
   /**
   * Gets the name of player O
   * @return the String of the name of player O
   */
   public String getOName() {
   
      return oName;
   }
   
   /**
   * Increses the win count for player X
   */
   public void increaseXWins() {
   
      xWins++;
   }
   
   /**
   * Increses the win count for player O
   */
   public void increaseOWins() {
   
      oWins++;
   }
   
   /**
   * Increses the tie count
   */
   public void increaseTies() {
   
      ties++;
   }
}
