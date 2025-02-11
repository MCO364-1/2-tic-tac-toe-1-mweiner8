import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TicTacToeTests {
    TicTacToe testModel = new TicTacToe();

    @Test
    void currentPlayer(){
        TicTacToe.Player currentPlayer = TicTacToe.Player.X;
        assertEquals(currentPlayer, testModel.currentPlayer());
    }
}