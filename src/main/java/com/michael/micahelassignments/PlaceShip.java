package com.michael.micahelassignments;

import org.apache.commons.lang3.ArrayUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;

public class PlaceShip extends JFrame implements MouseListener , MouseMotionListener  {
    JFrame window = new JFrame("Battleship ");
    JPanel borderPanel = new JPanel(new BorderLayout());

    JPanel mainPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JButton startButton = new JButton("Start");
    JButton flipButton = new JButton("Flip");
    JLabel selectedLabel = new JLabel("Nothing Selected");
    JPanel[][] grid;
    int size;

    private Ship selectedShip;
    private Point selectedPoint;

    public PlaceShip(int size) {
        this.size = size;
        mainPanel.setLayout(new GridLayout(size, size, 2, 2));
        grid = new JPanel[size][size];

        System.out.println(grid.length);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //System.out.println(i + "   " + j);
                grid[i][j] = new JPanel();
                mainPanel.add(grid[i][j]);
                grid[i][j].addMouseListener(this);
                grid[i][j].addMouseMotionListener(this);
                grid[i][j].setBackground(Color.BLUE);

            }
        }

        flipButton.addMouseListener(this);

        borderPanel.add(mainPanel, BorderLayout.CENTER);
        borderPanel.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(startButton);
        buttonPanel.add(flipButton);
        buttonPanel.add(selectedLabel);

        Helper.placeShipsRandomly(grid , Helper.playerShipMap , Color.blue , true);

        window.setSize(300, 300);
        window.add(borderPanel);
        window.setVisible(true);

        startButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JButton b = (JButton) e.getSource();
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(b);
                topFrame.setVisible(false);
                GamePlay gamePlay = new GamePlay(size , grid , topFrame);

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


        //nextButton.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == flipButton)
        {
            if (!selectedLabel.getText().equals("Nothing Selected"))
            {
                //System.out.println("flip button clicked");
                flipShip();
            }
        }
    }

    private void flipShip()
    {
        if (selectedShip != null )
        {
            System.out.println("will attempt to file ship " + selectedShip.getShipName());
            //check if possible to flip this ship
            Helper.attemptToFlipShip(grid,Color.blue,selectedShip , Helper.playerShipMap , true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (e.getSource() == grid[i][j]) {
                    System.out.println("mouse pressed " + i + " " + j);
                    if (grid[i][j].getName() != null)
                    {
                        //the name attribute in the grid cell is the name of the ship
                        Ship tempShip = Helper.playerShipMap.get(grid[i][j].getName());
                        selectedShip = tempShip;
                        selectedPoint = new Point(j,i);
                        //System.out.println(tempShip.getColor());
                        System.out.println("selected " + tempShip.getShipName());
                        selectedLabel.setText(selectedShip.getShipName());
                    } else
                    {
                        selectedShip = null;
                        selectedPoint = null;
                        selectedLabel.setText("Nothing Selected");
                        System.out.println("selected Nothing");
                    }
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //if a ship is seleccted then figure out if it can be moved here then move it
        //then clear the selected ship

        //if no ship is selecetd do nothing
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int buttonsDownMask =MouseEvent.BUTTON1_DOWN_MASK | MouseEvent.BUTTON2_DOWN_MASK;

        synchronized (this)
        {
            if ((e.getModifiersEx() & buttonsDownMask) !=0)
            {
                //System.out.println("Mouse Moved");
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[0].length; j++) {
                        Point p = MouseInfo.getPointerInfo().getLocation();
                        //SwingUtilities.convertPointFromScreen(p,grid[i][j]);
                        if(isWithinBounds(p.x,p.y,grid[i][j]))
                        {
                            if (grid[i][j].getName() != null)
                            {
                                System.out.println(selectedPoint);
                            }
                            System.out.println("i="+i + " j=" + j);

                            int xOffset = selectedPoint.x-j;
                            int yOffset = selectedPoint.y-i;

                            int targetX = selectedShip.getStartingPoint().x + (xOffset);
                            int targetY = selectedShip.getStartingPoint().y + (yOffset);

                            //System.out.println("targetx="+targetX + " targety=" + targetY);

                            boolean placementResult =Helper.attemptToPlaceShip(grid,Helper.playerShipMap, Color.BLUE,
                                    grid.length, grid[0].length, selectedShip,j,i,true);
                            if (placementResult == false)
                            {
                                System.out.println("placement result=" + placementResult);
                            } else
                            {
                                System.out.println("placement result=" + placementResult);
                            }

                        }

//                    if (e.getSource() == grid[i][j]) {
//                        System.out.println("i="+i+ " j=" + j);
//                    }
                    }
                }
            } else
            {
                System.out.println(e.getButton());
            }
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {


    }

    private boolean isWithinBounds(int x, int y, Component component) {

        // getBounds() returns a Rectangle.
        if (x >= component.getBounds().x
                && x <= component.getBounds().x + component.getBounds().width
                && y >= component.getBounds().y
                && y <= component.getBounds().y + component.getBounds().height) {

            return true;

        } else {

            return false;

        }

    }
}
