import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TicTacToeTests {
    TicTacToe testModel = new TicTacToe();

    @Test
    void currentPlayer(){
        Player currentPlayer = Player.X;
        assertEquals(currentPlayer, testModel.currentPlayer());
    }
}