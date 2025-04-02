package com.dotsgame.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

public class Line implements Serializable {
    private static final long serialVersionUID = 1L;

    private Dot start;
    private Dot end;

    public Line(Dot start, Dot end) {
        this.start = start;
        this.end = end;
    }

    public Dot getStart() {
        return start;
    }

    public Dot getEnd() {
        return end;
    }

    public double getLength() {
        int dx = start.getX() - end.getX();
        int dy = start.getY() - end.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void draw(Graphics2D g2d, Color color) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
    }
}