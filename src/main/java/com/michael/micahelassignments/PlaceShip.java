package com.michael.micahelassignments;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlaceShip extends JFrame implements MouseListener {
    JFrame window = new JFrame("Battleship ");
    JPanel borderPanel = new JPanel(new BorderLayout());

    JPanel mainPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JButton startButton = new JButton("Start");
    JButton flipButton = new JButton("Flip");

    JPanel[][] grid;

    int size;

    public PlaceShip(int size) {
        this.size = size;
        mainPanel.setLayout(new GridLayout(size, size, 2, 2));
        grid = new JPanel[size][size];

        System.out.println(grid.length);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.println(i + "   " + j);
                grid[i][j] = new JPanel();
                mainPanel.add(grid[i][j]);
                grid[i][j].addMouseListener(this);
                grid[i][j].setBackground(Color.BLUE);

            }
        }

        borderPanel.add(mainPanel, BorderLayout.CENTER);
        borderPanel.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(startButton);
        buttonPanel.add(flipButton);

        placeShipsRandomly();

        window.setSize(300, 300);
        window.add(borderPanel);
        window.setVisible(true);

        //nextButton.addMouseListener(this);
    }

    private void placeShipsRandomly()
    {
        int maxY = grid.length;
        int minY = 0;
        int maxX = grid[0].length;
        int minX = 0;

        Random random = new Random();

        generatePlayerShips().forEach(thisship -> {

            while(true)
            {
                int randomX = random.nextInt(maxX + 1 - minX) + minX;
                int randomY = random.nextInt(maxY + 1 - minY) + minY;

                if(randomX + thisship.getWidth()  < maxX)
                {
                    if(randomY + thisship.getHeight()<  maxY)
                    {

                        //check that we are not overlapping with another ship

                        for (int y = randomY; y <= thisship.getHeight()+randomY-1; y++)
                        {
                            for (int x = randomX;x <= thisship.getWidth()+randomX-1;x++ )
                            {
                                grid[y][x].setBackground(thisship.getColor());
                            }
                        }
                        break;
                    }
                }
            }
        });
    }

    private List<Ship> generatePlayerShips()
    {
        Ship carrier = new Ship(2,5, Color.green,true);
        Ship battleship = new Ship(1,4, Color.black,true);
        Ship destroyer = new Ship(1,3, Color.red,true);
        Ship submarine = new Ship(1,3, Color.yellow,true);
        Ship patrolBoat = new Ship(1,2, Color.gray,true);

        List<Ship> lst = new ArrayList<>();
        lst.add(carrier);
        lst.add(battleship);
        lst.add(destroyer);
        lst.add(submarine);
        lst.add(patrolBoat);
        return lst;

    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
