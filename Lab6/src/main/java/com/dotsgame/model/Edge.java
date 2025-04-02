package com.dotsgame.model;

public class Edge {
    private Dot start;
    private Dot end;
    private double weight;

    public Edge(Dot start, Dot end, double weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public Dot getStart() {
        return start;
    }

    public Dot getEnd() {
        return end;
    }

    public double getWeight() {
        return weight;
    }
}