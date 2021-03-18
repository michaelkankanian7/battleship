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
    JButton startButton = new JButton("Start");
    JButton flipButton = new JButton("Flip");
    JPanel[][] playerGrid;
    JPanel[][] computerGrid;
    int size;

    private boolean isPlayerTurn = true;

    public GamePlay(int size, JPanel[][] playerGrid) {
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
        Helper.placeShipsRandomly(computerGrid, Helper.computerShipMap, Color.darkGray , false);
        Helper.randomizeShipOrientation(computerGrid,Helper.computerShipMap,Color.darkGray,false);

        playerPanel.setPreferredSize(new Dimension(400, 400));
        borderPanel.add(playerPanel, BorderLayout.CENTER);

        computerPanel.setPreferredSize(new Dimension(400, 400));
        borderPanel.add(computerPanel, BorderLayout.NORTH);

        buttonPanel.add(startButton);
        buttonPanel.add(flipButton);


        window.setSize(900, 900);
        window.setTitle("Game Play Window");

        window.add(borderPanel);

        window.setVisible(true);
    }

    public boolean checkVictoryCondition() {
        boolean allComputerShipsDestroyed = true;
        for (int i = 0; i < computerGrid.length; i++) {
            for (int j = 0; j < computerGrid[0].length; j++) {
                    if (computerGrid[i][j].getName() != null) {
                        //the name attribute in the grid cell is the name of the ship
                        if (computerGrid[i][j].getBackground() != Color.pink)
                        {
                            allComputerShipsDestroyed = false;
                        }
                    }
                }
        }
        if (allComputerShipsDestroyed)
        {
            System.out.println("computer lost .. player wins !!!!!");
            return true;
        }

        boolean allPlayerShipsDestroyed = true;
        for (int i = 0; i < playerGrid.length; i++) {
            for (int j = 0; j < playerGrid[0].length; j++) {
                if (playerGrid[i][j].getName() != null) {
                    //the name attribute in the grid cell is the name of the ship
                    if (playerGrid[i][j].getBackground() != Color.pink)
                    {
                        allPlayerShipsDestroyed = false;
                    }
                }
            }
        }
        if (allPlayerShipsDestroyed)
        {
            System.out.println("computer Wins .. player Lost !!!!!");
            return true;
        }

        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!checkVictoryCondition()) {
            for (int i = 0; i < computerGrid.length; i++) {
                for (int j = 0; j < computerGrid[0].length; j++) {
                    if (e.getSource() == computerGrid[i][j]) {
                        //we found where the user clicked

                        //make sure the cell is not already clicked
                        if (!isCellClickedBefore(i,j , computerGrid))
                        {
                            if (computerGrid[i][j].getName() != null) {
                                //the name attribute in the grid cell is the name of the ship
                                Ship tempShip = Helper.computerShipMap.get(computerGrid[i][j].getName());
                                computerGrid[i][j].setBackground(Color.PINK);
                                System.out.println("player clicked on " + tempShip.getShipName());
                            } else {
                                System.out.println("Player Miss!!");
//                        Label x = new Label();
//                        x.setText("Miss!!");
//                        x.setVisible(true);
//                        x.setAlignment(Label.CENTER);
//                        x.setFont(new Font(null,Font.BOLD,20));
//                        computerGrid[i][j].add(x);
                                computerGrid[i][j].setBackground(Color.white);
                            }

                            if (!checkVictoryCondition())
                            {
                                computerTurn();
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isCellClickedBefore(int i , int j ,JPanel[][] designatedGrid)
    {
        if (designatedGrid[i][j].getBackground() != Color.pink)
        {
            if (designatedGrid[i][j].getBackground() != Color.white)
            {
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

        while(true)
        {
            randomX = random.nextInt(maxX  - minX) + minX;
            randomY = random.nextInt(maxY - minY) + minY;

            if (!isCellClickedBefore(randomX,randomY , playerGrid))
            {
                break;
            }
        }

        if (playerGrid[randomX][randomY].getName() != null)
        {
            playerGrid[randomX][randomY].setBackground(Color.PINK);
        } else
        {
            playerGrid[randomX][randomY].setBackground(Color.white);
        }

        if (checkVictoryCondition()) {
            System.out.println("Game Ended");
        }

        isPlayerTurn=true;
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
