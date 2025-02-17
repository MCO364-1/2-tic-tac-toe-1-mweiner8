import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class TicTacToeGUI extends JFrame {
    private final TicTacToe model;
    private int xWins = 0;
    private int oWins = 0;
    private Player currentPlayer;
    private Player firstPlayer;
    private final ArrayList<JButton> clickedButtons; //I need a way to keep track of the buttons, so I can clear them between games
    private final Font myBigFont;
    private boolean manVsMan = true;
    private final Random rand = new Random();
    private final JButton[][] buttons = new JButton[3][3];
    private boolean compWent = false;

    public TicTacToeGUI(){
        model = new TicTacToe();
        clickedButtons = new ArrayList<>();
        myBigFont = new Font("Arial", Font.BOLD, 100); //I looked up how to make the letters bigger and saw about this
        Font mySmallFont = new Font("Arial", Font.BOLD, 16);
        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setResizable(false); //it's always good to play it safe

        //Found this online to have the window be in the center of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - 500) / 2;
        int y = (screenSize.height - 500) / 2;
        setLocation(x, y);

        JPanel statusPanel = new JPanel(new GridLayout(1,3, 5, 5));
        currentPlayer = model.currentPlayer();
        firstPlayer = model.currentPlayer();
        JLabel currentPlayerSpot = new JLabel("Current Player: X");
        currentPlayerSpot.setFont(mySmallFont);
        statusPanel.add(currentPlayerSpot);
        JLabel xWinsSpot = new JLabel("Wins for X: " + xWins);
        xWinsSpot.setFont(mySmallFont);
        statusPanel.add(xWinsSpot);
        JLabel oWinsSpot = new JLabel("Wins for O: " + oWins);
        oWinsSpot.setFont(mySmallFont);
        statusPanel.add(oWinsSpot);
        add(statusPanel, BorderLayout.NORTH);

        JPanel bottomButtons = new JPanel(new GridLayout(1,2, 5, 5));
        JButton resetGame = new JButton("Reset Game");
        resetGame.setFont(mySmallFont);
        resetGame.addActionListener(e -> {
            currentPlayer = firstPlayer;
            if (currentPlayer != model.currentPlayer()){
                model.switchPlayer();
            }
            basicReset();
            currentPlayerSpot.setText("Current Player: " + currentPlayer);
            if (firstPlayer == Player.O){
                compWent = true;
                compButton().doClick();
            }
        });
        bottomButtons.add(resetGame);
        JButton newSeries = new JButton("New Series");
        newSeries.setFont(mySmallFont);
        newSeries.addActionListener(e -> {
            basicReset();
            currentPlayer = model.currentPlayer();
            if (currentPlayer == Player.O){
                model.switchPlayer();
                currentPlayer = Player.X;
            }
            firstPlayer = model.currentPlayer();
            currentPlayerSpot.setText("Current Player: X");
            xWins = 0;
            xWinsSpot.setText("Wins for X: " + xWins);
            oWins = 0;
            oWinsSpot.setText("Wins for O: " + oWins);
        });
        bottomButtons.add(newSeries);
        add(bottomButtons, BorderLayout.SOUTH);

        JPanel grid = new JPanel(new GridLayout(3,3,5,5));
        add(grid, BorderLayout.CENTER);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton b = new JButton();
                buttons[i][j] = b;
                grid.add(b);
                int finalI = i;
                int finalJ = j;
                b.addActionListener(e -> {
                    try{
                        model.makeMove(finalI, finalJ);
                        b.setFont(myBigFont);
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
                            firstPlayer = model.currentPlayer();
                        } else if (winner == Player.TIE) {
                            JOptionPane.showMessageDialog(grid, "Who would've thought? A game of tic tac toe ended in a tie!");
                            basicReset();
                            firstPlayer = model.currentPlayer();
                        }
                        currentPlayer = model.currentPlayer();
                        currentPlayerSpot.setText("Current Player: " + currentPlayer);
                        if (!manVsMan && !compWent){
                            compWent = true;
                            compButton().doClick();
                        }
                        compWent = false;
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(grid, ex.getMessage());
                    }
                });
            }
        }

        setVisible(true);

        Object[] options = {"Man vs. Man", "Man vs. Machine"};
        int choice = JOptionPane.showOptionDialog(grid, "How would you like to play", "Tic-Tac-Toe", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
        if (choice == 1){
            manVsMan = false;
        }

    }

    private void basicReset(){
        model.newGame();
        for (JButton button : clickedButtons){
            button.setText("");
        }
        clickedButtons.clear();
    }

    private JButton compButton(){
        int x;
        int y;
        do {
            x = rand.nextInt(3);
            y = rand.nextInt(3);
        } while (model.getPosition(x, y) != null);
        return buttons[x][y];
    }
}