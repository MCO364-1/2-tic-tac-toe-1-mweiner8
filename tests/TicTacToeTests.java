import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TicTacToeTests {
    TicTacToe testModel = new TicTacToe();

    @Test
    void currentPlayer(){
        Player currentPlayer = Player.X;
        assertEquals(currentPlayer, testModel.currentPlayer());
    }

    @Test
    void getStartingPositions(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertNull(testModel.getPosition(i, j));
            }
        }
    }

    @Test
    void makeMove(){
        testModel.makeMove(1,1);
        assertEquals(Player.X, testModel.getPosition(1,1));
        assertEquals(Player.O, testModel.currentPlayer());
        assertNull(testModel.getWinner());
    }

    @Test
    void checkForTie(){
        testModel.makeMove(1,1);
        testModel.makeMove(0,0);
        testModel.makeMove(1,0);
        testModel.makeMove(1,2);
        testModel.makeMove(0,1);
        testModel.makeMove(2,1);
        testModel.makeMove(2,0);
        testModel.makeMove(0,2);
        testModel.makeMove(2,2);
        assertEquals(Player.TIE, testModel.getWinner());
    }

    @Test
    void checkForWinner(){
        testModel.makeMove(1,1);
        testModel.makeMove(0,0);
        testModel.makeMove(1,0);
        testModel.makeMove(0,1);
        testModel.makeMove(1,2);
        assertEquals(Player.X, testModel.getWinner());
    }

    @Test
    void goToNextGame(){
        testModel.makeMove(1,1);
        testModel.makeMove(0,0);
        testModel.makeMove(1,0);
        testModel.makeMove(0,1);
        testModel.makeMove(1,2);
        assertEquals(Player.X, testModel.getWinner());
        assertEquals(Player.X, testModel.getPosition(1,1));
        testModel.newGame();
        testModel.makeMove(1,1);
        assertEquals(Player.O, testModel.getPosition(1,1));
    }
}