import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TicTacToeGUI extends JFrame {
    private final TicTacToe model;
    private int xWins = 0;
    private int oWins = 0;
    private Player currentPlayer;
    private final ArrayList<JButton> clickedButtons;
    private final Font myFont;

    public TicTacToeGUI(){
        model = new TicTacToe();
        clickedButtons = new ArrayList<>();
        myFont = new Font("Arial", Font.BOLD, 100);
        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setResizable(false); //it's always good to play it safe

        //Found this online to have the window be in the center of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - 500) / 2;
        int y = (screenSize.height - 500) / 2;
        setLocation(x, y);

        JPanel statusPanel = new JPanel(new GridLayout(1,4));
        currentPlayer = model.currentPlayer();
        JLabel currentPlayerSpot = new JLabel("Current Player: X");
        statusPanel.add(currentPlayerSpot);
        JLabel xWinsSpot = new JLabel("Wins for X: " + xWins);
        statusPanel.add(xWinsSpot);
        JLabel oWinsSpot = new JLabel("Wins for O: " + oWins);
        statusPanel.add(oWinsSpot);
        JButton newSeries = new JButton("New Series");
        newSeries.addActionListener(e -> {
            basicReset();
            currentPlayer = model.currentPlayer();
            if (currentPlayer == Player.O){
                model.switchPlayer();
                currentPlayer = Player.X;
            }
            currentPlayerSpot.setText("Current Player: X");
            xWins = 0;
            xWinsSpot.setText("Wins for X: " + xWins);
            oWins = 0;
            oWinsSpot.setText("Wins for O: " + oWins);
        });
        statusPanel.add(newSeries);
        add(statusPanel, BorderLayout.NORTH);
        JPanel grid = new JPanel(new GridLayout(3,3,5,5));
        add(grid, BorderLayout.CENTER);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton b = new JButton();
                grid.add(b);
                int finalI = i;
                int finalJ = j;
                b.addActionListener(e -> {
                    try{
                        model.makeMove(finalI, finalJ);
                        b.setFont(myFont);
                        b.setText(currentPlayer.toString());
                        clickedButtons.add(b);
                        Player winner = model.getWinner();
                        if (winner != null && winner != Player.TIE){
                            JOptionPane.showMessageDialog(grid, "Winner: " + winner);
                            if (winner == Player.X){
                                xWinsSpot.setText("Wins for X: " + ++xWins);
                            } else {
                                oWinsSpot.setText("Wins for O: " + ++oWins);
                            }
                            basicReset();
                        } else if (winner == Player.TIE) {
                            JOptionPane.showMessageDialog(grid, "Who would've thought? A game of tic tac toe ended in a tie!");
                            basicReset();
                        }
                        currentPlayer = model.currentPlayer();
                        currentPlayerSpot.setText("Current Player: " + currentPlayer);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(grid, ex.getMessage());
                    }
                });
            }
        }

        setVisible(true);
    }

    private void basicReset(){
        model.newGame();
        for (JButton button : clickedButtons){
            button.setText("");
        }
        clickedButtons.clear();
    }
}