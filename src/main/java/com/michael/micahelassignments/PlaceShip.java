package com.michael.micahelassignments;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

public class PlaceShip extends JFrame implements MouseListener {
    JFrame window = new JFrame("Battleship ");
    JPanel borderPanel = new JPanel(new BorderLayout());

    JPanel mainPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JButton startButton = new JButton("Start");
    JButton flipButton = new JButton("Flip");
    JPanel[][] grid;
    int size;
    Map<Color,Ship> shipMap= new HashMap<>();

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

        generatePlayerShips();
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

        shipMap.entrySet().stream().map(e -> e.getValue()).forEach(thisship -> {
            while(true)
            {
                int randomX = random.nextInt(maxX + 1 - minX) + minX;
                int randomY = random.nextInt(maxY + 1 - minY) + minY;

                if(randomX + thisship.getWidth()  < maxX)
                {
                    if(randomY + thisship.getHeight()<  maxY)
                    {
                        //check that we are not overlapping with another ship
                        LinkedList<Point> points = new LinkedList<>();

                        for (int y = randomY; y <= thisship.getHeight()+randomY-1; y++)
                        {
                            for (int x = randomX;x <= thisship.getWidth()+randomX-1;x++ )
                            {
                                points.add(new Point(x,y));

                                //grid[y][x].setBackground(thisship.getColor());
                            }
                        }
                        if (addAndValidatePoints(points,thisship)== true)
                        {
                            break; //next ship
                        }
                    }
                }
            }
        });
    }



    private boolean addAndValidatePoints(LinkedList<Point> points,Ship thisship)
    {
        //loop to validate fir.st
        for (Point point : points)
        {
            if (grid[point.y][point.x].getBackground() !=Color.blue)
            {
                return false;
            }
        }

        //assign the first point from the list as the starting point for the ship
        Ship tempShip = shipMap.get(thisship.getColor());
        tempShip.setStartingPoint(points.get(0));
        shipMap.put(tempShip.getColor(),tempShip);

        //loop to apply color
        for (Point point : points)
        {
            grid[point.y][point.x].setBackground(thisship.getColor());
        }
        return true;
    }

    private void generatePlayerShips()
    {
        Ship carrier = new Ship(2,5, Color.green,true);
        Ship battleship = new Ship(1,4, Color.black,true);
        Ship destroyer = new Ship(1,3, Color.red,true);
        Ship submarine = new Ship(1,3, Color.yellow,true);
        Ship patrolBoat = new Ship(1,2, Color.gray,true);

        shipMap.put(carrier.getColor(),carrier);
        shipMap.put(battleship.getColor(),battleship);
        shipMap.put(destroyer.getColor(),destroyer);
        shipMap.put(submarine.getColor(),submarine);
        shipMap.put(patrolBoat.getColor(),patrolBoat);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (e.getSource() == grid[i][j]) {
                    System.out.println("mouse pressed " + i + " " + j);
                    if (grid[i][j].getBackground() !=Color.blue)
                    {
                        //System.out.println("color is " + grid[i][j].getBackground());

                        Ship tempShip = shipMap.get(grid[i][j].getBackground());
                        System.out.println(tempShip.getStartingPoint());
                    }
                }
            }
        }
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
