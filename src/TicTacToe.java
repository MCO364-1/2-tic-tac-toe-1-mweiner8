public class TicTacToe {
    public enum Player {X, O, _0}
    private final Player[][] board = new Player[3][3];
    private Player currentPlayerVal = Player.X;

    public Player getPosition(int x, int y){
        if (x < 0 || x > 2 || y < 0 || y > 2){
            throw new IndexOutOfBoundsException();
        } else if (board[x][y] != null) {
            throw new IllegalStateException("Spot has been already filled");
        }
        return board[x][y];
    }

    public Player currentPlayer(){
        return currentPlayerVal;
    }

    public void makeMove(int x, int y){
        if (getPosition(x, y) == null){
            board[x][y] = currentPlayerVal;
            if (currentPlayerVal.equals(Player.X)){
                currentPlayerVal = Player.O;
            } else {
                currentPlayerVal = Player.X;
            }
        }
    }

    public Player getWinner(){
        if ((board[0][0] == board[0][1] && board[0][1] == board[0][2] && board[0][0] != null) ||
            (board[0][0] == board[1][0] && board[1][0] == board[2][0] && board[0][0] != null) ||
            (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != null)){
            return board[0][0];
        } else if (board[1][0] == board[1][1] && board[1][1] == board[1][2] && board[1][0] != null) {
            return board[1][0];
        } else if (board[2][0] == board[2][1] && board[2][1] == board[2][2] && board[2][0] != null) {
            return board[2][0];
        } else if (board[0][1] == board[1][1] && board[1][1] == board[2][1] && board[0][1] != null) {
            return board[0][1];
        } else if ((board[0][2] == board[1][2] && board[1][2] == board[2][2] && board[0][2] != null) ||
                   (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != null)) {
            return board[0][2];
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == null){
                    return null;
                }
            }
        }
        return Player._0;
    }

    public void newGame(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = null;
            }
        }
    }
}