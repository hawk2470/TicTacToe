/**
* TicTacToeUtils.java accompanies TicTacToeGame.java for the final project
* This program checks the winner of the game and has the unbeatable computer algorithm 
* @authov Abhi Battepati, Section A3
*/
public class TicTacToeUtils {

   /**
   * Checks the gameboard to see if there is a winner (3 in a row of same token)
   * @param board is the Tic-Tac-Toe gameboard
   * @return 'X' if player x wins, 'O' if player o wins, 'T' if it is a tie, and '\0' if the game is still going
   */
   // checkWinner method logic was inspired by (but it was mostly done by me): 
   // https://www.youtube.com/watch?v=rA7tfvpkw0I and https://www.youtube.com/watch?v=Nc77ymnm8Ss
   public static char checkWinner(char[][] board) {
   
      // Checks the rows if someone won
      for (int row = 0; row < 3; row++) {
      
         if (board[row][0] != '\0' && board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
         
            return board[row][0];
         }
      }
      
      // Checks the columns if someone won
      for (int col = 0; col < 3; col++) {
      
         if (board[0][col] != '\0' && board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
         
            return board[0][col];
         }
      }
      
      // Checks the first diagonal if someone won
      if (board[0][0] != '\0' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
      
         return board[0][0];
      }
      
      // Checks the second diagonal if someone won
      if (board[0][2] != '\0' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
      
         return board[0][2];
      }
      
      // Checks if the board is full
      // this was also inspired by one of the videos above
      // but again, I pretty much did it all myself but just in case I cited the sources     
      boolean boardFull = true;
      for (int row = 0; row < 3; row++) {
      
         for (int col = 0; col < 3; col++) {
         
            if (board[row][col] == '\0') {
            
               boardFull = false;
            }
         }
      }
      
      if (boardFull) {
         
         // For tie
         return 'T';
      }
      
      else {
      
         // If game still going
         return '\0';
      }
   }
   
   /**
   * Uses minimax algorithm to calculate the best move
   * @param board is the Tic-Tac-Toe gameboard
   * @param maximize is a boolean flag that decides to maximize or minimize score
   * @return the score of the board for the move
   */
   // Got minimax algorithm for these two but did a lot of the implementation myself:
   // https://stackoverflow.com/questions/62813731/minimax-algorithm-in-java
   // https://www.geeksforgeeks.org/finding-optimal-move-in-tic-tac-toe-using-minimax-algorithm-in-game-theory/
   private static int minimax(char[][] board, boolean maximize) {
   
      char winner = checkWinner(board);
   
      // Score 100 for O win
      if (winner == 'O') {
      
         return 100;
      }
   
      // Score -100 for X win
      if (winner == 'X') {
      
         return -100;
      }
   
      // Score 0 for tie
      if (winner == 'T') {
      
         return 0;
      }
   
      if (maximize) {
      
         int bestScore = Integer.MIN_VALUE;
         // Loops the rows
         for (int i = 0; i < 3; i++) {
         
            // Loops the columns
            for (int j = 0; j < 3; j++) {
               
               // Checks for empty cell
               if (board[i][j] == '\0') {
                  
                  // O move
                  board[i][j] = 'O';
                  // Calls minimax to minimizing player recursively
                  int score = minimax(board, false);
                  // Undos the move
                  board[i][j] = '\0';
                  // Updates score
                  bestScore = Math.max(score, bestScore);
               }
            }
         }
         
         return bestScore;
      }
      
      else {
      
         int bestScore = Integer.MAX_VALUE;
         // Loops the row
         for (int i = 0; i < 3; i++) {
            // Loops the col
            for (int j = 0; j < 3; j++) {
               
               // Checks for empty cell
               if (board[i][j] == '\0') {
                  
                  // X move
                  board[i][j] = 'X';
                  // Calls minimax to maximizing player recursively
                  int score = minimax(board, true);
                  // Undos the move
                  board[i][j] = '\0';
                  // Updates score
                  bestScore = Math.min(score, bestScore);
               }
            }
         }
         
         return bestScore;
      }
   }

   /**
   * Decides the best move using the minimax algorithm for the computer
   * @param board is the Tic-Tac-Toe gameboard
   * @return an array with the row and col of the best move
   */
   public static int[] bestMove(char[][] board) {
   
      int bestScore = Integer.MIN_VALUE;
      // Array storing the best move
      int[] move = new int[2];
   
      // Loops rows
      for (int i = 0; i < 3; i++) {
         
         // Loops cols
         for (int j = 0; j < 3; j++) {
         
            // Check for empty cell
            if (board[i][j] == '\0') {
            
               // O Move
               board[i][j] = 'O';
               
               // Minimax score for move
               int score = minimax(board, false);
               // Undos the move
               board[i][j] = '\0';
               if (score > bestScore) {
                  
                  // Updates the score
                  bestScore = score;
                  // Updates the move row
                  move[0] = i;
                  // Updates the move col
                  move[1] = j;
               }
            }
         }
      }
   
      return move;
   }
}
