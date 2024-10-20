import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class WhackAMole {
    int boardWidth = 600;
    int boardHeight = 650; //50 px for the text panel on top

    //Constructing the gui component
    JFrame frame = new JFrame("Mr bean : Whack A Mole");
    JLabel scoreLabel = new JLabel(); // Label to show the score
    JPanel scorePanel = new JPanel(); // Panel to store the score label
    JPanel boardPanel = new JPanel(); // We will store the buttons in this panel
    JButton[] board = new JButton[9];
    Levels levels = new Levels();

    JButton currMoleTile;
    JButton currPlantTile;

    ImageIcon mole;
    ImageIcon plant;

    Random random = new Random();
    Timer setMoleTimer;
    Timer setPlantTimer;
    int score;

    WhackAMole(){
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        scoreLabel.setFont(new Font("Arial",Font.PLAIN,50));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setText("Score: 0");
        scoreLabel.setOpaque(true);

        scorePanel.setLayout(new BorderLayout());
        boardPanel.setLayout(new GridLayout(3,3));
        scorePanel.add(scoreLabel);

        plant = levels.plantIcon;
        mole = levels.moleIcon;

        score = 0;
        for (int i = 0; i < 9; i++) {
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile);
            tile.setFocusable(false);
//            tile.setIcon(moleIcon);
            tile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton tile = (JButton) e.getSource();
                    if (tile == currMoleTile) {
                        score += 10;
                        scoreLabel.setText("Score: " + Integer.toString(score));
                    }
                    else if (tile == currPlantTile) {
                        scoreLabel.setText("Game Over . Your Score: " + Integer.toString(score));
                        setMoleTimer.stop();
                        setPlantTimer.stop();
                        for (int i = 0; i < 9; i++) {
                            board[i].setEnabled(false);

                        }
                    }
                }
            });
        }

        setMoleTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //remove mole from current tile
                if (currMoleTile != null) {
                    currMoleTile.setIcon(null);
                    currMoleTile = null;
                }

                //randomly select another tile
                int num = random.nextInt(9); //0-8
                JButton tile = board[num];

                //if tile is occupied by plant , skip tile for this turn
                if (currPlantTile == tile) return;

                //set tile to mole
                currMoleTile = tile;
                currMoleTile.setIcon(mole);

            }
        });

        setPlantTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currPlantTile != null) {
                    currPlantTile.setIcon(null);
                    currPlantTile = null;
                }
                int num = random.nextInt(9); // 0-8
                JButton tile = board[num];

                if (currMoleTile == tile) return;

                currPlantTile = tile;
                currPlantTile.setIcon(plant);
            }
        });

        frame.add(scorePanel,BorderLayout.NORTH);
        frame.add(boardPanel);

        setMoleTimer.start();
        setPlantTimer.start();
        frame.setVisible(true);
    }
}
