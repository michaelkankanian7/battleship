package com.michael.micahelassignments;

import java.awt.*;
import java.util.Objects;

public class Ship {

    private Integer width;
    private Integer height;
    private Color color;
    private boolean vertical = true;
    private Point startingPoint;
    private String shipName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return vertical == ship.vertical && width.equals(ship.width) && height.equals(ship.height) && color.equals(ship.color) && shipName.equals(ship.shipName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height, color, vertical, shipName);
    }

    public Ship(Integer width, Integer height, Color color, boolean vertical, String shipName) {
        this.width = width;
        this.height = height;
        this.color = color;
        this.vertical = vertical;
        this.shipName = shipName;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public Point getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(Point startingPoint) {
        this.startingPoint = startingPoint;
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
