package com.dotsgame.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

public class Dot implements Serializable {
    private static final long serialVersionUID = 1L;

    private int x;
    private int y;
    private static final int RADIUS = 10;

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean containsPoint(int px, int py) {
        int dx = px - x;
        int dy = py - y;
        return dx * dx + dy * dy <= RADIUS * RADIUS;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillOval(x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
    }
}