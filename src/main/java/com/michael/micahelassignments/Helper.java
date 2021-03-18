package com.michael.micahelassignments;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Helper {

    public static Map<String, Ship> playerShipMap = getShips();
    public static Map<String, Ship> computerShipMap = getComputerShips();

    public static Map<String, Ship> getShips() {
        Ship carrier = new Ship(2, 5, Color.green, true, "carrier");
        Ship battleship = new Ship(1, 4, Color.black, true, "battleship");
        Ship destroyer = new Ship(1, 3, Color.red, true, "destroyer");
        Ship submarine = new Ship(1, 3, Color.yellow, true, "submarine");
        Ship patrolBoat = new Ship(1, 2, Color.gray, true, "patrolBoat");

        Map<String, Ship> tempMap = new HashMap<>();
        tempMap.put("carrier", carrier);
        tempMap.put("battleship", battleship);
        tempMap.put("destroyer", destroyer);
        tempMap.put("submarine", submarine);
        tempMap.put("patrolBoat", patrolBoat);
        return tempMap;
    }

    public static Map<String, Ship> getComputerShips() {
        Ship carrier = new Ship(2, 5, Color.darkGray, true, "carrierComputer");
        Ship battleship = new Ship(1, 4, Color.darkGray, true, "battleshipComputer");
        Ship destroyer = new Ship(1, 3, Color.darkGray, true, "destroyerComputer");
        Ship submarine = new Ship(1, 3, Color.darkGray, true, "submarineComputer");
        Ship patrolBoat = new Ship(1, 2, Color.darkGray, true, "patrolBoatComputer");

        Map<String, Ship> tempMap = new HashMap<>();
        tempMap.put("carrierComputer", carrier);
        tempMap.put("battleshipComputer", battleship);
        tempMap.put("destroyerComputer", destroyer);
        tempMap.put("submarineComputer", submarine);
        tempMap.put("patrolBoatComputer", patrolBoat);
        return tempMap;
    }

    public static boolean addAndValidatePoints(LinkedList<Point> points,
                                               Ship thisShip,
                                               JPanel[][] grid,
                                               Map<String, Ship> designatedMap,
                                               Color designatedColor,
                                               boolean humanPlayer) {
        //loop to validate first
        for (Point point : points) {

            //debugging condition
            if (point.x < 0)
            {
                System.out.println("something is wrong X is less than 0");
            }
            if (point.y < 0)
            {
                System.out.println("something is wrong Y is less than 0");
            }

            //drag mode condition
            if (grid[point.y][point.x].getName() != null)
            {
                if (!grid[point.y][point.x].getName().equals(thisShip.getShipName()) )
                {
                    return false;
//                    if (grid[point.y][point.x].getBackground() != designatedColor) {
//                        return false;
//                    }
                }
            }

        }

        //clear the ship from the grid in case it exists
        //check if its a human player
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j].getName() != null)
                    {
                        if (grid[i][j].getName().equals(thisShip.getShipName()))
                        {
                            if (humanPlayer)
                            {
                                grid[i][j].setBackground(designatedColor);
                                grid[i][j].setName(null);
                            }
                        }
                    }
                }
            }



        //assign the first point from the list as the starting point for the ship
        Ship tempShip = designatedMap.get(thisShip.getShipName());
        tempShip.setStartingPoint(points.get(0));
        designatedMap.put(tempShip.getShipName(), tempShip);

        //loop to apply color
        for (Point point : points) {
            grid[point.y][point.x].setBackground(thisShip.getColor());
            grid[point.y][point.x].setName(thisShip.getShipName());
        }
        return true;
    }

    public static void placeShipsRandomly(JPanel[][] grid,
                                          Map<String, Ship> designatedMap,
                                          Color designatedColor,
                                          boolean humanPlayer) {
        int maxY = grid.length-1;
        int minY = 0;
        int maxX = grid[0].length-1;
        int minX = 0;

        Random random = new Random();

        designatedMap.entrySet().stream().map(e -> e.getValue()).forEach(thisship -> {
            while (true) {
                int randomX = random.nextInt(maxX - minX) + minX;
                int randomY = random.nextInt(maxY  - minY) + minY;

                if (attemptToPlaceShip(grid, designatedMap, designatedColor, maxY, maxX, thisship, randomX, randomY , humanPlayer))
                {
                    if (!humanPlayer)
                    {
                        System.out.println("added computer ship " + thisship.getShipName());
                    }

                    break; //next ship
                }

            }
        });
    }

    public static void randomizeShipOrientation(JPanel[][] grid,
                                                Map<String, Ship> designatedMap,
                                                Color designatedColor,
                                                boolean isHumanPlayer)
    {
        Random random = new Random(); // creating Random object
        for (String shipName : designatedMap.keySet())
        {
            Ship ship = designatedMap.get(shipName);
            if (random.nextBoolean())
            {
                attemptToFlipShip(grid,designatedColor,ship,designatedMap,isHumanPlayer);
            }
        }
    }

    public synchronized static boolean attemptToPlaceShip(JPanel[][] grid,
                                              Map<String, Ship> designatedMap,
                                              Color designatedColor,
                                              int maxY,
                                              int maxX,
                                              Ship thisship,
                                              int randomX,
                                              int randomY,
                                                          boolean humanPlayer) {

        if (randomX + thisship.getWidth()-1 < maxX) {
            if (randomY + thisship.getHeight()-1 < maxY) {
                //check that we are not overlapping with another ship
                LinkedList<Point> points = new LinkedList<>();

                for (int y = randomY; y < thisship.getHeight() + randomY ; y++) {
                    for (int x = randomX; x < thisship.getWidth() + randomX ; x++) {
                        points.add(new Point(x, y));

                        //grid[y][x].setBackground(thisship.getColor());
                    }
                }
                if (addAndValidatePoints(points, thisship, grid, designatedMap, designatedColor,humanPlayer)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean attemptToFlipShip(JPanel[][] grid,
                                             Color designatedColor,
                                             Ship thisShip,
                                            Map<String, Ship> designatedMap,
                                            boolean isHumanPlayer) {
        int maxY = grid.length;
        int maxX = grid[0].length;


        if (thisShip.getStartingPoint().y + thisShip.getWidth() > maxX)
        {
            return false;
        }
        if (thisShip.getStartingPoint().x + thisShip.getHeight() > maxY) {
            return false;
        }
        //check that we are not overlapping with another ship
        LinkedList<Point> points = new LinkedList<>();

        if (thisShip.isVertical())
        {
            for (int y = thisShip.getStartingPoint().y;
                 y <= thisShip.getWidth() + thisShip.getStartingPoint().y -1; y++) {
                for (int x = thisShip.getStartingPoint().x;
                     x <= thisShip.getHeight() + thisShip.getStartingPoint().x -1; x++) {
                    points.add(new Point(x, y));
                }
            }
        } else
        {
            for (int y = thisShip.getStartingPoint().y;
                 y <= thisShip.getHeight() + thisShip.getStartingPoint().y -1; y++) {
                for (int x = thisShip.getStartingPoint().x;
                     x <= thisShip.getWidth() + thisShip.getStartingPoint().x -1; x++) {
                    points.add(new Point(x, y));
                }
            }
        }


        if (addAndValidateFlippedPoints(points, thisShip, grid, designatedMap, designatedColor,isHumanPlayer)) {
            if (thisShip.isVertical())
            {
                thisShip.setVertical(false);
            } else
            {
                thisShip.setVertical(true);
            }
            return true;
        }
        return false;
    }

    public static boolean addAndValidateFlippedPoints(LinkedList<Point> points,
                                               Ship thisShip,
                                               JPanel[][] grid,
                                               Map<String, Ship> designatedMap,
                                               Color designatedColor,
                                                      boolean humanPlayer) {
        //loop to validate fir.st
        for (Point point : points) {
            if (grid[point.y][point.x].getName() != null)
            {
                if (!grid[point.y][point.x].getName().equals(thisShip.getShipName()))
                {
                    return false;
//                    if (grid[point.y][point.x].getBackground() != designatedColor) {
//
//                    }
                }
            }
        }

        //if we reached here this means it is possible to flip
        //we will now remove the ship color and ship name from the
        //cells of the ship before flipping it
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].getName() != null)
                {
                    if (grid[i][j].getName().equals(thisShip.getShipName()))
                    {
                        if (humanPlayer)
                        {
                            grid[i][j].setBackground(designatedColor);
                        }
                        grid[i][j].setName(null);
                    }
                }
            }
        }


        //assign the first point from the list as the starting point for the ship
        Ship tempShip = designatedMap.get(thisShip.getShipName());
        tempShip.setStartingPoint(points.get(0));
        designatedMap.put(tempShip.getShipName(), tempShip);

        //loop to apply color
        for (Point point : points) {
            if (humanPlayer)
            {
                grid[point.y][point.x].setBackground(thisShip.getColor());
            }
            grid[point.y][point.x].setName(thisShip.getShipName());
        }
        return true;
    }
}
