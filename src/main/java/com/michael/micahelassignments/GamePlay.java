package com.michael.micahelassignments;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

public class GamePlay implements MouseListener {
    JFrame window = new JFrame("Battleship ");
    JPanel borderPanel = new JPanel(new BorderLayout());
    //JPanel computerBorderPanel = new JPanel(new BorderLayout());

    JPanel playerPanel = new JPanel();
    JPanel computerPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JButton quitButton = new JButton("Quit");
    JButton endGameButton = new JButton("End Game");
    JPanel[][] playerGrid;
    JPanel[][] computerGrid;
    int size;

    //stats
    JPanel statsPanel = new JPanel();
    JLabel computerHitsLabel = new JLabel("0");
    JLabel computerMissesLabel = new JLabel("0");
    JLabel computerShipsSailingLabel = new JLabel("0");
    JLabel computerShipsSunkLabel = new JLabel("0");
    JLabel playerHitsLabel = new JLabel("0");
    JLabel playerMissesLabel = new JLabel("0");
    JLabel playerShipsSailingLabel = new JLabel("0");
    JLabel playerShipsSunkLabel = new JLabel("0");

    public GamePlay(int size, JPanel[][] playerGrid , JFrame topFrame) {
        this.size = size;
        this.playerGrid = playerGrid;
        //remove mouse listener from player grid
        for (int i = 0; i < this.playerGrid.length; i++) {
            for (int j = 0; j < this.playerGrid[0].length; j++) {
                MouseListener[] mouseListeners = this.playerGrid[i][j].getMouseListeners();
                this.playerGrid[i][j].removeMouseListener(mouseListeners[0]);
            }
        }

        //initialize player panel
        playerPanel.setLayout(new GridLayout(size, size, 2, 2));

        for (int i = 0; i < playerGrid.length; i++) {
            for (int j = 0; j < playerGrid[0].length; j++) {
                playerPanel.add(playerGrid[i][j]);
            }
        }

        //initialize computer panel
        computerPanel.setLayout(new GridLayout(size, size, 2, 2));
        computerGrid = new JPanel[size][size];
        for (int i = 0; i < computerGrid.length; i++) {
            for (int j = 0; j < computerGrid[0].length; j++) {
                computerGrid[i][j] = new JPanel();
                computerPanel.add(computerGrid[i][j]);
                computerGrid[i][j].setBackground(Color.DARK_GRAY);

                computerGrid[i][j].addMouseListener(this);
            }
        }
        Helper.placeShipsRandomly(computerGrid, Helper.computerShipMap, Color.darkGray, false);
        Helper.randomizeShipOrientation(computerGrid, Helper.computerShipMap, Color.darkGray, false);

        playerPanel.setPreferredSize(new Dimension(400, 400));
        borderPanel.add(playerPanel, BorderLayout.CENTER);

        computerPanel.setPreferredSize(new Dimension(400, 400));
        borderPanel.add(computerPanel, BorderLayout.NORTH);

        //stats panel
        //statsPanel.setLayout(new );
        statsPanel.setPreferredSize(new Dimension(100,100));
        statsPanel.add(new JLabel("Player Hits"));
        statsPanel.add(playerHitsLabel);
        statsPanel.add(new JLabel("Player Misses"));
        statsPanel.add(playerMissesLabel);
        statsPanel.add(new JLabel("Computer Hits"));
        statsPanel.add(computerHitsLabel);
        statsPanel.add(new JLabel("Computer Misses"));
        statsPanel.add(computerMissesLabel);
        statsPanel.add(new JLabel("Player Ships Sunk"));
        statsPanel.add(playerShipsSunkLabel);
        statsPanel.add(new JLabel("Player Ships Sailing"));
        statsPanel.add(playerShipsSailingLabel);
        statsPanel.add(new JLabel("Computer Ships Sunk"));
        statsPanel.add(computerShipsSunkLabel);
        statsPanel.add(new JLabel("Computer Ships Sailing"));
        statsPanel.add(computerShipsSailingLabel);

        statsPanel.add(quitButton);
        statsPanel.add(endGameButton);
        borderPanel.add(statsPanel, BorderLayout.SOUTH);


        quitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(1);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        endGameButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                window.dispose();
                PlaceShip placeShip = new PlaceShip(size);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

//        buttonPanel.add(quitButton);
//        buttonPanel.add(endGameButton);
//        borderPanel.add(buttonPanel, BorderLayout.PAGE_END);


        window.setSize(900, 900);
        window.setTitle("Game Play Window");

        window.add(borderPanel);

        window.setVisible(true);
    }

