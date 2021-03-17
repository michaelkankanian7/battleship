package com.michael.micahelassignments;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class Helper {

    public static Map<String,Ship> playerShipMap = getShips();
    public static Map<String,Ship> computerShipMap = getShips();

    public static Map<String,Ship> getShips()
    {
        Ship carrier = new Ship(2,5, Color.green,true , "carrier");
        Ship battleship = new Ship(1,4, Color.black,true , "battleship");
        Ship destroyer = new Ship(1,3, Color.red,true , "destroyer");
        Ship submarine = new Ship(1,3, Color.yellow,true , "submarine");
        Ship patrolBoat = new Ship(1,2, Color.gray,true , "patrolBoat");

        Map<String,Ship> tempMap = new HashMap<>();
        tempMap.put("carrier",carrier);
        tempMap.put("battleship",battleship);
        tempMap.put("destroyer",destroyer);
        tempMap.put("submarine",submarine);
        tempMap.put("patrolBoat",patrolBoat);
        return tempMap;
    }

    public  static boolean addAndValidatePoints(LinkedList<Point> points,
                                                Ship thisShip,
                                                JPanel[][] grid,
                                                Map<String,Ship> designatedMap,
                                                Color designatedColor)
    {
        //loop to validate fir.st
        for (Point point : points)
        {
            if (grid[point.y][point.x].getBackground() != designatedColor)
            {
                return false;
            }
        }

        //assign the first point from the list as the starting point for the ship
        Ship tempShip = designatedMap.get(thisShip.getShipName());
        tempShip.setStartingPoint(points.get(0));
        designatedMap.put(tempShip.getShipName(),tempShip);

        //loop to apply color
        for (Point point : points)
        {
            grid[point.y][point.x].setBackground(thisShip.getColor());
            grid[point.y][point.x].setName(thisShip.getShipName());
        }
        return true;
    }

    public static void placeShipsRandomly(JPanel[][] grid ,
                                          Map<String,Ship> designatedMap,
                                          Color designatedColor)
    {
        int maxY = grid.length;
        int minY = 0;
        int maxX = grid[0].length;
        int minX = 0;

        Random random = new Random();

        designatedMap.entrySet().stream().map(e -> e.getValue()).forEach(thisship -> {
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
                        if (addAndValidatePoints(points,thisship,grid,designatedMap,designatedColor )== true)
                        {
                            break; //next ship
                        }
                    }
                }
            }
        });
    }
}
