import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

/**
* The TicTacToeMouseListener.java is a mouse listener that accompanies the TicTacToeGame.java for the final project
* This extends MouseInputAdapter and handles the users mouse clicks
* @authov Abhi Battepati, Section A3
*/
public class TicTacToeMouseListener extends MouseInputAdapter {

    private TicTacToeGame game;
    
    /**
    * Initializes the mouse listener with the with the game
    * @param game is TicTacToeGame instance that it being interacted with
    */
    public TicTacToeMouseListener(TicTacToeGame game) {
    
        this.game = game;
    }
   
    /**
    * This method is called when the mouse is clicked
    * It helps components of the game like placing characters, checking for winners, and the end of the game function
    * @param event is the MouseEvent when the mouse is clicked
    */
    @Override
    public void mousePressed(MouseEvent event) {
        
        // Resets game if ended
        if (this.game.checkGameEnded()) {
            
            this.game.resetGame();
        }
        
        else {
            
            // Coordinates and position of the mouse click 
            int x = event.getX() / TicTacToeGame.SIZE;
            int y = event.getY() / TicTacToeGame.SIZE;
            
            // Checks if the click is within board and if slot in empty
            if (x >= 0 && x < 3 && y >= 0 && y < 3 && this.game.getBoard()[y][x] == '\0') {
                
                this.game.getBoard()[y][x] = this.game.checkPlayerTurn() ? 'X' : 'O';
                this.game.placeCharacter(y, x, this.game.getBoard()[y][x]);
                
                // Checks for winner/tie
                char winner = TicTacToeUtils.checkWinner(this.game.getBoard());
                if (winner == 'X' || winner == 'O') {
                
                    this.game.outcomeMessage((winner == 'X' ? this.game.getXName() : this.game.getOName()) + " wins!");
                    this.game.setGameEnded(true);
                    
                    // Updates the win counter
                    if (winner == 'X') {
                    
                        this.game.increaseXWins();
                    }
                    
                    else {
                    
                        this.game.increaseOWins();
                    }
                    this.game.playAgain();
                } 
                
                // Updates tie and game stats
                else if (winner == 'T') {
                
                    this.game.outcomeMessage("It's a tie!");
                    this.game.setGameEnded(true);
                    this.game.increaseTies();
                    this.game.playAgain();
                }
                
                else {
                
                    this.game.setPlayerTurn(!this.game.checkPlayerTurn());
                    // If PvC and computer turn
                    if (!this.game.checkGameMode() && !this.game.checkPlayerTurn()) {
                    
                        // Calculates best move for computer
                        int[] move = TicTacToeUtils.bestMove(this.game.getBoard());
                        this.game.getBoard()[move[0]][move[1]] = 'O';
                        this.game.placeCharacter(move[0], move[1], 'O');
                        winner = TicTacToeUtils.checkWinner(this.game.getBoard());
                        
                        // Check for winner or a tie after computer's move
                        if (winner == 'X' || winner == 'O') {
                        
                            this.game.outcomeMessage((winner == 'X' ? this.game.getXName() : this.game.getOName()) + " wins!");
                            this.game.setGameEnded(true);
                            
                            if (winner == 'X') {
                            
                                this.game.increaseXWins();
                            }
                            
                            else {
                            
                                this.game.increaseOWins();
                            }
                            this.game.playAgain();
                        }
                        
                        else if (winner == 'T') {
                        
                            this.game.outcomeMessage("It's a tie!");
                            this.game.setGameEnded(true);
                            this.game.increaseTies();
                            this.game.playAgain();
                        }
                        
                        else {
                            // Switches turn back to player
                            this.game.setPlayerTurn(true);
                        }
                    }
                }
            }
        }
    }
}