    public boolean checkVictoryCondition() {
        computeShipStatisticsForComputer();
        computeShipStatisticsForPlayer();
        boolean allComputerShipsDestroyed = true;
        for (int i = 0; i < computerGrid.length; i++) {
            for (int j = 0; j < computerGrid[0].length; j++) {
                if (computerGrid[i][j].getName() != null) {
                    //the name attribute in the grid cell is the name of the ship
                    if (computerGrid[i][j].getBackground() != Color.pink) {
                        allComputerShipsDestroyed = false;
                    }
                }
            }
        }
        if (allComputerShipsDestroyed) {
            System.out.println("computer lost .. player wins !!!!!");
            return true;
        }

        boolean allPlayerShipsDestroyed = true;
        for (int i = 0; i < playerGrid.length; i++) {
            for (int j = 0; j < playerGrid[0].length; j++) {
                if (playerGrid[i][j].getName() != null) {
                    //the name attribute in the grid cell is the name of the ship
                    if (playerGrid[i][j].getBackground() != Color.pink) {
                        allPlayerShipsDestroyed = false;
                    }
                }
            }
        }
        if (allPlayerShipsDestroyed) {
            System.out.println("computer Wins .. player Lost !!!!!");
            return true;
        }

        return false;
    }

    private void computeShipStatisticsForComputer()
    {
        Map<String,Integer> computerShipStats = new HashMap<>();

        Helper.computerShipMap.keySet().forEach(k -> computerShipStats.put(k,0));
        for (int i = 0; i < computerGrid.length; i++) {
            for (int j = 0; j < computerGrid[0].length; j++) {
                if (computerGrid[i][j].getBackground() == Color.pink) {
                    int hitCountForShip = computerShipStats.get(computerGrid[i][j].getName());
                    hitCountForShip = hitCountForShip + 1;
                    computerShipStats.put(computerGrid[i][j].getName() ,hitCountForShip );
                }
            }
        }

        Integer shipsSunk = 0;
        Integer shipsSailing = 0;
        for (String shipname : Helper.computerShipMap.keySet())
        {
            if (computerShipStats.get(shipname) <
                    (Helper.computerShipMap.get(shipname).getHeight() *
                            Helper.computerShipMap.get(shipname).getWidth()))
            {
                shipsSailing = shipsSailing + 1;
            } else
            {
                shipsSunk = shipsSunk + 1;
            }
        }

        computerShipsSunkLabel.setText(shipsSunk.toString());
        computerShipsSailingLabel.setText(shipsSailing.toString());
    }

