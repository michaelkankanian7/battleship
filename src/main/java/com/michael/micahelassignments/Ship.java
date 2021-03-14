package com.michael.micahelassignments;

import java.awt.*;

public class Ship {

    private Integer width;
    private Integer height;
    private Color color;
    private boolean vertical = true;
    private Point startingPoint;

    public Ship(Integer width, Integer height, Color color, boolean vertical) {
        this.width = width;
        this.height = height;
        this.color = color;
        this.vertical = vertical;
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