    private void computeShipStatisticsForPlayer()
    {
        Map<String,Integer> playerShipStatistics = new HashMap<>();

        Helper.playerShipMap.keySet().forEach(k -> playerShipStatistics.put(k,0));
        for (int i = 0; i < playerGrid.length; i++) {
            for (int j = 0; j < playerGrid[0].length; j++) {
                if (playerGrid[i][j].getBackground() == Color.pink) {
                    int hitCountForShip = playerShipStatistics.get(playerGrid[i][j].getName());
                    hitCountForShip = hitCountForShip + 1;
                    playerShipStatistics.put(playerGrid[i][j].getName() ,hitCountForShip );
                }
            }
        }

        Integer shipsSunk = 0;
        Integer shipsSailing = 0;
        for (String shipname : Helper.playerShipMap.keySet())
        {
            if (playerShipStatistics.get(shipname) <
                    (Helper.playerShipMap.get(shipname).getHeight() *
                            Helper.playerShipMap.get(shipname).getWidth()))
            {
                shipsSailing = shipsSailing + 1;
            } else
            {
                shipsSunk = shipsSunk + 1;
            }
        }

        playerShipsSunkLabel.setText(shipsSunk.toString());
        playerShipsSailingLabel.setText(shipsSailing.toString());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!checkVictoryCondition()) {
            for (int i = 0; i < computerGrid.length; i++) {
                for (int j = 0; j < computerGrid[0].length; j++) {
                    if (e.getSource() == computerGrid[i][j]) {
                        //we found where the user clicked

                        //make sure the cell is not already clicked
                        if (!isCellClickedBefore(i, j, computerGrid)) {
                            //HIT Condition
                            if (computerGrid[i][j].getName() != null) {
                                //the name attribute in the grid cell is the name of the ship
                                Ship tempShip = Helper.computerShipMap.get(computerGrid[i][j].getName());
                                computerGrid[i][j].setBackground(Color.PINK);
                                System.out.println("player clicked on " + tempShip.getShipName());
                                incrementPlayerHits();
                            } else { //Miss Condition
                                System.out.println("Player Miss!!");
                                incrementPlayerMisses();
//                        Label x = new Label();
//                        x.setText("Miss!!");
//                        x.setVisible(true);
//                        x.setAlignment(Label.CENTER);
//                        x.setFont(new Font(null,Font.BOLD,20));
//                        computerGrid[i][j].add(x);
                                computerGrid[i][j].setBackground(Color.white);
                            }

                            if (!checkVictoryCondition()) {
                                computerTurn();
                            }
                        }
                    }
                }
            }
        }
    }

    private void incrementPlayerMisses() {
        int misses = Integer.parseInt(playerMissesLabel.getText()) + 1;
        playerMissesLabel.setText(Integer.toString(misses));
    }

    private void incrementPlayerHits() {
        int hits = Integer.parseInt(playerHitsLabel.getText()) + 1;
        playerHitsLabel.setText(Integer.toString(hits));
    }

    private boolean isCellClickedBefore(int i, int j, JPanel[][] designatedGrid) {
        if (designatedGrid[i][j].getBackground() != Color.pink) {
            if (designatedGrid[i][j].getBackground() != Color.white) {
                return false;
            }
        }
        return true;
    }

    private void computerTurn() {

        System.out.println("computer turn");
        Random random = new Random();

        int maxY = playerGrid.length;
        int minY = 0;
        int maxX = playerGrid[0].length;
        int minX = 0;

        int randomX = 0;
        int randomY = 0;

        while (true) {
            randomX = random.nextInt(maxX - minX) + minX;
            randomY = random.nextInt(maxY - minY) + minY;

            if (!isCellClickedBefore(randomX, randomY, playerGrid)) {
                break;
            }
        }

        if (playerGrid[randomX][randomY].getName() != null) {
            playerGrid[randomX][randomY].setBackground(Color.PINK);
            incrementComputerHits();
        } else {
            playerGrid[randomX][randomY].setBackground(Color.white);
            incrementComputerMisses();
        }

        if (checkVictoryCondition()) {
            System.out.println("Game Ended");
        }
    }

    private void incrementComputerMisses() {
        int misses = Integer.parseInt(computerMissesLabel.getText()) + 1;
        computerMissesLabel.setText(Integer.toString(misses));
    }

    private void incrementComputerHits() {
        int hits = Integer.parseInt(computerHitsLabel.getText()) + 1;
        computerHitsLabel.setText(Integer.toString(hits));
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
